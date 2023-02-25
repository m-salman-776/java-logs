# Design Patterns Implementation


### Decorator Pattern

### Factory Pattern
Factory Pattern encapsulate object creation by letting the subclasses decide what object to create. 
Factor Method let the class defer the instantiation to subclasses



***
### Dependency Inversion Principal (DI)
It says depend on abstractions. Do not depend on concrete classes.
High level component should not depend on low level components. They should depend on abstraction.

***Guidelines to follow DI***
1. No variable should hold reference to concrete class. If you use **new** you will be holding reference to concrete class. Use factory to get around that.
2. No class should derive from concrete class. Derive from abstraction like Interface or abstract class.
