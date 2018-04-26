package com.seprid.fileanalyser.service;

import com.seprid.fileanalyser.DAO.LineObjectDAO;
import com.seprid.fileanalyser.entity.LineObject;


public class LineObjectServiceImpl implements LineObjectService {


    LineObjectDAO dao;

    public LineObjectServiceImpl(LineObjectDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(LineObject object) {
        dao.createLineObject(object);
    }

    @Override
    public LineObject getLineObject(int ID) {
        return dao.getLineObject(ID);
    }

    @Override
    public void update(LineObject object) {
        if (getLineObject(object.getLineID()) != null) {
            dao.updateLineObject(object);
        }else System.out.println("Line with ID:\'" + object.getLineID() + "\' doesn't exist.");
    }

    @Override
    public void delete(int ID) {
        if (getLineObject(ID) != null) {
            dao.deleteLineObject(ID);
        }else System.out.println("Line with ID:\'" + ID + "\' doesn't exist.");
    }
}
