package com.app.repo;

import com.app.pojos.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepo extends JpaRepository<Enquiry,Long> {
}
