package com.mydiary.my_diary_server.test2;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class AnalysisResponse {
	Integer nums;
	String mostWritten;
	Double[] emotions;
	LocalDateTime firstDate;
	LocalDate mostYearMonth;
	Integer mostNums;
	Long mostViewed;
	Integer mostViewedEmpathy;
	Integer mostViewedComments;
}
