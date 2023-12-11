package com.mydiary.my_diary_server.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DiaryReportResponse {
	private final LocalDateTime LD;
	private Integer counts;
	private Integer[] emoCount;
}
