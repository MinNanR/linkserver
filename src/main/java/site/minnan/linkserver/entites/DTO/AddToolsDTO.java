package site.minnan.linkserver.entites.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class AddToolsDTO {

    /**
     * 工具名称
     */
    private String name;

    /**
     * 上传的文件
     */
    private MultipartFile file;
}
