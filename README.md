# Spring Boot 커뮤니티 프로젝트
## Spring Boot와 MyBatis, MySQL로 만든 프로젝트입니다



<div><h2>📚 기술스택</h2></div>
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
  <br/>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
  <img src="https://img.shields.io/badge/lombok-E34F26?style=for-the-badge&logo=lombok&logoColor=white">
</div>


<div><h2>데이터베이스 테이블</h2></div>

```
CREATE TABLE member(
	idx int NOT NULL AUTO_INCREMENT PRIMARY KEY, # 회원 고유 번호
	email VARCHAR(50) NOT NULL, # 회원 이메일
	passwd VARCHAR(255), # 계정 비밀번호
	nick VARCHAR(12) NOT NULL, # 회원 닉네임
	role VARCHAR(20) NOT NULL, # 회원 권한
	provider VARCHAR(50), # OAuth2회원가입 시 회원 정보를 전달해주는 사이트(Google, Naver)
	providerId VARCHAR(255), # OAuth2회원가입 시 생성되는 회원의 고유 id
	regdate DATETIME # 가입일자
);

CREATE TABLE post(
	pidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY, # 게시글 고유 번호
	idx INT, # 게시글 작성자(회원)의 고유 번호
	title VARCHAR(200) NOT NULL, # 게시글 제목
	writer VARCHAR(20) NOT NULL, # 게시글 작성자(회원 닉네임)
	category VARCHAR(20) NOT NULL, # 게시글이 속한 카테고리
	content TEXT NOT NULL, # 게시글 내용
	replyCount INT, # 게시글의 댓글 수
	hit INT, # 게시글 조회수
	heartCount INT, # 게시글 추천 수
	regdate DATETIME, # 게시글 작성일자
	FOREIGN KEY(idx) REFERENCES member(idx)
);

CREATE TABLE reply(
	ridx INT NOT NULL AUTO_INCREMENT, # 댓글 고유 번호
	pidx INT NOT NULL, # 댓글이 속한 게시글의 고유 번호
	idx INT NOT NULL, # 댓글을 작성한 작성자의 고유 번호
	reRidxNum INT, #대댓글이 속한 댓글의 번호(ridx)
	ridxDepth INT NOT NULL, # 대댓글의 깊이 
	replyWriter VARCHAR(12) NOT NULL, # 댓글 작성자의 닉네임 
	replyContent TEXT NOT NULL, # 댓글 내용
	originReplyWriter VARCHAR(12),
	regdate DATETIME, # 댓글 작성 시간
	PRIMARY KEY(ridx),
	FOREIGN KEY(pidx) REFERENCES post(pidx) ON DELETE CASCADE
);

CREATE TABLE heart(
	hidx INT NOT NULL AUTO_INCREMENT, # 게시물 추천의 고유 번호
	pidx INT NOT NULL, # 추천을 누른 게시물의 고유 번호
	idx INT NOT NULL, # 추천을 누른 회원의 고유 번호
	PRIMARY KEY(hidx),
	FOREIGN KEY(pidx) REFERENCES post(pidx) ON DELETE CASCADE,
	FOREIGN KEY(idx) REFERENCES member(idx)
);

CREATE TABLE notice(
	nidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY, # 공지글 고유 번호
	title VARCHAR(200) NOT NULL, # 공지글 제목
	writer VARCHAR(12) NOT NULL, # 공지글 작성자(관리자)
	content TEXT NOT NULL, # 공지글 내용
	regdate datetime # 공지글 작성일자
);

```

<div><h2>프로젝트 정보</h2></div>

> 개발기간: 2023.03.20 ~ 2023.07.26 

> 개발인원: 1명


<div><h2>주요 기능</h2></div>

<h4>🤍 소셜 로그인 기능</h4>
* OAuth2를 이용한 소셜 로그인(Google, Naver)으로 간편로그인을 할 수 있습니다
<h4>🤍 댓글, 대댓글 작성 기능</h4>
* 댓글과 대댓글을 통한 회원간 소통이 가능합니다
<h4>🤍 게시글 추천 기능</h4>
* 마음에 드는 글을 추천할 수 있으며, 추천수가 많은 게시글 순으로 메인화면에 보여집니다
<h4>🤍 마이 페이지</h4>
* 회원이 작성한 게시글/댓글 관리와 회원정보를 수정할 수 있습니다
<h4>🤍 관리자 페이지</h4>
* 관리자 로그인 시 전체 게시글/댓글/회원 관리를 할 수 있으며, 공지사항을 작성할 수 있습니다 

<div><h2>테스트 영상</h2></div>
 ※ 해당 프로젝트의 post(게시물)테이블에는 더미데이터가 들어있습니다.
 ※ 동영상 중간에 css오류로 개행 관련 코드가 제대로 동작하지 않은 점 양해 부탁드립니다.
    (현재는 수정이 완료되었습니다)

<p align="center">
  <img src="https://github.com/PirateOfUniverse/community/assets/127758745/8449ac9d-f782-4b2c-8231-6e0fd103a010">
</p>

<p>테스트영상 링크</p>
https://www.youtube.com/watch?v=9Kj1-SHisPA&list=PLYUajYPwcwJAZXy1IwvQXnZzKHM79Vs4J








 
 
