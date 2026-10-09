package com.insectlabs.rm1010;


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


public class rm1010 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public rm1010( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "rm1010 - mixing amplifier", ModuleType.ModuleType_Mixers, 3.2 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "1a5f6d36bf97421886b9d20d36225c92" );
    }

void InitializeControls()
{

        labelBalance = new VoltageLabel( "labelBalance", "Label Balance", this, "BALANCE" );
        AddComponent( labelBalance );
        labelBalance.SetWantsMouseNotifications( false );
        labelBalance.SetPosition( 158, 25 );
        labelBalance.SetSize( 41, 20 );
        labelBalance.SetEditable( false, false );
        labelBalance.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBalance.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBalance.SetColor( new Color( 232, 232, 232, 255 ) );
        labelBalance.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBalance.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBalance.SetBorderSize( 1 );
        labelBalance.SetMultiLineEdit( false );
        labelBalance.SetIsNumberEditor( false );
        labelBalance.SetNumberEditorRange( 0, 100 );
        labelBalance.SetNumberEditorInterval( 1 );
        labelBalance.SetNumberEditorUsesMouseWheel( false );
        labelBalance.SetHasCustomTextHoverColor( false );
        labelBalance.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBalance.SetFont( "Times New Roman", 7, true, false );

        labelProcess = new VoltageLabel( "labelProcess", "Label Process", this, "PROCESS" );
        AddComponent( labelProcess );
        labelProcess.SetWantsMouseNotifications( false );
        labelProcess.SetPosition( 198, 25 );
        labelProcess.SetSize( 31, 20 );
        labelProcess.SetEditable( false, false );
        labelProcess.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelProcess.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelProcess.SetColor( new Color( 232, 232, 232, 255 ) );
        labelProcess.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelProcess.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelProcess.SetBorderSize( 1 );
        labelProcess.SetMultiLineEdit( false );
        labelProcess.SetIsNumberEditor( false );
        labelProcess.SetNumberEditorRange( 0, 100 );
        labelProcess.SetNumberEditorInterval( 1 );
        labelProcess.SetNumberEditorUsesMouseWheel( false );
        labelProcess.SetHasCustomTextHoverColor( false );
        labelProcess.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelProcess.SetFont( "Times New Roman", 7, true, false );

        labelGain = new VoltageLabel( "labelGain", "Label Gain", this, "GAIN" );
        AddComponent( labelGain );
        labelGain.SetWantsMouseNotifications( false );
        labelGain.SetPosition( 19, 25 );
        labelGain.SetSize( 41, 20 );
        labelGain.SetEditable( false, false );
        labelGain.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGain.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGain.SetColor( new Color( 232, 232, 232, 255 ) );
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
        labelGain.SetFont( "Times New Roman", 7, true, false );

        labelVolume = new VoltageLabel( "labelVolume", "Label Volume", this, "VOLUME" );
        AddComponent( labelVolume );
        labelVolume.SetWantsMouseNotifications( false );
        labelVolume.SetPosition( 57, 25 );
        labelVolume.SetSize( 41, 20 );
        labelVolume.SetEditable( false, false );
        labelVolume.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVolume.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVolume.SetColor( new Color( 232, 232, 232, 255 ) );
        labelVolume.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVolume.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVolume.SetBorderSize( 1 );
        labelVolume.SetMultiLineEdit( false );
        labelVolume.SetIsNumberEditor( false );
        labelVolume.SetNumberEditorRange( 0, 100 );
        labelVolume.SetNumberEditorInterval( 1 );
        labelVolume.SetNumberEditorUsesMouseWheel( false );
        labelVolume.SetHasCustomTextHoverColor( false );
        labelVolume.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVolume.SetFont( "Times New Roman", 7, true, false );

        output3 = new VoltageAudioJack( "output3", "Submix 3 Output", this, JackType.JackType_AudioOutput );
        AddComponent( output3 );
        output3.SetWantsMouseNotifications( false );
        output3.SetPosition( 165, 220 );
        output3.SetSize( 37, 37 );
        output3.SetSkin( "Rotated Half" );

        input4 = new VoltageAudioJack( "input4", "Input 4", this, JackType.JackType_AudioInput );
        AddComponent( input4 );
        input4.SetWantsMouseNotifications( false );
        input4.SetPosition( 62, 275 );
        input4.SetSize( 37, 37 );
        input4.SetSkin( "Dark Jack Straight" );

        linkInput = new VoltageAudioJack( "linkInput", "Link Input", this, JackType.JackType_AudioInput );
        AddComponent( linkInput );
        linkInput.SetWantsMouseNotifications( false );
        linkInput.SetPosition( 3, 250 );
        linkInput.SetSize( 25, 25 );
        linkInput.SetSkin( "Dark Jack Straight" );

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Label", this, "r." );
        AddComponent( manufacturerLabel );
        manufacturerLabel.SetWantsMouseNotifications( false );
        manufacturerLabel.SetPosition( 3, 337 );
        manufacturerLabel.SetSize( 20, 20 );
        manufacturerLabel.SetEditable( false, false );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        manufacturerLabel.SetBkColor( new Color( 51, 51, 51, 255 ) );
        manufacturerLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        manufacturerLabel.SetBorderSize( 2 );
        manufacturerLabel.SetMultiLineEdit( false );
        manufacturerLabel.SetIsNumberEditor( false );
        manufacturerLabel.SetNumberEditorRange( 0, 100 );
        manufacturerLabel.SetNumberEditorInterval( 1 );
        manufacturerLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerLabel.SetHasCustomTextHoverColor( false );
        manufacturerLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerLabel.SetFont( "Courier New", 13, true, false );

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Description Label", this, "mixing amplifier" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 0 );
        descriptionLabel.SetSize( 224, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        descriptionLabel.SetBkColor( new Color( 19, 19, 19, 0 ) );
        descriptionLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        descriptionLabel.SetBorderSize( 4 );
        descriptionLabel.SetMultiLineEdit( false );
        descriptionLabel.SetIsNumberEditor( false );
        descriptionLabel.SetNumberEditorRange( 0, 100 );
        descriptionLabel.SetNumberEditorInterval( 1 );
        descriptionLabel.SetNumberEditorUsesMouseWheel( false );
        descriptionLabel.SetHasCustomTextHoverColor( false );
        descriptionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        descriptionLabel.SetFont( "Courier New", 13, true, false );

        numberLabel = new VoltageLabel( "numberLabel", "Number Label", this, "rm1010" );
        AddComponent( numberLabel );
        numberLabel.SetWantsMouseNotifications( false );
        numberLabel.SetPosition( 177, 335 );
        numberLabel.SetSize( 50, 13 );
        numberLabel.SetEditable( false, false );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        numberLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        numberLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        numberLabel.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        numberLabel.SetBorderSize( 0 );
        numberLabel.SetMultiLineEdit( false );
        numberLabel.SetIsNumberEditor( false );
        numberLabel.SetNumberEditorRange( 0, 100 );
        numberLabel.SetNumberEditorInterval( 1 );
        numberLabel.SetNumberEditorUsesMouseWheel( false );
        numberLabel.SetHasCustomTextHoverColor( false );
        numberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        numberLabel.SetFont( "Courier New", 13, true, false );

        gain1 = new VoltageKnob( "gain1", "Gain 1", this, 0.0, 1.0, 0.0 );
        AddComponent( gain1 );
        gain1.SetWantsMouseNotifications( false );
        gain1.SetPosition( 20, 40 );
        gain1.SetSize( 35, 35 );
        gain1.SetSkin( "Cosmo Medium" );
        gain1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        gain1.SetKnobParams( 215, 145 );
        gain1.DisplayValueInPercent( true );
        gain1.SetKnobAdjustsRing( true );

        gain2 = new VoltageKnob( "gain2", "Gain 2", this, 0.0, 1.0, 0.0 );
        AddComponent( gain2 );
        gain2.SetWantsMouseNotifications( false );
        gain2.SetPosition( 20, 80 );
        gain2.SetSize( 35, 35 );
        gain2.SetSkin( "Cosmo Medium" );
        gain2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        gain2.SetKnobParams( 215, 145 );
        gain2.DisplayValueInPercent( true );
        gain2.SetKnobAdjustsRing( true );

        gain3 = new VoltageKnob( "gain3", "Gain 3", this, 0.0, 1.0, 0.0 );
        AddComponent( gain3 );
        gain3.SetWantsMouseNotifications( false );
        gain3.SetPosition( 20, 125 );
        gain3.SetSize( 35, 35 );
        gain3.SetSkin( "Cosmo Medium" );
        gain3.SetRange( 0.0, 1.0, 0.0, false, 0 );
        gain3.SetKnobParams( 215, 145 );
        gain3.DisplayValueInPercent( true );
        gain3.SetKnobAdjustsRing( true );

        gain4 = new VoltageKnob( "gain4", "Gain 4", this, 0.0, 1.0, 0.0 );
        AddComponent( gain4 );
        gain4.SetWantsMouseNotifications( false );
        gain4.SetPosition( 20, 165 );
        gain4.SetSize( 35, 35 );
        gain4.SetSkin( "Cosmo Medium" );
        gain4.SetRange( 0.0, 1.0, 0.0, false, 0 );
        gain4.SetKnobParams( 215, 145 );
        gain4.DisplayValueInPercent( true );
        gain4.SetKnobAdjustsRing( true );

        balance1 = new VoltageKnob( "balance1", "Balance 1", this, -1.0, 1.0, 0.0 );
        AddComponent( balance1 );
        balance1.SetWantsMouseNotifications( false );
        balance1.SetPosition( 160, 40 );
        balance1.SetSize( 35, 35 );
        balance1.SetSkin( "Cosmo Medium" );
        balance1.SetRange( -1.0, 1.0, 0.0, false, 0 );
        balance1.SetKnobParams( 215, 145 );
        balance1.DisplayValueInPercent( false );
        balance1.SetKnobAdjustsRing( true );

        volume1 = new VoltageKnob( "volume1", "Volume 1", this, 0.0, 1.0, 0.0 );
        AddComponent( volume1 );
        volume1.SetWantsMouseNotifications( false );
        volume1.SetPosition( 60, 40 );
        volume1.SetSize( 35, 35 );
        volume1.SetSkin( "Cosmo Medium" );
        volume1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        volume1.SetKnobParams( 215, 145 );
        volume1.DisplayValueInPercent( true );
        volume1.SetKnobAdjustsRing( true );

        balance2 = new VoltageKnob( "balance2", "Balance 2", this, -1.0, 1.0, 0.0 );
        AddComponent( balance2 );
        balance2.SetWantsMouseNotifications( false );
        balance2.SetPosition( 162, 82 );
        balance2.SetSize( 35, 35 );
        balance2.SetSkin( "Cosmo Medium" );
        balance2.SetRange( -1.0, 1.0, 0.0, false, 0 );
        balance2.SetKnobParams( 215, 145 );
        balance2.DisplayValueInPercent( false );
        balance2.SetKnobAdjustsRing( true );

        volume2 = new VoltageKnob( "volume2", "Volume 2", this, 0.0, 1.0, 0.0 );
        AddComponent( volume2 );
        volume2.SetWantsMouseNotifications( false );
        volume2.SetPosition( 60, 80 );
        volume2.SetSize( 35, 35 );
        volume2.SetSkin( "Cosmo Medium" );
        volume2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        volume2.SetKnobParams( 215, 145 );
        volume2.DisplayValueInPercent( true );
        volume2.SetKnobAdjustsRing( true );

        balance3 = new VoltageKnob( "balance3", "Balance 3", this, -1.0, 1.0, 0.0 );
        AddComponent( balance3 );
        balance3.SetWantsMouseNotifications( false );
        balance3.SetPosition( 162, 127 );
        balance3.SetSize( 35, 35 );
        balance3.SetSkin( "Cosmo Medium" );
        balance3.SetRange( -1.0, 1.0, 0.0, false, 0 );
        balance3.SetKnobParams( 215, 145 );
        balance3.DisplayValueInPercent( false );
        balance3.SetKnobAdjustsRing( true );

        volume3 = new VoltageKnob( "volume3", "Volume 3", this, 0.0, 1.0, 0.0 );
        AddComponent( volume3 );
        volume3.SetWantsMouseNotifications( false );
        volume3.SetPosition( 60, 125 );
        volume3.SetSize( 35, 35 );
        volume3.SetSkin( "Cosmo Medium" );
        volume3.SetRange( 0.0, 1.0, 0.0, false, 0 );
        volume3.SetKnobParams( 215, 145 );
        volume3.DisplayValueInPercent( true );
        volume3.SetKnobAdjustsRing( true );

        balance4 = new VoltageKnob( "balance4", "Balance 4", this, -1.0, 1.0, 0.0 );
        AddComponent( balance4 );
        balance4.SetWantsMouseNotifications( false );
        balance4.SetPosition( 162, 167 );
        balance4.SetSize( 35, 35 );
        balance4.SetSkin( "Cosmo Medium" );
        balance4.SetRange( -1.0, 1.0, 0.0, false, 0 );
        balance4.SetKnobParams( 215, 145 );
        balance4.DisplayValueInPercent( false );
        balance4.SetKnobAdjustsRing( true );

        volume4 = new VoltageKnob( "volume4", "Volume 4", this, 0.0, 1.0, 0.0 );
        AddComponent( volume4 );
        volume4.SetWantsMouseNotifications( false );
        volume4.SetPosition( 60, 165 );
        volume4.SetSize( 35, 35 );
        volume4.SetSkin( "Cosmo Medium" );
        volume4.SetRange( 0.0, 1.0, 0.0, false, 0 );
        volume4.SetKnobParams( 215, 145 );
        volume4.DisplayValueInPercent( true );
        volume4.SetKnobAdjustsRing( true );

        input1 = new VoltageAudioJack( "input1", "Input 1", this, JackType.JackType_AudioInput );
        AddComponent( input1 );
        input1.SetWantsMouseNotifications( false );
        input1.SetPosition( 22, 220 );
        input1.SetSize( 37, 37 );
        input1.SetSkin( "Dark Jack Straight" );

        input3 = new VoltageAudioJack( "input3", "Input 3", this, JackType.JackType_AudioInput );
        AddComponent( input3 );
        input3.SetWantsMouseNotifications( false );
        input3.SetPosition( 22, 275 );
        input3.SetSize( 37, 37 );
        input3.SetSkin( "Dark Jack Straight" );

        input2 = new VoltageAudioJack( "input2", "Input 2", this, JackType.JackType_AudioInput );
        AddComponent( input2 );
        input2.SetWantsMouseNotifications( false );
        input2.SetPosition( 62, 220 );
        input2.SetSize( 37, 37 );
        input2.SetSkin( "Dark Jack Straight" );

        switchRange1 = new VoltageSwitch( "switchRange1", "Switch Range 1", this, 1 );
        AddComponent( switchRange1 );
        switchRange1.SetWantsMouseNotifications( false );
        switchRange1.SetPosition( 102, 53 );
        switchRange1.SetSize( 51, 15 );
        switchRange1.SetSkin( "4-State Slide Horiz" );

        jackInput1Label = new VoltageLabel( "jackInput1Label", "Jack Input 1 Label", this, "I" );
        AddComponent( jackInput1Label );
        jackInput1Label.SetWantsMouseNotifications( false );
        jackInput1Label.SetPosition( 30, 255 );
        jackInput1Label.SetSize( 21, 20 );
        jackInput1Label.SetEditable( false, false );
        jackInput1Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        jackInput1Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        jackInput1Label.SetColor( new Color( 232, 232, 232, 255 ) );
        jackInput1Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        jackInput1Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        jackInput1Label.SetBorderSize( 1 );
        jackInput1Label.SetMultiLineEdit( false );
        jackInput1Label.SetIsNumberEditor( false );
        jackInput1Label.SetNumberEditorRange( 0, 100 );
        jackInput1Label.SetNumberEditorInterval( 1 );
        jackInput1Label.SetNumberEditorUsesMouseWheel( false );
        jackInput1Label.SetHasCustomTextHoverColor( false );
        jackInput1Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        jackInput1Label.SetFont( "Times New Roman", 13, true, false );

        jackInput2Label = new VoltageLabel( "jackInput2Label", "Jack Input 2 Label", this, "II" );
        AddComponent( jackInput2Label );
        jackInput2Label.SetWantsMouseNotifications( false );
        jackInput2Label.SetPosition( 70, 255 );
        jackInput2Label.SetSize( 21, 20 );
        jackInput2Label.SetEditable( false, false );
        jackInput2Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        jackInput2Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        jackInput2Label.SetColor( new Color( 232, 232, 232, 255 ) );
        jackInput2Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        jackInput2Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        jackInput2Label.SetBorderSize( 1 );
        jackInput2Label.SetMultiLineEdit( false );
        jackInput2Label.SetIsNumberEditor( false );
        jackInput2Label.SetNumberEditorRange( 0, 100 );
        jackInput2Label.SetNumberEditorInterval( 1 );
        jackInput2Label.SetNumberEditorUsesMouseWheel( false );
        jackInput2Label.SetHasCustomTextHoverColor( false );
        jackInput2Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        jackInput2Label.SetFont( "Times New Roman", 13, true, false );

        jackInput3Label = new VoltageLabel( "jackInput3Label", "Jack Input 3 Label", this, "III" );
        AddComponent( jackInput3Label );
        jackInput3Label.SetWantsMouseNotifications( false );
        jackInput3Label.SetPosition( 29, 310 );
        jackInput3Label.SetSize( 21, 20 );
        jackInput3Label.SetEditable( false, false );
        jackInput3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        jackInput3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        jackInput3Label.SetColor( new Color( 232, 232, 232, 255 ) );
        jackInput3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        jackInput3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        jackInput3Label.SetBorderSize( 1 );
        jackInput3Label.SetMultiLineEdit( false );
        jackInput3Label.SetIsNumberEditor( false );
        jackInput3Label.SetNumberEditorRange( 0, 100 );
        jackInput3Label.SetNumberEditorInterval( 1 );
        jackInput3Label.SetNumberEditorUsesMouseWheel( false );
        jackInput3Label.SetHasCustomTextHoverColor( false );
        jackInput3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        jackInput3Label.SetFont( "Times New Roman", 13, true, false );

        jackInput4Label = new VoltageLabel( "jackInput4Label", "Jack Input 4 Label", this, "IIII" );
        AddComponent( jackInput4Label );
        jackInput4Label.SetWantsMouseNotifications( false );
        jackInput4Label.SetPosition( 69, 310 );
        jackInput4Label.SetSize( 21, 20 );
        jackInput4Label.SetEditable( false, false );
        jackInput4Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        jackInput4Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        jackInput4Label.SetColor( new Color( 232, 232, 232, 255 ) );
        jackInput4Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        jackInput4Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        jackInput4Label.SetBorderSize( 1 );
        jackInput4Label.SetMultiLineEdit( false );
        jackInput4Label.SetIsNumberEditor( false );
        jackInput4Label.SetNumberEditorRange( 0, 100 );
        jackInput4Label.SetNumberEditorInterval( 1 );
        jackInput4Label.SetNumberEditorUsesMouseWheel( false );
        jackInput4Label.SetHasCustomTextHoverColor( false );
        jackInput4Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        jackInput4Label.SetFont( "Times New Roman", 13, true, false );

        labelChannel1 = new VoltageLabel( "labelChannel1", "Label Channel 1", this, "I" );
        AddComponent( labelChannel1 );
        labelChannel1.SetWantsMouseNotifications( false );
        labelChannel1.SetPosition( 0, 50 );
        labelChannel1.SetSize( 21, 20 );
        labelChannel1.SetEditable( false, false );
        labelChannel1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelChannel1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelChannel1.SetColor( new Color( 232, 232, 232, 255 ) );
        labelChannel1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelChannel1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelChannel1.SetBorderSize( 1 );
        labelChannel1.SetMultiLineEdit( false );
        labelChannel1.SetIsNumberEditor( false );
        labelChannel1.SetNumberEditorRange( 0, 100 );
        labelChannel1.SetNumberEditorInterval( 1 );
        labelChannel1.SetNumberEditorUsesMouseWheel( false );
        labelChannel1.SetHasCustomTextHoverColor( false );
        labelChannel1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelChannel1.SetFont( "Times New Roman", 13, true, false );

        labelChannel2 = new VoltageLabel( "labelChannel2", "Label Channel 2", this, "II" );
        AddComponent( labelChannel2 );
        labelChannel2.SetWantsMouseNotifications( false );
        labelChannel2.SetPosition( 0, 90 );
        labelChannel2.SetSize( 21, 20 );
        labelChannel2.SetEditable( false, false );
        labelChannel2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelChannel2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelChannel2.SetColor( new Color( 232, 232, 232, 255 ) );
        labelChannel2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelChannel2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelChannel2.SetBorderSize( 1 );
        labelChannel2.SetMultiLineEdit( false );
        labelChannel2.SetIsNumberEditor( false );
        labelChannel2.SetNumberEditorRange( 0, 100 );
        labelChannel2.SetNumberEditorInterval( 1 );
        labelChannel2.SetNumberEditorUsesMouseWheel( false );
        labelChannel2.SetHasCustomTextHoverColor( false );
        labelChannel2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelChannel2.SetFont( "Times New Roman", 13, true, false );

        labelChannel3 = new VoltageLabel( "labelChannel3", "Label Channel 3", this, "III" );
        AddComponent( labelChannel3 );
        labelChannel3.SetWantsMouseNotifications( false );
        labelChannel3.SetPosition( 0, 135 );
        labelChannel3.SetSize( 21, 20 );
        labelChannel3.SetEditable( false, false );
        labelChannel3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelChannel3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelChannel3.SetColor( new Color( 232, 232, 232, 255 ) );
        labelChannel3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelChannel3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelChannel3.SetBorderSize( 1 );
        labelChannel3.SetMultiLineEdit( false );
        labelChannel3.SetIsNumberEditor( false );
        labelChannel3.SetNumberEditorRange( 0, 100 );
        labelChannel3.SetNumberEditorInterval( 1 );
        labelChannel3.SetNumberEditorUsesMouseWheel( false );
        labelChannel3.SetHasCustomTextHoverColor( false );
        labelChannel3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelChannel3.SetFont( "Times New Roman", 13, true, false );

        labelChannel4 = new VoltageLabel( "labelChannel4", "Label Channel 4", this, "IIII" );
        AddComponent( labelChannel4 );
        labelChannel4.SetWantsMouseNotifications( false );
        labelChannel4.SetPosition( 0, 175 );
        labelChannel4.SetSize( 21, 20 );
        labelChannel4.SetEditable( false, false );
        labelChannel4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelChannel4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelChannel4.SetColor( new Color( 232, 232, 232, 255 ) );
        labelChannel4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelChannel4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelChannel4.SetBorderSize( 1 );
        labelChannel4.SetMultiLineEdit( false );
        labelChannel4.SetIsNumberEditor( false );
        labelChannel4.SetNumberEditorRange( 0, 100 );
        labelChannel4.SetNumberEditorInterval( 1 );
        labelChannel4.SetNumberEditorUsesMouseWheel( false );
        labelChannel4.SetHasCustomTextHoverColor( false );
        labelChannel4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelChannel4.SetFont( "Times New Roman", 13, true, false );

        labelRange1 = new VoltageLabel( "labelRange1", "Label Range 1", this, "0 • L • M • H" );
        AddComponent( labelRange1 );
        labelRange1.SetWantsMouseNotifications( false );
        labelRange1.SetPosition( 101, 63 );
        labelRange1.SetSize( 53, 16 );
        labelRange1.SetEditable( false, false );
        labelRange1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRange1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRange1.SetColor( new Color( 232, 232, 232, 255 ) );
        labelRange1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRange1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRange1.SetBorderSize( 1 );
        labelRange1.SetMultiLineEdit( false );
        labelRange1.SetIsNumberEditor( false );
        labelRange1.SetNumberEditorRange( 0, 100 );
        labelRange1.SetNumberEditorInterval( 1 );
        labelRange1.SetNumberEditorUsesMouseWheel( false );
        labelRange1.SetHasCustomTextHoverColor( false );
        labelRange1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRange1.SetFont( "Courier New", 6, true, false );

        returnJack = new VoltageAudioJack( "returnJack", "Process Return", this, JackType.JackType_AudioInput );
        AddComponent( returnJack );
        returnJack.SetWantsMouseNotifications( false );
        returnJack.SetPosition( 130, 275 );
        returnJack.SetSize( 37, 37 );
        returnJack.SetSkin( "Jack Round" );

        sendJack = new VoltageAudioJack( "sendJack", "Process Send", this, JackType.JackType_AudioOutput );
        AddComponent( sendJack );
        sendJack.SetWantsMouseNotifications( false );
        sendJack.SetPosition( 103, 282 );
        sendJack.SetSize( 25, 25 );
        sendJack.SetSkin( "Mini Jack 25px" );

        output7 = new VoltageAudioJack( "output7", "Submix 7 Output", this, JackType.JackType_AudioOutput );
        AddComponent( output7 );
        output7.SetWantsMouseNotifications( false );
        output7.SetPosition( 165, 275 );
        output7.SetSize( 37, 37 );
        output7.SetSkin( "Rotated Half" );

        switchRange2 = new VoltageSwitch( "switchRange2", "Switch Range 2", this, 0 );
        AddComponent( switchRange2 );
        switchRange2.SetWantsMouseNotifications( false );
        switchRange2.SetPosition( 102, 93 );
        switchRange2.SetSize( 51, 15 );
        switchRange2.SetSkin( "4-State Slide Horiz" );

        labelRange2 = new VoltageLabel( "labelRange2", "Label Range 2", this, "0 • L • M • H" );
        AddComponent( labelRange2 );
        labelRange2.SetWantsMouseNotifications( false );
        labelRange2.SetPosition( 101, 103 );
        labelRange2.SetSize( 53, 16 );
        labelRange2.SetEditable( false, false );
        labelRange2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRange2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRange2.SetColor( new Color( 232, 232, 232, 255 ) );
        labelRange2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRange2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRange2.SetBorderSize( 1 );
        labelRange2.SetMultiLineEdit( false );
        labelRange2.SetIsNumberEditor( false );
        labelRange2.SetNumberEditorRange( 0, 100 );
        labelRange2.SetNumberEditorInterval( 1 );
        labelRange2.SetNumberEditorUsesMouseWheel( false );
        labelRange2.SetHasCustomTextHoverColor( false );
        labelRange2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRange2.SetFont( "Courier New", 6, true, false );

        switchRange3 = new VoltageSwitch( "switchRange3", "Switch Range 3", this, 2 );
        AddComponent( switchRange3 );
        switchRange3.SetWantsMouseNotifications( false );
        switchRange3.SetPosition( 102, 138 );
        switchRange3.SetSize( 51, 15 );
        switchRange3.SetSkin( "4-State Slide Horiz" );

        labelRange3 = new VoltageLabel( "labelRange3", "Label Range 3", this, "0 • L • M • H" );
        AddComponent( labelRange3 );
        labelRange3.SetWantsMouseNotifications( false );
        labelRange3.SetPosition( 101, 148 );
        labelRange3.SetSize( 53, 16 );
        labelRange3.SetEditable( false, false );
        labelRange3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRange3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRange3.SetColor( new Color( 232, 232, 232, 255 ) );
        labelRange3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRange3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRange3.SetBorderSize( 1 );
        labelRange3.SetMultiLineEdit( false );
        labelRange3.SetIsNumberEditor( false );
        labelRange3.SetNumberEditorRange( 0, 100 );
        labelRange3.SetNumberEditorInterval( 1 );
        labelRange3.SetNumberEditorUsesMouseWheel( false );
        labelRange3.SetHasCustomTextHoverColor( false );
        labelRange3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRange3.SetFont( "Courier New", 6, true, false );

        switchRange4 = new VoltageSwitch( "switchRange4", "Switch Range 4", this, 0 );
        AddComponent( switchRange4 );
        switchRange4.SetWantsMouseNotifications( false );
        switchRange4.SetPosition( 102, 178 );
        switchRange4.SetSize( 51, 15 );
        switchRange4.SetSkin( "4-State Slide Horiz" );

        labelRange4 = new VoltageLabel( "labelRange4", "Label Range 4", this, "0 • L • M • H" );
        AddComponent( labelRange4 );
        labelRange4.SetWantsMouseNotifications( false );
        labelRange4.SetPosition( 101, 188 );
        labelRange4.SetSize( 53, 16 );
        labelRange4.SetEditable( false, false );
        labelRange4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRange4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRange4.SetColor( new Color( 232, 232, 232, 255 ) );
        labelRange4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRange4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRange4.SetBorderSize( 1 );
        labelRange4.SetMultiLineEdit( false );
        labelRange4.SetIsNumberEditor( false );
        labelRange4.SetNumberEditorRange( 0, 100 );
        labelRange4.SetNumberEditorInterval( 1 );
        labelRange4.SetNumberEditorUsesMouseWheel( false );
        labelRange4.SetHasCustomTextHoverColor( false );
        labelRange4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRange4.SetFont( "Courier New", 6, true, false );

        process1 = new VoltageKnob( "process1", "Process 1", this, 0.0, 1.0, 0.0 );
        AddComponent( process1 );
        process1.SetWantsMouseNotifications( false );
        process1.SetPosition( 200, 50 );
        process1.SetSize( 21, 21 );
        process1.SetSkin( "JP-106 Gray" );
        process1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        process1.SetKnobParams( 215, 145 );
        process1.DisplayValueInPercent( true );
        process1.SetKnobAdjustsRing( true );

        process2 = new VoltageKnob( "process2", "Process 2", this, 0.0, 1.0, 0.0 );
        AddComponent( process2 );
        process2.SetWantsMouseNotifications( false );
        process2.SetPosition( 200, 90 );
        process2.SetSize( 21, 21 );
        process2.SetSkin( "JP-106 Gray" );
        process2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        process2.SetKnobParams( 215, 145 );
        process2.DisplayValueInPercent( false );
        process2.SetKnobAdjustsRing( true );

        process3 = new VoltageKnob( "process3", "Process 3", this, 0.0, 1.0, 0.0 );
        AddComponent( process3 );
        process3.SetWantsMouseNotifications( false );
        process3.SetPosition( 200, 135 );
        process3.SetSize( 21, 21 );
        process3.SetSkin( "JP-106 Gray" );
        process3.SetRange( 0.0, 1.0, 0.0, false, 0 );
        process3.SetKnobParams( 215, 145 );
        process3.DisplayValueInPercent( false );
        process3.SetKnobAdjustsRing( true );

        process4 = new VoltageKnob( "process4", "Process 4", this, 0.0, 1.0, 0.0 );
        AddComponent( process4 );
        process4.SetWantsMouseNotifications( false );
        process4.SetPosition( 200, 175 );
        process4.SetSize( 21, 21 );
        process4.SetSkin( "JP-106 Gray" );
        process4.SetRange( 0.0, 1.0, 0.0, false, 0 );
        process4.SetKnobParams( 215, 145 );
        process4.DisplayValueInPercent( false );
        process4.SetKnobAdjustsRing( true );

        submix3Label = new VoltageLabel( "submix3Label", "Submix 3 Label", this, "3" );
        AddComponent( submix3Label );
        submix3Label.SetWantsMouseNotifications( false );
        submix3Label.SetPosition( 170, 255 );
        submix3Label.SetSize( 21, 20 );
        submix3Label.SetEditable( false, false );
        submix3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        submix3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        submix3Label.SetColor( new Color( 232, 232, 232, 255 ) );
        submix3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        submix3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        submix3Label.SetBorderSize( 1 );
        submix3Label.SetMultiLineEdit( false );
        submix3Label.SetIsNumberEditor( false );
        submix3Label.SetNumberEditorRange( 0, 100 );
        submix3Label.SetNumberEditorInterval( 1 );
        submix3Label.SetNumberEditorUsesMouseWheel( false );
        submix3Label.SetHasCustomTextHoverColor( false );
        submix3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        submix3Label.SetFont( "Times New Roman", 13, true, false );

        submix7Label = new VoltageLabel( "submix7Label", "Submix 7 Label", this, "7" );
        AddComponent( submix7Label );
        submix7Label.SetWantsMouseNotifications( false );
        submix7Label.SetPosition( 170, 310 );
        submix7Label.SetSize( 21, 20 );
        submix7Label.SetEditable( false, false );
        submix7Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        submix7Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        submix7Label.SetColor( new Color( 232, 232, 232, 255 ) );
        submix7Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        submix7Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        submix7Label.SetBorderSize( 1 );
        submix7Label.SetMultiLineEdit( false );
        submix7Label.SetIsNumberEditor( false );
        submix7Label.SetNumberEditorRange( 0, 100 );
        submix7Label.SetNumberEditorInterval( 1 );
        submix7Label.SetNumberEditorUsesMouseWheel( false );
        submix7Label.SetHasCustomTextHoverColor( false );
        submix7Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        submix7Label.SetFont( "Times New Roman", 13, true, false );

        sendLabel = new VoltageLabel( "sendLabel", "Send Label", this, "S" );
        AddComponent( sendLabel );
        sendLabel.SetWantsMouseNotifications( false );
        sendLabel.SetPosition( 95, 310 );
        sendLabel.SetSize( 41, 20 );
        sendLabel.SetEditable( false, false );
        sendLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sendLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sendLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sendLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sendLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sendLabel.SetBorderSize( 1 );
        sendLabel.SetMultiLineEdit( false );
        sendLabel.SetIsNumberEditor( false );
        sendLabel.SetNumberEditorRange( 0, 100 );
        sendLabel.SetNumberEditorInterval( 1 );
        sendLabel.SetNumberEditorUsesMouseWheel( false );
        sendLabel.SetHasCustomTextHoverColor( false );
        sendLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sendLabel.SetFont( "Times New Roman", 13, true, false );

        returnLabel = new VoltageLabel( "returnLabel", "Return Label", this, "R" );
        AddComponent( returnLabel );
        returnLabel.SetWantsMouseNotifications( false );
        returnLabel.SetPosition( 130, 310 );
        returnLabel.SetSize( 41, 20 );
        returnLabel.SetEditable( false, false );
        returnLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        returnLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        returnLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        returnLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        returnLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        returnLabel.SetBorderSize( 1 );
        returnLabel.SetMultiLineEdit( false );
        returnLabel.SetIsNumberEditor( false );
        returnLabel.SetNumberEditorRange( 0, 100 );
        returnLabel.SetNumberEditorInterval( 1 );
        returnLabel.SetNumberEditorUsesMouseWheel( false );
        returnLabel.SetHasCustomTextHoverColor( false );
        returnLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        returnLabel.SetFont( "Times New Roman", 13, true, false );

        linkLabel = new VoltageLabel( "linkLabel", "Link Label", this, "LINK" );
        AddComponent( linkLabel );
        linkLabel.SetWantsMouseNotifications( false );
        linkLabel.SetPosition( 5, 270 );
        linkLabel.SetSize( 21, 20 );
        linkLabel.SetEditable( false, false );
        linkLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        linkLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        linkLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        linkLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        linkLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        linkLabel.SetBorderSize( 1 );
        linkLabel.SetMultiLineEdit( false );
        linkLabel.SetIsNumberEditor( false );
        linkLabel.SetNumberEditorRange( 0, 100 );
        linkLabel.SetNumberEditorInterval( 1 );
        linkLabel.SetNumberEditorUsesMouseWheel( false );
        linkLabel.SetHasCustomTextHoverColor( false );
        linkLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        linkLabel.SetFont( "Times New Roman", 7, true, false );

        inputLevel1Led = new VoltageLED( "inputLevel1Led", "Input 1 Level", this );
        AddComponent( inputLevel1Led );
        inputLevel1Led.SetWantsMouseNotifications( false );
        inputLevel1Led.SetPosition( 55, 40 );
        inputLevel1Led.SetSize( 7, 7 );
        inputLevel1Led.SetSkin( "2500 Lamp Red" );

        inputLevel2Led = new VoltageLED( "inputLevel2Led", "Input 2 Level", this );
        AddComponent( inputLevel2Led );
        inputLevel2Led.SetWantsMouseNotifications( false );
        inputLevel2Led.SetPosition( 55, 80 );
        inputLevel2Led.SetSize( 7, 7 );
        inputLevel2Led.SetSkin( "2500 Lamp Red" );

        inputLevel3Led = new VoltageLED( "inputLevel3Led", "Input 3 Level", this );
        AddComponent( inputLevel3Led );
        inputLevel3Led.SetWantsMouseNotifications( false );
        inputLevel3Led.SetPosition( 55, 125 );
        inputLevel3Led.SetSize( 7, 7 );
        inputLevel3Led.SetSkin( "2500 Lamp Green" );

        inputLevel4Led = new VoltageLED( "inputLevel4Led", "Input 4 Level", this );
        AddComponent( inputLevel4Led );
        inputLevel4Led.SetWantsMouseNotifications( false );
        inputLevel4Led.SetPosition( 55, 165 );
        inputLevel4Led.SetSize( 7, 7 );
        inputLevel4Led.SetSkin( "2500 Lamp Green" );

        output10 = new VoltageAudioJack( "output10", "Main 10 Output", this, JackType.JackType_AudioOutput );
        AddComponent( output10 );
        output10.SetWantsMouseNotifications( false );
        output10.SetPosition( 190, 245 );
        output10.SetSize( 37, 37 );
        output10.SetSkin( "Rotated Half" );

        mainOutputLabel = new VoltageLabel( "mainOutputLabel", "Main Output Label", this, "10" );
        AddComponent( mainOutputLabel );
        mainOutputLabel.SetWantsMouseNotifications( false );
        mainOutputLabel.SetPosition( 200, 280 );
        mainOutputLabel.SetSize( 21, 20 );
        mainOutputLabel.SetEditable( false, false );
        mainOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        mainOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        mainOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        mainOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        mainOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        mainOutputLabel.SetBorderSize( 1 );
        mainOutputLabel.SetMultiLineEdit( false );
        mainOutputLabel.SetIsNumberEditor( false );
        mainOutputLabel.SetNumberEditorRange( 0, 100 );
        mainOutputLabel.SetNumberEditorInterval( 1 );
        mainOutputLabel.SetNumberEditorUsesMouseWheel( false );
        mainOutputLabel.SetHasCustomTextHoverColor( false );
        mainOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        mainOutputLabel.SetFont( "Times New Roman", 13, true, false );

        brandLogoImage = new VoltageImage( "brandLogoImage", "Brand Logo Image", this, false );
        AddComponent( brandLogoImage );
        brandLogoImage.SetWantsMouseNotifications( false );
        brandLogoImage.SetPosition( 83, 211 );
        brandLogoImage.SetSize( 98, 74 );
        brandLogoImage.SetCurrentImage( "image image(3).png" );
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

        gainKnobs = new VoltageKnob[] {gain1, gain2, gain3, gain4};
        balanceKnobs = new VoltageKnob[] {balance1, balance2, balance3, balance4};
        volumeKnobs = new VoltageKnob[] {volume1, volume2, volume3, volume4};
        processKnobs = new VoltageKnob[] {process1, process2, process3, process4};
        rangeSwitches = new VoltageSwitch[] {switchRange1, switchRange2, switchRange3, switchRange4};
        inputLeds = new VoltageLED[] {inputLevel1Led, inputLevel2Led, inputLevel3Led, inputLevel4Led};
        syncControlTargets();
        mixerCore.reset();
        mixerCore.snapControls();
        for (VoltageLED led : inputLeds) led.SetValue(0.0);



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
            case Knob_Changed:
            case Switch_Changed:
                syncControlTargets();
                break;
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
            case Reset:
            case Randomized:
                syncControlTargets();
                resumePending = true;
                break;
            default:
                break;
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

        if (bypassActive) {
            bypassActive = false;
            ledCountdown = 1;
        }

        if (resumePending) {
            syncControlTargets();
            mixerCore.reset();
            mixerCore.snapControls();
            resumePending = false;
        }
        audioInputs[0] = readInput(input1);
        audioInputs[1] = readInput(input2);
        audioInputs[2] = readInput(input3);
        audioInputs[3] = readInput(input4);
        audioInputs[4] = readInput(returnJack);
        audioInputs[5] = readInput(linkInput);
        mixerCore.process(audioInputs);
        output3.SetValue(mixerCore.outputs[0]);
        output7.SetValue(mixerCore.outputs[1]);
        output10.SetValue(mixerCore.outputs[2]);
        sendJack.SetValue(mixerCore.outputs[3]);
        if (--ledCountdown <= 0) {
            ledCountdown = 480;
            for (int channel = 0; channel < 4; channel++)
                inputLeds[channel].SetValue(Math.min(1.0, mixerCore.ledEnvelope[channel] / 5.0));
        }



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

        if (!bypassActive) {
            for (VoltageLED led : inputLeds)
                led.SetValue(0.0);
            bypassActive = true;
        }

        double firstPair = readInput(input1) + readInput(input2);
        double secondPair = readInput(input3) + readInput(input4);
        output3.SetValue(firstPair);
        output7.SetValue(secondPair);
        output10.SetValue(firstPair + secondPair + readInput(returnJack) + readInput(linkInput));
        sendJack.SetValue(0.0);
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

        if (gainKnobs != null) {
            for (int channel = 0; channel < 4; channel++) {
                if (component == gainKnobs[channel])
                    return String.format("%.2fx input gain", mixerCore.gainForKnob(channel, gainKnobs[channel].GetValue()));
                if (component == balanceKnobs[channel])
                    return String.format("%+.1f dB tilt; 900 Hz pivot", 6.0 * balanceKnobs[channel].GetValue());
                if (component == volumeKnobs[channel])
                    return String.format("VOLUME: %.1f%%", volumeKnobs[channel].GetValue() * 100.0);
                if (component == processKnobs[channel])
                    return String.format("PROCESS send: %.1f%%", processKnobs[channel].GetValue() * 100.0);
                if (component == rangeSwitches[channel]) {
                    int range = (int)Math.round(rangeSwitches[channel].GetValue());
                    return new String[] {"MUTE: dry/send silent; LED monitors input stage", "LOW: unity to 2x", "MEDIUM: unity to 5x", "HIGH: unity to 100x"}[Math.max(0, Math.min(3, range))];
                }
                if (component == inputLeds[channel]) return "Post-GAIN input-stage level; before BALANCE and VOLUME; active while muted";
            }
        }
        if (component == output3) return "I + II submix; remains connected to final mix";
        if (component == output7) return "III + IIII submix; remains connected to final mix";
        if (component == output10) return "Final mix: 3 + 7 + RETURN + LINK";
        if (component == sendJack) return "Sum of post-BALANCE, post-VOLUME PROCESS sends";
        if (component == returnJack) return "Unity effects return into final mix only";
        if (component == linkInput) return "Unity LINK input into final mix only";
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
        if (gainKnobs != null) {
            for (int channel = 0; channel < 4; channel++) {
                if (component == gainKnobs[channel]) {
                    double maximum = Rm1010Core.RANGE_MAX[mixerCore.lastRange[channel]];
                    gainKnobs[channel].SetValue(Math.sqrt(Rm1010Core.limit((newValue - 1.0) / (maximum - 1.0), 0.0, 1.0)));
                    syncControlTargets();
                    return;
                }
                if (component == balanceKnobs[channel]) {
                    balanceKnobs[channel].SetValue(Rm1010Core.limit(newValue / 6.0, -1.0, 1.0));
                    syncControlTargets();
                    return;
                }
                if (component == volumeKnobs[channel] || component == processKnobs[channel]) {
                    ((VoltageKnob)component).SetValue(Rm1010Core.limit(newValue / 100.0, 0.0, 1.0));
                    syncControlTargets();
                    return;
                }
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
    private VoltageImage brandLogoImage;
    private VoltageLabel mainOutputLabel;
    private VoltageAudioJack output10;
    private VoltageLED inputLevel4Led;
    private VoltageLED inputLevel3Led;
    private VoltageLED inputLevel2Led;
    private VoltageLED inputLevel1Led;
    private VoltageLabel linkLabel;
    private VoltageLabel returnLabel;
    private VoltageLabel sendLabel;
    private VoltageLabel submix7Label;
    private VoltageLabel submix3Label;
    private VoltageKnob process4;
    private VoltageKnob process3;
    private VoltageKnob process2;
    private VoltageKnob process1;
    private VoltageLabel labelRange4;
    private VoltageSwitch switchRange4;
    private VoltageLabel labelRange3;
    private VoltageSwitch switchRange3;
    private VoltageLabel labelRange2;
    private VoltageSwitch switchRange2;
    private VoltageAudioJack output7;
    private VoltageAudioJack sendJack;
    private VoltageAudioJack returnJack;
    private VoltageLabel labelRange1;
    private VoltageLabel labelChannel4;
    private VoltageLabel labelChannel3;
    private VoltageLabel labelChannel2;
    private VoltageLabel labelChannel1;
    private VoltageLabel jackInput4Label;
    private VoltageLabel jackInput3Label;
    private VoltageLabel jackInput2Label;
    private VoltageLabel jackInput1Label;
    private VoltageSwitch switchRange1;
    private VoltageAudioJack input2;
    private VoltageAudioJack input3;
    private VoltageAudioJack input1;
    private VoltageKnob volume4;
    private VoltageKnob balance4;
    private VoltageKnob volume3;
    private VoltageKnob balance3;
    private VoltageKnob volume2;
    private VoltageKnob balance2;
    private VoltageKnob volume1;
    private VoltageKnob balance1;
    private VoltageKnob gain4;
    private VoltageKnob gain3;
    private VoltageKnob gain2;
    private VoltageKnob gain1;
    private VoltageLabel numberLabel;
    private VoltageLabel descriptionLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageAudioJack linkInput;
    private VoltageAudioJack input4;
    private VoltageAudioJack output3;
    private VoltageLabel labelVolume;
    private VoltageLabel labelGain;
    private VoltageLabel labelProcess;
    private VoltageLabel labelBalance;


    //[user-code-and-variables]    Add your own variables and functions here


    // RM1010 v1.0.0: 48 kHz host, established Colorbox 2x half-band filtering.
    private final Rm1010Core mixerCore = new Rm1010Core();
    private final double[] audioInputs = new double[6];
    private VoltageKnob[] gainKnobs, balanceKnobs, volumeKnobs, processKnobs;
    private VoltageSwitch[] rangeSwitches;
    private VoltageLED[] inputLeds;
    private boolean resumePending;
    private boolean bypassActive;
    private int ledCountdown = 1;

    private void syncControlTargets() {
        if (gainKnobs == null)
            return;
        for (int i = 0; i < 4; i++)
            mixerCore.setControls(i, gainKnobs[i].GetValue(), balanceKnobs[i].GetValue(),
                volumeKnobs[i].GetValue(), processKnobs[i].GetValue(), (int)Math.round(rangeSwitches[i].GetValue()));
    }

    private static double readInput(VoltageAudioJack jack) {
        if (!jack.IsConnected())
            return 0.0;

        double input = jack.GetValue();
        if (!Double.isFinite(input))
            return 0.0;

        return Rm1010Core.limit(input, -1.0e6, 1.0e6);
    }

    private static final class Rm1010Core {
        private static final double DSP_RATE = 96000.0;
        private static final double SMOOTH = 1.0 - Math.exp(-1.0 / (0.010 * DSP_RATE));
        private static final double INPUT_COUPLING = Math.exp(-2.0 * Math.PI * 16.0 / DSP_RATE);
        private static final double OUTPUT_COUPLING = Math.exp(-2.0 * Math.PI * 3.3 / DSP_RATE);
        private static final double LED_ATTACK = 1.0 - Math.exp(-1.0 / (0.005 * DSP_RATE));
        private static final double LED_RELEASE = 1.0 - Math.exp(-1.0 / (0.120 * DSP_RATE));
        private static final double PIVOT_K = Math.tan(Math.PI * 900.0 / DSP_RATE);
        private static final double[] RANGE_MAX = {2.0, 2.0, 5.0, 100.0};
        private final int[] lastRange = {1, 1, 1, 1};
        private final double[][] target = new double[4][7];
        private final double[][] smooth = new double[4][7];
        private final double[] inputPrevious = new double[4], inputFiltered = new double[4];
        private final double[] tiltPrevious = new double[4], tiltFiltered = new double[4];
        private final double[] outputPrevious = new double[4], outputFiltered = new double[4];
        private final double[] ledEnvelope = new double[4], outputs = new double[4];
        private final double[] frame = new double[6], channels = new double[4];
        private final HalfBand19[] up = new HalfBand19[6], down = new HalfBand19[4];

        Rm1010Core() {
            for (int i = 0; i < 6; i++) up[i] = new HalfBand19();
            for (int i = 0; i < 4; i++) {down[i] = new HalfBand19(); setControls(i, 0, 0, 0, 0, 0);}
            snapControls();
        }

        double gainForKnob(int channel, double knob) {
            knob = limit(knob, 0, 1);
            return 1.0 + (RANGE_MAX[lastRange[channel]] - 1.0) * knob * knob;
        }

        void setControls(int channel, double gain, double tilt, double volume, double send, int range) {
            range = Math.max(0, Math.min(3, range));
            if (range != 0) lastRange[channel] = range;
            double g = Math.pow(10.0, 6.0 * limit(tilt, -1, 1) / 20.0);
            double denominator = 1.0 + PIVOT_K * g;
            target[channel][0] = gainForKnob(channel, gain);
            target[channel][1] = limit(volume, 0, 1);
            target[channel][2] = limit(send, 0, 1);
            target[channel][3] = range == 0 ? 0.0 : 1.0;
            // Bilinear first-order tilt: opposing +/-6 dB ends, unity at 900 Hz.
            target[channel][4] = (g + PIVOT_K) / denominator;
            target[channel][5] = (-g + PIVOT_K) / denominator;
            target[channel][6] = (-1.0 + PIVOT_K * g) / denominator;
        }

        void snapControls() {
            for (int i = 0; i < 4; i++) System.arraycopy(target[i], 0, smooth[i], 0, 7);
        }

        void reset() {
            for (HalfBand19 filter : up) filter.reset();
            for (HalfBand19 filter : down) filter.reset();
            for (int i = 0; i < 4; i++) {
                inputPrevious[i] = inputFiltered[i] = tiltPrevious[i] = tiltFiltered[i] = 0;
                outputPrevious[i] = outputFiltered[i] = ledEnvelope[i] = outputs[i] = 0;
            }
        }

        void process(double[] input) {
            for (int phase = 0; phase < 2; phase++) {
                for (int i = 0; i < 6; i++) {
                    double x = Double.isFinite(input[i]) ? limit(input[i], -1.0e6, 1.0e6) : 0.0;
                    frame[i] = 2.0 * up[i].process(phase == 0 ? x : 0.0);
                }
                for (int i = 0; i < 4; i++) {
                    for (int p = 0; p < 7; p++) smooth[i][p] += SMOOTH * (target[i][p] - smooth[i][p]);
                    double x = frame[i] - inputPrevious[i] + INPUT_COUPLING * inputFiltered[i];
                    inputPrevious[i] = frame[i]; inputFiltered[i] = x;
                    double driven = 10.0 * Math.tanh(x * smooth[i][0] / 10.0);
                    double level = Math.abs(driven);
                    ledEnvelope[i] += (level > ledEnvelope[i] ? LED_ATTACK : LED_RELEASE) * (level - ledEnvelope[i]);
                    double balanced = smooth[i][4] * driven + smooth[i][5] * tiltPrevious[i] - smooth[i][6] * tiltFiltered[i];
                    tiltPrevious[i] = driven; tiltFiltered[i] = balanced;
                    channels[i] = balanced * smooth[i][1] * smooth[i][3];
                }
                double first = busStage(channels[0] + channels[1]);
                double second = busStage(channels[2] + channels[3]);
                double send = 0;
                for (int i = 0; i < 4; i++) send += channels[i] * smooth[i][2];
                frame[0] = first; frame[1] = second;
                frame[2] = busStage(first + second + frame[4] + frame[5]);
                frame[3] = busStage(send);
                for (int i = 0; i < 4; i++) {
                    // Low-corner output coupling removes incidental overload/control DC.
                    double y = frame[i] - outputPrevious[i] + OUTPUT_COUPLING * outputFiltered[i];
                    outputPrevious[i] = frame[i]; outputFiltered[i] = y;
                    double filtered = down[i].process(y);
                    if (phase == 1) outputs[i] = filtered;
                }
            }
        }

        private static double busStage(double x) {
            double normalized = x / 12.0;
            return x / Math.sqrt(1.0 + normalized * normalized);
        }

        private static double limit(double x, double min, double max) {
            if (!Double.isFinite(x))
                return 0.0;
            return x < min ? min : (x > max ? max : x);
        }
    }

    // Reused unchanged from Colorbox RGB 4.0.2, the established 19-tap 2x FIR.
    private static final class HalfBand19
    {
        private static final double C0 =
             0.021277660466073;

        private static final double C2 =
            -0.027992303277934;

        private static final double C4 =
             0.049537687283950;

        private static final double C6 =
            -0.095789590456413;

        private static final double C8 =
             0.308510413777763;

        private static final double C9 =
             0.488912264413122;


        // A doubled ring buffer avoids array shifting and modulo.

        private final double[] buffer =
            new double[38];

        private int position = 0;
        private boolean dirty = false;


        public double process( double input )
        {
            position--;

            if( position < 0 )
            {
                position = 18;
            }

            buffer[position] = input;
            buffer[position + 19] = input;

            dirty = true;


            return
                C0 *
                    (buffer[position] +
                     buffer[position + 18])
                +
                C2 *
                    (buffer[position + 2] +
                     buffer[position + 16])
                +
                C4 *
                    (buffer[position + 4] +
                     buffer[position + 14])
                +
                C6 *
                    (buffer[position + 6] +
                     buffer[position + 12])
                +
                C8 *
                    (buffer[position + 8] +
                     buffer[position + 10])
                +
                C9 *
                    buffer[position + 9];
        }


        public void reset()
        {
            // Avoid repeatedly clearing an already-empty filter.

            if( !dirty )
            {
                return;
            }

            for( int i = 0; i < 38; i++ )
            {
                buffer[i] = 0.0;
            }

            position = 0;
            dirty = false;
        }
    }
    //[/user-code-and-variables]
}

 