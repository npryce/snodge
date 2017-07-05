package com.natpryce.snodge.demo

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.Random
import com.natpryce.snodge.json.defaultJsonMutagens
import com.natpryce.snodge.json.forStrings
import com.natpryce.snodge.mutant
import com.natpryce.snodge.text.replaceWithPossiblyMeaningfulText
import com.natpryce.snodge.text.splice
import com.natpryce.snodge.xml.defaultXmlMutagens
import com.natpryce.snodge.xml.forStrings
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass
import kotlin.dom.removeClass

external class SyntaxError : Throwable

val random = Random()
val originalTextArea = document.getElementById("original") as HTMLTextAreaElement
val mutantTextArea = document.getElementById("mutant") as HTMLTextAreaElement
val inputTypeSelector = document.getElementById("type") as HTMLSelectElement

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

fun selectedMutagen(): Mutagen<String> = inputTypeSelector.value.let {
    when(it) {
        "text" -> splice(replaceWithPossiblyMeaningfulText())
        "json" -> defaultJsonMutagens().forStrings()
        "xml" -> defaultXmlMutagens().forStrings()
        else -> throw IllegalStateException("unrecognised input type: $it")
    }
}

private fun mutate() {
    val originalText = originalTextArea.value
    try {
        val mutantText = random.mutant(selectedMutagen(), originalText)
        mutantTextArea.value = mutantText
        clearError()
    }
    catch (e: SyntaxError) {
        reportError(e.message ?: "could not parse input")
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
