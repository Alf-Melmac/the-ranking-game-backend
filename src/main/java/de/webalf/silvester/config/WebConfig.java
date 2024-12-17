package de.webalf.silvester.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Alf
 * @since 15.12.2024
 */
@Configuration
@EnableWebSecurity
public class WebConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								.anyRequest().permitAll()
				)

				.csrf(CsrfConfigurer::disable)

				.build();
	}
}
