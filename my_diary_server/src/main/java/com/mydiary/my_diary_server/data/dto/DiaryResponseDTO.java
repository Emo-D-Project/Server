package com.mydiary.my_diary_server.data.dto;

import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate date;
}
