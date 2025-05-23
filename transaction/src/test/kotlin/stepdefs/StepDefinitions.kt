package stepdefs

import io.cucumber.datatable.DataTable
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import ivan.dto.rest.FeeRequest
import ivan.transaction.service.RateCalculator
import org.junit.Assert.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import java.math.BigDecimal


class StepDefinitions {

    private val rateCalculator: RateCalculator by inject(RateCalculator::class.java)

    // Pair(expected, actual)
    private var results = setOf<Pair<BigDecimal, BigDecimal>>()

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Given("the RateCalculator is initialized")
    fun `the endpoint is up`() {
        // do nothing
    }

    @When("the RateCalculator is called with the following requests:")
    fun `the RateCalculator is called with the following transactions`(dataTable: DataTable) {
        val feeRequests = dataTable.asMaps(String::class.java, String::class.java).map { row ->
            Pair(FeeRequest(
                    transactionId = row["transaction_id"] ?: "",
                    amount = BigDecimal(row["amount"]),
                    asset = row["asset"] as String
                ),
                BigDecimal(row["expected_rate"])
            )
        }

        feeRequests.forEach { feeRequest ->
            val actualRate = rateCalculator.calculate(feeRequest.first)
            results += feeRequest.second to actualRate
        }
    }

    @Then("^it returns the correct rates for all requests$")
    fun `it returns the correct rates for all transactions`() {
        results.forEach {
            assertEquals(it.first, it.second)
        }
    }

}