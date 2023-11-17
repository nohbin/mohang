package com.example.mohang.repository;

import com.example.mohang.config.JpaConfig;
import com.example.mohang.domain.Hangouts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@DisplayName("JPA 연결 테스트")
public class JpaTest {


    private HangoutsRepository hangoutsRepository;


    public JpaTest(@Autowired HangoutsRepository hangoutsRepository) {
        this.hangoutsRepository = hangoutsRepository;
    }

    @DisplayName("Select Test")
    @Test
    void givenTestDate_whenSelecting_thenWorksFine(){
        List<Hangouts> hangs = hangoutsRepository.findAll();
        assertThat(hangs)
                .isNotNull()
                .hasSize(100);
    }

    @DisplayName("Insert Test")
    @Test
    void givenTestDate_whenInserting_thenWorksFine(){
        // Given
        Long previousCount = hangoutsRepository.count();
        Hangouts hangouts = Hangouts.of("new title", "new content", "new hasgtag");
        // When
        Hangouts savedArticle = hangoutsRepository.save(hangouts);
        List<Hangouts> articles = hangoutsRepository.findAll();

        // then
        assertThat(hangoutsRepository.count()).isEqualTo(previousCount + 1);

    }

    @DisplayName("update Test")
    @Test
    void givenTestDate_whenUpdating_thenWorksFine(){
        // Given
        Hangouts hangouts = Hangouts.of("new title", "new content", "new hasgtag");
        hangoutsRepository.save(hangouts);
        hangoutsRepository.findById(1L).orElseThrow();
        String updatingHashtag = "#Springboot";
        hangouts.setHashtag(updatingHashtag);

        // When
//        Article article = Article.of("new article", "new content", "#Spring");
        Hangouts savedHangs = hangoutsRepository.saveAndFlush(hangouts);

        // then
        assertThat(savedHangs).hasFieldOrPropertyWithValue("hashtag",updatingHashtag);

    }

    @DisplayName("delete Test")
    @Test
    void givenTestDate_whenDeleting_thenWorksFine(){
        // Given
        Hangouts hangouts = Hangouts.of("new article", "new content", "#Spring");
        hangoutsRepository.save(hangouts);
        hangoutsRepository.findById(1L).orElseThrow();
        long previousArticleCount = hangoutsRepository.count();
        long previousArticleCommentCount = hangoutsRepository.count();



        // When
//      Article article = Article.of("new article", "new content", "#Spring");
        hangoutsRepository.delete(hangouts);

        // then
        assertThat(hangoutsRepository.count()).isEqualTo(previousArticleCount - 1);
    }

}
