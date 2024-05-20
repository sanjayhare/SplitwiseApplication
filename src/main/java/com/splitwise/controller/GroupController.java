package com.splitwise.controller;

import com.splitwise.constant.TaskConstants;
import com.splitwise.dto.ResponseDto;
import com.splitwise.entity.Group;
import com.splitwise.service.GroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class GroupController {

    private GroupService groupService;

    @PostMapping("/createGroup")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody Group group) {
        System.out.println("in GroupController ");
        groupService.createGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskConstants.STATUS_201, TaskConstants.MESSAGE_201));
    }
    @GetMapping("/getGroupList")
    public ResponseEntity<List<Group>>getGroupList() {
        List<Group> groups = groupService.getGroupList();
        return ResponseEntity.status(HttpStatus.OK).body(groups);
    }
    @GetMapping("/getGroup")
    public ResponseEntity<Optional<Group>> getGroup(@RequestParam String id ) {
        Optional<Group> group = groupService.getGroup(Long.valueOf(Integer.parseInt(id)));
        return ResponseEntity.status(HttpStatus.OK).body(group);
    }
}