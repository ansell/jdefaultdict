/**
 * 
 */
package com.github.ansell.jdefaultdict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		assertEquals(test1.hashCode(), test2.hashCode());
		test1.put("test1", "original");
		assertFalse(test1.equals(test2));
		assertNotEquals(test1.hashCode(), test2.hashCode());
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
		assertEquals(test1.hashCode(), test2.hashCode());
		assertEquals(test1.hashCode(), delegate.hashCode());
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
	@Test
	public final void testMerge() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		
		test1.merge("test", "original", (k, v) -> "bogus");
		assertTrue(test1.containsKey("test"));
		assertTrue(test1.containsValue("original"));
		assertFalse(test1.containsValue("bogus"));
		test1.merge("test", "corrected", (a, b) -> a + "-after-" + b);
		assertTrue(test1.containsKey("test"));
		assertFalse(test1.containsValue("original"));
		assertFalse(test1.containsValue("bogus"));
		assertTrue(test1.containsValue("original-after-corrected"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#put(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Test
	public final void testPut() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.values().isEmpty());
		test1.put("test", "original");
		assertFalse(test1.values().isEmpty());
		assertTrue(test1.values().contains("original"));
		assertFalse(test1.keySet().isEmpty());
		assertTrue(test1.keySet().contains("test"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#putAll(java.util.Map)}
	 * .
	 */
	@Test
	public final void testPutAll() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());
		test1.putAll(Collections.singletonMap("test", "next"));
		assertFalse(test1.values().isEmpty());
		assertTrue(test1.values().contains("next"));
		assertFalse(test1.keySet().isEmpty());
		assertTrue(test1.keySet().contains("test"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#putIfAbsent(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Test
	public final void testPutIfAbsent() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());

		String putIfAbsent = test1.putIfAbsent("test", "original");
		assertNull(putIfAbsent);
		assertFalse(test1.values().isEmpty());
		assertTrue(test1.values().contains("original"));
		assertFalse(test1.keySet().isEmpty());
		assertTrue(test1.keySet().contains("test"));

		String putIfAbsent2 = test1.putIfAbsent("test", "next");
		assertEquals("original", putIfAbsent2);
		assertFalse(test1.values().isEmpty());
		assertTrue(test1.values().contains("original"));
		assertFalse(test1.values().contains("next"));
		assertFalse(test1.keySet().isEmpty());
		assertTrue(test1.keySet().contains("test"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#remove(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testRemoveObject() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());
		assertNull(test1.remove("test"));

		test1.put("test", "original");
		assertEquals("original", test1.remove("test"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#remove(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Test
	public final void testRemoveObjectObject() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());
		assertNull(test1.remove("test"));

		test1.put("test", "original");
		assertFalse(test1.remove("test", "dummy"));
		assertTrue(test1.remove("test", "original"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#replace(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Test
	public final void testReplaceKV() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());
		assertNull(test1.replace("test", "bogus"));

		test1.put("test", "original");
		assertTrue(test1.containsValue("original"));
		assertEquals("original", test1.replace("test", "dummy"));
		assertFalse(test1.containsValue("original"));
		assertTrue(test1.containsValue("dummy"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#replace(java.lang.Object, java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Test
	public final void testReplaceKVV() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());
		assertFalse(test1.replace("test", "dummy", "bogus"));
		assertFalse(test1.containsKey("test"));
		assertFalse(test1.containsValue("dummy"));
		assertFalse(test1.containsValue("bogus"));

		test1.put("test", "original");
		assertTrue(test1.containsKey("test"));
		assertTrue(test1.containsValue("original"));
		assertFalse(test1.containsValue("dummy"));
		assertFalse(test1.containsValue("bogus"));

		assertFalse(test1.replace("test", "dummy", "bogus2"));
		assertTrue(test1.containsKey("test"));
		assertTrue(test1.containsValue("original"));
		assertFalse(test1.containsValue("dummy"));
		assertFalse(test1.containsValue("bogus"));

		assertTrue(test1.replace("test", "original", "corrected"));
		assertTrue(test1.containsKey("test"));
		assertFalse(test1.containsValue("original"));
		assertFalse(test1.containsValue("dummy"));
		assertFalse(test1.containsValue("bogus"));
		assertTrue(test1.containsValue("corrected"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#replaceAll(java.util.function.BiFunction)}
	 * .
	 */
	@Test
	public final void testReplaceAll() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.isEmpty());
		test1.replaceAll((k, v) -> {
			throw new RuntimeException("Should not happen");
		});

		test1.put("test", "original");
		assertTrue(test1.containsKey("test"));
		assertTrue(test1.containsValue("original"));
		
		test1.replaceAll((k, v) -> "corrected");
		assertTrue(test1.containsKey("test"));
		assertFalse(test1.containsValue("original"));
		assertTrue(test1.containsValue("corrected"));
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#size()}.
	 */
	@Test
	public final void testSize() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertEquals(0, test1.size());
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#toString()}.
	 */
	@Test
	public final void testToString() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertNotNull(test1.toString());
		assertFalse(test1.toString().isEmpty());
	}

	/**
	 * Test method for
	 * {@link com.github.ansell.jdefaultdict.ConcurrentMapDelegate#values()}.
	 */
	@Test
	public final void testValues() throws Exception {
		ConcurrentMap<String, String> test1 = new ConcurrentMapDelegate<>(
				() -> new ConcurrentHashMap<>());
		assertTrue(test1.values().isEmpty());
		test1.put("test", "original");
		assertFalse(test1.values().isEmpty());
		assertTrue(test1.values().contains("original"));
	}

}
