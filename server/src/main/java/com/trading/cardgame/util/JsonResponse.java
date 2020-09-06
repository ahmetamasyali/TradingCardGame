package com.trading.cardgame.util;

public class JsonResponse
{
    String message;

    public JsonResponse(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
