package lesson5.api;

import lesson5.dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductController {

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @GET("products")
    Call<ResponseBody> getProducts();

    @GET("products/{id}")
    Call<Product> getProductByID(@Path("id") int id);

    @PUT("products")
    Call<Product> modifyProduct(@Body Product modifyProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProductById(@Path("id") int id);

}
