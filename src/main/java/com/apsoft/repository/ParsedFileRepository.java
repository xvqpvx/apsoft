package com.apsoft.repository;

import com.apsoft.model.ParsedFile;

public interface ParsedFileRepository {
    ParsedFile getById(int id);
    ParsedFile save(ParsedFile file);    
}