package com.mydiary.my_diary_server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.mydiary.my_diary_server.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SSEController {
	private final NotificationService notificationService;
	
	@GetMapping(value="/sub/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(@PathVariable Long id)
	{
		return notificationService.subscribe(id);
	}
	
	@PostMapping("/send-data/{id}")
	public void sendData(@PathVariable Long id)
	{
		notificationService.notify(id, "data");
	}
}
