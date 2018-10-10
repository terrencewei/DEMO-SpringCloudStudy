package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.CategoryDao;
import com.aaxis.microservice.training.demo1.dao.ProductDao;
import com.aaxis.microservice.training.demo1.domain.Category;
import com.aaxis.microservice.training.demo1.domain.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private CategoryDao mCategoryDao;

    @Autowired
    private ProductDao mProductDao;

    //    @Autowired
    //    private FeignInventoryService mFeignInventoryService;
    //
    //    @Autowired
    //    private FeignPricingService mFeignPricingService;

    @Autowired
    private RestTemplate restTemplate;

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
                    break;
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

        restTemplate.getForObject("http://172.17.118.200:8081/price/initData", Map.class);
        restTemplate.getForObject("http://172.17.118.200:8082/inventory/initData", Map.class);
        //        mFeignPricingService.initData();
        //        mFeignInventoryService.initData();
    }



    public List<Product> findProductsByCategoryId(String categoryId) {
        return mProductDao.findProductsByCategoryId(categoryId);
    }



    public Page<Product> findProductsInPLP(String categoryId, int page, String sortName, String sortValue) {
        long startTime = System.currentTimeMillis();
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
        Specification<Product> spec = (pRoot, pCriteriaQuery, pCriteriaBuilder) -> {
            Path<Category> name = pRoot.get("category");
            Predicate p = pCriteriaBuilder.equal(name.as(Category.class), mCategoryDao.findById(categoryId).get());
            return p;
        };

        Pageable pageable = null;

        if (sortName != null) {
            Sort sort = new Sort("ASC".equalsIgnoreCase(sortValue) ? QSort.Direction.ASC : QSort.Direction.DESC,
                    sortName);
            pageable = new PageRequest(page - 1, 20, sort);
        } else {
            pageable = new PageRequest(page - 1, 20);
        }

        Page<Product> pageResult = mProductDao.findAll(spec, pageable);
        addPriceAndInventory(pageResult.getContent());
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("COST_TIME:" + cost);
        return pageResult;
    }



    public Page<Product> searchProducts(int page, String productId, String name, String sortName, String sortValue) {

        // implemente this method.

        return null;
    }



    public void addPriceAndInventory(List<Product> products) {
        for (Product product : products) {
            product.setPrice(getProductPrice(product.getId()));
            product.setStock(getProductInventory(product.getId()));
        }
    }



    public double getProductPrice(String pProductId) {
        Double price = (Double) ((Map) restTemplate
                .getForObject("http://172.17.118.200:8081/api/price/" + pProductId, Map.class)).get("price");
        return price;
        //        return mFeignPricingService.findPrice(pProductId).getPrice();
    }



    public int getProductInventory(String pProductId) {
        Integer stock = (Integer) ((Map) restTemplate
                .getForObject("http://172.17.118.200:8082//api/inventory/" + pProductId, Map.class)).get("stock");
        return stock;
        //        return mFeignInventoryService.findInventory(pProductId).getStock();
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

