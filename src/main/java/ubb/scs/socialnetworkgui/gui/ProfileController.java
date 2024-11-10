package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

public class ProfileController extends MenuController implements Observer {
    public ProfileController(ApplicationService applicationService, String username) {
        super(applicationService, username);
        applicationService.addObserver(this);
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

    @Override
    public void update() {
        // TODO
    }
}
