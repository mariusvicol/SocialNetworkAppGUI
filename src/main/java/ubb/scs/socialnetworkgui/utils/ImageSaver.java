package ubb.scs.socialnetworkgui.utils;

public class ImageSaver {
    private String username;
    private String path;

    public ImageSaver(String username, String path) {
        this.username = username;
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsernameImage() {
        return username;
    }

    public String getPath() {
        return path;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
