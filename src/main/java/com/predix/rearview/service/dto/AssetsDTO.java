package com.predix.rearview.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Assets entity.
 */
public class AssetsDTO implements Serializable {

    private Long id;

    private Long assetUuid;

    private String description;

    private String properties;

    private String status;

    private String assetType;

    private String mediaType;

    private String eventTypes;

    private String coordinates;

    private Long parentAssetUuid;

    private ZonedDateTime assetCreationDate;

    private Long assetToLocationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetUuid() {
        return assetUuid;
    }

    public void setAssetUuid(Long assetUuid) {
        this.assetUuid = assetUuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(String eventTypes) {
        this.eventTypes = eventTypes;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Long getParentAssetUuid() {
        return parentAssetUuid;
    }

    public void setParentAssetUuid(Long parentAssetUuid) {
        this.parentAssetUuid = parentAssetUuid;
    }

    public ZonedDateTime getAssetCreationDate() {
        return assetCreationDate;
    }

    public void setAssetCreationDate(ZonedDateTime assetCreationDate) {
        this.assetCreationDate = assetCreationDate;
    }

    public Long getAssetToLocationId() {
        return assetToLocationId;
    }

    public void setAssetToLocationId(Long assetToLocationId) {
        this.assetToLocationId = assetToLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetsDTO assetsDTO = (AssetsDTO) o;
        if(assetsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssetsDTO{" +
            "id=" + getId() +
            ", assetUuid=" + getAssetUuid() +
            ", description='" + getDescription() + "'" +
            ", properties='" + getProperties() + "'" +
            ", status='" + getStatus() + "'" +
            ", assetType='" + getAssetType() + "'" +
            ", mediaType='" + getMediaType() + "'" +
            ", eventTypes='" + getEventTypes() + "'" +
            ", coordinates='" + getCoordinates() + "'" +
            ", parentAssetUuid=" + getParentAssetUuid() +
            ", assetCreationDate='" + getAssetCreationDate() + "'" +
            ", assetToLocationId=" + getAssetToLocationId() +
            "}";
    }
}
