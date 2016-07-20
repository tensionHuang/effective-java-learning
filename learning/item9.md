# Item9: Always override _hashCode_ when you overrid _equals_


3 contracts for overriding  _hashCode_  method:

1. 在沒有被修改的前提下，相同的物件的 hashCode method 一定要回傳相同的的整數值。（一致性） 
2. 兩個物件_equals_ 結果是相同時，他們兩個 hashCode 的回傳值也要是相同。
3. 若兩個物件 _equals_ 結果不相同時，他們的 hashCode 的回傳值沒有一定要不同。但是能夠保持不同可以改善 hash table 的效能。

#### 先說說為什麼要寫 _hashCode_ ?
#### why we need to override the hashCode?
如果沒有 override hashCode 以下 hash 相關的 collection 就無法正常運作。
hash-based collection: HashMap, HashSet, Hashtable

舉例：兩個 instances 邏輯上相等，但是放到 HashMap 要取出來的時候，回傳 null

HashMap 會做最佳化，每個 entry 他都會 cache 他的 hash code 。
HashMap 不會在特別去檢查 _object equality_ 比對兩個 instances 的 hash code 是否相同。

#### 在說說怎麼寫 _hashCode_ ?
第一種作法：直接回傳一個常數值（return a constant value.）
+ This ensures that every object has the same hash code.
+ so every object hashes to the same bucket and  hash tables degenerate(退化) to linked lists.

#### 什麼是一個好的 hash function?
產生不相等的物件要產生不同的 hash code（又叫做 hash value）

\# Review the third hash code provision( hash code 規定)
> Ideally,  a  hash  function  should  distribute  any  reasonable  collection  of
unequal instances uniformly across all possible hash values. 

##### 說明怎麼寫一個 hash function
分成四個步驟：
@Override public int hashCode() {
int result = 17;
result = 31 * result + areaCode;
result = 31 * result + prefix;
result = 31 * result + lineNumber;
return result;
}

1. 設定一個初始化變數 _result_ 唯一個 nonzero integer.
2. 對每一個 _significant field_ (就是 _equals_ 有用到的 field) 做處理：
	+ 計算他的整數 hash code _c_。（將 field 轉程 int，有 7 個規則）
	+ 和 result 加在一起。  result = 31 * result + _c_;
3. 回傳 result
4. 檢查 _equals_ 比較相等的 instances 他們有相同的 hash code



\# 為什麼初始值要為非零得整數？ 
初始值是 0 容易產生相同的 hash value。為了降低 hash collison 的機率，所以用 nonzero initial value.
\# 為什麼要用乘法？
會因為 field 的欄位順序而造成不同結果。以 String 為例，如果不用乘法，所有 anagrams 就會回傳相同的 hash value.
"cinema", "iceman"

\# 為什麼是選 31？因為她是值數
> A nice property of 31 is that the multiplication can be replaced by a shift and a subtraction for better performance:
31 * i == (i << 5) - i. Modern VMs do this sort of optimization automatically.


>uniformly distributed: 均勻分布

#### what is _redundant fields_? 這個 field 是從其他 filed 組合出來的
如果 _equals_ 沒用到的 fields 絕對要排除掉喔！ 
不然就違反了 second provision: 也就是 equals 相同的兩個物件，可能會有不同的 hashCode 



如果 class 是 immutable 然而計算 hashCode 的花費很重。
你可以考慮做 caching
那什麼時間點做 caching 呢？
可以做 eager 或是 lazy
eager: 如果你相信這個物件類別大部分的物件都會被當成 hash key 使用的話，在每個 instance create 的時候就計算 hashCode
lazy: 在第一次 hashCode 被使用的時候才計算。 
// Lazily initialized, cached hashCode
private volatile int hashCode; // (See Item 71)
@Override public int hashCode() {
int result = hashCode;
if (result == 0) {
result = 17;
result = 31 * result + areaCode;
result = 31 * result + prefix;
result = 31 * result + lineNumber;
hashCode = result;
}
return result;
}

> volatile: 強制一定要到主存記憶體取得變數值。
>state-of-the-art: 最先進的喔！

上述的作法產出了好的 hash function
但不是產出最先進的 hash fumction，而且 Java 在 1.6 也沒有提供這樣的 hash function.

這種 hash function 的研究還是交給專業的來！我們就不用自己管理 caching 這件事情吧！


> Do not be tempted to exclude significant parts of an object from the hash code computation to improve performance. 
千萬別為了改善 hashCode 的效能而犧牲了物件中重要的 field!!!

說明為什麼

While the resulting hash function may run faster, its poor quality may degrade(降級) hash tables' performance to the point
where they become unusably slow. （快速取得 hash value 的代價可能是 hash table 整體效能變很低）
 
If this happens, the hash function will map all the instances to a very few hash codes, and hash-based collections will display
quadratic performance. （慢了平方倍）



#### 問題：
* what is hash bucket? 
就是 hash table 儲存 key 的 index 的位置，就是 hash value!

* what is the different between _same hash bucket_ and _different hash bucket_?
不同的 key 對到 same bucket 表是有 collision
different hash bucket 就是不同的 hash value

* why talking about 'linked list' when talking about hash tables?
Hash Table 在 collision 就會用 linked list 處理
hash function 是說給你一個 key，會傳一個 hash value
 
* what is _redundant fields_? 
這個 field 是從其他 filed 組合出來的

* 思考這邊再討論 _equals_ 相不相等，為什麼需要 _hashCode_ 這件事情要弄清楚！
物件等於是 hash table 的 key, hashCode 是 hash value?
從 HashMap 取值，以為 equals 相同就一定會回傳想要的物件，但卻回傳 null，原因是 hash value 不正確。
因為 HashMap 需要比較物件是否相同是透過 hash code，這和資料結構有關，查詢比較快速 