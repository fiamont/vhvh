package se.sofiatherese.vhvh.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.section.SectionModel;
import se.sofiatherese.vhvh.section.SectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final SectionRepository sectionRepository;

    private final ArticleRepository articleRepository;

    @Override
    public ResponseEntity<ArticleModel> createArticle(ArticleModel articleModel, BindingResult result, Long sectionId) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        SectionModel sectionModel = sectionRepository.findBySectionId(sectionId);
        articleModel.setSectionModel(sectionModel);
        articleRepository.save(articleModel);
        return new ResponseEntity<>(articleModel, HttpStatus.CREATED);
    }

    public List<ArticleModel> articleModelList (SectionModel sectionModel, List<ArticleModel> allArticles) {
        List<ArticleModel> sectionArticles = new ArrayList<>();
        for (ArticleModel articleModel : allArticles) {
            if (articleModel.getSectionModel().equals(sectionModel)) {
                sectionArticles.add(articleModel);
            }
        }
        return sectionArticles;
    }

    @Override
    public ResponseEntity<List<ArticleModel>> viewAllArticles (Long sectionId) {
        try {
            SectionModel sectionModel = sectionRepository.findBySectionId(sectionId);
            List<ArticleModel> allArticles = articleRepository.findAll();
            List<ArticleModel> sectionArticles = articleModelList(sectionModel, allArticles);
            return new ResponseEntity<>(sectionArticles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ArticleModel>> viewAllArticlesOrderByName (Long sectionId) {
        try {
            SectionModel sectionModel = sectionRepository.findBySectionId(sectionId);
            List<ArticleModel> allArticles = articleRepository.orderByArticleName();
            List<ArticleModel> sectionArticles = articleModelList(sectionModel, allArticles);
            return new ResponseEntity<>(sectionArticles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ArticleModel>> viewAllArticlesOrderByBestBefore (Long sectionId) {
        try {
            SectionModel sectionModel = sectionRepository.findBySectionId(sectionId);
            List<ArticleModel> allArticles = articleRepository.orderByBestBefore();
            List<ArticleModel> sectionArticles = articleModelList(sectionModel, allArticles);
            return new ResponseEntity<>(sectionArticles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Optional<ArticleModel>> viewOneArticle(Long articleId) {
        try{
            return ResponseEntity.ok(this.articleRepository.findById(articleId));
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ArticleModel> removeArticle (Long articleId) {
        try {
            articleRepository.deleteById(articleId);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ArticleModel> updateArticle(Long articleId, ArticleModel articleModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            Optional<ArticleModel> usedArticle = articleRepository.findById(articleId);
            if(usedArticle.isPresent()){
                ArticleModel updatedArticle = usedArticle.get();
                updatedArticle.setArticleName(articleModel.getArticleName());
                updatedArticle.setTypeOfAmount(articleModel.getTypeOfAmount());
                updatedArticle.setArticleAmount(articleModel.getArticleAmount());
                updatedArticle.setBestBefore(articleModel.getBestBefore());
                articleRepository.save(updatedArticle);
                return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
