package com.insectlabs.shdot7437;


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


public class shDot7437 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public shDot7437( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "sh.7437 - dual slew processor", ModuleType.ModuleType_Utility, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "04a606fdb96945b2bfb48f23091e36eb" );
    }

void InitializeControls()
{

        positiveRateLabel = new VoltageLabel( "positiveRateLabel", "Positive Rate Label", this, "RATE" );
        AddComponent( positiveRateLabel );
        positiveRateLabel.SetWantsMouseNotifications( false );
        positiveRateLabel.SetPosition( 4, 158 );
        positiveRateLabel.SetSize( 30, 20 );
        positiveRateLabel.SetEditable( false, false );
        positiveRateLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveRateLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveRateLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveRateLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveRateLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveRateLabel.SetBorderSize( 1 );
        positiveRateLabel.SetMultiLineEdit( false );
        positiveRateLabel.SetIsNumberEditor( false );
        positiveRateLabel.SetNumberEditorRange( 0, 100 );
        positiveRateLabel.SetNumberEditorInterval( 1 );
        positiveRateLabel.SetNumberEditorUsesMouseWheel( false );
        positiveRateLabel.SetHasCustomTextHoverColor( false );
        positiveRateLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveRateLabel.SetFont( "Arial", 9, true, false );

        positiveHighLabel = new VoltageLabel( "positiveHighLabel", "Positive High Label", this, "PULSE HI" );
        AddComponent( positiveHighLabel );
        positiveHighLabel.SetWantsMouseNotifications( false );
        positiveHighLabel.SetPosition( 79, 112 );
        positiveHighLabel.SetSize( 30, 20 );
        positiveHighLabel.SetEditable( false, false );
        positiveHighLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveHighLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveHighLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveHighLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveHighLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveHighLabel.SetBorderSize( 1 );
        positiveHighLabel.SetMultiLineEdit( false );
        positiveHighLabel.SetIsNumberEditor( false );
        positiveHighLabel.SetNumberEditorRange( 0, 100 );
        positiveHighLabel.SetNumberEditorInterval( 1 );
        positiveHighLabel.SetNumberEditorUsesMouseWheel( false );
        positiveHighLabel.SetHasCustomTextHoverColor( false );
        positiveHighLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveHighLabel.SetFont( "Arial", 9, true, false );

        positiveLowLabel = new VoltageLabel( "positiveLowLabel", "Positive Low Label", this, "PULSE LO" );
        AddComponent( positiveLowLabel );
        positiveLowLabel.SetWantsMouseNotifications( false );
        positiveLowLabel.SetPosition( 79, 158 );
        positiveLowLabel.SetSize( 30, 20 );
        positiveLowLabel.SetEditable( false, false );
        positiveLowLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveLowLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveLowLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveLowLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveLowLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveLowLabel.SetBorderSize( 1 );
        positiveLowLabel.SetMultiLineEdit( false );
        positiveLowLabel.SetIsNumberEditor( false );
        positiveLowLabel.SetNumberEditorRange( 0, 100 );
        positiveLowLabel.SetNumberEditorInterval( 1 );
        positiveLowLabel.SetNumberEditorUsesMouseWheel( false );
        positiveLowLabel.SetHasCustomTextHoverColor( false );
        positiveLowLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveLowLabel.SetFont( "Arial", 8, true, false );

        negativePulseLabel = new VoltageLabel( "negativePulseLabel", "Negative Pulse Label", this, "PULSE" );
        AddComponent( negativePulseLabel );
        negativePulseLabel.SetWantsMouseNotifications( false );
        negativePulseLabel.SetPosition( 7, 226 );
        negativePulseLabel.SetSize( 30, 20 );
        negativePulseLabel.SetEditable( false, false );
        negativePulseLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativePulseLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativePulseLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativePulseLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativePulseLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativePulseLabel.SetBorderSize( 1 );
        negativePulseLabel.SetMultiLineEdit( false );
        negativePulseLabel.SetIsNumberEditor( false );
        negativePulseLabel.SetNumberEditorRange( 0, 100 );
        negativePulseLabel.SetNumberEditorInterval( 1 );
        negativePulseLabel.SetNumberEditorUsesMouseWheel( false );
        negativePulseLabel.SetHasCustomTextHoverColor( false );
        negativePulseLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativePulseLabel.SetFont( "Arial", 9, true, false );

        moduleTypeLabel = new VoltageLabel( "moduleTypeLabel", "Module Type Label", this, "dual slew processor" );
        AddComponent( moduleTypeLabel );
        moduleTypeLabel.SetWantsMouseNotifications( false );
        moduleTypeLabel.SetPosition( 3, 3 );
        moduleTypeLabel.SetSize( 109, 23 );
        moduleTypeLabel.SetEditable( false, false );
        moduleTypeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        moduleTypeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleTypeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        moduleTypeLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        moduleTypeLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        moduleTypeLabel.SetBorderSize( 4 );
        moduleTypeLabel.SetMultiLineEdit( false );
        moduleTypeLabel.SetIsNumberEditor( false );
        moduleTypeLabel.SetNumberEditorRange( 0, 100 );
        moduleTypeLabel.SetNumberEditorInterval( 1 );
        moduleTypeLabel.SetNumberEditorUsesMouseWheel( false );
        moduleTypeLabel.SetHasCustomTextHoverColor( false );
        moduleTypeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleTypeLabel.SetFont( "Courier New", 10, true, false );

        moduleTitleLabel = new VoltageLabel( "moduleTitleLabel", "Module Title Label", this, "sh.7437" );
        AddComponent( moduleTitleLabel );
        moduleTitleLabel.SetWantsMouseNotifications( false );
        moduleTitleLabel.SetPosition( 60, 335 );
        moduleTitleLabel.SetSize( 55, 13 );
        moduleTitleLabel.SetEditable( false, false );
        moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleTitleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        moduleTitleLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        moduleTitleLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        moduleTitleLabel.SetBorderSize( 1 );
        moduleTitleLabel.SetMultiLineEdit( false );
        moduleTitleLabel.SetIsNumberEditor( false );
        moduleTitleLabel.SetNumberEditorRange( 0, 100 );
        moduleTitleLabel.SetNumberEditorInterval( 1 );
        moduleTitleLabel.SetNumberEditorUsesMouseWheel( false );
        moduleTitleLabel.SetHasCustomTextHoverColor( false );
        moduleTitleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleTitleLabel.SetFont( "Courier New", 13, true, false );

        logoLabel = new VoltageLabel( "logoLabel", "Logo Label", this, "iL" );
        AddComponent( logoLabel );
        logoLabel.SetWantsMouseNotifications( false );
        logoLabel.SetPosition( 3, 337 );
        logoLabel.SetSize( 20, 20 );
        logoLabel.SetEditable( false, false );
        logoLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        logoLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        logoLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        logoLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        logoLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        logoLabel.SetBorderSize( 2 );
        logoLabel.SetMultiLineEdit( false );
        logoLabel.SetIsNumberEditor( false );
        logoLabel.SetNumberEditorRange( 0, 100 );
        logoLabel.SetNumberEditorInterval( 1 );
        logoLabel.SetNumberEditorUsesMouseWheel( false );
        logoLabel.SetHasCustomTextHoverColor( false );
        logoLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        logoLabel.SetFont( "Courier New", 13, true, false );

        positiveCvKnob = new VoltageKnob( "positiveCvKnob", "Positive VC Amount", this, -1.0, 1.0, 0.0 );
        AddComponent( positiveCvKnob );
        positiveCvKnob.SetWantsMouseNotifications( false );
        positiveCvKnob.SetPosition( 6, 83 );
        positiveCvKnob.SetSize( 30, 30 );
        positiveCvKnob.SetSkin( "Dial Blue" );
        positiveCvKnob.SetRange( -1.0, 1.0, 0.0, true, 0 );
        positiveCvKnob.SetKnobParams( 215, 145 );
        positiveCvKnob.DisplayValueInPercent( true );
        positiveCvKnob.SetKnobAdjustsRing( true );

        positiveRateKnob = new VoltageKnob( "positiveRateKnob", "Positive Rate", this, 0.0, 1.0, 0.6571893032751831 );
        AddComponent( positiveRateKnob );
        positiveRateKnob.SetWantsMouseNotifications( false );
        positiveRateKnob.SetPosition( 8, 131 );
        positiveRateKnob.SetSize( 30, 30 );
        positiveRateKnob.SetSkin( "Dial Black" );
        positiveRateKnob.SetRange( 0.0, 1.0, 0.6571893032751831, false, 0 );
        positiveRateKnob.SetKnobParams( 215, 145 );
        positiveRateKnob.DisplayValueInPercent( false );
        positiveRateKnob.SetKnobAdjustsRing( true );

        positiveLowLed = new VoltageLED( "positiveLowLed", "Positive Pulse Low Indicator", this );
        AddComponent( positiveLowLed );
        positiveLowLed.SetWantsMouseNotifications( false );
        positiveLowLed.SetPosition( 102, 155 );
        positiveLowLed.SetSize( 5, 5 );
        positiveLowLed.SetSkin( "2500 Lamp Red" );

        negativePulseLed = new VoltageLED( "negativePulseLed", "Negative Pulse Indicator", this );
        AddComponent( negativePulseLed );
        negativePulseLed.SetWantsMouseNotifications( false );
        negativePulseLed.SetPosition( 30, 222 );
        negativePulseLed.SetSize( 5, 5 );
        negativePulseLed.SetSkin( "2500 Lamp Red" );

        negativeRateKnob = new VoltageKnob( "negativeRateKnob", "Negative Rate", this, 0.0, 1.0, 0.9809983491460378 );
        AddComponent( negativeRateKnob );
        negativeRateKnob.SetWantsMouseNotifications( false );
        negativeRateKnob.SetPosition( 40, 285 );
        negativeRateKnob.SetSize( 30, 30 );
        negativeRateKnob.SetSkin( "Dial Black" );
        negativeRateKnob.SetRange( 0.0, 1.0, 0.9809983491460378, false, 0 );
        negativeRateKnob.SetKnobParams( 215, 145 );
        negativeRateKnob.DisplayValueInPercent( false );
        negativeRateKnob.SetKnobAdjustsRing( true );

        negativeCvKnob = new VoltageKnob( "negativeCvKnob", "Negative VC Amount", this, -1.0, 1.0, 0.0 );
        AddComponent( negativeCvKnob );
        negativeCvKnob.SetWantsMouseNotifications( false );
        negativeCvKnob.SetPosition( 43, 248 );
        negativeCvKnob.SetSize( 30, 30 );
        negativeCvKnob.SetSkin( "Dial Blue" );
        negativeCvKnob.SetRange( -1.0, 1.0, 0.0, true, 0 );
        negativeCvKnob.SetKnobParams( 215, 145 );
        negativeCvKnob.DisplayValueInPercent( true );
        negativeCvKnob.SetKnobAdjustsRing( true );

        positiveVcLabel = new VoltageLabel( "positiveVcLabel", "Positive Vc Label", this, "VC" );
        AddComponent( positiveVcLabel );
        positiveVcLabel.SetWantsMouseNotifications( false );
        positiveVcLabel.SetPosition( 9, 112 );
        positiveVcLabel.SetSize( 20, 20 );
        positiveVcLabel.SetEditable( false, false );
        positiveVcLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveVcLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveVcLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveVcLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveVcLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveVcLabel.SetBorderSize( 1 );
        positiveVcLabel.SetMultiLineEdit( false );
        positiveVcLabel.SetIsNumberEditor( false );
        positiveVcLabel.SetNumberEditorRange( 0, 100 );
        positiveVcLabel.SetNumberEditorInterval( 1 );
        positiveVcLabel.SetNumberEditorUsesMouseWheel( false );
        positiveVcLabel.SetHasCustomTextHoverColor( false );
        positiveVcLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveVcLabel.SetFont( "Arial", 9, true, false );

        negativeVcLabel = new VoltageLabel( "negativeVcLabel", "Negative Vc Label", this, "VC" );
        AddComponent( negativeVcLabel );
        negativeVcLabel.SetWantsMouseNotifications( false );
        negativeVcLabel.SetPosition( 12, 270 );
        negativeVcLabel.SetSize( 20, 20 );
        negativeVcLabel.SetEditable( false, false );
        negativeVcLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeVcLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeVcLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeVcLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeVcLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeVcLabel.SetBorderSize( 1 );
        negativeVcLabel.SetMultiLineEdit( false );
        negativeVcLabel.SetIsNumberEditor( false );
        negativeVcLabel.SetNumberEditorRange( 0, 100 );
        negativeVcLabel.SetNumberEditorInterval( 1 );
        negativeVcLabel.SetNumberEditorUsesMouseWheel( false );
        negativeVcLabel.SetHasCustomTextHoverColor( false );
        negativeVcLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeVcLabel.SetFont( "Arial", 9, true, false );

        negativeRateLabel = new VoltageLabel( "negativeRateLabel", "Negative Rate Label", this, "RATE" );
        AddComponent( negativeRateLabel );
        negativeRateLabel.SetWantsMouseNotifications( false );
        negativeRateLabel.SetPosition( 40, 315 );
        negativeRateLabel.SetSize( 30, 20 );
        negativeRateLabel.SetEditable( false, false );
        negativeRateLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeRateLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeRateLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeRateLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeRateLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeRateLabel.SetBorderSize( 1 );
        negativeRateLabel.SetMultiLineEdit( false );
        negativeRateLabel.SetIsNumberEditor( false );
        negativeRateLabel.SetNumberEditorRange( 0, 100 );
        negativeRateLabel.SetNumberEditorInterval( 1 );
        negativeRateLabel.SetNumberEditorUsesMouseWheel( false );
        negativeRateLabel.SetHasCustomTextHoverColor( false );
        negativeRateLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeRateLabel.SetFont( "Arial", 9, true, false );

        positiveInputLabel = new VoltageLabel( "positiveInputLabel", "Positive Input Label", this, "IN" );
        AddComponent( positiveInputLabel );
        positiveInputLabel.SetWantsMouseNotifications( false );
        positiveInputLabel.SetPosition( 47, 65 );
        positiveInputLabel.SetSize( 20, 20 );
        positiveInputLabel.SetEditable( false, false );
        positiveInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveInputLabel.SetBorderSize( 1 );
        positiveInputLabel.SetMultiLineEdit( false );
        positiveInputLabel.SetIsNumberEditor( false );
        positiveInputLabel.SetNumberEditorRange( 0, 100 );
        positiveInputLabel.SetNumberEditorInterval( 1 );
        positiveInputLabel.SetNumberEditorUsesMouseWheel( false );
        positiveInputLabel.SetHasCustomTextHoverColor( false );
        positiveInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveInputLabel.SetFont( "Arial", 9, true, false );

        positiveOutputLabel = new VoltageLabel( "positiveOutputLabel", "Positive Output Label", this, "OUT" );
        AddComponent( positiveOutputLabel );
        positiveOutputLabel.SetWantsMouseNotifications( false );
        positiveOutputLabel.SetPosition( 86, 65 );
        positiveOutputLabel.SetSize( 20, 20 );
        positiveOutputLabel.SetEditable( false, false );
        positiveOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveOutputLabel.SetBorderSize( 1 );
        positiveOutputLabel.SetMultiLineEdit( false );
        positiveOutputLabel.SetIsNumberEditor( false );
        positiveOutputLabel.SetNumberEditorRange( 0, 100 );
        positiveOutputLabel.SetNumberEditorInterval( 1 );
        positiveOutputLabel.SetNumberEditorUsesMouseWheel( false );
        positiveOutputLabel.SetHasCustomTextHoverColor( false );
        positiveOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveOutputLabel.SetFont( "Arial", 9, true, false );

        negativeOutputLabel = new VoltageLabel( "negativeOutputLabel", "Negative Output Label", this, "OUT" );
        AddComponent( negativeOutputLabel );
        negativeOutputLabel.SetWantsMouseNotifications( false );
        negativeOutputLabel.SetPosition( 46, 224 );
        negativeOutputLabel.SetSize( 20, 20 );
        negativeOutputLabel.SetEditable( false, false );
        negativeOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeOutputLabel.SetBorderSize( 1 );
        negativeOutputLabel.SetMultiLineEdit( false );
        negativeOutputLabel.SetIsNumberEditor( false );
        negativeOutputLabel.SetNumberEditorRange( 0, 100 );
        negativeOutputLabel.SetNumberEditorInterval( 1 );
        negativeOutputLabel.SetNumberEditorUsesMouseWheel( false );
        negativeOutputLabel.SetHasCustomTextHoverColor( false );
        negativeOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeOutputLabel.SetFont( "Arial", 9, true, false );

        negativeInputLabel = new VoltageLabel( "negativeInputLabel", "Negative Input Label", this, "IN" );
        AddComponent( negativeInputLabel );
        negativeInputLabel.SetWantsMouseNotifications( false );
        negativeInputLabel.SetPosition( 13, 315 );
        negativeInputLabel.SetSize( 20, 20 );
        negativeInputLabel.SetEditable( false, false );
        negativeInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeInputLabel.SetBorderSize( 1 );
        negativeInputLabel.SetMultiLineEdit( false );
        negativeInputLabel.SetIsNumberEditor( false );
        negativeInputLabel.SetNumberEditorRange( 0, 100 );
        negativeInputLabel.SetNumberEditorInterval( 1 );
        negativeInputLabel.SetNumberEditorUsesMouseWheel( false );
        negativeInputLabel.SetHasCustomTextHoverColor( false );
        negativeInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeInputLabel.SetFont( "Arial", 9, true, false );

        startLabel = new VoltageLabel( "startLabel", "Start Label", this, "START" );
        AddComponent( startLabel );
        startLabel.SetWantsMouseNotifications( false );
        startLabel.SetPosition( 42, 112 );
        startLabel.SetSize( 30, 20 );
        startLabel.SetEditable( false, false );
        startLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        startLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        startLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        startLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        startLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        startLabel.SetBorderSize( 1 );
        startLabel.SetMultiLineEdit( false );
        startLabel.SetIsNumberEditor( false );
        startLabel.SetNumberEditorRange( 0, 100 );
        startLabel.SetNumberEditorInterval( 1 );
        startLabel.SetNumberEditorUsesMouseWheel( false );
        startLabel.SetHasCustomTextHoverColor( false );
        startLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        startLabel.SetFont( "Arial", 9, true, false );

        sustainLabel = new VoltageLabel( "sustainLabel", "Sustain Label", this, "SUSTAIN" );
        AddComponent( sustainLabel );
        sustainLabel.SetWantsMouseNotifications( false );
        sustainLabel.SetPosition( 37, 158 );
        sustainLabel.SetSize( 40, 20 );
        sustainLabel.SetEditable( false, false );
        sustainLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sustainLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sustainLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sustainLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sustainLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sustainLabel.SetBorderSize( 1 );
        sustainLabel.SetMultiLineEdit( false );
        sustainLabel.SetIsNumberEditor( false );
        sustainLabel.SetNumberEditorRange( 0, 100 );
        sustainLabel.SetNumberEditorInterval( 1 );
        sustainLabel.SetNumberEditorUsesMouseWheel( false );
        sustainLabel.SetHasCustomTextHoverColor( false );
        sustainLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sustainLabel.SetFont( "Arial", 9, true, false );

        negativeSectionLabel = new VoltageLabel( "negativeSectionLabel", "Negative Section Label", this, "NEGATIVE" );
        AddComponent( negativeSectionLabel );
        negativeSectionLabel.SetWantsMouseNotifications( false );
        negativeSectionLabel.SetPosition( 0, 184 );
        negativeSectionLabel.SetSize( 115, 10 );
        negativeSectionLabel.SetEditable( false, false );
        negativeSectionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeSectionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeSectionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeSectionLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeSectionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeSectionLabel.SetBorderSize( 1 );
        negativeSectionLabel.SetMultiLineEdit( false );
        negativeSectionLabel.SetIsNumberEditor( false );
        negativeSectionLabel.SetNumberEditorRange( 0, 100 );
        negativeSectionLabel.SetNumberEditorInterval( 1 );
        negativeSectionLabel.SetNumberEditorUsesMouseWheel( false );
        negativeSectionLabel.SetHasCustomTextHoverColor( false );
        negativeSectionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeSectionLabel.SetFont( "Arial", 9, true, false );

        positiveSectionLabel = new VoltageLabel( "positiveSectionLabel", "Positive Section Label", this, "POSITIVE" );
        AddComponent( positiveSectionLabel );
        positiveSectionLabel.SetWantsMouseNotifications( false );
        positiveSectionLabel.SetPosition( 0, 29 );
        positiveSectionLabel.SetSize( 115, 10 );
        positiveSectionLabel.SetEditable( false, false );
        positiveSectionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveSectionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveSectionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveSectionLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveSectionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveSectionLabel.SetBorderSize( 1 );
        positiveSectionLabel.SetMultiLineEdit( false );
        positiveSectionLabel.SetIsNumberEditor( false );
        positiveSectionLabel.SetNumberEditorRange( 0, 100 );
        positiveSectionLabel.SetNumberEditorInterval( 1 );
        positiveSectionLabel.SetNumberEditorUsesMouseWheel( false );
        positiveSectionLabel.SetHasCustomTextHoverColor( false );
        positiveSectionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveSectionLabel.SetFont( "Arial", 9, true, false );

        positiveHighLed = new VoltageLED( "positiveHighLed", "Positive Pulse High Indicator", this );
        AddComponent( positiveHighLed );
        positiveHighLed.SetWantsMouseNotifications( false );
        positiveHighLed.SetPosition( 102, 107 );
        positiveHighLed.SetSize( 5, 5 );
        positiveHighLed.SetSkin( "2500 Lamp Red" );

        voltageSwitch = new VoltageSwitch( "voltageSwitch", "Generated Contour Voltage", this, 0 );
        AddComponent( voltageSwitch );
        voltageSwitch.SetWantsMouseNotifications( false );
        voltageSwitch.SetPosition( 94, 300 );
        voltageSwitch.SetSize( 12, 21 );
        voltageSwitch.SetSkin( "2-State Slide Black" );

        negativeRangeSwitch = new VoltageSwitch( "negativeRangeSwitch", "Negative Rate Range", this, 0 );
        AddComponent( negativeRangeSwitch );
        negativeRangeSwitch.SetWantsMouseNotifications( false );
        negativeRangeSwitch.SetPosition( 94, 254 );
        negativeRangeSwitch.SetSize( 12, 21 );
        negativeRangeSwitch.SetSkin( "2-State Slide Black" );

        positiveRangeSwitch = new VoltageSwitch( "positiveRangeSwitch", "Positive Rate Range", this, 1 );
        AddComponent( positiveRangeSwitch );
        positiveRangeSwitch.SetWantsMouseNotifications( false );
        positiveRangeSwitch.SetPosition( 94, 208 );
        positiveRangeSwitch.SetSize( 12, 21 );
        positiveRangeSwitch.SetSkin( "2-State Slide Black" );

        negativeFastLabel = new VoltageLabel( "negativeFastLabel", "Negative Fast Label", this, "FAST" );
        AddComponent( negativeFastLabel );
        negativeFastLabel.SetWantsMouseNotifications( false );
        negativeFastLabel.SetPosition( 90, 239 );
        negativeFastLabel.SetSize( 20, 20 );
        negativeFastLabel.SetEditable( false, false );
        negativeFastLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeFastLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeFastLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeFastLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeFastLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeFastLabel.SetBorderSize( 1 );
        negativeFastLabel.SetMultiLineEdit( false );
        negativeFastLabel.SetIsNumberEditor( false );
        negativeFastLabel.SetNumberEditorRange( 0, 100 );
        negativeFastLabel.SetNumberEditorInterval( 1 );
        negativeFastLabel.SetNumberEditorUsesMouseWheel( false );
        negativeFastLabel.SetHasCustomTextHoverColor( false );
        negativeFastLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeFastLabel.SetFont( "Arial", 9, true, false );

        tenVoltLabel = new VoltageLabel( "tenVoltLabel", "Ten Volt Label", this, "10 V" );
        AddComponent( tenVoltLabel );
        tenVoltLabel.SetWantsMouseNotifications( false );
        tenVoltLabel.SetPosition( 90, 285 );
        tenVoltLabel.SetSize( 20, 20 );
        tenVoltLabel.SetEditable( false, false );
        tenVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        tenVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        tenVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        tenVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        tenVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        tenVoltLabel.SetBorderSize( 1 );
        tenVoltLabel.SetMultiLineEdit( false );
        tenVoltLabel.SetIsNumberEditor( false );
        tenVoltLabel.SetNumberEditorRange( 0, 100 );
        tenVoltLabel.SetNumberEditorInterval( 1 );
        tenVoltLabel.SetNumberEditorUsesMouseWheel( false );
        tenVoltLabel.SetHasCustomTextHoverColor( false );
        tenVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        tenVoltLabel.SetFont( "Arial", 9, true, false );

        positiveFastLabel = new VoltageLabel( "positiveFastLabel", "Positive Fast Label", this, "FAST" );
        AddComponent( positiveFastLabel );
        positiveFastLabel.SetWantsMouseNotifications( false );
        positiveFastLabel.SetPosition( 90, 193 );
        positiveFastLabel.SetSize( 20, 20 );
        positiveFastLabel.SetEditable( false, false );
        positiveFastLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveFastLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveFastLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveFastLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveFastLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveFastLabel.SetBorderSize( 1 );
        positiveFastLabel.SetMultiLineEdit( false );
        positiveFastLabel.SetIsNumberEditor( false );
        positiveFastLabel.SetNumberEditorRange( 0, 100 );
        positiveFastLabel.SetNumberEditorInterval( 1 );
        positiveFastLabel.SetNumberEditorUsesMouseWheel( false );
        positiveFastLabel.SetHasCustomTextHoverColor( false );
        positiveFastLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveFastLabel.SetFont( "Arial", 9, true, false );

        fiveVoltLabel = new VoltageLabel( "fiveVoltLabel", "Five Volt Label", this, "5 V" );
        AddComponent( fiveVoltLabel );
        fiveVoltLabel.SetWantsMouseNotifications( false );
        fiveVoltLabel.SetPosition( 90, 318 );
        fiveVoltLabel.SetSize( 20, 20 );
        fiveVoltLabel.SetEditable( false, false );
        fiveVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fiveVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fiveVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fiveVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fiveVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fiveVoltLabel.SetBorderSize( 1 );
        fiveVoltLabel.SetMultiLineEdit( false );
        fiveVoltLabel.SetIsNumberEditor( false );
        fiveVoltLabel.SetNumberEditorRange( 0, 100 );
        fiveVoltLabel.SetNumberEditorInterval( 1 );
        fiveVoltLabel.SetNumberEditorUsesMouseWheel( false );
        fiveVoltLabel.SetHasCustomTextHoverColor( false );
        fiveVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fiveVoltLabel.SetFont( "Arial", 9, true, false );

        negativeSlowLabel = new VoltageLabel( "negativeSlowLabel", "Negative Slow Label", this, "SLOW" );
        AddComponent( negativeSlowLabel );
        negativeSlowLabel.SetWantsMouseNotifications( false );
        negativeSlowLabel.SetPosition( 90, 271 );
        negativeSlowLabel.SetSize( 20, 20 );
        negativeSlowLabel.SetEditable( false, false );
        negativeSlowLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeSlowLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeSlowLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeSlowLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeSlowLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeSlowLabel.SetBorderSize( 1 );
        negativeSlowLabel.SetMultiLineEdit( false );
        negativeSlowLabel.SetIsNumberEditor( false );
        negativeSlowLabel.SetNumberEditorRange( 0, 100 );
        negativeSlowLabel.SetNumberEditorInterval( 1 );
        negativeSlowLabel.SetNumberEditorUsesMouseWheel( false );
        negativeSlowLabel.SetHasCustomTextHoverColor( false );
        negativeSlowLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeSlowLabel.SetFont( "Arial", 9, true, false );

        positiveSlowLabel = new VoltageLabel( "positiveSlowLabel", "Positive Slow Label", this, "SLOW" );
        AddComponent( positiveSlowLabel );
        positiveSlowLabel.SetWantsMouseNotifications( false );
        positiveSlowLabel.SetPosition( 90, 225 );
        positiveSlowLabel.SetSize( 20, 20 );
        positiveSlowLabel.SetEditable( false, false );
        positiveSlowLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveSlowLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveSlowLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveSlowLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveSlowLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveSlowLabel.SetBorderSize( 1 );
        positiveSlowLabel.SetMultiLineEdit( false );
        positiveSlowLabel.SetIsNumberEditor( false );
        positiveSlowLabel.SetNumberEditorRange( 0, 100 );
        positiveSlowLabel.SetNumberEditorInterval( 1 );
        positiveSlowLabel.SetNumberEditorUsesMouseWheel( false );
        positiveSlowLabel.SetHasCustomTextHoverColor( false );
        positiveSlowLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveSlowLabel.SetFont( "Arial", 9, true, false );

        negativeCvInput = new VoltageAudioJack( "negativeCvInput", "Negative VC Input", this, JackType.JackType_AudioInput );
        AddComponent( negativeCvInput );
        negativeCvInput.SetWantsMouseNotifications( false );
        negativeCvInput.SetPosition( 10, 249 );
        negativeCvInput.SetSize( 25, 25 );
        negativeCvInput.SetSkin( "Dark Jack Straight" );

        negativeInput = new VoltageAudioJack( "negativeInput", "Negative Signal Input", this, JackType.JackType_AudioInput );
        AddComponent( negativeInput );
        negativeInput.SetWantsMouseNotifications( false );
        negativeInput.SetPosition( 10, 290 );
        negativeInput.SetSize( 25, 25 );
        negativeInput.SetSkin( "Dark Jack Straight" );

        negativePulseOutput = new VoltageAudioJack( "negativePulseOutput", "Negative Pulse", this, JackType.JackType_AudioOutput );
        AddComponent( negativePulseOutput );
        negativePulseOutput.SetWantsMouseNotifications( false );
        negativePulseOutput.SetPosition( 10, 202 );
        negativePulseOutput.SetSize( 25, 25 );
        negativePulseOutput.SetSkin( "Rotated Half" );

        negativeOutput = new VoltageAudioJack( "negativeOutput", "Negative Slew Output", this, JackType.JackType_AudioOutput );
        AddComponent( negativeOutput );
        negativeOutput.SetWantsMouseNotifications( false );
        negativeOutput.SetPosition( 44, 202 );
        negativeOutput.SetSize( 25, 25 );
        negativeOutput.SetSkin( "Rotated Half" );

        positiveCvInput = new VoltageAudioJack( "positiveCvInput", "Positive VC Input", this, JackType.JackType_AudioInput );
        AddComponent( positiveCvInput );
        positiveCvInput.SetWantsMouseNotifications( false );
        positiveCvInput.SetPosition( 8, 39 );
        positiveCvInput.SetSize( 25, 25 );
        positiveCvInput.SetSkin( "Dark Jack Straight" );

        positiveInput = new VoltageAudioJack( "positiveInput", "Positive Signal Input", this, JackType.JackType_AudioInput );
        AddComponent( positiveInput );
        positiveInput.SetWantsMouseNotifications( false );
        positiveInput.SetPosition( 44, 40 );
        positiveInput.SetSize( 25, 25 );
        positiveInput.SetSkin( "Dark Jack Straight" );

        startInput = new VoltageAudioJack( "startInput", "Positive Start", this, JackType.JackType_AudioInput );
        AddComponent( startInput );
        startInput.SetWantsMouseNotifications( false );
        startInput.SetPosition( 44, 84 );
        startInput.SetSize( 25, 25 );
        startInput.SetSkin( "Dark Jack Straight" );

        sustainInput = new VoltageAudioJack( "sustainInput", "Positive Sustain", this, JackType.JackType_AudioInput );
        AddComponent( sustainInput );
        sustainInput.SetWantsMouseNotifications( false );
        sustainInput.SetPosition( 44, 133 );
        sustainInput.SetSize( 25, 25 );
        sustainInput.SetSkin( "Dark Jack Straight" );

        positiveOutput = new VoltageAudioJack( "positiveOutput", "Positive Slew Output", this, JackType.JackType_AudioOutput );
        AddComponent( positiveOutput );
        positiveOutput.SetWantsMouseNotifications( false );
        positiveOutput.SetPosition( 83, 40 );
        positiveOutput.SetSize( 25, 25 );
        positiveOutput.SetSkin( "Rotated Half" );

        positiveHighOutput = new VoltageAudioJack( "positiveHighOutput", "Positive Pulse High", this, JackType.JackType_AudioOutput );
        AddComponent( positiveHighOutput );
        positiveHighOutput.SetWantsMouseNotifications( false );
        positiveHighOutput.SetPosition( 83, 85 );
        positiveHighOutput.SetSize( 25, 25 );
        positiveHighOutput.SetSkin( "Rotated Half" );

        positiveLowOutput = new VoltageAudioJack( "positiveLowOutput", "Positive Pulse Low", this, JackType.JackType_AudioOutput );
        AddComponent( positiveLowOutput );
        positiveLowOutput.SetWantsMouseNotifications( false );
        positiveLowOutput.SetPosition( 83, 134 );
        positiveLowOutput.SetSize( 25, 25 );
        positiveLowOutput.SetSkin( "Rotated Half" );

        colophon = new VoltageLabel( "colophon", "Colophon", this, "manufactured in allegheny city, PA" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 65, 343 );
        colophon.SetSize( 48, 17 );
        colophon.SetEditable( false, false );
        colophon.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        colophon.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        colophon.SetColor( new Color( 147, 147, 147, 147 ) );
        colophon.SetBkColor( new Color( 65, 65, 65, 0 ) );
        colophon.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        colophon.SetBorderSize( 1 );
        colophon.SetMultiLineEdit( true );
        colophon.SetIsNumberEditor( false );
        colophon.SetNumberEditorRange( 0, 100 );
        colophon.SetNumberEditorInterval( 1 );
        colophon.SetNumberEditorUsesMouseWheel( false );
        colophon.SetHasCustomTextHoverColor( false );
        colophon.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        colophon.SetFont( "Arial Black", 6, true, false );
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
        positiveOutput.SetValue(0.0);
        negativeOutput.SetValue(0.0);
        positiveHighOutput.SetValue(0.0);
        positiveLowOutput.SetValue(0.0);
        negativePulseOutput.SetValue(0.0);
        positiveHighLed.SetValue(0.0);
        positiveLowLed.SetValue(0.0);
        negativePulseLed.SetValue(0.0);
        displayedPositiveHigh = false;
        displayedPositiveLow = false;
        displayedNegativeLow = false;
        sherlockCore.resumePending = true;
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
        sherlockCore.setControls(positiveRateKnob.GetValue(), negativeRateKnob.GetValue(),
                positiveCvKnob.GetValue(), negativeCvKnob.GetValue(),
                positiveRangeSwitch.GetValue() >= 0.5, negativeRangeSwitch.GetValue() >= 0.5,
                voltageSwitch.GetValue() >= 0.5);
        sherlockCore.process(
        readInput(positiveInput),
        readInput(negativeInput),
        readInput(positiveCvInput),
        readInput(negativeCvInput),
        readInput(startInput),
        readInput(sustainInput)
);
        positiveOutput.SetValue(sherlockCore.positive);
        negativeOutput.SetValue(sherlockCore.negative);
        positiveHighOutput.SetValue(sherlockCore.positiveHigh);
        positiveLowOutput.SetValue(sherlockCore.positiveLow);
        negativePulseOutput.SetValue(sherlockCore.negativePulse);
        updatePulseIndicators(sherlockCore.positiveHigh > 0.0,
                sherlockCore.positiveLow > 0.0, sherlockCore.negativePulse > 0.0);
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
        positiveOutput.SetValue(readInput(positiveInput));
        negativeOutput.SetValue(readInput(negativeInput));
        positiveHighOutput.SetValue(0.0);
        positiveLowOutput.SetValue(0.0);
        negativePulseOutput.SetValue(0.0);
        updatePulseIndicators(false, false, false);
        sherlockCore.resumePending = true;
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
        if (component == positiveRateKnob || component == negativeRateKnob) {
            boolean positiveSection = component == positiveRateKnob;
            double knob = positiveSection ? positiveRateKnob.GetValue() : negativeRateKnob.GetValue();
            boolean fast = (positiveSection ? positiveRangeSwitch.GetValue() : negativeRangeSwitch.GetValue()) >= 0.5;
            return String.format(java.util.Locale.ROOT, "Rate: %.6f seconds per 5 V (%s; before VC)",
                    SherlockCore.timeFor(knob, fast), fast ? "FAST" : "SLOW");
        }
        if (component == positiveCvKnob || component == negativeCvKnob) {
            double amount = component == positiveCvKnob ? positiveCvKnob.GetValue() : negativeCvKnob.GetValue();
            return String.format(java.util.Locale.ROOT, "VC amount: %+.3fx (at +1x, +1 V doubles rate)", amount);
        }
        if (component == positiveRangeSwitch || component == negativeRangeSwitch) {
            boolean fast = (component == positiveRangeSwitch ? positiveRangeSwitch.GetValue() : negativeRangeSwitch.GetValue()) >= 0.5;
            return fast ? "FAST: 100 us to 4 s per 5 V" : "SLOW: 100 ms to 4000 s per 5 V";
        }
        if (component == voltageSwitch) return voltageSwitch.GetValue() >= 0.5
                ? "Generated positive contour: 10 V (twice the 5 V traversal time)" : "Generated positive contour: 5 V";
        if (component == positiveInput) return "Independent signal input: limits rising movement; unpatched = 0 V";
        if (component == negativeInput) return "Independent signal input: limits falling movement; unpatched = 0 V";
        if (component == positiveOutput) return "Positive slew output; patch to Negative IN for rise/fall shaping";
        if (component == negativeOutput) return "Negative slew output";
        if (component == startInput) return "Rising edge starts a full rise and reset; busy triggers ignored; patch PULSE LO here to cycle";
        if (component == sustainInput) return "Gate starts a full rise and holds its peak until released";
        if (component == positiveCvInput || component == negativeCvInput) return "Rate CV through its attenuverter; immediate modulation within selected range";
        if (component == positiveHighOutput || component == positiveHighLed) return "0/+5 V: high during generated rise and peak hold";
        if (component == positiveLowOutput || component == positiveLowLed) return "0/+5 V: high at/below zero region; patch PULSE LO to START for cycling";
        if (component == negativePulseOutput || component == negativePulseLed) return "0/+5 V: high at/below zero region; patch PULSE to Negative IN for cycling";
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
        if (component == positiveRateKnob || component == negativeRateKnob) {
            boolean positiveSection = component == positiveRateKnob;
            boolean fast = (positiveSection ? positiveRangeSwitch.GetValue() : negativeRangeSwitch.GetValue()) >= 0.5;
            double knob = SherlockCore.knobForTime(newValue, fast);
            if (positiveSection) positiveRateKnob.SetValue(knob);
            else negativeRateKnob.SetValue(knob);
            return;
        }
        if (component == positiveCvKnob || component == negativeCvKnob) {
            double amount = SherlockCore.bipolar(newValue);
            if (component == positiveCvKnob) positiveCvKnob.SetValue(amount);
            else negativeCvKnob.SetValue(amount);
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
    private VoltageLabel colophon;
    private VoltageAudioJack positiveLowOutput;
    private VoltageAudioJack positiveHighOutput;
    private VoltageAudioJack positiveOutput;
    private VoltageAudioJack sustainInput;
    private VoltageAudioJack startInput;
    private VoltageAudioJack positiveInput;
    private VoltageAudioJack positiveCvInput;
    private VoltageAudioJack negativeOutput;
    private VoltageAudioJack negativePulseOutput;
    private VoltageAudioJack negativeInput;
    private VoltageAudioJack negativeCvInput;
    private VoltageLabel positiveSlowLabel;
    private VoltageLabel negativeSlowLabel;
    private VoltageLabel fiveVoltLabel;
    private VoltageLabel positiveFastLabel;
    private VoltageLabel tenVoltLabel;
    private VoltageLabel negativeFastLabel;
    private VoltageSwitch positiveRangeSwitch;
    private VoltageSwitch negativeRangeSwitch;
    private VoltageSwitch voltageSwitch;
    private VoltageLED positiveHighLed;
    private VoltageLabel positiveSectionLabel;
    private VoltageLabel negativeSectionLabel;
    private VoltageLabel sustainLabel;
    private VoltageLabel startLabel;
    private VoltageLabel negativeInputLabel;
    private VoltageLabel negativeOutputLabel;
    private VoltageLabel positiveOutputLabel;
    private VoltageLabel positiveInputLabel;
    private VoltageLabel negativeRateLabel;
    private VoltageLabel negativeVcLabel;
    private VoltageLabel positiveVcLabel;
    private VoltageKnob negativeCvKnob;
    private VoltageKnob negativeRateKnob;
    private VoltageLED negativePulseLed;
    private VoltageLED positiveLowLed;
    private VoltageKnob positiveRateKnob;
    private VoltageKnob positiveCvKnob;
    private VoltageLabel logoLabel;
    private VoltageLabel moduleTitleLabel;
    private VoltageLabel moduleTypeLabel;
    private VoltageLabel negativePulseLabel;
    private VoltageLabel positiveLowLabel;
    private VoltageLabel positiveHighLabel;
    private VoltageLabel positiveRateLabel;


    //[user-code-and-variables]    Add your own variables and functions here
    private final SherlockCore sherlockCore = new SherlockCore(48000.0);
    private boolean displayedPositiveHigh;
    private boolean displayedPositiveLow;
    private boolean displayedNegativeLow;
    private static double readInput(
        VoltageAudioJack jack) {

    if (!jack.IsConnected()) {
        return 0.0;
    }

    return SherlockCore.signal(
            jack.GetValue()
    );
}

    private void updatePulseIndicators(boolean high, boolean low, boolean negative) {
        if (displayedPositiveHigh != high) {
            positiveHighLed.SetValue(high ? 1.0 : 0.0);
            displayedPositiveHigh = high;
        }
        if (displayedPositiveLow != low) {
            positiveLowLed.SetValue(low ? 1.0 : 0.0);
            displayedPositiveLow = low;
        }
        if (displayedNegativeLow != negative) {
            negativePulseLed.SetValue(negative ? 1.0 : 0.0);
            displayedNegativeLow = negative;
        }
    }

    // Voltage Modular's engine runs at 48 kHz. The core accepts a rate for numerical checks.
    // No sample-rate API from the early sketch is assumed to exist in the SDK.
    static final class SherlockCore {
        static final double FAST_MIN_SECONDS = 0.0001;
        static final double FAST_MAX_SECONDS = 4.0;
        static final double RANGE_FACTOR = 1000.0;
        static final double LOG_SPAN = Math.log(40000.0);
        static final double LOG_TWO = Math.log(2.0);
        static final double PULSE_VOLTS = 5.0;
        static final double LOW_ON_VOLTS = 0.001;
        static final double LOW_OFF_VOLTS = 0.002;
        static final double GATE_ON_VOLTS = 1.0;
        static final double GATE_OFF_VOLTS = 0.5;
        static final int IDLE = 0;
        static final int RISING = 1;
        static final int AT_PEAK = 2;
        static final int HOLDING = 3;

        final double sampleRate;
        final RateControl positiveRate;
        final RateControl negativeRate;
        double positive;
        double negative;
        double positiveHigh;
        double positiveLow;
        double negativePulse;
        boolean resumePending = true;
        boolean positiveFast = true;
        boolean negativeFast;
        double selectedPeak = 5.0;
        int positivePhase;
        boolean startHigh;
        boolean sustainHigh;
        boolean positiveLowState = true;
        boolean negativeLowState = true;

        SherlockCore(double rate) {
            sampleRate = rate;
            positiveRate = new RateControl(rate);
            negativeRate = new RateControl(rate);
        }

        static double unit(double value) {
            return Double.isFinite(value) ? Math.max(0.0, Math.min(1.0, value)) : 0.0;
        }

        static double bipolar(double value) {
            return Double.isFinite(value) ? Math.max(-1.0, Math.min(1.0, value)) : 0.0;
        }

        static double signal(double value) {
            return Double.isFinite(value) ? Math.max(-1000000.0, Math.min(1000000.0, value)) : 0.0;
        }

        static double timeFor(double knob, boolean fast) {
            return FAST_MAX_SECONDS * Math.exp(-LOG_SPAN * unit(knob))
                    * (fast ? 1.0 : RANGE_FACTOR);
        }

        static double knobForTime(double seconds, boolean fast) {
            double maximum = FAST_MAX_SECONDS * (fast ? 1.0 : RANGE_FACTOR);
            double minimum = FAST_MIN_SECONDS * (fast ? 1.0 : RANGE_FACTOR);
            return unit(Math.log(maximum / Math.max(minimum, Math.min(maximum, seconds))) / LOG_SPAN);
        }

        // Defaults target the nearest attainable native-sample loop period with a one-sample cable delay.
        // One sample holds the peak/reset transition; no interpolated or hidden oscillator is used.
        static double defaultKnob(boolean positiveSection, double rate) {
            double hz = positiveSection ? 261.6255653005986 : 8.175798915643707;
            double samples = Math.round(rate / hz) - 1.5;
            double seconds = samples / rate;
            if (!positiveSection) seconds *= PULSE_VOLTS / (PULSE_VOLTS - LOW_ON_VOLTS);
            return knobForTime(seconds, positiveSection);
        }

        void setControls(double positiveKnob, double negativeKnob, double positiveAmount,
                double negativeAmount, boolean fastPositive, boolean fastNegative, boolean tenVolts) {
            positiveRate.set(positiveKnob, positiveAmount);
            negativeRate.set(negativeKnob, negativeAmount);
            positiveFast = fastPositive;
            negativeFast = fastNegative;
            selectedPeak = tenVolts ? 10.0 : 5.0;
        }

        static boolean gate(boolean previous, double voltage) {
            return previous ? voltage > GATE_OFF_VOLTS : voltage >= GATE_ON_VOLTS;
        }

        static boolean lowGate(boolean previous, double voltage) {
            return previous ? voltage < LOW_OFF_VOLTS : voltage <= LOW_ON_VOLTS;
        }

        void process(double positiveInput, double negativeInput, double positiveCv,
                double negativeCv, double start, double sustain) {
            if (resumePending) {
                positive = 0.0;
                negative = 0.0;
                positivePhase = IDLE;
                startHigh = false;
                sustainHigh = false;
                positiveLowState = true;
                negativeLowState = true;
                positiveRate.snap();
                negativeRate.snap();
                resumePending = false;
            }
            double upStep = positiveRate.step(signal(positiveCv), positiveFast);
            double downStep = negativeRate.step(signal(negativeCv), negativeFast);
            boolean newStart = gate(startHigh, signal(start));
            boolean newSustain = gate(sustainHigh, signal(sustain));
            boolean fire = (newStart && !startHigh) || (newSustain && !sustainHigh);
            startHigh = newStart;
            sustainHigh = newSustain;

            // Busy edges are consumed, never queued. SUSTAIN alone can start a full rise.
            if (positivePhase == IDLE && fire) {
                positive = 0.0;
                positivePhase = RISING;
            }
            if (positivePhase == RISING) {
                positive = Math.min(selectedPeak, positive + upStep);
                if (positive >= selectedPeak) positivePhase = AT_PEAK;
            } else if (positivePhase == AT_PEAK || positivePhase == HOLDING) {
                if (sustainHigh) {
                    // Raising the voltage range while held still obeys the positive slew limit.
                    positive = Math.min(selectedPeak, positive + upStep);
                    positivePhase = positive < selectedPeak ? RISING : HOLDING;
                } else {
                    // Emit a real reset sample before returning to ordinary input following.
                    positive = 0.0;
                    positivePhase = IDLE;
                }
            } else {
                double target = signal(positiveInput);
                positive = target > positive ? Math.min(target, positive + upStep) : target;
            }

            // Fully independent input: no normal, crossfeed, rectification or voltage-range scaling.
            double target = signal(negativeInput);
            negative = target < negative ? Math.max(target, negative - downStep) : target;

            positiveLowState = lowGate(positiveLowState, positive);
            negativeLowState = lowGate(negativeLowState, negative);
            positiveHigh = positivePhase != IDLE ? PULSE_VOLTS : 0.0;
            positiveLow = positiveLowState ? PULSE_VOLTS : 0.0;
            negativePulse = negativeLowState ? PULSE_VOLTS : 0.0;
        }

        static final class RateControl {
            final double sampleRate;
            final double smoothing;
            double previousKnob = Double.NaN;
            double targetBase;
            double base;
            double targetAmount;
            double amount;
            double lastExponent = Double.NaN;
            double cvFactor = 1.0;

            RateControl(double rate) {
                sampleRate = rate;
                smoothing = 1.0 - Math.exp(-1.0 / (0.003 * rate));
            }

            void set(double knob, double attenuverter) {
                knob = unit(knob);
                if (knob != previousKnob) {
                    targetBase = PULSE_VOLTS / timeFor(knob, true);
                    previousKnob = knob;
                }
                targetAmount = bipolar(attenuverter);
            }

            void snap() {
                base = targetBase;
                amount = targetAmount;
                lastExponent = Double.NaN;
            }

            double step(double cv, boolean fast) {
                base += smoothing * (targetBase - base);
                if (Math.abs(base - targetBase) < Math.max(1.0, targetBase) * 1.0e-12) base = targetBase;
                amount += smoothing * (targetAmount - amount);
                if (Math.abs(amount - targetAmount) < 1.0e-12) amount = targetAmount;
                double exponent = Math.max(-32.0, Math.min(32.0, cv * amount));
                if (exponent != lastExponent) {
                    cvFactor = Math.exp(LOG_TWO * exponent);
                    lastExponent = exponent;
                }
                // CV moves within the selected range; the FAST/SLOW factor stays exactly 1000.
                double voltsPerSecond = Math.max(PULSE_VOLTS / FAST_MAX_SECONDS,
                        Math.min(PULSE_VOLTS / FAST_MIN_SECONDS, base * cvFactor));
                return voltsPerSecond / sampleRate / (fast ? 1.0 : RANGE_FACTOR);
            }
        }
    }
    //[/user-code-and-variables]
}

 