package com.insectlabs.sigproc;


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


public class SIGPROC extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

@SuppressWarnings("this-escape") 
public SIGPROC( long moduleID, VoltageObjects voltageObjects )
{
    super( moduleID, voltageObjects, "signal processor", ModuleType.ModuleType_Utility, 3.2 );

    InitializeControls();


    canBeBypassed = true;
    SetSkin( "57a17f99407d426d98aa2a30359ea6c0" );
}

void InitializeControls()
{

    leftGain = new VoltageKnob( "leftGain", "leftGain", this, -3.0, 3.0, 1.0 );
    AddComponent( leftGain );
    leftGain.SetWantsMouseNotifications( false );
    leftGain.SetPosition( 32, 35 );
    leftGain.SetSize( 50, 50 );
    leftGain.SetSkin( "Cosmo v2 Large" );
    leftGain.SetRange( -3.0, 3.0, 1.0, false, 0 );
    leftGain.SetKnobParams( 215, 145 );
    leftGain.DisplayValueInPercent( false );
    leftGain.SetKnobAdjustsRing( true );

    leftOffset = new VoltageKnob( "leftOffset", "leftOffset", this, -5.0, 5.0, 0.0 );
    AddComponent( leftOffset );
    leftOffset.SetWantsMouseNotifications( false );
    leftOffset.SetPosition( 33, 105 );
    leftOffset.SetSize( 50, 50 );
    leftOffset.SetSkin( "Cosmo v2 Large" );
    leftOffset.SetRange( -5.0, 5.0, 0.0, false, 0 );
    leftOffset.SetKnobParams( 215, 145 );
    leftOffset.DisplayValueInPercent( false );
    leftOffset.SetKnobAdjustsRing( true );

    leftExternalLevel = new VoltageKnob( "leftExternalLevel", "leftExternalLevel", this, -2.0, 2.0, 1.0 );
    AddComponent( leftExternalLevel );
    leftExternalLevel.SetWantsMouseNotifications( false );
    leftExternalLevel.SetPosition( 75, 230 );
    leftExternalLevel.SetSize( 25, 25 );
    leftExternalLevel.SetSkin( "Cosmo v2 Med" );
    leftExternalLevel.SetRange( -2.0, 2.0, 1.0, false, 0 );
    leftExternalLevel.SetKnobParams( 215, 145 );
    leftExternalLevel.DisplayValueInPercent( false );
    leftExternalLevel.SetKnobAdjustsRing( true );

    leftMode = new VoltageKnob( "leftMode", "leftMode", this, 0, 1, 0 );
    AddComponent( leftMode );
    leftMode.SetWantsMouseNotifications( false );
    leftMode.SetPosition( 37, 285 );
    leftMode.SetSize( 40, 40 );
    leftMode.SetSkin( "Cosmo v2 Pointer" );
    leftMode.SetRange( 0, 1, 0, false, 2 );
    leftMode.SetKnobParams( 335, 25 );
    leftMode.DisplayValueInPercent( false );
    leftMode.SetKnobAdjustsRing( true );

    leftInput = new VoltageAudioJack( "leftInput", "leftInput", this, JackType.JackType_AudioInput );
    AddComponent( leftInput );
    leftInput.SetWantsMouseNotifications( false );
    leftInput.SetPosition( 10, 170 );
    leftInput.SetSize( 37, 37 );
    leftInput.SetSkin( "Dark Jack Straight" );

    leftExternalInput = new VoltageAudioJack( "leftExternalInput", "leftExternalInput", this, JackType.JackType_AudioInput );
    AddComponent( leftExternalInput );
    leftExternalInput.SetWantsMouseNotifications( false );
    leftExternalInput.SetPosition( 10, 225 );
    leftExternalInput.SetSize( 37, 37 );
    leftExternalInput.SetSkin( "Dark Jack Straight" );

    manufacturerLabel = new VoltageLabel( "manufacturerLabel", "manufacturerLabel", this, "insect laboratories" );
    AddComponent( manufacturerLabel );
    manufacturerLabel.SetWantsMouseNotifications( false );
    manufacturerLabel.SetPosition( 0, 335 );
    manufacturerLabel.SetSize( 230, 23 );
    manufacturerLabel.SetEditable( false, false );
    manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    manufacturerLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    manufacturerLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
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

    moduleTitleLabel = new VoltageLabel( "moduleTitleLabel", "moduleTitleLabel", this, "signal processor" );
    AddComponent( moduleTitleLabel );
    moduleTitleLabel.SetWantsMouseNotifications( false );
    moduleTitleLabel.SetPosition( 13, 1 );
    moduleTitleLabel.SetSize( 115, 13 );
    moduleTitleLabel.SetEditable( false, false );
    moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.Left );
    moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    moduleTitleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    moduleTitleLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
    moduleTitleLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
    moduleTitleLabel.SetBorderSize( 4 );
    moduleTitleLabel.SetMultiLineEdit( false );
    moduleTitleLabel.SetIsNumberEditor( false );
    moduleTitleLabel.SetNumberEditorRange( 0, 100 );
    moduleTitleLabel.SetNumberEditorInterval( 1 );
    moduleTitleLabel.SetNumberEditorUsesMouseWheel( false );
    moduleTitleLabel.SetHasCustomTextHoverColor( false );
    moduleTitleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    moduleTitleLabel.SetFont( "Courier New", 13, true, false );

    rightGain = new VoltageKnob( "rightGain", "rightGain", this, -3.0, 3.0, 1.0 );
    AddComponent( rightGain );
    rightGain.SetWantsMouseNotifications( false );
    rightGain.SetPosition( 146, 35 );
    rightGain.SetSize( 50, 50 );
    rightGain.SetSkin( "Cosmo v2 Large" );
    rightGain.SetRange( -3.0, 3.0, 1.0, false, 0 );
    rightGain.SetKnobParams( 215, 145 );
    rightGain.DisplayValueInPercent( false );
    rightGain.SetKnobAdjustsRing( true );

    rightOffset = new VoltageKnob( "rightOffset", "rightOffset", this, -5.0, 5.0, 0.0 );
    AddComponent( rightOffset );
    rightOffset.SetWantsMouseNotifications( false );
    rightOffset.SetPosition( 146, 105 );
    rightOffset.SetSize( 50, 50 );
    rightOffset.SetSkin( "Cosmo v2 Large" );
    rightOffset.SetRange( -5.0, 5.0, 0.0, false, 0 );
    rightOffset.SetKnobParams( 215, 145 );
    rightOffset.DisplayValueInPercent( false );
    rightOffset.SetKnobAdjustsRing( true );

    rightExternalLevel = new VoltageKnob( "rightExternalLevel", "rightExternalLevel", this, -2.0, 2.0, 1.0 );
    AddComponent( rightExternalLevel );
    rightExternalLevel.SetWantsMouseNotifications( false );
    rightExternalLevel.SetPosition( 189, 230 );
    rightExternalLevel.SetSize( 25, 25 );
    rightExternalLevel.SetSkin( "Cosmo v2 Med" );
    rightExternalLevel.SetRange( -2.0, 2.0, 1.0, false, 0 );
    rightExternalLevel.SetKnobParams( 215, 145 );
    rightExternalLevel.DisplayValueInPercent( false );
    rightExternalLevel.SetKnobAdjustsRing( true );

    rightMode = new VoltageKnob( "rightMode", "rightMode", this, 0, 1, 0 );
    AddComponent( rightMode );
    rightMode.SetWantsMouseNotifications( false );
    rightMode.SetPosition( 150, 285 );
    rightMode.SetSize( 40, 40 );
    rightMode.SetSkin( "Cosmo v2 Pointer" );
    rightMode.SetRange( 0, 1, 0, false, 2 );
    rightMode.SetKnobParams( 335, 25 );
    rightMode.DisplayValueInPercent( false );
    rightMode.SetKnobAdjustsRing( true );

    rightInput = new VoltageAudioJack( "rightInput", "rightInput", this, JackType.JackType_AudioInput );
    AddComponent( rightInput );
    rightInput.SetWantsMouseNotifications( false );
    rightInput.SetPosition( 123, 170 );
    rightInput.SetSize( 37, 37 );
    rightInput.SetSkin( "Dark Jack Straight" );

    rightExternalInput = new VoltageAudioJack( "rightExternalInput", "rightExternalInput", this, JackType.JackType_AudioInput );
    AddComponent( rightExternalInput );
    rightExternalInput.SetWantsMouseNotifications( false );
    rightExternalInput.SetPosition( 123, 225 );
    rightExternalInput.SetSize( 37, 37 );
    rightExternalInput.SetSkin( "Dark Jack Straight" );

    leftGainLabel = new VoltageLabel( "leftGainLabel", "leftGainLabel", this, "GAIN" );
    AddComponent( leftGainLabel );
    leftGainLabel.SetWantsMouseNotifications( false );
    leftGainLabel.SetPosition( 43, 15 );
    leftGainLabel.SetSize( 30, 20 );
    leftGainLabel.SetEditable( false, false );
    leftGainLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    leftGainLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    leftGainLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    leftGainLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    leftGainLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    leftGainLabel.SetBorderSize( 1 );
    leftGainLabel.SetMultiLineEdit( false );
    leftGainLabel.SetIsNumberEditor( false );
    leftGainLabel.SetNumberEditorRange( 0, 100 );
    leftGainLabel.SetNumberEditorInterval( 1 );
    leftGainLabel.SetNumberEditorUsesMouseWheel( false );
    leftGainLabel.SetHasCustomTextHoverColor( false );
    leftGainLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    leftGainLabel.SetFont( "Arial", 9, true, false );

    rightGainLabel = new VoltageLabel( "rightGainLabel", "rightGainLabel", this, "GAIN" );
    AddComponent( rightGainLabel );
    rightGainLabel.SetWantsMouseNotifications( false );
    rightGainLabel.SetPosition( 156, 15 );
    rightGainLabel.SetSize( 30, 20 );
    rightGainLabel.SetEditable( false, false );
    rightGainLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    rightGainLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    rightGainLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    rightGainLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    rightGainLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    rightGainLabel.SetBorderSize( 1 );
    rightGainLabel.SetMultiLineEdit( false );
    rightGainLabel.SetIsNumberEditor( false );
    rightGainLabel.SetNumberEditorRange( 0, 100 );
    rightGainLabel.SetNumberEditorInterval( 1 );
    rightGainLabel.SetNumberEditorUsesMouseWheel( false );
    rightGainLabel.SetHasCustomTextHoverColor( false );
    rightGainLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    rightGainLabel.SetFont( "Arial", 9, true, false );

    leftOffsetLabel = new VoltageLabel( "leftOffsetLabel", "leftOffsetLabel", this, "OFFSET" );
    AddComponent( leftOffsetLabel );
    leftOffsetLabel.SetWantsMouseNotifications( false );
    leftOffsetLabel.SetPosition( 38, 85 );
    leftOffsetLabel.SetSize( 40, 20 );
    leftOffsetLabel.SetEditable( false, false );
    leftOffsetLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    leftOffsetLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    leftOffsetLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    leftOffsetLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    leftOffsetLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    leftOffsetLabel.SetBorderSize( 1 );
    leftOffsetLabel.SetMultiLineEdit( false );
    leftOffsetLabel.SetIsNumberEditor( false );
    leftOffsetLabel.SetNumberEditorRange( 0, 100 );
    leftOffsetLabel.SetNumberEditorInterval( 1 );
    leftOffsetLabel.SetNumberEditorUsesMouseWheel( false );
    leftOffsetLabel.SetHasCustomTextHoverColor( false );
    leftOffsetLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    leftOffsetLabel.SetFont( "Arial", 9, true, false );

    rightOffsetLabel = new VoltageLabel( "rightOffsetLabel", "rightOffsetLabel", this, "OFFSET" );
    AddComponent( rightOffsetLabel );
    rightOffsetLabel.SetWantsMouseNotifications( false );
    rightOffsetLabel.SetPosition( 151, 85 );
    rightOffsetLabel.SetSize( 40, 20 );
    rightOffsetLabel.SetEditable( false, false );
    rightOffsetLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    rightOffsetLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    rightOffsetLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    rightOffsetLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    rightOffsetLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    rightOffsetLabel.SetBorderSize( 1 );
    rightOffsetLabel.SetMultiLineEdit( false );
    rightOffsetLabel.SetIsNumberEditor( false );
    rightOffsetLabel.SetNumberEditorRange( 0, 100 );
    rightOffsetLabel.SetNumberEditorInterval( 1 );
    rightOffsetLabel.SetNumberEditorUsesMouseWheel( false );
    rightOffsetLabel.SetHasCustomTextHoverColor( false );
    rightOffsetLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    rightOffsetLabel.SetFont( "Arial", 9, true, false );

    leftInputLabel = new VoltageLabel( "leftInputLabel", "leftInputLabel", this, "INPUT" );
    AddComponent( leftInputLabel );
    leftInputLabel.SetWantsMouseNotifications( false );
    leftInputLabel.SetPosition( 8, 155 );
    leftInputLabel.SetSize( 40, 20 );
    leftInputLabel.SetEditable( false, false );
    leftInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    leftInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    leftInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    leftInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    leftInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    leftInputLabel.SetBorderSize( 1 );
    leftInputLabel.SetMultiLineEdit( false );
    leftInputLabel.SetIsNumberEditor( false );
    leftInputLabel.SetNumberEditorRange( 0, 100 );
    leftInputLabel.SetNumberEditorInterval( 1 );
    leftInputLabel.SetNumberEditorUsesMouseWheel( false );
    leftInputLabel.SetHasCustomTextHoverColor( false );
    leftInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    leftInputLabel.SetFont( "Arial", 9, true, false );

    rightInputLabel = new VoltageLabel( "rightInputLabel", "rightInputLabel", this, "INPUT" );
    AddComponent( rightInputLabel );
    rightInputLabel.SetWantsMouseNotifications( false );
    rightInputLabel.SetPosition( 121, 155 );
    rightInputLabel.SetSize( 40, 20 );
    rightInputLabel.SetEditable( false, false );
    rightInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    rightInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    rightInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    rightInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    rightInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    rightInputLabel.SetBorderSize( 1 );
    rightInputLabel.SetMultiLineEdit( false );
    rightInputLabel.SetIsNumberEditor( false );
    rightInputLabel.SetNumberEditorRange( 0, 100 );
    rightInputLabel.SetNumberEditorInterval( 1 );
    rightInputLabel.SetNumberEditorUsesMouseWheel( false );
    rightInputLabel.SetHasCustomTextHoverColor( false );
    rightInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    rightInputLabel.SetFont( "Arial", 9, true, false );

    leftOutputLabel = new VoltageLabel( "leftOutputLabel", "leftOutputLabel", this, "OUTPUT" );
    AddComponent( leftOutputLabel );
    leftOutputLabel.SetWantsMouseNotifications( false );
    leftOutputLabel.SetPosition( 69, 155 );
    leftOutputLabel.SetSize( 40, 20 );
    leftOutputLabel.SetEditable( false, false );
    leftOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    leftOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    leftOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    leftOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    leftOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    leftOutputLabel.SetBorderSize( 1 );
    leftOutputLabel.SetMultiLineEdit( false );
    leftOutputLabel.SetIsNumberEditor( false );
    leftOutputLabel.SetNumberEditorRange( 0, 100 );
    leftOutputLabel.SetNumberEditorInterval( 1 );
    leftOutputLabel.SetNumberEditorUsesMouseWheel( false );
    leftOutputLabel.SetHasCustomTextHoverColor( false );
    leftOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    leftOutputLabel.SetFont( "Arial", 9, true, false );

    rightOutputLabel = new VoltageLabel( "rightOutputLabel", "rightOutputLabel", this, "OUTPUT" );
    AddComponent( rightOutputLabel );
    rightOutputLabel.SetWantsMouseNotifications( false );
    rightOutputLabel.SetPosition( 182, 155 );
    rightOutputLabel.SetSize( 40, 20 );
    rightOutputLabel.SetEditable( false, false );
    rightOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    rightOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    rightOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    rightOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    rightOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    rightOutputLabel.SetBorderSize( 1 );
    rightOutputLabel.SetMultiLineEdit( false );
    rightOutputLabel.SetIsNumberEditor( false );
    rightOutputLabel.SetNumberEditorRange( 0, 100 );
    rightOutputLabel.SetNumberEditorInterval( 1 );
    rightOutputLabel.SetNumberEditorUsesMouseWheel( false );
    rightOutputLabel.SetHasCustomTextHoverColor( false );
    rightOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    rightOutputLabel.SetFont( "Arial", 9, true, false );

    leftExternalLevelLabel = new VoltageLabel( "leftExternalLevelLabel", "leftExternalLevelLabel", this, "EXT LVL" );
    AddComponent( leftExternalLevelLabel );
    leftExternalLevelLabel.SetWantsMouseNotifications( false );
    leftExternalLevelLabel.SetPosition( 62, 210 );
    leftExternalLevelLabel.SetSize( 50, 20 );
    leftExternalLevelLabel.SetEditable( false, false );
    leftExternalLevelLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    leftExternalLevelLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    leftExternalLevelLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    leftExternalLevelLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    leftExternalLevelLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    leftExternalLevelLabel.SetBorderSize( 1 );
    leftExternalLevelLabel.SetMultiLineEdit( false );
    leftExternalLevelLabel.SetIsNumberEditor( false );
    leftExternalLevelLabel.SetNumberEditorRange( 0, 100 );
    leftExternalLevelLabel.SetNumberEditorInterval( 1 );
    leftExternalLevelLabel.SetNumberEditorUsesMouseWheel( false );
    leftExternalLevelLabel.SetHasCustomTextHoverColor( false );
    leftExternalLevelLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    leftExternalLevelLabel.SetFont( "Arial", 9, true, false );

    rightExternalLevelLabel = new VoltageLabel( "rightExternalLevelLabel", "rightExternalLevelLabel", this, "EXT LVL" );
    AddComponent( rightExternalLevelLabel );
    rightExternalLevelLabel.SetWantsMouseNotifications( false );
    rightExternalLevelLabel.SetPosition( 182, 210 );
    rightExternalLevelLabel.SetSize( 40, 20 );
    rightExternalLevelLabel.SetEditable( false, false );
    rightExternalLevelLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    rightExternalLevelLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    rightExternalLevelLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    rightExternalLevelLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    rightExternalLevelLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    rightExternalLevelLabel.SetBorderSize( 1 );
    rightExternalLevelLabel.SetMultiLineEdit( false );
    rightExternalLevelLabel.SetIsNumberEditor( false );
    rightExternalLevelLabel.SetNumberEditorRange( 0, 100 );
    rightExternalLevelLabel.SetNumberEditorInterval( 1 );
    rightExternalLevelLabel.SetNumberEditorUsesMouseWheel( false );
    rightExternalLevelLabel.SetHasCustomTextHoverColor( false );
    rightExternalLevelLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    rightExternalLevelLabel.SetFont( "Arial", 9, true, false );

    leftExternalInputLabel = new VoltageLabel( "leftExternalInputLabel", "leftExternalInputLabel", this, "EXT INPUT" );
    AddComponent( leftExternalInputLabel );
    leftExternalInputLabel.SetWantsMouseNotifications( false );
    leftExternalInputLabel.SetPosition( 4, 210 );
    leftExternalInputLabel.SetSize( 50, 20 );
    leftExternalInputLabel.SetEditable( false, false );
    leftExternalInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    leftExternalInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    leftExternalInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    leftExternalInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    leftExternalInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    leftExternalInputLabel.SetBorderSize( 1 );
    leftExternalInputLabel.SetMultiLineEdit( false );
    leftExternalInputLabel.SetIsNumberEditor( false );
    leftExternalInputLabel.SetNumberEditorRange( 0, 100 );
    leftExternalInputLabel.SetNumberEditorInterval( 1 );
    leftExternalInputLabel.SetNumberEditorUsesMouseWheel( false );
    leftExternalInputLabel.SetHasCustomTextHoverColor( false );
    leftExternalInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    leftExternalInputLabel.SetFont( "Arial", 9, true, false );

    rightExternalInputLabel = new VoltageLabel( "rightExternalInputLabel", "rightExternalInputLabel", this, "EXT INPUT" );
    AddComponent( rightExternalInputLabel );
    rightExternalInputLabel.SetWantsMouseNotifications( false );
    rightExternalInputLabel.SetPosition( 117, 210 );
    rightExternalInputLabel.SetSize( 50, 20 );
    rightExternalInputLabel.SetEditable( false, false );
    rightExternalInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    rightExternalInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    rightExternalInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    rightExternalInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    rightExternalInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    rightExternalInputLabel.SetBorderSize( 1 );
    rightExternalInputLabel.SetMultiLineEdit( false );
    rightExternalInputLabel.SetIsNumberEditor( false );
    rightExternalInputLabel.SetNumberEditorRange( 0, 100 );
    rightExternalInputLabel.SetNumberEditorInterval( 1 );
    rightExternalInputLabel.SetNumberEditorUsesMouseWheel( false );
    rightExternalInputLabel.SetHasCustomTextHoverColor( false );
    rightExternalInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    rightExternalInputLabel.SetFont( "Arial", 9, true, false );

    leftModeLabel = new VoltageLabel( "leftModeLabel", "leftModeLabel", this, "PROC    VCA" );
    AddComponent( leftModeLabel );
    leftModeLabel.SetWantsMouseNotifications( false );
    leftModeLabel.SetPosition( 0, 265 );
    leftModeLabel.SetSize( 116, 20 );
    leftModeLabel.SetEditable( false, false );
    leftModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    leftModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    leftModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    leftModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    leftModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    leftModeLabel.SetBorderSize( 1 );
    leftModeLabel.SetMultiLineEdit( false );
    leftModeLabel.SetIsNumberEditor( false );
    leftModeLabel.SetNumberEditorRange( 0, 100 );
    leftModeLabel.SetNumberEditorInterval( 1 );
    leftModeLabel.SetNumberEditorUsesMouseWheel( false );
    leftModeLabel.SetHasCustomTextHoverColor( false );
    leftModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    leftModeLabel.SetFont( "Arial", 9, true, false );

    rightModeLabel = new VoltageLabel( "rightModeLabel", "rightModeLabel", this, "PROC    VCA" );
    AddComponent( rightModeLabel );
    rightModeLabel.SetWantsMouseNotifications( false );
    rightModeLabel.SetPosition( 113, 265 );
    rightModeLabel.SetSize( 116, 20 );
    rightModeLabel.SetEditable( false, false );
    rightModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    rightModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    rightModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
    rightModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
    rightModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
    rightModeLabel.SetBorderSize( 1 );
    rightModeLabel.SetMultiLineEdit( false );
    rightModeLabel.SetIsNumberEditor( false );
    rightModeLabel.SetNumberEditorRange( 0, 100 );
    rightModeLabel.SetNumberEditorInterval( 1 );
    rightModeLabel.SetNumberEditorUsesMouseWheel( false );
    rightModeLabel.SetHasCustomTextHoverColor( false );
    rightModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    rightModeLabel.SetFont( "Arial", 9, true, false );

    leftOutput = new VoltageAudioJack( "leftOutput", "leftOutput", this, JackType.JackType_AudioOutput );
    AddComponent( leftOutput );
    leftOutput.SetWantsMouseNotifications( false );
    leftOutput.SetPosition( 70, 170 );
    leftOutput.SetSize( 37, 37 );
    leftOutput.SetSkin( "Rotated Half" );

    rightOutput = new VoltageAudioJack( "rightOutput", "rightOutput", this, JackType.JackType_AudioOutput );
    AddComponent( rightOutput );
    rightOutput.SetWantsMouseNotifications( false );
    rightOutput.SetPosition( 183, 170 );
    rightOutput.SetSize( 37, 37 );
    rightOutput.SetSkin( "Rotated Half" );
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
    signalProcessor = new SignalProcessorDsp(SAMPLE_RATE);
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
    // Control reads stay on the audio thread, following the Colorbox baseline.
    signalProcessor.setLeftControls(leftGain.GetValue(), leftOffset.GetValue(), leftExternalLevel.GetValue(),
            leftMode.GetValue() >= 0.5);

    signalProcessor.setRightControls(rightGain.GetValue(), rightOffset.GetValue(), rightExternalLevel.GetValue(),
            rightMode.GetValue() >= 0.5);
    signalProcessor.process(readInput(leftInput), readInput(leftExternalInput),
            readInput(rightInput), readInput(rightExternalInput));
    leftOutput.SetValue(signalProcessor.getLeftOutput());
    rightOutput.SetValue(signalProcessor.getRightOutput());
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
    // Series-standard direct host bypass: read signal inputs only, copy them to
    // their corresponding outputs, and freeze all control and DSP histories.
    signalProcessor.processBypassed(readInput(leftInput), readInput(rightInput));
    leftOutput.SetValue(signalProcessor.getLeftOutput());
    rightOutput.SetValue(signalProcessor.getRightOutput());
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
    if (component == leftGain)
        return gainTooltip("LEFT", leftGain.GetValue(), leftMode.GetValue() >= 0.5);
    if (component == rightGain)
        return gainTooltip("RIGHT", rightGain.GetValue(), rightMode.GetValue() >= 0.5);
    if (component == leftOffset)
        return "LEFT offset: " + tooltipValue(leftOffset.GetValue()) + " V (post-character)";
    if (component == rightOffset)
        return "RIGHT offset: " + tooltipValue(rightOffset.GetValue()) + " V (post-character)";
    if (component == leftExternalLevel)
        return "LEFT external level: " + tooltipValue(leftExternalLevel.GetValue())
                + " x EXT INPUT / 5 V";
    if (component == rightExternalLevel)
        return "RIGHT external level: " + tooltipValue(rightExternalLevel.GetValue())
                + " x EXT INPUT / 5 V";
    if (component == leftMode)
        return modeTooltip("LEFT", leftMode.GetValue() >= 0.5);
    if (component == rightMode)
        return modeTooltip("RIGHT", rightMode.GetValue() >= 0.5);
    if (component == leftInput) return "LEFT signal input (unpatched: 0 V)";
    if (component == rightInput) return "RIGHT signal input (unpatched: 0 V)";
    if (component == leftExternalInput)
        return "LEFT external gain-control input; +5 V equals EXT LVL";
    if (component == rightExternalInput)
        return "RIGHT external gain-control input; +5 V equals EXT LVL";
    if (component == leftOutput) return "LEFT processed signal output";
    if (component == rightOutput) return "RIGHT processed signal output";
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
private VoltageAudioJack rightOutput;
private VoltageAudioJack leftOutput;
private VoltageLabel rightModeLabel;
private VoltageLabel leftModeLabel;
private VoltageLabel rightExternalInputLabel;
private VoltageLabel leftExternalInputLabel;
private VoltageLabel rightExternalLevelLabel;
private VoltageLabel leftExternalLevelLabel;
private VoltageLabel rightOutputLabel;
private VoltageLabel leftOutputLabel;
private VoltageLabel rightInputLabel;
private VoltageLabel leftInputLabel;
private VoltageLabel rightOffsetLabel;
private VoltageLabel leftOffsetLabel;
private VoltageLabel rightGainLabel;
private VoltageLabel leftGainLabel;
private VoltageAudioJack rightExternalInput;
private VoltageAudioJack rightInput;
private VoltageKnob rightMode;
private VoltageKnob rightExternalLevel;
private VoltageKnob rightOffset;
private VoltageKnob rightGain;
private VoltageLabel moduleTitleLabel;
private VoltageLabel manufacturerLabel;
private VoltageAudioJack leftExternalInput;
private VoltageAudioJack leftInput;
private VoltageKnob leftMode;
private VoltageKnob leftExternalLevel;
private VoltageKnob leftOffset;
private VoltageKnob leftGain;


