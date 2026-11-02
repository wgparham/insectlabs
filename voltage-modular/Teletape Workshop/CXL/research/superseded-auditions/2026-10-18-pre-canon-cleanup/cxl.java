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
        SetSkin( "4955bc315f3846e186d5296da3036fbd" );
    }

void InitializeControls()
{

        labelLocation_11 = new VoltageLabel( "labelLocation_11", "Location Mark_11", this, "12bit" );
        AddComponent( labelLocation_11 );
        labelLocation_11.SetWantsMouseNotifications( false );
        labelLocation_11.SetPosition( 38, 245 );
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

        labelLocation_12 = new VoltageLabel( "labelLocation_12", "Location Mark_12", this, "8bit" );
        AddComponent( labelLocation_12 );
        labelLocation_12.SetWantsMouseNotifications( false );
        labelLocation_12.SetPosition( 38, 275 );
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

        decayKnob = new VoltageKnob( "decayKnob", "Decay Time", this, 0.0, 1.0, 0.5 );
        AddComponent( decayKnob );
        decayKnob.SetWantsMouseNotifications( false );
        decayKnob.SetPosition( 15, 110 );
        decayKnob.SetSize( 25, 25 );
        decayKnob.SetSkin( "ARP2500 Sm Blue" );
        decayKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        decayKnob.SetKnobParams( 215, 145 );
        decayKnob.DisplayValueInPercent( false );
        decayKnob.SetKnobAdjustsRing( true );

        dampingKnob = new VoltageKnob( "dampingKnob", "High Frequency Damping", this, 0.0, 1.0, 0.5 );
        AddComponent( dampingKnob );
        dampingKnob.SetWantsMouseNotifications( false );
        dampingKnob.SetPosition( 75, 60 );
        dampingKnob.SetSize( 25, 25 );
        dampingKnob.SetSkin( "ARP2500 Sm Gold" );
        dampingKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        dampingKnob.SetKnobParams( 215, 145 );
        dampingKnob.DisplayValueInPercent( false );
        dampingKnob.SetKnobAdjustsRing( true );

        mixKnob = new VoltageKnob( "mixKnob", "Dry/Wet Mix", this, 0.0, 1.0, 0.5 );
        AddComponent( mixKnob );
        mixKnob.SetWantsMouseNotifications( false );
        mixKnob.SetPosition( 75, 130 );
        mixKnob.SetSize( 25, 25 );
        mixKnob.SetSkin( "ARP2500 Sm Silver" );
        mixKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        mixKnob.SetKnobParams( 215, 145 );
        mixKnob.DisplayValueInPercent( false );
        mixKnob.SetKnobAdjustsRing( true );

        leftInput = new VoltageAudioJack( "leftInput", "Left/Mono Input", this, JackType.JackType_AudioInput );
        AddComponent( leftInput );
        leftInput.SetWantsMouseNotifications( false );
        leftInput.SetPosition( 15, 230 );
        leftInput.SetSize( 25, 25 );
        leftInput.SetSkin( "Jack Round 25px" );

        rightInput = new VoltageAudioJack( "rightInput", "Right Input", this, JackType.JackType_AudioInput );
        AddComponent( rightInput );
        rightInput.SetWantsMouseNotifications( false );
        rightInput.SetPosition( 15, 280 );
        rightInput.SetSize( 25, 25 );
        rightInput.SetSkin( "Jack Round 25px" );

        leftOutput = new VoltageAudioJack( "leftOutput", "Left/Mono Output", this, JackType.JackType_AudioOutput );
        AddComponent( leftOutput );
        leftOutput.SetWantsMouseNotifications( false );
        leftOutput.SetPosition( 75, 230 );
        leftOutput.SetSize( 25, 25 );
        leftOutput.SetSkin( "Mini Jack 25px" );

        rightOutput = new VoltageAudioJack( "rightOutput", "Right Output", this, JackType.JackType_AudioOutput );
        AddComponent( rightOutput );
        rightOutput.SetWantsMouseNotifications( false );
        rightOutput.SetPosition( 75, 280 );
        rightOutput.SetSize( 25, 25 );
        rightOutput.SetSkin( "Mini Jack 25px" );

        converterSwitch = new VoltageSwitch( "converterSwitch", "Converter Character", this, 1 );
        AddComponent( converterSwitch );
        converterSwitch.SetWantsMouseNotifications( false );
        converterSwitch.SetPosition( 45, 255 );
        converterSwitch.SetSize( 25, 25 );
        converterSwitch.SetSkin( "2-State Red Cap" );

        driveKnob = new VoltageKnob( "driveKnob", "Input Gain/Drive", this, 0.0, 1.0, 0.5 );
        AddComponent( driveKnob );
        driveKnob.SetWantsMouseNotifications( false );
        driveKnob.SetPosition( 15, 180 );
        driveKnob.SetSize( 25, 25 );
        driveKnob.SetSkin( "ARP2500 Sm Silver" );
        driveKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        driveKnob.SetKnobParams( 215, 145 );
        driveKnob.DisplayValueInPercent( false );
        driveKnob.SetKnobAdjustsRing( true );

        outputKnob = new VoltageKnob( "outputKnob", "Output Amplitude", this, 0.0, 1.0, 0.5 );
        AddComponent( outputKnob );
        outputKnob.SetWantsMouseNotifications( false );
        outputKnob.SetPosition( 75, 180 );
        outputKnob.SetSize( 25, 25 );
        outputKnob.SetSkin( "ARP2500 Sm Silver" );
        outputKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        outputKnob.SetKnobParams( 215, 145 );
        outputKnob.DisplayValueInPercent( false );
        outputKnob.SetKnobAdjustsRing( true );

        predelayKnob = new VoltageKnob( "predelayKnob", "Pre-Delay", this, 0.0, 1.0, 0.5 );
        AddComponent( predelayKnob );
        predelayKnob.SetWantsMouseNotifications( false );
        predelayKnob.SetPosition( 15, 60 );
        predelayKnob.SetSize( 25, 25 );
        predelayKnob.SetSkin( "ARP2500 Sm Blue" );
        predelayKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        predelayKnob.SetKnobParams( 215, 145 );
        predelayKnob.DisplayValueInPercent( false );
        predelayKnob.SetKnobAdjustsRing( true );

        labelLocation_1 = new VoltageLabel( "labelLocation_1", "Location Mark_1", this, "DECAY" );
        AddComponent( labelLocation_1 );
        labelLocation_1.SetWantsMouseNotifications( false );
        labelLocation_1.SetPosition( 8, 98 );
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
        labelLocation_2.SetPosition( 68, 48 );
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
        labelLocation_3.SetPosition( 8, 48 );
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
        labelLocation_4.SetPosition( 68, 118 );
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
        labelLocation_5.SetPosition( 68, 168 );
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
        labelLocation_6.SetPosition( 8, 168 );
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
        labelLocation_7.SetPosition( 8, 218 );
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
        labelLocation_8.SetPosition( 68, 218 );
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
        labelLocation_9.SetPosition( 8, 268 );
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
        labelLocation_10.SetPosition( 68, 268 );
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
        resetPending.set(true);
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
        if (notification == ModuleNotifications.Reset) resetPending.set(true);
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
        if (resetPending.get() && resetPending.getAndSet(false)) resetAudioState();
        if (wasBypassed) {
            updateControls(true);
            activeFade = 0.0;
            wasBypassed = false;
            controlCounter = 0;
        }
        if (controlCounter-- <= 0) {
            updateControls(false);
            controlCounter = 63;
        }
        drive += CONTROL_SMOOTH * (driveTarget - drive);
        outputGain += CONTROL_SMOOTH * (outputGainTarget - outputGain);
        mix += CONTROL_SMOOTH * (mixTarget - mix);
        predelaySamples += CONTROL_SMOOTH * (predelayTarget - predelaySamples);
        extendedMode += MODE_SMOOTH * (extendedTarget - extendedMode);
        activeFade = Math.min(1.0, activeFade + 1.0 / 240.0);

        boolean connectedLeft = leftInput.IsConnected();
        boolean connectedRight = rightInput.IsConnected();
        double dryLeft = readSignal(leftInput, connectedLeft);
        double dryRight = readSignal(rightInput, connectedRight);
        if (!connectedLeft && connectedRight) dryLeft = dryRight;
        else if (connectedLeft && !connectedRight) dryRight = dryLeft;

        // The extended-time voice keeps the 8-bit character, softened to an 8.5-bit
        // effective converter step as a middle ground between grain and tonal clarity.
        double inverseLevels = (1.0 - extendedMode) / 2047.0 + extendedMode / 180.0;
        double exciteLeft = leftExciter.process(dryLeft * (drive / 5.0));
        double exciteRight = rightExciter.process(dryRight * (drive / 5.0));
        exciteLeft = inputConverterLeft.process(exciteLeft, inverseLevels);
        exciteRight = inputConverterRight.process(exciteRight, inverseLevels);
        plate.process(leftPredelay.predelay(exciteLeft, predelaySamples),
                rightPredelay.predelay(exciteRight, predelaySamples));

        // Broad pickup weight followed by output bandwidth, entirely in the wet path.
        pickupLowLeft = cleanState(pickupLowLeft + 0.0181593 * (plate.wetLeft - pickupLowLeft));
        pickupLowRight = cleanState(pickupLowRight + 0.0181593 * (plate.wetRight - pickupLowRight));
        double pickupLeft = plate.wetLeft + 0.18 * pickupLowLeft;
        double pickupRight = plate.wetRight + 0.18 * pickupLowRight;
        pickupTopLeft = cleanState(pickupTopLeft + 0.591 * (pickupLeft - pickupTopLeft));
        pickupTopRight = cleanState(pickupTopRight + 0.591 * (pickupRight - pickupTopRight));
        double wetLeft = 5.0 * outputConverterLeft.process(pickupTopLeft, inverseLevels);
        double wetRight = 5.0 * outputConverterRight.process(pickupTopRight, inverseLevels);
        if (!Double.isFinite(wetLeft) || !Double.isFinite(wetRight)) {
            resetAudioState();
            wetLeft = wetRight = 0.0;
        }
        double resultLeft = outputGain * (dryLeft * (1.0 - mix) + wetLeft * mix * activeFade);
        double resultRight = outputGain * (dryRight * (1.0 - mix) + wetRight * mix * activeFade);
        // Average rather than double the common dry component when folding down.
        leftOutput.SetValue(rightOutput.IsConnected() ? resultLeft : 0.5 * (resultLeft + resultRight));
        rightOutput.SetValue(resultRight);
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
        wasBypassed = true;
        boolean connectedLeft = leftInput.IsConnected();
        boolean connectedRight = rightInput.IsConnected();
        double left = readSignal(leftInput, connectedLeft);
        double right = readSignal(rightInput, connectedRight);
        if (!connectedLeft && connectedRight) left = right;
        else if (connectedLeft && !connectedRight) right = left;
        leftOutput.SetValue(rightOutput.IsConnected() ? left : 0.5 * (left + right));
        rightOutput.SetValue(right);
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
        if (component == predelayKnob) return String.format(java.util.Locale.ROOT,
                "Pre-delay: %.2f ms; 0-250 ms, CCW zero; enter milliseconds",
                Math.pow(251.0, unit(predelayKnob.GetValue(), 0.5)) - 1.0);
        if (component == decayKnob) return String.format(java.util.Locale.ROOT,
                "Nominal 500 Hz decay: %.3f s; %s; enter seconds. Bass may last longer.",
                secondsForDecay(decayKnob.GetValue(), isExtended()),
                isExtended() ? "8-bit character (softened 8.5-bit step): 3-30 s" : "12-bit: 0.8-5 s");
        if (component == dampingKnob) return String.format(java.util.Locale.ROOT,
                "Damping: %.1f%%; CW shortens the treble tail; enter percent",
                100.0 * unit(dampingKnob.GetValue(), 0.5));
        if (component == driveKnob) return String.format(java.util.Locale.ROOT,
                "Wet input drive: %.3fx; 0-4x, unity at noon; pushes excitation compression; enter gain multiplier",
                4.0 * Math.pow(unit(driveKnob.GetValue(), 0.5), 2.0));
        if (component == mixKnob) return String.format(java.util.Locale.ROOT,
                "Mix: %.1f%% wet; 0 dry, 100 send-only; enter percent",
                100.0 * unit(mixKnob.GetValue(), 0.5));
        if (component == outputKnob) return String.format(java.util.Locale.ROOT,
                "Output amplitude: %.3fx; 0-2x, unity at noon; affects dry and wet; enter gain multiplier",
                2.0 * unit(outputKnob.GetValue(), 0.5));
        if (component == converterSwitch) return isExtended()
                ? "DOWN: 8-bit character with softened 8.5-bit converter step; 3-30 s nominal decay; tail preserved on switching"
                : "UP: 12-bit plate, 0.8-5 s nominal decay; tail preserved on switching";
        if (component == leftInput || component == rightInput)
            return "Audio input; either input alone drives the full plate. Both patched: separate stereo injections into one shared tank. GAIN affects wet only.";
        if (component == leftOutput)
            return "L (mono): with no right-output cable, returns the average of both outputs; otherwise left stereo pickup. MIX and AMP apply.";
        if (component == rightOutput)
            return "Right stereo pickup; right alone remains the right pickup. Patch left alone for mono. MIX and AMP apply.";
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
        if (!Double.isFinite(newValue)) return;
        if (component == predelayKnob) {
            predelayKnob.SetValue(Math.log1p(Math.max(0.0, Math.min(250.0, newValue))) / Math.log(251.0));
            return;
        }
        if (component == decayKnob) {
            boolean extended = isExtended();
            double minimum = extended ? 3.0 : 0.8;
            double maximum = extended ? 30.0 : 5.0;
            double seconds = Math.max(minimum, Math.min(maximum, newValue));
            decayKnob.SetValue(Math.log(seconds / minimum) / Math.log(maximum / minimum));
            return;
        }
        if (component == dampingKnob || component == mixKnob) {
            ((VoltageKnob) component).SetValue(unit(newValue / 100.0, 0.5));
            return;
        }
        if (component == driveKnob) {
            driveKnob.SetValue(Math.sqrt(Math.max(0.0, Math.min(4.0, newValue)) / 4.0));
            return;
        }
        if (component == outputKnob) {
            outputKnob.SetValue(unit(newValue / 2.0, 0.5));
            return;
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
        resetPending.set(true);
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
    private VoltageKnob predelayKnob;
    private VoltageKnob outputKnob;
    private VoltageKnob driveKnob;
    private VoltageSwitch converterSwitch;
    private VoltageAudioJack rightOutput;
    private VoltageAudioJack leftOutput;
    private VoltageAudioJack rightInput;
    private VoltageAudioJack leftInput;
    private VoltageKnob mixKnob;
    private VoltageKnob dampingKnob;
    private VoltageKnob decayKnob;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel labelLocation_12;
    private VoltageLabel labelLocation_11;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE));
    private static final double MODE_SMOOTH = 1.0 - Math.exp(-1.0 / (0.025 * SAMPLE_RATE));
    private final java.util.concurrent.atomic.AtomicBoolean resetPending =
            new java.util.concurrent.atomic.AtomicBoolean(true);
    private final PlateTank plate = new PlateTank();
    private final Exciter leftExciter = new Exciter();
    private final Exciter rightExciter = new Exciter();
    private final DelayLine leftPredelay = new DelayLine(12004);
    private final DelayLine rightPredelay = new DelayLine(12004);
    private final SteppedConverter inputConverterLeft = new SteppedConverter(0x31a724b9);
    private final SteppedConverter inputConverterRight = new SteppedConverter(0x6a91f243);
    private final SteppedConverter outputConverterLeft = new SteppedConverter(0x5ba17d39);
    private final SteppedConverter outputConverterRight = new SteppedConverter(0x7e173ac5);
    private double drive = 1.0, driveTarget = 1.0;
    private double outputGain = 1.0, outputGainTarget = 1.0;
    private double mix = 0.5, mixTarget = 0.5;
    private double predelaySamples, predelayTarget;
    private double extendedMode, extendedTarget;
    private double activeFade;
    private double pickupLowLeft, pickupLowRight;
    private double pickupTopLeft, pickupTopRight;
    private int controlCounter;
    private boolean wasBypassed;

    private static double unit(double value, double fallback) {
        return Double.isFinite(value) ? Math.max(0.0, Math.min(1.0, value)) : fallback;
    }

    private static double readSignal(VoltageAudioJack jack, boolean connected) {
        if (!connected) return 0.0;
        double value = jack.GetValue();
        // Finite support extends far beyond modular audio; bound extreme input before squaring it.
        return Double.isFinite(value) ? Math.max(-1.0e6, Math.min(1.0e6, value)) : 0.0;
    }

    private boolean isExtended() {
        // The supplied two-state cap uses 1 for UP (12 bit) and 0 for DOWN (8 bit).
        return unit(converterSwitch.GetValue(), 1.0) < 0.5;
    }

    private static double secondsForDecay(double position, boolean extended) {
        return (extended ? 3.0 : 0.8) * Math.pow(extended ? 10.0 : 6.25, unit(position, 0.5));
    }

    private void updateControls(boolean snap) {
        boolean extended = isExtended();
        double decaySeconds = secondsForDecay(decayKnob.GetValue(), extended);
        double damping = unit(dampingKnob.GetValue(), 0.5);
        double gainPosition = unit(driveKnob.GetValue(), 0.5);
        double delayPosition = unit(predelayKnob.GetValue(), 0.5);
        driveTarget = 4.0 * gainPosition * gainPosition;
        outputGainTarget = 2.0 * unit(outputKnob.GetValue(), 0.5);
        mixTarget = unit(mixKnob.GetValue(), 0.5);
        predelayTarget = 48.0 * (Math.pow(251.0, delayPosition) - 1.0);
        extendedTarget = extended ? 1.0 : 0.0;
        plate.configure(decaySeconds, damping, snap);
        if (snap) {
            drive = driveTarget;
            outputGain = outputGainTarget;
            mix = mixTarget;
            predelaySamples = predelayTarget;
            extendedMode = extendedTarget;
        }
    }

    private void resetAudioState() {
        plate.clear();
        leftExciter.clear();
        rightExciter.clear();
        leftPredelay.clear();
        rightPredelay.clear();
        inputConverterLeft.reset();
        inputConverterRight.reset();
        outputConverterLeft.reset();
        outputConverterRight.reset();
        pickupLowLeft = pickupLowRight = pickupTopLeft = pickupTopRight = 0.0;
        controlCounter = 0;
        activeFade = 0.0;
        wasBypassed = false;
        updateControls(true);
    }

    private static double cleanState(double value) {
        return Math.abs(value) < 1.0e-20 ? 0.0 : value;
    }

    private static final class DelayLine {
        private final double[] data;
        private int writeIndex;

        DelayLine(int capacity) { data = new double[capacity]; }

        double read(double age) {
            // The write head is the next slot. Capacity is separate from requested delay.
            double position = writeIndex - age;
            if (position < 0.0) position += data.length;
            int index = (int) position;
            double fraction = position - index;
            int next = index + 1 == data.length ? 0 : index + 1;
            return data[index] + fraction * (data[next] - data[index]);
        }

        double tap(int age) {
            int index = writeIndex - age;
            if (index < 0) index += data.length;
            return data[index];
        }

        void push(double value) {
            data[writeIndex] = cleanState(value);
            if (++writeIndex == data.length) writeIndex = 0;
        }

        double process(double value, int age) {
            double delayed = tap(age);
            push(value);
            return delayed;
        }

        double predelay(double value, double age) {
            // A true zero-delay endpoint, including interpolation between zero and one sample.
            double delayed = age < 1.0 ? value + age * (tap(1) - value) : read(age);
            push(value);
            return delayed;
        }

        void clear() { java.util.Arrays.fill(data, 0.0); writeIndex = 0; }
    }

    private static final class AllPass {
        private final DelayLine line;
        private final int nominal;
        private final double coefficient;

        AllPass(int referenceLength, double coefficient, boolean modulated) {
            nominal = scaled(referenceLength);
            line = new DelayLine(nominal + (modulated ? 32 : 1));
            this.coefficient = coefficient;
        }

        double process(double input, double excursion) {
            double delayed = line.read(nominal + excursion);
            double output = delayed - coefficient * input;
            line.push(input + coefficient * output);
            return output;
        }

        double tap(int referenceAge) { return line.tap(scaled(referenceAge)); }
        void clear() { line.clear(); }
    }

    private static int scaled(int referenceSamples) {
        return (int) Math.round(referenceSamples * SAMPLE_RATE / 29761.0);
    }

    private static final class Exciter {
        private static final double HIGH_PASS = 1.0 - Math.exp(-2.0 * Math.PI * 45.0 / SAMPLE_RATE);
        private static final double PRESENCE = 1.0 - Math.exp(-2.0 * Math.PI * 1800.0 / SAMPLE_RATE);
        private static final double BANDWIDTH = 1.0 - Math.exp(-2.0 * Math.PI * 9500.0 / SAMPLE_RATE);
        private static final double ATTACK = 1.0 - Math.exp(-1.0 / (0.002 * SAMPLE_RATE));
        private static final double RELEASE = 1.0 - Math.exp(-1.0 / (0.080 * SAMPLE_RATE));
        private double low, presence, top, envelope;

        double process(double input) {
            low = cleanState(low + HIGH_PASS * (input - low));
            double highPassed = input - low;
            presence = cleanState(presence + PRESENCE * (highPassed - presence));
            double emphasized = highPassed + 0.4 * (highPassed - presence);
            top = cleanState(top + BANDWIDTH * (emphasized - top));
            double level = Math.abs(top);
            envelope = cleanState(envelope + (level > envelope ? ATTACK : RELEASE) * (level - envelope));
            double over = Math.max(0.0, envelope - 0.65);
            double compressed = top / Math.sqrt(1.0 + 2.0 * over * over);
            // Soft driver headroom, not a feedback-loop waveshaper or a measured EMT curve.
            return compressed / Math.sqrt(1.0 + 0.04 * compressed * compressed);
        }

        void clear() { low = presence = top = envelope = 0.0; }
    }

    private static final class SteppedConverter {
        private final int seed;
        private int randomState;

        SteppedConverter(int seed) { this.seed = seed; reset(); }
        void reset() { randomState = seed; }

        private double random() {
            int value = randomState;
            value ^= value << 13;
            value ^= value >>> 17;
            value ^= value << 5;
            randomState = value;
            return (value & 0xffffffffL) * (1.0 / 4294967296.0);
        }

        double process(double input, double inverseLevels) {
            double amplitude = Math.abs(input);
            if (amplitude < 1.0e-18) return 0.0;
            // Instantaneous binary gain ranges keep relative grain as the tail recedes.
            // This is a CXL model, not a bit-exact reconstruction of the Lexicon converter.
            double range = Math.scalb(1.0, Math.getExponent(amplitude) + 1);
            double step = range * inverseLevels;
            double dither = random() - random();
            return Math.rint(input / step + dither) * step;
        }
    }

    private static final class PlateTank {
        private final AllPass[] inputLeft = inputDiffusers();
        private final AllPass[] inputRight = inputDiffusers();
        private final AllPass earlyLeft = new AllPass(672, -0.7, true);
        private final AllPass earlyRight = new AllPass(908, -0.7, true);
        private final AllPass lateLeft = new AllPass(1800, 0.5, false);
        private final AllPass lateRight = new AllPass(2656, 0.5, false);
        private final int longLeftAge = scaled(4453), longRightAge = scaled(4217);
        private final int endLeftAge = scaled(3720), endRightAge = scaled(3163);
        private final DelayLine longLeft = new DelayLine(longLeftAge + 1);
        private final DelayLine longRight = new DelayLine(longRightAge + 1);
        private final DelayLine endLeft = new DelayLine(endLeftAge + 1);
        private final DelayLine endRight = new DelayLine(endRightAge + 1);
        private static final double HALF_LOOP_SECONDS = (672.0 + 908.0 + 1800.0 + 2656.0
                + 4453.0 + 4217.0 + 3720.0 + 3163.0) / (2.0 * 29761.0);
        private static final double COS_500 = Math.cos(2.0 * Math.PI * 500.0 / SAMPLE_RATE);
        private static final double PHASE_STEP = 2.0 * Math.PI * 0.23 / SAMPLE_RATE;
        private double gainLow, gainLowTarget, highRatio, highRatioTarget, pole, poleTarget;
        private double dampingLeft, dampingRight, feedbackLeft, feedbackRight;
        private double phaseSin, phaseCos = 1.0;
        private int phaseCounter;
        double wetLeft, wetRight;

        private static AllPass[] inputDiffusers() {
            return new AllPass[] {new AllPass(142, 0.75, false), new AllPass(107, 0.75, false),
                    new AllPass(379, 0.625, false), new AllPass(277, 0.625, false)};
        }

        void configure(double seconds, double damping, boolean snap) {
            double bassRatio = 1.05 + 0.85 * Math.max(0.0, Math.min(1.0, (seconds - 0.8) / 4.2));
            double trebleRatio = 0.55 * Math.pow(0.08 / 0.55, damping);
            double exponent = -Math.log(1000.0) * HALF_LOOP_SECONDS;
            gainLowTarget = Math.exp(exponent / (seconds * bassRatio));
            double midGain = Math.exp(exponent / seconds);
            highRatioTarget = Math.exp(exponent / (seconds * trebleRatio)) / gainLowTarget;
            double targetMagnitude = midGain / gainLowTarget;
            // Passive low/high shelf. Solve its pole so loss at 500 Hz matches the nominal
            // midband decay, rather than allowing DAMP to collapse that reference duration.
            double lowPole = 0.0, highPole = 0.9999999;
            for (int i = 0; i < 24; i++) {
                double candidate = 0.5 * (lowPole + highPole);
                double a = 1.0 - candidate * (1.0 - highRatioTarget);
                double b = highRatioTarget * candidate;
                double magnitudeSquared = (a * a + b * b - 2.0 * a * b * COS_500)
                        / (1.0 + candidate * candidate - 2.0 * candidate * COS_500);
                if (magnitudeSquared > targetMagnitude * targetMagnitude) lowPole = candidate;
                else highPole = candidate;
            }
            poleTarget = 0.5 * (lowPole + highPole);
            if (snap) { gainLow = gainLowTarget; highRatio = highRatioTarget; pole = poleTarget; }
        }

        void process(double left, double right) {
            gainLow += MODE_SMOOTH * (gainLowTarget - gainLow);
            highRatio += MODE_SMOOTH * (highRatioTarget - highRatio);
            pole += MODE_SMOOTH * (poleTarget - pole);
            for (int i = 0; i < inputLeft.length; i++) {
                left = inputLeft[i].process(left, 0.0);
                right = inputRight[i].process(right, 0.0);
            }
            // Both feedback samples belong to the previous frame: no lane-order bias.
            double earlyL = earlyLeft.process(0.5 * left + feedbackRight, 8.0 * phaseSin);
            double earlyR = earlyRight.process(0.5 * right + feedbackLeft, 8.0 * phaseCos);
            double longL = longLeft.process(earlyL, longLeftAge);
            double longR = longRight.process(earlyR, longRightAge);
            dampingLeft = cleanState((1.0 - pole) * longL + pole * dampingLeft);
            dampingRight = cleanState((1.0 - pole) * longR + pole * dampingRight);
            double lossL = gainLow * (highRatio * longL + (1.0 - highRatio) * dampingLeft);
            double lossR = gainLow * (highRatio * longR + (1.0 - highRatio) * dampingRight);
            feedbackLeft = endLeft.process(lateLeft.process(lossL, 0.0), endLeftAge);
            feedbackRight = endRight.process(lateRight.process(lossR, 0.0), endRightAge);
            // Distributed signed pickup taps. Ages scale with the paper's reference clock.
            wetLeft = 0.45 * (longRight.tap(scaled(266)) + longRight.tap(scaled(2974))
                    - lateRight.tap(1913) + endRight.tap(scaled(1996))
                    - longLeft.tap(scaled(1990)) - lateLeft.tap(187) - endLeft.tap(scaled(1066)));
            wetRight = 0.45 * (longLeft.tap(scaled(353)) + longLeft.tap(scaled(3627))
                    - lateLeft.tap(1228) + endLeft.tap(scaled(2673))
                    - longRight.tap(scaled(2111)) - lateRight.tap(335) - endRight.tap(scaled(121)));
            double nextSin = phaseSin + PHASE_STEP * phaseCos;
            phaseCos -= PHASE_STEP * nextSin;
            phaseSin = nextSin;
            if (++phaseCounter == 4096) {
                double inverseLength = 1.0 / Math.sqrt(phaseSin * phaseSin + phaseCos * phaseCos);
                phaseSin *= inverseLength;
                phaseCos *= inverseLength;
                phaseCounter = 0;
            }
        }

        void clear() {
            for (AllPass diffuser : inputLeft) diffuser.clear();
            for (AllPass diffuser : inputRight) diffuser.clear();
            earlyLeft.clear(); earlyRight.clear(); lateLeft.clear(); lateRight.clear();
            longLeft.clear(); longRight.clear(); endLeft.clear(); endRight.clear();
            dampingLeft = dampingRight = feedbackLeft = feedbackRight = wetLeft = wetRight = 0.0;
            phaseSin = 0.0; phaseCos = 1.0; phaseCounter = 0;
        }
    }
    //[/user-code-and-variables]
}

 