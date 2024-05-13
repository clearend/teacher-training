package com.example.training.core.service;

import com.example.training.core.entity.RemindMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.AddRemindRequest;
import com.example.training.core.entity.request.RemindMessageListRequest;
import com.example.training.core.entity.vo.RemindMessageListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-13
 */
public interface IRemindMessageService extends IService<RemindMessage> {

    void addRemindMessage(AddRemindRequest request);

    void deleteRemindMessage(String id);

    RemindMessageListVO getRemindMessageList(RemindMessageListRequest request, User user);
}
