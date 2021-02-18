package cn.practice.community.dto;

import lombok.Data;

/**
 * Created by tanhao on 2020/5/30.
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
