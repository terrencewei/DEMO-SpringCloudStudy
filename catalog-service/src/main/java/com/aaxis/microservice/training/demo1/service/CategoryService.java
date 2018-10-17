package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.CategoryDao;
import com.aaxis.microservice.training.demo1.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryDao mCategoryDao;

    @Autowired
    private Environment env;



    public void initData() {
        log.info("initData");
        String[] categoryIds = env.getProperty("categoryIds").split(",");
        log.trace("initData() categoryIds:{}", categoryIds);

        for (String categoryId : categoryIds) {
            if (mCategoryDao.findById(categoryId).isPresent()) {
                log.debug("initData() categoryId:{} isPresent, will be ignored", categoryId);
                continue;
            }
            Category category = new Category();
            category.setId(categoryId);
            String name = "Category_" + categoryId;
            category.setName(name);
            log.trace("initData() save category id:{}, name:{}", categoryId, name);
            mCategoryDao.save(category);
        }
    }



    public List<Category> findAllCategories() {
        log.debug("findAllCategories");
        return mCategoryDao.findAll();
    }
}
