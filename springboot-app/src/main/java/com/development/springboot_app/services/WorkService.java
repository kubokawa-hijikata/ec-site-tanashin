package com.development.springboot_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Work;
import com.development.springboot_app.repository.WorkRepository;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    
    public WorkService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    public List<Work> findByOrderId(Integer orderId) {
        return workRepository.findByOrderId(orderId);
    }

    public void addNew(Work work) {
        workRepository.save(work);
    }

    public Optional<Work> getLatestOne() {
        return workRepository.findOneOrderByIdDesc();
    }
}
