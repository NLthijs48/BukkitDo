# BukkitDo
Simple scheduling library to make use of Java 8 lamdas to produce easy to read code.

## Information
* **Build server:** http://jenkins.wiefferink.me/job/BukkitDo/
* **Javadocs:** https://wiefferink.me/BukkitDo/javadocs

## Usage
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