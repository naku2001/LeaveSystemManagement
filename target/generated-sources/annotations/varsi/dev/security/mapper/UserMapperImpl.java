package varsi.dev.security.mapper;

import javax.annotation.processing.Generated;
import varsi.dev.model.User;
import varsi.dev.security.dto.AuthenticatedUserDto;
import varsi.dev.security.dto.RegistrationRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-16T22:26:41+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.5 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User convertToUser(RegistrationRequest registrationRequest) {
        if ( registrationRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( registrationRequest.getName() );
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

        authenticatedUserDto.setName( user.getName() );
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

        user.name( authenticatedUserDto.getName() );
        user.username( authenticatedUserDto.getUsername() );
        user.password( authenticatedUserDto.getPassword() );
        user.userRole( authenticatedUserDto.getUserRole() );

        return user.build();
    }
}
