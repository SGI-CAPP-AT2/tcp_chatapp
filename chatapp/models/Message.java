package chatapp.models;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    public static int U = 1, M = 2, T = 3; 
    public String username, message;
    public Date time;
    public boolean delivered;
    public Message(Message m){
        username=m.username;
        message=m.message;
        time=m.time;
    }
    public Message(String u, String m, Date t){
        username=u;
        message=m;
        time=t;
    }
    public Object get(int type){
        if(Message.M==type)
            return message;
        else if(Message.U==type)
            return username;
        else if(Message.T==type)
            return time;
        else
            return "Nothing Found";
    }
    public String toString(){
        return username+"<"+time.toString()+">: \n"+message+"\n ";
    }
}
