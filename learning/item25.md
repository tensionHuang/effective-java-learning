## Item25: Prefer lists to arrays
****************
###\# 第一個差異
Arrays are covariant and reifiable
Sub is a subtype of Super => Sub[] is a subtype of Super[]

Generics are invariant and non-reifiable
Type1 和 Type2 是不同類型
List<Type1> 和 List<Type2> 兩者不會有任何繼承關係

```
// Fails at runtime!
Object[] objectArray = new Long[1];
objectArray[0] = "I don't fit in"; // Throws ArrayStoreException
```
```
// Won't compile!
List<Object> ol = new ArrayList<Long>(); // Incompatible types
ol.add("I don't fit in");
```

###\# 第二個差異
Arrays are reifiable
Arrays 在 runtime 會知道 elements 的 type，所以才會在 runtime 出現 ArrayStoreException

Generics are non-reifiable.    
Generic is implemented by erasure!    
Enforce their type constraints at compile time.    
Erase their element type information at runtime.    

所以沒有 Generic Array Creation 這種東西！！！

###\# 什麼是 erasure ?
compile time 會做 type 檢查，但是 runtime 就把 type 都丟掉。    
為了達成向前相容，也就是和舊的程式碼（沒有使用Generic的程式碼）能夠相容。

###\# 什麼是 non-reifiable type ?
A non-reifiable type is one whose runtime representation contains less information than its compile time representation.

###\# 什麼是 reifiable types?
Types that are completely available at run time are known as reifiable types.

Generic 和 Array 很難混和使用

###\# 什麼是 varargs method? 請參考 Item 42
當 varargs 和 generic 一起使用的時候，改怎麼處理呢？    
因為 varargs parameters 會用一個 Array 來儲存他    
It also means that you can get confusing warnings when using varargs methods (Item 42) in combination with generic types.    
 
###\# 解釋 the collection type List<E> in preference to the array type E[]    
實作 reduce method 使用 synchronized （同步）   
什麼時候要用到 synchronized ? 一次只能一個人使用？有 lock 機制？    
>要看 Item 67 - 什麼是 alien method?    
 
沒有辦法作 generic array creation 但允許 cast array to generic 
Casts to arrays of non-reifiable types should be used only under special circumstances (Item 26).  
例如：E[] snapshot = (E[]) list.toArray()     
 
 
 


