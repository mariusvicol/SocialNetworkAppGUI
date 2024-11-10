package ubb.scs.socialnetworkgui.gui;

import javafx.fxml.FXML;
import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

public class AdminPageController extends MenuController {

    public AdminPageController(ApplicationService applicationService, String username) {
        super(applicationService, username);
    }

    @FXML
    public void onProfileClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/profile.fxml", new ProfileController(applicationService, username));
    }

    @FXML
    public void onUsersListClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/userslist.fxml", new UsersListController(applicationService, username));
    }

    @FXML
    public void onChatsListClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/chatslist.fxml", new ChatsListController(applicationService, username));
    }

    @FXML
    public void onSettingsClick() {
        switchScene("/ubb/scs/socialnetworkgui/views/settings.fxml", new SettingsController(applicationService, username));
    }
}
