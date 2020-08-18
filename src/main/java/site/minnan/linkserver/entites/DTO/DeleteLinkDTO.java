package site.minnan.linkserver.entites.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteLinkDTO {

    /**
     * 链接数据库id
     */
    @NotNull
    private Integer id;
}
