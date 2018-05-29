package edu.four04.sscapp.domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime end_time;

    @Column(name = "asset_uuid")
    private Long asset_uuid;

    @Column(name = "asset_description")
    private String asset_description;

    @Column(name = "event_type")
    private String event_type;

    @Column(name = "counter_direction")
    private Integer counter_direction;

    @Column(name = "counter_direction_speed")
    private Long counter_direction_speed;

    @Column(name = "counter_direction_vehicle_count")
    private Long counter_direction_vehicle_count;

    @Column(name = "speed")
    private Long speed;

    @Column(name = "vehicle_count")
    private Long vehicle_count;

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

    public ZonedDateTime getEnd_time() {
        return end_time;
    }

    public TrafficMeasurements end_time(ZonedDateTime end_time) {
        this.end_time = end_time;
        return this;
    }

    public void setEnd_time(ZonedDateTime end_time) {
        this.end_time = end_time;
    }

    public Long getAsset_uuid() {
        return asset_uuid;
    }

    public TrafficMeasurements asset_uuid(Long asset_uuid) {
        this.asset_uuid = asset_uuid;
        return this;
    }

    public void setAsset_uuid(Long asset_uuid) {
        this.asset_uuid = asset_uuid;
    }

    public String getAsset_description() {
        return asset_description;
    }

    public TrafficMeasurements asset_description(String asset_description) {
        this.asset_description = asset_description;
        return this;
    }

    public void setAsset_description(String asset_description) {
        this.asset_description = asset_description;
    }

    public String getEvent_type() {
        return event_type;
    }

    public TrafficMeasurements event_type(String event_type) {
        this.event_type = event_type;
        return this;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public Integer getCounter_direction() {
        return counter_direction;
    }

    public TrafficMeasurements counter_direction(Integer counter_direction) {
        this.counter_direction = counter_direction;
        return this;
    }

    public void setCounter_direction(Integer counter_direction) {
        this.counter_direction = counter_direction;
    }

    public Long getCounter_direction_speed() {
        return counter_direction_speed;
    }

    public TrafficMeasurements counter_direction_speed(Long counter_direction_speed) {
        this.counter_direction_speed = counter_direction_speed;
        return this;
    }

    public void setCounter_direction_speed(Long counter_direction_speed) {
        this.counter_direction_speed = counter_direction_speed;
    }

    public Long getCounter_direction_vehicle_count() {
        return counter_direction_vehicle_count;
    }

    public TrafficMeasurements counter_direction_vehicle_count(Long counter_direction_vehicle_count) {
        this.counter_direction_vehicle_count = counter_direction_vehicle_count;
        return this;
    }

    public void setCounter_direction_vehicle_count(Long counter_direction_vehicle_count) {
        this.counter_direction_vehicle_count = counter_direction_vehicle_count;
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

    public Long getVehicle_count() {
        return vehicle_count;
    }

    public TrafficMeasurements vehicle_count(Long vehicle_count) {
        this.vehicle_count = vehicle_count;
        return this;
    }

    public void setVehicle_count(Long vehicle_count) {
        this.vehicle_count = vehicle_count;
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
            ", end_time='" + getEnd_time() + "'" +
            ", asset_uuid=" + getAsset_uuid() +
            ", asset_description='" + getAsset_description() + "'" +
            ", event_type='" + getEvent_type() + "'" +
            ", counter_direction=" + getCounter_direction() +
            ", counter_direction_speed=" + getCounter_direction_speed() +
            ", counter_direction_vehicle_count=" + getCounter_direction_vehicle_count() +
            ", speed=" + getSpeed() +
            ", vehicle_count=" + getVehicle_count() +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
