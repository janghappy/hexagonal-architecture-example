# 헥사고날 아키텍처(Ports & Adapters) 멀티 모듈 실습

회원(등록/조회)·주문(생성/조회) 기능으로 구성한 헥사고날(Ports & Adapters) 멀티 모듈 실습.

아래 흐름은 회원 기준이며, 주문은 같은 구조로 추가한 예시다.

```
 [Web 요청] → adapter.in.web.member.MemberController
                  │ (호출)
                  ▼
        application.port.in  (RegisterMemberUseCase, GetMemberUseCase)
                  ▲ (구현)
        application.service.MemberService ──► domain.Member
                  │ (호출)
                  ▼
        application.port.out (SaveMemberPort, LoadMemberPort)
                  ▲ (구현)
        adapter.out.persistence.member.MemberPersistenceAdapter → JPA/H2
```

## 모듈 구성

| 모듈 | 패키지 | 의존 |
|---|---|---|
| `domain` | `domain` | 없음 |
| `application` | `application.port.*`, `application.service` | `domain`(api), spring-context, spring-tx |
| `adapter-in-web` | `adapter.in.web.<기능>` | `application`, spring-boot-starter-webmvc |
| `adapter-out-persistence` | `adapter.out.persistence.<기능>` | `application`, spring-boot-starter-data-jpa |
| `bootstrap` | 루트(`@SpringBootApplication`), `application.yaml` | 두 어댑터 모듈, H2 |

컴파일 시점에 막히는 것:
- 모듈 경계: domain의 Spring/JPA 사용, 어댑터 간 상호 참조
- package-private: web 어댑터의 `MemberService` 직접 사용, persistence 밖에서의 JPA 엔티티·리포지토리 사용

그 밖의 위반(예: web 어댑터가 `port.out`을 직접 사용)은 `bootstrap`의 `HexagonalArchitectureTest`(ArchUnit)가 잡는다.

의존 방향은 항상 **바깥 → 안쪽**:

- `domain`: 어떤 것에도 의존하지 않는 순수 모델
- `application`: 유스케이스(in 포트)와 필요한 외부 기능(out 포트)을 인터페이스로 정의
- `adapter`: 웹/DB 같은 기술 세부사항. JPA 엔티티는 persistence 패키지 밖으로 나가지 않음

`MemberServiceTest`는 out 포트를 메모리 구현으로 바꿔 Spring/DB 없이 유스케이스를 테스트한다.

## 어댑터 내부 패키지: 기능 단위로 나눈다

어댑터 안은 역할(controller/dto/entity/repository)이 아니라 **기능(애그리거트) 단위**로 나눈다.
한 기능의 클래스가 한 패키지에 모여 있어야 DTO·JPA 엔티티·리포지토리를 package-private으로 숨길 수 있다.

```
adapter/in/web/
  common/   GlobalExceptionHandler (모든 컨트롤러 공통 예외 처리)
  member/   MemberController, RegisterMemberRequest, MemberResponse
  order/    OrderController, PlaceOrderRequest, OrderResponse      ← 기능이 늘면 이렇게 추가
adapter/out/persistence/
  member/   MemberJpaEntity, MemberJpaRepository, MemberPersistenceAdapter
  order/    OrderJpaEntity, OrderJpaRepository, OrderPersistenceAdapter
```

`controller/`, `dto/`, `entity/`처럼 역할별로 나누면 패키지끼리 참조하느라 모든 클래스를 `public`으로 열어야 하므로 피한다.

## 실행

```bash
./gradlew :bootstrap:bootRun
curl -X POST localhost:8080/members -H 'Content-Type: application/json' -d '{"name":"kim"}'
curl localhost:8080/members/1
```
