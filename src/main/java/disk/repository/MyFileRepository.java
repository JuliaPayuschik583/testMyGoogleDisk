package disk.repository;

import disk.domain.MyFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Julia
 */
public interface MyFileRepository extends CrudRepository<MyFile, Long> {
    @Query("select f from MyFile f where f.key = :key")
    MyFile findMyFileByKey(@Param("key") String key);

}
