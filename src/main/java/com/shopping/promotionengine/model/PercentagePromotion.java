package com.shopping.promotionengine.model;

/**
 * This class can be used when a promotion is applied on a item with some percentage value.
 */
public class PercentagePromotion extends AbstractPromotion {
    
    private final int percentageValue;
    private final String skuId;
    
    public PercentagePromotion(final int percentageValue, final String skuId) {
        super(PromotionOperator.PERCENTAGE);
        this.percentageValue = percentageValue;
        this.skuId = skuId;
    }
    
    public int getPercentageValue() {
        return percentageValue;
    }
    
    public String getSkuId() {
        return skuId;
    }
}
