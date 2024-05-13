package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.RemindMessage;
import com.example.training.core.entity.User;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.request.AddRemindRequest;
import com.example.training.core.entity.request.RemindMessageListRequest;
import com.example.training.core.entity.vo.RemindMessageListItemVO;
import com.example.training.core.entity.vo.RemindMessageListVO;
import com.example.training.core.mapper.RemindMessageMapper;
import com.example.training.core.service.IRemindMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author coder
 * @since 2024-05-13
 */
@Service
public class RemindMessageServiceImpl extends ServiceImpl<RemindMessageMapper, RemindMessage> implements IRemindMessageService {

    @Resource
    private RemindMessageMapper remindMessageMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addRemindMessage(AddRemindRequest request) {
        for (String userId: request.getRemindUserIds()) {
            RemindMessage remindMessage = new RemindMessage();
            remindMessage.setRemindId("rm-" + IdUtil.fastSimpleUUID());
            remindMessage.setRemindContent(request.getRemindContent());
            remindMessage.setRemindUserId(userId);
            remindMessage.setRemindTime(new Date(request.getRemindTime()));
            remindMessageMapper.insert(remindMessage);
        }
    }

    @Override
    public void deleteRemindMessage(String id) {
        RemindMessage remindMessage = remindMessageMapper.selectOne(
                new LambdaQueryWrapper<RemindMessage>()
                        .select(RemindMessage::getId)
                        .eq(RemindMessage::getRemindId, id)
        );

        if (remindMessage == null) {
            throw new BizException("待撤销的提醒消息不存在");
        }

        remindMessageMapper.deleteById(remindMessage);
    }

    @Override
    public RemindMessageListVO getRemindMessageList(RemindMessageListRequest request, User user) {
        RemindMessageListVO remindMessageListVO = new RemindMessageListVO();

        Integer currentPage = request.getPageRequest().getCurrentPage();
        Integer pageSize = request.getPageRequest().getPageSize();
        Integer offset = (currentPage - 1) * pageSize;

        MPJLambdaWrapper<RemindMessage> wrapper = new MPJLambdaWrapper<>();
        wrapper.leftJoin(User.class, User::getUserId, RemindMessage::getRemindUserId);

        remindMessageListVO.setCount(remindMessageMapper.selectJoinCount(wrapper));

        wrapper.select(RemindMessage::getRemindId, RemindMessage::getRemindContent, RemindMessage::getRemindUserId, RemindMessage::getRemindStatus, RemindMessage::getRemindTime)
                    .selectAs(User::getUserName, RemindMessageListItemVO::getRemindUserName)
                    .orderByDesc(RemindMessage::getRemindTime)
                    .last("limit " + offset + ", " + pageSize);
        if (user.getRole() == RoleEnum.USER) {
            wrapper.eq(RemindMessage::getRemindUserId, user.getUserId());
            wrapper.eq(RemindMessage::getRemindStatus, 1);
        }
        remindMessageListVO.setRemindList(remindMessageMapper.selectJoinList(RemindMessageListItemVO.class, wrapper));

        return remindMessageListVO;
    }

}
