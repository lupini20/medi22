package com.example.iknow243;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QuestionModel {


    public String prompt = "";
    public String response = "";
    public ArrayList<String> suggestion = new ArrayList<String>();


    public QuestionModel(){

    }



    public QuestionModel(String prompt, String response, ArrayList<String> suggestion){
        this.prompt=prompt;
        this.response=response;
        this.suggestion=suggestion;
    }



}
