package com.example.back_end.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 收藏表
 * @TableName favorite
 */
@TableName(value ="favorite")
@Data
public class Favorite {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 收藏状态：0-未收藏，1-已收藏
     */
    private int isExist;
}