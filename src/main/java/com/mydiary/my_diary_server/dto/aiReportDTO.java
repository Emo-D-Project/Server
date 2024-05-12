package com.mydiary.my_diary_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class aiReportDTO {
	public aiReportDTO() {
		// TODO Auto-generated constructor stub
	}
	private String positive;
	private String negative;
	private String mon = null;
	private String tue = null;
	private String wed = null;
	private String thu = null;
	private String fri = null;
	private String sat = null;
	private String sun = null;
	private String emotion;
}
