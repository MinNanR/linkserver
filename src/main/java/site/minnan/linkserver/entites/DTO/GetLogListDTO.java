package site.minnan.linkserver.entites.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetLogListDTO {

    /**
     * 页码
     */
    @NotNull
    private Integer pageIndex;

    /**
     * 每页数量
     */
    @NotNull
    private Integer pageSize;

    /**
     * 操作类型
     */
    private String operation;
}
