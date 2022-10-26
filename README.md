[1.View 환경 설정](#1view-환경-설정)  
[2.빌드하고 실행하기](#2빌드하고-실행하기)

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
    2. ```resources:templates/``` +{ViewName}+ ```.html```

## 2.빌드하고 실행하기

### 콘솔로 이동
1. ./gradlew build
2. cd build/libs
3. java -jar hello-spring-0.0.1-SNAPSHOT.jar
4. 실행 확인
   * [IntelliJ Git bash default 적용](https://violetboralee.medium.com/intellij-idea%EC%99%80-git-bash-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0-63e8216aa7de)