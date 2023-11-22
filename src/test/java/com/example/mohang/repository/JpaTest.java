package com.example.mohang.repository;

import com.example.mohang.config.JpaConfig;
import com.example.mohang.domain.Hangout;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@DisplayName("JPA 연결 테스트")
public class JpaTest {


    private HangoutRepository hangoutRepository;


    public JpaTest(@Autowired HangoutRepository hangoutsRepository) {
        this.hangoutRepository = hangoutsRepository;
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
        Hangout hangout = Hangout.of("new title", "new content", "new hasgtag");
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
        Hangout hangout = Hangout.of("new title", "new content", "new hasgtag");
        hangoutRepository.save(hangout);
        hangoutRepository.findById(1L).orElseThrow();
        String updatingHashtag = "#Springboot";
        hangout.setHashtag(updatingHashtag);

        // When
//        Article article = Article.of("new article", "new content", "#Spring");
        Hangout savedHang = hangoutRepository.saveAndFlush(hangout);

        // then
        assertThat(savedHang).hasFieldOrPropertyWithValue("hashtag",updatingHashtag);

    }

    @DisplayName("delete Test")
    @Test
    void givenTestDate_whenDeleting_thenWorksFine(){
        // Given
        Hangout hangout = Hangout.of("new article", "new content", "#Spring");
        hangoutRepository.save(hangout);
        hangoutRepository.findById(1L).orElseThrow();
        long previousArticleCount = hangoutRepository.count();
        long previousArticleCommentCount = hangoutRepository.count();



        // When
//      Article article = Article.of("new article", "new content", "#Spring");
        hangoutRepository.delete(hangout);

        // then
        assertThat(hangoutRepository.count()).isEqualTo(previousArticleCount - 1);
    }

}
