package com.expenses.entities;

import com.expenses.entities.enums.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotNull
    @Positive
    private Double amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @NotNull
    private String product;

    public Expense() {}

    public Expense(Date date, Double amount, Currency currency, String product) {
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
