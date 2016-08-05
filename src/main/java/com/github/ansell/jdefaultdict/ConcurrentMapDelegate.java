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

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of ConcurrentMap that pushes all operations to a delegate. It can also be used to lazily
 * create a backing map if/when accesses occur, based on a {@link Supplier} function sent to one of its
 * constructors.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * @param <K>
 *        The key type
 * @param <V>
 *        The value type
 */
public class ConcurrentMapDelegate<K, V> implements ConcurrentMap<K, V> {

	/**
	 * The delegate map. This should only be accessed using {@link #checkDelegate()} to ensure the volatile
	 * double-checked pattern is adhered to.
	 */
	private volatile ConcurrentMap<K, V> delegate;

	/**
	 * If the delegate map is specified when this class is instantiated, this must be null, otherwise it must
	 * a lambda that lazily supplies an instance of ConcurrentMap to use as the backing map for this instance
	 * when accesses to the map occur. If there are no accesses to the map, this function may never be called.
	 */
	private final Supplier<ConcurrentMap<K, V>> initialMapFunction;

	/**
	 * Creates a delegate map that directly uses the given map to delegate operations to.
	 * 
	 * @param delegate
	 *        The instance of {@link ConcurrentMap} to delegate all operations to.
	 */
	public ConcurrentMapDelegate(ConcurrentMap<K, V> delegate) {
		Objects.requireNonNull(delegate);
		this.initialMapFunction = null;
		this.delegate = delegate;
	}

	/**
	 * Specify a {@link Supplier} to use to lazily a delegate map that directly uses the given map to delegate
	 * operations to when they occur.
	 * 
	 * @param initialMapFunction
	 *        A {@link Supplier} that will be used to lazily create the delegate {@link ConcurrentMap} for
	 *        this object.
	 */
	public ConcurrentMapDelegate(Supplier<ConcurrentMap<K, V>> initialMapFunction) {
		Objects.requireNonNull(initialMapFunction);
		this.initialMapFunction = initialMapFunction;
	}

	/**
	 * Get the delegate, or create it if it wasn't created already.
	 * 
	 * @return A {@link ConcurrentMap} to delegate operations to, lazily created if necessary in a thread-safe
	 *         manner.
	 */
	protected final ConcurrentMap<K, V> checkDelegate() {
		ConcurrentMap<K, V> result = delegate;
		if (result == null) {
			synchronized (this) {
				result = delegate;
				if (result == null) {
					delegate = result = initialMapFunction.get();
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		checkDelegate().clear();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone()
		throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException("Cannot clone the delegate map");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#compute(java.lang.Object, java.util.function.BiFunction)
	 */
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return checkDelegate().compute(key, remappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#computeIfAbsent(java.lang.Object, java.util.function.Function)
	 */
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return checkDelegate().computeIfAbsent(key, mappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#computeIfPresent(java.lang.Object,
	 * java.util.function.BiFunction)
	 */
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return checkDelegate().computeIfPresent(key, remappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return checkDelegate().containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return checkDelegate().containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		return checkDelegate().entrySet();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return checkDelegate().equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#forEach(java.util.function.BiConsumer)
	 */
	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		checkDelegate().forEach(action);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		return checkDelegate().get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#getOrDefault(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return checkDelegate().getOrDefault(key, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return checkDelegate().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return checkDelegate().isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		return checkDelegate().keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#merge(java.lang.Object, java.lang.Object,
	 * java.util.function.BiFunction)
	 */
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return checkDelegate().merge(key, value, remappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		return checkDelegate().put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		checkDelegate().putAll(m);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V putIfAbsent(K key, V value) {
		return checkDelegate().putIfAbsent(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		return checkDelegate().remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#remove(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean remove(Object key, Object value) {
		return checkDelegate().remove(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#replace(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V replace(K key, V value) {
		return checkDelegate().replace(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return checkDelegate().replace(key, oldValue, newValue);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ConcurrentMap#replaceAll(java.util.function.BiFunction )
	 */
	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		checkDelegate().replaceAll(function);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return checkDelegate().size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return checkDelegate().toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		return checkDelegate().values();
	}

}
