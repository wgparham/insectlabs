package com.insectlabs.type9414;


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


public class type9414 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public type9414( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "type 9414 - Frequency Analyzer", ModuleType.ModuleType_Filters, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "64e506f75ad3485a872fa21cbde3f95c" );
    }

void InitializeControls()
{

        frequencyKnob = new VoltageKnob( "frequencyKnob", "Frequency", this, 0.0, 1.0, 0.5750949689835924 );
        AddComponent( frequencyKnob );
        frequencyKnob.SetWantsMouseNotifications( false );
        frequencyKnob.SetPosition( 17, 45 );
        frequencyKnob.SetSize( 80, 80 );
        frequencyKnob.SetSkin( "TR Large (white tick)" );
        frequencyKnob.SetRange( 0.0, 1.0, 0.5750949689835924, false, 0 );
        frequencyKnob.SetKnobParams( 215, 145 );
        frequencyKnob.DisplayValueInPercent( false );
        frequencyKnob.SetKnobAdjustsRing( true );

        fineKnob = new VoltageKnob( "fineKnob", "Fine Tune", this, -2.0, 2.0, 0.0 );
        AddComponent( fineKnob );
        fineKnob.SetWantsMouseNotifications( false );
        fineKnob.SetPosition( 37, 142 );
        fineKnob.SetSize( 40, 40 );
        fineKnob.SetSkin( "TR Large (white tick)" );
        fineKnob.SetRange( -2.0, 2.0, 0.0, false, 0 );
        fineKnob.SetKnobParams( 215, 145 );
        fineKnob.DisplayValueInPercent( false );
        fineKnob.SetKnobAdjustsRing( true );

        widthSlider = new VoltageSlider( "widthSlider", "Bandwidth", this, true, 0.0, 1.0, 0.0, 0 );
        AddComponent( widthSlider );
        widthSlider.SetWantsMouseNotifications( false );
        widthSlider.SetPosition( 10, 187 );
        widthSlider.SetSize( 15, 115 );
        widthSlider.SetSkin( "Juno Ext Tan" );
        widthSlider.DisplayValueInPercent( false );

        slopeSlider = new VoltageSlider( "slopeSlider", "Filter Slope", this, true, 1.0, 12.0, 4.0, 12 );
        AddComponent( slopeSlider );
        slopeSlider.SetWantsMouseNotifications( false );
        slopeSlider.SetPosition( 40, 187 );
        slopeSlider.SetSize( 15, 115 );
        slopeSlider.SetSkin( "Juno Ext Black" );
        slopeSlider.DisplayValueInPercent( false );

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Label", this, "freq. analyzer" );
        AddComponent( manufacturerLabel );
        manufacturerLabel.SetWantsMouseNotifications( false );
        manufacturerLabel.SetPosition( 3, 3 );
        manufacturerLabel.SetSize( 109, 23 );
        manufacturerLabel.SetEditable( false, false );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        manufacturerLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manufacturerLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        manufacturerLabel.SetBorderSize( 4 );
        manufacturerLabel.SetMultiLineEdit( false );
        manufacturerLabel.SetIsNumberEditor( false );
        manufacturerLabel.SetNumberEditorRange( 0, 100 );
        manufacturerLabel.SetNumberEditorInterval( 1 );
        manufacturerLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerLabel.SetHasCustomTextHoverColor( false );
        manufacturerLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerLabel.SetFont( "Courier New", 13, true, false );

        signalInput = new VoltageAudioJack( "signalInput", "Signal Input", this, JackType.JackType_AudioInput );
        AddComponent( signalInput );
        signalInput.SetWantsMouseNotifications( false );
        signalInput.SetPosition( 60, 237 );
        signalInput.SetSize( 25, 25 );
        signalInput.SetSkin( "Dark Jack Straight" );

        pitchInput = new VoltageAudioJack( "pitchInput", "V/Oct Input", this, JackType.JackType_AudioInput );
        AddComponent( pitchInput );
        pitchInput.SetWantsMouseNotifications( false );
        pitchInput.SetPosition( 60, 197 );
        pitchInput.SetSize( 25, 25 );
        pitchInput.SetSkin( "Dark Jack Straight" );

        fmInput = new VoltageAudioJack( "fmInput", "Linear FM Input", this, JackType.JackType_AudioInput );
        AddComponent( fmInput );
        fmInput.SetWantsMouseNotifications( false );
        fmInput.SetPosition( 85, 197 );
        fmInput.SetSize( 25, 25 );
        fmInput.SetSkin( "Dark Jack Straight" );

        mainOutput = new VoltageAudioJack( "mainOutput", "Bandpass Output", this, JackType.JackType_AudioOutput );
        AddComponent( mainOutput );
        mainOutput.SetWantsMouseNotifications( false );
        mainOutput.SetPosition( 85, 237 );
        mainOutput.SetSize( 25, 25 );
        mainOutput.SetSkin( "Rotated Half" );

        powerSwitch = new VoltageSwitch( "powerSwitch", "Power", this, 0 );
        AddComponent( powerSwitch );
        powerSwitch.SetWantsMouseNotifications( false );
        powerSwitch.SetPosition( 12, 315 );
        powerSwitch.SetSize( 40, 15 );
        powerSwitch.SetSkin( "Rocker Switch Plastic Orange Hor" );

        amplitudeKnob = new VoltageKnob( "amplitudeKnob", "Output Amplitude", this, -1.0, 1.0, 0.0 );
        AddComponent( amplitudeKnob );
        amplitudeKnob.SetWantsMouseNotifications( false );
        amplitudeKnob.SetPosition( 86, 277 );
        amplitudeKnob.SetSize( 23, 23 );
        amplitudeKnob.SetSkin( "TR Large" );
        amplitudeKnob.SetRange( -1.0, 1.0, 0.0, false, 0 );
        amplitudeKnob.SetKnobParams( 215, 145 );
        amplitudeKnob.DisplayValueInPercent( false );
        amplitudeKnob.SetKnobAdjustsRing( true );

        gainKnob = new VoltageKnob( "gainKnob", "Input Gain", this, -24.0, 24.0, 0.0 );
        AddComponent( gainKnob );
        gainKnob.SetWantsMouseNotifications( false );
        gainKnob.SetPosition( 61, 277 );
        gainKnob.SetSize( 23, 23 );
        gainKnob.SetSkin( "TR Large" );
        gainKnob.SetRange( -24.0, 24.0, 0.0, false, 0 );
        gainKnob.SetKnobParams( 215, 145 );
        gainKnob.DisplayValueInPercent( false );
        gainKnob.SetKnobAdjustsRing( true );

        pitchLabel = new VoltageLabel( "pitchLabel", "Pitch Label", this, "V/Oct" );
        AddComponent( pitchLabel );
        pitchLabel.SetWantsMouseNotifications( false );
        pitchLabel.SetPosition( 62, 222 );
        pitchLabel.SetSize( 20, 10 );
        pitchLabel.SetEditable( false, false );
        pitchLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        pitchLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        pitchLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        pitchLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        pitchLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        pitchLabel.SetBorderSize( 1 );
        pitchLabel.SetMultiLineEdit( false );
        pitchLabel.SetIsNumberEditor( false );
        pitchLabel.SetNumberEditorRange( 0, 100 );
        pitchLabel.SetNumberEditorInterval( 1 );
        pitchLabel.SetNumberEditorUsesMouseWheel( false );
        pitchLabel.SetHasCustomTextHoverColor( false );
        pitchLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        pitchLabel.SetFont( "Arial Black", 10, true, false );

        fmLabel = new VoltageLabel( "fmLabel", "Fm Label", this, "FM" );
        AddComponent( fmLabel );
        fmLabel.SetWantsMouseNotifications( false );
        fmLabel.SetPosition( 87, 222 );
        fmLabel.SetSize( 20, 10 );
        fmLabel.SetEditable( false, false );
        fmLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fmLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fmLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        fmLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fmLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fmLabel.SetBorderSize( 1 );
        fmLabel.SetMultiLineEdit( false );
        fmLabel.SetIsNumberEditor( false );
        fmLabel.SetNumberEditorRange( 0, 100 );
        fmLabel.SetNumberEditorInterval( 1 );
        fmLabel.SetNumberEditorUsesMouseWheel( false );
        fmLabel.SetHasCustomTextHoverColor( false );
        fmLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fmLabel.SetFont( "Arial Black", 10, true, false );

        signalLabel = new VoltageLabel( "signalLabel", "Signal Label", this, "S" );
        AddComponent( signalLabel );
        signalLabel.SetWantsMouseNotifications( false );
        signalLabel.SetPosition( 62, 262 );
        signalLabel.SetSize( 20, 10 );
        signalLabel.SetEditable( false, false );
        signalLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        signalLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        signalLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        signalLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        signalLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        signalLabel.SetBorderSize( 1 );
        signalLabel.SetMultiLineEdit( false );
        signalLabel.SetIsNumberEditor( false );
        signalLabel.SetNumberEditorRange( 0, 100 );
        signalLabel.SetNumberEditorInterval( 1 );
        signalLabel.SetNumberEditorUsesMouseWheel( false );
        signalLabel.SetHasCustomTextHoverColor( false );
        signalLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        signalLabel.SetFont( "Arial Black", 10, true, false );

        outputLabel = new VoltageLabel( "outputLabel", "Output Label", this, "OUT" );
        AddComponent( outputLabel );
        outputLabel.SetWantsMouseNotifications( false );
        outputLabel.SetPosition( 87, 262 );
        outputLabel.SetSize( 20, 10 );
        outputLabel.SetEditable( false, false );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        outputLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        outputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        outputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        outputLabel.SetBorderSize( 1 );
        outputLabel.SetMultiLineEdit( false );
        outputLabel.SetIsNumberEditor( false );
        outputLabel.SetNumberEditorRange( 0, 100 );
        outputLabel.SetNumberEditorInterval( 1 );
        outputLabel.SetNumberEditorUsesMouseWheel( false );
        outputLabel.SetHasCustomTextHoverColor( false );
        outputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        outputLabel.SetFont( "Arial Black", 10, true, false );

        courtesyLabel = new VoltageLabel( "courtesyLabel", "Courtesy Label", this, "C" );
        AddComponent( courtesyLabel );
        courtesyLabel.SetWantsMouseNotifications( false );
        courtesyLabel.SetPosition( 94, 318 );
        courtesyLabel.SetSize( 20, 10 );
        courtesyLabel.SetEditable( false, false );
        courtesyLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        courtesyLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        courtesyLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        courtesyLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        courtesyLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        courtesyLabel.SetBorderSize( 1 );
        courtesyLabel.SetMultiLineEdit( false );
        courtesyLabel.SetIsNumberEditor( false );
        courtesyLabel.SetNumberEditorRange( 0, 100 );
        courtesyLabel.SetNumberEditorInterval( 1 );
        courtesyLabel.SetNumberEditorUsesMouseWheel( false );
        courtesyLabel.SetHasCustomTextHoverColor( false );
        courtesyLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        courtesyLabel.SetFont( "Arial Black", 10, true, false );

        amplitudeLabel = new VoltageLabel( "amplitudeLabel", "Amplitude Label", this, "AMP" );
        AddComponent( amplitudeLabel );
        amplitudeLabel.SetWantsMouseNotifications( false );
        amplitudeLabel.SetPosition( 87, 302 );
        amplitudeLabel.SetSize( 20, 10 );
        amplitudeLabel.SetEditable( false, false );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        amplitudeLabel.SetColor( new Color( 85, 85, 0, 255 ) );
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
        amplitudeLabel.SetFont( "Arial Black", 10, true, false );

        gainLabel = new VoltageLabel( "gainLabel", "Gain Label", this, "GAIN" );
        AddComponent( gainLabel );
        gainLabel.SetWantsMouseNotifications( false );
        gainLabel.SetPosition( 62, 302 );
        gainLabel.SetSize( 20, 10 );
        gainLabel.SetEditable( false, false );
        gainLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        gainLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        gainLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        gainLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        gainLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        gainLabel.SetBorderSize( 1 );
        gainLabel.SetMultiLineEdit( false );
        gainLabel.SetIsNumberEditor( false );
        gainLabel.SetNumberEditorRange( 0, 100 );
        gainLabel.SetNumberEditorInterval( 1 );
        gainLabel.SetNumberEditorUsesMouseWheel( false );
        gainLabel.SetHasCustomTextHoverColor( false );
        gainLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        gainLabel.SetFont( "Arial Black", 10, true, false );

        widthLabel = new VoltageLabel( "widthLabel", "Width Label", this, "WIDTH" );
        AddComponent( widthLabel );
        widthLabel.SetWantsMouseNotifications( false );
        widthLabel.SetPosition( 7, 302 );
        widthLabel.SetSize( 20, 10 );
        widthLabel.SetEditable( false, false );
        widthLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        widthLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        widthLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        widthLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        widthLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        widthLabel.SetBorderSize( 1 );
        widthLabel.SetMultiLineEdit( false );
        widthLabel.SetIsNumberEditor( false );
        widthLabel.SetNumberEditorRange( 0, 100 );
        widthLabel.SetNumberEditorInterval( 1 );
        widthLabel.SetNumberEditorUsesMouseWheel( false );
        widthLabel.SetHasCustomTextHoverColor( false );
        widthLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        widthLabel.SetFont( "Arial Black", 10, true, false );

        slopeLabel = new VoltageLabel( "slopeLabel", "Slope Label", this, "SLOPE" );
        AddComponent( slopeLabel );
        slopeLabel.SetWantsMouseNotifications( false );
        slopeLabel.SetPosition( 37, 302 );
        slopeLabel.SetSize( 20, 10 );
        slopeLabel.SetEditable( false, false );
        slopeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        slopeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        slopeLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        slopeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        slopeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        slopeLabel.SetBorderSize( 1 );
        slopeLabel.SetMultiLineEdit( false );
        slopeLabel.SetIsNumberEditor( false );
        slopeLabel.SetNumberEditorRange( 0, 100 );
        slopeLabel.SetNumberEditorInterval( 1 );
        slopeLabel.SetNumberEditorUsesMouseWheel( false );
        slopeLabel.SetHasCustomTextHoverColor( false );
        slopeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        slopeLabel.SetFont( "Arial Black", 10, true, false );

        frequencyLabel = new VoltageLabel( "frequencyLabel", "Frequency Label", this, "FREQUENCY" );
        AddComponent( frequencyLabel );
        frequencyLabel.SetWantsMouseNotifications( false );
        frequencyLabel.SetPosition( 0, 124 );
        frequencyLabel.SetSize( 115, 15 );
        frequencyLabel.SetEditable( false, false );
        frequencyLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        frequencyLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        frequencyLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        frequencyLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        frequencyLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        frequencyLabel.SetBorderSize( 1 );
        frequencyLabel.SetMultiLineEdit( false );
        frequencyLabel.SetIsNumberEditor( false );
        frequencyLabel.SetNumberEditorRange( 0, 100 );
        frequencyLabel.SetNumberEditorInterval( 1 );
        frequencyLabel.SetNumberEditorUsesMouseWheel( false );
        frequencyLabel.SetHasCustomTextHoverColor( false );
        frequencyLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        frequencyLabel.SetFont( "Arial Black", 13, true, false );

        fineLabel = new VoltageLabel( "fineLabel", "Fine Label", this, "FINE" );
        AddComponent( fineLabel );
        fineLabel.SetWantsMouseNotifications( false );
        fineLabel.SetPosition( 0, 180 );
        fineLabel.SetSize( 115, 15 );
        fineLabel.SetEditable( false, false );
        fineLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        fineLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fineLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineLabel.SetBorderSize( 1 );
        fineLabel.SetMultiLineEdit( false );
        fineLabel.SetIsNumberEditor( false );
        fineLabel.SetNumberEditorRange( 0, 100 );
        fineLabel.SetNumberEditorInterval( 1 );
        fineLabel.SetNumberEditorUsesMouseWheel( false );
        fineLabel.SetHasCustomTextHoverColor( false );
        fineLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineLabel.SetFont( "Arial Black", 11, true, false );

        indicatorsLabel = new VoltageLabel( "indicatorsLabel", "Indicators Label", this, "SIG OL" );
        AddComponent( indicatorsLabel );
        indicatorsLabel.SetWantsMouseNotifications( false );
        indicatorsLabel.SetPosition( 2, 38 );
        indicatorsLabel.SetSize( 36, 15 );
        indicatorsLabel.SetEditable( false, false );
        indicatorsLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        indicatorsLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        indicatorsLabel.SetColor( new Color( 85, 85, 0, 255 ) );
        indicatorsLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        indicatorsLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        indicatorsLabel.SetBorderSize( 1 );
        indicatorsLabel.SetMultiLineEdit( false );
        indicatorsLabel.SetIsNumberEditor( false );
        indicatorsLabel.SetNumberEditorRange( 0, 100 );
        indicatorsLabel.SetNumberEditorInterval( 1 );
        indicatorsLabel.SetNumberEditorUsesMouseWheel( false );
        indicatorsLabel.SetHasCustomTextHoverColor( false );
        indicatorsLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        indicatorsLabel.SetFont( "Arial Black", 11, true, false );

        signalLed = new VoltageLED( "signalLed", "Signal Indicator", this );
        AddComponent( signalLed );
        signalLed.SetWantsMouseNotifications( false );
        signalLed.SetPosition( 8, 30 );
        signalLed.SetSize( 10, 10 );
        signalLed.SetSkin( "2500 Lamp Green" );

        overloadLed = new VoltageLED( "overloadLed", "Overload Indicator", this );
        AddComponent( overloadLed );
        overloadLed.SetWantsMouseNotifications( false );
        overloadLed.SetPosition( 23, 30 );
        overloadLed.SetSize( 10, 10 );
        overloadLed.SetSkin( "2500 Lamp Red" );

        manufacturerMarkLabel = new VoltageLabel( "manufacturerMarkLabel", "Manufacturer Label_1", this, "r." );
        AddComponent( manufacturerMarkLabel );
        manufacturerMarkLabel.SetWantsMouseNotifications( false );
        manufacturerMarkLabel.SetPosition( 10, 335 );
        manufacturerMarkLabel.SetSize( 20, 20 );
        manufacturerMarkLabel.SetEditable( false, false );
        manufacturerMarkLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerMarkLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerMarkLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        manufacturerMarkLabel.SetBkColor( new Color( 51, 51, 51, 255 ) );
        manufacturerMarkLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        manufacturerMarkLabel.SetBorderSize( 2 );
        manufacturerMarkLabel.SetMultiLineEdit( false );
        manufacturerMarkLabel.SetIsNumberEditor( false );
        manufacturerMarkLabel.SetNumberEditorRange( 0, 100 );
        manufacturerMarkLabel.SetNumberEditorInterval( 1 );
        manufacturerMarkLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerMarkLabel.SetHasCustomTextHoverColor( false );
        manufacturerMarkLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerMarkLabel.SetFont( "Courier New", 13, true, false );

        colophon = new VoltageLabel( "colophon", "Colophon", this, "type 9414 frequency analyzer  refuge kits                            oklahoma city, ok                             –– 1958 ––" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 25, 335 );
        colophon.SetSize( 80, 23 );
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
        colophon.SetFont( "Arial", 5, true, false );

        courtesyOutput = new VoltageAudioJack( "courtesyOutput", "Band-Reject Output", this, JackType.JackType_AudioOutput );
        AddComponent( courtesyOutput );
        courtesyOutput.SetWantsMouseNotifications( false );
        courtesyOutput.SetPosition( 73, 313 );
        courtesyOutput.SetSize( 22, 22 );
        courtesyOutput.SetSkin( "Mini Jack 25px" );
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
        selectiveCore = new SelectiveCore(SAMPLE_RATE);
        resumePending = true;
        bypassed = false;
        signalLed.SetValue(0.0);
        overloadLed.SetValue(0.0);
        StartGuiUpdateTimer(25);
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
        StopGuiUpdateTimer();
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
            case GUI_Update_Timer:
                updateIndicators();
                break;
            case Reset:
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                resetPending = true;
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
        if (selectiveCore == null) {
            return;
        }
        if (resetPending) {
            selectiveCore.reset();
            resumePending = true;
            resetPending = false;
        }
        bypassed = false;
        selectiveCore.process(readInput(signalInput), frequencyKnob.GetValue(), fineKnob.GetValue(),
                widthSlider.GetValue(), (int) Math.round(slopeSlider.GetValue()),
                gainKnob.GetValue(), amplitudeDb(amplitudeKnob.GetValue()),
                readInput(pitchInput), readInput(fmInput), powerSwitch.GetValue() >= 0.5, resumePending);
        resumePending = false;
        mainOutput.SetValue(selectiveCore.main);
        courtesyOutput.SetValue(selectiveCore.courtesy);
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
        // Exact dry splitter. No controls/CV reads and no DSP-history advancement.
        double dry = readInput(signalInput);
        mainOutput.SetValue(dry);
        courtesyOutput.SetValue(dry);
        resumePending = true;
        bypassed = true;
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
        if (component == frequencyKnob) {
            return "Frequency: " + shown(frequencyHz(frequencyKnob.GetValue())) + " Hz";
        }
        if (component == fineKnob) {
            return "Fine Tune: " + shown(fineKnob.GetValue()) + " Hz";
        }
        if (component == widthSlider) {
            return "Bandwidth: " + shown(widthHz(widthSlider.GetValue())) + " Hz (-3 dB width)";
        }
        if (component == slopeSlider) {
            return "Filter Slope: " + Math.round(slopeSlider.GetValue()) + " poles per skirt";
        }
        if (component == gainKnob) {
            return "Input Gain: " + shown(gainKnob.GetValue()) + " dB";
        }
        if (component == amplitudeKnob) {
            return "Output Amplitude: " + shown(amplitudeDb(amplitudeKnob.GetValue())) + " dB (MAIN only)";
        }
        if (component == powerSwitch) {
    return powerSwitch.GetValue() >= 0.5
            ? "Power: ON; 3.9 s warmup"
            : "Power: OFF; 2.3 s cooldown; C settles to passive S-through";
}
        if (component == pitchInput) {
            return "V/Oct: 1 V per octave relative to FREQUENCY";
        }
        if (component == fmInput) {
            return "Linear FM: 100 Hz/V; adds to V/Oct and FINE";
        }
        if (component == signalInput) {
            return "S: mono signal input";
        }
        if (component == mainOutput) {
            return "MAIN: bandpass through AMP; +/-24 V ceiling";
        }
        if (component == courtesyOutput) {
            return "C: matched band-reject; skips AMP; +/-20 V ceiling";
        }
        if (component == signalLed) {
            return "SIG: MAIN output level";
        }
        if (component == overloadLed) {
            return "OL: MAIN soft ceiling active";
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
        if (!Double.isFinite(newValue)) {
            return;
        }
        if (component == frequencyKnob) {
            frequencyKnob.SetValue(Math.log(clamp(newValue, 20.0, 18000.0) / 20.0) / Math.log(900.0));
            return;
        }
        if (component == widthSlider) {
            widthSlider.SetValue(Math.log(clamp(newValue, 2.0, 100.0) / 2.0) / Math.log(50.0));
            return;
        }
        if (component == amplitudeKnob) {
            double db = clamp(newValue, MIN_AMP_DB, MAX_AMP_DB);
            amplitudeKnob.SetValue(db < 0.0 ? db / -MIN_AMP_DB : db / MAX_AMP_DB);
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
    private VoltageAudioJack courtesyOutput;
    private VoltageLabel colophon;
    private VoltageLabel manufacturerMarkLabel;
    private VoltageLED overloadLed;
    private VoltageLED signalLed;
    private VoltageLabel indicatorsLabel;
    private VoltageLabel fineLabel;
    private VoltageLabel frequencyLabel;
    private VoltageLabel slopeLabel;
    private VoltageLabel widthLabel;
    private VoltageLabel gainLabel;
    private VoltageLabel amplitudeLabel;
    private VoltageLabel courtesyLabel;
    private VoltageLabel outputLabel;
    private VoltageLabel signalLabel;
    private VoltageLabel fmLabel;
    private VoltageLabel pitchLabel;
    private VoltageKnob gainKnob;
    private VoltageKnob amplitudeKnob;
    private VoltageSwitch powerSwitch;
    private VoltageAudioJack mainOutput;
    private VoltageAudioJack fmInput;
    private VoltageAudioJack pitchInput;
    private VoltageAudioJack signalInput;
    private VoltageLabel manufacturerLabel;
    private VoltageSlider slopeSlider;
    private VoltageSlider widthSlider;
    private VoltageKnob fineKnob;
    private VoltageKnob frequencyKnob;


    //[user-code-and-variables]    Add your own variables and functions here
    // Selective Service v1.0.0: canonical release, 48 kHz VM engine.
    private static final double SAMPLE_RATE = 48000.0;
    private static final double MIN_GAIN_DB = -24.0;
    private static final double MAX_GAIN_DB = 24.0;
    private static final double MIN_AMP_DB = -24.0;
    private static final double MAX_AMP_DB = 36.0;
    private static final double MAIN_KNEE_VOLTS = 20.0;
    private static final double MAIN_CEILING_VOLTS = 24.0;
    private static final double COURTESY_KNEE_VOLTS = 16.0;
    private static final double COURTESY_CEILING_VOLTS = 20.0;
    private SelectiveCore selectiveCore;
    private boolean resumePending = true;
    private volatile boolean resetPending;
    private volatile boolean bypassed;

    private static double readInput(VoltageAudioJack jack) {
    if (!jack.IsConnected()) {
        return 0.0;
    }

    double value = jack.GetValue();

    if (!Double.isFinite(value)) {
        return 0.0;
    }

    return Math.max(
            -1.0e6,
            Math.min(1.0e6, value)
    );
}

    private static double clamp(double x, double lo, double hi) {
        return Math.max(lo, Math.min(hi, Double.isFinite(x) ? x : lo));
    }

    private static double amplitudeDb(double position) {
        return position < 0.0 ? -MIN_AMP_DB * position : MAX_AMP_DB * position;
    }

    private static double frequencyHz(double position) {
        return 20.0 * Math.pow(900.0, clamp(position, 0.0, 1.0));
    }

    private static double widthHz(double position) {
        return 2.0 * Math.pow(50.0, clamp(position, 0.0, 1.0));
    }

    private static String shown(double value) {
        return String.format(java.util.Locale.ROOT, "%.2f", value);
    }

    private void updateIndicators() {
        if (selectiveCore == null || bypassed) {
            signalLed.SetValue(0.0);
            overloadLed.SetValue(0.0);
        } else {
            signalLed.SetValue(Math.pow(Math.min(1.0, selectiveCore.signalEnvelope / MAIN_KNEE_VOLTS), 0.6));
            overloadLed.SetValue(Math.min(1.0, selectiveCore.overloadEnvelope));
        }
    }

    private static final class SelectiveCore {
        private final FilterBank[] banks = {new FilterBank(), new FilterBank()};
        private final double smoothing;
        private final double meterRelease;
        private final int slopeFadeSamples;
        private final double powerWarmupStep;
        private final double powerCooldownStep;
        private int activeBank;
        private int slopeFadeRemaining;
        private double previousFrequencyControl = Double.NaN;
        private double previousWidthControl = Double.NaN;
        private double previousGainDb = Double.NaN;
        private double previousAmpDb = Double.NaN;
        private double previousPitch = Double.NaN;
        private double pitchMultiplier = 1.0;
        private double targetFrequency = 1000.0;
        private double targetWidth = 2.0;
        private double targetGain = 1.0;
        private double targetAmp = 1.0;
        private double smoothFrequency = 1000.0;
        private double smoothWidth = 2.0;
        private double smoothFine;
        private double smoothGain = 1.0;
        private double smoothAmp = 1.0;
        private double powerMix;
        private double effectiveFrequency = 1000.0;
        private double main;
        private double courtesy;
        private volatile double signalEnvelope;
        private volatile double overloadEnvelope;

        private SelectiveCore(double sampleRate) {
            if (sampleRate != SAMPLE_RATE) {
                throw new IllegalArgumentException("VM engine requires 48 kHz");
            }
            smoothing = 1.0 - Math.exp(-1.0 / (sampleRate * 0.005));
            meterRelease = Math.exp(-1.0 / (sampleRate * 0.080));
            slopeFadeSamples =
        (int) Math.round(
                sampleRate * 0.010
        );

powerWarmupStep =
        1.0 / (sampleRate * 3.9);

powerCooldownStep =
        1.0 / (sampleRate * 2.3);
            banks[0].setOrder(4);
            banks[1].setOrder(4);
        }

        private void reset() {
            banks[0].clear();
            banks[1].clear();
            slopeFadeRemaining = 0;
powerMix = 0.0;
signalEnvelope = overloadEnvelope = 0.0;
        }

        private double smooth(double current, double target) {
            return Math.abs(target - current) < 1e-10 ? target : current + smoothing * (target - current);
        }

        private void process(double input, double frequencyControl, double fine,
                double widthControl, int order, double gainDb, double ampDb,
                double pitch, double fm, boolean powered, boolean snap) {
            // Expensive control mappings run only when their source control changes.
            frequencyControl = clamp(frequencyControl, 0.0, 1.0);
            widthControl = clamp(widthControl, 0.0, 1.0);
            gainDb = clamp(gainDb, MIN_GAIN_DB, MAX_GAIN_DB);
            ampDb = clamp(ampDb, MIN_AMP_DB, MAX_AMP_DB);
            fine = clamp(fine, -2.0, 2.0);
            order = Math.max(1, Math.min(12, order));
            if (frequencyControl != previousFrequencyControl) {
                targetFrequency = frequencyHz(frequencyControl);
                previousFrequencyControl = frequencyControl;
            }
            if (widthControl != previousWidthControl) {
                targetWidth = widthHz(widthControl);
                previousWidthControl = widthControl;
            }
            if (gainDb != previousGainDb) {
                targetGain = Math.pow(10.0, gainDb / 20.0);
                previousGainDb = gainDb;
            }
            if (ampDb != previousAmpDb) {
                targetAmp = Math.pow(10.0, ampDb / 20.0);
                previousAmpDb = ampDb;
            }
            if (snap) {
                smoothFrequency = targetFrequency;
                smoothWidth = targetWidth;
                smoothFine = fine;
                smoothGain = targetGain;
                smoothAmp = targetAmp;
                if (banks[activeBank].order != order) {
                    banks[activeBank].setOrder(order);
                    banks[activeBank].clear();
                }
                slopeFadeRemaining = 0;
            }
            smoothFrequency = smooth(smoothFrequency, targetFrequency);
            smoothWidth = smooth(smoothWidth, targetWidth);
            smoothFine = smooth(smoothFine, fine);
            smoothGain = smooth(smoothGain, targetGain);
            smoothAmp = smooth(smoothAmp, targetAmp);
            if (powered) {
    powerMix = Math.min(
            1.0,
            powerMix + powerWarmupStep
    );
} else {
    powerMix = Math.max(
            0.0,
            powerMix - powerCooldownStep
    );
}
            // Prevent pathological upstream numeric values from poisoning filter state.
            input = Double.isFinite(input) ? Math.max(-1e6, Math.min(1e6, input)) : 0.0;
            if (powerMix <= 1e-12 && !powered) {
                powerMix = 0.0;
                main = 0.0;
                courtesy = ceiling(input, COURTESY_KNEE_VOLTS, COURTESY_CEILING_VOLTS);
                signalEnvelope = overloadEnvelope = 0.0;
                banks[activeBank].clearIfNeeded();
                slopeFadeRemaining = 0;
                return;
            }
            // CV is intentionally unsmoothed and read at the full sample rate.
            pitch = Double.isFinite(pitch) ? clamp(pitch, -32.0, 32.0) : 0.0;
            if (pitch != previousPitch) {
                pitchMultiplier = Math.pow(2.0, pitch);
                previousPitch = pitch;
            }
            fm = Double.isFinite(fm) ? fm : 0.0;
            effectiveFrequency = clamp(smoothFrequency * pitchMultiplier + smoothFine + 100.0 * fm, 20.0, 18000.0);
            if (banks[activeBank].order != order && slopeFadeRemaining == 0) {
                activeBank = 1 - activeBank;
                banks[activeBank].setOrder(order);
                banks[activeBank].clear();
                slopeFadeRemaining = slopeFadeSamples;
            }
            double gained = input * smoothGain;
            FilterBank current = banks[activeBank];
            current.process(gained, effectiveFrequency, smoothWidth);
            double bandpassSignal = current.bandpass;
            double bandRejectSignal = current.bandreject;
            if (slopeFadeRemaining > 0) {
                FilterBank old = banks[1 - activeBank];
                old.process(gained, effectiveFrequency, smoothWidth);
                double mix =
        1.0
        - (double) slopeFadeRemaining
        / slopeFadeSamples;
                bandpassSignal = old.bandpass + mix * (bandpassSignal - old.bandpass);
                bandRejectSignal = old.bandreject + mix * (bandRejectSignal - old.bandreject);
                slopeFadeRemaining--;
            }
            double amplified = bandpassSignal * smoothAmp;
            main = powerMix * ceiling(amplified, MAIN_KNEE_VOLTS, MAIN_CEILING_VOLTS);
            // OFF is a passive S-through-C path. The ceiling remains; GAIN does not.
            courtesy = ceiling(input + powerMix * (bandRejectSignal - input), COURTESY_KNEE_VOLTS, COURTESY_CEILING_VOLTS);
            signalEnvelope = Math.max(Math.abs(main), signalEnvelope * meterRelease);
            double limiting = Math.abs(amplified) > MAIN_KNEE_VOLTS ? powerMix : 0.0;
            overloadEnvelope = Math.max(limiting, overloadEnvelope * meterRelease);
        }

        private static double ceiling(double value, double knee, double limit) {
            double magnitude = Math.abs(value);
            if (magnitude <= knee) {
                return value;
            }
            return Math.copySign(knee + (limit - knee) * Math.tanh((magnitude - knee) / (limit - knee)), value);
        }
    }

    /** Butterworth prototype transformed to matched BP/BR filters.
     * Order specifies poles per skirt; the band transform has 2*order poles.
     * Both paths use the same stable TPT state-variable sections and separate states.
     * The exact digital center is tan(pi*f/fs); digital bandwidth is preserved by
     * B = tan(pi*width/fs)*(1 + center*center). No audio-thread allocation.
     */
    private static final class FilterBank {
        private final Section[] sections = new Section[12];
        private final double[] prototypeReal = new double[6];
        private final double[] prototypeImag = new double[6];
        private int order;
        private double lastFrequency = Double.NaN;
        private double lastWidth = Double.NaN;
        private double widthTangent;
        private double bandpass;
        private double bandreject;
        private boolean cleared = true;

        private FilterBank() {
            for (int i = 0; i < sections.length; i++) {
                sections[i] = new Section();
            }
        }

        private void setOrder(int requested) {
            if (order == requested) {
                return;
            }
            order = requested;
            for (int i = 0; i < order / 2; i++) {
                double angle = Math.PI * (2.0 * (order / 2 - 1 - i) + 1.0) / (2.0 * order);
                prototypeReal[i] = -Math.sin(angle);
                prototypeImag[i] = Math.cos(angle);
            }
            lastFrequency = Double.NaN;
        }

        private void clear() {
            for (Section section : sections) {
                section.clear();
            }
            bandpass = bandreject = 0.0;
            cleared = true;
        }

        private void clearIfNeeded() {
            if (!cleared) {
                clear();
            }
        }

        private void configure(double frequency, double width) {
            if (frequency == lastFrequency && width == lastWidth) {
                return;
            }
            if (width != lastWidth) {
                widthTangent = Math.tan(Math.PI * width / SAMPLE_RATE);
            }
            double center = Math.tan(Math.PI * frequency / SAMPLE_RATE);
            double centerSquared = center * center;
            double bandwidth = widthTangent * (1.0 + centerSquared);
            int sectionIndex = 0;
            if ((order & 1) != 0) {
                sections[sectionIndex++].configure(center, bandwidth / center, bandwidth / center, 1.0);
            }
            for (int i = 0; i < order / 2; i++) {
                double prototypePoleReal = 0.5 * bandwidth * prototypeReal[i];
                double prototypePoleImaginary = 0.5 * bandwidth * prototypeImag[i];
                double discriminantReal = prototypePoleReal * prototypePoleReal - prototypePoleImaginary * prototypePoleImaginary - centerSquared;
                double discriminantImaginary = 2.0 * prototypePoleReal * prototypePoleImaginary;
                double magnitude = Math.hypot(discriminantReal, discriminantImaginary);
                double squareRootImaginary = -Math.sqrt(0.5 * (magnitude - discriminantReal));
                double squareRootReal = discriminantImaginary / (2.0 * squareRootImaginary);
                for (int sign = -1; sign <= 1; sign += 2) {
                    double rootReal = prototypePoleReal + sign * squareRootReal;
                    double rootImag = prototypePoleImaginary + sign * squareRootImaginary;
                    double naturalFrequency = Math.hypot(rootReal, rootImag);
                    sections[sectionIndex++].configure(naturalFrequency, -2.0 * rootReal / naturalFrequency,
                            bandwidth / naturalFrequency, centerSquared / (naturalFrequency * naturalFrequency));
                }
            }
            lastFrequency = frequency;
            lastWidth = width;
        }

        private void process(double input, double frequency, double width) {
            configure(frequency, width);
            double bandpassSignal = input;
            double bandRejectSignal = input;
            for (int i = 0; i < order; i++) {
                Section section = sections[i];
                bandpassSignal = section.processBandpass(bandpassSignal);
                bandRejectSignal = section.processBandreject(bandRejectSignal);
            }
            bandpass = bandpassSignal;
            bandreject = bandRejectSignal;
            cleared = false;
        }
    }

    private static final class Section {
        private double a1, a2, a3, damping, bpScale, lpScale;
        private double bpState1, bpState2, brState1, brState2;

        private void configure(double integratorGain, double dampingCoefficient, double bpGain, double lowGain) {
            damping = dampingCoefficient;
            a1 = 1.0 / (1.0 + integratorGain * (integratorGain + dampingCoefficient));
            a2 = integratorGain * a1;
            a3 = integratorGain * a2;
            bpScale = bpGain;
            lpScale = lowGain;
        }

        private double processBandpass(double input) {
            double v3 = input - bpState2;
            double band = a1 * bpState1 + a2 * v3;
            double low = bpState2 + a2 * bpState1 + a3 * v3;
            bpState1 = 2.0 * band - bpState1;
            bpState2 = 2.0 * low - bpState2;
            return bpScale * band;
        }

        private double processBandreject(double input) {
            double v3 = input - brState2;
            double band = a1 * brState1 + a2 * v3;
            double low = brState2 + a2 * brState1 + a3 * v3;
            brState1 = 2.0 * band - brState1;
            brState2 = 2.0 * low - brState2;
            return input - damping * band + (lpScale - 1.0) * low;
        }

        private void clear() {
            bpState1 = bpState2 = brState1 = brState2 = 0.0;
        }
    }
    //[/user-code-and-variables]
}

 