package com.shopping.promotionengine.service;

import com.shopping.promotionengine.model.AbstractPromotion;

import java.util.List;

public interface ICartService {
    
    long checkOut(List<AbstractPromotion> promotions);
}
