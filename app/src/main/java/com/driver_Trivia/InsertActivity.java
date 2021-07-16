package com.driver_Trivia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.driver_Trivia.R;

public class InsertActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbManager=new DatabaseManager(this);
        setContentView(R.layout.activity_insert);
    }

    public void insert(View v){
        //Retrieve name and price
        EditText nameEditText = (EditText) findViewById(R.id.input_name);
        EditText answerEditText = (EditText) findViewById(R.id.input_answer);
        String name = nameEditText.getText().toString();
        String answer = answerEditText.getText().toString();

        //insert new question in database
        try{
            String nAnswer = answer;
            // pass the data from the screen to the class to run query
            Question question = new Question(0,name,nAnswer);
            dbManager.insert(question);
            Toast.makeText(this, "Question added", Toast.LENGTH_SHORT).show();
        } catch(NumberFormatException nfe){
            Toast.makeText(this, "Question error", Toast.LENGTH_LONG).show();
        }

        //clear data
        nameEditText.setText("");
        answerEditText.setText("");
    }

    public void goBack(View v){
        this.finish();
    }


}