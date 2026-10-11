package model.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.TestConfig;

public class RequestSpecs {
    static String username = TestConfig.getUsername();
    static String password = TestConfig.getPassword();
    static String baseUri = TestConfig.getBaseUrl();

    public static RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .setAuth(io.restassured.RestAssured.basic(username, password))
                .build();
    }
}
