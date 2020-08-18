package site.minnan.linkserver.entites.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddLinkDTO {
    /**
     * 链接名称
     */
    @NotBlank
    private String name;

    /**
     * 链接url
     */
    @NotBlank
    private String link;
}
