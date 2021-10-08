package com.anhnc2.ehealicords.repository;

//import com.anhnc2.ehealicords.data.entity.UserEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
//
//    @Transactional
//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query("update UserEntity p set p.avatarKey = :key where p.id = :id")
//    void updateAvatarKey(@Param(value = "id") long id, @Param(value = "key") String key);
//}
