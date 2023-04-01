package com.example.circlestriketest2;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity3 extends AppCompatActivity {

    private LinearLayout rootLayout;
    Handler handler;
    private LinearLayout circleLayout;
    private LinearLayout keyLayout;
    private Map<String, JSONArray> map = new LinkedHashMap<>();

    Socket msocket ;
    JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        handler= new Handler();

        msocket= SocketIOManager.getInstance().getSocket();
        msocket.connect();
        msocket.emit("act3");

        msocket.on("data2", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                object = (JSONObject) args[0];
                Iterator<String> keys = object.keys();

                while(keys.hasNext()){
                    String key = keys.next();
                    if(key.charAt(0)=='b'){
                        try {
                            JSONArray arr = object.getJSONArray(key);
                            map.put(key,arr);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    Log.d(key, String.valueOf(map.get(key)));
                }

            }
        });
        // Get the root layout
        rootLayout = findViewById(R.id.root_layout);

        // Get the circle layout
        circleLayout = findViewById(R.id.circlelayout);

        // Get the key layout
        keyLayout = findViewById(R.id.keylayout);

        // Create key buttons

        msocket.on("data2", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (String key : map.keySet()) {
                    Button keyButton = new Button(MainActivity3.this);
                    keyButton.setText(key);
                    keyButton.setTag(map.get(key));
                    keyButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                    keyButton.setAllCaps(false);
                    keyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            JSONArray array = (JSONArray) v.getTag();
                            // Remove existing circle buttons
                            circleLayout.removeAllViews();

                            // Create circle buttons for values
                            for (int i = 0; i < array.length(); i++) {
                                Button valueButton = new Button(MainActivity3.this);
                                try {
                                    valueButton.setText(String.valueOf(object.getJSONArray(key).getInt(i)+1));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                valueButton.setBackgroundResource(R.drawable.circle_button_bg);
                                valueButton.setTextColor(Color.WHITE);
                                valueButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                valueButton.setAllCaps(false);

                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_size),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_size)
                                );
                                layoutParams.setMargins(
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin)
                                );
                                valueButton.setLayoutParams(layoutParams);

                                valueButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        msocket.emit("send_coin_move",new String[]{key,valueButton.getText().toString()});
                                        circleLayout.removeAllViews();
                                    }
                                });
                                circleLayout.addView(valueButton);
                            }
                        }
                    });


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            keyLayout.addView(keyButton);
                        }
                    });
                }

            }
        });
        msocket.on("dataupdate", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                object = (JSONObject) args[0];
                Iterator<String> keys = object.keys();

                while(keys.hasNext()) {
                    String key = keys.next();
                    if (key.charAt(0) == 'b') {
                        try {
                            JSONArray arr = object.getJSONArray(key);
                            map.put(key, arr);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        keyLayout.removeAllViews();
                    }
                });
                for (String key : map.keySet()) {
                    Button keyButton = new Button(MainActivity3.this);
                    keyButton.setText(key);
                    keyButton.setTag(map.get(key));
                    keyButton.setAllCaps(false);
                    keyButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                    keyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            JSONArray array = (JSONArray) v.getTag();

                            // Remove existing circle buttons
                            circleLayout.removeAllViews();

                            // Create circle buttons for values
                            for (int i = 0; i < array.length(); i++) {
                                Button valueButton = new Button(MainActivity3.this);
                                try {
                                    valueButton.setText(String.valueOf(object.getJSONArray(key).getInt(i)+1));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                valueButton.setBackgroundResource(R.drawable.circle_button_bg);
                                valueButton.setTextColor(Color.WHITE);
                                valueButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                valueButton.setAllCaps(false);

                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_size),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_size)
                                );
                                layoutParams.setMargins(
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin),
                                        getResources().getDimensionPixelSize(R.dimen.circle_button_margin)
                                );
                                valueButton.setLayoutParams(layoutParams);

                                valueButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        msocket.emit("send_coin_move",new String[]{key,valueButton.getText().toString()});
                                        circleLayout.removeAllViews();
                                    }
                                });
                                circleLayout.addView(valueButton);
                            }
                        }
                    });


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            keyLayout.addView(keyButton);
                        }
                    });
                }

            }
        });

    }

}

