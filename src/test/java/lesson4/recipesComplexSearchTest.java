package lesson4;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class recipesComplexSearchTest extends AbstractTest {

    @Test
    void simpleSearchWithQuery() {
        JsonPath response = given()
                .spec(getRequestSpecificationWithQP("query", "rice"))
               // .queryParam("query", "rice")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("results.title"), everyItem(containsStringIgnoringCase("rice")));
        assertThat(response.get("totalResults"), equalTo(421));
    }

    @Test
    void searchWithSupportedCuisine() {
        JsonPath response = given()
                .spec(getRequestSpecificationWithQP("query", "pasta"))
            //    .queryParam("query", "pasta")
                .queryParam("cuisine", "Italian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(34));
    }

    @Test
    void emptySearch() {
        JsonPath response = given()
                .spec(getRequestSpecificationWithQP("", ""))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(5224));
        assertThat(response.get("results"), everyItem(notNullValue()));
    }

    @Test
    void searchWithUnsupportedCuisine() {
        JsonPath response = given()
                .spec(getRequestSpecificationWithQP("cuisine", "Russian"))
                //.queryParam("cuisine", "Russian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("results"), everyItem(nullValue()));
        assertThat(response.get("totalResults"), equalTo(0));
    }

    @Test
    void withoutApiKey() {
        JsonPath response = given()
                .queryParam("query", "rice")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("status"), containsStringIgnoringCase("failure"));
        assertThat(response.get("code"), equalTo(401));
        assertThat(response.get("message"), containsStringIgnoringCase("You are not authorized"));
    }
}
