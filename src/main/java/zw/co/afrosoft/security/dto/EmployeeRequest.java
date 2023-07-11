package zw.co.afrosoft.security.dto;

import lombok.*;
import zw.co.afrosoft.model.Departments;
import zw.co.afrosoft.model.Gender;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class EmployeeRequest {

    @NotEmpty(message = "{first_name_not_empty}")
    private String firstName;
    @NotEmpty(message = "{lastname_not_empty}")
    private String lastName;
    @NotEmpty(message = "{gender_not_empty}")
    private Gender gender;

    @NotEmpty(message = "{date_of_birth_not_empty}")
    private String dateOfBirth;
    @Email(message = "{registration_email_is_not_valid}")
    @NotEmpty(message = "{registration_email_not_empty}")
    private String email;

    @NotEmpty(message = "{password_not_empty}")
    private String password;

    @NotEmpty(message = "{username_not_empty}")
    private String username;

    private Long departmentId;

    public String getFirstName() {
        return firstName;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }
}
