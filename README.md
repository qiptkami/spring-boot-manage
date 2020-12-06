# spring-boot-manage

## 一、开发背景

基于spring boot + spring security + thymeleaf + mybatis + redis + druid + mysql的一个简单管理系统

spring security登录验证和权限控制，thymeleaf模板引擎渲染页面，mybatis框架简化数据库操作，redis做中间缓存（未使用spring-boot-data模块）

练习技术栈和springboot整合

## 二、开发规范

个人项目，所以代码直接推送至主分支。

### 2.1、文件tree

```
│  .gitignore
│  pom.xml
│  README.md
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─yiqiandewo
│  │  │          │  SpringBootThymeleafApplication.java
│  │  │          │
│  │  │          ├─component
│  │  │          │      MyLocaleResolver.java
│  │  │          │
│  │  │          ├─config
│  │  │          │      MvcConfig.java
│  │  │          │      RedisConfig.java
│  │  │          │      SecurityConfig.java
│  │  │          │
│  │  │          ├─controller
│  │  │          │      DepartmentController.java
│  │  │          │      EmployeeController.java
│  │  │          │
│  │  │          ├─mapper
│  │  │          │      IDepartmentMapper.java
│  │  │          │      IEmployeeMapper.java
│  │  │          │      IRoleMapper.java
│  │  │          │      IUserMapper.java
│  │  │          │
│  │  │          ├─pojo
│  │  │          │      Department.java
│  │  │          │      Employee.java
│  │  │          │      Role.java
│  │  │          │      User.java
│  │  │          │
│  │  │          └─service
│  │  │              │  IDepartmentService.java
│  │  │              │  IEmployeeService.java
│  │  │              │  IRoleService.java
│  │  │              │
│  │  │              └─impl
│  │  │                      DepartmentServiceImpl.java
│  │  │                      EmployeeServiceImpl.java
│  │  │                      RoleServiceImpl.java
│  │  │
│  │  └─resources
│  │      │  application.yml
│  │      │
│  │      ├─com
│  │      │  └─yiqiandewo
│  │      │      └─mapper
│  │      │              IDepartmentMapper.xml
│  │      │              IEmployeeMapper.xml
│  │      │
│  │      ├─i18n
│  │      │      login.properties
│  │      │      login_en_US.properties
│  │      │      login_zh_CN.properties
│  │      │
│  │      ├─static
│  │      │  └─asserts
│  │      │      ├─css
│  │      │      │      bootstrap.min.css
│  │      │      │      dashboard.css
│  │      │      │      signin.css
│  │      │      │
│  │      │      ├─img
│  │      │      │      bootstrap-solid.svg
│  │      │      │
│  │      │      └─js
│  │      │              bootstrap.min.js
│  │      │              Chart.min.js
│  │      │              feather.min.js
│  │      │              jquery-3.2.1.slim.min.js
│  │      │              popper.min.js
│  │      │
│  │      └─templates
│  │          │  dashboard.html
│  │          │  login.html
│  │          │
│  │          ├─commons
│  │          │      bar.html
│  │          │
│  │          ├─emp
│  │          │      add.html
│  │          │      list.html
│  │          │      update.html
│  │          │
│  │          └─error
│  │                  403.html
│  │                  404.html
```



> windows生成文件树的方法

在powershell中打开项目目录

命令：

```powershell
tree /f
```

---



## 三、开发中遇到的问题

### 3.1、spring security自定义登录页面成功登录跳转出错

```java
//登录
http.formLogin()
    .loginPage("/userLogin")  //自定义登录页面
    .defaultSuccessUrl("/main", true); 
//使用successForwardUrl()发送post请求，报错DefaultHandlerExceptionResolver  successFordwardUrls只支持post请求
//defaultSuccessUrl和successForwardUrl进行路径跳转的底层实现的方式不同，前者是重定向，后者是转发
```



### 3.2、spring security登录验证出错

spring security配置好，但是用户名密码都正确，但是登录时验证失败，从源码中得知，默认是username和password，前端表单中没有配置

```java
/**
* 				.usernameParameter(&quot;username&quot;) // default is username
* 				.passwordParameter(&quot;password&quot;) // default is password
* 				.loginPage(&quot;/authentication/login&quot;) // default is /login with an HTTP get
* 				.failureUrl(&quot;/authentication/login?failed&quot;) // default is /login?error
* 				.loginProcessingUrl(&quot;/authentication/login/process&quot;); // default is /login
* 																		// with an HTTP
* 																		// post
*/
```

