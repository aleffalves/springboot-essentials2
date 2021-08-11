package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/2", Anime.class);
        log.info(entity);
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));
        ResponseEntity<List<Anime>> listAnimes = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        log.info((listAnimes));

        Anime kingdom = Anime.builder()
                .name("kingdom")
                .build();
        ResponseEntity<Anime> kingdomSaved = new RestTemplate().postForEntity("http://localhost:8080/animes/", kingdom, Anime.class);
        log.info("Saved anime '{}'", kingdomSaved);

        Anime samuraiChamploo = Anime.builder()
                .name("samurai Champloo")
                .build();
        ResponseEntity<Anime> samuraiChamploSaved = new RestTemplate()
                .exchange("http://localhost:8080/animes/",
                        HttpMethod.POST,
                        new HttpEntity<>(samuraiChamploo, createJsonHeader()),
                        Anime.class);
        log.info("Saved anime '{}'", samuraiChamploSaved);

        Anime animeUpdate = samuraiChamploSaved.getBody();
        animeUpdate.setName("Samurai Champlo");

        ResponseEntity<Void> samuraiUpdate = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeUpdate, createJsonHeader()),
                Void.class);

        log.info(samuraiUpdate);


//        ResponseEntity<Void> samuraiDeleted = new RestTemplate()
//                .exchange("http://localhost:8080/animes/{id}",
//                        HttpMethod.DELETE,
//                        null,
//                        Void.class,
//                        13);
//
//        log.info(samuraiDeleted);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
