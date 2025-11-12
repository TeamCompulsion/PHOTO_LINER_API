package kr.kro.photoliner.domain.photo.dto.response;

import kr.kro.photoliner.common.util.datetime.DateTimes;
import kr.kro.photoliner.domain.photo.model.Photo;

import java.time.LocalDate;
import java.util.List;

public record ViewportResponse(
        PhotosResponse photos,
        List<InnerPointResponse> points
) {
    public static ViewportResponse from(List<Photo> photos, LocalDate from, LocalDate to){
        PhotosResponse photosResponse = PhotosResponse.from(
                photos.stream()
                        .filter(it->isBetweenDate(from, to, it))
                        .toList()
        );

        List<InnerPointResponse> points = photos.stream()
                .filter(it->!isBetweenDate(from, to, it))
                .map(InnerPointResponse::from)
                .toList();

        return new ViewportResponse(photosResponse, points);
    }

    public record InnerPointResponse(
            Long id,
            double lat, // y
            double lng // x
    ){
        public static InnerPointResponse from(Photo photo){
            return new InnerPointResponse(
                    photo.getId(),
                    photo.getLocation().getY(),
                    photo.getLocation().getX()
            );
        }
    }

    private static boolean isBetweenDate(LocalDate from, LocalDate to, Photo photo){
        return DateTimes.isBetween(from, to, photo.getCapturedDt().toLocalDate());
    }

}
