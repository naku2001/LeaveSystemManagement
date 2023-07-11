package zw.co.afrosoft.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.file.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
