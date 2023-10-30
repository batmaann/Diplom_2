import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.User.UserData;
import org.example.User.UserHttp;
import org.example.baseUrl.BaseUrl;
import org.example.orders.OrdersHttp;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasSize;

public class AllOrdersTest {
    private final OrdersHttp ordersHttp = new OrdersHttp(BaseUrl.BASE_URL);
    private final UserHttp userHttp = new UserHttp(BaseUrl.BASE_URL);
    @Test
    @DisplayName("Заказы")
    @Description("Тестирование всех заказов")
    public void testAllOrders() {
        ValidatableResponse response = ordersHttp.getAllOrders();

        assertThat(response.extract().statusCode(), equalTo(200));
        int count = 50;
        response.assertThat()
                .body("success", equalTo(true))
                .body("orders", hasSize(count))
                .body("total", not(emptyString()))
                .body("totalToday", not(emptyString()));
    }


    @Test
    @DisplayName("Получение заказа")
    @Description("Получение заказа с авторизацией")
    public void testAuthUser() {
        String email = "email@" + RandomStringUtils.randomAlphabetic(6) + ".ru";
        String password = "password";
        String name = "name";
        UserData request = new UserData(email, password, name);
        ValidatableResponse responseCreate = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        String responseBody = responseAuth.extract().body().asString(); // Получаем тело ответа в виде строки
        String token = JsonPath.from(responseBody).get("accessToken");
        token = token.replace("Bearer", "").trim();

        ValidatableResponse responseOrder = ordersHttp.getOrders(email, password,name, token);


        assertThat(responseOrder.extract().statusCode(), equalTo(200));
    }

}
















