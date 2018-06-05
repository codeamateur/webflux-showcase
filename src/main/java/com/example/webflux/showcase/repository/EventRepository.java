package com.example.webflux.showcase.repository;

import com.example.webflux.showcase.domain.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface EventRepository extends ReactiveMongoRepository<Event,Long> {

    @Tailable
    Flux<Event> findBy();
}
