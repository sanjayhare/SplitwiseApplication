package com.splitwise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "members")
@Data
@RequiredArgsConstructor
public class MembersDto {


    private Long memberId;

    private Long groupId;

    private String memberName;

    private BigDecimal amount;
}
