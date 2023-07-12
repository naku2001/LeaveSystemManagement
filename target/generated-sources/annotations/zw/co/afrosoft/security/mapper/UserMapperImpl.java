package zw.co.afrosoft.security.mapper;

import javax.annotation.processing.Generated;
import zw.co.afrosoft.model.user.User;
import zw.co.afrosoft.security.dto.AuthenticatedUserDto;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.security.dto.RegistrationRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-12T15:27:06+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User convertToUser(RegistrationRequest registrationRequest) {
        if ( registrationRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( registrationRequest.getFirstName() );
        user.lastName( registrationRequest.getLastName() );
        user.username( registrationRequest.getUsername() );
        user.password( registrationRequest.getPassword() );
        user.email( registrationRequest.getEmail() );

        return user.build();
    }

    @Override
    public AuthenticatedUserDto convertToAuthenticatedUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        AuthenticatedUserDto authenticatedUserDto = new AuthenticatedUserDto();

        authenticatedUserDto.setFirstName( user.getFirstName() );
        authenticatedUserDto.setLastName( user.getLastName() );
        authenticatedUserDto.setEmployee( user.getEmployee() );
        authenticatedUserDto.setEmail( user.getEmail() );
        authenticatedUserDto.setId( user.getId() );
        authenticatedUserDto.setUsername( user.getUsername() );
        authenticatedUserDto.setPassword( user.getPassword() );
        authenticatedUserDto.setUserRole( user.getUserRole() );

        return authenticatedUserDto;
    }

    @Override
    public User convertToUser(AuthenticatedUserDto authenticatedUserDto) {
        if ( authenticatedUserDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( authenticatedUserDto.getId() );
        user.firstName( authenticatedUserDto.getFirstName() );
        user.lastName( authenticatedUserDto.getLastName() );
        user.username( authenticatedUserDto.getUsername() );
        user.password( authenticatedUserDto.getPassword() );
        user.email( authenticatedUserDto.getEmail() );
        user.employee( authenticatedUserDto.getEmployee() );
        user.userRole( authenticatedUserDto.getUserRole() );

        return user.build();
    }

    @Override
    public User convertToUser(EmployeeRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( request.getFirstName() );
        user.lastName( request.getLastName() );
        user.username( request.getUsername() );
        user.password( request.getPassword() );
        user.email( request.getEmail() );

        return user.build();
    }
}
