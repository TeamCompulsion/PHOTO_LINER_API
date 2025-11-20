package kr.kro.photoliner.domain.photo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.kro.photoliner.domain.album.model.view.AlbumPhotoView;

public record MapMarkersResponse(
        InnerPhotoMarkers innerPhotoMarkers,
        InnerPoiMarkers innerPoiMarkers
) {

    public static MapMarkersResponse of(List<AlbumPhotoView> photosIncludedAlbum,
                                        List<AlbumPhotoView> photosExcludedAlbum) {
        return new MapMarkersResponse(
                InnerPhotoMarkers.from(photosIncludedAlbum),
                InnerPoiMarkers.from(photosExcludedAlbum)
        );
    }

    public record InnerPhotoMarkers(
            Integer count,
            List<InnerPhotoMarker> photoMarkers
    ) {

        public static InnerPhotoMarkers from(List<AlbumPhotoView> photos) {
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
                String thumbnailPath,
                Double lat,
                Double lng
        ) {

            public static InnerPhotoMarker from(AlbumPhotoView photo) {
                return new InnerPhotoMarker(
                        photo.getId(),
                        photo.getCapturedDt(),
                        photo.getFilePath(),
                        photo.getThumbnailPath(),
                        photo.getLongitude(),
                        photo.getLatitude()
                );
            }
        }
    }

    public record InnerPoiMarkers(
            Integer count,
            List<InnerPoiMarker> markers
    ) {

        public static InnerPoiMarkers from(List<AlbumPhotoView> photos) {
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
                String filePath,
                String thumbnailPath,
                Double lat,
                Double lng
        ) {

            public static InnerPoiMarker from(AlbumPhotoView photo) {
                return new InnerPoiMarker(
                        photo.getId(),
                        photo.getCapturedDt(),
                        photo.getFilePath(),
                        photo.getThumbnailPath(),
                        photo.getLongitude(),
                        photo.getLatitude()
                );
            }
        }
    }
}
