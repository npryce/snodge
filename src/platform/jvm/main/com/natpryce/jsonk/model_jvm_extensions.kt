package com.natpryce.jsonk

import java.math.BigDecimal

fun JsonNumber.toBigDecimal() = BigDecimal(valueAsString)
