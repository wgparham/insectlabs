package com.insectlabs.n02;


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


public class n02 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public n02( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "n02", ModuleType.ModuleType_Source, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "3675af2f35d44825ba51c9e384092e3d" );
    }

void InitializeControls()
{

        lableBlendMixDot = new VoltageLabel( "lableBlendMixDot", "Middle Dot Blend Mix", this, "·" );
        AddComponent( lableBlendMixDot );
        lableBlendMixDot.SetWantsMouseNotifications( false );
        lableBlendMixDot.SetPosition( 13, 277 );
        lableBlendMixDot.SetSize( 39, 13 );
        lableBlendMixDot.SetEditable( false, false );
        lableBlendMixDot.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot.SetBorderSize( 1 );
        lableBlendMixDot.SetMultiLineEdit( false );
        lableBlendMixDot.SetIsNumberEditor( false );
        lableBlendMixDot.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot.SetNumberEditorInterval( 1 );
        lableBlendMixDot.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot.SetHasCustomTextHoverColor( false );
        lableBlendMixDot.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot.SetFont( "Arial", 33, true, false );

        labelFooter = new VoltageLabel( "labelFooter", "", this, "dual noise generator" );
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

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "n02" );
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

        knobColourA = new VoltageKnob( "knobColourA", "Analog Colour", this, -1.0, 1.0, 0.0 );
        AddComponent( knobColourA );
        knobColourA.SetWantsMouseNotifications( false );
        knobColourA.SetPosition( 20, 179 );
        knobColourA.SetSize( 25, 25 );
        knobColourA.SetSkin( "ARP2500 Sm Emerald" );
        knobColourA.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobColourA.SetKnobParams( 215, 145 );
        knobColourA.DisplayValueInPercent( false );
        knobColourA.SetKnobAdjustsRing( true );

        knobLevelA = new VoltageKnob( "knobLevelA", "Analog Level", this, 0.0, 1.0, 0.0 );
        AddComponent( knobLevelA );
        knobLevelA.SetWantsMouseNotifications( false );
        knobLevelA.SetPosition( 70, 179 );
        knobLevelA.SetSize( 25, 25 );
        knobLevelA.SetSkin( "ARP2500 Sm Silver" );
        knobLevelA.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobLevelA.SetKnobParams( 215, 145 );
        knobLevelA.DisplayValueInPercent( false );
        knobLevelA.SetKnobAdjustsRing( true );

        labelColourA = new VoltageLabel( "labelColourA", "Analog Colour Label", this, "COLOUR" );
        AddComponent( labelColourA );
        labelColourA.SetWantsMouseNotifications( false );
        labelColourA.SetPosition( 13, 204 );
        labelColourA.SetSize( 39, 13 );
        labelColourA.SetEditable( false, false );
        labelColourA.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelColourA.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelColourA.SetColor( new Color( 42, 42, 42, 255 ) );
        labelColourA.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelColourA.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelColourA.SetBorderSize( 1 );
        labelColourA.SetMultiLineEdit( false );
        labelColourA.SetIsNumberEditor( false );
        labelColourA.SetNumberEditorRange( 0, 100 );
        labelColourA.SetNumberEditorInterval( 1 );
        labelColourA.SetNumberEditorUsesMouseWheel( false );
        labelColourA.SetHasCustomTextHoverColor( false );
        labelColourA.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelColourA.SetFont( "Arial", 7, true, false );

        labelColour_1 = new VoltageLabel( "labelColour_1", "Colour Label_1", this, "LEVEL" );
        AddComponent( labelColour_1 );
        labelColour_1.SetWantsMouseNotifications( false );
        labelColour_1.SetPosition( 63, 204 );
        labelColour_1.SetSize( 39, 13 );
        labelColour_1.SetEditable( false, false );
        labelColour_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelColour_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelColour_1.SetColor( new Color( 42, 42, 42, 255 ) );
        labelColour_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelColour_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelColour_1.SetBorderSize( 1 );
        labelColour_1.SetMultiLineEdit( false );
        labelColour_1.SetIsNumberEditor( false );
        labelColour_1.SetNumberEditorRange( 0, 100 );
        labelColour_1.SetNumberEditorInterval( 1 );
        labelColour_1.SetNumberEditorUsesMouseWheel( false );
        labelColour_1.SetHasCustomTextHoverColor( false );
        labelColour_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelColour_1.SetFont( "Arial", 7, true, false );

        knobSpeed = new VoltageKnob( "knobSpeed", "Digital Clock Rate", this, -1.0, 1.0, 0.0 );
        AddComponent( knobSpeed );
        knobSpeed.SetWantsMouseNotifications( false );
        knobSpeed.SetPosition( 20, 61 );
        knobSpeed.SetSize( 25, 25 );
        knobSpeed.SetSkin( "ARP2500 Sm Blue" );
        knobSpeed.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobSpeed.SetKnobParams( 215, 145 );
        knobSpeed.DisplayValueInPercent( false );
        knobSpeed.SetKnobAdjustsRing( true );

        knobLevelD = new VoltageKnob( "knobLevelD", "Digital Level", this, 0.0, 1.0, 0.0 );
        AddComponent( knobLevelD );
        knobLevelD.SetWantsMouseNotifications( false );
        knobLevelD.SetPosition( 20, 111 );
        knobLevelD.SetSize( 25, 25 );
        knobLevelD.SetSkin( "ARP2500 Sm Silver" );
        knobLevelD.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobLevelD.SetKnobParams( 215, 145 );
        knobLevelD.DisplayValueInPercent( false );
        knobLevelD.SetKnobAdjustsRing( true );

        knobColourD = new VoltageKnob( "knobColourD", "Digital Colour", this, -1.0, 1.0, 0.0 );
        AddComponent( knobColourD );
        knobColourD.SetWantsMouseNotifications( false );
        knobColourD.SetPosition( 70, 61 );
        knobColourD.SetSize( 25, 25 );
        knobColourD.SetSkin( "ARP2500 Sm Emerald" );
        knobColourD.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobColourD.SetKnobParams( 215, 145 );
        knobColourD.DisplayValueInPercent( false );
        knobColourD.SetKnobAdjustsRing( true );

        labelColourD = new VoltageLabel( "labelColourD", "Digital Colour Label", this, "COLOUR" );
        AddComponent( labelColourD );
        labelColourD.SetWantsMouseNotifications( false );
        labelColourD.SetPosition( 63, 86 );
        labelColourD.SetSize( 39, 13 );
        labelColourD.SetEditable( false, false );
        labelColourD.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelColourD.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelColourD.SetColor( new Color( 42, 42, 42, 255 ) );
        labelColourD.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelColourD.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelColourD.SetBorderSize( 1 );
        labelColourD.SetMultiLineEdit( false );
        labelColourD.SetIsNumberEditor( false );
        labelColourD.SetNumberEditorRange( 0, 100 );
        labelColourD.SetNumberEditorInterval( 1 );
        labelColourD.SetNumberEditorUsesMouseWheel( false );
        labelColourD.SetHasCustomTextHoverColor( false );
        labelColourD.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelColourD.SetFont( "Arial", 7, true, false );

        labelColour_3 = new VoltageLabel( "labelColour_3", "Colour Label_3", this, "LEVEL" );
        AddComponent( labelColour_3 );
        labelColour_3.SetWantsMouseNotifications( false );
        labelColour_3.SetPosition( 13, 136 );
        labelColour_3.SetSize( 39, 13 );
        labelColour_3.SetEditable( false, false );
        labelColour_3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelColour_3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelColour_3.SetColor( new Color( 42, 42, 42, 255 ) );
        labelColour_3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelColour_3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelColour_3.SetBorderSize( 1 );
        labelColour_3.SetMultiLineEdit( false );
        labelColour_3.SetIsNumberEditor( false );
        labelColour_3.SetNumberEditorRange( 0, 100 );
        labelColour_3.SetNumberEditorInterval( 1 );
        labelColour_3.SetNumberEditorUsesMouseWheel( false );
        labelColour_3.SetHasCustomTextHoverColor( false );
        labelColour_3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelColour_3.SetFont( "Arial", 7, true, false );

        labelColour_4 = new VoltageLabel( "labelColour_4", "Colour Label_4", this, "SPEED" );
        AddComponent( labelColour_4 );
        labelColour_4.SetWantsMouseNotifications( false );
        labelColour_4.SetPosition( 13, 86 );
        labelColour_4.SetSize( 39, 13 );
        labelColour_4.SetEditable( false, false );
        labelColour_4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelColour_4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelColour_4.SetColor( new Color( 42, 42, 42, 255 ) );
        labelColour_4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelColour_4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelColour_4.SetBorderSize( 1 );
        labelColour_4.SetMultiLineEdit( false );
        labelColour_4.SetIsNumberEditor( false );
        labelColour_4.SetNumberEditorRange( 0, 100 );
        labelColour_4.SetNumberEditorInterval( 1 );
        labelColour_4.SetNumberEditorUsesMouseWheel( false );
        labelColour_4.SetHasCustomTextHoverColor( false );
        labelColour_4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelColour_4.SetFont( "Arial", 7, true, false );

        outputAnalog = new VoltageAudioJack( "outputAnalog", "Analog Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputAnalog );
        outputAnalog.SetWantsMouseNotifications( false );
        outputAnalog.SetPosition( 45, 224 );
        outputAnalog.SetSize( 25, 25 );
        outputAnalog.SetSkin( "Mini Jack 25px" );

        outputDigital = new VoltageAudioJack( "outputDigital", "Digital Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputDigital );
        outputDigital.SetWantsMouseNotifications( false );
        outputDigital.SetPosition( 70, 111 );
        outputDigital.SetSize( 25, 25 );
        outputDigital.SetSkin( "Mini Jack 25px" );

        outputBlend = new VoltageAudioJack( "outputBlend", "Blend Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputBlend );
        outputBlend.SetWantsMouseNotifications( false );
        outputBlend.SetPosition( 70, 288 );
        outputBlend.SetSize( 25, 25 );
        outputBlend.SetSkin( "Mini Jack 25px" );

        knobMix = new VoltageKnob( "knobMix", "Mix", this, -1.0, 1.0, -1 );
        AddComponent( knobMix );
        knobMix.SetWantsMouseNotifications( false );
        knobMix.SetPosition( 20, 288 );
        knobMix.SetSize( 25, 25 );
        knobMix.SetSkin( "ARP2500 Sm Silver" );
        knobMix.SetRange( -1.0, 1.0, -1, false, 0 );
        knobMix.SetKnobParams( 215, 145 );
        knobMix.DisplayValueInPercent( false );
        knobMix.SetKnobAdjustsRing( true );

        labelMix = new VoltageLabel( "labelMix", "Mix Label", this, "MIX" );
        AddComponent( labelMix );
        labelMix.SetWantsMouseNotifications( false );
        labelMix.SetPosition( 13, 313 );
        labelMix.SetSize( 39, 13 );
        labelMix.SetEditable( false, false );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMix.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMix.SetColor( new Color( 42, 42, 42, 255 ) );
        labelMix.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMix.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMix.SetBorderSize( 1 );
        labelMix.SetMultiLineEdit( false );
        labelMix.SetIsNumberEditor( false );
        labelMix.SetNumberEditorRange( 0, 100 );
        labelMix.SetNumberEditorInterval( 1 );
        labelMix.SetNumberEditorUsesMouseWheel( false );
        labelMix.SetHasCustomTextHoverColor( false );
        labelMix.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMix.SetFont( "Arial", 7, true, false );

        labelBlendOut = new VoltageLabel( "labelBlendOut", "Blend Out Label", this, "OUT" );
        AddComponent( labelBlendOut );
        labelBlendOut.SetWantsMouseNotifications( false );
        labelBlendOut.SetPosition( 62, 313 );
        labelBlendOut.SetSize( 39, 13 );
        labelBlendOut.SetEditable( false, false );
        labelBlendOut.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBlendOut.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBlendOut.SetColor( new Color( 42, 42, 42, 255 ) );
        labelBlendOut.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBlendOut.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBlendOut.SetBorderSize( 1 );
        labelBlendOut.SetMultiLineEdit( false );
        labelBlendOut.SetIsNumberEditor( false );
        labelBlendOut.SetNumberEditorRange( 0, 100 );
        labelBlendOut.SetNumberEditorInterval( 1 );
        labelBlendOut.SetNumberEditorUsesMouseWheel( false );
        labelBlendOut.SetHasCustomTextHoverColor( false );
        labelBlendOut.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBlendOut.SetFont( "Arial", 7, true, false );

        labelMixOut_1 = new VoltageLabel( "labelMixOut_1", "Digital Out Label", this, "OUT" );
        AddComponent( labelMixOut_1 );
        labelMixOut_1.SetWantsMouseNotifications( false );
        labelMixOut_1.SetPosition( 63, 136 );
        labelMixOut_1.SetSize( 39, 13 );
        labelMixOut_1.SetEditable( false, false );
        labelMixOut_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMixOut_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMixOut_1.SetColor( new Color( 42, 42, 42, 255 ) );
        labelMixOut_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMixOut_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMixOut_1.SetBorderSize( 1 );
        labelMixOut_1.SetMultiLineEdit( false );
        labelMixOut_1.SetIsNumberEditor( false );
        labelMixOut_1.SetNumberEditorRange( 0, 100 );
        labelMixOut_1.SetNumberEditorInterval( 1 );
        labelMixOut_1.SetNumberEditorUsesMouseWheel( false );
        labelMixOut_1.SetHasCustomTextHoverColor( false );
        labelMixOut_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMixOut_1.SetFont( "Arial", 7, true, false );

        labelAnalogOut = new VoltageLabel( "labelAnalogOut", "Analog Out Label", this, "OUT" );
        AddComponent( labelAnalogOut );
        labelAnalogOut.SetWantsMouseNotifications( false );
        labelAnalogOut.SetPosition( 38, 249 );
        labelAnalogOut.SetSize( 39, 13 );
        labelAnalogOut.SetEditable( false, false );
        labelAnalogOut.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelAnalogOut.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelAnalogOut.SetColor( new Color( 42, 42, 42, 255 ) );
        labelAnalogOut.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelAnalogOut.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelAnalogOut.SetBorderSize( 1 );
        labelAnalogOut.SetMultiLineEdit( false );
        labelAnalogOut.SetIsNumberEditor( false );
        labelAnalogOut.SetNumberEditorRange( 0, 100 );
        labelAnalogOut.SetNumberEditorInterval( 1 );
        labelAnalogOut.SetNumberEditorUsesMouseWheel( false );
        labelAnalogOut.SetHasCustomTextHoverColor( false );
        labelAnalogOut.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelAnalogOut.SetFont( "Arial", 7, true, false );

        labelDigitalRed = new VoltageLabel( "labelDigitalRed", "Red Digital Label", this, "–—  DIGITAL  —–" );
        AddComponent( labelDigitalRed );
        labelDigitalRed.SetWantsMouseNotifications( false );
        labelDigitalRed.SetPosition( 0, 35 );
        labelDigitalRed.SetSize( 115, 20 );
        labelDigitalRed.SetEditable( false, false );
        labelDigitalRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDigitalRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDigitalRed.SetColor( new Color( 164, 42, 42, 255 ) );
        labelDigitalRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDigitalRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDigitalRed.SetBorderSize( 1 );
        labelDigitalRed.SetMultiLineEdit( false );
        labelDigitalRed.SetIsNumberEditor( false );
        labelDigitalRed.SetNumberEditorRange( 0, 100 );
        labelDigitalRed.SetNumberEditorInterval( 1 );
        labelDigitalRed.SetNumberEditorUsesMouseWheel( false );
        labelDigitalRed.SetHasCustomTextHoverColor( false );
        labelDigitalRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDigitalRed.SetFont( "Arial", 13, true, false );

        labelDigitalBlack = new VoltageLabel( "labelDigitalBlack", "Black Digital Label", this, "DIGITAL" );
        AddComponent( labelDigitalBlack );
        labelDigitalBlack.SetWantsMouseNotifications( false );
        labelDigitalBlack.SetPosition( 0, 35 );
        labelDigitalBlack.SetSize( 115, 20 );
        labelDigitalBlack.SetEditable( false, false );
        labelDigitalBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDigitalBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDigitalBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelDigitalBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDigitalBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDigitalBlack.SetBorderSize( 1 );
        labelDigitalBlack.SetMultiLineEdit( false );
        labelDigitalBlack.SetIsNumberEditor( false );
        labelDigitalBlack.SetNumberEditorRange( 0, 100 );
        labelDigitalBlack.SetNumberEditorInterval( 1 );
        labelDigitalBlack.SetNumberEditorUsesMouseWheel( false );
        labelDigitalBlack.SetHasCustomTextHoverColor( false );
        labelDigitalBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDigitalBlack.SetFont( "Arial", 13, true, false );

        labelDigitalRed_1 = new VoltageLabel( "labelDigitalRed_1", "Red Digital Label_1", this, "–—  ANALOG  —–" );
        AddComponent( labelDigitalRed_1 );
        labelDigitalRed_1.SetWantsMouseNotifications( false );
        labelDigitalRed_1.SetPosition( 0, 155 );
        labelDigitalRed_1.SetSize( 115, 20 );
        labelDigitalRed_1.SetEditable( false, false );
        labelDigitalRed_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDigitalRed_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDigitalRed_1.SetColor( new Color( 164, 42, 42, 255 ) );
        labelDigitalRed_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDigitalRed_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDigitalRed_1.SetBorderSize( 1 );
        labelDigitalRed_1.SetMultiLineEdit( false );
        labelDigitalRed_1.SetIsNumberEditor( false );
        labelDigitalRed_1.SetNumberEditorRange( 0, 100 );
        labelDigitalRed_1.SetNumberEditorInterval( 1 );
        labelDigitalRed_1.SetNumberEditorUsesMouseWheel( false );
        labelDigitalRed_1.SetHasCustomTextHoverColor( false );
        labelDigitalRed_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDigitalRed_1.SetFont( "Arial", 13, true, false );

        labelDigitalBlack_1 = new VoltageLabel( "labelDigitalBlack_1", "Black Digital Label_1", this, "ANALOG" );
        AddComponent( labelDigitalBlack_1 );
        labelDigitalBlack_1.SetWantsMouseNotifications( false );
        labelDigitalBlack_1.SetPosition( 0, 155 );
        labelDigitalBlack_1.SetSize( 115, 20 );
        labelDigitalBlack_1.SetEditable( false, false );
        labelDigitalBlack_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDigitalBlack_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDigitalBlack_1.SetColor( new Color( 26, 26, 26, 255 ) );
        labelDigitalBlack_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDigitalBlack_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDigitalBlack_1.SetBorderSize( 1 );
        labelDigitalBlack_1.SetMultiLineEdit( false );
        labelDigitalBlack_1.SetIsNumberEditor( false );
        labelDigitalBlack_1.SetNumberEditorRange( 0, 100 );
        labelDigitalBlack_1.SetNumberEditorInterval( 1 );
        labelDigitalBlack_1.SetNumberEditorUsesMouseWheel( false );
        labelDigitalBlack_1.SetHasCustomTextHoverColor( false );
        labelDigitalBlack_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDigitalBlack_1.SetFont( "Arial", 13, true, false );

        labelDigitalRed_3 = new VoltageLabel( "labelDigitalRed_3", "Red Digital Label_3", this, "——  BLEND  ——" );
        AddComponent( labelDigitalRed_3 );
        labelDigitalRed_3.SetWantsMouseNotifications( false );
        labelDigitalRed_3.SetPosition( 0, 260 );
        labelDigitalRed_3.SetSize( 115, 20 );
        labelDigitalRed_3.SetEditable( false, false );
        labelDigitalRed_3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDigitalRed_3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDigitalRed_3.SetColor( new Color( 164, 42, 42, 255 ) );
        labelDigitalRed_3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDigitalRed_3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDigitalRed_3.SetBorderSize( 1 );
        labelDigitalRed_3.SetMultiLineEdit( false );
        labelDigitalRed_3.SetIsNumberEditor( false );
        labelDigitalRed_3.SetNumberEditorRange( 0, 100 );
        labelDigitalRed_3.SetNumberEditorInterval( 1 );
        labelDigitalRed_3.SetNumberEditorUsesMouseWheel( false );
        labelDigitalRed_3.SetHasCustomTextHoverColor( false );
        labelDigitalRed_3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDigitalRed_3.SetFont( "Arial", 13, true, false );

        labelDigitalBlack_3 = new VoltageLabel( "labelDigitalBlack_3", "Black Digital Label_3", this, "BLEND" );
        AddComponent( labelDigitalBlack_3 );
        labelDigitalBlack_3.SetWantsMouseNotifications( false );
        labelDigitalBlack_3.SetPosition( 0, 260 );
        labelDigitalBlack_3.SetSize( 115, 20 );
        labelDigitalBlack_3.SetEditable( false, false );
        labelDigitalBlack_3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDigitalBlack_3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDigitalBlack_3.SetColor( new Color( 26, 26, 26, 255 ) );
        labelDigitalBlack_3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDigitalBlack_3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDigitalBlack_3.SetBorderSize( 1 );
        labelDigitalBlack_3.SetMultiLineEdit( false );
        labelDigitalBlack_3.SetIsNumberEditor( false );
        labelDigitalBlack_3.SetNumberEditorRange( 0, 100 );
        labelDigitalBlack_3.SetNumberEditorInterval( 1 );
        labelDigitalBlack_3.SetNumberEditorUsesMouseWheel( false );
        labelDigitalBlack_3.SetHasCustomTextHoverColor( false );
        labelDigitalBlack_3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDigitalBlack_3.SetFont( "Arial", 13, true, false );

        lableBlendMixDot_1 = new VoltageLabel( "lableBlendMixDot_1", "Middle Dot Blend Mix_1", this, "D" );
        AddComponent( lableBlendMixDot_1 );
        lableBlendMixDot_1.SetWantsMouseNotifications( false );
        lableBlendMixDot_1.SetPosition( 6, 300 );
        lableBlendMixDot_1.SetSize( 20, 13 );
        lableBlendMixDot_1.SetEditable( false, false );
        lableBlendMixDot_1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_1.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_1.SetBorderSize( 1 );
        lableBlendMixDot_1.SetMultiLineEdit( false );
        lableBlendMixDot_1.SetIsNumberEditor( false );
        lableBlendMixDot_1.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_1.SetNumberEditorInterval( 1 );
        lableBlendMixDot_1.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_1.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_1.SetFont( "Arial", 8, true, false );

        lableBlendMixDot_2 = new VoltageLabel( "lableBlendMixDot_2", "Middle Dot Blend Mix_2", this, "A" );
        AddComponent( lableBlendMixDot_2 );
        lableBlendMixDot_2.SetWantsMouseNotifications( false );
        lableBlendMixDot_2.SetPosition( 39, 300 );
        lableBlendMixDot_2.SetSize( 19, 13 );
        lableBlendMixDot_2.SetEditable( false, false );
        lableBlendMixDot_2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_2.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_2.SetBorderSize( 1 );
        lableBlendMixDot_2.SetMultiLineEdit( false );
        lableBlendMixDot_2.SetIsNumberEditor( false );
        lableBlendMixDot_2.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_2.SetNumberEditorInterval( 1 );
        lableBlendMixDot_2.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_2.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_2.SetFont( "Arial", 8, true, false );

        lableBlendMixDot_3 = new VoltageLabel( "lableBlendMixDot_3", "Middle Dot Blend Mix_3", this, "·" );
        AddComponent( lableBlendMixDot_3 );
        lableBlendMixDot_3.SetWantsMouseNotifications( false );
        lableBlendMixDot_3.SetPosition( 13, 168 );
        lableBlendMixDot_3.SetSize( 39, 13 );
        lableBlendMixDot_3.SetEditable( false, false );
        lableBlendMixDot_3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_3.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_3.SetBorderSize( 1 );
        lableBlendMixDot_3.SetMultiLineEdit( false );
        lableBlendMixDot_3.SetIsNumberEditor( false );
        lableBlendMixDot_3.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_3.SetNumberEditorInterval( 1 );
        lableBlendMixDot_3.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_3.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_3.SetFont( "Arial", 33, true, false );

        lableBlendMixDot_4 = new VoltageLabel( "lableBlendMixDot_4", "Middle Dot Blend Mix_4", this, "D" );
        AddComponent( lableBlendMixDot_4 );
        lableBlendMixDot_4.SetWantsMouseNotifications( false );
        lableBlendMixDot_4.SetPosition( 6, 190 );
        lableBlendMixDot_4.SetSize( 20, 13 );
        lableBlendMixDot_4.SetEditable( false, false );
        lableBlendMixDot_4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_4.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_4.SetBorderSize( 1 );
        lableBlendMixDot_4.SetMultiLineEdit( false );
        lableBlendMixDot_4.SetIsNumberEditor( false );
        lableBlendMixDot_4.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_4.SetNumberEditorInterval( 1 );
        lableBlendMixDot_4.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_4.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_4.SetFont( "Arial", 8, true, false );

        lableBlendMixDot_5 = new VoltageLabel( "lableBlendMixDot_5", "Middle Dot Blend Mix_5", this, "B" );
        AddComponent( lableBlendMixDot_5 );
        lableBlendMixDot_5.SetWantsMouseNotifications( false );
        lableBlendMixDot_5.SetPosition( 39, 190 );
        lableBlendMixDot_5.SetSize( 19, 13 );
        lableBlendMixDot_5.SetEditable( false, false );
        lableBlendMixDot_5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_5.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_5.SetBorderSize( 1 );
        lableBlendMixDot_5.SetMultiLineEdit( false );
        lableBlendMixDot_5.SetIsNumberEditor( false );
        lableBlendMixDot_5.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_5.SetNumberEditorInterval( 1 );
        lableBlendMixDot_5.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_5.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_5.SetFont( "Arial", 8, true, false );

        lableBlendMixDot_6 = new VoltageLabel( "lableBlendMixDot_6", "Middle Dot Blend Mix_6", this, "·" );
        AddComponent( lableBlendMixDot_6 );
        lableBlendMixDot_6.SetWantsMouseNotifications( false );
        lableBlendMixDot_6.SetPosition( 63, 50 );
        lableBlendMixDot_6.SetSize( 39, 13 );
        lableBlendMixDot_6.SetEditable( false, false );
        lableBlendMixDot_6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_6.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_6.SetBorderSize( 1 );
        lableBlendMixDot_6.SetMultiLineEdit( false );
        lableBlendMixDot_6.SetIsNumberEditor( false );
        lableBlendMixDot_6.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_6.SetNumberEditorInterval( 1 );
        lableBlendMixDot_6.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_6.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_6.SetFont( "Arial", 33, true, false );

        lableBlendMixDot_7 = new VoltageLabel( "lableBlendMixDot_7", "Middle Dot Blend Mix_7", this, "D" );
        AddComponent( lableBlendMixDot_7 );
        lableBlendMixDot_7.SetWantsMouseNotifications( false );
        lableBlendMixDot_7.SetPosition( 56, 72 );
        lableBlendMixDot_7.SetSize( 20, 13 );
        lableBlendMixDot_7.SetEditable( false, false );
        lableBlendMixDot_7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_7.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_7.SetBorderSize( 1 );
        lableBlendMixDot_7.SetMultiLineEdit( false );
        lableBlendMixDot_7.SetIsNumberEditor( false );
        lableBlendMixDot_7.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_7.SetNumberEditorInterval( 1 );
        lableBlendMixDot_7.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_7.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_7.SetFont( "Arial", 8, true, false );

        lableBlendMixDot_8 = new VoltageLabel( "lableBlendMixDot_8", "Middle Dot Blend Mix_8", this, "B" );
        AddComponent( lableBlendMixDot_8 );
        lableBlendMixDot_8.SetWantsMouseNotifications( false );
        lableBlendMixDot_8.SetPosition( 89, 72 );
        lableBlendMixDot_8.SetSize( 19, 13 );
        lableBlendMixDot_8.SetEditable( false, false );
        lableBlendMixDot_8.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        lableBlendMixDot_8.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        lableBlendMixDot_8.SetColor( new Color( 42, 42, 42, 255 ) );
        lableBlendMixDot_8.SetBkColor( new Color( 65, 65, 65, 0 ) );
        lableBlendMixDot_8.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        lableBlendMixDot_8.SetBorderSize( 1 );
        lableBlendMixDot_8.SetMultiLineEdit( false );
        lableBlendMixDot_8.SetIsNumberEditor( false );
        lableBlendMixDot_8.SetNumberEditorRange( 0, 100 );
        lableBlendMixDot_8.SetNumberEditorInterval( 1 );
        lableBlendMixDot_8.SetNumberEditorUsesMouseWheel( false );
        lableBlendMixDot_8.SetHasCustomTextHoverColor( false );
        lableBlendMixDot_8.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        lableBlendMixDot_8.SetFont( "Arial", 8, true, false );
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
        // Seeds belong to this instance; streams continue across recall and bypass.
        analogState = System.nanoTime() ^ ((long) System.identityHashCode(this) << 32);
        initializeReconstruction();
        adoptPending.set(true);

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
        processNoiseSample();

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
        outputAnalog.SetValue(0.0);
        outputDigital.SetValue(0.0);
        outputBlend.SetValue(0.0);
        adoptPending.set(true);

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
        if (component == knobSpeed) {
            int bank = adoptPending.get() ? wordForRate(rateFor(knobSpeed.GetValue())) : activeWord;
            return String.format(Locale.ROOT, "Clock: %.2f Hz; %d-bit sequence (slow 7 / mid 15 / fast 31)",
                    rateFor(knobSpeed.GetValue()), WORD_LENGTHS[bank]);
        }
        if (component == knobColourA || component == knobColourD) {
            double value = component == knobColourA ? knobColourA.GetValue() : knobColourD.GetValue();
            return String.format(Locale.ROOT, "Colour: %+.1f %% (dark / neutral / bright)", safe(value, -1.0, 1.0, 0.0) * 100.0);
        }
        if (component == knobLevelA || component == knobLevelD) {
            double value = component == knobLevelA ? knobLevelA.GetValue() : knobLevelD.GetValue();
            return String.format(Locale.ROOT, "Level: %.1f %% (also feeds Blend)", safe(value, 0.0, 1.0, 0.0) * 100.0);
        }
        if (component == knobMix) {
            return String.format(Locale.ROOT, "Blend: %.1f %% digital; CCW digital / CW analog", (1.0 - safe(knobMix.GetValue(), -1.0, 1.0, -1.0)) * 50.0);
        }
        if (component == outputAnalog) return "EMS-inspired noise after Analog Colour and Level";
        if (component == outputDigital) return "Clocked binary LFSR noise after Digital Colour and Level";
        if (component == outputBlend) return "Equal-power blend of the two level-controlled sources";
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
        if (component == knobSpeed) {
            double hz = safe(newValue, MIN_CLOCK, MAX_CLOCK, rateFor(knobSpeed.GetValue()));
            super.EditComponentValue(component, 2.0 * Math.log(hz / MIN_CLOCK) / LOG_CLOCK_SPAN - 1.0, newText);
            return;
        }
        if (component == knobColourA || component == knobColourD) {
            super.EditComponentValue(component, safe(newValue / 100.0, -1.0, 1.0, 0.0), newText);
            return;
        }
        if (component == knobMix) {
            super.EditComponentValue(component, 1.0 - 2.0 * safe(newValue / 100.0, 0.0, 1.0, 1.0), newText);
            return;
        }
        if (component == knobLevelA || component == knobLevelD) {
            super.EditComponentValue(component, safe(newValue / 100.0, 0.0, 1.0, 0.0), newText);
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
        // Native controls are restored by Designer; transient noise is never rewound.
        adoptPending.set(true);

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
    private VoltageLabel lableBlendMixDot_8;
    private VoltageLabel lableBlendMixDot_7;
    private VoltageLabel lableBlendMixDot_6;
    private VoltageLabel lableBlendMixDot_5;
    private VoltageLabel lableBlendMixDot_4;
    private VoltageLabel lableBlendMixDot_3;
    private VoltageLabel lableBlendMixDot_2;
    private VoltageLabel lableBlendMixDot_1;
    private VoltageLabel labelDigitalBlack_3;
    private VoltageLabel labelDigitalRed_3;
    private VoltageLabel labelDigitalBlack_1;
    private VoltageLabel labelDigitalRed_1;
    private VoltageLabel labelDigitalBlack;
    private VoltageLabel labelDigitalRed;
    private VoltageLabel labelAnalogOut;
    private VoltageLabel labelMixOut_1;
    private VoltageLabel labelBlendOut;
    private VoltageLabel labelMix;
    private VoltageKnob knobMix;
    private VoltageAudioJack outputBlend;
    private VoltageAudioJack outputDigital;
    private VoltageAudioJack outputAnalog;
    private VoltageLabel labelColour_4;
    private VoltageLabel labelColour_3;
    private VoltageLabel labelColourD;
    private VoltageKnob knobColourD;
    private VoltageKnob knobLevelD;
    private VoltageKnob knobSpeed;
    private VoltageLabel labelColour_1;
    private VoltageLabel labelColourA;
    private VoltageKnob knobLevelA;
    private VoltageKnob knobColourA;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel lableBlendMixDot;


    //[user-code-and-variables]    Add your own variables and functions here
    // Approved model choices and simplifications are documented in DSP-DESIGN.md.
    private static final double SAMPLE_RATE = 48000.0;
    private static final int OVERSAMPLE = 4;
    private static final double INTERNAL_RATE = SAMPLE_RATE * OVERSAMPLE;
    private static final double MIN_CLOCK = 20.0;
    private static final double MAX_CLOCK = 48000.0;
    private static final double LOG_CLOCK_SPAN = Math.log(MAX_CLOCK / MIN_CLOCK);
    private static final double SMOOTH = 1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE));
    // Complementary low/high colour branches; 2.7k and 47nF are EMS drawing values.
    private static final double COLOUR_CORNER = 1.0 / (2.0 * Math.PI * 2700.0 * 47.0e-9);
    private static final double COLOUR_POLE = 1.0 - Math.exp(-2.0 * Math.PI * COLOUR_CORNER / SAMPLE_RATE);
    private static final double ANALOG_DC_POLE = Math.exp(-2.0 * Math.PI * 20.0 / SAMPLE_RATE);
    private static final double DIGITAL_DC_POLE = Math.exp(-2.0 * Math.PI * 5.0 / SAMPLE_RATE);
    private static final int[] WORD_LENGTHS = {7, 15, 31};
    // Equal thirds of the logarithmic SPEED travel; 1% travel hysteresis either side.
    private static final double[] WORD_BOUNDARIES = {rateFor(-1.0 / 3.0), rateFor(1.0 / 3.0)};
    private static final double WORD_HYSTERESIS = Math.exp(LOG_CLOCK_SPAN * 0.01);
    // Right-shifting XOR Galois form; polynomial terms 7/6, 15/14, 31/28.
    private static final int[] FEEDBACK = {0x60, 0x6000, 0x48000000};
    private final int[] lfsr = {0x53, 0x51A3, 0x53A9B471};
    private final double[] wordWeight = new double[3];
    private static final int FIR_LENGTH = 127;
    private final double[] reconstruction = new double[FIR_LENGTH];
    private final double[] bitHistory = new double[FIR_LENGTH];
    private final AtomicBoolean adoptPending = new AtomicBoolean(true);
    private int writeIndex;
    private volatile int activeWord; // Published for the UI tooltip; DSP owns writes.
    private int wordFadeRemaining;
    private long analogState;
    private double clockPhase;
    private double clockHz = 1000.0;
    private double lastSpeed = Double.NaN;
    private double targetClock;
    private double analogColour, digitalColour, analogLevel, digitalLevel;
    private double mixAnalog, mixDigital, lastMix = Double.NaN, targetMixAnalog, targetMixDigital;
    private double analogColourLow, digitalColourLow;
    private double analogDcIn, analogDcOut, digitalDcIn, digitalDcOut;
    private double analogLow1, analogLow2;
    private double lpB0, lpB1, lpB2, lpA1, lpA2;

    private static double safe(double value, double low, double high, double fallback) {
        return Math.max(low, Math.min(high, Double.isFinite(value) ? value : fallback));
    }

    private static int wordForRate(double rate) {
        return rate < WORD_BOUNDARIES[0] ? 0 : rate < WORD_BOUNDARIES[1] ? 1 : 2;
    }

    private static int wordForRate(double rate, int previous) {
        int bank = previous;
        while (bank < 2 && rate > WORD_BOUNDARIES[bank] * WORD_HYSTERESIS) ++bank;
        while (bank > 0 && rate < WORD_BOUNDARIES[bank - 1] / WORD_HYSTERESIS) --bank;
        return bank;
    }

    private static double rateFor(double position) {
        return MIN_CLOCK * Math.exp(LOG_CLOCK_SPAN * (safe(position, -1.0, 1.0, 0.0) + 1.0) * 0.5);
    }

    private static int advanceLfsr(int state, int mask) {
        // XOR has an all-zero trap; seeds are nonzero and transitions preserve that.
        return (state >>> 1) ^ (-(state & 1) & mask);
    }

    private void initializeReconstruction() {
        // Blackman-windowed sinc at 192 kHz, evaluated only once during initialization.
        double sum = 0.0;
        double cutoff = 18000.0 / INTERNAL_RATE;
        for (int i = 0; i < FIR_LENGTH; ++i) {
            double offset = i - (FIR_LENGTH - 1) * 0.5;
            double sinc = offset == 0.0 ? 2.0 * cutoff
                    : Math.sin(2.0 * Math.PI * cutoff * offset) / (Math.PI * offset);
            double window = 0.42 - 0.5 * Math.cos(2.0 * Math.PI * i / (FIR_LENGTH - 1))
                    + 0.08 * Math.cos(4.0 * Math.PI * i / (FIR_LENGTH - 1));
            reconstruction[i] = sinc * window;
            sum += reconstruction[i];
        }
        for (int i = 0; i < FIR_LENGTH; ++i) reconstruction[i] /= sum;
        // Linear bandwidth limit for the analog innovation; no generic aging stage.
        double w = 2.0 * Math.PI * 16000.0 / SAMPLE_RATE;
        double cosine = Math.cos(w), alpha = Math.sin(w) / Math.sqrt(2.0);
        double a0 = 1.0 + alpha;
        lpB0 = (1.0 - cosine) * 0.5 / a0;
        lpB1 = (1.0 - cosine) / a0;
        lpB2 = lpB0;
        lpA1 = -2.0 * cosine / a0;
        lpA2 = (1.0 - alpha) / a0;
    }

    private double analogInnovation() {
        // Six independent uniforms approximate a centered Gaussian, with unit variance.
        double sum = 0.0;
        for (int i = 0; i < 6; ++i) {
            analogState += 0x9E3779B97F4A7C15L;
            long z = analogState;
            z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
            z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
            z ^= z >>> 31;
            sum += (z >>> 11) * 0x1.0p-53 - 0.5;
        }
        return sum * Math.sqrt(2.0);
    }

    private double currentBits() {
        return wordWeight[0] * ((lfsr[0] & 1) * 2.0 - 1.0)
                + wordWeight[1] * ((lfsr[1] & 1) * 2.0 - 1.0)
                + wordWeight[2] * ((lfsr[2] & 1) * 2.0 - 1.0);
    }

    private double digitalSample() {
        double increment = clockHz / INTERNAL_RATE;
        for (int tick = 0; tick < OVERSAMPLE; ++tick) {
            double value = currentBits();
            double nextPhase = clockPhase + increment;
            if (nextPhase >= 1.0) {
                // Average the two held bit values over the fractional edge interval.
                double before = (1.0 - clockPhase) / increment;
                for (int bank = 0; bank < 3; ++bank) lfsr[bank] = advanceLfsr(lfsr[bank], FEEDBACK[bank]);
                value = before * value + (1.0 - before) * currentBits();
                nextPhase -= 1.0;
            }
            clockPhase = nextPhase;
            bitHistory[writeIndex] = value;
            if (++writeIndex == FIR_LENGTH) writeIndex = 0;
        }
        double result = 0.0;
        int index = writeIndex;
        for (int tap = 0; tap < FIR_LENGTH; ++tap) {
            if (--index < 0) index = FIR_LENGTH - 1;
            result += reconstruction[tap] * bitHistory[index];
        }
        return result;
    }

    private void processNoiseSample() {
        double speed = safe(knobSpeed.GetValue(), -1.0, 1.0, 0.0);
        double mix = (1.0 - safe(knobMix.GetValue(), -1.0, 1.0, -1.0)) * 0.5;
        if (speed != lastSpeed) { lastSpeed = speed; targetClock = rateFor(speed); }
        if (mix != lastMix) {
            lastMix = mix;
            targetMixAnalog = Math.cos(mix * Math.PI * 0.5);
            targetMixDigital = Math.sin(mix * Math.PI * 0.5);
            if (mix == 0.0) targetMixDigital = 0.0;
            if (mix == 1.0) targetMixAnalog = 0.0;
        }
        double colourA = safe(knobColourA.GetValue(), -1.0, 1.0, 0.0);
        double colourD = safe(knobColourD.GetValue(), -1.0, 1.0, 0.0);
        double levelA = safe(knobLevelA.GetValue(), 0.0, 1.0, 0.0);
        double levelD = safe(knobLevelD.GetValue(), 0.0, 1.0, 0.0);
        int selected = wordForRate(targetClock);
        if (adoptPending.getAndSet(false)) {
            clockHz = targetClock;
            analogColour = colourA; digitalColour = colourD;
            analogLevel = levelA; digitalLevel = levelD;
            mixAnalog = targetMixAnalog; mixDigital = targetMixDigital;
            activeWord = selected; wordFadeRemaining = 0;
            for (int i = 0; i < 3; ++i) wordWeight[i] = i == selected ? 1.0 : 0.0;
        } else {
            clockHz += SMOOTH * (targetClock - clockHz);
            selected = wordForRate(clockHz, activeWord);
            analogColour += SMOOTH * (colourA - analogColour);
            digitalColour += SMOOTH * (colourD - digitalColour);
            analogLevel += SMOOTH * (levelA - analogLevel);
            digitalLevel += SMOOTH * (levelD - digitalLevel);
            mixAnalog += SMOOTH * (targetMixAnalog - mixAnalog);
            mixDigital += SMOOTH * (targetMixDigital - mixDigital);
            if (selected != activeWord) {
                activeWord = selected;
                wordFadeRemaining = 240;
            }
            if (wordFadeRemaining > 0) {
                for (int i = 0; i < 3; ++i) {
                    double target = i == selected ? 1.0 : 0.0;
                    wordWeight[i] += (target - wordWeight[i]) / wordFadeRemaining;
                }
                --wordFadeRemaining;
            }
        }
        double raw = analogInnovation();
        double limited = lpB0 * raw + analogLow1;
        analogLow1 = lpB1 * raw - lpA1 * limited + analogLow2;
        analogLow2 = lpB2 * raw - lpA2 * limited;
        double analog = limited - analogDcIn + ANALOG_DC_POLE * analogDcOut;
        analogDcIn = limited; analogDcOut = analog;
        analogColourLow += COLOUR_POLE * (analog - analogColourLow);
        analog += analogColour * (analog - 2.0 * analogColourLow);
        double bitstream = digitalSample();
        double digital = bitstream - digitalDcIn + DIGITAL_DC_POLE * digitalDcOut;
        digitalDcIn = bitstream; digitalDcOut = digital;
        digitalColourLow += COLOUR_POLE * (digital - digitalColourLow);
        digital += digitalColour * (digital - 2.0 * digitalColourLow);
        analog *= analogLevel;
        digital *= digitalLevel;
        outputAnalog.SetValue(analog);
        outputDigital.SetValue(digital);
        outputBlend.SetValue(mixAnalog * analog + mixDigital * digital);
    }

    //[/user-code-and-variables]
}

 