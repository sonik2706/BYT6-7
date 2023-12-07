package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(100, SEK100.getAmount(), 0);
		assertEquals(10, EUR10.getAmount(), 0);
		assertEquals(200, SEK200.getAmount(), 0);
		assertEquals(20, EUR20.getAmount(), 0);
		assertEquals(0, SEK0.getAmount(), 0);
		assertEquals(0, EUR0.getAmount(), 0);
		assertEquals(-100, SEKn100.getAmount(), 0);
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK200.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());
		assertEquals("200.0 SEK", SEK200.toString());
		assertEquals("20.0 EUR", EUR20.toString());
		assertEquals("0.0 SEK", SEK0.toString());
		assertEquals("0.0 EUR", EUR0.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500, (int) SEK100.universalValue());
		assertEquals(1500, (int) EUR10.universalValue());
		assertEquals(3000, (int) SEK200.universalValue());
		assertEquals(3000, (int) EUR20.universalValue());
		assertEquals(0, (int) SEK0.universalValue());
		assertEquals(0, (int) EUR0.universalValue());
		assertEquals(-1500, (int) SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(SEK100));
		assertTrue(EUR10.equals(EUR10));
		assertTrue(SEK200.equals(SEK200));
		assertTrue(EUR20.equals(EUR20));
		assertTrue(SEK0.equals(SEK0));
		assertTrue(EUR0.equals(EUR0));
		assertTrue(SEKn100.equals(SEKn100));
		assertTrue(SEK0.equals(EUR0));
		assertTrue(SEK100.equals(EUR10));
		assertTrue(EUR20.equals(SEK200));
		assertFalse(SEK100.equals(SEK200));
		assertFalse(SEK100.equals(EUR20));
		assertFalse(SEK100.equals(SEKn100));
	}

	@Test
	public void testAdd() {
		assertTrue(SEK200.equals(SEK100.add(EUR10)));
		assertTrue(EUR20.equals(EUR10.add(SEK100)));
		assertTrue(SEK0.equals(SEKn100.add(SEK100)));
		assertTrue(EUR0.equals(EUR10.add(SEKn100)));
	}

	@Test
	public void testSub() {
		assertTrue(SEK100.equals(SEK200.sub(EUR10)));
		assertTrue(EUR10.equals(EUR20.sub(SEK100)));
		assertTrue(SEK0.equals(SEKn100.sub(SEKn100)));
		assertTrue(EUR0.equals(EUR10.sub(SEK100)));	
	}

	@Test
	public void testIsZero() {
		assertFalse(SEK100.isZero());
		assertFalse(EUR10.isZero());
		assertFalse(SEK200.isZero());
		assertFalse(EUR20.isZero());
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());
		assertFalse(SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(-100, SEK100.negate().getAmount(), 0);
		assertEquals(-10, EUR10.negate().getAmount(), 0);
		assertEquals(-200, SEK200.negate().getAmount(), 0);
		assertEquals(-20, EUR20.negate().getAmount(), 0);
		assertEquals(0, SEK0.negate().getAmount(), 0);
		assertEquals(0, EUR0.negate().getAmount(), 0);
		assertEquals(100, SEKn100.negate().getAmount(), 0);
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(SEK100));
		assertEquals(0, EUR10.compareTo(EUR10));
		assertEquals(0, SEK200.compareTo(SEK200));
		assertEquals(0, EUR20.compareTo(EUR20));
		assertEquals(0, SEK0.compareTo(SEK0));
		assertEquals(0, EUR0.compareTo(EUR0));
		assertEquals(0, SEKn100.compareTo(SEKn100));
		assertEquals(0, SEK0.compareTo(EUR0));
		assertEquals(-1, SEK100.compareTo(SEK200));
		assertEquals(1, EUR20.compareTo(EUR10));	
		assertEquals(-1, EUR10.compareTo(SEK200));
		assertEquals(1, SEK200.compareTo(EUR10));
	}
}
