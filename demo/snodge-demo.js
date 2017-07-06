(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'snodge'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('snodge'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'snodge-demo'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'snodge-demo'.");
    }
    if (typeof snodge === 'undefined') {
      throw new Error("Error loading module 'snodge-demo'. Its dependency 'snodge' was not found. Please, check whether 'snodge' is loaded prior to 'snodge-demo'.");
    }
    root['snodge-demo'] = factory(typeof this['snodge-demo'] === 'undefined' ? {} : this['snodge-demo'], kotlin, snodge);
  }
}(this, function (_, Kotlin, $module$snodge) {
  'use strict';
  var MersenneTwisterFast_init = $module$snodge.com.natpryce.snodge.MersenneTwisterFast_init;
  var replaceWithPossiblyMeaningfulText = $module$snodge.com.natpryce.snodge.text.replaceWithPossiblyMeaningfulText;
  var splice = $module$snodge.com.natpryce.snodge.text.splice_72gdtb$;
  var to = Kotlin.kotlin.to_ujzrz7$;
  var defaultXmlMutagens = $module$snodge.com.natpryce.snodge.xml.defaultXmlMutagens;
  var forStrings = $module$snodge.com.natpryce.snodge.xml.forStrings_1pa5su$;
  var defaultJsonMutagens = $module$snodge.com.natpryce.snodge.json.defaultJsonMutagens;
  var forStrings_0 = $module$snodge.com.natpryce.snodge.json.forStrings_403vme$;
  var mapOf = Kotlin.kotlin.collections.mapOf_qfcya0$;
  var IllegalStateException = Kotlin.kotlin.IllegalStateException;
  var mutant = $module$snodge.com.natpryce.snodge.mutant_vq1p9g$;
  var addClass = Kotlin.kotlin.dom.addClass_hhb33f$;
  var removeClass = Kotlin.kotlin.dom.removeClass_hhb33f$;
  var random;
  var originalTextArea;
  var mutantTextArea;
  var inputTypeSelector;
  function Format(example, mutagen) {
    this.example = example;
    this.mutagen = mutagen;
  }
  Format.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Format',
    interfaces: []
  };
  Format.prototype.component1 = function () {
    return this.example;
  };
  Format.prototype.component2 = function () {
    return this.mutagen;
  };
  Format.prototype.copy_baqlwl$ = function (example, mutagen) {
    return new Format(example === void 0 ? this.example : example, mutagen === void 0 ? this.mutagen : mutagen);
  };
  Format.prototype.toString = function () {
    return 'Format(example=' + Kotlin.toString(this.example) + (', mutagen=' + Kotlin.toString(this.mutagen)) + ')';
  };
  Format.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.example) | 0;
    result = result * 31 + Kotlin.hashCode(this.mutagen) | 0;
    return result;
  };
  Format.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.example, other.example) && Kotlin.equals(this.mutagen, other.mutagen)))));
  };
  var jsonExample;
  var xmlExample;
  var textExample;
  function dataTypes$lambda() {
    return splice(replaceWithPossiblyMeaningfulText());
  }
  function dataTypes$lambda_0() {
    return forStrings(defaultXmlMutagens());
  }
  function dataTypes$lambda_1() {
    return forStrings_0(defaultJsonMutagens());
  }
  var dataTypes;
  function main$timerTick(closure$timer) {
    return function closure$timerTick() {
      var tmp$;
      mutate();
      tmp$ = Kotlin.getCallableRef('timerTick', function () {
        return closure$timerTick();
      });
      closure$timer.v = window.setTimeout(tmp$, 150);
    };
  }
  function main$lambda(closure$timerTick, closure$timer) {
    return function (event) {
      var tmp$;
      tmp$ = Kotlin.getCallableRef('timerTick', function () {
        return closure$timerTick();
      });
      closure$timer.v = window.setTimeout(tmp$, 500);
      event.preventDefault();
    };
  }
  function main$lambda_0(closure$timer) {
    return function (event) {
      var tmp$;
      if ((tmp$ = closure$timer.v) != null) {
        window.clearTimeout(tmp$);
      }
      mutate();
      event.preventDefault();
    };
  }
  function main$lambda_1(f) {
    showExampleOfSelectedType();
  }
  function main(args) {
    var tmp$;
    var mutateButton = Kotlin.isType(tmp$ = document.getElementById('mutate'), HTMLButtonElement) ? tmp$ : Kotlin.throwCCE();
    var timer = {v: null};
    var timerTick = main$timerTick(timer);
    mutateButton.onmousedown = main$lambda(timerTick, timer);
    mutateButton.onmouseup = main$lambda_0(timer);
    showExampleOfSelectedType();
    inputTypeSelector.onchange = main$lambda_1;
  }
  function showExampleOfSelectedType() {
    var tmp$, tmp$_0;
    originalTextArea.value = (tmp$_0 = (tmp$ = dataTypes.get_11rb$(inputTypeSelector.value)) != null ? tmp$.example : null) != null ? tmp$_0 : '';
  }
  function selectedMutagen(code) {
    if (Kotlin.equals(code, 'text'))
      return splice(replaceWithPossiblyMeaningfulText());
    else if (Kotlin.equals(code, 'json'))
      return forStrings_0(defaultJsonMutagens());
    else if (Kotlin.equals(code, 'xml'))
      return forStrings(defaultXmlMutagens());
    else
      throw new IllegalStateException('unrecognised input type: ' + code);
  }
  function mutate() {
    var tmp$;
    var originalText = originalTextArea.value;
    try {
      var mutantText = mutant(random, selectedMutagen(inputTypeSelector.value), originalText);
      mutantTextArea.value = mutantText;
      clearError();
    }
     catch (e) {
      if (Kotlin.isType(e, SyntaxError)) {
        reportError((tmp$ = e.message) != null ? tmp$ : 'could not parse input');
      }
       else
        throw e;
    }
  }
  function reportError(message) {
    var errorDialog_0 = errorDialog();
    errorDialog_0.innerText = message;
    addClass(errorDialog_0, ['active']);
  }
  function clearError() {
    var errorDialog_0 = errorDialog();
    removeClass(errorDialog_0, ['active']);
  }
  function errorDialog() {
    var tmp$;
    var errorDialog = Kotlin.isType(tmp$ = document.getElementById('error'), HTMLElement) ? tmp$ : Kotlin.throwCCE();
    return errorDialog;
  }
  var package$com = _.com || (_.com = {});
  var package$natpryce = package$com.natpryce || (package$com.natpryce = {});
  var package$snodge = package$natpryce.snodge || (package$natpryce.snodge = {});
  var package$demo = package$snodge.demo || (package$snodge.demo = {});
  Object.defineProperty(package$demo, 'random', {
    get: function () {
      return random;
    }
  });
  Object.defineProperty(package$demo, 'originalTextArea', {
    get: function () {
      return originalTextArea;
    }
  });
  Object.defineProperty(package$demo, 'mutantTextArea', {
    get: function () {
      return mutantTextArea;
    }
  });
  Object.defineProperty(package$demo, 'inputTypeSelector', {
    get: function () {
      return inputTypeSelector;
    }
  });
  package$demo.Format = Format;
  Object.defineProperty(package$demo, 'jsonExample', {
    get: function () {
      return jsonExample;
    }
  });
  Object.defineProperty(package$demo, 'xmlExample', {
    get: function () {
      return xmlExample;
    }
  });
  Object.defineProperty(package$demo, 'textExample', {
    get: function () {
      return textExample;
    }
  });
  Object.defineProperty(package$demo, 'dataTypes', {
    get: function () {
      return dataTypes;
    }
  });
  package$demo.main_kand9s$ = main;
  package$demo.showExampleOfSelectedType = showExampleOfSelectedType;
  package$demo.selectedMutagen_61zpoe$ = selectedMutagen;
  package$demo.reportError_61zpoe$ = reportError;
  package$demo.clearError = clearError;
  random = MersenneTwisterFast_init();
  var tmp$, tmp$_0, tmp$_1;
  originalTextArea = Kotlin.isType(tmp$ = document.getElementById('original'), HTMLTextAreaElement) ? tmp$ : Kotlin.throwCCE();
  mutantTextArea = Kotlin.isType(tmp$_0 = document.getElementById('mutant'), HTMLTextAreaElement) ? tmp$_0 : Kotlin.throwCCE();
  inputTypeSelector = Kotlin.isType(tmp$_1 = document.getElementById('type'), HTMLSelectElement) ? tmp$_1 : Kotlin.throwCCE();
  jsonExample = '{\n    "demo": {\n        "instructions": {\n            "1": "press the button in the middle to mutate this JSON",\n            "2": "a random mutation will appear in the right pane",\n            "3": "press the button again to get another mutant",\n            "4": "hold the button to repeatedly mutate this JSON",\n            "5": "you can replace this with your own JSON",\n            "6": "or mutate a different data format"\n        },\n        "uses": [\n            "Robustness testing",\n            "Security testing",\n            "Negative testing",\n            "Property-based testing"\n        ]\n    }\n}';
  xmlExample = '<demo>\n  <instructions>\n    <step n="1">press the button in the middle to mutate this XML<\/step>\n    <step n="2">a random mutation will appear in the right pane<\/step>\n    <step n="3">press the button again to get another mutant<\/step>\n    <step n="4">hold the button to repeatedly mutate this XML<\/step>\n    <step n="5">you can replace this with your own XML<\/step>\n    <step n="6">or mutate a different data format<\/step>\n  <\/instructions>\n  <uses>\n    <use>Robustness testing<\/use>\n    <use>Security testing<\/use>\n    <use>Negative testing<\/use>\n    <use>Property-based testing<\/use>\n  <\/uses>\n<\/demo>';
  textExample = '\nDemo\n====\n\nInstructions:\n\n1. press the button in the middle to mutate this text.\n2. a random mutation will appear in the right pane.\n3. press the button again to get another mutant.\n4. hold the button to repeatedly mutate this text.\n5. you can replace this with your own text.\n6. or mutate a different data format.\n\nUses:\n\n* Robustness testing\n* Security testing\n* Negative testing\n* Property-based testing\n';
  dataTypes = mapOf([to('text', new Format(textExample, dataTypes$lambda)), to('xml', new Format(xmlExample, dataTypes$lambda_0)), to('json', new Format(jsonExample, dataTypes$lambda_1))]);
  main([]);
  Kotlin.defineModule('snodge-demo', _);
  return _;
}));

//# sourceMappingURL=snodge-demo.js.map
