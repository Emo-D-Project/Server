package com.mydiary.my_diary_server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
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

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject body = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONArray items = body.getJSONObject("items").getJSONArray("item");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                if (item.getString("category").equals("PTY")) {
                    int pty = Integer.parseInt(item.getString("obsrValue"));
                    String weatherStatus = convertPtyToString(pty);
                    result.append("날씨 상태: ").append(weatherStatus);
                    break;
                }
            }

            conn.disconnect();
        } catch (IOException | JSONException e) {
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
