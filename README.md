[프로젝트 환경설정](#프로젝트-환경설정)  
[스프링 웹 개발 기초](#스프링-웹-개발-기초)  
# 프로젝트 환경설정

## 1.View 환경 설정

### Welcome Page 만들기

* index.html
  1. static/index.html 을 올려두면
  [Welcome page](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.welcome-page) 기능을 제공한다

### 템플릿 페이지
* HelloController.java
* hello.html
* 컨트롤러에서 리턴 값으로 문자를 반환하면,  
뷰 리졸버( viewResolver )가 화면을 찾아서 처리한다.
    1. 스프링 부트 템플릿엔진 기본 viewName 매핑
    2. `resources:templates/` +{ViewName}+ `.html`

## 2.빌드하고 실행하기

### 콘솔로 이동
1. ./gradlew build
2. cd build/libs
3. java -jar hello-spring-0.0.1-SNAPSHOT.jar
4. 실행 확인
   * [IntelliJ Git bash default 적용](https://violetboralee.medium.com/intellij-idea%EC%99%80-git-bash-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0-63e8216aa7de)

# 스프링 웹 개발 기초

## 정적 컨텐츠

### 스프링 부트 정적 컨텐츠 기능
* [스프링 부트 정적 컨텐츠 문서](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.static-content)

### 실행
* http://localhost:8080/hello-static.html

### 정적 컨텐츠 기능 flowchart
1. 웹 브라우저 (http://localhost:8080/hello-static.html)  
2. 내장 톰캣 서버 => 스프링 컨테이너
    * hello-static 관련 컨트롤러 색인, 없음 
    * static/hello-static.html 색인, 존재
3. static/hello-static.html => 웹 브라우저

## MVC와 템플릿 엔진

### MVC
* Model, View, Controller

### 실행
* http://localhost:8080/hello-mvc?name=spring

### MVC flowchart
1. 웹 브라우저 => 내장 톰캣 서버
    * http://localhost:8080/hello-mvc?name=spring
2. 내장 톰캣 서버 => 스프링 컨테이너
    * hello-mvc 관련 컨트롤러 색인, 존재
    * return hello-template
    * model(name:spring)
3. 스프링 컨테이너 => viewResolver
    * hello-mvc 의 return templates/hello-template.html 에 Thymeleaf 템플릿 엔진 처리
4. viewResolver => 웹 브라우저
    * Thymeleaf 템플릿 엔진 처리 후 HTML 변환

## API

### @ResponseBody 문자 반환
* `@ResponseBody` 를 사용하면 뷰 리졸버`( viewResolver )`를 사용하지 않음
* 대신에 HTTP의 BODY에 문자 내용을 직접 반환(HTML BODY TAG를 말하는 것이 아님)

### 실행
* http://localhost:8080/hello-spring?name=spring

### @ResponseBody 객체 반환
* `@ResponseBody` 를 사용하고, 객체를 반환하면 객체가 JSON으로 변환됨

### 실행
* http://localhost:8080/hello-api?name=spring

### @ResponseBody flowchart
1. 웹 브라우저 => 내장 톰캣 서버
    * http://localhost:8080/hello-strng?name=spring
    * @ResponseBody return: hello spring
    * http://localhost:8080/hello-api?name=spring
    * @ResponseBody return: hello(name:spring)
2. 내장 톰캣 서버 => 스프링 컨테이너(helloController)
3. 스프링 컨테이너(helloController) => 스프링 컨테이너(HttpMessageConverter)
    * HttpMessageConverter  
      implements JsonConverter, StringConverter
4. 스프링 컨테이너(HttpMessageConverter) => 웹 브라우저
    * hello spring
    * {"name" : "spring"}

### @ResponseBody 사용 원리
* `@ResponseBody` 를 사용
* HTTP의 BODY에 문자 내용을 직접 반환
* `viewResolver` 대신에 `HttpMessageConverter` 가 동작
* 기본 문자처리: `StringHttpMessageConverter`
* 기본 객체처리: `MappingJackson2HttpMessageConverter`
* byte 처리 등등 기타 여러 `HttpMessageConverter`가 기본으로 등록되어 있음

> 참고: 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서
HttpMessageConverter 가 선택된다.