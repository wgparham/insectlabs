package com.insectlabs.trm;


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


public class trm extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public trm( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "TRM", ModuleType.ModuleType_Processor, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "03b0b747329b4dd780dde88524730f88" );
    }

void InitializeControls()
{

        labelDiodeRed = new VoltageLabel( "labelDiodeRed", "Red Diode Label", this, "—    DIODE    —" );
        AddComponent( labelDiodeRed );
        labelDiodeRed.SetWantsMouseNotifications( false );
        labelDiodeRed.SetPosition( 0, 196 );
        labelDiodeRed.SetSize( 115, 15 );
        labelDiodeRed.SetEditable( false, false );
        labelDiodeRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDiodeRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDiodeRed.SetColor( new Color( 166, 36, 36, 255 ) );
        labelDiodeRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDiodeRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDiodeRed.SetBorderSize( 1 );
        labelDiodeRed.SetMultiLineEdit( false );
        labelDiodeRed.SetIsNumberEditor( false );
        labelDiodeRed.SetNumberEditorRange( 0, 100 );
        labelDiodeRed.SetNumberEditorInterval( 1 );
        labelDiodeRed.SetNumberEditorUsesMouseWheel( false );
        labelDiodeRed.SetHasCustomTextHoverColor( false );
        labelDiodeRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDiodeRed.SetFont( "Arial", 11, true, false );

        labelDiodeBlack = new VoltageLabel( "labelDiodeBlack", "Black Diode Label", this, "DIODE" );
        AddComponent( labelDiodeBlack );
        labelDiodeBlack.SetWantsMouseNotifications( false );
        labelDiodeBlack.SetPosition( 0, 196 );
        labelDiodeBlack.SetSize( 115, 15 );
        labelDiodeBlack.SetEditable( false, false );
        labelDiodeBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelDiodeBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelDiodeBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelDiodeBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelDiodeBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelDiodeBlack.SetBorderSize( 1 );
        labelDiodeBlack.SetMultiLineEdit( false );
        labelDiodeBlack.SetIsNumberEditor( false );
        labelDiodeBlack.SetNumberEditorRange( 0, 100 );
        labelDiodeBlack.SetNumberEditorInterval( 1 );
        labelDiodeBlack.SetNumberEditorUsesMouseWheel( false );
        labelDiodeBlack.SetHasCustomTextHoverColor( false );
        labelDiodeBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelDiodeBlack.SetFont( "Arial", 11, true, false );

        labelModulatorRed = new VoltageLabel( "labelModulatorRed", "Red Modulator Label", this, "—  MODULATOR  —" );
        AddComponent( labelModulatorRed );
        labelModulatorRed.SetWantsMouseNotifications( false );
        labelModulatorRed.SetPosition( 0, 115 );
        labelModulatorRed.SetSize( 115, 15 );
        labelModulatorRed.SetEditable( false, false );
        labelModulatorRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelModulatorRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelModulatorRed.SetColor( new Color( 166, 36, 36, 255 ) );
        labelModulatorRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelModulatorRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelModulatorRed.SetBorderSize( 1 );
        labelModulatorRed.SetMultiLineEdit( false );
        labelModulatorRed.SetIsNumberEditor( false );
        labelModulatorRed.SetNumberEditorRange( 0, 100 );
        labelModulatorRed.SetNumberEditorInterval( 1 );
        labelModulatorRed.SetNumberEditorUsesMouseWheel( false );
        labelModulatorRed.SetHasCustomTextHoverColor( false );
        labelModulatorRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelModulatorRed.SetFont( "Arial", 11, true, false );

        labelModulatorBlack = new VoltageLabel( "labelModulatorBlack", "Black Modulator Label", this, " MODULATOR " );
        AddComponent( labelModulatorBlack );
        labelModulatorBlack.SetWantsMouseNotifications( false );
        labelModulatorBlack.SetPosition( 0, 115 );
        labelModulatorBlack.SetSize( 115, 15 );
        labelModulatorBlack.SetEditable( false, false );
        labelModulatorBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelModulatorBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelModulatorBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelModulatorBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelModulatorBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelModulatorBlack.SetBorderSize( 1 );
        labelModulatorBlack.SetMultiLineEdit( false );
        labelModulatorBlack.SetIsNumberEditor( false );
        labelModulatorBlack.SetNumberEditorRange( 0, 100 );
        labelModulatorBlack.SetNumberEditorInterval( 1 );
        labelModulatorBlack.SetNumberEditorUsesMouseWheel( false );
        labelModulatorBlack.SetHasCustomTextHoverColor( false );
        labelModulatorBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelModulatorBlack.SetFont( "Arial", 11, true, false );

        labelCarrierRed = new VoltageLabel( "labelCarrierRed", "Red Carrier Label", this, "—   CARRIER   —" );
        AddComponent( labelCarrierRed );
        labelCarrierRed.SetWantsMouseNotifications( false );
        labelCarrierRed.SetPosition( 0, 30 );
        labelCarrierRed.SetSize( 115, 15 );
        labelCarrierRed.SetEditable( false, false );
        labelCarrierRed.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCarrierRed.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCarrierRed.SetColor( new Color( 166, 36, 36, 255 ) );
        labelCarrierRed.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCarrierRed.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCarrierRed.SetBorderSize( 1 );
        labelCarrierRed.SetMultiLineEdit( false );
        labelCarrierRed.SetIsNumberEditor( false );
        labelCarrierRed.SetNumberEditorRange( 0, 100 );
        labelCarrierRed.SetNumberEditorInterval( 1 );
        labelCarrierRed.SetNumberEditorUsesMouseWheel( false );
        labelCarrierRed.SetHasCustomTextHoverColor( false );
        labelCarrierRed.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCarrierRed.SetFont( "Arial", 11, true, false );

        labelCarrierBlack = new VoltageLabel( "labelCarrierBlack", "Carrier Label Black", this, "   CARRIER   " );
        AddComponent( labelCarrierBlack );
        labelCarrierBlack.SetWantsMouseNotifications( false );
        labelCarrierBlack.SetPosition( 0, 30 );
        labelCarrierBlack.SetSize( 115, 15 );
        labelCarrierBlack.SetEditable( false, false );
        labelCarrierBlack.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelCarrierBlack.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelCarrierBlack.SetColor( new Color( 26, 26, 26, 255 ) );
        labelCarrierBlack.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelCarrierBlack.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelCarrierBlack.SetBorderSize( 1 );
        labelCarrierBlack.SetMultiLineEdit( false );
        labelCarrierBlack.SetIsNumberEditor( false );
        labelCarrierBlack.SetNumberEditorRange( 0, 100 );
        labelCarrierBlack.SetNumberEditorInterval( 1 );
        labelCarrierBlack.SetNumberEditorUsesMouseWheel( false );
        labelCarrierBlack.SetHasCustomTextHoverColor( false );
        labelCarrierBlack.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelCarrierBlack.SetFont( "Arial", 11, true, false );

        labelFooter = new VoltageLabel( "labelFooter", "", this, "triple ring modulator" );
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

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "trm" );
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

        input1A = new VoltageAudioJack( "input1A", "Mod 1 A", this, JackType.JackType_AudioInput );
        AddComponent( input1A );
        input1A.SetWantsMouseNotifications( false );
        input1A.SetPosition( 10, 75 );
        input1A.SetSize( 25, 25 );
        input1A.SetSkin( "Jack Round 25px" );

        input1B = new VoltageAudioJack( "input1B", "Mod 1 B", this, JackType.JackType_AudioInput );
        AddComponent( input1B );
        input1B.SetWantsMouseNotifications( false );
        input1B.SetPosition( 10, 160 );
        input1B.SetSize( 25, 25 );
        input1B.SetSkin( "Jack Round 25px" );

        out1 = new VoltageAudioJack( "out1", "Out 1", this, JackType.JackType_AudioOutput );
        AddComponent( out1 );
        out1.SetWantsMouseNotifications( false );
        out1.SetPosition( 10, 290 );
        out1.SetSize( 25, 25 );
        out1.SetSkin( "Mini Jack 25px" );

        input2A = new VoltageAudioJack( "input2A", "Mod 2 A", this, JackType.JackType_AudioInput );
        AddComponent( input2A );
        input2A.SetWantsMouseNotifications( false );
        input2A.SetPosition( 45, 75 );
        input2A.SetSize( 25, 25 );
        input2A.SetSkin( "Jack Round 25px" );

        input2B = new VoltageAudioJack( "input2B", "Mod 2 B", this, JackType.JackType_AudioInput );
        AddComponent( input2B );
        input2B.SetWantsMouseNotifications( false );
        input2B.SetPosition( 45, 160 );
        input2B.SetSize( 25, 25 );
        input2B.SetSkin( "Jack Round 25px" );

        out2 = new VoltageAudioJack( "out2", "Out 2", this, JackType.JackType_AudioOutput );
        AddComponent( out2 );
        out2.SetWantsMouseNotifications( false );
        out2.SetPosition( 45, 290 );
        out2.SetSize( 25, 25 );
        out2.SetSkin( "Mini Jack 25px" );

        input3A = new VoltageAudioJack( "input3A", "Mod 3 A", this, JackType.JackType_AudioInput );
        AddComponent( input3A );
        input3A.SetWantsMouseNotifications( false );
        input3A.SetPosition( 80, 75 );
        input3A.SetSize( 25, 25 );
        input3A.SetSkin( "Jack Round 25px" );

        input3B = new VoltageAudioJack( "input3B", "Mod 3 B", this, JackType.JackType_AudioInput );
        AddComponent( input3B );
        input3B.SetWantsMouseNotifications( false );
        input3B.SetPosition( 80, 160 );
        input3B.SetSize( 25, 25 );
        input3B.SetSkin( "Jack Round 25px" );

        out3 = new VoltageAudioJack( "out3", "Out 3", this, JackType.JackType_AudioOutput );
        AddComponent( out3 );
        out3.SetWantsMouseNotifications( false );
        out3.SetPosition( 80, 290 );
        out3.SetSize( 25, 25 );
        out3.SetSkin( "Mini Jack 25px" );

        label1A = new VoltageLabel( "label1A", "1A Label", this, "1A" );
        AddComponent( label1A );
        label1A.SetWantsMouseNotifications( false );
        label1A.SetPosition( 10, 94 );
        label1A.SetSize( 25, 25 );
        label1A.SetEditable( false, false );
        label1A.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label1A.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label1A.SetColor( new Color( 42, 42, 42, 255 ) );
        label1A.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label1A.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label1A.SetBorderSize( 1 );
        label1A.SetMultiLineEdit( false );
        label1A.SetIsNumberEditor( false );
        label1A.SetNumberEditorRange( 0, 100 );
        label1A.SetNumberEditorInterval( 1 );
        label1A.SetNumberEditorUsesMouseWheel( false );
        label1A.SetHasCustomTextHoverColor( false );
        label1A.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label1A.SetFont( "Arial Black", 11, false, false );

        label2A = new VoltageLabel( "label2A", "2A Label", this, "2A" );
        AddComponent( label2A );
        label2A.SetWantsMouseNotifications( false );
        label2A.SetPosition( 45, 94 );
        label2A.SetSize( 25, 25 );
        label2A.SetEditable( false, false );
        label2A.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label2A.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label2A.SetColor( new Color( 42, 42, 42, 255 ) );
        label2A.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label2A.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label2A.SetBorderSize( 1 );
        label2A.SetMultiLineEdit( false );
        label2A.SetIsNumberEditor( false );
        label2A.SetNumberEditorRange( 0, 100 );
        label2A.SetNumberEditorInterval( 1 );
        label2A.SetNumberEditorUsesMouseWheel( false );
        label2A.SetHasCustomTextHoverColor( false );
        label2A.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label2A.SetFont( "Arial Black", 11, false, false );

        label1B = new VoltageLabel( "label1B", "1B Label", this, "1B" );
        AddComponent( label1B );
        label1B.SetWantsMouseNotifications( false );
        label1B.SetPosition( 10, 179 );
        label1B.SetSize( 25, 25 );
        label1B.SetEditable( false, false );
        label1B.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label1B.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label1B.SetColor( new Color( 26, 26, 26, 255 ) );
        label1B.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label1B.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label1B.SetBorderSize( 1 );
        label1B.SetMultiLineEdit( false );
        label1B.SetIsNumberEditor( false );
        label1B.SetNumberEditorRange( 0, 100 );
        label1B.SetNumberEditorInterval( 1 );
        label1B.SetNumberEditorUsesMouseWheel( false );
        label1B.SetHasCustomTextHoverColor( false );
        label1B.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label1B.SetFont( "Arial Black", 11, false, false );

        label3A = new VoltageLabel( "label3A", "3A Label", this, "3A" );
        AddComponent( label3A );
        label3A.SetWantsMouseNotifications( false );
        label3A.SetPosition( 80, 94 );
        label3A.SetSize( 25, 25 );
        label3A.SetEditable( false, false );
        label3A.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label3A.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label3A.SetColor( new Color( 42, 42, 42, 255 ) );
        label3A.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label3A.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label3A.SetBorderSize( 1 );
        label3A.SetMultiLineEdit( false );
        label3A.SetIsNumberEditor( false );
        label3A.SetNumberEditorRange( 0, 100 );
        label3A.SetNumberEditorInterval( 1 );
        label3A.SetNumberEditorUsesMouseWheel( false );
        label3A.SetHasCustomTextHoverColor( false );
        label3A.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label3A.SetFont( "Arial Black", 11, false, false );

        label2B = new VoltageLabel( "label2B", "2B Label", this, "2B" );
        AddComponent( label2B );
        label2B.SetWantsMouseNotifications( false );
        label2B.SetPosition( 45, 179 );
        label2B.SetSize( 25, 25 );
        label2B.SetEditable( false, false );
        label2B.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label2B.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label2B.SetColor( new Color( 26, 26, 26, 255 ) );
        label2B.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label2B.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label2B.SetBorderSize( 1 );
        label2B.SetMultiLineEdit( false );
        label2B.SetIsNumberEditor( false );
        label2B.SetNumberEditorRange( 0, 100 );
        label2B.SetNumberEditorInterval( 1 );
        label2B.SetNumberEditorUsesMouseWheel( false );
        label2B.SetHasCustomTextHoverColor( false );
        label2B.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label2B.SetFont( "Arial Black", 11, false, false );

        label3B = new VoltageLabel( "label3B", "3B Label", this, "3B" );
        AddComponent( label3B );
        label3B.SetWantsMouseNotifications( false );
        label3B.SetPosition( 80, 179 );
        label3B.SetSize( 25, 25 );
        label3B.SetEditable( false, false );
        label3B.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        label3B.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label3B.SetColor( new Color( 26, 26, 26, 255 ) );
        label3B.SetBkColor( new Color( 65, 65, 65, 0 ) );
        label3B.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        label3B.SetBorderSize( 1 );
        label3B.SetMultiLineEdit( false );
        label3B.SetIsNumberEditor( false );
        label3B.SetNumberEditorRange( 0, 100 );
        label3B.SetNumberEditorInterval( 1 );
        label3B.SetNumberEditorUsesMouseWheel( false );
        label3B.SetHasCustomTextHoverColor( false );
        label3B.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label3B.SetFont( "Arial Black", 11, false, false );

        labelOut1 = new VoltageLabel( "labelOut1", "Out Label 1", this, "OUT 1" );
        AddComponent( labelOut1 );
        labelOut1.SetWantsMouseNotifications( false );
        labelOut1.SetPosition( 5, 310 );
        labelOut1.SetSize( 35, 25 );
        labelOut1.SetEditable( false, false );
        labelOut1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOut1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOut1.SetColor( new Color( 26, 26, 26, 255 ) );
        labelOut1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOut1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOut1.SetBorderSize( 1 );
        labelOut1.SetMultiLineEdit( false );
        labelOut1.SetIsNumberEditor( false );
        labelOut1.SetNumberEditorRange( 0, 100 );
        labelOut1.SetNumberEditorInterval( 1 );
        labelOut1.SetNumberEditorUsesMouseWheel( false );
        labelOut1.SetHasCustomTextHoverColor( false );
        labelOut1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOut1.SetFont( "Arial Black", 11, false, false );

        labelOut2 = new VoltageLabel( "labelOut2", "Out Label 2", this, "OUT 2" );
        AddComponent( labelOut2 );
        labelOut2.SetWantsMouseNotifications( false );
        labelOut2.SetPosition( 39, 310 );
        labelOut2.SetSize( 35, 25 );
        labelOut2.SetEditable( false, false );
        labelOut2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOut2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOut2.SetColor( new Color( 26, 26, 26, 255 ) );
        labelOut2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOut2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOut2.SetBorderSize( 1 );
        labelOut2.SetMultiLineEdit( false );
        labelOut2.SetIsNumberEditor( false );
        labelOut2.SetNumberEditorRange( 0, 100 );
        labelOut2.SetNumberEditorInterval( 1 );
        labelOut2.SetNumberEditorUsesMouseWheel( false );
        labelOut2.SetHasCustomTextHoverColor( false );
        labelOut2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOut2.SetFont( "Arial Black", 11, false, false );

        labelOut3 = new VoltageLabel( "labelOut3", "Out Label 3", this, "OUT 3" );
        AddComponent( labelOut3 );
        labelOut3.SetWantsMouseNotifications( false );
        labelOut3.SetPosition( 76, 310 );
        labelOut3.SetSize( 35, 25 );
        labelOut3.SetEditable( false, false );
        labelOut3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelOut3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelOut3.SetColor( new Color( 26, 26, 26, 255 ) );
        labelOut3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelOut3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelOut3.SetBorderSize( 1 );
        labelOut3.SetMultiLineEdit( false );
        labelOut3.SetIsNumberEditor( false );
        labelOut3.SetNumberEditorRange( 0, 100 );
        labelOut3.SetNumberEditorInterval( 1 );
        labelOut3.SetNumberEditorUsesMouseWheel( false );
        labelOut3.SetHasCustomTextHoverColor( false );
        labelOut3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelOut3.SetFont( "Arial Black", 11, false, false );

        switchDiode1 = new VoltageSwitch( "switchDiode1", "Diode Switch 1", this, 2 );
        AddComponent( switchDiode1 );
        switchDiode1.SetWantsMouseNotifications( false );
        switchDiode1.SetPosition( 15, 210 );
        switchDiode1.SetSize( 16, 60 );
        switchDiode1.SetSkin( "4-State Slide Vertical" );

        switchDiode2 = new VoltageSwitch( "switchDiode2", "Diode Switch 2", this, 1 );
        AddComponent( switchDiode2 );
        switchDiode2.SetWantsMouseNotifications( false );
        switchDiode2.SetPosition( 50, 210 );
        switchDiode2.SetSize( 16, 60 );
        switchDiode2.SetSkin( "4-State Slide Vertical" );

        switchDiode3 = new VoltageSwitch( "switchDiode3", "Diode Switch 3", this, 3 );
        AddComponent( switchDiode3 );
        switchDiode3.SetWantsMouseNotifications( false );
        switchDiode3.SetPosition( 86, 210 );
        switchDiode3.SetSize( 16, 60 );
        switchDiode3.SetSkin( "4-State Slide Vertical" );

        labelTrafo1 = new VoltageLabel( "labelTrafo1", "Trafo Label 1", this, "T" );
        AddComponent( labelTrafo1 );
        labelTrafo1.SetWantsMouseNotifications( false );
        labelTrafo1.SetPosition( 28, 207 );
        labelTrafo1.SetSize( 25, 25 );
        labelTrafo1.SetEditable( false, false );
        labelTrafo1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTrafo1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTrafo1.SetColor( new Color( 166, 36, 36, 255 ) );
        labelTrafo1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTrafo1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTrafo1.SetBorderSize( 1 );
        labelTrafo1.SetMultiLineEdit( false );
        labelTrafo1.SetIsNumberEditor( false );
        labelTrafo1.SetNumberEditorRange( 0, 100 );
        labelTrafo1.SetNumberEditorInterval( 1 );
        labelTrafo1.SetNumberEditorUsesMouseWheel( false );
        labelTrafo1.SetHasCustomTextHoverColor( false );
        labelTrafo1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTrafo1.SetFont( "Arial Black", 11, false, false );

        labelTrafo2 = new VoltageLabel( "labelTrafo2", "Trafo Label 2", this, "T" );
        AddComponent( labelTrafo2 );
        labelTrafo2.SetWantsMouseNotifications( false );
        labelTrafo2.SetPosition( 63, 207 );
        labelTrafo2.SetSize( 25, 25 );
        labelTrafo2.SetEditable( false, false );
        labelTrafo2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTrafo2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTrafo2.SetColor( new Color( 166, 36, 36, 255 ) );
        labelTrafo2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTrafo2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTrafo2.SetBorderSize( 1 );
        labelTrafo2.SetMultiLineEdit( false );
        labelTrafo2.SetIsNumberEditor( false );
        labelTrafo2.SetNumberEditorRange( 0, 100 );
        labelTrafo2.SetNumberEditorInterval( 1 );
        labelTrafo2.SetNumberEditorUsesMouseWheel( false );
        labelTrafo2.SetHasCustomTextHoverColor( false );
        labelTrafo2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTrafo2.SetFont( "Arial Black", 11, false, false );

        labelGemanium1 = new VoltageLabel( "labelGemanium1", "Germanium Label 1", this, "G" );
        AddComponent( labelGemanium1 );
        labelGemanium1.SetWantsMouseNotifications( false );
        labelGemanium1.SetPosition( 28, 221 );
        labelGemanium1.SetSize( 25, 25 );
        labelGemanium1.SetEditable( false, false );
        labelGemanium1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGemanium1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGemanium1.SetColor( new Color( 166, 36, 36, 255 ) );
        labelGemanium1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelGemanium1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelGemanium1.SetBorderSize( 1 );
        labelGemanium1.SetMultiLineEdit( false );
        labelGemanium1.SetIsNumberEditor( false );
        labelGemanium1.SetNumberEditorRange( 0, 100 );
        labelGemanium1.SetNumberEditorInterval( 1 );
        labelGemanium1.SetNumberEditorUsesMouseWheel( false );
        labelGemanium1.SetHasCustomTextHoverColor( false );
        labelGemanium1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelGemanium1.SetFont( "Arial Black", 11, false, false );

        labelGemanium2 = new VoltageLabel( "labelGemanium2", "Germanium Label 2", this, "G" );
        AddComponent( labelGemanium2 );
        labelGemanium2.SetWantsMouseNotifications( false );
        labelGemanium2.SetPosition( 63, 221 );
        labelGemanium2.SetSize( 25, 25 );
        labelGemanium2.SetEditable( false, false );
        labelGemanium2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGemanium2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGemanium2.SetColor( new Color( 166, 36, 36, 255 ) );
        labelGemanium2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelGemanium2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelGemanium2.SetBorderSize( 1 );
        labelGemanium2.SetMultiLineEdit( false );
        labelGemanium2.SetIsNumberEditor( false );
        labelGemanium2.SetNumberEditorRange( 0, 100 );
        labelGemanium2.SetNumberEditorInterval( 1 );
        labelGemanium2.SetNumberEditorUsesMouseWheel( false );
        labelGemanium2.SetHasCustomTextHoverColor( false );
        labelGemanium2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelGemanium2.SetFont( "Arial Black", 11, false, false );

        labelSchottky1 = new VoltageLabel( "labelSchottky1", "Schottky Label 1", this, "S" );
        AddComponent( labelSchottky1 );
        labelSchottky1.SetWantsMouseNotifications( false );
        labelSchottky1.SetPosition( 28, 235 );
        labelSchottky1.SetSize( 25, 25 );
        labelSchottky1.SetEditable( false, false );
        labelSchottky1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSchottky1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSchottky1.SetColor( new Color( 166, 36, 36, 255 ) );
        labelSchottky1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSchottky1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSchottky1.SetBorderSize( 1 );
        labelSchottky1.SetMultiLineEdit( false );
        labelSchottky1.SetIsNumberEditor( false );
        labelSchottky1.SetNumberEditorRange( 0, 100 );
        labelSchottky1.SetNumberEditorInterval( 1 );
        labelSchottky1.SetNumberEditorUsesMouseWheel( false );
        labelSchottky1.SetHasCustomTextHoverColor( false );
        labelSchottky1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSchottky1.SetFont( "Arial Black", 11, false, false );

        labelSchottky2 = new VoltageLabel( "labelSchottky2", "Schottky Label 2", this, "S" );
        AddComponent( labelSchottky2 );
        labelSchottky2.SetWantsMouseNotifications( false );
        labelSchottky2.SetPosition( 63, 235 );
        labelSchottky2.SetSize( 25, 25 );
        labelSchottky2.SetEditable( false, false );
        labelSchottky2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSchottky2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSchottky2.SetColor( new Color( 166, 36, 36, 255 ) );
        labelSchottky2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSchottky2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSchottky2.SetBorderSize( 1 );
        labelSchottky2.SetMultiLineEdit( false );
        labelSchottky2.SetIsNumberEditor( false );
        labelSchottky2.SetNumberEditorRange( 0, 100 );
        labelSchottky2.SetNumberEditorInterval( 1 );
        labelSchottky2.SetNumberEditorUsesMouseWheel( false );
        labelSchottky2.SetHasCustomTextHoverColor( false );
        labelSchottky2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSchottky2.SetFont( "Arial Black", 11, false, false );

        labelSilicon1 = new VoltageLabel( "labelSilicon1", "Silicon Label 1", this, "Si" );
        AddComponent( labelSilicon1 );
        labelSilicon1.SetWantsMouseNotifications( false );
        labelSilicon1.SetPosition( 28, 250 );
        labelSilicon1.SetSize( 25, 24 );
        labelSilicon1.SetEditable( false, false );
        labelSilicon1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSilicon1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSilicon1.SetColor( new Color( 166, 36, 36, 255 ) );
        labelSilicon1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSilicon1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSilicon1.SetBorderSize( 1 );
        labelSilicon1.SetMultiLineEdit( false );
        labelSilicon1.SetIsNumberEditor( false );
        labelSilicon1.SetNumberEditorRange( 0, 100 );
        labelSilicon1.SetNumberEditorInterval( 1 );
        labelSilicon1.SetNumberEditorUsesMouseWheel( false );
        labelSilicon1.SetHasCustomTextHoverColor( false );
        labelSilicon1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSilicon1.SetFont( "Arial Black", 11, false, false );

        labelSilicon2 = new VoltageLabel( "labelSilicon2", "Silicon Label 2", this, "Si" );
        AddComponent( labelSilicon2 );
        labelSilicon2.SetWantsMouseNotifications( false );
        labelSilicon2.SetPosition( 63, 250 );
        labelSilicon2.SetSize( 25, 24 );
        labelSilicon2.SetEditable( false, false );
        labelSilicon2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSilicon2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSilicon2.SetColor( new Color( 166, 36, 36, 255 ) );
        labelSilicon2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSilicon2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSilicon2.SetBorderSize( 1 );
        labelSilicon2.SetMultiLineEdit( false );
        labelSilicon2.SetIsNumberEditor( false );
        labelSilicon2.SetNumberEditorRange( 0, 100 );
        labelSilicon2.SetNumberEditorInterval( 1 );
        labelSilicon2.SetNumberEditorUsesMouseWheel( false );
        labelSilicon2.SetHasCustomTextHoverColor( false );
        labelSilicon2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSilicon2.SetFont( "Arial Black", 11, false, false );

        knobCarrier1 = new VoltageKnob( "knobCarrier1", "Carrier Knob 1", this, 0.0, 2.0, 1.0 );
        AddComponent( knobCarrier1 );
        knobCarrier1.SetWantsMouseNotifications( false );
        knobCarrier1.SetPosition( 10, 45 );
        knobCarrier1.SetSize( 25, 25 );
        knobCarrier1.SetSkin( "ARP2500 Sm Silver" );
        knobCarrier1.SetRange( 0.0, 2.0, 1.0, false, 0 );
        knobCarrier1.SetKnobParams( 215, 145 );
        knobCarrier1.DisplayValueInPercent( false );
        knobCarrier1.SetKnobAdjustsRing( true );

        knobCarrier2 = new VoltageKnob( "knobCarrier2", "Carrier Knob 2", this, 0.0, 2.0, 1.0 );
        AddComponent( knobCarrier2 );
        knobCarrier2.SetWantsMouseNotifications( false );
        knobCarrier2.SetPosition( 45, 45 );
        knobCarrier2.SetSize( 25, 25 );
        knobCarrier2.SetSkin( "ARP2500 Sm Silver" );
        knobCarrier2.SetRange( 0.0, 2.0, 1.0, false, 0 );
        knobCarrier2.SetKnobParams( 215, 145 );
        knobCarrier2.DisplayValueInPercent( false );
        knobCarrier2.SetKnobAdjustsRing( true );

        knobCarrier3 = new VoltageKnob( "knobCarrier3", "Carrier Knob 3", this, 0.0, 2.0, 1.0 );
        AddComponent( knobCarrier3 );
        knobCarrier3.SetWantsMouseNotifications( false );
        knobCarrier3.SetPosition( 80, 45 );
        knobCarrier3.SetSize( 25, 25 );
        knobCarrier3.SetSkin( "ARP2500 Sm Silver" );
        knobCarrier3.SetRange( 0.0, 2.0, 1.0, false, 0 );
        knobCarrier3.SetKnobParams( 215, 145 );
        knobCarrier3.DisplayValueInPercent( false );
        knobCarrier3.SetKnobAdjustsRing( true );

        knobModulator1 = new VoltageKnob( "knobModulator1", "Modulator Knob 1", this, 0.0, 2.0, 1.0 );
        AddComponent( knobModulator1 );
        knobModulator1.SetWantsMouseNotifications( false );
        knobModulator1.SetPosition( 10, 130 );
        knobModulator1.SetSize( 25, 25 );
        knobModulator1.SetSkin( "ARP2500 Sm Gold" );
        knobModulator1.SetRange( 0.0, 2.0, 1.0, false, 0 );
        knobModulator1.SetKnobParams( 215, 145 );
        knobModulator1.DisplayValueInPercent( false );
        knobModulator1.SetKnobAdjustsRing( true );

        knobModulator2 = new VoltageKnob( "knobModulator2", "Modulator Knob 2", this, 0.0, 2.0, 1.0 );
        AddComponent( knobModulator2 );
        knobModulator2.SetWantsMouseNotifications( false );
        knobModulator2.SetPosition( 45, 130 );
        knobModulator2.SetSize( 25, 25 );
        knobModulator2.SetSkin( "ARP2500 Sm Gold" );
        knobModulator2.SetRange( 0.0, 2.0, 1.0, false, 0 );
        knobModulator2.SetKnobParams( 215, 145 );
        knobModulator2.DisplayValueInPercent( false );
        knobModulator2.SetKnobAdjustsRing( true );

        knobModulator3 = new VoltageKnob( "knobModulator3", "Modulator Knob 3", this, 0.0, 2.0, 1.0 );
        AddComponent( knobModulator3 );
        knobModulator3.SetWantsMouseNotifications( false );
        knobModulator3.SetPosition( 80, 130 );
        knobModulator3.SetSize( 25, 25 );
        knobModulator3.SetSkin( "ARP2500 Sm Gold" );
        knobModulator3.SetRange( 0.0, 2.0, 1.0, false, 0 );
        knobModulator3.SetKnobParams( 215, 145 );
        knobModulator3.DisplayValueInPercent( false );
        knobModulator3.SetKnobAdjustsRing( true );

        lamp1 = new VoltageLED( "lamp1", "Lamp 1", this );
        AddComponent( lamp1 );
        lamp1.SetWantsMouseNotifications( false );
        lamp1.SetPosition( 16, 274 );
        lamp1.SetSize( 14, 14 );
        lamp1.SetSkin( "2500 Lamp Red" );

        lamp2 = new VoltageLED( "lamp2", "Lamp 2", this );
        AddComponent( lamp2 );
        lamp2.SetWantsMouseNotifications( false );
        lamp2.SetPosition( 51, 274 );
        lamp2.SetSize( 14, 14 );
        lamp2.SetSkin( "2500 Lamp Red" );

        lamp3 = new VoltageLED( "lamp3", "Lamp 3", this );
        AddComponent( lamp3 );
        lamp3.SetWantsMouseNotifications( false );
        lamp3.SetPosition( 87, 274 );
        lamp3.SetSize( 14, 14 );
        lamp3.SetSkin( "2500 Lamp Red" );

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(Seattle) Ltd" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 5, 13 );
        labelLocation.SetSize( 41, 13 );
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
        carrierInputs = new VoltageAudioJack[] { input1A, input2A, input3A };
        modulatorInputs = new VoltageAudioJack[] { input1B, input2B, input3B };
        outputJacks = new VoltageAudioJack[] { out1, out2, out3 };
        carrierKnobs = new VoltageKnob[] { knobCarrier1, knobCarrier2, knobCarrier3 };
        modulatorKnobs = new VoltageKnob[] { knobModulator1, knobModulator2, knobModulator3 };
        diodeSwitches = new VoltageSwitch[] { switchDiode1, switchDiode2, switchDiode3 };
        lamps = new VoltageLED[] { lamp1, lamp2, lamp3 };

        for (int lane = 0; lane < LANE_COUNT; lane++) {
            carrierGain[lane] = safeGain(carrierKnobs[lane].GetValue());
            modulatorGain[lane] = safeGain(modulatorKnobs[lane].GetValue());
            modeFrom[lane] = modeFor(diodeSwitches[lane].GetValue());
            modeTarget[lane] = modeFrom[lane];
            modeBlend[lane] = 1.0;
        }
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
        boolean resuming = resumePending;
        if (resuming) {
            resetOversamplers();
        }

        for (int lane = 0; lane < LANE_COUNT; lane++) {
            double rawCarrier = readSignal(carrierInputs[lane]);
            double rawModulator = readSignal(modulatorInputs[lane]);

            double carrierTarget = safeGain(carrierKnobs[lane].GetValue());
            double modulatorTarget = safeGain(modulatorKnobs[lane].GetValue());
            if (resuming) {
                carrierGain[lane] = carrierTarget;
                modulatorGain[lane] = modulatorTarget;
            } else {
                carrierGain[lane] += GAIN_SMOOTHING * (carrierTarget - carrierGain[lane]);
                modulatorGain[lane] += GAIN_SMOOTHING * (modulatorTarget - modulatorGain[lane]);
            }

            double carrier = rawCarrier * carrierGain[lane];
            double modulator = rawModulator * modulatorGain[lane];
            double inputPeak = Math.max(Math.abs(carrier), Math.abs(modulator));
            if (resuming) {
                inputEnvelope[lane] = inputPeak;
            } else {
                double envelopeCoefficient = inputPeak > inputEnvelope[lane]
                        ? ENVELOPE_ATTACK : ENVELOPE_RELEASE;
                inputEnvelope[lane] += envelopeCoefficient * (inputPeak - inputEnvelope[lane]);
            }
            double normalizedEnvelope = clamp(inputEnvelope[lane] / ENVELOPE_REFERENCE_VOLTS, 0.0, 1.0);

            int selectedMode = modeFor(diodeSwitches[lane].GetValue());
            if (resuming) {
                modeFrom[lane] = selectedMode;
                modeTarget[lane] = selectedMode;
                modeBlend[lane] = 1.0;
            } else if (selectedMode != modeTarget[lane]) {
                modeFrom[lane] = modeBlend[lane] >= 0.5 ? modeTarget[lane] : modeFrom[lane];
                modeTarget[lane] = selectedMode;
                modeBlend[lane] = 0.0;
            }

            double carrierFirst = carrierUpsamplers[lane].process(carrier * 2.0);
            double carrierSecond = carrierUpsamplers[lane].process(0.0);
            double modulatorFirst = modulatorUpsamplers[lane].process(modulator * 2.0);
            double modulatorSecond = modulatorUpsamplers[lane].process(0.0);

            double first = processModeTransition(lane, carrierFirst, modulatorFirst, normalizedEnvelope);
            double second = processModeTransition(lane, carrierSecond, modulatorSecond, normalizedEnvelope);
            double output = outputDownsamplers[lane].process(first);
            outputDownsamplers[lane].process(second);
            output *= RESAMPLER_DC_NORMALIZATION;
            outputJacks[lane].SetValue(Double.isFinite(output) ? output : 0.0);

            double lampLevel = Math.sqrt(clamp(inputEnvelope[lane] / LAMP_REFERENCE_VOLTS, 0.0, 1.0));
            lamps[lane].SetValue(lampLevel);
        }
        resumePending = false;
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
        // This module has no dry path: bypass silences the outputs and freezes DSP history.
        for (int lane = 0; lane < LANE_COUNT; lane++) {
            outputJacks[lane].SetValue(0.0);
            lamps[lane].SetValue(0.0);
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
    private VoltageLabel labelLocation;
    private VoltageLED lamp3;
    private VoltageLED lamp2;
    private VoltageLED lamp1;
    private VoltageKnob knobModulator3;
    private VoltageKnob knobModulator2;
    private VoltageKnob knobModulator1;
    private VoltageKnob knobCarrier3;
    private VoltageKnob knobCarrier2;
    private VoltageKnob knobCarrier1;
    private VoltageLabel labelSilicon2;
    private VoltageLabel labelSilicon1;
    private VoltageLabel labelSchottky2;
    private VoltageLabel labelSchottky1;
    private VoltageLabel labelGemanium2;
    private VoltageLabel labelGemanium1;
    private VoltageLabel labelTrafo2;
    private VoltageLabel labelTrafo1;
    private VoltageSwitch switchDiode3;
    private VoltageSwitch switchDiode2;
    private VoltageSwitch switchDiode1;
    private VoltageLabel labelOut3;
    private VoltageLabel labelOut2;
    private VoltageLabel labelOut1;
    private VoltageLabel label3B;
    private VoltageLabel label2B;
    private VoltageLabel label3A;
    private VoltageLabel label1B;
    private VoltageLabel label2A;
    private VoltageLabel label1A;
    private VoltageAudioJack out3;
    private VoltageAudioJack input3B;
    private VoltageAudioJack input3A;
    private VoltageAudioJack out2;
    private VoltageAudioJack input2B;
    private VoltageAudioJack input2A;
    private VoltageAudioJack out1;
    private VoltageAudioJack input1B;
    private VoltageAudioJack input1A;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel labelCarrierBlack;
    private VoltageLabel labelCarrierRed;
    private VoltageLabel labelModulatorBlack;
    private VoltageLabel labelModulatorRed;
    private VoltageLabel labelDiodeBlack;
    private VoltageLabel labelDiodeRed;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final int LANE_COUNT = 3;
    private static final double SAMPLE_RATE = 48000.0;
    private static final double GAIN_SMOOTHING = 1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE));
    private static final double ENVELOPE_ATTACK = 1.0 - Math.exp(-1.0 / (0.008 * SAMPLE_RATE));
    private static final double ENVELOPE_RELEASE = 1.0 - Math.exp(-1.0 / (0.120 * SAMPLE_RATE));
    private static final double MODE_SMOOTHING_2X = 1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE * 2.0));
    private static final double ENVELOPE_REFERENCE_VOLTS = 5.0;
    private static final double LAMP_REFERENCE_VOLTS = 5.0;
    private static final double RESAMPLER_DC_NORMALIZATION = resamplerDcNormalization();

    private VoltageAudioJack[] carrierInputs;
    private VoltageAudioJack[] modulatorInputs;
    private VoltageAudioJack[] outputJacks;
    private VoltageKnob[] carrierKnobs;
    private VoltageKnob[] modulatorKnobs;
    private VoltageSwitch[] diodeSwitches;
    private VoltageLED[] lamps;

    private final double[] carrierGain = new double[LANE_COUNT];
    private final double[] modulatorGain = new double[LANE_COUNT];
    private final double[] inputEnvelope = new double[LANE_COUNT];
    private final int[] modeFrom = new int[LANE_COUNT];
    private final int[] modeTarget = new int[LANE_COUNT];
    private final double[] modeBlend = new double[LANE_COUNT];
    private final HalfBand19[] carrierUpsamplers = {
            new HalfBand19(), new HalfBand19(), new HalfBand19()
    };
    private final HalfBand19[] modulatorUpsamplers = {
            new HalfBand19(), new HalfBand19(), new HalfBand19()
    };
    private final HalfBand19[] outputDownsamplers = {
            new HalfBand19(), new HalfBand19(), new HalfBand19()
    };
    private boolean resumePending;

    private static double safeGain(double value) {
        return Double.isFinite(value) ? clamp(value, 0.0, 2.0) : 0.0;
    }

    private static double readSignal(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double value = jack.GetValue();
        if (!Double.isFinite(value)) {
            return 0.0;
        }
        return clamp(value, -1000.0, 1000.0);
    }

    private static int modeFor(double value) {
        if (!Double.isFinite(value)) {
            return 0;
        }
        return (int) clamp(Math.round(value), 0.0, 3.0);
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private double processModeTransition(int lane, double carrier, double modulator, double envelope) {
        int from = modeFrom[lane];
        int target = modeTarget[lane];
        if (from == target) {
            return processRingCore(carrier, modulator, target, envelope);
        }

        double blend = modeBlend[lane];
        double oldSignal = processRingCore(carrier, modulator, from, envelope);
        double newSignal = processRingCore(carrier, modulator, target, envelope);
        double output = oldSignal + blend * (newSignal - oldSignal);
        modeBlend[lane] = Math.min(1.0, blend + MODE_SMOOTHING_2X);
        if (modeBlend[lane] >= 1.0) {
            modeFrom[lane] = target;
        }
        return output;
    }

    /** Four intentional sound interpretations; these are not claims about EMS component variants. */
    private static double processRingCore(double carrier, double modulator, int mode, double envelope) {
        double shapedCarrier;
        double shapedModulator;
        double output;
        switch (mode) {
            case 0: // T: transformer-like asymmetry and a small carrier-leakage cost
                shapedCarrier = transformerTransfer(carrier, envelope);
                shapedModulator = transformerTransfer(modulator, envelope);
                output = shapedCarrier * shapedModulator / 5.0 + 0.035 * shapedCarrier;
                break;
            case 1: // G: soft conduction knee; quieter signals give up level
                double germaniumThreshold = 0.10 + 0.18 * envelope;
                shapedCarrier = softConduction(carrier, germaniumThreshold);
                shapedModulator = softConduction(modulator, germaniumThreshold);
                shapedCarrier = softClip(shapedCarrier, 10.0 - 1.8 * envelope);
                shapedModulator = softClip(shapedModulator, 10.0 - 1.8 * envelope);
                output = shapedCarrier * shapedModulator / 5.0;
                break;
            case 2: // S: low threshold and a tighter, brighter driven shoulder
                double schottkyThreshold = 0.035 + 0.045 * envelope;
                shapedCarrier = softConduction(carrier, schottkyThreshold);
                shapedModulator = softConduction(modulator, schottkyThreshold);
                shapedCarrier = softClip(shapedCarrier, 8.5 - 2.0 * envelope);
                shapedModulator = softClip(shapedModulator, 8.5 - 2.0 * envelope);
                output = shapedCarrier * shapedModulator / 5.0;
                break;
            default: // Si: a firmer low-level cutoff that opens as the lane is driven
                double siliconThreshold = 0.55 * (1.0 - 0.45 * envelope);
                shapedCarrier = hardConduction(carrier, siliconThreshold);
                shapedModulator = hardConduction(modulator, siliconThreshold);
                shapedCarrier = softClip(shapedCarrier, 6.5 - 1.0 * envelope);
                shapedModulator = softClip(shapedModulator, 6.5 - 1.0 * envelope);
                output = shapedCarrier * shapedModulator / 5.0;
                break;
        }
        return Double.isFinite(output) ? output : 0.0;
    }

    private static double transformerTransfer(double value, double envelope) {
        double headroom = 8.0 - 3.0 * envelope;
        double bias = 0.12 + 0.14 * envelope;
        double biasOffset = Math.tanh(bias / headroom);
        return headroom * (Math.tanh((value + bias) / headroom) - biasOffset);
    }

    private static double softConduction(double value, double threshold) {
        double magnitude = Math.abs(value);
        double conduction = magnitude - threshold * -Math.expm1(-magnitude / threshold);
        return Math.copySign(Math.max(0.0, conduction), value);
    }

    private static double hardConduction(double value, double threshold) {
        return Math.copySign(Math.max(0.0, Math.abs(value) - threshold), value);
    }

    private static double softClip(double value, double headroom) {
        return headroom * Math.tanh(value / headroom);
    }

    private static double resamplerDcNormalization() {
        double sum = 2.0 * (0.021277660466073 - 0.027992303277934
                + 0.049537687283950 - 0.095789590456413 + 0.308510413777763)
                + 0.488912264413122;
        double alternating = sum - 2.0 * 0.488912264413122;
        return 1.0 / (sum * sum + alternating * alternating);
    }

    private void resetOversamplers() {
        for (int lane = 0; lane < LANE_COUNT; lane++) {
            carrierUpsamplers[lane].reset();
            modulatorUpsamplers[lane].reset();
            outputDownsamplers[lane].reset();
        }
    }

    /** Established 19-tap half-band FIR used for the collection's 2x nonlinear path. */
    private static final class HalfBand19 {
        private static final double C0 = 0.021277660466073;
        private static final double C2 = -0.027992303277934;
        private static final double C4 = 0.049537687283950;
        private static final double C6 = -0.095789590456413;
        private static final double C8 = 0.308510413777763;
        private static final double C9 = 0.488912264413122;
        private final double[] buffer = new double[38];
        private int position;
        private boolean dirty;

        private double process(double input) {
            position--;
            if (position < 0) {
                position = 18;
            }
            buffer[position] = input;
            buffer[position + 19] = input;
            dirty = true;
            return C0 * (buffer[position] + buffer[position + 18])
                    + C2 * (buffer[position + 2] + buffer[position + 16])
                    + C4 * (buffer[position + 4] + buffer[position + 14])
                    + C6 * (buffer[position + 6] + buffer[position + 12])
                    + C8 * (buffer[position + 8] + buffer[position + 10])
                    + C9 * buffer[position + 9];
        }

        private void reset() {
            if (!dirty) {
                return;
            }
            java.util.Arrays.fill(buffer, 0.0);
            position = 0;
            dirty = false;
        }
    }
    //[/user-code-and-variables]
}

 