package com.insectlabs.sw2;


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


public class sw2 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public sw2( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "sw2 - Switcher – Distributer", ModuleType.ModuleType_Switches, 2.0 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "e5f136ffa6b14539a46a668c0a466c93" );
    }

void InitializeControls()
{

        sourceInput1 = new VoltageAudioJack( "sourceInput1", "Source Input 1", this, JackType.JackType_AudioInput );
        AddComponent( sourceInput1 );
        sourceInput1.SetWantsMouseNotifications( false );
        sourceInput1.SetPosition( 10, 130 );
        sourceInput1.SetSize( 37, 37 );
        sourceInput1.SetSkin( "Dark Jack Straight" );

        selectedOutputJack = new VoltageAudioJack( "selectedOutputJack", "Selected Output Jack", this, JackType.JackType_AudioOutput );
        AddComponent( selectedOutputJack );
        selectedOutputJack.SetWantsMouseNotifications( false );
        selectedOutputJack.SetPosition( 10, 285 );
        selectedOutputJack.SetSize( 37, 37 );
        selectedOutputJack.SetSkin( "Rotated Half" );

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Module Description", this, "switcher – distributer" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 3 );
        descriptionLabel.SetSize( 138, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        descriptionLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
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

        numberLabel = new VoltageLabel( "numberLabel", "Model Number", this, "sw2" );
        AddComponent( numberLabel );
        numberLabel.SetWantsMouseNotifications( false );
        numberLabel.SetPosition( 115, 335 );
        numberLabel.SetSize( 26, 13 );
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

        routeKnob = new VoltageKnob( "routeKnob", "Route Knob", this, 0, 4, 0 );
        AddComponent( routeKnob );
        routeKnob.SetWantsMouseNotifications( false );
        routeKnob.SetPosition( 32, 45 );
        routeKnob.SetSize( 80, 80 );
        routeKnob.SetSkin( "Cosmo Large Black" );
        routeKnob.SetRange( 0, 4, 0, false, 0 );
        routeKnob.SetKnobParams( 290, 70 );
        routeKnob.DisplayValueInPercent( false );
        routeKnob.SetKnobAdjustsRing( true );

        offLabel = new VoltageLabel( "offLabel", "Off Label", this, "O" );
        AddComponent( offLabel );
        offLabel.SetWantsMouseNotifications( false );
        offLabel.SetPosition( 18, 61 );
        offLabel.SetSize( 15, 15 );
        offLabel.SetEditable( false, false );
        offLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        offLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        offLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        offLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        offLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        offLabel.SetBorderSize( 1 );
        offLabel.SetMultiLineEdit( false );
        offLabel.SetIsNumberEditor( false );
        offLabel.SetNumberEditorRange( 0, 100 );
        offLabel.SetNumberEditorInterval( 1 );
        offLabel.SetNumberEditorUsesMouseWheel( false );
        offLabel.SetHasCustomTextHoverColor( false );
        offLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        offLabel.SetFont( "Arial", 13, true, false );

        route1Label = new VoltageLabel( "route1Label", "Route 1 Label", this, "1" );
        AddComponent( route1Label );
        route1Label.SetWantsMouseNotifications( false );
        route1Label.SetPosition( 34, 40 );
        route1Label.SetSize( 15, 15 );
        route1Label.SetEditable( false, false );
        route1Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        route1Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        route1Label.SetColor( new Color( 232, 232, 232, 255 ) );
        route1Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        route1Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        route1Label.SetBorderSize( 1 );
        route1Label.SetMultiLineEdit( false );
        route1Label.SetIsNumberEditor( false );
        route1Label.SetNumberEditorRange( 0, 100 );
        route1Label.SetNumberEditorInterval( 1 );
        route1Label.SetNumberEditorUsesMouseWheel( false );
        route1Label.SetHasCustomTextHoverColor( false );
        route1Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        route1Label.SetFont( "Arial", 13, true, false );

        destination1Label = new VoltageLabel( "destination1Label", "Destination 1 Label", this, "1" );
        AddComponent( destination1Label );
        destination1Label.SetWantsMouseNotifications( false );
        destination1Label.SetPosition( 20, 270 );
        destination1Label.SetSize( 15, 15 );
        destination1Label.SetEditable( false, false );
        destination1Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        destination1Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        destination1Label.SetColor( new Color( 232, 232, 232, 255 ) );
        destination1Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        destination1Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        destination1Label.SetBorderSize( 1 );
        destination1Label.SetMultiLineEdit( false );
        destination1Label.SetIsNumberEditor( false );
        destination1Label.SetNumberEditorRange( 0, 100 );
        destination1Label.SetNumberEditorInterval( 1 );
        destination1Label.SetNumberEditorUsesMouseWheel( false );
        destination1Label.SetHasCustomTextHoverColor( false );
        destination1Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        destination1Label.SetFont( "Arial", 13, true, false );

        route2Label = new VoltageLabel( "route2Label", "Route 2 Label", this, "2" );
        AddComponent( route2Label );
        route2Label.SetWantsMouseNotifications( false );
        route2Label.SetPosition( 65, 31 );
        route2Label.SetSize( 15, 15 );
        route2Label.SetEditable( false, false );
        route2Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        route2Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        route2Label.SetColor( new Color( 232, 232, 232, 255 ) );
        route2Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        route2Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        route2Label.SetBorderSize( 1 );
        route2Label.SetMultiLineEdit( false );
        route2Label.SetIsNumberEditor( false );
        route2Label.SetNumberEditorRange( 0, 100 );
        route2Label.SetNumberEditorInterval( 1 );
        route2Label.SetNumberEditorUsesMouseWheel( false );
        route2Label.SetHasCustomTextHoverColor( false );
        route2Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        route2Label.SetFont( "Arial", 13, true, false );

        destination2Label = new VoltageLabel( "destination2Label", "Destination 2 Label", this, "2" );
        AddComponent( destination2Label );
        destination2Label.SetWantsMouseNotifications( false );
        destination2Label.SetPosition( 50, 270 );
        destination2Label.SetSize( 15, 15 );
        destination2Label.SetEditable( false, false );
        destination2Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        destination2Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        destination2Label.SetColor( new Color( 232, 232, 232, 255 ) );
        destination2Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        destination2Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        destination2Label.SetBorderSize( 1 );
        destination2Label.SetMultiLineEdit( false );
        destination2Label.SetIsNumberEditor( false );
        destination2Label.SetNumberEditorRange( 0, 100 );
        destination2Label.SetNumberEditorInterval( 1 );
        destination2Label.SetNumberEditorUsesMouseWheel( false );
        destination2Label.SetHasCustomTextHoverColor( false );
        destination2Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        destination2Label.SetFont( "Arial", 13, true, false );

        route3Label = new VoltageLabel( "route3Label", "Route 3 Label", this, "3" );
        AddComponent( route3Label );
        route3Label.SetWantsMouseNotifications( false );
        route3Label.SetPosition( 95, 40 );
        route3Label.SetSize( 15, 15 );
        route3Label.SetEditable( false, false );
        route3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        route3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        route3Label.SetColor( new Color( 232, 232, 232, 255 ) );
        route3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        route3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        route3Label.SetBorderSize( 1 );
        route3Label.SetMultiLineEdit( false );
        route3Label.SetIsNumberEditor( false );
        route3Label.SetNumberEditorRange( 0, 100 );
        route3Label.SetNumberEditorInterval( 1 );
        route3Label.SetNumberEditorUsesMouseWheel( false );
        route3Label.SetHasCustomTextHoverColor( false );
        route3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        route3Label.SetFont( "Arial", 13, true, false );

        destination3Label = new VoltageLabel( "destination3Label", "Destination 3 Label", this, "3" );
        AddComponent( destination3Label );
        destination3Label.SetWantsMouseNotifications( false );
        destination3Label.SetPosition( 80, 270 );
        destination3Label.SetSize( 15, 15 );
        destination3Label.SetEditable( false, false );
        destination3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        destination3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        destination3Label.SetColor( new Color( 232, 232, 232, 255 ) );
        destination3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        destination3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        destination3Label.SetBorderSize( 1 );
        destination3Label.SetMultiLineEdit( false );
        destination3Label.SetIsNumberEditor( false );
        destination3Label.SetNumberEditorRange( 0, 100 );
        destination3Label.SetNumberEditorInterval( 1 );
        destination3Label.SetNumberEditorUsesMouseWheel( false );
        destination3Label.SetHasCustomTextHoverColor( false );
        destination3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        destination3Label.SetFont( "Arial", 13, true, false );

        route4Label = new VoltageLabel( "route4Label", "Route 4 Label", this, "4" );
        AddComponent( route4Label );
        route4Label.SetWantsMouseNotifications( false );
        route4Label.SetPosition( 110, 61 );
        route4Label.SetSize( 15, 15 );
        route4Label.SetEditable( false, false );
        route4Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        route4Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        route4Label.SetColor( new Color( 232, 232, 232, 255 ) );
        route4Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        route4Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        route4Label.SetBorderSize( 1 );
        route4Label.SetMultiLineEdit( false );
        route4Label.SetIsNumberEditor( false );
        route4Label.SetNumberEditorRange( 0, 100 );
        route4Label.SetNumberEditorInterval( 1 );
        route4Label.SetNumberEditorUsesMouseWheel( false );
        route4Label.SetHasCustomTextHoverColor( false );
        route4Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        route4Label.SetFont( "Arial", 13, true, false );

        destination4Label = new VoltageLabel( "destination4Label", "Destination 4 Label", this, "4" );
        AddComponent( destination4Label );
        destination4Label.SetWantsMouseNotifications( false );
        destination4Label.SetPosition( 110, 270 );
        destination4Label.SetSize( 15, 15 );
        destination4Label.SetEditable( false, false );
        destination4Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        destination4Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        destination4Label.SetColor( new Color( 232, 232, 232, 255 ) );
        destination4Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        destination4Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        destination4Label.SetBorderSize( 1 );
        destination4Label.SetMultiLineEdit( false );
        destination4Label.SetIsNumberEditor( false );
        destination4Label.SetNumberEditorRange( 0, 100 );
        destination4Label.SetNumberEditorInterval( 1 );
        destination4Label.SetNumberEditorUsesMouseWheel( false );
        destination4Label.SetHasCustomTextHoverColor( false );
        destination4Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        destination4Label.SetFont( "Arial", 13, true, false );

        clickFilterLabel = new VoltageLabel( "clickFilterLabel", "Click Filter Label", this, "CLK" );
        AddComponent( clickFilterLabel );
        clickFilterLabel.SetWantsMouseNotifications( false );
        clickFilterLabel.SetPosition( 0, 313 );
        clickFilterLabel.SetSize( 144, 8 );
        clickFilterLabel.SetEditable( false, false );
        clickFilterLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        clickFilterLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        clickFilterLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        clickFilterLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        clickFilterLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        clickFilterLabel.SetBorderSize( 1 );
        clickFilterLabel.SetMultiLineEdit( false );
        clickFilterLabel.SetIsNumberEditor( false );
        clickFilterLabel.SetNumberEditorRange( 0, 100 );
        clickFilterLabel.SetNumberEditorInterval( 1 );
        clickFilterLabel.SetNumberEditorUsesMouseWheel( false );
        clickFilterLabel.SetHasCustomTextHoverColor( false );
        clickFilterLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        clickFilterLabel.SetFont( "Arial", 6, true, false );

        sourceInput2 = new VoltageAudioJack( "sourceInput2", "Source Input 2", this, JackType.JackType_AudioInput );
        AddComponent( sourceInput2 );
        sourceInput2.SetWantsMouseNotifications( false );
        sourceInput2.SetPosition( 40, 160 );
        sourceInput2.SetSize( 37, 37 );
        sourceInput2.SetSkin( "Dark Jack Straight" );

        sourceInput3 = new VoltageAudioJack( "sourceInput3", "Source Input 3", this, JackType.JackType_AudioInput );
        AddComponent( sourceInput3 );
        sourceInput3.SetWantsMouseNotifications( false );
        sourceInput3.SetPosition( 70, 130 );
        sourceInput3.SetSize( 37, 37 );
        sourceInput3.SetSkin( "Dark Jack Straight" );

        sourceInput4 = new VoltageAudioJack( "sourceInput4", "Source Input 4", this, JackType.JackType_AudioInput );
        AddComponent( sourceInput4 );
        sourceInput4.SetWantsMouseNotifications( false );
        sourceInput4.SetPosition( 100, 160 );
        sourceInput4.SetSize( 37, 37 );
        sourceInput4.SetSkin( "Dark Jack Straight" );

        destinationOutput1 = new VoltageAudioJack( "destinationOutput1", "Destination Output 1", this, JackType.JackType_AudioOutput );
        AddComponent( destinationOutput1 );
        destinationOutput1.SetWantsMouseNotifications( false );
        destinationOutput1.SetPosition( 10, 200 );
        destinationOutput1.SetSize( 37, 37 );
        destinationOutput1.SetSkin( "Rotated Half" );

        destinationOutput2 = new VoltageAudioJack( "destinationOutput2", "Destination Output 2", this, JackType.JackType_AudioOutput );
        AddComponent( destinationOutput2 );
        destinationOutput2.SetWantsMouseNotifications( false );
        destinationOutput2.SetPosition( 40, 230 );
        destinationOutput2.SetSize( 37, 37 );
        destinationOutput2.SetSkin( "Rotated Half" );

        destinationOutput3 = new VoltageAudioJack( "destinationOutput3", "Destination Output 3", this, JackType.JackType_AudioOutput );
        AddComponent( destinationOutput3 );
        destinationOutput3.SetWantsMouseNotifications( false );
        destinationOutput3.SetPosition( 70, 200 );
        destinationOutput3.SetSize( 37, 37 );
        destinationOutput3.SetSkin( "Rotated Half" );

        destinationOutput4 = new VoltageAudioJack( "destinationOutput4", "Destination Output 4", this, JackType.JackType_AudioOutput );
        AddComponent( destinationOutput4 );
        destinationOutput4.SetWantsMouseNotifications( false );
        destinationOutput4.SetPosition( 100, 230 );
        destinationOutput4.SetSize( 37, 37 );
        destinationOutput4.SetSkin( "Rotated Half" );

        clickFilterSwitch = new VoltageSwitch( "clickFilterSwitch", "Click Filter Switch", this, 1 );
        AddComponent( clickFilterSwitch );
        clickFilterSwitch.SetWantsMouseNotifications( false );
        clickFilterSwitch.SetPosition( 67, 297 );
        clickFilterSwitch.SetSize( 10, 15 );
        clickFilterSwitch.SetSkin( "2-State Slide Black" );

        selectedOutputLabel = new VoltageLabel( "selectedOutputLabel", "Selected Output Label", this, "X" );
        AddComponent( selectedOutputLabel );
        selectedOutputLabel.SetWantsMouseNotifications( false );
        selectedOutputLabel.SetPosition( 20, 320 );
        selectedOutputLabel.SetSize( 15, 15 );
        selectedOutputLabel.SetEditable( false, false );
        selectedOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        selectedOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        selectedOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        selectedOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        selectedOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        selectedOutputLabel.SetBorderSize( 1 );
        selectedOutputLabel.SetMultiLineEdit( false );
        selectedOutputLabel.SetIsNumberEditor( false );
        selectedOutputLabel.SetNumberEditorRange( 0, 100 );
        selectedOutputLabel.SetNumberEditorInterval( 1 );
        selectedOutputLabel.SetNumberEditorUsesMouseWheel( false );
        selectedOutputLabel.SetHasCustomTextHoverColor( false );
        selectedOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        selectedOutputLabel.SetFont( "Arial", 13, true, false );

        commonInput = new VoltageAudioJack( "commonInput", "Common Input", this, JackType.JackType_AudioInput );
        AddComponent( commonInput );
        commonInput.SetWantsMouseNotifications( false );
        commonInput.SetPosition( 99, 285 );
        commonInput.SetSize( 37, 37 );
        commonInput.SetSkin( "Dark Jack Straight" );

        commonInputLabel = new VoltageLabel( "commonInputLabel", "Common Input Label", this, "Y" );
        AddComponent( commonInputLabel );
        commonInputLabel.SetWantsMouseNotifications( false );
        commonInputLabel.SetPosition( 109, 320 );
        commonInputLabel.SetSize( 15, 15 );
        commonInputLabel.SetEditable( false, false );
        commonInputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        commonInputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        commonInputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        commonInputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        commonInputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        commonInputLabel.SetBorderSize( 1 );
        commonInputLabel.SetMultiLineEdit( false );
        commonInputLabel.SetIsNumberEditor( false );
        commonInputLabel.SetNumberEditorRange( 0, 100 );
        commonInputLabel.SetNumberEditorInterval( 1 );
        commonInputLabel.SetNumberEditorUsesMouseWheel( false );
        commonInputLabel.SetHasCustomTextHoverColor( false );
        commonInputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        commonInputLabel.SetFont( "Arial", 13, true, false );

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

        colophon = new VoltageLabel( "colophon", "Colophon", this, "insect laboratories pittsburgh, PA               –– 1939 ––" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 42, 335 );
        colophon.SetSize( 60, 20 );
        colophon.SetEditable( false, false );
        colophon.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        colophon.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        colophon.SetColor( new Color( 232, 232, 232, 85 ) );
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
        resetRouting();
        cachedPosition = selectedPosition();
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
            case Knob_Changed:
                if (component == routeKnob) cachedPosition = selectedPosition();
                break;
            case Reset:
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
            case Randomized:
                resetRouting();
                cachedPosition = selectedPosition();
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

        cachedPosition = selectedPosition();
        processRouting(
                cachedPosition,
                readInput(sourceInput1), readInput(sourceInput2),
                readInput(sourceInput3), readInput(sourceInput4),
                readInput(commonInput), clickFilterEnabled());
        selectedOutputJack.SetValue(selectedOutput);
        destinationOutput1.SetValue(destinationOutputs[0]);
        destinationOutput2.SetValue(destinationOutputs[1]);
        destinationOutput3.SetValue(destinationOutputs[2]);
        destinationOutput4.SetValue(destinationOutputs[3]);
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

        processBypassedRouting(
                cachedPosition,
                cachedPosition == 1 ? readInput(sourceInput1) : 0.0, cachedPosition == 2 ? readInput(sourceInput2) : 0.0,
                cachedPosition == 3 ? readInput(sourceInput3) : 0.0, cachedPosition == 4 ? readInput(sourceInput4) : 0.0,
                cachedPosition == 0 ? 0.0 : readInput(commonInput));
        selectedOutputJack.SetValue(selectedOutput);
        destinationOutput1.SetValue(destinationOutputs[0]);
        destinationOutput2.SetValue(destinationOutputs[1]);
        destinationOutput3.SetValue(destinationOutputs[2]);
        destinationOutput4.SetValue(destinationOutputs[3]);
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
        if (component == routeKnob) {
            int position = selectedPosition();
            return position == 0 ? "ROUTE: OFF" : "ROUTE " + position + ": source to X; Y to destination";
        }
        if (component == clickFilterSwitch) {
        return clickFilterEnabled()
            ? "CLK ON: historical dark contact voicing; 2 ms settling and 2.2 kHz filtering"
            : "CLK OFF: immediate hard routing; amplifier character remains active";
            }
        if (component == selectedOutputJack) return "X: selected source output (OFF at route 0)";
        if (component == commonInput) return "Y: common input to selected destination";
        if (component == sourceInput1) return "SOURCE 1: routed to X at position 1";
        if (component == destinationOutput1) return "DESTINATION 1: receives Y at position 1";
        if (component == sourceInput2) return "SOURCE 2: routed to X at position 2";
        if (component == destinationOutput2) return "DESTINATION 2: receives Y at position 2";
        if (component == sourceInput3) return "SOURCE 3: routed to X at position 3";
        if (component == destinationOutput3) return "DESTINATION 3: receives Y at position 3";
        if (component == sourceInput4) return "SOURCE 4: routed to X at position 4";
        if (component == destinationOutput4) return "DESTINATION 4: receives Y at position 4";
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
    private VoltageLabel colophon;
    private VoltageLabel manufacturerLabel;
    private VoltageLabel commonInputLabel;
    private VoltageAudioJack commonInput;
    private VoltageLabel selectedOutputLabel;
    private VoltageSwitch clickFilterSwitch;
    private VoltageAudioJack destinationOutput4;
    private VoltageAudioJack destinationOutput3;
    private VoltageAudioJack destinationOutput2;
    private VoltageAudioJack destinationOutput1;
    private VoltageAudioJack sourceInput4;
    private VoltageAudioJack sourceInput3;
    private VoltageAudioJack sourceInput2;
    private VoltageLabel clickFilterLabel;
    private VoltageLabel destination4Label;
    private VoltageLabel route4Label;
    private VoltageLabel destination3Label;
    private VoltageLabel route3Label;
    private VoltageLabel destination2Label;
    private VoltageLabel route2Label;
    private VoltageLabel destination1Label;
    private VoltageLabel route1Label;
    private VoltageLabel offLabel;
    private VoltageKnob routeKnob;
    private VoltageLabel numberLabel;
    private VoltageLabel descriptionLabel;
    private VoltageAudioJack selectedOutputJack;
    private VoltageAudioJack sourceInput1;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    // Slightly more open rotary-contact voice than sw1; unity DC and settling stay intact.
    private static final double CLICK_FILTER_CUTOFF_HZ = 2200.0;
    private static final int RELAY_SETTLE_SAMPLES = 1024;
    private final double[] destinationOutputs = new double[4];
    private final double[] destinationFilterStates = new double[4];
    private final boolean[] destinationFilterReady = new boolean[4];
    private final double clickFilterCoefficient = 1.0 - Math.exp(
            -2.0 * Math.PI * CLICK_FILTER_CUTOFF_HZ / SAMPLE_RATE);
    // Two identical amplifier stages: X after source selection, Y before distribution.
    // 2x midpoint processing follows SIGPROC; coefficients are computed only once.
    private static final double AMPLIFIER_KNEE_VOLTS = 3.0;
    private static final double AMPLIFIER_CUTOFF_HZ = 14000.0;
    private static final double AMPLIFIER_KNEE_WIDTH = 4.0;
    private static final double AMPLIFIER_POSITIVE_AMOUNT = 0.30;
    private static final double AMPLIFIER_NEGATIVE_AMOUNT = 0.33;
    private final double amplifierCoefficient = 1.0 - Math.exp(
            -2.0 * Math.PI * AMPLIFIER_CUTOFF_HZ / (SAMPLE_RATE * 2.0));
    private final double[] amplifierPrevious = new double[2];
    private final double[] amplifierState = new double[2];
    private final boolean[] amplifierReady = new boolean[2];
    private boolean resumePending;
    private volatile int cachedPosition;
    private final double[] routeWeights = new double[4];
    private double selectedOutput;
    private double selectedFilterState;
    private boolean selectedFilterReady;
    private int newPosition;
    private int remainingSamples;
    private boolean routingReady;

    private static double readInput(VoltageAudioJack jack) {
    if (!jack.IsConnected()) {
        return 0.0;
    }

    double value = jack.GetValue();

    if (!Double.isFinite(value)) {
        return 0.0;
    }

    return Math.max(
            -1.0e6,
            Math.min(1.0e6, value)
    );
}

    private int selectedPosition() {
        return (int) Math.round(Math.max(0.0, Math.min(4.0, routeKnob.GetValue())));
    }

    /** CLK is up for direct contacts and down for the softened relay path. */
    private boolean clickFilterEnabled() {
        return clickFilterSwitch.GetValue() < 0.5;
    }

    private void resetRouting() {
        amplifierReady[0] = false;
        amplifierReady[1] = false;
        resumePending = false;
        selectedOutput = 0.0;
        selectedFilterState = 0.0;
        selectedFilterReady = false;
        newPosition = 0;
        remainingSamples = 0;
        routingReady = false;
        for (int index = 0; index < destinationOutputs.length; index++) {
            routeWeights[index] = 0.0;
            destinationOutputs[index] = 0.0;
            destinationFilterStates[index] = 0.0;
            destinationFilterReady[index] = false;
        }
    }

    /** Native-rate paired rotary routing: 1-4 to X and Y to 1-4, zero is OFF. */
    private void processRouting(int requestedPosition, double source1, double source2,
            double source3, double source4, double commonInput, boolean softened) {
        if (resumePending) {
            amplifierReady[0] = false;
            amplifierReady[1] = false;
            resetFilterHistories();
            routingReady = false;
            resumePending = false;
        }
        updateRouteTransition(requestedPosition, softened);
        double sourceMix = source1 * routeWeights[0] + source2 * routeWeights[1]
                + source3 * routeWeights[2] + source4 * routeWeights[3];
        double amplifiedSource = processAmplifier(0, sourceMix);
        // OFF has no amplifier residue once the routing transition finishes.
        if (newPosition == 0 && remainingSamples == 0) {
            amplifiedSource = 0.0;
            amplifierReady[0] = false;
        }
        selectedOutput = filterSelected(amplifiedSource, softened);
        double amplifiedCommon = processAmplifier(1, commonInput);
        for (int index = 0; index < destinationOutputs.length; index++) {
            double target = amplifiedCommon * routeWeights[index];
            destinationOutputs[index] = filterDestination(index, target, softened);
        }
    }

    /** Colorbox-style direct bypass preserves the currently selected route and bypasses all DSP. */
    private void processBypassedRouting(int position, double source1, double source2,
            double source3, double source4, double commonInput) {
        selectedOutput = sourceAt(position, source1, source2, source3, source4);
        for (int index = 0; index < destinationOutputs.length; index++) {
            destinationOutputs[index] = index + 1 == position ? commonInput : 0.0;
        }
        resumePending = true;

    }

    /** Retarget from the current mixture, including during a fast reversal or OFF sweep. */
    private void updateRouteTransition(int requestedPosition, boolean softened) {
        if (!routingReady || !softened) {
            newPosition = requestedPosition;
            remainingSamples = 0;
            routingReady = true;
            for (int index = 0; index < routeWeights.length; index++) {
                routeWeights[index] = index + 1 == requestedPosition ? 1.0 : 0.0;
            }
            return;
        }
        if (requestedPosition != newPosition) {
            newPosition = requestedPosition;
            remainingSamples = RELAY_SETTLE_SAMPLES;
        }
        if (remainingSamples > 0) {
            for (int index = 0; index < routeWeights.length; index++) {
                double target = index + 1 == newPosition ? 1.0 : 0.0;
                routeWeights[index] = remainingSamples == 1 ? target
                        : routeWeights[index] + (target - routeWeights[index]) / remainingSamples;
            }
            remainingSamples--;
        }
    }

    private static double sourceAt(int position, double source1, double source2,
            double source3, double source4) {
        switch (position) {
            case 1: return source1;
            case 2: return source2;
            case 3: return source3;
            case 4: return source4;
            default: return 0.0;
        }
    }

    private double filterSelected(double value, boolean enabled) {
        if (!enabled) {
            selectedFilterState = value;
            selectedFilterReady = true;
            return value;
        }
        if (!selectedFilterReady) {
            selectedFilterState = value;
            selectedFilterReady = true;
        } else {
            selectedFilterState += clickFilterCoefficient * (value - selectedFilterState);
        }
        return selectedFilterState;
    }

    private double filterDestination(int index, double value, boolean enabled) {
        if (!enabled) {
            destinationFilterStates[index] = value;
            destinationFilterReady[index] = true;
            return value;
        }
        if (!destinationFilterReady[index]) {
            destinationFilterStates[index] = value;
            destinationFilterReady[index] = true;
        } else {
            destinationFilterStates[index] += clickFilterCoefficient
                    * (value - destinationFilterStates[index]);
        }
        return destinationFilterStates[index];
    }

    /** Smooth knee with exact unity through +/-3 V and mild asymmetric peak compression.
     * The curve and its first two derivatives join continuously at the knee.
     * There is no added bias, hum, drift, or envelope detector.
     */
    private static double amplifierCurve(double value) {
        double excess = Math.abs(value) - AMPLIFIER_KNEE_VOLTS;
        if (excess <= 0.0) return value;
        double ratio = excess / AMPLIFIER_KNEE_WIDTH;
        double rounded = ratio * ratio / (1.0 + ratio * ratio);
        double amount = value >= 0.0 ? AMPLIFIER_POSITIVE_AMOUNT : AMPLIFIER_NEGATIVE_AMOUNT;
        return value - Math.copySign(amount * excess * rounded, value);
    }

    /** Fixed-cost 2x interpolation and one-pole tone conditioning, as in SIGPROC. */
    private double processAmplifier(int bank, double value) {
        if (!amplifierReady[bank]) {
            amplifierPrevious[bank] = value;
            amplifierState[bank] = amplifierCurve(value);
            amplifierReady[bank] = true;
            return amplifierState[bank];
        }
        double midpoint = (amplifierPrevious[bank] + value) * 0.5;
        amplifierPrevious[bank] = value;
        amplifierState[bank] += amplifierCoefficient
                * (amplifierCurve(midpoint) - amplifierState[bank]);
        amplifierState[bank] += amplifierCoefficient
                * (amplifierCurve(value) - amplifierState[bank]);
        return amplifierState[bank];
    }

    private void resetFilterHistories() {
        selectedFilterState = 0.0;
        selectedFilterReady = false;
        for (int index = 0; index < destinationOutputs.length; index++) {
            destinationFilterStates[index] = 0.0;
            destinationFilterReady[index] = false;
        }
    }
    //[/user-code-and-variables]
}

 