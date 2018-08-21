package com.api.tawan.tawanapichatapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainRoomActivity extends FragmentActivity implements
        View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        FragmentChatBox.FragmentChatBoxListener,
        FragmentMemberBox.FragmentMemberBoxListener,
        FragmentRoomBox.FragmentRoomBoxListener {
    private Socket socket;
    private String TOKEN;
    private String ID;

    private List<MsgBox> datasetChatBox;

    //new
    private MyViewPageAdapter mAdapter;
    private ViewPager vpagerContent;
    private BottomNavigationView bottomNavigationItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_room);

        init();
        socketSetup();
    }

    private void init() {
        //new
        //view pager
        mAdapter = new MyViewPageAdapter(getSupportFragmentManager());
        vpagerContent = findViewById(R.id.vp_content);
        vpagerContent.setAdapter(mAdapter);
        vpagerContent.setPageTransformer(true, new MyPageTransformer());
        vpagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationItemView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //bottom nav view
        bottomNavigationItemView = findViewById(R.id.bnv_nav_page_menu);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(this);
        //end new

        //Recycle View
        datasetChatBox = new ArrayList<>();
    }

    private void addMsgToChatBox(String textId, String textMsg) {
        MsgBox msgBox = new MsgBox(textId, textMsg);
        datasetChatBox.add(msgBox);
        FragmentChatBox.newInstance().setRvChatBox(datasetChatBox);
    }

    private void socketSetup() {
        socket = SocketSingleton.getInstance().getSocket();
        TOKEN = SocketSingleton.getInstance().getToken();
        ID = SocketSingleton.getInstance().getId();
        //check Token
        try {
            JSONObject data = new JSONObject();
            if (TOKEN != "") {
                data.put("token", TOKEN);
                socket.emit("token", data).on("token", socketOnToken);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //receive msg
        socket.on("send-res", socketOnSendRes);
        socket.on("user list", socketOnUserList);
        socket.on("room list", socketOnRoomList);
    }

    private Emitter.Listener socketOnToken = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        if (data.getString("status").equals("success")) {
                            ID = data.getString("id");
                            SocketSingleton.getInstance().setId(ID);
                        } else {
                            Intent intent = new Intent(MainRoomActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(MainRoomActivity.this, "Token Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener socketOnSendRes = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String sendId = data.getString("id");
                        String msg = data.getString("msg");
                        addMsgToChatBox(sendId, msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener socketOnUserList = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONArray users = data.getJSONArray("users");
                        List<MemberBox> datasetMemberBox = new ArrayList<>();
                        for (int i = 0; i < users.length(); i++) {
                            datasetMemberBox.add(new MemberBox(users.getString(i)));
                        }
                        FragmentMemberBox.newInstance().setRvMemberBox(datasetMemberBox);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener socketOnRoomList = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONArray rooms = data.getJSONArray("rooms");
                        List<RoomBox> datasetRoomBox = new ArrayList<>();
                        for (int i = 0; i < rooms.length(); i++) {
                            datasetRoomBox.add(new RoomBox(rooms.getJSONObject(i)));
                        }
                        FragmentRoomBox.newInstance().setRvRoomBox(datasetRoomBox);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_chat:
                vpagerContent.setCurrentItem(0);
                return true;
            case R.id.item_room:
                vpagerContent.setCurrentItem(1);
                return true;
            case R.id.item_online:
                vpagerContent.setCurrentItem(2);
                return true;
        }
        return false;
    }

    @Override
    public void onBtnSendMsgClick(String msg) {
        JSONObject data = new JSONObject();
        try {
            data.put("token", TOKEN);
            data.put("msg", msg);
            socket.emit("send", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
