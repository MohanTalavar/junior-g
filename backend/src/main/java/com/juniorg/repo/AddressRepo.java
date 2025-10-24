package com.juniorg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorg.pojos.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
