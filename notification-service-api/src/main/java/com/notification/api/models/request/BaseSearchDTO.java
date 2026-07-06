package com.notification.api.models.request;

import com.notification.api.exception.ValidationException;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.utils.CommonUtils;
import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Data
public abstract class BaseSearchDTO<T> {
    private Integer page = 0;

    private Integer size = 10;

    private SortRequest sortRequest;

    private final Field getField(Class<?> clazz, String name) {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }

    public PageRequest buildPageRequest() {
        ofNullable(page).orElse(0);
        ofNullable(size).orElse(10);

        Sort sort = ofNullable(sortRequest).filter(CommonUtils::isNotEmpty)
                .filter(req -> CommonUtils.isNotEmpty(req.getSortKey()) && CommonUtils.isNotEmpty(req.getSortType()))
                .filter(req -> {
                    try {
                        this.getClass().getDeclaredField(req.getSortKey());
                        return true;
                    } catch (NoSuchFieldException e) {
                        throw new ValidationException("Invalid sort key", HttpStatus.BAD_REQUEST.value());
                    }
                }).map(req -> Sort.by(Sort.Direction.fromString(req.getSortType().getValue()), req.getSortKey()))
                .orElse(Sort.by(Sort.Direction.DESC, "createdAt"));

        return PageRequest.of(ofNullable(page).orElse(0),
                ofNullable(size).orElse(10), sort);
    }

    public Example<T> buildSearch() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<T> clazz = getEntity();
        T instance = clazz.getDeclaredConstructor().newInstance(); // when we write like this Template template = new
        // Template(); - this is same sentence

        injectTenantId(instance);

        for (Field dtoField : this.getClass().getDeclaredFields()) {
            dtoField.setAccessible(true);
            Object fieldValue = dtoField.get(this);
            if (CommonUtils.isNotEmpty(fieldValue)) {
                Field entityField = getField(clazz, dtoField.getName());
                entityField.setAccessible(true);
                entityField.set(instance, fieldValue);
            }
        }

        // this is the operation where you want to perform on db
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withMatcher(
                        "name",
                        ExampleMatcher.GenericPropertyMatchers
                                .contains()
                                .ignoreCase()
                );
        return Example.of(instance, matcher);
    }

    public abstract Class<T> getEntity();

    private void injectTenantId(final Object instance) {
        if (NotificationContextHolder.getContext().ignoreTenantIdInjection()) {
            System.out.println("Ignoring Tenant Id Injection");
            return;
        }
        
        try {
            Field tenantIdField = getField(instance.getClass(), "tenantId");
            tenantIdField.setAccessible(true);
            tenantIdField.set(instance, UUID.fromString(CommonUtils.getCurrentTenantId()));
        } catch (IllegalAccessException e) {
            throw new ValidationException("Error while searching tenantId in search builder",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

}
