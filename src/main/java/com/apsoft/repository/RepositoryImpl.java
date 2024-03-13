package com.apsoft.repository;


import org.springframework.stereotype.Repository;

import com.apsoft.model.ParsedFile;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RepositoryImpl implements ParsedFileRepository {
    private final Map<Integer, ParsedFile> data = new HashMap<>();
    private int nextId = 0;

    @Override
    public ParsedFile getById(int id) {
        return data.get(id);
    }

    @Override
    public ParsedFile save(ParsedFile file) {
        file.setId(nextId++);
        data.put(file.getId(), file);
        return file;
    }   
}
