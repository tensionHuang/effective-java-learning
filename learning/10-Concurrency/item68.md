### Item68 Prefer executors and tasks to thread

* you can wait for a particular task to complete.
* you can wait for any or all of a collection of tasks to complete.
    + using _invokeAll_ or _invokeAny_ methods
* you can wait for executor service's graceful termination to complete.
    + using _awaitTermination_
* you can retrieve the results of task one by one as they complete.
    + using _ExecutorCompletionService



#### Introduce Thread Pool Executors
* Use _ThreadPoolExecutor_ directly!

* Executors.newCachedThreadPool: for lightly loaded server not for heavily loaded production server!

* In a cached thread pool, submitted tasks are not queued but immediately handed off to a thread for execution. 

* If no threads are available, a new one is created.

* For heavily loaded server, please use _Executors.newFixedThreadPool_ , which gives you a pool with a fixed number of threads.


* you should refrain from writing your own work queues.
* you should refrain from working directly with threads.

* The key abstraction now is _the unit of work_: Tasks
* Task could be _Runnable_ or _Callable_

#### Introduce ScheduledThreadPoolExecutor (replacement of java.util.Timer)
