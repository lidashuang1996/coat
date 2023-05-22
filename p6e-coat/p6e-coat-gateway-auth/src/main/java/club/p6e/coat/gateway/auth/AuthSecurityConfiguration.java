package club.p6e.coat.gateway.auth;

import club.p6e.coat.gateway.auth.oauth2.AuthReactiveClientRegistrationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginReactiveAuthenticationManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationCodeAuthenticationTokenConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.*;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
@Configuration
@EnableWebFluxSecurity
public class AuthSecurityConfiguration {

    public AuthenticationWebFilter createAuthenticationWebFilter(
            ReactiveAuthenticationManager manager, ServerAuthenticationConverter converter) {
        final AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(manager);
        authenticationWebFilter.setServerAuthenticationConverter(converter);
        authenticationWebFilter.setAuthenticationFailureHandler(
                new ServerAuthenticationEntryPointFailureHandler(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)));
        return authenticationWebFilter;
    }

    @Bean
    public SecurityWebFilterChain injectionSecurityWebFilterChainBean(
            ServerHttpSecurity http,
            AuthGatewayWebPathMatcher gMatcher,
            AuthReactiveAuthenticationManager aManager,
            AuthServerAuthenticationConverter converter,
            AuthOauth2WebPathMatcher oMatcher,
            AuthOauth2ServerAuthenticationConverter oConverter,
            AuthReactiveClientRegistrationRepository clientRegistrationRepository
    ) {
        return http
                .oauth2Login(new Customizer<ServerHttpSecurity.OAuth2LoginSpec>() {
                    @Override
                    public void customize(ServerHttpSecurity.OAuth2LoginSpec spec) {
                        OAuth2LoginReactiveAuthenticationManager oManager =
                                new OAuth2LoginReactiveAuthenticationManager(
                                        clientRegistrationRepository,
                                        new AuthOauth2WebClientReactiveAuthorizationCodeTokenResponseClient()
                                );
                        spec
                                .authenticationManager(oManager)
                                .authenticationMatcher(oMatcher)
                                .clientRegistrationRepository(clientRegistrationRepository);
                    }
                })
                .oauth2ResourceServer(new Customizer<ServerHttpSecurity.OAuth2ResourceServerSpec>() {
                    @Override
                    public void customize(ServerHttpSecurity.OAuth2ResourceServerSpec spec) {
                        spec.opaqueToken(new Customizer<ServerHttpSecurity.OAuth2ResourceServerSpec.OpaqueTokenSpec>() {
                            @Override
                            public void customize(ServerHttpSecurity.OAuth2ResourceServerSpec.OpaqueTokenSpec opaqueTokenSpec) {
                                opaqueTokenSpec.introspectionClientCredentials();
                            }
                        })
                    }
                })
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .securityMatcher(gMatcher)
                .addFilterAfter(createAuthenticationWebFilter(aManager, converter), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

}
