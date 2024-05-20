package com.splitwise.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Indexed;

import java.util.ArrayList;
import java.util.List;

@Document(value = "group")
@Data
@RequiredArgsConstructor
public class Group {

    @Transient
    public static final String SEQUENCE_NAME = "group_sequence";
    @Id
    private Long groupId;

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "description must not be blank")
    private String description;

    @NotBlank(message = "currency must not be blank")
    private String currency;

    @NotBlank(message = "catogory must not be blank")
    private String category;

   // @NotBlank(message = "members must not be blank")
    private List<Members> members;

}


