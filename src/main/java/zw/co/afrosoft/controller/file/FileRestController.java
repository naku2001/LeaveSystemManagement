package zw.co.afrosoft.controller.file;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.afrosoft.controller.FileService;
import zw.co.afrosoft.model.file.File;
import zw.co.afrosoft.model.SystemConstants;
import zw.co.afrosoft.repository.file.FileRepository;

import java.io.BufferedOutputStream;

import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Objects;
@CrossOrigin
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;
    private final FileRepository fileRepository;


    public String generateRandomString(int length) {

        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                + "lmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();

    }


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponse(responseCode = "200", description = "File uploaded successfully")

    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        String uploadRootPath = new java.io.File(SystemConstants.pictureFolderUrl).getAbsolutePath();
        java.io.File uploadRootDir = new java.io.File(uploadRootPath);
        File savedFile = new File();
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        if (Objects.nonNull(file)) {
            try {

                String nm = file.getOriginalFilename()
                        .replace(" ", "")
                        .replace("-", "");
                String filename = generateRandomString(10).concat(nm);
                String tempUrl = SystemConstants.pictureFolderUrl.concat(filename);
                java.io.File serverFile = new java.io.File(uploadRootDir.getPath() + java.io.File.separator + filename);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
                savedFile = fileRepository.save(File.builder()
                        .location(tempUrl)
                        .fileName(filename)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

        return ResponseEntity.ok().body(savedFile);

    }

    @GetMapping("getById{id}")
    private ResponseEntity findFile(@PathVariable Long id){
        return fileService.findFile(id);
    }

    @DeleteMapping("delete{id}")
    private ResponseEntity deleteFile(@PathVariable Long id){
        return fileService.deleteFile(id);
    }

    @GetMapping("/get")
    private ResponseEntity getAll(){
        return fileService.findFiles();
    }

}
