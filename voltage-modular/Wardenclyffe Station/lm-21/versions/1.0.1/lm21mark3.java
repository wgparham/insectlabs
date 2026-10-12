package com.insectlabs.lm21mark3;


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


public class lm21mark3 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public lm21mark3( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "lm-21 mk 3 - matrix mixer", ModuleType.ModuleType_Mixers, 6.4 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "9e08581a8f0e4f72adf2d7a3788c536c" );
    }

void InitializeControls()
{

        panelLine16 = new VoltageLine( "panelLine16", "Panel Line 16", this );
        AddComponent( panelLine16 );
        panelLine16.SetWantsMouseNotifications( false );
        panelLine16.SetLineColor( new Color( 147, 0, 0, 147 ) );
        panelLine16.SetLineWidth( (float)3 );
        panelLine16.SetStartPosition( 232, 277 );
        panelLine16.SetEndPosition( 282, 277 );
        panelLine16.SetHasArrow( false );
        panelLine16.SetArrowWidth( (float)8 );
        panelLine16.SetArrowLength( (float)12 );

        panelLine18 = new VoltageLine( "panelLine18", "Panel Line 18", this );
        AddComponent( panelLine18 );
        panelLine18.SetWantsMouseNotifications( false );
        panelLine18.SetLineColor( new Color( 147, 0, 0, 147 ) );
        panelLine18.SetLineWidth( (float)3 );
        panelLine18.SetStartPosition( 233, 276 );
        panelLine18.SetEndPosition( 282, 327 );
        panelLine18.SetHasArrow( false );
        panelLine18.SetArrowWidth( (float)8 );
        panelLine18.SetArrowLength( (float)12 );

        panelLine17 = new VoltageLine( "panelLine17", "Panel Line 17", this );
        AddComponent( panelLine17 );
        panelLine17.SetWantsMouseNotifications( false );
        panelLine17.SetLineColor( new Color( 147, 0, 0, 147 ) );
        panelLine17.SetLineWidth( (float)3 );
        panelLine17.SetStartPosition( 233, 278 );
        panelLine17.SetEndPosition( 232, 327 );
        panelLine17.SetHasArrow( false );
        panelLine17.SetArrowWidth( (float)8 );
        panelLine17.SetArrowLength( (float)12 );

        perspectiveKnob = new VoltageKnob( "perspectiveKnob", "Perspective", this, 0.0, 1.0, 0.0 );
        AddComponent( perspectiveKnob );
        perspectiveKnob.SetWantsMouseNotifications( false );
        perspectiveKnob.SetPosition( 320, 260 );
        perspectiveKnob.SetSize( 41, 41 );
        perspectiveKnob.SetSkin( "Cosmo v2 Med" );
        perspectiveKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        perspectiveKnob.SetKnobParams( 215, 145 );
        perspectiveKnob.DisplayValueInPercent( true );
        perspectiveKnob.SetKnobAdjustsRing( true );

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Label", this, "r." );
        AddComponent( manufacturerLabel );
        manufacturerLabel.SetWantsMouseNotifications( false );
        manufacturerLabel.SetPosition( 3, 337 );
        manufacturerLabel.SetSize( 20, 20 );
        manufacturerLabel.SetEditable( false, false );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        manufacturerLabel.SetBkColor( new Color( 35, 35, 35, 255 ) );
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

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Description Label", this, "matrix mixer – artificial acoustic distance generator" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( true );
        descriptionLabel.SetPosition( 3, 3 );
        descriptionLabel.SetSize( 454, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        descriptionLabel.SetBkColor( new Color( 35, 35, 35, 255 ) );
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

        numberLabel = new VoltageLabel( "numberLabel", "Number Label", this, "model lm-21 mk 3" );
        AddComponent( numberLabel );
        numberLabel.SetWantsMouseNotifications( false );
        numberLabel.SetPosition( 307, 335 );
        numberLabel.SetSize( 150, 13 );
        numberLabel.SetEditable( false, false );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        numberLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        numberLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        numberLabel.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        numberLabel.SetBorderSize( 0 );
        numberLabel.SetMultiLineEdit( false );
        numberLabel.SetIsNumberEditor( false );
        numberLabel.SetNumberEditorRange( 0, 100 );
        numberLabel.SetNumberEditorInterval( 1 );
        numberLabel.SetNumberEditorUsesMouseWheel( false );
        numberLabel.SetHasCustomTextHoverColor( false );
        numberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        numberLabel.SetFont( "Courier New", 13, true, false );

        input1Jack = new VoltageAudioJack( "input1Jack", "Input 1", this, JackType.JackType_AudioInput );
        AddComponent( input1Jack );
        input1Jack.SetWantsMouseNotifications( false );
        input1Jack.SetPosition( 5, 259 );
        input1Jack.SetSize( 37, 37 );
        input1Jack.SetSkin( "Dark Jack Straight" );

        input2Jack = new VoltageAudioJack( "input2Jack", "Input 2", this, JackType.JackType_AudioInput );
        AddComponent( input2Jack );
        input2Jack.SetWantsMouseNotifications( false );
        input2Jack.SetPosition( 55, 259 );
        input2Jack.SetSize( 37, 37 );
        input2Jack.SetSkin( "Dark Jack Straight" );

        input3Jack = new VoltageAudioJack( "input3Jack", "Input 3", this, JackType.JackType_AudioInput );
        AddComponent( input3Jack );
        input3Jack.SetWantsMouseNotifications( false );
        input3Jack.SetPosition( 105, 259 );
        input3Jack.SetSize( 37, 37 );
        input3Jack.SetSkin( "Dark Jack Straight" );

        input4Jack = new VoltageAudioJack( "input4Jack", "Input 4", this, JackType.JackType_AudioInput );
        AddComponent( input4Jack );
        input4Jack.SetWantsMouseNotifications( false );
        input4Jack.SetPosition( 155, 259 );
        input4Jack.SetSize( 37, 37 );
        input4Jack.SetSkin( "Dark Jack Straight" );

        matrixA1Knob = new VoltageKnob( "matrixA1Knob", "Input 1 to Output A", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixA1Knob );
        matrixA1Knob.SetWantsMouseNotifications( false );
        matrixA1Knob.SetPosition( 5, 64 );
        matrixA1Knob.SetSize( 40, 40 );
        matrixA1Knob.SetSkin( "Cosmo v2 Med" );
        matrixA1Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixA1Knob.SetKnobParams( 215, 145 );
        matrixA1Knob.DisplayValueInPercent( false );
        matrixA1Knob.SetKnobAdjustsRing( true );

        matrixB1Knob = new VoltageKnob( "matrixB1Knob", "Input 1 to Output B", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixB1Knob );
        matrixB1Knob.SetWantsMouseNotifications( false );
        matrixB1Knob.SetPosition( 5, 114 );
        matrixB1Knob.SetSize( 40, 40 );
        matrixB1Knob.SetSkin( "Cosmo v2 Med" );
        matrixB1Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixB1Knob.SetKnobParams( 215, 145 );
        matrixB1Knob.DisplayValueInPercent( false );
        matrixB1Knob.SetKnobAdjustsRing( true );

        matrixC1Knob = new VoltageKnob( "matrixC1Knob", "Input 1 to Output C", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixC1Knob );
        matrixC1Knob.SetWantsMouseNotifications( false );
        matrixC1Knob.SetPosition( 4, 164 );
        matrixC1Knob.SetSize( 40, 40 );
        matrixC1Knob.SetSkin( "Cosmo v2 Med" );
        matrixC1Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixC1Knob.SetKnobParams( 215, 145 );
        matrixC1Knob.DisplayValueInPercent( false );
        matrixC1Knob.SetKnobAdjustsRing( true );

        matrixD1Knob = new VoltageKnob( "matrixD1Knob", "Input 1 to Output D", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixD1Knob );
        matrixD1Knob.SetWantsMouseNotifications( false );
        matrixD1Knob.SetPosition( 5, 214 );
        matrixD1Knob.SetSize( 40, 40 );
        matrixD1Knob.SetSkin( "Cosmo v2 Med" );
        matrixD1Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixD1Knob.SetKnobParams( 215, 145 );
        matrixD1Knob.DisplayValueInPercent( false );
        matrixD1Knob.SetKnobAdjustsRing( true );

        matrixA2Knob = new VoltageKnob( "matrixA2Knob", "Input 2 to Output A", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixA2Knob );
        matrixA2Knob.SetWantsMouseNotifications( false );
        matrixA2Knob.SetPosition( 55, 64 );
        matrixA2Knob.SetSize( 40, 40 );
        matrixA2Knob.SetSkin( "Cosmo v2 Med" );
        matrixA2Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixA2Knob.SetKnobParams( 215, 145 );
        matrixA2Knob.DisplayValueInPercent( false );
        matrixA2Knob.SetKnobAdjustsRing( true );

        matrixB2Knob = new VoltageKnob( "matrixB2Knob", "Input 2 to Output B", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixB2Knob );
        matrixB2Knob.SetWantsMouseNotifications( false );
        matrixB2Knob.SetPosition( 55, 114 );
        matrixB2Knob.SetSize( 40, 40 );
        matrixB2Knob.SetSkin( "Cosmo v2 Med" );
        matrixB2Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixB2Knob.SetKnobParams( 215, 145 );
        matrixB2Knob.DisplayValueInPercent( false );
        matrixB2Knob.SetKnobAdjustsRing( true );

        matrixC2Knob = new VoltageKnob( "matrixC2Knob", "Input 2 to Output C", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixC2Knob );
        matrixC2Knob.SetWantsMouseNotifications( false );
        matrixC2Knob.SetPosition( 55, 164 );
        matrixC2Knob.SetSize( 40, 40 );
        matrixC2Knob.SetSkin( "Cosmo v2 Med" );
        matrixC2Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixC2Knob.SetKnobParams( 215, 145 );
        matrixC2Knob.DisplayValueInPercent( false );
        matrixC2Knob.SetKnobAdjustsRing( true );

        matrixD2Knob = new VoltageKnob( "matrixD2Knob", "Input 2 to Output D", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixD2Knob );
        matrixD2Knob.SetWantsMouseNotifications( false );
        matrixD2Knob.SetPosition( 55, 214 );
        matrixD2Knob.SetSize( 40, 40 );
        matrixD2Knob.SetSkin( "Cosmo v2 Med" );
        matrixD2Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixD2Knob.SetKnobParams( 215, 145 );
        matrixD2Knob.DisplayValueInPercent( false );
        matrixD2Knob.SetKnobAdjustsRing( true );

        matrixA3Knob = new VoltageKnob( "matrixA3Knob", "Input 3 to Output A", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixA3Knob );
        matrixA3Knob.SetWantsMouseNotifications( false );
        matrixA3Knob.SetPosition( 105, 64 );
        matrixA3Knob.SetSize( 40, 40 );
        matrixA3Knob.SetSkin( "Cosmo v2 Med" );
        matrixA3Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixA3Knob.SetKnobParams( 215, 145 );
        matrixA3Knob.DisplayValueInPercent( false );
        matrixA3Knob.SetKnobAdjustsRing( true );

        matrixB3Knob = new VoltageKnob( "matrixB3Knob", "Input 3 to Output B", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixB3Knob );
        matrixB3Knob.SetWantsMouseNotifications( false );
        matrixB3Knob.SetPosition( 105, 114 );
        matrixB3Knob.SetSize( 40, 40 );
        matrixB3Knob.SetSkin( "Cosmo v2 Med" );
        matrixB3Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixB3Knob.SetKnobParams( 215, 145 );
        matrixB3Knob.DisplayValueInPercent( false );
        matrixB3Knob.SetKnobAdjustsRing( true );

        matrixC3Knob = new VoltageKnob( "matrixC3Knob", "Input 3 to Output C", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixC3Knob );
        matrixC3Knob.SetWantsMouseNotifications( false );
        matrixC3Knob.SetPosition( 105, 164 );
        matrixC3Knob.SetSize( 40, 40 );
        matrixC3Knob.SetSkin( "Cosmo v2 Med" );
        matrixC3Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixC3Knob.SetKnobParams( 215, 145 );
        matrixC3Knob.DisplayValueInPercent( false );
        matrixC3Knob.SetKnobAdjustsRing( true );

        matrixD3Knob = new VoltageKnob( "matrixD3Knob", "Input 3 to Output D", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixD3Knob );
        matrixD3Knob.SetWantsMouseNotifications( false );
        matrixD3Knob.SetPosition( 105, 214 );
        matrixD3Knob.SetSize( 40, 40 );
        matrixD3Knob.SetSkin( "Cosmo v2 Med" );
        matrixD3Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixD3Knob.SetKnobParams( 215, 145 );
        matrixD3Knob.DisplayValueInPercent( false );
        matrixD3Knob.SetKnobAdjustsRing( true );

        matrixA4Knob = new VoltageKnob( "matrixA4Knob", "Input 4 to Output A", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixA4Knob );
        matrixA4Knob.SetWantsMouseNotifications( false );
        matrixA4Knob.SetPosition( 155, 64 );
        matrixA4Knob.SetSize( 40, 40 );
        matrixA4Knob.SetSkin( "Cosmo v2 Med" );
        matrixA4Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixA4Knob.SetKnobParams( 215, 145 );
        matrixA4Knob.DisplayValueInPercent( false );
        matrixA4Knob.SetKnobAdjustsRing( true );

        matrixB4Knob = new VoltageKnob( "matrixB4Knob", "Input 4 to Output B", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixB4Knob );
        matrixB4Knob.SetWantsMouseNotifications( false );
        matrixB4Knob.SetPosition( 155, 114 );
        matrixB4Knob.SetSize( 40, 40 );
        matrixB4Knob.SetSkin( "Cosmo v2 Med" );
        matrixB4Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixB4Knob.SetKnobParams( 215, 145 );
        matrixB4Knob.DisplayValueInPercent( false );
        matrixB4Knob.SetKnobAdjustsRing( true );

        matrixC4Knob = new VoltageKnob( "matrixC4Knob", "Input 4 to Output C", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixC4Knob );
        matrixC4Knob.SetWantsMouseNotifications( false );
        matrixC4Knob.SetPosition( 155, 164 );
        matrixC4Knob.SetSize( 40, 40 );
        matrixC4Knob.SetSkin( "Cosmo v2 Med" );
        matrixC4Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixC4Knob.SetKnobParams( 215, 145 );
        matrixC4Knob.DisplayValueInPercent( false );
        matrixC4Knob.SetKnobAdjustsRing( true );

        matrixD4Knob = new VoltageKnob( "matrixD4Knob", "Input 4 to Output D", this, -1.0, 1.0, 0.00 );
        AddComponent( matrixD4Knob );
        matrixD4Knob.SetWantsMouseNotifications( false );
        matrixD4Knob.SetPosition( 155, 214 );
        matrixD4Knob.SetSize( 40, 40 );
        matrixD4Knob.SetSkin( "Cosmo v2 Med" );
        matrixD4Knob.SetRange( -1.0, 1.0, 0.00, false, 0 );
        matrixD4Knob.SetKnobParams( 215, 145 );
        matrixD4Knob.DisplayValueInPercent( false );
        matrixD4Knob.SetKnobAdjustsRing( true );

        bassAKnob = new VoltageKnob( "bassAKnob", "Bass A", this, 0.0, 1.0, 0.5 );
        AddComponent( bassAKnob );
        bassAKnob.SetWantsMouseNotifications( false );
        bassAKnob.SetPosition( 211, 64 );
        bassAKnob.SetSize( 40, 40 );
        bassAKnob.SetSkin( "Cosmo v2 Med" );
        bassAKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        bassAKnob.SetKnobParams( 215, 145 );
        bassAKnob.DisplayValueInPercent( false );
        bassAKnob.SetKnobAdjustsRing( true );

        bassBKnob = new VoltageKnob( "bassBKnob", "Bass B", this, 0.0, 1.0, 0.5 );
        AddComponent( bassBKnob );
        bassBKnob.SetWantsMouseNotifications( false );
        bassBKnob.SetPosition( 211, 114 );
        bassBKnob.SetSize( 40, 40 );
        bassBKnob.SetSkin( "Cosmo v2 Med" );
        bassBKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        bassBKnob.SetKnobParams( 215, 145 );
        bassBKnob.DisplayValueInPercent( false );
        bassBKnob.SetKnobAdjustsRing( true );

        bassDKnob = new VoltageKnob( "bassDKnob", "Bass D", this, 0.0, 1.0, 0.5 );
        AddComponent( bassDKnob );
        bassDKnob.SetWantsMouseNotifications( false );
        bassDKnob.SetPosition( 211, 209 );
        bassDKnob.SetSize( 40, 40 );
        bassDKnob.SetSkin( "Cosmo v2 Med" );
        bassDKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        bassDKnob.SetKnobParams( 215, 145 );
        bassDKnob.DisplayValueInPercent( false );
        bassDKnob.SetKnobAdjustsRing( true );

        bassCKnob = new VoltageKnob( "bassCKnob", "Bass C", this, 0.0, 1.0, 0.5 );
        AddComponent( bassCKnob );
        bassCKnob.SetWantsMouseNotifications( false );
        bassCKnob.SetPosition( 211, 164 );
        bassCKnob.SetSize( 40, 40 );
        bassCKnob.SetSkin( "Cosmo v2 Med" );
        bassCKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        bassCKnob.SetKnobParams( 215, 145 );
        bassCKnob.DisplayValueInPercent( false );
        bassCKnob.SetKnobAdjustsRing( true );

        trebleAKnob = new VoltageKnob( "trebleAKnob", "Treble A", this, 0.0, 1.0, 0.5 );
        AddComponent( trebleAKnob );
        trebleAKnob.SetWantsMouseNotifications( false );
        trebleAKnob.SetPosition( 262, 64 );
        trebleAKnob.SetSize( 40, 40 );
        trebleAKnob.SetSkin( "Cosmo v2 Med" );
        trebleAKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        trebleAKnob.SetKnobParams( 215, 145 );
        trebleAKnob.DisplayValueInPercent( false );
        trebleAKnob.SetKnobAdjustsRing( true );

        trebleBKnob = new VoltageKnob( "trebleBKnob", "Treble B", this, 0.0, 1.0, 0.5 );
        AddComponent( trebleBKnob );
        trebleBKnob.SetWantsMouseNotifications( false );
        trebleBKnob.SetPosition( 262, 114 );
        trebleBKnob.SetSize( 40, 40 );
        trebleBKnob.SetSkin( "Cosmo v2 Med" );
        trebleBKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        trebleBKnob.SetKnobParams( 215, 145 );
        trebleBKnob.DisplayValueInPercent( false );
        trebleBKnob.SetKnobAdjustsRing( true );

        trebleCKnob = new VoltageKnob( "trebleCKnob", "Treble C", this, 0.0, 1.0, 0.5 );
        AddComponent( trebleCKnob );
        trebleCKnob.SetWantsMouseNotifications( false );
        trebleCKnob.SetPosition( 262, 164 );
        trebleCKnob.SetSize( 40, 40 );
        trebleCKnob.SetSkin( "Cosmo v2 Med" );
        trebleCKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        trebleCKnob.SetKnobParams( 215, 145 );
        trebleCKnob.DisplayValueInPercent( false );
        trebleCKnob.SetKnobAdjustsRing( true );

        trebleDKnob = new VoltageKnob( "trebleDKnob", "Treble D", this, 0.0, 1.0, 0.5 );
        AddComponent( trebleDKnob );
        trebleDKnob.SetWantsMouseNotifications( false );
        trebleDKnob.SetPosition( 262, 209 );
        trebleDKnob.SetSize( 40, 40 );
        trebleDKnob.SetSkin( "Cosmo v2 Med" );
        trebleDKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        trebleDKnob.SetKnobParams( 215, 145 );
        trebleDKnob.DisplayValueInPercent( false );
        trebleDKnob.SetKnobAdjustsRing( true );

        mixAKnob = new VoltageKnob( "mixAKnob", "Mix A", this, 0.0, 1.0, 0.0 );
        AddComponent( mixAKnob );
        mixAKnob.SetWantsMouseNotifications( false );
        mixAKnob.SetPosition( 348, 64 );
        mixAKnob.SetSize( 40, 40 );
        mixAKnob.SetSkin( "Cosmo v2 Med" );
        mixAKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        mixAKnob.SetKnobParams( 215, 145 );
        mixAKnob.DisplayValueInPercent( false );
        mixAKnob.SetKnobAdjustsRing( true );

        mixBKnob = new VoltageKnob( "mixBKnob", "Mix B", this, 0.0, 1.0, 0.0 );
        AddComponent( mixBKnob );
        mixBKnob.SetWantsMouseNotifications( false );
        mixBKnob.SetPosition( 348, 114 );
        mixBKnob.SetSize( 40, 40 );
        mixBKnob.SetSkin( "Cosmo v2 Med" );
        mixBKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        mixBKnob.SetKnobParams( 215, 145 );
        mixBKnob.DisplayValueInPercent( false );
        mixBKnob.SetKnobAdjustsRing( true );

        mixCKnob = new VoltageKnob( "mixCKnob", "Mix C", this, 0.0, 1.0, 0.0 );
        AddComponent( mixCKnob );
        mixCKnob.SetWantsMouseNotifications( false );
        mixCKnob.SetPosition( 348, 164 );
        mixCKnob.SetSize( 40, 40 );
        mixCKnob.SetSkin( "Cosmo v2 Med" );
        mixCKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        mixCKnob.SetKnobParams( 215, 145 );
        mixCKnob.DisplayValueInPercent( false );
        mixCKnob.SetKnobAdjustsRing( true );

        mixDKnob = new VoltageKnob( "mixDKnob", "Mix D", this, 0.0, 1.0, 0.0 );
        AddComponent( mixDKnob );
        mixDKnob.SetWantsMouseNotifications( false );
        mixDKnob.SetPosition( 348, 214 );
        mixDKnob.SetSize( 40, 40 );
        mixDKnob.SetSkin( "Cosmo v2 Med" );
        mixDKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        mixDKnob.SetKnobParams( 215, 145 );
        mixDKnob.DisplayValueInPercent( false );
        mixDKnob.SetKnobAdjustsRing( true );

        input1Label = new VoltageLabel( "input1Label", "Input1 Label", this, "I" );
        AddComponent( input1Label );
        input1Label.SetWantsMouseNotifications( false );
        input1Label.SetPosition( 12, 294 );
        input1Label.SetSize( 21, 21 );
        input1Label.SetEditable( false, false );
        input1Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        input1Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        input1Label.SetColor( new Color( 232, 232, 232, 255 ) );
        input1Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        input1Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        input1Label.SetBorderSize( 1 );
        input1Label.SetMultiLineEdit( false );
        input1Label.SetIsNumberEditor( false );
        input1Label.SetNumberEditorRange( 0, 100 );
        input1Label.SetNumberEditorInterval( 1 );
        input1Label.SetNumberEditorUsesMouseWheel( false );
        input1Label.SetHasCustomTextHoverColor( false );
        input1Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        input1Label.SetFont( "Times New Roman", 14, true, false );

        input2Label = new VoltageLabel( "input2Label", "Input2 Label", this, "II" );
        AddComponent( input2Label );
        input2Label.SetWantsMouseNotifications( false );
        input2Label.SetPosition( 63, 294 );
        input2Label.SetSize( 21, 21 );
        input2Label.SetEditable( false, false );
        input2Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        input2Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        input2Label.SetColor( new Color( 232, 232, 232, 255 ) );
        input2Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        input2Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        input2Label.SetBorderSize( 1 );
        input2Label.SetMultiLineEdit( false );
        input2Label.SetIsNumberEditor( false );
        input2Label.SetNumberEditorRange( 0, 100 );
        input2Label.SetNumberEditorInterval( 1 );
        input2Label.SetNumberEditorUsesMouseWheel( false );
        input2Label.SetHasCustomTextHoverColor( false );
        input2Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        input2Label.SetFont( "Times New Roman", 14, true, false );

        input3Label = new VoltageLabel( "input3Label", "Input3 Label", this, "III" );
        AddComponent( input3Label );
        input3Label.SetWantsMouseNotifications( false );
        input3Label.SetPosition( 113, 294 );
        input3Label.SetSize( 21, 21 );
        input3Label.SetEditable( false, false );
        input3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        input3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        input3Label.SetColor( new Color( 232, 232, 232, 255 ) );
        input3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        input3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        input3Label.SetBorderSize( 1 );
        input3Label.SetMultiLineEdit( false );
        input3Label.SetIsNumberEditor( false );
        input3Label.SetNumberEditorRange( 0, 100 );
        input3Label.SetNumberEditorInterval( 1 );
        input3Label.SetNumberEditorUsesMouseWheel( false );
        input3Label.SetHasCustomTextHoverColor( false );
        input3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        input3Label.SetFont( "Times New Roman", 14, true, false );

        input4Label = new VoltageLabel( "input4Label", "Input4 Label", this, "IIII" );
        AddComponent( input4Label );
        input4Label.SetWantsMouseNotifications( false );
        input4Label.SetPosition( 163, 294 );
        input4Label.SetSize( 21, 21 );
        input4Label.SetEditable( false, false );
        input4Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        input4Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        input4Label.SetColor( new Color( 232, 232, 232, 255 ) );
        input4Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        input4Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        input4Label.SetBorderSize( 1 );
        input4Label.SetMultiLineEdit( false );
        input4Label.SetIsNumberEditor( false );
        input4Label.SetNumberEditorRange( 0, 100 );
        input4Label.SetNumberEditorInterval( 1 );
        input4Label.SetNumberEditorUsesMouseWheel( false );
        input4Label.SetHasCustomTextHoverColor( false );
        input4Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        input4Label.SetFont( "Times New Roman", 14, true, false );

        colophon = new VoltageLabel( "colophon", "colophon", this, "refuge acoustical research oklahoma city, OK" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 25, 335 );
        colophon.SetSize( 98, 23 );
        colophon.SetEditable( false, false );
        colophon.SetJustificationFlags( VoltageLabel.Justification.Left );
        colophon.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        colophon.SetColor( new Color( 232, 232, 232, 255 ) );
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
        colophon.SetFont( "Arial Black", 9, true, false );

        labelBass = new VoltageLabel( "labelBass", "BASS Label", this, "BASS" );
        AddComponent( labelBass );
        labelBass.SetWantsMouseNotifications( false );
        labelBass.SetPosition( 205, 39 );
        labelBass.SetSize( 50, 21 );
        labelBass.SetEditable( false, false );
        labelBass.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBass.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBass.SetColor( new Color( 232, 232, 232, 255 ) );
        labelBass.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBass.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBass.SetBorderSize( 1 );
        labelBass.SetMultiLineEdit( false );
        labelBass.SetIsNumberEditor( false );
        labelBass.SetNumberEditorRange( 0, 100 );
        labelBass.SetNumberEditorInterval( 1 );
        labelBass.SetNumberEditorUsesMouseWheel( false );
        labelBass.SetHasCustomTextHoverColor( false );
        labelBass.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBass.SetFont( "Arial", 9, true, false );

        labelTreble = new VoltageLabel( "labelTreble", "TREBLE Label", this, "TREBLE" );
        AddComponent( labelTreble );
        labelTreble.SetWantsMouseNotifications( false );
        labelTreble.SetPosition( 255, 39 );
        labelTreble.SetSize( 50, 21 );
        labelTreble.SetEditable( false, false );
        labelTreble.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTreble.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTreble.SetColor( new Color( 232, 232, 232, 255 ) );
        labelTreble.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTreble.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTreble.SetBorderSize( 1 );
        labelTreble.SetMultiLineEdit( false );
        labelTreble.SetIsNumberEditor( false );
        labelTreble.SetNumberEditorRange( 0, 100 );
        labelTreble.SetNumberEditorInterval( 1 );
        labelTreble.SetNumberEditorUsesMouseWheel( false );
        labelTreble.SetHasCustomTextHoverColor( false );
        labelTreble.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTreble.SetFont( "Arial", 9, true, false );

        labelMix = new VoltageLabel( "labelMix", "MIX Label", this, "MIX" );
        AddComponent( labelMix );
        labelMix.SetWantsMouseNotifications( false );
        labelMix.SetPosition( 343, 39 );
        labelMix.SetSize( 50, 21 );
        labelMix.SetEditable( false, false );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMix.SetColor( new Color( 232, 232, 232, 255 ) );
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
        labelMix.SetFont( "Arial", 9, true, false );

        outputAJack = new VoltageAudioJack( "outputAJack", "Output A", this, JackType.JackType_AudioOutput );
        AddComponent( outputAJack );
        outputAJack.SetWantsMouseNotifications( false );
        outputAJack.SetPosition( 395, 65 );
        outputAJack.SetSize( 37, 37 );
        outputAJack.SetSkin( "Rotated Half" );

        outputBJack = new VoltageAudioJack( "outputBJack", "Output B", this, JackType.JackType_AudioOutput );
        AddComponent( outputBJack );
        outputBJack.SetWantsMouseNotifications( false );
        outputBJack.SetPosition( 395, 115 );
        outputBJack.SetSize( 37, 37 );
        outputBJack.SetSkin( "Rotated Half" );

        outputCJack = new VoltageAudioJack( "outputCJack", "Output C", this, JackType.JackType_AudioOutput );
        AddComponent( outputCJack );
        outputCJack.SetWantsMouseNotifications( false );
        outputCJack.SetPosition( 395, 165 );
        outputCJack.SetSize( 37, 37 );
        outputCJack.SetSkin( "Rotated Half" );

        labelA = new VoltageLabel( "labelA", "Output A Label", this, "A" );
        AddComponent( labelA );
        labelA.SetWantsMouseNotifications( false );
        labelA.SetPosition( 431, 69 );
        labelA.SetSize( 21, 21 );
        labelA.SetEditable( false, false );
        labelA.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelA.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelA.SetColor( new Color( 232, 232, 232, 255 ) );
        labelA.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelA.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelA.SetBorderSize( 1 );
        labelA.SetMultiLineEdit( false );
        labelA.SetIsNumberEditor( false );
        labelA.SetNumberEditorRange( 0, 100 );
        labelA.SetNumberEditorInterval( 1 );
        labelA.SetNumberEditorUsesMouseWheel( false );
        labelA.SetHasCustomTextHoverColor( false );
        labelA.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelA.SetFont( "Arial", 14, true, false );

        labelB = new VoltageLabel( "labelB", "Output B Label", this, "B" );
        AddComponent( labelB );
        labelB.SetWantsMouseNotifications( false );
        labelB.SetPosition( 431, 119 );
        labelB.SetSize( 21, 21 );
        labelB.SetEditable( false, false );
        labelB.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelB.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelB.SetColor( new Color( 232, 232, 232, 255 ) );
        labelB.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelB.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelB.SetBorderSize( 1 );
        labelB.SetMultiLineEdit( false );
        labelB.SetIsNumberEditor( false );
        labelB.SetNumberEditorRange( 0, 100 );
        labelB.SetNumberEditorInterval( 1 );
        labelB.SetNumberEditorUsesMouseWheel( false );
        labelB.SetHasCustomTextHoverColor( false );
        labelB.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelB.SetFont( "Arial", 14, true, false );

        labelC = new VoltageLabel( "labelC", "Output C label", this, "C" );
        AddComponent( labelC );
        labelC.SetWantsMouseNotifications( false );
        labelC.SetPosition( 431, 169 );
        labelC.SetSize( 21, 21 );
        labelC.SetEditable( false, false );
        labelC.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelC.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelC.SetColor( new Color( 232, 232, 232, 255 ) );
        labelC.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelC.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelC.SetBorderSize( 1 );
        labelC.SetMultiLineEdit( false );
        labelC.SetIsNumberEditor( false );
        labelC.SetNumberEditorRange( 0, 100 );
        labelC.SetNumberEditorInterval( 1 );
        labelC.SetNumberEditorUsesMouseWheel( false );
        labelC.SetHasCustomTextHoverColor( false );
        labelC.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelC.SetFont( "Arial", 14, true, false );

        labelD = new VoltageLabel( "labelD", "Output D Label", this, "D" );
        AddComponent( labelD );
        labelD.SetWantsMouseNotifications( false );
        labelD.SetPosition( 431, 219 );
        labelD.SetSize( 21, 21 );
        labelD.SetEditable( false, false );
        labelD.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelD.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelD.SetColor( new Color( 232, 232, 232, 255 ) );
        labelD.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelD.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelD.SetBorderSize( 1 );
        labelD.SetMultiLineEdit( false );
        labelD.SetIsNumberEditor( false );
        labelD.SetNumberEditorRange( 0, 100 );
        labelD.SetNumberEditorInterval( 1 );
        labelD.SetNumberEditorUsesMouseWheel( false );
        labelD.SetHasCustomTextHoverColor( false );
        labelD.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelD.SetFont( "Arial", 14, true, false );

        outputDJack = new VoltageAudioJack( "outputDJack", "Output D", this, JackType.JackType_AudioOutput );
        AddComponent( outputDJack );
        outputDJack.SetWantsMouseNotifications( false );
        outputDJack.SetPosition( 395, 215 );
        outputDJack.SetSize( 37, 37 );
        outputDJack.SetSkin( "Rotated Half" );

        fullMixOutput = new VoltageAudioJack( "fullMixOutput", "Full Mix Output", this, JackType.JackType_AudioOutput );
        AddComponent( fullMixOutput );
        fullMixOutput.SetWantsMouseNotifications( false );
        fullMixOutput.SetPosition( 404, 265 );
        fullMixOutput.SetSize( 25, 25 );
        fullMixOutput.SetSkin( "Mini Jack 25px" );

        distanceAKnob = new VoltageKnob( "distanceAKnob", "Distance A", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceAKnob );
        distanceAKnob.SetWantsMouseNotifications( false );
        distanceAKnob.SetPosition( 311, 69 );
        distanceAKnob.SetSize( 25, 25 );
        distanceAKnob.SetSkin( "Cosmo Small" );
        distanceAKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceAKnob.SetKnobParams( 215, 145 );
        distanceAKnob.DisplayValueInPercent( false );
        distanceAKnob.SetKnobAdjustsRing( true );

        distanceBKnob = new VoltageKnob( "distanceBKnob", "Distance B", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceBKnob );
        distanceBKnob.SetWantsMouseNotifications( false );
        distanceBKnob.SetPosition( 311, 119 );
        distanceBKnob.SetSize( 25, 25 );
        distanceBKnob.SetSkin( "Cosmo Small" );
        distanceBKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceBKnob.SetKnobParams( 215, 145 );
        distanceBKnob.DisplayValueInPercent( false );
        distanceBKnob.SetKnobAdjustsRing( true );

        distanceCKnob = new VoltageKnob( "distanceCKnob", "Distance C", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceCKnob );
        distanceCKnob.SetWantsMouseNotifications( false );
        distanceCKnob.SetPosition( 311, 169 );
        distanceCKnob.SetSize( 25, 25 );
        distanceCKnob.SetSkin( "Cosmo Small" );
        distanceCKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceCKnob.SetKnobParams( 215, 145 );
        distanceCKnob.DisplayValueInPercent( false );
        distanceCKnob.SetKnobAdjustsRing( true );

        distanceDKnob = new VoltageKnob( "distanceDKnob", "Distance D", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceDKnob );
        distanceDKnob.SetWantsMouseNotifications( false );
        distanceDKnob.SetPosition( 311, 214 );
        distanceDKnob.SetSize( 25, 25 );
        distanceDKnob.SetSkin( "Cosmo Small" );
        distanceDKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceDKnob.SetKnobParams( 215, 145 );
        distanceDKnob.DisplayValueInPercent( false );
        distanceDKnob.SetKnobAdjustsRing( true );

        perspectiveLabel = new VoltageLabel( "perspectiveLabel", "Perspective Label", this, "PERSPECTIVE" );
        AddComponent( perspectiveLabel );
        perspectiveLabel.SetWantsMouseNotifications( false );
        perspectiveLabel.SetPosition( 312, 299 );
        perspectiveLabel.SetSize( 60, 21 );
        perspectiveLabel.SetEditable( false, false );
        perspectiveLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        perspectiveLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        perspectiveLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        perspectiveLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        perspectiveLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        perspectiveLabel.SetBorderSize( 1 );
        perspectiveLabel.SetMultiLineEdit( false );
        perspectiveLabel.SetIsNumberEditor( false );
        perspectiveLabel.SetNumberEditorRange( 0, 100 );
        perspectiveLabel.SetNumberEditorInterval( 1 );
        perspectiveLabel.SetNumberEditorUsesMouseWheel( false );
        perspectiveLabel.SetHasCustomTextHoverColor( false );
        perspectiveLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        perspectiveLabel.SetFont( "Arial", 9, true, false );

        labelDistance = new VoltageLabel( "labelDistance", "DISTANCE Label", this, "DIST." );
        AddComponent( labelDistance );
        labelDistance.SetWantsMouseNotifications( false );
        labelDistance.SetPosition( 305, 39 );
        labelDistance.SetSize( 30, 21 );
        labelDistance.SetEditable( false, false );
        labelDistance.SetJustificationFlags( VoltageLabel.Justification.Right );
        labelDistance.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDistance.SetColor( new Color( 232, 232, 232, 255 ) );
        labelDistance.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDistance.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDistance.SetBorderSize( 1 );
        labelDistance.SetMultiLineEdit( false );
        labelDistance.SetIsNumberEditor( false );
        labelDistance.SetNumberEditorRange( 0, 100 );
        labelDistance.SetNumberEditorInterval( 1 );
        labelDistance.SetNumberEditorUsesMouseWheel( false );
        labelDistance.SetHasCustomTextHoverColor( false );
        labelDistance.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDistance.SetFont( "Arial", 9, true, false );

        catalogLabel = new VoltageLabel( "catalogLabel", "Catalog Label", this, "matrix mixer/" );
        AddComponent( catalogLabel );
        catalogLabel.SetWantsMouseNotifications( false );
        catalogLabel.SetPosition( 332, 318 );
        catalogLabel.SetSize( 125, 13 );
        catalogLabel.SetEditable( false, false );
        catalogLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        catalogLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        catalogLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        catalogLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        catalogLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        catalogLabel.SetBorderSize( 1 );
        catalogLabel.SetMultiLineEdit( false );
        catalogLabel.SetIsNumberEditor( false );
        catalogLabel.SetNumberEditorRange( 0, 100 );
        catalogLabel.SetNumberEditorInterval( 1 );
        catalogLabel.SetNumberEditorUsesMouseWheel( false );
        catalogLabel.SetHasCustomTextHoverColor( false );
        catalogLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        catalogLabel.SetFont( "Arial Black", 9, true, false );

        labelInputs = new VoltageLabel( "labelInputs", "INPUTS Label", this, "INPUTS" );
        AddComponent( labelInputs );
        labelInputs.SetWantsMouseNotifications( false );
        labelInputs.SetPosition( 75, 39 );
        labelInputs.SetSize( 50, 21 );
        labelInputs.SetEditable( false, false );
        labelInputs.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelInputs.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelInputs.SetColor( new Color( 232, 232, 232, 255 ) );
        labelInputs.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelInputs.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelInputs.SetBorderSize( 1 );
        labelInputs.SetMultiLineEdit( false );
        labelInputs.SetIsNumberEditor( false );
        labelInputs.SetNumberEditorRange( 0, 100 );
        labelInputs.SetNumberEditorInterval( 1 );
        labelInputs.SetNumberEditorUsesMouseWheel( false );
        labelInputs.SetHasCustomTextHoverColor( false );
        labelInputs.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelInputs.SetFont( "Arial", 9, true, false );

        multIn = new VoltageAudioJack( "multIn", "Multiple In", this, JackType.JackType_AudioInput );
        AddComponent( multIn );
        multIn.SetWantsMouseNotifications( false );
        multIn.SetPosition( 220, 265 );
        multIn.SetSize( 25, 25 );
        multIn.SetSkin( "Jack Round 25px" );

        multOut1 = new VoltageAudioJack( "multOut1", "Multiple Output 1", this, JackType.JackType_AudioOutput );
        AddComponent( multOut1 );
        multOut1.SetWantsMouseNotifications( false );
        multOut1.SetPosition( 270, 265 );
        multOut1.SetSize( 25, 25 );
        multOut1.SetSkin( "Mini Jack 25px" );

        multOut2 = new VoltageAudioJack( "multOut2", "Multiple Output 2", this, JackType.JackType_AudioOutput );
        AddComponent( multOut2 );
        multOut2.SetWantsMouseNotifications( false );
        multOut2.SetPosition( 270, 315 );
        multOut2.SetSize( 25, 25 );
        multOut2.SetSkin( "Mini Jack 25px" );

        multOut3 = new VoltageAudioJack( "multOut3", "Multiple Output 3", this, JackType.JackType_AudioOutput );
        AddComponent( multOut3 );
        multOut3.SetWantsMouseNotifications( false );
        multOut3.SetPosition( 220, 315 );
        multOut3.SetSize( 25, 25 );
        multOut3.SetSkin( "Mini Jack 25px" );

        acousticDistanceLabel = new VoltageLabel( "acousticDistanceLabel", "Acoustic Distance Label", this, "artificial acoustic distance generator" );
        AddComponent( acousticDistanceLabel );
        acousticDistanceLabel.SetWantsMouseNotifications( false );
        acousticDistanceLabel.SetPosition( 330, 324 );
        acousticDistanceLabel.SetSize( 125, 13 );
        acousticDistanceLabel.SetEditable( false, false );
        acousticDistanceLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        acousticDistanceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        acousticDistanceLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        acousticDistanceLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        acousticDistanceLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        acousticDistanceLabel.SetBorderSize( 1 );
        acousticDistanceLabel.SetMultiLineEdit( false );
        acousticDistanceLabel.SetIsNumberEditor( false );
        acousticDistanceLabel.SetNumberEditorRange( 0, 100 );
        acousticDistanceLabel.SetNumberEditorInterval( 1 );
        acousticDistanceLabel.SetNumberEditorUsesMouseWheel( false );
        acousticDistanceLabel.SetHasCustomTextHoverColor( false );
        acousticDistanceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        acousticDistanceLabel.SetFont( "Arial Black", 9, true, false );

        powerSwitch = new VoltageSwitch( "powerSwitch", "Power Switch", this, 0 );
        AddComponent( powerSwitch );
        powerSwitch.SetWantsMouseNotifications( false );
        powerSwitch.SetPosition( 400, 295 );
        powerSwitch.SetSize( 40, 20 );
        powerSwitch.SetSkin( "Rocker Switch Plastic Orange Hor" );

        powerIndicator = new VoltageLED( "powerIndicator", "Power Indicator", this );
        AddComponent( powerIndicator );
        powerIndicator.SetWantsMouseNotifications( false );
        powerIndicator.SetPosition( 20, 35 );
        powerIndicator.SetSize( 15, 15 );
        powerIndicator.SetSkin( "2500 Lamp Red" );
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

        syncControlTargets();

        boolean initiallyPowered = powerSwitch.GetValue() >= 0.5;
        powerGain = 0.0;
        powerOffStateCleared = !initiallyPowered;
        displayedPowerLevel = 0.0;
        powerDisplayCountdown = 1;
        powerIndicator.SetValue( 0.0 );

        // Start smoothing from the actual panel state so a newly-created module
        // does not glide in from arbitrary values.
        for ( int r = 0; r < 4; r++ )
        {
            for ( int c = 0; c < 4; c++ )
                matrixSmooth[r][c] = matrixTarget[r][c];

            bassGainSmooth[r] = bassGainTarget[r];
            trebleGainSmooth[r] = trebleGainTarget[r];
            distanceSmooth[r] = distanceTarget[r];
            levelSmooth[r] = levelTarget[r];

            reverbs[r] = new ReverbSCMono();
        }

        feedbackSmooth = feedbackTarget;
        dampFactSmooth = dampFactTarget;
        pitchModSmooth = pitchModTarget;
        ageSmooth = ageTarget;
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
                syncControlTargets();
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
                syncControlTargets();
                resumePending = true;
            }
            break;
        
            case Variation_Loading_Start:    // sent when a variation is about to load
            {
            }
            break;
        
            case Variation_Loading_Finish:   // sent when a variation has just finished loading
            {
                syncControlTargets();
                resumePending = true;
            }
            break;
        
            case Tempo_Changed:     // doubleValue is the new tempo
            {
            }
            break;
        
            case Randomized:     // called when the module's controls get randomized
            {
                syncControlTargets();
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
                syncControlTargets();
                resumePending = true;
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

        boolean powered = powerSwitch.GetValue() >= 0.5;
        updatePowerState( powered );

        if ( resumePending )
        {
            clearProcessingState();
            resumePending = false;
        }

        if ( !powered && powerGain <= POWER_CUTOFF )
        {
            powerGain = 0.0;
            if ( !powerOffStateCleared )
            {
                clearProcessingState();
                powerOffStateCleared = true;
            }
            outputAJack.SetValue( 0.0 );
            outputBJack.SetValue( 0.0 );
            outputCJack.SetValue( 0.0 );
            outputDJack.SetValue( 0.0 );
            fullMixOutput.SetValue( 0.0 );
            processMultiple();
            return;
        }
        if ( powered )
            powerOffStateCleared = false;

        // Smooth all front-panel controls.  This prevents zippering when matrix
        // coefficients, EQ, output levels, or distance amounts are moved.
        for ( int r = 0; r < 4; r++ )
        {
            for ( int c = 0; c < 4; c++ )
                matrixSmooth[r][c] += CONTROL_SMOOTH * (matrixTarget[r][c] - matrixSmooth[r][c]);

            bassGainSmooth[r] += CONTROL_SMOOTH * (bassGainTarget[r] - bassGainSmooth[r]);
            trebleGainSmooth[r] += CONTROL_SMOOTH * (trebleGainTarget[r] - trebleGainSmooth[r]);
            distanceSmooth[r] += CONTROL_SMOOTH * (distanceTarget[r] - distanceSmooth[r]);
            levelSmooth[r] += CONTROL_SMOOTH * (levelTarget[r] - levelSmooth[r]);
        }

        feedbackSmooth += PARAM_SMOOTH * (feedbackTarget - feedbackSmooth);
        dampFactSmooth += PARAM_SMOOTH * (dampFactTarget - dampFactSmooth);
        pitchModSmooth += PARAM_SMOOTH * (pitchModTarget - pitchModSmooth);
        ageSmooth += PARAM_SMOOTH * (ageTarget - ageSmooth);

        // The original 984 is AC-coupled.  A very-low corner keeps this an
        // audio mixer and prevents DC from building up in the reverb network.
        double in1 = acCoupleInput( 0, readInput( input1Jack ) );
        double in2 = acCoupleInput( 1, readInput( input2Jack ) );
        double in3 = acCoupleInput( 2, readInput( input3Jack ) );
        double in4 = acCoupleInput( 3, readInput( input4Jack ) );

        // Four independent matrix rows.  No normalization is applied: just as
        // with the 984, piling several sources into one row can push the analog
        // model harder.
        double rowA = matrixMix( 0, in1, in2, in3, in4 );
        double rowB = matrixMix( 1, in1, in2, in3, in4 );
        double rowC = matrixMix( 2, in1, in2, in3, in4 );
        double rowD = matrixMix( 3, in1, in2, in3, in4 );

        double outA = processOutputChannel( 0, rowA ) * powerGain;
        double outB = processOutputChannel( 1, rowB ) * powerGain;
        double outC = processOutputChannel( 2, rowC ) * powerGain;
        double outD = processOutputChannel( 3, rowD ) * powerGain;

        outputAJack.SetValue( outA );
        outputBJack.SetValue( outB );
        outputCJack.SetValue( outC );
        outputDJack.SetValue( outD );

        // Courtesy full mix.  Average rather than sum so four hot rows do not
        // automatically create a 4x level jump.
        fullMixOutput.SetValue( gentleFullMix( 0.25 * (outA + outB + outC + outD) ) );

        // Passive-style front-panel multiple, implemented as ideal buffered
        // copies in the digital module.
        processMultiple();
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

        // There is no unique "straight through" path for a matrix mixer.
        // Use the natural one-to-one mapping while bypassed.
        double a = readInput( input1Jack );
        double b = readInput( input2Jack );
        double c = readInput( input3Jack );
        double d = readInput( input4Jack );

        outputAJack.SetValue( a );
        outputBJack.SetValue( b );
        outputCJack.SetValue( c );
        outputDJack.SetValue( d );
        fullMixOutput.SetValue( 0.0 );

        processMultiple();
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

        if ( component == powerSwitch )
            return powerSwitch.GetValue() >= 0.5
                ? "Power on; 13.6 s warmup"
                : "Power off; 2.1 s cooldown; A-D and FULL MIX mute; MULT remains live";
        if ( component == powerIndicator )
            return "Power indicator: follows the warmup/cooldown envelope";

        if ( component == perspectiveKnob )
        {
            double seconds = perspectiveToSeconds( perspectiveKnob.GetValue() );
            if ( seconds < 10.0 )
                return String.format( "%.2f s", seconds );
            return String.format( "%.1f s", seconds );
        }

        if ( component == bassAKnob || component == bassBKnob ||
             component == bassCKnob || component == bassDKnob ||
             component == trebleAKnob || component == trebleBKnob ||
             component == trebleCKnob || component == trebleDKnob )
        {
            VoltageKnob k = (VoltageKnob) component;
            double db = (k.GetValue() - 0.5) * 24.0;
            return String.format( "%+.1f dB", db );
        }

        if ( component == mixAKnob || component == mixBKnob ||
             component == mixCKnob || component == mixDKnob )
        {
            VoltageKnob k = (VoltageKnob) component;
            double gain = mixKnobToGain( k.GetValue() );
            return gain <= 1.0e-6 ? "MUTE" : String.format( "%+.1f dB", 20.0 * Math.log10( gain ) );
        }

        if ( component == distanceAKnob || component == distanceBKnob ||
             component == distanceCKnob || component == distanceDKnob )
        {
            VoltageKnob k = (VoltageKnob) component;
            return String.format( "%.0f%%", k.GetValue() * 100.0 );
        }
        if ( component == input1Jack || component == input2Jack ||
             component == input3Jack || component == input4Jack )
            return "Mono matrix input; AC-coupled before the four output rows";
        if ( component == outputAJack || component == outputBJack ||
             component == outputCJack || component == outputDJack )
            return "Independent matrix row after tone, character and additive ambience";
        if ( component == fullMixOutput )
            return "A-D average through gentle courtesy-bus saturation";
        if ( component == multIn )
            return "Independent MULT input; remains live during power-off and bypass";
        if ( component == multOut1 || component == multOut2 || component == multOut3 )
            return "Direct buffered copy of MULT input";
        if ( component == matrixA1Knob ||
             component == matrixA2Knob ||
             component == matrixA3Knob ||
             component == matrixA4Knob ||
             component == matrixB1Knob ||
             component == matrixB2Knob ||
             component == matrixB3Knob ||
             component == matrixB4Knob ||
             component == matrixC1Knob ||
             component == matrixC2Knob ||
             component == matrixC3Knob ||
             component == matrixC4Knob ||
             component == matrixD1Knob ||
             component == matrixD2Knob ||
             component == matrixD3Knob ||
             component == matrixD4Knob )
            return String.format( "%+.1f%%; negative values invert this matrix contribution",
                ((VoltageKnob)component).GetValue() * 100.0 );

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
        if ( !Double.isFinite( newValue ) )
            return;

        if ( component == perspectiveKnob )
        {
            perspectiveKnob.SetValue( secondsToPerspective( newValue ) );
            return;
        }

        if ( component == bassAKnob || component == bassBKnob ||
             component == bassCKnob || component == bassDKnob ||
             component == trebleAKnob || component == trebleBKnob ||
             component == trebleCKnob || component == trebleDKnob )
        {
            ((VoltageKnob)component).SetValue(
                clamp( newValue / 24.0 + 0.5, 0.0, 1.0 )
            );
            return;
        }

        if ( component == mixAKnob || component == mixBKnob ||
             component == mixCKnob || component == mixDKnob )
        {
            double gain = Math.pow( 10.0, newValue / 20.0 );
            ((VoltageKnob)component).SetValue(
                clamp( Math.sqrt( gain / 4.0 ), 0.0, 1.0 )
            );
            return;
        }

        if ( component == distanceAKnob || component == distanceBKnob ||
             component == distanceCKnob || component == distanceDKnob )
        {
            ((VoltageKnob)component).SetValue(
                clamp( newValue / 100.0, 0.0, 1.0 )
            );
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
    private VoltageLED powerIndicator;
    private VoltageSwitch powerSwitch;
    private VoltageLabel acousticDistanceLabel;
    private VoltageAudioJack multOut3;
    private VoltageAudioJack multOut2;
    private VoltageAudioJack multOut1;
    private VoltageAudioJack multIn;
    private VoltageLabel labelInputs;
    private VoltageLabel catalogLabel;
    private VoltageLabel labelDistance;
    private VoltageLabel perspectiveLabel;
    private VoltageKnob distanceDKnob;
    private VoltageKnob distanceCKnob;
    private VoltageKnob distanceBKnob;
    private VoltageKnob distanceAKnob;
    private VoltageAudioJack fullMixOutput;
    private VoltageAudioJack outputDJack;
    private VoltageLabel labelD;
    private VoltageLabel labelC;
    private VoltageLabel labelB;
    private VoltageLabel labelA;
    private VoltageAudioJack outputCJack;
    private VoltageAudioJack outputBJack;
    private VoltageAudioJack outputAJack;
    private VoltageLabel labelMix;
    private VoltageLabel labelTreble;
    private VoltageLabel labelBass;
    private VoltageLabel colophon;
    private VoltageLabel input4Label;
    private VoltageLabel input3Label;
    private VoltageLabel input2Label;
    private VoltageLabel input1Label;
    private VoltageKnob mixDKnob;
    private VoltageKnob mixCKnob;
    private VoltageKnob mixBKnob;
    private VoltageKnob mixAKnob;
    private VoltageKnob trebleDKnob;
    private VoltageKnob trebleCKnob;
    private VoltageKnob trebleBKnob;
    private VoltageKnob trebleAKnob;
    private VoltageKnob bassCKnob;
    private VoltageKnob bassDKnob;
    private VoltageKnob bassBKnob;
    private VoltageKnob bassAKnob;
    private VoltageKnob matrixD4Knob;
    private VoltageKnob matrixC4Knob;
    private VoltageKnob matrixB4Knob;
    private VoltageKnob matrixA4Knob;
    private VoltageKnob matrixD3Knob;
    private VoltageKnob matrixC3Knob;
    private VoltageKnob matrixB3Knob;
    private VoltageKnob matrixA3Knob;
    private VoltageKnob matrixD2Knob;
    private VoltageKnob matrixC2Knob;
    private VoltageKnob matrixB2Knob;
    private VoltageKnob matrixA2Knob;
    private VoltageKnob matrixD1Knob;
    private VoltageKnob matrixC1Knob;
    private VoltageKnob matrixB1Knob;
    private VoltageKnob matrixA1Knob;
    private VoltageAudioJack input4Jack;
    private VoltageAudioJack input3Jack;
    private VoltageAudioJack input2Jack;
    private VoltageAudioJack input1Jack;
    private VoltageLabel numberLabel;
    private VoltageLabel descriptionLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageKnob perspectiveKnob;
    private VoltageLine panelLine17;
    private VoltageLine panelLine18;
    private VoltageLine panelLine16;


    //[user-code-and-variables]    Add your own variables and functions here

    // =========================================================================
    // LM-21 Mk III DSP — v1.0.1
    //
    // Mixer/EQ character:
    //   - Moog 984-inspired four-by-four matrix topology
    //   - nominal 1.25x gain with tone controls centered
    //   - AC coupling and gentle asymmetric transistor-style overload
    //   - +/-12 dB bass/treble shelves (a practical digital approximation of
    //     the original active tone network)
    //
    // Acoustic distance:
    //   - one independent mono ReverbSC network for each output row
    //   - Sean Costello / Csound 8-delay-line scattering FDN topology
    //   - DIST. adds the wet field to the dry row
    //   - PERSPECTIVE maps mostly across historically plausible plate-like
    //     RT60 values, then extends into deliberately dark/degraded long tails
    // =========================================================================

    private static final double SAMPLE_RATE = 48000.0;
    private static final double POWER_WARMUP_STEP =
        1.0 / (13.6 * SAMPLE_RATE);
    private static final double POWER_COOLDOWN_STEP =
        1.0 / (2.1 * SAMPLE_RATE);
    private static final double POWER_CUTOFF = 1.0e-4;

    // ~10 ms for panel controls; ~35 ms for global reverb parameters.
    private static final double CONTROL_SMOOTH =
        1.0 - Math.exp( -1.0 / (0.010 * SAMPLE_RATE) );
    private static final double PARAM_SMOOTH =
        1.0 - Math.exp( -1.0 / (0.035 * SAMPLE_RATE) );

    private static final double INPUT_HP_R =
        Math.exp( -2.0 * Math.PI * 3.3 / SAMPLE_RATE );
    private static final double OUTPUT_HP_R =
        Math.exp( -2.0 * Math.PI * 3.3 / SAMPLE_RATE );

    // Practical approximations to the broad 984 tone-control regions.
    private static final double BASS_ALPHA =
        1.0 - Math.exp( -2.0 * Math.PI * 250.0 / SAMPLE_RATE );
    private static final double TREBLE_ALPHA =
        1.0 - Math.exp( -2.0 * Math.PI * 2500.0 / SAMPLE_RATE );

    private static final double NOMINAL_984_GAIN = 1.25;
    private static final double WET_RETURN_GAIN = 1.0;

    private final double[][] matrixTarget = new double[4][4];
    private final double[][] matrixSmooth = new double[4][4];

    private final double[] bassGainTarget = new double[4];
    private final double[] bassGainSmooth = new double[4];
    private final double[] trebleGainTarget = new double[4];
    private final double[] trebleGainSmooth = new double[4];
    private final double[] distanceTarget = new double[4];
    private final double[] distanceSmooth = new double[4];
    private final double[] levelTarget = new double[4];
    private final double[] levelSmooth = new double[4];

    private final double[] inputPrevX = new double[4];
    private final double[] inputPrevY = new double[4];
    private final double[] outputPrevX = new double[4];
    private final double[] outputPrevY = new double[4];

    private final double[] bassLP = new double[4];
    private final double[] trebleLP = new double[4];

    private final ReverbSCMono[] reverbs = new ReverbSCMono[4];
    private double powerGain;
    private double displayedPowerLevel = -1.0;
    private int powerDisplayCountdown = 1;
    private boolean powerOffStateCleared = true;
    private boolean resumePending;

    private double feedbackTarget = 0.8;
    private double feedbackSmooth = 0.8;
    private double dampFactTarget = 0.5;
    private double dampFactSmooth = 0.5;
    private double pitchModTarget = 0.4;
    private double pitchModSmooth = 0.4;
    private double ageTarget = 0.0;
    private double ageSmooth = 0.0;

    private void syncControlTargets()
    {
        matrixTarget[0][0] = matrixA1Knob.GetValue();
        matrixTarget[0][1] = matrixA2Knob.GetValue();
        matrixTarget[0][2] = matrixA3Knob.GetValue();
        matrixTarget[0][3] = matrixA4Knob.GetValue();

        matrixTarget[1][0] = matrixB1Knob.GetValue();
        matrixTarget[1][1] = matrixB2Knob.GetValue();
        matrixTarget[1][2] = matrixB3Knob.GetValue();
        matrixTarget[1][3] = matrixB4Knob.GetValue();

        matrixTarget[2][0] = matrixC1Knob.GetValue();
        matrixTarget[2][1] = matrixC2Knob.GetValue();
        matrixTarget[2][2] = matrixC3Knob.GetValue();
        matrixTarget[2][3] = matrixC4Knob.GetValue();

        matrixTarget[3][0] = matrixD1Knob.GetValue();
        matrixTarget[3][1] = matrixD2Knob.GetValue();
        matrixTarget[3][2] = matrixD3Knob.GetValue();
        matrixTarget[3][3] = matrixD4Knob.GetValue();

        bassGainTarget[0] = toneKnobToGain( bassAKnob.GetValue() );
        bassGainTarget[1] = toneKnobToGain( bassBKnob.GetValue() );
        bassGainTarget[2] = toneKnobToGain( bassCKnob.GetValue() );
        bassGainTarget[3] = toneKnobToGain( bassDKnob.GetValue() );

        trebleGainTarget[0] = toneKnobToGain( trebleAKnob.GetValue() );
        trebleGainTarget[1] = toneKnobToGain( trebleBKnob.GetValue() );
        trebleGainTarget[2] = toneKnobToGain( trebleCKnob.GetValue() );
        trebleGainTarget[3] = toneKnobToGain( trebleDKnob.GetValue() );

        distanceTarget[0] = distanceAKnob.GetValue();
        distanceTarget[1] = distanceBKnob.GetValue();
        distanceTarget[2] = distanceCKnob.GetValue();
        distanceTarget[3] = distanceDKnob.GetValue();

        levelTarget[0] = mixKnobToGain( mixAKnob.GetValue() );
        levelTarget[1] = mixKnobToGain( mixBKnob.GetValue() );
        levelTarget[2] = mixKnobToGain( mixCKnob.GetValue() );
        levelTarget[3] = mixKnobToGain( mixDKnob.GetValue() );

        updatePerspectiveTargets( perspectiveKnob.GetValue() );
    }

    private static double mixKnobToGain( double knob )
    {
        knob = clamp( knob, 0.0, 1.0 );
        return 4.0 * knob * knob; // 0 = mute, midpoint = unity, full = +12 dB
    }

    private static double gentleFullMix( double x )
    {
        // Dedicated courtesy-bus saturation after A-D averaging.
        if ( x >= 0.0 )
            return 15.0 * Math.tanh( x / 15.0 );
        return 14.5 * Math.tanh( x / 14.5 );
    }

    private static double toneKnobToGain( double knob )
    {
        // Center = 0 dB; endpoints = -12 / +12 dB.
        double db = (knob - 0.5) * 24.0;
        return Math.pow( 10.0, db / 20.0 );
    }

    private static double perspectiveToSeconds( double p )
    {
        p = clamp( p, 0.0, 1.0 );

        // 60% of the travel covers 0.5 to 5.5 s.  The final 40% stretches
        // exponentially to 30 s for more control over long ambience.
        if ( p <= 0.60 )
        {
            double t = p / 0.60;
            return 0.5 * Math.pow( 11.0, t );
        }

        double t = (p - 0.60) / 0.40;
        return 5.5 * Math.pow( 30.0 / 5.5, t );
    }

    private static double secondsToPerspective( double seconds )
    {
        seconds = clamp( seconds, 0.5, 30.0 );

        if ( seconds <= 5.5 )
            return 0.60 * Math.log( seconds / 0.5 ) / Math.log( 11.0 );

        return 0.60 + 0.40
            * Math.log( seconds / 5.5 )
            / Math.log( 30.0 / 5.5 );
    }

    private void updatePerspectiveTargets( double p )
    {
        p = clamp( p, 0.0, 1.0 );
        double rt60 = perspectiveToSeconds( p );

        // Sean Costello's original eight delay lengths average about 68.38 ms.
        // Converting desired RT60 to loop gain keeps PERSPECTIVE musically
        // meaningful instead of making it an arbitrary "feedback" control.
        final double averageDelay = 0.06838;
        feedbackTarget = Math.pow( 10.0, (-3.0 * averageDelay) / rt60 );

        // Beyond the period-plausible region, the imaginary old delay/storage
        // system has to work harder.  It progressively loses bandwidth and
        // wanders more, so very long tails are possible but not pristine.
        double age = clamp( (p - 0.60) / 0.40, 0.0, 1.0 );
        age = age * age * (3.0 - 2.0 * age); // smoothstep
        ageTarget = age;

        double cutoff = 10500.0 * Math.pow( 3200.0 / 10500.0, age );
        dampFactTarget = reverbDampFactor( cutoff );

        // ReverbSC defaults to 1.0.  Held pitches are cleaner with less, so the
        // normal region is restrained; the extended region deliberately drifts.
        pitchModTarget = 0.40 + 1.60 * age;
    }

    private static double reverbDampFactor( double cutoff )
    {
        cutoff = clamp( cutoff, 40.0, SAMPLE_RATE * 0.45 );
        double d = 2.0 - Math.cos( cutoff * (2.0 * Math.PI) / SAMPLE_RATE );
        return d - Math.sqrt( d * d - 1.0 );
    }

    private static double readInput( VoltageAudioJack jack )
    {
        if ( !jack.IsConnected() )
            return 0.0;

        double value = jack.GetValue();
        if ( !Double.isFinite( value ) )
            return 0.0;

        return clamp( value, -1.0e6, 1.0e6 );
    }

    private void updatePowerState( boolean powered )
    {
        if ( powered )
            powerGain = Math.min( 1.0, powerGain + POWER_WARMUP_STEP );
        else
            powerGain = Math.max( 0.0, powerGain - POWER_COOLDOWN_STEP );

        if ( --powerDisplayCountdown <= 0 )
        {
            powerDisplayCountdown = 480;
            if ( Math.abs( powerGain - displayedPowerLevel ) > 0.002 )
            {
                powerIndicator.SetValue( powerGain );
                displayedPowerLevel = powerGain;
            }
        }
    }

    private void processMultiple()
    {
        double mult = readInput( multIn );
        multOut1.SetValue( mult );
        multOut2.SetValue( mult );
        multOut3.SetValue( mult );
    }

    private void clearProcessingState()
    {
        for ( int channel = 0; channel < 4; channel++ )
        {
            inputPrevX[channel] = 0.0;
            inputPrevY[channel] = 0.0;
            outputPrevX[channel] = 0.0;
            outputPrevY[channel] = 0.0;
            bassLP[channel] = 0.0;
            trebleLP[channel] = 0.0;
            reverbs[channel].reset();
        }
    }

    private double acCoupleInput( int n, double x )
    {
        double y = x - inputPrevX[n] + INPUT_HP_R * inputPrevY[n];
        inputPrevX[n] = x;
        inputPrevY[n] = y;
        return y;
    }

    private double acCoupleOutput( int n, double x )
    {
        double y = x - outputPrevX[n] + OUTPUT_HP_R * outputPrevY[n];
        outputPrevX[n] = x;
        outputPrevY[n] = y;
        return y;
    }

    private double matrixMix( int row, double a, double b, double c, double d )
    {
        return a * matrixSmooth[row][0]
             + b * matrixSmooth[row][1]
             + c * matrixSmooth[row][2]
             + d * matrixSmooth[row][3];
    }

    private double processOutputChannel( int ch, double x )
    {
        // 984 nominal gain with the tone controls centered.
        double y = x * levelSmooth[ch] * NOMINAL_984_GAIN;

        // Broad low shelf.
        bassLP[ch] += BASS_ALPHA * (y - bassLP[ch]);
        y += (bassGainSmooth[ch] - 1.0) * bassLP[ch];

        // Broad high shelf.
        trebleLP[ch] += TREBLE_ALPHA * (y - trebleLP[ch]);
        double high = y - trebleLP[ch];
        y += (trebleGainSmooth[ch] - 1.0) * high;

        // The real 984 transistor stages can be driven into distortion,
        // especially after EQ boost.  Keep low levels essentially clean and
        // let large sums round asymmetrically.
        double dry = vintage984Stage( y );

        // Each row owns a completely independent ReverbSC network, so there is
        // no hidden crosstalk between matrix outputs.
        double wet = reverbs[ch].process(
            dry,
            feedbackSmooth,
            dampFactSmooth,
            pitchModSmooth,
            ageSmooth
        );

        // DIST. is an additive ambience return rather than a dry/wet crossfade.
        double withDistance = dry + wet * distanceSmooth[ch] * WET_RETURN_GAIN;

        // MIX drives the row before the nonlinear stage; the reverb return remains audible.
        return acCoupleOutput( ch, withDistance );
    }

    private static double vintage984Stage( double x )
    {
        // Mildly asymmetric soft rails.  This is intentionally a musical
        // approximation of the transistor overload, not a component-level
        // transistor solver.
        if ( x >= 0.0 )
            return 7.0 * Math.tanh( x / 7.0 );
        return 6.2 * Math.tanh( x / 6.2 );
    }

    private static double clamp( double x, double lo, double hi )
    {
        if ( !Double.isFinite( x ) )
            return 0.0;

        return x < lo ? lo : (x > hi ? hi : x);
    }

    // -------------------------------------------------------------------------
    // Mono adaptation of ReverbSC
    //
    // The delay constants, random modulation rates/seeds, 8-way scattering
    // junction, cubic interpolation, feedback low-pass form, and output scale
    // follow the Costello/Csound ReverbSC family.  Feeding the same mono signal
    // to the original L/R inputs makes all eight lines participate; averaging
    // its two output sums is equivalent to summing all eight lines here.
    // -------------------------------------------------------------------------
    private static final class ReverbSCMono
    {
        private static final double DEFAULT_SR = 44100.0;
        private static final double OUTPUT_GAIN_MONO = 0.175; // 0.35 * 1/2
        private static final double JUNCTION_SCALE = 0.25;
        private static final double MAX_PITCH_MOD = 2.25;

        private static final double[] BASE_DELAY = {
            2473.0 / DEFAULT_SR,
            2767.0 / DEFAULT_SR,
            3217.0 / DEFAULT_SR,
            3557.0 / DEFAULT_SR,
            3907.0 / DEFAULT_SR,
            4127.0 / DEFAULT_SR,
            2143.0 / DEFAULT_SR,
            1933.0 / DEFAULT_SR
        };

        private static final double[] DELAY_VAR = {
            0.0010, 0.0011, 0.0017, 0.0006,
            0.0010, 0.0011, 0.0017, 0.0006
        };

        private static final double[] RAND_FREQ = {
            3.100, 3.500, 1.110, 3.973,
            2.341, 1.897, 0.891, 3.221
        };

        private static final int[] SEED = {
            1966, 29491, 22937, 9830,
            20643, 22937, 29491, 14417
        };

        private final DelayLine[] lines = new DelayLine[8];

        ReverbSCMono()
        {
            for ( int n = 0; n < 8; n++ )
            {
                int size = (int)Math.ceil(
                    (BASE_DELAY[n] + DELAY_VAR[n] * MAX_PITCH_MOD * 1.125)
                    * SAMPLE_RATE + 16.5
                );

                lines[n] = new DelayLine( size, SEED[n] );
                initializeLine( lines[n], n, 0.40 );
            }
        }

        void reset()
        {
            for ( int n = 0; n < 8; n++ )
            {
                DelayLine line = lines[n];
                for ( int i = 0; i < line.buffer.length; i++ )
                    line.buffer[i] = 0.0;
                initializeLine( line, n, 0.40 );
            }
        }

        double process( double input,
                        double feedback,
                        double dampFact,
                        double pitchMod,
                        double age )
        {
            double junction = 0.0;
            for ( int n = 0; n < 8; n++ )
                junction += lines[n].filterState;

            junction *= JUNCTION_SCALE;
            double drive = junction + input;

            double sum = 0.0;

            for ( int n = 0; n < 8; n++ )
            {
                DelayLine line = lines[n];

                line.buffer[line.writePos] = drive - line.filterState;
                line.writePos++;
                if ( line.writePos >= line.buffer.length )
                    line.writePos = 0;

                double v = readCubic( line );

                line.readPos += line.readInc;
                while ( line.readPos >= line.buffer.length )
                    line.readPos -= line.buffer.length;
                while ( line.readPos < 0.0 )
                    line.readPos += line.buffer.length;

                v *= feedback;

                // Exact one-pole feedback damping form used by ReverbSC.
                v = (line.filterState - v) * dampFact + v;

                // The normal region remains essentially ReverbSC.  Only the
                // extended PERSPECTIVE region introduces a gentle BBD-ish
                // "generation loss": increased rounding inside recirculation.
                if ( age > 0.000001 )
                {
                    double satAmount = 0.16 * age * age;
                    double sat = 6.0 * Math.tanh( v / 6.0 );
                    v += satAmount * (sat - v);
                }

                line.filterState = v;
                sum += v;

                line.randomCount--;
                if ( line.randomCount <= 0 )
                    nextRandomSegment( line, n, pitchMod );
            }

            return sum * OUTPUT_GAIN_MONO;
        }

        private static double readCubic( DelayLine line )
        {
            int size = line.buffer.length;
            int i0 = (int)Math.floor( line.readPos );
            double frac = line.readPos - i0;

            int im1 = i0 - 1;
            if ( im1 < 0 ) im1 += size;

            int i1 = i0 + 1;
            if ( i1 >= size ) i1 -= size;

            int i2 = i1 + 1;
            if ( i2 >= size ) i2 -= size;

            double vm1 = line.buffer[im1];
            double v0 = line.buffer[i0];
            double v1 = line.buffer[i1];
            double v2 = line.buffer[i2];

            // ReverbSC's original cubic interpolation polynomial.
            double a2 = (frac * frac - 1.0) / 6.0;
            double a1 = (frac + 1.0) * 0.5;
            double am1 = a1 - 1.0;
            double a0 = 3.0 * a2;

            a1 -= a0;
            am1 -= a2;
            a0 -= frac;

            return (am1 * vm1 + a0 * v0 + a1 * v1 + a2 * v2) * frac + v0;
        }

        private static void initializeLine( DelayLine line, int n, double pitchMod )
        {
            line.writePos = 0;
            line.seed = SEED[n];
            line.filterState = 0.0;

            double initialDelay =
                BASE_DELAY[n] + (line.seed * DELAY_VAR[n] / 32768.0) * pitchMod;

            line.readPos = line.buffer.length - initialDelay * SAMPLE_RATE;
            while ( line.readPos < 0.0 )
                line.readPos += line.buffer.length;
            while ( line.readPos >= line.buffer.length )
                line.readPos -= line.buffer.length;

            nextRandomSegment( line, n, pitchMod );
        }

        private static void nextRandomSegment( DelayLine line, int n, double pitchMod )
        {
            if ( line.seed < 0 )
                line.seed += 0x10000;

            line.seed = (line.seed * 15625 + 1) & 0xFFFF;

            if ( line.seed >= 0x8000 )
                line.seed -= 0x10000;

            line.randomCount = (int)(SAMPLE_RATE / RAND_FREQ[n] + 0.5);
            if ( line.randomCount < 1 )
                line.randomCount = 1;

            double previousDelay = line.writePos - line.readPos;
            while ( previousDelay < 0.0 )
                previousDelay += line.buffer.length;
            while ( previousDelay >= line.buffer.length )
                previousDelay -= line.buffer.length;
            previousDelay /= SAMPLE_RATE;

            double nextDelay =
                BASE_DELAY[n] + (line.seed * DELAY_VAR[n] / 32768.0) * pitchMod;

            line.readInc =
                ((previousDelay - nextDelay) * SAMPLE_RATE / line.randomCount) + 1.0;
        }

        private static final class DelayLine
        {
            final double[] buffer;
            int writePos;
            double readPos;
            double readInc;
            int seed;
            int randomCount;
            double filterState;

            DelayLine( int size, int initialSeed )
            {
                buffer = new double[size];
                seed = initialSeed;
            }
        }
    }
    //[/user-code-and-variables]
}

 