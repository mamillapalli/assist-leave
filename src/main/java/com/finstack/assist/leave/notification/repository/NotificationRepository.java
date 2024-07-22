package com.finstack.assist.leave.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finstack.assist.leave.notification.entity.Notification;
import com.finstack.assist.leave.notification.entity.NotificationStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Optional<List<Notification>> findByNotificationStatusIn(List<NotificationStatusEnum> notificationStatusEnumList);
}
