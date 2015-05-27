package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  private ParseLoginBuilder parseLoginBuilder;

  @InjectView(R.id.btn_LoadData) Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (parseLoginBuilder == null) {
      parseLoginBuilder = new ParseLoginBuilder(MainActivity.this);
    }

  }

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();

  }

  @Override
  protected void onResume() {
    super.onResume();
    if (ParseUser.getCurrentUser() == null)
      startActivityForResult(parseLoginBuilder.build(), 0);
    else this.navigator.navigateToRoomList(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    this.navigator.navigateToRoomList(this);
  }

  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData)
  void navigateToUserList() {
    this.navigator.navigateToRoomList(this);
  }
}
