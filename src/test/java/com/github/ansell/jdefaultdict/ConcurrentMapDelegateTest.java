/**
 * 
 */
package com.github.ansell.jdefaultdict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link ConcurrentMapDelegate}.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class ConcurrentMapDelegateTest {

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#hashCode()}.
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testHashCode() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#ConcurrentMapDelegate(java.util.concurrent.ConcurrentMap)}
	 * .
	 */
	@Test
	public final void testConcurrentMapDelegateConcurrentMapOfKV()
			throws Exception {
		ConcurrentMap<String, List<String>> delegate = new ConcurrentHashMap<>();
		delegate.put("test1", new ArrayList<>());
		ConcurrentMap<String, List<String>> test = new ConcurrentMapDelegate<>(
				delegate);
		assertTrue(test.containsKey("test1"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#ConcurrentMapDelegate(java.util.function.Supplier)}
	 * .
	 */
	@Test
	public final void testConcurrentMapDelegateSupplierOfConcurrentMapOfKV()
			throws Exception {
		ConcurrentMap<String, List<String>> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		test.put("test1", new ArrayList<>());
		assertEquals(1, test.size());
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#clear()}.
	 */
	@Test
	public final void testClear() throws Exception {
		ConcurrentMap<String, List<String>> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		test.put("test1", new ArrayList<>());
		assertEquals(1, test.size());
		test.clear();
		assertEquals(0, test.size());
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#compute(java.lang.Object, java.util.function.BiFunction)}
	 * .
	 */
	@Test
	public final void testCompute() throws Exception {
		ConcurrentMap<String, List<String>> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		test.put("test1", Arrays.asList("original"));
		assertEquals(1, test.size());
		List<String> testList = test.get("test1");
		assertEquals(1, testList.size());
		assertFalse(testList.contains("dummy"));
		assertTrue(testList.contains("original"));

		test.compute("test1", (a, b) -> Arrays.asList("dummy"));

		assertEquals(1, test.size());
		List<String> testListOriginal = test.get("test1");
		assertEquals(1, testListOriginal.size());
		assertTrue(testListOriginal.contains("dummy"));
		assertFalse(testListOriginal.contains("original"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#computeIfAbsent(java.lang.Object, java.util.function.Function)}
	 * .
	 */
	@Test
	public final void testComputeIfAbsent() throws Exception {
		ConcurrentMap<String, List<String>> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		test.put("test1", Arrays.asList("original"));
		assertEquals(1, test.size());
		List<String> testList = test.get("test1");
		assertEquals(1, testList.size());
		assertFalse(testList.contains("dummy"));
		assertTrue(testList.contains("original"));

		test.computeIfAbsent("test1", k -> Arrays.asList("dummy"));

		assertEquals(1, test.size());
		List<String> testListOriginal = test.get("test1");
		assertEquals(1, testListOriginal.size());
		assertFalse(testList.contains("dummy"));
		assertTrue(testList.contains("original"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#computeIfPresent(java.lang.Object, java.util.function.BiFunction)}
	 * .
	 */
	@Test
	public final void testComputeIfPresent() throws Exception {
		ConcurrentMap<String, List<String>> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		test.put("test1", Arrays.asList("original"));
		assertEquals(1, test.size());
		List<String> testList = test.get("test1");
		assertEquals(1, testList.size());
		assertFalse(testList.contains("dummy"));
		assertTrue(testList.contains("original"));

		test.computeIfPresent("test1", (a, b) -> Arrays.asList("dummy"));

		assertEquals(1, test.size());
		List<String> testListOriginal = test.get("test1");
		assertEquals(1, testListOriginal.size());
		assertTrue(testListOriginal.contains("dummy"));
		assertFalse(testListOriginal.contains("original"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#containsKey(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testContainsKey() throws Exception {
		ConcurrentMap<String, List<String>> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		assertFalse(test.containsKey("test1"));
		test.put("test1", Arrays.asList("original"));
		assertEquals(1, test.size());
		assertTrue(test.containsKey("test1"));
		assertFalse(test.containsKey("test2"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#containsValue(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testContainsValue() throws Exception {
		ConcurrentMap<String, String> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		assertFalse(test.containsKey("test1"));
		assertFalse(test.containsValue("original"));
		test.put("test1", "original");
		assertEquals(1, test.size());
		assertTrue(test.containsKey("test1"));
		assertTrue(test.containsValue("original"));
		assertFalse(test.containsKey("dummy"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#entrySet()}.
	 */
	@Test
	public final void testEntrySet() throws Exception {
		ConcurrentMap<String, String> test = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test.isEmpty());
		assertEquals(0, test.size());
		assertEquals(0, test.entrySet().size());
		test.put("test1", "original");
		assertEquals(1, test.size());
		assertEquals(1, test.entrySet().size());
		assertTrue(test.containsKey("test1"));
		assertTrue(test.containsValue("original"));
		assertFalse(test.containsKey("dummy"));
		Entry<String, String> next = test.entrySet().iterator().next();
		assertEquals("test1", next.getKey());
		assertEquals("original", next.getValue());
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testEqualsObjectLateBinding() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		ConcurrentMap<String, String> test2 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		// Two empty maps should be equals
		assertTrue(test1.equals(test2));
		test1.put("test1", "original");
		assertFalse(test1.equals(test2));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testEqualsObjectSameDelegate() throws Exception {
		ConcurrentMap<String, String> delegate = new ConcurrentHashMap<>();
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				delegate);
		ConcurrentMap<String, String> test2 = new ConcurrentMapDelegate<>(
				delegate);
		assertTrue(test1.equals(test2));
		test1.put("test1", "original");
		// Same backing map, so both should behave the same way
		assertTrue(test1.equals(test2));
		assertTrue(test1.containsKey("test1"));
		assertTrue(test2.containsKey("test1"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#forEach(java.util.function.BiConsumer)}
	 * .
	 */
	@Test
	public final void testForEach() throws Exception {
		List<String> resultKeys = new ArrayList<>();
		List<String> resultValues = new ArrayList<>();
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		test1.forEach((k, v) -> resultKeys.add(k));
		test1.forEach((k, v) -> resultValues.add(v));
		assertTrue(test1.isEmpty());
		assertTrue(resultKeys.isEmpty());
		assertTrue(resultValues.isEmpty());

		test1.put("test1", "original");
		test1.forEach((k, v) -> resultKeys.add(k));
		test1.forEach((k, v) -> resultValues.add(v));
		assertFalse(test1.isEmpty());
		assertFalse(resultKeys.isEmpty());
		assertFalse(resultValues.isEmpty());
		assertEquals(1, test1.size());
		assertEquals(1, resultKeys.size());
		assertEquals(1, resultValues.size());
		assertTrue(test1.containsKey("test1"));
		assertTrue(resultKeys.contains("test1"));
		assertTrue(test1.containsValue("original"));
		assertTrue(resultValues.contains("original"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#get(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testGet() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		
		assertNull(test1.get("test1"));
		test1.put("test1", "original");
		assertEquals("original", test1.get("test1"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#getOrDefault(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Test
	public final void testGetOrDefault() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		
		assertNull(test1.get("test1"));
		assertEquals("dummy", test1.getOrDefault("test1", "dummy"));
		test1.put("test1", "original");
		assertEquals("original", test1.get("test1"));
		assertEquals("original", test1.getOrDefault("test1", "dummy"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#isEmpty()}.
	 */
	@Test
	public final void testIsEmpty() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());
		test1.put("test1", "original");
		assertFalse(test1.isEmpty());
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#keySet()}.
	 */
	@Test
	public final void testKeySet() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.keySet().isEmpty());
		test1.put("test1", "original");
		assertFalse(test1.keySet().isEmpty());
		assertEquals(1, test1.keySet().size());
		assertTrue(test1.keySet().contains("test1"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#merge(java.lang.Object, java.lang.Object, java.util.function.BiFunction)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testMerge() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#put(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testPut() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#putAll(java.util.Map)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testPutAll() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#putIfAbsent(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testPutIfAbsent() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#remove(java.lang.Object)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testRemoveObject() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#remove(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testRemoveObjectObject() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#replace(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testReplaceKV() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#replace(java.lang.Object, java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testReplaceKVV() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#replaceAll(java.util.function.BiFunction)}
	 * .
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testReplaceAll() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#size()}.
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testSize() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#toString()}.
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testToString() throws Exception {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#values()}.
	 */
	@Ignore("TODO: Implement me")
	@Test
	public final void testValues() throws Exception {
		fail("Not yet implemented"); // TODO
	}

}
