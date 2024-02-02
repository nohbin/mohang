package com.example.mohang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoDotDto {
    private double lat1;
    private double lng1;
    private double lat2;
    private double lng2;
}
