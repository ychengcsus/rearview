package com.predix.rearview.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Assets.
 */
@Entity
@Table(name = "assets")
public class Assets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "asset_uuid")
    private Long assetUuid;

    @Column(name = "description")
    private String description;

    @Column(name = "properties")
    private String properties;

    @Column(name = "status")
    private String status;

    @Column(name = "asset_type")
    private String assetType;

    @Column(name = "media_types")
    private String mediaTypes;

    @Column(name = "event_types")
    private String eventTypes;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "parent_asset_uuid")
    private Long parentAssetUuid;

    @Column(name = "asset_creation_date")
    private ZonedDateTime assetCreationDate;

    @Column(name = "asset_to_location_id")
    private Long assetToLocationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetUuid() {
        return assetUuid;
    }

    public Assets assetUuid(Long assetUuid) {
        this.assetUuid = assetUuid;
        return this;
    }

    public void setAssetUuid(Long assetUuid) {
        this.assetUuid = assetUuid;
    }

    public String getDescription() {
        return description;
    }

    public Assets description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProperties() {
        return properties;
    }

    public Assets properties(String properties) {
        this.properties = properties;
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getStatus() {
        return status;
    }

    public Assets status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetType() {
        return assetType;
    }

    public Assets assetType(String assetType) {
        this.assetType = assetType;
        return this;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getMediaTypes() {
        return mediaTypes;
    }

    public Assets mediaTypes(String mediaTypes) {
        this.mediaTypes = mediaTypes;
        return this;
    }

    public void setMediaTypes(String mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public String getEventTypes() {
        return eventTypes;
    }

    public Assets eventTypes(String eventTypes) {
        this.eventTypes = eventTypes;
        return this;
    }

    public void setEventTypes(String eventTypes) {
        this.eventTypes = eventTypes;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public Assets coordinates(String coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Long getParentAssetUuid() {
        return parentAssetUuid;
    }

    public Assets parentAssetUuid(Long parentAssetUuid) {
        this.parentAssetUuid = parentAssetUuid;
        return this;
    }

    public void setParentAssetUuid(Long parentAssetUuid) {
        this.parentAssetUuid = parentAssetUuid;
    }

    public ZonedDateTime getAssetCreationDate() {
        return assetCreationDate;
    }

    public Assets assetCreationDate(ZonedDateTime assetCreationDate) {
        this.assetCreationDate = assetCreationDate;
        return this;
    }

    public void setAssetCreationDate(ZonedDateTime assetCreationDate) {
        this.assetCreationDate = assetCreationDate;
    }

    public Long getAssetToLocationId() {
        return assetToLocationId;
    }

    public Assets assetToLocationId(Long assetToLocationId) {
        this.assetToLocationId = assetToLocationId;
        return this;
    }

    public void setAssetToLocationId(Long assetToLocationId) {
        this.assetToLocationId = assetToLocationId;
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
        Assets assets = (Assets) o;
        if (assets.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assets.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assets{" +
            "id=" + getId() +
            ", assetUuid=" + getAssetUuid() +
            ", description='" + getDescription() + "'" +
            ", properties='" + getProperties() + "'" +
            ", status='" + getStatus() + "'" +
            ", assetType='" + getAssetType() + "'" +
            ", mediaTypes='" + getMediaTypes() + "'" +
            ", eventTypes='" + getEventTypes() + "'" +
            ", coordinates='" + getCoordinates() + "'" +
            ", parentAssetUuid=" + getParentAssetUuid() +
            ", assetCreationDate='" + getAssetCreationDate() + "'" +
            ", assetToLocationId=" + getAssetToLocationId() +
            "}";
    }
}
