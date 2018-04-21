package com.predix.rearview.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TrafficMeasurements.
 */
@Entity
@Table(name = "traffic_measurements")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrafficMeasurements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "asset_uuid")
    private Long assetUuid;

    @Column(name = "asset_description")
    private String assetDescription;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "counter_direction")
    private Long counterDirection;

    @Column(name = "counter_direction_speed")
    private Long counterDirectionSpeed;

    @Column(name = "counter_direction_vehicle_count")
    private Long counterDirectionVehicleCount;

    @Column(name = "speed")
    private Long speed;

    @Column(name = "vehicle_count")
    private Long vehicleCount;

    @Column(name = "jhi_timestamp")
    private ZonedDateTime timestamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public TrafficMeasurements startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public TrafficMeasurements endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getAssetUuid() {
        return assetUuid;
    }

    public TrafficMeasurements assetUuid(Long assetUuid) {
        this.assetUuid = assetUuid;
        return this;
    }

    public void setAssetUuid(Long assetUuid) {
        this.assetUuid = assetUuid;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public TrafficMeasurements assetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
        return this;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public TrafficMeasurements eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getCounterDirection() {
        return counterDirection;
    }

    public TrafficMeasurements counterDirection(Long counterDirection) {
        this.counterDirection = counterDirection;
        return this;
    }

    public void setCounterDirection(Long counterDirection) {
        this.counterDirection = counterDirection;
    }

    public Long getCounterDirectionSpeed() {
        return counterDirectionSpeed;
    }

    public TrafficMeasurements counterDirectionSpeed(Long counterDirectionSpeed) {
        this.counterDirectionSpeed = counterDirectionSpeed;
        return this;
    }

    public void setCounterDirectionSpeed(Long counterDirectionSpeed) {
        this.counterDirectionSpeed = counterDirectionSpeed;
    }

    public Long getCounterDirectionVehicleCount() {
        return counterDirectionVehicleCount;
    }

    public TrafficMeasurements counterDirectionVehicleCount(Long counterDirectionVehicleCount) {
        this.counterDirectionVehicleCount = counterDirectionVehicleCount;
        return this;
    }

    public void setCounterDirectionVehicleCount(Long counterDirectionVehicleCount) {
        this.counterDirectionVehicleCount = counterDirectionVehicleCount;
    }

    public Long getSpeed() {
        return speed;
    }

    public TrafficMeasurements speed(Long speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getVehicleCount() {
        return vehicleCount;
    }

    public TrafficMeasurements vehicleCount(Long vehicleCount) {
        this.vehicleCount = vehicleCount;
        return this;
    }

    public void setVehicleCount(Long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public TrafficMeasurements timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrafficMeasurements trafficMeasurements = (TrafficMeasurements) o;
        if (trafficMeasurements.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trafficMeasurements.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrafficMeasurements{" +
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
