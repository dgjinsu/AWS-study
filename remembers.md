## EC2

* 인스턴스 생성
* ubuntu 선택
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/e400c44f-5aad-4532-9808-a5d9072f69f1)


* t2.micro 사용 (프리티어 사용 가능)
 * 인스턴스를 탄력적 IP 주소에 연결하지 않은 시간당 비용이 $0.005 (프리티어로 만들어도 탄력 IP 부여하지 않으면 요금 발생 가능) 
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/d883ed5b-7a45-41a0-8a9a-cee3d85de196)

* 키페어 생성
  * ec2에 접속하기 위한 비밀번호
  * 잊어버리면 다시 다운받을 수 없다. (잊어버리지 않도록 주의)
  * 복제 후 새로운 키를 부여하면 가능하긴 함

* 스토리지
* 프리티어는 30까지 무료이다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/ce0fb0e0-2d23-46e8-89c6-da10328e35b0)

* 보안 그룹 생성
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/6d870934-f423-417d-83bb-6a015495639f)
 * 스프링 부트 기반 서버를 열어줄것이기 때문에 사용자 지정으로 8080 포트를 설정해준 뒤 url을 아는 누구나 접속할수있도록 Anywhere-IPv4 로 설정해줍니다.
 * 원격 EC2 인스턴스에 접속할때 사용되는 ssh 관련 방화벽으로 저는 밖에서도 접속할 때가 있으므로 저희집 고정 ip가 아닌 Anywhere-IPv4 로 설정했습니다. 또한 ssh는 기본 포트 연결로 22번 포트가 사용
 * HTTP 연결시 사용
 * HTTPS 연결시 사용
 * 추가로 mysql 을 사용한다면 3306 port를 열어주자(나중에 해도 됨)


**여기까지 완료 했으면 ec2를 생성 완료하고 관계형 DB를 쓴다면 RDS를 만들어야 한다.**



## RDS 

* RDS 는 mysql로 만들어주겠다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/853fcfc0-bbe0-4bfc-8dcc-b72086605395)

* 1년동안 무료로 사용 가능한 프리티어 선택
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/c5a1ba0d-b623-40e2-897a-4ed5d54b63f5)

* RDS이름과 사용자, 비밀번호를 설정해준다
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/82433fde-a515-4c0c-b8da-ea9db8ee48c2)

* DB인스턴스 크기에선 버스터블 클래스의 db.t2.micro 선택
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/2ea00a2a-703f-43b7-bdc9-89f0c52df4e0)

* 연결 부분에선 아래 부분만 따로 설정해주었다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/b6e5014c-e57d-42ad-9710-ca73398883f0)
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/3bebfb77-97ae-432c-9608-3be713ade1ee)
* 가용 영역은 ec2에서 확인할 수 있다.
* ![image](https://github.com/dgjinsu/AWS-study/assets/97269799/059a2fcf-9eb9-48a9-b0f8-0e4d81567190)


* 추가구성에서 백업 보존 기간을 0일로 설정하자
* 예전에 기본 7일로 두고 만들었다가 결제된적이 있다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/9c16fb61-1e66-4750-b3eb-06081d11120b)

* 생성이 완료되었다면 mysql workbench 와 연결하면 된다.

* 엔드포인트를 hostnamd에 작성, RDS 생성하며 만들었던 사용자 이름 작성 후 test connection
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/1b7794a3-3666-4b1f-bd94-edd94d6fe176)
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/b29405ba-1eb6-46e0-82c5-7f06a1609c1a)

* 인바운드 규칙에 mysql을 추가하지 않았다면 인바운드 규칙 - 편집에서 MySQL 워크벤치가  데이터베이스에 접근할 수 있도록 아래와 추가
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/27f9b294-b0c6-47d8-b310-99af1d3a4f71)

* 스프링 프로젝트에 만들어진 RDS 연결
```java
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://colony-test.coz9al7ljo0h.ap-northeast-2.rds.amazonaws.com:3306/colony-test
    username: root
    password: 12341234
```
 * driver-class-name 을 따로 지정해주지 않으니 오류가 났다.
 * 또한 스프링 3.x부터 mysql 의존성 추가 방법이 조금 달랐다. gradle 파일에 아래와 같이 설정
```java
runtimeOnly("com.mysql:mysql-connector-j")
```





## 다운 받은 키 페어를 통해 ssh 서버에 접속 (ec2에 접속)

* putty 설치
* puttyGen 도 함께 설치되는데 여기서 .pem 파일을 .ppk 파일로 바꿀 수 있다.
* load 를 클릭하고 .pem 파일을 선택한 후 save private key 버튼을 클릭하면 .ppk 파일로 바꿀 수 있다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/78ffe8d9-b5cc-4f70-a2f7-b3d7af4bdcd9)

* putty 를 실행하고 HostName에 ubuntu@{탄력적IP주소} 를 입력한다
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/32c84ff0-e986-405a-8163-95d03af3591e)

* SSH -> Auth -> Credentials 에 들어가 아까 만든 ppk 파일을 올려주고 open 을 누른다. 
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/ecd0a1eb-343d-4620-a470-ccdd5b661d8f)

* 이로써 키-페어로 ec2에 연결 완료했다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/c9ef462f-0f52-4e7d-b2bd-702337ed0b3c)


