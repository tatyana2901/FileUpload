package com.example.FileUploadDemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class FileContoller {

    @GetMapping("/")
    String index(){
        System.out.println("тук-тук");
        return "index";
    }

    @PostMapping("/upload")
    public String doUpload(MultipartFile xfile, Model model){
        if(xfile==null || xfile.isEmpty()){
            System.out.println("нет файла");
            model.addAttribute("message", "нет файла");
        }
        else{
            System.out.println("получен файл "+xfile.getOriginalFilename());
            System.out.println("xfile.getSize() = " + xfile.getSize());
            model.addAttribute("message", "получен файл "+xfile.getOriginalFilename());
            model.addAttribute("size", xfile.getSize());
            Path path = Path.of("directoryForUserFiles"+"/"+xfile.getOriginalFilename());
            try {
                //Сохранение файла на сервере
                xfile.transferTo(path);
            } catch (IOException e) {
                System.out.println("не удалось сохранить файл: "+e.getMessage());
                model.addAttribute("errorMessage", "не удалось сохранить файл: "+e.getMessage());
            }
            //АНАЛИЗ файла
            //Files.delete(path); //удаление файла, если он больше не нужен
        }
        return "index";
    }
}
