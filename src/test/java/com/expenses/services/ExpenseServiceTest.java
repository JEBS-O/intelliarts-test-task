package com.expenses.services;

import com.expenses.entities.Expense;
import com.expenses.entities.enums.Currency;
import com.expenses.repositories.ExpenseRepository;
import com.expenses.services.ExpenseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test-application.properties")
public class ExpenseServiceTest {
    @Autowired
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    @Test
    public void saveExpenseTest() {
        Expense expense = new Expense(Date.valueOf("2021-03-21"), 20.0, Currency.valueOf("EUR"), "Food");
        expenseService.save(expense);
        Mockito.verify(expenseRepository, Mockito.times(1)).save(expense);
    }

    @Test
    public void getAllExpensesTest() {
        expenseService.getAll();
        Mockito.verify(expenseRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getExpenseByIdTest() {
        expenseService.getById(1L);
        Mockito.verify(expenseRepository, Mockito.times(1)).getOne(1L);
    }

    @Test
    public void getExpenseByDateTest() {
        expenseService.getByDate(Date.valueOf("2021-03-21"));
        Mockito.verify(expenseRepository, Mockito.times(1)).findExpensesByDate(Date.valueOf("2021-03-21"));
    }

    @Test
    public void deleteExpenseTest() {
        Expense expense = new Expense(Date.valueOf("2021-03-21"), 20.0, Currency.valueOf("EUR"), "Food");
        expenseService.delete(expense);
        Mockito.verify(expenseRepository, Mockito.times(1)).delete(expense);
    }
}
