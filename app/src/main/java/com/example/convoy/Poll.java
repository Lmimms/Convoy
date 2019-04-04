package com.example.convoy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
        while (i.hasNext()){
            if(i.next() == "") return false;
        }

        String s;
        return !hasDuplicate(i);
    }

    private static boolean hasDuplicate(Iterator<String> i) {
    //FIXME
        return false;
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
