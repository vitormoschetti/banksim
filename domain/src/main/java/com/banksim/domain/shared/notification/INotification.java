package com.banksim.domain.shared.notification;

import java.util.Set;

public interface INotification {

    void addMessage(INotificationError error);

    Set<INotificationError> messages();

}
