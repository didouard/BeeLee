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
package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;


import com.fernandocejas.android10.sample.presentation.db.Message;
import com.fernandocejas.android10.sample.presentation.db.User;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import com.fernandocejas.android10.sample.presentation.model.IRoomModel;
import com.fernandocejas.android10.sample.presentation.model.IUsersModel;
import com.fernandocejas.android10.sample.presentation.model.RoomModel;
import com.fernandocejas.android10.sample.presentation.model.UsersModel;
import com.fernandocejas.android10.sample.presentation.view.RoomView;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class RoomPresenter implements Presenter {
  private final RoomPresenter self = this;
  /** id used to retrieve user details */
  private String roomId;
  private IRoomModel roomModel;
  private IUsersModel userModel;

  private RoomView viewDetailsView;
  private List<Message> messages;

  private ParseUser parseUser;
  @Inject
  public RoomPresenter() {
    roomModel = new RoomModel();
    userModel = new UsersModel();
    messages = new ArrayList<Message>();
    parseUser = ParseUser.getCurrentUser();
  }

  public ParseUser getUser() {
    return parseUser;
  }

  public void setView(@NonNull RoomView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {

  }

  public void initialize(String roomId) {
    this.roomId = roomId;
    this.roomModel.setId(roomId);
    this.loadMessage();
  }

  private void loadMessage() {
    this.hideViewRetry();
    this.showViewLoading();
    this.refreshMessages();
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }


  private void showMessagesInView(List<Message> messages) {
    this.viewDetailsView.renderMessages(messages);
  }

  public void sendMessage(final String message) {
    roomModel.sendMessage(message, parseUser, new RoomModel.RoomModelSendMessageCallback() {
      @Override
      public void done(Message message) {
        messages.add(message);
        showMessagesInView(messages);
      }
    });
  }

  public void refreshMessages() {
    roomModel.fetchMessages(new RoomModel.RoomModelFetchMessageCallback() {
      @Override
      public void done(List<Message> messages) {
        onCompleted();
        self.messages.clear();
        self.messages.addAll(messages);
        showMessagesInView(self.messages);
      }
    });
  }


  public void onCompleted() {
    this.hideViewLoading();
  }

  public void onError(Throwable e) {
    this.hideViewLoading();
    Log.e("beelee", e.getMessage());
    this.showViewRetry();
  }

}
