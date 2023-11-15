package com.derblaz.educational.institution.api.domain.exceptions;


import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

public class NotificationException extends DomainException{
    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}
