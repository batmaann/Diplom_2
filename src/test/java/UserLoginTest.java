import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.User.UserData;
import org.example.User.UserHttp;
import org.example.baseUrl.BaseUrl;
import org.junit.Test;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.User.UserData;
import org.example.User.UserHttp;
import org.example.baseUrl.BaseUrl;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Random;

import static io.restassured.RestAssured.when;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.everyItem;



public class UserLoginTest {

    private final UserHttp userHttp = new UserHttp(BaseUrl.BASE_URL);






    @Test
    @DisplayName("Авторизация")
    @Description("авторизация УЗ курьера с кодом 200")
    public void testAuthCourier() {
        String email = "email@" + RandomStringUtils.randomAlphabetic(6)+".ru";
        UserData request = new UserData(
                email,
                "password",
                "name"+ RandomStringUtils.randomAlphabetic(6));

        ValidatableResponse response = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        assertThat(response.extract().statusCode(), equalTo(200));


        responseAuth.assertThat()
                .body("success", equalTo(true))
                .body("accessToken", not(emptyString()))
                .body("refreshToken", not(emptyString()))
                //.body("user.email", equalTo(email))
                .body("user.email", not(emptyString()))
                .body("user.name", not(emptyString()));


        String responseBody = responseAuth.extract().body().asString(); // Получаем тело ответа в виде строки
        String token = JsonPath.from(responseBody).get("accessToken"); // Извлекаем токен из JSON
        token = token.replace("Bearer", "").trim();
        ValidatableResponse responseDelete = userHttp.deleteUser(token);
        assertThat(response.extract().statusCode(), equalTo(200));






    }

//    @Test
//    @DisplayName("Авторизация без обязательных полей")
//    @Description("авторизация если какого-то поля нет или пользователя, запрос возвращает ошибку;")
//    public void testAuthCourierNullLogin() {
//        CourierData request = new CourierData().withLogin("").withPassword(RandomStringUtils.randomAlphanumeric(8));
//        ValidatableResponse response = courierHttp.authCourier(request);
//        int statusCode = response.extract().statusCode();
//        if (statusCode == 400) {
//            assertThat(response.extract().body().jsonPath().getString("message"))
//                    .isEqualTo("Недостаточно данных для входа");
//        } else if (statusCode == 404) {
//            assertThat(response.extract().body().jsonPath().getString("message")).isEqualTo("Учетная запись не найдена");
//        }
//    }
//
//    @Test
//    @DisplayName("Авторизация без обязательных полей")
//    @Description("авторизация если какого-то поля нет или пользователя, запрос возвращает ошибку;")
//    public void testAuthCourierPassword() {
//        CourierData request = new CourierData().withLogin(RandomStringUtils.randomAlphanumeric(8)).withPassword("");
//        ValidatableResponse response = courierHttp.authCourier(request);
//        int statusCode = response.extract().statusCode();
//        if (statusCode == 400) {
//            assertThat(response.extract().body().jsonPath().getString("message"))
//                    .isEqualTo("Недостаточно данных для входа");
//        } else if (statusCode == 404) {
//            assertThat(response.extract().body().jsonPath().getString("message")).isEqualTo("Учетная запись не найдена");
//        }
//    }
















}
