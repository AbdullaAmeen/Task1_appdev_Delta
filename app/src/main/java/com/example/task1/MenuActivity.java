package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button bt_play, bt_hs, bt_exit;
        TextView tv_hs;

        bt_exit = findViewById(R.id.bt_exit);
        bt_play = findViewById(R.id.bt_play);

        tv_hs = findViewById(R.id.tv_hs);

        tv_hs.setText(""+ getDataInt());

        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();

            }
        });

        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.exit(0);
            }
        });


    }
    public void openMainActivity() {
        Intent intent = new Intent(this,MainActivity.class );
        startActivity(intent);

    }

    public int getDataInt() {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScore",MODE_PRIVATE);
        return sharedPreferences.getInt("HighScore",0);
    }

}