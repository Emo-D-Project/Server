package com.mydiary.my_diary_server.test2;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class aiReportResponse {
	String positiveEvent;
	String negativeEvent;
	String emotion;
	List<String> Summary;
}
