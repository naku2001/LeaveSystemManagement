package zw.co.afrosoft.model;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Builder

@AllArgsConstructor
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	private String companyName;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String username;
	private String password;
	private String email;
	@OneToOne
	private Employee employee;
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	private int availableSickLeave;
	private int availableVacationLeave;



	private int availableUnpaidLeave;
	public User() {
		this.availableSickLeave = 10;
		this.availableVacationLeave = 22;
		this.availableUnpaidLeave = 365;
	}




}
