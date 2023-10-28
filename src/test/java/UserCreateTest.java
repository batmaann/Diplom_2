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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;




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
                {"email@" + RandomStringUtils.randomAlphabetic(6)+".ru", "password", "name"+ RandomStringUtils.randomAlphabetic(6), 200},
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
        //response.body("someKey", equalTo("someValue"));
    }
}
