package com.example.webflux.showcase.controller;

import com.example.webflux.showcase.domain.User;
import com.example.webflux.showcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public Mono<User> save(User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return this.userService.deleteUserByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return this.userService.findUserByUsername(username);
    }

    @GetMapping(value="",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findAll() {
        return this.userService.findAll().delayElements(Duration.ofSeconds(3));
    }
}
