package quiz.app.task31;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;


public class QuizActivity extends AppCompatActivity {

    private List<QuizQuestion> quizQuestions = Arrays.asList(
            new QuizQuestion("What is the capital of France?",
                    new String[]{"Paris", "London", "Berlin"}, 0),
            new QuizQuestion("What is Homer Simpson's skin color",
                    new String[]{"Blue","White","Yellow"}, 2),
            new QuizQuestion("How many fingers do humans have?",
                    new String[]{"Ten", "Eight","Twenty"}, 1),
            new QuizQuestion("At what Celsius does water boil?",
                    new String[]{"88", "110","100"}, 2),
            new QuizQuestion("Who won the 2020 F1 Drivers World Championship?",
                    new String[]{"Lewis Hamilton", "Max Verstappen","Daniel Ricciardo"}, 0));
    ProgressBar progressBar;
    TextView question;
    TextView welcomeText;
    Button selection_1;
    Button selection_2;
    Button selection_3;
    Button nextButton;
    SharedPreferences sharedPreferences;
    private int currentQuestionIndex = 0;
    private int score;
    private String userNameFromSharedPreference;
    ColorStateList colorGreen = ColorStateList.valueOf(Color.parseColor("#17A508"));
    ColorStateList colorRed = ColorStateList.valueOf(Color.parseColor("#FFFF0000"));
    ColorStateList colorNeutral = ColorStateList.valueOf(Color.parseColor("#BFBFBF"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_layout);
        sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        userNameFromSharedPreference = sharedPreferences.getString("Username", "");
        score = sharedPreferences.getInt("Score", 0);

        welcomeText = findViewById(R.id.welcomeTextView);
        question = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(currentQuestionIndex);
        selection_1 = findViewById(R.id.button2);
        selection_2 = findViewById(R.id.button3);
        selection_3 = findViewById(R.id.button4);
        nextButton = findViewById(R.id.button5);

        welcomeText.setText("Welcome, " + userNameFromSharedPreference + "!");
        updateUI();

        selection_1.setOnClickListener(view -> {
            checkAnswer(0, selection_1);
        });
        selection_2.setOnClickListener(view -> {
            checkAnswer(1, selection_2);
        });
        selection_3.setOnClickListener(view -> {
            checkAnswer(2, selection_3);
        });
        nextButton.setOnClickListener(view -> {
            moveToNextQuestion();
        });
    }
    private void updateUI() {

        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        question.setText(currentQuestion.getQuestion());
        String[] options = currentQuestion.getOptions();
        selection_1.setText(options[0]);
        selection_2.setText(options[1]);
        selection_3.setText(options[2]);
    }
    private void checkAnswer(int selectedOptionIndex, Button button ) {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        if (selectedOptionIndex == currentQuestion.getCorrectAnswerIndex()) {
            button.setBackgroundTintList(colorGreen);
            score++;
        } else {
            button.setBackgroundTintList(colorRed);
        }
        disableButtons();
    }
    private void disableButtons() {
        selection_1.setEnabled(false);
        selection_2.setEnabled(false);
        selection_3.setEnabled(false);
    }
    private void enableButtons() {
        selection_1.setBackgroundTintList(colorNeutral);
        selection_2.setBackgroundTintList(colorNeutral);
        selection_3.setBackgroundTintList(colorNeutral);
        selection_1.setEnabled(true);
        selection_2.setEnabled(true);
        selection_3.setEnabled(true);
    }
    private void moveToNextQuestion() {
        currentQuestionIndex++;
        welcomeText.setEnabled(false);
        if (currentQuestionIndex < quizQuestions.size()) {
            enableButtons();
            updateUI();
        } else {
            SharedPreferences.Editor myEditor = sharedPreferences.edit();
            myEditor.putInt("Score", score);
            myEditor.apply();

            Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }


}
