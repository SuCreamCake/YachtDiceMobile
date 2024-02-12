package com.example.lastproject;// ScoreboardActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ScoreboardActivity extends AppCompatActivity {
    private ArrayList<Integer> diceValues;
    private Button continueButton;
    private int player1num = 0;
    private int player2num = 0;
    private int player1total = 0;
    private int player2total = 0;
    private boolean isClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        // 주사위 값을 받아오기
        Intent intent = getIntent();
        if (intent != null) {
            diceValues = intent.getIntegerArrayListExtra("diceValues");
            int [] player1score = intent.getIntArrayExtra("player1score");
            int [] player2score = intent.getIntArrayExtra("player2score");
            int [] player1check = intent.getIntArrayExtra("player1check");
            int [] player2check = intent.getIntArrayExtra("player2check");
            int turn = intent.getIntExtra("turn", 1);
            int player = intent.getIntExtra("player", 1);
            if (diceValues != null) {
                TextView diceValuesTextView = findViewById(R.id.diceValuesTextView);
                TextView turnTextView = findViewById(R.id.turn);
                diceValuesTextView.setText("Dice Values: " + diceValues.toString());
                turnTextView.setText("player : " + player + "  turn : "+ turn + "/13");

                // YachtGame 객체 생성
                YachtGame yachtGame = new YachtGame();
                //보너스와 총 점수 계산
                bonusAndTotal();
                // 주사위 값으로 점수 계산
                YachtGame.Board board = yachtGame.getScore(diceValues.toArray(new Integer[0]));
                setTextViewValue(R.id.player1acesresult, String.valueOf(player1score[1]));
                setTextViewValue(R.id.player1Deucesresult, String.valueOf(player1score[2]));
                setTextViewValue(R.id.player1Threesresult, String.valueOf(player1score[3]));
                setTextViewValue(R.id.player1Foursresult, String.valueOf(player1score[4]));
                setTextViewValue(R.id.player1Fivesresult, String.valueOf(player1score[5]));
                setTextViewValue(R.id.player1Sixesresult, String.valueOf(player1score[6]));
                setTextViewValue(R.id.player1Bouns, String.valueOf(player1num));
                setTextViewValue(R.id.player1Choiceresult, String.valueOf(player1score[8]));
                setTextViewValue(R.id.player1Fourcardresult, String.valueOf(player1score[9]));
                setTextViewValue(R.id.player1FullHouseresult, String.valueOf(player1score[10]));
                setTextViewValue(R.id.player1Smallresult, String.valueOf(player1score[11]));
                setTextViewValue(R.id.player1Largeresult, String.valueOf(player1score[12]));
                setTextViewValue(R.id.player1Yachtresult, String.valueOf(player1score[13]));
                setTextViewValue(R.id.player1Total, String.valueOf(player1total));


                setTextViewValue(R.id.player2acesresult, String.valueOf(player2score[1]));
                setTextViewValue(R.id.player2Deucesresult, String.valueOf(player2score[2]));
                setTextViewValue(R.id.player2Threesresult, String.valueOf(player2score[3]));
                setTextViewValue(R.id.player2Foursresult, String.valueOf(player2score[4]));
                setTextViewValue(R.id.player2Fivesresult, String.valueOf(player2score[5]));
                setTextViewValue(R.id.player2Sixesresult, String.valueOf(player2score[6]));
                setTextViewValue(R.id.player2Bouns, String.valueOf(player2num));
                setTextViewValue(R.id.player2Choiceresult, String.valueOf(player2score[8]));
                setTextViewValue(R.id.player2Fourcardresult, String.valueOf(player2score[9]));
                setTextViewValue(R.id.player2FullHouseresult, String.valueOf(player2score[10]));
                setTextViewValue(R.id.player2Smallresult, String.valueOf(player2score[11]));
                setTextViewValue(R.id.player2Largeresult, String.valueOf(player2score[12]));
                setTextViewValue(R.id.player2Yachtresult, String.valueOf(player2score[13]));
                setTextViewValue(R.id.player2Total, String.valueOf(player2total));
                if(turn != 13) {
                    if (player == 1) {
                        if(player1check[1] != 1){setEditTextClickListener(R.id.player1Button1);}
                        if(player1check[2] != 1){setEditTextClickListener(R.id.player1Button2);}
                        if(player1check[3] != 1){setEditTextClickListener(R.id.player1Button3);}
                        if(player1check[4] != 1){setEditTextClickListener(R.id.player1Button4);}
                        if(player1check[5] != 1){setEditTextClickListener(R.id.player1Button5);}
                        if(player1check[6] != 1){setEditTextClickListener(R.id.player1Button6);}
                        if(player1check[8] != 1){setEditTextClickListener(R.id.player1Button7);}
                        if(player1check[9] != 1){set    EditTextClickListener(R.id.player1Button8);}
                        if(player1check[10] != 1){setEditTextClickListener(R.id.player1Button9);}
                        if(player1check[11] != 1){setEditTextClickListener(R.id.player1Button10);}
                        if(player1check[12] != 1){setEditTextClickListener(R.id.player1Button11);}
                        if(player1check[13] != 1){setEditTextClickListener(R.id.player1Button12);}


                        setTextViewValue(R.id.player1Button1, String.valueOf(board.getOnes()));
                        setTextViewValue(R.id.player1Button2, String.valueOf(board.getTwos()));
                        setTextViewValue(R.id.player1Button3, String.valueOf(board.getThrees()));
                        setTextViewValue(R.id.player1Button4, String.valueOf(board.getFours()));
                        setTextViewValue(R.id.player1Button5, String.valueOf(board.getFives()));
                        setTextViewValue(R.id.player1Button6, String.valueOf(board.getSixes()));
                        setTextViewValue(R.id.player1Button7, String.valueOf(board.getChoice()));
                        setTextViewValue(R.id.player1Button8, String.valueOf(board.getFourCard()));
                        setTextViewValue(R.id.player1Button9, String.valueOf(board.getFullHouse()));
                        setTextViewValue(R.id.player1Button10, String.valueOf(board.getSmallStraight()));
                        setTextViewValue(R.id.player1Button11, String.valueOf(board.getLargeStraight()));
                        setTextViewValue(R.id.player1Button12, String.valueOf(board.getYacht()));

                    } else {
                        if(player2check[1] != 1){setEditTextClickListener(R.id.player2Button1);}
                        if(player2check[2] != 1){setEditTextClickListener(R.id.player2Button2);}
                        if(player2check[3] != 1){setEditTextClickListener(R.id.player2Button3);}
                        if(player2check[4] != 1){setEditTextClickListener(R.id.player2Button4);}
                        if(player2check[5] != 1){setEditTextClickListener(R.id.player2Button5);}
                        if(player2check[6] != 1){setEditTextClickListener(R.id.player2Button6);}
                        if(player2check[8] != 1){setEditTextClickListener(R.id.player2Button7);}
                        if(player2check[9] != 1){setEditTextClickListener(R.id.player2Button8);}
                        if(player2check[10] != 1){setEditTextClickListener(R.id.player2Button9);}
                        if(player2check[11] != 1){setEditTextClickListener(R.id.player2Button10);}
                        if(player2check[12] != 1){setEditTextClickListener(R.id.player2Button11);}
                        if(player2check[13] != 1){setEditTextClickListener(R.id.player2Button12);}


                        setTextViewValue(R.id.player2Button1, String.valueOf(board.getOnes()));
                        setTextViewValue(R.id.player2Button2, String.valueOf(board.getTwos()));
                        setTextViewValue(R.id.player2Button3, String.valueOf(board.getThrees()));
                        setTextViewValue(R.id.player2Button4, String.valueOf(board.getFours()));
                        setTextViewValue(R.id.player2Button5, String.valueOf(board.getFives()));
                        setTextViewValue(R.id.player2Button6, String.valueOf(board.getSixes()));
                        setTextViewValue(R.id.player2Button7, String.valueOf(board.getChoice()));
                        setTextViewValue(R.id.player2Button8, String.valueOf(board.getFourCard()));
                        setTextViewValue(R.id.player2Button9, String.valueOf(board.getFullHouse()));
                        setTextViewValue(R.id.player2Button10, String.valueOf(board.getSmallStraight()));
                        setTextViewValue(R.id.player2Button11, String.valueOf(board.getLargeStraight()));
                        setTextViewValue(R.id.player2Button12, String.valueOf(board.getYacht()));


                    }
                }else
                {
                    if(player1total > player2total)
                    {
                        TextView victoryText = findViewById(R.id.victoryText);
                        TextView victoryText2 = findViewById(R.id.victoryText2);
                        victoryText.setText("player1 : " + player1total + "  player2 : "+ player2total);
                        victoryText2.setText("player1이(가) 승리하였습니다.");
                    }else if(player1total < player2total)
                    {
                        TextView victoryText = findViewById(R.id.victoryText);
                        TextView victoryText2 = findViewById(R.id.victoryText2);
                        victoryText.setText("player1 : " + player1total + "  player2 : "+ player2total);
                        victoryText2.setText("player2이(가) 승리하였습니다.");
                    }else
                    {
                        TextView victoryText = findViewById(R.id.victoryText);
                        TextView victoryText2 = findViewById(R.id.victoryText2);
                        victoryText.setText("player1 : " + player1total + "  player2 : "+ player2total);
                        victoryText2.setText("무승부 입니다.");
                    }

                }
                continueButton = findViewById(R.id.continueButton);
                continueButton.setEnabled(false);
                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Intent를 생성하여 GameActivity를 시작
                        Intent gameIntent = new Intent(ScoreboardActivity.this, GameActivity.class);

                        // Intent를 통해 데이터를 GameActivity로 전달
                        gameIntent.putExtra("player1score", player1score);
                        gameIntent.putExtra("player2score", player2score);
                        gameIntent.putExtra("player1check",player1check);
                        gameIntent.putExtra("player2check",player2check);
                        gameIntent.putExtra("turn",turn);
                        gameIntent.putExtra("currentPlayer",player);

                        startActivity(gameIntent);
                        finish();
                    }
                });
            }
        }
    }
    private void bonusAndTotal(){
        Intent intent = getIntent();
        int [] player1score = intent.getIntArrayExtra("player1score");
        int [] player2score = intent.getIntArrayExtra("player2score");

        for(int i = 1; i < 7 ; i++)
        {
            player1num += player1score[i];
            player2num += player2score[i];
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
            player1total +=player1score[total];
            player2total +=player2score[total];
        }
    }

    // EditText의 값을 설정하는 메서드
    private void setTextViewValue(int textViewId, String value) {
        TextView textView = findViewById(textViewId);
        textView.setText(value);
    }

    private void setEditTextClickListener(int textViewId) {
        TextView textView = findViewById(textViewId);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isClicked != true) {
                    // 클릭된 EditText의 ID를 통해 분기 처리
                    handleEditTextClick(textViewId);

                    // 클릭 후에 TextView를 비활성화
                    textView.setEnabled(false);

                    // 텍스트 색상 변경 (원하는 색상으로 설정)
                    textView.setTextColor(getResources().getColor(android.R.color.holo_blue_light));

                    // 클릭 이벤트 제거
                    textView.setOnClickListener(null);
                    continueButton.setEnabled(true);
                    // 한 번 클릭되었음을 표시
                    isClicked = true;
                }else{
                    return;
                }
            }
        });
    }

    private void handleEditTextClick(int editTextId) {
        // EditText의 ID에 따라 다른 동작 수행
        YachtGame yachtGame = new YachtGame();
        // 주사위 값으로 점수 계산
        YachtGame.Board board = yachtGame.getScore(diceValues.toArray(new Integer[0]));
        Intent intent = getIntent();
        int [] player1score = intent.getIntArrayExtra("player1score");
        int [] player2score = intent.getIntArrayExtra("player2score");
        int [] player1check = intent.getIntArrayExtra("player1check");
        int [] player2check = intent.getIntArrayExtra("player2check");
        int player = intent.getIntExtra("player", 1);
        if(player == 1) {
            switch (editTextId) {
                case R.id.player1Button1:
                    player1score[1] = board.getOnes();
                    player1check[1] = 1;
                    setTextViewValue(R.id.player1acesresult, String.valueOf(player1score[1]));
                    break;
                case R.id.player1Button2:
                    player1score[2] = board.getTwos();
                    player1check[2] = 1;
                    setTextViewValue(R.id.player1Deucesresult, String.valueOf(player1score[2]));
                    break;
                case R.id.player1Button3:
                    player1score[3] = board.getThrees();
                    player1check[3] = 1;
                    setTextViewValue(R.id.player1Threesresult, String.valueOf(player1score[3]));
                    break;
                case R.id.player1Button4:
                    player1score[4] = board.getFours();
                    player1check[4] = 1;
                    setTextViewValue(R.id.player1Foursresult, String.valueOf(player1score[4]));
                    break;
                case R.id.player1Button5:
                    player1score[5] = board.getFives();
                    player1check[5] = 1;
                    setTextViewValue(R.id.player1Fivesresult, String.valueOf(player1score[5]));
                    break;
                case R.id.player1Button6:
                    player1score[6] = board.getSixes();
                    player1check[6] = 1;
                    setTextViewValue(R.id.player1Sixesresult, String.valueOf(player1score[6]));
                    break;
                case R.id.player1Button7:
                    player1score[8] = board.getChoice();
                    player1check[8] = 1;
                    setTextViewValue(R.id.player1Choiceresult, String.valueOf(player1score[8]));
                    break;
                case R.id.player1Button8:
                    player1score[9] = board.getFourCard();
                    player1check[9] = 1;
                    setTextViewValue(R.id.player1Fourcardresult, String.valueOf(player1score[9]));
                    break;
                case R.id.player1Button9:
                    player1score[10] = board.getFullHouse();
                    player1check[10] = 1;
                    setTextViewValue(R.id.player1FullHouseresult, String.valueOf(player1score[10]));
                    break;
                case R.id.player1Button10:
                    player1score[11] = board.getSmallStraight();
                    player1check[11] = 1;
                    setTextViewValue(R.id.player1Smallresult, String.valueOf(player1score[11]));
                    break;
                case R.id.player1Button11:
                    player1score[12] = board.getLargeStraight();
                    player1check[12] = 1;
                    setTextViewValue(R.id.player1Largeresult, String.valueOf(player1score[12]));
                    break;
                case R.id.player1Button12:
                    player1score[13] = board.getYacht();
                    player1check[13] = 1;
                    setTextViewValue(R.id.player1Yachtresult, String.valueOf(player1score[13]));
                    break;

                default:
                    // 기본 동작
                    break;
            }
        }else
        {
            switch (editTextId) {
                case R.id.player2Button1:
                    player2score[1] = board.getOnes();
                    player2check[1] = 1;
                    setTextViewValue(R.id.player2acesresult, String.valueOf(player2score[1]));
                    break;
                case R.id.player2Button2:
                    player2score[2] = board.getTwos();
                    player2check[2] = 1;
                    setTextViewValue(R.id.player2Deucesresult, String.valueOf(player2score[2]));
                    break;
                case R.id.player2Button3:
                    player2score[3] = board.getThrees();
                    player2check[3] = 1;
                    setTextViewValue(R.id.player2Threesresult, String.valueOf(player2score[3]));
                    break;
                case R.id.player2Button4:
                    player2score[4] = board.getFours();
                    player2check[4] = 1;
                    setTextViewValue(R.id.player2Foursresult, String.valueOf(player2score[4]));
                    break;
                case R.id.player2Button5:
                    player2score[5] = board.getFives();
                    player2check[5] = 1;
                    setTextViewValue(R.id.player2Fivesresult, String.valueOf(player2score[5]));
                    break;
                case R.id.player2Button6:
                    player2score[6] = board.getSixes();
                    player2check[6] = 1;
                    setTextViewValue(R.id.player2Sixesresult, String.valueOf(player2score[6]));
                    break;
                case R.id.player2Button7:
                    player2score[8] = board.getChoice();
                    player2check[8] = 1;
                    setTextViewValue(R.id.player2Choiceresult, String.valueOf(player2score[8]));
                    break;
                case R.id.player2Button8:
                    player2score[9] = board.getFourCard();
                    player2check[9] = 1;
                    setTextViewValue(R.id.player2Fourcardresult, String.valueOf(player2score[9]));
                    break;
                case R.id.player2Button9:
                    player2score[10] = board.getFullHouse();
                    player2check[10] = 1;
                    setTextViewValue(R.id.player2FullHouseresult, String.valueOf(player2score[10]));
                    break;
                case R.id.player2Button10:
                    player2score[11] = board.getSmallStraight();
                    player2check[11] = 1;
                    setTextViewValue(R.id.player2Smallresult, String.valueOf(player2score[11]));
                    break;
                case R.id.player2Button11:
                    player2score[12] = board.getLargeStraight();
                    player2check[12] = 1;
                    setTextViewValue(R.id.player2Largeresult, String.valueOf(player2score[12]));
                    break;
                case R.id.player2Button12:
                    player2score[13] = board.getYacht();
                    player2check[13] = 1;
                    setTextViewValue(R.id.player2Yachtresult, String.valueOf(player2score[13]));
                    break;

                default:
                    // 기본 동작
                    break;
            }
        }
    }
}