package disk.controllers;

import disk.domain.MyFile;
import disk.repository.MyFileRepository;
import disk.rest.FileInfo;
import disk.rest.Key;
import disk.storage.StorageService;
import disk.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Julia
 */
@RestController
public class FileController {

    private final StorageService storageService;
    @Autowired
    private MyFileRepository myFileRepository;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public Key upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "description", required = false) String description) throws StorageException {
        //create new data:
        MyFile myFile = new MyFile();
        myFile.setDescription(description);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        myFile.setExpirationDate(calendar.getTime());

        //need create unique key, using id and description
        MyFile savedMyFile = myFileRepository.save(myFile);
        String key = "key" + savedMyFile.hashCode();
        savedMyFile.setKey(key);
        myFileRepository.save(savedMyFile);

        //save to server
        storageService.store(file, key);

        return new Key(key);
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<Resource> getFile(@PathVariable String key) throws Exception {
        MyFile myFile = myFileRepository.findMyFileByKey(key);
        if (myFile == null) {
            throw new Exception("file with key=" + key + " is not exist");
        }
        Date now = new Date();
        if(now.after(myFile.getExpirationDate())) {
            myFileRepository.delete(myFile);
            storageService.deleteByKey(key);
            throw new Exception("file with key=" + key + " was expiration (and deleted)");
        }
        Resource file = storageService.loadAsResourceByKey(key);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @DeleteMapping("/delete/{key}")
    public FileInfo deleteFileByKey(@PathVariable String key) throws Exception {
        MyFile myFile = myFileRepository.findMyFileByKey(key);
        if (myFile == null) {
            throw new Exception("wrong key=" + key);
        }
        FileInfo fileInfo = new FileInfo(myFile.getDescription(), myFile.getKey(), true);
        myFileRepository.delete(myFile);
        storageService.deleteByKey(key);
        return fileInfo;
    }


}
