package com.example.webflux.showcase;

import com.example.webflux.showcase.domain.Event;
import com.example.webflux.showcase.domain.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebClientTest {

    @Test
    public void testHello() throws InterruptedException {
        WebClient webClient =   WebClient.create("http://localhost:8080");
        Mono<String> resp = webClient.get().uri("/hello").retrieve().bodyToMono(String.class);
        resp.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void testUser(){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/user").accept(MediaType.APPLICATION_STREAM_JSON).exchange().flatMapMany(response ->response.bodyToFlux(User.class))
                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    public void testTimes(){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/times").accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(String.class).log().take(10).blockLast();
    }

    @Test
    public void testEvent(){
        Flux<Event> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(l -> new Event(System.currentTimeMillis(), "message-" + l)).take(5); // 1
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient
                .post().uri("/events")
                .contentType(MediaType.APPLICATION_STREAM_JSON) // 2
                .body(eventFlux, Event.class) // 3
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }



}
