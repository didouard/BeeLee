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
package com.fernandocejas.android10.sample.test.mockito.presenter;

import android.content.Context;
import android.test.AndroidTestCase;
import com.fernandocejas.android10.sample.presentation.presenter.RoomPresenter;
import com.fernandocejas.android10.sample.presentation.view.RoomView;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class RoomPresenterTest extends AndroidTestCase {

  private static final String FAKE_ROOM_ID = "M9mLOkaOW1";

  private RoomPresenter roomPresenter;

  @Mock
  private Context mockContext;
  @Mock
  private RoomView mockRoomView;

  @Override protected void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);
    roomPresenter = new RoomPresenter();
    roomPresenter.setView(mockRoomView);
  }

  public void testUserDetailsPresenterInitialize() {
    given(mockRoomView.getContext()).willReturn(mockContext);

    roomPresenter.initialize(FAKE_ROOM_ID);
  }
}
