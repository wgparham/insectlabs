package com.insectlabs.osc;


import voltage.controllers.*;
import voltage.core.*;
import voltage.core.Jack.JackType;
import voltage.sources.*;
import voltage.utility.*;
import voltage.processors.*;
import voltage.effects.*;
import java.awt.*;

//[user-imports]   Add your own imports here
//[/user-imports]


public class osc extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public osc( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "OSC", ModuleType.ModuleType_Oscillators, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "94b99920099e49828b7f66d6e3321e3f" );
    }

void InitializeControls()
{

        labelPulseRed = new VoltageLabel( "labelPulseRed", "SQR/PLS Label Red", this, "—————  SQR  —————" );
        AddComponent( labelPulseRed );
        labelPulseRed.SetWantsMouseNotifications( false );
        labelPulseRed.SetPosition( 0, 128 );
        labelPulseRed.SetSize( 115, 18 );
        labelPulseRed.SetEditable( false, false );
        labelPulseRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelPulseRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelPulseRed.SetColor( new Color( 164, 36, 36, 255 ) );
        labelPulseRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelPulseRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelPulseRed.SetBorderSize( 1 );
        labelPulseRed.SetMultiLineEdit( false );
        labelPulseRed.SetIsNumberEditor( false );
        labelPulseRed.SetNumberEditorRange( 0, 100 );
        labelPulseRed.SetNumberEditorInterval( 1 );
        labelPulseRed.SetNumberEditorUsesMouseWheel( false );
        labelPulseRed.SetHasCustomTextHoverColor( false );
        labelPulseRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelPulseRed.SetFont( "Arial", 9, true, false );

        labelPulseBlack = new VoltageLabel( "labelPulseBlack", "SQR Label Black", this, "SQR" );
        AddComponent( labelPulseBlack );
        labelPulseBlack.SetWantsMouseNotifications( false );
        labelPulseBlack.SetPosition( 0, 128 );
        labelPulseBlack.SetSize( 115, 18 );
        labelPulseBlack.SetEditable( false, false );
        labelPulseBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelPulseBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelPulseBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelPulseBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelPulseBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelPulseBlack.SetBorderSize( 1 );
        labelPulseBlack.SetMultiLineEdit( false );
        labelPulseBlack.SetIsNumberEditor( false );
        labelPulseBlack.SetNumberEditorRange( 0, 100 );
        labelPulseBlack.SetNumberEditorInterval( 1 );
        labelPulseBlack.SetNumberEditorUsesMouseWheel( false );
        labelPulseBlack.SetHasCustomTextHoverColor( false );
        labelPulseBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelPulseBlack.SetFont( "Arial", 9, true, false );

        labelTriangleRed = new VoltageLabel( "labelTriangleRed", "TRI Label Red", this, "—————  TRI  —————" );
        AddComponent( labelTriangleRed );
        labelTriangleRed.SetWantsMouseNotifications( false );
        labelTriangleRed.SetPosition( 0, 80 );
        labelTriangleRed.SetSize( 115, 18 );
        labelTriangleRed.SetEditable( false, false );
        labelTriangleRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTriangleRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTriangleRed.SetColor( new Color( 164, 36, 36, 255 ) );
        labelTriangleRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTriangleRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTriangleRed.SetBorderSize( 1 );
        labelTriangleRed.SetMultiLineEdit( false );
        labelTriangleRed.SetIsNumberEditor( false );
        labelTriangleRed.SetNumberEditorRange( 0, 100 );
        labelTriangleRed.SetNumberEditorInterval( 1 );
        labelTriangleRed.SetNumberEditorUsesMouseWheel( false );
        labelTriangleRed.SetHasCustomTextHoverColor( false );
        labelTriangleRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTriangleRed.SetFont( "Arial", 9, true, false );

        labelTriangleBlack = new VoltageLabel( "labelTriangleBlack", "TRI Label Black", this, "TRI" );
        AddComponent( labelTriangleBlack );
        labelTriangleBlack.SetWantsMouseNotifications( false );
        labelTriangleBlack.SetPosition( 0, 80 );
        labelTriangleBlack.SetSize( 115, 18 );
        labelTriangleBlack.SetEditable( false, false );
        labelTriangleBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTriangleBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTriangleBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelTriangleBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTriangleBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTriangleBlack.SetBorderSize( 1 );
        labelTriangleBlack.SetMultiLineEdit( false );
        labelTriangleBlack.SetIsNumberEditor( false );
        labelTriangleBlack.SetNumberEditorRange( 0, 100 );
        labelTriangleBlack.SetNumberEditorInterval( 1 );
        labelTriangleBlack.SetNumberEditorUsesMouseWheel( false );
        labelTriangleBlack.SetHasCustomTextHoverColor( false );
        labelTriangleBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTriangleBlack.SetFont( "Arial", 9, true, false );

        labelSineRed = new VoltageLabel( "labelSineRed", "SIN Label Red", this, "—————  SIN  —————" );
        AddComponent( labelSineRed );
        labelSineRed.SetWantsMouseNotifications( false );
        labelSineRed.SetPosition( 0, 30 );
        labelSineRed.SetSize( 115, 18 );
        labelSineRed.SetEditable( false, false );
        labelSineRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSineRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSineRed.SetColor( new Color( 164, 36, 36, 255 ) );
        labelSineRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSineRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSineRed.SetBorderSize( 1 );
        labelSineRed.SetMultiLineEdit( false );
        labelSineRed.SetIsNumberEditor( false );
        labelSineRed.SetNumberEditorRange( 0, 100 );
        labelSineRed.SetNumberEditorInterval( 1 );
        labelSineRed.SetNumberEditorUsesMouseWheel( false );
        labelSineRed.SetHasCustomTextHoverColor( false );
        labelSineRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSineRed.SetFont( "Arial", 9, true, false );

        labelSineBlack = new VoltageLabel( "labelSineBlack", "SIN Label Black", this, "SIN" );
        AddComponent( labelSineBlack );
        labelSineBlack.SetWantsMouseNotifications( false );
        labelSineBlack.SetPosition( 0, 30 );
        labelSineBlack.SetSize( 115, 18 );
        labelSineBlack.SetEditable( false, false );
        labelSineBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSineBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSineBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelSineBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSineBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSineBlack.SetBorderSize( 1 );
        labelSineBlack.SetMultiLineEdit( false );
        labelSineBlack.SetIsNumberEditor( false );
        labelSineBlack.SetNumberEditorRange( 0, 100 );
        labelSineBlack.SetNumberEditorInterval( 1 );
        labelSineBlack.SetNumberEditorUsesMouseWheel( false );
        labelSineBlack.SetHasCustomTextHoverColor( false );
        labelSineBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSineBlack.SetFont( "Arial", 9, true, false );

        labelModeShape = new VoltageLabel( "labelModeShape", "Shape CV Mode Label", this, "SHAPE" );
        AddComponent( labelModeShape );
        labelModeShape.SetWantsMouseNotifications( false );
        labelModeShape.SetPosition( 86, 209 );
        labelModeShape.SetSize( 25, 13 );
        labelModeShape.SetEditable( false, false );
        labelModeShape.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelModeShape.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelModeShape.SetColor( new Color( 26, 26, 26, 255 ) );
        labelModeShape.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelModeShape.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelModeShape.SetBorderSize( 1 );
        labelModeShape.SetMultiLineEdit( false );
        labelModeShape.SetIsNumberEditor( false );
        labelModeShape.SetNumberEditorRange( 0, 100 );
        labelModeShape.SetNumberEditorInterval( 1 );
        labelModeShape.SetNumberEditorUsesMouseWheel( false );
        labelModeShape.SetHasCustomTextHoverColor( false );
        labelModeShape.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelModeShape.SetFont( "Arial", 7, true, false );

        labelRangeLFO = new VoltageLabel( "labelRangeLFO", "LFO Range Label", this, "LFO" );
        AddComponent( labelRangeLFO );
        labelRangeLFO.SetWantsMouseNotifications( false );
        labelRangeLFO.SetPosition( 3, 232 );
        labelRangeLFO.SetSize( 29, 13 );
        labelRangeLFO.SetEditable( false, false );
        labelRangeLFO.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRangeLFO.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRangeLFO.SetColor( new Color( 26, 26, 26, 255 ) );
        labelRangeLFO.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRangeLFO.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRangeLFO.SetBorderSize( 1 );
        labelRangeLFO.SetMultiLineEdit( false );
        labelRangeLFO.SetIsNumberEditor( false );
        labelRangeLFO.SetNumberEditorRange( 0, 100 );
        labelRangeLFO.SetNumberEditorInterval( 1 );
        labelRangeLFO.SetNumberEditorUsesMouseWheel( false );
        labelRangeLFO.SetHasCustomTextHoverColor( false );
        labelRangeLFO.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRangeLFO.SetFont( "Arial", 7, true, false );

        labelModeLevel = new VoltageLabel( "labelModeLevel", "Level CV Mode Label", this, "LEVEL" );
        AddComponent( labelModeLevel );
        labelModeLevel.SetWantsMouseNotifications( false );
        labelModeLevel.SetPosition( 86, 232 );
        labelModeLevel.SetSize( 25, 13 );
        labelModeLevel.SetEditable( false, false );
        labelModeLevel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelModeLevel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelModeLevel.SetColor( new Color( 26, 26, 26, 255 ) );
        labelModeLevel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelModeLevel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelModeLevel.SetBorderSize( 1 );
        labelModeLevel.SetMultiLineEdit( false );
        labelModeLevel.SetIsNumberEditor( false );
        labelModeLevel.SetNumberEditorRange( 0, 100 );
        labelModeLevel.SetNumberEditorInterval( 1 );
        labelModeLevel.SetNumberEditorUsesMouseWheel( false );
        labelModeLevel.SetHasCustomTextHoverColor( false );
        labelModeLevel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelModeLevel.SetFont( "Arial", 7, true, false );

        labelRangeVCO = new VoltageLabel( "labelRangeVCO", "VCO Range Label", this, "VCO" );
        AddComponent( labelRangeVCO );
        labelRangeVCO.SetWantsMouseNotifications( false );
        labelRangeVCO.SetPosition( 3, 209 );
        labelRangeVCO.SetSize( 29, 13 );
        labelRangeVCO.SetEditable( false, false );
        labelRangeVCO.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRangeVCO.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRangeVCO.SetColor( new Color( 26, 26, 26, 255 ) );
        labelRangeVCO.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRangeVCO.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRangeVCO.SetBorderSize( 1 );
        labelRangeVCO.SetMultiLineEdit( false );
        labelRangeVCO.SetIsNumberEditor( false );
        labelRangeVCO.SetNumberEditorRange( 0, 100 );
        labelRangeVCO.SetNumberEditorInterval( 1 );
        labelRangeVCO.SetNumberEditorUsesMouseWheel( false );
        labelRangeVCO.SetHasCustomTextHoverColor( false );
        labelRangeVCO.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRangeVCO.SetFont( "Arial", 7, true, false );

        labelFooter = new VoltageLabel( "labelFooter", "", this, "primary oscillator" );
        AddComponent( labelFooter );
        labelFooter.SetWantsMouseNotifications( false );
        labelFooter.SetPosition( 3, 335 );
        labelFooter.SetSize( 109, 23 );
        labelFooter.SetEditable( false, false );
        labelFooter.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFooter.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFooter.SetColor( new Color( 166, 36, 36, 255 ) );
        labelFooter.SetBkColor( new Color( 0, 0, 0, 0 ) );
        labelFooter.SetBorderColor( new Color( 26, 26, 26, 255 ) );
        labelFooter.SetBorderSize( 2 );
        labelFooter.SetMultiLineEdit( true );
        labelFooter.SetIsNumberEditor( false );
        labelFooter.SetNumberEditorRange( 0, 100 );
        labelFooter.SetNumberEditorInterval( 1 );
        labelFooter.SetNumberEditorUsesMouseWheel( false );
        labelFooter.SetHasCustomTextHoverColor( false );
        labelFooter.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFooter.SetFont( "Arial", 9, true, false );

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 5 );
        imageTTW.SetSize( 50, 16 );
        imageTTW.SetCurrentImage( "image image(3).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "osc" );
        AddComponent( labelNumber );
        labelNumber.SetWantsMouseNotifications( false );
        labelNumber.SetPosition( 85, 5 );
        labelNumber.SetSize( 25, 13 );
        labelNumber.SetEditable( false, false );
        labelNumber.SetJustificationFlags( VoltageLabel.Justification.Right );
        labelNumber.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNumber.SetColor( new Color( 166, 36, 36, 255 ) );
        labelNumber.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNumber.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNumber.SetBorderSize( 1 );
        labelNumber.SetMultiLineEdit( false );
        labelNumber.SetIsNumberEditor( false );
        labelNumber.SetNumberEditorRange( 0, 100 );
        labelNumber.SetNumberEditorInterval( 1 );
        labelNumber.SetNumberEditorUsesMouseWheel( false );
        labelNumber.SetHasCustomTextHoverColor( false );
        labelNumber.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNumber.SetFont( "Courier New", 13, true, false );

        knobSineLevel = new VoltageKnob( "knobSineLevel", "Sine Level", this, 0.0, 1.0, 0.0 );
        AddComponent( knobSineLevel );
        knobSineLevel.SetWantsMouseNotifications( false );
        knobSineLevel.SetPosition( 75, 57 );
        knobSineLevel.SetSize( 25, 25 );
        knobSineLevel.SetSkin( "ARP2500 Sm Silver" );
        knobSineLevel.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobSineLevel.SetKnobParams( 215, 145 );
        knobSineLevel.DisplayValueInPercent( false );
        knobSineLevel.SetKnobAdjustsRing( true );

        knobTriangleShape = new VoltageKnob( "knobTriangleShape", "Triangle Shape", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTriangleShape );
        knobTriangleShape.SetWantsMouseNotifications( false );
        knobTriangleShape.SetPosition( 15, 105 );
        knobTriangleShape.SetSize( 25, 25 );
        knobTriangleShape.SetSkin( "ARP2500 Sm Emerald" );
        knobTriangleShape.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTriangleShape.SetKnobParams( 215, 145 );
        knobTriangleShape.DisplayValueInPercent( false );
        knobTriangleShape.SetKnobAdjustsRing( true );

        knobTriangleLevel = new VoltageKnob( "knobTriangleLevel", "Triangle Level", this, 0.0, 1.0, 0.0 );
        AddComponent( knobTriangleLevel );
        knobTriangleLevel.SetWantsMouseNotifications( false );
        knobTriangleLevel.SetPosition( 75, 105 );
        knobTriangleLevel.SetSize( 25, 25 );
        knobTriangleLevel.SetSkin( "ARP2500 Sm Silver" );
        knobTriangleLevel.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobTriangleLevel.SetKnobParams( 215, 145 );
        knobTriangleLevel.DisplayValueInPercent( false );
        knobTriangleLevel.SetKnobAdjustsRing( true );

        knobPulseShape = new VoltageKnob( "knobPulseShape", "Square Shape", this, -1.0, 1.0, 0.0 );
        AddComponent( knobPulseShape );
        knobPulseShape.SetWantsMouseNotifications( false );
        knobPulseShape.SetPosition( 15, 154 );
        knobPulseShape.SetSize( 25, 25 );
        knobPulseShape.SetSkin( "ARP2500 Sm Emerald" );
        knobPulseShape.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobPulseShape.SetKnobParams( 215, 145 );
        knobPulseShape.DisplayValueInPercent( false );
        knobPulseShape.SetKnobAdjustsRing( true );

        knobPulseLevel = new VoltageKnob( "knobPulseLevel", "Square Level", this, 0.0, 1.0, 0.0 );
        AddComponent( knobPulseLevel );
        knobPulseLevel.SetWantsMouseNotifications( false );
        knobPulseLevel.SetPosition( 75, 154 );
        knobPulseLevel.SetSize( 25, 25 );
        knobPulseLevel.SetSkin( "ARP2500 Sm Silver" );
        knobPulseLevel.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobPulseLevel.SetKnobParams( 215, 145 );
        knobPulseLevel.DisplayValueInPercent( false );
        knobPulseLevel.SetKnobAdjustsRing( true );

        knobFrequency = new VoltageKnob( "knobFrequency", "Frequency", this, 0.0, 1.0, 0.5789337516261317 );
        AddComponent( knobFrequency );
        knobFrequency.SetWantsMouseNotifications( false );
        knobFrequency.SetPosition( 35, 201 );
        knobFrequency.SetSize( 45, 45 );
        knobFrequency.SetSkin( "ARP2500 Sm Blue" );
        knobFrequency.SetRange( 0.0, 1.0, 0.5789337516261317, false, 0 );
        knobFrequency.SetKnobParams( 215, 145 );
        knobFrequency.DisplayValueInPercent( false );
        knobFrequency.SetKnobAdjustsRing( true );

        inputLinearFm = new VoltageAudioJack( "inputLinearFm", "Frequency Control", this, JackType.JackType_AudioInput );
        AddComponent( inputLinearFm );
        inputLinearFm.SetWantsMouseNotifications( false );
        inputLinearFm.SetPosition( 45, 260 );
        inputLinearFm.SetSize( 25, 25 );
        inputLinearFm.SetSkin( "Jack Round 25px" );

        inputPitch = new VoltageAudioJack( "inputPitch", "Volt Per Octave Input", this, JackType.JackType_AudioInput );
        AddComponent( inputPitch );
        inputPitch.SetWantsMouseNotifications( false );
        inputPitch.SetPosition( 45, 300 );
        inputPitch.SetSize( 25, 25 );
        inputPitch.SetSkin( "Jack Round 25px" );

        outputSine = new VoltageAudioJack( "outputSine", "Sine Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputSine );
        outputSine.SetWantsMouseNotifications( false );
        outputSine.SetPosition( 15, 260 );
        outputSine.SetSize( 25, 25 );
        outputSine.SetSkin( "Mini Jack 25px" );

        outputTriangle = new VoltageAudioJack( "outputTriangle", "Morphing Triangle Out", this, JackType.JackType_AudioOutput );
        AddComponent( outputTriangle );
        outputTriangle.SetWantsMouseNotifications( false );
        outputTriangle.SetPosition( 15, 300 );
        outputTriangle.SetSize( 25, 25 );
        outputTriangle.SetSkin( "Mini Jack 25px" );

        outputPulse = new VoltageAudioJack( "outputPulse", "Pulse Wave Out", this, JackType.JackType_AudioOutput );
        AddComponent( outputPulse );
        outputPulse.SetWantsMouseNotifications( false );
        outputPulse.SetPosition( 75, 260 );
        outputPulse.SetSize( 25, 25 );
        outputPulse.SetSkin( "Mini Jack 25px" );

        outputMix = new VoltageAudioJack( "outputMix", "Wave Mix Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputMix );
        outputMix.SetWantsMouseNotifications( false );
        outputMix.SetPosition( 75, 300 );
        outputMix.SetSize( 25, 25 );
        outputMix.SetSkin( "Mini Jack 25px" );

        labelLevelSine = new VoltageLabel( "labelLevelSine", "SIN Level Label", this, "LEVEL" );
        AddComponent( labelLevelSine );
        labelLevelSine.SetWantsMouseNotifications( false );
        labelLevelSine.SetPosition( 73, 45 );
        labelLevelSine.SetSize( 29, 13 );
        labelLevelSine.SetEditable( false, false );
        labelLevelSine.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLevelSine.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLevelSine.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLevelSine.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLevelSine.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLevelSine.SetBorderSize( 1 );
        labelLevelSine.SetMultiLineEdit( false );
        labelLevelSine.SetIsNumberEditor( false );
        labelLevelSine.SetNumberEditorRange( 0, 100 );
        labelLevelSine.SetNumberEditorInterval( 1 );
        labelLevelSine.SetNumberEditorUsesMouseWheel( false );
        labelLevelSine.SetHasCustomTextHoverColor( false );
        labelLevelSine.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLevelSine.SetFont( "Arial", 7, true, false );

        labelShapeTriangle = new VoltageLabel( "labelShapeTriangle", "TRI Shape Label", this, "SHAPE" );
        AddComponent( labelShapeTriangle );
        labelShapeTriangle.SetWantsMouseNotifications( false );
        labelShapeTriangle.SetPosition( 13, 93 );
        labelShapeTriangle.SetSize( 29, 13 );
        labelShapeTriangle.SetEditable( false, false );
        labelShapeTriangle.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelShapeTriangle.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelShapeTriangle.SetColor( new Color( 26, 26, 26, 255 ) );
        labelShapeTriangle.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelShapeTriangle.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelShapeTriangle.SetBorderSize( 1 );
        labelShapeTriangle.SetMultiLineEdit( false );
        labelShapeTriangle.SetIsNumberEditor( false );
        labelShapeTriangle.SetNumberEditorRange( 0, 100 );
        labelShapeTriangle.SetNumberEditorInterval( 1 );
        labelShapeTriangle.SetNumberEditorUsesMouseWheel( false );
        labelShapeTriangle.SetHasCustomTextHoverColor( false );
        labelShapeTriangle.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelShapeTriangle.SetFont( "Arial", 7, true, false );

        labelLevelTriangle = new VoltageLabel( "labelLevelTriangle", "TRI Level Label", this, "LEVEL" );
        AddComponent( labelLevelTriangle );
        labelLevelTriangle.SetWantsMouseNotifications( false );
        labelLevelTriangle.SetPosition( 73, 93 );
        labelLevelTriangle.SetSize( 29, 13 );
        labelLevelTriangle.SetEditable( false, false );
        labelLevelTriangle.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLevelTriangle.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLevelTriangle.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLevelTriangle.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLevelTriangle.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLevelTriangle.SetBorderSize( 1 );
        labelLevelTriangle.SetMultiLineEdit( false );
        labelLevelTriangle.SetIsNumberEditor( false );
        labelLevelTriangle.SetNumberEditorRange( 0, 100 );
        labelLevelTriangle.SetNumberEditorInterval( 1 );
        labelLevelTriangle.SetNumberEditorUsesMouseWheel( false );
        labelLevelTriangle.SetHasCustomTextHoverColor( false );
        labelLevelTriangle.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLevelTriangle.SetFont( "Arial", 7, true, false );

        labelLocation_4 = new VoltageLabel( "labelLocation_4", "Location Mark_4", this, "SHAPE" );
        AddComponent( labelLocation_4 );
        labelLocation_4.SetWantsMouseNotifications( false );
        labelLocation_4.SetPosition( 13, 142 );
        labelLocation_4.SetSize( 29, 13 );
        labelLocation_4.SetEditable( false, false );
        labelLocation_4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_4.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_4.SetBorderSize( 1 );
        labelLocation_4.SetMultiLineEdit( false );
        labelLocation_4.SetIsNumberEditor( false );
        labelLocation_4.SetNumberEditorRange( 0, 100 );
        labelLocation_4.SetNumberEditorInterval( 1 );
        labelLocation_4.SetNumberEditorUsesMouseWheel( false );
        labelLocation_4.SetHasCustomTextHoverColor( false );
        labelLocation_4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_4.SetFont( "Arial", 7, true, false );

        labelLevelPulse = new VoltageLabel( "labelLevelPulse", "SQR Level Label", this, "LEVEL" );
        AddComponent( labelLevelPulse );
        labelLevelPulse.SetWantsMouseNotifications( false );
        labelLevelPulse.SetPosition( 73, 142 );
        labelLevelPulse.SetSize( 29, 13 );
        labelLevelPulse.SetEditable( false, false );
        labelLevelPulse.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLevelPulse.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLevelPulse.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLevelPulse.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLevelPulse.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLevelPulse.SetBorderSize( 1 );
        labelLevelPulse.SetMultiLineEdit( false );
        labelLevelPulse.SetIsNumberEditor( false );
        labelLevelPulse.SetNumberEditorRange( 0, 100 );
        labelLevelPulse.SetNumberEditorInterval( 1 );
        labelLevelPulse.SetNumberEditorUsesMouseWheel( false );
        labelLevelPulse.SetHasCustomTextHoverColor( false );
        labelLevelPulse.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLevelPulse.SetFont( "Arial", 7, true, false );

        labelOutputTriangle = new VoltageLabel( "labelOutputTriangle", "TRI Output Label", this, "TRI" );
        AddComponent( labelOutputTriangle );
        labelOutputTriangle.SetWantsMouseNotifications( false );
        labelOutputTriangle.SetPosition( 13, 290 );
        labelOutputTriangle.SetSize( 29, 13 );
        labelOutputTriangle.SetEditable( false, false );
        labelOutputTriangle.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOutputTriangle.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOutputTriangle.SetColor( new Color( 26, 26, 26, 255 ) );
        labelOutputTriangle.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOutputTriangle.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOutputTriangle.SetBorderSize( 1 );
        labelOutputTriangle.SetMultiLineEdit( false );
        labelOutputTriangle.SetIsNumberEditor( false );
        labelOutputTriangle.SetNumberEditorRange( 0, 100 );
        labelOutputTriangle.SetNumberEditorInterval( 1 );
        labelOutputTriangle.SetNumberEditorUsesMouseWheel( false );
        labelOutputTriangle.SetHasCustomTextHoverColor( false );
        labelOutputTriangle.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOutputTriangle.SetFont( "Arial", 7, true, false );

        labelOutputSine = new VoltageLabel( "labelOutputSine", "SIN Output Label", this, "SIN" );
        AddComponent( labelOutputSine );
        labelOutputSine.SetWantsMouseNotifications( false );
        labelOutputSine.SetPosition( 13, 250 );
        labelOutputSine.SetSize( 29, 13 );
        labelOutputSine.SetEditable( false, false );
        labelOutputSine.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOutputSine.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOutputSine.SetColor( new Color( 26, 26, 26, 255 ) );
        labelOutputSine.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOutputSine.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOutputSine.SetBorderSize( 1 );
        labelOutputSine.SetMultiLineEdit( false );
        labelOutputSine.SetIsNumberEditor( false );
        labelOutputSine.SetNumberEditorRange( 0, 100 );
        labelOutputSine.SetNumberEditorInterval( 1 );
        labelOutputSine.SetNumberEditorUsesMouseWheel( false );
        labelOutputSine.SetHasCustomTextHoverColor( false );
        labelOutputSine.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOutputSine.SetFont( "Arial", 7, true, false );

        labelOutputPulse = new VoltageLabel( "labelOutputPulse", "SQR Output Label", this, "SQR" );
        AddComponent( labelOutputPulse );
        labelOutputPulse.SetWantsMouseNotifications( false );
        labelOutputPulse.SetPosition( 73, 250 );
        labelOutputPulse.SetSize( 29, 13 );
        labelOutputPulse.SetEditable( false, false );
        labelOutputPulse.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOutputPulse.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOutputPulse.SetColor( new Color( 26, 26, 26, 255 ) );
        labelOutputPulse.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOutputPulse.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOutputPulse.SetBorderSize( 1 );
        labelOutputPulse.SetMultiLineEdit( false );
        labelOutputPulse.SetIsNumberEditor( false );
        labelOutputPulse.SetNumberEditorRange( 0, 100 );
        labelOutputPulse.SetNumberEditorInterval( 1 );
        labelOutputPulse.SetNumberEditorUsesMouseWheel( false );
        labelOutputPulse.SetHasCustomTextHoverColor( false );
        labelOutputPulse.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOutputPulse.SetFont( "Arial", 7, true, false );

        labelOutputMix = new VoltageLabel( "labelOutputMix", "MIX Output Label", this, "MIX" );
        AddComponent( labelOutputMix );
        labelOutputMix.SetWantsMouseNotifications( false );
        labelOutputMix.SetPosition( 73, 290 );
        labelOutputMix.SetSize( 29, 13 );
        labelOutputMix.SetEditable( false, false );
        labelOutputMix.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOutputMix.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOutputMix.SetColor( new Color( 26, 26, 26, 255 ) );
        labelOutputMix.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOutputMix.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOutputMix.SetBorderSize( 1 );
        labelOutputMix.SetMultiLineEdit( false );
        labelOutputMix.SetIsNumberEditor( false );
        labelOutputMix.SetNumberEditorRange( 0, 100 );
        labelOutputMix.SetNumberEditorInterval( 1 );
        labelOutputMix.SetNumberEditorUsesMouseWheel( false );
        labelOutputMix.SetHasCustomTextHoverColor( false );
        labelOutputMix.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOutputMix.SetFont( "Arial", 7, true, false );

        labelFm = new VoltageLabel( "labelFm", "Frequency Control Label", this, "FM" );
        AddComponent( labelFm );
        labelFm.SetWantsMouseNotifications( false );
        labelFm.SetPosition( 42, 250 );
        labelFm.SetSize( 29, 13 );
        labelFm.SetEditable( false, false );
        labelFm.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFm.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFm.SetColor( new Color( 26, 26, 26, 255 ) );
        labelFm.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFm.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFm.SetBorderSize( 1 );
        labelFm.SetMultiLineEdit( false );
        labelFm.SetIsNumberEditor( false );
        labelFm.SetNumberEditorRange( 0, 100 );
        labelFm.SetNumberEditorInterval( 1 );
        labelFm.SetNumberEditorUsesMouseWheel( false );
        labelFm.SetHasCustomTextHoverColor( false );
        labelFm.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFm.SetFont( "Arial", 7, true, false );

        labelPitch = new VoltageLabel( "labelPitch", "Volt Per Octave Label", this, "V/o" );
        AddComponent( labelPitch );
        labelPitch.SetWantsMouseNotifications( false );
        labelPitch.SetPosition( 42, 290 );
        labelPitch.SetSize( 29, 13 );
        labelPitch.SetEditable( false, false );
        labelPitch.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelPitch.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelPitch.SetColor( new Color( 26, 26, 26, 255 ) );
        labelPitch.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelPitch.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelPitch.SetBorderSize( 1 );
        labelPitch.SetMultiLineEdit( false );
        labelPitch.SetIsNumberEditor( false );
        labelPitch.SetNumberEditorRange( 0, 100 );
        labelPitch.SetNumberEditorInterval( 1 );
        labelPitch.SetNumberEditorUsesMouseWheel( false );
        labelPitch.SetHasCustomTextHoverColor( false );
        labelPitch.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelPitch.SetFont( "Arial", 7, true, false );

        labelFreqRed = new VoltageLabel( "labelFreqRed", "Red Frequency Lablel", this, "–—  FREQUENCY  —–" );
        AddComponent( labelFreqRed );
        labelFreqRed.SetWantsMouseNotifications( false );
        labelFreqRed.SetPosition( 0, 185 );
        labelFreqRed.SetSize( 115, 18 );
        labelFreqRed.SetEditable( false, false );
        labelFreqRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFreqRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFreqRed.SetColor( new Color( 164, 36, 36, 255 ) );
        labelFreqRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFreqRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFreqRed.SetBorderSize( 1 );
        labelFreqRed.SetMultiLineEdit( false );
        labelFreqRed.SetIsNumberEditor( false );
        labelFreqRed.SetNumberEditorRange( 0, 100 );
        labelFreqRed.SetNumberEditorInterval( 1 );
        labelFreqRed.SetNumberEditorUsesMouseWheel( false );
        labelFreqRed.SetHasCustomTextHoverColor( false );
        labelFreqRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFreqRed.SetFont( "Arial", 11, true, false );

        inputSineCv = new VoltageAudioJack( "inputSineCv", "SIN CV Input", this, JackType.JackType_AudioInput );
        AddComponent( inputSineCv );
        inputSineCv.SetWantsMouseNotifications( false );
        inputSineCv.SetPosition( 45, 57 );
        inputSineCv.SetSize( 25, 25 );
        inputSineCv.SetSkin( "Jack Round 25px" );

        inputTriangleCv = new VoltageAudioJack( "inputTriangleCv", "TRI CV Input", this, JackType.JackType_AudioInput );
        AddComponent( inputTriangleCv );
        inputTriangleCv.SetWantsMouseNotifications( false );
        inputTriangleCv.SetPosition( 45, 104 );
        inputTriangleCv.SetSize( 25, 25 );
        inputTriangleCv.SetSkin( "Jack Round 25px" );

        inputPulseCv = new VoltageAudioJack( "inputPulseCv", "SQR CV Input", this, JackType.JackType_AudioInput );
        AddComponent( inputPulseCv );
        inputPulseCv.SetWantsMouseNotifications( false );
        inputPulseCv.SetPosition( 45, 154 );
        inputPulseCv.SetSize( 25, 25 );
        inputPulseCv.SetSkin( "Jack Round 25px" );

        labelSineCv = new VoltageLabel( "labelSineCv", "SIN CV Label", this, "CV" );
        AddComponent( labelSineCv );
        labelSineCv.SetWantsMouseNotifications( false );
        labelSineCv.SetPosition( 43, 45 );
        labelSineCv.SetSize( 29, 13 );
        labelSineCv.SetEditable( false, false );
        labelSineCv.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSineCv.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSineCv.SetColor( new Color( 26, 26, 26, 255 ) );
        labelSineCv.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSineCv.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSineCv.SetBorderSize( 1 );
        labelSineCv.SetMultiLineEdit( false );
        labelSineCv.SetIsNumberEditor( false );
        labelSineCv.SetNumberEditorRange( 0, 100 );
        labelSineCv.SetNumberEditorInterval( 1 );
        labelSineCv.SetNumberEditorUsesMouseWheel( false );
        labelSineCv.SetHasCustomTextHoverColor( false );
        labelSineCv.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSineCv.SetFont( "Arial", 9, true, false );

        labelTriangleCv = new VoltageLabel( "labelTriangleCv", "TRI CV Label", this, "CV" );
        AddComponent( labelTriangleCv );
        labelTriangleCv.SetWantsMouseNotifications( false );
        labelTriangleCv.SetPosition( 43, 93 );
        labelTriangleCv.SetSize( 29, 13 );
        labelTriangleCv.SetEditable( false, false );
        labelTriangleCv.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTriangleCv.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTriangleCv.SetColor( new Color( 26, 26, 26, 255 ) );
        labelTriangleCv.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTriangleCv.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTriangleCv.SetBorderSize( 1 );
        labelTriangleCv.SetMultiLineEdit( false );
        labelTriangleCv.SetIsNumberEditor( false );
        labelTriangleCv.SetNumberEditorRange( 0, 100 );
        labelTriangleCv.SetNumberEditorInterval( 1 );
        labelTriangleCv.SetNumberEditorUsesMouseWheel( false );
        labelTriangleCv.SetHasCustomTextHoverColor( false );
        labelTriangleCv.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTriangleCv.SetFont( "Arial", 9, true, false );

        labelPulseCv = new VoltageLabel( "labelPulseCv", "SQR CV Label", this, "CV" );
        AddComponent( labelPulseCv );
        labelPulseCv.SetWantsMouseNotifications( false );
        labelPulseCv.SetPosition( 43, 142 );
        labelPulseCv.SetSize( 29, 13 );
        labelPulseCv.SetEditable( false, false );
        labelPulseCv.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelPulseCv.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelPulseCv.SetColor( new Color( 26, 26, 26, 255 ) );
        labelPulseCv.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelPulseCv.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelPulseCv.SetBorderSize( 1 );
        labelPulseCv.SetMultiLineEdit( false );
        labelPulseCv.SetIsNumberEditor( false );
        labelPulseCv.SetNumberEditorRange( 0, 100 );
        labelPulseCv.SetNumberEditorInterval( 1 );
        labelPulseCv.SetNumberEditorUsesMouseWheel( false );
        labelPulseCv.SetHasCustomTextHoverColor( false );
        labelPulseCv.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelPulseCv.SetFont( "Arial", 9, true, false );

        switchCvDestination = new VoltageSwitch( "switchCvDestination", "CV Destination", this, 0 );
        AddComponent( switchCvDestination );
        switchCvDestination.SetWantsMouseNotifications( false );
        switchCvDestination.SetPosition( 85, 215 );
        switchCvDestination.SetSize( 25, 25 );
        switchCvDestination.SetSkin( "3-State Red Cap" );

        switchRange = new VoltageSwitch( "switchRange", "Frequency Range", this, 1 );
        AddComponent( switchRange );
        switchRange.SetWantsMouseNotifications( false );
        switchRange.SetPosition( 5, 215 );
        switchRange.SetSize( 25, 25 );
        switchRange.SetSkin( "2-State Red Cap" );

        knobSineShape = new VoltageKnob( "knobSineShape", "Sine Shape", this, -1.0, 1.0, 0.0 );
        AddComponent( knobSineShape );
        knobSineShape.SetWantsMouseNotifications( false );
        knobSineShape.SetPosition( 15, 57 );
        knobSineShape.SetSize( 25, 25 );
        knobSineShape.SetSkin( "ARP2500 Sm Emerald" );
        knobSineShape.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobSineShape.SetKnobParams( 215, 145 );
        knobSineShape.DisplayValueInPercent( false );
        knobSineShape.SetKnobAdjustsRing( true );

        labelShape = new VoltageLabel( "labelShape", "Shape Label", this, "SHAPE" );
        AddComponent( labelShape );
        labelShape.SetWantsMouseNotifications( false );
        labelShape.SetPosition( 13, 45 );
        labelShape.SetSize( 29, 13 );
        labelShape.SetEditable( false, false );
        labelShape.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelShape.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelShape.SetColor( new Color( 26, 26, 26, 255 ) );
        labelShape.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelShape.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelShape.SetBorderSize( 1 );
        labelShape.SetMultiLineEdit( false );
        labelShape.SetIsNumberEditor( false );
        labelShape.SetNumberEditorRange( 0, 100 );
        labelShape.SetNumberEditorInterval( 1 );
        labelShape.SetNumberEditorUsesMouseWheel( false );
        labelShape.SetHasCustomTextHoverColor( false );
        labelShape.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelShape.SetFont( "Arial", 7, true, false );

        labelFreqBlack = new VoltageLabel( "labelFreqBlack", "Black Frequency Lablel", this, "FREQUENCY" );
        AddComponent( labelFreqBlack );
        labelFreqBlack.SetWantsMouseNotifications( false );
        labelFreqBlack.SetPosition( 0, 185 );
        labelFreqBlack.SetSize( 115, 18 );
        labelFreqBlack.SetEditable( false, false );
        labelFreqBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFreqBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFreqBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelFreqBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFreqBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFreqBlack.SetBorderSize( 1 );
        labelFreqBlack.SetMultiLineEdit( false );
        labelFreqBlack.SetIsNumberEditor( false );
        labelFreqBlack.SetNumberEditorRange( 0, 100 );
        labelFreqBlack.SetNumberEditorInterval( 1 );
        labelFreqBlack.SetNumberEditorUsesMouseWheel( false );
        labelFreqBlack.SetHasCustomTextHoverColor( false );
        labelFreqBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFreqBlack.SetFont( "Arial", 11, true, false );
}



    //-------------------------------------------------------------------------------
    //  public void Initialize()

        //  Initialize will get called shortly after your module's constructor runs. You can use it to
        //  do any initialization that the auto-generated code doesn't handle.
    //-------------------------------------------------------------------------------
    @Override
    public void Initialize()
    {
        //[user-Initialize]   Add your own initialization code here
        driftRandom = System.nanoTime() ^ ((long) System.identityHashCode(this) << 32);
        if (driftRandom == 0) driftRandom = 1;
        adoptPending.set(true);
        //[/user-Initialize]
    }


    //-------------------------------------------------------------------------------
    //  public void Destroy()

        //  Destroy will get called just before your module gets deleted. You can use it to perform any
        //  cleanup that's not handled automatically by Java.
    //-------------------------------------------------------------------------------
    @Override
    public void Destroy()
    {
        super.Destroy();
        //[user-Destroy]   Add your own module-getting-deleted code here



        //[/user-Destroy]
    }


    //-------------------------------------------------------------------------------
    //  public boolean Notify( VoltageComponent component, ModuleNotifications notification, double doubleValue, long longValue, int x, int y, Object object )

        //  Notify will get called when various events occur - control values changing, timers firing, etc.
    //-------------------------------------------------------------------------------
    @Override
    public boolean Notify( VoltageComponent component, ModuleNotifications notification, double doubleValue, long longValue, int x, int y, Object object )
    {
        //[user-Notify]   Add your own notification handling code between this line and the notify-close comment
        switch (notification) {
            case Reset:
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                adoptPending.set(true);
                break;
            default: break;
        }
        return false;
        //[/user-Notify]
    }


    //-------------------------------------------------------------------------------
    //  public void ProcessSample()

        //  ProcessSample is called once per sample. Usually it's where you read
        //  from input jacks, process audio, and write it to your output jacks.
        //  Since ProcesssSample gets called 48,000 times per second, offload CPU-intensive operations
        //  to other threads when possible and avoid calling native functions.
    //-------------------------------------------------------------------------------
    @Override
    public void ProcessSample()
    {
        //[user-ProcessSample]   Add your own process-sampling code here
        renderSample();
        //[/user-ProcessSample]
    }


    //-------------------------------------------------------------------------------
    //  public void ProcessBypassedSample()

        //  ProcessBypassedSample gets called instead of ProcessSample when you've checked the "Can Be Bypassed" box
        //  and a user has bypassed your module. If your module processes data from input jacks and then sends it to
        //  output jack(s), make ProcessBypassedSample just send the data from the input jacks to the output jacks without
        //  processing it.
    //-------------------------------------------------------------------------------
    @Override
    public void ProcessBypassedSample()
    {
        //[user-ProcessBypassedSample]   Add your own code here
        outputSine.SetValue(0);
        outputTriangle.SetValue(0);
        outputPulse.SetValue(0);
        outputMix.SetValue(0);
        resumePending = true;
        //[/user-ProcessBypassedSample]
    }


    //-------------------------------------------------------------------------------
    //  public String GetTooltipText( VoltageComponent component )

        //  Gets called when a tooltip is about to display for a control. Override it if
        //  you want to change what the tooltip displays - if you want a knob to work in logarithmic fashion,
        //  for instance, you can translate the knob's current value to a log-based string and display it here.
    //-------------------------------------------------------------------------------
    @Override
    public String GetTooltipText( VoltageComponent component )
    {
        //[user-GetTooltipText]   Add your own code here
        if (component == knobFrequency) return String.format(java.util.Locale.ROOT,
                "Frequency: %.4f Hz (separate 1 V/oct pitch)", frequencyFor(knobFrequency.GetValue(), state(switchRange.GetValue(), 1, 1)));
        if (component == knobSineLevel || component == knobTriangleLevel || component == knobPulseLevel)
            return String.format(java.util.Locale.ROOT, "Level: %.1f %%", 100.0 * safe(component.GetValue(), 0, 1, 0));
        if (component == knobPulseShape) return String.format(java.util.Locale.ROOT,
                "Pulse width: %.1f %%", 50.0 + 49.0 * safe(knobPulseShape.GetValue(), -1, 1, 0));
        if (component == knobSineShape || component == knobTriangleShape)
            return String.format(java.util.Locale.ROOT, "Shape: %+.1f %%", 100.0 * safe(component.GetValue(), -1, 1, 0));
        if (component == switchRange) return state(switchRange.GetValue(), 1, 1) == 1 ? "VCO: 1 Hz - 15 kHz" : "LFO: 0.05 - 500 Hz";
        if (component == switchCvDestination) return new String[]{"CV: LEVEL", "CV: SHAPE + LEVEL", "CV: SHAPE"}[state(switchCvDestination.GetValue(), 2, 0)];
        if (component == inputPitch) return "Pitch: +1 V raises one octave";
        if (component == inputLinearFm) return "Through-zero linear FM: +/-5 V adds +/-100% of tuned frequency; -5 V stops, below -5 V reverses";
        if (component == inputSineCv || component == inputTriangleCv || component == inputPulseCv)
            return "CV: +5 V adds one full shape/level sweep; destination selected by switch";
        if (component == outputSine) return "Sine output: follows Sine Level; shape can change level and DC";
        if (component == outputTriangle) return "Triangle/ramp output: follows Triangle Level";
        if (component == outputPulse) return "Pulse output: follows Pulse Level; duty changes DC and AC level";
        if (component == outputMix) return "Mix: direct sum of the three level-controlled outputs; no normalization";
        return super.GetTooltipText(component);
        //[/user-GetTooltipText]
    }


    //-------------------------------------------------------------------------------
    //  public void EditComponentValue( VoltageComponent component, double newValue, String newText )

        //  Gets called after a user clicks on a tooltip and types in a new value for a control. Override this if
        //  you've changed the default tooltip display (translating a linear value to logarithmic, for instance)
        //  in GetTooltipText().
    //-------------------------------------------------------------------------------
    @Override
    public void EditComponentValue( VoltageComponent component, double newValue, String newText )
    {
        //[user-EditComponentValue]   Add your own code here
        if (component == knobFrequency) {
            int range = state(switchRange.GetValue(), 1, 1);
            double hz = safe(newValue, range == 1 ? 1 : 0.05, range == 1 ? 15000 : 500,
                    frequencyFor(knobFrequency.GetValue(), range));
            double value = range == 1 ? Math.log(hz) / AUDIO_LOG_SPAN : Math.log(hz / 0.05) / Math.log(10000.0);
            super.EditComponentValue(component, value, newText); return;
        }
        if (component == knobSineLevel || component == knobTriangleLevel || component == knobPulseLevel) {
            super.EditComponentValue(component, safe(newValue / 100.0, 0, 1, 0), newText); return;
        }
        if (component == knobPulseShape) {
            super.EditComponentValue(component, safe((newValue - 50.0) / 49.0, -1, 1, 0), newText); return;
        }
        if (component == knobSineShape || component == knobTriangleShape) {
            super.EditComponentValue(component, safe(newValue / 100.0, -1, 1, 0), newText); return;
        }
        //[/user-EditComponentValue]
        super.EditComponentValue( component, newValue, newText );
    }


    //-------------------------------------------------------------------------------
    //  public void OnUndoRedo( String undoType, double newValue, Object optionalObject )

        //  If you've created custom undo events via calls to CreateUndoEvent, you'll need to
        //  process them in this function when they get triggered by undo/redo actions.
    //-------------------------------------------------------------------------------
    @Override
    public void OnUndoRedo( String undoType, double newValue, Object optionalObject )
    {
        //[user-OnUndoRedo]   Add your own code here



        //[/user-OnUndoRedo]
    }


    //-------------------------------------------------------------------------------
    //  public byte[] GetStateInformation()

        //  Gets called when the module's state gets saved, typically when the user saves a preset with
        //  this module in it. Voltage Modular will automatically save the states of knobs, sliders, etc.,
        //  but if you have any custom state information you need to save, return it from this function.
    //-------------------------------------------------------------------------------
    @Override
    public byte[] GetStateInformation()
    {
        //[user-GetStateInformation]   Add your own code here

        return null;

        //[/user-GetStateInformation]
    }


    //-------------------------------------------------------------------------------
    //  public void SetStateInformation(byte[] stateInfo)

        //  Gets called when this module's state is getting restored, typically when a user opens a preset with
        //  this module in it. The stateInfo parameter will contain whatever custom data you stored in GetStateInformation().
    //-------------------------------------------------------------------------------
    @Override
    public void SetStateInformation(byte[] stateInfo)
    {
        //[user-SetStateInformation]   Add your own code here
        adoptPending.set(true);
        //[/user-SetStateInformation]
    }


    //-------------------------------------------------------------------------------
    //  public byte[] GetStateInformationForVariations()

        //  Gets called when a user saves a variation with this module in it.
        //  Voltage Modular will automatically save the states of knobs, sliders, etc.,
        //  but if you have any custom state information you need to save, return it from this function.
    //-------------------------------------------------------------------------------
    @Override
    public byte[] GetStateInformationForVariations()
    {
        //[user-GetStateInformationForVariations]   Add your own code here

        return null;

        //[/user-GetStateInformationForVariations]
    }


    //-------------------------------------------------------------------------------
    //  public void SetStateInformationForVariations(byte[] stateInfo)

        //  Gets called when a user loads a variation with this module in it.
        //  The stateInfo parameter will contain whatever custom data you stored in GetStateInformationForVariations().
    //-------------------------------------------------------------------------------
    @Override
    public void SetStateInformationForVariations(byte[] stateInfo)
    {
        //[user-SetStateInformationForVariations]   Add your own code here
        SetStateInformation(stateInfo);



        //[/user-SetStateInformationForVariations]
    }


    // Auto-generated variables
    private VoltageLabel labelFreqBlack;
    private VoltageLabel labelShape;
    private VoltageKnob knobSineShape;
    private VoltageSwitch switchRange;
    private VoltageSwitch switchCvDestination;
    private VoltageLabel labelPulseCv;
    private VoltageLabel labelTriangleCv;
    private VoltageLabel labelSineCv;
    private VoltageAudioJack inputPulseCv;
    private VoltageAudioJack inputTriangleCv;
    private VoltageAudioJack inputSineCv;
    private VoltageLabel labelFreqRed;
    private VoltageLabel labelPitch;
    private VoltageLabel labelFm;
    private VoltageLabel labelOutputMix;
    private VoltageLabel labelOutputPulse;
    private VoltageLabel labelOutputSine;
    private VoltageLabel labelOutputTriangle;
    private VoltageLabel labelLevelPulse;
    private VoltageLabel labelLocation_4;
    private VoltageLabel labelLevelTriangle;
    private VoltageLabel labelShapeTriangle;
    private VoltageLabel labelLevelSine;
    private VoltageAudioJack outputMix;
    private VoltageAudioJack outputPulse;
    private VoltageAudioJack outputTriangle;
    private VoltageAudioJack outputSine;
    private VoltageAudioJack inputPitch;
    private VoltageAudioJack inputLinearFm;
    private VoltageKnob knobFrequency;
    private VoltageKnob knobPulseLevel;
    private VoltageKnob knobPulseShape;
    private VoltageKnob knobTriangleLevel;
    private VoltageKnob knobTriangleShape;
    private VoltageKnob knobSineLevel;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel labelRangeVCO;
    private VoltageLabel labelModeLevel;
    private VoltageLabel labelRangeLFO;
    private VoltageLabel labelModeShape;
    private VoltageLabel labelSineBlack;
    private VoltageLabel labelSineRed;
    private VoltageLabel labelTriangleBlack;
    private VoltageLabel labelTriangleRed;
    private VoltageLabel labelPulseBlack;
    private VoltageLabel labelPulseRed;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double HOST_RATE = 48000.0;
    private static final int OVERSAMPLING = 4;
    private static final double C4_HZ = 261.6255653005986;
    private static final double AUDIO_LOG_SPAN = Math.log(15000.0);
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.005 * HOST_RATE));
    private static final int TABLE_SIZE = 32768;
    private static final int TABLE_MASK = TABLE_SIZE - 1;
    private static final int TABLE_BANKS = 14;
    private static final int DECIMATOR_LENGTH = 159;
    private static final double[] DECIMATOR_COEFFICIENTS = makeDecimator();
    private static final float[][][] WAVE_TABLES = makeWaveTables();
    private static final float[] SINE_TABLE = makeSineTable();
    private final java.util.concurrent.atomic.AtomicBoolean adoptPending =
            new java.util.concurrent.atomic.AtomicBoolean(true);
    private final double[] shape = new double[3], level = new double[3];
    private final double[] previousCv = new double[3];
    private final UpperDecimator[] upperDecimators = {
        new UpperDecimator(), new UpperDecimator(), new UpperDecimator()
    };
    private final OutputDecimator[] decimators = {
        new OutputDecimator(), new OutputDecimator(), new OutputDecimator()
    };
    private boolean resumePending;
    private double phase, frequencyControl, previousFm, previousPitch;
    private int rangeState = 1, routeState, mipBank;
    private double mipBlend, selectedBandwidthHz = Double.NaN;
    private long driftRandom = 1;
    private int driftCountdown;
    private double driftTarget, driftFirst, driftSecond;
    // Model amounts are voicing choices, not measured EMS constants.
    private double driftDepthCents = 1.2, shapePitchCents = 0.35;

    private static double safe(double value, double low, double high, double fallback) {
        return Double.isFinite(value) ? Math.max(low, Math.min(high, value)) : fallback;
    }

    private static int state(double value, int maximum, int fallback) {
        return (int) Math.round(safe(value, 0.0, maximum, fallback));
    }

    private static double frequencyFor(double value, int range) {
        double x = safe(value, 0.0, 1.0, Math.log(C4_HZ) / AUDIO_LOG_SPAN);
        return range == 1 ? Math.exp(x * AUDIO_LOG_SPAN) : 0.05 * Math.exp(x * Math.log(10000.0));
    }

    private void adoptControls() {
        frequencyControl = safe(knobFrequency.GetValue(), 0, 1, Math.log(C4_HZ) / AUDIO_LOG_SPAN);
        shape[0] = safe(knobSineShape.GetValue(), -1, 1, 0);
        shape[1] = safe(knobTriangleShape.GetValue(), -1, 1, 0);
        shape[2] = safe(knobPulseShape.GetValue(), -1, 1, 0);
        level[0] = safe(knobSineLevel.GetValue(), 0, 1, 0);
        level[1] = safe(knobTriangleLevel.GetValue(), 0, 1, 0);
        level[2] = safe(knobPulseLevel.GetValue(), 0, 1, 0);
        rangeState = state(switchRange.GetValue(), 1, 1);
        routeState = state(switchCvDestination.GetValue(), 2, 0);
        previousFm = safe(inputLinearFm.GetValue(), -20, 20, 0);
        previousPitch = safe(inputPitch.GetValue(), -16, 16, 0);
        previousCv[0] = safe(inputSineCv.GetValue(), -20, 20, 0);
        previousCv[1] = safe(inputTriangleCv.GetValue(), -20, 20, 0);
        previousCv[2] = safe(inputPulseCv.GetValue(), -20, 20, 0);
        for (OutputDecimator decimator : decimators) decimator.reset();
        for (UpperDecimator decimator : upperDecimators) decimator.reset();
        previousTriangleRate = 0;
        previousPulseRate = 0;
        resumePending = false;
    }

    /** Exact mean of exp-interpolated pitch times linearly interpolated FM.
     * Signed frequency preserves phase continuity at zero and in reverse. */
    private static double linearFmMean(double base0, double base1, double m0, double m1) {
        if (base0 == base1) return base0 * (m0 + m1) * 0.5;
        double a = Math.log(base1 / base0);
        double f0, f1;
        if (Math.abs(a) < 1e-4) {
            double a2 = a*a, a3 = a2*a;
            f0 = 1 + a/2 + a2/6 + a3/24;
            f1 = 0.5 + a/3 + a2/8 + a3/30;
        } else {
            double e = Math.exp(a);
            f0 = Math.expm1(a) / a;
            f1 = ((a - 1)*e + 1) / (a*a);
        }
        return base0 * (m0*f0 + (m1-m0)*f1);
    }

    private static double wrap(double x) {
        double wrapped = x - Math.floor(x);
        // A negative sub-ulp remainder can round to 1.0 instead of below it.
        return wrapped >= 1.0 ? 0.0 : wrapped;
    }

    private static double lookup(float[] table, double cycle) {
        double at = wrap(cycle) * TABLE_SIZE;
        int index = (int) at;
        double t = at - index;
        double a = table[(index - 1) & TABLE_MASK], b = table[index];
        double c = table[(index + 1) & TABLE_MASK], d = table[(index + 2) & TABLE_MASK];
        return b + 0.5 * t * (c - a + t * (2*a - 5*b + 4*c - d + t * (3*(b-c) + d-a)));
    }

    private void selectBandwidth(double hz) {
        if (hz == selectedBandwidthHz) return;
        selectedBandwidthHz = hz;
        // Harmonic support stays below 19.2 kHz. Two adjacent conservative
        // banks crossfade continuously; no bank with support above the limit is used.
        double log = Math.log(Math.max(1.0, 0.4 * HOST_RATE / Math.max(1e-9, Math.abs(hz)))) / Math.log(2.0);
        mipBank = Math.min(TABLE_BANKS - 1, (int) log);
        mipBlend = Math.min(1.0, log - mipBank);
        mipBlend = mipBlend * mipBlend * (3.0 - 2.0 * mipBlend);
        if (mipBank == 0) mipBlend = 1.0;
    }

    private double wave(int kind, double cycle) {
        double high = lookup(WAVE_TABLES[kind][mipBank], cycle);
        if (mipBank == 0) return high;
        double low = lookup(WAVE_TABLES[kind][mipBank - 1], cycle);
        return low + mipBlend * (high - low);
    }

    private static double harmonicGain(double hz, int harmonic) {
        double x = safe((0.4 * HOST_RATE / Math.max(1e-9, Math.abs(hz)) - harmonic) / harmonic, 0, 1, 0);
        return x*x*(3.0 - 2.0*x);
    }

    private double sineWave(double cycle, double amount, double hz) {
        double sine = lookup(SINE_TABLE, cycle);
        // Small even/odd residuals model an imperfectly adjusted shaper.
        sine += 0.007 * harmonicGain(hz, 2) * lookup(SINE_TABLE, 2 * cycle)
              + 0.003 * harmonicGain(hz, 3) * lookup(SINE_TABLE, 3 * cycle);
        // At +/-1 retain the positive/negative half, respectively. Band-limited
        // absolute sine preserves its DC component and avoids a sampled hard kink.
        double normalization = (Math.sqrt(amount * amount + 0.04) - 0.2) / (Math.sqrt(1.04) - 0.2);
        return (sine + amount * wave(2, cycle)) / (1.0 + normalization);
    }

    private double triangleWave(double cycle, double amount) {
        double width = 0.5 - 0.49 * amount;
        // Integral of a rising saw has derivative equal to that saw. Its shifted
        // difference produces triangle -> either ramp with band-limited corners.
        return -(wave(1, cycle) - wave(1, cycle - width)) / (width * (1.0 - width));
    }

    private double pulseWave(double cycle, double amount) {
        double width = 0.5 + 0.49 * amount;
        return wave(0, cycle - width) - wave(0, cycle) + 2.0 * width - 1.0;
    }

    private void tickDrift() {
        if (--driftCountdown <= 0) {
            driftRandom ^= driftRandom << 13;
            driftRandom ^= driftRandom >>> 7;
            driftRandom ^= driftRandom << 17;
            driftTarget = ((driftRandom >>> 11) * 0x1.0p-53 * 2.0 - 1.0) * driftDepthCents;
            driftCountdown = 48000;
        }
        driftFirst += (driftTarget - driftFirst) / 96000.0;
        driftSecond += (driftFirst - driftSecond) / 24000.0;
    }

    private void renderSample() {
        if (adoptPending.getAndSet(false) || resumePending) adoptControls();
        int nextRange = state(switchRange.GetValue(), 1, 1);
        int nextRoute = state(switchCvDestination.GetValue(), 2, 0);
        if (nextRange != rangeState) {
            rangeState = nextRange;
            // Preserve the current pitch at a range change, then slew to the new range.
            double oldHz = Math.exp(frequencyLog);
            frequencyControl = rangeState == 1 ? Math.log(Math.max(1, oldHz)) / AUDIO_LOG_SPAN
                    : Math.log(Math.max(0.05, oldHz) / 0.05) / Math.log(10000.0);
        }
        routeState = nextRoute;
        frequencyControl += CONTROL_SMOOTH * (safe(knobFrequency.GetValue(), 0, 1, 0.5) - frequencyControl);
        shape[0] += CONTROL_SMOOTH * (safe(knobSineShape.GetValue(), -1, 1, 0) - shape[0]);
        shape[1] += CONTROL_SMOOTH * (safe(knobTriangleShape.GetValue(), -1, 1, 0) - shape[1]);
        shape[2] += CONTROL_SMOOTH * (safe(knobPulseShape.GetValue(), -1, 1, 0) - shape[2]);
        level[0] += CONTROL_SMOOTH * (safe(knobSineLevel.GetValue(), 0, 1, 0) - level[0]);
        level[1] += CONTROL_SMOOTH * (safe(knobTriangleLevel.GetValue(), 0, 1, 0) - level[1]);
        level[2] += CONTROL_SMOOTH * (safe(knobPulseLevel.GetValue(), 0, 1, 0) - level[2]);
        frequencyLog = Math.log(frequencyFor(frequencyControl, rangeState));
        double fm = safe(inputLinearFm.GetValue(), -20, 20, 0);
        double pitchCv = safe(inputPitch.GetValue(), -16, 16, 0);
        double c0 = safe(inputSineCv.GetValue(), -20, 20, 0);
        double c1 = safe(inputTriangleCv.GetValue(), -20, 20, 0);
        double c2 = safe(inputPulseCv.GetValue(), -20, 20, 0);
        tickDrift();
        double initialShape = safe(shape[0] + (routeState != 0 ? previousCv[0] * 0.4 : 0), -1, 1, 0);
        double previousHz = safe(Math.exp(frequencyLog + Math.log(2.0) *
                (previousPitch + (driftSecond + shapePitchCents * initialShape) / 1200.0)), 0.0001, 18000, C4_HZ);
        double previousTriangleShape = safe(shape[1] + (routeState != 0 ? previousCv[1] * 0.4 : 0), -1, 1, 0);
        double previousPulseShape = safe(shape[2] + (routeState != 0 ? previousCv[2] * 0.4 : 0), -1, 1, 0);
        double triangleEnd = safe(shape[1] + (routeState != 0 ? c1 * 0.4 : 0), -1, 1, 0);
        double pulseEnd = safe(shape[2] + (routeState != 0 ? c2 * 0.4 : 0), -1, 1, 0);
        double triangleRate = 0.49 * Math.abs(triangleEnd - previousTriangleShape) * HOST_RATE;
        double pulseRate = 0.49 * Math.abs(pulseEnd - previousPulseShape) * HOST_RATE;
        double previousMultiplier = 1.0 + previousFm / 5.0;
        for (int sub = 0; sub < OVERSAMPLING; ++sub) {
            double fraction = (sub + 1.0) / OVERSAMPLING;
            double cv0 = previousCv[0] + fraction * (c0 - previousCv[0]);
            double cv1 = previousCv[1] + fraction * (c1 - previousCv[1]);
            double cv2 = previousCv[2] + fraction * (c2 - previousCv[2]);
            boolean shapeCv = routeState != 0, levelCv = routeState != 2;
            double s0 = safe(shape[0] + (shapeCv ? cv0 * 0.4 : 0), -1, 1, 0);
            double s1 = safe(shape[1] + (shapeCv ? cv1 * 0.4 : 0), -1, 1, 0);
            double s2 = safe(shape[2] + (shapeCv ? cv2 * 0.4 : 0), -1, 1, 0);
            double l0 = safe(level[0] + (levelCv ? cv0 * 0.2 : 0), 0, 1, 0);
            double l1 = safe(level[1] + (levelCv ? cv1 * 0.2 : 0), 0, 1, 0);
            double l2 = safe(level[2] + (levelCv ? cv2 * 0.2 : 0), 0, 1, 0);
            double pitch = previousPitch + fraction * (pitchCv - previousPitch);
            double baseHz = safe(Math.exp(frequencyLog + Math.log(2.0) *
                    (pitch + (driftSecond + shapePitchCents * s0) / 1200.0)), 0.0001, 18000, C4_HZ);
            double multiplier = 1.0 + (previousFm + fraction * (fm - previousFm)) / 5.0;
            double meanHz = safe(linearFmMean(previousHz, baseHz, previousMultiplier, multiplier),
                    -18000, 18000, 0);
            phase = wrap(phase + meanHz / (OVERSAMPLING * HOST_RATE));
            previousHz = baseHz;
            previousMultiplier = multiplier;
            double hz = Math.abs(safe(baseHz * multiplier, -18000, 18000, 0));
            selectBandwidth(hz);
            pushOutput(0, l0 == 0 ? 0 : 5.0 * l0 * sineWave(phase, s0, hz), sub);
            // Moving a waveform corner adds phase velocity. Reserve bandwidth
            // for it as well as the master pitch, rather than using pitch alone.
            selectBandwidth(hz + previousTriangleRate + fraction * (triangleRate - previousTriangleRate));
            pushOutput(1, l1 == 0 ? 0 : 5.0 * l1 * triangleWave(phase, s1), sub);
            selectBandwidth(hz + previousPulseRate + fraction * (pulseRate - previousPulseRate));
            pushOutput(2, l2 == 0 ? 0 : 5.0 * l2 * pulseWave(phase, s2), sub);
            previousTriangleShape = s1;
            previousPulseShape = s2;
        }
        previousTriangleRate = triangleRate;
        previousPulseRate = pulseRate;
        previousFm = fm;
        previousPitch = pitchCv;
        previousCv[0] = c0;
        previousCv[1] = c1;
        previousCv[2] = c2;
        double a = decimators[0].output(), b = decimators[1].output(), c = decimators[2].output();
        outputSine.SetValue(a);
        outputTriangle.SetValue(b);
        outputPulse.SetValue(c);
        outputMix.SetValue(a + b + c);
    }

    private double frequencyLog = Math.log(C4_HZ);
    private double previousTriangleRate, previousPulseRate;

    private void pushOutput(int channel, double input, int sub) {
        upperDecimators[channel].push(input);
        if ((sub & 1) != 0) decimators[channel].push(upperDecimators[channel].output());
    }

    private static final double[] UPPER_COEFFICIENTS = makeUpperDecimator();
    private static double[] makeUpperDecimator() {
        double[] coefficients = new double[31];
        double sum = 0;
        for (int i = 0; i < 31; ++i) {
            int x = i - 15;
            coefficients[i] = x == 0 ? 0.5 : (x & 1) == 0 ? 0
                    : Math.sin(0.5*Math.PI*x) / (Math.PI*x)
                    * besselI0(8.6*Math.sqrt(1-(x/15.0)*(x/15.0))) / besselI0(8.6);
            sum += coefficients[i];
        }
        for (int i = 0; i < 31; ++i) coefficients[i] /= sum;
        return coefficients;
    }
    private static final class UpperDecimator {
        private final double[] history = new double[62];
        private int position, nonzeroRemaining;
        private void push(double input) {
            nonzeroRemaining = input != 0.0 ? 31 : Math.max(0, nonzeroRemaining - 1);
            if (--position < 0) position = 30;
            history[position] = input; history[position+31] = input;
        }
        private double output() {
            if (nonzeroRemaining == 0) return 0.0;
            double result = UPPER_COEFFICIENTS[15] * history[position+15];
            for (int i=0;i<15;i+=2) result += UPPER_COEFFICIENTS[i] * (history[position+i]+history[position+30-i]);
            return result;
        }
        private void reset() {
            java.util.Arrays.fill(history, 0.0);
            position = 0;
            nonzeroRemaining = 0;
        }
    }

    private static final class OutputDecimator {
        private final double[] history = new double[DECIMATOR_LENGTH * 2];
        private int position, nonzeroRemaining;
        private void push(double input) {
            nonzeroRemaining = input != 0.0 ? DECIMATOR_LENGTH : Math.max(0, nonzeroRemaining - 1);
            if (--position < 0) position = DECIMATOR_LENGTH - 1;
            history[position] = input;
            history[position + DECIMATOR_LENGTH] = input;
        }
        private double output() {
            if (nonzeroRemaining == 0) return 0.0;
            double result = DECIMATOR_COEFFICIENTS[79] * history[position + 79];
            for (int i = 0; i < 79; ++i)
                result += DECIMATOR_COEFFICIENTS[i] *
                        (history[position + i] + history[position + 158 - i]);
            return result;
        }
        private void reset() {
            java.util.Arrays.fill(history, 0.0);
            position = 0;
            nonzeroRemaining = 0;
        }
    }

    private static double besselI0(double x) {
        double sum = 1, term = 1;
        for (int k = 1; k < 50; ++k) {
            term *= x*x / (4.0*k*k); sum += term;
            if (term < sum * 1e-16) break;
        }
        return sum;
    }

    private static double[] makeDecimator() {
        double[] result = new double[DECIMATOR_LENGTH];
        double sum = 0, denominator = besselI0(8.6);
        for (int i = 0; i < DECIMATOR_LENGTH; ++i) {
            int x = i - 79;
            result[i] = (x == 0 ? 44000.0 / 96000.0 : Math.sin(2*Math.PI*22000*x/96000.0) / (Math.PI*x))
                    * besselI0(8.6 * Math.sqrt(1.0 - (x/79.0)*(x/79.0))) / denominator;
            sum += result[i];
        }
        for (int i = 0; i < DECIMATOR_LENGTH; ++i) result[i] /= sum;
        return result;
    }

    private static float[] makeSineTable() {
        float[] result = new float[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; ++i) result[i] = (float) Math.sin(2*Math.PI*i/TABLE_SIZE);
        return result;
    }

    private static float[][][] makeWaveTables() {
        float[][][] tables = new float[3][TABLE_BANKS][TABLE_SIZE];
        double[] real = new double[TABLE_SIZE], imaginary = new double[TABLE_SIZE];
        for (int kind = 0; kind < 3; ++kind) for (int bank = 0; bank < TABLE_BANKS; ++bank) {
            java.util.Arrays.fill(real, 0); java.util.Arrays.fill(imaginary, 0);
            int harmonics = 1 << bank;
            if (kind == 2) real[0] = 2.0 / Math.PI;
            for (int n = 1; n <= harmonics; ++n) {
                double taper = n <= harmonics * 0.5 || harmonics == 1 ? 1.0
                        : 0.5 + 0.5 * Math.cos(Math.PI * (2.0*n/harmonics - 1.0));
                if (kind == 0) {
                    imaginary[n] = taper / (Math.PI*n);
                    imaginary[TABLE_SIZE-n] = -imaginary[n];
                } else if (kind == 1) {
                    real[n] = taper / (2*Math.PI*Math.PI*n*n);
                    real[TABLE_SIZE-n] = real[n];
                } else if ((n & 1) == 0) {
                    real[n] = -taper * 2.0 / (Math.PI * (n*n - 1.0));
                    real[TABLE_SIZE-n] = real[n];
                }
            }
            inverseFourier(real, imaginary);
            for (int i = 0; i < TABLE_SIZE; ++i) tables[kind][bank][i] = (float) real[i];
        }
        return tables;
    }

    // Coefficients are Fourier-series amplitudes; inverse transform has no 1/N.
    private static void inverseFourier(double[] real, double[] imaginary) {
        for (int i = 1, j = 0; i < TABLE_SIZE; ++i) {
            int bit = TABLE_SIZE >> 1;
            for (; (j & bit) != 0; bit >>= 1) j ^= bit;
            j ^= bit;
            if (i < j) {
                double t = real[i]; real[i] = real[j]; real[j] = t;
                t = imaginary[i]; imaginary[i] = imaginary[j]; imaginary[j] = t;
            }
        }
        for (int length = 2; length <= TABLE_SIZE; length <<= 1) {
            double wr = Math.cos(2*Math.PI/length), wi = Math.sin(2*Math.PI/length);
            for (int start = 0; start < TABLE_SIZE; start += length) {
                double ur = 1, ui = 0;
                for (int j = 0; j < length/2; ++j) {
                    int a = start+j, b = a+length/2;
                    double vr = real[b]*ur - imaginary[b]*ui, vi = real[b]*ui + imaginary[b]*ur;
                    real[b] = real[a]-vr; imaginary[b] = imaginary[a]-vi;
                    real[a] += vr; imaginary[a] += vi;
                    double next = ur*wr-ui*wi; ui = ur*wi+ui*wr; ur = next;
                }
            }
        }
    }
    //[/user-code-and-variables]
}

 