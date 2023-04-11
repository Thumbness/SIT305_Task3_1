package quiz.app.task31;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
            new QuizQuestion(
                    "What is the capital of France?",
                    new String[]{"Paris", "London", "Berlin"},
                    0),
            new QuizQuestion("What is Homer Simpson's skin color",
                    new String[]{"Blue","White","Yellow"}, 2));
    ProgressBar progressBar;
    TextView question;
    Button selection_1;
    Button selection_2;
    Button selection_3;
    Button nextButton;
    SharedPreferences sharedPreferences;
    private int currentQuestionIndex = 0;
    ColorStateList colorGreen = ColorStateList.valueOf(Color.parseColor("#17A508"));
    ColorStateList colorRed = ColorStateList.valueOf(Color.parseColor("#FFFF0000"));
    ColorStateList colorNeutral = ColorStateList.valueOf(Color.parseColor("#BFBFBF"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_layout);
        sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userNameFromSharedPreference = sharedPreferences.getString("Username", "");
        Integer currentProgress = sharedPreferences.getInt("Progress", 0);
        Integer score = sharedPreferences.getInt("Score", 0);

        question = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(currentProgress);
        selection_1 = findViewById(R.id.button2);
        selection_2 = findViewById(R.id.button3);
        selection_3 = findViewById(R.id.button4);
        nextButton = findViewById(R.id.button5);
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
    private void checkAnswer(int selectedOptionIndex, Button button) {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        if (selectedOptionIndex == currentQuestion.getCorrectAnswerIndex()) {
            button.setBackgroundTintList(colorGreen);
        } else {
            button.setBackgroundTintList(colorRed);
        }
    }
    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quizQuestions.size()) {
            selection_1.setBackgroundTintList(colorNeutral);
            selection_2.setBackgroundTintList(colorNeutral);
            selection_3.setBackgroundTintList(colorNeutral);
            updateUI();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }


}
