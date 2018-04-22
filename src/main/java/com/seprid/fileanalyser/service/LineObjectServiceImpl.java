package com.seprid.fileanalyser.service;

import com.seprid.fileanalyser.entity.LineObject;
import com.seprid.fileanalyser.repository.LineObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LineObjectServiceImpl implements LineObjectService {

    @Autowired
    LineObjectRepository repository;

    @Override
    public void create(LineObject object) {
        repository.saveAndFlush(object);
    }

    @Override
    @Transactional(readOnly = true)
    public LineObject getLineObject(int ID) {
        return repository.getOne(ID);
    }

    @Override
    public void update(LineObject object) {
        if (repository.exists(object.getLineID())) {
            repository.delete(object.getLineID());
            repository.saveAndFlush(object);
        }else System.out.println("Line with ID:\'" + object.getLineID() + "\' doesn't exist.");
    }

    @Override
    public void delete(int ID) {
        if (repository.exists(ID)) {
            repository.delete(ID);
            repository.flush();
        }else System.out.println("Line with ID:\'" + ID + "\' doesn't exist.");
    }
}
