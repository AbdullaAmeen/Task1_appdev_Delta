package com.example.task1;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


public class GenQn {
    private LocalDate qn;
    private LocalDate start;
    private LocalDate end;
    private int answer;
    private String [] dayarray = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private Set<Integer> set_ans = new HashSet<Integer>();

    Random rand = new Random();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GenQn(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        this.qn = between(start, end);
        this.answer = qn.getDayOfWeek().getValue() - 1;
        set_ans.add(answer);
        for(int i=0; set_ans.size()<4;i++)
            set_ans.add((3+rand.nextInt(6))%7);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void NewQn(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        this.qn = between(start, end);
        this.answer = qn.getDayOfWeek().getValue() - 1;
        set_ans.clear();
        set_ans.add(answer);
        for(int i=0; set_ans.size()<4;i++)
            set_ans.add((3+rand.nextInt(6))%7);

    }

    public Boolean CheckAnswer(String answ)
    {
        return answ.equals(dayarray[answer]);
    }





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static LocalDate between(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }

   /* public static boolean CheckAnswer() {

    }*/

    public LocalDate getQn() {
        return qn;
    }


    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }


    public Set<Integer> getSet_ans() {
        return set_ans;
    }

    public void setSet_ans(Set<Integer> set_ans) {
        this.set_ans = set_ans;
    }
}
