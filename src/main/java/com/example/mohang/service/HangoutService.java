package com.example.mohang.service;

import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.domain.constant.SearchType;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.HangoutWithDto;
import com.example.mohang.dto.UserAccountDto;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.entity.HangoutWithID;
import com.example.mohang.repository.ChatRepository;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.repository.HangoutWithRepository;
import com.example.mohang.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class HangoutService {
    private final UserAccountRepository userAccountRepository;
    private final HangoutRepository hangoutRepository;
    private final HangoutWithRepository hangoutWithRepository;
    private final ChatRepository chatRepository;

    public Page<Hangout> getList(Pageable pageable) {
        return this.hangoutRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public HangoutDto getHangout(Long id) {
        return hangoutRepository.findById(id)
                .map(HangoutDto::from)
                .orElseThrow(()->new EntityNotFoundException("게시글이 없습니다 - id : " + id));
    }
    public Page<Hangout> search(String cate, String keyword, Pageable pageable) {

        if(cate.equals("title")) {
            return hangoutRepository.findByTitleContaining(keyword, pageable);
        } else if(cate.equals("content")) {
            return hangoutRepository.findByContentContaining(keyword, pageable);
        } else if(cate.equals("createdBy")) {
            return hangoutRepository.findByCreatedByContaining(keyword, pageable);
        } else {
            return null;
        }
    }
    @Transactional(readOnly = true)
    public Page<HangoutDto> searchHangouts(SearchType searchType, String searchKeyword , Pageable pageable){
        if(searchKeyword == null || searchKeyword.isBlank()){
            return hangoutRepository.findAll(pageable).map(HangoutDto::from);
        }

        return switch (searchType){
            case TITLE -> hangoutRepository.findByTitleContaining(searchKeyword,pageable).map(HangoutDto::from);
            case CONTENT ->hangoutRepository.findByContentContaining(searchKeyword, pageable).map(HangoutDto::from);
            case ID ->hangoutRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(HangoutDto::from);
            case NICKNAME ->hangoutRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(HangoutDto::from);
            case HASHTAG ->hangoutRepository.findByHashtag(searchKeyword, pageable).map(HangoutDto::from);
            case REGION ->hangoutRepository.findByRegion2(searchKeyword, pageable).map(HangoutDto::from);
        };
    }

    @Transactional
    public void saveHangout(HangoutDto dto){
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userId());
        Hangout written = hangoutRepository.save(dto.toEntity(userAccount));
        HangoutWith hangwith = new HangoutWith(written, userAccount.getUserId(), 1);
        hangoutWithRepository.save(hangwith);
    }

    public void saveHangoutWith(HangoutWithDto dto, UserAccountDto userAccountDto) {
        Hangout hangout = hangoutRepository.findById(dto.hangoutId()).orElse(null);
        HangoutWith hangwith = new HangoutWith(hangout, userAccountDto.userId(), 0);
        hangoutWithRepository.save(hangwith);
    }

    public void updateHangout(Long hangoutId, HangoutDto dto){
        Hangout hangout = hangoutRepository.getReferenceById(hangoutId);
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userId());

        if(hangout.getUserAccount().getUserId().equals(userAccount.getUserId())){
            if(dto.title() != null){
                hangout.setTitle(dto.title());
            }
            if(dto.content() != null){
                hangout.setContent(dto.content());
            }
            if(dto.hashtag() != null){
                hangout.setHashtag(dto.hashtag());
            }
            if(dto.meetDate() != null){
                hangout.setMeetDate(dto.meetDate());
            }
            if(dto.place() != null){
                hangout.setPlace(dto.place());
            }
            if(dto.address() != null){
                hangout.setAddress(dto.address());
            }
        }
        hangoutRepository.save(hangout);
    }

//    @Transactional
//    public Hangout write(HangoutDto dto) {
//        Hangout hangout = new Hangout(dto);
//        Hangout written = hangoutRepository.save(hangout);
//        String writer = written.getCreatedBy();
//        HangoutWith hangwith = new HangoutWith(written, writer, 1);
//        hangoutWithRepository.save(hangwith);
//        return written;
//    }


    public List<String> getHashtagList() {
        return hangoutRepository.findAllHashtag();
    }
    public Map<String, List<String>> getRegionListMap() {
        Map<String, List<String>> regionListMap = new HashMap<>();
        List<String> region1List = hangoutRepository.findAllRegion1();
        List<String> region2List;
        for(String region1 : region1List) {
            region2List = hangoutRepository.findAllRegion2(region1);
            regionListMap.put(region1, region2List);
        }
        return regionListMap;
    }
    public Page<Hangout> getByHashtag(String hashtag, Pageable pageable) {
        return hangoutRepository.findByHashtag(hashtag, pageable);
    }

    public Page<HangoutDto> searchHangoutVisHashtag(String hashtag, Pageable pageable) {
        if(hashtag == null || hashtag.isBlank()){
            return Page.empty(pageable);
        }
        return hangoutRepository.findByHashtag(hashtag,pageable).map(HangoutDto::from);
    }


    public void deleteHangout(long hangoutId, String userId) {
        hangoutWithRepository.deleteByHangout_Id(hangoutId);
        chatRepository.deleteByHangout_Id(hangoutId);
        hangoutRepository.deleteByIdAndUserAccount_UserId(hangoutId,userId);
    }

    public boolean isJoined(Long hangoutId, String userId) {
        return hangoutWithRepository.findByHangoutAndUserId(hangoutId, userId) == null ||
                hangoutWithRepository.findByHangoutAndUserId(hangoutId, userId).equals("") ? false : true;
    }

    public List<String[]> getJoinersByHangoutId(Long hangoutId) {
        return hangoutWithRepository.findByHangoutId(hangoutId);
    }
}
