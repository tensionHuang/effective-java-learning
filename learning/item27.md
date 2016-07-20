## Item 27: Favor generic methods

###章節重點

+ 有 Generification 的需求！  
+ compile time 可以檢查，可以保重 type safe!  
+ 當 type 是可以變動的時候！  
+ Class 有  
+ Method 也有
+ 關於 type parameter naming convention!

Generic Method
------------------------------------------------
1. 先說說 generic method 的定義是什麼?  
    + type parameter: <E> 放在 method modifier 和 return value 的中間！  
    e.g. public static <E> E mergeElement(E e1, E e2);  
    + 關於 type inference  
    compiler 會知道 type parameter 的真實 type 為何，又稱之為 type inference

2. 再來說說 generic static factory method  
+ 目的是為了簡化下列這種繁瑣的宣告   
```
// 1.6
Map<String,List<String>> map = new HashMap<String,List<String>>();
// 1.8
Map<String, List<String>> map =  new HashMap<>();
```
回顧 static factory method: 一個 new object 的作法，會有 method name   
\# java 8 把 generic static factory method 實作在 new 裡面了？
>"It would be nice if the language did the same kind of type inference when    
invoking constructors on generic types as it does when invoking generic methods.  
Someday it might, but as of release 1.6, it does not."  
> __1.8 做了這件事情XDD__

3. 說著就想到相關的 generic singleton factory
+ 重點：   
>create  an  object  that  is  immutable  but  applicable  to  many  different  types
This pattern is most frequently used for function objects!  
這邊用 identity function 做範例！(可以再看一遍)  
回傳 Object 在 cast 成 type parameter
```
// Generic singleton factory pattern
private static UnaryFunction<Object> IDENTITY_FUNCTION =
new UnaryFunction<Object>() {
public Object apply(Object arg) { return arg; }
};
```
```
// IDENTITY_FUNCTION is stateless and its type parameter is
// unbounded so it's safe to share one instance across all types.
@SuppressWarnings("unchecked")
public static <T> UnaryFunction<T> identityFunction() {
return (UnaryFunction<T>) IDENTITY_FUNCTION;
}
```
#### \# 經典代表（item21）: Collection.reverseOrder
+ 當 type parameter 被自己所限制（bounded）: Recursive Type Bound  
最常見的就是用在 Comparable interface  
```
public static <T extends Comparable<T>> T findMax(List<T> list)
```

#### \# 在弄清楚
+ generics are implemented by erasure(item25) 該怎麼跟別人介紹
+ Immutable
+ Hash and Equal(item8)