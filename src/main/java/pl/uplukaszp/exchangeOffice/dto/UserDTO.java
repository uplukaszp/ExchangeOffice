package pl.uplukaszp.exchangeOffice.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
	@NotBlank
	String login;
	@NotBlank
	@Size(min = 5)
	String password;
	Float gpb;
	Float eur;
	Float usd;
	Float czk;
	Float pln;
}
