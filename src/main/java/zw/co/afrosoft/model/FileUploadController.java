//package zw.co.afrosoft.model;
//
//import org.springframework.http.ContentDisposition;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import zw.co.afrosoft.repository.FileRepository;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/file")
//public class FileUploadController {
//    private final FileRepository fileRepository;
//
//    public FileUploadController(FileRepository fileRepository) {
//        this.fileRepository = fileRepository;
//    }
//
//    @PostMapping
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            // Save the file to storage or perform further processing as needed
//            // For simplicity, let's assume we are saving the file to the local file system
//            String fileName = file.getOriginalFilename();
//            byte[] fileBytes = file.getBytes();
//            // ... save the file
//
//            // Create and save the FileEntity to the database
//            File file1 = new File();
//            file1.setName(fileName);
//            // ... set other file attributes
//            fileRepository.save(file1);
//
//            return ResponseEntity.ok("File uploaded successfully.");
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
//        Optional<File> fileOptional = fileRepository.findById(id);
//        if (fileOptional.isPresent()) {
//            File fileEntity = fileOptional.get();
//            // Retrieve the file from storage or perform further processing as needed
//            // For simplicity, let's assume we are retrieving the file from the local file system
//            // ... retrieve the file as byte array
//            byte[] fileBytes = new byte[0];
//            // ... retrieve the file
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDisposition(ContentDisposition.attachment().filename(fileEntity.getName()).build());
//
//            return ResponseEntity.ok().headers(headers).body(fileBytes);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
//        Optional<File> fileOptional = fileRepository.findById(id);
//        if (fileOptional.isPresent()) {
//            // Delete the file from storage or perform further processing as needed
//            // For simplicity, let's assume we are deleting the file from the local file system
//            // ... delete the file
//
//            fileRepository.deleteById(id);
//            return ResponseEntity.ok("File deleted successfully.");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
