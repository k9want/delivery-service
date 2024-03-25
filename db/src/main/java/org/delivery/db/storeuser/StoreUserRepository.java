package org.delivery.db.storeuser;

import java.util.Optional;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    // select * from store_user where email = ? and status = ?  order by id desc limit 1
    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email,
        StoreUserStatus status);
}
