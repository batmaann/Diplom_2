import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.user.UserData;
import org.example.user.UserHttp;
import org.example.baseurl.BaseUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class UserCreateTest {

    private final UserHttp userHttp = new UserHttp(BaseUrl.BASE_URL);

    static Random random = new Random();
    private final String email;
    private final String password;
    private final String name;
    private final int status;

    public UserCreateTest(String email, String password, String name, int status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.status = status;
    }


    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[][]{
                {"email@" + RandomStringUtils.randomAlphabetic(6) + ".ru", "password", "name" + RandomStringUtils.randomAlphabetic(6), 200},
                {"email", "password", "name", 403},
                {"email", "", "name", 403},
        };
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Создание пользователя с проверками на статус код")
    public void createUser() {
        UserData request = new UserData(email, password, name);
        ValidatableResponse response = userHttp.createUser(request);
        assertThat(response.extract().statusCode(), equalTo(status));
    }
}
