package pl.uplukaszp.exchangeOffice.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;


import lombok.Data;

@Data
@Embeddable
public class WalletId implements Serializable{
	
	private Long userId;
	private Currency currency;
}