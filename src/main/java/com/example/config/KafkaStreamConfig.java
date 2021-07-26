package com.example.config;

import com.example.Employee;
import com.example.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class KafkaStreamConfig {

  private final MessageProcessor messageProcessor;

  @Bean
  public Supplier<Flux<Employee>> producer() {
    return messageProcessor::producer;
  }

  @Bean
  public Function<Flux<Employee>, Mono<Void>> consumer() {
    return messageProcessor::consumer;
  }
}