是由于登录表单中需要将username和password的name属性设置为username和password

```html
<input type="text" name="username" class="form-control" placeholder="username" th:placeholder="#{login.username}" required="" autofocus="">
<label class="sr-only" th:text="#{login.password}">Password</label>
<input type="password" name="password" class="form-control" placeholder="password" th:placeholder="#{login.password}" required="">
```



### 3.3、redis快照出错

redis再次启动时，发现redis中的缓存的key全部变为backup

百度过后，是因为没有设置密码。



### 3.4、springboot web配置put，delete请求

SpringBoot 2.2.X默认不支持put，delete等请求方式的。

1.SpringMVC配置HiddenHttpMethodFilter  (SpringBoot自动配置)
2.页面创建post表单
3.创建input name="_method" 值就是指定的请求方式

```html
<form id="delForm" method="post">
    <input type="hidden" name="_method" value="delete"/>
</form>
```

同时还要在`application.yml`中配置

```yaml
spring:
  mvc:
    hiddenmethod:
      filter:
        enabled: true # Spring Boot 的 META-INF/spring-configuration-metadata.json 配置文件中默认是关闭 Spring 的 hiddenmethod 过滤器的。
        # 这时候需要通Springboot配置文件application.yml/properties 中将 hiddenmethod 过滤器设置为启用即可。 # 可以配置delete put等请求
```



### 3.5、表单提交日期格式出错

前端提交表单日期格式为：yyyy-MM-dd，报错，spring boot默认的格式是yyyy/MM/dd，只需在配置文件中配置：

```yaml
spring:
  mvc:
    format:
      date: yyyy-MM-dd # 格式化日期
```



## 四、开发细节

### 4.1、mybatis配置

```yaml
mybatis:
  mapper-locations: classpath:com/yiqiandewo/mapper/*.xml # mapper映射文件位置
  type-aliases-package: com.yiqiandewo.pojo # 实体类所在位置
  configuration:
    map-underscore-to-camel-case: true  # 开启驼峰命名
```



### 4.2、使用redis缓存

(1) 速度快，因为数据存在内存中，类似于HashMap，HashMap的优势就是查找和操作的时间复杂度都是O(1)

(2) 支持丰富数据类型，支持string，list，set，sorted set，hash

(3) 支持事务，操作都是原子性，所谓的原子性就是对数据的更改要么全部执行，要么全部不执行

(4) 丰富的特性：可用于缓存，消息，按key设置过期时间，过期后将会自动删除



### 4.3、spring security

#### 4.3.1、spring security配置数据库用户名密码登录

登录使用spring security，实现`UserDetailsService`接口的`loadUserByUsername()`，

```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    com.yiqiandewo.pojo.User user = userMapper.queryByUsername(username);

    if (user == null) {
        throw new UsernameNotFoundException("用户不存在");
    }
    //添加权限
    Integer id = user.getRoleId();
    Role role = roleMapper.queryById(id);

    return new User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()), getAuthority(role));
}
```

在spring security配置类中配置

```java
@Override
public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(roleService).passwordEncoder(bCryptPasswordEncoder());
}
```



#### 4.3.2、spring security 注解权限控制与页面显示

```
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
```



##### controller

```java
@Secured("ROLE_root")  //需要将@EnableGlobalMethodSecurity的属性securedEnabled配置为true //权限名区分大小写
@GetMapping("/list")
public String queryAll(Model model) {
    List<Employee> list = employeeService.queryAllDepartment();
    model.addAttribute("emps", list);
    return "emp/list";
}
```



##### 页面

```html
<li class="nav-item" sec:authorize="hasRole('root')">  <!-- 只有root权限才可以看到标签体内容 -->
    ...
</li>
```



### 4.4、spring boot error页面配置

将错误页面放在templates/error文件下，并将文件名改为 错误状态码.html（404.html、403.html），如果出现404，就会自动跳转404.html



### 4.5、 使用restful

`controller`中全部使用restful风格的请求方式，根据前端发送的不同请求

GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源；



### 4.6、spring boot 2.2.x 项目名设置

```yml
server:
  servlet:
    context-path: /firstDemo # 项目名 # ContextPath must start with '/' and not end with '/'
```



## 五、写在最后

本次项目虽然内容比较少，但是个人之前学到的技术栈，大部分都有涵盖到，通过这个项目，熟练了各个技术栈的使用，在遇到问题时，优先去源码中或者官方文档中寻找答案而不是直接百度。