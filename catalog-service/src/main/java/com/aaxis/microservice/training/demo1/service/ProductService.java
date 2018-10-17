package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.CategoryDao;
import com.aaxis.microservice.training.demo1.dao.ProductDao;
import com.aaxis.microservice.training.demo1.domain.Category;
import com.aaxis.microservice.training.demo1.domain.Product;
import com.aaxis.microservice.training.demo1.domain.RestPageImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private CategoryDao mCategoryDao;

    @Autowired
    private ProductDao mProductDao;

    @Autowired
    private InventoryFeignClient mInventoryFeignClient;

    @Autowired
    private PricingFeignClient mPricingFeignClient;

    @PersistenceContext
    private EntityManager mEntityManager;

    @Autowired
    private Environment mEnvironment;

    private static final String ENV_MAXPRODUCTCOUNTINCATEGORY     = "maxProductCountInCategory";
    private static final String ENV_CHECKPRODUCTEXISTBEFOREADDING = "checkProductExistBeforeAdding";
    private static final String ENV_PRODUCTBATCHSIZE              = "productBatchSize";
    private static final String ENV_PAGESIZE                      = "pageSize";



    public void initData() {
        log.info("initData");
        List<Category> categories = mCategoryDao.findAll();

        if (categories == null) {
            log.error("initData() categories is null");
            return;
        }
        int maxProductCountInCategory = Integer.parseInt(mEnvironment.getProperty(ENV_MAXPRODUCTCOUNTINCATEGORY));
        log.trace("initData() maxProductCountInCategory:{}", maxProductCountInCategory);

        boolean checkProductExistBeforeAdding = Boolean
                .parseBoolean(mEnvironment.getProperty(ENV_CHECKPRODUCTEXISTBEFOREADDING));
        log.trace("initData() checkProductExistBeforeAdding:{}", checkProductExistBeforeAdding);

        int productBatchSize = Integer.parseInt(mEnvironment.getProperty(ENV_PRODUCTBATCHSIZE));
        for (Category category : categories) {

            int randomProductSize = new Random().nextInt(maxProductCountInCategory / 2) + maxProductCountInCategory / 2;
            log.debug("initData() randomProductSize:{}", randomProductSize);
            // select substr(id,3) from product where id like 'D_%' order by convert(substr(id,3),SIGNED) desc limit 1
            int maxProduct = mProductDao.getMaxProductId(category.getId() + "_%");
            log.debug("initData() maxProduct:{}", maxProduct);

            List<Product> productList = new ArrayList<>(productBatchSize);
            if (maxProduct < randomProductSize) {
                for (int i = maxProduct + 1; i <= randomProductSize; i++) {
                    String productId = category.getId() + "_" + i;
                    String productName = RandomStringUtils.randomAlphanumeric(32);
                    if (checkProductExistBeforeAdding && mProductDao.findById(productId).isPresent()) {
                        log.info("initData() Ignore this product:{}", productId);
                        continue;
                    }
                    log.info("initData() Create this product:{}, max is:{}", productId, randomProductSize);
                    Product product = new Product();
                    product.setId(productId);
                    product.setName(productName);
                    product.setPriority(new Random().nextInt(100));
                    Date date = randomDate("2010-01-01", "2018-01-01");
                    product.setCreatedDate(date);
                    //                product.setPrice(new BigDecimal(new Random().nextDouble() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP)
                    //                        .doubleValue());
                    product.setCategory(category);
                    //                mProductDao.save(product);
                    productList.add(product);

                    if (productList.size() % productBatchSize == 0) {
                        mProductDao.saveAll(productList);
                        productList.clear();
                    }
                }
            }

            if (!productList.isEmpty()) {
                mProductDao.saveAll(productList);
                productList.clear();
            }
        }

        mPricingFeignClient.initData();
        mInventoryFeignClient.initData();
    }



    public List<Product> findProductsByCategoryId(String categoryId) {
        return mProductDao.findProductsByCategoryId(categoryId);
    }



    public Page<Product> findProductsInPLP(String categoryId, int page, String sortName, String sortValue) {
        long startTime = System.currentTimeMillis();
        int pageSize = Integer.parseInt(mEnvironment.getProperty(ENV_PAGESIZE));
        // using native query
        Sort sort = null;
        List<Product> result = null;
        int total = mProductDao.findProductsInPLPCount(categoryId);
        int offset = (page - 1) * pageSize;
        if (sortName == null) {
            sort = Sort.unsorted();
            result = mProductDao.findProductsInPLP(categoryId, offset, pageSize);
        } else {
            if (!"ASC".equalsIgnoreCase(sortValue)) {
                sortValue = "DESC";
            }
            sort = Sort.by(QSort.Direction.valueOf(sortValue.toUpperCase()), sortName);
            // if offset is more than half, query by reverse order
            int sqlPageSize = pageSize;
            boolean reverse = offset > (total / 2);
            if (reverse) {
                int reverseOffset = total - offset;
                offset = reverseOffset > pageSize ? reverseOffset - pageSize : 0;
                if (reverseOffset < 0) {
                    // pageNo is more than last pageNo
                    sqlPageSize = 0;
                } else if (reverseOffset < pageSize) {
                    // pageNo is the last page and page content size is less than one page size
                    sqlPageSize = reverseOffset;
                }
                sortValue = "ASC".equalsIgnoreCase(sortValue) ? "DESC" : "ASC";
            }
            result = queryProductByRawSQL(categoryId, sortName, sortValue, offset, sqlPageSize);
            if (reverse) {
                Collections.reverse(result);
            }
        }
        Page<Product> pageResult = new RestPageImpl(result, page - 2 < 0 ?
                PageRequest.of(0, pageSize, sort).first() :
                PageRequest.of(page - 2, pageSize, sort).next(), total);
        addPriceAndInventory(pageResult.getContent());
        log.info("new    COST_TIME:{}", System.currentTimeMillis() - startTime);
        return pageResult;
    }



    /**
     * when sort name is not null, need dynamic add 'order by xxx' to raw SQL, so using mEntityManager to create native
     * query instead of using @Query by SpringDataJPA
     *
     * @param categoryId
     * @param sortName
     * @param sortType
     * @param offSet
     * @param pageSize
     * @return
     */
    public List<Product> queryProductByRawSQL(String categoryId, String sortName, String sortType, int offSet,
            int pageSize) {
        if (sortName.equalsIgnoreCase("createdDate")) {
            sortName = "created_date";
        }
        List<Product> products = new ArrayList<>();
        String queryStr = "SELECT p.id,created_date,name,priority FROM product p "
                + "INNER JOIN ( SELECT id FROM product USE INDEX (`PRIMARY`) WHERE category_id = ?1 ORDER BY "
                + sortName + " " + sortType + " LIMIT ?2,?3 ) t ON t.id = p.id";
        try {
            Query query = mEntityManager.createNativeQuery(queryStr);
            query.setParameter(1, categoryId);
            query.setParameter(2, offSet);
            query.setParameter(3, pageSize);
            Iterator itr = query.getResultList().iterator();
            query.getResultList().forEach(pO -> products.add(createProduct((Object[]) pO)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return products;
    }



    private Product createProduct(Object[] columns) {
        Product product = new Product();
        product.setId((String) columns[0]);
        product.setCreatedDate((Date) columns[1]);
        product.setName((String) columns[2]);
        product.setPriority((Integer) (columns[3]));
        return product;
    }



    /**
     * find products by spring data pagination
     *
     * @param categoryId
     * @param page
     * @param sortName
     * @param sortValue
     * @return
     */
    public Page<Product> findProductsInPLP_legacy(String categoryId, int page, String sortName, String sortValue) {
        long startTime = System.currentTimeMillis();
        int pageSize = Integer.parseInt(mEnvironment.getProperty(ENV_PAGESIZE));
        Specification<Product> spec = (pRoot, pCriteriaQuery, pCriteriaBuilder) -> pCriteriaBuilder
                .equal(pRoot.get("category").as(Category.class), mCategoryDao.findById(categoryId).get());
        Pageable pageable = PageRequest.of(page - 1, pageSize, sortName == null ?
                Sort.unsorted() :
                Sort.by(QSort.Direction.valueOf("ASC".equalsIgnoreCase(sortValue) ? "ASC" : "DESC"), sortName)).next();
        Page<Product> pageResult = mProductDao.findAll(spec, pageable);
        addPriceAndInventory(pageResult.getContent());
        log.info("Legacy COST_TIME:{}", System.currentTimeMillis() - startTime);
        return pageResult;
    }



    public Page<Product> searchProducts(int page, String productId, String name, String sortName, String sortValue) {

        // implemente this method.
        int pageSize = Integer.parseInt(mEnvironment.getProperty(ENV_PAGESIZE));
        Pageable pageable = PageRequest.of(page - 1, pageSize, sortName == null ?
                Sort.unsorted() :
                Sort.by(QSort.Direction.valueOf("ASC".equalsIgnoreCase(sortValue) ? "ASC" : "DESC"), sortName)).next();

        Page<Product> result = null;
        result = mProductDao.findByIdContainingAndNameContaining(StringUtils.isNotBlank(productId) ? productId : "",
                StringUtils.isNotBlank(name) ? name : "", pageable);
        return result;
    }



    public void addPriceAndInventory(List<Product> products) {
        if (products == null) {
            return;
        }
        products.forEach(pProduct -> {
            pProduct.setPrice(getProductPrice(pProduct.getId()));
            pProduct.setStock(getProductInventory(pProduct.getId()));
        });
    }



    public double getProductPrice(String pProductId) {
        return mPricingFeignClient.findPrice(pProductId).getPrice();
    }



    public int getProductInventory(String pProductId) {
        return mInventoryFeignClient.findInventory(pProductId).getStock();
    }



    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }



    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

