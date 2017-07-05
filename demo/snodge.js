(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'snodge'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'snodge'.");
    }
    root.snodge = factory(typeof snodge === 'undefined' ? {} : snodge, kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var emptyMap = Kotlin.kotlin.collections.emptyMap_q3lmfv$;
  var Map = Kotlin.kotlin.collections.Map;
  var putAll = Kotlin.kotlin.collections.putAll_cweazw$;
  var linkedMapOf = Kotlin.kotlin.collections.linkedMapOf_qfcya0$;
  var plus = Kotlin.kotlin.collections.plus_e8164j$;
  var plus_0 = Kotlin.kotlin.collections.plus_z7hp2i$;
  var minus = Kotlin.kotlin.collections.minus_4pa84t$;
  var List = Kotlin.kotlin.collections.List;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var plus_1 = Kotlin.kotlin.collections.plus_qloxvw$;
  var plus_2 = Kotlin.kotlin.collections.plus_mydzjv$;
  var plus_3 = Kotlin.kotlin.collections.plus_drqvgf$;
  var toMutableList = Kotlin.kotlin.collections.toMutableList_4c7yge$;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var toDouble = Kotlin.kotlin.text.toDouble_pdl1vz$;
  var toLong = Kotlin.kotlin.text.toLong_pdl1vz$;
  var toInt = Kotlin.kotlin.text.toInt_pdl1vz$;
  var toList_0 = Kotlin.kotlin.collections.toList_us0mfu$;
  var asSequence = Kotlin.kotlin.collections.asSequence_7wnvza$;
  var flatMap = Kotlin.kotlin.sequences.flatMap_49vfel$;
  var emptySequence = Kotlin.kotlin.sequences.emptySequence_287e2$;
  var sequenceOf = Kotlin.kotlin.sequences.sequenceOf_i5x0yv$;
  var plus_4 = Kotlin.kotlin.sequences.plus_v0iwhp$;
  var asIterable = Kotlin.kotlin.collections.asIterable_us0mfu$;
  var lazyOf = Kotlin.kotlin.lazyOf_mh5how$;
  var map = Kotlin.kotlin.sequences.map_z5avom$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var first = Kotlin.kotlin.collections.first_2p1efm$;
  var sliceArray = Kotlin.kotlin.collections.sliceArray_dww5cs$;
  var toSet = Kotlin.kotlin.collections.toSet_7wnvza$;
  var minus_0 = Kotlin.kotlin.collections.minus_2ws7j4$;
  var to = Kotlin.kotlin.to_ujzrz7$;
  var generateSequence = Kotlin.kotlin.sequences.generateSequence_gexuht$;
  var filterNot = Kotlin.kotlin.sequences.filterNot_euau3h$;
  var first_0 = Kotlin.kotlin.sequences.first_veqyi0$;
  var listOf_0 = Kotlin.kotlin.collections.listOf_mh5how$;
  var get_indices = Kotlin.kotlin.collections.get_indices_gzk92b$;
  var toList_1 = Kotlin.kotlin.collections.toList_abgq59$;
  var Pair = Kotlin.kotlin.Pair;
  var asSequence_0 = Kotlin.kotlin.collections.asSequence_abgq59$;
  var withIndex = Kotlin.kotlin.sequences.withIndex_veqyi0$;
  var slice = Kotlin.kotlin.text.slice_fc3b62$;
  var DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var filter = Kotlin.kotlin.sequences.filter_euau3h$;
  var withIndex_0 = Kotlin.kotlin.collections.withIndex_7wnvza$;
  var single = Kotlin.kotlin.collections.single_2p1efm$;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  var json = Kotlin.kotlin.js.json_pyyo18$;
  var get_indices_0 = Kotlin.kotlin.collections.get_indices_tmsbgo$;
  var IllegalArgumentException = Kotlin.kotlin.IllegalArgumentException;
  var get_indices_1 = Kotlin.kotlin.collections.get_indices_964n91$;
  var downTo = Kotlin.kotlin.ranges.downTo_dqglrj$;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  JsonObject.prototype = Object.create(JsonElement.prototype);
  JsonObject.prototype.constructor = JsonObject;
  JsonArray.prototype = Object.create(JsonElement.prototype);
  JsonArray.prototype.constructor = JsonArray;
  JsonNumber.prototype = Object.create(JsonElement.prototype);
  JsonNumber.prototype.constructor = JsonNumber;
  JsonString.prototype = Object.create(JsonElement.prototype);
  JsonString.prototype.constructor = JsonString;
  JsonBoolean.prototype = Object.create(JsonElement.prototype);
  JsonBoolean.prototype.constructor = JsonBoolean;
  JsonNull.prototype = Object.create(JsonElement.prototype);
  JsonNull.prototype.constructor = JsonNull;
  XmlText.prototype = Object.create(XmlNode.prototype);
  XmlText.prototype.constructor = XmlText;
  XmlElement.prototype = Object.create(XmlNode.prototype);
  XmlElement.prototype.constructor = XmlElement;
  XmlProcessingInstruction.prototype = Object.create(XmlNode.prototype);
  XmlProcessingInstruction.prototype.constructor = XmlProcessingInstruction;
  XmlComment.prototype = Object.create(XmlNode.prototype);
  XmlComment.prototype.constructor = XmlComment;
  function JsonElement() {
  }
  JsonElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'JsonElement',
    interfaces: []
  };
  function JsonObject(properties) {
    if (properties === void 0)
      properties = emptyMap();
    JsonElement.call(this);
    this.properties = properties;
  }
  Object.defineProperty(JsonObject.prototype, 'entries', {
    get: function () {
      return this.properties.entries;
    }
  });
  Object.defineProperty(JsonObject.prototype, 'keys', {
    get: function () {
      return this.properties.keys;
    }
  });
  Object.defineProperty(JsonObject.prototype, 'size', {
    get: function () {
      return this.properties.size;
    }
  });
  Object.defineProperty(JsonObject.prototype, 'values', {
    get: function () {
      return this.properties.values;
    }
  });
  JsonObject.prototype.containsKey_11rb$ = function (key) {
    return this.properties.containsKey_11rb$(key);
  };
  JsonObject.prototype.containsValue_11rc$ = function (value) {
    return this.properties.containsValue_11rc$(value);
  };
  JsonObject.prototype.get_11rb$ = function (key) {
    return this.properties.get_11rb$(key);
  };
  JsonObject.prototype.getOrDefault_xwzc9p$ = function (key, defaultValue) {
    return this.properties.getOrDefault_xwzc9p$(key, defaultValue);
  };
  JsonObject.prototype.isEmpty = function () {
    return this.properties.isEmpty();
  };
  JsonObject.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'JsonObject',
    interfaces: [Map, JsonElement]
  };
  function JsonObject_init(properties, $this) {
    $this = $this || Object.create(JsonObject.prototype);
    var $receiver = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$();
    properties;
    putAll($receiver, properties);
    JsonObject.call($this, $receiver);
    return $this;
  }
  function JsonObject_init_0(properties, $this) {
    $this = $this || Object.create(JsonObject.prototype);
    JsonObject.call($this, linkedMapOf(properties.slice()));
    return $this;
  }
  JsonObject.prototype.component1 = function () {
    return this.properties;
  };
  JsonObject.prototype.copy_dnu9dl$ = function (properties) {
    return new JsonObject(properties === void 0 ? this.properties : properties);
  };
  JsonObject.prototype.toString = function () {
    return 'JsonObject(properties=' + Kotlin.toString(this.properties) + ')';
  };
  JsonObject.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.properties) | 0;
    return result;
  };
  JsonObject.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.properties, other.properties))));
  };
  function withProperty($receiver, anotherProperty) {
    return $receiver.copy_dnu9dl$(plus($receiver.properties, anotherProperty));
  }
  function withProperties($receiver, moreProperties) {
    return $receiver.copy_dnu9dl$(plus_0($receiver.properties, moreProperties));
  }
  function removeProperty($receiver, key) {
    return $receiver.copy_dnu9dl$(minus($receiver.properties, key));
  }
  function JsonArray(elements) {
    JsonElement.call(this);
    this.elements = elements;
  }
  Object.defineProperty(JsonArray.prototype, 'size', {
    get: function () {
      return this.elements.size;
    }
  });
  JsonArray.prototype.contains_11rb$ = function (element) {
    return this.elements.contains_11rb$(element);
  };
  JsonArray.prototype.containsAll_brywnq$ = function (elements) {
    return this.elements.containsAll_brywnq$(elements);
  };
  JsonArray.prototype.get_za3lpa$ = function (index) {
    return this.elements.get_za3lpa$(index);
  };
  JsonArray.prototype.indexOf_11rb$ = function (element) {
    return this.elements.indexOf_11rb$(element);
  };
  JsonArray.prototype.isEmpty = function () {
    return this.elements.isEmpty();
  };
  JsonArray.prototype.iterator = function () {
    return this.elements.iterator();
  };
  JsonArray.prototype.lastIndexOf_11rb$ = function (element) {
    return this.elements.lastIndexOf_11rb$(element);
  };
  JsonArray.prototype.listIterator = function () {
    return this.elements.listIterator();
  };
  JsonArray.prototype.listIterator_za3lpa$ = function (index) {
    return this.elements.listIterator_za3lpa$(index);
  };
  JsonArray.prototype.subList_vux9f0$ = function (fromIndex, toIndex) {
    return this.elements.subList_vux9f0$(fromIndex, toIndex);
  };
  JsonArray.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'JsonArray',
    interfaces: [List, JsonElement]
  };
  function JsonArray_init(elements, $this) {
    $this = $this || Object.create(JsonArray.prototype);
    JsonArray.call($this, listOf(elements.slice()));
    return $this;
  }
  JsonArray.prototype.component1 = function () {
    return this.elements;
  };
  JsonArray.prototype.copy_o7u7g5$ = function (elements) {
    return new JsonArray(elements === void 0 ? this.elements : elements);
  };
  JsonArray.prototype.toString = function () {
    return 'JsonArray(elements=' + Kotlin.toString(this.elements) + ')';
  };
  JsonArray.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.elements) | 0;
    return result;
  };
  JsonArray.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.elements, other.elements))));
  };
  function append($receiver, anotherElement) {
    return $receiver.copy_o7u7g5$(plus_1($receiver.elements, anotherElement));
  }
  function appendAll($receiver, moreElements) {
    return $receiver.copy_o7u7g5$(plus_2($receiver.elements, moreElements));
  }
  function appendAll_0($receiver, moreElements) {
    return $receiver.copy_o7u7g5$(plus_3($receiver.elements, moreElements));
  }
  function remove($receiver, i) {
    var $receiver_0 = toMutableList($receiver.elements);
    $receiver_0.removeAt_za3lpa$(i);
    return $receiver.copy_o7u7g5$(toList($receiver_0));
  }
  function replace($receiver, i, newElement) {
    var $receiver_0 = toMutableList($receiver.elements);
    $receiver_0.set_wxm5ur$(i, newElement);
    return $receiver.copy_o7u7g5$(toList($receiver_0));
  }
  function JsonNumber(valueAsString) {
    JsonElement.call(this);
    this.valueAsString = valueAsString;
  }
  JsonNumber.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'JsonNumber',
    interfaces: [JsonElement]
  };
  function JsonNumber_init(value, $this) {
    $this = $this || Object.create(JsonNumber.prototype);
    JsonNumber.call($this, value.toString());
    return $this;
  }
  function JsonNumber_init_0(value, $this) {
    $this = $this || Object.create(JsonNumber.prototype);
    JsonNumber.call($this, value.toString());
    return $this;
  }
  function JsonNumber_init_1(value, $this) {
    $this = $this || Object.create(JsonNumber.prototype);
    JsonNumber.call($this, value.toString());
    return $this;
  }
  JsonNumber.prototype.component1 = function () {
    return this.valueAsString;
  };
  JsonNumber.prototype.copy_61zpoe$ = function (valueAsString) {
    return new JsonNumber(valueAsString === void 0 ? this.valueAsString : valueAsString);
  };
  JsonNumber.prototype.toString = function () {
    return 'JsonNumber(valueAsString=' + Kotlin.toString(this.valueAsString) + ')';
  };
  JsonNumber.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.valueAsString) | 0;
    return result;
  };
  JsonNumber.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.valueAsString, other.valueAsString))));
  };
  function toDouble_0($receiver) {
    return toDouble($receiver.valueAsString);
  }
  function toLong_0($receiver) {
    return toLong($receiver.valueAsString);
  }
  function toInt_0($receiver) {
    return toInt($receiver.valueAsString);
  }
  function JsonString(value) {
    JsonElement.call(this);
    this.value = value;
  }
  JsonString.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'JsonString',
    interfaces: [JsonElement]
  };
  JsonString.prototype.component1 = function () {
    return this.value;
  };
  JsonString.prototype.copy_61zpoe$ = function (value) {
    return new JsonString(value === void 0 ? this.value : value);
  };
  JsonString.prototype.toString = function () {
    return 'JsonString(value=' + Kotlin.toString(this.value) + ')';
  };
  JsonString.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  JsonString.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.value, other.value))));
  };
  function JsonBoolean(value) {
    JsonElement.call(this);
    this.value = value;
  }
  JsonBoolean.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'JsonBoolean',
    interfaces: [JsonElement]
  };
  JsonBoolean.prototype.component1 = function () {
    return this.value;
  };
  JsonBoolean.prototype.copy_6taknv$ = function (value) {
    return new JsonBoolean(value === void 0 ? this.value : value);
  };
  JsonBoolean.prototype.toString = function () {
    return 'JsonBoolean(value=' + Kotlin.toString(this.value) + ')';
  };
  JsonBoolean.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  JsonBoolean.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.value, other.value))));
  };
  function JsonNull() {
    JsonNull_instance = this;
    JsonElement.call(this);
  }
  JsonNull.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'JsonNull',
    interfaces: [JsonElement]
  };
  var JsonNull_instance = null;
  function JsonNull_getInstance() {
    if (JsonNull_instance === null) {
      new JsonNull();
    }
    return JsonNull_instance;
  }
  function combine(mutagens) {
    return combine_0(toList_0(mutagens));
  }
  function combine$lambda$lambda(closure$random, closure$original) {
    return function (it) {
      return it(closure$random, closure$original);
    };
  }
  function combine$lambda(closure$mutagens) {
    return function (random, original) {
      return flatMap(asSequence(closure$mutagens), combine$lambda$lambda(random, original));
    };
  }
  function combine_0(mutagens) {
    return combine$lambda(mutagens);
  }
  function plus_5($receiver, that) {
    return combine([$receiver, that]);
  }
  function mapped$lambda(this$mapped, closure$mapIn, closure$mapOut) {
    return function (random, original) {
      return mapLazy(this$mapped(random, closure$mapIn(original)), closure$mapOut);
    };
  }
  function mapped($receiver, mapIn, mapOut) {
    return mapped$lambda($receiver, mapIn, mapOut);
  }
  function withProbability$lambda(closure$p, this$withProbability) {
    return function (random, original) {
      return random.nextDouble() < closure$p ? this$withProbability(random, original) : emptySequence();
    };
  }
  function withProbability($receiver, p) {
    return withProbability$lambda(p, $receiver);
  }
  function and$lambda$lambda(closure$more, closure$random) {
    return function (it) {
      return plus_4(sequenceOf([it]), closure$more(closure$random, it.value));
    };
  }
  function and$lambda(this$and, closure$more) {
    return function (random, original) {
      return flatMap(this$and(random, original), and$lambda$lambda(closure$more, random));
    };
  }
  function and($receiver, more) {
    return and$lambda($receiver, more);
  }
  function filtered$lambda(closure$criteria, this$filtered) {
    return function (random, original) {
      return closure$criteria(original) ? this$filtered(random, original) : emptySequence();
    };
  }
  function filtered($receiver, criteria) {
    return filtered$lambda(criteria, $receiver);
  }
  function always(replacements) {
    return always_0(asIterable(replacements));
  }
  function always$lambda$lambda(it) {
    return lazyOf(it);
  }
  function always$lambda(closure$replacements) {
    return function (f, f_0) {
      return map(asSequence(closure$replacements), always$lambda$lambda);
    };
  }
  function always_0(replacements) {
    return always$lambda(replacements);
  }
  function repeat$lambda$lambda(closure$mutagen, closure$random, closure$original) {
    return function (it) {
      return closure$mutagen(closure$random, closure$original);
    };
  }
  function repeat$lambda(closure$n, closure$mutagen) {
    return function (random, original) {
      return flatMap(asSequence(new IntRange(1, closure$n)), repeat$lambda$lambda(closure$mutagen, random, original));
    };
  }
  function repeat(n, mutagen) {
    return repeat$lambda(n, mutagen);
  }
  function map$lambda$lambda(closure$f, closure$original) {
    return function () {
      return closure$f(closure$original);
    };
  }
  function map$lambda(closure$f) {
    return function (f, original) {
      return sequenceOf([lazy(map$lambda$lambda(closure$f, original))]);
    };
  }
  function map_0(f) {
    return map$lambda(f);
  }
  function mutants($receiver, mutagen, sampleSize, original) {
    var $receiver_0 = sample($receiver, sampleSize, mutagen($receiver, original));
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver_0, 10));
    var tmp$;
    tmp$ = $receiver_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.value);
    }
    return destination;
  }
  function mutant($receiver, mutagen, original) {
    return first(mutants($receiver, mutagen, 1, original));
  }
  function sample($receiver, maxSampleSize, sequence) {
    return sampleFrom(sequence.iterator(), maxSampleSize, $receiver);
  }
  function sample_0($receiver, maxSampleSize, sequence) {
    return sampleFrom(sequence.iterator(), maxSampleSize, $receiver);
  }
  function sampleFrom(sequence, maxSampleSize, random) {
    var selectedMutations = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    var counter = {v: 0};
    while (sequence.hasNext()) {
      var element = sequence.next();
      var count = (counter.v = counter.v + 1 | 0, counter.v);
      if (count <= maxSampleSize) {
        selectedMutations.add_11rb$(element);
      }
       else {
        var index = random.nextInt_za3lpa$(count);
        if (index < selectedMutations.size) {
          selectedMutations.set_wxm5ur$(index, element);
        }
      }
    }
    return selectedMutations;
  }
  function splice$lambda$lambda(closure$replaceSlice, closure$original, closure$replacedRange) {
    return function (it) {
      return closure$replaceSlice(closure$original, closure$replacedRange, it);
    };
  }
  function splice$lambda(closure$length, closure$sliceMutagen, closure$slice, closure$replaceSlice) {
    return function (random, original) {
      var replacedLength = 1 + random.nextInt_za3lpa$(closure$length(original) - 1 | 0) | 0;
      var replacedStart = random.nextInt_za3lpa$(closure$length(original) - replacedLength | 0);
      var replacedEnd = replacedStart + replacedLength | 0;
      var replacedRange = new IntRange(replacedStart, replacedEnd);
      return mapLazy(closure$sliceMutagen(random, closure$slice(original, replacedRange)), splice$lambda$lambda(closure$replaceSlice, original, replacedRange));
    };
  }
  function splice(sliceMutagen, length, slice, replaceSlice) {
    return splice$lambda(length, sliceMutagen, slice, replaceSlice);
  }
  function splice_0(b) {
    return splice_1(always([b]));
  }
  function splice$lambda_0(it) {
    return it.length;
  }
  function splice_1(sliceMutagen) {
    return splice(sliceMutagen, splice$lambda_0, Kotlin.getCallableRef('sliceArray', function ($receiver, indices) {
      return sliceArray($receiver, indices);
    }), Kotlin.getCallableRef('replaceRange', function ($receiver, range, inserted) {
      return replaceRange($receiver, range, inserted);
    }));
  }
  function form(fields) {
    return toList_0(fields);
  }
  function get_0($receiver, key) {
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      if (Kotlin.equals(element.first, key)) {
        destination.add_11rb$(element);
      }
    }
    var destination_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$(destination, 10));
    var tmp$_0;
    tmp$_0 = destination.iterator();
    while (tmp$_0.hasNext()) {
      var item = tmp$_0.next();
      destination_0.add_11rb$(item.second);
    }
    return destination_0;
  }
  function get_keys($receiver) {
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.first);
    }
    return toSet(destination);
  }
  function removeSingleFieldValue$lambda$lambda$lambda(closure$original, closure$field) {
    return function () {
      return minus_0(closure$original, closure$field);
    };
  }
  function removeSingleFieldValue$lambda$lambda(closure$original) {
    return function (field) {
      return lazy(removeSingleFieldValue$lambda$lambda$lambda(closure$original, field));
    };
  }
  function removeSingleFieldValue$lambda(f, original) {
    return map(asSequence(original), removeSingleFieldValue$lambda$lambda(original));
  }
  function removeSingleFieldValue() {
    return removeSingleFieldValue$lambda;
  }
  function removeField$lambda$lambda$lambda(closure$original, closure$key) {
    return function () {
      var $receiver = closure$original;
      var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (!Kotlin.equals(element.first, closure$key)) {
          destination.add_11rb$(element);
        }
      }
      return destination;
    };
  }
  function removeField$lambda$lambda(closure$original) {
    return function (key) {
      return lazy(removeField$lambda$lambda$lambda(closure$original, key));
    };
  }
  function removeField$lambda(f, original) {
    return map(asSequence(get_keys(original)), removeField$lambda$lambda(original));
  }
  function removeField() {
    return removeField$lambda;
  }
  function mutateValue$lambda$lambda$lambda(closure$original, closure$key, closure$value) {
    return function (newValue) {
      var $receiver = closure$original;
      var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var tmp$_0 = destination.add_11rb$;
        var closure$key_0 = closure$key;
        var closure$value_0 = closure$value;
        tmp$_0.call(destination, (item != null ? item.equals(to(closure$key_0, closure$value_0)) : null) ? to(closure$key_0, newValue) : item);
      }
      return destination;
    };
  }
  function mutateValue$lambda$lambda(closure$valueMutagen, closure$random, closure$original) {
    return function (f) {
      var key = f.component1()
      , value = f.component2();
      return mapLazy(closure$valueMutagen(closure$random, value), mutateValue$lambda$lambda$lambda(closure$original, key, value));
    };
  }
  function mutateValue$lambda(closure$valueMutagen) {
    return function (random, original) {
      return flatMap(asSequence(original), mutateValue$lambda$lambda(closure$valueMutagen, random, original));
    };
  }
  function mutateValue(valueMutagen) {
    return mutateValue$lambda(valueMutagen);
  }
  function addUniqueField$lambda$lambda(closure$original, closure$basename, closure$newValues) {
    return function () {
      var tmp$ = closure$original;
      var $receiver = unusedKey(closure$basename, closure$original);
      var $receiver_0 = closure$newValues;
      var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver_0, 10));
      var tmp$_0;
      tmp$_0 = $receiver_0.iterator();
      while (tmp$_0.hasNext()) {
        var item = tmp$_0.next();
        destination.add_11rb$(to($receiver, item));
      }
      return plus_2(tmp$, destination);
    };
  }
  function addUniqueField$lambda(closure$basename, closure$newValues) {
    return function (f, original) {
      return sequenceOf([lazy(addUniqueField$lambda$lambda(original, closure$basename, closure$newValues))]);
    };
  }
  function addUniqueField(newValues, basename) {
    if (basename === void 0)
      basename = 'x';
    return addUniqueField$lambda(basename, newValues);
  }
  function unusedKey$lambda$lambda(it) {
    return it + 1 | 0;
  }
  function unusedKey$lambda$lambda_0(closure$basename) {
    return function (i) {
      return closure$basename + '_' + i;
    };
  }
  function unusedKey$lambda$lambda_1(closure$keys) {
    return function (it) {
      return closure$keys.contains_11rb$(it);
    };
  }
  function unusedKey(basename, original) {
    var keys = get_keys(original);
    return first_0(filterNot(plus_4(sequenceOf([basename]), map(generateSequence(1, unusedKey$lambda$lambda), unusedKey$lambda$lambda_0(basename))), unusedKey$lambda$lambda_1(keys)));
  }
  function defaultFormMutagens() {
    var tmp$ = removeSingleFieldValue();
    var tmp$_0 = removeField();
    var tmp$_1 = mutateValue(replaceWithPossiblyMeaningfulText());
    var $receiver = possiblyMeaningfulStrings();
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$_2;
    tmp$_2 = $receiver.iterator();
    while (tmp$_2.hasNext()) {
      var item = tmp$_2.next();
      destination.add_11rb$(addUniqueField(listOf_0(item), 'x'));
    }
    return combine([tmp$, tmp$_0, tmp$_1, combine_0(destination), mutateValue(replaceWithTroublesomeClassName())]);
  }
  function replaceRange($receiver, range, inserted) {
    var $receiver_0 = sliceArray($receiver, new IntRange(0, range.first - 1 | 0));
    var $receiver_1 = Kotlin.primitiveArrayConcat($receiver_0, inserted);
    var elements = sliceArray($receiver, new IntRange(range.last + 1 | 0, $receiver.length - 1 | 0));
    return Kotlin.primitiveArrayConcat($receiver_1, elements);
  }
  function mapLazy$lambda$lambda(closure$f, closure$it) {
    return function () {
      return closure$f(closure$it.value);
    };
  }
  function mapLazy$lambda(closure$f) {
    return function (it) {
      return lazy(mapLazy$lambda$lambda(closure$f, it));
    };
  }
  function mapLazy($receiver, f) {
    return map($receiver, mapLazy$lambda(f));
  }
  function JsonMutagen$lambda$lambda(T_0, isT, closure$elementMutagen, closure$random) {
    return function (f) {
      var element = f.component1()
      , replaceInDocument = f.component2();
      if (isT(element)) {
        return _.com.natpryce.snodge.internal.mapLazy_n8y9gc$(closure$elementMutagen(closure$random, element), replaceInDocument);
      }
       else {
        return Kotlin.kotlin.sequences.emptySequence_287e2$();
      }
    };
  }
  function JsonMutagen$lambda(T_0, isT, closure$elementMutagen) {
    return function (random, original) {
      return Kotlin.kotlin.sequences.flatMap_49vfel$(_.com.natpryce.snodge.json.walk_rdvzxt$(original), _.com.natpryce.snodge.json.JsonMutagen$f$f(T_0, isT, closure$elementMutagen, random));
    };
  }
  var JsonMutagen = Kotlin.defineInlineFunction('snodge.com.natpryce.snodge.json.JsonMutagen_oi04m1$', function (T_0, isT, elementMutagen) {
    return _.com.natpryce.snodge.json.JsonMutagen$f(T_0, isT, elementMutagen);
  });
  function forStrings$lambda(it) {
    return toJsonElement(it);
  }
  function forStrings$lambda_0(it) {
    return toJsonString(it);
  }
  function forStrings($receiver) {
    return mapped($receiver, forStrings$lambda, forStrings$lambda_0);
  }
  function addArrayElement$lambda$lambda(closure$elementToMutate, closure$newElement) {
    return function () {
      return append(closure$elementToMutate, closure$newElement);
    };
  }
  function addArrayElement$lambda(closure$newElement) {
    return function (f, elementToMutate) {
      return sequenceOf([lazy(addArrayElement$lambda$lambda(elementToMutate, closure$newElement))]);
    };
  }
  function addArrayElement(newElement) {
    return _.com.natpryce.snodge.json.JsonMutagen$f(JsonArray, Kotlin.isInstanceOf(JsonArray), addArrayElement$lambda(newElement));
  }
  function addObjectProperty$lambda$lambda(closure$elementToMutate, closure$newElement) {
    return function () {
      return withProperty(closure$elementToMutate, to(newProperty(closure$elementToMutate, 'x'), closure$newElement));
    };
  }
  function addObjectProperty$lambda(closure$newElement) {
    return function (f, elementToMutate) {
      return sequenceOf([lazy(addObjectProperty$lambda$lambda(elementToMutate, closure$newElement))]);
    };
  }
  function addObjectProperty(newElement) {
    return _.com.natpryce.snodge.json.JsonMutagen$f(JsonObject, Kotlin.isInstanceOf(JsonObject), addObjectProperty$lambda(newElement));
  }
  function newProperty$lambda(it) {
    return it + 1 | 0;
  }
  function newProperty$lambda_0(closure$basename) {
    return function (i) {
      return closure$basename + '_' + i;
    };
  }
  function newProperty$lambda_1(this$newProperty) {
    return function (it) {
      var $receiver = this$newProperty;
      var tmp$;
      return (Kotlin.isType(tmp$ = $receiver, Kotlin.kotlin.collections.Map) ? tmp$ : Kotlin.throwCCE()).containsKey_11rb$(it);
    };
  }
  function newProperty($receiver, basename) {
    return first_0(filterNot(plus_4(sequenceOf([basename]), map(generateSequence(1, newProperty$lambda), newProperty$lambda_0(basename))), newProperty$lambda_1($receiver)));
  }
  function removeJsonObjectProperty$lambda$lambda$lambda(closure$elementToMutate, closure$key) {
    return function () {
      return removeProperty(closure$elementToMutate, closure$key);
    };
  }
  function removeJsonObjectProperty$lambda$lambda(closure$elementToMutate) {
    return function (key) {
      return lazy(removeJsonObjectProperty$lambda$lambda$lambda(closure$elementToMutate, key));
    };
  }
  function removeJsonObjectProperty$lambda(f, elementToMutate) {
    return map(asSequence(elementToMutate.keys), removeJsonObjectProperty$lambda$lambda(elementToMutate));
  }
  function removeJsonObjectProperty() {
    return _.com.natpryce.snodge.json.JsonMutagen$f(JsonObject, Kotlin.isInstanceOf(JsonObject), removeJsonObjectProperty$lambda);
  }
  function removeJsonArrayElement$lambda$lambda$lambda(closure$elementToMutate, closure$i) {
    return function () {
      return remove(closure$elementToMutate, closure$i);
    };
  }
  function removeJsonArrayElement$lambda$lambda(closure$elementToMutate) {
    return function (i) {
      return lazy(removeJsonArrayElement$lambda$lambda$lambda(closure$elementToMutate, i));
    };
  }
  function removeJsonArrayElement$lambda(f, elementToMutate) {
    return map(asSequence(get_indices(elementToMutate)), removeJsonArrayElement$lambda$lambda(elementToMutate));
  }
  function removeJsonArrayElement() {
    return _.com.natpryce.snodge.json.JsonMutagen$f(JsonArray, Kotlin.isInstanceOf(JsonArray), removeJsonArrayElement$lambda);
  }
  function removeJsonElement() {
    return plus_5(removeJsonObjectProperty(), removeJsonArrayElement());
  }
  function replaceJsonObjectProperty$lambda$lambda$lambda(closure$elementToMutate, closure$key, closure$replacement) {
    return function () {
      return withProperty(closure$elementToMutate, to(closure$key, closure$replacement));
    };
  }
  function replaceJsonObjectProperty$lambda$lambda(closure$elementToMutate, closure$replacement) {
    return function (key) {
      return lazy(replaceJsonObjectProperty$lambda$lambda$lambda(closure$elementToMutate, key, closure$replacement));
    };
  }
  function replaceJsonObjectProperty$lambda(closure$replacement) {
    return function (f, elementToMutate) {
      return map(asSequence(elementToMutate.keys), replaceJsonObjectProperty$lambda$lambda(elementToMutate, closure$replacement));
    };
  }
  function replaceJsonObjectProperty(replacement) {
    return _.com.natpryce.snodge.json.JsonMutagen$f(JsonObject, Kotlin.isInstanceOf(JsonObject), replaceJsonObjectProperty$lambda(replacement));
  }
  function replaceJsonArrayElement$lambda$lambda$lambda(closure$elementToMutate, closure$i, closure$replacement) {
    return function () {
      return replace(closure$elementToMutate, closure$i, closure$replacement);
    };
  }
  function replaceJsonArrayElement$lambda$lambda(closure$elementToMutate, closure$replacement) {
    return function (i) {
      return lazy(replaceJsonArrayElement$lambda$lambda$lambda(closure$elementToMutate, i, closure$replacement));
    };
  }
  function replaceJsonArrayElement$lambda(closure$replacement) {
    return function (f, elementToMutate) {
      return map(asSequence(get_indices(elementToMutate)), replaceJsonArrayElement$lambda$lambda(elementToMutate, closure$replacement));
    };
  }
  function replaceJsonArrayElement(replacement) {
    return _.com.natpryce.snodge.json.JsonMutagen$f(JsonArray, Kotlin.isInstanceOf(JsonArray), replaceJsonArrayElement$lambda(replacement));
  }
  function replaceJsonElement(replacement) {
    return plus_5(replaceJsonObjectProperty(replacement), replaceJsonArrayElement(replacement));
  }
  function reorderObjectProperties$lambda$lambda(closure$random, closure$elementToMutate) {
    return function () {
      return JsonObject_init(shuffled(closure$random, toList_1(closure$elementToMutate.properties)));
    };
  }
  function reorderObjectProperties$lambda(random, elementToMutate) {
    return sequenceOf([lazy(reorderObjectProperties$lambda$lambda(random, elementToMutate))]);
  }
  function reorderObjectProperties() {
    return _.com.natpryce.snodge.json.JsonMutagen$f(JsonObject, Kotlin.isInstanceOf(JsonObject), reorderObjectProperties$lambda);
  }
  function reflectionMutagens$lambda$lambda(it) {
    return Kotlin.isType(it, JsonString);
  }
  function reflectionMutagens() {
    var $receiver = troublesomeClasses();
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(filtered(replaceJsonElement(new JsonString(item)), reflectionMutagens$lambda$lambda));
    }
    return combine_0(destination);
  }
  var exampleElements;
  function defaultJsonMutagens() {
    var $receiver = exampleElements;
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(combine([replaceJsonElement(item), addArrayElement(item), addObjectProperty(item)]));
    }
    return combine([combine_0(destination), removeJsonElement(), reorderObjectProperties(), reflectionMutagens()]);
  }
  function allJsonMutagens() {
    return defaultJsonMutagens();
  }
  function walk$lambda(it) {
    return it;
  }
  function walk($receiver) {
    return walk_0($receiver, walk$lambda);
  }
  function walk_0(element, replaceInDocument) {
    return plus_4(sequenceOf([new Pair(element, replaceInDocument)]), walkChildren(element, replaceInDocument));
  }
  function walkChildren$lambda$lambda(closure$replaceInDocument, closure$parent, closure$key) {
    return function (newChild) {
      return closure$replaceInDocument(withProperty(closure$parent, to(closure$key, newChild)));
    };
  }
  function walkChildren$lambda(closure$replaceInDocument, closure$parent) {
    return function (f) {
      var key = f.key;
      var child = f.value;
      return walk_0(child, walkChildren$lambda$lambda(closure$replaceInDocument, closure$parent, key));
    };
  }
  function walkChildren$lambda$lambda_0(closure$replaceInDocument, closure$parent, closure$i) {
    return function (newChild) {
      return closure$replaceInDocument(replace(closure$parent, closure$i, newChild));
    };
  }
  function walkChildren$lambda_0(closure$replaceInDocument, closure$parent) {
    return function (f) {
      var i = f.component1()
      , child = f.component2();
      return walk_0(child, walkChildren$lambda$lambda_0(closure$replaceInDocument, closure$parent, i));
    };
  }
  function walkChildren(parent, replaceInDocument) {
    if (Kotlin.isType(parent, JsonObject))
      return flatMap(asSequence_0(parent), walkChildren$lambda(replaceInDocument, parent));
    else if (Kotlin.isType(parent, JsonArray))
      return flatMap(withIndex(asSequence(parent)), walkChildren$lambda_0(replaceInDocument, parent));
    else
      return emptySequence();
  }
  function replaceWithTroublesomeClassName() {
    return always_0(troublesomeClasses());
  }
  function splice_2(s) {
    return splice_3(always([s]));
  }
  function splice_3(sliceMutagen) {
    return splice(sliceMutagen, Kotlin.getPropertyCallableRef('length', 1, function ($receiver) {
      return $receiver.length;
    }), Kotlin.getCallableRef('slice', function ($receiver, indices) {
      return slice($receiver, indices);
    }), Kotlin.getCallableRef('replaceRange', function ($receiver, range, replacement) {
      var tmp$;
      return Kotlin.kotlin.text.replaceRange_r6gztw$(Kotlin.isCharSequence(tmp$ = $receiver) ? tmp$ : Kotlin.throwCCE(), range, replacement).toString();
    }));
  }
  function possiblyMeaningfulStrings() {
    return listOf(['', '1', '-1', '0', '-0', (new Kotlin.Long(-1, 2147483647)).toString(), (new Kotlin.Long(0, -2147483648)).toString(), 'true', 'false', '1.0', '-1.0', '0.0', '-0.0', DoubleCompanionObject.NaN.toString(), DoubleCompanionObject.POSITIVE_INFINITY.toString(), DoubleCompanionObject.NEGATIVE_INFINITY.toString(), DoubleCompanionObject.MAX_VALUE.toString(), DoubleCompanionObject.MIN_VALUE.toString()]);
  }
  function replaceWithPossiblyMeaningfulText() {
    return always_0(possiblyMeaningfulStrings());
  }
  function XmlMutagen$lambda$lambda(T_0, isT, closure$elementMutagen, closure$random) {
    return function (f) {
      var node = f.component1()
      , replaceInDocument = f.component2();
      if (isT(node)) {
        return _.com.natpryce.snodge.internal.mapLazy_n8y9gc$(closure$elementMutagen(closure$random, node), replaceInDocument);
      }
       else {
        return Kotlin.kotlin.sequences.emptySequence_287e2$();
      }
    };
  }
  function XmlMutagen$lambda(T_0, isT, closure$elementMutagen) {
    return function (random, original) {
      return Kotlin.kotlin.sequences.flatMap_49vfel$(_.com.natpryce.snodge.xml.walk_hn75bi$(original), _.com.natpryce.snodge.xml.XmlMutagen$f$f(T_0, isT, closure$elementMutagen, random));
    };
  }
  var XmlMutagen = Kotlin.defineInlineFunction('snodge.com.natpryce.snodge.xml.XmlMutagen_p46b4h$', function (T_0, isT, elementMutagen) {
    return _.com.natpryce.snodge.xml.XmlMutagen$f(T_0, isT, elementMutagen);
  });
  function forStrings$lambda_1(it) {
    return toXmlDocument(it);
  }
  function forStrings$lambda_2(it) {
    return toXmlString(it);
  }
  function forStrings_0($receiver) {
    return mapped($receiver, forStrings$lambda_1, forStrings$lambda_2);
  }
  function removeAttribute$lambda$lambda$lambda(closure$element, closure$it) {
    return function () {
      return minusAttribute(closure$element, closure$it);
    };
  }
  function removeAttribute$lambda$lambda(closure$element) {
    return function (it) {
      return lazy(removeAttribute$lambda$lambda$lambda(closure$element, it));
    };
  }
  function removeAttribute$lambda(f, element) {
    return map(asSequence(element.attributes.keys), removeAttribute$lambda$lambda(element));
  }
  function removeAttribute() {
    return _.com.natpryce.snodge.xml.XmlMutagen$f(XmlElement, Kotlin.isInstanceOf(XmlElement), removeAttribute$lambda);
  }
  function removeElement$lambda$lambda$lambda(closure$element, closure$it) {
    return function () {
      return minusChild(closure$element, closure$it);
    };
  }
  function removeElement$lambda(f, element) {
    var $receiver = get_indices(element.children);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(lazy(removeElement$lambda$lambda$lambda(element, item)));
    }
    return asSequence(destination);
  }
  function removeElement() {
    return _.com.natpryce.snodge.xml.XmlMutagen$f(XmlElement, Kotlin.isInstanceOf(XmlElement), removeElement$lambda);
  }
  function removeNamespace$lambda$lambda(closure$element) {
    return function () {
      return closure$element.copy_pbf30e$(withoutNamespace(closure$element.name));
    };
  }
  function removeNamespace$lambda$lambda_0(f) {
    var name = f.key;
    return name.namespaceURI != null;
  }
  function removeNamespace$lambda$lambda$lambda(closure$element, closure$name, closure$value) {
    return function () {
      return withAttribute(minusAttribute(closure$element, closure$name), to(withoutNamespace(closure$name), closure$value));
    };
  }
  function removeNamespace$lambda$lambda_1(closure$element) {
    return function (f) {
      var name = f.key;
      var value = f.value;
      return lazy(removeNamespace$lambda$lambda$lambda(closure$element, name, value));
    };
  }
  function removeNamespace$lambda(f, element) {
    return plus_4(sequenceOf([lazy(removeNamespace$lambda$lambda(element))]), map(filter(asSequence_0(element.attributes), removeNamespace$lambda$lambda_0), removeNamespace$lambda$lambda_1(element)));
  }
  function removeNamespace() {
    return _.com.natpryce.snodge.xml.XmlMutagen$f(XmlElement, Kotlin.isInstanceOf(XmlElement), removeNamespace$lambda);
  }
  function replaceNode$lambda(T_0, isT) {
    return function (it) {
      return isT(it);
    };
  }
  var replaceNode = Kotlin.defineInlineFunction('snodge.com.natpryce.snodge.xml.replaceNode_tno50o$', function (T_0, isT, replacement) {
    return _.com.natpryce.snodge.xml.replaceNodeIf_jvrbw5$(_.com.natpryce.snodge.xml.replaceNode$f(T_0, isT), replacement);
  });
  function replaceNodeIf$lambda$lambda(closure$p) {
    return function (f) {
      var child = f.component2();
      return closure$p(child);
    };
  }
  function replaceNodeIf$lambda$lambda$lambda(closure$element, closure$i, closure$replacement) {
    return function () {
      return replaceChild(closure$element, closure$i, closure$replacement);
    };
  }
  function replaceNodeIf$lambda$lambda_0(closure$element, closure$replacement) {
    return function (f) {
      var i = f.component1();
      return lazy(replaceNodeIf$lambda$lambda$lambda(closure$element, i, closure$replacement));
    };
  }
  function replaceNodeIf$lambda(closure$p, closure$replacement) {
    return function (f, element) {
      return map(filter(asSequence(withIndex_0(element.children)), replaceNodeIf$lambda$lambda(closure$p)), replaceNodeIf$lambda$lambda_0(element, closure$replacement));
    };
  }
  function replaceNodeIf(p, replacement) {
    return _.com.natpryce.snodge.xml.XmlMutagen$f(XmlElement, Kotlin.isInstanceOf(XmlElement), replaceNodeIf$lambda(p, replacement));
  }
  function replaceText(newText) {
    return _.com.natpryce.snodge.xml.replaceNodeIf_jvrbw5$(_.com.natpryce.snodge.xml.replaceNode$f(XmlText, Kotlin.isInstanceOf(XmlText)), new XmlText(newText));
  }
  function withoutNamespace($receiver) {
    return new QName($receiver.localPart);
  }
  function defaultXmlMutagens() {
    var tmp$ = removeAttribute();
    var tmp$_0 = removeElement();
    var tmp$_1 = removeNamespace();
    var tmp$_2 = _.com.natpryce.snodge.xml.replaceNodeIf_jvrbw5$(_.com.natpryce.snodge.xml.replaceNode$f(XmlNode, Kotlin.isInstanceOf(XmlNode)), new XmlElement(new QName('replacement')));
    var tmp$_3 = _.com.natpryce.snodge.xml.replaceNodeIf_jvrbw5$(_.com.natpryce.snodge.xml.replaceNode$f(XmlNode, Kotlin.isInstanceOf(XmlNode)), new XmlText('replacement'));
    var $receiver = possiblyMeaningfulStrings();
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$_4;
    tmp$_4 = $receiver.iterator();
    while (tmp$_4.hasNext()) {
      var item = tmp$_4.next();
      destination.add_11rb$(replaceText(item));
    }
    var tmp$_5 = combine_0(destination);
    var $receiver_0 = troublesomeClasses();
    var destination_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver_0, 10));
    var tmp$_6;
    tmp$_6 = $receiver_0.iterator();
    while (tmp$_6.hasNext()) {
      var item_0 = tmp$_6.next();
      destination_0.add_11rb$(replaceText(item_0));
    }
    return combine([tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_5, combine_0(destination_0)]);
  }
  function walk$lambda$lambda(this$walk, closure$i) {
    return function (it) {
      return this$walk.copy_g7yrug$(void 0, replace_0(this$walk.children, closure$i, it));
    };
  }
  function walk$lambda_0(this$walk) {
    return function (f) {
      var i = f.component1()
      , child = f.component2();
      return walk_2(child, walk$lambda$lambda(this$walk, i));
    };
  }
  function walk_1($receiver) {
    return flatMap(withIndex(asSequence($receiver.children)), walk$lambda_0($receiver));
  }
  function walk_2(element, replaceInDocument) {
    return plus_4(sequenceOf([new Pair(element, replaceInDocument)]), walkChildren_0(element, replaceInDocument));
  }
  function walkChildren$lambda$lambda_1(closure$replaceInDocument, this$walkChildren, closure$i) {
    return function (it) {
      return closure$replaceInDocument(this$walkChildren.copy_pbf30e$(void 0, void 0, replace_0(this$walkChildren.children, closure$i, it)));
    };
  }
  function walkChildren$lambda_1(closure$replaceInDocument, this$walkChildren) {
    return function (f) {
      var i = f.component1()
      , child = f.component2();
      return walk_2(child, walkChildren$lambda$lambda_1(closure$replaceInDocument, this$walkChildren, i));
    };
  }
  function walkChildren_0($receiver, replaceInDocument) {
    if (Kotlin.isType($receiver, XmlElement)) {
      return flatMap(withIndex(asSequence($receiver.children)), walkChildren$lambda_1(replaceInDocument, $receiver));
    }
     else {
      return emptySequence();
    }
  }
  function replace_0($receiver, i, newElement) {
    var $receiver_0 = toMutableList($receiver);
    $receiver_0.set_wxm5ur$(i, newElement);
    return toList($receiver_0);
  }
  function HasChildren() {
  }
  HasChildren.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasChildren',
    interfaces: []
  };
  function XmlDeclaration(version, encoding) {
    if (encoding === void 0)
      encoding = null;
    this.version = version;
    this.encoding = encoding;
  }
  XmlDeclaration.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'XmlDeclaration',
    interfaces: []
  };
  XmlDeclaration.prototype.component1 = function () {
    return this.version;
  };
  XmlDeclaration.prototype.component2 = function () {
    return this.encoding;
  };
  XmlDeclaration.prototype.copy_jyasbz$ = function (version, encoding) {
    return new XmlDeclaration(version === void 0 ? this.version : version, encoding === void 0 ? this.encoding : encoding);
  };
  XmlDeclaration.prototype.toString = function () {
    return 'XmlDeclaration(version=' + Kotlin.toString(this.version) + (', encoding=' + Kotlin.toString(this.encoding)) + ')';
  };
  XmlDeclaration.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.version) | 0;
    result = result * 31 + Kotlin.hashCode(this.encoding) | 0;
    return result;
  };
  XmlDeclaration.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.version, other.version) && Kotlin.equals(this.encoding, other.encoding)))));
  };
  function XmlDocument(xml, children) {
    this.xml = xml;
    this.children_hn75bt$_0 = children;
    var $receiver = this.children;
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      if (Kotlin.isType(element, XmlElement)) {
        destination.add_11rb$(element);
      }
    }
    this.root = single(destination);
  }
  Object.defineProperty(XmlDocument.prototype, 'children', {
    get: function () {
      return this.children_hn75bt$_0;
    }
  });
  XmlDocument.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'XmlDocument',
    interfaces: [HasChildren]
  };
  XmlDocument.prototype.component1 = function () {
    return this.xml;
  };
  XmlDocument.prototype.component2 = function () {
    return this.children;
  };
  XmlDocument.prototype.copy_g7yrug$ = function (xml, children) {
    return new XmlDocument(xml === void 0 ? this.xml : xml, children === void 0 ? this.children : children);
  };
  XmlDocument.prototype.toString = function () {
    return 'XmlDocument(xml=' + Kotlin.toString(this.xml) + (', children=' + Kotlin.toString(this.children)) + ')';
  };
  XmlDocument.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.xml) | 0;
    result = result * 31 + Kotlin.hashCode(this.children) | 0;
    return result;
  };
  XmlDocument.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.xml, other.xml) && Kotlin.equals(this.children, other.children)))));
  };
  function XmlNode() {
  }
  XmlNode.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'XmlNode',
    interfaces: []
  };
  function XmlText(text, asCData) {
    if (asCData === void 0)
      asCData = false;
    XmlNode.call(this);
    this.text = text;
    this.asCData = asCData;
  }
  XmlText.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'XmlText',
    interfaces: [XmlNode]
  };
  XmlText.prototype.component1 = function () {
    return this.text;
  };
  XmlText.prototype.component2 = function () {
    return this.asCData;
  };
  XmlText.prototype.copy_ivxn3r$ = function (text, asCData) {
    return new XmlText(text === void 0 ? this.text : text, asCData === void 0 ? this.asCData : asCData);
  };
  XmlText.prototype.toString = function () {
    return 'XmlText(text=' + Kotlin.toString(this.text) + (', asCData=' + Kotlin.toString(this.asCData)) + ')';
  };
  XmlText.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.text) | 0;
    result = result * 31 + Kotlin.hashCode(this.asCData) | 0;
    return result;
  };
  XmlText.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.text, other.text) && Kotlin.equals(this.asCData, other.asCData)))));
  };
  function QName(localPart, namespaceURI, prefix) {
    if (namespaceURI === void 0)
      namespaceURI = null;
    if (prefix === void 0)
      prefix = null;
    this.localPart = localPart;
    this.namespaceURI = namespaceURI;
    this.prefix = prefix;
  }
  QName.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'QName',
    interfaces: []
  };
  QName.prototype.component1 = function () {
    return this.localPart;
  };
  QName.prototype.component2 = function () {
    return this.namespaceURI;
  };
  QName.prototype.component3 = function () {
    return this.prefix;
  };
  QName.prototype.copy_5p2dge$ = function (localPart, namespaceURI, prefix) {
    return new QName(localPart === void 0 ? this.localPart : localPart, namespaceURI === void 0 ? this.namespaceURI : namespaceURI, prefix === void 0 ? this.prefix : prefix);
  };
  QName.prototype.toString = function () {
    return 'QName(localPart=' + Kotlin.toString(this.localPart) + (', namespaceURI=' + Kotlin.toString(this.namespaceURI)) + (', prefix=' + Kotlin.toString(this.prefix)) + ')';
  };
  QName.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.localPart) | 0;
    result = result * 31 + Kotlin.hashCode(this.namespaceURI) | 0;
    result = result * 31 + Kotlin.hashCode(this.prefix) | 0;
    return result;
  };
  QName.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.localPart, other.localPart) && Kotlin.equals(this.namespaceURI, other.namespaceURI) && Kotlin.equals(this.prefix, other.prefix)))));
  };
  function XmlElement(name, attributes, children) {
    if (attributes === void 0)
      attributes = emptyMap();
    if (children === void 0)
      children = emptyList();
    XmlNode.call(this);
    this.name = name;
    this.attributes = attributes;
    this.children_mzgyik$_0 = children;
  }
  Object.defineProperty(XmlElement.prototype, 'children', {
    get: function () {
      return this.children_mzgyik$_0;
    }
  });
  XmlElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'XmlElement',
    interfaces: [HasChildren, XmlNode]
  };
  XmlElement.prototype.component1 = function () {
    return this.name;
  };
  XmlElement.prototype.component2 = function () {
    return this.attributes;
  };
  XmlElement.prototype.component3 = function () {
    return this.children;
  };
  XmlElement.prototype.copy_pbf30e$ = function (name, attributes, children) {
    return new XmlElement(name === void 0 ? this.name : name, attributes === void 0 ? this.attributes : attributes, children === void 0 ? this.children : children);
  };
  XmlElement.prototype.toString = function () {
    return 'XmlElement(name=' + Kotlin.toString(this.name) + (', attributes=' + Kotlin.toString(this.attributes)) + (', children=' + Kotlin.toString(this.children)) + ')';
  };
  XmlElement.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.name) | 0;
    result = result * 31 + Kotlin.hashCode(this.attributes) | 0;
    result = result * 31 + Kotlin.hashCode(this.children) | 0;
    return result;
  };
  XmlElement.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.name, other.name) && Kotlin.equals(this.attributes, other.attributes) && Kotlin.equals(this.children, other.children)))));
  };
  function withAttribute($receiver, attr) {
    return $receiver.copy_pbf30e$(void 0, plus($receiver.attributes, attr));
  }
  function minusAttribute($receiver, attrName) {
    return $receiver.copy_pbf30e$(void 0, minus($receiver.attributes, attrName));
  }
  function plusChild($receiver, c) {
    return $receiver.copy_pbf30e$(void 0, void 0, plus_1($receiver.children, c));
  }
  function minusChild($receiver, index) {
    var tmp$ = void 0;
    var tmp$_0 = void 0;
    var $receiver_0 = toMutableList($receiver.children);
    $receiver_0.removeAt_za3lpa$(index);
    return $receiver.copy_pbf30e$(tmp$, tmp$_0, toList($receiver_0));
  }
  function replaceChild($receiver, index, replacement) {
    var tmp$ = void 0;
    var tmp$_0 = void 0;
    var $receiver_0 = toMutableList($receiver.children);
    $receiver_0.set_wxm5ur$(index, replacement);
    return $receiver.copy_pbf30e$(tmp$, tmp$_0, toList($receiver_0));
  }
  function XmlProcessingInstruction(target, data) {
    if (data === void 0)
      data = null;
    XmlNode.call(this);
    this.target = target;
    this.data = data;
  }
  XmlProcessingInstruction.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'XmlProcessingInstruction',
    interfaces: [XmlNode]
  };
  XmlProcessingInstruction.prototype.component1 = function () {
    return this.target;
  };
  XmlProcessingInstruction.prototype.component2 = function () {
    return this.data;
  };
  XmlProcessingInstruction.prototype.copy_jyasbz$ = function (target, data) {
    return new XmlProcessingInstruction(target === void 0 ? this.target : target, data === void 0 ? this.data : data);
  };
  XmlProcessingInstruction.prototype.toString = function () {
    return 'XmlProcessingInstruction(target=' + Kotlin.toString(this.target) + (', data=' + Kotlin.toString(this.data)) + ')';
  };
  XmlProcessingInstruction.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    return result;
  };
  XmlProcessingInstruction.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.target, other.target) && Kotlin.equals(this.data, other.data)))));
  };
  function XmlComment(text) {
    XmlNode.call(this);
    this.text = text;
  }
  XmlComment.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'XmlComment',
    interfaces: [XmlNode]
  };
  XmlComment.prototype.component1 = function () {
    return this.text;
  };
  XmlComment.prototype.copy_61zpoe$ = function (text) {
    return new XmlComment(text === void 0 ? this.text : text);
  };
  XmlComment.prototype.toString = function () {
    return 'XmlComment(text=' + Kotlin.toString(this.text) + ')';
  };
  XmlComment.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.text) | 0;
    return result;
  };
  XmlComment.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.text, other.text))));
  };
  function toJsonElement($receiver) {
    return toJsonElement_0(JSON.parse($receiver));
  }
  function toJsonElement_0($receiver) {
    var tmp$, tmp$_0;
    if (Kotlin.equals($receiver, undefined) || $receiver == null)
      tmp$_0 = JsonNull_getInstance();
    else if (Kotlin.isNumber($receiver))
      tmp$_0 = new JsonNumber($receiver.toString());
    else if (typeof $receiver === 'string')
      tmp$_0 = new JsonString($receiver);
    else if (typeof $receiver === 'boolean')
      tmp$_0 = new JsonBoolean($receiver);
    else if (Array.isArray($receiver)) {
      var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$($receiver.length);
      var tmp$_1;
      for (tmp$_1 = 0; tmp$_1 !== $receiver.length; ++tmp$_1) {
        var item = $receiver[tmp$_1];
        destination.add_11rb$(toJsonElement_0(item));
      }
      tmp$_0 = new JsonArray(destination);
    }
     else {
      var json = Kotlin.isType(tmp$ = $receiver, Object) ? tmp$ : Kotlin.throwCCE();
      var $receiver_0 = Object.keys(json);
      var destination_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$($receiver_0.length);
      var tmp$_2;
      for (tmp$_2 = 0; tmp$_2 !== $receiver_0.length; ++tmp$_2) {
        var item_0 = $receiver_0[tmp$_2];
        destination_0.add_11rb$(to(item_0, toJsonElement_0(json[item_0])));
      }
      tmp$_0 = JsonObject_init(destination_0);
    }
    return tmp$_0;
  }
  function toJsonString$lambda(f, node) {
    return node;
  }
  function toJsonString($receiver) {
    return JSON.stringify(toJavaScriptObject($receiver), toJsonString$lambda, 2);
  }
  function toJavaScriptObject($receiver) {
    var tmp$;
    if (Kotlin.isType($receiver, JsonObject)) {
      var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$($receiver.size);
      var tmp$_0;
      tmp$_0 = $receiver.entries.iterator();
      while (tmp$_0.hasNext()) {
        var item = tmp$_0.next();
        destination.add_11rb$(to(item.key, toJavaScriptObject(item.value)));
      }
      tmp$ = json(Kotlin.kotlin.collections.copyToArray(destination).slice());
    }
     else if (Kotlin.isType($receiver, JsonArray)) {
      var destination_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
      var tmp$_1;
      tmp$_1 = $receiver.iterator();
      while (tmp$_1.hasNext()) {
        var item_0 = tmp$_1.next();
        destination_0.add_11rb$(toJavaScriptObject(item_0));
      }
      tmp$ = destination_0;
    }
     else if (Kotlin.isType($receiver, JsonString))
      tmp$ = $receiver.value;
    else if (Kotlin.isType($receiver, JsonBoolean))
      tmp$ = $receiver.value;
    else if (Kotlin.isType($receiver, JsonNull))
      tmp$ = null;
    else if (Kotlin.isType($receiver, JsonNumber))
      tmp$ = toDouble_0($receiver);
    else
      tmp$ = Kotlin.noWhenBranchMatched();
    return tmp$;
  }
  function MersenneTwisterFast() {
    MersenneTwisterFast$Companion_getInstance();
    this.mt_57rlty$_0 = null;
    this.mti_57rlty$_0 = 0;
    this.mag01_57rlty$_0 = null;
    this.__nextNextGaussian_57rlty$_0 = 0;
    this.__haveNextNextGaussian_57rlty$_0 = false;
  }
  MersenneTwisterFast.prototype.stateEquals_57rltt$ = function (other) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4, tmp$_5, tmp$_6, tmp$_7, tmp$_8, tmp$_9, tmp$_10, tmp$_11, tmp$_12;
    if (other === this)
      return true;
    if (other == null)
      return false;
    if (this.mti_57rlty$_0 !== other.mti_57rlty$_0)
      return false;
    tmp$_0 = get_indices_0((tmp$ = this.mag01_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE());
    tmp$_1 = tmp$_0.first;
    tmp$_2 = tmp$_0.last;
    tmp$_3 = tmp$_0.step;
    for (var x = tmp$_1; x <= tmp$_2; x += tmp$_3)
      if (((tmp$_4 = this.mag01_57rlty$_0) != null ? tmp$_4 : Kotlin.throwNPE())[x] !== ((tmp$_5 = other.mag01_57rlty$_0) != null ? tmp$_5 : Kotlin.throwNPE())[x])
        return false;
    tmp$_7 = get_indices_0((tmp$_6 = this.mt_57rlty$_0) != null ? tmp$_6 : Kotlin.throwNPE());
    tmp$_8 = tmp$_7.first;
    tmp$_9 = tmp$_7.last;
    tmp$_10 = tmp$_7.step;
    for (var x_0 = tmp$_8; x_0 <= tmp$_9; x_0 += tmp$_10)
      if (((tmp$_11 = this.mt_57rlty$_0) != null ? tmp$_11 : Kotlin.throwNPE())[x_0] !== ((tmp$_12 = other.mt_57rlty$_0) != null ? tmp$_12 : Kotlin.throwNPE())[x_0])
        return false;
    return true;
  };
  MersenneTwisterFast.prototype.setSeed_s8cxhz$ = function (seed) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4, tmp$_5;
    this.__haveNextNextGaussian_57rlty$_0 = false;
    this.mt_57rlty$_0 = Kotlin.newArray(MersenneTwisterFast$Companion_getInstance().N_0, 0);
    var $receiver = Kotlin.newArray(2, 0);
    $receiver[0] = 0;
    $receiver[1] = MersenneTwisterFast$Companion_getInstance().MATRIX_A_0;
    this.mag01_57rlty$_0 = $receiver;
    ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[0] = seed.and(new Kotlin.Long(-1, 268435455)).toInt();
    this.mti_57rlty$_0 = 1;
    while (this.mti_57rlty$_0 < MersenneTwisterFast$Companion_getInstance().N_0) {
      tmp$_4 = this.mti_57rlty$_0;
      tmp$_3 = ((tmp$_0 = this.mt_57rlty$_0) != null ? tmp$_0 : Kotlin.throwNPE())[this.mti_57rlty$_0 - 1 | 0];
      tmp$_2 = ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[this.mti_57rlty$_0 - 1 | 0] >>> 30;
      ((tmp$_5 = this.mt_57rlty$_0) != null ? tmp$_5 : Kotlin.throwNPE())[tmp$_4] = Kotlin.imul(1812433253, tmp$_3 ^ tmp$_2) + this.mti_57rlty$_0 | 0;
      this.mti_57rlty$_0 = this.mti_57rlty$_0 + 1 | 0;
    }
  };
  MersenneTwisterFast.prototype.setSeed_q5rwfd$ = function (array) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4, tmp$_5, tmp$_6, tmp$_7, tmp$_8, tmp$_9, tmp$_10, tmp$_11, tmp$_12, tmp$_13, tmp$_14, tmp$_15;
    if (array.length === 0)
      throw new IllegalArgumentException('Array length must be greater than zero');
    var i;
    var j;
    var k;
    this.setSeed_s8cxhz$(Kotlin.Long.fromInt(19650218));
    i = 1;
    j = 0;
    k = MersenneTwisterFast$Companion_getInstance().N_0 > array.length ? MersenneTwisterFast$Companion_getInstance().N_0 : array.length;
    while (k !== 0) {
      tmp$_3 = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[i];
      tmp$_2 = Kotlin.imul(((tmp$_0 = this.mt_57rlty$_0) != null ? tmp$_0 : Kotlin.throwNPE())[i - 1 | 0] ^ ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[i - 1 | 0] >>> 30, 1664525);
      ((tmp$_4 = this.mt_57rlty$_0) != null ? tmp$_4 : Kotlin.throwNPE())[i] = (tmp$_3 ^ tmp$_2) + array[j] + j | 0;
      i = i + 1 | 0;
      j = j + 1 | 0;
      if (i >= MersenneTwisterFast$Companion_getInstance().N_0) {
        ((tmp$_6 = this.mt_57rlty$_0) != null ? tmp$_6 : Kotlin.throwNPE())[0] = ((tmp$_5 = this.mt_57rlty$_0) != null ? tmp$_5 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0];
        i = 1;
      }
      if (j >= array.length)
        j = 0;
      k = k - 1 | 0;
    }
    k = MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0;
    while (k !== 0) {
      tmp$_11 = ((tmp$_7 = this.mt_57rlty$_0) != null ? tmp$_7 : Kotlin.throwNPE())[i];
      tmp$_10 = Kotlin.imul(((tmp$_8 = this.mt_57rlty$_0) != null ? tmp$_8 : Kotlin.throwNPE())[i - 1 | 0] ^ ((tmp$_9 = this.mt_57rlty$_0) != null ? tmp$_9 : Kotlin.throwNPE())[i - 1 | 0] >>> 30, 1566083941);
      ((tmp$_12 = this.mt_57rlty$_0) != null ? tmp$_12 : Kotlin.throwNPE())[i] = (tmp$_11 ^ tmp$_10) - i | 0;
      i = i + 1 | 0;
      if (i >= MersenneTwisterFast$Companion_getInstance().N_0) {
        ((tmp$_14 = this.mt_57rlty$_0) != null ? tmp$_14 : Kotlin.throwNPE())[0] = ((tmp$_13 = this.mt_57rlty$_0) != null ? tmp$_13 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0];
        i = 1;
      }
      k = k - 1 | 0;
    }
    ((tmp$_15 = this.mt_57rlty$_0) != null ? tmp$_15 : Kotlin.throwNPE())[0] = (new Kotlin.Long(-2147483648, 0)).toInt();
  };
  MersenneTwisterFast.prototype.nextInt = function () {
    var tmp$, tmp$_0;
    var y;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    return y;
  };
  MersenneTwisterFast.prototype.nextShort = function () {
    var tmp$, tmp$_0;
    var y;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    return Kotlin.toShort(y >>> 16);
  };
  MersenneTwisterFast.prototype.nextChar = function () {
    var tmp$, tmp$_0;
    var y;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    return Kotlin.toBoxedChar(Kotlin.toChar(y >>> 16));
  };
  MersenneTwisterFast.prototype.nextBoolean = function () {
    var tmp$, tmp$_0;
    var y;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    return y >>> 31 !== 0;
  };
  MersenneTwisterFast.prototype.nextBoolean_mx4ult$ = function (probability) {
    var tmp$, tmp$_0;
    var y;
    if (probability < 0.0 || probability > 1.0)
      throw new IllegalArgumentException('probability must be between 0.0 and 1.0 inclusive.');
    if (probability === 0.0)
      return false;
    else if (probability === 1.0)
      return true;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    return (y >>> 8) / (1 << 24) < probability;
  };
  MersenneTwisterFast.prototype.nextBoolean_14dthe$ = function (probability) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var y;
    var z;
    if (probability < 0.0 || probability > 1.0)
      throw new IllegalArgumentException('probability must be between 0.0 and 1.0 inclusive.');
    if (probability === 0.0)
      return false;
    else if (probability === 1.0)
      return true;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk_0;
      var mt_0 = this.mt_57rlty$_0;
      var mag01_0 = this.mag01_57rlty$_0;
      kk_0 = 0;
      while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[kk_0] = mt_0[kk_0 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
        kk_0 = kk_0 + 1 | 0;
      }
      while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[kk_0] = mt_0[kk_0 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
        kk_0 = kk_0 + 1 | 0;
      }
      z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt_0[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_0[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
      this.mti_57rlty$_0 = 0;
    }
    z = ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[tmp$_2 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_2 + 1 | 0, tmp$_2];
    z = z ^ z >>> 11;
    z = z ^ z << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    z = z ^ z << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    z = z ^ z >>> 18;
    return Kotlin.Long.fromInt(y >>> 6).shiftLeft(27).add(Kotlin.Long.fromInt(z >>> 5)).toNumber() / (new Kotlin.Long(0, 2097152)).toNumber() < probability;
  };
  MersenneTwisterFast.prototype.nextByte = function () {
    var tmp$, tmp$_0;
    var y;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    return Kotlin.toByte(y >>> 24);
  };
  MersenneTwisterFast.prototype.nextBytes_fqrh44$ = function (bytes) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4;
    var y;
    tmp$ = get_indices_1(bytes);
    tmp$_0 = tmp$.first;
    tmp$_1 = tmp$.last;
    tmp$_2 = tmp$.step;
    for (var x = tmp$_0; x <= tmp$_1; x += tmp$_2) {
      if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
        var kk;
        var mt = this.mt_57rlty$_0;
        var mag01 = this.mag01_57rlty$_0;
        kk = 0;
        while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
          y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
          kk = kk + 1 | 0;
        }
        while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
          y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
          kk = kk + 1 | 0;
        }
        y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        this.mti_57rlty$_0 = 0;
      }
      y = ((tmp$_3 = this.mt_57rlty$_0) != null ? tmp$_3 : Kotlin.throwNPE())[tmp$_4 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_4 + 1 | 0, tmp$_4];
      y = y ^ y >>> 11;
      y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
      y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
      y = y ^ y >>> 18;
      bytes[x] = Kotlin.toByte(y >>> 24);
    }
  };
  MersenneTwisterFast.prototype.nextLong = function () {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var y;
    var z;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk_0;
      var mt_0 = this.mt_57rlty$_0;
      var mag01_0 = this.mag01_57rlty$_0;
      kk_0 = 0;
      while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[kk_0] = mt_0[kk_0 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
        kk_0 = kk_0 + 1 | 0;
      }
      while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[kk_0] = mt_0[kk_0 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
        kk_0 = kk_0 + 1 | 0;
      }
      z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt_0[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_0[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
      this.mti_57rlty$_0 = 0;
    }
    z = ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[tmp$_2 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_2 + 1 | 0, tmp$_2];
    z = z ^ z >>> 11;
    z = z ^ z << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    z = z ^ z << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    z = z ^ z >>> 18;
    return Kotlin.Long.fromInt(y).shiftLeft(32).add(Kotlin.Long.fromInt(z));
  };
  MersenneTwisterFast.prototype.nextLong_s8cxhz$ = function (n) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    if (n.compareTo_11rb$(Kotlin.Long.fromInt(0)) <= 0)
      throw new IllegalArgumentException('n must be positive, got: ' + Kotlin.toString(n));
    var bits;
    var val;
    do {
      var y;
      var z;
      if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
        var kk;
        var mt = this.mt_57rlty$_0;
        var mag01 = this.mag01_57rlty$_0;
        kk = 0;
        while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
          y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
          kk = kk + 1 | 0;
        }
        while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
          y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
          kk = kk + 1 | 0;
        }
        y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        this.mti_57rlty$_0 = 0;
      }
      y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
      y = y ^ y >>> 11;
      y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
      y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
      y = y ^ y >>> 18;
      if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
        var kk_0;
        var mt_0 = this.mt_57rlty$_0;
        var mag01_0 = this.mag01_57rlty$_0;
        kk_0 = 0;
        while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
          z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt_0[kk_0] = mt_0[kk_0 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
          kk_0 = kk_0 + 1 | 0;
        }
        while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
          z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt_0[kk_0] = mt_0[kk_0 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
          kk_0 = kk_0 + 1 | 0;
        }
        z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_0[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
        this.mti_57rlty$_0 = 0;
      }
      z = ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[tmp$_2 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_2 + 1 | 0, tmp$_2];
      z = z ^ z >>> 11;
      z = z ^ z << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
      z = z ^ z << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
      z = z ^ z >>> 18;
      bits = Kotlin.Long.fromInt(y).shiftLeft(32).add(Kotlin.Long.fromInt(z)).shiftRightUnsigned(1);
      val = bits.modulo(n);
    }
     while (bits.subtract(val).add(n.subtract(Kotlin.Long.fromInt(1))).compareTo_11rb$(Kotlin.Long.fromInt(0)) < 0);
    return val;
  };
  MersenneTwisterFast.prototype.nextDouble = function () {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var y;
    var z;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk_0;
      var mt_0 = this.mt_57rlty$_0;
      var mag01_0 = this.mag01_57rlty$_0;
      kk_0 = 0;
      while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[kk_0] = mt_0[kk_0 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
        kk_0 = kk_0 + 1 | 0;
      }
      while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[kk_0] = mt_0[kk_0 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
        kk_0 = kk_0 + 1 | 0;
      }
      z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt_0[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_0[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
      this.mti_57rlty$_0 = 0;
    }
    z = ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[tmp$_2 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_2 + 1 | 0, tmp$_2];
    z = z ^ z >>> 11;
    z = z ^ z << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    z = z ^ z << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    z = z ^ z >>> 18;
    return Kotlin.Long.fromInt(y >>> 6).shiftLeft(27).add(Kotlin.Long.fromInt(z >>> 5)).toNumber() / (new Kotlin.Long(0, 2097152)).toNumber();
  };
  MersenneTwisterFast.prototype.nextDouble_dqye30$ = function (includeZero, includeOne) {
    var d;
    do {
      d = this.nextDouble();
      if (includeOne && this.nextBoolean())
        d += 1.0;
    }
     while (d > 1.0 || (!includeZero && d === 0.0));
    return d;
  };
  MersenneTwisterFast.prototype.clearGaussian = function () {
    this.__haveNextNextGaussian_57rlty$_0 = false;
  };
  MersenneTwisterFast.prototype.nextGaussian = function () {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4, tmp$_5, tmp$_6;
    if (this.__haveNextNextGaussian_57rlty$_0) {
      this.__haveNextNextGaussian_57rlty$_0 = false;
      return this.__nextNextGaussian_57rlty$_0;
    }
     else {
      var v1;
      var v2;
      var s;
      do {
        var y;
        var z;
        var a;
        var b;
        if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
          var kk;
          var mt = this.mt_57rlty$_0;
          var mag01 = this.mag01_57rlty$_0;
          kk = 0;
          while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
            y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
            kk = kk + 1 | 0;
          }
          while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
            y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
            kk = kk + 1 | 0;
          }
          y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
          this.mti_57rlty$_0 = 0;
        }
        y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
        y = y ^ y >>> 11;
        y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
        y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
        y = y ^ y >>> 18;
        if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
          var kk_0;
          var mt_0 = this.mt_57rlty$_0;
          var mag01_0 = this.mag01_57rlty$_0;
          kk_0 = 0;
          while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
            z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt_0[kk_0] = mt_0[kk_0 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
            kk_0 = kk_0 + 1 | 0;
          }
          while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
            z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt_0[kk_0] = mt_0[kk_0 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
            kk_0 = kk_0 + 1 | 0;
          }
          z = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt_0[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_0[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ z >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[z & 1];
          this.mti_57rlty$_0 = 0;
        }
        z = ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[tmp$_2 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_2 + 1 | 0, tmp$_2];
        z = z ^ z >>> 11;
        z = z ^ z << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
        z = z ^ z << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
        z = z ^ z >>> 18;
        if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
          var kk_1;
          var mt_1 = this.mt_57rlty$_0;
          var mag01_1 = this.mag01_57rlty$_0;
          kk_1 = 0;
          while (kk_1 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
            a = (mt_1 != null ? mt_1 : Kotlin.throwNPE())[kk_1] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_1[kk_1 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt_1[kk_1] = mt_1[kk_1 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ a >>> 1 ^ (mag01_1 != null ? mag01_1 : Kotlin.throwNPE())[a & 1];
            kk_1 = kk_1 + 1 | 0;
          }
          while (kk_1 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
            a = (mt_1 != null ? mt_1 : Kotlin.throwNPE())[kk_1] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_1[kk_1 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt_1[kk_1] = mt_1[kk_1 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ a >>> 1 ^ (mag01_1 != null ? mag01_1 : Kotlin.throwNPE())[a & 1];
            kk_1 = kk_1 + 1 | 0;
          }
          a = (mt_1 != null ? mt_1 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_1[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt_1[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_1[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ a >>> 1 ^ (mag01_1 != null ? mag01_1 : Kotlin.throwNPE())[a & 1];
          this.mti_57rlty$_0 = 0;
        }
        a = ((tmp$_3 = this.mt_57rlty$_0) != null ? tmp$_3 : Kotlin.throwNPE())[tmp$_4 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_4 + 1 | 0, tmp$_4];
        a = a ^ a >>> 11;
        a = a ^ a << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
        a = a ^ a << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
        a = a ^ a >>> 18;
        if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
          var kk_2;
          var mt_2 = this.mt_57rlty$_0;
          var mag01_2 = this.mag01_57rlty$_0;
          kk_2 = 0;
          while (kk_2 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
            b = (mt_2 != null ? mt_2 : Kotlin.throwNPE())[kk_2] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_2[kk_2 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt_2[kk_2] = mt_2[kk_2 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ b >>> 1 ^ (mag01_2 != null ? mag01_2 : Kotlin.throwNPE())[b & 1];
            kk_2 = kk_2 + 1 | 0;
          }
          while (kk_2 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
            b = (mt_2 != null ? mt_2 : Kotlin.throwNPE())[kk_2] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_2[kk_2 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
            mt_2[kk_2] = mt_2[kk_2 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ b >>> 1 ^ (mag01_2 != null ? mag01_2 : Kotlin.throwNPE())[b & 1];
            kk_2 = kk_2 + 1 | 0;
          }
          b = (mt_2 != null ? mt_2 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_2[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt_2[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_2[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ b >>> 1 ^ (mag01_2 != null ? mag01_2 : Kotlin.throwNPE())[b & 1];
          this.mti_57rlty$_0 = 0;
        }
        b = ((tmp$_5 = this.mt_57rlty$_0) != null ? tmp$_5 : Kotlin.throwNPE())[tmp$_6 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_6 + 1 | 0, tmp$_6];
        b = b ^ b >>> 11;
        b = b ^ b << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
        b = b ^ b << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
        b = b ^ b >>> 18;
        v1 = 2 * (Kotlin.Long.fromInt(y >>> 6).shiftLeft(27).add(Kotlin.Long.fromInt(z >>> 5)).toNumber() / (new Kotlin.Long(0, 2097152)).toNumber()) - 1;
        v2 = 2 * (Kotlin.Long.fromInt(a >>> 6).shiftLeft(27).add(Kotlin.Long.fromInt(b >>> 5)).toNumber() / (new Kotlin.Long(0, 2097152)).toNumber()) - 1;
        s = v1 * v1 + v2 * v2;
      }
       while (s >= 1 || s === 0.0);
      var multiplier = Math.sqrt(-2 * Math.log(s) / s);
      this.__nextNextGaussian_57rlty$_0 = v2 * multiplier;
      this.__haveNextNextGaussian_57rlty$_0 = true;
      return v1 * multiplier;
    }
  };
  MersenneTwisterFast.prototype.nextFloat = function () {
    var tmp$, tmp$_0;
    var y;
    if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
      var kk;
      var mt = this.mt_57rlty$_0;
      var mag01 = this.mag01_57rlty$_0;
      kk = 0;
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
        y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        kk = kk + 1 | 0;
      }
      y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
      mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
      this.mti_57rlty$_0 = 0;
    }
    y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
    y = y ^ y >>> 11;
    y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
    y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
    y = y ^ y >>> 18;
    return (y >>> 8) / (1 << 24);
  };
  MersenneTwisterFast.prototype.nextFloat_dqye30$ = function (includeZero, includeOne) {
    var d;
    do {
      d = this.nextFloat();
      if (includeOne && this.nextBoolean())
        d += 1.0;
    }
     while (d > 1.0 || (!includeZero && d === 0.0));
    return d;
  };
  MersenneTwisterFast.prototype.nextInt_za3lpa$ = function (n) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    if (n <= 0)
      throw new IllegalArgumentException('n must be positive, got: ' + Kotlin.toString(n));
    if ((n & -n) === n) {
      var y;
      if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
        var kk;
        var mt = this.mt_57rlty$_0;
        var mag01 = this.mag01_57rlty$_0;
        kk = 0;
        while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
          y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt[kk] = mt[kk + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
          kk = kk + 1 | 0;
        }
        while (kk < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
          y = (mt != null ? mt : Kotlin.throwNPE())[kk] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[kk + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt[kk] = mt[kk + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
          kk = kk + 1 | 0;
        }
        y = (mt != null ? mt : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y >>> 1 ^ (mag01 != null ? mag01 : Kotlin.throwNPE())[y & 1];
        this.mti_57rlty$_0 = 0;
      }
      y = ((tmp$ = this.mt_57rlty$_0) != null ? tmp$ : Kotlin.throwNPE())[tmp$_0 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_0 + 1 | 0, tmp$_0];
      y = y ^ y >>> 11;
      y = y ^ y << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
      y = y ^ y << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
      y = y ^ y >>> 18;
      return Kotlin.Long.fromInt(n).multiply(Kotlin.Long.fromInt(y >>> 1)).shiftRight(31).toInt();
    }
    var bits;
    var val;
    do {
      var y_0;
      if (this.mti_57rlty$_0 >= MersenneTwisterFast$Companion_getInstance().N_0) {
        var kk_0;
        var mt_0 = this.mt_57rlty$_0;
        var mag01_0 = this.mag01_57rlty$_0;
        kk_0 = 0;
        while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - MersenneTwisterFast$Companion_getInstance().M_0 | 0)) {
          y_0 = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt_0[kk_0] = mt_0[kk_0 + MersenneTwisterFast$Companion_getInstance().M_0 | 0] ^ y_0 >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[y_0 & 1];
          kk_0 = kk_0 + 1 | 0;
        }
        while (kk_0 < (MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0)) {
          y_0 = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[kk_0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[kk_0 + 1 | 0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
          mt_0[kk_0] = mt_0[kk_0 + (MersenneTwisterFast$Companion_getInstance().M_0 - MersenneTwisterFast$Companion_getInstance().N_0) | 0] ^ y_0 >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[y_0 & 1];
          kk_0 = kk_0 + 1 | 0;
        }
        y_0 = (mt_0 != null ? mt_0 : Kotlin.throwNPE())[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] & MersenneTwisterFast$Companion_getInstance().UPPER_MASK_0 | mt_0[0] & MersenneTwisterFast$Companion_getInstance().LOWER_MASK_0;
        mt_0[MersenneTwisterFast$Companion_getInstance().N_0 - 1 | 0] = mt_0[MersenneTwisterFast$Companion_getInstance().M_0 - 1 | 0] ^ y_0 >>> 1 ^ (mag01_0 != null ? mag01_0 : Kotlin.throwNPE())[y_0 & 1];
        this.mti_57rlty$_0 = 0;
      }
      y_0 = ((tmp$_1 = this.mt_57rlty$_0) != null ? tmp$_1 : Kotlin.throwNPE())[tmp$_2 = this.mti_57rlty$_0, this.mti_57rlty$_0 = tmp$_2 + 1 | 0, tmp$_2];
      y_0 = y_0 ^ y_0 >>> 11;
      y_0 = y_0 ^ y_0 << 7 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_B_0;
      y_0 = y_0 ^ y_0 << 15 & MersenneTwisterFast$Companion_getInstance().TEMPERING_MASK_C_0;
      y_0 = y_0 ^ y_0 >>> 18;
      bits = y_0 >>> 1;
      val = bits % n;
    }
     while ((bits - val + (n - 1) | 0) < 0);
    return val;
  };
  function MersenneTwisterFast$Companion() {
    MersenneTwisterFast$Companion_instance = this;
    this.serialVersionUID_0 = new Kotlin.Long(-1478821509, -1913798197);
    this.N_0 = 624;
    this.M_0 = 397;
    this.MATRIX_A_0 = (new Kotlin.Long(-1727483681, 0)).toInt();
    this.UPPER_MASK_0 = (new Kotlin.Long(-2147483648, 0)).toInt();
    this.LOWER_MASK_0 = 2147483647;
    this.TEMPERING_MASK_B_0 = (new Kotlin.Long(-1658038656, 0)).toInt();
    this.TEMPERING_MASK_C_0 = (new Kotlin.Long(-272236544, 0)).toInt();
  }
  MersenneTwisterFast$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var MersenneTwisterFast$Companion_instance = null;
  function MersenneTwisterFast$Companion_getInstance() {
    if (MersenneTwisterFast$Companion_instance === null) {
      new MersenneTwisterFast$Companion();
    }
    return MersenneTwisterFast$Companion_instance;
  }
  MersenneTwisterFast.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'MersenneTwisterFast',
    interfaces: []
  };
  function MersenneTwisterFast_init($this) {
    $this = $this || Object.create(MersenneTwisterFast.prototype);
    MersenneTwisterFast_init_0(Kotlin.Long.fromNumber(Math.random() * (new Kotlin.Long(-1, 2147483647)).toNumber()), $this);
    return $this;
  }
  function MersenneTwisterFast_init_0(seed, $this) {
    $this = $this || Object.create(MersenneTwisterFast.prototype);
    MersenneTwisterFast.call($this);
    $this.setSeed_s8cxhz$(seed);
    return $this;
  }
  function MersenneTwisterFast_init_1(array, $this) {
    $this = $this || Object.create(MersenneTwisterFast.prototype);
    MersenneTwisterFast.call($this);
    $this.setSeed_q5rwfd$(array);
    return $this;
  }
  function shuffled($receiver, list) {
    var tmp$;
    var $receiver_0 = toList(list);
    var arr = Kotlin.kotlin.collections.copyToArray($receiver_0);
    tmp$ = downTo(arr.length, 2).iterator();
    while (tmp$.hasNext()) {
      var i = tmp$.next();
      swap(arr, i - 1 | 0, $receiver.nextInt_za3lpa$(i));
    }
    return toList_0(arr);
  }
  function swap($receiver, i, j) {
    var tmp = $receiver[i];
    $receiver[i] = $receiver[j];
    $receiver[j] = tmp;
  }
  function troublesomeClasses() {
    return emptyList();
  }
  function toXmlDocument($receiver) {
    return toXmlDocument_0((new DOMParser()).parseFromString($receiver, 'application/xml'));
  }
  function toXmlDocument$lambda(node) {
    return toXmlNode(node);
  }
  function toXmlDocument_0($receiver) {
    return new XmlDocument(null, map_1($receiver.childNodes, toXmlDocument$lambda));
  }
  function toXmlNode($receiver) {
    var tmp$;
    if (Kotlin.isType($receiver, Element))
      return toXmlElement($receiver);
    else if (Kotlin.isType($receiver, Text))
      return new XmlText($receiver.wholeText, false);
    else if (Kotlin.isType($receiver, CDATASection))
      return new XmlText($receiver.wholeText, true);
    else if (Kotlin.isType($receiver, ProcessingInstruction)) {
      var tmp$_0 = $receiver.target;
      var $receiver_0 = $receiver.data;
      return new XmlProcessingInstruction(tmp$_0, $receiver_0.length > 0 ? $receiver_0 : null);
    }
     else if (Kotlin.isType($receiver, Comment)) {
      return new XmlComment((tmp$ = $receiver.textContent) != null ? tmp$ : '');
    }
     else
      throw new IllegalArgumentException('cannot convert ' + Kotlin.toString(Kotlin.getKClassFromExpression($receiver).simpleName) + ' to XmlNode');
  }
  function toXmlElement$lambda(it) {
    return toXmlNode(it);
  }
  function toXmlElement($receiver) {
    return new XmlElement(new QName($receiver.localName, $receiver.namespaceURI, $receiver.prefix), emptyMap(), map_1($receiver.childNodes, toXmlElement$lambda));
  }
  function map_1($receiver, f) {
    var $receiver_0 = until(0, $receiver.length);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver_0, 10));
    var tmp$;
    tmp$ = $receiver_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0;
      destination.add_11rb$(f((tmp$_0 = $receiver[item]) != null ? tmp$_0 : Kotlin.throwNPE()));
    }
    return destination;
  }
  function toXmlString($receiver) {
    return (new XMLSerializer()).serializeToString(toDOM($receiver));
  }
  function toDOM($receiver) {
    var $receiver_0 = document.implementation.createDocument('', '', null);
    var tmp$;
    tmp$ = $receiver.children.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver_0.appendChild(toDOM_0(element));
    }
    return $receiver_0;
  }
  function toDOM_0($receiver) {
    var tmp$, tmp$_0, tmp$_1;
    if (Kotlin.isType($receiver, XmlText))
      return $receiver.asCData ? document.createCDATASection($receiver.text) : document.createTextNode($receiver.text);
    else if (Kotlin.isType($receiver, XmlElement)) {
      var $receiver_0 = document.createElementNS($receiver.name.namespaceURI, toDOM_1($receiver.name));
      var tmp$_2;
      tmp$_2 = $receiver.attributes.entries.iterator();
      while (tmp$_2.hasNext()) {
        var element = tmp$_2.next();
        var attrName = element.key;
        var attrValue = element.value;
        $receiver_0.setAttributeNS(attrName.namespaceURI, toDOM_1(attrName), attrValue);
      }
      var tmp$_3;
      tmp$_3 = $receiver.children.iterator();
      while (tmp$_3.hasNext()) {
        var element_0 = tmp$_3.next();
        $receiver_0.appendChild(toDOM_0(element_0));
      }
      return $receiver_0;
    }
     else if (Kotlin.isType($receiver, XmlComment))
      return document.createComment($receiver.text);
    else if (Kotlin.isType($receiver, XmlProcessingInstruction)) {
      tmp$ = $receiver.target;
      tmp$_1 = (tmp$_0 = $receiver.data) != null ? tmp$_0 : '';
      return document.createProcessingInstruction(tmp$, tmp$_1);
    }
     else
      return Kotlin.noWhenBranchMatched();
  }
  function toDOM_1($receiver) {
    var tmp$, tmp$_0;
    return ((tmp$_0 = (tmp$ = $receiver.prefix) != null ? tmp$ + Kotlin.toString(':') : null) != null ? tmp$_0 : '') + $receiver.localPart;
  }
  var package$com = _.com || (_.com = {});
  var package$natpryce = package$com.natpryce || (package$com.natpryce = {});
  var package$jsonk = package$natpryce.jsonk || (package$natpryce.jsonk = {});
  package$jsonk.JsonElement = JsonElement;
  package$jsonk.JsonObject_init_cbp3fy$ = JsonObject_init;
  package$jsonk.JsonObject_init_zchfik$ = JsonObject_init_0;
  package$jsonk.JsonObject = JsonObject;
  package$jsonk.withProperty_39czib$ = withProperty;
  package$jsonk.withProperties_9lonfc$ = withProperties;
  package$jsonk.removeProperty_zgdxmy$ = removeProperty;
  package$jsonk.JsonArray_init_8i0bc7$ = JsonArray_init;
  package$jsonk.JsonArray = JsonArray;
  package$jsonk.append_nteyoe$ = append;
  package$jsonk.appendAll_rup6gn$ = appendAll;
  package$jsonk.appendAll_j03c5x$ = appendAll_0;
  package$jsonk.remove_e77xxo$ = remove;
  package$jsonk.replace_2o11y0$ = replace;
  package$jsonk.JsonNumber_init_za3lpa$ = JsonNumber_init;
  package$jsonk.JsonNumber_init_s8cxhz$ = JsonNumber_init_0;
  package$jsonk.JsonNumber_init_14dthe$ = JsonNumber_init_1;
  package$jsonk.JsonNumber = JsonNumber;
  package$jsonk.toDouble_8rc2s6$ = toDouble_0;
  package$jsonk.toLong_8rc2s6$ = toLong_0;
  package$jsonk.toInt_8rc2s6$ = toInt_0;
  package$jsonk.JsonString = JsonString;
  package$jsonk.JsonBoolean = JsonBoolean;
  Object.defineProperty(package$jsonk, 'JsonNull', {
    get: JsonNull_getInstance
  });
  var package$snodge = package$natpryce.snodge || (package$natpryce.snodge = {});
  package$snodge.combine_ng7czy$ = combine;
  package$snodge.combine_teycvk$ = combine_0;
  package$snodge.plus_uajtxj$ = plus_5;
  package$snodge.mapped_ngvwb$ = mapped;
  package$snodge.withProbability_chj9xy$ = withProbability;
  package$snodge.and_uajtxj$ = and;
  package$snodge.filtered_ntfn6r$ = filtered;
  package$snodge.always_i5x0yv$ = always;
  package$snodge.always_yl67zr$ = always_0;
  package$snodge.repeat_enjbwb$ = repeat;
  package$snodge.map_7ioryi$ = map_0;
  package$snodge.mutants_23q2a$ = mutants;
  package$snodge.mutant_vq1p9g$ = mutant;
  package$snodge.sample_554uxs$ = sample;
  package$snodge.sample_87pr0y$ = sample_0;
  package$snodge.splice_4w02kr$ = splice;
  var package$bytes = package$snodge.bytes || (package$snodge.bytes = {});
  package$bytes.splice_fqrh44$ = splice_0;
  package$bytes.splice_eqqcv1$ = splice_1;
  var package$form = package$snodge.form || (package$snodge.form = {});
  package$form.form_9ih0sy$ = form;
  package$form.get_l475gz$ = get_0;
  package$form.get_keys_ye33rp$ = get_keys;
  package$form.removeSingleFieldValue = removeSingleFieldValue;
  package$form.removeField = removeField;
  package$form.mutateValue_72gdtb$ = mutateValue;
  package$form.addUniqueField_wotcd5$ = addUniqueField;
  package$form.defaultFormMutagens = defaultFormMutagens;
  var package$internal = package$snodge.internal || (package$snodge.internal = {});
  package$internal.replaceRange_jbr50s$ = replaceRange;
  package$internal.mapLazy_n8y9gc$ = mapLazy;
  var package$json = package$snodge.json || (package$snodge.json = {});
  package$json.walk_rdvzxt$ = walk;
  package$json.JsonMutagen$f$f = JsonMutagen$lambda$lambda;
  package$json.JsonMutagen$f = JsonMutagen$lambda;
  package$json.forStrings_403vme$ = forStrings;
  package$json.addArrayElement_36agbk$ = addArrayElement;
  package$json.addObjectProperty_36agbk$ = addObjectProperty;
  package$json.removeJsonObjectProperty = removeJsonObjectProperty;
  package$json.removeJsonArrayElement = removeJsonArrayElement;
  package$json.removeJsonElement = removeJsonElement;
  package$json.replaceJsonObjectProperty_36agbk$ = replaceJsonObjectProperty;
  package$json.replaceJsonArrayElement_36agbk$ = replaceJsonArrayElement;
  package$json.replaceJsonElement_36agbk$ = replaceJsonElement;
  package$json.reorderObjectProperties = reorderObjectProperties;
  package$json.reflectionMutagens = reflectionMutagens;
  package$json.defaultJsonMutagens = defaultJsonMutagens;
  package$json.allJsonMutagens = allJsonMutagens;
  var package$reflect = package$snodge.reflect || (package$snodge.reflect = {});
  package$reflect.replaceWithTroublesomeClassName = replaceWithTroublesomeClassName;
  var package$text = package$snodge.text || (package$snodge.text = {});
  package$text.splice_61zpoe$ = splice_2;
  package$text.splice_72gdtb$ = splice_3;
  package$text.possiblyMeaningfulStrings = possiblyMeaningfulStrings;
  package$text.replaceWithPossiblyMeaningfulText = replaceWithPossiblyMeaningfulText;
  var package$xml = package$snodge.xml || (package$snodge.xml = {});
  package$xml.walk_hn75bi$ = walk_1;
  package$xml.XmlMutagen$f$f = XmlMutagen$lambda$lambda;
  package$xml.XmlMutagen$f = XmlMutagen$lambda;
  package$xml.forStrings_1pa5su$ = forStrings_0;
  package$xml.removeAttribute = removeAttribute;
  package$xml.removeElement = removeElement;
  package$xml.removeNamespace = removeNamespace;
  package$xml.replaceNode$f = replaceNode$lambda;
  package$xml.replaceNodeIf_jvrbw5$ = replaceNodeIf;
  package$xml.replaceText_61zpoe$ = replaceText;
  package$xml.defaultXmlMutagens = defaultXmlMutagens;
  var package$xmlk = package$natpryce.xmlk || (package$natpryce.xmlk = {});
  package$xmlk.HasChildren = HasChildren;
  package$xmlk.XmlDeclaration = XmlDeclaration;
  package$xmlk.XmlDocument = XmlDocument;
  package$xmlk.XmlNode = XmlNode;
  package$xmlk.XmlText = XmlText;
  package$xmlk.QName = QName;
  package$xmlk.XmlElement = XmlElement;
  package$xmlk.withAttribute_637hgt$ = withAttribute;
  package$xmlk.minusAttribute_eije1e$ = minusAttribute;
  package$xmlk.plusChild_2bzdkf$ = plusChild;
  package$xmlk.minusChild_rbb5id$ = minusChild;
  package$xmlk.replaceChild_b71it3$ = replaceChild;
  package$xmlk.XmlProcessingInstruction = XmlProcessingInstruction;
  package$xmlk.XmlComment = XmlComment;
  package$jsonk.toJsonElement_pdl1vz$ = toJsonElement;
  package$jsonk.toJsonString_rdvzxt$ = toJsonString;
  Object.defineProperty(MersenneTwisterFast, 'Companion', {
    get: MersenneTwisterFast$Companion_getInstance
  });
  package$snodge.MersenneTwisterFast_init = MersenneTwisterFast_init;
  package$snodge.MersenneTwisterFast_init_s8cxhz$ = MersenneTwisterFast_init_0;
  package$snodge.MersenneTwisterFast_init_q5rwfd$ = MersenneTwisterFast_init_1;
  package$snodge.MersenneTwisterFast = MersenneTwisterFast;
  package$snodge.shuffled_utyit6$ = shuffled;
  package$reflect.troublesomeClasses = troublesomeClasses;
  package$xmlk.toXmlDocument_pdl1vz$ = toXmlDocument;
  package$xmlk.toXmlDocument_4wc2mh$ = toXmlDocument_0;
  package$xmlk.toXmlString_hn75bi$ = toXmlString;
  package$xmlk.toDOM_hn75bi$ = toDOM;
  package$xmlk.toDOM_kc9ie1$ = toDOM_0;
  exampleElements = listOf([JsonNull_getInstance(), new JsonBoolean(true), new JsonBoolean(false), JsonNumber_init(99), JsonNumber_init(-99), JsonNumber_init_1(0.0), JsonNumber_init_1(1.0), JsonNumber_init_1(-1.0), new JsonString('a string'), JsonArray_init([]), new JsonObject()]);
  Kotlin.defineModule('snodge', _);
  return _;
}));

//# sourceMappingURL=snodge.js.map
