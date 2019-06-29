package hu.eteosf.gergokovacs.userorders.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import hu.eteosf.gergokovacs.userorders.model.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(String userId);
}
