package com.insectlabs.sherlock;


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


public class sherlock extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public sherlock( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "sherlock", ModuleType.ModuleType_Utility, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "c8e7d23983524bb69b846d9418f67769" );
    }

void InitializeControls()
{

        outputJack3 = new VoltageAudioJack( "outputJack3", "PULSE LOW", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack3 );
        outputJack3.SetWantsMouseNotifications( false );
        outputJack3.SetPosition( 77, 129 );
        outputJack3.SetSize( 37, 37 );
        outputJack3.SetSkin( "Rotated Half" );

        outputJack2 = new VoltageAudioJack( "outputJack2", "PULSE HIGH", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack2 );
        outputJack2.SetWantsMouseNotifications( false );
        outputJack2.SetPosition( 77, 80 );
        outputJack2.SetSize( 37, 37 );
        outputJack2.SetSkin( "Rotated Half" );

        textLabel12 = new VoltageLabel( "textLabel12", "textLabel12", this, "PULSE HI" );
        AddComponent( textLabel12 );
        textLabel12.SetWantsMouseNotifications( false );
        textLabel12.SetPosition( 79, 112 );
        textLabel12.SetSize( 30, 20 );
        textLabel12.SetEditable( false, false );
        textLabel12.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel12.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel12.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel12.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel12.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel12.SetBorderSize( 1 );
        textLabel12.SetMultiLineEdit( false );
        textLabel12.SetIsNumberEditor( false );
        textLabel12.SetNumberEditorRange( 0, 100 );
        textLabel12.SetNumberEditorInterval( 1 );
        textLabel12.SetNumberEditorUsesMouseWheel( false );
        textLabel12.SetHasCustomTextHoverColor( false );
        textLabel12.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel12.SetFont( "Arial", 9, true, false );

        textLabel13 = new VoltageLabel( "textLabel13", "textLabel13", this, "PULSE LO" );
        AddComponent( textLabel13 );
        textLabel13.SetWantsMouseNotifications( false );
        textLabel13.SetPosition( 79, 158 );
        textLabel13.SetSize( 30, 20 );
        textLabel13.SetEditable( false, false );
        textLabel13.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel13.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel13.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel13.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel13.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel13.SetBorderSize( 1 );
        textLabel13.SetMultiLineEdit( false );
        textLabel13.SetIsNumberEditor( false );
        textLabel13.SetNumberEditorRange( 0, 100 );
        textLabel13.SetNumberEditorInterval( 1 );
        textLabel13.SetNumberEditorUsesMouseWheel( false );
        textLabel13.SetHasCustomTextHoverColor( false );
        textLabel13.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel13.SetFont( "Arial", 8, true, false );

        textLabel14 = new VoltageLabel( "textLabel14", "textLabel14", this, "PULSE" );
        AddComponent( textLabel14 );
        textLabel14.SetWantsMouseNotifications( false );
        textLabel14.SetPosition( 7, 226 );
        textLabel14.SetSize( 30, 20 );
        textLabel14.SetEditable( false, false );
        textLabel14.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel14.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel14.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel14.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel14.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel14.SetBorderSize( 1 );
        textLabel14.SetMultiLineEdit( false );
        textLabel14.SetIsNumberEditor( false );
        textLabel14.SetNumberEditorRange( 0, 100 );
        textLabel14.SetNumberEditorInterval( 1 );
        textLabel14.SetNumberEditorUsesMouseWheel( false );
        textLabel14.SetHasCustomTextHoverColor( false );
        textLabel14.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel14.SetFont( "Arial", 9, true, false );

        textLabel1 = new VoltageLabel( "textLabel1", "textLabel1", this, "dual slew generator" );
        AddComponent( textLabel1 );
        textLabel1.SetWantsMouseNotifications( false );
        textLabel1.SetPosition( 0, 335 );
        textLabel1.SetSize( 115, 23 );
        textLabel1.SetEditable( false, false );
        textLabel1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel1.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel1.SetBkColor( new Color( 51, 51, 51, 0 ) );
        textLabel1.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        textLabel1.SetBorderSize( 4 );
        textLabel1.SetMultiLineEdit( false );
        textLabel1.SetIsNumberEditor( false );
        textLabel1.SetNumberEditorRange( 0, 100 );
        textLabel1.SetNumberEditorInterval( 1 );
        textLabel1.SetNumberEditorUsesMouseWheel( false );
        textLabel1.SetHasCustomTextHoverColor( false );
        textLabel1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel1.SetFont( "Courier New", 10, true, false );

        textLabel2 = new VoltageLabel( "textLabel2", "textLabel2", this, "sherlock" );
        AddComponent( textLabel2 );
        textLabel2.SetWantsMouseNotifications( false );
        textLabel2.SetPosition( 20, 3 );
        textLabel2.SetSize( 95, 13 );
        textLabel2.SetEditable( false, false );
        textLabel2.SetJustificationFlags( VoltageLabel.Justification.Left );
        textLabel2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel2.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel2.SetBkColor( new Color( 51, 51, 51, 0 ) );
        textLabel2.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        textLabel2.SetBorderSize( 1 );
        textLabel2.SetMultiLineEdit( false );
        textLabel2.SetIsNumberEditor( false );
        textLabel2.SetNumberEditorRange( 0, 100 );
        textLabel2.SetNumberEditorInterval( 1 );
        textLabel2.SetNumberEditorUsesMouseWheel( false );
        textLabel2.SetHasCustomTextHoverColor( false );
        textLabel2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel2.SetFont( "Courier New", 13, true, false );

        textLabel3 = new VoltageLabel( "textLabel3", "textLabel3", this, "iL" );
        AddComponent( textLabel3 );
        textLabel3.SetWantsMouseNotifications( false );
        textLabel3.SetPosition( 0, 0 );
        textLabel3.SetSize( 20, 20 );
        textLabel3.SetEditable( false, false );
        textLabel3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel3.SetColor( new Color( 147, 0, 0, 255 ) );
        textLabel3.SetBkColor( new Color( 51, 51, 51, 0 ) );
        textLabel3.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        textLabel3.SetBorderSize( 2 );
        textLabel3.SetMultiLineEdit( false );
        textLabel3.SetIsNumberEditor( false );
        textLabel3.SetNumberEditorRange( 0, 100 );
        textLabel3.SetNumberEditorInterval( 1 );
        textLabel3.SetNumberEditorUsesMouseWheel( false );
        textLabel3.SetHasCustomTextHoverColor( false );
        textLabel3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel3.SetFont( "Courier New", 13, true, false );

        outputJack1 = new VoltageAudioJack( "outputJack1", "POSITIVE OUTPUT", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack1 );
        outputJack1.SetWantsMouseNotifications( false );
        outputJack1.SetPosition( 77, 35 );
        outputJack1.SetSize( 37, 37 );
        outputJack1.SetSkin( "Rotated Half" );

        inputJack1 = new VoltageAudioJack( "inputJack1", "POSITIVE VC IN", this, JackType.JackType_AudioInput );
        AddComponent( inputJack1 );
        inputJack1.SetWantsMouseNotifications( false );
        inputJack1.SetPosition( 2, 35 );
        inputJack1.SetSize( 37, 37 );
        inputJack1.SetSkin( "Dark Jack Straight" );

        inputJack2 = new VoltageAudioJack( "inputJack2", "POSITIVE INPUT", this, JackType.JackType_AudioInput );
        AddComponent( inputJack2 );
        inputJack2.SetWantsMouseNotifications( false );
        inputJack2.SetPosition( 38, 35 );
        inputJack2.SetSize( 37, 37 );
        inputJack2.SetSkin( "Dark Jack Straight" );

        inputJack3 = new VoltageAudioJack( "inputJack3", "POSITIVE START", this, JackType.JackType_AudioInput );
        AddComponent( inputJack3 );
        inputJack3.SetWantsMouseNotifications( false );
        inputJack3.SetPosition( 38, 80 );
        inputJack3.SetSize( 37, 37 );
        inputJack3.SetSkin( "Dark Jack Straight" );

        inputJack4 = new VoltageAudioJack( "inputJack4", "PODSITIVE SUSTAIN", this, JackType.JackType_AudioInput );
        AddComponent( inputJack4 );
        inputJack4.SetWantsMouseNotifications( false );
        inputJack4.SetPosition( 38, 128 );
        inputJack4.SetSize( 37, 37 );
        inputJack4.SetSkin( "Dark Jack Straight" );

        knob1 = new VoltageKnob( "knob1", "POSITIVE VC KNOB", this, 0.0, 1.0, 0.5 );
        AddComponent( knob1 );
        knob1.SetWantsMouseNotifications( false );
        knob1.SetPosition( 3, 80 );
        knob1.SetSize( 35, 35 );
        knob1.SetSkin( "Cosmo Large Black" );
        knob1.SetRange( 0.0, 1.0, 0.5, true, 0 );
        knob1.SetKnobParams( 215, 145 );
        knob1.DisplayValueInPercent( false );
        knob1.SetKnobAdjustsRing( true );

        knob2 = new VoltageKnob( "knob2", "POSITIVE RATE KNOB", this, 0.0, 1.0, 0.5 );
        AddComponent( knob2 );
        knob2.SetWantsMouseNotifications( false );
        knob2.SetPosition( 3, 129 );
        knob2.SetSize( 35, 35 );
        knob2.SetSkin( "Cosmo Large Black" );
        knob2.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob2.SetKnobParams( 215, 145 );
        knob2.DisplayValueInPercent( false );
        knob2.SetKnobAdjustsRing( true );

        LED2 = new VoltageLED( "LED2", "LED2", this );
        AddComponent( LED2 );
        LED2.SetWantsMouseNotifications( false );
        LED2.SetPosition( 105, 160 );
        LED2.SetSize( 5, 5 );
        LED2.SetSkin( "2500 Lamp Red" );

        inputJack5 = new VoltageAudioJack( "inputJack5", "NEGATIVE INPUT", this, JackType.JackType_AudioInput );
        AddComponent( inputJack5 );
        inputJack5.SetWantsMouseNotifications( false );
        inputJack5.SetPosition( 4, 290 );
        inputJack5.SetSize( 37, 37 );
        inputJack5.SetSkin( "Dark Jack Straight" );

        inputJack6 = new VoltageAudioJack( "inputJack6", "NEGATIVE VC", this, JackType.JackType_AudioInput );
        AddComponent( inputJack6 );
        inputJack6.SetWantsMouseNotifications( false );
        inputJack6.SetPosition( 4, 242 );
        inputJack6.SetSize( 37, 37 );
        inputJack6.SetSkin( "Dark Jack Straight" );

        outputJack4 = new VoltageAudioJack( "outputJack4", "PULSE", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack4 );
        outputJack4.SetWantsMouseNotifications( false );
        outputJack4.SetPosition( 4, 195 );
        outputJack4.SetSize( 37, 37 );
        outputJack4.SetSkin( "Rotated Half" );

        LED3 = new VoltageLED( "LED3", "LED3", this );
        AddComponent( LED3 );
        LED3.SetWantsMouseNotifications( false );
        LED3.SetPosition( 33, 226 );
        LED3.SetSize( 5, 5 );
        LED3.SetSkin( "2500 Lamp Red" );

        knob3 = new VoltageKnob( "knob3", "NEGATIVE RATE KNOB", this, 0.0, 1.0, 0.5 );
        AddComponent( knob3 );
        knob3.SetWantsMouseNotifications( false );
        knob3.SetPosition( 53, 288 );
        knob3.SetSize( 35, 35 );
        knob3.SetSkin( "Cosmo Large Black" );
        knob3.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob3.SetKnobParams( 215, 145 );
        knob3.DisplayValueInPercent( false );
        knob3.SetKnobAdjustsRing( true );

        knob4 = new VoltageKnob( "knob4", "NEGATIVE VC KNOB", this, 0.0, 1.0, 0.5 );
        AddComponent( knob4 );
        knob4.SetWantsMouseNotifications( false );
        knob4.SetPosition( 53, 245 );
        knob4.SetSize( 35, 35 );
        knob4.SetSkin( "Cosmo Large Black" );
        knob4.SetRange( 0.0, 1.0, 0.5, true, 0 );
        knob4.SetKnobParams( 215, 145 );
        knob4.DisplayValueInPercent( false );
        knob4.SetKnobAdjustsRing( true );

        outputJack5 = new VoltageAudioJack( "outputJack5", "NEGATIVE OUTPUT", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack5 );
        outputJack5.SetWantsMouseNotifications( false );
        outputJack5.SetPosition( 52, 195 );
        outputJack5.SetSize( 37, 37 );
        outputJack5.SetSkin( "Rotated Half" );

        textLabel4 = new VoltageLabel( "textLabel4", "textLabel4", this, "VC" );
        AddComponent( textLabel4 );
        textLabel4.SetWantsMouseNotifications( false );
        textLabel4.SetPosition( 9, 112 );
        textLabel4.SetSize( 20, 20 );
        textLabel4.SetEditable( false, false );
        textLabel4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel4.SetColor( new Color( 232, 232, 232, 255 ) );
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
        textLabel4.SetFont( "Arial", 9, true, false );

        textLabel5 = new VoltageLabel( "textLabel5", "textLabel5", this, "VC" );
        AddComponent( textLabel5 );
        textLabel5.SetWantsMouseNotifications( false );
        textLabel5.SetPosition( 12, 275 );
        textLabel5.SetSize( 20, 20 );
        textLabel5.SetEditable( false, false );
        textLabel5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel5.SetColor( new Color( 232, 232, 232, 255 ) );
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
        textLabel5.SetFont( "Arial", 9, true, false );

        textLabel6 = new VoltageLabel( "textLabel6", "textLabel6", this, "RATE" );
        AddComponent( textLabel6 );
        textLabel6.SetWantsMouseNotifications( false );
        textLabel6.SetPosition( 4, 158 );
        textLabel6.SetSize( 30, 20 );
        textLabel6.SetEditable( false, false );
        textLabel6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel6.SetColor( new Color( 232, 232, 232, 255 ) );
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
        textLabel6.SetFont( "Arial", 9, true, false );

        textLabel7 = new VoltageLabel( "textLabel7", "textLabel7", this, "RATE" );
        AddComponent( textLabel7 );
        textLabel7.SetWantsMouseNotifications( false );
        textLabel7.SetPosition( 55, 318 );
        textLabel7.SetSize( 30, 20 );
        textLabel7.SetEditable( false, false );
        textLabel7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel7.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel7.SetBorderSize( 1 );
        textLabel7.SetMultiLineEdit( false );
        textLabel7.SetIsNumberEditor( false );
        textLabel7.SetNumberEditorRange( 0, 100 );
        textLabel7.SetNumberEditorInterval( 1 );
        textLabel7.SetNumberEditorUsesMouseWheel( false );
        textLabel7.SetHasCustomTextHoverColor( false );
        textLabel7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel7.SetFont( "Arial", 9, true, false );

        textLabel8 = new VoltageLabel( "textLabel8", "textLabel8", this, "IN" );
        AddComponent( textLabel8 );
        textLabel8.SetWantsMouseNotifications( false );
        textLabel8.SetPosition( 47, 65 );
        textLabel8.SetSize( 20, 20 );
        textLabel8.SetEditable( false, false );
        textLabel8.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel8.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel8.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel8.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel8.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel8.SetBorderSize( 1 );
        textLabel8.SetMultiLineEdit( false );
        textLabel8.SetIsNumberEditor( false );
        textLabel8.SetNumberEditorRange( 0, 100 );
        textLabel8.SetNumberEditorInterval( 1 );
        textLabel8.SetNumberEditorUsesMouseWheel( false );
        textLabel8.SetHasCustomTextHoverColor( false );
        textLabel8.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel8.SetFont( "Arial", 9, true, false );

        textLabel15 = new VoltageLabel( "textLabel15", "textLabel15", this, "OUT" );
        AddComponent( textLabel15 );
        textLabel15.SetWantsMouseNotifications( false );
        textLabel15.SetPosition( 86, 65 );
        textLabel15.SetSize( 20, 20 );
        textLabel15.SetEditable( false, false );
        textLabel15.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel15.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel15.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel15.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel15.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel15.SetBorderSize( 1 );
        textLabel15.SetMultiLineEdit( false );
        textLabel15.SetIsNumberEditor( false );
        textLabel15.SetNumberEditorRange( 0, 100 );
        textLabel15.SetNumberEditorInterval( 1 );
        textLabel15.SetNumberEditorUsesMouseWheel( false );
        textLabel15.SetHasCustomTextHoverColor( false );
        textLabel15.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel15.SetFont( "Arial", 9, true, false );

        textLabel16 = new VoltageLabel( "textLabel16", "textLabel16", this, "OUT" );
        AddComponent( textLabel16 );
        textLabel16.SetWantsMouseNotifications( false );
        textLabel16.SetPosition( 60, 226 );
        textLabel16.SetSize( 20, 20 );
        textLabel16.SetEditable( false, false );
        textLabel16.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel16.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel16.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel16.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel16.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel16.SetBorderSize( 1 );
        textLabel16.SetMultiLineEdit( false );
        textLabel16.SetIsNumberEditor( false );
        textLabel16.SetNumberEditorRange( 0, 100 );
        textLabel16.SetNumberEditorInterval( 1 );
        textLabel16.SetNumberEditorUsesMouseWheel( false );
        textLabel16.SetHasCustomTextHoverColor( false );
        textLabel16.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel16.SetFont( "Arial", 9, true, false );

        textLabel9 = new VoltageLabel( "textLabel9", "textLabel9", this, "IN" );
        AddComponent( textLabel9 );
        textLabel9.SetWantsMouseNotifications( false );
        textLabel9.SetPosition( 13, 318 );
        textLabel9.SetSize( 20, 20 );
        textLabel9.SetEditable( false, false );
        textLabel9.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel9.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel9.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel9.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel9.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel9.SetBorderSize( 1 );
        textLabel9.SetMultiLineEdit( false );
        textLabel9.SetIsNumberEditor( false );
        textLabel9.SetNumberEditorRange( 0, 100 );
        textLabel9.SetNumberEditorInterval( 1 );
        textLabel9.SetNumberEditorUsesMouseWheel( false );
        textLabel9.SetHasCustomTextHoverColor( false );
        textLabel9.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel9.SetFont( "Arial", 9, true, false );

        textLabel10 = new VoltageLabel( "textLabel10", "textLabel10", this, "START" );
        AddComponent( textLabel10 );
        textLabel10.SetWantsMouseNotifications( false );
        textLabel10.SetPosition( 42, 112 );
        textLabel10.SetSize( 30, 20 );
        textLabel10.SetEditable( false, false );
        textLabel10.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel10.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel10.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel10.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel10.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel10.SetBorderSize( 1 );
        textLabel10.SetMultiLineEdit( false );
        textLabel10.SetIsNumberEditor( false );
        textLabel10.SetNumberEditorRange( 0, 100 );
        textLabel10.SetNumberEditorInterval( 1 );
        textLabel10.SetNumberEditorUsesMouseWheel( false );
        textLabel10.SetHasCustomTextHoverColor( false );
        textLabel10.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel10.SetFont( "Arial", 9, true, false );

        textLabel11 = new VoltageLabel( "textLabel11", "textLabel11", this, "SUSTAIN" );
        AddComponent( textLabel11 );
        textLabel11.SetWantsMouseNotifications( false );
        textLabel11.SetPosition( 37, 158 );
        textLabel11.SetSize( 40, 20 );
        textLabel11.SetEditable( false, false );
        textLabel11.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel11.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel11.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel11.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel11.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel11.SetBorderSize( 1 );
        textLabel11.SetMultiLineEdit( false );
        textLabel11.SetIsNumberEditor( false );
        textLabel11.SetNumberEditorRange( 0, 100 );
        textLabel11.SetNumberEditorInterval( 1 );
        textLabel11.SetNumberEditorUsesMouseWheel( false );
        textLabel11.SetHasCustomTextHoverColor( false );
        textLabel11.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel11.SetFont( "Arial", 9, true, false );

        textLabel17 = new VoltageLabel( "textLabel17", "textLabel17", this, "NEGATIVE SLEW" );
        AddComponent( textLabel17 );
        textLabel17.SetWantsMouseNotifications( false );
        textLabel17.SetPosition( 0, 184 );
        textLabel17.SetSize( 115, 10 );
        textLabel17.SetEditable( false, false );
        textLabel17.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel17.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel17.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel17.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel17.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel17.SetBorderSize( 1 );
        textLabel17.SetMultiLineEdit( false );
        textLabel17.SetIsNumberEditor( false );
        textLabel17.SetNumberEditorRange( 0, 100 );
        textLabel17.SetNumberEditorInterval( 1 );
        textLabel17.SetNumberEditorUsesMouseWheel( false );
        textLabel17.SetHasCustomTextHoverColor( false );
        textLabel17.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel17.SetFont( "Arial", 13, true, false );

        textLabel18 = new VoltageLabel( "textLabel18", "textLabel18", this, "POSITIVE SLEW" );
        AddComponent( textLabel18 );
        textLabel18.SetWantsMouseNotifications( false );
        textLabel18.SetPosition( 0, 22 );
        textLabel18.SetSize( 115, 10 );
        textLabel18.SetEditable( false, false );
        textLabel18.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel18.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel18.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel18.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel18.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel18.SetBorderSize( 1 );
        textLabel18.SetMultiLineEdit( false );
        textLabel18.SetIsNumberEditor( false );
        textLabel18.SetNumberEditorRange( 0, 100 );
        textLabel18.SetNumberEditorInterval( 1 );
        textLabel18.SetNumberEditorUsesMouseWheel( false );
        textLabel18.SetHasCustomTextHoverColor( false );
        textLabel18.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel18.SetFont( "Arial", 13, true, false );

        LED1 = new VoltageLED( "LED1", "LED1", this );
        AddComponent( LED1 );
        LED1.SetWantsMouseNotifications( false );
        LED1.SetPosition( 105, 112 );
        LED1.SetSize( 5, 5 );
        LED1.SetSkin( "2500 Lamp Red" );

        switch1 = new VoltageSwitch( "switch1", "VOLTAGE SWITCH", this, 0 );
        AddComponent( switch1 );
        switch1.SetWantsMouseNotifications( false );
        switch1.SetPosition( 99, 300 );
        switch1.SetSize( 12, 21 );
        switch1.SetSkin( "2-State Slide Black" );

        textLabel19 = new VoltageLabel( "textLabel19", "textLabel19", this, "10 V" );
        AddComponent( textLabel19 );
        textLabel19.SetWantsMouseNotifications( false );
        textLabel19.SetPosition( 95, 285 );
        textLabel19.SetSize( 20, 20 );
        textLabel19.SetEditable( false, false );
        textLabel19.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel19.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel19.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel19.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel19.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel19.SetBorderSize( 1 );
        textLabel19.SetMultiLineEdit( false );
        textLabel19.SetIsNumberEditor( false );
        textLabel19.SetNumberEditorRange( 0, 100 );
        textLabel19.SetNumberEditorInterval( 1 );
        textLabel19.SetNumberEditorUsesMouseWheel( false );
        textLabel19.SetHasCustomTextHoverColor( false );
        textLabel19.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel19.SetFont( "Arial", 9, true, false );

        textLabel20 = new VoltageLabel( "textLabel20", "textLabel20", this, "5 V" );
        AddComponent( textLabel20 );
        textLabel20.SetWantsMouseNotifications( false );
        textLabel20.SetPosition( 95, 317 );
        textLabel20.SetSize( 20, 20 );
        textLabel20.SetEditable( false, false );
        textLabel20.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel20.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel20.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel20.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel20.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel20.SetBorderSize( 1 );
        textLabel20.SetMultiLineEdit( false );
        textLabel20.SetIsNumberEditor( false );
        textLabel20.SetNumberEditorRange( 0, 100 );
        textLabel20.SetNumberEditorInterval( 1 );
        textLabel20.SetNumberEditorUsesMouseWheel( false );
        textLabel20.SetHasCustomTextHoverColor( false );
        textLabel20.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel20.SetFont( "Arial", 9, true, false );

        switch2 = new VoltageSwitch( "switch2", "NEGATIVE SLEW RATE SWITCH", this, 0 );
        AddComponent( switch2 );
        switch2.SetWantsMouseNotifications( false );
        switch2.SetPosition( 99, 254 );
        switch2.SetSize( 12, 21 );
        switch2.SetSkin( "2-State Slide Black" );

        textLabel21 = new VoltageLabel( "textLabel21", "textLabel21", this, "FAST" );
        AddComponent( textLabel21 );
        textLabel21.SetWantsMouseNotifications( false );
        textLabel21.SetPosition( 95, 239 );
        textLabel21.SetSize( 20, 20 );
        textLabel21.SetEditable( false, false );
        textLabel21.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel21.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel21.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel21.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel21.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel21.SetBorderSize( 1 );
        textLabel21.SetMultiLineEdit( false );
        textLabel21.SetIsNumberEditor( false );
        textLabel21.SetNumberEditorRange( 0, 100 );
        textLabel21.SetNumberEditorInterval( 1 );
        textLabel21.SetNumberEditorUsesMouseWheel( false );
        textLabel21.SetHasCustomTextHoverColor( false );
        textLabel21.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel21.SetFont( "Arial", 9, true, false );

        textLabel22 = new VoltageLabel( "textLabel22", "textLabel22", this, "SLOW" );
        AddComponent( textLabel22 );
        textLabel22.SetWantsMouseNotifications( false );
        textLabel22.SetPosition( 95, 271 );
        textLabel22.SetSize( 20, 20 );
        textLabel22.SetEditable( false, false );
        textLabel22.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel22.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel22.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel22.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel22.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel22.SetBorderSize( 1 );
        textLabel22.SetMultiLineEdit( false );
        textLabel22.SetIsNumberEditor( false );
        textLabel22.SetNumberEditorRange( 0, 100 );
        textLabel22.SetNumberEditorInterval( 1 );
        textLabel22.SetNumberEditorUsesMouseWheel( false );
        textLabel22.SetHasCustomTextHoverColor( false );
        textLabel22.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel22.SetFont( "Arial", 9, true, false );

        switch3 = new VoltageSwitch( "switch3", "POSITIVE SLEW RATE SWITCH", this, 1 );
        AddComponent( switch3 );
        switch3.SetWantsMouseNotifications( false );
        switch3.SetPosition( 99, 208 );
        switch3.SetSize( 12, 21 );
        switch3.SetSkin( "2-State Slide Black" );

        textLabel23 = new VoltageLabel( "textLabel23", "textLabel23", this, "FAST" );
        AddComponent( textLabel23 );
        textLabel23.SetWantsMouseNotifications( false );
        textLabel23.SetPosition( 95, 193 );
        textLabel23.SetSize( 20, 20 );
        textLabel23.SetEditable( false, false );
        textLabel23.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel23.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel23.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel23.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel23.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel23.SetBorderSize( 1 );
        textLabel23.SetMultiLineEdit( false );
        textLabel23.SetIsNumberEditor( false );
        textLabel23.SetNumberEditorRange( 0, 100 );
        textLabel23.SetNumberEditorInterval( 1 );
        textLabel23.SetNumberEditorUsesMouseWheel( false );
        textLabel23.SetHasCustomTextHoverColor( false );
        textLabel23.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel23.SetFont( "Arial", 9, true, false );

        textLabel24 = new VoltageLabel( "textLabel24", "textLabel24", this, "SLOW" );
        AddComponent( textLabel24 );
        textLabel24.SetWantsMouseNotifications( false );
        textLabel24.SetPosition( 95, 225 );
        textLabel24.SetSize( 20, 20 );
        textLabel24.SetEditable( false, false );
        textLabel24.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel24.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel24.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel24.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel24.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel24.SetBorderSize( 1 );
        textLabel24.SetMultiLineEdit( false );
        textLabel24.SetIsNumberEditor( false );
        textLabel24.SetNumberEditorRange( 0, 100 );
        textLabel24.SetNumberEditorInterval( 1 );
        textLabel24.SetNumberEditorUsesMouseWheel( false );
        textLabel24.SetHasCustomTextHoverColor( false );
        textLabel24.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel24.SetFont( "Arial", 9, true, false );
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
    private VoltageLabel textLabel24;
    private VoltageLabel textLabel23;
    private VoltageSwitch switch3;
    private VoltageLabel textLabel22;
    private VoltageLabel textLabel21;
    private VoltageSwitch switch2;
    private VoltageLabel textLabel20;
    private VoltageLabel textLabel19;
    private VoltageSwitch switch1;
    private VoltageLED LED1;
    private VoltageLabel textLabel18;
    private VoltageLabel textLabel17;
    private VoltageLabel textLabel11;
    private VoltageLabel textLabel10;
    private VoltageLabel textLabel9;
    private VoltageLabel textLabel16;
    private VoltageLabel textLabel15;
    private VoltageLabel textLabel8;
    private VoltageLabel textLabel7;
    private VoltageLabel textLabel6;
    private VoltageLabel textLabel5;
    private VoltageLabel textLabel4;
    private VoltageAudioJack outputJack5;
    private VoltageKnob knob4;
    private VoltageKnob knob3;
    private VoltageLED LED3;
    private VoltageAudioJack outputJack4;
    private VoltageAudioJack inputJack6;
    private VoltageAudioJack inputJack5;
    private VoltageLED LED2;
    private VoltageKnob knob2;
    private VoltageKnob knob1;
    private VoltageAudioJack inputJack4;
    private VoltageAudioJack inputJack3;
    private VoltageAudioJack inputJack2;
    private VoltageAudioJack inputJack1;
    private VoltageAudioJack outputJack1;
    private VoltageLabel textLabel3;
    private VoltageLabel textLabel2;
    private VoltageLabel textLabel1;
    private VoltageLabel textLabel14;
    private VoltageLabel textLabel13;
    private VoltageLabel textLabel12;
    private VoltageAudioJack outputJack2;
    private VoltageAudioJack outputJack3;


    //[user-code-and-variables]    Add your own variables and functions here
    //[/user-code-and-variables]





}

 