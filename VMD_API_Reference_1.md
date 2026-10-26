# Voltage Module Designer: Library menu reference

Source: screenshots of the Designer "Library" panel supplied by the user (2026-10-22).
Evidence tags: **[READ]** = signature/tooltip text seen verbatim; **[NAME]** = method name seen in the list, signature not seen; **[GAP]** = part of the list not captured.
Only tooltips that were visible are quoted. Anything marked [NAME] needs a hover before I rely on its arguments or return value.

## Captured tooltips (verbatim)

| Item | Tooltip |
|---|---|
| VoltageSound.LoadFromFile | `native boolean LoadFromFile(String filePath)` Loads a sound from a file. Supported formats are OGG, MP3, WAV and AIFF. |
| VoltageModule.ShowOpenFileDialog | `native String ShowOpenFileDialog(String dialogBoxTitle, String initialDirectory, String fileTypesToShow)` Shows a modal open-file dialog |
| VoltageModule.ShowSaveFileDialog | `native String ShowSaveFileDialog(String dialogBoxTitle, String initialDirectory, String fileTypesToShow)` Shows a modal save-file dialog |
| VoltageModule.WritePolyBusValue | `void WritePolyBusValue(int bus, int polyChannel, double value)` Writes a value to a given poly bus; bus index has a valid input range of 0 - 31 |
| (component).AddChildComponent | `native boolean AddChildComponent(VoltageComponent child)` Add a child component to this component. Moving or hiding/showing this component will also move/hide/show all its child components. |
| VoltageAudioJack.SetValue | `native void SetValue(double newValue)` Sets the current sample value for the jack. You'd typically call this from your ProcessSample() function to set the value for an output jack on your module. |
| VoltageButton ctor | `VoltageButton(String name, VoltageModule module, boolean bCallCreateButton)` |
| (component).GetTransparency | `double GetTransparency()` Returns this control's opacity. 1.0 = transparent, 0 = fully opaque. (as worded in Designer) |
| (component).SetZOrder | `native void SetZOrder(int zorder)` -1 = front, 0 = back, higher positive integers indicate higher positions in z-order |
| (component).SetSize | `native void SetSize(int width, int height)` sets the control's width and height, in pixels |
| (component).GetX | `int GetX()` Returns the control's horizontal position in pixels relative to its parent module's origin |
| (component).SetEnabled | `native void SetEnabled(boolean isEnabled)` Enables or disables the control |
| (component).GetUserProperty | `Object GetUserProperty(String property)` retrieves data that was stored via SetUserProperty() |
| (component).SetWantsMouseNotifications | `native void SetWantsMouseNotifications(boolean bWantsNotifications)` Set to true if you want this object to receive mouse notifications (Object_MouseMove, Object_LeftButtonUp, etc.) |
| VoltageCurve.SetStartValue | `native void SetStartValue(float val)` |
| VoltageCurve.CurveChangeNotification | object sent as the 'object' parameter to Notify() during Curve_Changed; members are public variables |
| VoltageDigitalCounter.SetJustificationFlags | `native void SetJustificationFlags(Justification justificationFlag)` Sets justification for digits within the counter's bounding rectangle |
| VoltageKeyboard ctor | `VoltageKeyboard(String name, VoltageModule parentModule)` Creates a horizontally-oriented VoltageKeyboard |
| VoltageKeyboard.GetOctaveForMiddleC | `native int GetOctaveForMiddleC()` |
| VoltageKnob.SetValueNoNotification | `native void SetValueNoNotification(double newValue, boolean bUpdateGUI)` sets the knob's value in a range from its min to max values; doesn't trigger a call to Notify() |
| VoltageLabel.SetBorderColor | `native void SetBorderColor(Color zeColor)` sets the label object's border color |
| VoltageLED.SetStopBlinking | `native void SetStopBlinking()` Stops a blink cycle started by SetMomentaryBlink() or SetBlinkEvery() |
| VoltageLine.GetStartX | `int GetStartX()` |
| VoltageMidiJack ctor | `VoltageMidiJack(String name, VoltageModule module, JackType type)` |
| VoltageSlider.SetSize | (same as component SetSize) |
| VoltageSound.Play | `native void Play(boolean bDeleteWhenDone)` Plays the sound and optionally deletes it when done |
| VoltageSound.SetLooping | `native void SetLooping(boolean bLoop)` |
| VoltageSound.ReadSamples | `native int ReadSamples(int numSamples, float[] leftSamples, float[] rightSamples)` Reads samples from the sound |
| VoltageToggle.SetButtonToggledStateImageOverlay | `native void SetButtonToggledStateImageOverlay(String resourceName, ButtonState state)` |
| VoltageViewport.AddVoltageComponent | `native boolean AddVoltageComponent(VoltageComponent child, int x, int y)` Adds a child component at the specified location (in pixels) |
| VoltageMouseKeyFlags | Passed as the 'object' parameter to Notify() with mouse notifications (Object_MouseMove, Object_LeftButtonDown, etc.) |
| VoltageModule.RegisterFileTypesForFileDrop | `native void RegisterFileTypesForFileDrop(String listOfFileTypes)` Specifies file types that this module will accept being dropped onto it. Parameter should be one or more extensions separated by semicolons (i.e., "mp3;ogg") |
| File_Dropped (notification) | User has drag & dropped a file onto the module; 'object' is a String containing the file path |
| VoltageModule.StartGuiUpdateTimer() | `void StartGuiUpdateTimer()` Starts the GUI update timer with the default time of 50 ms |
| VoltageModule.StartGuiUpdateTimer(long) | `void StartGuiUpdateTimer(long updateMilliseconds)` Starts the GUI update timer with a specified frequency (in milliseconds). |
| VoltageCanvas.GetGraphics | `Graphics2D GetGraphics()` returns a Java Graphics2D object for painting on this canvas |
| VoltageModule.SetGlobalProperty | `native void SetGlobalProperty(String propertyName, String value)` Sets a global property |
| VoltageSwitch.SetStateNames | `void SetStateNames(java.util.List<String> stringArrayList)` Allows you to provide a list of state names that will display as tooltips for the switch. |
| Values.ParseStringToDouble | `static double ParseStringToDouble(String string)` Converts a string to a double |
| ADSREnvelope | a traditional attack, decay, sustain, release envelope |
| AnalogFilter | a class to simulate an analog filter |
| BiQuadFilter ctor | `BiQuadFilter(FilterType type, double frequency, double gain, double bandwidth)` |
| SmoothValue ctor | `SmoothValue(double startValue)` |
| DigitalReverbEffect/Spring/etc. ProcessSample | `double ProcessSample(double input)` Processes a sample |
| SamplerVoiceProgram.attackHoldTime | `double attackHoldTime` Attack hold time in milliseconds |
| Jack_Disconnected | Sent when all cables have been disconnected from a jack; longValue is the new cable ID |
| Preset_Loading_Start | Sent at the beginning of the preset-loading process for this module, caused by loading either a Voltage Modular preset or a module-specific preset. NOT called when a user drags a module onto a cabinet. |
| VoltageKeyPressInfo.IsF8Key / IsHomeKey | `boolean IsF8Key()` etc. returns true if this key press is that key |

