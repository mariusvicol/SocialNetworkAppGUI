package ubb.scs.socialnetworkgui.utils.paging;

public class Pageable {
    private final int page;
    private final int size;

    public Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
