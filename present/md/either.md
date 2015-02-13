## Either

~~~java
Either<LEFT, RIGHT> aLeftOrRight;
~~~

- - -

**Disjoint union** (left and right) of values of **two types**

**Guarantee** : left **type** when left **side**, right type when right side.

Forces **handling** of **both** possibilities (at the same time)

*Not part of Java 8 but available through FINN's lambda-companion library (copy of the Scala interface + some extras)*


## Disjoint union

~~~java
 1  Either<PhoneNumber, EMailAddress> contact;
 2
 3  String sendingNumber = contact.fold(phone -> sendSms(phone, message),
 4                                      email -> sendEmail(email, message));
~~~

- - -

Both sides require specific handling from developer


## Failure handling

~~~java
 1  Either<Failure, Success> aFailureOrSuccess;
 2
 3  Value value = aFailureOrSuccess.fold(failure -> defaultValue,
 4                                       success -> success.getValue());
~~~

- - -

Forces handling of both success and failure (at the same time)

Left side for failure and Right side for success

Failure becomes a normal return value; does not break the control flow like try-catch


## Fold

~~~java
 1  Either<L, R> aLeftOrRight;
 2
 3  // L or R =====fold===> X
 4
 5  X x = aLeftOrRight.fold(left -> leftToX(left),     // L ===> X
 6                          right -> rightToX(right)); // R ===> X
~~~

- - -

Make a single value out of each of both possibilities


## Projection

~~~java
 1  Either<L, R> aLeftOrRight;
 2
 3  LeftProjection<L, R> l = aLeftOrRight.left().... // work only on left
 4
 5  RightProjection<L, R> r = aLeftOrRight.right().... // work only on right
~~~

- - -

 ```*Projection<L, R>``` is still the **same** ```Either<L, R>``` **with both** left and right but guarantees working on the **chosen side**


## Projection operations - 1

~~~java
 1  Either<L, R> either;
 2
 3
 4  // transform left value into somthing else; ex: L ===> X
 5  Either<X, R> mapped = either.left().map(left -> toX(left));
 6
 7  // return a default value
 8  L value = either.left().orElse(defaultValue);
 9
10  // supply a default value
11  L value = either.left().orElseGet(() -> supplier());
12
13  // make an Optional of it
14  Optional<L> opt = either.left().toOptional();
~~~


## Projection operations - 2

~~~java
 1  Either<L, R> either;
 2
 3
 4  // executes the side effect; returns the either
 5  Either<L, R> same = either.left().peek(left -> log(left));
 6
 7  // executes the side effect; returns void
 8  either.left().forEach(left -> log(left));
 9
10  // filter based on the value of the left side
11  Optional<Either<L, R>> maybe = either.left()
12                                       .filter(left -> predicate(left));
~~~


## Combine Either

~~~java
 1  // id ===> User
 2  Either<NotFoundException, User> getUser(long id) { ... }
 3
 4  // User ===> Account
 5  Either<NotFoundException, Account> getAccount(User user) { ... }
 6
 7
 8  // nested...! complicated
 9  Either<NotFoundException, Either<NotFoundException, Account>> wtf =
10      getUser(42L).right().map(user -> getAccount(user));
11
12
13  // a kind of flat-map on the right side
14  Either<NotFoundException, Account> account =
15      getUser(42L).joinRight(user -> getAccount(user));
~~~


## Logging failures

~~~java
 1  Either<NotFoundException, User> getUser(long id) { ... }
 2  Either<NotFoundException, Account> getAccount(User user) { ... }
 3
 4
 5  // a plain-text jersey-based HTTP service that shows the user's balance
 6  return
 7      // try to find the user
 8      getUser(42L)
 9      // if a right, i.e. we found the user, now get the account
10      .joinRight(user -> getAccount(user))
11      // if a left, i.e. a failure : log error
12      .left().peek(exception -> LOG.warn("It failed!", exception))
13      // in both cases, turn the result into a HTTP response
14      .fold(exception -> Response.notFound("Could not find info").build(),
15            account   -> Response.ok(account.getBalance + "$").build());
~~~


## commons-rest/http-client

~~~java
Result<String, Ad> res = http.handleRequest(request, converter);

if(res.isSuccess() && res.asSuccess().getValue().isPresent()) {
    doSomething(res.asSuccess().getValue().get());
} else {
    handleError(res.asFailure());
}
~~~

- - -

~~~java
Either<Failure<String>, Success<Ad>> res = http.handleRequest(request, c);

res.fold(failure -> handleError(failure),
         success -> doSomething(success));
~~~
