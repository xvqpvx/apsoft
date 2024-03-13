package com.apsoft.parser;

import org.springframework.stereotype.Component;
import com.apsoft.model.ParsedFile;
import java.util.*;

@Component
public class FileParserImpl implements FileParser {

    @Override
    public ParsedFile parseFile(String[] inputLines) {
        List<String> parsedLines = new ArrayList<>();
        int[] levelCounters = new int[100];
        int currentLevel = 0;

        for (String line : inputLines) {
            int level = getLevel(line);

            if (level > 0) {
                if (level > currentLevel) {
                    // уровень вложенности увеличился - сбрасываем счетчики для более глубоких уровней
                    for (int i = currentLevel + 1; i < level; i++) {
                        levelCounters[i] = 0;
                    }
                    currentLevel = level;
                } else if (level < currentLevel) {
                    // уровень вложенности уменьшился - сбрасываем счетчики для более глубоких уровней
                    for (int i = level + 1; i <= currentLevel; i++) {
                        levelCounters[i] = 0;
                    }
                    currentLevel = level;
                }

                StringBuilder prefixBuilder = new StringBuilder();

                for (int i = 1; i < level; i++) {
                    prefixBuilder.append(levelCounters[i]).append(".");
                }

                prefixBuilder.append(++levelCounters[level]).append(".");
                String prefix = prefixBuilder.toString();

                parsedLines.add(prefix + " " + line.substring(level).trim());
            } else {
                parsedLines.add(line);
            }
        }

        ParsedFile parsedFile = new ParsedFile();
        parsedFile.setLines(parsedLines.toArray(new String[0]));

        return parsedFile;
    }

    private int getLevel(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == '#') {
            count++;
        }
        return count;
    }
}