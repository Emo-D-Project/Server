package com.mydiary.my_diary_server.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnalysisResponse {
	Integer nums;
	String mostWritten;
	LocalDateTime firstDate;
	LocalDate mostYearMonth;
	Integer mostNums;
	Long mostViewed;
	Integer mostViewedEmpathy;
	Integer mostViewedComments;
}
