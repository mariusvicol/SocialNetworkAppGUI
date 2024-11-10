package ubb.scs.socialnetworkgui.gui;
import javafx.fxml.FXML;

import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

@SuppressWarnings("ClassEscapesDefinedScope")
public class UserPageController extends MenuController {
    public UserPageController(ApplicationService applicationService, String username) {
        super(applicationService, username);
    }

    @FXML
    public void onProfileClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/profile.fxml", new ProfileController(applicationService, username));
    }

    @FXML
    public void onFriendsListClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/friendslist.fxml", new FriendsListController(applicationService, username));
    }

    @FXML
    public void onFriendRequestsClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/friendrequests.fxml", new FriendRequestsController(applicationService, username));
    }

    @FXML
    public void onSettingsClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/settings.fxml", new SettingsController(applicationService, username));
    }
}
