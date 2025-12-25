package com.example.demo.service.impl;

import com.example.demo.entity.ComplianceThreshold;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ComplianceThresholdRepository;
import com.example.demo.service.ComplianceThresholdService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplianceThresholdServiceImpl implements ComplianceThresholdService {
    private final ComplianceThresholdRepository thresholdRepository;

    public ComplianceThresholdServiceImpl(ComplianceThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public ComplianceThreshold createThreshold(ComplianceThreshold threshold) {
        if (threshold.getMinValue() >= threshold.getMaxValue()) {
            throw new IllegalArgumentException("minvalue");
        }
        return thresholdRepository.save(threshold);
    }

    @Override
    public ComplianceThreshold getThreshold(Long id) {
        return thresholdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Threshold not found"));
    }

    @Override
    public ComplianceThreshold getThresholdBySensorType(String sensorType) {
        return thresholdRepository.findBySensorType(sensorType)
                .orElseThrow(() -> new ResourceNotFoundException("Threshold not found"));
    }

    @Override
    public List<ComplianceThreshold> getAllThresholds() {
        return thresholdRepository.findAll();
    }
}