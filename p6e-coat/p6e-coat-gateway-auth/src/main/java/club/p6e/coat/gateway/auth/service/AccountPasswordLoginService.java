package club.p6e.coat.gateway.auth.service;

import club.p6e.coat.gateway.auth.AuthUserDetails;
import club.p6e.coat.gateway.auth.context.LoginContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 账号密码登录服务
 *
 * @author lidashuang
 * @version 1.0
 */
public interface AccountPasswordLoginService {

    /**
     * 条件注册的条件表达式
     */
    String CONDITIONAL_EXPRESSION = "#{${p6e.auth.login.enable:false} && ${p6e.auth.login.account-password.enable:false}}";

    /**
     * 执行账号密码登录操作
     *
     * @param param 请求对象
     * @return 结果对象
     */
    public Mono<AuthUserDetails> execute(ServerWebExchange exchange, LoginContext.AccountPassword.Request param);

}
