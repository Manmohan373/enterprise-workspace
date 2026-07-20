# Spring Boot Startup

## Overview

When a Spring Boot application starts, it goes through several internal steps before it is ready to accept HTTP requests. Although we only write a single line of code:

```java
SpringApplication.run(EwmsApplication.class, args);
```

this method triggers the entire Spring Boot startup process, including creating the Spring container, initializing beans, performing dependency injection, and starting the embedded web server.

---

## Startup Flow

```
User Runs Application
        │
        ▼
JVM Starts
        │
        ▼
ClassLoader Loads Classes
        │
        ▼
main() Method Executes
        │
        ▼
SpringApplication.run()
        │
        ▼
Create SpringApplication Instance
        │
        ▼
Determine Application Type
        │
        ▼
Load Environment & Configuration
        │
        ▼
Create ApplicationContext
        │
        ▼
Component Scanning
        │
        ▼
Create Spring Beans
        │
        ▼
Dependency Injection
        │
        ▼
Start Embedded Tomcat
        │
        ▼
Register DispatcherServlet
        │
        ▼
Application Ready
```

---

## Step 1: JVM Starts

The operating system first launches the Java Virtual Machine (JVM) when the application is executed.

Example:

```bash
java -jar ewms.jar
```

Before Spring Boot is involved, the JVM:

- Creates memory areas (Heap, Stack, Metaspace, etc.)
- Initializes the runtime environment
- Loads required classes through the ClassLoader

Spring Boot cannot start until the JVM is fully initialized.

---

## Step 2: Execute the `main()` Method

The JVM searches for the application's entry point:

```java
public static void main(String[] args)
```

Execution always begins here because this is defined by the Java language specification, not by Spring.

Example:

```java
@SpringBootApplication
public class EwmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwmsApplication.class, args);
    }

}
```

---

## Step 3: `SpringApplication.run()`

The `run()` method is responsible for bootstrapping the Spring Boot application.

Internally, it behaves similarly to:

```java
SpringApplication application =
    new SpringApplication(EwmsApplication.class);

application.run(args);
```

The `SpringApplication` object stores startup information and coordinates the initialization process.

---

## Step 4: Create `SpringApplication`

During initialization, Spring Boot creates a `SpringApplication` instance that holds important configuration such as:

- Main application class
- Banner configuration
- Environment settings
- Application listeners
- Initializers
- Startup options

This object manages the remainder of the startup process.

---

## Step 5: Determine the Application Type

Spring Boot determines which type of application is being started.

Possible types include:

| Application Type | Embedded Server |
|------------------|-----------------|
| Servlet (Spring MVC) | Tomcat |
| Reactive (WebFlux) | Netty |
| Non-Web Application | None |

If the project contains Spring MVC dependencies, Spring Boot automatically configures it as a Servlet-based web application.

---

## Step 6: Load Configuration

Spring Boot loads external configuration files such as:

- `application.properties`
- `application.yml`

By default, these files are searched under:

```
src/main/resources
```

Configuration values commonly include:

- Server port
- Database connection
- Logging configuration
- Active profiles
- Custom application properties

### Convention over Configuration

Spring Boot follows the principle of **Convention over Configuration**.

Instead of requiring developers to configure every setting manually, Spring Boot uses sensible default locations and behaviors while still allowing customization when needed.

---

## Step 7: Create the ApplicationContext

The `ApplicationContext` is the core container of the Spring Framework.

It is responsible for managing the complete lifecycle of the application.

It stores:

- Beans
- Controllers
- Services
- Repositories
- Configuration classes
- DataSource
- TransactionManager
- ObjectMapper
- DispatcherServlet
- Other infrastructure components

Whenever Spring requires an object, it retrieves it from the `ApplicationContext`.

---

## Step 8: Component Scanning

Spring scans the project's packages for classes annotated with stereotypes such as:

```java
@Component
@Service
@Repository
@Controller
@RestController
@Configuration
```

These classes become candidates for Spring-managed beans.

Scanning starts from the package containing the class annotated with `@SpringBootApplication` and recursively scans its sub-packages.

---

## Step 9: Bean Creation

For every discovered component, Spring creates an object and stores it inside the `ApplicationContext`.

Example:

```java
@Service
public class UserService {

}
```

Spring internally creates:

```java
new UserService();
```

Developers typically do not instantiate such classes manually because Spring manages their lifecycle.

---

## Step 10: Dependency Injection

After bean creation, Spring resolves dependencies between beans.

Example:

```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

}
```

Startup process:

