package nl.filr.server.service;

import nl.filr.server.model.Item;
import nl.filr.server.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
public class FilesService {

    @Autowired
    FilesRepository filesRepository;

    public Collection<Item> filesList() throws IOException {

//        return filesRepository.listFiles(getUsername());
        return null;
    }

    public void newFile(MultipartFile file) throws IOException {
        filesRepository.storeFile("user", file);
    }

//    private String getUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
//    }
}
