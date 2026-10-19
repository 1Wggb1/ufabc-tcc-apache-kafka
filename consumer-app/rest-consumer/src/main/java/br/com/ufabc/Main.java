package br.com.ufabc;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@SpringBootApplication
class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .run();
    }

    public record EventRequest(String sku){
    }

    public record EventResponse(String status){
    }

    @RestController
    @RequestMapping("/consumes")
    public static class ConsumerController {
        @PostMapping
        public ResponseEntity<EventResponse> consumerEvent(@RequestBody EventRequest event) throws InterruptedException {
            IO.println("[%s] Starting event %s consume".formatted(LocalDateTime.now(), event));
            Thread.sleep(2_000);
            IO.println("[%s] Event %s processed".formatted(LocalDateTime.now(), event));
            return ResponseEntity.ok(new EventResponse("Event Processed"));
        }
    }
}