package zw.co.afrosoft.controller.team;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.team.Team;
import zw.co.afrosoft.service.team.TeamService;

import java.util.List;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamRestController {

    private final TeamService teamService;
    @PostMapping("create")
    @Operation(summary = "Create team")
    public ResponseEntity<Team> create(@RequestBody TeamRequest request){
        return teamService.create(request);
    }
    @Operation(summary = "Get team")
    @GetMapping("{id}")
    public  ResponseEntity<Team> get(@PathVariable  Long id){
        return teamService.get(id);
    }
    @Operation(summary = "Get all teams")
    @GetMapping()
    public List<Team> getAll(){
        return teamService.getAll();
    }
    @Operation(summary = "Delete team")
    @DeleteMapping("{id}")
    public ResponseEntity<Team> delete(@PathVariable Long id){
        return teamService.delete(id);
    }
}
