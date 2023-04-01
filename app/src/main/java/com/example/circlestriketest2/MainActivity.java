package com.example.circlestriketest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    Button red,blue;
    Socket msocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        red= findViewById(R.id.b1);
        blue= findViewById(R.id.b2);
        TextView txt = findViewById(R.id.textView);
        try {
            SocketIOManager.init("https://d8ec-106-79-206-142.in.ngrok.io/");
            msocket = SocketIOManager.getInstance().getSocket();
            msocket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }



        //msocket.emit("act1");

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent);
                finish();

            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });


        msocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("client :","connected");
            }
        });
        msocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("disconnect","client disconnected");
            }
        });
        msocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("Error :", "error connecting");
            }
        });


    }
}