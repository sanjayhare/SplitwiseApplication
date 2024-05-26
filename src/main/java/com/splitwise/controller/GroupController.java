package com.splitwise.controller;

import com.splitwise.constant.TaskConstants;
import com.splitwise.dto.ResponseDto;
import com.splitwise.entity.Group;
import com.splitwise.service.GroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
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
    public ResponseEntity<List<Group>> getGroupList() {
        List<Group> groups = groupService.getGroupList();
        return ResponseEntity.status(HttpStatus.OK).body(groups);
    }

    @GetMapping("/getGroup")
    public ResponseEntity<Optional<Group>> getGroup(@RequestParam  @Pattern(regexp = "\\d+", message = "The field must contain only numeric values")
                                                        String id) {
        Optional<Group> group = groupService.getGroup(Long.valueOf(Integer.parseInt(id)));
        return ResponseEntity.status(HttpStatus.OK).body(group);
    }

    @DeleteMapping("/deleteGroup")
    public ResponseEntity<ResponseDto> deleteGroup(@RequestParam
                                                       @Pattern(regexp = "\\d+", message = "The field must contain only numeric values")
                                                   String id) {
        boolean isDeleted = groupService.deleteGroup(Long.valueOf(id));
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(TaskConstants.STATUS_200, TaskConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_DELETE));
        }
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity<ResponseDto> deleteMember(@RequestParam
                                                   @Pattern(regexp = "\\d+", message = "The field must contain only numeric values")
                                                   String gId , String mId) {
        boolean isDeleted = groupService.deleteMember(Long.valueOf(gId),Long.valueOf(mId));
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(TaskConstants.STATUS_200, TaskConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_DELETE));
        }
    }

    @PutMapping ("/updateGroup")
    public ResponseEntity<ResponseDto> updateGroup(@Valid @RequestBody Group group) {

        boolean isUpdated = groupService.updateGroup(group);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(TaskConstants.STATUS_200, TaskConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_DELETE));
        }
    }
}