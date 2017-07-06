package disk.storage;

import disk.storage.exception.StorageException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Julia
 */
@Service
public class FileStorageService implements StorageService {

    private final String location = "upload-directory";
    private final Path rootLocation = Paths.get(location);

    @Override
    public void store(MultipartFile file, String folderKey) throws StorageException {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }

            Path pathFolderKey = Paths.get(location + File.separator + folderKey);
            Files.createDirectories(pathFolderKey);

            Files.copy(file.getInputStream(), pathFolderKey.resolve(file.getOriginalFilename()));
        } catch (IOException ex) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), ex);
        }
    }

    @Override
    public Stream<Path> loadByKey(String key) throws StorageException {
        try {
            Path pathFolderKey = Paths.get(location + File.separator + key);
            return Files.walk(pathFolderKey, 1)
                    .filter(Files::isRegularFile);
        } catch (IOException ex) {
            throw new StorageException("Failed to read stored files", ex);
        }

    }

    @Override
    public void deleteByKey(String key) throws StorageException {
        Path pathFolderKey = Paths.get(location + File.separator + key);
        try {
            FileUtils.deleteDirectory(pathFolderKey.toFile());
        } catch (IOException ex) {
            throw new StorageException("Failed to delete stored file", ex);
        }
    }

    @Override
    public Resource loadAsResourceByKey(String key) throws StorageException {
        try {
            Stream<Path> paths = loadByKey(key);
            List<Path> pathList = paths.collect(Collectors.toList());

            Resource resource = new UrlResource(pathList.get(0).toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException("Could not read file with key=" + key);
            }
        } catch (MalformedURLException ex) {
            throw new StorageException("Could not read file with key=" + key, ex);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() throws StorageException {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException ex) {
            throw new StorageException("Could not initialize storage", ex);
        }
    }
}
