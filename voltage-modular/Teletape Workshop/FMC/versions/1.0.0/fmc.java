package com.insectlabs.fmc;


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


public class fmc extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public fmc( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "fmc - Four Channel Modulator", ModuleType.ModuleType_Effect, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "6ca1904407664838bd87c0737ff18d58" );
    }

void InitializeControls()
{

        rateHeaderLeft = new VoltageLabel( "rateHeaderLeft", "Rate Header Left", this, "RATE" );
        AddComponent( rateHeaderLeft );
        rateHeaderLeft.SetWantsMouseNotifications( false );
        rateHeaderLeft.SetPosition( 0, 161 );
        rateHeaderLeft.SetSize( 115, 13 );
        rateHeaderLeft.SetEditable( false, false );
        rateHeaderLeft.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        rateHeaderLeft.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        rateHeaderLeft.SetColor( new Color( 166, 36, 36, 255 ) );
        rateHeaderLeft.SetBkColor( new Color( 26, 42, 58, 0 ) );
        rateHeaderLeft.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        rateHeaderLeft.SetBorderSize( 1 );
        rateHeaderLeft.SetMultiLineEdit( false );
        rateHeaderLeft.SetIsNumberEditor( false );
        rateHeaderLeft.SetNumberEditorRange( 0, 100 );
        rateHeaderLeft.SetNumberEditorInterval( 1 );
        rateHeaderLeft.SetNumberEditorUsesMouseWheel( false );
        rateHeaderLeft.SetHasCustomTextHoverColor( false );
        rateHeaderLeft.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        rateHeaderLeft.SetFont( "Courier New", 11, true, false );

        rateHeaderRight = new VoltageLabel( "rateHeaderRight", "Rate Header Right", this, "–———––              ––———–" );
        AddComponent( rateHeaderRight );
        rateHeaderRight.SetWantsMouseNotifications( false );
        rateHeaderRight.SetPosition( 0, 161 );
        rateHeaderRight.SetSize( 115, 13 );
        rateHeaderRight.SetEditable( false, false );
        rateHeaderRight.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        rateHeaderRight.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        rateHeaderRight.SetColor( new Color( 230, 225, 212, 255 ) );
        rateHeaderRight.SetBkColor( new Color( 26, 42, 58, 0 ) );
        rateHeaderRight.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        rateHeaderRight.SetBorderSize( 1 );
        rateHeaderRight.SetMultiLineEdit( false );
        rateHeaderRight.SetIsNumberEditor( false );
        rateHeaderRight.SetNumberEditorRange( 0, 100 );
        rateHeaderRight.SetNumberEditorInterval( 1 );
        rateHeaderRight.SetNumberEditorUsesMouseWheel( false );
        rateHeaderRight.SetHasCustomTextHoverColor( false );
        rateHeaderRight.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        rateHeaderRight.SetFont( "Arial Black", 11, true, false );

        channelTwoNumberLabel = new VoltageLabel( "channelTwoNumberLabel", "Channel 2 Number", this, "II" );
        AddComponent( channelTwoNumberLabel );
        channelTwoNumberLabel.SetWantsMouseNotifications( false );
        channelTwoNumberLabel.SetPosition( 29, 1 );
        channelTwoNumberLabel.SetSize( 31, 15 );
        channelTwoNumberLabel.SetEditable( false, false );
        channelTwoNumberLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        channelTwoNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        channelTwoNumberLabel.SetColor( new Color( 164, 34, 34, 255 ) );
        channelTwoNumberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        channelTwoNumberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        channelTwoNumberLabel.SetBorderSize( 1 );
        channelTwoNumberLabel.SetMultiLineEdit( false );
        channelTwoNumberLabel.SetIsNumberEditor( false );
        channelTwoNumberLabel.SetNumberEditorRange( 0, 100 );
        channelTwoNumberLabel.SetNumberEditorInterval( 1 );
        channelTwoNumberLabel.SetNumberEditorUsesMouseWheel( false );
        channelTwoNumberLabel.SetHasCustomTextHoverColor( false );
        channelTwoNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        channelTwoNumberLabel.SetFont( "Arial Black", 12, false, false );

        voiceDDLabel = new VoltageLabel( "voiceDDLabel", "DD-2 Voice Legend", this, "—————— DD ——————" );
        AddComponent( voiceDDLabel );
        voiceDDLabel.SetWantsMouseNotifications( false );
        voiceDDLabel.SetPosition( 0, 293 );
        voiceDDLabel.SetSize( 115, 17 );
        voiceDDLabel.SetEditable( false, false );
        voiceDDLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voiceDDLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voiceDDLabel.SetColor( new Color( 164, 34, 34, 255 ) );
        voiceDDLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voiceDDLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voiceDDLabel.SetBorderSize( 1 );
        voiceDDLabel.SetMultiLineEdit( false );
        voiceDDLabel.SetIsNumberEditor( false );
        voiceDDLabel.SetNumberEditorRange( 0, 100 );
        voiceDDLabel.SetNumberEditorInterval( 1 );
        voiceDDLabel.SetNumberEditorUsesMouseWheel( false );
        voiceDDLabel.SetHasCustomTextHoverColor( false );
        voiceDDLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voiceDDLabel.SetFont( "Arial Black", 12, false, false );

        voiceDMLabel = new VoltageLabel( "voiceDMLabel", "DM-2 Voice Legend", this, "—————— DM ——————" );
        AddComponent( voiceDMLabel );
        voiceDMLabel.SetWantsMouseNotifications( false );
        voiceDMLabel.SetPosition( 0, 285 );
        voiceDMLabel.SetSize( 115, 17 );
        voiceDMLabel.SetEditable( false, false );
        voiceDMLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voiceDMLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voiceDMLabel.SetColor( new Color( 230, 225, 211, 255 ) );
        voiceDMLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voiceDMLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voiceDMLabel.SetBorderSize( 1 );
        voiceDMLabel.SetMultiLineEdit( false );
        voiceDMLabel.SetIsNumberEditor( false );
        voiceDMLabel.SetNumberEditorRange( 0, 100 );
        voiceDMLabel.SetNumberEditorInterval( 1 );
        voiceDMLabel.SetNumberEditorUsesMouseWheel( false );
        voiceDMLabel.SetHasCustomTextHoverColor( false );
        voiceDMLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voiceDMLabel.SetFont( "Arial Black", 12, false, false );

        voiceTRLabel = new VoltageLabel( "voiceTRLabel", "TR-2 Voice Legend", this, "—————— TR ——————" );
        AddComponent( voiceTRLabel );
        voiceTRLabel.SetWantsMouseNotifications( false );
        voiceTRLabel.SetPosition( 0, 276 );
        voiceTRLabel.SetSize( 115, 17 );
        voiceTRLabel.SetEditable( false, false );
        voiceTRLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voiceTRLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voiceTRLabel.SetColor( new Color( 164, 34, 34, 255 ) );
        voiceTRLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voiceTRLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voiceTRLabel.SetBorderSize( 1 );
        voiceTRLabel.SetMultiLineEdit( false );
        voiceTRLabel.SetIsNumberEditor( false );
        voiceTRLabel.SetNumberEditorRange( 0, 100 );
        voiceTRLabel.SetNumberEditorInterval( 1 );
        voiceTRLabel.SetNumberEditorUsesMouseWheel( false );
        voiceTRLabel.SetHasCustomTextHoverColor( false );
        voiceTRLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voiceTRLabel.SetFont( "Arial Black", 12, false, false );

        voiceBFLabel = new VoltageLabel( "voiceBFLabel", "BF-2 Voice Legend", this, "—————— BF ——————" );
        AddComponent( voiceBFLabel );
        voiceBFLabel.SetWantsMouseNotifications( false );
        voiceBFLabel.SetPosition( 0, 268 );
        voiceBFLabel.SetSize( 115, 17 );
        voiceBFLabel.SetEditable( false, false );
        voiceBFLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voiceBFLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voiceBFLabel.SetColor( new Color( 230, 225, 211, 255 ) );
        voiceBFLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voiceBFLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voiceBFLabel.SetBorderSize( 1 );
        voiceBFLabel.SetMultiLineEdit( false );
        voiceBFLabel.SetIsNumberEditor( false );
        voiceBFLabel.SetNumberEditorRange( 0, 100 );
        voiceBFLabel.SetNumberEditorInterval( 1 );
        voiceBFLabel.SetNumberEditorUsesMouseWheel( false );
        voiceBFLabel.SetHasCustomTextHoverColor( false );
        voiceBFLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voiceBFLabel.SetFont( "Arial Black", 12, false, false );

        brandLogo = new VoltageImage( "brandLogo", "TTW Imagemark", this, false );
        AddComponent( brandLogo );
        brandLogo.SetWantsMouseNotifications( false );
        brandLogo.SetPosition( 5, 342 );
        brandLogo.SetSize( 41, 13 );
        brandLogo.SetCurrentImage( "image image(3).svg" );

        moduleCodeLabel = new VoltageLabel( "moduleCodeLabel", "Module Code", this, "fmc" );
        AddComponent( moduleCodeLabel );
        moduleCodeLabel.SetWantsMouseNotifications( false );
        moduleCodeLabel.SetPosition( 35, 120 );
        moduleCodeLabel.SetSize( 25, 13 );
        moduleCodeLabel.SetEditable( false, false );
        moduleCodeLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        moduleCodeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleCodeLabel.SetColor( new Color( 230, 225, 211, 255 ) );
        moduleCodeLabel.SetBkColor( new Color( 26, 42, 58, 255 ) );
        moduleCodeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        moduleCodeLabel.SetBorderSize( 1 );
        moduleCodeLabel.SetMultiLineEdit( false );
        moduleCodeLabel.SetIsNumberEditor( false );
        moduleCodeLabel.SetNumberEditorRange( 0, 100 );
        moduleCodeLabel.SetNumberEditorInterval( 1 );
        moduleCodeLabel.SetNumberEditorUsesMouseWheel( false );
        moduleCodeLabel.SetHasCustomTextHoverColor( false );
        moduleCodeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleCodeLabel.SetFont( "Courier New", 13, true, false );

        creditLabel = new VoltageLabel( "creditLabel", "Artist Credit", this, "(r)aza (k)raft" );
        AddComponent( creditLabel );
        creditLabel.SetWantsMouseNotifications( false );
        creditLabel.SetPosition( 74, 40 );
        creditLabel.SetSize( 41, 14 );
        creditLabel.SetEditable( false, false );
        creditLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        creditLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        creditLabel.SetColor( new Color( 230, 225, 211, 255 ) );
        creditLabel.SetBkColor( new Color( 26, 42, 58, 255 ) );
        creditLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        creditLabel.SetBorderSize( 1 );
        creditLabel.SetMultiLineEdit( false );
        creditLabel.SetIsNumberEditor( false );
        creditLabel.SetNumberEditorRange( 0, 100 );
        creditLabel.SetNumberEditorInterval( 1 );
        creditLabel.SetNumberEditorUsesMouseWheel( false );
        creditLabel.SetHasCustomTextHoverColor( false );
        creditLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        creditLabel.SetFont( "Arial", 7, true, false );

        channelTwoParameterA = new VoltageKnob( "channelTwoParameterA", "Channel 2 Parameter A", this, 0.0, 1.0, 0.0 );
        AddComponent( channelTwoParameterA );
        channelTwoParameterA.SetWantsMouseNotifications( false );
        channelTwoParameterA.SetPosition( 35, 15 );
        channelTwoParameterA.SetSize( 20, 20 );
        channelTwoParameterA.SetSkin( "TR Large (white tick)" );
        channelTwoParameterA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelTwoParameterA.SetKnobParams( 215, 145 );
        channelTwoParameterA.DisplayValueInPercent( false );
        channelTwoParameterA.SetKnobAdjustsRing( true );

        channelFourParameterA = new VoltageKnob( "channelFourParameterA", "Channel 4 Parameter A", this, 0.0, 1.0, 0.0 );
        AddComponent( channelFourParameterA );
        channelFourParameterA.SetWantsMouseNotifications( false );
        channelFourParameterA.SetPosition( 85, 15 );
        channelFourParameterA.SetSize( 20, 20 );
        channelFourParameterA.SetSkin( "TR Large (white tick)" );
        channelFourParameterA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelFourParameterA.SetKnobParams( 215, 145 );
        channelFourParameterA.DisplayValueInPercent( false );
        channelFourParameterA.SetKnobAdjustsRing( true );

        channelTwoParameterB = new VoltageKnob( "channelTwoParameterB", "Channel 2 Parameter B", this, 0.0, 1.0, 0.0 );
        AddComponent( channelTwoParameterB );
        channelTwoParameterB.SetWantsMouseNotifications( false );
        channelTwoParameterB.SetPosition( 35, 55 );
        channelTwoParameterB.SetSize( 20, 20 );
        channelTwoParameterB.SetSkin( "TR Large (white tick)" );
        channelTwoParameterB.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelTwoParameterB.SetKnobParams( 215, 145 );
        channelTwoParameterB.DisplayValueInPercent( false );
        channelTwoParameterB.SetKnobAdjustsRing( true );

        channelFourParameterB = new VoltageKnob( "channelFourParameterB", "Channel 4 Parameter B", this, 0.0, 1.0, 0.0 );
        AddComponent( channelFourParameterB );
        channelFourParameterB.SetWantsMouseNotifications( false );
        channelFourParameterB.SetPosition( 85, 55 );
        channelFourParameterB.SetSize( 20, 20 );
        channelFourParameterB.SetSkin( "TR Large (white tick)" );
        channelFourParameterB.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelFourParameterB.SetKnobParams( 215, 145 );
        channelFourParameterB.DisplayValueInPercent( false );
        channelFourParameterB.SetKnobAdjustsRing( true );

        channelTwoParameterC = new VoltageKnob( "channelTwoParameterC", "Channel 2 Parameter C", this, 0.0, 1.0, 0.0 );
        AddComponent( channelTwoParameterC );
        channelTwoParameterC.SetWantsMouseNotifications( false );
        channelTwoParameterC.SetPosition( 35, 96 );
        channelTwoParameterC.SetSize( 20, 20 );
        channelTwoParameterC.SetSkin( "TR Large (white tick)" );
        channelTwoParameterC.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelTwoParameterC.SetKnobParams( 215, 145 );
        channelTwoParameterC.DisplayValueInPercent( false );
        channelTwoParameterC.SetKnobAdjustsRing( true );

        channelFourParameterC = new VoltageKnob( "channelFourParameterC", "Channel 4 Parameter C", this, 0.0, 1.0, 0.0 );
        AddComponent( channelFourParameterC );
        channelFourParameterC.SetWantsMouseNotifications( false );
        channelFourParameterC.SetPosition( 85, 95 );
        channelFourParameterC.SetSize( 20, 20 );
        channelFourParameterC.SetSkin( "TR Large (white tick)" );
        channelFourParameterC.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelFourParameterC.SetKnobParams( 215, 145 );
        channelFourParameterC.DisplayValueInPercent( false );
        channelFourParameterC.SetKnobAdjustsRing( true );

        channelTwoParameterD = new VoltageKnob( "channelTwoParameterD", "Channel 2 Parameter D", this, 0.0, 1.0, 0.5 );
        AddComponent( channelTwoParameterD );
        channelTwoParameterD.SetWantsMouseNotifications( false );
        channelTwoParameterD.SetPosition( 35, 135 );
        channelTwoParameterD.SetSize( 20, 20 );
        channelTwoParameterD.SetSkin( "TR Large (white tick)" );
        channelTwoParameterD.SetRange( 0.0, 1.0, 0.5, false, 0 );
        channelTwoParameterD.SetKnobParams( 215, 145 );
        channelTwoParameterD.DisplayValueInPercent( true );
        channelTwoParameterD.SetKnobAdjustsRing( true );

        channelFourParameterD = new VoltageKnob( "channelFourParameterD", "Channel 4 Parameter D", this, 0.0, 1.0, 0.5 );
        AddComponent( channelFourParameterD );
        channelFourParameterD.SetWantsMouseNotifications( false );
        channelFourParameterD.SetPosition( 85, 135 );
        channelFourParameterD.SetSize( 20, 20 );
        channelFourParameterD.SetSkin( "TR Large (white tick)" );
        channelFourParameterD.SetRange( 0.0, 1.0, 0.5, false, 0 );
        channelFourParameterD.SetKnobParams( 215, 145 );
        channelFourParameterD.DisplayValueInPercent( true );
        channelFourParameterD.SetKnobAdjustsRing( true );

        channelOneParameterA = new VoltageKnob( "channelOneParameterA", "Channel 1 Parameter A", this, 0.0, 1.0, 0.0 );
        AddComponent( channelOneParameterA );
        channelOneParameterA.SetWantsMouseNotifications( false );
        channelOneParameterA.SetPosition( 10, 15 );
        channelOneParameterA.SetSize( 20, 20 );
        channelOneParameterA.SetSkin( "TR Large" );
        channelOneParameterA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelOneParameterA.SetKnobParams( 215, 145 );
        channelOneParameterA.DisplayValueInPercent( true );
        channelOneParameterA.SetKnobAdjustsRing( true );

        channelThreeParameterA = new VoltageKnob( "channelThreeParameterA", "Channel 3 Parameter A", this, 0.0, 1.0, 0.0 );
        AddComponent( channelThreeParameterA );
        channelThreeParameterA.SetWantsMouseNotifications( false );
        channelThreeParameterA.SetPosition( 60, 15 );
        channelThreeParameterA.SetSize( 20, 20 );
        channelThreeParameterA.SetSkin( "TR Large" );
        channelThreeParameterA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelThreeParameterA.SetKnobParams( 215, 145 );
        channelThreeParameterA.DisplayValueInPercent( false );
        channelThreeParameterA.SetKnobAdjustsRing( true );

        channelThreeParameterB = new VoltageKnob( "channelThreeParameterB", "Channel 3 Parameter B", this, 0.0, 1.0, 0.0 );
        AddComponent( channelThreeParameterB );
        channelThreeParameterB.SetWantsMouseNotifications( false );
        channelThreeParameterB.SetPosition( 60, 56 );
        channelThreeParameterB.SetSize( 20, 20 );
        channelThreeParameterB.SetSkin( "TR Large" );
        channelThreeParameterB.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelThreeParameterB.SetKnobParams( 215, 145 );
        channelThreeParameterB.DisplayValueInPercent( false );
        channelThreeParameterB.SetKnobAdjustsRing( true );

        channelThreeParameterC = new VoltageKnob( "channelThreeParameterC", "Channel 3 Parameter C", this, 0.0, 1.0, 0.0 );
        AddComponent( channelThreeParameterC );
        channelThreeParameterC.SetWantsMouseNotifications( false );
        channelThreeParameterC.SetPosition( 60, 96 );
        channelThreeParameterC.SetSize( 20, 20 );
        channelThreeParameterC.SetSkin( "TR Large" );
        channelThreeParameterC.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelThreeParameterC.SetKnobParams( 215, 145 );
        channelThreeParameterC.DisplayValueInPercent( false );
        channelThreeParameterC.SetKnobAdjustsRing( true );

        channelOneParameterB = new VoltageKnob( "channelOneParameterB", "Channel 1 Parameter B", this, 0.0, 1.0, 0.0 );
        AddComponent( channelOneParameterB );
        channelOneParameterB.SetWantsMouseNotifications( false );
        channelOneParameterB.SetPosition( 10, 55 );
        channelOneParameterB.SetSize( 20, 20 );
        channelOneParameterB.SetSkin( "TR Large" );
        channelOneParameterB.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelOneParameterB.SetKnobParams( 215, 145 );
        channelOneParameterB.DisplayValueInPercent( true );
        channelOneParameterB.SetKnobAdjustsRing( true );

        channelOneParameterC = new VoltageKnob( "channelOneParameterC", "Channel 1 Parameter C", this, 0.0, 1.0, 0.0 );
        AddComponent( channelOneParameterC );
        channelOneParameterC.SetWantsMouseNotifications( false );
        channelOneParameterC.SetPosition( 10, 96 );
        channelOneParameterC.SetSize( 20, 20 );
        channelOneParameterC.SetSkin( "TR Large" );
        channelOneParameterC.SetRange( 0.0, 1.0, 0.0, false, 0 );
        channelOneParameterC.SetKnobParams( 215, 145 );
        channelOneParameterC.DisplayValueInPercent( true );
        channelOneParameterC.SetKnobAdjustsRing( true );

        channelOneParameterD = new VoltageKnob( "channelOneParameterD", "Channel 1 Parameter D", this, 0.0, 1.0, 0.5 );
        AddComponent( channelOneParameterD );
        channelOneParameterD.SetWantsMouseNotifications( false );
        channelOneParameterD.SetPosition( 10, 135 );
        channelOneParameterD.SetSize( 20, 20 );
        channelOneParameterD.SetSkin( "TR Large" );
        channelOneParameterD.SetRange( 0.0, 1.0, 0.5, false, 0 );
        channelOneParameterD.SetKnobParams( 215, 145 );
        channelOneParameterD.DisplayValueInPercent( true );
        channelOneParameterD.SetKnobAdjustsRing( true );

        channelOneInput = new VoltageAudioJack( "channelOneInput", "Channel 1 Audio Input", this, JackType.JackType_AudioInput );
        AddComponent( channelOneInput );
        channelOneInput.SetWantsMouseNotifications( false );
        channelOneInput.SetPosition( 3, 245 );
        channelOneInput.SetSize( 25, 25 );
        channelOneInput.SetSkin( "Jack Round 25px" );

        channelTwoInput = new VoltageAudioJack( "channelTwoInput", "Channel 2 Audio Input", this, JackType.JackType_AudioInput );
        AddComponent( channelTwoInput );
        channelTwoInput.SetWantsMouseNotifications( false );
        channelTwoInput.SetPosition( 31, 246 );
        channelTwoInput.SetSize( 25, 25 );
        channelTwoInput.SetSkin( "Jack Round 25px" );

        channelThreeInput = new VoltageAudioJack( "channelThreeInput", "Channel 3 Audio Input", this, JackType.JackType_AudioInput );
        AddComponent( channelThreeInput );
        channelThreeInput.SetWantsMouseNotifications( false );
        channelThreeInput.SetPosition( 59, 246 );
        channelThreeInput.SetSize( 25, 25 );
        channelThreeInput.SetSkin( "Jack Round 25px" );

        channelFourInput = new VoltageAudioJack( "channelFourInput", "Channel 4 Audio Input", this, JackType.JackType_AudioInput );
        AddComponent( channelFourInput );
        channelFourInput.SetWantsMouseNotifications( false );
        channelFourInput.SetPosition( 87, 246 );
        channelFourInput.SetSize( 25, 25 );
        channelFourInput.SetSkin( "Jack Round 25px" );

        channelOneVoiceSwitch = new VoltageSwitch( "channelOneVoiceSwitch", "Channel 1 Voice Selector", this, 3 );
        AddComponent( channelOneVoiceSwitch );
        channelOneVoiceSwitch.SetWantsMouseNotifications( false );
        channelOneVoiceSwitch.SetPosition( 9, 273 );
        channelOneVoiceSwitch.SetSize( 13, 34 );
        channelOneVoiceSwitch.SetSkin( "4-State Slide Vertical" );

        channelTwoVoiceSwitch = new VoltageSwitch( "channelTwoVoiceSwitch", "Channel 2 Voice Selector", this, 2 );
        AddComponent( channelTwoVoiceSwitch );
        channelTwoVoiceSwitch.SetWantsMouseNotifications( false );
        channelTwoVoiceSwitch.SetPosition( 37, 273 );
        channelTwoVoiceSwitch.SetSize( 13, 34 );
        channelTwoVoiceSwitch.SetSkin( "4-State Slide Vertical" );

        channelThreeVoiceSwitch = new VoltageSwitch( "channelThreeVoiceSwitch", "Channel 3 Voice Selector", this, 1 );
        AddComponent( channelThreeVoiceSwitch );
        channelThreeVoiceSwitch.SetWantsMouseNotifications( false );
        channelThreeVoiceSwitch.SetPosition( 65, 273 );
        channelThreeVoiceSwitch.SetSize( 13, 34 );
        channelThreeVoiceSwitch.SetSkin( "4-State Slide Vertical" );

        channelFourVoiceSwitch = new VoltageSwitch( "channelFourVoiceSwitch", "Channel 4 Voice Selector", this, 0 );
        AddComponent( channelFourVoiceSwitch );
        channelFourVoiceSwitch.SetWantsMouseNotifications( false );
        channelFourVoiceSwitch.SetPosition( 93, 273 );
        channelFourVoiceSwitch.SetSize( 13, 34 );
        channelFourVoiceSwitch.SetSkin( "4-State Slide Vertical" );

        channelOneOutput = new VoltageAudioJack( "channelOneOutput", "Channel 1 Audio Output", this, JackType.JackType_AudioOutput );
        AddComponent( channelOneOutput );
        channelOneOutput.SetWantsMouseNotifications( false );
        channelOneOutput.SetPosition( 3, 308 );
        channelOneOutput.SetSize( 25, 25 );
        channelOneOutput.SetSkin( "Mini Jack 25px" );

        channelTwoOutput = new VoltageAudioJack( "channelTwoOutput", "Channel 2 Audio Output", this, JackType.JackType_AudioOutput );
        AddComponent( channelTwoOutput );
        channelTwoOutput.SetWantsMouseNotifications( false );
        channelTwoOutput.SetPosition( 31, 308 );
        channelTwoOutput.SetSize( 25, 25 );
        channelTwoOutput.SetSkin( "Mini Jack 25px" );

        channelThreeOutput = new VoltageAudioJack( "channelThreeOutput", "Channel 3 Audio Output", this, JackType.JackType_AudioOutput );
        AddComponent( channelThreeOutput );
        channelThreeOutput.SetWantsMouseNotifications( false );
        channelThreeOutput.SetPosition( 59, 308 );
        channelThreeOutput.SetSize( 25, 25 );
        channelThreeOutput.SetSkin( "Mini Jack 25px" );

        channelFourOutput = new VoltageAudioJack( "channelFourOutput", "Channel 4 Audio Output", this, JackType.JackType_AudioOutput );
        AddComponent( channelFourOutput );
        channelFourOutput.SetWantsMouseNotifications( false );
        channelFourOutput.SetPosition( 87, 308 );
        channelFourOutput.SetSize( 25, 25 );
        channelFourOutput.SetSkin( "Mini Jack 25px" );

        mixOutput = new VoltageAudioJack( "mixOutput", "Mix Output", this, JackType.JackType_AudioOutput );
        AddComponent( mixOutput );
        mixOutput.SetWantsMouseNotifications( false );
        mixOutput.SetPosition( 87, 335 );
        mixOutput.SetSize( 25, 25 );
        mixOutput.SetSkin( "Mini Jack 25px" );

        mixOutputLabel = new VoltageLabel( "mixOutputLabel", "Mix Output Label", this, "mix!" );
        AddComponent( mixOutputLabel );
        mixOutputLabel.SetWantsMouseNotifications( false );
        mixOutputLabel.SetPosition( 60, 340 );
        mixOutputLabel.SetSize( 25, 13 );
        mixOutputLabel.SetEditable( false, false );
        mixOutputLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        mixOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        mixOutputLabel.SetColor( new Color( 166, 36, 36, 255 ) );
        mixOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        mixOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        mixOutputLabel.SetBorderSize( 1 );
        mixOutputLabel.SetMultiLineEdit( false );
        mixOutputLabel.SetIsNumberEditor( false );
        mixOutputLabel.SetNumberEditorRange( 0, 100 );
        mixOutputLabel.SetNumberEditorInterval( 1 );
        mixOutputLabel.SetNumberEditorUsesMouseWheel( false );
        mixOutputLabel.SetHasCustomTextHoverColor( false );
        mixOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        mixOutputLabel.SetFont( "Courier New", 13, true, false );

        parameterAHeader = new VoltageLabel( "parameterAHeader", "Parameter A Label", this, "a." );
        AddComponent( parameterAHeader );
        parameterAHeader.SetWantsMouseNotifications( false );
        parameterAHeader.SetPosition( 25, 31 );
        parameterAHeader.SetSize( 25, 13 );
        parameterAHeader.SetEditable( false, false );
        parameterAHeader.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        parameterAHeader.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        parameterAHeader.SetColor( new Color( 166, 36, 36, 255 ) );
        parameterAHeader.SetBkColor( new Color( 65, 65, 65, 0 ) );
        parameterAHeader.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        parameterAHeader.SetBorderSize( 1 );
        parameterAHeader.SetMultiLineEdit( false );
        parameterAHeader.SetIsNumberEditor( false );
        parameterAHeader.SetNumberEditorRange( 0, 100 );
        parameterAHeader.SetNumberEditorInterval( 1 );
        parameterAHeader.SetNumberEditorUsesMouseWheel( false );
        parameterAHeader.SetHasCustomTextHoverColor( false );
        parameterAHeader.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        parameterAHeader.SetFont( "Courier New", 13, true, false );

        parameterBHeader = new VoltageLabel( "parameterBHeader", "Parameter B Label", this, "b." );
        AddComponent( parameterBHeader );
        parameterBHeader.SetWantsMouseNotifications( false );
        parameterBHeader.SetPosition( 50, 46 );
        parameterBHeader.SetSize( 25, 13 );
        parameterBHeader.SetEditable( false, false );
        parameterBHeader.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        parameterBHeader.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        parameterBHeader.SetColor( new Color( 166, 36, 36, 255 ) );
        parameterBHeader.SetBkColor( new Color( 65, 65, 65, 0 ) );
        parameterBHeader.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        parameterBHeader.SetBorderSize( 1 );
        parameterBHeader.SetMultiLineEdit( false );
        parameterBHeader.SetIsNumberEditor( false );
        parameterBHeader.SetNumberEditorRange( 0, 100 );
        parameterBHeader.SetNumberEditorInterval( 1 );
        parameterBHeader.SetNumberEditorUsesMouseWheel( false );
        parameterBHeader.SetHasCustomTextHoverColor( false );
        parameterBHeader.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        parameterBHeader.SetFont( "Courier New", 13, true, false );

        parameterCHeader = new VoltageLabel( "parameterCHeader", "Parameter C Label", this, "c." );
        AddComponent( parameterCHeader );
        parameterCHeader.SetWantsMouseNotifications( false );
        parameterCHeader.SetPosition( 0, 111 );
        parameterCHeader.SetSize( 25, 13 );
        parameterCHeader.SetEditable( false, false );
        parameterCHeader.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        parameterCHeader.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        parameterCHeader.SetColor( new Color( 166, 36, 36, 255 ) );
        parameterCHeader.SetBkColor( new Color( 65, 65, 65, 0 ) );
        parameterCHeader.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        parameterCHeader.SetBorderSize( 1 );
        parameterCHeader.SetMultiLineEdit( false );
        parameterCHeader.SetIsNumberEditor( false );
        parameterCHeader.SetNumberEditorRange( 0, 100 );
        parameterCHeader.SetNumberEditorInterval( 1 );
        parameterCHeader.SetNumberEditorUsesMouseWheel( false );
        parameterCHeader.SetHasCustomTextHoverColor( false );
        parameterCHeader.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        parameterCHeader.SetFont( "Courier New", 13, true, false );

        parameterDHeader = new VoltageLabel( "parameterDHeader", "Parameter D Label", this, "d." );
        AddComponent( parameterDHeader );
        parameterDHeader.SetWantsMouseNotifications( false );
        parameterDHeader.SetPosition( 75, 151 );
        parameterDHeader.SetSize( 25, 13 );
        parameterDHeader.SetEditable( false, false );
        parameterDHeader.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        parameterDHeader.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        parameterDHeader.SetColor( new Color( 166, 36, 36, 255 ) );
        parameterDHeader.SetBkColor( new Color( 65, 65, 65, 0 ) );
        parameterDHeader.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        parameterDHeader.SetBorderSize( 1 );
        parameterDHeader.SetMultiLineEdit( false );
        parameterDHeader.SetIsNumberEditor( false );
        parameterDHeader.SetNumberEditorRange( 0, 100 );
        parameterDHeader.SetNumberEditorInterval( 1 );
        parameterDHeader.SetNumberEditorUsesMouseWheel( false );
        parameterDHeader.SetHasCustomTextHoverColor( false );
        parameterDHeader.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        parameterDHeader.SetFont( "Courier New", 13, true, false );

        channelOneNumberLabel = new VoltageLabel( "channelOneNumberLabel", "Channel 1 Number", this, "I" );
        AddComponent( channelOneNumberLabel );
        channelOneNumberLabel.SetWantsMouseNotifications( false );
        channelOneNumberLabel.SetPosition( 5, 1 );
        channelOneNumberLabel.SetSize( 31, 15 );
        channelOneNumberLabel.SetEditable( false, false );
        channelOneNumberLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        channelOneNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        channelOneNumberLabel.SetColor( new Color( 164, 34, 34, 255 ) );
        channelOneNumberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        channelOneNumberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        channelOneNumberLabel.SetBorderSize( 1 );
        channelOneNumberLabel.SetMultiLineEdit( false );
        channelOneNumberLabel.SetIsNumberEditor( false );
        channelOneNumberLabel.SetNumberEditorRange( 0, 100 );
        channelOneNumberLabel.SetNumberEditorInterval( 1 );
        channelOneNumberLabel.SetNumberEditorUsesMouseWheel( false );
        channelOneNumberLabel.SetHasCustomTextHoverColor( false );
        channelOneNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        channelOneNumberLabel.SetFont( "Arial Black", 12, false, false );

        channelThreeNumberLabel = new VoltageLabel( "channelThreeNumberLabel", "Channel 3 Number", this, "III" );
        AddComponent( channelThreeNumberLabel );
        channelThreeNumberLabel.SetWantsMouseNotifications( false );
        channelThreeNumberLabel.SetPosition( 55, 1 );
        channelThreeNumberLabel.SetSize( 31, 15 );
        channelThreeNumberLabel.SetEditable( false, false );
        channelThreeNumberLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        channelThreeNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        channelThreeNumberLabel.SetColor( new Color( 164, 34, 34, 255 ) );
        channelThreeNumberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        channelThreeNumberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        channelThreeNumberLabel.SetBorderSize( 1 );
        channelThreeNumberLabel.SetMultiLineEdit( false );
        channelThreeNumberLabel.SetIsNumberEditor( false );
        channelThreeNumberLabel.SetNumberEditorRange( 0, 100 );
        channelThreeNumberLabel.SetNumberEditorInterval( 1 );
        channelThreeNumberLabel.SetNumberEditorUsesMouseWheel( false );
        channelThreeNumberLabel.SetHasCustomTextHoverColor( false );
        channelThreeNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        channelThreeNumberLabel.SetFont( "Arial Black", 11, false, false );

        channelThreeParameterD = new VoltageKnob( "channelThreeParameterD", "Channel 3 Parameter D", this, 0.0, 1.0, 0.5 );
        AddComponent( channelThreeParameterD );
        channelThreeParameterD.SetWantsMouseNotifications( false );
        channelThreeParameterD.SetPosition( 60, 135 );
        channelThreeParameterD.SetSize( 20, 20 );
        channelThreeParameterD.SetSkin( "TR Large" );
        channelThreeParameterD.SetRange( 0.0, 1.0, 0.5, false, 0 );
        channelThreeParameterD.SetKnobParams( 215, 145 );
        channelThreeParameterD.DisplayValueInPercent( false );
        channelThreeParameterD.SetKnobAdjustsRing( true );

        channelFourNumberLabel = new VoltageLabel( "channelFourNumberLabel", "Channel 4 Number", this, "IIII" );
        AddComponent( channelFourNumberLabel );
        channelFourNumberLabel.SetWantsMouseNotifications( false );
        channelFourNumberLabel.SetPosition( 79, 1 );
        channelFourNumberLabel.SetSize( 31, 15 );
        channelFourNumberLabel.SetEditable( false, false );
        channelFourNumberLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        channelFourNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        channelFourNumberLabel.SetColor( new Color( 164, 34, 34, 255 ) );
        channelFourNumberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        channelFourNumberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        channelFourNumberLabel.SetBorderSize( 1 );
        channelFourNumberLabel.SetMultiLineEdit( false );
        channelFourNumberLabel.SetIsNumberEditor( false );
        channelFourNumberLabel.SetNumberEditorRange( 0, 100 );
        channelFourNumberLabel.SetNumberEditorInterval( 1 );
        channelFourNumberLabel.SetNumberEditorUsesMouseWheel( false );
        channelFourNumberLabel.SetHasCustomTextHoverColor( false );
        channelFourNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        channelFourNumberLabel.SetFont( "Arial Black", 11, false, false );

        channelOneRateCvInput = new VoltageAudioJack( "channelOneRateCvInput", "Channel 1 Rate CV Input", this, JackType.JackType_AudioInput );
        AddComponent( channelOneRateCvInput );
        channelOneRateCvInput.SetWantsMouseNotifications( false );
        channelOneRateCvInput.SetPosition( 3, 178 );
        channelOneRateCvInput.SetSize( 25, 25 );
        channelOneRateCvInput.SetSkin( "Jack Round 25px" );

        channelTwoRateCvInput = new VoltageAudioJack( "channelTwoRateCvInput", "Channel 2 Rate CV Input", this, JackType.JackType_AudioInput );
        AddComponent( channelTwoRateCvInput );
        channelTwoRateCvInput.SetWantsMouseNotifications( false );
        channelTwoRateCvInput.SetPosition( 31, 178 );
        channelTwoRateCvInput.SetSize( 25, 25 );
        channelTwoRateCvInput.SetSkin( "Jack Round 25px" );

        channelThreeRateCvInput = new VoltageAudioJack( "channelThreeRateCvInput", "Channel 3 Rate CV Input", this, JackType.JackType_AudioInput );
        AddComponent( channelThreeRateCvInput );
        channelThreeRateCvInput.SetWantsMouseNotifications( false );
        channelThreeRateCvInput.SetPosition( 59, 178 );
        channelThreeRateCvInput.SetSize( 25, 25 );
        channelThreeRateCvInput.SetSkin( "Jack Round 25px" );

        channelFourRateCvInput = new VoltageAudioJack( "channelFourRateCvInput", "Channel 4 Rate CV Input", this, JackType.JackType_AudioInput );
        AddComponent( channelFourRateCvInput );
        channelFourRateCvInput.SetWantsMouseNotifications( false );
        channelFourRateCvInput.SetPosition( 87, 178 );
        channelFourRateCvInput.SetSize( 25, 25 );
        channelFourRateCvInput.SetSkin( "Jack Round 25px" );

        channelOneHoldToggle = new VoltageToggle( "channelOneHoldToggle", "Channel 1 Hold", this, false, 0 );
        AddComponent( channelOneHoldToggle );
        channelOneHoldToggle.SetWantsMouseNotifications( false );
        channelOneHoldToggle.SetPosition( 3, 211 );
        channelOneHoldToggle.SetSize( 25, 25 );
        channelOneHoldToggle.SetSkin( "2500 Square Big" );
        channelOneHoldToggle.ShowOverlay( true );
        channelOneHoldToggle.SetOverlayText( "HOLD" );
        channelOneHoldToggle.SetOverlayTextFont( "Arial Black", 8, true, false );
        channelOneHoldToggle.SetOverlayTextColor( new Color( 0, 0, 0 ) );
        channelOneHoldToggle.SetOverlayArea( 0, 0, 0, 0 );
        channelOneHoldToggle.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        channelTwoHoldToggle = new VoltageToggle( "channelTwoHoldToggle", "Channel 2 Hold", this, false, 0 );
        AddComponent( channelTwoHoldToggle );
        channelTwoHoldToggle.SetWantsMouseNotifications( false );
        channelTwoHoldToggle.SetPosition( 31, 211 );
        channelTwoHoldToggle.SetSize( 25, 25 );
        channelTwoHoldToggle.SetSkin( "2500 Square Big" );
        channelTwoHoldToggle.ShowOverlay( true );
        channelTwoHoldToggle.SetOverlayText( "HOLD" );
        channelTwoHoldToggle.SetOverlayTextFont( "Arial Black", 8, true, false );
        channelTwoHoldToggle.SetOverlayTextColor( new Color( 0, 0, 0 ) );
        channelTwoHoldToggle.SetOverlayArea( 0, 0, 0, 0 );
        channelTwoHoldToggle.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        channelThreeHoldToggle = new VoltageToggle( "channelThreeHoldToggle", "Channel 3 Hold", this, false, 0 );
        AddComponent( channelThreeHoldToggle );
        channelThreeHoldToggle.SetWantsMouseNotifications( false );
        channelThreeHoldToggle.SetPosition( 59, 211 );
        channelThreeHoldToggle.SetSize( 25, 25 );
        channelThreeHoldToggle.SetSkin( "2500 Square Big" );
        channelThreeHoldToggle.ShowOverlay( true );
        channelThreeHoldToggle.SetOverlayText( "HOLD" );
        channelThreeHoldToggle.SetOverlayTextFont( "Arial Black", 8, true, false );
        channelThreeHoldToggle.SetOverlayTextColor( new Color( 0, 0, 0 ) );
        channelThreeHoldToggle.SetOverlayArea( 0, 0, 0, 0 );
        channelThreeHoldToggle.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        channelFourHoldToggle = new VoltageToggle( "channelFourHoldToggle", "Channel 4 Hold", this, false, 0 );
        AddComponent( channelFourHoldToggle );
        channelFourHoldToggle.SetWantsMouseNotifications( false );
        channelFourHoldToggle.SetPosition( 87, 211 );
        channelFourHoldToggle.SetSize( 25, 25 );
        channelFourHoldToggle.SetSkin( "2500 Square Big" );
        channelFourHoldToggle.ShowOverlay( true );
        channelFourHoldToggle.SetOverlayText( "HOLD" );
        channelFourHoldToggle.SetOverlayTextFont( "Arial Black", 8, true, false );
        channelFourHoldToggle.SetOverlayTextColor( new Color( 0, 0, 0 ) );
        channelFourHoldToggle.SetOverlayArea( 0, 0, 0, 0 );
        channelFourHoldToggle.SetOverlayTextJustification( VoltageButton.Justification.Centered );
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
        // Controls exist now (InitializeControls ran in the constructor). Build lookup arrays, pull current values.
        audioInputs = new VoltageAudioJack[] { channelOneInput, channelTwoInput, channelThreeInput, channelFourInput };
        audioOutputs = new VoltageAudioJack[] { channelOneOutput, channelTwoOutput, channelThreeOutput, channelFourOutput };
        rateCvInputs = new VoltageAudioJack[] { channelOneRateCvInput, channelTwoRateCvInput, channelThreeRateCvInput, channelFourRateCvInput };
        // Rows A..D per channel I..IIII (the Designer variable names are historical, the panel position is what counts).
        channelParameters = new VoltageKnob[][] {
            { channelOneParameterA,   channelOneParameterB,   channelOneParameterC,   channelOneParameterD },
            { channelTwoParameterA,  channelTwoParameterB,  channelTwoParameterC,  channelTwoParameterD },
            { channelThreeParameterA, channelThreeParameterB, channelThreeParameterC, channelThreeParameterD },
            { channelFourParameterA, channelFourParameterB, channelFourParameterC, channelFourParameterD } };
        voiceSelectors = new VoltageSwitch[] { channelOneVoiceSwitch, channelTwoVoiceSwitch, channelThreeVoiceSwitch, channelFourVoiceSwitch };
        holdToggles = new VoltageToggle[] { channelOneHoldToggle, channelTwoHoldToggle, channelThreeHoldToggle, channelFourHoldToggle };
        for (int i = 0; i < CHANNELS; i++) {
            for (int r = 0; r < ROWS; r++) {
                channelParameters[i][r].DisplayValueInPercent( false );   // typed entry then reaches EditComponentValue in the tooltip unit
                handleKnobChanged(channelParameters[i][r], channelParameters[i][r].GetValue());
            }
            handleSwitchChanged(voiceSelectors[i]);
            handleHoldChanged(holdToggles[i], holdToggles[i].GetValue());
        }
        core.reset();
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
        } else if (notification == ModuleNotifications.Switch_Changed) {
            handleSwitchChanged(component);
        } else if (notification == ModuleNotifications.Button_Changed) {
            handleHoldChanged(component, doubleValue);
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
        if (audioInputs == null) {
            return;                       // before Initialize: nothing wired yet
        }
        if (resumePending) {              // returning from host bypass: clear stale tails once
            resumePending = false;
            core.reset();
            java.util.Arrays.fill(wasConnected, false);
        }
        double sum = 0.0;
        for (int i = 0; i < CHANNELS; i++) {
            boolean patched = audioInputs[i].IsConnected();
            core.setPatched(i, patched);
            double in;
            if (patched) {
                if (!wasConnected[i]) {   // freshly patched: drop any stale tail, unless HOLD or a tail is still running
                    wasConnected[i] = true;
                    if (!core.wantsProcessing(i)) {
                        core.resetChannel(i);
                    }
                }
                in = readSignal(audioInputs[i]) * INV_FULL_SCALE_VOLTS;
            } else {
                wasConnected[i] = false;
                if (!core.wantsProcessing(i)) {   // nothing held and no audible tail: channel is idle and silent
                    audioOutputs[i].SetValue(0.0);
                    continue;
                }
                in = 0.0;                 // HOLD lit or tail still ringing: keep running with no input
            }
            core.setRateCv(i, readSignal(rateCvInputs[i]) * INV_FULL_SCALE_VOLTS);   // unpatched = 0
            double y = core.process(i, in);
            audioOutputs[i].SetValue(y * FULL_SCALE_VOLTS);
            sum += y;
        }
        mixOutput.SetValue(FmcCore.MIX_SCALE * sum * FULL_SCALE_VOLTS);
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
        // Direct per-channel routing, no DSP; mix uses the same half-sum as active processing.
        if (audioInputs == null) {
            return;
        }
        double sum = 0.0;
        for (int i = 0; i < CHANNELS; i++) {
            double input = readSignal(audioInputs[i]);
            audioOutputs[i].SetValue(input);
            sum += input;
        }
        mixOutput.SetValue(FmcCore.MIX_SCALE * sum);
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
        // Knobs: unit and meaning follow the voice selected on that channel.
        if (channelParameters != null) {
            for (int i = 0; i < CHANNELS; i++) {
                for (int r = 0; r < ROWS; r++) {
                    if (component == channelParameters[i][r]) {
                        return knobText(i, voiceForSwitch(voiceSelectors[i]), r, clamp01(channelParameters[i][r].GetValue()));
                    }
                }
            }
        }
        if (voiceSelectors != null) {
            for (int i = 0; i < CHANNELS; i++) {
                if (component == voiceSelectors[i]) {
                    return "Voice selector: top to bottom BF-2 flanger, TR-2 tremolo, DM-2 analog delay, DD-2 digital delay.";
                }
                if (component == holdToggles[i]) {
                    return "HOLD mutes new input and sustains repeats on DM-2 and DD-2; BF-2 and TR-2 ignore it.";
                }
                if (component == rateCvInputs[i]) {
                    return "Rate/Time CV: unpatched = 0 V; +5 V doubles LFO rate or halves delay time, within the selected voice range.";
                }
                if (component == audioInputs[i]) {
                    return String.format(java.util.Locale.ROOT, "Channel %d audio input; unpatched inputs are silent.", i + 1);
                }
                if (component == audioOutputs[i]) {
                    return String.format(java.util.Locale.ROOT, "Channel %d processed output.", i + 1);
                }
            }
        }
        if (component == mixOutput) {
            return "Mix output: half the sum of the four channel outputs; levels can exceed one channel's range.";
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
        // Typed entry arrives in the displayed unit; convert to the knob's own 0..1 range, the base class applies it below.
        if (channelParameters != null) {
            for (int i = 0; i < CHANNELS; i++) {
                for (int r = 0; r < ROWS; r++) {
                    if (component == channelParameters[i][r]) {
                        newValue = posFromTyped(voiceForSwitch(voiceSelectors[i]), r, newValue);
                    }
                }
            }
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
    private VoltageToggle channelFourHoldToggle;
    private VoltageToggle channelThreeHoldToggle;
    private VoltageToggle channelTwoHoldToggle;
    private VoltageToggle channelOneHoldToggle;
    private VoltageAudioJack channelFourRateCvInput;
    private VoltageAudioJack channelThreeRateCvInput;
    private VoltageAudioJack channelTwoRateCvInput;
    private VoltageAudioJack channelOneRateCvInput;
    private VoltageLabel channelFourNumberLabel;
    private VoltageKnob channelThreeParameterD;
    private VoltageLabel channelThreeNumberLabel;
    private VoltageLabel channelOneNumberLabel;
    private VoltageLabel parameterDHeader;
    private VoltageLabel parameterCHeader;
    private VoltageLabel parameterBHeader;
    private VoltageLabel parameterAHeader;
    private VoltageLabel mixOutputLabel;
    private VoltageAudioJack mixOutput;
    private VoltageAudioJack channelFourOutput;
    private VoltageAudioJack channelThreeOutput;
    private VoltageAudioJack channelTwoOutput;
    private VoltageAudioJack channelOneOutput;
    private VoltageSwitch channelFourVoiceSwitch;
    private VoltageSwitch channelThreeVoiceSwitch;
    private VoltageSwitch channelTwoVoiceSwitch;
    private VoltageSwitch channelOneVoiceSwitch;
    private VoltageAudioJack channelFourInput;
    private VoltageAudioJack channelThreeInput;
    private VoltageAudioJack channelTwoInput;
    private VoltageAudioJack channelOneInput;
    private VoltageKnob channelOneParameterD;
    private VoltageKnob channelOneParameterC;
    private VoltageKnob channelOneParameterB;
    private VoltageKnob channelThreeParameterC;
    private VoltageKnob channelThreeParameterB;
    private VoltageKnob channelThreeParameterA;
    private VoltageKnob channelOneParameterA;
    private VoltageKnob channelFourParameterD;
    private VoltageKnob channelTwoParameterD;
    private VoltageKnob channelFourParameterC;
    private VoltageKnob channelTwoParameterC;
    private VoltageKnob channelFourParameterB;
    private VoltageKnob channelTwoParameterB;
    private VoltageKnob channelFourParameterA;
    private VoltageKnob channelTwoParameterA;
    private VoltageLabel creditLabel;
    private VoltageLabel moduleCodeLabel;
    private VoltageImage brandLogo;
    private VoltageLabel voiceBFLabel;
    private VoltageLabel voiceTRLabel;
    private VoltageLabel voiceDMLabel;
    private VoltageLabel voiceDDLabel;
    private VoltageLabel channelTwoNumberLabel;
    private VoltageLabel rateHeaderRight;
    private VoltageLabel rateHeaderLeft;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final int CHANNELS = 4;
    private static final int ROWS = 4;                                   // knob rows A..D
    private static final double FULL_SCALE_VOLTS = 5.0;                 // core 1.0 = 5 V
    private static final double INV_FULL_SCALE_VOLTS = 1.0 / FULL_SCALE_VOLTS;
    // Slide-switch position -> voice (0 BF-2, 1 TR-2, 2 DM-2, 3 DD-2). Position 0 is full down (author-confirmed); the legend top to
    // bottom is BF, TR, DM, DD, so position 3 = BF ... position 0 = DD. Defaults 3,2,1,0 give channels I..IIII = BF, TR, DM, DD.
    private static final int[] POSITION_TO_VOICE = { 3, 2, 1, 0 };

    private final FmcCore core = new FmcCore(48000.0);
    private VoltageAudioJack[] audioInputs, audioOutputs, rateCvInputs;
    private VoltageKnob[][] channelParameters;                                       // [channel][row A..D]
    private VoltageSwitch[] voiceSelectors;
    private VoltageToggle[] holdToggles;
    private final boolean[] wasConnected = new boolean[CHANNELS];     // audio thread
    private boolean resumePending = false;                             // audio thread only

    private static double readSignal(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double value = jack.GetValue();
        return Double.isFinite(value) ? value : 0.0;
    }

    private static double clamp01(double p) {
        return p < 0.0 ? 0.0 : (p > 1.0 ? 1.0 : p);
    }

    private void handleKnobChanged(VoltageComponent component, double value) {
        for (int i = 0; i < CHANNELS; i++) {
            for (int r = 0; r < ROWS; r++) {
                if (component == channelParameters[i][r]) {
                    switch (r) {
                        case 0:  core.setA(i, value); break;
                        case 1:  core.setB(i, value); break;
                        case 2:  core.setC(i, value); break;
                        default: core.setD(i, value); break;
                    }
                }
            }
        }
    }

    private void handleSwitchChanged(VoltageComponent component) {
        for (int i = 0; i < CHANNELS; i++) {
            if (component == voiceSelectors[i]) {
                core.setVoice(i, voiceForSwitch(voiceSelectors[i]));
            }
        }
    }

    private void handleHoldChanged(VoltageComponent component, double value) {
        for (int i = 0; i < CHANNELS; i++) {
            if (component == holdToggles[i]) {
                core.setHold(i, value > 0.5);
            }
        }
    }

    private static int voiceForSwitch(VoltageSwitch sw) {
        int pos = (int) Math.round(sw.GetValue());
        if (pos < 0) pos = 0; else if (pos > 3) pos = 3;
        return POSITION_TO_VOICE[pos];
    }

    // Tooltip text for knob row r of channel ch, in the unit of the voice currently selected on that channel. p is the knob value 0..1.
    private String knobText(int ch, int voice, int r, double p) {
        java.util.Locale L = java.util.Locale.ROOT;
        switch (voice) {
            case 0:   // BF-2
                switch (r) {
                    case 0:  { double hz = FmcCore.bfRateHz(p); return String.format(L, "BF-2 Rate: %.2f Hz (%.2f s period)", hz, 1.0 / hz); }
                    case 1:  return String.format(L, "BF-2 Depth: %.0f%% of the sweep", p * 100.0);
                    case 2:  return String.format(L, "BF-2 Manual: %.1f ms centre delay (1 to 13 ms)", FmcCore.bfDelayMs(p));
                    default: return String.format(L, "BF-2 Resonance: %.0f%%", p * 100.0);
                }
            case 1:   // TR-2
                switch (r) {
                    case 0:  {
                        double span = channelParameters[ch][3].GetValue(), ceil = channelParameters[ch][2].GetValue();
                        return String.format(L, "TR-2 Macro: %.0f%% (rate %.2f Hz, depth %.0f%%)", p * 100.0, FmcCore.trRateHz(p, span), FmcCore.trDepth(p, ceil) * 100.0);
                    }
                    case 1:  return String.format(L, "TR-2 Wave: %.0f%% (0 triangle, 50 sine, 100 square)", p * 100.0);
                    case 2:  return String.format(L, "TR-2 Depth ceiling: %.0f%%", p * 100.0);
                    default: return String.format(L, "TR-2 Rate span: x%.2f (0.5 = x1)", Math.pow(4.0, 2.0 * p - 1.0));
                }
            case 2:   // DM-2
                switch (r) {
                    case 0:  { double ms = FmcCore.dmTimeMs(p); return String.format(L, "DM-2 Time: %.0f ms (%.2f s), 20 ms to 2.4 s", ms, ms * 0.001); }
                    case 1:  return String.format(L, "DM-2 Intensity: %.0f%% (feedback x%.2f, self-oscillates near the top)", p * 100.0, 1.1 * p);
                    case 2:  return mixText("DM-2", p);
                    default: return String.format(L, "DM-2 Grit: %.0f%% (0 clean, compander and hiss above)", p * 100.0);
                }
            default:  // DD-2
                switch (r) {
                    case 0:  { double ms = FmcCore.ddTimeMs(p); return String.format(L, "DD-2 Time: %.0f ms (%.2f s), %.1f kHz sampling", ms, ms * 0.001, FmcCore.ddSampleRateHz(p) * 0.001); }
                    case 1:  return String.format(L, "DD-2 F.Back: %.0f%%", p * 100.0);
                    case 2:  return mixText("DD-2", p);
                    default: return String.format(L, "DD-2 Bits: %.1f", FmcCore.ddBits(p));
                }
        }
    }

    // Delay Mix: 0 dry only; 0..50 wet rises with dry at full; 50..100 dry falls with wet at full; 100 wet only (send use).
    private static String mixText(String name, double p) {
        return String.format(java.util.Locale.ROOT, "%s Mix: %.0f%% (wet %.0f%%, dry %.0f%%; 50 = both full, 100 = wet only)",
                name, p * 100.0, FmcCore.mixWet(p) * 100.0, FmcCore.mixDry(p) * 100.0);
    }

    // Typed entry arrives in the tooltip unit (Hz, ms, percent, x, bits); convert it to the knob position 0..1.
    private static double posFromTyped(int voice, int r, double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return 0.5;
        }
        double p;
        if (voice == 0 && r == 0) {
            p = x > 0.0 ? Math.log(x / 0.0625) / Math.log(160.0) : 0.0;
        } else if (voice == 0 && r == 2) {
            p = x > 0.0 ? Math.log(x) / Math.log(13.0) : 0.0;
        } else if (voice == 1 && r == 3) {
            p = x > 0.0 ? (Math.log(x) / Math.log(4.0) + 1.0) * 0.5 : 0.0;
        } else if (voice == 2 && r == 0) {
            p = x > 0.0 ? FmcCore.dmPosForMs(x) : 0.0;
        } else if (voice == 3 && r == 0) {
            p = x > 0.0 ? FmcCore.ddPosForMs(x) : 0.0;
        } else if (voice == 3 && r == 3) {
            p = (16.0 - x) / 8.0;
        } else {
            p = x / 100.0;
        }
        return clamp01(p);
    }

    // ---- FmcCore (framework-free; same source as FmcCore.java, tested headless) ----
    private static final class FmcCore {
    
        public static final int CHANNELS = 4;
        public static final int V_BF = 0, V_TR = 1, V_DM = 2, V_DD = 3;
        public static final double CEILING = 2.0;
        public static final double MIX_SCALE = 0.5;
        public static final double IN_LIMIT = 4.0;            // sanitize clamp, 20 V
        public static final double DM_TMIN = 0.020, DM_TMAX = 2.4;
        public static final double DD_TMIN = 0.0125, DD_TMAX = 6.4;
        public static final double CV_OCT_PER_UNIT = 1.0;     // octaves of LFO rate per 5 V [GUESS]
        public static final double NOISE_RMS = 6.3e-4;        // DM-2 BBD noise at full Grit, core units (-64 dBFS) [GUESS, author approved]
        static final int RING = 1 << 17, MASK = RING - 1;
        static final int NCAP = 131000;                       // DD-2 memory words: 8 x 16384 [GUESS]
        static final int BF_RING = 4096;
        static final double MAX_G = 100.0;                    // = sqrt(COMP_REF / 1e-5)
        static final double COMP_REF = 0.1;                   // compander reference amplitude [GUESS]
        static final double TAIL_FB_MAX = 0.7;                // feedback cap for an unpatched channel so its tail dies out [MODEL, author request]
        static final double ACTIVE_THR = 3e-5;                // -90 dBFS: below this the wet signal counts as silent [GUESS]
        static final int QUIET_MAX = 1 << 30;
        static final int ENV_DECIM = 48, ENV_RING = 6700;      // loop-level history at 1 kHz, covers 6.7 s
        static final double ENV_K = 10.0;                     // loop-level detector rate (1/tau in 1/s, tau 0.1 s)
        static final double HOLD_KAPPA = 0.5;                 // HOLD level correction per repeat [MODEL]
        static final double LOOP_LIMIT = 1.5;                 // soft limit inside feedback loops [MODEL]
    
        private final Channel[] ch = new Channel[CHANNELS];
    
        public FmcCore(double sampleRate) {
            double fs = (sampleRate > 8000.0 && sampleRate < 400000.0) ? sampleRate : 48000.0;
            for (int i = 0; i < CHANNELS; i++) ch[i] = new Channel(fs, 0x9E3779B97F4A7C15L * (i + 1));
        }
    
        public void setVoice(int c, int v) { if (ok(c)) ch[c].setVoice(v); }
        public void setA(int c, double v) { if (ok(c)) ch[c].aT = clamp01(v, 0.5); }
        public void setB(int c, double v) { if (ok(c)) ch[c].bT = clamp01(v, 0.5); }
        public void setC(int c, double v) { if (ok(c)) ch[c].cT = clamp01(v, 0.5); }
        public void setD(int c, double v) { if (ok(c)) ch[c].dT = clamp01(v, 0.5); }
        /** HOLD on the DM-2 and DD-2 voices; ignored by BF-2 and TR-2. */
        public void setHold(int c, boolean h) { if (ok(c)) ch[c].hold = h; }
        /** Tell the core whether the channel's input jack is patched (unpatched: feedback capped so tails die out). */
        public void setPatched(int c, boolean p) { if (ok(c)) ch[c].patched = p; }
        /** True while an unpatched channel still has something to do: HOLD lit on a delay voice, or an audible tail. */
        public boolean wantsProcessing(int c) { return ok(c) && ch[c].wantsProcessing(); }
        /** Rate CV in core units (1.0 = 5 V); BF-2 and TR-2 rate. NaN/Inf ignored. */
        public void setRateCv(int c, double v) {
            if (ok(c)) ch[c].cv = (Double.isNaN(v) || Double.isInfinite(v)) ? 0.0 : Math.max(-4.0, Math.min(4.0, v));
        }
        public void reset() { for (int i = 0; i < CHANNELS; i++) ch[i].resetAll(); }
        public void resetChannel(int c) { if (ok(c)) ch[c].resetAll(); }
        public double process(int c, double in) { return ok(c) ? ch[c].process(in) : 0.0; }
        public static double mix(double a, double b, double c, double d) { return MIX_SCALE * (a + b + c + d); }
    
        private static boolean ok(int c) { return c >= 0 && c < CHANNELS; }
        private static double clamp01(double v, double fb) {
            if (Double.isNaN(v) || Double.isInfinite(v)) return fb;
            return v < 0 ? 0 : (v > 1 ? 1 : v);
        }
    
        // ---- public mappings used by the module layer (tooltips, typed entry) ----
        public static double dmTimeMs(double p) { return 1000.0 * DM_TMIN * Math.pow(DM_TMAX / DM_TMIN, p); }
        public static double dmPosForMs(double ms) { return Math.log(ms / (1000.0 * DM_TMIN)) / Math.log(DM_TMAX / DM_TMIN); }
        public static double ddTimeMs(double p) { return 1000.0 * DD_TMIN * Math.pow(DD_TMAX / DD_TMIN, p); }
        public static double ddPosForMs(double ms) { return Math.log(ms / (1000.0 * DD_TMIN)) / Math.log(DD_TMAX / DD_TMIN); }
        /** BF-2 LFO rate in Hz: period 16 s .. 100 ms [DOC]. */
        public static double bfRateHz(double p) { return 0.0625 * Math.pow(160.0, p); }
        public static double bfDelayMs(double p) { return Math.pow(13.0, p); }          // 1..13 ms [DOC]
        /** DD-2 effective sample rate in Hz at time knob position p (48 kHz while the 131000-word memory still fits). */
        public static double ddSampleRateHz(double p) { double t = DD_TMIN * Math.pow(DD_TMAX / DD_TMIN, p); double r = NCAP / t; return r > 48000.0 ? 48000.0 : r; }
        /** Delay mix law: wet gain for knob position c. */
        public static double mixWet(double c) { double w = 2.0 * c; return w > 1.0 ? 1.0 : w; }
        /** Delay mix law: dry gain for knob position c. */
        public static double mixDry(double c) { double w = 2.0 * (1.0 - c); return w > 1.0 ? 1.0 : (w < 0.0 ? 0.0 : w); }
        public static double ddBits(double d) { return 16.0 - 8.0 * d; }
        public static double trRateHz(double macro, double span) { return 0.5 * Math.pow(24.0, macro) * Math.pow(4.0, 2.0 * span - 1.0); }
        public static double trDepth(double macro, double ceil) { return ceil * Math.min(1.0, 0.1 + 0.9 * macro); }
        /** DM-2 bandwidth limit in Hz for delay T seconds: 4096-stage BBD clock/2 x 0.45, capped by the fixed filters [READ/MODEL]. */
        public static double dmCutoffHz(double t) {
            double te = t < 0.3 ? t : 0.3;                    // extended stage count keeps the 300 ms clock [MODEL]
            double clock = 4096.0 / (2.0 * te);
            double fc = 0.45 * clock;
            return fc > 5000.0 ? 5000.0 : fc;
        }
    
        // ---- helpers ----
        static double limit(double x) {
            double u = x * (1.0 / LOOP_LIMIT); double q = u * u; q = q * q;      // 4th-power knee: transparent below ~0.5 of the limit
            return x / Math.sqrt(Math.sqrt(1.0 + q));
        }
    
        /** 4-point Hermite read; delay in samples (>= 3), buffer power of two. */
        static double hermite(double[] b, int mask, int wp, double d) {
            double pos = wp - d;
            int i = (int) Math.floor(pos);
            double t = pos - i;
            double y0 = b[(i - 1) & mask], y1 = b[i & mask], y2 = b[(i + 1) & mask], y3 = b[(i + 2) & mask];
            double c1 = 0.5 * (y2 - y0), c2 = y0 - 2.5 * y1 + 2.0 * y2 - 0.5 * y3, c3 = 0.5 * (y3 - y0) + 1.5 * (y1 - y2);
            return ((c3 * t + c2) * t + c1) * t + y1;
        }
    
        static final double LN2 = 0.6931471805599453;
        static double clampLn(double ln, double tmin, double tmax) {
            double lo = Math.log(tmin), hi = Math.log(tmax);
            return ln < lo ? lo : (ln > hi ? hi : ln);
        }
    
        /** One-pole lowpass coefficient for cutoff fc. */
        static double lpCoef(double fc, double fs) {
            double f = fc < 10.0 ? 10.0 : (fc > 0.45 * fs ? 0.45 * fs : fc);
            return 1.0 - Math.exp(-2.0 * Math.PI * f / fs);
        }
    
        /** First-order section from H(s) = (1 + s/wz) / (1 + s/wp), bilinear. */
        static final class Shelf {
            double b0, b1, a1, x1, y1;
            Shelf(double fz, double fp, double fs) {
                double kz = 2.0 * fs / (2.0 * Math.PI * fz), kp = 2.0 * fs / (2.0 * Math.PI * fp);
                b0 = (1.0 + kz) / (1.0 + kp); b1 = (1.0 - kz) / (1.0 + kp); a1 = (1.0 - kp) / (1.0 + kp);
            }
            double run(double x) { double y = b0 * x + b1 * x1 - a1 * y1; x1 = x; y1 = y; return y; }
            void reset() { x1 = 0; y1 = 0; }
        }
    
        /** NE570-style compressor gain: gain = sqrt(ref/env), env = peak detector with instant attack and 50 ms release.
         *  The expander undoes the exact gain that was applied (stored next to the delayed signal), so the round trip is transparent and
         *  the audible compander behaviour is the noise breathing: BBD noise is divided by the delayed gain. [MODEL] */
        static final class Compander {
            double env; final double rel;
            Compander(double fs) { rel = Math.exp(-1.0 / (0.050 * fs)); }
            double gain(double x) {
                double r = x < 0 ? -x : x;
                double d = env * rel;
                env = r > d ? r : d;
                double e = env < 1e-5 ? 1e-5 : env;
                return Math.sqrt(COMP_REF / e);                // at most sqrt(0.1/1e-5) = 100
            }
            void reset() { env = 0; }
        }
    
        private static final class Channel {
            final double fs;
            double aT = 0.5, bT = 0.5, cT = 0.5, dT = 0.5, a = 0.5, b = 0.5, c = 0.5, d = 0.5;
            double cv, cvS; boolean hold, patched = true;
            double holdLnG, holdEnv, meter, tLast = 0.1; boolean holdPrev; int holdAge; int quiet = QUIET_MAX;
            final double[] envRing = new double[ENV_RING]; int envWp, envDiv;
            final double ks, kcv;                       // 5 ms parameter smoothing
            boolean fresh = true;
            int vNow, vWant; double fade = 1.0; final double fadeStep;
            long noiseState;
            // BF-2
            final double[] bfBuf = new double[BF_RING]; int bfWp; double bfPh, bfFbLp; final double bfFbK;
            // TR-2
            double trPh, trG = 1.0; final double trK;
            // DM-2
            final double[] dmBuf = new double[RING], dmGBuf = new double[RING]; int dmWp;
            double dmLnT, dmFbSig; final double dmSlew;
            final double[] dmIn = new double[3], dmOut = new double[3];
            final Compander dmComp;
            // DD-2
            final double[] ddBuf = new double[RING], ddGBuf = new double[RING]; int ddWp;
            double ddLnT, ddAcc, ddHeld, ddHeldG = MAX_G, ddFbSig; final double ddSlew;
            final double[] ddIn = new double[3], ddOut = new double[3];
            final double ddK7;
            final Compander ddComp;
            final Shelf pre, de;
    
            Channel(double fs, long seed) {
                this.fs = fs;
                ks = 1.0 - Math.exp(-1.0 / (0.005 * fs));
                kcv = 1.0 - Math.exp(-1.0 / (0.002 * fs));
                fadeStep = 1.0 / (0.005 * fs);
                noiseState = seed == 0 ? 0x1234567L : seed;
                bfFbK = lpCoef(6000.0, fs);
                trK = 1.0 - Math.exp(-1.0 / (0.0015 * fs));
                dmSlew = 1.0 - Math.exp(-1.0 / (0.040 * fs));
                ddSlew = dmSlew;
                ddK7 = lpCoef(7000.0, fs);
                dmComp = new Compander(fs);
                ddComp = new Compander(fs);
                pre = new Shelf(2000.0, 5600.0, fs);     // +9 dB HF shelf [GUESS]
                de = new Shelf(5600.0, 2000.0, fs);
                dmLnT = Math.log(DM_TMIN); ddLnT = Math.log(DD_TMIN);
            }
    
            void setVoice(int v) {
                int n = v < 0 ? 0 : (v > 3 ? 3 : v);
                vWant = n;
                if (fresh && vNow != n) { vNow = n; clearVoice(n); }
            }
    
            boolean wantsProcessing() {
                if (hold && (vNow == V_DM || vNow == V_DD)) return true;
                int lim = vNow == V_TR ? 0 : (vNow == V_BF ? 4800 : (int) (1.2 * tLast * fs) + 4800);
                return quiet < lim;
            }
    
            /** loopTrack: level of the signal entering the delay loop (what the buffer holds). loopGain: feedback gain for the delay loops:
             *  user value, tail cap when unpatched, or the HOLD level controller that keeps the loop at its level when HOLD was pressed. */
            void loopTrack(double u, double t) {
                double au = u < 0 ? -u : u;
                tLast = t;
                holdEnv += ENV_K / fs * (au - holdEnv);
                if (++envDiv >= ENV_DECIM) {
                    envDiv = 0;
                    envWp = (envWp + 1) % ENV_RING;
                    envRing[envWp] = holdEnv;
                }
            }
    
            double loopGain(double normal, double wet, double t) {
                meter = wet < 0 ? -wet : wet;
                if (hold) {
                    // HOLD level controller: compare the loop level now with the same phase one delay time ago and trim the gain so they match.
                    if (!holdPrev) { holdPrev = true; holdLnG = 0.0; holdAge = 0; }
                    holdAge++;
                    int nDel = (int) (t * (fs / ENV_DECIM) + 0.5);
                    nDel = nDel < 1 ? 1 : (nDel > ENV_RING - 2 ? ENV_RING - 2 : nDel);
                    double past = envRing[(envWp - nDel + ENV_RING) % ENV_RING];
                    double now = holdEnv;
                    if (holdAge < (int) (t * fs) + (int) (0.3 * fs)) return Math.exp(holdLnG);   // first pass round: the 'past' is not held content yet
                    past = past < 1e-6 ? 1e-6 : past; now = now < 1e-6 ? 1e-6 : now;
                    holdLnG += (HOLD_KAPPA / (t * fs)) * (Math.log(past) - Math.log(now));
                    holdLnG = holdLnG < -0.357 ? -0.357 : (holdLnG > 0.0953 ? 0.0953 : holdLnG);
                    return Math.exp(holdLnG);
                }
                holdPrev = false;
                return (!patched && normal > TAIL_FB_MAX) ? TAIL_FB_MAX : normal;
            }
    
            void resetAll() {
                for (int v = 0; v < 4; v++) clearVoice(v);
                fade = 1.0; vNow = vWant; fresh = true;
            }
    
            void clearVoice(int v) {
                quiet = QUIET_MAX; holdPrev = false; holdEnv = 0.0; holdLnG = 0.0; meter = 0.0;
                java.util.Arrays.fill(envRing, 0.0); envWp = 0; envDiv = 0;
                if (v == V_BF) { java.util.Arrays.fill(bfBuf, 0.0); bfWp = 0; bfPh = 0; bfFbLp = 0; }
                else if (v == V_TR) { trPh = 0; trG = 1.0; }
                else if (v == V_DM) {
                    java.util.Arrays.fill(dmBuf, 0.0); dmWp = 0; dmFbSig = 0;
                    for (int i = 0; i < 3; i++) { dmIn[i] = 0; dmOut[i] = 0; }
                    java.util.Arrays.fill(dmGBuf, MAX_G); dmComp.reset();
                } else {
                    java.util.Arrays.fill(ddBuf, 0.0); ddWp = 0; ddAcc = 0; ddHeld = 0; ddFbSig = 0;
                    for (int i = 0; i < 3; i++) { ddIn[i] = 0; ddOut[i] = 0; }
                    java.util.Arrays.fill(ddGBuf, MAX_G); ddHeldG = MAX_G; ddComp.reset(); pre.reset(); de.reset();
                }
            }
    
            double noise() {
                long x = noiseState;
                x ^= x << 13; x ^= x >>> 7; x ^= x << 17;
                noiseState = x;
                return ((x >>> 11) * (1.0 / 9007199254740992.0)) * 2.0 - 1.0;   // uniform -1..1
            }
    
            double process(double in) {
                double x = (Double.isNaN(in) || Double.isInfinite(in)) ? 0.0 : (in > IN_LIMIT ? IN_LIMIT : (in < -IN_LIMIT ? -IN_LIMIT : in));
                cvS += kcv * (cv - cvS);
                if (fresh) { a = aT; b = bT; c = cT; d = dT; cvS = cv; fresh = false; snapTime(); }
                else { a += ks * (aT - a); b += ks * (bT - b); c += ks * (cT - c); d += ks * (dT - d); }
                if (vWant != vNow) {
                    fade -= fadeStep;
                    if (fade <= 0.0) { fade = 0.0; vNow = vWant; clearVoice(vNow); snapTime(); }
                } else if (fade < 1.0) {
                    fade += fadeStep; if (fade > 1.0) fade = 1.0;
                }
                double y;
                meter = 0.0;
                switch (vNow) {
                    case V_BF: y = bf(x); break;
                    case V_TR: y = tr(x); break;
                    case V_DM: y = dm(x); break;
                    default: y = dd(x); break;
                }
                y *= fade;
                if (Double.isNaN(y)) { clearVoice(vNow); y = 0.0; }
                if (meter > ACTIVE_THR || (hold && (vNow == V_DM || vNow == V_DD))) quiet = 0; else if (quiet < QUIET_MAX) quiet++;
                return y > CEILING ? CEILING : (y < -CEILING ? -CEILING : y);
            }
    
            void snapTime() {
                dmLnT = Math.log(DM_TMIN) + a * Math.log(DM_TMAX / DM_TMIN);
                ddLnT = Math.log(DD_TMIN) + a * Math.log(DD_TMAX / DD_TMIN);
            }
    
            // ---- BF-2 ----
            double bf(double x) {
                double rate = bfRateHz(a) * Math.pow(2.0, cv * CV_OCT_PER_UNIT);
                if (rate > 20.0) rate = 20.0;
                bfPh += rate / fs; if (bfPh >= 1.0) bfPh -= 1.0;
                double tri = 1.0 - 4.0 * Math.abs(bfPh - 0.5);
                double p = c + 0.5 * b * tri;
                p = p < 0 ? 0 : (p > 1 ? 1 : p);
                double dly = bfDelayMs(p) * 0.001 * fs;
                double wet = hermite(bfBuf, BF_RING - 1, bfWp, dly < 4.0 ? 4.0 : dly);
                meter = wet < 0 ? -wet : wet;
                bfFbLp += bfFbK * (wet - bfFbLp);
                bfBuf[bfWp] = limit(x + 0.9 * d * bfFbLp);
                bfWp = (bfWp + 1) & (BF_RING - 1);
                return 0.7071 * (x + wet);
            }
    
            // ---- TR-2 ----
            double tr(double x) {
                double rate = trRateHz(a, d) * Math.pow(2.0, cv * CV_OCT_PER_UNIT);
                rate = rate < 0.05 ? 0.05 : (rate > 30.0 ? 30.0 : rate);
                trPh += rate / fs; if (trPh >= 1.0) trPh -= 1.0;
                double sn = Math.sin(6.283185307179586 * trPh);
                double tri = 1.0 - 4.0 * Math.abs(((trPh + 0.25) % 1.0) - 0.5);       // same phase as sine
                double s;
                if (b < 0.5) { double u = b * 2.0; s = (1.0 - u) * tri + u * sn; }
                else { double v = (b - 0.5) * 2.0; double sq = sn / Math.sqrt(sn * sn + 0.003); s = (1.0 - v) * sn + v * sq; }
                double pass = 0.5 + 0.5 * s;
                double u = 1.0 - trDepth(a, c) * (1.0 - pass);
                double g = u * u;                                                      // JFET attenuator law [GUESS]
                trG += trK * (g - trG);
                return x * trG;
            }
    
            // ---- DM-2 ----
            double dm(double x) {
                double lnTt = Math.log(DM_TMIN) + a * Math.log(DM_TMAX / DM_TMIN);
                dmLnT += dmSlew * (lnTt - dmLnT);
                double t = Math.exp(clampLn(dmLnT - cvS * CV_OCT_PER_UNIT * LN2, DM_TMIN, DM_TMAX));   // CV = clock rate: up = shorter
                double k = lpCoef(dmCutoffHz(t), fs);
                double u = limit((hold ? 0.0 : x) + dmFbSig);
                loopTrack(u, t);
                double s = u;
                for (int i = 0; i < 3; i++) { dmIn[i] += k * (s - dmIn[i]); s = dmIn[i]; }
                boolean grit = d > 0.001;
                double gc = grit ? dmComp.gain(s) : 1.0;
                dmBuf[dmWp] = s * gc;
                dmGBuf[dmWp] = gc;
                dmWp = (dmWp + 1) & MASK;
                double dly = t * fs;
                double dd2 = dly < 4.0 ? 4.0 : (dly > RING - 8 ? RING - 8 : dly);
                double r = hermite(dmBuf, MASK, dmWp, dd2);
                if (grit) r += d * NOISE_RMS * 1.7321 * noise();
                double o = r;
                for (int i = 0; i < 3; i++) { dmOut[i] += k * (o - dmOut[i]); o = dmOut[i]; }
                double wet = o;
                if (grit) { double gr = hermite(dmGBuf, MASK, dmWp, dd2); wet = o / (gr < 1e-3 ? 1e-3 : gr); }
                dmFbSig = loopGain(1.1 * b, wet, t) * wet;
                return mixDry(c) * x + mixWet(c) * wet;
            }
    
            // ---- DD-2 ----
            double dd(double x) {
                double lnTt = Math.log(DD_TMIN) + a * Math.log(DD_TMAX / DD_TMIN);
                ddLnT += ddSlew * (lnTt - ddLnT);
                double t = Math.exp(clampLn(ddLnT - cvS * CV_OCT_PER_UNIT * LN2, DD_TMIN, DD_TMAX));
                double fsEff = NCAP / t; if (fsEff > fs) fsEff = fs;
                double u = limit((hold ? 0.0 : x) + ddFbSig);
                loopTrack(u, t);
                double s = pre.run(u);
                for (int i = 0; i < 3; i++) { ddIn[i] += ddK7 * (s - ddIn[i]); s = ddIn[i]; }
                double gc = ddComp.gain(s);
                double w = s * gc;
                ddAcc += fsEff / fs;
                if (ddAcc >= 1.0) {
                    ddAcc -= 1.0;
                    double step = 2.0 / Math.pow(2.0, ddBits(d));
                    double q = Math.floor(w / step + 0.5) * step;
                    q = q > 1.0 ? 1.0 : (q < -1.0 ? -1.0 : q);
                    ddBuf[ddWp] = q;
                    ddGBuf[ddWp] = gc;
                    ddWp = (ddWp + 1) & MASK;
                    double lr = t * fsEff;
                    double lrc = lr < 4.0 ? 4.0 : (lr > NCAP ? NCAP : lr);
                    ddHeld = hermite(ddBuf, MASK, ddWp, lrc);
                    ddHeldG = hermite(ddGBuf, MASK, ddWp, lrc);
                }
                double o = ddHeld;
                for (int i = 0; i < 3; i++) { ddOut[i] += ddK7 * (o - ddOut[i]); o = ddOut[i]; }
                double wet = de.run(o / (ddHeldG < 1e-3 ? 1e-3 : ddHeldG));
                ddFbSig = loopGain(b, wet, t) * wet;
                return mixDry(c) * x + mixWet(c) * wet;
            }
        }
    }
    //[/user-code-and-variables]
}

 