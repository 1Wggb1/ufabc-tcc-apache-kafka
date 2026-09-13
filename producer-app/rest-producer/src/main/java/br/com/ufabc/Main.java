package br.com.ufabc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .run();
    }

    public record EventRequest(String sku){
    }

    @RestController
    @RequestMapping("/produces")
    public static class ProducerController {
        private final RestClient restClient;
        private final AtomicInteger requestNumber;

        public ProducerController(@Value(value = "${consumer.endpoint.url}") String consumerEndpointUrl) {
            this.restClient = RestClient.create(consumerEndpointUrl);
            this.requestNumber = new AtomicInteger();
        }

        @PostMapping
        public ResponseEntity<Void> producesEvents() throws InterruptedException {
            IO.println("Starting event producer. Generating event %s".formatted(requestNumber.addAndGet(1)));
            EventRequest eventRequest = new EventRequest("" + requestNumber.get());
            IO.println("Sending event to consumer %s".formatted(eventRequest));
            ResponseEntity<Void> entity = restClient.post()
                    .body(eventRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity();
            HttpStatusCode statusCode = entity.getStatusCode();
            IO.println("Event response status code %s".formatted(statusCode));
            return ResponseEntity.ok(null);
        }
    }
}