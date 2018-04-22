package com.seprid.fileanalyser.repository;

import com.seprid.fileanalyser.entity.LineObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineObjectRepository extends JpaRepository<LineObject, Integer> {

    LineObject[] findByContainerName(String containerName);
}
