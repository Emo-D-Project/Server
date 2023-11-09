package com.mydiary.my_diary_server.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

public enum Emotion {
    ANDGRY, 
    HAPPY,
    SAD;
    
	@JsonCreator
	public static Emotion from(String value)
	{
		for()
	}
}
