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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of the Python defaultdict pattern using Java. All calls to {@link #get(Object)} are
 * transparently mapped to {@link #computeIfAbsent(Object, Function)}, using the lambda function provided in
 * the {@link JDefaultDict} constructors to create default values if the key does not exist.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * @param <K>
 *        The key type
 * @param <V>
 *        The value type
 */
public class JDefaultDict<K, V> extends ConcurrentMapDelegate<K, V> {

	private final Function<? super K, ? extends V> lambdaFunction;

	/**
	 * Create a {@link ConcurrentMap} that will use the given lambda function to create new values when
	 * {@link ConcurrentMap#get(Object)} is called and the key does not currently exist in the map.
	 * 
	 * @param lambdaFunction
	 *        The {@link Function} to use to create new values to insert into the map when
	 *        {@link #get(Object)} is called and the key does not currently exist in the map.
	 */
	public JDefaultDict(Function<? super K, ? extends V> lambdaFunction) {
		this(lambdaFunction, () -> new ConcurrentHashMap<K, V>());
	}

	/**
	 * Use the given {@link ConcurrentMap} as the backing map for this {@link JDefaultDict} and overlay it to
	 * use the given lambda function to create new values when {@link ConcurrentMap#get(Object)} is called and
	 * the key does not currently exist in the map.
	 * 
	 * @param lambdaFunction
	 *        The {@link Function} to use to create new values to insert into the map when
	 *        {@link #get(Object)} is called and the key does not currently exist in the map.
	 */
	public JDefaultDict(Function<? super K, ? extends V> lambdaFunction, ConcurrentMap<K, V> delegate) {
		super(delegate);
		this.lambdaFunction = lambdaFunction;
	}

	/**
	 * Use the given {@link Supplier} to lazily create a new {@link ConcurrentMap} if/when accesses occur. The
	 * map that would be created by the supplier is used as the backing map for this {@link JDefaultDict}, and
	 * will be overlayed to use the given lambda function to create new values when
	 * {@link ConcurrentMap#get(Object)} is called and the key does not currently exist in the map.
	 * 
	 * @param lambdaFunction
	 *        The {@link Function} to use to create new values to insert into the map when
	 *        {@link #get(Object)} is called and the key does not currently exist in the map.
	 */
	public JDefaultDict(Function<? super K, ? extends V> lambdaFunction,
			Supplier<ConcurrentMap<K, V>> initialMapFunction)
	{
		super(initialMapFunction);
		this.lambdaFunction = lambdaFunction;
	}

	/**
	 * Returns the value that exists for the given key if it exists, or creates a new entry using the lambda
	 * function that was given to the constructor. <br>
	 * In practice, it redirects the call to {@link #get(Object)} on to
	 * {@link #computeIfAbsent(Object, Function)}, inserting the lambda function specified in the constructor
	 * for this object in as the second parameter for computeIfAbsent.
	 * 
	 * @param key
	 *        The key object to search for, returning the value if it exists, or inserts a new value using the
	 *        lambda function that was given to the constructor.
	 * @return The object that existed in the map, or a new object that is created using the {@link Function}
	 *         that was passed to the constructor for this object and added to the map with the given key
	 *         before returning.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		return computeIfAbsent((K)key, lambdaFunction);
	}
}
