package com.example.convoy;

import com.example.convoy.AccountActivity.LoginActivity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTest
{

    @Test
    public  void emptyStringTest()
    {
       assertTrue(LoginActivity.emptyString("    "));
    }

    @Test
    public void EmptyString(){assertFalse(LoginActivity.emptyString("    a"));}

    @Test
    public void EmptyStringTestTrue(){assertTrue(LoginActivity.EmptyString(""));}

}
