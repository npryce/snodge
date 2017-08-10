package com.natpryce.xmlk


private val simpleExample = """<?xml version="1.0" encoding="UTF-8"?>
<hello>
    <who>world</who>
    <who>your mum</who>
</hello>"""

private val attributes = """<?xml version="1.0" encoding="UTF-8"?>
<hello xml:lang="en_GB">
    <who id="1">world</who>
    <who id="2">your mum</who>
</hello>"""

private val comments = """<?xml version="1.0" encoding="UTF-8"?>
<!-- comment before root -->
<hello>
    <!-- comment first element of root -->
    <who><!-- comment -->world<!-- comment --></who>
    <who>your mum</who>
    <who>fancy <!-- comment --> pants</who>
</hello>
<!-- comment after the document element -->"""

private val entities = """<?xml version="1.0" encoding="UTF-8"?>
<hello>
    <who>&lt;world&gt;</who>
</hello>"""

private val namespaces = """<?xml version="1.0" encoding="UTF-8"?>
<x:hello xmlns:x="http://example.com/x">
    <x:who a:what="zzz" xmlns:a="http://example.com/a">world</x:who>
    <y:who xmlns:y="http://example.com/y">your mum</y:who>
    <who>your mum</who>
</x:hello>"""

private val noXmlDeclaration = """<hello>
    <who>world</who>
    <who>your mum</who>
</hello>"""

private val processingInstructions = """<?xml version="1.0"?>
<root>
    <!-- note: the Java XML library writes invalid XML if there is a processing instruction before the root! -->
    <?alice one two three="bar"?>
    <child>
        <?bob?>
        <?carol a="1"?>
        <?carol a="2"?>
    </child>
    <?dave xxx?>
</root>
<?eve?>"""

object ExampleXmlFiles {
    private val examples = mapOf(
        "simple-example.xml" to simpleExample,
        "attributes.xml" to attributes,
        "comments.xml" to comments,
        "entities.xml" to entities,
        "namespaces.xml" to namespaces,
        "no-xml-declaration.xml" to noXmlDeclaration,
        "processing-instructions.xml" to processingInstructions
    )
    
    fun list() = examples.keys
    
    fun forEach(block: (String, XmlDocument) -> Unit) =
        list().forEach { name -> block(name, get(name)) }
    
    fun forEachText(block: (String, String) -> Unit) =
        list().forEach { name -> block(name, getText(name)) }
    
    private fun getText(name: String): String =
        examples[name] ?: throw IllegalArgumentException("example $name not found")
    
    private fun get(name: String): XmlDocument =
        getText(name).toXmlDocument()
}
