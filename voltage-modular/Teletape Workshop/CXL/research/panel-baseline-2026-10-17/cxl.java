package com.insectlabs.cxl;


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


public class cxl extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public cxl( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "CXL - Algorithmic Reverberation", ModuleType.ModuleType_Effect, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "12a35b96a615457c9faaa452a7025b9a" );
    }

void InitializeControls()
{

        labelLocation_11 = new VoltageLabel( "labelLocation_11", "Location Mark_11", this, "12bit" );
        AddComponent( labelLocation_11 );
        labelLocation_11.SetWantsMouseNotifications( false );
        labelLocation_11.SetPosition( 38, 255 );
        labelLocation_11.SetSize( 41, 14 );
        labelLocation_11.SetEditable( false, false );
        labelLocation_11.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_11.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_11.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_11.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_11.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_11.SetBorderSize( 1 );
        labelLocation_11.SetMultiLineEdit( false );
        labelLocation_11.SetIsNumberEditor( false );
        labelLocation_11.SetNumberEditorRange( 0, 100 );
        labelLocation_11.SetNumberEditorInterval( 1 );
        labelLocation_11.SetNumberEditorUsesMouseWheel( false );
        labelLocation_11.SetHasCustomTextHoverColor( false );
        labelLocation_11.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_11.SetFont( "Arial", 7, true, false );

        labelFooter = new VoltageLabel( "labelFooter", "Algorithmic Reverberation", this, "algorithmic reverberation" );
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
        imageTTW.SetSize( 41, 13 );
        imageTTW.SetCurrentImage( "image image(3).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "CXL" );
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

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(Seattle) Ltd" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 5, 13 );
        labelLocation.SetSize( 41, 14 );
        labelLocation.SetEditable( false, false );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation.SetBorderSize( 1 );
        labelLocation.SetMultiLineEdit( false );
        labelLocation.SetIsNumberEditor( false );
        labelLocation.SetNumberEditorRange( 0, 100 );
        labelLocation.SetNumberEditorInterval( 1 );
        labelLocation.SetNumberEditorUsesMouseWheel( false );
        labelLocation.SetHasCustomTextHoverColor( false );
        labelLocation.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation.SetFont( "Arial", 7, true, false );

        knobDecay = new VoltageKnob( "knobDecay", "Decay Knob", this, 0.0, 1.0, 0.5 );
        AddComponent( knobDecay );
        knobDecay.SetWantsMouseNotifications( false );
        knobDecay.SetPosition( 15, 90 );
        knobDecay.SetSize( 27, 27 );
        knobDecay.SetSkin( "Plastic White" );
        knobDecay.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobDecay.SetKnobParams( 215, 145 );
        knobDecay.DisplayValueInPercent( false );
        knobDecay.SetKnobAdjustsRing( true );

        knobDamp = new VoltageKnob( "knobDamp", "Damping Knob", this, 0.0, 1.0, 0.5 );
        AddComponent( knobDamp );
        knobDamp.SetWantsMouseNotifications( false );
        knobDamp.SetPosition( 75, 40 );
        knobDamp.SetSize( 27, 27 );
        knobDamp.SetSkin( "Plastic White" );
        knobDamp.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobDamp.SetKnobParams( 215, 145 );
        knobDamp.DisplayValueInPercent( false );
        knobDamp.SetKnobAdjustsRing( true );

        knobMix = new VoltageKnob( "knobMix", "Dry/Wet Mix Knob", this, 0.0, 1.0, 0.5 );
        AddComponent( knobMix );
        knobMix.SetWantsMouseNotifications( false );
        knobMix.SetPosition( 75, 140 );
        knobMix.SetSize( 27, 27 );
        knobMix.SetSkin( "Plastic White" );
        knobMix.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobMix.SetKnobParams( 215, 145 );
        knobMix.DisplayValueInPercent( false );
        knobMix.SetKnobAdjustsRing( true );

        inputLeft = new VoltageAudioJack( "inputLeft", "Left Input", this, JackType.JackType_AudioInput );
        AddComponent( inputLeft );
        inputLeft.SetWantsMouseNotifications( false );
        inputLeft.SetPosition( 15, 240 );
        inputLeft.SetSize( 25, 25 );
        inputLeft.SetSkin( "Jack Round 25px" );

        inputRight = new VoltageAudioJack( "inputRight", "Right Input", this, JackType.JackType_AudioInput );
        AddComponent( inputRight );
        inputRight.SetWantsMouseNotifications( false );
        inputRight.SetPosition( 15, 290 );
        inputRight.SetSize( 25, 25 );
        inputRight.SetSkin( "Jack Round 25px" );

        outputLeft = new VoltageAudioJack( "outputLeft", "Left Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputLeft );
        outputLeft.SetWantsMouseNotifications( false );
        outputLeft.SetPosition( 75, 240 );
        outputLeft.SetSize( 25, 25 );
        outputLeft.SetSkin( "Mini Jack 25px" );

        outputRight = new VoltageAudioJack( "outputRight", "Right Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputRight );
        outputRight.SetWantsMouseNotifications( false );
        outputRight.SetPosition( 75, 290 );
        outputRight.SetSize( 25, 25 );
        outputRight.SetSkin( "Mini Jack 25px" );

        switchADDA = new VoltageSwitch( "switchADDA", "AD/DA Switch", this, 1 );
        AddComponent( switchADDA );
        switchADDA.SetWantsMouseNotifications( false );
        switchADDA.SetPosition( 45, 265 );
        switchADDA.SetSize( 25, 25 );
        switchADDA.SetSkin( "2-State Red Cap" );

        knobGain = new VoltageKnob( "knobGain", "Input Gain/Drive", this, 0.0, 1.0, 0.5 );
        AddComponent( knobGain );
        knobGain.SetWantsMouseNotifications( false );
        knobGain.SetPosition( 15, 190 );
        knobGain.SetSize( 27, 27 );
        knobGain.SetSkin( "Plastic White" );
        knobGain.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobGain.SetKnobParams( 215, 145 );
        knobGain.DisplayValueInPercent( false );
        knobGain.SetKnobAdjustsRing( true );

        knobAMP = new VoltageKnob( "knobAMP", "Output Amp", this, 0.0, 1.0, 0.5 );
        AddComponent( knobAMP );
        knobAMP.SetWantsMouseNotifications( false );
        knobAMP.SetPosition( 75, 190 );
        knobAMP.SetSize( 27, 27 );
        knobAMP.SetSkin( "Plastic White" );
        knobAMP.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobAMP.SetKnobParams( 215, 145 );
        knobAMP.DisplayValueInPercent( false );
        knobAMP.SetKnobAdjustsRing( true );

        knobPreDelay = new VoltageKnob( "knobPreDelay", "Pre Delay Knob", this, 0.0, 1.0, 0.5 );
        AddComponent( knobPreDelay );
        knobPreDelay.SetWantsMouseNotifications( false );
        knobPreDelay.SetPosition( 15, 40 );
        knobPreDelay.SetSize( 27, 27 );
        knobPreDelay.SetSkin( "Plastic White" );
        knobPreDelay.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobPreDelay.SetKnobParams( 215, 145 );
        knobPreDelay.DisplayValueInPercent( false );
        knobPreDelay.SetKnobAdjustsRing( true );

        labelLocation_1 = new VoltageLabel( "labelLocation_1", "Location Mark_1", this, "DECAY" );
        AddComponent( labelLocation_1 );
        labelLocation_1.SetWantsMouseNotifications( false );
        labelLocation_1.SetPosition( 8, 78 );
        labelLocation_1.SetSize( 41, 14 );
        labelLocation_1.SetEditable( false, false );
        labelLocation_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_1.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_1.SetBorderSize( 1 );
        labelLocation_1.SetMultiLineEdit( false );
        labelLocation_1.SetIsNumberEditor( false );
        labelLocation_1.SetNumberEditorRange( 0, 100 );
        labelLocation_1.SetNumberEditorInterval( 1 );
        labelLocation_1.SetNumberEditorUsesMouseWheel( false );
        labelLocation_1.SetHasCustomTextHoverColor( false );
        labelLocation_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_1.SetFont( "Arial", 7, true, false );

        labelLocation_2 = new VoltageLabel( "labelLocation_2", "Location Mark_2", this, "DAMP" );
        AddComponent( labelLocation_2 );
        labelLocation_2.SetWantsMouseNotifications( false );
        labelLocation_2.SetPosition( 68, 28 );
        labelLocation_2.SetSize( 41, 14 );
        labelLocation_2.SetEditable( false, false );
        labelLocation_2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_2.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_2.SetBorderSize( 1 );
        labelLocation_2.SetMultiLineEdit( false );
        labelLocation_2.SetIsNumberEditor( false );
        labelLocation_2.SetNumberEditorRange( 0, 100 );
        labelLocation_2.SetNumberEditorInterval( 1 );
        labelLocation_2.SetNumberEditorUsesMouseWheel( false );
        labelLocation_2.SetHasCustomTextHoverColor( false );
        labelLocation_2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_2.SetFont( "Arial", 7, true, false );

        labelLocation_3 = new VoltageLabel( "labelLocation_3", "Location Mark_3", this, "PRE-DELAY" );
        AddComponent( labelLocation_3 );
        labelLocation_3.SetWantsMouseNotifications( false );
        labelLocation_3.SetPosition( 8, 28 );
        labelLocation_3.SetSize( 41, 14 );
        labelLocation_3.SetEditable( false, false );
        labelLocation_3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_3.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_3.SetBorderSize( 1 );
        labelLocation_3.SetMultiLineEdit( false );
        labelLocation_3.SetIsNumberEditor( false );
        labelLocation_3.SetNumberEditorRange( 0, 100 );
        labelLocation_3.SetNumberEditorInterval( 1 );
        labelLocation_3.SetNumberEditorUsesMouseWheel( false );
        labelLocation_3.SetHasCustomTextHoverColor( false );
        labelLocation_3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_3.SetFont( "Arial", 7, true, false );

        labelLocation_4 = new VoltageLabel( "labelLocation_4", "Location Mark_4", this, "MIX" );
        AddComponent( labelLocation_4 );
        labelLocation_4.SetWantsMouseNotifications( false );
        labelLocation_4.SetPosition( 68, 128 );
        labelLocation_4.SetSize( 41, 14 );
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

        labelLocation_5 = new VoltageLabel( "labelLocation_5", "Location Mark_5", this, "AMP" );
        AddComponent( labelLocation_5 );
        labelLocation_5.SetWantsMouseNotifications( false );
        labelLocation_5.SetPosition( 68, 178 );
        labelLocation_5.SetSize( 41, 14 );
        labelLocation_5.SetEditable( false, false );
        labelLocation_5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_5.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_5.SetBorderSize( 1 );
        labelLocation_5.SetMultiLineEdit( false );
        labelLocation_5.SetIsNumberEditor( false );
        labelLocation_5.SetNumberEditorRange( 0, 100 );
        labelLocation_5.SetNumberEditorInterval( 1 );
        labelLocation_5.SetNumberEditorUsesMouseWheel( false );
        labelLocation_5.SetHasCustomTextHoverColor( false );
        labelLocation_5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_5.SetFont( "Arial", 7, true, false );

        labelLocation_6 = new VoltageLabel( "labelLocation_6", "Location Mark_6", this, "GAIN" );
        AddComponent( labelLocation_6 );
        labelLocation_6.SetWantsMouseNotifications( false );
        labelLocation_6.SetPosition( 8, 178 );
        labelLocation_6.SetSize( 41, 14 );
        labelLocation_6.SetEditable( false, false );
        labelLocation_6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_6.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_6.SetBorderSize( 1 );
        labelLocation_6.SetMultiLineEdit( false );
        labelLocation_6.SetIsNumberEditor( false );
        labelLocation_6.SetNumberEditorRange( 0, 100 );
        labelLocation_6.SetNumberEditorInterval( 1 );
        labelLocation_6.SetNumberEditorUsesMouseWheel( false );
        labelLocation_6.SetHasCustomTextHoverColor( false );
        labelLocation_6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_6.SetFont( "Arial", 7, true, false );

        labelLocation_7 = new VoltageLabel( "labelLocation_7", "Location Mark_7", this, "L (m)" );
        AddComponent( labelLocation_7 );
        labelLocation_7.SetWantsMouseNotifications( false );
        labelLocation_7.SetPosition( 8, 228 );
        labelLocation_7.SetSize( 41, 14 );
        labelLocation_7.SetEditable( false, false );
        labelLocation_7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_7.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_7.SetBorderSize( 1 );
        labelLocation_7.SetMultiLineEdit( false );
        labelLocation_7.SetIsNumberEditor( false );
        labelLocation_7.SetNumberEditorRange( 0, 100 );
        labelLocation_7.SetNumberEditorInterval( 1 );
        labelLocation_7.SetNumberEditorUsesMouseWheel( false );
        labelLocation_7.SetHasCustomTextHoverColor( false );
        labelLocation_7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_7.SetFont( "Arial", 7, true, false );

        labelLocation_8 = new VoltageLabel( "labelLocation_8", "Location Mark_8", this, "L (m)" );
        AddComponent( labelLocation_8 );
        labelLocation_8.SetWantsMouseNotifications( false );
        labelLocation_8.SetPosition( 68, 228 );
        labelLocation_8.SetSize( 41, 14 );
        labelLocation_8.SetEditable( false, false );
        labelLocation_8.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_8.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_8.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_8.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_8.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_8.SetBorderSize( 1 );
        labelLocation_8.SetMultiLineEdit( false );
        labelLocation_8.SetIsNumberEditor( false );
        labelLocation_8.SetNumberEditorRange( 0, 100 );
        labelLocation_8.SetNumberEditorInterval( 1 );
        labelLocation_8.SetNumberEditorUsesMouseWheel( false );
        labelLocation_8.SetHasCustomTextHoverColor( false );
        labelLocation_8.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_8.SetFont( "Arial", 7, true, false );

        labelLocation_9 = new VoltageLabel( "labelLocation_9", "Location Mark_9", this, "R" );
        AddComponent( labelLocation_9 );
        labelLocation_9.SetWantsMouseNotifications( false );
        labelLocation_9.SetPosition( 8, 278 );
        labelLocation_9.SetSize( 41, 14 );
        labelLocation_9.SetEditable( false, false );
        labelLocation_9.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_9.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_9.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_9.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_9.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_9.SetBorderSize( 1 );
        labelLocation_9.SetMultiLineEdit( false );
        labelLocation_9.SetIsNumberEditor( false );
        labelLocation_9.SetNumberEditorRange( 0, 100 );
        labelLocation_9.SetNumberEditorInterval( 1 );
        labelLocation_9.SetNumberEditorUsesMouseWheel( false );
        labelLocation_9.SetHasCustomTextHoverColor( false );
        labelLocation_9.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_9.SetFont( "Arial", 7, true, false );

        labelLocation_10 = new VoltageLabel( "labelLocation_10", "Location Mark_10", this, "R" );
        AddComponent( labelLocation_10 );
        labelLocation_10.SetWantsMouseNotifications( false );
        labelLocation_10.SetPosition( 68, 278 );
        labelLocation_10.SetSize( 41, 14 );
        labelLocation_10.SetEditable( false, false );
        labelLocation_10.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_10.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_10.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_10.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_10.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_10.SetBorderSize( 1 );
        labelLocation_10.SetMultiLineEdit( false );
        labelLocation_10.SetIsNumberEditor( false );
        labelLocation_10.SetNumberEditorRange( 0, 100 );
        labelLocation_10.SetNumberEditorInterval( 1 );
        labelLocation_10.SetNumberEditorUsesMouseWheel( false );
        labelLocation_10.SetHasCustomTextHoverColor( false );
        labelLocation_10.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_10.SetFont( "Arial", 7, true, false );

        labelLocation_12 = new VoltageLabel( "labelLocation_12", "Location Mark_12", this, "8bit" );
        AddComponent( labelLocation_12 );
        labelLocation_12.SetWantsMouseNotifications( false );
        labelLocation_12.SetPosition( 38, 285 );
        labelLocation_12.SetSize( 41, 14 );
        labelLocation_12.SetEditable( false, false );
        labelLocation_12.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation_12.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation_12.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation_12.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation_12.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation_12.SetBorderSize( 1 );
        labelLocation_12.SetMultiLineEdit( false );
        labelLocation_12.SetIsNumberEditor( false );
        labelLocation_12.SetNumberEditorRange( 0, 100 );
        labelLocation_12.SetNumberEditorInterval( 1 );
        labelLocation_12.SetNumberEditorUsesMouseWheel( false );
        labelLocation_12.SetHasCustomTextHoverColor( false );
        labelLocation_12.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation_12.SetFont( "Arial", 7, true, false );
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

        return super.GetTooltipText( component );

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
    private VoltageLabel labelLocation_12;
    private VoltageLabel labelLocation_10;
    private VoltageLabel labelLocation_9;
    private VoltageLabel labelLocation_8;
    private VoltageLabel labelLocation_7;
    private VoltageLabel labelLocation_6;
    private VoltageLabel labelLocation_5;
    private VoltageLabel labelLocation_4;
    private VoltageLabel labelLocation_3;
    private VoltageLabel labelLocation_2;
    private VoltageLabel labelLocation_1;
    private VoltageKnob knobPreDelay;
    private VoltageKnob knobAMP;
    private VoltageKnob knobGain;
    private VoltageSwitch switchADDA;
    private VoltageAudioJack outputRight;
    private VoltageAudioJack outputLeft;
    private VoltageAudioJack inputRight;
    private VoltageAudioJack inputLeft;
    private VoltageKnob knobMix;
    private VoltageKnob knobDamp;
    private VoltageKnob knobDecay;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel labelLocation_11;


    //[user-code-and-variables]    Add your own variables and functions here
    //[/user-code-and-variables]





}

 