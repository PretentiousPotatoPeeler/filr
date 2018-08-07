package nl.filr.server.controller;

import nl.filr.server.model.Item;
import nl.filr.server.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
public class FilesController {

    private final FilesService filesService;

    @Autowired
    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping("/api")
    public ResponseEntity<Collection<Item>> listFiles(@RequestParam("path") String path) throws IOException {
        Collection<Item> items = filesService.filesList(path);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/api")
    public ResponseEntity postFile(@RequestParam("file") MultipartFile file) throws IOException {
        filesService.newFile(file);
        return ResponseEntity.ok().build();
    }
}
