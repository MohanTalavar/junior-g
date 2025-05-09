package com.app.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.app.dto.AddressRequestResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "address_tbl")
public class Address extends BaseEntity {

	@Column(length = 20)
	private String city;

	@Column(length = 20)
	private String state;

	@Column(length = 20)
	private String country;

	@Column(length = 6, name = "zip_code")
	private String zipCode;

//	// one to one asso : uni dir manner
//	// Address 1--->1 Student
//	@OneToOne(orphanRemoval = true)
//	@JoinColumn(name = "student_id", nullable = false)
//	@MapsId // Using shared PK
//	@Cascade(CascadeType.ALL)
//	private Student student;

	public Address(String city, String state, String country, String zipCode) {
		super();
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
	}

	public Address(AddressRequestResponseDto dto) {
		this.city = dto.getCity();
		this.state = dto.getState();
		this.country = dto.getCountry();
		this.zipCode = dto.getZipCode();
	}

	@Override
	public String toString() {
		return "Address [city=" + city + ", state=" + state + ", country=" + country + ", zipCode=" + zipCode + "]";
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
