//package club.p6e.coat.gateway.filter;
//
//import club.p6e.coat.gateway.Properties;
//import club.p6e.coat.gateway.WebFilterOrder;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
///**
// * Referer 过滤器
// *
// * @author lidashuang
// * @version 1.0
// */
//@Component
//public class RefererWebFilter implements WebFilter, Ordered {
//
//    /**
//     * 执行顺序
//     */
//    protected static final WebFilterOrder ORDER = WebFilterOrder.REFERER;
//
//    /**
//     * Referer 头名称
//     */
//    protected static final String REFERER_HEADER = "Referer";
//
//    /**
//     * Referer 适配内容
//     */
//    protected static final String REFERER_HEADER_ADAPTIVE_CONTENT = "*";
//
//    /**
//     * 配置文件对象
//     */
//    protected final Properties properties;
//
//    /**
//     * 构造方法初始化
//     *
//     * @param properties 配置文件对象
//     */
//    public RefererWebFilter(Properties properties) {
//        this.properties = properties;
//    }
//
//    @Override
//    public int getOrder() {
//        return ORDER.getOrder();
//    }
//
//    @NonNull
//    @Override
//    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
//        if (!properties.getReferer().isEnabled()) {
//            return chain.filter(exchange);
//        }
//        final ServerHttpRequest request = exchange.getRequest();
//        final List<String> refererList = request.getHeaders().get(REFERER_HEADER);
//        if (refererList != null && refererList.size() > 0) {
//            for (final String s : refererList) {
//                for (final String w : properties.getReferer().getWhiteList()) {
//                    if (!(REFERER_HEADER_ADAPTIVE_CONTENT.equalsIgnoreCase(w) || s.startsWith(w))) {
//                        return Mono.empty();
//                    }
//                }
//            }
//            return chain.filter(exchange);
//        } else {
//            for (final String w : properties.getReferer().getWhiteList()) {
//                if (REFERER_HEADER_ADAPTIVE_CONTENT.equalsIgnoreCase(w)) {
//                    return chain.filter(exchange);
//                }
//            }
//        }
//        return Mono.empty();
//    }
//
//}
