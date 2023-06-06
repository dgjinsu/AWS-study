## AWS

* 인스턴스 생성
* 윈도우 선택
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/ee6c90f1-1c08-46de-9d7d-65522ccfee19)

* t2.micro 사용 (프리티어 사용 가능)
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/d883ed5b-7a45-41a0-8a9a-cee3d85de196)

* 키페어 생성
  * 접속하기 위한 비밀번호
  * 잊어버리면 다시 다운받을 수 없다. (잊어버리지 않도록 주의)
  * 복제 후 새로운 키를 부여하면 가능하긴 함

* 스토리지
* 프리티어는 30까지 무료이다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/ce0fb0e0-2d23-46e8-89c6-da10328e35b0)

* 보안 그룹 생성
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/6d870934-f423-417d-83bb-6a015495639f)
 * 스프링 부트 기반 서버를 열어줄것이기 때문에 사용자 지정으로 8080 포트를 설정해준 뒤 url을 아는 누구나 접속할수있도록 Anywhere-IPv4 로 설정해줍니다.

 * 원격 EC2 인스턴스에 접속할때 사용되는 ssh 관련 방화벽으로 저는 밖에서도 접속할 때가 있으므로 저희집 고정 ip가 아닌 Anywhere-IPv4 로 설정했습니다. 또한 ssh는 기본 포트 연결로 22번 포트가 사용됩니다.

 * HTTP 연결시 사용됩니다.

 * HTTPS 연결시 사용됩니다.
