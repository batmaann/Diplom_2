import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.example.baseurl.BaseUrl;
import org.example.orders.IngredientData;
import org.example.orders.OrdersHttp;
import org.example.user.UserHttp;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasSize;

public class OrdersAuthTest {
    private final OrdersHttp ordersHttp = new OrdersHttp(BaseUrl.BASE_URL);
    private final UserHttp userHttp = new UserHttp(BaseUrl.BASE_URL);

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без авторизацией")
    public void testOrderNoAuth() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("6540e0269ed280001b37832b");
        list.add("6540da999ed280001b3782f9");
        IngredientData ingredientData = new IngredientData(list);
        ValidatableResponse responseOrder = ordersHttp.postIngridient(ingredientData,"");
        assertThat(responseOrder.extract().statusCode(), equalTo(400));
        responseOrder.assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("One or more ids provided are incorrect"));

    }

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
}
