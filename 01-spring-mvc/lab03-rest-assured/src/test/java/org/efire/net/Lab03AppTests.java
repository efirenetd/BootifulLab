package org.efire.net;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.efire.net.model.Winner;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lab03AppTests {

    @Test
    void givenALottoIdOneWhenValidatedExpectTwoWinnersAndResponseOk() {
        var response = RestAssured.get("/lab03/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("lottoId", Matchers.equalTo(1))
                .body("winningNumbers", Matchers.contains(1, 2, 3, 4, 5, 6))
                .extract().response();

        List<Winner> winners = JsonPath.from(response.asInputStream()).get("winners");
        assertThat(winners).hasSize(2);
    }

    @Test
    void givenLottoIdOneWhenWinneId3ValidatedExpectWithValues() {
        when()
                .get("lab03/1")
                .then()
                .body("winners.find { it.winnerId == 3 }.numbers", notNullValue());
    }

    @Test
    void givenInvalidLottoIdWhenValidatedExpectNoRecordStatusCode500() {
        RestAssured.get("lab03/2")
                .then().assertThat().statusCode(500);
    }
}
