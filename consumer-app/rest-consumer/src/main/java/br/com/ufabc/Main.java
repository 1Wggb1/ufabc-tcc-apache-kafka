package br.com.ufabc;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
            IO.println("Starting event consume %s".formatted(event));
            Thread.sleep(3_000);
            IO.println("Event processed %s".formatted(event));
            return ResponseEntity.ok(new EventResponse("Event Processed"));
        }
    }
}