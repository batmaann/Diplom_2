package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.BaseHttp;


import static org.example.baseurl.BaseUrl.*;

public class UserHttp extends BaseHttp {

    private final String url;

    public UserHttp(String baseurl) {
        super();
        url = baseurl;
    }
    @Step("Создание пользователя")
    public ValidatableResponse createUser(UserData UserData) {
        return doPostRequest(url + CREATE_USER_AND_AUTH, UserData);

    }
    @Step("Аунтификация пользователя")
    public ValidatableResponse authUser(UserData UserData) {
        return doPostRequest(url + AUTH_REGISTER, UserData);
    }
    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String token) {
        return doDeleteRequest(url + DELETE_USER, token);
    }
    @Step("Редактирование пользователя")
    public ValidatableResponse editUser(String email, String token) {
        return doPostRequest(url + PASSWORD_RESET, email, token);
    }

    @Step("Редактирование пользователя")
    public ValidatableResponse editUser(UserData userData ) {
        return doPostRequest(url + PASSWORD_RESET, userData);
    }



}
