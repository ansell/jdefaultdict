/**
 * 
 */
package com.github.ansell.jdefaultdict;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of the Python defaultdict pattern using Java.
 * 
 * All calls to {@link #get(Object)} are transparently mapped to
 * {@link #computeIfAbsent(Object, Function)}, using the lambda function
 * provided in the {@link JDefaultDict} constructors to create default values if
 * the key does not exist.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 * @param <K>
 *            The key type
 * @param <V>
 *            The value type
 */
public class JDefaultDict<K, V> extends ConcurrentMapDelegate<K, V> {

	private final Function<? super K, ? extends V> lambdaFunction;

	public JDefaultDict(Function<? super K, ? extends V> lambdaFunction) {
		this(lambdaFunction, () -> new ConcurrentHashMap<K, V>());
	}

	public JDefaultDict(Function<? super K, ? extends V> lambdaFunction,
			ConcurrentMap<K, V> delegate) {
		super(delegate);
		this.lambdaFunction = lambdaFunction;
	}

	public JDefaultDict(Function<? super K, ? extends V> lambdaFunction,
			Supplier<ConcurrentMap<K, V>> initialMapFunction) {
		super(initialMapFunction);
		this.lambdaFunction = lambdaFunction;
	}

	/**
	 * Returns the value that exists for the given key if it exists, or creates
	 * a new entry using the lambda function that was given to the constructor.
	 * 
	 * @param key
	 *            The key object to search for, returning the value if it
	 *            exists, or inserts a new value using the lambda function that
	 *            was given to the constructor.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		return computeIfAbsent((K) key, lambdaFunction);
	}
}
