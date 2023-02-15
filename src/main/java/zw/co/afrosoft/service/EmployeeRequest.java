package zw.co.afrosoft.service;

import lombok.*;
import zw.co.afrosoft.model.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EmployeeRequest {

    private String firstName;
    private String lastName;
    private Gender gender;
    private String dateOfBirth;

    private String email;
    private String password;
    private String username;

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
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
