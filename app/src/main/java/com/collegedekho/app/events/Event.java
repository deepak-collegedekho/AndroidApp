package com.collegedekho.app.events;


public class Event {
    private final String tag;
    private final String message;
    private final Object obj;

    public Event(String tag,Object obj,  String message) {
        this.tag = tag;
        this.obj = obj;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTag() {
        return tag;
    }

    public Object getObj()
    {
        return obj;
    }


}
