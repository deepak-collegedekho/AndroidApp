package com.collegedekho.app.events;


public class Event {
    private final String tag;
    private final String extra;
    private final Object obj;


    public Event(String tag,Object obj,  String extra) {
        this.tag = tag;
        this.obj = obj;
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    public String getTag() {
        return tag;
    }

    public Object getObj()
    {
        return obj;
    }


}
