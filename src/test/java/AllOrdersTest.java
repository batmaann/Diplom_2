import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.baseurl.BaseUrl;
import org.example.orders.IngredientData;
import org.example.orders.OrdersHttp;
import org.example.user.UserData;
import org.example.user.UserHttp;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class AllOrdersTest {
    private final OrdersHttp ordersHttp = new OrdersHttp(BaseUrl.BASE_URL);
    private final UserHttp userHttp = new UserHttp(BaseUrl.BASE_URL);

    String email = "email@" + RandomStringUtils.randomAlphabetic(6) + ".ru";
    String password = "password";
    String name = "name";

    @Test
    @DisplayName("Получение заказа")
    @Description("Получение заказа с авторизацией")
    public void testOrderhUser() {
        UserData request = new UserData(email, password, name);
        ValidatableResponse responseCreate = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        String responseBody = responseAuth.extract().body().asString(); // Получаем тело ответа в виде строки
        String token = JsonPath.from(responseBody).get("accessToken");
        token = token.replace("Bearer", "").trim();
        ValidatableResponse responseOrder = ordersHttp.getOrders(email, password, name, token);
        responseOrder.assertThat()
                .body("success", equalTo(true))
                .body("totalToday", not(empty()))
                .body("total", not(empty()));
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без ингредиентов")
    public void testOrderhUserNoIngridients() {
        ArrayList<String> list = new ArrayList<String>();
        IngredientData ingredientData = new IngredientData(list);
        UserData request = new UserData(email, password, name);
        ValidatableResponse responseCreate = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        String responseBody = responseAuth.extract().body().asString();
        String token = JsonPath.from(responseBody).get("accessToken");
        token = token.replace("Bearer", "").trim();
        ValidatableResponse responseOrder = ordersHttp.postIngridient(ingredientData, token);
        assertThat(responseOrder.extract().statusCode(), equalTo(400));
        responseOrder.assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));

    }

    @Test
    @DisplayName("Создание заказа")
    @Description("с неверным хешем ингредиентов")
    public void testOrderNoTrueIngridients() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("60d3b41abdacab0026a733c6     ");
        list.add("6 0964  6e4dc91  6e00276b2870");
        IngredientData ingredientData = new IngredientData(list);
        UserData request = new UserData(email, password, name);
        ValidatableResponse responseCreate = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        String responseBody = responseAuth.extract().body().asString();
        String token = JsonPath.from(responseBody).get("accessToken");
        token = token.replace("Bearer", "").trim();
        ValidatableResponse responseOrder = ordersHttp.postIngridient(ingredientData, token);
        assertThat(responseOrder.extract().statusCode(), equalTo(500));
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("с верным хешем ингредиентов")
    public void testOrderTrueIngridients() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("6540e0269ed280001b37832b");
        list.add("6540da999ed280001b3782f9");
        IngredientData ingredientData = new IngredientData(list);
        UserData request = new UserData(email, password, name);
        ValidatableResponse responseCreate = userHttp.createUser(request);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        String responseBody = responseAuth.extract().body().asString();
        String token = JsonPath.from(responseBody).get("accessToken");
        token = token.replace("Bearer", "").trim();
        ValidatableResponse responseOrder = ordersHttp.postIngridient(ingredientData, token);
    }

    @After
    public void deleteAfterUser() {
        UserData request = new UserData(email, password, name);
        ValidatableResponse responseAuth = userHttp.authUser(request);
        String responseBody = responseAuth.extract().body().asString(); // Получаем тело ответа в виде строки

        String token = JsonPath.from(responseBody).get("accessToken");
        token = token.replace("Bearer", "").trim();
        ValidatableResponse responseDelete = userHttp.deleteUser(token);


    }


}
