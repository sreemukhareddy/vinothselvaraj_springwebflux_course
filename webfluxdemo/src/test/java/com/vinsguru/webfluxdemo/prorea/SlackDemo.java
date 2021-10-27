package com.vinsguru.webfluxdemo.prorea;

import com.vinsguru.webfluxdemo.Util;

public class SlackDemo {
	
	public static void main(String[] args) {
		SlackRoom slackRoom = new SlackRoom("reactor");
		
		SlackMember sam = SlackMember.builder()
									.name("sam")
									.build();
		
		SlackMember jake = SlackMember.builder()
				.name("jake")
				.build();
		
		SlackMember mike = SlackMember.builder()
				.name("mike")
				.build();
		
		
		slackRoom.joinRoom(sam);
		slackRoom.joinRoom(jake);
		
		sam.says("Hi All");
		Util.sleep(4);
		jake.says("Hey!");
		sam.says("I simply wanted to say hi...");
		Util.sleep(4);
		
		slackRoom.joinRoom(mike);
		mike.says("Hey Guys Glad to be here");
		
	}

}
