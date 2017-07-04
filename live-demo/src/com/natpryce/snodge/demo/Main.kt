package com.natpryce.snodge.demo

import com.natpryce.snodge.Random
import com.natpryce.snodge.json.defaultJsonMutagens
import com.natpryce.snodge.json.forStrings
import com.natpryce.snodge.mutant
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTextAreaElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass
import kotlin.dom.removeClass

external class SyntaxError : Throwable

val random = Random()
val originalTextArea = document.getElementById("original") as HTMLTextAreaElement
val mutantTextArea = document.getElementById("mutant") as HTMLTextAreaElement

fun main(args: Array<String>) {
    val mutateButton = document.getElementById("mutate") as HTMLButtonElement
    
    var timer: Int? = null
    
    fun timerTick() {
        mutate()
        timer = window.setTimeout(timeout = 150, handler = ::timerTick)
    }
    
    mutateButton.onmousedown = { event ->
        timer = window.setTimeout(timeout = 500, handler = ::timerTick)
        event.preventDefault()
    }
    
    mutateButton.onmouseup = { event ->
        timer?.let { window.clearTimeout(it) }
        mutate()
        event.preventDefault()
    }
}

private fun mutate() {
    val originalText = originalTextArea.value
    try {
        val mutantText = random.mutant(defaultJsonMutagens().forStrings(), originalText)
        mutantTextArea.value = mutantText
        clearError()
    }
    catch (e: SyntaxError) {
        e.message?.let(::reportError)
    }
}

fun reportError(message: String) {
    val errorDialog = errorDialog()
    errorDialog.innerText = message
    errorDialog.addClass("active")
}

fun clearError() {
    val errorDialog = errorDialog()
    errorDialog.removeClass("active")
}

private fun errorDialog(): HTMLElement {
    val errorDialog = document.getElementById("error") as HTMLElement
    return errorDialog
}
