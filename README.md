# <span style="color: blue">Restate Demo</span>

> <span style="color: brown">⚠️ **The system was mainly tested on Windows. If you use other operating system most probably 
> you have to change some things. E.g. the handling of quote characters in the command line.**</span>



## <span style="color: limegreen">Start</span>

<span style="color: brown">**CHECK this dependency**</span>: on Mac OS uncomment, otherwise comment it

```kotlin
// Only for Mac OS
// runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.2.1.Final:osx-x86_64")
```

```shell
git clone git@github.com:ivanzahoranszky/restate-demo.git
cd restate-demo
gradlew clean jibDockerBuild
docker compose up -d
curl localhost:9070/deployments --json "{\"uri\": \"http://payment-service:9080\"}"
curl localhost:9070/deployments --json "{\"uri\": \"http://account-service:9080\"}"
curl localhost:9070/deployments --json "{\"uri\": \"http://transaction-service:9080\"}"
curl localhost:9070/deployments --json "{\"uri\": \"http://stats-service:9080\"}"
```



## <span style="color: limegreen">Try</span>

### Valid request

```shell
curl -X POST localhost:9000/transaction/fee -H "content-type: application/json" -d "{ \"transactionId\": \"bbafe60b-455a-4e8c-a8cf-d3cbeb3af913\", \"amount\": \"10000\", \"asset\": \"USD\",\"assetType\": \"FIAT\" }"
```

or

```shell
curl -X POST localhost:9000/payment/charge -H "content-type: application/json" -d "{ \"accountId\": \"ed6de3c7-95e2-4df0-b786-15f1b954da1c\", \"transactionId\": \"ed6de3c7-95e2-4df0-b786-15f1b954da1c\", \"amount\": \"200\" }"
````

### Bad request

```shell
curl -X POST localhost:9000/transaction/fee -H "content-type: application/json" -d "{ \"transactionId\": \"bbafe60b-455a-4e8c-a8cf-d3cbeb3af913\", \"amount\": \"10000\", \"asset\": \"USD\",\"assetType\": \"XXXX\" }"
```



## <span style="color: limegreen">Stop</span>

```shell
docker compose down
```



## <span style="color: limegreen">Tests</span>

- The BDD tests are written in Gherkin under the **transaction/src/test/resources/features** folder.
- The BDD integration tests can be found in **restserver/src/test/resources/features** folder

```shell
gradlew clean test
```



## <span style="color: limegreen">Architecture</span>

![restate.svg](restate.svg)



### Modules

- REST interface url: http://localhost:9000/transaction/fee
- REST sever: exposes the REST interface
- Restate server
- transaction-service: calculates the transaction fees and calls the **stats service**
- stats-service: service that calculates and stores statistics regarding the transactions. This is 
an empty service to demonstrate the interservice communication.
- Postgres DB: stores the transactions
- Flyway: database migration tool to initialize the DB

## <span style="color: limegreen">Possible improvements</span>

- Async DB driver (e.g. R2DBC)
- Improve test coverage
- Using mockito for simple unit tests

## <span style="color: limegreen">Useful stuff</span>

- https://docs.restate.dev/get_started/quickstart/
- https://docs.restate.dev/get_started/tour?sdk=java
- https://github.com/restatedev/examples/tree/main/java/tutorials/tour-of-restate-java