## VoltageSystem
GetApplicationPath, GetLibraryPath, GetDocumentsPath, GetUserLibraryPath [NAME]

## VoltageModule (methods)
CreateUndoNode (x2), StartGuiUpdateTimer (x2), StopGuiUpdateTimer, StartNamedTimer, StopNamedTimer, **ShowOpenFileDialog [READ]**, **ShowSaveFileDialog [READ]**, GetBinaryResource, GetTotalSamplesMixed, GetTempo, GetTimeSignatureNumerator, GetTimeSignatureDenominator, GetPlayPositionInSeconds, GetPlayPositionInQuarterNotes, Log, LogError, ShowDropDownMenu, ShowMessageBox, OutputMidiToHost, GetNumberOfPolyVoices, GetMaxNumberOfPolyVoices, SetMouseCursor, GetCursorType, **RegisterFileTypesForFileDrop**, AddComponent, GetIOPanelValue, GetIOPanelPolyValue, GetIOPanelMIDIMessages, GetIOPanelInputValue, SetIOPanelOutputValue, ReadBusValue, WriteBusValue, ReadPolyBusValue, **WritePolyBusValue [READ]**, ReadMidiBusMessages, WriteMidiBusMessage, GetNumberOfVariations, GetCurrentVariationIndex, GetCurrentVariationName, GetVariationNames, LoadVariation (x2), SaveVariation, DeleteVariation (x2), SetGlobalProperty, GetGlobalProperty, SetCustomCursor, GetManufacturerProperty, SetManufacturerProperty, GetCurrentKeyStates [NAME unless noted]
[GAP] The top of the VoltageModule list before `CreateUndoNode` (the lifecycle methods) was not captured here. The lifecycle methods (Initialize, ProcessSample, ProcessBypassedSample, Notify, GetStateInformation, SetStateInformation, GetStateInformationForVariations, SetStateInformationForVariations, GetTooltipText, EditComponentValue) are visible in your generated module code.

