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
import java.util.concurrent.atomic.AtomicBoolean;
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
        SetSkin( "7063778187fe479b9eb0ebec923ad4f2" );
    }

void InitializeControls()
{

        knob63Hz = new VoltageKnob( "knob63Hz", "63 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob63Hz );
        knob63Hz.SetWantsMouseNotifications( false );
        knob63Hz.SetPosition( 45, 25 );
        knob63Hz.SetSize( 25, 25 );
        knob63Hz.SetSkin( "ARP2500 Sm Silver" );
        knob63Hz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob63Hz.SetKnobParams( 215, 145 );
        knob63Hz.DisplayValueInPercent( true );
        knob63Hz.SetKnobAdjustsRing( true );

        knob500Hz = new VoltageKnob( "knob500Hz", "500 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob500Hz );
        knob500Hz.SetWantsMouseNotifications( false );
        knob500Hz.SetPosition( 45, 130 );
        knob500Hz.SetSize( 25, 25 );
        knob500Hz.SetSkin( "ARP2500 Sm Silver" );
        knob500Hz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob500Hz.SetKnobParams( 215, 145 );
        knob500Hz.DisplayValueInPercent( true );
        knob500Hz.SetKnobAdjustsRing( true );

        knob125Hz = new VoltageKnob( "knob125Hz", "125 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob125Hz );
        knob125Hz.SetWantsMouseNotifications( false );
        knob125Hz.SetPosition( 45, 60 );
        knob125Hz.SetSize( 25, 25 );
        knob125Hz.SetSkin( "ARP2500 Sm Silver" );
        knob125Hz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob125Hz.SetKnobParams( 215, 145 );
        knob125Hz.DisplayValueInPercent( true );
        knob125Hz.SetKnobAdjustsRing( true );

        knob1kHz = new VoltageKnob( "knob1kHz", "1 kHz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob1kHz );
        knob1kHz.SetWantsMouseNotifications( false );
        knob1kHz.SetPosition( 46, 165 );
        knob1kHz.SetSize( 25, 25 );
        knob1kHz.SetSkin( "ARP2500 Sm Silver" );
        knob1kHz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob1kHz.SetKnobParams( 215, 145 );
        knob1kHz.DisplayValueInPercent( true );
        knob1kHz.SetKnobAdjustsRing( true );

        knob4kHz = new VoltageKnob( "knob4kHz", "4 kHz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob4kHz );
        knob4kHz.SetWantsMouseNotifications( false );
        knob4kHz.SetPosition( 45, 235 );
        knob4kHz.SetSize( 25, 25 );
        knob4kHz.SetSkin( "ARP2500 Sm Silver" );
        knob4kHz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob4kHz.SetKnobParams( 215, 145 );
        knob4kHz.DisplayValueInPercent( true );
        knob4kHz.SetKnobAdjustsRing( true );

        jack125HzOut = new VoltageAudioJack( "jack125HzOut", "125 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack125HzOut );
        jack125HzOut.SetWantsMouseNotifications( false );
        jack125HzOut.SetPosition( 75, 60 );
        jack125HzOut.SetSize( 25, 25 );
        jack125HzOut.SetSkin( "Mini Jack 25px" );

        jack250HzOut = new VoltageAudioJack( "jack250HzOut", "250 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack250HzOut );
        jack250HzOut.SetWantsMouseNotifications( false );
        jack250HzOut.SetPosition( 75, 95 );
        jack250HzOut.SetSize( 25, 25 );
        jack250HzOut.SetSkin( "Mini Jack 25px" );

        jack500HzOut = new VoltageAudioJack( "jack500HzOut", "500 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack500HzOut );
        jack500HzOut.SetWantsMouseNotifications( false );
        jack500HzOut.SetPosition( 75, 130 );
        jack500HzOut.SetSize( 25, 25 );
        jack500HzOut.SetSkin( "Mini Jack 25px" );

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

        jack1kHzOut = new VoltageAudioJack( "jack1kHzOut", "1 kHz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack1kHzOut );
        jack1kHzOut.SetWantsMouseNotifications( false );
        jack1kHzOut.SetPosition( 75, 165 );
        jack1kHzOut.SetSize( 25, 25 );
        jack1kHzOut.SetSkin( "Mini Jack 25px" );

        jack2kHzOut = new VoltageAudioJack( "jack2kHzOut", "2 kHz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack2kHzOut );
        jack2kHzOut.SetWantsMouseNotifications( false );
        jack2kHzOut.SetPosition( 75, 200 );
        jack2kHzOut.SetSize( 25, 25 );
        jack2kHzOut.SetSkin( "Mini Jack 25px" );

        label500Hz = new VoltageLabel( "label500Hz", "500 Hz Label", this, "500 Hz" );
        AddComponent( label500Hz );
        label500Hz.SetWantsMouseNotifications( false );
        label500Hz.SetPosition( 46, 153 );
        label500Hz.SetSize( 26, 12 );
        label500Hz.SetEditable( false, false );
        label500Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label500Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label500Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label500Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label500Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label500Hz.SetBorderSize( 1 );
        label500Hz.SetMultiLineEdit( false );
        label500Hz.SetIsNumberEditor( false );
        label500Hz.SetNumberEditorRange( 0, 100 );
        label500Hz.SetNumberEditorInterval( 1 );
        label500Hz.SetNumberEditorUsesMouseWheel( false );
        label500Hz.SetHasCustomTextHoverColor( false );
        label500Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label500Hz.SetFont( "Arial", 7, true, false );

        jack4kHzOut = new VoltageAudioJack( "jack4kHzOut", "4 kHz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack4kHzOut );
        jack4kHzOut.SetWantsMouseNotifications( false );
        jack4kHzOut.SetPosition( 75, 235 );
        jack4kHzOut.SetSize( 25, 25 );
        jack4kHzOut.SetSkin( "Mini Jack 25px" );

        label63Hz = new VoltageLabel( "label63Hz", "63 Hz Label", this, "63 Hz" );
        AddComponent( label63Hz );
        label63Hz.SetWantsMouseNotifications( false );
        label63Hz.SetPosition( 43, 48 );
        label63Hz.SetSize( 26, 12 );
        label63Hz.SetEditable( false, false );
        label63Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label63Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label63Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label63Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label63Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label63Hz.SetBorderSize( 1 );
        label63Hz.SetMultiLineEdit( false );
        label63Hz.SetIsNumberEditor( false );
        label63Hz.SetNumberEditorRange( 0, 100 );
        label63Hz.SetNumberEditorInterval( 1 );
        label63Hz.SetNumberEditorUsesMouseWheel( false );
        label63Hz.SetHasCustomTextHoverColor( false );
        label63Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label63Hz.SetFont( "Arial", 7, true, false );

        jack8kHzOut = new VoltageAudioJack( "jack8kHzOut", "8 kHz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack8kHzOut );
        jack8kHzOut.SetWantsMouseNotifications( false );
        jack8kHzOut.SetPosition( 75, 270 );
        jack8kHzOut.SetSize( 25, 25 );
        jack8kHzOut.SetSkin( "Mini Jack 25px" );

        jack63HzOut = new VoltageAudioJack( "jack63HzOut", "63 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( jack63HzOut );
        jack63HzOut.SetWantsMouseNotifications( false );
        jack63HzOut.SetPosition( 75, 25 );
        jack63HzOut.SetSize( 25, 25 );
        jack63HzOut.SetSkin( "Mini Jack 25px" );

        label1000Hz = new VoltageLabel( "label1000Hz", "1000 Hz Label", this, "1000 Hz" );
        AddComponent( label1000Hz );
        label1000Hz.SetWantsMouseNotifications( false );
        label1000Hz.SetPosition( 46, 188 );
        label1000Hz.SetSize( 26, 12 );
        label1000Hz.SetEditable( false, false );
        label1000Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label1000Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label1000Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label1000Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label1000Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label1000Hz.SetBorderSize( 1 );
        label1000Hz.SetMultiLineEdit( false );
        label1000Hz.SetIsNumberEditor( false );
        label1000Hz.SetNumberEditorRange( 0, 100 );
        label1000Hz.SetNumberEditorInterval( 1 );
        label1000Hz.SetNumberEditorUsesMouseWheel( false );
        label1000Hz.SetHasCustomTextHoverColor( false );
        label1000Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label1000Hz.SetFont( "Arial", 7, true, false );

        label125Hz = new VoltageLabel( "label125Hz", "125 Hz Label", this, "125 Hz" );
        AddComponent( label125Hz );
        label125Hz.SetWantsMouseNotifications( false );
        label125Hz.SetPosition( 46, 83 );
        label125Hz.SetSize( 26, 12 );
        label125Hz.SetEditable( false, false );
        label125Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label125Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label125Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label125Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label125Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label125Hz.SetBorderSize( 1 );
        label125Hz.SetMultiLineEdit( false );
        label125Hz.SetIsNumberEditor( false );
        label125Hz.SetNumberEditorRange( 0, 100 );
        label125Hz.SetNumberEditorInterval( 1 );
        label125Hz.SetNumberEditorUsesMouseWheel( false );
        label125Hz.SetHasCustomTextHoverColor( false );
        label125Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label125Hz.SetFont( "Arial", 7, true, false );

        label4000Hz = new VoltageLabel( "label4000Hz", "4000 Hz Label", this, "4000 Hz" );
        AddComponent( label4000Hz );
        label4000Hz.SetWantsMouseNotifications( false );
        label4000Hz.SetPosition( 46, 258 );
        label4000Hz.SetSize( 26, 12 );
        label4000Hz.SetEditable( false, false );
        label4000Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label4000Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label4000Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label4000Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label4000Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label4000Hz.SetBorderSize( 1 );
        label4000Hz.SetMultiLineEdit( false );
        label4000Hz.SetIsNumberEditor( false );
        label4000Hz.SetNumberEditorRange( 0, 100 );
        label4000Hz.SetNumberEditorInterval( 1 );
        label4000Hz.SetNumberEditorUsesMouseWheel( false );
        label4000Hz.SetHasCustomTextHoverColor( false );
        label4000Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label4000Hz.SetFont( "Arial", 7, true, false );

        label8000Hz = new VoltageLabel( "label8000Hz", "8000 Hz Label", this, "8000 Hz" );
        AddComponent( label8000Hz );
        label8000Hz.SetWantsMouseNotifications( false );
        label8000Hz.SetPosition( 44, 294 );
        label8000Hz.SetSize( 26, 12 );
        label8000Hz.SetEditable( false, false );
        label8000Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label8000Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label8000Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label8000Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label8000Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label8000Hz.SetBorderSize( 1 );
        label8000Hz.SetMultiLineEdit( false );
        label8000Hz.SetIsNumberEditor( false );
        label8000Hz.SetNumberEditorRange( 0, 100 );
        label8000Hz.SetNumberEditorInterval( 1 );
        label8000Hz.SetNumberEditorUsesMouseWheel( false );
        label8000Hz.SetHasCustomTextHoverColor( false );
        label8000Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label8000Hz.SetFont( "Arial", 7, true, false );

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 5 );
        imageTTW.SetSize( 40, 12 );
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

        knob250Hz = new VoltageKnob( "knob250Hz", "250 Hz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob250Hz );
        knob250Hz.SetWantsMouseNotifications( false );
        knob250Hz.SetPosition( 45, 95 );
        knob250Hz.SetSize( 25, 25 );
        knob250Hz.SetSkin( "ARP2500 Sm Silver" );
        knob250Hz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob250Hz.SetKnobParams( 215, 145 );
        knob250Hz.DisplayValueInPercent( true );
        knob250Hz.SetKnobAdjustsRing( true );

        knob2kHz = new VoltageKnob( "knob2kHz", "2 kHz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob2kHz );
        knob2kHz.SetWantsMouseNotifications( false );
        knob2kHz.SetPosition( 45, 200 );
        knob2kHz.SetSize( 25, 25 );
        knob2kHz.SetSkin( "ARP2500 Sm Silver" );
        knob2kHz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob2kHz.SetKnobParams( 215, 145 );
        knob2kHz.DisplayValueInPercent( true );
        knob2kHz.SetKnobAdjustsRing( true );

        knob8kHz = new VoltageKnob( "knob8kHz", "8 kHz", this, 0.0, 1.0, 1.0 );
        AddComponent( knob8kHz );
        knob8kHz.SetWantsMouseNotifications( false );
        knob8kHz.SetPosition( 45, 270 );
        knob8kHz.SetSize( 25, 25 );
        knob8kHz.SetSkin( "ARP2500 Sm Silver" );
        knob8kHz.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knob8kHz.SetKnobParams( 215, 145 );
        knob8kHz.DisplayValueInPercent( true );
        knob8kHz.SetKnobAdjustsRing( true );

        label2000Hz = new VoltageLabel( "label2000Hz", "2000 Hz Label", this, "2000 Hz" );
        AddComponent( label2000Hz );
        label2000Hz.SetWantsMouseNotifications( false );
        label2000Hz.SetPosition( 46, 223 );
        label2000Hz.SetSize( 26, 12 );
        label2000Hz.SetEditable( false, false );
        label2000Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label2000Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label2000Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label2000Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label2000Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label2000Hz.SetBorderSize( 1 );
        label2000Hz.SetMultiLineEdit( false );
        label2000Hz.SetIsNumberEditor( false );
        label2000Hz.SetNumberEditorRange( 0, 100 );
        label2000Hz.SetNumberEditorInterval( 1 );
        label2000Hz.SetNumberEditorUsesMouseWheel( false );
        label2000Hz.SetHasCustomTextHoverColor( false );
        label2000Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label2000Hz.SetFont( "Arial", 7, true, false );

        label250Hz = new VoltageLabel( "label250Hz", "250 Hz Label", this, "250 Hz" );
        AddComponent( label250Hz );
        label250Hz.SetWantsMouseNotifications( false );
        label250Hz.SetPosition( 46, 118 );
        label250Hz.SetSize( 26, 12 );
        label250Hz.SetEditable( false, false );
        label250Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label250Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label250Hz.SetColor( new Color( 46, 46, 46, 255 ) );
        label250Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label250Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label250Hz.SetBorderSize( 1 );
        label250Hz.SetMultiLineEdit( false );
        label250Hz.SetIsNumberEditor( false );
        label250Hz.SetNumberEditorRange( 0, 100 );
        label250Hz.SetNumberEditorInterval( 1 );
        label250Hz.SetNumberEditorUsesMouseWheel( false );
        label250Hz.SetHasCustomTextHoverColor( false );
        label250Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label250Hz.SetFont( "Arial", 7, true, false );

        outputAudio = new VoltageAudioJack( "outputAudio", "Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputAudio );
        outputAudio.SetWantsMouseNotifications( false );
        outputAudio.SetPosition( 83, 310 );
        outputAudio.SetSize( 25, 25 );
        outputAudio.SetSkin( "Mini Jack 25px" );

        labelIn = new VoltageLabel( "labelIn", "In Label", this, "IN" );
        AddComponent( labelIn );
        labelIn.SetWantsMouseNotifications( false );
        labelIn.SetPosition( 0, 294 );
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
        labelOut.SetPosition( 78, 294 );
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

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(Seattle) Ltd " );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 5, 12 );
        labelLocation.SetSize( 40, 14 );
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

        inputAudio = new VoltageAudioJack( "inputAudio", "Input", this, JackType.JackType_AudioInput );
        AddComponent( inputAudio );
        inputAudio.SetWantsMouseNotifications( false );
        inputAudio.SetPosition( 5, 310 );
        inputAudio.SetSize( 25, 25 );
        inputAudio.SetSkin( "Jack Round 25px" );

        jack125HzIn = new VoltageAudioJack( "jack125HzIn", "125 Hz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack125HzIn );
        jack125HzIn.SetWantsMouseNotifications( false );
        jack125HzIn.SetPosition( 15, 60 );
        jack125HzIn.SetSize( 25, 25 );
        jack125HzIn.SetSkin( "Jack Round 25px" );

        jack250HzOut_1 = new VoltageAudioJack( "jack250HzOut_1", "250 Hz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack250HzOut_1 );
        jack250HzOut_1.SetWantsMouseNotifications( false );
        jack250HzOut_1.SetPosition( 15, 95 );
        jack250HzOut_1.SetSize( 25, 25 );
        jack250HzOut_1.SetSkin( "Jack Round 25px" );

        jack500HzOut_1 = new VoltageAudioJack( "jack500HzOut_1", "500 Hz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack500HzOut_1 );
        jack500HzOut_1.SetWantsMouseNotifications( false );
        jack500HzOut_1.SetPosition( 15, 130 );
        jack500HzOut_1.SetSize( 25, 25 );
        jack500HzOut_1.SetSkin( "Jack Round 25px" );

        jack1kHzOut_1 = new VoltageAudioJack( "jack1kHzOut_1", "1 kHz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack1kHzOut_1 );
        jack1kHzOut_1.SetWantsMouseNotifications( false );
        jack1kHzOut_1.SetPosition( 15, 165 );
        jack1kHzOut_1.SetSize( 25, 25 );
        jack1kHzOut_1.SetSkin( "Jack Round 25px" );

        jack2kHzOut_1 = new VoltageAudioJack( "jack2kHzOut_1", "2 kHz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack2kHzOut_1 );
        jack2kHzOut_1.SetWantsMouseNotifications( false );
        jack2kHzOut_1.SetPosition( 15, 200 );
        jack2kHzOut_1.SetSize( 25, 25 );
        jack2kHzOut_1.SetSkin( "Jack Round 25px" );

        jack4kHzOut_1 = new VoltageAudioJack( "jack4kHzOut_1", "4 kHz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack4kHzOut_1 );
        jack4kHzOut_1.SetWantsMouseNotifications( false );
        jack4kHzOut_1.SetPosition( 15, 235 );
        jack4kHzOut_1.SetSize( 25, 25 );
        jack4kHzOut_1.SetSkin( "Jack Round 25px" );

        jack8kHzOut_1 = new VoltageAudioJack( "jack8kHzOut_1", "8 kHz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack8kHzOut_1 );
        jack8kHzOut_1.SetWantsMouseNotifications( false );
        jack8kHzOut_1.SetPosition( 15, 270 );
        jack8kHzOut_1.SetSize( 25, 25 );
        jack8kHzOut_1.SetSkin( "Jack Round 25px" );

        jack63HzIn = new VoltageAudioJack( "jack63HzIn", "63 Hz CV In", this, JackType.JackType_AudioInput );
        AddComponent( jack63HzIn );
        jack63HzIn.SetWantsMouseNotifications( false );
        jack63HzIn.SetPosition( 15, 26 );
        jack63HzIn.SetSize( 25, 25 );
        jack63HzIn.SetSkin( "Jack Round 25px" );
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
        gainKnobs[0] = knob63Hz; individualOuts[0] = jack63HzOut;
        gainKnobs[1] = knob125Hz; individualOuts[1] = jack125HzOut;
        gainKnobs[2] = knob250Hz; individualOuts[2] = jack250HzOut;
        gainKnobs[3] = knob500Hz; individualOuts[3] = jack500HzOut;
        gainKnobs[4] = knob1kHz; individualOuts[4] = jack1kHzOut;
        gainKnobs[5] = knob2kHz; individualOuts[5] = jack2kHzOut;
        gainKnobs[6] = knob4kHz; individualOuts[6] = jack4kHzOut;
        gainKnobs[7] = knob8kHz; individualOuts[7] = jack8kHzOut;
        for (int i = 0; i < BAND_COUNT; i++) {
            highPass[i] = new BiquadFilter();
            lowPass[i] = new BiquadFilter();
            double highFrequency = 1.0 / (2.0 * Math.PI * HP_CAPACITORS[i]
                    * Math.sqrt(HP_RESISTOR_SMALL[i] * HP_RESISTOR_LARGE[i]));
            double highQ = Math.sqrt(HP_RESISTOR_LARGE[i] / HP_RESISTOR_SMALL[i]) / 2.0;
            double lowFrequency = 1.0 / (2.0 * Math.PI * LP_RESISTORS[i]
                    * Math.sqrt(LP_FEEDBACK_CAPACITORS[i] * LP_GROUND_CAPACITORS[i]));
            double lowQ = Math.sqrt(LP_FEEDBACK_CAPACITORS[i] / LP_GROUND_CAPACITORS[i]) / 2.0;
            highPass[i].configure(highFrequency, highQ, true);
            lowPass[i].configure(lowFrequency, lowQ, false);
            adoptGain(i);
        }


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
                resetPending.set(true);
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
        boolean clearHistory = resetPending.getAndSet(false);
        if (resumePending.getAndSet(false) || clearHistory) {
            for (int i = 0; i < BAND_COUNT; i++) {
                highPass[i].reset();
                lowPass[i].reset();
                adoptGain(i);
            }
            resetMixer();
        }
        double input = readSignal(inputAudio);
        double mix = 0.0;
        for (int i = 0; i < BAND_COUNT; i++) {
            double band = lowPass[i].process(highPass[i].process(input));
            double position = safeGain(gainKnobs[i].GetValue());
            if (position != cachedPositions[i]) {
                cachedPositions[i] = position;
                targetGains[i] = mixGainForKnob(position);
            }
            smoothedGains[i] += GAIN_SMOOTHING * (targetGains[i] - smoothedGains[i]);
            double delayedBand = tapDelay[i][tapDelayPosition];
            tapDelay[i][tapDelayPosition] = band;
            individualOuts[i].SetValue(delayedBand);
            mix += MIX_POLARITY[i] * band * smoothedGains[i];
        }
        tapDelayPosition++;
        if (tapDelayPosition == MIX_DELAY_SAMPLES) {
            tapDelayPosition = 0;
        }
        // Numerical guard outside the intended audio domain protects FIR histories.
        double safeMix = Double.isFinite(mix) ? Math.max(-1.0e6, Math.min(1.0e6, mix)) : 0.0;
        double first = mixUpsampler.process(safeMix * 2.0);
        double second = mixUpsampler.process(0.0);
        double output = mixDownsampler.process(shapeMix(first));
        mixDownsampler.process(shapeMix(second));
        outputAudio.SetValue(output * RESAMPLER_DC_NORMALIZATION);


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
        outputAudio.SetValue(readSignal(inputAudio));
        for (int i = 0; i < BAND_COUNT; i++) {
            individualOuts[i].SetValue(0.0);
        }
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
        if (component == inputAudio) {
            return "Audio input: unpatched = 0 V; buffered input feeds eight HP/LP paths; no added input saturation.";
        }
        if (component == outputAudio) {
            return "Main mix: gentle 12 V soft knee, 2x oversampled; no makeup gain; 9-sample delay; bypass passes input.";
        }
        for (int i = 0; i < BAND_COUNT; i++) {
            if (component == gainKnobs[i]) {
                double position = safeGain(gainKnobs[i].GetValue());
                return String.format(java.util.Locale.ROOT,
                        "%.0f Hz mix knob: %.2f%%; log attenuation %.2f%%; enter knob percent; direct tap unaffected",
                        FREQUENCIES[i], position * 100.0, mixGainForKnob(position) * 100.0);
            }
            if (component == individualOuts[i]) {
                return String.format(java.util.Locale.ROOT,
                        "%.0f Hz clean HP/LP tap before mix pot; aligned 9-sample delay; silent in bypass", FREQUENCIES[i]);
            }
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
        for (int i = 0; i < BAND_COUNT; i++) {
            if (component == gainKnobs[i]) {
                if (Double.isFinite(newValue)) {
                    gainKnobs[i].SetValue(safeGain(newValue / 100.0));
                }
                return;
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
    private VoltageAudioJack jack63HzIn;
    private VoltageAudioJack jack8kHzOut_1;
    private VoltageAudioJack jack4kHzOut_1;
    private VoltageAudioJack jack2kHzOut_1;
    private VoltageAudioJack jack1kHzOut_1;
    private VoltageAudioJack jack500HzOut_1;
    private VoltageAudioJack jack250HzOut_1;
    private VoltageAudioJack jack125HzIn;
    private VoltageAudioJack inputAudio;
    private VoltageLabel labelLocation;
    private VoltageLabel labelOut;
    private VoltageLabel labelIn;
    private VoltageAudioJack outputAudio;
    private VoltageLabel label250Hz;
    private VoltageLabel label2000Hz;
    private VoltageKnob knob8kHz;
    private VoltageKnob knob2kHz;
    private VoltageKnob knob250Hz;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel label8000Hz;
    private VoltageLabel label4000Hz;
    private VoltageLabel label125Hz;
    private VoltageLabel label1000Hz;
    private VoltageAudioJack jack63HzOut;
    private VoltageAudioJack jack8kHzOut;
    private VoltageLabel label63Hz;
    private VoltageAudioJack jack4kHzOut;
    private VoltageLabel label500Hz;
    private VoltageAudioJack jack2kHzOut;
    private VoltageAudioJack jack1kHzOut;
    private VoltageLabel labelFooter;
    private VoltageAudioJack jack500HzOut;
    private VoltageAudioJack jack250HzOut;
    private VoltageAudioJack jack125HzOut;
    private VoltageKnob knob4kHz;
    private VoltageKnob knob1kHz;
    private VoltageKnob knob125Hz;
    private VoltageKnob knob500Hz;
    private VoltageKnob knob63Hz;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final int BAND_COUNT = 8;
    private static final double SAMPLE_RATE = 48000.0;
    private static final double GAIN_SMOOTHING = 1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE));
    private static final double POT_OHMS = 5000.0;
    private static final double MIX_INPUT_OHMS = 10000.0;
    private static final double LOG_TAPER_SPAN = Math.log(100.0);
    private static final double[] FREQUENCIES = {63, 125, 250, 500, 1000, 2000, 4000, 8000};
    // Nominal components from drawing 82/11 p1; PCB low-band trim is not applied.
    private static final double[] HP_RESISTOR_SMALL = {18000, 9100, 4300, 2200, 3300, 1600, 2700, 1300};
    private static final double[] HP_RESISTOR_LARGE = {180000, 91000, 43000, 22000, 33000, 16000, 27000, 13000};
    private static final double[] HP_CAPACITORS = {47e-9, 47e-9, 47e-9, 47e-9, 15e-9, 15e-9, 4.7e-9, 4.7e-9};
    private static final double[] LP_RESISTORS = {56000, 27000, 13000, 6800, 10000, 5600, 8200, 4300};
    private static final double[] LP_FEEDBACK_CAPACITORS = {150e-9, 150e-9, 150e-9, 150e-9, 47e-9, 47e-9, 15e-9, 15e-9};
    private static final double[] LP_GROUND_CAPACITORS = {15e-9, 15e-9, 15e-9, 15e-9, 4.7e-9, 4.7e-9, 1.5e-9, 1.5e-9};
    private static final double[] MIX_POLARITY = {-1, 1, -1, 1, -1, 1, -1, 1};
    private final BiquadFilter[] highPass = new BiquadFilter[BAND_COUNT];
    private final BiquadFilter[] lowPass = new BiquadFilter[BAND_COUNT];
    private final VoltageKnob[] gainKnobs = new VoltageKnob[BAND_COUNT];
    private final VoltageAudioJack[] individualOuts = new VoltageAudioJack[BAND_COUNT];
    private final double[] smoothedGains = new double[BAND_COUNT];
    private final double[] cachedPositions = new double[BAND_COUNT];
    private final double[] targetGains = new double[BAND_COUNT];
    private final AtomicBoolean resetPending = new AtomicBoolean(false);
    private final AtomicBoolean resumePending = new AtomicBoolean(false);

    private static double safeGain(double value) {
        return Double.isFinite(value) ? Math.max(0.0, Math.min(1.0, value)) : 0.0;
    }

    private static double readSignal(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double value = jack.GetValue();
        return Double.isFinite(value) ? value : 0.0;
    }

    // Chosen smooth audio taper; actual vintage LOG-pot curve has not been measured.
    private static double mixGainForKnob(double position) {
        double fraction = Math.min(1.0, Math.expm1(LOG_TAPER_SPAN * safeGain(position)) / 99.0);
        return fraction / (1.0 + POT_OHMS * fraction * (1.0 - fraction) / MIX_INPUT_OHMS);
    }

    private void adoptGain(int index) {
        cachedPositions[index] = safeGain(gainKnobs[index].GetValue());
        targetGains[index] = mixGainForKnob(cachedPositions[index]);
        smoothedGains[index] = targetGains[index];
    }

    // Experimental summing-amplifier headroom; not a measured 741 saturation law.
    private static final double MIX_HEADROOM_VOLTS = 12.0;
    private static final int MIX_DELAY_SAMPLES = 9;
    private final HalfBand19 mixUpsampler = new HalfBand19();
    private final HalfBand19 mixDownsampler = new HalfBand19();
    private final double[][] tapDelay = new double[BAND_COUNT][MIX_DELAY_SAMPLES];
    private int tapDelayPosition;
    private static final double RESAMPLER_DC_NORMALIZATION = resamplerDcNormalization();

    private static double resamplerDcNormalization() {
        double sum = 2.0 * (0.021277660466073 - 0.027992303277934
                + 0.049537687283950 - 0.095789590456413 + 0.308510413777763)
                + 0.488912264413122;
        double alternating = sum - 2.0 * 0.488912264413122;
        return 1.0 / (sum * sum + alternating * alternating);
    }

    // Fourth-order soft knee: unity small-signal gain, no time-varying compressor or DC bias.
    private static double shapeMix(double value) {
        if (!Double.isFinite(value)) {
            return 0.0;
        }
        double ratio = Math.abs(value) / MIX_HEADROOM_VOLTS;
        if (ratio <= 1.0) {
            double squared = ratio * ratio;
            return value / Math.sqrt(Math.sqrt(1.0 + squared * squared));
        }
        double inverse = 1.0 / ratio;
        double squared = inverse * inverse;
        return Math.copySign(MIX_HEADROOM_VOLTS
                / Math.sqrt(Math.sqrt(1.0 + squared * squared)), value);
    }

    private void resetMixer() {
        mixUpsampler.reset();
        mixDownsampler.reset();
        for (int i = 0; i < BAND_COUNT; i++) {
            java.util.Arrays.fill(tapDelay[i], 0.0);
        }
        tapDelayPosition = 0;
    }

/** Established Colorbox 19-tap half-band FIR; coefficients/arithmetic preserved. */
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


    // Ideal unity-gain Sallen-Key equivalent, bilinear transformed with each f0 prewarped.
    private static final class BiquadFilter {
        private double b0, b1, b2, a1, a2;
        private double x1, x2, y1, y2;

        private void configure(double frequency, double q, boolean highpass) {
            if (!Double.isFinite(frequency) || !Double.isFinite(q)
                    || frequency <= 0.0 || frequency >= SAMPLE_RATE * 0.5 || q <= 0.0) {
                throw new IllegalArgumentException("Invalid fixed filter configuration");
            }
            double omega = 2.0 * Math.PI * frequency / SAMPLE_RATE;
            double cosine = Math.cos(omega);
            double alpha = Math.sin(omega) / (2.0 * q);
            double denominator = 1.0 + alpha;
            double numerator = highpass ? (1.0 + cosine) : (1.0 - cosine);
            b0 = numerator * 0.5 / denominator;
            b1 = (highpass ? -numerator : numerator) / denominator;
            b2 = b0;
            a1 = -2.0 * cosine / denominator;
            a2 = (1.0 - alpha) / denominator;
        }

        private double process(double input) {
            if (!Double.isFinite(input)) {
                input = 0.0;
            }
            if (!Double.isFinite(x1) || !Double.isFinite(x2)
                    || !Double.isFinite(y1) || !Double.isFinite(y2)) {
                reset();
            }
            double output = (b0 * input) + (b1 * x1) + (b2 * x2) - (a1 * y1) - (a2 * y2);
            if (!Double.isFinite(output)) {
                reset();
                return 0.0;
            }
            x2 = x1;
            x1 = input;
            y2 = y1;
            y1 = output;
            return output;
        }

        private void reset() {
            x1 = x2 = y1 = y2 = 0.0;
        }
    }


    
    //[/user-code-and-variables]
}

 