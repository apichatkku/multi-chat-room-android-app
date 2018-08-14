package com.api.tawan.tawanapichatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private Button btnLogin;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        socketSetup();
    }

    private void init() {
        etName = findViewById(R.id.et_name);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    private void socketSetup() {
        socket = SocketSingleton.getInstance().getSocket();
        socket.on("login", socketOnLogin);
    }


    @Override
    public void onClick(View v) {
        if (btnLogin.getId() == v.getId()) {
            String id = etName.getText().toString();
            try {
                JSONObject data = new JSONObject("{id:'" + id + "'}");
                socket.emit("login", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Emitter.Listener socketOnLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        if (data.getBoolean("status")) {
                            SocketSingleton.getInstance().setToken(data.getString("token"));
                            SocketSingleton.getInstance().setId(data.getString("id"));
                            Intent intent = new Intent(MainActivity.this, MainRoomActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
