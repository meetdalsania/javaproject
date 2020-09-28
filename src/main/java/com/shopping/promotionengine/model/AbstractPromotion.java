package com.shopping.promotionengine.model;

public abstract class AbstractPromotion {
    
    private final PromotionOperator operator;
    
    public AbstractPromotion(final PromotionOperator operator) {
        this.operator = operator;
    }
    
    public PromotionOperator getOperator() {
        return operator;
    }
    
    public enum PromotionOperator {
        ADD,
        PERCENTAGE
    }
}
