package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successful")
    void save_PersistAnime_WhenSuccessful(){
        Anime animeToSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToSaved);

        Assertions.assertThat(animeSaved).isNotNull();

        Assertions.assertThat(animeSaved.getId()).isNotNull();

        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToSaved.getName());
    }
    @Test
    @DisplayName("Save update anime when Successful")
    void save_UpdateAnime_WhenSuccessful(){
        Anime animeToSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToSaved);

        animeSaved.setName("Super Hero");

        Anime animeUpdate = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdate).isNotNull();

        Assertions.assertThat(animeUpdate.getId()).isNotNull();

        Assertions.assertThat(animeUpdate.getName()).isEqualTo(animeUpdate.getName());
    }
    @Test
    @DisplayName("Delete removes anime when Successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = animeRepository.save(animeToBeSaved);

        animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }
    @Test
    @DisplayName("Find By Name returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes).isNotNull();

        Assertions.assertThat(animes).isNotEmpty();

        Assertions.assertThat(animes).contains(animeSaved);

    }
    @Test
    @DisplayName("Find By Name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        List<Anime> animes = this.animeRepository.findByName("notFound");

        Assertions.assertThat(animes).isEmpty();
    }
    @Test
    @DisplayName("Save throw ConstrainViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        Anime anime = new Anime();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be empty");
    }
    
}
