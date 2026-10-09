package com.insectlabs.highpassfilters;


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


public class XL35h extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public XL35h( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "XL-35h - High Pass Filters", ModuleType.ModuleType_Filters, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "b3f8ed8998bb4eae801ec4aa745aa2fd" );
    }

void InitializeControls()
{

        moduleTitleLabel = new VoltageLabel( "moduleTitleLabel", "Module Title", this, "high filters" );
        AddComponent( moduleTitleLabel );
        moduleTitleLabel.SetWantsMouseNotifications( false );
        moduleTitleLabel.SetPosition( 3, 3 );
        moduleTitleLabel.SetSize( 109, 23 );
        moduleTitleLabel.SetEditable( false, false );
        moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleTitleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        moduleTitleLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        moduleTitleLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        moduleTitleLabel.SetBorderSize( 4 );
        moduleTitleLabel.SetMultiLineEdit( false );
        moduleTitleLabel.SetIsNumberEditor( false );
        moduleTitleLabel.SetNumberEditorRange( 0, 100 );
        moduleTitleLabel.SetNumberEditorInterval( 1 );
        moduleTitleLabel.SetNumberEditorUsesMouseWheel( false );
        moduleTitleLabel.SetHasCustomTextHoverColor( false );
        moduleTitleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleTitleLabel.SetFont( "Courier New", 13, true, false );

        modelIdentifierLabel = new VoltageLabel( "modelIdentifierLabel", "Model Number", this, "XL-35h" );
        AddComponent( modelIdentifierLabel );
        modelIdentifierLabel.SetWantsMouseNotifications( false );
        modelIdentifierLabel.SetPosition( 33, 335 );
        modelIdentifierLabel.SetSize( 79, 13 );
        modelIdentifierLabel.SetEditable( false, false );
        modelIdentifierLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        modelIdentifierLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modelIdentifierLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modelIdentifierLabel.SetBkColor( new Color( 85, 85, 85, 0 ) );
        modelIdentifierLabel.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        modelIdentifierLabel.SetBorderSize( 0 );
        modelIdentifierLabel.SetMultiLineEdit( false );
        modelIdentifierLabel.SetIsNumberEditor( false );
        modelIdentifierLabel.SetNumberEditorRange( 0, 100 );
        modelIdentifierLabel.SetNumberEditorInterval( 1 );
        modelIdentifierLabel.SetNumberEditorUsesMouseWheel( false );
        modelIdentifierLabel.SetHasCustomTextHoverColor( false );
        modelIdentifierLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modelIdentifierLabel.SetFont( "Courier New", 13, true, false );

        makerMarkLabel = new VoltageLabel( "makerMarkLabel", "Insect Laboratories Mark", this, "iL" );
        AddComponent( makerMarkLabel );
        makerMarkLabel.SetWantsMouseNotifications( false );
        makerMarkLabel.SetPosition( 3, 337 );
        makerMarkLabel.SetSize( 20, 20 );
        makerMarkLabel.SetEditable( false, false );
        makerMarkLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        makerMarkLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        makerMarkLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        makerMarkLabel.SetBkColor( new Color( 51, 51, 51, 255 ) );
        makerMarkLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        makerMarkLabel.SetBorderSize( 2 );
        makerMarkLabel.SetMultiLineEdit( false );
        makerMarkLabel.SetIsNumberEditor( false );
        makerMarkLabel.SetNumberEditorRange( 0, 100 );
        makerMarkLabel.SetNumberEditorInterval( 1 );
        makerMarkLabel.SetNumberEditorUsesMouseWheel( false );
        makerMarkLabel.SetHasCustomTextHoverColor( false );
        makerMarkLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        makerMarkLabel.SetFont( "Courier New", 13, true, false );

        outputJackUpper = new VoltageAudioJack( "outputJackUpper", "Upper High-Pass Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputJackUpper );
        outputJackUpper.SetWantsMouseNotifications( false );
        outputJackUpper.SetPosition( 70, 131 );
        outputJackUpper.SetSize( 37, 37 );
        outputJackUpper.SetSkin( "Rotated Half" );

        knobHighPassUpper = new VoltageKnob( "knobHighPassUpper", "Upper High-Pass Cutoff", this, 1, 8, 1 );
        AddComponent( knobHighPassUpper );
        knobHighPassUpper.SetWantsMouseNotifications( false );
        knobHighPassUpper.SetPosition( 32, 56 );
        knobHighPassUpper.SetSize( 51, 51 );
        knobHighPassUpper.SetSkin( "ARP2500 Large Black" );
        knobHighPassUpper.SetRange( 1, 8, 1, false, 0 );
        knobHighPassUpper.SetKnobParams( 280, 80 );
        knobHighPassUpper.DisplayValueInPercent( false );
        knobHighPassUpper.SetKnobAdjustsRing( true );

        knobHighPassLower = new VoltageKnob( "knobHighPassLower", "Lower High-Pass Cutoff", this, 1, 8, 1 );
        AddComponent( knobHighPassLower );
        knobHighPassLower.SetWantsMouseNotifications( false );
        knobHighPassLower.SetPosition( 32, 196 );
        knobHighPassLower.SetSize( 51, 51 );
        knobHighPassLower.SetSkin( "ARP2500 Large Black" );
        knobHighPassLower.SetRange( 1, 8, 1, false, 0 );
        knobHighPassLower.SetKnobParams( 280, 80 );
        knobHighPassLower.DisplayValueInPercent( false );
        knobHighPassLower.SetKnobAdjustsRing( true );

        inputJackLower = new VoltageAudioJack( "inputJackLower", "Lower High-Pass Input", this, JackType.JackType_AudioInput );
        AddComponent( inputJackLower );
        inputJackLower.SetWantsMouseNotifications( false );
        inputJackLower.SetPosition( 10, 271 );
        inputJackLower.SetSize( 37, 37 );
        inputJackLower.SetSkin( "Dark Jack Straight" );

        outputJackLower = new VoltageAudioJack( "outputJackLower", "Lower High-Pass Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputJackLower );
        outputJackLower.SetWantsMouseNotifications( false );
        outputJackLower.SetPosition( 70, 271 );
        outputJackLower.SetSize( 37, 37 );
        outputJackLower.SetSkin( "Rotated Half" );

        upperFrequencyUnitLabel = new VoltageLabel( "upperFrequencyUnitLabel", "Upper Frequency Unit", this, "CPS" );
        AddComponent( upperFrequencyUnitLabel );
        upperFrequencyUnitLabel.SetWantsMouseNotifications( false );
        upperFrequencyUnitLabel.SetPosition( 42, 108 );
        upperFrequencyUnitLabel.SetSize( 31, 13 );
        upperFrequencyUnitLabel.SetEditable( false, false );
        upperFrequencyUnitLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyUnitLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyUnitLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyUnitLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyUnitLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyUnitLabel.SetBorderSize( 1 );
        upperFrequencyUnitLabel.SetMultiLineEdit( false );
        upperFrequencyUnitLabel.SetIsNumberEditor( false );
        upperFrequencyUnitLabel.SetNumberEditorRange( 0, 100 );
        upperFrequencyUnitLabel.SetNumberEditorInterval( 1 );
        upperFrequencyUnitLabel.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyUnitLabel.SetHasCustomTextHoverColor( false );
        upperFrequencyUnitLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyUnitLabel.SetFont( "Arial", 7, true, false );

        lowerFrequencyUnitLabel = new VoltageLabel( "lowerFrequencyUnitLabel", "Lower Frequency Unit", this, "CPS" );
        AddComponent( lowerFrequencyUnitLabel );
        lowerFrequencyUnitLabel.SetWantsMouseNotifications( false );
        lowerFrequencyUnitLabel.SetPosition( 42, 248 );
        lowerFrequencyUnitLabel.SetSize( 31, 13 );
        lowerFrequencyUnitLabel.SetEditable( false, false );
        lowerFrequencyUnitLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyUnitLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyUnitLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyUnitLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyUnitLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyUnitLabel.SetBorderSize( 1 );
        lowerFrequencyUnitLabel.SetMultiLineEdit( false );
        lowerFrequencyUnitLabel.SetIsNumberEditor( false );
        lowerFrequencyUnitLabel.SetNumberEditorRange( 0, 100 );
        lowerFrequencyUnitLabel.SetNumberEditorInterval( 1 );
        lowerFrequencyUnitLabel.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyUnitLabel.SetHasCustomTextHoverColor( false );
        lowerFrequencyUnitLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyUnitLabel.SetFont( "Arial", 7, true, false );

        upperFrequencyStep1Label = new VoltageLabel( "upperFrequencyStep1Label", "Upper 5.3 Hz Step", this, "5.3" );
        AddComponent( upperFrequencyStep1Label );
        upperFrequencyStep1Label.SetWantsMouseNotifications( false );
        upperFrequencyStep1Label.SetPosition( 19, 67 );
        upperFrequencyStep1Label.SetSize( 15, 15 );
        upperFrequencyStep1Label.SetEditable( false, false );
        upperFrequencyStep1Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep1Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep1Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep1Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep1Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep1Label.SetBorderSize( 1 );
        upperFrequencyStep1Label.SetMultiLineEdit( false );
        upperFrequencyStep1Label.SetIsNumberEditor( false );
        upperFrequencyStep1Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep1Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep1Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep1Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep1Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep1Label.SetFont( "Arial", 7, true, false );

        upperFrequencyStep2Label = new VoltageLabel( "upperFrequencyStep2Label", "Upper 41 Hz Step", this, "41" );
        AddComponent( upperFrequencyStep2Label );
        upperFrequencyStep2Label.SetWantsMouseNotifications( false );
        upperFrequencyStep2Label.SetPosition( 23, 59 );
        upperFrequencyStep2Label.SetSize( 15, 15 );
        upperFrequencyStep2Label.SetEditable( false, false );
        upperFrequencyStep2Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep2Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep2Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep2Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep2Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep2Label.SetBorderSize( 1 );
        upperFrequencyStep2Label.SetMultiLineEdit( false );
        upperFrequencyStep2Label.SetIsNumberEditor( false );
        upperFrequencyStep2Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep2Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep2Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep2Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep2Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep2Label.SetFont( "Arial", 7, true, false );

        upperFrequencyStep3Label = new VoltageLabel( "upperFrequencyStep3Label", "Upper 159 Hz Step", this, "159" );
        AddComponent( upperFrequencyStep3Label );
        upperFrequencyStep3Label.SetWantsMouseNotifications( false );
        upperFrequencyStep3Label.SetPosition( 31, 51 );
        upperFrequencyStep3Label.SetSize( 15, 15 );
        upperFrequencyStep3Label.SetEditable( false, false );
        upperFrequencyStep3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep3Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep3Label.SetBorderSize( 1 );
        upperFrequencyStep3Label.SetMultiLineEdit( false );
        upperFrequencyStep3Label.SetIsNumberEditor( false );
        upperFrequencyStep3Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep3Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep3Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep3Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep3Label.SetFont( "Arial", 7, true, false );

        upperFrequencyStep4Label = new VoltageLabel( "upperFrequencyStep4Label", "Upper 800 Hz Step", this, "800" );
        AddComponent( upperFrequencyStep4Label );
        upperFrequencyStep4Label.SetWantsMouseNotifications( false );
        upperFrequencyStep4Label.SetPosition( 43, 45 );
        upperFrequencyStep4Label.SetSize( 15, 15 );
        upperFrequencyStep4Label.SetEditable( false, false );
        upperFrequencyStep4Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep4Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep4Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep4Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep4Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep4Label.SetBorderSize( 1 );
        upperFrequencyStep4Label.SetMultiLineEdit( false );
        upperFrequencyStep4Label.SetIsNumberEditor( false );
        upperFrequencyStep4Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep4Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep4Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep4Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep4Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep4Label.SetFont( "Arial", 7, true, false );

        upperFrequencyStep5Label = new VoltageLabel( "upperFrequencyStep5Label", "Upper 1591 Hz Step", this, "1591" );
        AddComponent( upperFrequencyStep5Label );
        upperFrequencyStep5Label.SetWantsMouseNotifications( false );
        upperFrequencyStep5Label.SetPosition( 57, 45 );
        upperFrequencyStep5Label.SetSize( 15, 15 );
        upperFrequencyStep5Label.SetEditable( false, false );
        upperFrequencyStep5Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep5Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep5Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep5Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep5Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep5Label.SetBorderSize( 1 );
        upperFrequencyStep5Label.SetMultiLineEdit( false );
        upperFrequencyStep5Label.SetIsNumberEditor( false );
        upperFrequencyStep5Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep5Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep5Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep5Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep5Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep5Label.SetFont( "Arial", 7, true, false );

        upperFrequencyStep6Label = new VoltageLabel( "upperFrequencyStep6Label", "Upper 3000 Hz Step", this, "3k" );
        AddComponent( upperFrequencyStep6Label );
        upperFrequencyStep6Label.SetWantsMouseNotifications( false );
        upperFrequencyStep6Label.SetPosition( 69, 51 );
        upperFrequencyStep6Label.SetSize( 15, 15 );
        upperFrequencyStep6Label.SetEditable( false, false );
        upperFrequencyStep6Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep6Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep6Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep6Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep6Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep6Label.SetBorderSize( 1 );
        upperFrequencyStep6Label.SetMultiLineEdit( false );
        upperFrequencyStep6Label.SetIsNumberEditor( false );
        upperFrequencyStep6Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep6Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep6Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep6Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep6Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep6Label.SetFont( "Arial", 7, true, false );

        upperFrequencyStep7Label = new VoltageLabel( "upperFrequencyStep7Label", "Upper 5300 Hz Step", this, "5.3k" );
        AddComponent( upperFrequencyStep7Label );
        upperFrequencyStep7Label.SetWantsMouseNotifications( false );
        upperFrequencyStep7Label.SetPosition( 78, 58 );
        upperFrequencyStep7Label.SetSize( 15, 15 );
        upperFrequencyStep7Label.SetEditable( false, false );
        upperFrequencyStep7Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep7Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep7Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep7Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep7Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep7Label.SetBorderSize( 1 );
        upperFrequencyStep7Label.SetMultiLineEdit( false );
        upperFrequencyStep7Label.SetIsNumberEditor( false );
        upperFrequencyStep7Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep7Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep7Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep7Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep7Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep7Label.SetFont( "Arial", 7, true, false );

        upperFrequencyStep8Label = new VoltageLabel( "upperFrequencyStep8Label", "Upper 16000 Hz Step", this, "16k" );
        AddComponent( upperFrequencyStep8Label );
        upperFrequencyStep8Label.SetWantsMouseNotifications( false );
        upperFrequencyStep8Label.SetPosition( 77, 67 );
        upperFrequencyStep8Label.SetSize( 20, 15 );
        upperFrequencyStep8Label.SetEditable( false, false );
        upperFrequencyStep8Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        upperFrequencyStep8Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        upperFrequencyStep8Label.SetColor( new Color( 232, 232, 232, 147 ) );
        upperFrequencyStep8Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        upperFrequencyStep8Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        upperFrequencyStep8Label.SetBorderSize( 1 );
        upperFrequencyStep8Label.SetMultiLineEdit( false );
        upperFrequencyStep8Label.SetIsNumberEditor( false );
        upperFrequencyStep8Label.SetNumberEditorRange( 0, 100 );
        upperFrequencyStep8Label.SetNumberEditorInterval( 1 );
        upperFrequencyStep8Label.SetNumberEditorUsesMouseWheel( false );
        upperFrequencyStep8Label.SetHasCustomTextHoverColor( false );
        upperFrequencyStep8Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        upperFrequencyStep8Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep2Label = new VoltageLabel( "lowerFrequencyStep2Label", "Lower 70 Hz Step", this, "70" );
        AddComponent( lowerFrequencyStep2Label );
        lowerFrequencyStep2Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep2Label.SetPosition( 23, 199 );
        lowerFrequencyStep2Label.SetSize( 15, 15 );
        lowerFrequencyStep2Label.SetEditable( false, false );
        lowerFrequencyStep2Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep2Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep2Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep2Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep2Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep2Label.SetBorderSize( 1 );
        lowerFrequencyStep2Label.SetMultiLineEdit( false );
        lowerFrequencyStep2Label.SetIsNumberEditor( false );
        lowerFrequencyStep2Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep2Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep2Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep2Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep2Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep2Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep3Label = new VoltageLabel( "lowerFrequencyStep3Label", "Lower 100 Hz Step", this, "100" );
        AddComponent( lowerFrequencyStep3Label );
        lowerFrequencyStep3Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep3Label.SetPosition( 29, 191 );
        lowerFrequencyStep3Label.SetSize( 15, 15 );
        lowerFrequencyStep3Label.SetEditable( false, false );
        lowerFrequencyStep3Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep3Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep3Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep3Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep3Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep3Label.SetBorderSize( 1 );
        lowerFrequencyStep3Label.SetMultiLineEdit( false );
        lowerFrequencyStep3Label.SetIsNumberEditor( false );
        lowerFrequencyStep3Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep3Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep3Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep3Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep3Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep3Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep4Label = new VoltageLabel( "lowerFrequencyStep4Label", "Lower 250 Hz Step", this, "250" );
        AddComponent( lowerFrequencyStep4Label );
        lowerFrequencyStep4Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep4Label.SetPosition( 43, 185 );
        lowerFrequencyStep4Label.SetSize( 15, 15 );
        lowerFrequencyStep4Label.SetEditable( false, false );
        lowerFrequencyStep4Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep4Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep4Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep4Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep4Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep4Label.SetBorderSize( 1 );
        lowerFrequencyStep4Label.SetMultiLineEdit( false );
        lowerFrequencyStep4Label.SetIsNumberEditor( false );
        lowerFrequencyStep4Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep4Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep4Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep4Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep4Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep4Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep5Label = new VoltageLabel( "lowerFrequencyStep5Label", "Lower 482 Hz Step", this, "482" );
        AddComponent( lowerFrequencyStep5Label );
        lowerFrequencyStep5Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep5Label.SetPosition( 57, 185 );
        lowerFrequencyStep5Label.SetSize( 15, 15 );
        lowerFrequencyStep5Label.SetEditable( false, false );
        lowerFrequencyStep5Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep5Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep5Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep5Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep5Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep5Label.SetBorderSize( 1 );
        lowerFrequencyStep5Label.SetMultiLineEdit( false );
        lowerFrequencyStep5Label.SetIsNumberEditor( false );
        lowerFrequencyStep5Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep5Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep5Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep5Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep5Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep5Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep6Label = new VoltageLabel( "lowerFrequencyStep6Label", "Lower 1000 Hz Step", this, "1k" );
        AddComponent( lowerFrequencyStep6Label );
        lowerFrequencyStep6Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep6Label.SetPosition( 69, 191 );
        lowerFrequencyStep6Label.SetSize( 15, 15 );
        lowerFrequencyStep6Label.SetEditable( false, false );
        lowerFrequencyStep6Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep6Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep6Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep6Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep6Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep6Label.SetBorderSize( 1 );
        lowerFrequencyStep6Label.SetMultiLineEdit( false );
        lowerFrequencyStep6Label.SetIsNumberEditor( false );
        lowerFrequencyStep6Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep6Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep6Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep6Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep6Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep6Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep7Label = new VoltageLabel( "lowerFrequencyStep7Label", "Lower 2000 Hz Step", this, "2k" );
        AddComponent( lowerFrequencyStep7Label );
        lowerFrequencyStep7Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep7Label.SetPosition( 78, 198 );
        lowerFrequencyStep7Label.SetSize( 15, 15 );
        lowerFrequencyStep7Label.SetEditable( false, false );
        lowerFrequencyStep7Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep7Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep7Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep7Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep7Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep7Label.SetBorderSize( 1 );
        lowerFrequencyStep7Label.SetMultiLineEdit( false );
        lowerFrequencyStep7Label.SetIsNumberEditor( false );
        lowerFrequencyStep7Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep7Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep7Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep7Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep7Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep7Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep8Label = new VoltageLabel( "lowerFrequencyStep8Label", "Lower 7500 Hz Step", this, "7.5k" );
        AddComponent( lowerFrequencyStep8Label );
        lowerFrequencyStep8Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep8Label.SetPosition( 78, 207 );
        lowerFrequencyStep8Label.SetSize( 20, 15 );
        lowerFrequencyStep8Label.SetEditable( false, false );
        lowerFrequencyStep8Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep8Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep8Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep8Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep8Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep8Label.SetBorderSize( 1 );
        lowerFrequencyStep8Label.SetMultiLineEdit( false );
        lowerFrequencyStep8Label.SetIsNumberEditor( false );
        lowerFrequencyStep8Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep8Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep8Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep8Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep8Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep8Label.SetFont( "Arial", 7, true, false );

        lowerFrequencyStep1Label = new VoltageLabel( "lowerFrequencyStep1Label", "Lower 16 Hz Step", this, "16" );
        AddComponent( lowerFrequencyStep1Label );
        lowerFrequencyStep1Label.SetWantsMouseNotifications( false );
        lowerFrequencyStep1Label.SetPosition( 19, 207 );
        lowerFrequencyStep1Label.SetSize( 15, 15 );
        lowerFrequencyStep1Label.SetEditable( false, false );
        lowerFrequencyStep1Label.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lowerFrequencyStep1Label.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lowerFrequencyStep1Label.SetColor( new Color( 232, 232, 232, 147 ) );
        lowerFrequencyStep1Label.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lowerFrequencyStep1Label.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lowerFrequencyStep1Label.SetBorderSize( 1 );
        lowerFrequencyStep1Label.SetMultiLineEdit( false );
        lowerFrequencyStep1Label.SetIsNumberEditor( false );
        lowerFrequencyStep1Label.SetNumberEditorRange( 0, 100 );
        lowerFrequencyStep1Label.SetNumberEditorInterval( 1 );
        lowerFrequencyStep1Label.SetNumberEditorUsesMouseWheel( false );
        lowerFrequencyStep1Label.SetHasCustomTextHoverColor( false );
        lowerFrequencyStep1Label.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lowerFrequencyStep1Label.SetFont( "Arial", 7, true, false );

        inputJackUpper = new VoltageAudioJack( "inputJackUpper", "Upper High-Pass Input", this, JackType.JackType_AudioInput );
        AddComponent( inputJackUpper );
        inputJackUpper.SetWantsMouseNotifications( false );
        inputJackUpper.SetPosition( 10, 131 );
        inputJackUpper.SetSize( 37, 37 );
        inputJackUpper.SetSkin( "Dark Jack Straight" );

        manufacturerCreditLabel = new VoltageLabel( "manufacturerCreditLabel", "Manufacturer Credit", this, "insect laboratories pittsburgh, PA        united states & beyond" );
        AddComponent( manufacturerCreditLabel );
        manufacturerCreditLabel.SetWantsMouseNotifications( false );
        manufacturerCreditLabel.SetPosition( 24, 337 );
        manufacturerCreditLabel.SetSize( 60, 20 );
        manufacturerCreditLabel.SetEditable( false, false );
        manufacturerCreditLabel.SetJustificationFlags( VoltageLabel.Justification.Left );
        manufacturerCreditLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerCreditLabel.SetColor( new Color( 35, 35, 35, 147 ) );
        manufacturerCreditLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manufacturerCreditLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerCreditLabel.SetBorderSize( 1 );
        manufacturerCreditLabel.SetMultiLineEdit( true );
        manufacturerCreditLabel.SetIsNumberEditor( false );
        manufacturerCreditLabel.SetNumberEditorRange( 0, 100 );
        manufacturerCreditLabel.SetNumberEditorInterval( 1 );
        manufacturerCreditLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerCreditLabel.SetHasCustomTextHoverColor( false );
        manufacturerCreditLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerCreditLabel.SetFont( "Arial Black", 6, true, false );
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
        updateFilters();
        resetPending = true;

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
                resetPending = true;
            }
            break;

            case Variation_Loading_Start:    // sent when a variation is about to load
            {
            }
            break;

            case Variation_Loading_Finish:   // sent when a variation has just finished loading
            {
                resetPending = true;
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
                resetPending = true;
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
        updateFilters();

        if (wasBypassed || resetPending) {
            upperFilter.reset();
            lowerFilter.reset();
            wasBypassed = false;
            resetPending = false;
        }

        double upperInput = readInput(inputJackUpper);
        double lowerInput = readInput(inputJackLower);
        outputJackUpper.SetValue(warmMakeupStage(upperFilter.process(upperInput)));
        outputJackLower.SetValue(lowerFilter.process(lowerInput));
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
        // Direct transfer skips control and DSP work; re-entry clears stale filter state once.
        outputJackUpper.SetValue(readInput(inputJackUpper));
        outputJackLower.SetValue(readInput(inputJackLower));
        wasBypassed = true;
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
        if (component == knobHighPassUpper) {
            return String.format(java.util.Locale.ROOT, "Upper HPF cutoff: %.1f Hz; one-pole, 6 dB/octave; typed Hz snaps to nearest labeled step", selectedFrequency(UPPER_CUTOFFS, knobHighPassUpper.GetValue()));
        }
        if (component == knobHighPassLower) {
            return String.format(java.util.Locale.ROOT, "Lower HPF cutoff: %.1f Hz; one-pole, 6 dB/octave; typed Hz snaps to nearest labeled step", selectedFrequency(LOWER_CUTOFFS, knobHighPassLower.GetValue()));
        }
        if (component == inputJackUpper) return "Upper HPF input; independent signal path";
        if (component == outputJackUpper) return "Upper HPF output; subtle warm output-stage compression";
        if (component == inputJackLower) return "Lower HPF input; independent signal path";
        if (component == outputJackLower) return "Lower HPF output; clean one-pole response";
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
        if (component == knobHighPassUpper && Double.isFinite(newValue)) {
            knobHighPassUpper.SetValue(nearestStep(UPPER_CUTOFFS, newValue));
            return;
        }
        if (component == knobHighPassLower && Double.isFinite(newValue)) {
            knobHighPassLower.SetValue(nearestStep(LOWER_CUTOFFS, newValue));
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
    private VoltageLabel manufacturerCreditLabel;
    private VoltageAudioJack inputJackUpper;
    private VoltageLabel lowerFrequencyStep1Label;
    private VoltageLabel lowerFrequencyStep8Label;
    private VoltageLabel lowerFrequencyStep7Label;
    private VoltageLabel lowerFrequencyStep6Label;
    private VoltageLabel lowerFrequencyStep5Label;
    private VoltageLabel lowerFrequencyStep4Label;
    private VoltageLabel lowerFrequencyStep3Label;
    private VoltageLabel lowerFrequencyStep2Label;
    private VoltageLabel upperFrequencyStep8Label;
    private VoltageLabel upperFrequencyStep7Label;
    private VoltageLabel upperFrequencyStep6Label;
    private VoltageLabel upperFrequencyStep5Label;
    private VoltageLabel upperFrequencyStep4Label;
    private VoltageLabel upperFrequencyStep3Label;
    private VoltageLabel upperFrequencyStep2Label;
    private VoltageLabel upperFrequencyStep1Label;
    private VoltageLabel lowerFrequencyUnitLabel;
    private VoltageLabel upperFrequencyUnitLabel;
    private VoltageAudioJack outputJackLower;
    private VoltageAudioJack inputJackLower;
    private VoltageKnob knobHighPassLower;
    private VoltageKnob knobHighPassUpper;
    private VoltageAudioJack outputJackUpper;
    private VoltageLabel makerMarkLabel;
    private VoltageLabel modelIdentifierLabel;
    private VoltageLabel moduleTitleLabel;


    //[user-code-and-variables]    Add your own variables and functions here
    private boolean wasBypassed;
    private volatile boolean resetPending;
    private static final double SAMPLE_RATE = 48000.0;
    private static final double[] UPPER_CUTOFFS = {5.3, 41.0, 159.0, 800.0, 1591.0, 3000.0, 5300.0, 16000.0};
    private static final double[] LOWER_CUTOFFS = {16.0, 70.0, 100.0, 250.0, 482.0, 1000.0, 2000.0, 7500.0};
    private final HighPassSection upperFilter = new HighPassSection();
    private final HighPassSection lowerFilter = new HighPassSection();

    private void updateFilters() {
        upperFilter.setCutoff(selectedIndex(knobHighPassUpper.GetValue()), UPPER_CUTOFFS);
        lowerFilter.setCutoff(selectedIndex(knobHighPassLower.GetValue()), LOWER_CUTOFFS);
    }

    private static int selectedIndex(double value) {
        return (int) Math.max(0, Math.min(7, Math.round(value) - 1));
    }

    private static double selectedFrequency(double[] frequencies, double value) {
        return frequencies[selectedIndex(value)];
    }

    private static double nearestStep(double[] frequencies, double requestedHz) {
        double target = Math.max(frequencies[0], Math.min(frequencies[frequencies.length - 1], requestedHz));
        int best = 0;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < frequencies.length; i++) {
            double distance = Math.abs(Math.log(target / frequencies[i]));
            if (distance < bestDistance) {
                best = i;
                bestDistance = distance;
            }
        }
        return best + 1.0;
    }

    private static double readInput(VoltageAudioJack input) {
        if (!input.IsConnected()) return 0.0;
        double value = input.GetValue();
        return Double.isFinite(value) ? Math.max(-1.0e6, Math.min(1.0e6, value)) : 0.0;
    }

    private static double warmMakeupStage(double input) {
        return input / (1.0 + 0.006 * Math.abs(input));
    }

    private static final class HighPassSection {
        private int step = -1;
        private double previousInput;
        private double previousOutput;
        private double feedForward = 1.0;
        private double feedback = 0.0;

        private void setCutoff(int requestedStep, double[] frequencies) {
            if (requestedStep == step) return;
            step = requestedStep;
            double tangent = Math.tan(Math.PI * frequencies[step] / SAMPLE_RATE);
            feedForward = 1.0 / (1.0 + tangent);
            feedback = (1.0 - tangent) / (1.0 + tangent);
        }

        private double process(double input) {
            double output = feedForward * (input - previousInput) + feedback * previousOutput;
            previousInput = input;
            previousOutput = Double.isFinite(output) ? output : 0.0;
            return previousOutput;
        }

        private void reset() {
            previousInput = 0.0;
            previousOutput = 0.0;
        }
    }
    //[/user-code-and-variables]
}

 