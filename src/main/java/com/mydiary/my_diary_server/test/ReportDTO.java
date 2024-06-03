package com.mydiary.my_diary_server.test;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class ReportDTO {
	YearMonth date;
	Integer mostEmotion;
	Integer leastEmotion;
	Integer[] emotionNums;
	String comment;
	Integer score;
}
