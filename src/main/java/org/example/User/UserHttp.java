package org.example.User;

import io.restassured.response.ValidatableResponse;
import org.example.BaseHttp;


import static org.example.baseUrl.BaseUrl.*;

public class UserHttp extends BaseHttp {

    private final String url;

    public UserHttp(String baseurl) {
        super();
        url = baseurl;
    }

    public ValidatableResponse createUser(UserData UserData) {
        return doPostRequest(url + CREATE_USER_AND_AUTH, UserData);

    }

    public ValidatableResponse authUser(UserData UserData) {
        return doPostRequest(url + AUTH_REGISTER, UserData);
    }
    public ValidatableResponse deleteUser(String token) {
        return doDeleteRequest(url + DELETE_USER, token);
    }

    public ValidatableResponse editUser(String email){
        return doPostRequest(url + PASSWORD_RESET, email);
    }



}
