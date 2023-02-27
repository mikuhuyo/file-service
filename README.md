# 文件服务

[![GitHub license](https://img.shields.io/github/license/mikuhuyo/file-service)](https://github.com/mikuhuyo/file-service/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/mikuhuyo/file-service)](https://github.com/mikuhuyo/file-service/issues)
[![GitHub stars](https://img.shields.io/github/stars/mikuhuyo/file-service)](https://github.com/mikuhuyo/file-service/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/mikuhuyo/file-service)](https://github.com/mikuhuyo/file-service/network)
![Java version](https://img.shields.io/badge/Jdk-11-yellow)
![SpringBoot version](https://img.shields.io/badge/SpringBoot-2.3.12.RELEASE-brightgreen)

> http://127.0.0.1:51000/swagger-ui.html

## 启动

```yaml
version: "3.7"
services:
  minio:
    image: minio/minio:RELEASE.2022-08-02T23-59-16Z
    privileged: true
    container_name: minio
    ports:
      - "9000:9000"
      - "9001:9001"
    volumes:
      - /root/docker/minio/data1:/data1
      - /root/docker/minio/data2:/data2
    command: server --console-address ":9001" http://minio/data{1...2}
    environment:
      - MINIO_ROOT_USER=root
      - MINIO_ROOT_PASSWORD=yueliminvc@outlook.com
      #- MINIO_ACCESS_KEY=AKIAIOSFODNN7EXAMPLE
      #- MINIO_SECRET_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:9000/minio/health/live"]
      interval: 30s
      timeout: 20s
      retries: 3
```

> docker-compose up -d

## 特别鸣谢

### 关注者

[![Stargazers repo roster for @mikuhuyo/file-service](https://reporoster.com/stars/mikuhuyo/file-service)](https://github.com/mikuhuyo/file-service/stargazers)

### 收藏者

[![Forkers repo roster for @mikuhuyo/file-service](https://reporoster.com/forks/mikuhuyo/file-service)](https://github.com/mikuhuyo/file-service/network/members)

## 请这个b喝杯水?

![Alipay](./image/alipays.png)

---

![WeChatPay](./image/wechats.png)
