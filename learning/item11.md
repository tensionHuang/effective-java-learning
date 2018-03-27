### item11: Override clone judiciously

mixin interface?

Cloneable is a mixin interface

Object's clone method is protected.

You can not invoke the clone method on an object merely because it implements Cloneable without resorting to reflection.

這一個 item 教我們怎麼實作一個運作良好的 clone method.
討論什麼時候作最恰當，以及其他替代方案

It modifies the behavior of a protected method on a superclass.

為什麼 Cloneable interface 沒有任何需要實作的 method.
想想如果實作一個 Cloneable 會影響某個類別，那這個類別的所有父類別變成都要遵守這個
"複雜、不可執行的、文件資訊少的協定"，所以它不是強制性的
>The resulting mechanism is extralinguistic: it creates an object without calling a constructor.


在 java.lang.Object.clone 定義的 clone override 約定中，有些問題值得討論。
1. "No constructors are called" too strong?
2. x.clone().getClass() == x.getClass() too weak?
> if you override the clone method in a nonfinal class, you should return an object obtained by invoking super.clone.
因為一定要呼叫到 Object.clone 才行！
有點類似 automatic constructor chaining，旦clone不是強制性的。

一般來說，我們會覺得實作 Cloneable 應該要提供一個運作正常的 public clone method。
但不是這樣 


1.5 release 之後，支援 convariant return type(Genrics)
所以 override method 的 return type 可以變成 subtype
>In other words, it is now legal for an overriding method's return type to be a subclass of the overridden method's return type.

```
####  Never make the client do anything the library can do for the client.
```

* 若 clone 用在一個 immutable object 沒有問題！直接照規定使用即可
* 如果 clone 用在一個 mutable object，該如何處理呢？
    + 這邊用一個 Stack 為範例:


elements in Stack are mutable so that super.clone returns the same elements of original object!
so let's clone recursively...


[item53] Reflection

---
> 2016/08/08

> In effect, the _clone_ method functions as another constructor;
> you must ensure that it dose no harm to the original object and that it properly establishes invariants on the clone.

#### 2016/09/29
clone & final
> the clone architecture is incompatible with normal use of final fields referring to mutable objects.

if original class field is final, it cannot assign other value.
if you wanna make a class cloneable, you should remove the _final_ modifier.
it is not sufficient to call clone recursively.
talk about some good conventions to clone a complex object




#### 問題：

* 看 Cloneable interface 的內容

* what is automatic constructor chaining?

* what is covariant return type?    
邏輯上跟著改變（協變）
http://stackoverflow.com/questions/1882584/what-is-a-covariant-return-type

* 什麼是 reflection? 弄清楚    
就是我知道某個 interface 的 method，但我不知道這個物件有沒有這個 method
我就可以用 reflection 來試試看，有的話就呼叫，沒有的話就沒有。    
http://stackoverflow.com/questions/37628/what-is-reflection-and-why-is-it-useful
http://docs.oracle.com/javase/tutorial/reflect/index.html

Reflection 的使用

>Reflection is commonly used by programs which require the ability to examine or modify the runtime behavior of applications running in the Java virtual machine. 
>This is a relatively advanced feature and should be used only by developers who have a strong grasp of the fundamentals of the language. 
> With that caveat in mind, reflection is a powerful technique and can enable applications to perform operations which would otherwise be impossible.

* 怎麼寫一個 annotation?
Annotations can be read from source files, class files, or reflectively at run time.
http://www.mkyong.com/java/java-custom-annotations-example/
