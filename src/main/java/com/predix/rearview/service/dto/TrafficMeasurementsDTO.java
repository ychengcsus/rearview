package com.predix.rearview.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TrafficMeasurements entity.
 */
public class TrafficMeasurementsDTO implements Serializable {

    private Long id;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private Long assetUuid;

    private String assetDescription;

    private String eventType;

    private Long counterDirection;

    private Long counterDirectionSpeed;

    private Long counterDirectionVehicleCount;

    private Long speed;

    private Long vehicleCount;

    private ZonedDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getAssetUuid() {
        return assetUuid;
    }

    public void setAssetUuid(Long assetUuid) {
        this.assetUuid = assetUuid;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getCounterDirection() {
        return counterDirection;
    }

    public void setCounterDirection(Long counterDirection) {
        this.counterDirection = counterDirection;
    }

    public Long getCounterDirectionSpeed() {
        return counterDirectionSpeed;
    }

    public void setCounterDirectionSpeed(Long counterDirectionSpeed) {
        this.counterDirectionSpeed = counterDirectionSpeed;
    }

    public Long getCounterDirectionVehicleCount() {
        return counterDirectionVehicleCount;
    }

    public void setCounterDirectionVehicleCount(Long counterDirectionVehicleCount) {
        this.counterDirectionVehicleCount = counterDirectionVehicleCount;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrafficMeasurementsDTO trafficMeasurementsDTO = (TrafficMeasurementsDTO) o;
        if(trafficMeasurementsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trafficMeasurementsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrafficMeasurementsDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", assetUuid=" + getAssetUuid() +
            ", assetDescription='" + getAssetDescription() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", counterDirection=" + getCounterDirection() +
            ", counterDirectionSpeed=" + getCounterDirectionSpeed() +
            ", counterDirectionVehicleCount=" + getCounterDirectionVehicleCount() +
            ", speed=" + getSpeed() +
            ", vehicleCount=" + getVehicleCount() +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
