jdefaultdict
============

A Java version of the Python defaultdict pattern

USAGE
=====

From Maven
----------

    <dependency>
        <groupId>com.github.ansell.jdefaultdict</groupId>
        <artifactId>jdefaultdict</artifactId>
        <version>0.1</version>
    </dependency>

Code example
------------

Create a multi-map using JDefaultDict

```java
ConcurrentMap<String, List<String>> defaultDict 
		= new JDefaultDict<>(k -> new ArrayList<>());
defaultDict.get("myKey1").add("myValue1");
defaultDict.get("myKey2").add("myValue2");
```

Wrap an existing map using JDefaultDict

```java
ConcurrentMap<String, List<String>> unwrapped = new ConcurrentMap<>();
ConcurrentMap<String, List<String>> defaultDict 
		= new JDefaultDict<>(k -> new ArrayList<>(), unwrapped);
defaultDict.get("myKey1").add("myValue1");
defaultDict.get("myKey2").add("myValue2");
```

Wrap multiple layers of JDefaultDict

```java
ConcurrentMap<String, ConcurrentMap<String, List<String>>> defaultDict 
		= new JDefaultDict<>(k1 -> new JDefaultDict(k2 -> new ArrayList<>()));
defaultDict.get("myKey1").get("yourKey1").add("myValue1");
defaultDict.get("myKey2").get("yourKey2").add("myValue2");
```

Lazily create the internal JDefaultDict as necessary, to avoid creating a map if it is never actually used
```java
ConcurrentMap<String, List<String>> defaultDict 
		= new JDefaultDict<>(k -> new ArrayList<>(), k -> new ConcurrentHashMap<>());
if(testValue) {
	// Only if the code reaches here will the internal default dict map be created
	defaultDict.get("test").add(testValue);
}
```
