package com.driver_Trivia;

public class Question {
        private int id;
        private String name;
        private String answer;

        public Question(int newID, String newName, String newAnswer){
            setID(newID);
            setName(newName);
            setAnswer(newAnswer);
        }

        public void setID(int newID) {
            id=newID;
        }

        public void setName(String newName) {
            name=newName;
        }

        public void setAnswer(String newAnswer) {
            if (newAnswer != "")
                answer = newAnswer;
        }

        public int getId(){
            return id;
        }

        public String getQuestion(){
            return name;
        }

        public String getAnswer(){
            return answer;
        }

        public String toString(){
            return id + " " + name + " " + answer;
        }

}
