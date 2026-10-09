package com.insectlabs.deeptone;


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


public class deeptone extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public deeptone( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "1A-30 - Deep Tone Generator - Modulator ", ModuleType.ModuleType_Oscillators, 3.2 );

        InitializeControls();
        InitializeControls2();


        canBeBypassed = true;
        SetSkin( "07f9c6a8b9874834b2afa60a3454d1df" );
    }

void InitializeControls()
{

        scale1A30DeepToneGenModLabel2 = new VoltageLabel( "scale1A30DeepToneGenModLabel2", "1A-30 deep tone gen/mod Label", this, "1A-30" );
        AddComponent( scale1A30DeepToneGenModLabel2 );
        scale1A30DeepToneGenModLabel2.SetWantsMouseNotifications( false );
        scale1A30DeepToneGenModLabel2.SetPosition( 33, 335 );
        scale1A30DeepToneGenModLabel2.SetSize( 194, 13 );
        scale1A30DeepToneGenModLabel2.SetEditable( false, false );
        scale1A30DeepToneGenModLabel2.SetJustificationFlags( VoltageLabel.Justification.Right );
        scale1A30DeepToneGenModLabel2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale1A30DeepToneGenModLabel2.SetColor( new Color( 0, 0, 0, 255 ) );
        scale1A30DeepToneGenModLabel2.SetBkColor( new Color( 85, 85, 85, 0 ) );
        scale1A30DeepToneGenModLabel2.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        scale1A30DeepToneGenModLabel2.SetBorderSize( 0 );
        scale1A30DeepToneGenModLabel2.SetMultiLineEdit( false );
        scale1A30DeepToneGenModLabel2.SetIsNumberEditor( false );
        scale1A30DeepToneGenModLabel2.SetNumberEditorRange( 0, 100 );
        scale1A30DeepToneGenModLabel2.SetNumberEditorInterval( 1 );
        scale1A30DeepToneGenModLabel2.SetNumberEditorUsesMouseWheel( false );
        scale1A30DeepToneGenModLabel2.SetHasCustomTextHoverColor( false );
        scale1A30DeepToneGenModLabel2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale1A30DeepToneGenModLabel2.SetFont( "Courier New", 13, true, false );

        courtesyOutput = new VoltageAudioJack( "courtesyOutput", "Courtesy Output", this, JackType.JackType_AudioOutput );
        AddComponent( courtesyOutput );
        courtesyOutput.SetWantsMouseNotifications( false );
        courtesyOutput.SetPosition( 166, 268 );
        courtesyOutput.SetSize( 25, 25 );
        courtesyOutput.SetSkin( "Mini Jack 25px" );

        mainOutput = new VoltageAudioJack( "mainOutput", "Main Output", this, JackType.JackType_AudioOutput );
        AddComponent( mainOutput );
        mainOutput.SetWantsMouseNotifications( false );
        mainOutput.SetPosition( 160, 227 );
        mainOutput.SetSize( 37, 37 );
        mainOutput.SetSkin( "Rotated Half" );

        swellButton = new VoltageButton( "swellButton", "Swell", this );
        AddComponent( swellButton );
        swellButton.SetWantsMouseNotifications( false );
        swellButton.SetPosition( 42, 297 );
        swellButton.SetSize( 20, 20 );
        swellButton.SetSkin( "2500 Square Big" );
        swellButton.ShowOverlay( false );
        swellButton.SetOverlayText( "" );
        swellButton.SetAutoRepeat( false );

        beatButton = new VoltageButton( "beatButton", "Beat", this );
        AddComponent( beatButton );
        beatButton.SetWantsMouseNotifications( false );
        beatButton.SetPosition( 42, 272 );
        beatButton.SetSize( 20, 20 );
        beatButton.SetSkin( "2500 Square Big" );
        beatButton.ShowOverlay( false );
        beatButton.SetOverlayText( "" );
        beatButton.SetAutoRepeat( false );

        toneButton = new VoltageButton( "toneButton", "Tone", this );
        AddComponent( toneButton );
        toneButton.SetWantsMouseNotifications( false );
        toneButton.SetPosition( 42, 247 );
        toneButton.SetSize( 20, 20 );
        toneButton.SetSkin( "2500 Square Big" );
        toneButton.ShowOverlay( false );
        toneButton.SetOverlayText( "" );
        toneButton.SetAutoRepeat( false );

        toneLabel4 = new VoltageLabel( "toneLabel4", "Tone Label", this, "Tone" );
        AddComponent( toneLabel4 );
        toneLabel4.SetWantsMouseNotifications( false );
        toneLabel4.SetPosition( 67, 247 );
        toneLabel4.SetSize( 40, 20 );
        toneLabel4.SetEditable( false, false );
        toneLabel4.SetJustificationFlags( VoltageLabel.Justification.Left );
        toneLabel4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        toneLabel4.SetColor( new Color( 0, 0, 0, 255 ) );
        toneLabel4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        toneLabel4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        toneLabel4.SetBorderSize( 1 );
        toneLabel4.SetMultiLineEdit( false );
        toneLabel4.SetIsNumberEditor( false );
        toneLabel4.SetNumberEditorRange( 0, 100 );
        toneLabel4.SetNumberEditorInterval( 1 );
        toneLabel4.SetNumberEditorUsesMouseWheel( false );
        toneLabel4.SetHasCustomTextHoverColor( false );
        toneLabel4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        toneLabel4.SetFont( "<Sans-Serif>", 9, true, false );

        beatLabel5 = new VoltageLabel( "beatLabel5", "Beat Label", this, "Beat" );
        AddComponent( beatLabel5 );
        beatLabel5.SetWantsMouseNotifications( false );
        beatLabel5.SetPosition( 67, 272 );
        beatLabel5.SetSize( 40, 20 );
        beatLabel5.SetEditable( false, false );
        beatLabel5.SetJustificationFlags( VoltageLabel.Justification.Left );
        beatLabel5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        beatLabel5.SetColor( new Color( 0, 0, 0, 255 ) );
        beatLabel5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        beatLabel5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        beatLabel5.SetBorderSize( 1 );
        beatLabel5.SetMultiLineEdit( false );
        beatLabel5.SetIsNumberEditor( false );
        beatLabel5.SetNumberEditorRange( 0, 100 );
        beatLabel5.SetNumberEditorInterval( 1 );
        beatLabel5.SetNumberEditorUsesMouseWheel( false );
        beatLabel5.SetHasCustomTextHoverColor( false );
        beatLabel5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        beatLabel5.SetFont( "<Sans-Serif>", 9, true, false );

        swellLabel6 = new VoltageLabel( "swellLabel6", "Swell Label", this, "Swell" );
        AddComponent( swellLabel6 );
        swellLabel6.SetWantsMouseNotifications( false );
        swellLabel6.SetPosition( 67, 297 );
        swellLabel6.SetSize( 40, 20 );
        swellLabel6.SetEditable( false, false );
        swellLabel6.SetJustificationFlags( VoltageLabel.Justification.Left );
        swellLabel6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        swellLabel6.SetColor( new Color( 0, 0, 0, 255 ) );
        swellLabel6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        swellLabel6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        swellLabel6.SetBorderSize( 1 );
        swellLabel6.SetMultiLineEdit( false );
        swellLabel6.SetIsNumberEditor( false );
        swellLabel6.SetNumberEditorRange( 0, 100 );
        swellLabel6.SetNumberEditorInterval( 1 );
        swellLabel6.SetNumberEditorUsesMouseWheel( false );
        swellLabel6.SetHasCustomTextHoverColor( false );
        swellLabel6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        swellLabel6.SetFont( "<Sans-Serif>", 9, true, false );

        amplitudeKnob = new VoltageKnob( "amplitudeKnob", "Amplitude", this, 0.0, 15.0, 0.0 );
        AddComponent( amplitudeKnob );
        amplitudeKnob.SetWantsMouseNotifications( false );
        amplitudeKnob.SetPosition( 125, 232 );
        amplitudeKnob.SetSize( 25, 25 );
        amplitudeKnob.SetSkin( "TR Large" );
        amplitudeKnob.SetRange( 0.0, 15.0, 0.0, false, 0 );
        amplitudeKnob.SetKnobParams( 215, 145 );
        amplitudeKnob.DisplayValueInPercent( true );
        amplitudeKnob.SetKnobAdjustsRing( true );

        wholeKnob = new VoltageKnob( "wholeKnob", "Whole Frequency", this, 0, 10, 2.0 );
        AddComponent( wholeKnob );
        wholeKnob.SetWantsMouseNotifications( false );
        wholeKnob.SetPosition( 95, 97 );
        wholeKnob.SetSize( 35, 35 );
        wholeKnob.SetSkin( "TR Large (white tick)" );
        wholeKnob.SetRange( 0, 10, 2.0, false, 11 );
        wholeKnob.SetKnobParams( 245, 115 );
        wholeKnob.DisplayValueInPercent( false );
        wholeKnob.SetKnobAdjustsRing( true );

        scale00Label7 = new VoltageLabel( "scale00Label7", "00 Label", this, "00" );
        AddComponent( scale00Label7 );
        scale00Label7.SetWantsMouseNotifications( false );
        scale00Label7.SetPosition( 85, 122 );
        scale00Label7.SetSize( 16, 8 );
        scale00Label7.SetEditable( false, false );
        scale00Label7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale00Label7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale00Label7.SetColor( new Color( 0, 0, 0, 255 ) );
        scale00Label7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale00Label7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale00Label7.SetBorderSize( 1 );
        scale00Label7.SetMultiLineEdit( false );
        scale00Label7.SetIsNumberEditor( false );
        scale00Label7.SetNumberEditorRange( 0, 100 );
        scale00Label7.SetNumberEditorInterval( 1 );
        scale00Label7.SetNumberEditorUsesMouseWheel( false );
        scale00Label7.SetHasCustomTextHoverColor( false );
        scale00Label7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale00Label7.SetFont( "Arial", 8, true, false );

        scale10Label8 = new VoltageLabel( "scale10Label8", "10 Label", this, "10" );
        AddComponent( scale10Label8 );
        scale10Label8.SetWantsMouseNotifications( false );
        scale10Label8.SetPosition( 124, 122 );
        scale10Label8.SetSize( 16, 8 );
        scale10Label8.SetEditable( false, false );
        scale10Label8.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale10Label8.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale10Label8.SetColor( new Color( 0, 0, 0, 255 ) );
        scale10Label8.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale10Label8.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale10Label8.SetBorderSize( 1 );
        scale10Label8.SetMultiLineEdit( false );
        scale10Label8.SetIsNumberEditor( false );
        scale10Label8.SetNumberEditorRange( 0, 100 );
        scale10Label8.SetNumberEditorInterval( 1 );
        scale10Label8.SetNumberEditorUsesMouseWheel( false );
        scale10Label8.SetHasCustomTextHoverColor( false );
        scale10Label8.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale10Label8.SetFont( "Arial", 8, true, false );

        scale09Label9 = new VoltageLabel( "scale09Label9", "09 Label", this, "09" );
        AddComponent( scale09Label9 );
        scale09Label9.SetWantsMouseNotifications( false );
        scale09Label9.SetPosition( 127, 114 );
        scale09Label9.SetSize( 16, 8 );
        scale09Label9.SetEditable( false, false );
        scale09Label9.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale09Label9.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale09Label9.SetColor( new Color( 0, 0, 0, 255 ) );
        scale09Label9.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale09Label9.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale09Label9.SetBorderSize( 1 );
        scale09Label9.SetMultiLineEdit( false );
        scale09Label9.SetIsNumberEditor( false );
        scale09Label9.SetNumberEditorRange( 0, 100 );
        scale09Label9.SetNumberEditorInterval( 1 );
        scale09Label9.SetNumberEditorUsesMouseWheel( false );
        scale09Label9.SetHasCustomTextHoverColor( false );
        scale09Label9.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale09Label9.SetFont( "Arial", 8, true, false );

        scale01Label10 = new VoltageLabel( "scale01Label10", "01 Label", this, "01" );
        AddComponent( scale01Label10 );
        scale01Label10.SetWantsMouseNotifications( false );
        scale01Label10.SetPosition( 82, 114 );
        scale01Label10.SetSize( 16, 8 );
        scale01Label10.SetEditable( false, false );
        scale01Label10.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale01Label10.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale01Label10.SetColor( new Color( 0, 0, 0, 255 ) );
        scale01Label10.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale01Label10.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale01Label10.SetBorderSize( 1 );
        scale01Label10.SetMultiLineEdit( false );
        scale01Label10.SetIsNumberEditor( false );
        scale01Label10.SetNumberEditorRange( 0, 100 );
        scale01Label10.SetNumberEditorInterval( 1 );
        scale01Label10.SetNumberEditorUsesMouseWheel( false );
        scale01Label10.SetHasCustomTextHoverColor( false );
        scale01Label10.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale01Label10.SetFont( "Arial", 8, true, false );

        scale02Label11 = new VoltageLabel( "scale02Label11", "02 Label", this, "02" );
        AddComponent( scale02Label11 );
        scale02Label11.SetWantsMouseNotifications( false );
        scale02Label11.SetPosition( 82, 105 );
        scale02Label11.SetSize( 16, 8 );
        scale02Label11.SetEditable( false, false );
        scale02Label11.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale02Label11.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale02Label11.SetColor( new Color( 0, 0, 0, 255 ) );
        scale02Label11.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale02Label11.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale02Label11.SetBorderSize( 1 );
        scale02Label11.SetMultiLineEdit( false );
        scale02Label11.SetIsNumberEditor( false );
        scale02Label11.SetNumberEditorRange( 0, 100 );
        scale02Label11.SetNumberEditorInterval( 1 );
        scale02Label11.SetNumberEditorUsesMouseWheel( false );
        scale02Label11.SetHasCustomTextHoverColor( false );
        scale02Label11.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale02Label11.SetFont( "Arial", 8, true, false );

        scale08Label13 = new VoltageLabel( "scale08Label13", "08 Label", this, "08" );
        AddComponent( scale08Label13 );
        scale08Label13.SetWantsMouseNotifications( false );
        scale08Label13.SetPosition( 127, 105 );
        scale08Label13.SetSize( 16, 8 );
        scale08Label13.SetEditable( false, false );
        scale08Label13.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale08Label13.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale08Label13.SetColor( new Color( 0, 0, 0, 255 ) );
        scale08Label13.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale08Label13.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale08Label13.SetBorderSize( 1 );
        scale08Label13.SetMultiLineEdit( false );
        scale08Label13.SetIsNumberEditor( false );
        scale08Label13.SetNumberEditorRange( 0, 100 );
        scale08Label13.SetNumberEditorInterval( 1 );
        scale08Label13.SetNumberEditorUsesMouseWheel( false );
        scale08Label13.SetHasCustomTextHoverColor( false );
        scale08Label13.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale08Label13.SetFont( "Arial", 8, true, false );

        scale07Label14 = new VoltageLabel( "scale07Label14", "07 Label", this, "07" );
        AddComponent( scale07Label14 );
        scale07Label14.SetWantsMouseNotifications( false );
        scale07Label14.SetPosition( 124, 97 );
        scale07Label14.SetSize( 16, 8 );
        scale07Label14.SetEditable( false, false );
        scale07Label14.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale07Label14.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale07Label14.SetColor( new Color( 0, 0, 0, 255 ) );
        scale07Label14.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale07Label14.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale07Label14.SetBorderSize( 1 );
        scale07Label14.SetMultiLineEdit( false );
        scale07Label14.SetIsNumberEditor( false );
        scale07Label14.SetNumberEditorRange( 0, 100 );
        scale07Label14.SetNumberEditorInterval( 1 );
        scale07Label14.SetNumberEditorUsesMouseWheel( false );
        scale07Label14.SetHasCustomTextHoverColor( false );
        scale07Label14.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale07Label14.SetFont( "Arial", 8, true, false );

        scale03Label15 = new VoltageLabel( "scale03Label15", "03 Label", this, "03" );
        AddComponent( scale03Label15 );
        scale03Label15.SetWantsMouseNotifications( false );
        scale03Label15.SetPosition( 85, 97 );
        scale03Label15.SetSize( 16, 8 );
        scale03Label15.SetEditable( false, false );
        scale03Label15.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale03Label15.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale03Label15.SetColor( new Color( 0, 0, 0, 255 ) );
        scale03Label15.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale03Label15.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale03Label15.SetBorderSize( 1 );
        scale03Label15.SetMultiLineEdit( false );
        scale03Label15.SetIsNumberEditor( false );
        scale03Label15.SetNumberEditorRange( 0, 100 );
        scale03Label15.SetNumberEditorInterval( 1 );
        scale03Label15.SetNumberEditorUsesMouseWheel( false );
        scale03Label15.SetHasCustomTextHoverColor( false );
        scale03Label15.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale03Label15.SetFont( "Arial", 8, true, false );

        scale04Label16 = new VoltageLabel( "scale04Label16", "04 Label", this, "04" );
        AddComponent( scale04Label16 );
        scale04Label16.SetWantsMouseNotifications( false );
        scale04Label16.SetPosition( 93, 90 );
        scale04Label16.SetSize( 16, 8 );
        scale04Label16.SetEditable( false, false );
        scale04Label16.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale04Label16.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale04Label16.SetColor( new Color( 0, 0, 0, 255 ) );
        scale04Label16.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale04Label16.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale04Label16.SetBorderSize( 1 );
        scale04Label16.SetMultiLineEdit( false );
        scale04Label16.SetIsNumberEditor( false );
        scale04Label16.SetNumberEditorRange( 0, 100 );
        scale04Label16.SetNumberEditorInterval( 1 );
        scale04Label16.SetNumberEditorUsesMouseWheel( false );
        scale04Label16.SetHasCustomTextHoverColor( false );
        scale04Label16.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale04Label16.SetFont( "Arial", 8, true, false );

        scale06Label17 = new VoltageLabel( "scale06Label17", "06 Label", this, "06" );
        AddComponent( scale06Label17 );
        scale06Label17.SetWantsMouseNotifications( false );
        scale06Label17.SetPosition( 116, 90 );
        scale06Label17.SetSize( 16, 8 );
        scale06Label17.SetEditable( false, false );
        scale06Label17.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale06Label17.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale06Label17.SetColor( new Color( 0, 0, 0, 255 ) );
        scale06Label17.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale06Label17.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale06Label17.SetBorderSize( 1 );
        scale06Label17.SetMultiLineEdit( false );
        scale06Label17.SetIsNumberEditor( false );
        scale06Label17.SetNumberEditorRange( 0, 100 );
        scale06Label17.SetNumberEditorInterval( 1 );
        scale06Label17.SetNumberEditorUsesMouseWheel( false );
        scale06Label17.SetHasCustomTextHoverColor( false );
        scale06Label17.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale06Label17.SetFont( "Arial", 8, true, false );

        scale05Label18 = new VoltageLabel( "scale05Label18", "05 Label", this, "05" );
        AddComponent( scale05Label18 );
        scale05Label18.SetWantsMouseNotifications( false );
        scale05Label18.SetPosition( 105, 87 );
        scale05Label18.SetSize( 16, 8 );
        scale05Label18.SetEditable( false, false );
        scale05Label18.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale05Label18.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale05Label18.SetColor( new Color( 0, 0, 0, 255 ) );
        scale05Label18.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale05Label18.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale05Label18.SetBorderSize( 1 );
        scale05Label18.SetMultiLineEdit( false );
        scale05Label18.SetIsNumberEditor( false );
        scale05Label18.SetNumberEditorRange( 0, 100 );
        scale05Label18.SetNumberEditorInterval( 1 );
        scale05Label18.SetNumberEditorUsesMouseWheel( false );
        scale05Label18.SetHasCustomTextHoverColor( false );
        scale05Label18.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale05Label18.SetFont( "Arial", 8, true, false );

        fractionKnob = new VoltageKnob( "fractionKnob", "Fraction Frequency", this, 0.0, 1.0, 0.616255653005986 );
        AddComponent( fractionKnob );
        fractionKnob.SetWantsMouseNotifications( false );
        fractionKnob.SetPosition( 160, 97 );
        fractionKnob.SetSize( 35, 35 );
        fractionKnob.SetSkin( "TR Large (white tick)" );
        fractionKnob.SetRange( 0.0, 1.0, 0.616255653005986, false, 0 );
        fractionKnob.SetKnobParams( 245, 115 );
        fractionKnob.DisplayValueInPercent( false );
        fractionKnob.SetKnobAdjustsRing( true );

        scale0Label12 = new VoltageLabel( "scale0Label12", "0 Label", this, "0" );
        AddComponent( scale0Label12 );
        scale0Label12.SetWantsMouseNotifications( false );
        scale0Label12.SetPosition( 150, 120 );
        scale0Label12.SetSize( 16, 8 );
        scale0Label12.SetEditable( false, false );
        scale0Label12.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale0Label12.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale0Label12.SetColor( new Color( 0, 0, 0, 255 ) );
        scale0Label12.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale0Label12.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale0Label12.SetBorderSize( 1 );
        scale0Label12.SetMultiLineEdit( false );
        scale0Label12.SetIsNumberEditor( false );
        scale0Label12.SetNumberEditorRange( 0, 100 );
        scale0Label12.SetNumberEditorInterval( 1 );
        scale0Label12.SetNumberEditorUsesMouseWheel( false );
        scale0Label12.SetHasCustomTextHoverColor( false );
        scale0Label12.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale0Label12.SetFont( "Arial", 8, true, false );

        scale1Label19 = new VoltageLabel( "scale1Label19", "1 Label", this, "1" );
        AddComponent( scale1Label19 );
        scale1Label19.SetWantsMouseNotifications( false );
        scale1Label19.SetPosition( 189, 120 );
        scale1Label19.SetSize( 16, 8 );
        scale1Label19.SetEditable( false, false );
        scale1Label19.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale1Label19.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale1Label19.SetColor( new Color( 0, 0, 0, 255 ) );
        scale1Label19.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale1Label19.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale1Label19.SetBorderSize( 1 );
        scale1Label19.SetMultiLineEdit( false );
        scale1Label19.SetIsNumberEditor( false );
        scale1Label19.SetNumberEditorRange( 0, 100 );
        scale1Label19.SetNumberEditorInterval( 1 );
        scale1Label19.SetNumberEditorUsesMouseWheel( false );
        scale1Label19.SetHasCustomTextHoverColor( false );
        scale1Label19.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale1Label19.SetFont( "Arial", 8, true, false );

        scale2Label22 = new VoltageLabel( "scale2Label22", ".2 Label", this, ".2" );
        AddComponent( scale2Label22 );
        scale2Label22.SetWantsMouseNotifications( false );
        scale2Label22.SetPosition( 148, 103 );
        scale2Label22.SetSize( 16, 8 );
        scale2Label22.SetEditable( false, false );
        scale2Label22.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale2Label22.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale2Label22.SetColor( new Color( 0, 0, 0, 255 ) );
        scale2Label22.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale2Label22.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale2Label22.SetBorderSize( 1 );
        scale2Label22.SetMultiLineEdit( false );
        scale2Label22.SetIsNumberEditor( false );
        scale2Label22.SetNumberEditorRange( 0, 100 );
        scale2Label22.SetNumberEditorInterval( 1 );
        scale2Label22.SetNumberEditorUsesMouseWheel( false );
        scale2Label22.SetHasCustomTextHoverColor( false );
        scale2Label22.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale2Label22.SetFont( "Arial", 8, true, false );

        scale8Label23 = new VoltageLabel( "scale8Label23", ".8 Label", this, ".8" );
        AddComponent( scale8Label23 );
        scale8Label23.SetWantsMouseNotifications( false );
        scale8Label23.SetPosition( 191, 103 );
        scale8Label23.SetSize( 16, 8 );
        scale8Label23.SetEditable( false, false );
        scale8Label23.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale8Label23.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale8Label23.SetColor( new Color( 0, 0, 0, 255 ) );
        scale8Label23.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale8Label23.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale8Label23.SetBorderSize( 1 );
        scale8Label23.SetMultiLineEdit( false );
        scale8Label23.SetIsNumberEditor( false );
        scale8Label23.SetNumberEditorRange( 0, 100 );
        scale8Label23.SetNumberEditorInterval( 1 );
        scale8Label23.SetNumberEditorUsesMouseWheel( false );
        scale8Label23.SetHasCustomTextHoverColor( false );
        scale8Label23.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale8Label23.SetFont( "Arial", 8, true, false );

        scale5Label28 = new VoltageLabel( "scale5Label28", ".5 Label", this, ".5" );
        AddComponent( scale5Label28 );
        scale5Label28.SetWantsMouseNotifications( false );
        scale5Label28.SetPosition( 169, 87 );
        scale5Label28.SetSize( 16, 8 );
        scale5Label28.SetEditable( false, false );
        scale5Label28.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale5Label28.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale5Label28.SetColor( new Color( 0, 0, 0, 255 ) );
        scale5Label28.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale5Label28.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale5Label28.SetBorderSize( 1 );
        scale5Label28.SetMultiLineEdit( false );
        scale5Label28.SetIsNumberEditor( false );
        scale5Label28.SetNumberEditorRange( 0, 100 );
        scale5Label28.SetNumberEditorInterval( 1 );
        scale5Label28.SetNumberEditorUsesMouseWheel( false );
        scale5Label28.SetHasCustomTextHoverColor( false );
        scale5Label28.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale5Label28.SetFont( "Arial", 8, true, false );

        multiplierKnob = new VoltageKnob( "multiplierKnob", "Frequency Multiplier", this, 1, 3, 3.0 );
        AddComponent( multiplierKnob );
        multiplierKnob.SetWantsMouseNotifications( false );
        multiplierKnob.SetPosition( 35, 97 );
        multiplierKnob.SetSize( 35, 35 );
        multiplierKnob.SetSkin( "TR Large (white tick)" );
        multiplierKnob.SetRange( 1, 3, 3.0, false, 3 );
        multiplierKnob.SetKnobParams( 320, 40 );
        multiplierKnob.DisplayValueInPercent( false );
        multiplierKnob.SetKnobAdjustsRing( true );

        x1Label30 = new VoltageLabel( "x1Label30", "X1 Label", this, "X1" );
        AddComponent( x1Label30 );
        x1Label30.SetWantsMouseNotifications( false );
        x1Label30.SetPosition( 27, 93 );
        x1Label30.SetSize( 20, 8 );
        x1Label30.SetEditable( false, false );
        x1Label30.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        x1Label30.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        x1Label30.SetColor( new Color( 0, 0, 0, 255 ) );
        x1Label30.SetBkColor( new Color( 65, 65, 65, 0 ) );
        x1Label30.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        x1Label30.SetBorderSize( 1 );
        x1Label30.SetMultiLineEdit( false );
        x1Label30.SetIsNumberEditor( false );
        x1Label30.SetNumberEditorRange( 0, 100 );
        x1Label30.SetNumberEditorInterval( 1 );
        x1Label30.SetNumberEditorUsesMouseWheel( false );
        x1Label30.SetHasCustomTextHoverColor( false );
        x1Label30.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        x1Label30.SetFont( "Arial", 8, true, false );

        x10Label33 = new VoltageLabel( "x10Label33", "X10 Label", this, "X10" );
        AddComponent( x10Label33 );
        x10Label33.SetWantsMouseNotifications( false );
        x10Label33.SetPosition( 43, 87 );
        x10Label33.SetSize( 20, 8 );
        x10Label33.SetEditable( false, false );
        x10Label33.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        x10Label33.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        x10Label33.SetColor( new Color( 0, 0, 0, 255 ) );
        x10Label33.SetBkColor( new Color( 65, 65, 65, 0 ) );
        x10Label33.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        x10Label33.SetBorderSize( 1 );
        x10Label33.SetMultiLineEdit( false );
        x10Label33.SetIsNumberEditor( false );
        x10Label33.SetNumberEditorRange( 0, 100 );
        x10Label33.SetNumberEditorInterval( 1 );
        x10Label33.SetNumberEditorUsesMouseWheel( false );
        x10Label33.SetHasCustomTextHoverColor( false );
        x10Label33.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        x10Label33.SetFont( "Arial", 8, true, false );

        x100Label36 = new VoltageLabel( "x100Label36", "X100 Label", this, "X100" );
        AddComponent( x100Label36 );
        x100Label36.SetWantsMouseNotifications( false );
        x100Label36.SetPosition( 62, 93 );
        x100Label36.SetSize( 20, 8 );
        x100Label36.SetEditable( false, false );
        x100Label36.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        x100Label36.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        x100Label36.SetColor( new Color( 0, 0, 0, 255 ) );
        x100Label36.SetBkColor( new Color( 65, 65, 65, 0 ) );
        x100Label36.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        x100Label36.SetBorderSize( 1 );
        x100Label36.SetMultiLineEdit( false );
        x100Label36.SetIsNumberEditor( false );
        x100Label36.SetNumberEditorRange( 0, 100 );
        x100Label36.SetNumberEditorInterval( 1 );
        x100Label36.SetNumberEditorUsesMouseWheel( false );
        x100Label36.SetHasCustomTextHoverColor( false );
        x100Label36.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        x100Label36.SetFont( "Arial", 8, true, false );

        shapeKnob = new VoltageKnob( "shapeKnob", "Shape", this, -1.0, 1.0, 0.0 );
        AddComponent( shapeKnob );
        shapeKnob.SetWantsMouseNotifications( false );
        shapeKnob.SetPosition( 19, 161 );
        shapeKnob.SetSize( 35, 35 );
        shapeKnob.SetSkin( "TR Large (white tick)" );
        shapeKnob.SetRange( -1.0, 1.0, 0.0, false, 0 );
        shapeKnob.SetKnobParams( 245, 115 );
        shapeKnob.DisplayValueInPercent( false );
        shapeKnob.SetKnobAdjustsRing( true );

        beatDepthKnob = new VoltageKnob( "beatDepthKnob", "Beat Depth", this, 0.0, 1.0, 0.0 );
        AddComponent( beatDepthKnob );
        beatDepthKnob.SetWantsMouseNotifications( false );
        beatDepthKnob.SetPosition( 69, 161 );
        beatDepthKnob.SetSize( 35, 35 );
        beatDepthKnob.SetSkin( "TR Large (white tick)" );
        beatDepthKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        beatDepthKnob.SetKnobParams( 245, 115 );
        beatDepthKnob.DisplayValueInPercent( true );
        beatDepthKnob.SetKnobAdjustsRing( true );

        offsetKnob = new VoltageKnob( "offsetKnob", "Offset", this, -1.0, 1.0, 0.0 );
        AddComponent( offsetKnob );
        offsetKnob.SetWantsMouseNotifications( false );
        offsetKnob.SetPosition( 119, 161 );
        offsetKnob.SetSize( 35, 35 );
        offsetKnob.SetSkin( "TR Large (white tick)" );
        offsetKnob.SetRange( -1.0, 1.0, 0.0, false, 0 );
        offsetKnob.SetKnobParams( 245, 115 );
        offsetKnob.DisplayValueInPercent( true );
        offsetKnob.SetKnobAdjustsRing( true );

        scale0Label31 = new VoltageLabel( "scale0Label31", "0 Label", this, "0" );
        AddComponent( scale0Label31 );
        scale0Label31.SetWantsMouseNotifications( false );
        scale0Label31.SetPosition( 59, 186 );
        scale0Label31.SetSize( 16, 8 );
        scale0Label31.SetEditable( false, false );
        scale0Label31.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale0Label31.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale0Label31.SetColor( new Color( 0, 0, 0, 255 ) );
        scale0Label31.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale0Label31.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale0Label31.SetBorderSize( 1 );
        scale0Label31.SetMultiLineEdit( false );
        scale0Label31.SetIsNumberEditor( false );
        scale0Label31.SetNumberEditorRange( 0, 100 );
        scale0Label31.SetNumberEditorInterval( 1 );
        scale0Label31.SetNumberEditorUsesMouseWheel( false );
        scale0Label31.SetHasCustomTextHoverColor( false );
        scale0Label31.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale0Label31.SetFont( "Arial", 8, true, false );

        rLabel29 = new VoltageLabel( "rLabel29", "R Label", this, "R" );
        AddComponent( rLabel29 );
        rLabel29.SetWantsMouseNotifications( false );
        rLabel29.SetPosition( 9, 186 );
        rLabel29.SetSize( 16, 8 );
        rLabel29.SetEditable( false, false );
        rLabel29.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        rLabel29.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        rLabel29.SetColor( new Color( 0, 0, 0, 255 ) );
        rLabel29.SetBkColor( new Color( 65, 65, 65, 0 ) );
        rLabel29.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        rLabel29.SetBorderSize( 1 );
        rLabel29.SetMultiLineEdit( false );
        rLabel29.SetIsNumberEditor( false );
        rLabel29.SetNumberEditorRange( 0, 100 );
        rLabel29.SetNumberEditorInterval( 1 );
        rLabel29.SetNumberEditorUsesMouseWheel( false );
        rLabel29.SetHasCustomTextHoverColor( false );
        rLabel29.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        rLabel29.SetFont( "Arial", 8, true, false );

        sLabel32 = new VoltageLabel( "sLabel32", "S Label", this, "S" );
        AddComponent( sLabel32 );
        sLabel32.SetWantsMouseNotifications( false );
        sLabel32.SetPosition( 48, 186 );
        sLabel32.SetSize( 16, 8 );
        sLabel32.SetEditable( false, false );
        sLabel32.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sLabel32.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sLabel32.SetColor( new Color( 0, 0, 0, 255 ) );
        sLabel32.SetBkColor( new Color( 65, 65, 65, 0 ) );
        sLabel32.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        sLabel32.SetBorderSize( 1 );
        sLabel32.SetMultiLineEdit( false );
        sLabel32.SetIsNumberEditor( false );
        sLabel32.SetNumberEditorRange( 0, 100 );
        sLabel32.SetNumberEditorInterval( 1 );
        sLabel32.SetNumberEditorUsesMouseWheel( false );
        sLabel32.SetHasCustomTextHoverColor( false );
        sLabel32.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sLabel32.SetFont( "Arial", 8, true, false );

        panelLabel37 = new VoltageLabel( "panelLabel37", "- Label", this, "—" );
        AddComponent( panelLabel37 );
        panelLabel37.SetWantsMouseNotifications( false );
        panelLabel37.SetPosition( 7, 169 );
        panelLabel37.SetSize( 16, 8 );
        panelLabel37.SetEditable( false, false );
        panelLabel37.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel37.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel37.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel37.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel37.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel37.SetBorderSize( 1 );
        panelLabel37.SetMultiLineEdit( false );
        panelLabel37.SetIsNumberEditor( false );
        panelLabel37.SetNumberEditorRange( 0, 100 );
        panelLabel37.SetNumberEditorInterval( 1 );
        panelLabel37.SetNumberEditorUsesMouseWheel( false );
        panelLabel37.SetHasCustomTextHoverColor( false );
        panelLabel37.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel37.SetFont( "Arial", 8, true, false );

        panelLabel38 = new VoltageLabel( "panelLabel38", "- Label", this, "—" );
        AddComponent( panelLabel38 );
        panelLabel38.SetWantsMouseNotifications( false );
        panelLabel38.SetPosition( 50, 169 );
        panelLabel38.SetSize( 16, 8 );
        panelLabel38.SetEditable( false, false );
        panelLabel38.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel38.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel38.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel38.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel38.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel38.SetBorderSize( 1 );
        panelLabel38.SetMultiLineEdit( false );
        panelLabel38.SetIsNumberEditor( false );
        panelLabel38.SetNumberEditorRange( 0, 100 );
        panelLabel38.SetNumberEditorInterval( 1 );
        panelLabel38.SetNumberEditorUsesMouseWheel( false );
        panelLabel38.SetHasCustomTextHoverColor( false );
        panelLabel38.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel38.SetFont( "Arial", 8, true, false );

        scale100Label44 = new VoltageLabel( "scale100Label44", "100 Label", this, "100" );
        AddComponent( scale100Label44 );
        scale100Label44.SetWantsMouseNotifications( false );
        scale100Label44.SetPosition( 99, 186 );
        scale100Label44.SetSize( 16, 8 );
        scale100Label44.SetEditable( false, false );
        scale100Label44.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale100Label44.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale100Label44.SetColor( new Color( 0, 0, 0, 255 ) );
        scale100Label44.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale100Label44.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale100Label44.SetBorderSize( 1 );
        scale100Label44.SetMultiLineEdit( false );
        scale100Label44.SetIsNumberEditor( false );
        scale100Label44.SetNumberEditorRange( 0, 100 );
        scale100Label44.SetNumberEditorInterval( 1 );
        scale100Label44.SetNumberEditorUsesMouseWheel( false );
        scale100Label44.SetHasCustomTextHoverColor( false );
        scale100Label44.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale100Label44.SetFont( "Arial", 8, true, false );

        fLabel61 = new VoltageLabel( "fLabel61", "F Label", this, "F" );
        AddComponent( fLabel61 );
        fLabel61.SetWantsMouseNotifications( false );
        fLabel61.SetPosition( 18, 267 );
        fLabel61.SetSize( 8, 8 );
        fLabel61.SetEditable( false, false );
        fLabel61.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fLabel61.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fLabel61.SetColor( new Color( 0, 0, 0, 255 ) );
        fLabel61.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fLabel61.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fLabel61.SetBorderSize( 1 );
        fLabel61.SetMultiLineEdit( true );
        fLabel61.SetIsNumberEditor( false );
        fLabel61.SetNumberEditorRange( 0, 100 );
        fLabel61.SetNumberEditorInterval( 1 );
        fLabel61.SetNumberEditorUsesMouseWheel( false );
        fLabel61.SetHasCustomTextHoverColor( false );
        fLabel61.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fLabel61.SetFont( "Arial", 8, true, false );

        mLabel62 = new VoltageLabel( "mLabel62", "M Label", this, "M" );
        AddComponent( mLabel62 );
        mLabel62.SetWantsMouseNotifications( false );
        mLabel62.SetPosition( 18, 290 );
        mLabel62.SetSize( 8, 8 );
        mLabel62.SetEditable( false, false );
        mLabel62.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        mLabel62.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        mLabel62.SetColor( new Color( 0, 0, 0, 255 ) );
        mLabel62.SetBkColor( new Color( 65, 65, 65, 0 ) );
        mLabel62.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        mLabel62.SetBorderSize( 1 );
        mLabel62.SetMultiLineEdit( true );
        mLabel62.SetIsNumberEditor( false );
        mLabel62.SetNumberEditorRange( 0, 100 );
        mLabel62.SetNumberEditorInterval( 1 );
        mLabel62.SetNumberEditorUsesMouseWheel( false );
        mLabel62.SetHasCustomTextHoverColor( false );
        mLabel62.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        mLabel62.SetFont( "Arial", 8, true, false );

        panelLabel63 = new VoltageLabel( "panelLabel63", "– Label", this, "–" );
        AddComponent( panelLabel63 );
        panelLabel63.SetWantsMouseNotifications( false );
        panelLabel63.SetPosition( 204, 287 );
        panelLabel63.SetSize( 8, 8 );
        panelLabel63.SetEditable( false, false );
        panelLabel63.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel63.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel63.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel63.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel63.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel63.SetBorderSize( 1 );
        panelLabel63.SetMultiLineEdit( true );
        panelLabel63.SetIsNumberEditor( false );
        panelLabel63.SetNumberEditorRange( 0, 100 );
        panelLabel63.SetNumberEditorInterval( 1 );
        panelLabel63.SetNumberEditorUsesMouseWheel( false );
        panelLabel63.SetHasCustomTextHoverColor( false );
        panelLabel63.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel63.SetFont( "Arial", 8, true, false );

        panelLabel64 = new VoltageLabel( "panelLabel64", "+ Label", this, "+" );
        AddComponent( panelLabel64 );
        panelLabel64.SetWantsMouseNotifications( false );
        panelLabel64.SetPosition( 204, 267 );
        panelLabel64.SetSize( 8, 8 );
        panelLabel64.SetEditable( false, false );
        panelLabel64.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel64.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel64.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel64.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel64.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel64.SetBorderSize( 1 );
        panelLabel64.SetMultiLineEdit( true );
        panelLabel64.SetIsNumberEditor( false );
        panelLabel64.SetNumberEditorRange( 0, 100 );
        panelLabel64.SetNumberEditorInterval( 1 );
        panelLabel64.SetNumberEditorUsesMouseWheel( false );
        panelLabel64.SetHasCustomTextHoverColor( false );
        panelLabel64.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel64.SetFont( "Arial", 8, true, false );

        tLabel43 = new VoltageLabel( "tLabel43", "T Label", this, "T" );
        AddComponent( tLabel43 );
        tLabel43.SetWantsMouseNotifications( false );
        tLabel43.SetPosition( 28, 152 );
        tLabel43.SetSize( 16, 8 );
        tLabel43.SetEditable( false, false );
        tLabel43.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        tLabel43.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        tLabel43.SetColor( new Color( 0, 0, 0, 255 ) );
        tLabel43.SetBkColor( new Color( 65, 65, 65, 0 ) );
        tLabel43.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        tLabel43.SetBorderSize( 1 );
        tLabel43.SetMultiLineEdit( false );
        tLabel43.SetIsNumberEditor( false );
        tLabel43.SetNumberEditorRange( 0, 100 );
        tLabel43.SetNumberEditorInterval( 1 );
        tLabel43.SetNumberEditorUsesMouseWheel( false );
        tLabel43.SetHasCustomTextHoverColor( false );
        tLabel43.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        tLabel43.SetFont( "Arial", 8, true, false );

        cLabel60 = new VoltageLabel( "cLabel60", "C Label", this, "C" );
        AddComponent( cLabel60 );
        cLabel60.SetWantsMouseNotifications( false );
        cLabel60.SetPosition( 170, 297 );
        cLabel60.SetSize( 16, 16 );
        cLabel60.SetEditable( false, false );
        cLabel60.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        cLabel60.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        cLabel60.SetColor( new Color( 0, 0, 0, 255 ) );
        cLabel60.SetBkColor( new Color( 65, 65, 65, 0 ) );
        cLabel60.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        cLabel60.SetBorderSize( 1 );
        cLabel60.SetMultiLineEdit( false );
        cLabel60.SetIsNumberEditor( false );
        cLabel60.SetNumberEditorRange( 0, 100 );
        cLabel60.SetNumberEditorInterval( 1 );
        cLabel60.SetNumberEditorUsesMouseWheel( false );
        cLabel60.SetHasCustomTextHoverColor( false );
        cLabel60.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        cLabel60.SetFont( "Arial", 13, true, false );

        panelLabel47 = new VoltageLabel( "panelLabel47", "- Label", this, "—" );
        AddComponent( panelLabel47 );
        panelLabel47.SetWantsMouseNotifications( false );
        panelLabel47.SetPosition( 57, 169 );
        panelLabel47.SetSize( 16, 8 );
        panelLabel47.SetEditable( false, false );
        panelLabel47.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel47.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel47.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel47.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel47.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel47.SetBorderSize( 1 );
        panelLabel47.SetMultiLineEdit( false );
        panelLabel47.SetIsNumberEditor( false );
        panelLabel47.SetNumberEditorRange( 0, 100 );
        panelLabel47.SetNumberEditorInterval( 1 );
        panelLabel47.SetNumberEditorUsesMouseWheel( false );
        panelLabel47.SetHasCustomTextHoverColor( false );
        panelLabel47.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel47.SetFont( "Arial", 8, true, false );

        scale50Label53 = new VoltageLabel( "scale50Label53", "50 Label", this, "50" );
        AddComponent( scale50Label53 );
        scale50Label53.SetWantsMouseNotifications( false );
        scale50Label53.SetPosition( 79, 152 );
        scale50Label53.SetSize( 16, 8 );
        scale50Label53.SetEditable( false, false );
        scale50Label53.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale50Label53.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale50Label53.SetColor( new Color( 0, 0, 0, 255 ) );
        scale50Label53.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale50Label53.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale50Label53.SetBorderSize( 1 );
        scale50Label53.SetMultiLineEdit( false );
        scale50Label53.SetIsNumberEditor( false );
        scale50Label53.SetNumberEditorRange( 0, 100 );
        scale50Label53.SetNumberEditorInterval( 1 );
        scale50Label53.SetNumberEditorUsesMouseWheel( false );
        scale50Label53.SetHasCustomTextHoverColor( false );
        scale50Label53.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale50Label53.SetFont( "Arial", 8, true, false );

        panelLabel48 = new VoltageLabel( "panelLabel48", "- Label", this, "—" );
        AddComponent( panelLabel48 );
        panelLabel48.SetWantsMouseNotifications( false );
        panelLabel48.SetPosition( 100, 169 );
        panelLabel48.SetSize( 16, 8 );
        panelLabel48.SetEditable( false, false );
        panelLabel48.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel48.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel48.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel48.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel48.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel48.SetBorderSize( 1 );
        panelLabel48.SetMultiLineEdit( false );
        panelLabel48.SetIsNumberEditor( false );
        panelLabel48.SetNumberEditorRange( 0, 100 );
        panelLabel48.SetNumberEditorInterval( 1 );
        panelLabel48.SetNumberEditorUsesMouseWheel( false );
        panelLabel48.SetHasCustomTextHoverColor( false );
        panelLabel48.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel48.SetFont( "Arial", 8, true, false );

        scale1Label34 = new VoltageLabel( "scale1Label34", "-1 Label", this, "-1" );
        AddComponent( scale1Label34 );
        scale1Label34.SetWantsMouseNotifications( false );
        scale1Label34.SetPosition( 109, 186 );
        scale1Label34.SetSize( 16, 8 );
        scale1Label34.SetEditable( false, false );
        scale1Label34.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale1Label34.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale1Label34.SetColor( new Color( 0, 0, 0, 255 ) );
        scale1Label34.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale1Label34.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale1Label34.SetBorderSize( 1 );
        scale1Label34.SetMultiLineEdit( false );
        scale1Label34.SetIsNumberEditor( false );
        scale1Label34.SetNumberEditorRange( 0, 100 );
        scale1Label34.SetNumberEditorInterval( 1 );
        scale1Label34.SetNumberEditorUsesMouseWheel( false );
        scale1Label34.SetHasCustomTextHoverColor( false );
        scale1Label34.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale1Label34.SetFont( "Arial", 8, true, false );

        scale1Label45 = new VoltageLabel( "scale1Label45", "+1 Label", this, "+1" );
        AddComponent( scale1Label45 );
        scale1Label45.SetWantsMouseNotifications( false );
        scale1Label45.SetPosition( 150, 186 );
        scale1Label45.SetSize( 16, 8 );
        scale1Label45.SetEditable( false, false );
        scale1Label45.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale1Label45.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale1Label45.SetColor( new Color( 0, 0, 0, 255 ) );
        scale1Label45.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale1Label45.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale1Label45.SetBorderSize( 1 );
        scale1Label45.SetMultiLineEdit( false );
        scale1Label45.SetIsNumberEditor( false );
        scale1Label45.SetNumberEditorRange( 0, 100 );
        scale1Label45.SetNumberEditorInterval( 1 );
        scale1Label45.SetNumberEditorUsesMouseWheel( false );
        scale1Label45.SetHasCustomTextHoverColor( false );
        scale1Label45.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale1Label45.SetFont( "Arial", 8, true, false );

        panelLabel49 = new VoltageLabel( "panelLabel49", "- Label", this, "—" );
        AddComponent( panelLabel49 );
        panelLabel49.SetWantsMouseNotifications( false );
        panelLabel49.SetPosition( 107, 169 );
        panelLabel49.SetSize( 16, 8 );
        panelLabel49.SetEditable( false, false );
        panelLabel49.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel49.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel49.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel49.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel49.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel49.SetBorderSize( 1 );
        panelLabel49.SetMultiLineEdit( false );
        panelLabel49.SetIsNumberEditor( false );
        panelLabel49.SetNumberEditorRange( 0, 100 );
        panelLabel49.SetNumberEditorInterval( 1 );
        panelLabel49.SetNumberEditorUsesMouseWheel( false );
        panelLabel49.SetHasCustomTextHoverColor( false );
        panelLabel49.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel49.SetFont( "Arial", 8, true, false );

        scale0Label54 = new VoltageLabel( "scale0Label54", "0 Label", this, "0" );
        AddComponent( scale0Label54 );
        scale0Label54.SetWantsMouseNotifications( false );
        scale0Label54.SetPosition( 129, 152 );
        scale0Label54.SetSize( 16, 8 );
        scale0Label54.SetEditable( false, false );
        scale0Label54.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale0Label54.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale0Label54.SetColor( new Color( 0, 0, 0, 255 ) );
        scale0Label54.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale0Label54.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale0Label54.SetBorderSize( 1 );
        scale0Label54.SetMultiLineEdit( false );
        scale0Label54.SetIsNumberEditor( false );
        scale0Label54.SetNumberEditorRange( 0, 100 );
        scale0Label54.SetNumberEditorInterval( 1 );
        scale0Label54.SetNumberEditorUsesMouseWheel( false );
        scale0Label54.SetHasCustomTextHoverColor( false );
        scale0Label54.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale0Label54.SetFont( "Arial", 8, true, false );

        panelLabel50 = new VoltageLabel( "panelLabel50", "- Label", this, "—" );
        AddComponent( panelLabel50 );
        panelLabel50.SetWantsMouseNotifications( false );
        panelLabel50.SetPosition( 150, 169 );
        panelLabel50.SetSize( 16, 8 );
        panelLabel50.SetEditable( false, false );
        panelLabel50.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel50.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel50.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel50.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel50.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel50.SetBorderSize( 1 );
        panelLabel50.SetMultiLineEdit( false );
        panelLabel50.SetIsNumberEditor( false );
        panelLabel50.SetNumberEditorRange( 0, 100 );
        panelLabel50.SetNumberEditorInterval( 1 );
        panelLabel50.SetNumberEditorUsesMouseWheel( false );
        panelLabel50.SetHasCustomTextHoverColor( false );
        panelLabel50.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel50.SetFont( "Arial", 8, true, false );

        frequencyDisplay = new VoltageDigitalCounter( "frequencyDisplay", "Base Frequency Display", this, 2 );
        AddComponent( frequencyDisplay );
        frequencyDisplay.SetWantsMouseNotifications( false );
        frequencyDisplay.SetPosition( 93, 35 );
        frequencyDisplay.SetSize( 40, 40 );
        frequencyDisplay.SetSkin( "Long Gray" );
        frequencyDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        fractionFrequencyDisplay = new VoltageDigitalCounter( "fractionFrequencyDisplay", "Fraction Frequency Display", this, 2 );
        AddComponent( fractionFrequencyDisplay );
        fractionFrequencyDisplay.SetWantsMouseNotifications( false );
        fractionFrequencyDisplay.SetPosition( 158, 35 );
        fractionFrequencyDisplay.SetSize( 40, 40 );
        fractionFrequencyDisplay.SetSkin( "Long Gray" );
        fractionFrequencyDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        amplitudeLabel35 = new VoltageLabel( "amplitudeLabel35", "Amplitude Label", this, "Amplitude" );
        AddComponent( amplitudeLabel35 );
        amplitudeLabel35.SetWantsMouseNotifications( false );
        amplitudeLabel35.SetPosition( 117, 254 );
        amplitudeLabel35.SetSize( 40, 20 );
        amplitudeLabel35.SetEditable( false, false );
        amplitudeLabel35.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        amplitudeLabel35.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        amplitudeLabel35.SetColor( new Color( 0, 0, 0, 255 ) );
        amplitudeLabel35.SetBkColor( new Color( 65, 65, 65, 0 ) );
        amplitudeLabel35.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        amplitudeLabel35.SetBorderSize( 1 );
        amplitudeLabel35.SetMultiLineEdit( false );
        amplitudeLabel35.SetIsNumberEditor( false );
        amplitudeLabel35.SetNumberEditorRange( 0, 100 );
        amplitudeLabel35.SetNumberEditorInterval( 1 );
        amplitudeLabel35.SetNumberEditorUsesMouseWheel( false );
        amplitudeLabel35.SetHasCustomTextHoverColor( false );
        amplitudeLabel35.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        amplitudeLabel35.SetFont( "<Sans-Serif>", 9, true, false );

        frequencyLabel3 = new VoltageLabel( "frequencyLabel3", "Frequency Label", this, "Frequency" );
        AddComponent( frequencyLabel3 );
        frequencyLabel3.SetWantsMouseNotifications( false );
        frequencyLabel3.SetPosition( 0, 73 );
        frequencyLabel3.SetSize( 230, 18 );
        frequencyLabel3.SetEditable( false, false );
        frequencyLabel3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        frequencyLabel3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        frequencyLabel3.SetColor( new Color( 0, 0, 0, 255 ) );
        frequencyLabel3.SetBkColor( new Color( 85, 85, 85, 0 ) );
        frequencyLabel3.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        frequencyLabel3.SetBorderSize( 0 );
        frequencyLabel3.SetMultiLineEdit( false );
        frequencyLabel3.SetIsNumberEditor( false );
        frequencyLabel3.SetNumberEditorRange( 0, 100 );
        frequencyLabel3.SetNumberEditorInterval( 1 );
        frequencyLabel3.SetNumberEditorUsesMouseWheel( false );
        frequencyLabel3.SetHasCustomTextHoverColor( false );
        frequencyLabel3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        frequencyLabel3.SetFont( "Arial", 10, true, false );

        beatOscillatorLabel39 = new VoltageLabel( "beatOscillatorLabel39", "Beat Oscillator Label", this, "Beat Oscillator" );
        AddComponent( beatOscillatorLabel39 );
        beatOscillatorLabel39.SetWantsMouseNotifications( false );
        beatOscillatorLabel39.SetPosition( 5, 135 );
        beatOscillatorLabel39.SetSize( 161, 23 );
        beatOscillatorLabel39.SetEditable( false, false );
        beatOscillatorLabel39.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        beatOscillatorLabel39.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        beatOscillatorLabel39.SetColor( new Color( 0, 0, 0, 255 ) );
        beatOscillatorLabel39.SetBkColor( new Color( 85, 85, 85, 0 ) );
        beatOscillatorLabel39.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        beatOscillatorLabel39.SetBorderSize( 0 );
        beatOscillatorLabel39.SetMultiLineEdit( false );
        beatOscillatorLabel39.SetIsNumberEditor( false );
        beatOscillatorLabel39.SetNumberEditorRange( 0, 100 );
        beatOscillatorLabel39.SetNumberEditorInterval( 1 );
        beatOscillatorLabel39.SetNumberEditorUsesMouseWheel( false );
        beatOscillatorLabel39.SetHasCustomTextHoverColor( false );
        beatOscillatorLabel39.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        beatOscillatorLabel39.SetFont( "Arial", 10, true, false );

        shapeLabel40 = new VoltageLabel( "shapeLabel40", "Shape Label", this, "Shape" );
        AddComponent( shapeLabel40 );
        shapeLabel40.SetWantsMouseNotifications( false );
        shapeLabel40.SetPosition( 15, 191 );
        shapeLabel40.SetSize( 40, 20 );
        shapeLabel40.SetEditable( false, false );
        shapeLabel40.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        shapeLabel40.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        shapeLabel40.SetColor( new Color( 0, 0, 0, 255 ) );
        shapeLabel40.SetBkColor( new Color( 65, 65, 65, 0 ) );
        shapeLabel40.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        shapeLabel40.SetBorderSize( 1 );
        shapeLabel40.SetMultiLineEdit( false );
        shapeLabel40.SetIsNumberEditor( false );
        shapeLabel40.SetNumberEditorRange( 0, 100 );
        shapeLabel40.SetNumberEditorInterval( 1 );
        shapeLabel40.SetNumberEditorUsesMouseWheel( false );
        shapeLabel40.SetHasCustomTextHoverColor( false );
        shapeLabel40.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        shapeLabel40.SetFont( "<Sans-Serif>", 9, true, false );

        depthLabel41 = new VoltageLabel( "depthLabel41", "Depth Label", this, "Depth" );
        AddComponent( depthLabel41 );
        depthLabel41.SetWantsMouseNotifications( false );
        depthLabel41.SetPosition( 65, 191 );
        depthLabel41.SetSize( 40, 20 );
        depthLabel41.SetEditable( false, false );
        depthLabel41.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        depthLabel41.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        depthLabel41.SetColor( new Color( 0, 0, 0, 255 ) );
        depthLabel41.SetBkColor( new Color( 65, 65, 65, 0 ) );
        depthLabel41.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        depthLabel41.SetBorderSize( 1 );
        depthLabel41.SetMultiLineEdit( false );
        depthLabel41.SetIsNumberEditor( false );
        depthLabel41.SetNumberEditorRange( 0, 100 );
        depthLabel41.SetNumberEditorInterval( 1 );
        depthLabel41.SetNumberEditorUsesMouseWheel( false );
        depthLabel41.SetHasCustomTextHoverColor( false );
        depthLabel41.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        depthLabel41.SetFont( "<Sans-Serif>", 9, true, false );

        offsetLabel42 = new VoltageLabel( "offsetLabel42", "Offset Label", this, "Offset" );
        AddComponent( offsetLabel42 );
        offsetLabel42.SetWantsMouseNotifications( false );
        offsetLabel42.SetPosition( 118, 191 );
        offsetLabel42.SetSize( 40, 20 );
        offsetLabel42.SetEditable( false, false );
        offsetLabel42.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        offsetLabel42.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        offsetLabel42.SetColor( new Color( 0, 0, 0, 255 ) );
        offsetLabel42.SetBkColor( new Color( 65, 65, 65, 0 ) );
        offsetLabel42.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        offsetLabel42.SetBorderSize( 1 );
        offsetLabel42.SetMultiLineEdit( false );
        offsetLabel42.SetIsNumberEditor( false );
        offsetLabel42.SetNumberEditorRange( 0, 100 );
        offsetLabel42.SetNumberEditorInterval( 1 );
        offsetLabel42.SetNumberEditorUsesMouseWheel( false );
        offsetLabel42.SetHasCustomTextHoverColor( false );
        offsetLabel42.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        offsetLabel42.SetFont( "<Sans-Serif>", 9, true, false );

        swellDepthKnob = new VoltageKnob( "swellDepthKnob", "Swell Depth", this, 0.0, 2.0, 0.0 );
        AddComponent( swellDepthKnob );
        swellDepthKnob.SetWantsMouseNotifications( false );
        swellDepthKnob.SetPosition( 176, 161 );
        swellDepthKnob.SetSize( 35, 35 );
        swellDepthKnob.SetSkin( "TR Large (white tick)" );
        swellDepthKnob.SetRange( 0.0, 2.0, 0.0, false, 0 );
        swellDepthKnob.SetKnobParams( 245, 115 );
        swellDepthKnob.DisplayValueInPercent( true );
        swellDepthKnob.SetKnobAdjustsRing( true );

        scale0Label46 = new VoltageLabel( "scale0Label46", "0 Label", this, "0" );
        AddComponent( scale0Label46 );
        scale0Label46.SetWantsMouseNotifications( false );
        scale0Label46.SetPosition( 166, 186 );
        scale0Label46.SetSize( 16, 8 );
        scale0Label46.SetEditable( false, false );
        scale0Label46.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale0Label46.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale0Label46.SetColor( new Color( 0, 0, 0, 255 ) );
        scale0Label46.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale0Label46.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale0Label46.SetBorderSize( 1 );
        scale0Label46.SetMultiLineEdit( false );
        scale0Label46.SetIsNumberEditor( false );
        scale0Label46.SetNumberEditorRange( 0, 100 );
        scale0Label46.SetNumberEditorInterval( 1 );
        scale0Label46.SetNumberEditorUsesMouseWheel( false );
        scale0Label46.SetHasCustomTextHoverColor( false );
        scale0Label46.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale0Label46.SetFont( "Arial", 8, true, false );

        scale100Label51 = new VoltageLabel( "scale100Label51", "200 Percent Label", this, "200" );
        AddComponent( scale100Label51 );
        scale100Label51.SetWantsMouseNotifications( false );
        scale100Label51.SetPosition( 207, 186 );
        scale100Label51.SetSize( 16, 8 );
        scale100Label51.SetEditable( false, false );
        scale100Label51.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale100Label51.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale100Label51.SetColor( new Color( 0, 0, 0, 255 ) );
        scale100Label51.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale100Label51.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale100Label51.SetBorderSize( 1 );
        scale100Label51.SetMultiLineEdit( false );
        scale100Label51.SetIsNumberEditor( false );
        scale100Label51.SetNumberEditorRange( 0, 100 );
        scale100Label51.SetNumberEditorInterval( 1 );
        scale100Label51.SetNumberEditorUsesMouseWheel( false );
        scale100Label51.SetHasCustomTextHoverColor( false );
        scale100Label51.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale100Label51.SetFont( "Arial", 8, true, false );

        panelLabel52 = new VoltageLabel( "panelLabel52", "- Label", this, "—" );
        AddComponent( panelLabel52 );
        panelLabel52.SetWantsMouseNotifications( false );
        panelLabel52.SetPosition( 164, 169 );
        panelLabel52.SetSize( 16, 8 );
        panelLabel52.SetEditable( false, false );
        panelLabel52.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel52.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel52.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel52.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel52.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel52.SetBorderSize( 1 );
        panelLabel52.SetMultiLineEdit( false );
        panelLabel52.SetIsNumberEditor( false );
        panelLabel52.SetNumberEditorRange( 0, 100 );
        panelLabel52.SetNumberEditorInterval( 1 );
        panelLabel52.SetNumberEditorUsesMouseWheel( false );
        panelLabel52.SetHasCustomTextHoverColor( false );
        panelLabel52.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel52.SetFont( "Arial", 8, true, false );

        scale50Label55 = new VoltageLabel( "scale50Label55", "100 Percent Label", this, "100" );
        AddComponent( scale50Label55 );
        scale50Label55.SetWantsMouseNotifications( false );
        scale50Label55.SetPosition( 186, 152 );
        scale50Label55.SetSize( 16, 8 );
        scale50Label55.SetEditable( false, false );
        scale50Label55.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale50Label55.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale50Label55.SetColor( new Color( 0, 0, 0, 255 ) );
        scale50Label55.SetBkColor( new Color( 65, 65, 65, 0 ) );
        scale50Label55.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        scale50Label55.SetBorderSize( 1 );
        scale50Label55.SetMultiLineEdit( false );
        scale50Label55.SetIsNumberEditor( false );
        scale50Label55.SetNumberEditorRange( 0, 100 );
        scale50Label55.SetNumberEditorInterval( 1 );
        scale50Label55.SetNumberEditorUsesMouseWheel( false );
        scale50Label55.SetHasCustomTextHoverColor( false );
        scale50Label55.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale50Label55.SetFont( "Arial", 8, true, false );

        panelLabel56 = new VoltageLabel( "panelLabel56", "- Label", this, "—" );
        AddComponent( panelLabel56 );
        panelLabel56.SetWantsMouseNotifications( false );
        panelLabel56.SetPosition( 207, 169 );
        panelLabel56.SetSize( 16, 8 );
        panelLabel56.SetEditable( false, false );
        panelLabel56.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel56.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel56.SetColor( new Color( 0, 0, 0, 255 ) );
        panelLabel56.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel56.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel56.SetBorderSize( 1 );
        panelLabel56.SetMultiLineEdit( false );
        panelLabel56.SetIsNumberEditor( false );
        panelLabel56.SetNumberEditorRange( 0, 100 );
        panelLabel56.SetNumberEditorInterval( 1 );
        panelLabel56.SetNumberEditorUsesMouseWheel( false );
        panelLabel56.SetHasCustomTextHoverColor( false );
        panelLabel56.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel56.SetFont( "Arial", 8, true, false );

        depthLabel57 = new VoltageLabel( "depthLabel57", "Depth Label", this, "Depth" );
        AddComponent( depthLabel57 );
        depthLabel57.SetWantsMouseNotifications( false );
        depthLabel57.SetPosition( 175, 191 );
        depthLabel57.SetSize( 40, 20 );
        depthLabel57.SetEditable( false, false );
        depthLabel57.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        depthLabel57.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        depthLabel57.SetColor( new Color( 0, 0, 0, 255 ) );
        depthLabel57.SetBkColor( new Color( 65, 65, 65, 0 ) );
        depthLabel57.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        depthLabel57.SetBorderSize( 1 );
        depthLabel57.SetMultiLineEdit( false );
        depthLabel57.SetIsNumberEditor( false );
        depthLabel57.SetNumberEditorRange( 0, 100 );
        depthLabel57.SetNumberEditorInterval( 1 );
        depthLabel57.SetNumberEditorUsesMouseWheel( false );
        depthLabel57.SetHasCustomTextHoverColor( false );
        depthLabel57.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        depthLabel57.SetFont( "<Sans-Serif>", 9, true, false );

        ampModLabel58 = new VoltageLabel( "ampModLabel58", "Amp Mod Label", this, "Amp Mod" );
        AddComponent( ampModLabel58 );
        ampModLabel58.SetWantsMouseNotifications( false );
        ampModLabel58.SetPosition( 164, 135 );
        ampModLabel58.SetSize( 61, 23 );
        ampModLabel58.SetEditable( false, false );
        ampModLabel58.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ampModLabel58.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ampModLabel58.SetColor( new Color( 0, 0, 0, 255 ) );
        ampModLabel58.SetBkColor( new Color( 85, 85, 85, 0 ) );
        ampModLabel58.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        ampModLabel58.SetBorderSize( 0 );
        ampModLabel58.SetMultiLineEdit( false );
        ampModLabel58.SetIsNumberEditor( false );
        ampModLabel58.SetNumberEditorRange( 0, 100 );
        ampModLabel58.SetNumberEditorInterval( 1 );
        ampModLabel58.SetNumberEditorUsesMouseWheel( false );
        ampModLabel58.SetHasCustomTextHoverColor( false );
        ampModLabel58.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ampModLabel58.SetFont( "Arial", 10, true, false );

        externalButton = new VoltageButton( "externalButton", "External", this );
        AddComponent( externalButton );
        externalButton.SetWantsMouseNotifications( false );
        externalButton.SetPosition( 42, 222 );
        externalButton.SetSize( 20, 20 );
        externalButton.SetSkin( "2500 Square Big" );
        externalButton.ShowOverlay( false );
        externalButton.SetOverlayText( "" );
        externalButton.SetAutoRepeat( false );

        externalLabel59 = new VoltageLabel( "externalLabel59", "External Label", this, "External" );
        AddComponent( externalLabel59 );
        externalLabel59.SetWantsMouseNotifications( false );
        externalLabel59.SetPosition( 67, 222 );
        externalLabel59.SetSize( 40, 20 );
        externalLabel59.SetEditable( false, false );
        externalLabel59.SetJustificationFlags( VoltageLabel.Justification.Left );
        externalLabel59.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        externalLabel59.SetColor( new Color( 0, 0, 0, 255 ) );
        externalLabel59.SetBkColor( new Color( 65, 65, 65, 0 ) );
        externalLabel59.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        externalLabel59.SetBorderSize( 1 );
        externalLabel59.SetMultiLineEdit( false );
        externalLabel59.SetIsNumberEditor( false );
        externalLabel59.SetNumberEditorRange( 0, 100 );
        externalLabel59.SetNumberEditorInterval( 1 );
        externalLabel59.SetNumberEditorUsesMouseWheel( false );
        externalLabel59.SetHasCustomTextHoverColor( false );
        externalLabel59.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        externalLabel59.SetFont( "<Sans-Serif>", 9, true, false );

        externalInput = new VoltageAudioJack( "externalInput", "External Input", this, JackType.JackType_AudioInput );
        AddComponent( externalInput );
        externalInput.SetWantsMouseNotifications( false );
        externalInput.SetPosition( 4, 214 );
        externalInput.SetSize( 37, 37 );
        externalInput.SetSkin( "Dark Jack Straight" );

        fmSwitch = new VoltageSwitch( "fmSwitch", "Beat Mix / FM", this, 0 );
        AddComponent( fmSwitch );
        fmSwitch.SetWantsMouseNotifications( false );
        fmSwitch.SetPosition( 18, 275 );
        fmSwitch.SetSize( 8, 15 );
        fmSwitch.SetSkin( "2-State Slide Black" );
}

