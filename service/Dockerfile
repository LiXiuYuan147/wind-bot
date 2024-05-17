FROM  openjdk:21

MAINTAINER kxw

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#容器中工作目录
WORKDIR /docker-work
#将本地jar包copy到容器根目录下
ADD  /wind-bot-service/target/wind-bot-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8000

CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.profiles.active=prod