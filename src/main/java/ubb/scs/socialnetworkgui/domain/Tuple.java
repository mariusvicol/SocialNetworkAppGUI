package ubb.scs.socialnetworkgui.domain;

import java.util.Objects;


/**
 * Define a Tuple o generic type entities
 * @param <E1> - tuple first entity type
 * @param <E2> - tuple second entity type
 */
public class Tuple<E1, E2> {
    private final E1 e1;
    private final E2 e2;

    public Tuple(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public E1 getFirst() {
        return e1;
    }

    public E2 getSecond() {
        return e2;
    }

    @Override
    public String toString() {
        return e1 + "," + e2;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) obj;
        return Objects.equals(e1, tuple.e1) && Objects.equals(e2, tuple.e2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }
}