//[user-code-and-variables]    Add your own variables and functions here
// Signal Processor 1.0.0 - insect laboratories.
// Two independent mono stages; 2x character processing and direct host bypass.
private static final double SAMPLE_RATE = 48000.0;
private static final double CV_REFERENCE_VOLTS = 5.0;
private static final double MANUAL_SMOOTH_SECONDS = 0.005;
private static final double VACTROL_ATTACK_SECONDS = 0.003;
private static final double VACTROL_RELEASE_SECONDS = 0.030;
private static final double OVERSAMPLE_FACTOR = 2.0;
private static final double NEGATIVE_PROC_CHARACTER = 0.35;
private static final double CHARACTER_KNEE_VOLTS = 3.5;
private static final double CHARACTER_DRIVE_START_VOLTS = 3.0;
private static final double CHARACTER_DRIVE_SPAN_VOLTS = 6.0;
private static final double CLEAN_CUTOFF_HZ = 30000.0;
private static final double DRIVEN_CUTOFF_HZ = 16000.0;
private static final double POSITIVE_COMPRESSION = 0.10;
private static final double NEGATIVE_COMPRESSION = 0.08;

private SignalProcessorDsp signalProcessor;

private static double readInput(VoltageAudioJack jack) {
    return jack.IsConnected() ? jack.GetValue() : 0.0;
}

