package ubb.scs.socialnetworkgui.domain;

import java.time.LocalDateTime;

public class Sessions extends Entity<String> {
   private String username;
   private LocalDateTime dataConnection;

    public Sessions(String username, LocalDateTime dataConnection){
        this.username = username;
        this.dataConnection = dataConnection;
    }

    public String getUsername(){
        return username;
    }

    public LocalDateTime getDataConnection(){
        return dataConnection;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setDataConnection(LocalDateTime dataConnection){
        this.dataConnection = dataConnection;
    }
}
