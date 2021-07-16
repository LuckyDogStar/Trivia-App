package com.driver_Trivia;

import android.graphics.Point;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity  {
    DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager( this );
        updateView( );
    }

    // Build a View dynamically with all the candies
    public void updateView( ) {
        ArrayList<Question> questions = dbManager.selectAll( );
        if( questions.size( ) > 0 ) {
            // create ScrollView and GridLayout
            ScrollView scrollView = new ScrollView( this );
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( questions.size( ) );
            grid.setColumnCount( 4 );

            // create arrays of components
            TextView [] ids = new TextView[questions.size( )];
            EditText [][] questionAndAnswer = new EditText[questions.size( )][2];
            Button [] buttons = new Button[questions.size( )];
            ButtonHandler bh = new ButtonHandler( );

            // retrieve width of screen
            Point size = new Point( );
            getWindowManager( ).getDefaultDisplay( ).getSize( size );
            int width = size.x;

            int i = 0;

            for ( Question question : questions ) {
                // create the TextView for the candy's id
                ids[i] = new TextView( this );
                ids[i].setGravity( Gravity.CENTER );
                ids[i].setText( "" + question.getId( ) );

                // create the two EditTexts for the candy's name and price
                questionAndAnswer[i][0] = new EditText( this );
                questionAndAnswer[i][1] = new EditText( this );
                questionAndAnswer[i][0].setText( question.getQuestion( ) );
                questionAndAnswer[i][1].setText( "" + question.getAnswer( ) );
                questionAndAnswer[i][0].setId( 10 * question.getId( ) );
                questionAndAnswer[i][1].setId( 10 * question.getId( ) + 1 );

                // create the button
                buttons[i] = new Button( this );
                buttons[i].setText( "Update" );
                buttons[i].setId( question.getId( ) );

                // set up event handling
                buttons[i].setOnClickListener( bh );

                // add the elements to grid
                grid.addView( ids[i], width / 10,
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( questionAndAnswer[i][0], ( int ) ( width * .4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( questionAndAnswer[i][1], ( int ) ( width * .15 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( buttons[i], ( int ) ( width * .35 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );

                i++;
            }
            scrollView.addView( grid );
            setContentView( scrollView );
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {
            // retrieve name and price of the candy
            int candyId = v.getId( );
            EditText nameET = ( EditText ) findViewById( 10 * candyId );
            EditText answerET = ( EditText ) findViewById( 10 * candyId + 1 );
            String name = nameET.getText( ).toString( );
            String answer = answerET.getText( ).toString( );

            // update candy in database
            try {
                String nAnswer = answer;
                dbManager.updateById( candyId, name, nAnswer );
                Toast.makeText( UpdateActivity.this, "Question updated",
                        Toast.LENGTH_SHORT ).show( );

                // update screen
                updateView( );
            } catch( NumberFormatException nfe ) {
                Toast.makeText( UpdateActivity.this,
                        "Answer error", Toast.LENGTH_LONG ).show( );
            }
        }
    }
}

