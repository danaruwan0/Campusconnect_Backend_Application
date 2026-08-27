package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.EmergencyAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmergencyRepository
        extends JpaRepository<EmergencyAlert,Long> {

    List<EmergencyAlert>
    findAllByOrderByCreatedAtDesc();

}