package zw.co.afrosoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
