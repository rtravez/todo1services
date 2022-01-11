package com.todo1.hulkstore.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * <b> Descripcion de la clase, interface o enumeracion.</b>
 * 
 * @author renetravez
 * @version $1.0$
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private InfoAdionalToken infoAdionalToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("frontend-hulkstore").secret(passwordEncoder.encode("12345")).scopes("read", "write").authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(3600);

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdionalToken, accessTokenConverter()));
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter()).tokenEnhancer(tokenEnhancerChain);
	}

	/**
	 * <b> Incluir aqui la descripcion del metodo. </b>
	 * <p>
	 * [Author renetravez, 21 sep. 2020]
	 * </p>
	 *
	 * @return TokenStore
	 */

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	/**
	 * <b> Metodo que permite almacenar los datos de autentificacion. </b>
	 * <p>
	 * [Author renetravez, 21 sep. 2020]
	 * </p>
	 *
	 * @return AccessTokenConverter
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		// jwtAccessTokenConverter.setSigningKey("EEmilia2020**");
		jwtAccessTokenConverter.setSigningKey("-----BEGIN RSA PRIVATE KEY-----\n" + "MIIEpQIBAAKCAQEA2zUlsY+k76XrxL1j0VQWXbA6s/4KwM49+at2qKeRzmJGSIfj\n"
				+ "ESEz2XVtF2gQ4ERQXf4KnO9QmnRZnxFmxU8waJ7H8UTrvamp3QXCQywkbPym+EUd\n" + "Q+Ar+bubyvT2Vs9OELsw+2mnqeQFbH3/QdGIRf9JXn1sZdkAYBNA533ifzausjJH\n"
				+ "P+3NxGfQOC8X4faFMNIvCKpGkpM+r+7iWKbUeIM3smCSBHTprs/XdJ0eGa1HTd5o\n" + "nN2/pQC4bVAngWXSagCK6xhPm5c5P4w0L2uXu2JgG8rgIa6rAs1Zzvca6uvAOrk5\n"
				+ "e3yFc82AoldTWNzNAdMXbMlxdClcCpWzkEgeTQIDAQABAoIBAQCiC4zc0OJNoitD\n" + "GfozIWUTRxZuPms2kn/wFBe4n39CwRVRwtpRrGjG5W6KsgxfrUB36zdvQd4k/8PM\n"
				+ "ujFtxJd1j4Mg0SEv6L9AO2SbAYO/6ZmIyJbUuV/o0g5eH24HSGJeLfMMt9MQ3RZB\n" + "WHcc8cc6b3iOWEuMkrkbTMBbII0SGjyUZoL5fPWQ70Rjp0c77E4nlCaZDfu67VSy\n"
				+ "JEy5V3+P71B2rjJ83Ag5eIWIntYmdawiaRxuqySnAvoIkIZSkpz6h2ei92MfbDUd\n" + "e5p6YXI3VyS9CLhUkIqUwAqTqtKdaGqu6VX+hXq7LiZQsuslP34qv90vLrwNGPjC\n"
				+ "3JchotwFAoGBAPoHwsNKCMzrUXJSSRGBHNSUjWCHkmFESdqE0/TIVw/CbL+lop3K\n" + "USRGrzqt0Z5lmKZ1OFcGKTqaGxFd+OOcYigj5tRjBjSRygeJpqVlis9a6qV9eSB8\n"
				+ "LF3cVPhH49IUruHdhFScaH60qb9UkL0P4tffcat3d+MSTj9ksX/cXfc7AoGBAOBw\n" + "/cyTMVZmBsQu6pSWwuzrM5BJs7siONfoy3M+T7X2BAkq7/I8FN/uLoPRoXWlbGDt\n"
				+ "14+GwolqwMj1w0SeNRcFNvL+7SnwxjCbmATLMxG6FaUGzMbnyH1YYXuuKO6dT80Y\n" + "BX71DWoh27rXlaALQSNHfv9Ppq/+1ilTVJRZJjgXAoGBAIjMUDprOk9r/L7w2HuU\n"
				+ "ScLhmEyVQHipVUMS46kNJ90+giOFK2jrteOELQGM8qCYc6G3huDORSaEBIVHBhL6\n" + "kBXoNrZDi7Sdg40GOkBKQ11aIAH1mZ01c0zTbUX+h1585IY9ndZxURbqH+nICfz3\n"
				+ "QSTdYyLulZCOhnNW02riliLbAoGBAMMsnxhqm8n1WUb7QZVLcWETu7hqzzgZr3La\n" + "hynFcw3aTXIne5eb9c8x0hNhldW3sAKucfjnCH71bYaUaWVfau2E5F71nwoMzlxw\n"
				+ "wgbRKazttGrY+fSylR+jXspuDjxKYp4ImKbptOupW+okFYKiptdmEiM1gc+kfVpc\n" + "k72ZtOmrAoGAOOOObxE0CeFZkrhoRk8i81/vSP4gR2T8tPn4P7GDSI2HVGKrKq8m\n"
				+ "7TRbWz+KRLntr3crVxBemh3m7oLVVeDmFJTVVEyVuGgt6cnvRse8nM4NujE9dluT\n" + "oQpQIL9dLhAWQHwjr+bJ//BAZPvx6h3kAnzqflCKvcOHXQq3AqPTBac=\n" + "-----END RSA PRIVATE KEY-----");
		jwtAccessTokenConverter.setVerifierKey("-----BEGIN PUBLIC KEY-----\n" + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2zUlsY+k76XrxL1j0VQW\n"
				+ "XbA6s/4KwM49+at2qKeRzmJGSIfjESEz2XVtF2gQ4ERQXf4KnO9QmnRZnxFmxU8w\n" + "aJ7H8UTrvamp3QXCQywkbPym+EUdQ+Ar+bubyvT2Vs9OELsw+2mnqeQFbH3/QdGI\n"
				+ "Rf9JXn1sZdkAYBNA533ifzausjJHP+3NxGfQOC8X4faFMNIvCKpGkpM+r+7iWKbU\n" + "eIM3smCSBHTprs/XdJ0eGa1HTd5onN2/pQC4bVAngWXSagCK6xhPm5c5P4w0L2uX\n"
				+ "u2JgG8rgIa6rAs1Zzvca6uvAOrk5e3yFc82AoldTWNzNAdMXbMlxdClcCpWzkEge\n" + "TQIDAQAB\n" + "-----END PUBLIC KEY-----");
		return jwtAccessTokenConverter;
	}

}
