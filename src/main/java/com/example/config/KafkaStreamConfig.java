package com.example.config;

import com.example.Employee;
import com.example.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class KafkaStreamConfig {

  private final MessageProcessor messageProcessor;

//  @Bean
  public Supplier<Flux<Message<Employee>>> producer() {
    return messageProcessor::producer;
  }

  @Bean
  public Consumer<KStream<String, Employee>> consumer() {
    return messageProcessor::consumer;
  }
}
