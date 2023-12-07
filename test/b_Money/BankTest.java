package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("TestAccount");
		assertEquals(0, SweBank.getBalance("TestAccount"), 0);
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		Money m = new Money(1000_00, SEK);
		
		SweBank.deposit("Ulrika", m);
		Nordea.deposit("Bob", m);
		DanskeBank.deposit("Gertrud", m);
		
		assertEquals(1000, SweBank.getBalance("Ulrika"), 0);
		assertEquals(1000, Nordea.getBalance("Bob"), 0);
		assertEquals(750, DanskeBank.getBalance("Gertrud"), 0);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		Money m = new Money(1000_00, SEK);
		
		SweBank.withdraw("Ulrika", m);
		Nordea.withdraw("Bob", m);
		DanskeBank.withdraw("Gertrud", m);
		
		assertEquals(-1000, SweBank.getBalance("Ulrika"), 0);
		assertEquals(-1000, Nordea.getBalance("Bob"), 0);
		assertEquals(-750, DanskeBank.getBalance("Gertrud"), 0);
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, SweBank.getBalance("Ulrika"), 0);
		assertEquals(0, SweBank.getBalance("Bob"), 0);
		assertEquals(0, Nordea.getBalance("Bob"), 0);
		assertEquals(0, DanskeBank.getBalance("Gertrud"), 0);		
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		Money m = new Money(800_00, SEK);
		SweBank.deposit("Ulrika", m);
		assertEquals(800, SweBank.getBalance("Ulrika"), 0);
		SweBank.transfer("Ulrika", DanskeBank, "Gertrud", m);
		assertEquals(0, SweBank.getBalance("Ulrika"), 0);
		assertEquals(600, DanskeBank.getBalance("Gertrud"), 0);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money m = new Money(200_00, SEK);
		SweBank.deposit("Ulrika", m);
		SweBank.addTimedPayment("Ulrika", "tp1", 3, 1, m, DanskeBank, "Gertrud");
		assertEquals(200, SweBank.getBalance("Ulrika"), 0);
		SweBank.tick();
		assertEquals(0, SweBank.getBalance("Ulrika"), 0);
		assertEquals(150, DanskeBank.getBalance("Gertrud"), 0);
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		assertEquals(-200, SweBank.getBalance("Ulrika"), 0);
		assertEquals(300, DanskeBank.getBalance("Gertrud"), 0);
	}
}
