package com.example.mohang.service;

import com.example.mohang.dto.GeoLocationResponseDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private final RestTemplate restTemplate;
    private final String hostName = "https://geolocation.apigw.ntruss.com";
    private final String requestUrl= "/geolocation/v2/geoLocation";

    @Value("${naver.rest.api.access}")
    private String access;
    @Value("${naver.rest.api.secret}")
    private String secret;



    public GeoLocationResponseDto getGeoLocation(String ip) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        //
        String baseString = requestUrl + "?" +
                "ip=" + ip +
                "&ext=t" +
                "&responseFormatType=json";

        //
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(hostName + baseString);
        URI uri = uriBuilder.build().encode().toUri();

        // signature 생성
        String timestamp = Long.toString(System.currentTimeMillis());
        String signature = makeSignature(baseString, timestamp, access, secret);

        // header 셋팅하기
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-timestamp", timestamp);
        headers.set("x-ncp-iam-access-key", access);
        headers.set("x-ncp-apigw-signature-v2", signature);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        // exchange
        String json = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class).getBody();
        GeoLocationResponseDto geoLocationResponseDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            GeoLocationResponseDto dto = objectMapper.readValue(json, GeoLocationResponseDto.class);
            geoLocationResponseDto = dto;


        } catch(Exception e) {
            return null;
        }

        // return
        return geoLocationResponseDto;
    }

    private static String makeSignature(String baseString, String timestamp, String accessKey, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String signature;
        String space = " ";
        String newLine = "\n";

        String rawString = new StringBuilder()
                .append("GET")
                .append(space)
                .append(baseString)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(rawString.getBytes("UTF-8"));

        signature = Base64.encodeBase64String(rawHmac);
        return signature;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

}
