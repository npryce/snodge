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

data class Format(
    val example: String,
    val mutagen: () -> Mutagen<String>
)

//language=JSON
val jsonExample = """{
  "demo": {
    "instructions": {
      "1": "press the button in the middle to mutate this JSON",
      "2": "a random mutation will appear in the right pane",
      "3": "press the button again to get another mutant",
      "4": "hold the button to repeatedly mutate this JSON",
      "5": "you can replace this with your own JSON",
      "6": "or mutate a different data format"
    },
    "uses": [
       "Robustness testing",
       "Security testing",
       "Negative testing",
       "Property-based testing"
    ]
  }
}"""

//language=XML
val xmlExample = """<demo>
  <instructions>
    <step n="1">press the button in the middle to mutate this XML</step>
    <step n="2">a random mutation will appear in the right pane</step>
    <step n="3">press the button again to get another mutant</step>
    <step n="4">hold the button to repeatedly mutate this XML</step>
    <step n="5">you can replace this with your own XML</step>
    <step n="6">or mutate a different data format</step>
  </instructions>
  <uses>
    <use>Robustness testing</use>
    <use>Security testing</use>
    <use>Negative testing</use>
    <use>Property-based testing</use>
  </uses>
</demo>"""

val textExample = """
Demo
====

Instructions:

1. press the button in the middle to mutate this text.
2. a random mutation will appear in the right pane.
3. press the button again to get another mutant.
4. hold the button to repeatedly mutate this text.
5. you can replace this with your own text.
6. or mutate a different data format.

Uses:

* Robustness testing
* Security testing
* Negative testing
* Property-based testing
"""

val dataTypes = mapOf(
    "text" to Format(
        example = textExample,
        mutagen = { splice(replaceWithPossiblyMeaningfulText()) }),
    "xml" to Format(
        example = xmlExample,
        mutagen = { defaultXmlMutagens().forStrings() }),
    "json" to Format(
        example = jsonExample,
        mutagen = { defaultJsonMutagens().forStrings() })
)


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
    
    showExampleOfSelectedType()
    inputTypeSelector.onchange = { _ ->
        showExampleOfSelectedType()
    }
}

fun  showExampleOfSelectedType() {
    originalTextArea.value = dataTypes[inputTypeSelector.value]?.example ?: ""
}

fun selectedMutagen(code: String): Mutagen<String> =
    when (code) {
        "text" -> splice(replaceWithPossiblyMeaningfulText())
        "json" -> defaultJsonMutagens().forStrings()
        "xml" -> defaultXmlMutagens().forStrings()
        else -> throw IllegalStateException("unrecognised input type: $code")
    }

private fun mutate() {
    val originalText = originalTextArea.value
    try {
        val mutantText = random.mutant(selectedMutagen(inputTypeSelector.value), originalText)
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
