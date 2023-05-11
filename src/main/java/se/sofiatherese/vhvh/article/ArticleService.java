package se.sofiatherese.vhvh.article;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public interface ArticleService {
    ResponseEntity<ArticleModel> createArticle (ArticleModel articleModel, BindingResult result, Long sectionId);
    ResponseEntity<List<ArticleModel>> viewAllArticles (Long sectionId);
    ResponseEntity<List<ArticleModel>> viewAllArticlesOrderByName (Long sectionId);
    ResponseEntity<List<ArticleModel>> viewAllArticlesOrderByBestBefore (Long sectionId);
    ResponseEntity<Optional<ArticleModel>> viewOneArticle(Long articleId);
    ResponseEntity<ArticleModel> removeArticle (Long articleId);
    ResponseEntity<ArticleModel> updateArticle(Long articleId, ArticleModel articleModel, BindingResult result);
}
