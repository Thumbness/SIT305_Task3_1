package quiz.app.task31;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class ResultsActivity  extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView congratsText;
    Button newQuizButton;
    Button exitButton;
    int userScoreFromSharedPreferences;
    String userNameFromSharedPreference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);
        sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        userNameFromSharedPreference = sharedPreferences.getString("Username", "");
        userScoreFromSharedPreferences = sharedPreferences.getInt("Score", 0);
        congratsText = findViewById(R.id.finCongratsText);
        newQuizButton = findViewById(R.id.newQuizButton);
        exitButton = findViewById(R.id.exitButton);
        congratsText.setText("Congratulations, " + userNameFromSharedPreference + "!\nYour Score: " + userScoreFromSharedPreferences + "/5");

        // Clicking new quiz button will start quiz over again
        newQuizButton.setOnClickListener(view -> {
            Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(intent);
        });
        // Clicking exit button will delete the username from shared preferences and exit the app.
        exitButton.setOnClickListener(view -> {
            SharedPreferences.Editor myEditor = sharedPreferences.edit();
            myEditor.putString("Username", null);
            myEditor.apply();

            finishAffinity();
            System.exit(0);
        });
    }
}