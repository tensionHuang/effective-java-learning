##Item8: Obey the general contract when overriding equals
********
###最簡單就是不要覆寫！
也就是每個 instance 都只會跟自己相等
四種情況可以不要覆寫
1. Each instance of the class is inherently(本質上) unique.    
    每個 instance 都是唯一的物件    
    e.g. Thread class 表示 active entities 而不是 values

2. You don't care the class provides a *"logical equality"* test.    
    e.g. java.util.Random 可以覆寫 *equals*     
    但是它沒有這個需求，沒有人會比較兩個 Random number 是否相等。

3. A superclass has already overridden *equals*, and the superclass behavior si appropriate for this class.    
    superclass 覆寫的 *equals* 已經夠我用的啦！    
    就好像 Set 用 AbstractSet, List 用 AbstractList, Map 用 AbstractMap

4. The class is private or package-private, and you are certain that its *equals* method will never be invoked.    

####Value Class
#####\# 什麼是 Value Class?
* Has a notion of *logical equality* that differs from mere object identity. 
* Super class has not already overridden equals to implement desired behavior.
* 舉例：Integer, Date

>Said by me: compare value not object. same value but not same object!

+ 區分兩種比對方式：	
    * Object identity    
    * Logical equality

#####\# 不需要覆寫 equals 的 Value class!!!
Instance Control 每個值都只會有一個對應的物件(有兩種)
1. (Item1) Static factory method => each value exists at most one object.
2. (Item30) Enum types 也屬於這類型


#### 如果你要 override *equals* method，請遵守 general contract 如下
1. Reflexive: x.equals(x)
2. Symmetric: x.equals(y) and y.equals(x)
3. Transitive: x.equals(y) and y.equals(z) => x.equals(z)
4. Consistent: x.equals(y) = _true_ should always return _true_
5. Non-nullity: equals to _null_ return _false_

#### \#不符合 Symmetric 的範例  
+ ill-conceived attempt: 考慮不周的嘗試  
> Once you've violated the *equals* contract, 
you simply don't know how other objects will behave 
when confronted with your object.

#### \#不符合 Transitive 的範例  
常見情況:    
subclass 需要新增 _value component_ 到他的 superclass    
也就是說，繼承的子類別比父類別多了一個 property(field)/value
> There is no way to extend an instantiable class and add a value component while preserving the *equals
  contract*, unless you are willing to forgo the benefits of object-oriented abstraction.

你不可能繼承一個可產生 instance 的 class 然後新增一個屬性同時又要保留 *equals contract*    
透過 getClass 取代 instanceof?
> 原因是為了確保是相同的 implementation，只是為了排除 subclass with added value component.

分兩種情況：
  1. subclass extends superclass and add *value component*
  2. subclass extends superclass and does not add *value component*
  
好的 workaround 可以解決，參考 [item16]() "Favor composition over inheritance" 

>Now you can add a value component to a subclass od and _abstract class_ without violating the _equals_ contract.
[Item20] "Prefer class hierarchies to tagged classes."
abstract class Shape    
subclass Circle    
subclass Rectangle    
因為 abstract class 無法直接新增一個 instance!    

#### \#不符合 Consistency 的範例  
mutable objects can be equal to different objects at different times while immutable objects can't.

再寫一個 Class 的時候，要仔細思考他是不是應該要設計成immutable[Item5]

>Do not write an _equals_ method that depends on unreliable resources.

這是什麼意思呢？

_equal_ method should perform deterministic computations on memory-resident objects.


#### \# 高品質的 _equals_ method 撰寫食譜來囉
1. Use the _==_ operator to check if the argument is a reference to this object.
```
if (this == o) return true;
```
2. Use the instanceof operator to check if the argument has the correct type.


```
@Override 
public boolean equals(Object o) {
    // check type 
    if (!(o instanceof MyType)) return false;
    ...
}
```
如果 class 實作的 interface 是有定義 _equals_ 則使用 interface 作為 type 比對的對象。    
java 中的 Set, List, Map, Map.Entry 都是這類型。    
參考 AbstractSet 的 _equals_ 實作：
```
public boolean equals(Object o) {
    if (o == this)
        return true;
    if (!(o instanceof Set))
        return false;
    Collection<?> c = (Collection<?>) o;
    if (c.size() != size())
        return false;
    try {
        return containsAll(c);
    } catch (ClassCastException unused)   {
        return false;
    } catch (NullPointerException unused) {
        return false;
    }
}
```

3. Cast the argument to the correct type.
```
@Override 
public boolean equals(Object o) {
    if (!(o instanceof MyType)) return false;
    // cast type
    MyType mt = (MyType) o;
    ...
}
```
4. For each "significant" field in the class, check if that field of the argument matches the corresponding field of this object.
+ 檢查所有需要比對物件相等真正重要的 field，全部都相等才回傳 _true_，否則回傳 _false_
+ 至於 primitive field 可以用 _==_ 做比對。除了 float 和 double 以外，這兩個分別用 _Float.compare_ 和 _Double.compare_
+ Array 的話也一樣，如果 Array 是整個都要比對的話，可以使用 _Arrays.equals_
+ 排除 NullPointerException 的小技巧
    ```
    return (field == o.field || (field != null && field.equals(o.field)))
    ```
5. When you are finished writing your _equals_ method, ask yourself three questions: symmetric? transitive? consistent?

### \# 問題  
+ instantiable class 是什麼? 可以產生 instance 的 class (interface 不行！)
+ What is Liskov substitution principle?

equals, hashCode 

了解為什麼要 override equals?
為什麼 override equals 就也要 override hashCode
logical equality 必需要是相同 class
```
if (o == null || getClass() != o.getClass()) return false;
```
subclass 可以用 superclass 的 equals 嗎？
應該是看需求
每個 subclass 還是要寫自己的 equals
interface 的話怎麼比較 equals
equals 是 instance 之間的比較是否相等

目前在看 general contract



  
  