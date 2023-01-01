package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: njy
 * @Date: 2022/12/31 - 12 - 31 - 13:14
 * @Description: com.heima.model.admin.dtos
 * @version: 1.0
 */
@Data
public class ChannelDTO extends PageRequestDTO {
    /**
     * 频道名称
     */
    @ApiModelProperty("频道名称")
    private String name;
    /**
     * 频道状态
     */
    @ApiModelProperty("频道状态 开启 1 关闭 ")
    private Integer status;
}
