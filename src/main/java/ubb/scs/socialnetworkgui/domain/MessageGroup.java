package ubb.scs.socialnetworkgui.domain;

import java.time.LocalDateTime;
import java.util.List;

public class MessageGroup extends Entity<Integer> {
    private String sender;
    private List<String> receivers;
    private String message;
    private Message reply;
    private LocalDateTime date;

    public MessageGroup(String sender, List<String> receivers, String message, LocalDateTime date){
        this.sender = sender;
        this.receivers = receivers;
        this.message = message;
        this.date = date;
        this.reply = null;
    }

    public String getSender(){
        return sender;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public List<String> getReceivers(){
        return receivers;
    }

    public void setReceivers(List<String> receivers){
        this.receivers = receivers;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setReply(Message reply){
        this.reply = reply;
    }

    public Message getReply(){
        return reply;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }
}
