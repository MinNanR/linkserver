package site.minnan.linkserver.entites.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@TableName("tools")
@NoArgsConstructor
public class Tools {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工具名称
     */
    private String fileName;

    /**
     * oss中的key键
     */
    private String ossKey;

    /**
     * 扩张名
     */
    private String extension;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
