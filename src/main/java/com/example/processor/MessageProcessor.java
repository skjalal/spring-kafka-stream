package com.example.processor;

import com.example.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@Slf4j
@Service
public class MessageProcessor {

  public Flux<Message<Employee>> producer() {
    log.info("Sending data to kafka");
    return Flux.fromStream(
        Stream.of(
            buildPayload("101", "ABC", "IT", "SSE"),
            buildPayload("102", "PQR", "RE", "SE"),
            buildPayload("103", "XYZ", "IT", "SE")));
  }

  public void consumer(KStream<String, Employee> employees) {
    employees.foreach(this::verify);
  }

  private void verify(String key, Employee employee) {
    log.info("Reading data from kafka - ID: {}", employee.getId());
  }

  private Message<Employee> buildPayload(String id, String name, String department, String designation) {
    Employee employee = Employee.newBuilder()
            .setId(id)
            .setName(name)
            .setDepartment(department)
            .setDesignation(designation)
            .build();
    String key = String.format("key-%s", id);
    return MessageBuilder.withPayload(employee).setHeader(KafkaHeaders.MESSAGE_KEY, key).build();
  }
}
