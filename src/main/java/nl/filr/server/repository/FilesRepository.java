package nl.filr.server.repository;

import nl.filr.server.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Repository
public class FilesRepository {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(FilesRepository.class);

    @Value("${filr.homedir}")
    String homeDir;

    public Collection<Item> listFiles(String username, String relativePath) throws IOException {
        List<Item> items = new ArrayList<>();

        Path userHome = getUserHome(username);
        Path path = userHome.resolve(relativePath);

        DirectoryStream<Path> paths = Files.newDirectoryStream(path);
        Iterator<Path> iterator = paths.iterator();

        while(iterator.hasNext()) {
            Path p = iterator.next();
            Item i = new Item();
            i.setName(p.getFileName().toString());
            i.setDate(Files.getLastModifiedTime(p).toInstant());
            i.setDir(Files.isDirectory(p));

            String filePath = p.toAbsolutePath().toString().substring(userHome.toString().length());

            i.setPath(filePath);
            items.add(i);
        }

        return items;
    }

    public void storeFile(String username, MultipartFile file) throws IOException {
        Path p = getUserHome(username);
        p = p.resolve(file.getOriginalFilename());
        file.transferTo(p.toFile());
        logger.info("stored file " + p.toString());
    }

    public Path getUserHome(String username) throws IOException {
        Path path = Paths.get(homeDir).resolve(username);

        checkIfDirectoryExists(path, true);

        return path;
    }

    private void checkIfDirectoryExists(Path p, boolean createOnFalse) throws IOException {
        boolean exists = Files.exists(p);
        if(!exists && createOnFalse) {
            Files.createDirectory(p);
        }
    }
}
