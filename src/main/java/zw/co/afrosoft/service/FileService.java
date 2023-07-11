package zw.co.afrosoft.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import zw.co.afrosoft.model.file.File;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface FileService {

    ResponseEntity createFile(FileRequest fileRequest);

    ResponseEntity updateFile(Long id, FileRequest fileRequest);

    ResponseEntity deleteFile(Long id);

    ResponseEntity findFile(Long id);

    Page<File> findFiles(Pageable pageable);

    ResponseEntity saveFile(MultipartFile file) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String storeFile(String location, MultipartFile file);

    String createCsvFile(String location);


}
