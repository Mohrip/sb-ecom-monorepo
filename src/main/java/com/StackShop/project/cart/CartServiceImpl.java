package com.StackShop.project.cart;


import com.StackShop.project.exceptions.ResourceNotFoundException;
import com.StackShop.project.product.Product;
import com.StackShop.project.product.ProductDTO;
import com.StackShop.project.product.ProductRepository;
import jakarta.transaction.Transactional;
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




    // We will do multiple db operations here, so we need to use @Transactional to insure data consistency
    // and integrity and to not have to manually handle transactions
    // Rollback will be done automatically if any exception occurs
    @Override
    @Transactional
    public CartDTO updateCartItemQuantity(Long productId, Integer quantity) {

        String emailId = authUtil.loggedInEmail();
        Cart usersCart = cartRepository.findCartByEmail(emailId);
        Long cartId = usersCart.getCartId();

        Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        if (product.getQuantity() == 0) {
            throw new APIException("Product" + product.getProductName() + "is out of stock");
        }
        if (product.getQuantity() < quantity) {
            throw new APIException("Please reduce quantity. Only" + product.getQuantity() + "items are available in stock");
        }
        CartItem cartItem = cartItemRepository.findItemByProductIdAndCartId(cartId, productId);
        if (cartItem == null) {
            throw new APIException("Product" + product.getProductName() + "not available in cart");
        }
        cartItem.setProductPrice(product.getspecialPrice());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setDiscount(product.getDiscount());
        cart.setTotalPrice(cart.getTotalPrice() + (product.getspecialPrice() * quantity));
        cartRepository.save(cart);
        CartItem updateItem = cartItemRepository.save(cartItem);
        if(updateItem.getQuantity() == 0) {
            cartItemRepository.deleteById(updateItem.getCartItemId());
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });
        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }

    @Override
    public String deleteProductFromCart(Long productId, Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        CartItem cartItem = cartItemRepository.findItemByProductIdAndCartId(cartId, productId);
        if (cartItem == null) {
            throw new APIException("Product with id" + productId + "not found in cart");
        }
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));
        cartItemRepository.deleteCartItemByProductIdAndCartId(productId, cartId);
        return "Product" + cartItem.getProduct().getProductName() + "removed from cart successfully";
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
