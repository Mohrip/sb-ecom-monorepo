package com.StackShop.project.cart;

import com.StackShop.project.product.ProductDTO;

public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cart;
    private ProductDTO productDTO;
    private Integer quantity;
    private Double discount;
    private Double productPrice;

}
