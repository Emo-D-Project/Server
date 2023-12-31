package com.mydiary.my_diary_server.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {
	
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	
	public void save(Long id, SseEmitter emitter)
	{
		emitters.put(id, emitter);
	}
	
	public void deleteById(Long id)
	{
		emitters.remove(id);
	}
	
	public SseEmitter get(Long id)
	{
		return emitters.get(id);
	}
}
