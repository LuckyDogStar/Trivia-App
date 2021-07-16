package com.driver_Trivia;

import android.os.Bundle;
import android.graphics.Point;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.driver_Trivia.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private ScrollView scrollView;
    private int buttonWidth;
    private int numberCorrect;
    private int numberOfQuestions;
    private TextView questionView;
    private ArrayList<Question> questions;
    private Button[] aButtons;
    private Random ran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(this);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        buttonWidth=size.x/2;
        updateView();
        ran = new Random();
        Question temp = new Question(0,"What is the largest planet in our solar system?", "Jupiter" );
        dbManager.insert(temp);
        temp = new Question(0,"What is the smallest planet in our solar system?", "Mercury" );
        dbManager.insert(temp);
        temp = new Question(0,"What celestial body was formerly a planet?", "Pluto" );
        dbManager.insert(temp);
        temp = new Question(0,"What is the largest moon of Jupiter?", "Ganymede" );
        dbManager.insert(temp);
        temp = new Question(0,"What planet is tilted on its axis?", "Uranus" );
        dbManager.insert(temp);
        temp = new Question(0,"What planet has the most visible rings in our solar system?", "Saturn" );
        dbManager.insert(temp);
        temp = new Question(0,"What is the third planet from the sun?", "Earth" );
        dbManager.insert(temp);
        temp = new Question(0,"What is the second planet from the sun?", "Venus" );
        dbManager.insert(temp);
        temp = new Question(0,"What is the largest dwarf planet in our solar system?", "Eris" );
        dbManager.insert(temp);
        temp = new Question(0,"What planet is named after the Roman god of War?", "Mars" );
        dbManager.insert(temp);
        temp = new Question(0,"What planet has the fastest winds in our solar system?", "Neptune" );
    }

    protected void onResume( ) {
        super.onResume( );
        updateView( );
    }

    public void updateView( ) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Log.w("MainActivity", "Add Selected");
                Intent insertIntent = new Intent(this, InsertActivity.class);
                this.startActivity(insertIntent);
                return true;
            case R.id.action_delete:
                Log.w("MainActivity", "Delete selected");
                Intent deleteIntent = new Intent(this, DeleteActivity.class);
                this.startActivity(deleteIntent);
                return true;
            case R.id.action_update:
                Log.w("MainActivity", "Update selected");
                Intent updateIntent = new Intent(this, UpdateActivity.class);
                this.startActivity(updateIntent);
                return true;


        }
        return false;
    }


    public void onClickPlay(View v){
        setContentView(R.layout.fragment_trivia);
        questionView = (TextView) findViewById(R.id.textView);
        getQuestions();
        updateText();
        numberCorrect = 0;
        aButtons = new Button[4];
        numberOfQuestions = 0;
        aButtons[0] = (Button) findViewById(R.id.answer1);
        aButtons[1] = (Button) findViewById(R.id.answer2);
        aButtons[2] = (Button) findViewById(R.id.answer3);
        aButtons[3] = (Button) findViewById(R.id.answer4);
        updateButtons();


    }

    private void updateText(){
        questionView.setText(questions.get(numberOfQuestions).getQuestion());
    }

    private void updateButtons(){
        for(Button button:aButtons){
            button.setText("");
        }
        aButtons[ran.nextInt(4)].setText(questions.get(numberOfQuestions).getAnswer());
        for(Button button:aButtons){
            if(button.getText().toString() != questions.get(numberOfQuestions).getAnswer()){
                button.setText(questions.get(ran.nextInt(questions.size())).getAnswer());
                while(button.getText().toString() == questions.get(numberOfQuestions).getAnswer()){
                    button.setText(questions.get(ran.nextInt(questions.size())).getAnswer());
                }

            }
        }

    }

    public void onClickOne(View v){
        if(aButtons[0].getText() == questions.get(numberOfQuestions).getAnswer()){
            buttonHelper(true);
        }
        else{
            buttonHelper(false);
        }
    }
    public void onClickTwo(View v){
        if(aButtons[1].getText() ==questions.get(numberOfQuestions).getAnswer()){
            buttonHelper(true);
        }
        else{
            buttonHelper(false);
        }
    }
    public void onClickThree(View v){
        if(aButtons[2].getText() ==questions.get(numberOfQuestions).getAnswer()){
            buttonHelper(true);
        }
        else{
            buttonHelper(false);
        }
    }
    public void onClickFour(View v){
        if(aButtons[3].getText() ==questions.get(numberOfQuestions).getAnswer()){
            buttonHelper(true);
        }
        else{
            buttonHelper(false);
        }
    }

    private void buttonHelper(boolean correct){
        if(correct){
            numberCorrect++;
            Toast.makeText(this, "Correct! ", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Incorrect. Correct Answer: "+ questions.get(numberOfQuestions).getAnswer(), Toast.LENGTH_SHORT).show();

        }
        for(Button button:aButtons){
            button.setEnabled(false);
        }
    }


    private void getQuestions(){
       questions = dbManager.selectAll();
    }



    public void onClickNext(View v){
        numberOfQuestions++;
        if(numberOfQuestions == questions.size()){
            onClickHome(v);
            return;
        }
        for(Button button:aButtons){
            button.setEnabled(true);

        }
        updateText();

        updateButtons();
    }
    public void onClickHome(View v){
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "Quiz Complete! You got "+ numberCorrect +"/"+ (numberOfQuestions)+ " correct", Toast.LENGTH_SHORT).show();
    }

}