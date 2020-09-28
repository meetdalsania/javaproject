package com.shopping.promotionengine.model;

import java.util.Objects;

/**
 * This class is used to create an item or list of items of a cart.
 * It also contains skuId on which discounts need to be applied.
 *
 */
public class Item {
    
    private final String skuId;
    private final int price;
    private int quantity;
    
    public Item(String skuId, int price, int quantity) {
        this.skuId = skuId;
        this.price = price;
        this.quantity = quantity;
    }
    
    // This is to create an unique item based on its id and price.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return price == item.price &&
            skuId.equals(item.skuId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(skuId, price);
    }
    
    public String getSkuId() {
        return skuId;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setQuantity(int remaining) {
        this.quantity = remaining;
    }
    
    
    public int getQuantity() {
        return quantity;
    }
}
