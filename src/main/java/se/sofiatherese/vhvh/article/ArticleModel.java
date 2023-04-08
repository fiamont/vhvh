package se.sofiatherese.vhvh.article;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import se.sofiatherese.vhvh.user.UserModel;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "articles")
public class ArticleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleId;

    @Column(name="articleName")
    private String articleName;
    @Column(name="articleAmount")
    private int articleAmount;

    @ManyToOne
    @JoinColumn(name="sectionId")
    private SectionModel section;

    @ManyToOne
    @JoinColumn(name="placeId")
    private PlaceModel place;

    @ManyToMany(mappedBy = "sharedPlaces")
    private Set<UserModel> sharedUsers = new HashSet<>();

    public ArticleModel(String articleName, int articleAmount, SectionModel section, PlaceModel place, Set<UserModel> sharedUsers) {
        this.articleName = articleName;
        this.articleAmount = articleAmount;
        this.section = section;
        this.place = place;
        this.sharedUsers = sharedUsers;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getArticleAmount() {
        return articleAmount;
    }

    public void setArticleAmount(int articleAmount) {
        this.articleAmount = articleAmount;
    }

    public SectionModel getSection() {
        return section;
    }

    public void setSectionModel(SectionModel section) {
        this.section = section;
    }

    public PlaceModel getPlace() {
        return place;
    }

    public void setPlaceModel(PlaceModel place) {
        this.place = place;
    }

    public Set<UserModel> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(Set<UserModel> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }
}
