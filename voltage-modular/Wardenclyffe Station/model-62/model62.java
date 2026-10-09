package com.insectlabs.model62;


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


public class model62 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public model62( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "Model 62 - Wire Player Recorder", ModuleType.ModuleType_Utility, 6.4 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "43d49cb375ab4a3488a0f4e86e3cd411" );
    }

void InitializeControls()
{

        wireframeSpoolImage = new VoltageImage( "wireframeSpoolImage", "Wireframe Spool Artwork", this, false );
        AddComponent( wireframeSpoolImage );
        wireframeSpoolImage.SetWantsMouseNotifications( false );
        wireframeSpoolImage.SetPosition( 218, 266 );
        wireframeSpoolImage.SetSize( 61, 61 );
        wireframeSpoolImage.SetCurrentImage( "image image(2).svg" );

        modelNumberLabel = new VoltageLabel( "modelNumberLabel", "Model Number", this, "model 62" );
        AddComponent( modelNumberLabel );
        modelNumberLabel.SetWantsMouseNotifications( false );
        modelNumberLabel.SetPosition( 260, 335 );
        modelNumberLabel.SetSize( 197, 13 );
        modelNumberLabel.SetEditable( false, false );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modelNumberLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        modelNumberLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        modelNumberLabel.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        modelNumberLabel.SetBorderSize( 0 );
        modelNumberLabel.SetMultiLineEdit( false );
        modelNumberLabel.SetIsNumberEditor( false );
        modelNumberLabel.SetNumberEditorRange( 0, 100 );
        modelNumberLabel.SetNumberEditorInterval( 1 );
        modelNumberLabel.SetNumberEditorUsesMouseWheel( false );
        modelNumberLabel.SetHasCustomTextHoverColor( false );
        modelNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modelNumberLabel.SetFont( "Courier New", 13, true, false );

        wireBreakJack = new VoltageAudioJack( "wireBreakJack", "Break Alarm", this, JackType.JackType_AudioOutput );
        AddComponent( wireBreakJack );
        wireBreakJack.SetWantsMouseNotifications( false );
        wireBreakJack.SetPosition( 26, 140 );
        wireBreakJack.SetSize( 25, 25 );
        wireBreakJack.SetSkin( "Mini Jack 25px" );

        manufacturerMarkLabel = new VoltageLabel( "manufacturerMarkLabel", "Manufacturer Mark", this, "iL" );
        AddComponent( manufacturerMarkLabel );
        manufacturerMarkLabel.SetWantsMouseNotifications( false );
        manufacturerMarkLabel.SetPosition( 4, 336 );
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

        signalInputLabel = new VoltageLabel( "signalInputLabel", "Signal Input", this, "SIGNAL IN" );
        AddComponent( signalInputLabel );
        signalInputLabel.SetWantsMouseNotifications( false );
        signalInputLabel.SetPosition( 15, 280 );
        signalInputLabel.SetSize( 46, 20 );
        signalInputLabel.SetEditable( false, false );
        signalInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        signalInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        signalInputLabel.SetColor( new Color( 232, 232, 232, 147 ) );
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
        signalInputLabel.SetFont( "Arial Black", 8, true, false );

        powerSwitch = new VoltageSwitch( "powerSwitch", "Power Switch", this, 0 );
        AddComponent( powerSwitch );
        powerSwitch.SetWantsMouseNotifications( false );
        powerSwitch.SetPosition( 405, 45 );
        powerSwitch.SetSize( 25, 40 );
        powerSwitch.SetSkin( "2-State Mg Rocker 2 OffOn Vert" );

        gainLabel = new VoltageLabel( "gainLabel", "Gain", this, "GAIN" );
        AddComponent( gainLabel );
        gainLabel.SetWantsMouseNotifications( false );
        gainLabel.SetPosition( 65, 280 );
        gainLabel.SetSize( 45, 20 );
        gainLabel.SetEditable( false, false );
        gainLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        gainLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        gainLabel.SetColor( new Color( 232, 232, 232, 147 ) );
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
        gainLabel.SetFont( "Arial Black", 8, true, false );

        audioInputJack = new VoltageAudioJack( "audioInputJack", "Signal In", this, JackType.JackType_AudioInput );
        AddComponent( audioInputJack );
        audioInputJack.SetWantsMouseNotifications( false );
        audioInputJack.SetPosition( 20, 250 );
        audioInputJack.SetSize( 37, 37 );
        audioInputJack.SetSkin( "Dark Jack Straight" );

        playStopButton = new VoltageButton( "playStopButton", "Play Stop Button", this );
        AddComponent( playStopButton );
        playStopButton.SetWantsMouseNotifications( false );
        playStopButton.SetPosition( 165, 205 );
        playStopButton.SetSize( 34, 34 );
        playStopButton.SetSkin( "2500 Square Big On-On" );
        playStopButton.ShowOverlay( true );
        playStopButton.SetOverlayText( "PLAY" );
        playStopButton.SetOverlayTextFont( "Arial Black", 10, false, false );
        playStopButton.SetOverlayTextColor( new Color( 35, 147, 35 ) );
        playStopButton.SetOverlayArea( 0, 0, 0, 0 );
        playStopButton.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        playStopButton.SetAutoRepeat( false );

        playStopGateJack = new VoltageAudioJack( "playStopGateJack", "Play Stop Gate", this, JackType.JackType_AudioInput );
        AddComponent( playStopGateJack );
        playStopGateJack.SetWantsMouseNotifications( false );
        playStopGateJack.SetPosition( 165, 250 );
        playStopGateJack.SetSize( 37, 37 );
        playStopGateJack.SetSkin( "Dark Jack Straight" );

        recordButton = new VoltageButton( "recordButton", "Record Button", this );
        AddComponent( recordButton );
        recordButton.SetWantsMouseNotifications( false );
        recordButton.SetPosition( 115, 205 );
        recordButton.SetSize( 34, 34 );
        recordButton.SetSkin( "2500 Square Big On-On" );
        recordButton.ShowOverlay( true );
        recordButton.SetOverlayText( "REC" );
        recordButton.SetOverlayTextFont( "Arial Black", 10, false, false );
        recordButton.SetOverlayTextColor( new Color( 85, 0, 0 ) );
        recordButton.SetOverlayArea( 0, 0, 0, 0 );
        recordButton.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        recordButton.SetAutoRepeat( false );

        recordGateJack = new VoltageAudioJack( "recordGateJack", "Record Gate", this, JackType.JackType_AudioInput );
        AddComponent( recordGateJack );
        recordGateJack.SetWantsMouseNotifications( false );
        recordGateJack.SetPosition( 115, 250 );
        recordGateJack.SetSize( 37, 37 );
        recordGateJack.SetSkin( "Dark Jack Straight" );

        elasticModeLabel = new VoltageLabel( "elasticModeLabel", "Elastic Mode Mark", this, "E" );
        AddComponent( elasticModeLabel );
        elasticModeLabel.SetWantsMouseNotifications( false );
        elasticModeLabel.SetPosition( 95, 230 );
        elasticModeLabel.SetSize( 12, 12 );
        elasticModeLabel.SetEditable( false, false );
        elasticModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        elasticModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        elasticModeLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        elasticModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        elasticModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        elasticModeLabel.SetBorderSize( 1 );
        elasticModeLabel.SetMultiLineEdit( false );
        elasticModeLabel.SetIsNumberEditor( false );
        elasticModeLabel.SetNumberEditorRange( 0, 100 );
        elasticModeLabel.SetNumberEditorInterval( 1 );
        elasticModeLabel.SetNumberEditorUsesMouseWheel( false );
        elasticModeLabel.SetHasCustomTextHoverColor( false );
        elasticModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        elasticModeLabel.SetFont( "Arial Black", 9, true, false );

        lockedModeLabel = new VoltageLabel( "lockedModeLabel", "Locked Mode Mark", this, "L" );
        AddComponent( lockedModeLabel );
        lockedModeLabel.SetWantsMouseNotifications( false );
        lockedModeLabel.SetPosition( 95, 200 );
        lockedModeLabel.SetSize( 12, 12 );
        lockedModeLabel.SetEditable( false, false );
        lockedModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lockedModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lockedModeLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        lockedModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lockedModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lockedModeLabel.SetBorderSize( 1 );
        lockedModeLabel.SetMultiLineEdit( false );
        lockedModeLabel.SetIsNumberEditor( false );
        lockedModeLabel.SetNumberEditorRange( 0, 100 );
        lockedModeLabel.SetNumberEditorInterval( 1 );
        lockedModeLabel.SetNumberEditorUsesMouseWheel( false );
        lockedModeLabel.SetHasCustomTextHoverColor( false );
        lockedModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lockedModeLabel.SetFont( "Arial Black", 9, true, false );

        normalSpeedRangeLabel = new VoltageLabel( "normalSpeedRangeLabel", "Normal Speed Range Mark", this, "x2" );
        AddComponent( normalSpeedRangeLabel );
        normalSpeedRangeLabel.SetWantsMouseNotifications( false );
        normalSpeedRangeLabel.SetPosition( 285, 195 );
        normalSpeedRangeLabel.SetSize( 12, 12 );
        normalSpeedRangeLabel.SetEditable( false, false );
        normalSpeedRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        normalSpeedRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        normalSpeedRangeLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        normalSpeedRangeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        normalSpeedRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        normalSpeedRangeLabel.SetBorderSize( 1 );
        normalSpeedRangeLabel.SetMultiLineEdit( false );
        normalSpeedRangeLabel.SetIsNumberEditor( false );
        normalSpeedRangeLabel.SetNumberEditorRange( 0, 100 );
        normalSpeedRangeLabel.SetNumberEditorInterval( 1 );
        normalSpeedRangeLabel.SetNumberEditorUsesMouseWheel( false );
        normalSpeedRangeLabel.SetHasCustomTextHoverColor( false );
        normalSpeedRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        normalSpeedRangeLabel.SetFont( "Arial Black", 9, true, false );

        extremeSpeedRangeLabel = new VoltageLabel( "extremeSpeedRangeLabel", "Extreme Speed Range Mark", this, "x4" );
        AddComponent( extremeSpeedRangeLabel );
        extremeSpeedRangeLabel.SetWantsMouseNotifications( false );
        extremeSpeedRangeLabel.SetPosition( 285, 230 );
        extremeSpeedRangeLabel.SetSize( 12, 12 );
        extremeSpeedRangeLabel.SetEditable( false, false );
        extremeSpeedRangeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        extremeSpeedRangeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        extremeSpeedRangeLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        extremeSpeedRangeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        extremeSpeedRangeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        extremeSpeedRangeLabel.SetBorderSize( 1 );
        extremeSpeedRangeLabel.SetMultiLineEdit( false );
        extremeSpeedRangeLabel.SetIsNumberEditor( false );
        extremeSpeedRangeLabel.SetNumberEditorRange( 0, 100 );
        extremeSpeedRangeLabel.SetNumberEditorInterval( 1 );
        extremeSpeedRangeLabel.SetNumberEditorUsesMouseWheel( false );
        extremeSpeedRangeLabel.SetHasCustomTextHoverColor( false );
        extremeSpeedRangeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        extremeSpeedRangeLabel.SetFont( "Arial Black", 9, true, false );

        recordModeSwitch = new VoltageSwitch( "recordModeSwitch", "Recording Mode", this, 1 );
        AddComponent( recordModeSwitch );
        recordModeSwitch.SetWantsMouseNotifications( false );
        recordModeSwitch.SetPosition( 95, 210 );
        recordModeSwitch.SetSize( 12, 21 );
        recordModeSwitch.SetSkin( "2-State Slide Black" );

        spoolLengthPhiLabel = new VoltageLabel( "spoolLengthPhiLabel", "1.618 Second Spool Mark", this, "φ" );
        AddComponent( spoolLengthPhiLabel );
        spoolLengthPhiLabel.SetWantsMouseNotifications( false );
        spoolLengthPhiLabel.SetPosition( 12, 82 );
        spoolLengthPhiLabel.SetSize( 12, 12 );
        spoolLengthPhiLabel.SetEditable( false, false );
        spoolLengthPhiLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spoolLengthPhiLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spoolLengthPhiLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        spoolLengthPhiLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        spoolLengthPhiLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        spoolLengthPhiLabel.SetBorderSize( 1 );
        spoolLengthPhiLabel.SetMultiLineEdit( false );
        spoolLengthPhiLabel.SetIsNumberEditor( false );
        spoolLengthPhiLabel.SetNumberEditorRange( 0, 100 );
        spoolLengthPhiLabel.SetNumberEditorInterval( 1 );
        spoolLengthPhiLabel.SetNumberEditorUsesMouseWheel( false );
        spoolLengthPhiLabel.SetHasCustomTextHoverColor( false );
        spoolLengthPhiLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spoolLengthPhiLabel.SetFont( "Arial", 9, true, false );

        spoolLengthOneLabel = new VoltageLabel( "spoolLengthOneLabel", "One Second Spool Mark", this, "1" );
        AddComponent( spoolLengthOneLabel );
        spoolLengthOneLabel.SetWantsMouseNotifications( false );
        spoolLengthOneLabel.SetPosition( 7, 97 );
        spoolLengthOneLabel.SetSize( 12, 12 );
        spoolLengthOneLabel.SetEditable( false, false );
        spoolLengthOneLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spoolLengthOneLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spoolLengthOneLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        spoolLengthOneLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        spoolLengthOneLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        spoolLengthOneLabel.SetBorderSize( 1 );
        spoolLengthOneLabel.SetMultiLineEdit( false );
        spoolLengthOneLabel.SetIsNumberEditor( false );
        spoolLengthOneLabel.SetNumberEditorRange( 0, 100 );
        spoolLengthOneLabel.SetNumberEditorInterval( 1 );
        spoolLengthOneLabel.SetNumberEditorUsesMouseWheel( false );
        spoolLengthOneLabel.SetHasCustomTextHoverColor( false );
        spoolLengthOneLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spoolLengthOneLabel.SetFont( "Arial", 9, true, false );

        spoolLengthFortyEightLabel = new VoltageLabel( "spoolLengthFortyEightLabel", "Forty-Eight Second Spool Mark", this, "48" );
        AddComponent( spoolLengthFortyEightLabel );
        spoolLengthFortyEightLabel.SetWantsMouseNotifications( false );
        spoolLengthFortyEightLabel.SetPosition( 62, 97 );
        spoolLengthFortyEightLabel.SetSize( 12, 12 );
        spoolLengthFortyEightLabel.SetEditable( false, false );
        spoolLengthFortyEightLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spoolLengthFortyEightLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spoolLengthFortyEightLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        spoolLengthFortyEightLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        spoolLengthFortyEightLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        spoolLengthFortyEightLabel.SetBorderSize( 1 );
        spoolLengthFortyEightLabel.SetMultiLineEdit( false );
        spoolLengthFortyEightLabel.SetIsNumberEditor( false );
        spoolLengthFortyEightLabel.SetNumberEditorRange( 0, 100 );
        spoolLengthFortyEightLabel.SetNumberEditorInterval( 1 );
        spoolLengthFortyEightLabel.SetNumberEditorUsesMouseWheel( false );
        spoolLengthFortyEightLabel.SetHasCustomTextHoverColor( false );
        spoolLengthFortyEightLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spoolLengthFortyEightLabel.SetFont( "Arial", 9, true, false );

        spoolLengthSixteenLabel = new VoltageLabel( "spoolLengthSixteenLabel", "Sixteen Second Spool Mark", this, "16" );
        AddComponent( spoolLengthSixteenLabel );
        spoolLengthSixteenLabel.SetWantsMouseNotifications( false );
        spoolLengthSixteenLabel.SetPosition( 54, 82 );
        spoolLengthSixteenLabel.SetSize( 12, 12 );
        spoolLengthSixteenLabel.SetEditable( false, false );
        spoolLengthSixteenLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spoolLengthSixteenLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spoolLengthSixteenLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        spoolLengthSixteenLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        spoolLengthSixteenLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        spoolLengthSixteenLabel.SetBorderSize( 1 );
        spoolLengthSixteenLabel.SetMultiLineEdit( false );
        spoolLengthSixteenLabel.SetIsNumberEditor( false );
        spoolLengthSixteenLabel.SetNumberEditorRange( 0, 100 );
        spoolLengthSixteenLabel.SetNumberEditorInterval( 1 );
        spoolLengthSixteenLabel.SetNumberEditorUsesMouseWheel( false );
        spoolLengthSixteenLabel.SetHasCustomTextHoverColor( false );
        spoolLengthSixteenLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spoolLengthSixteenLabel.SetFont( "Arial", 9, true, false );

        spoolLengthSevenLabel = new VoltageLabel( "spoolLengthSevenLabel", "Seven Second Spool Mark", this, "7" );
        AddComponent( spoolLengthSevenLabel );
        spoolLengthSevenLabel.SetWantsMouseNotifications( false );
        spoolLengthSevenLabel.SetPosition( 42, 74 );
        spoolLengthSevenLabel.SetSize( 12, 12 );
        spoolLengthSevenLabel.SetEditable( false, false );
        spoolLengthSevenLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spoolLengthSevenLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spoolLengthSevenLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        spoolLengthSevenLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        spoolLengthSevenLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        spoolLengthSevenLabel.SetBorderSize( 1 );
        spoolLengthSevenLabel.SetMultiLineEdit( false );
        spoolLengthSevenLabel.SetIsNumberEditor( false );
        spoolLengthSevenLabel.SetNumberEditorRange( 0, 100 );
        spoolLengthSevenLabel.SetNumberEditorInterval( 1 );
        spoolLengthSevenLabel.SetNumberEditorUsesMouseWheel( false );
        spoolLengthSevenLabel.SetHasCustomTextHoverColor( false );
        spoolLengthSevenLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spoolLengthSevenLabel.SetFont( "Arial", 9, true, false );

        spoolLengthFourLabel = new VoltageLabel( "spoolLengthFourLabel", "Four Second Spool Mark", this, "4" );
        AddComponent( spoolLengthFourLabel );
        spoolLengthFourLabel.SetWantsMouseNotifications( false );
        spoolLengthFourLabel.SetPosition( 26, 74 );
        spoolLengthFourLabel.SetSize( 12, 12 );
        spoolLengthFourLabel.SetEditable( false, false );
        spoolLengthFourLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spoolLengthFourLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spoolLengthFourLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        spoolLengthFourLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        spoolLengthFourLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        spoolLengthFourLabel.SetBorderSize( 1 );
        spoolLengthFourLabel.SetMultiLineEdit( false );
        spoolLengthFourLabel.SetIsNumberEditor( false );
        spoolLengthFourLabel.SetNumberEditorRange( 0, 100 );
        spoolLengthFourLabel.SetNumberEditorInterval( 1 );
        spoolLengthFourLabel.SetNumberEditorUsesMouseWheel( false );
        spoolLengthFourLabel.SetHasCustomTextHoverColor( false );
        spoolLengthFourLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spoolLengthFourLabel.SetFont( "Arial", 9, true, false );

        spoolLabel = new VoltageLabel( "spoolLabel", "Spool", this, "SPOOL" );
        AddComponent( spoolLabel );
        spoolLabel.SetWantsMouseNotifications( false );
        spoolLabel.SetPosition( 19, 122 );
        spoolLabel.SetSize( 40, 20 );
        spoolLabel.SetEditable( false, false );
        spoolLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spoolLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spoolLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        spoolLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        spoolLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        spoolLabel.SetBorderSize( 1 );
        spoolLabel.SetMultiLineEdit( false );
        spoolLabel.SetIsNumberEditor( false );
        spoolLabel.SetNumberEditorRange( 0, 100 );
        spoolLabel.SetNumberEditorInterval( 1 );
        spoolLabel.SetNumberEditorUsesMouseWheel( false );
        spoolLabel.SetHasCustomTextHoverColor( false );
        spoolLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spoolLabel.SetFont( "Arial", 7, true, false );

        powerLamp = new VoltageLED( "powerLamp", "Power Indicator", this );
        AddComponent( powerLamp );
        powerLamp.SetWantsMouseNotifications( false );
        powerLamp.SetPosition( 410, 95 );
        powerLamp.SetSize( 16, 16 );
        powerLamp.SetSkin( "2500 Lamp White" );

        eyeCanvas = new VoltageCanvas( "eyeCanvas", "Magic Eye Indicator", this, 35, 35 );
        AddComponent( eyeCanvas );
        eyeCanvas.SetWantsMouseNotifications( false );
        eyeCanvas.SetHighDensity( true );
        eyeCanvas.SetPosition( 400, 135 );
        eyeCanvas.SetSize( 35, 35 );

        spoolCanvas = new VoltageCanvas( "spoolCanvas", "Spool Animation", this, 280, 135 );
        AddComponent( spoolCanvas );
        spoolCanvas.SetWantsMouseNotifications( false );
        spoolCanvas.SetHighDensity( true );
        spoolCanvas.SetPosition( 90, 40 );
        spoolCanvas.SetSize( 280, 135 );

        audioOutputJack = new VoltageAudioJack( "audioOutputJack", "Signal Out", this, JackType.JackType_AudioOutput );
        AddComponent( audioOutputJack );
        audioOutputJack.SetWantsMouseNotifications( false );
        audioOutputJack.SetPosition( 415, 250 );
        audioOutputJack.SetSize( 37, 37 );
        audioOutputJack.SetSkin( "Rotated Half" );

        monitorLabel = new VoltageLabel( "monitorLabel", "Monitor", this, "MONITOR" );
        AddComponent( monitorLabel );
        monitorLabel.SetWantsMouseNotifications( false );
        monitorLabel.SetPosition( 410, 230 );
        monitorLabel.SetSize( 46, 20 );
        monitorLabel.SetEditable( false, false );
        monitorLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        monitorLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        monitorLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        monitorLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        monitorLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        monitorLabel.SetBorderSize( 1 );
        monitorLabel.SetMultiLineEdit( false );
        monitorLabel.SetIsNumberEditor( false );
        monitorLabel.SetNumberEditorRange( 0, 100 );
        monitorLabel.SetNumberEditorInterval( 1 );
        monitorLabel.SetNumberEditorUsesMouseWheel( false );
        monitorLabel.SetHasCustomTextHoverColor( false );
        monitorLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        monitorLabel.SetFont( "Arial Black", 8, true, false );

        warningLabel = new VoltageLabel( "warningLabel", "Warning", this, "WARNING" );
        AddComponent( warningLabel );
        warningLabel.SetWantsMouseNotifications( false );
        warningLabel.SetPosition( 15, 165 );
        warningLabel.SetSize( 46, 20 );
        warningLabel.SetEditable( false, false );
        warningLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        warningLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        warningLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        warningLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        warningLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        warningLabel.SetBorderSize( 1 );
        warningLabel.SetMultiLineEdit( false );
        warningLabel.SetIsNumberEditor( false );
        warningLabel.SetNumberEditorRange( 0, 100 );
        warningLabel.SetNumberEditorInterval( 1 );
        warningLabel.SetNumberEditorUsesMouseWheel( false );
        warningLabel.SetHasCustomTextHoverColor( false );
        warningLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        warningLabel.SetFont( "Arial Black", 8, true, false );

        monitorOutputJack = new VoltageAudioJack( "monitorOutputJack", "Monitor Output", this, JackType.JackType_AudioOutput );
        AddComponent( monitorOutputJack );
        monitorOutputJack.SetWantsMouseNotifications( false );
        monitorOutputJack.SetPosition( 415, 200 );
        monitorOutputJack.SetSize( 37, 37 );
        monitorOutputJack.SetSkin( "Rotated Half" );

        outputLabel = new VoltageLabel( "outputLabel", "Output", this, "OUTPUT" );
        AddComponent( outputLabel );
        outputLabel.SetWantsMouseNotifications( false );
        outputLabel.SetPosition( 410, 280 );
        outputLabel.SetSize( 46, 20 );
        outputLabel.SetEditable( false, false );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        outputLabel.SetColor( new Color( 232, 232, 232, 147 ) );
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
        outputLabel.SetFont( "Arial Black", 8, true, false );

        outputLevelKnob = new VoltageKnob( "outputLevelKnob", "Output Level", this, 0.0, 2.0, 1.0 );
        AddComponent( outputLevelKnob );
        outputLevelKnob.SetWantsMouseNotifications( false );
        outputLevelKnob.SetPosition( 365, 250 );
        outputLevelKnob.SetSize( 35, 35 );
        outputLevelKnob.SetSkin( "Cosmo Medium" );
        outputLevelKnob.SetRange( 0.0, 2.0, 1.0, false, 0 );
        outputLevelKnob.SetKnobParams( 215, 145 );
        outputLevelKnob.DisplayValueInPercent( false );
        outputLevelKnob.SetKnobAdjustsRing( true );

        speedLabel = new VoltageLabel( "speedLabel", "Speed", this, "SPEED" );
        AddComponent( speedLabel );
        speedLabel.SetWantsMouseNotifications( false );
        speedLabel.SetPosition( 225, 255 );
        speedLabel.SetSize( 46, 20 );
        speedLabel.SetEditable( false, false );
        speedLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        speedLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        speedLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        speedLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        speedLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        speedLabel.SetBorderSize( 1 );
        speedLabel.SetMultiLineEdit( false );
        speedLabel.SetIsNumberEditor( false );
        speedLabel.SetNumberEditorRange( 0, 100 );
        speedLabel.SetNumberEditorInterval( 1 );
        speedLabel.SetNumberEditorUsesMouseWheel( false );
        speedLabel.SetHasCustomTextHoverColor( false );
        speedLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        speedLabel.SetFont( "Arial Black", 8, true, false );

        signalLossLabel = new VoltageLabel( "signalLossLabel", "Signal Loss", this, "SIGNAL LOSS" );
        AddComponent( signalLossLabel );
        signalLossLabel.SetWantsMouseNotifications( false );
        signalLossLabel.SetPosition( 310, 230 );
        signalLossLabel.SetSize( 46, 24 );
        signalLossLabel.SetEditable( false, false );
        signalLossLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        signalLossLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        signalLossLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        signalLossLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        signalLossLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        signalLossLabel.SetBorderSize( 1 );
        signalLossLabel.SetMultiLineEdit( false );
        signalLossLabel.SetIsNumberEditor( false );
        signalLossLabel.SetNumberEditorRange( 0, 100 );
        signalLossLabel.SetNumberEditorInterval( 1 );
        signalLossLabel.SetNumberEditorUsesMouseWheel( false );
        signalLossLabel.SetHasCustomTextHoverColor( false );
        signalLossLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        signalLossLabel.SetFont( "Arial Black", 8, true, false );

        amplitudeLabel = new VoltageLabel( "amplitudeLabel", "Amplitude", this, "AMPLITUDE" );
        AddComponent( amplitudeLabel );
        amplitudeLabel.SetWantsMouseNotifications( false );
        amplitudeLabel.SetPosition( 360, 280 );
        amplitudeLabel.SetSize( 46, 20 );
        amplitudeLabel.SetEditable( false, false );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        amplitudeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        amplitudeLabel.SetColor( new Color( 232, 232, 232, 147 ) );
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
        amplitudeLabel.SetFont( "Arial Black", 8, true, false );

        varispeedCvJack = new VoltageAudioJack( "varispeedCvJack", "Varispeed CV", this, JackType.JackType_AudioInput );
        AddComponent( varispeedCvJack );
        varispeedCvJack.SetWantsMouseNotifications( false );
        varispeedCvJack.SetPosition( 230, 280 );
        varispeedCvJack.SetSize( 37, 37 );
        varispeedCvJack.SetSkin( "Dark Jack Straight" );

        memoryCvJack = new VoltageAudioJack( "memoryCvJack", "Memory CV", this, JackType.JackType_AudioInput );
        AddComponent( memoryCvJack );
        memoryCvJack.SetWantsMouseNotifications( false );
        memoryCvJack.SetPosition( 315, 250 );
        memoryCvJack.SetSize( 37, 37 );
        memoryCvJack.SetSkin( "Dark Jack Straight" );

        varispeedKnob = new VoltageKnob( "varispeedKnob", "Varispeed", this, -2.0, 2.0, 1.0 );
        AddComponent( varispeedKnob );
        varispeedKnob.SetWantsMouseNotifications( false );
        varispeedKnob.SetPosition( 215, 185 );
        varispeedKnob.SetSize( 66, 66 );
        varispeedKnob.SetSkin( "Cosmo v2 Large" );
        varispeedKnob.SetRange( -2.0, 2.0, 1.0, false, 0 );
        varispeedKnob.SetKnobParams( 215, 145 );
        varispeedKnob.DisplayValueInPercent( false );
        varispeedKnob.SetKnobAdjustsRing( true );

        memoryKnob = new VoltageKnob( "memoryKnob", "Memory", this, 0.0, 1.0, .99 );
        AddComponent( memoryKnob );
        memoryKnob.SetWantsMouseNotifications( false );
        memoryKnob.SetPosition( 315, 200 );
        memoryKnob.SetSize( 35, 35 );
        memoryKnob.SetSkin( "Cosmo v2 Med" );
        memoryKnob.SetRange( 0.0, 1.0, .99, false, 0 );
        memoryKnob.SetKnobParams( 215, 145 );
        memoryKnob.DisplayValueInPercent( false );
        memoryKnob.SetKnobAdjustsRing( true );

        machineTitleLabel = new VoltageLabel( "machineTitleLabel", "Machine Title", this, "wire player – recorder" );
        AddComponent( machineTitleLabel );
        machineTitleLabel.SetWantsMouseNotifications( false );
        machineTitleLabel.SetPosition( 3, 3 );
        machineTitleLabel.SetSize( 454, 23 );
        machineTitleLabel.SetEditable( false, false );
        machineTitleLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        machineTitleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        machineTitleLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        machineTitleLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        machineTitleLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        machineTitleLabel.SetBorderSize( 4 );
        machineTitleLabel.SetMultiLineEdit( false );
        machineTitleLabel.SetIsNumberEditor( false );
        machineTitleLabel.SetNumberEditorRange( 0, 100 );
        machineTitleLabel.SetNumberEditorInterval( 1 );
        machineTitleLabel.SetNumberEditorUsesMouseWheel( false );
        machineTitleLabel.SetHasCustomTextHoverColor( false );
        machineTitleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        machineTitleLabel.SetFont( "Courier New", 13, true, false );

        spliceButton = new VoltageButton( "spliceButton", "Splice Button", this );
        AddComponent( spliceButton );
        spliceButton.SetWantsMouseNotifications( false );
        spliceButton.SetPosition( 20, 185 );
        spliceButton.SetSize( 35, 35 );
        spliceButton.SetSkin( "2500 Square Big On-On" );
        spliceButton.ShowOverlay( true );
        spliceButton.SetOverlayText( "SPLICE" );
        spliceButton.SetOverlayTextFont( "Arial Black", 8, true, false );
        spliceButton.SetOverlayTextColor( new Color( 155, 84, 0 ) );
        spliceButton.SetOverlayArea( 0, 0, 0, 0 );
        spliceButton.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        spliceButton.SetAutoRepeat( false );

        speedSwitch = new VoltageSwitch( "speedSwitch", "Speed Range", this, 1 );
        AddComponent( speedSwitch );
        speedSwitch.SetWantsMouseNotifications( false );
        speedSwitch.SetPosition( 285, 210 );
        speedSwitch.SetSize( 11, 20 );
        speedSwitch.SetSkin( "2-State Slide Black" );

        spoolSelect = new VoltageKnob( "spoolSelect", "Wire Spool Length", this, 1, 6, 1 );
        AddComponent( spoolSelect );
        spoolSelect.SetWantsMouseNotifications( false );
        spoolSelect.SetPosition( 20, 85 );
        spoolSelect.SetSize( 40, 40 );
        spoolSelect.SetSkin( "Cosmo v2 Pointer" );
        spoolSelect.SetRange( 1, 6, 1, false, 6 );
        spoolSelect.SetKnobParams( 280, 80 );
        spoolSelect.DisplayValueInPercent( false );
        spoolSelect.SetKnobAdjustsRing( true );

        inputGainKnob = new VoltageKnob( "inputGainKnob", "Input Gain", this, 0.0, 1.0, 0.5 );
        AddComponent( inputGainKnob );
        inputGainKnob.SetWantsMouseNotifications( false );
        inputGainKnob.SetPosition( 70, 250 );
        inputGainKnob.SetSize( 35, 35 );
        inputGainKnob.SetSkin( "Cosmo v2 Med" );
        inputGainKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        inputGainKnob.SetKnobParams( 215, 145 );
        inputGainKnob.DisplayValueInPercent( false );
        inputGainKnob.SetKnobAdjustsRing( true );

        burroughsMarkImage = new VoltageImage( "burroughsMarkImage", "Burroughs Logo", this, false );
        AddComponent( burroughsMarkImage );
        burroughsMarkImage.SetWantsMouseNotifications( false );
        burroughsMarkImage.SetPosition( 313, 299 );
        burroughsMarkImage.SetSize( 147, 49 );
        burroughsMarkImage.SetCurrentImage( "image image(2).png" );

        studioColophonLabel = new VoltageLabel( "studioColophonLabel", "Insect Laboratories Colophon", this, "insect laboratories pittsburgh, PA        united states & beyond" );
        AddComponent( studioColophonLabel );
        studioColophonLabel.SetWantsMouseNotifications( false );
        studioColophonLabel.SetPosition( 24, 336 );
        studioColophonLabel.SetSize( 60, 20 );
        studioColophonLabel.SetEditable( false, false );
        studioColophonLabel.SetJustificationFlags( VoltageLabel.Justification.Left );
        studioColophonLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        studioColophonLabel.SetColor( new Color( 85, 85, 85, 187 ) );
        studioColophonLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        studioColophonLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        studioColophonLabel.SetBorderSize( 1 );
        studioColophonLabel.SetMultiLineEdit( true );
        studioColophonLabel.SetIsNumberEditor( false );
        studioColophonLabel.SetNumberEditorRange( 0, 100 );
        studioColophonLabel.SetNumberEditorInterval( 1 );
        studioColophonLabel.SetNumberEditorUsesMouseWheel( false );
        studioColophonLabel.SetHasCustomTextHoverColor( false );
        studioColophonLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        studioColophonLabel.SetFont( "Arial Black", 6, true, false );
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
        wireCore = new WireCore(48000.0);
        wireCore.randomState ^= (int) moduleID;
        if (wireCore.randomState == 0) wireCore.randomState = 1;
        syncControls();
        wireCore.selectSpool(spoolTarget);
        wireCore.setPower(powerTarget);
        StartGuiUpdateTimer(33);
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
        if (wireCore == null) return false;
        switch (notification) {
            case Knob_Changed:
            case Switch_Changed:
                if (!restoring) syncControls();
                break;
            case Button_Changed:
                if (doubleValue > .5 && !restoring && !IsBypassed()) {
                    if (component == playStopButton) wireCore.togglePlay();
                    else if (component == recordButton) wireCore.toggleRecord();
                    else if (component == spliceButton) {
                        wireCore.repair();
                        spliceFlashUntilNanos = System.nanoTime() + 1_200_000_000L;
                    }
                }
                break;
            case GUI_Update_Timer:
                drawDisplays();
                break;
            case Preset_Loading_Start:
            case Variation_Loading_Start:
                restoring = true;
                break;
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                finishRestore();
                break;
            case Reset:
                // Reset controls and stop the transport; a new spool alone erases audio.
                finishRestore();
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
        WireCore core = wireCore;
        if (core == null || restoring) {
            audioOutputJack.SetValue(0);
            monitorOutputJack.SetValue(0);
            wireBreakJack.SetValue(0);
            return;
        }
        playGateHigh = readInput(playStopGateJack) >= (playGateHigh ? 1.0 : 2.5);
        recordGateHigh = readInput(recordGateJack) >= (recordGateHigh ? 1.0 : 2.5);
        if (resumePending) {
            core.resume(playGateHigh, recordGateHigh);
            resumePending = false;
        }
        core.setPower(powerTarget);
        core.selectSpool(spoolTarget);
        double memory = memoryCvJack.IsConnected() ? readInput(memoryCvJack) / 5 : memoryTarget;
        core.tick(readInput(audioInputJack), speedTarget, readInput(varispeedCvJack),
                maximumSpeed, memory, gainTarget, amplitudeTarget,
                playGateHigh, recordGateHigh, elasticTarget,
                audioInputJack.IsConnected());
        audioOutputJack.SetValue(core.output);
        monitorOutputJack.SetValue(core.monitor);
        wireBreakJack.SetValue(core.alarm);
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
        double signal = readInput(audioInputJack);
        audioOutputJack.SetValue(signal);
        monitorOutputJack.SetValue(signal);
        wireBreakJack.SetValue(0);
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
        if (component == varispeedKnob) return String.format(java.util.Locale.ROOT, "Varispeed: %+.3fx", varispeedKnob.GetValue() * maximumSpeed / 2);
        if (component == memoryKnob) return String.format(java.util.Locale.ROOT, "Memory: %.1f%% retained per write pass", memoryKnob.GetValue() * 100);
        if (component == inputGainKnob) return String.format(java.util.Locale.ROOT, "Input Gain: %.1f%%", gainTarget * 100);
        if (component == outputLevelKnob) return String.format(java.util.Locale.ROOT, "Output Level: %.1f%%", outputLevelKnob.GetValue() * 100);
        if (component == spoolSelect) return "Spool: " + WireCore.LENGTHS[(int) WireCore.limit(Math.round(spoolSelect.GetValue()) - 1, 0, 5)] + " s at 1x; changing clears recording; changing while moving breaks the wire";
        if (component == speedSwitch) return maximumSpeed == 2 ? "Normal: +/-2x" : "Extreme: +/-4x";
        if (component == recordModeSwitch) return elasticTarget ? "Elastic: recording follows playback" : "Locked: recording moves forward at 1x";
        if (component == monitorOutputJack) return "Live input after GAIN; no wire treatment or playback";
        if (component == audioOutputJack) return "Wire playback; follows output level";
        if (component == audioInputJack) return "Mono signal to monitor and recording head";
        if (component == wireBreakJack) return "Break alarm: +5 V held until repair; silent when power is off";
        if (component == spliceButton) return "Repair broken wire; leaves a persistent splice and transport stopped";
        if (component == playStopGateJack) return "Rising edge toggles PLAY/STOP (2.5 V high, 1 V low)";
        if (component == recordGateJack) return "High enables recording; requires powered PLAY";
        if (component == varispeedCvJack) return "Additive speed CV: +/-5 V spans selected speed range";
        if (component == memoryCvJack) return "0-5 V replaces MEMORY knob when patched";
        if (component == playStopButton) return "Toggle motor PLAY/STOP with inertia";
        if (component == recordButton) return "Toggle recording arm; actual recording begins only during powered PLAY";
        if (component == powerSwitch) return "Power off coasts to a stop; recording survives";
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
        if (component == varispeedKnob) newValue = WireCore.limit(newValue * 2 / maximumSpeed, -2, 2);
        else if (component == memoryKnob) newValue = WireCore.limit(newValue / 100, 0, 1);
        else if (component == outputLevelKnob) newValue = WireCore.limit(newValue / 100, 0, 2);
        else if (component == inputGainKnob) {
            double g = WireCore.limit(newValue / 100, .5, 2.5);
            newValue = g <= 1 ? g - .5 : .5 + (g - 1) / 3;
        }
        else if (component == spoolSelect) {
            int best = 0;
            for (int i = 1; i < 6; i++) if (Math.abs(WireCore.LENGTHS[i] - newValue) < Math.abs(WireCore.LENGTHS[best] - newValue)) best = i;
            newValue = best + 1;
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
        WireCore core = wireCore;
        return core == null ? null : core.save();
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
        if (stateInfo == null || stateInfo.length == 0) return;
        try {
            pendingRestore = WireCore.load(stateInfo, 48000.0);
            if (!restoring) finishRestore();
        } catch (IllegalArgumentException ex) {
            LogError("Model 62: recording not restored: " + ex.getMessage());
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
    private VoltageLabel studioColophonLabel;
    private VoltageImage burroughsMarkImage;
    private VoltageKnob inputGainKnob;
    private VoltageKnob spoolSelect;
    private VoltageSwitch speedSwitch;
    private VoltageButton spliceButton;
    private VoltageLabel machineTitleLabel;
    private VoltageKnob memoryKnob;
    private VoltageKnob varispeedKnob;
    private VoltageAudioJack memoryCvJack;
    private VoltageAudioJack varispeedCvJack;
    private VoltageLabel amplitudeLabel;
    private VoltageLabel signalLossLabel;
    private VoltageLabel speedLabel;
    private VoltageKnob outputLevelKnob;
    private VoltageLabel outputLabel;
    private VoltageAudioJack monitorOutputJack;
    private VoltageLabel warningLabel;
    private VoltageLabel monitorLabel;
    private VoltageAudioJack audioOutputJack;
    private VoltageCanvas spoolCanvas;
    private VoltageCanvas eyeCanvas;
    private VoltageLED powerLamp;
    private VoltageLabel spoolLabel;
    private VoltageLabel spoolLengthFourLabel;
    private VoltageLabel spoolLengthSevenLabel;
    private VoltageLabel spoolLengthSixteenLabel;
    private VoltageLabel spoolLengthFortyEightLabel;
    private VoltageLabel spoolLengthOneLabel;
    private VoltageLabel spoolLengthPhiLabel;
    private VoltageSwitch recordModeSwitch;
    private VoltageLabel extremeSpeedRangeLabel;
    private VoltageLabel normalSpeedRangeLabel;
    private VoltageLabel lockedModeLabel;
    private VoltageLabel elasticModeLabel;
    private VoltageAudioJack recordGateJack;
    private VoltageButton recordButton;
    private VoltageAudioJack playStopGateJack;
    private VoltageButton playStopButton;
    private VoltageAudioJack audioInputJack;
    private VoltageLabel gainLabel;
    private VoltageSwitch powerSwitch;
    private VoltageLabel signalInputLabel;
    private VoltageLabel manufacturerMarkLabel;
    private VoltageAudioJack wireBreakJack;
    private VoltageLabel modelNumberLabel;
    private VoltageImage wireframeSpoolImage;


    //[user-code-and-variables]    Add your own variables and functions here
    private volatile WireCore wireCore;
    private volatile WireCore pendingRestore;
    private volatile boolean restoring;
    private boolean resumePending;
    private boolean playGateHigh;
    private boolean recordGateHigh;
    private volatile double speedTarget = 1;
    private volatile double maximumSpeed = 2;
    private volatile double memoryTarget = .5;
    private volatile double gainTarget = 1;
    private volatile double amplitudeTarget = 1;
    private volatile boolean powerTarget;
    private volatile boolean elasticTarget;
    private volatile int spoolTarget = 1;
    private volatile long spliceFlashUntilNanos;
    private boolean lastPlayLight;
    private boolean lastRecordLight;
    private boolean lastSpliceLight;

    private void syncControls() {
        maximumSpeed = speedSwitch.GetValue() > .5 ? 2 : 4;
        speedTarget = varispeedKnob.GetValue() * maximumSpeed / 2;
        memoryTarget = memoryKnob.GetValue();
        double g = inputGainKnob.GetValue();
        gainTarget = g <= .5 ? .5 + g : 1 + (g - .5) * 3;
        amplitudeTarget = outputLevelKnob.GetValue();
        powerTarget = powerSwitch.GetValue() > .5;
        elasticTarget = recordModeSwitch.GetValue() > .5;
        spoolTarget = Math.max(0, Math.min(5, (int) Math.round(spoolSelect.GetValue()) - 1));
    }

    private static double readInput(VoltageAudioJack jack) {
        if (!jack.IsConnected()) return 0;

        double value = jack.GetValue();
        if (!Double.isFinite(value)) return 0;

        return WireCore.limit(value, -1.0e6, 1.0e6);
    }

    private void finishRestore() {
        WireCore restored = pendingRestore;
        if (restored != null) {
            wireCore = restored;
            pendingRestore = null;
            spoolSelect.SetValueNoNotification(restored.spool + 1, false);
        }
        syncControls();
        WireCore core = wireCore;
        if (core != null) {
            synchronized (core) {
                core.playing = false;
                core.recordLatched = false;
                core.speed = 0;
                core.resume(false, false);
            }
        }
        restoring = false;
        resumePending = true;
    }

    private void drawDisplays() {
        WireCore core = wireCore;
        if (core == null) return;
        double rotation, eyeLevel;
        boolean power, broken, play, record, recordArmed;
        long repairs;
        synchronized (core) {
            rotation = core.displayRotation * Math.PI * 2;
            eyeLevel = core.eye;
            power = core.powered;
            broken = core.broken;
            play = core.playing || Math.abs(core.speed) > .01;
            record = core.recording;
            recordArmed = core.recordLatched || core.recording
                    || (core.gatesArmed && core.recordHigh);
            repairs = core.repairs;
        }
        boolean active = power && !IsBypassed();
        powerLamp.SetValue(active ? 1 : 0);
        boolean playLit = active && play;
        boolean recordLit = active && recordArmed;
        boolean spliceFlash = active && System.nanoTime() < spliceFlashUntilNanos
                && ((System.nanoTime() / 160_000_000L & 1L) == 0);
        playStopButton.SetValueNoNotification(playLit ? 1 : 0, false);
        recordButton.SetValueNoNotification(recordLit ? 1 : 0, false);
        spliceButton.SetValueNoNotification(spliceFlash ? 1 : 0, false);
        if (playLit != lastPlayLight) {
            playStopButton.SetOverlayTextColor(playLit
                    ? new Color( 165, 255, 175 ) : new Color( 0, 0, 0 ));
            lastPlayLight = playLit;
        }
        if (recordLit != lastRecordLight) {
            recordButton.SetOverlayTextColor(recordLit
                    ? new Color( 255, 85, 65 ) : new Color( 85, 0, 0 ));
            lastRecordLight = recordLit;
        }
        if (spliceFlash != lastSpliceLight) {
            spliceButton.SetOverlayTextColor(spliceFlash
                    ? new Color( 255, 245, 170 ) : new Color( 150, 90, 0 ));
            lastSpliceLight = spliceFlash;
        }
        java.awt.Graphics2D g = spoolCanvas.GetGraphics();
        if (g != null) {
            try {
                g.setTransform(new java.awt.geom.AffineTransform());
                g.setColor(new Color(35, 31, 32));
                g.fillRect(0, 0, spoolCanvas.GetBitmapWidth(), spoolCanvas.GetBitmapHeight());
                g.scale(spoolCanvas.GetBitmapWidth() / 301.0, spoolCanvas.GetBitmapHeight() / 150.0);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                drawSpool(g, 62, 65, rotation);
                drawSpool(g, 239, 65, rotation);
                g.setColor(new Color(185, 185, 180));
                int y = 65 + (int) (12 * Math.sin(rotation * .23));
                g.fillRoundRect(139, y - 8, 22, 16, 4, 4);
                if (broken) {
                    g.drawLine(62, 65, 110, 96);
                    g.drawLine(239, 65, 198, 100);
                    g.setColor(new Color(220, 60, 35));
                } else {
                    g.drawLine(62, 65, 149, y);
                    g.drawLine(151, y, 239, 65);
                    g.setColor(new Color(205, 200, 182));
                }
                g.setFont(new Font("Monospaced", Font.PLAIN, 11));
                String status = broken ? "WIRE BROKEN - SPLICE" : record ? "RECORDING" : play ? "PLAY" : "STOPPED";
                g.drawString(status, 10, 126);
                g.drawString("SPLICES " + repairs, 10, 142);
            } finally { g.dispose(); }
            spoolCanvas.Invalidate();
        }
        g = eyeCanvas.GetGraphics();
        if (g != null) {
            try {
                g.setTransform(new java.awt.geom.AffineTransform());
                int w = eyeCanvas.GetBitmapWidth(), h = eyeCanvas.GetBitmapHeight();
                g.setColor(new Color(22, 27, 22)); g.fillRect(0, 0, w, h);
                g.setColor(power && !IsBypassed() ? new Color(70, 185, 82) : new Color(35, 55, 35));
                g.fillOval(2, 2, w - 4, h - 4);
                int angle = (int) (110 * (1 - Math.min(1, eyeLevel / 2.0)));
                g.setColor(new Color(10, 25, 12));
                g.fillArc(2, 2, w - 4, h - 4, 270 - angle / 2, angle);
                g.setColor(eyeLevel > .7 && power && !IsBypassed()
                        ? new Color(200, 255, 210)
                        : new Color(40, 70, 45));
                g.fillOval(w / 2 - 5, h / 2 - 5, 10, 10);
            } finally { g.dispose(); }
            eyeCanvas.Invalidate();
        }
    }

    private static void drawSpool(java.awt.Graphics2D g, int x, int y, double phase) {
        g.setColor(new Color(112, 116, 116)); g.fillOval(x - 42, y - 42, 84, 84);
        g.setColor(new Color(170, 171, 165)); g.fillOval(x - 34, y - 34, 68, 68);
        g.setColor(new Color(40, 42, 41));
        g.setStroke(new BasicStroke(5));
        for (int i = 0; i < 3; i++) {
            double a = phase + i * Math.PI * 2 / 3;
            g.drawLine(x, y, x + (int) (29 * Math.cos(a)), y + (int) (29 * Math.sin(a)));
        }
        g.fillOval(x - 5, y - 5, 10, 10);
        g.setStroke(new BasicStroke(1));
    }

    /** Model 62 v1.0.0: wire-domain storage, host-domain transport and filtering.
     * No allocation in tick(). Native VM integration runs at its fixed 48 kHz rate.
     * A page copy-on-write snapshot lets patch saving retain a coherent spool
     * without holding the audio lock for an entire multi-megabyte copy.
     */
    static final class WireCore {
        static final int WIRE_RATE = 24000;
        static final double[] LENGTHS = {1.0, 1.618, 4.0, 7.0, 16.0, 48.0};
        static final int CAPACITY = WIRE_RATE * 48;
        static final int PAGE = 256;
        static final int PAGES = (CAPACITY + PAGE - 1) / PAGE;
        static final double HEAD_DISTANCE = WIRE_RATE * (33.0 / 609.6);
        static final double STORAGE_SCALE = 32767.0 / 16.0;
        static final int TAPS = 24;
        static final int PHASES = 256;
        static final int BANDS = 16;
        static final double[][] RESAMPLE = makeResampler();
        final short[] wire = new short[CAPACITY];
        final byte[] scars = new byte[CAPACITY];
        final int[] pageEpoch = new int[PAGES];
        final double sampleRate;
        final double dt;
        final double motorAlpha;
        final double smoothAlpha;
        final Biquad inputLow1 = new Biquad();
        final Biquad inputLow2 = new Biquad();
        final Biquad playbackLow = new Biquad();
        private final Object saveLock = new Object();
        private Snapshot snapshot;
        int epoch = 1;
        int spool = 1;
        int length = (int) Math.round(LENGTHS[1] * WIRE_RATE);
        long repairs;
        double readPosition;
        double writePosition = HEAD_DISTANCE;
        double breakPosition;
        double speed;
        double wowPhase;
        double flutterPhase;
        double drift;
        double driftTarget;
        int driftCountdown;
        int randomState = 0x7134ac9d;
        int filterCountdown;
        double previousInput;
        double oldLow;
        double previousSum;
        double recordFade;
        final double recordFadeAlpha;
        double outputFade;
        double monitorFade;
        double gain = 1;
        double amplitude = 1;
        double hpInput;
        double hpOutput;
        double eye;
        double stress;
        double runAge;
        double breakThreshold = 1.5;
        boolean idle;
        boolean powered;
        boolean playing;
        boolean recordLatched;
        boolean broken;
        boolean elastic;
        boolean playHigh;
        boolean recordHigh;
        boolean gatesArmed = true;
        boolean recording;
        double output;
        double monitor;
        double alarm;
        double displayRotation;

        boolean wobbleEnabled = true;

        WireCore(double rate) {
            if (!Double.isFinite(rate) || rate < 24000 || rate > 192000) {
                throw new IllegalArgumentException("Unsupported processing rate");
            }
            sampleRate = rate;
            dt = 1.0 / rate;
            recordFadeAlpha = 1 - Math.exp(-dt / .004);
            motorAlpha = 1 - Math.exp(-dt / .12);
            smoothAlpha = 1 - Math.exp(-dt / .008);
            inputLow1.lowpass(8500, .5411961, rate);
            inputLow2.lowpass(8500, 1.306563, rate);
            playbackLow.lowpass(6200, .70710678, rate);
        }

        static double limit(double x, double lo, double hi) {
            return Double.isFinite(x) ? Math.max(lo, Math.min(hi, x)) : 0;
        }

        synchronized void togglePlay() {
            if (powered && !broken) {
                playing = !playing;
                runAge = 0;
                stress = 0;
            }
        }

        synchronized void toggleRecord() {
            if (powered && !broken) recordLatched = !recordLatched;
        }

        synchronized void setPower(boolean value) {
            if (powered == value) return;
            powered = value;
            playing = false;
            recordLatched = false;
            gatesArmed = false;
            stress = 0;
        }

        synchronized void selectSpool(int selected) {
            selected = Math.max(0, Math.min(5, selected));
            if (selected == spool) return;
            boolean moving = playing || Math.abs(speed) > .0001;
            // Lazy page invalidation avoids clearing the whole spool on an audio sample.
            epoch++;
            if (epoch == 0) { // Once per ~4 billion spool changes.
                java.util.Arrays.fill(pageEpoch, 0);
                epoch = 1;
            }
            spool = selected;
            length = (int) Math.round(LENGTHS[spool] * WIRE_RATE);
            readPosition = 0;
            writePosition = HEAD_DISTANCE;
            breakPosition = 0;
            repairs = 0;
            breakThreshold = 1.5;
            speed = 0;
            playing = false;
            recordLatched = false;
            broken = moving;
            stress = 0;
            resetFilters();
        }

        void resetFilters() {
            inputLow1.reset(); inputLow2.reset(); playbackLow.reset();
            previousInput = oldLow = previousSum = 0;
            hpInput = hpOutput = outputFade = recordFade = 0;
        }

        void snap() {
            broken = true;
            playing = false;
            recordLatched = false;
            breakPosition = readPosition;
            speed = 0;
            stress = 0;
            gatesArmed = false;
        }

        synchronized void repair() {
            if (!broken) return;
            // Spatial scar: repeats at the same location, reverses and changes
            // duration with the wire. Re-recording cannot erase a physical knot.
            int center = (int) Math.floor(breakPosition);
            for (int j = -24; j <= 24; j++) {
                int at = wrapIndex(center + j);
                preparePage(at / PAGE);
                int strength = (int) Math.round(165 * (1.0 - Math.abs(j) / 25.0));
                scars[at] = (byte) Math.min(255, (scars[at] & 255) + strength);
            }
            if (repairs < Long.MAX_VALUE) repairs++;
            breakThreshold = Math.max(.8, 1.5 / (1 + .08 * Math.log1p(repairs)));
            broken = false;
            playing = false;
            recordLatched = false;
            gatesArmed = false;
            speed = 0;
            resetFilters();
        }

        synchronized void resume(boolean pGate, boolean rGate) {
            // Do not manufacture trigger edges or resume recording from a held gate.
            playHigh = pGate;
            recordHigh = rGate;
            gatesArmed = false;
        }

        synchronized void tick(double input, double manualSpeed, double speedCv,
                double maximumSpeed, double memory, double gainTarget,
                double ampTarget, boolean pGate, boolean rGate, boolean elasticMode,
                boolean inputConnected) {
            if (!gatesArmed) {
                if (!pGate && !rGate) gatesArmed = true;
            } else if (pGate && !playHigh) {
                togglePlay();
            }
            playHigh = pGate;
            recordHigh = rGate;
            input = limit(input, -1000, 1000);
            gain += smoothAlpha * (limit(gainTarget, .5, 2.5) - gain);
            amplitude += smoothAlpha * (limit(ampTarget, 0, 2) - amplitude);
            monitorFade += smoothAlpha * ((powered ? 1 : 0) - monitorFade);
            monitor = input * gain * monitorFade;
            recordHigh = rGate;
            boolean useInputMeter = inputConnected
                    && (recordLatched || recording || (gatesArmed && rGate));
            double eyeTarget = .85 * (useInputMeter ? Math.abs(input * gain) : Math.abs(output));
            eye += (eyeTarget > eye ? .035 : .0015) * (eyeTarget - eye);
            if (speed == 0 && !playing) {
                if (!idle) resetFilters();
                idle = true;
                recording = false;
                output = 0;
                alarm = powered && broken ? 5 : 0;
                return;
            }
            idle = false;
            double demand = playing && powered && !broken
                    ? limit(manualSpeed + speedCv * maximumSpeed / 5, -maximumSpeed, maximumSpeed) : 0;
            if (playing && powered && !broken) {
                runAge += dt;
                if (runAge > .3) {
                    double excess = Math.max(0, Math.abs(demand - speed) - breakThreshold);
                    stress = stress * (1 - dt / .08) + excess * dt;
                    if (stress > .018) snap();
                }
            }
            if (broken) demand = 0;
            speed += motorAlpha * (demand - speed);
            if (Math.abs(speed) < .00001 && demand == 0) speed = 0;
            double movement = speed;
            if (speed != 0) {
                if (--driftCountdown <= 0) {
                    driftTarget = noise() * .1;
                    driftCountdown = Math.max(1, (int) (sampleRate / 20));
                }
                drift += dt * (driftTarget - drift);
                // Signed phase travel: reverse revisits the mechanical cycle backwards.
                wowPhase = wrapPhase(wowPhase + dt * speed * 1.4 * (1 + drift));
                flutterPhase = wrapPhase(flutterPhase + dt * speed * 32 * (1 + drift * .5));
                if (wobbleEnabled) movement *= 1 + .00085 * Math.sin(2 * Math.PI * wowPhase)
                        + .0017 * Math.sin(2 * Math.PI * flutterPhase);
            }
            double step = movement * WIRE_RATE * dt;
            if (elasticMode != elastic) {
                elastic = elasticMode;
                writePosition = wrap(readPosition + HEAD_DISTANCE);
                previousInput = oldLow = previousSum = 0;
            }
            double writeStep = elastic ? step : (playing && powered && !broken ? WIRE_RATE * dt : 0);
            recording = powered && playing && !broken
                    && (recordLatched || (gatesArmed && rGate)) && Math.abs(writeStep) > 1e-8;
            // Ease the record head in and out to suppress switching thumps.
            recordFade += recordFadeAlpha * ((recording ? 1 : 0) - recordFade);
            recordFade = limit(recordFade, 0, 1);
            if (--filterCountdown <= 0) {
                double writeSpeed = Math.abs(writeStep) * sampleRate / WIRE_RATE;
                double cutoff = Math.max(10, Math.min(8500, 8500 * writeSpeed));
                inputLow1.lowpass(cutoff, .5411961, sampleRate);
                inputLow2.lowpass(cutoff, 1.306563, sampleRate);
                filterCountdown = 64;
            }
            double filtered = inputLow2.process(inputLow1.process(input * gain));
            filtered = inputCharacter(filtered);
            if (recordFade > .0001) writeCrossings(writePosition, writeStep, previousInput,
                    filtered, limit(memory, 0, 1), recordFade);
            previousInput = filtered;
            writePosition = wrap(writePosition + writeStep);
            double raw = step == 0 || broken ? 0 : readBandlimited(readPosition, Math.abs(step));
            if (step != 0 && !broken) {
                double scar = scarAt(readPosition);
                raw = raw * (1 - .90 * scar) + .035 * scar
                        + .006 * scar * noise();
            }
            double shaped = playbackLow.process(raw);
            // Mild fixed head/electronics bandwidth: 55 Hz high-pass + 6.2 kHz low-pass.
            double hp = shaped - hpInput + (1 - 2 * Math.PI * 55 * dt) * hpOutput;
            hpInput = shaped;
            hpOutput = Math.abs(hp) < 1e-20 ? 0 : hp;
            double movingGain = broken ? 0 : Math.min(1, Math.abs(speed) / .01);
            outputFade += smoothAlpha * (movingGain - outputFade);
            output = hpOutput * outputFade * amplitude;
            alarm = powered && broken ? 5 : 0;
            readPosition = wrap(readPosition + step);
            displayRotation = wrapPhase(displayRotation + movement * dt * 1.7);
        }

        void writeCrossings(double position, double step, double before, double now,
                double memory, double fade) {
            double end = position + step;
            int direction = step > 0 ? 1 : -1;
            double boundary = step > 0 ? Math.floor(position) + 1 : Math.ceil(position) - 1;
            while (step > 0 ? boundary <= end : boundary >= end) {
                double t = (boundary - position) / step;
                int at = wrapIndex((int) boundary);
                double old = sample(at);
                oldLow += .32 * (old - oldLow);
                double retained = .94 * old + .06 * oldLow;
                double recordedNoise = .0011 * noise();
                double sum = memory * retained + before + (now - before) * t
                        + recordedNoise;
                // Two substeps soften nonlinearity images; band-limited storage and
                // head filtering supply the remaining deliberate bandwidth constraint.
                double shaped = .5 * (saturate(.5 * (previousSum + sum)) + saturate(sum));
                previousSum = sum;
                preparePage(at / PAGE);
                wire[at] = (short) Math.round(limit(old + fade * (shaped - old), -16, 16) * STORAGE_SCALE);
                boundary += direction;
            }
        }

        // A low-cost input-stage softening with a restrained even-order bend.
        static double inputCharacter(double x) {
            double magnitude = Math.abs(x);
            double driven = x / (1 + .025 * magnitude);
            return driven + .018 * driven * driven / (2 + Math.abs(driven));
        }

        static double saturate(double x) {
            double magnitude = Math.abs(x);
            if (magnitude <= 3) return x;
            double excess = magnitude - 3;
            return Math.copySign(3 + 9 * excess / (9 + excess), x);
        }

        double readBandlimited(double position, double step) {
            int center = (int) Math.floor(position);
            int phase = Math.min(PHASES - 1, (int) ((position - center) * PHASES));
            int band = Math.min(BANDS - 1, Math.max(0, (int) Math.ceil((step - 1) / .2)));
            double[] coefficients = RESAMPLE[band];
            int offset = phase * TAPS;
            double result = 0;
            for (int tap = 0; tap < TAPS; tap++) {
                result += sample(wrapIndex(center + tap - TAPS / 2 + 1)) * coefficients[offset + tap];
            }
            return result;
        }

        double scarAt(double position) {
            int at = (int) position;
            int next = wrapIndex(at + 1);
            double a = pageEpoch[at / PAGE] == epoch ? (scars[at] & 255) / 255.0 : 0;
            double b = pageEpoch[next / PAGE] == epoch ? (scars[next] & 255) / 255.0 : 0;
            return a + (b - a) * (position - at);
        }

        double sample(int at) {
            return pageEpoch[at / PAGE] == epoch ? wire[at] / STORAGE_SCALE : 0;
        }

        void preparePage(int page) {
            if (snapshot != null) copySnapshotPage(snapshot, page);
            if (pageEpoch[page] != epoch) {
                int start = page * PAGE;
                int end = Math.min(CAPACITY, start + PAGE);
                java.util.Arrays.fill(wire, start, end, (short) 0);
                java.util.Arrays.fill(scars, start, end, (byte) 0);
                pageEpoch[page] = epoch;
            }
        }

        double wrap(double position) {
            position %= length;
            return position < 0 ? position + length : position;
        }

        int wrapIndex(int at) {
            at %= length;
            return at < 0 ? at + length : at;
        }

        static double wrapPhase(double phase) { return phase - Math.floor(phase); }

        double noise() {
            randomState ^= randomState << 13;
            randomState ^= randomState >>> 17;
            randomState ^= randomState << 5;
            return randomState / 2147483648.0;
        }

        static double[][] makeResampler() {
            double[][] table = new double[BANDS][PHASES * TAPS];
            for (int band = 0; band < BANDS; band++) {
                double cutoff = .46 / (1 + .2 * band);
                for (int phase = 0; phase < PHASES; phase++) {
                    double sum = 0;
                    for (int tap = 0; tap < TAPS; tap++) {
                        double x = tap - TAPS / 2 + 1 - phase / (double) PHASES;
                        double window = .42 + .5 * Math.cos(Math.PI * x / (TAPS / 2))
                                + .08 * Math.cos(2 * Math.PI * x / (TAPS / 2));
                        double v = Math.abs(x) < 1e-12 ? 2 * cutoff
                                : Math.sin(2 * Math.PI * cutoff * x) / (Math.PI * x);
                        v *= window;
                        table[band][phase * TAPS + tap] = v;
                        sum += v;
                    }
                    for (int tap = 0; tap < TAPS; tap++) table[band][phase * TAPS + tap] /= sum;
                }
            }
            return table;
        }

        static final class Biquad {
            double b0, b1, b2, a1, a2, z1, z2;
            void lowpass(double hz, double q, double rate) {
                double w = 2 * Math.PI * Math.min(hz, .45 * rate) / rate;
                double c = Math.cos(w), alpha = Math.sin(w) / (2 * q), norm = 1 / (1 + alpha);
                b0 = .5 * (1 - c) * norm; b1 = (1 - c) * norm; b2 = b0;
                a1 = -2 * c * norm; a2 = (1 - alpha) * norm;
            }
            double process(double x) {
                double y = b0 * x + z1;
                z1 = b1 * x - a1 * y + z2;
                z2 = b2 * x - a2 * y;
                if (Math.abs(z1) < 1e-20) z1 = 0;
                if (Math.abs(z2) < 1e-20) z2 = 0;
                return y;
            }
            void reset() { z1 = z2 = 0; }
        }

        static final class Snapshot {
            final short[] audio = new short[CAPACITY];
            final byte[] marks = new byte[CAPACITY];
            final boolean[] valid = new boolean[PAGES];
            final boolean[] copied = new boolean[PAGES];
            int spool, length;
            long repairs;
            double read, write, breakAt;
            boolean broken;
        }

        void copySnapshotPage(Snapshot target, int page) {
            if (page * PAGE >= target.length || target.copied[page]) return;
            if (target.valid[page]) {
                int first = page * PAGE;
                int count = Math.min(PAGE, target.length - first);
                System.arraycopy(wire, first, target.audio, first, count);
                System.arraycopy(scars, first, target.marks, first, count);
            }
            target.copied[page] = true;
        }

        byte[] save() {
            synchronized (saveLock) {
                Snapshot result = new Snapshot(); // State callback only, never audio.
                synchronized (this) {
                    result.spool = spool; result.length = length; result.repairs = repairs;
                    result.read = readPosition; result.write = writePosition;
                    result.breakAt = breakPosition; result.broken = broken;
                    for (int p = 0; p < PAGES; p++) result.valid[p] = pageEpoch[p] == epoch;
                    snapshot = result;
                }
                for (int p = 0; p * PAGE < result.length; p++) {
                    synchronized (this) { copySnapshotPage(result, p); }
                }
                synchronized (this) { snapshot = null; }
                try {
                    java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();
                    java.io.DataOutputStream out = new java.io.DataOutputStream(bytes);
                    out.writeInt(0x42575231); out.writeInt(1); out.writeInt(result.spool);
                    out.writeInt(result.length); out.writeLong(result.repairs);
                    out.writeDouble(result.read); out.writeDouble(result.write);
                    out.writeDouble(result.breakAt); out.writeBoolean(result.broken);
                    for (int i = 0; i < result.length; i++) out.writeShort(result.audio[i]);
                    int count = 0;
                    for (int i = 0; i < result.length; i++) if (result.marks[i] != 0) count++;
                    out.writeInt(count);
                    for (int i = 0; i < result.length; i++) if (result.marks[i] != 0) {
                        out.writeInt(i); out.writeByte(result.marks[i]);
                    }
                    out.flush();
                    byte[] payload = bytes.toByteArray();
                    java.util.zip.CRC32 crc = new java.util.zip.CRC32();
                    crc.update(payload); out.writeLong(crc.getValue()); out.flush();
                    return bytes.toByteArray();
                } catch (java.io.IOException impossible) {
                    throw new IllegalStateException(impossible);
                }
            }
        }

        static WireCore load(byte[] bytes, double rate) {
            if (bytes == null || bytes.length < 61 || bytes.length > CAPACITY * 7 + 100) {
                throw new IllegalArgumentException("Invalid wire state length");
            }
            try {
                java.util.zip.CRC32 crc = new java.util.zip.CRC32();
                crc.update(bytes, 0, bytes.length - 8);
                long expected = java.nio.ByteBuffer.wrap(bytes, bytes.length - 8, 8).getLong();
                if (crc.getValue() != expected) throw new IllegalArgumentException("Wire state checksum");
                java.io.DataInputStream in = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bytes));
                if (in.readInt() != 0x42575231 || in.readInt() != 1) throw new IllegalArgumentException("Wire state version");
                int selected = in.readInt(), size = in.readInt();
                if (selected < 0 || selected > 5 || size != Math.round(LENGTHS[selected] * WIRE_RATE)) {
                    throw new IllegalArgumentException("Wire spool mismatch");
                }
                WireCore core = new WireCore(rate);
                core.spool = selected; core.length = size; core.repairs = in.readLong();
                core.readPosition = in.readDouble(); core.writePosition = in.readDouble();
                core.breakPosition = in.readDouble(); core.broken = in.readBoolean();
                if (core.repairs < 0 || !validPosition(core.readPosition, size)
                        || !validPosition(core.writePosition, size) || !validPosition(core.breakPosition, size)) {
                    throw new IllegalArgumentException("Invalid wire position");
                }
                for (int i = 0; i < size; i++) core.wire[i] = in.readShort();
                int count = in.readInt();
                if (count < 0 || count > size) throw new IllegalArgumentException("Invalid splice map");
                for (int i = 0; i < count; i++) {
                    int at = in.readInt();
                    if (at < 0 || at >= size) throw new IllegalArgumentException("Invalid splice position");
                    core.scars[at] = in.readByte();
                }
                if (in.available() != 8) throw new IllegalArgumentException("Unexpected wire data");
                java.util.Arrays.fill(core.pageEpoch, core.epoch);
                core.breakThreshold = Math.max(.8, 1.5 / (1 + .08 * Math.log1p(core.repairs)));
                core.gatesArmed = false;
                return core;
            } catch (java.io.IOException ex) {
                throw new IllegalArgumentException("Truncated wire state", ex);
            }
        }

        static boolean validPosition(double position, int size) {
            return Double.isFinite(position) && position >= 0 && position < size;
        }
    }
    //[/user-code-and-variables]
}

 