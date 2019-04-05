package com.example.convoy;

import com.example.convoy.AccountActivity.RegisterActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegisterTest {
    @Test
    public void passwordMatch() {
        assertTrue(RegisterActivity.matchPass("password","password"));
    }

    @Test
    public void validPassword()
    {
        assertFalse(RegisterActivity.validPass("hi"));
    }
    @Test
    public void emptyStrings(){
        assertTrue(RegisterActivity.fieldsAreEmpty("","","",""));
    }
    @Test
    public void notEmptyStrings(){
        assertFalse(RegisterActivity.fieldsAreEmpty("word","4","56","more words"));
    }
}