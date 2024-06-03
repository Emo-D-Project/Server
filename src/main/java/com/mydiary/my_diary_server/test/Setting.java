package com.mydiary.my_diary_server.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Builder.Default
    private boolean allowMsg = false; // 기본값 설정

    @Builder.Default
    private boolean msgAlarm = false;

    @Builder.Default
    private boolean empAlarm = false;

    @Builder.Default
    private boolean commAlarm = false;

    @Builder.Default
    private boolean actAlarm = false;


}
