/**
 * 
 */
package com.github.ansell.jdefaultdict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * Tests for {@link JDefaultDict}
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class JDefaultDictTest {

	@Test
	public final void testGetExists() {
		ConcurrentMap<String, List<String>> test = new JDefaultDict<>(
				k -> new ArrayList<>());
		assertFalse(test.containsKey("test"));
		test.put("test", new ArrayList<>());
		assertTrue(test.containsKey("test"));
		List<String> testList = test.get("test");
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

	@Test
	public final void testGetNotExists() {
		ConcurrentMap<String, List<String>> test = new JDefaultDict<>(
				k -> new ArrayList<>());
		assertFalse(test.containsKey("test"));
		List<String> testList = test.get("test");
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

	@Test
	public final void testGetChained() {
		ConcurrentMap<String, ConcurrentMap<String, List<String>>> test = new JDefaultDict<>(
				k1 -> new JDefaultDict<>(k2 -> new ArrayList<>()));
		assertFalse(test.containsKey("test"));
		List<String> testList = test.get("test").get("test");
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

	@Test
	public final void testGetChainedAtomicIntegerSerial() {
		ConcurrentMap<String, ConcurrentMap<String, AtomicInteger>> test = new JDefaultDict<>(
				k1 -> new JDefaultDict<>(k2 -> new AtomicInteger(0)));
		assertFalse(test.containsKey("test"));
		for (int i = 1; i < 1000000; i++) {
			int testValue = test.get("test").get("test").incrementAndGet();
			assertEquals(i, testValue);
		}
	}
}
