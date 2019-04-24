package com.example.convoy;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.lang.reflect.Member;
import java.util.ArrayList;

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

    @Test
    public void MemberGetTest()
    {
        GroupMember m = new GroupMember("aUser", 34.5,36.7,"123456789");
        assertEquals("123456789",m.getUserID());
        assertEquals("aUser",m.getName());
    }

    @Test
    public void MemberSetIDTest()
    {
        GroupMember m = new GroupMember("aUser", 34.5,36.7,"123456789");
        m.setUserID("101");
        assertEquals("101",m.getUserID());
    }

    @Test
    public void GroupArrayTest(){
        ArrayList<Group> aGroupList = new ArrayList<>();
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        assertEquals(15,aGroupList.size());
    }
    @Test
    public void checkGroupObject(){
        Group g = new Group("12345", "The Group Name");
        assertEquals("12345",g.getId());

    }

}