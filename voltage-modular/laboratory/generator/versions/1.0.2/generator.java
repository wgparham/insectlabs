package com.insectlabs.generator;


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


public class generator extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public generator( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "dual waveform generator", ModuleType.ModuleType_Oscillators, 6.4 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "6555658fa3054a88aa314b8d028eecd0" );
    }

void InitializeControls()
{

        amplitudeLabel = new VoltageLabel( "amplitudeLabel", "Amplitude Label", this, "Amplitude" );
        AddComponent( amplitudeLabel );
        amplitudeLabel.SetWantsMouseNotifications( false );
        amplitudeLabel.SetPosition( 335, 305 );
        amplitudeLabel.SetSize( 60, 23 );
        amplitudeLabel.SetEditable( false, false );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        amplitudeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        amplitudeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        amplitudeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        amplitudeLabel.SetBorderSize( 1 );
        amplitudeLabel.SetMultiLineEdit( false );
        amplitudeLabel.SetIsNumberEditor( false );
        amplitudeLabel.SetNumberEditorRange( 0, 100 );
        amplitudeLabel.SetNumberEditorInterval( 1 );
        amplitudeLabel.SetNumberEditorUsesMouseWheel( false );
        amplitudeLabel.SetHasCustomTextHoverColor( false );
        amplitudeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        amplitudeLabel.SetFont( "Arial", 10, true, false );

        outputMeter = new VoltageAnalogVUMeter( "outputMeter", "Output Level", this );
        AddComponent( outputMeter );
        outputMeter.SetWantsMouseNotifications( false );
        outputMeter.SetPosition( 170, 20 );
        outputMeter.SetSize( 120, 60 );
        outputMeter.SetSkin( "Analog Amber" );

        powerSwitch = new VoltageSwitch( "powerSwitch", "Power", this, 0 );
        AddComponent( powerSwitch );
        powerSwitch.SetWantsMouseNotifications( false );
        powerSwitch.SetPosition( 60, 30 );
        powerSwitch.SetSize( 40, 40 );
        powerSwitch.SetSkin( "2-State Red Cap" );

        amplitudeKnob = new VoltageKnob( "amplitudeKnob", "Amplitude", this, 0.0, 1.0, 0.0 );
        AddComponent( amplitudeKnob );
        amplitudeKnob.SetWantsMouseNotifications( false );
        amplitudeKnob.SetPosition( 341, 260 );
        amplitudeKnob.SetSize( 50, 50 );
        amplitudeKnob.SetSkin( "Cosmo v2 Large" );
        amplitudeKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        amplitudeKnob.SetKnobParams( 215, 145 );
        amplitudeKnob.DisplayValueInPercent( true );
        amplitudeKnob.SetKnobAdjustsRing( true );

        frequencyScaleKnob = new VoltageKnob( "frequencyScaleKnob", "Frequency Scale", this, 1, 4, 3 );
        AddComponent( frequencyScaleKnob );
        frequencyScaleKnob.SetWantsMouseNotifications( false );
        frequencyScaleKnob.SetPosition( 290, 270 );
        frequencyScaleKnob.SetSize( 35, 35 );
        frequencyScaleKnob.SetSkin( "Cosmo v2 Pointer" );
        frequencyScaleKnob.SetRange( 1, 4, 3, false, 4 );
        frequencyScaleKnob.SetKnobParams( 320, 40 );
        frequencyScaleKnob.DisplayValueInPercent( false );
        frequencyScaleKnob.SetKnobAdjustsRing( true );

        frequencyKnob = new VoltageKnob( "frequencyKnob", "Frequency", this, -1.0, 1.0, 0.4177 );
        AddComponent( frequencyKnob );
        frequencyKnob.SetWantsMouseNotifications( false );
        frequencyKnob.SetPosition( 150, 90 );
        frequencyKnob.SetSize( 160, 160 );
        frequencyKnob.SetSkin( "Cosmo Large Black" );
        frequencyKnob.SetRange( -1.0, 1.0, 0.4177, false, 0 );
        frequencyKnob.SetKnobParams( 215, 145 );
        frequencyKnob.DisplayValueInPercent( false );
        frequencyKnob.SetKnobAdjustsRing( true );

        waveformKnob = new VoltageKnob( "waveformKnob", "Waveform", this, 1, 2, 1 );
        AddComponent( waveformKnob );
        waveformKnob.SetWantsMouseNotifications( false );
        waveformKnob.SetPosition( 20, 270 );
        waveformKnob.SetSize( 35, 35 );
        waveformKnob.SetSkin( "Cosmo v2 Pointer" );
        waveformKnob.SetRange( 1, 2, 1, false, 2 );
        waveformKnob.SetKnobParams( 320, 40 );
        waveformKnob.DisplayValueInPercent( false );
        waveformKnob.SetKnobAdjustsRing( true );

        dutyCycleKnob = new VoltageKnob( "dutyCycleKnob", "Duty Cycle", this, -1.0, 1.0, 0.0 );
        AddComponent( dutyCycleKnob );
        dutyCycleKnob.SetWantsMouseNotifications( false );
        dutyCycleKnob.SetPosition( 95, 270 );
        dutyCycleKnob.SetSize( 35, 35 );
        dutyCycleKnob.SetSkin( "Cosmo v2 Med" );
        dutyCycleKnob.SetRange( -1.0, 1.0, 0.0, false, 0 );
        dutyCycleKnob.SetKnobParams( 215, 145 );
        dutyCycleKnob.DisplayValueInPercent( false );
        dutyCycleKnob.SetKnobAdjustsRing( true );

        modulationModeKnob = new VoltageKnob( "modulationModeKnob", "Modulation Mode", this, 1, 4, 1 );
        AddComponent( modulationModeKnob );
        modulationModeKnob.SetWantsMouseNotifications( false );
        modulationModeKnob.SetPosition( 20, 195 );
        modulationModeKnob.SetSize( 35, 35 );
        modulationModeKnob.SetSkin( "Cosmo v2 Pointer" );
        modulationModeKnob.SetRange( 1, 4, 1, false, 4 );
        modulationModeKnob.SetKnobParams( 320, 40 );
        modulationModeKnob.DisplayValueInPercent( false );
        modulationModeKnob.SetKnobAdjustsRing( true );

        referenceOutput = new VoltageAudioJack( "referenceOutput", "1 kHz Reference Output", this, JackType.JackType_AudioOutput );
        AddComponent( referenceOutput );
        referenceOutput.SetWantsMouseNotifications( false );
        referenceOutput.SetPosition( 403, 30 );
        referenceOutput.SetSize( 37, 37 );
        referenceOutput.SetSkin( "Rotated Half" );

        referenceSwitch = new VoltageSwitch( "referenceSwitch", "Reference Destination", this, 1 );
        AddComponent( referenceSwitch );
        referenceSwitch.SetWantsMouseNotifications( false );
        referenceSwitch.SetPosition( 378, 30 );
        referenceSwitch.SetSize( 15, 30 );
        referenceSwitch.SetSkin( "3-State Slide Black" );

        mainOutput = new VoltageAudioJack( "mainOutput", "Main Output", this, JackType.JackType_AudioOutput );
        AddComponent( mainOutput );
        mainOutput.SetWantsMouseNotifications( false );
        mainOutput.SetPosition( 403, 270 );
        mainOutput.SetSize( 37, 37 );
        mainOutput.SetSkin( "Rotated Half" );

        brandLabel = new VoltageLabel( "brandLabel", "Brand Label", this, "insect laboratories" );
        AddComponent( brandLabel );
        brandLabel.SetWantsMouseNotifications( false );
        brandLabel.SetPosition( 0, 335 );
        brandLabel.SetSize( 460, 23 );
        brandLabel.SetEditable( false, false );
        brandLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        brandLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        brandLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        brandLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        brandLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        brandLabel.SetBorderSize( 4 );
        brandLabel.SetMultiLineEdit( false );
        brandLabel.SetIsNumberEditor( false );
        brandLabel.SetNumberEditorRange( 0, 100 );
        brandLabel.SetNumberEditorInterval( 1 );
        brandLabel.SetNumberEditorUsesMouseWheel( false );
        brandLabel.SetHasCustomTextHoverColor( false );
        brandLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        brandLabel.SetFont( "Courier New", 13, true, false );

        titleLabel = new VoltageLabel( "titleLabel", "Module Title", this, "Dual Waveform Generator" );
        AddComponent( titleLabel );
        titleLabel.SetWantsMouseNotifications( false );
        titleLabel.SetPosition( 18, 3 );
        titleLabel.SetSize( 300, 13 );
        titleLabel.SetEditable( false, false );
        titleLabel.SetJustificationFlags( VoltageLabel.Justification.Left );
        titleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        titleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        titleLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        titleLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        titleLabel.SetBorderSize( 1 );
        titleLabel.SetMultiLineEdit( false );
        titleLabel.SetIsNumberEditor( false );
        titleLabel.SetNumberEditorRange( 0, 100 );
        titleLabel.SetNumberEditorInterval( 1 );
        titleLabel.SetNumberEditorUsesMouseWheel( false );
        titleLabel.SetHasCustomTextHoverColor( false );
        titleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        titleLabel.SetFont( "Courier New", 13, true, false );

        outputLabel = new VoltageLabel( "outputLabel", "Output Label", this, "Output" );
        AddComponent( outputLabel );
        outputLabel.SetWantsMouseNotifications( false );
        outputLabel.SetPosition( 403, 305 );
        outputLabel.SetSize( 35, 23 );
        outputLabel.SetEditable( false, false );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        outputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        outputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        outputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        outputLabel.SetBorderSize( 1 );
        outputLabel.SetMultiLineEdit( false );
        outputLabel.SetIsNumberEditor( false );
        outputLabel.SetNumberEditorRange( 0, 100 );
        outputLabel.SetNumberEditorInterval( 1 );
        outputLabel.SetNumberEditorUsesMouseWheel( false );
        outputLabel.SetHasCustomTextHoverColor( false );
        outputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        outputLabel.SetFont( "Arial", 10, true, false );

        referenceLabel = new VoltageLabel( "referenceLabel", "Reference Label", this, "1kHz Ref." );
        AddComponent( referenceLabel );
        referenceLabel.SetWantsMouseNotifications( false );
        referenceLabel.SetPosition( 379, 65 );
        referenceLabel.SetSize( 60, 23 );
        referenceLabel.SetEditable( false, false );
        referenceLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        referenceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        referenceLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        referenceLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        referenceLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        referenceLabel.SetBorderSize( 1 );
        referenceLabel.SetMultiLineEdit( false );
        referenceLabel.SetIsNumberEditor( false );
        referenceLabel.SetNumberEditorRange( 0, 100 );
        referenceLabel.SetNumberEditorInterval( 1 );
        referenceLabel.SetNumberEditorUsesMouseWheel( false );
        referenceLabel.SetHasCustomTextHoverColor( false );
        referenceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        referenceLabel.SetFont( "Arial", 10, true, false );

        dutyCycleLabel = new VoltageLabel( "dutyCycleLabel", "Duty Cycle Label", this, "Duty Cycle" );
        AddComponent( dutyCycleLabel );
        dutyCycleLabel.SetWantsMouseNotifications( false );
        dutyCycleLabel.SetPosition( 92, 305 );
        dutyCycleLabel.SetSize( 40, 23 );
        dutyCycleLabel.SetEditable( false, false );
        dutyCycleLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        dutyCycleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        dutyCycleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        dutyCycleLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        dutyCycleLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        dutyCycleLabel.SetBorderSize( 1 );
        dutyCycleLabel.SetMultiLineEdit( false );
        dutyCycleLabel.SetIsNumberEditor( false );
        dutyCycleLabel.SetNumberEditorRange( 0, 100 );
        dutyCycleLabel.SetNumberEditorInterval( 1 );
        dutyCycleLabel.SetNumberEditorUsesMouseWheel( false );
        dutyCycleLabel.SetHasCustomTextHoverColor( false );
        dutyCycleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        dutyCycleLabel.SetFont( "Arial", 10, true, false );

        modulationOffLabel = new VoltageLabel( "modulationOffLabel", "Modulation Off Label", this, "Off" );
        AddComponent( modulationOffLabel );
        modulationOffLabel.SetWantsMouseNotifications( false );
        modulationOffLabel.SetPosition( 10, 190 );
        modulationOffLabel.SetSize( 16, 8 );
        modulationOffLabel.SetEditable( false, false );
        modulationOffLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulationOffLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulationOffLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modulationOffLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modulationOffLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modulationOffLabel.SetBorderSize( 1 );
        modulationOffLabel.SetMultiLineEdit( false );
        modulationOffLabel.SetIsNumberEditor( false );
        modulationOffLabel.SetNumberEditorRange( 0, 100 );
        modulationOffLabel.SetNumberEditorInterval( 1 );
        modulationOffLabel.SetNumberEditorUsesMouseWheel( false );
        modulationOffLabel.SetHasCustomTextHoverColor( false );
        modulationOffLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulationOffLabel.SetFont( "Arial", 6, true, false );

        modulationQuarterLabel = new VoltageLabel( "modulationQuarterLabel", "Modulation Int/4 Label", this, "Int/4" );
        AddComponent( modulationQuarterLabel );
        modulationQuarterLabel.SetWantsMouseNotifications( false );
        modulationQuarterLabel.SetPosition( 22, 183 );
        modulationQuarterLabel.SetSize( 16, 8 );
        modulationQuarterLabel.SetEditable( false, false );
        modulationQuarterLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulationQuarterLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulationQuarterLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modulationQuarterLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modulationQuarterLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modulationQuarterLabel.SetBorderSize( 1 );
        modulationQuarterLabel.SetMultiLineEdit( false );
        modulationQuarterLabel.SetIsNumberEditor( false );
        modulationQuarterLabel.SetNumberEditorRange( 0, 100 );
        modulationQuarterLabel.SetNumberEditorInterval( 1 );
        modulationQuarterLabel.SetNumberEditorUsesMouseWheel( false );
        modulationQuarterLabel.SetHasCustomTextHoverColor( false );
        modulationQuarterLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulationQuarterLabel.SetFont( "Arial", 6, true, false );

        modulationInternalLabel = new VoltageLabel( "modulationInternalLabel", "Modulation Int Label", this, "Int." );
        AddComponent( modulationInternalLabel );
        modulationInternalLabel.SetWantsMouseNotifications( false );
        modulationInternalLabel.SetPosition( 40, 183 );
        modulationInternalLabel.SetSize( 16, 8 );
        modulationInternalLabel.SetEditable( false, false );
        modulationInternalLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulationInternalLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulationInternalLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modulationInternalLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modulationInternalLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modulationInternalLabel.SetBorderSize( 1 );
        modulationInternalLabel.SetMultiLineEdit( false );
        modulationInternalLabel.SetIsNumberEditor( false );
        modulationInternalLabel.SetNumberEditorRange( 0, 100 );
        modulationInternalLabel.SetNumberEditorInterval( 1 );
        modulationInternalLabel.SetNumberEditorUsesMouseWheel( false );
        modulationInternalLabel.SetHasCustomTextHoverColor( false );
        modulationInternalLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulationInternalLabel.SetFont( "Arial", 6, true, false );

        modulationExternalLabel = new VoltageLabel( "modulationExternalLabel", "Modulation Ext Label", this, "Ext." );
        AddComponent( modulationExternalLabel );
        modulationExternalLabel.SetWantsMouseNotifications( false );
        modulationExternalLabel.SetPosition( 50, 190 );
        modulationExternalLabel.SetSize( 16, 8 );
        modulationExternalLabel.SetEditable( false, false );
        modulationExternalLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulationExternalLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulationExternalLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modulationExternalLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modulationExternalLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modulationExternalLabel.SetBorderSize( 1 );
        modulationExternalLabel.SetMultiLineEdit( false );
        modulationExternalLabel.SetIsNumberEditor( false );
        modulationExternalLabel.SetNumberEditorRange( 0, 100 );
        modulationExternalLabel.SetNumberEditorInterval( 1 );
        modulationExternalLabel.SetNumberEditorUsesMouseWheel( false );
        modulationExternalLabel.SetHasCustomTextHoverColor( false );
        modulationExternalLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulationExternalLabel.SetFont( "Arial", 6, true, false );

        sineLabel = new VoltageLabel( "sineLabel", "Sine Label", this, "SIN" );
        AddComponent( sineLabel );
        sineLabel.SetWantsMouseNotifications( false );
        sineLabel.SetPosition( 10, 265 );
        sineLabel.SetSize( 16, 8 );
        sineLabel.SetEditable( false, false );
        sineLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sineLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineLabel.SetBorderSize( 1 );
        sineLabel.SetMultiLineEdit( false );
        sineLabel.SetIsNumberEditor( false );
        sineLabel.SetNumberEditorRange( 0, 100 );
        sineLabel.SetNumberEditorInterval( 1 );
        sineLabel.SetNumberEditorUsesMouseWheel( false );
        sineLabel.SetHasCustomTextHoverColor( false );
        sineLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineLabel.SetFont( "Arial", 6, true, false );

        triangleLabel = new VoltageLabel( "triangleLabel", "Triangle Label", this, "TRI" );
        AddComponent( triangleLabel );
        triangleLabel.SetWantsMouseNotifications( false );
        triangleLabel.SetPosition( 45, 265 );
        triangleLabel.SetSize( 16, 8 );
        triangleLabel.SetEditable( false, false );
        triangleLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        triangleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        triangleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        triangleLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        triangleLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        triangleLabel.SetBorderSize( 1 );
        triangleLabel.SetMultiLineEdit( false );
        triangleLabel.SetIsNumberEditor( false );
        triangleLabel.SetNumberEditorRange( 0, 100 );
        triangleLabel.SetNumberEditorInterval( 1 );
        triangleLabel.SetNumberEditorUsesMouseWheel( false );
        triangleLabel.SetHasCustomTextHoverColor( false );
        triangleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        triangleLabel.SetFont( "Arial", 6, true, false );

        scaleHundredthLabel = new VoltageLabel( "scaleHundredthLabel", "Scale 0.01 Label", this, "0.01" );
        AddComponent( scaleHundredthLabel );
        scaleHundredthLabel.SetWantsMouseNotifications( false );
        scaleHundredthLabel.SetPosition( 280, 265 );
        scaleHundredthLabel.SetSize( 16, 8 );
        scaleHundredthLabel.SetEditable( false, false );
        scaleHundredthLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scaleHundredthLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scaleHundredthLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        scaleHundredthLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scaleHundredthLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scaleHundredthLabel.SetBorderSize( 1 );
        scaleHundredthLabel.SetMultiLineEdit( false );
        scaleHundredthLabel.SetIsNumberEditor( false );
        scaleHundredthLabel.SetNumberEditorRange( 0, 100 );
        scaleHundredthLabel.SetNumberEditorInterval( 1 );
        scaleHundredthLabel.SetNumberEditorUsesMouseWheel( false );
        scaleHundredthLabel.SetHasCustomTextHoverColor( false );
        scaleHundredthLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scaleHundredthLabel.SetFont( "Arial", 6, true, false );

        scaleTenthLabel = new VoltageLabel( "scaleTenthLabel", "Scale 0.10 Label", this, "0.10" );
        AddComponent( scaleTenthLabel );
        scaleTenthLabel.SetWantsMouseNotifications( false );
        scaleTenthLabel.SetPosition( 293, 255 );
        scaleTenthLabel.SetSize( 16, 8 );
        scaleTenthLabel.SetEditable( false, false );
        scaleTenthLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scaleTenthLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scaleTenthLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        scaleTenthLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scaleTenthLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scaleTenthLabel.SetBorderSize( 1 );
        scaleTenthLabel.SetMultiLineEdit( false );
        scaleTenthLabel.SetIsNumberEditor( false );
        scaleTenthLabel.SetNumberEditorRange( 0, 100 );
        scaleTenthLabel.SetNumberEditorInterval( 1 );
        scaleTenthLabel.SetNumberEditorUsesMouseWheel( false );
        scaleTenthLabel.SetHasCustomTextHoverColor( false );
        scaleTenthLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scaleTenthLabel.SetFont( "Arial", 6, true, false );

        scaleUnityLabel = new VoltageLabel( "scaleUnityLabel", "Scale 1.00 Label", this, "1.00" );
        AddComponent( scaleUnityLabel );
        scaleUnityLabel.SetWantsMouseNotifications( false );
        scaleUnityLabel.SetPosition( 307, 255 );
        scaleUnityLabel.SetSize( 16, 8 );
        scaleUnityLabel.SetEditable( false, false );
        scaleUnityLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scaleUnityLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scaleUnityLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        scaleUnityLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scaleUnityLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scaleUnityLabel.SetBorderSize( 1 );
        scaleUnityLabel.SetMultiLineEdit( false );
        scaleUnityLabel.SetIsNumberEditor( false );
        scaleUnityLabel.SetNumberEditorRange( 0, 100 );
        scaleUnityLabel.SetNumberEditorInterval( 1 );
        scaleUnityLabel.SetNumberEditorUsesMouseWheel( false );
        scaleUnityLabel.SetHasCustomTextHoverColor( false );
        scaleUnityLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scaleUnityLabel.SetFont( "Arial", 6, true, false );

        scaleTenLabel = new VoltageLabel( "scaleTenLabel", "Scale 10.0 Label", this, "10.0" );
        AddComponent( scaleTenLabel );
        scaleTenLabel.SetWantsMouseNotifications( false );
        scaleTenLabel.SetPosition( 320, 265 );
        scaleTenLabel.SetSize( 16, 8 );
        scaleTenLabel.SetEditable( false, false );
        scaleTenLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scaleTenLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scaleTenLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        scaleTenLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scaleTenLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scaleTenLabel.SetBorderSize( 1 );
        scaleTenLabel.SetMultiLineEdit( false );
        scaleTenLabel.SetIsNumberEditor( false );
        scaleTenLabel.SetNumberEditorRange( 0, 100 );
        scaleTenLabel.SetNumberEditorInterval( 1 );
        scaleTenLabel.SetNumberEditorUsesMouseWheel( false );
        scaleTenLabel.SetHasCustomTextHoverColor( false );
        scaleTenLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scaleTenLabel.SetFont( "Arial", 6, true, false );

        scaleLabel = new VoltageLabel( "scaleLabel", "Scale Label", this, "Scale" );
        AddComponent( scaleLabel );
        scaleLabel.SetWantsMouseNotifications( true );
        scaleLabel.SetPosition( 290, 305 );
        scaleLabel.SetSize( 35, 23 );
        scaleLabel.SetEditable( false, false );
        scaleLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scaleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scaleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        scaleLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scaleLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scaleLabel.SetBorderSize( 1 );
        scaleLabel.SetMultiLineEdit( false );
        scaleLabel.SetIsNumberEditor( false );
        scaleLabel.SetNumberEditorRange( 0, 100 );
        scaleLabel.SetNumberEditorInterval( 1 );
        scaleLabel.SetNumberEditorUsesMouseWheel( false );
        scaleLabel.SetHasCustomTextHoverColor( false );
        scaleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scaleLabel.SetFont( "Arial", 10, true, false );

        modulationSelectLabel = new VoltageLabel( "modulationSelectLabel", "Modulation Select Label", this, "Select" );
        AddComponent( modulationSelectLabel );
        modulationSelectLabel.SetWantsMouseNotifications( false );
        modulationSelectLabel.SetPosition( 20, 230 );
        modulationSelectLabel.SetSize( 35, 23 );
        modulationSelectLabel.SetEditable( false, false );
        modulationSelectLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulationSelectLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulationSelectLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modulationSelectLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modulationSelectLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modulationSelectLabel.SetBorderSize( 1 );
        modulationSelectLabel.SetMultiLineEdit( false );
        modulationSelectLabel.SetIsNumberEditor( false );
        modulationSelectLabel.SetNumberEditorRange( 0, 100 );
        modulationSelectLabel.SetNumberEditorInterval( 1 );
        modulationSelectLabel.SetNumberEditorUsesMouseWheel( false );
        modulationSelectLabel.SetHasCustomTextHoverColor( false );
        modulationSelectLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulationSelectLabel.SetFont( "Arial", 10, true, false );

        modulationAdjustKnob = new VoltageKnob( "modulationAdjustKnob", "Modulation Adjust", this, 0.0, 1.0, 0.5 );
        AddComponent( modulationAdjustKnob );
        modulationAdjustKnob.SetWantsMouseNotifications( false );
        modulationAdjustKnob.SetPosition( 95, 195 );
        modulationAdjustKnob.SetSize( 35, 35 );
        modulationAdjustKnob.SetSkin( "Cosmo v2 Med" );
        modulationAdjustKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        modulationAdjustKnob.SetKnobParams( 215, 145 );
        modulationAdjustKnob.DisplayValueInPercent( false );
        modulationAdjustKnob.SetKnobAdjustsRing( true );

        modulationAdjustLabel = new VoltageLabel( "modulationAdjustLabel", "Modulation Adjust Label", this, "Adjust" );
        AddComponent( modulationAdjustLabel );
        modulationAdjustLabel.SetWantsMouseNotifications( false );
        modulationAdjustLabel.SetPosition( 95, 230 );
        modulationAdjustLabel.SetSize( 35, 23 );
        modulationAdjustLabel.SetEditable( false, false );
        modulationAdjustLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulationAdjustLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulationAdjustLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modulationAdjustLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modulationAdjustLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modulationAdjustLabel.SetBorderSize( 1 );
        modulationAdjustLabel.SetMultiLineEdit( false );
        modulationAdjustLabel.SetIsNumberEditor( false );
        modulationAdjustLabel.SetNumberEditorRange( 0, 100 );
        modulationAdjustLabel.SetNumberEditorInterval( 1 );
        modulationAdjustLabel.SetNumberEditorUsesMouseWheel( false );
        modulationAdjustLabel.SetHasCustomTextHoverColor( false );
        modulationAdjustLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulationAdjustLabel.SetFont( "Arial", 10, true, false );

        externalFmInput = new VoltageAudioJack( "externalFmInput", "External FM Input", this, JackType.JackType_AudioInput );
        AddComponent( externalFmInput );
        externalFmInput.SetWantsMouseNotifications( false );
        externalFmInput.SetPosition( 60, 150 );
        externalFmInput.SetSize( 37, 37 );
        externalFmInput.SetSkin( "Dark Jack Straight" );
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
        resetGenerator();

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
        boolean powerRequested = isPowered();
        updatePowerGain(powerRequested);
        if (!powerRequested && powerGain <= 0.0) {
            referenceOutput.SetValue(0.0);
            mainOutput.SetValue(0.0);
            outputMeter.SetValue(0.0);
            return;
        }

        double mainFrequency = smoothMainFrequency(mainFrequencyHz());
        int modulationMode = selectedModulationMode();
        double modulation;
        if (modulationMode == MODULATION_INTERNAL_QUARTER || modulationMode == MODULATION_INTERNAL) {
            modulation = internalModulation() * internalDepthScale(modulationMode);
        } else if (modulationMode == MODULATION_EXTERNAL) {
            modulation = readInput(externalFmInput)
                    * clamp(modulationAdjustKnob.GetValue(), 0.0, 1.0) / FM_INPUT_REFERENCE_VOLTS;
        } else {
            modulation = 0.0;
        }
        mainFrequency = clamp(mainFrequency * (1.0 + INTERNAL_FM_DEPTH * modulation),
                MINIMUM_FREQUENCY_HZ, MAXIMUM_FREQUENCY_HZ);
        mainPhase = advancePhase(mainPhase, mainFrequency);

        double dutyCycle = smoothDutyCycle(dutyCycleKnob.GetValue());
        double source = selectedWaveform(mainPhase, dutyCycle);
        double targetAmplitude = clamp(amplitudeKnob.GetValue(), 0.0, 1.0) * MAXIMUM_OUTPUT_VOLTS;
        smoothedAmplitude += AMPLITUDE_SMOOTH_COEFFICIENT * (targetAmplitude - smoothedAmplitude);
        if (Math.abs(targetAmplitude - smoothedAmplitude) < 1.0e-10) {
            smoothedAmplitude = targetAmplitude;
        }
        double amplitude = smoothedAmplitude;
        double mainSignal = outputStage(source * amplitude);

        int referenceDestination = selectedReferenceDestination();
        double reference = sine(referencePhase);
        double referenceSignal = referenceDestination == REFERENCE_JACK
                ? REFERENCE_OUTPUT_VOLTS * reference : 0.0;
        if (referenceDestination == REFERENCE_MIX) {
            mainSignal = outputStage(mainSignal + REFERENCE_MIX_VOLTS * reference);
        }
        referencePhase = advancePhase(referencePhase, REFERENCE_FREQUENCY_HZ);

        referenceSignal *= powerGain;
        mainSignal *= powerGain;
        referenceOutput.SetValue(referenceSignal);
        mainOutput.SetValue(mainSignal);
        updateOutputMeter(mainSignal);

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
        // A source module has no signal-through path. Bypass is silent and freezes state.
        referenceOutput.SetValue(0.0);
        mainOutput.SetValue(0.0);
        outputMeter.SetValue(0.0);

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
        if (component == powerSwitch) {
            return isPowered() ? "POWER: 8 ms fade in" : "POWER: 8 ms fade out, then oscillator stops";
        }
        if (component == frequencyKnob) {
            return "FREQUENCY: " + formatFrequency(mainFrequencyHz());
        }
        if (component == frequencyScaleKnob) {
            return "SCALE: " + frequencyScaleText() + "; enter position 1–4";
        }
        if (component == amplitudeKnob) {
            return "AMPLITUDE: " + formatOneDecimal(amplitudeKnob.GetValue() * MAXIMUM_OUTPUT_VOLTS)
                    + " V peak requested, before compression";
        }
        if (component == waveformKnob) {
            return selectedWaveformMode() == WAVEFORM_SINE
                    ? "WAVEFORM: SIN (1)" : "WAVEFORM: TRI / RMP / SAW (2)";
        }
        if (component == dutyCycleKnob) {
            return "DUTY CYCLE: " + dutyLabel() + "; TRI only (SIN unaffected)";
        }
        if (component == modulationModeKnob) {
            return "FM SELECT: " + modulationLabel() + "; 1 Off, 2 Int/4, 3 Int, 4 Ext";
        }
        if (component == modulationAdjustKnob) {
            return selectedModulationMode() == MODULATION_EXTERNAL
                    ? "FM ADJUST: external depth " + formatOneDecimal(modulationAdjustKnob.GetValue() * 100.0) + "%"
                    : "FM ADJUST: internal rate " + formatFrequency(internalModulationRateHz());
        }
        if (component == externalFmInput) {
            return "EXTERNAL FM: at 100% ADJUST, ±5 V gives ±18% carrier deviation";
        }
        if (component == referenceSwitch) {
            int destination = selectedReferenceDestination();
            if (destination == REFERENCE_JACK) return "1 kHz REFERENCE: up, dedicated jack (2)";
            if (destination == REFERENCE_MIX) return "1 kHz REFERENCE: down, main mix (0)";
            return "1 kHz REFERENCE: middle, off (1)";
        }
        if (component == referenceOutput) {
            return "1 kHz REFERENCE: 5 V peak sine; reference switch up, power on";
        }
        if (component == mainOutput) {
            return "MAIN OUTPUT: selected waveform; optional 2.5 V reference mix through the output stage";
        }
        if (component == outputMeter) {
            return "MAIN OUTPUT: averaged level indication; not a calibrated reference meter";
        }
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
        // Convert displayed physical units before handing the edit to the SDK's undo-aware path.
        if (!Double.isFinite(newValue)) return;
        newValue = controlValueFromDisplay(component, newValue);
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



        return GetStateInformation();
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
    private VoltageAudioJack externalFmInput;
    private VoltageLabel modulationAdjustLabel;
    private VoltageKnob modulationAdjustKnob;
    private VoltageLabel modulationSelectLabel;
    private VoltageLabel scaleLabel;
    private VoltageLabel scaleTenLabel;
    private VoltageLabel scaleUnityLabel;
    private VoltageLabel scaleTenthLabel;
    private VoltageLabel scaleHundredthLabel;
    private VoltageLabel triangleLabel;
    private VoltageLabel sineLabel;
    private VoltageLabel modulationExternalLabel;
    private VoltageLabel modulationInternalLabel;
    private VoltageLabel modulationQuarterLabel;
    private VoltageLabel modulationOffLabel;
    private VoltageLabel dutyCycleLabel;
    private VoltageLabel referenceLabel;
    private VoltageLabel outputLabel;
    private VoltageLabel titleLabel;
    private VoltageLabel brandLabel;
    private VoltageAudioJack mainOutput;
    private VoltageSwitch referenceSwitch;
    private VoltageAudioJack referenceOutput;
    private VoltageKnob modulationModeKnob;
    private VoltageKnob dutyCycleKnob;
    private VoltageKnob waveformKnob;
    private VoltageKnob frequencyKnob;
    private VoltageKnob frequencyScaleKnob;
    private VoltageKnob amplitudeKnob;
    private VoltageSwitch powerSwitch;
    private VoltageAnalogVUMeter outputMeter;
    private VoltageLabel amplitudeLabel;


    //[user-code-and-variables]    Add your own variables and functions here
    // Generator 1.0.1 candidate: cleanup and manual duty-cycle smoothing. See development/REVIEW.md for retained
    // source-module bypass, native-rate voicing and control-smoothing exceptions.
    private static final double SAMPLE_RATE = 48000.0;
    private static final double TWO_PI = Math.PI * 2.0;
    private static final double MINIMUM_FREQUENCY_HZ = 0.01;
    private static final double MAXIMUM_FREQUENCY_HZ = 18000.0;
    private static final double REFERENCE_FREQUENCY_HZ = 1000.0;
    private static final double REFERENCE_OUTPUT_VOLTS = 5.0;
    private static final double REFERENCE_MIX_VOLTS = 2.5;
    private static final double MAXIMUM_OUTPUT_VOLTS = 10.0;
    private static final double FM_INPUT_REFERENCE_VOLTS = 5.0;
    private static final double INTERNAL_FM_DEPTH = 0.18;
    private static final double INTERNAL_FM_DRIFT_HZ = 0.018;
    private static final int WAVEFORM_SINE = 1;
    private static final int WAVEFORM_TRIANGLE = 2;
    private static final int MODULATION_OFF = 1;
    private static final int MODULATION_INTERNAL_QUARTER = 2;
    private static final int MODULATION_INTERNAL = 3;
    private static final int MODULATION_EXTERNAL = 4;
    private static final int REFERENCE_JACK = 2;
    private static final int REFERENCE_OFF = 1;
    private static final int REFERENCE_MIX = 0;

    // 10 ms time constant; computed once, with no per-sample allocation.
    private static final double AMPLITUDE_SMOOTH_COEFFICIENT =
            1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010));
    private double smoothedAmplitude;
    // Smooth only manual carrier tuning, before FM, so external/audio-rate FM stays immediate.
    private static final double FREQUENCY_SMOOTH_COEFFICIENT =
            1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010));
    private double smoothedFrequency = Double.NaN;
    private static final double DUTY_CYCLE_SMOOTH_COEFFICIENT =
            1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010));
    private double smoothedDutyCycle = Double.NaN;
    // Manual POWER alone receives a short declicking fade. Host bypass remains hard.
    private static final double POWER_FADE_STEP = 1.0 / (SAMPLE_RATE * 0.008);
    private double powerGain;
    private static final double METER_SMOOTH_COEFFICIENT =
            1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.150));
    private double meterEnvelope;
    private int meterCountdown;

    private double smoothMainFrequency(double target) {
        if (Double.isNaN(smoothedFrequency)) {
            smoothedFrequency = target;
        } else {
            smoothedFrequency += FREQUENCY_SMOOTH_COEFFICIENT * (target - smoothedFrequency);
        }
        return smoothedFrequency;
    }

    private double smoothDutyCycle(double target) {
        target = clamp(target, -1.0, 1.0);
        if (Double.isNaN(smoothedDutyCycle)) {
            // Adopt the initial/saved value without an unintended startup sweep.
            smoothedDutyCycle = target;
        } else {
            smoothedDutyCycle += DUTY_CYCLE_SMOOTH_COEFFICIENT * (target - smoothedDutyCycle);
        }
        return smoothedDutyCycle;
    }

    private void updatePowerGain(boolean powerRequested) {
        if (powerRequested) {
            powerGain = Math.min(1.0, powerGain + POWER_FADE_STEP);
        } else {
            powerGain = Math.max(0.0, powerGain - POWER_FADE_STEP);
        }
    }

    private static double internalDepthScale(int mode) {
        return mode == MODULATION_INTERNAL_QUARTER ? 0.25 : 1.0;
    }

    private double averagedMeterLevel(double output) {
        meterEnvelope += METER_SMOOTH_COEFFICIENT * (Math.abs(output) - meterEnvelope);
        // Nominal 5 V peak sine sits near mid-scale; driven levels retain headroom.
        return clamp(meterEnvelope * (Math.PI / 20.0), 0.0, 1.0);
    }

    private void updateOutputMeter(double output) {
        double level = averagedMeterLevel(output);
        if (--meterCountdown <= 0) {
            outputMeter.SetValue(level);
            meterCountdown = 480; // 100 display updates/second at Voltage Modular's 48 kHz.
        }
    }

    private double mainPhase;
    private double internalModulationPhase;
    private double referencePhase;
    private double modulationDriftPhase;

    private void resetGenerator() {
        smoothedAmplitude = 0.0;
        smoothedFrequency = Double.NaN;
        smoothedDutyCycle = Double.NaN;
        powerGain = 0.0;
        meterEnvelope = 0.0;
        meterCountdown = 0;
        mainPhase = internalModulationPhase = referencePhase = 0.0;
        modulationDriftPhase = 0.173;
    }

    private boolean isPowered() {
        return powerSwitch.GetValue() >= 0.5;
    }

    private double mainFrequencyHz() {
        return clamp(100.0 * scaleMultiplier() * Math.pow(10.0, frequencyKnob.GetValue()),
                MINIMUM_FREQUENCY_HZ, MAXIMUM_FREQUENCY_HZ);
    }

    private double scaleMultiplier() {
        switch ((int) Math.round(frequencyScaleKnob.GetValue())) {
            case 1: return 0.01;
            case 2: return 0.10;
            case 3: return 1.00;
            default: return 10.0;
        }
    }

    private double internalModulation() {
        double rate = internalModulationRateHz();
        modulationDriftPhase = advancePhase(modulationDriftPhase, INTERNAL_FM_DRIFT_HZ);
        internalModulationPhase = advancePhase(internalModulationPhase,
                rate * (1.0 + Math.sin(TWO_PI * modulationDriftPhase) * 0.025));
        return triangle(internalModulationPhase);
    }

    private double internalModulationRateHz() {
        return 0.05 * Math.pow(1000.0, clamp(modulationAdjustKnob.GetValue(), 0.0, 1.0));
    }

    private int selectedWaveformMode() {
        return (int) Math.round(waveformKnob.GetValue());
    }

    private int selectedModulationMode() {
        return (int) Math.round(modulationModeKnob.GetValue());
    }

    private int selectedReferenceDestination() {
        return (int) Math.round(referenceSwitch.GetValue());
    }

    private double selectedWaveform(double phase, double dutyControl) {
        if (selectedWaveformMode() == WAVEFORM_SINE) return sine(phase);
        double duty = 0.08 + 0.84 * ((clamp(dutyControl, -1.0, 1.0) + 1.0) * 0.5);
        return variableTriangle(phase, duty);
    }

    private static double sine(double phase) {
        return Math.sin(TWO_PI * phase);
    }

    private static double triangle(double phase) {
        return 1.0 - 4.0 * Math.abs(phase - 0.5);
    }

    private static double variableTriangle(double phase, double duty) {
        return phase < duty
                ? -1.0 + 2.0 * phase / duty
                : 1.0 - 2.0 * (phase - duty) / (1.0 - duty);
    }

    private static double advancePhase(double phase, double frequency) {
        phase += frequency / SAMPLE_RATE;
        return phase - Math.floor(phase);
    }

    private static double outputStage(double value) {
        double magnitude = Math.abs(value);
        if (magnitude <= 5.0) return value;
        double excess = magnitude - 5.0;
        double compressed = 5.0 + excess / (1.0 + 0.17 * excess);
        // Ease in from 5 to 7 V. Above 7 V the auditioned curve is unchanged.
        double position = Math.min(1.0, excess * 0.5);
        double blend = position * position * position * (10.0 + position * (-15.0 + 6.0 * position));
        return Math.copySign(magnitude + blend * (compressed - magnitude), value);
    }

    private String frequencyScaleText() {
        return formatOneDecimal(scaleMultiplier() * 100.0) + " Hz center";
    }

    private String dutyLabel() {
        return formatOneDecimal(8.0 + 84.0
                * ((clamp(dutyCycleKnob.GetValue(), -1.0, 1.0) + 1.0) * 0.5)) + "% rise";
    }

    private String modulationLabel() {
        switch (selectedModulationMode()) {
            case MODULATION_INTERNAL_QUARTER: return "INT/4";
            case MODULATION_INTERNAL: return "INT";
            case MODULATION_EXTERNAL: return "EXT";
            default: return "OFF";
        }
    }

    private static String formatFrequency(double frequency) {
        return String.format(java.util.Locale.US, "%.2f Hz", frequency);
    }

    private double controlValueFromDisplay(VoltageComponent component, double value) {
        if (component == frequencyKnob) {
            double center = 100.0 * scaleMultiplier();
            return Math.log10(clamp(value, center * 0.1, center * 10.0) / center);
        }
        if (component == amplitudeKnob) return clamp(value / MAXIMUM_OUTPUT_VOLTS, 0.0, 1.0);
        if (component == dutyCycleKnob) return clamp((value - 8.0) / 42.0 - 1.0, -1.0, 1.0);
        if (component == modulationAdjustKnob) {
            if (selectedModulationMode() == MODULATION_EXTERNAL) return clamp(value / 100.0, 0.0, 1.0);
            return Math.log(clamp(value, 0.05, 50.0) / 0.05) / Math.log(1000.0);
        }
        return value;
    }

    private static String formatOneDecimal(double value) {
        return String.format(java.util.Locale.US, "%.1f", value);
    }

    private static double readInput(VoltageAudioJack jack) {
        return jack.IsConnected() ? jack.GetValue() : 0.0;
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    //[/user-code-and-variables]
}

 