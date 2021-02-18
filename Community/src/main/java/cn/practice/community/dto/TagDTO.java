package cn.practice.community.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by tanhao on 2020/6/5.
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
