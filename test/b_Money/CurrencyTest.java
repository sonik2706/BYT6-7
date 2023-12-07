package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals(SEK.getName(), "SEK");
		assertEquals(DKK.getName(), "DKK");
		assertEquals(EUR.getName(), "EUR");
	}
	
	@Test
	public void testGetRate() {
		assertEquals(SEK.getRate(), 0.15, 0);
		assertEquals(DKK.getRate(), 0.20, 0);
		assertEquals(EUR.getRate(), 1.5, 0);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(1.0);
		DKK.setRate(2.0);
		EUR.setRate(5.5);
		
		assertEquals(SEK.getRate(), 1.0, 0);
		assertEquals(DKK.getRate(), 2.0, 0);
		assertEquals(EUR.getRate(), 5.5, 0);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(15, (int) SEK.universalValue(100));
		assertEquals(20, (int) DKK.universalValue(100));
		assertEquals(150, (int) EUR.universalValue(100));
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(20, (int) SEK.valueInThisCurrency(2, EUR));
		assertEquals(15, (int) DKK.valueInThisCurrency(2, EUR));
	}

}
