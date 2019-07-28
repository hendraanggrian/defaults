package local.logger.slf4j

import local.Local
import local.LocalLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@PublishedApi internal val logger: Logger = LoggerFactory.getLogger(Local::class.java)

inline val LocalLogger.Companion.SLF4J: LocalLogger
    get() = LocalLogger {
        logger.debug(it)
    }
