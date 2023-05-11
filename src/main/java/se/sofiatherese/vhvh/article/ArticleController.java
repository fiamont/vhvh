package se.sofiatherese.vhvh.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class ArticleController {
    private final ArticleService articleService;

    @CrossOrigin
    @PostMapping("/createArticle/{sectionId}")
    public ResponseEntity<ArticleModel> createArticle (@Valid @RequestBody ArticleModel articleModel, BindingResult result, @PathVariable Long sectionId) {
        return articleService.createArticle(articleModel, result, sectionId);
    }

    @CrossOrigin
    @GetMapping("/viewAllArticles/{sectionId}")
    public ResponseEntity<List<ArticleModel>> viewAllArticles (@PathVariable Long sectionId) {
        return articleService.viewAllArticles(sectionId);
    }

    @CrossOrigin
    @GetMapping("/viewAllArticlesByName/{sectionId}")
    public ResponseEntity<List<ArticleModel>> viewAllArticlesByName (@PathVariable Long sectionId) {
        return articleService.viewAllArticlesOrderByName(sectionId);
    }

    @CrossOrigin
    @GetMapping("/viewAllArticlesBestBefore/{sectionId}")
    public ResponseEntity<List<ArticleModel>> viewAllArticlesByBestBefore (@PathVariable Long sectionId) {
        return articleService.viewAllArticlesOrderByBestBefore(sectionId);
    }

    @CrossOrigin
    @GetMapping("/viewOneArticle/{articleId}")
    public ResponseEntity<Optional<ArticleModel>> viewOneArticle (@PathVariable Long articleId) {
        return articleService.viewOneArticle(articleId);
    }

    @CrossOrigin
    @DeleteMapping("/deleteArticle/{articleId}")
    public ResponseEntity<ArticleModel> deleteArticle (@PathVariable Long articleId) {
        return articleService.removeArticle(articleId);
    }

    @CrossOrigin
    @PutMapping("/updateArticle/{articleId}")
    public ResponseEntity<ArticleModel> updateArticle (@Valid @PathVariable Long articleId, @RequestBody ArticleModel articleModel, BindingResult result) {
        return articleService.updateArticle(articleId, articleModel, result);
    }

}
