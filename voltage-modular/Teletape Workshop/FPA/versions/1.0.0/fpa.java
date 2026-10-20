package com.insectlabs.fpa;


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


public class fpa extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public fpa( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "fpa - Four Pre-Amps", ModuleType.ModuleType_Effect, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "8e3e490e65554aa7884111a8d6e62609" );
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

        textLabel6 = new VoltageLabel( "textLabel6", "O Voicing Label", this, "—————— O ——————" );
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

        textLabel5 = new VoltageLabel( "textLabel5", "D Voicing Label", this, "—————— D ——————" );
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

        textLabel4 = new VoltageLabel( "textLabel4", "Re Voicing Label", this, "—————— Re ——————" );
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

        textLabel3 = new VoltageLabel( "textLabel3", "C Voicing Label", this, "—————— C ——————" );
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

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "fpa" );
        AddComponent( labelNumber );
        labelNumber.SetWantsMouseNotifications( false );
        labelNumber.SetPosition( 9, 66 );
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

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(r)aza (k)raft" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 1, 55 );
        labelLocation.SetSize( 41, 14 );
        labelLocation.SetEditable( false, false );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation.SetColor( new Color( 164, 34, 34, 255 ) );
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
        knobGain4.DisplayValueInPercent( true );
        knobGain4.SetKnobAdjustsRing( true );

        knobTone1 = new VoltageKnob( "knobTone1", "Tone 1", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone1 );
        knobTone1.SetWantsMouseNotifications( false );
        knobTone1.SetPosition( 85, 30 );
        knobTone1.SetSize( 20, 20 );
        knobTone1.SetSkin( "TR Large" );
        knobTone1.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTone1.SetKnobParams( 215, 145 );
        knobTone1.DisplayValueInPercent( true );
        knobTone1.SetKnobAdjustsRing( true );

        knobTone2 = new VoltageKnob( "knobTone2", "Tone 2", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone2 );
        knobTone2.SetWantsMouseNotifications( false );
        knobTone2.SetPosition( 85, 85 );
        knobTone2.SetSize( 20, 20 );
        knobTone2.SetSkin( "TR Large" );
        knobTone2.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTone2.SetKnobParams( 215, 145 );
        knobTone2.DisplayValueInPercent( true );
        knobTone2.SetKnobAdjustsRing( true );

        knobTone3 = new VoltageKnob( "knobTone3", "Tone 3", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone3 );
        knobTone3.SetWantsMouseNotifications( false );
        knobTone3.SetPosition( 85, 140 );
        knobTone3.SetSize( 20, 20 );
        knobTone3.SetSkin( "TR Large" );
        knobTone3.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobTone3.SetKnobParams( 215, 145 );
        knobTone3.DisplayValueInPercent( true );
        knobTone3.SetKnobAdjustsRing( true );

        knobTone4 = new VoltageKnob( "knobTone4", "Tone 4", this, -1.0, 1.0, 0.0 );
        AddComponent( knobTone4 );
        knobTone4.SetWantsMouseNotifications( false );
        knobTone4.SetPosition( 85, 195 );
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
        labelTone.SetPosition( 80, 16 );
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

        buttonPhase1 = new VoltageToggle( "buttonPhase1", "Phase Button 1", this, false, 0 );
        AddComponent( buttonPhase1 );
        buttonPhase1.SetWantsMouseNotifications( false );
        buttonPhase1.SetPosition( 10, 30 );
        buttonPhase1.SetSize( 20, 20 );
        buttonPhase1.SetSkin( "2500 Square Big" );
        buttonPhase1.ShowOverlay( true );
        buttonPhase1.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase1.SetOverlayText( "" );
        buttonPhase1.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase1.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        buttonPhase2 = new VoltageToggle( "buttonPhase2", "Phase Button 2", this, false, 0 );
        AddComponent( buttonPhase2 );
        buttonPhase2.SetWantsMouseNotifications( false );
        buttonPhase2.SetPosition( 10, 85 );
        buttonPhase2.SetSize( 20, 20 );
        buttonPhase2.SetSkin( "2500 Square Big" );
        buttonPhase2.ShowOverlay( true );
        buttonPhase2.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase2.SetOverlayText( "" );
        buttonPhase2.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase2.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        buttonPhase3 = new VoltageToggle( "buttonPhase3", "Phase Button 3", this, false, 0 );
        AddComponent( buttonPhase3 );
        buttonPhase3.SetWantsMouseNotifications( false );
        buttonPhase3.SetPosition( 10, 140 );
        buttonPhase3.SetSize( 20, 20 );
        buttonPhase3.SetSkin( "2500 Square Big" );
        buttonPhase3.ShowOverlay( true );
        buttonPhase3.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase3.SetOverlayText( "" );
        buttonPhase3.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase3.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        buttonPhase4 = new VoltageToggle( "buttonPhase4", "Phase Button 4", this, false, 0 );
        AddComponent( buttonPhase4 );
        buttonPhase4.SetWantsMouseNotifications( false );
        buttonPhase4.SetPosition( 10, 195 );
        buttonPhase4.SetSize( 20, 20 );
        buttonPhase4.SetSkin( "2500 Square Big" );
        buttonPhase4.ShowOverlay( true );
        buttonPhase4.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase4.SetOverlayText( "" );
        buttonPhase4.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase4.SetOverlayTextJustification( VoltageButton.Justification.Centered );
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
        modeSwitches = new VoltageSwitch[] { switch1, switch2, switch3, switch4 };
        for (int i = 0; i < CHANNELS; i++) {
            handleKnobChanged(gainKnobs[i], gainKnobs[i].GetValue());
            handleKnobChanged(toneKnobs[i], toneKnobs[i].GetValue());
            handleSwitchChanged(modeSwitches[i]);
        }
        // Phase toggles are assumed up until a Button_Changed arrives (it is sent after Initialize on recall).
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
        } else if (notification == ModuleNotifications.Button_Changed) {
            handlePhaseChanged(component, doubleValue);
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
                wasConnected[i] = false;  // unpatched input = 0 -> exactly 0 out in every voicing (tested)
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
        outputMix.SetValue(PreampCore.MIX_SCALE * sum * FULL_SCALE_VOLTS);
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
    private VoltageToggle buttonPhase4;
    private VoltageToggle buttonPhase3;
    private VoltageToggle buttonPhase2;
    private VoltageToggle buttonPhase1;
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
    // Slide-switch position -> voicing (0 C, 1 Re, 2 D, 3 O). Defaults 3,2,1,0 give channels 1..4 = C,Re,D,O.
    // [OPEN] verify against the panel legend top-to-bottom; edit this table if the legend order differs.
    private static final int[] POSITION_TO_MODE = { 3, 2, 1, 0 };

    private final PreampCore core = new PreampCore(48000.0);
    private VoltageAudioJack[] inJacks, outJacks;
    private VoltageKnob[] gainKnobs, toneKnobs;
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

    private void handleKnobChanged(VoltageComponent component, double value) {
        for (int i = 0; i < CHANNELS; i++) {
            if (component == gainKnobs[i]) {
                core.setGain(i, value);
            } else if (component == toneKnobs[i]) {
                core.setTone(i, value);
            }
        }
    }

    private void handlePhaseChanged(VoltageComponent component, double value) {
        if (component == buttonPhase1) { core.setPhase(0, value > 0.5); }
        else if (component == buttonPhase2) { core.setPhase(1, value > 0.5); }
        else if (component == buttonPhase3) { core.setPhase(2, value > 0.5); }
        else if (component == buttonPhase4) { core.setPhase(3, value > 0.5); }
    }

    private void handleSwitchChanged(VoltageComponent component) {
        for (int i = 0; i < CHANNELS; i++) {
            if (component == modeSwitches[i]) {
                int pos = (int) Math.round(modeSwitches[i].GetValue());
                if (pos < 0) pos = 0; else if (pos > 3) pos = 3;
                core.setMode(i, POSITION_TO_MODE[pos]);
            }
        }
    }

    // ---- PreampCore (framework-free; same source as PreampCore.java, tested headless) ----
    private static final class PreampCore {


    static final int CHANNELS = 4;
    static final int MODE_C = 0, MODE_RE = 1, MODE_D = 2, MODE_O = 3;
    static final double CEILING = 2.0;          // 10 V [MODEL, author decision]
    static final double MIX_SCALE = 0.5;        // author: "at least .5"
    static final double REF_PEAK = 1.0;         // calibration sine, 5 V peak [MODEL]
    static final double TARGET_TOP_DB = 6.0;    // output RMS at GAIN=1 vs GAIN=0 [GUESS]
    static final int TABLE_POINTS = 33;

    private final double fs;
    private final Channel[] ch = new Channel[CHANNELS];
    private static double[][] sharedTable;
    private static double sharedTableFs;

    PreampCore(double sampleRate) {
        this.fs = (sampleRate > 8000.0 && sampleRate < 400000.0 && !Double.isNaN(sampleRate)) ? sampleRate : 48000.0;
        double[][] t = table(this.fs);
        for (int i = 0; i < CHANNELS; i++) ch[i] = new Channel(this.fs, t);
    }

    // ---- parameter setters (sanitized) ----
    void setMode(int c, int mode) { if (ok(c)) ch[c].setMode(mode); }
    void setGain(int c, double g) { if (ok(c)) ch[c].gainTarget = clamp01(g, 0.5); }
    void setTone(int c, double t) { if (ok(c)) ch[c].toneTarget = clampSigned(t, 0.0); }
    void setPhase(int c, boolean inv) { if (ok(c)) ch[c].polTarget = inv ? -1.0 : 1.0; }
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
    private static double clampSigned(double v, double fb) {
        if (Double.isNaN(v) || Double.isInfinite(v)) return fb;
        return v < -1.0 ? -1.0 : (v > 1.0 ? 1.0 : v);
    }

    // ---- shared helpers ----
    static double softClip(double x) {                  // same law as ChorusCore [MODEL]
        if (x >= 3.0) return 1.0;
        if (x <= -3.0) return -1.0;
        double x2 = x * x;
        return x * (27.0 + x2) / (27.0 + 9.0 * x2);
    }
    private static double soft(double u) { return u / Math.sqrt(1.0 + u * u); }

    /** Drive range per voicing [GUESS]. */
    static double driveFor(int mode, double g) {
        double lo, hi;
        switch (mode) {
            case MODE_C:  lo = 0.7; hi = 10.0; break;   // CE-1 boost: author-approved soft clip, x0.1..x10 in CED
            case MODE_RE: lo = 1.5; hi = 25.0; break;   // [READ] RE-201 mic amp ~ +44 dB open loop feel
            case MODE_D:  lo = 1.0; hi = 40.0; break;   // [READ] BD-2 dual gain stage + op-amp diode clipper
            default:      lo = 1.0; hi = 30.0; break;   // [READ] SD-1 op-amp stage, 1M drive pot
        }
        return lo * Math.pow(hi / lo, g);
    }

    static synchronized double[][] table(double fs) {
        if (sharedTable != null && sharedTableFs == fs) return sharedTable;
        double[][] t = new double[4][TABLE_POINTS];
        int warm = (int) (fs * 0.05), meas = (int) (fs * 0.1);
        for (int m = 0; m < 4; m++) {
            Channel c = new Channel(fs, null);
            c.setMode(m);
            for (int k = 0; k < TABLE_POINTS; k++) {
                double g = k / (double) (TABLE_POINTS - 1);
                double want = 0.70710678 * Math.pow(10.0, targetRmsDb(g) / 20.0);
                double cp = 1.0;
                for (int it = 0; it < 4; it++) {   // iterate: the output ceiling is inside the measured chain
                    c.reset();
                    c.gainTarget = g; c.gainS = g; c.toneTarget = 0.0; c.toneS = 0.0;
                    c.fade = 1.0; c.calibrating = true; c.forcedComp = cp; c.lastG = -1;
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
        sharedTable = t; sharedTableFs = fs;
        return t;
    }

    // ---- one channel ----
    static final class Channel {
        final double fs;
        final double[][] tab;
        // 2x halfband-ish FIR (windowed sinc, 23 taps), polyphase up / direct down
        static final int L = 23;
        final double[] h = new double[L];
        final double[] xh = new double[L];  // input history (base rate)
        final double[] vh = new double[L];  // processed history (2x rate)
        int xi = 0, vi = 0;
        // params
        int mode = 0; double gainTarget = 0.5, toneTarget = 0.0, polTarget = 1.0;
        double gainS = 0.5, toneS = 0.0, pol = 1.0, fade = 1.0, lastG = -1, lastT = -1;
        boolean calibrating = false; double forcedComp = 1.0;
        double drive = 1.0, comp = 1.0;
        // filters
        final double a2x, aDc, aSm, aPolSm, fadeStep;
        double lpPre = 0, dcY = 0, dcX = 0, lpT1 = 0, lpT2 = 0;
        double gLo = 1, gHi = 1, aT1 = 0, aT2 = 0;
        final double biasSlope;
        static final double BIAS = 0.3;

        Channel(double fs, double[][] tab) {
            this.fs = fs; this.tab = tab;
            double fs2 = fs * 2.0;
            a2x = 1.0 - Math.exp(-2.0 * Math.PI * 720.0 / fs2);       // SD-1 pre-emphasis corner [READ ~720 Hz]
            aDc = 1.0 - Math.exp(-2.0 * Math.PI * 12.0 / fs);          // DC block / output coupling [MODEL]
            aSm = 1.0 - Math.exp(-1.0 / (0.005 * fs));                 // 5 ms control smoothing [MODEL]
            aPolSm = 1.0 - Math.exp(-1.0 / (0.004 * fs));              // 4 ms polarity glide [MODEL]
            fadeStep = 1.0 / (0.005 * fs);
            double e = 1e-4;
            biasSlope = (softClip(BIAS + e) - softClip(BIAS - e)) / (2 * e);
            int mid = L / 2;
            double fc = 0.25 * 0.9;                                    // cutoff ~0.9 * (fs2/4) in cycles/sample of 2x rate
            double sum = 0;
            for (int i = 0; i < L; i++) {
                double n = i - mid;
                double s = n == 0 ? 2.0 * fc : Math.sin(2.0 * Math.PI * fc * n) / (Math.PI * n);
                double w = 0.42 - 0.5 * Math.cos(2.0 * Math.PI * i / (L - 1)) + 0.08 * Math.cos(4.0 * Math.PI * i / (L - 1));
                h[i] = s * w; sum += h[i];
            }
            for (int i = 0; i < L; i++) h[i] /= sum;                   // unity DC gain at 2x rate
            reset();
        }

        void setMode(int m) {
            if (m < 0 || m > 3) m = 0;
            if (m != mode) { mode = m; clearStates(); fade = 0.0; lastG = -1; lastT = -1; }
        }

        void clearStates() {
            java.util.Arrays.fill(xh, 0); java.util.Arrays.fill(vh, 0);
            lpPre = 0; dcY = 0; dcX = 0; lpT1 = 0; lpT2 = 0;
        }

        void reset() {
            clearStates();
            gainS = gainTarget; toneS = toneTarget; pol = polTarget; fade = 1.0; lastG = -1; lastT = -1;
        }

        void update() {
            if (Math.abs(gainS - lastG) > 1e-5) {
                lastG = gainS;
                drive = driveFor(mode, gainS);
                if (tab != null && !calibrating) {
                    double p = gainS * (TABLE_POINTS - 1);
                    int i = (int) p; if (i >= TABLE_POINTS - 1) i = TABLE_POINTS - 2;
                    double f = p - i;
                    comp = tab[mode][i] * (1 - f) + tab[mode][i + 1] * f;
                } else comp = forcedComp;
            }
            if (Math.abs(toneS - lastT) > 1e-5) {
                lastT = toneS;
                double t = toneS;                                       // -1..+1, 0 = flat for centered voicings
                switch (mode) {
                    case MODE_C:  // tilt, pivot 1 kHz, +/-6 dB [MODEL, author: tilt OK]
                        aT1 = 1.0 - Math.exp(-2.0 * Math.PI * 1000.0 / fs);
                        gLo = Math.pow(10.0, -6.0 * t / 20.0); gHi = Math.pow(10.0, 6.0 * t / 20.0); break;
                    case MODE_RE: // Baxandall-derived: bass shelf 100 Hz, treble shelf 3 kHz, opposite +/-10 dB [READ topology, GUESS corners]
                        aT1 = 1.0 - Math.exp(-2.0 * Math.PI * 100.0 / fs);
                        aT2 = 1.0 - Math.exp(-2.0 * Math.PI * 3000.0 / fs);
                        gLo = Math.pow(10.0, -10.0 * t / 20.0); gHi = Math.pow(10.0, 10.0 * t / 20.0); break;
                    case MODE_D:  // BD-2 tone: bass/treble tilt, pivot ~1.2 kHz, +/-10 dB [READ network, GUESS values]
                        aT1 = 1.0 - Math.exp(-2.0 * Math.PI * 1200.0 / fs);
                        gLo = Math.pow(10.0, -10.0 * t / 20.0); gHi = Math.pow(10.0, 10.0 * t / 20.0); break;
                    default:      // SD-1: low-pass 1.5 kHz .. 8 kHz-ish [GUESS]; center is geometric midpoint
                        double tonePosition = (toneS + 1.0) * 0.5;
                        double fcut = 1500.0 * Math.pow(8000.0 / 1500.0, tonePosition);
                        aT1 = 1.0 - Math.exp(-2.0 * Math.PI * fcut / fs);
                        gLo = 1; gHi = 1; break;
                }
            }
        }

        double nl(double x) {                                           // runs at 2x rate
            switch (mode) {
                case MODE_C:
                    return softClip(x * drive);
                case MODE_RE: {
                    double v = x * drive + BIAS;                        // asymmetric discrete stage [MODEL]
                    return (softClip(v) - softClip(BIAS)) / biasSlope * 0.6;
                }
                case MODE_D: {
                    double v = x * drive;                               // diode feedback clipper, soft knee [MODEL]
                    return 0.5 * soft(v / 0.5);
                }
                default: {
                    lpPre += a2x * (x - lpPre);                         // pre-emphasis: unity at LF, 1+drive at HF [READ]
                    double v = x + (drive - 1.0) * (x - lpPre);
                    // asymmetric clipper: 1 diode positive, 2 diodes negative [READ]
                    return v >= 0 ? 0.6 * soft(v / 0.6) : 1.0 * soft(v / 1.0);
                }
            }
        }

        double process(double in) {
            if (Double.isNaN(in) || Double.isInfinite(in)) in = 0;
            else if (in > 10) in = 10; else if (in < -10) in = -10;
            gainS += aSm * (gainTarget - gainS);
            toneS += aSm * (toneTarget - toneS);
            update();
            xh[xi] = in;
            // polyphase up: even taps -> sample 0, odd taps -> sample 1 (gain 2 for zero stuffing)
            double u0 = 0, u1 = 0;
            for (int j = 0, k = xi; 2 * j < L; j++) {
                u0 += h[2 * j] * xh[k];
                if (2 * j + 1 < L) u1 += h[2 * j + 1] * xh[k];
                if (--k < 0) k = L - 1;
            }
            xi = (xi + 1) % L;
            u0 *= 2.0; u1 *= 2.0;
            double v0 = nl(u0), v1 = nl(u1);
            vh[vi] = v0; vi = (vi + 1) % L;
            vh[vi] = v1;
            double y = 0;
            for (int k = 0, p = vi; k < L; k++) { y += h[k] * vh[p]; if (--p < 0) p = L - 1; }
            vi = (vi + 1) % L;
            // DC block (asymmetric voicings) [MODEL]
            double yd = y - dcX + (1.0 - aDc) * dcY; dcX = y; dcY = yd;  // 1-pole HP
            // tone
            double out;
            if (mode == MODE_O) { lpT1 += aT1 * (yd - lpT1); out = lpT1; }
            else if (mode == MODE_RE) {
                lpT1 += aT1 * (yd - lpT1); lpT2 += aT2 * (yd - lpT2);
                out = yd + (gLo - 1.0) * lpT1 + (gHi - 1.0) * (yd - lpT2);
            } else {
                lpT1 += aT1 * (yd - lpT1);
                out = lpT1 * gLo + (yd - lpT1) * gHi;
            }
            out *= comp;
            out = CEILING * softClip(out / CEILING);                    // hard-bounded to +/-10 V
            if (fade < 1.0) { fade += fadeStep; if (fade > 1.0) fade = 1.0; out *= fade; }
            pol += aPolSm * (polTarget - pol);
            if (Math.abs(polTarget - pol) < 1e-6) pol = polTarget;
            return out * pol;
        }
    }
    }
    //[/user-code-and-variables]
}

 
