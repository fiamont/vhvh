package se.sofiatherese.vhvh.article;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.sofiatherese.vhvh.section.SectionModel;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class ArticleModel {
    @SequenceGenerator(name = "articleIdGenerator", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "articleIdGenerator")
    private Long articleId;
    @NotEmpty
    private String articleName;
    private Integer articleAmount;
    private String typeOfAmount;
    private LocalDate bestBefore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="section_id", nullable = false)
    private SectionModel sectionModel;

}
