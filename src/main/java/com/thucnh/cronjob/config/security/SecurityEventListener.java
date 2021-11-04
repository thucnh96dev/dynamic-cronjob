package com.thucnh.cronjob.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :04/11/2021 - 9:55 AM
 */
@Component
public class SecurityEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityEventListener.class);
    @EventListener(condition = "#event.auditEvent.type == 'AUTHENTICATION_FAILURE'")
    public void onAuthFailure(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();
        LOG.info("authentication failure, user: {}", auditEvent.getPrincipal());
    }

    @EventListener(condition = "#event.auditEvent.type == 'AUTHENTICATION_SUCCESS'")
    public void onAuthSuccess(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();
        LOG.info("authentication success, user: {}", auditEvent.getPrincipal());
    }

    @EventListener(condition = "#event.auditEvent.type == 'AUTHENTICATION_SWITCH'")
    public void onAuthSwitch(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();
        LOG.info("authentication switch, user: {}", auditEvent.getPrincipal());
    }
}
