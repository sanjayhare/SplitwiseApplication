package com.splitwise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Document(value = "expenses")
@Data
@RequiredArgsConstructor
public class Expenses {

    @Transient
    public static final String SEQUENCE_NAME = "expenses_sequence";
    @Id
    private Long expId;

    //@NotBlank(message = "groupId must not be blank")
    private Long groupId;

    @NotBlank(message = "title must not be blank")
    private String title;

   // @NotBlank(message = "Amount must not be blank")
    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

   // @NotBlank(message = "paidBy must not be blank")
    private Long paidBy;

    @NotBlank(message = "splitType must not be blank")
    private String splitType;

    private List<Contributers> contributers;

}
