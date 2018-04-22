package com.seprid.fileanalyser.service;

import com.seprid.fileanalyser.entity.LineObject;
import org.springframework.stereotype.Component;

@Component
public interface LineObjectService {

    void create(LineObject object);
    LineObject getLineObject(int ID);
    void update(LineObject object);
    void delete(int ID);
}
