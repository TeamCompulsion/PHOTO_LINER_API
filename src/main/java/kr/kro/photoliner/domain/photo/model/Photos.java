package kr.kro.photoliner.domain.photo.model;

import java.time.LocalDate;
import java.util.List;

public record Photos(
    List<Photo> photos
) {

  public int count() {
    return photos.size();
  }

  public List<Photo> filterInDate(LocalDate from, LocalDate to) {
    return photos.stream()
        .filter(photo -> photo.isBetween(from, to))
        .toList();
  }

  public List<Photo> filterOutOfDate(LocalDate from, LocalDate to) {
    return photos.stream()
        .filter(photo -> !photo.isBetween(from, to))
        .toList();
  }
}
