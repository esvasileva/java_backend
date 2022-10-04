package lesson4;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MealPlaneTest extends AbstractTest {


    @Test
    void createAndDeletedMeal() {

        Request request = new Request();
        request.setDate(1644995179);
        request.setPosition(2);
        request.setSlot(2);
        request.setType("INGREDIENTS");

        Request.Ingredient ingredient = new Request.Ingredient();
        ingredient.setName("25 apple");

        List<Request.Ingredient> list = new ArrayList<>();
        list.add(ingredient);

        Request.Value value = new Request.Value();
        value.setIngredients(list);

        request.setValue(value);

        JsonPath id = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", "8fde41d25053dae85c2ccf08d812fd27d2dbc342")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getBaseUrl()+"mealplanner/888921c4-13eb-4bef-ae17-5c8185919615/items")
                .jsonPath();

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", "8fde41d25053dae85c2ccf08d812fd27d2dbc342")
                .pathParam("id", id.get("id"))
                .when()
                .delete(getBaseUrl() + "mealplanner/888921c4-13eb-4bef-ae17-5c8185919615/items/{id}")
                .body()
                .jsonPath();
        assertThat(response.get("status"), equalTo("success"));
    }
}
