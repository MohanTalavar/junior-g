package com.app.dto;

import com.app.pojos.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressRequestResponseDto {
	
	@NotBlank(message = "City cannot be blank")
	@Size(max = 20, message = "City must not exceed 20 characters")
	private String city;
	
	@NotBlank(message = "State cannot be blank")
	@Size(max = 20, message = "State must not exceed 20 characters")
	private String state;
	
	@NotBlank(message = "Country cannot be blank")
	@Size(max = 20, message = "Country must not exceed 20 characters")
	private String country;
	
	@NotBlank(message = "Zipcode cannot be blank")
	@Pattern(regexp = "\\d{5}|\\d{6}", message = "Zip code must be 5 or 6 digits")
	private String zipCode;
	
	public AddressRequestResponseDto(Address address) {
		this.city = address.getCity();
		this.state = address.getState();
		this.country = address.getCountry();
		this.zipCode = address.getZipCode();
	}

}
