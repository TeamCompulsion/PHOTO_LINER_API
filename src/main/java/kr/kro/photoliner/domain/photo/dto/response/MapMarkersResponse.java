package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.kro.photoliner.domain.photo.model.Photo;

public record MapMarkersResponse(
    InnerPhotoMarkers innerPhotoMarkers,
    InnerPoiMarkers innerPoiMarkers
) {

  public static MapMarkersResponse of(List<Photo> photosInDate, List<Photo> photosOutOfDate) {
    return new MapMarkersResponse(
        InnerPhotoMarkers.from(photosInDate),
        InnerPoiMarkers.from(photosOutOfDate)
    );
  }

  public record InnerPhotoMarkers(
      Integer count,
      List<InnerPhotoMarker> photoMarkers
  ) {

    public static InnerPhotoMarkers from(List<Photo> photos) {
      return new InnerPhotoMarkers(
          photos.size(),
          photos.stream()
              .map(InnerPhotoMarker::from)
              .toList()
      );
    }

    public record InnerPhotoMarker(
        Long id,
        LocalDateTime capturedDt,
        String filePath,
        double lat,
        double lng
    ) {

      public static InnerPhotoMarker from(Photo photo) {
        return new InnerPhotoMarker(
            photo.getId(),
            photo.getCapturedDt(),
            photo.getFilePath(),
            photo.getLocation().getY(),
            photo.getLocation().getX()
        );
      }
    }
  }

  public record InnerPoiMarkers(
      Integer count,
      List<InnerPoiMarker> markers
  ) {

    public static InnerPoiMarkers from(List<Photo> photos) {
      return new InnerPoiMarkers(
          photos.size(),
          photos.stream()
              .map(InnerPoiMarker::from)
              .toList()
      );
    }

    public record InnerPoiMarker(
        Long id,
        LocalDateTime capturedDt,
        double lat,
        double lng
    ) {

      public static InnerPoiMarker from(Photo photo) {
        return new InnerPoiMarker(
            photo.getId(),
            photo.getCapturedDt(),
            photo.getLocation().getY(),
            photo.getLocation().getX()
        );
      }
    }
  }
}
