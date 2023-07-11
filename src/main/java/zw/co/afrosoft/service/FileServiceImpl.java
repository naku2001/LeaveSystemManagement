//package zw.co.afrosoft.service;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import zw.co.afrosoft.model.File;
//import zw.co.afrosoft.model.SystemConstants;
//import zw.co.afrosoft.repository.FileRepository;
//
//import java.io.BufferedOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.security.SecureRandom;
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//public class FileServiceImpl implements FileService{
//    private final FileRepository fileRepository;
//
//    public FileServiceImpl(FileRepository fileRepository) {
//        this.fileRepository = fileRepository;
//    }
//
//    @Override
//    public ResponseEntity createFile(FileRequest fileRequest) {
//        if (fileRepository.existsFileByName(fileRequest.getName())){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File already exist");
//        }
//        File file=File.builder()
//                .name(fileRequest.getName())
//                .extension(fileRequest.getExtension())
//                .location(fileRequest.getLocation())
//                .build();
//        return ResponseEntity.ok(fileRepository.save(file));
//    }
//
//    @Override
//    public ResponseEntity updateFile(Long id, FileRequest fileRequest) {
//        Optional<File> file=fileRepository.findById(id);
//        if (file.isPresent()){
//            File updatedFile=file.get();
//            updatedFile.setName(fileRequest.getName());
//            updatedFile.setExtension(fileRequest.getExtension());
//            updatedFile.setLocation(fileRequest.getLocation());
//            return ResponseEntity.ok(updatedFile);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File not found");
//    }
//
//    @Override
//    public ResponseEntity deleteFile(Long id) {
//        Optional<File> file=fileRepository.findById(id);
//        if (file.isPresent()){
//            fileRepository.delete(file.get());
//            return ResponseEntity.ok(file);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File not found");
//    }
//
//    @Override
//    public ResponseEntity findFile(Long id) {
//        Optional<File> file=fileRepository.findById(id);
//        if (file.isPresent()) {
//            return ResponseEntity.ok(file);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File not found");
//    }
//
//    @Override
//    public Page<File> findFiles(Pageable pageable) {
//        return fileRepository.findAll(pageable);
//    }
//
//    @Override
//    public ResponseEntity saveFile(MultipartFile file) {
//        String uploadRootPath = new java.io.File(SystemConstants.pictureFolderUrl).getAbsolutePath();
//        java.io.File uploadRootDir = new java.io.File(uploadRootPath);
//        File savedFile = new File();
//        if (!uploadRootDir.exists()) {
//            uploadRootDir.mkdirs();
//        }
//        if (Objects.nonNull(file)) {
//            try {
//
//                String nm = file.getOriginalFilename()
//                        .replace(" ", "")
//                        .replace("-", "");
//                String filename = generateRandomString(10).concat(nm);
//                String tempUrl = SystemConstants.pictureFolderUrl.concat(filename);
//                java.io.File serverFile = new java.io.File(uploadRootDir.getPath() + java.io.File.separator + filename);
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
//                stream.write(file.getBytes());
//                stream.close();
//                savedFile = fileRepository.save(File.builder()
//                        .extension(getExtensionByStringHandling(nm).get())
//                        .location(tempUrl)
//                        .name(filename)
//                        .build());
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//            }
//        }
//
//
//        return ResponseEntity.ok(savedFile);
//    }
//
//    @Override
//    public String storeFile(String location, MultipartFile file) {
//        final Path filesDir = Paths.get(location).toAbsolutePath().normalize();
//        String fileName = org.springframework.util.StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//
//        try {
////            if (fileName.contains("..")) {
////                throw new InvalidRequestException("Sorry! Filename contains invalid path sequence " + fileName);
////            }
//
//            if(Files.notExists(filesDir)){
//                Files.createDirectories(filesDir);
//            }
//
//            Path targetLocation = filesDir.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return filesDir.toString();
//        } catch (IOException ex) {
////            throw new SystemErrorException("Could not store file " + fileName + ". Please try again!");
//            return null;
//        }
//    }
//
//    @Override
//    public String createCsvFile(String location) {
//        final Path filesDir = Paths.get(location).toAbsolutePath().normalize();
//        String fileName = org.springframework.util.StringUtils.cleanPath(System.currentTimeMillis() + ".csv");
//        try {
//            if (Files.notExists(filesDir)) {
//                Files.createDirectories(filesDir);
//            }
//        } catch (IOException ex) {
////            throw new SystemErrorException("Could not store file " + fileName + ". Please try again!");
//        }
//
//        Path targetLocation = filesDir.resolve(fileName);
//        try {
//            Files.createFile(targetLocation);
//        } catch (IOException exception) {
////            throw new SystemErrorException("Could not store file " + fileName + ". Please try again!");
//        }
//
//        return targetLocation.toString();
//    }
//
//    public Optional<String> getExtensionByStringHandling(String filename) {
//        return Optional.ofNullable(filename)
//                .filter(f -> f.contains("."))
//                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
//    }
//
//    public String generateRandomString(int length) {
//
//        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
//                + "lmnopqrstuvwxyz";
//        SecureRandom rnd = new SecureRandom();
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++)
//            sb.append(chars.charAt(rnd.nextInt(chars.length())));
//        return sb.toString();
//
//    }
//
//}
