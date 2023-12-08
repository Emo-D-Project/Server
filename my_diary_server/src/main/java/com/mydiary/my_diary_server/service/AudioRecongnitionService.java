package com.mydiary.my_diary_server.service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AudioRecongnitionService {

    public String PostTranscribeSample(MultipartFile audioFile) throws IOException, InterruptedException {
        String accessToken = GetAccessToken();

        // MultipartFile을 File로 변환
        File convertedFile = convertMultipartFileToFile(audioFile);

        // API 엔드포인트 및 HTTP 연결 설정
        URL url = new URL("https://openapi.vito.ai/v1/transcribe");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
        httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=authsample");
        httpConn.setDoOutput(true);

        try (DataOutputStream outputStream = new DataOutputStream(httpConn.getOutputStream())) {
            // 파일 전송 부분 시작
            outputStream.writeBytes("--authsample\r\n");
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + convertedFile.getName() + "\"\r\n");
            outputStream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(convertedFile.getName()) + "\r\n");
            outputStream.writeBytes("Content-Transfer-Encoding: binary" + "\r\n");
            outputStream.writeBytes("\r\n");

            // 파일 내용 전송
            try (FileInputStream in = new FileInputStream(convertedFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // 나머지 폼 데이터 전송
            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("--authsample\r\n");
            outputStream.writeBytes("Content-Disposition: form-data; name=\"config\"\r\n");
            outputStream.writeBytes("Content-Type: application/json\r\n");
            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("{}");
            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("--authsample\r\n");
        }

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        s.close();
        System.out.println("파일 분석 바디" + response);

        Gson gson = new Gson();
        IdResponse idResponse = gson.fromJson(response, IdResponse.class);

        // 응답에서 id 값을 가져옴
        String id = idResponse.getId();

        System.out.println("ID: " + id);
        Thread.sleep((int) (convertedFile.length() / 1024) * 50L); // 5초 동안 코드를 지연

        return GetTranscribeSample(id,accessToken);

    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // MultipartFile을 File로 변환하는 부분
        File file = File.createTempFile("temp", null);
        multipartFile.transferTo(file);
        return file;
    }

    public String GetAccessToken() throws IOException {
        String CLIENT_ID = "XoWxCBzaDz0tsQ7WyXHH";
        String CLIENT_SECRET = "i0VCNYdITLcERiAqZbzmjICf1yCqrvWyamFbzZYS";

        URL url = new URL("https://openapi.vito.ai/v1/authenticate");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpConn.setDoOutput(true);

        String data = "client_id="+ CLIENT_ID +"&client_secret=" + CLIENT_SECRET;

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = httpConn.getOutputStream();
        stream.write(out);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        s.close();
        System.out.println(response);

        Gson gson = new Gson();
        AccessTokenResponse accessTokenResponse = gson.fromJson(response, AccessTokenResponse.class);

        // 응답에서 access_token 값을 가져옴
        String accessToken = accessTokenResponse.getAccessToken();

        System.out.println("Access Token: " + accessToken);

        return accessToken;
    }

    public String GetTranscribeSample(String id, String accessToken) throws IOException {
        URL url = new URL("https://openapi.vito.ai/v1/transcribe/"+id);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        s.close();
        System.out.println(response);

        // JSON 파싱
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

       
        try {
            // utterances 배열이 비어있는지 확인
            Utterance[] utterances = apiResponse.getResults().getUtterances();
            if (utterances != null && utterances.length > 0) {
                // 첫 번째 요소에서 msg 값을 출력
                String msg = utterances[0].getMsg();
                System.out.println("msg: " + msg);
                return msg;
            } else {
                // utterances 배열이 비어있는 경우 또는 msg가 없는 경우에 대한 처리
                System.out.println("No utterances or msg found in the response.");
                return "";
            }
        } catch (Exception e) {
            // 예외가 발생한 경우에 대한 처리
            System.out.println("Error processing API response: " + e.getMessage());
        }

        return "";
    }
}

// ApiResponse 클래스 정의
class ApiResponse {
    private String id;
    private String status;
    private Results results;

    // Getter 메서드 (생략)

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Results getResults() {
        return results;
    }
}

// Results 클래스 정의
class Results {
    private Utterance[] utterances;
    private boolean verified;

    // Getter 메서드 (생략)

    public Utterance[] getUtterances() {
        return utterances;
    }

    public boolean isVerified() {
        return verified;
    }
}

// Utterance 클래스 정의
class Utterance {
    private int start_at;
    private int duration;
    private int spk;
    private String spk_type;
    private String msg;

    // Getter 메서드 (생략)

    public int getStart_at() {
        return start_at;
    }

    public int getDuration() {
        return duration;
    }

    public int getSpk() {
        return spk;
    }

    public String getSpk_type() {
        return spk_type;
    }

    public String getMsg() {
        return msg;
    }
}
class IdResponse {
    private String id;

    public String getId() {
        return id;
    }
}


class AccessTokenResponse {
    private String access_token;
    private long expire_at;

    public String getAccessToken() {
        return access_token;
    }

    public long getExpireAt() {
        return expire_at;
    }
}