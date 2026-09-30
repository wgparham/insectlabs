package com.insectlabs.generator;


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


public class generator extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public generator( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "dual waveform generator", ModuleType.ModuleType_Utility, 6.4 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "de28c18d3aaf44b1b17b0133f5112828" );
    }

void InitializeControls()
{

        analogMeter1 = new VoltageAnalogVUMeter( "analogMeter1", "analogMeter1", this );
        AddComponent( analogMeter1 );
        analogMeter1.SetWantsMouseNotifications( false );
        analogMeter1.SetPosition( 170, 20 );
        analogMeter1.SetSize( 120, 66 );
        analogMeter1.SetSkin( "Analog Amber" );

        switch1 = new VoltageSwitch( "switch1", "power", this, 0 );
        AddComponent( switch1 );
        switch1.SetWantsMouseNotifications( false );
        switch1.SetPosition( 40, 40 );
        switch1.SetSize( 40, 40 );
        switch1.SetSkin( "2-State Red Cap" );

        knob1 = new VoltageKnob( "knob1", "knob1", this, 0.0, 1.0, 0.0 );
        AddComponent( knob1 );
        knob1.SetWantsMouseNotifications( false );
        knob1.SetPosition( 320, 265 );
        knob1.SetSize( 50, 50 );
        knob1.SetSkin( "Cosmo v2 Med" );
        knob1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob1.SetKnobParams( 215, 145 );
        knob1.DisplayValueInPercent( true );
        knob1.SetKnobAdjustsRing( true );

        knob2 = new VoltageKnob( "knob2", "knob2", this, 1, 4, 3 );
        AddComponent( knob2 );
        knob2.SetWantsMouseNotifications( false );
        knob2.SetPosition( 265, 270 );
        knob2.SetSize( 40, 40 );
        knob2.SetSkin( "Cosmo v2 Pointer" );
        knob2.SetRange( 1, 4, 3, false, 4 );
        knob2.SetKnobParams( 320, 40 );
        knob2.DisplayValueInPercent( false );
        knob2.SetKnobAdjustsRing( true );

        knob3 = new VoltageKnob( "knob3", "frequency", this, -1.0, 1.0, 0.0 );
        AddComponent( knob3 );
        knob3.SetWantsMouseNotifications( false );
        knob3.SetPosition( 150, 100 );
        knob3.SetSize( 160, 160 );
        knob3.SetSkin( "Cosmo Large Black" );
        knob3.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knob3.SetKnobParams( 215, 145 );
        knob3.DisplayValueInPercent( false );
        knob3.SetKnobAdjustsRing( true );

        knob4 = new VoltageKnob( "knob4", "Waveform Select", this, 1, 2, 1 );
        AddComponent( knob4 );
        knob4.SetWantsMouseNotifications( false );
        knob4.SetPosition( 40, 270 );
        knob4.SetSize( 40, 40 );
        knob4.SetSkin( "Cosmo v2 Pointer" );
        knob4.SetRange( 1, 2, 1, false, 2 );
        knob4.SetKnobParams( 320, 40 );
        knob4.DisplayValueInPercent( false );
        knob4.SetKnobAdjustsRing( true );

        knob5 = new VoltageKnob( "knob5", "triangle duty cycle", this, -1.0, 1.0, 0.0 );
        AddComponent( knob5 );
        knob5.SetWantsMouseNotifications( false );
        knob5.SetPosition( 95, 270 );
        knob5.SetSize( 40, 40 );
        knob5.SetSkin( "Cosmo v2 Med" );
        knob5.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knob5.SetKnobParams( 215, 145 );
        knob5.DisplayValueInPercent( false );
        knob5.SetKnobAdjustsRing( true );

        knob6 = new VoltageKnob( "knob6", "modulation select", this, 1, 4, 1 );
        AddComponent( knob6 );
        knob6.SetWantsMouseNotifications( false );
        knob6.SetPosition( 40, 195 );
        knob6.SetSize( 40, 40 );
        knob6.SetSkin( "Cosmo v2 Pointer" );
        knob6.SetRange( 1, 4, 1, false, 4 );
        knob6.SetKnobParams( 320, 40 );
        knob6.DisplayValueInPercent( false );
        knob6.SetKnobAdjustsRing( true );

        outputJack1 = new VoltageAudioJack( "outputJack1", "1kHz Reference Tone", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack1 );
        outputJack1.SetWantsMouseNotifications( false );
        outputJack1.SetPosition( 385, 40 );
        outputJack1.SetSize( 37, 37 );
        outputJack1.SetSkin( "Rotated Half" );

        switch2 = new VoltageSwitch( "switch2", "ref tone destination switch", this, 1 );
        AddComponent( switch2 );
        switch2.SetWantsMouseNotifications( false );
        switch2.SetPosition( 430, 45 );
        switch2.SetSize( 15, 30 );
        switch2.SetSkin( "3-State Slide Black" );

        outputJack2 = new VoltageAudioJack( "outputJack2", "outputJack2", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack2 );
        outputJack2.SetWantsMouseNotifications( false );
        outputJack2.SetPosition( 385, 270 );
        outputJack2.SetSize( 37, 37 );
        outputJack2.SetSkin( "Rotated Half" );

        textLabel1 = new VoltageLabel( "textLabel1", "textLabel1", this, "insect laboratories" );
        AddComponent( textLabel1 );
        textLabel1.SetWantsMouseNotifications( false );
        textLabel1.SetPosition( 0, 335 );
        textLabel1.SetSize( 460, 23 );
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
        textLabel1.SetFont( "Courier New", 13, true, false );

        textLabel2 = new VoltageLabel( "textLabel2", "textLabel2", this, "Dual Waveform Generator" );
        AddComponent( textLabel2 );
        textLabel2.SetWantsMouseNotifications( false );
        textLabel2.SetPosition( 18, 3 );
        textLabel2.SetSize( 300, 13 );
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

        textLabel3 = new VoltageLabel( "textLabel3", "textLabel3", this, "Output" );
        AddComponent( textLabel3 );
        textLabel3.SetWantsMouseNotifications( false );
        textLabel3.SetPosition( 383, 310 );
        textLabel3.SetSize( 40, 23 );
        textLabel3.SetEditable( false, false );
        textLabel3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel3.SetColor( new Color( 232, 232, 232, 255 ) );
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
        textLabel3.SetFont( "Arial", 10, true, false );

        textLabel4 = new VoltageLabel( "textLabel4", "textLabel4", this, "1kHz Ref." );
        AddComponent( textLabel4 );
        textLabel4.SetWantsMouseNotifications( false );
        textLabel4.SetPosition( 386, 75 );
        textLabel4.SetSize( 60, 23 );
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
        textLabel4.SetFont( "Arial", 10, true, false );

        textLabel5 = new VoltageLabel( "textLabel5", "textLabel5", this, "Amplitude" );
        AddComponent( textLabel5 );
        textLabel5.SetWantsMouseNotifications( false );
        textLabel5.SetPosition( 315, 310 );
        textLabel5.SetSize( 60, 23 );
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
        textLabel5.SetFont( "Arial", 10, true, false );

        textLabel18 = new VoltageLabel( "textLabel18", "textLabel11", this, "Duty Cycle" );
        AddComponent( textLabel18 );
        textLabel18.SetWantsMouseNotifications( false );
        textLabel18.SetPosition( 84, 310 );
        textLabel18.SetSize( 60, 23 );
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
        textLabel18.SetFont( "Arial", 10, true, false );

        textLabel13 = new VoltageLabel( "textLabel13", "textLabel13", this, "Off" );
        AddComponent( textLabel13 );
        textLabel13.SetWantsMouseNotifications( false );
        textLabel13.SetPosition( 30, 190 );
        textLabel13.SetSize( 16, 8 );
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
        textLabel13.SetFont( "Arial", 6, true, false );

        textLabel14 = new VoltageLabel( "textLabel14", "textLabel14", this, "Int/4" );
        AddComponent( textLabel14 );
        textLabel14.SetWantsMouseNotifications( false );
        textLabel14.SetPosition( 42, 183 );
        textLabel14.SetSize( 16, 8 );
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
        textLabel14.SetFont( "Arial", 6, true, false );

        textLabel19 = new VoltageLabel( "textLabel19", "textLabel18", this, "Int." );
        AddComponent( textLabel19 );
        textLabel19.SetWantsMouseNotifications( false );
        textLabel19.SetPosition( 60, 183 );
        textLabel19.SetSize( 16, 8 );
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
        textLabel19.SetFont( "Arial", 6, true, false );

        textLabel15 = new VoltageLabel( "textLabel15", "textLabel15", this, "Ext." );
        AddComponent( textLabel15 );
        textLabel15.SetWantsMouseNotifications( false );
        textLabel15.SetPosition( 70, 190 );
        textLabel15.SetSize( 16, 8 );
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
        textLabel15.SetFont( "Arial", 6, true, false );

        textLabel16 = new VoltageLabel( "textLabel16", "textLabel16", this, "SIN" );
        AddComponent( textLabel16 );
        textLabel16.SetWantsMouseNotifications( false );
        textLabel16.SetPosition( 35, 265 );
        textLabel16.SetSize( 16, 8 );
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
        textLabel16.SetFont( "Arial", 6, true, false );

        textLabel17 = new VoltageLabel( "textLabel17", "textLabel17", this, "TRI" );
        AddComponent( textLabel17 );
        textLabel17.SetWantsMouseNotifications( false );
        textLabel17.SetPosition( 70, 265 );
        textLabel17.SetSize( 16, 8 );
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
        textLabel17.SetFont( "Arial", 6, true, false );

        textLabel6 = new VoltageLabel( "textLabel6", "textLabel6", this, "0.01" );
        AddComponent( textLabel6 );
        textLabel6.SetWantsMouseNotifications( false );
        textLabel6.SetPosition( 255, 265 );
        textLabel6.SetSize( 16, 8 );
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
        textLabel6.SetFont( "Arial", 6, true, false );

        textLabel7 = new VoltageLabel( "textLabel7", "textLabel7", this, "0.10" );
        AddComponent( textLabel7 );
        textLabel7.SetWantsMouseNotifications( false );
        textLabel7.SetPosition( 270, 255 );
        textLabel7.SetSize( 16, 8 );
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
        textLabel7.SetFont( "Arial", 6, true, false );

        textLabel8 = new VoltageLabel( "textLabel8", "textLabel8", this, "1.00" );
        AddComponent( textLabel8 );
        textLabel8.SetWantsMouseNotifications( false );
        textLabel8.SetPosition( 285, 255 );
        textLabel8.SetSize( 16, 8 );
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
        textLabel8.SetFont( "Arial", 6, true, false );

        textLabel9 = new VoltageLabel( "textLabel9", "textLabel9", this, "10.0" );
        AddComponent( textLabel9 );
        textLabel9.SetWantsMouseNotifications( false );
        textLabel9.SetPosition( 300, 265 );
        textLabel9.SetSize( 16, 8 );
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
        textLabel9.SetFont( "Arial", 6, true, false );

        textLabel10 = new VoltageLabel( "textLabel10", "textLabel10", this, "Scale" );
        AddComponent( textLabel10 );
        textLabel10.SetWantsMouseNotifications( false );
        textLabel10.SetPosition( 264, 310 );
        textLabel10.SetSize( 40, 23 );
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
        textLabel10.SetFont( "Arial", 10, true, false );

        textLabel11 = new VoltageLabel( "textLabel11", "Modulation Select", this, "Select" );
        AddComponent( textLabel11 );
        textLabel11.SetWantsMouseNotifications( false );
        textLabel11.SetPosition( 30, 235 );
        textLabel11.SetSize( 60, 23 );
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
        textLabel11.SetFont( "Arial", 10, true, false );

        textLabel12 = new VoltageLabel( "textLabel12", "textLabel12", this, "Adjust" );
        AddComponent( textLabel12 );
        textLabel12.SetWantsMouseNotifications( false );
        textLabel12.SetPosition( 83, 235 );
        textLabel12.SetSize( 60, 23 );
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
        textLabel12.SetFont( "Arial", 10, true, false );

        knob7 = new VoltageKnob( "knob7", "Modulation Adjustment", this, 0.0, 1.0, 0.5 );
        AddComponent( knob7 );
        knob7.SetWantsMouseNotifications( false );
        knob7.SetPosition( 95, 195 );
        knob7.SetSize( 40, 40 );
        knob7.SetSkin( "Cosmo v2 Med" );
        knob7.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob7.SetKnobParams( 215, 145 );
        knob7.DisplayValueInPercent( false );
        knob7.SetKnobAdjustsRing( true );

        inputJack1 = new VoltageAudioJack( "inputJack1", "modulation input", this, JackType.JackType_AudioInput );
        AddComponent( inputJack1 );
        inputJack1.SetWantsMouseNotifications( false );
        inputJack1.SetPosition( 70, 150 );
        inputJack1.SetSize( 37, 37 );
        inputJack1.SetSkin( "Dark Jack Straight" );
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
        resetGenerator();

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
        if (!isPowered()) {
            outputJack1.SetValue(0.0);
            outputJack2.SetValue(0.0);
            analogMeter1.SetValue(0.0);
            return;
        }

        double mainFrequency = smoothMainFrequency(mainFrequencyHz());
        int modulationMode = modulationSelect();
        double modulation = (modulationMode == MODULATION_INTERNAL_QUARTER || modulationMode == MODULATION_INTERNAL)
                ? internalModulation() * internalDepthScale(modulationMode)
                : modulationMode == MODULATION_EXTERNAL
                ? readInput(inputJack1) * clamp(knob7.GetValue(), 0.0, 1.0) / FM_INPUT_REFERENCE_VOLTS : 0.0;
        mainFrequency = clamp(mainFrequency * (1.0 + INTERNAL_FM_DEPTH * modulation),
                MINIMUM_FREQUENCY_HZ, MAXIMUM_FREQUENCY_HZ);
        mainPhase = advancePhase(mainPhase, mainFrequency);

        double source = selectedWaveform(mainPhase, knob5.GetValue());
        double targetAmplitude = clamp(knob1.GetValue(), 0.0, 1.0) * MAXIMUM_OUTPUT_VOLTS;
        smoothedAmplitude += AMPLITUDE_SMOOTH * (targetAmplitude - smoothedAmplitude);
        if (Math.abs(targetAmplitude - smoothedAmplitude) < 1.0e-10) smoothedAmplitude = targetAmplitude;
        double amplitude = smoothedAmplitude;
        double mainOutput = outputStage(source * amplitude);

        int referenceDestination = referenceDestination();
        double reference = sine(mainPhaseReference);
        double referenceOutput = referenceDestination == REFERENCE_JACK ? REFERENCE_OUTPUT_VOLTS * reference : 0.0;
        if (referenceDestination == REFERENCE_MIX) mainOutput = outputStage(mainOutput + REFERENCE_MIX_VOLTS * reference);
        mainPhaseReference = advancePhase(mainPhaseReference, REFERENCE_FREQUENCY_HZ);

        outputJack1.SetValue(referenceOutput);
        outputJack2.SetValue(mainOutput);
        updateOutputMeter(mainOutput);

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
        // A source module has no signal-through path. Bypass is silent and freezes state.
        outputJack1.SetValue(0.0);
        outputJack2.SetValue(0.0);
        analogMeter1.SetValue(0.0);

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
        if (component == switch1) return isPowered() ? "POWER: oscillator running" : "POWER: oscillator stopped";
        if (component == knob3) return "FREQUENCY: " + formatFrequency(mainFrequencyHz());
        if (component == knob2) return "SCALE: " + scaleLabel();
        if (component == knob1) return "AMPLITUDE: " + oneDecimal(knob1.GetValue() * MAXIMUM_OUTPUT_VOLTS) + " V peak";
        if (component == knob4) return waveformSelect() == WAVEFORM_SINE ? "WAVEFORM: SIN" : "WAVEFORM: TRI / RMP / SAW";
        if (component == knob5) return "DUTY CYCLE: " + dutyLabel();
        if (component == knob6) return "FM SELECT: " + modulationLabel();
        if (component == knob7) return modulationSelect() == MODULATION_EXTERNAL
                ? "FM ADJUST: external depth " + oneDecimal(knob7.GetValue() * 100.0) + "%"
                : "FM ADJUST: internal rate " + formatFrequency(internalModulationRateHz());
        if (component == inputJack1) return "EXTERNAL FM INPUT: ±5 V is full external depth";
        if (component == switch2) return referenceDestination() == REFERENCE_JACK ? "1 kHz REFERENCE: dedicated reference jack"
                : referenceDestination() == REFERENCE_MIX ? "1 kHz REFERENCE: mixed with main output" : "1 kHz REFERENCE: off";
        if (component == outputJack1) return "1 kHz REFERENCE output; active only with reference switch up";
        if (component == outputJack2) return "MAIN OUTPUT: selected waveform and optional mixed 1 kHz reference";
        if (component == analogMeter1) return "MAIN OUTPUT: averaged level indication (not a calibrated reference meter)";

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
    private VoltageAudioJack inputJack1;
    private VoltageKnob knob7;
    private VoltageLabel textLabel12;
    private VoltageLabel textLabel11;
    private VoltageLabel textLabel10;
    private VoltageLabel textLabel9;
    private VoltageLabel textLabel8;
    private VoltageLabel textLabel7;
    private VoltageLabel textLabel6;
    private VoltageLabel textLabel17;
    private VoltageLabel textLabel16;
    private VoltageLabel textLabel15;
    private VoltageLabel textLabel19;
    private VoltageLabel textLabel14;
    private VoltageLabel textLabel13;
    private VoltageLabel textLabel18;
    private VoltageLabel textLabel5;
    private VoltageLabel textLabel4;
    private VoltageLabel textLabel3;
    private VoltageLabel textLabel2;
    private VoltageLabel textLabel1;
    private VoltageAudioJack outputJack2;
    private VoltageSwitch switch2;
    private VoltageAudioJack outputJack1;
    private VoltageKnob knob6;
    private VoltageKnob knob5;
    private VoltageKnob knob4;
    private VoltageKnob knob3;
    private VoltageKnob knob2;
    private VoltageKnob knob1;
    private VoltageSwitch switch1;
    private VoltageAnalogVUMeter analogMeter1;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double TWO_PI = Math.PI * 2.0;
    private static final double MINIMUM_FREQUENCY_HZ = 0.01;
    private static final double MAXIMUM_FREQUENCY_HZ = 18000.0;
    private static final double REFERENCE_FREQUENCY_HZ = 1000.0;
    private static final double REFERENCE_OUTPUT_VOLTS = 5.0;
    private static final double REFERENCE_MIX_VOLTS = 2.5;
    private static final double MAXIMUM_OUTPUT_VOLTS = 10.0;
    private static final double FM_INPUT_REFERENCE_VOLTS = 5.0;
    private static final double INTERNAL_FM_DEPTH = 0.18;
    private static final double INTERNAL_FM_DRIFT_HZ = 0.018;
    private static final int WAVEFORM_SINE = 1;
    private static final int WAVEFORM_TRIANGLE = 2;
    private static final int MODULATION_OFF = 1;
    private static final int MODULATION_INTERNAL_QUARTER = 2;
    private static final int MODULATION_INTERNAL = 3;
    private static final int MODULATION_EXTERNAL = 4;
    private static final int REFERENCE_JACK = 2;
    private static final int REFERENCE_OFF = 1;
    private static final int REFERENCE_MIX = 0;

    // 10 ms time constant; computed once, with no per-sample allocation.
    private static final double AMPLITUDE_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010));
    private double smoothedAmplitude;
    // Smooth only manual carrier tuning, before FM, so external/audio-rate FM stays immediate.
    private static final double FREQUENCY_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010));
    private double smoothedFrequency = Double.NaN;
    private static final double METER_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.150));
    private double meterEnvelope;
    private int meterCountdown;

    private double smoothMainFrequency(double target) {
        if (Double.isNaN(smoothedFrequency)) smoothedFrequency = target;
        else smoothedFrequency += FREQUENCY_SMOOTH * (target - smoothedFrequency);
        return smoothedFrequency;
    }

    private static double internalDepthScale(int mode) {
        return mode == MODULATION_INTERNAL_QUARTER ? 0.25 : 1.0;
    }

    private double averagedMeterLevel(double output) {
        meterEnvelope += METER_SMOOTH * (Math.abs(output) - meterEnvelope);
        // Nominal 5 V peak sine sits near mid-scale; driven levels retain headroom.
        return clamp(meterEnvelope * (Math.PI / 20.0), 0.0, 1.0);
    }

    private void updateOutputMeter(double output) {
        double level = averagedMeterLevel(output);
        if (--meterCountdown <= 0) {
            analogMeter1.SetValue(level);
            meterCountdown = 480; // 100 display updates/second at Voltage Modular's 48 kHz.
        }
    }

    private double mainPhase;
    private double internalModulationPhase;
    private double mainPhaseReference;
    private double internalDriftPhase;

    private void resetGenerator() { smoothedAmplitude = 0.0; smoothedFrequency = Double.NaN; meterEnvelope = 0.0; meterCountdown = 0; mainPhase = internalModulationPhase = mainPhaseReference = 0.0; internalDriftPhase = 0.173; }
    private boolean isPowered() { return switch1.GetValue() >= 0.5; }
    private double mainFrequencyHz() { return clamp(100.0 * scaleMultiplier() * Math.pow(10.0, knob3.GetValue()), MINIMUM_FREQUENCY_HZ, MAXIMUM_FREQUENCY_HZ); }
    private double scaleMultiplier() { switch ((int) Math.round(knob2.GetValue())) { case 1: return 0.01; case 2: return 0.10; case 3: return 1.00; default: return 10.0; } }
    private double internalModulation() { double rate = internalModulationRateHz(); internalDriftPhase = advancePhase(internalDriftPhase, INTERNAL_FM_DRIFT_HZ); internalModulationPhase = advancePhase(internalModulationPhase, rate * (1.0 + Math.sin(TWO_PI * internalDriftPhase) * 0.025)); return triangle(internalModulationPhase); }
    private double internalModulationRateHz() { return 0.05 * Math.pow(1000.0, clamp(knob7.GetValue(), 0.0, 1.0)); }
    private int waveformSelect() { return (int) Math.round(knob4.GetValue()); }
    private int modulationSelect() { return (int) Math.round(knob6.GetValue()); }
    private int referenceDestination() { return (int) Math.round(switch2.GetValue()); }
    private double selectedWaveform(double phase, double dutyControl) { if (waveformSelect() == WAVEFORM_SINE) return sine(phase); double duty = 0.08 + 0.84 * ((clamp(dutyControl, -1.0, 1.0) + 1.0) * 0.5); return variableTriangle(phase, duty); }
    private static double sine(double phase) { return Math.sin(TWO_PI * phase); }
    private static double triangle(double phase) { return 1.0 - 4.0 * Math.abs(phase - 0.5); }
    private static double variableTriangle(double phase, double duty) { return phase < duty ? -1.0 + 2.0 * phase / duty : 1.0 - 2.0 * (phase - duty) / (1.0 - duty); }
    private static double advancePhase(double phase, double frequency) { phase += frequency / SAMPLE_RATE; return phase - Math.floor(phase); }
    private static double outputStage(double value) {
        double magnitude = Math.abs(value);
        if (magnitude <= 5.0) return value;
        double excess = magnitude - 5.0;
        double compressed = 5.0 + excess / (1.0 + 0.17 * excess);
        // Ease in from 5 to 7 V. Above 7 V the auditioned curve is unchanged.
        double position = Math.min(1.0, excess * 0.5);
        double blend = position * position * position * (10.0 + position * (-15.0 + 6.0 * position));
        return Math.copySign(magnitude + blend * (compressed - magnitude), value);
    }
    private String scaleLabel() { return oneDecimal(scaleMultiplier() * 100.0) + " Hz center"; }
    private String dutyLabel() { return oneDecimal(8.0 + 84.0 * ((clamp(knob5.GetValue(), -1.0, 1.0) + 1.0) * 0.5)) + "% rise"; }
    private String modulationLabel() { int mode = modulationSelect(); return mode == MODULATION_INTERNAL_QUARTER ? "INT/4" : mode == MODULATION_INTERNAL ? "INT" : mode == MODULATION_EXTERNAL ? "EXT" : "OFF"; }
    private static String formatFrequency(double frequency) { return frequency < 10.0 ? oneDecimal(frequency) + " Hz" : Math.round(frequency) + " Hz"; }
    private static String oneDecimal(double value) { return String.format(java.util.Locale.US, "%.1f", value); }
    private static double readInput(VoltageAudioJack jack) { return jack.IsConnected() ? jack.GetValue() : 0.0; }
    private static double clamp(double value, double minimum, double maximum) { return Math.max(minimum, Math.min(maximum, value)); }
    //[/user-code-and-variables]
}

 