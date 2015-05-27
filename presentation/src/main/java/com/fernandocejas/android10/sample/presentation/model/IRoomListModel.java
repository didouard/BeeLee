package com.fernandocejas.android10.sample.presentation.model;

import java.util.List;

/**
 * Created by Edouard on 25/05/2015.
 */
public interface IRoomListModel {
    void setRoomList(List<com.fernandocejas.android10.sample.presentation.db.Room> rooms);
    List<com.fernandocejas.android10.sample.presentation.db.Room> getRoomList();

    void fetchRooms(final RoomListModel.FetchRoomListModelCallback callback);
    void createRoom(String name, final RoomListModel.CreateRoomListModelCallback callback);
    void deleteRoom(com.fernandocejas.android10.sample.presentation.db.Room room);
}
