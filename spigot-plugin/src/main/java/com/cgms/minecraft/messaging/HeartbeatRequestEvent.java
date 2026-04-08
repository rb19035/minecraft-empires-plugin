/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.messaging;


import java.util.Date;

public class HeartbeatRequestEvent
{
    private String message;
    private Date timestamp;

    public HeartbeatRequestEvent()
    {
        this.message = "";
        this.timestamp = new Date();
    }

    public HeartbeatRequestEvent( String message )
    {
        this.message = message;
        this.timestamp = new Date();
    }

    public String getMessage()
    {
        return message;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public void setDate( Date date )
    {
        this.timestamp = date;
    }
}