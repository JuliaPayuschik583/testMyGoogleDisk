package disk.storage;

import disk.storage.exception.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author Julia
 */
public interface StorageService {

    void init() throws StorageException;

    void store(MultipartFile file, String folderKey) throws StorageException;

    Stream<Path> loadByKey(String key) throws StorageException;

    void deleteByKey(String key) throws StorageException;

    Resource loadAsResourceByKey(String key) throws StorageException;

    void deleteAll();

}