1. Spring creates `UserRepository`
2. Spring creates `UserService`
3. Spring injects the repository into the constructor

This approach promotes loose coupling and easier testing.

---

## Step 11: Start Embedded Tomcat

Only after the entire `ApplicationContext` has been initialized does Spring Boot start the embedded servlet container.

For Spring MVC applications, the default embedded server is Tomcat.

At this stage:

- Port (default: 8080) is opened
- HTTP listener starts
- Requests can now be accepted

Tomcat starts **after** the Spring container is ready, ensuring the application is fully initialized before serving requests.

---

## Step 12: Register DispatcherServlet

Spring Boot registers the `DispatcherServlet`, which acts as the Front Controller in Spring MVC.

Every incoming HTTP request follows this path:

```
Client
   │
   ▼
DispatcherServlet
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

The `DispatcherServlet` is responsible for routing requests to the appropriate controller methods.

---

## Step 13: Application Ready

After all initialization is complete, Spring Boot logs a message similar to:

```
Started EwmsApplication in 4.2 seconds
```

The application is now fully initialized and ready to process incoming requests.

---

# Key Concepts

### JVM

The Java Virtual Machine provides the runtime environment for executing Java applications. Spring Boot runs on top of the JVM.

### SpringApplication

Coordinates the complete startup process of a Spring Boot application.

### ApplicationContext

The central Spring container responsible for creating, managing, and providing access to all Spring-managed beans.

### Bean

A Java object whose lifecycle is managed by the Spring container.

### Component Scanning

The process of discovering annotated classes and registering them as beans automatically.

### Dependency Injection

A design pattern where Spring provides required dependencies instead of objects creating them manually.

### Embedded Tomcat

A built-in web server that allows Spring Boot applications to run without deploying a WAR file to an external server.

### DispatcherServlet

The front controller responsible for receiving every HTTP request and dispatching it to the appropriate controller.

### Convention over Configuration

Spring Boot uses predefined conventions and default configurations, reducing the amount of manual configuration required by developers.

---

# Important Interview Questions

1. What happens internally when `SpringApplication.run()` is executed?
2. What is the responsibility of the JVM before Spring Boot starts?
3. What is the purpose of the `SpringApplication` class?
4. What is the `ApplicationContext`, and why is it important?
5. What is the difference between `BeanFactory` and `ApplicationContext`?
6. How does component scanning work?
7. What is a Spring Bean?
8. What is Dependency Injection, and what are its benefits?
9. When does the embedded Tomcat server actually start?
10. What is the role of the `DispatcherServlet` in Spring MVC?
11. What does "Convention over Configuration" mean in Spring Boot?

---


# Interview Questions & Answers (3–4 Years Spring Boot Developer)

These are concise but interview-ready answers expected from a Software Development Engineer (SDE) with 3–4 years of Spring Boot experience.

---

## 1. What is the first thing that starts when you run a Spring Boot application?

The first thing that starts is the **Java Virtual Machine (JVM)**, not Spring Boot.

When we execute:

```bash
java -jar application.jar
```

the operating system launches the JVM. The JVM initializes its runtime environment by creating memory areas such as Heap, Stack, Metaspace, and Code Cache. It then loads the application's classes using the ClassLoader and locates the `main()` method.

Only after the JVM is fully initialized does Spring Boot begin its startup process.

**Interview Tip:**
> Spring Boot does not start directly. The JVM starts first, loads the application classes, and invokes the `main()` method, which then calls `SpringApplication.run()`.

---

## 2. What is the role of the JVM before Spring starts?

Before Spring Boot is initialized, the JVM is responsible for:

- Loading application classes using the ClassLoader
- Creating runtime memory areas
- Initializing the Java runtime environment
- Executing the `main()` method
- Managing object allocation and garbage collection

Spring Boot is simply a Java application, so it depends entirely on the JVM to execute.

---

## 3. What does `SpringApplication.run()` actually do?

`SpringApplication.run()` bootstraps the entire Spring Boot application.

Internally, it performs several operations:

- Creates a `SpringApplication` instance
- Determines the application type (Servlet, Reactive, or Non-Web)
- Loads application configuration
- Creates the `ApplicationContext`
- Performs component scanning
- Creates and initializes beans
- Resolves dependency injection
- Executes lifecycle callbacks
- Starts the embedded web server (Tomcat/Jetty/Undertow)
- Publishes startup events
- Marks the application as ready

Although we write a single line of code, thousands of framework classes are executed internally.

**Interview Tip:**
> `SpringApplication.run()` is the bootstrap method responsible for initializing the Spring container and starting the application.

---

## 4. What is the ApplicationContext?

`ApplicationContext` is the core IoC (Inversion of Control) container in Spring.

Its responsibilities include:

- Creating beans
- Managing bean lifecycle
- Performing dependency injection
- Loading configuration
- Publishing application events
- Managing resources
- Resolving properties
- Supporting internationalization
- Integrating AOP, transactions, and validation

Every Spring-managed object exists inside the `ApplicationContext`.

**Real-world Example**

When we annotate a class with:

```java
@Service
public class UserService {

}
```

Spring creates the object once and stores it inside the `ApplicationContext`.

Whenever another bean requires `UserService`, Spring retrieves the existing instance instead of creating a new one.

---

## 5. What is the difference between BeanFactory and ApplicationContext?

| BeanFactory | ApplicationContext |
|-------------|-------------------|
| Basic IoC container | Advanced IoC container |
| Lazy initialization by default | Eager singleton initialization by default |
| Limited enterprise features | Supports AOP, Events, Validation, Transactions, Internationalization, Property Sources, Resource Loading |
| Rarely used directly | Used by almost every Spring Boot application |

**Interview Answer**

`ApplicationContext` extends `BeanFactory` and adds enterprise-level features. In Spring Boot, we generally work with `ApplicationContext` because it provides dependency injection along with additional framework capabilities.

---

## 6. What is Component Scanning?

Component scanning is the process where Spring automatically searches for classes annotated with:

```java
@Component
@Service
@Repository
@Controller
@RestController
@Configuration
```

These classes are automatically registered as Spring Beans.

Scanning begins from the package containing the class annotated with:

```java
@SpringBootApplication
```

and recursively scans all sub-packages.

Without component scanning, every bean would have to be configured manually.

---

## 7. Why do we use Dependency Injection?

Dependency Injection (DI) allows Spring to provide an object's dependencies rather than the object creating them itself.

Instead of:

```java
UserRepository repository = new UserRepository();
```

we write:

```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

}
```

Spring injects the dependency automatically.

### Benefits

- Loose coupling
- Easier testing
- Better maintainability
- Improved readability
- Easier replacement of implementations
- Centralized object lifecycle management

Constructor injection is generally preferred because it makes dependencies explicit and supports immutable fields.

---

## 8. When does Tomcat actually start?

A common misconception is that Tomcat starts immediately.

The actual order is:

```
JVM
↓

