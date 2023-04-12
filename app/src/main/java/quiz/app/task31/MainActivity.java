package quiz.app.task31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    EditText editUsername;
    SharedPreferences sharedPreferences;
    String userNameFromSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button);
        editUsername = findViewById(R.id.editTextTextPersonName);

        sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        userNameFromSharedPreference = sharedPreferences.getString("Username", "");
        editUsername.setText(userNameFromSharedPreference);

        startButton.setOnClickListener(view -> {
            Log.v("Button clicked", "Button");
            SharedPreferences.Editor myEditor = sharedPreferences.edit();
            myEditor.putString("Username", editUsername.getText().toString());
            myEditor.apply();

            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });
    }
}