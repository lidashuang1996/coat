package club.p6e.cloud.test.infrastructure.context;

import com.darvi.hksi.badminton.lib.SearchableContext;
import com.darvi.hksi.badminton.lib.SortableContext;
import com.darvi.hksi.badminton.lib.context.BaseContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Oauth2Context implements Serializable {

    public static class Client {
        @Data
        @Accessors(chain = true)
        @EqualsAndHashCode(callSuper = true)
        public static class Request extends BaseContext.PagingParam implements Serializable {
            private String query;
            private SortableContext<SortableContext.Option> sort;
            private SearchableContext<SearchableContext.Option> search;

            private Integer id;
            private String type;
            private String key;
            private String value;
            private String language;
        }

        @Data
        @Accessors(chain = true)
        public static class Vo implements Serializable {
            private Integer id;
            private String enabled;
            private String type;
            private String scope;
            private String redirectUri;
            private String reconfirm;
            private String clientId;
            private String clientName;
            private String clientSecret;
            private String clientDescribe;
            private String clientAvatar;
            private LocalDateTime createDate;
            private LocalDateTime updateDate;
            private String operator;
        }

        @Data
        @Accessors(chain = true)
        @EqualsAndHashCode(callSuper = true)
        public static class ListVo extends BaseContext.ListResult implements Serializable {
            private List<Item> list;
        }


        @Data
        @Accessors(chain = true)
        public static class Dto implements Serializable {
            private Integer id;
            private String enabled;
            private String type;
            private String scope;
            private String redirectUri;
            private String reconfirm;
            private String clientId;
            private String clientName;
            private String clientSecret;
            private String clientDescribe;
            private String clientAvatar;
            private LocalDateTime createDate;
            private LocalDateTime updateDate;
            private String operator;
        }

        @Data
        @Accessors(chain = true)
        @EqualsAndHashCode(callSuper = true)
        public static class ListDto extends BaseContext.ListResult implements Serializable {
            private List<Item> list;
        }

        @Data
        @Accessors(chain = true)
        public static class Item implements Serializable {
            private Integer id;
            private String enabled;
            private String type;
            private String scope;
            private String redirectUri;
            private String reconfirm;
            private String clientId;
            private String clientName;
            private String clientDescribe;
            private String clientAvatar;
            private LocalDateTime createDate;
            private LocalDateTime updateDate;
            private String operator;
        }
    }



}
