package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
	private final EmitterRepository emitterRepository;
	
	public SseEmitter subscribe(Long userId)
	{
		SseEmitter emitter = createEmitter(userId);
		
		sendToClient(userId, "EventScream Created. " + userId);
		return emitter;
	}
	
	public void notify(Long userId, Object event)
	{
		sendToClient(userId, event);
	}
	
	public void sendToClient(Long id, Object Data)
	{
		SseEmitter emitter = emitterRepository.get(id);
		if(emitter != null)
		{
			try {
				emitter.send(SseEmitter.event().id(String.valueOf(id)).name("sse").data(Data));
			}catch(IOException exp)
			{
				emitterRepository.deleteById(id);
				emitter.completeWithError(exp);
			}
			
		}
	}

	private SseEmitter createEmitter(Long id)
	{
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitterRepository.save(id, emitter);
		
		emitter.onCompletion(() -> emitterRepository.deleteById(id));
		emitter.onTimeout(() -> emitterRepository.deleteById(id));
		return emitter;
	}
}
