# Item 67: Avoid excessive synchronization

+ To avoid _liveness_ and _safety failures_, never cede control to the client within a synchronization method or block.


[reading murmur]
- in synchronized region, invoking a method that is designed to be overridden.
- method provided by a client in the form of function object(Item21: static member of class?)
- observable case to make more concrete details.(example from Item16)
- what is lock's role in synchronization?

#### Terms
+ safety failures?
+ _alien_ method?
+ function object?
+ reentrant?