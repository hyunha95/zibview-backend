package com.view.zib.domain.image.facade;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.service.ImageCommandService;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.domain.storage.service.StorageService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.exception.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageFacade {

    private final StorageService storageService;
    private final ImageCommandService imageCommandService;
    private final ImageQueryService imageQueryService;
    private final AuthService authService;
    private final ClockHolder clockHolder;

    /**
     * 이미지 파일을 저장하는 기능을 트랜잭션에서 분리
     *
     * @param saveImageRequest
     */
    public void saveImage(SaveImageRequest saveImageRequest) {
        // 이미지 저장 어뷰징 방지
        preventImageAbusing(saveImageRequest);

        // 스토리지에 이미지 파일 저장
        Map<String, Storage> storageByUuid = saveImageRequest.getImages().stream()
                .map(image -> storageService.store(image.getImage(), image.getUuid(), clockHolder))
                .collect(toMap(Storage::uuid, Function.identity()));

        // 데이터베이스에 이미지 데이터 저장
        imageCommandService.saveImage(saveImageRequest, storageByUuid);
    }

    public void deleteImage(String imageUuid) {
        User currentUser = authService.getCurrentUser();
        Image image = imageQueryService.getByUuid(imageUuid);

        if (!currentUser.isMyImage(image)) {
            log.error("{} has no permission to delete image({})", authService.getEmail(), imageUuid);
            throw new ForbiddenException("이미지 삭제 권한이 없습니다.");
        }

        // 스토리지에서 이미지 파일 삭제
        storageService.deleteImage(image);

        // 이미지 데이터 삭제(삭제 컬럼 업데이트)
        imageCommandService.delete(image);
    }

    public Resource getImage(String storedFilename) throws IOException {
        long start = System.currentTimeMillis();

        // 이미지 정보 가져오기
        Image image = imageQueryService.getByStoredFilename(storedFilename);
        Resource resource = storageService.loadAsResource(image);

        // 이미지 읽기
        BufferedImage bufferedImage;
        try (InputStream inputStream = resource.getInputStream()) {
            bufferedImage = ImageIO.read(inputStream);
        }

        // 이미지 회전
        int orientation = getOrientation(resource);
        bufferedImage = rotateImage(bufferedImage, orientation);

        // 이미지 크기 조정
        int originHeight = bufferedImage.getHeight();
        int originWidth = bufferedImage.getWidth();
        int targetHeight = Math.min(originHeight, 300);
        int targetWidth = Math.min(originWidth, 720);
        BufferedImage resizedBufferedImage = Thumbnails.of(bufferedImage)
//                .size(targetWidth, targetHeight)
                .width(targetWidth)
                .outputQuality(1)
//                .keepAspectRatio(true)
                .asBufferedImage();

        // 이미지 바이트 배열로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (baos) {
            ImageIO.write(resizedBufferedImage, image.getExtension(), baos);
        }
        byte[] bytes = baos.toByteArray();

        // 로깅
        log.info("Image({}kb) converting took {} ms", (double) bytes.length / 1024,  System.currentTimeMillis() - start);

        // 리소스 반환
        return new ByteArrayResource(bytes);
    }

    /**
     * 이미지 저장 어뷰징 방지
     *
     * @param saveImageRequest
     */
    private void preventImageAbusing(SaveImageRequest saveImageRequest) {
        List<Image> unassignedImages = imageQueryService.findByUserIdAndSubPostIdIsNullAndDeletedFalse(authService.getCurrentUser().getId());
        List<Image> imagesToDelete = imageCommandService.preventAbusing(saveImageRequest.getImages(), unassignedImages);
        imagesToDelete.forEach(storageService::deleteImage);
    }

    /**
     * EXIF 데이터를 읽어 이미지의 회전 정보를 얻는다.
     */
    private int getOrientation(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            ImageMetadata metadata = Imaging.getMetadata(inputStream.readAllBytes());
            if (metadata instanceof JpegImageMetadata jpegMetadata) {
                TiffField orientationField = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_ORIENTATION);
                if (orientationField != null) {
                    return orientationField.getIntValue();
                }
            }
        } catch (Exception e) {
            log.warn("Failed to read EXIF metadata", e);
        }
        return 1; // Default orientation
    }

    /**
     * 회전 정보를 기반으로 이미지를 회전시킨다.
     */
    private BufferedImage rotateImage(BufferedImage bufferedImage, int orientation) {
        int angle = 0;
        switch (orientation) {
            case 3:
                angle = 180;
                break;
            case 6:
                angle = 90;
                break;
            case 8:
                angle = 270;
                break;
            default:
                return bufferedImage;
        }

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), bufferedImage.getWidth() / 2.0, bufferedImage.getHeight() / 2.0);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
        Graphics2D g = rotatedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, op, 0, 0);
        g.dispose();
        return rotatedImage;
    }
}
