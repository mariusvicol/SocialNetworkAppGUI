package ubb.scs.socialnetworkgui.utils.paging;

public class Page <E>{
    private final int totalNumber;
    private final Iterable<E> content;

    public Page(int totalNumber, Iterable<E> content) {
        this.totalNumber = totalNumber;
        this.content = content;
    }

    public int getTotalNumberOfElements() {
        return totalNumber;
    }

    public Iterable<E> getContent() {
        return content;
    }
}
