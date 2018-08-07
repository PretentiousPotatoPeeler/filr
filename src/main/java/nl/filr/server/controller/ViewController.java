package nl.filr.server.controller;

import nl.filr.server.model.User;
import nl.filr.server.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
//@RequestMapping("/files")
public class ViewController {

    private final FilesService filesService;

    @Autowired
    public ViewController(FilesService filesService) {
        this.filesService = filesService;
    }

    @RequestMapping(value = "/f/**", method = RequestMethod.GET)
    public String index(Map<String, Object> model, HttpServletRequest request) throws IOException {
        model.put("username", filesService.getUsername());
        String requestUrl = request.getRequestURL().toString();
        String path = requestUrl.substring(requestUrl.indexOf("/f/")).substring(3);
        if(filesService.isDirectory(path)){
            model.put("items", filesService.filesList(path));
            return "f";
        } else {
            return "redirect:/d/" + path;
        }


    }

    @RequestMapping(value = "/d/**", method = RequestMethod.GET)
    public void download(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String requestUrl = request.getRequestURL().toString().replace("%20", " ");
        String path = requestUrl.substring(requestUrl.indexOf("/d/")).substring(3);
        InputStream inputStream = filesService.getInputStream(path);
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }


    @RequestMapping("/register")
    public String register(Map<String, Object> model) throws IOException {
        model.put("user", new User());
        return "register";
    }

//    @RequestMapping("/{path}/upload")
//    public String upload(@PathVariable("path") String path,
//                         @RequestParam("file") MultipartFile file, Map<String, Object> model) throws IOException {
//        if(file == null) {
//            model.put("error", "Select a file");
//        }
//        filesService.newFile(file);
//        return "f";
//    }
}
