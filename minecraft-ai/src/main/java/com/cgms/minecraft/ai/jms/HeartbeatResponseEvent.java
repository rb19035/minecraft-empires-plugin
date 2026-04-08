/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 */

package com.cgms.minecraft.ai.jms;

import java.util.Date;

public class HeartbeatResponseEvent
{
    private String message;
    private Date date;

    public HeartbeatResponseEvent()
    {
        this.message = "";
        this.date = new Date();
    }

    public HeartbeatResponseEvent( String message )
    {
        this.message = message;
        this.date = new Date();
    }

    public String getMessage()
    {
        return message;
    }

    public Date getDate()
    {
        return date;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }
}