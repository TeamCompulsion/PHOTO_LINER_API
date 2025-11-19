package kr.kro.photoliner.domain.album.dto.response;

import java.util.List;
import kr.kro.photoliner.domain.album.model.Album;
import org.springframework.data.domain.Page;

public record AlbumsResponse(
        List<InnerAlbum> albums,
        InnerPageInfo pageInfo
) {

    public static AlbumsResponse from(Page<Album> albumPage) {
        return new AlbumsResponse(
                albumPage.getContent().stream()
                        .map(InnerAlbum::from)
                        .toList(),
                InnerPageInfo.from(albumPage)
        );
    }

    public record InnerAlbum(
            Long id,
            String name
    ) {

        public static InnerAlbum from(Album album) {
            return new InnerAlbum(
                    album.getId(),
                    album.getName()
            );
        }
    }

    public record InnerPageInfo(
            long totalElements,
            int totalPages,
            int currentPage,
            int size,
            boolean hasNext,
            boolean hasPrevious
    ) {

        public static InnerPageInfo from(Page<Album> page) {
            return new InnerPageInfo(
                    page.getTotalElements(),
                    page.getTotalPages(),
                    page.getNumber(),
                    page.getSize(),
                    page.hasNext(),
                    page.hasPrevious()
            );
        }
    }
}
