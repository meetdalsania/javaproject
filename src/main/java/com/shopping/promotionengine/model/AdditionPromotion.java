package com.shopping.promotionengine.model;

import java.util.List;

/**
 * This class can be used when a promotion is applied on multiple items.
 */
public class AdditionPromotion extends AbstractPromotion {
    
    private final int discountPrice;
    private final List<String> skuIds;
    
    public AdditionPromotion(final int discountPrice, final String...skuIds) {
        super(PromotionOperator.ADD);
        this.discountPrice = discountPrice;
        this.skuIds = List.of(skuIds);
    }
    
    public int getDiscountPrice() {
        return discountPrice;
    }
    
    public List<String> getSkuIds() {
        return skuIds;
    }
}
