package com.fernandocejas.android10.sample.presentation.model;

/**
 * Created by Edouard on 25/05/2015.
 */
public interface IUsersModel {
    void getUser(String username, final UsersModel.FetchUserModelCallback callback);
}
