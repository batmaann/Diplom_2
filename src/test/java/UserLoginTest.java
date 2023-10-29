import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.User.UserData;
import org.example.User.UserHttp;
import org.example.baseUrl.BaseUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;

@RunWith(Parameterized.class)
public class UserLoginTest {

    private final UserHttp userHttp = new UserHttp(BaseUrl.BASE_URL);
    private final String email;
    private final String password;
    private final String name;



    public UserLoginTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[][]{
                {"email@test.ru", "password", ""},
                {"email@test.ru", "", "name"},
                {"", "password", "name"},
                {"", "", ""}

        };
    }





    @Test
    @DisplayName("Авторизация")
    @Description("авторизация УЗ курьера с кодом 200")
    public void testAuthUser() {
        String email = "email@" + RandomStringUtils.randomAlphabetic(6) + ".ru";
        email = email.toLowerCase();
        String password = RandomStringUtils.randomAlphabetic(6);
        String name = "name" + RandomStringUtils.randomAlphabetic(6);

        UserData request = new UserData(email, password, name);

        ValidatableResponse response = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        assertThat(response.extract().statusCode(), equalTo(200));
        responseAuth.assertThat()
                .body("success", equalTo(true))
                .body("accessToken", not(emptyString()))
                .body("refreshToken", not(emptyString()))
                .body("user.email", not(emptyString()))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
        String responseBody = responseAuth.extract().body().asString(); // Получаем тело ответа в виде строки
        String token = JsonPath.from(responseBody).get("accessToken"); // Извлекаем токен из JSON
        token = token.replace("Bearer", "").trim();
        ValidatableResponse responseDelete = userHttp.deleteUser(token);
        assertThat(response.extract().statusCode(), equalTo(200));
    }
    @Test
    @DisplayName("Авторизация логин с неверным логином и паролем")
    @Description("авторизация если какого-то поля нет или пользователя, запрос возвращает ошибку;")
    public void testAuthUserNullLogin() {
        UserData request = new UserData(email, password, name);
        ValidatableResponse response = userHttp.authUser(request);
        assertThat(response.extract().statusCode(), equalTo(401));
        response.assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

}
