package com.view.zib.domain.image.controller.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SaveImageRequestTest {

    @Test
    void imagesCanBeNull() {
        SaveImageRequest saveImageRequest = new SaveImageRequest(null);
        assertNull(saveImageRequest.getImages());
    }

    @Test
    void imagesMoreThan10ShouldFail() {
        // given
        List<SaveImageRequest.Image> images = IntStream.rangeClosed(1, 11)
                .mapToObj(i -> new SaveImageRequest.Image(UUID.randomUUID().toString(), "", "", "", new MockMultipartFile("image" + i, new byte[0])))
                .toList();
        SaveImageRequest saveImageRequest = new SaveImageRequest(images);

        // when
        Set<ConstraintViolation<SaveImageRequest>> validate = getConstraintViolations(saveImageRequest);

        // then
        for (ConstraintViolation<SaveImageRequest> saveImageRequestConstraintViolation : validate) {
            Assertions.assertThat(saveImageRequestConstraintViolation.getMessage()).isEqualTo("크기가 0에서 10 사이여야 합니다");
        }
    }

    private static Set<ConstraintViolation<SaveImageRequest>> getConstraintViolations(SaveImageRequest saveImageRequest) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SaveImageRequest>> validate = validator.validate(saveImageRequest);
        return validate;
    }


}