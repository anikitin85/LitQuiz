package com.gmail.anikitin85.litquiz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int result, currentQuestion;
    String[] answers, questions, options;
    TextView question, answer1, answer2, answer3, answer4;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = (TextView) findViewById(R.id.question);
        answer1 = (TextView) findViewById(R.id.answer_1);
        answer2 = (TextView) findViewById(R.id.answer_2);
        answer3 = (TextView) findViewById(R.id.answer_3);
        answer4 = (TextView) findViewById(R.id.answer_4);
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);

        questions = new String[]{getString(R.string.quote1),
                getString(R.string.quote2), getString(R.string.quote3),
                getString(R.string.quote4), getString(R.string.quote5),
                getString(R.string.quote6), getString(R.string.quote7),
                getString(R.string.quote8), getString(R.string.quote9),
                getString(R.string.quote10)};
        answers = new String[]{getString(R.string.name1),
                getString(R.string.name2), getString(R.string.name3),
                getString(R.string.name4), getString(R.string.name5),
                getString(R.string.name6), getString(R.string.name7),
                getString(R.string.name8), getString(R.string.name9),
                getString(R.string.name10)};
        result = 0;
        currentQuestion = 0;
        setQuestion(0);
    }

    public void init() {
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    private void setQuestion(int questionNumber) {
        String questionText = getString(R.string.pre, questionNumber + 1) + "\n\"" + questions[questionNumber] + "\"";
        question.setText(questionText);
        randomizeAnswers();
        answer1.setText(options[0]);
        answer2.setText(options[1]);
        answer3.setText(options[2]);
        answer4.setText(options[3]);
    }

    @Override
    public void onClick(View view) {
        String userAnswer = ((TextView) view).getText().toString();
        if (userAnswer.equals(getString(R.string.play_again))) {
            init();
        }
        if (userAnswer.equals(answers[currentQuestion])) {
            result++;
        }
        if (currentQuestion < 9) {
            showNextQuestion();
        } else if (userAnswer.equals(getString(R.string.show_answers))) {
            showAnswers();
        } else {
            showResults();
        }
    }

    private void showNextQuestion() {
        currentQuestion++;
        setQuestion(currentQuestion);
    }

    private void showResults() {
        question.setText("RESULTS\nYou've got " + result + " out of " + questions.length + "!");
        answer1.setText(getString(R.string.play_again));
        answer2.setText(getString(R.string.show_answers));
        answer2.setElevation(6);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);
    }

    private void showAnswers() {
        String allAnswers = "";
        for (int i = 0; i < questions.length; i++) {
            allAnswers += "\"" + questions[i] + "\"\n" + answers[i] + "\n\n";
        }
        question.setText(allAnswers);
        answer1.setElevation(6);
        answer2.setVisibility(View.GONE);
    }

    // Create a String array of four answers, one of them is correct
    private void randomizeAnswers() {
        Random rn = new Random();
        options = new String[]{"", "", "", ""};
        int r;
        for (int i = 0; i < options.length; i++) {
            do {
                r = rn.nextInt(10);
            } while (isInArray(answers[r]));
            options[i] = answers[r];
        }
        if (!isInArray(answers[currentQuestion])) {
            options[rn.nextInt(4)] = answers[currentQuestion];
        }
    }

    // Check if a particular answer is already present in the array of answers
    private boolean isInArray(String option) {
        check = false;
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(option)) check = true;
        }
        return check;
    }
}