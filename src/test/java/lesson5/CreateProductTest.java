package lesson5;

import com.github.javafaker.Faker;
import lesson5.api.ProductController;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateProductTest {

    static ProductController productController;
    Product product = null;
    Faker faker = new Faker();
    int id, price;
    String title, categoryTitle;

    @BeforeAll
    static void beforeAll() throws IOException {
        productController = RetrofitUtils.getRetrofit()
                .create(ProductController.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random()*1000));
    }

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productController.createProduct(product)
                .execute();
        id = response.body().getId();
        price = response.body().getPrice();
        title = response.body().getTitle();
        categoryTitle = response.body().getCategoryTitle();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), CoreMatchers.equalTo(201));

        response = productController.getProductByID(id)
                .execute();
        assertThat(response.body().getId(), CoreMatchers.equalTo(id));
        assertThat(response.body().getTitle(), CoreMatchers.equalTo(title));
        assertThat(response.body().getCategoryTitle(), CoreMatchers.equalTo(categoryTitle));
        assertThat(response.body().getPrice(), CoreMatchers.equalTo(price));

        response = productController.modifyProduct(product
                        .withTitle(faker.food().ingredient())
                        .withCategoryTitle(categoryTitle)
                        .withPrice(price)
                        .withId(id)
                )
                .execute();
        title = response.body().getTitle();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        Response<ResponseBody> responseBody = productController.deleteProductById(id)
                .execute();

        assertThat(responseBody.isSuccessful(), CoreMatchers.is(true));
        response = productController.getProductByID(id)
                .execute();
        assertThat(response.code(), CoreMatchers.equalTo(404));
    }

//    @AfterEach
//    void tearDown() throws IOException {
//        Response<ResponseBody> response = productController.deleteProductById(id).execute();
//        assertThat(response.isSuccessful(), CoreMatchers.is(true));
//    }

}
