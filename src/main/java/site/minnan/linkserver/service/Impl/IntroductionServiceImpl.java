package site.minnan.linkserver.service.Impl;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.aliyun.oss.OSS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import site.minnan.linkserver.entites.DO.Image;
import site.minnan.linkserver.entites.DTO.AddImageDTO;
import site.minnan.linkserver.entites.DTO.DeleteImageDTO;
import site.minnan.linkserver.entites.DTO.UpdateIntroductionDTO;
import site.minnan.linkserver.entites.ResponseEntity;
import site.minnan.linkserver.entites.VO.IntroductionVO;
import site.minnan.linkserver.mapper.ImageMapper;
import site.minnan.linkserver.service.IntroductionService;
import site.minnan.linkserver.utils.RedisUtil;
import site.minnan.linkserver.utils.ResponseCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class IntroductionServiceImpl implements IntroductionService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OSS oss;

    @Autowired
    private ImageMapper imageMapper;

    @Value("${aliyun.bucketName}")
    private String bucketName;

    @Value("${aliyun.baseUrl}")
    private String baseUrl;

    @Override
    public ResponseEntity<?> addImage(AddImageDTO dto) throws IOException {
        MultipartFile image = dto.getImage();
        String originalFilename = image.getOriginalFilename();
        String[] fileExtensionArray = originalFilename.split("\\.");
        String fileExtension = fileExtensionArray[fileExtensionArray.length - 1].toLowerCase();
        String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
        String ossKey = String.format("%s.%s", uuidString, fileExtension);
        Image imageEntity = new Image();
        imageEntity.setUrl(ossKey);
        int i = imageMapper.insert(imageEntity);
        if (i > 0) {
            InputStream is = image.getInputStream();
            oss.putObject(bucketName, ossKey, is);
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "插入图片成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "添加图片失败");
    }

    @Override
    public List<Image> getImageList() {
        List<Image> imageList = imageMapper.selectList(null);
        imageList.forEach(image -> image.setUrl(String.format("%s/%s", baseUrl, image.getUrl())));
        return imageList;
    }

    @Override
    public ResponseEntity<?> deleteImage(DeleteImageDTO dto) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", dto.getId());
        Image image = imageMapper.selectOne(queryWrapper);
        int i = imageMapper.delete(queryWrapper);
        if (image != null && i>0) {
            oss.deleteObject(bucketName, image.getUrl());
            return new ResponseEntity<>(ResponseCode.CODE_SUCCESS, "删除成功");
        }
        return new ResponseEntity<>(ResponseCode.CODE_FAIL, "删除失败");
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
