package com.splitwise.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "members")
@Data
@RequiredArgsConstructor
public class Members {

    @Transient
    public static final String SEQUENCE_NAME = "members_sequence";
    @Id
    private Long  memberId;

    @NotBlank(message="title must not be blank")
    private String name;


}
