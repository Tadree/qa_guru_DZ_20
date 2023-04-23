package ru.jiehk.specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static ru.jiehk.helpers.CustomApiListener.withCustomTemplates;

public class UsersListRequestSpec {
    public static RequestSpecification userListRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/users")
            .log().all()
            .contentType(JSON);
}
