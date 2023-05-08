package se.sofiatherese.vhvh.place;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.sofiatherese.vhvh.user.UserModel;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "place")
public class PlaceModel {
    @SequenceGenerator(name = "placeIdGenerator", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placeId;
    @NotEmpty
    private String placeName;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userModel;

    public PlaceModel(String placeName) {
        this.placeName = placeName;
    }
}
