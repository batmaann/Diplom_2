package org.example.baseurl;

public class BaseUrl {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String GET_DATA_INGRIDIENTS = "/api/ingredients";
    public static final String CREATE_ORDER = "/api/orders";
    public static final String CREATE_USER_AND_AUTH = "/api/auth/register";
    public static final String DELETE_USER = "/api/auth/user";
    public static final String AUTH_REGISTER = "/api/auth/login";
    public static final String GET_ALL_ORDERS = "/api/orders/all";
    public static final String GET_ORDER = "/api/orders";
    public static final String CANCEL_ORDER = "/api/v1/orders/cancel";
    public static final String PASSWORD_RESET = "/api/password-reset";
    public static final String RESET_PASSWORD = "/api/password-reset/reset";
    public static final String LOGOUT_USER ="/api/auth/logout";
    public static final String AUTH_TOKEN ="/api/auth/token";
    public static final String GET_UPDATE_USER ="/api/auth/user";


    public BaseUrl(String baseUrl) {
    }
}