package api.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.TestConfig;

public final class RequestSpecs {
    private RequestSpecs() {
    }


    public static RequestSpecification baseRequestSpec() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(TestConfig.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAuth(RestAssured.basic(
                        TestConfig.getUsername(),
                        TestConfig.getPassword()
                ))
                .addFilter(new AllureRestAssured())
                .build();

        return requestSpec.log().ifValidationFails();
    }

    public static RequestSpecification unauthenticatedRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(TestConfig.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

}
