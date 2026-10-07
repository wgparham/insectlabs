package com.insectlabs.faderdistributera;


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


public class faderdistributera extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public faderdistributera( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "6121198a - fader – distributer", ModuleType.ModuleType_Utility, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "e4c71f1926d74059b2455374284fcb99" );
    }

void InitializeControls()
{

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Module Description", this, "fader – distributer" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 3 );
        descriptionLabel.SetSize( 109, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
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

        faderSectionLabel = new VoltageLabel( "faderSectionLabel", "Fader Section Label", this, "FADER" );
        AddComponent( faderSectionLabel );
        faderSectionLabel.SetWantsMouseNotifications( false );
        faderSectionLabel.SetPosition( 36, 145 );
        faderSectionLabel.SetSize( 41, 13 );
        faderSectionLabel.SetEditable( false, false );
        faderSectionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        faderSectionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        faderSectionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        faderSectionLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        faderSectionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        faderSectionLabel.SetBorderSize( 1 );
        faderSectionLabel.SetMultiLineEdit( false );
        faderSectionLabel.SetIsNumberEditor( false );
        faderSectionLabel.SetNumberEditorRange( 0, 100 );
        faderSectionLabel.SetNumberEditorInterval( 1 );
        faderSectionLabel.SetNumberEditorUsesMouseWheel( false );
        faderSectionLabel.SetHasCustomTextHoverColor( false );
        faderSectionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        faderSectionLabel.SetFont( "Arial", 13, true, false );

        distrSectionLabel = new VoltageLabel( "distrSectionLabel", "Distr Section Label", this, "DISTR" );
        AddComponent( distrSectionLabel );
        distrSectionLabel.SetWantsMouseNotifications( false );
        distrSectionLabel.SetPosition( 36, 240 );
        distrSectionLabel.SetSize( 41, 13 );
        distrSectionLabel.SetEditable( false, false );
        distrSectionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        distrSectionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        distrSectionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        distrSectionLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        distrSectionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        distrSectionLabel.SetBorderSize( 1 );
        distrSectionLabel.SetMultiLineEdit( false );
        distrSectionLabel.SetIsNumberEditor( false );
        distrSectionLabel.SetNumberEditorRange( 0, 100 );
        distrSectionLabel.SetNumberEditorInterval( 1 );
        distrSectionLabel.SetNumberEditorUsesMouseWheel( false );
        distrSectionLabel.SetHasCustomTextHoverColor( false );
        distrSectionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        distrSectionLabel.SetFont( "Arial", 13, true, false );

        numberLabel = new VoltageLabel( "numberLabel", "Model Number", this, "612-1-198a" );
        AddComponent( numberLabel );
        numberLabel.SetWantsMouseNotifications( false );
        numberLabel.SetPosition( 33, 344 );
        numberLabel.SetSize( 77, 13 );
        numberLabel.SetEditable( false, false );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        numberLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        numberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        numberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        numberLabel.SetBorderSize( 1 );
        numberLabel.SetMultiLineEdit( false );
        numberLabel.SetIsNumberEditor( false );
        numberLabel.SetNumberEditorRange( 0, 100 );
        numberLabel.SetNumberEditorInterval( 1 );
        numberLabel.SetNumberEditorUsesMouseWheel( false );
        numberLabel.SetHasCustomTextHoverColor( false );
        numberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        numberLabel.SetFont( "Courier New", 13, true, false );

        bias = new VoltageKnob( "bias", "Bias", this, -1.0, 1.0, 0.0 );
        AddComponent( bias );
        bias.SetWantsMouseNotifications( false );
        bias.SetPosition( 20, 44 );
        bias.SetSize( 75, 75 );
        bias.SetSkin( "Cosmo Large Black" );
        bias.SetRange( -1.0, 1.0, 0.0, false, 0 );
        bias.SetKnobParams( 215, 145 );
        bias.DisplayValueInPercent( true );
        bias.SetKnobAdjustsRing( true );

        xLabel = new VoltageLabel( "xLabel", "X Label", this, "X" );
        AddComponent( xLabel );
        xLabel.SetWantsMouseNotifications( false );
        xLabel.SetPosition( 16, 160 );
        xLabel.SetSize( 15, 15 );
        xLabel.SetEditable( false, false );
        xLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        xLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        xLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        xLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        xLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        xLabel.SetBorderSize( 1 );
        xLabel.SetMultiLineEdit( false );
        xLabel.SetIsNumberEditor( false );
        xLabel.SetNumberEditorRange( 0, 100 );
        xLabel.SetNumberEditorInterval( 1 );
        xLabel.SetNumberEditorUsesMouseWheel( false );
        xLabel.SetHasCustomTextHoverColor( false );
        xLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        xLabel.SetFont( "Arial", 15, true, false );

        yLabel = new VoltageLabel( "yLabel", "Y Label", this, "Y" );
        AddComponent( yLabel );
        yLabel.SetWantsMouseNotifications( false );
        yLabel.SetPosition( 84, 160 );
        yLabel.SetSize( 14, 15 );
        yLabel.SetEditable( false, false );
        yLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        yLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        yLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        yLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        yLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        yLabel.SetBorderSize( 1 );
        yLabel.SetMultiLineEdit( false );
        yLabel.SetIsNumberEditor( false );
        yLabel.SetNumberEditorRange( 0, 100 );
        yLabel.SetNumberEditorInterval( 1 );
        yLabel.SetNumberEditorUsesMouseWheel( false );
        yLabel.SetHasCustomTextHoverColor( false );
        yLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        yLabel.SetFont( "Arial", 15, true, false );

        zLabel = new VoltageLabel( "zLabel", "Z Label", this, "Z" );
        AddComponent( zLabel );
        zLabel.SetWantsMouseNotifications( false );
        zLabel.SetPosition( 50, 180 );
        zLabel.SetSize( 15, 15 );
        zLabel.SetEditable( false, false );
        zLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        zLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        zLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        zLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        zLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        zLabel.SetBorderSize( 1 );
        zLabel.SetMultiLineEdit( false );
        zLabel.SetIsNumberEditor( false );
        zLabel.SetNumberEditorRange( 0, 100 );
        zLabel.SetNumberEditorInterval( 1 );
        zLabel.SetNumberEditorUsesMouseWheel( false );
        zLabel.SetHasCustomTextHoverColor( false );
        zLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        zLabel.SetFont( "Arial", 15, true, false );

        xInput = new VoltageAudioJack( "xInput", "X Input", this, JackType.JackType_AudioInput );
        AddComponent( xInput );
        xInput.SetWantsMouseNotifications( false );
        xInput.SetPosition( 5, 175 );
        xInput.SetSize( 37, 37 );
        xInput.SetSkin( "Dark Jack Straight" );

        yInput = new VoltageAudioJack( "yInput", "Y Input", this, JackType.JackType_AudioInput );
        AddComponent( yInput );
        yInput.SetWantsMouseNotifications( false );
        yInput.SetPosition( 73, 175 );
        yInput.SetSize( 37, 37 );
        yInput.SetSkin( "Dark Jack Straight" );

        zOutput = new VoltageAudioJack( "zOutput", "Z Output", this, JackType.JackType_AudioOutput );
        AddComponent( zOutput );
        zOutput.SetWantsMouseNotifications( false );
        zOutput.SetPosition( 39, 195 );
        zOutput.SetSize( 37, 37 );
        zOutput.SetSkin( "Rotated Half" );

        oneLabel = new VoltageLabel( "oneLabel", "One Label", this, "1" );
        AddComponent( oneLabel );
        oneLabel.SetWantsMouseNotifications( false );
        oneLabel.SetPosition( 16, 270 );
        oneLabel.SetSize( 15, 15 );
        oneLabel.SetEditable( false, false );
        oneLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        oneLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        oneLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        oneLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        oneLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        oneLabel.SetBorderSize( 1 );
        oneLabel.SetMultiLineEdit( false );
        oneLabel.SetIsNumberEditor( false );
        oneLabel.SetNumberEditorRange( 0, 100 );
        oneLabel.SetNumberEditorInterval( 1 );
        oneLabel.SetNumberEditorUsesMouseWheel( false );
        oneLabel.SetHasCustomTextHoverColor( false );
        oneLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        oneLabel.SetFont( "Arial", 15, true, false );

        oneOutput = new VoltageAudioJack( "oneOutput", "One Output", this, JackType.JackType_AudioOutput );
        AddComponent( oneOutput );
        oneOutput.SetWantsMouseNotifications( false );
        oneOutput.SetPosition( 5, 290 );
        oneOutput.SetSize( 37, 37 );
        oneOutput.SetSkin( "Rotated Half" );

        twoLabel = new VoltageLabel( "twoLabel", "Two Label", this, "2" );
        AddComponent( twoLabel );
        twoLabel.SetWantsMouseNotifications( false );
        twoLabel.SetPosition( 84, 270 );
        twoLabel.SetSize( 15, 15 );
        twoLabel.SetEditable( false, false );
        twoLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        twoLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        twoLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        twoLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        twoLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        twoLabel.SetBorderSize( 1 );
        twoLabel.SetMultiLineEdit( false );
        twoLabel.SetIsNumberEditor( false );
        twoLabel.SetNumberEditorRange( 0, 100 );
        twoLabel.SetNumberEditorInterval( 1 );
        twoLabel.SetNumberEditorUsesMouseWheel( false );
        twoLabel.SetHasCustomTextHoverColor( false );
        twoLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        twoLabel.SetFont( "Arial", 15, true, false );

        twoOutput = new VoltageAudioJack( "twoOutput", "Two Output", this, JackType.JackType_AudioOutput );
        AddComponent( twoOutput );
        twoOutput.SetWantsMouseNotifications( false );
        twoOutput.SetPosition( 73, 290 );
        twoOutput.SetSize( 37, 37 );
        twoOutput.SetSkin( "Rotated Half" );

        signalLabel = new VoltageLabel( "signalLabel", "Signal Label", this, "S" );
        AddComponent( signalLabel );
        signalLabel.SetWantsMouseNotifications( false );
        signalLabel.SetPosition( 50, 255 );
        signalLabel.SetSize( 14, 15 );
        signalLabel.SetEditable( false, false );
        signalLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        signalLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        signalLabel.SetColor( new Color( 232, 232, 232, 255 ) );
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
        signalLabel.SetFont( "Arial", 15, true, false );

        signalInput = new VoltageAudioJack( "signalInput", "Signal Input", this, JackType.JackType_AudioInput );
        AddComponent( signalInput );
        signalInput.SetWantsMouseNotifications( false );
        signalInput.SetPosition( 39, 270 );
        signalInput.SetSize( 37, 37 );
        signalInput.SetSkin( "Dark Jack Straight" );

        yTwoBiasLabel = new VoltageLabel( "yTwoBiasLabel", "Y Two Bias Label", this, "Y / 2" );
        AddComponent( yTwoBiasLabel );
        yTwoBiasLabel.SetWantsMouseNotifications( false );
        yTwoBiasLabel.SetPosition( 70, 120 );
        yTwoBiasLabel.SetSize( 25, 10 );
        yTwoBiasLabel.SetEditable( false, false );
        yTwoBiasLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        yTwoBiasLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        yTwoBiasLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        yTwoBiasLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        yTwoBiasLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        yTwoBiasLabel.SetBorderSize( 1 );
        yTwoBiasLabel.SetMultiLineEdit( false );
        yTwoBiasLabel.SetIsNumberEditor( false );
        yTwoBiasLabel.SetNumberEditorRange( 0, 100 );
        yTwoBiasLabel.SetNumberEditorInterval( 1 );
        yTwoBiasLabel.SetNumberEditorUsesMouseWheel( false );
        yTwoBiasLabel.SetHasCustomTextHoverColor( false );
        yTwoBiasLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        yTwoBiasLabel.SetFont( "Arial", 10, true, false );

        xOneBiasLabel = new VoltageLabel( "xOneBiasLabel", "X One Bias Label", this, "X / 1" );
        AddComponent( xOneBiasLabel );
        xOneBiasLabel.SetWantsMouseNotifications( false );
        xOneBiasLabel.SetPosition( 15, 120 );
        xOneBiasLabel.SetSize( 25, 10 );
        xOneBiasLabel.SetEditable( false, false );
        xOneBiasLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        xOneBiasLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        xOneBiasLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        xOneBiasLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        xOneBiasLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        xOneBiasLabel.SetBorderSize( 1 );
        xOneBiasLabel.SetMultiLineEdit( false );
        xOneBiasLabel.SetIsNumberEditor( false );
        xOneBiasLabel.SetNumberEditorRange( 0, 100 );
        xOneBiasLabel.SetNumberEditorInterval( 1 );
        xOneBiasLabel.SetNumberEditorUsesMouseWheel( false );
        xOneBiasLabel.SetHasCustomTextHoverColor( false );
        xOneBiasLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        xOneBiasLabel.SetFont( "Arial", 10, true, false );

        biasLabel = new VoltageLabel( "biasLabel", "Bias Label", this, "BIAS" );
        AddComponent( biasLabel );
        biasLabel.SetWantsMouseNotifications( false );
        biasLabel.SetPosition( 45, 30 );
        biasLabel.SetSize( 25, 10 );
        biasLabel.SetEditable( false, false );
        biasLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        biasLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        biasLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        biasLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        biasLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        biasLabel.SetBorderSize( 1 );
        biasLabel.SetMultiLineEdit( false );
        biasLabel.SetIsNumberEditor( false );
        biasLabel.SetNumberEditorRange( 0, 100 );
        biasLabel.SetNumberEditorInterval( 1 );
        biasLabel.SetNumberEditorUsesMouseWheel( false );
        biasLabel.SetHasCustomTextHoverColor( false );
        biasLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        biasLabel.SetFont( "Arial", 10, true, false );

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

        faderLawLabel = new VoltageLabel( "faderLawLabel", "Fader Law Label", this, "LINEAR" );
        AddComponent( faderLawLabel );
        faderLawLabel.SetWantsMouseNotifications( false );
        faderLawLabel.SetPosition( 38, 119 );
        faderLawLabel.SetSize( 35, 13 );
        faderLawLabel.SetEditable( false, false );
        faderLawLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        faderLawLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        faderLawLabel.SetColor( new Color( 232, 232, 232, 51 ) );
        faderLawLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        faderLawLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        faderLawLabel.SetBorderSize( 1 );
        faderLawLabel.SetMultiLineEdit( false );
        faderLawLabel.SetIsNumberEditor( false );
        faderLawLabel.SetNumberEditorRange( 0, 100 );
        faderLawLabel.SetNumberEditorInterval( 1 );
        faderLawLabel.SetNumberEditorUsesMouseWheel( false );
        faderLawLabel.SetHasCustomTextHoverColor( false );
        faderLawLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        faderLawLabel.SetFont( "Arial", 9, true, false );
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
        faderDistr = new FaderDistrCore(false, SAMPLE_RATE);
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
        faderDistr.process(bias.GetValue(), readInput(signalInput),
                readInput(xInput), readInput(yInput));
        zOutput.SetValue(faderDistr.getMixOutput());
        oneOutput.SetValue(faderDistr.getLeftOutput());
        twoOutput.SetValue(faderDistr.getRightOutput());
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
        faderDistr.processBypassed(readInput(signalInput), readInput(xInput));
        zOutput.SetValue(faderDistr.getMixOutput());
        oneOutput.SetValue(faderDistr.getLeftOutput());
        twoOutput.SetValue(faderDistr.getRightOutput());
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
        if (component == bias) {
    return "BIAS: " + displayBias(bias.GetValue())
            + " (linear; -1 = X/1, 0 = equal 50/50, +1 = Y/2)";
}
        if (component == xInput) return "FADER X input";
        if (component == yInput) return "FADER Y input";
        if (component == zOutput) return "FADER Z: linear X/Y mix";
        if (component == signalInput) return "DISTR S signal input";
        if (component == oneOutput) return "DISTR 1: linear S distribution";
        if (component == twoOutput) return "DISTR 2: linear S distribution";
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
    private VoltageLabel faderLawLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageLabel biasLabel;
    private VoltageLabel xOneBiasLabel;
    private VoltageLabel yTwoBiasLabel;
    private VoltageAudioJack signalInput;
    private VoltageLabel signalLabel;
    private VoltageAudioJack twoOutput;
    private VoltageLabel twoLabel;
    private VoltageAudioJack oneOutput;
    private VoltageLabel oneLabel;
    private VoltageAudioJack zOutput;
    private VoltageAudioJack yInput;
    private VoltageAudioJack xInput;
    private VoltageLabel zLabel;
    private VoltageLabel yLabel;
    private VoltageLabel xLabel;
    private VoltageKnob bias;
    private VoltageLabel numberLabel;
    private VoltageLabel distrSectionLabel;
    private VoltageLabel faderSectionLabel;
    private VoltageLabel descriptionLabel;


    //[user-code-and-variables]    Add your own variables and functions here
    // Fader|Distr A: fixed linear law, two simultaneous mono functions.
    private static final double SAMPLE_RATE = 48000.0;
    private FaderDistrCore faderDistr;

    private static double readInput(VoltageAudioJack jack) {
    if (!jack.IsConnected()) {
        return 0.0;
    }

    double value = jack.GetValue();
    return Double.isFinite(value) ? value : 0.0;
}

    private static double displayBias(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private static final class FaderDistrCore {
        private static final double SMOOTH_SECONDS = 0.005;

        private final boolean equalPower;
        private final WeightRamp leftWeight;
        private final WeightRamp rightWeight;
        private final RelayVoice signalVoice;
        private final RelayVoice xVoice;
        private final RelayVoice yVoice;
        private boolean resumePending = true;
        private double targetPosition = Double.NaN;
        private double leftOutput;
        private double rightOutput;
        private double mixOutput;

        private FaderDistrCore(boolean equalPower, double sampleRate) {
            if (!Double.isFinite(sampleRate) || sampleRate < 1 || sampleRate > 1_000_000) {
                throw new IllegalArgumentException("Invalid sample rate");
            }
            this.equalPower = equalPower;
            int smoothingSamples = Math.max(1, (int) Math.round(sampleRate * SMOOTH_SECONDS));
            double center = equalPower ? Math.sqrt(0.5) : 0.5;
            leftWeight = new WeightRamp(center, smoothingSamples);
            rightWeight = new WeightRamp(center, smoothingSamples);
            signalVoice = new RelayVoice(sampleRate);
            xVoice = new RelayVoice(sampleRate);
            yVoice = new RelayVoice(sampleRate);
        }

        public void process(double position, double panInput, double inputA, double inputB) {
            setPosition(position);
            if (resumePending) {
                leftWeight.snap();
                rightWeight.snap();
                resumePending = false;
            }
            double left = leftWeight.next();
            double right = rightWeight.next();
            double voicedSignal = signalVoice.process(panInput);
            double voicedX = xVoice.process(inputA);
            double voicedY = yVoice.process(inputB);
            leftOutput = voicedSignal * left;
            rightOutput = voicedSignal * right;
            mixOutput = voicedX * left + voicedY * right;
        }

        public void processBypassed(double panInput, double inputA) {
            leftOutput = panInput;
            rightOutput = panInput;
            mixOutput = inputA;
            signalVoice.reset();
            xVoice.reset();
            yVoice.reset();
            resumePending = true;
        }

        public double getLeftOutput() {
            return leftOutput;
        }

        public double getRightOutput() {
            return rightOutput;
        }

        public double getMixOutput() {
            return mixOutput;
        }

        private void setPosition(double position) {
            double limited = clamp(position, -1, 1);
            if (limited == targetPosition) return;
            targetPosition = limited;
            double p = 0.5 * (limited + 1);
            if (equalPower) {
                leftWeight.setTarget(Math.cos(0.5 * Math.PI * p));
                rightWeight.setTarget(Math.sin(0.5 * Math.PI * p));
            } else {
                leftWeight.setTarget(1 - p);
                rightWeight.setTarget(p);
            }
        }

        private static double clamp(double value, double minimum, double maximum) {
            if (!Double.isFinite(value)) return 0;
            return Math.max(minimum, Math.min(maximum, value));
        }

        private static final class WeightRamp {
            private final int durationSamples;
            private double current;
            private double target;
            private double step;
            private int remainingSamples;

            private WeightRamp(double initial, int durationSamples) {
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

        // Fixed relay/contact character: a soft asymmetric knee and one low-pass state per input.
        // This remains intentionally native-rate and level-matched at either fully selected output.
        private static final class RelayVoice {
            private static final double KNEE_VOLTS = 1.5;
            private static final double DRIVE_START_VOLTS = 0.9;
            private static final double DRIVE_SPAN_VOLTS = 5.0;
            private static final double POSITIVE_COMPRESSION = 0.10;
            private static final double NEGATIVE_COMPRESSION = 0.08;
            private static final double CLEAN_CUTOFF_HZ = 10_000.0;
            private static final double DRIVEN_CUTOFF_HZ = 6_500.0;

            private final double cleanCoefficient;
            private final double drivenCoefficient;
            private double toneState;
            private boolean ready;

            private RelayVoice(double sampleRate) {
                cleanCoefficient = onePoleCoefficient(CLEAN_CUTOFF_HZ, sampleRate);
                drivenCoefficient = onePoleCoefficient(DRIVEN_CUTOFF_HZ, sampleRate);
            }

            private double process(double input) {
                double shaped = shape(input);
                if (!ready) {
                    toneState = shaped;
                    ready = true;
                    return toneState;
                }
                double drive = clamp((Math.abs(input) - DRIVE_START_VOLTS) / DRIVE_SPAN_VOLTS, 0, 1);
                double coefficient = cleanCoefficient + drive * (drivenCoefficient - cleanCoefficient);
                toneState += coefficient * (shaped - toneState);
                return toneState;
            }

            private void reset() {
                ready = false;
                toneState = 0;
            }

            private static double shape(double input) {
                double magnitude = Math.abs(input);
                if (magnitude <= KNEE_VOLTS) return input;
                double excess = magnitude - KNEE_VOLTS;
                double compression = input >= 0 ? POSITIVE_COMPRESSION : NEGATIVE_COMPRESSION;
                double shaped = KNEE_VOLTS + excess / (1 + compression * excess);
                return Math.copySign(shaped, input);
            }

            private static double onePoleCoefficient(double cutoff, double sampleRate) {
                return 1 - Math.exp(-2 * Math.PI * cutoff / sampleRate);
            }
        }
    }





    //[/user-code-and-variables]
}

 