/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.db.Room;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerRoomComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.RoomComponent;
import com.fernandocejas.android10.sample.presentation.view.fragment.RoomListFragment;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Activity that shows a list of Users.
 */
public class RoomListActivity extends BaseActivity implements HasComponent<RoomComponent>,
        RoomListFragment.RoomListListener {

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, RoomListActivity.class);
  }

  private RoomComponent roomComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_room_list);

    this.initializeInjector();
    setTitle(R.string.activity_title_room_list);
  }

  private void initializeInjector() {
    this.roomComponent = DaggerRoomComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public RoomComponent getComponent() {
    return roomComponent;
  }

  @Override public void onRoomClicked(Room room) {
    this.navigator.navigateToRoom(this, room.getObjectId());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_fragment_room_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_disconnect) {
      ParseUser.logOutInBackground(new LogOutCallback() {
        @Override
        public void done(ParseException e) {
          finish();
        }
      });
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

  }

}
