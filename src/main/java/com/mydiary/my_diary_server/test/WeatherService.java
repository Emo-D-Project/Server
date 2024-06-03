package com.mydiary.my_diary_server.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {

    public String getWeatherData(double lat, double lon) {
        StringBuilder result = new StringBuilder();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        // 범위 체크
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            // 유효하지 않은 위도 또는 경도 값이므로 처리
            return "유효하지 않은 위도 또는 경도 값입니다.";
        }

        int intLat = (int) lat; // 소수점 이하 값은 버려집니다.
        int intLon = (int) lon; // 소수점 이하 값은 버려집니다.

        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=bpTMd5DM6zekecRgqG2V5zH49prvWhdB%2F4dXBZuzWOEL7CnMpce9SGuwF5ftbplVVB0UMwbnyX83Vao5fNfJ8A%3D%3D");
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(currentDate.format(dateFormatter), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(currentTime.format(timeFormatter), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(intLat), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(intLon), "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            // ObjectMapper 객체 생성
            ObjectMapper mapper = new ObjectMapper();

            // JSON 문자열을 JsonNode 객체로 파싱
            JsonNode rootNode = mapper.readTree(response.toString());

            // response 필드 내의 body 객체 가져오기
            JsonNode bodyNode = rootNode.get("response").get("body");

            // body 객체 내의 items 객체 가져오기
            JsonNode itemsNode = bodyNode.get("items");

            // items 객체 내의 item 배열 가져오기
            JsonNode itemArrayNode = itemsNode.get("item");

            // item 배열 순회
            for (JsonNode item : itemArrayNode) {
                // category가 PTY인지 확인
                if (item.get("category").asText().equals("PTY")) {
                    // obsrValue 값을 정수로 변환
                    int pty = item.get("obsrValue").asInt();
                    // pty에 따라 날씨 상태를 가져오는 함수 호출
                    String weatherStatus = convertPtyToString(pty);
                    // 결과 문자열에 날씨 상태 추가
                    result.append("날씨 상태: ").append(weatherStatus);
                    break;
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
    private static String convertPtyToString(int pty) {
        switch (pty) {
            case 0:
                return "없음";
            case 1:
                return "비";
            case 2:
                return "비/눈";
            case 3:
                return "눈";
            case 5:
                return "빗방울";
            case 6:
                return "빗방울눈날림";
            case 7:
                return "눈날림";
            default:
                return "알 수 없음";
        }
    }
}
