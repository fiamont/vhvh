package se.sofiatherese.vhvh.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import se.sofiatherese.vhvh.config.AppPasswordConfig;

import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final AppPasswordConfig appPasswordConfig;


    @Autowired
    public ArticleController(ArticleService articleService, ArticleRepository articleRepository, AppPasswordConfig appPasswordConfig) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
        this.appPasswordConfig = appPasswordConfig;
    }

    public List<ArticleModel> getArticlesBySection(SectionModel section) {
        return articleRepository.findBySectionModel(section);
    }

}
