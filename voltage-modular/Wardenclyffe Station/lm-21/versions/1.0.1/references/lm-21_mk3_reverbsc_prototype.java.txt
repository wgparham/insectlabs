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
        SetSkin( "c777cd74120b44ae94e5ad5e620ab241" );
    }

void InitializeControls()
{

        line16 = new VoltageLine( "line16", "line16", this );
        AddComponent( line16 );
        line16.SetWantsMouseNotifications( false );
        line16.SetLineColor( new Color( 147, 0, 0, 147 ) );
        line16.SetLineWidth( (float)3 );
        line16.SetStartPosition( 232, 277 );
        line16.SetEndPosition( 282, 277 );
        line16.SetHasArrow( false );
        line16.SetArrowWidth( (float)8 );
        line16.SetArrowLength( (float)12 );

        line18 = new VoltageLine( "line18", "line18", this );
        AddComponent( line18 );
        line18.SetWantsMouseNotifications( false );
        line18.SetLineColor( new Color( 147, 0, 0, 147 ) );
        line18.SetLineWidth( (float)3 );
        line18.SetStartPosition( 233, 276 );
        line18.SetEndPosition( 282, 327 );
        line18.SetHasArrow( false );
        line18.SetArrowWidth( (float)8 );
        line18.SetArrowLength( (float)12 );

        line17 = new VoltageLine( "line17", "line17", this );
        AddComponent( line17 );
        line17.SetWantsMouseNotifications( false );
        line17.SetLineColor( new Color( 147, 0, 0, 147 ) );
        line17.SetLineWidth( (float)3 );
        line17.SetStartPosition( 233, 278 );
        line17.SetEndPosition( 232, 327 );
        line17.SetHasArrow( false );
        line17.SetArrowWidth( (float)8 );
        line17.SetArrowLength( (float)12 );

        distancePerspective = new VoltageKnob( "distancePerspective", "DISTANCE PERSPECTIVE Level", this, 0.0, 1.0, 0.0 );
        AddComponent( distancePerspective );
        distancePerspective.SetWantsMouseNotifications( false );
        distancePerspective.SetPosition( 320, 259 );
        distancePerspective.SetSize( 41, 41 );
        distancePerspective.SetSkin( "Cosmo v2 Med" );
        distancePerspective.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distancePerspective.SetKnobParams( 215, 145 );
        distancePerspective.DisplayValueInPercent( true );
        distancePerspective.SetKnobAdjustsRing( true );

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

        descriptionLabel = new VoltageLabel( "descriptionLabel", "MODULE DESCRIPTION", this, "matrix mixer – artificial acoustic distance generator" );
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

        numberLabel = new VoltageLabel( "numberLabel", "Model Number", this, "model lm-21 mk 3" );
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

        input1 = new VoltageAudioJack( "input1", "Input 1", this, JackType.JackType_AudioInput );
        AddComponent( input1 );
        input1.SetWantsMouseNotifications( false );
        input1.SetPosition( 5, 259 );
        input1.SetSize( 37, 37 );
        input1.SetSkin( "Dark Jack Straight" );

        input2 = new VoltageAudioJack( "input2", "Input 2", this, JackType.JackType_AudioInput );
        AddComponent( input2 );
        input2.SetWantsMouseNotifications( false );
        input2.SetPosition( 55, 259 );
        input2.SetSize( 37, 37 );
        input2.SetSkin( "Dark Jack Straight" );

        inputJack3 = new VoltageAudioJack( "inputJack3", "inputJack3", this, JackType.JackType_AudioInput );
        AddComponent( inputJack3 );
        inputJack3.SetWantsMouseNotifications( false );
        inputJack3.SetPosition( 105, 259 );
        inputJack3.SetSize( 37, 37 );
        inputJack3.SetSkin( "Dark Jack Straight" );

        input4 = new VoltageAudioJack( "input4", "Input 4", this, JackType.JackType_AudioInput );
        AddComponent( input4 );
        input4.SetWantsMouseNotifications( false );
        input4.SetPosition( 155, 259 );
        input4.SetSize( 37, 37 );
        input4.SetSkin( "Dark Jack Straight" );

        knobA1 = new VoltageKnob( "knobA1", "Knob A1", this, -1.0, 1.0, 0.00 );
        AddComponent( knobA1 );
        knobA1.SetWantsMouseNotifications( false );
        knobA1.SetPosition( 5, 64 );
        knobA1.SetSize( 40, 40 );
        knobA1.SetSkin( "Cosmo v2 Med" );
        knobA1.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobA1.SetKnobParams( 215, 145 );
        knobA1.DisplayValueInPercent( false );
        knobA1.SetKnobAdjustsRing( true );

        knobB1 = new VoltageKnob( "knobB1", "Knob B1", this, -1.0, 1.0, 0.00 );
        AddComponent( knobB1 );
        knobB1.SetWantsMouseNotifications( false );
        knobB1.SetPosition( 5, 114 );
        knobB1.SetSize( 40, 40 );
        knobB1.SetSkin( "Cosmo v2 Med" );
        knobB1.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobB1.SetKnobParams( 215, 145 );
        knobB1.DisplayValueInPercent( false );
        knobB1.SetKnobAdjustsRing( true );

        knobC = new VoltageKnob( "knobC1", "Knob C1", this, -1.0, 1.0, 0.00 );
        AddComponent( knobC );
        knobC.SetWantsMouseNotifications( false );
        knobC.SetPosition( 4, 164 );
        knobC.SetSize( 40, 40 );
        knobC.SetSkin( "Cosmo v2 Med" );
        knobC.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobC.SetKnobParams( 215, 145 );
        knobC.DisplayValueInPercent( false );
        knobC.SetKnobAdjustsRing( true );

        knobD1 = new VoltageKnob( "knobD1", "Knob D1", this, -1.0, 1.0, 0.00 );
        AddComponent( knobD1 );
        knobD1.SetWantsMouseNotifications( false );
        knobD1.SetPosition( 5, 214 );
        knobD1.SetSize( 40, 40 );
        knobD1.SetSkin( "Cosmo v2 Med" );
        knobD1.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobD1.SetKnobParams( 215, 145 );
        knobD1.DisplayValueInPercent( false );
        knobD1.SetKnobAdjustsRing( true );

        knobA2 = new VoltageKnob( "knobA2", "Knob A2", this, -1.0, 1.0, 0.00 );
        AddComponent( knobA2 );
        knobA2.SetWantsMouseNotifications( false );
        knobA2.SetPosition( 55, 64 );
        knobA2.SetSize( 40, 40 );
        knobA2.SetSkin( "Cosmo v2 Med" );
        knobA2.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobA2.SetKnobParams( 215, 145 );
        knobA2.DisplayValueInPercent( false );
        knobA2.SetKnobAdjustsRing( true );

        knobB2 = new VoltageKnob( "knobB2", "Knob B2", this, -1.0, 1.0, 0.00 );
        AddComponent( knobB2 );
        knobB2.SetWantsMouseNotifications( false );
        knobB2.SetPosition( 55, 114 );
        knobB2.SetSize( 40, 40 );
        knobB2.SetSkin( "Cosmo v2 Med" );
        knobB2.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobB2.SetKnobParams( 215, 145 );
        knobB2.DisplayValueInPercent( false );
        knobB2.SetKnobAdjustsRing( true );

        knobC2 = new VoltageKnob( "knobC2", "Knob C2", this, -1.0, 1.0, 0.00 );
        AddComponent( knobC2 );
        knobC2.SetWantsMouseNotifications( false );
        knobC2.SetPosition( 55, 164 );
        knobC2.SetSize( 40, 40 );
        knobC2.SetSkin( "Cosmo v2 Med" );
        knobC2.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobC2.SetKnobParams( 215, 145 );
        knobC2.DisplayValueInPercent( false );
        knobC2.SetKnobAdjustsRing( true );

        knobD2 = new VoltageKnob( "knobD2", "Knob D2", this, -1.0, 1.0, 0.00 );
        AddComponent( knobD2 );
        knobD2.SetWantsMouseNotifications( false );
        knobD2.SetPosition( 55, 214 );
        knobD2.SetSize( 40, 40 );
        knobD2.SetSkin( "Cosmo v2 Med" );
        knobD2.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobD2.SetKnobParams( 215, 145 );
        knobD2.DisplayValueInPercent( false );
        knobD2.SetKnobAdjustsRing( true );

        knobA3 = new VoltageKnob( "knobA3", "Knob A3", this, -1.0, 1.0, 0.00 );
        AddComponent( knobA3 );
        knobA3.SetWantsMouseNotifications( false );
        knobA3.SetPosition( 105, 64 );
        knobA3.SetSize( 40, 40 );
        knobA3.SetSkin( "Cosmo v2 Med" );
        knobA3.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobA3.SetKnobParams( 215, 145 );
        knobA3.DisplayValueInPercent( false );
        knobA3.SetKnobAdjustsRing( true );

        knobB3 = new VoltageKnob( "knobB3", "Knob B3", this, -1.0, 1.0, 0.00 );
        AddComponent( knobB3 );
        knobB3.SetWantsMouseNotifications( false );
        knobB3.SetPosition( 105, 114 );
        knobB3.SetSize( 40, 40 );
        knobB3.SetSkin( "Cosmo v2 Med" );
        knobB3.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobB3.SetKnobParams( 215, 145 );
        knobB3.DisplayValueInPercent( false );
        knobB3.SetKnobAdjustsRing( true );

        knobC3 = new VoltageKnob( "knobC3", "Knob C3", this, -1.0, 1.0, 0.00 );
        AddComponent( knobC3 );
        knobC3.SetWantsMouseNotifications( false );
        knobC3.SetPosition( 105, 164 );
        knobC3.SetSize( 40, 40 );
        knobC3.SetSkin( "Cosmo v2 Med" );
        knobC3.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobC3.SetKnobParams( 215, 145 );
        knobC3.DisplayValueInPercent( false );
        knobC3.SetKnobAdjustsRing( true );

        knobD3 = new VoltageKnob( "knobD3", "Knob D3", this, -1.0, 1.0, 0.00 );
        AddComponent( knobD3 );
        knobD3.SetWantsMouseNotifications( false );
        knobD3.SetPosition( 105, 214 );
        knobD3.SetSize( 40, 40 );
        knobD3.SetSkin( "Cosmo v2 Med" );
        knobD3.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobD3.SetKnobParams( 215, 145 );
        knobD3.DisplayValueInPercent( false );
        knobD3.SetKnobAdjustsRing( true );

        knobA4 = new VoltageKnob( "knobA4", "Knob A4", this, -1.0, 1.0, 0.00 );
        AddComponent( knobA4 );
        knobA4.SetWantsMouseNotifications( false );
        knobA4.SetPosition( 155, 64 );
        knobA4.SetSize( 40, 40 );
        knobA4.SetSkin( "Cosmo v2 Med" );
        knobA4.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobA4.SetKnobParams( 215, 145 );
        knobA4.DisplayValueInPercent( false );
        knobA4.SetKnobAdjustsRing( true );

        knobB4 = new VoltageKnob( "knobB4", "Knob B4", this, -1.0, 1.0, 0.00 );
        AddComponent( knobB4 );
        knobB4.SetWantsMouseNotifications( false );
        knobB4.SetPosition( 155, 114 );
        knobB4.SetSize( 40, 40 );
        knobB4.SetSkin( "Cosmo v2 Med" );
        knobB4.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobB4.SetKnobParams( 215, 145 );
        knobB4.DisplayValueInPercent( false );
        knobB4.SetKnobAdjustsRing( true );

        knobC4 = new VoltageKnob( "knobC4", "Knob C4", this, -1.0, 1.0, 0.00 );
        AddComponent( knobC4 );
        knobC4.SetWantsMouseNotifications( false );
        knobC4.SetPosition( 155, 164 );
        knobC4.SetSize( 40, 40 );
        knobC4.SetSkin( "Cosmo v2 Med" );
        knobC4.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobC4.SetKnobParams( 215, 145 );
        knobC4.DisplayValueInPercent( false );
        knobC4.SetKnobAdjustsRing( true );

        knobD4 = new VoltageKnob( "knobD4", "Knob D4", this, -1.0, 1.0, 0.00 );
        AddComponent( knobD4 );
        knobD4.SetWantsMouseNotifications( false );
        knobD4.SetPosition( 155, 214 );
        knobD4.SetSize( 40, 40 );
        knobD4.SetSkin( "Cosmo v2 Med" );
        knobD4.SetRange( -1.0, 1.0, 0.00, false, 0 );
        knobD4.SetKnobParams( 215, 145 );
        knobD4.DisplayValueInPercent( false );
        knobD4.SetKnobAdjustsRing( true );

        knobBassA = new VoltageKnob( "knobBassA", "A BASS LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobBassA );
        knobBassA.SetWantsMouseNotifications( false );
        knobBassA.SetPosition( 211, 64 );
        knobBassA.SetSize( 40, 40 );
        knobBassA.SetSkin( "Cosmo v2 Med" );
        knobBassA.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobBassA.SetKnobParams( 215, 145 );
        knobBassA.DisplayValueInPercent( false );
        knobBassA.SetKnobAdjustsRing( true );

        knobBassB = new VoltageKnob( "knobBassB", "B BASS LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobBassB );
        knobBassB.SetWantsMouseNotifications( false );
        knobBassB.SetPosition( 211, 114 );
        knobBassB.SetSize( 40, 40 );
        knobBassB.SetSkin( "Cosmo v2 Med" );
        knobBassB.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobBassB.SetKnobParams( 215, 145 );
        knobBassB.DisplayValueInPercent( false );
        knobBassB.SetKnobAdjustsRing( true );

        knobBassD = new VoltageKnob( "knobBassD", "D BASS LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobBassD );
        knobBassD.SetWantsMouseNotifications( false );
        knobBassD.SetPosition( 211, 209 );
        knobBassD.SetSize( 40, 40 );
        knobBassD.SetSkin( "Cosmo v2 Med" );
        knobBassD.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobBassD.SetKnobParams( 215, 145 );
        knobBassD.DisplayValueInPercent( false );
        knobBassD.SetKnobAdjustsRing( true );

        knobBassC = new VoltageKnob( "knobBassC", "C BASS LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobBassC );
        knobBassC.SetWantsMouseNotifications( false );
        knobBassC.SetPosition( 211, 164 );
        knobBassC.SetSize( 40, 40 );
        knobBassC.SetSkin( "Cosmo v2 Med" );
        knobBassC.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobBassC.SetKnobParams( 215, 145 );
        knobBassC.DisplayValueInPercent( false );
        knobBassC.SetKnobAdjustsRing( true );

        knobTrebleA = new VoltageKnob( "knobTrebleA", "A TREBLE LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobTrebleA );
        knobTrebleA.SetWantsMouseNotifications( false );
        knobTrebleA.SetPosition( 262, 64 );
        knobTrebleA.SetSize( 40, 40 );
        knobTrebleA.SetSkin( "Cosmo v2 Med" );
        knobTrebleA.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobTrebleA.SetKnobParams( 215, 145 );
        knobTrebleA.DisplayValueInPercent( false );
        knobTrebleA.SetKnobAdjustsRing( true );

        knobTrebleB = new VoltageKnob( "knobTrebleB", "B TREBLE LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobTrebleB );
        knobTrebleB.SetWantsMouseNotifications( false );
        knobTrebleB.SetPosition( 262, 114 );
        knobTrebleB.SetSize( 40, 40 );
        knobTrebleB.SetSkin( "Cosmo v2 Med" );
        knobTrebleB.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobTrebleB.SetKnobParams( 215, 145 );
        knobTrebleB.DisplayValueInPercent( false );
        knobTrebleB.SetKnobAdjustsRing( true );

        knobTrebleC = new VoltageKnob( "knobTrebleC", "C TREBLE LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobTrebleC );
        knobTrebleC.SetWantsMouseNotifications( false );
        knobTrebleC.SetPosition( 262, 164 );
        knobTrebleC.SetSize( 40, 40 );
        knobTrebleC.SetSkin( "Cosmo v2 Med" );
        knobTrebleC.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobTrebleC.SetKnobParams( 215, 145 );
        knobTrebleC.DisplayValueInPercent( false );
        knobTrebleC.SetKnobAdjustsRing( true );

        knobTrebleD = new VoltageKnob( "knobTrebleD", "D TREBLE LEVEL", this, 0.0, 1.0, 0.5 );
        AddComponent( knobTrebleD );
        knobTrebleD.SetWantsMouseNotifications( false );
        knobTrebleD.SetPosition( 262, 209 );
        knobTrebleD.SetSize( 40, 40 );
        knobTrebleD.SetSkin( "Cosmo v2 Med" );
        knobTrebleD.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobTrebleD.SetKnobParams( 215, 145 );
        knobTrebleD.DisplayValueInPercent( false );
        knobTrebleD.SetKnobAdjustsRing( true );

        levelA = new VoltageKnob( "levelA", "Mix A Level", this, 0.0, 1.0, 0.0 );
        AddComponent( levelA );
        levelA.SetWantsMouseNotifications( false );
        levelA.SetPosition( 348, 64 );
        levelA.SetSize( 40, 40 );
        levelA.SetSkin( "Cosmo v2 Med" );
        levelA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        levelA.SetKnobParams( 215, 145 );
        levelA.DisplayValueInPercent( false );
        levelA.SetKnobAdjustsRing( true );

        levelB = new VoltageKnob( "levelB", "Mix B Level", this, 0.0, 1.0, 0.0 );
        AddComponent( levelB );
        levelB.SetWantsMouseNotifications( false );
        levelB.SetPosition( 348, 114 );
        levelB.SetSize( 40, 40 );
        levelB.SetSkin( "Cosmo v2 Med" );
        levelB.SetRange( 0.0, 1.0, 0.0, false, 0 );
        levelB.SetKnobParams( 215, 145 );
        levelB.DisplayValueInPercent( false );
        levelB.SetKnobAdjustsRing( true );

        levelC = new VoltageKnob( "levelC", "Mix C Level", this, 0.0, 1.0, 0.0 );
        AddComponent( levelC );
        levelC.SetWantsMouseNotifications( false );
        levelC.SetPosition( 348, 164 );
        levelC.SetSize( 40, 40 );
        levelC.SetSkin( "Cosmo v2 Med" );
        levelC.SetRange( 0.0, 1.0, 0.0, false, 0 );
        levelC.SetKnobParams( 215, 145 );
        levelC.DisplayValueInPercent( false );
        levelC.SetKnobAdjustsRing( true );

        levelD = new VoltageKnob( "levelD", "Mix D Level", this, 0.0, 1.0, 0.0 );
        AddComponent( levelD );
        levelD.SetWantsMouseNotifications( false );
        levelD.SetPosition( 348, 214 );
        levelD.SetSize( 40, 40 );
        levelD.SetSkin( "Cosmo v2 Med" );
        levelD.SetRange( 0.0, 1.0, 0.0, false, 0 );
        levelD.SetKnobParams( 215, 145 );
        levelD.DisplayValueInPercent( false );
        levelD.SetKnobAdjustsRing( true );

        textLabel4 = new VoltageLabel( "textLabel4", "textLabel4", this, "I" );
        AddComponent( textLabel4 );
        textLabel4.SetWantsMouseNotifications( false );
        textLabel4.SetPosition( 12, 294 );
        textLabel4.SetSize( 21, 21 );
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
        textLabel4.SetFont( "Times New Roman", 14, true, false );

        textLabel5 = new VoltageLabel( "textLabel5", "textLabel5", this, "II" );
        AddComponent( textLabel5 );
        textLabel5.SetWantsMouseNotifications( false );
        textLabel5.SetPosition( 63, 294 );
        textLabel5.SetSize( 21, 21 );
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
        textLabel5.SetFont( "Times New Roman", 14, true, false );

        textLabel6 = new VoltageLabel( "textLabel6", "textLabel6", this, "III" );
        AddComponent( textLabel6 );
        textLabel6.SetWantsMouseNotifications( false );
        textLabel6.SetPosition( 113, 294 );
        textLabel6.SetSize( 21, 21 );
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
        textLabel6.SetFont( "Times New Roman", 14, true, false );

        input3 = new VoltageLabel( "input3", "Input 3", this, "IIII" );
        AddComponent( input3 );
        input3.SetWantsMouseNotifications( false );
        input3.SetPosition( 163, 294 );
        input3.SetSize( 21, 21 );
        input3.SetEditable( false, false );
        input3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        input3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        input3.SetColor( new Color( 232, 232, 232, 255 ) );
        input3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        input3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        input3.SetBorderSize( 1 );
        input3.SetMultiLineEdit( false );
        input3.SetIsNumberEditor( false );
        input3.SetNumberEditorRange( 0, 100 );
        input3.SetNumberEditorInterval( 1 );
        input3.SetNumberEditorUsesMouseWheel( false );
        input3.SetHasCustomTextHoverColor( false );
        input3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        input3.SetFont( "Times New Roman", 14, true, false );

        colophon = new VoltageLabel( "colophon", "colophon", this, "refuge acoustic research oklahoma city, OK" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 25, 335 );
        colophon.SetSize( 93, 23 );
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

        outputa = new VoltageAudioJack( "outputa", "Output A", this, JackType.JackType_AudioOutput );
        AddComponent( outputa );
        outputa.SetWantsMouseNotifications( false );
        outputa.SetPosition( 395, 64 );
        outputa.SetSize( 37, 37 );
        outputa.SetSkin( "Rotated Half" );

        outputb = new VoltageAudioJack( "outputb", "Output B", this, JackType.JackType_AudioOutput );
        AddComponent( outputb );
        outputb.SetWantsMouseNotifications( false );
        outputb.SetPosition( 395, 114 );
        outputb.SetSize( 37, 37 );
        outputb.SetSkin( "Rotated Half" );

        outputc = new VoltageAudioJack( "outputc", "Output C", this, JackType.JackType_AudioOutput );
        AddComponent( outputc );
        outputc.SetWantsMouseNotifications( false );
        outputc.SetPosition( 395, 164 );
        outputc.SetSize( 37, 37 );
        outputc.SetSkin( "Rotated Half" );

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

        labelD = new VoltageLabel( "Output D Label", "", this, "D" );
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

        outputd = new VoltageAudioJack( "outputd", "Output D", this, JackType.JackType_AudioOutput );
        AddComponent( outputd );
        outputd.SetWantsMouseNotifications( false );
        outputd.SetPosition( 395, 214 );
        outputd.SetSize( 37, 37 );
        outputd.SetSkin( "Rotated Half" );

        outputMix = new VoltageAudioJack( "outputMix", "Full Mix Courtesy Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputMix );
        outputMix.SetWantsMouseNotifications( false );
        outputMix.SetPosition( 404, 265 );
        outputMix.SetSize( 25, 25 );
        outputMix.SetSkin( "Mini Jack 25px" );

        distanceA = new VoltageKnob( "distanceA", "A DISTANCE blend", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceA );
        distanceA.SetWantsMouseNotifications( false );
        distanceA.SetPosition( 311, 69 );
        distanceA.SetSize( 25, 25 );
        distanceA.SetSkin( "Cosmo Small" );
        distanceA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceA.SetKnobParams( 215, 145 );
        distanceA.DisplayValueInPercent( false );
        distanceA.SetKnobAdjustsRing( true );

        distanceB = new VoltageKnob( "distanceB", "B DISTANCE blend", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceB );
        distanceB.SetWantsMouseNotifications( false );
        distanceB.SetPosition( 311, 119 );
        distanceB.SetSize( 25, 25 );
        distanceB.SetSkin( "Cosmo Small" );
        distanceB.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceB.SetKnobParams( 215, 145 );
        distanceB.DisplayValueInPercent( false );
        distanceB.SetKnobAdjustsRing( true );

        distanceC = new VoltageKnob( "distanceC", "C DISTANCE blend", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceC );
        distanceC.SetWantsMouseNotifications( false );
        distanceC.SetPosition( 311, 169 );
        distanceC.SetSize( 25, 25 );
        distanceC.SetSkin( "Cosmo Small" );
        distanceC.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceC.SetKnobParams( 215, 145 );
        distanceC.DisplayValueInPercent( false );
        distanceC.SetKnobAdjustsRing( true );

        distanceD = new VoltageKnob( "distanceD", "D DISTANCE blend", this, 0.0, 1.0, 0.0 );
        AddComponent( distanceD );
        distanceD.SetWantsMouseNotifications( false );
        distanceD.SetPosition( 311, 214 );
        distanceD.SetSize( 25, 25 );
        distanceD.SetSkin( "Cosmo Small" );
        distanceD.SetRange( 0.0, 1.0, 0.0, false, 0 );
        distanceD.SetKnobParams( 215, 145 );
        distanceD.DisplayValueInPercent( false );
        distanceD.SetKnobAdjustsRing( true );

        textLabel16 = new VoltageLabel( "textLabel16", "textLabel16", this, "PERSPECTIVE" );
        AddComponent( textLabel16 );
        textLabel16.SetWantsMouseNotifications( false );
        textLabel16.SetPosition( 312, 299 );
        textLabel16.SetSize( 60, 21 );
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

        data = new VoltageLabel( "data", "Catalog Data", this, "matrix mixer/" );
        AddComponent( data );
        data.SetWantsMouseNotifications( false );
        data.SetPosition( 332, 318 );
        data.SetSize( 125, 13 );
        data.SetEditable( false, false );
        data.SetJustificationFlags( VoltageLabel.Justification.Right );
        data.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        data.SetColor( new Color( 232, 232, 232, 255 ) );
        data.SetBkColor( new Color( 65, 65, 65, 0 ) );
        data.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        data.SetBorderSize( 1 );
        data.SetMultiLineEdit( false );
        data.SetIsNumberEditor( false );
        data.SetNumberEditorRange( 0, 100 );
        data.SetNumberEditorInterval( 1 );
        data.SetNumberEditorUsesMouseWheel( false );
        data.SetHasCustomTextHoverColor( false );
        data.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        data.SetFont( "Arial Black", 9, true, false );

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

        colophon_2 = new VoltageLabel( "colophon_2", "colophon_2", this, "artificial acoustic distance generator" );
        AddComponent( colophon_2 );
        colophon_2.SetWantsMouseNotifications( false );
        colophon_2.SetPosition( 330, 324 );
        colophon_2.SetSize( 125, 13 );
        colophon_2.SetEditable( false, false );
        colophon_2.SetJustificationFlags( VoltageLabel.Justification.Right );
        colophon_2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        colophon_2.SetColor( new Color( 232, 232, 232, 255 ) );
        colophon_2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        colophon_2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        colophon_2.SetBorderSize( 1 );
        colophon_2.SetMultiLineEdit( false );
        colophon_2.SetIsNumberEditor( false );
        colophon_2.SetNumberEditorRange( 0, 100 );
        colophon_2.SetNumberEditorInterval( 1 );
        colophon_2.SetNumberEditorUsesMouseWheel( false );
        colophon_2.SetHasCustomTextHoverColor( false );
        colophon_2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        colophon_2.SetFont( "Arial Black", 9, true, false );
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

                // The heavy mappings (dB -> linear gain, RT60 -> feedback,
                // cutoff -> damping coefficient) happen only when a control
                // actually moves, not 48,000 times per second.
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
        double in1 = acCoupleInput( 0, input1.GetValue() );
        double in2 = acCoupleInput( 1, input2.GetValue() );
        double in3 = acCoupleInput( 2, inputJack3.GetValue() );
        double in4 = acCoupleInput( 3, input4.GetValue() );

        // Four independent matrix rows.  No normalization is applied: just as
        // with the 984, piling several sources into one row can push the analog
        // model harder.
        double rowA = matrixMix( 0, in1, in2, in3, in4 );
        double rowB = matrixMix( 1, in1, in2, in3, in4 );
        double rowC = matrixMix( 2, in1, in2, in3, in4 );
        double rowD = matrixMix( 3, in1, in2, in3, in4 );

        double outA = processOutputChannel( 0, rowA );
        double outB = processOutputChannel( 1, rowB );
        double outC = processOutputChannel( 2, rowC );
        double outD = processOutputChannel( 3, rowD );

        outputa.SetValue( outA );
        outputb.SetValue( outB );
        outputc.SetValue( outC );
        outputd.SetValue( outD );

        // Courtesy full mix.  Average rather than sum so four hot rows do not
        // automatically create a 4x level jump.
        outputMix.SetValue( 0.25 * (outA + outB + outC + outD) );

        // Passive-style front-panel multiple, implemented as ideal buffered
        // copies in the digital module.
        double mult = multIn.GetValue();
        multOut1.SetValue( mult );
        multOut2.SetValue( mult );
        multOut3.SetValue( mult );
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
        double a = input1.GetValue();
        double b = input2.GetValue();
        double c = inputJack3.GetValue();
        double d = input4.GetValue();

        outputa.SetValue( a );
        outputb.SetValue( b );
        outputc.SetValue( c );
        outputd.SetValue( d );
        outputMix.SetValue( 0.25 * (a + b + c + d) );

        double mult = multIn.GetValue();
        multOut1.SetValue( mult );
        multOut2.SetValue( mult );
        multOut3.SetValue( mult );
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

        if ( component == distancePerspective )
        {
            double seconds = perspectiveToSeconds( distancePerspective.GetValue() );
            if ( seconds < 10.0 )
                return String.format( "%.2f s", seconds );
            return String.format( "%.1f s", seconds );
        }

        if ( component == knobBassA || component == knobBassB ||
             component == knobBassC || component == knobBassD ||
             component == knobTrebleA || component == knobTrebleB ||
             component == knobTrebleC || component == knobTrebleD )
        {
            VoltageKnob k = (VoltageKnob) component;
            double db = (k.GetValue() - 0.5) * 24.0;
            return String.format( "%+.1f dB", db );
        }

        if ( component == distanceA || component == distanceB ||
             component == distanceC || component == distanceD )
        {
            VoltageKnob k = (VoltageKnob) component;
            return String.format( "%.0f%%", k.GetValue() * 100.0 );
        }
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
    private VoltageLabel colophon_2;
    private VoltageAudioJack multOut3;
    private VoltageAudioJack multOut2;
    private VoltageAudioJack multOut1;
    private VoltageAudioJack multIn;
    private VoltageLabel labelInputs;
    private VoltageLabel data;
    private VoltageLabel labelDistance;
    private VoltageLabel textLabel16;
    private VoltageKnob distanceD;
    private VoltageKnob distanceC;
    private VoltageKnob distanceB;
    private VoltageKnob distanceA;
    private VoltageAudioJack outputMix;
    private VoltageAudioJack outputd;
    private VoltageLabel labelD;
    private VoltageLabel labelC;
    private VoltageLabel labelB;
    private VoltageLabel labelA;
    private VoltageAudioJack outputc;
    private VoltageAudioJack outputb;
    private VoltageAudioJack outputa;
    private VoltageLabel labelMix;
    private VoltageLabel labelTreble;
    private VoltageLabel labelBass;
    private VoltageLabel colophon;
    private VoltageLabel input3;
    private VoltageLabel textLabel6;
    private VoltageLabel textLabel5;
    private VoltageLabel textLabel4;
    private VoltageKnob levelD;
    private VoltageKnob levelC;
    private VoltageKnob levelB;
    private VoltageKnob levelA;
    private VoltageKnob knobTrebleD;
    private VoltageKnob knobTrebleC;
    private VoltageKnob knobTrebleB;
    private VoltageKnob knobTrebleA;
    private VoltageKnob knobBassC;
    private VoltageKnob knobBassD;
    private VoltageKnob knobBassB;
    private VoltageKnob knobBassA;
    private VoltageKnob knobD4;
    private VoltageKnob knobC4;
    private VoltageKnob knobB4;
    private VoltageKnob knobA4;
    private VoltageKnob knobD3;
    private VoltageKnob knobC3;
    private VoltageKnob knobB3;
    private VoltageKnob knobA3;
    private VoltageKnob knobD2;
    private VoltageKnob knobC2;
    private VoltageKnob knobB2;
    private VoltageKnob knobA2;
    private VoltageKnob knobD1;
    private VoltageKnob knobC;
    private VoltageKnob knobB1;
    private VoltageKnob knobA1;
    private VoltageAudioJack input4;
    private VoltageAudioJack inputJack3;
    private VoltageAudioJack input2;
    private VoltageAudioJack input1;
    private VoltageLabel numberLabel;
    private VoltageLabel descriptionLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageKnob distancePerspective;
    private VoltageLine line17;
    private VoltageLine line18;
    private VoltageLine line16;


    //[user-code-and-variables]    Add your own variables and functions here

    // =========================================================================
    // LM-21 mk 3 DSP
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
        matrixTarget[0][0] = knobA1.GetValue();
        matrixTarget[0][1] = knobA2.GetValue();
        matrixTarget[0][2] = knobA3.GetValue();
        matrixTarget[0][3] = knobA4.GetValue();

        matrixTarget[1][0] = knobB1.GetValue();
        matrixTarget[1][1] = knobB2.GetValue();
        matrixTarget[1][2] = knobB3.GetValue();
        matrixTarget[1][3] = knobB4.GetValue();

        matrixTarget[2][0] = knobC.GetValue();
        matrixTarget[2][1] = knobC2.GetValue();
        matrixTarget[2][2] = knobC3.GetValue();
        matrixTarget[2][3] = knobC4.GetValue();

        matrixTarget[3][0] = knobD1.GetValue();
        matrixTarget[3][1] = knobD2.GetValue();
        matrixTarget[3][2] = knobD3.GetValue();
        matrixTarget[3][3] = knobD4.GetValue();

        bassGainTarget[0] = toneKnobToGain( knobBassA.GetValue() );
        bassGainTarget[1] = toneKnobToGain( knobBassB.GetValue() );
        bassGainTarget[2] = toneKnobToGain( knobBassC.GetValue() );
        bassGainTarget[3] = toneKnobToGain( knobBassD.GetValue() );

        trebleGainTarget[0] = toneKnobToGain( knobTrebleA.GetValue() );
        trebleGainTarget[1] = toneKnobToGain( knobTrebleB.GetValue() );
        trebleGainTarget[2] = toneKnobToGain( knobTrebleC.GetValue() );
        trebleGainTarget[3] = toneKnobToGain( knobTrebleD.GetValue() );

        distanceTarget[0] = distanceA.GetValue();
        distanceTarget[1] = distanceB.GetValue();
        distanceTarget[2] = distanceC.GetValue();
        distanceTarget[3] = distanceD.GetValue();

        levelTarget[0] = levelA.GetValue();
        levelTarget[1] = levelB.GetValue();
        levelTarget[2] = levelC.GetValue();
        levelTarget[3] = levelD.GetValue();

        updatePerspectiveTargets( distancePerspective.GetValue() );
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

        // 75% of the travel covers 0.5 to 5.5 s: broadly plausible territory
        // for a plate-like studio reverberator.  The final quarter stretches
        // exponentially to 30 s.
        if ( p <= 0.75 )
        {
            double t = p / 0.75;
            return 0.5 * Math.pow( 11.0, t );
        }

        double t = (p - 0.75) / 0.25;
        return 5.5 * Math.pow( 30.0 / 5.5, t );
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
        double age = clamp( (p - 0.75) / 0.25, 0.0, 1.0 );
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
        double y = x * NOMINAL_984_GAIN;

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

        // MIX is the final row output level.
        double out = withDistance * levelSmooth[ch];

        return acCoupleOutput( ch, out );
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

 