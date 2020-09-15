package site.minnan.linkserver.service.Impl;

import com.aliyun.oss.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import site.minnan.linkserver.entites.DTO.AddImageDTO;
import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;
import site.minnan.linkserver.service.IntroductionService;
import site.minnan.linkserver.utils.RedisUtil;
import site.minnan.linkserver.utils.ResponseCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class IntroductionServiceImpl implements IntroductionService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OSS oss;

    @Value("${aliyun.bucketName}")
    private String bucketName;

    @Value("${aliyun.baseUrl}")
    private String baseUrl;

    @Override
    public ResponseEntity<String> addImage(AddImageDTO dto) throws IOException {
        MultipartFile image = dto.getImage();
        String originalFilename = image.getOriginalFilename();
        String[] fileExtensionArray = originalFilename.split("\\.");
        String fileExtension = fileExtensionArray[fileExtensionArray.length - 1].toLowerCase();
        String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
        String ossKey = String.format("%s.%s", uuidString, fileExtension);
        InputStream is = image.getInputStream();
        oss.putObject(bucketName, ossKey, is);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "插入图片成功");
        responseEntity.setData(String.format("%s/%s", baseUrl, ossKey));
        return responseEntity;
    }

    @Override
    public ResponseEntity<?> updateIntroduction(UpdateIntroductionDTO dto) {
        redisUtil.valueSet("introduction", dto.getContent());
        return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "修改成功");
    }

    @Override
    public IntroductionVO getIntroduction() {
        String introduction = (String) redisUtil.getValue("introduction");
        return new IntroductionVO(StringUtils.isEmpty(introduction) ? "敬请期待" : introduction);
    }
}
