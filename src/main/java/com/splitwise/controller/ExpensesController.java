package com.splitwise.controller;

import com.splitwise.constant.TaskConstants;
import com.splitwise.dto.ResponseDto;

import com.splitwise.entity.Expenses;

import com.splitwise.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/expense", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ExpensesController {

    private ExpenseService expenseService;


    @PostMapping("/createExpense")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody Expenses expenses) {

        System.out.println("in ExpensesController ");

        expenseService.createExpense(expenses);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskConstants.STATUS_201, TaskConstants.MESSAGE_201));
    }

    @GetMapping("/getExpenseList")
    public ResponseEntity<List<Expenses>>getExpenseList() {
        List<Expenses> expenses = expenseService.getExpenseList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenses);
    }

    @GetMapping("/getExpense")
    public ResponseEntity<Optional<Expenses>> getExpense(@RequestParam String id) {
        Optional<Expenses> expenses = expenseService.getExpense(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenses);
    }
}
