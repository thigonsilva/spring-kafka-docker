package com.thiago.kafkaadmin.topic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("topic")
@Slf4j
public class TopicController {
	
	@Autowired
	private AdminClient adminClient;
	
	@GetMapping("/list")
	public Map<String, TopicListing> listTopics() throws InterruptedException, ExecutionException {
		ListTopicsResult listTopics = adminClient.listTopics();
		Collection<TopicListing> collection = listTopics.listings().get();
		Stream<TopicListing> stream = collection.stream();
		
		KafkaFuture<Map<String,TopicListing>> namesToListings = listTopics.namesToListings();
		return namesToListings.get();
	}
	
	@GetMapping("/{topicName}/describe")
	public Map<String, Object> describeTopic() throws InterruptedException, ExecutionException {
		return null;
	}
	
	@PostMapping("/create")
	public void createTopic(@RequestBody Map<String, String> request) {
		if (null == request.get("topicName")) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Parameter topicName is required!");
		NewTopic newTopic = new NewTopic(request.get("topicName"), Optional.empty(), Optional.empty());
		adminClient.createTopics(Collections.singleton(newTopic));
	}
	
	@GetMapping("/describe")
	public Map<String, Object> describeKafka() throws InterruptedException, ExecutionException {
		Map<String, Object> resultMap = new HashMap<>();
		
		DescribeClusterResult describeCluster = adminClient.describeCluster();
		
		resultMap.put("clusterId", describeCluster.clusterId().get());
		resultMap.put("nodeController", describeCluster.controller().get());
		resultMap.put("nodes", describeCluster.nodes().get());
		resultMap.put("authorizedOperations", describeCluster.authorizedOperations().get());
		
		return resultMap;
	}
	
	@DeleteMapping("/{topicName}")
	public void destroyTopic(@PathVariable String topicName) {
		log.debug("Deleting topic: " + topicName);
		adminClient.deleteTopics(Collections.singleton(topicName));
	}

}
