package com.mydiary.my_diary_server.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class aiReportResponse {
	String positiveEvent;
	String negativeEvent;
	String emotion;
	List<String> Summary;
}
