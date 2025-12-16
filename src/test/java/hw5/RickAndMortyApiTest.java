package hw5;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import java.util.List;

public class RickAndMortyApiTest {

        private static final String BASE_URL = "https://rickandmortyapi.com/api";

        @Test
        public void testMortyAndLastEpisodeCharacter() {

            String mortyJsonResponse = RestAssured
                    .given()
                    .baseUri(BASE_URL)
                    .when()
                    .get("/character/?name=Morty Smith")
                    .then()
                    .statusCode(200)
                    .extract()
                    .asString();

            JsonPath mortyJsonPath = new JsonPath(mortyJsonResponse);

            String mortyUrl = mortyJsonPath.getString("results[0].url");
            System.out.println("Найдена ссылка на Морти:" + mortyUrl);

            JsonPath mortyDetails = RestAssured.get(mortyUrl).jsonPath();

            String mortySpecies = mortyDetails.getString("species");
            String mortyLocationName = mortyDetails.getString("location.name");
            List<String> mortyEpisodes = mortyDetails.getList("episode");
            String lastMortyEpisodeUrl = mortyEpisodes.get(mortyEpisodes.size() - 1);

            System.out.println("Раса Морти:" + mortySpecies);
            System.out.println("Локация Морти:" + mortyLocationName);
            System.out.println("Последний эпизод с Морти:" + lastMortyEpisodeUrl);

            JsonPath lastEpisodeJsonPath = RestAssured.get(lastMortyEpisodeUrl).jsonPath();
            List<String> episodeCharacters = lastEpisodeJsonPath.getList("characters");
            String lastCharacterUrl = episodeCharacters.get(episodeCharacters.size() - 1);

            System.out.println("Ссылка на последнего персонажа эпизода:" + lastCharacterUrl);

            JsonPath lastCharacterJsonPath = RestAssured.get(lastCharacterUrl).jsonPath();

            String lastCharacterName = lastCharacterJsonPath.getString("name");
            String lastCharacterSpecies = lastCharacterJsonPath.getString("species");
            String lastCharacterLocation = lastCharacterJsonPath.getString("location.name");

            System.out.println("Имя:" + lastCharacterName);
            System.out.println("Раса:" + lastCharacterSpecies);
            System.out.println("Локация:" + lastCharacterLocation);

            System.out.println("Совпадает ли раса:"
                    + lastCharacterSpecies.equals(mortySpecies));
            System.out.println("Совпадает ли локация:"
                    + lastCharacterLocation.equals(mortyLocationName));
        }

}
