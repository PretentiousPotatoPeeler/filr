package nl.filr.server.service;

import nl.filr.server.model.Item;
import nl.filr.server.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

@Service
public class FilesService {

    private final FilesRepository filesRepository;

    @Autowired
    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public Collection<Item> filesList(String path) throws IOException {
        return filesRepository.listFiles(getUsername(), path);
    }

    public void newFile(MultipartFile file) throws IOException {
        filesRepository.storeFile(getUsername(), file);
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public boolean isDirectory(String path) throws IOException {
        return Files.isDirectory(filesRepository.getUserHome(getUsername()).resolve(path));
    }

    public InputStream getInputStream(String path) throws IOException {
        Path userHome = filesRepository.getUserHome(getUsername());
        File f = userHome.resolve(path).toFile();
        return new FileInputStream(f);
    }
}
