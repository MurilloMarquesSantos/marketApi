package market.api.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("java:S2160")
public class PageableResponse<T> extends PageImpl<T> {
    private boolean first;
    private boolean last;
    private int totalPages;
    private int numberOfElements;
    private int size;
    private int number;
    private long totalElements;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageableResponse(
            @JsonProperty("content") List<T> content,
            @JsonProperty("page") PageMetadata pageMetadata) {
        super(content, PageRequest.of(pageMetadata.getNumber(), pageMetadata.getSize()), pageMetadata.getTotalElements());

        this.size = pageMetadata.getSize();
        this.number = pageMetadata.getNumber();
        this.totalElements = pageMetadata.getTotalElements();
        this.totalPages = pageMetadata.getTotalPages();
    }

    @Getter
    @Setter
    public static class PageMetadata {
        private int size;
        private int number;
        private int totalPages;
        private long totalElements;
    }
}
