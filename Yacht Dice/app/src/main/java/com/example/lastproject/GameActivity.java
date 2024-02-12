package com.example.lastproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameActivity extends AppCompatActivity {


    private final List<Integer> diceImages = new ArrayList<Integer>() {{
        add(R.drawable.dice1);
        add(R.drawable.dice2);
        add(R.drawable.dice3);
        add(R.drawable.dice4);
        add(R.drawable.dice5);
        add(R.drawable.dice6);
    }};

    private final List<Integer> savedDiceImages = new ArrayList<Integer>(){{
        add(R.drawable.selectdice1);
        add(R.drawable.selectdice2);
        add(R.drawable.selectdice3);
        add(R.drawable.selectdice4);
        add(R.drawable.selectdice5);
        add(R.drawable.selectdice6);
    }};

    private List<Integer> currentDiceValues = new ArrayList<Integer>() {{
        add(1);
        add(1);
        add(1);
        add(1);
        add(1);
    }};

    private List<Boolean> diceSaved = new ArrayList<Boolean>() {{
        add(false);
        add(false);
        add(false);
        add(false);
        add(false);
    }};

    private int rollsLeft = 3; // 남은 굴릴 수 있는 횟수
    private TextView rollCountTextView;
    private Button scoreboardButton;
    private Button confirmButton;
    private Button saveButton;
    private Button rollButton;
    private int currentPlayer = 1; // 현재 플레이어 (1 또는 2)
    private int turn = 1; //턴수
    private int player1num = 0;
    private int player2num = 0;
    private int player1total = 0;
    private int player2total = 0;
    private MenuItem menu_item1;
    private MenuItem menu_item2;
    int[] receivedPlayer1Scores = new int[14];
    int[] receivedPlayer2Scores = new int[14];
    int[] receivedPlayer1check = new int[14];
    int[] receivedPlayer2check = new int[14];

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        menu_item1 = menu.findItem(R.id.menu_item1);
        menu_item2 = menu.findItem(R.id.menu_item2);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                rollButton.setEnabled(true);
                confirmButton.setEnabled(true);
                scoreboardButton.setEnabled(true);
                return true;
            case R.id.menu_item2:
                showPlayerScoresDialog();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        rollButton = findViewById(R.id.rollButton);
        ImageView diceImage1 = findViewById(R.id.diceImage1);
        ImageView diceImage2 = findViewById(R.id.diceImage2);
        ImageView diceImage3 = findViewById(R.id.diceImage3);
        ImageView diceImage4 = findViewById(R.id.diceImage4);
        ImageView diceImage5 = findViewById(R.id.diceImage5);


        final List<ImageView> diceImagesList = new ArrayList<ImageView>() {{
            add(diceImage1);
            add(diceImage2);
            add(diceImage3);
            add(diceImage4);
            add(diceImage5);
        }};

        // 주사위 굴리기 버튼 클릭 이벤트 처리
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rollsLeft > 0) {
                    rollsLeft--;
                    rollCountTextView = findViewById(R.id.rollCountTextView);
                    updateRollsLeft();
                    rollDice(diceImagesList);
                }
            }
        });

        // 각 주사위 이미지 클릭 이벤트 처리
        for (int i = 0; i < diceImagesList.size(); i++) {
            final int index = i;
            diceImagesList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleDice(index, diceImagesList.get(index), rollButton);
                }
            });
        }
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                for (int i = 0; i < diceImagesList.size(); i++) {
                    diceImagesList.get(i).setImageResource(savedDiceImages.get(currentDiceValues.get(i) - 1));
                    diceSaved.set(i, true);
                }
                rollsLeft = 0; // 모든 주사위를 확정하면 더 이상 굴릴 수 없음
                updateRollsLeft();
                rollButton.setEnabled(false); // 굴리기 버튼 비활성화
                scoreboardButton.setEnabled(true); // 족보 버튼 활성화
            }
        });

        scoreboardButton = findViewById(R.id.scoreboardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rollsLeft == 0) {
                    // 주사위 굴리기 기회를 모두 소진한 경우에만 족보 액티비티로 이동
                    Intent intent = new Intent(GameActivity.this, ScoreboardActivity.class);
                    intent.putIntegerArrayListExtra("diceValues", new ArrayList<>(currentDiceValues));
                    intent.putExtra("player", currentPlayer);
                    intent.putExtra("player1score", receivedPlayer1Scores);
                    intent.putExtra("player2score", receivedPlayer2Scores);
                    intent.putExtra("player1check", receivedPlayer1check);
                    intent.putExtra("player2check", receivedPlayer2check);
                    intent.putExtra("turn", turn);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(GameActivity.this, "주사위 굴리기를 모두 사용한 후에 족보를 확인할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rollButton.setEnabled(false);
        confirmButton.setEnabled(false);
        scoreboardButton.setEnabled(false);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollButton.setEnabled(true);
                confirmButton.setEnabled(true);
                scoreboardButton.setEnabled(true);
                menu_item1.setEnabled(false);
                menu_item2.setEnabled(true);
                Intent intent = getIntent();
                receivedPlayer1Scores = intent.getIntArrayExtra("player1score");
                receivedPlayer2Scores = intent.getIntArrayExtra("player2score");
                receivedPlayer1check = intent.getIntArrayExtra("player1check");
                receivedPlayer2check = intent.getIntArrayExtra("player2check");
                turn = intent.getIntExtra("turn", 1);
                currentPlayer = intent.getIntExtra("currentPlayer", 1);
                if (currentPlayer == 2) {
                    turn++;
                }
                bonusAndTotal();
                switchPlayer();
            }
        });
    }

    // 주사위 굴리기 함수
    private void rollDice(List<ImageView> diceImagesList) {
        for (int i = 0; i < diceImagesList.size(); i++) {
            // 저장된 주사위는 변경되지 않도록 함
            if (!diceSaved.get(i)) {
                int randomValue = new Random().nextInt(6) + 1;
                currentDiceValues.set(i, randomValue);
                diceImagesList.get(i).setImageResource(diceImages.get(randomValue - 1));
            }
        }
    }

    // 주사위 클릭 시 숫자 저장 또는 굴리기 가능 상태로 전환
    private void toggleDice(int index, ImageView diceImage, Button rollButton) {
        if (rollsLeft > 0) {
            if (diceSaved.get(index)) {
                // 저장된 주사위를 다시 클릭할 경우 굴리기 가능 상태로 전환
                diceSaved.set(index, false);
                rollButton.setEnabled(true);
                diceImage.setImageResource(diceImages.get(currentDiceValues.get(index) - 1));
            } else {
                // 주사위 클릭 시 현재 숫자에 해당하는 이미지로 설정 및 저장
                diceImage.setImageResource(savedDiceImages.get(currentDiceValues.get(index) - 1));
                diceSaved.set(index, true);

                // 모든 주사위가 저장되었을 때 굴리기 버튼 비활성화
                if (allDiceSaved()) {
                    rollButton.setEnabled(false);

                }
            }
        }
    }

    // 모든 주사위가 저장되었는지 확인
    private boolean allDiceSaved() {
        for (boolean saved : diceSaved) {
            if (!saved) {
                return false;
            }
        }
        return true;
    }

    // 현재 플레이어 변경
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;

    }

    private void updateRollsLeft() {
        rollCountTextView.setText("Rolls Left: " + rollsLeft);
    }

    private void showPlayerScoresDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(currentPlayer == 1) {
            builder.setTitle("Player 1 Scores");

            // Convert the array to a string for display
            StringBuilder scoresString = new StringBuilder();
            for (int i = 1; i < 15; i++) {
                if (i == 1 && receivedPlayer1check[i] == 1) {
                    scoresString.append("1 Aces: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 2 && receivedPlayer1check[i] == 1) {
                    scoresString.append("2 Deuces: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 3 && receivedPlayer1check[i] == 1) {
                    scoresString.append("3 Threes: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 4 && receivedPlayer1check[i] == 1) {
                    scoresString.append("4 Fours: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 5 && receivedPlayer1check[i] == 1) {
                    scoresString.append("5 Fives: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 6 && receivedPlayer1check[i] == 1) {
                    scoresString.append("6 Sixes: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 7) {
                    scoresString.append("Bouns: ").append(player1num).append("/63 \n");
                }
                if (i == 8 && receivedPlayer1check[i] == 1) {
                    scoresString.append("Choice: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 9 && receivedPlayer1check[i] == 1) {
                    scoresString.append("4 of a Kind: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 10 && receivedPlayer1check[i] == 1) {
                    scoresString.append("Full House: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 11 && receivedPlayer1check[i] == 1) {
                    scoresString.append("S. Straight: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 12 && receivedPlayer1check[i] == 1) {
                    scoresString.append("L. Straight: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 13 && receivedPlayer1check[i] == 1) {
                    scoresString.append("Yacht: ").append(receivedPlayer1Scores[i]).append("\n");
                }
                if (i == 14) {
                    scoresString.append("total: ").append(player1total).append("\n");
                }
            }

            builder.setMessage(scoresString.toString());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing or add any additional actions upon clicking OK
                }
            });

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            builder.setTitle("Player 2 Scores");

            // Convert the array to a string for display
            StringBuilder scoresString = new StringBuilder();
            for (int i = 1; i < 15; i++) {
                if (i == 1 && receivedPlayer2check[i] == 1) {
                    scoresString.append("1 Aces: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 2 && receivedPlayer2check[i] == 1) {
                    scoresString.append("2 Deuces: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 3 && receivedPlayer2check[i] == 1) {
                    scoresString.append("3 Threes: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 4 && receivedPlayer2check[i] == 1) {
                    scoresString.append("4 Fours: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 5 && receivedPlayer2check[i] == 1) {
                    scoresString.append("5 Fives: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 6 && receivedPlayer2check[i] == 1) {
                    scoresString.append("6 Sixes: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 7) {
                    scoresString.append("Bouns: ").append(player2num).append("/63 \n");
                }
                if (i == 8 && receivedPlayer2check[i] == 1) {
                    scoresString.append("Choice: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 9 && receivedPlayer2check[i] == 1) {
                    scoresString.append("4 of a Kind: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 10 && receivedPlayer2check[i] == 1) {
                    scoresString.append("Full House: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 11 && receivedPlayer2check[i] == 1) {
                    scoresString.append("S. Straight: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 12 && receivedPlayer2check[i] == 1) {
                    scoresString.append("L. Straight: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 13 && receivedPlayer2check[i] == 1) {
                    scoresString.append("Yacht: ").append(receivedPlayer2Scores[i]).append("\n");
                }
                if (i == 14) {
                    scoresString.append("total: ").append(player2total).append("\n");
                }
            }

            builder.setMessage(scoresString.toString());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing or add any additional actions upon clicking OK
                }
            });

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    private void bonusAndTotal(){


        for(int i = 1; i < 7 ; i++)
        {
            player1num += receivedPlayer1Scores[i];
            player2num += receivedPlayer2Scores[i];
        }
        if(player1num >= 63)
        {
            player1total +=35;
        }
        if(player2num >= 63)
        {
            player2total +=35;
        }
        for(int total = 1; total < 14; total++)
        {
            player1total +=receivedPlayer1Scores[total];
            player2total +=receivedPlayer2Scores[total];
        }
    }
}