private static double tooltipValue(double value) {
    return Math.round(value * 100.0) / 100.0;
}

private static String gainTooltip(String side, double value, boolean vcaMode) {
    String detail;
    if (vcaMode) {
        detail = "VCA; unipolar vactrol response";
    } else if (value < 0) {
        detail = "PROC; inverted, lighter character";
    } else {
        detail = "PROC; bipolar, full character";
    }
    return side + " gain: " + tooltipValue(value) + " x (" + detail + ")";
}

private static String modeTooltip(String side, boolean vcaMode) {
    return vcaMode
            ? side + " mode: VCA - unipolar, rounded, 3 ms attack / 30 ms release"
            : side + " mode: PROC - bipolar, immediate external modulation";
}

private static final class SignalProcessorDsp {
    private final ProcessorStage left;
    private final ProcessorStage right;
    private boolean resumePending = true;
    private double leftOutput;
    private double rightOutput;

    private SignalProcessorDsp(double sampleRate) {
        if (!Double.isFinite(sampleRate) || sampleRate < 1 || sampleRate > 1000000) {
            throw new IllegalArgumentException("Invalid sample rate");
        }
        int rampSamples = Math.max(1, (int) Math.round(sampleRate * MANUAL_SMOOTH_SECONDS));
        left = new ProcessorStage(sampleRate, rampSamples);
        right = new ProcessorStage(sampleRate, rampSamples);
    }

