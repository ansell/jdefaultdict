/**
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
 * An implementation of ConcurrentMap that pushes all operations to a delegate.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 * @param <K>
 *            The key type
 * @param <V>
 *            The value type
 */
public class ConcurrentMapDelegate<K, V> implements ConcurrentMap<K, V> {

	/**
	 * The delegate map.
	 */
	private volatile ConcurrentMap<K, V> delegate;

	private final Supplier<ConcurrentMap<K, V>> initialMapFunction;

	public ConcurrentMapDelegate(ConcurrentMap<K, V> delegate) {
		Objects.requireNonNull(delegate);
		this.initialMapFunction = null;
		this.delegate = delegate;
	}

	public ConcurrentMapDelegate(
			Supplier<ConcurrentMap<K, V>> initialMapFunction) {
		Objects.requireNonNull(initialMapFunction);
		this.initialMapFunction = initialMapFunction;
	}

	protected final ConcurrentMap<K, V> checkDelegate() {
		ConcurrentMap<K, V> result = delegate;
		if (result == null) {
			synchronized (this) {
				result = delegate;
				if (result == null) {
					result = delegate = initialMapFunction.get();
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		checkDelegate().clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Cannot clone the delegate map");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ConcurrentMap#compute(java.lang.Object,
	 * java.util.function.BiFunction)
	 */
	@Override
	public V compute(K key,
			BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return checkDelegate().compute(key, remappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ConcurrentMap#computeIfAbsent(java.lang.Object,
	 * java.util.function.Function)
	 */
	@Override
	public V computeIfAbsent(K key,
			Function<? super K, ? extends V> mappingFunction) {
		return checkDelegate().computeIfAbsent(key, mappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.util.concurrent.ConcurrentMap#computeIfPresent(java.lang.Object,
	 * java.util.function.BiFunction)
	 */
	@Override
	public V computeIfPresent(K key,
			BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return checkDelegate().computeIfPresent(key, remappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return checkDelegate().containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return checkDelegate().containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		return checkDelegate().entrySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return checkDelegate().equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.util.concurrent.ConcurrentMap#forEach(java.util.function.BiConsumer)
	 */
	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		checkDelegate().forEach(action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		return checkDelegate().get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ConcurrentMap#getOrDefault(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return checkDelegate().getOrDefault(key, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return checkDelegate().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return checkDelegate().isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		return checkDelegate().keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ConcurrentMap#merge(java.lang.Object,
	 * java.lang.Object, java.util.function.BiFunction)
	 */
	@Override
	public V merge(K key, V value,
			BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return checkDelegate().merge(key, value, remappingFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @see java.util.concurrent.ConcurrentMap#putIfAbsent(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public V putIfAbsent(K key, V value) {
		return checkDelegate().putIfAbsent(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		return checkDelegate().remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ConcurrentMap#remove(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public boolean remove(Object key, Object value) {
		return checkDelegate().remove(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ConcurrentMap#replace(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public V replace(K key, V value) {
		return checkDelegate().replace(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ConcurrentMap#replace(java.lang.Object,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return checkDelegate().replace(key, oldValue, newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.util.concurrent.ConcurrentMap#replaceAll(java.util.function.BiFunction
	 * )
	 */
	@Override
	public void replaceAll(
			BiFunction<? super K, ? super V, ? extends V> function) {
		checkDelegate().replaceAll(function);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return checkDelegate().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return checkDelegate().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		return checkDelegate().values();
	}

}
