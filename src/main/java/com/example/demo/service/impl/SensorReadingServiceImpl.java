package com.example.demo.service.impl;

import com.example.demo.entity.Sensor;
import com.example.demo.entity.SensorReading;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SensorReadingRepository;
import com.example.demo.repository.SensorRepository;
import com.example.demo.service.SensorReadingService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorReadingServiceImpl implements SensorReadingService {
    private final SensorReadingRepository sensorReadingRepository;
    private final SensorRepository sensorRepository;

    public SensorReadingServiceImpl(SensorReadingRepository sensorReadingRepository, SensorRepository sensorRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public SensorReading submitReading(Long sensorId, SensorReading reading) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found"));
        
        if (reading.getReadingValue() == null || reading.getReadingValue() == 0) {
            throw new IllegalArgumentException("readingvalue");
        }
        
        if (reading.getReadingTime() != null && reading.getReadingTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reading time cannot be in the future");
        }
        
        reading.setSensor(sensor);
        reading.setStatus("PENDING");
        return sensorReadingRepository.save(reading);
    }

    @Override
    public SensorReading getReading(Long id) {
        return sensorReadingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reading not found"));
    }

    @Override
    public List<SensorReading> getReadingsBySensor(Long sensorId) {
        return sensorReadingRepository.findBySensor_Id(sensorId);
    }
}