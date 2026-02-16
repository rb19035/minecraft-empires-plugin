package com.cgms.minecraft.messaging;


public class HeartbeatRequestEvent
{
    private String message;

    public HeartbeatRequestEvent( String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }
}