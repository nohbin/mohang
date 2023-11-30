package com.example.mohang.repository;

import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaTest.TestJpaConfig.class)
@DisplayName("JPA 연결 테스트")
public class JpaTest {


    private final HangoutRepository hangoutRepository;
    private final UserAccountRepository userAccountRepository;


    public JpaTest(
            @Autowired HangoutRepository hangoutsRepository,
            @Autowired UserAccountRepository userAccountRepository) {
        this.hangoutRepository = hangoutsRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("Select Test")
    @Test
    void givenTestDate_whenSelecting_thenWorksFine(){
        List<Hangout> hangs = hangoutRepository.findAll();
        assertThat(hangs)
                .isNotNull()
                .hasSize(100);
    }

    @DisplayName("Insert Test")
    @Test
    void givenTestDate_whenInserting_thenWorksFine(){
        // Given
        Long previousCount = hangoutRepository.count();
        UserAccount user = userAccountRepository.save(UserAccount.of("nohbin","1234","nickname","email",null));
        Hangout hangout = Hangout.of(user, "new title", "new content","new hashtag",null,"new place","new address");
        // When
        Hangout savedArticle = hangoutRepository.save(hangout);
        List<Hangout> articles = hangoutRepository.findAll();

        // then
        assertThat(hangoutRepository.count()).isEqualTo(previousCount + 1);

    }
    @DisplayName("update Test")
    @Test
    void givenTestDate_whenUpdating_thenWorksFine(){
        // Given
        Hangout hangout = hangoutRepository.findById(1L).orElseThrow();
        String updatingHashtag = "#Springboot";
        hangout.setHashtag(updatingHashtag);

        // When
        Hangout savedHang = hangoutRepository.saveAndFlush(hangout);

        // then
        assertThat(savedHang).hasFieldOrPropertyWithValue("hashtag",updatingHashtag);

    }

    @DisplayName("delete Test")
    @Test
    void givenTestDate_whenDeleting_thenWorksFine(){
        // Given
        Hangout hangout = hangoutRepository.findById(1L).orElseThrow();
        hangoutRepository.save(hangout);
        hangoutRepository.findById(1L).orElseThrow();
        long previousArticleCount = hangoutRepository.count();



        // When
        hangoutRepository.delete(hangout);

        // then
        assertThat(hangoutRepository.count()).isEqualTo(previousArticleCount - 1);
    }

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig{
        @Bean
        public AuditorAware<String> auditorAware(){
            return () -> Optional.of("nohbin");
        }
    }

}
