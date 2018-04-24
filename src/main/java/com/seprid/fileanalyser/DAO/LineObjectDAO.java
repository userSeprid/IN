package com.seprid.fileanalyser.DAO;

import com.seprid.fileanalyser.entity.LineObject;

/**
 * Data access object that handle connection to DB and perform a CRUD operations.
 */
public interface LineObjectDAO {
    int createLineObject(LineObject object);
    LineObject getLineObject(Integer ID);
    void updateLineObject(LineObject updatedObject);
    void deleteLineObject(int ID);
}
