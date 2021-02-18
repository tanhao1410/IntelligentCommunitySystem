package cn.practice.community.dto;

import lombok.Data;

/**
 * Created by tanhao on 2020/8/2.
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
