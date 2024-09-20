#!/usr/bin/bash

##登录测试 docker 仓库
CI_REGISTRY_TEST=registry.cn-hangzhou.aliyuncs.com
CI_PROJECT_PATH=xiuyuan-fenger
BUILD_VERSION=24.9.10

docker login -u 15934729199 -p FengYe@233 $CI_REGISTRY_TEST

###################################### blog-manager ##############################################
 service_name="wind"
  {
  echo "docker build $CI_REGISTRY_TEST/$CI_PROJECT_PATH/${service_name}:$BUILD_VERSION"
  docker build . -t "$CI_REGISTRY_TEST/$CI_PROJECT_PATH/${service_name}:$BUILD_VERSION" -f service/Dockerfile &&
  if [ $? -ne 0 ]; then
     export errormsg="$errormsg \n ${service_name}像构建失败"
  fi
 docker push "$CI_REGISTRY_TEST/$CI_PROJECT_PATH/${service_name}:$BUILD_VERSION"
   if [ $? -ne 0 ]; then
     export errormsg="$errormsg \n ${service_name}像推送失败"
   fi
 }
wait

if [ -n "$errormsg" ]; then
  echo "构建失败： \n $errormsg"
  exit 1
fi

echo "镜像构建成功"
exit 0

