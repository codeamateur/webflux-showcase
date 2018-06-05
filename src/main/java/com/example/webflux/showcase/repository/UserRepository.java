package com.example.webflux.showcase.repository;

import com.example.webflux.showcase.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User,String>{

    Mono<User> findUserByUsername(String username);

    Mono<Long> deleteUserByUsername(String username);
}
