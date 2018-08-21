package com.api.tawan.tawanapichatapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoomBox {
    private String name;
    private String id;
    private boolean lock;
    private JSONArray memberList;

    public RoomBox(JSONObject room) {
        try {
            this.id = room.getString("id");
            this.name = room.getString("name");
            this.lock = room.getBoolean("lock");
            this.memberList = room.getJSONArray("members");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public JSONArray getMemberList() {
        return memberList;
    }

    public boolean getLock(){
        return lock;
    }

    public int getNumMember(){
        return memberList.length();
    }
}
