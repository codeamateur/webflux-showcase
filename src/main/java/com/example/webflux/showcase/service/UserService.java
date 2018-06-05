package com.example.webflux.showcase.service;

import com.example.webflux.showcase.domain.User;
import com.example.webflux.showcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Mono<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public Mono<Long> deleteUserByUsername(String username){
        return userRepository.deleteUserByUsername(username);
    }

    public Flux<User> findAll(){
        return userRepository.findAll();
    }

    public Mono<User> save(User user){
     return userRepository.save(user).onErrorResume(e -> userRepository.findUserByUsername(user.getUsername()).flatMap(originUser ->{
         user.setId(originUser.getId());
         return userRepository.save(user);
     }));
    }
}
