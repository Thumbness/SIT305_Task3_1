package quiz.app.task31;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;


public class QuizActivity extends AppCompatActivity {

    // Instantiate a list of QuizQuestion objects that contain the question, answers and correct answer index
    private final List<QuizQuestion> quizQuestions = Arrays.asList(
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
    TextView progressText;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_layout);
        // Get shared preference for username and initialize score to zero
        sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        userNameFromSharedPreference = sharedPreferences.getString("Username", "");
        score = 0;

        // Set layout objects
        welcomeText = findViewById(R.id.welcomeTextView);
        question = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(5);
        progressBar.setProgress(currentQuestionIndex);
        selection_1 = findViewById(R.id.button2);
        selection_2 = findViewById(R.id.button3);
        selection_3 = findViewById(R.id.button4);
        nextButton = findViewById(R.id.button5);
        progressText = findViewById(R.id.progressText);


        // Method that updates the quiz activity with new questions and answers
        updateUI();

        // Calls method that checks answer based on option clicked
        selection_1.setOnClickListener(view -> checkAnswer(0, selection_1));
        selection_2.setOnClickListener(view -> checkAnswer(1, selection_2));
        selection_3.setOnClickListener(view -> checkAnswer(2, selection_3));
        // Move to next question once selection is checked
        nextButton.setOnClickListener(view -> moveToNextQuestion());
    }
    @SuppressLint("SetTextI18n")
    private void updateUI() {
        // instantiates new question class object with current question index used for calling next QuizQuestion
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        question.setText(currentQuestion.getQuestion());
        progressText.setText(currentQuestionIndex+1 + "/" + quizQuestions.size());

        //Create array of question answer options and set button text to potential answers
        String[] options = currentQuestion.getOptions();
        selection_1.setText(options[0]);
        selection_2.setText(options[1]);
        selection_3.setText(options[2]);
        progressBar.setProgress(currentQuestionIndex+1);

        // Check if user at the first question and set welcome message to visibile
        if (currentQuestionIndex <= 0)
        {
            welcomeText.setEnabled(true);
            welcomeText.setText("Welcome, " + userNameFromSharedPreference + "!");
        }
        else
        {
            welcomeText.setEnabled(false);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(int selectedOptionIndex, Button button ) {
        // Creates a quiz question object at current quiz question index,
        // Checks quiz question correct answer index to the selected user answer
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        if (selectedOptionIndex == currentQuestion.getCorrectAnswerIndex()) { // if answer correct
            button.setBackgroundTintList(colorGreen);
            score++;
        } else { // if answer incorrect
            button.setBackgroundTintList(colorRed);
        }
        //disables all buttons except the next question button after user selects option
        disableButtons();
    }
    private void disableButtons() {
        //disables all buttons except the next question button after user selects option
        selection_1.setEnabled(false);
        selection_2.setEnabled(false);
        selection_3.setEnabled(false);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableButtons() {
        // Enables all buttons for user to press
        selection_1.setBackgroundTintList(colorNeutral);
        selection_2.setBackgroundTintList(colorNeutral);
        selection_3.setBackgroundTintList(colorNeutral);
        selection_1.setEnabled(true);
        selection_2.setEnabled(true);
        selection_3.setEnabled(true);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void moveToNextQuestion() {
        currentQuestionIndex++;
        // check if user has reached the end of the quiz
        if (currentQuestionIndex < quizQuestions.size()) {
            enableButtons();
            updateUI();
        } else {
            // end of quiz: pass score to shared preferences, change to result activity
            SharedPreferences.Editor myEditor = sharedPreferences.edit();
            myEditor.putInt("Score", score);
            myEditor.apply();

            Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }


}
