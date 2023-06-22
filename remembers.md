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


* 생성이 완료 되었으면 이제 몇가지 필수 설정을 해야할 차례
* 이제 파라미터 그룹을 생성해줘야 한다.
* 파라미터 그룹 패밀리는 방금전에 생성했던 mySql 버전과 같은 버전으로 맞춰준다.
* 
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/02aba126-8611-4cd0-8d53-4a1a2b1bac1b)


* 제일 먼저 Time Zone을 변경해준다. 검색창에 time_zone을 검색한 뒤 값을 Asia/Seoul로 변경한다. 변경 후 체크박스를 체크하고 재설정을 선택한다.
* 재설정 하겠냐고 물어보는데 확인 누르면 됨
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/791e7ae9-252b-4f8e-b91d-ffc5c14e9bb6)

* character을 검색한 뒤 6개 항목에 대해 값을 utf8mb4로 변경한다.
 * character_set_client
 * character_set_connection
 * character_set_database
 * character_set_filesystem
 * character_set_results
 * character_set_server
* collation을 검색한 뒤 아래 항목에 대해 값을 utf8mb4_general_ci로 변경하고 역시 재설정 한다.
 * collation_connection
 * collation_server
 * utf8mb4는 utf8에서 😀나 😍와 같은 이모지 저장이 가능해진 Character Set이다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/de5bbdbc-d8c5-4f55-8815-367f0d10a444)
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/e7db1fa9-9a52-4a99-bb4a-e66b70b32540)
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/7f438fdc-564a-468e-ae4b-c59328a6e94e)

* DB에 파라미터 연결

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/ea568e1f-dfd7-42ff-a15c-a32c05185fb8)
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/e0a2b0a0-5ee2-42d4-b254-8a73b6fa3e4a)
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/b9b9847a-5a18-4e31-bc5b-edbf9b939e01)

* 마지막으로 재부팅 해주면 된다.
![image](https://github.com/dgjinsu/AWS-study/assets/97269799/db263cee-5280-4cf7-b36b-78d47d72415f)





## 다운 받은 키 페어를 통해 ssh 서버에 접속 (ec2에 접속)

* putty 설치
* puttyGen 도 함께 설치되는데 여기서 .pem 파일을 .ppk 파일로 바꿀 수 있다.
* load 를 클릭하고 .pem 파일을 선택한 후 save private key 버튼을 클릭하면 .ppk 파일로 바꿀 수 있다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/78ffe8d9-b5cc-4f70-a2f7-b3d7af4bdcd9)

* putty 를 실행하고 HostName에 ubuntu@{탄력적IP주소} 를 입력한다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/32c84ff0-e986-405a-8163-95d03af3591e)

* SSH -> Auth -> Credentials 에 들어가 아까 만든 ppk 파일을 올려주고 open 을 누른다. 

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/ecd0a1eb-343d-4620-a470-ccdd5b661d8f)

* 이로써 키-페어로 ec2에 연결 완료했다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/c9ef462f-0f52-4e7d-b2bd-702337ed0b3c)


## 가상 컴퓨터에 환경 세팅

* 서버 패키지와 리포지토리를 최신 상태로 업데이트 해준다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/051325a8-3c7b-4995-9861-40b51191f0b5)

* 자바 17 설치

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/3ca61ab4-b13b-4d42-82a9-854412181177)

* 설치 후 버전 확인

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/3e55ad49-5b9c-4106-85fc-b16d500759a0)

* 프로젝트가 있는 git에서 clone 한 후 build 해준다.
* EC2 프리티어는 인스턴스 유형을 t2.micro밖에 사용할 수 없고, 해당 유형의 스펙은 싱글 코어 및 1GB의 RAM이므로 java 빌드를 수행할 때 테스트는 생략하는 것이 좋다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/ac76de3a-56b1-4d8d-bc87-5c7e99abaf55)

* 메모리의 부족한 부분을 채워줄 수 있다. 디스크의 일부를 대신 사용하도록 설정해줌 으로써 해결 가능하다. 이를 메모리 스왑이라고 한다.
* 아래와 같이 명령어를 입력해주면 스왑 메모리가 2GB 잡혀서 메모리 부족으로 빌드가 멈추는 현상은 사라지지만, 디스크는 RAM 보다 훨씬 속도가 느리기 때문에 서비스에 퍼포먼스 문제가 발생할 있다. 따라서 이 방법은 임시방편으로 쓰고 사양을 올려야 한다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/197ae576-7466-4988-9a18-97e1b4999fd4)

* 아래 명령어로 스왑 메모리를 해제할 수 있다.

![image](https://github.com/dgjinsu/AWS-study/assets/97269799/5ec0cc89-b74d-4228-8da9-d76340e9ba8b)




