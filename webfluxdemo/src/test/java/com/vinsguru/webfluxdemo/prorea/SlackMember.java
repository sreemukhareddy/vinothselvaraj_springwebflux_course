package com.vinsguru.webfluxdemo.prorea;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlackMember {
	
	private String name;
	private Consumer<String> messageConsumer;
	
	public void receiveMessage(String message) {
		System.out.println("Received " + message);
	}
	
	public void says(String message) {
		this.messageConsumer.accept(message);
	}
	
	void setMessageConsumer(Consumer<String> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}

}
