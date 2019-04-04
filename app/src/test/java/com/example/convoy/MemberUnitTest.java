package com.example.convoy;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.lang.reflect.Member;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MemberUnitTest {

    @Test
    public void MemberTest(){
        GroupMember m = new GroupMember("aUser", 34.5,36.7,"123456789");
        assertNotEquals(m,null);

    }

    @Test
    public void MemberTest2(){
        GroupMember m = new GroupMember();
        assertEquals(m,null);
    }

    @Test
    public void MemberLatLngtest(){
        GroupMember m = new GroupMember("aUser", 34.5,36.7,"123456789");
        LatLng point = m.getLocation();
        assertEquals(new LatLng(34.5,36.7),point);
    }


}