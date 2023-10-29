package com.mydiary.my_diary_server.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean allowMsg;

    private boolean msgAlarm;

    private boolean empAlarm;

    private boolean commAlarm;

    private boolean actAlarm;


}
