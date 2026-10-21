package com.insectlabs.hgd;


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


public class hgd extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public hgd( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "hgd - High Gain Device", ModuleType.ModuleType_Effect, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "fbe4ac534c004ea8b96ce5da48b2649b" );
    }

void InitializeControls()
{

        labelGain = new VoltageLabel( "labelGain", "Gain Label", this, "GAIN" );
        AddComponent( labelGain );
        labelGain.SetWantsMouseNotifications( false );
        labelGain.SetPosition( 0, 0 );
        labelGain.SetSize( 115, 15 );
        labelGain.SetEditable( false, false );
        labelGain.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGain.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGain.SetColor( new Color( 164, 34, 34, 255 ) );
        labelGain.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelGain.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelGain.SetBorderSize( 1 );
        labelGain.SetMultiLineEdit( false );
        labelGain.SetIsNumberEditor( false );
        labelGain.SetNumberEditorRange( 0, 100 );
        labelGain.SetNumberEditorInterval( 1 );
        labelGain.SetNumberEditorUsesMouseWheel( false );
        labelGain.SetHasCustomTextHoverColor( false );
        labelGain.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelGain.SetFont( "Arial Black", 12, false, false );

        textLabel6 = new VoltageLabel( "textLabel6", "O Voicing Label", this, "—————— MT ——————" );
        AddComponent( textLabel6 );
        textLabel6.SetWantsMouseNotifications( false );
        textLabel6.SetPosition( 0, 280 );
        textLabel6.SetSize( 115, 17 );
        textLabel6.SetEditable( false, false );
        textLabel6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel6.SetColor( new Color( 164, 34, 34, 255 ) );
        textLabel6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel6.SetBorderSize( 1 );
        textLabel6.SetMultiLineEdit( false );
        textLabel6.SetIsNumberEditor( false );
        textLabel6.SetNumberEditorRange( 0, 100 );
        textLabel6.SetNumberEditorInterval( 1 );
        textLabel6.SetNumberEditorUsesMouseWheel( false );
        textLabel6.SetHasCustomTextHoverColor( false );
        textLabel6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel6.SetFont( "Arial Black", 12, false, false );

        textLabel5 = new VoltageLabel( "textLabel5", "D Voicing Label", this, "—————— HM ——————" );
        AddComponent( textLabel5 );
        textLabel5.SetWantsMouseNotifications( false );
        textLabel5.SetPosition( 0, 272 );
        textLabel5.SetSize( 115, 17 );
        textLabel5.SetEditable( false, false );
        textLabel5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel5.SetColor( new Color( 164, 34, 34, 255 ) );
        textLabel5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel5.SetBorderSize( 1 );
        textLabel5.SetMultiLineEdit( false );
        textLabel5.SetIsNumberEditor( false );
        textLabel5.SetNumberEditorRange( 0, 100 );
        textLabel5.SetNumberEditorInterval( 1 );
        textLabel5.SetNumberEditorUsesMouseWheel( false );
        textLabel5.SetHasCustomTextHoverColor( false );
        textLabel5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel5.SetFont( "Arial Black", 12, false, false );

        textLabel4 = new VoltageLabel( "textLabel4", "Re Voicing Label", this, "—————— FZ ——————" );
        AddComponent( textLabel4 );
        textLabel4.SetWantsMouseNotifications( false );
        textLabel4.SetPosition( 0, 263 );
        textLabel4.SetSize( 115, 17 );
        textLabel4.SetEditable( false, false );
        textLabel4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel4.SetColor( new Color( 164, 34, 34, 255 ) );
        textLabel4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel4.SetBorderSize( 1 );
        textLabel4.SetMultiLineEdit( false );
        textLabel4.SetIsNumberEditor( false );
        textLabel4.SetNumberEditorRange( 0, 100 );
        textLabel4.SetNumberEditorInterval( 1 );
        textLabel4.SetNumberEditorUsesMouseWheel( false );
        textLabel4.SetHasCustomTextHoverColor( false );
        textLabel4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel4.SetFont( "Arial Black", 12, false, false );

        textLabel3 = new VoltageLabel( "textLabel3", "C Voicing Label", this, "—————— DS ——————" );
        AddComponent( textLabel3 );
        textLabel3.SetWantsMouseNotifications( false );
        textLabel3.SetPosition( 0, 255 );
        textLabel3.SetSize( 115, 17 );
        textLabel3.SetEditable( false, false );
        textLabel3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel3.SetColor( new Color( 164, 34, 34, 255 ) );
        textLabel3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel3.SetBorderSize( 1 );
        textLabel3.SetMultiLineEdit( false );
        textLabel3.SetIsNumberEditor( false );
        textLabel3.SetNumberEditorRange( 0, 100 );
        textLabel3.SetNumberEditorInterval( 1 );
        textLabel3.SetNumberEditorUsesMouseWheel( false );
        textLabel3.SetHasCustomTextHoverColor( false );
        textLabel3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel3.SetFont( "Arial Black", 12, false, false );

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 342 );
        imageTTW.SetSize( 41, 13 );
        imageTTW.SetCurrentImage( "image image(3).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "hgd" );
        AddComponent( labelNumber );
        labelNumber.SetWantsMouseNotifications( false );
        labelNumber.SetPosition( 9, 176 );
        labelNumber.SetSize( 25, 13 );
        labelNumber.SetEditable( false, false );
        labelNumber.SetJustificationFlags( VoltageLabel.Justification.Right );
        labelNumber.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNumber.SetColor( new Color( 166, 36, 36, 255 ) );
        labelNumber.SetBkColor( new Color( 26, 42, 58, 255 ) );
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

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(r)aza (k)raft" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 1, 163 );
        labelLocation.SetSize( 41, 14 );
        labelLocation.SetEditable( false, false );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation.SetColor( new Color( 164, 34, 34, 255 ) );
        labelLocation.SetBkColor( new Color( 26, 42, 58, 255 ) );
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

        knobGain1 = new VoltageKnob( "knobGain1", "Gain 1", this, 0.0, 1.0, 0.0 );
        AddComponent( knobGain1 );
        knobGain1.SetWantsMouseNotifications( false );
        knobGain1.SetPosition( 34, 15 );
        knobGain1.SetSize( 48, 48 );
        knobGain1.SetSkin( "ARP2500 Large Black" );
        knobGain1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobGain1.SetKnobParams( 215, 145 );
        knobGain1.DisplayValueInPercent( false );
        knobGain1.SetKnobAdjustsRing( true );

        knobGain2 = new VoltageKnob( "knobGain2", "Gain 2", this, 0.0, 1.0, 0.0 );
        AddComponent( knobGain2 );
        knobGain2.SetWantsMouseNotifications( false );
        knobGain2.SetPosition( 34, 70 );
        knobGain2.SetSize( 48, 48 );
        knobGain2.SetSkin( "ARP2500 Large Black" );
        knobGain2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobGain2.SetKnobParams( 215, 145 );
        knobGain2.DisplayValueInPercent( false );
        knobGain2.SetKnobAdjustsRing( true );

        knobGain3 = new VoltageKnob( "knobGain3", "Gain 3", this, 0.0, 1.0, 0.0 );
        AddComponent( knobGain3 );
        knobGain3.SetWantsMouseNotifications( false );
        knobGain3.SetPosition( 34, 125 );
        knobGain3.SetSize( 48, 48 );
        knobGain3.SetSkin( "ARP2500 Large Black" );
        knobGain3.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobGain3.SetKnobParams( 215, 145 );
        knobGain3.DisplayValueInPercent( false );
        knobGain3.SetKnobAdjustsRing( true );

        knobGain4 = new VoltageKnob( "knobGain4", "Gain 4", this, 0.0, 1.0, 0.0 );
        AddComponent( knobGain4 );
        knobGain4.SetWantsMouseNotifications( false );
        knobGain4.SetPosition( 34, 180 );
        knobGain4.SetSize( 48, 48 );
        knobGain4.SetSkin( "ARP2500 Large Black" );
        knobGain4.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobGain4.SetKnobParams( 215, 145 );
        knobGain4.DisplayValueInPercent( false );
        knobGain4.SetKnobAdjustsRing( true );

        knobTone1 = new VoltageKnob( "knobTone1", "Tone 1", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone1 );
        knobTone1.SetWantsMouseNotifications( false );
        knobTone1.SetPosition( 10, 30 );
        knobTone1.SetSize( 20, 20 );
        knobTone1.SetSkin( "TR Large" );
        knobTone1.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTone1.SetKnobParams( 215, 145 );
        knobTone1.DisplayValueInPercent( true );
        knobTone1.SetKnobAdjustsRing( true );

        knobThird1 = new VoltageKnob( "knobThird1", "Third Knob 1", this, 0.0, 1.0, 0.0 );
        AddComponent( knobThird1 );
        knobThird1.SetWantsMouseNotifications( false );
        knobThird1.SetPosition( 85, 30 );
        knobThird1.SetSize( 20, 20 );
        knobThird1.SetSkin( "TR Large" );
        knobThird1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobThird1.SetKnobParams( 215, 145 );
        knobThird1.DisplayValueInPercent( false );
        knobThird1.SetKnobAdjustsRing( true );

        knobThird2 = new VoltageKnob( "knobThird2", "Third Knob 2", this, 0.0, 1.0, 0.0 );
        AddComponent( knobThird2 );
        knobThird2.SetWantsMouseNotifications( false );
        knobThird2.SetPosition( 85, 86 );
        knobThird2.SetSize( 20, 20 );
        knobThird2.SetSkin( "TR Large" );
        knobThird2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobThird2.SetKnobParams( 215, 145 );
        knobThird2.DisplayValueInPercent( false );
        knobThird2.SetKnobAdjustsRing( true );

        knobThird3 = new VoltageKnob( "knobThird3", "Third Knob 3", this, -1.0, 1.0, 0.0 );
        AddComponent( knobThird3 );
        knobThird3.SetWantsMouseNotifications( false );
        knobThird3.SetPosition( 85, 140 );
        knobThird3.SetSize( 20, 20 );
        knobThird3.SetSkin( "TR Large" );
        knobThird3.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobThird3.SetKnobParams( 215, 145 );
        knobThird3.DisplayValueInPercent( false );
        knobThird3.SetKnobAdjustsRing( true );

        knobTone2 = new VoltageKnob( "knobTone2", "Tone 2", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone2 );
        knobTone2.SetWantsMouseNotifications( false );
        knobTone2.SetPosition( 10, 85 );
        knobTone2.SetSize( 20, 20 );
        knobTone2.SetSkin( "TR Large" );
        knobTone2.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTone2.SetKnobParams( 215, 145 );
        knobTone2.DisplayValueInPercent( true );
        knobTone2.SetKnobAdjustsRing( true );

        knobTone3 = new VoltageKnob( "knobTone3", "Tone 3", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone3 );
        knobTone3.SetWantsMouseNotifications( false );
        knobTone3.SetPosition( 10, 140 );
        knobTone3.SetSize( 20, 20 );
        knobTone3.SetSkin( "TR Large" );
        knobTone3.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTone3.SetKnobParams( 215, 145 );
        knobTone3.DisplayValueInPercent( true );
        knobTone3.SetKnobAdjustsRing( true );

        knobTone4 = new VoltageKnob( "knobTone4", "Tone 4", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone4 );
        knobTone4.SetWantsMouseNotifications( false );
        knobTone4.SetPosition( 10, 195 );
        knobTone4.SetSize( 20, 20 );
        knobTone4.SetSkin( "TR Large" );
        knobTone4.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTone4.SetKnobParams( 215, 145 );
        knobTone4.DisplayValueInPercent( true );
        knobTone4.SetKnobAdjustsRing( true );

        inputPre1 = new VoltageAudioJack( "inputPre1", "Input Pre-Amp 1", this, JackType.JackType_AudioInput );
        AddComponent( inputPre1 );
        inputPre1.SetWantsMouseNotifications( false );
        inputPre1.SetPosition( 3, 229 );
        inputPre1.SetSize( 25, 25 );
        inputPre1.SetSkin( "Jack Round 25px" );

        inputPre2 = new VoltageAudioJack( "inputPre2", "Input Pre-Amp 2", this, JackType.JackType_AudioInput );
        AddComponent( inputPre2 );
        inputPre2.SetWantsMouseNotifications( false );
        inputPre2.SetPosition( 31, 230 );
        inputPre2.SetSize( 25, 25 );
        inputPre2.SetSkin( "Jack Round 25px" );

        inputPre3 = new VoltageAudioJack( "inputPre3", "Input Pre-Amp 3", this, JackType.JackType_AudioInput );
        AddComponent( inputPre3 );
        inputPre3.SetWantsMouseNotifications( false );
        inputPre3.SetPosition( 59, 230 );
        inputPre3.SetSize( 25, 25 );
        inputPre3.SetSkin( "Jack Round 25px" );

        inputPre4 = new VoltageAudioJack( "inputPre4", "Input Pre-Amp 4", this, JackType.JackType_AudioInput );
        AddComponent( inputPre4 );
        inputPre4.SetWantsMouseNotifications( false );
        inputPre4.SetPosition( 87, 230 );
        inputPre4.SetSize( 25, 25 );
        inputPre4.SetSkin( "Jack Round 25px" );

        switch1 = new VoltageSwitch( "switch1", "Mode Selector Switch 1", this, 3 );
        AddComponent( switch1 );
        switch1.SetWantsMouseNotifications( false );
        switch1.SetPosition( 9, 260 );
        switch1.SetSize( 13, 34 );
        switch1.SetSkin( "4-State Slide Vertical" );

        switch2 = new VoltageSwitch( "switch2", "Mode Selector Switch 2", this, 2 );
        AddComponent( switch2 );
        switch2.SetWantsMouseNotifications( false );
        switch2.SetPosition( 37, 260 );
        switch2.SetSize( 13, 34 );
        switch2.SetSkin( "4-State Slide Vertical" );

        switch3 = new VoltageSwitch( "switch3", "Mode Selector Switch 3", this, 1 );
        AddComponent( switch3 );
        switch3.SetWantsMouseNotifications( false );
        switch3.SetPosition( 65, 260 );
        switch3.SetSize( 13, 34 );
        switch3.SetSkin( "4-State Slide Vertical" );

        switch4 = new VoltageSwitch( "switch4", "Mode Selector Switch 4", this, 0 );
        AddComponent( switch4 );
        switch4.SetWantsMouseNotifications( false );
        switch4.SetPosition( 93, 260 );
        switch4.SetSize( 13, 34 );
        switch4.SetSkin( "4-State Slide Vertical" );

        output1 = new VoltageAudioJack( "output1", "Output 1", this, JackType.JackType_AudioOutput );
        AddComponent( output1 );
        output1.SetWantsMouseNotifications( false );
        output1.SetPosition( 4, 300 );
        output1.SetSize( 25, 25 );
        output1.SetSkin( "Mini Jack 25px" );

        output2 = new VoltageAudioJack( "output2", "Output 2", this, JackType.JackType_AudioOutput );
        AddComponent( output2 );
        output2.SetWantsMouseNotifications( false );
        output2.SetPosition( 31, 300 );
        output2.SetSize( 25, 25 );
        output2.SetSkin( "Mini Jack 25px" );

        output3 = new VoltageAudioJack( "output3", "Output 3", this, JackType.JackType_AudioOutput );
        AddComponent( output3 );
        output3.SetWantsMouseNotifications( false );
        output3.SetPosition( 59, 300 );
        output3.SetSize( 25, 25 );
        output3.SetSkin( "Mini Jack 25px" );

        output4 = new VoltageAudioJack( "output4", "Output 4", this, JackType.JackType_AudioOutput );
        AddComponent( output4 );
        output4.SetWantsMouseNotifications( false );
        output4.SetPosition( 87, 300 );
        output4.SetSize( 25, 25 );
        output4.SetSkin( "Mini Jack 25px" );

        outputMix = new VoltageAudioJack( "outputMix", "Mix Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputMix );
        outputMix.SetWantsMouseNotifications( false );
        outputMix.SetPosition( 87, 328 );
        outputMix.SetSize( 25, 25 );
        outputMix.SetSkin( "Mini Jack 25px" );

        labelMix = new VoltageLabel( "labelMix", "Mix Output Label", this, "mix!" );
        AddComponent( labelMix );
        labelMix.SetWantsMouseNotifications( false );
        labelMix.SetPosition( 60, 335 );
        labelMix.SetSize( 25, 13 );
        labelMix.SetEditable( false, false );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.Right );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMix.SetColor( new Color( 166, 36, 36, 255 ) );
        labelMix.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMix.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMix.SetBorderSize( 1 );
        labelMix.SetMultiLineEdit( false );
        labelMix.SetIsNumberEditor( false );
        labelMix.SetNumberEditorRange( 0, 100 );
        labelMix.SetNumberEditorInterval( 1 );
        labelMix.SetNumberEditorUsesMouseWheel( false );
        labelMix.SetHasCustomTextHoverColor( false );
        labelMix.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMix.SetFont( "Courier New", 13, true, false );

        label1 = new VoltageLabel( "label1", "Channel Number 1", this, "1." );
        AddComponent( label1 );
        label1.SetWantsMouseNotifications( false );
        label1.SetPosition( 75, 55 );
        label1.SetSize( 25, 13 );
        label1.SetEditable( false, false );
        label1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label1.SetColor( new Color( 166, 36, 36, 255 ) );
        label1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label1.SetBorderSize( 1 );
        label1.SetMultiLineEdit( false );
        label1.SetIsNumberEditor( false );
        label1.SetNumberEditorRange( 0, 100 );
        label1.SetNumberEditorInterval( 1 );
        label1.SetNumberEditorUsesMouseWheel( false );
        label1.SetHasCustomTextHoverColor( false );
        label1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label1.SetFont( "Courier New", 13, true, false );

        label2 = new VoltageLabel( "label2", "Channel Number 2", this, "2." );
        AddComponent( label2 );
        label2.SetWantsMouseNotifications( false );
        label2.SetPosition( 75, 105 );
        label2.SetSize( 25, 13 );
        label2.SetEditable( false, false );
        label2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label2.SetColor( new Color( 166, 36, 36, 255 ) );
        label2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label2.SetBorderSize( 1 );
        label2.SetMultiLineEdit( false );
        label2.SetIsNumberEditor( false );
        label2.SetNumberEditorRange( 0, 100 );
        label2.SetNumberEditorInterval( 1 );
        label2.SetNumberEditorUsesMouseWheel( false );
        label2.SetHasCustomTextHoverColor( false );
        label2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label2.SetFont( "Courier New", 13, true, false );

        label3 = new VoltageLabel( "label3", "Channel Number 3", this, "3." );
        AddComponent( label3 );
        label3.SetWantsMouseNotifications( false );
        label3.SetPosition( 75, 160 );
        label3.SetSize( 25, 13 );
        label3.SetEditable( false, false );
        label3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label3.SetColor( new Color( 166, 36, 36, 255 ) );
        label3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label3.SetBorderSize( 1 );
        label3.SetMultiLineEdit( false );
        label3.SetIsNumberEditor( false );
        label3.SetNumberEditorRange( 0, 100 );
        label3.SetNumberEditorInterval( 1 );
        label3.SetNumberEditorUsesMouseWheel( false );
        label3.SetHasCustomTextHoverColor( false );
        label3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label3.SetFont( "Courier New", 13, true, false );

        label4 = new VoltageLabel( "label4", "Channel Number 4", this, "4." );
        AddComponent( label4 );
        label4.SetWantsMouseNotifications( false );
        label4.SetPosition( 75, 215 );
        label4.SetSize( 25, 13 );
        label4.SetEditable( false, false );
        label4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label4.SetColor( new Color( 166, 36, 36, 255 ) );
        label4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label4.SetBorderSize( 1 );
        label4.SetMultiLineEdit( false );
        label4.SetIsNumberEditor( false );
        label4.SetNumberEditorRange( 0, 100 );
        label4.SetNumberEditorInterval( 1 );
        label4.SetNumberEditorUsesMouseWheel( false );
        label4.SetHasCustomTextHoverColor( false );
        label4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label4.SetFont( "Courier New", 13, true, false );

        labelTone = new VoltageLabel( "labelTone", "Tone Label", this, "TONE" );
        AddComponent( labelTone );
        labelTone.SetWantsMouseNotifications( false );
        labelTone.SetPosition( 5, 16 );
        labelTone.SetSize( 31, 15 );
        labelTone.SetEditable( false, false );
        labelTone.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTone.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTone.SetColor( new Color( 164, 34, 34, 255 ) );
        labelTone.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTone.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTone.SetBorderSize( 1 );
        labelTone.SetMultiLineEdit( false );
        labelTone.SetIsNumberEditor( false );
        labelTone.SetNumberEditorRange( 0, 100 );
        labelTone.SetNumberEditorInterval( 1 );
        labelTone.SetNumberEditorUsesMouseWheel( false );
        labelTone.SetHasCustomTextHoverColor( false );
        labelTone.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTone.SetFont( "Arial Black", 9, false, false );

        labelVoiceControl = new VoltageLabel( "labelVoiceControl", "Voice Control Label", this, "III" );
        AddComponent( labelVoiceControl );
        labelVoiceControl.SetWantsMouseNotifications( false );
        labelVoiceControl.SetPosition( 79, 16 );
        labelVoiceControl.SetSize( 31, 15 );
        labelVoiceControl.SetEditable( false, false );
        labelVoiceControl.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVoiceControl.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVoiceControl.SetColor( new Color( 164, 34, 34, 255 ) );
        labelVoiceControl.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVoiceControl.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVoiceControl.SetBorderSize( 1 );
        labelVoiceControl.SetMultiLineEdit( false );
        labelVoiceControl.SetIsNumberEditor( false );
        labelVoiceControl.SetNumberEditorRange( 0, 100 );
        labelVoiceControl.SetNumberEditorInterval( 1 );
        labelVoiceControl.SetNumberEditorUsesMouseWheel( false );
        labelVoiceControl.SetHasCustomTextHoverColor( false );
        labelVoiceControl.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVoiceControl.SetFont( "Arial Black", 11, false, false );

        knobThird4 = new VoltageKnob( "knobThird4", "Third Knob 4", this, -1.0, 1.0, 0.0 );
        AddComponent( knobThird4 );
        knobThird4.SetWantsMouseNotifications( false );
        knobThird4.SetPosition( 85, 195 );
        knobThird4.SetSize( 20, 20 );
        knobThird4.SetSkin( "TR Large" );
        knobThird4.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobThird4.SetKnobParams( 215, 145 );
        knobThird4.DisplayValueInPercent( false );
        knobThird4.SetKnobAdjustsRing( true );
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
        // Controls exist now (InitializeControls ran in the constructor). Build lookup arrays, pull current values.
        inJacks = new VoltageAudioJack[] { inputPre1, inputPre2, inputPre3, inputPre4 };
        outJacks = new VoltageAudioJack[] { output1, output2, output3, output4 };
        gainKnobs = new VoltageKnob[] { knobGain1, knobGain2, knobGain3, knobGain4 };
        toneKnobs = new VoltageKnob[] { knobTone1, knobTone2, knobTone3, knobTone4 };
        thirdKnobs = new VoltageKnob[] { knobThird1, knobThird2, knobThird3, knobThird4 };
        modeSwitches = new VoltageSwitch[] { switch1, switch2, switch3, switch4 };
        for (int i = 0; i < CHANNELS; i++) {
            handleKnobChanged(gainKnobs[i], gainKnobs[i].GetValue());
            handleKnobChanged(toneKnobs[i], toneKnobs[i].GetValue());
            handleKnobChanged(thirdKnobs[i], thirdKnobs[i].GetValue());
            handleSwitchChanged(modeSwitches[i]);
        }
        core.reset();
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
        if (notification == ModuleNotifications.Knob_Changed) {
            handleKnobChanged(component, doubleValue);
        } else if (notification == ModuleNotifications.Switch_Changed) {
            handleSwitchChanged(component);
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
        if (inJacks == null) {
            return;                       // before Initialize: nothing wired yet
        }
        if (resumePending) {              // returning from host bypass: clear stale tails once
            resumePending = false;
            core.reset();
            java.util.Arrays.fill(wasConnected, false);
        }
        double sum = 0.0;
        for (int i = 0; i < CHANNELS; i++) {
            if (!inJacks[i].IsConnected()) {
                wasConnected[i] = false;  // unpatched input: channel is skipped and silent (also keeps HM hiss off unpatched jacks)
                outJacks[i].SetValue(0.0);
                continue;
            }
            if (!wasConnected[i]) {       // freshly patched: drop any stale tail
                wasConnected[i] = true;
                core.resetChannel(i);
            }
            double y = core.process(i, readSignal(inJacks[i]) * INV_FULL_SCALE_VOLTS);
            outJacks[i].SetValue(y * FULL_SCALE_VOLTS);
            sum += y;
        }
        outputMix.SetValue(HgdCore.MIX_SCALE * sum * FULL_SCALE_VOLTS);
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
        // Direct per-channel routing, no DSP; mix output is silent. Core is reset once on return.
        if (inJacks == null) {
            return;
        }
        for (int i = 0; i < CHANNELS; i++) {
            outJacks[i].SetValue(readSignal(inJacks[i]));
        }
        outputMix.SetValue(0.0);
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
        // Third knobs: unit and meaning follow the voice selected on that channel.
        for (int i = 0; i < CHANNELS; i++) {
            if (thirdKnobs != null && component == thirdKnobs[i]) {
                return thirdText(voiceForSwitch(modeSwitches[i]), thirdPos(i, thirdKnobs[i].GetValue()));
            }
        }
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
        // Typed entry on a Third knob arrives in the displayed unit; convert to the knob's own range, the base class applies it below.
        for (int i = 0; i < CHANNELS; i++) {
            if (thirdKnobs != null && component == thirdKnobs[i]) {
                int voice = voiceForSwitch(modeSwitches[i]);
                double position = thirdPosFromTyped(voice, newValue);
                newValue = thirdKnobValue(i, position);
                break;
            }
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
    private VoltageKnob knobThird4;
    private VoltageLabel labelVoiceControl;
    private VoltageLabel labelTone;
    private VoltageLabel label4;
    private VoltageLabel label3;
    private VoltageLabel label2;
    private VoltageLabel label1;
    private VoltageLabel labelMix;
    private VoltageAudioJack outputMix;
    private VoltageAudioJack output4;
    private VoltageAudioJack output3;
    private VoltageAudioJack output2;
    private VoltageAudioJack output1;
    private VoltageSwitch switch4;
    private VoltageSwitch switch3;
    private VoltageSwitch switch2;
    private VoltageSwitch switch1;
    private VoltageAudioJack inputPre4;
    private VoltageAudioJack inputPre3;
    private VoltageAudioJack inputPre2;
    private VoltageAudioJack inputPre1;
    private VoltageKnob knobTone4;
    private VoltageKnob knobTone3;
    private VoltageKnob knobTone2;
    private VoltageKnob knobThird3;
    private VoltageKnob knobThird2;
    private VoltageKnob knobThird1;
    private VoltageKnob knobTone1;
    private VoltageKnob knobGain4;
    private VoltageKnob knobGain3;
    private VoltageKnob knobGain2;
    private VoltageKnob knobGain1;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel textLabel3;
    private VoltageLabel textLabel4;
    private VoltageLabel textLabel5;
    private VoltageLabel textLabel6;
    private VoltageLabel labelGain;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final int CHANNELS = 4;
    private static final double FULL_SCALE_VOLTS = 5.0;                 // core 1.0 = 5 V
    private static final double INV_FULL_SCALE_VOLTS = 1.0 / FULL_SCALE_VOLTS;
    // Slide-switch position -> voicing (0 DS, 1 FZ, 2 HM, 3 MT). Position 0 is full down (author-confirmed), legend top to bottom is
    // DS, FZ, HM, MT, so position 3 = DS ... position 0 = MT. Defaults 3,2,1,0 give channels 1..4 = DS, FZ, HM, MT.
    private static final int[] POSITION_TO_VOICE = { 3, 2, 1, 0 };
    private static final String[] VOICE_NAMES = { "DS", "FZ", "HM", "MT" };
    private static final String[] THIRD_NAMES = { "Push", "Modern - Vintage - Maestro starve", "Mid scoop", "Mid frequency" };

    private final HgdCore core = new HgdCore(48000.0);
    private VoltageAudioJack[] inJacks, outJacks;
    private VoltageKnob[] gainKnobs, toneKnobs, thirdKnobs;
    private VoltageSwitch[] modeSwitches;
    private final boolean[] wasConnected = new boolean[CHANNELS];     // audio thread
    private boolean resumePending = false;                             // audio thread only

    private static double readSignal(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double value = jack.GetValue();
        return Double.isFinite(value) ? value : 0.0;
    }

    // Panel ranges (set in Designer): Gain 0..1; Tone -1..+1 on all channels; Third knob 1 and 2 are 0..1 (DS Push, FZ), 3 and 4 are -1..+1 (HM, MT).
    // The core takes every control as a 0..1 position, so a knob value is normalised with its own channel's range.
    private static final double[] THIRD_MIN = { 0.0, 0.0, -1.0, -1.0 };
    private static final double[] THIRD_MAX = { 1.0, 1.0, 1.0, 1.0 };

    private static double fromBipolar(double v) {
        return (v + 1.0) * 0.5;
    }

    private static double thirdPos(int ch, double v) {
        double p = (v - THIRD_MIN[ch]) / (THIRD_MAX[ch] - THIRD_MIN[ch]);
        return p < 0.0 ? 0.0 : (p > 1.0 ? 1.0 : p);
    }

    private static double thirdKnobValue(int ch, double p) {
        return THIRD_MIN[ch] + p * (THIRD_MAX[ch] - THIRD_MIN[ch]);
    }

    private void handleKnobChanged(VoltageComponent component, double value) {
        for (int i = 0; i < CHANNELS; i++) {
            if (component == gainKnobs[i]) {
                core.setGain(i, value);
            } else if (component == toneKnobs[i]) {
                core.setTone(i, fromBipolar(value));
            } else if (component == thirdKnobs[i]) {
                core.setThird(i, thirdPos(i, value));
            }
        }
    }

    // Third knob: displayed unit per voicing (dB / percent / dB / Hz); typed entry is the inverse. Position p is 0..1.
    private static String thirdText(int voice, double p) {
        switch (voice) {
            case 0:  return String.format(java.util.Locale.ROOT, "DS Push: %.1f dB stage-1 gain (17 to 35 dB)", 20.0 * Math.log10(HgdCore.dsPushGain(p)));
            case 1:  return String.format(java.util.Locale.ROOT, "FZ character: %.0f%% (0 Modern, 50 Vintage, 100 Maestro starve)", p * 100.0);
            case 2:  return String.format(java.util.Locale.ROOT, "HM mids: %+.1f dB re as-built (-14 scoop to +10 fill)", 20.0 * Math.log10(HgdCore.hmMidWeight(p) / 0.5));
            default: return String.format(java.util.Locale.ROOT, "MT mid frequency: %.0f Hz (250 to 4000)", HgdCore.mtMidHz(p));
        }
    }

    private static double thirdPosFromTyped(int voice, double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return 0.5;
        }
        double p;
        switch (voice) {
            case 0:  p = Math.log(Math.pow(10.0, x / 20.0) / 7.0) / Math.log(8.0); break;
            case 1:  p = x / 100.0; break;
            case 2:  p = HgdCore.hmPosForWeight(0.5 * Math.pow(10.0, x / 20.0)); break;
            default: p = x > 0.0 ? Math.log(x / 250.0) / Math.log(16.0) : 0.0; break;
        }
        return p < 0.0 ? 0.0 : (p > 1.0 ? 1.0 : p);
    }

    private void handleSwitchChanged(VoltageComponent component) {
        for (int i = 0; i < CHANNELS; i++) {
            if (component == modeSwitches[i]) {
                core.setVoice(i, voiceForSwitch(modeSwitches[i]));
            }
        }
    }

    private static int voiceForSwitch(VoltageSwitch sw) {
        int pos = (int) Math.round(sw.GetValue());
        if (pos < 0) pos = 0; else if (pos > 3) pos = 3;
        return POSITION_TO_VOICE[pos];
    }

    // ---- HgdCore (framework-free; same source as HgdCore.java, tested headless) ----
    private static final class HgdCore {


    static final int CHANNELS = 4;
    static final int V_DS = 0, V_FZ = 1, V_HM = 2, V_MT = 3;
    static final double CEILING = 2.0;          // 10 V [MODEL, author decision]
    static final double MIX_SCALE = 0.5;
    static final double PEDAL_IN_VOLTS = 0.5;   // pedal volts per core unit [GUESS]
    static final double REF_PEAK = 1.0;
    static final double TARGET_TOP_DB = 6.0;    // [GUESS]
    static final double NOISE_DBV = -92.0;      // HM-2 voicing only, input-referred, volts rms at pedal level [GUESS, author approved]
    static final double MT_MID_DB = -9.0;       // fixed mid-band gain, MT voicing [MODEL]
    static final int TABLE_POINTS = 33;
    static final int OS = 4, L = 63, PH = 16;          // oversampling, FIR taps, taps per polyphase branch

    private final Channel[] ch = new Channel[CHANNELS];
    private static double[][] sharedTable;
    private static double sharedFs;

    HgdCore(double sampleRate) {
        double fs = (sampleRate > 8000.0 && sampleRate < 400000.0) ? sampleRate : 48000.0;   // NaN fails both tests
        double[][] t = table(fs);
        for (int i = 0; i < CHANNELS; i++) ch[i] = new Channel(fs, t, 0x9E3779B9L * (i + 1));
    }

    void setVoice(int c, int v) { if (ok(c)) ch[c].setVoice(v); }
    void setGain(int c, double g) { if (ok(c)) ch[c].gT = clamp01(g, 0.5); }
    void setTone(int c, double t) { if (ok(c)) ch[c].tT = clamp01(t, 0.5); }
    /** Third knob. DS: Push (stage-1 gain 17..35 dB). FZ: 0 Modern -> 0.5 Vintage -> 1 Maestro starve (gating).
     *  HM: mid weight (0 deep scoop, 0.5 as built, 1 filled in). MT: mid-band centre frequency 250 Hz..4 kHz. */
    void setThird(int c, double p) { if (ok(c)) ch[c].pT = clamp01(p, 0.5); }
    void reset() { for (int i = 0; i < CHANNELS; i++) ch[i].reset(); }
    void resetChannel(int c) { if (ok(c)) ch[c].reset(); }
    double process(int c, double in) { return ok(c) ? ch[c].process(in) : 0.0; }
    static double mix(double a, double b, double c, double d) { return MIX_SCALE * (a + b + c + d); }
    static double targetRmsDb(double g) { return TARGET_TOP_DB * g; }

    private static boolean ok(int c) { return c >= 0 && c < CHANNELS; }
    private static double clamp01(double v, double fb) {
        if (Double.isNaN(v) || Double.isInfinite(v)) return fb;
        return v < 0 ? 0 : (v > 1 ? 1 : v);
    }

    // ---- shaping helpers (all bounded, allocation-free) ----
    /** C1-continuous hard-ish clip: +/-1 for |u|>=1, slope 1.5 at 0. */
    static double cubic(double u) {
        if (u >= 1.0) return 1.0;
        if (u <= -1.0) return -1.0;
        return u * (1.5 - 0.5 * u * u);
    }
    /** Soft knee, approaches +/-1 slowly. */
    static double soft(double u) { return u / Math.sqrt(1.0 + u * u); }

    /** DS Push: stage-1 gain (linear) for third position p in 0..1: 7x (17 dB) .. 56x (35 dB). [DOC] */
    static double dsPushGain(double p) { return 7.0 * Math.pow(8.0, p); }
    /** MT mid-band centre frequency in Hz for third position p in 0..1. [MODEL] */
    static double mtMidHz(double p) { return 250.0 * Math.pow(16.0, p); }
    /** HM mid-band weight for third position p: 0 deep scoop (0.1), 0.5 pedal-as-built (0.5), 1 filled-in (1.6). [MODEL] */
    static double hmMidWeight(double p) { return p <= 0.5 ? 0.1 + 0.8 * p : 0.5 + 1.1 * (2.0 * p - 1.0); }
    static double hmPosForWeight(double w) { return w <= 0.5 ? (w - 0.1) / 0.8 : 0.5 + (w - 0.5) / 2.2; }

    static synchronized double[][] table(double fs) {
        if (sharedTable != null && sharedFs == fs) return sharedTable;
        double[][] t = new double[4][TABLE_POINTS];
        int warm = (int) (fs * 0.03), meas = (int) (fs * 0.06);
        for (int m = 0; m < 4; m++) {
            Channel c = new Channel(fs, null, 1L);
            c.setVoice(m);
            for (int k = 0; k < TABLE_POINTS; k++) {
                double g = k / (double) (TABLE_POINTS - 1);
                double want = 0.70710678 * Math.pow(10.0, targetRmsDb(g) / 20.0);
                double cp = 1.0;
                for (int it = 0; it < 4; it++) {
                    c.reset();
                    c.gT = g; c.gS = g; c.tT = 0.5; c.tS = 0.5; c.pT = 0.5; c.pS = 0.5;
                    c.fade = 1.0; c.calibrating = true; c.forcedComp = cp; c.noiseOn = false; c.lastG = -1; c.lastT = -1; c.lastP = -1;
                    double w = 2.0 * Math.PI * 1000.0 / fs, acc = 0;
                    for (int n = 0; n < warm + meas; n++) {
                        double y = c.process(REF_PEAK * Math.sin(w * n));
                        if (n >= warm) acc += y * y;
                    }
                    double rms = Math.sqrt(acc / meas);
                    if (rms > 1e-9) cp *= want / rms;
                }
                t[m][k] = cp;
            }
        }
        sharedTable = t; sharedFs = fs;
        return t;
    }

    // ---- one channel ----
    static final class Channel {
        final double fs, fs4;
        final double[][] tab;
        final double[] h = new double[L];
        final double[] xh = new double[PH];
        final double[] vh = new double[L];
        int xi = 0, vi = 0;
        int voice = 0;
        double gT = 0.5, tT = 0.5, pT = 0.5, gS = 0.5, tS = 0.5, pS = 0.5;
        double fade = 1.0, lastG = -1, lastT = -1, lastP = -1, comp = 1.0, forcedComp = 1.0;
        boolean calibrating = false, noiseOn = true;
        long rng;
        final double aSm, fadeStep, aDc;
        final double noiseAmp;

        // derived per-voicing parameters
        double gq = 7, gOp = 1, gF = 4, thrP = 1, thrN = 1, dzP = 0, dzN = 0, bMod = 0.5, gHm = 20, wL = 1, wH = 1, wM = 1, gMt2 = 1;
        double aMil = 0.5, bumpAmt = 0, aTilt1 = 0, aTilt2 = 0, tiltK = 0;
        double sLo = 1, sHi = 1;
        double b0 = 1, b1 = 0, b2 = 0, a1 = 0, a2 = 0;           // MT mid biquad (base rate)

        // fixed one-pole coefficients
        final double c33, c23, c72, c7k, c234, c1063, c1k, c500, c180, c2600, c100b, c3kb, c16, c100hz, c20, cNoise, c800;
        // state (os rate)
        double s33, s23, s72, s7k, sA, sB, sC, nz1, s16, s100;
        double s20, mil;
        double hm1, hm2, hm3, hm4;
        // state (base rate)
        double dcX, dcY, tLo, tHi, tA, tB, z1, z2, tn1, tn2;

        static double a(double fc, double fsr) { return 1.0 - Math.exp(-2.0 * Math.PI * fc / fsr); }

        Channel(double fs, double[][] tab, long seed) {
            this.fs = fs; this.fs4 = fs * OS; this.tab = tab;
            aSm = a(1.0 / (2.0 * Math.PI * 0.005), fs);
            fadeStep = 1.0 / (0.005 * fs);
            aDc = a(15.0, fs);
            rng = seed == 0 ? 1 : seed;
            noiseAmp = Math.pow(10.0, NOISE_DBV / 20.0) * Math.sqrt((double) OS) * Math.sqrt(3.0);  // uniform -> unit var, os-rate scaling
            c33 = a(33.0, fs4); c23 = a(23.0, fs4); c72 = a(72.0, fs4); c7k = a(7200.0, fs4);
            c16 = a(16.0, fs4); c100hz = a(106.0, fs4); c20 = a(20.0, fs4); cNoise = a(6000.0, fs4);
            c234 = a(234.0, fs); c1063 = a(1063.0, fs); c1k = a(1100.0, fs); c500 = a(500.0, fs);
            c180 = a(180.0, fs); c2600 = a(2600.0, fs); c100b = a(100.0, fs); c3kb = a(3000.0, fs); c800 = a(800.0, fs);
            // windowed-sinc low-pass at 0.9*Nyquist(base), 63 taps, Blackman, DC gain 1 at the os rate
            double fc = 0.45 / OS;                                  // cycles/sample at os rate
            int mid = L / 2; double sum = 0;
            for (int i = 0; i < L; i++) {
                double n = i - mid;
                double s = n == 0 ? 2.0 * fc : Math.sin(2.0 * Math.PI * fc * n) / (Math.PI * n);
                double w = 0.42 - 0.5 * Math.cos(2.0 * Math.PI * i / (L - 1)) + 0.08 * Math.cos(4.0 * Math.PI * i / (L - 1));
                h[i] = s * w; sum += h[i];
            }
            for (int i = 0; i < L; i++) h[i] /= sum;
            reset();
        }

        void setVoice(int v) {
            if (v < 0 || v > 3) v = 0;
            if (v != voice) { voice = v; clear(); fade = 0.0; lastG = -1; lastT = -1; lastP = -1; }
        }

        void clear() {
            java.util.Arrays.fill(xh, 0); java.util.Arrays.fill(vh, 0);
            s33 = s23 = s72 = s7k = sA = sB = sC = nz1 = s16 = s100 = s20 = mil = 0;
            hm1 = hm2 = hm3 = hm4 = 0;
            dcX = dcY = tLo = tHi = tA = tB = z1 = z2 = tn1 = tn2 = 0;
        }

        void reset() { clear(); gS = gT; tS = tT; pS = pT; fade = 1.0; lastG = lastT = lastP = -1; }

        double rnd() {                       // xorshift64*, uniform in (-1,1)
            rng ^= rng >>> 12; rng ^= rng << 25; rng ^= rng >>> 27;
            long r = rng * 0x2545F4914F6CDD1DL;
            return (r >> 11) * (1.0 / 4503599627370496.0);
        }

        void update() {
            if (Math.abs(gS - lastG) > 1e-5) {
                lastG = gS;
                switch (voice) {
                    case V_DS: gOp = 1.0 + 21.3 * gS * gS; break;                    // 1 + VR1/R13, 100k/4.7k [DOC]
                    case V_FZ: gF = 4.0 * Math.pow(62.5, gS); break;                 // Fuzz knob [MODEL]
                    case V_HM: gHm = 20.0 * Math.pow(30.0, gS); break;               // [GUESS] range
                    default:   gMt2 = 1.0 + 250.0 * gS * gS; break;                  // 1 + 250k/1k, VR01 [READ]
                }
                comp = (tab != null && !calibrating) ? interp(gS) : forcedComp;
            }
            if (Math.abs(tS - lastT) > 1e-5) {
                lastT = tS;
                double t = 2.0 * tS - 1.0;                                           // -1..+1
                switch (voice) {
                    case V_FZ: tiltK = Math.abs(t) * 0.85; break;                    // [DOC measurement] tilt, [MODEL] shape
                    case V_HM: wL = 0.3 + 1.4 * (1.0 - tS); wH = 0.3 + 1.4 * tS; break;   // [READ] Low/High colour mix
                    case V_MT: sLo = Math.pow(10.0, -15.0 * t / 20.0); sHi = Math.pow(10.0, 15.0 * t / 20.0); break; // [READ] Low/High pots
                    default: break;
                }
            }
            if (Math.abs(pS - lastP) > 1e-5) {
                lastP = pS;
                switch (voice) {
                    case V_DS: gq = dsPushGain(pS); break;                  // R9 22 ohm .. ~1k mod: 17..35 dB [DOC]
                    case V_FZ: {
                        bMod = pS < 0.5 ? pS * 2.0 : 1.0;                            // 0 Modern, 1 Vintage
                        double s = pS > 0.5 ? (pS - 0.5) * 2.0 : 0.0;                // Maestro starve
                        thrP = 1.0 + (0.45 - 1.0) * bMod;                            // [DOC measurement] top clips hard in Vintage
                        thrN = 1.0 + (1.5 - 1.0) * bMod;
                        dzP = 0.30 * s; dzN = 0.45 * s;                              // [MODEL] Maestro bias starvation, uneven edges
                        aMil = a(3500.0 + 4500.0 * bMod, fs4);                       // Miller roll-off, M mode lower [READ]
                        bumpAmt = 1.2 * (1.0 - bMod);                                // M-mode mid bump [DOC measurement]
                        break;
                    }
                    case V_HM: wM = hmMidWeight(pS); break;
                    default: {
                        double f0 = mtMidHz(pS), q = 0.9, gdb = MT_MID_DB;
                        double A = Math.pow(10.0, gdb / 40.0), w0 = 2.0 * Math.PI * Math.min(f0, 0.45 * fs) / fs;
                        double al = Math.sin(w0) / (2.0 * q), cw = Math.cos(w0);
                        double ia0 = 1.0 / (1.0 + al / A);
                        b0 = (1.0 + al * A) * ia0; b1 = -2.0 * cw * ia0; b2 = (1.0 - al * A) * ia0;
                        a1 = -2.0 * cw * ia0; a2 = (1.0 - al / A) * ia0;
                        break;
                    }
                }
            }
        }

        double interp(double g) {
            double p = g * (TABLE_POINTS - 1);
            int i = (int) p; if (i >= TABLE_POINTS - 1) i = TABLE_POINTS - 2;
            double f = p - i;
            return tab[voice][i] * (1 - f) + tab[voice][i + 1] * f;
        }

        double nl(double x) {            // x in pedal volts, os rate
            switch (voice) {
                case V_DS: {
                    s33 += c33 * (x - s33);
                    double v = (x - s33) * gq;                                       // Q2 booster, HP 33 Hz [DOC]
                    double y = v >= 0 ? 4.0 * soft(v / 4.0) : 3.4 * soft(v / 3.4);   // asymmetric rail saturation [DOC soft asym; values MODEL]
                    s23 += c23 * (y - s23);
                    double u = y - s23;                                              // op-amp input HP 23 Hz [DOC]
                    s72 += c72 * (u - s72);
                    double o = u + (gOp - 1.0) * (u - s72);                          // bass corner ~72 Hz at max [DOC]
                    double c = 0.7 * cubic(o / 0.7);                                 // 1N4148 pair [DOC]
                    s7k += c7k * (c - s7k);                                          // R14/C10 ~7.2 kHz [DOC]
                    return s7k;
                }
                case V_FZ: {
                    double u = x * gF;
                    double dz = u >= 0 ? dzP : dzN;
                    if (dz > 1e-9) u -= dz * cubic(u / dz);                          // starved bias: dead zone with smooth knee
                    double y = u >= 0 ? thrP * cubic(u / thrP) : thrN * cubic(u / thrN);
                    mil += aMil * (y - mil);                                         // Miller/HF roll-off [READ]
                    s20 += c20 * (mil - s20);
                    return mil - s20;                                                // DC block, os rate
                }
                case V_HM: {
                    double v = x;
                    if (noiseOn) {
                        nz1 += cNoise * (rnd() * noiseAmp - nz1);
                        v += nz1;
                    }
                    double u = v * gHm;
                    double a1c = 0.6 * cubic(u / 0.6);                               // op-amp stage, diodes in feedback [READ]
                    return 0.45 * soft(a1c / 0.45);                                  // D6/D7 + D8/D9 network [READ]
                }
                default: {
                    s100 += c100hz * (x - s100);
                    double u0 = (x - s100) * 10.0;                                   // 3b stage, HP ~106 Hz, ~+20 dB [READ]
                    s16 += c16 * (u0 - s16);
                    double u = u0 + (gMt2 - 1.0) * (u0 - s16);                       // 3a stage, Dist pot [READ]
                    double c = 0.55 * cubic(u / 0.55);                               // 1SS133 pair [READ]
                    double y = c * 6.0;                                              // 4b stage gain ~6 [GUESS]
                    return 4.2 * soft(y / 4.2);                                      // op-amp rail saturation [MODEL]
                }
            }
        }

        double tone(double y) {          // base rate
            switch (voice) {
                case V_DS: {
                    tLo += c234 * (y - tLo);
                    tHi = y - (tA += c1063 * (y - tA));                              // HP 1063 Hz
                    return ((1.0 - tS) * tLo + tS * tHi) * 4.0;                      // passive pair mixed by VR3 [DOC]
                }
                case V_FZ: {
                    double t = 2.0 * tS - 1.0, out;
                    if (t < 0) { tLo += c800 * (y - tLo); out = y * (1.0 - tiltK) + tLo * tiltK; }      // flat to ~800 Hz, then rolls off [DOC measurement]
                    else { tA += c3kb * (y - tA); out = y * (1.0 - tiltK) + (y - tA) * tiltK; }          // flat above ~2.5-3 kHz, rolls off below
                    out += bumpAmt * ((tB += c1k * (y - tB)) - (tn1 += c500 * (y - tn1)));              // Modern mid bump near 800 Hz
                    return out;
                }
                case V_HM: {
                    tLo += c180 * (y - tLo); tn1 += c180 * (tLo - tn1);              // 2-pole low band
                    tA += c2600 * (y - tA); tB += c2600 * (tA - tB);                 // 2-pole low-pass at 2.6 kHz
                    double lo = tn1, hi = y - tB, mid = y - lo - hi;
                    return wL * lo + wH * hi + wM * mid;
                }
                default: {
                    tLo += c100b * (y - tLo); double hiB = y - (tA += c3kb * (y - tA));
                    double s = y + (sLo - 1.0) * tLo + (sHi - 1.0) * hiB;            // Low/High shelves [READ]
                    double o = b0 * s + z1;                                          // mid peaking biquad (TDF2)
                    z1 = b1 * s - a1 * o + z2; z2 = b2 * s - a2 * o;
                    return o;
                }
            }
        }

        double process(double in) {
            if (Double.isNaN(in) || Double.isInfinite(in)) in = 0;
            else if (in > 10) in = 10; else if (in < -10) in = -10;
            gS += aSm * (gT - gS); tS += aSm * (tT - tS); pS += aSm * (pT - pS);
            update();
            xh[xi] = in * PEDAL_IN_VOLTS;
            // polyphase up (OS branches), run nl on each os-rate sample, push into FIR history
            for (int r = 0; r < OS; r++) {
                double u = 0;
                for (int j = 0, k = xi; j < PH; j++) {
                    int idx = j * OS + r;
                    if (idx < L) u += h[idx] * xh[k];
                    if (--k < 0) k = PH - 1;
                }
                vh[vi] = nl(u * OS);
                if (r < OS - 1) vi = (vi + 1) % L;
            }
            xi = (xi + 1) % PH;
            double y = 0;
            for (int k = 0, p = vi; k < L; k++) { y += h[k] * vh[p]; if (--p < 0) p = L - 1; }
            vi = (vi + 1) % L;
            double yd = y - dcX + (1.0 - aDc) * dcY; dcX = y; dcY = yd;
            double out = tone(yd) * (calibrating ? forcedComp : comp);
            out = CEILING * (CEILING == 0 ? 0 : sc(out / CEILING));
            if (fade < 1.0) { fade += fadeStep; if (fade > 1.0) fade = 1.0; out *= fade; }
            return out;
        }

        static double sc(double x) {
            if (x >= 3.0) return 1.0;
            if (x <= -3.0) return -1.0;
            double x2 = x * x;
            return x * (27.0 + x2) / (27.0 + 9.0 * x2);
        }
    }
    }
    //[/user-code-and-variables]
}

 
