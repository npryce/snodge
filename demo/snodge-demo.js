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
  var defaultJsonMutagens = $module$snodge.com.natpryce.snodge.json.defaultJsonMutagens;
  var forStrings = $module$snodge.com.natpryce.snodge.json.forStrings_403vme$;
  var defaultXmlMutagens = $module$snodge.com.natpryce.snodge.xml.defaultXmlMutagens;
  var forStrings_0 = $module$snodge.com.natpryce.snodge.xml.forStrings_1pa5su$;
  var IllegalStateException = Kotlin.kotlin.IllegalStateException;
  var mutant = $module$snodge.com.natpryce.snodge.mutant_vq1p9g$;
  var addClass = Kotlin.kotlin.dom.addClass_hhb33f$;
  var removeClass = Kotlin.kotlin.dom.removeClass_hhb33f$;
  var random;
  var originalTextArea;
  var mutantTextArea;
  var inputTypeSelector;
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
  function main(args) {
    var tmp$;
    var mutateButton = Kotlin.isType(tmp$ = document.getElementById('mutate'), HTMLButtonElement) ? tmp$ : Kotlin.throwCCE();
    var timer = {v: null};
    var timerTick = main$timerTick(timer);
    mutateButton.onmousedown = main$lambda(timerTick, timer);
    mutateButton.onmouseup = main$lambda_0(timer);
  }
  function selectedMutagen() {
    var it = inputTypeSelector.value;
    var block$result;
    if (Kotlin.equals(it, 'text')) {
      block$result = splice(replaceWithPossiblyMeaningfulText());
    }
     else if (Kotlin.equals(it, 'json')) {
      block$result = forStrings(defaultJsonMutagens());
    }
     else if (Kotlin.equals(it, 'xml')) {
      block$result = forStrings_0(defaultXmlMutagens());
    }
     else
      throw new IllegalStateException('unrecognised input type: ' + it);
    return block$result;
  }
  function mutate() {
    var tmp$;
    var originalText = originalTextArea.value;
    try {
      var mutantText = mutant(random, selectedMutagen(), originalText);
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
  package$demo.main_kand9s$ = main;
  package$demo.selectedMutagen = selectedMutagen;
  package$demo.reportError_61zpoe$ = reportError;
  package$demo.clearError = clearError;
  random = MersenneTwisterFast_init();
  var tmp$, tmp$_0, tmp$_1;
  originalTextArea = Kotlin.isType(tmp$ = document.getElementById('original'), HTMLTextAreaElement) ? tmp$ : Kotlin.throwCCE();
  mutantTextArea = Kotlin.isType(tmp$_0 = document.getElementById('mutant'), HTMLTextAreaElement) ? tmp$_0 : Kotlin.throwCCE();
  inputTypeSelector = Kotlin.isType(tmp$_1 = document.getElementById('type'), HTMLSelectElement) ? tmp$_1 : Kotlin.throwCCE();
  main([]);
  Kotlin.defineModule('snodge-demo', _);
  return _;
}));

//# sourceMappingURL=snodge-demo.js.map