## Common to every GUI component (listed under each control)
GetComponentID, SetPosition, SetSize, SetRect, GetRect, GetX, GetY, GetWidth, GetHeight, SetVisible, IsVisible, SetZOrder, SetTransparency, GetTransparency, SetUserProperty, GetUserProperty, SetEnabled, IsEnabled, SetSkin, GetSkin, SetWantsMouseNotifications, WantsMouseNotifications, AddChildComponent, RemoveChildComponent (not every control lists every one; jacks/lines/LEDs omit a few)

## GUI controls

- **VoltageAnalogVUMeter**: SetValue, GetValue (+ common)
- **VoltageAudioJack**: AudioJack, IsConnected, **SetValue [READ]**, GetValue (+ common)
- **VoltageButton**: VoltageButton (x2 ctors), SetDragMode, ShowOverlay, SetOverlayArea (x2), SetOverlayTextFont, SetOverlayTextColor, SetOverlayText, SetOverlayTextJustification, SetValue, GetValue, SetValueNoNotification, SetImageOverlay, GetImageOverlay, SetButtonStateImageOverlay, GetButtonStateImageOverlay, SetOverlayStateTextColor (x2), GetOverlayTextColor (x2), SetAutoRepeat (+ common)
- **VoltageCanvas**: SetAlwaysRedrawEntireCanvas, GetGraphics, Clear, GetPixelData, GetPixelDataRect, Invalidate, InvalidateRect, SetHighDensity, IsHighDensity, GetBitmapWidth, GetBitmapHeight (+ common)
- **VoltageCurve**: SetBackgroundColor, SetBorderColor, SetCurveColor, SetDottedLineColor, SetBorderSize, SetCurveThickness, SetHasRoundedCorners, SetRange, SetStartValue, SetEndValue, getters, SetEditable, IsEditable, SetHorizontal, IsHorizontal, GetRangeMin/Max, SetCurveType, GetCurveType, SetControlPoint1EditType, Get..., SetControlPoint1/2, GetControlPoint1/2, GetCurvePosition, GetCurvePositionNormalized, InvertValue
- **VoltageDigitalCounter**: SetBarsForDigit, ClearBarsForDigit, SetJustificationFlags, SetValue, GetValue, SetDigitsBarArray, ClearAllDigits
- **VoltageEllipse**: SetEllipseColor, SetBorderColor, SetBorderSize, getters
- **VoltageImage**: SetCurrentImage, AddNewImage, StartAnimation, GetNumberOfFrames, SetCurrentFrame, GetCurrentFrame, SetAnimationSpeed, AddImageFromMemory, Animate, SetAnimateMode
- **VoltageKeyboard**: SetKeyboardColors, SetKeySizes, SeKeyRange (sic), SetScrollButtonsVisible, SetScrollButtonWidth, SetVelocity, SetMidiChannel, IsNoteOn, SetNoteOn, SetNoteOff, SetAllNotesOff, SetKeyPressesToAutomaticallyGoTo_IOModule, SetNoteText, ClearNoteText, ClearAllNoteText, SetKeyPressForNote, RemoveKeyPressForNote, SetKeyPressBaseOctave, SetShowKeyPressMappingsOnKeys, SetOctaveForMiddleC, GetOctaveForMiddleC
- **VoltageKnob**: GetMinValue, GetMaxValue, SetRange, SetKnobParams, **DisplayValueInPercent**, SetMouseSensitivity, SetRingSkin, GetRingSkin, SetKnobAdjustsRing, GetKnobAdjustsRing, SetRingValue, GetRingValue, SetValue, GetValue, **SetValueNoNotification [READ]**, SetMidpointValue, SetRangeSkewValue, GetMidpointValue, IsSkewedFromCenter, GetRangeSkewValue, HasNonLinearRange, GetDefaultValue
- **VoltageLabel**: SetText, GetText, SetColor, GetColor, SetFont, SetJustificationFlags, SetEditable, SetHorizontalScale, IsBold, IsItalic, GetFontSize, GetFont, GetJustificationFlags, SetEditOutlineColor/TextColor/BackColor (+Get), SetBorderColor, SetBkColor, SetBorderSize, SetMultiLineEdit, SetHasCustomTextHoverColor, SetTextHoverColor (+Get)
- **VoltageLED**: SetMomentaryBlink, SetBlinkEvery, SetStopBlinking, SetValue, GetValue
- **VoltageLine**: SetLineColor, SetLineWidth, SetPosition, SetStartPosition, GetStartX/Y, SetEndPosition, GetEndX/Y, SetHasArrow, HasArrow, SetArrowWidth/Length (+Get)
- **VoltageMidiJack**: AddMessage, GetMessages, ClearMessages, ClearMessagesBeforeWriting, IsConnected
- **VoltagePolyJack**: SetPolyValue, GetPolyValue, IsConnected
- **VoltageRectangle**: SetRectangleColor, SetBorderColor, SetBorderSize, SetCornerSize (+Get)
- **VoltageScrollbar**: GetPosition, GetRangeStart, GetRangeEnd, GetThumbSize, SetRange, SetThumbSize, ScrollTo, SetThumbCornerSize
- **VoltageSlider**: SetRange, DisplayValueInPercent, SetMouseSensitivity, SetMouseDownRelativeDragMode, SetSliderSize, SetTrackSize, SetValue, GetValue, SetValueNoNotification, SetMidpointValue, SetRangeSkewValue, GetMidpointValue, IsSkewedFromCenter, GetRangeSkewValue, HasNonLinearRange, GetDefaultValue, SetRangeMin/MaxBlankPixels (+Get)
- **VoltageSoundView**: SetColors, played/unplayed/back/play-indicator colors, Play (x2), Stop, SeekToStart, LoadFromMemory, LoadFromFile, LoadFromResource, LoadAudioData, ReadSamples, NumSamples, CurrentOffset, SeekTo, Looping, SetLooping, IsPlaying
- **VoltageSwitch**: SetState, SetStateNames, SetMouseSensitivity, GetValue, GetDefaultState, SetInitialState
- **VoltageToggle**: as VoltageButton plus SetButtonToggledStateImageOverlay (+Get), SetToggledOverlayStateTextColor (x2), GetToggledOverlayTextColor, IsToggledByDefault
- **VoltageViewport**: AddVoltageComponent, RemoveVoltageComponent, NumVoltageComponents, GetVoltageComponent, SetViewedSize, GetViewedWidth/Height, SetViewedPosition, GetViewedPositionX/Y, SetScrollThickness (+Get), CanScrollVertically, CanScrollHorizontally, SetScrollSingleStep, SetZoom, SetBackgroundColor, SetScrollBackColor/TrackColor/ThumbColor, SetShowScrollbarsIfNeeded, Set/Get Horizontal/VerticalZoom
- **VoltageVUMeter**: InLinearMode, SetLinearMode, SetValue, GetValue

