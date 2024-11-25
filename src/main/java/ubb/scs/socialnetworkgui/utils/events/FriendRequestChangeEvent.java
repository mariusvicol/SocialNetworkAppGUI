package ubb.scs.socialnetworkgui.utils.events;

import ubb.scs.socialnetworkgui.domain.FriendRequest;

public class FriendRequestChangeEvent implements Event {
    private ChangeEventType type;
    private FriendRequest data, oldData;

    public FriendRequestChangeEvent(ChangeEventType type, FriendRequest data) {
        this.type = type;
        this.data = data;
    }

    public FriendRequestChangeEvent(ChangeEventType type, FriendRequest data, FriendRequest oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public FriendRequest getData() {
        return data;
    }

    public FriendRequest getOldData() {
        return oldData;
    }
}
