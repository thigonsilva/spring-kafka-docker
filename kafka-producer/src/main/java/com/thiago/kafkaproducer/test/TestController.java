package com.thiago.kafkaproducer.test;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("test")
public class TestController {
	
	@Autowired
	private KafkaTemplate<String, String> template;
	
	@GetMapping
	public String getTest() {
		return "Working!";
	}
	
	@PostMapping
	public void sendMessage(@RequestBody Map<String, String> request) {
		System.out.println("request: " + request);

		Optional<String> car = Optional.of(request.get("car"));
		Optional<String> cell = Optional.of(request.get("cell"));
		
		final String key = UUID.randomUUID().toString();
		
		if (car.isPresent()) template.send("cars", key, car.get());
		if (cell.isPresent()) template.send("cells", key, cell.get());
	}

}
