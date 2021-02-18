package cn.practice.community.dto;

import cn.practice.community.model.User;
import lombok.Data;

/**
 * Created by tanhao on 2020/5/7.
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
