package com.insectlabs.sn93t;


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


public class sn93t extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape")
    public sn93t( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "sn93t", ModuleType.ModuleType_Utility, 3.2 );

        InitializeControls();
        InitializeControls2();


        canBeBypassed = true;
        SetSkin( "98e43ae354904f41853bf3ce537ef171" );
    }

void InitializeControls()
{

        labelC = new VoltageLabel( "labelC", "C Label", this, "C" );
        AddComponent( labelC );
        labelC.SetWantsMouseNotifications( false );
        labelC.SetPosition( 172, 162 );
        labelC.SetSize( 10, 10 );
        labelC.SetEditable( false, false );
        labelC.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelC.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelC.SetColor( new Color( 230, 225, 211, 255 ) );
        labelC.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelC.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelC.SetBorderSize( 1 );
        labelC.SetMultiLineEdit( false );
        labelC.SetIsNumberEditor( false );
        labelC.SetNumberEditorRange( 0, 100 );
        labelC.SetNumberEditorInterval( 1 );
        labelC.SetNumberEditorUsesMouseWheel( false );
        labelC.SetHasCustomTextHoverColor( false );
        labelC.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelC.SetFont( "Arial", 4, true, false );

        label0 = new VoltageLabel( "label0", "0 Label", this, "0" );
        AddComponent( label0 );
        label0.SetWantsMouseNotifications( false );
        label0.SetPosition( 72, 207 );
        label0.SetSize( 10, 10 );
        label0.SetEditable( false, false );
        label0.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label0.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label0.SetColor( new Color( 166, 36, 36, 255 ) );
        label0.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label0.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label0.SetBorderSize( 1 );
        label0.SetMultiLineEdit( false );
        label0.SetIsNumberEditor( false );
        label0.SetNumberEditorRange( 0, 100 );
        label0.SetNumberEditorInterval( 1 );
        label0.SetNumberEditorUsesMouseWheel( false );
        label0.SetHasCustomTextHoverColor( false );
        label0.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label0.SetFont( "Arial", 4, true, false );

        label2Oct = new VoltageLabel( "label2Oct", "2 Octaves Label", this, "2" );
        AddComponent( label2Oct );
        label2Oct.SetWantsMouseNotifications( false );
        label2Oct.SetPosition( 173, 207 );
        label2Oct.SetSize( 10, 10 );
        label2Oct.SetEditable( false, false );
        label2Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label2Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label2Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label2Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label2Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label2Oct.SetBorderSize( 1 );
        label2Oct.SetMultiLineEdit( false );
        label2Oct.SetIsNumberEditor( false );
        label2Oct.SetNumberEditorRange( 0, 100 );
        label2Oct.SetNumberEditorInterval( 1 );
        label2Oct.SetNumberEditorUsesMouseWheel( false );
        label2Oct.SetHasCustomTextHoverColor( false );
        label2Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label2Oct.SetFont( "Arial", 4, true, false );

        labelCsharp = new VoltageLabel( "labelCsharp", "C♯ Label", this, "C♯" );
        AddComponent( labelCsharp );
        labelCsharp.SetWantsMouseNotifications( false );
        labelCsharp.SetPosition( 169, 157 );
        labelCsharp.SetSize( 10, 10 );
        labelCsharp.SetEditable( false, false );
        labelCsharp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCsharp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCsharp.SetColor( new Color( 230, 225, 211, 255 ) );
        labelCsharp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCsharp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCsharp.SetBorderSize( 1 );
        labelCsharp.SetMultiLineEdit( false );
        labelCsharp.SetIsNumberEditor( false );
        labelCsharp.SetNumberEditorRange( 0, 100 );
        labelCsharp.SetNumberEditorInterval( 1 );
        labelCsharp.SetNumberEditorUsesMouseWheel( false );
        labelCsharp.SetHasCustomTextHoverColor( false );
        labelCsharp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCsharp.SetFont( "Arial", 4, true, false );

        labelDotTun = new VoltageLabel( "labelDotTun", "Custom .tun Label", this, ".TUN" );
        AddComponent( labelDotTun );
        labelDotTun.SetWantsMouseNotifications( false );
        labelDotTun.SetPosition( 146, 162 );
        labelDotTun.SetSize( 20, 10 );
        labelDotTun.SetEditable( false, false );
        labelDotTun.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDotTun.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDotTun.SetColor( new Color( 230, 225, 211, 255 ) );
        labelDotTun.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDotTun.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDotTun.SetBorderSize( 1 );
        labelDotTun.SetMultiLineEdit( false );
        labelDotTun.SetIsNumberEditor( false );
        labelDotTun.SetNumberEditorRange( 0, 100 );
        labelDotTun.SetNumberEditorInterval( 1 );
        labelDotTun.SetNumberEditorUsesMouseWheel( false );
        labelDotTun.SetHasCustomTextHoverColor( false );
        labelDotTun.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDotTun.SetFont( "Arial", 4, true, false );

        label12tet = new VoltageLabel( "label12tet", "12-TET Label", this, "12-TET" );
        AddComponent( label12tet );
        label12tet.SetWantsMouseNotifications( false );
        label12tet.SetPosition( 114, 162 );
        label12tet.SetSize( 20, 10 );
        label12tet.SetEditable( false, false );
        label12tet.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label12tet.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label12tet.SetColor( new Color( 230, 225, 211, 255 ) );
        label12tet.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label12tet.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label12tet.SetBorderSize( 1 );
        label12tet.SetMultiLineEdit( false );
        label12tet.SetIsNumberEditor( false );
        label12tet.SetNumberEditorRange( 0, 100 );
        label12tet.SetNumberEditorInterval( 1 );
        label12tet.SetNumberEditorUsesMouseWheel( false );
        label12tet.SetHasCustomTextHoverColor( false );
        label12tet.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label12tet.SetFont( "Arial", 4, true, false );

        labelManual = new VoltageLabel( "labelManual", "Manual Label", this, "MANUAL" );
        AddComponent( labelManual );
        labelManual.SetWantsMouseNotifications( false );
        labelManual.SetPosition( 194, 266 );
        labelManual.SetSize( 25, 15 );
        labelManual.SetEditable( false, false );
        labelManual.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelManual.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelManual.SetColor( new Color( 230, 225, 211, 255 ) );
        labelManual.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelManual.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelManual.SetBorderSize( 1 );
        labelManual.SetMultiLineEdit( false );
        labelManual.SetIsNumberEditor( false );
        labelManual.SetNumberEditorRange( 0, 100 );
        labelManual.SetNumberEditorInterval( 1 );
        labelManual.SetNumberEditorUsesMouseWheel( false );
        labelManual.SetHasCustomTextHoverColor( false );
        labelManual.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelManual.SetFont( "Arial", 5, true, false );

        labelFollow = new VoltageLabel( "labelFollow", "Follow Label", this, "FOLLOW" );
        AddComponent( labelFollow );
        labelFollow.SetWantsMouseNotifications( false );
        labelFollow.SetPosition( 194, 236 );
        labelFollow.SetSize( 25, 15 );
        labelFollow.SetEditable( false, false );
        labelFollow.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFollow.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFollow.SetColor( new Color( 230, 225, 211, 255 ) );
        labelFollow.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFollow.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFollow.SetBorderSize( 1 );
        labelFollow.SetMultiLineEdit( false );
        labelFollow.SetIsNumberEditor( false );
        labelFollow.SetNumberEditorRange( 0, 100 );
        labelFollow.SetNumberEditorInterval( 1 );
        labelFollow.SetNumberEditorUsesMouseWheel( false );
        labelFollow.SetHasCustomTextHoverColor( false );
        labelFollow.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFollow.SetFont( "Arial", 5, true, false );

        labelFooter = new VoltageLabel( "labelFooter", "", this, "(r)aza(kraft) by thee teletape workshop" );
        AddComponent( labelFooter );
        labelFooter.SetWantsMouseNotifications( false );
        labelFooter.SetPosition( 3, 332 );
        labelFooter.SetSize( 224, 23 );
        labelFooter.SetEditable( false, false );
        labelFooter.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFooter.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFooter.SetColor( new Color( 166, 36, 36, 255 ) );
        labelFooter.SetBkColor( new Color( 0, 0, 0, 0 ) );
        labelFooter.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFooter.SetBorderSize( 0 );
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

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "sn93t" );
        AddComponent( labelNumber );
        labelNumber.SetWantsMouseNotifications( false );
        labelNumber.SetPosition( 48, 5 );
        labelNumber.SetSize( 177, 13 );
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

        input = new VoltageAudioJack( "input", "Input", this, JackType.JackType_AudioInput );
        AddComponent( input );
        input.SetWantsMouseNotifications( false );
        input.SetPosition( 13, 303 );
        input.SetSize( 25, 25 );
        input.SetSkin( "Jack Round 25px" );

        thruput = new VoltageAudioJack( "thruput", "Throughput", this, JackType.JackType_AudioOutput );
        AddComponent( thruput );
        thruput.SetWantsMouseNotifications( false );
        thruput.SetPosition( 44, 303 );
        thruput.SetSize( 25, 25 );
        thruput.SetSkin( "Mini Jack 25px" );

        outputTone = new VoltageAudioJack( "outputTone", "Tone Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputTone );
        outputTone.SetWantsMouseNotifications( false );
        outputTone.SetPosition( 74, 303 );
        outputTone.SetSize( 25, 25 );
        outputTone.SetSkin( "Mini Jack 25px" );

        outputVoct = new VoltageAudioJack( "outputVoct", "Volt Per Octave Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputVoct );
        outputVoct.SetWantsMouseNotifications( false );
        outputVoct.SetPosition( 104, 303 );
        outputVoct.SetSize( 25, 25 );
        outputVoct.SetSkin( "Mini Jack 25px" );

        outputCents = new VoltageAudioJack( "outputCents", "Cents Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputCents );
        outputCents.SetWantsMouseNotifications( false );
        outputCents.SetPosition( 134, 303 );
        outputCents.SetSize( 25, 25 );
        outputCents.SetSkin( "Mini Jack 25px" );

        outputGate = new VoltageAudioJack( "outputGate", "Gate Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputGate );
        outputGate.SetWantsMouseNotifications( false );
        outputGate.SetPosition( 164, 303 );
        outputGate.SetSize( 25, 25 );
        outputGate.SetSkin( "Mini Jack 25px" );

        outputCal = new VoltageAudioJack( "outputCal", "Calibration CV Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputCal );
        outputCal.SetWantsMouseNotifications( false );
        outputCal.SetPosition( 194, 303 );
        outputCal.SetSize( 25, 25 );
        outputCal.SetSkin( "Mini Jack 25px" );

        labelInput = new VoltageLabel( "labelInput", "Input Label", this, "IN" );
        AddComponent( labelInput );
        labelInput.SetWantsMouseNotifications( false );
        labelInput.SetPosition( 18, 285 );
        labelInput.SetSize( 15, 15 );
        labelInput.SetEditable( false, false );
        labelInput.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelInput.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelInput.SetColor( new Color( 166, 36, 36, 255 ) );
        labelInput.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelInput.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelInput.SetBorderSize( 1 );
        labelInput.SetMultiLineEdit( false );
        labelInput.SetIsNumberEditor( false );
        labelInput.SetNumberEditorRange( 0, 100 );
        labelInput.SetNumberEditorInterval( 1 );
        labelInput.SetNumberEditorUsesMouseWheel( false );
        labelInput.SetHasCustomTextHoverColor( false );
        labelInput.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelInput.SetFont( "Arial", 8, true, false );

        labelThru = new VoltageLabel( "labelThru", "Throughput Label", this, "THRU" );
        AddComponent( labelThru );
        labelThru.SetWantsMouseNotifications( false );
        labelThru.SetPosition( 44, 285 );
        labelThru.SetSize( 25, 15 );
        labelThru.SetEditable( false, false );
        labelThru.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelThru.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelThru.SetColor( new Color( 230, 225, 211, 255 ) );
        labelThru.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelThru.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelThru.SetBorderSize( 1 );
        labelThru.SetMultiLineEdit( false );
        labelThru.SetIsNumberEditor( false );
        labelThru.SetNumberEditorRange( 0, 100 );
        labelThru.SetNumberEditorInterval( 1 );
        labelThru.SetNumberEditorUsesMouseWheel( false );
        labelThru.SetHasCustomTextHoverColor( false );
        labelThru.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelThru.SetFont( "Arial", 8, true, false );

        labelToneOut = new VoltageLabel( "labelToneOut", "Tone Output Label", this, "TONE" );
        AddComponent( labelToneOut );
        labelToneOut.SetWantsMouseNotifications( false );
        labelToneOut.SetPosition( 74, 285 );
        labelToneOut.SetSize( 25, 15 );
        labelToneOut.SetEditable( false, false );
        labelToneOut.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelToneOut.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelToneOut.SetColor( new Color( 230, 225, 211, 255 ) );
        labelToneOut.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelToneOut.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelToneOut.SetBorderSize( 1 );
        labelToneOut.SetMultiLineEdit( false );
        labelToneOut.SetIsNumberEditor( false );
        labelToneOut.SetNumberEditorRange( 0, 100 );
        labelToneOut.SetNumberEditorInterval( 1 );
        labelToneOut.SetNumberEditorUsesMouseWheel( false );
        labelToneOut.SetHasCustomTextHoverColor( false );
        labelToneOut.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelToneOut.SetFont( "Arial", 8, true, false );

        labelVoct = new VoltageLabel( "labelVoct", "Volt Per Octave Label", this, "V/o" );
        AddComponent( labelVoct );
        labelVoct.SetWantsMouseNotifications( false );
        labelVoct.SetPosition( 104, 285 );
        labelVoct.SetSize( 25, 15 );
        labelVoct.SetEditable( false, false );
        labelVoct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelVoct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelVoct.SetColor( new Color( 230, 225, 211, 255 ) );
        labelVoct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelVoct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelVoct.SetBorderSize( 1 );
        labelVoct.SetMultiLineEdit( false );
        labelVoct.SetIsNumberEditor( false );
        labelVoct.SetNumberEditorRange( 0, 100 );
        labelVoct.SetNumberEditorInterval( 1 );
        labelVoct.SetNumberEditorUsesMouseWheel( false );
        labelVoct.SetHasCustomTextHoverColor( false );
        labelVoct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelVoct.SetFont( "Arial", 8, true, false );

        labelCents = new VoltageLabel( "labelCents", "Cents Label", this, "CENTS" );
        AddComponent( labelCents );
        labelCents.SetWantsMouseNotifications( false );
        labelCents.SetPosition( 134, 285 );
        labelCents.SetSize( 25, 15 );
        labelCents.SetEditable( false, false );
        labelCents.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCents.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCents.SetColor( new Color( 230, 225, 211, 255 ) );
        labelCents.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCents.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCents.SetBorderSize( 1 );
        labelCents.SetMultiLineEdit( false );
        labelCents.SetIsNumberEditor( false );
        labelCents.SetNumberEditorRange( 0, 100 );
        labelCents.SetNumberEditorInterval( 1 );
        labelCents.SetNumberEditorUsesMouseWheel( false );
        labelCents.SetHasCustomTextHoverColor( false );
        labelCents.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCents.SetFont( "Arial", 8, true, false );

        labelGate = new VoltageLabel( "labelGate", "Gate Label", this, "GATE" );
        AddComponent( labelGate );
        labelGate.SetWantsMouseNotifications( false );
        labelGate.SetPosition( 164, 285 );
        labelGate.SetSize( 25, 15 );
        labelGate.SetEditable( false, false );
        labelGate.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGate.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGate.SetColor( new Color( 230, 225, 211, 255 ) );
        labelGate.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelGate.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelGate.SetBorderSize( 1 );
        labelGate.SetMultiLineEdit( false );
        labelGate.SetIsNumberEditor( false );
        labelGate.SetNumberEditorRange( 0, 100 );
        labelGate.SetNumberEditorInterval( 1 );
        labelGate.SetNumberEditorUsesMouseWheel( false );
        labelGate.SetHasCustomTextHoverColor( false );
        labelGate.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelGate.SetFont( "Arial", 8, true, false );

        labelCal = new VoltageLabel( "labelCal", "Calibration CV Label", this, "CAL" );
        AddComponent( labelCal );
        labelCal.SetWantsMouseNotifications( false );
        labelCal.SetPosition( 194, 285 );
        labelCal.SetSize( 25, 15 );
        labelCal.SetEditable( false, false );
        labelCal.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCal.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCal.SetColor( new Color( 230, 225, 211, 255 ) );
        labelCal.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCal.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCal.SetBorderSize( 1 );
        labelCal.SetMultiLineEdit( false );
        labelCal.SetIsNumberEditor( false );
        labelCal.SetNumberEditorRange( 0, 100 );
        labelCal.SetNumberEditorInterval( 1 );
        labelCal.SetNumberEditorUsesMouseWheel( false );
        labelCal.SetHasCustomTextHoverColor( false );
        labelCal.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCal.SetFont( "Arial", 8, true, false );

        switchRange = new VoltageSwitch( "switchRange", "Range Switch", this, 3 );
        AddComponent( switchRange );
        switchRange.SetWantsMouseNotifications( false );
        switchRange.SetPosition( 20, 241 );
        switchRange.SetSize( 11, 36 );
        switchRange.SetSkin( "4-State Slide Vertical" );

        labelRangeSwitch = new VoltageLabel( "labelRangeSwitch", "Range Switch Label", this, "RANGE" );
        AddComponent( labelRangeSwitch );
        labelRangeSwitch.SetWantsMouseNotifications( false );
        labelRangeSwitch.SetPosition( 8, 226 );
        labelRangeSwitch.SetSize( 35, 15 );
        labelRangeSwitch.SetEditable( false, false );
        labelRangeSwitch.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRangeSwitch.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRangeSwitch.SetColor( new Color( 230, 225, 212, 255 ) );
        labelRangeSwitch.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRangeSwitch.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRangeSwitch.SetBorderSize( 1 );
        labelRangeSwitch.SetMultiLineEdit( false );
        labelRangeSwitch.SetIsNumberEditor( false );
        labelRangeSwitch.SetNumberEditorRange( 0, 100 );
        labelRangeSwitch.SetNumberEditorInterval( 1 );
        labelRangeSwitch.SetNumberEditorUsesMouseWheel( false );
        labelRangeSwitch.SetHasCustomTextHoverColor( false );
        labelRangeSwitch.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRangeSwitch.SetFont( "Arial", 6, true, false );

        labelSource = new VoltageLabel( "labelSource", "Soutce Label", this, "TONE SOURCE" );
        AddComponent( labelSource );
        labelSource.SetWantsMouseNotifications( false );
        labelSource.SetPosition( 189, 226 );
        labelSource.SetSize( 35, 15 );
        labelSource.SetEditable( false, false );
        labelSource.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSource.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSource.SetColor( new Color( 166, 36, 36, 255 ) );
        labelSource.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSource.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSource.SetBorderSize( 1 );
        labelSource.SetMultiLineEdit( false );
        labelSource.SetIsNumberEditor( false );
        labelSource.SetNumberEditorRange( 0, 100 );
        labelSource.SetNumberEditorInterval( 1 );
        labelSource.SetNumberEditorUsesMouseWheel( false );
        labelSource.SetHasCustomTextHoverColor( false );
        labelSource.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSource.SetFont( "Arial", 6, true, false );

        switchSource = new VoltageSwitch( "switchSource", "Tone Source Switch", this, 1 );
        AddComponent( switchSource );
        switchSource.SetWantsMouseNotifications( false );
        switchSource.SetPosition( 196, 241 );
        switchSource.SetSize( 21, 35 );
        switchSource.SetSkin( "2-State Hex Silver Vert" );

        buttonHold = new VoltageToggle( "buttonHold", "Hold Button", this, false, 0 );
        AddComponent( buttonHold );
        buttonHold.SetWantsMouseNotifications( false );
        buttonHold.SetPosition( 44, 246 );
        buttonHold.SetSize( 25, 25 );
        buttonHold.SetSkin( "2500 Square Big" );
        buttonHold.ShowOverlay( true );
        buttonHold.SetOverlayText( "HOLD" );
        buttonHold.SetOverlayTextFont( "Arial Black", 7, true, false );
        buttonHold.SetOverlayTextColor( new Color( 46, 46, 46 ) );
        buttonHold.SetOverlayArea( 0, 0, 0, 0 );
        buttonHold.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        buttonMute = new VoltageToggle( "buttonMute", "Mute Button", this, false, 0 );
        AddComponent( buttonMute );
        buttonMute.SetWantsMouseNotifications( false );
        buttonMute.SetPosition( 74, 246 );
        buttonMute.SetSize( 25, 25 );
        buttonMute.SetSkin( "2500 Square Big" );
        buttonMute.ShowOverlay( true );
        buttonMute.SetOverlayText( "MUTE" );
        buttonMute.SetOverlayTextFont( "Arial Black", 7, true, false );
        buttonMute.SetOverlayTextColor( new Color( 46, 46, 46 ) );
        buttonMute.SetOverlayArea( 0, 0, 0, 0 );
        buttonMute.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        buttonTone = new VoltageToggle( "buttonTone", "Tone Button", this, false, 0 );
        AddComponent( buttonTone );
        buttonTone.SetWantsMouseNotifications( false );
        buttonTone.SetPosition( 104, 246 );
        buttonTone.SetSize( 25, 25 );
        buttonTone.SetSkin( "2500 Square Big" );
        buttonTone.ShowOverlay( true );
        buttonTone.SetOverlayText( "TONE" );
        buttonTone.SetOverlayTextFont( "Arial Black", 7, true, false );
        buttonTone.SetOverlayTextColor( new Color( 46, 46, 46 ) );
        buttonTone.SetOverlayArea( 0, 0, 0, 0 );
        buttonTone.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        buttonCal = new VoltageToggle( "buttonCal", "Calibration Button", this, false, 0 );
        AddComponent( buttonCal );
        buttonCal.SetWantsMouseNotifications( false );
        buttonCal.SetPosition( 134, 246 );
        buttonCal.SetSize( 25, 25 );
        buttonCal.SetSkin( "2500 Square Big" );
        buttonCal.ShowOverlay( true );
        buttonCal.SetOverlayText( "CAL" );
        buttonCal.SetOverlayTextFont( "Arial Black", 7, true, false );
        buttonCal.SetOverlayTextColor( new Color( 46, 46, 46 ) );
        buttonCal.SetOverlayArea( 0, 0, 0, 0 );
        buttonCal.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        buttonLoad = new VoltageToggle( "buttonLoad", "Load Button", this, false, 0 );
        AddComponent( buttonLoad );
        buttonLoad.SetWantsMouseNotifications( false );
        buttonLoad.SetPosition( 164, 246 );
        buttonLoad.SetSize( 25, 25 );
        buttonLoad.SetSkin( "2500 Square Big" );
        buttonLoad.ShowOverlay( true );
        buttonLoad.SetOverlayText( "LOAD" );
        buttonLoad.SetOverlayTextFont( "Arial Black", 7, false, false );
        buttonLoad.SetOverlayTextColor( new Color( 46, 46, 46 ) );
        buttonLoad.SetOverlayArea( 0, 0, 0, 0 );
        buttonLoad.SetOverlayTextJustification( VoltageButton.Justification.Centered );

        labelRanges = new VoltageLabel( "labelRanges", "Ranges Label", this, "AUTO               |               LOW               |               MID               |               HIGH" );
        AddComponent( labelRanges );
        labelRanges.SetWantsMouseNotifications( false );
        labelRanges.SetPosition( 29, 241 );
        labelRanges.SetSize( 15, 36 );
        labelRanges.SetEditable( false, false );
        labelRanges.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRanges.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRanges.SetColor( new Color( 230, 225, 211, 255 ) );
        labelRanges.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRanges.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRanges.SetBorderSize( 1 );
        labelRanges.SetMultiLineEdit( true );
        labelRanges.SetIsNumberEditor( false );
        labelRanges.SetNumberEditorRange( 0, 100 );
        labelRanges.SetNumberEditorInterval( 1 );
        labelRanges.SetNumberEditorUsesMouseWheel( false );
        labelRanges.SetHasCustomTextHoverColor( false );
        labelRanges.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRanges.SetFont( "Arial", 4, true, false );

        knobTone = new VoltageKnob( "knobTone", "Tone Note Knob", this, 0.0, 1.0, 0.0 );
        AddComponent( knobTone );
        knobTone.SetWantsMouseNotifications( false );
        knobTone.SetPosition( 27, 190 );
        knobTone.SetSize( 25, 25 );
        knobTone.SetSkin( "ARP2500 Sm Red" );
        knobTone.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobTone.SetKnobParams( 215, 145 );
        knobTone.DisplayValueInPercent( false );
        knobTone.SetKnobAdjustsRing( true );

        knob66 = new VoltageKnob( "knob66", "knob66", this, 0, 7, 2 );
        AddComponent( knob66 );
        knob66.SetWantsMouseNotifications( false );
        knob66.SetPosition( 77, 190 );
        knob66.SetSize( 25, 25 );
        knob66.SetSkin( "ARP2500 Sm Red" );
        knob66.SetRange( 0, 7, 2, false, 8 );
        knob66.SetKnobParams( 215, 145 );
        knob66.DisplayValueInPercent( false );
        knob66.SetKnobAdjustsRing( true );

        knobLevel = new VoltageKnob( "knobLevel", "Tone Level Knob", this, 0.0, 1.0, 0.0 );
        AddComponent( knobLevel );
        knobLevel.SetWantsMouseNotifications( false );
        knobLevel.SetPosition( 127, 190 );
        knobLevel.SetSize( 25, 25 );
        knobLevel.SetSkin( "ARP2500 Sm Red" );
        knobLevel.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobLevel.SetKnobParams( 215, 145 );
        knobLevel.DisplayValueInPercent( false );
        knobLevel.SetKnobAdjustsRing( true );

        knob68 = new VoltageKnob( "knob68", "knob68", this, 0, 7, 0 );
        AddComponent( knob68 );
        knob68.SetWantsMouseNotifications( false );
        knob68.SetPosition( 177, 190 );
        knob68.SetSize( 25, 25 );
        knob68.SetSkin( "ARP2500 Sm Silver" );
        knob68.SetRange( 0, 7, 0, false, 8 );
        knob68.SetKnobParams( 215, 145 );
        knob68.DisplayValueInPercent( false );
        knob68.SetKnobAdjustsRing( true );

        knobKey = new VoltageKnob( "knobKey", "Key Knob", this, 1, 12, 1 );
        AddComponent( knobKey );
        knobKey.SetWantsMouseNotifications( false );
        knobKey.SetPosition( 177, 145 );
        knobKey.SetSize( 25, 25 );
        knobKey.SetSkin( "ARP2500 Sm Silver" );
        knobKey.SetRange( 1, 12, 1, false, 12 );
        knobKey.SetKnobParams( 215, 145 );
        knobKey.DisplayValueInPercent( false );
        knobKey.SetKnobAdjustsRing( true );

        knobTemp = new VoltageKnob( "knobTemp", "Temperament Knob", this, 1, 10, 1 );
        AddComponent( knobTemp );
        knobTemp.SetWantsMouseNotifications( false );
        knobTemp.SetPosition( 127, 145 );
        knobTemp.SetSize( 25, 25 );
        knobTemp.SetSkin( "ARP2500 Sm Silver" );
        knobTemp.SetRange( 1, 10, 1, false, 10 );
        knobTemp.SetKnobParams( 215, 145 );
        knobTemp.DisplayValueInPercent( false );
        knobTemp.SetKnobAdjustsRing( true );

        knobResponse = new VoltageKnob( "knobResponse", "Response Knob", this, 0.0, 1.0, 0.5 );
        AddComponent( knobResponse );
        knobResponse.SetWantsMouseNotifications( false );
        knobResponse.SetPosition( 77, 145 );
        knobResponse.SetSize( 25, 25 );
        knobResponse.SetSkin( "ARP2500 Sm Silver" );
        knobResponse.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobResponse.SetKnobParams( 215, 145 );
        knobResponse.DisplayValueInPercent( false );
        knobResponse.SetKnobAdjustsRing( true );

        knobRef = new VoltageKnob( "knobRef", "A4 Reference Knob", this, 380, 480, 440 );
        AddComponent( knobRef );
        knobRef.SetWantsMouseNotifications( false );
        knobRef.SetPosition( 27, 145 );
        knobRef.SetSize( 25, 25 );
        knobRef.SetSkin( "ARP2500 Sm Silver" );
        knobRef.SetRange( 380, 480, 440, false, 200 );
        knobRef.SetKnobParams( 215, 145 );
        knobRef.DisplayValueInPercent( false );
        knobRef.SetKnobAdjustsRing( true );
        knobRef.SetMidpointValue( 440 );

        labelA4 = new VoltageLabel( "labelA4", "A4 Reference Label", this, "A4" );
        AddComponent( labelA4 );
        labelA4.SetWantsMouseNotifications( false );
        labelA4.SetPosition( 27, 167 );
        labelA4.SetSize( 25, 15 );
        labelA4.SetEditable( false, false );
        labelA4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelA4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelA4.SetColor( new Color( 230, 225, 211, 255 ) );
        labelA4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelA4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelA4.SetBorderSize( 1 );
        labelA4.SetMultiLineEdit( false );
        labelA4.SetIsNumberEditor( false );
        labelA4.SetNumberEditorRange( 0, 100 );
        labelA4.SetNumberEditorInterval( 1 );
        labelA4.SetNumberEditorUsesMouseWheel( false );
        labelA4.SetHasCustomTextHoverColor( false );
        labelA4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelA4.SetFont( "Arial", 8, true, false );

        label380 = new VoltageLabel( "label380", "380 Label", this, "380" );
        AddComponent( label380 );
        label380.SetWantsMouseNotifications( false );
        label380.SetPosition( 21, 162 );
        label380.SetSize( 10, 10 );
        label380.SetEditable( false, false );
        label380.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label380.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label380.SetColor( new Color( 230, 225, 211, 255 ) );
        label380.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label380.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label380.SetBorderSize( 1 );
        label380.SetMultiLineEdit( false );
        label380.SetIsNumberEditor( false );
        label380.SetNumberEditorRange( 0, 100 );
        label380.SetNumberEditorInterval( 1 );
        label380.SetNumberEditorUsesMouseWheel( false );
        label380.SetHasCustomTextHoverColor( false );
        label380.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label380.SetFont( "Arial", 4, true, false );

        label480 = new VoltageLabel( "label480", "480 Label", this, "480" );
        AddComponent( label480 );
        label480.SetWantsMouseNotifications( false );
        label480.SetPosition( 49, 162 );
        label480.SetSize( 10, 10 );
        label480.SetEditable( false, false );
        label480.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label480.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label480.SetColor( new Color( 230, 225, 211, 255 ) );
        label480.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label480.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label480.SetBorderSize( 1 );
        label480.SetMultiLineEdit( false );
        label480.SetIsNumberEditor( false );
        label480.SetNumberEditorRange( 0, 100 );
        label480.SetNumberEditorInterval( 1 );
        label480.SetNumberEditorUsesMouseWheel( false );
        label480.SetHasCustomTextHoverColor( false );
        label480.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label480.SetFont( "Arial", 4, true, false );

        label440 = new VoltageLabel( "label440", "440 Label", this, "440" );
        AddComponent( label440 );
        label440.SetWantsMouseNotifications( false );
        label440.SetPosition( 35, 137 );
        label440.SetSize( 10, 10 );
        label440.SetEditable( false, false );
        label440.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label440.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label440.SetColor( new Color( 230, 225, 211, 255 ) );
        label440.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label440.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label440.SetBorderSize( 1 );
        label440.SetMultiLineEdit( false );
        label440.SetIsNumberEditor( false );
        label440.SetNumberEditorRange( 0, 100 );
        label440.SetNumberEditorInterval( 1 );
        label440.SetNumberEditorUsesMouseWheel( false );
        label440.SetHasCustomTextHoverColor( false );
        label440.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label440.SetFont( "Arial", 4, true, false );

        labelNeg20 = new VoltageLabel( "labelNeg20", "-20 Label", this, "-20" );
        AddComponent( labelNeg20 );
        labelNeg20.SetWantsMouseNotifications( false );
        labelNeg20.SetPosition( 134, 182 );
        labelNeg20.SetSize( 10, 10 );
        labelNeg20.SetEditable( false, false );
        labelNeg20.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelNeg20.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNeg20.SetColor( new Color( 166, 36, 36, 255 ) );
        labelNeg20.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNeg20.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNeg20.SetBorderSize( 1 );
        labelNeg20.SetMultiLineEdit( false );
        labelNeg20.SetIsNumberEditor( false );
        labelNeg20.SetNumberEditorRange( 0, 100 );
        labelNeg20.SetNumberEditorInterval( 1 );
        labelNeg20.SetNumberEditorUsesMouseWheel( false );
        labelNeg20.SetHasCustomTextHoverColor( false );
        labelNeg20.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNeg20.SetFont( "Arial", 4, true, false );

        labelMid = new VoltageLabel( "labelMid", "Mid Dot Label", this, "•" );
        AddComponent( labelMid );
        labelMid.SetWantsMouseNotifications( false );
        labelMid.SetPosition( 34, 182 );
        labelMid.SetSize( 10, 10 );
        labelMid.SetEditable( false, false );
        labelMid.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMid.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMid.SetColor( new Color( 166, 36, 36, 255 ) );
        labelMid.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMid.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMid.SetBorderSize( 1 );
        labelMid.SetMultiLineEdit( false );
        labelMid.SetIsNumberEditor( false );
        labelMid.SetNumberEditorRange( 0, 100 );
        labelMid.SetNumberEditorInterval( 1 );
        labelMid.SetNumberEditorUsesMouseWheel( false );
        labelMid.SetHasCustomTextHoverColor( false );
        labelMid.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMid.SetFont( "Arial", 7, true, false );

        labelResponse = new VoltageLabel( "labelResponse", "Response Label", this, "RESPONSE" );
        AddComponent( labelResponse );
        labelResponse.SetWantsMouseNotifications( false );
        labelResponse.SetPosition( 72, 167 );
        labelResponse.SetSize( 35, 15 );
        labelResponse.SetEditable( false, false );
        labelResponse.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelResponse.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelResponse.SetColor( new Color( 230, 225, 211, 255 ) );
        labelResponse.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelResponse.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelResponse.SetBorderSize( 1 );
        labelResponse.SetMultiLineEdit( false );
        labelResponse.SetIsNumberEditor( false );
        labelResponse.SetNumberEditorRange( 0, 100 );
        labelResponse.SetNumberEditorInterval( 1 );
        labelResponse.SetNumberEditorUsesMouseWheel( false );
        labelResponse.SetHasCustomTextHoverColor( false );
        labelResponse.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelResponse.SetFont( "Arial", 8, true, false );

        labelFast = new VoltageLabel( "labelFast", "Fast Label", this, "FAST" );
        AddComponent( labelFast );
        labelFast.SetWantsMouseNotifications( false );
        labelFast.SetPosition( 71, 162 );
        labelFast.SetSize( 10, 10 );
        labelFast.SetEditable( false, false );
        labelFast.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFast.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFast.SetColor( new Color( 230, 225, 211, 255 ) );
        labelFast.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFast.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFast.SetBorderSize( 1 );
        labelFast.SetMultiLineEdit( false );
        labelFast.SetIsNumberEditor( false );
        labelFast.SetNumberEditorRange( 0, 100 );
        labelFast.SetNumberEditorInterval( 1 );
        labelFast.SetNumberEditorUsesMouseWheel( false );
        labelFast.SetHasCustomTextHoverColor( false );
        labelFast.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFast.SetFont( "Arial", 4, true, false );

        labelFine = new VoltageLabel( "labelFIne", "Fine Label", this, "FINE" );
        AddComponent( labelFine );
        labelFine.SetWantsMouseNotifications( false );
        labelFine.SetPosition( 99, 162 );
        labelFine.SetSize( 10, 10 );
        labelFine.SetEditable( false, false );
        labelFine.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFine.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFine.SetColor( new Color( 230, 225, 211, 255 ) );
        labelFine.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFine.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFine.SetBorderSize( 1 );
        labelFine.SetMultiLineEdit( false );
        labelFine.SetIsNumberEditor( false );
        labelFine.SetNumberEditorRange( 0, 100 );
        labelFine.SetNumberEditorInterval( 1 );
        labelFine.SetNumberEditorUsesMouseWheel( false );
        labelFine.SetHasCustomTextHoverColor( false );
        labelFine.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFine.SetFont( "Arial", 4, true, false );

        labelMedium = new VoltageLabel( "labelMedium", "Medium Label", this, "MEDIUM" );
        AddComponent( labelMedium );
        labelMedium.SetWantsMouseNotifications( false );
        labelMedium.SetPosition( 74, 137 );
        labelMedium.SetSize( 30, 10 );
        labelMedium.SetEditable( false, false );
        labelMedium.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMedium.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMedium.SetColor( new Color( 230, 225, 211, 255 ) );
        labelMedium.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelMedium.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelMedium.SetBorderSize( 1 );
        labelMedium.SetMultiLineEdit( false );
        labelMedium.SetIsNumberEditor( false );
        labelMedium.SetNumberEditorRange( 0, 100 );
        labelMedium.SetNumberEditorInterval( 1 );
        labelMedium.SetNumberEditorUsesMouseWheel( false );
        labelMedium.SetHasCustomTextHoverColor( false );
        labelMedium.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelMedium.SetFont( "Arial", 4, true, false );

        labelPyth = new VoltageLabel( "labelPyth", "Pythagorean Label", this, "PYTH" );
        AddComponent( labelPyth );
        labelPyth.SetWantsMouseNotifications( false );
        labelPyth.SetPosition( 149, 156 );
        labelPyth.SetSize( 20, 10 );
        labelPyth.SetEditable( false, false );
        labelPyth.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelPyth.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelPyth.SetColor( new Color( 230, 225, 211, 255 ) );
        labelPyth.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelPyth.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelPyth.SetBorderSize( 1 );
        labelPyth.SetMultiLineEdit( false );
        labelPyth.SetIsNumberEditor( false );
        labelPyth.SetNumberEditorRange( 0, 100 );
        labelPyth.SetNumberEditorInterval( 1 );
        labelPyth.SetNumberEditorUsesMouseWheel( false );
        labelPyth.SetHasCustomTextHoverColor( false );
        labelPyth.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelPyth.SetFont( "Arial", 4, true, false );

        labelWerkIII = new VoltageLabel( "labelWerkIII", "Werkmeister III Label", this, "WERK III" );
        AddComponent( labelWerkIII );
        labelWerkIII.SetWantsMouseNotifications( false );
        labelWerkIII.SetPosition( 108, 156 );
        labelWerkIII.SetSize( 20, 10 );
        labelWerkIII.SetEditable( false, false );
        labelWerkIII.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelWerkIII.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelWerkIII.SetColor( new Color( 230, 225, 211, 255 ) );
        labelWerkIII.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelWerkIII.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelWerkIII.SetBorderSize( 1 );
        labelWerkIII.SetMultiLineEdit( false );
        labelWerkIII.SetIsNumberEditor( false );
        labelWerkIII.SetNumberEditorRange( 0, 100 );
        labelWerkIII.SetNumberEditorInterval( 1 );
        labelWerkIII.SetNumberEditorUsesMouseWheel( false );
        labelWerkIII.SetHasCustomTextHoverColor( false );
        labelWerkIII.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelWerkIII.SetFont( "Arial", 4, true, false );

        labelJust = new VoltageLabel( "labelJust", "5-Limti Just Label", this, "JUST" );
        AddComponent( labelJust );
        labelJust.SetWantsMouseNotifications( false );
        labelJust.SetPosition( 149, 149 );
        labelJust.SetSize( 20, 10 );
        labelJust.SetEditable( false, false );
        labelJust.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelJust.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelJust.SetColor( new Color( 230, 225, 211, 255 ) );
        labelJust.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelJust.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelJust.SetBorderSize( 1 );
        labelJust.SetMultiLineEdit( false );
        labelJust.SetIsNumberEditor( false );
        labelJust.SetNumberEditorRange( 0, 100 );
        labelJust.SetNumberEditorInterval( 1 );
        labelJust.SetNumberEditorUsesMouseWheel( false );
        labelJust.SetHasCustomTextHoverColor( false );
        labelJust.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelJust.SetFont( "Arial", 4, true, false );

        labelMean = new VoltageLabel( "labelMean", "¼-Comma Mean Label", this, "¼ MEAN" );
        AddComponent( labelMean );
        labelMean.SetWantsMouseNotifications( false );
        labelMean.SetPosition( 108, 149 );
        labelMean.SetSize( 20, 10 );
        labelMean.SetEditable( false, false );
        labelMean.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelMean.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelMean.SetColor( new Color( 230, 225, 211, 255 ) );
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
        labelMean.SetFont( "Arial", 4, true, false );

        labelBP = new VoltageLabel( "labelBP", "Bohlen-Pierce Label", this, "B-P" );
        AddComponent( labelBP );
        labelBP.SetWantsMouseNotifications( false );
        labelBP.SetPosition( 144, 143 );
        labelBP.SetSize( 20, 10 );
        labelBP.SetEditable( false, false );
        labelBP.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBP.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBP.SetColor( new Color( 230, 225, 211, 255 ) );
        labelBP.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBP.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBP.SetBorderSize( 1 );
        labelBP.SetMultiLineEdit( false );
        labelBP.SetIsNumberEditor( false );
        labelBP.SetNumberEditorRange( 0, 100 );
        labelBP.SetNumberEditorInterval( 1 );
        labelBP.SetNumberEditorUsesMouseWheel( false );
        labelBP.SetHasCustomTextHoverColor( false );
        labelBP.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBP.SetFont( "Arial", 4, true, false );

        label19EDO = new VoltageLabel( "label19EDO", "19-EDO Label", this, "19-EDO" );
        AddComponent( label19EDO );
        label19EDO.SetWantsMouseNotifications( false );
        label19EDO.SetPosition( 110, 143 );
        label19EDO.SetSize( 20, 10 );
        label19EDO.SetEditable( false, false );
        label19EDO.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label19EDO.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label19EDO.SetColor( new Color( 230, 225, 211, 255 ) );
        label19EDO.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label19EDO.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label19EDO.SetBorderSize( 1 );
        label19EDO.SetMultiLineEdit( false );
        label19EDO.SetIsNumberEditor( false );
        label19EDO.SetNumberEditorRange( 0, 100 );
        label19EDO.SetNumberEditorInterval( 1 );
        label19EDO.SetNumberEditorUsesMouseWheel( false );
        label19EDO.SetHasCustomTextHoverColor( false );
        label19EDO.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label19EDO.SetFont( "Arial", 4, true, false );

        label53EDO = new VoltageLabel( "label53EDO", "53-EDO Label", this, "53-EDO" );
        AddComponent( label53EDO );
        label53EDO.SetWantsMouseNotifications( false );
        label53EDO.SetPosition( 135, 137 );
        label53EDO.SetSize( 20, 10 );
        label53EDO.SetEditable( false, false );
        label53EDO.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label53EDO.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label53EDO.SetColor( new Color( 230, 225, 211, 255 ) );
        label53EDO.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label53EDO.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label53EDO.SetBorderSize( 1 );
        label53EDO.SetMultiLineEdit( false );
        label53EDO.SetIsNumberEditor( false );
        label53EDO.SetNumberEditorRange( 0, 100 );
        label53EDO.SetNumberEditorInterval( 1 );
        label53EDO.SetNumberEditorUsesMouseWheel( false );
        label53EDO.SetHasCustomTextHoverColor( false );
        label53EDO.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label53EDO.SetFont( "Arial", 4, true, false );

        label24EDO = new VoltageLabel( "label24EDO", "24-EDO Label", this, "24-EDO" );
        AddComponent( label24EDO );
        label24EDO.SetWantsMouseNotifications( false );
        label24EDO.SetPosition( 119, 137 );
        label24EDO.SetSize( 20, 10 );
        label24EDO.SetEditable( false, false );
        label24EDO.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label24EDO.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label24EDO.SetColor( new Color( 230, 225, 211, 255 ) );
        label24EDO.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label24EDO.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label24EDO.SetBorderSize( 1 );
        label24EDO.SetMultiLineEdit( false );
        label24EDO.SetIsNumberEditor( false );
        label24EDO.SetNumberEditorRange( 0, 100 );
        label24EDO.SetNumberEditorInterval( 1 );
        label24EDO.SetNumberEditorUsesMouseWheel( false );
        label24EDO.SetHasCustomTextHoverColor( false );
        label24EDO.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label24EDO.SetFont( "Arial", 4, true, false );

        labelTemp = new VoltageLabel( "labelTemp", "Temperament Label", this, "TEMPERAMENT" );
        AddComponent( labelTemp );
        labelTemp.SetWantsMouseNotifications( false );
        labelTemp.SetPosition( 117, 167 );
        labelTemp.SetSize( 45, 15 );
        labelTemp.SetEditable( false, false );
        labelTemp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTemp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTemp.SetColor( new Color( 230, 225, 211, 255 ) );
        labelTemp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTemp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTemp.SetBorderSize( 1 );
        labelTemp.SetMultiLineEdit( false );
        labelTemp.SetIsNumberEditor( false );
        labelTemp.SetNumberEditorRange( 0, 100 );
        labelTemp.SetNumberEditorInterval( 1 );
        labelTemp.SetNumberEditorUsesMouseWheel( false );
        labelTemp.SetHasCustomTextHoverColor( false );
        labelTemp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTemp.SetFont( "Arial", 8, true, false );

        labelKey = new VoltageLabel( "labelKey", "Key Label", this, "KEY" );
        AddComponent( labelKey );
        labelKey.SetWantsMouseNotifications( false );
        labelKey.SetPosition( 177, 167 );
        labelKey.SetSize( 25, 15 );
        labelKey.SetEditable( false, false );
        labelKey.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelKey.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelKey.SetColor( new Color( 230, 225, 211, 255 ) );
        labelKey.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelKey.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelKey.SetBorderSize( 1 );
        labelKey.SetMultiLineEdit( false );
        labelKey.SetIsNumberEditor( false );
        labelKey.SetNumberEditorRange( 0, 100 );
        labelKey.SetNumberEditorInterval( 1 );
        labelKey.SetNumberEditorUsesMouseWheel( false );
        labelKey.SetHasCustomTextHoverColor( false );
        labelKey.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelKey.SetFont( "Arial", 8, true, false );

        labelCalSpan = new VoltageLabel( "labelCalSpan", "Calibration Span Label", this, "CAL SPAN" );
        AddComponent( labelCalSpan );
        labelCalSpan.SetWantsMouseNotifications( false );
        labelCalSpan.SetPosition( 167, 212 );
        labelCalSpan.SetSize( 45, 15 );
        labelCalSpan.SetEditable( false, false );
        labelCalSpan.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCalSpan.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCalSpan.SetColor( new Color( 230, 225, 211, 255 ) );
        labelCalSpan.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCalSpan.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCalSpan.SetBorderSize( 1 );
        labelCalSpan.SetMultiLineEdit( false );
        labelCalSpan.SetIsNumberEditor( false );
        labelCalSpan.SetNumberEditorRange( 0, 100 );
        labelCalSpan.SetNumberEditorInterval( 1 );
        labelCalSpan.SetNumberEditorUsesMouseWheel( false );
        labelCalSpan.SetHasCustomTextHoverColor( false );
        labelCalSpan.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCalSpan.SetFont( "Arial", 8, true, false );

        label7 = new VoltageLabel( "label7", "7 Label", this, "7" );
        AddComponent( label7 );
        label7.SetWantsMouseNotifications( false );
        label7.SetPosition( 98, 207 );
        label7.SetSize( 10, 10 );
        label7.SetEditable( false, false );
        label7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label7.SetColor( new Color( 166, 36, 36, 255 ) );
        label7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label7.SetBorderSize( 1 );
        label7.SetMultiLineEdit( false );
        label7.SetIsNumberEditor( false );
        label7.SetNumberEditorRange( 0, 100 );
        label7.SetNumberEditorInterval( 1 );
        label7.SetNumberEditorUsesMouseWheel( false );
        label7.SetHasCustomTextHoverColor( false );
        label7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label7.SetFont( "Arial", 4, true, false );

        label9Oct = new VoltageLabel( "label9Oct", "9 Octaves Label", this, "9" );
        AddComponent( label9Oct );
        label9Oct.SetWantsMouseNotifications( false );
        label9Oct.SetPosition( 197, 207 );
        label9Oct.SetSize( 10, 10 );
        label9Oct.SetEditable( false, false );
        label9Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label9Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label9Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label9Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label9Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label9Oct.SetBorderSize( 1 );
        label9Oct.SetMultiLineEdit( false );
        label9Oct.SetIsNumberEditor( false );
        label9Oct.SetNumberEditorRange( 0, 100 );
        label9Oct.SetNumberEditorInterval( 1 );
        label9Oct.SetNumberEditorUsesMouseWheel( false );
        label9Oct.SetHasCustomTextHoverColor( false );
        label9Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label9Oct.SetFont( "Arial", 4, true, false );

        labelB = new VoltageLabel( "labelB", "B Label", this, "B" );
        AddComponent( labelB );
        labelB.SetWantsMouseNotifications( false );
        labelB.SetPosition( 198, 162 );
        labelB.SetSize( 10, 10 );
        labelB.SetEditable( false, false );
        labelB.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelB.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelB.SetColor( new Color( 230, 225, 211, 255 ) );
        labelB.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelB.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelB.SetBorderSize( 1 );
        labelB.SetMultiLineEdit( false );
        labelB.SetIsNumberEditor( false );
        labelB.SetNumberEditorRange( 0, 100 );
        labelB.SetNumberEditorInterval( 1 );
        labelB.SetNumberEditorUsesMouseWheel( false );
        labelB.SetHasCustomTextHoverColor( false );
        labelB.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelB.SetFont( "Arial", 4, true, false );

        labelD = new VoltageLabel( "labelD", "D Label", this, "D" );
        AddComponent( labelD );
        labelD.SetWantsMouseNotifications( false );
        labelD.SetPosition( 168, 151 );
        labelD.SetSize( 10, 10 );
        labelD.SetEditable( false, false );
        labelD.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelD.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelD.SetColor( new Color( 230, 225, 211, 255 ) );
        labelD.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelD.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelD.SetBorderSize( 1 );
        labelD.SetMultiLineEdit( false );
        labelD.SetIsNumberEditor( false );
        labelD.SetNumberEditorRange( 0, 100 );
        labelD.SetNumberEditorInterval( 1 );
        labelD.SetNumberEditorUsesMouseWheel( false );
        labelD.SetHasCustomTextHoverColor( false );
        labelD.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelD.SetFont( "Arial", 4, true, false );

        label3Oct = new VoltageLabel( "label3Oct", "3 Octaves Label", this, "3" );
        AddComponent( label3Oct );
        label3Oct.SetWantsMouseNotifications( false );
        label3Oct.SetPosition( 169, 199 );
        label3Oct.SetSize( 10, 10 );
        label3Oct.SetEditable( false, false );
        label3Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label3Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label3Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label3Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label3Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label3Oct.SetBorderSize( 1 );
        label3Oct.SetMultiLineEdit( false );
        label3Oct.SetIsNumberEditor( false );
        label3Oct.SetNumberEditorRange( 0, 100 );
        label3Oct.SetNumberEditorInterval( 1 );
        label3Oct.SetNumberEditorUsesMouseWheel( false );
        label3Oct.SetHasCustomTextHoverColor( false );
        label3Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label3Oct.SetFont( "Arial", 4, true, false );

        label1 = new VoltageLabel( "label1", "1 Label", this, "1" );
        AddComponent( label1 );
        label1.SetWantsMouseNotifications( false );
        label1.SetPosition( 69, 199 );
        label1.SetSize( 10, 10 );
        label1.SetEditable( false, false );
        label1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label1.SetColor( new Color( 166, 36, 36, 255 ) );
        label1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label1.SetBorderSize( 1 );
        label1.SetMultiLineEdit( false );
        label1.SetIsNumberEditor( false );
        label1.SetNumberEditorRange( 0, 100 );
        label1.SetNumberEditorInterval( 1 );
        label1.SetNumberEditorUsesMouseWheel( false );
        label1.SetHasCustomTextHoverColor( false );
        label1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label1.SetFont( "Arial", 4, true, false );

        labelAsharp = new VoltageLabel( "labelAsharp", "A♯ Label", this, "A♯" );
        AddComponent( labelAsharp );
        labelAsharp.SetWantsMouseNotifications( false );
        labelAsharp.SetPosition( 200, 157 );
        labelAsharp.SetSize( 10, 10 );
        labelAsharp.SetEditable( false, false );
        labelAsharp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelAsharp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelAsharp.SetColor( new Color( 230, 225, 211, 255 ) );
        labelAsharp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelAsharp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelAsharp.SetBorderSize( 1 );
        labelAsharp.SetMultiLineEdit( false );
        labelAsharp.SetIsNumberEditor( false );
        labelAsharp.SetNumberEditorRange( 0, 100 );
        labelAsharp.SetNumberEditorInterval( 1 );
        labelAsharp.SetNumberEditorUsesMouseWheel( false );
        labelAsharp.SetHasCustomTextHoverColor( false );
        labelAsharp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelAsharp.SetFont( "Arial", 4, true, false );

        label6 = new VoltageLabel( "label6", "6 Label", this, "6" );
        AddComponent( label6 );
        label6.SetWantsMouseNotifications( false );
        label6.SetPosition( 100, 199 );
        label6.SetSize( 10, 10 );
        label6.SetEditable( false, false );
        label6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label6.SetColor( new Color( 166, 36, 36, 255 ) );
        label6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label6.SetBorderSize( 1 );
        label6.SetMultiLineEdit( false );
        label6.SetIsNumberEditor( false );
        label6.SetNumberEditorRange( 0, 100 );
        label6.SetNumberEditorInterval( 1 );
        label6.SetNumberEditorUsesMouseWheel( false );
        label6.SetHasCustomTextHoverColor( false );
        label6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label6.SetFont( "Arial", 4, true, false );

        label8Oct = new VoltageLabel( "label8Oct", "8 Octaves Label", this, "8" );
        AddComponent( label8Oct );
        label8Oct.SetWantsMouseNotifications( false );
        label8Oct.SetPosition( 200, 199 );
        label8Oct.SetSize( 10, 10 );
        label8Oct.SetEditable( false, false );
        label8Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label8Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label8Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label8Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label8Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label8Oct.SetBorderSize( 1 );
        label8Oct.SetMultiLineEdit( false );
        label8Oct.SetIsNumberEditor( false );
        label8Oct.SetNumberEditorRange( 0, 100 );
        label8Oct.SetNumberEditorInterval( 1 );
        label8Oct.SetNumberEditorUsesMouseWheel( false );
        label8Oct.SetHasCustomTextHoverColor( false );
        label8Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label8Oct.SetFont( "Arial", 4, true, false );

        labelA = new VoltageLabel( "labelA", "A Label", this, "A" );
        AddComponent( labelA );
        labelA.SetWantsMouseNotifications( false );
        labelA.SetPosition( 200, 151 );
        labelA.SetSize( 10, 10 );
        labelA.SetEditable( false, false );
        labelA.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelA.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelA.SetColor( new Color( 230, 225, 211, 255 ) );
        labelA.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelA.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelA.SetBorderSize( 1 );
        labelA.SetMultiLineEdit( false );
        labelA.SetIsNumberEditor( false );
        labelA.SetNumberEditorRange( 0, 100 );
        labelA.SetNumberEditorInterval( 1 );
        labelA.SetNumberEditorUsesMouseWheel( false );
        labelA.SetHasCustomTextHoverColor( false );
        labelA.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelA.SetFont( "Arial", 4, true, false );
}

void InitializeControls2()
{

        labelDsharp = new VoltageLabel( "labelDsharp", "D♯ Label", this, "D♯" );
        AddComponent( labelDsharp );
        labelDsharp.SetWantsMouseNotifications( false );
        labelDsharp.SetPosition( 170, 145 );
        labelDsharp.SetSize( 10, 10 );
        labelDsharp.SetEditable( false, false );
        labelDsharp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDsharp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDsharp.SetColor( new Color( 230, 225, 211, 255 ) );
        labelDsharp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDsharp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDsharp.SetBorderSize( 1 );
        labelDsharp.SetMultiLineEdit( false );
        labelDsharp.SetIsNumberEditor( false );
        labelDsharp.SetNumberEditorRange( 0, 100 );
        labelDsharp.SetNumberEditorInterval( 1 );
        labelDsharp.SetNumberEditorUsesMouseWheel( false );
        labelDsharp.SetHasCustomTextHoverColor( false );
        labelDsharp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDsharp.SetFont( "Arial", 4, true, false );

        labelGsharp = new VoltageLabel( "labelGsharp", "G♯ Label", this, "G♯" );
        AddComponent( labelGsharp );
        labelGsharp.SetWantsMouseNotifications( false );
        labelGsharp.SetPosition( 199, 145 );
        labelGsharp.SetSize( 10, 10 );
        labelGsharp.SetEditable( false, false );
        labelGsharp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGsharp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGsharp.SetColor( new Color( 230, 225, 211, 255 ) );
        labelGsharp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelGsharp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelGsharp.SetBorderSize( 1 );
        labelGsharp.SetMultiLineEdit( false );
        labelGsharp.SetIsNumberEditor( false );
        labelGsharp.SetNumberEditorRange( 0, 100 );
        labelGsharp.SetNumberEditorInterval( 1 );
        labelGsharp.SetNumberEditorUsesMouseWheel( false );
        labelGsharp.SetHasCustomTextHoverColor( false );
        labelGsharp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelGsharp.SetFont( "Arial", 4, true, false );

        labelE = new VoltageLabel( "labelE", "E Label", this, "E" );
        AddComponent( labelE );
        labelE.SetWantsMouseNotifications( false );
        labelE.SetPosition( 174, 139 );
        labelE.SetSize( 10, 10 );
        labelE.SetEditable( false, false );
        labelE.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelE.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelE.SetColor( new Color( 230, 225, 211, 255 ) );
        labelE.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelE.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelE.SetBorderSize( 1 );
        labelE.SetMultiLineEdit( false );
        labelE.SetIsNumberEditor( false );
        labelE.SetNumberEditorRange( 0, 100 );
        labelE.SetNumberEditorInterval( 1 );
        labelE.SetNumberEditorUsesMouseWheel( false );
        labelE.SetHasCustomTextHoverColor( false );
        labelE.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelE.SetFont( "Arial", 4, true, false );

        labelDot1 = new VoltageLabel( "labelDot1", "dB Dot Label 1", this, "•" );
        AddComponent( labelDot1 );
        labelDot1.SetWantsMouseNotifications( false );
        labelDot1.SetPosition( 118, 200 );
        labelDot1.SetSize( 10, 10 );
        labelDot1.SetEditable( false, false );
        labelDot1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDot1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDot1.SetColor( new Color( 166, 36, 36, 255 ) );
        labelDot1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDot1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDot1.SetBorderSize( 1 );
        labelDot1.SetMultiLineEdit( false );
        labelDot1.SetIsNumberEditor( false );
        labelDot1.SetNumberEditorRange( 0, 100 );
        labelDot1.SetNumberEditorInterval( 1 );
        labelDot1.SetNumberEditorUsesMouseWheel( false );
        labelDot1.SetHasCustomTextHoverColor( false );
        labelDot1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDot1.SetFont( "Arial", 7, true, false );

        labelDot2 = new VoltageLabel( "labelDot2", "Dot Label 2", this, "•" );
        AddComponent( labelDot2 );
        labelDot2.SetWantsMouseNotifications( false );
        labelDot2.SetPosition( 123, 185 );
        labelDot2.SetSize( 10, 10 );
        labelDot2.SetEditable( false, false );
        labelDot2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDot2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDot2.SetColor( new Color( 166, 36, 36, 255 ) );
        labelDot2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDot2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDot2.SetBorderSize( 1 );
        labelDot2.SetMultiLineEdit( false );
        labelDot2.SetIsNumberEditor( false );
        labelDot2.SetNumberEditorRange( 0, 100 );
        labelDot2.SetNumberEditorInterval( 1 );
        labelDot2.SetNumberEditorUsesMouseWheel( false );
        labelDot2.SetHasCustomTextHoverColor( false );
        labelDot2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDot2.SetFont( "Arial", 7, true, false );

        label4Oct = new VoltageLabel( "label4Oct", "4 Octaves Label", this, "4" );
        AddComponent( label4Oct );
        label4Oct.SetWantsMouseNotifications( false );
        label4Oct.SetPosition( 171, 190 );
        label4Oct.SetSize( 10, 10 );
        label4Oct.SetEditable( false, false );
        label4Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label4Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label4Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label4Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label4Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label4Oct.SetBorderSize( 1 );
        label4Oct.SetMultiLineEdit( false );
        label4Oct.SetIsNumberEditor( false );
        label4Oct.SetNumberEditorRange( 0, 100 );
        label4Oct.SetNumberEditorInterval( 1 );
        label4Oct.SetNumberEditorUsesMouseWheel( false );
        label4Oct.SetHasCustomTextHoverColor( false );
        label4Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label4Oct.SetFont( "Arial", 4, true, false );

        label2 = new VoltageLabel( "label2", "2 Label", this, "2" );
        AddComponent( label2 );
        label2.SetWantsMouseNotifications( false );
        label2.SetPosition( 71, 189 );
        label2.SetSize( 10, 10 );
        label2.SetEditable( false, false );
        label2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label2.SetColor( new Color( 166, 36, 36, 255 ) );
        label2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label2.SetBorderSize( 1 );
        label2.SetMultiLineEdit( false );
        label2.SetIsNumberEditor( false );
        label2.SetNumberEditorRange( 0, 100 );
        label2.SetNumberEditorInterval( 1 );
        label2.SetNumberEditorUsesMouseWheel( false );
        label2.SetHasCustomTextHoverColor( false );
        label2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label2.SetFont( "Arial", 4, true, false );

        labelG = new VoltageLabel( "labelG", "G Label", this, "G" );
        AddComponent( labelG );
        labelG.SetWantsMouseNotifications( false );
        labelG.SetPosition( 195, 139 );
        labelG.SetSize( 10, 10 );
        labelG.SetEditable( false, false );
        labelG.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelG.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelG.SetColor( new Color( 230, 225, 211, 255 ) );
        labelG.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelG.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelG.SetBorderSize( 1 );
        labelG.SetMultiLineEdit( false );
        labelG.SetIsNumberEditor( false );
        labelG.SetNumberEditorRange( 0, 100 );
        labelG.SetNumberEditorInterval( 1 );
        labelG.SetNumberEditorUsesMouseWheel( false );
        labelG.SetHasCustomTextHoverColor( false );
        labelG.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelG.SetFont( "Arial", 4, true, false );

        labelDot3 = new VoltageLabel( "labelDot3", "Dor Laebl 3", this, "•" );
        AddComponent( labelDot3 );
        labelDot3.SetWantsMouseNotifications( false );
        labelDot3.SetPosition( 146, 185 );
        labelDot3.SetSize( 10, 10 );
        labelDot3.SetEditable( false, false );
        labelDot3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDot3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDot3.SetColor( new Color( 166, 36, 36, 255 ) );
        labelDot3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDot3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDot3.SetBorderSize( 1 );
        labelDot3.SetMultiLineEdit( false );
        labelDot3.SetIsNumberEditor( false );
        labelDot3.SetNumberEditorRange( 0, 100 );
        labelDot3.SetNumberEditorInterval( 1 );
        labelDot3.SetNumberEditorUsesMouseWheel( false );
        labelDot3.SetHasCustomTextHoverColor( false );
        labelDot3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDot3.SetFont( "Arial", 7, true, false );

        label5 = new VoltageLabel( "label5", "5 Label", this, "5" );
        AddComponent( label5 );
        label5.SetWantsMouseNotifications( false );
        label5.SetPosition( 98, 189 );
        label5.SetSize( 10, 10 );
        label5.SetEditable( false, false );
        label5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label5.SetColor( new Color( 166, 36, 36, 255 ) );
        label5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label5.SetBorderSize( 1 );
        label5.SetMultiLineEdit( false );
        label5.SetIsNumberEditor( false );
        label5.SetNumberEditorRange( 0, 100 );
        label5.SetNumberEditorInterval( 1 );
        label5.SetNumberEditorUsesMouseWheel( false );
        label5.SetHasCustomTextHoverColor( false );
        label5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label5.SetFont( "Arial", 4, true, false );

        label7Oct = new VoltageLabel( "label7Oct", "7 Octaves Label", this, "7" );
        AddComponent( label7Oct );
        label7Oct.SetWantsMouseNotifications( false );
        label7Oct.SetPosition( 199, 190 );
        label7Oct.SetSize( 10, 10 );
        label7Oct.SetEditable( false, false );
        label7Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label7Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label7Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label7Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label7Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label7Oct.SetBorderSize( 1 );
        label7Oct.SetMultiLineEdit( false );
        label7Oct.SetIsNumberEditor( false );
        label7Oct.SetNumberEditorRange( 0, 100 );
        label7Oct.SetNumberEditorInterval( 1 );
        label7Oct.SetNumberEditorUsesMouseWheel( false );
        label7Oct.SetHasCustomTextHoverColor( false );
        label7Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label7Oct.SetFont( "Arial", 4, true, false );

        labelDot4 = new VoltageLabel( "labelDot4", "Dot Label 4", this, "•" );
        AddComponent( labelDot4 );
        labelDot4.SetWantsMouseNotifications( false );
        labelDot4.SetPosition( 151, 200 );
        labelDot4.SetSize( 10, 10 );
        labelDot4.SetEditable( false, false );
        labelDot4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDot4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDot4.SetColor( new Color( 166, 36, 36, 255 ) );
        labelDot4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDot4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDot4.SetBorderSize( 1 );
        labelDot4.SetMultiLineEdit( false );
        labelDot4.SetIsNumberEditor( false );
        labelDot4.SetNumberEditorRange( 0, 100 );
        labelDot4.SetNumberEditorInterval( 1 );
        labelDot4.SetNumberEditorUsesMouseWheel( false );
        labelDot4.SetHasCustomTextHoverColor( false );
        labelDot4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDot4.SetFont( "Arial", 7, true, false );

        labelF = new VoltageLabel( "labelF", "F Label", this, "F" );
        AddComponent( labelF );
        labelF.SetWantsMouseNotifications( false );
        labelF.SetPosition( 181, 137 );
        labelF.SetSize( 10, 10 );
        labelF.SetEditable( false, false );
        labelF.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelF.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelF.SetColor( new Color( 230, 225, 211, 255 ) );
        labelF.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelF.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelF.SetBorderSize( 1 );
        labelF.SetMultiLineEdit( false );
        labelF.SetIsNumberEditor( false );
        labelF.SetNumberEditorRange( 0, 100 );
        labelF.SetNumberEditorInterval( 1 );
        labelF.SetNumberEditorUsesMouseWheel( false );
        labelF.SetHasCustomTextHoverColor( false );
        labelF.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelF.SetFont( "Arial", 4, true, false );

        labelFsharp = new VoltageLabel( "labelFsharp", "F♯ Label", this, "F♯" );
        AddComponent( labelFsharp );
        labelFsharp.SetWantsMouseNotifications( false );
        labelFsharp.SetPosition( 188, 137 );
        labelFsharp.SetSize( 10, 10 );
        labelFsharp.SetEditable( false, false );
        labelFsharp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFsharp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFsharp.SetColor( new Color( 230, 225, 211, 255 ) );
        labelFsharp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFsharp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFsharp.SetBorderSize( 1 );
        labelFsharp.SetMultiLineEdit( false );
        labelFsharp.SetIsNumberEditor( false );
        labelFsharp.SetNumberEditorRange( 0, 100 );
        labelFsharp.SetNumberEditorInterval( 1 );
        labelFsharp.SetNumberEditorUsesMouseWheel( false );
        labelFsharp.SetHasCustomTextHoverColor( false );
        labelFsharp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFsharp.SetFont( "Arial", 4, true, false );

        label3 = new VoltageLabel( "label3", "3 Label", this, "3" );
        AddComponent( label3 );
        label3.SetWantsMouseNotifications( false );
        label3.SetPosition( 79, 182 );
        label3.SetSize( 10, 10 );
        label3.SetEditable( false, false );
        label3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label3.SetColor( new Color( 166, 36, 36, 255 ) );
        label3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label3.SetBorderSize( 1 );
        label3.SetMultiLineEdit( false );
        label3.SetIsNumberEditor( false );
        label3.SetNumberEditorRange( 0, 100 );
        label3.SetNumberEditorInterval( 1 );
        label3.SetNumberEditorUsesMouseWheel( false );
        label3.SetHasCustomTextHoverColor( false );
        label3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label3.SetFont( "Arial", 4, true, false );

        label6Oct = new VoltageLabel( "label6Oct", "6 Octaves Label", this, "6" );
        AddComponent( label6Oct );
        label6Oct.SetWantsMouseNotifications( false );
        label6Oct.SetPosition( 190, 183 );
        label6Oct.SetSize( 10, 10 );
        label6Oct.SetEditable( false, false );
        label6Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label6Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label6Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label6Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label6Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label6Oct.SetBorderSize( 1 );
        label6Oct.SetMultiLineEdit( false );
        label6Oct.SetIsNumberEditor( false );
        label6Oct.SetNumberEditorRange( 0, 100 );
        label6Oct.SetNumberEditorInterval( 1 );
        label6Oct.SetNumberEditorUsesMouseWheel( false );
        label6Oct.SetHasCustomTextHoverColor( false );
        label6Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label6Oct.SetFont( "Arial", 4, true, false );

        label5Oct = new VoltageLabel( "label5Oct", "5 Octaves Label", this, "5" );
        AddComponent( label5Oct );
        label5Oct.SetWantsMouseNotifications( false );
        label5Oct.SetPosition( 178, 183 );
        label5Oct.SetSize( 10, 10 );
        label5Oct.SetEditable( false, false );
        label5Oct.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label5Oct.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label5Oct.SetColor( new Color( 230, 209, 211, 255 ) );
        label5Oct.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label5Oct.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label5Oct.SetBorderSize( 1 );
        label5Oct.SetMultiLineEdit( false );
        label5Oct.SetIsNumberEditor( false );
        label5Oct.SetNumberEditorRange( 0, 100 );
        label5Oct.SetNumberEditorInterval( 1 );
        label5Oct.SetNumberEditorUsesMouseWheel( false );
        label5Oct.SetHasCustomTextHoverColor( false );
        label5Oct.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label5Oct.SetFont( "Arial", 4, true, false );

        label4 = new VoltageLabel( "label4", "4 Label", this, "4" );
        AddComponent( label4 );
        label4.SetWantsMouseNotifications( false );
        label4.SetPosition( 90, 182 );
        label4.SetSize( 10, 10 );
        label4.SetEditable( false, false );
        label4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label4.SetColor( new Color( 166, 36, 36, 255 ) );
        label4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label4.SetBorderSize( 1 );
        label4.SetMultiLineEdit( false );
        label4.SetIsNumberEditor( false );
        label4.SetNumberEditorRange( 0, 100 );
        label4.SetNumberEditorInterval( 1 );
        label4.SetNumberEditorUsesMouseWheel( false );
        label4.SetHasCustomTextHoverColor( false );
        label4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label4.SetFont( "Arial", 4, true, false );

        canvas1 = new VoltageCanvas( "canvas1", "canvas1", this, 220, 110 );
        AddComponent( canvas1 );
        canvas1.SetWantsMouseNotifications( false );
        canvas1.SetPosition( 5, 22 );
        canvas1.SetSize( 220, 110 );

        labelLevel = new VoltageLabel( "labelLevel", "Tone Level Label", this, "dB" );
        AddComponent( labelLevel );
        labelLevel.SetWantsMouseNotifications( false );
        labelLevel.SetPosition( 117, 212 );
        labelLevel.SetSize( 45, 15 );
        labelLevel.SetEditable( false, false );
        labelLevel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLevel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLevel.SetColor( new Color( 166, 36, 36, 255 ) );
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
        labelLevel.SetFont( "Arial", 8, true, false );

        labelOctave = new VoltageLabel( "labelOctave", "Octave Label", this, "OCTAVE" );
        AddComponent( labelOctave );
        labelOctave.SetWantsMouseNotifications( false );
        labelOctave.SetPosition( 67, 212 );
        labelOctave.SetSize( 45, 15 );
        labelOctave.SetEditable( false, false );
        labelOctave.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOctave.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOctave.SetColor( new Color( 166, 36, 36, 255 ) );
        labelOctave.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOctave.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOctave.SetBorderSize( 1 );
        labelOctave.SetMultiLineEdit( false );
        labelOctave.SetIsNumberEditor( false );
        labelOctave.SetNumberEditorRange( 0, 100 );
        labelOctave.SetNumberEditorInterval( 1 );
        labelOctave.SetNumberEditorUsesMouseWheel( false );
        labelOctave.SetHasCustomTextHoverColor( false );
        labelOctave.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOctave.SetFont( "Arial", 8, true, false );

        labelToneKnob = new VoltageLabel( "labelToneKnob", "Tone Knob Label", this, "TONE" );
        AddComponent( labelToneKnob );
        labelToneKnob.SetWantsMouseNotifications( false );
        labelToneKnob.SetPosition( 17, 212 );
        labelToneKnob.SetSize( 45, 15 );
        labelToneKnob.SetEditable( false, false );
        labelToneKnob.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelToneKnob.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelToneKnob.SetColor( new Color( 166, 36, 36, 255 ) );
        labelToneKnob.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelToneKnob.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelToneKnob.SetBorderSize( 1 );
        labelToneKnob.SetMultiLineEdit( false );
        labelToneKnob.SetIsNumberEditor( false );
        labelToneKnob.SetNumberEditorRange( 0, 100 );
        labelToneKnob.SetNumberEditorInterval( 1 );
        labelToneKnob.SetNumberEditorUsesMouseWheel( false );
        labelToneKnob.SetHasCustomTextHoverColor( false );
        labelToneKnob.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelToneKnob.SetFont( "Arial", 8, true, false );

        labelRoot = new VoltageLabel( "labelRoot", "Root Label", this, "ROOT" );
        AddComponent( labelRoot );
        labelRoot.SetWantsMouseNotifications( false );
        labelRoot.SetPosition( 21, 207 );
        labelRoot.SetSize( 10, 10 );
        labelRoot.SetEditable( false, false );
        labelRoot.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelRoot.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelRoot.SetColor( new Color( 166, 36, 36, 255 ) );
        labelRoot.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelRoot.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelRoot.SetBorderSize( 1 );
        labelRoot.SetMultiLineEdit( false );
        labelRoot.SetIsNumberEditor( false );
        labelRoot.SetNumberEditorRange( 0, 100 );
        labelRoot.SetNumberEditorInterval( 1 );
        labelRoot.SetNumberEditorUsesMouseWheel( false );
        labelRoot.SetHasCustomTextHoverColor( false );
        labelRoot.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelRoot.SetFont( "Arial", 4, true, false );

        labelNeg30 = new VoltageLabel( "labelNeg30", "-30 Label", this, "-30" );
        AddComponent( labelNeg30 );
        labelNeg30.SetWantsMouseNotifications( false );
        labelNeg30.SetPosition( 119, 192 );
        labelNeg30.SetSize( 10, 10 );
        labelNeg30.SetEditable( false, false );
        labelNeg30.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelNeg30.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNeg30.SetColor( new Color( 166, 36, 36, 255 ) );
        labelNeg30.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNeg30.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNeg30.SetBorderSize( 1 );
        labelNeg30.SetMultiLineEdit( false );
        labelNeg30.SetIsNumberEditor( false );
        labelNeg30.SetNumberEditorRange( 0, 100 );
        labelNeg30.SetNumberEditorInterval( 1 );
        labelNeg30.SetNumberEditorUsesMouseWheel( false );
        labelNeg30.SetHasCustomTextHoverColor( false );
        labelNeg30.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNeg30.SetFont( "Arial", 4, true, false );

        labelNeg40 = new VoltageLabel( "labelNeg40", "-40 Label", this, "-40" );
        AddComponent( labelNeg40 );
        labelNeg40.SetWantsMouseNotifications( false );
        labelNeg40.SetPosition( 121, 207 );
        labelNeg40.SetSize( 10, 10 );
        labelNeg40.SetEditable( false, false );
        labelNeg40.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelNeg40.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNeg40.SetColor( new Color( 166, 36, 36, 255 ) );
        labelNeg40.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNeg40.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNeg40.SetBorderSize( 1 );
        labelNeg40.SetMultiLineEdit( false );
        labelNeg40.SetIsNumberEditor( false );
        labelNeg40.SetNumberEditorRange( 0, 100 );
        labelNeg40.SetNumberEditorInterval( 1 );
        labelNeg40.SetNumberEditorUsesMouseWheel( false );
        labelNeg40.SetHasCustomTextHoverColor( false );
        labelNeg40.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNeg40.SetFont( "Arial", 4, true, false );

        labelUnity = new VoltageLabel( "labelUnity", "0 dB Label", this, "0" );
        AddComponent( labelUnity );
        labelUnity.SetWantsMouseNotifications( false );
        labelUnity.SetPosition( 149, 207 );
        labelUnity.SetSize( 10, 10 );
        labelUnity.SetEditable( false, false );
        labelUnity.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelUnity.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelUnity.SetColor( new Color( 166, 36, 36, 255 ) );
        labelUnity.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelUnity.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelUnity.SetBorderSize( 1 );
        labelUnity.SetMultiLineEdit( false );
        labelUnity.SetIsNumberEditor( false );
        labelUnity.SetNumberEditorRange( 0, 100 );
        labelUnity.SetNumberEditorInterval( 1 );
        labelUnity.SetNumberEditorUsesMouseWheel( false );
        labelUnity.SetHasCustomTextHoverColor( false );
        labelUnity.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelUnity.SetFont( "Arial", 4, true, false );

        labelNeg10 = new VoltageLabel( "labelNeg10", "-10 Label", this, "-10" );
        AddComponent( labelNeg10 );
        labelNeg10.SetWantsMouseNotifications( false );
        labelNeg10.SetPosition( 151, 192 );
        labelNeg10.SetSize( 10, 10 );
        labelNeg10.SetEditable( false, false );
        labelNeg10.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelNeg10.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelNeg10.SetColor( new Color( 166, 36, 36, 255 ) );
        labelNeg10.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelNeg10.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelNeg10.SetBorderSize( 1 );
        labelNeg10.SetMultiLineEdit( false );
        labelNeg10.SetIsNumberEditor( false );
        labelNeg10.SetNumberEditorRange( 0, 100 );
        labelNeg10.SetNumberEditorInterval( 1 );
        labelNeg10.SetNumberEditorUsesMouseWheel( false );
        labelNeg10.SetHasCustomTextHoverColor( false );
        labelNeg10.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelNeg10.SetFont( "Arial", 4, true, false );

        labelTop = new VoltageLabel( "labelTop", "Top Label", this, "TOP" );
        AddComponent( labelTop );
        labelTop.SetWantsMouseNotifications( false );
        labelTop.SetPosition( 49, 207 );
        labelTop.SetSize( 10, 10 );
        labelTop.SetEditable( false, false );
        labelTop.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTop.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTop.SetColor( new Color( 166, 36, 36, 255 ) );
        labelTop.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTop.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTop.SetBorderSize( 1 );
        labelTop.SetMultiLineEdit( false );
        labelTop.SetIsNumberEditor( false );
        labelTop.SetNumberEditorRange( 0, 100 );
        labelTop.SetNumberEditorInterval( 1 );
        labelTop.SetNumberEditorUsesMouseWheel( false );
        labelTop.SetHasCustomTextHoverColor( false );
        labelTop.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTop.SetFont( "Arial", 4, true, false );
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
        RegisterFileTypesForFileDrop( "tun;scl" );
        startWorker();
        applyAll();
        ready = true;
        StartGuiUpdateTimer( TIMER_MS );
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
        stopWorker();
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
        if (!ready) {
            return false;
        }
        if (notification == ModuleNotifications.GUI_Update_Timer) {
            tick();
        } else if (notification == ModuleNotifications.Canvas_Painting) {
            paint( object );
        } else if (notification == ModuleNotifications.Knob_Changed || notification == ModuleNotifications.Switch_Changed) {
            applyAll();
        } else if (notification == ModuleNotifications.Button_Changed) {
            if (component == buttonLoad) {
                if (doubleValue > 0.5) {
                    doLoad();
                }
            } else if (component == buttonCal) {
                calButton( doubleValue > 0.5 );
            } else {
                applyAll();
            }
        } else if (notification == ModuleNotifications.File_Dropped) {
            if (object instanceof String) {
                loadFile( (String) object );
            }
        } else if (notification == ModuleNotifications.Preset_Loading_Finish) {
            applyAll();
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
        if (!ready) {
            return;
        }
        double x = readVolts( input );
        core.push( x );
        thruput.SetValue( muteOn ? 0.0 : x * INV_VOLTS );
        outputTone.SetValue( core.toneSample() * INV_VOLTS );
        if (++hopCount >= TnrCore.HOP) {
            hopCount = 0;
            if (jobState == 0) {
                core.prepare();
                jobState = 1;
            }
        }
        if (core.valid) {
            lastPitchV = core.cvPitchVolts;
            lastCentsV = core.cvCentsVolts;
            outputGate.SetValue( 1.0 );
        } else {
            lastCentsV = 0.0;
            outputGate.SetValue( 0.0 );
        }
        outputVoct.SetValue( lastPitchV * CV_JACK_PER_VOLT );
        outputCents.SetValue( lastCentsV * INV_VOLTS );
        outputCal.SetValue( core.calCv * CV_JACK_PER_VOLT );
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
        // Bypassed: pass the input straight through, everything else silent.
        if (!ready) {
            return;
        }
        thruput.SetValue( input.IsConnected() ? input.GetValue() : 0.0 );
        outputTone.SetValue( 0.0 );
        outputVoct.SetValue( 0.0 );
        outputCents.SetValue( 0.0 );
        outputGate.SetValue( 0.0 );
        outputCal.SetValue( 0.0 );
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
        if (ready) {
            String tip = tipFor( component );
            if (tip != null) {
                return tip;
            }
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
        // Typed entry arrives in the displayed unit: dB for the tone level (the knob itself is 0..1). The A4 knob is in Hz, so typed Hz goes straight through.
        if (ready && component == knobLevel) {
            double f = clamp01((newValue + 40.0) / 40.0);
            newValue = knobLevel.GetMinValue() + f * (knobLevel.GetMaxValue() - knobLevel.GetMinValue());
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
        // no custom undo events
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
        return stateBytes();
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
        restoreState( stateInfo );
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
        return stateBytes();
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
        restoreState( stateInfo );
        //[/user-SetStateInformationForVariations]
    }


    // Auto-generated variables
    private VoltageLabel labelTop;
    private VoltageLabel labelNeg10;
    private VoltageLabel labelUnity;
    private VoltageLabel labelNeg40;
    private VoltageLabel labelNeg30;
    private VoltageLabel labelRoot;
    private VoltageLabel labelToneKnob;
    private VoltageLabel labelOctave;
    private VoltageLabel labelLevel;
    private VoltageCanvas canvas1;
    private VoltageLabel label4;
    private VoltageLabel label5Oct;
    private VoltageLabel label6Oct;
    private VoltageLabel label3;
    private VoltageLabel labelFsharp;
    private VoltageLabel labelF;
    private VoltageLabel labelDot4;
    private VoltageLabel label7Oct;
    private VoltageLabel label5;
    private VoltageLabel labelDot3;
    private VoltageLabel labelG;
    private VoltageLabel label2;
    private VoltageLabel label4Oct;
    private VoltageLabel labelDot2;
    private VoltageLabel labelDot1;
    private VoltageLabel labelE;
    private VoltageLabel labelGsharp;
    private VoltageLabel labelDsharp;
    private VoltageLabel labelA;
    private VoltageLabel label8Oct;
    private VoltageLabel label6;
    private VoltageLabel labelAsharp;
    private VoltageLabel label1;
    private VoltageLabel label3Oct;
    private VoltageLabel labelD;
    private VoltageLabel labelB;
    private VoltageLabel label9Oct;
    private VoltageLabel label7;
    private VoltageLabel labelCalSpan;
    private VoltageLabel labelKey;
    private VoltageLabel labelTemp;
    private VoltageLabel label24EDO;
    private VoltageLabel label53EDO;
    private VoltageLabel label19EDO;
    private VoltageLabel labelBP;
    private VoltageLabel labelMean;
    private VoltageLabel labelJust;
    private VoltageLabel labelWerkIII;
    private VoltageLabel labelPyth;
    private VoltageLabel labelMedium;
    private VoltageLabel labelFine;
    private VoltageLabel labelFast;
    private VoltageLabel labelResponse;
    private VoltageLabel labelMid;
    private VoltageLabel labelNeg20;
    private VoltageLabel label440;
    private VoltageLabel label480;
    private VoltageLabel label380;
    private VoltageLabel labelA4;
    private VoltageKnob knobRef;
    private VoltageKnob knobResponse;
    private VoltageKnob knobTemp;
    private VoltageKnob knobKey;
    private VoltageKnob knob68;
    private VoltageKnob knobLevel;
    private VoltageKnob knob66;
    private VoltageKnob knobTone;
    private VoltageLabel labelRanges;
    private VoltageToggle buttonLoad;
    private VoltageToggle buttonCal;
    private VoltageToggle buttonTone;
    private VoltageToggle buttonMute;
    private VoltageToggle buttonHold;
    private VoltageSwitch switchSource;
    private VoltageLabel labelSource;
    private VoltageLabel labelRangeSwitch;
    private VoltageSwitch switchRange;
    private VoltageLabel labelCal;
    private VoltageLabel labelGate;
    private VoltageLabel labelCents;
    private VoltageLabel labelVoct;
    private VoltageLabel labelToneOut;
    private VoltageLabel labelThru;
    private VoltageLabel labelInput;
    private VoltageAudioJack outputCal;
    private VoltageAudioJack outputGate;
    private VoltageAudioJack outputCents;
    private VoltageAudioJack outputVoct;
    private VoltageAudioJack outputTone;
    private VoltageAudioJack thruput;
    private VoltageAudioJack input;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel labelFollow;
    private VoltageLabel labelManual;
    private VoltageLabel label12tet;
    private VoltageLabel labelDotTun;
    private VoltageLabel labelCsharp;
    private VoltageLabel label2Oct;
    private VoltageLabel label0;
    private VoltageLabel labelC;


    //[user-code-and-variables]    Add your own variables and functions here
    // ---- TnrCore (framework-free; same source as TnrCore.java, tested headless) ----
    private static final class TnrCore {

        // ---------------------------------------------------------------- constants
        public static final double FS = 48000.0;                 // [READ] fixed 48 kHz
        public static final int DEC = 4;
        public static final double FS12 = FS / DEC;               // 12 kHz coarse path
        public static final int N12 = 2048;                       // ~170 ms coarse window, 2 periods of 30 Hz
        public static final int NHI = 8192;                       // 48 kHz fine ring
        public static final int HOP = 1200;                       // 25 ms analysis hop
        public static final double MIDI0_HZ = 8.1757989156437073336; // 12-TET note 0 at A4=440
        public static final double C4_OFFSET_FROM_A4_CENTS = -900.0;

        public static final int RANGE_AUTO = 0, RANGE_LOW = 1, RANGE_MID = 2, RANGE_HIGH = 3;
        static final double[] RANGE_LO = {30.0, 30.0, 70.0, 250.0};     // [GUESS] range edges
        static final double[] RANGE_HI = {2200.0, 140.0, 700.0, 2200.0};

        static final double MPM_K = 0.80;                          // key-maximum threshold [MODEL]
        static final double MIN_CONF = 0.80;                       // confidence gate [GUESS]
        static final double DEFAULT_THRESHOLD_RMS = 0.002;         // volts, DC-blocked RMS [GUESS]
        static final int INVALID_HOLD_HOPS = 10;                   // 250 ms before "no signal"

        // ---------------------------------------------------------------- input path
        private final float[] lo = new float[N12];
        private final float[] hi = new float[NHI];
        private int loPos, hiPos, decCnt;
        private long pushed;
        private double dcX1, dcY1;
        private final double[][] bq = new double[2][5];            // b0 b1 b2 a1 a2 (normalised)
        private final double[][] bqz = new double[2][2];
        private double pw;                                         // smoothed power
        private final double pwK = 1.0 - Math.exp(-1.0 / (0.030 * FS));

        // ---------------------------------------------------------------- analysis scratch
        private final double[] wl = new double[N12];
        private final double[] wh = new double[NHI];
        private final double[] nsd = new double[N12 / 2 + 4];
        private final int[] kmIdx = new int[64];
        private final double[] kmVal = new double[64];
        private double prepLevel;
        private boolean prepReady;
        private int prepRange;

        // raw estimate (worker writes, update() reads)
        private volatile boolean estValid;
        private volatile double estHz, estConf;

        // ---------------------------------------------------------------- stabiliser
        private final double[] stab = new double[9];
        private final double[] scratch = new double[9];
        private int stabN, invalidRun;
        private volatile int stabW = 5;
        private double cand; private int candCount;
        private volatile boolean hold;
        private volatile double rangeLo = RANGE_LO[0], rangeHi = RANGE_HI[0];
        private int range;
        private volatile double thresholdRms = DEFAULT_THRESHOLD_RMS;

        // ---------------------------------------------------------------- published results
        public volatile boolean valid;                // a stable reading exists
        public volatile double hz, conf, level;
        public volatile int idx = -1;                 // table index of nearest note
        public volatile double centsErr;              // vs nearest note
        public volatile double targetHz;
        public volatile int labelA, labelOct;         // pitch class (0-11) or degree
        public volatile boolean labelIsNote;          // kind 1 only; see labelKind
        public volatile int labelKind;                // 0 degree, 1 note, 2 quarter-tone note, 3 note number (.tun that is not a 12-note keyboard tuning)
        public volatile double cvPitchVolts, cvCentsVolts;
        private int lastIdx = -1;

        // ---------------------------------------------------------------- tuning / reference
        private volatile Tuning tuning = Tunings.equal(0);
        private volatile double refA4 = 440.0;

        // ---------------------------------------------------------------- tone
        private double tonePhase, toneHz = 440.0;
        private volatile double toneHzTarget = 440.0, toneLevel = 0.3;
        private volatile boolean toneOn;

        // ---------------------------------------------------------------- CAL
        public static final int CAL_IDLE = 0, CAL_SETTLE = 1, CAL_MEASURE = 2, CAL_DONE = 3, CAL_FAIL = 4;
        public static final int CAL_SETTLE_HOPS = 16, CAL_MEAS_HOPS = 16;
        private volatile int calState;
        private double calV0, calStepV; private int calN, calStep, calHop;
        private final double[] calLog = new double[16];
        private final boolean[] calOk = new boolean[16];
        private double calAcc; private int calAccN;
        public volatile double calCv;                  // volts to drive the CAL CV jack
        public volatile double calSlopeOctPerV, calErrCentsPerOct, calMaxResidCents, calHzAtV0;
        public volatile int calGoodSteps;

        // ================================================================ construction
        public TnrCore() {
            lowpass(0, 4500.0, 0.5412);
            lowpass(1, 4500.0, 1.3066);
            setRange(RANGE_AUTO);
        }

        private void lowpass(int s, double fc, double q) {
            double w0 = 2 * Math.PI * fc / FS, cs = Math.cos(w0), al = Math.sin(w0) / (2 * q);
            double a0 = 1 + al;
            bq[s][0] = (1 - cs) / 2 / a0; bq[s][1] = (1 - cs) / a0; bq[s][2] = bq[s][0];
            bq[s][3] = -2 * cs / a0; bq[s][4] = (1 - al) / a0;
        }

        // ================================================================ setters (control thread)
        public void setTuning(Tuning t) { if (t != null) { tuning = t; lastIdx = -1; } }
        public Tuning getTuning() { return tuning; }
        // A4 reference range 380..480 Hz, end points included (NaN fails both tests)
        public void setRefA4(double f) { if (f >= 380.0 && f <= 480.0) { refA4 = f; lastIdx = -1; } }
        public double getRefA4() { return refA4; }
        public void setHold(boolean h) { hold = h; }
        public void setThresholdRms(double v) { if (v > 1e-6 && v < 1) thresholdRms = v; }
        public void setRange(int r) {
            if (r < 0 || r > 3) r = 0;
            range = r; rangeLo = RANGE_LO[r]; rangeHi = RANGE_HI[r];
        }
        /** r in 0..1: 0 = fast (3-point median) .. 1 = fine (9-point median). */
        public void setResponse(double r) {
            if (!(r >= 0)) r = 0; if (r > 1) r = 1;
            int w = 3 + 2 * (int) Math.round(r * 3);   // 3,5,7,9
            stabW = w;
        }

        // ================================================================ audio thread
        public void push(double x) {
            if (Double.isNaN(x) || Double.isInfinite(x)) x = 0;
            if (x > 20) x = 20; else if (x < -20) x = -20;
            // DC blocker, 15 Hz corner [MODEL]
            double y = x - dcX1 + 0.998 * dcY1; dcX1 = x; dcY1 = y;
            hi[hiPos] = (float) y; hiPos = (hiPos + 1) & (NHI - 1);
            pw += (y * y - pw) * pwK;
            double v = y;
            for (int s = 0; s < 2; s++) {
                double o = bq[s][0] * v + bqz[s][0];
                bqz[s][0] = bq[s][1] * v - bq[s][3] * o + bqz[s][1];
                bqz[s][1] = bq[s][2] * v - bq[s][4] * o;
                v = o;
            }
            if (++decCnt == DEC) { decCnt = 0; lo[loPos] = (float) v; loPos = (loPos + 1) & (N12 - 1); }
            pushed++;
        }

        public double toneSample() {
            if (!toneOn || calState == CAL_SETTLE || calState == CAL_MEASURE) return 0;
            toneHz += (toneHzTarget - toneHz) * 0.002;           // ~10 ms glide against clicks [GUESS]
            tonePhase += toneHz / FS; if (tonePhase >= 1) tonePhase -= 1;
            return toneLevel * Math.sin(2 * Math.PI * tonePhase);
        }
        public void setTone(boolean on, double hz, double levelVolts) {
            if (hz > 8 && hz < 12000) toneHzTarget = hz;
            if (!toneOn && on) toneHz = toneHzTarget;
            toneOn = on;
            if (levelVolts >= 0 && levelVolts <= 5) toneLevel = levelVolts;
        }

        // ================================================================ analysis (worker)
        /** Copies the rings into analysis arrays. Call from the audio thread or under the same lock as push. */
        public void prepare() {
            for (int i = 0; i < N12; i++) wl[i] = lo[(loPos + i) & (N12 - 1)];
            for (int i = 0; i < NHI; i++) wh[i] = hi[(hiPos + i) & (NHI - 1)];
            prepLevel = Math.sqrt(pw);
            prepRange = range;
            prepReady = pushed > (long) (N12 * DEC);            // window must be full
        }

        public void analyze() {
            level = prepLevel;
            if (!prepReady || prepLevel < thresholdRms) { estValid = false; return; }
            int lagMax = Math.min(N12 / 2 - 1, (int) Math.ceil(FS12 / rangeLo));
            int lagMin = Math.max(2, (int) Math.floor(FS12 / rangeHi));
            if (lagMin >= lagMax) { estValid = false; return; }
            nsdf(wl, 0, N12, 1, lagMax + 1, nsd);
            // key maxima
            int nk = 0, pos = 1;
            while (pos < lagMax && nsd[pos] > 0) pos++;               // skip first positive lobe
            while (pos < lagMax && nk < 64) {
                while (pos < lagMax && nsd[pos] <= 0) pos++;
                if (pos >= lagMax) break;
                int best = pos;
                while (pos < lagMax && nsd[pos] > 0) { if (nsd[pos] > nsd[best]) best = pos; pos++; }
                if (best >= lagMin && best > 1 && best < lagMax) { kmIdx[nk] = best; kmVal[nk] = nsd[best]; nk++; }
            }
            if (nk == 0) { estValid = false; return; }
            double mx = 0; for (int i = 0; i < nk; i++) if (kmVal[i] > mx) mx = kmVal[i];
            int pick = -1;
            for (int i = 0; i < nk; i++) if (kmVal[i] >= MPM_K * mx) { pick = i; break; }
            int i0 = kmIdx[pick];
            double a = nsd[i0 - 1], b = nsd[i0], c = nsd[i0 + 1];
            double den = a - 2 * b + c;
            double lag12 = i0 + (den < 0 ? 0.5 * (a - c) / den : 0);
            // refine on 48 kHz ring
            double lag0 = lag12 * DEC;
            int l0 = (int) Math.round(lag0);
            int wf = Math.min(NHI, Math.max(4096, 4 * l0));
            int base = NHI - wf;
            int lo48 = Math.max(2, l0 - 8), hi48 = Math.min(wf / 2 - 2, l0 + 8);
            if (hi48 <= lo48 + 2) { estValid = false; return; }
            int n = hi48 - lo48 + 1;
            double bestV = -2; int bi = lo48;
            double[] tmp = hiTmp;
            nsdf(wh, base, wf, lo48 - 1, hi48 + 1, tmp);   // tmp[k] for lag k (offset 0 index = lag)
            for (int l = lo48; l <= hi48; l++) if (tmp[l] > bestV) { bestV = tmp[l]; bi = l; }
            double fa = tmp[bi - 1], fb = tmp[bi], fc = tmp[bi + 1];
            double fd = fa - 2 * fb + fc;
            double lagF = bi + (fd < 0 ? 0.5 * (fa - fc) / fd : 0);
            double f = FS / lagF;
            double cf = bestV;
            if (!(f >= rangeLo * 0.9 && f <= rangeHi * 1.1) || cf < MIN_CONF) { estValid = false; return; }
            estHz = f; estConf = cf; estValid = true;
        }
        private final double[] hiTmp = new double[NHI / 2 + 8];

        /** NSDF for lags from..to (inclusive) of x[off .. off+len-1], results stored at out[lag]. */
        private static void nsdf(double[] x, int off, int len, int from, int to, double[] out) {
            for (int t = from; t <= to; t++) {
                double r = 0, m = 0;
                int end = off + len - t;
                for (int j = off; j < end; j++) {
                    double u = x[j], v = x[j + t];
                    r += u * v; m += u * u + v * v;
                }
                out[t] = m > 1e-18 ? 2 * r / m : 0;
            }
        }

        // ================================================================ stabiliser + mapper + CAL
        /** Call after analyze(). Updates published results and CAL. */
        public void update() {
            boolean ev = estValid; double eh = estHz, ec = estConf;
            if (!hold) {
                if (ev) {
                    invalidRun = 0;
                    double l = Math.log(eh) / Math.log(2);
                    if (stabN >= 3) {
                        double med = median();
                        if (Math.abs(l - med) * 1200 > 80) {              // possible note change / octave jump
                            if (candCount > 0 && Math.abs(l - cand) * 1200 < 30) candCount++;
                            else { cand = l; candCount = 1; }
                            if (candCount >= 2) { stabN = 0; candCount = 0; pushLog(l); }
                        } else { candCount = 0; pushLog(l); }
                    } else pushLog(l);
                    conf = ec;
                    publish();
                } else if (++invalidRun >= INVALID_HOLD_HOPS) {
                    stabN = 0; valid = false; idx = -1; lastIdx = -1;
                }
            }
            calUpdate(ev, eh);
        }
        private void pushLog(double l) {
            int w = stabW;
            if (stabN > w) stabN = w;
            if (stabN < w) stab[stabN++] = l;
            else { for (int i = 1; i < w; i++) stab[i - 1] = stab[i]; stab[w - 1] = l; }
        }
        private double median() {
            int n = Math.min(stabN, stabW);
            for (int i = 0; i < n; i++) scratch[i] = stab[stabN - n + i];
            for (int i = 1; i < n; i++) { double v = scratch[i]; int j = i - 1; while (j >= 0 && scratch[j] > v) { scratch[j + 1] = scratch[j]; j--; } scratch[j + 1] = v; }
            return scratch[n / 2];
        }
        private void publish() {
            double f = Math.pow(2, median());
            hz = f;
            map(f);
            valid = true;
            double c4 = refA4 * Math.pow(2, C4_OFFSET_FROM_A4_CENTS / 1200.0);
            cvPitchVolts = Math.log(f / c4) / Math.log(2);            // 0 V = C4 [OPEN: Voltage Modular convention]
            cvCentsVolts = Math.max(-5, Math.min(5, centsErr / 10.0)); // +-50 cents = +-5 V [GUESS]
        }

        /** Maps a frequency to the nearest tuning entry with hysteresis; fills the published fields. */
        public void map(double f) {
            Tuning t = tuning;
            double c = 1200.0 * Math.log(f / refA4) / Math.log(2);
            int i = t.nearest(c);
            if (lastIdx >= 0 && lastIdx < t.cents.length && lastIdx != i) {
                double e = c - t.cents[lastIdx];
                double dn = lastIdx > 0 ? (t.cents[lastIdx] - t.cents[lastIdx - 1]) / 2 : 1e9;
                double up = lastIdx + 1 < t.cents.length ? (t.cents[lastIdx + 1] - t.cents[lastIdx]) / 2 : 1e9;
                if (e > -dn - HYST_CENTS && e < up + HYST_CENTS) i = lastIdx;   // keep previous note near a boundary
            }
            lastIdx = i;
            idx = i;
            centsErr = c - t.cents[i];
            targetHz = refA4 * Math.pow(2, t.cents[i] / 1200.0);
            labelA = t.labA[i]; labelOct = t.labOct[i]; labelIsNote = t.noteNames; labelKind = t.kind;
        }
        static final double HYST_CENTS = 6.0;                       // [GUESS]

        /** Test helper: run one analysis hop inline. */
        public void tickHop() { prepare(); analyze(); update(); }

        // ---------------------------------------------------------------- CAL
        /** Start a calibration sweep: drive calCv from v0 in steps of stepV for n steps; the VCO must be patched to the IN jack. */
        public void calStart(double v0, double stepV, int n) {
            if (n < 3) n = 3; if (n > 16) n = 16;
            calV0 = v0; calStepV = stepV; calN = n; calStep = 0; calHop = 0; calAcc = 0; calAccN = 0;
            for (int i = 0; i < 16; i++) calOk[i] = false;
            calCv = v0; calState = CAL_SETTLE;
            hold = false;
        }
        public void calCancel() { calState = CAL_IDLE; calCv = 0; }
        public int calState() { return calState; }
        public int calStepIndex() { return calStep; }
        public int calSteps() { return calN; }

        private void calUpdate(boolean ev, double eh) {
            int s = calState;
            if (s != CAL_SETTLE && s != CAL_MEASURE) return;
            calHop++;
            if (s == CAL_SETTLE) {
                if (calHop >= CAL_SETTLE_HOPS) { calState = CAL_MEASURE; calHop = 0; calAcc = 0; calAccN = 0; }
                return;
            }
            if (ev) { calAcc += Math.log(eh) / Math.log(2); calAccN++; }
            if (calHop >= CAL_MEAS_HOPS) {
                if (calAccN >= CAL_MEAS_HOPS / 2) { calLog[calStep] = calAcc / calAccN; calOk[calStep] = true; }
                calStep++; calHop = 0; calAcc = 0; calAccN = 0;
                if (calStep >= calN) { calFinish(); return; }
                calCv = calV0 + calStep * calStepV; calState = CAL_SETTLE;
            }
        }
        public volatile int calTotalSteps;
        private void calFinish() {
            calCv = 0;
            calTotalSteps = calN;
            // keep only steps the detector can really follow: inside the current range edges
            boolean[] use = new boolean[16];
            int n = 0;
            for (int i = 0; i < calN; i++) {
                double hz = Math.pow(2, calLog[i]);
                use[i] = calOk[i] && hz >= rangeLo && hz <= rangeHi;
                if (use[i]) n++;
            }
            // drop the worst-fitting step while the fit is poor (more than 30 c off) and at least 4 steps remain
            double slope = 0, icpt = 0, mr = 0;
            while (true) {
                if (n < 3) { calGoodSteps = n; calState = CAL_FAIL; return; }
                double sx = 0, sy = 0;
                for (int i = 0; i < calN; i++) if (use[i]) { sx += calV0 + i * calStepV; sy += calLog[i]; }
                double mx = sx / n, my = sy / n, sxx = 0, sxy = 0;
                for (int i = 0; i < calN; i++) if (use[i]) { double dx = calV0 + i * calStepV - mx; sxx += dx * dx; sxy += dx * (calLog[i] - my); }
                slope = sxy / sxx; icpt = my - slope * mx;
                mr = 0; int worst = -1;
                for (int i = 0; i < calN; i++) if (use[i]) {
                    double r = Math.abs(calLog[i] - (icpt + slope * (calV0 + i * calStepV))) * 1200;
                    if (r > mr) { mr = r; worst = i; }
                }
                if (mr > CAL_OUTLIER_CENTS && n > 3) { use[worst] = false; n--; } else break;
            }
            calGoodSteps = n;
            calSlopeOctPerV = slope;
            calErrCentsPerOct = (slope - 1.0) * 1200.0;
            calMaxResidCents = mr;
            calHzAtV0 = Math.pow(2, icpt + slope * calV0);
            calState = CAL_DONE;
        }
        public static final double CAL_OUTLIER_CENTS = 30.0;

        // ================================================================ Tuning
        public static final class Tuning {
            public final String name;
            public final double[] cents;      // ascending, relative to A4 reference
            public final int[] labA, labOct;  // pitch class 0-11 (note names) or degree; octave
            public final boolean noteNames;   // kind 1
            public final int kind;            // 0 = degree numbers, 1 = 12-note names, 2 = quarter-tone names (labA = 2*pc + quarter)
            public final int per;             // degrees per period (what the TONE NOTE knob divides into)
            final int jFirst;                 // periodic tunings: generation index j of entry 0 (entry k has j = jFirst + k)
            final double[] absCents;          // absolute (.tun) tunings: cents by MIDI note 0..127, else null
            Tuning(String name, double[] cents, int[] labA, int[] labOct, int kind, int per, int jFirst, double[] absCents) {
                this.name = name; this.cents = cents; this.labA = labA; this.labOct = labOct; this.kind = kind; this.noteNames = (kind == 1);
                this.per = per; this.jFirst = jFirst; this.absCents = absCents;
            }
            /**
             * Table index of the tone note: the key root in octave `octave` (scientific numbering, root of C is C4 at octave 4),
             * plus `degree` steps up. Returns -1 when the note is outside the table.
             */
            public int toneIndex(int rootPc, int octave, int degree) {
                if (kind == 3 && absCents != null) {
                    // numbered .tun table: TONE OCT 0..7 selects a page of 16 notes, the tone knob picks one of the 16 (8 x 16 = all 128 notes; the key is not used)
                    int n = octave * 16 + degree;
                    return (degree < 0 || degree > 15 || n < 0 || n > 127) ? -1 : nearest(absCents[n]);
                }
                if (absCents != null) {
                    int midi = 12 * (octave + 1) + (((rootPc % 12) + 12) % 12) + degree;
                    if (midi < 0 || midi > 127) return -1;
                    return nearest(absCents[midi]);
                }
                int j = (octave - 4) * per + degree;
                int k = j - jFirst;
                return (k >= 0 && k < cents.length) ? k : -1;
            }
            public int nearest(double c) {
                int lo = 0, hi = cents.length - 1;
                if (c <= cents[0]) return 0;
                if (c >= cents[hi]) return hi;
                while (hi - lo > 1) { int m = (lo + hi) >>> 1; if (cents[m] <= c) lo = m; else hi = m; }
                return (c - cents[lo] <= cents[hi] - c) ? lo : hi;
            }
            public int size() { return cents.length; }
        }

        public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

        public static final class Tunings {
            private Tunings() {}

            /** Periodic scale: degrees[] in cents from the root (degrees[0]=0, ascending, < period). */
            public static Tuning periodic(String name, double[] degrees, double period, int rootPc) {
                boolean names = (degrees.length == 12 && Math.abs(period - 1200) < 1e-6);
                return periodic(name, degrees, period, rootPc, names ? 1 : 0);
            }
            public static Tuning periodic(String name, double[] degrees, double period, int rootPc, int kind) {
                return periodic(name, degrees, period, rootPc, kind, false);
            }
            /** Named 12-note temperament: the note called A is pinned to the A4 reference, whatever the key. */
            public static Tuning periodicNamed(String name, double[] degrees, int rootPc) {
                return periodic(name, degrees, 1200, rootPc, 1, true);
            }
            public static Tuning periodic(String name, double[] degrees, double period, int rootPc, int kind, boolean anchorA) {
                int per = degrees.length;
                boolean names = (kind == 1);
                int firstJ = Integer.MAX_VALUE;
                int rootMidi = 60 + ((rootPc % 12) + 12) % 12;
                double rootCents = (rootMidi - 69) * 100.0;
                if (anchorA && kind == 1 && per == 12 && Math.abs(period - 1200.0) < 1e-6) {
                    // The note called A is pinned to the A4 reference (0 cents), whatever the key and temperament.
                    // (The scale degrees are relative to the key's root; the table is then shifted so the A degree lands exactly on A4.)
                    int dA = Math.floorMod(9 - rootPc, 12);                         // degree number of the note called A
                    double aEt = rootCents + dA * 100.0;                            // where an equal-tempered A would sit relative to A4
                    double octShift = -Math.rint(aEt / 1200.0);                     // whole octaves that bring that A next to A4
                    rootCents = -(octShift * 1200.0 + degrees[dA]);                 // root placed so the A degree lands exactly on 0 cents (A4)
                }
                int cap = per * 140 + 8;
                double[] c = new double[cap]; int[] la = new int[cap], lo = new int[cap]; int n = 0;
                for (int j = -per * 8; j <= per * 8; j++) {
                    int oct = Math.floorDiv(j, per), d = Math.floorMod(j, per);
                    double cc = rootCents + oct * period + degrees[d];
                    if (cc < -6950 || cc > 5850) continue;
                    if (firstJ == Integer.MAX_VALUE) firstJ = j;
                    c[n] = cc;
                    if (kind == 2) { int midi = rootMidi + oct * 12 + d / 2; la[n] = 2 * Math.floorMod(midi, 12) + (d & 1); lo[n] = Math.floorDiv(midi, 12) - 1; }
                    else if (names) { int midi = rootMidi + oct * 12 + d; la[n] = Math.floorMod(midi, 12); lo[n] = Math.floorDiv(midi, 12) - 1; }
                    else { la[n] = d; lo[n] = 4 + oct; }
                    n++;
                }
                return new Tuning(name, java.util.Arrays.copyOf(c, n), java.util.Arrays.copyOf(la, n), java.util.Arrays.copyOf(lo, n), kind, per, firstJ, null);
            }
            public static Tuning equal(int rootPc) {
                double[] d = new double[12]; for (int i = 0; i < 12; i++) d[i] = i * 100.0;
                return periodicNamed("12-TET", d, rootPc);
            }
            /** 24-tone equal temperament (quarter tones): the usual keyboard grid for Arabic music [MODEL, general background knowledge]. */
            public static Tuning quarterTone(int rootPc) {
                double[] d = new double[24]; for (int i = 0; i < 24; i++) d[i] = i * 50.0;
                return periodic("24-EDO quarter-tone", d, 1200, rootPc, 2);
            }
            /** Equal-tempered Bohlen-Pierce: 13 equal steps of the 3:1 tritave (1901.955 c), no octave [MODEL]. Octave label counts tritaves. */
            public static Tuning bohlenPierce(int rootPc) {
                double period = 1200.0 * Math.log(3.0) / Math.log(2.0);
                double[] d = new double[13]; for (int i = 0; i < 13; i++) d[i] = i * period / 13.0;
                return periodic("Bohlen-Pierce 13-EDT", d, period, rootPc, 0);
            }
            public static Tuning edo(int n, int rootPc) {
                double[] d = new double[n]; for (int i = 0; i < n; i++) d[i] = i * 1200.0 / n;
                return periodic(n + "-EDO", d, 1200, rootPc);
            }
            static double[] chain(double[] fifthCents, int[] chainPc) {
                // chainPc[k] = pitch-class step (0-11, from C) of the k-th note up the chain; fifthCents[k] = cents from chain[k] to chain[k+1]
                double[] deg = new double[12]; double pos = 0;
                for (int k = 0; k < 12; k++) {
                    if (k > 0) pos += fifthCents[k - 1];
                    double c = ((pos % 1200) + 1200) % 1200;
                    deg[chainPc[k]] = c;
                }
                return deg;
            }
            // chain of fifths starting at C: C G D A E B F# C# G# D# A# F
            static final int[] CHAIN_FROM_C = {0, 7, 2, 9, 4, 11, 6, 1, 8, 3, 10, 5};
            public static Tuning werckmeister3(int rootPc) {
                double pyth = 701.9550008653874, narrow = pyth - 23.46001038465 / 4.0;
                // tempered fifths: C-G, G-D, D-A, B-F#; all others pure [READ: standard definition]
                double[] f = {narrow, narrow, narrow, pyth, pyth, narrow, pyth, pyth, pyth, pyth, pyth};
                return periodicNamed("Werckmeister III", chain(f, CHAIN_FROM_C), rootPc);
            }
            public static Tuning meantoneQuarterComma(int rootPc) {
                double fifth = 1200.0 * Math.log(5.0) / Math.log(2.0) / 4.0;     // 696.578
                // chain C G D A E B F# C# G# then F, Bb, Eb downward (wolf between G# and Eb)
                double[] deg = new double[12];
                int[] up = {0, 7, 2, 9, 4, 11, 6, 1, 8};
                for (int k = 0; k < up.length; k++) deg[up[k]] = (((k * fifth) % 1200) + 1200) % 1200;
                int[] dn = {5, 10, 3};
                for (int k = 0; k < dn.length; k++) deg[dn[k]] = ((((-(k + 1)) * fifth) % 1200) + 1200) % 1200;
                return periodicNamed("1/4-comma meantone", deg, rootPc);
            }
            public static Tuning pythagorean(int rootPc) {
                // Twelve pure fifths (3:2 = 701.955 c) laid out Eb Bb F C G D A E B F# C# G#; the wolf fifth falls between G# and Eb, far from C.
                double p = 701.9550008653874;
                double[] deg = new double[12];
                int[] pc = {3, 10, 5, 0, 7, 2, 9, 4, 11, 6, 1, 8};          // pitch classes at -3 .. +8 fifths from C
                for (int k = -3; k <= 8; k++) deg[pc[k + 3]] = (((k * p) % 1200.0) + 1200.0) % 1200.0;
                return periodicNamed("Pythagorean", deg, rootPc);
            }
            public static Tuning justIntonation(int rootPc) {
                double[] r = {1, 16.0 / 15, 9.0 / 8, 6.0 / 5, 5.0 / 4, 4.0 / 3, 45.0 / 32, 3.0 / 2, 8.0 / 5, 5.0 / 3, 9.0 / 5, 15.0 / 8};
                double[] d = new double[12]; for (int i = 0; i < 12; i++) d[i] = 1200.0 * Math.log(r[i]) / Math.log(2);
                return periodicNamed("5-limit just", d, rootPc);
            }
        }

        // ================================================================ parsers
        /** Scala .scl text. Returns null when the text is not a usable scale. */
        public static Tuning parseScl(String text, String name, int rootPc) {
            if (text == null) return null;
            String[] lines = text.split("\\r?\\n");
            int stage = 0, count = -1; java.util.ArrayList<Double> deg = new java.util.ArrayList<Double>();
            for (String raw : lines) {
                String s = raw.trim();
                if (s.startsWith("!")) continue;
                if (stage == 0) { stage = 1; continue; }                 // description (may be empty)
                if (stage == 1) {
                    if (s.isEmpty()) continue;
                    try { count = Integer.parseInt(firstToken(s)); } catch (NumberFormatException e) { return null; }
                    if (count < 1 || count > 128) return null;
                    stage = 2; continue;
                }
                if (s.isEmpty()) continue;
                if (deg.size() >= count) break;
                Double v = sclValue(firstToken(s));
                if (v == null) return null;
                deg.add(v);
            }
            if (stage < 2 || deg.size() != count) return null;
            double period = deg.get(count - 1);
            if (!(period > 1.0)) return null;
            double[] d = new double[count]; d[0] = 0;
            for (int i = 1; i < count; i++) d[i] = deg.get(i - 1);
            for (int i = 1; i < count; i++) if (!(d[i] > d[i - 1]) || d[i] >= period) return null;
            return Tunings.periodic(name == null ? "SCL" : name, d, period, rootPc);
        }
        private static String firstToken(String s) { int i = 0; while (i < s.length() && !Character.isWhitespace(s.charAt(i))) i++; return s.substring(0, i); }
        private static Double sclValue(String tok) {
            try {
                if (tok.indexOf('.') >= 0) return Double.valueOf(tok);                          // cents
                int sl = tok.indexOf('/');
                double num, den = 1;
                if (sl >= 0) { num = Double.parseDouble(tok.substring(0, sl)); den = Double.parseDouble(tok.substring(sl + 1)); }
                else num = Double.parseDouble(tok);
                if (!(num > 0) || !(den > 0)) return null;
                return Double.valueOf(1200.0 * Math.log(num / den) / Math.log(2));
            } catch (NumberFormatException e) { return null; }
        }

        /**
         * AnaMark .tun text. [Exact Tuning] (BaseFreq + real cents) is preferred over [Tuning] (integer cents).
         * Cents are measured from MIDI note 0, whose frequency is BaseFreq (default 8.1757989156 Hz) [READ via secondary source:
         * hmt Anamark.hs and the xen wiki; the v2 PDF itself was not read].
         */
        public static Tuning parseTun(String text, String name) {
            if (text == null) return null;
            double[] exact = new double[128], integer = new double[128];
            boolean[] hasE = new boolean[128], hasI = new boolean[128];
            double base = MIDI0_HZ; boolean baseSet = false;
            String sec = "";
            for (String raw : text.split("\\r?\\n")) {
                String s = raw.trim();
                if (s.isEmpty() || s.startsWith(";")) continue;
                if (s.startsWith("[")) { int e = s.indexOf(']'); sec = (e > 0 ? s.substring(1, e) : s.substring(1)).trim().toLowerCase(); continue; }
                int eq = s.indexOf('='); if (eq < 0) continue;
                String k = s.substring(0, eq).trim().toLowerCase(), v = s.substring(eq + 1).trim();
                if (sec.equals("exact tuning") && k.equals("basefreq")) {
                    try { double b = Double.parseDouble(v); if (b > 0.1 && b < 100) { base = b; baseSet = true; } } catch (NumberFormatException e) { }
                } else if (k.startsWith("note")) {
                    int n; double c;
                    try { n = Integer.parseInt(k.substring(4).trim()); c = Double.parseDouble(v); } catch (NumberFormatException e) { continue; }
                    if (n < 0 || n > 127) continue;
                    if (sec.equals("exact tuning")) { exact[n] = c; hasE[n] = true; }
                    else if (sec.equals("tuning")) { integer[n] = c; hasI[n] = true; }
                }
            }
            int cE = 0, cI = 0; for (int i = 0; i < 128; i++) { if (hasE[i]) cE++; if (hasI[i]) cI++; }
            boolean useE = cE >= 12; if (!useE && cI < 12) return null;
            double[] cc = new double[128]; boolean[] has = new boolean[128];
            int n = 0;
            for (int i = 0; i < 128; i++) {
                boolean h = useE ? hasE[i] : hasI[i];
                if (!h) { // fall back to 12-TET for gaps [GUESS]
                    cc[i] = i * 100.0 + 0; has[i] = true;
                    double hzI = MIDI0_HZ * Math.pow(2, cc[i] / 1200.0);
                    cc[i] = 1200.0 * Math.log(hzI / 440.0) / Math.log(2);
                } else {
                    double b = useE ? base : MIDI0_HZ;
                    double hzI = b * Math.pow(2, (useE ? exact[i] : integer[i]) / 1200.0);
                    cc[i] = 1200.0 * Math.log(hzI / 440.0) / Math.log(2);   // relative to A4=440; the A4 knob shifts all [GUESS]
                    has[i] = true;
                }
            }
            // Does this table look like a 12-notes-per-octave keyboard tuning (note n+12 about 1200 c above note n)? Then MIDI note names make sense;
            // otherwise (Partch 43, Carlos Alpha ...) the labels are the .tun's own note numbers.
            int near = 0, tot = 0;
            for (int i = 0; i + 12 < 128; i++) { tot++; if (Math.abs(cc[i + 12] - cc[i] - 1200.0) < 30.0) near++; }
            boolean keyboard12 = near >= 0.9 * tot;
            Integer[] order = new Integer[128]; for (int i = 0; i < 128; i++) order[i] = i;
            final double[] ccf = cc;
            java.util.Arrays.sort(order, new java.util.Comparator<Integer>() { public int compare(Integer a, Integer b) { return Double.compare(ccf[a], ccf[b]); } });
            double[] c = new double[128]; int[] la = new int[128], lo = new int[128];
            for (int i = 0; i < 128; i++) { int m = order[i]; c[i] = cc[m]; la[i] = keyboard12 ? m % 12 : m; lo[i] = keyboard12 ? m / 12 - 1 : 0; }
            return new Tuning(name == null ? "TUN" : name, c, la, lo, keyboard12 ? 1 : 3, keyboard12 ? 12 : 16, 0, cc);   // numbered tables: 16 notes per tone page
        }

        // ================================================================ helpers
        /** kind 2: labA = 2*pitchClass + quarter; quarter-sharp is written with a trailing '+' (D+ = D plus 50 cents). */
        public static String labelText(int kind, int a, int oct) {
            if (kind == 3) return "#" + a;
            if (kind == 2) return NOTE_NAMES[(((a >> 1) % 12) + 12) % 12] + ((a & 1) == 1 ? "+" : "") + oct;
            return labelText(kind == 1, a, oct);
        }
        public static String labelText(boolean isNote, int a, int oct) {
            return isNote ? NOTE_NAMES[((a % 12) + 12) % 12] + oct : "d" + a + " o" + oct;
        }
        public static double hzOf(Tuning t, int i, double refA4) { return refA4 * Math.pow(2, t.cents[i] / 1200.0); }
    }

    // ---- sn93t module layer ----
    private static final double VOLTS = 5.0;                      // core 1.0 = 5 V
    private static final double INV_VOLTS = 1.0 / VOLTS;
    // [MEASURED v0.1.9] a 1 V/oct VCO tracks exactly (CAL -960.16 c/oct, resid 0.05 c) when the CV jack carries 0.2 per commanded volt, i.e. 1.0 jack unit = 1 octave = 1 V.
    // So the 1 V/oct paths (pitch CV, CAL CV) use jack value = volts. Audio, tone level and the cents CV keep the VOLTS scale ([OPEN]).
    private static final double CV_JACK_PER_VOLT = 1.0;
    private static final String FILE_TYPES = "*.tun;*.scl";       // [OPEN] filter syntax of ShowOpenFileDialog (JUCE-style wildcard assumed)
    private static final int TEMP_COUNT = 10, TEMP_CUSTOM = 9;    // TEMP knob values 1..10
    private static final double CAL_START_VOLTS = -1.0, CAL_STEP_VOLTS = 1.0;
    private static final long TIMER_MS = 33;
    private static final double A4_MIN_HZ = 380.0, A4_MAX_HZ = 480.0;
    // Range slide: position 0 (full down) = HIGH, 1 = MID, 2 = LOW, 3 = AUTO (author's note on switchRange)
    private static final int[] SWITCH_TO_RANGE = { TnrCore.RANGE_HIGH, TnrCore.RANGE_MID, TnrCore.RANGE_LOW, TnrCore.RANGE_AUTO };
    private static final String[] RANGE_NAMES = { "AUTO", "LOW", "MID", "HIGH" };

    private final TnrCore core = new TnrCore();
    private boolean ready = false;
    private volatile boolean muteOn = false;
    private int keyPc = 0, tempIdx = 0;
    private int builtKey = -1, builtTemp = -1, builtVersion = -1, customVersion = 0;
    private String customText = "", customExt = "", customName = "";
    private boolean followTone = true;
    private boolean toneOnFlag = false;
    private double toneHzLast = 440.0;
    private String toneLabel = "";
    private String statusMsg = "";
    private long statusUntil = 0L, calShownUntil = 0L;
    private int lastCalState = TnrCore.CAL_IDLE;
    // audio-thread state
    private int hopCount = 0;
    private double lastPitchV = 0.0, lastCentsV = 0.0;
    // analysis worker
    private volatile int jobState = 0;                            // 0 idle, 1 requested by audio thread, 2 running on worker
    private volatile boolean workerRun = false;
    private Thread worker;

    private static double clamp01(double p) { return p < 0.0 ? 0.0 : (p > 1.0 ? 1.0 : p); }
    private static int clampInt(int v, int lo, int hi) { return v < lo ? lo : (v > hi ? hi : v); }
    private static String fmt(String f, double v) { return String.format(java.util.Locale.ROOT, f, v); }

    private static double readVolts(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double v = jack.GetValue();
        return Double.isFinite(v) ? v * VOLTS : 0.0;
    }

    // ---------------------------------------------------------------- controls -> core
    private double knobFraction(VoltageKnob k) {
        double lo = k.GetMinValue(), hi = k.GetMaxValue();
        return hi > lo ? clamp01((k.GetValue() - lo) / (hi - lo)) : 0.0;
    }

    private double refHz() {
        return Math.max(A4_MIN_HZ, Math.min(A4_MAX_HZ, knobRef.GetValue()));      // the A4 knob is in Hz (380..480, 100 steps)
    }

    private void applyAll() {
        keyPc = clampInt((int) Math.round(knobKey.GetValue()) - 1, 0, 11);
        tempIdx = clampInt((int) Math.round(knobTemp.GetValue()) - 1, 0, TEMP_COUNT - 1);
        rebuildTuning();
        core.setResponse(knobFraction(knobResponse));
        core.setRefA4(refHz());
        core.setRange(SWITCH_TO_RANGE[clampInt((int) Math.round(switchRange.GetValue()), 0, 3)]);
        core.setHold(buttonHold.GetValue() > 0.5);
        muteOn = buttonMute.GetValue() > 0.5;
        toneOnFlag = buttonTone.GetValue() > 0.5;
        followTone = switchSource.GetValue() > 0.5;                                  // up = follow (1), down = manual (0)
        updateTone();
    }

    private void rebuildTuning() {
        if (builtKey == keyPc && builtTemp == tempIdx && builtVersion == customVersion) {
            return;
        }
        TnrCore.Tuning t;
        switch (tempIdx) {
            case 0:  t = TnrCore.Tunings.equal(keyPc); break;
            case 1:  t = TnrCore.Tunings.werckmeister3(keyPc); break;
            case 2:  t = TnrCore.Tunings.meantoneQuarterComma(keyPc); break;
            case 3:  t = TnrCore.Tunings.edo(19, keyPc); break;
            case 4:  t = TnrCore.Tunings.quarterTone(keyPc); break;
            case 5:  t = TnrCore.Tunings.edo(53, keyPc); break;
            case 6:  t = TnrCore.Tunings.bohlenPierce(keyPc); break;
            case 7:  t = TnrCore.Tunings.justIntonation(keyPc); break;
            case 8:  t = TnrCore.Tunings.pythagorean(keyPc); break;
            default: t = buildCustom(); break;
        }
        builtKey = keyPc; builtTemp = tempIdx; builtVersion = customVersion;
        core.setTuning(t);
    }

    private TnrCore.Tuning buildCustom() {
        TnrCore.Tuning t = null;
        if (customText.length() > 0) {
            t = customExt.equals("scl") ? TnrCore.parseScl(customText, customName, keyPc) : TnrCore.parseTun(customText, customName);
        }
        if (t == null) {
            setStatus(customText.length() == 0 ? "NO TUNING LOADED - 12-TET" : "BAD TUNING FILE - 12-TET");
            t = TnrCore.Tunings.equal(keyPc);
        }
        return t;
    }

    private void setStatus(String s) {
        statusMsg = s;
        statusUntil = System.currentTimeMillis() + 4000L;
    }

    private void updateTone() {
        TnrCore.Tuning t = core.getTuning();
        if (followTone) {
            if (core.valid) {
                toneHzLast = core.targetHz;
                toneLabel = TnrCore.labelText(core.labelKind, core.labelA, core.labelOct);
            }
        } else {
            int per = t.per;
            int deg = (int) Math.floor(clamp01(knobFraction(knobTone)) * per);
            if (deg > per - 1) deg = per - 1;
            int oct = clampInt((int) Math.round(knob66.GetValue()), 0, 7);
            int idx = t.toneIndex(keyPc, oct, deg);
            if (idx >= 0) {
                toneHzLast = TnrCore.hzOf(t, idx, core.getRefA4());
                toneLabel = TnrCore.labelText(t.kind, t.labA[idx], t.labOct[idx]);
            }
        }
        double dB = -40.0 + 40.0 * knobFraction(knobLevel);
        core.setTone(toneOnFlag, toneHzLast, VOLTS * Math.pow(10.0, dB / 20.0));
    }

    // ---------------------------------------------------------------- CAL, LOAD
    private void calButton(boolean on) {
        int s = core.calState();
        boolean running = (s == TnrCore.CAL_SETTLE || s == TnrCore.CAL_MEASURE);
        if (on && !running) {
            if (!input.IsConnected()) {
                setStatus("PATCH THE VCO TO IN");
                buttonCal.SetValue(0.0);
                return;
            }
            int octaves = clampInt((int) Math.round(knob68.GetValue()), 0, 7) + 2;     // CAL SPAN legend 2..9
            buttonHold.SetValue(0.0);
            core.calStart(CAL_START_VOLTS, CAL_STEP_VOLTS, octaves + 1);
            calShownUntil = Long.MAX_VALUE;
        } else if (!on && running) {
            core.calCancel();
            calShownUntil = 0L;
        }
    }

    private void doLoad() {
        String path = ShowOpenFileDialog("Load tuning (.tun / .scl)", "", FILE_TYPES);
        buttonLoad.SetValue(0.0);
        if (path != null && path.length() > 0) {
            loadFile(path);
        }
    }

    private void loadFile(String path) {
        try {
            java.nio.file.Path p = java.nio.file.Paths.get(path);
            byte[] b = java.nio.file.Files.readAllBytes(p);
            if (b.length == 0 || b.length > 1000000) {
                setStatus("BAD TUNING FILE");
                return;
            }
            String text = new String(b, java.nio.charset.StandardCharsets.UTF_8);
            String fn = p.getFileName() == null ? "custom" : p.getFileName().toString();
            String ext = fn.toLowerCase().endsWith(".scl") ? "scl" : "tun";
            String nm = fn.indexOf('.') > 0 ? fn.substring(0, fn.lastIndexOf('.')) : fn;
            boolean ok = ext.equals("scl") ? TnrCore.parseScl(text, nm, keyPc) != null : TnrCore.parseTun(text, nm) != null;
            if (!ok) {
                setStatus("BAD TUNING FILE");
                return;
            }
            customText = text; customExt = ext; customName = nm;
            customVersion++;
            knobTemp.SetValue((double) TEMP_CUSTOM + 1);                 // select CUSTOM (knob values are 1-based)
            applyAll();
            setStatus("LOADED " + nm.toUpperCase());
        } catch (Throwable t) {
            setStatus("NO FILE ACCESS");
        }
    }

    // ---------------------------------------------------------------- GUI timer and display
    private void tick() {
        updateTone();

        int s = core.calState();
        if (s != lastCalState) {
            if (s == TnrCore.CAL_DONE || s == TnrCore.CAL_FAIL) {
                calShownUntil = System.currentTimeMillis() + 15000L;
                buttonCal.SetValue(0.0);
            }
            lastCalState = s;
        }
        canvas1.Invalidate();
    }

    private static final Color C_BG = new Color(8, 17, 28), C_TEXT = new Color(230, 225, 212), C_DIM = new Color(155, 179, 200),
        C_OK = new Color(95, 224, 160), C_WARN = new Color(240, 179, 74), C_BAD = new Color(239, 106, 95),
        C_OFF = new Color(27, 42, 61), C_ACC = new Color(236, 106, 99);
    private static final Font F_BIG = new Font("Arial", Font.BOLD, 46), F_ACC = new Font("Arial", Font.BOLD, 20), F_SUB = new Font("Arial", Font.BOLD, 16),
        F_CENTS = new Font("Monospaced", Font.BOLD, 26), F_MID = new Font("Monospaced", Font.BOLD, 11), F_SMALL = new Font("Arial", Font.BOLD, 9),
        F_TINY = new Font("Arial", Font.BOLD, 8), F_VAL = new Font("Monospaced", Font.BOLD, 10);

    private static Color meterColor(double cents) {
        double a = Math.abs(cents);
        return a <= 2.5 ? C_OK : (a <= 15.0 ? C_WARN : C_BAD);
    }

    private static Color withAlpha(Color c, int a) { return new Color(c.getRed(), c.getGreen(), c.getBlue(), a); }

    private static void drawStr(Graphics2D g, String s, int x, int y, Font f, Color c) {
        g.setFont(f); g.setColor(c); g.drawString(s, x, y);
    }

    private static void drawRight(Graphics2D g, String s, int xRight, int y, Font f, Color c) {
        g.setFont(f); g.setColor(c);
        g.drawString(s, xRight - g.getFontMetrics().stringWidth(s), y);
    }

    /** Shorten s with "..." until it fits maxW pixels in font f. */
    private static String fit(Graphics2D g, Font f, String s, int maxW) {
        g.setFont(f);
        java.awt.FontMetrics fm = g.getFontMetrics();
        if (fm.stringWidth(s) <= maxW) {
            return s;
        }
        String e = "...";
        int n = s.length();
        while (n > 0 && fm.stringWidth(s.substring(0, n) + e) > maxW) {
            n--;
        }
        return n > 0 ? s.substring(0, n) + e : "";
    }

    private void paint(Object object) {
        Graphics2D g = (object instanceof Graphics2D) ? (Graphics2D) object : canvas1.GetGraphics();   // [OPEN] what Canvas_Painting passes
        if (g != null) {
            paintDisplay(g, canvas1.GetWidth(), canvas1.GetHeight());
        }
    }

    private void paintDisplay(Graphics2D g, int w, int h) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(C_BG);
        g.fillRect(0, 0, w, h);
        long now = System.currentTimeMillis();
        boolean valid = core.valid;
        double cents = core.centsErr;
        TnrCore.Tuning t = core.getTuning();
        int calS = core.calState();
        boolean calBusy = (calS == TnrCore.CAL_SETTLE || calS == TnrCore.CAL_MEASURE);

        // top strip
        String a4Text = "A4 " + fmt("%.1f", core.getRefA4());
        String keyText = "  KEY " + TnrCore.NOTE_NAMES[keyPc];
        g.setFont(F_SMALL);
        int a4W = g.getFontMetrics().stringWidth(a4Text), keyW = g.getFontMetrics().stringWidth(keyText);
        drawStr(g, fit(g, F_SMALL, t.name.toUpperCase(), w - 12 - a4W - 8 - keyW) + keyText, 6, 10, F_SMALL, C_DIM);   // long file names are shortened
        drawRight(g, a4Text, w - 6, 10, F_SMALL, C_DIM);

        // note name
        String main = "-", acc = "", sub = "";
        if (valid) {
            int a = core.labelA;
            if (core.labelKind == 0) {
                main = String.valueOf(a); sub = "o" + core.labelOct;
            } else if (core.labelKind == 3) {
                main = String.valueOf(a);                                   // .tun that is not a 12-note keyboard tuning: the file's own note number
            } else {
                String nm = TnrCore.NOTE_NAMES[((core.labelKind == 2 ? (a >> 1) : a) % 12 + 12) % 12];
                main = nm.substring(0, 1);
                acc = nm.substring(1) + ((core.labelKind == 2 && (a & 1) == 1) ? "+" : "");
                sub = String.valueOf(core.labelOct);
            }
        }
        g.setFont(F_BIG);
        int wm = g.getFontMetrics().stringWidth(main);
        drawStr(g, main, 8, 56, F_BIG, valid ? C_TEXT : C_DIM);
        if (acc.length() > 0) drawStr(g, acc, 8 + wm + 1, 30, F_ACC, C_DIM);
        if (sub.length() > 0) drawStr(g, sub, 8 + wm + 1, 56, F_SUB, C_TEXT);

        // cents readout and state pill
        Color sc = valid ? meterColor(cents) : C_DIM;
        String ct = valid ? ((cents >= 0 ? "+" : "-") + fmt("%.1f", Math.abs(cents))) : "- -";
        drawRight(g, ct, w - 18, 38, F_CENTS, sc);
        drawStr(g, "¢", w - 16, 38, F_SUB, C_DIM);
        String st = !valid ? "NO SIGNAL" : (Math.abs(cents) <= 2.5 ? "IN TUNE" : (cents > 0 ? "SHARP" : "FLAT"));
        g.setFont(F_TINY);
        int pw = g.getFontMetrics().stringWidth(st) + 12;
        g.setColor(sc);
        g.fillRoundRect(w - 6 - pw, 44, pw, 12, 10, 10);
        drawStr(g, st, w - 6 - pw + 6, 53, F_TINY, C_BG);

        // frequency row (CAL messages take it over)
        if (calBusy || (calS != TnrCore.CAL_IDLE && now < calShownUntil)) {
            String m;
            if (calBusy) {
                m = "CAL " + (core.calStepIndex() + 1) + "/" + core.calSteps() + (calS == TnrCore.CAL_SETTLE ? " SETTLE" : " MEASURE")
                    + (valid ? "  " + fmt("%.2f", core.hz) + " Hz" : "");
            } else if (calS == TnrCore.CAL_DONE) {
                double e = core.calErrCentsPerOct;
                m = "CAL " + (e >= 0 ? "+" : "-") + fmt("%.2f", Math.abs(e)) + " c/oct  RESID " + fmt("%.2f", core.calMaxResidCents) + " c"
                    + (core.calGoodSteps < core.calTotalSteps ? "  (" + core.calGoodSteps + "/" + core.calTotalSteps + " STEPS)" : "");
            } else {
                m = "CAL FAILED - NEED 3 STEPS IN RANGE";
            }
            drawStr(g, m, 6, 70, F_MID, C_ACC);
        } else if (valid) {
            drawStr(g, fmt("%.2f", core.hz) + " Hz", 6, 70, F_MID, C_DIM);
            drawRight(g, "TARGET " + fmt("%.2f", core.targetHz), w - 6, 70, F_MID, C_DIM);
        }

        // coarse meter +-50 c, 25 segments of 4 c
        int x0 = 6, span = w - 12;
        int idx = valid ? clampInt((int) Math.round(Math.max(-50.0, Math.min(50.0, cents)) / 4.0), -12, 12) : 99;
        double segW = (span - 24 * 1.5) / 25.0;
        for (int i = -12; i <= 12; i++) {
            double x = x0 + (i + 12) * (segW + 1.5);
            boolean on = valid && i == idx;
            boolean trail = valid && ((idx > 0 && i > 0 && i < idx) || (idx < 0 && i < 0 && i > idx));
            Color c = meterColor(i * 4.0);
            g.setColor(on ? c : (trail ? withAlpha(c, 90) : C_OFF));
            int hh = i == 0 ? 15 : 11;
            g.fillRoundRect((int) Math.round(x), 76 + (15 - hh) / 2, (int) Math.max(2, Math.round(segW)), hh, 2, 2);
        }
        // fine meter +-5 c, 21 segments of 0.5 c
        int fidx = valid ? clampInt((int) Math.round(Math.max(-5.0, Math.min(5.0, cents)) * 2.0), -10, 10) : 99;
        double fw = (span - 20 * 1.5) / 21.0;
        for (int i = -10; i <= 10; i++) {
            double x = x0 + (i + 10) * (fw + 1.5);
            boolean on = valid && i == fidx;
            Color c = (Math.abs(cents) > 5.0 && (i == -10 || i == 10)) ? C_BAD : meterColor(i / 2.0);
            g.setColor(on ? c : C_OFF);
            int hh = i == 0 ? 9 : 6;
            g.fillRoundRect((int) Math.round(x), 94 + (9 - hh) / 2, (int) Math.max(2, Math.round(fw)), hh, 2, 2);
        }

        // bottom strip: signal bars, range, flags, status
        double lvl = core.level;
        int bars = lvl >= 0.002 ? clampInt((int) Math.ceil(20.0 * Math.log10(lvl / 0.002) / 8.0), 1, 8) : 0;
        for (int i = 0; i < 8; i++) {
            g.setColor(i < bars ? C_OK : C_OFF);
            g.fillRect(6 + i * 4, 108 - (2 + i), 3, 2 + i);
        }
        String rn = RANGE_NAMES[SWITCH_TO_RANGE[clampInt((int) Math.round(switchRange.GetValue()), 0, 3)]];
        String flags = rn + (buttonHold.GetValue() > 0.5 ? "  HOLD" : "") + (muteOn ? "  MUTE" : "");
        drawStr(g, flags, 42, 108, F_TINY, C_DIM);
        g.setFont(F_TINY);
        int roomW = w - 6 - (42 + g.getFontMetrics().stringWidth(flags) + 8);          // what is left of the bottom strip after the flags
        if (now < statusUntil) {
            drawRight(g, fit(g, F_TINY, statusMsg, roomW), w - 6, 108, F_TINY, C_WARN);
        } else if (toneOnFlag && toneLabel.length() > 0) {
            // the note and frequency are the part that must stay readable: monospaced and bright; the small red label shortens first when the strip is crowded
            String val = fit(g, F_VAL, toneLabel + " " + fmt("%.2f", toneHzLast), roomW);
            g.setFont(F_VAL);
            int valW = g.getFontMetrics().stringWidth(val);
            String[] labels = followTone ? new String[] { "TONE FOLLOW", "TONE", "" } : new String[] { "TONE", "" };
            String lab = "";
            for (int i = 0; i < labels.length; i++) {
                g.setFont(F_TINY);
                if (labels[i].length() == 0 || g.getFontMetrics().stringWidth(labels[i]) + 4 + valW <= roomW) {
                    lab = labels[i];
                    break;
                }
            }
            drawRight(g, val, w - 6, 108, F_VAL, C_TEXT);
            if (lab.length() > 0) {
                drawRight(g, lab, w - 6 - valW - 4, 108, F_TINY, C_ACC);
            }
        }
    }

    // ---------------------------------------------------------------- analysis worker
    private void startWorker() {
        if (worker != null) {
            return;
        }
        workerRun = true;
        worker = new Thread(new Runnable() {
            public void run() {
                while (workerRun) {
                    try {
                        if (jobState == 1) {
                            jobState = 2;
                            core.analyze();
                            core.update();
                            jobState = 0;
                        } else {
                            Thread.sleep(3L);
                        }
                    } catch (Throwable e) {
                        jobState = 0;
                    }
                }
            }
        }, "sn93t-analysis");
        worker.setDaemon(true);
        worker.setPriority(Thread.NORM_PRIORITY - 1);
        worker.start();
    }

    private void stopWorker() {
        workerRun = false;
        worker = null;
    }

    // ---------------------------------------------------------------- state
    private byte[] stateBytes() {
        if (customText.length() == 0) {
            return null;
        }
        return ("TNR1\n" + customExt + "\n" + customName + "\n" + customText).getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    private void restoreState(byte[] b) {
        if (b == null || b.length < 8) {
            return;
        }
        try {
            String s = new String(b, java.nio.charset.StandardCharsets.UTF_8);
            if (!s.startsWith("TNR1\n")) {
                return;
            }
            int a = s.indexOf('\n', 5), c = a < 0 ? -1 : s.indexOf('\n', a + 1);
            if (a < 0 || c < 0) {
                return;
            }
            customExt = s.substring(5, a);
            customName = s.substring(a + 1, c);
            customText = s.substring(c + 1);
            customVersion++;
            if (ready) {
                applyAll();
            }
        } catch (Throwable t) {
            customText = "";
        }
    }

    private String tipFor(VoltageComponent component) {
        if (component == knobRef) return "A4 = " + fmt("%.1f", refHz()) + " Hz";
        if (component == knobResponse) {
            double r = knobFraction(knobResponse);
            return "Response: " + (r < 0.34 ? "fast" : (r < 0.67 ? "medium" : "fine")) + " (" + (3 + 2 * (int) Math.round(r * 3)) + "-reading median)";
        }
        if (component == knobTemp) return core.getTuning().name;
        if (component == knobKey) return "Key " + TnrCore.NOTE_NAMES[clampInt((int) Math.round(knobKey.GetValue()) - 1, 0, 11)];
        if (component == knobTone) return "Tone note: " + (toneLabel.length() > 0 ? toneLabel + "  " + fmt("%.2f", toneHzLast) + " Hz" : "-");
        if (component == knob66) {
            int o = clampInt((int) Math.round(knob66.GetValue()), 0, 7);
            if (core.getTuning().kind == 3) {
                return "Tone page " + o + ": notes #" + (o * 16) + " to #" + (o * 16 + 15);          // numbered .tun: 8 pages of 16 notes
            }
            return "Tone octave " + o;
        }
        if (component == knobLevel) return fmt("%.0f", -40.0 + 40.0 * knobFraction(knobLevel)) + " dB";
        if (component == knob68) return "CAL span " + (clampInt((int) Math.round(knob68.GetValue()), 0, 7) + 2) + " octaves";
        return null;
    }
    //[/user-code-and-variables]
}
