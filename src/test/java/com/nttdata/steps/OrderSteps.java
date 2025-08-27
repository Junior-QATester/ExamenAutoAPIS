package com.nttdata.steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OrderSteps {

    private Response response;
    private static String BASE_URL = "https://petstore.swagger.io/v2";
    private static int createdOrderId;

    @When("envío una petición POST a {string} con el siguiente body:")
    public void envioUnaPeticionPOSTConBody(String endpoint, String body) {
        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(BASE_URL + endpoint)
                .then()
                .extract()
                .response();

        // Guardamos el ID para el siguiente escenario
        createdOrderId = response.jsonPath().getInt("id");
    }

    @Then("el código de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("el body de la respuesta debe contener el {string} igual a {int}")
    public void validarCampoInt(String field, int value) {
        response.then().body(field, equalTo(value));
    }

    @Then("el body de la respuesta debe contener el {string} igual a {string}")
    public void validarCampoString(String field, String value) {
        response.then().body(field, equalTo(value));
    }

    @Given("tengo el id de la orden creada")
    public void tengoElIdDeLaOrdenCreada() {
        // Solo validamos que el ID esté guardado
        if (createdOrderId == 0) {
            throw new RuntimeException("No se encontró un ID de orden creada");
        }
    }

    @When("envío una petición GET a {string}")
    public void envioUnaPeticionGET(String endpoint) {
        String finalEndpoint = endpoint.replace("{orderId}", String.valueOf(createdOrderId));
        response = given()
                .when()
                .get(BASE_URL + finalEndpoint)
                .then()
                .extract()
                .response();
    }

    @Then("el body de la respuesta debe contener el {string} igual al id creado")
    public void validarCampoIdCreado(String field) {
        response.then().body(field, equalTo(createdOrderId));
    }


}
