package ivan.stats.service

import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.kotlin.Context
import ivan.dto.rest.FeeRequest
import ivan.stats.Config
import org.slf4j.LoggerFactory

@Service
class StatsService(private val cofig: Config) {

    private val logger = LoggerFactory.getLogger("stats")

    @Handler
    fun store(ctx: Context, feeRequest: FeeRequest) {
        logger.info("${cofig.logMessage}: {}", feeRequest)
    }

}