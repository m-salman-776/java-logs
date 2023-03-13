# Design Patterns Implementation

###  The Observer Pattern
It defines a one-to-many dependency between objects so that when one object changes state, 
all of its dependents are notified and updated automatically.

### Decorator Pattern
The Decorator Pattern attaches additional responsibilities to an object dynamically.
Decorators provide a flexible alternative to subclassing for extending functionality.
It wraps the object for adding more behaviour. The decorator adds its own behavior either 
before and/or after delegating it to object they decorate.Decorators have the same supertype as the objects they decorate



### Factory Pattern
Factory Pattern encapsulate object creation by letting the subclasses decide what object to create. 
Factor Method let the class defer the instantiation to subclasses

### Abstract Factory Pattern
It provides an interface for creating families of related or
dependent objects without specifying their concrete classes.



***
### Dependency Inversion Principal (DI)
It says depend on abstractions. Do not depend on concrete classes.
High level component should not depend on low level components. They should depend on abstraction.

***Guidelines to follow DI***
1. No variable should hold reference to concrete class. If you use **new** you will be holding reference to concrete class. Use factory to get around that.
2. No class should derive from concrete class. Derive from abstraction like Interface or abstract class.
