## Item 28: Use bounded wildcards to increase API flexibility
####\# Why bounded wildcard type ?

因為有這一個關於 List 的需求

item25有說明:
Integer is subtype of Number   
parametrized type is invariant   
但是 List<Integer> is not subtype of  List<Number>

```
Set<Number> set = new HashMap<Number>();
public void pushAll(Iterable<E> src){
	for(E e : src){
		push(e);
	}
}
```

+ 需求一：  
    - 在這情況下我輸入 Iterable<Integer> 這應該很合理，要能夠正常的 push 進去
    - 因為 Integer 是 Number 的 subtype   
    - 但是會有 compile error   
    - 可以用 subtype 的 type bounded 就可以讓 subtype 可以 work
        ```
        public void pushAll(Iterable<? extends E>)
        ```
+ 需求二：   
反過來，如果要將 generic type 儲存到 Object
```
public void popAll(Collection<E> dist)

public void popAll(Collection<? super E> dist)
```
NOTE:   
>The lesson is clear.
For maximum flexibility, use wildcard types on input parameters that represent producers or consumers. 

* producer-extends: who produce E instances, use extends
* consumer-super: who consum E instances, user super

>接下來用這個 principle 來套用前面 item 的範例   
另外！ return type 千萬不要用 bounded wildcard type!   
這會導致 client 在乎叫 API 的混淆！

1) reduce method
```
public static <E> reduce(List<? extends E>, Function<E> f, E initValue )
```
2) union method 搭配 explicit type paremter 可以解決
```
public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2)
```
```
Set<Integer> integers = ... ;
Set<Double> doubles = ... ;
Set<Number> numbers = union(integers, doubles);
會出現 compile error: incompatible type
改成 Union.<Number>union(integers, doubles)
```

3) max method   
recursive type parameter

分成四類:  
1. unbounded
2. bounded
3. non-wildcard
4. wildcard

>If a type parameter appears only once in a method declaration, replace it with a wildcard. 

```
public static void swap(List<?> list, int i, int j) {
swapHelper(list, i, j);
}
// Private helper method for wildcard capture
private static <E> void swapHelper(List<E> list, int i, int j) {
list.set(i, list.set(j, list.get(i)));
}
```




####\# 問題累積：
+ what is PECS? Produce-Extends, Consume-Super 的 principle
+ what is explicit type parameter
