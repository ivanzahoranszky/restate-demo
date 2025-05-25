# <span style="color: blue">Restate Demo</span>

> <span style="color: brown">⚠️ **The <u>main</u> branch contains the original demo you requested. This branch (<u>advanced</u>) contains an improved version of the demo with 
> more functionality. However, it was not tested thoroughly because of lack of time.**</span>

> <span style="color: brown">⚠️ **The system was mainly tested on Windows. If you use other operating system most probably 
> you have to change some things. E.g. the handling of quote characters in the command line.**</span>


## <span style="color: limegreen">Requirement clarification</span>

**These questions should be asked from a real customer**:

- What are the rules for the fee calculation?
- Are the rules fix or they will be modified? How frequently?
- If they can be modified who will do it? The developers (new release) or the user?
- Do we have many rules or only a few? -> consider using a rule engine
- Are the rules complex or simple? -> consider using a rule engine
- Do we need to query the stored transactions later, or we just store it for audit purposes? -> used db type (SQL, noSQL) 
- If we need to do so what the queries are based on? Which fields? -> which columns need indexes?
- What is the expected response time?
- What is the expected max load on the interface?
- Do we expect peaks or the load is uniform? -> kubernetes, autoscaling
- What is the geographic location of the clients? -> multiple AWS regions
- Uptime?

# <span style="color: limegreen">The implemented (extended) use case</span>

1. The client sends a charge request (some customer is charged for something)
2. The payment service sends a request to the account service to update the balance
3. The payment service sends a request to the transaction service to calculate the fee and store the transaction
4. The payment service sends a request to the stats service to update the statistics
5. The response is sent back to the client



## <span style="color: limegreen">Start</span>

<span style="color: brown">**CHECK this dependency**</span>: on Mac OS uncomment, otherwise comment it

```kotlin
// Only for Mac OS
// runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.2.1.Final:osx-x86_64")
```

```shell
git clone git@github.com:ivanzahoranszky/restate-demo.git
cd restate-demo
git checkout advanced
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
curl -X POST localhost:9000/transaction/fee -H "content-type: application/json" -d "{ \"transactionId\": \"bbafe60b-455a-4e8c-a8cf-d3cbeb3af913\", \"amount\": \"10000\", \"asset\": \"XXX\",\"assetType\": \"FIAT\" }"
```

### Check the database

```shell
docker exec -it  restate-demo-postgres-1 bash
root@bb927eb7696a:/# psql -U ivan -d postgres
psql (13.21 (Debian 13.21-1.pgdg120+1))
Type "help" for help.

postgres=# SELECT * FROM transactionfees;
 id |            transaction_id            |  amount  | asset_type | asset
----+--------------------------------------+----------+------------+-------
 19 | bbafe60b-455a-4e8c-a8cf-d3cbeb3af913 | 10000.00 | FIAT       | USD
 20 | ed6de3c7-95e2-4df0-b786-15f1b954da1c |   200.00 | FIAT       | USD
(2 rows)
```

## <span style="color: limegreen">Stop</span>

```shell
docker compose down
```



## <span style="color: limegreen">Tests</span>

- The BDD tests are written in Gherkin under the **transaction/src/test/resources/features** folder.
- The BDD integration tests can be found in **restserver/src/test/resources/features** folder

```shell
docker compose down
gradlew clean test
```



## <span style="color: limegreen">Architecture</span>

### Overview

![restate.svg](restate.svg)

### Sequence diagram

![restate.svg](restate-sequence-diag.svg)

### Modules

- REST sever: exposes the REST interface
- Restate server
- payment service: handles payments and calls the 
  * the **account service**
  * the **transaction service**
  * and the **stats service**
- account service: stores account information (balance)
- transaction-service: calculates the transaction fees and calls the **stats service**
- stats-service: service that calculates and stores statistics regarding the transactions. This is 
an empty service to demonstrate the interservice communication.
- Postgres DB: stores the transactions
- Flyway: database migration tool to initialize the DB

## <span style="color: limegreen">Possible improvements</span>

- The DB for the account is a mock one (demo purposes). In memory and returns the same value for each customer.
- Compensation messages (saga pattern)
- Async DB driver (e.g. R2DBC)
- Improve test coverage
- Using mockito for simple unit tests
- Much more validation
- code cleanup (e.g. introduce constants for hardcoded strings and port numbers)
- better commit messages



## <span style="color: limegreen">Useful stuff</span>

- https://docs.restate.dev/get_started/quickstart/
- https://docs.restate.dev/get_started/tour?sdk=java
- https://github.com/restatedev/examples/tree/main/java/tutorials/tour-of-restate-java