    private void setLeftControls(
            double gain, double offset, double externalLevel, boolean vcaMode) {
        left.setControls(gain, offset, externalLevel, vcaMode);
    }

    private void setRightControls(
            double gain, double offset, double externalLevel, boolean vcaMode) {
        right.setControls(gain, offset, externalLevel, vcaMode);
    }

    private void process(double leftInput, double leftCv, double rightInput, double rightCv) {
        if (resumePending) {
            left.snap();
            right.snap();
            resumePending = false;
        }
        leftOutput = left.process(leftInput, leftCv);
        rightOutput = right.process(rightInput, rightCv);
    }

    private void processBypassed(double leftInput, double rightInput) {
        leftOutput = leftInput;
        rightOutput = rightInput;
        resumePending = true;
    }

    private double getLeftOutput() {
        return leftOutput;
    }

    private double getRightOutput() {
        return rightOutput;
    }
}
private static final class ProcessorStage {
    private final ControlRamp gain;
    private final ControlRamp offset;
    private final ControlRamp externalLevel;
    private final double vactrolAttackCoefficient;
    private final double vactrolReleaseCoefficient;
    private final double oversampledRate;
    private boolean vcaMode;
    private double vactrolGain = Double.NaN;
    private double previousCharacterInput;
    private double toneState;
    private boolean characterReady;

