package lesson3;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class recipesCuisineTest extends AbstractTest {

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void simpleClassifyCuisine() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "Sushi")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Japanese"));
    }

    @Test
    void emptyRequest() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }

    @Test
    void classifyCuisineOnIngredients() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .formParam("ingredientList", "spaghetti\ntomato")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
        assertThat(response.get("cuisines"), hasItem(containsStringIgnoringCase("Italian")));
    }

    @Test
    void requestWithoutApiKey() {
        JsonPath response = given()
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("status"), containsStringIgnoringCase("failure"));
        assertThat(response.get("code"), equalTo(401));
        assertThat(response.get("message"), containsStringIgnoringCase("You are not authorized"));
    }

    @Test
    void getRequest() {
        given()
                .queryParam("apiKey", getApiKey())
                .when()
                .get(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(405);
    }

}
