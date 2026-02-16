package com.cgms.minecraft.ai.jms;


public class AiRequestEvent
{
    private String message;

    public AiRequestEvent(String message)
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
