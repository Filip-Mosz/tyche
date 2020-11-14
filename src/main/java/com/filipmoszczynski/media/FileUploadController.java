package com.filipmoszczynski.media;

import com.filipmoszczynski.media.storage.FileSystemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

    private final FileSystemStorage storage;

    public FileUploadController(FileSystemStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/media")
    public String getMediaPage() {
        return "media/index.html";
    }

    @PostMapping("/media")
    public String saveFile(@RequestParam("file")MultipartFile file,
                           RedirectAttributes redirectAttributes) {
        storage.store(file);

        redirectAttributes.addFlashAttribute(
          "message",
          "You succesfully uploaded" + file.getOriginalFilename()
        );
        return "redirect:/media";
    }
}
