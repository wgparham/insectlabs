package com.insectlabs.standards;


import voltage.controllers.*;
import voltage.core.*;
import voltage.core.Jack.JackType;
import voltage.sources.*;
import voltage.utility.*;
import voltage.processors.*;
import voltage.effects.*;
import java.awt.*;

//[user-imports]   Add your own imports here
import java.util.EnumSet;
import voltage.core.VoltageDigitalCounter.Flags;
//[/user-imports]


public class standards extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape")
    public standards( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "sn46 - standards", ModuleType.ModuleType_Source, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "c26151acdf624d6fba9dc38f46cf9dd4" );
    }

void InitializeControls()
{

        voltageKnob = new VoltageKnob( "voltageKnob", "Voltage", this, 0, 12, 0 );
        AddComponent( voltageKnob );
        voltageKnob.SetWantsMouseNotifications( false );
        voltageKnob.SetPosition( 16, 40 );
        voltageKnob.SetSize( 50, 50 );
        voltageKnob.SetSkin( "Cosmo v2 Pointer" );
        voltageKnob.SetRange( 0, 12, 0, false, 0 );
        voltageKnob.SetKnobParams( 215, 145 );
        voltageKnob.DisplayValueInPercent( false );
        voltageKnob.SetKnobAdjustsRing( true );

        voltageScale9Label = new VoltageLabel( "voltageScale9Label", "Voltage Scale9 Label", this, "9" );
        AddComponent( voltageScale9Label );
        voltageScale9Label.SetWantsMouseNotifications( false );
        voltageScale9Label.SetPosition( 66, 51 );
        voltageScale9Label.SetSize( 10, 10 );
        voltageScale9Label.SetEditable( false, false );
        voltageScale9Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale9Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale9Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale9Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale9Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale9Label.SetBorderSize( 1 );
        voltageScale9Label.SetMultiLineEdit( false );
        voltageScale9Label.SetIsNumberEditor( false );
        voltageScale9Label.SetNumberEditorRange( 0, 100 );
        voltageScale9Label.SetNumberEditorInterval( 1 );
        voltageScale9Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale9Label.SetHasCustomTextHoverColor( false );
        voltageScale9Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale9Label.SetFont( "Arial Black", 10, false, false );

        voltageScale10Label = new VoltageLabel( "voltageScale10Label", "Voltage Scale10 Label", this, "10" );
        AddComponent( voltageScale10Label );
        voltageScale10Label.SetWantsMouseNotifications( false );
        voltageScale10Label.SetPosition( 67, 64 );
        voltageScale10Label.SetSize( 10, 10 );
        voltageScale10Label.SetEditable( false, false );
        voltageScale10Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale10Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale10Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale10Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale10Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale10Label.SetBorderSize( 1 );
        voltageScale10Label.SetMultiLineEdit( false );
        voltageScale10Label.SetIsNumberEditor( false );
        voltageScale10Label.SetNumberEditorRange( 0, 100 );
        voltageScale10Label.SetNumberEditorInterval( 1 );
        voltageScale10Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale10Label.SetHasCustomTextHoverColor( false );
        voltageScale10Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale10Label.SetFont( "Arial Black", 10, false, false );

        voltageScale12Label = new VoltageLabel( "voltageScale12Label", "Voltage Scale12 Label", this, "12" );
        AddComponent( voltageScale12Label );
        voltageScale12Label.SetWantsMouseNotifications( false );
        voltageScale12Label.SetPosition( 55, 86 );
        voltageScale12Label.SetSize( 10, 10 );
        voltageScale12Label.SetEditable( false, false );
        voltageScale12Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale12Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale12Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale12Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale12Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale12Label.SetBorderSize( 1 );
        voltageScale12Label.SetMultiLineEdit( false );
        voltageScale12Label.SetIsNumberEditor( false );
        voltageScale12Label.SetNumberEditorRange( 0, 100 );
        voltageScale12Label.SetNumberEditorInterval( 1 );
        voltageScale12Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale12Label.SetHasCustomTextHoverColor( false );
        voltageScale12Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale12Label.SetFont( "Arial Black", 10, false, false );

        voltageScale11Label = new VoltageLabel( "voltageScale11Label", "Voltage Scale11 Label", this, "11" );
        AddComponent( voltageScale11Label );
        voltageScale11Label.SetWantsMouseNotifications( false );
        voltageScale11Label.SetPosition( 63, 76 );
        voltageScale11Label.SetSize( 10, 10 );
        voltageScale11Label.SetEditable( false, false );
        voltageScale11Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale11Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale11Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale11Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale11Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale11Label.SetBorderSize( 1 );
        voltageScale11Label.SetMultiLineEdit( false );
        voltageScale11Label.SetIsNumberEditor( false );
        voltageScale11Label.SetNumberEditorRange( 0, 100 );
        voltageScale11Label.SetNumberEditorInterval( 1 );
        voltageScale11Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale11Label.SetHasCustomTextHoverColor( false );
        voltageScale11Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale11Label.SetFont( "Arial Black", 10, false, false );

        voltageScale8Label = new VoltageLabel( "voltageScale8Label", "Voltage Scale8 Label", this, "8" );
        AddComponent( voltageScale8Label );
        voltageScale8Label.SetWantsMouseNotifications( false );
        voltageScale8Label.SetPosition( 61, 39 );
        voltageScale8Label.SetSize( 10, 10 );
        voltageScale8Label.SetEditable( false, false );
        voltageScale8Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale8Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale8Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale8Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale8Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale8Label.SetBorderSize( 1 );
        voltageScale8Label.SetMultiLineEdit( false );
        voltageScale8Label.SetIsNumberEditor( false );
        voltageScale8Label.SetNumberEditorRange( 0, 100 );
        voltageScale8Label.SetNumberEditorInterval( 1 );
        voltageScale8Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale8Label.SetHasCustomTextHoverColor( false );
        voltageScale8Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale8Label.SetFont( "Arial Black", 10, false, false );

        voltageScale7Label = new VoltageLabel( "voltageScale7Label", "Voltage Scale7 Label", this, "7" );
        AddComponent( voltageScale7Label );
        voltageScale7Label.SetWantsMouseNotifications( false );
        voltageScale7Label.SetPosition( 49, 31 );
        voltageScale7Label.SetSize( 10, 10 );
        voltageScale7Label.SetEditable( false, false );
        voltageScale7Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale7Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale7Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale7Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale7Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale7Label.SetBorderSize( 1 );
        voltageScale7Label.SetMultiLineEdit( false );
        voltageScale7Label.SetIsNumberEditor( false );
        voltageScale7Label.SetNumberEditorRange( 0, 100 );
        voltageScale7Label.SetNumberEditorInterval( 1 );
        voltageScale7Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale7Label.SetHasCustomTextHoverColor( false );
        voltageScale7Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale7Label.SetFont( "Arial Black", 10, false, false );

        voltageScale6Label = new VoltageLabel( "voltageScale6Label", "Voltage Scale6 Label", this, "6" );
        AddComponent( voltageScale6Label );
        voltageScale6Label.SetWantsMouseNotifications( false );
        voltageScale6Label.SetPosition( 36, 28 );
        voltageScale6Label.SetSize( 10, 10 );
        voltageScale6Label.SetEditable( false, false );
        voltageScale6Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale6Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale6Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale6Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale6Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale6Label.SetBorderSize( 1 );
        voltageScale6Label.SetMultiLineEdit( false );
        voltageScale6Label.SetIsNumberEditor( false );
        voltageScale6Label.SetNumberEditorRange( 0, 100 );
        voltageScale6Label.SetNumberEditorInterval( 1 );
        voltageScale6Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale6Label.SetHasCustomTextHoverColor( false );
        voltageScale6Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale6Label.SetFont( "Arial Black", 10, false, false );

        voltageScale4Label = new VoltageLabel( "voltageScale4Label", "Voltage Scale4 Label", this, "4" );
        AddComponent( voltageScale4Label );
        voltageScale4Label.SetWantsMouseNotifications( false );
        voltageScale4Label.SetPosition( 11, 39 );
        voltageScale4Label.SetSize( 10, 10 );
        voltageScale4Label.SetEditable( false, false );
        voltageScale4Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale4Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale4Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale4Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale4Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale4Label.SetBorderSize( 1 );
        voltageScale4Label.SetMultiLineEdit( false );
        voltageScale4Label.SetIsNumberEditor( false );
        voltageScale4Label.SetNumberEditorRange( 0, 100 );
        voltageScale4Label.SetNumberEditorInterval( 1 );
        voltageScale4Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale4Label.SetHasCustomTextHoverColor( false );
        voltageScale4Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale4Label.SetFont( "Arial Black", 10, false, false );

        voltageScale3Label = new VoltageLabel( "voltageScale3Label", "Voltage Scale3 Label", this, "3" );
        AddComponent( voltageScale3Label );
        voltageScale3Label.SetWantsMouseNotifications( false );
        voltageScale3Label.SetPosition( 6, 51 );
        voltageScale3Label.SetSize( 10, 10 );
        voltageScale3Label.SetEditable( false, false );
        voltageScale3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale3Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale3Label.SetBorderSize( 1 );
        voltageScale3Label.SetMultiLineEdit( false );
        voltageScale3Label.SetIsNumberEditor( false );
        voltageScale3Label.SetNumberEditorRange( 0, 100 );
        voltageScale3Label.SetNumberEditorInterval( 1 );
        voltageScale3Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale3Label.SetHasCustomTextHoverColor( false );
        voltageScale3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale3Label.SetFont( "Arial Black", 10, false, false );

        voltageScale2Label = new VoltageLabel( "voltageScale2Label", "Voltage Scale2 Label", this, "2" );
        AddComponent( voltageScale2Label );
        voltageScale2Label.SetWantsMouseNotifications( false );
        voltageScale2Label.SetPosition( 5, 64 );
        voltageScale2Label.SetSize( 10, 10 );
        voltageScale2Label.SetEditable( false, false );
        voltageScale2Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale2Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale2Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale2Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale2Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale2Label.SetBorderSize( 1 );
        voltageScale2Label.SetMultiLineEdit( false );
        voltageScale2Label.SetIsNumberEditor( false );
        voltageScale2Label.SetNumberEditorRange( 0, 100 );
        voltageScale2Label.SetNumberEditorInterval( 1 );
        voltageScale2Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale2Label.SetHasCustomTextHoverColor( false );
        voltageScale2Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale2Label.SetFont( "Arial Black", 10, false, false );

        voltageScale1Label = new VoltageLabel( "voltageScale1Label", "Voltage Scale1 Label", this, "1" );
        AddComponent( voltageScale1Label );
        voltageScale1Label.SetWantsMouseNotifications( false );
        voltageScale1Label.SetPosition( 9, 76 );
        voltageScale1Label.SetSize( 10, 10 );
        voltageScale1Label.SetEditable( false, false );
        voltageScale1Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale1Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale1Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale1Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale1Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale1Label.SetBorderSize( 1 );
        voltageScale1Label.SetMultiLineEdit( false );
        voltageScale1Label.SetIsNumberEditor( false );
        voltageScale1Label.SetNumberEditorRange( 0, 100 );
        voltageScale1Label.SetNumberEditorInterval( 1 );
        voltageScale1Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale1Label.SetHasCustomTextHoverColor( false );
        voltageScale1Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale1Label.SetFont( "Arial Black", 10, false, false );

        voltageScale0Label = new VoltageLabel( "voltageScale0Label", "Voltage Scale0 Label", this, "0" );
        AddComponent( voltageScale0Label );
        voltageScale0Label.SetWantsMouseNotifications( false );
        voltageScale0Label.SetPosition( 17, 86 );
        voltageScale0Label.SetSize( 10, 10 );
        voltageScale0Label.SetEditable( false, false );
        voltageScale0Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale0Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale0Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale0Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale0Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale0Label.SetBorderSize( 1 );
        voltageScale0Label.SetMultiLineEdit( false );
        voltageScale0Label.SetIsNumberEditor( false );
        voltageScale0Label.SetNumberEditorRange( 0, 100 );
        voltageScale0Label.SetNumberEditorInterval( 1 );
        voltageScale0Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale0Label.SetHasCustomTextHoverColor( false );
        voltageScale0Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale0Label.SetFont( "Arial Black", 10, false, false );

        voltageScale5Label = new VoltageLabel( "voltageScale5Label", "Voltage Scale5 Label", this, "5" );
        AddComponent( voltageScale5Label );
        voltageScale5Label.SetWantsMouseNotifications( false );
        voltageScale5Label.SetPosition( 23, 31 );
        voltageScale5Label.SetSize( 10, 10 );
        voltageScale5Label.SetEditable( false, false );
        voltageScale5Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageScale5Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageScale5Label.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageScale5Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageScale5Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageScale5Label.SetBorderSize( 1 );
        voltageScale5Label.SetMultiLineEdit( false );
        voltageScale5Label.SetIsNumberEditor( false );
        voltageScale5Label.SetNumberEditorRange( 0, 100 );
        voltageScale5Label.SetNumberEditorInterval( 1 );
        voltageScale5Label.SetNumberEditorUsesMouseWheel( false );
        voltageScale5Label.SetHasCustomTextHoverColor( false );
        voltageScale5Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageScale5Label.SetFont( "Arial Black", 10, false, false );

        voltageUnitsLabel = new VoltageLabel( "voltageUnitsLabel", "Voltage Units Label", this, "V" );
        AddComponent( voltageUnitsLabel );
        voltageUnitsLabel.SetWantsMouseNotifications( false );
        voltageUnitsLabel.SetPosition( 27, 84 );
        voltageUnitsLabel.SetSize( 30, 30 );
        voltageUnitsLabel.SetEditable( false, false );
        voltageUnitsLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        voltageUnitsLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        voltageUnitsLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        voltageUnitsLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        voltageUnitsLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        voltageUnitsLabel.SetBorderSize( 1 );
        voltageUnitsLabel.SetMultiLineEdit( false );
        voltageUnitsLabel.SetIsNumberEditor( false );
        voltageUnitsLabel.SetNumberEditorRange( 0, 100 );
        voltageUnitsLabel.SetNumberEditorInterval( 1 );
        voltageUnitsLabel.SetNumberEditorUsesMouseWheel( false );
        voltageUnitsLabel.SetHasCustomTextHoverColor( false );
        voltageUnitsLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        voltageUnitsLabel.SetFont( "Arial Black", 18, false, false );

        specimenImage = new VoltageImage( "specimenImage", "Specimen Mark", this, false );
        AddComponent( specimenImage );
        specimenImage.SetWantsMouseNotifications( false );
        specimenImage.SetPosition( 20, 101 );
        specimenImage.SetSize( 76, 76 );
        specimenImage.SetCurrentImage( "image image.svg" );

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Description Label", this, "standards" );
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

        modelLabel = new VoltageLabel( "modelLabel", "Model Label", this, "sn46" );
        AddComponent( modelLabel );
        modelLabel.SetWantsMouseNotifications( false );
        modelLabel.SetPosition( 33, 335 );
        modelLabel.SetSize( 79, 13 );
        modelLabel.SetEditable( false, false );
        modelLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        modelLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modelLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modelLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        modelLabel.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        modelLabel.SetBorderSize( 0 );
        modelLabel.SetMultiLineEdit( false );
        modelLabel.SetIsNumberEditor( false );
        modelLabel.SetNumberEditorRange( 0, 100 );
        modelLabel.SetNumberEditorInterval( 1 );
        modelLabel.SetNumberEditorUsesMouseWheel( false );
        modelLabel.SetHasCustomTextHoverColor( false );
        modelLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modelLabel.SetFont( "Courier New", 13, true, false );

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Label", this, "iL" );
        AddComponent( manufacturerLabel );
        manufacturerLabel.SetWantsMouseNotifications( false );
        manufacturerLabel.SetPosition( 3, 337 );
        manufacturerLabel.SetSize( 20, 20 );
        manufacturerLabel.SetEditable( false, false );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        manufacturerLabel.SetBkColor( new Color( 0, 0, 0, 255 ) );
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

        studioColophonLabel = new VoltageLabel( "studioColophonLabel", "Studio Colophon", this, "insect laboratories pittsburgh, PA              it's already too late" );
        AddComponent( studioColophonLabel );
        studioColophonLabel.SetWantsMouseNotifications( false );
        studioColophonLabel.SetPosition( 26, 336 );
        studioColophonLabel.SetSize( 61, 22 );
        studioColophonLabel.SetEditable( false, false );
        studioColophonLabel.SetJustificationFlags( VoltageLabel.Justification.Left );
        studioColophonLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        studioColophonLabel.SetColor( new Color( 147, 147, 147, 147 ) );
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

        outputNegative = new VoltageAudioJack( "outputNegative", "Negative Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputNegative );
        outputNegative.SetWantsMouseNotifications( false );
        outputNegative.SetPosition( 5, 166 );
        outputNegative.SetSize( 25, 25 );
        outputNegative.SetSkin( "Mini Jack 25px" );

        outputPositive = new VoltageAudioJack( "outputPositive", "Positive Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputPositive );
        outputPositive.SetWantsMouseNotifications( false );
        outputPositive.SetPosition( 45, 165 );
        outputPositive.SetSize( 25, 25 );
        outputPositive.SetSkin( "Mini Jack 25px" );

        fineVoltageKnob = new VoltageKnob( "fineVoltageKnob", "Fine Voltage", this, 0.0, 1.0, 0.0 );
        AddComponent( fineVoltageKnob );
        fineVoltageKnob.SetWantsMouseNotifications( false );
        fineVoltageKnob.SetPosition( 78, 84 );
        fineVoltageKnob.SetSize( 25, 25 );
        fineVoltageKnob.SetSkin( "Cosmo v2 Med" );
        fineVoltageKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        fineVoltageKnob.SetKnobParams( 215, 145 );
        fineVoltageKnob.DisplayValueInPercent( true );
        fineVoltageKnob.SetKnobAdjustsRing( true );

        outputA = new VoltageAudioJack( "outputA", "A Reference Tone", this, JackType.JackType_AudioOutput );
        AddComponent( outputA );
        outputA.SetWantsMouseNotifications( false );
        outputA.SetPosition( 5, 294 );
        outputA.SetSize( 25, 25 );
        outputA.SetSkin( "Mini Jack 25px" );

        fineVoltageUnitsLabel = new VoltageLabel( "fineVoltageUnitsLabel", "Fine Voltage Units Label", this, "cV" );
        AddComponent( fineVoltageUnitsLabel );
        fineVoltageUnitsLabel.SetWantsMouseNotifications( false );
        fineVoltageUnitsLabel.SetPosition( 76, 102 );
        fineVoltageUnitsLabel.SetSize( 30, 30 );
        fineVoltageUnitsLabel.SetEditable( false, false );
        fineVoltageUnitsLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineVoltageUnitsLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineVoltageUnitsLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fineVoltageUnitsLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fineVoltageUnitsLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineVoltageUnitsLabel.SetBorderSize( 1 );
        fineVoltageUnitsLabel.SetMultiLineEdit( false );
        fineVoltageUnitsLabel.SetIsNumberEditor( false );
        fineVoltageUnitsLabel.SetNumberEditorRange( 0, 100 );
        fineVoltageUnitsLabel.SetNumberEditorInterval( 1 );
        fineVoltageUnitsLabel.SetNumberEditorUsesMouseWheel( false );
        fineVoltageUnitsLabel.SetHasCustomTextHoverColor( false );
        fineVoltageUnitsLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineVoltageUnitsLabel.SetFont( "Arial Black", 13, false, false );

        positiveOutputLabel = new VoltageLabel( "positiveOutputLabel", "Positive Output Label", this, "+" );
        AddComponent( positiveOutputLabel );
        positiveOutputLabel.SetWantsMouseNotifications( false );
        positiveOutputLabel.SetPosition( 47, 188 );
        positiveOutputLabel.SetSize( 20, 20 );
        positiveOutputLabel.SetEditable( false, false );
        positiveOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        positiveOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        positiveOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        positiveOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        positiveOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        positiveOutputLabel.SetBorderSize( 1 );
        positiveOutputLabel.SetMultiLineEdit( false );
        positiveOutputLabel.SetIsNumberEditor( false );
        positiveOutputLabel.SetNumberEditorRange( 0, 100 );
        positiveOutputLabel.SetNumberEditorInterval( 1 );
        positiveOutputLabel.SetNumberEditorUsesMouseWheel( false );
        positiveOutputLabel.SetHasCustomTextHoverColor( false );
        positiveOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        positiveOutputLabel.SetFont( "Arial Black", 18, false, false );

        negativeOutputLabel = new VoltageLabel( "negativeOutputLabel", "Negative Output Label", this, "-" );
        AddComponent( negativeOutputLabel );
        negativeOutputLabel.SetWantsMouseNotifications( false );
        negativeOutputLabel.SetPosition( 7, 186 );
        negativeOutputLabel.SetSize( 20, 20 );
        negativeOutputLabel.SetEditable( false, false );
        negativeOutputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        negativeOutputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        negativeOutputLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        negativeOutputLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        negativeOutputLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        negativeOutputLabel.SetBorderSize( 1 );
        negativeOutputLabel.SetMultiLineEdit( false );
        negativeOutputLabel.SetIsNumberEditor( false );
        negativeOutputLabel.SetNumberEditorRange( 0, 100 );
        negativeOutputLabel.SetNumberEditorInterval( 1 );
        negativeOutputLabel.SetNumberEditorUsesMouseWheel( false );
        negativeOutputLabel.SetHasCustomTextHoverColor( false );
        negativeOutputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        negativeOutputLabel.SetFont( "Arial Black", 23, false, false );

        outputNoise = new VoltageAudioJack( "outputNoise", "Pink Noise", this, JackType.JackType_AudioOutput );
        AddComponent( outputNoise );
        outputNoise.SetWantsMouseNotifications( false );
        outputNoise.SetPosition( 85, 294 );
        outputNoise.SetSize( 25, 25 );
        outputNoise.SetSkin( "Mini Jack 25px" );

        outputPulses = new VoltageAudioJack( "outputPulses", "Slow Pulses", this, JackType.JackType_AudioOutput );
        AddComponent( outputPulses );
        outputPulses.SetWantsMouseNotifications( false );
        outputPulses.SetPosition( 5, 254 );
        outputPulses.SetSize( 25, 25 );
        outputPulses.SetSkin( "Mini Jack 25px" );

        pinkNoiseLabel = new VoltageLabel( "pinkNoiseLabel", "Pink Noise Label", this, "PINK" );
        AddComponent( pinkNoiseLabel );
        pinkNoiseLabel.SetWantsMouseNotifications( false );
        pinkNoiseLabel.SetPosition( 87, 315 );
        pinkNoiseLabel.SetSize( 20, 20 );
        pinkNoiseLabel.SetEditable( false, false );
        pinkNoiseLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        pinkNoiseLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        pinkNoiseLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        pinkNoiseLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        pinkNoiseLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        pinkNoiseLabel.SetBorderSize( 1 );
        pinkNoiseLabel.SetMultiLineEdit( false );
        pinkNoiseLabel.SetIsNumberEditor( false );
        pinkNoiseLabel.SetNumberEditorRange( 0, 100 );
        pinkNoiseLabel.SetNumberEditorInterval( 1 );
        pinkNoiseLabel.SetNumberEditorUsesMouseWheel( false );
        pinkNoiseLabel.SetHasCustomTextHoverColor( false );
        pinkNoiseLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        pinkNoiseLabel.SetFont( "Arial Black", 8, false, false );

        pulsesLabel = new VoltageLabel( "pulsesLabel", "Pulses Label", this, "Pulses" );
        AddComponent( pulsesLabel );
        pulsesLabel.SetWantsMouseNotifications( false );
        pulsesLabel.SetPosition( 8, 275 );
        pulsesLabel.SetSize( 20, 20 );
        pulsesLabel.SetEditable( false, false );
        pulsesLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        pulsesLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        pulsesLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        pulsesLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        pulsesLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        pulsesLabel.SetBorderSize( 1 );
        pulsesLabel.SetMultiLineEdit( false );
        pulsesLabel.SetIsNumberEditor( false );
        pulsesLabel.SetNumberEditorRange( 0, 100 );
        pulsesLabel.SetNumberEditorInterval( 1 );
        pulsesLabel.SetNumberEditorUsesMouseWheel( false );
        pulsesLabel.SetHasCustomTextHoverColor( false );
        pulsesLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        pulsesLabel.SetFont( "Arial Black", 8, false, false );

        output1Hz = new VoltageAudioJack( "output1Hz", "1 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output1Hz );
        output1Hz.SetWantsMouseNotifications( false );
        output1Hz.SetPosition( 45, 254 );
        output1Hz.SetSize( 25, 25 );
        output1Hz.SetSkin( "Mini Jack 25px" );

        output100Hz = new VoltageAudioJack( "output100Hz", "100 Hz Output", this, JackType.JackType_AudioOutput );
        AddComponent( output100Hz );
        output100Hz.SetWantsMouseNotifications( false );
        output100Hz.SetPosition( 85, 254 );
        output100Hz.SetSize( 25, 25 );
        output100Hz.SetSkin( "Mini Jack 25px" );

        oneHzLabel = new VoltageLabel( "oneHzLabel", "One Hz Label", this, "1 Hz" );
        AddComponent( oneHzLabel );
        oneHzLabel.SetWantsMouseNotifications( false );
        oneHzLabel.SetPosition( 46, 275 );
        oneHzLabel.SetSize( 20, 20 );
        oneHzLabel.SetEditable( false, false );
        oneHzLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        oneHzLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        oneHzLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        oneHzLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        oneHzLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        oneHzLabel.SetBorderSize( 1 );
        oneHzLabel.SetMultiLineEdit( false );
        oneHzLabel.SetIsNumberEditor( false );
        oneHzLabel.SetNumberEditorRange( 0, 100 );
        oneHzLabel.SetNumberEditorInterval( 1 );
        oneHzLabel.SetNumberEditorUsesMouseWheel( false );
        oneHzLabel.SetHasCustomTextHoverColor( false );
        oneHzLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        oneHzLabel.SetFont( "Arial Black", 10, false, false );

        hundredHzLabel = new VoltageLabel( "hundredHzLabel", "Hundred Hz Label", this, "100 Hz" );
        AddComponent( hundredHzLabel );
        hundredHzLabel.SetWantsMouseNotifications( false );
        hundredHzLabel.SetPosition( 88, 275 );
        hundredHzLabel.SetSize( 20, 20 );
        hundredHzLabel.SetEditable( false, false );
        hundredHzLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        hundredHzLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        hundredHzLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        hundredHzLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        hundredHzLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        hundredHzLabel.SetBorderSize( 1 );
        hundredHzLabel.SetMultiLineEdit( false );
        hundredHzLabel.SetIsNumberEditor( false );
        hundredHzLabel.SetNumberEditorRange( 0, 100 );
        hundredHzLabel.SetNumberEditorInterval( 1 );
        hundredHzLabel.SetNumberEditorUsesMouseWheel( false );
        hundredHzLabel.SetHasCustomTextHoverColor( false );
        hundredHzLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        hundredHzLabel.SetFont( "Arial Black", 10, false, false );

        outputFsharp = new VoltageAudioJack( "outputFsharp", "F Sharp Reference Tone", this, JackType.JackType_AudioOutput );
        AddComponent( outputFsharp );
        outputFsharp.SetWantsMouseNotifications( false );
        outputFsharp.SetPosition( 45, 294 );
        outputFsharp.SetSize( 25, 25 );
        outputFsharp.SetSkin( "Mini Jack 25px" );

        aReferenceLabel = new VoltageLabel( "aReferenceLabel", "A Reference Label", this, "A" );
        AddComponent( aReferenceLabel );
        aReferenceLabel.SetWantsMouseNotifications( false );
        aReferenceLabel.SetPosition( 8, 315 );
        aReferenceLabel.SetSize( 20, 20 );
        aReferenceLabel.SetEditable( false, false );
        aReferenceLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        aReferenceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        aReferenceLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        aReferenceLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        aReferenceLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        aReferenceLabel.SetBorderSize( 1 );
        aReferenceLabel.SetMultiLineEdit( false );
        aReferenceLabel.SetIsNumberEditor( false );
        aReferenceLabel.SetNumberEditorRange( 0, 100 );
        aReferenceLabel.SetNumberEditorInterval( 1 );
        aReferenceLabel.SetNumberEditorUsesMouseWheel( false );
        aReferenceLabel.SetHasCustomTextHoverColor( false );
        aReferenceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        aReferenceLabel.SetFont( "Arial Black", 13, false, false );

        fSharpReferenceLabel = new VoltageLabel( "fSharpReferenceLabel", "F Sharp Reference Label", this, "F#" );
        AddComponent( fSharpReferenceLabel );
        fSharpReferenceLabel.SetWantsMouseNotifications( false );
        fSharpReferenceLabel.SetPosition( 47, 315 );
        fSharpReferenceLabel.SetSize( 20, 20 );
        fSharpReferenceLabel.SetEditable( false, false );
        fSharpReferenceLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fSharpReferenceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fSharpReferenceLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fSharpReferenceLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fSharpReferenceLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fSharpReferenceLabel.SetBorderSize( 1 );
        fSharpReferenceLabel.SetMultiLineEdit( false );
        fSharpReferenceLabel.SetIsNumberEditor( false );
        fSharpReferenceLabel.SetNumberEditorRange( 0, 100 );
        fSharpReferenceLabel.SetNumberEditorInterval( 1 );
        fSharpReferenceLabel.SetNumberEditorUsesMouseWheel( false );
        fSharpReferenceLabel.SetHasCustomTextHoverColor( false );
        fSharpReferenceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fSharpReferenceLabel.SetFont( "Arial Black", 13, false, false );

        output33V = new VoltageAudioJack( "output33V", "3.3 V Output", this, JackType.JackType_AudioOutput );
        AddComponent( output33V );
        output33V.SetWantsMouseNotifications( false );
        output33V.SetPosition( 5, 205 );
        output33V.SetSize( 25, 25 );
        output33V.SetSkin( "Mini Jack 25px" );

        threePointThreeVoltLabel = new VoltageLabel( "threePointThreeVoltLabel", "Three Point Three Volt Label", this, "3.3 V" );
        AddComponent( threePointThreeVoltLabel );
        threePointThreeVoltLabel.SetWantsMouseNotifications( false );
        threePointThreeVoltLabel.SetPosition( 8, 227 );
        threePointThreeVoltLabel.SetSize( 20, 20 );
        threePointThreeVoltLabel.SetEditable( false, false );
        threePointThreeVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        threePointThreeVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        threePointThreeVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        threePointThreeVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        threePointThreeVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        threePointThreeVoltLabel.SetBorderSize( 1 );
        threePointThreeVoltLabel.SetMultiLineEdit( false );
        threePointThreeVoltLabel.SetIsNumberEditor( false );
        threePointThreeVoltLabel.SetNumberEditorRange( 0, 100 );
        threePointThreeVoltLabel.SetNumberEditorInterval( 1 );
        threePointThreeVoltLabel.SetNumberEditorUsesMouseWheel( false );
        threePointThreeVoltLabel.SetHasCustomTextHoverColor( false );
        threePointThreeVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        threePointThreeVoltLabel.SetFont( "Arial Black", 10, false, false );

        output5V = new VoltageAudioJack( "output5V", "5 V Output", this, JackType.JackType_AudioOutput );
        AddComponent( output5V );
        output5V.SetWantsMouseNotifications( false );
        output5V.SetPosition( 45, 205 );
        output5V.SetSize( 25, 25 );
        output5V.SetSkin( "Mini Jack 25px" );

        fiveVoltLabel = new VoltageLabel( "fiveVoltLabel", "Five Volt Label", this, "5 V" );
        AddComponent( fiveVoltLabel );
        fiveVoltLabel.SetWantsMouseNotifications( false );
        fiveVoltLabel.SetPosition( 48, 227 );
        fiveVoltLabel.SetSize( 20, 20 );
        fiveVoltLabel.SetEditable( false, false );
        fiveVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fiveVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fiveVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fiveVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fiveVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fiveVoltLabel.SetBorderSize( 1 );
        fiveVoltLabel.SetMultiLineEdit( false );
        fiveVoltLabel.SetIsNumberEditor( false );
        fiveVoltLabel.SetNumberEditorRange( 0, 100 );
        fiveVoltLabel.SetNumberEditorInterval( 1 );
        fiveVoltLabel.SetNumberEditorUsesMouseWheel( false );
        fiveVoltLabel.SetHasCustomTextHoverColor( false );
        fiveVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fiveVoltLabel.SetFont( "Arial Black", 11, false, false );

        nineVoltLabel = new VoltageLabel( "nineVoltLabel", "Nine Volt Label", this, "9 V" );
        AddComponent( nineVoltLabel );
        nineVoltLabel.SetWantsMouseNotifications( false );
        nineVoltLabel.SetPosition( 88, 227 );
        nineVoltLabel.SetSize( 20, 20 );
        nineVoltLabel.SetEditable( false, false );
        nineVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        nineVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        nineVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        nineVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        nineVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        nineVoltLabel.SetBorderSize( 1 );
        nineVoltLabel.SetMultiLineEdit( false );
        nineVoltLabel.SetIsNumberEditor( false );
        nineVoltLabel.SetNumberEditorRange( 0, 100 );
        nineVoltLabel.SetNumberEditorInterval( 1 );
        nineVoltLabel.SetNumberEditorUsesMouseWheel( false );
        nineVoltLabel.SetHasCustomTextHoverColor( false );
        nineVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        nineVoltLabel.SetFont( "Arial Black", 11, false, false );

        output0V = new VoltageAudioJack( "output0V", "0 V Output", this, JackType.JackType_AudioOutput );
        AddComponent( output0V );
        output0V.SetWantsMouseNotifications( false );
        output0V.SetPosition( 85, 165 );
        output0V.SetSize( 25, 25 );
        output0V.SetSkin( "Mini Jack 25px" );

        zeroVoltLabel = new VoltageLabel( "zeroVoltLabel", "Zero Volt Label", this, "0 V" );
        AddComponent( zeroVoltLabel );
        zeroVoltLabel.SetWantsMouseNotifications( false );
        zeroVoltLabel.SetPosition( 87, 188 );
        zeroVoltLabel.SetSize( 20, 20 );
        zeroVoltLabel.SetEditable( false, false );
        zeroVoltLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        zeroVoltLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        zeroVoltLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        zeroVoltLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        zeroVoltLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        zeroVoltLabel.SetBorderSize( 1 );
        zeroVoltLabel.SetMultiLineEdit( false );
        zeroVoltLabel.SetIsNumberEditor( false );
        zeroVoltLabel.SetNumberEditorRange( 0, 100 );
        zeroVoltLabel.SetNumberEditorInterval( 1 );
        zeroVoltLabel.SetNumberEditorUsesMouseWheel( false );
        zeroVoltLabel.SetHasCustomTextHoverColor( false );
        zeroVoltLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        zeroVoltLabel.SetFont( "Arial Black", 13, false, false );

        output9V = new VoltageAudioJack( "output9V", "9 V Output", this, JackType.JackType_AudioOutput );
        AddComponent( output9V );
        output9V.SetWantsMouseNotifications( false );
        output9V.SetPosition( 85, 205 );
        output9V.SetSize( 25, 25 );
        output9V.SetSkin( "Mini Jack 25px" );

        fineZeroLabel = new VoltageLabel( "fineZeroLabel", "Fine Zero Label", this, "0" );
        AddComponent( fineZeroLabel );
        fineZeroLabel.SetWantsMouseNotifications( false );
        fineZeroLabel.SetPosition( 69, 101 );
        fineZeroLabel.SetSize( 10, 10 );
        fineZeroLabel.SetEditable( false, false );
        fineZeroLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineZeroLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineZeroLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fineZeroLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fineZeroLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineZeroLabel.SetBorderSize( 1 );
        fineZeroLabel.SetMultiLineEdit( false );
        fineZeroLabel.SetIsNumberEditor( false );
        fineZeroLabel.SetNumberEditorRange( 0, 100 );
        fineZeroLabel.SetNumberEditorInterval( 1 );
        fineZeroLabel.SetNumberEditorUsesMouseWheel( false );
        fineZeroLabel.SetHasCustomTextHoverColor( false );
        fineZeroLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineZeroLabel.SetFont( "Arial Black", 10, false, false );

        voltageCounter = new VoltageDigitalCounter( "voltageCounter", "Voltage Display", this, 4 );
        AddComponent( voltageCounter );
        voltageCounter.SetWantsMouseNotifications( false );
        voltageCounter.SetPosition( 31, 127 );
        voltageCounter.SetSize( 54, 25 );
        voltageCounter.SetSkin( "Red" );
        voltageCounter.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        fineOneLabel = new VoltageLabel( "fineOneLabel", "Fine One Label", this, "1" );
        AddComponent( fineOneLabel );
        fineOneLabel.SetWantsMouseNotifications( false );
        fineOneLabel.SetPosition( 101, 101 );
        fineOneLabel.SetSize( 10, 10 );
        fineOneLabel.SetEditable( false, false );
        fineOneLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineOneLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineOneLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fineOneLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fineOneLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineOneLabel.SetBorderSize( 1 );
        fineOneLabel.SetMultiLineEdit( false );
        fineOneLabel.SetIsNumberEditor( false );
        fineOneLabel.SetNumberEditorRange( 0, 100 );
        fineOneLabel.SetNumberEditorInterval( 1 );
        fineOneLabel.SetNumberEditorUsesMouseWheel( false );
        fineOneLabel.SetHasCustomTextHoverColor( false );
        fineOneLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineOneLabel.SetFont( "Arial Black", 10, false, false );

        fineMidpointLabel = new VoltageLabel( "fineMidpointLabel", "Fine Midpoint Label", this, "0.5" );
        AddComponent( fineMidpointLabel );
        fineMidpointLabel.SetWantsMouseNotifications( false );
        fineMidpointLabel.SetPosition( 85, 71 );
        fineMidpointLabel.SetSize( 10, 10 );
        fineMidpointLabel.SetEditable( false, false );
        fineMidpointLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineMidpointLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineMidpointLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fineMidpointLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fineMidpointLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineMidpointLabel.SetBorderSize( 1 );
        fineMidpointLabel.SetMultiLineEdit( false );
        fineMidpointLabel.SetIsNumberEditor( false );
        fineMidpointLabel.SetNumberEditorRange( 0, 100 );
        fineMidpointLabel.SetNumberEditorInterval( 1 );
        fineMidpointLabel.SetNumberEditorUsesMouseWheel( false );
        fineMidpointLabel.SetHasCustomTextHoverColor( false );
        fineMidpointLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineMidpointLabel.SetFont( "Arial Black", 10, false, false );

        fineUpperMarkLabel = new VoltageLabel( "fineUpperMarkLabel", "Fine Upper Mark Label", this, "•" );
        AddComponent( fineUpperMarkLabel );
        fineUpperMarkLabel.SetWantsMouseNotifications( false );
        fineUpperMarkLabel.SetPosition( 104, 86 );
        fineUpperMarkLabel.SetSize( 10, 10 );
        fineUpperMarkLabel.SetEditable( false, false );
        fineUpperMarkLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineUpperMarkLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineUpperMarkLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fineUpperMarkLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fineUpperMarkLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineUpperMarkLabel.SetBorderSize( 1 );
        fineUpperMarkLabel.SetMultiLineEdit( false );
        fineUpperMarkLabel.SetIsNumberEditor( false );
        fineUpperMarkLabel.SetNumberEditorRange( 0, 100 );
        fineUpperMarkLabel.SetNumberEditorInterval( 1 );
        fineUpperMarkLabel.SetNumberEditorUsesMouseWheel( false );
        fineUpperMarkLabel.SetHasCustomTextHoverColor( false );
        fineUpperMarkLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineUpperMarkLabel.SetFont( "Arial Black", 10, false, false );

        fineLowerMarkLabel = new VoltageLabel( "fineLowerMarkLabel", "Fine Lower Mark Label", this, "•" );
        AddComponent( fineLowerMarkLabel );
        fineLowerMarkLabel.SetWantsMouseNotifications( false );
        fineLowerMarkLabel.SetPosition( 67, 86 );
        fineLowerMarkLabel.SetSize( 10, 10 );
        fineLowerMarkLabel.SetEditable( false, false );
        fineLowerMarkLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        fineLowerMarkLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        fineLowerMarkLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        fineLowerMarkLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        fineLowerMarkLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        fineLowerMarkLabel.SetBorderSize( 1 );
        fineLowerMarkLabel.SetMultiLineEdit( false );
        fineLowerMarkLabel.SetIsNumberEditor( false );
        fineLowerMarkLabel.SetNumberEditorRange( 0, 100 );
        fineLowerMarkLabel.SetNumberEditorInterval( 1 );
        fineLowerMarkLabel.SetNumberEditorUsesMouseWheel( false );
        fineLowerMarkLabel.SetHasCustomTextHoverColor( false );
        fineLowerMarkLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        fineLowerMarkLabel.SetFont( "Arial Black", 10, false, false );

        reference432Button = new VoltageButton( "reference432Button", "432 Hz Reference", this );
        AddComponent( reference432Button );
        reference432Button.SetWantsMouseNotifications( false );
        reference432Button.SetPosition( 32, 311 );
        reference432Button.SetSize( 11, 11 );
        reference432Button.SetSkin( "2500 Square Big" );
        reference432Button.ShowOverlay( true );
        reference432Button.SetOverlayText( "432" );
        reference432Button.SetOverlayTextFont( "Arial Black", 5, false, false );
        reference432Button.SetOverlayTextColor( new Color( 0, 0, 0 ) );
        reference432Button.SetOverlayArea( 0, 0, 0, 0 );
        reference432Button.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        reference432Button.SetAutoRepeat( false );

        decimalPointLed = new VoltageLED( "decimalPointLed", "Voltage Decimal Point", this );
        AddComponent( decimalPointLed );
        decimalPointLed.SetWantsMouseNotifications( false );
        decimalPointLed.SetPosition( 55, 146 );
        decimalPointLed.SetSize( 4, 4 );
        decimalPointLed.SetSkin( "Red" );
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
        decimalPointLed.SetValue(1.0); // Fixed decimal point is always lit.
        StartGuiUpdateTimer(25);
        refreshDisplay();
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
                // AutoRepeat is disabled. Do not depend on a release notification.
                if (component == reference432Button && doubleValue >= 0.5) {
                    boolean previous = reference432;
                    reference432 = !reference432;
                    CreateUndoNode("A Reference", "reference432", previous ? 1.0 : 0.0,
                            reference432 ? 1.0 : 0.0);
                }
                refreshDisplay();
                break;
            case GUI_Update_Timer:
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                refreshDisplay();
                break;
            case Reset:
                reference432 = false;
                refreshDisplay();
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
        bypassed = false;
        double volts = clamp(voltageKnob.GetValue(), 0.0, 12.0)
                + clamp(fineVoltageKnob.GetValue(), 0.0, 1.0);
        shownVoltage = volts;
        outputPositive.SetValue(volts);
        outputNegative.SetValue(-volts);

        output0V.SetValue(0.0);
        output33V.SetValue(3.3);
        output5V.SetValue(5.0);
        output9V.SetValue(9.0);

        double targetTuning = reference432 ? 432.0 : 440.0;
        tuningHz += CONTROL_SMOOTH * (targetTuning - tuningHz);
        if (Math.abs(tuningHz - targetTuning) < 1.0e-9) tuningHz = targetTuning;
        outputA.SetValue(5.0 * Math.sin(TWO_PI * phaseA));
        outputFsharp.SetValue(5.0 * Math.sin(TWO_PI * phaseFsharp));
        output1Hz.SetValue(5.0 * Math.sin(TWO_PI * phase1Hz));
        output100Hz.SetValue(5.0 * Math.sin(TWO_PI * phase100Hz));

        phaseA = nextPhase(phaseA, tuningHz);
        phaseFsharp = nextPhase(phaseFsharp, tuningHz * F_SHARP_RATIO);
        phase1Hz = nextPhase(phase1Hz, 1.0);
        phase100Hz = nextPhase(phase100Hz, 100.0);

        outputPulses.SetValue(pulseSample == 0 ? 5.0 : 0.0);
        pulseSample = (pulseSample + 1) & 4095;

        noise.process();
        outputNoise.SetValue(noise.pink);
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
        bypassed = true;
        outputPositive.SetValue(0.0);
        outputNegative.SetValue(0.0);
        output0V.SetValue(0.0);
        output33V.SetValue(0.0);
        output5V.SetValue(0.0);
        output9V.SetValue(0.0);
        outputA.SetValue(0.0);
        outputFsharp.SetValue(0.0);
        outputPulses.SetValue(0.0);
        output1Hz.SetValue(0.0);
        output100Hz.SetValue(0.0);
        outputNoise.SetValue(0.0);
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
        if (component == voltageKnob) return String.format(java.util.Locale.ROOT,
                "Voltage: %.3f V (0 to 12 V, plus Fine Voltage)", clamp(voltageKnob.GetValue(), 0.0, 12.0));
        if (component == fineVoltageKnob) return String.format(java.util.Locale.ROOT,
                "Fine Voltage: %.3f V (0 to 1 V added)", clamp(fineVoltageKnob.GetValue(), 0.0, 1.0));
        if (component == outputPositive) return "Positive: combined Voltage + Fine Voltage, 0 to +13 V";
        if (component == outputNegative) return "Negative: inverted combined Voltage + Fine Voltage, 0 to -13 V";
        if (component == output0V) return "Zero: fixed 0 V reference";
        if (component == output33V) return "Fixed +3.3 V reference";
        if (component == output5V) return "Fixed +5 V reference";
        if (component == output9V) return "Fixed +9 V reference";
        if (component == reference432Button) return "432: lit selects A432 and matching lower F Sharp; dark selects A440";
        if (component == outputA) return "A reference: 440 Hz or 432 Hz sine, +/-5 V";
        if (component == outputFsharp) return "F Sharp below A: about 369.994 or 363.267 Hz sine, +/-5 V";
        if (component == output1Hz) return "1 Hz sine reference, +/-5 V";
        if (component == output100Hz) return "100 Hz sine reference, +/-5 V";
        if (component == outputPulses) return "Pulses: +5 V single-sample impulses, 11.71875 Hz (same as SN-16u Slow Pulses)";
        if (component == outputNoise) return "Pink noise: continuous colored noise for sound and testing; not a calibrated RMS source";
        if (component == voltageCounter) return "Voltage magnitude: combined Voltage + Fine Voltage, displayed to 0.01 V";
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
        if ("reference432".equals(undoType) && Double.isFinite(newValue)) {
            reference432 = newValue >= 0.5;
            reference432Button.UpdateGUIValue(reference432 ? 1.0 : 0.0);
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
        return new byte[] { 'S', 'N', '4', '6', (byte) (reference432 ? 1 : 0) };
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
        if (stateInfo != null && stateInfo.length == 5
                && stateInfo[0] == 'S' && stateInfo[1] == 'N'
                && stateInfo[2] == '4' && stateInfo[3] == '6'
                && (stateInfo[4] == 0 || stateInfo[4] == 1)) {
            reference432 = stateInfo[4] != 0;
            if (reference432Button != null) reference432Button.UpdateGUIValue(reference432 ? 1.0 : 0.0);
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
    private VoltageLED decimalPointLed;
    private VoltageButton reference432Button;
    private VoltageLabel fineLowerMarkLabel;
    private VoltageLabel fineUpperMarkLabel;
    private VoltageLabel fineMidpointLabel;
    private VoltageLabel fineOneLabel;
    private VoltageDigitalCounter voltageCounter;
    private VoltageLabel fineZeroLabel;
    private VoltageAudioJack output9V;
    private VoltageLabel zeroVoltLabel;
    private VoltageAudioJack output0V;
    private VoltageLabel nineVoltLabel;
    private VoltageLabel fiveVoltLabel;
    private VoltageAudioJack output5V;
    private VoltageLabel threePointThreeVoltLabel;
    private VoltageAudioJack output33V;
    private VoltageLabel fSharpReferenceLabel;
    private VoltageLabel aReferenceLabel;
    private VoltageAudioJack outputFsharp;
    private VoltageLabel hundredHzLabel;
    private VoltageLabel oneHzLabel;
    private VoltageAudioJack output100Hz;
    private VoltageAudioJack output1Hz;
    private VoltageLabel pulsesLabel;
    private VoltageLabel pinkNoiseLabel;
    private VoltageAudioJack outputPulses;
    private VoltageAudioJack outputNoise;
    private VoltageLabel negativeOutputLabel;
    private VoltageLabel positiveOutputLabel;
    private VoltageLabel fineVoltageUnitsLabel;
    private VoltageAudioJack outputA;
    private VoltageKnob fineVoltageKnob;
    private VoltageAudioJack outputPositive;
    private VoltageAudioJack outputNegative;
    private VoltageLabel studioColophonLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageLabel modelLabel;
    private VoltageLabel descriptionLabel;
    private VoltageImage specimenImage;
    private VoltageLabel voltageUnitsLabel;
    private VoltageLabel voltageScale5Label;
    private VoltageLabel voltageScale0Label;
    private VoltageLabel voltageScale1Label;
    private VoltageLabel voltageScale2Label;
    private VoltageLabel voltageScale3Label;
    private VoltageLabel voltageScale4Label;
    private VoltageLabel voltageScale6Label;
    private VoltageLabel voltageScale7Label;
    private VoltageLabel voltageScale8Label;
    private VoltageLabel voltageScale11Label;
    private VoltageLabel voltageScale12Label;
    private VoltageLabel voltageScale10Label;
    private VoltageLabel voltageScale9Label;
    private VoltageKnob voltageKnob;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double TWO_PI = 2.0 * Math.PI;
    private static final double F_SHARP_RATIO = Math.pow(2.0, -3.0 / 12.0);
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE));
    private static final EnumSet<Flags> ZERO_DIGIT_SEGMENTS = EnumSet.of(
            Flags.Top,
            Flags.TopLeft,
            Flags.TopRight,
            Flags.BottomLeft,
            Flags.BottomRight,
            Flags.Bottom);

    private final NoiseSource noise = new NoiseSource();
    private final boolean[] leadingZeroOverrides = new boolean[4];
    private volatile boolean reference432;
    private volatile boolean bypassed;
    private volatile double shownVoltage;
    private double tuningHz = 440.0;
    private double phaseA, phaseFsharp, phase1Hz, phase100Hz;
    private int pulseSample;

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, Double.isFinite(value) ? value : 0.0));
    }

    private static double nextPhase(double phase, double frequency) {
        phase += frequency / SAMPLE_RATE;
        return phase - Math.floor(phase);
    }

    private void refreshDisplay() {
        int hundredths = (int) Math.round(clamp(bypassed ? 0.0 : shownVoltage, 0.0, 13.0) * 100.0);
        voltageCounter.SetValue(hundredths);

        // The panel artwork supplies a fixed decimal point between digits 2 and 3.
        // VoltageDigitalCounter displays integers, so keep the four digits zero-padded.
        int mostSignificantDigit = hundredths >= 1000 ? 3
                : hundredths >= 100 ? 2
                : hundredths >= 10 ? 1 : 0;
        for (int digit = 1; digit < 4; digit++) {
            boolean needsLeadingZero = digit > mostSignificantDigit;
            if (needsLeadingZero && !leadingZeroOverrides[digit]) {
                voltageCounter.SetBarsForDigit(digit, ZERO_DIGIT_SEGMENTS);
                leadingZeroOverrides[digit] = true;
            } else if (!needsLeadingZero && leadingZeroOverrides[digit]) {
                voltageCounter.ClearBarsForDigit(digit);
                leadingZeroOverrides[digit] = false;
            }
        }
        reference432Button.UpdateGUIValue(reference432 ? 1.0 : 0.0);
    }

    private static final class NoiseSource {
        private long seed = System.nanoTime() ^ 0x39c45b7a1dL;
        private double b0, b1, b2, b3, b4, b5, b6;
        private double pink;

        private double white() {
            seed ^= seed << 13;
            seed ^= seed >>> 7;
            seed ^= seed << 17;
            return (seed >> 11) * 0x1.0p-52;
        }

        private void process() {
            double white = white();
            b0 = 0.99886 * b0 + white * 0.0555179;
            b1 = 0.99332 * b1 + white * 0.0750759;
            b2 = 0.96900 * b2 + white * 0.1538520;
            b3 = 0.86650 * b3 + white * 0.3104856;
            b4 = 0.55000 * b4 + white * 0.5329522;
            b5 = -0.7616 * b5 - white * 0.0168980;
            double raw = b0 + b1 + b2 + b3 + b4 + b5 + b6 + white * 0.5362;
            b6 = white * 0.115926;
            pink = raw * 0.5;
        }
    }
    //[/user-code-and-variables]
}
