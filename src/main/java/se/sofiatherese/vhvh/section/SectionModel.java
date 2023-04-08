package se.sofiatherese.vhvh.section;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "section")
public class SectionModel {

    @SequenceGenerator(name = "sectionIdGenerator", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sectionId;
    @NotEmpty
    private String sectionName;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceModel placeModel;

    public SectionModel(String sectionName) {
        this.sectionName = sectionName;
    }
}
