package com.predix.rearview.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GeoLocationInformation entity.
 */
public class GeoLocationInformationDTO implements Serializable {

    private Long id;

    private Long locationUuid;

    private String locationType;

    private Long parentLocationUuid;

    private String coordinatesType;

    private Long coordinates;

    private String city;

    private String state;

    private String country;

    private Long zipcode;

    private String timezone;

    private String address;

    private String analyticCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationUuid() {
        return locationUuid;
    }

    public void setLocationUuid(Long locationUuid) {
        this.locationUuid = locationUuid;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getParentLocationUuid() {
        return parentLocationUuid;
    }

    public void setParentLocationUuid(Long parentLocationUuid) {
        this.parentLocationUuid = parentLocationUuid;
    }

    public String getCoordinatesType() {
        return coordinatesType;
    }

    public void setCoordinatesType(String coordinatesType) {
        this.coordinatesType = coordinatesType;
    }

    public Long getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Long coordinates) {
        this.coordinates = coordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAnalyticCategory() {
        return analyticCategory;
    }

    public void setAnalyticCategory(String analyticCategory) {
        this.analyticCategory = analyticCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeoLocationInformationDTO geoLocationInformationDTO = (GeoLocationInformationDTO) o;
        if(geoLocationInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), geoLocationInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeoLocationInformationDTO{" +
            "id=" + getId() +
            ", locationUuid=" + getLocationUuid() +
            ", locationType='" + getLocationType() + "'" +
            ", parentLocationUuid=" + getParentLocationUuid() +
            ", coordinatesType='" + getCoordinatesType() + "'" +
            ", coordinates=" + getCoordinates() +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", zipcode=" + getZipcode() +
            ", timezone='" + getTimezone() + "'" +
            ", address='" + getAddress() + "'" +
            ", analyticCategory='" + getAnalyticCategory() + "'" +
            "}";
    }
}
