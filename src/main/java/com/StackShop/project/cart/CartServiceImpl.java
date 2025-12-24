package com.StackShop.project.cart;


import com.StackShop.project.exceptions.ResourceNotFoundException;
import com.StackShop.project.product.Product;
import com.StackShop.project.product.ProductDTO;
import com.StackShop.project.product.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.StackShop.project.exceptions.APIException;
import com.StackShop.project.util.AuthUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createNewCart();

        // Retrieve product details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findItemByProductIdAndCartId(cart.getCartId(), productId);

        if (cartItem != null) {
            throw new APIException("Product" + product.getProductName() + "already exists in cart");
        }

        if (product.getQuantity() == 0) {
            throw new APIException("Product" + product.getProductName() + "is out of stock");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please reduce quantity. Only" + product.getQuantity() + "items are available in stock");
        }


        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getspecialPrice());

        cartItemRepository.save(newCartItem);
        product.setQuantity(product.getQuantity());


        cart.setTotalPrice(cart.getTotalPrice() + (product.getspecialPrice() * quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        cartDTO.setProducts(productDTOStream.toList());


        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (carts.size() == 0) {
            throw new APIException("No carts found");
        }
        List<CartDTO> cartDTOs = carts.stream()
                .map(cart ->  {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                    List<ProductDTO> products = cart.getCartItems().stream()
                            .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                            .collect(Collectors.toList());
                    cartDTO.setProducts(products);
                    return cartDTO;
                }).collect(Collectors.toList());

        return cartDTOs;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
       // return null;
    }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(c-> c.getProduct().setQuantity(c.getQuantity()));
           List<ProductDTO> products = cart.getCartItems().stream()
                .map(p ->  modelMapper.map(p.getProduct(), ProductDTO.class))
                    .toList();
                    cartDTO.setProducts(products);
                    return cartDTO;
    }

    private Cart createNewCart() {
    Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
    if (userCart != null) {
        return userCart;
    }
    Cart cart = new Cart();
    cart.setTotalPrice(0.00);
    cart.setUser(authUtil.loggedInUser());
    Cart newCart = cartRepository.save(cart);
    return newCart;
}

}
