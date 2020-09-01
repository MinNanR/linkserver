package site.minnan.linkserver.entites.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class LogListVO {

    @Data
    public static class LogVO{
        private String username;

        private String requestUri;

        private String ip;

        private String operation;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
        private Timestamp createTime;
    }

    private List<LogVO> list;

    private Integer totalCount;


}
