package com.bdt.queswer.service;

import com.bdt.queswer.dto.NotificationDto;
import com.bdt.queswer.dto.NotificationPageDto;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.Notification;
import com.bdt.queswer.model.User;
import com.bdt.queswer.repository.NotificationRepository;
import com.bdt.queswer.repository.NotificationTypeRepository;
import com.bdt.queswer.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notiRepository;

    @Autowired
    NotificationTypeRepository notiTypeRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    public NotificationPageDto getListByUser(int limit, int pageNumber, String sortCrit)
            throws CustomException {
        NotificationPageDto dto = new NotificationPageDto();
        dto.setNotifications(new ArrayList<>());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByAccountEmail(authentication.getName()).get();
        Sort sort;
        switch (sortCrit) {
            case "+date":
                sort = Sort.by("creationDate").ascending();
                break;
            case "-date":
                sort = Sort.by("creationDate").descending();
                break;
            default:
                throw new CustomException("Tham số sort không hợp lệ");
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, sort);
        Page<Notification> page = notiRepository.findAllByUserId(user.getId(), pageable);
        page.forEach(item -> {
            dto.getNotifications().add(mapper.map(item, NotificationDto.class));
        });
        dto.setTotalPage(page.getTotalPages());
        return dto;
    }

    public void addNotification(String message,NotiType type,Long postId,long userId) {
        Notification notification = new Notification();
        notification.setChecked(false);
        notification.setMessage(message);
        notification.setUser(userRepository.findById(userId).get());
        notification.setCreationDate(new Date());
        switch (type) {
            case VOTE:
                notification.setType(notiTypeRepository.findByName("VOTE").get());
                break;
            case ANSWER:
                notification.setType(notiTypeRepository.findByName("ANSWER").get());
                notification.setPostId(postId);
                break;
            case QUESTION:
                notification.setType(notiTypeRepository.findByName("QUESTION").get());
                notification.setPostId(postId);
                break;
        }
        notiRepository.save(notification);
    }

    public void allNotificationChecked() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByAccountEmail(authentication.getName()).get();
        notiRepository.checkAllNotification(user.getId());
    }
}
