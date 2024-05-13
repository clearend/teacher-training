package com.example.training.utils;

import com.example.training.core.entity.RemindMessage;
import com.example.training.core.entity.User;
import com.example.training.core.entity.vo.RemindScheduleVO;
import com.example.training.core.mapper.RemindMessageMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleUtils {

    @Resource
    private RemindMessageMapper remindMessageMapper;

    @Resource
    private MailUtils mailUtils;

    @Scheduled(cron = "0 15 0 * * ? ")
    @Transactional(rollbackFor = {Exception.class})
    public void checkRemindMessage() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());
        Date now;
        try {
            now = sdf.parse(s);
        } catch (Exception e) {
            throw new RuntimeException("时间格式转换错误");
        }

        List<RemindScheduleVO> remindMessageList = remindMessageMapper.selectJoinList(
                RemindScheduleVO.class,
                new MPJLambdaWrapper<RemindMessage>()
                        .leftJoin(User.class, User::getUserId, RemindMessage::getRemindUserId)
                        .select(RemindMessage::getId, RemindMessage::getRemindContent, RemindMessage::getRemindStatus, RemindMessage::getRemindUserId)
                        .select(User::getEmail, User::getRemindType)
                        .eq(RemindMessage::getRemindStatus, 0)
                        .eq(RemindMessage::getRemindTime, now)
        );

        if (remindMessageList != null) {
            for (RemindScheduleVO remindSchedule: remindMessageList) {
                if (remindSchedule.getRemindType() == 1) {
                    String mailDomain = remindSchedule.getEmail().split("@")[1];
                    if (!"example.com".equals(mailDomain)) {
                        mailUtils.sendTextMailMessage(remindSchedule.getEmail(), "学习提醒", remindSchedule.getRemindContent());
                    }
                }

                RemindMessage remindMessage = new RemindMessage();
                BeanUtils.copyProperties(remindSchedule, remindMessage);
                remindMessage.setRemindStatus(1);
                remindMessageMapper.updateById(remindMessage);
            }
        }
    }
}