    private ProcessorStage(double sampleRate, int rampSamples) {
        gain = new ControlRamp(1, rampSamples);
        offset = new ControlRamp(0, rampSamples);
        externalLevel = new ControlRamp(0, rampSamples);
        vactrolAttackCoefficient = 1 - Math.exp(-1 / (sampleRate * VACTROL_ATTACK_SECONDS));
        vactrolReleaseCoefficient = 1 - Math.exp(-1 / (sampleRate * VACTROL_RELEASE_SECONDS));
        oversampledRate = sampleRate * OVERSAMPLE_FACTOR;
    }

    private void setControls(
            double newGain, double newOffset, double newExternalLevel, boolean newVcaMode) {
        if (!Double.isFinite(newGain)
                || !Double.isFinite(newOffset)
                || !Double.isFinite(newExternalLevel)) {
            throw new IllegalArgumentException("Controls must be finite");
        }
        gain.setTarget(clamp(newGain, -3, 3));
        offset.setTarget(clamp(newOffset, -5, 5));
        externalLevel.setTarget(clamp(newExternalLevel, -2, 2));
        if (vcaMode != newVcaMode) {
            vactrolGain = Double.NaN;
        }
        vcaMode = newVcaMode;
    }

    private void snap() {
        gain.snap();
        offset.snap();
        externalLevel.snap();
        vactrolGain = Double.NaN;
        characterReady = false;
    }

