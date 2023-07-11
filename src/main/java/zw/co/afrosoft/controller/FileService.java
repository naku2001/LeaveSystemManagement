package zw.co.afrosoft.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zw.co.afrosoft.model.FileEntity;
import zw.co.afrosoft.repository.FileRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }



    public ResponseEntity findFile(Long id) {
        Optional<FileEntity> file = fileRepository.findById(id);
        return ResponseEntity.ok().body(file);
    }

    public ResponseEntity deleteFile(Long id) {
        Optional<FileEntity> file = fileRepository.findById(id);
        if(!file.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file not found");
        return ResponseEntity.ok().body(file);
    }

    public ResponseEntity<List<FileEntity>> findFiles() {
        List<FileEntity> fileEntityList = fileRepository.findAll();
        return ResponseEntity.ok().body(fileEntityList);
    }
}


