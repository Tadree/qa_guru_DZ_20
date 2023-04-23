package ru.jiehk.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.jiehk.models.RegistrationBody;
import ru.jiehk.models.RegistrationSuccessfulResponse;
import ru.jiehk.models.UsersListResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.jiehk.specs.RegistrationRequestSpec.registrationRequestSpec;
import static ru.jiehk.specs.RegistrationResponseSpec.registrationResponseSpec;
import static ru.jiehk.specs.UserListResponseSpec.userListResponseSpec;
import static ru.jiehk.specs.UsersListRequestSpec.userListRequestSpec;

public class ReqresInTests {
    @Test
    void registrationTest() {
        RegistrationBody body = new RegistrationBody();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");
        RegistrationSuccessfulResponse response = given()
                .spec(registrationRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .statusCode(200)
                .extract().as(RegistrationSuccessfulResponse.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo(4);
    }

    @Test
    void missingPasswordRegistrationTest() {
        RegistrationBody body = new RegistrationBody();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("");
        given()
                .spec(registrationRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void missingEmailRegistrationTest() {
        RegistrationBody body = new RegistrationBody();
        body.setEmail("");
        body.setPassword("12345");
        given()
                .spec(registrationRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void usersListPaginationTest() {
        UsersListResponse response = given()
                .spec(userListRequestSpec)
                .param("page", 1)
                .when()
                .get()
                .then()
                .spec(userListResponseSpec)
                .extract().as(UsersListResponse.class);

        assertThat(response.getData().size()).isEqualTo(response.getPer_page());
    }

    @Test
    void emptyUsersListTest() {
        UsersListResponse response = given()
                .spec(userListRequestSpec)
                .param("page", 100)
                .when()
                .get()
                .then()
                .spec(userListResponseSpec)
                .extract().as(UsersListResponse.class);

        assertThat(response.getData()).isEmpty();
    }

}
