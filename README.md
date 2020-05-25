Pace Maker
============
### 개요
편입 도우미 어플 나만의 단어장 기능, 학교별 편입 기출문제, 푸시알림을 통한 일정관리 등 다양한 기능 제공 예정 
***
### 설계 방향
#### Mysql을 이용한 회원및 파일, 바탕이 되는 자료들을 관리
* php 또는 javaScript 를 이용해 로컬DB 를 서버로 올려 사용할 예정 
* ngrok을 통한 포트포워딩
* 서버DB Link: [서버DB 연결](http://nobles1030.cafe24.com/dbEditor/)
#### 푸시 알림을 위한 설계
* fcm 사용
* 생성된 Token값은 php를 이용해 자동으로 서버DB로 올리도록 설계
## Xampp 혹은 Bitnami 사용시 해당 경로에 htdocs 폴더 안에 auto_send_notification.php파일 넣고 xampp 혹은 Bitnami 실행↓

## Xampp 가동

<img src="https://user-images.githubusercontent.com/48575996/72768989-80831500-3c3c-11ea-9c80-8dfd3e3bc606.png" width="80%"></img>  

## 올려놓은 .bat의 배치파일을 윈도우 스케줄러에 등록 기본작업만들기 -> 나머진 쉬움
