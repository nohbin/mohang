package com.example.mohang.controller;

import com.example.mohang.config.TestSecurityConfig;
import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.domain.constant.FormStatus;
import com.example.mohang.domain.constant.SearchType;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.HangoutWithChatDto;
import com.example.mohang.dto.UserAccountDto;
import com.example.mohang.repository.HangoutWithRepository;
import com.example.mohang.request.HangoutRequest;
import com.example.mohang.response.HangoutResponse;
import com.example.mohang.service.HangoutService;
import com.example.mohang.service.PaginationService;
import com.example.mohang.utill.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Import({TestSecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(HangoutController.class)
public class HangoutControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean
    private HangoutService hangoutService;
    @MockBean
    private PaginationService paginationService;

    public HangoutControllerTest(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @DisplayName("view/get 행아웃 리스트 페이지 - 호출")
    @Test
    public void givenNothing_whenRequestiongHangoutView_thenReturnHangoutView() throws Exception {
        given(hangoutService.searchHangouts(eq(null),eq(null),any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(),anyInt())).willReturn(List.of(0,1,2,3,4,5));

        mvc.perform(get("/hangouts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("hangouts/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("hangouts"))
                .andExpect(model().attributeExists("paginationBarNumbers"));

        then(hangoutService).should().searchHangouts(eq(null),eq(null),any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(),anyInt());
    }

    @DisplayName("view/get 행아웃 리스트 페이지 - 검색어")
    @Test
    public void givenSearchKeyword_whenRequestingSearchingHangoutView_thenReturnsHangoutView() throws Exception {
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        given(hangoutService.searchHangouts(eq(searchType),eq(searchValue),any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(),anyInt())).willReturn(List.of(0,1,2,3,4,5));

        mvc.perform(get("/hangouts")
                        .queryParam("searchType", searchType.name())
                        .queryParam("searchValue", searchValue))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("hangouts/index"))
                .andExpect(model().attributeExists("hangouts"))
                .andExpect(model().attributeExists("searchTypes"));
        then(hangoutService).should().searchHangouts(eq(searchType), eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @WithMockUser
    @DisplayName("view/get 행아웃 작성 페이지")
    @Test
    void givenNothing_whenRequesting_thenReturnsNewHangoutPage() throws Exception {
        mvc.perform(get("/hangouts/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("hangouts/write"))
                .andExpect(model().attribute("formStatus", FormStatus.CREATE));
    }

    @WithUserDetails(value = "nohbin", userDetailsServiceBeanName = "userDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("view/post 새 행아웃 등록 - 정상 호출")
    @Test
    void givenNewHangoutInfo_whenRequesting_thenSavesNewHangout() throws Exception {
        HangoutRequest hangoutRequest = HangoutRequest.of("title","content","hashtag",LocalDateTime.now(),"place","address");
        willDoNothing().given(hangoutService).saveHangout(any(HangoutDto.class));

        mvc.perform(
                post("/hangouts/form")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(hangoutRequest))
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/hangouts"))
                .andExpect(redirectedUrl("/hangouts"));
        then(hangoutService).should().saveHangout(any(HangoutDto.class));
    }

    @WithMockUser
    @DisplayName("view/get 행아웃 수정 - 정상 호출, 사용지 인증")
    @Test
    void givenNothing_whenRequesting_thenReturnsUpdatedHangoutPage() throws Exception {
        long hangoutId = 1L;
        HangoutDto dto = createHangoutDto();
        given(hangoutService.getHangout(hangoutId)).willReturn(dto);

        mvc.perform(get("/hangouts/" + hangoutId + "/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("hangouts/write"))
                .andExpect(model().attribute("hangout", HangoutResponse.from(dto)))
                .andExpect(model().attribute("formStatus",FormStatus.UPDATE));

        then(hangoutService).should().getHangout(hangoutId);
    }

    @WithUserDetails(value = "nohbin", userDetailsServiceBeanName = "userDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("view/post 행아웃 수정 - 정상 호출")
    @Test
    void givenUpdateHangoutInfo_whenRequesting_thenUpdateHangout() throws Exception {
        long hangoutId = 1L;
        HangoutRequest hangoutRequest = HangoutRequest.of("title","content","#new",LocalDateTime.now(),"place","address");
        willDoNothing().given(hangoutService).updateHangout(eq(hangoutId),any(HangoutDto.class));

        mvc.perform(
                post("/hangouts/" + hangoutId + "/form")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(hangoutRequest))
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/hangouts/" + hangoutId))
                .andExpect(redirectedUrl("/hangouts/"+hangoutId));
        then(hangoutService).should().updateHangout(eq(hangoutId),any(HangoutDto.class));

    }

    @WithUserDetails(value = "nohbin", userDetailsServiceBeanName = "userDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("view/post 게시글 삭제 - 정상 호출")
    @Test
    void givenHangoutId_whenRequestion_thenDeleteHangout(){
        long hangoutId = 1l;
        String userId = "nohbin";
        willDoNothing().given(hangoutService).deleteHangout(hangoutId, userId);
    }



//    //    @Disabled("에러 수정 전 테스트 사용 막음")
//    @Test
//    void insert() {
//        HangoutDto dto = new HangoutDto("테스트", "안녕하세요", "걍테스트", LocalDateTime.now(), "엔젤리너스", "수원역", null, null,null, null);
//        Hangout expected = new Hangout(dto);
//        Hangout saved = service.write(dto);
//        assertEquals(expected, saved);
//    }

    private HangoutDto createHangoutDto(){
        return HangoutDto.of(
                userAccountDto(),
                "title",
                "content",
                "#drink",
                LocalDateTime.now(),
                "place",
                "address"
        );
    }
    private HangoutWithChatDto hangoutWithChatDto(){
        return hangoutWithChatDto().of(
               1L,
               userAccountDto(),
                Set.of(),
                "title",
                "content",
                "#eat",
                LocalDateTime.now(),
                "palce",
                "address",
                LocalDateTime.now(),
                "nohbin",
                LocalDateTime.now(),
                "nohbin"
        );
    }

    private UserAccountDto userAccountDto(){
        return UserAccountDto.of(
                "nohbin",
                "1234",
                "nohbin@email.com",
                "nohbin",
                LocalDateTime.now(),
                "nohbin",
                LocalDateTime.now(),
                "nohbin"
        );
    }


}
