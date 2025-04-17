package com.jwelkam.jewelrymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jwelkam.jewelrymanagement.dto.request.WorkerRequest;
import com.jwelkam.jewelrymanagement.dto.response.WorkerResponse;
import com.jwelkam.jewelrymanagement.service.WorkerService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(@Autowired WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WorkerResponse> createWorker(
            @RequestPart("worker") WorkerRequest workerRequest,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
    
        WorkerResponse createdWorker = workerService.createWorker(workerRequest);
    
        if (photo != null && !photo.isEmpty()) {
            workerService.uploadWorkerPhoto(createdWorker.getId(), photo);
        }
    
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workerService.getWorkerById(createdWorker.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponse> getWorkerById(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getWorkerById(id));
    }

    @GetMapping("/get-all-work")
    public ResponseEntity<List<WorkerResponse>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<WorkerResponse>> getWorkersByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(workerService.getWorkersByCity(cityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerResponse> updateWorker(
            @PathVariable Long id, @RequestBody WorkerRequest workerRequest) {
        return ResponseEntity.ok(workerService.updateWorker(id, workerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }
}