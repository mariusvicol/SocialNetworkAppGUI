package ubb.scs.socialnetworkgui.domain;

import java.time.LocalDateTime;

public class Message extends Entity<Integer> {
    private String sender;
    private String receiver;
    private LocalDateTime data;
    private String content;
    private Message reply;

    public Message(String sender, String receiver, String content, LocalDateTime data){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.data = data;
        this.reply = null;
    }

    public String getSender(){
        return sender;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public String getReceiver(){
        return receiver;
    }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public LocalDateTime getData(){
        return data;
    }

    public void setData(LocalDateTime data){
        this.data = data;
    }

    public void setReply(Message reply){
        this.reply = reply;
    }

    public Message getReply(){
        return reply;
    }
}
