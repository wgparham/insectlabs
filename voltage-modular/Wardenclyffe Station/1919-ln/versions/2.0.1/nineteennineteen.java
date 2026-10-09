package com.insectlabs.nineteennineteen;


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


public class nineteennineteen extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public nineteennineteen( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "1919/ln - tone burst generator", ModuleType.ModuleType_Utility, 3.2 );

        InitializeControls();
        InitializeControls2();


        canBeBypassed = true;
        SetSkin( "e4fa6c7248cd4e828592a78bed344152" );
    }

void InitializeControls()
{

        modelNumberLabel = new VoltageLabel( "modelNumberLabel", "Model Number_1", this, "model 1919/ln" );
        AddComponent( modelNumberLabel );
        modelNumberLabel.SetWantsMouseNotifications( false );
        modelNumberLabel.SetPosition( 140, 301 );
        modelNumberLabel.SetSize( 80, 10 );
        modelNumberLabel.SetEditable( false, false );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modelNumberLabel.SetColor( new Color( 85, 85, 85, 147 ) );
        modelNumberLabel.SetBkColor( new Color( 0, 0, 0, 0 ) );
        modelNumberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modelNumberLabel.SetBorderSize( 1 );
        modelNumberLabel.SetMultiLineEdit( false );
        modelNumberLabel.SetIsNumberEditor( false );
        modelNumberLabel.SetNumberEditorRange( 0, 100 );
        modelNumberLabel.SetNumberEditorInterval( 1 );
        modelNumberLabel.SetNumberEditorUsesMouseWheel( false );
        modelNumberLabel.SetHasCustomTextHoverColor( false );
        modelNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modelNumberLabel.SetFont( "Courier New", 6, true, false );

        manufacturerLocationLabel = new VoltageLabel( "manufacturerLocationLabel", "Model Number_4", this, "pittsburgh • usa" );
        AddComponent( manufacturerLocationLabel );
        manufacturerLocationLabel.SetWantsMouseNotifications( false );
        manufacturerLocationLabel.SetPosition( 140, 323 );
        manufacturerLocationLabel.SetSize( 80, 10 );
        manufacturerLocationLabel.SetEditable( false, false );
        manufacturerLocationLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLocationLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLocationLabel.SetColor( new Color( 85, 85, 85, 147 ) );
        manufacturerLocationLabel.SetBkColor( new Color( 0, 0, 0, 0 ) );
        manufacturerLocationLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerLocationLabel.SetBorderSize( 1 );
        manufacturerLocationLabel.SetMultiLineEdit( false );
        manufacturerLocationLabel.SetIsNumberEditor( false );
        manufacturerLocationLabel.SetNumberEditorRange( 0, 100 );
        manufacturerLocationLabel.SetNumberEditorInterval( 1 );
        manufacturerLocationLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerLocationLabel.SetHasCustomTextHoverColor( false );
        manufacturerLocationLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerLocationLabel.SetFont( "Courier New", 6, true, false );

        moduleDescriptionLabel = new VoltageLabel( "moduleDescriptionLabel", "Model Number_2", this, "low noise burst generator" );
        AddComponent( moduleDescriptionLabel );
        moduleDescriptionLabel.SetWantsMouseNotifications( false );
        moduleDescriptionLabel.SetPosition( 140, 307 );
        moduleDescriptionLabel.SetSize( 80, 10 );
        moduleDescriptionLabel.SetEditable( false, false );
        moduleDescriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        moduleDescriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleDescriptionLabel.SetColor( new Color( 85, 85, 85, 147 ) );
        moduleDescriptionLabel.SetBkColor( new Color( 0, 0, 0, 0 ) );
        moduleDescriptionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        moduleDescriptionLabel.SetBorderSize( 1 );
        moduleDescriptionLabel.SetMultiLineEdit( false );
        moduleDescriptionLabel.SetIsNumberEditor( false );
        moduleDescriptionLabel.SetNumberEditorRange( 0, 100 );
        moduleDescriptionLabel.SetNumberEditorInterval( 1 );
        moduleDescriptionLabel.SetNumberEditorUsesMouseWheel( false );
        moduleDescriptionLabel.SetHasCustomTextHoverColor( false );
        moduleDescriptionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleDescriptionLabel.SetFont( "Courier New", 6, true, false );

        setCountXDisplay = new VoltageDigitalCounter( "setCountXDisplay", "X Set Count", this, 4 );
        AddComponent( setCountXDisplay );
        setCountXDisplay.SetWantsMouseNotifications( false );
        setCountXDisplay.SetPosition( 15, 101 );
        setCountXDisplay.SetSize( 81, 50 );
        setCountXDisplay.SetSkin( "Gray" );
        setCountXDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        nameTitle = new VoltageLabel( "nameTitle", "Module Name", this, "tone burst generator" );
        AddComponent( nameTitle );
        nameTitle.SetWantsMouseNotifications( false );
        nameTitle.SetPosition( 3, 3 );
        nameTitle.SetSize( 224, 23 );
        nameTitle.SetEditable( false, false );
        nameTitle.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        nameTitle.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        nameTitle.SetColor( new Color( 232, 232, 232, 255 ) );
        nameTitle.SetBkColor( new Color( 65, 65, 65, 0 ) );
        nameTitle.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        nameTitle.SetBorderSize( 4 );
        nameTitle.SetMultiLineEdit( false );
        nameTitle.SetIsNumberEditor( false );
        nameTitle.SetNumberEditorRange( 0, 100 );
        nameTitle.SetNumberEditorInterval( 1 );
        nameTitle.SetNumberEditorUsesMouseWheel( false );
        nameTitle.SetHasCustomTextHoverColor( false );
        nameTitle.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        nameTitle.SetFont( "Courier New", 13, true, false );

        numberTitle = new VoltageLabel( "numberTitle", "Model Number", this, "1919/ln" );
        AddComponent( numberTitle );
        numberTitle.SetWantsMouseNotifications( false );
        numberTitle.SetPosition( 33, 335 );
        numberTitle.SetSize( 194, 13 );
        numberTitle.SetEditable( false, false );
        numberTitle.SetJustificationFlags( VoltageLabel.Justification.Right );
        numberTitle.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        numberTitle.SetColor( new Color( 232, 232, 232, 255 ) );
        numberTitle.SetBkColor( new Color( 0, 0, 0, 0 ) );
        numberTitle.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        numberTitle.SetBorderSize( 1 );
        numberTitle.SetMultiLineEdit( false );
        numberTitle.SetIsNumberEditor( false );
        numberTitle.SetNumberEditorRange( 0, 100 );
        numberTitle.SetNumberEditorInterval( 1 );
        numberTitle.SetNumberEditorUsesMouseWheel( false );
        numberTitle.SetHasCustomTextHoverColor( false );
        numberTitle.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        numberTitle.SetFont( "Courier New", 13, true, false );

        setCountYDisplay = new VoltageDigitalCounter( "setCountYDisplay", "Y Set Count", this, 4 );
        AddComponent( setCountYDisplay );
        setCountYDisplay.SetWantsMouseNotifications( false );
        setCountYDisplay.SetPosition( 133, 101 );
        setCountYDisplay.SetSize( 81, 50 );
        setCountYDisplay.SetSkin( "Gray" );
        setCountYDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        remainingCountXDisplay = new VoltageDigitalCounter( "remainingCountXDisplay", "X Remaining Count", this, 4 );
        AddComponent( remainingCountXDisplay );
        remainingCountXDisplay.SetWantsMouseNotifications( false );
        remainingCountXDisplay.SetPosition( 15, 187 );
        remainingCountXDisplay.SetSize( 81, 50 );
        remainingCountXDisplay.SetSkin( "Gray" );
        remainingCountXDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        remainingCountYDisplay = new VoltageDigitalCounter( "remainingCountYDisplay", "Y Remaining Count", this, 4 );
        AddComponent( remainingCountYDisplay );
        remainingCountYDisplay.SetWantsMouseNotifications( false );
        remainingCountYDisplay.SetPosition( 133, 187 );
        remainingCountYDisplay.SetSize( 81, 50 );
        remainingCountYDisplay.SetSkin( "Gray" );
        remainingCountYDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        engageButton = new VoltageButton( "engageButton", "Engage Button", this );
        AddComponent( engageButton );
        engageButton.SetWantsMouseNotifications( false );
        engageButton.SetPosition( 10, 287 );
        engageButton.SetSize( 35, 35 );
        engageButton.SetSkin( "2500 Square Big" );
        engageButton.ShowOverlay( false );
        engageButton.SetOverlayText( "" );
        engageButton.SetAutoRepeat( false );

        resetButton = new VoltageButton( "resetButton", "Reset Button", this );
        AddComponent( resetButton );
        resetButton.SetWantsMouseNotifications( false );
        resetButton.SetPosition( 53, 287 );
        resetButton.SetSize( 35, 35 );
        resetButton.SetSkin( "2500 Square Big" );
        resetButton.ShowOverlay( false );
        resetButton.SetOverlayText( "" );
        resetButton.SetAutoRepeat( false );

        runModeSwitch = new VoltageSwitch( "runModeSwitch", "Run Mode Switch", this, 1 );
        AddComponent( runModeSwitch );
        runModeSwitch.SetWantsMouseNotifications( false );
        runModeSwitch.SetPosition( 167, 287 );
        runModeSwitch.SetSize( 26, 10 );
        runModeSwitch.SetSkin( "Rocker Switch Plastic Cream Hor" );

        outputZ = new VoltageAudioJack( "outputZ", "Z Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputZ );
        outputZ.SetWantsMouseNotifications( false );
        outputZ.SetPosition( 97, 287 );
        outputZ.SetSize( 37, 37 );
        outputZ.SetSkin( "Rotated Half" );

        inputX = new VoltageAudioJack( "inputX", "X Input", this, JackType.JackType_AudioInput );
        AddComponent( inputX );
        inputX.SetWantsMouseNotifications( false );
        inputX.SetPosition( 15, 240 );
        inputX.SetSize( 37, 37 );
        inputX.SetSkin( "Dark Jack Straight" );

        inputY = new VoltageAudioJack( "inputY", "Y Input", this, JackType.JackType_AudioInput );
        AddComponent( inputY );
        inputY.SetWantsMouseNotifications( false );
        inputY.SetPosition( 133, 240 );
        inputY.SetSize( 37, 37 );
        inputY.SetSkin( "Dark Jack Straight" );

        outputX = new VoltageAudioJack( "outputX", "X Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputX );
        outputX.SetWantsMouseNotifications( false );
        outputX.SetPosition( 70, 245 );
        outputX.SetSize( 25, 25 );
        outputX.SetSkin( "Mini Jack 25px" );

        outputY = new VoltageAudioJack( "outputY", "Y Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputY );
        outputY.SetWantsMouseNotifications( false );
        outputY.SetPosition( 188, 245 );
        outputY.SetSize( 25, 25 );
        outputY.SetSkin( "Mini Jack 25px" );

        xSideLabel = new VoltageLabel( "xSideLabel", "X Side Label", this, "X" );
        AddComponent( xSideLabel );
        xSideLabel.SetWantsMouseNotifications( true );
        xSideLabel.SetPosition( 0, 155 );
        xSideLabel.SetSize( 30, 30 );
        xSideLabel.SetEditable( false, false );
        xSideLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        xSideLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        xSideLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        xSideLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        xSideLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        xSideLabel.SetBorderSize( 1 );
        xSideLabel.SetMultiLineEdit( false );
        xSideLabel.SetIsNumberEditor( false );
        xSideLabel.SetNumberEditorRange( 0, 100 );
        xSideLabel.SetNumberEditorInterval( 1 );
        xSideLabel.SetNumberEditorUsesMouseWheel( false );
        xSideLabel.SetHasCustomTextHoverColor( false );
        xSideLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        xSideLabel.SetFont( "Arial", 20, true, false );

        ySideLabel = new VoltageLabel( "ySideLabel", "Y Side Label", this, "Y" );
        AddComponent( ySideLabel );
        ySideLabel.SetWantsMouseNotifications( false );
        ySideLabel.SetPosition( 200, 155 );
        ySideLabel.SetSize( 30, 30 );
        ySideLabel.SetEditable( false, false );
        ySideLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ySideLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ySideLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ySideLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ySideLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ySideLabel.SetBorderSize( 1 );
        ySideLabel.SetMultiLineEdit( false );
        ySideLabel.SetIsNumberEditor( false );
        ySideLabel.SetNumberEditorRange( 0, 100 );
        ySideLabel.SetNumberEditorInterval( 1 );
        ySideLabel.SetNumberEditorUsesMouseWheel( false );
        ySideLabel.SetHasCustomTextHoverColor( false );
        ySideLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ySideLabel.SetFont( "Arial", 20, true, false );

        setXHeading = new VoltageLabel( "setXHeading", "Set X Heading", this, "SET" );
        AddComponent( setXHeading );
        setXHeading.SetWantsMouseNotifications( false );
        setXHeading.SetPosition( 5, 81 );
        setXHeading.SetSize( 101, 23 );
        setXHeading.SetEditable( false, false );
        setXHeading.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        setXHeading.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        setXHeading.SetColor( new Color( 232, 232, 232, 255 ) );
        setXHeading.SetBkColor( new Color( 65, 65, 65, 0 ) );
        setXHeading.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        setXHeading.SetBorderSize( 1 );
        setXHeading.SetMultiLineEdit( false );
        setXHeading.SetIsNumberEditor( false );
        setXHeading.SetNumberEditorRange( 0, 100 );
        setXHeading.SetNumberEditorInterval( 1 );
        setXHeading.SetNumberEditorUsesMouseWheel( false );
        setXHeading.SetHasCustomTextHoverColor( false );
        setXHeading.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        setXHeading.SetFont( "Arial", 9, true, false );

        remainingXHeading = new VoltageLabel( "remainingXHeading", "Remaining X Heading", this, "REMAIN" );
        AddComponent( remainingXHeading );
        remainingXHeading.SetWantsMouseNotifications( false );
        remainingXHeading.SetPosition( 5, 168 );
        remainingXHeading.SetSize( 101, 23 );
        remainingXHeading.SetEditable( false, false );
        remainingXHeading.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        remainingXHeading.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        remainingXHeading.SetColor( new Color( 232, 232, 232, 255 ) );
        remainingXHeading.SetBkColor( new Color( 65, 65, 65, 0 ) );
        remainingXHeading.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        remainingXHeading.SetBorderSize( 1 );
        remainingXHeading.SetMultiLineEdit( false );
        remainingXHeading.SetIsNumberEditor( false );
        remainingXHeading.SetNumberEditorRange( 0, 100 );
        remainingXHeading.SetNumberEditorInterval( 1 );
        remainingXHeading.SetNumberEditorUsesMouseWheel( false );
        remainingXHeading.SetHasCustomTextHoverColor( false );
        remainingXHeading.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        remainingXHeading.SetFont( "Arial", 9, true, false );

        remainingYHeading = new VoltageLabel( "remainingYHeading", "Remaining Y Heading", this, "REMAIN" );
        AddComponent( remainingYHeading );
        remainingYHeading.SetWantsMouseNotifications( false );
        remainingYHeading.SetPosition( 123, 168 );
        remainingYHeading.SetSize( 101, 23 );
        remainingYHeading.SetEditable( false, false );
        remainingYHeading.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        remainingYHeading.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        remainingYHeading.SetColor( new Color( 232, 232, 232, 255 ) );
        remainingYHeading.SetBkColor( new Color( 65, 65, 65, 0 ) );
        remainingYHeading.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        remainingYHeading.SetBorderSize( 1 );
        remainingYHeading.SetMultiLineEdit( false );
        remainingYHeading.SetIsNumberEditor( false );
        remainingYHeading.SetNumberEditorRange( 0, 100 );
        remainingYHeading.SetNumberEditorInterval( 1 );
        remainingYHeading.SetNumberEditorUsesMouseWheel( false );
        remainingYHeading.SetHasCustomTextHoverColor( false );
        remainingYHeading.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        remainingYHeading.SetFont( "Arial", 9, true, false );

        setYHeading = new VoltageLabel( "setYHeading", "Set Y Heading", this, "SET" );
        AddComponent( setYHeading );
        setYHeading.SetWantsMouseNotifications( false );
        setYHeading.SetPosition( 123, 81 );
        setYHeading.SetSize( 101, 23 );
        setYHeading.SetEditable( false, false );
        setYHeading.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        setYHeading.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        setYHeading.SetColor( new Color( 232, 232, 232, 255 ) );
        setYHeading.SetBkColor( new Color( 65, 65, 65, 0 ) );
        setYHeading.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        setYHeading.SetBorderSize( 1 );
        setYHeading.SetMultiLineEdit( false );
        setYHeading.SetIsNumberEditor( false );
        setYHeading.SetNumberEditorRange( 0, 100 );
        setYHeading.SetNumberEditorInterval( 1 );
        setYHeading.SetNumberEditorUsesMouseWheel( false );
        setYHeading.SetHasCustomTextHoverColor( false );
        setYHeading.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        setYHeading.SetFont( "Arial", 9, true, false );

        engageButtonLabel = new VoltageLabel( "engageButtonLabel", "Engage Button Label", this, "ENGAGE" );
        AddComponent( engageButtonLabel );
        engageButtonLabel.SetWantsMouseNotifications( false );
        engageButtonLabel.SetPosition( 7, 322 );
        engageButtonLabel.SetSize( 40, 13 );
        engageButtonLabel.SetEditable( false, false );
        engageButtonLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        engageButtonLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        engageButtonLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        engageButtonLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        engageButtonLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        engageButtonLabel.SetBorderSize( 1 );
        engageButtonLabel.SetMultiLineEdit( false );
        engageButtonLabel.SetIsNumberEditor( false );
        engageButtonLabel.SetNumberEditorRange( 0, 100 );
        engageButtonLabel.SetNumberEditorInterval( 1 );
        engageButtonLabel.SetNumberEditorUsesMouseWheel( false );
        engageButtonLabel.SetHasCustomTextHoverColor( false );
        engageButtonLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        engageButtonLabel.SetFont( "Arial", 6, true, false );

        resetButtonLabel = new VoltageLabel( "resetButtonLabel", "Reset Button Label", this, "RESET" );
        AddComponent( resetButtonLabel );
        resetButtonLabel.SetWantsMouseNotifications( false );
        resetButtonLabel.SetPosition( 50, 322 );
        resetButtonLabel.SetSize( 40, 13 );
        resetButtonLabel.SetEditable( false, false );
        resetButtonLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        resetButtonLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        resetButtonLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        resetButtonLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        resetButtonLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        resetButtonLabel.SetBorderSize( 1 );
        resetButtonLabel.SetMultiLineEdit( false );
        resetButtonLabel.SetIsNumberEditor( false );
        resetButtonLabel.SetNumberEditorRange( 0, 100 );
        resetButtonLabel.SetNumberEditorInterval( 1 );
        resetButtonLabel.SetNumberEditorUsesMouseWheel( false );
        resetButtonLabel.SetHasCustomTextHoverColor( false );
        resetButtonLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        resetButtonLabel.SetFont( "Arial", 6, true, false );

        oneShotLabel = new VoltageLabel( "oneShotLabel", "One Shot Label", this, "1 SHOT" );
        AddComponent( oneShotLabel );
        oneShotLabel.SetWantsMouseNotifications( false );
        oneShotLabel.SetPosition( 145, 287 );
        oneShotLabel.SetSize( 20, 10 );
        oneShotLabel.SetEditable( false, false );
        oneShotLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        oneShotLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        oneShotLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        oneShotLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        oneShotLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        oneShotLabel.SetBorderSize( 1 );
        oneShotLabel.SetMultiLineEdit( false );
        oneShotLabel.SetIsNumberEditor( false );
        oneShotLabel.SetNumberEditorRange( 0, 100 );
        oneShotLabel.SetNumberEditorInterval( 1 );
        oneShotLabel.SetNumberEditorUsesMouseWheel( false );
        oneShotLabel.SetHasCustomTextHoverColor( false );
        oneShotLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        oneShotLabel.SetFont( "Arial", 8, true, false );

        loopLabel = new VoltageLabel( "loopLabel", "Loop Label", this, "LOOP" );
        AddComponent( loopLabel );
        loopLabel.SetWantsMouseNotifications( false );
        loopLabel.SetPosition( 194, 287 );
        loopLabel.SetSize( 20, 10 );
        loopLabel.SetEditable( false, false );
        loopLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        loopLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        loopLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        loopLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        loopLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        loopLabel.SetBorderSize( 1 );
        loopLabel.SetMultiLineEdit( false );
        loopLabel.SetIsNumberEditor( false );
        loopLabel.SetNumberEditorRange( 0, 100 );
        loopLabel.SetNumberEditorInterval( 1 );
        loopLabel.SetNumberEditorUsesMouseWheel( false );
        loopLabel.SetHasCustomTextHoverColor( false );
        loopLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        loopLabel.SetFont( "Arial", 8, true, false );

        outputZLabel = new VoltageLabel( "outputZLabel", "Output Z Label", this, "Z" );
        AddComponent( outputZLabel );
        outputZLabel.SetWantsMouseNotifications( false );
        outputZLabel.SetPosition( 95, 325 );
        outputZLabel.SetSize( 40, 13 );
        outputZLabel.SetEditable( false, false );
        outputZLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        outputZLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        outputZLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        outputZLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        outputZLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        outputZLabel.SetBorderSize( 1 );
        outputZLabel.SetMultiLineEdit( false );
        outputZLabel.SetIsNumberEditor( false );
        outputZLabel.SetNumberEditorRange( 0, 100 );
        outputZLabel.SetNumberEditorInterval( 1 );
        outputZLabel.SetNumberEditorUsesMouseWheel( false );
        outputZLabel.SetHasCustomTextHoverColor( false );
        outputZLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        outputZLabel.SetFont( "Arial", 13, true, false );

        xInputLabel = new VoltageLabel( "xInputLabel", "X Input Label", this, "I" );
        AddComponent( xInputLabel );
        xInputLabel.SetWantsMouseNotifications( false );
        xInputLabel.SetPosition( 0, 245 );
        xInputLabel.SetSize( 25, 25 );
        xInputLabel.SetEditable( false, false );
        xInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        xInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        xInputLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        xInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        xInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        xInputLabel.SetBorderSize( 1 );
        xInputLabel.SetMultiLineEdit( false );
        xInputLabel.SetIsNumberEditor( false );
        xInputLabel.SetNumberEditorRange( 0, 100 );
        xInputLabel.SetNumberEditorInterval( 1 );
        xInputLabel.SetNumberEditorUsesMouseWheel( false );
        xInputLabel.SetHasCustomTextHoverColor( false );
        xInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        xInputLabel.SetFont( "Arial", 9, true, false );

        xOutputLabel = new VoltageLabel( "xOutputLabel", "X Output Label", this, "O" );
        AddComponent( xOutputLabel );
        xOutputLabel.SetWantsMouseNotifications( false );
        xOutputLabel.SetPosition( 90, 245 );
        xOutputLabel.SetSize( 25, 25 );
        xOutputLabel.SetEditable( false, false );
        xOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        xOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        xOutputLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        xOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        xOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        xOutputLabel.SetBorderSize( 1 );
        xOutputLabel.SetMultiLineEdit( false );
        xOutputLabel.SetIsNumberEditor( false );
        xOutputLabel.SetNumberEditorRange( 0, 100 );
        xOutputLabel.SetNumberEditorInterval( 1 );
        xOutputLabel.SetNumberEditorUsesMouseWheel( false );
        xOutputLabel.SetHasCustomTextHoverColor( false );
        xOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        xOutputLabel.SetFont( "Arial", 9, true, false );

        countXThousands = new VoltageKnob( "countXThousands", "Count X Thousands", this, 0, 9, 0 );
        AddComponent( countXThousands );
        countXThousands.SetWantsMouseNotifications( false );
        countXThousands.SetPosition( 25, 155 );
        countXThousands.SetSize( 13, 13 );
        countXThousands.SetSkin( "Plastic Mint" );
        countXThousands.SetRange( 0, 9, 0, false, 10 );
        countXThousands.SetKnobParams( 215, 145 );
        countXThousands.DisplayValueInPercent( false );
        countXThousands.SetKnobAdjustsRing( true );

        yOutputLabel = new VoltageLabel( "yOutputLabel", "Y Output Label", this, "O" );
        AddComponent( yOutputLabel );
        yOutputLabel.SetWantsMouseNotifications( false );
        yOutputLabel.SetPosition( 210, 245 );
        yOutputLabel.SetSize( 20, 25 );
        yOutputLabel.SetEditable( false, false );
        yOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        yOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        yOutputLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        yOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        yOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        yOutputLabel.SetBorderSize( 1 );
        yOutputLabel.SetMultiLineEdit( false );
        yOutputLabel.SetIsNumberEditor( false );
        yOutputLabel.SetNumberEditorRange( 0, 100 );
        yOutputLabel.SetNumberEditorInterval( 1 );
        yOutputLabel.SetNumberEditorUsesMouseWheel( false );
        yOutputLabel.SetHasCustomTextHoverColor( false );
        yOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        yOutputLabel.SetFont( "Arial", 9, true, false );

        yInputLabel = new VoltageLabel( "yInputLabel", "Y Input Label", this, "I" );
        AddComponent( yInputLabel );
        yInputLabel.SetWantsMouseNotifications( false );
        yInputLabel.SetPosition( 118, 245 );
        yInputLabel.SetSize( 25, 25 );
        yInputLabel.SetEditable( false, false );
        yInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        yInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        yInputLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        yInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        yInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        yInputLabel.SetBorderSize( 1 );
        yInputLabel.SetMultiLineEdit( false );
        yInputLabel.SetIsNumberEditor( false );
        yInputLabel.SetNumberEditorRange( 0, 100 );
        yInputLabel.SetNumberEditorInterval( 1 );
        yInputLabel.SetNumberEditorUsesMouseWheel( false );
        yInputLabel.SetHasCustomTextHoverColor( false );
        yInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        yInputLabel.SetFont( "Arial", 9, true, false );

        countXOnes = new VoltageKnob( "countXOnes", "Count X Ones", this, 0, 9, 0 );
        AddComponent( countXOnes );
        countXOnes.SetWantsMouseNotifications( false );
        countXOnes.SetPosition( 70, 155 );
        countXOnes.SetSize( 13, 13 );
        countXOnes.SetSkin( "Plastic Mint" );
        countXOnes.SetRange( 0, 9, 0, false, 10 );
        countXOnes.SetKnobParams( 215, 145 );
        countXOnes.DisplayValueInPercent( false );
        countXOnes.SetKnobAdjustsRing( true );

        countXHundreds = new VoltageKnob( "countXHundreds", "Count X Hundreds", this, 0, 9, 0 );
        AddComponent( countXHundreds );
        countXHundreds.SetWantsMouseNotifications( false );
        countXHundreds.SetPosition( 40, 155 );
        countXHundreds.SetSize( 13, 13 );
        countXHundreds.SetSkin( "Plastic Mint" );
        countXHundreds.SetRange( 0, 9, 0, false, 10 );
        countXHundreds.SetKnobParams( 215, 145 );
        countXHundreds.DisplayValueInPercent( false );
        countXHundreds.SetKnobAdjustsRing( true );

        countYThousands = new VoltageKnob( "countYThousands", "Count Y Thousands", this, 0, 9, 0 );
        AddComponent( countYThousands );
        countYThousands.SetWantsMouseNotifications( false );
        countYThousands.SetPosition( 143, 155 );
        countYThousands.SetSize( 13, 13 );
        countYThousands.SetSkin( "Plastic Red" );
        countYThousands.SetRange( 0, 9, 0, false, 10 );
        countYThousands.SetKnobParams( 215, 145 );
        countYThousands.DisplayValueInPercent( false );
        countYThousands.SetKnobAdjustsRing( true );

        countYHundreds = new VoltageKnob( "countYHundreds", "Count Y Hundreds", this, 0, 9, 0 );
        AddComponent( countYHundreds );
        countYHundreds.SetWantsMouseNotifications( false );
        countYHundreds.SetPosition( 158, 155 );
        countYHundreds.SetSize( 13, 13 );
        countYHundreds.SetSkin( "Plastic Red" );
        countYHundreds.SetRange( 0, 9, 0, false, 10 );
        countYHundreds.SetKnobParams( 215, 145 );
        countYHundreds.DisplayValueInPercent( false );
        countYHundreds.SetKnobAdjustsRing( true );

        countYTens = new VoltageKnob( "countYTens", "Count Y Tens", this, 0, 9, 0 );
        AddComponent( countYTens );
        countYTens.SetWantsMouseNotifications( false );
        countYTens.SetPosition( 173, 155 );
        countYTens.SetSize( 13, 13 );
        countYTens.SetSkin( "Plastic Red" );
        countYTens.SetRange( 0, 9, 0, false, 10 );
        countYTens.SetKnobParams( 215, 145 );
        countYTens.DisplayValueInPercent( false );
        countYTens.SetKnobAdjustsRing( true );

        countYOnes = new VoltageKnob( "countYOnes", "Count Y Ones", this, 0, 9, 0 );
        AddComponent( countYOnes );
        countYOnes.SetWantsMouseNotifications( false );
        countYOnes.SetPosition( 188, 155 );
        countYOnes.SetSize( 13, 13 );
        countYOnes.SetSkin( "Plastic Red" );
        countYOnes.SetRange( 0, 9, 0, false, 10 );
        countYOnes.SetKnobParams( 215, 145 );
        countYOnes.DisplayValueInPercent( false );
        countYOnes.SetKnobAdjustsRing( true );

        countXTens = new VoltageKnob( "countXTens", "Count X Tens", this, 0, 9, 0 );
        AddComponent( countXTens );
        countXTens.SetWantsMouseNotifications( false );
        countXTens.SetPosition( 55, 155 );
        countXTens.SetSize( 13, 13 );
        countXTens.SetSkin( "Plastic Mint" );
        countXTens.SetRange( 0, 9, 0, false, 10 );
        countXTens.SetKnobParams( 215, 145 );
        countXTens.DisplayValueInPercent( false );
        countXTens.SetKnobAdjustsRing( true );

        ratioKnob = new VoltageKnob( "ratioKnob", "Clock Ratio", this, 1, 11, 6 );
        AddComponent( ratioKnob );
        ratioKnob.SetWantsMouseNotifications( false );
        ratioKnob.SetPosition( 143, 35 );
        ratioKnob.SetSize( 35, 35 );
        ratioKnob.SetSkin( "Cosmo v2 Pointer" );
        ratioKnob.SetRange( 1, 11, 6, false, 11 );
        ratioKnob.SetKnobParams( 280, 80 );
        ratioKnob.DisplayValueInPercent( false );
        ratioKnob.SetKnobAdjustsRing( true );

        externalClockInput = new VoltageAudioJack( "externalClockInput", "External Clock Input", this, JackType.JackType_AudioInput );
        AddComponent( externalClockInput );
        externalClockInput.SetWantsMouseNotifications( false );
        externalClockInput.SetPosition( 187, 33 );
        externalClockInput.SetSize( 37, 37 );
        externalClockInput.SetSkin( "Jack Round" );

        ratioUnityLabel = new VoltageLabel( "ratioUnityLabel", "Ratio x1", this, "x1" );
        AddComponent( ratioUnityLabel );
        ratioUnityLabel.SetWantsMouseNotifications( false );
        ratioUnityLabel.SetPosition( 156, 26 );
        ratioUnityLabel.SetSize( 10, 10 );
        ratioUnityLabel.SetEditable( false, false );
        ratioUnityLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioUnityLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioUnityLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioUnityLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioUnityLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioUnityLabel.SetBorderSize( 1 );
        ratioUnityLabel.SetMultiLineEdit( false );
        ratioUnityLabel.SetIsNumberEditor( false );
        ratioUnityLabel.SetNumberEditorRange( 0, 100 );
        ratioUnityLabel.SetNumberEditorInterval( 1 );
        ratioUnityLabel.SetNumberEditorUsesMouseWheel( false );
        ratioUnityLabel.SetHasCustomTextHoverColor( false );
        ratioUnityLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioUnityLabel.SetFont( "Arial", 5, true, false );

        ratio1618Label = new VoltageLabel( "ratio1618Label", "Ratio x1.618", this, "x1.618" );
        AddComponent( ratio1618Label );
        ratio1618Label.SetWantsMouseNotifications( false );
        ratio1618Label.SetPosition( 163, 27 );
        ratio1618Label.SetSize( 12, 10 );
        ratio1618Label.SetEditable( false, false );
        ratio1618Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratio1618Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratio1618Label.SetColor( new Color( 147, 147, 147, 255 ) );
        ratio1618Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratio1618Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratio1618Label.SetBorderSize( 1 );
        ratio1618Label.SetMultiLineEdit( false );
        ratio1618Label.SetIsNumberEditor( false );
        ratio1618Label.SetNumberEditorRange( 0, 100 );
        ratio1618Label.SetNumberEditorInterval( 1 );
        ratio1618Label.SetNumberEditorUsesMouseWheel( false );
        ratio1618Label.SetHasCustomTextHoverColor( false );
        ratio1618Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratio1618Label.SetFont( "Arial", 4, true, false );

        ratioFourThirdsLabel = new VoltageLabel( "ratioFourThirdsLabel", "Ratio ÷1.333", this, "÷1.333" );
        AddComponent( ratioFourThirdsLabel );
        ratioFourThirdsLabel.SetWantsMouseNotifications( false );
        ratioFourThirdsLabel.SetPosition( 147, 27 );
        ratioFourThirdsLabel.SetSize( 11, 10 );
        ratioFourThirdsLabel.SetEditable( false, false );
        ratioFourThirdsLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioFourThirdsLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioFourThirdsLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioFourThirdsLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioFourThirdsLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioFourThirdsLabel.SetBorderSize( 1 );
        ratioFourThirdsLabel.SetMultiLineEdit( false );
        ratioFourThirdsLabel.SetIsNumberEditor( false );
        ratioFourThirdsLabel.SetNumberEditorRange( 0, 100 );
        ratioFourThirdsLabel.SetNumberEditorInterval( 1 );
        ratioFourThirdsLabel.SetNumberEditorUsesMouseWheel( false );
        ratioFourThirdsLabel.SetHasCustomTextHoverColor( false );
        ratioFourThirdsLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioFourThirdsLabel.SetFont( "Arial", 4, true, false );

        ratioThreeHalvesLabel = new VoltageLabel( "ratioThreeHalvesLabel", "Ratio ÷1.5", this, "÷1.5" );
        AddComponent( ratioThreeHalvesLabel );
        ratioThreeHalvesLabel.SetWantsMouseNotifications( false );
        ratioThreeHalvesLabel.SetPosition( 141, 31 );
        ratioThreeHalvesLabel.SetSize( 10, 10 );
        ratioThreeHalvesLabel.SetEditable( false, false );
        ratioThreeHalvesLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioThreeHalvesLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioThreeHalvesLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioThreeHalvesLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioThreeHalvesLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioThreeHalvesLabel.SetBorderSize( 1 );
        ratioThreeHalvesLabel.SetMultiLineEdit( false );
        ratioThreeHalvesLabel.SetIsNumberEditor( false );
        ratioThreeHalvesLabel.SetNumberEditorRange( 0, 100 );
        ratioThreeHalvesLabel.SetNumberEditorInterval( 1 );
        ratioThreeHalvesLabel.SetNumberEditorUsesMouseWheel( false );
        ratioThreeHalvesLabel.SetHasCustomTextHoverColor( false );
        ratioThreeHalvesLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioThreeHalvesLabel.SetFont( "Arial", 5, true, false );

        ratioEightLabel = new VoltageLabel( "ratioEightLabel", "Ratio ÷8", this, "÷8" );
        AddComponent( ratioEightLabel );
        ratioEightLabel.SetWantsMouseNotifications( false );
        ratioEightLabel.SetPosition( 134, 45 );
        ratioEightLabel.SetSize( 10, 10 );
        ratioEightLabel.SetEditable( false, false );
        ratioEightLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioEightLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioEightLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioEightLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioEightLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioEightLabel.SetBorderSize( 1 );
        ratioEightLabel.SetMultiLineEdit( false );
        ratioEightLabel.SetIsNumberEditor( false );
        ratioEightLabel.SetNumberEditorRange( 0, 100 );
        ratioEightLabel.SetNumberEditorInterval( 1 );
        ratioEightLabel.SetNumberEditorUsesMouseWheel( false );
        ratioEightLabel.SetHasCustomTextHoverColor( false );
        ratioEightLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioEightLabel.SetFont( "Arial", 5, true, false );

        ratioHalfLabel = new VoltageLabel( "ratioHalfLabel", "Ratio ÷2", this, "÷2" );
        AddComponent( ratioHalfLabel );
        ratioHalfLabel.SetWantsMouseNotifications( false );
        ratioHalfLabel.SetPosition( 136, 35 );
        ratioHalfLabel.SetSize( 10, 10 );
        ratioHalfLabel.SetEditable( false, false );
        ratioHalfLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioHalfLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioHalfLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioHalfLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioHalfLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioHalfLabel.SetBorderSize( 1 );
        ratioHalfLabel.SetMultiLineEdit( false );
        ratioHalfLabel.SetIsNumberEditor( false );
        ratioHalfLabel.SetNumberEditorRange( 0, 100 );
        ratioHalfLabel.SetNumberEditorInterval( 1 );
        ratioHalfLabel.SetNumberEditorUsesMouseWheel( false );
        ratioHalfLabel.SetHasCustomTextHoverColor( false );
        ratioHalfLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioHalfLabel.SetFont( "Arial", 5, true, false );

        ratioFourLabel = new VoltageLabel( "ratioFourLabel", "Ratio ÷4", this, "÷4" );
        AddComponent( ratioFourLabel );
        ratioFourLabel.SetWantsMouseNotifications( false );
        ratioFourLabel.SetPosition( 134, 40 );
        ratioFourLabel.SetSize( 10, 10 );
        ratioFourLabel.SetEditable( false, false );
        ratioFourLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioFourLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioFourLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioFourLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioFourLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioFourLabel.SetBorderSize( 1 );
        ratioFourLabel.SetMultiLineEdit( false );
        ratioFourLabel.SetIsNumberEditor( false );
        ratioFourLabel.SetNumberEditorRange( 0, 100 );
        ratioFourLabel.SetNumberEditorInterval( 1 );
        ratioFourLabel.SetNumberEditorUsesMouseWheel( false );
        ratioFourLabel.SetHasCustomTextHoverColor( false );
        ratioFourLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioFourLabel.SetFont( "Arial", 5, true, false );

        ratioTwoLabel = new VoltageLabel( "ratioTwoLabel", "Ratio x2", this, "x2" );
        AddComponent( ratioTwoLabel );
        ratioTwoLabel.SetWantsMouseNotifications( false );
        ratioTwoLabel.SetPosition( 170, 31 );
        ratioTwoLabel.SetSize( 10, 10 );
        ratioTwoLabel.SetEditable( false, false );
        ratioTwoLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioTwoLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioTwoLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioTwoLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioTwoLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioTwoLabel.SetBorderSize( 1 );
        ratioTwoLabel.SetMultiLineEdit( false );
        ratioTwoLabel.SetIsNumberEditor( false );
        ratioTwoLabel.SetNumberEditorRange( 0, 100 );
        ratioTwoLabel.SetNumberEditorInterval( 1 );
        ratioTwoLabel.SetNumberEditorUsesMouseWheel( false );
        ratioTwoLabel.SetHasCustomTextHoverColor( false );
        ratioTwoLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioTwoLabel.SetFont( "Arial", 5, true, false );

        ratioFiveLabel = new VoltageLabel( "ratioFiveLabel", "Ratio x5", this, "x5" );
        AddComponent( ratioFiveLabel );
        ratioFiveLabel.SetWantsMouseNotifications( false );
        ratioFiveLabel.SetPosition( 176, 40 );
        ratioFiveLabel.SetSize( 10, 10 );
        ratioFiveLabel.SetEditable( false, false );
        ratioFiveLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioFiveLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioFiveLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioFiveLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioFiveLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioFiveLabel.SetBorderSize( 1 );
        ratioFiveLabel.SetMultiLineEdit( false );
        ratioFiveLabel.SetIsNumberEditor( false );
        ratioFiveLabel.SetNumberEditorRange( 0, 100 );
        ratioFiveLabel.SetNumberEditorInterval( 1 );
        ratioFiveLabel.SetNumberEditorUsesMouseWheel( false );
        ratioFiveLabel.SetHasCustomTextHoverColor( false );
        ratioFiveLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioFiveLabel.SetFont( "Arial", 5, true, false );

        ratioSevenLabel = new VoltageLabel( "ratioSevenLabel", "Ratio x7", this, "x7" );
        AddComponent( ratioSevenLabel );
        ratioSevenLabel.SetWantsMouseNotifications( false );
        ratioSevenLabel.SetPosition( 177, 45 );
        ratioSevenLabel.SetSize( 10, 10 );
        ratioSevenLabel.SetEditable( false, false );
        ratioSevenLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioSevenLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioSevenLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioSevenLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioSevenLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioSevenLabel.SetBorderSize( 1 );
        ratioSevenLabel.SetMultiLineEdit( false );
        ratioSevenLabel.SetIsNumberEditor( false );
        ratioSevenLabel.SetNumberEditorRange( 0, 100 );
        ratioSevenLabel.SetNumberEditorInterval( 1 );
        ratioSevenLabel.SetNumberEditorUsesMouseWheel( false );
        ratioSevenLabel.SetHasCustomTextHoverColor( false );
        ratioSevenLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioSevenLabel.SetFont( "Arial", 5, true, false );

        ratioThreeLabel = new VoltageLabel( "ratioThreeLabel", "Ratio x3", this, "x3" );
        AddComponent( ratioThreeLabel );
        ratioThreeLabel.SetWantsMouseNotifications( false );
        ratioThreeLabel.SetPosition( 174, 35 );
        ratioThreeLabel.SetSize( 10, 10 );
        ratioThreeLabel.SetEditable( false, false );
        ratioThreeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioThreeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioThreeLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioThreeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioThreeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioThreeLabel.SetBorderSize( 1 );
        ratioThreeLabel.SetMultiLineEdit( false );
        ratioThreeLabel.SetIsNumberEditor( false );
        ratioThreeLabel.SetNumberEditorRange( 0, 100 );
        ratioThreeLabel.SetNumberEditorInterval( 1 );
        ratioThreeLabel.SetNumberEditorUsesMouseWheel( false );
        ratioThreeLabel.SetHasCustomTextHoverColor( false );
        ratioThreeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioThreeLabel.SetFont( "Arial", 5, true, false );

        ratioKnobLabel = new VoltageLabel( "ratioKnobLabel", "Ratio Knob Label", this, "RATIO" );
        AddComponent( ratioKnobLabel );
        ratioKnobLabel.SetWantsMouseNotifications( false );
        ratioKnobLabel.SetPosition( 146, 70 );
        ratioKnobLabel.SetSize( 30, 13 );
        ratioKnobLabel.SetEditable( false, false );
        ratioKnobLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioKnobLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioKnobLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        ratioKnobLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioKnobLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioKnobLabel.SetBorderSize( 1 );
        ratioKnobLabel.SetMultiLineEdit( false );
        ratioKnobLabel.SetIsNumberEditor( false );
        ratioKnobLabel.SetNumberEditorRange( 0, 100 );
        ratioKnobLabel.SetNumberEditorInterval( 1 );
        ratioKnobLabel.SetNumberEditorUsesMouseWheel( false );
        ratioKnobLabel.SetHasCustomTextHoverColor( false );
        ratioKnobLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioKnobLabel.SetFont( "Arial", 6, true, false );

        externalClockLabel = new VoltageLabel( "externalClockLabel", "External Clock Label", this, "EXTERNAL" );
        AddComponent( externalClockLabel );
        externalClockLabel.SetWantsMouseNotifications( false );
        externalClockLabel.SetPosition( 190, 70 );
        externalClockLabel.SetSize( 30, 13 );
        externalClockLabel.SetEditable( false, false );
        externalClockLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        externalClockLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        externalClockLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        externalClockLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        externalClockLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        externalClockLabel.SetBorderSize( 1 );
        externalClockLabel.SetMultiLineEdit( false );
        externalClockLabel.SetIsNumberEditor( false );
        externalClockLabel.SetNumberEditorRange( 0, 100 );
        externalClockLabel.SetNumberEditorInterval( 1 );
        externalClockLabel.SetNumberEditorUsesMouseWheel( false );
        externalClockLabel.SetHasCustomTextHoverColor( false );
        externalClockLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        externalClockLabel.SetFont( "Arial", 6, true, false );

        clockSourceSwitch = new VoltageSwitch( "clockSourceSwitch", "Clock Source Switch", this, 0 );
        AddComponent( clockSourceSwitch );
        clockSourceSwitch.SetWantsMouseNotifications( false );
        clockSourceSwitch.SetPosition( 24, 70 );
        clockSourceSwitch.SetSize( 26, 10 );
        clockSourceSwitch.SetSkin( "Rocker Switch Plastic Black Hor" );

        internalClockSourceLabel = new VoltageLabel( "internalClockSourceLabel", "Internal Clock Source Label", this, "INT" );
        AddComponent( internalClockSourceLabel );
        internalClockSourceLabel.SetWantsMouseNotifications( false );
        internalClockSourceLabel.SetPosition( 8, 70 );
        internalClockSourceLabel.SetSize( 20, 10 );
        internalClockSourceLabel.SetEditable( false, false );
        internalClockSourceLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        internalClockSourceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        internalClockSourceLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        internalClockSourceLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        internalClockSourceLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        internalClockSourceLabel.SetBorderSize( 1 );
        internalClockSourceLabel.SetMultiLineEdit( false );
        internalClockSourceLabel.SetIsNumberEditor( false );
        internalClockSourceLabel.SetNumberEditorRange( 0, 100 );
        internalClockSourceLabel.SetNumberEditorInterval( 1 );
        internalClockSourceLabel.SetNumberEditorUsesMouseWheel( false );
        internalClockSourceLabel.SetHasCustomTextHoverColor( false );
        internalClockSourceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        internalClockSourceLabel.SetFont( "Arial", 5, true, false );

        externalClockSourceLabel = new VoltageLabel( "externalClockSourceLabel", "External Clock Source Label", this, "EXT" );
        AddComponent( externalClockSourceLabel );
        externalClockSourceLabel.SetWantsMouseNotifications( false );
        externalClockSourceLabel.SetPosition( 48, 70 );
        externalClockSourceLabel.SetSize( 20, 10 );
        externalClockSourceLabel.SetEditable( false, false );
        externalClockSourceLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        externalClockSourceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        externalClockSourceLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        externalClockSourceLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        externalClockSourceLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        externalClockSourceLabel.SetBorderSize( 1 );
        externalClockSourceLabel.SetMultiLineEdit( false );
        externalClockSourceLabel.SetIsNumberEditor( false );
        externalClockSourceLabel.SetNumberEditorRange( 0, 100 );
        externalClockSourceLabel.SetNumberEditorInterval( 1 );
        externalClockSourceLabel.SetNumberEditorUsesMouseWheel( false );
        externalClockSourceLabel.SetHasCustomTextHoverColor( false );
        externalClockSourceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        externalClockSourceLabel.SetFont( "Arial", 5, true, false );

        internalClockKnob = new VoltageKnob( "internalClockKnob", "Internal Clock", this, 0.0, 1.0, 0.5 );
        AddComponent( internalClockKnob );
        internalClockKnob.SetWantsMouseNotifications( false );
        internalClockKnob.SetPosition( 21, 28 );
        internalClockKnob.SetSize( 35, 35 );
        internalClockKnob.SetSkin( "Cosmo Medium" );
        internalClockKnob.SetRange( 0.0, 1.0, 0.5, false, 0 );
        internalClockKnob.SetKnobParams( 215, 145 );
        internalClockKnob.DisplayValueInPercent( false );
        internalClockKnob.SetKnobAdjustsRing( true );

        internalClockKnobLabel = new VoltageLabel( "internalClockKnobLabel", "Internal Clock Knob Label", this, "CLOCK" );
        AddComponent( internalClockKnobLabel );
        internalClockKnobLabel.SetWantsMouseNotifications( false );
        internalClockKnobLabel.SetPosition( 22, 59 );
        internalClockKnobLabel.SetSize( 30, 13 );
        internalClockKnobLabel.SetEditable( false, false );
        internalClockKnobLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        internalClockKnobLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        internalClockKnobLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        internalClockKnobLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        internalClockKnobLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        internalClockKnobLabel.SetBorderSize( 1 );
        internalClockKnobLabel.SetMultiLineEdit( false );
        internalClockKnobLabel.SetIsNumberEditor( false );
        internalClockKnobLabel.SetNumberEditorRange( 0, 100 );
        internalClockKnobLabel.SetNumberEditorInterval( 1 );
        internalClockKnobLabel.SetNumberEditorUsesMouseWheel( false );
        internalClockKnobLabel.SetHasCustomTextHoverColor( false );
        internalClockKnobLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        internalClockKnobLabel.SetFont( "Arial", 5, true, false );

        ratioDestinationSwitch = new VoltageSwitch( "ratioDestinationSwitch", "Ratio Destination Switch", this, 0 );
        AddComponent( ratioDestinationSwitch );
        ratioDestinationSwitch.SetWantsMouseNotifications( false );
        ratioDestinationSwitch.SetPosition( 121, 45 );
        ratioDestinationSwitch.SetSize( 8, 15 );
        ratioDestinationSwitch.SetSkin( "2-State Slide Vertical" );

        clockRangeSwitch = new VoltageSwitch( "clockRangeSwitch", "Clock Range Switch", this, 1 );
        AddComponent( clockRangeSwitch );
        clockRangeSwitch.SetWantsMouseNotifications( false );
        clockRangeSwitch.SetPosition( 7, 45 );
        clockRangeSwitch.SetSize( 8, 20 );
        clockRangeSwitch.SetSkin( "3-State Slide Vertical" );

        xGateModeLabel = new VoltageLabel( "xGateModeLabel", "X Gate Mode Label", this, "GATE" );
        AddComponent( xGateModeLabel );
        xGateModeLabel.SetWantsMouseNotifications( false );
        xGateModeLabel.SetPosition( 49, 265 );
        xGateModeLabel.SetSize( 20, 10 );
        xGateModeLabel.SetEditable( false, false );
        xGateModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        xGateModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        xGateModeLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        xGateModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        xGateModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        xGateModeLabel.SetBorderSize( 1 );
        xGateModeLabel.SetMultiLineEdit( false );
        xGateModeLabel.SetIsNumberEditor( false );
        xGateModeLabel.SetNumberEditorRange( 0, 100 );
        xGateModeLabel.SetNumberEditorInterval( 1 );
        xGateModeLabel.SetNumberEditorUsesMouseWheel( false );
        xGateModeLabel.SetHasCustomTextHoverColor( false );
        xGateModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        xGateModeLabel.SetFont( "Arial", 4, true, false );

        yGateModeLabel = new VoltageLabel( "yGateModeLabel", "Y Gate Mode Label", this, "GATE" );
        AddComponent( yGateModeLabel );
        yGateModeLabel.SetWantsMouseNotifications( false );
        yGateModeLabel.SetPosition( 169, 265 );
        yGateModeLabel.SetSize( 20, 10 );
        yGateModeLabel.SetEditable( false, false );
        yGateModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        yGateModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        yGateModeLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        yGateModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        yGateModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        yGateModeLabel.SetBorderSize( 1 );
        yGateModeLabel.SetMultiLineEdit( false );
        yGateModeLabel.SetIsNumberEditor( false );
        yGateModeLabel.SetNumberEditorRange( 0, 100 );
        yGateModeLabel.SetNumberEditorInterval( 1 );
        yGateModeLabel.SetNumberEditorUsesMouseWheel( false );
        yGateModeLabel.SetHasCustomTextHoverColor( false );
        yGateModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        yGateModeLabel.SetFont( "Arial", 4, true, false );

        ratioExternalModeLabel = new VoltageLabel( "ratioExternalModeLabel", "Ratio External Mode Label", this, "EXT" );
        AddComponent( ratioExternalModeLabel );
        ratioExternalModeLabel.SetWantsMouseNotifications( false );
        ratioExternalModeLabel.SetPosition( 115, 58 );
        ratioExternalModeLabel.SetSize( 20, 10 );
        ratioExternalModeLabel.SetEditable( false, false );
        ratioExternalModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioExternalModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioExternalModeLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioExternalModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioExternalModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioExternalModeLabel.SetBorderSize( 1 );
        ratioExternalModeLabel.SetMultiLineEdit( false );
        ratioExternalModeLabel.SetIsNumberEditor( false );
        ratioExternalModeLabel.SetNumberEditorRange( 0, 100 );
        ratioExternalModeLabel.SetNumberEditorInterval( 1 );
        ratioExternalModeLabel.SetNumberEditorUsesMouseWheel( false );
        ratioExternalModeLabel.SetHasCustomTextHoverColor( false );
        ratioExternalModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioExternalModeLabel.SetFont( "Arial", 4, true, false );

        ratioBothModeLabel = new VoltageLabel( "ratioBothModeLabel", "Ratio Both Mode Label", this, "BOTH" );
        AddComponent( ratioBothModeLabel );
        ratioBothModeLabel.SetWantsMouseNotifications( false );
        ratioBothModeLabel.SetPosition( 115, 37 );
        ratioBothModeLabel.SetSize( 20, 10 );
        ratioBothModeLabel.SetEditable( false, false );
        ratioBothModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        ratioBothModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        ratioBothModeLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        ratioBothModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        ratioBothModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        ratioBothModeLabel.SetBorderSize( 1 );
        ratioBothModeLabel.SetMultiLineEdit( false );
        ratioBothModeLabel.SetIsNumberEditor( false );
        ratioBothModeLabel.SetNumberEditorRange( 0, 100 );
        ratioBothModeLabel.SetNumberEditorInterval( 1 );
        ratioBothModeLabel.SetNumberEditorUsesMouseWheel( false );
        ratioBothModeLabel.SetHasCustomTextHoverColor( false );
        ratioBothModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        ratioBothModeLabel.SetFont( "Arial", 4, true, false );

        yPassModeLabel = new VoltageLabel( "yPassModeLabel", "Y Pass Mode Label", this, "PASS" );
        AddComponent( yPassModeLabel );
        yPassModeLabel.SetWantsMouseNotifications( false );
        yPassModeLabel.SetPosition( 169, 243 );
        yPassModeLabel.SetSize( 20, 10 );
        yPassModeLabel.SetEditable( false, false );
        yPassModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        yPassModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        yPassModeLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        yPassModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        yPassModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        yPassModeLabel.SetBorderSize( 1 );
        yPassModeLabel.SetMultiLineEdit( false );
        yPassModeLabel.SetIsNumberEditor( false );
        yPassModeLabel.SetNumberEditorRange( 0, 100 );
        yPassModeLabel.SetNumberEditorInterval( 1 );
        yPassModeLabel.SetNumberEditorUsesMouseWheel( false );
        yPassModeLabel.SetHasCustomTextHoverColor( false );
        yPassModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        yPassModeLabel.SetFont( "Arial", 4, true, false );

        clockRangeSwitchLabel = new VoltageLabel( "clockRangeSwitchLabel", "Clock Range Switch Label", this, "RANGE" );
        AddComponent( clockRangeSwitchLabel );
        clockRangeSwitchLabel.SetWantsMouseNotifications( false );
        clockRangeSwitchLabel.SetPosition( 1, 63 );
        clockRangeSwitchLabel.SetSize( 20, 10 );
        clockRangeSwitchLabel.SetEditable( false, false );
        clockRangeSwitchLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        clockRangeSwitchLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        clockRangeSwitchLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        clockRangeSwitchLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        clockRangeSwitchLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        clockRangeSwitchLabel.SetBorderSize( 1 );
        clockRangeSwitchLabel.SetMultiLineEdit( false );
        clockRangeSwitchLabel.SetIsNumberEditor( false );
        clockRangeSwitchLabel.SetNumberEditorRange( 0, 100 );
        clockRangeSwitchLabel.SetNumberEditorInterval( 1 );
        clockRangeSwitchLabel.SetNumberEditorUsesMouseWheel( false );
        clockRangeSwitchLabel.SetHasCustomTextHoverColor( false );
        clockRangeSwitchLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        clockRangeSwitchLabel.SetFont( "Arial", 4, true, false );

        xPassModeLabel = new VoltageLabel( "xPassModeLabel", "X Pass Mode Label", this, "PASS" );
        AddComponent( xPassModeLabel );
        xPassModeLabel.SetWantsMouseNotifications( false );
        xPassModeLabel.SetPosition( 50, 243 );
        xPassModeLabel.SetSize( 20, 10 );
        xPassModeLabel.SetEditable( false, false );
        xPassModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        xPassModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        xPassModeLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        xPassModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        xPassModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        xPassModeLabel.SetBorderSize( 1 );
        xPassModeLabel.SetMultiLineEdit( false );
        xPassModeLabel.SetIsNumberEditor( false );
        xPassModeLabel.SetNumberEditorRange( 0, 100 );
        xPassModeLabel.SetNumberEditorInterval( 1 );
        xPassModeLabel.SetNumberEditorUsesMouseWheel( false );
        xPassModeLabel.SetHasCustomTextHoverColor( false );
        xPassModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        xPassModeLabel.SetFont( "Arial", 4, true, false );

        clockCourtesyOutput = new VoltageAudioJack( "clockCourtesyOutput", "C Clock Courtesy", this, JackType.JackType_AudioOutput );
        AddComponent( clockCourtesyOutput );
        clockCourtesyOutput.SetWantsMouseNotifications( false );
        clockCourtesyOutput.SetPosition( 60, 39 );
        clockCourtesyOutput.SetSize( 25, 25 );
        clockCourtesyOutput.SetSkin( "Mini Jack 25px" );

        clockCourtesyLabel = new VoltageLabel( "clockCourtesyLabel", "C Courtesy Label", this, "C" );
        AddComponent( clockCourtesyLabel );
        clockCourtesyLabel.SetWantsMouseNotifications( false );
        clockCourtesyLabel.SetPosition( 57, 60 );
        clockCourtesyLabel.SetSize( 30, 30 );
        clockCourtesyLabel.SetEditable( false, false );
        clockCourtesyLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        clockCourtesyLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        clockCourtesyLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        clockCourtesyLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        clockCourtesyLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        clockCourtesyLabel.SetBorderSize( 1 );
        clockCourtesyLabel.SetMultiLineEdit( false );
        clockCourtesyLabel.SetIsNumberEditor( false );
        clockCourtesyLabel.SetNumberEditorRange( 0, 100 );
        clockCourtesyLabel.SetNumberEditorInterval( 1 );
        clockCourtesyLabel.SetNumberEditorUsesMouseWheel( false );
        clockCourtesyLabel.SetHasCustomTextHoverColor( false );
        clockCourtesyLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        clockCourtesyLabel.SetFont( "Arial", 9, true, false );

        rampCourtesyOutput = new VoltageAudioJack( "rampCourtesyOutput", "R Ramp Courtesy", this, JackType.JackType_AudioOutput );
        AddComponent( rampCourtesyOutput );
        rampCourtesyOutput.SetWantsMouseNotifications( false );
        rampCourtesyOutput.SetPosition( 90, 39 );
        rampCourtesyOutput.SetSize( 25, 25 );
        rampCourtesyOutput.SetSkin( "Mini Jack 25px" );

        rampCourtesyLabel = new VoltageLabel( "rampCourtesyLabel", "R Courtesy Label", this, "R" );
        AddComponent( rampCourtesyLabel );
        rampCourtesyLabel.SetWantsMouseNotifications( false );
        rampCourtesyLabel.SetPosition( 87, 60 );
        rampCourtesyLabel.SetSize( 30, 30 );
        rampCourtesyLabel.SetEditable( false, false );
        rampCourtesyLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        rampCourtesyLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        rampCourtesyLabel.SetColor( new Color( 147, 147, 147, 255 ) );
        rampCourtesyLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        rampCourtesyLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        rampCourtesyLabel.SetBorderSize( 1 );
        rampCourtesyLabel.SetMultiLineEdit( false );
        rampCourtesyLabel.SetIsNumberEditor( false );
        rampCourtesyLabel.SetNumberEditorRange( 0, 100 );
        rampCourtesyLabel.SetNumberEditorInterval( 1 );
        rampCourtesyLabel.SetNumberEditorUsesMouseWheel( false );
        rampCourtesyLabel.SetHasCustomTextHoverColor( false );
        rampCourtesyLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        rampCourtesyLabel.SetFont( "Arial", 9, true, false );

        xActiveLed = new VoltageLED( "xActiveLed", "X Active Indicator", this );
        AddComponent( xActiveLed );
        xActiveLed.SetWantsMouseNotifications( false );
        xActiveLed.SetPosition( 9, 93 );
        xActiveLed.SetSize( 10, 10 );
        xActiveLed.SetSkin( "2500 Lamp Green" );

        yActiveLed = new VoltageLED( "yActiveLed", "Y Active Indicator", this );
        AddComponent( yActiveLed );
        yActiveLed.SetWantsMouseNotifications( false );
        yActiveLed.SetPosition( 212, 93 );
        yActiveLed.SetSize( 10, 10 );
        yActiveLed.SetSkin( "2500 Lamp Red" );
}

void InitializeControls2()
{

        xPassSwitch = new VoltageSwitch( "xPassSwitch", "X Pass Switch", this, 0 );
        AddComponent( xPassSwitch );
        xPassSwitch.SetWantsMouseNotifications( false );
        xPassSwitch.SetPosition( 55, 250 );
        xPassSwitch.SetSize( 8, 15 );
        xPassSwitch.SetSkin( "2-State Slide Vertical" );

        yPassSwitch = new VoltageSwitch( "yPassSwitch", "Y Pass Switch", this, 1 );
        AddComponent( yPassSwitch );
        yPassSwitch.SetWantsMouseNotifications( false );
        yPassSwitch.SetPosition( 175, 250 );
        yPassSwitch.SetSize( 8, 15 );
        yPassSwitch.SetSkin( "2-State Slide Vertical" );

        badgeTitle = new VoltageLabel( "badgeTitle", "Manufacturer Badge", this, "iL" );
        AddComponent( badgeTitle );
        badgeTitle.SetWantsMouseNotifications( false );
        badgeTitle.SetPosition( 3, 337 );
        badgeTitle.SetSize( 20, 20 );
        badgeTitle.SetEditable( false, false );
        badgeTitle.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        badgeTitle.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        badgeTitle.SetColor( new Color( 147, 0, 0, 255 ) );
        badgeTitle.SetBkColor( new Color( 51, 51, 51, 255 ) );
        badgeTitle.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        badgeTitle.SetBorderSize( 1 );
        badgeTitle.SetMultiLineEdit( false );
        badgeTitle.SetIsNumberEditor( false );
        badgeTitle.SetNumberEditorRange( 0, 100 );
        badgeTitle.SetNumberEditorInterval( 1 );
        badgeTitle.SetNumberEditorUsesMouseWheel( false );
        badgeTitle.SetHasCustomTextHoverColor( false );
        badgeTitle.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        badgeTitle.SetFont( "Courier New", 13, true, false );

        manufacturerNameLabel = new VoltageLabel( "manufacturerNameLabel", "Model Number_3", this, "insect labs" );
        AddComponent( manufacturerNameLabel );
        manufacturerNameLabel.SetWantsMouseNotifications( false );
        manufacturerNameLabel.SetPosition( 140, 317 );
        manufacturerNameLabel.SetSize( 80, 10 );
        manufacturerNameLabel.SetEditable( false, false );
        manufacturerNameLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerNameLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerNameLabel.SetColor( new Color( 85, 85, 85, 147 ) );
        manufacturerNameLabel.SetBkColor( new Color( 0, 0, 0, 0 ) );
        manufacturerNameLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerNameLabel.SetBorderSize( 1 );
        manufacturerNameLabel.SetMultiLineEdit( false );
        manufacturerNameLabel.SetIsNumberEditor( false );
        manufacturerNameLabel.SetNumberEditorRange( 0, 100 );
        manufacturerNameLabel.SetNumberEditorInterval( 1 );
        manufacturerNameLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerNameLabel.SetHasCustomTextHoverColor( false );
        manufacturerNameLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerNameLabel.SetFont( "Courier New", 6, true, false );
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
displayCountdown = 1;
xActiveLed.SetValue(0.0);
yActiveLed.SetValue(0.0);
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
    case Button_Changed:
        if (doubleValue >= 0.5) {
            if (component == engageButton) {
                engagePresses.incrementAndGet();
            } else if (component == resetButton) {
                resetPresses.incrementAndGet();
            }
        }
        break;

    case Reset:
        resetPresses.incrementAndGet();
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
int setCountX = readSetCount(
        countXThousands,
        countXHundreds,
        countXTens,
        countXOnes
);

int setCountY = readSetCount(
        countYThousands,
        countYHundreds,
        countYTens,
        countYOnes
);

burstGenCore.process(
        readAudioInput(inputX),
        readAudioInput(inputY),
        readAudioInput(externalClockInput),
        externalClockInput.IsConnected(),
        setCountX,
        setCountY,
        internalClockKnob.GetValue(),
        (int) Math.round(clockRangeSwitch.GetValue()),
        (int) Math.round(ratioKnob.GetValue()),
        ratioDestinationSwitch.GetValue() >= 0.5,
        clockSourceSwitch.GetValue() >= 0.5,
        runModeSwitch.GetValue() >= 0.5,
        xPassSwitch.GetValue() >= 0.5,
        yPassSwitch.GetValue() >= 0.5,
        engagePresses.getAndSet(0) > 0,
        resetPresses.getAndSet(0) > 0
);

// Latch brief activity so high-rate bursts remain visible.
if (burstGenCore.isXActive()) {
    xActivitySeen = true;
}

if (burstGenCore.isYActive()) {
    yActivitySeen = true;
}

outputZ.SetValue(burstGenCore.getOutputZ());
outputX.SetValue(burstGenCore.getOutputX());
outputY.SetValue(burstGenCore.getOutputY());

clockCourtesyOutput.SetValue(
        burstGenCore.getClockCourtesy()
);

rampCourtesyOutput.SetValue(
        burstGenCore.getRampCourtesy()
);

if (--displayCountdown <= 0) {
    // 100 Hz visual refresh; counting remains sample accurate.
    displayCountdown = 480;

    updateCounterDisplay(
            setCountXDisplay,
            setCountX,
            0
    );

    updateCounterDisplay(
            setCountYDisplay,
            setCountY,
            1
    );

    updateCounterDisplay(
            remainingCountXDisplay,
            burstGenCore.getRemainingX(),
            2
    );

    updateCounterDisplay(
            remainingCountYDisplay,
            burstGenCore.getRemainingY(),
            3
    );

    updateActiveIndicators();
}
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
engagePresses.set(0);
resetPresses.set(0);

double dryX = readAudioInput(this.inputX);
double dryY = readAudioInput(this.inputY);

burstGenCore.processBypassed(
        dryX,
        dryY
);

outputZ.SetValue(dryX + dryY);
outputX.SetValue(dryX);
outputY.SetValue(dryY);

clockCourtesyOutput.SetValue(0.0);
rampCourtesyOutput.SetValue(0.0);

xActivitySeen = false;
yActivitySeen = false;

xLedDisplay = 0.0;
yLedDisplay = 0.0;

xActiveLed.SetValue(0.0);
yActiveLed.SetValue(0.0);

displayCountdown = 1;
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
if (component == internalClockKnob) {
    int range =
            (int) Math.round(
                    clockRangeSwitch.GetValue()
            );

    double baseRate =
            BurstGenCore.internalFrequency(
                    internalClockKnob.GetValue(),
                    range
            );

    double countRate =
            ratioDestinationSwitch.GetValue() >= 0.5
            ? baseRate * clockRatio(
                    (int) Math.round(
                            ratioKnob.GetValue()
                    )
            )
            : baseRate;

    return String.format(
            java.util.Locale.ROOT,
            "Clock %.4g Hz · count %.4g Hz · enter clock frequency in Hz",
            baseRate,
            countRate
    );
}

if (component == ratioKnob) {
    int position =
            (int) Math.round(
                    ratioKnob.GetValue()
            );

    String value =
            ratioLabel(position);

    String destination =
            ratioDestinationSwitch.GetValue() >= 0.5
            ? "Internal and external clocks"
            : "External clock only";

    return value + " · " + destination;
}

if (component == ratioDestinationSwitch) {
    return ratioDestinationSwitch.GetValue() >= 0.5
            ? "Both: ratio adjusts external and internal timing; R follows internal timing."
            : "External: ratio adjusts external timing only.";
}

if (component == clockSourceSwitch) {
    return clockSourceSwitch.GetValue() >= 0.5
            ? "External rising-edge clock"
            : "Free-running internal clock";
}

if (component == clockRangeSwitch) {
    int range =
            (int) Math.round(
                    clockRangeSwitch.GetValue()
            );

    return range <= 0
            ? "LO · 0.005–5 Hz"
            : (
                    range == 1
                    ? "MID · 1–200 Hz"
                    : "HI · 20–3,000 Hz"
            );
}

if (component == xPassSwitch ||
        component == yPassSwitch) {

    return component == xPassSwitch
            ? (
                    xPassSwitch.GetValue() >= 0.5
                    ? "X output passes input"
                    : "X output follows gate"
            )
            : (
                    yPassSwitch.GetValue() >= 0.5
                    ? "Y output passes input"
                    : "Y output follows gate"
            );
}

if (component == engageButton) return "ENGAGE: start, pause, or resume the X/Y burst sequence";
if (component == resetButton) return "RESET: reload the displayed counts and prepare X";
if (component == runModeSwitch) return "Run mode: LOOP repeats X then Y; 1 SHOT runs one full X/Y cycle";
if (component == outputZ) return "Z Output: sum of the X and Y signals after their counted gates";
if (component == inputX) return "X Input: signal passed or gated at X Output and included in Z while X is active";
if (component == inputY) return "Y Input: signal passed or gated at Y Output and included in Z while Y is active";
if (component == outputX) return "X Output: X input, either continuously passed or controlled by the X gate";
if (component == outputY) return "Y Output: Y input, either continuously passed or controlled by the Y gate";
if (component == countXThousands || component == countXHundreds || component == countXTens || component == countXOnes) return "X Set Count: four digits, 0000–9999 clock ticks";
if (component == countYThousands || component == countYHundreds || component == countYTens || component == countYOnes) return "Y Set Count: four digits, 0000–9999 clock ticks";
if (component == externalClockInput) return "External Clock: rising edges advance the active count when EXT is selected";
if (component == clockCourtesyOutput) return "C Courtesy: free-running internal 0 to +5 V square clock; unaffected by EXT";
if (component == rampCourtesyOutput) return "R Courtesy: rising internal ramp; its reset edge marks the internal timing tick";
if (component == xActiveLed) return "X Active: lit while the X counted gate is open";
if (component == yActiveLed) return "Y Active: lit while the Y counted gate is open";
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

if (component == internalClockKnob) {
    int range =
            (int) Math.round(
                    clockRangeSwitch.GetValue()
            );

    internalClockKnob.SetValue(
            internalClockPositionForHz(
                    newValue,
                    range
            )
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
    private VoltageLabel manufacturerNameLabel;
    private VoltageLabel badgeTitle;
    private VoltageSwitch yPassSwitch;
    private VoltageSwitch xPassSwitch;
    private VoltageLED yActiveLed;
    private VoltageLED xActiveLed;
    private VoltageLabel rampCourtesyLabel;
    private VoltageAudioJack rampCourtesyOutput;
    private VoltageLabel clockCourtesyLabel;
    private VoltageAudioJack clockCourtesyOutput;
    private VoltageLabel xPassModeLabel;
    private VoltageLabel clockRangeSwitchLabel;
    private VoltageLabel yPassModeLabel;
    private VoltageLabel ratioBothModeLabel;
    private VoltageLabel ratioExternalModeLabel;
    private VoltageLabel yGateModeLabel;
    private VoltageLabel xGateModeLabel;
    private VoltageSwitch clockRangeSwitch;
    private VoltageSwitch ratioDestinationSwitch;
    private VoltageLabel internalClockKnobLabel;
    private VoltageKnob internalClockKnob;
    private VoltageLabel externalClockSourceLabel;
    private VoltageLabel internalClockSourceLabel;
    private VoltageSwitch clockSourceSwitch;
    private VoltageLabel externalClockLabel;
    private VoltageLabel ratioKnobLabel;
    private VoltageLabel ratioThreeLabel;
    private VoltageLabel ratioSevenLabel;
    private VoltageLabel ratioFiveLabel;
    private VoltageLabel ratioTwoLabel;
    private VoltageLabel ratioFourLabel;
    private VoltageLabel ratioHalfLabel;
    private VoltageLabel ratioEightLabel;
    private VoltageLabel ratioThreeHalvesLabel;
    private VoltageLabel ratioFourThirdsLabel;
    private VoltageLabel ratio1618Label;
    private VoltageLabel ratioUnityLabel;
    private VoltageAudioJack externalClockInput;
    private VoltageKnob ratioKnob;
    private VoltageKnob countXTens;
    private VoltageKnob countYOnes;
    private VoltageKnob countYTens;
    private VoltageKnob countYHundreds;
    private VoltageKnob countYThousands;
    private VoltageKnob countXHundreds;
    private VoltageKnob countXOnes;
    private VoltageLabel yInputLabel;
    private VoltageLabel yOutputLabel;
    private VoltageKnob countXThousands;
    private VoltageLabel xOutputLabel;
    private VoltageLabel xInputLabel;
    private VoltageLabel outputZLabel;
    private VoltageLabel loopLabel;
    private VoltageLabel oneShotLabel;
    private VoltageLabel resetButtonLabel;
    private VoltageLabel engageButtonLabel;
    private VoltageLabel setYHeading;
    private VoltageLabel remainingYHeading;
    private VoltageLabel remainingXHeading;
    private VoltageLabel setXHeading;
    private VoltageLabel ySideLabel;
    private VoltageLabel xSideLabel;
    private VoltageAudioJack outputY;
    private VoltageAudioJack outputX;
    private VoltageAudioJack inputY;
    private VoltageAudioJack inputX;
    private VoltageAudioJack outputZ;
    private VoltageSwitch runModeSwitch;
    private VoltageButton resetButton;
    private VoltageButton engageButton;
    private VoltageDigitalCounter remainingCountYDisplay;
    private VoltageDigitalCounter remainingCountXDisplay;
    private VoltageDigitalCounter setCountYDisplay;
    private VoltageLabel numberTitle;
    private VoltageLabel nameTitle;
    private VoltageDigitalCounter setCountXDisplay;
    private VoltageLabel moduleDescriptionLabel;
    private VoltageLabel manufacturerLocationLabel;
    private VoltageLabel modelNumberLabel;


    //[user-code-and-variables]    Add your own variables and functions here
private static final double SAMPLE_RATE = 48000.0;

private final java.util.concurrent.atomic.AtomicInteger engagePresses =
        new java.util.concurrent.atomic.AtomicInteger();

private final java.util.concurrent.atomic.AtomicInteger resetPresses =
        new java.util.concurrent.atomic.AtomicInteger();

// Keep initialization in the user-owned region:
// Designer regenerates the constructor.
private final BurstGenCore burstGenCore =
        new BurstGenCore(SAMPLE_RATE);

private final int[] previousCounterValues =
        {-1, -1, -1, -1};

private int displayCountdown;


// -----------------------------------------------------------------------------
// Activity LEDs
// -----------------------------------------------------------------------------

private static final double ACTIVE_LED_DECAY_PER_REFRESH = 0.72;

private boolean xActivitySeen;
private boolean yActivitySeen;

private double xLedDisplay;
private double yLedDisplay;


// -----------------------------------------------------------------------------
// Counter helpers
// -----------------------------------------------------------------------------

private static int readSetCount(
        VoltageKnob thousands,
        VoltageKnob hundreds,
        VoltageKnob tens,
        VoltageKnob ones) {

    return digitValue(thousands) * 1000
            + digitValue(hundreds) * 100
            + digitValue(tens) * 10
            + digitValue(ones);
}


private static int digitValue(
        VoltageKnob knob) {

    return (int) Math.round(
            knob.GetValue()
    );
}


private void updateCounterDisplay(
        VoltageDigitalCounter counter,
        int value,
        int index) {

    if (previousCounterValues[index] != value) {
        counter.SetValue(value);
        previousCounterValues[index] = value;
    }
}


// -----------------------------------------------------------------------------
// Activity indicators
// -----------------------------------------------------------------------------

private void updateActiveIndicators() {
    xLedDisplay =
            xActivitySeen
            ? 1.0
            : xLedDisplay * ACTIVE_LED_DECAY_PER_REFRESH;

    yLedDisplay =
            yActivitySeen
            ? 1.0
            : yLedDisplay * ACTIVE_LED_DECAY_PER_REFRESH;

    if (xLedDisplay < 0.01) {
        xLedDisplay = 0.0;
    }

    if (yLedDisplay < 0.01) {
        yLedDisplay = 0.0;
    }

    xActiveLed.SetValue(xLedDisplay);
    yActiveLed.SetValue(yLedDisplay);

    xActivitySeen = false;
    yActivitySeen = false;
}


// -----------------------------------------------------------------------------
// Typed clock-frequency conversion
// -----------------------------------------------------------------------------

private static double internalClockPositionForHz(
        double hz,
        int range) {

    double low =
            range <= 0
            ? 0.005
            : (
                    range == 1
                    ? 1.0
                    : 20.0
            );

    double high =
            range <= 0
            ? 5.0
            : (
                    range == 1
                    ? 200.0
                    : 3000.0
            );

    double clampedHz =
            Math.max(
                    low,
                    Math.min(high, hz)
            );

    return Math.log(clampedHz / low)
            / Math.log(high / low);
}


// -----------------------------------------------------------------------------
// Input helper
// -----------------------------------------------------------------------------

private static double readAudioInput(
        VoltageAudioJack jack) {

    return jack.IsConnected()
            ? jack.GetValue()
            : 0.0;
}


// -----------------------------------------------------------------------------
// Burst generator core
// -----------------------------------------------------------------------------

static final class BurstGenCore {

    private static final double GATE_HIGH_VOLTS = 5.0;

    private static final int MAX_FADE_SAMPLES = 24;

    private static final double MAX_FADE_PHASE_FRACTION = 0.04;

    private static final double FAST_CLOCK_FADE_CUTOFF_HZ = 1000.0;


    private final double sampleRate;

    private final ExternalClock externalClock =
            new ExternalClock();


    private double internalPhase = 0.5;
    private double internalFrequency = 1.0;

    private double previousClockControl =
            Double.NaN;

    private int previousClockRange = -1;

    private boolean internalClockHigh;

    private double timingPhase;

    private int remainingX;
    private int remainingY;

    private int setX;
    private int setY;

    private int activeSide;

    private boolean running;
    private boolean oneShotCycle;
    private boolean stopAfterCurrentSide;

    private boolean lastLoopMode = true;

    private double gateGainX;
    private double gateGainY;

    private double targetGainX;
    private double targetGainY;

    private double gateStepX;
    private double gateStepY;

    private int gateFadeRemaining;

    private double outputX;
    private double outputY;
    private double outputZ;

    private double clockCourtesy;
    private double rampCourtesy;

    private boolean bypassed;


    BurstGenCore(double sampleRate) {
        this.sampleRate = sampleRate;
    }


    void process(
            double inputX,
            double inputY,
            double externalClockSample,
            boolean externalClockConnected,
            int requestedSetX,
            int requestedSetY,
            double internalClockControl,
            int rangePosition,
            int ratioPosition,
            boolean ratioBoth,
            boolean useExternalClock,
            boolean loopMode,
            boolean passX,
            boolean passY,
            boolean engagePressed,
            boolean resetPressed) {

        setX = clampCount(requestedSetX);
        setY = clampCount(requestedSetY);


        if (bypassed) {
            externalClock.resume(
                    externalClockSample,
                    externalClockConnected
            );

            gateGainX = targetGainX;
            gateGainY = targetGainY;

            gateFadeRemaining = 0;
            bypassed = false;
        }


        boolean wasRunning = running;


        if (!running && activeSide == 0) {
            remainingX = setX;
            remainingY = setY;
        }


        if (resetPressed) {
            if (running) {
                restartCurrentSide();
            } else {
                finishCycle();
            }
        }


        if (engagePressed) {
            if (running) {
                running = false;
            } else if (
                    activeSide != 0 &&
                    (remainingX > 0 || remainingY > 0)
            ) {
                running = true;
            } else {
                startCycle(loopMode);
            }
        }


        if (
                wasRunning &&
                running &&
                lastLoopMode &&
                !loopMode
        ) {
            stopAfterCurrentSide = true;
        } else if (loopMode) {
            stopAfterCurrentSide = false;
            oneShotCycle = false;
        }

        lastLoopMode = loopMode;


        if (
                internalClockControl != previousClockControl ||
                rangePosition != previousClockRange
        ) {
            internalFrequency =
                    internalFrequency(
                            internalClockControl,
                            rangePosition
                    );

            previousClockControl =
                    internalClockControl;

            previousClockRange =
                    rangePosition;
        }


        double timingFrequency =
                internalFrequency
                * (
                        ratioBoth
                        ? clockRatio(ratioPosition)
                        : 1.0
                );


        boolean internalTick =
                advanceInternalClock(
                        timingFrequency,
                        ratioBoth
                );


        boolean externalTick =
                externalClock.process(
                        externalClockSample,
                        externalClockConnected,
                        ratioPosition,
                        sampleRate
                );


        boolean selectedTick =
                useExternalClock
                ? externalTick
                : internalTick;


        double selectedRate =
                useExternalClock
                ? externalClock.getEffectiveFrequency()
                : timingFrequency;


        if (running && selectedTick) {
            advanceSequencer();
        }


        boolean shouldOpenX =
                running && activeSide == 1;

        boolean shouldOpenY =
                running && activeSide == 2;


        setGateTargets(
                shouldOpenX,
                shouldOpenY,
                selectedRate
        );


        outputX =
                inputX
                * (
                        passX
                        ? 1.0
                        : gateGainX
                );


        outputY =
                inputY
                * (
                        passY
                        ? 1.0
                        : gateGainY
                );


        outputZ =
                inputX * gateGainX
                + inputY * gateGainY;


        clockCourtesy =
                internalClockHigh
                ? GATE_HIGH_VOLTS
                : 0.0;


        rampCourtesy =
                internalRampValue();
    }


    void processBypassed(
            double inputX,
            double inputY) {

        outputX = inputX;
        outputY = inputY;

        outputZ =
                inputX + inputY;

        clockCourtesy = 0.0;
        rampCourtesy = 0.0;

        externalClock.pause();

        bypassed = true;
    }


    private boolean advanceInternalClock(
            double timingFrequency,
            boolean ratioBoth) {

        double oldPhase =
                internalPhase;

        internalPhase +=
                internalFrequency / sampleRate;

        if (internalPhase >= 1.0) {
            internalPhase -= 1.0;
        }


        internalClockHigh =
                internalPhase < 0.5;


        if (!ratioBoth) {
            timingPhase =
                    internalPhase >= 0.5
                    ? internalPhase - 0.5
                    : internalPhase + 0.5;

            return oldPhase < 0.5
                    && internalPhase >= 0.5;
        }


        // Direct frequency scaling needs no edge
        // acquisition, even at the slowest range.
        timingPhase +=
                timingFrequency / sampleRate;


        if (timingPhase >= 1.0) {
            timingPhase -= 1.0;
            return true;
        }

        return false;
    }


    private double internalRampValue() {
        return timingPhase
                * GATE_HIGH_VOLTS;
    }


    private void startCycle(
            boolean loopMode) {

        if (setX == 0 && setY == 0) {
            activeSide = 0;
            remainingX = 0;
            remainingY = 0;

            running = false;
            oneShotCycle = false;

            return;
        }


        remainingX = setX;
        remainingY = setY;


        activeSide =
                setX > 0
                ? 1
                : 2;


        if (
                activeSide == 1 &&
                remainingX == 0
        ) {
            activeSide = 2;
        }


        running = true;

        oneShotCycle =
                !loopMode;

        stopAfterCurrentSide =
                false;
    }


    private void restartCurrentSide() {
        if (activeSide == 1) {
            remainingX = setX;

            if (remainingX == 0) {
                selectSide(2);
            }

        } else if (activeSide == 2) {
            remainingY = setY;

            if (remainingY == 0) {
                selectSide(1);
            }
        }
    }


    private void advanceSequencer() {
        if (activeSide == 1) {

            if (remainingX > 0) {
                remainingX--;
            }

            if (remainingX == 0) {
                if (stopAfterCurrentSide) {
                    finishCycle();
                } else {
                    selectSide(2);
                }
            }

        } else if (activeSide == 2) {

            if (remainingY > 0) {
                remainingY--;
            }

            if (remainingY == 0) {
                if (
                        stopAfterCurrentSide ||
                        oneShotCycle ||
                        !lastLoopMode
                ) {
                    finishCycle();
                } else {
                    selectSide(1);
                }
            }
        }
    }


    private void selectSide(
            int requestedSide) {

        if (setX == 0 && setY == 0) {

            finishCycle();

        } else if (
                requestedSide == 2 &&
                setY == 0 &&
                oneShotCycle
        ) {

            finishCycle();

        } else {

            activeSide =
                    requestedSide == 1
                    ? (
                            setX > 0
                            ? 1
                            : 2
                    )
                    : (
                            setY > 0
                            ? 2
                            : 1
                    );


            if (activeSide == 1) {
                remainingX = setX;
            } else {
                remainingY = setY;
            }
        }
    }


    private void finishCycle() {
        running = false;

        activeSide = 0;

        oneShotCycle = false;
        stopAfterCurrentSide = false;

        remainingX = setX;
        remainingY = setY;
    }


    private void setGateTargets(
            boolean openX,
            boolean openY,
            double tickRate) {

        double nextX =
                openX
                ? 1.0
                : 0.0;

        double nextY =
                openY
                ? 1.0
                : 0.0;


        if (
                nextX == targetGainX &&
                nextY == targetGainY
        ) {

            if (
                    tickRate >=
                    FAST_CLOCK_FADE_CUTOFF_HZ
            ) {
                gateGainX = nextX;
                gateGainY = nextY;

                gateFadeRemaining = 0;
            }

            advanceGateFades();

            return;
        }


        targetGainX = nextX;
        targetGainY = nextY;


        int shortestCount =
                shortestActiveCount();


        int fadeSamples =
                fadeSamples(
                        tickRate,
                        shortestCount
                );


        if (fadeSamples == 0) {

            gateGainX = targetGainX;
            gateGainY = targetGainY;

            gateFadeRemaining = 0;

            gateStepX = 0.0;
            gateStepY = 0.0;

        } else {

            gateFadeRemaining =
                    fadeSamples;

            gateStepX =
                    (targetGainX - gateGainX)
                    / fadeSamples;

            gateStepY =
                    (targetGainY - gateGainY)
                    / fadeSamples;
        }
    }


    private int shortestActiveCount() {

        int current =
                activeSide == 1
                ? remainingX
                : remainingY;

        int other =
                activeSide == 1
                ? setY
                : setX;


        if (current <= 0) {
            current = 1;
        }


        if (other <= 0) {
            other = current;
        }


        return Math.min(
                current,
                other
        );
    }


    private int fadeSamples(
            double tickRate,
            int gateTicks) {

        if (
                !(tickRate > 0.0) ||
                gateTicks < 1 ||
                tickRate >= FAST_CLOCK_FADE_CUTOFF_HZ
        ) {
            return 0;
        }


        double gateDurationSamples =
                gateTicks
                * sampleRate
                / tickRate;


        double speedScale =
                1.0
                - tickRate
                / FAST_CLOCK_FADE_CUTOFF_HZ;


        double requestedFade =
                gateDurationSamples
                * MAX_FADE_PHASE_FRACTION;


        int candidate =
                (int) Math.floor(
                        Math.min(
                                MAX_FADE_SAMPLES,
                                requestedFade
                        )
                        * speedScale
                );


        return candidate >= 2
                ? candidate
                : 0;
    }


    private void advanceGateFades() {

        if (gateFadeRemaining <= 0) {
            return;
        }


        gateGainX += gateStepX;
        gateGainY += gateStepY;

        gateFadeRemaining--;


        if (gateFadeRemaining == 0) {
            gateGainX = targetGainX;
            gateGainY = targetGainY;
        }
    }


    private static double internalFrequency(
            double control,
            int range) {

        double low =
                range <= 0
                ? 0.005
                : (
                        range == 1
                        ? 1.0
                        : 20.0
                );


        double high =
                range <= 0
                ? 5.0
                : (
                        range == 1
                        ? 200.0
                        : 3000.0
                );


        double position =
                Math.max(
                        0.0,
                        Math.min(
                                1.0,
                                control
                        )
                );


        return low
                * Math.pow(
                        high / low,
                        position
                );
    }


    private static int clampCount(
            int count) {

        return Math.max(
                0,
                Math.min(
                        9999,
                        count
                )
        );
    }


    double getOutputX() {
        return outputX;
    }


    double getOutputY() {
        return outputY;
    }


    double getOutputZ() {
        return outputZ;
    }


    double getClockCourtesy() {
        return clockCourtesy;
    }


    double getRampCourtesy() {
        return rampCourtesy;
    }


    int getRemainingX() {
        return remainingX;
    }


    int getRemainingY() {
        return remainingY;
    }


    boolean isXActive() {
        return running
                && activeSide == 1;
    }


    boolean isYActive() {
        return running
                && activeSide == 2;
    }
}


// -----------------------------------------------------------------------------
// External clock
// -----------------------------------------------------------------------------

static final class ExternalClock {

    private static final double LOW_THRESHOLD = 0.1;
    private static final double HIGH_THRESHOLD = 1.0;


    private boolean armed;
    private boolean connected;
    private boolean hasSeenEdge;

    private int ratioPosition = 6;

    private long samplesSinceEdge;

    private double measuredPeriodSamples;
    private double effectiveFrequency;

    private double dividerPhase;
    private double multiplierFraction;

    private int remainingExtraTicks;

    private double samplesToExtraTick;
    private double extraTickSpacing;


    boolean process(
            double sample,
            boolean isConnected,
            int newRatioPosition,
            double sampleRate) {

        int nextPosition =
                Math.max(
                        1,
                        Math.min(
                                11,
                                newRatioPosition
                        )
                );


        if (nextPosition != ratioPosition) {
            resetRatio(nextPosition);
        }


        if (!isConnected) {

            connected = false;
            armed = false;
            hasSeenEdge = false;

            samplesSinceEdge = 0;

            measuredPeriodSamples = 0.0;
            effectiveFrequency = 0.0;

            remainingExtraTicks = 0;

            return false;
        }


        if (!connected) {

            dividerPhase = 0.0;
            multiplierFraction = 0.0;

            connected = true;

            armed =
                    sample <= LOW_THRESHOLD;

            hasSeenEdge = false;

            samplesSinceEdge = 0;

            remainingExtraTicks = 0;
        }


        samplesSinceEdge++;


        if (
                armed &&
                sample >= HIGH_THRESHOLD
        ) {

            armed = false;


            long interval =
                    samplesSinceEdge;


            boolean hadPreviousEdge =
                    hasSeenEdge &&
                    interval > 0;


            if (hadPreviousEdge) {
                measuredPeriodSamples =
                        interval;
            }


            samplesSinceEdge = 0;
            hasSeenEdge = true;


            double divider =
                    dividerForPosition(
                            ratioPosition
                    );


            if (divider > 1.0) {

                dividerPhase += 1.0;


                if (dividerPhase >= divider) {

                    dividerPhase -=
                            divider;


                    effectiveFrequency =
                            sampleRate
                            / Math.max(
                                    1.0,
                                    measuredPeriodSamples
                            )
                            / divider;


                    return true;
                }


                effectiveFrequency =
                        measuredPeriodSamples > 0.0
                        ? sampleRate
                                / measuredPeriodSamples
                                / divider
                        : 0.0;


                return false;
            }


            double multiplier =
                    multiplierForPosition(
                            ratioPosition
                    );


            boolean tick = true;

            remainingExtraTicks = 0;


            if (
                    multiplier > 1.0 &&
                    hadPreviousEdge
            ) {

                double extras =
                        multiplier
                        - 1.0
                        + multiplierFraction;


                remainingExtraTicks =
                        (int) Math.floor(
                                extras + 1.0e-12
                        );


                multiplierFraction =
                        extras
                        - remainingExtraTicks;


                if (remainingExtraTicks > 0) {

                    extraTickSpacing =
                            measuredPeriodSamples
                            / (
                                    remainingExtraTicks
                                    + 1.0
                            );


                    samplesToExtraTick =
                            extraTickSpacing;
                }
            }


            effectiveFrequency =
                    measuredPeriodSamples > 0.0
                    ? sampleRate
                            / measuredPeriodSamples
                            * multiplier
                    : 0.0;


            return tick;
        }


        if (
                !armed &&
                sample <= LOW_THRESHOLD
        ) {
            armed = true;
        }


        if (remainingExtraTicks > 0) {

            samplesToExtraTick -= 1.0;


            if (samplesToExtraTick <= 0.0) {

                remainingExtraTicks--;

                samplesToExtraTick +=
                        extraTickSpacing;

                return true;
            }
        }


        return false;
    }


    void resume(
            double sample,
            boolean isConnected) {

        armed =
                isConnected &&
                sample <= LOW_THRESHOLD;

        connected =
                isConnected;

        samplesSinceEdge = 0;

        hasSeenEdge = false;

        measuredPeriodSamples = 0.0;

        effectiveFrequency = 0.0;

        remainingExtraTicks = 0;

        multiplierFraction = 0.0;

        dividerPhase = 0.0;
    }


    void pause() {
        remainingExtraTicks = 0;
    }


    double getEffectiveFrequency() {
        return effectiveFrequency;
    }


    private void resetRatio(
            int position) {

        ratioPosition =
                position;

        dividerPhase = 0.0;
        multiplierFraction = 0.0;

        remainingExtraTicks = 0;

        samplesToExtraTick = 0.0;
    }


    private static double dividerForPosition(
            int position) {

        double ratio =
                clockRatio(position);

        return ratio < 1.0
                ? 1.0 / ratio
                : 1.0;
    }


    private static double multiplierForPosition(
            int position) {

        return Math.max(
                1.0,
                clockRatio(position)
        );
    }
}


// -----------------------------------------------------------------------------
// Ratio mapping
// -----------------------------------------------------------------------------

private static double clockRatio(
        int position) {

    switch (position) {

        case 1:
            return 0.125;

        case 2:
            return 0.25;

        case 3:
            return 0.5;

        case 4:
            return 1.0 / 1.5;

        case 5:
            // Panel ÷1.333 denotes the exact 4/3 divisor.
            return 0.75;

        case 7:
            return 1.618;

        case 8:
            return 2.0;

        case 9:
            return 3.0;

        case 10:
            return 5.0;

        case 11:
            return 7.0;

        default:
            return 1.0;
    }
}


private static String ratioLabel(
        int position) {

    switch (position) {

        case 1:
            return "÷8";

        case 2:
            return "÷4";

        case 3:
            return "÷2";

        case 4:
            return "÷1.5";

        case 5:
            return "÷1.333";

        case 7:
            return "×1.618";

        case 8:
            return "×2";

        case 9:
            return "×3";

        case 10:
            return "×5";

        case 11:
            return "×7";

        default:
            return "×1";
    }
}
    //[/user-code-and-variables]
}

 