package com.example.convoy;

import java.util.ArrayList;
import java.util.Iterator;

public class Poll {

    private ArrayList<String> choices;

    public Poll(ArrayList<String> c){
        if(isValid(c)){
        this.choices = c;}
        else{
            return;
        }
    }
    public static boolean isValid(ArrayList<String> c){
        if(c.size() == 0) return false;
        Iterator<String> i = c.iterator();
        String s;
        while(i.hasNext())
        {s = i.next();
            if(s.length() <= 0 || c.indexOf(s)!= s.lastIndexOf(s) ){
             return false;
            }
        }
    return true;
    }
    public ArrayList<String> getChoices(){
        return this.choices;
    }
    public int getChoiceCount(){
        return choices.size();
    }
    public int getChoiceResult(String s){
        //FIXME return firebase results
        return choices.indexOf(s);
    }
    public int[] getResults(){
        //FIXME
        return null;
    }
}
