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
package com.fernandocejas.android10.sample.presentation.model;

import android.util.Log;

import com.fernandocejas.android10.sample.presentation.db.Room;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RoomListModel implements IRoomListModel {

  List<Room> rooms;

  public RoomListModel() {
    rooms = new ArrayList<com.fernandocejas.android10.sample.presentation.db.Room>();
  }

  @Override
  public void setRoomList(List<com.fernandocejas.android10.sample.presentation.db.Room> rooms) {
    this.rooms = rooms;
  }

  @Override
  public List<com.fernandocejas.android10.sample.presentation.db.Room> getRoomList() {
    return rooms;
  }

  @Override
  public void deleteRoom(com.fernandocejas.android10.sample.presentation.db.Room room) {
    rooms.remove(room);
  }

  public interface CreateRoomListModelCallback {
    void done(Room room);
  }

  @Override
  public void createRoom(String name, final CreateRoomListModelCallback callback) {
    final Room mRoom = new Room();
    mRoom.setName(name);
    mRoom.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        rooms.add(mRoom);
        callback.done(mRoom);
      }
    });
  }

  public interface FetchRoomListModelCallback {
    void done(List<Room> rooms);
  }
  @Override
  public void fetchRooms(final FetchRoomListModelCallback callback) {
    ParseQuery<Room> query = ParseQuery.getQuery(Room.class);
    query.setLimit(100);
    query.orderByAscending("position");
    query.findInBackground(new FindCallback<Room>() {
      @Override
      public void done(List<Room> rooms, ParseException e) {
        if (e == null) {
          callback.done(rooms);
        } else {
          Log.d("room", "Error: " + e.getMessage());
        }
      }
    });

  }
}