## Other GUI classes
VoltageCurve.CurveChangeNotification (ctrlPoint1X/1Y/2X/2Y and ...Changed flags), VoltageKeyPressInfo (GetKeyCode, GetCharacter, IsNonCharacterKeyCode, Is*Key for backspace, delete, arrows, end, enter, escape, F1-F35, fast-forward, home, insert, numpad keys, page up/down, play, return, rewind, space, stop, tab; IsShiftKeyDown, IsCtrlKeyDown, IsAltKeyDown, IsCommandDown), VoltageMouseKeyFlags (IsShiftKeyDown, IsCtrlKeyDown, IsAltKeyDown, IsCommandDown, IsLeftButtonDown, IsRightButtonDown, IsMiddleButtonDown)

## Audio processing
- **AnalogOscillator**: SetFrequency, SetPulseWidth, GetPulseValue, GetSquareValue, GetSawtoothValue, GetRampValue, GetTriangleValue, GetSineValue, AdvanceSample, SetCycle, GetCycle, ResetCycle, GetPureSineValue
- **DigitalOscillator**: same set as AnalogOscillator [partly captured]
- **NoiseSource**: GetWhiteNoise, GetPinkNoise
- **ADSREnvelope**: SetAttackTime, SetAttackHoldTime, SetDecayTime, SetSustainLevel, SetReleaseTime, SetGate, GetValue, AdvanceSample, GetStage, SetFastAttack, Reset
- **PercussionEnvelope**: SetAttackHoldTime, SetDecayTime, SetDecayCurve, SetSmoothAttack, Trigger, Reset, IsActive, GetValue
- **EnvelopeFollower**: SetAttackTime, GetAttackTime, SetReleaseTime, GetRelease, ProcessSample
- **AnalogFilter**: SetCutoff, SetResonance, SetTwoPole, SetFourPole, ProcessSample, GetLowpassValue, GetBandpassValue, GetHighpassValue
- **SaturatedAnalogFilter**: same plus Reset
- **BiQuadFilter**: two ctors, ProcessSample, SetFrequency/GetFrequency, SetdBGain/GetdBGain, SetBandwidth/GetBandwidth
- **DelayEffect**: SetDelayTime, SetFeedback, SetDryVolume, SetWetVolume, SetFilterCutoff, Reset, ProcessSample
- **DigitalReverbEffect**: ProcessSample (x2), SetDamp, SetRoomSize, SetWet
- **SpringReverbEffect**: SetWetVolume, SetDryVolume, SetDecaySpeed, ProcessSample
- **TubePreamp**: SetDrive, Process
- **Limiter**: SetThresholdDecibels, SetThresholdPercent, SetAttackTime, SetReleaseTime, getters, Reset, ProcessSample (x2), GetGain
- **Distortion**: SetPreGain, SetDistortion, SetPostGain, Process
- **ClockDivider**: SetDivisor, Reset, ProcessSample
- **SmoothValue**: SetGlideTime, SetValue, ResetValue, GetSmoothValue, GetTargetValue, SetFixedTime
- **WaveSmoother**: Smooth (x2)
- **VoltageSound**: Play (x2), Stop, SeekToStart, LoadFromMemory, **LoadFromFile [READ]**, LoadFromResource, LoadAudioData, ReadSamples, NumSamples, CurrentOffset, SeekTo, Looping, SetLooping, IsPlaying, IsStereo, GetMetaDataValue, SampleRate, SetResampling
- **SamplerVoice**: NoteOn, NoteOff, IsPlaying, GetNote, GetProgram, SetPitchModulation, SetFilterModulation, ProcessSamples, Reset
- **SamplerVoiceProgram**: fields sampleInfo, sampleName, loopStartSample, loopEndSample, pan, coarseTune, tune, speed, rootNote, attenuation, velToAttenuation, attackTime, attackHoldTime, decayTime, sustainLevel, releaseTime, filterCutoff, filterResonance, lowFilterCutoff, highFilterCutoff, loopSample, reverseSamples, smoothResampling
- **SamplerVoiceProgram.SampleInfo**: Load16BitBuffer, LoadFloatBuffer, LoadDoubleBuffer, sampleData, numSamples, numChannels, sampleRate
- **VoltageAudioDevice**: Initialize, GetInputDeviceNames, GetOutputDeviceNames, OpenDevices, GetBufferSize, Close, GetLastError, Start, Stop

