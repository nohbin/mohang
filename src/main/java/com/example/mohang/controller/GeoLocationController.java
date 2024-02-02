package com.example.mohang.controller;

import com.example.mohang.dto.TwoDotDto;
import com.example.mohang.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geolocation")
public class GeoLocationController {
    private final GeoLocationService geoLocationService;

    @GetMapping("/distance")
    public double getDistance(@RequestParam double lat1,
                              @RequestParam double lng1,
                              @RequestParam double lat2,
                              @RequestParam double lng2) {
        return geoLocationService.calculateDistance(lat1, lng1, lat2, lng2);
    }
}
