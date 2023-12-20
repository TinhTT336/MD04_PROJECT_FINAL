package com.tinh.model.service.product;

import com.tinh.model.dto.product.ProductDTO;
import com.tinh.model.entity.Product;

import java.util.List;

public interface ProductService{
    List<Product> findAll();
    boolean update(Product product);
    Product findById(Integer id);
    List<Product>findByName(String s);
    List<Product>pagination(Integer limit,Integer currentPage);
    void changeStatus(Integer id);

    Integer save(Product product);
    Integer save(ProductDTO productDTO);
    boolean checkProductNameExist(String productName);
    List<Product> findAllActiveStatus(boolean status);
    boolean checkCategoryNull(Integer categoryId);
     List<Product> search(String name,int limit,int currentPage);
    Integer totalPageSearch(String name,int limit);

    int countProductByStatus(boolean b);
    Integer totalProductSearch(String name);

    List<Product> paginationActive(Integer limit, Integer currentPage);
    List<Product> searchActive(String name,int limit,int currentPage);
    Integer totalPageSearchActive(String name,int limit);
    Integer totalPageActive(int limit,int currentPage);
    Integer totalProductSearchActive(String name);
    List<Product> paginationAndSortActive(Integer limit, Integer currentPage,String sortType);
    Integer totalPageActiveSort(Integer limit, Integer currentPage,String sortType);
    List<Product> findByCategoryId(Integer categoryId);
    List<String> listBrand();
    List<Product>findByBrand(String brand);
    Integer countProductByBrand(String brand);
    List<Product> paginationAndSort(Integer limit, Integer currentPage, String sortType);

    Integer totalPageSort(Integer limit, Integer currentPage, String sortType);

}
