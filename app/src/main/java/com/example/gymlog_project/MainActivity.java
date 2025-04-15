package com.example.gymlog_project;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymlog_project.database.Entities.GymLog;
import com.example.gymlog_project.database.GymLogRepository;
import com.example.gymlog_project.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

// note: type DAC_GYMLOG into logcat to see possible errors

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GymLogRepository repository;

    public static final String TAG = "DAC_GYMLOG";
    String Exercise = "";
    double Weight = 0.0;
    int Reps = 0;

    //TODO: Add login information
    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {                // This is the first method that will run when the app is shown
        super.onCreate(savedInstanceState);                             // This basically means "run the default setup first" (before we start adding our own code in)

        binding = ActivityMainBinding.inflate(getLayoutInflater());

            // "ActivityMainBinding" is a JAVA or Kotlin class that is automatically generated by Android Studio when we have a file named "activity_main.xml" and turn on and use View Binding
            // This class allows direct access to every view with an id in that xml file
            // Therefore, we mo longer have to use "findViewById" method.
            // So what does this mean? It means if we have:

                // [xml file]
                // <TextView
                //      android:id="@+id/textViewTitle"/>

                // Then we can directly access that is in the JAVA file by saying:

                // [JAVA file]
                // binding.textViewTitle.setText("Access it just like this");

        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());
        updateDisplay();
        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });

        binding.exerciseInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplay();
            }
        });
    }

    private void insertGymLogRecord(){
        if(Exercise.isEmpty()){
            return;
        }
        GymLog log = new GymLog(Exercise, Weight, Reps, loggedInUserId);
        repository.insertGymLog(log);
    }

    private void updateDisplay(){
        ArrayList<GymLog> allLogs = repository.getAllLogs();
        if(allLogs.isEmpty()){
            binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
        }
        StringBuilder sb = new StringBuilder();
        for(GymLog log : allLogs){
            sb.append(log);
        }
        binding.logDisplayTextView.setText(sb.toString());
    }

    private void getInformationFromDisplay(){

        // This is will get the String information that the user types in the exerciseInputEditText
        Exercise = binding.exerciseInputEditText.getText().toString();

        // This is will get the double information that the user types in the exerciseInputEditText and turn it into a String
        // Note: we have to put this in a try-catch block because it will throw a NumberFormatException error if the user puts in a blank entry
        try{
            Weight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        }
        catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from weight edit text.");
        }

        // This is will get the int information that the user types in the RepsInputEditText and turn it into a String
        // Note: we have to put this in a try-catch block because it will throw a NumberFormatException error if the user puts in a blank entry
        try{
            Reps = Integer.parseInt(binding.repInputEditText.getText().toString());
        }
        catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from reps edit text.");
        }
    }
}