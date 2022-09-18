package lesson4;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class recipesCuisineTest extends AbstractTest {

    @Test
    void simpleClassifyCuisine() {
        JsonPath response = given()
                .spec(getRequestSpecificationWithFP("title", "Sushi"))
                //.formParam("title", "Sushi")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Japanese"));
    }

    @Test
    void emptyRequest() {
        JsonPath response = given()
                .spec(getRequestSpecificationWithFP("", ""))
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Japanese"));
    }

    @Test
    void classifyCuisineOnIngredients() {
        JsonPath response = given()
                .spec(getRequestSpecificationWithFP("ingredientList", "spaghetti\ntomato"))
                //.formParam("ingredientList", "spaghetti\ntomato")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Japanese"));
        assertThat(response.get("cuisines"), hasItem(containsStringIgnoringCase("Japanese")));
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
                .spec(getRequestSpecificationWithFP("", ""))
                .when()
                .get(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(405);
    }
}
