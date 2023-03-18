package com.example.proyectoIntegradorE8.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    //BEAN: es una anotación de Spring que indica que el método anotado debe devolver un objeto que Spring debe administrar en su contenedor.
    @Bean
    //
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
//La configuración de seguridad en Spring se realiza a través de la clase WebSecurityConfigurerAdapter.
// El método filterChain es parte de esta configuración y devuelve un objeto SecurityFilterChain,
// que se utiliza para configurar la seguridad de la aplicación web.
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return http
                //desactiva la protección CSRF, ya que la aplicación usará JSON Web Tokens (JWT) para la autenticación.
                .csrf().disable()
                //inicia la configuración de autorización para las solicitudes.
                .authorizeHttpRequests()
                //indica que cualquier solicitud debe ser autorizada.
                .anyRequest()
                // indica que la solicitud debe estar autenticada para ser autorizada.
                .authenticated()
                .and()
                //inicia la configuración de gestión de sesiones.
                .sessionManagement()
                // indica que no se creará ninguna sesión para las solicitudes, ya que la autenticación se realiza mediante JWT,
                // lo que significa que no es necesario almacenar información de sesión en el servidor.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //se encarga de la autenticación de los usuarios mediante JWT.
                .addFilter(jwtAuthenticationFilter)
                // agrega el filtro jwtAuthorizationFilter a la cadena de filtros de seguridad,
                // que se ejecuta antes del filtro de autenticación basado en nombre de usuario y contraseña
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                //construye la cadena de filtros de seguridad y devuelve un objeto SecurityFilterChain que
                // se utiliza para configurar la seguridad de la aplicación web.
                .build();

    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles()
//                .build());
//        return manager;
//    }


    //Este @Bean define y configura un AuthenticationManager que se utilizará para autenticar a los usuarios en la aplicación.
    //El AuthenticationManager define un método authenticate que se utiliza para autenticar a un usuario.
    // El método authManager define este objeto y le proporciona dos argumentos:
    //HttpSecurity http: se utiliza para obtener una instancia compartida de AuthenticationManagerBuilder,
    // que es una clase de ayuda que se utiliza para configurar y crear un AuthenticationManager en Spring Security.
    //PasswordEncoder passwordEncoder: un objeto PasswordEncoder que se utiliza para cifrar y descifrar contraseñas.
    //El método userDetailsService se utiliza para configurar un UserDetailsService que proporciona detalles de usuario a AuthenticationManager.
    //El método passwordEncoder se utiliza para proporcionar un objeto PasswordEncoder que se utilizará para cifrar y
    // descifrar contraseñas de usuario.
    @Bean
    AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();

    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
