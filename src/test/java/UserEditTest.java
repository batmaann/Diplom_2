import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.User.UserData;
import org.example.User.UserHttp;
import org.example.baseUrl.BaseUrl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;

public class UserEditTest {
    private final UserHttp userHttp = new UserHttp(BaseUrl.BASE_URL);

    @Test
    @DisplayName("Редактирование")
    @Description("Редактирование пользователя который авторизован")
    public void testAuthUser() {
        String email = "email@" + RandomStringUtils.randomAlphabetic(6) + ".ru";
        email = email.toLowerCase();
        String password = RandomStringUtils.randomAlphabetic(6);
        String name = "name" + RandomStringUtils.randomAlphabetic(6);

        UserData request = new UserData(email, password, name);
        ValidatableResponse response = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);

        String responseBody = responseAuth.extract().body().asString(); // Получаем тело ответа в виде строки
        String responseEmail = responseAuth.extract().body().asString();

        String token = JsonPath.from(responseBody).get("accessToken");
        String emailBody = JsonPath.from(responseEmail).get("email");

        token = token.replace("Bearer", "").trim();
        //Edit user

        ValidatableResponse responseEdit = userHttp.editUser(emailBody);
        responseAuth.assertThat()
                .body("success", equalTo(true))
                .body("message", equalTo("Reset email sent"));





        ValidatableResponse responseDelete = userHttp.deleteUser(token);
        assertThat(response.extract().statusCode(), equalTo(200));


    }



}
