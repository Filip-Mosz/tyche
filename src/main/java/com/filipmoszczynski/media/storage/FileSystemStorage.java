package com.filipmoszczynski.media.storage;

import com.filipmoszczynski.media.repository.MediaEntity;
import com.filipmoszczynski.media.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorage {

    @Value("${mediaRootLocation}")
    private String path;

    private final MediaRepository mediaRepository;

    public FileSystemStorage(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public void store(MultipartFile file) {
        try {
            Path destinationFile = Paths.get(path)
                    .resolve(Paths.get(file.getOriginalFilename()))
                    .normalize()
                    .toAbsolutePath();
            try (InputStream inputStream = file.getInputStream() ){
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            mediaRepository.saveAndFlush(
                    new MediaEntity(file.getOriginalFilename())
            );

        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

}
