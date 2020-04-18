package com.sm.app.config.event.listener;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sm.app.config.securtiy.SecurityUtils;
import com.sm.app.orm.entity.Authority;
import com.sm.app.orm.entity.Role;
import com.sm.app.orm.entity.RolesE;
import com.sm.app.orm.entity.UserEntity;
import com.sm.app.orm.repository.AuthorityRepository;
import com.sm.app.orm.repository.RoleRepository;
import com.sm.app.orm.repository.UserRepository;

@Component
public class InitialUsersSetup {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	SecurityUtils utils;

	@Autowired
	UserRepository userRepository;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("From Application ready event...");

		Authority readAuthority = createAuthority("READ_AUTHORITY");
		Authority writeAuthority = createAuthority("WRITE_AUTHORITY");
		Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

		createRole(RolesE.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
		Role roleAdmin = createRole(RolesE.ROLE_ADMIN.name(),
				Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

		if (roleAdmin == null)
			return;

		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("Salman");
		adminUser.setLastName("Khan");
		adminUser.setEmail("abc@hotmail.com");
		adminUser.setEmailVerificationStatus(true);
		adminUser.setUserId(utils.generateUserId(30));
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
		adminUser.setRoles(Arrays.asList(roleAdmin));

		userRepository.save(adminUser);

	}

	@Transactional
	private Authority createAuthority(String name) {

		Authority authority = authorityRepository.findByName(name);
		if (authority == null) {
			authority = new Authority(name);
			authorityRepository.save(authority);
		}
		return authority;
	}

	@Transactional
	private Role createRole(String name, Collection<Authority> authorities) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
			return role;
		}
		return null;
	}

}
