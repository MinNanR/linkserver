package site.minnan.linkserver.entites.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddImageDTO implements Serializable,FileDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 上传的文件
     */
    private MultipartFile image;

    @Override
    public MultipartFile getMultipartFile() {
        return image;
    }
}
