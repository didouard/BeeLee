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
package com.fernandocejas.android10.sample.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import com.fernandocejas.android10.sample.presentation.view.activity.RoomActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.RoomListActivity;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public void Navigator() {
    //empty
  }

  public void navigateToRoomList(Context context) {
    if (context != null) {
      Intent intentToLaunch = RoomListActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  public void navigateToRoom(Context context, String roomId) {
    if (context != null) {
      Intent intentToLaunch = RoomActivity.getCallingIntent(context, roomId);
      context.startActivity(intentToLaunch);
    }
  }
}
