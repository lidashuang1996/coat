package club.p6e.coat.gateway.auth.context;

import club.p6e.coat.gateway.auth.AuthVoucherContext;
import lombok.Data;

import java.io.Serializable;

/**
 * 分片上传上下文对象
 *
 * @author lidashuang
 * @version 1.0
 */
@Data
public class VoucherContext implements Serializable {

    @Data
    public static class Request implements Serializable {
    }

}
