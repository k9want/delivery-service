package org.delivery.db.storemenu;

import java.util.List;
import java.util.Optional;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity, Long> {

    // 유효한 메뉴 가져오기 id로 해당 메뉴 찾기
    // select * from store_menu where id = ? and status = ? order by id desc limit 1
    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus status);

    // 어떤 가게의 모든 메뉴
    // select * from store_menu where store_id = ? and status = ? order by sequence desc
    List<StoreMenuEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId,
        StoreMenuStatus status);
}
