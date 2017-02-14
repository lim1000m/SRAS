#SRAS
REDIS+MONGODB를 활용한 대용량 메세징 부하 처리 테스트 배드

#기능
1. Redis에서 전송된 Mongo Db 메시지 조회 
2. Redis에 메세지 생성 및 저장과 Mongo Db로의 업로드
3. Mongo Db에 저장된 메시지 검색
의 기능을 통해 대용량 처리 시 소요되는 시간 분석 

#구성
1. Redis와 Mongo Db의 각각의 연결 드라이버 사용
2. Apache pool과 Bson Document 라이브러리를 사용
3. Swing Ui로 시각화 구성

#실행
1. jar 익스포트를 통해 실행
2. Property 파일에 연결정보 설정 후 실행
