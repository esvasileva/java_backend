package lesson4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.requestSpecification;

public abstract class AbstractTest {

    static Properties properties = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;

    @BeforeAll
    static void initTest() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        configFile = new FileInputStream("src/main/resources/my.properties");
        properties.load(configFile);

        apiKey = properties.getProperty("apiKey");
        baseUrl = properties.getProperty("base_url");

//        responseSpecification = new ResponseSpecBuilder()
//                .expectStatusCode(200)
//                .expectStatusLine("HTTP/1.1 200 OK")
//                .build();

//        requestSpecification = new RequestSpecBuilder()
//                .addQueryParam("apiKey", apiKey)
//                .build();

//        responseSpecification = responseSpecification;
//        requestSpecification = requestSpecification;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public RequestSpecification getRequestSpecificationWithQP(String nameQP, String valueQP){
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam(nameQP, valueQP)
                .build();
        return requestSpecification;
    }

    public RequestSpecification getRequestSpecificationWithFP(String nameFP, String valueQP){
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam(nameFP, valueQP)
                .build();
        return requestSpecification;
    }
}
