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

### `@ResponseBody` flowchart
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

### `@ResponseBody` 사용 원리
* `@ResponseBody` 를 사용
* HTTP의 BODY에 문자 내용을 직접 반환
* `viewResolver` 대신에 `HttpMessageConverter` 가 동작
* 기본 문자처리: `StringHttpMessageConverter`
* 기본 객체처리: `MappingJackson2HttpMessageConverter`
* byte 처리 등등 기타 여러 `HttpMessageConverter`가 기본으로 등록되어 있음

> 참고: 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서
HttpMessageConverter 가 선택된다.

# 회원 관리 예제 - 백엔드 개발

## 비즈니스 요구사항 정리

### 데이터
* 회원ID, 이름
### 기능
* 회원 등록, 조회
### 추가적 시나리오
* 아직 데이터 저장소가 선정되지 않음(가상의 시나리오)

## 일반적인 웹 애플리케이션 계층 구조

### Controller
* 웹 MVC 의 Controller 역할
### Service
* 서비스 핵심 기술 구현
### Repository
* 데이터베이스 접근, 도메인 객체를 DB 에 저장하고 관리
### Domain
* 비즈니스 도메인 객체
* 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨
### flowchart
```
Controller  →  Service  →  Repository  →  DB
         ↘        ↓        ↙
               Domain
```

## 클래스 의존관계

### 아직 데이터 저장소가 선정되지 않았음
* 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계 
### 데이터 저장소
* RDB, NoSQL 등등 다양한 저장소를 고민하고 있는 상황으로 가정
### 개발을 진행하기 위해서 초기 개발 단계
* 구현체로 가벼운 메모리 기반의 데이터 저장소를 사용
### flowchart 일반적인 웹 애플리케이션 계층 구조
```
MemberService  →  MemberRepository(interface)
                        ⇡
                  MemoryMemberRepository(implements)
```

## 회원 도메인과 리포지토리 만들기
* 회원 객체
* 회원 리포지토리 인터페이스
* 회원 리포지토리 메모리 구현체

## 회원 리포지토리 테스트 케이스 작성
* 개발한 기능을 실행해서 테스트 할 때 자바의 main 메서드를 통해서 실행하거나, 웹 애플리케이션의 컨트롤러를 통해서 해당 기능을 실행한다.
* 이러한 방법은 준비하고 실행하는데 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에 실행하기 어렵다는 단점이 있다. 
* 자바는 JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다.

### 회원 리포지토리 메모리 구현체 테스트
* `src/test/java` 하위 폴더에 `MemoryMemberRepositoryTest` 를 생성한다.

### `@AfterEach`
* 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다.
* 이렇게 되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다.
* `@AfterEach` 를 사용하면 각 테스트가 종료될 때 마다 이 기능을 실행한다.
* 여기서는 메모리 DB에 저장된 데이터를 삭제한다. 

### 테스트는 각각 독립적으로 실행되어야 한다.
* 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다.

## 회원 서비스 개발
* 회원가입
* 전체 회원 조회

## 회원 서비스 테스트
* 기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성하게 했다.
* 회원 리포지토리의 코드가 **회원 서비스 코드를 DI 가능하게 변경한다.**
### `@BeforeEach`
* 각 테스트 실행 전에 호출된다. 
* 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다