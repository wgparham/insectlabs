package com.insectlabs.svf;


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


public class svf extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public svf( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "svf - State Variable Filter", ModuleType.ModuleType_Filters, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "80dd113f6a5041109d46b7c937ecd0dc" );
    }

void InitializeControls()
{

        labelFooter = new VoltageLabel( "labelFooter", "", this, "state variable filter" );
        AddComponent( labelFooter );
        labelFooter.SetWantsMouseNotifications( false );
        labelFooter.SetPosition( 3, 335 );
        labelFooter.SetSize( 109, 23 );
        labelFooter.SetEditable( false, false );
        labelFooter.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFooter.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFooter.SetColor( new Color( 166, 36, 36, 255 ) );
        labelFooter.SetBkColor( new Color( 0, 0, 0, 0 ) );
        labelFooter.SetBorderColor( new Color( 26, 26, 26, 255 ) );
        labelFooter.SetBorderSize( 2 );
        labelFooter.SetMultiLineEdit( true );
        labelFooter.SetIsNumberEditor( false );
        labelFooter.SetNumberEditorRange( 0, 100 );
        labelFooter.SetNumberEditorInterval( 1 );
        labelFooter.SetNumberEditorUsesMouseWheel( false );
        labelFooter.SetHasCustomTextHoverColor( false );
        labelFooter.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFooter.SetFont( "Arial", 9, true, false );

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 5, 5 );
        imageTTW.SetSize( 41, 13 );
        imageTTW.SetCurrentImage( "image image.svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "svf" );
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

        knobIn1 = new VoltageKnob( "knobIn1", "In 1 Level", this, 0.0, 1.0, 0.5 );
        AddComponent( knobIn1 );
        knobIn1.SetWantsMouseNotifications( false );
        knobIn1.SetPosition( 22, 30 );
        knobIn1.SetSize( 25, 25 );
        knobIn1.SetSkin( "ARP2500 Sm Silver" );
        knobIn1.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobIn1.SetKnobParams( 215, 145 );
        knobIn1.DisplayValueInPercent( false );
        knobIn1.SetKnobAdjustsRing( true );

        knobCV1 = new VoltageKnob( "knobCV1", "CV 1", this, 0.0, 1.0, 0.0 );
        AddComponent( knobCV1 );
        knobCV1.SetWantsMouseNotifications( false );
        knobCV1.SetPosition( 22, 63 );
        knobCV1.SetSize( 25, 25 );
        knobCV1.SetSkin( "ARP2500 Sm Emerald" );
        knobCV1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobCV1.SetKnobParams( 215, 145 );
        knobCV1.DisplayValueInPercent( false );
        knobCV1.SetKnobAdjustsRing( true );

        knobFrequency1 = new VoltageKnob( "knobFrequency1", "Frequency 1", this, 0.0, 1.0, 1.0 );
        AddComponent( knobFrequency1 );
        knobFrequency1.SetWantsMouseNotifications( false );
        knobFrequency1.SetPosition( 22, 95 );
        knobFrequency1.SetSize( 25, 25 );
        knobFrequency1.SetSkin( "ARP2500 Sm Blue" );
        knobFrequency1.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knobFrequency1.SetKnobParams( 215, 145 );
        knobFrequency1.DisplayValueInPercent( false );
        knobFrequency1.SetKnobAdjustsRing( true );

        knobResponse1 = new VoltageKnob( "knobResponse1", "Response 1", this, 0.0, 1.0, 0.0 );
        AddComponent( knobResponse1 );
        knobResponse1.SetWantsMouseNotifications( false );
        knobResponse1.SetPosition( 22, 128 );
        knobResponse1.SetSize( 25, 25 );
        knobResponse1.SetSkin( "ARP2500 Sm Gold" );
        knobResponse1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobResponse1.SetKnobParams( 215, 145 );
        knobResponse1.DisplayValueInPercent( false );
        knobResponse1.SetKnobAdjustsRing( true );

        jackInput1 = new VoltageAudioJack( "jackInput1", "In 1", this, JackType.JackType_AudioInput );
        AddComponent( jackInput1 );
        jackInput1.SetWantsMouseNotifications( false );
        jackInput1.SetPosition( 22, 184 );
        jackInput1.SetSize( 25, 25 );
        jackInput1.SetSkin( "Jack Round 25px" );

        jackCVin1 = new VoltageAudioJack( "jackCVin1", "CV In 1", this, JackType.JackType_AudioInput );
        AddComponent( jackCVin1 );
        jackCVin1.SetWantsMouseNotifications( false );
        jackCVin1.SetPosition( 22, 214 );
        jackCVin1.SetSize( 25, 25 );
        jackCVin1.SetSkin( "Jack Round 25px" );

        jackHPF1 = new VoltageAudioJack( "jackHPF1", "HPF Out 1", this, JackType.JackType_AudioOutput );
        AddComponent( jackHPF1 );
        jackHPF1.SetWantsMouseNotifications( false );
        jackHPF1.SetPosition( 22, 244 );
        jackHPF1.SetSize( 25, 25 );
        jackHPF1.SetSkin( "Mini Jack 25px" );

        knobIn2 = new VoltageKnob( "knobIn2", "In 2 Level", this, 0.0, 1.0, 0.5 );
        AddComponent( knobIn2 );
        knobIn2.SetWantsMouseNotifications( false );
        knobIn2.SetPosition( 68, 30 );
        knobIn2.SetSize( 25, 25 );
        knobIn2.SetSkin( "ARP2500 Sm Silver" );
        knobIn2.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobIn2.SetKnobParams( 215, 145 );
        knobIn2.DisplayValueInPercent( false );
        knobIn2.SetKnobAdjustsRing( true );

        knobCV2 = new VoltageKnob( "knobCV2", "CV 2", this, 0.0, 1.0, 0.0 );
        AddComponent( knobCV2 );
        knobCV2.SetWantsMouseNotifications( false );
        knobCV2.SetPosition( 68, 63 );
        knobCV2.SetSize( 25, 25 );
        knobCV2.SetSkin( "ARP2500 Sm Emerald" );
        knobCV2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobCV2.SetKnobParams( 215, 145 );
        knobCV2.DisplayValueInPercent( false );
        knobCV2.SetKnobAdjustsRing( true );

        knobFrequency2 = new VoltageKnob( "knobFrequency2", "Frequency 2", this, 0.0, 1.0, 1.0 );
        AddComponent( knobFrequency2 );
        knobFrequency2.SetWantsMouseNotifications( false );
        knobFrequency2.SetPosition( 68, 95 );
        knobFrequency2.SetSize( 25, 25 );
        knobFrequency2.SetSkin( "ARP2500 Sm Blue" );
        knobFrequency2.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knobFrequency2.SetKnobParams( 215, 145 );
        knobFrequency2.DisplayValueInPercent( false );
        knobFrequency2.SetKnobAdjustsRing( true );

        knobResponse2 = new VoltageKnob( "knobResponse2", "Response 2", this, 0.0, 1.0, 0.0 );
        AddComponent( knobResponse2 );
        knobResponse2.SetWantsMouseNotifications( false );
        knobResponse2.SetPosition( 68, 128 );
        knobResponse2.SetSize( 25, 25 );
        knobResponse2.SetSkin( "ARP2500 Sm Gold" );
        knobResponse2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobResponse2.SetKnobParams( 215, 145 );
        knobResponse2.DisplayValueInPercent( false );
        knobResponse2.SetKnobAdjustsRing( true );

        jackInput2 = new VoltageAudioJack( "jackInput2", "In 2", this, JackType.JackType_AudioInput );
        AddComponent( jackInput2 );
        jackInput2.SetWantsMouseNotifications( false );
        jackInput2.SetPosition( 68, 184 );
        jackInput2.SetSize( 25, 25 );
        jackInput2.SetSkin( "Jack Round 25px" );

        jackCVin2 = new VoltageAudioJack( "jackCVin2", "CV In 2", this, JackType.JackType_AudioInput );
        AddComponent( jackCVin2 );
        jackCVin2.SetWantsMouseNotifications( false );
        jackCVin2.SetPosition( 68, 214 );
        jackCVin2.SetSize( 25, 25 );
        jackCVin2.SetSkin( "Jack Round 25px" );

        jackHPF2 = new VoltageAudioJack( "jackHPF2", "HPF Out 2", this, JackType.JackType_AudioOutput );
        AddComponent( jackHPF2 );
        jackHPF2.SetWantsMouseNotifications( false );
        jackHPF2.SetPosition( 68, 244 );
        jackHPF2.SetSize( 25, 25 );
        jackHPF2.SetSkin( "Mini Jack 25px" );

        switchMode = new VoltageSwitch( "switchMode", "Mode Switch", this, 1 );
        AddComponent( switchMode );
        switchMode.SetWantsMouseNotifications( false );
        switchMode.SetPosition( 40, 155 );
        switchMode.SetSize( 35, 35 );
        switchMode.SetSkin( "3-State Silver" );

        labelInKnob1 = new VoltageLabel( "labelInKnob1", "Label In Knob 1", this, "IN 1" );
        AddComponent( labelInKnob1 );
        labelInKnob1.SetWantsMouseNotifications( false );
        labelInKnob1.SetPosition( 15, 52 );
        labelInKnob1.SetSize( 40, 15 );
        labelInKnob1.SetEditable( false, false );
        labelInKnob1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelInKnob1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelInKnob1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelInKnob1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelInKnob1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelInKnob1.SetBorderSize( 1 );
        labelInKnob1.SetMultiLineEdit( false );
        labelInKnob1.SetIsNumberEditor( false );
        labelInKnob1.SetNumberEditorRange( 0, 100 );
        labelInKnob1.SetNumberEditorInterval( 1 );
        labelInKnob1.SetNumberEditorUsesMouseWheel( false );
        labelInKnob1.SetHasCustomTextHoverColor( false );
        labelInKnob1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelInKnob1.SetFont( "Arial", 6, true, false );

        labelInKnob2 = new VoltageLabel( "labelInKnob2", "Label In Knob 2", this, "IN 2" );
        AddComponent( labelInKnob2 );
        labelInKnob2.SetWantsMouseNotifications( false );
        labelInKnob2.SetPosition( 60, 52 );
        labelInKnob2.SetSize( 40, 15 );
        labelInKnob2.SetEditable( false, false );
        labelInKnob2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelInKnob2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelInKnob2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelInKnob2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelInKnob2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelInKnob2.SetBorderSize( 1 );
        labelInKnob2.SetMultiLineEdit( false );
        labelInKnob2.SetIsNumberEditor( false );
        labelInKnob2.SetNumberEditorRange( 0, 100 );
        labelInKnob2.SetNumberEditorInterval( 1 );
        labelInKnob2.SetNumberEditorUsesMouseWheel( false );
        labelInKnob2.SetHasCustomTextHoverColor( false );
        labelInKnob2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelInKnob2.SetFont( "Arial", 6, true, false );

        labelCV1 = new VoltageLabel( "labelCV1", "Label CV 1", this, "CV 1" );
        AddComponent( labelCV1 );
        labelCV1.SetWantsMouseNotifications( false );
        labelCV1.SetPosition( 16, 85 );
        labelCV1.SetSize( 40, 15 );
        labelCV1.SetEditable( false, false );
        labelCV1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCV1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCV1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelCV1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCV1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCV1.SetBorderSize( 1 );
        labelCV1.SetMultiLineEdit( false );
        labelCV1.SetIsNumberEditor( false );
        labelCV1.SetNumberEditorRange( 0, 100 );
        labelCV1.SetNumberEditorInterval( 1 );
        labelCV1.SetNumberEditorUsesMouseWheel( false );
        labelCV1.SetHasCustomTextHoverColor( false );
        labelCV1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCV1.SetFont( "Arial", 6, true, false );

        labelCV2 = new VoltageLabel( "labelCV2", "Label CV 2", this, "CV 2" );
        AddComponent( labelCV2 );
        labelCV2.SetWantsMouseNotifications( false );
        labelCV2.SetPosition( 59, 85 );
        labelCV2.SetSize( 40, 15 );
        labelCV2.SetEditable( false, false );
        labelCV2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCV2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCV2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelCV2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCV2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCV2.SetBorderSize( 1 );
        labelCV2.SetMultiLineEdit( false );
        labelCV2.SetIsNumberEditor( false );
        labelCV2.SetNumberEditorRange( 0, 100 );
        labelCV2.SetNumberEditorInterval( 1 );
        labelCV2.SetNumberEditorUsesMouseWheel( false );
        labelCV2.SetHasCustomTextHoverColor( false );
        labelCV2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCV2.SetFont( "Arial", 6, true, false );

        labelFreq1 = new VoltageLabel( "labelFreq1", "Label Frequency 1", this, "FREQ 1" );
        AddComponent( labelFreq1 );
        labelFreq1.SetWantsMouseNotifications( false );
        labelFreq1.SetPosition( 15, 117 );
        labelFreq1.SetSize( 40, 15 );
        labelFreq1.SetEditable( false, false );
        labelFreq1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFreq1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFreq1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelFreq1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFreq1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFreq1.SetBorderSize( 1 );
        labelFreq1.SetMultiLineEdit( false );
        labelFreq1.SetIsNumberEditor( false );
        labelFreq1.SetNumberEditorRange( 0, 100 );
        labelFreq1.SetNumberEditorInterval( 1 );
        labelFreq1.SetNumberEditorUsesMouseWheel( false );
        labelFreq1.SetHasCustomTextHoverColor( false );
        labelFreq1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFreq1.SetFont( "Arial", 6, true, false );

        labelFreq2 = new VoltageLabel( "labelFreq2", "Label Frequency 2", this, "FREQ 2" );
        AddComponent( labelFreq2 );
        labelFreq2.SetWantsMouseNotifications( false );
        labelFreq2.SetPosition( 61, 117 );
        labelFreq2.SetSize( 40, 15 );
        labelFreq2.SetEditable( false, false );
        labelFreq2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFreq2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFreq2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelFreq2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFreq2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFreq2.SetBorderSize( 1 );
        labelFreq2.SetMultiLineEdit( false );
        labelFreq2.SetIsNumberEditor( false );
        labelFreq2.SetNumberEditorRange( 0, 100 );
        labelFreq2.SetNumberEditorInterval( 1 );
        labelFreq2.SetNumberEditorUsesMouseWheel( false );
        labelFreq2.SetHasCustomTextHoverColor( false );
        labelFreq2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFreq2.SetFont( "Arial", 6, true, false );

        labelResp1 = new VoltageLabel( "labelResp1", "Label Response 1", this, "RESP 1" );
        AddComponent( labelResp1 );
        labelResp1.SetWantsMouseNotifications( false );
        labelResp1.SetPosition( 15, 151 );
        labelResp1.SetSize( 40, 15 );
        labelResp1.SetEditable( false, false );
        labelResp1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelResp1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelResp1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelResp1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelResp1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelResp1.SetBorderSize( 1 );
        labelResp1.SetMultiLineEdit( false );
        labelResp1.SetIsNumberEditor( false );
        labelResp1.SetNumberEditorRange( 0, 100 );
        labelResp1.SetNumberEditorInterval( 1 );
        labelResp1.SetNumberEditorUsesMouseWheel( false );
        labelResp1.SetHasCustomTextHoverColor( false );
        labelResp1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelResp1.SetFont( "Arial", 6, true, false );

        labelResp2 = new VoltageLabel( "labelResp2", "Label Response 2", this, "RESP 2" );
        AddComponent( labelResp2 );
        labelResp2.SetWantsMouseNotifications( false );
        labelResp2.SetPosition( 60, 151 );
        labelResp2.SetSize( 40, 15 );
        labelResp2.SetEditable( false, false );
        labelResp2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelResp2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelResp2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelResp2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelResp2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelResp2.SetBorderSize( 1 );
        labelResp2.SetMultiLineEdit( false );
        labelResp2.SetIsNumberEditor( false );
        labelResp2.SetNumberEditorRange( 0, 100 );
        labelResp2.SetNumberEditorInterval( 1 );
        labelResp2.SetNumberEditorUsesMouseWheel( false );
        labelResp2.SetHasCustomTextHoverColor( false );
        labelResp2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelResp2.SetFont( "Arial", 6, true, false );

        jackBPF1 = new VoltageAudioJack( "jackBPF1", "Band Pass Out 1", this, JackType.JackType_AudioOutput );
        AddComponent( jackBPF1 );
        jackBPF1.SetWantsMouseNotifications( false );
        jackBPF1.SetPosition( 21, 273 );
        jackBPF1.SetSize( 25, 25 );
        jackBPF1.SetSkin( "Mini Jack 25px" );

        jackBPF2 = new VoltageAudioJack( "jackBPF2", "Band Pass Out 2", this, JackType.JackType_AudioOutput );
        AddComponent( jackBPF2 );
        jackBPF2.SetWantsMouseNotifications( false );
        jackBPF2.SetPosition( 67, 273 );
        jackBPF2.SetSize( 25, 25 );
        jackBPF2.SetSkin( "Mini Jack 25px" );

        jackLPF1 = new VoltageAudioJack( "jackLPF1", "Low Pass Out 1", this, JackType.JackType_AudioOutput );
        AddComponent( jackLPF1 );
        jackLPF1.SetWantsMouseNotifications( false );
        jackLPF1.SetPosition( 21, 302 );
        jackLPF1.SetSize( 25, 25 );
        jackLPF1.SetSkin( "Mini Jack 25px" );

        jackLPF2 = new VoltageAudioJack( "jackLPF2", "Low Pass Out 2", this, JackType.JackType_AudioOutput );
        AddComponent( jackLPF2 );
        jackLPF2.SetWantsMouseNotifications( false );
        jackLPF2.SetPosition( 67, 302 );
        jackLPF2.SetSize( 25, 25 );
        jackLPF2.SetSkin( "Mini Jack 25px" );

        labelIn1 = new VoltageLabel( "labelIn1", "Label In 1", this, "IN 1" );
        AddComponent( labelIn1 );
        labelIn1.SetWantsMouseNotifications( false );
        labelIn1.SetPosition( 15, 205 );
        labelIn1.SetSize( 40, 15 );
        labelIn1.SetEditable( false, false );
        labelIn1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelIn1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelIn1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelIn1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelIn1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelIn1.SetBorderSize( 1 );
        labelIn1.SetMultiLineEdit( false );
        labelIn1.SetIsNumberEditor( false );
        labelIn1.SetNumberEditorRange( 0, 100 );
        labelIn1.SetNumberEditorInterval( 1 );
        labelIn1.SetNumberEditorUsesMouseWheel( false );
        labelIn1.SetHasCustomTextHoverColor( false );
        labelIn1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelIn1.SetFont( "Arial", 6, true, false );

        labelIn2 = new VoltageLabel( "labelIn2", "Label In 2", this, "IN 2" );
        AddComponent( labelIn2 );
        labelIn2.SetWantsMouseNotifications( false );
        labelIn2.SetPosition( 60, 205 );
        labelIn2.SetSize( 40, 15 );
        labelIn2.SetEditable( false, false );
        labelIn2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelIn2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelIn2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelIn2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelIn2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelIn2.SetBorderSize( 1 );
        labelIn2.SetMultiLineEdit( false );
        labelIn2.SetIsNumberEditor( false );
        labelIn2.SetNumberEditorRange( 0, 100 );
        labelIn2.SetNumberEditorInterval( 1 );
        labelIn2.SetNumberEditorUsesMouseWheel( false );
        labelIn2.SetHasCustomTextHoverColor( false );
        labelIn2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelIn2.SetFont( "Arial", 6, true, false );

        labelCVin2 = new VoltageLabel( "labelCVin2", "Label CV In 2", this, "CV 2" );
        AddComponent( labelCVin2 );
        labelCVin2.SetWantsMouseNotifications( false );
        labelCVin2.SetPosition( 59, 235 );
        labelCVin2.SetSize( 40, 15 );
        labelCVin2.SetEditable( false, false );
        labelCVin2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCVin2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCVin2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelCVin2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCVin2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCVin2.SetBorderSize( 1 );
        labelCVin2.SetMultiLineEdit( false );
        labelCVin2.SetIsNumberEditor( false );
        labelCVin2.SetNumberEditorRange( 0, 100 );
        labelCVin2.SetNumberEditorInterval( 1 );
        labelCVin2.SetNumberEditorUsesMouseWheel( false );
        labelCVin2.SetHasCustomTextHoverColor( false );
        labelCVin2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCVin2.SetFont( "Arial", 6, true, false );

        labelCVin1 = new VoltageLabel( "labelCVin1", "Label CV In 1", this, "CV 1" );
        AddComponent( labelCVin1 );
        labelCVin1.SetWantsMouseNotifications( false );
        labelCVin1.SetPosition( 14, 235 );
        labelCVin1.SetSize( 40, 15 );
        labelCVin1.SetEditable( false, false );
        labelCVin1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCVin1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCVin1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelCVin1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCVin1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCVin1.SetBorderSize( 1 );
        labelCVin1.SetMultiLineEdit( false );
        labelCVin1.SetIsNumberEditor( false );
        labelCVin1.SetNumberEditorRange( 0, 100 );
        labelCVin1.SetNumberEditorInterval( 1 );
        labelCVin1.SetNumberEditorUsesMouseWheel( false );
        labelCVin1.SetHasCustomTextHoverColor( false );
        labelCVin1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCVin1.SetFont( "Arial", 6, true, false );

        labelHP2 = new VoltageLabel( "labelHP2", "High Pass 2", this, "HIGH" );
        AddComponent( labelHP2 );
        labelHP2.SetWantsMouseNotifications( false );
        labelHP2.SetPosition( 59, 264 );
        labelHP2.SetSize( 40, 15 );
        labelHP2.SetEditable( false, false );
        labelHP2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelHP2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelHP2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelHP2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelHP2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelHP2.SetBorderSize( 1 );
        labelHP2.SetMultiLineEdit( false );
        labelHP2.SetIsNumberEditor( false );
        labelHP2.SetNumberEditorRange( 0, 100 );
        labelHP2.SetNumberEditorInterval( 1 );
        labelHP2.SetNumberEditorUsesMouseWheel( false );
        labelHP2.SetHasCustomTextHoverColor( false );
        labelHP2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelHP2.SetFont( "Arial", 6, true, false );

        labelHP1 = new VoltageLabel( "labelHP1", "High Pass 1", this, "HIGH" );
        AddComponent( labelHP1 );
        labelHP1.SetWantsMouseNotifications( false );
        labelHP1.SetPosition( 16, 264 );
        labelHP1.SetSize( 40, 15 );
        labelHP1.SetEditable( false, false );
        labelHP1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelHP1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelHP1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelHP1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelHP1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelHP1.SetBorderSize( 1 );
        labelHP1.SetMultiLineEdit( false );
        labelHP1.SetIsNumberEditor( false );
        labelHP1.SetNumberEditorRange( 0, 100 );
        labelHP1.SetNumberEditorInterval( 1 );
        labelHP1.SetNumberEditorUsesMouseWheel( false );
        labelHP1.SetHasCustomTextHoverColor( false );
        labelHP1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelHP1.SetFont( "Arial", 6, true, false );

        labelBPF1 = new VoltageLabel( "labelBPF1", "Band Pass 1", this, "BAND" );
        AddComponent( labelBPF1 );
        labelBPF1.SetWantsMouseNotifications( false );
        labelBPF1.SetPosition( 14, 293 );
        labelBPF1.SetSize( 40, 15 );
        labelBPF1.SetEditable( false, false );
        labelBPF1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBPF1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBPF1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelBPF1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBPF1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBPF1.SetBorderSize( 1 );
        labelBPF1.SetMultiLineEdit( false );
        labelBPF1.SetIsNumberEditor( false );
        labelBPF1.SetNumberEditorRange( 0, 100 );
        labelBPF1.SetNumberEditorInterval( 1 );
        labelBPF1.SetNumberEditorUsesMouseWheel( false );
        labelBPF1.SetHasCustomTextHoverColor( false );
        labelBPF1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBPF1.SetFont( "Arial", 6, true, false );

        labelBPF2 = new VoltageLabel( "labelBPF2", "Band Pass 2", this, "BAND" );
        AddComponent( labelBPF2 );
        labelBPF2.SetWantsMouseNotifications( false );
        labelBPF2.SetPosition( 59, 293 );
        labelBPF2.SetSize( 40, 15 );
        labelBPF2.SetEditable( false, false );
        labelBPF2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBPF2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBPF2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelBPF2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBPF2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBPF2.SetBorderSize( 1 );
        labelBPF2.SetMultiLineEdit( false );
        labelBPF2.SetIsNumberEditor( false );
        labelBPF2.SetNumberEditorRange( 0, 100 );
        labelBPF2.SetNumberEditorInterval( 1 );
        labelBPF2.SetNumberEditorUsesMouseWheel( false );
        labelBPF2.SetHasCustomTextHoverColor( false );
        labelBPF2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBPF2.SetFont( "Arial", 6, true, false );

        labelLPF2 = new VoltageLabel( "labelLPF2", "Low Pass 2", this, "LOW" );
        AddComponent( labelLPF2 );
        labelLPF2.SetWantsMouseNotifications( false );
        labelLPF2.SetPosition( 59, 322 );
        labelLPF2.SetSize( 40, 15 );
        labelLPF2.SetEditable( false, false );
        labelLPF2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLPF2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLPF2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelLPF2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLPF2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLPF2.SetBorderSize( 1 );
        labelLPF2.SetMultiLineEdit( false );
        labelLPF2.SetIsNumberEditor( false );
        labelLPF2.SetNumberEditorRange( 0, 100 );
        labelLPF2.SetNumberEditorInterval( 1 );
        labelLPF2.SetNumberEditorUsesMouseWheel( false );
        labelLPF2.SetHasCustomTextHoverColor( false );
        labelLPF2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLPF2.SetFont( "Arial", 6, true, false );

        labelLPF1 = new VoltageLabel( "labelLPF1", "Low Pass 1", this, "LOW" );
        AddComponent( labelLPF1 );
        labelLPF1.SetWantsMouseNotifications( false );
        labelLPF1.SetPosition( 14, 322 );
        labelLPF1.SetSize( 40, 15 );
        labelLPF1.SetEditable( false, false );
        labelLPF1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLPF1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLPF1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelLPF1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLPF1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLPF1.SetBorderSize( 1 );
        labelLPF1.SetMultiLineEdit( false );
        labelLPF1.SetIsNumberEditor( false );
        labelLPF1.SetNumberEditorRange( 0, 100 );
        labelLPF1.SetNumberEditorInterval( 1 );
        labelLPF1.SetNumberEditorUsesMouseWheel( false );
        labelLPF1.SetHasCustomTextHoverColor( false );
        labelLPF1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLPF1.SetFont( "Arial", 6, true, false );

        labelSeries = new VoltageLabel( "labelSeries", "Series Label", this, "S" );
        AddComponent( labelSeries );
        labelSeries.SetWantsMouseNotifications( false );
        labelSeries.SetPosition( 47, 148 );
        labelSeries.SetSize( 21, 21 );
        labelSeries.SetEditable( false, false );
        labelSeries.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSeries.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSeries.SetColor( new Color( 0, 0, 0, 255 ) );
        labelSeries.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSeries.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSeries.SetBorderSize( 1 );
        labelSeries.SetMultiLineEdit( false );
        labelSeries.SetIsNumberEditor( false );
        labelSeries.SetNumberEditorRange( 0, 100 );
        labelSeries.SetNumberEditorInterval( 1 );
        labelSeries.SetNumberEditorUsesMouseWheel( false );
        labelSeries.SetHasCustomTextHoverColor( false );
        labelSeries.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSeries.SetFont( "Arial Black", 11, false, false );

        labelOff = new VoltageLabel( "labelOff", "Off Label", this, "O" );
        AddComponent( labelOff );
        labelOff.SetWantsMouseNotifications( false );
        labelOff.SetPosition( 61, 163 );
        labelOff.SetSize( 21, 21 );
        labelOff.SetEditable( false, false );
        labelOff.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOff.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOff.SetColor( new Color( 0, 0, 0, 255 ) );
        labelOff.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOff.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOff.SetBorderSize( 1 );
        labelOff.SetMultiLineEdit( false );
        labelOff.SetIsNumberEditor( false );
        labelOff.SetNumberEditorRange( 0, 100 );
        labelOff.SetNumberEditorInterval( 1 );
        labelOff.SetNumberEditorUsesMouseWheel( false );
        labelOff.SetHasCustomTextHoverColor( false );
        labelOff.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOff.SetFont( "Arial Black", 11, false, false );

        labelParallel = new VoltageLabel( "labelParallel", "Parallel Label", this, "P" );
        AddComponent( labelParallel );
        labelParallel.SetWantsMouseNotifications( false );
        labelParallel.SetPosition( 47, 176 );
        labelParallel.SetSize( 21, 21 );
        labelParallel.SetEditable( false, false );
        labelParallel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelParallel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelParallel.SetColor( new Color( 0, 0, 0, 255 ) );
        labelParallel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelParallel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelParallel.SetBorderSize( 1 );
        labelParallel.SetMultiLineEdit( false );
        labelParallel.SetIsNumberEditor( false );
        labelParallel.SetNumberEditorRange( 0, 100 );
        labelParallel.SetNumberEditorInterval( 1 );
        labelParallel.SetNumberEditorUsesMouseWheel( false );
        labelParallel.SetHasCustomTextHoverColor( false );
        labelParallel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelParallel.SetFont( "Arial Black", 11, false, false );

        switchRoute = new VoltageSwitch( "switchRoute", "Route Switch", this, 2 );
        AddComponent( switchRoute );
        switchRoute.SetWantsMouseNotifications( false );
        switchRoute.SetPosition( 52, 124 );
        switchRoute.SetSize( 11, 30 );
        switchRoute.SetSkin( "3-State Slide Black" );
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

        knobs[0] = new VoltageKnob[] {knobIn1, knobCV1, knobFrequency1, knobResponse1};
        knobs[1] = new VoltageKnob[] {knobIn2, knobCV2, knobFrequency2, knobResponse2};
        inputs[0] = jackInput1; inputs[1] = jackInput2;
        cvInputs[0] = jackCVin1; cvInputs[1] = jackCVin2;
        outputs[0] = new VoltageAudioJack[] {jackHPF1, jackBPF1, jackLPF1};
        outputs[1] = new VoltageAudioJack[] {jackHPF2, jackBPF2, jackLPF2};
        resetDsp();

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
            case Reset:
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                resetPending.set(true);
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

        if (resetPending.getAndSet(false) || resumePending) {
            resetDsp();
        }
        activeMode = 2 - switchPosition(switchMode.GetValue(), 1);
        activeRoute = 2 - switchPosition(switchRoute.GetValue(), 2);
        boolean secondPatched = inputs[1].IsConnected();
        for (int channel = 0; channel < 2; channel++) {
            for (int control = 0; control < 4; control++) {
                double target = unit(knobs[channel][control].GetValue());
                controls[channel][control] += CONTROL_SMOOTH * (target - controls[channel][control]);
            }
            cores[channel].configure(frequencyFor(controls[channel][2],
                    readSignal(cvInputs[channel]), controls[channel][1]), controls[channel][3]);
            double input = readSignal(inputs[channel]);
            phaseInputs[channel][0] = upsamplers[channel].process(2.0 * input);
            phaseInputs[channel][1] = upsamplers[channel].process(0.0);
        }
        for (int phase = 0; phase < 2; phase++) {
            cores[0].process(phaseInputs[0][phase] * 2.0 * controls[0][0]);
            double secondInput = phaseInputs[1][phase];
            if (!secondPatched) {
                if (activeMode == 0) {
                    secondInput = cores[0].taps[activeRoute];
                } else if (activeMode == 2) {
                    secondInput = phaseInputs[0][phase];
                }
            }
            cores[1].process(secondInput * 2.0 * controls[1][0]);
            for (int channel = 0; channel < 2; channel++) {
                for (int tap = 0; tap < 3; tap++) {
                    double filtered = downsamplers[channel][tap].process(cores[channel].taps[tap]);
                    if (phase == 0) {
                        nativeOutputs[channel][tap] = filtered * RESAMPLER_GAIN;
                    }
                }
            }
        }
        for (int channel = 0; channel < 2; channel++) {
            for (int tap = 0; tap < 3; tap++) {
                outputs[channel][tap].SetValue(nativeOutputs[channel][tap]);
            }
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

        double first = readSignal(inputs[0]);
        double second = inputs[1].IsConnected() ? readSignal(inputs[1])
                : (activeMode == 1 ? 0.0 : first);
        for (int tap = 0; tap < 3; tap++) {
            outputs[0][tap].SetValue(first);
            outputs[1][tap].SetValue(second);
        }
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

        for (int channel = 0; channel < 2; channel++) {
            int number = channel + 1;
            if (component == knobs[channel][0]) {
                return String.format(java.util.Locale.ROOT,
                        "Core %d input gain: %.1f%% (50%% = unity); enter 0-100 percent", number,
                        100.0 * unit(knobs[channel][0].GetValue()));
            }
            if (component == knobs[channel][1]) {
                return String.format(java.util.Locale.ROOT,
                        "Core %d cutoff CV amount: %.1f%%; full = 0.5 V/octave; enter percent", number,
                        100.0 * unit(knobs[channel][1].GetValue()));
            }
            if (component == knobs[channel][2]) {
                return String.format(java.util.Locale.ROOT,
                        "Core %d manual cutoff: %.2f Hz; enter Hz (5-15000); CV can reach 20000 Hz", number,
                        frequencyFor(knobs[channel][2].GetValue(), 0.0, 0.0));
            }
            if (component == knobs[channel][3]) {
                return String.format(java.util.Locale.ROOT,
                        "Core %d response: %.1f%%; resonance rises toward oscillation above about 89%%; enter percent",
                        number, 100.0 * unit(knobs[channel][3].GetValue()));
            }
            if (component == inputs[channel]) {
                return channel == 0 ? "Core 1 audio input; unpatched = silence"
                        : "Core 2 audio input; patch overrides series/parallel normalization";
            }
            if (component == cvInputs[channel]) {
                return "Cutoff CV: positive raises frequency; 0.5 V/octave at full CV amount; unpatched = 0 V";
            }
            for (int tap = 0; tap < 3; tap++) {
                if (component == outputs[channel][tap]) {
                    return "Core " + number + " " + (tap == 0 ? "HIGH" : tap == 1 ? "BAND" : "LOW")
                            + " output; simultaneous circuit tap, shared cutoff/response; nominal 9-sample FIR delay";
                }
            }
        }
        if (component == switchMode) {
            return "Routing: up SERIES, middle independent, down PARALLEL; IN 2 patch overrides normalization";
        }
        if (component == switchRoute) {
            return "SERIES source from core 1: up HIGH, middle BAND, down LOW; other modes ignore this switch";
        }
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

        for (int channel = 0; channel < 2; channel++) {
            for (int control = 0; control < 4; control++) {
                if (component == knobs[channel][control]) {
                    if (Double.isFinite(newValue)) {
                        double position = control == 2
                                ? Math.log(Math.max(MIN_HZ, Math.min(MANUAL_MAX_HZ, newValue)) / MIN_HZ)
                                    / FREQUENCY_SPAN
                                : unit(newValue / 100.0);
                        super.EditComponentValue(component, position, newText);
                    }
                    return;
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
        resetPending.set(true);

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
    private VoltageSwitch switchRoute;
    private VoltageLabel labelParallel;
    private VoltageLabel labelOff;
    private VoltageLabel labelSeries;
    private VoltageLabel labelLPF1;
    private VoltageLabel labelLPF2;
    private VoltageLabel labelBPF2;
    private VoltageLabel labelBPF1;
    private VoltageLabel labelHP1;
    private VoltageLabel labelHP2;
    private VoltageLabel labelCVin1;
    private VoltageLabel labelCVin2;
    private VoltageLabel labelIn2;
    private VoltageLabel labelIn1;
    private VoltageAudioJack jackLPF2;
    private VoltageAudioJack jackLPF1;
    private VoltageAudioJack jackBPF2;
    private VoltageAudioJack jackBPF1;
    private VoltageLabel labelResp2;
    private VoltageLabel labelResp1;
    private VoltageLabel labelFreq2;
    private VoltageLabel labelFreq1;
    private VoltageLabel labelCV2;
    private VoltageLabel labelCV1;
    private VoltageLabel labelInKnob2;
    private VoltageLabel labelInKnob1;
    private VoltageSwitch switchMode;
    private VoltageAudioJack jackHPF2;
    private VoltageAudioJack jackCVin2;
    private VoltageAudioJack jackInput2;
    private VoltageKnob knobResponse2;
    private VoltageKnob knobFrequency2;
    private VoltageKnob knobCV2;
    private VoltageKnob knobIn2;
    private VoltageAudioJack jackHPF1;
    private VoltageAudioJack jackCVin1;
    private VoltageAudioJack jackInput1;
    private VoltageKnob knobResponse1;
    private VoltageKnob knobFrequency1;
    private VoltageKnob knobCV1;
    private VoltageKnob knobIn1;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;


    //[user-code-and-variables]    Add your own variables and functions here

    private static final double SAMPLE_RATE = 48000.0;
    private static final double INTERNAL_RATE = SAMPLE_RATE * 2.0;
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE));
    private static final double MIN_HZ = 5.0;
    private static final double MANUAL_MAX_HZ = 15000.0;
    private static final double MAX_HZ = 20000.0;
    private static final double FREQUENCY_SPAN = Math.log(MANUAL_MAX_HZ / MIN_HZ);
    private static final double INPUT_SUM_GAIN = 27000.0 / 100000.0;
    private static final double OUTPUT_GAIN = 1.0 + 10000.0 / 3300.0;
    // R17/R16 positive feedback estimate; exact FET calibration is not known.
    private static final double POSITIVE_FEEDBACK = (2.0 + INPUT_SUM_GAIN) / 101.0;
    private static final double MAX_DAMPING = 27000.0 / 15000.0;
    private static final double OTA_SCALE = 6.4;
    // Chosen level-dependent loop loss, not a measured transistor model.
    private static final double LOOP_LOSS = 0.04;
    private static final double OUTPUT_HEADROOM = 10.0;
    private static final double SIGNAL_LIMIT = 10000.0;
    private static final double RESAMPLER_GAIN = resamplerGain();
    private final java.util.concurrent.atomic.AtomicBoolean resetPending =
            new java.util.concurrent.atomic.AtomicBoolean(false);
    private final SvfCore[] cores = {new SvfCore(), new SvfCore()};
    private final HalfBand19[] upsamplers = {new HalfBand19(), new HalfBand19()};
    private final HalfBand19[][] downsamplers = {
            {new HalfBand19(), new HalfBand19(), new HalfBand19()},
            {new HalfBand19(), new HalfBand19(), new HalfBand19()}};
    private final VoltageKnob[][] knobs = new VoltageKnob[2][4];
    private final VoltageAudioJack[] inputs = new VoltageAudioJack[2];
    private final VoltageAudioJack[] cvInputs = new VoltageAudioJack[2];
    private final VoltageAudioJack[][] outputs = new VoltageAudioJack[2][3];
    private final double[][] controls = new double[2][4];
    private final double[][] phaseInputs = new double[2][2];
    private final double[][] nativeOutputs = new double[2][3];
    private int activeMode = 1;
    private int activeRoute;
    private boolean resumePending;

    private static double unit(double value) {
        return Double.isFinite(value) ? Math.max(0.0, Math.min(1.0, value)) : 0.0;
    }

    private static double safeSignal(double value) {
        return Double.isFinite(value)
                ? Math.max(-SIGNAL_LIMIT, Math.min(SIGNAL_LIMIT, value)) : 0.0;
    }

    private static double readSignal(VoltageAudioJack jack) {
        return jack.IsConnected() ? safeSignal(jack.GetValue()) : 0.0;
    }

    private static int switchPosition(double value, int fallback) {
        return Double.isFinite(value) ? (int) Math.max(0, Math.min(2, Math.round(value))) : fallback;
    }

    private static double frequencyFor(double position, double cv, double depth) {
        double exponent = FREQUENCY_SPAN * unit(position)
                + Math.log(2.0) * 2.0 * safeSignal(cv) * unit(depth);
        exponent = Math.max(0.0, Math.min(Math.log(MAX_HZ / MIN_HZ), exponent));
        return MIN_HZ * Math.exp(exponent);
    }

    private static double dampingFor(double response) {
        double remaining = 1.0 - unit(response);
        return MAX_DAMPING * remaining * remaining - POSITIVE_FEEDBACK;
    }

    private static double resamplerGain() {
        double sum = 2.0 * (0.021277660466073 - 0.027992303277934
                + 0.049537687283950 - 0.095789590456413 + 0.308510413777763)
                + 0.488912264413122;
        double image = sum - 2.0 * 0.488912264413122;
        return 1.0 / (sum * sum + image * image);
    }

    private static double outputStage(double value) {
        double scaled = OUTPUT_GAIN * value;
        double ratio = scaled / OUTPUT_HEADROOM;
        double squared = ratio * ratio;
        return scaled / Math.sqrt(Math.sqrt(1.0 + squared * squared));
    }

    private void resetDsp() {
        for (int channel = 0; channel < 2; channel++) {
            cores[channel].reset();
            upsamplers[channel].reset();
            for (int tap = 0; tap < 3; tap++) {
                downsamplers[channel][tap].reset();
                nativeOutputs[channel][tap] = 0.0;
            }
            for (int control = 0; control < 4; control++) {
                controls[channel][control] = unit(knobs[channel][control].GetValue());
            }
        }
        activeMode = 2 - switchPosition(switchMode.GetValue(), 1);
        activeRoute = 2 - switchPosition(switchRoute.GetValue(), 2);
        resumePending = false;
    }

    /** Two OTA integrators, solved together by implicit midpoint iteration. */
    private static final class SvfCore {
        private double bandState;
        private double lowState;
        private double g;
        private double damping;
        private double cachedFrequency = -1.0;
        private boolean oscillationSeeded;
        private final double[] taps = new double[3];

        private void reset() {
            bandState = lowState = 0.0;
            oscillationSeeded = false;
            java.util.Arrays.fill(taps, 0.0);
        }

        private void configure(double frequency, double response) {
            if (frequency != cachedFrequency) {
                g = Math.tan(Math.PI * frequency / INTERNAL_RATE);
                cachedFrequency = frequency;
            }
            damping = dampingFor(response);
            if (damping < 0.0 && !oscillationSeeded) {
                // A one-time microscopic perturbation substitutes for hardware noise.
                if (Math.abs(bandState) + Math.abs(lowState) < 1.0e-8) {
                    bandState = 1.0e-6;
                }
                oscillationSeeded = true;
            } else if (damping >= 0.0) {
                oscillationSeeded = false;
            }
        }

        private void process(double input) {
            if (!Double.isFinite(bandState) || !Double.isFinite(lowState)
                    || Math.abs(bandState) > 64.0 || Math.abs(lowState) > 64.0) {
                reset();
            }
            double drive = -INPUT_SUM_GAIN * safeSignal(input);
            double midpointBand = (bandState + g * (drive - lowState))
                    / (1.0 + g * damping + g * g);
            midpointBand = Math.max(-64.0, Math.min(64.0, midpointBand));
            double midpointLow = lowState;
            double high = 0.0;
            for (int iteration = 0; iteration < 10; iteration++) {
                double bandTanh = Math.tanh(midpointBand / OTA_SCALE);
                double bandDerivative = 1.0 - bandTanh * bandTanh;
                midpointLow = lowState + g * OTA_SCALE * bandTanh;
                high = drive - midpointLow - damping * midpointBand
                        - LOOP_LOSS * midpointBand * midpointBand * midpointBand;
                double highTanh = Math.tanh(high / OTA_SCALE);
                double residual = midpointBand - bandState - g * OTA_SCALE * highTanh;
                double derivative = 1.0 + g * (1.0 - highTanh * highTanh)
                        * (g * bandDerivative + damping + 3.0 * LOOP_LOSS * midpointBand * midpointBand);
                double correction = residual / derivative;
                midpointBand -= correction;
                if (Math.abs(correction) < 1.0e-11) {
                    break;
                }
            }
            midpointLow = lowState + g * OTA_SCALE * Math.tanh(midpointBand / OTA_SCALE);
            high = drive - midpointLow - damping * midpointBand
                    - LOOP_LOSS * midpointBand * midpointBand * midpointBand;
            bandState = 2.0 * midpointBand - bandState;
            lowState = 2.0 * midpointLow - lowState;
            if (!Double.isFinite(bandState) || !Double.isFinite(lowState)
                    || Math.abs(bandState) > 64.0 || Math.abs(lowState) > 64.0) {
                reset();
                return;
            }
            taps[0] = outputStage(high);
            taps[1] = outputStage(midpointBand);
            taps[2] = outputStage(midpointLow);
        }
    }

/** Established Colorbox 19-tap half-band FIR; coefficients/arithmetic preserved. */
private static final class HalfBand19
{
    private static final double C0 =
         0.021277660466073;

    private static final double C2 =
        -0.027992303277934;

    private static final double C4 =
         0.049537687283950;

    private static final double C6 =
        -0.095789590456413;

    private static final double C8 =
         0.308510413777763;

    private static final double C9 =
         0.488912264413122;


    // A doubled ring buffer avoids array shifting and modulo.

    private final double[] buffer =
        new double[38];

    private int position = 0;
    private boolean dirty = false;


    public double process( double input )
    {
        position--;

        if( position < 0 )
        {
            position = 18;
        }

        buffer[position] = input;
        buffer[position + 19] = input;

        dirty = true;


        return
            C0 *
                (buffer[position] +
                 buffer[position + 18])
            +
            C2 *
                (buffer[position + 2] +
                 buffer[position + 16])
            +
            C4 *
                (buffer[position + 4] +
                 buffer[position + 14])
            +
            C6 *
                (buffer[position + 6] +
                 buffer[position + 12])
            +
            C8 *
                (buffer[position + 8] +
                 buffer[position + 10])
            +
            C9 *
                buffer[position + 9];
    }


    public void reset()
    {
        // Avoid repeatedly clearing an already-empty filter.

        if( !dirty )
        {
            return;
        }

        for( int i = 0; i < 38; i++ )
        {
            buffer[i] = 0.0;
        }

        position = 0;
        dirty = false;
    }
}

    //[/user-code-and-variables]
}

 