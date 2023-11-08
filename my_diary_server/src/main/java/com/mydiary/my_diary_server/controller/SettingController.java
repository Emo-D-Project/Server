package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.Setting;
import com.mydiary.my_diary_server.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    private final SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }
    @Operation(summary = "설정 정보 불러오기")
    @GetMapping()
    public ResponseEntity<Setting> getSettingById(Principal principal) {
        Setting setting = settingService.findSettingByUserId(Long.parseLong(principal.getName()));
        if (setting != null) {
            return ResponseEntity.ok(setting);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "설정 정보 저장")
    @PostMapping("save")
    public ResponseEntity<Setting> saveOrUpdate(@RequestBody Setting setting, Principal principal) {
        Setting createdSetting = settingService.createOrUpdate(setting, Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSetting);
    }




}

