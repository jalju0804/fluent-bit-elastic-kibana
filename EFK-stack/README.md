# EFK stack

개인적으로 사용해서 사용하려고 작성한 EFK docker-compose 및 환경 파일입니다.

Includes:

- Elasticsearch
- Fluentd
- Kibana

## Introduction

스타트업 몇 군데서 일을 하다보니, 모니터링 시스템이 잘 구축된 곳에 가면 제가 직접 구축할 필요가 없었지만 없는 곳이라면 언제 만들어질지 모르는 일이 많았습니다.
UI, API 테스트 자동화 구축을 할 때에도 필요하기도 하고, docker-compose 파일을 작성해두면 언제 어디서든지 편하지 않을까? 해서 작성하고 업로드 해놓기로 했습니다.

Fluentd가 설치된 곳의 log 파일 및 http input으로 들어온 데이터를 elasticsearch에 저장을 하고, kibana를 통해서 보여주는 EFK Stack입니다.

## Launching the EFK stack

### Requirements

- Docker
- Docker Compose

### Run

```bash
cd /path/to/project
docker-compose up
```

위의 명령어 실행후 모든 docker가 띄어져있으면, kibana(http://localhost:5601) 로 접근해서 elasticsearch와 연결해주시면 됩니다.

저는 manual connect를 선택해서 http://es01:9200 으로 연결했습니다.

Please note: 모든 포트는 기본 포트를 사용하고 있습니다. dockercompose 파일에 포트 설정 부분을 확인해주시면 됩니다.

### Testing with sample data

docker-compose를 통해서 설치가 완료되면 fluentd가 설치된 도커의 /var/log/%Y-%m-%d.log 형태의 로그 파일과 http로 요청이 들어온 내용을 kibana에서 확인이 가능합니다.

- 로그 파일로 저장하는 방식(fluentd docker 안에서 명령어를 입력해줍니다.)

```bash
echo '{"hello":"world"}' | tee -a /var/log/2023-12-10.log
```
- http input을 통해 전달하는 방식(fluentd http input port인 9880으로 요청, windows cmd에서는 \를 더블 쿼터 앞에 넣어주세요.)

```bash
curl -X POST -d {\"foo\":\"bar\"} http://localhost:9880/api.log
```
