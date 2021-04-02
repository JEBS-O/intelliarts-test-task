package com.expenses.controllers;

import com.expenses.entities.enums.Currency;
import com.expenses.entities.Expense;
import com.expenses.services.ExpenseService;
import com.expenses.dto.TotalDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public Map<String, List<Expense>> get() {
        return new TreeMap<>(expenseService.getAll().stream()
                .collect(Collectors.groupingBy(e -> e.getDate().toString())));
    }

    @PostMapping("/expenses")
    public ResponseEntity add(@RequestBody Expense expense) {
        expenseService.save(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/expenses")
    public ResponseEntity<List<Expense>> delete(@RequestParam("date") String date) {
        List<Expense> expensesForDelete = expenseService.getByDate(Date.valueOf(date));
        expensesForDelete.forEach(expenseService::delete);
        return ResponseEntity.ok(expensesForDelete);
    }

    @GetMapping("/total")
    public ResponseEntity<TotalDTO> getCurrency(@RequestParam("base") String base) {
        double total = expenseService.getTotal(Currency.valueOf(base));
        return ResponseEntity.ok(new TotalDTO(total, base));
    }
}