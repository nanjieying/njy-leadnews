package com.heima.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.ChannelDTO;
import com.heima.model.admin.pojo.AdChannel;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @Author: njy
 * @Date: 2022/12/31 - 12 - 31 - 13:18
 * @Description: com.heima.admin.service
 * @version: 1.0
 */
public interface ChannelService extends IService<AdChannel> {

    /**
     * 根据名称分页查询频道列表
     * @param dto
     * @return
     */
    public ResponseResult findByNameAndPage(ChannelDTO dto);

    public ResponseResult insert(AdChannel channel);

    public ResponseResult update(AdChannel channel);

    public ResponseResult delete(Integer id);
}