    private double process(double input, double cv) {
        double requestedGain = gain.next()
                + externalLevel.next() * (cv / CV_REFERENCE_VOLTS);
        double effectiveGain;
        if (vcaMode) {
            double target = roundedVcaGain(clamp(requestedGain, 0, 3));
            if (Double.isNaN(vactrolGain)) {
                vactrolGain = target;
            } else {
                double coefficient = target > vactrolGain
                        ? vactrolAttackCoefficient
                        : vactrolReleaseCoefficient;
                vactrolGain += coefficient * (target - vactrolGain);
            }
            effectiveGain = vactrolGain;
        } else {
            effectiveGain = clamp(requestedGain, -3, 3);
        }
        double characterAmount = !vcaMode && effectiveGain < 0
                ? NEGATIVE_PROC_CHARACTER
                : 1.0;
        return processCharacter(input * effectiveGain, characterAmount) + offset.next();
    }

    private static double roundedVcaGain(double value) {
        return value < 1 ? value * value * (2 - value) : value;
    }

    private double processCharacter(double value, double amount) {
        if (!characterReady) {
            previousCharacterInput = value;
            toneState = characterCurve(value, amount);
            characterReady = true;
            return toneState;
        }
        processCharacterStep((previousCharacterInput + value) * 0.5, amount);
        previousCharacterInput = value;
        return processCharacterStep(value, amount);
    }

