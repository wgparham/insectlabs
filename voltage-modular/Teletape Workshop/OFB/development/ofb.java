package com.insectlabs.ofb;


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


public class ofb extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public ofb( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "8 Octave Filter Bank", ModuleType.ModuleType_Filters, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "2ecf9d9540784e3f9762d02fa5a89092" );
    }

void InitializeControls()
{

        knob63 = new VoltageKnob( "knob63", "63 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob63 );
        knob63.SetWantsMouseNotifications( false );
        knob63.SetPosition( 31, 26 );
        knob63.SetSize( 25, 25 );
        knob63.SetSkin( "ARP2500 Sm Silver" );
        knob63.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob63.SetKnobParams( 215, 145 );
        knob63.DisplayValueInPercent( true );
        knob63.SetKnobAdjustsRing( true );

        knob500 = new VoltageKnob( "knob500", "500 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob500 );
        knob500.SetWantsMouseNotifications( false );
        knob500.SetPosition( 31, 131 );
        knob500.SetSize( 25, 25 );
        knob500.SetSkin( "ARP2500 Sm Silver" );
        knob500.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob500.SetKnobParams( 215, 145 );
        knob500.DisplayValueInPercent( true );
        knob500.SetKnobAdjustsRing( true );

        knob125 = new VoltageKnob( "knob125", "125 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob125 );
        knob125.SetWantsMouseNotifications( false );
        knob125.SetPosition( 31, 61 );
        knob125.SetSize( 25, 25 );
        knob125.SetSkin( "ARP2500 Sm Silver" );
        knob125.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob125.SetKnobParams( 215, 145 );
        knob125.DisplayValueInPercent( true );
        knob125.SetKnobAdjustsRing( true );

        knob1000 = new VoltageKnob( "knob1000", "1000 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob1000 );
        knob1000.SetWantsMouseNotifications( false );
        knob1000.SetPosition( 31, 166 );
        knob1000.SetSize( 25, 25 );
        knob1000.SetSkin( "ARP2500 Sm Silver" );
        knob1000.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob1000.SetKnobParams( 215, 145 );
        knob1000.DisplayValueInPercent( true );
        knob1000.SetKnobAdjustsRing( true );

        knob4000 = new VoltageKnob( "knob4000", "4000 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob4000 );
        knob4000.SetWantsMouseNotifications( false );
        knob4000.SetPosition( 31, 236 );
        knob4000.SetSize( 25, 25 );
        knob4000.SetSkin( "ARP2500 Sm Silver" );
        knob4000.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob4000.SetKnobParams( 215, 145 );
        knob4000.DisplayValueInPercent( true );
        knob4000.SetKnobAdjustsRing( true );

        output125 = new VoltageAudioJack( "output125", "125 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output125 );
        output125.SetWantsMouseNotifications( false );
        output125.SetPosition( 58, 61 );
        output125.SetSize( 25, 25 );
        output125.SetSkin( "Mini Jack 25px" );

        output250 = new VoltageAudioJack( "output250", "250 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output250 );
        output250.SetWantsMouseNotifications( false );
        output250.SetPosition( 58, 96 );
        output250.SetSize( 25, 25 );
        output250.SetSkin( "Mini Jack 25px" );

        output500 = new VoltageAudioJack( "output500", "500 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output500 );
        output500.SetWantsMouseNotifications( false );
        output500.SetPosition( 58, 131 );
        output500.SetSize( 25, 25 );
        output500.SetSkin( "Mini Jack 25px" );

        labelFooter = new VoltageLabel( "labelFooter", "Footer", this, "8 Octave Filter Bank" );
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

        output1000 = new VoltageAudioJack( "output1000", "1000 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output1000 );
        output1000.SetWantsMouseNotifications( false );
        output1000.SetPosition( 58, 166 );
        output1000.SetSize( 25, 25 );
        output1000.SetSkin( "Mini Jack 25px" );

        output2000 = new VoltageAudioJack( "output2000", "2000 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output2000 );
        output2000.SetWantsMouseNotifications( false );
        output2000.SetPosition( 58, 201 );
        output2000.SetSize( 25, 25 );
        output2000.SetSkin( "Mini Jack 25px" );

        labelV4 = new VoltageLabel( "labelV4", "Voltage 1 Label_1", this, "500 Hz" );
        AddComponent( labelV4 );
        labelV4.SetWantsMouseNotifications( false );
        labelV4.SetPosition( 32, 154 );
        labelV4.SetSize( 26, 12 );
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
        labelV4.SetFont( "Arial", 7, true, false );

        output4000 = new VoltageAudioJack( "output4000", "4000 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output4000 );
        output4000.SetWantsMouseNotifications( false );
        output4000.SetPosition( 58, 236 );
        output4000.SetSize( 25, 25 );
        output4000.SetSkin( "Mini Jack 25px" );

        labelV1 = new VoltageLabel( "labelV1", "Voltage 1 Label", this, "63 Hz" );
        AddComponent( labelV1 );
        labelV1.SetWantsMouseNotifications( false );
        labelV1.SetPosition( 29, 49 );
        labelV1.SetSize( 26, 12 );
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

        output8000 = new VoltageAudioJack( "output8000", "8000 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output8000 );
        output8000.SetWantsMouseNotifications( false );
        output8000.SetPosition( 58, 271 );
        output8000.SetSize( 25, 25 );
        output8000.SetSkin( "Mini Jack 25px" );

        output63 = new VoltageAudioJack( "output63", "63 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output63 );
        output63.SetWantsMouseNotifications( false );
        output63.SetPosition( 58, 26 );
        output63.SetSize( 25, 25 );
        output63.SetSkin( "Mini Jack 25px" );

        labelV5 = new VoltageLabel( "labelV5", "Voltage 2 Label_1", this, "1000 Hz" );
        AddComponent( labelV5 );
        labelV5.SetWantsMouseNotifications( false );
        labelV5.SetPosition( 32, 189 );
        labelV5.SetSize( 26, 12 );
        labelV5.SetEditable( false, false );
        labelV5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV5.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV5.SetBorderSize( 1 );
        labelV5.SetMultiLineEdit( false );
        labelV5.SetIsNumberEditor( false );
        labelV5.SetNumberEditorRange( 0, 100 );
        labelV5.SetNumberEditorInterval( 1 );
        labelV5.SetNumberEditorUsesMouseWheel( false );
        labelV5.SetHasCustomTextHoverColor( false );
        labelV5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV5.SetFont( "Arial", 7, true, false );

        labelV2 = new VoltageLabel( "labelV2", "Voltage 2 Label", this, "125 Hz" );
        AddComponent( labelV2 );
        labelV2.SetWantsMouseNotifications( false );
        labelV2.SetPosition( 32, 84 );
        labelV2.SetSize( 26, 12 );
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

        labelV7 = new VoltageLabel( "labelV7", "Voltage 2 Label_2", this, "4000 Hz" );
        AddComponent( labelV7 );
        labelV7.SetWantsMouseNotifications( false );
        labelV7.SetPosition( 32, 259 );
        labelV7.SetSize( 26, 12 );
        labelV7.SetEditable( false, false );
        labelV7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV7.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV7.SetBorderSize( 1 );
        labelV7.SetMultiLineEdit( false );
        labelV7.SetIsNumberEditor( false );
        labelV7.SetNumberEditorRange( 0, 100 );
        labelV7.SetNumberEditorInterval( 1 );
        labelV7.SetNumberEditorUsesMouseWheel( false );
        labelV7.SetHasCustomTextHoverColor( false );
        labelV7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV7.SetFont( "Arial", 7, true, false );

        labelV9 = new VoltageLabel( "labelV9", "Voltage 2 Label_3", this, "8000 Hz" );
        AddComponent( labelV9 );
        labelV9.SetWantsMouseNotifications( false );
        labelV9.SetPosition( 32, 294 );
        labelV9.SetSize( 26, 12 );
        labelV9.SetEditable( false, false );
        labelV9.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV9.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV9.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV9.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV9.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV9.SetBorderSize( 1 );
        labelV9.SetMultiLineEdit( false );
        labelV9.SetIsNumberEditor( false );
        labelV9.SetNumberEditorRange( 0, 100 );
        labelV9.SetNumberEditorInterval( 1 );
        labelV9.SetNumberEditorUsesMouseWheel( false );
        labelV9.SetHasCustomTextHoverColor( false );
        labelV9.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV9.SetFont( "Arial", 7, true, false );

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 5 );
        imageTTW.SetSize( 31, 10 );
        imageTTW.SetCurrentImage( "image image.svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "ofb" );
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

        knob250 = new VoltageKnob( "knob250", "250 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob250 );
        knob250.SetWantsMouseNotifications( false );
        knob250.SetPosition( 31, 96 );
        knob250.SetSize( 25, 25 );
        knob250.SetSkin( "ARP2500 Sm Silver" );
        knob250.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob250.SetKnobParams( 215, 145 );
        knob250.DisplayValueInPercent( true );
        knob250.SetKnobAdjustsRing( true );

        knob2000 = new VoltageKnob( "knob2000", "2000 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob2000 );
        knob2000.SetWantsMouseNotifications( false );
        knob2000.SetPosition( 31, 201 );
        knob2000.SetSize( 25, 25 );
        knob2000.SetSkin( "ARP2500 Sm Silver" );
        knob2000.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob2000.SetKnobParams( 215, 145 );
        knob2000.DisplayValueInPercent( true );
        knob2000.SetKnobAdjustsRing( true );

        knob8000 = new VoltageKnob( "knob8000", "8000 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob8000 );
        knob8000.SetWantsMouseNotifications( false );
        knob8000.SetPosition( 31, 271 );
        knob8000.SetSize( 25, 25 );
        knob8000.SetSkin( "ARP2500 Sm Silver" );
        knob8000.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob8000.SetKnobParams( 215, 145 );
        knob8000.DisplayValueInPercent( true );
        knob8000.SetKnobAdjustsRing( true );

        labelV6 = new VoltageLabel( "labelV6", "Voltage3 Label_1", this, "2000 Hz" );
        AddComponent( labelV6 );
        labelV6.SetWantsMouseNotifications( false );
        labelV6.SetPosition( 32, 224 );
        labelV6.SetSize( 26, 12 );
        labelV6.SetEditable( false, false );
        labelV6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV6.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV6.SetBorderSize( 1 );
        labelV6.SetMultiLineEdit( false );
        labelV6.SetIsNumberEditor( false );
        labelV6.SetNumberEditorRange( 0, 100 );
        labelV6.SetNumberEditorInterval( 1 );
        labelV6.SetNumberEditorUsesMouseWheel( false );
        labelV6.SetHasCustomTextHoverColor( false );
        labelV6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV6.SetFont( "Arial", 7, true, false );

        labelV3 = new VoltageLabel( "labelV3", "Voltage3 Label", this, "250 Hz" );
        AddComponent( labelV3 );
        labelV3.SetWantsMouseNotifications( false );
        labelV3.SetPosition( 32, 119 );
        labelV3.SetSize( 26, 12 );
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

        output = new VoltageAudioJack( "output", "Output", this, JackType.JackType_AudioOutput );
        AddComponent( output );
        output.SetWantsMouseNotifications( false );
        output.SetPosition( 83, 306 );
        output.SetSize( 25, 25 );
        output.SetSkin( "Mini Jack 25px" );

        labelIn = new VoltageLabel( "labelIn", "In Label", this, "IN" );
        AddComponent( labelIn );
        labelIn.SetWantsMouseNotifications( false );
        labelIn.SetPosition( 0, 286 );
        labelIn.SetSize( 36, 23 );
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
        labelOut.SetPosition( 78, 286 );
        labelOut.SetSize( 36, 23 );
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

        input = new VoltageAudioJack( "input", "Input", this, JackType.JackType_AudioInput );
        AddComponent( input );
        input.SetWantsMouseNotifications( false );
        input.SetPosition( 5, 306 );
        input.SetSize( 25, 25 );
        input.SetSkin( "Jack Round 25px" );
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
            }
            break;
        
            case Variation_Loading_Start:    // sent when a variation is about to load
            {
            }
            break;
        
            case Variation_Loading_Finish:   // sent when a variation has just finished loading
            {
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
        outputV1.SetValue(0.0);
        outputV2.SetValue(0.0);
        outputV3.SetValue(0.0);  
                                    
                                    
                                          
                                 
                             

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
    private VoltageAudioJack input;
    private VoltageLabel labelLocation;
    private VoltageLabel labelOut;
    private VoltageLabel labelIn;
    private VoltageAudioJack output;
    private VoltageLabel labelV3;
    private VoltageLabel labelV6;
    private VoltageKnob knob8000;
    private VoltageKnob knob2000;
    private VoltageKnob knob250;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelV9;
    private VoltageLabel labelV7;
    private VoltageLabel labelV2;
    private VoltageLabel labelV5;
    private VoltageAudioJack output63;
    private VoltageAudioJack output8000;
    private VoltageLabel labelV1;
    private VoltageAudioJack output4000;
    private VoltageLabel labelV4;
    private VoltageAudioJack output2000;
    private VoltageAudioJack output1000;
    private VoltageLabel labelFooter;
    private VoltageAudioJack output500;
    private VoltageAudioJack output250;
    private VoltageAudioJack output125;
    private VoltageKnob knob4000;
    private VoltageKnob knob1000;
    private VoltageKnob knob125;
    private VoltageKnob knob500;
    private VoltageKnob knob63;


    //[user-code-and-variables]    Add your own variables and functions here
    //[/user-code-and-variables]





}

 