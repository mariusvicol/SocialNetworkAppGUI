package ubb.scs.socialnetworkgui.gui;

import ubb.scs.socialnetworkgui.service.ApplicationService;
import ubb.scs.socialnetworkgui.utils.observer.Observer;

public class ChatsListController extends MenuController implements Observer {
    public ChatsListController(ApplicationService applicationService, String username) {
        super(applicationService, username);
    }

    @Override
    public void update() {
        // TODO
    }
}
