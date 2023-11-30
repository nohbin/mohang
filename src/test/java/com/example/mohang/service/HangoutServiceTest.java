package com.example.mohang.service;

import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.domain.constant.SearchType;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - hangout")
@ExtendWith(MockitoExtension.class)
class HangoutServiceTest {

    @InjectMocks
    private HangoutService hangoutService;

    @Mock
    HangoutRepository hangoutRepository;

    @Mock
    UserAccountRepository userAccountRepository;

    @DisplayName("검색어 없이 검색 할 경우 , 모든 글을 return")
    @Test
    void givenNoSearchKeyword_whenSearchingHangout_thenReturnHangoutView(){
        Pageable pageable = Pageable.ofSize(10);
        given(hangoutRepository.findAll(pageable)).willReturn(Page.empty());

        Page<HangoutDto> hangouts = hangoutService.searchHangouts(null,null,pageable);

        assertThat(hangouts).isEmpty();
        BDDMockito.then(hangoutRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 행아웃 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {

        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(10);
        given(hangoutRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        Page<HangoutDto> hangouts = hangoutService.searchHangouts(searchType, searchKeyword, pageable);

        assertThat(hangouts).isEmpty();

        then(hangoutRepository.findByTitleContaining(searchKeyword, pageable));
    }

    @DisplayName("해시태그 없이 검색 할 경우 , 모든 글을 return")
    @Test
    void givenNoSearchParameters_whenSearchingHangoutViaHashtag_thenReturnsEmptyPage(){
        Pageable pageable = Pageable.ofSize(10);

        Page<HangoutDto> hangouts = hangoutService.searchHangoutVisHashtag(null,pageable);

        assertThat(hangouts).isEmpty();
        BDDMockito.then(hangoutRepository).shouldHaveNoInteractions();
    }

    @DisplayName("게시글을 해시태그 검색하면, 게시글 페이지를 반환한다")
    @Test
    void givenHashtag_whenSearchingHangoutViaHashtag_thenReturnsHangoutsPage() {
        String hashtag = "#soju";
        Pageable pageable = Pageable.ofSize(10);
        given(hangoutRepository.findByHashtag(hashtag,pageable)).willReturn(Page.empty(pageable));

        Page<HangoutDto> hangouts = hangoutService.searchHangoutVisHashtag(hashtag,pageable);

        assertThat(hangouts).isEqualTo(Page.empty(pageable));
        then(hangoutRepository).should().findByHashtag(hashtag,pageable);
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenHangoutId_whenSearchingHangout_thenReturnsHangout() {
        Long hangoutId = 1L;
        Hangout hangout = createHangout();
        given(hangoutRepository.findById(hangoutId)).willReturn(Optional.of(hangout));

        HangoutDto hangoutDto = hangoutService.getHangout(hangoutId);

        assertThat(hangoutDto)
                .hasFieldOrPropertyWithValue("title",hangout.getTitle())
                .hasFieldOrPropertyWithValue("content",hangout.getContent())
                .hasFieldOrPropertyWithValue("hashtag",hangout.getHashtag());
        then(hangoutRepository).should().findById(hangoutId);
    }

    private Hangout createHangout() {
        Hangout hangout = Hangout.of(
                createUserAccount(),
                "title",
                "content",
                "#hashtag",
                LocalDateTime.now(),
                "place",
                "address"
        );
        ReflectionTestUtils.setField(hangout,"id",1L);
        return hangout;
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "nohbin",
                "1234",
                "nohbin",
                "nohbin@email.com",
                "nohbin"
        );
    }
}