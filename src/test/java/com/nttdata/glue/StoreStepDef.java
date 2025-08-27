package com.nttdata.glue;

import com.nttdata.steps.StoreSteps;
import io.cucumber.java.en.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.Assert;

public class StoreStepDef {
    StoreSteps store = new StoreSteps();

    @When("envío una petición POST a {string} con los datos {int}, {int}, {int}, {string}, {string}, {string}")
    public void envioPost(String endpoint, int id, int petId, int quantity, String shipDate, String status, String complete) {
        store.crearOrden(id, petId, quantity, shipDate, status, Boolean.parseBoolean(complete));
    }

    @Then("el código de respuesta debe ser {int}")
    public void validarStatusCode(int code) {
        store.validarStatusCode(code);
    }

    @And("el body debe contener {string} = {string}")
    public void validarBody(String key, String value) {
        store.validarBody(key, value);
    }

    @When("envío una petición GET a {string} usando el id creado")
    public void envioGet(String endpoint) {
        store.consultarOrden();
    }


}
