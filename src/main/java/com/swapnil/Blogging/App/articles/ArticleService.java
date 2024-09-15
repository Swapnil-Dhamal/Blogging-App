package com.swapnil.Blogging.App.articles;

import com.swapnil.Blogging.App.articles.dtos.CreateArticleRequest;
import com.swapnil.Blogging.App.articles.dtos.UpdateArticleRequest;
import com.swapnil.Blogging.App.users.UserRepo;
import com.swapnil.Blogging.App.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {


    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, UserRepo userRepo) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
    }






    public Iterable<ArticleEntity> getAllArticles(){
       return  articleRepo.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article= articleRepo.findBySlug(slug);

        if(article==null){
            throw new ArticleNotFoundException(slug);
        }
        return (ArticleEntity) article;
    }

    public ArticleEntity createArticle(CreateArticleRequest req, Long authorId){
        var author=userRepo.findById(authorId).orElseThrow(()-> new UserService.UserNotFoundException(authorId));

        return articleRepo.save(ArticleEntity.builder()
                        .title(req.getTitle())
                        .slug(req.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                        .body(req.getBody())
                        .subTitle(req.getSubTitle())
                        .author(author)
                        .build());
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest req){

        var article=articleRepo.findById(articleId).orElseThrow(()->new ArticleNotFoundException(articleId));

        if(req.getTitle()!=null){
            article.setTitle(req.getTitle());
            article.setSlug(req.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }

        if(req.getBody()!=null){
            article.setBody(req.getBody());
        }

        if(req.getSubTitle()!=null){
            article.setSubTitle(req.getSubTitle());
        }

        return articleRepo.save(article);
    }


    class ArticleNotFoundException extends IllegalArgumentException{

        public ArticleNotFoundException(String slug) {
            super("Article with "+slug+" not found");
        }

        public ArticleNotFoundException(Long articleId) {
            super("Article with "+articleId+" not found");
        }
    }


}
