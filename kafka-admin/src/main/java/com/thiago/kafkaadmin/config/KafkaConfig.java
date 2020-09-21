package com.thiago.kafkaadmin.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Bean
	public AdminClient adminClient() {	
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return AdminClient.create(configs);
	}
	
	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name("cars")
				.compact()
				.build();
	}
	
	@Bean
	public NewTopic topic2() {
		return TopicBuilder.name("cells")
				.compact()
				.build();
	}
	
}
