package com.apsoft.parser;

import com.apsoft.model.ParsedFile;

public interface FileParser {
    ParsedFile parseFile(String[] inputLines);
}
