package club.p6e.coat.file.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传模型
 *
 * @author lidashuang
 * @version 1.0
 */
@Data
@Table(UploadModel.TABLE)
@Accessors(chain = true)
public class UploadModel implements Serializable {

    public static final String TABLE = "hksi_file_upload";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String STORAGE_LOCATION = "storageLocation";
    public static final String CREATE_DATE = "createDate";
    public static final String UPDATE_DATE = "updateDate";
    public static final String OPERATOR = "operator";
    public static final String LOCK = "lock";
    public static final String VERSION = "version";

    @Id
    private Integer id;
    private String name;
    private String storageLocation;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String operator;
    private Integer lock;
    private Integer version;


    public Map<String, Object> toMap() {
        final Map<String, Object> map = new HashMap<>(8);
        map.put("id", id);
        map.put("name", name);
        map.put("storageLocation", storageLocation);
        map.put("createDate", createDate);
        map.put("updateDate", updateDate);
        map.put("operator", operator);
        map.put("lock", lock);
        map.put("version", version);
        return map;
    }
}