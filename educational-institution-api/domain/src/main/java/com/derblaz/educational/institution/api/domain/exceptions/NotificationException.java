package com.derblaz.educational.institution.api.domain.exceptions;


import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.stream.Collectors;

public class NotificationException extends DomainException{
    public NotificationException(final String aMessage, final Notification notification) {
        super(ErroMessage(aMessage, notification), notification.getErrors());
    }

    private static String ErroMessage(final String aMessage, final Notification notification){
        return !aMessage.isBlank() ? aMessage : notification.getErrors().stream()
                .map(Error::message).collect(Collectors.joining(", "));
    }
}
