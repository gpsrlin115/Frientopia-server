# Frientopia | 캡스톤디자인
2023년도 1학기 캡스톤디자인 프렌토피아 팀의<br/>
교내 멘토링 매칭 서비스 플랫폼입니다. <br/>
**기술 및 프로젝트에 관한 상세한 설명은 [블로그](https://velog.io/@ygy0102/Spring-Boot-%EC%B2%AB%EB%B2%88%EC%A7%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%99%84%EC%84%B1%EA%B3%BC-%ED%9A%8C%EA%B3%A0)를 참고 해주세요!**
## 멤버 및 역할
|이름|역할|
|------|---|
|장진윤|기획, 문서작성|
|윤재영|Frontend 개발, Backend 개발, Infra 구축 및 관리|
|심서현|Backend 개발, Database 설계|
|김성진|Backend 개발|

## 프로젝트 개요
### 배경
간단한 취미생활 뿐만 아니라 전공과 관련된 학습 역시 수업, 개인적인 공부를 통해 의문을 해소하기 힘든 경우가 많다.    
취미 생활의 경우, 교내에 동아리라는 시스템이 있지만, 가볍게 배우고 취미생활을 즐기고 싶은 학생은 한 학기 동안    
꾸준한 참여를 요구하는 동아리가 부담으로 다가 올 수도 있다.    
또한 학교에서는 학습능력 증진, 유대감 등을 목적으로 튜터링 프로그램을 진행하고 있다.   
그러나 현재 학교에서 진행하는 튜터링은 기간, 인원이 제한 적이며, 한정된 부분에서만 진행이 가능하다.   
위의 생각에서 비롯되어 교내 학생들이 자유롭게 이용하고 참여 할 수 있는 멘토링 플랫폼을 제작 하고자 한다.

### 목표
- 교내 구성원에게 서로 필요한 멘토링 프로그램을 진행 함으로써 학업 증진 뿐만 아닌 유대감 형성이 가능하다.
- 시간, 공간 등 기타 제약사항에 얽매이지 않고 자유로운 멘토링이 가능하다.
- 능동적인 학습 분위기 조성을 기대할 수 있다.

### 주요기능
- 위치 기반 서비스를 통한 사용자의 근처 멘토링 찾기
- iamport API를 이용하여 결제 서비스
- 코딩 멘토링을 위한 웹 IDE
- 멘토링을 신청한 인원들(매핑)의 멘토링룸 개설 및 입장
- 멘토링룸 내의 인원들만의 독립적인 실시간 채팅 공간

## 기능명세
### 유스케이스 다이어그램
![유스케이스](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/caca562a-bc64-4bb1-b422-afa05898b69a)

### UI Design
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/9346a802-3b9e-4b53-8574-7d6983a8fd52)
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/f06a92c0-f9fb-4517-85dc-1dd1fdd3a95e)
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/1cabaf43-3fbc-4543-83e3-c075b0442f6b) 
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/1ddeab24-5637-4f76-b82e-fd847df2e8ee)
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/4dcbf697-cf87-4ddf-b366-721515021130)
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/98d6639d-056f-4e82-ad0d-163df5c51a29)
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/28051d9b-b1c3-4746-9f4c-f3135caeb580)
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/c620aaf5-bd4c-474c-a798-9b86e3bbdb30)

## ERD
### AWS RDS 에 올라간 최종 ERD
![image](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/da195a36-8666-48ea-835b-fc846155532c)

## 사용기술
![realstack](https://github.com/Jae-Young98/Frientopia-server/assets/86467141/fc5c0229-b66c-40c5-b76f-be9b637c1102)


## 참고서적
[스프링부트와 AWS로 혼자 구현하는 웹 서비스](https://product.kyobobook.co.kr/detail/S000001019679)
