package com.fernandocejas.android10.sample.presentation.model;

import com.fernandocejas.android10.sample.presentation.db.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Doud on 5/28/2015.
 */
public class UsersModel implements IUsersModel {
    final public UsersModel self = this;
    List<User> users;

    public UsersModel() {
        this.users = new ArrayList<>();
    }

    public interface FetchUserModelCallback {
        void done(User user);
    }

    @Override
    public void getUser(String username, final UsersModel.FetchUserModelCallback callback) {
        for (User user: users) {
            if (user.getUsername() == username) {
                callback.done(user);
                return;
            }
        }

        ParseQuery<User> query = ParseQuery.getQuery(User.class);
        query.setLimit(1);
        query.whereContains("username", username);
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> users, ParseException e) {
                self.users.add(users.get(0));
                callback.done(users.get(0));
            }
        });
    }
}
