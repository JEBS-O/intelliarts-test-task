package com.expenses.services;

import com.expenses.entities.enums.Currency;
import com.expenses.utils.HttpUtil;
import com.expenses.utils.JsonUtil;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class RatesService {
    private final String ratesLink;

    public RatesService(@Value("${exchangerate.api.link}") String ratesLink) {
        this.ratesLink = ratesLink;
    }

    public Double convertRates(Currency from, Currency to, double amount) {
        try {
            String requestURL = String.format(Locale.US, "%s/convert?from=%s&to=%s&amount=%.2f", ratesLink, from.name(), to.name(), amount);
            String response = HttpUtil.get(requestURL);
            return (Double) JsonUtil.parse(response, "result");
        } catch(ParseException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}