[1.View 환경 설정](#1.view-환경-설정)

# 1.View 환경 설정

---
## Welcome Page 만들기

* index.html
  1. static/index.html 을 올려두면
  [Welcome page](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.welcome-page) 기능을 제공한다

## 템플릿 페이지
* HelloController.java
* hello.html
* 컨트롤러에서 리턴 값으로 문자를 반환하면,  
뷰 리졸버( viewResolver )가 화면을 찾아서 처리한다.
    1. 스프링 부트 템플릿엔진 기본 viewName 매핑
    2. ```resources:templates/``` +{ViewName}+ ```.html```