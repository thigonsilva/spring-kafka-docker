package com.thiago.kafkaconsumer.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestListener {
	
	@KafkaListener(topics = "cars")
	public void listenCars(ConsumerRecord<String, String> message) throws Exception {
		log.info("Incoming message: " + message.value());
		log.info("message key: " + message.key());
		log.info("Incoming message: " + message.headers());
	}
	
	@KafkaListener(topics = "cells")
	public void listenCells(ConsumerRecord<String, String> message) throws Exception {
		log.info("Incoming message: " + message.value());
		log.info("message key: " + message.key());
		log.info("Incoming message: " + message.headers());
	}
	
}
