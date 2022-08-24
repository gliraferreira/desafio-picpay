package com.picpay.desafio.android.testcore

import java.util.*
import kotlin.random.Random

object RandomUtil {

    fun uuid() = StringGenerator.uuid()

    fun string() = StringGenerator.words()

    fun email() = StringGenerator.email()

    fun url() = StringGenerator.url()

    fun name() = StringGenerator.name()

    fun firstName() = StringGenerator.firstName()

    fun lastName() = StringGenerator.lastName()

    fun word() = StringGenerator.word()

    fun word(notIn: Set<String>) = StringGenerator.word(notIn)

    fun words() = StringGenerator.words()

    fun sentence() = StringGenerator.sentence()

    fun sentences() = StringGenerator.sentences()

    fun paragraphs() = StringGenerator.paragraphs()

    fun paragraph() = StringGenerator.paragraph()

    fun boolean() = Math.random() < 0.5

    fun monthNumber() = int(min = 1, max = 12)

    fun yearNumber() = int(min = 2000, max = 2030)

    fun double(
        min: Double = 1.0,
        max: Double = 50.0,
    ) = Random.nextDouble(min, max)

    fun bigDecimal() = double().toBigDecimal()

    fun float(
        min: Float = 1f,
        max: Float = 50f,
    ) = double(min.toDouble(), max.toDouble()).toFloat()

    fun int(
        min: Int = 1,
        max: Int = 5,
    ) = Random.nextInt(min, max)

    fun int(interval: IntRange) = Random.nextInt(interval.first, interval.last)

    fun long(
        min: Long = 1L,
        max: Long = 50L,
    ) = Random.nextLong(min, max)

    fun <T : Enum<*>> enum(clazz: Class<T>, exceptions: List<T> = emptyList()): T {
        val result = clazz.enumConstants!!.random()
        return if (result !in exceptions) result else enum(clazz, exceptions = exceptions)
    }

    fun phoneNumber(
        separator: String = "-",
    ): String {
        val first = int(min = 1000, max = 9999)
        val second = int(min = 1000, max = 9999)

        return "$first$separator$second"
    }

    fun creditCard(
        separator: String = "",
    ): String {
        val first = int(min = 1000, max = 9999)
        val second = int(min = 1000, max = 9999)
        val third = int(min = 1000, max = 9999)
        val fourth = int(min = 1000, max = 9999)

        return "$first$separator$second$separator$third$separator$fourth"
    }

    fun calendar(
        year: Int = int(1900, 2020),
        month: Int = int(1, 12),
        date: Int = int(1, 30),
        hourOfDay: Int = int(0, 23),
        minute: Int = int(0, 59),
        second: Int = int(0, 59),
        timeZone: TimeZone = TimeZone.getDefault(),
    ): Calendar = Calendar.getInstance(timeZone).apply {
        set(year, month, date, hourOfDay, minute, second)
        set(Calendar.MILLISECOND, 0)
    }

    fun gregorianCalendar(
        year: Int = int(1900, 2020),
        month: Int = int(1, 12),
        date: Int = int(1, 30),
        hourOfDay: Int = int(0, 23),
        minute: Int = int(0, 59),
        second: Int = int(0, 59),
        dayOfWeek: Int? = null,
    ) = GregorianCalendar(year, month, date, hourOfDay, minute, second).apply {
        set(Calendar.MILLISECOND, 0)
        dayOfWeek?.let { set(Calendar.DAY_OF_WEEK, dayOfWeek) }
    }

    fun date(
        year: Int = int(1900, 2020),
        month: Int = int(1, 12),
        date: Int = int(1, 30),
        hourOfDay: Int = int(0, 23),
        minute: Int = int(0, 59),
        second: Int = int(0, 59),
    ): Date = calendar(year, month, date, hourOfDay, minute, second).time

    fun stringList(quantity: Int = int()) = listOf(quantity) { words() }

    fun uuidList(quantity: Int = int()) = listOf(quantity) { uuid() }

    fun stringMap(quantity: Int = int()) = mapOf(quantity) { words() to words() }

    fun bigDecimalMap(quantity: Int = int()) = mapOf(quantity) { words() to bigDecimal() }

    fun intMap(quantity: Int = int()) = mapOf(quantity) { words() to int() }

    fun doubleList(quantity: Int = int()) = listOf(quantity) { double() }

    fun <T> listOf(
        quantity: Int = int(max = 3),
        block: () -> T,
    ) = mutableListOf<T>().apply {
        repeat(quantity) { add(block()) }
    }

    fun <L, R> mapOf(
        quantity: Int = int(max = 3),
        block: () -> Pair<L, R>,
    ) = mutableMapOf<L, R>().apply {
        repeat(quantity) {
            val entry = block()
            put(entry.first, entry.second)
        }
    }

    fun <T> setOf(
        quantity: Int = int(max = 3),
        block: () -> T,
    ) = mutableSetOf<T>().apply {
        while (size < quantity) {
            add(block())
        }
    }

    fun <T> oneOf(vararg items: T) = oneOf(items.toList())

    fun <T> oneOf(items: List<T>) = items[items.indices.random()]
}