SpringApplication

↓

ApplicationContext

↓

Beans Created

↓

Dependency Injection

↓

Embedded Tomcat Starts

↓

DispatcherServlet Registered
```

Tomcat starts only after the Spring container has been fully initialized.

This ensures that all controllers, services, repositories, and other beans are ready before handling incoming requests.

---

## 9. What is the purpose of the DispatcherServlet?

`DispatcherServlet` is the Front Controller of Spring MVC.

Every HTTP request first reaches the `DispatcherServlet`.

Its responsibilities include:

- Receiving HTTP requests
- Identifying the appropriate controller
- Invoking the controller method
- Handling exceptions
- Resolving views (for MVC applications)
- Returning HTTP responses

Typical request flow:

```
Client
↓

DispatcherServlet
↓

HandlerMapping
↓

Controller
↓

Service
↓

Repository
↓

Database
↓

Response
```

It centralizes request processing and is a key part of the Spring MVC architecture.

---

## 10. Why is Spring Boot called "Convention over Configuration"?

Spring Boot follows predefined conventions so developers don't have to configure everything manually.

Examples:

- Automatically searches for `application.properties` or `application.yml` in `src/main/resources`
- Automatically scans components from the main package
- Starts Tomcat on port `8080` by default
- Auto-configures `DataSource`, Jackson, Validation, Logging, and many other components based on dependencies present on the classpath

Developers only override defaults when necessary.

This significantly reduces boilerplate configuration while still allowing customization.

---

# Bonus Interview Questions (Frequently Asked)

## What is Auto Configuration?

Auto Configuration automatically configures Spring beans based on the dependencies available on the classpath and existing configuration.

For example, adding:

```xml
spring-boot-starter-data-jpa
```

automatically configures:

- DataSource
- EntityManagerFactory
- Hibernate
- TransactionManager

without requiring manual bean definitions.

---

## Why is `@SpringBootApplication` important?

`@SpringBootApplication` is a composed annotation that combines:

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

It enables component scanning, auto-configuration, and identifies the main configuration class.

---

## Why is constructor injection preferred?

Constructor injection:

- Makes dependencies mandatory
- Supports immutable (`final`) fields
- Improves testability
- Avoids `NullPointerException` due to missing dependencies
- Is recommended by the Spring team

---

## Explain the Spring Boot startup sequence in one answer.

When a Spring Boot application starts, the operating system launches the JVM. The JVM initializes memory, loads classes, and invokes the `main()` method. `SpringApplication.run()` then creates a `SpringApplication` instance, determines the application type, loads configuration files, creates the `ApplicationContext`, performs component scanning, creates and wires beans through dependency injection, executes startup callbacks, starts the embedded Tomcat server, registers the `DispatcherServlet`, and finally publishes the application-ready event, making the application ready to serve HTTP requests.

---

# SDE-Level Interview Tips

For a 3–4 year experienced developer, interviewers typically expect you to go beyond definitions. Be prepared to discuss:

- **How auto-configuration works** (`@EnableAutoConfiguration`, `AutoConfiguration.imports`, conditional annotations).
- **Bean lifecycle** (`@PostConstruct`, `InitializingBean`, `BeanPostProcessor`, `@PreDestroy`).
- **How the `ApplicationContext` differs from the IoC container conceptually.**
- **Embedded Tomcat initialization** and how it integrates with Spring Boot.
- **Request flow** from `DispatcherServlet` to `Controller`, `Service`, `Repository`, and back.
- **Dependency Injection best practices**, especially why constructor injection is preferred.
- **How startup time can be optimized**, for example by lazy initialization, excluding unnecessary auto-configurations, or using Spring AOT/GraalVM in suitable scenarios.

These topics commonly differentiate mid-level developers from senior candidates in Spring Boot interviews.

# Summary

Although starting a Spring Boot application appears as a single call to `SpringApplication.run()`, the framework performs many initialization steps behind the scenes. It initializes the JVM, creates the Spring container, loads configuration, scans components, creates and injects beans, starts the embedded Tomcat server, registers the `DispatcherServlet`, and finally makes the application ready to serve HTTP requests.

Understanding this startup sequence provides a strong foundation for learning advanced Spring concepts and is frequently tested in technical interviews.


---

# Advanced Topics (3–4 Years Experience)

## Auto Configuration

After determining the application type, Spring Boot performs **Auto Configuration**.

`@SpringBootApplication` includes `@EnableAutoConfiguration`, which loads auto-configuration classes from:

```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

