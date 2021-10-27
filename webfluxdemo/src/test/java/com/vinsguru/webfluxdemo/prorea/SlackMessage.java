package com.vinsguru.webfluxdemo.prorea;

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
public class SlackMessage {
	
	private String senderName;
	private String receiverName;
	private String message;

}
