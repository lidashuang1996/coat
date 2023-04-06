package club.p6e.coat.file;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置文件
 *
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "p6e.coat.file")
public class Properties implements Serializable {

    /**
     * 分片上传配置
     */
    private SliceUpload sliceUpload = new SliceUpload();

    /**
     * 简单（小文件）上传配置
     */
    private SimpleUpload simpleUpload = new SimpleUpload();

    /**
     * 资源配置
     */
    private Map<String, Resource> resources = new HashMap<>();

    /**
     * 下载配置
     */
    private Map<String, Download> downloads = new HashMap<>();

    /**
     * 下载
     */
    @Data
    @Accessors(chain = true)
    public static class Download implements Serializable {

        /**
         * 基础的文件路径
         */
        private String path;

    }

    /**
     * 资源
     */
    @Data
    @Accessors(chain = true)
    public static class Resource implements Serializable {

        /**
         * 基础的文件路径
         */
        private String path;

        /**
         * 允许的文件后缀以及对应的媒体类型
         */
        private Map<String, MediaType> suffixes = new HashMap<>();

    }

    /**
     * 简单（小文件）上传
     */
    @Data
    public static class SimpleUpload implements Serializable {

        /**
         * 基础的文件路径
         */
        private String path = "/Users/admin/Documents/2023/coat/p6e-coat/";

        /**
         * 允许上传的文件大小的最大值
         */
        private long maxSize = 1024 * 1024 * 15;

    }

    /**
     * 分片上传
     */
    @Data
    @Accessors(chain = true)
    public static class SliceUpload implements Serializable {

        /**
         * 基础的文件路径
         */
        private String path = "/Users/admin/Documents/2023/coat/p6e-coat/";
//        private String path = "F://a/";

        /**
         * 允许上传的文件大小的最大值
         */
        private long maxSize = 1024 * 1024 * 15;

    }

    public Properties() {
        this.downloads.put("test", new Download().setPath("/Users/admin/Documents/2023/coat/p6e-coat/"));
        final Resource resource = new Resource().setPath("/Users/admin/Documents/2023/coat/p6e-coat/");
        resource.getSuffixes().put("png", MediaType.IMAGE_PNG);
        resource.getSuffixes().put("jpg", MediaType.IMAGE_JPEG);
        resource.getSuffixes().put("jpeg", MediaType.IMAGE_JPEG);
        resource.getSuffixes().put("mp4", MediaType.valueOf("video/mp4"));
        this.resources.put("test", resource);
    }
}