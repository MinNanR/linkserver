package site.minnan.linkserver.entites.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ToolsVO {

    private Integer id;
    private String fileName;
    private String extension;
    private Long size;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp updateTime;
}
