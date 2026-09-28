# DEARESTSHOP

Spring Boot 기반 온라인 쇼핑몰 백엔드 프로젝트

## 프로젝트 소개

DEARESTSHOP은 온라인 쇼핑몰의 주요 기능을 구현한 프로젝트입니다.

회원 관리부터 상품, 장바구니, 위시리스트, 주문, 배송지 관리까지
쇼핑몰의 기본적인 구매 흐름을 Spring Boot 기반으로 구현했습니다.

또한 Spring Security와 JWT를 이용한 인증/인가를 적용하고,
QueryDSL을 이용하여 상품 검색, 카테고리 필터링 및 정렬 기능을 구현했습니다.

---

## 기술 스택

### Backend

- Java 21
- Spring Boot
- Spring Data JPA
- QueryDSL
- Spring Security
- JWT
- H2 Database

### Test

- JUnit 5
- Spring Boot Test

### Frontend

- React
- Vite
- React Router
- Axios
- Bootstrap
- React Icons

---

## 주요 기능

### 회원

- 회원가입
- 로그인
- 비밀번호 암호화
- JWT 기반 인증
- 회원 조회

### 상품

- 상품 등록
- 상품 조회
- 상품 상세 조회
- 상품 이미지 등록
- 카테고리 조회
- 상품 검색
- 카테고리 필터링
- 최신순 정렬
- 판매량순 정렬

### 장바구니

- 장바구니 상품 추가
- 장바구니 수량 변경
- 장바구니 상품 삭제
- 장바구니 조회

### 위시리스트

- 상품 찜하기
- 찜한 상품 조회
- 찜 취소

### 주문

- 주문 생성
- 주문 조회
- 주문 상세 조회
- 주문 완료
- 주문 취소
- 주문 생성 시 재고 감소
- 주문 생성 시 판매량 증가
- 주문 취소 시 재고 복구
- 주문 취소 시 판매량 복구

### 배송지

- 배송지 등록
- 배송지 조회
- 배송지 수정
- 배송지 삭제
- 기본 배송지 설정

### 관리자

- 회원 관리
- 상품 등록

---

## 인증

Spring Security와 JWT를 이용하여 로그인 인증을 구현했습니다.

로그인 성공 시 JWT를 발급하고,
이후 요청에서 JWT를 이용하여 로그인 회원을 확인합니다.

---

## 상품 검색

QueryDSL을 이용하여 상품 검색 기능을 구현했습니다.

- 상품명 검색
- 카테고리 필터링
- 최신순 정렬
- 판매량순 정렬

검색 조건에 따라 동적으로 쿼리를 구성하도록 구현했습니다.

---

## 주문 처리

주문 생성 시 주문 상품의 판매량을 증가시키고 재고를 감소시킵니다.

주문 취소 시에는 반대로 재고를 복구하고 판매량을 감소시킵니다.

```
주문 생성
    ↓
판매량 증가
    ↓
재고 감소

주문 취소
    ↓
판매량 감소
    ↓
재고 복구
```

---

## 테스트

Integration 테스트

@SpringBootTest를 이용하여 실제 Spring Context를 구성하고,
테스트용 H2 데이터베이스를 사용하여 여러 계층이 함께 동작하는지 테스트했습니다.

회원 통합 테스트
상품 통합 테스트
주문 통합 테스트

## 프로젝트 구조

```text
dearestshop
├── src
│   ├── main
│   │   ├── java
│   │   │   └── dearest
│   │   │       └── dearestshop
│   │   │           ├── api
│   │   │           ├── config
│   │   │           ├── controller
│   │   │           ├── domain
│   │   │           ├── dto
│   │   │           ├── jwt
│   │   │           ├── repository
│   │   │           └── service
│   │   │
│   │   └── resources
│   │       └── application.yml
│   │
│   └── test
│       ├── java
│       │   └── dearest
│       │       └── dearestshop
│       │           └── ...
│       └── resources
│           └── application.yml
│
├── docs
│   └── erd.png
│
├── build.gradle
├── settings.gradle
└── README.md
```

## ERD
![DEARESTSHOP ERD](docs/dearest_Erd.png)

