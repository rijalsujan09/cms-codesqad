package com.codesqad.cms;


import com.codesqad.cms.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true,  securedEnabled = true,  jsr250Enabled = true)
@RequiredArgsConstructor
public class CmsCodesqadApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CmsCodesqadApplication.class, args);
	}


	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;

	@Override
	public void run(String... args) throws Exception {
//		UserDtoSave usr = UserDtoSave.builder()
//				.firstName("Code")
//				.lastName("SQAD")
//				.email("admin@codesqad.com")
//				.username("admin")
//				.password("CodeSQAD!23")
//				.role(Role.SUPER_ADMIN).build();
//		this.authService.register(usr);
	}
}
