package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private Double readingValue;

    @Column(nullable = false)
    private LocalDateTime readingTime;

    @Column(nullable = false)
    private String status = "PENDING";

    @OneToMany(mappedBy = "sensorReading", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ComplianceLog> complianceLogs;

    public SensorReading() {}

    public SensorReading(Sensor sensor, Double readingValue, LocalDateTime readingTime, String status) {
        this.sensor = sensor;
        this.readingValue = readingValue;
        this.readingTime = readingTime;
        this.status = status != null ? status : "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }

    public Double getReadingValue() { return readingValue; }
    public void setReadingValue(Double readingValue) { this.readingValue = readingValue; }

    public LocalDateTime getReadingTime() { return readingTime; }
    public void setReadingTime(LocalDateTime readingTime) { this.readingTime = readingTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<ComplianceLog> getComplianceLogs() { return complianceLogs; }
    public void setComplianceLogs(List<ComplianceLog> complianceLogs) { this.complianceLogs = complianceLogs; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorReading that = (SensorReading) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}