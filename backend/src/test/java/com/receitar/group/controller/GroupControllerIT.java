package com.receitar.group.controller;

import com.receitar.client.controller.UserControllerIT;
import com.receitar.commons.BaseIT;
import com.receitar.group.dto.ChangeAdministratorDto;
import com.receitar.group.dto.GroupAddDto;
import com.receitar.group.dto.GroupCreateDto;
import com.receitar.group.dto.GroupViewDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


class GroupControllerIT extends BaseIT {

    private final UserControllerIT userControllerIT = new UserControllerIT();


    @Test
    void shouldCreateGroup() {
        var userId = userControllerIT.createUser("lua");
        createGroup("receitasFit", "receitas saudáveis fáceis", userId);
    }

    @Test
    void shouldReturnAllGroupsEmpty() {
        given()
                .when()
                .get("/groups")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    void shouldReturnGroupById() {
        var userId = userControllerIT.createUser("lua");
        var groupId = createGroup("Receitas fit", "receitas saudáveis", userId);

        given()
                .when()
                .get("/groups/{id}", groupId)
                .then()
                .statusCode(200)
                .body("id", equalTo(groupId))
                .body("name", equalTo("Receitas fit"))
                .body("description", equalTo("receitas saudáveis"))
                .body("quantity", equalTo(1))
                .body("quantityAdmin", equalTo(1))
                .body("createdBy", equalTo(userId))
                .body("createdDate", notNullValue());
    }

    @Test
    void shouldAddUserToGroup() {
        var userId = userControllerIT.createUser("lua");
        var groupId = createGroup("Receitas fit", "receitas saudáveis", userId);

        var userToAddId = userControllerIT.createUser("bruna");
        addUserToGroupSuccess(groupId, userToAddId, userId);

        given()
                .when()
                .get("/groups/{id}", groupId)
                .then()
                .statusCode(200)
                .body("id", equalTo(groupId.toString()))
                .body("name", equalTo("Receitas fit"))
                .body("description", equalTo("receitas saudáveis"))
                .body("quantity", equalTo(2))
                .body("quantityAdmin", equalTo(1))
                .body("createdBy", equalTo(userId.toString()))
                .body("createdDate", notNullValue());
    }

    @Test
    void shouldReturnErrorNotAdminAddUserToGroup() {
        var userId = userControllerIT.createUser("lua");
        var groupId = createGroup("Receitas fit", "receitas saudáveis", userId);

        var userToAddId = userControllerIT.createUser("bruna");
        addUserToGroupSuccess(groupId, userToAddId, userId);

        var userToAdd2Id = userControllerIT.createUser("carla");
        addUserToGroup(groupId, userToAdd2Id, userToAddId)
                .then()
                .statusCode(500);

        given()
                .when()
                .get("/groups/{id}", groupId)
                .then()
                .statusCode(200)
                .body("id", equalTo(groupId.toString()))
                .body("name", equalTo("Receitas fit"))
                .body("description", equalTo("receitas saudáveis"))
                .body("quantity", equalTo(2))
                .body("quantityAdmin", equalTo(1))
                .body("createdBy", equalTo(userId.toString()))
                .body("createdDate", notNullValue());
    }

    @Test
    void shouldChangeAdminAndAddUserToGroup() {
        var userCreator = userControllerIT.createUser("lua");
        var groupId = createGroup("Receitas fit", "receitas saudáveis", userCreator);

        var userNewAdmin = userControllerIT.createUser("bruna");
        addUserToGroupSuccess(groupId, userNewAdmin, userCreator);

        changeAdmin(userNewAdmin, groupId, true, userCreator)
                .then()
                .statusCode(200);

        var userToAddId = userControllerIT.createUser("carla");
        addUserToGroupSuccess(groupId, userToAddId, userNewAdmin);

        given()
                .when()
                .get("/groups/{id}", groupId)
                .then()
                .statusCode(200)
                .body("id", equalTo(groupId.toString()))
                .body("name", equalTo("Receitas fit"))
                .body("description", equalTo("receitas saudáveis"))
                .body("quantity", equalTo(3))
                .body("quantityAdmin", equalTo(2))
                .body("createdBy", equalTo(userCreator.toString()))
                .body("createdDate", notNullValue());
    }

    @Test
    void shouldReturnErrorChangeAdmin() {
        var userCreator = userControllerIT.createUser("lua");
        var groupId = createGroup("Receitas fit", "receitas saudáveis", userCreator);

        var userToAddId = userControllerIT.createUser("bruna");
        addUserToGroupSuccess(groupId, userToAddId, userCreator);

        changeAdmin(userCreator, groupId, false, userToAddId)
                .then()
                .statusCode(500)
                .body("message", equalTo("System user is not admin."));
    }

    private Response changeAdmin(UUID userToChange, UUID groupId, boolean isAdm, UUID systemAdmin) {
        var changeAdministratorDto = new ChangeAdministratorDto(userToChange, groupId, isAdm, systemAdmin);
        return given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(changeAdministratorDto))
                .when()
                .post("/groups/users/admins");
    }

    private UUID createGroup(String name, String description, UUID userId) {
        GroupCreateDto groupCreateDto = new GroupCreateDto(name, description, userId);

        return given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(groupCreateDto))
                .when()
                .post("/groups")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(name))
                .body("description", equalTo(description))
                .body("quantity", equalTo(1))
                .body("quantityAdmin", equalTo(1))
                .body("createdBy", equalTo(userId.toString()))
                .extract()
                .as(GroupViewDto.class)
                .id()
                ;

    }

    private void addUserToGroupSuccess(UUID groupId, UUID userToAddId, UUID systemUserId) {
        addUserToGroup(groupId, userToAddId, systemUserId)
                .then()
                .statusCode(200);
    }

    private Response addUserToGroup(UUID groupId, UUID userId, UUID systemUserId) {
        GroupAddDto groupAddDto = new GroupAddDto(groupId, userId, systemUserId);
        return given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(groupAddDto))
                .when()
                .post("/groups/users");
    }
}