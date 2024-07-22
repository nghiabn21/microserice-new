FROM openjdk:17
#copy tất cả thư mục trong target lưu vào file app.jar trong container docker
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]