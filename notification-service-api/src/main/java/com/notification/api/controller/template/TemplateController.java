package com.notification.api.controller.template;

import com.notification.api.models.request.CreateUpdateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.services.interfaces.TemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/template")
public class TemplateController {
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    public ResponseEntity<TemplateResponse> createTemplate(@Valid @RequestBody CreateUpdateTemplateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(templateService.createTemplate(request));
    }

    @GetMapping
    public ResponseEntity<FilterTemplateResponse> findAllTemplates(TemplateFilterRequest filterRequest) {
        return ResponseEntity.ok().body(templateService.findAllTemplate(filterRequest));

    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(@PathVariable String id,
                                                           @Valid @RequestBody CreateUpdateTemplateRequest request) {
        return ResponseEntity.ok(templateService.updateTemplate(id, request));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable String id,
                                                 Supplier<? extends Throwable> exceptionHandler) {
        templateService.deleteTemplate(id, exceptionHandler);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Template deleted successfully.");
    }
}
