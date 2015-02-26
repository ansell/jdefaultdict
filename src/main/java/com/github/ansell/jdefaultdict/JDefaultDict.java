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
 * All calls to get are transparently mapped to computeIfAbsent, using the
 * lambda function provided in the constructor to create objects if they do not
 * exist.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		return computeIfAbsent((K) key, lambdaFunction);
	}
}
