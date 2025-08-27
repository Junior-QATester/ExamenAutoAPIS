@store
Feature: Validación del API Store de PetStore
  Para garantizar la integridad del API de Store
  Como automatizador principal
  Quiero validar la creación y consulta de órdenes

  Scenario Outline: Creación de una Order
    When envío una petición POST a "/store/order" con el siguiente body:
      """
      {
        "id": <id>,
        "petId": <petId>,
        "quantity": <quantity>,
        "shipDate": "<shipDate>",
        "status": "<status>",
        "complete": <complete>
      }
      """
    Then el código de respuesta debe ser 200
    And el body de la respuesta debe contener el "id" igual a <id>
    And el body de la respuesta debe contener el "status" igual a "<status>"

    Examples:
      | id   | petId | quantity | shipDate              | status  | complete |
      | 101  | 555   | 1        | 2025-08-27T10:00:00Z | placed  | true     |

  Scenario: Consulta de la Order creada
    Given tengo el id de la orden creada
    When envío una petición GET a "/store/order/{orderId}"
    Then el código de respuesta debe ser 200
    And el body de la respuesta debe contener el "id" igual al id creado
    And el body de la respuesta debe contener el "status" igual a "placed"