## Enumerations
- ADSREnvelope.ENV_STAGE: ADSR_Stage_Off, _Attack, _Attack_Hold, _Decay, _Sustain, _Release
- BiQuadFilter.FilterType: PEAKINGEQ, LOWSHELF, HIGHSHELF, LOWPASS, HIGHPASS, BANDPASS, NOTCH
- Jack.JackType: JackType_AudioInput, JackType_AudioOutput, JackType_MidiInput, JackType_MidiOutput
- VoltageButton.Justification / VoltageLabel.Justification / VoltageDigitalCounter.Justification: Left, Right, HorizCentered, Top, Bottom, VertCentered, HorizJustified, Centered, CenteredLeft, CenteredRight, CenteredTop, CenteredBottom, TopLeft, TopRight, BottomLeft, BottomRight
- VoltageButton.ButtonState: Normal, Hover, Pushed, Disabled
- VoltageCurve.ControlPt1EditType: BothXAndYEditable, XMirrorsY, YMirrorsX
- VoltageCurve.CurveType: Cubic, Quadratic
- VoltageCurve.MouseState: Normal, Hover, Pushed, Disabled, ALL_Mouse_States
- VoltageDigitalCounter.Flags: None, Top, TopLeft, TopRight, Center, BottomLeft, BottomRight, Bottom
- VoltageImage.AnimationMode: Forward, Backward, ForwardAndBack, BackAndForth
- VoltageKeyboard.KeyboardOrientation: Horizontal, VerticalLeft, VerticalRight
- VoltageModule.IOPanelPolyValues: IOPanel_PolyPitch, _PolyGate, _PolyVelocity
- VoltageModule.IOPanelValues: IOPanel_Pitch, _Gate, _Trigger, _Velocity, _Aftertouch, _Sustain, _Bend, _ModWheel, _Play, _Stop, _Sync, _PlayGate
- VoltageModule.MessageBoxType: NoIcon, QuestionIcon, WarningIcon, InfoIcon
- **VoltageModule.ModuleNotifications**: Jack_Connected, Jack_Disconnected, Knob_Changed, Slider_Changed, Button_Changed, Switch_Changed, GUI_Update_Timer, Named_Timer, Canvas_Painting, Canvas_Painted, SoundPlayback_Start, SoundPlayback_End, Control_DragStart, Control_DragOn, Control_DragOff, Control_DragEnd, Label_Changed, Scrollbar_Position, PolyVoices_Changed, **File_Dropped**, Preset_Loading_Start, Preset_Loading_Finish, Variation_Loading_Start, Variation_Loading_Finish, Tempo_Changed, Randomized, VariationListChanged, Object_MouseMove, Object_MouseLeave, Object_LeftButtonDown, Object_LeftButtonUp, Object_RightButtonDown, Object_RightButtonUp, Object_LeftButtonDoubleClick, Key_Press, Reset, Keyboard_NoteOn, Keyboard_NoteOff
- VoltageModule.MouseCursorTypes: Parent, Normal, Busy, IBeam, Crosshair, Copying, PointingHand, DraggingHand, ResizeLeftRight, ResizeUpDown, ResizeLeftRightUpDown, ResizeLeftEdge, ResizeRightEdge, ResizeTopEdge, ResizeBottomEdge, ResizeTopLeft, ResizeTopRight, ResizeBottomLeft, ResizeBottomRight, CustomCursor

