package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.afrosoft.model.RoleRequest;
import zw.co.afrosoft.service.RoleService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;

    ResponseEntity createRole(RoleRequest roleRequest){
        return roleService.create(roleRequest);

    }
}
