package org.example.User;

import io.restassured.response.ValidatableResponse;
import org.example.BaseHttp;


import static org.example.baseUrl.BaseUrl.CREATE_USER_AND_AUTH;

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
        return doPostRequest(url + CREATE_USER_AND_AUTH, UserData);
    }

}
