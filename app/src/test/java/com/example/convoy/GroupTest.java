package com.example.convoy;

import static org.junit.Assert.*;
import org.junit.Test;

public class GroupTest {
    @Test
    public void groupGetNameTest()
    {
        Group jedi = new Group("123","Jedi" );
        assertEquals("Jedi",jedi.getName());
    }


}
