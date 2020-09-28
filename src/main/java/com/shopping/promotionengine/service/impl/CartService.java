package com.shopping.promotionengine.service.impl;

import com.shopping.promotionengine.model.AbstractPromotion;
import com.shopping.promotionengine.model.AdditionPromotion;
import com.shopping.promotionengine.model.Cart;
import com.shopping.promotionengine.model.Item;
import com.shopping.promotionengine.model.PercentagePromotion;
import com.shopping.promotionengine.service.ICartService;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class CartService implements ICartService {
    
    private final Cart cart;
    
    public CartService(final Cart cart) {
        this.cart = cart;
    }
    
    /**
     * Need to use to calculate the final price while check-out.
     * @param promotions contains which type of promotion it is and which all SKU ids or combination of SKU ids need to consider.
     * @return final cart price after applying a discount.
     */
    @Override
    public long checkOut(final List<AbstractPromotion> promotions) {
        final AtomicLong finalPrice = new AtomicLong(0);
        promotions.forEach(promotion -> {
            if (promotion.getOperator().equals(AbstractPromotion.PromotionOperator.ADD)) {
                final AdditionPromotion additionPromotion = (AdditionPromotion) promotion;
                finalPrice.getAndAdd(calculate(additionPromotion));
            } else {
                final PercentagePromotion percentagePromotion = (PercentagePromotion) promotion;
                finalPrice.getAndAdd(calculate(percentagePromotion));
            }
        });
        cart.getCartItems().stream().filter(item -> item.getQuantity() > 0).forEach(item -> finalPrice.addAndGet(item.getQuantity() * item.getPrice()));
        return finalPrice.get();
    }
    
    private long calculate(final AdditionPromotion promotion) {
        long finalPrice = 0;
        final long count = promotion.getSkuIds().stream().distinct().count();
        finalPrice += count == 1 ? calculateDiscountOnSingleCombination(promotion) : calculateDiscountOnMultipleCombination(promotion);
        return finalPrice;
    }
    
    private long calculate(final PercentagePromotion promotion) {
        final AtomicLong finalPrice = new AtomicLong();
        final Item matched = cart.getCartItems().stream().filter(item -> promotion.getSkuId().equals(item.getSkuId())).findFirst().orElse(null);
        Optional.ofNullable(matched).ifPresent(item -> {
            final int minus = (matched.getPrice() * promotion.getPercentageValue()) / 100;
            finalPrice.addAndGet((matched.getPrice() - minus) * matched.getQuantity());
            matched.setQuantity(0);
        });
        return finalPrice.get();
    }
    
    private long calculateDiscountOnSingleCombination(final AdditionPromotion promotion) {
        long finalPrice = 0;
        final Item item = cart.getCartItems()
            .stream()
            .filter(i -> i.getSkuId().equals(promotion.getSkuIds().get(0)))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item is not found in the cart."));
        final int discountQuantity = promotion.getSkuIds().size();
        final int discountPrice = item.getQuantity() / discountQuantity * promotion.getDiscountPrice();
        final int each = item.getQuantity() % discountQuantity * item.getPrice();
        item.setQuantity(0);
        finalPrice += discountPrice + each;
        return finalPrice;
    }
    
    private long calculateDiscountOnMultipleCombination(final AdditionPromotion promotion) {
        long finalPrice = 0;
        final Set<String> sortedIds = promotion.getSkuIds().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        final Map<String, List<Item>> matched = cart.getCartItems().stream()
            .filter(item -> sortedIds.contains(item.getSkuId()))
            .collect(Collectors.groupingBy(Item::getSkuId, Collectors.toList()));
        if (sortedIds.equals(matched.keySet())) {
            final Integer min = matched.values().stream().flatMap(Collection::stream).map(Item::getQuantity).min(Integer::compareTo).orElse(0);
            finalPrice += min * promotion.getDiscountPrice();
            cart.getCartItems().stream().filter(item -> sortedIds.contains(item.getSkuId())).forEach(item -> item.setQuantity(item.getQuantity() - 2));
        }
        return finalPrice;
    }
}
