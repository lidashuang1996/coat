package club.p6e.coat.gateway.auth.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 账号密码登录的密码签名
 *
 * @param <P> 参数对象类型
 * @param <R> 结果对象类型
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/login/account/signature")
public interface AccountPasswordLoginSignatureController<P, R> {

    /**
     * 条件注册的条件表达式
     */
    public final static String CONDITIONAL_EXPRESSION = "#{ "
            + "${p6e.auth.login.enable:false} "
            + "&& ${p6e.auth.login.account-password.enable:false} "
            + "&& ${p6e.auth.login.account-password.enable-transmission-encryption:false} "
            + "}";

    /**
     * [ GET ]
     * 账号密码登录的密码签名操作
     *
     * @param param 请求对象
     * @return 结果对象
     */
    @PostMapping("")
    public Mono<R> execute(ServerWebExchange exchange, P param);

}
