package com.insectlabs.dca;


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

//[/user-imports]


public class dca extends VoltageModule
//[user-inheritance]
//[/user-inheritance]

//[/user-inheritance]
{

    @SuppressWarnings("this-escape")
    public dca( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "dca - Dual Controlled Amplifier", ModuleType.ModuleType_Source, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "cf08e19bfde84cafa4543980471b3b21" );
    }

void InitializeControls()
{

        linkInputR = new VoltageAudioJack( "linkInputR", "Link Input rIGHT", this, JackType.JackType_AudioInput );
        AddComponent( linkInputR );
        linkInputR.SetWantsMouseNotifications( false );
        linkInputR.SetPosition( 60, 36 );
        linkInputR.SetSize( 25, 25 );
        linkInputR.SetSkin( "Dark Jack Straight" );

        linkInputL = new VoltageAudioJack( "linkInputL", "Link Input Left", this, JackType.JackType_AudioInput );
        AddComponent( linkInputL );
        linkInputL.SetWantsMouseNotifications( false );
        linkInputL.SetPosition( 30, 36 );
        linkInputL.SetSize( 25, 25 );
        linkInputL.SetSkin( "Dark Jack Straight" );

        inputB = new VoltageAudioJack( "inputB", "Channel B Input", this, JackType.JackType_AudioInput );
        AddComponent( inputB );
        inputB.SetWantsMouseNotifications( false );
        inputB.SetPosition( 70, 60 );
        inputB.SetSize( 25, 25 );
        inputB.SetSkin( "Jack Round 25px" );

        inputA = new VoltageAudioJack( "inputA", "Channel A Input", this, JackType.JackType_AudioInput );
        AddComponent( inputA );
        inputA.SetWantsMouseNotifications( false );
        inputA.SetPosition( 20, 60 );
        inputA.SetSize( 25, 25 );
        inputA.SetSkin( "Jack Round 25px" );

        levelA = new VoltageSlider( "levelA", "Channel A Level", this, true, 0.0, 4.0, 0.0, 0 );
        AddComponent( levelA );
        levelA.SetWantsMouseNotifications( false );
        levelA.SetPosition( 25, 146 );
        levelA.SetSize( 13, 90 );
        levelA.SetSkin( "Juno Ext Dark Gray" );
        levelA.DisplayValueInPercent( false );
        levelA.SetMidpointValue( 1.0 );

        levelB = new VoltageSlider( "levelB", "Channel B Level", this, true, 0.0, 4.0, 1.0, 0 );
        AddComponent( levelB );
        levelB.SetWantsMouseNotifications( false );
        levelB.SetPosition( 75, 146 );
        levelB.SetSize( 13, 90 );
        levelB.SetSkin( "Juno Ext Dark Gray" );
        levelB.DisplayValueInPercent( false );
        levelB.SetMidpointValue( 1.0 );

        labelFooter = new VoltageLabel( "labelFooter", "", this, "dual controlled amplifier" );
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

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "dca" );
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

        inputCVA = new VoltageAudioJack( "inputCVA", "Channel A CV Input", this, JackType.JackType_AudioInput );
        AddComponent( inputCVA );
        inputCVA.SetWantsMouseNotifications( false );
        inputCVA.SetPosition( 20, 254 );
        inputCVA.SetSize( 25, 25 );
        inputCVA.SetSkin( "Jack Round 25px" );

        inputCVB = new VoltageAudioJack( "inputCVB", "Channel B CV Input", this, JackType.JackType_AudioInput );
        AddComponent( inputCVB );
        inputCVB.SetWantsMouseNotifications( false );
        inputCVB.SetPosition( 70, 254 );
        inputCVB.SetSize( 25, 25 );
        inputCVB.SetSkin( "Jack Round 25px" );

        outputA = new VoltageAudioJack( "outputA", "Channel A Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputA );
        outputA.SetWantsMouseNotifications( false );
        outputA.SetPosition( 20, 281 );
        outputA.SetSize( 25, 25 );
        outputA.SetSkin( "Mini Jack 25px" );

        outputB = new VoltageAudioJack( "outputB", "Channel B Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputB );
        outputB.SetWantsMouseNotifications( false );
        outputB.SetPosition( 70, 281 );
        outputB.SetSize( 25, 25 );
        outputB.SetSkin( "Mini Jack 25px" );

        outputL = new VoltageAudioJack( "outputL", "Left Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputL );
        outputL.SetWantsMouseNotifications( false );
        outputL.SetPosition( 20, 306 );
        outputL.SetSize( 25, 25 );
        outputL.SetSkin( "Mini Jack 25px" );

        outputR = new VoltageAudioJack( "outputR", "Rightt Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputR );
        outputR.SetWantsMouseNotifications( false );
        outputR.SetPosition( 70, 307 );
        outputR.SetSize( 25, 25 );
        outputR.SetSkin( "Mini Jack 25px" );

        linkLabel = new VoltageLabel( "linkLabel", "Link Label", this, "LINK" );
        AddComponent( linkLabel );
        linkLabel.SetWantsMouseNotifications( false );
        linkLabel.SetPosition( 0, 25 );
        linkLabel.SetSize( 115, 20 );
        linkLabel.SetEditable( false, false );
        linkLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        linkLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        linkLabel.SetColor( new Color( 42, 42, 42, 255 ) );
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
        linkLabel.SetFont( "Arial", 7, true, false );

        labelIn = new VoltageLabel( "labelIn", "In Label", this, "IN" );
        AddComponent( labelIn );
        labelIn.SetWantsMouseNotifications( false );
        labelIn.SetPosition( 50, 68 );
        labelIn.SetSize( 16, 10 );
        labelIn.SetEditable( false, false );
        labelIn.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelIn.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelIn.SetColor( new Color( 42, 42, 42, 255 ) );
        labelIn.SetBkColor( new Color( 230, 225, 211, 255 ) );
        labelIn.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelIn.SetBorderSize( 1 );
        labelIn.SetMultiLineEdit( false );
        labelIn.SetIsNumberEditor( false );
        labelIn.SetNumberEditorRange( 0, 100 );
        labelIn.SetNumberEditorInterval( 1 );
        labelIn.SetNumberEditorUsesMouseWheel( false );
        labelIn.SetHasCustomTextHoverColor( false );
        labelIn.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelIn.SetFont( "Arial", 7, true, false );

        labelPan = new VoltageLabel( "labelPan", "Pan Label", this, "PAN" );
        AddComponent( labelPan );
        labelPan.SetWantsMouseNotifications( false );
        labelPan.SetPosition( 50, 98 );
        labelPan.SetSize( 16, 10 );
        labelPan.SetEditable( false, false );
        labelPan.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelPan.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelPan.SetColor( new Color( 42, 42, 42, 255 ) );
        labelPan.SetBkColor( new Color( 230, 225, 211, 255 ) );
        labelPan.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelPan.SetBorderSize( 1 );
        labelPan.SetMultiLineEdit( false );
        labelPan.SetIsNumberEditor( false );
        labelPan.SetNumberEditorRange( 0, 100 );
        labelPan.SetNumberEditorInterval( 1 );
        labelPan.SetNumberEditorUsesMouseWheel( false );
        labelPan.SetHasCustomTextHoverColor( false );
        labelPan.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelPan.SetFont( "Arial", 7, true, false );

        labelBias = new VoltageLabel( "labelBias", "Bias Label", this, "BIAS" );
        AddComponent( labelBias );
        labelBias.SetWantsMouseNotifications( false );
        labelBias.SetPosition( 50, 128 );
        labelBias.SetSize( 16, 10 );
        labelBias.SetEditable( false, false );
        labelBias.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBias.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBias.SetColor( new Color( 42, 42, 42, 255 ) );
        labelBias.SetBkColor( new Color( 230, 225, 211, 255 ) );
        labelBias.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBias.SetBorderSize( 1 );
        labelBias.SetMultiLineEdit( false );
        labelBias.SetIsNumberEditor( false );
        labelBias.SetNumberEditorRange( 0, 100 );
        labelBias.SetNumberEditorInterval( 1 );
        labelBias.SetNumberEditorUsesMouseWheel( false );
        labelBias.SetHasCustomTextHoverColor( false );
        labelBias.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBias.SetFont( "Arial", 7, true, false );

        labelShapeA = new VoltageLabel( "labelShapeA", "Channel A Shape Label", this, "SHAPE" );
        AddComponent( labelShapeA );
        labelShapeA.SetWantsMouseNotifications( false );
        labelShapeA.SetPosition( 4, 150 );
        labelShapeA.SetSize( 17, 10 );
        labelShapeA.SetEditable( false, false );
        labelShapeA.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelShapeA.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelShapeA.SetColor( new Color( 42, 42, 42, 255 ) );
        labelShapeA.SetBkColor( new Color( 230, 225, 211, 0 ) );
        labelShapeA.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelShapeA.SetBorderSize( 1 );
        labelShapeA.SetMultiLineEdit( false );
        labelShapeA.SetIsNumberEditor( false );
        labelShapeA.SetNumberEditorRange( 0, 100 );
        labelShapeA.SetNumberEditorInterval( 1 );
        labelShapeA.SetNumberEditorUsesMouseWheel( false );
        labelShapeA.SetHasCustomTextHoverColor( false );
        labelShapeA.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelShapeA.SetFont( "Arial", 7, true, false );

        labelShapeB = new VoltageLabel( "labelShapeB", "Channel B Shape Label", this, "SHAPE" );
        AddComponent( labelShapeB );
        labelShapeB.SetWantsMouseNotifications( false );
        labelShapeB.SetPosition( 94, 150 );
        labelShapeB.SetSize( 17, 10 );
        labelShapeB.SetEditable( false, false );
        labelShapeB.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelShapeB.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelShapeB.SetColor( new Color( 42, 42, 42, 255 ) );
        labelShapeB.SetBkColor( new Color( 230, 225, 211, 0 ) );
        labelShapeB.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelShapeB.SetBorderSize( 1 );
        labelShapeB.SetMultiLineEdit( false );
        labelShapeB.SetIsNumberEditor( false );
        labelShapeB.SetNumberEditorRange( 0, 100 );
        labelShapeB.SetNumberEditorInterval( 1 );
        labelShapeB.SetNumberEditorUsesMouseWheel( false );
        labelShapeB.SetHasCustomTextHoverColor( false );
        labelShapeB.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelShapeB.SetFont( "Arial", 7, true, false );

        labelIND = new VoltageLabel( "labelIND", "Independent Label", this, "IND" );
        AddComponent( labelIND );
        labelIND.SetWantsMouseNotifications( false );
        labelIND.SetPosition( 19, 239 );
        labelIND.SetSize( 17, 10 );
        labelIND.SetEditable( false, false );
        labelIND.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelIND.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelIND.SetColor( new Color( 42, 42, 42, 255 ) );
        labelIND.SetBkColor( new Color( 230, 225, 211, 0 ) );
        labelIND.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelIND.SetBorderSize( 1 );
        labelIND.SetMultiLineEdit( false );
        labelIND.SetIsNumberEditor( false );
        labelIND.SetNumberEditorRange( 0, 100 );
        labelIND.SetNumberEditorInterval( 1 );
        labelIND.SetNumberEditorUsesMouseWheel( false );
        labelIND.SetHasCustomTextHoverColor( false );
        labelIND.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelIND.SetFont( "Arial", 7, true, false );

        labelST = new VoltageLabel( "labelST", "Stereo Label", this, "ST" );
        AddComponent( labelST );
        labelST.SetWantsMouseNotifications( false );
        labelST.SetPosition( 79, 239 );
        labelST.SetSize( 17, 10 );
        labelST.SetEditable( false, false );
        labelST.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelST.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelST.SetColor( new Color( 42, 42, 42, 255 ) );
        labelST.SetBkColor( new Color( 230, 225, 211, 0 ) );
        labelST.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelST.SetBorderSize( 1 );
        labelST.SetMultiLineEdit( false );
        labelST.SetIsNumberEditor( false );
        labelST.SetNumberEditorRange( 0, 100 );
        labelST.SetNumberEditorInterval( 1 );
        labelST.SetNumberEditorUsesMouseWheel( false );
        labelST.SetHasCustomTextHoverColor( false );
        labelST.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelST.SetFont( "Arial", 7, true, false );

        labelSERIAL = new VoltageLabel( "labelSERIAL", "Serial Label", this, "SERIAL" );
        AddComponent( labelSERIAL );
        labelSERIAL.SetWantsMouseNotifications( false );
        labelSERIAL.SetPosition( 44, 227 );
        labelSERIAL.SetSize( 27, 10 );
        labelSERIAL.SetEditable( false, false );
        labelSERIAL.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSERIAL.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSERIAL.SetColor( new Color( 42, 42, 42, 255 ) );
        labelSERIAL.SetBkColor( new Color( 230, 225, 211, 0 ) );
        labelSERIAL.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSERIAL.SetBorderSize( 1 );
        labelSERIAL.SetMultiLineEdit( false );
        labelSERIAL.SetIsNumberEditor( false );
        labelSERIAL.SetNumberEditorRange( 0, 100 );
        labelSERIAL.SetNumberEditorInterval( 1 );
        labelSERIAL.SetNumberEditorUsesMouseWheel( false );
        labelSERIAL.SetHasCustomTextHoverColor( false );
        labelSERIAL.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSERIAL.SetFont( "Arial", 7, true, false );

        switchMode = new VoltageSwitch( "switchMode", "Mode Switch", this, 0 );
        AddComponent( switchMode );
        switchMode.SetWantsMouseNotifications( false );
        switchMode.SetPosition( 37, 236 );
        switchMode.SetSize( 41, 16 );
        switchMode.SetSkin( "3-State Slide Horizontal" );

        knobBiasB = new VoltageKnob( "knobBiasB", "Channel B Bias Knob", this, 0.0, 1.0, 0.5 );
        AddComponent( knobBiasB );
        knobBiasB.SetWantsMouseNotifications( false );
        knobBiasB.SetPosition( 70, 120 );
        knobBiasB.SetSize( 25, 25 );
        knobBiasB.SetSkin( "ARP2500 Sm Emerald" );
        knobBiasB.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobBiasB.SetKnobParams( 215, 145 );
        knobBiasB.DisplayValueInPercent( false );
        knobBiasB.SetKnobAdjustsRing( true );

        knobPanB = new VoltageKnob( "knobPanB", "Channel B Pan Knob", this, 0.0, 1.0, 1.0 );
        AddComponent( knobPanB );
        knobPanB.SetWantsMouseNotifications( false );
        knobPanB.SetPosition( 70, 90 );
        knobPanB.SetSize( 25, 25 );
        knobPanB.SetSkin( "ARP2500 Sm Red" );
        knobPanB.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knobPanB.SetKnobParams( 215, 145 );
        knobPanB.DisplayValueInPercent( false );
        knobPanB.SetKnobAdjustsRing( true );

        knobPanA = new VoltageKnob( "knobPanA", "Channel A Pan Knob", this, 0.0, 1.0, 0.0 );
        AddComponent( knobPanA );
        knobPanA.SetWantsMouseNotifications( false );
        knobPanA.SetPosition( 20, 90 );
        knobPanA.SetSize( 25, 25 );
        knobPanA.SetSkin( "ARP2500 Sm Red" );
        knobPanA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobPanA.SetKnobParams( 215, 145 );
        knobPanA.DisplayValueInPercent( false );
        knobPanA.SetKnobAdjustsRing( true );

        knobBiasA = new VoltageKnob( "knobBiasA", "Channel A Bias Knob", this, 0.0, 1.0, 0.5 );
        AddComponent( knobBiasA );
        knobBiasA.SetWantsMouseNotifications( false );
        knobBiasA.SetPosition( 20, 120 );
        knobBiasA.SetSize( 25, 25 );
        knobBiasA.SetSkin( "ARP2500 Sm Emerald" );
        knobBiasA.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobBiasA.SetKnobParams( 215, 145 );
        knobBiasA.DisplayValueInPercent( false );
        knobBiasA.SetKnobAdjustsRing( true );

        labelLevel = new VoltageLabel( "labelLevel", "Level Faders Label", this, "LEVEL" );
        AddComponent( labelLevel );
        labelLevel.SetWantsMouseNotifications( false );
        labelLevel.SetPosition( 50, 187 );
        labelLevel.SetSize( 16, 10 );
        labelLevel.SetEditable( false, false );
        labelLevel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLevel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLevel.SetColor( new Color( 42, 42, 42, 255 ) );
        labelLevel.SetBkColor( new Color( 230, 225, 211, 255 ) );
        labelLevel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLevel.SetBorderSize( 1 );
        labelLevel.SetMultiLineEdit( false );
        labelLevel.SetIsNumberEditor( false );
        labelLevel.SetNumberEditorRange( 0, 100 );
        labelLevel.SetNumberEditorInterval( 1 );
        labelLevel.SetNumberEditorUsesMouseWheel( false );
        labelLevel.SetHasCustomTextHoverColor( false );
        labelLevel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLevel.SetFont( "Arial", 7, true, false );

        labelCVjacks = new VoltageLabel( "labelCVjacks", "CV Jacks Label", this, "CV" );
        AddComponent( labelCVjacks );
        labelCVjacks.SetWantsMouseNotifications( false );
        labelCVjacks.SetPosition( 50, 262 );
        labelCVjacks.SetSize( 16, 10 );
        labelCVjacks.SetEditable( false, false );
        labelCVjacks.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCVjacks.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCVjacks.SetColor( new Color( 42, 42, 42, 255 ) );
        labelCVjacks.SetBkColor( new Color( 230, 225, 211, 255 ) );
        labelCVjacks.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCVjacks.SetBorderSize( 1 );
        labelCVjacks.SetMultiLineEdit( false );
        labelCVjacks.SetIsNumberEditor( false );
        labelCVjacks.SetNumberEditorRange( 0, 100 );
        labelCVjacks.SetNumberEditorInterval( 1 );
        labelCVjacks.SetNumberEditorUsesMouseWheel( false );
        labelCVjacks.SetHasCustomTextHoverColor( false );
        labelCVjacks.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCVjacks.SetFont( "Arial", 7, true, false );

        labelMono = new VoltageLabel( "labelMono", "Mono Label", this, "MONO" );
        AddComponent( labelMono );
        labelMono.SetWantsMouseNotifications( false );
        labelMono.SetPosition( 50, 288 );
        labelMono.SetSize( 16, 10 );
        labelMono.SetEditable( false, false );
        labelMono.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMono.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMono.SetColor( new Color( 42, 42, 42, 255 ) );
        labelMono.SetBkColor( new Color( 230, 225, 211, 255 ) );
        labelMono.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMono.SetBorderSize( 1 );
        labelMono.SetMultiLineEdit( false );
        labelMono.SetIsNumberEditor( false );
        labelMono.SetNumberEditorRange( 0, 100 );
        labelMono.SetNumberEditorInterval( 1 );
        labelMono.SetNumberEditorUsesMouseWheel( false );
        labelMono.SetHasCustomTextHoverColor( false );
        labelMono.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMono.SetFont( "Arial", 7, true, false );

        labelDual = new VoltageLabel( "labelDual", "Dual Label", this, "DUAL" );
        AddComponent( labelDual );
        labelDual.SetWantsMouseNotifications( false );
        labelDual.SetPosition( 50, 314 );
        labelDual.SetSize( 16, 10 );
        labelDual.SetEditable( false, false );
        labelDual.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDual.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDual.SetColor( new Color( 42, 42, 42, 255 ) );
        labelDual.SetBkColor( new Color( 230, 225, 211, 255 ) );
        labelDual.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDual.SetBorderSize( 1 );
        labelDual.SetMultiLineEdit( false );
        labelDual.SetIsNumberEditor( false );
        labelDual.SetNumberEditorRange( 0, 100 );
        labelDual.SetNumberEditorInterval( 1 );
        labelDual.SetNumberEditorUsesMouseWheel( false );
        labelDual.SetHasCustomTextHoverColor( false );
        labelDual.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDual.SetFont( "Arial", 7, true, false );

        knobShapeB = new VoltageKnob( "knobShapeB", "Channel B Shape Knob", this, 0.00, 1.00, 1.0 );
        AddComponent( knobShapeB );
        knobShapeB.SetWantsMouseNotifications( false );
        knobShapeB.SetPosition( 95, 161 );
        knobShapeB.SetSize( 15, 15 );
        knobShapeB.SetSkin( "ARP2500 Med Black" );
        knobShapeB.SetRange( 0.00, 1.00, 1.0, false, 0 );
        knobShapeB.SetKnobParams( 215, 145 );
        knobShapeB.DisplayValueInPercent( false );
        knobShapeB.SetKnobAdjustsRing( true );

        knobShapeA = new VoltageKnob( "knobShapeA", "Channel A Shape Knob", this, 0.00, 1.00, 1.0 );
        AddComponent( knobShapeA );
        knobShapeA.SetWantsMouseNotifications( false );
        knobShapeA.SetPosition( 5, 161 );
        knobShapeA.SetSize( 15, 15 );
        knobShapeA.SetSkin( "ARP2500 Med Black" );
        knobShapeA.SetRange( 0.00, 1.00, 1.0, false, 0 );
        knobShapeA.SetKnobParams( 215, 145 );
        knobShapeA.DisplayValueInPercent( false );
        knobShapeA.SetKnobAdjustsRing( true );
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
        core.reset();
        ready = true;
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
        // nothing to release: no threads, no timers
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
        // controls are read every sample; nothing to handle here
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
        if (!ready) {
            return;
        }
        core.process( modeIndex(),
            readJack( inputA ), inputA.IsConnected(), readJack( inputCVA ), inputCVA.IsConnected(),
            clampD( levelA.GetValue(), 0.0, 4.0 ), knobShapeA.GetValue(), ( knobBiasA.GetValue() - 0.5 ) * 2.0 * BIAS_VOLTS, knobPanA.GetValue(),
            readJack( inputB ), inputB.IsConnected(), readJack( inputCVB ), inputCVB.IsConnected(),
            clampD( levelB.GetValue(), 0.0, 4.0 ), knobShapeB.GetValue(), ( knobBiasB.GetValue() - 0.5 ) * 2.0 * BIAS_VOLTS, knobPanB.GetValue(),
            readJack( linkInputL ), readJack( linkInputR ), outputR.IsConnected() );
        outputA.SetValue( core.outA );
        outputB.SetValue( core.outB );
        outputL.SetValue( core.outL );
        outputR.SetValue( core.outR );
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
        // Bypassed: no DSP. The inputs keep going to the same places: MONO A / MONO B carry IN A / IN B (IN A is the normal for B), and DUAL L / R use
        // the routing and pan values frozen at the last processed sample, including the mono mixdown when nothing is in DUAL R.
        if (!ready) {
            return;
        }
        if (!core.hasRouting()) {
            core.setRouting( modeIndex(), knobPanA.GetValue(), knobPanB.GetValue() );
        }
        core.bypass( readJack( inputA ), inputA.IsConnected(), readJack( inputB ), inputB.IsConnected(),
            readJack( linkInputL ), readJack( linkInputR ), outputR.IsConnected() );
        outputA.SetValue( core.outA );
        outputB.SetValue( core.outB );
        outputL.SetValue( core.outL );
        outputR.SetValue( core.outR );
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
        // native tooltips and typed entry are used unchanged
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
        // no custom undo events
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
        return new byte[0];
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
        // no custom state: knobs, sliders and the switch are saved by Voltage
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
        return new byte[0];
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
        // no custom state
        //[/user-SetStateInformationForVariations]
    }


    // Auto-generated variables
    private VoltageKnob knobShapeA;
    private VoltageKnob knobShapeB;
    private VoltageLabel labelDual;
    private VoltageLabel labelMono;
    private VoltageLabel labelCVjacks;
    private VoltageLabel labelLevel;
    private VoltageKnob knobBiasA;
    private VoltageKnob knobPanA;
    private VoltageKnob knobPanB;
    private VoltageKnob knobBiasB;
    private VoltageSwitch switchMode;
    private VoltageLabel labelSERIAL;
    private VoltageLabel labelST;
    private VoltageLabel labelIND;
    private VoltageLabel labelShapeB;
    private VoltageLabel labelShapeA;
    private VoltageLabel labelBias;
    private VoltageLabel labelPan;
    private VoltageLabel labelIn;
    private VoltageLabel linkLabel;
    private VoltageAudioJack outputR;
    private VoltageAudioJack outputL;
    private VoltageAudioJack outputB;
    private VoltageAudioJack outputA;
    private VoltageAudioJack inputCVB;
    private VoltageAudioJack inputCVA;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageSlider levelB;
    private VoltageSlider levelA;
    private VoltageAudioJack inputA;
    private VoltageAudioJack inputB;
    private VoltageAudioJack linkInputL;
    private VoltageAudioJack linkInputR;


    //[user-code-and-variables]    Add your own variables and functions here
    // ---- dca module layer ----
    // [MEASURED] jack values are volts. Controls as designed in the panel (author's notes fields):
    //   LEVEL slider 0..4 = gain at 5 V CV (5 V reference when CV is unpatched) or CV depth 0..4x; BIAS 0..1 (0.5 = none) offsets the incoming signal;
    //   PAN 0..1 (0 = left, 1 = right) only affects the DUAL outputs; MONO outputs are VCA only; LINK inputs go to DUAL at unity.
    // MODE switch: 0 IND, 1 SERIAL (A feeds B when IN B is unpatched), 2 ST (only A's LEVEL, SHAPE, CV and PAN act; BIAS stays per channel; PAN A is a balance control).
    // IN A is normalled to B in SERIAL (A's output) and ST (copy with A's controls); patching IN B breaks it. IND has no normal.
    // When DUAL R is unpatched, DUAL L carries a mono mixdown of both channels and both LINK inputs.
    private static final double BIAS_VOLTS = 5.0;                  // BIAS knob spans +-5 V on the incoming signal [GUESS]
    private final DcaCore core = new DcaCore();
    private boolean ready;

    // ---- DcaCore (framework-free; exercised by the DCA release test harness) ----
    private static final class DcaCore {
        public static final double FS = 48000.0;
        public static final double MAX_GAIN = 4.0;                 // fader + CV depth top, linear [author's panel notes]
        public static final double REF_V = 5.0;                    // unpatched CV reference, and full CV [author]
        public static final double KNEE_V = 10.0, CEILING_V = 11.0; // soft limit starts at 10 V, hard asymptote 11 V [author]
        public static final double SAT_BETA = 0.12;                // [MODEL] third harmonic about 3 % at k = 1, 10 V peak (Synthi 3 % THD level, DOC)
        public static final double NOISE_RMS_V = 7.0710678 / 5011.872; // 74 dB below a 10 V peak sine (rms 7.07 V) [DOC 74 dB, MODEL]
        public static final double K_UNITY = 0.15;                 // color amount at unity gain [author "gentle build", GUESS]
        public static final double SMOOTH_S = 0.010;               // control smoothing time constant (level, shape, bias, pan) [GUESS]
        static final int DRY_DELAY_SHIFT = 16 - 9;           // reads the value written 9 calls ago = 9 samples of delay
        public static final int MODE_IND = 0, MODE_SERIAL = 1, MODE_ST = 2;

        // ------------------------------------------------------------ laws (static, tested)
        /** Gain for control amount x (0..4) and SHAPE s (0..1): 0 = LIN, 0.5 = EXP, 1 = LOG (Synthi dB-linear over 60 dB). Unity at x = 1 for every s. */
        public static double gainLaw(double x, double s) {
            if (!(x > 0.0)) return 0.0;
            if (x >= 1.0) return x > MAX_GAIN ? MAX_GAIN : x;       // above unity: plain linear amplification
            double la = s <= 0.5 ? 2.0 * s : 1.0 + 4.0 * (s - 0.5); // log10(a): 0 (lin), 1 (a = 10, EXP), 3 (a = 1000, LOG)
            if (la < 1e-4) return x;
            double a = Math.pow(10.0, la);
            return (Math.pow(a, x) - 1.0) / (a - 1.0);
        }

        /** Color amount 0..1 from the gain: 0 at silence, 0.15 at unity, 1 at 4x. */
        public static double colorAmount(double g) {
            if (!(g > 0.0)) return 0.0;
            if (g <= 1.0) return K_UNITY * g * g;
            double u = 20.0 * Math.log10(g) / 12.0;
            if (u > 1.0) u = 1.0;
            return K_UNITY + (1.0 - K_UNITY) * Math.sqrt(u);
        }

        /** Output waveshaper: gentle cubic compression scaled by k up to 10 V, then tanh toward the 11 V ceiling (value and slope continuous at 10 V). */
        public static double shape(double u, double k) {
            double a = u < 0 ? -u : u;
            double bk = SAT_BETA * k;
            if (a <= KNEE_V) {
                double r = u / KNEE_V;
                return u * (1.0 - bk * r * r);
            }
            double y10 = KNEE_V * (1.0 - bk);
            double m = 1.0 - 3.0 * bk;
            double h = CEILING_V - y10;
            double y = y10 + h * Math.tanh((a - KNEE_V) * m / h);
            return u < 0 ? -y : y;
        }

        /** Constant-power pan, p 0 (left) .. 1 (right). */
        public static double panL(double p) { return Math.cos(clamp(p, 0, 1) * Math.PI * 0.5); }
        public static double panR(double p) { return Math.sin(clamp(p, 0, 1) * Math.PI * 0.5); }

        static double clamp(double v, double lo, double hi) { return v < lo ? lo : (v > hi ? hi : v); }
        static double fin(double v) { return (v == v && v != Double.POSITIVE_INFINITY && v != Double.NEGATIVE_INFINITY) ? v : 0.0; }

        // ------------------------------------------------------------ half-band filter (author's HalfBand19, coefficients and arithmetic preserved)
        static final class HalfBand19 {
            private static final double C0 = 0.021277660466073;
            private static final double C2 = -0.027992303277934;
            private static final double C4 = 0.049537687283950;
            private static final double C6 = -0.095789590456413;
            private static final double C8 = 0.308510413777763;
            private static final double C9 = 0.488912264413122;
            private final double[] buffer = new double[38];
            private int position = 0;
            private boolean dirty = false;
            double process(double input) {
                position--;
                if (position < 0) position = 18;
                buffer[position] = input;
                buffer[position + 19] = input;
                dirty = true;
                return C0 * (buffer[position] + buffer[position + 18])
                     + C2 * (buffer[position + 2] + buffer[position + 16])
                     + C4 * (buffer[position + 4] + buffer[position + 14])
                     + C6 * (buffer[position + 6] + buffer[position + 12])
                     + C8 * (buffer[position + 8] + buffer[position + 10])
                     + C9 * buffer[position + 9];
            }
            void reset() {
                if (!dirty) return;
                for (int i = 0; i < 38; i++) buffer[i] = 0.0;
                position = 0;
                dirty = false;
            }
        }

        // ------------------------------------------------------------ one channel
        public static final class Channel {
            private final HalfBand19 up = new HalfBand19(), down = new HalfBand19();
            private double lvl, shp, bias;            // smoothed controls
            private boolean primed;
            private final double[] dry = new double[16];     // exact 9-sample delay of the clean path (matches the up+down half-band latency)
            private int dryPos;
            private boolean idle;
            public boolean isIdle() { return idle; }
            private int rng = 0x2545F491;
            private static final double ALPHA = 1.0 - Math.exp(-1.0 / (SMOOTH_S * FS));
            public double lastGain, lastColor, lastX;

            public void reset() { up.reset(); down.reset(); primed = false; for (int i = 0; i < dry.length; i++) dry[i] = 0.0; dryPos = 0; }

            private double smooth(double cur, double target) {
                double d = target - cur;
                return (d > -1e-7 && d < 1e-7) ? target : cur + d * ALPHA;
            }
            private double noise() {                  // xorshift32 -> uniform with unit variance, no allocation
                int x = rng; x ^= x << 13; x ^= x >>> 17; x ^= x << 5; rng = x;
                return (x * (1.0 / 2147483648.0)) * 1.7320508;
            }

            /** level 0..4 (gain at 5 V CV), shape 0..1, biasV -5..+5 added to the signal; cv ignored (5 V reference) when not connected. */
            public double process(double in, boolean inConnected, boolean noiseForced, double cv, boolean cvConnected, double level, double shape, double biasV) {
                level = clamp(fin(level), 0, MAX_GAIN); shape = clamp(fin(shape), 0, 1); biasV = clamp(fin(biasV), -REF_V, REF_V);
                // Nothing patched, no forced noise floor and BIAS centred: the channel is silent and costs nothing.
                if (!inConnected && !noiseForced && biasV > -1e-3 && biasV < 1e-3) {
                    if (!idle) { reset(); idle = true; lastGain = 0.0; lastColor = 0.0; }
                    return 0.0;
                }
                idle = false;
                if (!primed) { lvl = level; shp = shape; bias = biasV; primed = true; }
                lvl = smooth(lvl, level); shp = smooth(shp, shape); bias = smooth(bias, biasV);
                double cvEff = cvConnected ? Math.max(0.0, fin(cv)) : REF_V;       // half-wave rectified, not smoothed
                double x = clamp(lvl * cvEff / REF_V, 0.0, MAX_GAIN);
                double g = gainLaw(x, shp);
                double k = colorAmount(g);
                lastX = x; lastGain = g; lastColor = k;
                double v = (fin(in) + bias) * g;
                if (inConnected || noiseForced) v += noise() * NOISE_RMS_V * (g / MAX_GAIN) * k;   // amplifier noise tracks gain and color, none at cutoff; unpatched channels stay silent except the forced case (ST with only IN B)
                // 2x oversampled nonlinear stage. Only the nonlinear residual goes through the down-sampling half-band, so the clean path is a pure delay
                // (flat response) and the harmonics the waveshaper creates are band-limited before they fold back.
                double u0 = up.process(2.0 * v), u1 = up.process(0.0);
                double r0 = shape(u0, k) - u0, r1 = shape(u1, k) - u1;
                double res = down.process(r0);          // the even-phase output sits exactly 9 samples (at 48 kHz) behind the input
                down.process(r1);
                dry[dryPos] = v;
                double d = dry[(dryPos + DRY_DELAY_SHIFT) & 15];
                dryPos = (dryPos + 1) & 15;
                return d + res;
            }
        }

        // ------------------------------------------------------------ module-level mix
        public final Channel a = new Channel(), b = new Channel();
        public double outA, outB, outL, outR;                // MONO A, MONO B (VCA only), DUAL L, DUAL R
        private double panA, panB; private boolean panPrimed;
        private static final double PAN_ALPHA = 1.0 - Math.exp(-1.0 / (SMOOTH_S * FS));

        public void reset() { a.reset(); b.reset(); panPrimed = false; outA = outB = outL = outR = 0.0; }

        // routing weights, shared by process() and bypass(): DUAL L = wLA*A + wLB*B + links, DUAL R = wRA*A + wRB*B + links
        private double wLA, wLB, wRA, wRB;
        private boolean monoMix, routed;
        private int lastMode;
        public boolean hasRouting() { return routed; }
        /** Seed the routing before anything has been processed (module bypassed from the start). */
        public void setRouting(int mode, double pA, double pB) { lastMode = mode; panA = clamp(fin(pA), 0, 1); panB = clamp(fin(pB), 0, 1); panPrimed = true; routed = true; }

        private void route(int mode, boolean conB, boolean rightOut) {
            monoMix = !rightOut;
            if (monoMix) {                                         // mono environment: everything folds into DUAL L at unity
                wLA = 1.0; wLB = (mode == MODE_ST && !conB) ? 0.0 : 1.0; wRA = wRB = 0.0;
                if (mode == MODE_ST && !conB) { wLA = 0.5; wLB = 0.5; }   // ST normal: the two copies are one signal, not two
            } else if (mode == MODE_ST) {                          // balance: centre = both full, turning away fades the opposite side
                wLA = Math.min(1.0, 2.0 * (1.0 - panA)); wLB = 0.0; wRA = 0.0; wRB = Math.min(1.0, 2.0 * panA);
            } else {
                wLA = panL(panA); wRA = panR(panA); wLB = panL(panB); wRB = panR(panB);
            }
        }
        private void mix(double ya, double yb, double linkL, double linkR) {
            outA = ya; outB = yb;
            linkL = fin(linkL); linkR = fin(linkR);
            if (monoMix) { outL = wLA * ya + wLB * yb + linkL + linkR; outR = outL; }
            else { outL = wLA * ya + wLB * yb + linkL; outR = wRA * ya + wRB * yb + linkR; }
        }

        /**
         * mode: 0 IND, 1 SERIAL, 2 ST. IN B unpatched is normalled in SERIAL (A's output) and ST (a copy of IN A with A's controls,
         * B keeps its own BIAS); patching IN B breaks the normal. IND has no normal: unpatched IN B is silent.
         * ST: only channel A's LEVEL, SHAPE, CV and PAN act. A feeds the left side, B the right, and PAN A is a balance control.
         * With IN B alone, A is still in the mix and carries only its noise floor.
         * Link inputs are summed at unity into DUAL only. rightOut = a plug is in the DUAL R jack; when false, DUAL L carries a mono mixdown.
         */
        public void process(int mode,
                            double inA, boolean conA, double cvA, boolean cvConA, double levelA, double shapeA, double biasVA, double pA,
                            double inB, boolean conB, double cvB, boolean cvConB, double levelB, double shapeB, double biasVB, double pB,
                            double linkL, double linkR, boolean rightOut) {
            double ya, yb;
            if (mode == MODE_ST) {
                ya = a.process(inA, conA, !conA && conB, cvA, cvConA, levelA, shapeA, biasVA);                  // IN B alone: A still in the mix, noise floor only
                yb = conB ? b.process(inB, true, false, cvA, cvConA, levelA, shapeA, biasVB)
                          : b.process(inA, conA, false, cvA, cvConA, levelA, shapeA, biasVB);                   // normal from IN A, B's own BIAS
            } else {
                ya = a.process(inA, conA, false, cvA, cvConA, levelA, shapeA, biasVA);
                if (mode == MODE_SERIAL && !conB) yb = b.process(ya, conA, false, cvB, cvConB, levelB, shapeB, biasVB);   // normal: A's output
                else yb = b.process(inB, conB, false, cvB, cvConB, levelB, shapeB, biasVB);                     // IND: no normal, unpatched IN B is silent (BIAS still acts)
            }
            pA = clamp(fin(pA), 0, 1); pB = clamp(fin(pB), 0, 1);
            if (!panPrimed) { panA = pA; panB = pB; panPrimed = true; }
            panA += (pA - panA) * PAN_ALPHA; panB += (pB - panB) * PAN_ALPHA;
            lastMode = mode; routed = true; bypassed = false;
            route(mode, conB, rightOut);
            mix(ya, yb, linkL, linkR);
        }

        private boolean bypassed;
        /** Bypass: no DSP at all. IN A and IN B (or IN A as the normal) go through the routing and pan values frozen at the last processed sample. */
        public void bypass(double inA, boolean conA, double inB, boolean conB, double linkL, double linkR, boolean rightOut) {
            if (!bypassed) { a.reset(); b.reset(); bypassed = true; }
            double ya = conA ? fin(inA) : 0.0;
            double yb = conB ? fin(inB) : (lastMode == MODE_IND ? 0.0 : ya);   // SERIAL and ST normals hold; IND has none
            route(lastMode, conB, rightOut);
            mix(ya, yb, linkL, linkR);
        }
    }

    private static double clampD(double v, double lo, double hi) {
        return v < lo ? lo : (v > hi ? hi : v);
    }

    private static double readJack(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double v = jack.GetValue();
        return Double.isFinite(v) ? v : 0.0;
    }

    private int modeIndex() {
        return (int) clampD(Math.round(switchMode.GetValue()), 0, 2);
    }
    //[/user-code-and-variables]
}
