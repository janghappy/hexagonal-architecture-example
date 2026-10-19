# Hexagonal Architecture Example

회원(Member) 등록/조회만 있는 아주 단순한 헥사고날(Ports & Adapters) 예제.

```
 [Web 요청] → adapter.in.web.MemberController
                  │ (호출)
                  ▼
        application.port.in  (RegisterMemberUseCase, GetMemberUseCase)
                  ▲ (구현)
        application.service.MemberService ──► domain.Member
                  │ (호출)
                  ▼
        application.port.out (SaveMemberPort, LoadMemberPort)
                  ▲ (구현)
        adapter.out.persistence.MemberPersistenceAdapter → JPA/H2
```

의존 방향은 항상 **바깥 → 안쪽**:

- `domain`: 어떤 것에도 의존하지 않는 순수 모델
- `application`: 유스케이스(in 포트)와 필요한 외부 기능(out 포트)을 인터페이스로 정의
- `adapter`: 웹/DB 같은 기술 세부사항. JPA 엔티티는 persistence 패키지 밖으로 나가지 않음

`MemberServiceTest`는 out 포트를 메모리 구현으로 바꿔 Spring/DB 없이 유스케이스를 테스트한다.

## 실행

```bash
./gradlew bootRun
curl -X POST localhost:8080/members -H 'Content-Type: application/json' -d '{"name":"kim"}'
curl localhost:8080/members/1
```
