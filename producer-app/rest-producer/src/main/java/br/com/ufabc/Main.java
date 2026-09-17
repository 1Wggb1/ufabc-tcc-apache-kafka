package br.com.ufabc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.time.Duration;
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
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(Duration.ofSeconds(3));
            factory.setReadTimeout(Duration.ofSeconds(10));
            this.restClient = RestClient.builder()
                    .baseUrl(consumerEndpointUrl)
                    .requestFactory(factory)
                    .build();
            this.requestNumber = new AtomicInteger();
        }

        @PostMapping
        public ResponseEntity<String> producesEvents() throws InterruptedException {
            IO.println("Starting event producer. Generating event %s".formatted(requestNumber.addAndGet(1)));
            EventRequest eventRequest = new EventRequest("" + requestNumber.get());
            try {
                ResponseEntity<Void> entity = restClient.post()
                        .body(eventRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toBodilessEntity();
                HttpStatusCode statusCode = entity.getStatusCode();
                if(statusCode.isError()){
                    throw new RuntimeException("Error status code %s from event %s".formatted(statusCode, eventRequest));
                }
                IO.println("Event %s produced successfully".formatted(eventRequest));
            } catch (Exception e){
                IO.println("Exception %s in event %s".formatted(e.getMessage(), eventRequest));
                return ResponseEntity.status(HttpStatusCode.valueOf(504)).build();
            }
            return ResponseEntity.ok("produced");
        }
    }
}