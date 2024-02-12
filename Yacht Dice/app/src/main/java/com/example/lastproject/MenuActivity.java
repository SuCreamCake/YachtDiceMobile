package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 시작 버튼 클릭 시
        Button btnStartGame = findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 게임 액티비티로 이동
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        // 규칙 버튼 클릭 시
        Button btnRules = findViewById(R.id.btnRules);
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 규칙 액티비티로 이동
                Intent intent = new Intent(MenuActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });

        // 종료 버튼 클릭 시
        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 앱 종료
                finish();
            }
        });
    }
}