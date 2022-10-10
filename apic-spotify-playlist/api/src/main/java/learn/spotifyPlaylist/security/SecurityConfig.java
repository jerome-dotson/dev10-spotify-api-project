package learn.spotifyPlaylist.security;


//imports

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Add JwtConverter as a dependency
    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter){
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();


        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll() //give all users the ability to authenticate
                // new...
                .antMatchers("/create_account").permitAll()
                // new...
                .antMatchers("/refresh_token").authenticated()
                .antMatchers(HttpMethod.GET, "/{GetURL}").permitAll()
                .antMatchers(HttpMethod.GET, "/{GetDifferentURL}", "/{GetDifferentURL}/*").permitAll() //* denotes wildcard for any identifier
                .antMatchers(HttpMethod.POST, "/{PostURL}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/{PutURL}/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/{DeleteURL}/*").hasAnyRole("ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}
