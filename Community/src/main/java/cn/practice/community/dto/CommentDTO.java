package cn.practice.community.dto;

import cn.practice.community.model.User;
import lombok.Data;

/**
 * Created by tanhao on 2020/6/2.
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
