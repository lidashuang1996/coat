package club.p6e.coat.gateway.auth.service;


import club.p6e.coat.gateway.auth.context.Oauth2Context;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * OAUTH2 认证服务
 *
 * @author lidashuang
 * @version 1.0
 */
public interface Oauth2AuthService {

    /**
     * 条件注册的条件表达式
     */
    public final static String CONDITIONAL_EXPRESSION =
            "#{${p6e.auth.oauth2.enable:false} && ${p6e.auth.oauth2.authorization-code.enable:false}}";

    /**
     * 执行 OAUTH2 认证
     *
     * @param param 请求对象
     * @return 结果对象
     */
    public Mono<Void> execute(ServerWebExchange exchange, Oauth2Context.Auth.Request param);

}
