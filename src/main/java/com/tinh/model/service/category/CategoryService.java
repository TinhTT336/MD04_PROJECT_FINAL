package com.tinh.model.service.category;

import com.tinh.model.entity.Category;
import com.tinh.model.service.IGenericService;

import java.util.List;

public interface CategoryService extends IGenericService<Category,Integer,String> {
     void changeStatus(Integer id);
     List<Category> findParent();
     Integer getTotalPagePagination(Integer limit, Integer currentPage);
     Integer getTotalPageSearch(String searchName);
     Integer countCategory(Boolean b);
     Boolean findByName(String name);
     Boolean checkCategoryNameExist(String categoryName);
     Boolean checkCategoryNameExistEdit(String categoryName,String name);
     Boolean checkParentId(Integer parentId);
     List<Category>findByParentId(Integer parentId);
}
