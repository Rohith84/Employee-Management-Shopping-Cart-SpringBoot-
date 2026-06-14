package com.employee.management.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.dto.UserRequest;
import com.employee.management.dto.UserResponse;
import com.employee.management.model.User;
import com.employee.management.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User s){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.addUser(s));
}
    // @PostMapping
    // public ResponseEntity<String> addStudent(@RequestBody Student s){
    //     return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(s));
    // }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        //return studentService.getStudent(id);
        //return studentService.getStudent(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
        UserResponse response=userService.getUser(id);
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest s) {

        UserResponse user = userService.updateUser(id, s);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

//     @PostMapping("/students")
//     public String addStudent(@RequestBody Student s){
//         students.add(s);
//         return "Students Added Successfully";
//     }
}
