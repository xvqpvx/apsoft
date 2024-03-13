package com.apsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.web.multipart.MultipartFile;

import com.apsoft.model.ParsedFile;
import com.apsoft.service.ParsedFileService;

@RestController
@RequestMapping("/api")
public class FileController {
    private final ParsedFileService fileService;
    private final int MAX_FILE_SIZE = 1048576;

    @Autowired
    public FileController(ParsedFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/getFileById/{id}")
    public ResponseEntity<?> getFileById(@PathVariable("id") int id) {
        ParsedFile parsedFile = fileService.getById(id);

        if (parsedFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with id " + id + " not found");
        }

        return ResponseEntity.ok(parsedFile.getLines());
    }

    @PostMapping("/parseFileWithResult")
    public ResponseEntity<?> parseFileWithResult(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No files were uploaded");
        }
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size is too big!");
        }

        ParsedFile parsedFile = new ParsedFile();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            parsedFile.setLines(content.toString().split("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        parsedFile = fileService.save(parsedFile);

        return ResponseEntity.ok(parsedFile.getLines());
    }

    @PostMapping("/parseFile")
    public ResponseEntity<?> parseFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No files were uploaded");
        }
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size is too big!");
        }

        ParsedFile parsedFile = new ParsedFile();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            parsedFile.setLines(content.toString().split("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        parsedFile = fileService.save(parsedFile);

        return ResponseEntity.ok(parsedFile.getId());
    }

}
