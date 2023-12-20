package com.tinh.model.dao.category;

import com.tinh.model.dao.IGenericDao;
import com.tinh.model.entity.Category;

import java.util.List;

public interface CategoryDao extends IGenericDao<Category,Integer,String> {
    List<Category> findParent();
     Integer getTotalPagePagination(Integer limit, Integer currentPage);
     Integer getTotalPageSearch(String searchName);
     Integer countCategory(Boolean b);
    Boolean findByName(String name);
    Boolean checkCategoryNameExist(String categoryName);
    List<Category>findByParentId(Integer parentId);
}
