# API Adapters

Snodge defines its own object model for JSON and XML.  But you'll most likely want to use it with whatever API you use in your application.  To help with that, Snodge provides _API Adapters_, extension methods that turn a Mutagen of Snodge's object model into a Mutagen of an API's object model.

If the API is on the classpath, you can call the extension function. If it is not, the call to the extension function won't compile, so you won't accidentally get errors that only occur at runtime.

If your favourite API is not supported let us know. Until it is, you can fall back on mutating data serialised as strings.


## JSON API Adapters:

Import `com.natpryce.snodge.json.forXxx` (where _forXxx_ is the API adapter you want to use).

[Argo](http://argo.sourceforge.net/): `Mutagen<JsonElement>.forArgo()`

[GSON](https://github.com/google/gson): `Mutagen<JsonElement>.forGson()`

[Jackson](https://github.com/FasterXML/jackson): `Mutagen<JsonElement>.forJackson()`

[JSR-374 JSONP](http://docs.oracle.com/middleware/1213/wls/WLPRG/java-api-for-json-proc.htm): `Mutagen<JsonElement>.forJsonp()`

JSON serialised as a string: `Mutagen<JsonElement>.forStrings()`

## XML API Adapters

Import `com.natpryce.snodge.xml.forXxx` (where _forXxx_ is the API adapter you want to use).

[W3C DOM](https://www.w3.org/DOM/): `Mutagen<XmlDocument>.forDOM()`

XML serialised as a string: `Mutagen<XmlDocument>.forStrings()`
