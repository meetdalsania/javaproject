package com.shopping.promotionengine.service.impl;

import com.shopping.promotionengine.model.AdditionPromotion;
import com.shopping.promotionengine.model.Cart;
import com.shopping.promotionengine.model.Item;
import com.shopping.promotionengine.service.ICartService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CartServiceTest {
    
    private Cart cart;
    
    @Before
    public void setUp() {
        cart = Cart.getInstance(new ArrayList<>());
        cart.getCartItems().clear();
    }
    
    @Test
    public void testAdditionDiscountFinalPriceScenario1() {
        createItems(1, 1, 1).forEach(item -> cart.addItem(item));
        ICartService cartService = new CartService(cart);
        long price = cartService.checkOut(List.of(
            new AdditionPromotion(130, "A", "A", "A"),
            new AdditionPromotion(45, "B", "B"),
            new AdditionPromotion(30, "C", "D")
        ));
        Assert.assertEquals(100, price);
    }
    
    @Test
    public void testAdditionDiscountFinalPriceScenario2() {
        createItems(5, 5, 1).forEach(item -> cart.addItem(item));
        ICartService cartService = new CartService(cart);
        long price = cartService.checkOut(List.of(
            new AdditionPromotion(130, "A", "A", "A"),
            new AdditionPromotion(45, "B", "B"),
            new AdditionPromotion(30, "C", "D")
        ));
        Assert.assertEquals(370, price);
    }
    
    @Test
    public void testAdditionDiscountFinalPriceScenario3() {
        createItems(3, 5, 1).forEach(item -> cart.addItem(item));
        cart.addItem(new Item("D", 15, 1));
        ICartService cartService = new CartService(cart);
        long price = cartService.checkOut(List.of(
            new AdditionPromotion(130, "A", "A", "A"),
            new AdditionPromotion(45, "B", "B"),
            new AdditionPromotion(30, "C", "D")
        ));
        Assert.assertEquals(280, price);
    }
    
    private List<Item> createItems(int aQuantity, int bQuantity, int cQuantity) {
        return List.of(
            new Item("A", 50, aQuantity),
            new Item("B", 30, bQuantity),
            new Item("C", 20, cQuantity)
        );
    }
    
    private List<AdditionPromotion> createPromotions() {
        return List.of(
            new AdditionPromotion(130, "A", "A", "A"),
            new AdditionPromotion(45, "B", "B"),
            new AdditionPromotion(30, "C", "D")
        );
    }
}
