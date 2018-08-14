package com.api.tawan.tawanapichatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainRoomActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etMsg;
    private Button btnSend;

    private Socket socket;
    private String TOKEN;
    private String ID;

    private RecyclerView rvChatBox;
    private RecyclerView.Adapter adapterChatBox;
    private RecyclerView.LayoutManager layoutManagerChatBox;
    private List<MsgBox> datasetChatBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_room);

        init();
        socketSetup();
    }

    private void init() {
        etMsg = findViewById(R.id.et_msg);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        //Recycle View
        rvChatBox = findViewById(R.id.rv_chat_box);
        rvChatBox.setHasFixedSize(true);

        layoutManagerChatBox = new LinearLayoutManager(this);
        rvChatBox.setLayoutManager(layoutManagerChatBox);

        adapterChatBox = new ChatBoxAdapter(this, initMsgBox());
        rvChatBox.setAdapter(adapterChatBox);
    }

    private List<MsgBox> initMsgBox() {
        datasetChatBox = new ArrayList<MsgBox>();
        return datasetChatBox;
    }

    private void addMsgToChatBox(String textId, String textMsg) {
        MsgBox msgBox = new MsgBox(textId, textMsg);
        datasetChatBox.add(msgBox);
        adapterChatBox = new ChatBoxAdapter(this, datasetChatBox);
        rvChatBox.setAdapter(adapterChatBox);
        rvChatBox.scrollToPosition(datasetChatBox.size() - 1);
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

    @Override
    public void onClick(View v) {
        if (v.getId()==btnSend.getId()){
            JSONObject data = new JSONObject();
            try {
                data.put("token", TOKEN);
                data.put("msg", etMsg.getText().toString());
                socket.emit("send", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
