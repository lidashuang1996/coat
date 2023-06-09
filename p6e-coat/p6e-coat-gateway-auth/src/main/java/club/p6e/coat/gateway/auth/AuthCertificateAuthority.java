package club.p6e.coat.gateway.auth;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface AuthCertificateAuthority {
    public Mono<Object> present(ServerWebExchange exchange, AuthUser user);
}
