package com.shopping.promotionengine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Use this class to create a single cart instance which can have multiple items in it.
 */
public class Cart implements Serializable {
    
    private List<Item> cartItems;
    private static volatile Cart cart;
    
    private Cart(final List<Item> cartItems) {
        //Prevent form the reflection.
        if (cart != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of a cart.");
        }
        this.cartItems = cartItems;
    }
    
    /**
     * Use to create a single cart object.
     * @param cartItems list of items in a cart.
     */
    public static Cart getInstance(final List<Item> cartItems) {
        if (cart == null) {
            synchronized (Cart.class) {
                cart = new Cart(cartItems);
            }
        }
        return cart;
    }
    
    public List<Item> getCartItems() {
        return cartItems;
    }
    
    public void addItem(final Item item) {
        this.cartItems.add(item);
    }
    
    // To get the same object while serializing or deserializing.
    public Cart readResolve() {
        return getInstance(cartItems);
    }
}
