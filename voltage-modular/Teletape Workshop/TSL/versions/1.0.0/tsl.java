package com.insectlabs.tsl;


import voltage.controllers.*;
import voltage.core.*;
import voltage.core.Jack.JackType;
import voltage.sources.*;
import voltage.utility.*;
import voltage.processors.*;
import voltage.effects.*;
import java.awt.*;

//[user-imports]   Add your own imports here
import java.util.concurrent.atomic.AtomicBoolean;
//[/user-imports]


public class tsl extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public tsl( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "Triple Slew Limiter", ModuleType.ModuleType_Source, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "7c1391bc2f85460abe0bcdf693317689" );
    }

void InitializeControls()
{

        labelBars = new VoltageLabel( "labelBars", "V.C. Bars Label", this, "—  V.C. INPUT  —" );
        AddComponent( labelBars );
        labelBars.SetWantsMouseNotifications( false );
        labelBars.SetPosition( 0, 245 );
        labelBars.SetSize( 115, 18 );
        labelBars.SetEditable( false, false );
        labelBars.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBars.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBars.SetColor( new Color( 166, 36, 36, 255 ) );
        labelBars.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBars.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBars.SetBorderSize( 1 );
        labelBars.SetMultiLineEdit( false );
        labelBars.SetIsNumberEditor( false );
        labelBars.SetNumberEditorRange( 0, 100 );
        labelBars.SetNumberEditorInterval( 1 );
        labelBars.SetNumberEditorUsesMouseWheel( false );
        labelBars.SetHasCustomTextHoverColor( false );
        labelBars.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBars.SetFont( "Arial", 13, true, false );

        labelV4 = new VoltageLabel( "labelV4", "V.C. Input Label", this, "V.C. INPUT" );
        AddComponent( labelV4 );
        labelV4.SetWantsMouseNotifications( false );
        labelV4.SetPosition( 0, 245 );
        labelV4.SetSize( 115, 18 );
        labelV4.SetEditable( false, false );
        labelV4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV4.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV4.SetBorderSize( 1 );
        labelV4.SetMultiLineEdit( false );
        labelV4.SetIsNumberEditor( false );
        labelV4.SetNumberEditorRange( 0, 100 );
        labelV4.SetNumberEditorInterval( 1 );
        labelV4.SetNumberEditorUsesMouseWheel( false );
        labelV4.SetHasCustomTextHoverColor( false );
        labelV4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV4.SetFont( "Arial", 13, true, false );

        labelVbars = new VoltageLabel( "labelVbars", "Voltage Bars", this, "—  VOLTAGE  —" );
        AddComponent( labelVbars );
        labelVbars.SetWantsMouseNotifications( true );
        labelVbars.SetPosition( 0, 40 );
        labelVbars.SetSize( 115, 18 );
        labelVbars.SetEditable( false, false );
        labelVbars.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVbars.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVbars.SetColor( new Color( 166, 36, 36, 255 ) );
        labelVbars.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVbars.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVbars.SetBorderSize( 1 );
        labelVbars.SetMultiLineEdit( false );
        labelVbars.SetIsNumberEditor( false );
        labelVbars.SetNumberEditorRange( 0, 100 );
        labelVbars.SetNumberEditorInterval( 1 );
        labelVbars.SetNumberEditorUsesMouseWheel( false );
        labelVbars.SetHasCustomTextHoverColor( false );
        labelVbars.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVbars.SetFont( "Arial", 13, true, false );

        labelVoltage = new VoltageLabel( "labelVoltage", "Voltage Label", this, "VOLTAGE" );
        AddComponent( labelVoltage );
        labelVoltage.SetWantsMouseNotifications( true );
        labelVoltage.SetPosition( 0, 40 );
        labelVoltage.SetSize( 115, 18 );
        labelVoltage.SetEditable( false, false );
        labelVoltage.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVoltage.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVoltage.SetColor( new Color( 46, 46, 46, 255 ) );
        labelVoltage.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVoltage.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVoltage.SetBorderSize( 1 );
        labelVoltage.SetMultiLineEdit( false );
        labelVoltage.SetIsNumberEditor( false );
        labelVoltage.SetNumberEditorRange( 0, 100 );
        labelVoltage.SetNumberEditorInterval( 1 );
        labelVoltage.SetNumberEditorUsesMouseWheel( false );
        labelVoltage.SetHasCustomTextHoverColor( false );
        labelVoltage.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVoltage.SetFont( "Arial", 13, true, false );

        knobV1 = new VoltageKnob( "knobV1", "Voltage 1", this, 0.0, 1.0, 0.0 );
        AddComponent( knobV1 );
        knobV1.SetWantsMouseNotifications( false );
        knobV1.SetPosition( 42, 60 );
        knobV1.SetSize( 31, 31 );
        knobV1.SetSkin( "ARP2500 Sm Silver" );
        knobV1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobV1.SetKnobParams( 215, 145 );
        knobV1.DisplayValueInPercent( true );
        knobV1.SetKnobAdjustsRing( true );
        knobV1.SetRangeSkewValue( 1.0, true );

        knobV2 = new VoltageKnob( "knobV2", "Voltage 2", this, 0.0, 1.0, 0.0 );
        AddComponent( knobV2 );
        knobV2.SetWantsMouseNotifications( false );
        knobV2.SetPosition( 42, 110 );
        knobV2.SetSize( 31, 31 );
        knobV2.SetSkin( "ARP2500 Sm Silver" );
        knobV2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobV2.SetKnobParams( 215, 145 );
        knobV2.DisplayValueInPercent( true );
        knobV2.SetKnobAdjustsRing( true );
        knobV2.SetRangeSkewValue( 1.0, true );

        lampPower = new VoltageLED( "lampPower", "Power Lamp", this );
        AddComponent( lampPower );
        lampPower.SetWantsMouseNotifications( false );
        lampPower.SetPosition( 49, 216 );
        lampPower.SetSize( 16, 16 );
        lampPower.SetSkin( "2500 Lamp Red" );

        inputVC1 = new VoltageAudioJack( "inputVC1", "V.C. Input 1", this, JackType.JackType_AudioInput );
        AddComponent( inputVC1 );
        inputVC1.SetWantsMouseNotifications( false );
        inputVC1.SetPosition( 10, 265 );
        inputVC1.SetSize( 25, 25 );
        inputVC1.SetSkin( "Jack Round 25px" );

        inputVC2 = new VoltageAudioJack( "inputVC2", "V.C. Input 2", this, JackType.JackType_AudioInput );
        AddComponent( inputVC2 );
        inputVC2.SetWantsMouseNotifications( false );
        inputVC2.SetPosition( 45, 265 );
        inputVC2.SetSize( 25, 25 );
        inputVC2.SetSkin( "Jack Round 25px" );

        inputVC3 = new VoltageAudioJack( "inputVC3", "Track/Hold Gate Input", this, JackType.JackType_AudioInput );
        AddComponent( inputVC3 );
        inputVC3.SetWantsMouseNotifications( false );
        inputVC3.SetPosition( 80, 265 );
        inputVC3.SetSize( 25, 25 );
        inputVC3.SetSkin( "Jack Round 25px" );

        labelFooter = new VoltageLabel( "labelFooter", "Footer", this, "TRIPLE SLEW LIMITER" );
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

        labelVCinput1 = new VoltageLabel( "labelVCinput1", "V.C. Input 1 Label", this, "1" );
        AddComponent( labelVCinput1 );
        labelVCinput1.SetWantsMouseNotifications( false );
        labelVCinput1.SetPosition( 4, 288 );
        labelVCinput1.SetSize( 37, 23 );
        labelVCinput1.SetEditable( false, false );
        labelVCinput1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVCinput1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVCinput1.SetColor( new Color( 46, 46, 46, 255 ) );
        labelVCinput1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVCinput1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVCinput1.SetBorderSize( 1 );
        labelVCinput1.SetMultiLineEdit( false );
        labelVCinput1.SetIsNumberEditor( false );
        labelVCinput1.SetNumberEditorRange( 0, 100 );
        labelVCinput1.SetNumberEditorInterval( 1 );
        labelVCinput1.SetNumberEditorUsesMouseWheel( false );
        labelVCinput1.SetHasCustomTextHoverColor( false );
        labelVCinput1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVCinput1.SetFont( "Arial", 13, true, false );

        labelV1 = new VoltageLabel( "labelV1", "Voltage 1 Label", this, "VOLTAGE 1" );
        AddComponent( labelV1 );
        labelV1.SetWantsMouseNotifications( false );
        labelV1.SetPosition( 30, 90 );
        labelV1.SetSize( 55, 13 );
        labelV1.SetEditable( false, false );
        labelV1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV1.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV1.SetBorderSize( 1 );
        labelV1.SetMultiLineEdit( false );
        labelV1.SetIsNumberEditor( false );
        labelV1.SetNumberEditorRange( 0, 100 );
        labelV1.SetNumberEditorInterval( 1 );
        labelV1.SetNumberEditorUsesMouseWheel( false );
        labelV1.SetHasCustomTextHoverColor( false );
        labelV1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV1.SetFont( "Arial", 7, true, false );

        labelVCinput2 = new VoltageLabel( "labelVCinput2", "V.C. Input 2 Label", this, "2" );
        AddComponent( labelVCinput2 );
        labelVCinput2.SetWantsMouseNotifications( false );
        labelVCinput2.SetPosition( 40, 289 );
        labelVCinput2.SetSize( 37, 23 );
        labelVCinput2.SetEditable( false, false );
        labelVCinput2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVCinput2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVCinput2.SetColor( new Color( 46, 46, 46, 255 ) );
        labelVCinput2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVCinput2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVCinput2.SetBorderSize( 1 );
        labelVCinput2.SetMultiLineEdit( false );
        labelVCinput2.SetIsNumberEditor( false );
        labelVCinput2.SetNumberEditorRange( 0, 100 );
        labelVCinput2.SetNumberEditorInterval( 1 );
        labelVCinput2.SetNumberEditorUsesMouseWheel( false );
        labelVCinput2.SetHasCustomTextHoverColor( false );
        labelVCinput2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVCinput2.SetFont( "Arial", 13, true, false );

        labelVCinput3 = new VoltageLabel( "labelVCinput3", "Track/Hold Label", this, "T&H" );
        AddComponent( labelVCinput3 );
        labelVCinput3.SetWantsMouseNotifications( false );
        labelVCinput3.SetPosition( 77, 289 );
        labelVCinput3.SetSize( 32, 23 );
        labelVCinput3.SetEditable( false, false );
        labelVCinput3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVCinput3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVCinput3.SetColor( new Color( 46, 46, 46, 255 ) );
        labelVCinput3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVCinput3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVCinput3.SetBorderSize( 1 );
        labelVCinput3.SetMultiLineEdit( false );
        labelVCinput3.SetIsNumberEditor( false );
        labelVCinput3.SetNumberEditorRange( 0, 100 );
        labelVCinput3.SetNumberEditorInterval( 1 );
        labelVCinput3.SetNumberEditorUsesMouseWheel( false );
        labelVCinput3.SetHasCustomTextHoverColor( false );
        labelVCinput3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVCinput3.SetFont( "Arial", 13, true, false );

        labelV2 = new VoltageLabel( "labelV2", "Voltage 2 Label", this, "VOLTAGE 2" );
        AddComponent( labelV2 );
        labelV2.SetWantsMouseNotifications( false );
        labelV2.SetPosition( 30, 140 );
        labelV2.SetSize( 55, 13 );
        labelV2.SetEditable( false, false );
        labelV2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV2.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV2.SetBorderSize( 1 );
        labelV2.SetMultiLineEdit( false );
        labelV2.SetIsNumberEditor( false );
        labelV2.SetNumberEditorRange( 0, 100 );
        labelV2.SetNumberEditorInterval( 1 );
        labelV2.SetNumberEditorUsesMouseWheel( false );
        labelV2.SetHasCustomTextHoverColor( false );
        labelV2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV2.SetFont( "Arial", 7, true, false );

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 5 );
        imageTTW.SetSize( 31, 10 );
        imageTTW.SetCurrentImage( "image image(2).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "tsl" );
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

        knobV3 = new VoltageKnob( "knobV3", "Voltage 3", this, 0.0, 1.0, 0.0 );
        AddComponent( knobV3 );
        knobV3.SetWantsMouseNotifications( false );
        knobV3.SetPosition( 42, 160 );
        knobV3.SetSize( 31, 31 );
        knobV3.SetSkin( "ARP2500 Sm Silver" );
        knobV3.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobV3.SetKnobParams( 215, 145 );
        knobV3.DisplayValueInPercent( true );
        knobV3.SetKnobAdjustsRing( true );
        knobV3.SetRangeSkewValue( 1.0, true );

        labelV3 = new VoltageLabel( "labelV3", "Voltage 3 Label", this, "VOLTAGE 3" );
        AddComponent( labelV3 );
        labelV3.SetWantsMouseNotifications( false );
        labelV3.SetPosition( 30, 190 );
        labelV3.SetSize( 55, 13 );
        labelV3.SetEditable( false, false );
        labelV3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV3.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV3.SetBorderSize( 1 );
        labelV3.SetMultiLineEdit( false );
        labelV3.SetIsNumberEditor( false );
        labelV3.SetNumberEditorRange( 0, 100 );
        labelV3.SetNumberEditorInterval( 1 );
        labelV3.SetNumberEditorUsesMouseWheel( false );
        labelV3.SetHasCustomTextHoverColor( false );
        labelV3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV3.SetFont( "Arial", 7, true, false );

        outputV1 = new VoltageAudioJack( "outputV1", "Voltage 1 Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputV1 );
        outputV1.SetWantsMouseNotifications( false );
        outputV1.SetPosition( 80, 78 );
        outputV1.SetSize( 25, 25 );
        outputV1.SetSkin( "Mini Jack 25px" );

        outputV2 = new VoltageAudioJack( "outputV2", "Voltage 2 Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputV2 );
        outputV2.SetWantsMouseNotifications( false );
        outputV2.SetPosition( 80, 128 );
        outputV2.SetSize( 25, 25 );
        outputV2.SetSkin( "Mini Jack 25px" );

        outputV3 = new VoltageAudioJack( "outputV3", "Voltage 3 Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputV3 );
        outputV3.SetWantsMouseNotifications( false );
        outputV3.SetPosition( 80, 178 );
        outputV3.SetSize( 25, 25 );
        outputV3.SetSkin( "Mini Jack 25px" );

        labelIn = new VoltageLabel( "labelIn", "In Label", this, "IN" );
        AddComponent( labelIn );
        labelIn.SetWantsMouseNotifications( false );
        labelIn.SetPosition( 4, 58 );
        labelIn.SetSize( 37, 23 );
        labelIn.SetEditable( false, false );
        labelIn.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelIn.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelIn.SetColor( new Color( 46, 46, 46, 255 ) );
        labelIn.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelIn.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelIn.SetBorderSize( 1 );
        labelIn.SetMultiLineEdit( false );
        labelIn.SetIsNumberEditor( false );
        labelIn.SetNumberEditorRange( 0, 100 );
        labelIn.SetNumberEditorInterval( 1 );
        labelIn.SetNumberEditorUsesMouseWheel( false );
        labelIn.SetHasCustomTextHoverColor( false );
        labelIn.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelIn.SetFont( "Arial", 13, true, false );

        labelOut = new VoltageLabel( "labelOut", "Output Label", this, "OUT" );
        AddComponent( labelOut );
        labelOut.SetWantsMouseNotifications( false );
        labelOut.SetPosition( 77, 59 );
        labelOut.SetSize( 32, 23 );
        labelOut.SetEditable( false, false );
        labelOut.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOut.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOut.SetColor( new Color( 46, 46, 46, 255 ) );
        labelOut.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOut.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOut.SetBorderSize( 1 );
        labelOut.SetMultiLineEdit( false );
        labelOut.SetIsNumberEditor( false );
        labelOut.SetNumberEditorRange( 0, 100 );
        labelOut.SetNumberEditorInterval( 1 );
        labelOut.SetNumberEditorUsesMouseWheel( false );
        labelOut.SetHasCustomTextHoverColor( false );
        labelOut.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOut.SetFont( "Arial", 13, true, false );

        labelPower = new VoltageLabel( "labelPower", "Power Label", this, "POWER" );
        AddComponent( labelPower );
        labelPower.SetWantsMouseNotifications( false );
        labelPower.SetPosition( 30, 205 );
        labelPower.SetSize( 55, 13 );
        labelPower.SetEditable( false, false );
        labelPower.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelPower.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelPower.SetColor( new Color( 46, 46, 46, 255 ) );
        labelPower.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelPower.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelPower.SetBorderSize( 1 );
        labelPower.SetMultiLineEdit( false );
        labelPower.SetIsNumberEditor( false );
        labelPower.SetNumberEditorRange( 0, 100 );
        labelPower.SetNumberEditorInterval( 1 );
        labelPower.SetNumberEditorUsesMouseWheel( false );
        labelPower.SetHasCustomTextHoverColor( false );
        labelPower.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelPower.SetFont( "Arial", 7, true, false );

        labelOn = new VoltageLabel( "labelOn", "On Label", this, "ON" );
        AddComponent( labelOn );
        labelOn.SetWantsMouseNotifications( false );
        labelOn.SetPosition( 30, 230 );
        labelOn.SetSize( 55, 13 );
        labelOn.SetEditable( false, false );
        labelOn.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOn.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOn.SetColor( new Color( 46, 46, 46, 255 ) );
        labelOn.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOn.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOn.SetBorderSize( 1 );
        labelOn.SetMultiLineEdit( false );
        labelOn.SetIsNumberEditor( false );
        labelOn.SetNumberEditorRange( 0, 100 );
        labelOn.SetNumberEditorInterval( 1 );
        labelOn.SetNumberEditorUsesMouseWheel( false );
        labelOn.SetHasCustomTextHoverColor( false );
        labelOn.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOn.SetFont( "Arial", 7, true, false );

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(Seattle) Ltd" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 0, 11 );
        labelLocation.SetSize( 39, 13 );
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
        labelLocation.SetFont( "Arial", 5, true, false );

        inputV1 = new VoltageAudioJack( "inputV1", "Voltage 1 Input", this, JackType.JackType_AudioInput );
        AddComponent( inputV1 );
        inputV1.SetWantsMouseNotifications( false );
        inputV1.SetPosition( 10, 77 );
        inputV1.SetSize( 25, 25 );
        inputV1.SetSkin( "Jack Round 25px" );

        inputV2 = new VoltageAudioJack( "inputV2", "Voltage 2 Input", this, JackType.JackType_AudioInput );
        AddComponent( inputV2 );
        inputV2.SetWantsMouseNotifications( false );
        inputV2.SetPosition( 10, 127 );
        inputV2.SetSize( 25, 25 );
        inputV2.SetSkin( "Jack Round 25px" );

        inputV3 = new VoltageAudioJack( "inputV3", "Voltage 3 Input", this, JackType.JackType_AudioInput );
        AddComponent( inputV3 );
        inputV3.SetWantsMouseNotifications( false );
        inputV3.SetPosition( 10, 177 );
        inputV3.SetSize( 25, 25 );
        inputV3.SetSkin( "Jack Round 25px" );
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

        // This module is permanently powered; the lamp is a courtesy indicator.
        lampPower.SetValue(1.0);
        resumePending.set(false);

    



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
        switch( notification )
        {
            case Knob_Changed:   // doubleValue is the new VoltageKnob value
            {
            }
            break;
        
            case Slider_Changed:   // doubleValue is the new slider value
            {
            }
            break;
        
            case Button_Changed:   // doubleValue is the new button/toggle button value
            {
            }
            break;
        
            case Switch_Changed:   // doubleValue is the new switch value
            {
            }
            break;
        
            case Jack_Connected:   // longValue is the new cable ID
            {
            }
            break;
        
            case Jack_Disconnected:   // All cables have been disconnected from this jack
            {
            }
            break;
        
            case GUI_Update_Timer:   // Called every 50ms (by default) if turned on
            {
            }
            break;
        
            case Object_MouseMove:   // called when mouse is over an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_MouseLeave:  // called when mouse leaves an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_LeftButtonDown:   // called when user left-clicks on an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_LeftButtonUp:   // called when user releases left mouse button on an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_RightButtonDown:   // called when user releases right mouse button on an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_RightButtonUp:   // called when user right-clicks on an object that receives mouse notifications
            {
            }
            break;
        
            case Object_LeftButtonDoubleClick: // called when user left-button double-clicks on an object that receives mouse notifications
            {
            }
            break;
        
            // Less common notifications:
        
            case Named_Timer:   // object contains a String with the name of the timer that has fired
            {
            }
            break;
        
            case Canvas_Painting:   // About to paint canvas.  object is a java.awt.Rectangle with painting boundaries
            {
            }
            break;
        
            case Canvas_Painted:   // Canvas painting is complete
            {
            }
            break;
        
            case Control_DragStart:    // A user has started dragging on a control that has been marked as draggable
            {
            }
            break;
        
            case Control_DragOn:       // This control has been dragged over during a drag operation. object contains the dragged object
            {
            }
            break;
        
            case Control_DragOff:      // This control has been dragged over during a drag operation. object contains the dragged object
            {
            }
            break;
        
            case Control_DragEnd:      // A user has ended their drag on a control that has been marked as draggable
            {
            }
            break;
        
            case Label_Changed:        // The text of an editable text control has changed
            {
            }
            break;
        
            case SoundPlayback_Start:   // A sound has begun playback
            {
            }
            break;
        
            case SoundPlayback_End:     // A sound has ended playback
            {
            }
            break;
        
            case Scrollbar_Position:    // longValue is the new scrollbar position
            {
            }
            break;
        
            case PolyVoices_Changed:    // longValue is the new number of poly voices
            {
            }
            break;
        
            case File_Dropped:     // 'object' is a String containing the file path
            {
            }
            break;
        
            case Preset_Loading_Start:   // called when preset loading begins
            {
            }
            break;
        
            case Preset_Loading_Finish:  // called when preset loading finishes
            {
                resumePending.set(true);
            }
            break;
        
            case Variation_Loading_Start:    // sent when a variation is about to load
            {
            }
            break;
        
            case Variation_Loading_Finish:   // sent when a variation has just finished loading
            {
                resumePending.set(true);
            }
            break;
        
            case Tempo_Changed:     // doubleValue is the new tempo
            {
            }
            break;
        
            case Randomized:     // called when the module's controls get randomized
            {
            }
            break;
        
            case VariationListChanged:   // sent when a variation gets added, deleted, or renamed, or the variations list gets reordered
            {
            }
            break;
        
            case Key_Press:     // sent when module has keyboard focus and a key is pressed; object is a VoltageKeyPressInfo object
            {
            }
            break;
        
            case Reset:    // sent when the module has been reset to default settings
            {
                resumePending.set(true);
            }
            break;
        
            case Keyboard_NoteOn:   // sent when a note has been pressed on a VoltageKeyboard object. longValue is the note value ( 0-127 )
            {
            }
            break;
        
            case Keyboard_NoteOff:   // sent when a note has been released on a VoltageKeyboard object. longValue is the note value ( 0-127 )
            {
            }
            break;
        
            case Curve_Changed:   // sent when user has edited a curve's value. 'object' will be a VoltageCurve.CurveChangeNotification object.
            {
            }
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

        // EMS-inspired slew / track-and-hold engine
        if (resumePending.getAndSet(false)) {
            currentVolt1 = readInput(inputV1);
            currentVolt2 = readInput(inputV2);
            currentVolt3 = readInput(inputV3);
        }

        double dt = SAMPLE_PERIOD;

        // --- CHANNEL 1 (Voltage-controlled slew) ---
        double input1 = readInput(inputV1);
        double cv1 = readInput(inputVC1);
        double maxRate1 = baseRateForKnob(clamp01(knobV1.GetValue() + (cv1 / 5.0)));
        double maxChange1 = maxRate1 * dt;

        double diff1 = input1 - currentVolt1;
        if (diff1 > maxChange1)       currentVolt1 += maxChange1;
        else if (diff1 < -maxChange1) currentVolt1 -= maxChange1;
        else                          currentVolt1 = input1;
        outputV1.SetValue(currentVolt1);

        // --- CHANNEL 2 (Voltage-controlled slew) ---
        double input2 = readInput(inputV2);
        double cv2 = readInput(inputVC2);
        double maxRate2 = baseRateForKnob(clamp01(knobV2.GetValue() + (cv2 / 5.0)));
        double maxChange2 = maxRate2 * dt;

        double diff2 = input2 - currentVolt2;
        if (diff2 > maxChange2)       currentVolt2 += maxChange2;
        else if (diff2 < -maxChange2) currentVolt2 -= maxChange2;
        else                          currentVolt2 = input2;
        outputV2.SetValue(currentVolt2);

        // --- CHANNEL 3 (Slew / Track & Hold) ---
        double input3 = readInput(inputV3);
        double gate3 = readInput(inputVC3);
        boolean trackMode3 = !inputVC3.IsConnected() || gate3 > 3.0;

        if (trackMode3) {
            double maxRate3 = baseRateForKnob(knobV3.GetValue());
            double maxChange3 = maxRate3 * dt;
            double diff3 = input3 - currentVolt3;
            if (diff3 > maxChange3)       currentVolt3 += maxChange3;
            else if (diff3 < -maxChange3) currentVolt3 -= maxChange3;
            else                          currentVolt3 = input3;
        }

        outputV3.SetValue(currentVolt3);
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


        // Direct host bypass: no control/CV reads and no history advancement.
        outputV1.SetValue(inputV1.GetValue());
        outputV2.SetValue(inputV2.GetValue());
        outputV3.SetValue(inputV3.GetValue());
        resumePending.set(true);
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
        if (component == knobV1) return slewTooltip(1, knobV1.GetValue());
        if (component == knobV2) return slewTooltip(2, knobV2.GetValue());
        if (component == knobV3) return slewTooltip(3, knobV3.GetValue());

        if (component == inputVC1 || component == inputVC2 || component == inputVC3)
            return component == inputVC3 ? "V.C. 3: TRACK/HOLD gate; unpatched or >3 V tracks, <=3 V holds" : "V.C. 1/2: +5 V offsets one full knob travel toward FAST; -5 V toward SLOW";

        if (component == lampPower)
            return "Power indicator: module is permanently powered";


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
        if (!Double.isFinite(newValue))
            return;

        if (component == knobV1 || component == knobV2 || component == knobV3)
        {
            ((VoltageKnob)component).SetValue(knobForRate(newValue));
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
    private VoltageAudioJack inputV3;
    private VoltageAudioJack inputV2;
    private VoltageAudioJack inputV1;
    private VoltageLabel labelLocation;
    private VoltageLabel labelOn;
    private VoltageLabel labelPower;
    private VoltageLabel labelOut;
    private VoltageLabel labelIn;
    private VoltageAudioJack outputV3;
    private VoltageAudioJack outputV2;
    private VoltageAudioJack outputV1;
    private VoltageLabel labelV3;
    private VoltageKnob knobV3;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelV2;
    private VoltageLabel labelVCinput3;
    private VoltageLabel labelVCinput2;
    private VoltageLabel labelV1;
    private VoltageLabel labelVCinput1;
    private VoltageLabel labelFooter;
    private VoltageAudioJack inputVC3;
    private VoltageAudioJack inputVC2;
    private VoltageAudioJack inputVC1;
    private VoltageLED lampPower;
    private VoltageKnob knobV2;
    private VoltageKnob knobV1;
    private VoltageLabel labelVoltage;
    private VoltageLabel labelVbars;
    private VoltageLabel labelV4;
    private VoltageLabel labelBars;


    //[user-code-and-variables]    Add your own variables and functions here
    // Persistent channel voltages; behavioral integrator model, not a circuit simulation.
    private double currentVolt1 = 0.0;
    private double currentVolt2 = 0.0;
    private double currentVolt3 = 0.0;
    private final AtomicBoolean resumePending = new AtomicBoolean(false);

    // Fixed host callback rate; range informed by the Sequencer 256 manual.
    private static final double SAMPLE_RATE = 48000.0;
    private static final double SAMPLE_PERIOD = 1.0 / SAMPLE_RATE;
    private static final double MAX_SPEED = 1000.0; // 1000 V/s (fastest rate)
    private static final double MIN_SPEED = 0.05;   // 0.05 V/s (slowest rate)

    private static double readInput(VoltageAudioJack jack)
    {
        if (!jack.IsConnected()) return 0.0;

        double value = jack.GetValue();
        if (!Double.isFinite(value)) return 0.0;

        return Math.max(-1.0e6, Math.min(1.0e6, value));
    }

    private static double clamp01(double value)
    {
        if (!Double.isFinite(value)) return 0.0;
        return Math.max(0.0, Math.min(1.0, value));
    }

    private static double baseRateForKnob(double knob)
    {
        knob = clamp01(knob);
        return MIN_SPEED * Math.pow(MAX_SPEED / MIN_SPEED, knob);
    }

    private static double knobForRate(double rate)
    {
        if (!Double.isFinite(rate)) return 0.0;

        rate = Math.max(MIN_SPEED, Math.min(MAX_SPEED, rate));
        return clamp01(
                Math.log(rate / MIN_SPEED)
                / Math.log(MAX_SPEED / MIN_SPEED)
        );
    }

    private static String slewTooltip(int channel, double knob)
    {
        double rate = baseRateForKnob(knob);
        double oneVoltTime = 1.0 / rate;
        double tenVoltTime = 10.0 / rate;

        return String.format(
                java.util.Locale.ROOT,
                "Channel %d: %.4g V/s; 1 V: %.4g s; 10 V: %.4g s; CCW slow / CW fast; enter V/s",
                channel,
                rate,
                oneVoltTime,
                tenVoltTime
        );
    }
    //[/user-code-and-variables]
}

 