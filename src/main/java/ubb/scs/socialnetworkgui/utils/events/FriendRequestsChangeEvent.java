package ubb.scs.socialnetworkgui.utils.events;

import ubb.scs.socialnetworkgui.domain.FriendRequest;

public class FriendRequestsChangeEvent implements Event {
    private ChangeEventType type;
    private FriendRequest friendRequest;

    public FriendRequestsChangeEvent(ChangeEventType type, FriendRequest friendRequest) {
        this.type = type;
        this.friendRequest = friendRequest;
    }

    public ChangeEventType getType() {
        return type;
    }

    public FriendRequest getFriendRequest() {
        return friendRequest;
    }
}