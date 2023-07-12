package zw.co.afrosoft.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.team.Team;

public interface TeamRepo extends JpaRepository<Team,Long> {
}
