package zw.co.afrosoft.service.team;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.controller.team.TeamRequest;
import zw.co.afrosoft.model.team.Team;

import java.util.List;

public interface TeamService {

    ResponseEntity<Team> create(TeamRequest request);

    ResponseEntity<Team> get(Long id);

    List<Team> getAll();

    ResponseEntity<Team> delete(Long id);
}
