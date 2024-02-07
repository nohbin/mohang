package com.example.mohang.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoLocationResponseDto {
    private int returnCode;
    private String requestId;
    private Map<String,String> geoLocation;
}

