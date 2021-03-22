package com.expenses.services;

import com.expenses.entities.enums.Currency;
import com.expenses.entities.Expense;
import com.expenses.repositories.ExpenseRepository;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ExpenseService implements SimpleService<Expense> {
    private final ExpenseRepository expenseRepository;
    private final RatesService ratesService;

    public ExpenseService(ExpenseRepository expenseRepository, RatesService ratesService) {
        this.expenseRepository = expenseRepository;
        this.ratesService = ratesService;
    }

    @Override
    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getById(Long id) {
        return expenseRepository.getOne(id);
    }

    public List<Expense> getByDate(Date date) {
        return expenseRepository.findExpensesByDate(date);
    }

    public Double getTotal(Currency currency) {
        return expenseRepository.findAll().stream()
                .mapToDouble(e -> ratesService.convertRates(e.getCurrency(), currency, e.getAmount()))
                .sum();
    }

    @Override
    public void save(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public Expense delete(@NotNull Expense expense) {
        expenseRepository.delete(expense);
        return expense;
    }
}