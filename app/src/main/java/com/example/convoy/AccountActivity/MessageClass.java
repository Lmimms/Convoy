package com.example.convoy.AccountActivity;

import java.sql.Timestamp;
import java.util.Date;

public class MessageClass {
    private String message;
    private String sender;
    private Timestamp tStamp;

    public MessageClass (String txtMessage, String user, long time)
    {
        this.message = txtMessage;
        this.sender = user;
        this.tStamp = new Timestamp(time);
    }

    public String getMessage(MessageClass M)
    {
        return M.message;
    }

    public String getUser(MessageClass M)
    {
        return M.sender;
    }
    public Timestamp getMessageTime(MessageClass M)
    {
        return M.tStamp;
    }
}
