package com.inomera.promotionstest.service;

import com.inomera.promotionstest.bootstrap.InitialData;
import com.inomera.promotionstest.model.Promotion;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static javax.management.timer.Timer.ONE_MINUTE;

@AllArgsConstructor
@Component
public class PromotionService {

    private PromotionApplyService promotionApplyService;
    private SlackMessageService slackMessageService;

    @Scheduled(fixedDelay = 5 * ONE_MINUTE)
    public void applyPromotions() throws MalformedURLException {
        InitialData initialData = new InitialData();
        List<Promotion> resultList = new ArrayList<>();
        for (Promotion promotion : initialData.getPromotions()){
            resultList.add(promotionApplyService.applyPromotion(promotion));
        }

        String message = "";
        for (Promotion promotion : resultList){
            if (!promotion.isResult()){
                message += "Promosyon Çalışmamaktadır :" + promotion.getPromotionName() + "\n";
            }
        }

        if (!StringUtils.isEmpty(message)){
            sendMessageToInomera(message);
        }

    }


    private void sendMessageToInomera(String message) {
        slackMessageService.sendSlackMessage(
                "https://hooks.slack.com/services/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",
                "#wrongdeliverycost",
                "Promotion Test",
                message);
    }
}

// product
// quantity
// condition quantity
// pay quantity
// percent
// coupon code
//


// login eklenmeli

// coupon eklenmeli

// shipping fee ekllencek

// shipping testi yapılacak

// sepette 15 indirmi  Kodu: 600.15.01.0813

// promotion type

// slack etegrasyon

// spring boot yap local test ve canlı bilgilerini gir
