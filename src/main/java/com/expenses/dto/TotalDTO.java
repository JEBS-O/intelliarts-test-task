package com.expenses.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TotalDTO {
    private Double total;
    private String currency;

    public TotalDTO(Double total, String currency) {
        BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
        this.currency = currency;
        this.total = bd.doubleValue();
    }

    public Double getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }
}
