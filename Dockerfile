#===== 1단계: 빌드 (소스코드를 실행 파일로 만들기) =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
#윈도우에서 만든 파일의 줄바꿈 문제 방지 + 실행 권한 부여
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
#테스트는 건너뛰고 jar 파일 생성
RUN ./gradlew bootJar -x test --no-daemon
RUN cp $(ls build/libs/*.jar | grep -v plain | head -1) app.jar

#===== 2단계: 실행 (완성된 것만 가져와서 돌리기) =====
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/app.jar app.jar
#상품 이미지 폴더도 같이 가져오기
COPY --from=build /app/images /app/images
#무료 서버는 메모리가 작아서 사용량 제한
ENTRYPOINT ["java", "-Xmx380m", "-jar", "app.jar"]