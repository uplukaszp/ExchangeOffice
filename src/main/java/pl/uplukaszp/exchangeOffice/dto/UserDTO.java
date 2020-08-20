package pl.uplukaszp.exchangeOffice.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
	@NotBlank(message = "Login can't be blank")
	String login;
	@NotBlank(message = "Password can't be blank")
	@Size(min = 5, message = "Size must be at least 5")
	private String password;
	@Min(value = 0, message = "Value must be greather than 0")
	@Max(value=(long) Float.MAX_VALUE, message = "Enter a smaller amount")
	private Float gbp;
	@Min(value = 0, message = "Value must be greather than 0")
	@Max(value=(long) Float.MAX_VALUE, message = "Enter a smaller amount")
	private Float eur;
	@Min(value = 0, message = "Value must be greather than 0")
	@Max(value=(long) Float.MAX_VALUE, message = "Enter a smaller amount")
	private Float usd;
	@Min(value = 0, message = "Value must be greather than 0")
	@Max(value=(long) Float.MAX_VALUE, message = "Enter a smaller amount")
	private Float czk;
	@Min(value = 0, message = "Value must be greather than 0")
	@Max(value=(long) Float.MAX_VALUE, message = "Enter a smaller amount")
	private Float pln;
	@Min(value = 0, message = "Value must be greather than 0")
	@Max(value=(long) Float.MAX_VALUE, message = "Enter a smaller amount")
	private Float cfh;
	@Min(value = 0, message = "Value must be greather than 0")
	@Max(value=(long) Float.MAX_VALUE, message = "Enter a smaller amount")
	private Float rub;
}
