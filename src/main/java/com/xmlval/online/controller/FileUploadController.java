package com.xmlval.online.controller;

import com.xmlval.online.service.IbkValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/")
public class FileUploadController {

    //private static String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
    private static String UPLOADED_FOLDER = System.getProperty("./");

    @GetMapping
    public String main() {
        return "index";
    }

    @PostMapping
    public String upload(@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2, RedirectAttributes redirectAttributes) {
        try {
            IbkValidator validator = new IbkValidator();

            // Get the file and save it somewhere
            byte[] bytes = file1.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file1.getOriginalFilename());

            Files.write(path, bytes);

            byte[] bytes2 = file2.getBytes();
            Path path2 = Paths.get(UPLOADED_FOLDER + file2.getOriginalFilename());
            Files.write(path2, bytes2);

            String respon = validator.validate(path.toString(), path2.toString());

            Files.delete(path);
            Files.delete(path2);

            redirectAttributes.addFlashAttribute("message",respon);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}