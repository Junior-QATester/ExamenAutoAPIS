package com.nttdata.steps;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StoreSteps {
    private static String BASE_URL = "https://petstore.swagger.io/v2";
    private static int orderId;
    private Response response;

    @Step("Envio POST para crear orden")
    public void crearOrden(int id, int petId, int quantity, String shipDate, String status, boolean complete) {
        response = given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body("{\"id\": "+id+",\"petId\": "+petId+",\"quantity\": "+quantity+",\"shipDate\": \""+shipDate+"\",\"status\": \""+status+"\",\"complete\": "+complete+"}")
                .when()
                .post("/store/order");
        orderId = id; // guardar id para el siguiente escenario
    }

    @Step("Valido código de respuesta {0}")
    public void validarStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Step("Valido que el body contenga {0} = {1}")
    public void validarBody(String key, String value) {
        response.then().body(key, equalTo(value));
    }

    @Step("Envio GET para consultar la orden creada")
    public void consultarOrden() {
        response = given()
                .baseUri(BASE_URL)
                .when()
                .get("/store/order/" + orderId);
    }


}

