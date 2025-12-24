package com.StackShop.project.cart;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CartService {
    public CartDTO addProductToCart( Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    @Transactional
    CartDTO updateCartItemQuantity(Long productId, Integer quantity);

    String deleteProductFromCart(Long productId, Long cartId);

}
