package com.mydiary.my_diary_server.test2;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GptService {
	String getAssistantMsg(String userMsg) throws JsonProcessingException;
}
