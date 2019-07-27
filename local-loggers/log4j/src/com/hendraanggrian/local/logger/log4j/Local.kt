package com.hendraanggrian.local.logger.log4j

import com.hendraanggrian.local.Local
import com.hendraanggrian.local.LocalLogger
import org.apache.logging.log4j.LogManager

@PublishedApi internal val logger = LogManager.getLogger(Local::class.java)

inline val LocalLogger.Companion.Log4J: LocalLogger
    get() = LocalLogger {
        logger.debug(it)
    }