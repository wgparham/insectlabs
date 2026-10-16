package com.insectlabs.zoid;


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
import java.util.Locale;
//[/user-imports]


public class zoid extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public zoid( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "zoid", ModuleType.ModuleType_EnvelopeGenerators, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "b620a0d3c45f4a5bacb810ef78607dc6" );
    }

void InitializeControls()
{

        line6 = new VoltageLine( "line6", "line6", this );
        AddComponent( line6 );
        line6.SetWantsMouseNotifications( false );
        line6.SetLineColor( new Color( 166, 36, 36, 255 ) );
        line6.SetLineWidth( (float)2 );
        line6.SetStartPosition( 58, 53 );
        line6.SetEndPosition( 30, 62 );
        line6.SetHasArrow( false );
        line6.SetArrowWidth( (float)8 );
        line6.SetArrowLength( (float)12 );

        labelFooter = new VoltageLabel( "labelFooter", "", this, "trapezoid generator" );
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
        imageTTW.SetCurrentImage( "image image(3).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "zoid" );
        AddComponent( labelNumber );
        labelNumber.SetWantsMouseNotifications( false );
        labelNumber.SetPosition( 49, 5 );
        labelNumber.SetSize( 61, 13 );
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

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(Seattle) Ltd" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 5, 13 );
        labelLocation.SetSize( 41, 14 );
        labelLocation.SetEditable( false, false );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation.SetColor( new Color( 26, 26, 26, 255 ) );
        labelLocation.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLocation.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLocation.SetBorderSize( 1 );
        labelLocation.SetMultiLineEdit( false );
        labelLocation.SetIsNumberEditor( false );
        labelLocation.SetNumberEditorRange( 0, 100 );
        labelLocation.SetNumberEditorInterval( 1 );
        labelLocation.SetNumberEditorUsesMouseWheel( false );
        labelLocation.SetHasCustomTextHoverColor( false );
        labelLocation.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLocation.SetFont( "Arial", 7, true, false );

        knobMode = new VoltageKnob( "knobMode", "Mode", this, 0, 4, 4 );
        AddComponent( knobMode );
        knobMode.SetWantsMouseNotifications( false );
        knobMode.SetPosition( 45, 40 );
        knobMode.SetSize( 25, 25 );
        knobMode.SetSkin( "ARP2500 Sm Gold" );
        knobMode.SetRange( 0, 4, 4, false, 5 );
        knobMode.SetKnobParams( 300, 60 );
        knobMode.DisplayValueInPercent( false );
        knobMode.SetKnobAdjustsRing( true );

        knobDelay = new VoltageKnob( "knobDelay", "Delay", this, 0.0, 1.0, 0.5 );
        AddComponent( knobDelay );
        knobDelay.SetWantsMouseNotifications( false );
        knobDelay.SetPosition( 45, 90 );
        knobDelay.SetSize( 25, 25 );
        knobDelay.SetSkin( "ARP2500 Sm Red" );
        knobDelay.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobDelay.SetKnobParams( 215, 145 );
        knobDelay.DisplayValueInPercent( false );
        knobDelay.SetKnobAdjustsRing( true );

        knobAttack = new VoltageKnob( "knobAttack", "Attack", this, 0.0, 1.0, 0.5 );
        AddComponent( knobAttack );
        knobAttack.SetWantsMouseNotifications( false );
        knobAttack.SetPosition( 45, 130 );
        knobAttack.SetSize( 25, 25 );
        knobAttack.SetSkin( "ARP2500 Sm Red" );
        knobAttack.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobAttack.SetKnobParams( 215, 145 );
        knobAttack.DisplayValueInPercent( false );
        knobAttack.SetKnobAdjustsRing( true );

        knobOn = new VoltageKnob( "knobOn", "On", this, 0.0, 1.0, 0.5 );
        AddComponent( knobOn );
        knobOn.SetWantsMouseNotifications( false );
        knobOn.SetPosition( 45, 170 );
        knobOn.SetSize( 25, 25 );
        knobOn.SetSkin( "ARP2500 Sm Red" );
        knobOn.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobOn.SetKnobParams( 215, 145 );
        knobOn.DisplayValueInPercent( false );
        knobOn.SetKnobAdjustsRing( true );

        knobDecay = new VoltageKnob( "knobDecay", "Decay", this, 0.0, 1.0, 0.5 );
        AddComponent( knobDecay );
        knobDecay.SetWantsMouseNotifications( false );
        knobDecay.SetPosition( 45, 210 );
        knobDecay.SetSize( 25, 25 );
        knobDecay.SetSkin( "ARP2500 Sm Red" );
        knobDecay.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobDecay.SetKnobParams( 215, 145 );
        knobDecay.DisplayValueInPercent( false );
        knobDecay.SetKnobAdjustsRing( true );

        knobZoid1 = new VoltageKnob( "knobZoid1", "Trapezoid I", this, -1.0, 1.0, 0.0 );
        AddComponent( knobZoid1 );
        knobZoid1.SetWantsMouseNotifications( false );
        knobZoid1.SetPosition( 32, 300 );
        knobZoid1.SetSize( 25, 25 );
        knobZoid1.SetSkin( "ARP2500 Sm Silver" );
        knobZoid1.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobZoid1.SetKnobParams( 215, 145 );
        knobZoid1.DisplayValueInPercent( false );
        knobZoid1.SetKnobAdjustsRing( true );

        knobZoid2 = new VoltageKnob( "knobZoid2", "Trapezoid II", this, -1.0, 1.0, 0.0 );
        AddComponent( knobZoid2 );
        knobZoid2.SetWantsMouseNotifications( false );
        knobZoid2.SetPosition( 58, 300 );
        knobZoid2.SetSize( 25, 25 );
        knobZoid2.SetSkin( "ARP2500 Sm Silver" );
        knobZoid2.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobZoid2.SetKnobParams( 215, 145 );
        knobZoid2.DisplayValueInPercent( false );
        knobZoid2.SetKnobAdjustsRing( true );

        knobLevel = new VoltageKnob( "knobLevel", "Signal Level", this, 0.0, 2.0, 1.0 );
        AddComponent( knobLevel );
        knobLevel.SetWantsMouseNotifications( false );
        knobLevel.SetPosition( 45, 260 );
        knobLevel.SetSize( 25, 25 );
        knobLevel.SetSkin( "ARP2500 Sm Silver" );
        knobLevel.SetRange( 0.0, 2.0, 1.0, false, 0 );
        knobLevel.SetKnobParams( 215, 145 );
        knobLevel.DisplayValueInPercent( true );
        knobLevel.SetKnobAdjustsRing( true );

        outZoid1 = new VoltageAudioJack( "outZoid1", "Trapezoid I Out", this, JackType.JackType_AudioOutput );
        AddComponent( outZoid1 );
        outZoid1.SetWantsMouseNotifications( false );
        outZoid1.SetPosition( 5, 300 );
        outZoid1.SetSize( 25, 25 );
        outZoid1.SetSkin( "Mini Jack 25px" );

        outZoid2 = new VoltageAudioJack( "outZoid2", "Trapezoid II Out", this, JackType.JackType_AudioOutput );
        AddComponent( outZoid2 );
        outZoid2.SetWantsMouseNotifications( false );
        outZoid2.SetPosition( 85, 300 );
        outZoid2.SetSize( 25, 25 );
        outZoid2.SetSkin( "Mini Jack 25px" );

        inSignal = new VoltageAudioJack( "inSignal", "SIgnal In", this, JackType.JackType_AudioInput );
        AddComponent( inSignal );
        inSignal.SetWantsMouseNotifications( false );
        inSignal.SetPosition( 17, 260 );
        inSignal.SetSize( 25, 25 );
        inSignal.SetSkin( "Jack Round 25px" );

        lampAttack = new VoltageLED( "lampAttack", "Attack Lamp", this );
        AddComponent( lampAttack );
        lampAttack.SetWantsMouseNotifications( false );
        lampAttack.SetPosition( 95, 125 );
        lampAttack.SetSize( 12, 12 );
        lampAttack.SetSkin( "2500 Lamp Green" );

        outEOC = new VoltageAudioJack( "outEOC", "End Of Cycle", this, JackType.JackType_AudioOutput );
        AddComponent( outEOC );
        outEOC.SetWantsMouseNotifications( false );
        outEOC.SetPosition( 73, 210 );
        outEOC.SetSize( 25, 25 );
        outEOC.SetSkin( "Mini Jack 25px" );

        inputDelay = new VoltageAudioJack( "inputDelay", "Delay CV In", this, JackType.JackType_AudioInput );
        AddComponent( inputDelay );
        inputDelay.SetWantsMouseNotifications( false );
        inputDelay.SetPosition( 18, 90 );
        inputDelay.SetSize( 25, 25 );
        inputDelay.SetSkin( "Jack Round 25px" );

        inputAttack = new VoltageAudioJack( "inputAttack", "Attack CV", this, JackType.JackType_AudioInput );
        AddComponent( inputAttack );
        inputAttack.SetWantsMouseNotifications( false );
        inputAttack.SetPosition( 18, 130 );
        inputAttack.SetSize( 25, 25 );
        inputAttack.SetSkin( "Jack Round 25px" );

        outEOA = new VoltageAudioJack( "outEOA", "End Of Attack", this, JackType.JackType_AudioOutput );
        AddComponent( outEOA );
        outEOA.SetWantsMouseNotifications( false );
        outEOA.SetPosition( 73, 130 );
        outEOA.SetSize( 25, 25 );
        outEOA.SetSkin( "Mini Jack 25px" );

        inputOn = new VoltageAudioJack( "inputOn", "On CV", this, JackType.JackType_AudioInput );
        AddComponent( inputOn );
        inputOn.SetWantsMouseNotifications( false );
        inputOn.SetPosition( 18, 170 );
        inputOn.SetSize( 25, 25 );
        inputOn.SetSkin( "Jack Round 25px" );

        inputDecay = new VoltageAudioJack( "inputDecay", "Decay CV", this, JackType.JackType_AudioInput );
        AddComponent( inputDecay );
        inputDecay.SetWantsMouseNotifications( false );
        inputDecay.SetPosition( 18, 210 );
        inputDecay.SetSize( 25, 25 );
        inputDecay.SetSkin( "Jack Round 25px" );

        outSignal = new VoltageAudioJack( "outSignal", "Signal Out", this, JackType.JackType_AudioOutput );
        AddComponent( outSignal );
        outSignal.SetWantsMouseNotifications( false );
        outSignal.SetPosition( 73, 260 );
        outSignal.SetSize( 25, 25 );
        outSignal.SetSkin( "Mini Jack 25px" );

        labelMode = new VoltageLabel( "labelMode", "Mode Label", this, "MODE" );
        AddComponent( labelMode );
        labelMode.SetWantsMouseNotifications( false );
        labelMode.SetPosition( 0, 65 );
        labelMode.SetSize( 115, 10 );
        labelMode.SetEditable( false, false );
        labelMode.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMode.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMode.SetColor( new Color( 0, 0, 0, 255 ) );
        labelMode.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMode.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMode.SetBorderSize( 1 );
        labelMode.SetMultiLineEdit( false );
        labelMode.SetIsNumberEditor( false );
        labelMode.SetNumberEditorRange( 0, 100 );
        labelMode.SetNumberEditorInterval( 1 );
        labelMode.SetNumberEditorUsesMouseWheel( false );
        labelMode.SetHasCustomTextHoverColor( false );
        labelMode.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMode.SetFont( "Arial", 7, true, false );

        labelDelay = new VoltageLabel( "labelDelay", "Delay Label", this, "DELAY" );
        AddComponent( labelDelay );
        labelDelay.SetWantsMouseNotifications( false );
        labelDelay.SetPosition( 5, 80 );
        labelDelay.SetSize( 80, 10 );
        labelDelay.SetEditable( false, false );
        labelDelay.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDelay.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDelay.SetColor( new Color( 0, 0, 0, 255 ) );
        labelDelay.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDelay.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDelay.SetBorderSize( 1 );
        labelDelay.SetMultiLineEdit( false );
        labelDelay.SetIsNumberEditor( false );
        labelDelay.SetNumberEditorRange( 0, 100 );
        labelDelay.SetNumberEditorInterval( 1 );
        labelDelay.SetNumberEditorUsesMouseWheel( false );
        labelDelay.SetHasCustomTextHoverColor( false );
        labelDelay.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDelay.SetFont( "Arial", 7, true, false );

        labelAttack = new VoltageLabel( "labelAttack", "Attack Label", this, "ATTACK" );
        AddComponent( labelAttack );
        labelAttack.SetWantsMouseNotifications( false );
        labelAttack.SetPosition( 5, 120 );
        labelAttack.SetSize( 80, 10 );
        labelAttack.SetEditable( false, false );
        labelAttack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelAttack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelAttack.SetColor( new Color( 0, 0, 0, 255 ) );
        labelAttack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelAttack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelAttack.SetBorderSize( 1 );
        labelAttack.SetMultiLineEdit( false );
        labelAttack.SetIsNumberEditor( false );
        labelAttack.SetNumberEditorRange( 0, 100 );
        labelAttack.SetNumberEditorInterval( 1 );
        labelAttack.SetNumberEditorUsesMouseWheel( false );
        labelAttack.SetHasCustomTextHoverColor( false );
        labelAttack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelAttack.SetFont( "Arial", 7, true, false );

        labelAttack_1 = new VoltageLabel( "labelAttack_1", "Attack Label_1", this, "ON" );
        AddComponent( labelAttack_1 );
        labelAttack_1.SetWantsMouseNotifications( false );
        labelAttack_1.SetPosition( 4, 160 );
        labelAttack_1.SetSize( 80, 10 );
        labelAttack_1.SetEditable( false, false );
        labelAttack_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelAttack_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelAttack_1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelAttack_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelAttack_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelAttack_1.SetBorderSize( 1 );
        labelAttack_1.SetMultiLineEdit( false );
        labelAttack_1.SetIsNumberEditor( false );
        labelAttack_1.SetNumberEditorRange( 0, 100 );
        labelAttack_1.SetNumberEditorInterval( 1 );
        labelAttack_1.SetNumberEditorUsesMouseWheel( false );
        labelAttack_1.SetHasCustomTextHoverColor( false );
        labelAttack_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelAttack_1.SetFont( "Arial", 7, true, false );

        labelDecay = new VoltageLabel( "labelDecay", "Decay Label", this, "DECAY" );
        AddComponent( labelDecay );
        labelDecay.SetWantsMouseNotifications( false );
        labelDecay.SetPosition( 5, 200 );
        labelDecay.SetSize( 80, 10 );
        labelDecay.SetEditable( false, false );
        labelDecay.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDecay.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDecay.SetColor( new Color( 0, 0, 0, 255 ) );
        labelDecay.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDecay.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDecay.SetBorderSize( 1 );
        labelDecay.SetMultiLineEdit( false );
        labelDecay.SetIsNumberEditor( false );
        labelDecay.SetNumberEditorRange( 0, 100 );
        labelDecay.SetNumberEditorInterval( 1 );
        labelDecay.SetNumberEditorUsesMouseWheel( false );
        labelDecay.SetHasCustomTextHoverColor( false );
        labelDecay.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDecay.SetFont( "Arial", 7, true, false );

        labelEOC = new VoltageLabel( "labelEOC", "EOC Label", this, "EOC" );
        AddComponent( labelEOC );
        labelEOC.SetWantsMouseNotifications( false );
        labelEOC.SetPosition( 75, 200 );
        labelEOC.SetSize( 21, 10 );
        labelEOC.SetEditable( false, false );
        labelEOC.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEOC.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEOC.SetColor( new Color( 0, 0, 0, 255 ) );
        labelEOC.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEOC.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEOC.SetBorderSize( 1 );
        labelEOC.SetMultiLineEdit( false );
        labelEOC.SetIsNumberEditor( false );
        labelEOC.SetNumberEditorRange( 0, 100 );
        labelEOC.SetNumberEditorInterval( 1 );
        labelEOC.SetNumberEditorUsesMouseWheel( false );
        labelEOC.SetHasCustomTextHoverColor( false );
        labelEOC.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEOC.SetFont( "Arial", 7, true, false );

        labelSignal = new VoltageLabel( "labelSignal", "Signal Label", this, "SIGNAL" );
        AddComponent( labelSignal );
        labelSignal.SetWantsMouseNotifications( false );
        labelSignal.SetPosition( 9, 250 );
        labelSignal.SetSize( 41, 10 );
        labelSignal.SetEditable( false, false );
        labelSignal.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSignal.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSignal.SetColor( new Color( 0, 0, 0, 255 ) );
        labelSignal.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSignal.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSignal.SetBorderSize( 1 );
        labelSignal.SetMultiLineEdit( false );
        labelSignal.SetIsNumberEditor( false );
        labelSignal.SetNumberEditorRange( 0, 100 );
        labelSignal.SetNumberEditorInterval( 1 );
        labelSignal.SetNumberEditorUsesMouseWheel( false );
        labelSignal.SetHasCustomTextHoverColor( false );
        labelSignal.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSignal.SetFont( "Arial", 7, true, false );

        lampOn = new VoltageLED( "lampOn", "On Lamp", this );
        AddComponent( lampOn );
        lampOn.SetWantsMouseNotifications( false );
        lampOn.SetPosition( 95, 165 );
        lampOn.SetSize( 12, 12 );
        lampOn.SetSkin( "2500 Lamp White" );

        lampDecay = new VoltageLED( "lampDecay", "Decay Lamp", this );
        AddComponent( lampDecay );
        lampDecay.SetWantsMouseNotifications( false );
        lampDecay.SetPosition( 95, 205 );
        lampDecay.SetSize( 12, 12 );
        lampDecay.SetSkin( "2500 Lamp Red" );

        labelEOA = new VoltageLabel( "labelEOA", "EOA Label", this, "EOA" );
        AddComponent( labelEOA );
        labelEOA.SetWantsMouseNotifications( false );
        labelEOA.SetPosition( 75, 120 );
        labelEOA.SetSize( 21, 10 );
        labelEOA.SetEditable( false, false );
        labelEOA.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEOA.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEOA.SetColor( new Color( 0, 0, 0, 255 ) );
        labelEOA.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEOA.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEOA.SetBorderSize( 1 );
        labelEOA.SetMultiLineEdit( false );
        labelEOA.SetIsNumberEditor( false );
        labelEOA.SetNumberEditorRange( 0, 100 );
        labelEOA.SetNumberEditorInterval( 1 );
        labelEOA.SetNumberEditorUsesMouseWheel( false );
        labelEOA.SetHasCustomTextHoverColor( false );
        labelEOA.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEOA.SetFont( "Arial", 7, true, false );

        labelZoid = new VoltageLabel( "labelZoid", "Trapezoid Label", this, "TRAPEZOID I       TRAPEZOID II" );
        AddComponent( labelZoid );
        labelZoid.SetWantsMouseNotifications( false );
        labelZoid.SetPosition( 0, 290 );
        labelZoid.SetSize( 115, 10 );
        labelZoid.SetEditable( false, false );
        labelZoid.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelZoid.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelZoid.SetColor( new Color( 0, 0, 0, 255 ) );
        labelZoid.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelZoid.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelZoid.SetBorderSize( 1 );
        labelZoid.SetMultiLineEdit( false );
        labelZoid.SetIsNumberEditor( false );
        labelZoid.SetNumberEditorRange( 0, 100 );
        labelZoid.SetNumberEditorInterval( 1 );
        labelZoid.SetNumberEditorUsesMouseWheel( false );
        labelZoid.SetHasCustomTextHoverColor( false );
        labelZoid.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelZoid.SetFont( "Arial", 7, true, false );

        labelEOA_1 = new VoltageLabel( "labelEOA_1", "EOA Label_1", this, "FREE RUN" );
        AddComponent( labelEOA_1 );
        labelEOA_1.SetWantsMouseNotifications( false );
        labelEOA_1.SetPosition( 26, 40 );
        labelEOA_1.SetSize( 20, 8 );
        labelEOA_1.SetEditable( false, false );
        labelEOA_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEOA_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEOA_1.SetColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEOA_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEOA_1.SetBorderSize( 1 );
        labelEOA_1.SetMultiLineEdit( false );
        labelEOA_1.SetIsNumberEditor( false );
        labelEOA_1.SetNumberEditorRange( 0, 100 );
        labelEOA_1.SetNumberEditorInterval( 1 );
        labelEOA_1.SetNumberEditorUsesMouseWheel( false );
        labelEOA_1.SetHasCustomTextHoverColor( false );
        labelEOA_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_1.SetFont( "Arial", 4, true, false );

        labelEOA_2 = new VoltageLabel( "labelEOA_2", "EOA Label_2", this, "HOLD-ON" );
        AddComponent( labelEOA_2 );
        labelEOA_2.SetWantsMouseNotifications( false );
        labelEOA_2.SetPosition( 36, 35 );
        labelEOA_2.SetSize( 16, 8 );
        labelEOA_2.SetEditable( false, false );
        labelEOA_2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEOA_2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEOA_2.SetColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEOA_2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEOA_2.SetBorderSize( 1 );
        labelEOA_2.SetMultiLineEdit( false );
        labelEOA_2.SetIsNumberEditor( false );
        labelEOA_2.SetNumberEditorRange( 0, 100 );
        labelEOA_2.SetNumberEditorInterval( 1 );
        labelEOA_2.SetNumberEditorUsesMouseWheel( false );
        labelEOA_2.SetHasCustomTextHoverColor( false );
        labelEOA_2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_2.SetFont( "Arial", 4, true, false );

        labelEOA_3 = new VoltageLabel( "labelEOA_3", "EOA Label_3", this, "SINGLE CYCLE" );
        AddComponent( labelEOA_3 );
        labelEOA_3.SetWantsMouseNotifications( false );
        labelEOA_3.SetPosition( 0, 30 );
        labelEOA_3.SetSize( 115, 8 );
        labelEOA_3.SetEditable( false, false );
        labelEOA_3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEOA_3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEOA_3.SetColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEOA_3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEOA_3.SetBorderSize( 1 );
        labelEOA_3.SetMultiLineEdit( false );
        labelEOA_3.SetIsNumberEditor( false );
        labelEOA_3.SetNumberEditorRange( 0, 100 );
        labelEOA_3.SetNumberEditorInterval( 1 );
        labelEOA_3.SetNumberEditorUsesMouseWheel( false );
        labelEOA_3.SetHasCustomTextHoverColor( false );
        labelEOA_3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_3.SetFont( "Arial", 4, true, false );

        labelEOA_4 = new VoltageLabel( "labelEOA_4", "EOA Label_4", this, "MULTI CYCLE" );
        AddComponent( labelEOA_4 );
        labelEOA_4.SetWantsMouseNotifications( false );
        labelEOA_4.SetPosition( 64, 35 );
        labelEOA_4.SetSize( 25, 8 );
        labelEOA_4.SetEditable( false, false );
        labelEOA_4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEOA_4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEOA_4.SetColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEOA_4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEOA_4.SetBorderSize( 1 );
        labelEOA_4.SetMultiLineEdit( false );
        labelEOA_4.SetIsNumberEditor( false );
        labelEOA_4.SetNumberEditorRange( 0, 100 );
        labelEOA_4.SetNumberEditorInterval( 1 );
        labelEOA_4.SetNumberEditorUsesMouseWheel( false );
        labelEOA_4.SetHasCustomTextHoverColor( false );
        labelEOA_4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_4.SetFont( "Arial", 4, true, false );

        labelEOA_5 = new VoltageLabel( "labelEOA_5", "EOA Label_5", this, "THRESHOLD" );
        AddComponent( labelEOA_5 );
        labelEOA_5.SetWantsMouseNotifications( false );
        labelEOA_5.SetPosition( 70, 40 );
        labelEOA_5.SetSize( 20, 8 );
        labelEOA_5.SetEditable( false, false );
        labelEOA_5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelEOA_5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelEOA_5.SetColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelEOA_5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelEOA_5.SetBorderSize( 1 );
        labelEOA_5.SetMultiLineEdit( false );
        labelEOA_5.SetIsNumberEditor( false );
        labelEOA_5.SetNumberEditorRange( 0, 100 );
        labelEOA_5.SetNumberEditorInterval( 1 );
        labelEOA_5.SetNumberEditorUsesMouseWheel( false );
        labelEOA_5.SetHasCustomTextHoverColor( false );
        labelEOA_5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelEOA_5.SetFont( "Arial", 4, true, false );

        inputTrigger = new VoltageAudioJack( "inputTrigger", "Trigger Input", this, JackType.JackType_AudioInput );
        AddComponent( inputTrigger );
        inputTrigger.SetWantsMouseNotifications( false );
        inputTrigger.SetPosition( 18, 50 );
        inputTrigger.SetSize( 25, 25 );
        inputTrigger.SetSkin( "Jack Round 25px" );

        knobThreshold = new VoltageKnob( "knobThreshold", "Threshold Level", this, 0.0, 1.0, 1.0 );
        AddComponent( knobThreshold );
        knobThreshold.SetWantsMouseNotifications( false );
        knobThreshold.SetPosition( 92, 39 );
        knobThreshold.SetSize( 11, 11 );
        knobThreshold.SetSkin( "Dial Black" );
        knobThreshold.SetRange( 0.0, 1.0, 1.0, false, 0 );
        knobThreshold.SetKnobParams( 215, 145 );
        knobThreshold.DisplayValueInPercent( false );
        knobThreshold.SetKnobAdjustsRing( true );

        labelLevel = new VoltageLabel( "labelLevel", "Level Label", this, "LEVEL" );
        AddComponent( labelLevel );
        labelLevel.SetWantsMouseNotifications( false );
        labelLevel.SetPosition( 37, 250 );
        labelLevel.SetSize( 41, 10 );
        labelLevel.SetEditable( false, false );
        labelLevel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLevel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLevel.SetColor( new Color( 0, 0, 0, 255 ) );
        labelLevel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLevel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLevel.SetBorderSize( 1 );
        labelLevel.SetMultiLineEdit( false );
        labelLevel.SetIsNumberEditor( false );
        labelLevel.SetNumberEditorRange( 0, 100 );
        labelLevel.SetNumberEditorInterval( 1 );
        labelLevel.SetNumberEditorUsesMouseWheel( false );
        labelLevel.SetHasCustomTextHoverColor( false );
        labelLevel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLevel.SetFont( "Arial", 7, true, false );

        labelAmp = new VoltageLabel( "labelAmp", "Amp Label", this, "AMP" );
        AddComponent( labelAmp );
        labelAmp.SetWantsMouseNotifications( false );
        labelAmp.SetPosition( 65, 250 );
        labelAmp.SetSize( 41, 10 );
        labelAmp.SetEditable( false, false );
        labelAmp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelAmp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelAmp.SetColor( new Color( 0, 0, 0, 255 ) );
        labelAmp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelAmp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelAmp.SetBorderSize( 1 );
        labelAmp.SetMultiLineEdit( false );
        labelAmp.SetIsNumberEditor( false );
        labelAmp.SetNumberEditorRange( 0, 100 );
        labelAmp.SetNumberEditorInterval( 1 );
        labelAmp.SetNumberEditorUsesMouseWheel( false );
        labelAmp.SetHasCustomTextHoverColor( false );
        labelAmp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelAmp.SetFont( "Arial", 7, true, false );

        buttonTrigger = new VoltageButton( "buttonTrigger", "Trigger Button", this );
        AddComponent( buttonTrigger );
        buttonTrigger.SetWantsMouseNotifications( false );
        buttonTrigger.SetPosition( 81, 57 );
        buttonTrigger.SetSize( 11, 11 );
        buttonTrigger.SetSkin( "Yellow Square" );
        buttonTrigger.ShowOverlay( true );
        buttonTrigger.SetOverlayText( "T" );
        buttonTrigger.SetOverlayTextFont( "<Sans-Serif>", 7, false, false );
        buttonTrigger.SetOverlayTextColor( new Color( 42, 42, 42 ) );
        buttonTrigger.SetOverlayArea( 0, 0, 0, 0 );
        buttonTrigger.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        buttonTrigger.SetAutoRepeat( false );
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
        manualKnobs = new VoltageKnob[] { knobDelay, knobAttack, knobOn, knobDecay,
                knobLevel, knobZoid1, knobZoid2, knobThreshold };
        resetPending.set(true);
        StartGuiUpdateTimer(25);
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
        if (notification == ModuleNotifications.Button_Changed && component == buttonTrigger) {
            boolean down = doubleValue >= 0.5;
            if (down && !buttonHeld) buttonPending.set(true);
            buttonHeld = down;
            return true;
        }
        if (notification == ModuleNotifications.GUI_Update_Timer) {
            int bits = lampBits;
            lampAttack.SetValue((bits & 1) != 0 ? 1.0 : 0.0);
            lampOn.SetValue((bits & 2) != 0 ? 1.0 : 0.0);
            lampDecay.SetValue((bits & 4) != 0 ? 1.0 : 0.0);
            return true;
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
        boolean reset = resetPending.getAndSet(false);
        boolean resume = bypassed;
        bypassed = false;
        int mode = (int)Math.round(bound(knobMode.GetValue(), 0.0, 4.0));
        boolean changedMode = mode != lastMode;
        if (reset || changedMode) {
            first.reset();
            lastMode = mode;
            eoaRemaining = eocRemaining = 0;
            for (int i = 0; i < lampRemaining.length; ++i) lampRemaining[i] = 0;
        }
        for (int i = 0; i < manual.length; ++i) {
            double lo = i == 5 || i == 6 ? -1.0 : 0.0;
            double hi = i == 4 ? 2.0 : 1.0;
            double target = bound(manualKnobs[i].GetValue(), lo, hi);
            manual[i] = reset || resume || changedMode ? target
                    : manual[i] + SMOOTH * (target - manual[i]);
        }
        double signal = safeSignal(inSignal.GetValue());
        double trigger = safeSignal(inputTrigger.GetValue());
        boolean oldGate = gateHigh;
        gateHigh = mode == 2 ? trigger > 0.0 : schmitt(trigger, gateHigh, 1.0, 0.8);
        boolean oldThreshold = thresholdHigh;
        double threshold = 5.0 * manual[7];
        thresholdHigh = schmitt(Math.max(signal, trigger), thresholdHigh,
                threshold + 0.025, threshold - 0.025);
        boolean buttonEvent = buttonPending.getAndSet(false);
        if (reset || resume || changedMode) {
            // Re-arm at the actual input levels: recalling/bypass must not invent edges.
            oldGate = gateHigh;
            oldThreshold = thresholdHigh;
            buttonEvent = false;
            eoaRemaining = eocRemaining = 0;
        }
        boolean event = buttonEvent || (mode == 4
                ? thresholdHigh && !oldThreshold : gateHigh && !oldGate);
        boolean hold = mode == 1 && (gateHigh || buttonHeld);
        boolean cycling = mode == 0 || mode == 3 && (gateHigh || buttonHeld);
        timing.delay = stageTime(0, manual[0], inputDelay.GetValue());
        timing.attack = stageTime(1, manual[1], inputAttack.GetValue());
        timing.on = stageTime(2, manual[2], inputOn.GetValue());
        timing.decay = stageTime(3, manual[3], inputDecay.GetValue());
        if (event || cycling && first.phase == SharedTrapezoid.IDLE) first.trigger();
        int firstEvents = first.step(timing, hold);
        if ((firstEvents & 1) != 0) {
            eoaRemaining = PULSE_SAMPLES;
        }
        if ((firstEvents & 2) != 0) eocRemaining = PULSE_SAMPLES;
        outZoid1.SetValue(5.0 * (2.0 * first.value - 1.0) * manual[5]);
        outZoid2.SetValue(5.0 * (2.0 * first.other - 1.0) * manual[6]);
        outSignal.SetValue(safeSignal(signal * vcaGain(first.value) * manual[4]));
        outEOA.SetValue(eoaRemaining > 0 ? 5.0 : 0.0);
        outEOC.SetValue(eocRemaining > 0 ? 5.0 : 0.0);
        if (eoaRemaining > 0) --eoaRemaining;
        if (eocRemaining > 0) --eocRemaining;
        int bits = 0;
        for (int i = 0; i < 3; ++i) {
            if ((firstEvents & (4 << i)) != 0 || first.phase == SharedTrapezoid.ATTACK + i)
                lampRemaining[i] = LAMP_SAMPLES;
            else if (lampRemaining[i] > 0) --lampRemaining[i];
            if (lampRemaining[i] > 0) bits |= 1 << i;
        }
        lampBits = bits;
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
        outSignal.SetValue(safeSignal(inSignal.GetValue()));
        outZoid1.SetValue(0.0);
        outZoid2.SetValue(0.0);
        outEOA.SetValue(0.0);
        outEOC.SetValue(0.0);
        lampBits = 0;
        bypassed = true;
        for (int i = 0; i < lampRemaining.length; ++i) lampRemaining[i] = 0;
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
        if (component == knobMode) return "Mode: " + MODE_NAMES[(int)Math.round(bound(knobMode.GetValue(), 0, 4))];
        if (component == knobDelay) return timeTip("Delay", 0, knobDelay.GetValue());
        if (component == knobAttack) return timeTip("Attack", 1, knobAttack.GetValue());
        if (component == knobOn) return timeTip("On", 2, knobOn.GetValue());
        if (component == knobDecay) return timeTip("Decay", 3, knobDecay.GetValue());
        if (component == knobThreshold) return String.format(Locale.ROOT,
                "Threshold: %.3f V; positive crossings of Signal In or Trigger In", 5 * bound(knobThreshold.GetValue(), 0, 1));
        if (component == knobLevel) return String.format(Locale.ROOT,
                "VCA level: %.1f%%; independent of trapezoid output levels", 100 * bound(knobLevel.GetValue(), 0, 2));
        if (component == knobZoid1 || component == knobZoid2) return String.format(Locale.ROOT,
                "Trapezoid signed level: %+.3f V; center zero, negative inverts", 5 * bound(component == knobZoid1 ? knobZoid1.GetValue() : knobZoid2.GetValue(), -1, 1));
        if (component == inputDelay || component == inputAttack || component == inputOn || component == inputDecay)
            return "Stage time CV: +5 V adds one full knob sweep; positive CV lengthens time";
        if (component == inputTrigger) return "Trigger/Gate: Single Cycle detects positive zero crossings; other gate modes use 1 V/0.8 V; Threshold uses its setting";
        if (component == inSignal) return "Signal In: primary-envelope VCA input; also sensed in Threshold mode";
        if (component == outSignal) return "Logarithmic primary VCA: -60 dB at low plateau, unity at peak with Level 100%";
        if (component == outZoid1) return "Trapezoid I: -5 to +5 V at +5 V signed level; center zero, CCW inverts";
        if (component == outZoid2) return "Phase II: falls in Delay, low in Attack, rises in On, high in Decay; center zero, CCW inverts";
        if (component == outEOA) return "Primary End of Attack: +5 V, 5 ms pulse; shared core enters On and phase II begins rising";
        if (component == outEOC) return "Primary End of Cycle: +5 V, 5 ms pulse at end of Decay";
        if (component == buttonTrigger) return "Manual trigger; held gate in Hold-On and Multi Cycle modes";
        if (component == lampAttack) return "Primary Attack activity (green)";
        if (component == lampOn) return "Primary On activity (white)";
        if (component == lampDecay) return "Primary Decay activity (red)";
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
        if (component == knobDelay || component == knobAttack || component == knobOn || component == knobDecay) {
            int stage = component == knobDelay ? 0 : component == knobAttack ? 1 : component == knobOn ? 2 : 3;
            double seconds = bound(newValue, MIN_TIME[stage], MAX_TIME[stage]);
            double position = Math.log(seconds / MIN_TIME[stage]) / TIME_CURVE;
            super.EditComponentValue(component, position, newText);
            return;
        }
        if (component == knobThreshold) {
            super.EditComponentValue(component, bound(newValue / 5, 0, 1), newText);
            return;
        }
        if (component == knobZoid1 || component == knobZoid2) {
            super.EditComponentValue(component, bound(newValue / 5, -1, 1), newText);
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
    private VoltageButton buttonTrigger;
    private VoltageLabel labelAmp;
    private VoltageLabel labelLevel;
    private VoltageKnob knobThreshold;
    private VoltageAudioJack inputTrigger;
    private VoltageLabel labelEOA_5;
    private VoltageLabel labelEOA_4;
    private VoltageLabel labelEOA_3;
    private VoltageLabel labelEOA_2;
    private VoltageLabel labelEOA_1;
    private VoltageLabel labelZoid;
    private VoltageLabel labelEOA;
    private VoltageLED lampDecay;
    private VoltageLED lampOn;
    private VoltageLabel labelSignal;
    private VoltageLabel labelEOC;
    private VoltageLabel labelDecay;
    private VoltageLabel labelAttack_1;
    private VoltageLabel labelAttack;
    private VoltageLabel labelDelay;
    private VoltageLabel labelMode;
    private VoltageAudioJack outSignal;
    private VoltageAudioJack inputDecay;
    private VoltageAudioJack inputOn;
    private VoltageAudioJack outEOA;
    private VoltageAudioJack inputAttack;
    private VoltageAudioJack inputDelay;
    private VoltageAudioJack outEOC;
    private VoltageLED lampAttack;
    private VoltageAudioJack inSignal;
    private VoltageAudioJack outZoid2;
    private VoltageAudioJack outZoid1;
    private VoltageKnob knobLevel;
    private VoltageKnob knobZoid2;
    private VoltageKnob knobZoid1;
    private VoltageKnob knobDecay;
    private VoltageKnob knobOn;
    private VoltageKnob knobAttack;
    private VoltageKnob knobDelay;
    private VoltageKnob knobMode;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLine line6;


    //[user-code-and-variables]    Add your own variables and functions here
    // ZOID 1.0.0: native 48 kHz test DSP. Timings are chosen calibrations,
    // not a component-level reconstruction of a particular Synthi revision.
    private static final double DT = 1.0 / 48000.0;
    private static final double SMOOTH = 1.0 - Math.exp(-DT / 0.005);
    private static final double TIME_CURVE = Math.log(10000.0);
    private static final int PULSE_SAMPLES = 240;
    private static final int LAMP_SAMPLES = 1440; // Capture short stages for the 25 ms UI timer.
    private static final double[] MIN_TIME = { 0.002, 0.002, 0.002, 0.002 };
    private static final double[] MAX_TIME = { 20.0, 20.0, 20.0, 20.0 };
    private static final String[] MODE_NAMES = {
        "Free Run", "Hold-On", "Single Cycle", "Multi Cycle", "Threshold"
    };
    private final AtomicBoolean resetPending = new AtomicBoolean(true);
    private final AtomicBoolean buttonPending = new AtomicBoolean(false);
    private volatile boolean buttonHeld;
    private volatile int lampBits;
    private final SharedTrapezoid first = new SharedTrapezoid();
    private final Timing timing = new Timing();
    private VoltageKnob[] manualKnobs;
    private final double[] manual = new double[8];
    private final double[] cachedPosition = { -1, -1, -1, -1 };
    private final double[] cachedTime = new double[4];
    private final int[] lampRemaining = new int[3];
    private int lastMode = -1;
    private int eoaRemaining, eocRemaining;
    private boolean gateHigh, thresholdHigh, bypassed;

    private static double bound(double value, double lo, double hi) {
        return Double.isFinite(value) ? Math.max(lo, Math.min(hi, value)) : lo <= 0 && hi >= 0 ? 0 : lo;
    }

    private static double safeSignal(double value) {
        return Double.isFinite(value) ? value : 0.0;
    }

    private static boolean schmitt(double input, boolean high, double rise, double fall) {
        return high ? input > fall : input >= rise;
    }

    private static double timeForPosition(int stage, double position) {
        return MIN_TIME[stage] * Math.exp(TIME_CURVE * bound(position, 0, 1));
    }

    private double stageTime(int stage, double position, double cv) {
        double p = bound(position + bound(cv, -20, 20) / 5, 0, 1);
        if (p != cachedPosition[stage]) {
            cachedPosition[stage] = p;
            cachedTime[stage] = timeForPosition(stage, p);
        }
        double seconds = cachedTime[stage];
        return seconds == 0.0 ? 0.0 : bound(seconds, DT, 240.0);
    }

    private static String timeTip(String name, int stage, double position) {
        return String.format(Locale.ROOT, "%s: %.6f s (manual); clockwise longer; type seconds",
                name, timeForPosition(stage, position));
    }

    private static final class Timing {
        double delay, attack, on, decay;
    }

    // Exponential gain versus control voltage, 60 dB from low to high.
    // The brochure does not establish absolute CV span; +/-5 V outputs are our calibration.
    private static double vcaGain(double control) {
        return Math.exp(Math.log(1000.0) * (bound(control, 0, 1) - 1.0));
    }

    /** One segment clock, two rotated trapezoids. Internal output levels are 0..1. */
    private static final class SharedTrapezoid {
        static final int IDLE = 0, DELAY = 1, ATTACK = 2, ON = 3, DECAY = 4;
        int phase;
        double value, other = 1.0, progress;

        void reset() {
            phase = IDLE;
            progress = 0.0;
            updateOutputs();
        }

        void trigger() {
            switch (phase) {
                case IDLE:
                case DELAY:
                    phase = DELAY;
                    progress = 0.0;
                    break;
                case ATTACK:
                    break;
                case ON:
                    progress = 0.0;
                    break;
                case DECAY:
                    phase = ATTACK;
                    progress = value; // Keep I continuous; II resynchronizes to its low plateau.
                    break;
                default:
                    reset();
                    return;
            }
            updateOutputs();
        }

        private void updateOutputs() {
            switch (phase) {
                case DELAY: value = 0.0; other = 1.0 - progress; break;
                case ATTACK: value = progress; other = 0.0; break;
                case ON: value = 1.0; other = progress; break;
                case DECAY: value = 1.0 - progress; other = 1.0; break;
                default: value = 0.0; other = 1.0; break;
            }
        }

        int step(Timing time, boolean hold) {
            if (!Double.isFinite(progress)) reset();
            if (phase == DELAY && time.delay <= 0.0) {
                // Skip the segment in this same sample. Phase II's fall becomes instantaneous.
                phase = ATTACK;
                progress = 0.0;
            }
            int events = 0;
            switch (phase) {
                case DELAY:
                    progress = Math.min(1.0, progress + DT / time.delay);
                    if (progress >= 1.0 - 1e-12) {
                        phase = ATTACK;
                        progress = 0.0;
                        events = 4;
                    }
                    break;
                case ATTACK:
                    progress = Math.min(1.0, progress + DT / time.attack);
                    events = 4;
                    if (progress >= 1.0 - 1e-12) {
                        phase = ON;
                        progress = 0.0;
                        events |= 1 | 8;
                    }
                    break;
                case ON:
                    progress = Math.min(1.0, progress + DT / time.on);
                    events = 8;
                    // ON runs while held. Once its time expires, a high gate blocks Decay.
                    if (progress >= 1.0 - 1e-12 && !hold) {
                        phase = DECAY;
                        progress = 0.0;
                        events |= 16;
                    }
                    break;
                case DECAY:
                    progress = Math.min(1.0, progress + DT / time.decay);
                    events = 16;
                    if (progress >= 1.0 - 1e-12) {
                        phase = IDLE;
                        progress = 0.0;
                        events |= 2;
                    }
                    break;
                default:
                    break;
            }
            updateOutputs();
            return events;
        }
    }
    //[/user-code-and-variables]
}

 
