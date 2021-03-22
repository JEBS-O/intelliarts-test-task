package com.expenses.services;

import com.expenses.entities.enums.Currency;
import com.expenses.services.RatesService;
import com.expenses.utils.HttpUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test-application.properties")
public class RatesServiceTest {
    @Autowired
    private RatesService ratesService;
    @Value("${exchangerate.api.link}")
    private String link;

    @Test
    public void checkLink() {
        String response = HttpUtil.get(link);
        Assert.assertEquals("{\"details\":\"https://exchangerate.host\",\"motd\":{\"msg\":\"If you or your company use this project or like what we doing, please consider backing us so we can continue maintaining and evolving this project.\",\"url\":\"https://exchangerate.host/#/donate\"}}", response);
    }

    @Test
    public void checkService() {
        Double result = ratesService.convertRates(Currency.valueOf("USD"), Currency.valueOf("EUR"), 25.0);

        Assert.assertNotNull(result);
        Assert.assertTrue(result > 0);
    }
}
