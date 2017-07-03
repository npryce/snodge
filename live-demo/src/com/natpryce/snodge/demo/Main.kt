package com.natpryce.snodge.demo

import com.natpryce.snodge.Random
import com.natpryce.snodge.json.defaultJsonMutagens
import com.natpryce.snodge.json.forStrings
import com.natpryce.snodge.mutant
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTextAreaElement
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.removeClass

external class SyntaxError: Throwable

fun main(args: Array<String>) {
    val random = Random()
    
    val mutateButton = document.getElementById("mutate") as HTMLButtonElement
    val originalTextArea = document.getElementById("original") as HTMLTextAreaElement
    val mutantTextArea = document.getElementById("mutant") as HTMLTextAreaElement
    
    mutateButton.onclick = { onClickEvent ->
        val originalText = originalTextArea.value
        try {
            val mutantText = random.mutant(defaultJsonMutagens().forStrings(), originalText)
            mutantTextArea.value = mutantText
            clearError()
        }
        catch (e: SyntaxError) {
            e.message?.let(::reportError)
        }
        
        onClickEvent.preventDefault()
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
