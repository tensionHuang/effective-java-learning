java.lang.Object 的 _toString_ method

@+ hexadecimal

_toString_ contract:

1) a concise but informative representation that is easy for a person to read.
2) It id recommended that all subclasses override this method.

不像 _equals_ 和 _hashCode_ 有那種一定要覆寫的必要性 。

討論 specify format 的優缺點
因為 _toString_ 的使用者可能將結果拿來做 parsing

如果 _toString_ 的內容有用到的資訊，都要能夠有 accessor 可以取得。
不然使用者又要 parse 你的 _toString_ 了

#### 重點回顧：

Providing a good _toString_ implementation makes your class mush more pleasant to use.

When pratical, the _toString_ method should return all of the interesting information contained in the object.

Whether or not you decide to specify the format, you should clearly document your intensions.

Provide programmatic access to all of the information contained in the value returned by _toString_.

#### 問題：
# 學習 printf or String.format 的規則阿！ （review the rules）