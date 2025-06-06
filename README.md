# Restate Demo


> <span style="color: brown">⚠️ **The system was mainly tested on Windows. If you use other operating system most probably 
> you have to change some things. E.g. the handling of quote characters in the command line.**</span>

## <span style="color: limegreen">Start</span>

<span style="color: brown">**CHECK this dependency**</span>: on Mac OS uncomment, otherwise comment it

```kotlin
// Only for Mac OS
// runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.2.1.Final:osx-x86_64")
```

```shell
gradlew clean jibDockerBuild
docker compose up -d
curl localhost:9070/deployments --json "{\"uri\": \"http://transaction-service:9080\"}"
curl localhost:9070/deployments --json "{\"uri\": \"http://stats-service:9080\"}"
```

## <span style="color: limegreen">Try</span>

### Valid request

```shell
curl -X POST localhost:9000/transaction/fee -H "content-type: application/json" -d "{ \"transactionId\": \"ID_BOB\", \"amount\": \"10000\", \"asset\": \"USD\",\"assetType\": \"FIAT\" }"
```

### Bad request

```shell
curl -X POST localhost:9000/transaction/fee -H "content-type: application/json" -d "{ \"transactionId\": \"ID_BOB\", \"amount\": \"10000\", \"asset\": \"USD\",\"assetType\": \"XXXX\" }"
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

## <span style="color: limegreen">Useful stuff</span>

- https://docs.restate.dev/get_started/quickstart/
- https://docs.restate.dev/get_started/tour?sdk=java
- https://github.com/restatedev/examples/tree/main/java/tutorials/tour-of-restate-java
