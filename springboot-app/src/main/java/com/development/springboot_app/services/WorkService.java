package com.development.springboot_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Work;
import com.development.springboot_app.repository.WorkRepository;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    
    public WorkService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    public List<Work> getAll() {
        return workRepository.findAll();
    }

    public void addNew(Work work) {
        workRepository.save(work);
    }

    public Work getOne(Integer workId) {
        return workRepository.findById(workId).orElseThrow(() -> new UsernameNotFoundException("作品が存在しません。"));
    }

    public Optional<Work> getLatestOne() {
        return workRepository.findOneOrderByIdDesc();
    }
}
