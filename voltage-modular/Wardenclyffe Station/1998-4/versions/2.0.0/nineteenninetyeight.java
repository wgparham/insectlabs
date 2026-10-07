package com.insectlabs.nineteenninetyeight;


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


public class nineteenninetyeight extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public nineteenninetyeight( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "1998/4 - dynamic modulator", ModuleType.ModuleType_EnvelopeGenerators, 3.2 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "76a8b4645d70488683aa869c15cfdb1d" );
    }

void InitializeControls()
{

        colophon = new VoltageLabel( "colophon", "Colophon", this, "insect laboratories pittsburgh, PA        united states & beyond" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 168, 49 );
        colophon.SetSize( 60, 20 );
        colophon.SetEditable( false, false );
        colophon.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        colophon.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        colophon.SetColor( new Color( 19, 19, 19, 147 ) );
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

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Module Description", this, "dynamic modulator" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 3 );
        descriptionLabel.SetSize( 224, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        descriptionLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
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

        numberLabel = new VoltageLabel( "numberLabel", "Model Number", this, "1998/4" );
        AddComponent( numberLabel );
        numberLabel.SetWantsMouseNotifications( false );
        numberLabel.SetPosition( 23, 339 );
        numberLabel.SetSize( 204, 13 );
        numberLabel.SetEditable( false, false );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        numberLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        numberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        numberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        numberLabel.SetBorderSize( 1 );
        numberLabel.SetMultiLineEdit( false );
        numberLabel.SetIsNumberEditor( false );
        numberLabel.SetNumberEditorRange( 0, 100 );
        numberLabel.SetNumberEditorInterval( 1 );
        numberLabel.SetNumberEditorUsesMouseWheel( false );
        numberLabel.SetHasCustomTextHoverColor( false );
        numberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        numberLabel.SetFont( "Courier New", 13, true, false );

        signalInput = new VoltageAudioJack( "signalInput", "Signal Input", this, JackType.JackType_AudioInput );
        AddComponent( signalInput );
        signalInput.SetWantsMouseNotifications( false );
        signalInput.SetPosition( 20, 115 );
        signalInput.SetSize( 37, 37 );
        signalInput.SetSkin( "Dark Jack Straight" );

        amplitudeKnob = new VoltageKnob( "amplitudeKnob", "Amplitude", this, 0.0, 1.0, 0.5 );
        AddComponent( amplitudeKnob );
        amplitudeKnob.SetWantsMouseNotifications( false );
        amplitudeKnob.SetPosition( 10, 40 );
        amplitudeKnob.SetSize( 60, 60 );
        amplitudeKnob.SetSkin( "Cosmo v2 Large" );
        amplitudeKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        amplitudeKnob.SetKnobParams( 215, 145 );
        amplitudeKnob.DisplayValueInPercent( false );
        amplitudeKnob.SetKnobAdjustsRing( true );

        filterKnob = new VoltageKnob( "filterKnob", "Detector Filter", this, 0.0, 1.0, 0.0 );
        AddComponent( filterKnob );
        filterKnob.SetWantsMouseNotifications( false );
        filterKnob.SetPosition( 75, 50 );
        filterKnob.SetSize( 40, 40 );
        filterKnob.SetSkin( "Cosmo v2 Med" );
        filterKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        filterKnob.SetKnobParams( 215, 145 );
        filterKnob.DisplayValueInPercent( false );
        filterKnob.SetKnobAdjustsRing( true );

        attackKnob = new VoltageKnob( "attackKnob", "Manual Attack", this, 0.0, 1.0, 0.0 );
        AddComponent( attackKnob );
        attackKnob.SetWantsMouseNotifications( false );
        attackKnob.SetPosition( 75, 200 );
        attackKnob.SetSize( 40, 40 );
        attackKnob.SetSkin( "Cosmo v2 Med" );
        attackKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        attackKnob.SetKnobParams( 215, 145 );
        attackKnob.DisplayValueInPercent( false );
        attackKnob.SetKnobAdjustsRing( true );

        delayKnob = new VoltageKnob( "delayKnob", "Envelope Delay", this, 0.0, 1.0, 0.0 );
        AddComponent( delayKnob );
        delayKnob.SetWantsMouseNotifications( false );
        delayKnob.SetPosition( 21, 270 );
        delayKnob.SetSize( 40, 40 );
        delayKnob.SetSkin( "Cosmo v2 Med" );
        delayKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        delayKnob.SetKnobParams( 215, 145 );
        delayKnob.DisplayValueInPercent( false );
        delayKnob.SetKnobAdjustsRing( true );

        balanceKnob = new VoltageKnob( "balanceKnob", "Envelope Balance", this, 0.0, 1.0, 0.0 );
        AddComponent( balanceKnob );
        balanceKnob.SetWantsMouseNotifications( false );
        balanceKnob.SetPosition( 125, 273 );
        balanceKnob.SetSize( 30, 30 );
        balanceKnob.SetSkin( "Cosmo Small" );
        balanceKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        balanceKnob.SetKnobParams( 215, 145 );
        balanceKnob.DisplayValueInPercent( false );
        balanceKnob.SetKnobAdjustsRing( true );

        powerSwitch = new VoltageSwitch( "powerSwitch", "Power", this, 0 );
        AddComponent( powerSwitch );
        powerSwitch.SetWantsMouseNotifications( false );
        powerSwitch.SetPosition( 195, 25 );
        powerSwitch.SetSize( 30, 30 );
        powerSwitch.SetSkin( "2-State Red Cap" );

        negativeEnvelopeOutput = new VoltageAudioJack( "negativeEnvelopeOutput", "Negative Envelope", this, JackType.JackType_AudioOutput );
        AddComponent( negativeEnvelopeOutput );
        negativeEnvelopeOutput.SetWantsMouseNotifications( false );
        negativeEnvelopeOutput.SetPosition( 170, 199 );
        negativeEnvelopeOutput.SetSize( 37, 37 );
        negativeEnvelopeOutput.SetSkin( "Rotated Half" );

        positiveEnvelopeOutput = new VoltageAudioJack( "positiveEnvelopeOutput", "Positive Envelope", this, JackType.JackType_AudioOutput );
        AddComponent( positiveEnvelopeOutput );
        positiveEnvelopeOutput.SetWantsMouseNotifications( false );
        positiveEnvelopeOutput.SetPosition( 170, 160 );
        positiveEnvelopeOutput.SetSize( 37, 37 );
        positiveEnvelopeOutput.SetSkin( "Rotated Half" );

        thresholdKnob = new VoltageKnob( "thresholdKnob", "Gate Threshold", this, 0.0, 1.0, 1.0 );
        AddComponent( thresholdKnob );
        thresholdKnob.SetWantsMouseNotifications( false );
        thresholdKnob.SetPosition( 120, 50 );
        thresholdKnob.SetSize( 40, 40 );
        thresholdKnob.SetSkin( "Cosmo v2 Med" );
        thresholdKnob.SetRange( 0.0, 1.0, 1.0, false, 0 );
        thresholdKnob.SetKnobParams( 215, 145 );
        thresholdKnob.DisplayValueInPercent( false );
        thresholdKnob.SetKnobAdjustsRing( true );

        gateOutput = new VoltageAudioJack( "gateOutput", "Gate", this, JackType.JackType_AudioOutput );
        AddComponent( gateOutput );
        gateOutput.SetWantsMouseNotifications( false );
        gateOutput.SetPosition( 170, 238 );
        gateOutput.SetSize( 37, 37 );
        gateOutput.SetSkin( "Rotated Half" );

        amplitudeLabel = new VoltageLabel( "amplitudeLabel", "Amplitude label", this, "AMPLITUDE" );
        AddComponent( amplitudeLabel );
        amplitudeLabel.SetWantsMouseNotifications( false );
        amplitudeLabel.SetPosition( 0, 96 );
        amplitudeLabel.SetSize( 80, 20 );
        amplitudeLabel.SetEditable( false, false );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        amplitudeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        amplitudeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        amplitudeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        amplitudeLabel.SetBorderSize( 1 );
        amplitudeLabel.SetMultiLineEdit( false );
        amplitudeLabel.SetIsNumberEditor( false );
        amplitudeLabel.SetNumberEditorRange( 0, 100 );
        amplitudeLabel.SetNumberEditorInterval( 1 );
        amplitudeLabel.SetNumberEditorUsesMouseWheel( false );
        amplitudeLabel.SetHasCustomTextHoverColor( false );
        amplitudeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        amplitudeLabel.SetFont( "Arial", 8, true, false );

        filterLabel = new VoltageLabel( "filterLabel", "Filter label", this, "FILTER" );
        AddComponent( filterLabel );
        filterLabel.SetWantsMouseNotifications( false );
        filterLabel.SetPosition( 53, 88 );
        filterLabel.SetSize( 80, 20 );
        filterLabel.SetEditable( false, false );
        filterLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        filterLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        filterLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        filterLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        filterLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        filterLabel.SetBorderSize( 1 );
        filterLabel.SetMultiLineEdit( false );
        filterLabel.SetIsNumberEditor( false );
        filterLabel.SetNumberEditorRange( 0, 100 );
        filterLabel.SetNumberEditorInterval( 1 );
        filterLabel.SetNumberEditorUsesMouseWheel( false );
        filterLabel.SetHasCustomTextHoverColor( false );
        filterLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        filterLabel.SetFont( "Arial", 8, true, false );

        thresholdLabel = new VoltageLabel( "thresholdLabel", "Threshold label", this, "THRESHOLD" );
        AddComponent( thresholdLabel );
        thresholdLabel.SetWantsMouseNotifications( false );
        thresholdLabel.SetPosition( 100, 88 );
        thresholdLabel.SetSize( 80, 20 );
        thresholdLabel.SetEditable( false, false );
        thresholdLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        thresholdLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        thresholdLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        thresholdLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        thresholdLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        thresholdLabel.SetBorderSize( 1 );
        thresholdLabel.SetMultiLineEdit( false );
        thresholdLabel.SetIsNumberEditor( false );
        thresholdLabel.SetNumberEditorRange( 0, 100 );
        thresholdLabel.SetNumberEditorInterval( 1 );
        thresholdLabel.SetNumberEditorUsesMouseWheel( false );
        thresholdLabel.SetHasCustomTextHoverColor( false );
        thresholdLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        thresholdLabel.SetFont( "Arial", 8, true, false );

        signalInputLabel = new VoltageLabel( "signalInputLabel", "Signal input label", this, "S" );
        AddComponent( signalInputLabel );
        signalInputLabel.SetWantsMouseNotifications( false );
        signalInputLabel.SetPosition( 0, 145 );
        signalInputLabel.SetSize( 80, 20 );
        signalInputLabel.SetEditable( false, false );
        signalInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        signalInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        signalInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        signalInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        signalInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        signalInputLabel.SetBorderSize( 1 );
        signalInputLabel.SetMultiLineEdit( false );
        signalInputLabel.SetIsNumberEditor( false );
        signalInputLabel.SetNumberEditorRange( 0, 100 );
        signalInputLabel.SetNumberEditorInterval( 1 );
        signalInputLabel.SetNumberEditorUsesMouseWheel( false );
        signalInputLabel.SetHasCustomTextHoverColor( false );
        signalInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        signalInputLabel.SetFont( "Arial", 13, true, false );

        attackLabel = new VoltageLabel( "attackLabel", "Attack label", this, "ATTACK" );
        AddComponent( attackLabel );
        attackLabel.SetWantsMouseNotifications( false );
        attackLabel.SetPosition( 54, 239 );
        attackLabel.SetSize( 80, 20 );
        attackLabel.SetEditable( false, false );
        attackLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        attackLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        attackLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        attackLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        attackLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        attackLabel.SetBorderSize( 1 );
        attackLabel.SetMultiLineEdit( false );
        attackLabel.SetIsNumberEditor( false );
        attackLabel.SetNumberEditorRange( 0, 100 );
        attackLabel.SetNumberEditorInterval( 1 );
        attackLabel.SetNumberEditorUsesMouseWheel( false );
        attackLabel.SetHasCustomTextHoverColor( false );
        attackLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        attackLabel.SetFont( "Arial", 8, true, false );

        delayLabel = new VoltageLabel( "delayLabel", "Delay label", this, "DELAY" );
        AddComponent( delayLabel );
        delayLabel.SetWantsMouseNotifications( false );
        delayLabel.SetPosition( 10, 307 );
        delayLabel.SetSize( 60, 20 );
        delayLabel.SetEditable( false, false );
        delayLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        delayLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        delayLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        delayLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        delayLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        delayLabel.SetBorderSize( 1 );
        delayLabel.SetMultiLineEdit( false );
        delayLabel.SetIsNumberEditor( false );
        delayLabel.SetNumberEditorRange( 0, 100 );
        delayLabel.SetNumberEditorInterval( 1 );
        delayLabel.SetNumberEditorUsesMouseWheel( false );
        delayLabel.SetHasCustomTextHoverColor( false );
        delayLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        delayLabel.SetFont( "Arial", 8, true, false );

        balanceLabel = new VoltageLabel( "balanceLabel", "Balance label", this, "BALANCE" );
        AddComponent( balanceLabel );
        balanceLabel.SetWantsMouseNotifications( false );
        balanceLabel.SetPosition( 99, 299 );
        balanceLabel.SetSize( 80, 20 );
        balanceLabel.SetEditable( false, false );
        balanceLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        balanceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        balanceLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        balanceLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        balanceLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        balanceLabel.SetBorderSize( 1 );
        balanceLabel.SetMultiLineEdit( false );
        balanceLabel.SetIsNumberEditor( false );
        balanceLabel.SetNumberEditorRange( 0, 100 );
        balanceLabel.SetNumberEditorInterval( 1 );
        balanceLabel.SetNumberEditorUsesMouseWheel( false );
        balanceLabel.SetHasCustomTextHoverColor( false );
        balanceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        balanceLabel.SetFont( "Arial", 8, true, false );

        positiveEnvelopeLabel = new VoltageLabel( "positiveEnvelopeLabel", "Positive envelope label", this, "+" );
        AddComponent( positiveEnvelopeLabel );
        positiveEnvelopeLabel.SetWantsMouseNotifications( false );
        positiveEnvelopeLabel.SetPosition( 203, 166 );
        positiveEnvelopeLabel.SetSize( 20, 20 );
        positiveEnvelopeLabel.SetEditable( false, false );
        positiveEnvelopeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveEnvelopeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveEnvelopeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveEnvelopeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveEnvelopeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveEnvelopeLabel.SetBorderSize( 1 );
        positiveEnvelopeLabel.SetMultiLineEdit( false );
        positiveEnvelopeLabel.SetIsNumberEditor( false );
        positiveEnvelopeLabel.SetNumberEditorRange( 0, 100 );
        positiveEnvelopeLabel.SetNumberEditorInterval( 1 );
        positiveEnvelopeLabel.SetNumberEditorUsesMouseWheel( false );
        positiveEnvelopeLabel.SetHasCustomTextHoverColor( false );
        positiveEnvelopeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveEnvelopeLabel.SetFont( "Arial", 13, true, false );

        negativeEnvelopeLabel = new VoltageLabel( "negativeEnvelopeLabel", "Negative envelope label", this, "-" );
        AddComponent( negativeEnvelopeLabel );
        negativeEnvelopeLabel.SetWantsMouseNotifications( false );
        negativeEnvelopeLabel.SetPosition( 203, 203 );
        negativeEnvelopeLabel.SetSize( 20, 20 );
        negativeEnvelopeLabel.SetEditable( false, false );
        negativeEnvelopeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeEnvelopeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeEnvelopeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeEnvelopeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeEnvelopeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeEnvelopeLabel.SetBorderSize( 1 );
        negativeEnvelopeLabel.SetMultiLineEdit( false );
        negativeEnvelopeLabel.SetIsNumberEditor( false );
        negativeEnvelopeLabel.SetNumberEditorRange( 0, 100 );
        negativeEnvelopeLabel.SetNumberEditorInterval( 1 );
        negativeEnvelopeLabel.SetNumberEditorUsesMouseWheel( false );
        negativeEnvelopeLabel.SetHasCustomTextHoverColor( false );
        negativeEnvelopeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeEnvelopeLabel.SetFont( "Arial", 23, true, false );

        gateLabel = new VoltageLabel( "gateLabel", "Gate label", this, "G" );
        AddComponent( gateLabel );
        gateLabel.SetWantsMouseNotifications( false );
        gateLabel.SetPosition( 203, 245 );
        gateLabel.SetSize( 20, 20 );
        gateLabel.SetEditable( false, false );
        gateLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        gateLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        gateLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        gateLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        gateLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        gateLabel.SetBorderSize( 1 );
        gateLabel.SetMultiLineEdit( false );
        gateLabel.SetIsNumberEditor( false );
        gateLabel.SetNumberEditorRange( 0, 100 );
        gateLabel.SetNumberEditorInterval( 1 );
        gateLabel.SetNumberEditorUsesMouseWheel( false );
        gateLabel.SetHasCustomTextHoverColor( false );
        gateLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        gateLabel.SetFont( "Arial", 13, true, false );

        powerLed = new VoltageLED( "powerLed", "Power Indicator", this );
        AddComponent( powerLed );
        powerLed.SetWantsMouseNotifications( false );
        powerLed.SetPosition( 177, 31 );
        powerLed.SetSize( 15, 15 );
        powerLed.SetSkin( "2500 Lamp Red" );

        delayedGateOutput = new VoltageAudioJack( "delayedGateOutput", "Delayed Gate Courtesy", this, JackType.JackType_AudioOutput );
        AddComponent( delayedGateOutput );
        delayedGateOutput.SetWantsMouseNotifications( false );
        delayedGateOutput.SetPosition( 177, 307 );
        delayedGateOutput.SetSize( 25, 25 );
        delayedGateOutput.SetSkin( "Jack Round Cherry Ring" );

        autoAttackLabel = new VoltageLabel( "autoAttackLabel", "Auto attack label", this, "AUTO" );
        AddComponent( autoAttackLabel );
        autoAttackLabel.SetWantsMouseNotifications( false );
        autoAttackLabel.SetPosition( 27, 193 );
        autoAttackLabel.SetSize( 25, 25 );
        autoAttackLabel.SetEditable( false, false );
        autoAttackLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        autoAttackLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        autoAttackLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        autoAttackLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        autoAttackLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        autoAttackLabel.SetBorderSize( 1 );
        autoAttackLabel.SetMultiLineEdit( false );
        autoAttackLabel.SetIsNumberEditor( false );
        autoAttackLabel.SetNumberEditorRange( 0, 100 );
        autoAttackLabel.SetNumberEditorInterval( 1 );
        autoAttackLabel.SetNumberEditorUsesMouseWheel( false );
        autoAttackLabel.SetHasCustomTextHoverColor( false );
        autoAttackLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        autoAttackLabel.SetFont( "Arial", 6, true, false );

        manualAttackLabel = new VoltageLabel( "manualAttackLabel", "Manual attack label", this, "MANUAL" );
        AddComponent( manualAttackLabel );
        manualAttackLabel.SetWantsMouseNotifications( false );
        manualAttackLabel.SetPosition( 27, 228 );
        manualAttackLabel.SetSize( 25, 25 );
        manualAttackLabel.SetEditable( false, false );
        manualAttackLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manualAttackLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manualAttackLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        manualAttackLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manualAttackLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manualAttackLabel.SetBorderSize( 1 );
        manualAttackLabel.SetMultiLineEdit( false );
        manualAttackLabel.SetIsNumberEditor( false );
        manualAttackLabel.SetNumberEditorRange( 0, 100 );
        manualAttackLabel.SetNumberEditorInterval( 1 );
        manualAttackLabel.SetNumberEditorUsesMouseWheel( false );
        manualAttackLabel.SetHasCustomTextHoverColor( false );
        manualAttackLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manualAttackLabel.SetFont( "Arial", 6, true, false );

        signalThroughOutput = new VoltageAudioJack( "signalThroughOutput", "Signal Through", this, JackType.JackType_AudioOutput );
        AddComponent( signalThroughOutput );
        signalThroughOutput.SetWantsMouseNotifications( false );
        signalThroughOutput.SetPosition( 82, 121 );
        signalThroughOutput.SetSize( 25, 25 );
        signalThroughOutput.SetSkin( "Mini Jack 25px" );

        delayedEnvelopeOutput = new VoltageAudioJack( "delayedEnvelopeOutput", "Delayed Envelope Courtesy", this, JackType.JackType_AudioOutput );
        AddComponent( delayedEnvelopeOutput );
        delayedEnvelopeOutput.SetWantsMouseNotifications( false );
        delayedEnvelopeOutput.SetPosition( 82, 278 );
        delayedEnvelopeOutput.SetSize( 25, 25 );
        delayedEnvelopeOutput.SetSkin( "Mini Jack 25px" );

        attackModeSwitch = new VoltageSwitch( "attackModeSwitch", "Attack Mode", this, 1 );
        AddComponent( attackModeSwitch );
        attackModeSwitch.SetWantsMouseNotifications( false );
        attackModeSwitch.SetPosition( 25, 208 );
        attackModeSwitch.SetSize( 30, 30 );
        attackModeSwitch.SetSkin( "2-State Silver" );

        gateLed = new VoltageLED( "gateLed", "Gate Indicator", this );
        AddComponent( gateLed );
        gateLed.SetWantsMouseNotifications( false );
        gateLed.SetPosition( 159, 253 );
        gateLed.SetSize( 8, 8 );
        gateLed.SetSkin( "2500 Lamp Green" );

        delayedGateLed = new VoltageLED( "delayedGateLed", "Delayed Gate Indicator", this );
        AddComponent( delayedGateLed );
        delayedGateLed.SetWantsMouseNotifications( false );
        delayedGateLed.SetPosition( 209, 316 );
        delayedGateLed.SetSize( 8, 8 );
        delayedGateLed.SetSkin( "2500 Lamp Green" );

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Label", this, "iL" );
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
        silenceOutputs();

        powerLed.SetValue(0.0);
        displayedPowerLevel = 0.0;
        powerDisplayCountdown = 1;

        gateLed.SetValue(0.0);
        delayedGateLed.SetValue(0.0);

        followingCore.resumePending = true;
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
        boolean powered = powerSwitch.GetValue() >= 0.5;

        if (--powerDisplayCountdown <= 0) {
        powerDisplayCountdown = 480; // 100 Hz visual refresh.

        double lamp = followingCore.powerGain;

        if (Math.abs(lamp - displayedPowerLevel) > 0.002) {
            powerLed.SetValue(lamp);
            displayedPowerLevel = lamp;
    }
}
        if (!powered && followingCore.powerGain == 0.0 && !followingCore.resumePending) {
            silenceOutputs();
            return;
        }
        followingCore.setControls(amplitudeKnob.GetValue(), filterKnob.GetValue(),
                attackKnob.GetValue(), thresholdKnob.GetValue(), delayKnob.GetValue(),
                balanceKnob.GetValue(), attackModeSwitch.GetValue() >= 0.5);
        followingCore.process(inputValue(signalInput), powered);
        signalThroughOutput.SetValue(followingCore.through);
        positiveEnvelopeOutput.SetValue(followingCore.positive);
        negativeEnvelopeOutput.SetValue(followingCore.negative);
        gateOutput.SetValue(followingCore.immediateGate);
        delayedEnvelopeOutput.SetValue(followingCore.delayed);
        delayedGateOutput.SetValue(followingCore.delayedGate);
        updateGateIndicators(followingCore.immediateGate > 0.0, followingCore.delayedGate > 0.0);
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
        // Direct host bypass: no controls or DSP histories are advanced.
        followingCore.processBypassed(inputValue(signalInput));
        signalThroughOutput.SetValue(followingCore.through);
        positiveEnvelopeOutput.SetValue(0.0);
        negativeEnvelopeOutput.SetValue(0.0);
        gateOutput.SetValue(0.0);
        delayedEnvelopeOutput.SetValue(0.0);
        delayedGateOutput.SetValue(0.0);
        updateGateIndicators(false, false);
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
        if (component == signalInput) return "Signal input: feeds the shared AMPLITUDE gain and drive stage";
        if (component == powerLed)
    return "Power indicator: follows the 1.3 s warmup and 0.7 s cooldown envelope";
        if (component == amplitudeKnob) return String.format(java.util.Locale.ROOT,
                "Amplitude: %.2fx; shared gain and drive", FollowingCore.gainFor(unit(amplitudeKnob.GetValue())));
        if (component == filterKnob) return String.format(java.util.Locale.ROOT,
                "Detector high-pass: %.1f Hz", FollowingCore.filterFor(unit(filterKnob.GetValue())));
        if (component == thresholdKnob) return String.format(java.util.Locale.ROOT,
                "Gate threshold: %.3f V", FollowingCore.thresholdFor(unit(thresholdKnob.GetValue())));
        if (component == attackKnob) return String.format(java.util.Locale.ROOT,
                "Manual attack: %.2f ms%s", 1000.0 * FollowingCore.attackFor(unit(attackKnob.GetValue())),
                attackModeSwitch.GetValue() >= 0.5 ? " (inactive in AUTO)" : "");
        if (component == delayKnob) return String.format(java.util.Locale.ROOT,
                "Envelope delay: %.1f ms", 1000.0 * FollowingCore.delayFor(unit(delayKnob.GetValue())));
        if (component == balanceKnob) return String.format(java.util.Locale.ROOT,
                "Balance: %.0f%% delayed / %.0f%% immediate", 100.0*unit(balanceKnob.GetValue()),
                100.0*(1.0-unit(balanceKnob.GetValue())));
        if (component == powerSwitch) return powerSwitch.GetValue() >= 0.5
            ? "Power: On; 1.3 s warmup"
            : "Power: Off; 0.7 s cooldown";
        if (component == attackModeSwitch) return attackModeSwitch.GetValue() >= 0.5 ? "Attack: AUTO" : "Attack: MANUAL";
        if (component == gateLed) return "Lit while G is high";
        if (component == delayedGateLed) return "Lit while DELAYED GATE is high";
        if (component == signalThroughOutput) return "Signal Through: audio after AMPLITUDE and shared drive, before detector filtering";
        if (component == positiveEnvelopeOutput) return "Positive envelope: 0 to +5 V, set by BALANCE";
        if (component == negativeEnvelopeOutput) return "Negative envelope: inverted positive output, 0 to -5 V";
        if (component == delayedEnvelopeOutput) return "Delayed envelope: 0 to +5 V, independent of BALANCE";
        if (component == gateOutput) return "Gate: 0/+5 V from immediate envelope, independent of BALANCE";
        if (component == delayedGateOutput) return "Delayed gate: 0/+5 V from delayed envelope";
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
        if (component == amplitudeKnob) {
            amplitudeKnob.SetValue(Math.sqrt(Math.max(0.0, Math.min(4.0, newValue))/4.0));
            return;
        }
        if (component == filterKnob) {
            filterKnob.SetValue(unit(Math.log(Math.max(20.0,newValue)/20.0)/Math.log(10.0)));
            return;
        }
        if (component == thresholdKnob) {
            thresholdKnob.SetValue(unit(Math.log1p(Math.max(0.0,newValue)/0.05)/Math.log(101.0)));
            return;
        }
        if (component == attackKnob) {
            attackKnob.SetValue(unit(Math.log(Math.max(0.2,newValue)/0.2)/Math.log(5000.0)));
            return;
        }
        if (component == delayKnob) {
            delayKnob.SetValue(unit(Math.log1p(Math.max(0.0,newValue))/Math.log(3001.0)));
            return;
        }
        if (component == balanceKnob) {
            balanceKnob.SetValue(unit(newValue/100.0));
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
    private VoltageLabel manufacturerLabel;
    private VoltageLED delayedGateLed;
    private VoltageLED gateLed;
    private VoltageSwitch attackModeSwitch;
    private VoltageAudioJack delayedEnvelopeOutput;
    private VoltageAudioJack signalThroughOutput;
    private VoltageLabel manualAttackLabel;
    private VoltageLabel autoAttackLabel;
    private VoltageAudioJack delayedGateOutput;
    private VoltageLED powerLed;
    private VoltageLabel gateLabel;
    private VoltageLabel negativeEnvelopeLabel;
    private VoltageLabel positiveEnvelopeLabel;
    private VoltageLabel balanceLabel;
    private VoltageLabel delayLabel;
    private VoltageLabel attackLabel;
    private VoltageLabel signalInputLabel;
    private VoltageLabel thresholdLabel;
    private VoltageLabel filterLabel;
    private VoltageLabel amplitudeLabel;
    private VoltageAudioJack gateOutput;
    private VoltageKnob thresholdKnob;
    private VoltageAudioJack positiveEnvelopeOutput;
    private VoltageAudioJack negativeEnvelopeOutput;
    private VoltageSwitch powerSwitch;
    private VoltageKnob balanceKnob;
    private VoltageKnob delayKnob;
    private VoltageKnob attackKnob;
    private VoltageKnob filterKnob;
    private VoltageKnob amplitudeKnob;
    private VoltageAudioJack signalInput;
    private VoltageLabel numberLabel;
    private VoltageLabel descriptionLabel;
    private VoltageLabel colophon;


    //[user-code-and-variables]    Add your own variables and functions here
    private final FollowingCore followingCore = new FollowingCore(48000.0);
    private double displayedPowerLevel = -1.0;
    private int powerDisplayCountdown = 1;

    private boolean displayedGate;
    private boolean displayedDelayedGate;

    private static double unit(double value) {
        return Double.isFinite(value) ? Math.max(0.0, Math.min(1.0, value)) : 0.0;
    }

    private static double inputValue(VoltageAudioJack jack) {
        double value = jack.GetValue();
        return Double.isFinite(value) ? Math.max(-1000000.0, Math.min(1000000.0, value)) : 0.0;
    }

    private void updateGateIndicators(boolean gate, boolean delayedGate) {
        // Native indicator writes are needed only when the corresponding gate changes.
        if (displayedGate != gate) {
            gateLed.SetValue(gate ? 1.0 : 0.0);
            displayedGate = gate;
        }
        if (displayedDelayedGate != delayedGate) {
            delayedGateLed.SetValue(delayedGate ? 1.0 : 0.0);
            displayedDelayedGate = delayedGate;
        }
    }

    private void silenceOutputs() {
        signalThroughOutput.SetValue(0.0);
        positiveEnvelopeOutput.SetValue(0.0);
        negativeEnvelopeOutput.SetValue(0.0);
        gateOutput.SetValue(0.0);
        delayedEnvelopeOutput.SetValue(0.0);
        delayedGateOutput.SetValue(0.0);
        updateGateIndicators(false, false);
    }

    // Independent of host objects so the actual DSP can be exercised in the test harness.
    static final class FollowingCore {
        final double sampleRate;
        final double[] delayLine;
        final double controlCoefficient;
        final double powerWarmupStep;
        final double powerCooldownStep;
        final double toneCoefficient;
        final double quickAttack, gentleAttack, peakRetention, memoryCoefficient;
        final double fastReleaseShort, fastReleaseLong, tailReleaseShort, tailReleaseLong;
        double gainTarget, filterTarget, attackTarget, thresholdTarget, balanceTarget, delayTarget;
        double gain, filter, attack, threshold, balance, delaySamples;
        double cachedGain = -1, cachedFilter = -1, cachedAttack = -1;
        double cachedThreshold = -1, cachedDelay = -1;
        double previousInput, toneState, highPassLow, envelope, recentPeak, programMemory;
        double powerGain, through, positive, negative, immediateGate, delayed, delayedGate;
        boolean automatic, gateHigh, delayedGateHigh, resumePending = true;
        int writeIndex, validSamples;

        FollowingCore(double rate) {
            sampleRate = rate;
            delayLine = new double[(int) Math.ceil(rate * 3.0) + 2];
            controlCoefficient = coefficient(0.005);
            powerWarmupStep = 1.0 / (rate * 1.3);
            powerCooldownStep = 1.0 / (rate * 0.7);
            toneCoefficient = 1.0 - Math.exp(-2.0 * Math.PI * 20000.0 / (rate * 2.0));
            quickAttack = coefficient(0.0002);
            gentleAttack = coefficient(0.004);
            peakRetention = Math.exp(-1.0 / (rate * 1.5));
            memoryCoefficient = coefficient(0.12);
            fastReleaseShort = coefficient(0.025);
            fastReleaseLong = coefficient(0.090);
            tailReleaseShort = coefficient(0.150);
            tailReleaseLong = coefficient(0.650);
        }

        double coefficient(double seconds) {
            return 1.0 - Math.exp(-1.0 / (sampleRate * seconds));
        }

        static double gainFor(double position) {
            return 4.0 * position * position;
        }

        static double filterFor(double position) {
            return 20.0 * Math.pow(10.0, position);
        }

        static double attackFor(double position) {
            return 0.0002 * Math.pow(5000.0, position);
        }

        static double delayFor(double position) {
            return Math.expm1(position * Math.log(3001.0)) / 1000.0;
        }

        static double thresholdFor(double position) {
            return Math.min(5.0, 0.05 * Math.expm1(position * Math.log(101.0)));
        }

        void setControls(double amplitude, double cutoff, double manualAttack,
                double gateThreshold, double delay, double mix, boolean autoAttack) {
            amplitude = unit(amplitude);
            cutoff = unit(cutoff);
            manualAttack = unit(manualAttack);
            gateThreshold = unit(gateThreshold);
            delay = unit(delay);
            if (amplitude != cachedGain) {
                cachedGain = amplitude;
                gainTarget = gainFor(amplitude);
            }
            if (cutoff != cachedFilter) {
                cachedFilter = cutoff;
                filterTarget = 1.0 - Math.exp(-2.0 * Math.PI * filterFor(cutoff) / sampleRate);
            }
            if (manualAttack != cachedAttack) {
                cachedAttack = manualAttack;
                attackTarget = coefficient(attackFor(manualAttack));
            }
            if (gateThreshold != cachedThreshold) {
                cachedThreshold = gateThreshold;
                thresholdTarget = thresholdFor(gateThreshold);
            }
            if (delay != cachedDelay) {
                cachedDelay = delay;
                delayTarget = Math.min(sampleRate * 3.0, delayFor(delay) * sampleRate);
            }
            balanceTarget = unit(mix);
            automatic = autoAttack;
        }

        void snapControls() {
            gain = gainTarget;
            filter = filterTarget;
            attack = attackTarget;
            threshold = thresholdTarget;
            balance = balanceTarget;
            delaySamples = delayTarget;
        }

        void resetHistory() {
            previousInput = toneState = highPassLow = envelope = recentPeak = programMemory = 0.0;
            gateHigh = delayedGateHigh = false;
            // Invalidating the ring is constant-time; no clearing/allocation on the audio thread.
            writeIndex = validSamples = 0;
        }

        void clearOutputs() {
            through = positive = negative = immediateGate = delayed = delayedGate = 0.0;
        }

        void processBypassed(double input) {
            through = input;
            positive = negative = immediateGate = delayed = delayedGate = 0.0;
            resumePending = true;
        }

        void process(double input, boolean powered) {
            if (resumePending) {
                resetHistory();
                snapControls();
                powerGain = 0.0;
                resumePending = false;
            }
            if (!powered && powerGain == 0.0) {
                clearOutputs();
                return;
            }
            powerGain = powered
        ? Math.min(
                1.0,
                powerGain + powerWarmupStep
        )
        : Math.max(
                0.0,
                powerGain - powerCooldownStep
        );
            if (powerGain == 0.0) {
                resetHistory();
                clearOutputs();
                return;
            }
            gain += controlCoefficient * (gainTarget - gain);
            filter += controlCoefficient * (filterTarget - filter);
            attack += controlCoefficient * (attackTarget - attack);
            threshold += controlCoefficient * (thresholdTarget - threshold);
            balance += controlCoefficient * (balanceTarget - balance);
            delaySamples += controlCoefficient * (delayTarget - delaySamples);
            if (Math.abs(delaySamples - delayTarget) < 1.0e-7) delaySamples = delayTarget;

            // Shared input amplifier: midpoint 2x character processing, as in SIGPROC.
            double driven = (Double.isFinite(input) ? Math.max(-1.0e6, Math.min(1.0e6, input)) : 0.0) * gain;
            characterStep((previousInput + driven) * 0.5);
            characterStep(driven);
            previousInput = driven;
            through = toneState * powerGain;

            // Only the detector branch is high-passed. Audio through is tapped above.
            highPassLow += filter * (toneState - highPassLow);
            double detected = Math.abs(toneState - highPassLow);
            followEnvelope(detected);
            double immediate = Math.min(5.0, envelope);
            double shifted = delayEnvelope(immediate);
            double combined = immediate + balance * (shifted - immediate);
            positive = combined * powerGain;
            negative = -positive;
            delayed = shifted * powerGain;
            gateHigh = gateState(gateHigh, immediate, threshold);
            delayedGateHigh = gateState(delayedGateHigh, shifted, threshold);
            immediateGate = gateHigh ? 5.0 * powerGain : 0.0;
            delayedGate = delayedGateHigh ? 5.0 * powerGain : 0.0;
        }

        void characterStep(double value) {
            double magnitude = Math.abs(value);
            if (magnitude > 3.5) {
                double excess = magnitude - 3.5;
                magnitude = 3.5 + excess / (1.0 + 0.20 * excess);
            }
            double shaped = Math.copySign(magnitude, value);
            toneState += toneCoefficient * (shaped - toneState);
        }

        void followEnvelope(double detected) {
            recentPeak = Math.max(detected, recentPeak * peakRetention);
            double relativeInput = Math.min(1.0, detected / Math.max(0.001, recentPeak));
            programMemory += memoryCoefficient * (relativeInput - programMemory);
            if (detected > envelope) {
                double transientStrength = Math.min(1.0, (detected - envelope) / Math.max(0.1, recentPeak * 0.4));
                double rise = automatic ? gentleAttack + transientStrength * (quickAttack - gentleAttack) : attack;
                envelope += rise * (detected - envelope);
            } else {
                // High recent level and sustained material lengthen recovery. As the envelope
                // drops below its recent peak, the fast section eases into the slower tail.
                double history = unit(0.5 * Math.min(1.0, recentPeak / 5.0) + 0.5 * programMemory);
                double fast = fastReleaseShort + history * (fastReleaseLong - fastReleaseShort);
                double tail = tailReleaseShort + history * (tailReleaseLong - tailReleaseShort);
                double stage = unit((envelope / Math.max(0.001, recentPeak) - 0.15) / 0.50);
                stage = stage * stage * (3.0 - 2.0 * stage);
                envelope += (tail + stage * (fast - tail)) * (detected - envelope);
            }
            if (envelope < 1.0e-12) envelope = 0.0;
        }

        double delayEnvelope(double value) {
            delayLine[writeIndex] = value;
            if (validSamples < delayLine.length) ++validSamples;
            int whole = (int) delaySamples;
            double fraction = delaySamples - whole;
            int first = writeIndex - whole;
            if (first < 0) first += delayLine.length;
            int second = first == 0 ? delayLine.length - 1 : first - 1;
            double a = whole < validSamples ? delayLine[first] : 0.0;
            double b = whole + 1 < validSamples ? delayLine[second] : 0.0;
            double result = a + fraction * (b - a);
            if (++writeIndex == delayLine.length) writeIndex = 0;
            return result;
        }

        static boolean gateState(boolean high, double value, double threshold) {
            double opening = Math.max(0.001, threshold);
            double closing = Math.max(0.0005, opening - 0.025);
            return high ? value > closing : value >= opening;
        }
    }
    //[/user-code-and-variables]
}

 