package com.StackShop.project.Product;

import org.springframework.beans.factory.annotation.Autowired;


public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();
}
