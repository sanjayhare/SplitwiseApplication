package com.splitwise.controller;

import com.splitwise.dto.MembersDto;
import com.splitwise.entity.Group;
import com.splitwise.service.GroupService;
import com.splitwise.service.MembersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MemberController {

    private MembersService membersService;

    @GetMapping("/getMemberExpList")
    public ResponseEntity<List<MembersDto>> getGroupList() {
        List<MembersDto> membersDto = membersService.getMembersExpensesList();
        return ResponseEntity.status(HttpStatus.OK).body(membersDto);
    }
}
