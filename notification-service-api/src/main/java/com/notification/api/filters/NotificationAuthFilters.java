package com.notification.api.filters;

import com.notification.api.constants.ApplicationConstant;
import com.notification.api.models.context.NotificationContext;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.utils.CommonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.notification.api.constants.ApplicationConstant.X_REQUEST_ID;

@Component
public class NotificationAuthFilters extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        if (isValidApi(request.getRequestURI())) {
            String xTenantId = request.getHeader(ApplicationConstant.X_TENANT_ID);

            if (CommonUtils.isEmpty(xTenantId)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("UnAuthorize! API key required.");
                return;
            }
            UUID generateUUID = CommonUtils.generateUUID();
            MDC.put(X_REQUEST_ID, String.valueOf(generateUUID));
            response.setHeader(X_REQUEST_ID, String.valueOf(generateUUID));
            NotificationContextHolder.setContext(new NotificationContext(xTenantId, false));
        }
        filterChain.doFilter(request, response);

        if (isValidApi(request.getRequestURI())) {
            NotificationContextHolder.clear();
            MDC.clear();
        }

    }

    private boolean isValidApi(final String requestURI) {
        return requestURI.startsWith("/api");
    }
}
