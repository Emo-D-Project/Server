package com.mydiary.my_diary_server.dto;

import java.time.LocalDateTime;

public class ReportResponse {
	LocalDateTime date;
	String mostEmotion;
	String leastEmotion;
	Integer[] emotionNums;
	String comment;
	Integer score;
}
