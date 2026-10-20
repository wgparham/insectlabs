package com.insectlabs.ced;


import voltage.controllers.*;
import voltage.core.*;
import voltage.core.Jack.JackType;
import voltage.sources.*;
import voltage.utility.*;
import voltage.processors.*;
import voltage.effects.*;
import java.awt.*;

//[user-imports]   Add your own imports here
import java.util.Locale;
//[/user-imports]


public class ced extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public ced( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "CED - Chorus Ensemble Device", ModuleType.ModuleType_Effect, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "a17dd99a897b47b9ba71937a1587a2bb" );
    }

void InitializeControls()
{

        labelEnsembleHeadingRed = new VoltageLabel( "labelEnsembleHeadingRed", "Ensemble Heading Red", this, "––—––  ENSEMBLE  ––—––" );
        AddComponent( labelEnsembleHeadingRed );
        labelEnsembleHeadingRed.SetWantsMouseNotifications( false );
        labelEnsembleHeadingRed.SetPosition( 0, 102 );
        labelEnsembleHeadingRed.SetSize( 115, 18 );
        labelEnsembleHeadingRed.SetEditable( false, false );
        labelEnsembleHeadingRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEnsembleHeadingRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEnsembleHeadingRed.SetColor( new Color( 164, 34, 34, 255 ) );
        labelEnsembleHeadingRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEnsembleHeadingRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEnsembleHeadingRed.SetBorderSize( 1 );
        labelEnsembleHeadingRed.SetMultiLineEdit( false );
        labelEnsembleHeadingRed.SetIsNumberEditor( false );
        labelEnsembleHeadingRed.SetNumberEditorRange( 0, 100 );
        labelEnsembleHeadingRed.SetNumberEditorInterval( 1 );
        labelEnsembleHeadingRed.SetNumberEditorUsesMouseWheel( false );
        labelEnsembleHeadingRed.SetHasCustomTextHoverColor( false );
        labelEnsembleHeadingRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEnsembleHeadingRed.SetFont( "Arial", 9, true, false );

        labelEnsembleHeadingBase = new VoltageLabel( "labelEnsembleHeadingBase", "Ensemble Heading Base", this, "ENSEMBLE" );
        AddComponent( labelEnsembleHeadingBase );
        labelEnsembleHeadingBase.SetWantsMouseNotifications( false );
        labelEnsembleHeadingBase.SetPosition( 0, 102 );
        labelEnsembleHeadingBase.SetSize( 115, 18 );
        labelEnsembleHeadingBase.SetEditable( false, false );
        labelEnsembleHeadingBase.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEnsembleHeadingBase.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEnsembleHeadingBase.SetColor( new Color( 26, 26, 26, 255 ) );
        labelEnsembleHeadingBase.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEnsembleHeadingBase.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEnsembleHeadingBase.SetBorderSize( 1 );
        labelEnsembleHeadingBase.SetMultiLineEdit( false );
        labelEnsembleHeadingBase.SetIsNumberEditor( false );
        labelEnsembleHeadingBase.SetNumberEditorRange( 0, 100 );
        labelEnsembleHeadingBase.SetNumberEditorInterval( 1 );
        labelEnsembleHeadingBase.SetNumberEditorUsesMouseWheel( false );
        labelEnsembleHeadingBase.SetHasCustomTextHoverColor( false );
        labelEnsembleHeadingBase.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEnsembleHeadingBase.SetFont( "Arial", 9, true, false );

        labelChorusHeadingRed = new VoltageLabel( "labelChorusHeadingRed", "Chorus Heading Red", this, "–——––  CHORUS  ––——–" );
        AddComponent( labelChorusHeadingRed );
        labelChorusHeadingRed.SetWantsMouseNotifications( false );
        labelChorusHeadingRed.SetPosition( 0, 34 );
        labelChorusHeadingRed.SetSize( 115, 18 );
        labelChorusHeadingRed.SetEditable( false, false );
        labelChorusHeadingRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelChorusHeadingRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelChorusHeadingRed.SetColor( new Color( 164, 34, 34, 255 ) );
        labelChorusHeadingRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelChorusHeadingRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelChorusHeadingRed.SetBorderSize( 1 );
        labelChorusHeadingRed.SetMultiLineEdit( false );
        labelChorusHeadingRed.SetIsNumberEditor( false );
        labelChorusHeadingRed.SetNumberEditorRange( 0, 100 );
        labelChorusHeadingRed.SetNumberEditorInterval( 1 );
        labelChorusHeadingRed.SetNumberEditorUsesMouseWheel( false );
        labelChorusHeadingRed.SetHasCustomTextHoverColor( false );
        labelChorusHeadingRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelChorusHeadingRed.SetFont( "Arial", 9, true, false );

        labelChorusHeadingBase = new VoltageLabel( "labelChorusHeadingBase", "Chorus Heading Base", this, "CHORUS" );
        AddComponent( labelChorusHeadingBase );
        labelChorusHeadingBase.SetWantsMouseNotifications( false );
        labelChorusHeadingBase.SetPosition( 0, 34 );
        labelChorusHeadingBase.SetSize( 115, 18 );
        labelChorusHeadingBase.SetEditable( false, false );
        labelChorusHeadingBase.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelChorusHeadingBase.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelChorusHeadingBase.SetColor( new Color( 26, 26, 26, 255 ) );
        labelChorusHeadingBase.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelChorusHeadingBase.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelChorusHeadingBase.SetBorderSize( 1 );
        labelChorusHeadingBase.SetMultiLineEdit( false );
        labelChorusHeadingBase.SetIsNumberEditor( false );
        labelChorusHeadingBase.SetNumberEditorRange( 0, 100 );
        labelChorusHeadingBase.SetNumberEditorInterval( 1 );
        labelChorusHeadingBase.SetNumberEditorUsesMouseWheel( false );
        labelChorusHeadingBase.SetHasCustomTextHoverColor( false );
        labelChorusHeadingBase.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelChorusHeadingBase.SetFont( "Arial", 9, true, false );

        labelFooter = new VoltageLabel( "labelFooter", "Ensemble Chorus Device", this, "CHORUS ENSEMBLE DEVICE" );
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

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 5 );
        imageTTW.SetSize( 41, 13 );
        imageTTW.SetCurrentImage( "image image(3).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "ced" );
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

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(Seattle) Ltd" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 5, 13 );
        labelLocation.SetSize( 41, 14 );
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

        audioInLeft = new VoltageAudioJack( "audioInLeft", "In L", this, JackType.JackType_AudioInput );
        AddComponent( audioInLeft );
        audioInLeft.SetWantsMouseNotifications( false );
        audioInLeft.SetPosition( 20, 245 );
        audioInLeft.SetSize( 25, 25 );
        audioInLeft.SetSkin( "Jack Round 25px" );

        audioInRight = new VoltageAudioJack( "audioInRight", "In R", this, JackType.JackType_AudioInput );
        AddComponent( audioInRight );
        audioInRight.SetWantsMouseNotifications( false );
        audioInRight.SetPosition( 70, 245 );
        audioInRight.SetSize( 25, 25 );
        audioInRight.SetSkin( "Jack Round 25px" );

        audioOutLeft = new VoltageAudioJack( "audioOutLeft", "Out L", this, JackType.JackType_AudioOutput );
        AddComponent( audioOutLeft );
        audioOutLeft.SetWantsMouseNotifications( false );
        audioOutLeft.SetPosition( 20, 285 );
        audioOutLeft.SetSize( 25, 25 );
        audioOutLeft.SetSkin( "Mini Jack 25px" );

        audioOutRight = new VoltageAudioJack( "audioOutRight", "Out R", this, JackType.JackType_AudioOutput );
        AddComponent( audioOutRight );
        audioOutRight.SetWantsMouseNotifications( false );
        audioOutRight.SetPosition( 70, 285 );
        audioOutRight.SetSize( 25, 25 );
        audioOutRight.SetSkin( "Mini Jack 25px" );

        rateCvIn = new VoltageAudioJack( "rateCvIn", "Rate CV", this, JackType.JackType_AudioInput );
        AddComponent( rateCvIn );
        rateCvIn.SetWantsMouseNotifications( false );
        rateCvIn.SetPosition( 5, 177 );
        rateCvIn.SetSize( 25, 25 );
        rateCvIn.SetSkin( "Jack Round 25px" );

        depthCvIn = new VoltageAudioJack( "depthCvIn", "Depth CV", this, JackType.JackType_AudioInput );
        AddComponent( depthCvIn );
        depthCvIn.SetWantsMouseNotifications( false );
        depthCvIn.SetPosition( 60, 177 );
        depthCvIn.SetSize( 25, 25 );
        depthCvIn.SetSkin( "Jack Round 25px" );

        dimensionButton1 = new VoltageToggle( "dimensionButton1", "1", this, false, 0 );
        AddComponent( dimensionButton1 );
        dimensionButton1.SetWantsMouseNotifications( false );
        dimensionButton1.SetPosition( 10, 120 );
        dimensionButton1.SetSize( 19, 19 );
        dimensionButton1.SetSkin( "2500 Square Big" );
        dimensionButton1.ShowOverlay( true );
        dimensionButton1.SetImageOverlay( "button-label-1.svg" );
        dimensionButton1.SetOverlayText( "" );
        dimensionButton1.SetOverlayArea( 0, 0, 0, 0 );
        dimensionButton1.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        dimensionButton2 = new VoltageToggle( "dimensionButton2", "2", this, false, 0 );
        AddComponent( dimensionButton2 );
        dimensionButton2.SetWantsMouseNotifications( false );
        dimensionButton2.SetPosition( 35, 120 );
        dimensionButton2.SetSize( 19, 19 );
        dimensionButton2.SetSkin( "2500 Square Big" );
        dimensionButton2.ShowOverlay( true );
        dimensionButton2.SetImageOverlay( "button-label-2.svg" );
        dimensionButton2.SetOverlayText( "" );
        dimensionButton2.SetOverlayArea( 0, 0, 0, 0 );
        dimensionButton2.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        dimensionButton3 = new VoltageToggle( "dimensionButton3", "3", this, false, 0 );
        AddComponent( dimensionButton3 );
        dimensionButton3.SetWantsMouseNotifications( false );
        dimensionButton3.SetPosition( 60, 120 );
        dimensionButton3.SetSize( 19, 19 );
        dimensionButton3.SetSkin( "2500 Square Big" );
        dimensionButton3.ShowOverlay( true );
        dimensionButton3.SetImageOverlay( "button-label-3.svg" );
        dimensionButton3.SetOverlayText( "" );
        dimensionButton3.SetOverlayArea( 0, 0, 0, 0 );
        dimensionButton3.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        dimensionButton4 = new VoltageToggle( "dimensionButton4", "4", this, false, 0 );
        AddComponent( dimensionButton4 );
        dimensionButton4.SetWantsMouseNotifications( false );
        dimensionButton4.SetPosition( 85, 120 );
        dimensionButton4.SetSize( 19, 19 );
        dimensionButton4.SetSkin( "2500 Square Big" );
        dimensionButton4.ShowOverlay( true );
        dimensionButton4.SetImageOverlay( "button-label-4.svg" );
        dimensionButton4.SetOverlayText( "" );
        dimensionButton4.SetOverlayArea( 0, 0, 0, 0 );
        dimensionButton4.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        exclusiveToggle = new VoltageToggle( "exclusiveToggle", "Exclusive", this, false, 0 );
        AddComponent( exclusiveToggle );
        exclusiveToggle.SetWantsMouseNotifications( false );
        exclusiveToggle.SetPosition( 12, 151 );
        exclusiveToggle.SetSize( 15, 15 );
        exclusiveToggle.SetSkin( "Mini Amber" );
        exclusiveToggle.ShowOverlay( true );
        exclusiveToggle.SetOverlayText( "EXC" );
        exclusiveToggle.SetOverlayTextFont( "Arial Black", 7, false, false );
        exclusiveToggle.SetOverlayTextColor( new Color( 0, 0, 0 ) );
        exclusiveToggle.SetOverlayArea( 0, 0, 0, 0 );
        exclusiveToggle.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        companderToggle = new VoltageToggle( "companderToggle", "Compander", this, false, 0 );
        AddComponent( companderToggle );
        companderToggle.SetWantsMouseNotifications( false );
        companderToggle.SetPosition( 37, 151 );
        companderToggle.SetSize( 15, 15 );
        companderToggle.SetSkin( "Mini Amber" );
        companderToggle.ShowOverlay( true );
        companderToggle.SetOverlayText( "CMP" );
        companderToggle.SetOverlayTextFont( "Arial Black", 7, false, false );
        companderToggle.SetOverlayTextColor( new Color( 0, 0, 0 ) );
        companderToggle.SetOverlayArea( 0, 0, 0, 0 );
        companderToggle.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        modeSwitch = new VoltageSwitch( "modeSwitch", "Mode Switch", this, 0 );
        AddComponent( modeSwitch );
        modeSwitch.SetWantsMouseNotifications( false );
        modeSwitch.SetPosition( 39, 311 );
        modeSwitch.SetSize( 37, 15 );
        modeSwitch.SetSkin( "Rocker Switch Plastic Orange Hor" );

        combineSwitch = new VoltageSwitch( "combineSwitch", "Combine Switch", this, 0 );
        AddComponent( combineSwitch );
        combineSwitch.SetWantsMouseNotifications( false );
        combineSwitch.SetPosition( 36, 205 );
        combineSwitch.SetSize( 13, 31 );
        combineSwitch.SetSkin( "3-State Slide Black" );

        inputSwitch = new VoltageSwitch( "inputSwitch", "Input Switch", this, 0 );
        AddComponent( inputSwitch );
        inputSwitch.SetWantsMouseNotifications( false );
        inputSwitch.SetPosition( 5, 267 );
        inputSwitch.SetSize( 11, 21 );
        inputSwitch.SetSkin( "2-State Slide Vertical" );

        vibratoSwitch = new VoltageSwitch( "vibratoSwitch", "Vibrato Switch", this, 0 );
        AddComponent( vibratoSwitch );
        vibratoSwitch.SetWantsMouseNotifications( false );
        vibratoSwitch.SetPosition( 39, 87 );
        vibratoSwitch.SetSize( 37, 15 );
        vibratoSwitch.SetSkin( "Rocker Switch Plastic Blue Hor" );

        mixKnob = new VoltageKnob( "mixKnob", "Mix Knob", this, 0.0, 1.0, 1.0 );
        AddComponent( mixKnob );
        mixKnob.SetWantsMouseNotifications( false );
        mixKnob.SetPosition( 48, 268 );
        mixKnob.SetSize( 19, 19 );
        mixKnob.SetSkin( "ARP2500 Sm Silver" );
        mixKnob.SetRange( 0.0, 1.0, 1.0, false, 0 );
        mixKnob.SetKnobParams( 215, 145 );
        mixKnob.DisplayValueInPercent( true );
        mixKnob.SetKnobAdjustsRing( true );

        rateKnob = new VoltageKnob( "rateKnob", "Rate Knob", this, 0.0, 1.0, 0.68364 );
        AddComponent( rateKnob );
        rateKnob.SetWantsMouseNotifications( false );
        rateKnob.SetPosition( 33, 180 );
        rateKnob.SetSize( 19, 19 );
        rateKnob.SetSkin( "ARP2500 Sm Emerald" );
        rateKnob.SetRange( 0.0, 1.0, 0.68364, false, 0 );
        rateKnob.SetKnobParams( 215, 145 );
        rateKnob.DisplayValueInPercent( false );
        rateKnob.SetKnobAdjustsRing( true );

        depthKnob = new VoltageKnob( "depthKnob", "Depth Knob", this, 0.0, 2.0, 1.0 );
        AddComponent( depthKnob );
        depthKnob.SetWantsMouseNotifications( false );
        depthKnob.SetPosition( 88, 180 );
        depthKnob.SetSize( 19, 19 );
        depthKnob.SetSkin( "ARP2500 Sm Emerald" );
        depthKnob.SetRange( 0.0, 2.0, 1.0, false, 0 );
        depthKnob.SetKnobParams( 215, 145 );
        depthKnob.DisplayValueInPercent( true );
        depthKnob.SetKnobAdjustsRing( true );

        noiseKnob = new VoltageKnob( "noiseKnob", "Noise Knob", this, 0.0, 1.0, 0.0 );
        AddComponent( noiseKnob );
        noiseKnob.SetWantsMouseNotifications( false );
        noiseKnob.SetPosition( 66, 146 );
        noiseKnob.SetSize( 25, 25 );
        noiseKnob.SetSkin( "ARP2500 Sm Blue" );
        noiseKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        noiseKnob.SetKnobParams( 215, 145 );
        noiseKnob.DisplayValueInPercent( false );
        noiseKnob.SetKnobAdjustsRing( true );

        ceRateKnob = new VoltageKnob( "ceRateKnob", "CE-1 Rate Knob", this, 0.0, 1.0, 0.54660 );
        AddComponent( ceRateKnob );
        ceRateKnob.SetWantsMouseNotifications( false );
        ceRateKnob.SetPosition( 10, 50 );
        ceRateKnob.SetSize( 25, 25 );
        ceRateKnob.SetSkin( "ARP2500 Sm Blue" );
        ceRateKnob.SetRange( 0.0, 1.0, 0.54660, false, 0 );
        ceRateKnob.SetKnobParams( 215, 145 );
        ceRateKnob.SetUnits( "Hz" );
        ceRateKnob.DisplayValueInPercent( false );
        ceRateKnob.SetKnobAdjustsRing( true );

        ceIntensityKnob = new VoltageKnob( "ceIntensityKnob", "CE-1 Intensity", this, 0.0, 1.0, 0.5 );
        AddComponent( ceIntensityKnob );
        ceIntensityKnob.SetWantsMouseNotifications( false );
        ceIntensityKnob.SetPosition( 45, 50 );
        ceIntensityKnob.SetSize( 25, 25 );
        ceIntensityKnob.SetSkin( "ARP2500 Sm Silver" );
        ceIntensityKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        ceIntensityKnob.SetKnobParams( 215, 145 );
        ceIntensityKnob.DisplayValueInPercent( true );
        ceIntensityKnob.SetKnobAdjustsRing( true );

        ceDriveKnob = new VoltageKnob( "ceDriveKnob", "CE-1 Drive", this, 0.0, 1.0, 0.58804 );
        AddComponent( ceDriveKnob );
        ceDriveKnob.SetWantsMouseNotifications( false );
        ceDriveKnob.SetPosition( 80, 50 );
        ceDriveKnob.SetSize( 25, 25 );
        ceDriveKnob.SetSkin( "ARP2500 Sm Red" );
        ceDriveKnob.SetRange( 0.0, 1.0, 0.58804, false, 0 );
        ceDriveKnob.SetKnobParams( 215, 145 );
        ceDriveKnob.DisplayValueInPercent( false );
        ceDriveKnob.SetKnobAdjustsRing( true );

        labelNoise = new VoltageLabel( "labelNoise", "Noise Label", this, "NOISE" );
        AddComponent( labelNoise );
        labelNoise.SetWantsMouseNotifications( false );
        labelNoise.SetPosition( 90, 152 );
        labelNoise.SetSize( 21, 13 );
        labelNoise.SetEditable( false, false );
        labelNoise.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelNoise.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNoise.SetColor( new Color( 42, 42, 42, 255 ) );
        labelNoise.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNoise.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNoise.SetBorderSize( 1 );
        labelNoise.SetMultiLineEdit( false );
        labelNoise.SetIsNumberEditor( false );
        labelNoise.SetNumberEditorRange( 0, 100 );
        labelNoise.SetNumberEditorInterval( 1 );
        labelNoise.SetNumberEditorUsesMouseWheel( false );
        labelNoise.SetHasCustomTextHoverColor( false );
        labelNoise.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNoise.SetFont( "Arial", 6, true, false );

        labelRate = new VoltageLabel( "labelRate", "Rate Label", this, "RATE" );
        AddComponent( labelRate );
        labelRate.SetWantsMouseNotifications( false );
        labelRate.SetPosition( 12, 75 );
        labelRate.SetSize( 21, 13 );
        labelRate.SetEditable( false, false );
        labelRate.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRate.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRate.SetColor( new Color( 42, 42, 42, 255 ) );
        labelRate.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRate.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRate.SetBorderSize( 1 );
        labelRate.SetMultiLineEdit( false );
        labelRate.SetIsNumberEditor( false );
        labelRate.SetNumberEditorRange( 0, 100 );
        labelRate.SetNumberEditorInterval( 1 );
        labelRate.SetNumberEditorUsesMouseWheel( false );
        labelRate.SetHasCustomTextHoverColor( false );
        labelRate.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRate.SetFont( "Arial", 6, true, false );

        labelEnsRate = new VoltageLabel( "labelEnsRate", "Ensemble Rate Label", this, "RATE" );
        AddComponent( labelEnsRate );
        labelEnsRate.SetWantsMouseNotifications( false );
        labelEnsRate.SetPosition( 20, 169 );
        labelEnsRate.SetSize( 21, 13 );
        labelEnsRate.SetEditable( false, false );
        labelEnsRate.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEnsRate.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEnsRate.SetColor( new Color( 42, 42, 42, 255 ) );
        labelEnsRate.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEnsRate.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEnsRate.SetBorderSize( 1 );
        labelEnsRate.SetMultiLineEdit( false );
        labelEnsRate.SetIsNumberEditor( false );
        labelEnsRate.SetNumberEditorRange( 0, 100 );
        labelEnsRate.SetNumberEditorInterval( 1 );
        labelEnsRate.SetNumberEditorUsesMouseWheel( false );
        labelEnsRate.SetHasCustomTextHoverColor( false );
        labelEnsRate.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEnsRate.SetFont( "Arial", 6, true, false );

        labelMix = new VoltageLabel( "labelMix", "Mix Label", this, "MIX" );
        AddComponent( labelMix );
        labelMix.SetWantsMouseNotifications( false );
        labelMix.SetPosition( 47, 256 );
        labelMix.SetSize( 21, 13 );
        labelMix.SetEditable( false, false );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMix.SetColor( new Color( 42, 42, 42, 255 ) );
        labelMix.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMix.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMix.SetBorderSize( 1 );
        labelMix.SetMultiLineEdit( false );
        labelMix.SetIsNumberEditor( false );
        labelMix.SetNumberEditorRange( 0, 100 );
        labelMix.SetNumberEditorInterval( 1 );
        labelMix.SetNumberEditorUsesMouseWheel( false );
        labelMix.SetHasCustomTextHoverColor( false );
        labelMix.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMix.SetFont( "Arial", 6, true, false );

        labelM = new VoltageLabel( "labelM", "M Label", this, "M" );
        AddComponent( labelM );
        labelM.SetWantsMouseNotifications( false );
        labelM.SetPosition( 0, 257 );
        labelM.SetSize( 21, 13 );
        labelM.SetEditable( false, false );
        labelM.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelM.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelM.SetColor( new Color( 42, 42, 42, 255 ) );
        labelM.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelM.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelM.SetBorderSize( 1 );
        labelM.SetMultiLineEdit( false );
        labelM.SetIsNumberEditor( false );
        labelM.SetNumberEditorRange( 0, 100 );
        labelM.SetNumberEditorInterval( 1 );
        labelM.SetNumberEditorUsesMouseWheel( false );
        labelM.SetHasCustomTextHoverColor( false );
        labelM.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelM.SetFont( "Arial", 6, true, false );

        labelS = new VoltageLabel( "labelS", "S Label", this, "S" );
        AddComponent( labelS );
        labelS.SetWantsMouseNotifications( false );
        labelS.SetPosition( 0, 286 );
        labelS.SetSize( 21, 13 );
        labelS.SetEditable( false, false );
        labelS.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelS.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelS.SetColor( new Color( 42, 42, 42, 255 ) );
        labelS.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelS.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelS.SetBorderSize( 1 );
        labelS.SetMultiLineEdit( false );
        labelS.SetIsNumberEditor( false );
        labelS.SetNumberEditorRange( 0, 100 );
        labelS.SetNumberEditorInterval( 1 );
        labelS.SetNumberEditorUsesMouseWheel( false );
        labelS.SetHasCustomTextHoverColor( false );
        labelS.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelS.SetFont( "Arial", 6, true, false );

        labelL = new VoltageLabel( "labelL", "L Label", this, "L" );
        AddComponent( labelL );
        labelL.SetWantsMouseNotifications( false );
        labelL.SetPosition( 22, 271 );
        labelL.SetSize( 21, 13 );
        labelL.SetEditable( false, false );
        labelL.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelL.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelL.SetColor( new Color( 42, 42, 42, 255 ) );
        labelL.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelL.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelL.SetBorderSize( 1 );
        labelL.SetMultiLineEdit( false );
        labelL.SetIsNumberEditor( false );
        labelL.SetNumberEditorRange( 0, 100 );
        labelL.SetNumberEditorInterval( 1 );
        labelL.SetNumberEditorUsesMouseWheel( false );
        labelL.SetHasCustomTextHoverColor( false );
        labelL.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelL.SetFont( "Arial Black", 11, true, false );

        labelR = new VoltageLabel( "labelR", "R Label", this, "R" );
        AddComponent( labelR );
        labelR.SetWantsMouseNotifications( false );
        labelR.SetPosition( 72, 271 );
        labelR.SetSize( 21, 13 );
        labelR.SetEditable( false, false );
        labelR.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelR.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelR.SetColor( new Color( 42, 42, 42, 255 ) );
        labelR.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelR.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelR.SetBorderSize( 1 );
        labelR.SetMultiLineEdit( false );
        labelR.SetIsNumberEditor( false );
        labelR.SetNumberEditorRange( 0, 100 );
        labelR.SetNumberEditorInterval( 1 );
        labelR.SetNumberEditorUsesMouseWheel( false );
        labelR.SetHasCustomTextHoverColor( false );
        labelR.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelR.SetFont( "Arial Black", 11, true, false );

        labelIntensity = new VoltageLabel( "labelIntensity", "Intensity Label", this, "INTENSITY" );
        AddComponent( labelIntensity );
        labelIntensity.SetWantsMouseNotifications( false );
        labelIntensity.SetPosition( 37, 75 );
        labelIntensity.SetSize( 41, 13 );
        labelIntensity.SetEditable( false, false );
        labelIntensity.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelIntensity.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelIntensity.SetColor( new Color( 42, 42, 42, 255 ) );
        labelIntensity.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelIntensity.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelIntensity.SetBorderSize( 1 );
        labelIntensity.SetMultiLineEdit( false );
        labelIntensity.SetIsNumberEditor( false );
        labelIntensity.SetNumberEditorRange( 0, 100 );
        labelIntensity.SetNumberEditorInterval( 1 );
        labelIntensity.SetNumberEditorUsesMouseWheel( false );
        labelIntensity.SetHasCustomTextHoverColor( false );
        labelIntensity.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelIntensity.SetFont( "Arial", 6, true, false );

        labelDepth = new VoltageLabel( "labelDepth", "Depth Label", this, "DEPTH" );
        AddComponent( labelDepth );
        labelDepth.SetWantsMouseNotifications( false );
        labelDepth.SetPosition( 75, 169 );
        labelDepth.SetSize( 21, 13 );
        labelDepth.SetEditable( false, false );
        labelDepth.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDepth.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDepth.SetColor( new Color( 42, 42, 42, 255 ) );
        labelDepth.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDepth.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDepth.SetBorderSize( 1 );
        labelDepth.SetMultiLineEdit( false );
        labelDepth.SetIsNumberEditor( false );
        labelDepth.SetNumberEditorRange( 0, 100 );
        labelDepth.SetNumberEditorInterval( 1 );
        labelDepth.SetNumberEditorUsesMouseWheel( false );
        labelDepth.SetHasCustomTextHoverColor( false );
        labelDepth.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDepth.SetFont( "Arial", 6, true, false );

        labelDrive = new VoltageLabel( "labelDrive", "Drive Label", this, "DRIVE" );
        AddComponent( labelDrive );
        labelDrive.SetWantsMouseNotifications( false );
        labelDrive.SetPosition( 82, 75 );
        labelDrive.SetSize( 21, 13 );
        labelDrive.SetEditable( false, false );
        labelDrive.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDrive.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDrive.SetColor( new Color( 42, 42, 42, 255 ) );
        labelDrive.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDrive.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDrive.SetBorderSize( 1 );
        labelDrive.SetMultiLineEdit( false );
        labelDrive.SetIsNumberEditor( false );
        labelDrive.SetNumberEditorRange( 0, 100 );
        labelDrive.SetNumberEditorInterval( 1 );
        labelDrive.SetNumberEditorUsesMouseWheel( false );
        labelDrive.SetHasCustomTextHoverColor( false );
        labelDrive.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDrive.SetFont( "Arial", 6, true, false );

        labelSummedLfos = new VoltageLabel( "labelSummedLfos", "Summed LFOs Label", this, "SUMMED LFOS" );
        AddComponent( labelSummedLfos );
        labelSummedLfos.SetWantsMouseNotifications( false );
        labelSummedLfos.SetPosition( 41, 223 );
        labelSummedLfos.SetSize( 60, 13 );
        labelSummedLfos.SetEditable( false, false );
        labelSummedLfos.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSummedLfos.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSummedLfos.SetColor( new Color( 42, 42, 42, 255 ) );
        labelSummedLfos.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSummedLfos.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSummedLfos.SetBorderSize( 1 );
        labelSummedLfos.SetMultiLineEdit( false );
        labelSummedLfos.SetIsNumberEditor( false );
        labelSummedLfos.SetNumberEditorRange( 0, 100 );
        labelSummedLfos.SetNumberEditorInterval( 1 );
        labelSummedLfos.SetNumberEditorUsesMouseWheel( false );
        labelSummedLfos.SetHasCustomTextHoverColor( false );
        labelSummedLfos.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSummedLfos.SetFont( "Arial", 6, true, false );

        labelModeChorus = new VoltageLabel( "labelModeChorus", "Chorus Mode Label", this, "CHO" );
        AddComponent( labelModeChorus );
        labelModeChorus.SetWantsMouseNotifications( false );
        labelModeChorus.SetPosition( 71, 310 );
        labelModeChorus.SetSize( 33, 17 );
        labelModeChorus.SetEditable( false, false );
        labelModeChorus.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelModeChorus.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelModeChorus.SetColor( new Color( 42, 42, 42, 255 ) );
        labelModeChorus.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelModeChorus.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelModeChorus.SetBorderSize( 1 );
        labelModeChorus.SetMultiLineEdit( false );
        labelModeChorus.SetIsNumberEditor( false );
        labelModeChorus.SetNumberEditorRange( 0, 100 );
        labelModeChorus.SetNumberEditorInterval( 1 );
        labelModeChorus.SetNumberEditorUsesMouseWheel( false );
        labelModeChorus.SetHasCustomTextHoverColor( false );
        labelModeChorus.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelModeChorus.SetFont( "Arial", 7, true, false );

        labelVibe = new VoltageLabel( "labelVibe", "Vibe Label", this, "VIBE" );
        AddComponent( labelVibe );
        labelVibe.SetWantsMouseNotifications( false );
        labelVibe.SetPosition( 69, 87 );
        labelVibe.SetSize( 33, 15 );
        labelVibe.SetEditable( false, false );
        labelVibe.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVibe.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVibe.SetColor( new Color( 42, 42, 42, 255 ) );
        labelVibe.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVibe.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVibe.SetBorderSize( 1 );
        labelVibe.SetMultiLineEdit( false );
        labelVibe.SetIsNumberEditor( false );
        labelVibe.SetNumberEditorRange( 0, 100 );
        labelVibe.SetNumberEditorInterval( 1 );
        labelVibe.SetNumberEditorUsesMouseWheel( false );
        labelVibe.SetHasCustomTextHoverColor( false );
        labelVibe.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVibe.SetFont( "Arial", 6, true, false );

        labelChorus = new VoltageLabel( "labelChorus", "Chorus Label", this, "CHORUS" );
        AddComponent( labelChorus );
        labelChorus.SetWantsMouseNotifications( false );
        labelChorus.SetPosition( 8, 87 );
        labelChorus.SetSize( 33, 15 );
        labelChorus.SetEditable( false, false );
        labelChorus.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelChorus.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelChorus.SetColor( new Color( 42, 42, 42, 255 ) );
        labelChorus.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelChorus.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelChorus.SetBorderSize( 1 );
        labelChorus.SetMultiLineEdit( false );
        labelChorus.SetIsNumberEditor( false );
        labelChorus.SetNumberEditorRange( 0, 100 );
        labelChorus.SetNumberEditorInterval( 1 );
        labelChorus.SetNumberEditorUsesMouseWheel( false );
        labelChorus.SetHasCustomTextHoverColor( false );
        labelChorus.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelChorus.SetFont( "Arial", 6, true, false );

        labelAverage = new VoltageLabel( "labelAverage", "Average Label", this, "AVERAGE" );
        AddComponent( labelAverage );
        labelAverage.SetWantsMouseNotifications( false );
        labelAverage.SetPosition( 34, 214 );
        labelAverage.SetSize( 60, 13 );
        labelAverage.SetEditable( false, false );
        labelAverage.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelAverage.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelAverage.SetColor( new Color( 42, 42, 42, 255 ) );
        labelAverage.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelAverage.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelAverage.SetBorderSize( 1 );
        labelAverage.SetMultiLineEdit( false );
        labelAverage.SetIsNumberEditor( false );
        labelAverage.SetNumberEditorRange( 0, 100 );
        labelAverage.SetNumberEditorInterval( 1 );
        labelAverage.SetNumberEditorUsesMouseWheel( false );
        labelAverage.SetHasCustomTextHoverColor( false );
        labelAverage.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelAverage.SetFont( "Arial", 6, true, false );

        labelParallelTaps = new VoltageLabel( "labelParallelTaps", "Parallel Taps Label", this, "PARALLEL TAPS" );
        AddComponent( labelParallelTaps );
        labelParallelTaps.SetWantsMouseNotifications( false );
        labelParallelTaps.SetPosition( 43, 205 );
        labelParallelTaps.SetSize( 60, 13 );
        labelParallelTaps.SetEditable( false, false );
        labelParallelTaps.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelParallelTaps.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelParallelTaps.SetColor( new Color( 42, 42, 42, 255 ) );
        labelParallelTaps.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelParallelTaps.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelParallelTaps.SetBorderSize( 1 );
        labelParallelTaps.SetMultiLineEdit( false );
        labelParallelTaps.SetIsNumberEditor( false );
        labelParallelTaps.SetNumberEditorRange( 0, 100 );
        labelParallelTaps.SetNumberEditorInterval( 1 );
        labelParallelTaps.SetNumberEditorUsesMouseWheel( false );
        labelParallelTaps.SetHasCustomTextHoverColor( false );
        labelParallelTaps.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelParallelTaps.SetFont( "Arial", 6, true, false );

        labelModeEnsemble = new VoltageLabel( "labelModeEnsemble", "Ensemble Mode Label", this, "ENS" );
        AddComponent( labelModeEnsemble );
        labelModeEnsemble.SetWantsMouseNotifications( false );
        labelModeEnsemble.SetPosition( 11, 310 );
        labelModeEnsemble.SetSize( 33, 17 );
        labelModeEnsemble.SetEditable( false, false );
        labelModeEnsemble.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelModeEnsemble.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelModeEnsemble.SetColor( new Color( 42, 42, 42, 255 ) );
        labelModeEnsemble.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelModeEnsemble.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelModeEnsemble.SetBorderSize( 1 );
        labelModeEnsemble.SetMultiLineEdit( false );
        labelModeEnsemble.SetIsNumberEditor( false );
        labelModeEnsemble.SetNumberEditorRange( 0, 100 );
        labelModeEnsemble.SetNumberEditorInterval( 1 );
        labelModeEnsemble.SetNumberEditorUsesMouseWheel( false );
        labelModeEnsemble.SetHasCustomTextHoverColor( false );
        labelModeEnsemble.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelModeEnsemble.SetFont( "Arial", 8, true, false );
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
        // Pull current knob/switch values into the core. Toggles arrive via Button_Changed
        // notifications after Initialize(), so the core's defaults (all buttons up) apply until then.
        syncControlsToCore();
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
        if (notification == ModuleNotifications.Knob_Changed) {
            handleKnobChanged(component, doubleValue);
        } else if (notification == ModuleNotifications.Button_Changed) {
            handleToggleChanged(component, doubleValue);
        } else if (notification == ModuleNotifications.Switch_Changed) {
            handleSwitchChanged(component);
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
        if (resumePending) {              // returning from host bypass: clear stale tails once
            resumePending = false;
            core.reset();
        }

        double left = readSignal(audioInLeft);
        double right = audioInRight.IsConnected() ? readSignal(audioInRight) : left;   // R normalled to L

        double newRateCv = rateCvIn.IsConnected() ? readSignal(rateCvIn) : 0.0;
        if (newRateCv != rateCv) {
            rateCv = newRateCv;
            applyRateScale();
        }
        double newDepthCv = depthCvIn.IsConnected() ? readSignal(depthCvIn) : 0.0;
        if (newDepthCv != depthCv) {
            depthCv = newDepthCv;
            applyDepthScale();
        }

        core.process(left * INV_FULL_SCALE_VOLTS, right * INV_FULL_SCALE_VOLTS);
        audioOutLeft.SetValue(core.outL * FULL_SCALE_VOLTS);
        audioOutRight.SetValue(core.outR * FULL_SCALE_VOLTS);
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
        // Direct routing; DSP histories stay frozen. The core is reset once on return (see ProcessSample).
        double left = readSignal(audioInLeft);
        double right = audioInRight.IsConnected() ? readSignal(audioInRight) : left;
        audioOutLeft.SetValue(left);
        audioOutRight.SetValue(right);
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
        if (component == rateKnob) {
            return String.format(Locale.ROOT, "LFO rate: x%.2f of the mode's rate (x%.2f to x%.0f)",
                    rateScaleFromPosition(rateKnob.GetValue()), RATE_SCALE_MIN, RATE_SCALE_MAX);
        }
        if (component == ceRateKnob) {
            return String.format(Locale.ROOT, "CE-1 LFO rate: %.2f Hz (%.2f to %.0f Hz)",
                    ceRateHzFromPosition(ceRateKnob.GetValue()), CE_RATE_MIN_HZ, CE_RATE_MAX_HZ);
        }
        if (component == ceDriveKnob) {
            return String.format(Locale.ROOT, "CE-1 preamp drive: x%.2f before the soft clip (x%.1f to x%.0f)",
                    ceDriveFromPosition(ceDriveKnob.GetValue()), CE_DRIVE_MIN, CE_DRIVE_MAX);
        }
        if (component == noiseKnob) {
            double level = noiseLevelFromPosition(noiseKnob.GetValue());
            return level <= 0.0 ? "BBD noise: off"
                    : String.format(Locale.ROOT, "BBD noise: %.1f dB re 5 V peak", 20.0 * Math.log10(level));
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
        if (component == rateKnob) {
            newValue = positionFromRateScale(newValue);
        } else if (component == ceRateKnob) {
            newValue = positionFromCeRateHz(newValue);
        } else if (component == ceDriveKnob) {
            newValue = positionFromCeDrive(newValue);
        } else if (component == noiseKnob) {
            newValue = positionFromNoiseDb(newValue);
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

        return null;
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
    private VoltageLabel labelModeEnsemble;
    private VoltageLabel labelParallelTaps;
    private VoltageLabel labelAverage;
    private VoltageLabel labelChorus;
    private VoltageLabel labelVibe;
    private VoltageLabel labelModeChorus;
    private VoltageLabel labelSummedLfos;
    private VoltageLabel labelDrive;
    private VoltageLabel labelDepth;
    private VoltageLabel labelIntensity;
    private VoltageLabel labelR;
    private VoltageLabel labelL;
    private VoltageLabel labelS;
    private VoltageLabel labelM;
    private VoltageLabel labelMix;
    private VoltageLabel labelEnsRate;
    private VoltageLabel labelRate;
    private VoltageLabel labelNoise;
    private VoltageKnob ceDriveKnob;
    private VoltageKnob ceIntensityKnob;
    private VoltageKnob ceRateKnob;
    private VoltageKnob noiseKnob;
    private VoltageKnob depthKnob;
    private VoltageKnob rateKnob;
    private VoltageKnob mixKnob;
    private VoltageSwitch vibratoSwitch;
    private VoltageSwitch inputSwitch;
    private VoltageSwitch combineSwitch;
    private VoltageSwitch modeSwitch;
    private VoltageToggle companderToggle;
    private VoltageToggle exclusiveToggle;
    private VoltageToggle dimensionButton4;
    private VoltageToggle dimensionButton3;
    private VoltageToggle dimensionButton2;
    private VoltageToggle dimensionButton1;
    private VoltageAudioJack depthCvIn;
    private VoltageAudioJack rateCvIn;
    private VoltageAudioJack audioOutRight;
    private VoltageAudioJack audioOutLeft;
    private VoltageAudioJack audioInRight;
    private VoltageAudioJack audioInLeft;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel labelChorusHeadingBase;
    private VoltageLabel labelChorusHeadingRed;
    private VoltageLabel labelEnsembleHeadingBase;
    private VoltageLabel labelEnsembleHeadingRed;


    //[user-code-and-variables]    Add your own variables and functions here

    // ---- Units and mappings ---------------------------------------------------------------
    private static final double FULL_SCALE_VOLTS = 5.0;                 // core 1.0 = 5 V
    private static final double INV_FULL_SCALE_VOLTS = 1.0 / FULL_SCALE_VOLTS;
    private static final double RATE_SCALE_MIN = 0.05, RATE_SCALE_MAX = 4.0;
    private static final double CE_RATE_MIN_HZ = 0.05, CE_RATE_MAX_HZ = 12.0;
    private static final double CE_DRIVE_MIN = 0.1, CE_DRIVE_MAX = 10.0;
    private static final double NOISE_MAX_LEVEL = 0.05;                 // core units (about -26 dB re 5 V)
    private static final double RATE_CV_OCTAVES_PER_VOLT = 0.4;         // +/-5 V = +/-2 octaves of rate
    private static final double DEPTH_CV_PER_VOLT = 0.2;                // +/-5 V = +/-1.0 added to depth

    // ---- Module state ---------------------------------------------------------------------
    private final ChorusCore core = new ChorusCore();
    private int pressedMask = 0;               // UI thread: Dimension buttons currently down
    private int lastPressedIndex = -1;         // UI thread: for Exclusive mode
    private boolean exclusiveMode = false;
    private volatile double rateBase = 1.0;    // knob-derived, before CV
    private volatile double depthBase = 1.0;
    private double rateCv = 0.0;               // audio thread
    private double depthCv = 0.0;
    private boolean resumePending = false;     // audio thread only (both callbacks run there)

    // ---- Control events -------------------------------------------------------------------
    private void handleKnobChanged(VoltageComponent component, double value) {
        if (component == mixKnob) {
            core.setMix(value);
        } else if (component == rateKnob) {
            rateBase = rateScaleFromPosition(value);
            applyRateScale();
        } else if (component == depthKnob) {
            depthBase = value;
            applyDepthScale();
        } else if (component == noiseKnob) {
            core.setNoiseLevel(noiseLevelFromPosition(value));
        } else if (component == ceRateKnob) {
            core.setCe1RateHz(ceRateHzFromPosition(value));
        } else if (component == ceIntensityKnob) {
            core.setCe1Intensity(value);
        } else if (component == ceDriveKnob) {
            core.setCe1Drive(ceDriveFromPosition(value));
        }
    }

    private void handleToggleChanged(VoltageComponent component, double value) {
        boolean down = value > 0.5;
        int index = buttonIndex(component);
        if (index >= 0) {
            if (down) {
                pressedMask |= (1 << index);
                lastPressedIndex = index;
                if (exclusiveMode) {
                    releaseOthers(index);
                }
            } else {
                pressedMask &= ~(1 << index);
                if (lastPressedIndex == index) {
                    lastPressedIndex = highestBit(pressedMask);
                }
            }
            core.setButtonMask(pressedMask);
        } else if (component == exclusiveToggle) {
            exclusiveMode = down;
            if (down && Integer.bitCount(pressedMask) > 1) {
                int keep = (lastPressedIndex >= 0 && ((pressedMask >> lastPressedIndex) & 1) != 0)
                        ? lastPressedIndex : Integer.numberOfTrailingZeros(pressedMask);
                releaseOthers(keep);
                core.setButtonMask(pressedMask);
            }
        } else if (component == companderToggle) {
            core.setCompanderOn(down);
        }
    }

    private void handleSwitchChanged(VoltageComponent component) {
        if (component == modeSwitch) {
            core.setMode(modeSwitch.GetValue() >= 0.5 ? ChorusCore.Mode.CE1 : ChorusCore.Mode.DIMENSION);
        } else if (component == combineSwitch) {
            core.setCombineLaw(combineLawFromPosition((int) Math.round(combineSwitch.GetValue())));
        } else if (component == inputSwitch) {
            core.setInputMono(inputSwitch.GetValue() >= 0.5);
        } else if (component == vibratoSwitch) {
            core.setCe1Vibrato(vibratoSwitch.GetValue() >= 0.5);
        }
    }

    private void syncControlsToCore() {
        handleKnobChanged(mixKnob, mixKnob.GetValue());
        handleKnobChanged(rateKnob, rateKnob.GetValue());
        handleKnobChanged(depthKnob, depthKnob.GetValue());
        handleKnobChanged(noiseKnob, noiseKnob.GetValue());
        handleKnobChanged(ceRateKnob, ceRateKnob.GetValue());
        handleKnobChanged(ceIntensityKnob, ceIntensityKnob.GetValue());
        handleKnobChanged(ceDriveKnob, ceDriveKnob.GetValue());
        handleSwitchChanged(modeSwitch);
        handleSwitchChanged(combineSwitch);
        handleSwitchChanged(inputSwitch);
        handleSwitchChanged(vibratoSwitch);
    }

    private void applyRateScale() {
        core.setRateScale(rateBase * Math.pow(2.0, RATE_CV_OCTAVES_PER_VOLT * rateCv));
    }

    private void applyDepthScale() {
        core.setDepthScale(depthBase + DEPTH_CV_PER_VOLT * depthCv);
    }

    // ---- Exclusive-mode helpers (the only place that calls SetValue on a toggle: verify natively) ----
    private int buttonIndex(VoltageComponent component) {
        if (component == dimensionButton1) return 0;
        if (component == dimensionButton2) return 1;
        if (component == dimensionButton3) return 2;
        if (component == dimensionButton4) return 3;
        return -1;
    }

    private VoltageToggle buttonByIndex(int index) {
        switch (index) {
            case 0: return dimensionButton1;
            case 1: return dimensionButton2;
            case 2: return dimensionButton3;
            default: return dimensionButton4;
        }
    }

    private void releaseOthers(int keep) {
        for (int i = 0; i < 4; i++) {
            if (i != keep && ((pressedMask >> i) & 1) != 0) {
                pressedMask &= ~(1 << i);          // clear first so a re-entrant notification is idempotent
                buttonByIndex(i).SetValue(0.0);
            }
        }
    }

    private static int highestBit(int mask) {
        return mask == 0 ? -1 : 31 - Integer.numberOfLeadingZeros(mask);
    }

    private static ChorusCore.CombineLaw combineLawFromPosition(int position) {
        if (position == 1) return ChorusCore.CombineLaw.AVERAGE;
        if (position == 2) return ChorusCore.CombineLaw.PARALLEL;
        return ChorusCore.CombineLaw.SUM_LFO;       // position 0 (default)
    }

    // ---- Signal and knob-mapping helpers ---------------------------------------------------
    private static double readSignal(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double value = jack.GetValue();
        return Double.isFinite(value) ? value : 0.0;
    }

    private static double clampParameter(double value, double minimum, double maximum, double fallback) {
        double safeValue = Double.isFinite(value) ? value : fallback;
        return Math.max(minimum, Math.min(maximum, safeValue));
    }

    private static double logMap(double position, double min, double max) {
        return min * Math.pow(max / min, clampParameter(position, 0.0, 1.0, 0.0));
    }

    private static double logUnmap(double value, double min, double max, double fallbackValue) {
        double v = clampParameter(value, min, max, fallbackValue);
        return Math.log(v / min) / Math.log(max / min);
    }

    static double rateScaleFromPosition(double p) { return logMap(p, RATE_SCALE_MIN, RATE_SCALE_MAX); }
    static double positionFromRateScale(double s) { return logUnmap(s, RATE_SCALE_MIN, RATE_SCALE_MAX, 1.0); }
    static double ceRateHzFromPosition(double p) { return logMap(p, CE_RATE_MIN_HZ, CE_RATE_MAX_HZ); }
    static double positionFromCeRateHz(double hz) { return logUnmap(hz, CE_RATE_MIN_HZ, CE_RATE_MAX_HZ, 1.0); }
    static double ceDriveFromPosition(double p) { return logMap(p, CE_DRIVE_MIN, CE_DRIVE_MAX); }
    static double positionFromCeDrive(double d) { return logUnmap(d, CE_DRIVE_MIN, CE_DRIVE_MAX, 1.5); }

    static double noiseLevelFromPosition(double p) {
        double q = clampParameter(p, 0.0, 1.0, 0.0);
        return NOISE_MAX_LEVEL * q * q;
    }

    /** Typed entry is in dB re 5 V peak; -90 dB or lower (or an invalid entry) means off. */
    static double positionFromNoiseDb(double db) {
        if (!Double.isFinite(db) || db <= -90.0) return 0.0;
        double level = Math.min(NOISE_MAX_LEVEL, Math.pow(10.0, db / 20.0));
        return Math.sqrt(level / NOISE_MAX_LEVEL);
    }

    // ---- DSP core (generated from src/ChorusCore.java by tools/assemble.py; edit that file) ----
    private static final class ChorusCore {

        public static final double SAMPLE_RATE = 48000.0;
        public static final double SAMPLE_PERIOD = 1.0 / SAMPLE_RATE;

        public enum Mode { DIMENSION, CE1 }

        /** How several simultaneously pressed Dimension buttons are combined. */
        public enum CombineLaw { AVERAGE, SUM_LFO, PARALLEL }

        // ---- Dimension mode tables ------------------------------------------------------

        /** [DOC] sweep minimum, ms, per button (service notes checking table, a). */
        private static final double[] DIM_MIN_MS = {8.0, 5.0, 6.0, 6.0};
        /** [DOC] sweep maximum, ms, per button (service notes checking table, b). */
        private static final double[] DIM_MAX_MS = {12.0, 10.0, 9.0, 9.0};
        /** [READ] LFO rate: schematic annotation "1,2 - 4 sec; 3,4 - 2 sec" read as the period. */
        private static final double[] DIM_RATE_HZ = {0.25, 0.25, 0.5, 0.5};
        /** [MODEL] distinct start phases so same-rate buttons are not identical when combined. */
        private static final double[] DIM_PHASE = {0.0, 0.25, 0.5, 0.75};

        private static final double MIN_DELAY_MS = 0.5;    // [MODEL] floor so a delay can never reach zero
        private static final double MAX_DELAY_MS = 20.0;
        private static final int BUFFER_SIZE = 2048;       // power of two, > 20 ms at 48 kHz + interpolation

        // [GUESS] wet-path bandwidth per mode
        private static final double DIM_BANDWIDTH_HZ = 12000.0;
        private static final double CE1_BANDWIDTH_HZ = 6500.0;

        // [MODEL] button weight smoothing (about 20 ms)
        private static final double WEIGHT_COEF = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.020);
        // [MODEL] manual control smoothing (handbook section 10 suggests about 5 ms)
        private static final double CONTROL_COEF = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.005);

        // [GUESS] CE-1 sweep around a center delay; intensity scales the half-range
        private static final double CE1_CENTER_MS = 3.8;
        private static final double CE1_MAX_HALF_RANGE_MS = 2.4;

        // ---- Parameters (written by the UI thread, read by the audio thread) --------------

        private volatile Mode mode = Mode.DIMENSION;
        private volatile CombineLaw combineLaw = CombineLaw.SUM_LFO;
        private volatile int buttonMask = 0;          // bit i = Dimension button i+1 is down
        private volatile boolean inputMono = false;
        private volatile double mix = 1.0;            // 0..1; 0 = exact dry
        private volatile double rateScale = 1.0;      // multiplies LFO rates, 0.05..4
        private volatile double depthScale = 1.0;     // scales sweep half-range, 0..2
        private volatile double noiseLevel = 0.0;     // normalized rms-ish amplitude, 0 = off
        private volatile boolean companderOn = false;
        private volatile double ce1RateHz = 1.0;
        private volatile double ce1Intensity = 0.5;   // 0..1
        private volatile boolean ce1Vibrato = false;
        private volatile double ce1Drive = 1.5;       // preamp gain ahead of soft clip

        public void setMode(Mode m) { mode = m; }
        public void setCombineLaw(CombineLaw law) { combineLaw = law; }
        public void setButtonMask(int mask) { buttonMask = mask & 0x0F; }
        public void setInputMono(boolean mono) { inputMono = mono; }
        public void setMix(double v) { mix = clampParameter(v, 0.0, 1.0, 1.0); }
        public void setRateScale(double v) { rateScale = clampParameter(v, 0.05, 4.0, 1.0); }
        public void setDepthScale(double v) { depthScale = clampParameter(v, 0.0, 2.0, 1.0); }
        public void setNoiseLevel(double v) { noiseLevel = clampParameter(v, 0.0, 0.05, 0.0); }
        public void setCompanderOn(boolean on) { companderOn = on; }
        public void setCe1RateHz(double v) { ce1RateHz = clampParameter(v, 0.05, 12.0, 1.0); }
        public void setCe1Intensity(double v) { ce1Intensity = clampParameter(v, 0.0, 1.0, 0.5); }
        public void setCe1Vibrato(boolean on) { ce1Vibrato = on; }
        public void setCe1Drive(double v) { ce1Drive = clampParameter(v, 0.1, 10.0, 1.5); }

        // ---- Outputs -----------------------------------------------------------------------

        public double outL;
        public double outR;

        // ---- State (audio thread only) -----------------------------------------------------

        private final Delay[] delay = { new Delay(), new Delay() };
        private final OnePole[][] preLp = new OnePole[2][2];
        private final OnePole[][] postLp = new OnePole[2][2];
        private final Compander[] compressor = { new Compander(), new Compander() };
        private final Compander[] expander = { new Compander(), new Compander() };
        private final double[] lfoPhase = new double[4];
        private double averagePhase = 0.0;
        private final double[] weight = new double[4];
        private double ce1Phase = 0.0;
        private double ce1RateSm = 1.0, ce1IntensitySm = 0.5;
        private long noiseState = 0x2545F4914F6CDD1DL;
        private Mode lastMode = Mode.DIMENSION;

        // per-sample scratch (fields, not locals, so nothing is allocated)
        private final double[] pa = new double[4], pb = new double[4], pk = new double[4];
        private final double[] pd0 = new double[4], pmid = new double[4], lfoOut = new double[4];
        private final double[] lastDelayMs = new double[2];   // test/diagnostic: delay used last sample

        public ChorusCore() {
            for (int c = 0; c < 2; c++) {
                for (int s = 0; s < 2; s++) {
                    preLp[c][s] = new OnePole();
                    postLp[c][s] = new OnePole();
                }
            }
            compressor[0].configure(true);  compressor[1].configure(true);
            expander[0].configure(false);   expander[1].configure(false);
            setBandwidth(DIM_BANDWIDTH_HZ);
            for (int i = 0; i < 4; i++) lfoPhase[i] = DIM_PHASE[i];
            averagePhase = DIM_PHASE[0];
        }

        /** Clears audio histories; keeps LFO phases (policy: free-running) and adopts current buttons. */
        public void reset() {
            for (int c = 0; c < 2; c++) {
                delay[c].clear();
                compressor[c].clear();
                expander[c].clear();
                for (int s = 0; s < 2; s++) { preLp[c][s].clear(); postLp[c][s].clear(); }
            }
            int mask = buttonMask;
            for (int i = 0; i < 4; i++) weight[i] = ((mask >> i) & 1);
            ce1RateSm = ce1RateHz;
            ce1IntensitySm = ce1Intensity;
        }

        /** Diagnostic: the delay (ms) used for channel 0 (left) or 1 (right) on the last sample. */
        public double getLastDelayMs(int channel) { return lastDelayMs[channel]; }

        // ---- Audio ---------------------------------------------------------------------------

        /** Inputs and outputs are normalized (1.0 = 5 V). Outputs land in outL / outR. */
        public void process(double inL, double inR) {
            inL = finiteOrZero(inL);
            inR = finiteOrZero(inR);
            Mode m = mode;
            if (m != lastMode) {                 // mode switch: clear wet-path history once
                lastMode = m;
                setBandwidth(m == Mode.DIMENSION ? DIM_BANDWIDTH_HZ : CE1_BANDWIDTH_HZ);
                reset();
            }
            if (m == Mode.DIMENSION) processDimension(inL, inR);
            else processCe1(inL);
        }

        private void processDimension(double inL, double inR) {
            final int mask = buttonMask;
            final CombineLaw law = combineLaw;
            final double rs = rateScale;
            final double ds = depthScale;
            final double mixNow = mix;
            final double noise = noiseLevel;
            final boolean compand = companderOn;

            // 1. smoothed button weights
            double wSum = 0.0;
            for (int i = 0; i < 4; i++) {
                weight[i] += WEIGHT_COEF * (((mask >> i) & 1) - weight[i]);
                wSum += weight[i];
            }
            final double engage = Math.min(1.0, wSum);

            // 2. per-button sweep parameters (depth scales the half-range about the center)
            for (int i = 0; i < 4; i++) {
                double mid = 0.5 * (DIM_MIN_MS[i] + DIM_MAX_MS[i]);
                pa[i] = Math.max(MIN_DELAY_MS, mid - ds * (mid - DIM_MIN_MS[i]));
                pb[i] = Math.min(MAX_DELAY_MS, mid + ds * (DIM_MAX_MS[i] - mid));
                pmid[i] = mid;
                pk[i] = (pb[i] - pa[i]) / (pb[i] + pa[i]);
                pd0[i] = 2.0 * pa[i] * pb[i] / (pa[i] + pb[i]);
                lfoPhase[i] = advance(lfoPhase[i], DIM_RATE_HZ[i] * rs);
                lfoOut[i] = triangle(lfoPhase[i]);
            }

            // AVERAGE law: one LFO, blended endpoints and rate
            double avgA = 0.0, avgB = 0.0, avgRate = 0.0;
            final double wSafe = Math.max(wSum, 1.0e-3);
            for (int i = 0; i < 4; i++) {
                double wn = weight[i] / wSafe;
                avgA += wn * pa[i];
                avgB += wn * pb[i];
                avgRate += wn * DIM_RATE_HZ[i];
            }
            averagePhase = advance(averagePhase, avgRate * rs);
            final double avgM = triangle(averagePhase);
            final double avgK = (avgB - avgA) / (avgB + avgA + 1.0e-9);
            final double avgD0 = 2.0 * avgA * avgB / (avgA + avgB + 1.0e-9);

            final double parallelDen = Math.max(1.0e-3, wSum < 1.0 ? wSum : Math.sqrt(wSum));
            final double sumDen = Math.sqrt(Math.max(1.0, wSum));

            for (int ch = 0; ch < 2; ch++) {
                final double sign = (ch == 0) ? 1.0 : -1.0;   // [DOC] second LFO phase is an inverter
                final double dry = inputMono ? 0.5 * (inL + inR) : (ch == 0 ? inL : inR);

                // write path: compress -> pre-filter -> delay line
                double x = dry;
                if (compand) x = compressor[ch].process(x);
                x = preLp[ch][1].lp(preLp[ch][0].lp(x));
                delay[ch].write(x);

                // read path by combine law
                double tap;
                double reportedMs;
                if (law == CombineLaw.AVERAGE) {
                    reportedMs = delayMs(avgD0, avgK, sign * avgM);
                    tap = delay[ch].read(reportedMs * (SAMPLE_RATE / 1000.0));
                } else if (law == CombineLaw.SUM_LFO) {
                    double center = 0.0, offsets = 0.0;
                    for (int i = 0; i < 4; i++) {
                        if (weight[i] < 1.0e-4) continue;
                        center += weight[i] * pmid[i];
                        offsets += weight[i] * (delayMs(pd0[i], pk[i], sign * lfoOut[i]) - pmid[i]);
                    }
                    reportedMs = center / wSafe + offsets / sumDen;
                    reportedMs = Math.max(MIN_DELAY_MS, Math.min(MAX_DELAY_MS, reportedMs));
                    tap = delay[ch].read(reportedMs * (SAMPLE_RATE / 1000.0));
                } else {                                      // PARALLEL: several taps, one shared line
                    double acc = 0.0, msAcc = 0.0;
                    for (int i = 0; i < 4; i++) {
                        if (weight[i] < 1.0e-4) continue;
                        double d = delayMs(pd0[i], pk[i], sign * lfoOut[i]);
                        d = Math.max(MIN_DELAY_MS, Math.min(MAX_DELAY_MS, d));
                        acc += weight[i] * delay[ch].read(d * (SAMPLE_RATE / 1000.0));
                        msAcc += weight[i] * d;
                    }
                    tap = acc / parallelDen;
                    reportedMs = msAcc / wSafe;
                }
                lastDelayMs[ch] = reportedMs;

                // BBD output: noise -> post-filter -> expand
                double y = tap;
                if (noise > 0.0) y += noise * nextNoise();
                y = postLp[ch][1].lp(postLp[ch][0].lp(y));
                if (compand) y = expander[ch].process(y);

                double wet = (law == CombineLaw.PARALLEL) ? y : y * engage;
                double out = dry * (1.0 - 0.5 * mixNow * engage) + 0.5 * mixNow * wet;
                if (ch == 0) outL = out; else outR = out;
            }
        }

        private void processCe1(double in) {
            ce1RateSm += CONTROL_COEF * (ce1RateHz - ce1RateSm);
            ce1IntensitySm += CONTROL_COEF * (ce1Intensity - ce1IntensitySm);
            ce1Phase = advance(ce1Phase, ce1RateSm);
            double m = triangle(ce1Phase);

            // [MODEL] preamp: gain then soft clip (CE-1 input stage has roughly 50x gain; see brief)
            double x = softClip(in * ce1Drive);

            double half = ce1IntensitySm * CE1_MAX_HALF_RANGE_MS;
            double a = Math.max(MIN_DELAY_MS, CE1_CENTER_MS - half);
            double b = CE1_CENTER_MS + half;
            double k = (b - a) / (b + a);
            double d0 = 2.0 * a * b / (a + b);
            double ms = delayMs(d0, k, m);

            double w = preLp[0][1].lp(preLp[0][0].lp(x));
            delay[0].write(w);
            double tap = delay[0].read(ms * (SAMPLE_RATE / 1000.0));
            double noise = noiseLevel;
            if (noise > 0.0) tap += noise * nextNoise();
            double wet = postLp[0][1].lp(postLp[0][0].lp(tap));
            lastDelayMs[0] = ms;
            lastDelayMs[1] = ms;

            double out = ce1Vibrato ? wet : 0.5 * (x + wet);
            outL = out;
            outR = out;
        }

        // ---- Helpers -------------------------------------------------------------------------

        /** [MODEL] clock-controlled BBD: delay proportional to 1/f_clk, f_clk linear in the LFO.
         *  With m = +1 -> a (min), m = -1 -> b (max), so a..b endpoints match the documented sweep. */
        private static double delayMs(double d0, double k, double m) {
            return d0 / (1.0 + k * m);
        }

        private static double advance(double phase, double rateHz) {
            phase += rateHz * SAMPLE_PERIOD;
            if (phase >= 1.0) phase -= Math.floor(phase);
            return phase;
        }

        /** Triangle from phase 0..1, range -1..+1, starting at -1. */
        private static double triangle(double phase) {
            return phase < 0.5 ? 4.0 * phase - 1.0 : 3.0 - 4.0 * phase;
        }

        /** Rational tanh-like soft clip, exact +/-1 beyond |x| = 3, continuous and monotone. */
        private static double softClip(double x) {
            if (x >= 3.0) return 1.0;
            if (x <= -3.0) return -1.0;
            double x2 = x * x;
            return x * (27.0 + x2) / (27.0 + 9.0 * x2);
        }

        private double nextNoise() {                          // xorshift64*, uniform -1..1
            long s = noiseState;
            s ^= s >>> 12; s ^= s << 25; s ^= s >>> 27;
            noiseState = s;
            long r = s * 0x2545F4914F6CDD1DL;
            return (r >>> 11) * (2.0 / 9007199254740992.0) - 1.0;
        }

        private void setBandwidth(double hz) {
            for (int c = 0; c < 2; c++) {
                for (int s = 0; s < 2; s++) {
                    preLp[c][s].setCutoff(hz);
                    postLp[c][s].setCutoff(hz);
                }
            }
        }

        private static double finiteOrZero(double v) { return Double.isFinite(v) ? v : 0.0; }

        private static double clampParameter(double value, double min, double max, double fallback) {
            double safe = Double.isFinite(value) ? value : fallback;
            return Math.max(min, Math.min(max, safe));
        }

        // ---- Primitives ------------------------------------------------------------------------

        /** Ring buffer with 4-point Hermite fractional read. */
        private static final class Delay {
            private final double[] buf = new double[BUFFER_SIZE];
            private static final int MASK = BUFFER_SIZE - 1;
            private int w = 0;

            void clear() { java.util.Arrays.fill(buf, 0.0); w = 0; }
            void write(double x) { buf[w] = x; w = (w + 1) & MASK; }

            /** Returns the input from delaySamples ago; 0 is the sample just written. Needs >= 2. */
            double read(double delaySamples) {
                double d = delaySamples;
                int di = (int) d;
                double f = d - di;
                int i1 = (w - 1 - di) & MASK;
                double y0 = buf[(i1 + 1) & MASK];
                double y1 = buf[i1];
                double y2 = buf[(i1 - 1) & MASK];
                double y3 = buf[(i1 - 2) & MASK];
                double c1 = 0.5 * (y2 - y0);
                double c2 = y0 - 2.5 * y1 + 2.0 * y2 - 0.5 * y3;
                double c3 = 0.5 * (y3 - y0) + 1.5 * (y1 - y2);
                return ((c3 * f + c2) * f + c1) * f + y1;
            }
        }

        private static final class OnePole {
            private double a = 0.5, z = 0.0;
            void setCutoff(double fc) { a = 1.0 - Math.exp(-2.0 * Math.PI * fc / SAMPLE_RATE); }
            void clear() { z = 0.0; }
            double lp(double x) { z += a * (x - z); return z; }
        }

        /**
         * [MODEL] NE570-style 2:1 compressor / 1:2 expander half. Peak-ish envelope follower.
         * Floors are matched (expander floor = sqrt(compressor floor)) so the pair is unity for any level.
         * Attack/release are [GUESS].
         */
        static final class Compander {
            private static final double C_FLOOR = 1.0e-3;
            private static final double E_FLOOR = Math.sqrt(C_FLOOR);
            private static final double ATTACK = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.005);
            private static final double RELEASE = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.030);
            private double env = 0.0;
            private boolean isCompressor = true;

            void configure(boolean compressor) { isCompressor = compressor; }
            void clear() { env = 0.0; }

            double process(double x) {
                double a = Math.abs(x);
                env += (a > env ? ATTACK : RELEASE) * (a - env);
                if (isCompressor) return x / Math.sqrt(Math.max(env, C_FLOOR));
                return x * Math.max(env, E_FLOOR);
            }
        }
    }
    //[/user-code-and-variables]
}

 