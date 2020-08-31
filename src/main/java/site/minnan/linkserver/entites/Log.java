package site.minnan.linkserver.entites;

import lombok.Data;

@Data
public class Log {

    /**
     * 主键id
     */
    private String id;

    /**
     * 操作者id
     */
    private Integer userId;

    /**
     * 操作用户名
     */
    String username;

    /**
     * 请求参数
     */
    String requestContent;

    /**
     * 响应参数
     */
    String responseContent;

    /**
     * 登录IP
     */
    String ip;
}
