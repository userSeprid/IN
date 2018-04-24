package com.seprid.fileanalyser.service;

import com.seprid.fileanalyser.entity.LineObject;


public interface LineObjectService {

    int create(LineObject object);
    LineObject getLineObject(int ID);
    void update(LineObject object);
    void delete(int ID);
}
