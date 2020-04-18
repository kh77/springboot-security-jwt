package com.sm.app.orm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sm.app.orm.entity.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	Authority findByName(String name);
}