Based on the dependencies available on the classpath, Spring Boot automatically configures common infrastructure such as:

- DataSource
- DispatcherServlet
- Jackson ObjectMapper
- Validation
- TransactionManager
- Spring MVC

Auto Configuration relies on conditional annotations such as:

- `@ConditionalOnClass`
- `@ConditionalOnMissingBean`
- `@ConditionalOnProperty`

This is one of the main reasons Spring Boot requires very little manual configuration.

---

## Bean Lifecycle During Startup

After Spring creates a bean and injects its dependencies, it completes the bean lifecycle before making it available.

```text
Bean Created
      ↓
Dependency Injection
      ↓
@PostConstruct
      ↓
InitializingBean.afterPropertiesSet() (if implemented)
      ↓
Bean Ready
```

This ensures the bean is fully initialized before being used by other components.

---

## Spring Boot Startup Events

During startup Spring publishes several lifecycle events.

```text
ApplicationStartingEvent
        ↓
ApplicationEnvironmentPreparedEvent
        ↓
ApplicationPreparedEvent
        ↓
ApplicationStartedEvent
        ↓
ApplicationReadyEvent
```

`ApplicationReadyEvent` indicates that the application is fully initialized and ready to serve requests.

---

## Internal Startup Call Stack

Although we call only one method:

```java
SpringApplication.run(EwmsApplication.class, args);
```

Internally the flow is conceptually similar to:

```text
main()
   ↓
SpringApplication.run()
   ↓
new SpringApplication(...)
   ↓
run()
   ↓
prepareEnvironment()
   ↓
createApplicationContext()
   ↓
refreshContext()
   ↓
finishRefresh()
   ↓
Start Embedded Tomcat
   ↓
ApplicationReadyEvent
```

---

## One-Minute Interview Answer

When a Spring Boot application starts, the JVM is launched first. It initializes memory, loads classes, and invokes the `main()` method. `SpringApplication.run()` then creates a `SpringApplication` instance, determines the application type, loads configuration, performs auto-configuration, creates the `ApplicationContext`, scans components, creates and injects beans, completes the bean lifecycle, starts the embedded Tomcat server, registers the `DispatcherServlet`, publishes the `ApplicationReadyEvent`, and finally begins accepting HTTP requests.
