package club.p6e.coat.file.mapper;

import club.p6e.coat.file.context.DownloadContext;
import club.p6e.coat.file.error.ParameterException;
import club.p6e.coat.file.utils.FileUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 打开上传请求参数映射器
 *
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConditionalOnMissingBean(
        value = DownloadContextRequestParameterMapper.class,
        ignored = DownloadContextRequestParameterMapper.class
)
public class DownloadContextRequestParameterMapper extends RequestParameterMapper {

    /**
     * NODE 请求参数
     */
    private static final String NODE_PARAMETER_NAME = "node";

    /**
     * PATH 请求参数
     */
    private static final String PATH_PARAMETER_NAME = "path";

    @Override
    public Class<?> outputClass() {
        return DownloadContext.class;
    }

    @Override
    public Mono<Object> execute(@RequestBody ServerRequest request) {
        final ServerHttpRequest httpRequest = request.exchange().getRequest();
        final MultiValueMap<String, String> queryParams = httpRequest.getQueryParams();
        final DownloadContext context = new DownloadContext();
        context.putAll(queryParams);
        final List<String> nodes = queryParams.get(NODE_PARAMETER_NAME);
        if (nodes != null && nodes.size() > 0) {
            context.setNode(nodes.get(0));
        } else {
            throw new ParameterException(this.getClass(),
                    "<" + NODE_PARAMETER_NAME + "> request parameter is null");
        }
        final List<String> paths = queryParams.get(PATH_PARAMETER_NAME);
        if (paths != null && paths.size() > 0) {
            final String pc = paths.get(0);
            final String name = FileUtil.name(pc);
            final String path = FileUtil.path(pc);
            if (name == null || path == null) {
                context.setPath(FileUtil.composePath(path, name));
            } else {
                throw new ParameterException(this.getClass(),
                        "<" + PATH_PARAMETER_NAME + "> request parameter format error");
            }
        } else {
            throw new ParameterException(this.getClass(),
                    "<" + PATH_PARAMETER_NAME + "> request parameter is null");
        }
        return Mono.just(context);
    }

}