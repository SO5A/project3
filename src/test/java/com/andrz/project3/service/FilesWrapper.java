package com.andrz.project3.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWrapper {

    public String probeContentType(Path path) throws IOException {
        return Files.probeContentType(path);
    }
}
