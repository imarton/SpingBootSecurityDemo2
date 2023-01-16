package dev.martoni.secdemo2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityController {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("pass")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("pass")
                .roles("USER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http
//                //.csrf(csrf->csrf.disable())
//                .authorizeHttpRequests()
//                    .requestMatchers("/").permitAll()
//                    .requestMatchers("/admin").hasRole("ADMIN")
//                    .anyRequest().hasRole("USER")
//                /*Az authorizeHttpRequsest() kezelésének másik módját lásd alább kommentben */
//                .and()
//                .httpBasic(Customizer.withDefaults())
//        ;
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                //.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/").permitAll();
                            auth.requestMatchers("/admin").hasRole("ADMIN");
                            auth.anyRequest().hasRole("USER");
                        }

                )
//                .formLogin()
                .httpBasic(Customizer.withDefaults())
        ;

        return http.build();
    }

}
