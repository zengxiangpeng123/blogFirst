package com.example.back_end.contrller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_end.common.Result;
import com.example.back_end.exceptionHandler.ArticleException;
import com.example.back_end.exceptionHandler.UserOperationException;
import com.example.back_end.model.domain.Article;
import com.example.back_end.model.domain.request.ArticleQueryRequest;
import com.example.back_end.service.ArticleService;
import com.example.back_end.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @Resource
    private UserService userService;

    /**
     * 获取所有文章列表（不分页）
     * @return 所有文章列表
     */
    @GetMapping("/list")
    public Result<List<Article>> getArticleList() {
        List<Article> list = articleService.list();
        return Result.success(list);
    }

    /**
     * 分页查询文章列表（推荐使用）
     * @param request 查询请求参数
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<Page<Article>> getArticlePage(ArticleQueryRequest request) {
        // 参数校验
        if (request == null) {
            request = new ArticleQueryRequest();
        }
        Page<Article> articlePage = articleService.getArticlePage(request);
        return Result.success(articlePage);
    }

    /**
     * 根据用户ID获取文章列表
     * @param userId 用户ID
     * @return 文章列表
     */
    @GetMapping("/list/{userId}")
    public Result<List<Article>> getArticleList(@PathVariable String userId) {
        if(StringUtils.isBlank(userId)) {
            throw new UserOperationException(400, "用户ID不能为空");
        }
        List<Article> list = articleService.list(new QueryWrapper<Article>().eq("user_id", userId));
        return Result.success(list);
    }
    /**
     * 根据ID获取文章详情
     * @param id 文章ID
     * @return 文章详情
     */
    @GetMapping("/{id}")
    public Result<Article> getArticleById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new ArticleException(400, "文章ID不能为空");
        }
        Article article = articleService.getById(id);
        if (article == null) {
            throw new ArticleException(404, "文章不存在");
        }
        return Result.success(article);
    }

    /**
     * 添加文章
     * @param article 文章参数
     * @return 添加后的文章
     */
    @PostMapping("/add")
    public Result<Article> addArticle(@RequestBody Article article) {

        Article checkedArticle = checkArticle(article);
        try {
            articleService.save(checkedArticle);
        } catch (Exception e) {
            throw new ArticleException(500, "文章添加失败: " + e.getMessage());
        }
        return Result.success(checkedArticle);
    }

    /**
     * 更新文章
     * @param article 文章参数
     * @return 更新后的文章
     */
    @PutMapping("/update")
    public Result<Article> updateArticle(@RequestBody Article article) {
        if (article.getId() == null) {
            throw new ArticleException(400, "文章ID不能为空");
        }

        Article existArticle = articleService.getById(article.getId());
        if (existArticle == null) {
            throw new ArticleException(404, "文章不存在");
        }

        // 更新时间
        article.setUpdatedAt(new Date());

        try {
            articleService.updateById(article);
        } catch (Exception e) {
            throw new ArticleException(500, "文章更新失败: " + e.getMessage());
        }
        return Result.success(article);
    }

    /**
     * 删除文章
     * @param id 文章ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteArticle(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new ArticleException(400, "文章ID不能为空");
        }

        Article article = articleService.getById(id);
        if (article == null) {
            throw new ArticleException(404, "文章不存在");
        }

        boolean result = articleService.removeById(id);
        return Result.success(result);
    }

    /**
     * 检查文章参数
     * @param article 文章参数
     * @return 检查后的文章参数
     * @throws ArticleException 参数异常
     */
    private Article checkArticle(Article article) {
        if (article == null) {
            throw new ArticleException(400, "文章参数不能为空");
        }

        Long userId = article.getUserId();
        Long categoryId = article.getCategoryId();
        String title = article.getTitle();
        String content = article.getContent();
        Integer isPinned = article.getIsPinned();

        // 必填字段校验
        if (userId == null && isPinned != 0) {
            throw new ArticleException(400, "用户ID不能为空");
        }
        if (categoryId == null && isPinned != 0) {
            throw new ArticleException(400, "分类ID不能为空");
        }
        if (StringUtils.isBlank(title)) {
            throw new ArticleException(400, "文章标题不能为空");
        }
        if (StringUtils.isBlank(content)) {
            throw new ArticleException(400, "文章内容不能为空");
        }

        // 设置默认值
        if (article.getViewCount() == null) {
            article.setViewCount(0);
        }
        if (article.getIsPinned() == null) {
            article.setIsPinned(0);
        }
        if (article.getIsPublished() == null) {
            article.setIsPublished(1);
        }

        // 设置时间
        if (article.getCreatedAt() == null) {
            article.setCreatedAt(new Date());
        }
        article.setUpdatedAt(new Date());

        return article;
    }
}
