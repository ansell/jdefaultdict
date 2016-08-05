/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to <http://unlicense.org>
 * 
 */
package com.github.ansell.jdefaultdict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * Tests for {@link JDefaultDict}
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class JDefaultDictTest {

	@Test
	public final void testGetExists() {
		ConcurrentMap<String, List<String>> test = new JDefaultDict<>(k -> new ArrayList<>());
		assertFalse(test.containsKey("test"));
		test.put("test", new ArrayList<>());
		assertTrue(test.containsKey("test"));
		List<String> testList = test.get("test");
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

	@Test
	public final void testGetExistsInDelegate() {
		ConcurrentMap<String, List<String>> delegate = new ConcurrentHashMap<>();
		delegate.put("test", new ArrayList<>());
		ConcurrentMap<String, List<String>> test = new JDefaultDict<>(k -> new ArrayList<>(), delegate);
		assertTrue(test.containsKey("test"));
		List<String> testList = test.get("test");
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

	@Test
	public final void testGetNotExists() {
		ConcurrentMap<String, List<String>> test = new JDefaultDict<>(k -> new ArrayList<>());
		assertFalse(test.containsKey("test"));
		List<String> testList = test.get("test");
		assertTrue(test.containsKey("test"));
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

	@Test
	public final void testGetChained() {
		ConcurrentMap<String, ConcurrentMap<String, List<String>>> test = new JDefaultDict<>(
				k1 -> new JDefaultDict<>(k2 -> new ArrayList<>()));
		assertFalse(test.containsKey("test"));
		List<String> testList = test.get("test").get("test");
		assertTrue(test.containsKey("test"));
		assertTrue(test.get("test").containsKey("test"));
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

	@Test
	public final void testGetChainedAtomicIntegerSerial() {
		ConcurrentMap<String, ConcurrentMap<String, AtomicInteger>> test = new JDefaultDict<>(
				k1 -> new JDefaultDict<>(k2 -> new AtomicInteger(0)));
		assertFalse(test.containsKey("test"));
		for (int i = 1; i < 1000001; i++) {
			int testValue = test.get("test").get("test").incrementAndGet();
			assertEquals(i, testValue);
		}
		assertEquals(1000000, test.get("test").get("test").intValue());
	}

	@Test
	public final void testGetChainedAtomicIntegerParallel() {
		ConcurrentMap<String, ConcurrentMap<String, AtomicInteger>> test = new JDefaultDict<>(
				k1 -> new JDefaultDict<>(k2 -> new AtomicInteger(0)));
		assertFalse(test.containsKey("test"));
		IntStream.range(1, 1000001).parallel().forEach(i -> {
			int testValue = test.get("test").get("test").incrementAndGet();
			assertTrue(testValue > 0);
		});
		assertEquals(1000000, test.get("test").get("test").intValue());
	}

	@Test
	public final void testGetLateBindingBackingMap() {
		ConcurrentMap<String, List<String>> test = new JDefaultDict<>(k -> new ArrayList<>(),
				() -> new ConcurrentHashMap<>());
		assertFalse(test.containsKey("test"));
		test.put("test", new ArrayList<>());
		assertTrue(test.containsKey("test"));
		List<String> testList = test.get("test");
		assertNotNull(testList);
		assertTrue(testList.isEmpty());
	}

}
