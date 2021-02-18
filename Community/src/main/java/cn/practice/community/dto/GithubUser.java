package cn.practice.community.dto;

import lombok.Data;

/**
 * Created by tanhao on 2020/4/24.
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