void InitializeControls2()
{

        courtesyPolaritySwitch = new VoltageSwitch( "courtesyPolaritySwitch", "Courtesy Polarity", this, 1 );
        AddComponent( courtesyPolaritySwitch );
        courtesyPolaritySwitch.SetWantsMouseNotifications( false );
        courtesyPolaritySwitch.SetPosition( 204, 275 );
        courtesyPolaritySwitch.SetSize( 8, 14 );
        courtesyPolaritySwitch.SetSkin( "2-State Slide Black" );

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

        descriptionLabel = new VoltageLabel( "descriptionLabel", "MODULE DESCRIPTION", this, "deep tone generator - modulator" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 3 );
        descriptionLabel.SetSize( 224, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 0, 0, 0, 255 ) );
        descriptionLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
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

        colophon = new VoltageLabel( "colophon", "Colophon", this, "insect laboratories       — pittsburgh, PA —       united states & beyond" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 85, 335 );
        colophon.SetSize( 60, 20 );
        colophon.SetEditable( false, false );
        colophon.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        colophon.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        colophon.SetColor( new Color( 19, 19, 19, 255 ) );
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

        image2 = new VoltageImage( "image2", "image2", this, false );
        AddComponent( image2 );
        image2.SetWantsMouseNotifications( false );
        image2.SetPosition( 85, 280 );
        image2.SetSize( 60, 60 );
        image2.SetCurrentImage( "wireframe_globe_E8E8E8.svg" );
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
        updatePanel();
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
            case Button_Changed:
                int bit = functionBit(component);
                if (bit != 0 && doubleValue >= 0.5) {
                    int previous = enabledFunctions;
                    enabledFunctions = previous ^ bit;
                    CreateUndoNode("Deep Tone Functions", "deepToneFunctions", (double) previous, (double) enabledFunctions);
                }
                break;
            case GUI_Update_Timer:
                updatePanel();
                break;
            case Reset:
                enabledFunctions = 0;
                resumePending = true;
                updatePanel();
                break;
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                resumePending = true;
                updatePanel();
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
        int enabled = enabledFunctions;
        double frequencyTarget = baseFrequency() * multiplier();
        double offsetPosition = offsetKnob.GetValue();
        double offsetTarget = offsetHz(offsetPosition);
        double shapeTarget = shapeKnob.GetValue();
        double beatDepthTarget = beatDepthKnob.GetValue();
        double swellDepthTarget = swellDepthKnob.GetValue();
        double amplitudePosition = amplitudeKnob.GetValue() / 15.0;
        double amplitudeTarget = 15.0 * amplitudePosition * amplitudePosition;
        double fmTarget = fmSwitch.GetValue() >= 0.5 ? 1.0 : 0.0;
        double polarityTarget = courtesyPolaritySwitch.GetValue() >= 0.5 ? 1.0 : -1.0;
        if (resumePending) {
            resumePending = false;
            frequency = frequencyTarget;
            offset = offsetTarget;
            shape = shapeTarget;
            beatDepth = beatDepthTarget;
            swellDepth = swellDepthTarget;
            amplitude = amplitudeTarget;
            fmMix = fmTarget;
            courtesyPolarity = polarityTarget;
            toneGate = (enabled & TONE) != 0 ? 1.0 : 0.0;
            beatGate = (enabled & BEAT) != 0 ? 1.0 : 0.0;
            externalGate = (enabled & EXTERNAL) != 0 ? 1.0 : 0.0;
            swellGate = (enabled & SWELL) != 0 ? 1.0 : 0.0;
            previousRaw = 0.0;
        }
        frequency = smooth(frequency, frequencyTarget);
        offset = smooth(offset, offsetTarget);
        shape = smooth(shape, shapeTarget);
        beatDepth = smooth(beatDepth, beatDepthTarget);
        swellDepth = smooth(swellDepth, swellDepthTarget);
        amplitude = smooth(amplitude, amplitudeTarget);
        fmMix = smooth(fmMix, fmTarget);
        courtesyPolarity = smooth(courtesyPolarity, polarityTarget);
        toneGate = smooth(toneGate, (enabled & TONE) != 0 ? 1.0 : 0.0);
        beatGate = smooth(beatGate, (enabled & BEAT) != 0 ? 1.0 : 0.0);
        externalGate = smooth(externalGate, (enabled & EXTERNAL) != 0 ? 1.0 : 0.0);
        swellGate = smooth(swellGate, (enabled & SWELL) != 0 ? 1.0 : 0.0);

        // Courtesy is always active. Zero offset holds the phase and its current voltage.
        double swellStep = Math.abs(offset) / SAMPLE_RATE;
        swellPhase = advance(swellPhase, swellStep);
        double swellWave = shapedWave(swellPhase, shape, swellStep, SWELL_EDGE_HALF_SAMPLES);
        double courtesyWave = shapedWave(swellPhase, shape, swellStep, COURTESY_EDGE_HALF_SAMPLES);
        courtesyOutput.SetValue(3.0 * courtesyWave * courtesyPolarity);

        double beatStep = clamp(frequency + offset, 0.0, 1200.0) / SAMPLE_RATE;
        if (beatGate > 0.0) {
            beatPhase = advance(beatPhase, beatStep);
        }
        double beatWave = shapedWave(beatPhase, shape, beatStep, BEAT_EDGE_HALF_SAMPLES);
        double instantFrequency = clamp(frequency * (1.0 + fmMix * beatGate * beatDepth * beatWave),
                0.0, SAMPLE_RATE * 0.495);
        if (toneGate > 0.0) {
            tonePhase = advance(tonePhase, instantFrequency / SAMPLE_RATE);
        }
        double external = 0.0;
        if (externalGate > 0.0 && externalInput.IsConnected()) {
            double input = externalInput.GetValue();
            external = Double.isFinite(input) ? clamp(input / 5.0, -16.0, 16.0) : 0.0;
        }
        double mix = externalGate * external + toneGate * Math.sin(TWO_PI * tonePhase)
                + beatGate * beatDepth * (1.0 - fmMix) * beatWave;
        // Above 100%, deepen the muted portion without reversing polarity.
        double fullSwellGain = Math.max(0.0, 1.0 - swellDepth * (0.5 - 0.5 * swellWave));
        double swellGain = 1.0 + swellGate * (fullSwellGain - 1.0);
        double raw = mix * swellGain * amplitude;
        // Lightweight 2x midpoint conditioning with averaging at the output.
        // Both evaluations stay within the soft output rails, including sudden external transients.
        double midpoint = 0.5 * (raw + previousRaw);
        double output = 0.5 * (midpoint + compressionResidual(midpoint)
                + raw + compressionResidual(raw));
        previousRaw = raw;
        mainOutput.SetValue(output);

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
        double input = 0.0;
        if ((enabledFunctions & EXTERNAL) != 0 && externalInput.IsConnected()) {
            input = externalInput.GetValue();
        }
        mainOutput.SetValue(input);
        courtesyOutput.SetValue(0.0);
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
        if (component == wholeKnob) return "Whole Frequency: " + Math.round(wholeKnob.GetValue()) + " Hz before multiplier";
        if (component == fractionKnob) return String.format(java.util.Locale.ROOT, "Fraction Frequency: %.6f Hz before multiplier", fractionKnob.GetValue());
        if (component == multiplierKnob) return "Frequency Multiplier: x" + (int) multiplier();
        if (component == frequencyDisplay || component == fractionFrequencyDisplay) return String.format(java.util.Locale.ROOT, "Base: %.6f Hz; Tone: %.6f Hz; right display shows hundredths", baseFrequency(), baseFrequency() * multiplier());
        if (component == offsetKnob) { double hz = offsetHz(offsetKnob.GetValue()); return String.format(java.util.Locale.ROOT, "Offset: %+.4f Hz; Swell/Courtesy: %.4f Hz", hz, Math.abs(hz)); }
        if (component == amplitudeKnob) { double p = amplitudeKnob.GetValue() / 15.0; return String.format(java.util.Locale.ROOT, "Amplitude: %.4f V nominal peak; compression above 3 V", 15.0 * p * p); }
        if (component == shapeKnob) return "Shape: rising ramp left; triangle center; falling saw right";
        if (component == beatDepthKnob) return String.format(java.util.Locale.ROOT, "Beat Depth: %.1f%%; second oscillator mix or linear FM depth", 100.0 * beatDepthKnob.GetValue());
        if (component == swellDepthKnob) return String.format(java.util.Locale.ROOT, "Swell Depth: %.1f%%; 100%% reaches silence; up to 200%% extends the silent portion without polarity inversion", 100.0 * swellDepthKnob.GetValue());
        if (component == externalButton) return "External: latch the external audio input into the main mix";
        if (component == toneButton) return "Tone: start/stop the main sine oscillator";
        if (component == beatButton) return "Beat: start/stop the second oscillator contribution; does not gate Courtesy or Swell";
        if (component == swellButton) return "Swell: amplitude-modulate the main mix at the OFFSET magnitude";
        if (component == fmSwitch) return "Beat Mix / FM: down mixes the second oscillator; up applies linear FM to Tone";
        if (component == courtesyPolaritySwitch) return "Courtesy Polarity: up normal; down inverted; affects Courtesy only";
        if (component == externalInput) return "External Input: 5 V nominal; enabled by External, then Swell and main Amplitude";
        if (component == mainOutput) return "Main Output: selected sources through Swell and the main amplitude/character stage";
        if (component == courtesyOutput) return "Courtesy: fixed +/-3 V SHAPE waveform at absolute OFFSET; holds voltage at zero; silent in host bypass";
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
        if (component == offsetKnob) {
            double hz = clamp(newValue, -65.0, 65.0);
            super.EditComponentValue(component, Math.copySign(Math.pow(Math.abs(hz) / 65.0, 0.2), hz), newText);
            return;
        }
        if (component == amplitudeKnob) {
            super.EditComponentValue(component, 15.0 * Math.sqrt(clamp(newValue, 0.0, 15.0) / 15.0), newText);
            return;
        }
        if (component == beatDepthKnob || component == swellDepthKnob) {
            super.EditComponentValue(component, clamp(newValue / 100.0, 0.0, component == swellDepthKnob ? 2.0 : 1.0), newText);
            return;
        }
        if (component == multiplierKnob) {
            super.EditComponentValue(component, newValue <= 1.0 ? 1.0 : newValue <= 10.0 ? 2.0 : 3.0, newText);
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
        if ("deepToneFunctions".equals(undoType) && Double.isFinite(newValue)) {
            enabledFunctions = ((int) Math.round(newValue)) & 15;
            updatePanel();
        }
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
        return new byte[] {1, (byte) enabledFunctions};
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
        if (stateInfo != null && stateInfo.length == 2 && stateInfo[0] == 1) {
            enabledFunctions = stateInfo[1] & 15;
            resumePending = true;
        }
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
    private VoltageImage image2;
    private VoltageLabel colophon;
    private VoltageLabel descriptionLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageSwitch courtesyPolaritySwitch;
    private VoltageSwitch fmSwitch;
    private VoltageAudioJack externalInput;
    private VoltageLabel externalLabel59;
    private VoltageButton externalButton;
    private VoltageLabel ampModLabel58;
    private VoltageLabel depthLabel57;
    private VoltageLabel panelLabel56;
    private VoltageLabel scale50Label55;
    private VoltageLabel panelLabel52;
    private VoltageLabel scale100Label51;
    private VoltageLabel scale0Label46;
    private VoltageKnob swellDepthKnob;
    private VoltageLabel offsetLabel42;
    private VoltageLabel depthLabel41;
    private VoltageLabel shapeLabel40;
    private VoltageLabel beatOscillatorLabel39;
    private VoltageLabel frequencyLabel3;
    private VoltageLabel amplitudeLabel35;
    private VoltageDigitalCounter fractionFrequencyDisplay;
    private VoltageDigitalCounter frequencyDisplay;
    private VoltageLabel panelLabel50;
    private VoltageLabel scale0Label54;
    private VoltageLabel panelLabel49;
    private VoltageLabel scale1Label45;
    private VoltageLabel scale1Label34;
    private VoltageLabel panelLabel48;
    private VoltageLabel scale50Label53;
    private VoltageLabel panelLabel47;
    private VoltageLabel cLabel60;
    private VoltageLabel tLabel43;
    private VoltageLabel panelLabel64;
    private VoltageLabel panelLabel63;
    private VoltageLabel mLabel62;
    private VoltageLabel fLabel61;
    private VoltageLabel scale100Label44;
    private VoltageLabel panelLabel38;
    private VoltageLabel panelLabel37;
    private VoltageLabel sLabel32;
    private VoltageLabel rLabel29;
    private VoltageLabel scale0Label31;
    private VoltageKnob offsetKnob;
    private VoltageKnob beatDepthKnob;
    private VoltageKnob shapeKnob;
    private VoltageLabel x100Label36;
    private VoltageLabel x10Label33;
    private VoltageLabel x1Label30;
    private VoltageKnob multiplierKnob;
    private VoltageLabel scale5Label28;
    private VoltageLabel scale8Label23;
    private VoltageLabel scale2Label22;
    private VoltageLabel scale1Label19;
    private VoltageLabel scale0Label12;
    private VoltageKnob fractionKnob;
    private VoltageLabel scale05Label18;
    private VoltageLabel scale06Label17;
    private VoltageLabel scale04Label16;
    private VoltageLabel scale03Label15;
    private VoltageLabel scale07Label14;
    private VoltageLabel scale08Label13;
    private VoltageLabel scale02Label11;
    private VoltageLabel scale01Label10;
    private VoltageLabel scale09Label9;
    private VoltageLabel scale10Label8;
    private VoltageLabel scale00Label7;
    private VoltageKnob wholeKnob;
    private VoltageKnob amplitudeKnob;
    private VoltageLabel swellLabel6;
    private VoltageLabel beatLabel5;
    private VoltageLabel toneLabel4;
    private VoltageButton toneButton;
    private VoltageButton beatButton;
    private VoltageButton swellButton;
    private VoltageAudioJack mainOutput;
    private VoltageAudioJack courtesyOutput;
    private VoltageLabel scale1A30DeepToneGenModLabel2;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double TWO_PI = 2.0 * Math.PI;
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE));
    // Smooth only ramp/saw resets, preserving the triangle and the rest of each slope.
    // Total reset spans: 6 ms for Swell, 2 ms for Courtesy and 1 ms for audible Beat.
    private static final double SWELL_EDGE_HALF_SAMPLES = 0.003 * SAMPLE_RATE;
    private static final double COURTESY_EDGE_HALF_SAMPLES = 0.001 * SAMPLE_RATE;
    private static final double BEAT_EDGE_HALF_SAMPLES = 0.0005 * SAMPLE_RATE;
    private static final int EXTERNAL = 1;
    private static final int TONE = 2;
    private static final int BEAT = 4;
    private static final int SWELL = 8;
    private volatile int enabledFunctions;
    private volatile boolean resumePending = true;
    private double frequency, offset, shape, beatDepth, swellDepth, amplitude;
    private double toneGate, beatGate, externalGate, swellGate, fmMix, courtesyPolarity;
    private double tonePhase, beatPhase, swellPhase, previousRaw;

    private static double clamp(double value, double low, double high) {
        return Math.max(low, Math.min(high, value));
    }

    private static double offsetHz(double position) {
        double square = position * position;
        return 65.0 * position * square * square;
    }

    private static double smooth(double current, double target) {
        double next = current + CONTROL_SMOOTH * (target - current);
        return Math.abs(target - next) < 1.0e-12 ? target : next;
    }

    private double baseFrequency() {
        return Math.round(wholeKnob.GetValue()) + fractionKnob.GetValue();
    }

    private double multiplier() {
        int selection = (int) Math.round(multiplierKnob.GetValue());
        return selection == 1 ? 1.0 : selection == 2 ? 10.0 : 100.0;
    }

    private static double advance(double phase, double step) {
        double next = phase + step;
        return next - Math.floor(next);
    }

    private static double edgeCorrection(double phase, double step) {
        if (step <= 0.0) {
            return 0.0;
        }
        if (phase < step) {
            double t = phase / step;
            return t + t - t * t - 1.0;
        }
        if (phase > 1.0 - step) {
            double t = (phase - 1.0) / step;
            return t * t + t + t + 1.0;
        }
        return 0.0;
    }

    private static double shapedWave(double phase, double shape, double step, double edgeHalfSamples) {
        double triangle = 1.0 - 4.0 * Math.abs(phase - 0.5);
        // Broaden the existing polynomial reset; cap it at a quarter of a cycle
        // so faster settings retain a distinct ramp. No whole-signal low-pass filter.
        double edgeWidth = Math.min(0.125, step * edgeHalfSamples);
        double rising = 2.0 * phase - 1.0 - edgeCorrection(phase, edgeWidth);
        double saw = shape < 0.0 ? rising : -rising;
        return triangle + Math.abs(shape) * (saw - triangle);
    }

    private static double compressionResidual(double value) {
        double excess = Math.abs(value) - 3.0;
        if (excess <= 0.0) {
            return 0.0;
        }
        return Math.copySign(3.0 + 11.0 * Math.tanh(excess / 11.0), value) - value;
    }

    private int functionBit(VoltageComponent component) {
        if (component == externalButton) return EXTERNAL;
        if (component == toneButton) return TONE;
        if (component == beatButton) return BEAT;
        if (component == swellButton) return SWELL;
        return 0;
    }

    private void updatePanel() {
        // Carry at FRACTION=1 so the two counters still describe the summed base.
        int hundredths = (int) Math.floor(baseFrequency() * 100.0 + 1.0e-9);
        frequencyDisplay.SetValue(hundredths / 100);
        fractionFrequencyDisplay.SetValue(hundredths % 100);
        int enabled = enabledFunctions;
        externalButton.UpdateGUIValue((enabled & EXTERNAL) != 0 ? 1.0 : 0.0);
        toneButton.UpdateGUIValue((enabled & TONE) != 0 ? 1.0 : 0.0);
        beatButton.UpdateGUIValue((enabled & BEAT) != 0 ? 1.0 : 0.0);
        swellButton.UpdateGUIValue((enabled & SWELL) != 0 ? 1.0 : 0.0);
    }

    //[/user-code-and-variables]
}

 