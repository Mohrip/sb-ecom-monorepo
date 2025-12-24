package com.StackShop.project.cart;

import org.springframework.stereotype.Service;

public interface CartService {
    public CartDTO addProductToCart( Long productId, Integer quantity);
        // Implementation goes here

}
