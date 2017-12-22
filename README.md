# BukkitDo
Simple scheduling library to make use of Java 8 lamdas to produce easy to read code.

## Information
* **Build server:** http://jenkins.wiefferink.me/job/BukkitDo/
* **Javadocs:** https://wiefferink.me/BukkitDo/javadocs

## Examples
Below are some examples for the methods available in this library. Parameters of the `Run` or `RunResult<>` type are interfaces and therefore a lambda or a method reference like `this::someMethod` can be used. Often a calling a single method is enough, leading to really clean code.

#### Synchronous task for the next tick
```java
Do.sync(this::someMethod);

Do.sync(() -> someMethod(player));

Do.sync(() -> {
    boolean result = doSomething();
    if(result) {
        doSomethingElse();
    }
});
```

#### Synchronous task for later
```java
Do.syncLater(20, this::someMethod);

Do.syncLater(20, () -> someMethod(player));

Do.syncLater(20, () -> {
    boolean result = doSomething();
    if(result) {
        doSomethingElse();
    }
});
```

#### Synchronous timer
Call a method synchronously every second (20 ticks):
```java
Do.syncTimer(20, this::someMethod)
```

If you want to cancel the task at some point, either save the resulting `BukkitTask` or return false from your method.
```java
BukkitTask task = Do.syncTimer(20, this::someMethod);
task.cancel();
```

```java
Do.syncTimer(20, () -> {
    doSomething();
    if(someCondition) {
        return false; // Stop the task
    }
    return true; // Keep running the task
});
```

#### Asynchronous versions
For the methods shown above async versions are also available, simply use `async()` and `asyncLater()`.

#### Do a heavy operation over multiple ticks
To perform a heavy operation without slowing down the server it is a good idea to spread it over multiple ticks. The idea is to handle X items each tick, which the `Do.forall()` method can do for you.

```java
Do.forall(
    // Do 10 items per tick (optional, defaults to 1)
    10,
    // Collection of objects to do something for
    Bukkit.getOnlinePlayers(),
    // This method will be called once for each item from your collection, in this case with a player
    player -> {
        calculateScore(player);
        player.sendMessage("done");
    }
    // When everything is complete, log a message (optional)
    () -> plugin.getLogger().info("completed");
);
```

## Use with Maven
1. Add Maven repository:

    ```xml
    <repositories>
      <repository>
        <id>nlthijs48</id>
        <url>http://maven.wiefferink.me</url>
      </repository>
    </repositories>
    ```
1. Add Maven dependency:

    ```xml
    <dependencies>
      <dependency>
        <groupId>me.wiefferink</groupId>
        <artifactId>bukkitdo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
      </dependency>
    </dependencies>
    ```
1. Relocate the library (compatibility with other plugins):

    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>2.4.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <relocations>
                                <!-- Relocate BukkitDo -->
                                <relocation>
                                    <pattern>me.wiefferink.bukkitdo</pattern>
                                    <shadedPattern>your.package.here.shaded.bukkitdo</shadedPattern>
                                </relocation>
                            </relocations>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ```    
