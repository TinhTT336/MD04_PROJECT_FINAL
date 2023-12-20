package com.tinh.model.service.product;

import com.tinh.model.dao.category.CategoryDao;
import com.tinh.model.dao.product.ProductDao;
import com.tinh.model.dto.product.ProductDTO;
import com.tinh.model.entity.Category;
import com.tinh.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public boolean update(Product product) {
        return productDao.update(product);
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findByName(String s) {
        return productDao.findByName(s);
    }

    @Override
    public List<Product> pagination(Integer litmit, Integer currentPage) {
        return productDao.pagination(litmit, currentPage);
    }

    @Override
    public void changeStatus(Integer id) {
        productDao.changeStatus(id);
    }

    @Override
    public Integer save(Product product) {
        return productDao.save(product);
    }

    @Override
    public Integer save(ProductDTO productDTO) {
//        Product product=new ModelMapper().map(productDTO, Product.class);

        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setImage(productDTO.getImage().getOriginalFilename());
        product.setProductStatus(productDTO.isProductStatus());
        product.setStock(productDTO.getStock());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        return productDao.save(product);
    }

    @Override
    public boolean checkProductNameExist(String productName) {
        List<Product> productList = productDao.findByName(productName);
        if (!productList.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Product> findAllActiveStatus(boolean status) {
        return productDao.findAllActiveStatus(status);
    }

    @Override
    public boolean checkCategoryNull(Integer categoryId) {
        Category category = categoryDao.findById(categoryId);
        if (category == null) {
            return true;
        }
        return false;
    }

    @Override
    public List<Product> search(String name, int limit, int currentPage) {
        return productDao.search(name, limit, currentPage);
    }

    @Override
    public Integer totalPageSearch(String name,int limit) {
        return productDao.totalPageSearch(name,limit);
    }

    @Override
    public int countProductByStatus(boolean b) {
        return productDao.countProductByStatus(b);
    }

    @Override
    public Integer totalProductSearch(String name) {
        return productDao.totalProductSearch(name);
    }

    @Override
    public List<Product> paginationActive(Integer limit, Integer currentPage) {
        return productDao.paginationActive(limit, currentPage);
    }

    @Override
    public List<Product> searchActive(String name, int limit, int currentPage) {
        return productDao.searchActive(name, limit, currentPage);
    }

    @Override
    public Integer totalPageSearchActive(String name, int limit) {
        return productDao.totalPageSearchActive(name, limit);
    }

    @Override
    public Integer totalPageActive( int limit, int currentPage) {
        return productDao.totalPageActive( limit, currentPage);
    }

    @Override
    public Integer totalProductSearchActive(String name) {
        return productDao.totalProductSearchActive(name);
    }

    @Override
    public List<Product> paginationAndSortActive(Integer limit, Integer currentPage, String sortType) {
        return productDao.paginationAndSortActive(limit, currentPage, sortType);
    }

    @Override
    public Integer totalPageActiveSort(Integer limit, Integer currentPage, String sortType) {
        return productDao.totalPageActiveSort(limit, currentPage, sortType);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productDao.findByCategoryId(categoryId);
    }

    @Override
    public List<String> listBrand() {
        return productDao.listBrand();
    }

    @Override
    public List<Product> findByBrand(String brand) {
        return productDao.findByBrand(brand);
    }

    @Override
    public Integer countProductByBrand(String brand) {
        return productDao.countProductByBrand(brand);
    }

    @Override
    public List<Product> paginationAndSort(Integer limit, Integer currentPage, String sortType) {
        return productDao.paginationAndSort(limit, currentPage, sortType);
    }

    @Override
    public Integer totalPageSort(Integer limit, Integer currentPage, String sortType) {
        return productDao.totalPageSort(limit, currentPage, sortType);
    }


}
