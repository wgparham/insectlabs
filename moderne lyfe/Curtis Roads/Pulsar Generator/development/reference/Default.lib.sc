
Object {
 classvar <dependantsDictionary, currentEnvironment, <uniqueMethods; 
 classvar <nl="\n";
 
 *new { arg maxSize = 0; 
 _BasicNew 
 ^this.primitiveFailed
     }
 *newClear { arg indexedSize = 0; 
 _BasicNewClear 
 ^this.primitiveFailed
     }
 
 dump { _ObjectDump }
 post { this.asString.post }
 postln { this.asString.postln; }
 postc { this.asString.postc }
 postcln { this.asString.postcln; }
 flushPostBuf { _FlushPostBuf }
 flushErrors { _FlushErrors }
 gcInfo { _GCInfo }
 gcSanity { _GCSanity }
 
  size { ^this.basicSize }
 maxSize { ^this.basicMaxSize }
 instVarSize { _InstVarSize; ^this.primitiveFailed }
 instRawSize { _InstRawSize; ^this.primitiveFailed }
 basicSize { _BasicSize; ^0 }
 basicMaxSize { _BasicMaxSize; ^0 }
 basicAt { arg index; 
 _BasicAt;
 index.dump;
 ^this.primitiveFailed;
 }
 basicClipAt { arg index; 
 _BasicClipAt;
 ^this.primitiveFailed;
 }
 basicWrapAt { arg index; 
 _BasicWrapAt;
 ^this.primitiveFailed;
 }
 basicFoldAt { arg index; 
 _BasicFoldAt;
 ^this.primitiveFailed;
 }
 basicPut { arg index, item; 
 _BasicPut;
 ^this.primitiveFailed;
 }
 basicClipPut { arg index, item; 
 _BasicClipPut;
 ^this.primitiveFailed;
 }
 basicWrapPut { arg index, item; 
 _BasicWrapPut;
 ^this.primitiveFailed;
 }
 basicFoldPut { arg index, item; 
 _BasicFoldPut;
 ^this.primitiveFailed;
 }
 instVarAt { arg index; 
 _InstVarAt;
 ^this.primitiveFailed;
 }
 instVarPut { arg index, item; 
 _InstVarPut;
 ^this.primitiveFailed;
 }
 basicRemoveAt { arg index; 
 _BasicRemoveAt;
 ^this.primitiveFailed;
 }
 basicTakeAt { arg index; 
 _BasicTakeAt;
 ^this.primitiveFailed;
 }
 
 at { arg index; ^this.basicAt(index) }
 clipAt { arg index; ^this.basicClipAt(index) }
 wrapAt { arg index; ^this.basicWrapAt(index) }
 foldAt { arg index; ^this.basicFoldAt(index) }
 
 removeAt { arg index; ^this.basicRemoveAt(index) }
 takeAt { arg index; ^this.basicTakeAt(index) }
 
 @ { arg index; ^this.at(index) }
 |@| { arg index; ^this.clipAt(index) }
 @@ { arg index; ^this.wrapAt(index) }
 @|@ { arg index; ^this.foldAt(index) }
 
 put { arg index, item; ^this.basicPut(index, item) }
 clipPut { arg index, item; ^this.basicClipPut(index, item) }
 wrapPut { arg index, item; ^this.basicWrapPut(index, item) }
 foldPut { arg index, item; ^this.basicFoldPut(index, item) }
 
 basicSwap { arg i, j; var temp;
 _BasicSwap;
 temp = this.basicAt(i);
 this.basicPut(i, this.basicAt(j));
 this.basicPut(j, temp);
 }
 swap { arg i, j; ^this.basicSwap(i,j) }
 
 atModify { arg index, function; function.value(this.at(index)) }
 atInc { arg index, inc=1; this.put(index, this.at(index)+inc); }
 atDec { arg index, dec=1; this.put(index, this.at(index)-dec); }
 do { arg function; function.value(this, 0) }
 
  class { _ObjectClass; ^this.primitiveFailed }
 isKindOf { arg aClass; _ObjectIsKindOf; ^this.primitiveFailed }
 isMemberOf { arg aClass; _ObjectIsMemberOf; ^this.primitiveFailed }
 respondsTo { arg aSymbol; _ObjectRespondsTo; ^this.primitiveFailed }
 perform { arg selector ... args;
 _ObjectPerform; 
 ^this.primitiveFailed 
 }
 performList { arg selector, arglist;
 _ObjectPerformList; 
 ^this.primitiveFailed 
 }
 performMsg { arg msg;
 _ObjectPerformMsg; 
 ^this.primitiveFailed 
 }
 multiChannelPerform { arg selector ... args;
 _MultiChannelPerform 
 ^this.primitiveFailed 
 }
 multiChannelPerformList { arg selector ... args;
 _MultiChannelPerformList
 ^this.primitiveFailed 
 }
  copy { ^this.shallowCopy }
 contentsCopy { ^this.shallowCopy }
 shallowCopy { _ObjectShallowCopy; ^this.primitiveFailed }
 copyImmutable { 
  _ObjectCopyImmutable; 
 ^this.primitiveFailed 
 }
 copyRange { arg start, end; 
   _ObjectCopyRange;
 ^this.primitiveFailed 
 }
 copyToEnd { arg start;
 ^this.copyRange(start, this.size - 1)
 }
 copyFromStart { arg end;
 ^this.copyRange(0, end)
 }
 
 deepCopy {
 ^this.deepCopyWithDict(IdentityDictionary.new(128));
 }
 deepCopyWithDict { arg dict;
 var i, imax, newobj, item, foundobj;
 
 foundobj = dict.at(this);
 if (foundobj.notNil, { ^foundobj });  
 newobj = this.shallowCopy;
 dict.put(this, newobj);
 
 imax = this.instVarSize;
 i = 0;
 while ({ i < imax }, {
 item = this.instVarAt(i);
 newobj.instVarPut(i, item.deepCopyWithDict(dict));
 i = i + 1;
 });
 
 imax = this.basicSize;
 i = 0;
 while ({ i < imax }, {
 item = this.basicAt(i);
 newobj.basicPut(i, item.deepCopyWithDict(dict));
 i = i + 1;
 });
 ^newobj
 } 
 
 
  value { ^this }
 valueArray { ^this }
 poll { ^this.value }
 
  == { arg obj; ^this === obj }
 != { arg obj; ^this !== obj }
 === { arg obj; _Identical; ^this.primitiveFailed }
 !== { arg obj;_NotIdentical; ^this.primitiveFailed }
 
 basicHash { _ObjectHash; ^this.primitiveFailed }
 hash { _ObjectHash; ^this.primitiveFailed }
 identityHash { _ObjectHash; ^this.primitiveFailed }
 
  -> { arg obj; ^Association.new(this, obj) }
 
  next { ^this }
 reset { ^this }
 embedInStream { this.yield; }
 asStream { ^this }
 eventAt { ^nil }
 finishEvent {
 this.newErrorWindow;
 error("finishEvent : EventStreams are not appropriate for this pattern.\n");
 postln("Returned the following non-Event :");
 this.dump;
 this.dumpBackTrace;
 this.flushErrors;
 this.halt;
 }
 
  ? { arg obj; ^this }
 isNil { ^false }
 notNil { ^true }
 isNumber { ^false }
 isInteger { ^false }
 isFloat { ^false } 
 isValidUGenInput { ^false }
 pointsTo { arg obj; _ObjectPointsTo; ^this.primitiveFailed }
 mutable { _ObjectIsMutable; ^this.primitiveFailed }
 
  halt { 
 MixerPlayer.reset;
 currentEnvironment = nil;  this.prHalt 
 }
 prHalt { _Halt }
 primitiveFailed { 
 this.newErrorWindow;
 error("Primitive '" 
 ++ thisThread.failedPrimitiveName.asString 
 ++ "' failed.\n"); 
 this.primitiveErrorString.postln; 
 "RECEIVER:\n".post;
 this.dump;
 this.dumpBackTrace;
 this.flushErrors;
 this.halt 
 }
 primitiveError { _PrimitiveError }
 primitiveErrorString { _PrimitiveErrorString; }
 newErrorWindow { _NewPostWindow }
 
 subclassResponsibility { arg method;
 this.newErrorWindow;
 error( "'" ++ method.name.asString ++ "' should have been implemented by "
 ++ this.class.name.asString ++ ".\n");
 this.dumpBackTrace;
 this.flushErrors;
 this.halt;
 }
 doesNotUnderstand { arg selector ... args;
 
 
 this.newErrorWindow;
 error("Message '" 
 ++ selector.asString
 ++ "' not understood.\n"); 
 "RECEIVER:\n".post;
 this.dump;
 "ARGS:\n".post;
 args.dump;
 this.dumpBackTrace;
 this.flushErrors;
 this.halt 
 }
 shouldNotImplement { arg method;
 this.newErrorWindow;
 error(method.ownerClass.name.asString ++ "::" ++ method.name.asString 
 ++ " : Message not valid for this subclass\n");
 this.dumpBackTrace;
 this.flushErrors;
 this.halt;
 } 
 mustBeBoolean {
 this.newErrorWindow;
 error("Non Boolean in test:\n");
 this.dump;
 this.dumpBackTrace;
 this.flushErrors;
 this.halt;
 }
 nonIntegerIndex { error("non Integer index used.\n") }
 indexOutOfRange { error("Index out of range.\n") }
 notYetImplemented { inform("Not yet implemented.\n") }
 dumpBackTrace { _DumpBackTrace }
 
  species { ^this.class }
 asSymbol { ^this.asString.asSymbol }
 asString {
 var string, limit = 200;
 _ObjectString
 string = String.streamContentsLimit({ arg stream; this.printOn(stream); }, limit);
 if (string.size >= limit, { string = string ++ "...etc..."; });
 ^string
 }
 asCompileString {
 _ObjectCompileString 
 ^String.streamContents({ arg stream; this.storeOn(stream); });
 }
 printOn { arg stream;
 var title;
 title = this.class.name.asString;
 stream << if((title @ 0).isVowel, { "an " }, { "a " }) << title ;
 }
 storeOn { arg stream;
 var title;
 stream << this.class.name << ".new";
 this.storeParamsOn(stream);
 this.storeModifiersOn(stream);
 }
 storeParamsOn { arg stream;
 }
 storeModifiersOn { arg stream;
 }
 
 as { arg aSimilarClass; ^aSimilarClass.newFrom(this) }
 dereference { ^this }  asRef { ^Ref.new(this) }
 asArray { ^Array.with(this) }
 
  while { arg body;
   while({ this.value }, {
 body.value
 });
 }
 
  yield { arg val;
 _RoutineYield
 ^this.primitiveFailed
 }
 alwaysYield { arg val;
 _RoutineAlwaysYield
 ^this.primitiveFailed
 }
 yieldAndReset { arg val, reset = true;
 _RoutineYieldAndReset
 ^this.primitiveFailed
 }
 
  dependants {
 if (dependantsDictionary.isNil, { 
 ^IdentitySet.new 
 },{
 ^dependantsDictionary.atFail(this, { ^IdentitySet.new });
 })
 }
 changed { arg theChanger;
 var theDependants;
 theDependants = this.dependants;
 theDependants.do({ arg item;
 item.update(this, theChanger);
 });
 }
 addDependant { arg dependant;
 var theDependants;
 if (dependantsDictionary.isNil, {
 dependantsDictionary = IdentityDictionary.new(4);
 });
 theDependants = this.dependants;
 dependantsDictionary.put(this, theDependants.add(dependant));
 }
 removeDependant { arg dependant;
 var theDependants;
 if (dependantsDictionary.notNil, { 
 theDependants = dependantsDictionary.atFail(this);
 if (theDependants.notNil, {
 theDependants.remove(dependant);
 if (theDependants.size == 0, {
 dependantsDictionary.removeAt(this);
 });
 });
 })
 }
 release {
 if (dependantsDictionary.notNil, {
 dependantsDictionary.removeAt(this);
 })
 }
 update { arg theChanged, theChanger;  }
  addUniqueMethod { arg selector, function;
 var methodDict;
 if (uniqueMethods.isNil, { uniqueMethods = IdentityDictionary.new });
 methodDict = uniqueMethods.at(this);
 if (methodDict.isNil, {
 methodDict = IdentityDictionary.new;
 uniqueMethods.put(this, methodDict);
 });
 methodDict.put(selector, function);
 }
 removeUniqueMethods {
 if (uniqueMethods.notNil, { 
 uniqueMethods.removeAt(this);
 });
 }
 removeUniqueMethod { arg selector;
 var methodDict;
 if (uniqueMethods.notNil, {
 methodDict = uniqueMethods.at(this);
 if (methodDict.notNil, {
 methodDict.removeAt(selector);
 if (methodDict.size < 1, {
 uniqueMethods.removeAt(this);
 });
 });
 }); 
 }
  
 
  libMenuAction {
 this.asString.postln;
 }
  crash { _HostDebugger }  stackDepth { _StackDepth }
 dumpStack { _DumpStack }
 dumpDetailedBackTrace { _DumpDetailedBackTrace }
}

TableViews { }
ControlPanel { }
File_Menu { }
Other_Menus { }
Synth_Menu { }
Fill_Menu { }
Alter_Menu { }
Tables_Bank { }
FX_RingMod { }
What_is_Pulsar_Synthesis { }
Copyright { }

BurstLoop {   
 var <pos = -1, numOns = 2, numOffs = 1, length = 3;
 
 *ratio { arg on = 2, off = 1;  ^super.new.ratio(on, off); 
 }
 ratio { arg on = 2, off = 2; 
 numOns = on.asInteger; numOffs = off.asInteger;
 length = numOns + numOffs;
 }
 next { var newPos; 
 newPos = pos = (pos + 1) % (length); 
 ^(newPos < numOns);
 }
 
 update { arg newOn = 2, newOff = 1; 
 this.ratio(newOn, newOff);
 }
 setPos { arg inPos; pos = inPos;
 }
}
BurstLoop2 : List {     var <pos = -1;
 *ratio { arg on = 2, off = 1;  ^this.newFrom(Array.fill(on.asInteger, true) ++ Array.fill(off.asInteger, false));
 }
 
 ratio { arg on, off; this.update(on, off); }
 next { ^array.wrapAt(pos = pos + 1); }
 
 update { arg newOn = 2, newOff = 1; 
 this.array = Array.fill(newOn.asInteger, true) ++ Array.fill(newOff.asInteger, false);
 ^this
 }
 setPos { arg inPos; pos = inPos;
 }
}

Cycle {
  *ar { arg list, numChannels = 1, nextTime = 1.0, maxRepeats = 1,
 mul = 1.0, add = 0.0;
 maxRepeats = if (maxRepeats.notNil, { maxRepeats * list.size },{ nil });
 
 ^Spawn.ar(
 { arg spawn, eventCount, synth;
 list.wrapAt(eventCount).spawn(spawn, eventCount, synth);
 }, 
 numChannels, nextTime, maxRepeats, mul, add
 )
 }
}
RandomEvent {
 *ar { arg list, numChannels = 1, nextTime = 1.0, maxRepeats,
 mul = 1.0, add = 0.0; 
 ^Spawn.ar(
 { arg spawn, eventCount, synth;
 list.choose.spawn(spawn, eventCount, synth);
 }, 
 numChannels, nextTime, maxRepeats, mul, add
 )
 }
}
SelectEvent {
 *ar { arg list, selectFunc, numChannels = 1, nextTime = 1.0, maxRepeats,
 mul = 1.0, add = 0.0; 
 ^Spawn.ar(
 { arg spawn, eventCount, synth;
 list.wrapAt(
 selectFunc.value(spawn, eventCount, synth).asInteger
 ).spawn(spawn, eventCount, synth);
 }, 
 
 numChannels, nextTime, maxRepeats, mul, add
 )
 }
}

OrcScore {
   *ar { arg orchestra, score, numChannels = 1, nextTime = 1.0, maxRepeats = 1,
 mul = 1.0, add = 0.0; 
 maxRepeats = if (maxRepeats.notNil, { maxRepeats * score.size },{ nil });
 
 ^Spawn.ar(
 { arg spawn, eventCount, synth;
 var pfields, instrument;
 pfields = score.wrapAt(eventCount);
 spawn.nextTime = pfields.at(0);  instrument = orchestra.at(pfields.at(1));  instrument.spawn(spawn, eventCount, synth, pfields);  }, 
 numChannels, nextTime, maxRepeats, mul, add
 )
 }
}
OrcStream {
    *ar { arg orchestra, score, numChannels = 1, nextTime = 1.0,
 mul = 1.0, add = 0.0; 
 
 ^Spawn.ar(
 { arg spawn, eventCount, synth;
 var pfields, instrument;
 pfields = score.next;
 if (pfields.notNil, {
 spawn.nextTime = pfields.at(0);  instrument = orchestra.at(pfields.at(1));  instrument.spawn(spawn, eventCount, synth, pfields);  });
 }, 
 numChannels, nextTime, nil, mul, add
 )
 }
}





PulsarEngine4 { 
 
 var <pulsaret, <>pulsaretSignal, <>xFadeSignals; 
 
 var <numPulsars = 0, releaseEnv, predictEnv, <>xFadePlugs,  maxCutoffTime = 0.02, cutoffTime = 0.000316221;   var fundFreq, <>pulseMask, <trainControls, trainLevels, fadeInPlug;
 
 var fundFreqPlug, activeTrains, spawnFuncLib, currentSpawnFunc; 
 var playFlagFunc = true,  <trainFlags;
 
 var <maxFundFreq = 2000, <maxFundFreqs, <maxFundFreqPlug;
 *new { 
 arg pulsaret, pulseEnv,
 fundFreq = 18, pulseMask = 1,
 trainControls = #[ 
 [100, -1.0, 0.5], 
 [200, 0.0, 0.5], 
 [400, 1.0, 0.5]
 ], 
 mul = 1.0; 
 ^super.new.init(pulsaret, pulseEnv, fundFreq, pulseMask, trainControls, mul); 
 }
 
 init {
 arg inPulsaret, inPulseEnv, inFundFreq, inPulseMask, inTrainControls, mul; 
 
 fundFreq = inFundFreq; 
 pulseMask = inPulseMask;
 trainControls = inTrainControls.wrapExtend(3); 
 trainFlags = [ true, true, true ];
 trainLevels = [ 0.3, 0.3, 0.3 ];
   
 maxFundFreqs = PG.maxFundFreqs;
 
 pulsaret = Pulsaret.new(inPulsaret, inPulseEnv);
 
 releaseEnv = Env.new([1, 1, 0], [0, cutoffTime ], releaseNode: 1);  predictEnv = Env.new([1, 1, 0], [1, cutoffTime ]);  
 this.updateActiveTrains;
 
 ^this;
 }
 
 setMaxFundFreqs { arg newMaxFreqs; 
 newMaxFreqs.do({ arg eachNew, i; this.setMaxFundFreq(i, eachNew); });
 this.updateMaxFundFreq;
 }
 
 setMaxFundFreq { arg which, val; 
  
 if (val.isNumber, { maxFundFreqs.put(which, val); }); 
 if (activeTrains.size == which, { this.updateMaxFundFreq });
 }
 
 setAllControls { arg newControls; 
 
 fundFreq = newControls.at(0); 
 
 pulseMask = newControls.at(1); 
 this.setTrainControls(
 newControls.copyRange(2, newControls.size - 1).clump(3)
 );
 }
 setTrainControls { arg newControls; 
 trainControls = newControls; 
 this.updateActiveTrains;
 }
 muteTrain { arg which; this.setTrainFlag(which, false); }
 
 unmuteTrain { arg which; this.setTrainFlag(which, true); }
 
 setTrainFlag { arg which, onOrOff; 
 if (onOrOff.isNumber, { onOrOff = onOrOff.isStrictlyPositive });
 trainFlags.put(which, onOrOff); 
 this.updateActiveTrains;
 }
 
 setTrainLevel { arg index, level;
 trainLevels.put(index, level);
 }
 
 setFormFreq { arg index, value; 
 trainControls.at(index).put(0, value);
 } 
 setAmp { arg index, value; 
 trainControls.at(index).put(1, value);
 } 
 setPan { arg index, value; 
 trainControls.at(index).put(2, value);
 } 
 
 updateActiveTrains {  activeTrains = trainControls.flop.add(trainLevels).flop  .select({ arg each, index; trainFlags.at(index) == true });  this.updateMaxFundFreq;  }
 
 updateMaxFundFreq { 
 maxFundFreq = maxFundFreqs.at(activeTrains.size); 
 if (Synth.isOn, { maxFundFreqPlug.source = maxFundFreq }); 
  }
 
 switchSpawnFunc { arg key; 
 if (spawnFuncLib.keys.includes(key), 
 { currentSpawnFunc = spawnFuncLib.at(key) }
 );
 }
 
 prepareXFadeSignals { arg newPulsaret, newPulseEnv; 
 var newComboSig; 
 newComboSig = pulsaret.makeNewComboSig(newPulsaret, newPulseEnv); 
 xFadeSignals = [ pulsaretSignal, newComboSig ]; 
 
 }
 
 startFade { arg fadeTime; 
 fadeInPlug.line(1, fadeTime);
 this.switchSpawnFunc(\xFade);
 thisSynth.sched(fadeTime, { this.endFade });
 }
 
 endFade { 
 pulsaretSignal = xFadeSignals.at(1);   pulsaret.setPulsaretAndEnv(  PG.currentBank.at(\currentTables).at(\pulsaret),
 PG.currentBank.at(\currentTables).at(\pulseEnv)
 );
 this.switchSpawnFunc(\turbo); 
 fadeInPlug.source_(0); 
 }
 
 asUgen { arg numChannels = 2;
 var lowestForm = 0; 
 var nextPulseWillPlay;   
 
 fundFreqPlug = Plug.ar(fundFreq); 
 
  
 maxFundFreqPlug = Plug.kr(maxFundFreq);
 fadeInPlug = Plug.kr(0);
 
 if (numChannels < 3, { 
  spawnFuncLib = IdentityDictionary[ 
 'turbo' -> 
 { arg trains; 
 Mix.ar(
 trains.collect({ arg eachControlArray; 
 var formFreq, pan, amp, level, pulseDur; 
 #formFreq, pan, amp, level = eachControlArray;
 formFreq = formFreq.poll;
 pulseDur = formFreq.abs.max(1).reciprocal;  lowestForm = min(formFreq, lowestForm);
 
 Pan2.ar(
 in: Osc1.ar(
 table: pulsaretSignal,  dur: pulseDur, 
 mul: amp.poll * level
 ), 
 pos: pan.poll
 )
 })
 );
 }, 
 'xFade' -> 
 { arg trains; 
 
 Mix.ar(
 trains.collect({ arg eachControlArray; 
 var formFreq, pan, amp, level, oldSig, newSig, pulseDur, fadeInAmp; 
 
 #formFreq, pan, amp, level = eachControlArray; 
 formFreq = formFreq.poll;
 pulseDur = formFreq.abs.max(1).reciprocal;  lowestForm = min(formFreq, lowestForm); 
 
 #oldSig, newSig = xFadeSignals;  
   
 fadeInAmp = fadeInPlug.poll; 
 
 Pan2.ar(  in: Osc1.ar(
 newSig,
 pulseDur, 
 fadeInAmp, 
 Osc1.ar(   oldSig,
 pulseDur, 
 1 - fadeInAmp 
 )
 ), 
 pos: pan.poll, 
 level: amp.poll * level
 )
 })
 );
 }
 ];
 }, 
 {   
 spawnFuncLib = IdentityDictionary[ 
 'turbo' -> 
 { arg trains; 
 Mix.ar(
 trains.collect({ arg eachControlArray; 
 var formFreq, pan, amp, level, pulseDur; 
 #formFreq, pan, amp, level = eachControlArray;
 formFreq = formFreq.poll;
 pulseDur = formFreq.abs.max(1).reciprocal;  lowestForm = min(formFreq, lowestForm);
 
 PanAz.ar(
 numChannels,
 in: Osc1.ar(
 table: pulsaretSignal,  dur: pulseDur, 
 mul: amp.poll * level
 ), 
 pos: pan.poll + 1  )
 })
 );
 }, 
 'xFade' -> 
 { arg trains; 
 
 Mix.ar(
 trains.collect({ arg eachControlArray; 
 var formFreq, pan, amp, level, oldSig, newSig, pulseDur, fadeInAmp; 
 
 #formFreq, pan, amp, level = eachControlArray; 
 formFreq = formFreq.poll;
 pulseDur = formFreq.abs.max(1).reciprocal;  lowestForm = min(formFreq, lowestForm); 
 
 #oldSig, newSig = xFadeSignals;  
   
 fadeInAmp = fadeInPlug.poll; 
 
 PanAz.ar(
 numChans: numChannels,
 in: Osc1.ar(
 newSig,
 pulseDur, 
 fadeInAmp, 
 Osc1.ar(   oldSig,
 pulseDur, 
 1 - fadeInAmp 
 )
 ), 
 pos: pan.poll + 1,  level: amp.poll * level
 )
 })
 );
 }
 ];
 }); 
 
 
 this.switchSpawnFunc('turbo');  PG.burstLoop.setPos(-2);   
 ^TSpawn.ar( 
 { arg thisSpawn, count, synth;
 var out, fund, playThisPulse;
 
 playThisPulse = nextPulseWillPlay;  nextPulseWillPlay = playFlagFunc.value(count);
 
 playThisPulse = playThisPulse ? nextPulseWillPlay;    
 if ( playThisPulse and: PG.isMuted.not, 
 { thisSpawn.releaseAll;   numPulsars = numPulsars + 1; 
 out = currentSpawnFunc.value(activeTrains);  
   fund = fundFreq.poll.abs; 
 
 
 if (lowestForm < fund,    {
 if (fund < 20 or: nextPulseWillPlay.not,  { EnvGen.ar(releaseEnv, out) },    
 { 
 
 predictEnv.times.put(0,   min(fund.reciprocal, 1)  ); 
 
  EnvGen.ar(predictEnv, out)
 }
 );
 }, 
 { out }  );
 }, 
 { nil }   )
 },
 numChannels: numChannels, 
 trig: Impulse.ar( fundFreqPlug.abs.min(maxFundFreqPlug) )    ); 
 }
  setPulsaretAndEnv { arg newPulsaret, newPulseEnv; 
 pulsaret.setPulsaretAndEnv(newPulsaret, newPulseEnv); 
 pulsaretSignal = pulsaret.comboSig;
 
 } 
 setPulsaret { arg newPulsaret; 
 pulsaret.setPulsaret(newPulsaret);
 pulsaretSignal = pulsaret.comboSig;
 } 
 setPulseEnv { arg newpulseEnv; 
 pulsaret.setPulseEnv(newpulseEnv);
 pulsaretSignal = pulsaret.comboSig;
 } 
 
 setEdgeFactor { arg edgeFactor;     
 this.setCutoffTime(0.002 * ( 0.6915 ** edgeFactor.clip(0.0, 10.0) ) ); 
 }
 
 setCutoffTime { arg newTime; 
  if (newTime.poll.isNumber, 
 { cutoffTime = newTime.clip(0, maxCutoffTime); }
 );
 this.updateEnvs;
 }
 setPlayFlagFunc { arg booleanOrFunc; 
 playFlagFunc = booleanOrFunc;
 }
 setFundFreq { arg newFreq; 
 if (newFreq.isNumber, { fundFreq = newFreq }); 
 ^this;
 }
 setFundFreqSource { arg newSource; 
 if (fundFreqPlug.notNil, 
 { fundFreqPlug.source = newSource; }
 );
 fundFreq = newSource; 
 ^this;
 }
  
 updateComboPulsaret { 
 pulsaret.updateComboSig;
 }
 updateEnvs { 
 releaseEnv.times.put(1, cutoffTime); 
 predictEnv.times.put(1, cutoffTime); 
 }
 
 defaultPulsaret {     ^Signal.sineFill(2048, 
 Array.fill(4, 1), 
 Array.fill(4, { arg i; [ -0.5pi, 0.5pi ].wrapAt(i) }) 
 ).asWavetable; 
 }
 defaultPulseEnvSignal { ^Signal.fill(2048, 1).asWavetable; }
}

ChoiceWindow { 
 *new { arg string, buttonsArray = #[ ], buttonColor;
 var w, width, height, stringWidth, numColumns, maxItemsPerColumn = 20; 
 
 buttonColor = buttonColor ? Color.white;
 
 numColumns = (buttonsArray.size div: maxItemsPerColumn + 1);
 stringWidth = string.size + 2 * 6;
 width = (stringWidth + 10 * numColumns + 28).max(192);
 height = buttonsArray.size.min(20) + 2 * 22; 
 
 
 w = GUIWindow.new("Please Choose:", Rect.newBy(300, 150, width, height)
 ).backColor_(Color.guiGrey); 
 StringView.new(w, Rect.newBy(10, 10, stringWidth, 20), string)
 .prSetBorderStyle(4)
 .backColor_(Color.white); 
 buttonsArray.do({ arg eachSpec, i; var label, action; 
 #label, action = eachSpec; 
 ButtonView.new(w, 
 Rect.newBy( i div: maxItemsPerColumn * (stringWidth + 10) + 10, 
 i % maxItemsPerColumn * 22 + 35, 
 string.size * 6 + 12, 18), label)
 .action_(action)
 .backColor_(buttonColor);
 });
 ^w;
 }
}

XFadeTexture {
 *ar { arg inputPatchFunc, sustainTime = 4.0, transitionTime = 4.0, numChannels = 1,
 maxRepeats, mul = 1.0, add = 0.0;
 var env, period;
 
 period = transitionTime + sustainTime;
 
 env = Env.new(
 #[ 0.0, 1.0, 1.0, 0.0],
 [ transitionTime, sustainTime, transitionTime ],
 'welch'
 );
 
 ^Spawn.ar({ arg spawn, i, synth;
 EnvGen.kr(env, timeScale: synth.tempo.reciprocal) * inputPatchFunc.value(spawn, i, synth); 
 }, 
 numChannels, period, maxRepeats, mul, add
 )
 }
}
OverlapTexture {
 *ar { arg inputPatchFunc, sustainTime = 4.0, transitionTime = 4.0, density = 2, 
 numChannels = 1, maxRepeats, mul = 1.0, add = 0.0;
 var env, period;
 
 period = (2 * transitionTime + sustainTime) / density;
 
 env = Env.new(
 #[ 0.0, 1.0, 1.0, 0.0],
 [ transitionTime, sustainTime, transitionTime ],
 'welch'
 );
 
 ^Spawn.ar({ arg spawn, i, synth;
 EnvGen.kr(env, timeScale: synth.tempo.reciprocal) * inputPatchFunc.value(spawn, i, synth); 
 }, 
 numChannels, period, maxRepeats, mul, add
 )
 }
}
TrigXFade {
 *ar { arg inputPatchFunc, trig, transitionTime = 0.01, numChannels = 1,
 maxRepeats, mul = 1.0, add = 0.0;
 var envgen, env, tspawn, prevSynth;
 
 env = Env.new(
 [ 1.0, 1.0, 0.0],
 [ transitionTime, transitionTime ],
 'linear', 1
 );
 
 thisSynth.sched(0, { tspawn.source.trigger });  
 ^tspawn = TSpawn.ar({ arg spawn, i, synth;
 if (prevSynth.notNil, {  env.levels.put(0, 0.0);  prevSynth.release;
 });
 prevSynth = synth;
 envgen = EnvGen.kr(env);
 envgen * inputPatchFunc.value(spawn, i, synth); 
 }, 
 numChannels, maxRepeats, trig, mul, add
 )
 }
}
FilePlayer {  
 var <soundFile, <>loopFlag = true;
 *new { arg fileName; 
 if (fileName.isNil, 
 { ^super.new }, 
 { ^super.new.init(fileName); }
 );
 }
 init { arg fileName; 
 var file;
 file = SoundFile.new;
 if (file.readHeader(fileName), 
 { soundFile = file; 
 if (soundFile.numFrames * soundFile.numChannels <= 65536, 
 { soundFile.readData;  },
 { soundFile.preloadData(0, 65536);    }
 ); 
 }, 
 { (fileName ++ "not found.").postcln; }
 );
 }
 asUgen { arg loop, startFrame=0, numFrames = 32768;
 var data; 
 loop = loop ? loopFlag;
 if (soundFile.isNil, { "no soundFile.".postcln; ^nil });
 
 if (soundFile.data.isNil, 
 {  ^DiskIn.ar(soundFile, loop, startFrame); 
 }, 
 {  data = if (soundFile.numChannels == 1, 
 { soundFile.data.at(0) }, 
 { soundFile.data.at(0) }
 );
 ^PlayBuf.ar(data, soundFile.sampleRate); 
 }
 );
 }
 play { ^Synth.play({ this.asUgen }); }
 scope { ^Synth.scope({ this.asUgen }); }
 
 getFile { arg action;  if (Synth.isOn, { 
 MessageWindow("Sorry, cannot pick soundfile while Synth is on."); 
 ^this; 
 });
 
 GetFileDialog.new({ arg ok, path; 
 if (ok, { this.init(path); 
 action.value(ok, path); });
 });
 ^this;
 }
 *getFile { ^this.new.getFile } 
 
 hasFile { ^soundFile.isKindOf(SoundFile) }
 
 fileName { ^soundFile.path }
}
MultiFilePlayer : FilePlayer {  var <soundFileList, playingFiles, envGen, plug; 
 
 
     
    
 init { arg fileName; 
 playingFiles = List.new;
 ^super.init(fileName);
 }
 
 asUgen { arg loop, startFrame=0, numFrames = 32768;
 var player, envGen; 
 
 player = super.asUgen(loop, startFrame, numFrames);
 
 if (player.isNil, { ^player },
 { plug = Plug.kr(1);
 envGen = EnvGen.kr(
 Env.new([1.0, 1.0, 0.0], [0.0, 0.01], releaseNode: 1),
 mul: plug
 ); 
 
  
 ^player * envGen; 
 }
 );
 }
 fadeOut { arg fadeTime = 2; 
 plug.line(0, fadeTime);
 thisSynth.sched(fadeTime, { thisSynth.release }); 
 }
}
Instrument {
 var <>func, <>name;
 
 *new { arg name, func;
 ^super.new.func_(func).name_(name)
 }
 
 *play { arg name, func, volume = 0.5;
 this.new(name, func).play(volume);
 }
 
 play { arg volume = 0.5;
 MixerPlayer.play(MixerVoice.new(this), volume);
 }
 
  ar { arg ... inputs;
 ^func.valueArray(inputs);
 }
 
  *libPut { arg names, func;
 var instr;
 instr = this.new(names.last, func);
 Library.put(names, instr);
 ^instr
 }
 libMenuAction { arg names;
 this.play;
 }
 
  spawn { arg spawn, eventCount, synth, inputs;
 ^{ arg deltaTime, instrumentNum ... inputs;
  func.valueArray(inputs)
 }.valueArray(inputs);
 }
}
MixerPlayer {
 classvar tspawn, <>mixer, <inputs, <>numChannels = 2, <>numInputs = 12, env;
 classvar recBtn, recFile, recID=0, scopeBtn, scopeW;
 *reset {
  tspawn = nil;
 mixer = nil;
 inputs = nil;
 env = nil;
 recBtn = nil;
 recFile = nil;
 }
 *play { arg voice, volume = 0.5;
 var input;
 
 this.makeMixer;
 input = this.findFreeInput;
 if (input.isNil, {^this});
 
 this.initInput(input, voice, voice.instrument.name, volume);
 
 if (tspawn.isNil, {
 this.prepareRecord;
 Synth.play(this.playFunc(input));
 this.synthDone;
 },{
 tspawn.trigger(input);
 });
 }
 
  *initInput { arg input, voice, name, volume;
 input.voice = voice;
 input.name = name.asString;
 input.nameView.label = input.name;
 if (voice.canMakeEditWindow, {
 input.editBtn.label = "Edit";
 });
 input.volumeSlider.value = volume;
 input.killBox.value = 1;
 input.killBox.action = {
 if (input.killBox.value == 0, {
 if ( tspawn.notNil and: { input.synth.notNil }, {
 input.synth.release;
 });
 input.voice.close;
 input.voice = nil;
 input.name = nil;
 input.synth = nil;
 input.volumeSlider.value = 0.0005;
 input.killBox.action = nil;
 input.copyBtn.action = nil;
 input.editBtn.action = nil;
 input.nameView.label = "-";
 input.editBtn.label = "_";
 });
 };
 input.copyBtn.action = {
 this.play(input.voice.copy, input.volumeSlider.value);
 };
 input.editBtn.action = {
 input.voice.makeEditWindow;
 };
 }
 *playFunc { arg input;
 ^{ arg synth;
 var outs;
 
 outs = TSpawn.ar({ arg argTSpawn, i, tsynth, argInput;
 argInput.synth = tsynth;
 Pause.ar({
 argInput.voice.play(synth);
 }, argInput.volumeSlider.kr - 0.001) * EnvGen.kr(env);
 }, numChannels);
 
 tspawn = outs.source;
 synth.sched(0, { 
 tspawn.trigger(input) 
 });
 
 if (recFile.notNil, {
  Pause.ar({ DiskOut.ar(recFile, 32768, outs); }, recBtn.kr);
 });
 
 outs
 }
 }
 *synthDone {
 if (recFile.notNil, { recFile.endRecord; recID = recID + 1; });
 recBtn.value = 0;
 tspawn = nil;
 inputs.do({ arg anInput;
 anInput.killBox.value_(0).doAction;
 }); 
 }
 *makeMixer { 
 if (mixer.notNil, { 
  ^mixer 
 });
 
  env = Env.new(#[1,1,0],[1,0.01],'linear',1);
 
 mixer = GUIWindow.new("Mixer", Rect.newBy( 80, 80, 550, 80 + (24 * numInputs)) );
 recBtn = CheckBoxView.new( mixer, Rect.newBy( 10, 4, 100, 20 ), "Record", 0, 0, 1, 0, 'linear');
 scopeBtn = CheckBoxView.new( mixer, Rect.newBy( 120, 4, 100, 20 ), "Scope", 0, 0, 1, 0, 'linear');
 inputs = Array.new(numInputs);
 numInputs.do({ arg i;
 var vol, box, copybtn, editbtn, str, top;
 top = 24 * i + 30;
 vol = SliderView.new( mixer, Rect.newBy( 10, top, 128, 20 ), "SliderView", 0.0, 0.0005, 1, 0, 'exponential');
 box = CheckBoxView.new( mixer, Rect.newBy( 142, top, 20, 20 ), "", 0, 0, 1, 0, 'linear');
 copybtn = ButtonView.new( mixer, Rect.newBy( 168, top, 40, 20 ), "Copy", 0, 0, 1, 0, 'linear');
 editbtn = ButtonView.new( mixer, Rect.newBy( 212, top, 40, 20 ), "_", 0, 0, 1, 0, 'linear');
 str = StringView.new( mixer, Rect.newBy( 264, top, 128, 20 ), "-");
 inputs.add(
 MixerInputSpec.new(i, nil, nil, nil, vol, box, copybtn, editbtn, str)
 );
 });
 mixer.onClose = { 
 inputs.do({ arg anInput;
 if (anInput.notNil, {
 anInput.killBox.value_(0).doAction;
 });
 }); 
 mixer = nil; 
 };
 ^mixer;
 
 }
 *findFreeInput {
 ^inputs.detect({ arg anInput; anInput.voice.isNil });
 }
 *prepareRecord {
 var filename;
 
 if (thisProcess.isDemo.not, {
  recFile = SoundFile.new;
 recFile.numChannels = numChannels;
 recFile.headerFormat = 'AIFF';
 recFile.sampleFormat = '16 big endian signed';
 filename = ":Sounds:MixerOut_" ++ (recID%10);
 if (recFile.writeHeader(filename), {
 recFile.prepareRecord;
 },{
 ("write record file header failed : " ++ filename).postln;
 recFile = nil;
 });
 });
 }
 *forInput { arg channel, func;
 var inputs, input;
 inputs = MixerPlayer.inputs;
 inputs.notNil.if({
 input = inputs.at(channel - 1);
 input.notNil.if({
 ^func.value(input);
 });
 });
 ^nil
 }
}
MixerVoice {
 var <>instrument, <>editWindow, <>plugs, <>params;
 *new { arg instrument;
 ^super.new.instrument_(instrument);
 }
 
 init {
 }
 
 copy { 
 var copy;
 copy = this.class.new(instrument) ;
 copy.params = Array.fill(plugs.size, { arg i; plugs.at(i).source.value; });
 ^copy
 }
 
 canMakeEditWindow {
 ^instrument.func.def.argNames.size >= 1
 }
 makeEditWindow {
 var def, defargs, defvals;
 if (editWindow.notNil, { ^editWindow });
 if (plugs.isNil, { ^nil });
 
 def = instrument.func.def;
 defargs = def.argNames;
 defvals = def.prototypeFrame;
 
 if (defargs.size < 1, { ^nil });
 
 editWindow = GUIWindow.new(instrument.name.asString, Rect.newBy( 80, 80, 550, 60 + (24 * defargs.size)) );
 editWindow.onClose = { editWindow = nil; };
 
 defargs.do({ arg argName, i;
 var spec, top, val, numer, slider, plug;
 top = 24 * i + 30;
 
 StringView.new( editWindow, Rect.newBy( 10, top, 100, 20 ), argName );
 spec = ParamSpec.specs.at(argName);
 plug = plugs.at(i);
 if (spec.notNil, {
  val = plug.source.value;
 slider = SliderView.new( editWindow, Rect.newBy( 114, top, 128, 20 ), 
 nil, val, spec.minval, spec.maxval, spec.step, spec.warp
 );
 numer = NumericalView.new( editWindow, Rect.newBy( 246, top, 60, 20 ), 
 nil, val, spec.minval, spec.maxval, spec.step, spec.warp
 );
 slider.action = { numer.value = slider.value };
 numer.action = { slider.value = numer.value };
 },{
 numer = NumericalView.new( editWindow, Rect.newBy( 114, top, 60, 20 ), 
 nil, defvals.at(i) ? 0.0, -1e10, 1e10
 );
 });
 plugs.at(i).source = numer;
 });
 }
 close {
 if (editWindow.notNil, { 
 editWindow.close;
 });
 }
 
 play { arg synth;
 var def, size;
 def = instrument.func.def;
 size = def.argNames.size;
 plugs = Array.new(size);
 if (params.notNil, {
 size.do({ arg i;
 plugs.add(Plug.kr(params.at(i)));
 }); 
 },{
 size.do({ arg i;
 plugs.add(Plug.kr(def.prototypeFrame.at(i)));
 }); 
 });
 ^instrument.func.valueArray(plugs)
 }
 
}
MixerInputSpec {
 var <>index, <>voice, <>name, <>synth, <>volumeSlider, 
 <>killBox, <>copyBtn, <>editBtn, <>nameView;
 
 *new { arg index, voice, name, synth, volumeSlider, killBox, copyBtn, editBtn, nameView;
 ^super.new.init(index, voice, name, synth, volumeSlider, killBox, copyBtn, editBtn, nameView)
 }
 init { arg argIndex, argMixerVoice, argName, argSynth, argVolumeSlider, 
 argKillBox, argCopyBtn, argEditBtn, argNameView;
 index = argIndex;
 voice = argMixerVoice;
 name = argName;
 synth = argSynth;
 volumeSlider = argVolumeSlider;
 killBox = argKillBox;
 copyBtn = argCopyBtn;
 editBtn = argEditBtn;
 nameView = argNameView;
 }
}
ParamSpec {
 classvar <>specs;
 var <>minval, <>maxval, <>step, <>warp;
 
 *new { arg minval=0.0, maxval=1.0, step=0.0, warp='linear';
 ^super.new.minval_(minval).maxval_(maxval).step_(step).warp_(warp)
 }
 
 *default {
 specs = Environment.new;
 
   specs.use({
 ~freq = ParamSpec(20,20000,0,\exponential);
 ~ffreq = ParamSpec(20,20000,0,\exponential);  ~rq = ParamSpec(0.001,5,0,\exponential);
 
 ~amp = ParamSpec(0.0001, 1.0, 0.0, \exponential);
 ~pan = ParamSpec(-1, 1);
 ~velocity = ParamSpec(0, 127);
 ~detune = ParamSpec(-20,20);
 ~beats = ParamSpec(0,20);
 
 ~rate = ParamSpec(0.125, 8 ,0, \exponential);  
 ~vibRate = ParamSpec(0, 20);  ~vibDepth = ParamSpec(0, 1);
 ~tremRate = ParamSpec(0, 20);  ~tremDepth = ParamSpec(0, 1);
 ~panRate = ParamSpec(0, 20);
 ~panDepth = ParamSpec(0, 1);
 
 ~delay = ParamSpec(0.0001, 1, 0, \exponential);
 });
 }
}



Pulsaret { 
 var <pulsaretTable, <pulseEnvTable, 
 <comboSig, <xFadeSignals, 
 newComboSig, newPulsaret, newPulseEnv,
 <reverseComboSig, <previousReverseComboSig, <reverseXFadeSignals, 
 <whichUpdate = \instant, updateMethods; 
 *new { arg pulsaret, pulseEnv; 
 ^super.new.initTables(pulsaret, pulseEnv).initUpdateMethods; 
 }
 
 setPulsaretAndEnv { arg pulsaret, pulseEnv; 
 ^this.initTables(pulsaret, pulseEnv);
 }
 setPulsaret { arg newPulsaret; 
 pulsaretTable = newPulsaret; 
 this.updateComboSig;
 }
 
 setPulseEnv { arg newPulseEnv; 
 pulseEnvTable = newPulseEnv; 
 this.updateComboSig;
 }
 
 initTables { arg inPulsaret, inPulseEnv; 
 if ([ inPulsaret, inPulseEnv ].includes(nil).not, 
 
 { pulsaretTable.removeDependant(this);
 pulseEnvTable.removeDependant(this); 
 
 pulsaretTable = inPulsaret; 
 pulseEnvTable = inPulseEnv;
 
 pulsaretTable.addDependant(this);
 pulseEnvTable.addDependant(this); 
 
 this.updateComboSig;
 }
 );
 }
 
 initUpdateMethods {  updateMethods = IdentityDictionary[ 
 \none -> { "wait for explicit update: Pulsaret.updateComboSig;".postcln; }, 
 \instant -> { this.updateComboSig; PG.engine.pulsaretSignal_(comboSig); }, 
 \xFade -> { this.updateComboSig; PG.engine.xFadeSignals(xFadeSignals); }
 ];
 }
 
 updateMethod_ { arg key; 
 if ( [ \none, \instant, \xFade ].includes(key), 
 { whichUpdate = key; }
 );
 }
 
 updateMethod { updateMethods.at(whichUpdate) }
 
 update { updateMethods.at(whichUpdate).value }
 
 updateComboSig { 
 comboSig = pulsaretTable.asSignal * pulseEnvTable.asSignal; 
 
 
   }
 
 adoptNewPulsaret { 
 this.initTables(newPulsaret, newPulseEnv); 
 }
 makeNewComboSig { arg aPulsaret, aPulseEnv; 
 ^newComboSig = aPulsaret.asSignal * aPulseEnv.asSignal; 
 }
 
 atSign { arg sign;   if (sign == -1.0, reverseComboSig, comboSig );
 }
 atSignXFade { arg sign;   if (sign == -1.0, reverseXFadeSignals, 
 xFadeSignals );
 }
}
StringToNumber { 
 *new { arg string; 
 
 var numValue = 0, sign = 1, commaShift, noCommaYet = true; 
 
 if (string.isNil, { "no string given.".postln; ^nil; });
 
 if (string.at(0) == $-, { 
 sign = -1; 
 string = string.copyRange(1, string.size - 1); 
 });
 
 if (string.isEmpty, { "string is empty.".postln; ^nil; });
 
 string.do({ arg eachChar, i; 
 
 if (eachChar.isDecDigit, 
 { numValue = numValue * 10 + eachChar.digit }, 
 { if (eachChar == $., 
 { if (noCommaYet, 
 { commaShift = i + 1; noCommaYet = false; }, 
 { "String contains more than one comma!".postln; 
 ^nil; 
 }
 );
 }, 
 { ("String contains at least one non-number character: " 
 ++ [ eachChar ] ++ ".").postln; 
 ^nil;
 }
 );
 }
 )
 }); 
 
 if (noCommaYet, 
 { ^numValue * sign; }, 
 { ^numValue * sign * (0.1 ** (string.size - commaShift)) }
 ); 
 
 }
}
SoundFile {
 classvar <>searchPaths;  
 var <path;  var <>headerFormat = 'Sun';
 var <>sampleFormat = '32 big endian float';
 var <dataOffset = 0;  var <numFrames = 0;  var <>numChannels = 1;  
 var <data;  var <>sampleRate = 44100.0;
 
 var <>midiNote = 60.0;
 var <>detune = 0;  var <>gain = 0;  
 var <>midiLo = 0;  var <>midiHi = 127;
 var <>velocLo = 0;  var <>velocHi = 127;
 
 var <>numLoops = 0;  var <>sustainLoopStart = 0.0;
 var <>sustainLoopEnd = 0.0;
 var <>releaseLoopStart = 0.0;
 var <>releaseLoopEnd = 0.0;
 
 var <fileID;  var <startFrame;  var <preloadBuffer;  
 *new { arg argData;
 ^super.new.data_(argData)
 }
 
 data_ { arg arrayOfSignal;
 data = arrayOfSignal;
 numChannels = arrayOfSignal.size;
 }
 duration { ^numFrames/sampleRate }
 
 read { arg pathName;
  var res;
 res = this.readHeader(pathName);
 if (res, {
 res = this.readData;
 });
 ^res
 }
 preload { arg pathName, argNumFrames, argStartFrame;
  var res;
 res = this.readHeader(pathName);
 if (res, {
 res = this.preloadData(argNumFrames, argStartFrame);
 });
 ^res
 }
 write { arg pathName;
  var res;
 res = this.writeHeader(pathName);
 if (res, {
 res = this.writeData;
 });
 ^res
 }
 readHeader { arg pathName; 
  _ReadSoundFileHeader
 ^this.primitiveFailed;
 }
 readData {
    _ReadSoundFileData
 ^this.primitiveFailed;
 }
 
 
 writeHeader { arg pathName;
    _WriteSoundFileHeader
 ^this.primitiveFailed;
 }
 writeData {
    _WriteSoundFileData
 ^this.primitiveFailed;
 }
 
 preloadData { arg argStartFrame=0, bufFrames=32768;
      
  
 _PreloadSoundFileData
 ^this.primitiveFailed;
 }
 prepareRecord {
    
 
 _PrepareSoundFileRecord
 ^this.primitiveFailed;
 }
 endRecord {
   _EndSoundFileRecord
 ^this.primitiveFailed;
 }
 find { arg name;
   if (searchPaths.notNil, {
 searchPaths.do({ arg path;
 if (this.readHeader(path ++ name), { ^true });
 });
 });
 ^false
 }
 plot { arg rect;
 if ( rect.isNil, { rect = Rect.newBy(80,80,400,400) });
 ^SignalWindow.new(path, rect, data, sampleRate);
 }
 
 *play { arg pathName, argStartFrame=0, argNumFrames=32768, duration, loop=false;
 var file;
 file = this.new;
 file.play(pathName, argStartFrame, argNumFrames, duration, loop);
 ^file
 }
 play { arg pathName, argStartFrame=0, argNumFrames=32768, duration=0.1, loop=false;
  var res;
 res = this.readHeader(pathName);
 if (res.not, { ^nil });
 Synth.play({
 DiskIn.ar(this, loop, argStartFrame, argNumFrames)
 }, duration); 
 }
 *scope { arg pathName, argStartFrame=0, argNumFrames=32768, duration=0.1;
 var file;
 file = this.new;
 file.scope(pathName, argStartFrame, argNumFrames, duration);
 ^file
 }
 scope { arg pathName, argStartFrame=0, argNumFrames=32768, duration=0.1;
  var res;
 res = this.readHeader(pathName);
 if (res.not, { ^nil });
 Synth.scope({
 DiskIn.ar(this, true, argStartFrame, argNumFrames)
 }, duration); 
 }
 
 *fftScope { arg pathName, argStartFrame=0, argNumFrames=32768;
 var file;
 file = this.new;
 file.scope(pathName, argStartFrame, argNumFrames);
 ^file
 }
 fftScope { arg pathName, argStartFrame=0, argNumFrames=32768;
  var res;
 res = this.readHeader(pathName);
 if (res.not, { ^nil });
 Synth.fftScope({
 DiskIn.ar(this, true, argStartFrame, argNumFrames)
 }); 
 }
 
 libMenuAction { arg names;
 var file;
  file = this.copy;
 if (file.readData, { file.plot; });
 }
}

SoundFileXT : SoundFile { 
    classvar <possibleRecFormats = #[ 
 
 [ 'AIFF', '16 big endian signed', "AIFF 16 bit linear" ],
 [ 'AIFF', '32 big endian signed', "AIFF 32 bit linear" ],
 [ 'SD2', '16 big endian signed', "SD2 16 bit linear" ],
 [ 'RIFF', '16 little endian signed', "WAV 16 bit linear" ],
 [ 'RIFF', '32 little endian signed', "WAV 32 bit linear" ],
 [ 'Sun', '16 big endian signed', "Sun 16 bit linear" ],
 [ 'Sun', '32 big endian signed', "Sun 32 bit linear" ],
 [ 'Sun', '32 big endian float', "Sun 32 bit float" ],
 [ 'IRCAM', '16 big endian signed', "IRCAM 16 bit linear" ],
 [ 'IRCAM', '32 big endian signed', "IRCAM 32 bit linear" ],
 [ 'IRCAM', '32 big endian float', "IRCAM 32 bit float" ]
 ];
 *currentFormatSpecs {   ^this.possibleHeaderFormats.detect({ 
 arg eachSpec; 
 this.possibleSampleFormats.includes(eachSpec) 
 });
 }
 currentFormatCanRecord { 
 ^this.currentFormatSpecs.notEmpty;
 }
 
 possibleHeaderFormats { arg headerF; 
 headerF = headerF ? headerFormat; 
 ^possibleRecFormats.select(
 { arg eachSpec; var header;
 header = eachSpec.at(0); 
  headerF == header;
 }
 );
 }
 possibleSampleFormats { arg sampleF; 
 sampleF = sampleF ? sampleFormat; 
 ^possibleRecFormats.select(
 { arg eachSpec; var samples;
 samples = eachSpec.at(1); 
  sampleF == samples;
 }
 );
 }
 *exists { arg path = ""; 
 ^this.new.readHeader(path)
 }
 exists { arg path = ""; 
 ^this.class.exists(path);
 }
 isEmpty { 
 ^numFrames == 0;
 }
}
TextFile {  var <>text, <path; 
 
 *new { arg text; 
 ^super.new.text_(text);
 }
 *read { arg path;
 ^this.new.read(path);
 } 
 read { arg argPath; 
 var file, string, nextLineBreak, lines; 
 
 path = argPath;
 
 file = File(path,"r");  string = file.readAllString;  file.close; 
 
 if (string.isNil, { ("Text file " ++ path ++ " not found.").postcln; ^nil });
 lines = List.new; 
 while (
 { nextLineBreak = string.indexOf($\n); 
 nextLineBreak.notNil }, 
 { lines.add(string.copyRange(0, nextLineBreak - 1)); 
 string = string.copyRange(
 nextLineBreak + 1, 
 string.size - 1
 );
 }
 );
 lines.add( string.copyRange( 0, string.size - 1 ) ); 
 ^this.text_(lines);
 }
 
 *save { arg path, newText; 
 this.new(newText).save(path);
 }
 
 save { arg path; 
 var collOfStrings, numLines;
 var file, oneString = ""; 
 
 if ( text.isKindOf(String), 
 { oneString = text }, 
 { collOfStrings = text; 
 numLines = collOfStrings.size;
 collOfStrings.do({ arg eachLine, i;
 oneString = oneString ++ (eachLine.asString); 
 if (i < (numLines - 1), 
 { oneString = oneString ++ "\n" }
 );
 });
 });
 file = File(path, "w"); 
 file.write(oneString);
 file.close; 
 }
 *exists { arg path;   ^SoundFile.new.readHeader(path);
 }
 
 *getFile { arg action; 
 var file;
 GetFileDialog({ arg ok, path; 
 if (ok, { 
 file = TextFile.read(path); 
 action.value(file);
 });
 });
 }
 
 putFile { arg prompt = "Save textFile as:", defaultName = "untitled.txt";
 PutFileDialog(prompt, 
 defaultName, 
 { arg ok, path; 
 if (ok, { 
 TextFile.save(path, text); 
 });
 });
 }
 
 postText { 
 if (text.isKindOf(String), 
 { text.postln }, 
 { text.do({ arg line; line.postln; }); }
 );
 }
}

Class {
 var <name, <nextclass, superclass, <subclasses;
 var <methods, <instVarNames, <classVarNames;
 var <iprototype, <cprototype;
 var instanceFormat, instanceFlags, <instanceRawSize;
 var <classIndex, classFlags, <maxSubclassIndex;
 var <rawInitFunc;
 var <filenameSymbol, <lineNo;
     
 superclass { 
  ^superclass.asClass
 }
 asClass { ^this }
 *allClasses { _AllClasses }
 
 findMethod { arg methodName;
 if ( methods.notNil, {
 ^methods.detect({ arg method; method.name == methodName });
 },{ ^nil });
 }
 dumpByteCodes { arg methodName;
 var meth;
 meth = this.findMethod(methodName);
 if (meth.notNil, { meth.dumpByteCodes });
 }
 
 dumpClassSubtree { _DumpClassSubtree }
 dumpInterface {
   this.methods.do({ arg meth;
 var numargs;
 numargs = meth.argNames.size - 1;
 "   ".post;
 meth.name.post;
 " ( ".post;
 meth.argNames.do({ arg name, i;
 if (i > 0, {  name.post;
 if (i < numargs, {
 ", ".post;
 });
 });
 });
 " )\n".post;
 });
 }
 
 asString {
 ^name.asString
 }
 printOn { arg stream;
 stream << "class " << name;
 }
 storeOn { arg stream;
 stream << name;
 }
 
 hasHelpFile { _ClassHasHelpFile }
 openHelpFile { _ClassOpenHelpFile }
 
 deepCopyWithDict { ^this }
 shallowCopy { ^this }
}
Process {
  var classVars, <interpreter;
 var curThread, mainThread, <readyThreads;
 var <toolboxSem;
 
 startUp {
    toolboxSem = Semaphore.new(0);
 }
 hardwareSetup {
   }
 run {
   }
 shutDown {
   OSCPort.closeAll;
 }
 
 interpretCmdLine {
  interpreter.interpretCmdLine;
 }
 interpretPrintCmdLine {
  interpreter.interpretPrintCmdLine;
 }
 isDemo { _IsDemo }
 deepCopyWithDict { ^this }
 shallowCopy { ^this }
 
 toolboxAcquire { toolboxSem.acquire; }
 toolboxRelease { toolboxSem.release; }
 toolboxSync {
 this.prToolboxSync({ toolboxSem.release; });
 toolboxSem.acquire;
 }
 prToolboxSync { arg func;
 _ToolboxSync
 ^this.primitiveFailed
 }
}
FunctionDef {
 var <code, <literals, <prototypeFrame, <context, <argNames, <varNames;
    dumpByteCodes { _DumpByteCodes }
 
 numArgs { _FunDef_NumArgs }  numVars { _FunDef_NumVars }  varArgs { _FunDef_VarArgs } 
 deepCopyWithDict { ^this }
 shallowCopy { ^this }
}
Method : FunctionDef {
 var <ownerClass, <name, <primitiveName, <lineNo, <byteMeter, <callMeter;
}
Frame {
    deepCopyWithDict { error("Can't deepCopy a Frame."); this.halt; }
 shallowCopy { ^this }
}
RawPointer {
   }
Interpreter { 
   
 var <cmdLine;  var context;  
    var <>a, <>b, <>c, <>d, <>e, <>f, <>g, <>h, <>i, <>j;
 var <>k, <>l, <>m, <>n, <>o, <>p, <>q, <>r, <>s, <>t;
 var <>u, <>v, <>w, <>x, <>y, <>z;
 
 *new { ^this.shouldNotImplement }
 *newClear { ^this.shouldNotImplement }
 
 interpretCmdLine { ^this.interpret(cmdLine) }
 interpretPrintCmdLine { ^this.interpretPrint(cmdLine) }
 
 interpret { arg string;
  ^this.compile(string).value;
 }
 interpretPrint { arg string;
  ^this.compile(string).value.postln;
 }
 compile { arg string;
 _CompileExpression
     ^nil
 }
 
 clearAll {
 a = b = c = d = e = f = g = h = i = j = k = l = m = 
 n = o = p = q = r = s = t = u = v = w = x = y = z = nil;
 }
 
 executeFile { arg pathName;
 ^this.compileFile(pathName).value;
 }
 
 compileFile { arg pathName;
 var file, text;
 file = File.new(pathName, "r");
 if (file.isNil, { 
 error("file open failed\n");
 ^nil 
 });
 text = file.readAllString;
 file.close;
 ^this.compile(text)
 }
 
  functionCompileContext { 
    
 {}   }
 deepCopyWithDict { ^this }
 shallowCopy { ^this }
}
Magnitude : Object {
  
 == { arg aMagnitude; ^this.subclassResponsibility(thisMethod) }
 != { arg aMagnitude; ^(this == aMagnitude).not }
 hash { ^this.subclassResponsibility(thisMethod) }
 
  < { arg aMagnitude; ^this.subclassResponsibility(thisMethod) }
 
 > { arg aMagnitude; ^aMagnitude < this }
 <= { arg aMagnitude; ^(aMagnitude < this).not }
 >= { arg aMagnitude; ^(this < aMagnitude).not }
 exclusivelyBetween { arg lo, hi; ^(lo < this) and: { this < hi } }
 inclusivelyBetween { arg lo, hi; ^(lo <= this) and: { this <= hi } }
 min { arg aMagnitude; if (this < aMagnitude, {^this}, {^aMagnitude}) }
 max { arg aMagnitude; if (this < aMagnitude, {^aMagnitude}, {^this}) }
 clip { arg lo, hi; 
  ^if (this < lo, {^lo}, { if (hi < this, {^hi}, {^this}) }) 
 }
}
Infinitum : Magnitude {
      
 do { arg function;
 var i=0;
  loop({
 function.value(i, i);
 i = i + 1;
 });
 }
 == { arg aNumber; _EQ; ^aNumber.performBinaryOpOnInfinitum('==', this) }
 != { arg aNumber; _NE; ^aNumber.performBinaryOpOnInfinitum('!=', this) }
 < { arg aNumber; _LT; ^aNumber.performBinaryOpOnInfinitum('<', this) }
 > { arg aNumber; _GT; ^aNumber.performBinaryOpOnInfinitum('>', this) }
 <= { arg aNumber; _LE; ^aNumber.performBinaryOpOnInfinitum('<=', this) }
 >= { arg aNumber; _GE; ^aNumber.performBinaryOpOnInfinitum('>=', this) }
 min { arg aNumber; _Min; ^aNumber } 
 max { arg aNumber; _Max; ^this }
 performBinaryOpOnInfinitum { arg aSelector, aInf; ^error("Math operation failed.\n") }
 hash { _ObjectHash; ^this.primitiveFailed }
 *new { ^this.shouldNotImplement }
 *newClear { ^this.shouldNotImplement }
 printOn { arg stream;
 stream.putAll(this.asString);
 }
 storeOn { arg stream;
 stream.putAll(this.asCompileString);
 }
}
Association : Magnitude {
 var <>key, <>value;
 
 *new { arg key, value;
 ^super.new.key_(key).value_(value)
 }
 
 == { arg anAssociation;
 ^key == anAssociation.key
 }
 
 hash {
 ^key.hash
 }
 
 < { arg anAssociation;
 ^key < anAssociation.key
 }
 printOn { arg stream;
 stream << "(" << key << " -> " << value << ")";
 }
 storeOn { arg stream;
 stream << "(" <<< key << " -> " <<< value << ")";
 }
  embedInStream { arg inval;
 ^inval.add(this).yield;
 }
 transformEvent { arg event;
 ^event.add(this);
 }
}
Char : Magnitude {
 *new { ^this.shouldNotImplement(thisMethod) }
 *newClear { ^this.shouldNotImplement(thisMethod) }
  
 ascii {
  _AsciiValue
 }
 digit {
  _DigitValue
 ^this.primitiveFailed
 }
 asAscii { ^this }
 
  toUpper {
 _ToUpper
 }
 toLower {
 _ToLower
 }
 
  isAlpha {
  _IsAlpha
 }
 isAlphaNum {
  _IsAlphaNum
 }
 isPrint {
  _IsPrint
 }
 isPunct {
  _IsPunct
 }
 isControl {
  _IsControl
 }
 isSpace {
 _IsSpace
  }
 isVowel { 
 ^"AEIOU".includes(this.toUpper);
 }
 
 isDecDigit {
  _IsDecDigit
 }
 
 < { arg aChar;
 ^this.ascii < aChar.ascii
 }
 
 *nl { ^$\n }
 *ff { ^$\f }
 *tab { ^$\t }
 *space { ^$  }
 *comma { ^$\, }
 printOn { arg stream;
 stream.put(this);
 }
 storeOn { arg stream;
 stream.putAll(this.asCompileString);
 }
}
Number : Magnitude {
 isNumber { ^true }
 
 + { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 - { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 * { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 +! { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 -! { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 *! { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 / { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 % { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 div { arg aNumber; ^this.subclassResponsibility(thisMethod) }
 **{ arg aNumber; ^this.subclassResponsibility(thisMethod) }
 squared { ^this * this }
 cubed { ^this * this * this }
 blend { arg aNumber, blendFrac = 0.5;
  ^(this * (1.0 - blendFrac)) + (aNumber * blendFrac)
 }
 
 performBinaryOpOnSeqColl { arg aSelector, aSeqColl; 
 ^aSeqColl.collect({ arg item; 
 item.perform(aSelector, this)
 }) 
 }
 performBinaryOpOnPoint { arg op, aPoint;
 ^Point.new(this.perform(op, aPoint.x), this.perform(op, aPoint.y));
 } 
 
  rho { ^this }
 theta { ^0.0 }
 
  real { ^this }
 imag { ^0.0 }
  @ { arg aNumber; ^Point.new(this, aNumber) }
 complex { arg imaginaryPart; ^Complex.new(this, imaginaryPart) } 
 polar { arg angle; ^Polar.new(this, angle) } 
  for { arg endValue, function; 
 var i;
 i = this;
 while ({ i <= endValue }, { function.value(i); i = i + 1; });
 }
 forBy { arg endValue, stepValue, function; 
 var i;
 i = this;
 while ({ i <= endValue }, { function.value(i); i = i + stepValue; });
 }
}
Complex : Number {
 var <>real, <>imag; 
 *new { arg aReal, anImag;
 ^super.new.real_(aReal).imag_(anImag);
 }
 
 + { arg aNumber;
 if ( aNumber.isNumber, {
 ^Complex.new(real + aNumber.real, imag + aNumber.imag)
 },{
 ^aNumber.performBinaryOpOnComplex('+', this)
 });
 }
 - { arg aNumber; 
 if ( aNumber.isNumber, {
 ^Complex.new(real - aNumber.real, imag - aNumber.imag)
 },{
 ^aNumber.performBinaryOpOnComplex('-', this)
 });
 }
 * { arg aNumber; 
 if ( aNumber.isNumber, {
 ^Complex.new(
   (real * aNumber.real) - (imag * aNumber.imag),
 (real * aNumber.imag) + (imag * aNumber.real)
 )
 },{
 ^aNumber.performBinaryOpOnComplex('*', this)
 });
 }
 / { arg aNumber; 
 var denom, yr, yi;
 if ( aNumber.isNumber, {
 yr = aNumber.real; 
 yi = aNumber.imag;
 denom = 1.0 / (yr * yr + (yi * yi));
 ^Complex.new(
 ((real * yr) + (imag * yi)) * denom, 
 ((imag * yr) - (real * yi)) * denom)
 },{
 ^aNumber.performBinaryOpOnComplex('/', this)
 });
 }
 < { arg aNumber; 
 if ( aNumber.isNumber, {
 ^real < aNumber.real
 },{
 ^aNumber.performBinaryOpOnComplex('<', this)
 });
 }
 == { arg aNumber; 
 if ( aNumber.isNumber, {
 ^real == aNumber.real and: { imag == aNumber.imag }
 },{
 ^aNumber.performBinaryOpOnComplex('==', this)
 });
 }
 hash {
 ^real.hash bitXor: imag.hash
 }
 
  performBinaryOpOnSimpleNumber { arg aSelector, aNumber; 
 ^aNumber.asComplex.perform(aSelector, this) 
 }
 performBinaryOpOnSignal { arg aSelector, aSignal; ^aSignal.asComplex.perform(aSelector, this) }
 performBinaryOpOnComplex { arg aSelector, aNumber; ^error("Math operation failed.\n") }
 performBinaryOpOnUGen { arg aSelector, aUGen;
 ^aUGen.asComplex.perform(aSelector, this)
 }
 
 neg { ^Complex.new(real.neg, imag.neg) }
 conjugate { ^Complex.new(real, imag.neg) }
 
 magnitude { ^hypot(real, imag) }
 rho { ^hypot(real, imag) }
 magnitudeApx { ^hypotApx(real, imag) }
 
 angle { ^atan2(imag, real) }
 phase { ^atan2(imag, real) }
 theta { ^atan2(imag, real) }
 
 coerce { arg aNumber; ^aNumber.asComplex }
 
 asInteger { ^real.asInteger }
 asFloat { ^real.asFloat }
 asComplex { ^this }
 asPolar { ^Polar.new(this.rho, this.theta) }
 
 printOn { arg stream;
 stream << "Complex( " << real << ", " << imag << " )";
 }
 storeOn { arg stream;
 stream << "Complex( " <<< real << ", " <<< imag << " )";
 }
}
SimpleNumber : Number {
 
 isValidUGenInput { ^true }
 magnitude { ^this.abs }
 angle { if (this >= 0, {^0.0}, {^pi} ) }
 degreeToKey { arg scale, stepsPerOctave=12;
 var size, scaleDegree;
 size = scale.size;
 scaleDegree = this.asInteger;
 ^(stepsPerOctave * (scaleDegree div: size)) + scale.wrapAt(scaleDegree.asInteger)
 }
 neg { _Neg; ^this.primitiveFailed }
 bitNot { _BitNot; ^this.primitiveFailed }
 abs { _Abs; ^this.primitiveFailed }
 ceil { _Ceil; ^this.primitiveFailed }
 floor { _Floor; ^this.primitiveFailed }
 frac { _Frac; ^this.primitiveFailed }
 sign { _Sign; ^this.primitiveFailed }
 squared { _Squared; ^this.primitiveFailed }
 cubed { _Cubed; ^this.primitiveFailed }
 sqrt { _Sqrt; ^this.primitiveFailed }
 exp { _Exp; ^this.primitiveFailed }
 reciprocal { _Recip; ^this.primitiveFailed }
 midicps { _MIDICPS; ^this.primitiveFailed }
 cpsmidi { _CPSMIDI; ^this.primitiveFailed }
 midiratio { _MIDIRatio; ^this.primitiveFailed }
 ratiomidi { _RatioMIDI; ^this.primitiveFailed }
 ampdb { _AmpDb; ^this.primitiveFailed }
 dbamp { _DbAmp; ^this.primitiveFailed } 
 octcps { _OctCPS; ^this.primitiveFailed }
 cpsoct { _CPSOct; ^this.primitiveFailed }
 log { _Log; ^this.primitiveFailed }
 log2 { _Log2; ^this.primitiveFailed }
 log10 { _Log10; ^this.primitiveFailed }
 sin { _Sin; ^this.primitiveFailed }
 cos { _Cos; ^this.primitiveFailed }
 tan { _Tan; ^this.primitiveFailed }
 asin { _ArcSin; ^this.primitiveFailed }
 acos { _ArcCos; ^this.primitiveFailed }
 atan { _ArcTan; ^this.primitiveFailed }
 sinh { _SinH; ^this.primitiveFailed }
 cosh { _CosH; ^this.primitiveFailed }
 tanh { _TanH; ^this.primitiveFailed }
 rand { _Rand; ^this.primitiveFailed }
 rand2 { _Rand2; ^this.primitiveFailed }
 linrand { _LinRand; ^this.primitiveFailed }
 bilinrand { _BiLinRand; ^this.primitiveFailed }
 sum3rand { _Sum3Rand; ^this.primitiveFailed }
 distort { _Distort; ^this.primitiveFailed }
 softclip { _SoftClip; ^this.primitiveFailed }
  coin { _Coin; ^this.primitiveFailed }
 even { _Even; ^this.primitiveFailed }
 odd { _Odd; ^this.primitiveFailed }
 isPositive { _IsPositive; ^this.primitiveFailed }
 isNegative { _IsNegative; ^this.primitiveFailed }
 isStrictlyPositive { _IsStrictlyPos; ^this.primitiveFailed }
 
 + { arg aNumber; _Add; ^aNumber.performBinaryOpOnSimpleNumber('+', this) }
 - { arg aNumber; _Sub; ^aNumber.performBinaryOpOnSimpleNumber('-', this) }
 * { arg aNumber; _Mul; ^aNumber.performBinaryOpOnSimpleNumber('*', this) }
 +! { arg aNumber; _AddInPlace; ^aNumber.performBinaryOpOnSimpleNumber('+!', this) }
 -! { arg aNumber; _SubInPlace; ^aNumber.performBinaryOpOnSimpleNumber('-!', this) }
 *! { arg aNumber; _MulInPlace; ^aNumber.performBinaryOpOnSimpleNumber('*!', this) }
 / { arg aNumber; _FDiv; ^aNumber.performBinaryOpOnSimpleNumber('/', this) }
 % { arg aNumber; _Mod; ^aNumber.performBinaryOpOnSimpleNumber('%', this) }
 div { arg aNumber; _IDiv; ^aNumber.performBinaryOpOnSimpleNumber('div', this) }
 ** { arg aNumber; _Pow; ^aNumber.performBinaryOpOnSimpleNumber('**', this) }
 min { arg aNumber; _Min; ^aNumber.performBinaryOpOnSimpleNumber('min', this) } 
 max { arg aNumber; _Max; ^aNumber.performBinaryOpOnSimpleNumber('max', this) }
 & { arg aNumber; _BitAnd; ^aNumber.performBinaryOpOnSimpleNumber('&', this) }
 | { arg aNumber; _BitOr; ^aNumber.performBinaryOpOnSimpleNumber('|', this) }
 bitXor { arg aNumber; _BitXor; ^aNumber.performBinaryOpOnSimpleNumber('bitXor', this) } 
 lcm { arg aNumber; _LCM; ^aNumber.performBinaryOpOnSimpleNumber('lcm', this) }
 gcd { arg aNumber; _GCD; ^aNumber.performBinaryOpOnSimpleNumber('gcd', this) }
 round { arg aNumber; _Round; ^aNumber.performBinaryOpOnSimpleNumber('round', this) }
 trunc { arg aNumber; _Trunc; ^aNumber.performBinaryOpOnSimpleNumber('trunc', this) }
 atan2 { arg aNumber; _Atan2; ^aNumber.performBinaryOpOnSimpleNumber('atan2', this) } 
 hypot { arg aNumber; _Hypot; ^aNumber.performBinaryOpOnSimpleNumber('hypot', this) }
 hypotApx { arg aNumber; _HypotApx; ^aNumber.performBinaryOpOnSimpleNumber('hypotApx', this) }
 << { arg aNumber; _ShiftLeft; ^aNumber.performBinaryOpOnSimpleNumber('<<', this) }
 >> { arg aNumber; _ShiftRight; ^aNumber.performBinaryOpOnSimpleNumber('>>', this) }
 +>> { arg aNumber; _UnsignedShift; ^aNumber.performBinaryOpOnSimpleNumber('+>>', this) }
 ring1 { arg aNumber; _Ring1; ^aNumber.performBinaryOpOnSimpleNumber('ring1', this) }
 ring2 { arg aNumber; _Ring2; ^aNumber.performBinaryOpOnSimpleNumber('ring2', this) }
 ring3 { arg aNumber; _Ring3; ^aNumber.performBinaryOpOnSimpleNumber('ring3', this) }
 ring4 { arg aNumber; _Ring4; ^aNumber.performBinaryOpOnSimpleNumber('ring4', this) }
 difsqr { arg aNumber; _DifSqr; ^aNumber.performBinaryOpOnSimpleNumber('difsqr', this) }
 sumsqr { arg aNumber; _SumSqr; ^aNumber.performBinaryOpOnSimpleNumber('sumsqr', this) }
 sqrsum { arg aNumber; _SqrSum; ^aNumber.performBinaryOpOnSimpleNumber('sqrsum', this) }
 sqrdif { arg aNumber; _SqrDif; ^aNumber.performBinaryOpOnSimpleNumber('sqrdif', this) }
 absdif { arg aNumber; _AbsDif; ^aNumber.performBinaryOpOnSimpleNumber('absdif', this) }
 thresh { arg aNumber; _Thresh; ^aNumber.performBinaryOpOnSimpleNumber('thresh', this) }
 amclip { arg aNumber; _AMClip; ^aNumber.performBinaryOpOnSimpleNumber('amclip', this) }
 scaleneg { arg aNumber; _ScaleNeg; ^aNumber.performBinaryOpOnSimpleNumber('scaleneg', this) }
 clip2 { arg aNumber; _Clip2; ^aNumber.performBinaryOpOnSimpleNumber('clip2', this) }
 fold2 { arg aNumber; _Fold2; ^aNumber.performBinaryOpOnSimpleNumber('fold2', this) }
 wrap2 { arg aNumber; _Wrap2; ^aNumber.performBinaryOpOnSimpleNumber('wrap2', this) }
 excess { arg aNumber; _Excess; ^aNumber.performBinaryOpOnSimpleNumber('excess', this) }
 <! { arg aNumber; _FirstArg; ^aNumber.performBinaryOpOnSimpleNumber('<!', this) }
 rrand { arg aNumber; _RandRange; ^aNumber.performBinaryOpOnSimpleNumber('rrand', this) }
 exprand { arg aNumber; _ExpRandRange; ^aNumber.performBinaryOpOnSimpleNumber('exprand', this) }
 == { arg aNumber; _EQ; ^aNumber.performBinaryOpOnSimpleNumber('==', this) }
 != { arg aNumber; _NE; ^aNumber.performBinaryOpOnSimpleNumber('!=', this) }
 < { arg aNumber; _LT; ^aNumber.performBinaryOpOnSimpleNumber('<', this) }
 > { arg aNumber; _GT; ^aNumber.performBinaryOpOnSimpleNumber('>', this) }
 <= { arg aNumber; _LE; ^aNumber.performBinaryOpOnSimpleNumber('<=', this) }
 >= { arg aNumber; _GE; ^aNumber.performBinaryOpOnSimpleNumber('>=', this) }
 hash { _ObjectHash; ^this.primitiveFailed }
 
 asInteger { _AsInt; ^this.primitiveFailed }
 asFloat { _AsFloat; ^this.primitiveFailed }
 asComplex { ^Complex.new(this, 0.0) }
 asSignal { _AsSignal; this.primitiveFailed }
 
 performBinaryOpOnSimpleNumber { arg aSelector, aNumber; ^error("Math operation failed.\n") }
 performBinaryOpOnComplex { arg aSelector, aComplex; ^aComplex.perform(aSelector, this.asComplex) }
 performBinaryOpOnSignal { arg aSelector, aSignal; ^error("Math operation failed.\n") }
 performBinaryOpOnInfinitum { arg aSelector, aInf; ^error("Math operation failed.\n") }
 
  wait {
 thisThread.wait(this)
 }
 
 printOn { arg stream;
 stream.putAll(this.asString);
 }
 storeOn { arg stream;
 stream.putAll(this.asString);
 }
}
Integer : SimpleNumber {
 isInteger { ^true } 
 
 + { arg aNumber; _AddInt; ^aNumber.performBinaryOpOnSimpleNumber('+', this) }
 - { arg aNumber; _SubInt; ^aNumber.performBinaryOpOnSimpleNumber('-', this) }
 * { arg aNumber; _MulInt; ^aNumber.performBinaryOpOnSimpleNumber('*', this) }
 
 clip { arg lo, hi; _ClipInt; ^this.primitiveFailed }
 wrap { arg lo, hi; _WrapInt; ^this.primitiveFailed }
 fold { arg lo, hi; _FoldInt; ^this.primitiveFailed }
 
 xrand { arg exclude=0; 
 ^(exclude + (this - 1).rand + 1) % this;
 }
 xrand2 { arg exclude=0; 
 var res;
 res = (2 * this).rand - this;
 if (res == exclude, { ^this },{ ^res });
 }
 
  do { arg function;
   var i = 0;
 while ({ i < this }, { function.value(i, i); i = i + 1; });
 }
 reverseDo { arg function;
   var i=0, j=0;
 i = this - 1;
 while ({ i >= 0 }, { function.value(i, j); i = i - 1; j = j + 1; });
 }
 for { arg endval, function;
   var i, j=0;
 i = this;
 while ({ i <= endval }, { function.value(i, j); i = i + 1; j = j + 1; });
 }
 forBy { arg endval, stepval, function;
   var i, j=0;
 i = this;
 while ({ i <= endval }, { function.value(i, j); i = i + stepval; j = j + 1; });
 }
 to { arg hi, step=1;
 ^Interval.new(this, hi, step)
 }
 
  asAscii {
  _AsAscii
 ^this.primitiveFailed
 }
 asDigit {
  _AsDigit
 ^this.primitiveFailed
 }
 
 nextPowerOfTwo {
 _NextPowerOfTwo
 }
 isPowerOfTwo {
 _IsPowerOfTwo
 }
 nthPrime {
 _NthPrime
 }
 prevPrime {
 _PrevPrime
 }
 nextPrime {
 _NextPrime
 }
 isPrime {
  _IsPrime
 
 }
 getKeys { _GetKeys } 
}
Float : SimpleNumber {
 isFloat { ^true } 
 asFloat { ^this } 
 + { arg aNumber; _AddFloat; ^aNumber.performBinaryOpOnSimpleNumber('+', this) }
 - { arg aNumber; _SubFloat; ^aNumber.performBinaryOpOnSimpleNumber('-', this) }
 * { arg aNumber; _MulFloat; ^aNumber.performBinaryOpOnSimpleNumber('*', this) }
 
 clip { arg lo, hi; _ClipFloat; ^this.primitiveFailed }
 wrap { arg lo, hi; _WrapFloat; ^this.primitiveFailed }
 fold { arg lo, hi; _FoldFloat; ^this.primitiveFailed }
 
 coin { ^1.0.rand < this }
 xrand2 { ^this.rand2 }
 as32Bits { _As32Bits }  
  do { arg function;
   var i = 0.0;
 while ({ (i + 0.5) < this }, { function.value(i, i); i = i + 1.0; });
 }
 reverseDo { arg function;
   var i, j=0.0;
 i = this - 1.0;
 while ({ (i + 0.5) >= 0.0 }, { function.value(i, j); i = i - 1.0; j = j + 1.0; });
 }
}

Polar : Number {
 var <>rho, <>theta;
 
 *new { arg rho, theta;
 ^super.new.rho_(rho).theta_(theta)
 }
 
 magnitude { ^rho }
 
 angle { ^theta }
 phase { ^theta }
 
 real { ^rho * cos(theta) }
 imag { ^rho * sin(theta) }
 
 asComplex { ^Complex.new(this.real, this.imag) }
 asPoint { ^Point.new(this.real, this.imag) }
 scale { arg scale; 
 ^Polar.new(rho * scale, theta)
 } 
 rotate { arg angle;  ^Polar.new(rho, theta + angle)
 }
 
  + { arg aNumber; ^this.asComplex + aNumber }
 - { arg aNumber; ^this.asComplex - aNumber }
 * { arg aNumber; ^this.asComplex * aNumber }
 / { arg aNumber; ^this.asComplex / aNumber }
 
 neg { ^Polar.new(rho, theta + pi) }
 performBinaryOpOnUGen { arg aSelector, aUGen;
 ^Complex.new(
 BinaryOpUGen.new(aSelector, aUGen, this.real), 
 BinaryOpUGen.new(aSelector, aUGen, this.imag)
 ); 
 }
 printOn { arg stream;
 stream << "Polar( " << rho << ", " << theta << " )";
 }
 storeOn { arg stream;
 stream << "Polar( " <<< rho << ", " <<< theta << " )";
 }
}

Pattern : AbstractFunction {
 play { arg protoEvent, channels=2, controls, effects, duration;
 Synth.play( this.asUGenFunc( protoEvent, channels, controls, effects ), duration );
 }
 scope { arg protoEvent, channels=2, controls, effects, duration = 0.1;
 Synth.scope( this.asUGenFunc( protoEvent, channels, controls, effects ), duration );
 }
 bench { arg protoEvent, channels=2, controls, effects, duration;
 Synth.bench( this.asUGenFunc( protoEvent, channels, controls, effects ), duration);
 }
 
  ++ { arg aPattern;
 ^Pseq.new([this, aPattern])
 } 
 
 asEventStream { arg protoEvent;
 if (protoEvent.isNil, { protoEvent = Event.protoEvent; });
 ^EventStream.new(this.asStream, protoEvent);
 }
 asStream {
 ^this.subclassResponsibility(thisMethod)
 }
 asUGenFunc { arg protoEvent, channels, controls, effects;
 ^{ arg outersynth;
 var outputs;
 
 if (controls.notNil, {
 controls.value(outersynth);
 });
 
 outputs = this.asSpawn(protoEvent, channels);
 
 if (effects.notNil, {
 effects.value(outputs, outersynth);
 },{
 outputs;
 });
 }
 }
  asSpawn { arg protoEvent, channels=1;
 var stream, outersynth, out, tspawn;
 stream = this.asEventStream(protoEvent);
 outersynth = thisSynth;
 
 out = TSpawn.ar(nil, channels);
 
 tspawn = out.source;
 stream.protoEvent.put(\inSpawn, tspawn);
 stream.protoEvent.put(\outersynth, outersynth);
 
  outersynth.sched(0, { arg outersynth, now, task;
 var event, freqs, lag, newSynth, inSpawn;
 newSynth = Synth.new({ arg innersynth;
 event = stream.next;
 if (event.isNil, { 
 nil
 },{
 event.use({
 var freqs, strum;
 outersynth.sched(event.delta, task);  outersynth.tempo = ~tempo.value;
 freqs = ~freq;
 if ( freqs.isKindOf(Symbol), {
 nil;  },{
 ~outersynth = outersynth;
 ~innersynth = innersynth;
 innersynth.channelOffset = ~chanOffset;
 if (freqs.isKindOf(SequenceableCollection), {
 strum = ~strum;
 if (strum == 0.0, {
  innersynth.releaseTime = ~sustain;
 Mix.arFill(freqs.size, { arg i;
 ~freq = freqs.at(i);
 ~ugenFunc.valueEnvir;
 });
 },{
  if (strum > 0.0, {
 Spawn.ar({ arg sp, i, strumsynth;
 strumsynth.releaseTime = ~sustain;
 ~freq = freqs.at(i);
 ~ugenFunc.valueEnvir;
 }, channels, strum, freqs.size);
 },{  Spawn.ar({ arg sp, i, strumsynth;
 strumsynth.releaseTime = ~sustain;
 ~freq = freqs.at(freqs.size - 1 - i);
 ~ugenFunc.valueEnvir;
 }, channels, strum.abs, freqs.size);
 });
 },{
 });
 },{
 innersynth.releaseTime = ~sustain;
 ~ugenFunc.valueEnvir;  });
 });
 });  });
 });  if (newSynth.notNil, {
 inSpawn = event.at(\inSpawn);
 
 lag = event.at(\lag);
 if (lag <= 0.0, {
 inSpawn.triggerSynth(newSynth);
 },{
  inSpawn.synth.sched(lag, { 
 inSpawn.triggerSynth(newSynth); 
 });
 });
 });
 });
 ^out
 }
 
 embedInStream { arg inval;
 var stream, outval;
 stream = this.asStream;
 while ({
 outval = stream.value(inval);
 outval.notNil
 },{
 inval = outval.yield;
 });
 ^inval
 }
 
 collect { arg function;
 ^Pcollect.new(function, this)
 }
 select { arg function;
 ^Pselect.new(function, this)
 }
 reject { arg function;
 ^Preject.new(function, this)
 }
 
  composeUnaryOp { arg operator;
 ^Punop.new(operator, this)
 }
 composeBinaryOp { arg operator, pattern;
 ^Pbinop.new(operator, this, pattern)
 }
 reverseComposeBinaryOp { arg operator, pattern;
 ^Pbinop.new(operator, pattern, this)
 }
 composeNAryOp { arg selector, argList;
 ^this.notYetImplemented
 }
}
EventStream {
 var <>stream, <>protoEvent;
 *new { arg stream, protoEvent;
 ^super.new.stream_(stream).protoEvent_(protoEvent);
 }
 next {
 ^stream.next( protoEvent.copy ).finishEvent
 }
}
Pfunc : Pattern {
 var <>nextFunc;  var <>resetFunc;  *new { arg nextFunc, resetFunc; 
 ^super.new.nextFunc_(nextFunc).resetFunc_(resetFunc)
 }
 asStream {
 ^FuncStream.new(nextFunc, resetFunc)
 }
}
Prout : Pattern {
 var <>routineFunc;
 *new { arg routineFunc;
 ^super.new.routineFunc_(routineFunc)
 }
 asStream {
 ^Routine.new(routineFunc)
 }
}
Pfuncn : Pattern {
 var <>func, <>repeats;
 *new { arg func, repeats = 1;
 ^super.new.func_(func).repeats_(repeats)
 }
 asStream { 
 ^Routine.new({ arg inval;
 repeats.value.do({
 inval = func.value(inval).yield;
 });
 });
 }
} 
Punop : Pattern {
 var <>operator, <>a;
 *new { arg operator, a;
 ^super.new.a_(a).operator_(operator)
 }
 asStream {
 var stream;
 stream = a.asStream;
 ^UnaryOpStream.new(operator, stream);
 }
}
Pbinop : Pattern {
 var <>operator, <>a, <>b;
 *new { arg operator, a, b;
 ^super.new.a_(a).b_(b).operator_(operator)
 }
 asStream {
 var streamA, streamB;
 streamA = a.asStream;
 streamB = b.asStream;
 ^BinaryOpStream.new(operator, streamA, streamB);
 }
}
Pbind : Pattern {
 var <>patternpairs;
 *new { arg ... pairs;
 if (pairs.size.odd, { "Pbind should have even number of args.\n".error; this.halt });
 ^super.new.patternpairs_(pairs)
 }
 asStream {
 var streampairs, endval;
 
 streampairs = patternpairs.copy;
 endval = streampairs.size - 1;
 forBy (1, endval, 2, { arg i;
 streampairs.put(i, streampairs.at(i).asStream);
 });
 
 ^FuncStream.new({ arg inval;
 var sawNil = false;
 forBy (0, endval, 2, { arg i;
 var name, stream, streamout;
 name = streampairs.at(i);
 stream = streampairs.at(i+1);
 
 streamout = stream.next(inval);
 
 if (streamout.isNil, {
 sawNil = true;
 },{
 if (name.isKindOf(SequenceableCollection), { 
 streamout.do({ arg val, i;
 inval.put(name.at(i), val);
 });
 },{
 inval.put(name, streamout);
 });
 });
 });
 if (sawNil, { nil },{ 
 inval 
 });
 },{
 streampairs = patternpairs.copy;
 endval = streampairs.size - 1;
 forBy (1, endval, 2, { arg i;
 streampairs.put(i, streampairs.at(i).asStream);
 });
 });
 }
}
Pseries : Pattern {  var <>start=0, <>step=1, <>length=inf;
 *new { arg start = 0, step = 1, length=inf;
 ^super.new.start_(start).step_(step).length_(length)
 } 
 asStream {
 var cur, counter = 0;
 cur = start;
 ^FuncStream.new({
 var outval;
 if (counter >= length, {
 nil
 },{
 outval = cur;
 cur = cur + step;
 counter = counter + 1;
 outval
 });
 },{
 cur = start;
 counter = 0;
 });
 }
}
 
Pgeom : Pattern {  var <>start=1.0, <>grow=1.0, <>length=inf;
 *new { arg start = 0, grow = 1, length=inf;
 ^super.new.start_(start).grow_(grow).length_(length)
 } 
 asStream {
 var cur, counter = 0;
 cur = start;
 ^FuncStream.new({
 var outval;
 if (counter >= length, {
 nil
 },{
 outval = cur;
 cur = cur * grow;
 counter = counter + 1;
 outval
 });
 },{
 cur = start;
 counter = 0;
 });
 }
}
Pbrown : Pattern {
 var <>lo, <>hi, <>step, <>length;
 *new { arg lo, hi, step, length=inf;
 ^super.new.lo_(lo).hi_(hi).step_(step).length_(length)
 } 
 asStream {
 ^Routine.new({
 var cur;
 cur = lo rrand: hi;
 length.do({
 cur = (cur + step.xrand2).fold(lo,hi);
 cur.yield; 
 });
 });
 }
} 
Pwhite : Pattern {
 var <>lo, <>hi, <>length;
 *new { arg lo, hi, length=inf;
 ^super.new.lo_(lo).hi_(hi).length_(length)
 } 
 asStream {
 ^Routine.new({
 length.do({
 rrand(lo,hi).yield; 
 });
 });
 }
} 
 
Pstep2add : Pattern {
 var <>pattern1, <>pattern2;
 *new { arg pattern1, pattern2;
 ^super.new.pattern1_(pattern1).pattern2_(pattern2)
 } 
 asStream {
 ^Routine.new({ arg inval;
 var stream1, stream2, val1, val2;
 
 stream1 = pattern1.asStream;
 while ({
 (val1 = stream1.next(inval)).notNil;
 },{
 stream2 = pattern2.asStream;
 while ({
 (val2 = stream2.next(inval)).notNil;
 },{
 inval = yield( val1 + val2 );
 });
 });
 }) 
 }
}
Pstep3add : Pattern {
 var <>pattern1, <>pattern2, <>pattern3;
 *new { arg pattern1, pattern2, pattern3;
 ^super.new.pattern1_(pattern1).pattern2_(pattern2).pattern3_(pattern3)
 } 
 asStream {
 ^Routine.new({ arg inval;
 var stream1, stream2, stream3, val1, val2, val3;
 
 stream1 = pattern1.asStream;
 while ({
 (val1 = stream1.next(inval)).notNil;
 },{
 stream2 = pattern2.asStream;
 while ({
 (val2 = stream2.next(inval)).notNil;
 },{
 stream3 = pattern3.asStream;
 while ({
 (val3 = stream3.next(inval)).notNil;
 },{
 inval = yield( val1 + val2 + val3 );
 });
 });
 });
 }) 
 }
}


ExternalControlSource {
 var <>minval = 0.0, <>maxval = 1.0, <>warp = 'linear';
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear';
 ^super.new.setRange(minval, maxval, warp).activate;
 }
 setRange { arg argMinVal, argMaxVal, argWarp;
 minval = argMinVal;
 maxval = argMaxVal;
 warp = argWarp;
 }
 value {
 _ExternalControlSource_Value
 ^this.primitiveFailed
 } 
  activate {
  _ExternalControlSource_Activate
 ^this.primitiveFailed
 } 
}
MIDIController : ExternalControlSource {
 var <>channel = 1, <>controller = 7;
 
 *new { arg channel = 1, controller = 7, minval = 0.0, maxval = 1.0, warp = 'linear';
 ^super.new(minval, maxval, warp).initMIDIController(channel, controller);
 }
 *kr { arg channel = 1, controller = 7, minval = 0.0, maxval = 1.0, 
 warp = 'linear', lagTime = 0.1;
 ^Plug.kr(this.new(channel, controller, minval, maxval, warp), lagTime)
 }
 initMIDIController { arg argChannel, argController;
 channel = argChannel;
 controller = argController;
 }
 
}
MIDIPolyTouch : ExternalControlSource {
 var <>channel = 1, <>key = 60;
 
 *new { arg channel = 1, key = 60, minval = 0.0, maxval = 1.0, warp = 'linear';
 ^super.new(minval, maxval, warp).initMIDIPolyTouch(channel, key);
 }
 *kr { arg channel = 1, key = 60, minval = 0.0, maxval = 1.0, 
 warp = 'linear', lagTime = 0.1;
 ^Plug.kr(this.new(channel, key, minval, maxval, warp), lagTime)
 }
 initMIDIPolyTouch { arg argChannel, argKey;
 channel = argChannel;
 key = argKey;
 }
 
}
MIDINoteGate : ExternalControlSource {
 var <>channel = 1, <>key = 60;
 
 *new { arg channel = 1, key = 60, minval = 0.0, maxval = 1.0;
 ^super.new(minval, maxval).initMIDINoteGate(channel, key);
 }
 *kr { arg channel = 1, key = 60, minval = 0.0, maxval = 1.0, lagTime = 0.1;
 ^Plug.kr(this.new(channel, key, minval, maxval), lagTime)
 }
 initMIDINoteGate { arg argChannel, argKey;
 channel = argChannel;
 key = argKey;
 } 
}
MIDIPitchBend : ExternalControlSource {
 var <>channel = 1;
 
 *new { arg channel = 1, minval = -1.0, maxval = 1.0, warp = 'linear';
 ^super.new(minval, maxval, warp).initMIDIPitchBend(channel);
 }
 *kr { arg channel = 1, minval = -1.0, maxval = 1.0, 
 warp = 'linear', lagTime = 0.1;
 ^Plug.kr(this.new(channel, minval, maxval, warp), lagTime)
 }
 initMIDIPitchBend { arg argChannel;
 channel = argChannel;
 }
 
}
MIDITouch : ExternalControlSource {
 var <>channel = 1;
 
 *new { arg channel = 1, minval = 0.0, maxval = 1.0, warp = 'linear';
 ^super.new(minval, maxval, warp).initMIDITouch(channel);
 }
 *kr { arg channel = 1, minval = 0.0, maxval = 1.0, 
 warp = 'linear', lagTime = 0.1;
 ^Plug.kr(this.new(channel, minval, maxval, warp), lagTime)
 }
 initMIDITouch { arg argChannel;
 channel = argChannel;
 }
 
}
MIDIProgram : ExternalControlSource {
 var <>channel = 1;
 
 *new { arg channel = 1, minval = 0.0, maxval = 127.0, warp = 'linear';
 ^super.new(minval, maxval, warp).initMIDIProgram(channel);
 }
 *kr { arg channel = 1, minval = 0.0, maxval = 1.0, 
 warp = 'linear', lagTime = 0.1;
 ^Plug.kr(this.new(channel, minval, maxval, warp), lagTime)
 }
 initMIDIProgram { arg argChannel;
 channel = argChannel;
 }
 
}
MIDIMostRecentNote : ExternalControlSource {
 var <>channel = 1;
 
 *new { arg channel = 1, minval = 0.0, maxval = 127.0, warp = 'linear';
 ^super.new(minval, maxval, warp).initMIDIMostRecentNote(channel);
 }
 *kr { arg channel = 1, minval = 0.0, maxval = 127.0, 
 warp = 'linear', lagTime = 0.0;
 ^Plug.kr(this.new(channel, minval, maxval, warp), lagTime)
 }
 initMIDIMostRecentNote { arg argChannel;
 channel = argChannel;
 }
}
MIDIMostRecentVelocity : ExternalControlSource {
 var <>channel = 1;
 
 *new { arg channel = 1, minval = 0.0, maxval = 1.0, warp = 'linear';
 ^super.new(minval, maxval, warp).initMIDIMostRecentVelocity(channel);
 }
 *kr { arg channel = 1, minval = 0.0, maxval = 1.0, 
 warp = 'linear', lagTime = 0.1;
 ^Plug.kr(this.new(channel, minval, maxval, warp), lagTime)
 }
 initMIDIMostRecentVelocity { arg argChannel;
 channel = argChannel;
 }
}
MouseAxis : ExternalControlSource {
 var <>axis = 'x';  
 *kr { arg minval = 0.0, maxval = 1.0, warp = 'linear', lagTime = 0.1;
 ^Plug.kr(this.new(minval, maxval, warp), lagTime)
 }
}
MouseX : MouseAxis {
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear';
 ^super.new(minval, maxval, warp);
 }
}
MouseY : MouseAxis {
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear';
 ^super.new(minval, maxval, warp).axis_('y');
 }
}


TabletAxis : ExternalControlSource {
 var <>axis = 0;
 var <>device = 0; 
 
            
    
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear', axis = 0, device = 0;
 ^super.new.setRange(minval, maxval, warp).axis_(axis).device_(device);
 }
 
 *kr { arg minval = 0.0, maxval = 1.0, warp = 'linear', device = 0, lagTime = 0.1;
 ^Plug.kr(this.new(minval, maxval, warp, device), lagTime)
 }
}
TabletX : TabletAxis {
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 0, device);
 }
}
TabletY : TabletAxis {
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 1, device);
 }
}
TabletZ : TabletAxis {
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 2, device);
 }
}
TabletXTilt : TabletAxis {
 *new { arg minval = -1.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 3, device);
 }
}
TabletYTilt : TabletAxis {
 *new { arg minval = -1.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 4, device);
 }
}
TabletPressure : TabletAxis {
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 5, device);
 }
}
TabletTurn : TabletAxis {
 *new { arg minval = -1.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 6, device);
 }
}
TabletWheel : TabletAxis {
 *new { arg minval = -1.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 7, device);
 }
}
TabletProximity : TabletAxis {
 *new { arg minval = 0.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 8, device);
 }
}
TabletButton : TabletAxis {
 var <>button = 0; 
 
            
 
 *new { arg button = 0, minval = 0.0, maxval = 1.0, warp = 'linear', device = 0;
 ^super.new(minval, maxval, warp, 9, device).button_(button);
 }
 *kr { arg button = 0, minval = 0.0, maxval = 1.0, warp = 'linear', device = 0, lagTime = 0.1;
 ^Plug.kr(this.new(button, minval, maxval, warp, device), lagTime)
 }
}

GUIWindow {
 classvar <>allWindows, <>generatedCode;
 var <name, <views, <focus, <>onClose, 
 <>closeable =true, <isVisible = true;
 *new { arg name = "panel", bounds;
 ^super.new.initGUIWindow(name, bounds)
 }
 initGUIWindow { arg argName, argBounds;
 name = argName.asString;
 argBounds = if (argBounds.notNil, { argBounds },{ Rect.newBy(128, 64, 400, 400) });
  views = Array.new(8);
 if (allWindows.isNil, {
 allWindows = Array.new(8).add(this);
 },{
 allWindows = allWindows.add(this);
 });
 this.prInit(name, argBounds);
 }
 *closeAll {
 var list;
 list = allWindows;
 allWindows = Array.new(8);
 list.do({ arg window; window.close; });
 }
 close {
 if (this.closeable,  { 
 onClose.value;  allWindows.remove(this);
 this.prClose;
 views.do({ arg view; view.release; });
 views = nil; 
 }, 
 { this.hide }
 )
 }
 
 reallyClose { this.closeable_(true); this.close; } 
 
 *reallyCloseAll { 
 allWindows.do({ arg eachW; eachW.closeable = true });
 this.closeAll;
 }
 show { arg show = true; 
 isVisible = show; 
 this.prShow(show);
 }
 prShow { arg show = true;
 _GUIWindow_Show
 ^this.primitiveFailed
 }
 
 hide {
 this.show(false);
 }
 toFront {
 _GUIWindow_ToFront
 ^this.primitiveFailed
 }
 refresh {
 _GUIWindow_Refresh
 ^this.primitiveFailed
 }
 
 name_ { arg argName;
 name = argName;
 this.prSetName(argName);
 }
 bounds_ { arg argBounds; 
 this.prSetBounds(argBounds);
 }
 bounds {
 ^this.prGetBounds(Rect.new);
 }
 
 backColor_ { arg color;
 ^this.prSetColor(color, 0);
 }
 focusColor_ { arg color;
 ^this.prSetColor(color, 1);
 }
 
 backColor { 
 ^this.prGetColor(0);
 }
 focusColor {
 ^this.prGetColor(1)
 }
 
 add { arg argView;
 var res;
 res = this.checkNotClosed;
 if ( res, {
 views = views.add(argView);
 });
 ^res
 }
 remove { arg argView;
 if (this.checkNotClosed, {
 if (argView === this.focus, { this.focus_(nil) });
 views.remove(argView);
 this.prRmvView(argView);
 });
 }
 at { arg index;
 if (views.notNil, { ^views.at(index) }, { ^nil })
 }
 focus_ { arg aViewToFocus;
 focus = aViewToFocus;
 this.prSetFocus(aViewToFocus);
 }
 focusNext {
 var index, startIndex, maxIndex, view;
 
 if (views.size == 0, { ^this });
 maxIndex = views.size - 1;
 startIndex = views.indexOf(focus);
 if (startIndex.isNil, { startIndex = -1 });
 index = startIndex;
 while ({ 
 index = index + 1;
 if (index > maxIndex, { index = 0 });
 index != startIndex 
 }, {
 view = views.at(index);
 if (view.canFocus, {
 this.focus_(view);
 ^this
 })
 })
 }
 focusPrev {
 var index, startIndex, view;
 
 if (views.size == 0, { ^this });
 startIndex = views.indexOf(focus);
 if (startIndex.isNil, { startIndex = views.size });
 index = startIndex;
 while ({ 
 index = index - 1;
 if (index < 0, { index = views.size - 1 });
 index != startIndex 
 },{
 view = views.at(index);
 if (view.canFocus, {
 this.focus_(view);
 ^this
 })
 })
 }
 removeSelected { 
 var list;
 list = views.select({ arg view; view.isSelected });
 list.do({ arg view; this.remove(view); });
 this.refresh;
 }
 duplicateSelected {
 var list, boundingRect, gap = 4, xoff=0, yoff=0;
 list = views.select({ arg view; view.isSelected });
 if (list.size > 0, {
 boundingRect = list.at(0).bounds;
 list.do({ arg view; 
 boundingRect = boundingRect.union(view.bounds);
 view.setSelected(false)
 });
 if (boundingRect.width > boundingRect.height, {
 yoff = boundingRect.height + gap;
 list.do({ arg view; 
 view.duplicateOffsetBy(xoff, yoff) 
 });
 },{
 xoff = boundingRect.width + gap;
 list.do({ arg view; 
 view.duplicateOffsetBy(xoff, yoff) 
 });
 });
 });
 this.refresh;
 }
 
 
 checkNotClosed {
 if (views.isNil, {
 "GUIWindow: Window has closed.\n".post;
 ^false
 },{
 ^true
 });
 }
 
 generateCode {
 generatedCode = String.streamContents({ arg stream; 
 stream << "\tvar w;\n\tw = " <<< this << ";\n" ;
 this.generateViewsCode(stream); 
 stream.nl;
 })
 ^generatedCode
 }
 storeModifiersOn { arg stream;
 this.storeColorsOn(stream);
 }
 storeParamsOn { arg stream;
 stream << "(" <<<* [ this.name, this.bounds ] << ")" ;
 }
 generateViewsCode { arg stream;
 views.do({ arg view;
 stream << "\t" <<< view << ";\n" ;
 });
 }
 storeColorsOn { arg stream;
 var test;
 if (this.backColor != Color.guiGrey, {
 stream << "\n\t\t.backColor_(" <<< this.backColor << ")" ;
 });
 }
 
  prInit { arg argName, argBounds;
 _GUIWindow_Init
 ^this.primitiveFailed
 }
 prClose {
 _GUIWindow_Close
 ^this.primitiveFailed
 }
 prSetName { arg argName;
 _GUIWindow_SetName
 ^this.primitiveFailed
 }
 prSetBounds { arg argBounds;
 _GUIWindow_SetBounds
 ^this.primitiveFailed
 }
 prGetBounds { arg argBounds;
 _GUIWindow_GetBounds
 ^this.primitiveFailed
 }
 prRmvView { arg argView;
 _GUIWindow_RmvView
 ^this.primitiveFailed
 }
 prSetFocus { arg argView;
 _GUIWindow_SetFocus
 ^this.primitiveFailed
 }
 prSetColor { arg color, index;
 _GUIWindow_SetColor
 ^this.primitiveFailed
 } 
 prGetColor { arg index;
 _GUIWindow_GetColor
 ^this.primitiveFailed
 } 
}
SignalWindow : GUIWindow {
 var <signals, scroller;
 *new { arg name, bounds, signals, sampleRate;
 bounds = if (bounds.notNil, { bounds },{ Rect.new(40,60,600,475) });
 ^super.new(name, bounds).initSignalWindow(signals, bounds, sampleRate);
 }
 initSignalWindow { arg signal, bounds, argSampleRate;
 var view, bounds2, height, maxlength = 0;
 
 if (signal.isKindOf(Array), {
 signal.do({ arg item, i;
 maxlength = max(maxlength, item.size);
 })
 },{
 maxlength = signal.size;
 });
 bounds = bounds.moveTo(0,0).insetAll(8,8,24,24);
 bounds2 = Rect.new(bounds.left, bounds.top, bounds.right, bounds.top + 16);
 bounds.top = bounds.top + 24;
 scroller = SliderView.new(this, bounds2, nil, 0,0,maxlength).hElastic;
 
 if (signal.isKindOf(Array), {
 signals = signal;
 if (signal.size == 1, {
 signal = signal.at(0);
 view = SignalView.new(this, bounds, signal).hElastic.vElastic;
 view.zoom = ceil(signal.size / bounds.width);
 view.sampleRate = argSampleRate;
 },{
 height = (bounds.height - (4 * (signal.size - 1))) / signal.size;
 signal.do({ arg item, i;
 var top;
 top = bounds.top + ((height + 4) * i);
 bounds2 = Rect.new(bounds.left, top, bounds.right, top + height);
 view = SignalView.new(this, bounds2, item).hElastic;
 view.zoom = ceil(item.size / bounds2.width);
 view.sampleRate = argSampleRate;
 });
 });
 },{
 signals = Array.with(signal);
 view = SignalView.new(this, bounds, signal).hElastic.vElastic;
 view.zoom = ceil(signal.size / bounds.width);
 view.sampleRate = argSampleRate;
 });
 scroller.action = {
 views.do({ arg item; 
 if (item.isKindOf(SignalView), {
 item.scroll = scroller.value; 
 });
 }) 
 };
 views.at(1).focus;
 this.refresh;
 }
 scrollSignals {
 views.do({ arg item; 
 if (item.isKindOf(SignalView), {
 item.scroll = scroller.value; 
 });
 }) 
 }
}
ScaleTablePanel : GUIWindow { 
 var <tableWindow, <wavetable; 
 var currentMinBox, currentMaxBox, 
 newMinBox, newMaxBox, scaleRangeBtn, 
 flipBtn, bipolarBtn, uniPolarBtn, 
 scaleByBtn, scalerBox, 
 offsetBtn, offsetBox, 
 scaleAndOffsetBtn; 
 
 var tableSig, scaler, offset, currentRange, currentMin, currentMax; 
 *new { arg wavetable, tableWindow; 
 var bounds; 
 if (tableWindow.isNil, { bounds = Rect.newBy(360, 60, 344, 140) }, 
 { bounds = Rect.newBy(
 tableWindow.bounds.left, 
 tableWindow.bounds.top + tableWindow.bounds.height + 24, 
 344, 140);
 }
 );
 ^super.new("Edit : " ++ tableWindow.name, bounds)
 .wavetable_(wavetable).tableWindow_(tableWindow).init;
 }
 
 close {  if (Synth.isOn and: this.closeable, 
 { this.closeable_(false); thisSynth.sched(0.2, { this.closeable_(true); }); }, 
 { wavetable.removeDependant(this); }
 );
 ^super.close;
 }
 
 wavetable_ { arg aWavetable;
 if (aWavetable.isKindOf(Wavetable), {
 wavetable.removeDependant(this);
 wavetable = aWavetable;
 wavetable.addDependant(this);
 });
 this.update;
 }
 
 tableWindow_ { arg aWindow; tableWindow = aWindow; }
 update { 
 tableSig = wavetable.asSignal; 
 currentMin = tableSig.minItem;
 currentMax = tableSig.maxItem; 
 this.syncCurrentBoxes;
 }
 
 syncCurrentBoxes { 
 if (currentMinBox.notNil and: currentMin.notNil, { currentMinBox.label = currentMin });
 if (currentMaxBox.notNil and: currentMax.notNil, { currentMaxBox.label = currentMax });
 }
 
 init { 
 
 var left = 3, top = 3, height, width;  
 var backC, stringC, buttonC; 
 backC = Color.guiGrey;
 stringC = rgb(208, 208, 208);
 buttonC = rgb(224, 224, 224);
 StringView.new( this, Rect.newBy(left, top, 164, 18), "Min | Current Range | Max")
 .prSetBorderStyle(4);
 currentMinBox = StringView.new( this, Rect.newBy(left, top + 22, 64, 18), 
 currentMin.asString).backColor_(Color.white);
 currentMaxBox = StringView.new( this, Rect.newBy(left + 64 + 8 + 28, top + 22, 64, 18),
 currentMax.asString)
 .backColor_(Color.white); 
 
 StringView.new( this, Rect.newBy(left, top + 49, 164, 18), "Min | Set New Range | Max")
 .prSetBorderStyle(4);
 newMinBox = NumericalView.new( this, Rect.newBy(left, top + 71, 64, 18), "", currentMin, -20000, 20000, 0, 'linear');
 newMaxBox = NumericalView.new( this, Rect.newBy(left + 64 + 8 + 28, top + 71, 64, 18), "", currentMax, -20000, 20000, 0, 'linear');
 scaleRangeBtn = ButtonView.new( this, Rect.newBy(left, top + 49 + 44, 164, 18), "Scale To New Range Now")
 .action_({ this.scaleToRange(newMinBox.value, newMaxBox.value); });
 
 
 left = left + 175;
 ButtonView.new( this, Rect.newBy(left, top, 160, 18), "Flip Within Current Range")
 .action_({ this.flipWithinRange }); 
 
 ButtonView.new( this, Rect.newBy(left, top + 22, 76, 18), "-> [-1, 1]")
 .action_({ this.biPolarize }); 
 ButtonView.new( this, Rect.newBy(left + 84, top + 22, 76, 18), "-> [0, 1]")
 .action_({ this.uniPolarize }); 
 scaleByBtn = ButtonView.new( this, Rect.newBy(left, top + 49, 76, 18), "Scale By:")
 .action_({ this.scaleAndOffsetTable(scalerBox.value, 0); });
 scalerBox = NumericalView.new( this, Rect.newBy(left + 84, top + 49, 76, 18), "", 1, -20000, 20000, 0.0, 'linear');
 
 offsetBtn = ButtonView.new( this, Rect.newBy(left, top + 49 + 22, 76, 18), "Add Offset:")
 .action_({ this.scaleAndOffsetTable(1, offsetBox.value); });
 offsetBox = NumericalView.new( this, Rect.newBy(left + 84, top + 49 + 22, 76, 18), "", 0, -20000, 20000, 0.0, 'linear');
 scaleAndOffsetBtn = ButtonView.new( this, Rect.newBy(left, top + 49 + 44, 160, 18), 
 "Scale and Offset Now")
 .action_({ this.scaleAndOffsetTable(scalerBox.value, offsetBox.value); });
 
 this.syncCurrentBoxes; 
 this.addShortCuts; 
 
 views.do({ arg each; var color;  if (each.isKindOf(StringView), { color = stringC });
 if (each.isKindOf(ButtonView), { color = buttonC });
 
 if (color.notNil, { each.backColor_(color); 
 each.labelColor_(Color.black); });
 
 })
 ^this;
 }
 
 addShortCuts { 
 var newHeight;
 ButtonView.new(this, Rect.newBy(120, 120, 100, 16), "View Shortcuts")
 .action_({ 
 newHeight = if (this.bounds.height > 210, 140, 240);
 this.bounds_(this.bounds.height_(newHeight)); 
 tableWindow.toFront;
 });
 
 [ "S Save", "L Load", "I Import", "D Default", "e openEditor",
 "c copy", "v paste", "V scalePaste", "m mixClipBd", "M MulClipBd", 
 "z undo/redo", "n normalize", "k clip neg.", "0 All Zero", "1 All One",
 "- minimize", "+ midiSize", "* maximize", "a basicRange", "A AutoRange"
 ].do({ arg eachName, i; 
 var left = 3, top = 140, width = 82, height = 16, offset = 3;
 
 StringView.new( this, 
 Rect.newBy( 
 (width + offset) * (i div: 5) + left, 
 (height + offset) * (i % 5) + top, 
 width, height), 
 eachName
 ).prSetBorderStyle(3);
 });
 }
 scaleAndOffsetTable { arg scaler = 1, offset = 0; 
 
 if (scaler == 1 and: (offset == 0), { ^this });  
 #currentMin, currentMax = ([ currentMin, currentMax ] * scaler + offset).sort; 
 
 currentMinBox.label = currentMin;
 currentMaxBox.label = currentMax;
 
 if (scaler != 1, { tableSig = tableSig * scaler });  if (offset != 0, { tableSig = tableSig + offset });
 
 wavetable.overWrite( tableSig.asWavetable, 0); 
 this.refresh; 
 }
 
 flipWithinRange { 
 var scaler = -1, offset; 
 
 offset = currentMin + currentMax; 
 
 this.scaleAndOffsetTable(scaler, offset);
 }
 
 uniPolarize { this.scaleToRange(0, 1) }
 biPolarize { this.scaleToRange(-1, 1) }
 
 scaleToRange { arg newMin, newMax;  var scaler, offset, oldRange; 
 
 oldRange = currentMax - currentMin; 
 if (oldRange == 0, { scaler = 0; offset = newMax + newMin * 0.5; }, 
 { scaler = (newMax - newMin) / oldRange; 
 offset = ([ currentMax, currentMin ] * scaler).minItem.neg + newMin; 
 }
 );
 this.scaleAndOffsetTable(scaler, offset); 
 }
}
TableEditor : ScaleTablePanel { 
 *new { arg wavetable, tableWindow; 
 var bounds; 
 if (tableWindow.isNil, { bounds = Rect.newBy(360, 60, 356, 160) }, 
 { bounds = Rect.newBy(
 tableWindow.bounds.left + tableWindow.bounds.width, 
 tableWindow.bounds.top, 
 356, 220);
 }
 );
 ^super.new("Scale Wavetable: " ++ tableWindow.name, bounds)
 .wavetable_(wavetable).init.addButtons;
 }
 
 addButtons { 
 
 }
}
MessageWindow : GUIWindow { 
 *new { arg message = "", name = "A Message:", left = 300, top = 300; 
 var w, width, strings, leftOffset = 8; 
 if (message.isKindOf(String), { strings = [ message ] }, { strings = message }); 
 
 w = super.new(name, Rect.newBy(left, top, 120, (strings.size * 20 + 25).max(90))
 ).backColor_(Color.guiGrey); 
 w.addColumn(strings, 8); 
 ^w;
 }
 
 addColumn { arg collOfStrings, leftOffset; var width;
 leftOffset = if (leftOffset.notNil, leftOffset,  { 16 + (this.views.last.bounds.width) }); 
 width = collOfStrings.collect(
 { arg each; each.size }
 ).maxItem + 2 * 6;  
 this.bounds_( this.bounds.width_(width + leftOffset + 8) ); 
 collOfStrings.do({ arg each, i; 
 if (each.notNil, 
 { 
 StringView.new(this, Rect.newBy(leftOffset, i * 20 + 10, width, 18), each)
 .prSetBorderStyle(4)
 .backColor_(PG.white); 
 }
 );
 });
 ^this;
 }
}

PGInfoWindow : MessageWindow { 
 classvar <pgInfo = #[ 
 "© 2001 The Regents of the University of California.",
 "Written by Alberto de Campo and Curtis Roads,",
 "CREATE, University of California Santa Barbara.",
 "For more on PulsarGenerator see www.create.ucsb.edu.", 
 nil,
 "E-mail contact:",
 "Alberto de Campo: alberto@create.ucsb.edu.",
 "Curtis Roads: clang@create.ucsb.edu.",
 nil,
 "Made with James McCartney's SuperCollider language;", 
 "for more on SuperCollider 2 see www.audiosynth.com."
 ]; 
 classvar origBounds, uniqueW; 
 
 *new { arg name = 'PG Info';
 
 if (uniqueW.notNil,  
 { ^uniqueW.show.bounds_(this.origBounds).toFront; }, 
 { 
 ^uniqueW = super.new(
 [ "Pulsar Generator Version " ++ PG.versionNumber ++", March 2001." ] 
 ++ pgInfo, 
 name: 'PG Info', 
 left: this.origBounds.left, 
 top: this.origBounds.top
 )
 .backColor_(PG.bAndKGreen); 
 }
 );
 } 
 *origBounds { 
 origBounds = 
 if ( origBounds.notNil, { origBounds }, 
 { Rect.newBy(260, 160, 352, 235); 
 });
 ^origBounds;
 }
 
 close { if (closeable, { uniqueW = nil; super.close; }, { this.hide }) }
}
PGShortcutsWindow : MessageWindow {  classvar origBounds, uniqueW;
 
 classvar generalShortcuts = #[ 
  "COMMAND-R : START SYNTH", 
 "Command-. : Stop Synth", 
 nil,  "IN ANY WINDOW:",
 "Tab : next controller",
 "Shift-Tab : previous controller",
 "Command-\\ : Send window to back", 
 nil,
 "NUMBER BOXES/SLIDERS:",
 "arrow up/right: increment",
 "arrow down/left: decrement",
 "x : jump to maximum", 
 "n : jump to minimum",
 "r : jump to random value",
 " ",
 "NUMBER BOXES:",
 "type number, hit enter",
 "click and drag up : increment",
 "click and drag down : decrement",
 " ",
 "SLIDERS:", 
 "Type <0-9> jumps to that value", 
 nil, 
 "CHECKBOXES:",
 "n or 0 : off, x or 1: on",
 "return : toggle",
 nil, 
 "BUTTONS:",
 "return : repeat button action", 
 nil,
 "CYCLEBAR:", 
 "f : freeze/unfreeze"
 ],
 
 tableShortcuts = #[
 "IN WAVETABLE VIEWS:", 
 
 "To Begin Drawing :",
 "Command-click : straight line(s)", 
 "Option-click : exponential line(s)", 
 "Control-click : cosine line(s)", 
 "To End Drawing: double-click",
 " ",
 "S : save wavetable (.wt) file", 
 "L : load wavetable (.wt) file", 
 "I : import soundfile",
 "D : Default wavetable", 
 "E : open Editor Window",
 " ",
 "EDITING:", 
 "c : copy", 
 "v : paste", 
 "V : scale to peak, then paste",
 "k : clip negative", 
 "m : mix with clipboard", 
 "M : multiply with clipboard", 
 "n : normalize", 
 "0 / 1 : set entire table to 0 / 1", 
 " ",
 "DISPLAY:",
 "+ : resize window to cycle bar", 
 "- : back to original window size",
 "* : maximize window",
 " ",
 "a : scale view to default range",
 "shift-A : AutoScale view to range",
 "arrow up: increase view range",
 "arrow down: decrease view range"
 ];
 *origBounds { 
 origBounds = 
 if ( origBounds.notNil, 
 { origBounds }, 
 { Rect.newBy(224, 75, 456, 575); }
 );
 ^origBounds;
 }
 
 *new { arg name = 'Shortcuts';
 var height, w; 
 
 if (uniqueW.notNil, 
 { ^uniqueW.bounds_(this.origBounds)
 .show
 .toFront; 
 }, 
 { 
 uniqueW = super.new(generalShortcuts, 'Shortcuts', 
 this.origBounds.left, 
 this.origBounds.top)
 .backColor_(PG.bAndKGreen); 
 
 uniqueW.addColumn(tableShortcuts); 
 origBounds = uniqueW.bounds;
 uniqueW;
 
 ^uniqueW;
 }
 )
 } 
 *libPut { arg name = 'Shortcuts'; 
 Library.put([name], 
 { this.new }
 );
 }
 close { if (closeable, { uniqueW = nil; super.close; }, { this.hide }) }
}
PrefsPanel : GUIWindow { 
 
 var <folderView, <fileNameView, <numChansBox;
 *new { 
 ^super.new("PG Preferences", Rect.newBy(160, 80, 320, 144)).init; 
 }
 
 init { 
 this.backColor = PG.bAndKGreen; 
 this.onClose_({ PG.savePrefs });  

 StringView.new( this, Rect.newBy(8, 36, 128, 20), "Record Preferences:")
 .prSetBorderStyle(4).backColor_(PG.bAndKGreenLight);
 StringView.new( this, Rect.newBy(160, 36, 56, 20), "Folder:")
 .prSetBorderStyle(4).backColor_(PG.bAndKGreenLight);
 folderView = ButtonView.new( this, Rect.newBy(8, 60, 304, 20), PG.recFolder)
 .backColor_(PG.bAndKGreenLight)
 .action_(
 { arg btn; var shortPath; 
 PutFileDialog.new(
 "Save recordings in this folder:", 
 " ", { arg ok, path; 
 if (ok, { 
 shortPath = PathName(path).pathOnly; 
 PG.recFolder = shortPath;
 
 if (shortPath.size > 49, 
 { shortPath = shortPath.copyRange(0, 21) ++ "..." 
 ++ shortPath.copyRange(
 shortPath.size - 22, 
 shortPath.size - 1);
 }
 );
 btn.label = shortPath; 
 }
 );
 }
 );
 }
 );
 StringView.new( this, Rect.newBy(8, 84, 128, 20), "Default Filename:")
 .prSetBorderStyle(4).backColor_(PG.bAndKGreenLight);
 ButtonView.new( this, Rect.newBy(141, 84, 128, 20), PG.recFileName)
 .backColor_(PG.bAndKGreenLight)
 .action_({ arg btn; 
 GetStringDialog.new("Please enter rec. filename:", 
 "PulsarGenRec", 
 { arg ok, newFileName; 
 if (ok, { btn.label = newFileName; 
 PG.recFileName = newFileName;
 }
 );
 }
 );
 });
 StringView.new( this, Rect.newBy(8, 108, 128, 20), "Soundfile Format:")
 .prSetBorderStyle(4).backColor_(PG.bAndKGreenLight); 
 
 ButtonView.new( this, Rect.newBy(141, 108, 128, 20), 
 SoundFileXT.possibleRecFormats.detect({ arg each; 
 (each.at(0).asSymbol == PG.headerFormat.asSymbol)
 and: 
 (each.at(1).asSymbol == PG.sampleFormat.asSymbol) 
 }).at(2)
 )
 .backColor_(PG.bAndKGreenLight)
 .action_({ arg formatbtn; 
 var w; 
 w = ChoiceWindow.new(
 "Pick a soundfile format:", 
 buttonsArray: 
 SoundFileXT.possibleRecFormats.collect(
 { arg each; 
 var head, bits, label; 
 #head, bits, label = each; 
 [ label, 
 { arg btn; 
 btn.window.views.do({ arg v; 
 if (v.backColor != PG.white, 
 { v.backColor_(PG.white) }
 );
 });
 btn.backColor_(PG.onYellow); 
 PG.headerFormat = head; 
 PG.sampleFormat = bits; 
 formatbtn.label = label;
 }
 ];
 }
 )
 ).name_("RecordFile Formats"); 
 
  });
 
 }
}
MixerWindow : GUIWindow {   
 var <startlevels, bounds, <diskInDisplay, <filePlayer, <outSliders;
 var <knobOnColor = rgb(250, 250, 144), 
 <knobOffColor = rgb(224, 224, 224), 
 <stringColor = rgb(224, 224, 224),
 <buttonColor = rgb(224, 224, 224);
 *new { arg name = "Output Effects", bounds, fxSpecs;
 bounds = bounds ? Rect.newBy(20, 640, 168, 120); 
 
 ^super.new(name, bounds).init(fxSpecs); 
 } 
 
 knobOnColor_ { arg color; knobOnColor = color; this.updateKnobColors; }
 knobOffColor_ { arg color; knobOffColor = color; this.updateKnobColors; }
 
 updateKnobColors { 
 outSliders.do({ arg eachSlider; 
 eachSlider.doAction; 
 }); 
 } 
 
 stringColor_ { arg color; 
 stringColor = color;
 this.views.do({ arg view; 
 if (view.isKindOf(StringView), { view.backColor_(color) });
 });
 }
 
 buttonColor_ { arg color;
 buttonColor = color;
 this.views.do({ arg view; 
 if (view.isKindOf(ButtonView), { view.backColor_(color) });
 });
 }
 
 init { arg fxSpecs; 
 this.addFilePlayer; 
 if (fxSpecs.notNil, { this.addSliders(fxSpecs); });
 }
 
 addFilePlayer { 
 filePlayer = FilePlayer.new;
 
 ButtonView.new( this, Rect.newBy(5, 5, 158, 16), 
 "Pick soundfile to loop")
 .action_({ this.chooseDiskInFile; })
 .backColor_(stringColor); 
 
 diskInDisplay = StringView.new( this, Rect.newBy(5, 25, 158, 16), 
 " ")
 .backColor_(stringColor); 
 
 }
 
 addSliders { arg fxSpecs; 
 
 outSliders = fxSpecs.collect({ arg eachSpec, i; 
 var color, label, startLevel, maxLevel; 
 #label, startLevel, maxLevel = eachSpec; 
 startLevel = startLevel ? 0; 
 maxLevel = maxLevel ? 1;
 
 StringView.new( this, Rect.newBy(5, 20 * i + 50, 52, 15), label)
 .prSetBorderStyle(4)
 .backColor_(stringColor); 
 color = if (startLevel > 0, { knobOnColor }, { stringColor });
 
 SliderView.new( this, Rect.newBy(62, 20 * i + 50, 96, 15), 
 nil, startLevel * 10, 0, maxLevel * 10, 0, 'linear')
 .backColor_(stringColor)
 .knobColor_(color)
 .action_({ arg sl; 
 if (sl.value == 0, 
 { sl.knobColor_(stringColor) },
 { sl.knobColor_(knobOnColor) }
 )
 });
 }
 ); 
 }
 
 noSoundFile { 
 diskInDisplay.label = "No File. RingMod is off.";
 this.fileIsOff;
 }
 fileIsOn { 
 diskInDisplay.backColor = knobOnColor;
 }
 fileIsOff { 
 diskInDisplay.backColor = stringColor;
 }
 chooseDiskInFile { 
 filePlayer.getFile({ arg ok, path; 
 if (ok, { diskInDisplay.label = PathName(path).fileName; }); 
 });
 ^this;
 }
 hasFile { 
 ^filePlayer.hasFile;
 }
}
TablesPanel : GUIWindow { 
 var <>currentBank, fadeLight, <fadeTimeBox, fadeTimeSlider, <setSwitches, <previousButton; 
 
 *new { arg name, bounds, currentBank; 
 ^super.new(
 name ? "Tables Bank", 
 bounds ? PG.allWindowBounds.at('tablesPanel')
 ).init(currentBank); 
 }
 init { arg bank; 
 currentBank = bank;
 this.backColor_(PG.bAndKGreen);
 this.addButtons; 
 this.addFadeViews;
 this.addTableSetSwitches; 
 this.addCheckBoxes;
  }
 
 prSetBounds { arg argBounds;  super.prSetBounds(argBounds);
 setSwitches.do({ arg each; 
 if (each.label.size > 1, { each.bounds_( each.bounds.width_(115) ) });
 });
 }
 addButtons { 
 [
 [ "Load Bank ...", { currentBank.loadBankSafely; } ],  [ "Save Bank ...", { currentBank.saveBankDialog; } ],  [ "Load Tables ...", { currentBank.loadTablesDialog } ], 
 [ "Save Tables ...", { currentBank.saveTablesWDialog } ],
 [ "Current To Panel", { currentBank.addCurrentSet } ],
 [ "Remove Tables ...", { currentBank.openRemoveWindow; } ],
 [ "Rename Tables ...", { currentBank.openRenameWindow; } ],
 [ "Default Tables ", { currentBank.revertToDefaultTables } ]
 ].collect(
 { arg labelAndAction, index; 
 var label, action, bounds; 
 #label, action = labelAndAction;
 
 bounds = Rect.newBy( index % 2 * 120 + 8, index div: 2 * 22 + 4, 115, 19 );
 
 ButtonView.new( this, bounds, label)
 .backColor_(PG.bAndKGreenLight)
 .action_(action)
 }
 );
 }
 
 addFadeViews {
 var left = 8, top = 100; 
 
 fadeLight = StringView.new( this, Rect.newBy(left, top, 68, 20), 
 "Fade Time:").backColor_(PG.bAndKGreenLight).prSetBorderStyle(4);
 
 fadeTimeBox = NumericalView.new( this, Rect.newBy(left + 76, top, 39, 20 ), 
 "fadeTimeBox", 2, 0, 30, 0.05, 'exponential');
 
 fadeTimeSlider = SliderView.new( this, Rect.newBy(left + 120, top, 115, 20 ), 
 "fadeTimeSl", 2, 0.1, 30, 0.0, 'exponential'); 
 
 fadeTimeBox.action = { fadeTimeSlider.value = fadeTimeBox.value; };
 fadeTimeSlider.action = { fadeTimeBox.value = fadeTimeSlider.value; };
 
 }
 
 xFadeOn { fadeLight.backColor_(PG.onYellow).label_("Fading...").labelColor_(PG.black); }
 xFadeOff { fadeLight.backColor_(PG.bAndKGreenLight).label_("Fade Time:").labelColor_(PG.black); }
 xFadeRefused { fadeLight.backColor_(PG.recRed).label_("Wait...").labelColor_(PG.onYellow); 
 this.restorePrevButton;
 } 
 
 newCurrentSet { arg setKey; 
 var testLabel, whichButton; 
 testLabel = setKey.asString;
 
 setSwitches.select({ arg btn; btn.value == 1; })
 .do({ arg btn; btn.value = 0; }); 
 
 whichButton = setSwitches.detect({ arg btn; btn.label == testLabel; }); 
 if (whichButton.notNil, { whichButton.value = 1; }); 
 }
 
 restorePrevButton { 
 setSwitches.select({ arg btn; btn.value == 1; })
 .do({ arg btn; btn.value = 0; }); 
 
 if ( previousButton.notNil, { previousButton.value = 1; } );
 } 
 addTableSetSwitches {
 var top = 130;
 setSwitches = Array.fill(16, 
 { arg index; 
 var button;
 button = RadioButtonView.new( 
 this, 
 Rect.newBy( index div: 8 * 120 + 8, index % 8 * 20 + top, 0, 17 ),   " "
 )
 .backColor_(PG.bAndKGreenLight)
 .keyDownAction_(
 { arg asciiChar; button.value_(0); 
 if (asciiChar == $z, { currentBank.restorePreviousSet; });
 }
 );
 }
 );
 
 }
 
 updateSetSwitches {  currentBank.keys.do({ arg eachSetName;
 if ([ 'currentTables', 'defaultTables' ].includes(eachSetName).not,
 { this.addName(eachSetName); }
 );
 }); 
 }
 
 setSwitchTo { arg label, newValue;  var whichSwitch;
 whichSwitch = setSwitches.detect({ arg each; each.label == label }); 
 if (whichSwitch.notNil, { whichSwitch.value_(newValue) });
 }
 
 addName { arg setName, initValue = 0;
 var whichButton; 
 
  whichButton = setSwitches.detect({ arg each; setName.asString == each.label }); 
  whichButton = whichButton ? setSwitches.detect({ arg each; each.label == " " }); 
 
 if (whichButton.isNil, { MessageWindow("No space on panel? Should never happen!"); ^this; });
 
 whichButton.label_(setName.asString); 
 whichButton.bounds_(whichButton.bounds.width_(116)); 
 whichButton.followTop.followLeft;
 
 if (initValue == 1.0, { 
 setSwitches.do({ arg sw; if (sw.value != 0, { sw.value_(0).doAction; }); });
 whichButton.value_(1); 
 });
 whichButton.action_({ 
 if (whichButton.value == 1, 
 { currentBank.makeCurrent(setName); }, 
 { previousButton = whichButton }  ); 
 }); 
 }
 
 addCheckBoxes { 
 var left = 8, top = 292; 
 ButtonView.new( this, 
 Rect.newBy(
 this.bounds.width - 16, 
 top - 34, 14, 14), "v")
 .followBottom
 .followRight
 .action_({ var height, defHeight; 
 height = this.bounds.height; 
 defHeight = PG.allWindowBounds.at(\tablesPanel).height;
 height = if (height > defHeight, { defHeight }, { defHeight + 40 }); 
 this.bounds = this.bounds.height_(height); 
 setSwitches.do({ arg sw; 
 if (sw.label.size > 1, { sw.bounds = sw.bounds.width_(115) });
 });
 });
 CheckBoxView.new( this, Rect.newBy(left, top, 200, 16), "Link PlayControls to Tables")
 .prSetBorderStyle(5)
 .backColor_(PG.bAndKGreenLight)
 .value_(1)
 .action_({ arg btn; 
 if (btn.value == 1, 
 { PG.linkSettings; }, 
 { PG.unLinkSettings; });
 });
 CheckBoxView.new( this, Rect.newBy(left, top + 20, 200, 16), "Always Save Tables as .set")
 .prSetBorderStyle(5)
 .backColor_(PG.bAndKGreenLight)
 .value_(1)
 .action_({ arg btn; 
 if (btn.value == 1, 
 { currentBank.saveTablesAsSet; }, 
 { currentBank.saveIndividualTables });
 });
 }
 removeName { arg name; 
 var whichButton, nameString; 
 nameString = name.asString;
 
 whichButton = setSwitches.detect({ arg eachV; eachV.label == nameString });
 
 if ( whichButton.notNil,   { whichButton.value(0);
 whichButton.label_(" ").action_(nil);
 whichButton.bounds_(whichButton.bounds.width_(0));
 whichButton.followTop.followLeft;
 this.refresh;
 }
 );
 }
}
PGPrefsPanel : GUIWindow { 
 
 var <folderView, <fileNameView, <numChansBox, backupPrefs, fileFormat;
 *new { 
 ^super.new("PG Preferences", Rect.newBy(254, 121, 238, 222)).init; 
 }
 
 init {  
 var backupPrefs;
 backupPrefs = PG.getPrefs;
 
 this.backColor = PG.bAndKGreen; 
 this.onClose_({ PG.savePrefs });  
 StringView.new( this, Rect.newBy(5, 5, 150, 20), "Number of Channels: 2")
 .prSetBorderStyle(4).backColor_(PG.bAndKGreenLight).backColor_(PG.guiGrey);
 StringView.new( this, Rect.newBy(5, 30, 188, 20), "Load Limit: max. fundFreq ...")
 .prSetBorderStyle(4).backColor_(PG.bAndKGreenLight);
 
 [ "for 1 train", "2 trains  ", "3 trains  " ].do({ arg eachLabel, i; 
 StringView.new( this, Rect.newBy(5, i * 25 + 53, 75, 18), eachLabel)
 .prSetBorderStyle(4).backColor_(PG.bAndKGreenLight); 
 NumericalView.new( this, Rect.newBy(5 + 80, i * 25 + 53, 40, 18), eachLabel, 
 PG.maxFundFreqs.at(i + 1), 200, 10000, 1)
 .action_({ arg box; PG.setMaxFundFreq(i + 1, box.value); });
 });
 
 
 StringView.new( this, Rect.newBy(5, 130, 140, 20), "Recording Preferences")
 .prSetBorderStyle(4);
 
 StringView.new( this, Rect.newBy(5, 156, 82, 18), "Folder:")
 .prSetBorderStyle(4).backColor_(rgb(230,255,230));
 ButtonView.new( this, Rect.newBy(96, 156, 118, 18), PG.recFolder)
 .backColor_(rgb(230,255,230))
 .action_({ arg btn; this.pickRecFolder(btn); });
 
 StringView.new( this, Rect.newBy(5, 176, 82, 18), "File Name:")
 .prSetBorderStyle(4).backColor_(rgb(230,255,230));
 ButtonView.new( this, Rect.newBy(96, 176, 118, 18), PG.recFileName)
 .backColor_(rgb(230,255,230))
 .action_({ arg btn; this.setRecFileName(btn); });
 
 
 fileFormat = SoundFileXT.possibleRecFormats.detect({ arg each; 
 (each.at(0).asSymbol == PG.headerFormat.asSymbol)
 and: 
 (each.at(1).asSymbol == PG.sampleFormat.asSymbol) 
 }).at(2);
 
 StringView.new( this, Rect.newBy(5, 196, 82, 18), "File Format: ")
 .prSetBorderStyle(4).backColor_(rgb(230,255,230));
 ButtonView.new( this, Rect.newBy(96, 196, 118, 18), fileFormat)
 .backColor_(rgb(230,255,230))
 .action_({ arg btn; this.setRecFormat(btn); });
 }
 
 pickRecFolder { arg btn; var shortPath; 
 PutFileDialog.new(
 "Save recordings in this folder:", 
 " ", { arg ok, path; 
 if (ok, { 
 shortPath = PathName(path).pathOnly; 
 PG.recFolder = shortPath;
 
 if (shortPath.size > 49, 
 { shortPath = shortPath.copyRange(0, 21) ++ "..." 
 ++ shortPath.copyRange(
 shortPath.size - 22, 
 shortPath.size - 1);
 }
 );
 btn.label = shortPath; 
 }
 );
 }
 );
 }
 
 setRecFileName { arg btn; 
 GetStringDialog.new("Please enter rec. filename:", 
 "PulsarGenRec", 
 { arg ok, newFileName; 
 if (ok, { btn.label = newFileName; 
 PG.recFileName = newFileName;
 }
 );
 }
 );
 } 
 
 setRecFormat { arg formatbtn; 
 var w; 
 w = ChoiceWindow.new(
 "Pick a soundfile format:", 
 buttonsArray: 
 SoundFileXT.possibleRecFormats.collect(
 { arg each; 
 var head, bits, label; 
 #head, bits, label = each; 
 [ label, 
 { arg btn; 
 btn.window.views.do({ arg v; 
 if (v.backColor != PG.white, 
 { v.backColor_(PG.white) }
 );
 });
 btn.backColor_(PG.onYellow); 
 PG.headerFormat = head; 
 PG.sampleFormat = bits; 
 formatbtn.label = label;
 }
 ];
 }
 )
 ).name_("RecordFile Formats"); 
 
 "formatbtn.label = ".postc; formatbtn.label.postln;
 
  SoundFileXT.possibleRecFormats.do({ arg eachFormat, i; 
 var head, bits, label; 
 #head, bits, label = eachFormat; 
 if ( label == formatbtn.label, 
 { [ eachFormat, formatbtn.label, i ].postln; 
 w.views.detect({ arg each; each.label == formatbtn.label }).backColor_(PG.onYellow); 
 });
 });
 }
}
      
ScopeWindow : GUIWindow {
 var <scopeViews, <numChannels, <>onHide, <>onShow;
 *new { arg name = "Scope", bounds, numChannels = 2;
 
 bounds = if (bounds.notNil, bounds, { Rect.newBy(726, 60, 256, 264) });
 ^super.new(name, bounds).init(numChannels);
 }
 
 init { arg numChannels;     
  
 var top = 4, left = 4, width = 248, offset = 4, height = 120, fullHeight = 264; 
 var numSamples; 
 width = this.bounds.width - 8;
 numSamples = 0.1 * Synth.sampleRate;
 height = this.bounds.height - 16 - (numChannels * 4) / numChannels;
 
 scopeViews = Array.fill( numChannels, 
 { arg i; 
 ScopeView.new( this, 
 Rect.newBy(left, top + (height + offset * i), width, height), 
 numSamples, -1, 1)
 .hElastic  }
 );
 }
 zoomAll { arg asciiChar; scopeViews.keyDown(asciiChar); }
 
 syncZoom { arg scopeView; 
  }
 prSetBounds { arg argBounds; var eachHeight; 
 
 super.prSetBounds(argBounds);
 
 eachHeight = argBounds.height - 24 / scopeViews.size; 
 
     
    
 scopeViews.do({ arg eachView, i; 
 eachView.bounds = Rect.newBy(8, 
 eachHeight * i + 8, 
 argBounds.width - 16, 
 eachHeight - 5); 
 });
 }
}TableWindow : GUIWindow {   
 var <>table, <>viewSpecs, <>buttonSpecs, <buttonColor = rgb(255,255, 255);
 var <tableView; 
 *new { arg table, name = "table", bounds, viewSpecs = #[ -1, 1, -20000, 20000, 0.1, 0 ],
 buttonSpecs;
 
 bounds = bounds ? Rect.newBy(320, 160, 320, 240); 
 
 ^super.new(name, bounds).table_(table).viewSpecs_(viewSpecs).buttonSpecs_(buttonSpecs).init; 
 }
 init { 
 var min, max, setMin, setMax, setStep, defaultVal;
 
 #min, max, setMin, setMax, setStep, defaultVal = viewSpecs;
 
 tableView = TableView.new( this, 
 Rect.newBy(8, 22, this.bounds.width - 28, this.bounds.height - 28), 
 table, viewSpecs )
 .hElastic.vElastic.focus.minval_(min).maxval_(max);
 
 ^this;
 }
 addButtons { arg buttonSpecs; 
 buttonSpecs.do({ arg eachLabelAndAction, i; 
 var label, action; 
 #label, action = eachLabelAndAction; 
  ButtonView.new( this, 
 Rect.newBy(this.bounds.width - 16, 18 * i, 16, 16), label)
 .followRight.backColor_(buttonColor) 
 .action_(action);
 });
 ^this; 
 }
 
 addFourierButton { arg left, top;
 var nextBoxBounds; 
 nextBoxBounds = tableView.maxSetBox.bounds;
 
 if (left.isNil, { left = nextBoxBounds.right + 4 });
 if (top.isNil, { top = nextBoxBounds.top });
 ButtonView.new( this, Rect.newBy(left, top, 35, nextBoxBounds.height), "Harm")
 .action_({ HarmonicsDialog(table) });
 }
 labelColor_ { arg color; 
 this.views.do({ arg view; 
 if (view.isKindOf(StringView), 
 { view.backColor_(color); }
 );
 })
 }
 
 close {  if (Synth.isOn and: this.closeable, 
 { this.closeable_(false); thisSynth.sched(0.2, { this.closeable_(true); }); }
 );
 ^super.close;
 }
}TableWindow2 : TableWindow { 
 init {  
 var min, max, setMin, setMax, setStep, defaultVal;
 
 #min, max, setMin, setMax, setStep, defaultVal = viewSpecs;
 
 tableView = TableView.new( this,  Rect.newBy(8, 22, this.bounds.width - 28, this.bounds.height - 44), 
 table, viewSpecs )
 .hElastic.vElastic.focus.minval_(min).maxval_(max);
 
 ^this;
 }
}

PGControlPanel : GUIWindow { 
 
 classvar current, engine;  
 var <muteButtons, <ampSliders;
 var <burstMaskSwitch, <burstOnBox, <burstOffBox;
 var <startButton, <recordButton, <loopButton, <scopeButton, <muteButton;
 var <trainDurBox, <>edgeAction, <freezeButton, <backwardsButton; 
 var <edgeBox, <edgeSlider, <turboSwitch, <cycleDisplay, <credits;
 var <masterVol, <fadeButton, <masterFadeTimeBox;
 
 *current { arg name, bounds;
 ^this.new(name, bounds);
 }
 *new { arg name, bounds; 
 if (current.isNil, 
 { bounds = bounds ? PG.allWindowBounds.at('controlPanel'); 
 name = "Pulsar Generator" ++ PG.versionNumber ++ if (PG.isDemo, " Demo", "");
 ^super.new(name, bounds).init }, 
 {  ^current }
 ); 
 }
 
 close {  current = nil; 
 ^super.close;
 }
 
 startButtonOff { 
 startButton.value_(0).label_("S T A R T").backColor_(PG.darkGreen).labelColor_(PG.white); 
 }
 startButtonOn { 
 startButton.value_(1).label_("S T O P").backColor_(PG.recRed).labelColor_(PG.white);
 }
 
 *defaultBounds { ^Rect.newBy( 196, 492, 520, 144 ) }  
 init {  
 current = this;
 
 this.backColor_(PG.bAndKGreen);
 this.makeSlidersAndButtons;
 this.makeBurstControls;
 this.makeMuteButton;
 this.makeStartLoopRecordButtons;
 this.makeTrainDurScopeTablesButtons;
 this.makeFreezeAndDirButtons;
 this.makeBottom;
 this.makeMasterVolFade;
 
 startButton.focus; 
 
  startButton.keyDownAction_({ arg asciiChar, keyCode, modifiers;
 if (asciiChar == $ , { startButton.action.value(startButton); });
 });
 }
 
 synthHasStopped { arg numPulsars;
 credits.label_("You have used " ++ numPulsars ++ " PulsarCredits."); 
 this.startButtonOff;
 
 cycleDisplay.value_(0).doAction; 
 recordButton.value_(0).doAction;  freezeButton.value_(1).doAction;   backwardsButton.value_(0).doAction; 
 
 }
  
 onOffColor { arg btn;  if (btn.value == 0, 
 { btn.backColor_(PG.bAndKGreenLight) }, 
 { btn.backColor_(PG.onYellow) }
 )
 } 
 
 updateCredits { arg numPulsars; 
 credits.label_("You have used " ++ numPulsars.asString ++ " Pulsar credits.")
 }
 
 simpleMode { arg flag = true; 
  }
  
 makeSlidersAndButtons { 
 #muteButtons, ampSliders = Array.fill(3, 
 { arg index; 
 
 StringView.new( this, 
 Rect.newBy(index * 176 + 30, 2, 56, 12), 
 "Volume " ++ (index + 1).asString); 
 
 [ CheckBoxView.new( this, 
 Rect.newBy(index * 176 + 90, 2, 45, 12), "ON", 1)
 .backColor_(PG.onYellow)
 .prSetBorderStyle(4)
 .action_({ arg btn;
 if (btn.value > 0, 
 { btn.backColor_(PG.onYellow); },
 { btn.backColor_(PG.bAndKGreenLight); }
 ); 
 PG.engine.setTrainFlag(index, btn.value);
 }),
 
 SliderView.new( this, Rect.newBy( index * 176 + 8, 18, 140, 20 ), 
 "amp", 3, 0, 10, 0.1, 'linear')
 .knobColor_( PG.onYellow )
 .action_({ arg eachSl; 
 if (eachSl.value == 0.0, 
 { eachSl.knobColor_(PG.bAndKGreenLight); 
 PG.engine.muteTrain(index); }, 
 { eachSl.knobColor_(PG.onYellow); 
 PG.engine.setTrainLevel(index, eachSl.value * 0.1);
 PG.engine.setTrainFlag(index, muteButtons.at(index).value) 
 }
 )
 });
 ]
 }
 ).flop; 
 ^this
 }
 makeBurstControls { 
 burstMaskSwitch = CheckBoxView.new( this, 
 Rect.newBy(8, 48, 140, 20), "   Burst Ratio:")
 .backColor_(PG.bAndKGreenLight)
 .action_({ arg btn; this.onOffColor(btn); this.sendBurstInfo });
 StringView.new( this, Rect.newBy(26, 70, 28, 20), "on:")
 .backColor_(PG.bAndKGreenLight);
 burstOnBox = NumericalView.new( this, Rect.newBy(56, 70, 28, 20), 
 "burstOnBox", 2, 1, 100, 1, 'linear')
 .backColor_(PG.bAndKGreenLight)
 .action_({ this.sendBurstInfo });
 StringView.new( this, Rect.newBy(86, 70, 32, 20), "off:")
 .backColor_(PG.bAndKGreenLight);
 burstOffBox = NumericalView.new( this, Rect.newBy(120, 70, 28, 20),
 "burstOnBox", 1, 0, 100, 1, 'linear')
 .backColor_(PG.bAndKGreenLight)
 .action_({ this.sendBurstInfo }); 
 } 
 
 sendBurstInfo {  PG.updateBurstLoop( burstMaskSwitch.value, burstOnBox.value, burstOffBox.value ); 
 }
 
 makeStartLoopRecordButtons {
 
 startButton = ButtonView.new( this, 
 Rect.newBy( 176 + 8, 48, 140, 20 ), "S T A R T")
 .backColor_(PG.darkGreen).labelColor_(Color.white)
 .action_({ arg btn; btn.toggle; if (startButton.value == 1, { PG.run }, { Synth.stop }) });
 
 loopButton = CheckBoxView.new( this, 
 Rect.newBy( 176 + 8, 72, 66, 20 ), "Loop", 1)
 .backColor_(PG.onYellow)
 .action_({ arg btn;
 if (btn.value == 1, 
 { btn.backColor_(PG.onYellow); PG.oscBank.loopOn }, 
 { btn.backColor_(PG.bAndKGreenLight); PG.oscBank.loopOff }
 )
 });
 
 recordButton = CheckBoxView.new( this, 
 Rect.newBy( 176 + 8 + 74, 72, 66, 20 ), "Record")
 .backColor_(PG.bAndKGreenLight)
 .action_({ arg btn; 
 if (btn.value == 1, 
 { btn.backColor_(PG.recRed).labelColor_(Color.white); }, 
 { btn.backColor_(PG.bAndKGreenLight).labelColor_(Color.black); }
 );
 });
 }
 makeMuteButton { 
 muteButton = CheckBoxView.new( this, Rect.newBy( 176 + 8 + 74 + 74, 72, 74, 20 ), "M U T E")
 .action_({ arg box; 
 if (box.value == 0, 
 { box.backColor_(PG.bAndKGreenLight).labelColor_(Color.black);
 PG.unmute; }, 
 { box.backColor_(PG.recRed).labelColor_(Color.white);
 PG.mute; }
 );
 });
 }
 
 makeTrainDurScopeTablesButtons {
  StringView.new( this, Rect.newBy(184 + 176, 48, 96, 20), "Train Duration:")
 .backColor_(PG.bAndKGreenLight);
 trainDurBox = NumericalView.new( this, Rect.newBy( 288 + 176, 48, 32, 20 ), 
 "trainDurBox", 10, 0.1, 100, 0.1, 'linear')
 .action_({ arg box; PG.oscBank.loopTime_(box.value); 
 freezeButton.value_(1).doAction;
 });
 
 scopeButton = ButtonView.new( this, 
 Rect.newBy( 176 * 2 + 8 + 74, 72, 66, 20 ), 
 "Scope"
 )
 .backColor_(PG.bAndKGreenLight)
 .action_(
 { arg btn; btn.toggle;
 if (btn.value == 1, 
 { PG.scopeWindow.show },
 { PG.scopeWindow.hide }
 );
 });
 
 ButtonView.new( this, 
 Rect.newBy( 176 * 2 + 8 + 74, 96, 66, 20 ),
 "Tables")
 .backColor_(PG.bAndKGreenLight)
 .action_({ arg btn; btn.toggle;
 if (btn.value == 1, 
 { PG.tablesPanel.show },
 { PG.tablesPanel.hide }
 );
 });
 }
 makeFreezeAndDirButtons { 
 
 freezeButton = ButtonView.new( this, Rect.newBy(404, 96, 24, 20), "[F]")
 .action_({ arg btn; 
 if (PG.oscBank.loopIsOn.not, { nil }, 
 { btn.toggle; 
 if (btn.value == 1, 
 { PG.oscBank.loopFreq_(0); 
 btn.backColor_(PG.onYellow)
 }, 
 { PG.oscBank.loopTime_(PG.controlPanel.trainDurBox.value); 
 btn.backColor_(PG.bAndKGreenLight);
 }
 );
 });
 });
 
 backwardsButton = ButtonView.new( this, Rect.newBy(384, 96, 18, 20), "<-")
 .backColor_(PG.bAndKGreenLight)
 .action_({ arg btn; 
 if (PG.oscBank.loopIsOn.not, { nil }, 
 { btn.toggle; 
 if (btn.value == 0, 
 { PG.oscBank.loopForward_(false); 
 btn.backColor_(PG.onYellow);
  }, 
 { PG.oscBank.loopForward_(true); 
 btn.backColor_(PG.bAndKGreenLight);
  } 
 )
 });
 });
 }
 
 sendEdge { arg newEdgeFactor; 
 PG.engine.setEdgeFactor(newEdgeFactor); 
 }
 
 makeBottom {
  credits = StringView.new(this, 
 Rect.newBy( 176 + 8 + 70 - 110, 100, 220, 18 )
 )
 .backColor_(PG.bAndKGreenLight);
 this.updateCredits(0);
 
 cycleDisplay = SliderView.new( this, 
 Rect.newBy(8, 126, 176 * 2 + 140, 12), 
 "cycleDisplay", 0, 0, 10, 0, 'linear')
 .hElastic
 .backColor_(PG.bAndKGreenLight)
 .knobColor_(PG.onYellow);
 
  StringView.new( this, Rect.newBy(8, 144, 36, 18), "Edge")  .backColor_(PG.bAndKGreenLight);
 
 edgeBox = NumericalView.new( this, Rect.newBy(48, 144, 28, 18), 
 "edgeBox", 5, 0, 10, 0.1, 'linear')
 ;
 
  edgeSlider = SliderView.new( this, 
 Rect.newBy(80, 144, 68, 18), "edge", 
 5, 0, 10, 0.1, 'linear'); 
 
 edgeBox.action = { arg box; edgeSlider.value = edgeBox.value; this.sendEdge(box.value); };
 edgeSlider.action = { arg slider; edgeBox.value = edgeSlider.value; this.sendEdge(slider.value); };
 
 
 ButtonView.new( this, 
 Rect.newBy( 2 * 176 + 8, 144, 140, 18 ), 
 "Randomize Controls"
 ).backColor_(PG.bAndKGreenLight)
 .action_({ PG.randomizeControls; });
 
  ButtonView.new( this, 
 Rect.newBy(506, 115, 14, 14), "v")
 .followBottom
 .followRight
 .action_({ var height; height = this.bounds.height; 
 height = if (height <= 144, { 168 }, { 144 }); 
 this.bounds = this.bounds.height_(height);
 });
 }
 
 makeMasterVolFade { 
 
 masterVol = SliderView.new( this, Rect.newBy(151, 144, 135, 20), "masterAmp", 1, 0, 3, 0.001, 'linear')
 .knobColor_(rgb(250,250,144)); 
 fadeButton = ButtonView.new( this, Rect.newBy(290, 144, 30, 20), "Fade").backColor_(rgb(230,255,230));
 masterFadeTimeBox = NumericalView.new( this, Rect.newBy(325, 144, 28, 20), "fadeTime", 5, 0, 100, 0.1);
 
 fadeButton.action_({ arg btn; 
 var repTime = 0.025, targetVol, fadeTime, numSteps; 
 var fadeStepFunc, currentVol, volStep; 
 
 if (Synth.isOn, { 
 currentVol = masterVol.value;
 targetVol = if (currentVol == 0, 1, 0); 
 fadeTime = masterFadeTimeBox.value;
 numSteps = (fadeTime / repTime).ceil; 
 volStep = targetVol - currentVol / numSteps;
 
 fadeStepFunc = { 
 if ( ( ( targetVol - currentVol ).sign == volStep.sign) 
 and: ( currentVol == masterVol.value ), 
 { PG.controlPanel.masterVol.value_(currentVol + volStep); 
 currentVol = masterVol.value;
 thisSynth.sched(repTime, { fadeStepFunc.value; }) 
 },
 {  }
 );
 }; 
 fadeStepFunc.value;
 })
 });
 }
} 

PG {
 
 classvar <>isDemo = false, <userMode = \full, <>canStart = true, <startupTime;
 classvar <versionNumber = " 1.0.01b-debug"; 
 classvar numPulsars = 0, credits;
  classvar <>recFileName, recFile, <>recFolder, 
 <numChannels = 2, <maxNumChannels = 16,
 <headerFormat = 'AIFF', <sampleFormat = '16 big endian signed',
 <maxFundFreqs = #[ 3000, 3000, 2400, 2000 ];
 classvar <>defaultPrefs = 
 #[ 2,  ":StartupTables.set",  "",  "PulsarGenRec",  ":Sounds:floating_1",  'AIFF',
 '16 big endian signed', 
 3000,  3000,  2400, 
 2000
 ];
 classvar <>prefs, noPrefs = true;
 classvar <defaultSettings;
 classvar <startupTableSet = ":StartupTables.set";
 classvar <>tableSetsToPreload; 
 classvar <demoTablesBank = 
 #[ ":DemoTables f:Beat_01.set",
 ":DemoTables f:Crazy.set",
 ":DemoTables f:Discrete notes.set",
 ":DemoTables f:Jumper.set",
 ":DemoTables f:Expresset.set",
 ":DemoTables f:Oddset_1.set",
 ":DemoTables f:PM_AM.set",
 ":DemoTables f:PulsarMaster.set",
 ":DemoTables f:Rhythm_01.set",
 ":DemoTables f:Set 99.set",
 ":DemoTables f:Squeeze.set",
 ":DemoTables f:Vela X-1.1.set",
 ":DemoTables f:Vela X-1.2.set"
 ];
 classvar <isMuted = false;
 
  classvar 
 <bAndKGreen = rgb(205, 250, 205),  <guiGrey = rgb(176, 176, 176), 
 <onYellow = rgb(250,250,144), 
 <bAndKGreenLight = rgb(230,255,230),  <darkGreen = rgb (43, 88, 43),
 <white = rgb(255,255,255),
 <recRed = rgb ( 159, 17, 21 ),
 <black = rgb(0,0,0);
 
 classvar <currentBank;
  classvar <settingsLib, <settingsAreLinked = true, settingsName = "PulsarGenSettings.sig";
  
 classvar <allWindowBounds, <allTableViewSpecs;  classvar <allTableViews, <allTableWindows;
 classvar <scopeWindow, <mixerWindow;  classvar <controlPanel, <tablesPanel; 
  
 classvar stackOfTasks, waitTimes; 
 classvar <oscBank, <engine, <filePlayer;  classvar <>diskInFileName, <>burstLoop; 
 classvar xFadeLocked = false;
 classvar fadePrepareTime, fadeTime;  
 *new { 
 inform("// PG is a class-only singleton. Just use PG.someMethod."); 
 ^PG 
 } 
 
 *mute { isMuted = true }
 *unmute { isMuted = false }
 
 *addDebugHelp { 
 Library.put(['debug', 'gcInfo'], { this.gcInfo });
 Library.put(['debug', ' PG.dump'], { PG.dump });
 Library.put(['debug', 'oscBank.dump '], { PG.oscBank.dump });
 Library.put(['debug', 'engine.dump'], { PG.engine.dump });
 Library.put(['debug', 'currentBank.dump'], { PG.currentBank.dump });
 } 
 *notYet { MessageWindow(["Sorry, not yet.", "To be implemented ASAP."]) }
  *init { 

 this.initLib; 
 this.initGlobalVars; 
 
 currentBank = TablesBank.new; 
 settingsLib = SettingsLib.new; 
 
 oscBank = OscXFadeBank.new;
 engine = PulsarEngine4.new;
 burstLoop = BurstLoop.ratio(2, 1); 
 this.initGUISpecs;
 this.makeGUI; 
 
 if (noPrefs, { 
 Copyright.openHelpFile; 
 this.savePrefs;   });
 
 settingsLib.setState(
 currentBank.at(\currentTables).at(\settings)
 );
 
 if (this.isDemo, {
 maxNumChannels = 2;
 controlPanel.recordButton.action_({ arg btn; 
 if (btn.value == 1, 
 { MessageWindow("Recording only works in the full version.") }
 )
 })
 });
 startupTime = Date.getDate.bootSeconds;
 Main.runAction = { PG.run };  }
 *initLib {
 Library.put( ['About PG'], { PGInfoWindow.new } );
 Library.put( ['Shortcuts'], { PGShortcutsWindow.new } );
 Library.put( ['User Mode', 'Basic'], { this.basicUserMode } ); 
 Library.put( ['User Mode', 'Full'], { this.fullUserMode } ); 
 
 [ What_is_Pulsar_Synthesis, TableViews, ControlPanel, File_Menu, Synth_Menu, 
 Fill_Menu, Alter_Menu, Other_Menus, Tables_Bank, FX_RingMod ].do({ arg eachClass; 
 Library.put( ['Help', eachClass.asSymbol ], { eachClass.openHelpFile } );
 });
 Library.put(['PG to Front'], { this.toFront });
 Library.put(['Preferences'], { PGPrefsPanel.new });
 Library.bar([ ]); 
 
 }
 
 *loadPulsaretAndEnvLib { 
   
   }
 
 *initGlobalVars {
 var prefsFile;
 prefsFile = this.readPrefs;  if (prefsFile.isNil, 
 { prefs = defaultPrefs; },  { noPrefs = false;
 prefs = prefsFile.text }
 ); 
 
 this.setPrefs(prefs);
 }
 
 *getPrefs { 
 ^[ numChannels,
 startupTableSet, 
 recFolder, 
 recFileName, 
 diskInFileName,
 headerFormat,
 sampleFormat
 ] ++ engine.maxFundFreqs
 } 
 *setPrefs { arg newPrefSettings;
 #numChannels, startupTableSet, 
 recFolder, recFileName, diskInFileName,
 headerFormat, sampleFormat = 
 newPrefSettings; 
 
 maxFundFreqs = newPrefSettings.copyRange(7, 10)  .collect({ arg each; 
 if (each.isKindOf(String), { StringToNumber(each); }, each) 
 });
 
 numChannels = 2;   }
 *savePrefs { 
 prefs = this.getPrefs; 
 TextFile.save("PulsarGenerator Preferences", prefs);
 }
 *readPrefs { 
 ^TextFile.read("PulsarGenerator Preferences");
 }
 *setMaxFundFreq { arg which, val; 
 engine.setMaxFundFreq(which, val);
 maxFundFreqs.put(which, val);
 }
  *initOscBank { 
 var initTables, initSet; 
 initSet = currentBank.at('currentTables');
 initTables = [ 
 'fundFreq', 'pulseMask',
 'formFreq1', 'pan1', 'amp1', 
 'formFreq2', 'pan2', 'amp2', 
 'formFreq3', 'pan3', 'amp3' 
 ].collect({ arg key; initSet.at(key); });
 
 oscBank.init(initTables, 10 ); 
 oscBank.cycleDisplay = controlPanel.cycleDisplay;
 oscBank.cycleDisplay.action_(
 { arg display; oscBank.jumpToPhase(display.value * 0.2pi); }
 );
 
 oscBank.cycleDisplay.keyDownAction_({ arg asciiChar; 
 if (asciiChar == $f, { controlPanel.freezeButton.doAction });
 });
 }
 
 
 *initEngine {
 engine.setPulsaretAndEnv(
 currentBank.at(\currentTables).at(\pulsaret),
 currentBank.at(\currentTables).at(\pulseEnv)
 );
 }
 
 *initGUISpecs {
 allWindowBounds = IdentityDictionary[
 \alignToCycle -> Rect.newBy(196, 60, 520, 408),
 \max -> Rect.newBy(8, 60, 1008, 676),
 'scopeWindow' -> Rect.newBy(726, 60, 256, 264),
 'mixerWindow' -> Rect.newBy(20, 640, 168, 105),
 \tWindowToMaxCycle -> Rect.newBy(8, 60, 1008, 678),
 \controlPanelToMaxCycle -> Rect.newBy(8, 620, 1008, 140), 
 'controlPanel' -> Rect.newBy( 196, 492, 520, 144 ),
 'tablesPanel' -> Rect.newBy(726, 348, 256, 288),
 'table clipboard' -> Rect.newBy(288, 144, 168, 120)
 ];
 [ 'fundFreq', 'formFreq1', 'formFreq2', 'formFreq3', 
 'pulseMask', 'pan1', 'pan2', 'pan3', 
 'pulsaret', 'amp1', 'amp2', 'amp3',
 'pulseEnv' ].do(
 { arg each, index; 
 var left = 20, top = 60, width = 168, height = 120, bounds; 
 
 bounds = Rect.newBy(
 index % 4 * (width + 8) + left, 
 index div: 4 * ( height + 24) + top, 
 width, 
 height);
 
 allWindowBounds.put(each, bounds); 
 }
 ); 
 
 allTableViewSpecs = IdentityDictionary[
   'fundFreq' -> [ 0, 200, 0, 2000, 10, 18 ], 
 'formFreq1' -> [ 0, 2000, 0, 20000, 10, 400], 
 'formFreq2' -> [ 0, 2000, 0, 20000, 10, 400 ], 
 'formFreq3' -> [ 0, 2000, 0, 20000, 10, 400 ], 
 'pulseMask' -> [ 0, 1, 0, 1, 0.1, 1 ], 
 'pan1' -> [ -1, 1, -1, 1, 0.1, 0], 
 'pan2' -> [ -1, 1, -1, 1, 0.1, 0], 
 'pan3' -> [ -1, 1, -1, 1, 0.1, 0], 
 'pulsaret' -> [ -1, 1, -1, 1, 0.1, 0], 
 'amp1' -> [ -1, 1, -1, 1, 0.1, 0.5], 
 'amp2' -> [ -1, 1, -1, 1, 0.1, 0.5], 
 'amp3' -> [ -1, 1, -1, 1, 0.1, 0.5], 
 'pulseEnv' -> [ -1, 1, -1, 1, 0.1, 1]
 ];
 }
 *numChannels_ { arg inNumChans = 2; var dummy; 
 
 if (Synth.isOn, { MessageWindow("Can't switch numChannels while Synth is on.") ^this });
 
 if (inNumChans.isNumber, { dummy = inNumChans.clip(2, 8).asInteger; });
 if (inNumChans.isKindOf(String), { dummy = inNumChans.at(0).digit; });
 if (inNumChans.isKindOf(Char), { dummy = inNumChans.digit; }); 
 if (inNumChans != numChannels, 
 { numChannels = dummy; 
 scopeWindow.scopeViews.do({ arg each; PG.scopeWindow.remove(each) });
 scopeWindow.init(numChannels);
 }
 );
 
   } 
 
 *headerFormat_ { arg inHeader;
 if (SoundFileXT.possibleRecFormats.flop.at(0).includes(inHeader.asSymbol), 
 { headerFormat = inHeader }, 
 { inHeader.postc; " is not a valid headerFormat.".postln; }
 );
 }
 
 *sampleFormat_ { arg inSample; 
 if (SoundFileXT.possibleRecFormats.flop.at(1).includes(inSample), 
 { sampleFormat = inSample },
 { inSample.postc; " is not a valid sampleFormat.".postln; }
 );
 
 }
 
 *xFadeIsLocked { ^xFadeLocked } 
 *lockXFade { xFadeLocked = true }
 *unLockXFade { xFadeLocked = false }
 *linkSettings { settingsAreLinked = true }
 *unLinkSettings { settingsAreLinked = false }
 
 
     
 *addTasks { arg tasks, time = 0.03;   if (stackOfTasks.isNil, { stackOfTasks = List.new });
 if (waitTimes.isNil, { waitTimes = List.new });
 
 stackOfTasks = stackOfTasks ++ tasks; 
 
 waitTimes = waitTimes ++ Array.fill(tasks.size, time); 
 }
 
 *doTasks {
 if (stackOfTasks.size == 0, { ^this });
 if (Synth.isOn, 
 { Task({ 
 while(
 { stackOfTasks.size > 0 },
 { stackOfTasks.removeAt(0).value; 
 waitTimes.removeAt(0).wait; 
 }
 )
 });
 }, 
 { while({ stackOfTasks.size > 0 },
 { stackOfTasks.removeAt(0).value; 
 waitTimes.removeAt(0); 
 }
 )
 }
 );
 }
 
 *masterFade { arg fadeTime = 5, level = 0; 
 var currentMasterLevel, volCtl, increment, repTime = 0.03, stepFunc; 
 volCtl = controlPanel.masterVol; 
 currentMasterLevel = volCtl.value; 
 increment = level - currentMasterLevel / fadeTime * repTime; 
 if (thisSynth.notNil, { 
 stepFunc = { 
 if (volCtl.value == currentMasterLevel, { 
 currentMasterLevel = currentMasterLevel + increment; 
 volCtl.value = currentMasterLevel; 
 });
 };
 thisSynth.repeat(repTime, repTime, { 
 if (currentMasterLevel = volCtl.value, 
 { }
 );
 });
 }); 
 }
 *run {  
 var startState, oscBankIsFrozen, playDur;
 
 if (this.isDemo, { 
 if (Date.getDate.bootSeconds - startupTime > 1800, {  ^MessageWindow([ "Runtime for the demo has been exceeded.", "Please buy the full version." ]) 
 });
 if (numPulsars > 75025, {  ^MessageWindow([ "You are out of Pulsar Credits.", "Please buy the full version." ]) 
 });
 });
 
 if (canStart.not, { ^MessageWindow("Still busy... Please try again later.") });
 
 playDur = if (this.isDemo, 60, nil); 
 
 startState = settingsLib.getState;    
 oscBankIsFrozen = (oscBank.loopFreq == 0.0);  
 currentBank.at(\currentTables).put(\settings, startState);
 settingsLib.setState(startState); 
 
 controlPanel.startButtonOn; 
 
 if (this.isDemo, { "demo cannot record.".postln; }, 
 { this.prepareRecord; }
 );
 
 this.initOscBank; 
 if (oscBankIsFrozen, { oscBank.loopFreq_(0) }, 
 { oscBank.loopTime_(startState.at(31)) } );
 
 this.initEngine; 
 
 Synth.play({ 
 var out, directLevel, xInLevel, fxBlend, fileSound;
 engine.setAllControls(oscBank.asUgen);
 
 out = engine.asUgen(numChannels);
 
  
 if (mixerWindow.hasFile, 
 { 
 mixerWindow.fileIsOn; 
 
    
 #xInLevel, directLevel = Plug.kr([1, 0]); 
 fxBlend = mixerWindow.outSliders.at(0)
 .action_({ arg sl; 
  
 xInLevel.source = fxBlend.value * 0.3;    directLevel.source = 1 - (fxBlend.value * 0.1); 
 });
 
 fxBlend.doAction;  
   out = (out * directLevel) +
 
 Pause.ar({ mixerWindow.filePlayer.asUgen.asArray.wrapExtend(numChannels) * out }, xInLevel);
 }, 
 {  mixerWindow.noSoundFile;   }
 ); 
            
 out = LeakDC.ar(out, 0.999, controlPanel.masterVol.kr).reverse;   if (this.isDemo.not, { 
 Pause.ar({ DiskOut.ar(recFile, 32768, out) }, 
 controlPanel.recordButton.kr(0)
 );  });
 
 Pause.ar(  { Scope.ar(scopeWindow.scopeViews, out) },
 controlPanel.scopeButton.kr(0) 
 ); 
 out; 
 }, playDur);
 this.synthHasStopped;
 } 
 
 *updateBurstLoop { arg onOrOff, ons = 2, offs = 1;  onOrOff = onOrOff.asInteger;
 burstLoop.ratio(ons, offs); 
 engine.setPlayFlagFunc(
 [ { engine.pulseMask.poll.coin }, 
 { engine.pulseMask.poll.coin and: burstLoop.next }
 ].at( onOrOff.asInteger );
 );
 }
 
 *prepareRecord {   var filename;
 recFile = SoundFile.new;
 filename = recFolder ++ recFileName;
 
  while (
 { recFile.readHeader(filename) and: ( recFile.numFrames > 0) }, 
 {  filename = PathName(filename).nextName;
 }
 );
 
 recFile.numChannels = PG.numChannels;  recFile.headerFormat = headerFormat.asSymbol;
 recFile.sampleFormat = sampleFormat.asSymbol;
 
 if (recFile.writeHeader(filename), 
 { 
 recFileName = PathName(recFileName).fileName;
 recFile.prepareRecord;
 },
 { ("write record file header failed : " ++ filename).postln;
 recFile = nil;
 }
 );
 }
 
 *synthHasStopped { 
 if (recFile.notNil and: this.isDemo.not, { recFile.endRecord; });
 this.unLockXFade; 
 oscBank.phaseOffset = 0; 
 
 
 numPulsars = engine.numPulsars;
 
 tablesPanel.xFadeOff;
 controlPanel.synthHasStopped(numPulsars);
 mixerWindow.fileIsOff;
 
 this.doTasks;  
 this.closeStrayWindows;
 }
 
 *closeStrayWindows {  var strayWindows;    
 strayWindows = GUIWindow.allWindows.select({ arg w; w.isVisible.not and: w.closeable }); 
 strayWindows.do({ arg w; w.close; });
 }
 
 *beginXFade { 
 var newTables, tableSet, editWindows; 
 fadeTime = tablesPanel.fadeTimeBox.value; 
 
 this.lockXFade;
 
 editWindows = GUIWindow.allWindows.select({ arg w; w.isKindOf(ScaleTablePanel) });
 editWindows.do({ arg w; w.hide });
 
 engine.prepareXFadeSignals(
 currentBank.at('currentTables').at(\pulsaret),
 currentBank.at('currentTables').at(\pulseEnv)
 ); 
 engine.startFade(fadeTime); 
 if (controlPanel.freezeButton.value == 1, 
 { controlPanel.freezeButton.doAction; }  );  
 tableSet = currentBank.at('currentTables');
 newTables = [ 
 'fundFreq', 'pulseMask',
 'formFreq1', 'pan1', 'amp1', 
 'formFreq2', 'pan2', 'amp2', 
 'formFreq3', 'pan3', 'amp3' 
 ].collect({ arg key; tableSet.at(key); });
 oscBank.startFade(newTables, fadeTime); 
 
 thisSynth.sched(fadeTime, { tablesPanel.xFadeOff; this.unLockXFade; });
 }
 *newCurrentTables {  
 var newSettings; 
 
 currentBank.at(\currentTables).keysValuesDo({ arg key, table; 
  if (key == \settings, 
 { newSettings = table; },
 { allTableViews.at(key).wavetable_(table).refresh; }
 );
 });
 
 if (Synth.isOn, 
 { tablesPanel.xFadeOn; 
 this.beginXFade; 
 if (settingsAreLinked and: newSettings.notNil, 
 { settingsLib.xFadeTo(newSettings, fadeTime); 
 }
 ); 
 }, 
 { 
 if (newSettings.notNil, { settingsLib.setState(newSettings); });
 
 engine.setPulsaretAndEnv(  currentBank.at(\currentTables).at(\pulsaret), 
 currentBank.at(\currentTables).at(\pulseEnv)
 );
 }
 );
 }
 *randomizeControls { 
 var finalState, fadeTime; 
 
 finalState = settingsLib.randomState; 
 fadeTime = tablesPanel.fadeTimeBox.value;
 
 if (Synth.isOn, 
 { this.lockXFade;
 settingsLib.xFadeTo(finalState, fadeTime); 
 thisSynth.sched(fadeTime, { this.unLockXFade; });
 }, 
 { settingsLib.setState(finalState); }
 );
 } 
 
 *makeGUI {
 this.makeControlPanel; 
 this.makeTablesPanel; 
 this.makeTableWindows;
 this.makeScopeWindow; 
 scopeWindow.hide;
 
 mixerWindow = MixerWindow.new("FX : RingMod", 
 allWindowBounds.at('mixerWindow'), 
 [ [ "Dry/Wet" ]  ]
 );
 mixerWindow.backColor_(bAndKGreen)
 .stringColor_(bAndKGreenLight)
 .buttonColor_(bAndKGreenLight)
 .knobOnColor_(onYellow)
 .knobOffColor_(white); 
 
 GUIWindow.allWindows.do({  arg eachW; 
 eachW.closeable_(false);
 });
 controlPanel.toFront;
 }
 
 *makeScopeWindow { 
 
 scopeWindow = ScopeWindow.new("Scope", 
 allWindowBounds.at('scopeWindow'),
 2); 
 
 scopeWindow.closeable_(false).backColor_(bAndKGreen);
  
 scopeWindow.hide;
 }
 
 *makeTableWindows { 
 
 allTableViews = IdentityDictionary.new; 
 allTableWindows = IdentityDictionary.new; 
 
 currentBank.tableNames.do(
 { arg key; 
 var tWindow; 
 tWindow = TableWindow.new(
 currentBank.at(\currentTables).at(key), 
 key.asString, 
 allWindowBounds.at(key),
 allTableViewSpecs.at(key)
 )
 .backColor_(bAndKGreen)
 .labelColor_(bAndKGreenLight);
 tWindow.addButtons(
 [ [ "E", { tWindow.tableView.focus.keyDown($E); } ], 
 [ "S", { tWindow.tableView.focus.keyDown($S) } ], 
 [ "L", { tWindow.tableView.focus.keyDown($L) } ], 
 [ "I", { tWindow.tableView.focus.importSoundFile; } ],
 [ "A", { tWindow.tableView.focus.autoScale } ], 
 [ "D", { tWindow.tableView.focus.keyDown($D) } ]
 ] 
 ); 
 
  tWindow.tableView.keyDownAction_({ arg asciiChar;
 if ("+-*".includes(asciiChar), 
 { tWindow.bounds = allWindowBounds.at(
 Dictionary[ 
 '*' -> \max, 
 '-' -> tWindow.name.asSymbol, 
 '+' -> \alignToCycle
 ].at(asciiChar.asSymbol)
 );
 
 if ([ \pulsaret, \pulseEnv ].includes(tWindow.name.asSymbol).not, 
 { controlPanel.bounds = allWindowBounds.at(
 if (asciiChar == $*, 
 \controlPanelToMaxCycle, 
 \controlPanel) 
 );
 }
 );
 }
 ); 
 });
 
 allTableViews.put(key, tWindow.tableView);
 allTableWindows.put(key, tWindow);
 }
 );
 } 
 
 *makeControlPanel {  
 controlPanel = PGControlPanel.new;
 
 controlPanel.muteButtons.do({ arg eachBtn, index; 
 settingsLib.putSaveable(1 + index, eachBtn); 
 });
 
 controlPanel.ampSliders.do({ arg eachSl, index; 
 settingsLib.putSaveable(11 + index, eachSl); 
 });
 
 settingsLib.putSaveable(21, controlPanel.burstMaskSwitch);
 settingsLib.putSaveable(22, controlPanel.burstOnBox);
 settingsLib.putSaveable(23, controlPanel.burstOffBox);
 settingsLib.putSaveable(31, controlPanel.trainDurBox); 
 settingsLib.putSaveable(41, controlPanel.edgeBox);
 
 } 
 
 *makeTablesPanel { 
 tablesPanel = TablesPanel.new("Tables Bank", allWindowBounds.at('tablesPanel'))
 .backColor_(bAndKGreen); 
 
 currentBank.panel_(tablesPanel);  tablesPanel.currentBank_(currentBank);
 tablesPanel.updateSetSwitches;
 }
 *toFront {  currentBank.tableNames.do({ arg eachName; 
 var eachW; 
 eachW = allTableWindows.at(eachName); 
 eachW.bounds = allWindowBounds.at(eachName);
 };
 ); 
 scopeWindow.bounds_(allWindowBounds.at('scopeWindow'));
 mixerWindow.bounds_(allWindowBounds.at('mixerWindow'));
 controlPanel.bounds_(allWindowBounds.at('controlPanel')); 
 if (userMode == \full, { 
 allTableWindows.do({ arg w; w.show.toFront; }); 
 tablesPanel.show.toFront;
 mixerWindow.show.toFront;
 scopeWindow.show.toFront; 
 controlPanel.scopeButton.value_(1);
 });
 if (userMode == \basic, { this.basicUserMode; });
 
 controlPanel.show.toFront; 
 }
 
 *basicUserMode { 
 userMode = \basic;
   
  PG.allTableWindows.do({ arg w; 
 if ([ "pan3", "pan2", "amp2", "amp3", "pulseEnv", 
 "formFreq2", "formFreq3" ].includes(w.name), 
 { w.hide }, { w.show });
 });
 
  PG.mixerWindow.hide; 
 PG.tablesPanel.hide; 
 PG.scopeWindow.show;
 
 
  PG.allTableViews.at(\pulseEnv).keyDown($1);
 
 
  PG.controlPanel.loopButton.value_(1).doAction;
 PG.controlPanel.burstMaskSwitch.value_(0).doAction;
 
  PG.controlPanel.muteButtons.do({ arg btn, i; 
 if (i > 0, { btn.value_(0).doAction; });
 });
 }
 *fullUserMode { 
 
 userMode = \full;
 
 PG.allTableWindows.do({ arg w; w.show });
 
  PG.mixerWindow.show; 
 PG.tablesPanel.show; 
 PG.scopeWindow.show; 
 
 }
  
 *loadSettings { arg pathWithoutFileName; 
 ^settingsLib.readState(pathWithoutFileName);
 }
 
 *saveSettings { arg pathWithoutFileName; 
 settingsLib.saveState(settingsLib.getState, pathWithoutFileName); 
 }
 
 *getCurrentSettings { ^settingsLib.getState; } 
}
PriorityQueue {
 var <array;
 
 put { arg time, item;
 _PriorityQueueAdd
 ^this.primitiveFailed;
 }
 pop {
 _PriorityQueuePop
 ^this.primitiveFailed;
 }
 topPriority {
 _PriorityQueueTop
 ^this.primitiveFailed;
 }
 clear {
 _PriorityQueueClear
 ^this.primitiveFailed;
 }
 isEmpty {
 _PriorityQueueEmpty
 ^this.primitiveFailed;
 }
 notEmpty { ^this.isEmpty.not }
}Collection { 
 *newFrom { arg aCollection;
 var newCollection;
 newCollection = this.new(aCollection.size);
 aCollection.do({ arg item, i; newCollection.add(item) });
 ^newCollection
 }
 *with { arg ... args; 
 var newColl;
   newColl = this.new(args.size);
 newColl.addAll(args);
 ^newColl
 }
 *fill { arg size, function;
 var obj;
 obj = this.new(size);
 size.do({ arg i;
 obj.add(function.value(i));
 });
 ^obj
 } 
 
 == { arg aCollection;
 if (this.size != aCollection.size, { ^false });
 this.do({ arg item, i;
 if (item != aCollection.at(i), { ^false });
 });
 ^true
 }
 hash { 
 var hash = 87263867;
 this.do({ arg item;
 hash = hash bitXor: item.hash;
 });
 }
 
 species { ^List }
 do { ^this.subclassResponsibility(thisMethod) }
 size { 
  var tally = 0;
 this.do({ tally = tally + 1 });
 ^tally
 }
 
 isEmpty { ^this.size == 0 }
 notEmpty { ^this.size > 0 }
 
 add { ^this.subclassResponsibility(thisMethod) }
 addAll { arg aCollection; aCollection.do({ arg item; this.add(item) }) }
 remove { ^this.subclassResponsibility(thisMethod) }
 removeAll { arg list; list.do({ arg item; this.remove(item) }) }
 removeAllSuchThat { arg function; 
 var removedItems, copy;
 removedItems = this.species.new;
 copy = this.copy;
 copy.do({ arg item, i;
 if ( function.value(item, i), {
 this.remove(item);
 removedItems.add(item);
 })
 });
 ^removedItems
 }
 
 includes { arg item1; 
 this.do({ arg item2; if (item1 == item2, {^true}) });
 ^false
 }
 includesAny { arg aCollection;
 aCollection.do({ arg item; if (this.includes(item), {^true}) });
 ^false
 }
 includesAll { arg aCollection;
 aCollection.do({ arg item; if (this.includes(item).not, {^false}) });
 ^true 
 }
 collect { arg function;
 var i, res;
 res = this.species.new(this.size);
 this.do({ arg elem, i; res.add(function.value(elem, i)) })
 ^res;
 }
 select { arg function;
 var i, res;
 res = this.species.new(this.size);
 this.do({ arg elem, i; if (function.value(elem, i), { res.add(elem) }) })
 ^res;
 }
 reject { arg function;
 var i, res;
 res = this.species.new(this.size);
 this.do({ arg elem, i; if (function.value(elem, i).not, {res.add(elem)}) })
 ^res;
 }
 detect { arg function;
 this.do({ arg elem, i; if (function.value(elem, i), { ^elem }) })
 ^nil;
 }
 inject { arg thisValue, function;
 var nextValue;
 nextValue = thisValue;
 this.do({ arg item, i; 
 nextValue = function.value(nextValue, item, i);
 });
 ^nextValue
 }
 count { arg function;
 var sum = 0;
 this.do({ arg elem, i; if (function.value(elem, i), { sum=sum+1 }) })
 ^sum;
 }
 occurencesOf { arg obj;
 var sum = 0;
 this.do({ arg elem; if (elem == obj, { sum=sum+1 }) })
 ^sum;
 }
 any { arg function;
 this.do({ arg elem, i; if (function.value(elem, i), { ^true }) })
 ^false;
 }
 every { arg function;
 this.do({ arg elem, i; if (function.value(elem, i).not, { ^false }) })
 ^true;
 }
 sum { arg function;
 var i, sum = 0;
 if (function.isNil, {  this.do({ arg elem; sum = sum + elem; })
 },{
 this.do({ arg elem, i; sum = sum + function.value(elem, i); })
 })
 ^sum;
 }
 sumabs {  var sum = 0;
 this.do({ arg elem; 
 if (elem.isKindOf(SequenceableCollection), { elem = elem.at(0) });
 sum = sum + elem.abs; 
 })
 ^sum;
 }
 maxItem { arg function;
 var maxValue, maxElement;
 if (function.isNil, {  this.do({ arg elem; 
 if (maxElement.isNil, {
 maxElement = elem; 
 },{
 if (elem > maxElement, {
 maxElement = elem; 
 })
 }) 
 })
 ^maxElement;
 },{
 this.do({ arg elem, i; var val;
 if (maxValue.isNil, {
 maxValue = function.value(elem, i);
 maxElement = elem; 
 },{ 
 val = function.value(elem, i);
 if (val > maxValue, {
 maxValue = val;
 maxElement = elem; 
 })
 }) 
 })
 ^maxElement;
 })
 }
 minItem { arg function;
 var minValue, minElement;
 if (function.isNil, {  this.do({ arg elem, i; 
 if (minElement.isNil, {
 minElement = elem; 
 },{
 if (elem < minElement, {
 minElement = elem; 
 })
 }) 
 });
 ^minElement;
 },{
 this.do({ arg elem, i; var val;
 if (minValue.isNil, {
 minValue = function.value(elem, i);
 minElement = elem; 
 },{ 
 val = function.value(elem, i);
 if (val < minValue, {
 minValue = val;
 minElement = elem; 
 })
 }) 
 })
 ^minElement;
 })
 }
 asBag { ^Bag.new(this.size).addAll(this); }
 asList { ^List.new(this.size).addAll(this); }
 asSet { ^Set.new(this.size).addAll(this); }
 asSortedList { ^SortedList.new(this.size).addAll(this); }
 
 printOn { arg stream;
 stream << this.class.name << "[ " ;
 this.printItemsOn(stream);
 stream << " ]" ;
 }
 storeOn { arg stream;
 stream << this.class.name << "[ " ;
 this.storeItemsOn(stream);
 stream << " ]" ;
 }
 storeItemsOn { arg stream, itemsPerLine = 10;
 var last, itemsPerLinem1;
 itemsPerLinem1 = itemsPerLine - 1;
 last = this.size - 1;
 this.do({ arg item, i;
 item.storeOn(stream);
 if (i < last, { stream.comma.space;
 if (i % itemsPerLine == itemsPerLinem1, { stream.nl.space.space });
 });
 });
 }
 printItemsOn { arg stream, itemsPerLine = 10;
 var last, itemsPerLinem1;
 itemsPerLinem1 = itemsPerLine - 1;
 last = this.size - 1;
 this.do({ arg item, i;
 item.printOn(stream);
 if (i < last, { stream.comma.space;
 if (i % itemsPerLine == itemsPerLinem1, { stream.nl.space.space });
 });
 });
 }
}
SequenceableCollection : Collection {
  *series { arg size, start, step;
 var i=0, obj;
 obj = this.new(size);
 while ({ i < size },{
 obj.add(start);
 start = start + step;
 i = i + 1;
 });
 ^obj
 } 
  *geom { arg size, start, grow;
 var i=0, obj;
 obj = this.new(size);
 while ({ i < size },{
 obj.add(start);
 start = start * grow;
 i = i + 1;
 });
 ^obj
 } 
 *rand { arg size, minVal, maxVal;
 var i=0, obj;
 obj = this.new(size);
 while ({ i < size },{
 obj.add(rrand(minVal, maxVal));
 i = i + 1;
 });
 ^obj
 }
 *rand2 { arg size, val;
 var i=0, obj;
 obj = this.new(size);
 while ({ i < size },{
 obj.add(val.rand2);
 i = i + 1;
 });
 ^obj
 }
 *linrand { arg size, minVal, maxVal;
 var i=0, obj, range;
 range = maxVal - minVal;
 obj = this.new(size);
 while ({ i < size },{
 obj.add(minVal + range.linrand);
 i = i + 1;
 });
 ^obj
 }
 ++ { arg aSequentialCollection; 
 var newlist;
 newlist = this.species.new(this.size + aSequentialCollection.size);
 newlist = newlist.addAll(this).addAll(aSequentialCollection);
 ^newlist
 }
 
  choose { 
 ^this.at(this.size.rand) 
 }
  wchoose { arg weights;
 ^this.at(weights.windex) 
 }
 copyRange { arg start, end;
 var newColl, i;
 i = start;
 newColl = this.species.new(end - start);
 while ({ i < end },{
 newColl.add(this.at(i));
 i = i + 1;
 });
 ^newColl
 }
 indexOf { arg item;
 this.do({ arg elem, i;
 if ( item === elem, { ^i })
 });
 ^nil
 }
 remove { arg item;
 var index;
 index = this.indexOf(item);
 ^if ( index.notNil, {
 this.removeAt(index);
 },{
 nil
 });
 }
 take { arg item;
 var index;
 index = this.indexOf(item);
 ^if ( index.notNil, {
 this.takeAt(index);
 },{
 nil
 });
 }
 
  first { if (this.size > 0, { ^this.at(0) }, { ^nil }) }
 last { if (this.size > 0, { ^this.at(this.size - 1) }, { ^nil }) }
 top { ^this.last }
  doAdjacentPairs { arg function;
 (this.size - 1).do({ arg i;
 function.value(this.at(i), this.at(i+1), i);
 })
 }
 separate { arg function;
 var list, sublist;
 list = this.species.new;
 sublist = this.species.new;
 this.doAdjacentPairs({ arg a, b, i;
 sublist = sublist.add(a);
 if ( function.value(a, b, i), {
 list = list.add(sublist);
 sublist = this.species.new;
 });
 });
 sublist = sublist.add(this.last);
 list = list.add(sublist);
 ^list
 }
 clump { arg groupSize;
 var list, sublist;
 list = this.species.new;
 sublist = this.species.new;
 this.do({ arg item;
 sublist = sublist.add(item);
 if (sublist.size >= groupSize, {
 list = list.add(sublist);
 sublist = this.species.new;
 });
 });
 if (sublist.size > 0, { list = list.add(sublist); });
 ^list
 }
 clumps { arg groupSizeList;
 var list, sublist, i=0;
 list = this.species.new;
 sublist = this.species.new;
 this.do({ arg item;
 sublist = sublist.add(item);
 if (sublist.size >= groupSizeList.wrapAt(i), {
 i = i + 1;
 list = list.add(sublist);
 sublist = this.species.new;
 });
 });
 if (sublist.size > 0, { list = list.add(sublist); });
 ^list
 }
 curdle { arg probability;
 ^this.separate({ probability.coin });
 }
 flatten { arg numLevels=1;
 var list;
 
 if (numLevels <= 0, { ^this });
 numLevels = numLevels - 1;
 
 list = this.species.new;
 this.do({ arg item;
 if (item.respondsTo('flatten'), {
 list = list.addAll(item.flatten(numLevels));
 },{
 list = list.add(item);
 });
 });
 ^list
 }
 
 flat {
 var list; 
 list = this.species.new;
 this.do({ arg item, i;
 if (item.respondsTo('flat'), {
 list = list.addAll(item.flat);
 },{
 list = list.add(item);
 });
 });
 ^list
 }
 flop {
 var list, size, maxsize;
 
 size = this.size;
 maxsize = 0;
 this.do({ arg sublist;
 var sz;
 sz = if (sublist.isKindOf(SequenceableCollection), { sublist.size },{ 1 });
 if (sz > maxsize, { maxsize = sz });
 });
 
 list = this.species.fill(maxsize, { this.species.new(size) });
 this.do({ arg isublist, i;
 if (isublist.isKindOf(SequenceableCollection), {
 list.do({ arg jsublist, j;
 jsublist.add( isublist.wrapAt(j); );
 });
 },{
 list.do({ arg jsublist, j;
 jsublist.add( isublist );
 });
 });
 });
 ^list
 }
 unlace { arg numlists;
 var size, list, sublist;
 
 size = (this.size + numlists - 1) div: numlists;
 list = this.species.fill(numlists, { this.species.new(size) });
 this.do({ arg item, i;
 sublist = list.at(i % numlists);
 sublist.add(item);
 });
 ^list
 }
 
  degreeToKey { arg scale, stepsPerOctave=12;
 var size;
 size = scale.size;
 ^this.collect({ arg scaleDegree;
 scaleDegree = scaleDegree.asInteger;
 (stepsPerOctave * (scaleDegree div: size)) + scale.wrapAt(scaleDegree)
 });
 }
 transposeKey { arg amount, octave=12;
 ^((this + amount) % octave).sort
 }
 mode { arg degree, octave=12;
 var transpose;
 transpose = this.at(degree);
 ^((this - transpose) % octave)
 }
 zxmode { arg degree, octave=12;
 var transpose, i=0, j, size, newColl;
 transpose = this.at(degree);
 size = this.size;
 newColl = this.class.new(size);
 j = degree;
 while ({ i < size },{
 newColl.add((this.at(j) - transpose) % octave);
 j = j + 1;
 if (j >= size, { j = 0 });
 });
 ^newColl;
 }
 
  convertRhythm {
 var list, tie;
 list = List.new;
 tie = this.convertOneRhythm(list);
 if (tie > 0.0, { list.add(tie) });  ^list
 }
 convertOneRhythm { arg list, tie = 0.0, stretch = 1.0;
 var beats, divisions, repeats;
 
 #beats, divisions, repeats = this;
 stretch = stretch * beats / divisions.sum;
 repeats.do({
 divisions.do({ arg val;
 if (val.isKindOf(SequenceableCollection), {
 tie = val.convertOneRhythm(list, tie, stretch)
 },{
 val = val * stretch;
 if (val > 0.0, { 
 list.add(val + tie); 
 tie = 0.0; 
 },{ 
 tie = tie - val 
 });
 });
 });
 });
 ^tie
 }
  blend { arg anotherSeqColl, blendFrac = 0.5;
   ^this.collect({ arg item, i; item.blend(anotherSeqColl.at(i), blendFrac); });
 }
 
  neg { ^this.performUnaryOp('neg') }
 bitNot { ^this.performUnaryOp('bitNot') }
 abs { ^this.performUnaryOp('abs') }
 ceil { ^this.performUnaryOp('ceil') }
 floor { ^this.performUnaryOp('floor') }
 frac { ^this.performUnaryOp('frac') }
 sign { ^this.performUnaryOp('sign') }
 squared { ^this.performUnaryOp('squared') }
 cubed { ^this.performUnaryOp('cubed') }
 sqrt { ^this.performUnaryOp('sqrt') }
 exp { ^this.performUnaryOp('exp') }
 reciprocal { ^this.performUnaryOp('reciprocal') }
 midicps { ^this.performUnaryOp('midicps') }
 cpsmidi { ^this.performUnaryOp('cpsmidi') }
 midiratio { ^this.performUnaryOp('midiratio') }
 ratiomidi { ^this.performUnaryOp('ratiomidi') }
 ampdb { ^this.performUnaryOp('ampdb') }
 dbamp { ^this.performUnaryOp('dbamp') } 
 octcps { ^this.performUnaryOp('octcps') }
 cpsoct { ^this.performUnaryOp('cpsoct') }
 log { ^this.performUnaryOp('log') }
 log2 { ^this.performUnaryOp('log2') }
 log10 { ^this.performUnaryOp('log10') }
 sin { ^this.performUnaryOp('sin') }
 cos { ^this.performUnaryOp('cos') }
 tan { ^this.performUnaryOp('tan') }
 asin { ^this.performUnaryOp('asin') }
 acos { ^this.performUnaryOp('acos') }
 atan { ^this.performUnaryOp('atan') }
 sinh { ^this.performUnaryOp('sinh') }
 cosh { ^this.performUnaryOp('cosh') }
 tanh { ^this.performUnaryOp('tanh') }
 rand { ^this.performUnaryOp('rand') }
 rand2 { ^this.performUnaryOp('rand2') }
 linrand { ^this.performUnaryOp('linrand') }
 bilinrand { ^this.performUnaryOp('bilinrand') }
 sum3rand { ^this.performUnaryOp('sum3rand') }
 distort { ^this.performUnaryOp('distort') }
 softclip { ^this.performUnaryOp('softclip') }
 nyqring { ^this.performUnaryOp('nyqring') }
 coin { ^this.performUnaryOp('coin') }
 even { ^this.performUnaryOp('even') }
 odd { ^this.performUnaryOp('odd') }
 isPositive { ^this.performUnaryOp('isPositive') }
 isNegative { ^this.performUnaryOp('isNegative') }
 isStrictlyPositive { ^this.performUnaryOp('isStrictlyPositive') }
 asFloat { ^this.performUnaryOp('asFloat') }
 asInteger { ^this.performUnaryOp('asInt') }
 
 real { ^this.performUnaryOp('real') }
 imag { ^this.performUnaryOp('imag') }
 magnitude { ^this.performUnaryOp('magnitude') }
 magnitudeApx { ^this.performUnaryOp('magnitudeApx') }
 phase { ^this.performUnaryOp('phase') }
 angle { ^this.performUnaryOp('angle') }
 rho { ^this.performUnaryOp('rho') }
 theta { ^this.performUnaryOp('theta') }
 
  + { arg aNumber; ^this.performBinaryOp('+', aNumber) }
 - { arg aNumber; ^this.performBinaryOp('-', aNumber) }
 * { arg aNumber; ^this.performBinaryOp('*', aNumber) }
 / { arg aNumber; ^this.performBinaryOp('/', aNumber) }
 div { arg aNumber; ^this.performBinaryOp('div', aNumber) }
 % { arg aNumber; ^this.performBinaryOp('%', aNumber) }
 ** { arg aNumber; ^this.performBinaryOp('**', aNumber) }
 min { arg aNumber; ^this.performBinaryOp('min', aNumber) } 
 max { arg aNumber; ^this.performBinaryOp('max', aNumber) }
 
 < { arg aNumber; ^this.performBinaryOp('<', aNumber) }
 <= { arg aNumber; ^this.performBinaryOp('<=', aNumber) }
 > { arg aNumber; ^this.performBinaryOp('>', aNumber) }
 >= { arg aNumber; ^this.performBinaryOp('>=', aNumber) }
 & { arg aNumber; ^this.performBinaryOp('&', aNumber) }
 | { arg aNumber; ^this.performBinaryOp('|', aNumber) }
 bitXor { arg aNumber; ^this.performBinaryOp('bitXor', aNumber) } 
 lcm { arg aNumber; ^this.performBinaryOp('lcm', aNumber) }
 gcd { arg aNumber; ^this.performBinaryOp('gcd', aNumber) }
 round { arg aNumber; ^this.performBinaryOp('round', aNumber) }
 trunc { arg aNumber; ^this.performBinaryOp('trunc', aNumber) }
 atan2 { arg aNumber; ^this.performBinaryOp('atan2', aNumber) } 
 hypot { arg aNumber; ^this.performBinaryOp('hypot', aNumber) }
 hypotApx { arg aNumber; ^this.performBinaryOp('hypotApx', aNumber) }
 >> { arg aNumber; ^this.performBinaryOp('>>', aNumber) }
 +>> { arg aNumber; ^this.performBinaryOp('+>>', aNumber) }
 ring1 { arg aNumber; ^this.performBinaryOp('ring1', aNumber) }
 ring2 { arg aNumber; ^this.performBinaryOp('ring2', aNumber) }
 ring3 { arg aNumber; ^this.performBinaryOp('ring3', aNumber) }
 ring4 { arg aNumber; ^this.performBinaryOp('ring4', aNumber) }
 difsqr { arg aNumber; ^this.performBinaryOp('difsqr', aNumber) }
 sumsqr { arg aNumber; ^this.performBinaryOp('sumsqr', aNumber) }
 sqrsum { arg aNumber; ^this.performBinaryOp('sqrsum', aNumber) }
 sqrdif { arg aNumber; ^this.performBinaryOp('sqrdif', aNumber) }
 absdif { arg aNumber; ^this.performBinaryOp('absdif', aNumber) }
 thresh { arg aNumber; ^this.performBinaryOp('thresh', aNumber) }
 amclip { arg aNumber; ^this.performBinaryOp('amclip', aNumber) }
 scaleneg { arg aNumber; ^this.performBinaryOp('scaleneg', aNumber) }
 clip2 { arg aNumber; ^this.performBinaryOp('clip2', aNumber) }
 fold2 { arg aNumber; ^this.performBinaryOp('fold2', aNumber) }
 wrap2 { arg aNumber; ^this.performBinaryOp('wrap2', aNumber) }
 excess { arg aNumber; ^this.performBinaryOp('excess', aNumber) }
 <! { arg aNumber; ^this.performBinaryOp('<!', aNumber) }
 rrand { arg aNumber; ^this.performBinaryOp('rrand', aNumber) }
 exprand { arg aNumber; ^this.performBinaryOp('exprand', aNumber) }
  performUnaryOp { arg aSelector;
 ^this.collect({ arg item; item.perform(aSelector) });
 }
 performBinaryOp { arg aSelector, theOperand;
 var i, minSize, newList;
 ^theOperand.performBinaryOpOnSeqColl(aSelector, this);
 }
 performBinaryOpOnSeqColl { arg aSelector, theOperand;
 var minSize, newList;
 minSize = this.size min: theOperand.size;
 newList = this.species.new(minSize);
 minSize.do({ arg i;
 newList.add(theOperand.at(i).perform(aSelector, this.at(i)));
 });
 ^newList
 }
 performBinaryOpOnSimpleNumber { arg aSelector, aNumber; 
 ^this.collect({ arg item; 
 aNumber.perform(aSelector, item)
 }) 
 }
 performBinaryOpOnComplex { arg aSelector, aComplex; 
 ^this.collect({ arg item; 
 aComplex.perform(aSelector, item)
 }) 
 }
 performBinaryOpOnInfinitum { arg aSelector, aNumber; 
 ^this.collect({ arg item; 
 aNumber.perform(aSelector, item)
 }) 
 }
  sort { arg function; 
 if (function.isNil, { function = { arg a, b; a < b }; }); 
 this.sortRange(0, this.size - 1, function) 
 }
 
 swap { arg i, j;
 var temp;
 temp = this.at(i);
 this.put(i, this.at(j));
 this.put(j, temp);
 }
 
 sortRange { arg i, j, function;
   var di, dij, dj, tt, ij, k, l, n;
 
  if ((n = j + 1 - i) <= 1, { ^this });  
  di = this.at(i);
 dj = this.at(j);
 if (function.value(di, dj).not, {  this.swap(i,j);
 tt = di;
 di = dj;
 dj = tt;
 });
 if ( n > 2, {  ij = (i + j) div: 2;  dij = this.at(ij);  if (function.value(di, dij), {  if (function.value(dij, dj).not, {  this.swap(j, ij);
 dij = dj;
 })
 },{  this.swap(i, ij);
 dij = di;
 });
 if ( n > 3, {    k = i;
 l = j;
 while ({
 while ({
 l = l - 1; 
 k <= l and: { function.value(dij, this.at(l)) }
 });  while ({
 k = k + 1; 
 k <= l and: { function.value(this.at(k), dij) };
 });  k <= l
 },{
 this.swap(k, l); 
 });
   this.sortRange(i, l, function);
 this.sortRange(k, j, function);
 });
 });
 }
  *streamContents { arg function;
 var stream;
 stream = CollStream.on(this.new(100));
 function.value(stream);
 ^stream.contents
 }
 *streamContentsLimit { arg function, limit=2000;
 var stream;
 stream = LimitedWriteStream.on(this.new(100 min: limit));
 stream.limit_(limit).limitFunc_({ ^stream.contents });
 function.value(stream);
 ^stream.contents
 }
 
}

ArrayedCollection : SequenceableCollection {
   asArray { ^this }
 species { ^this.class }
 *with { arg ... args; 
 var newColl;
   newColl = this.new(args.size);
 args.do({ arg item, i; newColl.add(i, item) });
 ^newColl
 }
 *series { arg size, start, step;
 var array;
 array = this.new(size);
 size.do({ arg i;
 array.add(start);
 start = start + step;
 });
 ^array
 }
 *geom { arg size, start, grow;
 var array;
 array = this.new(size);
 size.do({ arg i;
 array.add(start);
 start = start * grow;
 });
 ^array
 } 
 copyRange { arg start, end; 
 _ObjectCopyRange;
  ^this.primitiveFailed 
 }
 add { arg item; 
    _ArrayAdd 
 ^this.primitiveFailed; 
 }
 addAll { arg aCollection; 
 var array;
 _ArrayAddAll
 array = this;
 aCollection.do({ arg item; array = array.add(item) }) ;
 ^array
 }
 insert { arg index, item; 
    _ArrayInsert
 ^this.primitiveFailed; 
 }
 addFirst { arg item; ^this.insert(0, item) }
 pop { 
  _ArrayPop 
 ^nil; 
 }
 ++ { arg anArray; 
   _ArrayCat;
  ^super ++ anArray
 }
 overWrite { arg aCollection, pos;
 var array, grow;
   grow = pos + aCollection.size - this.size;
 if (grow > 0, { array = this ++ this.class.newClear(grow); },{ array = this });
 aCollection.do({ arg item, i; array.put(pos + i, item); });
 ^array
 }
 grow { arg sizeIncrease;
   _ArrayGrow 
 ^this.primitiveFailed
 }
 growClear { arg sizeIncrease;
    _ArrayGrowClear 
 ^this.primitiveFailed
 }
 seriesFill { arg start, step;
 this.size.do({ arg i;
 this.put(i, start);
 start = start + step;
 });
 }
 size { ^this.basicSize }
 fill { arg value; 
 _ArrayFill 
 ^this.primitiveFailed
 
 }
 do { arg function;
  var size, i=0;
 size = this.size;
 while ({ i < size }, {
 function.value(this.at(i), i);
 i = i + 1;
 })
 }
 reverseDo { arg function;
  var i=0, j=0;
 i = this.size - 1;
 while ({ i >= 0 },{ 
 function.value(this.at(i), j);
 i = i - 1;
 j = j + 1;
 })
 }
 reverse { 
 var i = 0, size1, halfsize;
 size1 = this.size - 1;
 halfsize = this.size div: 2;
 halfsize.do({ arg i;
 this.swap(i, size1 - i);
 });
 }
 windex {
 _ArrayWIndex
 ^this.primitiveFailed 
           }
 normalizeSum {
 _ArrayNormalizeSum
 ^this.primitiveFailed 
  }
}
RawArray : ArrayedCollection {
 write { arg path;
 var file;
 file = File.new(path, "wb");
 if (file.notNil, {
 file.write(this);
 file.close;
 });
 }
 putFile { arg prompt, defaultName;
 if (prompt.isNil, {
 prompt = "Save " ++ this.class.name.asString ++ 
 " as:"
 });
 if (defaultName.isNil, {
 defaultName = "Untitled " ++ this.class.name.asString;
 });
 PutFileDialog.new(prompt, defaultName, { arg ok, path;
 if (ok, {
 this.write(path);
 });
 });
 }
 *read { arg path;
 var file, array;
 file = File.new(path, "rb");
 
 if (file.isOpen, {  array = this.readNew(file);
 file.close;
 ^array
 });
 ^nil
 }
 *getFile { arg action;
 GetFileDialog.new({ arg ok, path;
 var array;
 if (ok, { array = this.read(path); });
 action.value(ok, array);
 });
 }
}
Int8Array[int8] : RawArray {
 *readNew { arg file;
 ^file.readAllInt8;
 }
}
Int16Array[int16] : RawArray {
 *readNew { arg file;
 ^file.readAllInt16;
 }
}
Int32Array[int32] : RawArray {
 *readNew { arg file;
 ^file.readAllInt32;
 }
}
RGBArray[color] : RawArray {
 *readNew { arg file;
 ^file.readAllRGB;
 }
}
FloatArray[float] : RawArray {
 *readNew { arg file;
 ^file.readAllFloat;
 }
}
DoubleArray[double] : RawArray {
 *readNew { arg file;
 ^file.readAllDouble;
 }
}
Array[slot] : ArrayedCollection {
 
 *with { arg ... args; 
   ^args 
 }
 reverse { 
 _ArrayReverse 
 ^this.primitiveFailed 
 }
 scramble { 
 _ArrayScramble 
 ^this.primitiveFailed 
 }
 mirror { 
 _ArrayMirror 
 ^this.primitiveFailed 
 }
 mirror1 { 
 _ArrayMirror1 
 ^this.primitiveFailed 
 }
 mirror2 { 
 _ArrayMirror2 
 ^this.primitiveFailed 
 }
 stutter { arg n=2;
 _ArrayStutter 
 ^this.primitiveFailed 
 }
 rotate { arg n=1;
 _ArrayRotate 
 ^this.primitiveFailed 
 }
 pyramid { arg patternType=1;  _ArrayPyramid 
 ^this.primitiveFailed 
 }
 lace { arg length;
 _ArrayLace
 ^this.primitiveFailed 
 }
 permute { arg nthPermutation;
 _ArrayPermute
 ^this.primitiveFailed 
 }
 wrapExtend { arg length;
 _ArrayExtendWrap 
 ^this.primitiveFailed 
 }
 foldExtend { arg length;
 _ArrayExtendFold
 ^this.primitiveFailed 
 }
 slide { arg windowLength=3, stepSize=1;
 _ArraySlide 
 ^this.primitiveFailed 
 }
 containsSeqColl {
 _ArrayContainsSeqColl 
 ^this.primitiveFailed 
 }
 
  multiChannelExpand {
 _ArrayMultiChannelExpand 
 ^this.primitiveFailed 
 }
 source {
 var elem;
  elem = this.at(0);
 if (elem.isKindOf(OutputProxy), {
 ^elem.source
 },{
 error("source: Not an Array of OutputProxy(s)\n");
 this.halt;
 });
 }
 isValidUGenInput { ^true }
 
 
 
  atIdentityHash { arg argKey;
 _Array_AtIdentityHash
 ^this.primitiveFailed 
 }
  atIdentityHashInPairs { arg argKey;
 _Array_AtIdentityHashInPairs
 ^this.primitiveFailed 
 }
 
 printOn { arg stream;
 stream << "[ " ;
 this.printItemsOn(stream);
 stream << " ]" ;
 }
 storeOn { arg stream;
 stream << "[ " ;
 this.storeItemsOn(stream);
 stream << " ]" ;
 }
 
}

Array2D[slot] : Array {
 var <rows, <cols;
 *new { arg rows=1, cols=1; 
 ^this.newClear(rows, cols) 
 }
 *newClear { arg rows=1, cols=1;
 ^super.newClear(rows*cols).init(rows, cols);
 }
 init { arg argRows, argCols;
 rows = argRows;
 cols = argCols;
 }
 
 at { arg row, col; 
 ^super.at(row*cols + col) 
 }
 put { arg row, col, val; 
 super.put(row*cols + col, val) 
 }
}

Signal[float] : FloatArray {
 
 *sineFill { arg size, amplitudes, phases;
 ^Signal.newClear(size).sineFill(amplitudes, phases).normalize
 }
 *chebyFill { arg size, amplitudes;
 ^Signal.newClear(size).chebyFill(amplitudes).normalizeTransfer
 }
 *hammingWindow { arg size, pad=0;
 if (pad == 0, {
 ^this.newClear(size).fill(0.5).addSine(1, 0.39, -0.5pi);
 },{
 ^this.newClear(size-pad).fill(0.5).addSine(1, 0.39, -0.5pi) ++ this.newClear(pad);
 });
 }
 *hanningWindow { arg size, pad=0;
 if (pad == 0, {
 ^this.newClear(size).fill(0.5).addSine(1, 0.5, -0.5pi);
 },{
 ^this.newClear(size-pad).fill(0.5).addSine(1, 0.5, -0.5pi) ++ this.newClear(pad);
 });
 }
 *welchWindow { arg size, pad=0;
 if (pad == 0, {
 ^this.newClear(size).addSine(0.5, 1, 0);
 },{
 ^this.newClear(size-pad).addSine(0.5, 1, 0) ++ this.newClear(pad);
 });
 }
 *rectWindow { arg size, pad=0;
 if (pad == 0, {
 ^this.newClear(size).fill(1.0);
 },{
 ^this.newClear(size-pad).fill(1.0) ++ this.newClear(pad);
 });
 }
 *readNew { arg file;
 ^file.readAllSignal;
 }
  fill { arg val; _SignalFill ^this.primitiveFailed }
 scale { arg scale; _SignalScale ^this.primitiveFailed }
 offset { arg offset; _SignalOffset ^this.primitiveFailed }
 
 asWavetable { 
   _SignalAsWavetable; 
 ^this.primitiveFailed 
 }
 
 peak { _SignalPeak; ^this.primitiveFailed }
 
 normalize { arg beginSamp=0, endSamp;
 _SignalNormalize; 
 ^this.primitiveFailed 
 }
 normalizeTransfer {
 _SignalNormalizeTransferFn; 
 ^this.primitiveFailed 
 }
 
 invert { arg beginSamp=0, endSamp;
 _SignalInvert; 
 ^this.primitiveFailed 
 }
 reverse { arg beginSamp=0, endSamp;
 _SignalReverse; 
 ^this.primitiveFailed 
 }
 fade { arg beginSamp=0, endSamp, beginLevel=0.0, endLevel=1.0; 
 _SignalFade; 
 ^this.primitiveFailed 
 }
 rotate { arg n=1;
 _SignalRotate 
 ^this.primitiveFailed 
 }
 zeroPad {
 var size;
 size = (this.size.nextPowerOfTwo * 2);
 ^this ++ Signal.newClear(size - this.size);
 }
 
 integral { _SignalIntegral; ^this.primitiveFailed }
 overDub { arg aSignal, index; 
 _SignalOverDub 
   ^this.primitiveFailed 
 }
 overWrite { arg aSignal, index; 
 _SignalOverWrite 
   ^this.primitiveFailed 
 }
 plot { arg name, bounds;
  name = name ? "signal";
 bounds = bounds ? Rect.new(40,80,600,380);
 ^SignalWindow.new(name, bounds, this);  }
 play { arg sampleRate, name=\Signal, loop = true;
 var playbackRate = 1.0;
 if (sampleRate.isNil, { 
 sampleRate = Synth.sampleRate 
 },{
 playbackRate = sampleRate / Synth.sampleRate;
 });
 Instrument.play(name.asSymbol, { arg rate = 1.0;
 if (loop, {
 PlayBuf.ar(this, sampleRate, playbackRate * rate, 0, 0, this.size-2)
 },{
 PlayBuf.ar(this, sampleRate, playbackRate * rate, 0, this.size-2, this.size-2)
 }); 
 }, 1.0);
 }
 
 waveFill { arg function, start = 0.0, end = 1.0;
 var i = 0, step, size, val, x;
 
   size = this.size;
 if (size <= 0, { ^this });
 
 x = start;
 step = (end - start) / size;
 while ({ i < size }, {
 val = function.value(x, this.at(i), i);
 this.put(i, val);
 x = x + step;
 i = i + 1;
 });
 ^this
 }
 addSine { arg harmonicNumber = 1, amplitude = 1.0, phase = 0.0;
 _SignalAddHarmonic
 ^this.primitiveFailed 
 }
 sineFill { arg amplitudes, phases;
 this.fill(0.0);
 if (phases.isNil, { phases = #[0]; });
 amplitudes.do({ arg amp, i; this.addSine(i+1, amp, phases @@ i) });
 }
 sineFill2 { arg list;
 this.fill(0.0);
 list.do({ arg item, i; 
 var harm, amp, phase;
 # harm, amp, phase = item;
 this.addSine(harm, amp, phase);
 });
 }
 
 addChebyshev { arg harmonicNumber = 1, amplitude = 1.0;
 _SignalAddChebyshev
 ^this.primitiveFailed 
 }
 chebyFill { arg amplitudes;
 this.fill(0.0);
 amplitudes.do({ arg amp, i; this.addChebyshev(i+1, amp) });
 this.normalizeTransfer
 }
 
 *fftCosTable { arg fftsize;
 ^this.newClear((fftsize/4) + 1).fftCosTable
 }
 fftCosTable {
 var harm;
 harm = this.size / ((this.size - 1) * 4);
 this.addSine(harm, 1, 0.5pi);
 }
 
 fft { arg imag, cosTable; 
   _Signal_FFT
 ^this.primitiveFailed
 }
 ifft { arg imag, cosTable;
   _Signal_IFFT
 ^this.primitiveFailed
 }
 
 neg { _Neg; ^this.primitiveFailed }
 abs { _Abs; ^this.primitiveFailed }
 sign { _Sign; ^this.primitiveFailed }
 squared { _Squared; ^this.primitiveFailed }
 cubed { _Cubed; ^this.primitiveFailed }
 sqrt { _Sqrt; ^this.primitiveFailed }
 exp { _Exp; ^this.primitiveFailed }
          log { _Log; ^this.primitiveFailed }
 log2 { _Log2; ^this.primitiveFailed }
 log10 { _Log10; ^this.primitiveFailed }
 sin { _Sin; ^this.primitiveFailed }
 cos { _Cos; ^this.primitiveFailed }
 tan { _Tan; ^this.primitiveFailed }
 asin { _ArcSin; ^this.primitiveFailed }
 acos { _ArcCos; ^this.primitiveFailed }
 atan { _ArcTan; ^this.primitiveFailed }
 sinh { _SinH; ^this.primitiveFailed }
 cosh { _CosH; ^this.primitiveFailed }
 tanh { _TanH; ^this.primitiveFailed }
 distort { _Distort; ^this.primitiveFailed }
 softclip { _SoftClip; ^this.primitiveFailed }
 nyqring { _NyqRing; ^this.primitiveFailed }
 isPositive { _IsPositive; ^this.primitiveFailed }
 isNegative { _IsNegative; ^this.primitiveFailed }
 isStrictlyPositive { _IsStrictlyPos; ^this.primitiveFailed } 
 
 + { arg aNumber; _Add; ^aNumber.performBinaryOpOnSignal('+', this) }
 - { arg aNumber; _Sub; ^aNumber.performBinaryOpOnSignal('-', this) }
 * { arg aNumber; _Mul; ^aNumber.performBinaryOpOnSignal('*', this) }
 +! { arg aNumber; _AddInPlace; ^aNumber.performBinaryOpOnSignal('+!', this) }
 -! { arg aNumber; _SubInPlace; ^aNumber.performBinaryOpOnSignal('-!', this) }
 *! { arg aNumber; _MulInPlace; ^aNumber.performBinaryOpOnSignal('*!', this) }
 / { arg aNumber; _FDiv; ^aNumber.performBinaryOpOnSignal('/', this) }
 % { arg aNumber; _Mod; ^aNumber.performBinaryOpOnSignal('%', this) }
 div { arg aNumber; _IDiv; ^aNumber.performBinaryOpOnSignal('div', this) }
 ** { arg aNumber; _Pow; ^aNumber.performBinaryOpOnSignal('**', this) }
 min { arg aNumber; _Min; ^aNumber.performBinaryOpOnSignal('min', this) } 
 max { arg aNumber; _Max; ^aNumber.performBinaryOpOnSignal('max', this) }
 ring1 { arg aNumber; _Ring1; ^aNumber.performBinaryOpOnSignal('ring1', this) }
 ring2 { arg aNumber; _Ring2; ^aNumber.performBinaryOpOnSignal('ring2', this) }
 ring3 { arg aNumber; _Ring3; ^aNumber.performBinaryOpOnSignal('ring3', this) }
 ring4 { arg aNumber; _Ring4; ^aNumber.performBinaryOpOnSignal('ring4', this) }
 difsqr { arg aNumber; _DifSqr; ^aNumber.performBinaryOpOnSignal('difsqr', this) }
 sumsqr { arg aNumber; _SumSqr; ^aNumber.performBinaryOpOnSignal('sumsqr', this) }
 sqrsum { arg aNumber; _SqrSum; ^aNumber.performBinaryOpOnSignal('sqrsum', this) }
 sqrdif { arg aNumber; _SqrDif; ^aNumber.performBinaryOpOnSignal('sqrdif', this) }
 absdif { arg aNumber; _AbsDif; ^aNumber.performBinaryOpOnSignal('absdif', this) }
 thresh { arg aNumber; _Thresh; ^aNumber.performBinaryOpOnSignal('thresh', this) }
 amclip { arg aNumber; _AMClip; ^aNumber.performBinaryOpOnSignal('amclip', this) }
 scaleneg { arg aNumber; _ScaleNeg; ^aNumber.performBinaryOpOnSignal('scaleneg', this) }
 clip2 { arg aNumber; _Clip2; ^aNumber.performBinaryOpOnSignal('clip2', this) }
 fold2 { arg aNumber; _Fold2; ^aNumber.performBinaryOpOnSignal('fold2', this) }
 wrap2 { arg aNumber; _Wrap2; ^aNumber.performBinaryOpOnSignal('wrap2', this) }
 excess { arg aNumber; _Excess; ^aNumber.performBinaryOpOnSignal('excess', this) }
 <! { arg aNumber; _FirstArg; ^aNumber.performBinaryOpOnSignal('<!', this) }
 == { arg aNumber; _EQ; ^aNumber.performBinaryOpOnSignal('==', this) }
 != { arg aNumber; _NE; ^aNumber.performBinaryOpOnSignal('!=', this) }
 
 clip { arg lo, hi; _ClipSignal; ^this.primitiveFailed }
 wrap { arg lo, hi; _WrapSignal; ^this.primitiveFailed }
 fold { arg lo, hi; _FoldSignal; ^this.primitiveFailed }
 
 asInteger { _AsInt; ^this.primitiveFailed }
 asFloat { _AsFloat; ^this.primitiveFailed }
 asComplex { ^Complex.new(this, 0.0) }
 asSignal { ^this }
 asString { _SignalString; ^this.primitiveFailed }
 
 blend { arg anotherSignal, blendFrac=0.5;
  ^(this * (1.0 - blendFrac)) + (anotherSignal * blendFrac)
 }
 
  real { ^this }
 imag { ^0.0 }
 
 libMenuAction { arg names;
 this.plot(names.last);
 }
 
 
  performBinaryOpOnSignal { arg aSelector, aNumber; ^error("Math operation failed.\n") }
 performBinaryOpOnComplex { arg aSelector, aComplex; ^aComplex.perform(aSelector, this.asComplex) }
 performBinaryOpOnSimpleNumber { arg aSelector, aSimpleNumber; ^aSimpleNumber.perform(aSelector, this) }
}
Wavetable[float] : FloatArray {
  *new { 
 ^this.shouldNotImplement(thisMethod)
 }
 *newClear { 
 ^this.shouldNotImplement(thisMethod)
 }
 
 *sineFill { arg size, amplitudes, phases;
 ^Signal.sineFill(size, amplitudes, phases).asWavetable
 }
 *chebyFill { arg size, amplitudes;
 ^Signal.chebyFill(size, amplitudes).asWavetable
 }
 
 asSignal {
 _WavetableAsSignal
 ^this.primitiveFailed 
 }

 plot { arg name, bounds;  var w;
 name = name ? "wavetable";
 bounds = bounds ? Rect.new(40,60,300,250);
 w = TableWindow.new(this, name, bounds, [ -1, 1, -20000, 20000, 1, 0 ]);
 w.tableView.autoScale; 
 ^w
 }
   overWrite { arg aWavetable, index; 
 super.overWrite(aWavetable, index);
 ^this.changed; 
 }
 play { arg name=\Wavetable;
 Instrument.play(name.asSymbol, { arg freq=200; Osc.ar(this, freq, 0, 0.2) });
 }
 blend { arg anotherWavetable, blendFrac=0.5;
 ^this.asSignal.blend(anotherWavetable.asSignal, blendFrac).asWavetable;
 }
 
 *readNew { arg file;
 ^file.readAllSignal.asWavetable;
 }
 write { arg path;
 var file;
 file = File.new(path, "wb");
 if (file.notNil, {
 file.write(this.asSignal);
 file.close;
 });
 }
 libMenuAction { arg names;
 this.plot(
 names.at(names.size - 2).asString 
 ++ ":" ++ names.last.asString
 );
 }
}
String[char] : RawArray {
 
 asSymbol { 
 _StringAsSymbol 
 ^this.primitiveFailed
 }
 
 compare { arg aString; _StringCompare }
 < { arg aString; ^this.compare(aString) < 0 }
 == { arg aString; ^this.compare(aString) == 0 }
 hash { _StringHash }
 
 asString { ^this }
 asCompileString { ^"\"" ++ this ++ "\"" }
 
 postln { _PostLine }
 post { _PostString }
 postcln { "// ".post; this.postln; }
 postc { "// ".post; this.post; }
 
 error { "ERROR:\n".post; this.postln; }
 warn { "WARNING:\n".post; this.postln }
 inform { ^this.postln }
 ++ { arg anObject; ^this prCat: anObject.asString; }
 + { arg anObject; ^this prCat: " " prCat: anObject.asString; }
 catArgs { arg ... items; ^this.catList(items) }
 scatArgs { arg ... items; ^this.scatList(items) }
 ccatArgs { arg ... items; ^this.scatList(items) }
 catList { arg list; 
  var string;
 string = this.copy;
 list.do({ arg item, i;
 string = string ++ item;
 });
 ^string
 }
 scatList { arg list; 
 var string;
 string = this.copy;
 list.do({ arg item, i;
 string = string primCat: " " ++ item;
 });
 ^string
 }
 ccatList { arg list; 
 var string;
 string = this.copy;
 list.do({ arg item, i;
 string = string primCat: ", " ++ item;
 });
 ^string
 }
 compile { ^thisProcess.interpreter.compile(this); }
 interpret { ^thisProcess.interpreter.interpret(this); } 
 interpretPrint { ^thisProcess.interpreter.interpretPrint(this); }
 *readNew { arg file;
 ^file.readAllString;
 }
 prCat { arg aString; _ArrayCat }
 
 printOn { arg stream;
 stream.putAll(this);
 }
 storeOn { arg stream;
 stream.putAll(this.asCompileString);
 }
}
LinkedListNode { 
 var <>prev, <>next, <>obj;
  
 *new { arg item;
 ^super.new.obj_(item)
 }
 remove {
 if (prev.notNil, { prev.next_(next); });
 if (next.notNil, { next.prev_(prev); });
 next = prev = nil;
 } 
}
LinkedList : SequenceableCollection {
 var head, tail, <size = 0;
 
 species { ^this.class }
 do { arg function;
 var i = 0, node;
 node = head;
 while ({ node.notNil },{
 function.value(node.obj, i);
 node = node.next;
 i = i + 1;
 });
 }
 reverseDo { arg function;
 var i, node;
 i = size - 1;
 node = tail;
 while ({ node.notNil },{
 function.value(node.obj, i);
 node = node.prev;
 i = i - 1;
 });
 }
 addFirst { arg obj;
 var node;
 node = LinkedListNode.new(obj);
 if (head.notNil, {
 node.next_(head);
 head.prev_(node);
 });
 head = node;
 if (tail.isNil, {
 tail = node;
 });
 size = size + 1;
 }
 add { arg obj;
 var node;
 node = LinkedListNode.new(obj);
 if (tail.notNil, {
 node.prev_(tail);
 tail.next_(node);
 });
 tail = node;
 if (head.isNil, {
 head = node;
 });
 size = size + 1;
 }
 remove { arg obj;
 var node;
 node = this.findNodeOfObj(obj);
 if ( node.notNil, {
 if (head == node, { head = node.next; });
 if (tail == node, { tail = node.prev; });
 node.remove;
 size = size - 1;
 });
 }
 pop { 
 var node;
 if ( tail.notNil, {
 node = tail;
 tail = node.prev;
 if (head == node, { head = nil; });
 node.remove;
 size = size - 1;
 ^node.obj
 },{
 ^nil
 });
 
 }
 popFirst { 
 var node;
 if ( head.notNil, {
 node = head;
 head = node.next;
 if (tail == node, { tail = nil; });
 node.remove;
 size = size - 1;
 ^node.obj
 },{
 ^nil
 });
 
 }
 
 first { if (head.notNil, { ^head.obj },{ ^nil }) }
 last { if (tail.notNil, { ^tail.obj },{ ^nil }) }
 
 at { arg index;
 var node;
 node = this.nodeAt(index);
 if (node.notNil, {
 ^node.obj
 },{
 ^nil
 })
 }
 put { arg index, item;
 var node;
 node = this.nodeAt(index);
 if (node.notNil, {
 node.obj_(item);
 });
 }
 removeAt { arg index;
 var node;
 node = this.nodeAt(index);
 if (node.notNil, {
 if (head == node, { head = node.next; });
 if (tail == node, { tail = node.prev; });
 node.remove;
 size = size - 1;
 ^node.obj
 },{
 ^nil
 });
 }
  nodeAt { arg index;
 var i = 0, node;
 if ( index >= 0 and: { index < size }, {
 if (index < (size - index), {
 node = head;
 while ({ i != index },{
 node = node.next;
 i = i + 1;
 });
 ^node
 },{
 i = size - 1;
 node = tail;
 while ({ i != index },{
 node = node.prev;
 i = i - 1;
 });
 ^node
 });
 },{
 this.indexOutOfRange;
 ^nil
 })
 }
 
 findNodeOfObj { arg obj;
 var node;
 node = head;
 while ({ node.notNil },{
 if (node.obj == obj, { ^node });
 node = node.next;
 });
 ^nil
 }
}
List : SequenceableCollection {
 var <>array;
 
 *new { arg size = 8; ^super.new.setCollection(Array.new(size)); }
 *newClear { arg size = 0; 
 if ( size == 0, {
 ^super.new.setCollection(Array.new(8)); 
 },{
 ^super.new.setCollection(Array.newClear(size));
 });
 }
 *copyInstance { arg aList; ^super.new.array_( aList.array.copy ) }
 *newUsing { arg anArray; 
  ^super.new.setCollection( anArray ) 
 }
 
 asArray { ^array.copy }
 copy { ^this.class.copyInstance(this) }
 copyRange { arg start, end; ^this.class.newUsing(array.copyRange(start, end)) }
 
 grow { arg sizeIncrease; array = array.grow(sizeIncrease); }
 size { ^array.size }
 dump { "List's array:\n".post; array.dump }
 
  
 at { arg i; ^array.at(i) }
 clipAt { arg i; i = i.clip(0, this.size - 1); ^array.at(i) }
 wrapAt { arg i; i = i.wrap(0, this.size - 1); ^array.at(i) }
 foldAt { arg i; i = i.fold(0, this.size - 1); ^array.at(i) }
 
 put { arg i, item; array.put(i, item) }
 clipPut { arg i, item; i = i.clip(0, this.size - 1); ^array.put(i, item) }
 wrapPut { arg i, item; i = i.wrap(0, this.size - 1); ^array.put(i, item) }
 foldPut { arg i, item; i = i.fold(0, this.size - 1); ^array.put(i, item) }
 
 add { arg item; array = array.add(item); }
 addFirst { arg item; array = array.addFirst(item); }
 insert { arg index, item; array = array.insert(index, item); }
 removeAt { arg item; ^array.removeAt(item); }
 pop { ^array.pop }
 first { if (this.size > 0, { ^array.at(0) }, { ^nil }) }
 last { if (this.size > 0, { ^array.at(this.size - 1) }, { ^nil }) }
 fill { arg item; array.fill(item) }
 
  do { arg function;
 array.do(function);
 }
 reverseDo { arg function;
 array.reverseDo(function);
 }
 doAdjacentPairs { arg function;
 var i, last;
 i = 0;
 last = this.size - 2;
 while ({ i <= last },{ 
 function.value(array.at(i), array.at(i+1), i);
 i = i + 1;
 })
 }
 
  swap { arg i,j; array.swap(i, j) }
 
 reverse { 
 ^this.class.newUsing(array.reverse);
 }
 rotate { arg n=1;
 ^this.class.newUsing(array.rotate(n));
 }
 stutter { arg n=2;
 ^this.class.newUsing(array.stutter(n));
 }
 mirror { 
 ^this.class.newUsing(array.mirror);
 }
 mirror2 { 
 ^this.class.newUsing(array.mirror2);
 }
 mirror1 { 
 ^this.class.newUsing(array.mirror1);
 }
 scramble {
 ^this.class.newUsing(array.scramble);
 }
 permute { arg nthPermutation;
 ^this.class.newUsing(array.permute(nthPermutation));
 }
 wrapExtend { arg length;
 ^this.class.newUsing(array.wrapExtend(length));
 }
 foldExtend { arg length;
 ^this.class.newUsing(array.foldExtend(length));
 }
 pyramid { arg patternType=1;  ^this.class.newUsing(array.pyramid(patternType));
 }
 lace { arg length;
 ^this.species.newUsing(array.lace(length))
 }
 slide { arg windowLength=3, stepSize=1;
 ^this.class.newUsing(array.slide(windowLength, stepSize));
 }
  setCollection { arg aColl;
 array = aColl.asArray;
 }
} 
 
SortedList : List {
 var <>function;
 
 *new { arg size = 8, function;
 function = function ? { arg a, b; a < b }
 ^super.new(size).function_(function)
 }
 add { arg item;
 var nextIndex;
 
 if ( this.isEmpty, {
 ^super.add(item);
 });
 nextIndex = this.indexForInserting(item);
 this.insert(nextIndex, item);
 } 
 addAll { arg aCollection;
 if ( aCollection.size > (this.size div: 3), {
  aCollection.do({ arg each; super.add(each) });
 this.sort
 },{  aCollection.do({ arg each; this.add(each) });
 });
 }
 
  indexForInserting { arg newObject;
 var index, low, high;
 low = 0;
 high = this.size-1;
 while ({ 
 index = high + low div: 2;
 low <= high;
 },{
 if (function.value(array.at(index), newObject), {
 low = index + 1;
 },{
 high = index - 1;
 });
 });
 ^low 
 }
 
 sort { this.sortRange(0, array.size - 1) }
 
 sortRange { arg i, j;
  
 var di, dij, dj, tt, ij, k, l, n;
  if ((n = j + 1 - i) <= 1, { ^this });  
  di = array.at(i);
 dj = array.at(j);
 if (function.value(di, dj).not, {  array.swap(i,j);
 tt = di;
 di = dj;
 dj = tt;
 });
 if ( n > 2, {  ij = (i + j) div: 2;  dij = array.at(ij);  if (function.value(di, dij), {  if (function.value(dij, dj).not, {  array.swap(j, ij);
 dij = dj;
 })
 },{  array.swap(i, ij);
 dij = di;
 });
 if ( n > 3, {    k = i;
 l = j;
 while ({
 while ({
 l = l - 1; 
 k <= l and: { function.value(dij, array.at(l)) }
 });  while ({
 k = k + 1; 
 k <= l and: { function.value(array.at(k), dij) };
 });  k <= l
 },{
 array.swap(k, l); 
 });
   this.sortRange(i, l);
 this.sortRange(k, j);
 });
 });
 }
}
Interval : Collection {
 var <>start, <>end, <>step;
 
 *new { arg start, end, step=1;
 ^super.new.start_(start).end_(end).step_(step)
 }
 
 size { ^end - start div: step + 1 }
 at { arg index; 
 var val;
 if (index < 0 or: { index >= this.size }, { ^nil });
 ^step * index + start;
 }
 do { arg function;
 forBy(start, end, step, function);
 }
 
 add { ^this.shouldNotImplement }
 put { ^this.shouldNotImplement }
 
 storeParamsOn { arg stream;
 stream.putAll("( ");
 [start, end, step].storeItemsOn(stream);
 stream.putAll(" )");
 }
}Library : Collection {
 classvar <mainLibrary;
 
 var <>dictionary;
 
 *put { arg names, item;
 if (mainLibrary.isNil, {
 mainLibrary = Library.new;
 });
 if (this.at(names).isNil, {
 this.prLibMenuPut(names);
 });
 mainLibrary.put(names, item);
 }
 *at { arg names;
 if (mainLibrary.isNil, {
 ^nil
 },{
 ^mainLibrary.at(names);
 });
 }
 *removeAt { arg names;
 if (mainLibrary.isNil, {
 ^nil
 },{
 this.prLibMenuRemoveAt(names);
 ^mainLibrary.removeAt(names);
 });
 }
 *bar { arg names;
 this.prLibMenuPut(names.add('(-'));
 }
 *new {
 ^super.new.dictionary_(IdentityDictionary.new)
 }
 
 at { arg names;
 ^this.prLibAt(0, names)
 }
 put { arg names, item;
 this.prLibPut(0, names, item);
 }
 removeAt { arg names;
 ^this.prLibRemoveAt(0, names)
 }
 
 do { arg function;
 dictionary.do(function);
 }
 add { arg assn;
 this.put(assn.key, assn.value);
 }
 remove { ^this.shouldNotImplement }
 removeFail { ^this.shouldNotImplement }
 
  prLibAt { arg pos, names;
 var lastpos, item;
 lastpos = names.size - 1;
 item = dictionary.at(names.at(pos));
 if ((pos < lastpos) and: { item.respondsTo('prLibAt') }, 
 {
 ^item.prLibAt(pos + 1, names);
 },{
 ^item
 })
 }
 prLibRemoveAt { arg pos, names;
 var lastpos, item;
 lastpos = names.size - 1;
 if (pos < lastpos, 
 {
 item = dictionary.at(names.at(pos));
 if (item.respondsTo('prLibRemoveAt'), {
 ^item.prLibRemoveAt(pos + 1, names);
 });
 },{
 ^dictionary.removeAt(names.at(pos))
 })
 }
 prLibPut { arg pos, names, item;
 var lastpos, sublib, name;
 lastpos = names.size - 1;
 name = names.at(pos);
 if (pos < lastpos, {
 sublib = dictionary.at(name);
 if (sublib.isNil, {
 sublib = Library.new;
 dictionary.put(name, sublib);
 });
 sublib.prLibPut(pos + 1, names, item);
 },{
 dictionary.put(name, item);
 });
 }
 *prLibMenuPut { arg names;
 _LibMenuPut
 ^this.primitiveFailed
 }
 *prLibMenuRemoveAt { arg names;
 _LibMenuRemoveAt
 ^this.primitiveFailed
 }
 *prLibMenuChoose { arg names;
 var item;
 item = Library.at(names);
 item.libMenuAction(names);
 }
}
Bag : Collection {
 var contents;
 
 *new { arg n=4;
 ^super.new.setDictionary(n)
 }
  includes { arg item;
 ^contents.includesKey(item)
 }
  add { arg item, count=1;
 if ( this.includes(item), {
 contents.put(item, count + contents.at(item));
 },{
 contents.put(item, count);
 });
 }
 
  at { ^this.shouldNotImplement }
 atFail { ^this.shouldNotImplement }
 put { ^this.shouldNotImplement }
  do { arg function;
 var j = 0;
 contents.associationsDo({ arg assn;
 assn.value.do({ 
 function.value(assn.key, j);
 j = j + 1;
 })
 });
 }
 
  setDictionary { arg n;
 contents = Dictionary.new(n)
 }
}Set : Collection {
 var <>array, <size=0;
 
 *new { arg n=3; ^super.new.initSet(this.sizeFor(n)) }
 *sizeFor { arg n=3; ^if (n < 0, {1}, { n * 3 div: 2 }) }
 copy { ^this.shallowCopy.array_( array.copy ) }
 
 do { arg function;
 var i=0;
 if ( size > 0, {
 array.do({ arg item;
 if (item.notNil, {
 function.value(item, i);
 i = i + 1;
 })
 })
 })
 }
 
 add { arg item;
 var index;
 if (item.isNil, { "A Set cannot contain nil.\n".error; this.halt; });
 index = this.scanFor(item);
 if ( array.at(index).isNil, { this.putCheck(index, item) });
 }
 remove { arg item;
 var index;
 index = this.scanFor(item);
 if ( array.at(index).notNil, {
 array.put(index, nil);
 size = size - 1;
 this.fixCollisionsFrom(index);
 });
 }
 
  initSet { arg n; array = Array.newClear(n); }
 putCheck { arg index, item;
 array.put(index, item);
 size = size + 1;
 this.fullCheck;
 }
 fullCheck {
 if (array.size < (size * 3 div: 2), { this.grow });
 }
 growSize { ^max( array.size, 2 ) }
 grow {
 var oldElements;
 oldElements = array;
 array = Array.newClear(array.size + this.growSize);
 size = 0;
 oldElements.do({ arg item;
 if ( item.notNil, { this.noCheckAdd(item) })
 });
 }
 noCheckAdd { arg item;
 array.put(this.scanFor(item), item);
 size = size + 1;
 }
 scanFor { arg obj;
 var i, start, end, elem;
 
 start = obj.hash % array.size;
 end = array.size;
 i = start;
 while ({ i < end }, {
 elem = array.at(i);
 if ( elem.isNil or: { elem == obj }, { ^i });
 i = i + 1;
 });
 end = start - 1;
 i = 0;
 while ({ i < end }, {
 elem = array.at(i);
 if ( elem.isNil or: { elem == obj }, { ^i });
 i = i + 1;
 });
 error("There is no free space in this set!\n");
 ^-1
 }
 
 fixCollisionsFrom { arg index; 
 var lastKeyIndex, oldIndex, newIndex, element;
 oldIndex = index;
 lastKeyIndex = array.size - 1;
 while ({
 if (oldIndex == lastKeyIndex, { oldIndex = 0 }, { oldIndex = oldIndex + 1 });
 (element = this.keyAt(oldIndex)).notNil
 },{
 newIndex = this.scanFor(element);
 if ( oldIndex != newIndex, { this.swap(oldIndex, newIndex) })
 })
 }
 swap { arg i, j; array.swap(i, j) }
 keyAt { arg index; ^array.at(index) }
 
}
IdentitySet : Set {
 scanFor { arg argKey;
 ^array.atIdentityHash(argKey)
 
 }
}Dictionary : Set {
 
 *new { arg n=3; ^super.new(n*2) }
 
  at { arg key;
 ^array.at(this.scanFor(key) + 1)
 }
 atFail { arg key, function;
 var val;
 val = array.at(this.scanFor(key) + 1);
 if ( val.isNil, { ^function.value }, { ^val });
 }
 add { arg anAssociation;
 this.put(anAssociation.key, anAssociation.value);
 }
 put { arg key, obj;
 var index, atKey;
 index = this.scanFor(key);
 array.put(index+1, obj);
 if ( array.at(index).isNil, {
 array.put(index, key);
 size = size + 1;
 if (array.size < (size * 3), { this.grow });
 });
 }
 putAll { arg aDictionary; 
 aDictionary.keysValuesDo({ arg key, value; 
 this.put(key, value) 
 }) 
 }
 associationAt { arg key;
 var index, anAssociation;
 index = this.scanFor(key);
 if (index >= 0, {
 ^Association.new(array.at(index), array.at(index+1));
 },{
 ^nil
 });
 }
 associationAtFail { arg argKey, function;
 var index, key;
 index = this.scanFor(argKey);
 key = array.at(index);
 if ( key.isNil, { ^function.value }, { 
 ^Association.new(key, array.at(index+1)) });
 }
 keys {
 var set;
 set = Set.new(size);
 this.keysDo({ arg key; set.add(key) });
 ^set
 }
 values {
 var list;
 list = List.new(size);
 this.do({ arg value; list.add(value) });
 ^list
 }
 
  includes { arg item1; 
 this.do({ arg item2; if (item1 == item2, {^true}) });
 ^false
 }
 includesKey { arg item1; 
 this.keysDo({ arg item2; if (item1 == item2, {^true}) });
 ^false
 }
 
  removeAt { arg key;
 var index, atKeyIndex, val;
 index = this.scanFor(key);
 atKeyIndex = array.at(index);
 if ( atKeyIndex.isNil, { ^nil });
 val = array.at(index+1);
 array.put(index, nil);
 array.put(index+1, nil);
 size = size - 1;
 this.fixCollisionsFrom(index);
 ^val
 } 
 removeAtFail { arg key, function;
 var index, atKeyIndex, val;
 index = this.scanFor(key);
 atKeyIndex = array.at(index);
 if ( atKeyIndex.isNil, { ^function.value });
 val = array.at(index+1);
 array.put(index, nil);
 array.put(index+1, nil);
 size = size - 1;
 this.fixCollisionsFrom(index);
 ^val
 } 
 
 remove { ^this.shouldNotImplement }
 removeFail { ^this.shouldNotImplement }
 
  keysValuesDo { arg function;
 this.keysValuesArrayDo(array, function);
 }
 do { arg function;
 this.keysValuesDo({ arg key, value, i;
 function.value(value, i);
 })
 }
 keysDo { arg function;
 this.keysValuesDo({ arg key, val, i;
 function.value(key, i);
 })
 }
 associationsDo { arg function;
 this.keysValuesDo({ arg key, val, i;
 function.value( Association.new(key, val), i);
 })
 }
 findKeyForValue { arg argValue;
 this.keysValuesArrayDo(array, { arg key, val, i;
 if (argValue == val, { ^key })
 });
 ^nil
 }
 
  transformEvent { arg event;
 ^event.putAll(this);
 }
 embedInStream { arg event;
 ^event.putAll(this).yield;
 }
 
  keysValuesArrayDo { arg argArray, function;
  var i=0, j=0, arraySize, key, val;
 arraySize = argArray.size; 
 while ({ i < arraySize },{
 key = argArray.at(i);
 if (key.notNil, {
 val = argArray.at(i+1);
 function.value(key, val, j);
 j = j + 1;
 });
 i = i + 2;
 });
 }
 *sizeFor { arg n=3; ^if (n < 0, {1}, { n * 3 }) }
 grow {
 var oldElements, index;
 oldElements = array;
 array = Array.newClear(array.size + this.growSize);
 this.keysValuesArrayDo(oldElements, 
 { arg key, val;
 index = this.scanFor(key);
 array.put(index, key);
 array.put(index+1, val);
 });
 }
 fixCollisionsFrom { arg index; 
 var lastKeyIndex, oldIndex, newIndex, key;
 oldIndex = index;
 lastKeyIndex = array.size - 2;
 while ({
 if (oldIndex == lastKeyIndex, { oldIndex = 0 }, { oldIndex = oldIndex + 2 });
 (key = array.at(oldIndex)).notNil
 },{
 newIndex = this.scanFor(key);
 if ( oldIndex != newIndex, { 
 this.swap(oldIndex, newIndex);
 this.swap(oldIndex+1, newIndex+1) 
 })
 })
 }
 scanFor { arg argKey;
 var i, start, end, key, maxHash;
 
 maxHash = array.size div: 2;
 start = (argKey.hash % maxHash) * 2;
 end = array.size-1;
 i = start;
 forBy( start, end, 2, { arg i;
 key = array.at(i);
 if ( key.isNil or: { key == argKey }, { ^i });
 });
 end = start - 1;
 forBy( 0, start-2, 2, { arg i;
 key = array.at(i);
 if ( key.isNil or: { key == argKey }, { ^i });
 });
 ^-2
 }
 
 storeItemsOn { arg stream, itemsPerLine = 5;
 var last, itemsPerLinem1;
 itemsPerLinem1 = itemsPerLine - 1;
 last = this.size - 1;
 this.associationsDo({ arg item, i;
 item.storeOn(stream);
 if (i < last, { stream.comma.space;
 if (i % itemsPerLine == itemsPerLinem1, { stream.nl.space.space });
 });
 });
 }
 printItemsOn { arg stream, itemsPerLine = 5;
 var last, itemsPerLinem1;
 itemsPerLinem1 = itemsPerLine - 1;
 last = this.size - 1;
 this.associationsDo({ arg item, i;
 item.printOn(stream);
 if (i < last, { stream.comma.space;
 if (i % itemsPerLine == itemsPerLinem1, { stream.nl.space.space });
 });
 });
 }
}
IdentityDictionary : Dictionary {
 at { arg key, value;
 _IdentDict_At
 ^this.primitiveFailed
 
 }
 put { arg key, value;
 _IdentDict_Put
 ^this.primitiveFailed
 
 }
 putGet { arg key, value;
 _IdentDict_PutGet
 ^this.primitiveFailed
 
 }
 findKeyForValue { arg argValue;
 this.keysValuesArrayDo(array, { arg key, val, i;
 if (argValue === val, { ^key })
 });
 ^nil
 }
 scanFor { arg argKey;
 ^array.atIdentityHashInPairs(argKey)

 }
}
Orchestra : IdentityDictionary
{
 add { arg anInstrument;
 this.put(anInstrument.name, anInstrument);
 }
}

Environment : IdentityDictionary {
 *new { arg size=8; ^super.new(size) }
 *make { arg function;
 ^this.new.make(function)
 }
 *use { arg function;
 ^this.new.use(function)
 }
 make { arg function;
   var result, saveEnvir;
 
 saveEnvir = currentEnvironment;
 currentEnvironment = this;
 function.value(this);
 currentEnvironment = saveEnvir;
 }
 use { arg function;
   var result, saveEnvir;
 
 saveEnvir = currentEnvironment;
 currentEnvironment = this;
 result = function.value(this);
 currentEnvironment = saveEnvir;
 ^result
 }
 
 eventAt { arg key; ^this.at(key) } 
}

Event : Environment {
 finishEvent {
  this.use( this.at('finish') );
 }
 delta {
 _Event_Delta
 ^this.primitiveFailed;
 
 }
 *protoEvent { 
 ^this.make({
 
  
 ~reverse = false;
 
 ~tempo = nil;  ~dur = 1.0;
 ~stretch = 1.0;
 ~legato = 0.8;
 ~sustain = { ~dur * ~legato * ~stretch };
 ~lag = 0.0;
 ~strum = 0.0;
 
 ~amp = { ~db.dbamp };
 ~db = -20.0;
 ~velocity = 64;  ~pan = 0.0;  ~channels = 2;
 
 ~mtranspose = 0;
 ~gtranspose = 0.0;
 ~ctranspose = 0.0;
 
 ~octave = 5.0;
 ~root = 0.0;  ~degree = 0;
 ~scale = #[0, 2, 4, 5, 7, 9, 11];  ~stepsPerOctave = 12.0;
 ~detune = 0.0;  
 ~note = {
 (~degree + ~mtranspose).degreeToKey(~scale, ~stepsPerOctave);
 };
 ~midinote = {
 var divs; 
 divs = ~stepsPerOctave;
 (~note.value + ~gtranspose + (~octave * divs) + ~root)
 * 12.0 / divs; 
 };
 ~freq = {
 (~midinote.value + ~ctranspose).midicps;
 };
 
 ~env = Env.asr(0.01, 1.0, 0.5);
 
 ~chanOffset = 0;
 ~instrument = \default;
 ~wavetable = Wavetable.sineFill(1024, 1/[1,2,3,4,5,6]);
 ~orchestra = 
 Orchestra[
 Instrument.new(\default, 
 { arg freq, amp, pan, env, wavetable; 
 Pan2.ar(
 Osc.ar(wavetable, freq, 0, EnvGen.kr(env, amp)),
 pan
 );
 }),
 Instrument.new(\sine, 
 { arg freq, amp, pan, env; 
 Pan2.ar(
 SinOsc.ar(freq, 0, EnvGen.kr(env, amp)),
 pan
 );
 })
 ];
 ~ugenFunc = { 
 ~orchestra.at(~instrument).func.valueEnvir; 
 };
 
 ~finish = {
  ~freq = ~freq.value + ~detune;
 ~amp = ~amp.value;
 ~sustain = ~sustain.value; 
 };
 });
 }
}


TablesBank : IdentityDictionary { 
 var <tableNames =  #[ 'fundFreq', 'pulseMask', 'pulsaret', 'pulseEnv', 
 'formFreq1', 'formFreq2', 'formFreq3', 
 'pan1', 'pan2', 'pan3', 
 'amp1', 'amp2', 'amp3' ]; 
 
 var <>panel, <>currentTableWindows, maxSize = 18, 
 <defaultTables, <savesTablesAsSet = true;
 
 var <previousTableSet; 
 
 var <bankFileNames; 
 
 var lastStringEndNumber = 0;  
 *new { ^super.new.init }
 
 readBankFilenames { 
 var bankLibFile; 
 bankLibFile = TextFile.read("BanksLibrary.txt");
 if (bankLibFile.notNil,
 { bankFileNames = bankLibFile.text; 
 bankFileNames.do({ arg eachPath; 
 this.addLoadableBank(eachPath);
 });
 }
 );
 } 
 
 addLoadableBank { arg bankPath; 
 var bankName, bankFile;
 bankName = PathName(bankPath).fileName.asSymbol;
 bankFile = TextFile.read(bankPath); 
 if (bankFile.notNil, {
 Library.put([ 'Load Preset Bank', bankName ], 
 { if (Synth.isOn, 
 { MessageWindow("Can't load tables while synth is on."); }, 
 { this.loadBankOfTableSets(bankFile.text); 
 this.addBankToLib(bankName);
 }
 );
 }); 
 }
 );
 }
 
 init { 
 var startupTableSet, currentTables;
 
 defaultTables = IdentityDictionary[ 
 'fundFreq' -> Signal.newClear(2048).fill(10).asWavetable, 
 'pulseMask' -> Signal.newClear(2048).fill(1).asWavetable, 
 'pulsaret' -> Wavetable.sineFill(2048, [1]), 
 'pulseEnv' -> Signal.newClear(2048).fill(1).asWavetable, 
 'formFreq1' -> Signal.newClear(2048).fill(80).asWavetable, 
 'formFreq2' -> Signal.newClear(2048).fill(600).asWavetable, 
 'formFreq3' -> Signal.newClear(2048).fill(1200).asWavetable, 
 'pan1' -> Signal.newClear(2048).asWavetable, 
 'pan2' -> Signal.newClear(2048).asWavetable, 
 'pan3' -> Signal.newClear(2048).asWavetable, 
 'amp1' -> Signal.newClear(2048).fill(0.5).asWavetable, 
 'amp2' -> Signal.newClear(2048).fill(0.5).asWavetable, 
 'amp3' -> Signal.newClear(2048).fill(0.5).asWavetable,
 \settings -> SettingsLib.defaultSettings
 ]; 
 
  startupTableSet = this.loadTablesOrSet(PG.startupTableSet); 
 currentTables = if (
 startupTableSet.isEmpty, 
 defaultTables, 
 startupTableSet).deepCopy;
 

 Library.put([ 'Load Preset Bank', 'DemoTables' ], 
 { if (Synth.isOn, 
 { MessageWindow("Can't Load DemoTables while Synth is on."); }, 
 { this.loadBankOfTableSets(PG.demoTablesBank); 
 this.addBankToLib('DemoTables');
 }
 );
 }); 
 Library.put(['Table Clipboard'], { TableView.plotClipboard });
 this.putInMainLib('defaultTables', defaultTables); 
 this.putInMainLib('currentTables', currentTables); 
 Library.bar([ ]);
 
 } 
 
 putInMainLib { arg key, tableSet; 
  tableNames  .do({ arg eachKey; 
 
 if (tableSet.at(eachKey).isNil, 
 { ("TableSet " ++ key.asString ++ ", " ++ 
 eachKey.asString ++ " replaced with default.").postln; 
 tableSet.put(eachKey, defaultTables.at(eachKey).copy); }
 )
 });
 
 this.put(key, tableSet); 
 if (key != 'currentTables', {
 tableNames.do({ arg eachKey;
 Library.put([key, eachKey], tableSet.at(eachKey) );
 }); 
 });
 
 if ( panel.notNil and: ( this.isReservedSetName(key).not ), 
 { panel.addName(key, 0); } 
 ); 
 } 
 
 removeAtMainLib { arg key; 
 this.removeAt(key); 
 Library.removeAt([key]);
 if (panel.notNil, { panel.removeName(key); });
 } 
 
 addBankToLib { arg bankKey; 
 var thisTableSet;
 
 panel.name = "Bank : " ++ bankKey.asString; 
 
 this.keys.do({ arg eachSetKey;       
 if (this.isReservedSetName(eachSetKey).not,
 { 
 thisTableSet = this.at(eachSetKey);
  
  
 }
 ); 
 });
 }
 
 clearFrontLib { 
 this.keys.copy.do({ arg eachKey; 
 if (this.isReservedSetName(eachKey).not, 
 { this.removeAtMainLib(eachKey) }
 );
 });
 panel.name = "Tables Bank";
 }
 
 orderedAt { arg key; var thisSet;
 thisSet = this.at(key);
 ^tableNames.collect({ arg eachKey; thisSet.at(eachKey); });
 } 
 
 nameIsFree { arg name; ^this.keys.includes(name.asSymbol).not; }
 
 hasFreeSpace { ^(this.size < maxSize) }
 
 makeCurrent { arg setKey; 
 if (PG.xFadeIsLocked, { panel.xFadeRefused; ^this }); 
 previousTableSet = this.at(\currentTables); 
 panel.newCurrentSet(setKey);
 
 
 this.copyAndAddSet(
 \currentTables, 
 this.at(setKey)
 );
 }
 
 restorePreviousSet { 
 var nextCurrentSet;
 
 if (previousTableSet.isNil, { ^this });
 if (PG.xFadeIsLocked, { panel.xFadeRefused; ^this }); 
 
 nextCurrentSet = previousTableSet;
 previousTableSet = this.at(\currentTables); 
 this.copyAndAddSet(\currentTables, nextCurrentSet);
 }
 
 revertToDefaultTables { this.makeCurrent('defaultTables'); }
 
 addCurrentSet {
 var newSettings, window; 
 newSettings = PG.settingsLib.getState.copy; 
 this.at(\currentTables).put(\settings, newSettings);
 
 if (this.hasFreeSpace.not, { 
 window = ChoiceWindow("Sorry, Bank is full!", 
 [ [ "Remove Tables Now?", { this.openRemoveWindow; window.close; } ] ]
 ); 
 ^this; 
 });
 
 GetStringDialog.new("Please name set of Tables:", "New Set", 
 { arg ok, newName; 
 
 if (ok, { 
 
 if (this.nameIsFree(newName.asSymbol).not, 
 { newName = this.autoNameSet(newName); });
 
 this.copyAndAddSet(
 newName.asSymbol, 
 this.at('currentTables');
 );
 });
 }
 );
 }
 
 copyAndAddSet { arg name, newSet; 
   var newTasks, copiedSet; 
 
 copiedSet = IdentityDictionary.new;
 newTasks = List.new; 
 
 newSet.keysValuesDo({ arg key, table; 
 newTasks.add(
 { copiedSet.put(key, table.copy); }
 );
 });
 newTasks.add({ this.putInMainLib( name, copiedSet ); }); 
 
 if ('currentTables' == name, 
 {  newTasks.add({ PG.newCurrentTables }); }
 );
 
 PG.addTasks(newTasks, 0.03);
 PG.doTasks;
 }
 
 negotiateNewSetName { arg newSetKey, newSet;
 MessageWindow("Sorry, Name Exists! Method Not done yet...")
 }
 
 
 saveTablesWDialog { 
 
 if (Synth.isOn, { MessageWindow.new("Sorry, no saving while Synth is on."); ^this; } );
 
 if (this.savesTablesAsSet, 
 { ^this.saveTablesAsSignalDialog(\currentTables); }, 
 { ^this.saveInFolderDialog(\currentTables); }
 );
 }
 
 saveInFolderDialog { 
 var folderName, fileName = "fundFreq", file, fullPath; 
 var numExistingTables = 0; 
 PutFileDialog.new(
 "Pick/make folder for tables.", 
 fileName, 
 { arg ok, path;
 if (ok, { 
 
 folderName = PathName(path).pathOnly; 
 
  tableNames.do({ arg eachName; 
 
 fullPath = folderName ++ eachName.asString ++ ".wt"; 
 
 file = File.new(fullPath, "rb");
 if (file.isOpen, 
 { numExistingTables = numExistingTables + 1; 
 file.close; 
 }); 
 }); 
 
 if (numExistingTables == 0, 
 { this.saveTables(folderName); },
 { ChoiceWindow.new("Tables are already there!", 
 [ [ "Pick New Folder", { arg btn; this.saveTablesWDialog; btn.window.close; } ],
 [ "Overwrite", { arg btn; this.saveTables(folderName); btn.window.close; } ],
 [ "Cancel", { arg btn; btn.window.close; } ] 
 ]
 ).views.at(1).focus; 
 }
 );
 });
 }
 ); 
 }
 saveTables { arg pathWithoutFilename; 
 var tablesToSave; 
 tablesToSave = this.at('currentTables');
 
 if (Synth.isOn, {}); 
 tableNames.do({ arg eachName; 
 tablesToSave.at(eachName)
 .write(pathWithoutFilename ++ eachName.asString ++ ".wt"); 
 }
 ); 
 
 PG.saveSettings(pathWithoutFilename);
 this.copyAndAddSet(
 PathName(pathWithoutFilename).folderName.asSymbol, 
 tablesToSave
 );
 } 
 
 saveTablesAsSignalDialog { arg setKey;
 var newSetName;
 PutFileDialog("Save currentTables as:", 
 setKey.asString, 
 { arg ok, pathName; 
 if (ok, { 
 newSetName = PathName(pathName).fileName.asSymbol;
 this.saveTableSetAsSignal(setKey, pathName ++ ".set"); 
 this.copyAndAddSet(newSetName, this.at(setKey) );
 });
 }
 ); 
 }
 
 convertTablesToSignal { arg tableSet; 
 var orderedSetOfTables, tableSetAsSignal;
 var blankHeader, settings, paddingSize;
 
 orderedSetOfTables = 
 this.tableNames.collect(
 { arg eachTName; tableSet.at(eachTName) }
 );
  blankHeader = Signal.newClear(256);
  settings = tableSet.at(\settings) ? SettingsLib.defaultSettings; 
 paddingSize = 256 - settings.size;
 if (paddingSize > 0, {  settings = ( settings ++ Signal.newClear(paddingSize) ); });
 
 tableSetAsSignal = (blankHeader ++ settings);
 
 orderedSetOfTables.do({ arg eachTable, i; 
 tableSetAsSignal = tableSetAsSignal ++ eachTable.asSignal;
 });
 ^tableSetAsSignal; 
 }
 saveTableSetAsSignal { arg setKey, pathName; 
 var tableSetAsSignal;
 tableSetAsSignal = this.convertTablesToSignal(this.at(setKey)); 
 tableSetAsSignal.write(pathName);
 ^pathName
 }
 
 saveIndividualTables { savesTablesAsSet = false; }
 saveTablesAsSet { savesTablesAsSet = true; }
 
 readTablesSignal { arg path;
 var tablesAsSignal, newSettings, newTableSet;
 var tableStart, tablesOffset = 512; 
 newTableSet = IdentityDictionary.new; 
 
 tablesAsSignal = Signal.read(path);
 if (tablesAsSignal.isNil, 
 { ("SetFile " ++ path ++ " not found.").postln; ^nil });
 
 newSettings = tablesAsSignal.copyRange(256, 511); 
 
 newTableSet.put(\settings, newSettings);
 
 tableNames.do({ arg eachName, i;
 tableStart = i * 2048 + tablesOffset;
 newTableSet.put(
 eachName, 
 tablesAsSignal.copyRange( tableStart, tableStart + 2047 ).asWavetable
 )
 }); 
 ^newTableSet; 
 }
 
 saveBankDialog { arg nextAction;
 var folderName, listOfPaths, pathName, pathToFolder, savePath, bankFileName;
 if (Synth.isOn, { MessageWindow("Sorry, no bank save while playing."); ^this });
 PutFileDialog("Make/Pick folder for all sets.", 
 "new.bank",
 { arg ok, path; 
 if (ok, { 
 pathName = PathName(path);
 folderName = pathName.folderName; 
 pathToFolder = pathName.pathOnly;
 
 listOfPaths = List.new; 
 panel.setSwitches.do({ 
 arg eachSw; 
 if ( ( eachSw.label == " " ).not, {  [ "eachSw.label:", eachSw.label ].postln; 
 
 savePath = pathToFolder ++ eachSw.label ++ ".set";
 this.saveTableSetAsSignal(eachSw.label.asSymbol, savePath);
 listOfPaths.add(savePath).postln; 
 });
 }); 
 
 bankFileName = if (
 path.copyRange(path.size - 4, path.size - 1) == "bank", 
 path, 
 { bankFileName = path ++ ".bank"; }
 );
 TextFile.save(bankFileName, listOfPaths);
 
 nextAction.value;
 })
 }
 );
 }
  loadBankSafely {
 var window;
 if (Synth.isOn, { MessageWindow("Sorry, no bank load while playing."); ^this });
  if (this.keys.size < 3, { ^this.loadBankDialog; });
 
 window = ChoiceWindow.new(" What About the Current Bank? ",
 [
 [ "SAVE Current Bank Then Load", 
 { this.saveBankDialog(nextAction: { this.loadBankDialog; }); } 
 ], 
 [ "DELETE Current Bank Then Load", 
 { this.clearFrontLib; 
 window.close;
 this.loadBankDialog;
 } ],
 [ "CANCEL Loading", { window.close; } ]
 ]
 ); 
 }
 
 loadBankDialog {
 var folderName, listOfPaths;
 GetFileDialog({ arg ok, path; 
 if (ok, { 
 if ( (path.copyRange(path.size - 4, path.size - 1) == "bank").not, 
 { MessageWindow(["Are you sure this is a bank file?", 
 "If so, please rename it to end with '.bank'." ]); 
 },
 { folderName = PathName(path).folderName;
 listOfPaths = TextFile.read(path).text; 
 this.loadBankOfTableSets(listOfPaths, folderName.asSymbol);
 }
 );
 }); 
 });
 }
 
 loadBankOfTableSets { arg listofPaths, bankKey; 
 var newSetToAdd, newSetName, tableSetsToPreload; 
 
 PG.canStart = false;
  this.clearFrontLib; 
  tableSetsToPreload = listofPaths.reject({ arg path; path == ""; }); 
 
 tableSetsToPreload.do({ arg path; 
 
 if (path.copyRange(path.size - 3, path.size - 1) == "set", 
 {  newSetToAdd = this.readTablesSignal(path);
 newSetName = PathName(path).fileName; 
 newSetName = newSetName.copyRange(0, newSetName.size - 5).postln; 
 }, 
 {  newSetToAdd = this.loadTables(path); 
 newSetName = PathName(path).folderName.postln; 
 }
 );
 
 if (newSetToAdd.isNil, 
 { "tables/Set " ++ path ++ " not found.".postln; },
 {
 if (this.nameIsFree(newSetName).not, 
 { newSetName = this.autoNameSet(newSetName) }
 );
 this.putInMainLib(newSetName.asSymbol, newSetToAdd);
  }
 );
 });
 PG.canStart = true;
 }
 
 changeableSetNames { 
 ^this.keys.reject({ arg eachKey; this.isReservedSetName(eachKey); });
 }
 
 isReservedSetName { arg testKey;
 ^[ 'currentTables', 'defaultTables' ].includes(testKey) 
 }
 
 openRemoveWindow { 
 var removeWindow, removableSetKeys, wName = "Remove Which?", buttonLabelsActions;
 
 removableSetKeys = this.changeableSetNames;
 
 if (removableSetKeys.size == 0, { MessageWindow("None To Remove!"); ^this });
 
 buttonLabelsActions = 
 [ [ "REMOVE ALL!", 
 { this.clearFrontLib; removeWindow.close; } ] ]
 ++ removableSetKeys.collect(
 { arg eachKey, i;
 [ eachKey.asString, { arg btn; this.removeTables(eachKey); 
 btn.backColor_(Color.guiGrey).prSetBorderStyle(1); } 
 ] 
 }); 
 
 removeWindow = ChoiceWindow.new(wName, buttonLabelsActions);
 }
 removeTables { arg setKey; this.removeAtMainLib(setKey); }
 
 openRenameWindow {  var renameWindow, renameableSetKeys, wName = "Rename Which?", buttonLabelsActions;
 
 renameableSetKeys = this.changeableSetNames;
 if (renameableSetKeys.size == 0, { MessageWindow("None To Rename!"); ^this });
 
 buttonLabelsActions = renameableSetKeys.collect(
 { arg eachKey; 
 [ eachKey, { arg btn; this.renameTables(eachKey, renameWindow) } ] 
 }); 
 
 renameWindow = ChoiceWindow.new(wName, buttonLabelsActions);
 }
 
 renameTables { arg setKey, renameWindow; 
 var setToBeRenamed;
 GetStringDialog.new(
 "Enter new name for: ", 
 setKey.asString, 
 { arg ok, newName; 
 
 if (ok.not, { ^this }); 
 if (this.keys.includes(newName.asSymbol), 
 { MessageWindow("Name exists!"); ^this });
 setToBeRenamed = this.at(setKey); 
 this.removeAtMainLib(setKey);
 this.putInMainLib(newName.asSymbol, setToBeRenamed);
 if (renameWindow.notNil, { renameWindow.close; });
 }
 );
 }
 
 changeTable { arg names, table;  var setKey, tableKey; 
 #setKey, tableKey = names; 
 this.at(setKey).put(tableKey, table);
 Library.put(names, table);
 } 
 
 loadTablesDialog { 
 if (Synth.isOn, { MessageWindow.new("Sorry, no loading while Synth is on."); ^this; });
 
 GetFileDialog.new({ arg ok, path; 
 if (ok, { this.loadTablesOrSet(path); });
 });
 }
 
 loadTablesOrSet { arg path;
 var tableSetName, newSetKey, newSet, fileName, fileEnding; 
 
 fileName = PathName(path).fileName;
 fileEnding = fileName.copyRange(fileName.size - 3, fileName.size - 1);
 
 if (fileEnding == "set", 
 { newSet = this.readTablesSignal(path); 
 newSetKey = fileName.copyRange(0, fileName.size - 5); 
 }, 
 { newSet = this.loadTables(path);
 newSetKey = PathName(path).folderName;
 }
 ); 
 
 if (newSet.isNil, { ("New Set at " ++ path ++ " not found.").postln; ^[ ]; });
 
  newSetKey = newSetKey.reject({ arg each; each == $! }).asSymbol; 
 
 if (this.nameIsFree(newSetKey), 
 { this.putInMainLib(newSetKey, newSet) }, 
 { newSetKey = this.autoNameSet(newSetKey); 
 this.putInMainLib(newSetKey, newSet); 
 }
 ); 
 if (panel.isNil, { "early exit during startup only."; ^newSet });
 panel.setSwitchTo(newSetKey.asString, 1);  ^this.makeCurrent(newSetKey); 
 }
 
 loadTables { arg path; 
 var myPathName, newSet, pathWithoutFile, folderName, newSettings; 
 var numberOfFailedReads = 0, fileName, fileEnd; 
 
 myPathName = PathName(path);
 pathWithoutFile = myPathName.pathOnly; 
 folderName = myPathName.folderName; 
 
 newSet = IdentityDictionary.new; 
 
 tableNames.do({ arg eachTName; var newTable; 
 
 newTable = Wavetable.read(pathWithoutFile ++ eachTName.asString ++ ".wt"); 
 if (newTable.notNil, { newSet.put(eachTName, newTable); }, 
 { numberOfFailedReads = numberOfFailedReads + 1; 
 ("WARNING: Wavetable " ++ path ++ " could not be read.").postln; 
 }
 ); 
 }); 
 
  newSettings = PG.loadSettings(pathWithoutFile);
 if ( newSettings.notNil, { newSet.put(\settings, newSettings) } );
 
 if (numberOfFailedReads >= 13, 
 { MessageWindow(
 [ "Error: You may have selected the wrong file."
 "Because the file you chose was not a tableset file (.set)", 
 "PG looked for individual tables in folder " ++ folderName, 
 "and found none.", 
 "To load a set of tables, please make sure you pick either ", 
 "a .set file, or any table within a folder that contains ", 
 "individual tables named fundfreq.wt, amp1.wt, etc." 
 ]
 );
 ^nil }); 
 if (numberOfFailedReads > 0, 
 { MessageWindow(
 [ "TableSet " ++ folderName ++ " is incomplete.", 
 "The missing tables will be", "filled with default tables." ]);
 }, 
 { ("TableSet " ++ folderName ++ " OK.").postln; }
 );
 
 ^newSet;
 
 }
 
 autoNameSet { arg existingKey;  
 var string, testKey; 
 var shortString, endNumber; 
 string = existingKey.asString;
 
 shortString = PathName(string).noEndNumbers;
 endNumber = PathName(string).endNumber;
 
 testKey = ( shortString ++ (lastStringEndNumber + 1).asString ).asSymbol;
 
    if ( this.keys.includes(testKey).not, 
 { lastStringEndNumber = lastStringEndNumber + 1; 
 endNumber = lastStringEndNumber; 
 ^testKey },
 { endNumber = lastStringEndNumber; }  );
 while (  { tableNames.includes(testKey) }, 
 { endNumber = endNumber + 1; 
 testKey = ( shortString ++ endNumber ).asSymbol; 
 }
 ); 
 
 lastStringEndNumber = endNumber;  
 ^testKey; 
 }
}
SettingsLib : IdentityDictionary { 
        
        
   
 classvar <settingsName = "PulsarGenSettings.sig"; 
 classvar <memorableSettings, defaultSettings; 
 
 var <saveables, switches, linkedLevels, morphables, steppables;
 var currentState, targetLevels, newState;
 *new { ^super.new.init; }
 init { 
 
 saveables = IdentityDictionary.new(16); 
 currentState = Signal.newClear(256).fill(-99); 
 this.getState; 
 newState = Signal.newClear(256).fill(-99); 
 
 switches = [ 1, 2, 3, 21 ]; 
 linkedLevels = [ 11, 12, 13, 23 ]; 
 morphables = [ 11, 12, 13, 31, 41 ]; 
 steppables = [ 22, 23 ]; 
 ^this;
 }
 
 *defaultSettings { 
 if (defaultSettings.isNil, 
 { defaultSettings = Signal.newClear(256).fill(-99); 
 [ [ 1, 1], [ 2, 1 ], [ 3, 1 ], 
 [ 11, 3 ], [ 12, 3 ], [ 13, 3 ],
 [ 21, 0], [ 22, 2 ], [ 23, 1 ], 
 [ 31, 10 ], [ 41, 5 ], [ 51, 0 ]
 ].do(
 { arg eachPair; 
 var index, value; 
 #index, value = eachPair;
 defaultSettings.put(index, value);
 });
 }
 );
 ^defaultSettings;
 }
 randomState { var newState; 
 newState = Signal.newClear(256).fill(-99); 
 
  switches.do({ arg i; newState.put(i, [ 0, 1 ].choose); });
 
  [ 11, 12, 13, 41 ].do({ arg i; newState.put(i, rrand(0.0, 10.0)); }); 
 
  newState.put(22, linrand(50)); 
 newState.put(23, rand(newState.at(22))); 
 
  newState.put(31, 10 + 10.bilinrand );
 
 ^newState; 
 } 
 
 putSaveable { arg index, item; saveables.put(index, item); }
 removeSaveableAt { arg index; ^saveables.removeAt(index); }
 getState { 
 saveables.keysValuesDo(
 { arg indexKey, guiItem;
 currentState.put(indexKey, guiItem.poll); 
 }
 );
 ^currentState;
 }
 
 saveState { arg stateSig, pathWithoutFile; 
 currentState.write(pathWithoutFile ++ settingsName); 
 }
 
 readState { arg pathWithoutFile; var newState;
 newState = Signal.read(pathWithoutFile ++ settingsName);
 ^newState; 
 }
 
 setState { arg inState; 
 if (inState.isNil, { ^this });
 newState = inState;
 saveables.keysValuesDo(
 { arg indexKey, guiItem; 
 guiItem.value_(newState.at(indexKey));
 guiItem.doAction;
 }
 ); 
 ^this; 
 } 
        
 xFadeTo { arg inState, fadeTime = 2;
 
 var updateInterval = 0.1, numSteps, increments, currentVal; 
 var settingsToMorphTo;
 
 if (inState.isNil, { ^this });
 this.getState; 
 this.makeStartAndTargetLevels(inState); 
 settingsToMorphTo = this.makeStartAndTargetLevels(inState);
 
 numSteps = (fadeTime.round(updateInterval) / updateInterval - 1).max(1).asInteger; 
 
 increments = morphables.collect({ arg eachI;
 settingsToMorphTo.at(eachI) - currentState.at(eachI) / numSteps;
 }); 
 this.morphSteppables(settingsToMorphTo, inState, fadeTime);
 
 thisSynth.repeatN(updateInterval, updateInterval, numSteps,
 { morphables.do({ arg eachI, i; 
 
 currentState.put(eachI, 
 (currentState.at(eachI) + increments.at(i)) ); 
 
 saveables.at(eachI).value_(currentState.at(eachI)).doAction;
 }); 
 morphables;
 }, 
 {  switches.do({ arg i; 
 saveables.at(i).value_(inState.at(i))
 .doAction; 
 }); 
 
 morphables.do({ arg i; 
 saveables.at(i).value_(inState.at(i))
 .doAction; 
 });
 }
 );
 } 
  morphSteppables { arg stateToMorphTo, finalState, fadeTime; 
 steppables.do({ arg index;
 var updateInterval, difference, numSteps, increment; 
 difference = (stateToMorphTo.at(index) - currentState.at(index) ); 
 numSteps = difference.abs.asInteger; 
 increment = difference.sign;
 
  
 updateInterval = (fadeTime / numSteps); 
 
   if (fadeTime <= 0.1, 
 {  this.setState(finalState);
 }, 
 { if (updateInterval < 0.1, 
 { updateInterval = 0.1;
 
 numSteps = (fadeTime / updateInterval).ceil.asInteger; 
 updateInterval = fadeTime / numSteps;
 increment = difference / numSteps; 
 }
 )
 });
 thisSynth.repeatN(updateInterval, updateInterval, numSteps,
 { currentState.put(index, currentState.at(index) + increment ); 
 saveables.at(index).value_(currentState.at(index)).doAction;
 }, 
 {  saveables.at(index).value_(finalState.at(index)).doAction; 
 }
 );
 });
 }
 makeStartAndTargetLevels { arg newState;  var intermediateTargets;
 intermediateTargets = newState.copy; 
 [ switches, linkedLevels ].flop.do(
 { arg eachSandL; 
 var swI, levI, switch, level; 
 
 #swI, levI = eachSandL; 
 switch = saveables.at(swI);
 level = saveables.at(levI); 
 
   
 if ( [ currentState.at(swI), newState.at(swI) ] == [ 0, 1 ], 
 {  saveables.at(levI).value_(0.0); 
 saveables.at(swI).value_(1.0); 
 
 currentState.put(levI, 0.0); 
 });
 if ( [ currentState.at(swI), newState.at(swI) ] == [ 1, 0 ], 
 { 

 intermediateTargets.put(levI, 0.0);
 });
 }); 
 ^intermediateTargets;
 }
}

TwoWayIdentityDictionary : Collection
{
 var idToObj, objToID;
 
 *new {
 ^super.new.init;
 }
 
 add { arg anAssociation;
 this.put(anAssociation.key, anAssociation.value);
 }
 put { arg key, obj;
 idToObj.put(key, obj);
 objToID.put(obj, key);
 }
 
 remove { arg obj;
 var key;
 key = this.getID(obj);
 idToObj.remove(key, obj);
 objToID.remove(obj, key);
 }
 
 do { arg function;
 ^idToObj.do(function);
 }
 
 at { arg id;
 ^idToObj.at(id);
 }
 getID { arg obj;
 ^objToID.at(obj);
 }
 
  init {
 idToObj = IdentityDictionary.new;
 objToID = IdentityDictionary.new;
 }
}
ObjectTable : TwoWayIdentityDictionary
{
 classvar mainObjectTable;
 
 var nextID=0;
 
 *new {
 ^super.new;
 }
 
 add { arg obj;
 this.put(nextID, obj);
 nextID = nextID + 1;
 }
 
 *init {
 mainObjectTable = this.new;
 }
 *add { arg obj;
 mainObjectTable.add(obj);
 }
 *put { arg key, obj;
 mainObjectTable.put(key, obj);
 }
 *at { arg id;
 ^mainObjectTable.at(id);
 }
 *getID { arg obj;
 ^mainObjectTable.getID(obj);
 }
}
Pair : Collection { var <>linkDown, <>linkAcross; 
  size { var i = 0, link;
 link = linkAcross;
 while ({ link.respondsTo('linkAcross') },{
 i = i + 1;
 link = link.linkAcross;
 });
 ^i
 }
 depth { var i = 0, link;
 link = linkDown;
 while ({ link.respondsTo('linkDown') },{
 i = i + 1;
 link = link.linkDown;
 });
 ^i
 }
 do { arg function;
 var i = 0, link, res;
 link = linkAcross;
 while ({ link.respondsTo('linkAcross') },{
 i = i + 1;
 res = function.value(link, i);
 link = link.linkAcross;
 });
 ^res
 }
 
 traverse { arg function;
  ^this.depthFirstPreOrderTraversal(function)
 }
 depthFirstPreOrderTraversal { arg function;
 var link;
 function.value(this);
 if ( linkDown.respondsTo('depthFirstPreOrderTraversal'), {
 linkDown.depthFirstPreOrderTraversal(function);
 });
  link = linkAcross;
 while ({ link.notNil },{
 function.value(link);
 if ( link.linkDown.respondsTo('depthFirstPreOrderTraversal'), {
 link.linkDown.depthFirstPreOrderTraversal(function);
 });
 link = link.linkAcross;
 });
 }
 depthFirstPostOrderTraversal { arg function;
 var link;
 if ( linkDown.respondsTo('depthFirstPostOrderTraversal'), {
 linkDown.depthFirstPostOrderTraversal(function);
 });
 function.value(this);
  link = linkAcross;
 while ({ link.notNil },{
 if ( link.linkDown.respondsTo('depthFirstPostOrderTraversal'), {
 link.linkDown.depthFirstPostOrderTraversal(function);
 });
 function.value(link);
 link = link.linkAcross;
 });
 }
 
}
AbstractFunction {
 
   composeUnaryOp { arg aSelector;
 ^{ this.value.perform(aSelector) }
 }
 composeBinaryOp { arg aSelector, function;
 ^{ this.value.perform(aSelector, function.value) }
 }
 reverseComposeBinaryOp { arg aSelector, something;
 ^{ something.value.perform(aSelector, this.value) }
 }
 composeNAryOp { arg aSelector, anArgList;
 ^{ this.value.performList(aSelector, anArgList) }
 }
  performBinaryOpOnSimpleNumber { arg aSelector, aNumber; 
 ^this.reverseComposeBinaryOp(aSelector, aNumber) 
 }
 performBinaryOpOnSignal { arg aSelector, aSignal; ^this.reverseComposeBinaryOp(aSelector, aSignal) }
 performBinaryOpOnComplex { arg aSelector, aComplex; ^this.reverseComposeBinaryOp(aSelector, aComplex) }
 performBinaryOpOnSeqColl { arg aSelector, aSeqColl; ^this.reverseComposeBinaryOp(aSelector, aSeqColl) }
 
  neg { ^this.composeUnaryOp('neg') }
 reciprocal { ^this.composeUnaryOp('reciprocal') }
 bitNot { ^this.composeUnaryOp('bitNot') }
 abs { ^this.composeUnaryOp('abs') }
 asFloat { ^this.composeUnaryOp('asFloat') }
 asInteger { ^this.composeUnaryOp('asInt') }
 ceil { ^this.composeUnaryOp('ceil') }
 floor { ^this.composeUnaryOp('floor') }
 frac { ^this.composeUnaryOp('frac') }
 sign { ^this.composeUnaryOp('sign') }
 squared { ^this.composeUnaryOp('squared') }
 cubed { ^this.composeUnaryOp('cubed') }
 sqrt { ^this.composeUnaryOp('sqrt') }
 exp { ^this.composeUnaryOp('exp') }
 midicps { ^this.composeUnaryOp('midicps') }
 cpsmidi { ^this.composeUnaryOp('cpsmidi') }
 midiratio { ^this.composeUnaryOp('midiratio') }
 ratiomidi { ^this.composeUnaryOp('ratiomidi') }
 ampdb { ^this.composeUnaryOp('ampdb') }
 dbamp { ^this.composeUnaryOp('dbamp') } 
 octcps { ^this.composeUnaryOp('octcps') }
 cpsoct { ^this.composeUnaryOp('cpsoct') }
 log { ^this.composeUnaryOp('log') }
 log2 { ^this.composeUnaryOp('log2') }
 log10 { ^this.composeUnaryOp('log10') }
 sin { ^this.composeUnaryOp('sin') }
 cos { ^this.composeUnaryOp('cos') }
 tan { ^this.composeUnaryOp('tan') }
 asin { ^this.composeUnaryOp('asin') }
 acos { ^this.composeUnaryOp('acos') }
 atan { ^this.composeUnaryOp('atan') }
 sinh { ^this.composeUnaryOp('sinh') }
 cosh { ^this.composeUnaryOp('cosh') }
 tanh { ^this.composeUnaryOp('tanh') }
 rand { ^this.composeUnaryOp('rand') }
 rand2 { ^this.composeUnaryOp('rand2') }
 linrand { ^this.composeUnaryOp('linrand') }
 bilinrand { ^this.composeUnaryOp('bilinrand') }
 sum3rand { ^this.composeUnaryOp('sum3rand') }
 distort { ^this.composeUnaryOp('distort') }
 softclip { ^this.composeUnaryOp('softclip') }
 nyqring { ^this.composeUnaryOp('nyqring') }
 coin { ^this.composeUnaryOp('coin') }
 even { ^this.composeUnaryOp('even') }
 odd { ^this.composeUnaryOp('odd') }
 isPositive { ^this.composeUnaryOp('isPositive') }
 isNegative { ^this.composeUnaryOp('isNegative') }
 isStrictlyPositive { ^this.composeUnaryOp('isStrictlyPositive') }
 
 + { arg function; ^this.composeBinaryOp('+', function) }
 - { arg function; ^this.composeBinaryOp('-', function) }
 * { arg function; ^this.composeBinaryOp('*', function) }
 / { arg function; ^this.composeBinaryOp('/', function) }
 div { arg function; ^this.composeBinaryOp('div', function) }
 % { arg function; ^this.composeBinaryOp('%', function) }
 ** { arg function; ^this.composeBinaryOp('**', function) }
 min { arg function; ^this.composeBinaryOp('min', function) } 
 max { arg function; ^this.composeBinaryOp('max', function) }
 
 < { arg function; ^this.composeBinaryOp('<', function) }
 <= { arg function; ^this.composeBinaryOp('<=', function) }
 > { arg function; ^this.composeBinaryOp('>', function) }
 >= { arg function; ^this.composeBinaryOp('>=', function) }
 
 & { arg function; ^this.composeBinaryOp('&', function) }
 | { arg function; ^this.composeBinaryOp('|', function) }
 bitXor { arg function; ^this.composeBinaryOp('bitXor', function) } 
 lcm { arg function; ^this.composeBinaryOp('lcm', function) }
 gcd { arg function; ^this.composeBinaryOp('gcd', function) }
 round { arg function; ^this.composeBinaryOp('round', function) }
 trunc { arg function; ^this.composeBinaryOp('trunc', function) }
 atan2 { arg function; ^this.composeBinaryOp('atan2', function) } 
 hypot { arg function; ^this.composeBinaryOp('hypot', function) }
 hypotApx { arg function; ^this.composeBinaryOp('hypotApx', function) }
 >> { arg function; ^this.composeBinaryOp('>>', function) }
 +>> { arg function; ^this.composeBinaryOp('+>>', function) }
 ring1 { arg function; ^this.composeBinaryOp('ring1', function) }
 ring2 { arg function; ^this.composeBinaryOp('ring2', function) }
 ring3 { arg function; ^this.composeBinaryOp('ring3', function) }
 ring4 { arg function; ^this.composeBinaryOp('ring4', function) }
 difsqr { arg function; ^this.composeBinaryOp('difsqr', function) }
 sumsqr { arg function; ^this.composeBinaryOp('sumsqr', function) }
 sqrsum { arg function; ^this.composeBinaryOp('sqrsum', function) }
 sqrdif { arg function; ^this.composeBinaryOp('sqrdif', function) }
 absdif { arg function; ^this.composeBinaryOp('absdif', function) }
 thresh { arg function; ^this.composeBinaryOp('thresh', function) }
 amclip { arg function; ^this.composeBinaryOp('amclip', function) }
 scaleneg { arg function; ^this.composeBinaryOp('scaleneg', function) }
 clip2 { arg function; ^this.composeBinaryOp('clip2', function) }
 fold2 { arg function; ^this.composeBinaryOp('fold2', function) }
 wrap2 { arg function; ^this.composeBinaryOp('wrap2', function) }
 excess { arg function; ^this.composeBinaryOp('excess', function) }
 <! { arg function; ^this.composeBinaryOp('<!', function) }
 rrand { arg function; ^this.composeBinaryOp('rrand', function) }
 exprand { arg function; ^this.composeBinaryOp('exprand', function) }
  real { ^this }
 imag { ^0.0 }
}
Function : AbstractFunction {
 var <def, context;
  
 *new { ^this.shouldNotImplement(thisMethod) }
 *newClear { ^this.shouldNotImplement(thisMethod) }
 deepCopyWithDict { ^this }
 shallowCopy { ^this }
 choose { ^this.value }
 
  value { arg ... args; 
 _FunctionValue 
  ^this.primitiveFailed 
 }
 valueArray { arg ... args;
 _FunctionValueArray 
   ^this.primitiveFailed 
 }
 
 valueEnvir { arg ... args; 
 _FunctionValueEnvir
   ^this.primitiveFailed 
 }
 valueArrayEnvir { arg ... args;
 _FunctionValueArrayEnvir 
    ^this.primitiveFailed 
 }
 
 loop {
   loop({ this.value });
 }
 
 asRoutine {
 ^Routine.new(this)
 }
 
  spawn { arg ... args; 
 _FunctionValueArray 
   ^this.primitiveFailed 
 }
 kr { arg lagTime = 0.1;
 ^Plug.kr(this, lagTime)
 }
 
  play { arg duration; ^Synth.play(this, duration) }
 scope { arg duration = 0.01; ^Synth.scope(this, duration) }
 plot { arg duration = 0.01; ^Synth.plot(this, duration) }
 fftScope { ^Synth.fftScope(this) }
 wakeUp { arg synth, now, task; ^this.value(synth, now, task) }
 
  libMenuAction { arg names; this.value(names) }
  transformEvent { arg event;
 ^this.value(event)
 }
}
UGen : AbstractFunction {
 var <synth;
 var <>inputs;
 var <>rate = 'audio';
 var <state = 0;
 
  *ar1 { 
 ^this.new; 
 }  *kr1 {
 ^this.new.rate_('control'); 
 }  *sr1 {
 ^this.new.rate_('spectral'); 
 }  
 init { arg ... theInputs;
  inputs = theInputs;
 }
 
  *new {
 _UGen_New
 ^this.primitiveFailed
 }
 *newClear { ^this.shouldNotImplement }
 poll {  _UGenPoll
 ^this.primitiveFailed
 }
 
 source { ^this }
 isValidUGenInput { ^true }
 degreeToKey { arg scale, stepsPerOctave=12;
 ^DegreeToKey.kr(scale, this, stepsPerOctave)
 }
 
   composeUnaryOp { arg aSelector;
 ^UnaryOpUGen.new(aSelector, this)
 }
 composeBinaryOp { arg aSelector, anInput;
 if (anInput.isValidUGenInput, {
 ^BinaryOpUGen.new(aSelector, this, anInput)
 },{
 anInput.performBinaryOpOnUGen(aSelector, this);
 });
 }
 reverseComposeBinaryOp { arg aSelector, aUGen;
 ^BinaryOpUGen.new(aSelector, aUGen, this)
 }
 composeNAryOp { arg aSelector, anArgList;
 ^this.notYetImplemented
 }
 
  
 asComplex { ^Complex.new(this, 0.0) }
 performBinaryOpOnComplex { arg aSelector, aComplex; ^aComplex.perform(aSelector, this.asComplex) }
 
 if { arg trueUGen, falseUGen;
 ^(this * (trueUGen - falseUGen)) + falseUGen;
 }
}
MultiOutUGen : UGen {
  var channels;
 initOutputs { arg argNumChannels;
 var i = 0, outChan;
 if (argNumChannels <= 1, {
 outChan = OutputProxy.ar1(this);
 channels = Array.with(outChan);
 ^outChan
 },{
 channels = Array.new(argNumChannels);
 while ({ i < argNumChannels },{
 outChan = OutputProxy.ar1(this);
 channels.add(outChan);
 i = i + 1;
 });
 ^channels
 });
 }
} 
UGenPlugIn : UGen {
}
MultiOutUGenPlugIn : MultiOutUGen {
}
ExamplePlugIn : UGenPlugIn { 
 *ar { arg a = 0.0, b = 0.0;
 ^this.multiChannelPerform('ar1', a, b)
 }
 *kr { arg a = 0.0, b = 0.0;
 ^this.multiChannelPerform('kr1', a, b)
 }
 *ar1 { arg a = 0.0, b = 0.0;
 ^super.ar1.init(a, b)
 }
 *kr1 { arg a = 0.0, b = 0.0;
 ^super.kr1.init(a, b)
 }
}
OutputProxy : UGen {
 var <>source;
 *ar1 { arg itsSourceUGen;
 ^super.ar1.inputs_(itsSourceUGen).source_(itsSourceUGen)
 }
 *kr1 { arg itsSourceUGen;
 ^super.kr1.inputs_(itsSourceUGen).source_(itsSourceUGen)
 }
 *sr1 { arg itsSourceUGen;
 ^super.sr1.inputs_(itsSourceUGen).source_(itsSourceUGen)
 }
}

TapN : UGen {
 var signalBuffer;
 
 *ar { arg argSignalBuffer, delaytime = 0.2, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', argSignalBuffer, delaytime, mul, add)
 }
 *ar1 { arg argSignalBuffer, delaytime = 0.2, mul = 1.0, add = 0.0;
 ^super.ar1.init(argSignalBuffer, delaytime, mul, add)
 }
 init { arg argSignalBuffer ... theInputs;
 signalBuffer = argSignalBuffer;
 inputs = theInputs;
 }
}
TapL : TapN { }
TapA : TapN { }
DelayWr : UGen {
 var signalBuffer;
 
 *ar { arg argSignalBuffer, in = 0.0;
 ^this.multiChannelPerform('ar1', argSignalBuffer, in)
 }
 *ar1 { arg argSignalBuffer, in = 0.0;
 ^super.ar1.init(argSignalBuffer, in)
 }
 init { arg argSignalBuffer ... theInputs;
 signalBuffer = argSignalBuffer;
 inputs = theInputs;
 }
}
 
GrainTap : UGen {
 var signalBuffer;
 
 *ar { arg argSignalBuffer, grainDur = 0.2, pchRatio = 1.0, 
 pchDispersion = 0.0, timeDispersion = 0.0, overlap = 2.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', argSignalBuffer, grainDur, pchRatio, 
 pchDispersion, timeDispersion, overlap, mul, add)
 }
 *ar1 { arg argSignalBuffer, grainDur = 0.2, pchRatio = 1.0, 
 pchDispersion = 0.0, timeDispersion = 0.0, overlap = 2.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(argSignalBuffer, grainDur, pchRatio, 
 pchDispersion, timeDispersion, overlap, mul, add)
 }
 init { arg argSignalBuffer ... theInputs;
 signalBuffer = argSignalBuffer;
 inputs = theInputs;
 }
}
PingPongN : UGen {
 *ar { arg argLeft, argRight, maxDelayTime, actDelayTime, feedback;
 
 var leftBuffer, rightBuffer, leftDelayedSignal, rightDelayedSignal, output, dummy;
 var bufLength;
 
 bufLength = Synth.sampleRate * maxDelayTime;
 leftBuffer = Signal.new(bufLength);  rightBuffer = Signal.new(bufLength);  leftDelayedSignal = TapN.ar(leftBuffer, actDelayTime);  rightDelayedSignal = TapN.ar(rightBuffer, actDelayTime); 
  output = [leftDelayedSignal + argLeft, rightDelayedSignal + argRight]; 
 dummy = DelayWr.ar([rightBuffer, leftBuffer], output * feedback);  ^output <! dummy;  }
}
PingPongL : UGen {
 *ar { arg argLeft, argRight, maxDelayTime, actDelayTime, feedback;
 
 var leftBuffer, rightBuffer, leftDelayedSignal, rightDelayedSignal, output, dummy;
 var bufLength;
 
 bufLength = Synth.sampleRate * maxDelayTime;
 leftBuffer = Signal.new(bufLength);  rightBuffer = Signal.new(bufLength);  leftDelayedSignal = TapL.ar(leftBuffer, actDelayTime);  rightDelayedSignal = TapL.ar(rightBuffer, actDelayTime); 
  output = [leftDelayedSignal + argLeft, rightDelayedSignal + argRight]; 
 dummy = DelayWr.ar([rightBuffer, leftBuffer], output * feedback);  ^output <! dummy;  }
}
DelayProcessN : UGen {
 *ar { arg process, in, maxDelayTime, actDelayTime, feedback;
 
 var buffer, bufLength, delayedSignal, output, dummy;
 
 bufLength = Synth.sampleRate * maxDelayTime;
 buffer = Signal.new(bufLength); 
 delayedSignal = TapN.ar(buffer, actDelayTime); 
 output = process.value(delayedSignal) * feedback + in;
 
 
 dummy = DelayWr.ar(buffer, output);
 ^output <! dummy;  }
}
DelayProcessL : UGen {
 *ar { arg process, in, maxDelayTime, actDelayTime, feedback;
 
 var buffer, bufLength, delayedSignal, output, dummy;
 
 bufLength = Synth.sampleRate * maxDelayTime;
 buffer = Signal.new(bufLength); 
 delayedSignal = TapL.ar(buffer, actDelayTime); 
 output = process.value(delayedSignal) * feedback + in;
 
 
 dummy = DelayWr.ar(buffer, output);  ^output <! dummy;  }
}

Osc : UGen {
 var table;
 
 *ar { 
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', table, freq, phase, mul, add)
 }
 *kr {
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', table, freq, phase, mul, add)
 }
 *ar1 { 
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^super.ar1.init(table, freq, phase, mul, add)
 }
 *kr1 { 
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^super.kr1.init(table, freq, phase, mul, add)
 }
 
 init { arg aTable ... theInputs;
 inputs = theInputs;
 table = aTable;
 }
}
SinOsc : UGen { 
 *ar { 
 arg freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', freq, phase, mul, add)
 }
 *kr {
 arg freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', freq, phase, mul, add)
 }
 *ar1 { 
 arg freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^super.ar1.init(freq, phase, mul, add)
 }
 *kr1 { 
 arg freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^super.kr1.init(freq, phase, mul, add)
 }
}
OscN : UGen {
 var table;
 
 *ar { 
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', table, freq, phase, mul, add)
 }
 *kr {
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', table, freq, phase, mul, add)
 }
 *ar1 { 
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^super.ar1.init(table, freq, phase, mul, add)
 }
 *kr1 { 
 arg table, freq=440.0, phase=0.0, mul=1.0, add=0.0;
 ^super.kr1.init(table, freq, phase, mul, add)
 }
 
 init { arg aTable ... theInputs;
 inputs = theInputs;
 table = aTable;
 }
}
COsc : UGen {
 var table;
 
 *ar { 
 arg table, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', table, freq, beats, mul, add)
 }
 *kr {
 arg table, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', table, freq, beats, mul, add)
 }
 *ar1 { 
 arg table, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^super.ar1.init(table, freq, beats, mul, add)
 }
 *kr1 { 
 arg table, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^super.kr1.init(table, freq, beats, mul, add)
 }
 
 init { arg aTable ... theInputs;
 inputs = theInputs;
 table = aTable;
 }
}
COsc2 : UGen {
 var table1, table2;
 
 *ar { 
 arg table1, table2, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', table1, table2, freq, beats, mul, add)
 }
 *kr {
 arg table1, table2, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', table1, table2, freq, beats, mul, add)
 }
 *ar1 { 
 arg table1, table2, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^super.ar1.init(table1, table2, freq, beats, mul, add)
 }
 *kr1 { 
 arg table1, table2, freq=440.0, beats=0.5, mul=1.0, add=0.0;
 ^super.kr1.init(table1, table2, freq, beats, mul, add)
 }
 
 init { arg aTable1, aTable2 ... theInputs;
 inputs = theInputs;
 table1 = aTable1;
 table2 = aTable2;
 }
}
OscX4 : UGen {
 var table1, table2, table3, table4;
 
 *ar { 
 arg table1, table2, table3, table4, freq=440.0, xpos=0.0, ypos=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', table1, table2, table3, table4, freq, xpos, ypos, mul, add)
 }
 *kr {
 arg table1, table2, table3, table4, freq=440.0, xpos=0.0, ypos=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', table1, table2, table3, table4, freq, xpos, ypos, mul, add)
 }
 *ar1 { 
 arg table1, table2, table3, table4, freq=440.0, xpos=0.0, ypos=0.0, mul=1.0, add=0.0;
 ^super.ar1.init(table1, table2, table3, table4, freq, xpos, ypos, mul, add)
 }
 *kr1 { 
 arg table1, table2, table3, table4, freq=440.0, xpos=0.0, ypos=0.0, mul=1.0, add=0.0;
 ^super.kr1.init(table1, table2, table3, table4, freq, xpos, ypos, mul, add)
 }
 
 init { arg aTable1, aTable2, aTable3, aTable4 ... theInputs;
 inputs = theInputs;
 table1 = aTable1;
 table2 = aTable2;
 table3 = aTable3;
 table4 = aTable4;
 }
}
PMOsc : UGen { 
 *ar { 
 arg carfreq=440.0, modfreq=440.0, pmindex=0.0, modphase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', carfreq, modfreq, pmindex, modphase, mul, add)
 }
 *kr {
 arg carfreq=440.0, modfreq=440.0, pmindex=0.0, modphase=0.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', carfreq, modfreq, pmindex, modphase, mul, add)
 }
 *ar1 { 
 arg carfreq=440.0, modfreq=440.0, pmindex=0.0, modphase=0.0, mul=1.0, add=0.0;
 ^super.ar1.init(carfreq, modfreq, pmindex, modphase, mul, add)
 }
 *kr1 { 
 arg carfreq=440.0, modfreq=440.0, pmindex=0.0, modphase=0.0, mul=1.0, add=0.0;
 ^super.kr1.init(carfreq, modfreq, pmindex, modphase, mul, add)
 }
}
Formant : UGen {
 *ar {
 arg fundfreq = 440.0, formfreq = 1760.0, bwfreq = 880.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', fundfreq, formfreq, bwfreq, mul, add)
 }
 *ar1 { 
 arg fundfreq = 440.0, formfreq = 1760.0, bwfreq = 880.0, mul=1.0, add=0.0;
 ^super.ar1.init(fundfreq, formfreq, bwfreq, mul, add)
 }
}
Phasor : UGen {
 *ar {
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, mul, add)
 }
 *ar1 { 
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, mul, add)
 }
}
LFSaw : UGen {
 *ar {
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, mul, add)
 }
 *ar1 { 
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, mul, add)
 }
 *kr {
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', freq, mul, add)
 }
 *kr1 { 
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(freq, mul, add)
 }
}
LFTri : LFSaw
{
}
LFPulse : UGen {
 *ar {
 arg freq = 440.0, width = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, width, mul, add)
 }
 *ar1 { 
 arg freq = 440.0, width = 0.5, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, width, mul, add)
 }
 *kr {
 arg freq = 440.0, width = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', freq, width, mul, add)
 }
 *kr1 { 
 arg freq = 440.0, width = 0.5, mul = 1.0, add = 0.0;
 ^super.kr1.init(freq, width, mul, add)
 }
}
Impulse : UGen {
 *ar {
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, mul, add)
 }
 *ar1 { 
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, mul, add)
 }
 *kr {
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', freq, mul, add)
 }
 *kr1 { 
 arg freq = 440.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(freq, mul, add)
 }
}
SyncSaw : UGen {
 *ar {
 arg syncFreq = 440.0, sawFreq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', syncFreq, sawFreq, mul, add)
 }
 *ar1 { 
 arg syncFreq = 440.0, sawFreq = 440.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(syncFreq, sawFreq, mul, add)
 }
 *kr {
 arg syncFreq = 440.0, sawFreq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', syncFreq, sawFreq, mul, add)
 }
 *kr1 { 
 arg syncFreq = 440.0, sawFreq = 440.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(syncFreq, sawFreq, mul, add)
 }
}
Index : UGen {
 var table;
 *ar {
 arg table, in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', table, in, mul, add)
 }
 *kr {
 arg table, in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', table, in, mul, add)
 }
 *ar1 {
 arg table, in = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(table, in, mul, add)
 }
 *kr1 {
 arg table, in = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(table, in, mul, add)
 }
 init { arg aTable ... theInputs;
 inputs = theInputs;
 table = aTable;
 }
}
WrapIndex : Index {
}
Shaper : Index {
}
DegreeToKey : UGen {
 var table;
 *ar {
 arg table, in = 0.0, octave = 12.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', table, in, octave, mul, add)
 }
 *kr {
 arg table, in = 0.0, octave = 12.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', table, in, octave, mul, add)
 }
 *ar1 {
 arg table, in = 0.0, octave = 12.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(table, in, octave, mul, add)
 }
 *kr1 {
 arg table, in = 0.0, octave = 12.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(table, in, octave, mul, add)
 }
 init { arg aTable ... theInputs;
 inputs = theInputs;
 table = aTable;
 }
}

Trig1 : UGen {
 
 *ar { arg in = 0.0, dur = 0.1;
 ^this.multiChannelPerform('ar1', in, dur)
 }
 *kr { arg in = 0.0, dur = 0.1;
 ^this.multiChannelPerform('kr1', in, dur)
 }
 *ar1 { arg in = 0.0, dur = 0.1;
 ^super.ar1.init(in, dur)
 }
 *kr1 { arg in = 0.0, dur = 0.1;
 ^super.kr1.init(in, dur)
 }
 
}
Trig : Trig1 {
}
TDelay : Trig1 {
}
Latch : UGen {
 
 *ar { arg in = 0.0, trig = 0.0;
 ^this.multiChannelPerform('ar1', in, trig)
 }
 *kr { arg in = 0.0, trig = 0.0;
 ^this.multiChannelPerform('kr1', in, trig)
 }
 *ar1 { arg in = 0.0, trig = 0.0;
 ^super.ar1.init(in, trig)
 }
 *kr1 { arg in = 0.0, trig = 0.0;
 ^super.kr1.init(in, trig)
 }
 
}
Gate : Latch { 
}
PulseCount : UGen {
 
 *ar { arg trig = 0.0, reset = 0.0;
 ^this.multiChannelPerform('ar1', trig, reset)
 }
 *kr { arg trig = 0.0, reset = 0.0;
 ^this.multiChannelPerform('kr1', trig, reset)
 }
 *ar1 { arg trig = 0.0, reset = 0.0;
 ^super.ar1.init(trig, reset)
 }
 *kr1 { arg trig = 0.0, reset = 0.0;
 ^super.kr1.init(trig, reset)
 }
 
}
Peak : PulseCount {
}
PulseDivider : UGen {
 
 *ar { arg trig = 0.0, div = 2.0, start = 0.0;
 ^this.multiChannelPerform('ar1', trig, div, start)
 }
 *kr { arg trig = 0.0, div = 2.0, start = 0.0;
 ^this.multiChannelPerform('kr1', trig, div, start)
 }
 *ar1 { arg trig = 0.0, div = 2.0, start = 0.0;
 ^super.ar1.init(trig, div, start)
 }
 *kr1 { arg trig = 0.0, div = 2.0, start = 0.0;
 ^super.kr1.init(trig, div, start)
 }
 
}
SetResetFF : PulseCount {
 set { _SetResetFF_Set }
 reset { _SetResetFF_Reset }
 toggle { _SetResetFF_Toggle }
}
ToggleFF : UGen {
 
 *ar { arg trig = 0.0;
 ^this.multiChannelPerform('ar1', trig)
 }
 *kr { arg trig = 0.0;
 ^this.multiChannelPerform('kr1', trig)
 }
 *ar1 { arg trig = 0.0;
 ^super.ar1.init(trig)
 }
 *kr1 { arg trig = 0.0;
 ^super.kr1.init(trig)
 }
 
 init { arg theInput; 
 inputs = theInput;
 }
 set { _ToggleFF_Set }
 reset { _ToggleFF_Reset }
 toggle { _ToggleFF_Toggle }
}
ImpulseSequencer : UGen {
 var <>stream;
 
 *ar { arg sequence, trig = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', sequence, trig, mul, add)
 }
 *kr { arg sequence, trig = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', sequence, trig, mul, add)
 }
 *ar1 { arg sequence, trig = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(sequence, trig, mul, add)
 }
 *kr1 { arg sequence, trig = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(sequence, trig, mul, add)
 }
 init { arg sequence ... theInputs;
 stream = sequence;
 inputs = theInputs;
 }
}
Sequencer : ImpulseSequencer {
}
UserUGen : UGen {
 var <>stream;
 
 *ar { arg sequence, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', sequence, mul, add)
 }
 *kr { arg sequence, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', sequence, mul, add)
 }
 *ar1 { arg sequence, mul = 1.0, add = 0.0;
 ^super.ar1.init(sequence, mul, add)
 }
 *kr1 { arg sequence, mul = 1.0, add = 0.0;
 ^super.kr1.init(sequence, mul, add)
 }
 init { arg sequence ... theInputs;
 stream = sequence;
 inputs = theInputs;
 }
 next {  ^stream.next(nil)  }
}
StepClock : UGen {
 var <>stream;
 
 *ar { arg sequence, rate = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', sequence, rate, mul, add)
 }
 *kr { arg sequence, rate = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', sequence, rate, mul, add)
 }
 *ar1 { arg sequence, rate = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(sequence, rate, mul, add)
 }
 *kr1 { arg sequence, rate = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(sequence, rate, mul, add)
 }
 init { arg sequence ... theInputs;
 stream = sequence;
 inputs = theInputs;
 }
 next {  ^stream.next(nil)
 }
}
ZeroCrossing : UGen {
 
 *ar { arg in = 0.0;
 ^this.multiChannelPerform('ar1', in)
 }
 *kr { arg in = 0.0;
 ^this.multiChannelPerform('kr1', in)
 }
 *ar1 { arg in = 0.0;
 ^super.ar1.init(in)
 }
 *kr1 { arg in = 0.0;
 ^super.kr1.init(in)
 }
 init { arg theInput; 
 inputs = theInput;
 }
}
PeakFollower : UGen {
 
 *ar { arg in = 0.0, decay = 0.999;
 ^this.multiChannelPerform('ar1', in, decay)
 }
 *kr { arg in = 0.0, decay = 0.999;
 ^this.multiChannelPerform('kr1', in, decay)
 }
 *ar1 { arg in = 0.0, decay = 0.999;
 ^super.ar1.init(in, decay)
 }
 *kr1 { arg in = 0.0, decay = 0.999;
 ^super.kr1.init(in, decay)
 }
}
Pitch : MultiOutUGen {
 var buffer, initFreq, minFreq, maxFreq, execFreq;
 var maxBinsPerOctave, median, ampThreshold, peakThreshold, downSample;
 
 *kr { arg in = 0.0, initFreq = 440.0, minFreq = 60.0, maxFreq = 4000.0, execFreq = 100.0,
 maxBinsPerOctave = 16, median = 1, ampThreshold = 0.01, peakThreshold = 0.5, downSample = 1;
 ^this.multiChannelPerform('kr1', in, initFreq, minFreq, maxFreq, execFreq,
 maxBinsPerOctave, median, ampThreshold, peakThreshold, downSample)
 }
 *kr1 { arg in = 0.0, initFreq = 440.0, minFreq = 60.0, maxFreq = 4000.0, execFreq = 100.0,
 maxBinsPerOctave = 16, median = 1, ampThreshold = 0.01, peakThreshold = 0.5, downSample = 1;
 ^super.kr1.init(in, initFreq, minFreq, maxFreq, execFreq,
 maxBinsPerOctave, median, ampThreshold, peakThreshold, downSample)
 }
 init { arg argInput, argInitFreq, argMinFreq, argMaxFreq, argExecFreq,
 argMaxBinsPerOctave, argMedian, argAmpThreshold, argPeakThreshold, argDownSample;
 inputs = argInput;
 initFreq = argInitFreq;
 minFreq = argMinFreq;
 maxFreq = argMaxFreq;
 execFreq = argExecFreq;
 maxBinsPerOctave = argMaxBinsPerOctave;
 median = argMedian;
 ampThreshold = argAmpThreshold;
 peakThreshold = argPeakThreshold;
 downSample = argDownSample;
 channels = [ OutputProxy.kr1(this), OutputProxy.kr1(this) ];
 ^channels
 }
}

PitchShift : UGen {
 var table;
 
 *ar { arg in = 0.0, winSize = 0.2, pchRatio = 1.0, 
 pchDispersion = 0.0, timeDispersion = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, winSize, pchRatio, 
 pchDispersion, timeDispersion, mul, add)
 }
 *ar1 { arg in = 0.0, winSize = 0.2, pchRatio = 1.0, 
 pchDispersion = 0.0, timeDispersion = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, winSize, pchRatio, pchDispersion, timeDispersion, mul, add)
 }
}


WhiteNoise : UGen {
 
 *ar { arg mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', mul, add)
 }
 *kr { arg mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', mul, add)
 }
 *ar1 { arg mul = 1.0, add = 0.0;
 ^super.ar1.init(mul, add)
 }
 *kr1 { arg mul = 1.0, add = 0.0;
 ^super.kr1.init(mul, add)
 }
 
}
BrownNoise : WhiteNoise {
}
PinkNoise : WhiteNoise {
}
PinkerNoise : WhiteNoise {
}
ClipNoise : WhiteNoise {
}
GrayNoise : WhiteNoise {
}
Crackle : UGen {
 
 *ar { arg chaosParam=1.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', chaosParam, mul, add)
 }
 *kr { arg chaosParam=1.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', chaosParam, mul, add)
 }
 *ar1 { arg chaosParam=1.5, mul = 1.0, add = 0.0;
 ^super.ar1.init(chaosParam, mul, add)
 }
 *kr1 { arg chaosParam=1.5, mul = 1.0, add = 0.0;
 ^super.kr1.init(chaosParam, mul, add)
 } 
}
Rossler : UGen {
 
 *ar { arg chaosParam=1.5, dt = 0.04, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', chaosParam, dt, mul, add)
 }
 *kr { arg chaosParam=1.5, dt = 0.04, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', chaosParam, dt, mul, add)
 }
 *ar1 { arg chaosParam=1.5, dt = 0.04, mul = 1.0, add = 0.0;
 ^super.ar1.init(chaosParam, dt, mul, add)
 }
 *kr1 { arg chaosParam=1.5, dt = 0.04, mul = 1.0, add = 0.0;
 ^super.kr1.init(chaosParam, dt, mul, add)
 } 
}
LFNoise0 : UGen {
 
 *ar { arg freq=500.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, mul, add)
 }
 *kr { arg freq=500.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', freq, mul, add)
 }
 *ar1 { arg freq=500.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, mul, add)
 }
 *kr1 { arg freq=500.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(freq, mul, add)
 } 
}
LFNoise1 : LFNoise0 {
}
LFNoise2 : LFNoise0 {
}
LFClipNoise : LFNoise0 {
}
Dust : UGen {
 
 *ar { arg density = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', density, mul, add)
 }
 *kr { arg density = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', density, mul, add)
 }
 *ar1 { arg density = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(density, mul, add)
 }
 *kr1 { arg density = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(density, mul, add)
 }
 
}
Dust2 : Dust {
}
LinCong : UGen {
 var iseed, imul, iadd, imod;
 
 *ar { arg iseed, imul, iadd, imod, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', iseed, imul, iadd, imod, mul, add)
 }
 *kr { arg iseed, imul, iadd, imod, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', iseed, imul, iadd, imod, mul, add)
 }
 *ar1 { arg iseed, imul, iadd, imod, mul = 1.0, add = 0.0;
 ^super.ar1.init(iseed, imul, iadd, imod, mul, add)
 }
 *kr1 { arg iseed, imul, iadd, imod, mul = 1.0, add = 0.0;
 ^super.kr1.init(iseed, imul, iadd, imod, mul, add)
 } 
 init { arg jseed, jmul, jadd, jmod ... theInputs;
 inputs = theInputs;
 iseed = jseed;
 imul = jmul;
 iadd = jadd;
 imod = jmod;
 }
}
Latoocarfian : UGen {
 
 *ar { arg a, b, c, d, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', a, b, c, d, mul, add)
 }
 *kr { arg a, b, c, d, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', a, b, c, d, mul, add)
 }
 *ar1 { arg a, b, c, d, mul = 1.0, add = 0.0;
 ^super.ar1.init(a, b, c, d, mul, add)
 }
 *kr1 { arg a, b, c, d, mul = 1.0, add = 0.0;
 ^super.kr1.init(a, b, c, d, mul, add)
 } 
}

Delay1 : UGen {
 
 *ar { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, mul, add)
 }
 *kr { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, mul, add)
 }
 *ar1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, mul, add)
 }
 *kr1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, mul, add)
 } 
}
Delay2 : Delay1 { }
DelayN : UGen {
 var <buffer;
 
 *ar { arg in = 0.0, maxdelaytime = 0.2, delaytime = 0.2, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, maxdelaytime, delaytime, mul, add)
 }
 *ar1 { arg in = 0.0, maxdelaytime = 0.2, delaytime = 0.2, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, maxdelaytime, delaytime, mul, add)
 }
 *kr { arg in = 0.0, maxdelaytime = 0.2, delaytime = 0.2, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, maxdelaytime, delaytime, mul, add)
 }
 *kr1 { arg in = 0.0, maxdelaytime = 0.2, delaytime = 0.2, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, maxdelaytime, delaytime, mul, add)
 }
}
DelayL : DelayN { }
DelayA : DelayN { }
CombN : UGen {
 var <buffer;
 
 *ar { arg in = 0.0, maxdelaytime = 0.2, delaytime = 0.2, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, maxdelaytime, delaytime, decaytime, mul, add)
 }
 *ar1 { arg in = 0.0, maxdelaytime = 0.2, delaytime = 0.2, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, maxdelaytime, delaytime, decaytime, mul, add)
 }
}
CombL : CombN { }
CombA : CombN { }
AllpassN : CombN { }
AllpassL : CombN { }
AllpassA : CombN { }
MultiTap : UGen {
 var <buffer, times, levels;
 *ar { arg timesArray, levelsArray, in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', timesArray, levelsArray, in, mul, add)
 }
 *ar1 { arg timesArray, levelsArray, in = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(timesArray, levelsArray, in, mul, add)
 }
 init { arg argTimes, argLevels ... theInputs;
 inputs = theInputs;
 times = argTimes.value;
 levels = argLevels.value;
 }
}

Resonz : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, rq, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, rq, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, rq, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, rq, mul, add)
 } 
}
OnePole : UGen {
 
 *ar { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, coef, mul, add)
 }
 *kr { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, coef, mul, add)
 }
 *ar1 { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, coef, mul, add)
 }
 *kr1 { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, coef, mul, add)
 } 
}
OneZero : UGen {
 
 *ar { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, coef, mul, add)
 }
 *kr { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, coef, mul, add)
 }
 *ar1 { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, coef, mul, add)
 }
 *kr1 { arg in = 0.0, coef = 0.5, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, coef, mul, add)
 } 
}
TwoPole : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, radius, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, radius, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, radius, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, radius, mul, add)
 } 
}
TwoZero : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, radius, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, radius, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, radius, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, radius = 0.8, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, radius, mul, add)
 } 
}
Integrator : UGen {
 
 *ar { arg in = 0.0, coef = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, coef, mul, add)
 }
 *kr { arg in = 0.0, coef = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, coef, mul, add)
 }
 *ar1 { arg in = 0.0, coef = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, coef, mul, add)
 }
 *kr1 { arg in = 0.0, coef = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, coef, mul, add)
 } 
}
Decay : UGen {
 
 *ar { arg in = 0.0, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, decayTime, mul, add)
 }
 *kr { arg in = 0.0, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, decayTime, mul, add)
 }
 *ar1 { arg in = 0.0, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, decayTime, mul, add)
 }
 *kr1 { arg in = 0.0, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, decayTime, mul, add)
 } 
}
Decay2 : UGen {
 
 *ar { arg in = 0.0, attackTime = 0.01, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, attackTime, decayTime, mul, add)
 }
 *kr { arg in = 0.0, attackTime = 0.01, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, attackTime, decayTime, mul, add)
 }
 *ar1 { arg in = 0.0, attackTime = 0.01, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, attackTime, decayTime, mul, add)
 }
 *kr1 { arg in = 0.0, attackTime = 0.01, decayTime = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, attackTime, decayTime, mul, add)
 } 
}
Lag : UGen {
 
 *ar { arg in = 0.0, lagTime = 0.2, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, lagTime, mul, add)
 }
 *kr { arg in = 0.0, lagTime = 0.2, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, lagTime, mul, add)
 }
 *ar1 { arg in = 0.0, lagTime = 0.2, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, lagTime, mul, add)
 }
 *kr1 { arg in = 0.0, lagTime = 0.2, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, lagTime, mul, add)
 }
}
LeakDC : UGen {
 
 *ar { arg in = 0.0, coef = 0.99, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, coef, mul, add)
 }
 *kr { arg in = 0.0, coef = 0.9, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, coef, mul, add)
 }
 *ar1 { arg in = 0.0, coef = 0.99, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, coef, mul, add)
 }
 *kr1 { arg in = 0.0, coef = 0.9, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, coef, mul, add)
 } 
}
RLPF : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, q = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, q, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, q = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, q, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, q = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, q, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, q = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, q, mul, add)
 } 
}
RHPF : RLPF {}
LPF : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, mul, add)
 } 
}
HPF : LPF {}
BPF : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, rq, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, rq, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, rq, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, rq = 0.1, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, rq, mul, add)
 } 
}
BRF : BPF {}
LPZ1 : UGen {
 
 *ar { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, mul, add)
 }
 *kr { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, mul, add)
 }
 *ar1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, mul, add)
 }
 *kr1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, mul, add)
 } 
}
HPZ1 : LPZ1 {}
Slope : UGen {
 
 *ar { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, mul, add)
 }
 *kr { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, mul, add)
 }
 *ar1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, mul, add)
 }
 *kr1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, mul, add)
 } 
}
LPZ2 : UGen {
 
 *ar { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, mul, add)
 }
 *kr { arg in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, mul, add)
 }
 *ar1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, mul, add)
 }
 *kr1 { arg in = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, mul, add)
 } 
}
HPZ2 : LPZ2 {}
BPZ2 : LPZ2 {}
BRZ2 : LPZ2 {}
Median : UGen {
 var length;
 *ar { arg length=3, in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', length, in, mul, add)
 }
 *kr { arg length=3, in = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', length, in, mul, add)
 }
 *ar1 { arg length=3, in = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(length, in, mul, add)
 }
 *kr1 { arg length=3, in = 0.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(length, in, mul, add)
 }
 init { arg argLength ... argInputs;
 length = argLength;
 inputs = argInputs; 
 }
}
AvgAbsAmp : UGen {
 
 *ar { arg in = 0.0, coef = 0.999, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, coef, mul, add)
 }
 *kr { arg in = 0.0, coef = 0.999, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, coef, mul, add)
 }
 *ar1 { arg in = 0.0, coef = 0.999, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, coef, mul, add)
 }
 *kr1 { arg in = 0.0, coef = 0.999, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, coef, mul, add)
 } 
}
Slew : UGen {
 *ar { arg in = 0.0, up = 1.0, dn = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, up, dn, mul, add)
 }
 *kr { arg in = 0.0, up = 1.0, dn = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, up, dn, mul, add)
 }
 *ar1 { arg in = 0.0, up = 1.0, dn = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, up, dn, mul, add)
 }
 *kr1 { arg in = 0.0, up = 1.0, dn = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, up, dn, mul, add)
 }
}
RLPF4 : UGen {
 
 *ar { arg in = 0.0, freq = 0.5, res = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, res, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 0.5, res = 0.5, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, res, mul, add)
 }
}
FOS : UGen {
 *ar { arg in = 0.0, a0 = 0.0, a1 = 0.0, b1 = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, a0, a1, b1, mul, add)
 }
 *ar1 { arg in = 0.0, a0 = 0.0, a1 = 0.0, b1 = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, a0, a1, b1, mul, add)
 }
}
SOS : UGen {
 *ar { arg in = 0.0, a0 = 0.0, a1 = 0.0, a2 = 0.0, b1 = 0.0, b2 = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, a0, a1, a2, b1, b2, mul, add)
 }
 *ar1 { arg in = 0.0, a0 = 0.0, a1 = 0.0, a2 = 0.0, b1 = 0.0, b2 = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, a0, a1, a2, b1, b2, mul, add)
 }
}
Ringz : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, decaytime, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, decaytime, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, decaytime, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, decaytime, mul, add)
 } 
}
Formlet : UGen {
 
 *ar { arg in = 0.0, freq = 440.0, attacktime = 1.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, freq, attacktime, decaytime, mul, add)
 }
 *kr { arg in = 0.0, freq = 440.0, attacktime = 1.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, freq, attacktime, decaytime, mul, add)
 }
 *ar1 { arg in = 0.0, freq = 440.0, attacktime = 1.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, freq, attacktime, decaytime, mul, add)
 }
 *kr1 { arg in = 0.0, freq = 440.0, attacktime = 1.0, decaytime = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, freq, attacktime, decaytime, mul, add)
 } 
}

PlayBuf : UGen {
 var signal;
 
 *ar { arg signal, sigSampleRate=44100.0, playbackRate=1.0, offset=0.0, 
 loopstart=0.0, loopend, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', signal, sigSampleRate, playbackRate, offset, 
 loopstart, loopend, mul, add)
 }
 *ar1 { arg signal, sigSampleRate=44100.0, playbackRate=1.0, offset=0.0, 
 loopstart=0.0, loopend, mul=1.0, add=0.0;
 if (loopend.isNil, { loopend = signal.size - 2 });
 ^super.ar1.init(signal, sigSampleRate, playbackRate, offset, 
 loopstart, loopend, mul, add)
 }
 
 init { arg theSignal ... theInputs;
 inputs = theInputs;
 signal = theSignal;
 }
}
RecordBuf : UGen {
 var signal;
 
 *ar { arg signal, in, recLevel=1.0, preLevel=0.0, reset=0.0, run=1.0, loopMode=0.0;
 ^this.multiChannelPerform('ar1', signal, in, recLevel, preLevel, reset, run, loopMode)
 }
 *ar1 { arg signal, in, recLevel=1.0, preLevel=0.0, reset=0.0, run=1.0, loopMode=0.0;
 ^super.ar1.init(signal, in, recLevel, preLevel, reset, run, loopMode)
 }
 init { arg argSignal ... theInputs;
 signal = argSignal;
 inputs = theInputs;
 }
}

DiskOut : UGen {
 var soundFile, numFrames, buffer1, buffer2;
 *ar { arg argSoundFile, argNumFrames, argChannelsArray;
 ^this.multiChannelPerformList('ar1', argSoundFile, argNumFrames, argChannelsArray);
 }
 *ar1 { arg argSoundFile, argNumFrames ... argChannelsArray;
 super.ar1.init(argSoundFile, argNumFrames, argChannelsArray.copy);
 ^argChannelsArray
 }
 init { arg argSoundFile, argNumFrames, argChannelsArray;
 soundFile = argSoundFile;
 numFrames = argNumFrames;
 inputs = argChannelsArray;
 }
}
DiskIn : MultiOutUGen {
 var soundFile, numFrames, buffer1, buffer2, loop, startFrame;
 
 *ar { arg argSoundFile, argLoop = false, argStartFrame=0, argNumFrames = 32768;
 ^this.multiChannelPerform('ar1', argSoundFile, argLoop, argStartFrame, argNumFrames);
 }
 *ar1 { arg argSoundFile, argLoop = false, argStartFrame=0, argNumFrames = 32768;
 ^super.ar1.init(argSoundFile, argLoop, argStartFrame, argNumFrames)
 }
 init { arg argSoundFile, argLoop = false, argStartFrame=0, argNumFrames = 32768;
 soundFile = argSoundFile;
 loop = argLoop;
 numFrames = argNumFrames;
 startFrame = argStartFrame;
 ^this.initOutputs(soundFile.numChannels);
 }
}
EnvGen : UGen {  var env;
 
 *ar { arg env, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^this.multiChannelPerform('ar1', env, mul, add, levelScale, levelBias, timeScale, gate)
 }
 *kr { arg env, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^this.multiChannelPerform('kr1', env, mul, add, levelScale, levelBias, timeScale, gate)
 }
 *ar1 { arg env, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^super.ar1.init( env, mul, add, levelScale, levelBias, timeScale, gate)
 }
 *kr1 { arg env, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^super.kr1.init( env, mul, add, levelScale, levelBias, timeScale, gate)
 }
 trigger { arg env;
 _EnvGen_Trigger
 ^this.primitiveFailed
 }
 
  init { arg argEnvelope ... theInputs;
 env = argEnvelope.value;
 inputs = theInputs;
 } 
}
EnvGen2 : UGen {  var env1, env2;
 
 *ar { arg env1, env2, blend = 0.0, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^this.multiChannelPerform('ar1', env1, env2, blend, mul, add, levelScale, levelBias, timeScale, gate)
 }
 *kr { arg env1, env2, blend = 0.0, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^this.multiChannelPerform('kr1', env1, env2, blend, mul, add, levelScale, levelBias, timeScale, gate)
 }
 *ar1 { arg env1, env2, blend = 0.0, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^super.ar1.init( env1, env2, blend, mul, add, levelScale, levelBias, timeScale, gate)
 }
 *kr1 { arg env1, env2, blend = 0.0, mul = 1.0, add = 0.0, 
 levelScale = 1.0, levelBias = 0.0, timeScale = 1.0, gate = -99.0;
 ^super.kr1.init( env1, env2, blend, mul, add, levelScale, levelBias, timeScale, gate)
 }
 
  init { arg argEnvelope1, argEnvelope2 ... theInputs;
 env1 = argEnvelope1.value;
 env2 = argEnvelope2.value;
 inputs = theInputs;
 } 
}
Osc1 : UGen {
 var table;
 
 *ar { 
 arg table, dur=1.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('ar1', table, dur, mul, add)
 }
 *kr {
 arg table, dur=1.0, mul=1.0, add=0.0;
 ^this.multiChannelPerform('kr1', table, dur, mul, add)
 }
 *ar1 { 
 arg table, dur=1.0, mul=1.0, add=0.0;
 ^super.ar1.init(table, dur, mul, add)
 }
 *kr1 { 
 arg table, dur=1.0, mul=1.0, add=0.0;
 ^super.kr1.init(table, dur, mul, add)
 }
 
 init { arg aTable ... theInputs;
 inputs = theInputs;
 table = aTable;
 }
}

Plug : UGen { 
 var <>source, prevSource, <>index = 0, result;
 
 *kr { arg source, lagTime = 0.1, index = 0;
 ^this.multiChannelPerform('kr1', source, lagTime, index)
 }
 *kr1 { arg source, lagTime = 0.1, index;
 ^super.kr1.init(source, lagTime, index)
 } 
 *ar { arg source, lagTime = 0.1, index;
 ^this.multiChannelPerform('ar1', source, lagTime, index)
 }
 *ar1 { arg source, lagTime = 0.1, index = 0;
 ^super.ar1.init(source, lagTime, index)
 } 
 init { arg argSource, lagTime, argIndex;
 source = argSource;
 inputs = lagTime;
 index = argIndex;
 }
 line { arg end=1.0, dur=1.0;
 _Plug_Line
 ^this.primitiveFailed
 }
 xline { arg end=1.0, dur=1.0;
 _Plug_XLine
 ^this.primitiveFailed
 }
 gate { arg level=1.0, dur=0.0;
 _Plug_Gate
 ^this.primitiveFailed
 }
 
 next {
  result = source.value(index);
 }
}
ControlIn : Plug { 
 }
Scope : UGen { 
 var <>view;
 
 *window { arg name, bufsize, bounds, input, minval = -1.0, maxval = 1.0;
 
 var scopeBuf1, scopeBuf2, wind, view;
 
 scopeBuf1 = Signal.newClear(bufsize);
 scopeBuf2 = Signal.newClear(bufsize);
  wind = SignalWindow.new(name, bounds, scopeBuf1, Synth.sampleRate);
 
 view = wind.at(1);    view.scopeSignal = scopeBuf2;
 view.minval = minval;
 view.maxval = maxval;
 
  ^Scope.ar(view, input);
 }
 
 *ar { arg argSignalView, input;
 ^this.multiChannelPerform('ar1', argSignalView, input)
 }
 *ar1 { arg argSignalView, input;
 ^super.ar1.init(argSignalView, input)
 } 
 init { arg argSignalView, theInput;
 view = argSignalView;
 inputs = theInput;
 } 
}
SetTempo : UGen { 
 var <>tempoBase;
 
 *kr { arg argTempoBase, in=0.0;
 ^this.multiChannelPerform('kr1', argTempoBase, in)
 }
 *kr1 { arg argTempoBase, in=0.0;
 ^super.kr1.init(argTempoBase, in)
 } 
 init { arg argTempoBase, theInput;
 tempoBase = argTempoBase;
 inputs = theInput;
 }
}
GetTempo : UGen { 
 
 *kr {
 ^super.kr1
 }
}

FSinOsc : UGen { 
 *ar { arg freq=440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, mul, add)
 }
 *kr { arg freq=440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', freq, mul, add)
 }
 *ar1 { arg freq=440.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, mul, add)
 }
 *kr1 { arg freq=440.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(freq, mul, add)
 }
}
Klang : UGen {
 var freqs, amps, phases, coefs;
 
 *ar { arg argSpecificationsArrayRef, 
 freqscale = 1.0, freqoffset = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', argSpecificationsArrayRef, 
 freqscale, freqoffset, mul, add)
 }
 *ar1 { arg argSpecificationsArrayRef, 
 freqscale = 1.0, freqoffset = 0.0, mul = 1.0, add = 0.0;
 ^super.ar1.init( argSpecificationsArrayRef, 
 freqscale, freqoffset, mul, add)
 }
 init { arg argSpecificationsArrayRef ... theInputs;
 var specificationsArray;
 specificationsArray = argSpecificationsArrayRef.value;
 # freqs, amps, phases = specificationsArray;
 inputs = theInputs;
 } 
}
Klank : UGen {
 var freqs, amps, times, coefs;
 
 *ar { arg argSpecificationsArrayRef, input, 
 freqscale = 1.0, freqoffset = 0.0, decayscale = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', argSpecificationsArrayRef, input, 
 freqscale, freqoffset, decayscale, mul, add)
 }
 *ar1 { arg argSpecificationsArrayRef, input, 
 freqscale = 1.0, freqoffset = 0.0, decayscale = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init( argSpecificationsArrayRef, input, 
 freqscale, freqoffset, decayscale, mul, add)
 }
 init { arg argSpecificationsArrayRef ... theInputs;
 var specificationsArray;
 specificationsArray = argSpecificationsArrayRef.value;
 # freqs, amps, times = specificationsArray;
 inputs = theInputs;
 } 
}
OldKlank : Klank {
 }
Blip : UGen { 
 *ar { arg freq=440.0, numharm = 200.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, numharm, mul, add)
 }
 *ar1 { arg freq=440.0, numharm = 200.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, numharm, mul, add)
 }
}
Saw : UGen { 
 *ar { arg freq=440.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, mul, add)
 }
 *ar1 { arg freq=440.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, mul, add)
 }
}
Pulse : UGen { 
 *ar { arg freq=440.0, width = 0.5, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', freq, width, mul, add)
 }
 *ar1 { arg freq=440.0, width = 0.5, mul = 1.0, add = 0.0;
 ^super.ar1.init(freq, width, mul, add)
 }
}
Line : UGen { 
 *ar { arg start=0.0, end = 1.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', start, end, dur, mul, add)
 }
 *kr { arg start=0.0, end = 1.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', start, end, dur, mul, add)
 }
 *ar1 { arg start=0.0, end = 1.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init( start, end, dur, mul, add)
 }
 *kr1 { arg start=0.0, end = 1.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init( start, end, dur, mul, add)
 } 
}
XLine : UGen { 
 *ar { arg start=1.0, end = 2.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', start, end, dur, mul, add)
 }
 *kr { arg start=1.0, end = 2.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', start, end, dur, mul, add)
 }
 *ar1 { arg start=1.0, end = 2.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^super.ar1.init( start, end, dur, mul, add)
 }
 *kr1 { arg start=1.0, end = 2.0, dur = 1.0, mul = 1.0, add = 0.0;
 ^super.kr1.init( start, end, dur, mul, add)
 } 
}
LinExp : UGen {
 *ar { arg in=0.0, srclo = 0.0, srchi = 1.0, dstlo = 1.0, dsthi = 2.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, srclo, srchi, dstlo, dsthi, mul, add)
 }
 *kr { arg in=0.0, srclo = 0.0, srchi = 1.0, dstlo = 1.0, dsthi = 2.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, srclo, srchi, dstlo, dsthi, mul, add)
 }
 *ar1 { arg in=0.0, srclo = 0.0, srchi = 1.0, dstlo = 1.0, dsthi = 2.0, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, srclo, srchi, dstlo, dsthi, mul, add)
 }
 *kr1 { arg in=0.0, srclo = 0.0, srchi = 1.0, dstlo = 1.0, dsthi = 2.0, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, srclo, srchi, dstlo, dsthi, mul, add)
 } 
} 
K2A : UGen {  *ar { arg in = 0.0;
 ^this.multiChannelPerform('ar1', in)
 }
 *ar1 { arg in=0.0;
 ^super.ar1.init(in)
 }
}

PSinGrain : UGen { 
 *ar { arg freq = 440.0, dur = 0.2, amp = 1.0;
 ^this.multiChannelPerform('ar1', freq, dur, amp)
 }
 *ar1 { arg freq = 440.0, dur = 0.2, amp = 1.0;
 ^super.ar1.init(freq, dur, amp)
 }
 }

Amplitude : UGen {
 *kr { arg in = 0.0, attackTime = 0.01, releaseTime = 0.01, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('kr1', in, attackTime, releaseTime, mul, add)
 }
 *kr1 { arg in = 0.0, attackTime = 0.01, releaseTime = 0.01, mul = 1.0, add = 0.0;
 ^super.kr1.init(in, attackTime, releaseTime, mul, add)
 }
}
Compander : UGen {
 *ar { arg in = 0.0, control = 0.0, thresh = 0.5, slopeBelow = 1.0, slopeAbove = 1.0,
 clampTime = 0.01, relaxTime = 0.01, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', in, control, thresh, slopeBelow, slopeAbove,
 clampTime, relaxTime, mul, add)
 }
 *ar1 { arg in = 0.0, control = 0.0, thresh = 0.5, slopeBelow = 1.0, slopeAbove = 1.0,
 clampTime = 0.01, relaxTime = 0.01, mul = 1.0, add = 0.0;
 ^super.ar1.init(in, control, thresh, slopeBelow, slopeAbove,
 clampTime, relaxTime, mul, add)
 }
}

CompanderD : UGen {
 *ar { arg in = 0.0, thresh = 0.5, slopeBelow = 1.0, slopeAbove = 1.0,
 clampTime = 0.01, relaxTime = 0.01, mul = 1.0, add = 0.0;
 
 ^Compander.ar(DelayN.ar(in, clampTime, clampTime), in, thresh, 
 slopeBelow, slopeAbove, clampTime, relaxTime, mul, add)
 } 
}
Normalizer : UGen {
 var buffer;
 *ar { arg in = 0.0, level = 1.0, dur = 0.01;
 ^this.multiChannelPerform('ar1', in, level, dur)
 }
 *ar1 { arg in = 0.0, level = 1.0, dur = 0.01;
 ^super.ar1.init(in, level, dur)
 }
}
Limiter : Normalizer {}
LinPan2 : UGen {
  *ar { arg in, pan;
 ^this.multiChannelPerform('ar1', in, pan)
 }
 *kr { arg in, pan;
 ^this.multiChannelPerform('kr1', in, pan)
 }
 *ar1 { arg in, pan;
  pan = pan * 0.5 + 0.5;
 ^[in * (1.0 - pan), in * pan]
 }
 *kr1 { arg in, pan;
  pan = pan * 0.5 + 0.5;
 ^[in * (1.0 - pan), in * pan]
 }
}
LinPan4 : UGen {
  *ar { arg in, xpan0, ypan0;
 ^this.multiChannelPerform('ar1', in, xpan0, ypan0)
 }
 *kr { arg in, xpan0, ypan0;
 ^this.multiChannelPerform('kr1', in, xpan0, ypan0)
 }
 *ar1 { arg in, xpan0, ypan0;
 var xpan1, ypan1, inx0, inx1;
 xpan0 = xpan0.clip2(1) * 0.5 + 0.5;
 ypan0 = ypan0.clip2(1) * 0.5 + 0.5;
 xpan1 = 1.0 - xpan0;
 ypan1 = 1.0 - ypan0;
 inx0 = in * xpan0;
 inx1 = in * xpan1;
 ^[inx1 * ypan0, inx0 * ypan0, inx1 * ypan1, inx0 * ypan1]
 }
 *kr1 { arg in, xpan0, ypan0;
 var xpan1, ypan1, inx0, inx1;
 xpan0 = xpan0.clip2(1) * 0.5 + 0.5;
 ypan0 = ypan0.clip2(1) * 0.5 + 0.5;
 xpan1 = 1.0 - xpan0;
 ypan1 = 1.0 - ypan0;
 inx0 = in * xpan0;
 inx1 = in * xpan1;
 ^[inx1 * ypan0, inx0 * ypan0, inx1 * ypan1, inx0 * ypan1]
 }
}
LinXFade2 : UGen {
  *ar { arg l, r, pan;
 ^this.multiChannelPerform('ar1', l, r, pan)
 }
 *kr { arg l, r, pan;
 ^this.multiChannelPerform('kr1', l, r, pan)
 }
 *ar1 { arg l, r, pan;
 pan = pan.clip2(1) * 0.5 + 0.5;
 ^l + (pan * (r - l))
 }
 *kr1 { arg l, r, pan;
 pan = pan.clip2(1) * 0.5 + 0.5;
 ^l + (pan * (r - l))
 }
}
LinXFade4 : UGen {
  *ar { arg lf, rf, lb, rb, xpan, ypan;
 ^this.multiChannelPerform('ar1', lf, rf, lb, rb, xpan, ypan)
 }
 *kr { arg lf, rf, lb, rb, xpan, ypan;
 ^this.multiChannelPerform('kr1', lf, rf, lb, rb, xpan, ypan)
 }
 *ar1 { arg lf, rf, lb, rb, xpan, ypan;
 var front, back;
 xpan = 0.5 * xpan.clip2(1) + 0.5;
 ypan = 0.5 * ypan.clip2(1) + 0.5;
 front = lf + (xpan * (rf - lf));
 back = lb + (xpan * (rb - lb));
 ^back + (ypan * (front - back))
 }
 *kr1 { arg lf, rf, lb, rb, xpan, ypan;
 var front, back;
 xpan = 0.5 * xpan.clip2(1) + 0.5;
 ypan = 0.5 * ypan.clip2(1) + 0.5;
 front = lf + (xpan * (rf - lf));
 back = lb + (xpan * (rb - lb));
 ^back + (ypan * (front - back))
 }
}
AudioIn : UGen {
 var <channel;
 
 *ar { arg argChannel = 1, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', argChannel, mul, add)
 }
 *ar1 { arg argChannel = 1, mul = 1.0, add = 0.0;
 ^super.ar1.init(argChannel, mul, add)
 }
 init { arg argChannel ... theInputs;
 channel = argChannel;
 inputs = theInputs;
 }
}
 

UnaryOpUGen : UGen { 
 var <operator;
 
 *new { arg selector, a;
 ^this.multiChannelPerform('new1', selector, a)
 }
 *new1 { arg selector, a; 
 ^super.ar1.init(selector, a)
 }
 
 init { arg theOperator, theInput;
 operator = theOperator;
 inputs = theInput;
 }
}
BinaryOpUGen : UGen { 
 var <operator;
 
 *new { arg selector, a, b;
 ^this.multiChannelPerform('new1', selector, a, b)
 }
 *new1 { arg selector, a, b; 
 ^super.ar1.init(selector, a, b)
 }
 
 init { arg theOperator ... theInputs;
 
 operator = theOperator;
 inputs = theInputs;
 }
}

FFT : MultiOutUGen {
 var fftsize, fftoffset=0, inputWindow, outputWindow, cosTable;
 
 *ar { arg fftsize, fftoffset, cosTable, inputWindow, outputWindow, 
 real, imag = 0.0; 
 ^this.multiChannelPerform('ar1', fftsize, fftoffset, 
 cosTable, inputWindow, outputWindow, real, imag)
 }
 *ar1 { arg fftsize, fftoffset, cosTable, inputWindow, outputWindow, 
 real, imag = 0.0; 
 ^super.ar1.init(fftsize, fftoffset, cosTable, inputWindow, outputWindow, 
 real, imag)
 }
 *sr { arg cosTable, inputWindow, outputWindow, 
 real, imag = 0.0; 
 ^this.multiChannelPerform('sr1', cosTable, inputWindow, outputWindow, 
 real, imag)
 }
 *sr1 { arg cosTable, inputWindow, outputWindow, 
 real, imag = 0.0; 
 ^super.sr1.srInit(cosTable, inputWindow, outputWindow, 
 real, imag)
 }
 
 *a2sr { arg fftsize, fftoffset, cosTable, inputWindow, outputWindow, 
 real, imag = 0.0;
 var in;
 in = A2S.sr(fftsize, fftoffset, real, imag);
 ^this.sr(cosTable, inputWindow, outputWindow, in.real, in.imag)
 }
 
 *s2ar { arg cosTable, inputWindow, outputWindow, 
 real, imag = 0.0;
 var out;
 out = this.sr(cosTable, inputWindow, outputWindow, real, imag)
 ^S2A.ar(out.real, out.imag);
 }
 
  init { arg argFFTsize, argFFToffset, argCosTable, 
 argInputWindow, argOutputWindow ... theInputs;
 fftsize = argFFTsize;
 fftoffset = argFFToffset;
 cosTable = argCosTable;
 inputWindow = argInputWindow;
 outputWindow = argOutputWindow;
 inputs = theInputs;
 channels = Complex.new( OutputProxy.ar1(this), OutputProxy.ar1(this) );
 ^channels
 }
 srInit { arg argCosTable, argInputWindow, argOutputWindow ... theInputs;
 cosTable = argCosTable;
 inputWindow = argInputWindow;
 outputWindow = argOutputWindow;
 inputs = theInputs;
 channels = Complex.new( OutputProxy.sr1(this), OutputProxy.sr1(this) );
 ^channels
 }
}
IFFT : FFT {
 }
A2S : MultiOutUGen {
 var fftsize, fftoffset=0;
 *sr { arg fftsize, fftoffset, real, imag = 0.0; 
 ^this.multiChannelPerform('sr1', fftsize, fftoffset, real, imag)
 }
 *sr1 { arg fftsize, fftoffset, real, imag = 0.0; 
 ^super.sr1.init(fftsize, fftoffset, imag, real, imag)
 }
 init { arg argFFTsize, argFFToffset, imag ... theInputs;
 fftsize = argFFTsize;
 fftoffset = argFFToffset;
 inputs = theInputs;
 if (imag == 0.0, {
 channels = Complex.new( OutputProxy.sr1(this), 0.0 );
 },{
 channels = Complex.new( OutputProxy.sr1(this), OutputProxy.ar1(this) );
 });
 ^channels
 }
}
S2A : MultiOutUGen {
 *ar { arg real, imag = 0.0; 
 ^this.multiChannelPerform('ar1', real, imag)
 }
 *ar1 { arg real, imag = 0.0; 
 ^super.ar1.init(imag, real, imag)
 }
 init { arg imag ... theInputs;
 inputs = theInputs;
 if (imag == 0.0, {
 channels = Complex.new( OutputProxy.ar1(this), 0.0 );
 },{
 channels = Complex.new( OutputProxy.ar1(this), OutputProxy.ar1(this) );
 });
 ^channels
 }
}
SROsc : MultiOutUGen {
 var fftoffset=0, real, imag;
 *sr { arg fftoffset, realBuf, imaginaryBuf = 0.0; 
 ^this.multiChannelPerform('sr1', fftoffset, realBuf, imaginaryBuf)
 }
 *sr1 { arg fftoffset, realBuf, imaginaryBuf = 0.0; 
 ^super.sr1.init(fftoffset, realBuf, imaginaryBuf)
 }
 init { arg argFFToffset, realBuf, imaginaryBuf;
 fftoffset = argFFToffset;
 real = realBuf;
 imag = imaginaryBuf;
 if (imag == 0.0, {
 channels = Complex.new( OutputProxy.ar1(this), 0.0 );
 },{
 channels = Complex.new( OutputProxy.ar1(this), OutputProxy.ar1(this) );
 });
 ^channels
 }
}
BinShift : MultiOutUGen {
 *sr { arg real, imag = 0.0, shift = 0.0, stretch = 1.0;
 ^this.multiChannelPerform('sr1', real, imag, shift, stretch)
 }
 *sr1 { arg real, imag = 0.0, shift = 0.0, stretch = 1.0;
 ^super.sr1.init(imag, real, imag, shift, stretch)
 }
 init { arg imag ... theInputs;
 inputs = theInputs;
 if (imag == 0.0, {
 channels = Complex.new( OutputProxy.ar1(this), 0.0 );
 },{
 channels = Complex.new( OutputProxy.ar1(this), OutputProxy.ar1(this) );
 });
 ^channels
 }
}

Pan2 : MultiOutUGen {
 
 *ar { arg in, pos = 0.0, level = 1.0;
 ^this.multiChannelPerform('ar1', in, pos, level )
 }
 *ar1 { arg in, pos = 0.0, level = 1.0;
 ^super.ar1.init( in, pos, level )
 }
 init { arg ... theInputs;
 inputs = theInputs; 
 channels = [ OutputProxy.ar1(this), OutputProxy.ar1(this) ];
 ^channels
 }
}
Pan4 : MultiOutUGen {
 
 *ar { arg in, xpos = 0.0, ypos = 0.0, level = 1.0;
 ^this.multiChannelPerform('ar1', in, xpos, ypos, level )
 }
 *ar1 { arg in, xpos = 0.0, ypos = 0.0, level = 1.0;
 ^super.ar1.init( in, xpos, ypos, level )
 }
 init { arg ... theInputs;
 inputs = theInputs; 
 channels = [ OutputProxy.ar1(this), OutputProxy.ar1(this),
 OutputProxy.ar1(this), OutputProxy.ar1(this) ];
 ^channels
 }
}
PanB : UGen {
 var channels;
 
 *ar { arg in, azimuth, elevation, gain;
 ^this.multiChannelPerform('ar1', in, azimuth, elevation, gain )
 }
 *ar1 { arg in, azimuth, elevation, gain;
 ^super.ar1.init( in, azimuth, elevation, gain )
 }
 init { arg ... theInputs;
 inputs = theInputs; 
 channels = [ OutputProxy.ar1(this), OutputProxy.ar1(this),
 OutputProxy.ar1(this), OutputProxy.ar1(this) ];
 ^channels
 }
}
PanAz : MultiOutUGen {
 
 *ar { arg numChans, in, pos = 0.0, level = 1.0, width = 2.0;
 ^this.multiChannelPerform('ar1', numChans, in, pos, level, width )
 }
 *ar1 { arg numChans, in, pos = 0.0, level = 1.0, width = 2.0;
 ^super.ar1.init( numChans, in, pos, level, width )
 }
 init { arg numChans ... theInputs;
 inputs = theInputs; 
 channels = Array.fill(numChans, { OutputProxy.ar1(this) });
 ^channels
 }
}

Pause : MultiOutUGen { 
 var runningSynth;
 
  *ar { arg argEventFunc, level = 0.0;
 
 ^this.multiChannelPerform('ar1', argEventFunc, level)
 }
 *ar1 { arg argEventFunc, level = 0.0;
 ^super.ar1.init(argEventFunc, level)
 }
  
  init { arg argEventFunc, theInput;
 runningSynth = Synth.new(argEventFunc);
 runningSynth.newTempoBase(synth.tempo);
 
 inputs = theInput;
 ^this.initOutputs;
 }
 initOutputs {
 var i = 0, outChan, numChannels, outputs;
 if (runningSynth.notNil, {
 outputs = runningSynth.outputs;
 if (outputs.isKindOf(Array), {
 numChannels = outputs.size;
 channels = Array.new(numChannels);
 while ({ i < numChannels },{
 outChan = OutputProxy.ar1(this);
 channels.add(outChan);
 i = i + 1;
 });
 ^channels
 },{
 outChan = OutputProxy.ar1(this);
 channels = Array.with(outChan);
 ^outChan
 });
 });
 }
}

Spawn : MultiOutUGen { 
 var newEventFunction;
 var <>nextTime = 0;
 var nextTimeValue;
 var defaultNextTime;
 var maxRepeats;
 var numRepeats = 0;
 var <runningSynths;  var recentSynth; 
 var <>maxVoices;  var stop = false;  var envir;  var <>bufsync = false;    
  *ar { arg newEventFunc, numChannels = 1, nextTime = nil, maxRepeats,
 mul = 1.0, add = 0.0;
 
 ^this.multiChannelPerform('ar1', newEventFunc, numChannels, nextTime, maxRepeats,
 mul, add)
 }
 *ar1 { arg argEventFunc, argNumChannels = 1, argNextTime = 1.0, argMaxRepeats, 
 mul = 1.0, add = 0.0;
 ^super.ar1.init(argEventFunc, argNumChannels, argNextTime, argMaxRepeats, 
 mul, add)
 }
 *ugen_ar1 {  ^super.ar1
 }
  
 stop { 
 stop = true; 
 }
 
 releaseAll {
 _Spawn_ReleaseAll
 ^this.primitiveFailed
 }
 stealAll {
 _Spawn_StealAll
 ^this.primitiveFailed
 }
  init { arg argEventFunc, argNumChannels, argNextTime, argMaxRepeats ... theInputs;
 newEventFunction = argEventFunc;
 inputs = theInputs;
 defaultNextTime = argNextTime;
 maxRepeats = argMaxRepeats;
 runningSynths = Array.new(16);
 envir = currentEnvironment;
 
 ^this.initOutputs(argNumChannels);
 }
 
 getNewEvent {
 var newSynth;
  
 newSynth = Synth.new({ arg synth;
 newEventFunction.spawn(this, numRepeats, synth);
 });
 nextTimeValue = nextTime.value(this, numRepeats);
 ^newSynth
 }
 
}
TSpawn : Spawn { 
 
  *ar { arg newEventFunc, numChannels = 1, maxRepeats,
 trig = 0.0, mul = 1.0, add = 0.0;
 ^this.multiChannelPerform('ar1', newEventFunc, numChannels, maxRepeats,
 trig, mul, add);
 }
 *ar1 { arg argEventFunc, argNumChannels = 1, argMaxRepeats, 
 trig = 0.0, mul = 1.0, add = 0.0;
 ^super.ugen_ar1.init(argEventFunc, argNumChannels, argMaxRepeats,
 trig, mul, add);
 }
 
 trigger { arg ... extraArgs;
  _TSpawn_Trigger
 ^this.primitiveFailed
 }
 triggerSynth { arg newSynth, bufSync = false;
  _TSpawn_TriggerSynth
 ^this.primitiveFailed
 }
 
  init { arg argEventFunc, argNumChannels, argMaxRepeats ... theInputs;
 newEventFunction = argEventFunc;
 inputs = theInputs;
 maxRepeats = argMaxRepeats;
 runningSynths = Array.new(16);
 envir = currentEnvironment;
 
 ^this.initOutputs(argNumChannels);
 }
 triggerNewEvent { arg ... extraArgs;
 var newSynth;
  
 newSynth = Synth.new({ arg synth;
 newEventFunction.spawn(this, numRepeats, synth, extraArgs);
 });
 nextTimeValue = nextTime.value(this, numRepeats);
 ^newSynth
 }
}
Voicer : Spawn {
 var midiChannel;  var triggerMode = 'normal'; 
 *ar { arg newEventFunc, numChannels = 1, midiChan=1, maxVoices=8,
 mul = 1.0, add = 0.0;
 
 ^this.multiChannelPerform('ar1', newEventFunc, numChannels, midiChan, 
 maxVoices, mul, add)
 }
 *ar1 { arg argNewEventFunction, argNumChannels = 1, argMIDIChan=1, argMaxVoices=8,
 mul = 1.0, add = 0.0;
 ^super.ugen_ar1.init(argNewEventFunction, argNumChannels, argMIDIChan, argMaxVoices,
 mul, add)
 }
 noteOn { arg chan=1, note=60, veloc=64;
 _Voicer_NoteOn
 ^this.primitiveFailed
 }
 noteDur { arg chan=1, note=60, veloc=64, dur=1.0;
 this.noteOn(chan, note, veloc);
 synth.sched(dur, { this.noteOn(chan, note, 0); });
 }
 sustainPedal { arg isOn=true;
 _Voicer_SustainPedal
 ^this.primitiveFailed
 }
 sostenutoPedal { arg isOn=true;
 _Voicer_SostenutoPedal
 ^this.primitiveFailed
 }
  init { arg argEventFunc, argNumChannels, argMIDIChan, argMaxVoices ... theInputs;
 newEventFunction = argEventFunc;
 inputs = theInputs;
 midiChannel = argMIDIChan;
 if (argMaxVoices >= 0, {
 maxVoices = argMaxVoices;
 },{
 maxVoices = argMaxVoices.neg;
 triggerMode = 'toggle';
 });
 runningSynths = Array.new(maxVoices + 1);
 
 ^this.initOutputs(argNumChannels);
 }
 getNewEvent { arg chan, note, veloc;
 var newSynth;
  
 newSynth = Synth.new({ arg synth;
 newEventFunction.spawn(this, numRepeats, synth, nil, chan, note, veloc);
 });
 nextTimeValue = nextTime.value(this, numRepeats, synth);
 ^newSynth
 }
 trigger { arg argChan, argNote, argVeloc;
  _Voicer_Trigger
 ^this.primitiveFailed
 }
}

Mix : UGen {
 *ar { arg channelsArray;
 ^this.multiChannelPerformList('ar1', channelsArray);
 }
 *kr { arg channelsArray;
 ^this.multiChannelPerformList('kr1', channelsArray);
 }
 *ar1 { arg ... channelsArray;
 ^super.ar1.inputs_(channelsArray.copy)
 }
 *kr1 { arg ... channelsArray;
 ^super.kr1.inputs_(channelsArray.copy)
 }
 
  *arFill { arg n, function;
 ^this.multiChannelPerformList('ar1', Array.fill(n, function));
 }
 *krFill { arg n, function;
 ^this.multiChannelPerformList('kr1', Array.fill(n, function));
 }
 
 init { ^this.shouldNotImplement(thisMethod) }
}

Sink : UGen {
 
 *ar { arg theInputArray;
 ^this.multiChannelPerformList('ar1', theInputArray)
 }
 *kr { arg theInputArray;
 ^this.multiChannelPerformList('kr1', theInputArray)
 }
 *ar1 { arg ... theInputArray;
 ^super.ar1.inputs_(theInputArray.copy)
 }
 *kr1 { arg ... theInputArray;
 ^super.kr1.inputs_(theInputArray.copy)
 } 
 init { ^this.shouldNotImplement(thisMethod) }
}
 
Stream : AbstractFunction { 
   next { ^this.subclassResponsibility(thisMethod) }
 value { ^this.next }
 valueArray { ^this.next }
 
 nextN { arg n;
 Array.fill(n, { this.next; });
 }
 all {
 arg list;
 list = List.new;
 this.do({ arg item; list.add(item); });
 ^list
 }
 
  put { arg item;
 ^this.subclassResponsibility(thisMethod) 
 }
 putN { arg n, item; 
 n.do({ this.put(item); });
 }
 putAll { arg aCollection;
 aCollection.do({ arg item; this.put(item); });
 }
 
 do { arg function;
 var item;
 while ({ 
 item = this.next; 
 item.notNil 
 },{
 function.value(item);
 });
 }
 
  collect { arg argCollectFunc;
  var nextFunc, resetFunc;
 nextFunc = { arg inval;
 inval = this.next(inval);
 if ( inval.notNil, {
 argCollectFunc.value(inval) 
 },{ 
 nil 
 })
 };
 resetFunc = { this.reset };
 ^FuncStream.new(nextFunc, resetFunc);
 }
 reject { arg function;
  var nextFunc, resetFunc;
 nextFunc = { arg inval;
 inval = this.next(inval);
 while ({ 
 inval.notNil and: { function.value(inval) }
 },{
 inval = this.next(inval);
 });
 inval
 };
 resetFunc = { this.reset };
 ^FuncStream.new(nextFunc, resetFunc);
 }
 select { arg function;
  var nextFunc, resetFunc;
 nextFunc = { arg inval;
 inval = this.next(inval);
 while ({ 
 inval.notNil and: { function.value(inval).not }
 },{
 inval = this.next(inval);
 });
 inval
 };
 resetFunc = { this.reset };
 ^FuncStream.new(nextFunc, resetFunc);
 }
 
 dot { arg function, stream;
  ^FuncStream.new(
 { 
 var x, y; 
 x = this.next; 
 y = stream.next;
 if ( x.notNil and: { y.notNil }, {
 function.value(x, y)
 });
 },
 { this.reset; stream.reset; }
 );
 }
 interlace { arg function, stream;
  var nextx, nexty;
 
 nextx = this.next;
 nexty = stream.next;
 ^FuncStream.new({
 var val;
 if ( nextx.isNil, {
 if ( nexty.isNil, {nil},{ val=nexty; nexty = stream.next; val });
 },{
 if ( nexty.isNil or: { function.value(nextx,nexty) }, 
 { val=nextx; nextx = this.next; val },
 { val=nexty; nexty = stream.next; val }
 ); 
 });
 },
 { 
 this.reset; stream.reset; 
 nextx = this.next;
 nexty = stream.next;
 });
 }
 ++ { arg stream;
  ^this.interlace(true, stream);
 }
 collate { arg stream;
  ^this.interlace({ arg x, y; x < y }, stream);
 }
 
  composeUnaryOp { arg argSelector;
 ^UnaryOpStream.new(argSelector, this)
 }
 composeBinaryOp { arg argSelector, argStream;
 ^BinaryOpStream.new(argSelector, this, argStream)
 }
 reverseComposeBinaryOp { arg argSelector, argStream;
 ^BinaryOpStream.new(argSelector, argStream, this)
 }
 composeNAryOp { arg argSelector, anArgList;
 ^this.notYetImplemented
 }
 
 spawn { arg spawn, eventCount, synth;
  this.next.spawn(spawn, eventCount, synth); 
 }
}
FuncStream : Stream {
 var <>nextFunc;  var <>resetFunc;  *new { arg nextFunc, resetFunc; 
 ^super.new.nextFunc_(nextFunc).resetFunc_(resetFunc)
 }
 next { arg inval;
 ^nextFunc.value(inval) 
 }
 reset { 
 ^resetFunc.value
 }
}


UnaryOpStream : Stream { 
 var >operator, >a;
 
 *new { arg operator, a;
 ^super.new.a_(a).operator_(operator)
 }
 next {
 var vala;
 vala = a.next;
 if (vala.isNil, { ^nil },{ ^vala.perform(operator); });
 }
 reset { a.reset }
}
BinaryOpStream : Stream { 
 var >operator, >a, >b;
 
 *new { arg operator, a, b; 
 ^super.new.a_(a).b_(b).operator_(operator)
 }
 next { 
 var vala, valb;
 vala = a.next;
 if (vala.isNil, { ^nil });
 valb = b.next;
 if (valb.isNil, { ^nil });
 
 ^vala.perform(operator, valb);
 }
 reset { a.reset; b.reset }
}
IOStream : Stream {
 reset { this.pos = 0; }
 skip { arg n;
 this.pos = this.pos + n;
 }
 comma { this.put(Char.comma);}
 space { this.put(Char.space); }
 nl { this.put(Char.nl); }
 ff { this.put(Char.ff); }
 tab { this.put(Char.tab); }
 << { arg item;
 item.printOn(this); 
 }
 <<< { arg item;
 item.storeOn(this); 
 }
 <<* { arg collection;
 collection.printItemsOn(this); 
 }
 <<<* { arg collection;
 collection.storeItemsOn(this); 
 }
 
 readUpTo { arg delimiter = $\f;
 var string, char;
 string = String.new;
 while ({
 char = this.next;
 char.notNil and: { char != delimiter }
 },{
 string = string.add(char);
 });
 ^string
 }
}
CollStream : IOStream {
 var <>collection, <pos = 0;
 
 *new {
 ^super.new.collection_(String.new(128))
 }
 *on { arg aCollection; 
 ^super.new.on(aCollection); 
 }
 on { arg aCollection;
 collection = aCollection;
 this.reset;
 }
 
  pos_ { arg toPos;
 pos = toPos.clip(0, collection.size);
 }
 
 next {
 if (pos >= collection.size, {
 ^nil
 },{
 pos = pos + 1;
 ^collection.at(pos - 1);
 })
 }
 nextN { arg n;
 collection.species.fill(n, { this.next; });
 }
 contents {
 ^collection.copyRange(0, collection.size-1);
 }
 put { arg item;
  if (pos >= collection.size, {
 pos = collection.size + 1;
 collection = collection.add(item);
 },{
 collection.put(pos, item);
 pos = pos + 1;
 })
 }
 putAll { arg aCollection;
 collection = collection.overWrite(aCollection, pos);
 pos = pos + aCollection.size;
 }
 
 


}
LimitedWriteStream : CollStream {
 var <>limit, <>limitFunc;
 
 put { arg item;
 var newpos;
 newpos = pos + 1;
 if (newpos > limit, {
 limitFunc.value;
 limitFunc = nil;
 },{
 super.put(item);
 });
 }
 putAll { arg aCollection;
 var newpos;
 newpos = pos + aCollection.size;
 if (newpos > limit, {
 aCollection = aCollection.copyFromStart(limit - pos - 1);
 collection = collection.overWrite(aCollection, pos);
 pos = limit;
 limitFunc.value;
 limitFunc = nil;
 },{
 collection = collection.overWrite(aCollection, pos);
 pos = newpos;
 });
 }
 
}
Post {
 * << { arg item;
 item.printOn(this); 
 }
 * <<< { arg item;
 item.storeOn(this); 
 }
 * <<* { arg collection;
 collection.printItemsOn(this); 
 }
 * <<<* { arg collection;
 collection.storeItemsOn(this); 
 }
 * put { arg item;
 item.post;
 }
 * putAll { arg aCollection;
 aCollection.post;
 }
 * comma { this.put(Char.comma);}
 * space { this.put(Char.space); }
 * nl { this.put(Char.nl); }
 * ff { this.put(Char.ff); }
 * tab { this.put(Char.tab); }
}
File : IOStream {
 var fileptr=0;
  
 *new { arg pathName, mode; 
 ^super.new.open(pathName, mode);
 }
 open { arg pathName, mode;
 
 _FileOpen 
 ^this.primitiveFailed;
 }
 close {   _FileClose 
 ^this.primitiveFailed;
 }
 isOpen {
 thisProcess.toolboxSync;  ^fileptr != 0 
 }
 length {  ^this.prFileRequestSync(\length);
 }
 pos {  ^this.prFileRequestSync(\pos);
 }
 pos_ { arg toPos;
 toPos = toPos.clip(0, this.length - 1);
 this.seek(toPos, 0);
 }
 seek { arg offset, origin; 
     
 _FileSeek
 ^this.primitiveFailed;
 }
 
 next { ^this.getChar }
 nextN { arg n;
 String.fill(n, { this.next; });
 }
 contents {
 ^this.readAllString;
 }
 
 put { arg item; this.write(item); }
 putAll { arg aCollection;
 if (aCollection.isKindOf(RawArray), {
 this.write( aCollection );
 },{
 aCollection.do({ arg item; this.write(item) });
 });
 }
 write { arg item; 
 
 _FileWrite 
 ^this.primitiveFailed;
 }
 
 getLine { ^this.prFileRequestSync(\getLine, String.new(1024)); }
 
   getChar { ^this.prFileRequestSync(\getChar); }
 getInt8 { ^this.prFileRequestSync(\getInt8); }
 getInt16 { ^this.prFileRequestSync(\getInt16); }
 getInt32 { ^this.prFileRequestSync(\getInt32); }
 getFloat { ^this.prFileRequestSync(\getFloat); }
 getDouble { ^this.prFileRequestSync(\getDouble); }
 
   readAllString { ^this.prFileRequestSync(\readAllString, String.new(this.length)); }
 readAllInt8 { ^this.prFileRequestSync(\readAllInt8, Int8Array.new(this.length)); }
 readAllInt16 { ^this.prFileRequestSync(\readAllInt16, Int16Array.new(this.length div: 2)); }
 readAllInt32 { ^this.prFileRequestSync(\readAllInt32, Int32Array.new(this.length div: 4)); }
 readAllRGB { ^this.prFileRequestSync(\readAllRGB, RGBArray.new(this.length div: 4)); }
 readAllFloat { ^this.prFileRequestSync(\readAllFloat, FloatArray.new(this.length div: 4)); }
 readAllSignal { ^this.prFileRequestSync(\readAllSignal, Signal.new(this.length div: 4)); }
 readAllDouble { ^this.prFileRequestSync(\readAllDouble, Double.new(this.length div: 8)); }
 
 putChar { arg aChar; ^this.prFileCommand(\putChar, aChar); }
 putInt8 { arg anInteger; ^this.prFileCommand(\putInt8, anInteger); }
 putInt16 { arg anInteger; ^this.prFileCommand(\putInt16, anInteger); }
 putInt32 { arg anInteger; ^this.prFileCommand(\putInt32, anInteger); }
 putFloat { arg aFloat; ^this.prFileCommand(\putFloat, aFloat); }
 putDouble { arg aFloat; ^this.prFileCommand(\putDouble, aFloat); }
 putString { arg aString; ^this.prFileCommand(\putString, aString); }
 
  prFileCommand { arg selector, optionalData;
  _FileCommand;
 ^this.primitiveFailed;
 }
 prFileRequest { arg selector, completionFunction, optionalData;
  _FileRequest;
 ^this.primitiveFailed;
 }
 prFileRequestSync { arg selector, optionalData;
  var result;
 this.prFileRequest(
 selector, 
 { arg inResult; result = inResult; thisProcess.toolboxRelease; }, 
 optionalData
 );
 thisProcess.toolboxAcquire;
 ^result
 }
}


Thread : Stream {
 var state=0, func, stack, stackSize=0, method, block, frame, ip=0, sp=0;
 var top=0, numpop=0, returnLevels=0, level=0, receiver, numArgsPushed=0;
 var parent, terminalValue;
 var nextThread, acquireCount, parentIsTask=false;
 var <primitiveError=0, <primitiveIndex=0, <randSeed=0;
 *new { arg func, stackSize=512, seed = -1;
 ^super.new.init(func, stackSize, seed)
 }
 init { arg argFunc, argStackSize=512, argSeed = -1;
 _ThreadInit
 ^this.primitiveFailed
 }
 
 run { arg inval;
 _ThreadRun
 ^this.primitiveFailed
 }
 
 randSeed_ { arg newSeed; 
 if ( newSeed.isInteger, { 
 randSeed = newSeed 
 },{
 error("random seed must be an integer.\n")
 });
 }
 failedPrimitiveName { _PrimName }
 
  next { ^this }
 value { ^this }
 valueArray { ^this }
 deepCopyWithDict { ^this }
 shallowCopy { ^this }
 
  prStart { arg inval;
 func.value(inval);
 this.prRunNextThread;
 }
 prRunNextThread {
 _RunNextThread
 }
}
Routine : Thread {
 run { 
 ^this.shouldNotImplement(thisMethod)
 }
 
  resume { arg inval;
 _RoutineResume
 ^this.primitiveFailed
 }
 next { arg inval;
 _RoutineResume
 ^this.primitiveFailed
 }
 value { arg inval;
 _RoutineResume
 ^this.primitiveFailed
 }
 valueArray { arg inval;
 ^this.value(inval) 
 }
 
 reset { arg inval;
 _RoutineReset
 ^this.primitiveFailed
 }
 wait { arg synth, time;
 synth.sched(time, { this.next; });
 this.yield;
 }
 
  prStart { arg inval;
 func.value(inval);
 
  nil.alwaysYield;
 }
}
Task : Thread {
 var <>synth, <time=0.0;
 *new { arg func, synth, startTime=0.0, stackSize=512, seed = -1;
 var newtask;
 newtask = super.new(func, stackSize, seed).synth_(synth ? thisSynth);
 newtask.synth.sched(startTime.value, newtask);
 ^newtask
 }
 wait { arg beats;
 synth.sched(beats.value, this);
 this.prWait;
 }
 
  value { arg argSynth, argTime, argTask;
 _TaskRun
 }
 prWait {
 _TaskWait
 }
 prStart { arg inval;
 func.value(inval);
 this.prEnd;
 }
 prEnd {
 _TaskEnd
 }
 prReady {
 synth.sched(time - synth.beats, this);
 } 
}
Semaphore {
 var <count=1, firstWaiting, lastWaiting;
 
 *new { arg count=1;
 ^super.new.init(count)
 }
 init { arg argCount;
 count = argCount;
 }
 release { arg number=1;
 _SemaphoreRelease
 ^this.primitiveFailed
 }
 acquire { arg number=1;
 _SemaphoreAcquire
 ^this.primitiveFailed
 }
}

ListPattern : Pattern {
 var <>list, <>repeats=1;
 
 *new { arg list, repeats=1;
 ^super.new.list_(list).repeats_(repeats)
 }
 copy {
 ^super.copy.list_(list.copy)
 }
}
Pseq : ListPattern {
 var <>offset;
 *new { arg list, repeats=1, offset=0;
 ^super.new(list, repeats).offset_(offset)
 }
 asStream { 
 ^Routine.new({ arg inval;
 var item, offsetValue;
 offsetValue = offset.value;
 if (inval.eventAt('reverse') == true, {
 repeats.value.do({ arg j;
 list.size.reverseDo({ arg i;
 item = list @@ (i + offsetValue);
 inval = item.embedInStream(inval);
 });
 });
 },{
 repeats.value.do({ arg j;
 list.size.do({ arg i;
 item = list @@ (i + offsetValue);
 inval = item.embedInStream(inval);
 });
 });
 });
 });
 }
}
Pser : Pseq {
 asStream {
 ^Routine.new({ arg inval;
 var item, offsetValue;
 
 offsetValue = offset.value;
 if (inval.eventAt('reverse') == true, {
 repeats.value.reverseDo({ arg i;
 item = list @@ (i + offsetValue);
 inval = item.embedInStream(inval);
 });
 },{
 repeats.value.do({ arg i;
 item = list @@ (i + offsetValue);
 inval = item.embedInStream(inval);
 });
 });
 });
 }
} 
Pshuf : ListPattern {
 asStream { 
 ^Routine.new({ arg inval;
 var item, stream;
 
 list = list.scramble;
 repeats.value.do({ arg j;
 list.size.do({ arg i;
 item = list @@ i;
 inval = item.embedInStream(inval);
 });
 });
 });
 }
}
Prand : ListPattern {
 asStream { 
 ^Routine.new({ arg inval;
 var item;
 
 repeats.value.do({ arg i;
 item = list.at(list.size.rand);
 inval = item.embedInStream(inval);
 });
 });
 }
} 
Pxrand : ListPattern {
 asStream { 
 ^Routine.new({ arg inval; 
 var item, index, size;
 index = list.size.rand;
 repeats.value.do({ arg i;
 size = list.size;
 index = (index + (size - 1).rand + 1) % size;
 item = list.at(index);
 inval = item.embedInStream(inval);
 });
 });
 }
} 
Pwrand : ListPattern {
 var <>weights;
 *new { arg list, weights, repeats=1;
 ^super.new(list, repeats).weights_(weights)
 }
 asStream { 
 ^Routine.new({ arg inval;
 var item;
 repeats.value.do({ arg i;
 item = list.at(weights.windex);
 inval = item.embedInStream(inval);
 });
 });
 }
} 
Pfsm : ListPattern {
 asStream { 
 ^Routine.new({ arg inval;
 var item, index=0, maxState;
 maxState = ((list.size - 1) div: 2) - 1;
 repeats.value.do({
 index = 0;
 while({
 index = list.at(index).choose.clip(0, maxState) * 2 + 2;
 item = list.at(index - 1);
 inval = item.embedInStream(inval);
 inval.notNil;
 });
 });
 });
 }
} 
Ppar : ListPattern {
 initStreams { arg priorityQ;
 list.do({ arg pattern, i; 
 priorityQ.put(0.0, pattern.asStream);
 });
 }
 asStream {
 var priorityQ, assn;
 
 priorityQ = PriorityQueue.new;
 
 ^Routine.new({ arg inval;
 
 repeats.value.do({ arg j;
 var outval, stream, nexttime, now = 0.0;
 this.initStreams(priorityQ);
 
  if (priorityQ.notEmpty and: { (nexttime = priorityQ.topPriority) > 0.0 }, {
 outval = inval;
 outval.put(\freq, \rest); 
 outval.put(\delta, nexttime);
 
 inval = outval.yield;
 now = nexttime; 
 });
 
 while({
 priorityQ.notEmpty
 },{
 stream = priorityQ.pop;
 outval = stream.next(inval);
 if (outval.isNil, {
 nexttime = priorityQ.topPriority;
 if (nexttime.notNil, {
   outval = inval;
 outval.put(\freq, \rest); 
 outval.put(\delta, nexttime - now);
 
 inval = outval.yield;
 now = nexttime; 
 },{
 priorityQ.clear;
 }); 
 },{ 
  priorityQ.put(now + outval.delta, stream);
 nexttime = priorityQ.topPriority;
 outval.put(\delta, nexttime - now);
 
 inval = outval.yield;
 now = nexttime; 
 }); 
 });
 });
 });
 }
} 
Ptpar : Ppar {
 initStreams { arg priorityQ;
 forBy(0, list.size-1, 2, { arg i;
 priorityQ.put(list.at(i).value, list.at(i+1).asStream);
 });
 }
}
Pswitch : Pattern {
 var <>list, <>which=0;
 *new { arg list, which=0;
 ^super.new.list_(list).which_(which)
 }
 asStream { 
 ^Routine.new({ arg inval;
 var item, index, indexStream;
 
 indexStream = which.asStream;
 while ({
 (index = indexStream.next).notNil;
 },{
 inval = list.wrapAt(index).embedInStream(inval);
 });
 });
 }
}
Pswitch1 : Pswitch { 
 asStream { 
 var streamList, indexStream;
 
 streamList = list.collect({ arg pattern; pattern.asStream; });
 indexStream = which.asStream;
 
 ^FuncStream.new({ arg inval;
 var index;
 if ((index = indexStream.next).notNil, {
 streamList.wrapAt(index).next(inval);
 });
 },{
 streamList.do({ arg stream; stream.reset; });
 });
 }
} 
Ptuple : ListPattern {
 asStream { 
 ^Routine.new({ arg inval;
 var item, streams, tuple, outval;
 
 streams = list.collect({ arg item; item.asStream });
 
 repeats.value.do({ arg j;
 var sawNil = false;
 
 while ({
 tuple = Array.new(streams.size);
 streams.do({ arg stream; 
 outval = stream.next(inval);
 if (outval.isNil, { sawNil = true; });
 tuple.add(outval);
 });
 sawNil.not
 },{
 inval = yield(tuple);
 });
 
 });
 });
 }
}
Place : Pseq {
 asStream { 
 ^Routine.new({ arg inval;
 var item, offsetValue;
 
 offsetValue = offset.value;
 if (inval.eventAt('reverse') == true, {
 repeats.value.do({ arg j;
 list.size.reverseDo({ arg i;
 item = list @@ (i + offsetValue);
 if (item.isKindOf(SequenceableCollection), {
 item = item @@ j;
 });
 inval = item.embedInStream(inval);
 });
 });
 },{
 repeats.value.do({ arg j;
 list.size.do({ arg i;
 item = list @@ (i + offsetValue);
 if (item.isKindOf(SequenceableCollection), {
 item = item @@ j;
 });
 inval = item.embedInStream(inval);
 });
 });
 });
 });
 }
}
FilterPattern : Pattern {
 var <>pattern;
 *new { arg pattern;
 ^super.new.pattern_(pattern)
 }
}
Pn : FilterPattern {
 var <>repeats;
 *new { arg pattern, repeats;
 ^super.new(pattern).repeats_(repeats)
 }
 asStream {
 ^Routine.new({ arg inval;
 repeats.do({
 inval = pattern.embedInStream(inval);
 });
 });
 }
}
FuncFilterPattern : FilterPattern {
 var <>func;
 
 *new { arg func, pattern;
 ^super.new(pattern).func_(func)
 }
}
Pcollect : FuncFilterPattern {
 asStream {
 ^pattern.asStream.collect(func);
 }
}
Pselect : FuncFilterPattern {
 asStream {
 ^pattern.asStream.select(func);
 }
}
Preject : FuncFilterPattern {
 asStream {
 ^pattern.asStream.reject(func);
 }
}
Pfset : FuncFilterPattern {
 asStream {
 var stream, envir;
 envir = Event.make(func);
 
 stream = pattern.asStream;
 
 ^FuncStream.new({ arg event;
 event = event.copy;
 event.putAll(envir);
 stream.next(event);
 });
 }
}
Pset : FilterPattern {
 var <>name, <>value;
 
 *new { arg name, value, pattern;
 ^super.new(pattern).name_(name).value_(value)
 }
 asStream {
 var stream;
 
 stream = pattern.asStream;
 
 ^FuncStream.new({ arg event;
 event = event.copy;
 event.put(name, value);
 stream.next(event);
 });
 }
}
Padd : Pset {
 asStream {
 var stream;
 
 stream = pattern.asStream;
 
 ^FuncStream.new({ arg event;
 event = event.copy;
 event.put(name, event.at(name) + value);
 stream.next(event);
 });
 }
}
Pmul : Pset {
 asStream {
 var stream;
 
 stream = pattern.asStream;
 
 ^FuncStream.new({ arg event;
 event = event.copy;
 event.put(name, event.at(name) * value);
 stream.next(event);
 });
 }
}
Pnot : FilterPattern {
 var <>name;
 
 *new { arg name, pattern;
 ^super.new(pattern).name_(name)
 }
 asStream {
 var stream;
 
 stream = pattern.asStream;
 
 ^FuncStream.new({ arg event;
 event = event.copy;
 event.put(name, event.at(name).not);
 stream.next(event);
 });
 }
}
Psetp : Pset {
 asStream {
 ^Routine.new({ arg inevent;
 var valStream, evtStream, val, outevent;
 valStream = value.asStream;
 while({
 val = valStream.next;
 val.notNil
 },{
 evtStream = pattern.asStream;
 while({
 outevent = evtStream.next(inevent);
 outevent.notNil
 },{
 outevent.put(name, val);
 inevent = outevent.yield;
 });
 });
 });
 }
}
Paddp : Pset {
 asStream {
 ^Routine.new({ arg inevent;
 var valStream, evtStream, val, outevent;
 
 valStream = value.asStream;
 while({
 val = valStream.next;
 val.notNil
 },{
 evtStream = pattern.asStream;
 while({
 outevent = evtStream.next(inevent);
 outevent.notNil
 },{
 outevent.put(name, outevent.at(name) + val);
 inevent = outevent.yield;
 });
 });
 });
 }
}
Pmulp : Pset {
 asStream {
 ^Routine.new({ arg inevent;
 var valStream, evtStream, val, outevent;
 
 valStream = value.asStream;
 while({
 val = valStream.next;
 val.notNil
 },{
 evtStream = pattern.asStream;
 while({
 outevent = evtStream.next(inevent);
 outevent.notNil
 },{
 outevent.put(name, outevent.at(name) * val);
 inevent = outevent.yield;
 });
 });
 });
 }
}
Pfin : FilterPattern {
 var <>count;
 *new { arg count, pattern;
 ^super.new(pattern).count_(count)
 }
 asStream { 
 ^Routine.new({ arg inevent;
 var item, stream;
 
 stream = pattern.asStream;
 
 count.value.do({
 inevent = stream.next(inevent).yield;
 });
 });
 }
} 
Plag : FilterPattern {
 var <>lag;
 *new { arg lag, pattern;
 ^super.new(pattern).lag_(lag)
 }
 asStream {
 var stream, item;
 
 stream = pattern.asStream;
 
 ^Routine.new({ arg inevent; 
 var newevent; 
 newevent = inevent.copy;
 newevent.put('freq', \rest);
 newevent.put('dur', lag);
 inevent = newevent.yield;
 loop ({
 inevent = stream.next(inevent).yield;
 });
 });
 }
}

Pbindf : FilterPattern {
 var <>patternpairs;
 *new { arg ... pairs;
 if (pairs.size.even, { 
 "Pbindf should have odd number of args.\n".error; 
 pairs.dump;
 this.halt 
 });
 ^super.new(pairs.last).patternpairs_(pairs)
 }
 asStream {
 var patstream, streampairs, endval;
 
 patstream = pattern.asStream;
 streampairs = patternpairs.copy;
 endval = streampairs.size - 2;
 forBy (1, endval, 2, { arg i;
 streampairs.put(i, streampairs.at(i).asStream);
 });
 
 ^FuncStream.new({ arg inevent;
 var sawNil = false;
 inevent = inevent.copy;
 forBy (0, endval, 2, { arg i;
 var name, stream, streamout;
 name = streampairs.at(i);
 stream = streampairs.at(i+1);
 
 streamout = stream.next(inevent);
 
 if (streamout.isNil, {
 sawNil = true;
 },{
 if (name.isKindOf(SequenceableCollection), { 
 streamout.do({ arg val, i;
 inevent.put(name.at(i), val);
 });
 },{
 inevent.put(name, streamout);
 });
 });
 });
 if (sawNil, { nil },{ 
 inevent = patstream.next(inevent);
 });
 },{
 patstream.reset;
 
 streampairs = patternpairs.copy;
 endval = streampairs.size - 1;
 forBy (1, endval, 2, { arg i;
 streampairs.put(i, streampairs.at(i).reset);
 });
 });
 }
}
Pstutter : FilterPattern {
 var <>n;
 *new { arg n, pattern;
 ^super.new(pattern).n_(n)
 }
 asStream { 
 ^Routine.new({ arg inevent;
 var outevent, stream;
 
 stream = pattern.asStream;
 
 while ({
 (outevent = stream.next(inevent)).notNil
 },{
 n.do({
 inevent = outevent.copy.yield;
 });
 });
 });
 }
} 
Pfx : FilterPattern {
 var <>effects, <>numChannels, <>postroll, <>decay;
 
 *new { arg pattern, effects, numChannels=2, postroll = 0.5, decay = 0.5;
 ^super.new(pattern).effects_(effects).numChannels_(numChannels)
 .postroll_(postroll).decay_(decay)
 }
 asStream {
 var stream;
 
 stream = pattern.asStream;
 
 ^Routine.new({ arg inevent;
 var out, tspawn, synth, outevent, copyevent;
 
  inevent.use({
 synth = Synth.new({
 out = TSpawn.ar(nil, numChannels);
 tspawn = out.source;
 effects.valueEnvir( out )
 * EnvGen.kr( 
 Env.new( #[1,1,1,0], [0.5, postroll, decay], \linear, 1) 
 );
 });
 });
 if (synth.notNil, { 
 inevent.at(\inSpawn).triggerSynth( synth, true ); 
 while ({
 copyevent = inevent.copy;
 copyevent.put(\inSpawn, tspawn);
 (outevent = stream.next(copyevent)).notNil
 },{
 inevent = outevent.yield;
 });
 synth.release;
 });
 });
 }
}
 
 
Pwhile : FuncFilterPattern {
 asStream {
 ^Routine.new({ arg inval;
 while({ func.value },{
 inval = pattern.embedInStream(inval);
 });
 });
 }
}
Ppatmod : Pattern {
 var <>pattern, <>func, <>repeats=1;
 *new { arg pattern, func, repeats;
 ^super.new.pattern_(pattern).func_(func).repeats_(repeats)
 }
 asStream { 
 ^Routine.new({ arg inval;
 var localPat;
 localPat = pattern.value.copy;
 repeats.do({ arg i;
 inval = localPat.embedInStream(inval);
 localPat = func.value(localPat, i);
 });
 })
 }
}
Prewrite : FilterPattern {
 var <>dict, <>levels=1;
 *new { arg pattern, dict, levels;
 ^super.new(pattern).dict_(dict).levels_(levels)
 }
 rewriteList { arg list, inval, level;
 var newlist;
 if (level == 0, { 
  if (list.isKindOf(SequenceableCollection), {
  list.do({ arg item; 
 inval = item.embedInStream(inval); 
 });
 },{
 inval = list.embedInStream(inval);
 });
 },{
 if (list.isKindOf(SequenceableCollection), {
  list.do({ arg item; 
  newlist = dict.at(item); 
 
  if (newlist.notNil, {
  inval = this.rewriteList( newlist, inval, level - 1 );
 },{
  inval = item.embedInStream(inval);
 });
 });
 },{
  newlist = dict.at(list); 
 
  if (newlist.notNil, {
  inval = this.rewriteList( newlist, inval, level - 1 );
 },{
  inval = list.embedInStream(inval);
 });
 });
 });
 ^inval;
 }
 asStream { 
 var stream;
 
 stream = pattern.asStream;
 
 ^Routine.new({ arg inval;
 var outval;
 while({
 outval = stream.next(inval);
 outval.notNil;
 },{
 inval = this.rewriteList(outval, inval, levels);
 });
 }, 2048)
 }
}


OSCNode {
 classvar <>sc; 
   classvar <>password; 
 var <name, <>children, <>function, <>value, <port;
 
 *new { arg name, function, children, value;
 ^super.new.name_(name).function_(function ? { arg node, x; x })
 .children_(children).value_(value);
 }
 *tree { arg children, argName, argValue;
 children = children.collect({ arg subtree; 
 var name, thing, value;
 #name, thing, value = subtree;
 if (thing.isKindOf(Array), {
 this.tree(thing, name, value); 
 },{
 OSCNode(name, thing, nil, value);
 });
 });
 ^OSCNode(argName, nil, children, argValue);
 }
 add { arg child;
 if (children.isNil, { children = Array.new(4) });
 children = children.add(child);
 }
 remove { arg child;
 if (children.notNil, { children.take(child); });
 }
 empty {
 children = nil;
 }
 name_ { arg theName; name = theName.asSymbol; }
 
 *default {
    sc = this.tree([
 [\interpreter, [
    [\run, { arg node, msgPassword, string; 
 if (password.isKindOf(Symbol) and: { password === msgPassword }, 
 {
 string.interpret;  })
 }],
 [\set, { arg node, varName, value;  thisProcess.interpreter.perform(varName.asSetter, value);
 }],
 [\clearAll, { thisProcess.interpreter.clearAll; }],
 [\executeFile, { arg node, pathname; thisProcess.interpreter.executeFile(pathname); }]
 ]],
 
 [\library, [
 [\choose, { arg node, names; Library.prLibMenuChoose(names); }],
 [\perform, { arg node, names, selector ... args; 
 var obj;
 obj = Library.at(names); 
 if (obj.notNil, {
 obj.performList(selector, args);
 });
 }]
 ]],
 
 [\obj, [
 [\perform, { arg node, id, selector ... args; 
 var obj;
 obj = ObjectTable.at(id); 
 if (obj.notNil, {
 obj.performList(selector, args);
 });
 }],
 [\remove, { arg node, id; ObjectTable.remove(id); }]
 ]],
 [\sound, [
 [\play, { arg node, pathName, duration, loop=false;
 SoundFile.play(pathName, duration: duration, loop: loop);
 }]
 ]],
 [\mixer, [
 [\stop, { arg node, channel;
 MixerPlayer.forInput(channel, { arg input; 
 input.killBox.value = 0;
 input.killBox.doAction; 
 });
 }],
 [\copy, { arg node, channel;
 MixerPlayer.forInput(channel, { arg input; 
 input.copyBtn.doAction; 
 });
 }],
 [\volume, { arg node, channel, volume = 0.0;
 MixerPlayer.forInput(channel, { arg input; 
 input.volumeSlider.value = volume; 
 });
 }],
 [\close, { arg node;
 MixerPlayer.mixer.notNil.if({
 MixerPlayer.mixer.close; 
 });
 }]
 ]],
 [\run, { thisProcess.run }], 
 [\stop, { Synth.stop }], 
 [\ping, { arg node; node.port.reply("/sc/postln", "I'm listening") }], 
 
 [\post, { arg node ... args; args.do({ arg item; item.post; " ".post; }); "\n".post; }],
 [\postln, { arg node ... args; args.do({ arg item; item.postln; });}]
 ]);
 }
 
  call { arg rcvPort ... args;
 port = rcvPort;
 value = function.valueArray(this, args);
 port = nil;
 }
}


OSCPort {
 classvar <openPorts;  var <portID, udpPort, <>oscNode;
 
 *closeAll {
 if (openPorts.notNil, {
 openPorts.copy.do({ arg port; port.close; });
 });
 }
 close {
 openPorts.remove(this);
 this.prClose;
 }
  prClose {
 _OSCPort_Close
 ^this.primitiveFailed;
 }
 addOpenPort {
 if (openPorts.isNil, { openPorts = Array.new(4); });
 openPorts = openPorts.add(this);
 }
}
OSCInPort : OSCPort {
  
 *new { arg portID, oscNode;
 ^super.new.init(portID, oscNode)
 }
 reply { arg oscAddress ... args;
 _OSCInPort_Reply
 ^this.primitiveFailed;
 }
 
  init { arg argPortID, argOSCNode;
 portID = argPortID;
 oscNode = argOSCNode;
 this.addOpenPort;
 this.prInit
 }
 prInit {
 _OSCInPort_Init
 ^this.primitiveFailed;
 }
}
OSCOutPort : OSCPort {
  var <hostname;
 
 *new { arg portID, hostname, oscNode;
 ^super.new.init(portID, hostname, oscNode)
 }
 
 send { arg oscAddress ... args;
 _OSCOutPort_Send
 ^this.primitiveFailed;
 }
 sendNoTags { arg oscAddress ... args;
 _OSCOutPort_SendNoTags
 ^this.primitiveFailed;
 }
 sendRaw { arg rawArray;
 _OSCOutPort_SendRaw
 ^this.primitiveFailed;
 }
 
 sendArray { arg array;
 this.performList(\send, array);
 }
 
  init { arg argPortID, argHostname, argOSCNode;
 portID = argPortID;
 hostname = argHostname;
 oscNode = argOSCNode;
 this.addOpenPort;
 this.prInit
 }
 prInit {
 _OSCOutPort_Init
 ^this.primitiveFailed;
 }
}

DialogWindow {
 classvar <>allDialogs; 
 addDialog {
 if (allDialogs.isNil, {
 allDialogs = Array.new(8).add(this);
 },{
 allDialogs = allDialogs.add(this);
 });
 }
 *closeAll {
 var list;
 list = allDialogs;
 allDialogs = Array.new(8);
 list.do({ arg window; window.close; });
 }
 close {
 allDialogs.remove(this);
 this.prClose;
 }
 update { arg theChanged, theChanger;
 _Dialog_SubjectChanged
 ^this.primitiveFailed
 }
 
 
  prInit {
 _Dialog_Init
 ^this.primitiveFailed
 }
 prClose {
 _Dialog_Close
 ^this.primitiveFailed
 }
}

ViewDialog : DialogWindow {
 var <view;
 
 *new { arg view;
 ^super.new.init(view)
 }
 init { arg argView;
 view = argView;
 view.addDependant(this);
 this.addDialog;
 this.prInit;
 }
 close {
 view.removeDependant(this);
 ^super.close
 }
}
ViewEditDialog : ViewDialog {
}
WaveBoundsDialog : ViewDialog {
}
SignalBoundsDialog : ViewDialog {
}

WavetableDialog : DialogWindow {
 var <wavetable;
 *new { arg wavetable;
 ^super.new.init(wavetable)
 }
 init { arg argWavetable;
 wavetable = argWavetable;
 wavetable.addDependant(this);
 this.addDialog;
 this.prInit
 }
 close {
 wavetable.removeDependant(this);
 ^super.close
 }
}
 
HarmonicsDialog : WavetableDialog {
}
PMFillDialog : WavetableDialog {
}
GBuzzFillDialog : WavetableDialog {
}
GrainEnvFillDialog : WavetableDialog {
}
CombTableDialog : WavetableDialog {
}
ScaleTableDialog : WavetableDialog {
}

ModalDialogWindow : DialogWindow {
 var <>action, <ok;
}

GetColorDialog : ModalDialogWindow {
 var <color, prompt;
 *new { arg prompt, color, action;
 ^super.new.init(prompt, color, action)
 }
 init { arg argPrompt, argColor, argAction;
 action = argAction;
 color = argColor;
 prompt = argPrompt;
 this.addDialog;
 this.prInit(argPrompt);
 }
 doAction {
 action.value(ok, color);
 this.close;
 }
}

GetFileDialog : ModalDialogWindow {
 var <path;
 *new { arg action;
 ^super.new.init(action);
 }
 init { arg argAction;
 action = argAction;
 path = String.new(256);
 this.addDialog;
 this.prInit;
 }
 doAction {
 action.value(ok, path);
 this.close;
 }
}
PutFileDialog : ModalDialogWindow {
 var <path, prompt, defaultFileName;
 
 *new { arg prompt, defaultFileName, action;
 ^super.new.init(prompt, defaultFileName, action);
 }
 init { arg argPrompt, argDefaultFileName, argAction;
 prompt = argPrompt;
 defaultFileName = argDefaultFileName;
 action = argAction;
 path = String.new(256);
 this.addDialog;
 this.prInit(argPrompt, argDefaultFileName);
 }
 doAction {
 action.value(ok, path);
 this.close;
 }
}

GetStringDialog : ModalDialogWindow {
 var <string, prompt, defaultString;
 *new { arg prompt, defaultString, action;
 ^super.new.init(prompt, defaultString, action)
 }
 init { arg argPrompt, argDefaultString, argAction;
 action = argAction;
 prompt = argPrompt;
 defaultString = argDefaultString;
 string = String.new(256);
 this.addDialog;
 this.prInit(argPrompt, argDefaultString);
 }
 doAction {
 action.value(ok, string);
 this.close;
 }
}
MIDIOut {
 var <>port;
 
 *new { arg port;
 ^super.new.port_(port)
 }
 
 write { arg port, len, hiStatus, chan, a=0, b=0;
 _MIDIOut_Write
 ^this.primitiveFailed
 }
 
 noteOn { arg chan, note=60, veloc=64;
 this.write(port, 3, 16r90, chan, note, veloc);
 }
 noteOff { arg chan, note=60, veloc=64;
 this.write(port, 3, 16r80, chan, note, veloc);
 }
 polyTouch { arg chan, note=60, val=64;
 this.write(port, 3, 16rA0, chan, note, val);
 }
 control { arg chan, ctlNum=7, val=64;
 this.write(port, 3, 16rB0, chan, ctlNum, val);
 }
 program { arg chan, num=1;
 this.write(port, 2, 16rC0, chan, num);
 }
 touch { arg chan, val=64;
 this.write(port, 2, 16rD0, chan, val);
 }
 bend { arg chan, val=64;
 this.write(port, 3, 16rE0, chan, val, 0);
 }
 allNotesOff { arg chan;
 this.control(chan, 123, 0);
 }
}
 Rect {
 var <>left=0, <>top=0, <>right=0, <>bottom=0;
 
 *new { arg left=0, top=0, right=0, bottom=0;
 ^super.new.set(left, top, right, bottom)
 }
 *newBy { arg left=0, top=0, width=0, height=0;
 ^super.new.setBy(left, top, width, height)
 }
 *fromPoints { arg pt1, pt2;
 ^super.new.left_(pt1.x min: pt2.x).top_(pt1.y min: pt2.y)
 .right_(pt1.x max: pt2.x).bottom_(pt1.y max: pt2.y)
 }
 
 set { arg argLeft=0, argTop=0, argRight=0, argBottom=0;
 left = argLeft;
 top = argTop;
 right = argRight;
 bottom = argBottom;
 }
 setBy { arg argLeft=0, argTop=0, argWidth=0, argHeight=0;
 left = argLeft;
 top = argTop;
 right = argLeft + argWidth;
 bottom = argTop + argHeight;
 }
 setExtent { arg width=0, height=0;
 right = left + width;
 bottom = top + height;
 }
 
 width { ^right - left }
 height { ^bottom - top }
 width_ { arg h; right = left + h; }
 height_ { arg v; bottom = top + v; }
 
 origin { ^Point.new(left, top) }
 corner { ^Point.new(right, bottom) }
 extent { ^Point.new(right - left, bottom - top) }
 
 leftTop { ^Point.new(left, top) }
 rightTop { ^Point.new(right, top) }
 leftBottom { ^Point.new(left, bottom) }
 rightBottom { ^Point.new(right, bottom) }
 
 moveBy { arg h, v;
 ^Rect.new(left + h, top + v, right + h, bottom + v)
 }
 moveTo { arg h, v;
 ^Rect.new(h, v, right - left + h, bottom - top + v)
 }
 moveToPoint { arg aPoint;
 ^Rect.new(aPoint.x, aPoint.y, right - left + aPoint.x, bottom - top + aPoint.y)
 }
 resizeBy { arg h, v;
 ^Rect.new(left, top, right + h, bottom + v)
 }
 resizeTo { arg h, v;
 ^Rect.new(left, top, left + h, top + v)
 }
 insetBy { arg h, v;
 ^Rect.new(left + h, top + v, right - h, bottom - v)
 }
 insetAll { arg a, b, c, d;
 ^Rect.new(left + a, top + b, right - c, bottom - d)
 }
 
 contains { arg aPoint;
 ^ aPoint.x.inclusivelyBetween(left, right) 
 and: { aPoint.y.inclusivelyBetween(top, bottom) }
 }
 intersects { arg aRect;
 if (aRect.right < left, { ^false });
 if (aRect.bottom < top, { ^false });
 if (aRect.left > right, { ^false });
 if (aRect.top > bottom, { ^false });
 ^true
 }
 
 & { arg aRect; ^this sect: aRect }
 | { arg aRect; ^this union: aRect }
 
 union { arg aRect;
 ^Rect.new( left min: aRect.left, top min: aRect.top,
 right max: aRect.right, bottom max: aRect.bottom)
 }
 sect { arg aRect;
 ^Rect.new( left max: aRect.left, top max: aRect.top,
 right min: aRect.right, bottom min: aRect.bottom)
 }
 printOn { arg stream;
 stream << this.class.name << ".newBy(" 
 <<* [left, top, this.width, this.height] << ")";
 }
 storeOn { arg stream;
 stream << this.class.name << ".newBy(" 
 <<<* [left, top, this.width, this.height] << ")";
 }
}


Ref {
 var <>value;
 *new { arg thing; ^super.new.value_(thing) }
 set { arg thing; value = thing }
 get { ^value }
 dereference { ^value }
 asRef { ^this }
 printOn { arg stream;
 stream << "`(" << value << ")";
 }
 storeOn { arg stream;
 stream << "`(" <<< value << ")";
 }
}

Peep {
 *ar { arg in, label="peep", period=1;
 Sequencer.kr({ var val;
 val = in.poll;
 (label ++ ": " ++ val.asString).postln;
 val
 }, Impulse.kr(period));
 ^in
 }
 *kr { arg in, label="peep", period=1;
 Sequencer.kr({ var val;
 val = in.poll;
 (label ++ ": " ++ val.asString).postln;
 val
 }, Impulse.kr(period));
 ^in
 }
}
 Model {
 var <>dependants;
 
 changed {
 if (dependants.notNil, {
 dependants.do({ arg item;
 item.update(this);
 });
 });
 }
 addDependant { arg dependant;
 this.checkDependants;
 dependants.add(dependant);
 }
 removeDependant { arg dependant;
 if (dependants.notNil, {
 dependants.remove(dependant);
 })
 }
 release {
 dependants = nil;
 }
 checkDependants {
 if (dependants.isNil, {
 dependants = IdentitySet.new(4);
 })
 }
}
TestDependant {
 update { arg thing;
 (thing.asString ++ " was changed.\n").post;
 }
}
View {
 var <window, <>model, <>keyDownAction;
 
 *new { arg window, bounds;
 ^super.new.initView(window, bounds)
 }
 *dropNew { arg window, x, y;  ^this.new(window, Rect.newBy(x, y, 128, 20)).setSelected(true)
 }
 *canDrop { ^false }
 
 edit {
 ^ViewEditDialog.new(this);
 }
 
 bounds_ { arg bounds;
 this.prSetBounds(bounds);
 }
 bounds {
 ^this.prGetBounds(Rect.new);
 }
 
 backColor_ { arg color;
 this.prSetColor(color, 0);
 }
 backColor { 
 ^this.prGetColor(0);
 }
 labelColor_ { arg color;
 this.prSetColor(color, 1);
 }
 labelColor { 
 ^this.prGetColor(1);
 }
 
 followLeft { 
 this.prSetHResize(1);
 }
 followRight {
 this.prSetHResize(2);
 }
 hElastic {
 this.prSetHResize(3);
 }
 
 followTop { 
 this.prSetVResize(1);
 }
 followBottom {
 this.prSetVResize(2);
 }
 vElastic {
 this.prSetVResize(3);
 }
 
 canFocus { ^true }
 focus { arg flag = true;
 if (flag && this.canFocus, { window.focus_(this) },{ window.focus_(nil) });
 }
 isFocus { 
 _View_IsFocus
 ^this.primitiveFailed
 }
 refresh {  _View_Refresh
 ^this.primitiveFailed
 }
 keyDown { arg asciiChar, keyCode, modifiers;
  keyDownAction.value(asciiChar, keyCode, modifiers)
 }
 
  isSelected {
 _View_IsSelected
 ^this.primitiveFailed
 }
 setSelected { arg argSelect=true;
 this.prSetSelected(argSelect);
 this.refresh;
 }
 
 update {  this.refresh
 }
  storeModifiersOn { arg stream;
 var string;
 string = String.streamContents({ arg stream2; 
 this.storeResizeOn(stream2); 
 this.storeBordersOn(stream2); 
 this.storeColorsOn(stream2); 
 });
 if (string.size > 0, { stream.nl.tab.tab });
 stream << string;
 }
 storeParamsOn { arg stream;
 stream << "( w, " <<< this.bounds << ")" ;
 } 
 storeResizeOn { arg stream;
 stream 
 << #["",".followRight",".hElastic"].at(this.prGetHResize - 1)
 << #["",".followBottom",".vElastic"].at(this.prGetVResize - 1);
 }
 storeBordersOn { arg stream;
 var borderStyle;
 borderStyle = this.prGetBorderStyle;
 if (borderStyle != 1, {
 stream << ".prSetBorderStyle(" <<< borderStyle << ")" ;
 });
 }
 storeColorsOn { arg stream;
 if (this.backColor != window.backColor, {
 stream << ".backColor_(" <<< this.backColor << ")" ;
 });
 if (this.labelColor != Color.black, {
 stream << ".labelColor_(" <<< this.labelColor << ")" ;
 });
 }
 storeRadioGroupOn { arg stream;
 }
 
  initView { arg argWindow, argBounds;
 if (argWindow.add(this), {
 window = argWindow;
 this.prViewInit(argWindow, argBounds);
 });
 }
 
 duplicateOffsetBy { arg x, y;
 var newView;
 newView = this.class.new(window, this.bounds.moveBy(x, y));
 newView.copyGUIParamsFrom(this);
 newView.setSelected(true);
 ^newView
 }
 copyGUIParamsFrom { arg aView;
 this.prSetHResize(aView.prGetHResize);
 this.prSetVResize(aView.prGetVResize);
 this.prSetBorderStyle(aView.prGetBorderStyle);
 this.copyColorsFrom(aView);
 }
 copyColorsFrom { arg aView;
 this.prSetColor(this.prGetColor(0), 0);
 this.prSetColor(this.prGetColor(1), 1);
 this.prSetColor(this.prGetColor(2), 2);
 }
 setViewAttr { arg index, value;
 _View_SetAttr
 ^this.primitiveFailed
 }
 getViewFloatAttr { arg index;
 _View_GetFloatAttr
 ^this.primitiveFailed
 }
 getViewIntAttr { arg index;
 _View_GetIntAttr
 ^this.primitiveFailed
 }
 getViewObjAttr { arg index;
 _View_GetObjAttr
 ^this.primitiveFailed
 }
 getViewSymbolAttr { arg index;
 _View_GetSymbolAttr
 ^this.primitiveFailed
 }
 prViewInit { arg window, bounds;
 _View_Init
 ^this.primitiveFailed
 }
 prSetBounds { arg argBounds;
 _View_SetBounds
 ^this.primitiveFailed
 }
 prGetBounds { arg argBounds;
 _View_GetBounds
 ^this.primitiveFailed
 }
 
 prSetHResize { arg flag;
 _View_SetHResize
 ^this.primitiveFailed
 }
 prSetVResize { arg flag;
 _View_SetVResize
 ^this.primitiveFailed
 }
 prGetHResize {
 _View_GetHResize
 ^this.primitiveFailed
 }
 prGetVResize {
 _View_GetVResize
 ^this.primitiveFailed
 } 
 prSetSelected { arg argSelect=true;
 _View_SetSelected
 ^this.primitiveFailed
 }
 prGetBorderStyle {
 _View_GetBorderStyle
 ^this.primitiveFailed
 }
 prSetBorderStyle { arg argStyle = 0;
 _View_SetBorderStyle
 ^this.primitiveFailed
 }
 prSetColor { arg color, index;
 _View_SetColor
 ^this.primitiveFailed
 } 
 prGetColor { arg index;
 _View_GetColor
 ^this.primitiveFailed
 } 
}
ControlView : View {
 var <label, <action;
 *new { arg window, bounds, label, val = 0.0, minval = 0.0, maxval = 1.0, step = 0.0,
 warp = 'linear';
 ^super.new(window, bounds).setParams(val, minval, maxval, step, warp).label_(label)
 }
 *dropNew { arg window, x, y;  ^this.new(window, Rect.newBy(x, y, 128, 20), this.name.asString).setSelected(true)
 }
 *canDrop { ^false }
 
 duplicateOffsetBy { arg x, y;
 ^this.class.new(window, this.bounds.moveBy(x, y), label, 
 this.value, this.minval, this.maxval, this.step, this.warp
 ).action_(action).setSelected(true).copyGUIParamsFrom(this)
 }
 
 setParams { arg argValue = 0.0, argMinVal = 0.0, argMaxVal = 1.0, argStep = 0.0, argWarp = 'linear';
 this.minval = argMinVal;
 this.maxval = argMaxVal;
 this.step = argStep;
 this.value = argValue;
 this.warp = argWarp;
 }
 
  value {
 ^this.getViewFloatAttr(1);
 }
 minval {
 ^this.getViewFloatAttr(2);
 }
 maxval {
 ^this.getViewFloatAttr(3);
 }
 step {
 ^this.getViewFloatAttr(4);
 }
 warp {
 ^this.getViewSymbolAttr(5);
 }
 
 
  label_ { arg aString;
 label = aString.asString;
 this.setViewAttr(0, label);
 }
 value_ { arg argValue; 
 argValue = argValue.clip(this.minval, this.maxval).round(this.step);
 this.setViewAttr(1, argValue.asFloat);
 }
 minval_ { arg minval;
 this.setViewAttr(2, minval.asFloat);
 }
 maxval_ { arg maxval;
 this.setViewAttr(3, maxval.asFloat);
 }
 step_ { arg step;
 this.setViewAttr(4, step.asFloat);
 }
 warp_ { arg warp;
 if (warp.class == Symbol, {
 this.setViewAttr(5, warp);
 },{ 
 (warp.asString ++ " is not a symbol").error; 
 });
 }
 action_ { arg function;
 action = function;
 this.setViewAttr(6, function);
 }
 
 doAction { action.value(this) }
 
 toggle { 
 ^this.value_(1.0 - this.value) 
 }
 
  kr { arg lagTime = 0.1, index = 0;
 ^Plug.kr(this, lagTime, index)
 }
 
  mapToPolyTouch { arg channel = 1, key = 60;
 this.prSetMIDIMapping(16rA0, channel, key);
 }
 mapToController { arg channel = 1, ctlnum = 7;
 this.prSetMIDIMapping(16rB0, channel, ctlnum);
 }
 mapToTouch { arg channel = 1;
 this.prSetMIDIMapping(16rD0, channel, 0);
 }
 mapToPitchBend { arg channel = 1;
 this.prSetMIDIMapping(16rE0, channel, 0);
 }
 
 getMIDIMappingStatus {
 _View_GetMIDIMappingStatus
 ^this.primitiveFailed
 }
 getMIDIMappingChannel {
 _View_GetMIDIMappingChannel
 ^this.primitiveFailed
 }
 getMIDIMappingIndex {
 _View_GetMIDIMappingIndex
 ^this.primitiveFailed
 }
 storeParamsOn { arg stream;
 stream << "( w, " <<<* 
 [ 
 this.bounds, this.label, this.value, this.minval, 
 this.maxval, this.step, this.warp 
 ] << ")" ;
 } 
 storeModifiersOn { arg stream;
 var string;
 string = String.streamContents({ arg stream2; 
 this.storeResizeOn(stream2); 
 this.storeBordersOn(stream2); 
 this.storeColorsOn(stream2); 
 this.storeRadioGroupOn(stream2); 
 this.storeMidiMappingOn(stream2); 
 });
 if (string.size > 0, { stream.nl.tab.tab });
 stream << string ;
 }
 storeMidiMappingOn { arg stream;
 var status;
 
 status = this.getMIDIMappingStatus;
 if (status != 0, { 
 stream << ".prSetMIDIMapping(" 
 <<<* [status, this.getMIDIMappingChannel, this.getMIDIMappingIndex]
 << ")" ;
 });
 }
 
  prSetMIDIMapping { arg status = 16rB0, channel = 1, index = 7;
 _View_SetMIDIMapping
 ^this.primitiveFailed
 }
}
RangeView : ControlView {
 *new { arg window, bounds, label, val = 0.0, val2 = 0.0, minval = 0.0, maxval = 1.0, step = 0.0,
 warp = 'linear';
 ^super.new(window, bounds).setParams(val, val2, minval, maxval, step, warp).label_(label)
 }
 *canDrop { ^true }
 duplicateOffsetBy { arg argX, argY;
 ^this.class.new(window, this.bounds.moveBy(argX, argY), label, 
 this.value, this.range, this.minval, this.maxval, this.step, this.warp
 ).action_(action).setSelected(true).copyGUIParamsFrom(this)
 }
 
 setParams { 
 arg argValue = 0.0, argValue2 = 0.0, argMinVal = 0.0, argMaxVal = 1.0, argStep = 0.0, 
 argWarp = 'linear';
 this.minval = argMinVal;
 this.maxval = argMaxVal;
 this.step = argStep;
 this.value = argValue;
 this.range = argValue2;
 this.warp = argWarp;
 }
 
 range_ { arg argRange;
 argRange = ((argRange + this.value).clip(this.minval, this.maxval) - this.value).round(this.step);
 this.setViewAttr(7, argRange.asFloat);
 }
 range {
 ^this.getViewFloatAttr(7);
 }
 highValue {
 ^this.value + this.range
 }
 centerValue {
 ^this.value + (this.range * 0.5)
 }
 
 rangeColor_ { arg color;
 this.prSetColor(color, 2);
 }
 rangeColor { 
 ^this.prGetColor(2);
 }
 storeColorsOn { arg stream;
 super.storeColorsOn(stream);
 if (this.rangeColor != Color.blue, {
 stream << ".rangeColor_(" <<< this.rangeColor << ")" ;
 });
 }
 storeParamsOn { arg stream;
 stream << "( w, " <<<* 
 [ 
 this.bounds, this.label, this.value, this.range, 
 this.minval, this.maxval, this.step, this.warp 
 ] << ")" ;
 } 
 storeBordersOn { }
}
StringView : View {
 var <label;
 *new { arg window, bounds, label;
 ^super.new(window, bounds).label_(label)
 }
 *dropNew { arg argWindow, argX, argY;  ^this.new(argWindow, Rect.newBy(argX, argY, 128, 20), this.name.asString).setSelected(true)
 }
 *canDrop { ^true }
 
 duplicateOffsetBy { arg argX, argY;
 ^this.class.new(window, this.bounds.moveBy(argX, argY), label)
 .setSelected(true).copyGUIParamsFrom(this)
 }
 label_ { arg aString;
 label = aString.asString;
 this.setViewAttr(0, label);
 }
 canFocus { ^false }
 storeParamsOn { arg stream;
 stream << "( w, " <<<* [ this.bounds, this.label ] << ")" ;
 } 
}
SliderView : ControlView {
 *canDrop { ^true }
 storeBordersOn { }
 knobColor_ { arg color;
 this.prSetColor(color, 2);
 }
 knobColor { 
 ^this.prGetColor(2);
 }
 storeColorsOn { arg stream;
 super.storeColorsOn(stream);
 if (this.knobColor != window.backColor, {
 stream << ".knobColor_(" <<< this.knobColor << ")" ;
 });
 }
}
MeterView : ControlView {
 *canDrop { ^false }  canFocus { ^false }
 storeBordersOn { }
}
NumericalView : ControlView {
 *dropNew { arg argWindow, argX, argY;  ^this.new(argWindow, Rect.newBy(argX, argY, 64, 20), this.name.asString,
 0, -1e10, 1e10).setSelected(true)
 }
 *canDrop { ^true }
 storeBordersOn { }
}
ButtonView : ControlView {
 *canDrop { ^true }
 storeBordersOn { }
}
RadioButtonView : ControlView {
 *canDrop { ^true }
 group { _View_GetRadioGroup ^this.primitiveFailed }
 group_ { arg groupnum; _View_SetRadioGroup ^this.primitiveFailed }
 copyGUIParamsFrom { arg aView;
 super.copyGUIParamsFrom(aView);
 this.group = aView.group;
 }
 storeRadioGroupOn { arg stream;
 if (this.group != 0, {
 stream << ".group_(" << this.group << ")" ;
 });
 }
}
CheckBoxView : ControlView {
 *canDrop { ^true }
}
WavetableView : View {
 var <wavetable;
 *new { arg window, bounds, wavetable, minval = -1.0, maxval = 1.0;
 ^super.new(window, bounds).setAll(wavetable, minval, maxval)
 }
 *dropNew { arg argWindow, argX, argY;  ^this.new(argWindow, Rect.newBy(argX, argY, 128, 100)).setSelected(true)
 }
 *canDrop { ^true }
 
 duplicateOffsetBy { arg argX, argY;
 ^this.class.new(window, this.bounds.moveBy(argX, argY), wavetable, 
 this.minval, this.maxval).setSelected(true).copyGUIParamsFrom(this)
 }
 setAll { arg argWavetable, argMinVal = -1.0, argMaxVal = 1.0, argScroll = 0, argZoom = 1;
 this.minval = argMinVal;
 this.maxval = argMaxVal;
 this.wavetable = argWavetable;
 }
 release {
 wavetable.removeDependant(this);
 super.release;
 }
 minval {
 ^this.getViewFloatAttr(1);
 }
 maxval {
 ^this.getViewFloatAttr(2);
 }
 wavetable_ { arg aWavetable;
 if (aWavetable.isKindOf(Wavetable), {
 wavetable.removeDependant(this);
 wavetable = aWavetable;
 wavetable.addDependant(this);
 this.setViewAttr(0, aWavetable);
 });
 }
 minval_ { arg argMinVal;
 this.setViewAttr(1, argMinVal.asFloat);
 }
 maxval_ { arg argMaxVal;
 this.setViewAttr(2, argMaxVal.asFloat);
 }
 keyDown { arg asciiChar, keyCode, modifiers; 
 if ( wavetable.notNil, {
 if (asciiChar == $k, {
 this.wavetable = max(wavetable, 0);
 this.refresh;
 ^this
 });
 if (asciiChar == $u, {
 this.wavetable = 0.5 * wavetable + 0.5;
 this.refresh;
 ^this
 });
 if (asciiChar == $d, {
 this.wavetable = 2.0 * (wavetable - 0.5);
 this.refresh;
 ^this
 });
 if (asciiChar == $ , {
 this.wavetable.play(window.name);
 ^this
 });
 });
 ^super.keyDown(asciiChar, keyCode, modifiers);
 }
 storeParamsOn { arg stream;
 stream << "( w, " <<<* 
 [ 
 this.bounds, nil, this.minval, this.maxval 
 ] << ")" ;
 } 
 storeBordersOn { }
}
SignalView : View {
 var <signal, <scopeSignal, <>sampleRate;
 *new { arg window, bounds, signal, minval = -1.0, maxval = 1.0, 
 scroll = 0, zoom = 1;
 ^super.new(window, bounds).setAll(signal, minval, maxval, scroll, zoom)
 .sampleRate_(Synth.sampleRate)
 }
 *dropNew { arg argWindow, argX, argY;  ^this.new(argWindow, Rect.newBy(argX, argY, 128, 100)).setSelected(true)
 }
 *canDrop { ^true }
 
 duplicateOffsetBy { arg argX, argY;
 ^this.class.new(window, this.bounds.moveBy(argX, argY), signal, 
 this.minval, this.maxval, this.zoom).setSelected(true).copyGUIParamsFrom(this)
 }
 setAll { arg argSignal, argMinVal = -1.0, argMaxVal = 1.0,
 argScroll = 0, argZoom = 1;
 this.minval = argMinVal;
 this.maxval = argMaxVal;
 this.scroll = argScroll;
 this.zoom = argZoom;
 this.signal = argSignal;
 }
 release {
 signal.removeDependant(this);
 scopeSignal.removeDependant(this);
 super.release;
 }
 minval {
 ^this.getViewFloatAttr(1);
 }
 maxval {
 ^this.getViewFloatAttr(2);
 }
 scroll {
 ^this.getViewIntAttr(3);
 }
 zoom {
 ^this.getViewIntAttr(4);
 }
 selectionStart {
 ^this.getViewIntAttr(5);
 }
 selectionEnd {
 ^this.getViewIntAttr(6);
 }
 signal_ { arg aSignal;
 if (aSignal.isKindOf(Signal), {
 signal.removeDependant(this);
 signal = aSignal;
 signal.addDependant(this);
 this.setViewAttr(0, aSignal);
 });
 }
  scopeSignal_ { arg aSignal;
 if (aSignal.isKindOf(Signal), {
 scopeSignal.removeDependant(this);
 scopeSignal = aSignal;
 scopeSignal.addDependant(this);
 this.setViewAttr(7, aSignal);
 });
 }
 
 minval_ { arg argMinVal;
 this.setViewAttr(1, argMinVal.asFloat);
 }
 maxval_ { arg argMaxVal;
 this.setViewAttr(2, argMaxVal.asFloat);
 }
 scroll_ { arg argScrollPos;
 argScrollPos = argScrollPos.asInteger max: 0;
 this.setViewAttr(3, argScrollPos);
 }
 zoom_ { arg argZoom;
 argZoom = argZoom.asInteger max: 1;
 this.setViewAttr(4, argZoom);
 }
 
 setSelection { arg argSelectionStart, argSelectionEnd;
 _SignalView_SetSelection
 ^this.primitiveFailed
 }
 
 keyDown { arg asciiChar, keyCode, modifiers;
 if ( signal.notNil, {
 if (asciiChar == $ , {
 this.signal.play(sampleRate, window.name);
 ^this
 });
 if (asciiChar == $k, {
 this.signal = max(signal, 0);
 this.refresh;
 ^this
 });
 if (asciiChar == $u, {
 this.signal = 0.5 * signal + 0.5;
 this.refresh;
 ^this
 });
 if (asciiChar == $d, {
 this.signal = 2.0 * (signal - 0.5);
 this.refresh;
 ^this
 });
 if (asciiChar == $/, {
 this.signal = signal.distort.normalize;
 this.refresh;
 ^this
 });
 });
 ^super.keyDown(asciiChar, keyCode, modifiers);
 }
 storeParamsOn { arg stream;
 stream << "( w, " <<<* 
 [ 
 this.bounds, nil, this.minval, this.maxval 
 ] << ")" ;
 } 
 storeBordersOn { }
}
ScopeView : SignalView {
 *new { arg window, bounds, size, minval = -1.0, maxval = 1.0;
 var view;
 view = super.new(window, bounds, nil, minval, maxval, 
 0, ceil(size / bounds.width));
 view.signal = Signal.newClear(size);
 view.scopeSignal = Signal.newClear(size);
 ^view
 
 }
 *dropNew { arg argWindow, argX, argY;  ^this.new(argWindow, Rect.newBy(argX, argY, 128, 100), (Synth.sampleRate/10)).setSelected(true)
 }
 
 duplicateOffsetBy { arg argX, argY;
 ^this.class.new(window, this.bounds.moveBy(argX, argY), signal, 
 this.minval, this.maxval, this.zoom).setSelected(true).copyGUIParamsFrom(this)
 }
 storeParamsOn { arg stream;
 stream << "( w, " <<<* 
 [ 
 this.bounds, this.signal.size, this.minval, this.maxval 
 ] << ")" ;
 } 
}
BPEnvView : View {
 var <levels, <times;
 *canDrop { ^false }  levels_ { arg argLevels;
 levels = argLevels;
 this.setViewAttr(0, argLevels);
 }
 times_ { arg argTimes;
 times = argTimes;
 this.setViewAttr(1, argTimes);
 }
}


TableView : WavetableView { 
 classvar <tableClipboard, <clipboardWindow;
 var <viewSpecs = #[ -1, 1, -20000, 20000, 0.1, 0], <minSetBox, <maxSetBox, <labelColor; 
 var <undoTable, nextUndoTable, <>defaultTable;
 var <tableAsSig, undoSignal, nextUndoSignal;
 
 *new { arg window, bounds, table, viewSpecs, boxes = \top; 
 ^super.new(window, bounds, table)
 .init(viewSpecs, boxes);
 }
 
 init { arg newSpecs, boxes;
 viewSpecs = newSpecs ? viewSpecs; 
 
 if (wavetable.notNil, {
 nextUndoTable = wavetable.copy;
 tableAsSig = wavetable.asSignal; 
 nextUndoSignal = tableAsSig.copy;
 });
 
 if ( (boxes == false).not, { this.addMinMaxBoxes(boxes); });
 
 this.viewSpecs_(viewSpecs);
 }
 
 wavetable_ { arg newTable, prepUndo = false; 
   if (newTable.notNil, {
 super.wavetable_(newTable);
 if (prepUndo, 
 { this.update(newTable); }, 
 { this.wipeUndo }  ); 
 }); 
 }
 
 wipeUndo { 
 undoTable = nil; 
 nextUndoTable = nil; 
 tableAsSig = nil; 
 undoSignal = nil; 
 nextUndoSignal = nil; 
 }
 
 *tableClipboard_ { arg table; 
 tableClipboard = table;
 if (clipboardWindow.notNil, { this.plotClipboard; });
 ^this;
 }
 
 tableClipboard_ { arg table; 
 this.class.tableClipboard_(table);
 }
 
 *plotClipboard { arg name, bounds;
 if (tableClipboard.isNil, { MessageWindow("Table Clipboard is empty."); ^this; });
 
 if (clipboardWindow.isNil, 
 { clipboardWindow = TableWindow(tableClipboard, "Table Clipboard", bounds)
 .onClose_({ clipboardWindow = nil; }); 
 }, 
 { clipboardWindow.show; clipboardWindow.tableView.wavetable_(tableClipboard); }
 );
 
 clipboardWindow.tableView.autoScale; 
 ^clipboardWindow;
 }
 
 viewSpecs_ { arg newSpecs; 
 var min, max, setMin, setMax, step, resetVal;
 viewSpecs = newSpecs ? viewSpecs; 
 
 #min, max, setMin, setMax, step, resetVal = viewSpecs;
 this.minval_(min).maxval_(max); 
 if (minSetBox.notNil, { minSetBox.maxval_(setMax).minval_(setMin).value_(min); });
 if (maxSetBox.notNil, { maxSetBox.maxval_(setMax).minval_(setMin).value_(max); });
 }
 
 update { arg newTable; 
  undoTable = nextUndoTable; 
 nextUndoTable = newTable.copy;
 undoSignal = nextUndoSignal;
 tableAsSig = nextUndoSignal = newTable.asSignal;
 
 ^super.update;
 }
 
 undoLastEdit { 
 if (undoTable.notNil, 
 { nextUndoTable = wavetable.copy; 
 wavetable.overWrite(undoTable, 0); 
 tableAsSig = undoSignal;
 this.refresh; 
 });
 ^this;
 }
 
 *defaultViewSpecs { ^ [ -1, 1, -1, 1, 0.1, 0] } 
 
 addMinMaxBoxes { arg where = \top; 
 var min, max, setMin, setMax, setStep, defaultVal; 
 var boxWidth, top, left;
 
 if (where == \top, { });
 if (where == \left, { });
 
 #min, max, setMin, setMax, setStep, defaultVal = viewSpecs;
 
 boxWidth = if (max <=1 and: (min >= -1) , 32, 40 );
 top = this.bounds.top - 18;
 left = this.bounds.left - 4;
 StringView.new( this.window, Rect.newBy(left, top, 24, 12), "min");
 minSetBox = NumericalView.new( this.window, Rect.newBy(left + 26, top, boxWidth, 12), " ", 
 min, setMin, setMax, setStep, 'linear');
 minSetBox.action = { this.updateMinval_(minSetBox.value); };
 StringView.new( this.window, Rect.newBy(left + 28 + boxWidth, top, 24, 12), "max");
 maxSetBox = NumericalView.new( this.window, 
 Rect.newBy(left + 56 + boxWidth, top, boxWidth, 12), " ", 
 max, setMin, setMax, setStep);
 maxSetBox.action = { this.updateMaxval_(maxSetBox.value); };
 ^this;
 }
 
 updateMinval_ { arg min; 
 if (minSetBox.notNil, 
 { minSetBox.value = min(min, maxSetBox.value - viewSpecs.at(4)); }
 );
 ^this.minval_(minSetBox.value);
 }
 updateMaxval_ { arg max; 
 if (maxSetBox.notNil, 
 { maxSetBox.value = max(max, minSetBox.value + viewSpecs.at(4)); }
 );
 ^this.maxval_(maxSetBox.value); 
 }
 
 keyDown { arg asciiChar, keyCode, modifiers;    
 var newTable, targetSig, mixSig, scaler; 
 
 if ( wavetable.notNil, {
 if ([ $0, $1 ].includes(asciiChar),  { wavetable.overWrite( 
 Signal.newClear(wavetable.size div: 2)
 .fill(asciiChar.digit)
 .asWavetable, 
 0);
 this.refresh;
 ^this;
 }
 );
 
 if (asciiChar == $z, { ^this.undoLastEdit; });
 
 if (asciiChar == $k, {  wavetable.overWrite(max(tableAsSig, 0).asWavetable, 0);
 this.refresh;
 ^this
 });
 if (asciiChar == $i, {  wavetable.overWrite(tableAsSig.neg.asWavetable, 0);
 this.refresh;
 ^this
 });
 if (asciiChar == $r, {  wavetable.overWrite(tableAsSig.reverse.asWavetable, 0);
 this.refresh;
 ^this
 });
 
 if ("Ee".includes(asciiChar), { ^this.openEditor; });
 
 if ("fh".includes(asciiChar), { ^HarmonicsDialog(this.wavetable); }); 
 if ("Bb".includes(asciiChar), { ^TableWindow(this.wavetable, this.window.name, Rect.newBy(12, 64, 1000, 700)); }); 
 
 if (asciiChar == $S, {  if (Synth.isOn, { MessageWindow.new("Sorry, no saving while Synth is on."); ^this; });
 
 wavetable.putFile(defaultName: this.window.name ++ ".wt")
 ^this
 });
 if (asciiChar == $L, {  if (Synth.isOn, { MessageWindow.new("Sorry, no loading while Synth is on."); ^this; });
 Wavetable.getFile({ arg ok, array; 
 if (ok, 
 { if (array.size == wavetable.size,
 { wavetable.overWrite(array, 0) },
 { inform("File size is not " ++ wavetable.size.asString ++ ".") }
 );
 this.refresh;
 }
 ); 
 
 });
 ^this
 });
 if (asciiChar == $I, { this.importSoundFile; ^this.refresh; });
 if (asciiChar == $n,  { wavetable.overWrite(tableAsSig.normalize.asWavetable, 0);
 ^this.refresh; 
 }
 );
 
 if (asciiChar == $A, { ^this.autoScale; });
 if (asciiChar == $a, { ^this.scaleToDefault; });
 
 if (asciiChar == $D,  { newTable = PG.currentBank.defaultTables.at(
 this.window.name.asSymbol
 );
 if (newTable.notNil, 
 { wavetable.overWrite(newTable, 0); }
 );
 ^this.refresh; 
 }
 ); 
 
 if (asciiChar == $c,  { 
 newTable = wavetable.copy;
 this.tableClipboard_(newTable); 
  ^this;
 }
 );
 if (asciiChar == $C,  { 
 newTable = wavetable.copy;
  
 ^TableWindow(newTable.copy, "Copy of " ++ window.name).tableView.autoScale;
 }
 );
 if (asciiChar == $v,  { if (tableClipboard.isKindOf(Wavetable), 
 { 
 newTable = tableClipboard; 
    wavetable.overWrite(newTable, 0); 
 this.refresh; 
 ^this;
 }
 )
 }
 );
 if (asciiChar == $V,   { if (tableClipboard.isKindOf(Wavetable), 
 { 
  
 targetSig = tableAsSig; 
 mixSig = tableClipboard.asSignal;
 
 scaler = targetSig.peak.max(1) / mixSig.peak; 
 
 newTable = (mixSig * scaler).asWavetable;
 
 this.wavetable.overWrite(newTable, 0); 
 this.refresh; 
 ^this;
 }
 )
 }
 );
 if (asciiChar == $m,  { 
 if (tableClipboard.isKindOf(Wavetable), 
 {  
 targetSig = tableAsSig; 
 mixSig = tableClipboard.asSignal;
 
 scaler = targetSig.peak.max(1) / mixSig.peak; 
 
 newTable = (targetSig + (mixSig * scaler) * 0.5).asWavetable;
 this.wavetable.overWrite(newTable, 0); 
 this.refresh; 
 ^this;
 }, 
 { MessageWindow("clipboard is empty."); }
 ); 
 
 }
 );
  if (keyCode == 46, 
 { if (modifiers == 8, {  newTable = (tableAsSig + tableClipboard.asSignal).asWavetable; 
 this.wavetable.overWrite(newTable, 0); 
 this.refresh; 
 ^this;
 }); 
 if (modifiers == 10, {  newTable = (tableAsSig * tableClipboard.asSignal).asWavetable; 
 this.wavetable.overWrite(newTable, 0); 
 this.refresh; 
 ^this;
 }); 
 }
 );
 
 if (asciiChar == $M,  { 
 if (tableClipboard.isKindOf(Wavetable), 
 
 { 
 if (modifiers == 10, 
 { newTable = (tableAsSig * tableClipboard.asSignal).asWavetable; },
 {  mixSig = tableClipboard.asSignal; 
 scaler = mixSig.peak.abs.reciprocal; 
 newTable = (tableAsSig * mixSig * scaler).asWavetable; 
 }
 );
 wavetable.overWrite(newTable, 0); 
 this.refresh;
 ^this;
 }, 
 { MessageWindow("clipboard is empty."); }
 ); 
 
 }
 );
  if ("du ".includes(asciiChar), { ^this }); 
 
 ^super.keyDown(asciiChar, keyCode, modifiers);
 });
 }
 
 openEditor { 
 var existingEditor;
 existingEditor = GUIWindow.allWindows.detect({ arg w; w.name == ("Edit : " ++ window.name) }); 
 if (existingEditor.notNil, 
 { existingEditor.wavetable_(wavetable).show.toFront }, 
 { ScaleTablePanel.new(wavetable, window); }
 );
 }
 
 autoScale { arg min, max; 
 var tempSig; 
 
 if (max.isNil or: min.isNil, 
 { tempSig = wavetable.asSignal;  max = max ? tempSig.maxItem;  min = min ? tempSig.minItem; 
 }
 );
 
 if (min == max, { 
 if (max == 0, { min = -1.0; max = 1.0 }, 
 { min = 0; max = max * 2 }
 );
 }); 
  minSetBox.value_(min.trunc(viewSpecs.at(4)));
 maxSetBox.value_( max.round(viewSpecs.at(4)));
 ^this.minval_(min).maxval_(max); 
 }
 
 scaleToDefault { 
 ^this.updateMinval_(viewSpecs.at(0)).updateMaxval_(viewSpecs.at(1));
 }
 
 importSoundFile {
 var file, diskInSignal, newTable;
 if (Synth.isOn, { MessageWindow.new("Sorry, no import while Synth is on."); ^this; });
 
 GetFileDialog.new({ arg ok, path; 
 if (ok, {  file = SoundFile.new; 
 if (file.readHeader(path) 
 and: file.preloadData(0, wavetable.size), 
 
 { diskInSignal = Synth.collect(
 { DiskIn.ar(file) }, 
 wavetable.size * 0.5 / Synth.sampleRate
 );
 
 newTable = if (diskInSignal.isKindOf(Signal),
 { diskInSignal }, 
 { diskInSignal.at(0) })
 .normalize.asWavetable; 
 
 this.wavetable.overWrite(newTable, 0);
 }
 );
 });
 }); 
 }
}
Nil {
 *new { ^this.shouldNotImplement(thisMethod) }
 *newClear { ^this.shouldNotImplement(thisMethod) }
 
 isNil { ^true }
 notNil { ^false }
 ? { arg obj; ^obj }
 
  push { arg function; ^function.value }
 
  spawn {}
  source {}
 source_ {}
  dependants { 
 ^IdentitySet.new 
 }
 changed {}
 addDependant {}
 removeDependant {}
 release {}
 update {}
  transformEvent { arg event;
 ^event
 }
 finishEvent {
 }
 printOn { arg stream;
 stream.putAll(this.asString);
 }
 storeOn { arg stream;
 stream.putAll(this.asCompileString);
 }
}
Env { 
    var <levels;
 var <times;
 var <curves = 'linear';   var <releaseNode;  var <loopNode;  
 
 *new { arg levels=#[0,1,0], times=#[1,1], curve='linear', releaseNode, loopNode;
 ^super.new.init(levels, times, curve, releaseNode, loopNode)
 }
 init { arg argLevelsArray, argTimesArray, argCurvesArray, argReleaseNode, argLoopNode;
 levels = argLevelsArray;
 times = argTimesArray;
 curves = argCurvesArray;
 releaseNode = argReleaseNode;
 loopNode = argLoopNode;
 }
 
  
  *linen { arg attack=1.0, sustain=2.0, release=1.0, level=1.0, curve='linear';
  ^this.new(
 [0, level, level, 0],
 [attack, sustain, release],
 curve
 )
 }
 *triangle { arg dur=1.0, level=1.0;
 dur = dur * 0.5;
 ^this.new(
 [0, level, 0],
 [dur, dur]
 )
 } 
 *sine { arg dur=1.0, level=1.0;
 dur = dur * 0.5;
 ^this.new(
 [0, level, 0],
 [dur, dur],
 'sine'
 )
 } 
 *perc { arg attack=0.01, release=1.0, level=1.0, curve = -4.0;
 ^this.new(
 [0, level, 0],
 [attack, release], 
 curve
 )
 } 
 
 
  *adsr { arg attack=0.01, decay=0.3, sustain=0.5, release=1.0,
 level=1.0, curve = -4.0;
 ^this.new(
 [0, level, level * sustain, 0],
 [attack, decay, release], 
 curve,
 2
 )
 }
 *asr { arg attack=0.01, sustain=1.0, release=1.0, level=1.0, curve = -4.0;
 ^this.new(
 [0, level * sustain, 0],
 [attack, release], 
 curve,
 1
 )
 }
 
  blend { arg argAnotherEnv, argBlendFrac=0.5;
 ^this.class.new(
 levels.blend(argAnotherEnv.levels, argBlendFrac),
 times.blend(argAnotherEnv.times, argBlendFrac),
 curves.blend(argAnotherEnv.curves, argBlendFrac),
 releaseNode,
 loopNode
 )
 }
 plot {
 var timeScale;
 timeScale = 0.01 / times.sum;
 Synth.plot({ arg synth;
 synth.releaseTime = 0.005;
 EnvGen.ar(this, 1, 0, 1, 0, timeScale)
 }, 0.01)
 }
 test { arg releaseTime = 3.0;
 Synth.play({ arg synth;
 synth.releaseTime = releaseTime;
 EnvGen.ar(this, FSinOsc.ar(800, 0.3))
 })
 }
}
Cutoff {
  *ar { arg in, sustain = 1.0, decay = 0.01, curve = 'linear';
 var env;
 env = Env.new(#[1.0, 1.0, 0.0], [sustain, decay], curve);
 ^in * EnvGen.ar(env);
 }
}
Point {
 var <>x = 0, <>y = 0;
 
 *new { arg argX=0, argY=0;
 ^super.new.x_(argX).y_(argY);
 }
 @ { arg aPoint;
 ^Rect.fromPoints(this, aPoint)
 }
 set { arg argX=0, argY=0; x = argX; y = argY; }
 
 asPoint { ^this }
 asComplex { ^Complex.new(x,y) }
 
 == { arg aPoint; ^(x == aPoint.x) and: { y == aPoint.y } }
 hash { ^ (x.hash << 2) bitXor: y.hash }
 
 + { arg delta; 
 var deltaPoint;
 deltaPoint = delta.asPoint;
 ^(this.x + deltaPoint.x) @ (this.y + deltaPoint.y)
 } 
 - { arg delta; 
 var deltaPoint;
 deltaPoint = delta.asPoint;
 ^(this.x - deltaPoint.x) @ (this.y - deltaPoint.y)
 }
 
 * { arg scale; 
 var scalePoint;
 scalePoint = scale.asPoint;
 ^(this.x * scalePoint.x) @ (this.y * scalePoint.y)
 }
 / { arg scale; 
 var scalePoint;
 scalePoint = scale.asPoint;
 ^(this.x / scalePoint.x) @ (this.y / scalePoint.y)
 }
 div { arg scale; 
 var scalePoint;
 scalePoint = scale.asPoint;
 ^(this.x div: scalePoint.x) @ (this.y div: scalePoint.y)
 }
 translate { arg delta; 
 ^(this.x + delta.x) @ (this.y + delta.y)
 } 
 scale { arg scale; 
 ^(this.x * scale.x) @ (this.y * scale.y)
 } 
 rotate { arg angle;  var sinr, cosr;
 sinr = angle.sin;
 cosr = angle.cos;
 ^((x * cosr) + (y * sinr)) @ ((x * sinr) + (y * cosr))  }
 abs { ^x.abs @ y.abs }
 
 rho { ^hypot(x, y) }
 theta { ^atan2(y, x) }
 
 dist { arg aPoint;
 aPoint = aPoint.asPoint;
 ^hypot(x - aPoint.x, y - aPoint.y)
 }
 transpose { ^y @ x }
 
 round { arg quant; 
 quant = quant.asPoint;
 ^x.round(quant.x) @ y.round(quant.y)
 }
 trunc { arg quant; 
 quant = quant.asPoint;
 ^x.trunc(quant.x) @ y.trunc(quant.y)
 }
 
 printOn { arg stream;
 stream << "Point( " << x << ", " << y << " )";
 }
 storeOn { arg stream;
 stream << "Point( " <<< x << ", " <<< y << " )";
 }
}
Color {
 classvar <black = rgb(0,0,0), <white = rgb(255,255,255), <grey = rgb(128,128,128);
 classvar <red = rgb(255,0,0), <green = rgb(0,255,0), <blue = rgb(0,0,255);
 classvar <guiGrey = rgb(176,176,176);
 
 *new { arg red=0, green=0, blue=0, alpha=255;
 _ColorNew
 ^this.primitiveFailed
 }
 *newClear { ^this.shouldNotImplement }
 red { _ColorGetRed }
 green { _ColorGetGreen }
 blue { _ColorGetBlue }
 alpha { _ColorGetAlpha }
 
 printOn { arg stream;
 stream.putAll(this.asString);
 }
 storeOn { arg stream;
 stream.putAll(this.asCompileString);
 }
}


Date {
 var <>year, <>month, <>day, <>hour, <>minute, <>second, <>dayOfWeek, 
 <>rawSeconds, <>bootSeconds;
 
 *getDateAsync { arg function;
 _GetDate
 ^this.primitiveFailed
 }
 *getDate {
 var date;
 this.getDateAsync({ arg inDate; date = inDate; thisProcess.toolboxRelease; });
 thisProcess.toolboxAcquire;
 ^date
 }
 *new { arg year, month, day, hour, minute, second, dayOfWeek, rawSeconds, bootSeconds;
 ^super.new.year_(year).month_(month).day_(day)
 .hour_(hour).minute_(minute).second_(second)
 .dayOfWeek_(dayOfWeek)
 .rawSeconds_(rawSeconds).bootSeconds_(bootSeconds);
 }
 
  *gotDate { arg function, year, month, day, hour, minute, second, dayOfWeek, 
 rawSeconds, bootSeconds;
 var date;
 date = this.new(year, month, day, hour, minute, second, dayOfWeek, 
 rawSeconds, bootSeconds);
 function.value(date);
 } 
}


Synth {
   classvar <sampleRate=44100.0;
 classvar <>defaultBlockSize=64; 
  
 var <ugens;  var <outputs;  var <>parent;  
  var <>blockSize;
 var <>releaseTime;
 var <>channelOffset = 0; 
 
 var schedQueue;  var bSchedQueue;  
 classvar <isOn = false;
 var <>onRelease;  
 *new { arg ugenGraphFunc;
    _Synth_New
 ^this.primitiveFailed
 }
 
 *isOff { ^isOn.not }
 
 *stop {
 _Synth_Stop
 ^this.primitiveFailed
 }
 
 *playWithFadeOut { arg ugenGraphFunc, duration = 60.0, fadeout = 5.0;
 var newsynth, env;
 env = Env.new(#[1, 1, 0],[duration - fadeout, fadeout], -2);
 newsynth = this.new({ arg synth;
 var outputs; 
 outputs = ugenGraphFunc.value(synth);
 outputs * EnvGen.kr(env);
 });
 if (newsynth.notNil, { newsynth.play(duration) });
 ^newsynth
 }
 *play { arg ugenGraphFunc, duration;
 var newsynth;
 newsynth = this.new(ugenGraphFunc);
 if (newsynth.notNil, { newsynth.play(duration) });
 ^newsynth
 }
 *bench { arg ugenGraphFunc, duration, yield=true;
 var newsynth;
 newsynth = this.new(ugenGraphFunc);
 if (newsynth.notNil, { newsynth.bench(duration, yield) });
 ^newsynth
 }
 *collect { arg ugenGraphFunc, duration = 0.01;
 var newsynth;
 newsynth = this.new(ugenGraphFunc);
 ^if (newsynth.notNil, { newsynth.collect(duration) });
 }
 *plot { arg ugenGraphFunc, duration = 0.01, name, bounds;
 var res;
 res = this.collect(ugenGraphFunc, duration);
 name = name ? "plot";
 bounds = bounds ? Rect.new(40,50,600,475);
 ^SignalWindow.new(name, bounds, res);
 }
 *scope { arg ugenGraphFunc, duration = 0.01, name, bounds;
 var wind;
 this.play({ arg synth;
 var scopeData, signalSize, view, ugenGraph;
 
 ugenGraph = ugenGraphFunc.value(synth);
 
 name = name ? "scope";
 bounds = bounds ? Rect.new(40,60,600,475);
 signalSize = duration * sampleRate;
 if (ugenGraph.isKindOf(Array), {
  scopeData = Array.fill(ugenGraph.size, { Signal.newClear(signalSize) });
  wind = SignalWindow.new(name, bounds, scopeData, sampleRate);
  ugenGraph = ugenGraph.collect({ arg ugen, i; 
 view = wind.at(i+1);
   view.scopeSignal = Signal.newClear(signalSize);
 
 Scope.ar(view, ugen) 
 });
 },{
  scopeData = Signal.newClear(signalSize);
  wind = SignalWindow.new(name, bounds, scopeData, sampleRate);
 
 view = wind.at(1);
   view.scopeSignal = Signal.newClear(signalSize);
 
  ugenGraph = Scope.ar(view, ugenGraph)
 });
 });
 wind.close;
 }
 *fftScope { arg ugenGraphFunc, name, bounds, fftsize=4096;
 var wind, fftWindow, cosineTable;
 
 fftWindow = Signal.hanningWindow(fftsize);  cosineTable = Signal.fftCosTable(fftsize);  
 this.play({ arg synth;
 var scopeData, view, ugenGraph, fft;
 
 ugenGraph = ugenGraphFunc.value(synth);
 
 name = name ? "scope";
 bounds = bounds ? Rect.new(40,60,600,475);
 if (ugenGraph.isKindOf(Array), {
  scopeData = Array.fill(ugenGraph.size, { Signal.newClear(fftsize) });
  wind = SignalWindow.new(name, bounds, scopeData, sampleRate);
  ugenGraph = ugenGraph.collect({ arg ugen, i; 
 view = wind.at(i+1);
 view.minval = -150; view.maxval = 0;
   view.scopeSignal = Signal.newClear(fftsize);
 
 fft = FFT.ar(fftsize, 0, cosineTable, fftWindow, nil, ugen, 0.0);
 Scope.ar(view, (fft/fftsize).magnitudeApx.max(1e-10).ampdb);
 ugen
 });
 },{
  scopeData = Signal.newClear(fftsize);
  wind = SignalWindow.new(name, bounds, scopeData, sampleRate);
 
 view = wind.at(1);
 view.minval = -150; view.maxval = 0;
   view.scopeSignal = Signal.newClear(fftsize);
 
 fft = FFT.ar(fftsize, 0, cosineTable, fftWindow, nil, ugenGraph, 0.0);
  Scope.ar(view, (fft/fftsize).magnitudeApx.max(1e-10).ampdb);
 ugenGraph
 });
 });
 wind.close;
 }
 *dualScope { arg ugenGraphFunc, duration = 0.01, name, bounds, fftsize=4096;
 var wind, fftWindow, cosineTable;
 
 fftWindow = Signal.hanningWindow(fftsize);  cosineTable = Signal.fftCosTable(fftsize);  
 this.play({ arg synth;
 var scopeData, fftscopeData, view, ugenGraph, fft, mixout, signalSize;
 
 ugenGraph = ugenGraphFunc.value(synth);
 
 mixout = if (ugenGraph.isKindOf(Array), { Mix.ar(ugenGraph) },{ ugenGraph });
 
 name = name ? "scope";
 bounds = bounds ? Rect.new(40,60,600,475);
 signalSize = duration * sampleRate;
  fftscopeData = Signal.newClear(fftsize);
 scopeData = Signal.newClear(signalSize);
 
  wind = SignalWindow.new(name, bounds, [scopeData, fftscopeData], sampleRate);
 
 view = wind.at(1);
 view.scopeSignal = Signal.newClear(signalSize);
 Scope.ar(view, ugenGraph);
 
 view = wind.at(2);
 view.minval = -150; view.maxval = 0;
 view.scopeSignal = Signal.newClear(fftsize);
 
 fft = FFT.ar(fftsize, 0, cosineTable, fftWindow, nil, mixout, 0.0);
  Scope.ar(view, (fft/fftsize).magnitudeApx.max(1e-10).ampdb);
 ugenGraph
 });
 wind.close;
 }
 *record { arg ugenGraphFunc, duration, pathName,
 headerFormat = 'AIFF', sampleFormat = '16 big endian signed';
 var file, z, newsynth;
 newsynth = this.new({ arg synth;
 var ugenGraph;
 ugenGraph = ugenGraphFunc.value(synth).asArray;
 
 file = SoundFile.new;
 file.headerFormat = headerFormat;
 file.sampleFormat = sampleFormat;
 file.numChannels = ugenGraph.size;
 if (file.writeHeader(pathName), {
 file.prepareRecord;
 DiskOut.ar(file, 32768, ugenGraph)
 },{
 file = nil;
 nil
 });
 });
 
 if (newsynth.notNil, { 
 newsynth.play(duration);
 file.endRecord 
 });
 ^file
 }
 *recordMonoFiles { arg ugenGraphFunc, duration, pathName,
 headerFormat = 'AIFF', sampleFormat = '16 big endian signed';
 var files, z, newsynth;
 
 files = List.new;
 newsynth = this.new({ arg synth;
 var ugenGraph;
 ugenGraph = ugenGraphFunc.value(synth).asArray;
 
   ugenGraph.do({ arg channel, i;
 var file;
 file = SoundFile.new;
 file.headerFormat = headerFormat;
 file.sampleFormat = sampleFormat;
 file.numChannels = 1;
 if (file.writeHeader(pathName ++ "." ++ (i+1).asString), {
 file.prepareRecord;
 files.add(file);
 DiskOut.ar(file, 32768, [channel])
 });
 channel
 });
 });
 
 if (newsynth.notNil, { 
 newsynth.play(duration);
 files.do({ arg file; file.endRecord; });
 });
 ^files
 }

 *write { arg ugenGraphFunc, duration, pathName,
 headerFormat = 'AIFF', sampleFormat = '16 big endian signed';
 
 var synth, file;
 synth = Synth.new(ugenGraphFunc);
 ^if (synth.notNil, {
 file = SoundFile.new;
 file.headerFormat = headerFormat;
 file.sampleFormat = sampleFormat;
 file.numChannels = synth.numChannels;
 if (file.writeHeader(pathName), {
 
 synth.write(file, duration);
 },{
 "Write header failed.".error;
 });
 file
 });
 }
 *sampleRate_ { arg newSampleRate=44100.0;
 if (Synth.isOn, {
 error("cannot change sample rate while playing\n");
 },{
 sampleRate = newSampleRate;
 });
 }
 
 collect { arg duration = 0.01;
   _SynthCollect
 ^this.primitiveFailed
 }
 prPlay { arg duration;  _SynthPlay
 ^this.primitiveFailed
 }
 
 play { arg duration;  isOn = true; 
 this.prPlay(duration); 
 isOn = false;  }
  reset {
 Synth.stop;
 isOn = false;
 }
 bench { arg duration = 5.0, yield=true;
 _SynthBench
 ^this.primitiveFailed
 }
 debug { arg numBlocks = 12;
 _SynthDebug
 ^this.primitiveFailed
 }
 write { arg soundFile, duration = 2.0;
 _SynthWrite
 ^this.primitiveFailed
 }
 
 numChannels {
 if (outputs.isKindOf(Array), { ^outputs.size },{ ^1 })
 }
 
  time {
  _Synth_ElapsedTime
 ^this.primitiveFailed
 }
 beats {
  _Synth_ElapsedBeats
 ^this.primitiveFailed
 }
 sampleCount {
  _Synth_ElapsedSamples
 ^this.primitiveFailed
 }
 
 release {  onRelease.value; 
  this.prRelease;
 } 
 
 prRelease {   _Synth_Release
 ^this.primitiveFailed
 }
 steal {
  _Synth_Steal
 ^this.primitiveFailed
 }
 
 tsched { arg timeFromNow, func;
 _Synth_SchedSecs
 ^this.primitiveFailed
 }
 trepeat { arg start, period, userFunc;
 var count = 0;
 this.tsched(start.value, { arg synth, now, repeatFunc;
 userFunc.value(synth, now, count);
 count = count + 1;
 this.tsched(period.value, repeatFunc);
 });
 }
 trepeatN { arg start, period, maxRepeats, userFunc, completionFunc;
 var count = 0;
 if (maxRepeats > 0, {
 this.tsched(start.value, { arg synth, now, repeatFunc;
 userFunc.value(synth, now, count);
 count = count + 1;
 if (count < maxRepeats, {
 this.tsched(period.value, repeatFunc);
 },{
 if (completionFunc.notNil, {
 this.tsched(period.value, { completionFunc.value(synth, now) }); 
 });
 });
 });
 }); 
 }
 
 sched { arg beatsFromNow, func;
 _Synth_SchedBeats
 ^this.primitiveFailed
 }
 repeat { arg start, period, userFunc;
 var count = 0;
 this.sched(start.value, { arg synth, now, repeatFunc;
 userFunc.value(synth, now, count);
 count = count + 1;
 this.sched(period.value, repeatFunc);
 });
 }
 repeatN { arg start, period, maxRepeats, userFunc, completionFunc;
 var count = 0;
 if (maxRepeats > 0, {
 this.sched(start.value, { arg synth, now, repeatFunc;
 userFunc.value(synth, now, count);
 count = count + 1;
 if (count < maxRepeats, {
 this.sched(period.value, repeatFunc);
 },{
 if (completionFunc.notNil, {
 this.sched(period.value, { completionFunc.value(synth, now) }); 
 });
 });
 });
 }); 
 }
 
 task { arg start, func, stackSize = 512, seed = -1;
 var task;
 task = Task(func, this, stackSize, seed);
 this.sched(start.value, { task.next });
 }
 
 *hardwareName {
 _SynthGetHardware
 ^this.primitiveFailed
 }
 *setInputRouting { arg routingArray;
 _SynthSetInRouting
 ^this.primitiveFailed
 }
 *setOutputRouting { arg routingArray;
 _SynthSetOutRouting
 ^this.primitiveFailed
 }
 *normalRouting {
 this.setInputRouting(Array.series(64,1,1));
 this.setOutputRouting(Array.series(64,1,1));
 }
 
  newTempoBase { arg tempo;
 _Synth_NewTempoBase
 }
 
 tempo_ { arg tempo;
 _Synth_SetTempo
 }
 
 tempo {
 _Synth_GetTempo
 }
 
 *newSynth {
 "CHANGE: Use 'thisSynth' instead of 'Synth.newSynth'".postln;
 ^thisSynth
 }
 
}
Symbol {
 *new { ^this.shouldNotImplement }
  asString { _SymbolString }
 asSymbol { ^this }
  
 asClass { 
 _SymbolClass 
  }
 asSetter {
 _SymbolAsSetter
 ^this.primitiveFailed
 }
 asGetter {
 _SymbolAsGetter
 ^this.primitiveFailed
 }
 
  isSetter {
  _SymbolIsSetter
 ^this.primitiveFailed
 }
 isClassName {
 _SymbolIsClassName
  }
 isMetaClassName {
 _SymbolIsMetaClassName
  }
 
     envirGet { ^currentEnvironment.at(this) }
 envirPut { arg aValue; ^currentEnvironment.put(this, aValue) }
 blend {  ^this
 }
 
  neg { ^this }
 bitNot { ^this }
 abs { ^this }
 asFloat { ^this }
 asInteger { ^this }
 ceil { ^this }
 floor { ^this }
 frac { ^this }
 sign { ^this }
 squared { ^this }
 cubed { ^this }
 sqrt { ^this }
 exp { ^this }
 midicps { ^this }
 cpsmidi { ^this }
 midiratio { ^this }
 ratiomidi { ^this }
 ampdb { ^this }
 dbamp { ^this } 
 octcps { ^this }
 cpsoct { ^this }
 log { ^this }
 log2 { ^this }
 log10 { ^this }
 sin { ^this }
 cos { ^this }
 tan { ^this }
 asin { ^this }
 acos { ^this }
 atan { ^this }
 sinh { ^this }
 cosh { ^this }
 tanh { ^this }
 rand { ^this }
 rand2 { ^this }
 linrand { ^this }
 bilinrand { ^this }
 sum3rand { ^this }
 distort { ^this }
 softclip { ^this }
 nyqring { ^this }
 coin { ^this }
 even { ^this }
 odd { ^this }
  + { arg x; ^this } 
 +! { arg x; ^this } 
 - { arg x; ^this } 
 -! { arg x; ^this } 
 * { arg x; ^this } 
 *! { arg x; ^this } 
 / { arg x; ^this } 
 % { arg x; ^this }
 min { arg x; ^this } 
 max { arg x; ^this }
 & { arg x; ^this }
 | { arg x; ^this }
 bitXor { arg x; ^this } 
 lcm { arg x; ^this }
 gcd { arg x; ^this }
 round { arg x; ^this }
 trunc { arg x; ^this }
 atan2 { arg x; ^this } 
 hypot { arg x; ^this }
 hypotApx { arg x; ^this }
 ** { arg x; ^this }
 >> { arg x; ^this }
 +>> { arg x; ^this }
 ring1 { arg x; ^this }
 ring2 { arg x; ^this }
 ring3 { arg x; ^this }
 ring4 { arg x; ^this }
 difsqr { arg x; ^this }
 sumsqr { arg x; ^this }
 sqrsum { arg x; ^this }
 sqrdif { arg x; ^this }
 <! { arg x; ^this }
 rrand { arg x; ^this }
 exprand { arg x; ^this }
 
 degreeToKey { ^this }
 
 doNumberOp { arg x; ^this }
 doComplexOp { arg x; ^this }
 doSignalOp { arg x; ^this }
 doListOp { arg aSelector, aList; 
 aList.collect({ arg item; 
 item.perform(aSelector, this)
 }) 
 }
 printOn { arg stream;
 stream.putAll(this.asString);
 }
 storeOn { arg stream;
 stream.putAll(this.asCompileString);
 }
}
OscXFadeBank { 
 var <tables, <loopFreq, <tSpawn, loopFreqPlug, tablesPhasor, <>phaseOffset = 0,
 <xFadeLock = false, <topSynth, <xFadePlugs, <loopIsOn = true, <>cheap = true,
 fadeTime = 2, cutoffEnv, spawnSynths, <cycleDisplay, phaseOffsetPlug; 
 var <loopForward = true, loopDirSource = 1, loopDirPlug;
 
 var eventCount = 0;  
 *new { arg tables, loopTime = 10; 
 ^super.new.init(tables, loopTime);
 }
 
 init { arg inTables, inLoopTime; 
 tables = inTables; 
 loopFreq = inLoopTime.reciprocal; 
 cutoffEnv = Env.new(#[1.0, 1.0, 0.0], [0.0, 0.01], releaseNode: 1);
 spawnSynths = List.new;
 }
 
 loopOn { loopIsOn = true }
 loopOff { loopIsOn = false }
 
 lock { xFadeLock = true; }  
 unLock { xFadeLock = false; }  
 loopTime_ { arg newTime; loopFreq = newTime.reciprocal; loopFreqPlug.source = loopFreq; }
 
  loopFreq_ { arg newFreq; loopFreq = newFreq; loopFreqPlug.source = loopFreq; }
 loopTime { ^loopFreq.reciprocal } 
 
 loopForward_ { arg newDir; 
 var flipOffset; 
 if (newDir != loopForward, 
 { if (newDir, 
 { loopDirSource = 1; },
 { loopDirSource = -1; }
 ); 
 
 if (Synth.isOn, 
 { 
 if (newDir != loopForward, 
 { if (newDir, 
 { loopDirPlug.source = 1; },
 { loopDirPlug.source = -1; }
 ); 
 }); 
 
  
  
    
 flipOffset = tablesPhasor.poll * 2 - phaseOffsetPlug.source; 
 phaseOffsetPlug.source = flipOffset; 
 } 
 ); 
 
 loopForward = newDir;
 }
 )
 }
 
 startFade { arg newTables, time; 
 if (xFadeLock, { "Fading Already!".postln },  { 
 tables = newTables; 
 fadeTime = time ? fadeTime; 
 tSpawn.trigger; 
 }
 );
 }
 
 cycleDisplay_ { arg controlView; 
 if (controlView.respondsTo(\value_), 
 { cycleDisplay = controlView; }
 );
  } 
 
 jumpToPhase { arg newPhase = 0;  var newOffset;  if (Synth.isOn, 
 { 
 newOffset = newPhase - (tablesPhasor.poll - phaseOffsetPlug.source); 
 phaseOffsetPlug.source = newOffset; 
 }, 
 { phaseOffset = newPhase }
 ); 
 }
 
 checkLoopPos { arg prevPhase; 
 var newPhase;
 newPhase = tablesPhasor.poll.wrap(0, 2pi); 
 
 if (newPhase < prevPhase, 
 { if (loopIsOn.not, { Synth.stop }) }
 ); 
   if (cycleDisplay.notNil, { cycleDisplay.value = newPhase * 5 / pi; }); 
 
 ^newPhase;
 }
 asUgen { 
 var out, oldSynth, fadeInPlug, fadeOutPlug, prevPhase = 0;
 
 eventCount = 0;   
  xFadePlugs = Plug.kr([1, 0]);  
 loopFreqPlug = Plug.kr( loopFreq );  
 phaseOffsetPlug = Plug.ar( phaseOffset, 0.0 );  loopDirPlug = Plug.kr(loopDirSource, 0.0); 
 
 tablesPhasor = Phasor.ar(loopFreqPlug, mul: loopDirPlug, add: phaseOffsetPlug);  
 thisSynth.repeat(0.0333, 0.0333,   { prevPhase = this.checkLoopPos(prevPhase); }
 );
 
 out = TSpawn.ar(  { arg spawn, i, synth; 
 var newOut, cutoffEnvGen; 
 
 spawnSynths.add(synth);  #fadeInPlug, fadeOutPlug = xFadePlugs.rotate(eventCount % 2); 
   
 eventCount = eventCount + 1;  
 if ( (spawnSynths.size == 2), 
 { this.lock;  fadeOutPlug.line(0, fadeTime); 
 fadeInPlug.line(1, fadeTime); 
 oldSynth = spawnSynths.removeAt(0); 
  oldSynth.parent.sched(fadeTime, 
 { 
  oldSynth.release;   this.unLock;  }
 ); 
 }
 );
 
 cutoffEnvGen = EnvGen.kr(cutoffEnv, fadeInPlug);  
 newOut = tables.collect(
 { arg eachTable; 
 if (cheap == true,          
 {  K2A.ar(
 Osc.kr(  eachTable, 
 freq: 0, 
 phase: tablesPhasor
 ) * cutoffEnvGen
 ) 
 }, 
 {  Osc.ar(eachTable, 
 freq: 0, 
 phase: tablesPhasor,
 mul: cutoffEnvGen
 )
 }
 );
 }
 );
 }, 
 numChannels: tables.size
 );   
 tSpawn = out.source;  thisSynth.sched(0.003, { tSpawn.trigger });  
 ^out; 
 }
}
Boolean {
 *new { ^this.shouldNotImplement(thisMethod) }
 *newClear { ^this.shouldNotImplement(thisMethod) }
 xor { arg bool; ^(this === bool).not }
 if { ^this.subclassResponsibility(thisMethod) }
 not { ^this.subclassResponsibility(thisMethod) }
 && { ^this.subclassResponsibility(thisMethod) }
 || { ^this.subclassResponsibility(thisMethod) }
 and { ^this.subclassResponsibility(thisMethod) }
 or { ^this.subclassResponsibility(thisMethod) }
 
 
 keywordWarnings { 
  _KeywordError 
 } 
 trace { _Trace }  tabletTracking { _TabletTracking }  
 printOn { arg stream;
 stream.putAll(this.asString);
 }
 storeOn { arg stream;
 stream.putAll(this.asCompileString);
 }
}
True : Boolean {
 if { arg trueFunc, falseFunc; ^trueFunc.value }
 not { ^false }
 && { arg bool; ^bool }
 || { arg bool; ^this }
 and { arg function; ^function.value }
 or { arg function; ^this }
 binaryValue { ^1 }
}
False : Boolean {
 if { arg trueFunc, falseFunc; ^falseFunc.value }
 not { ^true }
 && { arg bool; ^this }
 || { arg bool; ^bool }
 and { arg function; ^this }
 or { arg function; ^function.value }
 binaryValue { ^0 }
}

Main : Process {
 classvar <>runAction; 
 
 startUp {
 super.startUp;
 false.trace;
 currentEnvironment = Environment.new;
 this.initLib;
 ParamSpec.default;
 OSCNode.default;
 ObjectTable.init;
 }
 hardwareSetup {
 super.hardwareSetup;
 false.trace;
 if (Synth.hardwareName == 'Korg', {
  Synth.setOutputRouting([1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 1, 2]);
 Synth.setInputRouting([9, 10, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]);
 },{
 Synth.normalRouting;
 });
 }
 initLib { 
 var vsp;
  PG.init; 
 runAction = { PG.run };
 }
 run { runAction.value; }
}
 
PathName { var <fullPath, <colonIndices;
 *new { arg path = ""; 
 ^super.new.init(path); 
 } 
 init { arg inPath;    fullPath = inPath; 
 colonIndices = List.new; 
 fullPath.do({ arg eachChar, i; 
 if (eachChar == $:, { colonIndices.add(i) }); 
 });
 } 
 
 fileName { 
 ^fullPath.copyRange((this.lastColonIndex) + 1, fullPath.size -1);
 } 
 
 pathOnly { 
 ^fullPath.copyRange(0, this.lastColonIndex);
 }
 
 diskName { ^fullPath.copyRange(0, colonIndices.first - 1) }
 
 isAbsolutePath { ^this.isRelativePath.not }
 
 isRelativePath { ^fullPath.first == $: }
 allFolders { 
 var folderNames, pathCopy; 
 folderNames = List.new; 
 pathCopy = fullPath;
 colonIndices.doAdjacentPairs({ arg startColon, endColon; 
 folderNames.add( fullPath.copyRange(startColon + 1, endColon - 1) );
 });
 
 ^folderNames
 }
 
 folderName {
 var indexBeforeFolder; 
 if (colonIndices.isEmpty, { ^"" }); 
 
 indexBeforeFolder = 
 if (colonIndices.size == 1, 0, 
 { colonIndices.at(colonIndices.size - 2) + 1 });
 ^fullPath.copyRange(indexBeforeFolder, this.lastColonIndex - 1);
 } 
 lastColonIndex { 
 ^if (colonIndices.isEmpty, { -1 }, { colonIndices.last }) ;
 } 
 
 nextName { 
 var nextName;
 nextName = if (fullPath.last.isDecDigit, 
 { this.noEndNumbers ++ (this.endNumber + 1).asString;
 },
 {fullPath ++ "1"; }
 );
 ^nextName; 
 }
 
 noEndNumbers { 
 var result, count = 0; 
 
 result = fullPath.copy;
 while(
 { count = count + 1;
 fullPath.at(fullPath.size - count).isDecDigit;
 }, 
 { result = result.copyRange(0, result.size - 2) }
 );
 ^result;
 }
 endNumber {  
 var reverseNumString = "";
 var count = 0, char, number;
 while(
 { count = count + 1;
 char = fullPath.at(fullPath.size - count);
 char.isDecDigit;
 }, 
 { reverseNumString = reverseNumString ++ char }
 );
  number = reverseNumString.inject(0, 
 { arg sum, eachChar, i; sum = sum + (eachChar.digit * (10 ** i))
 }
 );
 ^number
 }
}