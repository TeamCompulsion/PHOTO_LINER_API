package kr.kro.photoliner.domain.photo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PhotoCapturedDateUpdateRequest(
        @NotNull(message = "캡쳐 날짜는 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime capturedDt
) {

}
