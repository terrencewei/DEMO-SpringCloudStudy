package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.CategoryDao;
import com.aaxis.microservice.training.demo1.dao.ProductDao;
import com.aaxis.microservice.training.demo1.domain.Category;
import com.aaxis.microservice.training.demo1.domain.Product;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {
    @Autowired
    private CategoryDao mCategoryDao;

    @Autowired
    private ProductDao mProductDao;

    // <<<<<<<<<<<< origin
    //    @Autowired
    //    private RestTemplate          restTemplate;
    // ============
    @Autowired
    private FeignInventoryService mFeignInventoryService;

    @Autowired
    private FeignPricingService mFeignPricingService;

    // >>>>>>>>>>>> terrencewei updated

    @Autowired
    private Environment env;

    private static final int PRODUCT_BATCH_SIZE = 1000;



    public void initData() {
        List<Category> categories = mCategoryDao.findAll();

        if (categories == null) {
            return;
        }
        int maxProductCountInCategory = Integer.parseInt(env.getProperty("maxProductCountInCategory"));

        String checkProductExistBeforeAdding = env.getProperty("checkProductExistBeforeAdding");

        for (Category category : categories) {

            int randomProductSize = new Random().nextInt(maxProductCountInCategory / 2) + maxProductCountInCategory / 2;

            List<Product> productList = new ArrayList<>(PRODUCT_BATCH_SIZE);

            for (int i = 1; i <= randomProductSize; i++) {
                String productId = category.getId() + "_" + i;
                String productName = RandomStringUtils.randomAlphanumeric(32);
                if ("true".equalsIgnoreCase(checkProductExistBeforeAdding) && mProductDao.findById(productId)
                        .isPresent()) {
                    continue;
                }
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

                if (productList.size() % PRODUCT_BATCH_SIZE == 0) {
                    mProductDao.saveAll(productList);
                    productList.clear();
                }
            }

            if (!productList.isEmpty()) {
                mProductDao.saveAll(productList);
                productList.clear();
            }
        }

        // <<<<<<<<<<<< origin
        //        restTemplate.getForObject("http://172.17.118.200:8081/api/price/initData", Map.class);
        //        restTemplate.getForObject("http://172.17.118.200:8082/api/inventory/initData", Map.class);
        // ============
        mFeignPricingService.initData();
        mFeignInventoryService.initData();
        // >>>>>>>>>>>> terrencewei updated
    }



    public List<Product> findProductsByCategoryId(String categoryId) {
        return mProductDao.findProductsByCategoryId(categoryId);
    }



    public Page<Product> findProductsInPLP(String categoryId, int page, String sortName, String sortValue) {
        long startTime = System.currentTimeMillis();
        // <<<<<<<<<<<< origin
        //        Specification<Product> spec = new Specification<Product>() {
        //            @Nullable
        //            @Override
        //            public Predicate toPredicate(Root<Product> pRoot, CriteriaQuery<?> pCriteriaQuery,
        //                    CriteriaBuilder pCriteriaBuilder) {
        //                Path<Category> name = pRoot.get("category");
        //                Predicate p = pCriteriaBuilder.equal(name.as(Category.class), mCategoryDao.findById(categoryId).get());
        //                return p;
        //            }
        //        };
        // ============
        Specification<Product> spec = (pRoot, pCriteriaQuery, pCriteriaBuilder) -> pCriteriaBuilder
                .equal(pRoot.get("category").as(Category.class), mCategoryDao.findById(categoryId).get());
        // >>>>>>>>>>>> terrencewei updated

        // <<<<<<<<<<<< origin
        //        Pageable pageable = null;
        //
        //        if (sortName != null) {
        //            Sort sort = new Sort("ASC".equalsIgnoreCase(sortValue) ? QSort.Direction.ASC : QSort.Direction.DESC, sortName);
        //            pageable = new PageRequest(page-1, 20, sort);
        //        } else {
        //            pageable = new PageRequest(page-1, 20);
        //        }
        //
        //        Page<Product> pageResult = mProductDao.findAll(spec, pageable);
        //        addPriceAndInventory(pageResult.getContent());
        //        long cost = System.currentTimeMillis()-startTime;
        //        System.out.println("COST_TIME:"+cost);
        //        return pageResult;
        // ============
        Pageable pageable = PageRequest.of(page - 1, 20, sortName == null ?
                Sort.unsorted() :
                Sort.by(QSort.Direction.valueOf("ASC".equalsIgnoreCase(sortValue) ? "ASC" : "DESC"), sortName)).first();
        // >>>>>>>>>>>> terrencewei updated
        Page<Product> pageResult = mProductDao.findAll(spec, pageable);
        addPriceAndInventory(pageResult.getContent());
        System.out.println("COST_TIME:" + (System.currentTimeMillis() - startTime));
        return pageResult;
    }



    public Page<Product> searchProducts(int page, String productId, String name, String sortName, String sortValue) {

        // implemente this method.
        Pageable pageable = PageRequest.of(page - 1, 20, sortName == null ?
                Sort.unsorted() :
                Sort.by(QSort.Direction.valueOf("ASC".equalsIgnoreCase(sortValue) ? "ASC" : "DESC"), sortName)).first();

        Page<Product> result = null;
        if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(name)) {
            result = mProductDao.findByIdContainingAndNameContaining(productId, name, pageable);
        } else if (StringUtils.isNotBlank(productId)) {
            result = mProductDao.findByIdContaining(productId, pageable);
        } else if (StringUtils.isNotBlank(name)) {
            result = mProductDao.findByNameContaining(name, pageable);
        } else {
            result = mProductDao.findAll(pageable);
        }
        return result;
    }



    public void addPriceAndInventory(List<Product> products) {
        // <<<<<<<<<<<< origin
        //        for (Product product : products) {
        //            product.setPrice(getProductPrice(product.getId()));
        //            product.setStock(getProductInventory(product.getId()));
        //        }
        // ============
        if (products == null) {
            return;
        }
        products.forEach(pProduct -> {
            pProduct.setPrice(getProductPrice(pProduct.getId()));
            pProduct.setStock(getProductInventory(pProduct.getId()));
        });
        // >>>>>>>>>>>> terrencewei updated
    }



    public double getProductPrice(String pProductId) {
        // <<<<<<<<<<<< origin
        //        Double price = (Double) ((Map) restTemplate
        //                .getForObject("http://172.17.118.200:8081/api/price/" + pProductId, Map.class)).get("price");
        //        return price;
        // ============
        return mFeignPricingService.findPrice(pProductId).getPrice();
        // >>>>>>>>>>>> terrencewei updated
    }



    public int getProductInventory(String pProductId) {
        // <<<<<<<<<<<< origin
        //        Integer stock = (Integer) ((Map) restTemplate
        //                .getForObject("http://172.17.118.200:8082//api/inventory/" + pProductId, Map.class)).get("stock");
        //        return stock;
        // ============
        return mFeignInventoryService.findInventory(pProductId).getStock();
        // >>>>>>>>>>>> terrencewei updated
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

