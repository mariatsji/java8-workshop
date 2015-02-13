## Function-style

~~~java
 1  Optional<User> optional = Optional.ofNullable(aUser);
 2
 3  // User ===> Account
 4  Optional<Account> account = optional.map(new Function<User, Account>() {
 5                                     public Account apply(User user){
 6                                         // user promised non-null
 7                                         return user.getAccount();
 8                                     }
 9                                 });
~~~


## lambda-style

~~~java
 1  Optional<User> optional = Optional.ofNullable(aUser);
 2
 3  // User ===> Account
 4  Optional<Account> account = optional.map(user -> user.getAccount());
 5                                           // user promised non-null
~~~


## function-reference-style

~~~java
 1  Optional<User> optional = Optional.ofNullable(aUser);
 2
 3  // User ===> Account
 4  Optional<Account> account = optional.map(User::getAccount);
~~~
