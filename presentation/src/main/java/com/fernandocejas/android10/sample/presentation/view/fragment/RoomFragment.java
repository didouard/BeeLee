/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.db.Message;
import com.fernandocejas.android10.sample.presentation.internal.di.components.RoomComponent;
import com.fernandocejas.android10.sample.presentation.presenter.RoomPresenter;
import com.fernandocejas.android10.sample.presentation.view.RoomView;
import com.fernandocejas.android10.sample.presentation.view.adapter.RoomAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class RoomFragment extends BaseFragment implements RoomView {

  private static final String ARGUMENT_KEY_ROOM_ID = "org.android10.ARGUMENT_ROOM_ID";

  private String roomId;

  @Inject
  RoomPresenter roomPresenter;

  @InjectView(R.id.lvChat)
  ListView lvChat;
  @InjectView(R.id.etMessage)
  EditText etMessage;
  @InjectView(R.id.btSend)
  Button btSend;

  private ArrayList<Message> mMessages;
  private RoomAdapter mAdapter;

  public RoomFragment() { super(); }

  public static RoomFragment newInstance(String roomId) {
    RoomFragment roomFragment = new RoomFragment();

    Bundle argumentsBundle = new Bundle();
    argumentsBundle.putString(ARGUMENT_KEY_ROOM_ID, roomId);
    roomFragment.setArguments(argumentsBundle);

    return roomFragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_room, container, false);
    ButterKnife.inject(this, fragmentView);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.roomPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.roomPresenter.pause();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.roomPresenter.destroy();
  }

  private void initialize() {
    getComponent(RoomComponent.class).inject(this);
    roomPresenter.setView(this);
    roomId = getArguments().getString(ARGUMENT_KEY_ROOM_ID);
    roomPresenter.initialize(this.roomId);
    mMessages = new ArrayList<Message>();
    mAdapter = new RoomAdapter(this.getActivity(), roomPresenter, mMessages);
    lvChat.setAdapter(mAdapter);
  }

  @Override public void renderMessages(List<Message> messages) {
    mMessages.clear();
    mMessages.addAll(messages);
    mAdapter.notifyDataSetChanged();
    lvChat.invalidate();
    getActivity().setTitle(getString(R.string.activity_title_room));
  }

  @Override public void showLoading() {
    //this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    //this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    //this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    //this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context getContext() {
    return getActivity().getApplicationContext();
  }

  @OnClick(R.id.btSend)
  void onClickSendMessage() {
    roomPresenter.sendMessage(etMessage.getText().toString());
    etMessage.setText("");
  }
}
