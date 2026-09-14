package com.phegondev.InventoryMgtSystem.services;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IAMMetricsService {

    private static final String AUTH_ATTEMPTS_TOTAL = "iam.auth.attempts.total";
    private static final String RBAC_ACCESS_DENIED_TOTAL = "iam.rbac.access.denied.total";

    private final MeterRegistry meterRegistry;

    public void recordLoginAttempt(boolean success, String reason) {
        String status = success ? "success" : "failure";
        String reasonTag = (reason == null || reason.isBlank()) ? "none" : reason;
        meterRegistry.counter(AUTH_ATTEMPTS_TOTAL, "status", status, "reason", reasonTag).increment();
    }

    public void recordAccessDenied(String uri, String role) {
        String uriTag = (uri == null || uri.isBlank()) ? "unknown" : uri;
        String roleTag = (role == null || role.isBlank()) ? "anonymous" : role;
        meterRegistry.counter(RBAC_ACCESS_DENIED_TOTAL, "uri", uriTag, "role", roleTag).increment();
    }
}
