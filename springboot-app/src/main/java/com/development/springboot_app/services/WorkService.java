package com.development.springboot_app.services;

import java.util.List;

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

    public Work getOne(int id) {
        return workRepository.findById(id).orElseThrow(()->new RuntimeException("id=" + id + "の作品は存在しません。"));
    }

    public void addNew(Work work) {
        workRepository.save(work);
    }
}
