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

  public Flux<Employee> producer() {
    log.info("Sending data to kafka");
    return Flux.fromStream(
        Stream.of(
            buildPayload("101", "ABC", "IT", "SSE"),
            buildPayload("102", "PQR", "RE", "SE"),
            buildPayload("103", "XYZ", "IT", "SE")));
  }

  public Mono<Void> consumer(Flux<Employee> employees) {
    return employees.doOnNext(this::verify).then();
  }

  private void verify(Employee employee) {
    log.info("Reading data from kafka - ID: {}", employee.getId());
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
