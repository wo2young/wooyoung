package kr.or.human2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.human2.dao.MemberDAO;

@Service  // 이 클래스가 "서비스 계층"의 빈(Bean)임을 스프링에게 알림 → 컴포넌트 스캔 시 등록됨
public class MemberService {

     // 스프링이 MemberDAO 타입의 빈(구현체: MemberDAOImpl)을 찾아 자동으로 주입해줌
     // 지금은 "필드 주입" 방식
     @Autowired
     MemberDAO memberDAO;

    // [대안] 생성자 주입 버전 (Spring 4.3+는 @Autowired 생략 가능)
    // → 생성자 주입은 필드 주입보다 불변성, 테스트 편의성에서 유리함
//    public MemberService(MemberDAO memberDAO) {
//        this.memberDAO = memberDAO;
//    }

    public List getList() {
        // 내부에서 절대 new MemberDAOImpl() 같이 직접 구현체를 생성하지 않는다.
        // (스프링 컨테이너가 주입해준 memberDAO만 사용해야 DI 원칙을 지킬 수 있음)
        return this.memberDAO.select();
    }

    /*
     ────────────────────────── 전체 정리 ──────────────────────────
     - 현재 구조: @Autowired 필드 주입으로 MemberDAO 구현체(MemberDAOImpl)가 연결됨
     - 장점: 코드 짧고 간단, 지금처럼 구조 잡을 때 편리
     - 단점: 필드가 final이 아니므로 변경 가능 → 불변성 보장 X, 테스트 더블 교체 어려움
     - 권장: 설정이 안정되면 "생성자 주입"으로 바꿔서 안정성과 가독성을 높이는 게 좋음
     - 핵심 원칙: Service 계층은 DAO 인터페이스에만 의존하고,
                  실제 구현체는 스프링 컨테이너가 주입하도록 맡긴다.
     - 주의사항: MemberDAOImpl 클래스에 @Repository 붙이고,
                 root-context.xml(또는 ComponentScan 설정)에 kr.or.human2.dao 패키지가 포함돼야 한다.
    */
}
