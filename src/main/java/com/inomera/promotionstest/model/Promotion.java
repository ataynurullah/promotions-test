package com.inomera.promotionstest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Promotion {
    private String promotionName;
    private PromotionType promotionType;
    private String siteUrl;
    private String productId;
    private int productQty;
    private int productConditionQty;
    private int payQty;
    private double discountPercent;
    private String couponCode;
    private boolean result;
}
