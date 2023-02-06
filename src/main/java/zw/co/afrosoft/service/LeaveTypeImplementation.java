package zw.co.afrosoft.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.Leaves;
import zw.co.afrosoft.repository.LeavesRepository;

import java.util.Optional;

@Service
public class LeaveTypeImplementation implements LeaveTypeService{

    private final LeavesRepository leavesRepository;

    public LeaveTypeImplementation(LeavesRepository leavesRepository) {
        this.leavesRepository = leavesRepository;
    }

    @Override
    public ResponseEntity setDefaultLeave(LeaveTypeRequest request) {
        Leaves leaves = new Leaves();

        leaves.setLeaveType(request.getLeaveType());
        leaves.setDefaultDays(request.getDefaultDays());
        leaves.setAvailableDays(request.getDefaultDays());
        leaves.setAvailableDays(request.getDefaultDays());
        return ResponseEntity.ok().body(leavesRepository.save(leaves));
    }

    @Override
    public Page getAlLeaves(int offset, int size) {

        return leavesRepository.findAll(PageRequest.of(offset, size));

    }

    @Override
    public ResponseEntity delete(Long id) {
        Optional<Leaves> leave = leavesRepository.findById(id);

        if(!leave.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("leave type not available");
        leavesRepository.delete(leave.get());
        return ResponseEntity.ok().body("Deleted Successfully");
    }



}
