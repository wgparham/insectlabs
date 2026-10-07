package com.insectlabs.nineteenfortyseven;


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


public class nineteenfortyseven extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public nineteenfortyseven( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "1947B - SIN/RND Generator – Filter", ModuleType.ModuleType_Oscillators, 6.4 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "13514d8c7b744817a558437b18515a86" );
    }

void InitializeControls()
{

        brandLabel = new VoltageLabel( "brandLabel", "Model Name", this, "SIN/RND generator – filter" );
        AddComponent( brandLabel );
        brandLabel.SetWantsMouseNotifications( false );
        brandLabel.SetPosition( 3, 3 );
        brandLabel.SetSize( 454, 23 );
        brandLabel.SetEditable( false, false );
        brandLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        brandLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        brandLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        brandLabel.SetBkColor( new Color( 51, 51, 51, 147 ) );
        brandLabel.SetBorderColor( new Color( 51, 51, 0, 255 ) );
        brandLabel.SetBorderSize( 4 );
        brandLabel.SetMultiLineEdit( false );
        brandLabel.SetIsNumberEditor( false );
        brandLabel.SetNumberEditorRange( 0, 100 );
        brandLabel.SetNumberEditorInterval( 1 );
        brandLabel.SetNumberEditorUsesMouseWheel( false );
        brandLabel.SetHasCustomTextHoverColor( false );
        brandLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        brandLabel.SetFont( "Courier New", 13, true, false );

        numberLabel = new VoltageLabel( "numberLabel", "Model Number Label", this, "1947B" );
        AddComponent( numberLabel );
        numberLabel.SetWantsMouseNotifications( false );
        numberLabel.SetPosition( 33, 335 );
        numberLabel.SetSize( 424, 13 );
        numberLabel.SetEditable( false, false );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        numberLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        numberLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        numberLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        numberLabel.SetBorderSize( 1 );
        numberLabel.SetMultiLineEdit( false );
        numberLabel.SetIsNumberEditor( false );
        numberLabel.SetNumberEditorRange( 0, 100 );
        numberLabel.SetNumberEditorInterval( 1 );
        numberLabel.SetNumberEditorUsesMouseWheel( false );
        numberLabel.SetHasCustomTextHoverColor( false );
        numberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        numberLabel.SetFont( "Courier New", 13, true, false );

        outputMeter = new VoltageAnalogVUMeter( "outputMeter", "Output Level", this );
        AddComponent( outputMeter );
        outputMeter.SetWantsMouseNotifications( false );
        outputMeter.SetPosition( 170, 30 );
        outputMeter.SetSize( 120, 60 );
        outputMeter.SetSkin( "Analog Black" );

        powerSwitch = new VoltageSwitch( "powerSwitch", "Power", this, 0 );
        AddComponent( powerSwitch );
        powerSwitch.SetWantsMouseNotifications( false );
        powerSwitch.SetPosition( 23, 305 );
        powerSwitch.SetSize( 35, 15 );
        powerSwitch.SetSkin( "Rocker Switch Plastic Orange Hor" );

        mainOutput = new VoltageAudioJack( "mainOutput", "Main Output", this, JackType.JackType_AudioOutput );
        AddComponent( mainOutput );
        mainOutput.SetWantsMouseNotifications( false );
        mainOutput.SetPosition( 382, 240 );
        mainOutput.SetSize( 37, 37 );
        mainOutput.SetSkin( "Rotated Half" );

        amplitudeKnob = new VoltageKnob( "amplitudeKnob", "Amplitude", this, 0.0, 1.0, 0.0 );
        AddComponent( amplitudeKnob );
        amplitudeKnob.SetWantsMouseNotifications( false );
        amplitudeKnob.SetPosition( 315, 240 );
        amplitudeKnob.SetSize( 50, 50 );
        amplitudeKnob.SetSkin( "Cosmo v2 Large" );
        amplitudeKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        amplitudeKnob.SetKnobParams( 215, 145 );
        amplitudeKnob.DisplayValueInPercent( true );
        amplitudeKnob.SetKnobAdjustsRing( true );

        amplitudeRangeKnob = new VoltageKnob( "amplitudeRangeKnob", "Amplitude Range", this, 1, 4, 3 );
        AddComponent( amplitudeRangeKnob );
        amplitudeRangeKnob.SetWantsMouseNotifications( false );
        amplitudeRangeKnob.SetPosition( 263, 240 );
        amplitudeRangeKnob.SetSize( 35, 35 );
        amplitudeRangeKnob.SetSkin( "Cosmo v2 Pointer" );
        amplitudeRangeKnob.SetRange( 1, 4, 3, false, 4 );
        amplitudeRangeKnob.SetKnobParams( 320, 40 );
        amplitudeRangeKnob.DisplayValueInPercent( false );
        amplitudeRangeKnob.SetKnobAdjustsRing( true );

        externalFmInput = new VoltageAudioJack( "externalFmInput", "External FM Input", this, JackType.JackType_AudioInput );
        AddComponent( externalFmInput );
        externalFmInput.SetWantsMouseNotifications( false );
        externalFmInput.SetPosition( 382, 180 );
        externalFmInput.SetSize( 37, 37 );
        externalFmInput.SetSkin( "Dark Jack Straight" );

        externalFmLevelKnob = new VoltageKnob( "externalFmLevelKnob", "MOD Level", this, -2.0, 2.0, 0.0 );
        AddComponent( externalFmLevelKnob );
        externalFmLevelKnob.SetWantsMouseNotifications( false );
        externalFmLevelKnob.SetPosition( 323, 180 );
        externalFmLevelKnob.SetSize( 35, 35 );
        externalFmLevelKnob.SetSkin( "Cosmo v2 Med" );
        externalFmLevelKnob.SetRange( -2.0, 2.0, 0.0, false, 0 );
        externalFmLevelKnob.SetKnobParams( 215, 145 );
        externalFmLevelKnob.DisplayValueInPercent( false );
        externalFmLevelKnob.SetKnobAdjustsRing( true );

        frequencyKnob = new VoltageKnob( "frequencyKnob", "Frequency", this, 0.0, 1.0, 0.281152240467025 );
        AddComponent( frequencyKnob );
        frequencyKnob.SetWantsMouseNotifications( false );
        frequencyKnob.SetPosition( 170, 110 );
        frequencyKnob.SetSize( 120, 120 );
        frequencyKnob.SetSkin( "Cosmo v2 Large" );
        frequencyKnob.SetRange( 0.0, 1.0, 0.281152240467025, false, 0 );
        frequencyKnob.SetKnobParams( 215, 145 );
        frequencyKnob.DisplayValueInPercent( false );
        frequencyKnob.SetKnobAdjustsRing( true );

        fineFrequencyKnob = new VoltageKnob( "fineFrequencyKnob", "Fine Frequency", this, -1.0, 1.0, 0.0 );
        AddComponent( fineFrequencyKnob );
        fineFrequencyKnob.SetWantsMouseNotifications( false );
        fineFrequencyKnob.SetPosition( 150, 190 );
        fineFrequencyKnob.SetSize( 20, 20 );
        fineFrequencyKnob.SetSkin( "Cosmo Small" );
        fineFrequencyKnob.SetRange( -1.0, 1.0, 0.0, false, 0 );
        fineFrequencyKnob.SetKnobParams( 215, 145 );
        fineFrequencyKnob.DisplayValueInPercent( false );
        fineFrequencyKnob.SetKnobAdjustsRing( true );

        randomModulationKnob = new VoltageKnob( "randomModulationKnob", "Random Modulation", this, 0.0, 1.0, 0.0 );
        AddComponent( randomModulationKnob );
        randomModulationKnob.SetWantsMouseNotifications( false );
        randomModulationKnob.SetPosition( 75, 240 );
        randomModulationKnob.SetSize( 50, 50 );
        randomModulationKnob.SetSkin( "Cosmo v2 Large" );
        randomModulationKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        randomModulationKnob.SetKnobParams( 215, 145 );
        randomModulationKnob.DisplayValueInPercent( true );
        randomModulationKnob.SetKnobAdjustsRing( true );

        modeKnob = new VoltageKnob( "modeKnob", "Mode", this, 1, 5, 1 );
        AddComponent( modeKnob );
        modeKnob.SetWantsMouseNotifications( false );
        modeKnob.SetPosition( 23, 240 );
        modeKnob.SetSize( 35, 35 );
        modeKnob.SetSkin( "Cosmo v2 Pointer" );
        modeKnob.SetRange( 1, 5, 1, false, 5 );
        modeKnob.SetKnobParams( 320, 40 );
        modeKnob.DisplayValueInPercent( false );
        modeKnob.SetKnobAdjustsRing( true );

        filterKnob = new VoltageKnob( "filterKnob", "Noise Filter", this, 0.0, 1.0, 0.5 );
        AddComponent( filterKnob );
        filterKnob.SetWantsMouseNotifications( false );
        filterKnob.SetPosition( 143, 240 );
        filterKnob.SetSize( 35, 35 );
        filterKnob.SetSkin( "Cosmo v2 Med" );
        filterKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        filterKnob.SetKnobParams( 215, 145 );
        filterKnob.DisplayValueInPercent( false );
        filterKnob.SetKnobAdjustsRing( true );

        powerLed = new VoltageLED( "powerLed", "Power Indicator", this );
        AddComponent( powerLed );
        powerLed.SetWantsMouseNotifications( false );
        powerLed.SetPosition( 402, 45 );
        powerLed.SetSize( 15, 15 );
        powerLed.SetSkin( "2500 Lamp Red" );

        randomModulationLabel = new VoltageLabel( "randomModulationLabel", "Random Modulation Label", this, "RND MOD" );
        AddComponent( randomModulationLabel );
        randomModulationLabel.SetWantsMouseNotifications( false );
        randomModulationLabel.SetPosition( 69, 220 );
        randomModulationLabel.SetSize( 60, 23 );
        randomModulationLabel.SetEditable( false, false );
        randomModulationLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        randomModulationLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        randomModulationLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        randomModulationLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        randomModulationLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        randomModulationLabel.SetBorderSize( 1 );
        randomModulationLabel.SetMultiLineEdit( false );
        randomModulationLabel.SetIsNumberEditor( false );
        randomModulationLabel.SetNumberEditorRange( 0, 100 );
        randomModulationLabel.SetNumberEditorInterval( 1 );
        randomModulationLabel.SetNumberEditorUsesMouseWheel( false );
        randomModulationLabel.SetHasCustomTextHoverColor( false );
        randomModulationLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        randomModulationLabel.SetFont( "Arial", 10, true, false );

        filterLabel = new VoltageLabel( "filterLabel", "Filter Label", this, "FILTER" );
        AddComponent( filterLabel );
        filterLabel.SetWantsMouseNotifications( false );
        filterLabel.SetPosition( 130, 220 );
        filterLabel.SetSize( 60, 23 );
        filterLabel.SetEditable( false, false );
        filterLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        filterLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        filterLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        filterLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        filterLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        filterLabel.SetBorderSize( 1 );
        filterLabel.SetMultiLineEdit( false );
        filterLabel.SetIsNumberEditor( false );
        filterLabel.SetNumberEditorRange( 0, 100 );
        filterLabel.SetNumberEditorInterval( 1 );
        filterLabel.SetNumberEditorUsesMouseWheel( false );
        filterLabel.SetHasCustomTextHoverColor( false );
        filterLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        filterLabel.SetFont( "Arial", 10, true, false );

        sineModeLabel = new VoltageLabel( "sineModeLabel", "Sine Mode Label", this, "SIN" );
        AddComponent( sineModeLabel );
        sineModeLabel.SetWantsMouseNotifications( false );
        sineModeLabel.SetPosition( 5, 235 );
        sineModeLabel.SetSize( 16, 8 );
        sineModeLabel.SetEditable( false, false );
        sineModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sineModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sineModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sineModeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        sineModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sineModeLabel.SetBorderSize( 1 );
        sineModeLabel.SetMultiLineEdit( false );
        sineModeLabel.SetIsNumberEditor( false );
        sineModeLabel.SetNumberEditorRange( 0, 100 );
        sineModeLabel.SetNumberEditorInterval( 1 );
        sineModeLabel.SetNumberEditorUsesMouseWheel( false );
        sineModeLabel.SetHasCustomTextHoverColor( false );
        sineModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sineModeLabel.SetFont( "Arial", 6, true, false );

        noiseModeLabel = new VoltageLabel( "noiseModeLabel", "Noise Mode Label", this, "RND" );
        AddComponent( noiseModeLabel );
        noiseModeLabel.SetWantsMouseNotifications( false );
        noiseModeLabel.SetPosition( 17, 225 );
        noiseModeLabel.SetSize( 16, 8 );
        noiseModeLabel.SetEditable( false, false );
        noiseModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        noiseModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        noiseModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        noiseModeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        noiseModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        noiseModeLabel.SetBorderSize( 1 );
        noiseModeLabel.SetMultiLineEdit( false );
        noiseModeLabel.SetIsNumberEditor( false );
        noiseModeLabel.SetNumberEditorRange( 0, 100 );
        noiseModeLabel.SetNumberEditorInterval( 1 );
        noiseModeLabel.SetNumberEditorUsesMouseWheel( false );
        noiseModeLabel.SetHasCustomTextHoverColor( false );
        noiseModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        noiseModeLabel.SetFont( "Arial", 6, true, false );

        modulationModeLabel = new VoltageLabel( "modulationModeLabel", "Modulation Mode Label", this, "MOD" );
        AddComponent( modulationModeLabel );
        modulationModeLabel.SetWantsMouseNotifications( false );
        modulationModeLabel.SetPosition( 48, 225 );
        modulationModeLabel.SetSize( 16, 8 );
        modulationModeLabel.SetEditable( false, false );
        modulationModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulationModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulationModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modulationModeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        modulationModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modulationModeLabel.SetBorderSize( 1 );
        modulationModeLabel.SetMultiLineEdit( false );
        modulationModeLabel.SetIsNumberEditor( false );
        modulationModeLabel.SetNumberEditorRange( 0, 100 );
        modulationModeLabel.SetNumberEditorInterval( 1 );
        modulationModeLabel.SetNumberEditorUsesMouseWheel( false );
        modulationModeLabel.SetHasCustomTextHoverColor( false );
        modulationModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulationModeLabel.SetFont( "Arial", 6, true, false );

        rd2ModeLabel = new VoltageLabel( "rd2ModeLabel", "RD2 Mode", this, "RD2" );
        AddComponent( rd2ModeLabel );
        rd2ModeLabel.SetWantsMouseNotifications( false );
        rd2ModeLabel.SetPosition( 33, 220 );
        rd2ModeLabel.SetSize( 16, 8 );
        rd2ModeLabel.SetEditable( false, false );
        rd2ModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        rd2ModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        rd2ModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        rd2ModeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        rd2ModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        rd2ModeLabel.SetBorderSize( 1 );
        rd2ModeLabel.SetMultiLineEdit( false );
        rd2ModeLabel.SetIsNumberEditor( false );
        rd2ModeLabel.SetNumberEditorRange( 0, 100 );
        rd2ModeLabel.SetNumberEditorInterval( 1 );
        rd2ModeLabel.SetNumberEditorUsesMouseWheel( false );
        rd2ModeLabel.SetHasCustomTextHoverColor( false );
        rd2ModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        rd2ModeLabel.SetFont( "Arial", 6, true, false );

        filteredModeLabel = new VoltageLabel( "filteredModeLabel", "Filtered Mode Label", this, "FLT" );
        AddComponent( filteredModeLabel );
        filteredModeLabel.SetWantsMouseNotifications( false );
        filteredModeLabel.SetPosition( 60, 235 );
        filteredModeLabel.SetSize( 16, 8 );
        filteredModeLabel.SetEditable( false, false );
        filteredModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        filteredModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        filteredModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        filteredModeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        filteredModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        filteredModeLabel.SetBorderSize( 1 );
        filteredModeLabel.SetMultiLineEdit( false );
        filteredModeLabel.SetIsNumberEditor( false );
        filteredModeLabel.SetNumberEditorRange( 0, 100 );
        filteredModeLabel.SetNumberEditorInterval( 1 );
        filteredModeLabel.SetNumberEditorUsesMouseWheel( false );
        filteredModeLabel.SetHasCustomTextHoverColor( false );
        filteredModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        filteredModeLabel.SetFont( "Arial", 6, true, false );

        millivoltRangeLabel = new VoltageLabel( "millivoltRangeLabel", "Millivolt Range Label", this, "0.01V" );
        AddComponent( millivoltRangeLabel );
        millivoltRangeLabel.SetWantsMouseNotifications( false );
        millivoltRangeLabel.SetPosition( 252, 235 );
        millivoltRangeLabel.SetSize( 16, 8 );
        millivoltRangeLabel.SetEditable( false, false );
        millivoltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        millivoltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        millivoltRangeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        millivoltRangeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        millivoltRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        millivoltRangeLabel.SetBorderSize( 1 );
        millivoltRangeLabel.SetMultiLineEdit( false );
        millivoltRangeLabel.SetIsNumberEditor( false );
        millivoltRangeLabel.SetNumberEditorRange( 0, 100 );
        millivoltRangeLabel.SetNumberEditorInterval( 1 );
        millivoltRangeLabel.SetNumberEditorUsesMouseWheel( false );
        millivoltRangeLabel.SetHasCustomTextHoverColor( false );
        millivoltRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        millivoltRangeLabel.SetFont( "Arial", 6, true, false );

        tenthVoltRangeLabel = new VoltageLabel( "tenthVoltRangeLabel", "Tenth Volt Range Label", this, "0.1V" );
        AddComponent( tenthVoltRangeLabel );
        tenthVoltRangeLabel.SetWantsMouseNotifications( false );
        tenthVoltRangeLabel.SetPosition( 265, 227 );
        tenthVoltRangeLabel.SetSize( 16, 8 );
        tenthVoltRangeLabel.SetEditable( false, false );
        tenthVoltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        tenthVoltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        tenthVoltRangeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        tenthVoltRangeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        tenthVoltRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        tenthVoltRangeLabel.SetBorderSize( 1 );
        tenthVoltRangeLabel.SetMultiLineEdit( false );
        tenthVoltRangeLabel.SetIsNumberEditor( false );
        tenthVoltRangeLabel.SetNumberEditorRange( 0, 100 );
        tenthVoltRangeLabel.SetNumberEditorInterval( 1 );
        tenthVoltRangeLabel.SetNumberEditorUsesMouseWheel( false );
        tenthVoltRangeLabel.SetHasCustomTextHoverColor( false );
        tenthVoltRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        tenthVoltRangeLabel.SetFont( "Arial", 6, true, false );

        voltRangeLabel = new VoltageLabel( "voltRangeLabel", "Volt Range Label", this, "1V" );
        AddComponent( voltRangeLabel );
        voltRangeLabel.SetWantsMouseNotifications( false );
        voltRangeLabel.SetPosition( 280, 227 );
        voltRangeLabel.SetSize( 16, 8 );
        voltRangeLabel.SetEditable( false, false );
        voltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltRangeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        voltRangeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        voltRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltRangeLabel.SetBorderSize( 1 );
        voltRangeLabel.SetMultiLineEdit( false );
        voltRangeLabel.SetIsNumberEditor( false );
        voltRangeLabel.SetNumberEditorRange( 0, 100 );
        voltRangeLabel.SetNumberEditorInterval( 1 );
        voltRangeLabel.SetNumberEditorUsesMouseWheel( false );
        voltRangeLabel.SetHasCustomTextHoverColor( false );
        voltRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltRangeLabel.SetFont( "Arial", 6, true, false );

        tenVoltRangeLabel = new VoltageLabel( "tenVoltRangeLabel", "Ten Volt Range Label", this, "10V" );
        AddComponent( tenVoltRangeLabel );
        tenVoltRangeLabel.SetWantsMouseNotifications( false );
        tenVoltRangeLabel.SetPosition( 293, 235 );
        tenVoltRangeLabel.SetSize( 16, 8 );
        tenVoltRangeLabel.SetEditable( false, false );
        tenVoltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        tenVoltRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        tenVoltRangeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        tenVoltRangeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        tenVoltRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        tenVoltRangeLabel.SetBorderSize( 1 );
        tenVoltRangeLabel.SetMultiLineEdit( false );
        tenVoltRangeLabel.SetIsNumberEditor( false );
        tenVoltRangeLabel.SetNumberEditorRange( 0, 100 );
        tenVoltRangeLabel.SetNumberEditorInterval( 1 );
        tenVoltRangeLabel.SetNumberEditorUsesMouseWheel( false );
        tenVoltRangeLabel.SetHasCustomTextHoverColor( false );
        tenVoltRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        tenVoltRangeLabel.SetFont( "Arial", 6, true, false );

        fineFrequencyLabel = new VoltageLabel( "fineFrequencyLabel", "Fine Frequency Label", this, "FINE" );
        AddComponent( fineFrequencyLabel );
        fineFrequencyLabel.SetWantsMouseNotifications( false );
        fineFrequencyLabel.SetPosition( 152, 181 );
        fineFrequencyLabel.SetSize( 16, 8 );
        fineFrequencyLabel.SetEditable( false, false );
        fineFrequencyLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineFrequencyLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineFrequencyLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fineFrequencyLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        fineFrequencyLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineFrequencyLabel.SetBorderSize( 1 );
        fineFrequencyLabel.SetMultiLineEdit( false );
        fineFrequencyLabel.SetIsNumberEditor( false );
        fineFrequencyLabel.SetNumberEditorRange( 0, 100 );
        fineFrequencyLabel.SetNumberEditorInterval( 1 );
        fineFrequencyLabel.SetNumberEditorUsesMouseWheel( false );
        fineFrequencyLabel.SetHasCustomTextHoverColor( false );
        fineFrequencyLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineFrequencyLabel.SetFont( "Arial", 6, true, false );

        externalFmInputLabel = new VoltageLabel( "externalFmInputLabel", "External Fm Input Label", this, "MOD IN" );
        AddComponent( externalFmInputLabel );
        externalFmInputLabel.SetWantsMouseNotifications( false );
        externalFmInputLabel.SetPosition( 370, 160 );
        externalFmInputLabel.SetSize( 60, 23 );
        externalFmInputLabel.SetEditable( false, false );
        externalFmInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        externalFmInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        externalFmInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        externalFmInputLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        externalFmInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        externalFmInputLabel.SetBorderSize( 1 );
        externalFmInputLabel.SetMultiLineEdit( false );
        externalFmInputLabel.SetIsNumberEditor( false );
        externalFmInputLabel.SetNumberEditorRange( 0, 100 );
        externalFmInputLabel.SetNumberEditorInterval( 1 );
        externalFmInputLabel.SetNumberEditorUsesMouseWheel( false );
        externalFmInputLabel.SetHasCustomTextHoverColor( false );
        externalFmInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        externalFmInputLabel.SetFont( "Arial", 10, true, false );

        externalFmLevelLabel = new VoltageLabel( "externalFmLevelLabel", "External Fm Level Label", this, "MOD LEVEL" );
        AddComponent( externalFmLevelLabel );
        externalFmLevelLabel.SetWantsMouseNotifications( false );
        externalFmLevelLabel.SetPosition( 310, 160 );
        externalFmLevelLabel.SetSize( 60, 23 );
        externalFmLevelLabel.SetEditable( false, false );
        externalFmLevelLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        externalFmLevelLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        externalFmLevelLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        externalFmLevelLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        externalFmLevelLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        externalFmLevelLabel.SetBorderSize( 1 );
        externalFmLevelLabel.SetMultiLineEdit( false );
        externalFmLevelLabel.SetIsNumberEditor( false );
        externalFmLevelLabel.SetNumberEditorRange( 0, 100 );
        externalFmLevelLabel.SetNumberEditorInterval( 1 );
        externalFmLevelLabel.SetNumberEditorUsesMouseWheel( false );
        externalFmLevelLabel.SetHasCustomTextHoverColor( false );
        externalFmLevelLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        externalFmLevelLabel.SetFont( "Arial", 10, true, false );

        frequencyLabel = new VoltageLabel( "frequencyLabel", "Frequency Label", this, "FREQUENCY" );
        AddComponent( frequencyLabel );
        frequencyLabel.SetWantsMouseNotifications( false );
        frequencyLabel.SetPosition( 200, 91 );
        frequencyLabel.SetSize( 60, 23 );
        frequencyLabel.SetEditable( false, false );
        frequencyLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        frequencyLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        frequencyLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        frequencyLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        frequencyLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        frequencyLabel.SetBorderSize( 1 );
        frequencyLabel.SetMultiLineEdit( false );
        frequencyLabel.SetIsNumberEditor( false );
        frequencyLabel.SetNumberEditorRange( 0, 100 );
        frequencyLabel.SetNumberEditorInterval( 1 );
        frequencyLabel.SetNumberEditorUsesMouseWheel( false );
        frequencyLabel.SetHasCustomTextHoverColor( false );
        frequencyLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        frequencyLabel.SetFont( "Arial", 10, true, false );

        outputLabel = new VoltageLabel( "outputLabel", "Output Label", this, "OUT" );
        AddComponent( outputLabel );
        outputLabel.SetWantsMouseNotifications( false );
        outputLabel.SetPosition( 370, 220 );
        outputLabel.SetSize( 60, 23 );
        outputLabel.SetEditable( false, false );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        outputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        outputLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        outputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        outputLabel.SetBorderSize( 1 );
        outputLabel.SetMultiLineEdit( false );
        outputLabel.SetIsNumberEditor( false );
        outputLabel.SetNumberEditorRange( 0, 100 );
        outputLabel.SetNumberEditorInterval( 1 );
        outputLabel.SetNumberEditorUsesMouseWheel( false );
        outputLabel.SetHasCustomTextHoverColor( false );
        outputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        outputLabel.SetFont( "Arial", 10, true, false );

        amplitudeLabel = new VoltageLabel( "amplitudeLabel", "Amplitude Label", this, "AMPLITUDE" );
        AddComponent( amplitudeLabel );
        amplitudeLabel.SetWantsMouseNotifications( false );
        amplitudeLabel.SetPosition( 309, 220 );
        amplitudeLabel.SetSize( 60, 23 );
        amplitudeLabel.SetEditable( false, false );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        amplitudeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        amplitudeLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        amplitudeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        amplitudeLabel.SetBorderSize( 1 );
        amplitudeLabel.SetMultiLineEdit( false );
        amplitudeLabel.SetIsNumberEditor( false );
        amplitudeLabel.SetNumberEditorRange( 0, 100 );
        amplitudeLabel.SetNumberEditorInterval( 1 );
        amplitudeLabel.SetNumberEditorUsesMouseWheel( false );
        amplitudeLabel.SetHasCustomTextHoverColor( false );
        amplitudeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        amplitudeLabel.SetFont( "Arial", 10, true, false );

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Logo", this, "iL" );
        AddComponent( manufacturerLabel );
        manufacturerLabel.SetWantsMouseNotifications( false );
        manufacturerLabel.SetPosition( 3, 337 );
        manufacturerLabel.SetSize( 20, 20 );
        manufacturerLabel.SetEditable( false, false );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLabel.SetColor( new Color( 147, 0, 0, 147 ) );
        manufacturerLabel.SetBkColor( new Color( 51, 51, 51, 255 ) );
        manufacturerLabel.SetBorderColor( new Color( 51, 51, 0, 147 ) );
        manufacturerLabel.SetBorderSize( 2 );
        manufacturerLabel.SetMultiLineEdit( false );
        manufacturerLabel.SetIsNumberEditor( false );
        manufacturerLabel.SetNumberEditorRange( 0, 100 );
        manufacturerLabel.SetNumberEditorInterval( 1 );
        manufacturerLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerLabel.SetHasCustomTextHoverColor( false );
        manufacturerLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerLabel.SetFont( "Courier New", 13, true, false );

        manufacturerNameLabel = new VoltageLabel( "manufacturerNameLabel", "Manufacturer Name Label", this, "INSECT LABORATORIES" );
        AddComponent( manufacturerNameLabel );
        manufacturerNameLabel.SetWantsMouseNotifications( false );
        manufacturerNameLabel.SetPosition( 31, 139 );
        manufacturerNameLabel.SetSize( 80, 10 );
        manufacturerNameLabel.SetEditable( false, false );
        manufacturerNameLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerNameLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerNameLabel.SetColor( new Color( 51, 51, 0, 255 ) );
        manufacturerNameLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manufacturerNameLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerNameLabel.SetBorderSize( 1 );
        manufacturerNameLabel.SetMultiLineEdit( false );
        manufacturerNameLabel.SetIsNumberEditor( false );
        manufacturerNameLabel.SetNumberEditorRange( 0, 100 );
        manufacturerNameLabel.SetNumberEditorInterval( 1 );
        manufacturerNameLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerNameLabel.SetHasCustomTextHoverColor( false );
        manufacturerNameLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerNameLabel.SetFont( "Arial Black", 9, false, false );

        manufacturerCityLabel = new VoltageLabel( "manufacturerCityLabel", "Manufacturer City Label", this, "PITTSBURGH" );
        AddComponent( manufacturerCityLabel );
        manufacturerCityLabel.SetWantsMouseNotifications( false );
        manufacturerCityLabel.SetPosition( 31, 147 );
        manufacturerCityLabel.SetSize( 80, 10 );
        manufacturerCityLabel.SetEditable( false, false );
        manufacturerCityLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerCityLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerCityLabel.SetColor( new Color( 51, 51, 0, 255 ) );
        manufacturerCityLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manufacturerCityLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerCityLabel.SetBorderSize( 1 );
        manufacturerCityLabel.SetMultiLineEdit( false );
        manufacturerCityLabel.SetIsNumberEditor( false );
        manufacturerCityLabel.SetNumberEditorRange( 0, 100 );
        manufacturerCityLabel.SetNumberEditorInterval( 1 );
        manufacturerCityLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerCityLabel.SetHasCustomTextHoverColor( false );
        manufacturerCityLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerCityLabel.SetFont( "Arial Black", 9, false, false );

        manufacturerCountryLabel = new VoltageLabel( "manufacturerCountryLabel", "Manufacturer Country Label", this, "UNITED STATES OF AMERICA" );
        AddComponent( manufacturerCountryLabel );
        manufacturerCountryLabel.SetWantsMouseNotifications( false );
        manufacturerCountryLabel.SetPosition( 31, 155 );
        manufacturerCountryLabel.SetSize( 80, 10 );
        manufacturerCountryLabel.SetEditable( false, false );
        manufacturerCountryLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerCountryLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerCountryLabel.SetColor( new Color( 51, 51, 0, 255 ) );
        manufacturerCountryLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manufacturerCountryLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerCountryLabel.SetBorderSize( 1 );
        manufacturerCountryLabel.SetMultiLineEdit( false );
        manufacturerCountryLabel.SetIsNumberEditor( false );
        manufacturerCountryLabel.SetNumberEditorRange( 0, 100 );
        manufacturerCountryLabel.SetNumberEditorInterval( 1 );
        manufacturerCountryLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerCountryLabel.SetHasCustomTextHoverColor( false );
        manufacturerCountryLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerCountryLabel.SetFont( "Arial Black", 9, false, false );

        modelNumberLabel = new VoltageLabel( "modelNumberLabel", "Model Number Label", this, "MODEL 1947B Mk II" );
        AddComponent( modelNumberLabel );
        modelNumberLabel.SetWantsMouseNotifications( false );
        modelNumberLabel.SetPosition( 370, 63 );
        modelNumberLabel.SetSize( 80, 10 );
        modelNumberLabel.SetEditable( false, false );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modelNumberLabel.SetColor( new Color( 51, 51, 0, 255 ) );
        modelNumberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modelNumberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modelNumberLabel.SetBorderSize( 1 );
        modelNumberLabel.SetMultiLineEdit( false );
        modelNumberLabel.SetIsNumberEditor( false );
        modelNumberLabel.SetNumberEditorRange( 0, 100 );
        modelNumberLabel.SetNumberEditorInterval( 1 );
        modelNumberLabel.SetNumberEditorUsesMouseWheel( false );
        modelNumberLabel.SetHasCustomTextHoverColor( false );
        modelNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modelNumberLabel.SetFont( "Arial Black", 9, false, false );

        moduleDescriptionLabel = new VoltageLabel( "moduleDescriptionLabel", "Module Description Label", this, "SIN/RND GENERATOR - FILTER" );
        AddComponent( moduleDescriptionLabel );
        moduleDescriptionLabel.SetWantsMouseNotifications( false );
        moduleDescriptionLabel.SetPosition( 370, 70 );
        moduleDescriptionLabel.SetSize( 80, 10 );
        moduleDescriptionLabel.SetEditable( false, false );
        moduleDescriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        moduleDescriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleDescriptionLabel.SetColor( new Color( 51, 51, 0, 255 ) );
        moduleDescriptionLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        moduleDescriptionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        moduleDescriptionLabel.SetBorderSize( 1 );
        moduleDescriptionLabel.SetMultiLineEdit( false );
        moduleDescriptionLabel.SetIsNumberEditor( false );
        moduleDescriptionLabel.SetNumberEditorRange( 0, 100 );
        moduleDescriptionLabel.SetNumberEditorInterval( 1 );
        moduleDescriptionLabel.SetNumberEditorUsesMouseWheel( false );
        moduleDescriptionLabel.SetHasCustomTextHoverColor( false );
        moduleDescriptionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleDescriptionLabel.SetFont( "Arial Black", 9, false, false );
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
        resumePending = true;
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
                outputMeter.SetValue(meterDisplay);
                powerLed.SetValue(lampDisplay);
                break;
            case Reset:
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
        boolean powered = powerSwitch.GetValue() >= 0.5;
        if (!powered && powerEnvelope == 0.0) {
            resumePending = true;
            meterDisplay = 0.0;
            lampDisplay = 0.0;
            mainOutput.SetValue(0.0);
            return;
        }
        updateTargets();
        int mode = modeIndex();
        double amplitudeTarget = amplitudeKnob.GetValue() * amplitudeRange() * 2;
        double externalTarget = externalFmLevelKnob.GetValue();
        double randomTarget = randomModulationKnob.GetValue();
        boolean externalInputConnected = externalFmInput.IsConnected();
        boolean filteredAudioTarget = mode == 5 && externalInputConnected;
        double noiseTarget = mode == 2 ? 1.0 : 0.0;
        double filteredNoiseTarget = mode == 3 ? 1.0 : 0.0;
        double internalTarget = (mode == 4 || (mode == 5 && !filteredAudioTarget)) ? 1.0 : 0.0;
        double filteredTarget = mode == 5 && !filteredAudioTarget ? 1.0 : 0.0;
        if (resumePending) {
            resumePending = false;
            frequency = targetFrequency;
            amplitude = amplitudeTarget;
            externalLevel = externalTarget;
            randomLevel = randomTarget;
            noiseMix = noiseTarget;
            filteredNoiseMix = filteredNoiseTarget;
            filteredAudioMix = filteredAudioTarget ? 1.0 : 0.0;
            internalMix = internalTarget;
            filteredMix = filteredTarget;
            highCoefficient = targetHighCoefficient;
            lowCoefficient = targetLowCoefficient;
            lowState = highState = previousRaw = 0.0;
            meterEnvelope = 0.0;
            overloadEnvelope = 0.0;
            powerEnvelope = 0.0;
        }
        frequency += CONTROL_SMOOTH * (targetFrequency - frequency);
        amplitude += CONTROL_SMOOTH * (amplitudeTarget - amplitude);
        externalLevel += CONTROL_SMOOTH * (externalTarget - externalLevel);
        randomLevel += CONTROL_SMOOTH * (randomTarget - randomLevel);
        noiseMix += CONTROL_SMOOTH * (noiseTarget - noiseMix);
        filteredNoiseMix += CONTROL_SMOOTH * (filteredNoiseTarget - filteredNoiseMix);
        filteredAudioMix += CONTROL_SMOOTH * ((filteredAudioTarget ? 1.0 : 0.0) - filteredAudioMix);
        internalMix += CONTROL_SMOOTH * (internalTarget - internalMix);
        filteredMix += CONTROL_SMOOTH * (filteredTarget - filteredMix);
        highCoefficient += CONTROL_SMOOTH * (targetHighCoefficient - highCoefficient);
        lowCoefficient += CONTROL_SMOOTH * (targetLowCoefficient - lowCoefficient);
        updatePowerEnvelope(powered);

        double noise = whiteNoise();
        boolean externalFmMode = externalInputConnected && (mode == 1 || mode == 4);
        double external = 0.0;
        if (externalFmMode || filteredAudioTarget) {
            double input = externalFmInput.GetValue();
            external = Double.isFinite(input) ? input : 0.0;
        }
        // FLT with a connected MOD IN is an audio filter. RND MOD blends white
        // noise before the existing filter, while MOD LEVEL is its input attenuverter.
        double filterSource = filteredAudioTarget
                ? clamp(external * externalLevel / 5.0 + noise * randomLevel, -16.0, 16.0)
                : noise;
        highState += highCoefficient * (filterSource - highState);
        lowState += lowCoefficient * (filterSource - highState - lowState);
        double modulation;
        double filteredNoise = lowState - filterSource;
        // Cable presence is authoritative, even with silence or a zero attenuverter.
        if (externalFmMode) {
            modulation = external * externalLevel / 5.0;
        } else {
            modulation = (noise + filteredMix * (filteredNoise - noise))
                    * randomLevel * internalMix * 1.5;
        }
        double thermalFrequency =
        frequency * thermalFrequencyScale();

double instantFrequency = externalFmMode
        ? clamp(
                thermalFrequency * (1.0 + modulation),
                0.0,
                SAMPLE_RATE * 0.495
        )
        : clamp(
                thermalFrequency * Math.pow(2.0, modulation),
                0.0,
                SAMPLE_RATE * 0.495
        );
        phase += instantFrequency / SAMPLE_RATE;
        phase -= Math.floor(phase);
        double sine = Math.sin(2.0 * Math.PI * phase);
        double sineMix = 1.0 - noiseMix - filteredNoiseMix;
        double generatorSignal = sineMix * sine + noiseMix * noise + filteredNoiseMix * filteredNoise;
        double raw = ((1.0 - filteredAudioMix) * generatorSignal + filteredAudioMix * filteredNoise) * amplitude;
        // Two evaluations of the nonlinear residual; the clean path stays sample exact.
        // Lightweight 2x midpoint approximation, not a full bandlimited oversampler.
        double residual = 0.5 * (compressionResidual(0.5 * (raw + previousRaw)) + compressionResidual(raw));
        previousRaw = raw;
        double output = (raw + residual) * powerEnvelope;
        mainOutput.SetValue(output);
        // Log-like meter response so all four amplitude ranges
// produce useful visible movement.
double meterMagnitude =
        Math.max(Math.abs(output), 1.0e-9);

double meterDb =
        20.0 * Math.log10(meterMagnitude / 10.0);

double meterTarget =
        clamp(
                (meterDb + 80.0) / 80.0,
                0.0,
                1.0
        );

double meterRate =
        meterTarget > meterEnvelope
        ? METER_ATTACK
        : METER_RELEASE;

meterEnvelope +=
        meterRate * (meterTarget - meterEnvelope);

meterDisplay = meterEnvelope;


// Output-stage overload indication.
double overload =
        clamp(
                Math.abs(residual) / 1.0,
                0.0,
                1.0
        );

double lampRate =
        overload > overloadEnvelope
        ? LAMP_ATTACK
        : LAMP_RELEASE;

overloadEnvelope +=
        lampRate * (overload - overloadEnvelope);


// Lamp shows ordinary signal activity as well as overload.
// It retains a faint filament glow when powered but idle.
lampDisplay =
        powerEnvelope
        * clamp(
                0.08
                + 0.62 * meterEnvelope
                + 0.30 * overloadEnvelope,
                0.0,
                1.0
        );
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
        resumePending = true;
        meterDisplay = 0.0;
        lampDisplay = 0.0;
        mainOutput.SetValue(0.0);
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
        if (component == frequencyKnob) return String.format(java.util.Locale.ROOT, "Frequency: %.2f Hz (before fine adjustment)", 50.0 * Math.pow(360.0, frequencyKnob.GetValue()));
        if (component == fineFrequencyKnob) return String.format(java.util.Locale.ROOT, "Fine Frequency: %+.1f cents", 200.0 * fineFrequencyKnob.GetValue());
        if (component == amplitudeKnob) return String.format(java.util.Locale.ROOT, "Amplitude: %.4f V peak requested", amplitudeKnob.GetValue() * amplitudeRange() * 2);
        if (component == amplitudeRangeKnob) return String.format(java.util.Locale.ROOT, "Amplitude Range: %.2f V peak", amplitudeRange());
        if (component == modeKnob) return new String[] {"SIN: sine", "RND: white noise", "RD2: filtered white noise", "MOD: noise FM", "FLT: filtered-noise FM"}[modeIndex() - 1];
        if (component == externalFmInput) return "MOD IN: linear FM in SIN/MOD; filtered audio input in FLT; unused in RND/RD2";
        if (component == externalFmLevelKnob) return String.format(java.util.Locale.ROOT, "MOD Level: %+.1f%%; 5 V at full level gives 200%% FM deviation or 2x FLT input level", 100.0 * externalFmLevelKnob.GetValue());
        if (component == randomModulationKnob) return String.format(java.util.Locale.ROOT, "Random Modulation: %.1f%%; MOD/FLT depth, or white-noise blend before FLT input filtering", 100.0 * randomModulationKnob.GetValue());
        if (component == filterKnob) return "Noise Filter: left is low/wide; right is high/narrow; affects RD2, internal FLT modulation and FLT input audio";
        if (component == powerSwitch) return "Power: 2.8s warmup; 1.5s cooldown; oscillator pitch settles as the circuitry warms";
        if (component == powerLed) return "Power: filament-style warmup/cooldown; brightness still responds to output-stage overload";
        if (component == outputMeter) return "Output Level: averaged indication; not a calibrated reference meter";
        if (component == mainOutput) return "OUT: selected SIN, RND, RD2, MOD, or FLT signal";
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
            frequencyKnob.SetValue(Math.log(clamp(newValue, 50.0, 18000.0) / 50.0) / Math.log(360.0));
            return;
        }
        if (component == fineFrequencyKnob) { fineFrequencyKnob.SetValue(clamp(newValue / 200.0, -1.0, 1.0)); return; }
        if (component == amplitudeKnob) { amplitudeKnob.SetValue(clamp(newValue / amplitudeRange(), 0.0, 1.0)); return; }
        if (component == amplitudeRangeKnob) {
            amplitudeRangeKnob.SetValue(newValue <= 0.01 ? 1.0 : newValue <= 0.1 ? 2.0 : newValue <= 1.0 ? 3.0 : 4.0);
            return;
        }
        if (component == externalFmLevelKnob) { externalFmLevelKnob.SetValue(clamp(newValue / 100.0, -2.0, 2.0)); return; }
        if (component == randomModulationKnob) { randomModulationKnob.SetValue(clamp(newValue / 100.0, 0.0, 1.0)); return; }
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
    private VoltageLabel moduleDescriptionLabel;
    private VoltageLabel modelNumberLabel;
    private VoltageLabel manufacturerCountryLabel;
    private VoltageLabel manufacturerCityLabel;
    private VoltageLabel manufacturerNameLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageLabel amplitudeLabel;
    private VoltageLabel outputLabel;
    private VoltageLabel frequencyLabel;
    private VoltageLabel externalFmLevelLabel;
    private VoltageLabel externalFmInputLabel;
    private VoltageLabel fineFrequencyLabel;
    private VoltageLabel tenVoltRangeLabel;
    private VoltageLabel voltRangeLabel;
    private VoltageLabel tenthVoltRangeLabel;
    private VoltageLabel millivoltRangeLabel;
    private VoltageLabel filteredModeLabel;
    private VoltageLabel modulationModeLabel;
    private VoltageLabel rd2ModeLabel;
    private VoltageLabel noiseModeLabel;
    private VoltageLabel sineModeLabel;
    private VoltageLabel filterLabel;
    private VoltageLabel randomModulationLabel;
    private VoltageLED powerLed;
    private VoltageKnob filterKnob;
    private VoltageKnob modeKnob;
    private VoltageKnob randomModulationKnob;
    private VoltageKnob fineFrequencyKnob;
    private VoltageKnob frequencyKnob;
    private VoltageKnob externalFmLevelKnob;
    private VoltageAudioJack externalFmInput;
    private VoltageKnob amplitudeRangeKnob;
    private VoltageKnob amplitudeKnob;
    private VoltageAudioJack mainOutput;
    private VoltageSwitch powerSwitch;
    private VoltageAnalogVUMeter outputMeter;
    private VoltageLabel numberLabel;
    private VoltageLabel brandLabel;


    //[user-code-and-variables]    Add your own variables and functions here
    // Voltage Modular processes audio at 48 kHz. All per-sample state is allocation-free.
    private static final double SAMPLE_RATE = 48000.0;
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE));
    private static final double METER_ATTACK = 1.0 - Math.exp(-1.0 / (0.015 * SAMPLE_RATE));
    private static final double METER_RELEASE = 1.0 - Math.exp(-1.0 / (0.180 * SAMPLE_RATE));
    private static final double POWER_WARMUP_STEP = 1.0 / (2.8 * SAMPLE_RATE);
    private static final double POWER_COOLDOWN_STEP = 1.0 / (1.5 * SAMPLE_RATE);
    private static final double LAMP_ATTACK = 1.0 - Math.exp(-1.0 / (0.008 * SAMPLE_RATE));
    private static final double LAMP_RELEASE = 1.0 - Math.exp(-1.0 / (0.140 * SAMPLE_RATE));
    private volatile boolean resumePending = true;
    private volatile double meterDisplay, lampDisplay;
    private double phase, powerEnvelope, frequency = 50.0, amplitude, externalLevel, randomLevel;
    private double noiseMix, filteredNoiseMix, filteredAudioMix, internalMix, filteredMix, lowState, highState;
    private double highCoefficient, lowCoefficient, targetHighCoefficient, targetLowCoefficient;
    private double cachedFrequency = Double.NaN, cachedFine = Double.NaN, targetFrequency = 50.0;
    private double cachedFilter = Double.NaN, meterEnvelope, previousRaw, overloadEnvelope;
    private int noiseState = 0x195195;

    private static double clamp(double value, double low, double high) {
        return Math.max(low, Math.min(high, value));
    }

    private int modeIndex() { return (int) clamp(Math.round(modeKnob.GetValue()), 1.0, 5.0); }

    private double amplitudeRange() {
        switch ((int) Math.round(amplitudeRangeKnob.GetValue())) {
            case 1: return 0.01;
            case 2: return 0.1;
            case 3: return 1.0;
            default: return 10.0;
        }
    }

    private void updateTargets() {
        double coarse = frequencyKnob.GetValue();
        double fine = fineFrequencyKnob.GetValue();
        if (coarse != cachedFrequency || fine != cachedFine) {
            cachedFrequency = coarse;
            cachedFine = fine;
            targetFrequency = clamp(50.0 * Math.pow(360.0, coarse) * Math.pow(2.0, fine / 6.0), 50.0, 18000.0);
        }
        double filter = filterKnob.GetValue();
        if (filter != cachedFilter) {
            cachedFilter = filter;
            // A broad low band opens into a narrower high band; this is an audition voicing.
            double highHz = 20.0 * Math.pow(225.0, filter);
            double lowHz = filter <= 0.5 ? 3000.0 * Math.pow(3400.0 / 3000.0, filter * 2.0)
                    : 3400.0 * Math.pow(6000.0 / 3400.0, (filter - 0.5) * 2.0);
            targetHighCoefficient = 1.0 - Math.exp(-2.0 * Math.PI * highHz / SAMPLE_RATE);
            targetLowCoefficient = 1.0 - Math.exp(-2.0 * Math.PI * lowHz / SAMPLE_RATE);
        }
    }
private void updatePowerEnvelope(boolean powered) {
    if (powered) {
        powerEnvelope = Math.min(
                1.0,
                powerEnvelope + POWER_WARMUP_STEP
        );
    } else {
        powerEnvelope = Math.max(
                0.0,
                powerEnvelope - POWER_COOLDOWN_STEP
        );
    }
}

private double thermalFrequencyScale() {
    // Up to 1.5% flat when cold, settling to calibrated
    // pitch as the simulated circuitry warms.
    return 1.0
            - (1.0 - powerEnvelope) * 0.015;
}
    private double whiteNoise() {
        noiseState ^= noiseState << 13;
        noiseState ^= noiseState >>> 17;
        noiseState ^= noiseState << 5;
        return noiseState / 2147483648.0;
    }

    // Unity below 5 V, continuous slope at the knee; high-range compression only.
    private static double compressionResidual(double value) {
        double excess = Math.abs(value) - 5.0;
        if (excess <= 0.0) return 0.0;
        return Math.copySign(5.0 + 4.6 * Math.tanh(excess / 4.6), value) - value;
    }

    //[/user-code-and-variables]
}

 