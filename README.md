# wanted-pre-onboarding-backend
## 지원자 : 김승철
## 애플리케이션 실행 방법
### 온라인에서 실행
이 애플리케이션은 AWS에 배포되어 있습니다.
배포 주소는 http://ec2-15-164-32-145.ap-northeast-2.compute.amazonaws.com:8080 입니다.
![chlwhd1](https://github.com/kim-ksp7331/wanted-pre-onboarding-backend/assets/119999208/1f3d4b94-f8f2-452a-8714-ad8175cdde75)

### 로컬에서 실행
1. 최상위 디렉토리에 .env 파일을 생성합니다.
2. .env파일에 다음 환경변수를 추가합니다. 등호 오른쪽에는 직접 값을 입력합니다. `JWT_SECRET_KEY`는 256bit 이상이어야 합니다.
 ```
 MYSQL_ROOT_PASSWORD=mysql 루트 비밀번호
 MYSQL_PASSWORD=mysql 일반 비밀번호
 JWT_SECRET_KEY=JWT 키
 ``` 
3. `docker compose up -d` 명령어로 실행합니다.
4. 도메인 주소는 http://localhost:8080 입니다.

## 데이터베이스 테이블 구조
![Untitled (6)](https://github.com/kim-ksp7331/wanted-pre-onboarding-backend/assets/119999208/502617af-1bd5-4157-bb29-926d83547f52)

## 구현 동작 및 설명
### API 동작 영상
[![Video Label](http://img.youtube.com/vi/Boa3V9bcn4w/0.jpg)](https://youtu.be/Boa3V9bcn4w)
### 부연 설명
#### 환경 구성
애플리케이션은 AWS EC2에 배포했습니다. 데이터베이스같은 경우 RDS를 사용할 경우 비용이 많이 발생할 것을 우려해서 EC2에 MySQL을 설치하여 사용했습니다. 
이때 MySQL을 설치하는 과정이 번거로울 수 있기 때문에 Docker를 이용하여 DB를 설치하고 WAS와 연결했습니다.
#### 기능 구현
로그인과 인증은 Spring Security와 JWT라이브러리를 이용해서 구현했습니다.
게시글 작성자를 구분하는 로직은 별도로 검증용 클래스를 만들어서(`PostAuthorizationManager`) 구현했습니다.
단위 테스트시에는 계층간의 분리를 위해 Mockito를 사용했습니다.
## API 명세서
### API 명세서 주소
https://documenter.getpostman.com/view/24282147/2s9Xy5NWvT#443ef854-2869-4f2e-b8db-29143d29f8b9
