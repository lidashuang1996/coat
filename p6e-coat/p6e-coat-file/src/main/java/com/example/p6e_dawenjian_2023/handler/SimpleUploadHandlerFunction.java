package com.example.p6e_dawenjian_2023.handler;

import com.example.p6e_dawenjian_2023.aspect.SimpleUploadAspect;
import com.example.p6e_dawenjian_2023.context.SimpleUploadContext;
import com.example.p6e_dawenjian_2023.mapper.RequestParameterMapper;
import com.example.p6e_dawenjian_2023.service.SimpleUploadService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 简单（小文件）上传操作处理程序函数
 *
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConditionalOnMissingBean(
        value = SimpleUploadHandlerFunction.class,
        ignored = SimpleUploadHandlerFunction.class
)
public class SimpleUploadHandlerFunction extends AspectHandlerFunction implements HandlerFunction<ServerResponse> {

    /**
     * 简单（小文件）上传切面对象
     */
    private final SimpleUploadAspect aspect;

    /**
     * 简单（小文件）上传服务对象
     */
    private final SimpleUploadService service;

    /**
     * 构造函数初始化
     *
     * @param aspect  简单（小文件）上传切面对象
     * @param service 简单（小文件）上传服务对象
     */
    public SimpleUploadHandlerFunction(SimpleUploadAspect aspect, SimpleUploadService service) {
        this.aspect = aspect;
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return
                // 通过请求参数映射器获取上下文对象
                RequestParameterMapper.execute(request, SimpleUploadContext.class)
                        // 执行简单（小文件）上传操作之前的切点
                        .flatMap(c -> before(aspect, c.toMap()))
                        .flatMap(m -> {
                            final SimpleUploadContext context = new SimpleUploadContext(m);
                            return
                                    // 执行简单（小文件）上传服务
                                    service.execute(context)
                                            // 执行简单（小文件）上传操作之后的切点
                                            .flatMap(r -> after(aspect, context.toMap(), r));
                        })
                        // 结果返回
                        .flatMap(r -> ServerResponse.ok().bodyValue(r));
    }

}
