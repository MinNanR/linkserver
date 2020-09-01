package site.minnan.linkserver.entites;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("log")
public class Log {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 操作者id
     */
    private Integer userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 所进行的操作
     */
    private String operation;

    /**
     * 请求的路径
     */
    private String requestUri;

    /**
     * 请求参数
     */
    private String requestContent;

    /**
     * 响应参数
     */
    private String responseContent;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 用时
     */
    private Long costTime;

    /**
     *
     */
    private Timestamp createTime;
}
