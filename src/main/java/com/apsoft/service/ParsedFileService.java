package com.apsoft.service;

import com.apsoft.model.ParsedFile;

public interface ParsedFileService {
    ParsedFile getById(int id);

    ParsedFile save(ParsedFile parsedFile);
}
