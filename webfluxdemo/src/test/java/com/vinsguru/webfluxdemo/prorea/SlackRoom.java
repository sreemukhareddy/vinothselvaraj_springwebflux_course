package com.vinsguru.webfluxdemo.prorea;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackRoom {
	
	private String name;
	private Sinks.Many<SlackMessage> sink;
	private Flux<SlackMessage> flux;
	
	public SlackRoom(String name) {
		this.name = name;
		this.sink = Sinks.many().replay().all();
		this.flux = this.sink.asFlux();
	}
	
	public void joinRoom(SlackMember slackMember) {
		System.out.println(slackMember.getName() + " joined " + this.name);
		this.getMessage(slackMember);
		slackMember.setMessageConsumer(msg -> this.postMessage(msg, slackMember));
	}
	
	private void postMessage(String msg, SlackMember slackMember) {
		SlackMessage message = SlackMessage
								.builder()
								.senderName(slackMember.getName())
								.message(msg)
								.build();
		this.sink.tryEmitNext(message);
	}
	
	private void getMessage(SlackMember slackMember) {
		this.flux
			.filter(sm -> !sm.getSenderName().equalsIgnoreCase(slackMember.getName()))
			.doOnNext(s -> s.setReceiverName(slackMember.getName()))
		    .map(s -> s.toString())
			.subscribe(slackMember::receiveMessage);
	}

}
