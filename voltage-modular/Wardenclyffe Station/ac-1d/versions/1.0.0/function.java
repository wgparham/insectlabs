package com.insectlabs.function;


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


public class function extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public function( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "function", ModuleType.ModuleType_Oscillators, 3.2 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "cafce17c6ba340afae55ea5f9e417c51" );
    }

void InitializeControls()
{

        sineAmplitudeKnob = new VoltageKnob( "sineAmplitudeKnob", "Sine Amplitude", this, 0.0, 1.0, 0.0 );
        AddComponent( sineAmplitudeKnob );
        sineAmplitudeKnob.SetWantsMouseNotifications( false );
        sineAmplitudeKnob.SetPosition( 140, 280 );
        sineAmplitudeKnob.SetSize( 35, 35 );
        sineAmplitudeKnob.SetSkin( "TR Large" );
        sineAmplitudeKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        sineAmplitudeKnob.SetKnobParams( 215, 145 );
        sineAmplitudeKnob.DisplayValueInPercent( true );
        sineAmplitudeKnob.SetKnobAdjustsRing( true );

        brandLabel = new VoltageLabel( "brandLabel", "Brand Label", this, "refuge" );
        AddComponent( brandLabel );
        brandLabel.SetWantsMouseNotifications( false );
        brandLabel.SetPosition( 0, 335 );
        brandLabel.SetSize( 230, 23 );
        brandLabel.SetEditable( false, false );
        brandLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        brandLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        brandLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        brandLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        brandLabel.SetBorderColor( new Color( 147, 147, 0, 255 ) );
        brandLabel.SetBorderSize( 1 );
        brandLabel.SetMultiLineEdit( false );
        brandLabel.SetIsNumberEditor( false );
        brandLabel.SetNumberEditorRange( 0, 100 );
        brandLabel.SetNumberEditorInterval( 1 );
        brandLabel.SetNumberEditorUsesMouseWheel( false );
        brandLabel.SetHasCustomTextHoverColor( false );
        brandLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        brandLabel.SetFont( "Courier New", 13, true, false );

        titleLabel = new VoltageLabel( "titleLabel", "Title Label", this, "AC/1D FUNCTION GENERATOR" );
        AddComponent( titleLabel );
        titleLabel.SetWantsMouseNotifications( false );
        titleLabel.SetPosition( 0, 3 );
        titleLabel.SetSize( 230, 13 );
        titleLabel.SetEditable( false, false );
        titleLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        titleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        titleLabel.SetColor( new Color( 147, 147, 0, 255 ) );
        titleLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        titleLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        titleLabel.SetBorderSize( 1 );
        titleLabel.SetMultiLineEdit( false );
        titleLabel.SetIsNumberEditor( false );
        titleLabel.SetNumberEditorRange( 0, 100 );
        titleLabel.SetNumberEditorInterval( 1 );
        titleLabel.SetNumberEditorUsesMouseWheel( false );
        titleLabel.SetHasCustomTextHoverColor( false );
        titleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        titleLabel.SetFont( "Courier New", 14, true, false );

        powerSwitch = new VoltageSwitch( "powerSwitch", "Power Switch", this, 0 );
        AddComponent( powerSwitch );
        powerSwitch.SetWantsMouseNotifications( false );
        powerSwitch.SetPosition( 175, 30 );
        powerSwitch.SetSize( 40, 40 );
        powerSwitch.SetSkin( "2-State Red Cap" );

        powerIndicator = new VoltageLED( "powerIndicator", "Power Indicator", this );
        AddComponent( powerIndicator );
        powerIndicator.SetWantsMouseNotifications( false );
        powerIndicator.SetPosition( 210, 45 );
        powerIndicator.SetSize( 10, 10 );
        powerIndicator.SetSkin( "2500 Lamp Red" );

        squareOutput = new VoltageAudioJack( "squareOutput", "Square Output", this, JackType.JackType_AudioOutput );
        AddComponent( squareOutput );
        squareOutput.SetWantsMouseNotifications( false );
        squareOutput.SetPosition( 15, 285 );
        squareOutput.SetSize( 25, 25 );
        squareOutput.SetSkin( "Mini Jack 25px" );

        squareAmplitudeKnob = new VoltageKnob( "squareAmplitudeKnob", "Square Amplitude", this, 0.0, 1.0, 0.0 );
        AddComponent( squareAmplitudeKnob );
        squareAmplitudeKnob.SetWantsMouseNotifications( false );
        squareAmplitudeKnob.SetPosition( 50, 280 );
        squareAmplitudeKnob.SetSize( 35, 35 );
        squareAmplitudeKnob.SetSkin( "TR Large" );
        squareAmplitudeKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        squareAmplitudeKnob.SetKnobParams( 215, 145 );
        squareAmplitudeKnob.DisplayValueInPercent( true );
        squareAmplitudeKnob.SetKnobAdjustsRing( true );

        squareRangeKnob = new VoltageKnob( "squareRangeKnob", "Square Range", this, 1, 3, 2 );
        AddComponent( squareRangeKnob );
        squareRangeKnob.SetWantsMouseNotifications( false );
        squareRangeKnob.SetPosition( 50, 230 );
        squareRangeKnob.SetSize( 35, 35 );
        squareRangeKnob.SetSkin( "Pointer Knob Black" );
        squareRangeKnob.SetRange( 1, 3, 2, false, 3 );
        squareRangeKnob.SetKnobParams( 320, 40 );
        squareRangeKnob.DisplayValueInPercent( false );
        squareRangeKnob.SetKnobAdjustsRing( true );

        sineOutput = new VoltageAudioJack( "sineOutput", "Sine Output", this, JackType.JackType_AudioOutput );
        AddComponent( sineOutput );
        sineOutput.SetWantsMouseNotifications( false );
        sineOutput.SetPosition( 185, 285 );
        sineOutput.SetSize( 25, 25 );
        sineOutput.SetSkin( "Mini Jack 25px" );

        sineRangeKnob = new VoltageKnob( "sineRangeKnob", "Sine Range", this, 1, 3, 2 );
        AddComponent( sineRangeKnob );
        sineRangeKnob.SetWantsMouseNotifications( false );
        sineRangeKnob.SetPosition( 140, 230 );
        sineRangeKnob.SetSize( 35, 35 );
        sineRangeKnob.SetSkin( "Pointer Knob Black" );
        sineRangeKnob.SetRange( 1, 3, 2, false, 3 );
        sineRangeKnob.SetKnobParams( 320, 40 );
        sineRangeKnob.DisplayValueInPercent( false );
        sineRangeKnob.SetKnobAdjustsRing( true );

        pulseWidthKnob = new VoltageKnob( "pulseWidthKnob", "Square Pulse Width", this, 0.0, 5.0, 2.5 );
        AddComponent( pulseWidthKnob );
        pulseWidthKnob.SetWantsMouseNotifications( false );
        pulseWidthKnob.SetPosition( 95, 280 );
        pulseWidthKnob.SetSize( 35, 35 );
        pulseWidthKnob.SetSkin( "TR Large (white tick)" );
        pulseWidthKnob.SetRange( 0.0, 5.0, 2.5, false, 0 );
        pulseWidthKnob.SetKnobParams( 215, 145 );
        pulseWidthKnob.DisplayValueInPercent( false );
        pulseWidthKnob.SetKnobAdjustsRing( true );

        frequencyMultiplierKnob = new VoltageKnob( "frequencyMultiplierKnob", "Frequency Multiplier", this, 1, 5, 2 );
        AddComponent( frequencyMultiplierKnob );
        frequencyMultiplierKnob.SetWantsMouseNotifications( false );
        frequencyMultiplierKnob.SetPosition( 95, 205 );
        frequencyMultiplierKnob.SetSize( 35, 35 );
        frequencyMultiplierKnob.SetSkin( "Pointer Knob Black" );
        frequencyMultiplierKnob.SetRange( 1, 5, 2, false, 5 );
        frequencyMultiplierKnob.SetKnobParams( 320, 40 );
        frequencyMultiplierKnob.DisplayValueInPercent( false );
        frequencyMultiplierKnob.SetKnobAdjustsRing( true );

        frequencyKnob = new VoltageKnob( "frequencyKnob", "Frequency", this, 0.0, 1.0, 0.4177 );
        AddComponent( frequencyKnob );
        frequencyKnob.SetWantsMouseNotifications( false );
        frequencyKnob.SetPosition( 65, 90 );
        frequencyKnob.SetSize( 100, 100 );
        frequencyKnob.SetSkin( "Cosmo v2 Large" );
        frequencyKnob.SetRange( 0.0, 1.0, 0.4177, false, 0 );
        frequencyKnob.SetKnobParams( 215, 145 );
        frequencyKnob.DisplayValueInPercent( false );
        frequencyKnob.SetKnobAdjustsRing( true );

        frequencyDisplay = new VoltageDigitalCounter( "frequencyDisplay", "Frequency Display", this, 6 );
        AddComponent( frequencyDisplay );
        frequencyDisplay.SetWantsMouseNotifications( false );
        frequencyDisplay.SetPosition( 65, 30 );
        frequencyDisplay.SetSize( 100, 50 );
        frequencyDisplay.SetSkin( "Gray" );
        frequencyDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        multiplierOneLabel = new VoltageLabel( "multiplierOneLabel", "X1 Range Label", this, "1" );
        AddComponent( multiplierOneLabel );
        multiplierOneLabel.SetWantsMouseNotifications( false );
        multiplierOneLabel.SetPosition( 80, 200 );
        multiplierOneLabel.SetSize( 16, 16 );
        multiplierOneLabel.SetEditable( false, false );
        multiplierOneLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        multiplierOneLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        multiplierOneLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        multiplierOneLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        multiplierOneLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        multiplierOneLabel.SetBorderSize( 1 );
        multiplierOneLabel.SetMultiLineEdit( false );
        multiplierOneLabel.SetIsNumberEditor( false );
        multiplierOneLabel.SetNumberEditorRange( 0, 100 );
        multiplierOneLabel.SetNumberEditorInterval( 1 );
        multiplierOneLabel.SetNumberEditorUsesMouseWheel( false );
        multiplierOneLabel.SetHasCustomTextHoverColor( false );
        multiplierOneLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        multiplierOneLabel.SetFont( "Arial Black", 8, true, false );

        squareTenthVoltLabel = new VoltageLabel( "squareTenthVoltLabel", "Square 0.1 Volt Label", this, ".1V" );
        AddComponent( squareTenthVoltLabel );
        squareTenthVoltLabel.SetWantsMouseNotifications( false );
        squareTenthVoltLabel.SetPosition( 43, 220 );
        squareTenthVoltLabel.SetSize( 16, 16 );
        squareTenthVoltLabel.SetEditable( false, false );
        squareTenthVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        squareTenthVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        squareTenthVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        squareTenthVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        squareTenthVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        squareTenthVoltLabel.SetBorderSize( 1 );
        squareTenthVoltLabel.SetMultiLineEdit( false );
        squareTenthVoltLabel.SetIsNumberEditor( false );
        squareTenthVoltLabel.SetNumberEditorRange( 0, 100 );
        squareTenthVoltLabel.SetNumberEditorInterval( 1 );
        squareTenthVoltLabel.SetNumberEditorUsesMouseWheel( false );
        squareTenthVoltLabel.SetHasCustomTextHoverColor( false );
        squareTenthVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        squareTenthVoltLabel.SetFont( "Arial Black", 8, true, false );

        sineTenVoltLabel = new VoltageLabel( "sineTenVoltLabel", "Sine 10 Volt Label", this, "10V" );
        AddComponent( sineTenVoltLabel );
        sineTenVoltLabel.SetWantsMouseNotifications( false );
        sineTenVoltLabel.SetPosition( 167, 220 );
        sineTenVoltLabel.SetSize( 16, 16 );
        sineTenVoltLabel.SetEditable( false, false );
        sineTenVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineTenVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineTenVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineTenVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sineTenVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineTenVoltLabel.SetBorderSize( 1 );
        sineTenVoltLabel.SetMultiLineEdit( false );
        sineTenVoltLabel.SetIsNumberEditor( false );
        sineTenVoltLabel.SetNumberEditorRange( 0, 100 );
        sineTenVoltLabel.SetNumberEditorInterval( 1 );
        sineTenVoltLabel.SetNumberEditorUsesMouseWheel( false );
        sineTenVoltLabel.SetHasCustomTextHoverColor( false );
        sineTenVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineTenVoltLabel.SetFont( "Arial Black", 8, true, false );

        sineTenthVoltLabel = new VoltageLabel( "sineTenthVoltLabel", "Sine 0.1 Volt Label", this, ".1V" );
        AddComponent( sineTenthVoltLabel );
        sineTenthVoltLabel.SetWantsMouseNotifications( false );
        sineTenthVoltLabel.SetPosition( 135, 220 );
        sineTenthVoltLabel.SetSize( 16, 16 );
        sineTenthVoltLabel.SetEditable( false, false );
        sineTenthVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineTenthVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineTenthVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineTenthVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sineTenthVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineTenthVoltLabel.SetBorderSize( 1 );
        sineTenthVoltLabel.SetMultiLineEdit( false );
        sineTenthVoltLabel.SetIsNumberEditor( false );
        sineTenthVoltLabel.SetNumberEditorRange( 0, 100 );
        sineTenthVoltLabel.SetNumberEditorInterval( 1 );
        sineTenthVoltLabel.SetNumberEditorUsesMouseWheel( false );
        sineTenthVoltLabel.SetHasCustomTextHoverColor( false );
        sineTenthVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineTenthVoltLabel.SetFont( "Arial Black", 8, true, false );

        squareTenVoltLabel = new VoltageLabel( "squareTenVoltLabel", "Square 10 Volt Label", this, "10V" );
        AddComponent( squareTenVoltLabel );
        squareTenVoltLabel.SetWantsMouseNotifications( false );
        squareTenVoltLabel.SetPosition( 75, 220 );
        squareTenVoltLabel.SetSize( 16, 16 );
        squareTenVoltLabel.SetEditable( false, false );
        squareTenVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        squareTenVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        squareTenVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        squareTenVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        squareTenVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        squareTenVoltLabel.SetBorderSize( 1 );
        squareTenVoltLabel.SetMultiLineEdit( false );
        squareTenVoltLabel.SetIsNumberEditor( false );
        squareTenVoltLabel.SetNumberEditorRange( 0, 100 );
        squareTenVoltLabel.SetNumberEditorInterval( 1 );
        squareTenVoltLabel.SetNumberEditorUsesMouseWheel( false );
        squareTenVoltLabel.SetHasCustomTextHoverColor( false );
        squareTenVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        squareTenVoltLabel.SetFont( "Arial Black", 8, true, false );

        squareOneVoltLabel = new VoltageLabel( "squareOneVoltLabel", "Square 1 Volt Label", this, "1V" );
        AddComponent( squareOneVoltLabel );
        squareOneVoltLabel.SetWantsMouseNotifications( false );
        squareOneVoltLabel.SetPosition( 59, 215 );
        squareOneVoltLabel.SetSize( 16, 16 );
        squareOneVoltLabel.SetEditable( false, false );
        squareOneVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        squareOneVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        squareOneVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        squareOneVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        squareOneVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        squareOneVoltLabel.SetBorderSize( 1 );
        squareOneVoltLabel.SetMultiLineEdit( false );
        squareOneVoltLabel.SetIsNumberEditor( false );
        squareOneVoltLabel.SetNumberEditorRange( 0, 100 );
        squareOneVoltLabel.SetNumberEditorInterval( 1 );
        squareOneVoltLabel.SetNumberEditorUsesMouseWheel( false );
        squareOneVoltLabel.SetHasCustomTextHoverColor( false );
        squareOneVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        squareOneVoltLabel.SetFont( "Arial Black", 8, true, false );

        sineOneVoltLabel = new VoltageLabel( "sineOneVoltLabel", "Sine 1 Volt Label", this, "1V" );
        AddComponent( sineOneVoltLabel );
        sineOneVoltLabel.SetWantsMouseNotifications( false );
        sineOneVoltLabel.SetPosition( 150, 215 );
        sineOneVoltLabel.SetSize( 16, 16 );
        sineOneVoltLabel.SetEditable( false, false );
        sineOneVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineOneVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineOneVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineOneVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sineOneVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineOneVoltLabel.SetBorderSize( 1 );
        sineOneVoltLabel.SetMultiLineEdit( false );
        sineOneVoltLabel.SetIsNumberEditor( false );
        sineOneVoltLabel.SetNumberEditorRange( 0, 100 );
        sineOneVoltLabel.SetNumberEditorInterval( 1 );
        sineOneVoltLabel.SetNumberEditorUsesMouseWheel( false );
        sineOneVoltLabel.SetHasCustomTextHoverColor( false );
        sineOneVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineOneVoltLabel.SetFont( "Arial Black", 8, true, false );

        multiplierTenLabel = new VoltageLabel( "multiplierTenLabel", "X10 Range Label", this, "10" );
        AddComponent( multiplierTenLabel );
        multiplierTenLabel.SetWantsMouseNotifications( false );
        multiplierTenLabel.SetPosition( 91, 191 );
        multiplierTenLabel.SetSize( 16, 16 );
        multiplierTenLabel.SetEditable( false, false );
        multiplierTenLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        multiplierTenLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        multiplierTenLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        multiplierTenLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        multiplierTenLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        multiplierTenLabel.SetBorderSize( 1 );
        multiplierTenLabel.SetMultiLineEdit( false );
        multiplierTenLabel.SetIsNumberEditor( false );
        multiplierTenLabel.SetNumberEditorRange( 0, 100 );
        multiplierTenLabel.SetNumberEditorInterval( 1 );
        multiplierTenLabel.SetNumberEditorUsesMouseWheel( false );
        multiplierTenLabel.SetHasCustomTextHoverColor( false );
        multiplierTenLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        multiplierTenLabel.SetFont( "Arial Black", 8, true, false );

        multiplierHundredLabel = new VoltageLabel( "multiplierHundredLabel", "X100 Range Label", this, "100" );
        AddComponent( multiplierHundredLabel );
        multiplierHundredLabel.SetWantsMouseNotifications( false );
        multiplierHundredLabel.SetPosition( 105, 187 );
        multiplierHundredLabel.SetSize( 16, 16 );
        multiplierHundredLabel.SetEditable( false, false );
        multiplierHundredLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        multiplierHundredLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        multiplierHundredLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        multiplierHundredLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        multiplierHundredLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        multiplierHundredLabel.SetBorderSize( 1 );
        multiplierHundredLabel.SetMultiLineEdit( false );
        multiplierHundredLabel.SetIsNumberEditor( false );
        multiplierHundredLabel.SetNumberEditorRange( 0, 100 );
        multiplierHundredLabel.SetNumberEditorInterval( 1 );
        multiplierHundredLabel.SetNumberEditorUsesMouseWheel( false );
        multiplierHundredLabel.SetHasCustomTextHoverColor( false );
        multiplierHundredLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        multiplierHundredLabel.SetFont( "Arial Black", 8, true, false );

        multiplierOneKilohertzLabel = new VoltageLabel( "multiplierOneKilohertzLabel", "X1K Range Label", this, "1K" );
        AddComponent( multiplierOneKilohertzLabel );
        multiplierOneKilohertzLabel.SetWantsMouseNotifications( false );
        multiplierOneKilohertzLabel.SetPosition( 119, 191 );
        multiplierOneKilohertzLabel.SetSize( 16, 16 );
        multiplierOneKilohertzLabel.SetEditable( false, false );
        multiplierOneKilohertzLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        multiplierOneKilohertzLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        multiplierOneKilohertzLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        multiplierOneKilohertzLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        multiplierOneKilohertzLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        multiplierOneKilohertzLabel.SetBorderSize( 1 );
        multiplierOneKilohertzLabel.SetMultiLineEdit( false );
        multiplierOneKilohertzLabel.SetIsNumberEditor( false );
        multiplierOneKilohertzLabel.SetNumberEditorRange( 0, 100 );
        multiplierOneKilohertzLabel.SetNumberEditorInterval( 1 );
        multiplierOneKilohertzLabel.SetNumberEditorUsesMouseWheel( false );
        multiplierOneKilohertzLabel.SetHasCustomTextHoverColor( false );
        multiplierOneKilohertzLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        multiplierOneKilohertzLabel.SetFont( "Arial Black", 8, true, false );

        multiplierTenKilohertzLabel = new VoltageLabel( "multiplierTenKilohertzLabel", "X10K Range Label", this, "10K" );
        AddComponent( multiplierTenKilohertzLabel );
        multiplierTenKilohertzLabel.SetWantsMouseNotifications( false );
        multiplierTenKilohertzLabel.SetPosition( 130, 200 );
        multiplierTenKilohertzLabel.SetSize( 16, 16 );
        multiplierTenKilohertzLabel.SetEditable( false, false );
        multiplierTenKilohertzLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        multiplierTenKilohertzLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        multiplierTenKilohertzLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        multiplierTenKilohertzLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        multiplierTenKilohertzLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        multiplierTenKilohertzLabel.SetBorderSize( 1 );
        multiplierTenKilohertzLabel.SetMultiLineEdit( false );
        multiplierTenKilohertzLabel.SetIsNumberEditor( false );
        multiplierTenKilohertzLabel.SetNumberEditorRange( 0, 100 );
        multiplierTenKilohertzLabel.SetNumberEditorInterval( 1 );
        multiplierTenKilohertzLabel.SetNumberEditorUsesMouseWheel( false );
        multiplierTenKilohertzLabel.SetHasCustomTextHoverColor( false );
        multiplierTenKilohertzLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        multiplierTenKilohertzLabel.SetFont( "Arial Black", 8, true, false );

        squareLabel = new VoltageLabel( "squareLabel", "Square Label", this, "SQR" );
        AddComponent( squareLabel );
        squareLabel.SetWantsMouseNotifications( false );
        squareLabel.SetPosition( 11, 310 );
        squareLabel.SetSize( 34, 23 );
        squareLabel.SetEditable( false, false );
        squareLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        squareLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        squareLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        squareLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        squareLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        squareLabel.SetBorderSize( 1 );
        squareLabel.SetMultiLineEdit( false );
        squareLabel.SetIsNumberEditor( false );
        squareLabel.SetNumberEditorRange( 0, 100 );
        squareLabel.SetNumberEditorInterval( 1 );
        squareLabel.SetNumberEditorUsesMouseWheel( false );
        squareLabel.SetHasCustomTextHoverColor( false );
        squareLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        squareLabel.SetFont( "Arial Black", 10, true, false );

        squareAmplitudeLabel = new VoltageLabel( "squareAmplitudeLabel", "Square Amplitude Label", this, "AMP" );
        AddComponent( squareAmplitudeLabel );
        squareAmplitudeLabel.SetWantsMouseNotifications( false );
        squareAmplitudeLabel.SetPosition( 51, 310 );
        squareAmplitudeLabel.SetSize( 34, 23 );
        squareAmplitudeLabel.SetEditable( false, false );
        squareAmplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        squareAmplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        squareAmplitudeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        squareAmplitudeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        squareAmplitudeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        squareAmplitudeLabel.SetBorderSize( 1 );
        squareAmplitudeLabel.SetMultiLineEdit( false );
        squareAmplitudeLabel.SetIsNumberEditor( false );
        squareAmplitudeLabel.SetNumberEditorRange( 0, 100 );
        squareAmplitudeLabel.SetNumberEditorInterval( 1 );
        squareAmplitudeLabel.SetNumberEditorUsesMouseWheel( false );
        squareAmplitudeLabel.SetHasCustomTextHoverColor( false );
        squareAmplitudeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        squareAmplitudeLabel.SetFont( "Arial Black", 10, true, false );

        sineLabel = new VoltageLabel( "sineLabel", "Sine Label", this, "SIN" );
        AddComponent( sineLabel );
        sineLabel.SetWantsMouseNotifications( false );
        sineLabel.SetPosition( 181, 310 );
        sineLabel.SetSize( 34, 23 );
        sineLabel.SetEditable( false, false );
        sineLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sineLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineLabel.SetBorderSize( 1 );
        sineLabel.SetMultiLineEdit( false );
        sineLabel.SetIsNumberEditor( false );
        sineLabel.SetNumberEditorRange( 0, 100 );
        sineLabel.SetNumberEditorInterval( 1 );
        sineLabel.SetNumberEditorUsesMouseWheel( false );
        sineLabel.SetHasCustomTextHoverColor( false );
        sineLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineLabel.SetFont( "Arial Black", 10, true, false );

        sineAmplitudeLabel = new VoltageLabel( "sineAmplitudeLabel", "Sine Amplitude Label", this, "AMP" );
        AddComponent( sineAmplitudeLabel );
        sineAmplitudeLabel.SetWantsMouseNotifications( false );
        sineAmplitudeLabel.SetPosition( 140, 310 );
        sineAmplitudeLabel.SetSize( 34, 23 );
        sineAmplitudeLabel.SetEditable( false, false );
        sineAmplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineAmplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineAmplitudeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineAmplitudeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sineAmplitudeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineAmplitudeLabel.SetBorderSize( 1 );
        sineAmplitudeLabel.SetMultiLineEdit( false );
        sineAmplitudeLabel.SetIsNumberEditor( false );
        sineAmplitudeLabel.SetNumberEditorRange( 0, 100 );
        sineAmplitudeLabel.SetNumberEditorInterval( 1 );
        sineAmplitudeLabel.SetNumberEditorUsesMouseWheel( false );
        sineAmplitudeLabel.SetHasCustomTextHoverColor( false );
        sineAmplitudeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineAmplitudeLabel.SetFont( "Arial Black", 10, true, false );

        sineRangeLabel = new VoltageLabel( "sineRangeLabel", "Sine Range Label", this, "RANGE" );
        AddComponent( sineRangeLabel );
        sineRangeLabel.SetWantsMouseNotifications( false );
        sineRangeLabel.SetPosition( 140, 260 );
        sineRangeLabel.SetSize( 34, 23 );
        sineRangeLabel.SetEditable( false, false );
        sineRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineRangeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineRangeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sineRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineRangeLabel.SetBorderSize( 1 );
        sineRangeLabel.SetMultiLineEdit( false );
        sineRangeLabel.SetIsNumberEditor( false );
        sineRangeLabel.SetNumberEditorRange( 0, 100 );
        sineRangeLabel.SetNumberEditorInterval( 1 );
        sineRangeLabel.SetNumberEditorUsesMouseWheel( false );
        sineRangeLabel.SetHasCustomTextHoverColor( false );
        sineRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineRangeLabel.SetFont( "Arial Black", 10, true, false );

        multiplierSymbolLabel = new VoltageLabel( "multiplierSymbolLabel", "Multiplier Symbol Label", this, "X" );
        AddComponent( multiplierSymbolLabel );
        multiplierSymbolLabel.SetWantsMouseNotifications( false );
        multiplierSymbolLabel.SetPosition( 95, 235 );
        multiplierSymbolLabel.SetSize( 34, 23 );
        multiplierSymbolLabel.SetEditable( false, false );
        multiplierSymbolLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        multiplierSymbolLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        multiplierSymbolLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        multiplierSymbolLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        multiplierSymbolLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        multiplierSymbolLabel.SetBorderSize( 1 );
        multiplierSymbolLabel.SetMultiLineEdit( false );
        multiplierSymbolLabel.SetIsNumberEditor( false );
        multiplierSymbolLabel.SetNumberEditorRange( 0, 100 );
        multiplierSymbolLabel.SetNumberEditorInterval( 1 );
        multiplierSymbolLabel.SetNumberEditorUsesMouseWheel( false );
        multiplierSymbolLabel.SetHasCustomTextHoverColor( false );
        multiplierSymbolLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        multiplierSymbolLabel.SetFont( "Arial Black", 10, true, false );

        pulseWidthLabel = new VoltageLabel( "pulseWidthLabel", "Pulse Width Label", this, "WIDTH" );
        AddComponent( pulseWidthLabel );
        pulseWidthLabel.SetWantsMouseNotifications( false );
        pulseWidthLabel.SetPosition( 95, 310 );
        pulseWidthLabel.SetSize( 34, 23 );
        pulseWidthLabel.SetEditable( false, false );
        pulseWidthLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        pulseWidthLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        pulseWidthLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        pulseWidthLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        pulseWidthLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        pulseWidthLabel.SetBorderSize( 1 );
        pulseWidthLabel.SetMultiLineEdit( false );
        pulseWidthLabel.SetIsNumberEditor( false );
        pulseWidthLabel.SetNumberEditorRange( 0, 100 );
        pulseWidthLabel.SetNumberEditorInterval( 1 );
        pulseWidthLabel.SetNumberEditorUsesMouseWheel( false );
        pulseWidthLabel.SetHasCustomTextHoverColor( false );
        pulseWidthLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        pulseWidthLabel.SetFont( "Arial Black", 10, true, false );

        squareRangeLabel = new VoltageLabel( "squareRangeLabel", "Square Range Label", this, "RANGE" );
        AddComponent( squareRangeLabel );
        squareRangeLabel.SetWantsMouseNotifications( false );
        squareRangeLabel.SetPosition( 50, 260 );
        squareRangeLabel.SetSize( 34, 23 );
        squareRangeLabel.SetEditable( false, false );
        squareRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        squareRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        squareRangeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        squareRangeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        squareRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        squareRangeLabel.SetBorderSize( 1 );
        squareRangeLabel.SetMultiLineEdit( false );
        squareRangeLabel.SetIsNumberEditor( false );
        squareRangeLabel.SetNumberEditorRange( 0, 100 );
        squareRangeLabel.SetNumberEditorInterval( 1 );
        squareRangeLabel.SetNumberEditorUsesMouseWheel( false );
        squareRangeLabel.SetHasCustomTextHoverColor( false );
        squareRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        squareRangeLabel.SetFont( "Arial Black", 10, true, false );
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
        resetFunctionGenerator();
        smoothedFrequencyHz = effectiveBaseFrequencyHz() * selectedMultiplier();
        frequencyDisplay.SetValue(displayFrequencyHz());
        powerIndicator.SetValue(0.0);
        squareOutput.SetValue(0.0);
        sineOutput.SetValue(0.0);
        StartGuiUpdateTimer();
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
        //[user-Destroy]   Add your own module-getting-deleted code here
        StopGuiUpdateTimer();
        //[/user-Destroy]
        super.Destroy();
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
            case GUI_Update_Timer:
                frequencyDisplay.SetValue(displayFrequencyHz());
                powerIndicator.SetValue(bypassed ? 0.0 : powerGain);
                break;
            case Reset:
                resetFunctionGenerator();
                resumePending = true;
                break;
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
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
        double baseHz = effectiveBaseFrequencyHz();
        bypassed = false;
        if (resumePending) {
            smoothedFrequencyHz = baseHz * selectedMultiplier();
            smoothedSquareAmplitude = selectedAmplitude(squareAmplitudeKnob, squareRangeKnob);
            smoothedSineAmplitude = selectedAmplitude(sineAmplitudeKnob, sineRangeKnob);
            smoothedDutyCycle = requestedDutyCycle();
            resumePending = false;
        }
        boolean powered = powerSwitch.GetValue() >= 0.5;
        updatePowerGain(powered);
        if (!powered && powerGain <= 0.0) {
            squareOutput.SetValue(0.0);
            sineOutput.SetValue(0.0);
            return;
        }

        smoothedFrequencyHz += FREQUENCY_SMOOTH
                * (baseHz * selectedMultiplier() - smoothedFrequencyHz);
        smoothedSquareAmplitude += AMPLITUDE_SMOOTH
                * (selectedAmplitude(squareAmplitudeKnob, squareRangeKnob) - smoothedSquareAmplitude);
        smoothedSineAmplitude += AMPLITUDE_SMOOTH
                * (selectedAmplitude(sineAmplitudeKnob, sineRangeKnob) - smoothedSineAmplitude);
        smoothedDutyCycle += DUTY_SMOOTH * (requestedDutyCycle() - smoothedDutyCycle);

        // Independent oscillator boards share tuning but never reset each other's phase.
        double squareFrequency = Math.min(MAXIMUM_FREQUENCY_HZ, smoothedFrequencyHz
                * (1.0 + 0.0015 * Math.sin(squareDriftPhase * TWO_PI)));
        double sineFrequency = Math.min(MAXIMUM_FREQUENCY_HZ, smoothedFrequencyHz
                * (1.0 - 0.0010 * Math.sin(sineDriftPhase * TWO_PI + 0.73)));
        squarePhase = advancePhase(squarePhase, squareFrequency);
        sinePhase = advancePhase(sinePhase, sineFrequency);
        squareDriftPhase = advancePhase(squareDriftPhase, 0.071);
        sineDriftPhase = advancePhase(sineDriftPhase, 0.043);

        double squareSignal = bandLimitedPulse(squarePhase, smoothedDutyCycle, squareFrequency)
                * smoothedSquareAmplitude;
        double sineSignal = voicedSine(sinePhase) * smoothedSineAmplitude;
        squareOutput.SetValue(outputStage(squareSignal) * powerGain);
        sineOutput.SetValue(outputStage(sineSignal) * powerGain);
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
        // Source bypass is immediate silence; oscillator and smoothing state stay frozen.
        squareOutput.SetValue(0.0);
        sineOutput.SetValue(0.0);
        bypassed = true;
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
        if (component == frequencyKnob) return "FREQUENCY: " + formatHz(displayFrequencyHz())
                + " nominal; enter output frequency in Hz";
        if (component == frequencyMultiplierKnob) return "MULTIPLIER: " + multiplierLabel() + "; X1, X10, X100, X1K, or X10K";
        if (component == pulseWidthKnob) return "SQUARE WIDTH: " + Math.round(requestedDutyCycle() * 100.0) + "%";
        if (component == squareAmplitudeKnob || component == sineAmplitudeKnob) {
            VoltageKnob amplitude = component == squareAmplitudeKnob ? squareAmplitudeKnob : sineAmplitudeKnob;
            VoltageKnob range = component == squareAmplitudeKnob ? squareRangeKnob : sineRangeKnob;
            return "AMPLITUDE: " + String.format(java.util.Locale.ROOT, "%.1f%%; %.3f V peak before output voicing",
                    amplitude.GetValue() * 100.0, selectedAmplitude(amplitude, range));
        }
        if (component == squareRangeKnob || component == sineRangeKnob) return "RANGE: "
                + rangeLabel(component == squareRangeKnob ? squareRangeKnob : sineRangeKnob) + "; enter position 1-3";
        if (component == powerSwitch) return "POWER: 8 ms fade; oscillators stop after fade-out";
        if (component == squareOutput) return "SQUARE: independent variable-width pulse oscillator";
        if (component == sineOutput) return "SINE: independent sine oscillator";
        if (component == powerIndicator) return "POWER: lamp follows the physical power transition";
        if (component == frequencyDisplay) return "FREQUENCY DISPLAY: calibrated output frequency; boards drift independently";
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
        if (component == frequencyKnob) {
            newValue = positionFromOutputHz(newValue);
        } else if (component == pulseWidthKnob) {
            newValue = (Math.max(5.0, Math.min(95.0, newValue)) - 5.0) / 18.0;
        } else if (component == squareAmplitudeKnob || component == sineAmplitudeKnob) {
            newValue = Math.max(0.0, Math.min(1.0, newValue / 100.0));
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
    private VoltageLabel squareRangeLabel;
    private VoltageLabel pulseWidthLabel;
    private VoltageLabel multiplierSymbolLabel;
    private VoltageLabel sineRangeLabel;
    private VoltageLabel sineAmplitudeLabel;
    private VoltageLabel sineLabel;
    private VoltageLabel squareAmplitudeLabel;
    private VoltageLabel squareLabel;
    private VoltageLabel multiplierTenKilohertzLabel;
    private VoltageLabel multiplierOneKilohertzLabel;
    private VoltageLabel multiplierHundredLabel;
    private VoltageLabel multiplierTenLabel;
    private VoltageLabel sineOneVoltLabel;
    private VoltageLabel squareOneVoltLabel;
    private VoltageLabel squareTenVoltLabel;
    private VoltageLabel sineTenthVoltLabel;
    private VoltageLabel sineTenVoltLabel;
    private VoltageLabel squareTenthVoltLabel;
    private VoltageLabel multiplierOneLabel;
    private VoltageDigitalCounter frequencyDisplay;
    private VoltageKnob frequencyKnob;
    private VoltageKnob frequencyMultiplierKnob;
    private VoltageKnob pulseWidthKnob;
    private VoltageKnob sineRangeKnob;
    private VoltageAudioJack sineOutput;
    private VoltageKnob squareRangeKnob;
    private VoltageKnob squareAmplitudeKnob;
    private VoltageAudioJack squareOutput;
    private VoltageLED powerIndicator;
    private VoltageSwitch powerSwitch;
    private VoltageLabel titleLabel;
    private VoltageLabel brandLabel;
    private VoltageKnob sineAmplitudeKnob;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double TWO_PI = Math.PI * 2.0;
    private static final double C4_HZ = 261.625565;
    private static final double DEFAULT_FREQUENCY_POSITION = 0.4177;
    private static final double DEFAULT_BASE_HZ = C4_HZ / 10.0;
    private static final double MINIMUM_BASE_HZ = 0.1;
    private static final double MAXIMUM_BASE_HZ = 100.0;
    private static final double MAXIMUM_FREQUENCY_HZ = 23760.0;
    private static final double POWER_FADE_STEP = 1.0 / (SAMPLE_RATE * 0.008);
    private static final double FREQUENCY_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010));
    private static final double AMPLITUDE_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.008));
    private static final double DUTY_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.006));
    private double squarePhase;
    private double sinePhase = 0.347;
    private double squareDriftPhase = 0.137;
    private double sineDriftPhase = 0.691;
    private volatile double powerGain;
    private volatile boolean bypassed;
    private volatile boolean resumePending;
    private double smoothedFrequencyHz = C4_HZ;
    private double smoothedSquareAmplitude;
    private double smoothedSineAmplitude;
    private double smoothedDutyCycle = 0.5;
    private double cachedFrequencyPosition = Double.NaN;
    private double cachedMultiplier = Double.NaN;
    private double cachedBaseHz = DEFAULT_BASE_HZ;

    private void resetFunctionGenerator() {
        squarePhase = 0.0;
        sinePhase = 0.347;
        squareDriftPhase = 0.137;
        sineDriftPhase = 0.691;
        powerGain = 0.0;
        smoothedFrequencyHz = C4_HZ;
        smoothedSquareAmplitude = 0.0;
        smoothedSineAmplitude = 0.0;
        smoothedDutyCycle = 0.5;
        cachedFrequencyPosition = Double.NaN;
        cachedMultiplier = Double.NaN;
        cachedBaseHz = DEFAULT_BASE_HZ;
        bypassed = false;
        resumePending = false;
    }

    private void updatePowerGain(boolean powered) {
        powerGain = powered ? Math.min(1.0, powerGain + POWER_FADE_STEP) : Math.max(0.0, powerGain - POWER_FADE_STEP);
    }

    private double selectedMultiplier() {
        switch ((int) Math.round(frequencyMultiplierKnob.GetValue())) {
            case 1: return 1.0; case 2: return 10.0; case 3: return 100.0; case 4: return 1000.0; default: return 10000.0;
        }
    }

    private String multiplierLabel() {
        double value = selectedMultiplier();
        return value >= 1000.0 ? "X" + (int) (value / 1000.0) + "K" : "X" + (int) value;
    }

    // The custom taper keeps the existing panel's 0.4177 default exactly at C4 on X10.
    private double requestedBaseFrequencyHz() {
        double position = Math.max(0.0, Math.min(1.0, frequencyKnob.GetValue()));
        double multiplier = selectedMultiplier();
        if (position == cachedFrequencyPosition && multiplier == cachedMultiplier) return cachedBaseHz;
        cachedFrequencyPosition = position;
        cachedMultiplier = multiplier;
        cachedBaseHz = baseAtPosition(position * usableDialTravel(multiplier));
        return cachedBaseHz;
    }

    // Preserve the accepted low-range taper; expand the usable part of capped ranges to a full turn.
    private double usableDialTravel(double multiplier) {
        return positionFromBase(Math.min(MAXIMUM_BASE_HZ, MAXIMUM_FREQUENCY_HZ / multiplier));
    }

    private double baseAtPosition(double position) {
        if (position <= DEFAULT_FREQUENCY_POSITION) {
            return MINIMUM_BASE_HZ * Math.pow(DEFAULT_BASE_HZ / MINIMUM_BASE_HZ,
                    position / DEFAULT_FREQUENCY_POSITION);
        }
        return DEFAULT_BASE_HZ * Math.pow(MAXIMUM_BASE_HZ / DEFAULT_BASE_HZ,
                (position - DEFAULT_FREQUENCY_POSITION) / (1.0 - DEFAULT_FREQUENCY_POSITION));
    }

    private double positionFromBase(double hz) {
        return hz <= DEFAULT_BASE_HZ
                ? DEFAULT_FREQUENCY_POSITION * Math.log(hz / MINIMUM_BASE_HZ)
                    / Math.log(DEFAULT_BASE_HZ / MINIMUM_BASE_HZ)
                : DEFAULT_FREQUENCY_POSITION + (1.0 - DEFAULT_FREQUENCY_POSITION)
                    * Math.log(hz / DEFAULT_BASE_HZ) / Math.log(MAXIMUM_BASE_HZ / DEFAULT_BASE_HZ);
    }

    private double positionFromOutputHz(double hz) {
        double multiplier = selectedMultiplier();
        double base = Math.max(MINIMUM_BASE_HZ,
                Math.min(Math.min(MAXIMUM_BASE_HZ, MAXIMUM_FREQUENCY_HZ / multiplier), hz / multiplier));
        return Math.max(0.0, Math.min(1.0, positionFromBase(base) / usableDialTravel(multiplier)));
    }

    private double effectiveBaseFrequencyHz() {
        return Math.min(requestedBaseFrequencyHz(), MAXIMUM_FREQUENCY_HZ / selectedMultiplier());
    }

    private double requestedDutyCycle() {
        return 0.05 + 0.90 * (pulseWidthKnob.GetValue() / 5.0);
    }

    private double selectedAmplitude(VoltageKnob amplitudeKnob, VoltageKnob rangeKnob) {
        return amplitudeKnob.GetValue() * selectedRangeVolts(rangeKnob);
    }

    private double selectedRangeVolts(VoltageKnob rangeKnob) {
        switch ((int) Math.round(rangeKnob.GetValue())) {
            case 1:
                return 0.1;
            case 2:
                return 1.0;
            default:
                return 10.0;
        }
    }

    private String rangeLabel(VoltageKnob rangeKnob) {
        double volts = selectedRangeVolts(rangeKnob);
        return volts < 1.0 ? "0.1 V" : (int) volts + " V";
    }
    private double advancePhase(double phase, double frequency) { phase += Math.max(0.0, Math.min(MAXIMUM_FREQUENCY_HZ, frequency)) / SAMPLE_RATE; return phase >= 1.0 ? phase - Math.floor(phase) : phase; }
    private double polyBlep(double phase, double increment) {
        if (phase < increment) { double t = phase / increment; return t + t - t * t - 1.0; }
        if (phase > 1.0 - increment) { double t = (phase - 1.0) / increment; return t * t + t + t + 1.0; }
        return 0.0;
    }
    private double bandLimitedPulse(double phase, double duty, double frequency) {
        double increment = Math.min(0.495, frequency / SAMPLE_RATE);
        double signal = phase < duty ? 1.0 : -1.0;
        signal += polyBlep(phase, increment);
        double fallingPhase = phase - duty; if (fallingPhase < 0.0) fallingPhase += 1.0;
        signal -= polyBlep(fallingPhase, increment);
        return signal;
    }
    private double voicedSine(double phase) {
        double angle = phase * TWO_PI;
        return Math.sin(angle) + 0.018 * Math.sin(angle * 2.0 + 0.21) + 0.006 * Math.sin(angle * 3.0);
    }
    private double outputStage(double signal) {
        double magnitude = Math.abs(signal);
        if (magnitude <= 5.0) return signal;
        double sign = signal < 0.0 ? -1.0 : 1.0;
        return sign * (5.0 + 4.6 * Math.tanh((magnitude - 5.0) / 4.6));
    }
    private double displayFrequencyHz() {
        double multiplier = selectedMultiplier();
        double position = Math.max(0.0, Math.min(1.0, frequencyKnob.GetValue()));
        return Math.min(MAXIMUM_FREQUENCY_HZ,
                baseAtPosition(position * usableDialTravel(multiplier)) * multiplier);
    }
    private String formatHz(double hertz) { return hertz >= 1000.0 ? String.format(java.util.Locale.ROOT, "%.3f kHz", hertz / 1000.0) : String.format(java.util.Locale.ROOT, "%.3f Hz", hertz); }
    //[/user-code-and-variables]
}

 