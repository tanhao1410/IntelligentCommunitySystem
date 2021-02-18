package cn.practice.community.dto;

import lombok.Data;

/**
 * Created by tanhao on 2020/4/24.
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
