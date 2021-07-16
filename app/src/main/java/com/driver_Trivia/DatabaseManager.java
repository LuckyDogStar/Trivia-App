package com.driver_Trivia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

    public class DatabaseManager extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "triviaDB";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_QUESTION = "Tquestions";
        private static final String ID = "id";
        private static final String NAME = "question";
        private static final String ANSWER = "answer";

        public DatabaseManager( Context context ) {
            super( context, DATABASE_NAME, null, DATABASE_VERSION );
        }

        public void onCreate( SQLiteDatabase db ) {
            // build sql create statement
            String sqlCreate = "CREATE TABLE " + TABLE_QUESTION + "( " + ID;
            sqlCreate += " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME;
            sqlCreate += " TEXT, " + ANSWER + " TEXT );";

            db.execSQL( sqlCreate );
        }
        @Override
        public void onUpgrade( SQLiteDatabase db,
                               int oldVersion, int newVersion ) {
            // Drop old table if it exists
            db.execSQL( "drop table if exists " + TABLE_QUESTION);
            // Re-create tables
            onCreate( db );
        }

        public void insert( Question question) {
            SQLiteDatabase db = this.getWritableDatabase( );
            String sqlInsert = "insert into " + TABLE_QUESTION;
            sqlInsert += " values( null, '" + question.getQuestion( );
            sqlInsert += "', '" + question.getAnswer( ) + "' )";

            db.execSQL( sqlInsert );
            db.close( );
        }

        public void deleteById( int id ) {
            SQLiteDatabase db = this.getWritableDatabase( );
            String sqlDelete = "delete from " + TABLE_QUESTION;
            sqlDelete += " where " + ID + " = " + id;

            db.execSQL( sqlDelete );
            db.close( );
        }

        public void updateById( int id, String name, String answer ) {
            SQLiteDatabase db = this.getWritableDatabase();

            String sqlUpdate = "update " + TABLE_QUESTION;
            sqlUpdate += " set " + NAME + " = '" + name + "', ";
            sqlUpdate += ANSWER + " = '" + answer + "'";
            sqlUpdate += " where " + ID + " = " + id;

            db.execSQL( sqlUpdate );
            db.close( );
        }

        public ArrayList<Question> selectAll( ) {
            String sqlQuery = "select * from " + TABLE_QUESTION;

            SQLiteDatabase db = this.getWritableDatabase( );
            Cursor cursor = db.rawQuery( sqlQuery, null );

            ArrayList<Question> questions = new ArrayList<Question>( );
            while( cursor.moveToNext( ) ) {
                Question currentQuestion
                        = new Question( Integer.parseInt( cursor.getString( 0 ) ),
                        cursor.getString( 1 ), cursor.getString( 2 ) );
                questions.add(currentQuestion);
            }
            db.close( );
            return questions;
        }

        public Question selectById(int id ) {
            String sqlQuery = "SELECT * FROM " + TABLE_QUESTION + ";";
            sqlQuery += " where " + ID + " = " + id;

            SQLiteDatabase db = this.getWritableDatabase( );
            Cursor cursor = db.rawQuery( sqlQuery, null );

            Question question = null;
            if( cursor.moveToFirst( ) )
                question = new Question( Integer.parseInt( cursor.getString( 0 ) ),
                        cursor.getString( 1 ), cursor.getString( 2 ) );
            return question;
        }



    }

