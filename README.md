# MOTD

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https://github.com/BanseokSuh/motd&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

<br>

## 프로젝트 소개

MOTD는 <b>Miracle On ThursDay</b>의 약자로, 목요일마다 작은 기적을 통해 더 나은 세상을 만들기 위한 사람들의 커뮤니티 서비스입니다.

(2024.08.17 기준) <br>
아직 프로젝트에 대한 기획이 준비 중이기에, 아래의 기본적인 기능들만 구현되어 있습니다.<br>
지속적으로 기능 추가, 리팩토링 및 고도화가 진행될 예정입니다.<br>
- 회원가입/로그인/회원탈퇴
- 게시글 조회/작성/수정/삭제
- 게시글 상세 조회
  - 댓글 목록 조회 (댓글 작성자, 작성일 포함)
  - 좋아요 누른 사용자 목록 조회
- 댓글 작성
- 좋아요
- 실시간 알림 (댓글 작성, 좋아요 시 작성자에게 실시간 메시지 발송)
- 이메일 비동기 발송
- Logback을 이용한 로그 관리
  - Profile에 따른 로그 레벨 설정

<br>

## 기술 스택

<div align=center> 
  <img src="https://img.shields.io/badge/java 17-007396?style=for-the-badge&logo=java&logoColor=white" alt=""> 
  <br>
  <img src="https://img.shields.io/badge/springboot 3.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="">
  <br>
  <img src="https://img.shields.io/badge/SPRING DATA JPA-68BC71?style=for-the-badge&logo=null&logoColor=white" alt="">  
  <img src="https://img.shields.io/badge/QUERYDSL-004088?style=for-the-badge&logo=null&logoColor=white" alt="">
  <br>
  <img src="https://img.shields.io/badge/postgresql-4479A1?style=for-the-badge&logo=postgresql&logoColor=white" alt=""> 
  <img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white" alt=""> 
<br>
  <img src="https://img.shields.io/badge/APACHE KAFKA-231F20?style=for-the-badge&logo=apachekafka&logoColor=white" alt="">
  <br>
  <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt=""> 
  <br>
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white" alt="">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white" alt="">
  <br>
</div>

<br>

## ERD

<img src="https://github.com/user-attachments/assets/20e94730-d6e7-48e3-9c6f-374529f02445" alt="">

<br>

## Architecture

### Project Architecture

![Simple_Architecture](https://github.com/user-attachments/assets/98e5f702-a248-4139-86bf-41d03cc2555e)

### Alarm using Kafka

![Alarm_Kafka](https://github.com/user-attachments/assets/fa17734c-f3d7-45b0-a07a-d5c5f25b5c89)

### Join

![Join](https://github.com/user-attachments/assets/90d41f08-5d4c-464f-94ab-964782a2b35b)

- [Architecture 더보기](https://github.com/BanseokSuh/motd/wiki)

<br>

## API 문서

- [MOTD API Document](https://documenter.getpostman.com/view/10226658/2sA3kaBdxn)

<br>

## 주요 기능

- 로그인/회원가입 - Spring Security를 이용한 JWT 토큰 기반 인증
- Redis를 이용한 유저 정보 caching
- Redis를 Session storage로 사용하여 세션 관리
- 게시글 조회/작성/수정/삭제
- 게시글 상세 조회
  - 댓글 목록 조회 (댓글 작성자, 작성일 포함)
  - 좋아요 목록 조회 (좋아요 작성자, 작성일 포함)
- 댓글 작성
- 좋아요 기능
- 이메일 비동기 발송
  - Thymeleaf를 이용한 이메일 템플릿 작성
- SSE/Kafka를 이용한 실시간 알림

<br>

## 트러블슈팅

- [트러블슈팅](https://github.com/BanseokSuh/motd/wiki/%5BTroubleShooting%5D-troubleshooting-1)

<br>

## 시작 가이드

준비중입니다.

<br>

## 


