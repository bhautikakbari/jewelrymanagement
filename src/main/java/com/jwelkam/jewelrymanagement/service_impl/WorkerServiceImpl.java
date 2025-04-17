package com.jwelkam.jewelrymanagement.service_impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.lang.reflect.Type;
import org.modelmapper.TypeToken;
import com.jwelkam.jewelrymanagement.dto.request.WorkerRequest;
import com.jwelkam.jewelrymanagement.dto.response.WorkerResponse;
import com.jwelkam.jewelrymanagement.entity.City;
import com.jwelkam.jewelrymanagement.entity.Worker;
import com.jwelkam.jewelrymanagement.exception.BadRequestException;
import com.jwelkam.jewelrymanagement.exception.ResourceNotFoundException;
import com.jwelkam.jewelrymanagement.repository.CityRepository;
import com.jwelkam.jewelrymanagement.repository.WorkerRepository;
import com.jwelkam.jewelrymanagement.service.FileStorageService;
import com.jwelkam.jewelrymanagement.service.WorkerService;

import java.io.IOException;
import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final CityRepository cityRepository;
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;

    public WorkerServiceImpl(
            @Autowired WorkerRepository workerRepository,
            @Autowired CityRepository cityRepository,
            @Autowired FileStorageService fileStorageService,
            @Autowired ModelMapper modelMapper) {
        this.workerRepository = workerRepository;
        this.cityRepository = cityRepository;
        this.fileStorageService = fileStorageService;
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkerResponse createWorker(WorkerRequest workerRequest) {

        if (workerRequest.getEmail() != null &&
                workerRepository.findByEmail(workerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Worker with this email already exists");
        }

        if (workerRepository.findByMobileNumber(workerRequest.getMobileNumber()).isPresent()) {
            throw new BadRequestException("Worker with this mobile number already exists");
        }

        City city = cityRepository.findById(workerRequest.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", workerRequest.getCityId()));

        Worker worker = Worker.builder().name(workerRequest.getName()).email(workerRequest.getEmail())
                .mobileNumber(workerRequest.getMobileNumber()).address(workerRequest.getAddress()).city(city).build();

        Worker savedWorker = workerRepository.save(worker);

        return modelMapper.map(savedWorker, WorkerResponse.class);
    }

    @Override
    public WorkerResponse getWorkerById(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));

        return modelMapper.map(worker, WorkerResponse.class);
    }

    @Override
    public List<WorkerResponse> getAllWorkers() {
        List<Worker> workers = workerRepository.findAll();

        Type listType = new TypeToken<List<WorkerResponse>>() {
        }.getType();
        return modelMapper.map(workers, listType);
    }

    @Override
    public List<WorkerResponse> getWorkersByCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", cityId));

        List<Worker> workers = workerRepository.findByCity(city);

        Type listType = new TypeToken<List<WorkerResponse>>() {
        }.getType();
        return modelMapper.map(workers, listType);
    }

    @Override
    public WorkerResponse updateWorker(Long id, WorkerRequest workerRequest) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));

        if (workerRequest.getEmail() != null &&
                !worker.getEmail().equals(workerRequest.getEmail()) &&
                workerRepository.findByEmail(workerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Worker with this email already exists");
        }

        if (!worker.getMobileNumber().equals(workerRequest.getMobileNumber()) &&
                workerRepository.findByMobileNumber(workerRequest.getMobileNumber()).isPresent()) {
            throw new BadRequestException("Worker with this mobile number already exists");
        }

        City city = cityRepository.findById(workerRequest.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", workerRequest.getCityId()));

        Worker updatedWorker = Worker.builder()
                .id(worker.getId())
                .name(workerRequest.getName())
                .email(workerRequest.getEmail())
                .mobileNumber(workerRequest.getMobileNumber())
                .address(workerRequest.getAddress())
                .city(city)
                .build();

        workerRepository.save(updatedWorker);

        return modelMapper.map(updatedWorker, WorkerResponse.class);
    }

    @Override
    public WorkerResponse uploadWorkerPhoto(Long id, MultipartFile photo) throws IOException {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));

        if (worker.getPhotoPath() != null) {
            fileStorageService.deleteFile(worker.getPhotoPath());
        }

        String photoPath = fileStorageService.storeFile(photo, "workers");
        worker.setPhotoPath(photoPath);

        Worker updatedWorker = workerRepository.save(worker);

        return modelMapper.map(updatedWorker, WorkerResponse.class);
    }

    @Override
    public void deleteWorker(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));

        try {
            if (worker.getPhotoPath() != null) {
                fileStorageService.deleteFile(worker.getPhotoPath());
            }
        } catch (IOException e) {
            System.err.println("Error deleting worker photo: " + e.getMessage());
        }

        workerRepository.delete(worker);
    }
}