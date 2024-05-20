package com.splitwise.entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Contributers {

   // @NotBlank(message = "cId must not be blank")
    private Long contId;
   // @NotBlank(message = "proportion must not be blank")
    private Double proportion;

    private Double amount;

}
