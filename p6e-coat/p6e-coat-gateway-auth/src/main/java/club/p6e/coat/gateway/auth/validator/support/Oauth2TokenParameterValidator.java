package club.p6e.coat.gateway.auth.validator.support;

import club.p6e.coat.gateway.auth.context.Oauth2Context;
import club.p6e.coat.gateway.auth.validator.ParameterValidatorInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * OAUTH2 AUTH 参数
 *
 * @author lidashuang
 * @version 1.0
 */
@Component
//@ConditionalOnExpression(Oauth2TokenParameterValidator.CONDITIONAL_EXPRESSION)
public class Oauth2TokenParameterValidator implements ParameterValidatorInterface {

    /**
     * 执行顺序
     */
    private static final int ORDER = 0;

    /**
     * 条件注册的条件表达式
     */
    public final static String CONDITIONAL_EXPRESSION = "#{${p6e.auth.oauth2.enable:false}}";

    /**
     * 授予类型参数
     */
    private static final String GRANT_TYPE_PARAM = "grant_type";

    /**
     * 重定向参数
     */
    private static final String REDIRECT_URI_PARAM = "redirect_uri";

    /**
     * 客户端编号参数
     */
    private static final String CLIENT_ID_PARAM = "client_id";

    /**
     * 客户端密钥参数
     */
    private static final String CLIENT_SECRET_PARAM = "client_secret";

    @Override
    public int order() {
        return ORDER;
    }

    @Override
    public Class<?> select() {
        return Oauth2Context.Token.Request.class;
    }

    @Override
    public Mono<Boolean> execute(ServerWebExchange exchange, Object data) {
        if (data instanceof final Oauth2Context.Token.Request param) {
            final ServerHttpRequest request = exchange.getRequest();
            if (param.getGrantType() == null) {
                param.setGrantType(request.getQueryParams().getFirst(GRANT_TYPE_PARAM));
            }
            if (param.getRedirectUri() == null) {
                param.setRedirectUri(request.getQueryParams().getFirst(REDIRECT_URI_PARAM));
            }
            if (param.getClientId() == null) {
                param.setClientId(request.getQueryParams().getFirst(CLIENT_ID_PARAM));
            }
            if (param.getClientId() == null) {
                param.setClientSecret(request.getQueryParams().getFirst(CLIENT_SECRET_PARAM));
            }
            return Mono.just(param.getGrantType() != null
                    && param.getClientId() != null
                    && param.getClientSecret() != null
                    && param.getRedirectUri() != null
                    && param.getCode() != null
                    && param.getUsername() != null
                    && param.getPassword() != null);
        }
        return Mono.just(false);
    }

}
