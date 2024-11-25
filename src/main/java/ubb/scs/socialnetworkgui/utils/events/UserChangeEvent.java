package ubb.scs.socialnetworkgui.utils.events;

import ubb.scs.socialnetworkgui.domain.UserInfo;

public class UserChangeEvent implements Event {
    private ChangeEventType type;
    private UserInfo data, oldData;

    public UserChangeEvent(ChangeEventType type, UserInfo data) {
        this.type = type;
        this.data = data;
    }

    public UserChangeEvent(ChangeEventType type, UserInfo data, UserInfo oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public UserInfo getData() {
        return data;
    }

    public UserInfo getOldData() {
        return oldData;
    }
}
