package com.fernandocejas.android10.sample.presentation.db;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Doud on 5/28/2015.
 */
@ParseClassName("User")
public class User extends ParseObject {
    public String getUsername() {
        return getString("username");
    }
}
