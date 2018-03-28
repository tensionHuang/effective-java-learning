### Item69 Prefer concurrency utilities to wait and notify

* use of _wait_ and _notify_ is less important now

* there is far less reason to use wait and notify after Java 1.5 release.

* Java 1.5 provides higher level concurrency utilities that do the sorts of things you

### quote
* Given the difficulty of using wait and notify correctly, you should use the higher-level concurrency utilities instead.

### Concurrency Utilities
* 3 Categories
	+ Executor Framework
	+ Concurrent Collections
	+ Synchronizers

* it is impossible to exclude concurrent activity from a concurrent collections; locking it will have no effect but to slow the program.

* client can atomically compose method invocations on concurrent collections.

### questions
* what is __state-dependent-modify operations__ ? 
