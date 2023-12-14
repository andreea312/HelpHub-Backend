package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Achievement;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.interfaces.IAchievementService;
import com.donatii.donatiiapi.service.interfaces.ICauzaService;
import com.donatii.donatiiapi.service.interfaces.IUserService;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {
    private final IUserService userService;
    private final ICauzaService cauzaService;

    @Autowired
    public UserController(IUserService userService, ICauzaService cauzaService ) {
        this.userService = userService;
        this.cauzaService = cauzaService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User loginRequest) {
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getParola());
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User registerRequest) {
        try {
            User user = userService.register(registerRequest);
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok().body("User updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().body("User deleted!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/resources/{user_id}/{points}")
    public ResponseEntity<Object> updateResources(@PathVariable("user_id") Long user_id,
                                                  @PathVariable("points") Long points) {
        try {
            userService.updateResources(user_id, points);
            return ResponseEntity.ok().body("Resources updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
