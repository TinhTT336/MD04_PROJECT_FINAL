package com.tinh.model.service.category;

import com.tinh.model.dao.category.CategoryDao;
import com.tinh.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    private static int totalPage;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        return categoryDao.saveOrUpdate(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryDao.findById(id);
    }

    @Override
    public List<Category> search(String s) {
        return categoryDao.search(s);
    }

    @Override
    public List<Category> pagination(Integer a, Integer b) {
        return categoryDao.pagination(a, b);
    }


    @Override
    public void changeStatus(Integer id) {
        categoryDao.changeStatus(id);
    }

    @Override
    public List<Category> findParent() {
        return categoryDao.findParent();
    }

    @Override
    public Integer getTotalPagePagination(Integer limit, Integer currentPage) {
        return categoryDao.getTotalPagePagination(limit, currentPage);
    }

    @Override
    public Integer getTotalPageSearch(String searchName) {
        return categoryDao.getTotalPageSearch(searchName);
    }

    @Override
    public Integer countCategory(Boolean b) {
        return categoryDao.countCategory(b);
    }

    @Override
    public Boolean findByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public Boolean checkCategoryNameExist(String categoryName) {
        return categoryDao.checkCategoryNameExist(categoryName);
    }

    @Override
    public Boolean checkCategoryNameExistEdit(String categoryName, String name) {
        for (Category category : categoryDao.findAll()) {
            if(!category.getCategoryName().equalsIgnoreCase(name)){
                if (category.getCategoryName().equals(categoryName)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public List<Category> findByParentId(Integer parentId) {
        return categoryDao.findByParentId(parentId);
    }

    @Override
    public Boolean checkParentId(Integer parentId) {
        if (parentId == null) {
            return true;
        }
        return false;
    }
}
