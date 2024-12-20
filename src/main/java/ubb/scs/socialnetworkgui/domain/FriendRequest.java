package ubb.scs.socialnetworkgui.domain;
import ubb.scs.socialnetworkgui.domain.Entity;

import java.time.LocalDateTime;

public class FriendRequest extends Entity<Tuple<String,String>> {

    private String from;
    private String to;
    private LocalDateTime dateTime;

    public FriendRequest(String from, String to, LocalDateTime dateTime) {
        this.from = from;
        this.to = to;
        this.dateTime = dateTime;
    }

    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                '}';
    }
}
