package br.com.amsj.spring.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier(value = "authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer)
			throws Exception {
		
		authorizationServerSecurityConfigurer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser("clark").password(encoder.encode("password")).roles("USER").and().
			withUser("bruce").password(encoder.encode("password")).roles("USER");
	}
	

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		System.out.println("--> configure 1");

		endpoints.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		System.out.println("--> configure 2");
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		clients.inMemory()
			.withClient("carServiceClient")
			.secret(encoder.encode("clientSecret"))
			.authorizedGrantTypes("authorization_code", "refresh_token", "password").scopes("openid")
			.authorities("USER")
			.redirectUris("http://localhost:9090/authCode")
			.accessTokenValiditySeconds(240) // access token is valid for 4 minute
			.refreshTokenValiditySeconds(600); // refresh token is valid for 10 minutes
		
	}

}