    private double processCharacterStep(double value, double amount) {
        double saturated = characterCurve(value, amount);
        double magnitude = Math.abs(value);
        double drive = clamp(
                (magnitude - CHARACTER_DRIVE_START_VOLTS) / CHARACTER_DRIVE_SPAN_VOLTS,
                0,
                1) * amount;
        double cutoff = CLEAN_CUTOFF_HZ - (CLEAN_CUTOFF_HZ - DRIVEN_CUTOFF_HZ) * drive;
        double coefficient = 1 - Math.exp(-2 * Math.PI * cutoff / oversampledRate);
        toneState += coefficient * (saturated - toneState);
        return toneState;
    }

    private static double characterCurve(double value, double amount) {
        return value + amount * (fullCharacterCurve(value) - value);
    }

    private static double fullCharacterCurve(double value) {
        double magnitude = Math.abs(value);
        double shaped = magnitude;
        if (magnitude > CHARACTER_KNEE_VOLTS) {
            double excess = magnitude - CHARACTER_KNEE_VOLTS;
            double compression = value >= 0 ? POSITIVE_COMPRESSION : NEGATIVE_COMPRESSION;
            shaped = CHARACTER_KNEE_VOLTS + excess / (1 + compression * excess);
        }
        return Math.copySign(shaped, value);
    }
}
private static final class ControlRamp {
    private final int durationSamples;
    private double current;
    private double target;
    private double step;
    private int remainingSamples;

    private ControlRamp(double initial, int durationSamples) {
        this.durationSamples = durationSamples;
        current = target = initial;
    }

    private void setTarget(double value) {
        if (value == target) return;
        target = value;
        step = (target - current) / durationSamples;
        remainingSamples = durationSamples;
    }

    private double next() {
        if (remainingSamples > 0) {
            current += step;
            if (--remainingSamples == 0) current = target;
        }
        return current;
    }

    private void snap() {
        current = target;
        remainingSamples = 0;
    }
}
private static double clamp(double value, double minimum, double maximum) {
    return Math.max(minimum, Math.min(maximum, value));
}





//[/user-code-and-variables]
}

 