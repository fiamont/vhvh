package se.sofiatherese.vhvh.place;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceModelDTO {

    @NotEmpty
    private String placeName;
    private Long placeId;
    private Long userId;
}
