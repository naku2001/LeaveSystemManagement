package zw.co.afrosoft.security.mapper;

import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.user.User;
import zw.co.afrosoft.security.dto.AuthenticatedUserDto;
import zw.co.afrosoft.security.dto.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import zw.co.afrosoft.security.dto.EmployeeRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User convertToUser(RegistrationRequest registrationRequest);



	AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

	User convertToUser(AuthenticatedUserDto authenticatedUserDto);

	User convertToUser(EmployeeRequest request);

//	User convertToUser(Employee employees);
}
