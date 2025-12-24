package com.StackShop.project.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Spring JPA can't auto generate this query, so we have to declare it here
    // It finds a cart by the user's email because email is defined as unique in the User entity not in the Cart entity

    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1")
    // ?1 refers to the first parameter of the method
    Cart findCartByEmail(String email);
}
