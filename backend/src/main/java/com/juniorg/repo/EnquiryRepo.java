package com.juniorg.repo;

import com.juniorg.pojos.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepo extends JpaRepository<Enquiry,Long> {
}
