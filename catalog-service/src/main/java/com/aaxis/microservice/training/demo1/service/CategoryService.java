package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.CategoryDao;
import com.aaxis.microservice.training.demo1.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {
    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryDao mCategoryDao;

    @Autowired
    private Environment env;



    public void initData() {
        logger.info("initData");
        String[] categoryIds = env.getProperty("categoryIds").split(",");
        logger.trace("initData() categoryIds:{}", categoryIds);

        for (String categoryId : categoryIds) {
            if (mCategoryDao.findById(categoryId).isPresent()) {
                logger.debug("initData() categoryId:{} isPresent, will be ignored", categoryId);
                continue;
            }
            Category category = new Category();
            category.setId(categoryId);
            String name = "Category_" + categoryId;
            category.setName(name);
            logger.trace("initData() save category id:{}, name:{}", categoryId, name);
            mCategoryDao.save(category);
        }
    }



    public List<Category> findAllCategories() {
        logger.info("findAllCategories");
        return mCategoryDao.findAll();
    }
}
