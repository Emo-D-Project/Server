package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.Setting;
import com.mydiary.my_diary_server.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    private final SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setting> getSettingById(@PathVariable Long id) {
        Setting setting = settingService.getSettingById(id);
        if (setting != null) {
            return ResponseEntity.ok(setting);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Setting> createSetting(@RequestBody Setting setting) {
        Setting createdSetting = settingService.createSetting(setting);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSetting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setting> updateSetting(@PathVariable Long id, @RequestBody Setting updatedSetting) {
        Setting setting = settingService.getSettingById(id);
        if (setting != null) {
            Setting updated = settingService.updateSetting(id, updatedSetting);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        settingService.deleteSetting(id);
        return ResponseEntity.noContent().build();
    }
}

