package com.mydiary.my_diary_server.test2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DiaryReportResponse {
	private final LocalDateTime LD;
	private Integer counts;
	private Integer[] emoCount;
}
