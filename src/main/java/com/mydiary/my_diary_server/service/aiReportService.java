package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.aiReport;
import com.mydiary.my_diary_server.dto.aiReportDTO;
import com.mydiary.my_diary_server.repository.aiReportRepository;

public class aiReportService {
	aiReportRepository rep;
	
	public void save(Long userid, aiReportDTO dto)
	{
		rep.save(new aiReport(userid, dto));
	}
}
