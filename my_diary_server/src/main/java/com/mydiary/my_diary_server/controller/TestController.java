package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.service.SpeechRecongnition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<String> handleGetRequest(){
        return ResponseEntity.status(HttpStatus.OK).body("Good");
    }

    @PostMapping()
    public ResponseEntity<String> handleDefaultRequest(@RequestBody String name) {
        return ResponseEntity.status(HttpStatus.OK).body(name);
    }

    @PostMapping("/audioRecognition")
    public ResponseEntity<String> handleAudioRecognition(@RequestBody String audioUrl){
        return ResponseEntity.status(HttpStatus.OK).body(new SpeechRecongnition().run(audioUrl));
    }


}


