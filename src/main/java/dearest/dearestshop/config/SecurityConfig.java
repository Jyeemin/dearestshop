package dearest.dearestshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Security 규칙 설정
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {


        http
                .cors(cors -> {})
                // React + JWT 방식에서는 보통 사용하지 않음
                .csrf(csrf -> csrf.disable())


                // URL 접근 권한 설정
                .authorizeHttpRequests(auth -> auth


                        // 회원가입은 누구나 가능
                        .requestMatchers(
                                "/api/members/join"
                        )
                        .permitAll()


                        // 나머지는 일단 모두 허용
                        // (개발 초기 단계)
                        .anyRequest()
                        .permitAll()
                );


        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }






}
