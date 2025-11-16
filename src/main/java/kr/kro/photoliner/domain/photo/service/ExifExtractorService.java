package kr.kro.photoliner.domain.photo.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import kr.kro.photoliner.domain.photo.dto.ExifData;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ExifExtractorService {

    private final GeometryFactory geometryFactory;

    public ExifData extractExifData(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
            LocalDateTime capturedDt = extractCapturedDateTime(metadata);
            Point location = extractGpsLocation(metadata);
            return new ExifData(capturedDt, location);
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime extractCapturedDateTime(Metadata metadata) {
        return Optional.ofNullable(metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class))
                .map(this::extractCapturedDate)
                .map(this::normalize)
                .orElse(null);
    }

    private Point extractGpsLocation(Metadata metadata) {
        return Optional.ofNullable(metadata)
                .map(m -> m.getFirstDirectoryOfType(GpsDirectory.class))
                .map(GpsDirectory::getGeoLocation)
                .filter(this::filterInvalidLocation)
                .map(this::toPoint)
                .orElse(null);
    }

    private LocalDateTime normalize(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private Date extractCapturedDate(ExifSubIFDDirectory dir) {
        return Optional.ofNullable(dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL))
                .orElseGet(() -> dir.getDate(ExifSubIFDDirectory.TAG_DATETIME));
    }

    private boolean filterInvalidLocation(GeoLocation loc) {
        double lat = loc.getLatitude();
        double lon = loc.getLongitude();
        return Double.isFinite(lat) && Double.isFinite(lon) && isValidCoordinate(lat, lon);
    }

    private Point toPoint(GeoLocation loc) {
        return geometryFactory.createPoint(new Coordinate(loc.getLongitude(), loc.getLatitude()));
    }

    private boolean isValidCoordinate(double latitude, double longitude) {
        return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180;
    }
}
