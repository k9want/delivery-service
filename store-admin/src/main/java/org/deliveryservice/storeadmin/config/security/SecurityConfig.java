package org.deliveryservice.storeadmin.config.security;

import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity // security 활성화
public class SecurityConfig {

    private final List<String> SWAGGER = List.of(
        "/swagger-ui/index.html",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);
        httpSecurity
            .csrf(CsrfConfigurer::disable)
            .requestCache(request -> {
                request.requestCache(requestCache);
            })
            .authorizeHttpRequests(it -> {
                it
                    .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                    ).permitAll()  // resource에 대해서는 모든 요청 허용

                    // open-api/** 하위 모든 주소는 인증 없이 통과
                    .requestMatchers(
                        "/open-api/**"
                    ).permitAll()  // resource에 대해서는 모든 요청 허용

                    // swagger는 인증 없이 통과
                    .requestMatchers(
                        SWAGGER.toArray(new String[0])
                    ).permitAll()

                    // 그 외 모든 요청은 인증 사용
                    .anyRequest().authenticated();
            })
            .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // hash로 암호화
        return new BCryptPasswordEncoder();
    }
}
