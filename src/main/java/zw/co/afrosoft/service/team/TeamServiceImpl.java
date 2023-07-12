package zw.co.afrosoft.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.controller.team.TeamRequest;
import zw.co.afrosoft.model.team.Team;
import zw.co.afrosoft.repository.team.TeamRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepo teamRepo;
    @Override
    public ResponseEntity<Team> create(TeamRequest request) {
        Team team = Team.builder().name(request.getName())
                        .teamLead(request.getTeamLeadName())
                                .description(request.getDescription()).build();
        Team saved = teamRepo.save(team);
        return ResponseEntity.ok().body(saved);
    }
    @Override
    public ResponseEntity<Team> get(Long id) {
        Optional<Team> team = teamRepo.findById(id);
        return ResponseEntity.ok().body(team.get());
    }

    @Override
    public List<Team> getAll() {
        List<Team> teamList = teamRepo.findAll();
        return teamList;
    }

    @Override
    public ResponseEntity<Team> delete(Long id) {
        Optional<Team> team = teamRepo.findById(id);
        return ResponseEntity.ok().body(team.get());
    }
}
