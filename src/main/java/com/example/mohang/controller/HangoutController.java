package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;

import com.example.mohang.domain.constant.FormStatus;
import com.example.mohang.domain.constant.SearchType;
import com.example.mohang.dto.GeoLocationResponseDto;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.HangoutWithDto;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.repository.HangoutRepository;

import com.example.mohang.request.HangoutRequest;
import com.example.mohang.response.HangoutResponse;
import com.example.mohang.security.CustomPrincipal;
import com.example.mohang.service.GeoLocationService;
import com.example.mohang.service.HangoutService;
import com.example.mohang.service.PaginationService;
import io.micrometer.core.instrument.search.Search;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hangouts")
@Controller
public class HangoutController {

    private final HangoutService hangoutService;
    private final PaginationService paginationService;
    private final GeoLocationService geoLocationService;

    @GetMapping
    public String hangouts(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable,
            ModelMap map
    ){
        Page<HangoutResponse> hangouts = hangoutService.searchHangouts(searchType,searchValue,pageable).map(HangoutResponse::from);
        List<Integer> barNumber = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), hangouts.getTotalPages());
        Map<String,List<String>> regionListMap = hangoutService.getRegionListMap();
        map.addAttribute("regionListMap", regionListMap);
        map.addAttribute("hangouts", hangouts);
        map.addAttribute("paginationBarNumbers",barNumber);
        map.addAttribute("searchTypes",SearchType.values());
        return "hangouts/index";
    }

//    @Transactional
//    @GetMapping
//    public String hangout(Model model,
//                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
//                          @RequestParam(value="page", defaultValue="0") int page) {
//        Page<Hangout> paging = service.getList(pageable);
//        List<String> hashtagList = service.getHashtagList();
//        log.info(hashtagList.toString());
//        model.addAttribute("hashtagList", hashtagList);
//        model.addAttribute("paging", paging);
//        return "/hangout/hangouts";
//    }

    @GetMapping("/{hangoutId}")
    public String hangout(@PathVariable Long hangoutId, Model model,
                          @RequestParam(required = false) SearchType searchType,
                          @AuthenticationPrincipal CustomPrincipal customPrincipal) {
        HangoutDto hangout = hangoutService.getHangout(hangoutId);
        model.addAttribute("hangout", hangout);
        List<String[]> joiners = hangoutService.getJoinersByHangoutId(hangoutId);
        model.addAttribute("joiners", joiners);
        String userId = customPrincipal.getUsername();
        if(!userId.equals("") && userId !=null) {
            model.addAttribute("userId", userId);
        }
        boolean isJoined = hangoutService.isJoined(hangout.id(), userId);
        model.addAttribute("isJoined", isJoined);
        Map<String,List<String>> regionListMap = hangoutService.getRegionListMap();
        model.addAttribute("regionListMap", regionListMap);
        model.addAttribute("searchTypes",SearchType.values());
        return "hangouts/hangout";
    }

//    @GetMapping("/search")
//    public String search(Model model,
//                         @RequestParam("cate") String cate,
//                         @RequestParam("keyword") String keyword,
//                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
//                         @RequestParam(value="page", defaultValue="0") int page) {
//        Page<Hangout> paging = hangoutService.search(cate, keyword, pageable);
//        model.addAttribute("paging", paging);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("cate", cate);
//        return "/hangout/hangouts";
//    }

    @GetMapping("/form")
    public String writeForm(ModelMap map,
                            @RequestParam(required = false) SearchType searchType,
                            HttpServletRequest request
                            ) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        // 사용자 ip 받아오기.
        String ip = request.getHeader("X-Forwarded-For");
        log.info("X-FORWARDED-FOR : " + ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            log.info("getRemoteAddr : "+ip);
        }
        log.info("Result : IP Address : "+ip);

        ip = "221.147.174.40"; //TODO 삭제필요함, 테스트용이었음.

        GeoLocationResponseDto geoLocationResponseDto = geoLocationService.getGeoLocation(ip);
        map.addAttribute("geoLocationResponseDto", geoLocationResponseDto);
        map.addAttribute("formStatus", FormStatus.CREATE);
        Map<String,List<String>> regionListMap = hangoutService.getRegionListMap();
        map.addAttribute("regionListMap", regionListMap);
        map.addAttribute("searchTypes",SearchType.values());

        return "hangouts/write";
    }
    @PostMapping("/form")
    public String postNewHangout(
            HangoutRequest hangoutRequest,
            @AuthenticationPrincipal CustomPrincipal customPrincipal
            ){
        hangoutService.saveHangout(hangoutRequest.toDto(customPrincipal.toDto()));
        return "redirect:/hangouts";
    }
    @GetMapping("/{hangoutId}/form")
    public String updateHangoutForm(
            @RequestParam(required = false) SearchType searchType,
            @PathVariable Long hangoutId,
            ModelMap map
    ){
        HangoutResponse hangout = HangoutResponse.from(hangoutService.getHangout(hangoutId));

        map.addAttribute("hangout",hangout);
        map.addAttribute("formStatus", FormStatus.UPDATE);
        Map<String,List<String>> regionListMap = hangoutService.getRegionListMap();
        map.addAttribute("regionListMap", regionListMap);
        map.addAttribute("searchTypes",SearchType.values());


        return "hangouts/write";
    }
    @PostMapping("/{hangoutId}/form")
    public String updateHangout(
            @PathVariable Long hangoutId,
            @AuthenticationPrincipal CustomPrincipal customPrincipal,
            HangoutRequest hangoutRequest
    ){
        hangoutService.updateHangout(hangoutId, hangoutRequest.toDto(customPrincipal.toDto()));

        return "redirect:/hangouts/"+hangoutId;
    }

    @Transactional
    @ResponseBody
    @DeleteMapping("/{hangoutId}/delete")
    public ResponseEntity<?> deleteHangout(
            @PathVariable Long hangoutId,
            @AuthenticationPrincipal CustomPrincipal customPrincipal
    ){
        hangoutService.deleteHangout(hangoutId, customPrincipal.getUsername());
        return ResponseEntity.ok().build();
    }



//    @PostMapping
//    public String write(Model model, HangoutDto dto) {
//        Hangout written = hangoutService.write(dto);
//        model.addAttribute("hangout", written);
//        return "/hangouts/hangout";
//    }
//    @GetMapping("/hashtag/{hashtag}")
//    public String getByHashtag(Model model, @PathVariable("hashtag") String hashtag,
//        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Hangout> paging = hangoutService.getByHashtag(hashtag, pageable);
//        model.addAttribute("paging", paging);
//        model.addAttribute("hashtag", hashtag);
//        Map<String,List<String>> regionListMap = hangoutService.getRegionListMap();
//        model.addAttribute("regionListMap", regionListMap);
//        return "/hangouts/hangouts";
//    }

    @GetMapping("/kakao")
    public String loginexample(){
        return "hangouts/kakaologin";
    }

}
