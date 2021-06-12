package com.example.task1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Iterator;


@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    Button ans_0, ans_1, ans_2, ans_3, bt_menu;
    View lt_bg;
    TextView tv_qn, tv_level, tv_score, tv_endscore;
    ProgressBar progressBar;

    int timeround = 15;
    int score=0, timereamin=timeround, level = 0;

    boolean usesecondtimer = false;



    String [] dayarray = {"MON", "TUE", "WED", "THU", "FRI", "SAT","SUN"};

    LocalDate start = LocalDate.of(1970, Month.JANUARY, 1);
    LocalDate end = LocalDate.of(2021, Month.DECEMBER, 31);

    GenQn Qn = new GenQn(start, end);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.v("bruh", "water");
        setContentView(R.layout.activity_main);
        lt_bg = findViewById(R.id.lt_background);
        ans_0 = findViewById(R.id.ans_0);
        ans_1 = findViewById(R.id.ans_1);
        ans_2 = findViewById(R.id.ans_2);
        ans_3 = findViewById(R.id.ans_3);
        tv_level = findViewById(R.id.tv_level);
        tv_qn = findViewById(R.id.tv_qn);
        tv_score = findViewById((R.id.tv_score));
        progressBar = findViewById(R.id.CHUNGUSTIMER);
        tv_endscore = findViewById(R.id.tv_endScore);
        bt_menu = findViewById(R.id.bt_menu);

        tv_endscore.setVisibility(View.INVISIBLE);
        bt_menu.setVisibility(View.INVISIBLE);

        final Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        //timer
        CountDownTimer timer = new CountDownTimer(timeround*1000, 1000 ) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("bundle",Integer.toString(timereamin));
               // timereamin = (int)(millisUntilFinished/1000);
                progressBar.setProgress(timereamin);
                timereamin--;
                if(timereamin <0){
                    gameOver();
                    cancel();
                }
            }

            @Override
            public void onFinish() {

                gameOver();
            }
        };



     if(savedInstanceState != null){
            usesecondtimer = true;
            timeround = savedInstanceState.getInt("timeremain");
            timereamin = timeround;
            Log.v("bundle",Integer.toString(timeround));
            new CountDownTimer(timeround*1000, 1000 ) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(!usesecondtimer) {
                        cancel();
                    }
                    Log.v("bundle",Integer.toString(timereamin));
                    // timereamin = (int)(millisUntilFinished/1000);
                    progressBar.setProgress(timereamin);
                    timereamin--;

                    if(timereamin <0){
                        gameOver();
                        cancel();
                    }
                }

                @Override
                public void onFinish() {

                    gameOver();
                }
            }.start();

        }
        else{
            timer.start();
         next_qn();
     }


        //button listeners for answer buttons
        View.OnClickListener answerCl = v -> {
            Button bt_ans = (Button) v;
            Handler handler = new Handler();


            if(Qn.CheckAnswer(bt_ans.getText().toString())){
                vibrator.vibrate(40);
                score += 15;
                tv_score.setText(Integer.toString(score));
                lt_bg.setBackgroundColor(Color.parseColor("#00897B"));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lt_bg.setBackgroundColor(Color.parseColor("#212121"));
                    }
                }, 500);
                next_qn();
                timer.cancel();
                timer.start();


            }
            else{
                vibrator.vibrate(100);
                score -= 5;

                timereamin-=3;

                tv_score.setText(Integer.toString(score));

                lt_bg.setBackgroundColor(Color.parseColor("#C62828"));


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lt_bg.setBackgroundColor(Color.parseColor("#212121"));



                    }

                }, 2000);
            }

        };


        for (Button button : Arrays.asList(ans_0, ans_1, ans_2, ans_3)) {
            button.setOnClickListener(answerCl);
        }

        //end Menu listener
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        Log.v("bundle", "null");

        outState.putInt("level",level);
        outState.putInt("score",score);
        outState.putInt("timeremain",timereamin);
        outState.putInt("ans", Qn.getAnswer());
        outState.putCharSequence("ans_0",ans_0.getText());
        outState.putCharSequence("ans_1",ans_1.getText());
        outState.putCharSequence("ans_2",ans_2.getText());
        outState.putCharSequence("ans_3",ans_3.getText());
        outState.putCharSequence("qn",tv_qn.getText());

        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        level = savedInstanceState.getInt("level");
        score = savedInstanceState.getInt("score");
        ans_0.setText(savedInstanceState.getCharSequence("ans_0"));
        ans_1.setText(savedInstanceState.getCharSequence("ans_1"));
        ans_2.setText(savedInstanceState.getCharSequence("ans_2"));
        ans_3.setText(savedInstanceState.getCharSequence("ans_3"));
        tv_qn.setText(savedInstanceState.getCharSequence("qn"));
        Qn.setAnswer(savedInstanceState.getInt("ans"));
        tv_level.setText("Level - " + level);
        tv_score.setText(Integer.toString(score));
    }

    public void saveDataInt(int data) {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HighScore",data);
        editor.commit();
    }

    public int getDataInt() {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScore",MODE_PRIVATE);
        return sharedPreferences.getInt("HighScore",0);
    }

    private void gameOver() {
        if(getDataInt() < score) {
            saveDataInt(score);
            tv_endscore.setText("High Score : " + score);
        }

        else {
            tv_endscore.setText(("Score : " + score));
        }

        tv_endscore.setVisibility(View.VISIBLE);
        bt_menu.setVisibility(View.VISIBLE);

    }

    private void next_qn() {
        timereamin = 15;
        usesecondtimer = false;
        timeround = 15;
        Qn.NewQn(start, end);
        level++;
        qn_display();

    }

    private void qn_display(){
        tv_qn.setText(Qn.getQn().toString());
        tv_level.setText("Level - " + level);
        tv_score.setText(Integer.toString(score));

        Iterator<Integer> itr = Qn.getSet_ans().iterator();

        for (Button button : Arrays.asList(ans_0, ans_1, ans_2, ans_3)) {
            button.setText(dayarray[itr.next()]);
        }
    }


}