## Values (static helpers)
SampleRate, MsPerSample, SamplesPerMs, PI, HALF_PI, LinearToVolumeTransform, VolumeToLinearTransform, PercentToDecibels, DecibelsToPercent, FastAtan, FastTanh, ParseStringToDouble, FastRandom, FastRandomInclusive, FastTwoToTheX, FastTenToTheX, FastEToTheX [NAME]
FastEToTheX is the last entry in the list (confirmed by the user).

## Not in the list (confirmed absent from the captured parts)
No FFT, pitch detector, frequency counter or text-file reader. `VoltageSound.LoadFromFile` is audio-only.

## Resolved by later hovers
- File drop: `RegisterFileTypesForFileDrop("tun;scl")`, then `File_Dropped` gives the path as a String in `object`. [READ]
- GUI timer: default 50 ms, or `StartGuiUpdateTimer(long ms)`. [READ]
- Canvas painting uses Java `Graphics2D`. [READ]
- Global properties are String-to-String. [READ]
- Switch state names are tooltips (a `List<String>`), not on-panel labels. [READ]

## Still open
1. ShowOpenFileDialog: return value on cancel (null or ""), and the `fileTypesToShow` format.
2. ParseStringToDouble: behaviour on a bad string (exception or 0?).
3. Plain `java.io` file reads on the returned path (needs the small test module).