## API 
![DEARESTSHOP API](docs/dearest_api.png)

## 화면

### 회원
- 로그인
  ![login](docs/images/login.png)
- 회원가입

### 상품
- 상품 목록
  ![products](docs/images/products.png)
- 상품 상세
  ![product_detail](docs/images/products_detail.png)

### 구매
- 장바구니
  ![cart](docs/images/cart.png)
- 주문
  ![order](docs/images/order.png)

### 마이페이지
- 마이페이지
  ![mypage](docs/images/mypage.png)
- 주문 내역
  ![orderlist](docs/images/orderlist.png)
- 배송지 관리
- 위시리스트

### 관리자
- 회원 관리
- 상품 등록
  ![product_add](docs/images/product_add.png)

## 트러블슈팅 

### 1. JWT 인증 과정에서 403 오류 발생

**문제**
상품 등록 요청 시 403 Forbidden 오류가 발생했습니다.

**원인**
JWT 토큰 만료로 인해 인증되지 않은 요청으로 처리되었습니다.

**해결**
재로그인 후 새로운 JWT를 발급받고 Authorization 헤더가 전달되는지 확인했습니다.

---

### 2. 상품 등록 시 Category ID가 전달되지 않는 문제

**문제**
상품 등록 요청에서 Category ID가 null로 전달되었습니다.

**원인**
프론트엔드의 `categoryId`와 DTO 필드명이 일치하지 않았습니다.

**해결**
프론트엔드 요청 데이터와 DTO 필드명을 동일하게 수정하였습니다.

---

### 3. 테스트 데이터 생성 시 Category 중복 오류

**문제**
테스트 실행 시 Category의 unique constraint 오류가 발생했습닌다.

**원인**
테스트마다 동일한 `TOPS` 카테고리를 생성했습니다.

**해결**
테스트 데이터 생성 방식을 수정하여 중복 데이터가 생성되지 않도록 처리하였습니다.

---

### 4. 주문 취소 시 상품 재고 복구

**문제**
주문 취소 후 상품 재고가 원래 수량으로 복구되지 않았습니다.

**원인**
주문 생성 시 재고를 감소시키는 로직은 있었지만,
주문 취소 시 재고를 증가시키는 로직이 없었습니다.

**해결**
주문 취소 시 주문 수량만큼 재고를 복구하고 판매량을 감소시키도록 수정하였습니다.

## 실행 방법 

dearest 프로젝트는 별도의 서버에 배포하지 않았으며,
로컬 환경에서 실행할 수 있습니다.

### 1. H2 Database 실행

H2 Database를 실행합니다.

dearest 프로젝트는 H2 TCP Server를 사용하며,
Spring Boot에서 다음과 같은 데이터베이스에 연결합니다.
개발 및 테스트 목적으로 H2 Database와 JPA `ddl-auto: create` 설정을 사용합니다.

```text
jdbc:h2:tcp://localhost/~/dearestshop
```

H2서버가 실행 중인 상태에서 Backend를 실행합니다.

### 2. H2 Backend 실행

IntelliJ에서 Spring Boot 메인 클래스를 실행합니다.

```text
http://localhost:8080
```

Spring Boot 실행 시 초기 데이터가 자동으로 생성됩니다.
- 카테고리 : TOPS, SKIRTS, PANTS, DRESSES
- 초기 상품 : 총 21개
- 일반 회원 1명
- 관리자 회원 1명
- 관리자 기본 배송지 1개

로컬 테스트 전용 계정입니다.
구분	    이메일	            비밀번호
일반 회원	123@test.com	    123
관리자	    admin@test.com	    admin

### 3. Frontend 실행

React 프로젝트로 이동합니다.

npm install
npm run dev

```text
http://localhost:5173
```

### 4. 웹사이트 접속

로그인 후 상품 조회, 검색, 장바구니,
위시리스트, 배송지 관리, 주문 및 관리자 기능을
직접 확인할 수 있습니다.

## 배포

현재 별도의 서버 배포는 진행하지 않았으며,
로컬 개발 환경에서 Backend와 Frontend를 연동하여 구현 및 테스트했습니다.

