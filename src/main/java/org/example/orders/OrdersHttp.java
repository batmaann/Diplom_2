package org.example.orders;

import io.restassured.response.ValidatableResponse;
import org.example.BaseHttp;
import org.example.User.UserData;

import static org.example.baseUrl.BaseUrl.GET_ALL_ORDERS;
import static org.example.baseUrl.BaseUrl.GET_ORDER;

public class OrdersHttp extends BaseHttp {
    private final String url;


    public OrdersHttp(String baseurl) {
        super();
        url = baseurl;
    }

    public ValidatableResponse getAllOrders(UserData UserData) {
        return doGetRequest(url + GET_ALL_ORDERS, UserData);
    }

    public ValidatableResponse getOrders(UserData UserData) {
        return doGetRequest(url + GET_ORDER, UserData);
    }


}
