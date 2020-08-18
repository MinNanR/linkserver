package site.minnan.linkserver.entites.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateLinkDTO {

    /**
     * 主键id
     */
    @NotNull
    private Integer id;

    /**
     * 节点名称
     */
    @NotBlank
    private String name;

    /**
     * 节点链接
     */
    @NotBlank
    private String link;
}
