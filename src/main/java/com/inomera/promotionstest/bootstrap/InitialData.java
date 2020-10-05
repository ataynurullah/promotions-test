package com.inomera.promotionstest.bootstrap;


import com.inomera.promotionstest.model.Promotion;
import com.inomera.promotionstest.model.PromotionType;

import java.util.ArrayList;
import java.util.List;

public class InitialData {

    public List<Promotion> getPromotions() {
        List<Promotion> promotions = new ArrayList<>();


//        Promotion europe_1_1 = new Promotion();
//        europe_1_1.setPromotionName("europe_1_1");
//        europe_1_1.setPromotionType(PromotionType.GIFT);
//        europe_1_1.setSiteUrl("https://www.karaca.com/de/");
//        europe_1_1.setProductId("153.03.08.0008");
//        europe_1_1.setProductQty(2);
//        europe_1_1.setPayQty(1);
//        promotions.add(europe_1_1);
//
//
//        Promotion ems_3_2 = new Promotion();
//        ems_3_2.setPromotionName("ems_3_2");
//        ems_3_2.setPromotionType(PromotionType.GIFT);
//        ems_3_2.setSiteUrl("https://www.emsan.com.tr/");
//        ems_3_2.setProductId("ems-600.15.01.0084");
//        ems_3_2.setProductQty(3);
//        ems_3_2.setPayQty(2);
//        promotions.add(ems_3_2);

//        Promotion europe_coupon_percent_20 = new Promotion();
//        europe_coupon_percent_20.setPromotionName("europe_coupon_percent_20");
//        europe_coupon_percent_20.setPromotionType(PromotionType.COUPON);
//        europe_coupon_percent_20.setSiteUrl("https://www.karaca.com/de/");
//        europe_coupon_percent_20.setProductId("153.13.01.1049");
//        europe_coupon_percent_20.setProductQty(3);
//        europe_coupon_percent_20.setCouponCode("europe20");
//        promotions.add(europe_coupon_percent_20);

        Promotion europeShippingGreater = new Promotion();
        europeShippingGreater.setPromotionType(PromotionType.SHIPPING);
        europeShippingGreater.setPromotionName("Shipping");
        europeShippingGreater.setSiteUrl("https://www.karaca.com/de/");
        europeShippingGreater.setProductId("153.03.07.8266");
        europeShippingGreater.setProductQty(3);
        promotions.add(europeShippingGreater);

        Promotion europeShippingLess = new Promotion();
        europeShippingLess.setPromotionType(PromotionType.SHIPPING);
        europeShippingLess.setPromotionName("Shipping");
        europeShippingLess.setSiteUrl("https://www.karaca.com/de/");
        europeShippingLess.setProductId("153.03.07.8266");
        europeShippingLess.setProductQty(1);
        promotions.add(europeShippingLess);

//        Promotion emsShippingGreater = new Promotion();
//        emsShippingGreater.setPromotionType(PromotionType.SHIPPING);
//        emsShippingGreater.setPromotionName("Shipping");
//        emsShippingGreater.setSiteUrl("https://www.emsan.com.tr/");
//        emsShippingGreater.setProductId("ems-600.15.01.1029");
//        emsShippingGreater.setProductQty(2);
//        promotions.add(emsShippingGreater);
//
//        Promotion emsShippingLess = new Promotion();
//        emsShippingLess.setPromotionType(PromotionType.SHIPPING);
//        emsShippingLess.setPromotionName("Shipping");
//        emsShippingLess.setSiteUrl("https://www.emsan.com.tr/");
//        emsShippingLess.setProductId("ems-600.15.01.0807");
//        emsShippingLess.setProductQty(1);
//        promotions.add(emsShippingLess);
//

        return promotions;
    }
}



