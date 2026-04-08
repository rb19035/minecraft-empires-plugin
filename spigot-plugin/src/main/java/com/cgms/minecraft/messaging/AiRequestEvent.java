/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.messaging;


import java.util.Date;

public class AiRequestEvent
{
    private String message;
    private Date timestamp;

    public AiRequestEvent(String message )
    {
        this.message = message;
        this.timestamp = new Date();
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

}