package lesson5.utils;

import lombok.experimental.UtilityClass;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class RetrofitUtils {

    Properties prop = new Properties();
    private static InputStream configFile;

    static {
        try {
            configFile = new FileInputStream("src/main/resources/my.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() throws IOException {
        prop.load(configFile);
        return prop.getProperty("base_url");
    }

    public Retrofit getRetrofit() throws IOException {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
