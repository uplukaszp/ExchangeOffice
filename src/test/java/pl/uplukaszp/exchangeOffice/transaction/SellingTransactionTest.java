package pl.uplukaszp.exchangeOffice.transaction;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Test;

import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.MainWallet;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.domain.WalletId;
import pl.uplukaszp.exchangeOffice.repository.MainWalletRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;
import pl.uplukaszp.exchangeOffice.transaction.SellingTransaction;
import pl.uplukaszp.exchangeOffice.transaction.TransactionData;

public class SellingTransactionTest {

	@Test
	public void isEnoughtMoneyTest() {
		TransactionData data = getTransactionData();
		data.setAmount(1000L);
		WalletRepository walletRepo = mock(WalletRepository.class);
		MainWalletRepository mainWalletRepo = mock(MainWalletRepository.class);
		SellingTransaction transaction = new SellingTransaction(data, walletRepo, mainWalletRepo);

		Boolean isEnoughtMoney = transaction.hasEnoughtMoney();

		assertTrue(isEnoughtMoney);
	}

	@Test
	public void isNotEnoughtMoneyTest() {
		TransactionData data = getTransactionData();
		data.setAmount(1001L);
		WalletRepository walletRepo = mock(WalletRepository.class);
		MainWalletRepository mainWalletRepo = mock(MainWalletRepository.class);
		SellingTransaction transaction = new SellingTransaction(data, walletRepo, mainWalletRepo);

		Boolean isEnoughtMoney = transaction.hasEnoughtMoney();

		assertFalse(isEnoughtMoney);
	}

	@Test
	public void userHasEnoughtAndExchangeOfficeHasNotEnought() {
		TransactionData data = getTransactionData();
		data.setAmount(1000L);
		data.getMainSettlementWallet().setAmount(new BigDecimal(999));
		WalletRepository walletRepo = mock(WalletRepository.class);
		MainWalletRepository mainWalletRepo = mock(MainWalletRepository.class);
		SellingTransaction transaction = new SellingTransaction(data, walletRepo, mainWalletRepo);

		Boolean isEnoughtMoney = transaction.hasEnoughtMoney();

		assertFalse(isEnoughtMoney);
	}

	@Test
	public void saveTransactionTest() {
		TransactionData data = getTransactionData();
		data.setAmount(1L);
		WalletRepository walletRepo = mock(WalletRepository.class);
		MainWalletRepository mainWalletRepo = mock(MainWalletRepository.class);
		SellingTransaction transaction = new SellingTransaction(data, walletRepo, mainWalletRepo);

		Boolean transactionStatus = transaction.executeTransaction();

		assertTrue(transactionStatus);
		verify(walletRepo, times(1)).save(data.getUserWallet());
		verify(walletRepo, times(1)).save(data.getUserSettlementWallet());
		verify(mainWalletRepo, times(1)).save(data.getMainWallet());
		verify(mainWalletRepo, times(1)).save(data.getMainSettlementWallet());

	}

	@Test
	public void verifyCalculationTest() {
		TransactionData data = getTransactionData();
		data.setAmount(1L);
		WalletRepository walletRepo = mock(WalletRepository.class);
		MainWalletRepository mainWalletRepo = mock(MainWalletRepository.class);
		SellingTransaction transaction = new SellingTransaction(data, walletRepo, mainWalletRepo);

		Boolean transactionStatus = transaction.executeTransaction();

		assertTrue(transactionStatus);
		assertEquals(new BigDecimal(1001), data.getUserSettlementWallet().getAmount());
		assertEquals(new BigDecimal(999), data.getUserWallet().getAmount());
		assertEquals(new BigDecimal(999), data.getMainSettlementWallet().getAmount());
		assertEquals(new BigDecimal(1001), data.getMainWallet().getAmount());

	}

	private TransactionData getTransactionData() {
		TransactionData data = new TransactionData();
		data.setCurrency(Currency.USD);
		data.setMainSettlementWallet(getMainSettlementWallet());
		data.setMainWallet(getMainWallet());
		data.setPrice(new BigDecimal(1));
		data.setUnit(1);
		data.setUserSettlementWallet(getUserSettlementWallet());
		data.setUserWallet(getUserWallet());
		return data;
	}

	private MainWallet getMainSettlementWallet() {
		MainWallet m = new MainWallet();
		m.setAmount(new BigDecimal(1000));
		return m;
	}

	private MainWallet getMainWallet() {
		MainWallet m = new MainWallet();
		m.setAmount(new BigDecimal(1000));
		return m;
	}

	private Wallet getUserSettlementWallet() {
		Wallet w = new Wallet();
		w.setId(new WalletId());
		w.setAmount(new BigDecimal(1000));
		return w;
	}

	private Wallet getUserWallet() {
		Wallet w = new Wallet();
		w.setAmount(new BigDecimal(1000));
		return w;
	}
}
