/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.presentation;

import android.app.Application;

import com.fernandocejas.android10.sample.presentation.db.Message;
import com.fernandocejas.android10.sample.presentation.db.Room;
import com.fernandocejas.android10.sample.presentation.db.User;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ApplicationModule;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    this.initParse();
    this.initializeInjector();

  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();

  }

  private void initParse() {
    Parse.enableLocalDatastore(this);
    ParseObject.registerSubclass(Room.class);
    ParseObject.registerSubclass(Message.class);
    ParseObject.registerSubclass(User.class);
    Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }
}
