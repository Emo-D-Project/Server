package com.mydiary.my_diary_server.service;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GptService {
	String getAssistantMsg(String userMsg) throws JsonProcessingException;
}
