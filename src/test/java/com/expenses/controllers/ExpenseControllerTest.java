package com.expenses.controllers;

import com.expenses.entities.Expense;
import com.expenses.entities.enums.Currency;
import com.expenses.repositories.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test-application.properties")
public class ExpenseControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ExpenseRepository expenseRepository;

    @After
    public void deleteAllFromDB() {
        expenseRepository.deleteAll();
    }

    @Test
    public void postRequestAddExpenseWithCorrectValues() throws Exception {
        Expense expense = new Expense(Date.valueOf("2021-03-20"), 10.0, Currency.valueOf("EUR"), "Food");

        mockMvc.perform(
                post("/expenses")
                        .content(objectMapper.writeValueAsString(expense))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void postRequestAddExpenseWithNegativeAmount() throws Exception {
        Expense expense = new Expense(Date.valueOf("2021-03-20"), -10.0, Currency.valueOf("EUR"), "Food");
        mockMvc.perform(
                post("/expenses")
                        .content(objectMapper.writeValueAsString(expense))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void postRequestAddExpenseWithNotCorrectDate() throws Exception {
        Expense expense = new Expense(Date.valueOf("2021-032-270asd"), 10.0, Currency.valueOf("EUR"), "Food");
        mockMvc.perform(
                post("/expenses")
                        .content(objectMapper.writeValueAsString(expense))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postRequestAddExpenseWithNullValue() throws Exception {
        Expense expense = new Expense(null, 10.0, Currency.valueOf("EUR"), "Food");
        mockMvc.perform(
                post("/expenses")
                        .content(objectMapper.writeValueAsString(expense))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRequestGetAll() throws Exception {
        expenseRepository.save(new Expense(Date.valueOf("2021-03-20"), 10.0, Currency.valueOf("EUR"), "Bananas"));
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 25.2, Currency.valueOf("USD"), "Milk"));
        expenseRepository.save(new Expense(Date.valueOf("2021-03-21"), 6.71, Currency.valueOf("USD"), "Bread"));
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 3.25, Currency.valueOf("EUR"), "Coffee"));

        mockMvc.perform(
                get("/expenses")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.2021-04-25.*", hasSize(2)))
                .andExpect(jsonPath("$.2021-03-21.*", hasSize(1)))
                .andExpect(jsonPath("$.2021-03-20.*", hasSize(1)))
                .andExpect(jsonPath("$.2021-04-25.[0].product").value("Milk"))
                .andExpect(jsonPath("$.2021-04-25.[1].product").value("Coffee"))
                .andExpect(jsonPath("$.2021-03-21.[0].product").value("Bread"))
                .andExpect(jsonPath("$.2021-03-20.[0].product").value("Bananas"));
    }

    @Test
    public void deleteRequestWithExistDate() throws Exception {
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 25.2, Currency.valueOf("USD"), "Milk"));
        expenseRepository.save(new Expense(Date.valueOf("2021-03-21"), 6.71, Currency.valueOf("USD"), "Bread"));
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 3.25, Currency.valueOf("EUR"), "Coffee"));

        mockMvc.perform(
                    delete("/expenses")
                        .param("date","2021-04-25")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].date").value("2021-04-25"))
                .andExpect(jsonPath("$[0].product").value("Milk"))
                .andExpect(jsonPath("$[0].currency").value("USD"))
                .andExpect(jsonPath("$[0].amount").value(25.2))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].date").value("2021-04-25"))
                .andExpect(jsonPath("$[1].product").value("Coffee"))
                .andExpect(jsonPath("$[1].currency").value("EUR"))
                .andExpect(jsonPath("$[1].amount").value(3.25));
    }

    @Test
    public void deleteRequestWithNotExistDate() throws Exception {
        expenseRepository.save(new Expense(Date.valueOf("2021-04-24"), 230.2, Currency.valueOf("USD"), "Phone"));

        mockMvc.perform(
                delete("/expenses")
                        .param("date","2021-04-25")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteRequestWithNotCorrectDate() throws Exception {
        expenseRepository.save(new Expense(Date.valueOf("2021-03-21"), 330.0, Currency.valueOf("USD"), "TV"));

        mockMvc.perform(
                delete("/expenses")
                        .param("date","21.03.2021")
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRequestTotalWithCorrectValues() throws Exception {
        expenseRepository.save(new Expense(Date.valueOf("2021-03-20"), 10.0, Currency.valueOf("EUR"), "Bananas"));
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 25.2, Currency.valueOf("USD"), "Milk"));
        expenseRepository.save(new Expense(Date.valueOf("2021-03-21"), 6.71, Currency.valueOf("USD"), "Bread"));
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 3.25, Currency.valueOf("EUR"), "Coffee"));
        String currency = "UAH";

        mockMvc.perform(
                get("/total")
                    .param("base", currency)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.total").isNumber())
                .andExpect(jsonPath("$.currency").value(currency));
    }

    @Test
    public void getRequestTotalWithNotCorrectCurrency() throws Exception {
        expenseRepository.save(new Expense(Date.valueOf("2021-03-20"), 10.0, Currency.valueOf("EUR"), "Bananas"));
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 25.2, Currency.valueOf("USD"), "Milk"));
        expenseRepository.save(new Expense(Date.valueOf("2021-03-21"), 6.71, Currency.valueOf("USD"), "Bread"));
        expenseRepository.save(new Expense(Date.valueOf("2021-04-25"), 3.25, Currency.valueOf("EUR"), "Coffee"));
        String currency = "Not correct currency example";

        mockMvc.perform(
                get("/total")
                        .param("base", currency)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}