package com.jwelkam.jewelrymanagement.service;


import org.springframework.web.multipart.MultipartFile;

import com.jwelkam.jewelrymanagement.dto.request.WorkerRequest;
import com.jwelkam.jewelrymanagement.dto.response.WorkerResponse;

import java.io.IOException;
import java.util.List;

public interface WorkerService {
    
    WorkerResponse createWorker(WorkerRequest workerRequest);

    WorkerResponse getWorkerById(Long id);

    List<WorkerResponse> getAllWorkers();

    List<WorkerResponse> getWorkersByCity(Long cityId);

    WorkerResponse updateWorker(Long id, WorkerRequest workerRequest);

    WorkerResponse uploadWorkerPhoto(Long id, MultipartFile photo) throws IOException;

    void deleteWorker(Long id);
}