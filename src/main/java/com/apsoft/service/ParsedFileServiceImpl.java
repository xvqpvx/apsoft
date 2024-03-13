package com.apsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apsoft.model.ParsedFile;
import com.apsoft.parser.FileParser;
import com.apsoft.repository.ParsedFileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParsedFileServiceImpl implements ParsedFileService {

    private final ParsedFileRepository fileRepository;
    private final FileParser fileParser;

    @Autowired
    public ParsedFileServiceImpl(ParsedFileRepository fileRepository, FileParser fileParser) {
        this.fileRepository = fileRepository;
        this.fileParser = fileParser;
    }

    @Override
    public ParsedFile getById(int id) {
        ParsedFile file = fileRepository.getById(id);
        if (file == null) {
            log.info("File with id " + id + " not found");
            return null;
        }

        return file;
    }

    @Override
    public ParsedFile save(ParsedFile file) {

        ParsedFile parsedFile = fileParser.parseFile(file.getLines());

        parsedFile = fileRepository.save(parsedFile);

        return parsedFile;
    }
}
