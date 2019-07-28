package local.logger.log4j

import local.Local
import local.LocalLogger
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@PublishedApi internal val logger: Logger = LogManager.getLogger(Local::class.java)

inline val LocalLogger.Companion.Log4j: LocalLogger
    get() = LocalLogger {
        logger.debug(it)
    }
