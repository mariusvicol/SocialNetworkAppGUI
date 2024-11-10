package ubb.scs.socialnetworkgui.domain;
import ubb.scs.socialnetworkgui.domain.Entity;

import java.time.LocalDateTime;

public class FriendRequest extends Entity<Tuple<String,String>> {

    private final String from;
    private final String to;
    private final LocalDateTime dateTime;

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
    @Override
    public String toString() {
        return "FriendRequest{" +
                "from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                '}';
    }
}
