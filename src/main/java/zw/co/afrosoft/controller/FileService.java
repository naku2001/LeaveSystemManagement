package zw.co.afrosoft.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.file.File;
import zw.co.afrosoft.repository.file.FileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }



    public ResponseEntity findFile(Long id) {
        Optional<File> file = fileRepository.findById(id);
        return ResponseEntity.ok().body(file);
    }

    public ResponseEntity deleteFile(Long id) {
        Optional<File> file = fileRepository.findById(id);
        if(!file.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file not found");
        return ResponseEntity.ok().body(file);
    }

    public ResponseEntity<List<File>> findFiles() {
        List<File> fileEntityList = fileRepository.findAll();
        return ResponseEntity.ok().body(fileEntityList);
    }
}


