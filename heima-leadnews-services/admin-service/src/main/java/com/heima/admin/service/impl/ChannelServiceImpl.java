package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.ChannelMapper;
import com.heima.admin.service.ChannelService;
import com.heima.model.admin.dtos.ChannelDTO;
import com.heima.model.admin.pojo.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author: njy
 * @Date: 2022/12/31 - 12 - 31 - 13:27
 * @Description: com.heima.admin.service.impl
 * @version: 1.0
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper,AdChannel> implements ChannelService {


    @Override
    public ResponseResult findByNameAndPage(ChannelDTO dto) {
        //1、校验参数
        if (dto ==null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"参数错误");
        }

        dto.checkParam();

        Page<AdChannel> pageReq = new Page<>(dto.getPage(),dto.getSize());

        //2、条件查询 name status ord排序
        LambdaQueryWrapper<AdChannel> wrapper = Wrappers.lambdaQuery();

        //name 如果不为空 则模糊查询
        if(StringUtils.isNotBlank(dto.getName())){
            wrapper.like(AdChannel::getName,dto.getName());
        }
        if(dto.getStatus()!=null){
            wrapper.eq(AdChannel::getStatus,dto.getStatus());
        }
        //排序
        wrapper.orderByAsc(AdChannel::getOrd);
        //3、执行查询
        IPage<AdChannel> pageRes = this.page(pageReq,wrapper);

        return new PageResponseResult(dto.getPage(),dto.getSize(),pageRes.getTotal(),pageRes.getRecords());
    }

    @Override
    public ResponseResult insert(AdChannel channel) {
        //1、 参数校验 （name 不为空 不能大于10个字符）
        String name = channel.getName();
        if (StringUtils.isBlank(name)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"频道名称不能为空");
        }
        if (name.length() > 10) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"频道名称长度不能大于10");
        }
        //查询名称是否重复
        int count = this.count(Wrappers.<AdChannel>lambdaQuery().eq(AdChannel::getName,channel.getName()));

        if(count  > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"频道名称不能重复");
        }
        //2、保存频道
        channel.setCreatedTime(new Date());
        this.save(channel);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult update(AdChannel channel) {
        //1 参数校验
        if (channel == null || channel.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2 执行修改
        AdChannel achannel = getById(channel.getId());
        if (achannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"频道信息不存在");
        }
        //3. 校验名称唯一性
        if(StringUtils.isNotBlank(channel.getName()) && !channel.getName().equals(achannel.getName())){
            int count = this.count(Wrappers.<AdChannel>lambdaQuery()
                    .eq(AdChannel::getName, channel.getName()));
            if(count > 0){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"该频道已存在");
            }
        }
        updateById(channel);
        //4 返回结果
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Integer id) {
        //1.检查参数
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.判断当前频道是否存在 和 是否有效
        AdChannel adChannel = getById(id);
        if(adChannel==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        // 启用状态下不能删除
        if (adChannel.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        //3.删除频道
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
