package com.insectlabs.rvg;


import voltage.controllers.*;
import voltage.core.*;
import voltage.core.Jack.JackType;
import voltage.sources.*;
import voltage.utility.*;
import voltage.processors.*;
import voltage.effects.*;
import java.awt.*;

//[user-imports]   Add your own imports here
import java.util.concurrent.atomic.AtomicBoolean;
//[/user-imports]


public class rvg extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape")
    public rvg( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "Random Voltage Generator", ModuleType.ModuleType_Source, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "f477bfcd0e7a4f0cae7e06bf4fecf8d6" );
    }

void InitializeControls()
{

        labelComp = new VoltageLabel( "labelComp", "Compensate Label", this, "COMPENSATE" );
        AddComponent( labelComp );
        labelComp.SetWantsMouseNotifications( false );
        labelComp.SetPosition( 75, 283 );
        labelComp.SetSize( 37, 13 );
        labelComp.SetEditable( false, false );
        labelComp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelComp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelComp.SetColor( new Color( 46, 46, 46, 255 ) );
        labelComp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelComp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelComp.SetBorderSize( 1 );
        labelComp.SetMultiLineEdit( false );
        labelComp.SetIsNumberEditor( false );
        labelComp.SetNumberEditorRange( 0, 100 );
        labelComp.SetNumberEditorInterval( 1 );
        labelComp.SetNumberEditorUsesMouseWheel( false );
        labelComp.SetHasCustomTextHoverColor( false );
        labelComp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelComp.SetFont( "Arial", 7, true, false );

        labelBlown = new VoltageLabel( "labelBlown", "Blown Circuit Label", this, "FAILURE" );
        AddComponent( labelBlown );
        labelBlown.SetWantsMouseNotifications( false );
        labelBlown.SetPosition( 77, 313 );
        labelBlown.SetSize( 32, 13 );
        labelBlown.SetEditable( false, false );
        labelBlown.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBlown.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBlown.SetColor( new Color( 46, 46, 46, 255 ) );
        labelBlown.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBlown.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBlown.SetBorderSize( 1 );
        labelBlown.SetMultiLineEdit( false );
        labelBlown.SetIsNumberEditor( false );
        labelBlown.SetNumberEditorRange( 0, 100 );
        labelBlown.SetNumberEditorInterval( 1 );
        labelBlown.SetNumberEditorUsesMouseWheel( false );
        labelBlown.SetHasCustomTextHoverColor( false );
        labelBlown.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBlown.SetFont( "Arial", 7, true, false );

        labelRun = new VoltageLabel( "labelRun", "Free Run Label", this, "FREE RUN" );
        AddComponent( labelRun );
        labelRun.SetWantsMouseNotifications( false );
        labelRun.SetPosition( 0, 191 );
        labelRun.SetSize( 115, 13 );
        labelRun.SetEditable( false, false );
        labelRun.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRun.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRun.SetColor( new Color( 46, 46, 46, 255 ) );
        labelRun.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRun.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRun.SetBorderSize( 1 );
        labelRun.SetMultiLineEdit( false );
        labelRun.SetIsNumberEditor( false );
        labelRun.SetNumberEditorRange( 0, 100 );
        labelRun.SetNumberEditorInterval( 1 );
        labelRun.SetNumberEditorUsesMouseWheel( false );
        labelRun.SetHasCustomTextHoverColor( false );
        labelRun.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRun.SetFont( "Arial", 7, true, false );

        labelNew = new VoltageLabel( "labelNew", "New Label", this, "NEW" );
        AddComponent( labelNew );
        labelNew.SetWantsMouseNotifications( false );
        labelNew.SetPosition( 0, 203 );
        labelNew.SetSize( 115, 13 );
        labelNew.SetEditable( false, false );
        labelNew.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelNew.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNew.SetColor( new Color( 46, 46, 46, 255 ) );
        labelNew.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNew.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNew.SetBorderSize( 1 );
        labelNew.SetMultiLineEdit( false );
        labelNew.SetIsNumberEditor( false );
        labelNew.SetNumberEditorRange( 0, 100 );
        labelNew.SetNumberEditorInterval( 1 );
        labelNew.SetNumberEditorUsesMouseWheel( false );
        labelNew.SetHasCustomTextHoverColor( false );
        labelNew.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNew.SetFont( "Arial", 7, true, false );

        labelSelection = new VoltageLabel( "labelSelection", "Selection Label", this, "SELECTION" );
        AddComponent( labelSelection );
        labelSelection.SetWantsMouseNotifications( false );
        labelSelection.SetPosition( 0, 235 );
        labelSelection.SetSize( 115, 13 );
        labelSelection.SetEditable( false, false );
        labelSelection.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSelection.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSelection.SetColor( new Color( 46, 46, 46, 255 ) );
        labelSelection.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSelection.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSelection.SetBorderSize( 1 );
        labelSelection.SetMultiLineEdit( false );
        labelSelection.SetIsNumberEditor( false );
        labelSelection.SetNumberEditorRange( 0, 100 );
        labelSelection.SetNumberEditorInterval( 1 );
        labelSelection.SetNumberEditorUsesMouseWheel( false );
        labelSelection.SetHasCustomTextHoverColor( false );
        labelSelection.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSelection.SetFont( "Arial", 7, true, false );

        switchInhibit = new VoltageSwitch( "switchInhibit", "Inhibit / Free Run Switch", this, 0 );
        AddComponent( switchInhibit );
        switchInhibit.SetWantsMouseNotifications( false );
        switchInhibit.SetPosition( 40, 165 );
        switchInhibit.SetSize( 35, 35 );
        switchInhibit.SetSkin( "2-State Silver" );

        knobRate = new VoltageKnob( "knobRate", "Mean Rate", this, 0.0, 1.0, 0.25 );
        AddComponent( knobRate );
        knobRate.SetWantsMouseNotifications( false );
        knobRate.SetPosition( 10, 40 );
        knobRate.SetSize( 31, 31 );
        knobRate.SetSkin( "ARP2500 Sm Blue" );
        knobRate.SetRange( 0.0, 1.0, 0.25, false, 0 );
        knobRate.SetKnobParams( 215, 145 );
        knobRate.DisplayValueInPercent( true );
        knobRate.SetKnobAdjustsRing( true );

        knobVariance = new VoltageKnob( "knobVariance", "Variance", this, 0.0, 1.0, 0.0 );
        AddComponent( knobVariance );
        knobVariance.SetWantsMouseNotifications( false );
        knobVariance.SetPosition( 70, 40 );
        knobVariance.SetSize( 31, 31 );
        knobVariance.SetSkin( "ARP2500 Sm Blue" );
        knobVariance.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobVariance.SetKnobParams( 215, 145 );
        knobVariance.DisplayValueInPercent( true );
        knobVariance.SetKnobAdjustsRing( true );

        knobV1 = new VoltageKnob( "knobV1", "V1 Level", this, 0.0, 1.0, 0.0 );
        AddComponent( knobV1 );
        knobV1.SetWantsMouseNotifications( false );
        knobV1.SetPosition( 10, 85 );
        knobV1.SetSize( 31, 31 );
        knobV1.SetSkin( "ARP2500 Sm Silver" );
        knobV1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobV1.SetKnobParams( 215, 145 );
        knobV1.DisplayValueInPercent( true );
        knobV1.SetKnobAdjustsRing( true );

        knobV2 = new VoltageKnob( "knobV2", "V2 Level", this, 0.0, 1.0, 0.0 );
        AddComponent( knobV2 );
        knobV2.SetWantsMouseNotifications( false );
        knobV2.SetPosition( 70, 85 );
        knobV2.SetSize( 31, 31 );
        knobV2.SetSkin( "ARP2500 Sm Silver" );
        knobV2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobV2.SetKnobParams( 215, 145 );
        knobV2.DisplayValueInPercent( true );
        knobV2.SetKnobAdjustsRing( true );

        buttonSelect = new VoltageButton( "buttonSelect", "Re-Select Button", this );
        AddComponent( buttonSelect );
        buttonSelect.SetWantsMouseNotifications( false );
        buttonSelect.SetPosition( 49, 133 );
        buttonSelect.SetSize( 16, 16 );
        buttonSelect.SetSkin( "2500 Round Red" );
        buttonSelect.ShowOverlay( false );
        buttonSelect.SetOverlayText( "" );
        buttonSelect.SetAutoRepeat( false );

        lampSelection = new VoltageLED( "lampSelection", "New Selection Lamp", this );
        AddComponent( lampSelection );
        lampSelection.SetWantsMouseNotifications( false );
        lampSelection.SetPosition( 47, 215 );
        lampSelection.SetSize( 20, 20 );
        lampSelection.SetSkin( "2500 Lamp Red" );

        outputV1 = new VoltageAudioJack( "outputV1", "V1 Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputV1 );
        outputV1.SetWantsMouseNotifications( false );
        outputV1.SetPosition( 10, 250 );
        outputV1.SetSize( 25, 25 );
        outputV1.SetSkin( "Jack Round 25px" );

        outputV2 = new VoltageAudioJack( "outputV2", "V2 Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputV2 );
        outputV2.SetWantsMouseNotifications( false );
        outputV2.SetPosition( 45, 250 );
        outputV2.SetSize( 25, 25 );
        outputV2.SetSkin( "Jack Round 25px" );

        outputTrig = new VoltageAudioJack( "outputTrig", "Trig Out", this, JackType.JackType_AudioOutput );
        AddComponent( outputTrig );
        outputTrig.SetWantsMouseNotifications( false );
        outputTrig.SetPosition( 80, 250 );
        outputTrig.SetSize( 25, 25 );
        outputTrig.SetSkin( "Jack Round 25px" );

        inputSelect = new VoltageAudioJack( "inputSelect", "Select Input", this, JackType.JackType_AudioInput );
        AddComponent( inputSelect );
        inputSelect.SetWantsMouseNotifications( false );
        inputSelect.SetPosition( 45, 290 );
        inputSelect.SetSize( 25, 25 );
        inputSelect.SetSkin( "Jack Round White Ring" );

        inputMean = new VoltageAudioJack( "inputMean", "Voltage Control Of Mean", this, JackType.JackType_AudioInput );
        AddComponent( inputMean );
        inputMean.SetWantsMouseNotifications( false );
        inputMean.SetPosition( 10, 290 );
        inputMean.SetSize( 25, 25 );
        inputMean.SetSkin( "Jack Round White Ring" );

        scale1A30DeepToneGenModLabel2 = new VoltageLabel( "scale1A30DeepToneGenModLabel2", "1A-30 deep tone gen/mod Label", this, "RANDOM VOLTAGE          GENERATOR" );
        AddComponent( scale1A30DeepToneGenModLabel2 );
        scale1A30DeepToneGenModLabel2.SetWantsMouseNotifications( false );
        scale1A30DeepToneGenModLabel2.SetPosition( 3, 335 );
        scale1A30DeepToneGenModLabel2.SetSize( 109, 23 );
        scale1A30DeepToneGenModLabel2.SetEditable( false, false );
        scale1A30DeepToneGenModLabel2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        scale1A30DeepToneGenModLabel2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale1A30DeepToneGenModLabel2.SetColor( new Color( 166, 36, 36, 255 ) );
        scale1A30DeepToneGenModLabel2.SetBkColor( new Color( 0, 0, 0, 0 ) );
        scale1A30DeepToneGenModLabel2.SetBorderColor( new Color( 26, 26, 26, 255 ) );
        scale1A30DeepToneGenModLabel2.SetBorderSize( 2 );
        scale1A30DeepToneGenModLabel2.SetMultiLineEdit( true );
        scale1A30DeepToneGenModLabel2.SetIsNumberEditor( false );
        scale1A30DeepToneGenModLabel2.SetNumberEditorRange( 0, 100 );
        scale1A30DeepToneGenModLabel2.SetNumberEditorInterval( 1 );
        scale1A30DeepToneGenModLabel2.SetNumberEditorUsesMouseWheel( false );
        scale1A30DeepToneGenModLabel2.SetHasCustomTextHoverColor( false );
        scale1A30DeepToneGenModLabel2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale1A30DeepToneGenModLabel2.SetFont( "Arial", 9, true, false );

        labelRate = new VoltageLabel( "labelRate", "Mean Rate Label", this, "MEAN RATE" );
        AddComponent( labelRate );
        labelRate.SetWantsMouseNotifications( false );
        labelRate.SetPosition( 0, 71 );
        labelRate.SetSize( 50, 13 );
        labelRate.SetEditable( false, false );
        labelRate.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRate.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRate.SetColor( new Color( 46, 46, 46, 255 ) );
        labelRate.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRate.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRate.SetBorderSize( 1 );
        labelRate.SetMultiLineEdit( false );
        labelRate.SetIsNumberEditor( false );
        labelRate.SetNumberEditorRange( 0, 100 );
        labelRate.SetNumberEditorInterval( 1 );
        labelRate.SetNumberEditorUsesMouseWheel( false );
        labelRate.SetHasCustomTextHoverColor( false );
        labelRate.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRate.SetFont( "Arial", 7, true, false );

        labelVariance = new VoltageLabel( "labelVariance", "Variance Label", this, "VARIANCE" );
        AddComponent( labelVariance );
        labelVariance.SetWantsMouseNotifications( false );
        labelVariance.SetPosition( 57, 71 );
        labelVariance.SetSize( 58, 13 );
        labelVariance.SetEditable( false, false );
        labelVariance.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVariance.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVariance.SetColor( new Color( 46, 46, 46, 255 ) );
        labelVariance.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVariance.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVariance.SetBorderSize( 1 );
        labelVariance.SetMultiLineEdit( false );
        labelVariance.SetIsNumberEditor( false );
        labelVariance.SetNumberEditorRange( 0, 100 );
        labelVariance.SetNumberEditorInterval( 1 );
        labelVariance.SetNumberEditorUsesMouseWheel( false );
        labelVariance.SetHasCustomTextHoverColor( false );
        labelVariance.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVariance.SetFont( "Arial", 7, true, false );

        labelV1 = new VoltageLabel( "labelV1", "V1 Label", this, "V1 LEVEL" );
        AddComponent( labelV1 );
        labelV1.SetWantsMouseNotifications( false );
        labelV1.SetPosition( 0, 116 );
        labelV1.SetSize( 52, 13 );
        labelV1.SetEditable( false, false );
        labelV1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV1.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV1.SetBorderSize( 1 );
        labelV1.SetMultiLineEdit( false );
        labelV1.SetIsNumberEditor( false );
        labelV1.SetNumberEditorRange( 0, 100 );
        labelV1.SetNumberEditorInterval( 1 );
        labelV1.SetNumberEditorUsesMouseWheel( false );
        labelV1.SetHasCustomTextHoverColor( false );
        labelV1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV1.SetFont( "Arial", 7, true, false );

        labelV1Out = new VoltageLabel( "labelV1Out", "V1 Output Label", this, "V1" );
        AddComponent( labelV1Out );
        labelV1Out.SetWantsMouseNotifications( false );
        labelV1Out.SetPosition( 5, 273 );
        labelV1Out.SetSize( 37, 13 );
        labelV1Out.SetEditable( false, false );
        labelV1Out.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV1Out.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV1Out.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV1Out.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV1Out.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV1Out.SetBorderSize( 1 );
        labelV1Out.SetMultiLineEdit( false );
        labelV1Out.SetIsNumberEditor( false );
        labelV1Out.SetNumberEditorRange( 0, 100 );
        labelV1Out.SetNumberEditorInterval( 1 );
        labelV1Out.SetNumberEditorUsesMouseWheel( false );
        labelV1Out.SetHasCustomTextHoverColor( false );
        labelV1Out.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV1Out.SetFont( "Arial", 7, true, false );

        labelV2Out = new VoltageLabel( "labelV2Out", "V2 Output Label", this, "V2" );
        AddComponent( labelV2Out );
        labelV2Out.SetWantsMouseNotifications( false );
        labelV2Out.SetPosition( 40, 274 );
        labelV2Out.SetSize( 37, 13 );
        labelV2Out.SetEditable( false, false );
        labelV2Out.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV2Out.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV2Out.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV2Out.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV2Out.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV2Out.SetBorderSize( 1 );
        labelV2Out.SetMultiLineEdit( false );
        labelV2Out.SetIsNumberEditor( false );
        labelV2Out.SetNumberEditorRange( 0, 100 );
        labelV2Out.SetNumberEditorInterval( 1 );
        labelV2Out.SetNumberEditorUsesMouseWheel( false );
        labelV2Out.SetHasCustomTextHoverColor( false );
        labelV2Out.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV2Out.SetFont( "Arial", 7, true, false );

        labelTrigOut = new VoltageLabel( "labelTrigOut", "Trigger Output", this, "TRIG" );
        AddComponent( labelTrigOut );
        labelTrigOut.SetWantsMouseNotifications( false );
        labelTrigOut.SetPosition( 77, 274 );
        labelTrigOut.SetSize( 32, 13 );
        labelTrigOut.SetEditable( false, false );
        labelTrigOut.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTrigOut.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTrigOut.SetColor( new Color( 46, 46, 46, 255 ) );
        labelTrigOut.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTrigOut.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTrigOut.SetBorderSize( 1 );
        labelTrigOut.SetMultiLineEdit( false );
        labelTrigOut.SetIsNumberEditor( false );
        labelTrigOut.SetNumberEditorRange( 0, 100 );
        labelTrigOut.SetNumberEditorInterval( 1 );
        labelTrigOut.SetNumberEditorUsesMouseWheel( false );
        labelTrigOut.SetHasCustomTextHoverColor( false );
        labelTrigOut.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTrigOut.SetFont( "Arial", 7, true, false );

        labelSelectInput = new VoltageLabel( "labelSelectInput", "Select Input Label", this, "SELECT" );
        AddComponent( labelSelectInput );
        labelSelectInput.SetWantsMouseNotifications( false );
        labelSelectInput.SetPosition( 40, 313 );
        labelSelectInput.SetSize( 37, 13 );
        labelSelectInput.SetEditable( false, false );
        labelSelectInput.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSelectInput.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSelectInput.SetColor( new Color( 46, 46, 46, 255 ) );
        labelSelectInput.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSelectInput.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSelectInput.SetBorderSize( 1 );
        labelSelectInput.SetMultiLineEdit( false );
        labelSelectInput.SetIsNumberEditor( false );
        labelSelectInput.SetNumberEditorRange( 0, 100 );
        labelSelectInput.SetNumberEditorInterval( 1 );
        labelSelectInput.SetNumberEditorUsesMouseWheel( false );
        labelSelectInput.SetHasCustomTextHoverColor( false );
        labelSelectInput.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSelectInput.SetFont( "Arial", 7, true, false );

        labelMean = new VoltageLabel( "labelMean", "Voltage Control Of Mean Input Label", this, "VC MEAN" );
        AddComponent( labelMean );
        labelMean.SetWantsMouseNotifications( false );
        labelMean.SetPosition( 5, 314 );
        labelMean.SetSize( 35, 13 );
        labelMean.SetEditable( false, false );
        labelMean.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMean.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMean.SetColor( new Color( 46, 46, 46, 255 ) );
        labelMean.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMean.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMean.SetBorderSize( 1 );
        labelMean.SetMultiLineEdit( false );
        labelMean.SetIsNumberEditor( false );
        labelMean.SetNumberEditorRange( 0, 100 );
        labelMean.SetNumberEditorInterval( 1 );
        labelMean.SetNumberEditorUsesMouseWheel( false );
        labelMean.SetHasCustomTextHoverColor( false );
        labelMean.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMean.SetFont( "Arial", 7, true, false );

        labelV2 = new VoltageLabel( "labelV2", "V2 Label", this, "V2 LEVEL" );
        AddComponent( labelV2 );
        labelV2.SetWantsMouseNotifications( false );
        labelV2.SetPosition( 57, 116 );
        labelV2.SetSize( 58, 13 );
        labelV2.SetEditable( false, false );
        labelV2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelV2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelV2.SetColor( new Color( 46, 46, 46, 255 ) );
        labelV2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelV2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelV2.SetBorderSize( 1 );
        labelV2.SetMultiLineEdit( false );
        labelV2.SetIsNumberEditor( false );
        labelV2.SetNumberEditorRange( 0, 100 );
        labelV2.SetNumberEditorInterval( 1 );
        labelV2.SetNumberEditorUsesMouseWheel( false );
        labelV2.SetHasCustomTextHoverColor( false );
        labelV2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelV2.SetFont( "Arial", 7, true, false );

        labelSelect = new VoltageLabel( "labelSelect", "Re-Select Label", this, "RE-SELECT" );
        AddComponent( labelSelect );
        labelSelect.SetWantsMouseNotifications( false );
        labelSelect.SetPosition( 0, 148 );
        labelSelect.SetSize( 115, 13 );
        labelSelect.SetEditable( false, false );
        labelSelect.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSelect.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSelect.SetColor( new Color( 46, 46, 46, 255 ) );
        labelSelect.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSelect.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSelect.SetBorderSize( 1 );
        labelSelect.SetMultiLineEdit( false );
        labelSelect.SetIsNumberEditor( false );
        labelSelect.SetNumberEditorRange( 0, 100 );
        labelSelect.SetNumberEditorInterval( 1 );
        labelSelect.SetNumberEditorUsesMouseWheel( false );
        labelSelect.SetHasCustomTextHoverColor( false );
        labelSelect.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSelect.SetFont( "Arial", 7, true, false );

        labelInhibit = new VoltageLabel( "labelInhibit", "Inhibit Label", this, "INHIBIT" );
        AddComponent( labelInhibit );
        labelInhibit.SetWantsMouseNotifications( false );
        labelInhibit.SetPosition( 0, 161 );
        labelInhibit.SetSize( 115, 13 );
        labelInhibit.SetEditable( false, false );
        labelInhibit.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelInhibit.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelInhibit.SetColor( new Color( 46, 46, 46, 255 ) );
        labelInhibit.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelInhibit.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelInhibit.SetBorderSize( 1 );
        labelInhibit.SetMultiLineEdit( false );
        labelInhibit.SetIsNumberEditor( false );
        labelInhibit.SetNumberEditorRange( 0, 100 );
        labelInhibit.SetNumberEditorInterval( 1 );
        labelInhibit.SetNumberEditorUsesMouseWheel( false );
        labelInhibit.SetHasCustomTextHoverColor( false );
        labelInhibit.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelInhibit.SetFont( "Arial", 7, true, false );

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 5 );
        imageTTW.SetSize( 31, 10 );
        imageTTW.SetCurrentImage( "image image.svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "rvg" );
        AddComponent( labelNumber );
        labelNumber.SetWantsMouseNotifications( false );
        labelNumber.SetPosition( 85, 5 );
        labelNumber.SetSize( 25, 13 );
        labelNumber.SetEditable( false, false );
        labelNumber.SetJustificationFlags( VoltageLabel.Justification.Right );
        labelNumber.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNumber.SetColor( new Color( 166, 36, 36, 255 ) );
        labelNumber.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNumber.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNumber.SetBorderSize( 1 );
        labelNumber.SetMultiLineEdit( false );
        labelNumber.SetIsNumberEditor( false );
        labelNumber.SetNumberEditorRange( 0, 100 );
        labelNumber.SetNumberEditorInterval( 1 );
        labelNumber.SetNumberEditorUsesMouseWheel( false );
        labelNumber.SetHasCustomTextHoverColor( false );
        labelNumber.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNumber.SetFont( "Courier New", 13, true, false );

        switchComp = new VoltageSwitch( "switchComp", "COMPENSATED MODE Switch", this, 1 );
        AddComponent( switchComp );
        switchComp.SetWantsMouseNotifications( false );
        switchComp.SetPosition( 75, 284 );
        switchComp.SetSize( 35, 35 );
        switchComp.SetSkin( "3-State Silver" );
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
        StartGuiUpdateTimer(25);
        selectionPending.set(true);
        compensationMode = normalizeCompensationMode(switchComp.GetValue());

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
                if (component == buttonSelect && doubleValue >= 0.5) {
                    selectionPending.set(true);
                }
                break;
            case Switch_Changed:
                if (component == switchComp) {
                    compensationMode = normalizeCompensationMode(doubleValue);
                }
                break;
            case GUI_Update_Timer:
                lampSelection.SetValue(selectionLampLevel);
                break;
            case Reset:
                selectionPending.set(true);
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
        if (resumePending) {
            resumePending = false;
            double resumeGate = inputSelect.IsConnected()
                    ? finite(inputSelect.GetValue()) : 0.0;
            selectArmed = resumeGate <= SELECT_GATE_LOW;
        }

        double selectVoltage = inputSelect.IsConnected()
                ? finite(inputSelect.GetValue()) : 0.0;
        boolean externalSelection = false;
        if (selectVoltage <= SELECT_GATE_LOW) {
            selectArmed = true;
        } else if (selectVoltage >= SELECT_GATE_HIGH && selectArmed) {
            externalSelection = true;
            selectArmed = false;
        }

        boolean selected = selectionPending.getAndSet(false) || externalSelection;
        if (!hasSelection) {
            selected = true;
        }
        if (selected) {
            clockPhase = 0.0;
            sampleAndHold();
            triggerSamplesRemaining = TRIGGER_PULSE_SAMPLES;
            selectionLampSamplesRemaining = SELECTION_LAMP_SAMPLES;
        }

        double droopRate = compensationDroopRate(compensationMode);
        if (droopRate > 0.0) {
            double droopStep = droopRate / SAMPLE_RATE;
            heldV1 = droopTowardZero(heldV1, droopStep);
            heldV2 = droopTowardZero(heldV2, droopStep);
        }

        double trigger = triggerSamplesRemaining > 0 ? TRIGGER_VOLTS : 0.0;
        if (triggerSamplesRemaining > 0) {
            triggerSamplesRemaining--;
        }
        if (selectionLampSamplesRemaining > 0) {
            selectionLampSamplesRemaining--;
            selectionLampLevel = 1.0;
        } else {
            selectionLampLevel = 0.0;
        }

        boolean inhibited = switchInhibit.GetValue() >= 0.5;
        if (inhibited) {
            clockPhase = 0.0;
        } else {
            double knobRate = clamp(knobRateValue(), 0.0, 1.0);
            double baseRate = MIN_RATE_HZ * Math.pow(MAX_RATE_HZ / MIN_RATE_HZ, knobRate);
            if (inputMean.IsConnected()) {
                baseRate *= Math.pow(2.0, 2.0 * finite(inputMean.GetValue()));
            }
            baseRate = clamp(baseRate, MIN_RATE_HZ, MAX_RATE_HZ);

            double variance = clamp(knobVariance.GetValue(), 0.0, 1.0);
            double spread = variance * MAX_VARIANCE_SPREAD;
            double timing = 1.0;
            if (spread > 1.0e-9) {
                timing = Math.exp(spread * (2.0 * heldRateRandom - 1.0))
                        / (Math.sinh(spread) / spread);
            }
            double rate = clamp(baseRate * timing, 0.01, 200.0);
            clockPhase += rate / SAMPLE_RATE;
            if (clockPhase >= 1.0) {
                clockPhase -= 1.0;
                sampleAndHold();
                triggerSamplesRemaining = TRIGGER_PULSE_SAMPLES;
                selectionLampSamplesRemaining = SELECTION_LAMP_SAMPLES;
                trigger = TRIGGER_VOLTS;
            }
        }

        outputV1.SetValue(heldV1 * clamp(knobV1.GetValue(), 0.0, 1.0));
        outputV2.SetValue(heldV2 * clamp(knobV2.GetValue(), 0.0, 1.0));
        outputTrig.SetValue(trigger);

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
        outputV1.SetValue(0.0);
        outputV2.SetValue(0.0);
        outputTrig.SetValue(0.0);
        selectionPending.set(false);
        triggerSamplesRemaining = 0;
        selectionLampSamplesRemaining = 0;
        selectionLampLevel = 0.0;
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
        if (component == knobRate) {
            return "Mean event rate: 0.2 to 20 selections per second. The Mean CV input scales rate at 0.5 V per octave.";
        }
        if (component == knobVariance) {
            return "Adds random timing variation. At maximum, event-rate multipliers span a 128:1 range with an arithmetic mean of 1.";
        }
        if (component == knobV1 || component == knobV2) {
            return "Scales the bipolar random output from 0 to its full +/-2.5 V range.";
        }
        if (component == switchInhibit) {
            return "Up inhibits the internal clock; down runs it. Manual and external selections remain active while inhibited.";
        }
        if (component == switchComp) {
            return "Up: compensated, no droop. Middle: factory, 15 mV/s toward zero. Down: blown circuit, 115 mV/s toward zero.";
        }
        if (component == buttonSelect) {
            return "Take a new random selection immediately.";
        }
        if (component == lampSelection) {
            return "Lights for 100 ms after each random selection.";
        }
        if (component == inputMean) {
            return "Mean-rate CV: 0.5 V raises the rate by one octave. The base rate is bounded to 0.2–20 Hz.";
        }
        if (component == inputSelect) {
            return "Select gate: triggers on a rising crossing of 1 V and rearms below 0.1 V.";
        }
        if (component == outputV1 || component == outputV2) {
            return "Stepped bipolar random voltage, scaled by its corresponding level control.";
        }
        if (component == outputTrig) {
            return "Rises to +4 V for 10 ms at each random selection.";
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
    private VoltageSwitch switchComp;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelInhibit;
    private VoltageLabel labelSelect;
    private VoltageLabel labelV2;
    private VoltageLabel labelMean;
    private VoltageLabel labelSelectInput;
    private VoltageLabel labelTrigOut;
    private VoltageLabel labelV2Out;
    private VoltageLabel labelV1Out;
    private VoltageLabel labelV1;
    private VoltageLabel labelVariance;
    private VoltageLabel labelRate;
    private VoltageLabel scale1A30DeepToneGenModLabel2;
    private VoltageAudioJack inputMean;
    private VoltageAudioJack inputSelect;
    private VoltageAudioJack outputTrig;
    private VoltageAudioJack outputV2;
    private VoltageAudioJack outputV1;
    private VoltageLED lampSelection;
    private VoltageButton buttonSelect;
    private VoltageKnob knobV2;
    private VoltageKnob knobV1;
    private VoltageKnob knobVariance;
    private VoltageKnob knobRate;
    private VoltageSwitch switchInhibit;
    private VoltageLabel labelSelection;
    private VoltageLabel labelNew;
    private VoltageLabel labelRun;
    private VoltageLabel labelBlown;
    private VoltageLabel labelComp;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double MIN_RATE_HZ = 0.2;
    private static final double MAX_RATE_HZ = 20.0;
    private static final double OUTPUT_PEAK_VOLTS = 2.5;
    private static final double TRIGGER_VOLTS = 4.0;
    private static final double MAX_VARIANCE_SPREAD = 0.5 * Math.log(128.0);
    private static final int MODE_BLOWN_CIRCUIT = 0;
    private static final int MODE_FACTORY = 1;
    private static final int MODE_COMPENSATED = 2;
    private static final double FACTORY_DROOP_VOLTS_PER_SECOND =0.015;
    private static final double BLOWN_DROOP_VOLTS_PER_SECOND = 0.115;
    private static final double SELECT_GATE_HIGH = 1.0;
    private static final double SELECT_GATE_LOW = 0.1;
    private static final int TRIGGER_PULSE_SAMPLES = 480;
    private static final int SELECTION_LAMP_SAMPLES = 4800;

    private final AtomicBoolean selectionPending = new AtomicBoolean(false);
    private long randomState = System.nanoTime();
    private double heldV1;
    private double heldV2;
    private double heldRateRandom = 0.5;
    private double clockPhase;
    private int triggerSamplesRemaining;
    private int selectionLampSamplesRemaining;
    private volatile double selectionLampLevel;
    private volatile int compensationMode = MODE_COMPENSATED;
    private boolean selectArmed = true;
    private boolean hasSelection;
    private boolean resumePending;

    private double knobRateValue() {
        return knobRate.GetValue();
    }

    private void sampleAndHold() {
        heldV1 = (2.0 * nextRandomUnit() - 1.0) * OUTPUT_PEAK_VOLTS;
        heldV2 = (2.0 * nextRandomUnit() - 1.0) * OUTPUT_PEAK_VOLTS;
        heldRateRandom = nextRandomUnit();
        hasSelection = true;
    }

    private double nextRandomUnit() {
        long value = randomState;
        if (value == 0L) {
            value = 0x9E3779B97F4A7C15L;
        }
        value ^= value << 13;
        value ^= value >>> 7;
        value ^= value << 17;
        randomState = value;
        return (value >>> 11) * 0x1.0p-53;
    }

    private static int normalizeCompensationMode(double value) {
        return (int) Math.max(MODE_BLOWN_CIRCUIT,
                Math.min(MODE_COMPENSATED, Math.round(value)));
    }

    private static double compensationDroopRate(int mode) {
        switch (mode) {
            case MODE_FACTORY:
                return FACTORY_DROOP_VOLTS_PER_SECOND;
            case MODE_BLOWN_CIRCUIT:
                return BLOWN_DROOP_VOLTS_PER_SECOND;
            case MODE_COMPENSATED:
            default:
                return 0.0;
        }
    }

    private static double droopTowardZero(double value, double step) {
        if (value > 0.0) {
            return Math.max(0.0, value - step);
        }
        if (value < 0.0) {
            return Math.min(0.0, value + step);
        }
        return 0.0;
    }

    private static double finite(double value) {
        return Double.isFinite(value) ? value : 0.0;
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
    //[/user-code-and-variables]
}
