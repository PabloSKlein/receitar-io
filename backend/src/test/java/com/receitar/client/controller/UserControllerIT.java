package com.receitar.client.controller;

import com.receitar.client.dto.UserCreateDto;
import com.receitar.client.dto.UserViewDto;
import com.receitar.commons.BaseIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

//limpar bd antes de cada teste
public class UserControllerIT extends BaseIT {

    //exemplo de post
    @Test
    void shouldCreateUser() {
        createUser("pablo");
    }

    //exemplo get
    @Test
    void shouldReturnUserById() {
        var id = createUser("paulo");
        given()
                .when()
                .get("/users/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.toString()))
                .body("name", equalTo("paulo"))
        ;
    }

    //exemplo de erro
    @Test
    void shouldReturnUserNotFound() {
        String id = "0be667fd-eb9b-4656-bd70-2b810d38c5dc";
        given()
                .when()
                .get("/users/{id}", id)
                .then()
                .statusCode(404)
                .body("message", equalTo("User not found."))
        ;
    }

    //exemplo getALl users
    @Test
    void shouldReturnAllUsers() {
        var lua = createUser("lua");
        var luana = createUser("luana");
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("id", hasItems(lua.toString(), luana.toString()))
                .body("name", hasItems("lua", "luana"))
                .body("[0].id", equalTo(lua.toString()))
                .body("[0].name", equalTo("lua"))
                .body("[1].id", equalTo(luana.toString()))
                .body("[1].name", equalTo("luana"))
        ;
    }

    @Test
    void shouldGetUserByName() {
        var userId = createUser("lua");
        UserCreateDto userCreateDto = new UserCreateDto("lua");
        given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(userCreateDto))
                .when()
                .get("/users/name")
                .then()
                .statusCode(200)
                .body("id", equalTo(userId.toString()))
                .body("name", equalTo("lua"))
        ;
    }

    @Test
    void shouldDeleteUser() {
        var userId = createUser("paloma");
        given()
                .when()
                .delete("/users/{id}", userId)
                .then()
                .statusCode(200);
        given()
                .when()
                .get("/users/{id}", userId)
                .then()
                .statusCode(404)
                .body("message", equalTo("User not found."))
        ;
    }


    public UUID createUser(String name) {
        //given / arrange - preparar os dados
        UserCreateDto userCreateDto = new UserCreateDto(name);
        return given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(userCreateDto))
                //when / act - chamar o método que queremos testar via Api
                .when()
                .post("/users")
                //then / assert - validar o retorno
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(name))
                .extract()
                .as(UserViewDto.class)
                .id()
                ;
    }

}