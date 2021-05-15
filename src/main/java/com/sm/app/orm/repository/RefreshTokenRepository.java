package com.sm.app.orm.repository;

import com.sm.app.orm.entity.RefreshToken;
import com.sm.app.orm.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
	RefreshToken findByToken(String token);

	@Modifying
	int deleteByUser(UserEntity user);
}
