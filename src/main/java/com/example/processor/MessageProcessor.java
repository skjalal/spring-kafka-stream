package com.example.processor;

import com.example.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Slf4j
@Service
public class MessageProcessor {

  public Flux<String> producer() {
    log.info("Sending data to kafka");
    return Flux.fromStream(Stream.of("101", "102", "103", "104"));
  }

  public Mono<Void> consumer(Flux<String> employees) {
    return employees.doOnNext(this::verify).then();
  }

  private void verify(String data) {
    log.info("Reading data from kafka - ID: {}", data);
  }

  private Employee buildPayload(String id, String name, String department, String designation) {
    return Employee.newBuilder()
        .setId(id)
        .setName(name)
        .setDepartment(department)
        .setDesignation(designation)
        .build();
  }
}
