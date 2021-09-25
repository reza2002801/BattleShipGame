package models;

import com.google.gson.Gson;

public class Event {
    String AuthToken;

    int[] xoy;

    int type;

    public Event(int type,int[] xoy,String AuthToken){
        this.xoy = xoy;
        this.AuthToken = AuthToken;
        this.type = type;
    }

    public int[] getClickedPosition(){
        return xoy;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
