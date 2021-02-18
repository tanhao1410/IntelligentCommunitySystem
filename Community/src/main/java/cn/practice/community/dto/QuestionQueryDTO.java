package cn.practice.community.dto;

import lombok.Data;

/**
 * Created by tanhao on 2020/7/1.
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private String sort;
    private Long time;
    private String tag;
    private Integer page;
    private Integer size;
}
