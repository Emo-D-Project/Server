package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.service.AudioRecongnitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/audio")
public class AudioRecognitionController {

    @Autowired
    AudioRecongnitionService audioRecongnitionService;

    @GetMapping
    public ResponseEntity<Void> test() throws IOException, InterruptedException {

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> handleFileUpload(@RequestPart(required=false)  MultipartFile audioFile) throws IOException, InterruptedException {
        if (audioFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Audio file is empty");
        }

        String msg = audioRecongnitionService.PostTranscribeSample(audioFile);

        return ResponseEntity.ok(msg);
    }

}
