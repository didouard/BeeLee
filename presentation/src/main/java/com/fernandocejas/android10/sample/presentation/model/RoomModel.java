package com.fernandocejas.android10.sample.presentation.model;


import android.util.Log;

import com.fernandocejas.android10.sample.presentation.db.Message;
import com.fernandocejas.android10.sample.presentation.db.Room;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edouard on 25/05/2015.
 */
public class RoomModel implements IRoomModel {
    final RoomModel self = this;
    private Room room;
    List<Message> messages;
    String id;
    ParseUser user;

    public RoomModel() {
        room = new Room();
        messages = new ArrayList<>();
        user = ParseUser.getCurrentUser();
    }

    public ParseUser getUser() {
        return user;
    }

    public void setUser(ParseUser user) {
        this.user = user;
    }

    @Override
    public void setName(String name) {
        room.setName(name);
    }

    @Override
    public String getName() {
        return room.getName();
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void setPosition(Integer position) {
        room.setPosition(position);
    }

    @Override
    public Integer getPosition() {
        return null;
    }

    public interface RoomModelFetchRoomCallback {
        void done(RoomModel room);
    }

    @Override
    public void fetchRoom(final RoomModelFetchRoomCallback callback) {
        ParseQuery<Room> query = ParseQuery.getQuery(Room.class);
        query.setLimit(1);
        query.whereContains("objectId", id);
        query.findInBackground(new FindCallback<Room>() {
            public void done(List<Room> rooms, ParseException e) {
                if (e == null) {
                    self.room = rooms.get(0);
                    callback.done(self);
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    public interface RoomModelSendMessageCallback {
        public void done(Message message);
    }

    @Override
    public void sendMessage(String message, ParseUser parseUser, final RoomModelSendMessageCallback callback) {
        final Message mMessage = new Message();
        mMessage.setRoomId(id);
        mMessage.setBody(message);
        mMessage.setUserId(parseUser.getObjectId());
        mMessage.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                callback.done(mMessage);
            }
        });
    }

    public interface RoomModelFetchMessageCallback {
        public void done(List<Message> messages);
    }

    @Override
    public void fetchMessages(final RoomModelFetchMessageCallback callback) {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.setLimit(50);
        query.whereContains("roomId", id);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    callback.done(messages);
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }
}
