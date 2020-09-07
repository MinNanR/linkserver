package site.minnan.linkserver.entites.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import site.minnan.linkserver.serializer.MultipartFileSerializer;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddToolsDTO implements Serializable, FileDTO {

    /**
     * 工具名称
     */
    private String name;

    /**
     * 上传的文件
     */
    private MultipartFile file;

    @Override
    public MultipartFile getMultipartFile() {
        return file;
    }
}
