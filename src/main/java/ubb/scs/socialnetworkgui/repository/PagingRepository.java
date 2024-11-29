package ubb.scs.socialnetworkgui.repository;

import ubb.scs.socialnetworkgui.domain.Entity;
import ubb.scs.socialnetworkgui.utils.paging.Page;
import ubb.scs.socialnetworkgui.utils.paging.Pageable;

public interface PagingRepository <ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findFriendshipsForUserWithPagination(Long idUser, Pageable pageable);
}
