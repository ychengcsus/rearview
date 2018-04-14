package com.predix.rearview.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GeoLocationInformation.
 */
@Entity
@Table(name = "geo_location_information")
public class GeoLocationInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "location_uuid")
    private Long locationUuid;

    @Column(name = "location_type")
    private String locationType;

    @Column(name = "parent_location_uuid")
    private Long parentLocationUuid;

    @Column(name = "coordinates_type")
    private String coordinatesType;

    @Column(name = "coordinates")
    private Long coordinates;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zipcode")
    private Long zipcode;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "address")
    private String address;

    @Column(name = "analytic_category")
    private String analyticCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationUuid() {
        return locationUuid;
    }

    public GeoLocationInformation locationUuid(Long locationUuid) {
        this.locationUuid = locationUuid;
        return this;
    }

    public void setLocationUuid(Long locationUuid) {
        this.locationUuid = locationUuid;
    }

    public String getLocationType() {
        return locationType;
    }

    public GeoLocationInformation locationType(String locationType) {
        this.locationType = locationType;
        return this;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getParentLocationUuid() {
        return parentLocationUuid;
    }

    public GeoLocationInformation parentLocationUuid(Long parentLocationUuid) {
        this.parentLocationUuid = parentLocationUuid;
        return this;
    }

    public void setParentLocationUuid(Long parentLocationUuid) {
        this.parentLocationUuid = parentLocationUuid;
    }

    public String getCoordinatesType() {
        return coordinatesType;
    }

    public GeoLocationInformation coordinatesType(String coordinatesType) {
        this.coordinatesType = coordinatesType;
        return this;
    }

    public void setCoordinatesType(String coordinatesType) {
        this.coordinatesType = coordinatesType;
    }

    public Long getCoordinates() {
        return coordinates;
    }

    public GeoLocationInformation coordinates(Long coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public void setCoordinates(Long coordinates) {
        this.coordinates = coordinates;
    }

    public String getCity() {
        return city;
    }

    public GeoLocationInformation city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public GeoLocationInformation state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public GeoLocationInformation country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public GeoLocationInformation zipcode(Long zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public String getTimezone() {
        return timezone;
    }

    public GeoLocationInformation timezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAddress() {
        return address;
    }

    public GeoLocationInformation address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAnalyticCategory() {
        return analyticCategory;
    }

    public GeoLocationInformation analyticCategory(String analyticCategory) {
        this.analyticCategory = analyticCategory;
        return this;
    }

    public void setAnalyticCategory(String analyticCategory) {
        this.analyticCategory = analyticCategory;
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
        GeoLocationInformation geoLocationInformation = (GeoLocationInformation) o;
        if (geoLocationInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), geoLocationInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeoLocationInformation{" +
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
