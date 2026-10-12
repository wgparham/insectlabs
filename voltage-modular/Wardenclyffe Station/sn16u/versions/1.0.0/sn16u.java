package com.insectlabs.sn16u;


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


public class sn16u extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape")
    public sn16u( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "sn-16u", ModuleType.ModuleType_Utility, 6.4 );

        InitializeControls();
        InitializeControls2();
        InitializeControls3();


        canBeBypassed = true;
        SetSkin( "df645aea2b504db886d7829b5148ac3a" );
    }

void InitializeControls()
{

        fixedMinus15Output = new VoltageAudioJack( "fixedMinus15Output", "-15 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedMinus15Output );
        fixedMinus15Output.SetWantsMouseNotifications( false );
        fixedMinus15Output.SetPosition( 95, 75 );
        fixedMinus15Output.SetSize( 25, 25 );
        fixedMinus15Output.SetSkin( "Mini Jack 25px" );

        fixedMinus12Output = new VoltageAudioJack( "fixedMinus12Output", "-12 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedMinus12Output );
        fixedMinus12Output.SetWantsMouseNotifications( false );
        fixedMinus12Output.SetPosition( 95, 110 );
        fixedMinus12Output.SetSize( 25, 25 );
        fixedMinus12Output.SetSkin( "Mini Jack 25px" );

        fixedMinus10Output = new VoltageAudioJack( "fixedMinus10Output", "-10 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedMinus10Output );
        fixedMinus10Output.SetWantsMouseNotifications( false );
        fixedMinus10Output.SetPosition( 95, 145 );
        fixedMinus10Output.SetSize( 25, 25 );
        fixedMinus10Output.SetSkin( "Mini Jack 25px" );

        fixedMinus5Output = new VoltageAudioJack( "fixedMinus5Output", "-5 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedMinus5Output );
        fixedMinus5Output.SetWantsMouseNotifications( false );
        fixedMinus5Output.SetPosition( 95, 180 );
        fixedMinus5Output.SetSize( 25, 25 );
        fixedMinus5Output.SetSkin( "Mini Jack 25px" );

        fixedMinus1Output = new VoltageAudioJack( "fixedMinus1Output", "-1 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedMinus1Output );
        fixedMinus1Output.SetWantsMouseNotifications( false );
        fixedMinus1Output.SetPosition( 95, 215 );
        fixedMinus1Output.SetSize( 25, 25 );
        fixedMinus1Output.SetSkin( "Mini Jack 25px" );

        fixedZeroOutput = new VoltageAudioJack( "fixedZeroOutput", "+0 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedZeroOutput );
        fixedZeroOutput.SetWantsMouseNotifications( false );
        fixedZeroOutput.SetPosition( 112, 250 );
        fixedZeroOutput.SetSize( 25, 25 );
        fixedZeroOutput.SetSkin( "Mini Jack 25px" );

        fixedPlus1Output = new VoltageAudioJack( "fixedPlus1Output", "+1 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedPlus1Output );
        fixedPlus1Output.SetWantsMouseNotifications( false );
        fixedPlus1Output.SetPosition( 129, 215 );
        fixedPlus1Output.SetSize( 25, 25 );
        fixedPlus1Output.SetSkin( "Mini Jack 25px" );

        highPassOutput = new VoltageAudioJack( "highPassOutput", "High Pass Output", this, JackType.JackType_AudioOutput );
        AddComponent( highPassOutput );
        highPassOutput.SetWantsMouseNotifications( false );
        highPassOutput.SetPosition( 418, 224 );
        highPassOutput.SetSize( 25, 25 );
        highPassOutput.SetSkin( "Mini Jack 25px" );

        fixedPlus5Output = new VoltageAudioJack( "fixedPlus5Output", "+5 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedPlus5Output );
        fixedPlus5Output.SetWantsMouseNotifications( false );
        fixedPlus5Output.SetPosition( 129, 180 );
        fixedPlus5Output.SetSize( 25, 25 );
        fixedPlus5Output.SetSkin( "Mini Jack 25px" );

        fixedPlus10Output = new VoltageAudioJack( "fixedPlus10Output", "+10 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedPlus10Output );
        fixedPlus10Output.SetWantsMouseNotifications( false );
        fixedPlus10Output.SetPosition( 129, 145 );
        fixedPlus10Output.SetSize( 25, 25 );
        fixedPlus10Output.SetSkin( "Mini Jack 25px" );

        fixedPlus12Output = new VoltageAudioJack( "fixedPlus12Output", "+12 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedPlus12Output );
        fixedPlus12Output.SetWantsMouseNotifications( false );
        fixedPlus12Output.SetPosition( 129, 110 );
        fixedPlus12Output.SetSize( 25, 25 );
        fixedPlus12Output.SetSkin( "Mini Jack 25px" );

        fixedPlus15Output = new VoltageAudioJack( "fixedPlus15Output", "+15 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedPlus15Output );
        fixedPlus15Output.SetWantsMouseNotifications( false );
        fixedPlus15Output.SetPosition( 129, 75 );
        fixedPlus15Output.SetSize( 25, 25 );
        fixedPlus15Output.SetSkin( "Mini Jack 25px" );

        fixed3v3Output = new VoltageAudioJack( "fixed3v3Output", "+3.3 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixed3v3Output );
        fixed3v3Output.SetWantsMouseNotifications( false );
        fixed3v3Output.SetPosition( 95, 285 );
        fixed3v3Output.SetSize( 25, 25 );
        fixed3v3Output.SetSkin( "Mini Jack 25px" );

        panelLabelSnMinus16U = new VoltageLabel( "panelLabelSnMinus16U", "Panel label: sn-16u", this, "sn-16u" );
        AddComponent( panelLabelSnMinus16U );
        panelLabelSnMinus16U.SetWantsMouseNotifications( false );
        panelLabelSnMinus16U.SetPosition( 18, 3 );
        panelLabelSnMinus16U.SetSize( 327, 13 );
        panelLabelSnMinus16U.SetEditable( false, false );
        panelLabelSnMinus16U.SetJustificationFlags( VoltageLabel.Justification.Left );
        panelLabelSnMinus16U.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelSnMinus16U.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelSnMinus16U.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelSnMinus16U.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelSnMinus16U.SetBorderSize( 1 );
        panelLabelSnMinus16U.SetMultiLineEdit( false );
        panelLabelSnMinus16U.SetIsNumberEditor( false );
        panelLabelSnMinus16U.SetNumberEditorRange( 0, 100 );
        panelLabelSnMinus16U.SetNumberEditorInterval( 1 );
        panelLabelSnMinus16U.SetNumberEditorUsesMouseWheel( false );
        panelLabelSnMinus16U.SetHasCustomTextHoverColor( false );
        panelLabelSnMinus16U.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelSnMinus16U.SetFont( "Courier New", 13, true, false );

        panelLabelMinus15V = new VoltageLabel( "panelLabelMinus15V", "Panel label: -15V", this, "-15V" );
        AddComponent( panelLabelMinus15V );
        panelLabelMinus15V.SetWantsMouseNotifications( false );
        panelLabelMinus15V.SetPosition( 97, 95 );
        panelLabelMinus15V.SetSize( 20, 20 );
        panelLabelMinus15V.SetEditable( false, false );
        panelLabelMinus15V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus15V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus15V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinus15V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus15V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus15V.SetBorderSize( 1 );
        panelLabelMinus15V.SetMultiLineEdit( false );
        panelLabelMinus15V.SetIsNumberEditor( false );
        panelLabelMinus15V.SetNumberEditorRange( 0, 100 );
        panelLabelMinus15V.SetNumberEditorInterval( 1 );
        panelLabelMinus15V.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus15V.SetHasCustomTextHoverColor( false );
        panelLabelMinus15V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus15V.SetFont( "Arial", 9, true, false );

        panelLabelMinus12V = new VoltageLabel( "panelLabelMinus12V", "Panel label: -12V", this, "-12V" );
        AddComponent( panelLabelMinus12V );
        panelLabelMinus12V.SetWantsMouseNotifications( false );
        panelLabelMinus12V.SetPosition( 97, 130 );
        panelLabelMinus12V.SetSize( 20, 20 );
        panelLabelMinus12V.SetEditable( false, false );
        panelLabelMinus12V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus12V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus12V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinus12V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus12V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus12V.SetBorderSize( 1 );
        panelLabelMinus12V.SetMultiLineEdit( false );
        panelLabelMinus12V.SetIsNumberEditor( false );
        panelLabelMinus12V.SetNumberEditorRange( 0, 100 );
        panelLabelMinus12V.SetNumberEditorInterval( 1 );
        panelLabelMinus12V.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus12V.SetHasCustomTextHoverColor( false );
        panelLabelMinus12V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus12V.SetFont( "Arial", 9, true, false );

        panelLabelMinus10V = new VoltageLabel( "panelLabelMinus10V", "Panel label: -10V", this, "-10V" );
        AddComponent( panelLabelMinus10V );
        panelLabelMinus10V.SetWantsMouseNotifications( false );
        panelLabelMinus10V.SetPosition( 97, 165 );
        panelLabelMinus10V.SetSize( 20, 20 );
        panelLabelMinus10V.SetEditable( false, false );
        panelLabelMinus10V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus10V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus10V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinus10V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus10V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus10V.SetBorderSize( 1 );
        panelLabelMinus10V.SetMultiLineEdit( false );
        panelLabelMinus10V.SetIsNumberEditor( false );
        panelLabelMinus10V.SetNumberEditorRange( 0, 100 );
        panelLabelMinus10V.SetNumberEditorInterval( 1 );
        panelLabelMinus10V.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus10V.SetHasCustomTextHoverColor( false );
        panelLabelMinus10V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus10V.SetFont( "Arial", 9, true, false );

        panelLabelMinus5V = new VoltageLabel( "panelLabelMinus5V", "Panel label: -5v", this, "-5v" );
        AddComponent( panelLabelMinus5V );
        panelLabelMinus5V.SetWantsMouseNotifications( false );
        panelLabelMinus5V.SetPosition( 97, 200 );
        panelLabelMinus5V.SetSize( 20, 20 );
        panelLabelMinus5V.SetEditable( false, false );
        panelLabelMinus5V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus5V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus5V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinus5V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus5V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus5V.SetBorderSize( 1 );
        panelLabelMinus5V.SetMultiLineEdit( false );
        panelLabelMinus5V.SetIsNumberEditor( false );
        panelLabelMinus5V.SetNumberEditorRange( 0, 100 );
        panelLabelMinus5V.SetNumberEditorInterval( 1 );
        panelLabelMinus5V.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus5V.SetHasCustomTextHoverColor( false );
        panelLabelMinus5V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus5V.SetFont( "Arial", 9, true, false );

        panelLabelMinus1V = new VoltageLabel( "panelLabelMinus1V", "Panel label: -1V", this, "-1V" );
        AddComponent( panelLabelMinus1V );
        panelLabelMinus1V.SetWantsMouseNotifications( false );
        panelLabelMinus1V.SetPosition( 97, 235 );
        panelLabelMinus1V.SetSize( 20, 20 );
        panelLabelMinus1V.SetEditable( false, false );
        panelLabelMinus1V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus1V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus1V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinus1V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus1V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus1V.SetBorderSize( 1 );
        panelLabelMinus1V.SetMultiLineEdit( false );
        panelLabelMinus1V.SetIsNumberEditor( false );
        panelLabelMinus1V.SetNumberEditorRange( 0, 100 );
        panelLabelMinus1V.SetNumberEditorInterval( 1 );
        panelLabelMinus1V.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus1V.SetHasCustomTextHoverColor( false );
        panelLabelMinus1V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus1V.SetFont( "Arial", 9, true, false );

        panelLabelI2 = new VoltageLabel( "panelLabelI2", "Panel label: I", this, "I" );
        AddComponent( panelLabelI2 );
        panelLabelI2.SetWantsMouseNotifications( false );
        panelLabelI2.SetPosition( 386, 244 );
        panelLabelI2.SetSize( 20, 20 );
        panelLabelI2.SetEditable( false, false );
        panelLabelI2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelI2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelI2.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelI2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelI2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelI2.SetBorderSize( 1 );
        panelLabelI2.SetMultiLineEdit( false );
        panelLabelI2.SetIsNumberEditor( false );
        panelLabelI2.SetNumberEditorRange( 0, 100 );
        panelLabelI2.SetNumberEditorInterval( 1 );
        panelLabelI2.SetNumberEditorUsesMouseWheel( false );
        panelLabelI2.SetHasCustomTextHoverColor( false );
        panelLabelI2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelI2.SetFont( "Arial", 9, true, false );

        panelLabel1V = new VoltageLabel( "panelLabel1V", "Panel label: 1V", this, "1V" );
        AddComponent( panelLabel1V );
        panelLabel1V.SetWantsMouseNotifications( false );
        panelLabel1V.SetPosition( 131, 235 );
        panelLabel1V.SetSize( 20, 20 );
        panelLabel1V.SetEditable( false, false );
        panelLabel1V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel1V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel1V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel1V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel1V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel1V.SetBorderSize( 1 );
        panelLabel1V.SetMultiLineEdit( false );
        panelLabel1V.SetIsNumberEditor( false );
        panelLabel1V.SetNumberEditorRange( 0, 100 );
        panelLabel1V.SetNumberEditorInterval( 1 );
        panelLabel1V.SetNumberEditorUsesMouseWheel( false );
        panelLabel1V.SetHasCustomTextHoverColor( false );
        panelLabel1V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel1V.SetFont( "Arial", 9, true, false );

        panelLabel1KHz = new VoltageLabel( "panelLabel1KHz", "Panel label: 1 kHz", this, "1 kHz" );
        AddComponent( panelLabel1KHz );
        panelLabel1KHz.SetWantsMouseNotifications( false );
        panelLabel1KHz.SetPosition( 246, 233 );
        panelLabel1KHz.SetSize( 20, 20 );
        panelLabel1KHz.SetEditable( false, false );
        panelLabel1KHz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel1KHz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel1KHz.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel1KHz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel1KHz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel1KHz.SetBorderSize( 1 );
        panelLabel1KHz.SetMultiLineEdit( false );
        panelLabel1KHz.SetIsNumberEditor( false );
        panelLabel1KHz.SetNumberEditorRange( 0, 100 );
        panelLabel1KHz.SetNumberEditorInterval( 1 );
        panelLabel1KHz.SetNumberEditorUsesMouseWheel( false );
        panelLabel1KHz.SetHasCustomTextHoverColor( false );
        panelLabel1KHz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel1KHz.SetFont( "Arial", 9, true, false );

        panelLabel1Hz = new VoltageLabel( "panelLabel1Hz", "Panel label: 1 Hz", this, "1 Hz" );
        AddComponent( panelLabel1Hz );
        panelLabel1Hz.SetWantsMouseNotifications( false );
        panelLabel1Hz.SetPosition( 173, 233 );
        panelLabel1Hz.SetSize( 40, 20 );
        panelLabel1Hz.SetEditable( false, false );
        panelLabel1Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel1Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel1Hz.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel1Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel1Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel1Hz.SetBorderSize( 1 );
        panelLabel1Hz.SetMultiLineEdit( false );
        panelLabel1Hz.SetIsNumberEditor( false );
        panelLabel1Hz.SetNumberEditorRange( 0, 100 );
        panelLabel1Hz.SetNumberEditorInterval( 1 );
        panelLabel1Hz.SetNumberEditorUsesMouseWheel( false );
        panelLabel1Hz.SetHasCustomTextHoverColor( false );
        panelLabel1Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel1Hz.SetFont( "Arial", 9, true, false );

        panelLabel100Hz = new VoltageLabel( "panelLabel100Hz", "Panel label: 100 Hz", this, "100 Hz" );
        AddComponent( panelLabel100Hz );
        panelLabel100Hz.SetWantsMouseNotifications( false );
        panelLabel100Hz.SetPosition( 206, 233 );
        panelLabel100Hz.SetSize( 40, 20 );
        panelLabel100Hz.SetEditable( false, false );
        panelLabel100Hz.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel100Hz.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel100Hz.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel100Hz.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel100Hz.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel100Hz.SetBorderSize( 1 );
        panelLabel100Hz.SetMultiLineEdit( false );
        panelLabel100Hz.SetIsNumberEditor( false );
        panelLabel100Hz.SetNumberEditorRange( 0, 100 );
        panelLabel100Hz.SetNumberEditorInterval( 1 );
        panelLabel100Hz.SetNumberEditorUsesMouseWheel( false );
        panelLabel100Hz.SetHasCustomTextHoverColor( false );
        panelLabel100Hz.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel100Hz.SetFont( "Arial", 9, true, false );

        panelLabelSWEEP = new VoltageLabel( "panelLabelSWEEP", "Panel label: SWEEP", this, "SWEEP" );
        AddComponent( panelLabelSWEEP );
        panelLabelSWEEP.SetWantsMouseNotifications( false );
        panelLabelSWEEP.SetPosition( 227, 269 );
        panelLabelSWEEP.SetSize( 40, 20 );
        panelLabelSWEEP.SetEditable( false, false );
        panelLabelSWEEP.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelSWEEP.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelSWEEP.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelSWEEP.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelSWEEP.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelSWEEP.SetBorderSize( 1 );
        panelLabelSWEEP.SetMultiLineEdit( false );
        panelLabelSWEEP.SetIsNumberEditor( false );
        panelLabelSWEEP.SetNumberEditorRange( 0, 100 );
        panelLabelSWEEP.SetNumberEditorInterval( 1 );
        panelLabelSWEEP.SetNumberEditorUsesMouseWheel( false );
        panelLabelSWEEP.SetHasCustomTextHoverColor( false );
        panelLabelSWEEP.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelSWEEP.SetFont( "Arial", 9, true, false );

        panelLabelO2 = new VoltageLabel( "panelLabelO2", "Panel label: O", this, "O" );
        AddComponent( panelLabelO2 );
        panelLabelO2.SetWantsMouseNotifications( false );
        panelLabelO2.SetPosition( 419, 245 );
        panelLabelO2.SetSize( 20, 20 );
        panelLabelO2.SetEditable( false, false );
        panelLabelO2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelO2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelO2.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelO2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelO2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelO2.SetBorderSize( 1 );
        panelLabelO2.SetMultiLineEdit( false );
        panelLabelO2.SetIsNumberEditor( false );
        panelLabelO2.SetNumberEditorRange( 0, 100 );
        panelLabelO2.SetNumberEditorInterval( 1 );
        panelLabelO2.SetNumberEditorUsesMouseWheel( false );
        panelLabelO2.SetHasCustomTextHoverColor( false );
        panelLabelO2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelO2.SetFont( "Arial", 9, true, false );

        panelLabel5V = new VoltageLabel( "panelLabel5V", "Panel label: 5V", this, "5V" );
        AddComponent( panelLabel5V );
        panelLabel5V.SetWantsMouseNotifications( false );
        panelLabel5V.SetPosition( 131, 200 );
        panelLabel5V.SetSize( 20, 20 );
        panelLabel5V.SetEditable( false, false );
        panelLabel5V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel5V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel5V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel5V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel5V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel5V.SetBorderSize( 1 );
        panelLabel5V.SetMultiLineEdit( false );
        panelLabel5V.SetIsNumberEditor( false );
        panelLabel5V.SetNumberEditorRange( 0, 100 );
        panelLabel5V.SetNumberEditorInterval( 1 );
        panelLabel5V.SetNumberEditorUsesMouseWheel( false );
        panelLabel5V.SetHasCustomTextHoverColor( false );
        panelLabel5V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel5V.SetFont( "Arial", 9, true, false );

        panelLabel10V = new VoltageLabel( "panelLabel10V", "Panel label: 10V", this, "10V" );
        AddComponent( panelLabel10V );
        panelLabel10V.SetWantsMouseNotifications( false );
        panelLabel10V.SetPosition( 131, 165 );
        panelLabel10V.SetSize( 20, 20 );
        panelLabel10V.SetEditable( false, false );
        panelLabel10V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel10V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel10V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel10V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel10V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel10V.SetBorderSize( 1 );
        panelLabel10V.SetMultiLineEdit( false );
        panelLabel10V.SetIsNumberEditor( false );
        panelLabel10V.SetNumberEditorRange( 0, 100 );
        panelLabel10V.SetNumberEditorInterval( 1 );
        panelLabel10V.SetNumberEditorUsesMouseWheel( false );
        panelLabel10V.SetHasCustomTextHoverColor( false );
        panelLabel10V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel10V.SetFont( "Arial", 9, true, false );

        panelLabel12V = new VoltageLabel( "panelLabel12V", "Panel label: 12V", this, "12V" );
        AddComponent( panelLabel12V );
        panelLabel12V.SetWantsMouseNotifications( false );
        panelLabel12V.SetPosition( 131, 131 );
        panelLabel12V.SetSize( 20, 20 );
        panelLabel12V.SetEditable( false, false );
        panelLabel12V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel12V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel12V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel12V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel12V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel12V.SetBorderSize( 1 );
        panelLabel12V.SetMultiLineEdit( false );
        panelLabel12V.SetIsNumberEditor( false );
        panelLabel12V.SetNumberEditorRange( 0, 100 );
        panelLabel12V.SetNumberEditorInterval( 1 );
        panelLabel12V.SetNumberEditorUsesMouseWheel( false );
        panelLabel12V.SetHasCustomTextHoverColor( false );
        panelLabel12V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel12V.SetFont( "Arial", 9, true, false );

        panelLabel15V = new VoltageLabel( "panelLabel15V", "Panel label: 15V", this, "15V" );
        AddComponent( panelLabel15V );
        panelLabel15V.SetWantsMouseNotifications( false );
        panelLabel15V.SetPosition( 131, 95 );
        panelLabel15V.SetSize( 20, 20 );
        panelLabel15V.SetEditable( false, false );
        panelLabel15V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel15V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel15V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel15V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel15V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel15V.SetBorderSize( 1 );
        panelLabel15V.SetMultiLineEdit( false );
        panelLabel15V.SetIsNumberEditor( false );
        panelLabel15V.SetNumberEditorRange( 0, 100 );
        panelLabel15V.SetNumberEditorInterval( 1 );
        panelLabel15V.SetNumberEditorUsesMouseWheel( false );
        panelLabel15V.SetHasCustomTextHoverColor( false );
        panelLabel15V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel15V.SetFont( "Arial", 9, true, false );

        panelLabel0V = new VoltageLabel( "panelLabel0V", "Panel label: 0V", this, "0V" );
        AddComponent( panelLabel0V );
        panelLabel0V.SetWantsMouseNotifications( false );
        panelLabel0V.SetPosition( 113, 270 );
        panelLabel0V.SetSize( 20, 20 );
        panelLabel0V.SetEditable( false, false );
        panelLabel0V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel0V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel0V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel0V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel0V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel0V.SetBorderSize( 1 );
        panelLabel0V.SetMultiLineEdit( false );
        panelLabel0V.SetIsNumberEditor( false );
        panelLabel0V.SetNumberEditorRange( 0, 100 );
        panelLabel0V.SetNumberEditorInterval( 1 );
        panelLabel0V.SetNumberEditorUsesMouseWheel( false );
        panelLabel0V.SetHasCustomTextHoverColor( false );
        panelLabel0V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel0V.SetFont( "Arial", 9, true, false );

        fixed9Output = new VoltageAudioJack( "fixed9Output", "+9 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixed9Output );
        fixed9Output.SetWantsMouseNotifications( false );
        fixed9Output.SetPosition( 129, 285 );
        fixed9Output.SetSize( 25, 25 );
        fixed9Output.SetSkin( "Mini Jack 25px" );

        fixedMinus24Output = new VoltageAudioJack( "fixedMinus24Output", "-24 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedMinus24Output );
        fixedMinus24Output.SetWantsMouseNotifications( false );
        fixedMinus24Output.SetPosition( 95, 40 );
        fixedMinus24Output.SetSize( 25, 25 );
        fixedMinus24Output.SetSkin( "Mini Jack 25px" );

        panelLabel33V = new VoltageLabel( "panelLabel33V", "Panel label: 3.3V", this, "3.3V" );
        AddComponent( panelLabel33V );
        panelLabel33V.SetWantsMouseNotifications( false );
        panelLabel33V.SetPosition( 97, 304 );
        panelLabel33V.SetSize( 20, 20 );
        panelLabel33V.SetEditable( false, false );
        panelLabel33V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel33V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel33V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel33V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel33V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel33V.SetBorderSize( 1 );
        panelLabel33V.SetMultiLineEdit( false );
        panelLabel33V.SetIsNumberEditor( false );
        panelLabel33V.SetNumberEditorRange( 0, 100 );
        panelLabel33V.SetNumberEditorInterval( 1 );
        panelLabel33V.SetNumberEditorUsesMouseWheel( false );
        panelLabel33V.SetHasCustomTextHoverColor( false );
        panelLabel33V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel33V.SetFont( "Arial", 9, true, false );

        panelLabel9V = new VoltageLabel( "panelLabel9V", "Panel label: 9V", this, "9V" );
        AddComponent( panelLabel9V );
        panelLabel9V.SetWantsMouseNotifications( false );
        panelLabel9V.SetPosition( 131, 305 );
        panelLabel9V.SetSize( 20, 20 );
        panelLabel9V.SetEditable( false, false );
        panelLabel9V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel9V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel9V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel9V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel9V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel9V.SetBorderSize( 1 );
        panelLabel9V.SetMultiLineEdit( false );
        panelLabel9V.SetIsNumberEditor( false );
        panelLabel9V.SetNumberEditorRange( 0, 100 );
        panelLabel9V.SetNumberEditorInterval( 1 );
        panelLabel9V.SetNumberEditorUsesMouseWheel( false );
        panelLabel9V.SetHasCustomTextHoverColor( false );
        panelLabel9V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel9V.SetFont( "Arial", 9, true, false );

        panelLabelMinus24V = new VoltageLabel( "panelLabelMinus24V", "Panel label: -24V", this, "-24V" );
        AddComponent( panelLabelMinus24V );
        panelLabelMinus24V.SetWantsMouseNotifications( false );
        panelLabelMinus24V.SetPosition( 97, 60 );
        panelLabelMinus24V.SetSize( 20, 20 );
        panelLabelMinus24V.SetEditable( false, false );
        panelLabelMinus24V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus24V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus24V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinus24V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus24V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus24V.SetBorderSize( 1 );
        panelLabelMinus24V.SetMultiLineEdit( false );
        panelLabelMinus24V.SetIsNumberEditor( false );
        panelLabelMinus24V.SetNumberEditorRange( 0, 100 );
        panelLabelMinus24V.SetNumberEditorInterval( 1 );
        panelLabelMinus24V.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus24V.SetHasCustomTextHoverColor( false );
        panelLabelMinus24V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus24V.SetFont( "Arial", 9, true, false );

        pinkOutput = new VoltageAudioJack( "pinkOutput", "Pink Noise", this, JackType.JackType_AudioOutput );
        AddComponent( pinkOutput );
        pinkOutput.SetWantsMouseNotifications( false );
        pinkOutput.SetPosition( 400, 38 );
        pinkOutput.SetSize( 25, 25 );
        pinkOutput.SetSkin( "Mini Jack 25px" );

        panelLabelPINK = new VoltageLabel( "panelLabelPINK", "Panel label: PINK", this, "PINK" );
        AddComponent( panelLabelPINK );
        panelLabelPINK.SetWantsMouseNotifications( false );
        panelLabelPINK.SetPosition( 402, 58 );
        panelLabelPINK.SetSize( 20, 20 );
        panelLabelPINK.SetEditable( false, false );
        panelLabelPINK.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelPINK.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelPINK.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelPINK.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelPINK.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelPINK.SetBorderSize( 1 );
        panelLabelPINK.SetMultiLineEdit( false );
        panelLabelPINK.SetIsNumberEditor( false );
        panelLabelPINK.SetNumberEditorRange( 0, 100 );
        panelLabelPINK.SetNumberEditorInterval( 1 );
        panelLabelPINK.SetNumberEditorUsesMouseWheel( false );
        panelLabelPINK.SetHasCustomTextHoverColor( false );
        panelLabelPINK.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelPINK.SetFont( "Arial", 9, true, false );

        blueOutput = new VoltageAudioJack( "blueOutput", "Blue Noise", this, JackType.JackType_AudioOutput );
        AddComponent( blueOutput );
        blueOutput.SetWantsMouseNotifications( false );
        blueOutput.SetPosition( 400, 73 );
        blueOutput.SetSize( 25, 25 );
        blueOutput.SetSkin( "Mini Jack 25px" );

        panelLabelBLUE = new VoltageLabel( "panelLabelBLUE", "Panel label: BLUE", this, "BLUE" );
        AddComponent( panelLabelBLUE );
        panelLabelBLUE.SetWantsMouseNotifications( false );
        panelLabelBLUE.SetPosition( 402, 93 );
        panelLabelBLUE.SetSize( 20, 20 );
        panelLabelBLUE.SetEditable( false, false );
        panelLabelBLUE.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelBLUE.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelBLUE.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelBLUE.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelBLUE.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelBLUE.SetBorderSize( 1 );
        panelLabelBLUE.SetMultiLineEdit( false );
        panelLabelBLUE.SetIsNumberEditor( false );
        panelLabelBLUE.SetNumberEditorRange( 0, 100 );
        panelLabelBLUE.SetNumberEditorInterval( 1 );
        panelLabelBLUE.SetNumberEditorUsesMouseWheel( false );
        panelLabelBLUE.SetHasCustomTextHoverColor( false );
        panelLabelBLUE.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelBLUE.SetFont( "Arial", 9, true, false );

        panelLabelInsectLaboratories = new VoltageLabel( "panelLabelInsectLaboratories", "Panel label: insect laboratories", this, "insect laboratories" );
        AddComponent( panelLabelInsectLaboratories );
        panelLabelInsectLaboratories.SetWantsMouseNotifications( false );
        panelLabelInsectLaboratories.SetPosition( 0, 335 );
        panelLabelInsectLaboratories.SetSize( 460, 23 );
        panelLabelInsectLaboratories.SetEditable( false, false );
        panelLabelInsectLaboratories.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelInsectLaboratories.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelInsectLaboratories.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelInsectLaboratories.SetBkColor( new Color( 51, 51, 51, 0 ) );
        panelLabelInsectLaboratories.SetBorderColor( new Color( 85, 85, 0, 255 ) );
        panelLabelInsectLaboratories.SetBorderSize( 5 );
        panelLabelInsectLaboratories.SetMultiLineEdit( false );
        panelLabelInsectLaboratories.SetIsNumberEditor( false );
        panelLabelInsectLaboratories.SetNumberEditorRange( 0, 100 );
        panelLabelInsectLaboratories.SetNumberEditorInterval( 1 );
        panelLabelInsectLaboratories.SetNumberEditorUsesMouseWheel( false );
        panelLabelInsectLaboratories.SetHasCustomTextHoverColor( false );
        panelLabelInsectLaboratories.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelInsectLaboratories.SetFont( "Courier New", 13, true, false );

        fastPulseOutput = new VoltageAudioJack( "fastPulseOutput", "Fast Impulses", this, JackType.JackType_AudioOutput );
        AddComponent( fastPulseOutput );
        fastPulseOutput.SetWantsMouseNotifications( false );
        fastPulseOutput.SetPosition( 400, 108 );
        fastPulseOutput.SetSize( 25, 25 );
        fastPulseOutput.SetSkin( "Mini Jack 25px" );

        panelLabelFASTPULSES = new VoltageLabel( "panelLabelFASTPULSES", "Panel label: FAST PULSES", this, "FAST PULSES" );
        AddComponent( panelLabelFASTPULSES );
        panelLabelFASTPULSES.SetWantsMouseNotifications( false );
        panelLabelFASTPULSES.SetPosition( 387, 127 );
        panelLabelFASTPULSES.SetSize( 50, 20 );
        panelLabelFASTPULSES.SetEditable( false, false );
        panelLabelFASTPULSES.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelFASTPULSES.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelFASTPULSES.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelFASTPULSES.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelFASTPULSES.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelFASTPULSES.SetBorderSize( 1 );
        panelLabelFASTPULSES.SetMultiLineEdit( false );
        panelLabelFASTPULSES.SetIsNumberEditor( false );
        panelLabelFASTPULSES.SetNumberEditorRange( 0, 100 );
        panelLabelFASTPULSES.SetNumberEditorInterval( 1 );
        panelLabelFASTPULSES.SetNumberEditorUsesMouseWheel( false );
        panelLabelFASTPULSES.SetHasCustomTextHoverColor( false );
        panelLabelFASTPULSES.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelFASTPULSES.SetFont( "Arial", 9, true, false );

        wholeVoltsKnob = new VoltageKnob( "wholeVoltsKnob", "Whole Volts", this, 0, 10, 0 );
        AddComponent( wholeVoltsKnob );
        wholeVoltsKnob.SetWantsMouseNotifications( false );
        wholeVoltsKnob.SetPosition( 26, 80 );
        wholeVoltsKnob.SetSize( 42, 42 );
        wholeVoltsKnob.SetSkin( "Cosmo v2 Pointer" );
        wholeVoltsKnob.SetRange( 0, 10, 0, false, 11 );
        wholeVoltsKnob.SetKnobParams( 215, 145 );
        wholeVoltsKnob.DisplayValueInPercent( false );
        wholeVoltsKnob.SetKnobAdjustsRing( true );

        tuningSelector = new VoltageKnob( "tuningSelector", "A Reference", this, 1, 7, 4 );
        AddComponent( tuningSelector );
        tuningSelector.SetWantsMouseNotifications( false );
        tuningSelector.SetPosition( 202, 80 );
        tuningSelector.SetSize( 42, 42 );
        tuningSelector.SetSkin( "Cosmo v2 Pointer" );
        tuningSelector.SetRange( 1, 7, 4, false, 7 );
        tuningSelector.SetKnobParams( 215, 145 );
        tuningSelector.DisplayValueInPercent( false );
        tuningSelector.SetKnobAdjustsRing( true );

        fixedPlus24Output = new VoltageAudioJack( "fixedPlus24Output", "+24 V Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fixedPlus24Output );
        fixedPlus24Output.SetWantsMouseNotifications( false );
        fixedPlus24Output.SetPosition( 129, 40 );
        fixedPlus24Output.SetSize( 25, 25 );
        fixedPlus24Output.SetSkin( "Mini Jack 25px" );

        panelLabel24V = new VoltageLabel( "panelLabel24V", "Panel label: 24V", this, "24V" );
        AddComponent( panelLabel24V );
        panelLabel24V.SetWantsMouseNotifications( false );
        panelLabel24V.SetPosition( 131, 60 );
        panelLabel24V.SetSize( 20, 20 );
        panelLabel24V.SetEditable( false, false );
        panelLabel24V.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel24V.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel24V.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabel24V.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel24V.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel24V.SetBorderSize( 1 );
        panelLabel24V.SetMultiLineEdit( false );
        panelLabel24V.SetIsNumberEditor( false );
        panelLabel24V.SetNumberEditorRange( 0, 100 );
        panelLabel24V.SetNumberEditorInterval( 1 );
        panelLabel24V.SetNumberEditorUsesMouseWheel( false );
        panelLabel24V.SetHasCustomTextHoverColor( false );
        panelLabel24V.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel24V.SetFont( "Arial", 9, true, false );

        slowPulseOutput = new VoltageAudioJack( "slowPulseOutput", "Slow Impulses", this, JackType.JackType_AudioOutput );
        AddComponent( slowPulseOutput );
        slowPulseOutput.SetWantsMouseNotifications( false );
        slowPulseOutput.SetPosition( 400, 143 );
        slowPulseOutput.SetSize( 25, 25 );
        slowPulseOutput.SetSkin( "Mini Jack 25px" );

        panelLabelSLOWPULSES = new VoltageLabel( "panelLabelSLOWPULSES", "Panel label: SLOW PULSES", this, "SLOW PULSES" );
        AddComponent( panelLabelSLOWPULSES );
        panelLabelSLOWPULSES.SetWantsMouseNotifications( false );
        panelLabelSLOWPULSES.SetPosition( 387, 162 );
        panelLabelSLOWPULSES.SetSize( 50, 20 );
        panelLabelSLOWPULSES.SetEditable( false, false );
        panelLabelSLOWPULSES.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelSLOWPULSES.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelSLOWPULSES.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelSLOWPULSES.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelSLOWPULSES.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelSLOWPULSES.SetBorderSize( 1 );
        panelLabelSLOWPULSES.SetMultiLineEdit( false );
        panelLabelSLOWPULSES.SetIsNumberEditor( false );
        panelLabelSLOWPULSES.SetNumberEditorRange( 0, 100 );
        panelLabelSLOWPULSES.SetNumberEditorInterval( 1 );
        panelLabelSLOWPULSES.SetNumberEditorUsesMouseWheel( false );
        panelLabelSLOWPULSES.SetHasCustomTextHoverColor( false );
        panelLabelSLOWPULSES.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelSLOWPULSES.SetFont( "Arial", 9, true, false );

        panelLabel0 = new VoltageLabel( "panelLabel0", "Panel label: 0", this, "0" );
        AddComponent( panelLabel0 );
        panelLabel0.SetWantsMouseNotifications( false );
        panelLabel0.SetPosition( 24, 117 );
        panelLabel0.SetSize( 16, 8 );
        panelLabel0.SetEditable( false, false );
        panelLabel0.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel0.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel0.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel0.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel0.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel0.SetBorderSize( 1 );
        panelLabel0.SetMultiLineEdit( false );
        panelLabel0.SetIsNumberEditor( false );
        panelLabel0.SetNumberEditorRange( 0, 100 );
        panelLabel0.SetNumberEditorInterval( 1 );
        panelLabel0.SetNumberEditorUsesMouseWheel( false );
        panelLabel0.SetHasCustomTextHoverColor( false );
        panelLabel0.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel0.SetFont( "Arial", 6, true, false );

        panelLabel10 = new VoltageLabel( "panelLabel10", "Panel label: 10", this, "10" );
        AddComponent( panelLabel10 );
        panelLabel10.SetWantsMouseNotifications( false );
        panelLabel10.SetPosition( 53, 117 );
        panelLabel10.SetSize( 16, 8 );
        panelLabel10.SetEditable( false, false );
        panelLabel10.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel10.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel10.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel10.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel10.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel10.SetBorderSize( 1 );
        panelLabel10.SetMultiLineEdit( false );
        panelLabel10.SetIsNumberEditor( false );
        panelLabel10.SetNumberEditorRange( 0, 100 );
        panelLabel10.SetNumberEditorInterval( 1 );
        panelLabel10.SetNumberEditorUsesMouseWheel( false );
        panelLabel10.SetHasCustomTextHoverColor( false );
        panelLabel10.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel10.SetFont( "Arial", 6, true, false );

        panelLabel5 = new VoltageLabel( "panelLabel5", "Panel label: 5", this, "5" );
        AddComponent( panelLabel5 );
        panelLabel5.SetWantsMouseNotifications( false );
        panelLabel5.SetPosition( 39, 70 );
        panelLabel5.SetSize( 16, 8 );
        panelLabel5.SetEditable( false, false );
        panelLabel5.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel5.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel5.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel5.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel5.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel5.SetBorderSize( 1 );
        panelLabel5.SetMultiLineEdit( false );
        panelLabel5.SetIsNumberEditor( false );
        panelLabel5.SetNumberEditorRange( 0, 100 );
        panelLabel5.SetNumberEditorInterval( 1 );
        panelLabel5.SetNumberEditorUsesMouseWheel( false );
        panelLabel5.SetHasCustomTextHoverColor( false );
        panelLabel5.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel5.SetFont( "Arial", 6, true, false );

        panelLabel3 = new VoltageLabel( "panelLabel3", "Panel label: 3", this, "3" );
        AddComponent( panelLabel3 );
        panelLabel3.SetWantsMouseNotifications( false );
        panelLabel3.SetPosition( 19, 80 );
        panelLabel3.SetSize( 16, 8 );
        panelLabel3.SetEditable( false, false );
        panelLabel3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel3.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel3.SetBorderSize( 1 );
        panelLabel3.SetMultiLineEdit( false );
        panelLabel3.SetIsNumberEditor( false );
        panelLabel3.SetNumberEditorRange( 0, 100 );
        panelLabel3.SetNumberEditorInterval( 1 );
        panelLabel3.SetNumberEditorUsesMouseWheel( false );
        panelLabel3.SetHasCustomTextHoverColor( false );
        panelLabel3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel3.SetFont( "Arial", 6, true, false );

        panelLabel2 = new VoltageLabel( "panelLabel2", "Panel label: 2", this, "2" );
        AddComponent( panelLabel2 );
        panelLabel2.SetWantsMouseNotifications( false );
        panelLabel2.SetPosition( 14, 92 );
        panelLabel2.SetSize( 16, 8 );
        panelLabel2.SetEditable( false, false );
        panelLabel2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel2.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel2.SetBorderSize( 1 );
        panelLabel2.SetMultiLineEdit( false );
        panelLabel2.SetIsNumberEditor( false );
        panelLabel2.SetNumberEditorRange( 0, 100 );
        panelLabel2.SetNumberEditorInterval( 1 );
        panelLabel2.SetNumberEditorUsesMouseWheel( false );
        panelLabel2.SetHasCustomTextHoverColor( false );
        panelLabel2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel2.SetFont( "Arial", 6, true, false );

        panelLabel7 = new VoltageLabel( "panelLabel7", "Panel label: 7", this, "7" );
        AddComponent( panelLabel7 );
        panelLabel7.SetWantsMouseNotifications( false );
        panelLabel7.SetPosition( 59, 80 );
        panelLabel7.SetSize( 15, 8 );
        panelLabel7.SetEditable( false, false );
        panelLabel7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel7.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel7.SetBorderSize( 1 );
        panelLabel7.SetMultiLineEdit( false );
        panelLabel7.SetIsNumberEditor( false );
        panelLabel7.SetNumberEditorRange( 0, 100 );
        panelLabel7.SetNumberEditorInterval( 1 );
        panelLabel7.SetNumberEditorUsesMouseWheel( false );
        panelLabel7.SetHasCustomTextHoverColor( false );
        panelLabel7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel7.SetFont( "Arial", 6, true, false );

        panelLabel8 = new VoltageLabel( "panelLabel8", "Panel label: 8", this, "8" );
        AddComponent( panelLabel8 );
        panelLabel8.SetWantsMouseNotifications( false );
        panelLabel8.SetPosition( 68, 92 );
        panelLabel8.SetSize( 8, 8 );
        panelLabel8.SetEditable( false, false );
        panelLabel8.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel8.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel8.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel8.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel8.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel8.SetBorderSize( 1 );
        panelLabel8.SetMultiLineEdit( false );
        panelLabel8.SetIsNumberEditor( false );
        panelLabel8.SetNumberEditorRange( 0, 100 );
        panelLabel8.SetNumberEditorInterval( 1 );
        panelLabel8.SetNumberEditorUsesMouseWheel( false );
        panelLabel8.SetHasCustomTextHoverColor( false );
        panelLabel8.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel8.SetFont( "Arial", 6, true, false );

        panelLabel4 = new VoltageLabel( "panelLabel4", "Panel label: 4", this, "4" );
        AddComponent( panelLabel4 );
        panelLabel4.SetWantsMouseNotifications( false );
        panelLabel4.SetPosition( 28, 72 );
        panelLabel4.SetSize( 16, 8 );
        panelLabel4.SetEditable( false, false );
        panelLabel4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel4.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel4.SetBorderSize( 1 );
        panelLabel4.SetMultiLineEdit( false );
        panelLabel4.SetIsNumberEditor( false );
        panelLabel4.SetNumberEditorRange( 0, 100 );
        panelLabel4.SetNumberEditorInterval( 1 );
        panelLabel4.SetNumberEditorUsesMouseWheel( false );
        panelLabel4.SetHasCustomTextHoverColor( false );
        panelLabel4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel4.SetFont( "Arial", 6, true, false );

        panelLabel6 = new VoltageLabel( "panelLabel6", "Panel label: 6", this, "6" );
        AddComponent( panelLabel6 );
        panelLabel6.SetWantsMouseNotifications( false );
        panelLabel6.SetPosition( 50, 72 );
        panelLabel6.SetSize( 16, 8 );
        panelLabel6.SetEditable( false, false );
        panelLabel6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel6.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel6.SetBorderSize( 1 );
        panelLabel6.SetMultiLineEdit( false );
        panelLabel6.SetIsNumberEditor( false );
        panelLabel6.SetNumberEditorRange( 0, 100 );
        panelLabel6.SetNumberEditorInterval( 1 );
        panelLabel6.SetNumberEditorUsesMouseWheel( false );
        panelLabel6.SetHasCustomTextHoverColor( false );
        panelLabel6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel6.SetFont( "Arial", 6, true, false );

        panelLabelV = new VoltageLabel( "panelLabelV", "Panel label: V", this, "V" );
        AddComponent( panelLabelV );
        panelLabelV.SetWantsMouseNotifications( false );
        panelLabelV.SetPosition( 37, 119 );
        panelLabelV.SetSize( 20, 20 );
        panelLabelV.SetEditable( false, false );
        panelLabelV.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelV.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelV.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelV.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelV.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelV.SetBorderSize( 1 );
        panelLabelV.SetMultiLineEdit( false );
        panelLabelV.SetIsNumberEditor( false );
        panelLabelV.SetNumberEditorRange( 0, 100 );
        panelLabelV.SetNumberEditorInterval( 1 );
        panelLabelV.SetNumberEditorUsesMouseWheel( false );
        panelLabelV.SetHasCustomTextHoverColor( false );
        panelLabelV.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelV.SetFont( "Arial", 11, true, false );

        panelLabel1 = new VoltageLabel( "panelLabel1", "Panel label: 1", this, "1" );
        AddComponent( panelLabel1 );
        panelLabel1.SetWantsMouseNotifications( false );
        panelLabel1.SetPosition( 15, 106 );
        panelLabel1.SetSize( 16, 8 );
        panelLabel1.SetEditable( false, false );
        panelLabel1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel1.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel1.SetBorderSize( 1 );
        panelLabel1.SetMultiLineEdit( false );
        panelLabel1.SetIsNumberEditor( false );
        panelLabel1.SetNumberEditorRange( 0, 100 );
        panelLabel1.SetNumberEditorInterval( 1 );
        panelLabel1.SetNumberEditorUsesMouseWheel( false );
        panelLabel1.SetHasCustomTextHoverColor( false );
        panelLabel1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel1.SetFont( "Arial", 6, true, false );

        fineTuneKnob = new VoltageKnob( "fineTuneKnob", "Fine Tuning", this, -1, 1, 0 );
        AddComponent( fineTuneKnob );
        fineTuneKnob.SetWantsMouseNotifications( false );
        fineTuneKnob.SetPosition( 208, 145 );
        fineTuneKnob.SetSize( 30, 30 );
        fineTuneKnob.SetSkin( "Cosmo v2 Med" );
        fineTuneKnob.SetRange( -1, 1, 0, false, 0 );
        fineTuneKnob.SetKnobParams( 215, 145 );
        fineTuneKnob.DisplayValueInPercent( false );
        fineTuneKnob.SetKnobAdjustsRing( true );

        fractionVoltsKnob = new VoltageKnob( "fractionVoltsKnob", "Fractional Volts", this, 0, 1, 0 );
        AddComponent( fractionVoltsKnob );
        fractionVoltsKnob.SetWantsMouseNotifications( false );
        fractionVoltsKnob.SetPosition( 31, 142 );
        fractionVoltsKnob.SetSize( 30, 30 );
        fractionVoltsKnob.SetSkin( "Cosmo v2 Med" );
        fractionVoltsKnob.SetRange( 0, 1, 0, false, 0 );
        fractionVoltsKnob.SetKnobParams( 215, 145 );
        fractionVoltsKnob.DisplayValueInPercent( false );
        fractionVoltsKnob.SetKnobAdjustsRing( true );

        panelLabel9 = new VoltageLabel( "panelLabel9", "Panel label: 9", this, "9" );
        AddComponent( panelLabel9 );
        panelLabel9.SetWantsMouseNotifications( false );
        panelLabel9.SetPosition( 65, 106 );
        panelLabel9.SetSize( 11, 8 );
        panelLabel9.SetEditable( false, false );
        panelLabel9.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel9.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel9.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel9.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel9.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel9.SetBorderSize( 1 );
        panelLabel9.SetMultiLineEdit( false );
        panelLabel9.SetIsNumberEditor( false );
        panelLabel9.SetNumberEditorRange( 0, 100 );
        panelLabel9.SetNumberEditorInterval( 1 );
        panelLabel9.SetNumberEditorUsesMouseWheel( false );
        panelLabel9.SetHasCustomTextHoverColor( false );
        panelLabel9.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel9.SetFont( "Arial", 6, true, false );

        panelLabel02 = new VoltageLabel( "panelLabel02", "Panel label: 0", this, "0" );
        AddComponent( panelLabel02 );
        panelLabel02.SetWantsMouseNotifications( false );
        panelLabel02.SetPosition( 25, 169 );
        panelLabel02.SetSize( 16, 8 );
        panelLabel02.SetEditable( false, false );
        panelLabel02.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel02.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel02.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel02.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel02.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel02.SetBorderSize( 1 );
        panelLabel02.SetMultiLineEdit( false );
        panelLabel02.SetIsNumberEditor( false );
        panelLabel02.SetNumberEditorRange( 0, 100 );
        panelLabel02.SetNumberEditorInterval( 1 );
        panelLabel02.SetNumberEditorUsesMouseWheel( false );
        panelLabel02.SetHasCustomTextHoverColor( false );
        panelLabel02.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel02.SetFont( "Arial", 6, true, false );

        panelLabel12 = new VoltageLabel( "panelLabel12", "Panel label: 1", this, "1" );
        AddComponent( panelLabel12 );
        panelLabel12.SetWantsMouseNotifications( false );
        panelLabel12.SetPosition( 50, 169 );
        panelLabel12.SetSize( 16, 8 );
        panelLabel12.SetEditable( false, false );
        panelLabel12.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel12.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel12.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel12.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel12.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel12.SetBorderSize( 1 );
        panelLabel12.SetMultiLineEdit( false );
        panelLabel12.SetIsNumberEditor( false );
        panelLabel12.SetNumberEditorRange( 0, 100 );
        panelLabel12.SetNumberEditorInterval( 1 );
        panelLabel12.SetNumberEditorUsesMouseWheel( false );
        panelLabel12.SetHasCustomTextHoverColor( false );
        panelLabel12.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel12.SetFont( "Arial", 6, true, false );

        panelLabel05 = new VoltageLabel( "panelLabel05", "Panel label: 0.5", this, "0.5" );
        AddComponent( panelLabel05 );
        panelLabel05.SetWantsMouseNotifications( false );
        panelLabel05.SetPosition( 38, 134 );
        panelLabel05.SetSize( 16, 8 );
        panelLabel05.SetEditable( false, false );
        panelLabel05.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel05.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel05.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel05.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel05.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel05.SetBorderSize( 1 );
        panelLabel05.SetMultiLineEdit( false );
        panelLabel05.SetIsNumberEditor( false );
        panelLabel05.SetNumberEditorRange( 0, 100 );
        panelLabel05.SetNumberEditorInterval( 1 );
        panelLabel05.SetNumberEditorUsesMouseWheel( false );
        panelLabel05.SetHasCustomTextHoverColor( false );
        panelLabel05.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel05.SetFont( "Arial", 6, true, false );

        panelLabelCV = new VoltageLabel( "panelLabelCV", "Panel label: cV", this, "cV" );
        AddComponent( panelLabelCV );
        panelLabelCV.SetWantsMouseNotifications( false );
        panelLabelCV.SetPosition( 36, 168 );
        panelLabelCV.SetSize( 20, 20 );
        panelLabelCV.SetEditable( false, false );
        panelLabelCV.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelCV.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelCV.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelCV.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelCV.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelCV.SetBorderSize( 1 );
        panelLabelCV.SetMultiLineEdit( false );
        panelLabelCV.SetIsNumberEditor( false );
        panelLabelCV.SetNumberEditorRange( 0, 100 );
        panelLabelCV.SetNumberEditorInterval( 1 );
        panelLabelCV.SetNumberEditorUsesMouseWheel( false );
        panelLabelCV.SetHasCustomTextHoverColor( false );
        panelLabelCV.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelCV.SetFont( "Arial", 11, true, false );

        panelLabelEmpty = new VoltageLabel( "panelLabelEmpty", "Panel label: •", this, "•" );
        AddComponent( panelLabelEmpty );
        panelLabelEmpty.SetWantsMouseNotifications( false );
        panelLabelEmpty.SetPosition( 19, 147 );
        panelLabelEmpty.SetSize( 16, 8 );
        panelLabelEmpty.SetEditable( false, false );
        panelLabelEmpty.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelEmpty.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelEmpty.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelEmpty.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelEmpty.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelEmpty.SetBorderSize( 1 );
        panelLabelEmpty.SetMultiLineEdit( false );
        panelLabelEmpty.SetIsNumberEditor( false );
        panelLabelEmpty.SetNumberEditorRange( 0, 100 );
        panelLabelEmpty.SetNumberEditorInterval( 1 );
        panelLabelEmpty.SetNumberEditorUsesMouseWheel( false );
        panelLabelEmpty.SetHasCustomTextHoverColor( false );
        panelLabelEmpty.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelEmpty.SetFont( "Arial", 6, true, false );

        panelLabelEmpty2 = new VoltageLabel( "panelLabelEmpty2", "Panel label: •", this, "•" );
        AddComponent( panelLabelEmpty2 );
        panelLabelEmpty2.SetWantsMouseNotifications( false );
        panelLabelEmpty2.SetPosition( 56, 147 );
        panelLabelEmpty2.SetSize( 16, 8 );
        panelLabelEmpty2.SetEditable( false, false );
        panelLabelEmpty2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelEmpty2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelEmpty2.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelEmpty2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelEmpty2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelEmpty2.SetBorderSize( 1 );
        panelLabelEmpty2.SetMultiLineEdit( false );
        panelLabelEmpty2.SetIsNumberEditor( false );
        panelLabelEmpty2.SetNumberEditorRange( 0, 100 );
        panelLabelEmpty2.SetNumberEditorInterval( 1 );
        panelLabelEmpty2.SetNumberEditorUsesMouseWheel( false );
        panelLabelEmpty2.SetHasCustomTextHoverColor( false );
        panelLabelEmpty2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelEmpty2.SetFont( "Arial", 6, true, false );

        toneSwitch = new VoltageSwitch( "toneSwitch", "Reference Tone", this, 0 );
        AddComponent( toneSwitch );
        toneSwitch.SetWantsMouseNotifications( false );
        toneSwitch.SetPosition( 211, 191 );
        toneSwitch.SetSize( 23, 10 );
        toneSwitch.SetSkin( "Rocker Switch Plastic Orange Hor" );

        standardsSwitch = new VoltageSwitch( "standardsSwitch", "Pitch Standard", this, 1 );
        AddComponent( standardsSwitch );
        standardsSwitch.SetWantsMouseNotifications( false );
        standardsSwitch.SetPosition( 36, 272 );
        standardsSwitch.SetSize( 23, 10 );
        standardsSwitch.SetSkin( "3-State Slide Horizontal" );

        polaritySwitch = new VoltageSwitch( "polaritySwitch", "Manual Voltage Polarity", this, 1 );
        AddComponent( polaritySwitch );
        polaritySwitch.SetWantsMouseNotifications( false );
        polaritySwitch.SetPosition( 36, 185 );
        polaritySwitch.SetSize( 23, 10 );
        polaritySwitch.SetSkin( "Rocker Switch Plastic Cream Hor" );

        panelLabelHzV = new VoltageLabel( "panelLabelHzV", "Panel label: Hz/V", this, "Hz/V" );
        AddComponent( panelLabelHzV );
        panelLabelHzV.SetWantsMouseNotifications( false );
        panelLabelHzV.SetPosition( 15, 272 );
        panelLabelHzV.SetSize( 20, 10 );
        panelLabelHzV.SetEditable( false, false );
        panelLabelHzV.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelHzV.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelHzV.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelHzV.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelHzV.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelHzV.SetBorderSize( 1 );
        panelLabelHzV.SetMultiLineEdit( false );
        panelLabelHzV.SetIsNumberEditor( false );
        panelLabelHzV.SetNumberEditorRange( 0, 100 );
        panelLabelHzV.SetNumberEditorInterval( 1 );
        panelLabelHzV.SetNumberEditorUsesMouseWheel( false );
        panelLabelHzV.SetHasCustomTextHoverColor( false );
        panelLabelHzV.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelHzV.SetFont( "Arial", 5, true, false );

        panelLabelMinus = new VoltageLabel( "panelLabelMinus", "Panel label: -", this, "-" );
        AddComponent( panelLabelMinus );
        panelLabelMinus.SetWantsMouseNotifications( false );
        panelLabelMinus.SetPosition( 26, 184 );
        panelLabelMinus.SetSize( 10, 10 );
        panelLabelMinus.SetEditable( false, false );
        panelLabelMinus.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinus.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus.SetBorderSize( 1 );
        panelLabelMinus.SetMultiLineEdit( false );
        panelLabelMinus.SetIsNumberEditor( false );
        panelLabelMinus.SetNumberEditorRange( 0, 100 );
        panelLabelMinus.SetNumberEditorInterval( 1 );
        panelLabelMinus.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus.SetHasCustomTextHoverColor( false );
        panelLabelMinus.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus.SetFont( "Arial", 13, true, false );

        panelLabel05VO = new VoltageLabel( "panelLabel05VO", "Panel label: 0.5V/O", this, "0.5V/O" );
        AddComponent( panelLabel05VO );
        panelLabel05VO.SetWantsMouseNotifications( false );
        panelLabel05VO.SetPosition( 60, 272 );
        panelLabel05VO.SetSize( 20, 10 );
        panelLabel05VO.SetEditable( false, false );
        panelLabel05VO.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel05VO.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel05VO.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel05VO.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel05VO.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel05VO.SetBorderSize( 1 );
        panelLabel05VO.SetMultiLineEdit( false );
        panelLabel05VO.SetIsNumberEditor( false );
        panelLabel05VO.SetNumberEditorRange( 0, 100 );
        panelLabel05VO.SetNumberEditorInterval( 1 );
        panelLabel05VO.SetNumberEditorUsesMouseWheel( false );
        panelLabel05VO.SetHasCustomTextHoverColor( false );
        panelLabel05VO.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel05VO.SetFont( "Arial", 5, true, false );
}

void InitializeControls2()
{

        panelLabelPlus = new VoltageLabel( "panelLabelPlus", "Panel label: +", this, "+" );
        AddComponent( panelLabelPlus );
        panelLabelPlus.SetWantsMouseNotifications( false );
        panelLabelPlus.SetPosition( 59, 185 );
        panelLabelPlus.SetSize( 10, 10 );
        panelLabelPlus.SetEditable( false, false );
        panelLabelPlus.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelPlus.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelPlus.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelPlus.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelPlus.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelPlus.SetBorderSize( 1 );
        panelLabelPlus.SetMultiLineEdit( false );
        panelLabelPlus.SetIsNumberEditor( false );
        panelLabelPlus.SetNumberEditorRange( 0, 100 );
        panelLabelPlus.SetNumberEditorInterval( 1 );
        panelLabelPlus.SetNumberEditorUsesMouseWheel( false );
        panelLabelPlus.SetHasCustomTextHoverColor( false );
        panelLabelPlus.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelPlus.SetFont( "Arial", 9, true, false );

        panelLabelADDCONVA = new VoltageLabel( "panelLabelADDCONVA", "Panel label: ADD/CONV. A", this, "ADD/CONV. A" );
        AddComponent( panelLabelADDCONVA );
        panelLabelADDCONVA.SetWantsMouseNotifications( false );
        panelLabelADDCONVA.SetPosition( 18, 218 );
        panelLabelADDCONVA.SetSize( 60, 20 );
        panelLabelADDCONVA.SetEditable( false, false );
        panelLabelADDCONVA.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelADDCONVA.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelADDCONVA.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelADDCONVA.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelADDCONVA.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelADDCONVA.SetBorderSize( 1 );
        panelLabelADDCONVA.SetMultiLineEdit( false );
        panelLabelADDCONVA.SetIsNumberEditor( false );
        panelLabelADDCONVA.SetNumberEditorRange( 0, 100 );
        panelLabelADDCONVA.SetNumberEditorInterval( 1 );
        panelLabelADDCONVA.SetNumberEditorUsesMouseWheel( false );
        panelLabelADDCONVA.SetHasCustomTextHoverColor( false );
        panelLabelADDCONVA.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelADDCONVA.SetFont( "Arial", 9, true, false );

        negativeDcOutput = new VoltageAudioJack( "negativeDcOutput", "Negative Master Output", this, JackType.JackType_AudioOutput );
        AddComponent( negativeDcOutput );
        negativeDcOutput.SetWantsMouseNotifications( false );
        negativeDcOutput.SetPosition( 19, 284 );
        negativeDcOutput.SetSize( 25, 25 );
        negativeDcOutput.SetSkin( "Mini Jack 25px" );

        positiveDcOutput = new VoltageAudioJack( "positiveDcOutput", "Positive Master Output", this, JackType.JackType_AudioOutput );
        AddComponent( positiveDcOutput );
        positiveDcOutput.SetWantsMouseNotifications( false );
        positiveDcOutput.SetPosition( 53, 285 );
        positiveDcOutput.SetSize( 25, 25 );
        positiveDcOutput.SetSkin( "Mini Jack 25px" );

        oneHzOutput = new VoltageAudioJack( "oneHzOutput", "1 Hz Courtesy", this, JackType.JackType_AudioOutput );
        AddComponent( oneHzOutput );
        oneHzOutput.SetWantsMouseNotifications( false );
        oneHzOutput.SetPosition( 180, 213 );
        oneHzOutput.SetSize( 25, 25 );
        oneHzOutput.SetSkin( "Mini Jack 25px" );

        hundredHzOutput = new VoltageAudioJack( "hundredHzOutput", "100 Hz Courtesy", this, JackType.JackType_AudioOutput );
        AddComponent( hundredHzOutput );
        hundredHzOutput.SetWantsMouseNotifications( false );
        hundredHzOutput.SetPosition( 212, 213 );
        hundredHzOutput.SetSize( 25, 25 );
        hundredHzOutput.SetSkin( "Mini Jack 25px" );

        aReferenceOutput = new VoltageAudioJack( "aReferenceOutput", "A4 Reference", this, JackType.JackType_AudioOutput );
        AddComponent( aReferenceOutput );
        aReferenceOutput.SetWantsMouseNotifications( false );
        aReferenceOutput.SetPosition( 180, 285 );
        aReferenceOutput.SetSize( 25, 25 );
        aReferenceOutput.SetSkin( "Mini Jack 25px" );

        thousandHzOutput = new VoltageAudioJack( "thousandHzOutput", "1 kHz Courtesy", this, JackType.JackType_AudioOutput );
        AddComponent( thousandHzOutput );
        thousandHzOutput.SetWantsMouseNotifications( false );
        thousandHzOutput.SetPosition( 244, 213 );
        thousandHzOutput.SetSize( 25, 25 );
        thousandHzOutput.SetSkin( "Mini Jack 25px" );

        sweepOutput = new VoltageAudioJack( "sweepOutput", "Sine Sweep", this, JackType.JackType_AudioOutput );
        AddComponent( sweepOutput );
        sweepOutput.SetWantsMouseNotifications( false );
        sweepOutput.SetPosition( 235, 250 );
        sweepOutput.SetSize( 25, 25 );
        sweepOutput.SetSkin( "Mini Jack 25px" );

        cReferenceOutput = new VoltageAudioJack( "cReferenceOutput", "C4 Reference", this, JackType.JackType_AudioOutput );
        AddComponent( cReferenceOutput );
        cReferenceOutput.SetWantsMouseNotifications( false );
        cReferenceOutput.SetPosition( 212, 285 );
        cReferenceOutput.SetSize( 25, 25 );
        cReferenceOutput.SetSkin( "Mini Jack 25px" );

        fSharpReferenceOutput = new VoltageAudioJack( "fSharpReferenceOutput", "F#4 Reference", this, JackType.JackType_AudioOutput );
        AddComponent( fSharpReferenceOutput );
        fSharpReferenceOutput.SetWantsMouseNotifications( false );
        fSharpReferenceOutput.SetPosition( 242, 285 );
        fSharpReferenceOutput.SetSize( 25, 25 );
        fSharpReferenceOutput.SetSkin( "Mini Jack 25px" );

        panelLabelPlusDC = new VoltageLabel( "panelLabelPlusDC", "Panel label: + DC", this, "+ DC" );
        AddComponent( panelLabelPlusDC );
        panelLabelPlusDC.SetWantsMouseNotifications( false );
        panelLabelPlusDC.SetPosition( 55, 305 );
        panelLabelPlusDC.SetSize( 20, 20 );
        panelLabelPlusDC.SetEditable( false, false );
        panelLabelPlusDC.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelPlusDC.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelPlusDC.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelPlusDC.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelPlusDC.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelPlusDC.SetBorderSize( 1 );
        panelLabelPlusDC.SetMultiLineEdit( false );
        panelLabelPlusDC.SetIsNumberEditor( false );
        panelLabelPlusDC.SetNumberEditorRange( 0, 100 );
        panelLabelPlusDC.SetNumberEditorInterval( 1 );
        panelLabelPlusDC.SetNumberEditorUsesMouseWheel( false );
        panelLabelPlusDC.SetHasCustomTextHoverColor( false );
        panelLabelPlusDC.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelPlusDC.SetFont( "Arial", 9, true, false );

        panelLabelMinusDC = new VoltageLabel( "panelLabelMinusDC", "Panel label: - DC", this, "- DC" );
        AddComponent( panelLabelMinusDC );
        panelLabelMinusDC.SetWantsMouseNotifications( false );
        panelLabelMinusDC.SetPosition( 21, 304 );
        panelLabelMinusDC.SetSize( 20, 20 );
        panelLabelMinusDC.SetEditable( false, false );
        panelLabelMinusDC.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinusDC.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinusDC.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelMinusDC.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinusDC.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinusDC.SetBorderSize( 1 );
        panelLabelMinusDC.SetMultiLineEdit( false );
        panelLabelMinusDC.SetIsNumberEditor( false );
        panelLabelMinusDC.SetNumberEditorRange( 0, 100 );
        panelLabelMinusDC.SetNumberEditorInterval( 1 );
        panelLabelMinusDC.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinusDC.SetHasCustomTextHoverColor( false );
        panelLabelMinusDC.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinusDC.SetFont( "Arial", 9, true, false );

        tuningDisplay = new VoltageDigitalCounter( "tuningDisplay", "A Reference Frequency", this, 5 );
        AddComponent( tuningDisplay );
        tuningDisplay.SetWantsMouseNotifications( false );
        tuningDisplay.SetPosition( 195, 38 );
        tuningDisplay.SetSize( 60, 30 );
        tuningDisplay.SetSkin( "Gray" );
        tuningDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        voltageDisplay = new VoltageDigitalCounter( "voltageDisplay", "Converted Master Voltage", this, 5 );
        AddComponent( voltageDisplay );
        voltageDisplay.SetWantsMouseNotifications( false );
        voltageDisplay.SetPosition( 17, 38 );
        voltageDisplay.SetSize( 60, 30 );
        voltageDisplay.SetSkin( "Gray" );
        voltageDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        lowPassOutput = new VoltageAudioJack( "lowPassOutput", "Low Pass Output", this, JackType.JackType_AudioOutput );
        AddComponent( lowPassOutput );
        lowPassOutput.SetWantsMouseNotifications( false );
        lowPassOutput.SetPosition( 418, 285 );
        lowPassOutput.SetSize( 25, 25 );
        lowPassOutput.SetSkin( "Mini Jack 25px" );

        panelLabelI = new VoltageLabel( "panelLabelI", "Panel label: I", this, "I" );
        AddComponent( panelLabelI );
        panelLabelI.SetWantsMouseNotifications( false );
        panelLabelI.SetPosition( 386, 305 );
        panelLabelI.SetSize( 20, 20 );
        panelLabelI.SetEditable( false, false );
        panelLabelI.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelI.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelI.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelI.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelI.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelI.SetBorderSize( 1 );
        panelLabelI.SetMultiLineEdit( false );
        panelLabelI.SetIsNumberEditor( false );
        panelLabelI.SetNumberEditorRange( 0, 100 );
        panelLabelI.SetNumberEditorInterval( 1 );
        panelLabelI.SetNumberEditorUsesMouseWheel( false );
        panelLabelI.SetHasCustomTextHoverColor( false );
        panelLabelI.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelI.SetFont( "Arial", 9, true, false );

        panelLabelO = new VoltageLabel( "panelLabelO", "Panel label: O", this, "O" );
        AddComponent( panelLabelO );
        panelLabelO.SetWantsMouseNotifications( false );
        panelLabelO.SetPosition( 419, 305 );
        panelLabelO.SetSize( 20, 20 );
        panelLabelO.SetEditable( false, false );
        panelLabelO.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelO.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelO.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelO.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelO.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelO.SetBorderSize( 1 );
        panelLabelO.SetMultiLineEdit( false );
        panelLabelO.SetIsNumberEditor( false );
        panelLabelO.SetNumberEditorRange( 0, 100 );
        panelLabelO.SetNumberEditorInterval( 1 );
        panelLabelO.SetNumberEditorUsesMouseWheel( false );
        panelLabelO.SetHasCustomTextHoverColor( false );
        panelLabelO.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelO.SetFont( "Arial", 9, true, false );

        panelLabelLPF = new VoltageLabel( "panelLabelLPF", "Panel label: LPF", this, "LPF" );
        AddComponent( panelLabelLPF );
        panelLabelLPF.SetWantsMouseNotifications( false );
        panelLabelLPF.SetPosition( 385, 272 );
        panelLabelLPF.SetSize( 57, 20 );
        panelLabelLPF.SetEditable( false, false );
        panelLabelLPF.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelLPF.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelLPF.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelLPF.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelLPF.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelLPF.SetBorderSize( 1 );
        panelLabelLPF.SetMultiLineEdit( false );
        panelLabelLPF.SetIsNumberEditor( false );
        panelLabelLPF.SetNumberEditorRange( 0, 100 );
        panelLabelLPF.SetNumberEditorInterval( 1 );
        panelLabelLPF.SetNumberEditorUsesMouseWheel( false );
        panelLabelLPF.SetHasCustomTextHoverColor( false );
        panelLabelLPF.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelLPF.SetFont( "Arial", 9, true, false );

        panelLabelHPF = new VoltageLabel( "panelLabelHPF", "Panel label: HPF", this, "HPF" );
        AddComponent( panelLabelHPF );
        panelLabelHPF.SetWantsMouseNotifications( false );
        panelLabelHPF.SetPosition( 385, 211 );
        panelLabelHPF.SetSize( 57, 20 );
        panelLabelHPF.SetEditable( false, false );
        panelLabelHPF.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelHPF.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelHPF.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelHPF.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelHPF.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelHPF.SetBorderSize( 1 );
        panelLabelHPF.SetMultiLineEdit( false );
        panelLabelHPF.SetIsNumberEditor( false );
        panelLabelHPF.SetNumberEditorRange( 0, 100 );
        panelLabelHPF.SetNumberEditorInterval( 1 );
        panelLabelHPF.SetNumberEditorUsesMouseWheel( false );
        panelLabelHPF.SetHasCustomTextHoverColor( false );
        panelLabelHPF.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelHPF.SetFont( "Arial", 9, true, false );

        highPassKnob = new VoltageKnob( "highPassKnob", "High Pass Cutoff", this, 0, 1, 0 );
        AddComponent( highPassKnob );
        highPassKnob.SetWantsMouseNotifications( false );
        highPassKnob.SetPosition( 401, 191 );
        highPassKnob.SetSize( 25, 25 );
        highPassKnob.SetSkin( "Cosmo Small" );
        highPassKnob.SetRange( 0, 1, 0, false, 0 );
        highPassKnob.SetKnobParams( 215, 145 );
        highPassKnob.DisplayValueInPercent( false );
        highPassKnob.SetKnobAdjustsRing( true );

        lowPassKnob = new VoltageKnob( "lowPassKnob", "Low Pass Cutoff", this, 0, 1, 1 );
        AddComponent( lowPassKnob );
        lowPassKnob.SetWantsMouseNotifications( false );
        lowPassKnob.SetPosition( 401, 253 );
        lowPassKnob.SetSize( 25, 25 );
        lowPassKnob.SetSkin( "Cosmo Small" );
        lowPassKnob.SetRange( 0, 1, 1, false, 0 );
        lowPassKnob.SetKnobParams( 215, 145 );
        lowPassKnob.DisplayValueInPercent( false );
        lowPassKnob.SetKnobAdjustsRing( true );

        panelLabel392 = new VoltageLabel( "panelLabel392", "Panel label: 392", this, "392" );
        AddComponent( panelLabel392 );
        panelLabel392.SetWantsMouseNotifications( false );
        panelLabel392.SetPosition( 196, 120 );
        panelLabel392.SetSize( 16, 8 );
        panelLabel392.SetEditable( false, false );
        panelLabel392.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel392.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel392.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel392.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel392.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel392.SetBorderSize( 1 );
        panelLabel392.SetMultiLineEdit( false );
        panelLabel392.SetIsNumberEditor( false );
        panelLabel392.SetNumberEditorRange( 0, 100 );
        panelLabel392.SetNumberEditorInterval( 1 );
        panelLabel392.SetNumberEditorUsesMouseWheel( false );
        panelLabel392.SetHasCustomTextHoverColor( false );
        panelLabel392.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel392.SetFont( "Arial", 6, true, false );

        panelLabelEXP = new VoltageLabel( "panelLabelEXP", "Panel label: EXP", this, "EXP" );
        AddComponent( panelLabelEXP );
        panelLabelEXP.SetWantsMouseNotifications( false );
        panelLabelEXP.SetPosition( 176, 249 );
        panelLabelEXP.SetSize( 16, 8 );
        panelLabelEXP.SetEditable( false, false );
        panelLabelEXP.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelEXP.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelEXP.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelEXP.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelEXP.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelEXP.SetBorderSize( 1 );
        panelLabelEXP.SetMultiLineEdit( false );
        panelLabelEXP.SetIsNumberEditor( false );
        panelLabelEXP.SetNumberEditorRange( 0, 100 );
        panelLabelEXP.SetNumberEditorInterval( 1 );
        panelLabelEXP.SetNumberEditorUsesMouseWheel( false );
        panelLabelEXP.SetHasCustomTextHoverColor( false );
        panelLabelEXP.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelEXP.SetFont( "Arial", 4, true, false );

        panelLabelDC = new VoltageLabel( "panelLabelDC", "Panel label: DC", this, "DC" );
        AddComponent( panelLabelDC );
        panelLabelDC.SetWantsMouseNotifications( false );
        panelLabelDC.SetPosition( 348, 201 );
        panelLabelDC.SetSize( 16, 8 );
        panelLabelDC.SetEditable( false, false );
        panelLabelDC.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelDC.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelDC.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelDC.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelDC.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelDC.SetBorderSize( 1 );
        panelLabelDC.SetMultiLineEdit( false );
        panelLabelDC.SetIsNumberEditor( false );
        panelLabelDC.SetNumberEditorRange( 0, 100 );
        panelLabelDC.SetNumberEditorInterval( 1 );
        panelLabelDC.SetNumberEditorUsesMouseWheel( false );
        panelLabelDC.SetHasCustomTextHoverColor( false );
        panelLabelDC.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelDC.SetFont( "Arial", 4, true, false );

        panelLabelDC2 = new VoltageLabel( "panelLabelDC2", "Panel label: DC", this, "DC" );
        AddComponent( panelLabelDC2 );
        panelLabelDC2.SetWantsMouseNotifications( false );
        panelLabelDC2.SetPosition( 275, 246 );
        panelLabelDC2.SetSize( 16, 8 );
        panelLabelDC2.SetEditable( false, false );
        panelLabelDC2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelDC2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelDC2.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelDC2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelDC2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelDC2.SetBorderSize( 1 );
        panelLabelDC2.SetMultiLineEdit( false );
        panelLabelDC2.SetIsNumberEditor( false );
        panelLabelDC2.SetNumberEditorRange( 0, 100 );
        panelLabelDC2.SetNumberEditorInterval( 1 );
        panelLabelDC2.SetNumberEditorUsesMouseWheel( false );
        panelLabelDC2.SetHasCustomTextHoverColor( false );
        panelLabelDC2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelDC2.SetFont( "Arial", 4, true, false );

        panelLabelVOLTS = new VoltageLabel( "panelLabelVOLTS", "Panel label: VOLTS", this, "VOLTS" );
        AddComponent( panelLabelVOLTS );
        panelLabelVOLTS.SetWantsMouseNotifications( false );
        panelLabelVOLTS.SetPosition( 312, 284 );
        panelLabelVOLTS.SetSize( 16, 8 );
        panelLabelVOLTS.SetEditable( false, false );
        panelLabelVOLTS.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelVOLTS.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelVOLTS.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelVOLTS.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelVOLTS.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelVOLTS.SetBorderSize( 1 );
        panelLabelVOLTS.SetMultiLineEdit( false );
        panelLabelVOLTS.SetIsNumberEditor( false );
        panelLabelVOLTS.SetNumberEditorRange( 0, 100 );
        panelLabelVOLTS.SetNumberEditorInterval( 1 );
        panelLabelVOLTS.SetNumberEditorUsesMouseWheel( false );
        panelLabelVOLTS.SetHasCustomTextHoverColor( false );
        panelLabelVOLTS.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelVOLTS.SetFont( "Arial", 4, true, false );

        panelLabelVpp = new VoltageLabel( "panelLabelVpp", "Panel label: Vpp", this, "Vpp" );
        AddComponent( panelLabelVpp );
        panelLabelVpp.SetWantsMouseNotifications( false );
        panelLabelVpp.SetPosition( 348, 221 );
        panelLabelVpp.SetSize( 16, 8 );
        panelLabelVpp.SetEditable( false, false );
        panelLabelVpp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelVpp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelVpp.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelVpp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelVpp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelVpp.SetBorderSize( 1 );
        panelLabelVpp.SetMultiLineEdit( false );
        panelLabelVpp.SetIsNumberEditor( false );
        panelLabelVpp.SetNumberEditorRange( 0, 100 );
        panelLabelVpp.SetNumberEditorInterval( 1 );
        panelLabelVpp.SetNumberEditorUsesMouseWheel( false );
        panelLabelVpp.SetHasCustomTextHoverColor( false );
        panelLabelVpp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelVpp.SetFont( "Arial", 4, true, false );

        panelLabelLIN = new VoltageLabel( "panelLabelLIN", "Panel label: LIN", this, "LIN" );
        AddComponent( panelLabelLIN );
        panelLabelLIN.SetWantsMouseNotifications( false );
        panelLabelLIN.SetPosition( 176, 269 );
        panelLabelLIN.SetSize( 16, 8 );
        panelLabelLIN.SetEditable( false, false );
        panelLabelLIN.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelLIN.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelLIN.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelLIN.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelLIN.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelLIN.SetBorderSize( 1 );
        panelLabelLIN.SetMultiLineEdit( false );
        panelLabelLIN.SetIsNumberEditor( false );
        panelLabelLIN.SetNumberEditorRange( 0, 100 );
        panelLabelLIN.SetNumberEditorInterval( 1 );
        panelLabelLIN.SetNumberEditorUsesMouseWheel( false );
        panelLabelLIN.SetHasCustomTextHoverColor( false );
        panelLabelLIN.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelLIN.SetFont( "Arial", 4, true, false );

        panelLabelVpp2 = new VoltageLabel( "panelLabelVpp2", "Panel label: Vpp", this, "Vpp" );
        AddComponent( panelLabelVpp2 );
        panelLabelVpp2.SetWantsMouseNotifications( false );
        panelLabelVpp2.SetPosition( 275, 266 );
        panelLabelVpp2.SetSize( 16, 8 );
        panelLabelVpp2.SetEditable( false, false );
        panelLabelVpp2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelVpp2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelVpp2.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelVpp2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelVpp2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelVpp2.SetBorderSize( 1 );
        panelLabelVpp2.SetMultiLineEdit( false );
        panelLabelVpp2.SetIsNumberEditor( false );
        panelLabelVpp2.SetNumberEditorRange( 0, 100 );
        panelLabelVpp2.SetNumberEditorInterval( 1 );
        panelLabelVpp2.SetNumberEditorUsesMouseWheel( false );
        panelLabelVpp2.SetHasCustomTextHoverColor( false );
        panelLabelVpp2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelVpp2.SetFont( "Arial", 4, true, false );

        panelLabelPITCH = new VoltageLabel( "panelLabelPITCH", "Panel label: PITCH", this, "PITCH" );
        AddComponent( panelLabelPITCH );
        panelLabelPITCH.SetWantsMouseNotifications( false );
        panelLabelPITCH.SetPosition( 312, 304 );
        panelLabelPITCH.SetSize( 16, 8 );
        panelLabelPITCH.SetEditable( false, false );
        panelLabelPITCH.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelPITCH.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelPITCH.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelPITCH.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelPITCH.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelPITCH.SetBorderSize( 1 );
        panelLabelPITCH.SetMultiLineEdit( false );
        panelLabelPITCH.SetIsNumberEditor( false );
        panelLabelPITCH.SetNumberEditorRange( 0, 100 );
        panelLabelPITCH.SetNumberEditorInterval( 1 );
        panelLabelPITCH.SetNumberEditorUsesMouseWheel( false );
        panelLabelPITCH.SetHasCustomTextHoverColor( false );
        panelLabelPITCH.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelPITCH.SetFont( "Arial", 4, true, false );

        panelLabel444 = new VoltageLabel( "panelLabel444", "Panel label: 444", this, "444" );
        AddComponent( panelLabel444 );
        panelLabel444.SetWantsMouseNotifications( false );
        panelLabel444.SetPosition( 233, 120 );
        panelLabel444.SetSize( 16, 8 );
        panelLabel444.SetEditable( false, false );
        panelLabel444.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel444.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel444.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel444.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel444.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel444.SetBorderSize( 1 );
        panelLabel444.SetMultiLineEdit( false );
        panelLabel444.SetIsNumberEditor( false );
        panelLabel444.SetNumberEditorRange( 0, 100 );
        panelLabel444.SetNumberEditorInterval( 1 );
        panelLabel444.SetNumberEditorUsesMouseWheel( false );
        panelLabel444.SetHasCustomTextHoverColor( false );
        panelLabel444.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel444.SetFont( "Arial", 6, true, false );

        panelLabel440 = new VoltageLabel( "panelLabel440", "Panel label: 440", this, "440" );
        AddComponent( panelLabel440 );
        panelLabel440.SetWantsMouseNotifications( false );
        panelLabel440.SetPosition( 215, 70 );
        panelLabel440.SetSize( 16, 8 );
        panelLabel440.SetEditable( false, false );
        panelLabel440.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel440.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel440.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel440.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel440.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel440.SetBorderSize( 1 );
        panelLabel440.SetMultiLineEdit( false );
        panelLabel440.SetIsNumberEditor( false );
        panelLabel440.SetNumberEditorRange( 0, 100 );
        panelLabel440.SetNumberEditorInterval( 1 );
        panelLabel440.SetNumberEditorUsesMouseWheel( false );
        panelLabel440.SetHasCustomTextHoverColor( false );
        panelLabel440.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel440.SetFont( "Arial", 6, true, false );

        panelLabel432 = new VoltageLabel( "panelLabel432", "Panel label: 432", this, "432" );
        AddComponent( panelLabel432 );
        panelLabel432.SetWantsMouseNotifications( false );
        panelLabel432.SetPosition( 189, 80 );
        panelLabel432.SetSize( 16, 8 );
        panelLabel432.SetEditable( false, false );
        panelLabel432.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel432.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel432.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel432.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel432.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel432.SetBorderSize( 1 );
        panelLabel432.SetMultiLineEdit( false );
        panelLabel432.SetIsNumberEditor( false );
        panelLabel432.SetNumberEditorRange( 0, 100 );
        panelLabel432.SetNumberEditorInterval( 1 );
        panelLabel432.SetNumberEditorUsesMouseWheel( false );
        panelLabel432.SetHasCustomTextHoverColor( false );
        panelLabel432.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel432.SetFont( "Arial", 6, true, false );

        panelLabel435 = new VoltageLabel( "panelLabel435", "Panel label: 435", this, "435" );
        AddComponent( panelLabel435 );
        panelLabel435.SetWantsMouseNotifications( false );
        panelLabel435.SetPosition( 239, 80 );
        panelLabel435.SetSize( 15, 8 );
        panelLabel435.SetEditable( false, false );
        panelLabel435.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel435.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel435.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel435.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel435.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel435.SetBorderSize( 1 );
        panelLabel435.SetMultiLineEdit( false );
        panelLabel435.SetIsNumberEditor( false );
        panelLabel435.SetNumberEditorRange( 0, 100 );
        panelLabel435.SetNumberEditorInterval( 1 );
        panelLabel435.SetNumberEditorUsesMouseWheel( false );
        panelLabel435.SetHasCustomTextHoverColor( false );
        panelLabel435.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel435.SetFont( "Arial", 6, true, false );

        panelLabelA = new VoltageLabel( "panelLabelA", "Panel label: A=", this, "A=" );
        AddComponent( panelLabelA );
        panelLabelA.SetWantsMouseNotifications( false );
        panelLabelA.SetPosition( 213, 119 );
        panelLabelA.SetSize( 20, 20 );
        panelLabelA.SetEditable( false, false );
        panelLabelA.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelA.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelA.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelA.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelA.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelA.SetBorderSize( 1 );
        panelLabelA.SetMultiLineEdit( false );
        panelLabelA.SetIsNumberEditor( false );
        panelLabelA.SetNumberEditorRange( 0, 100 );
        panelLabelA.SetNumberEditorInterval( 1 );
        panelLabelA.SetNumberEditorUsesMouseWheel( false );
        panelLabelA.SetHasCustomTextHoverColor( false );
        panelLabelA.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelA.SetFont( "Arial", 11, true, false );

        panelLabelA2 = new VoltageLabel( "panelLabelA2", "Panel label: A", this, "A" );
        AddComponent( panelLabelA2 );
        panelLabelA2.SetWantsMouseNotifications( false );
        panelLabelA2.SetPosition( 183, 305 );
        panelLabelA2.SetSize( 20, 20 );
        panelLabelA2.SetEditable( false, false );
        panelLabelA2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelA2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelA2.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelA2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelA2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelA2.SetBorderSize( 1 );
        panelLabelA2.SetMultiLineEdit( false );
        panelLabelA2.SetIsNumberEditor( false );
        panelLabelA2.SetNumberEditorRange( 0, 100 );
        panelLabelA2.SetNumberEditorInterval( 1 );
        panelLabelA2.SetNumberEditorUsesMouseWheel( false );
        panelLabelA2.SetHasCustomTextHoverColor( false );
        panelLabelA2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelA2.SetFont( "Arial", 10, true, false );

        panelLabelC = new VoltageLabel( "panelLabelC", "Panel label: C", this, "C" );
        AddComponent( panelLabelC );
        panelLabelC.SetWantsMouseNotifications( false );
        panelLabelC.SetPosition( 214, 305 );
        panelLabelC.SetSize( 20, 20 );
        panelLabelC.SetEditable( false, false );
        panelLabelC.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelC.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelC.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelC.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelC.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelC.SetBorderSize( 1 );
        panelLabelC.SetMultiLineEdit( false );
        panelLabelC.SetIsNumberEditor( false );
        panelLabelC.SetNumberEditorRange( 0, 100 );
        panelLabelC.SetNumberEditorInterval( 1 );
        panelLabelC.SetNumberEditorUsesMouseWheel( false );
        panelLabelC.SetHasCustomTextHoverColor( false );
        panelLabelC.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelC.SetFont( "Arial", 10, true, false );

        panelLabelFSharp = new VoltageLabel( "panelLabelFSharp", "Panel label: F#", this, "F#" );
        AddComponent( panelLabelFSharp );
        panelLabelFSharp.SetWantsMouseNotifications( false );
        panelLabelFSharp.SetPosition( 244, 305 );
        panelLabelFSharp.SetSize( 20, 20 );
        panelLabelFSharp.SetEditable( false, false );
        panelLabelFSharp.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelFSharp.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelFSharp.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelFSharp.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelFSharp.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelFSharp.SetBorderSize( 1 );
        panelLabelFSharp.SetMultiLineEdit( false );
        panelLabelFSharp.SetIsNumberEditor( false );
        panelLabelFSharp.SetNumberEditorRange( 0, 100 );
        panelLabelFSharp.SetNumberEditorInterval( 1 );
        panelLabelFSharp.SetNumberEditorUsesMouseWheel( false );
        panelLabelFSharp.SetHasCustomTextHoverColor( false );
        panelLabelFSharp.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelFSharp.SetFont( "Arial", 10, true, false );

        panelLabel4225 = new VoltageLabel( "panelLabel4225", "Panel label: 422.5", this, "422.5" );
        AddComponent( panelLabel4225 );
        panelLabel4225.SetWantsMouseNotifications( false );
        panelLabel4225.SetPosition( 185, 100 );
        panelLabel4225.SetSize( 16, 8 );
        panelLabel4225.SetEditable( false, false );
        panelLabel4225.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel4225.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel4225.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel4225.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel4225.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel4225.SetBorderSize( 1 );
        panelLabel4225.SetMultiLineEdit( false );
        panelLabel4225.SetIsNumberEditor( false );
        panelLabel4225.SetNumberEditorRange( 0, 100 );
        panelLabel4225.SetNumberEditorInterval( 1 );
        panelLabel4225.SetNumberEditorUsesMouseWheel( false );
        panelLabel4225.SetHasCustomTextHoverColor( false );
        panelLabel4225.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel4225.SetFont( "Arial", 6, true, false );

        panelLabel442 = new VoltageLabel( "panelLabel442", "Panel label: 442", this, "442" );
        AddComponent( panelLabel442 );
        panelLabel442.SetWantsMouseNotifications( false );
        panelLabel442.SetPosition( 246, 100 );
        panelLabel442.SetSize( 16, 8 );
        panelLabel442.SetEditable( false, false );
        panelLabel442.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel442.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel442.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel442.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel442.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel442.SetBorderSize( 1 );
        panelLabel442.SetMultiLineEdit( false );
        panelLabel442.SetIsNumberEditor( false );
        panelLabel442.SetNumberEditorRange( 0, 100 );
        panelLabel442.SetNumberEditorInterval( 1 );
        panelLabel442.SetNumberEditorUsesMouseWheel( false );
        panelLabel442.SetHasCustomTextHoverColor( false );
        panelLabel442.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel442.SetFont( "Arial", 6, true, false );

        panelLabelMinus1 = new VoltageLabel( "panelLabelMinus1", "Panel label: -1", this, "-1" );
        AddComponent( panelLabelMinus1 );
        panelLabelMinus1.SetWantsMouseNotifications( false );
        panelLabelMinus1.SetPosition( 200, 172 );
        panelLabelMinus1.SetSize( 16, 8 );
        panelLabelMinus1.SetEditable( false, false );
        panelLabelMinus1.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMinus1.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMinus1.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelMinus1.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMinus1.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMinus1.SetBorderSize( 1 );
        panelLabelMinus1.SetMultiLineEdit( false );
        panelLabelMinus1.SetIsNumberEditor( false );
        panelLabelMinus1.SetNumberEditorRange( 0, 100 );
        panelLabelMinus1.SetNumberEditorInterval( 1 );
        panelLabelMinus1.SetNumberEditorUsesMouseWheel( false );
        panelLabelMinus1.SetHasCustomTextHoverColor( false );
        panelLabelMinus1.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMinus1.SetFont( "Arial", 6, true, false );

        panelLabel13 = new VoltageLabel( "panelLabel13", "Panel label: 1", this, "1" );
        AddComponent( panelLabel13 );
        panelLabel13.SetWantsMouseNotifications( false );
        panelLabel13.SetPosition( 229, 172 );
        panelLabel13.SetSize( 16, 8 );
        panelLabel13.SetEditable( false, false );
        panelLabel13.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel13.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel13.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel13.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel13.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel13.SetBorderSize( 1 );
        panelLabel13.SetMultiLineEdit( false );
        panelLabel13.SetIsNumberEditor( false );
        panelLabel13.SetNumberEditorRange( 0, 100 );
        panelLabel13.SetNumberEditorInterval( 1 );
        panelLabel13.SetNumberEditorUsesMouseWheel( false );
        panelLabel13.SetHasCustomTextHoverColor( false );
        panelLabel13.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel13.SetFont( "Arial", 6, true, false );

        panelLabel03 = new VoltageLabel( "panelLabel03", "Panel label: 0", this, "0" );
        AddComponent( panelLabel03 );
        panelLabel03.SetWantsMouseNotifications( false );
        panelLabel03.SetPosition( 215, 136 );
        panelLabel03.SetSize( 16, 8 );
        panelLabel03.SetEditable( false, false );
        panelLabel03.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabel03.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabel03.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabel03.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabel03.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabel03.SetBorderSize( 1 );
        panelLabel03.SetMultiLineEdit( false );
        panelLabel03.SetIsNumberEditor( false );
        panelLabel03.SetNumberEditorRange( 0, 100 );
        panelLabel03.SetNumberEditorInterval( 1 );
        panelLabel03.SetNumberEditorUsesMouseWheel( false );
        panelLabel03.SetHasCustomTextHoverColor( false );
        panelLabel03.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabel03.SetFont( "Arial", 6, true, false );

        panelLabelFINE = new VoltageLabel( "panelLabelFINE", "Panel label: FINE", this, "FINE" );
        AddComponent( panelLabelFINE );
        panelLabelFINE.SetWantsMouseNotifications( false );
        panelLabelFINE.SetPosition( 213, 171 );
        panelLabelFINE.SetSize( 20, 20 );
        panelLabelFINE.SetEditable( false, false );
        panelLabelFINE.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelFINE.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelFINE.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelFINE.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelFINE.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelFINE.SetBorderSize( 1 );
        panelLabelFINE.SetMultiLineEdit( false );
        panelLabelFINE.SetIsNumberEditor( false );
        panelLabelFINE.SetNumberEditorRange( 0, 100 );
        panelLabelFINE.SetNumberEditorInterval( 1 );
        panelLabelFINE.SetNumberEditorUsesMouseWheel( false );
        panelLabelFINE.SetHasCustomTextHoverColor( false );
        panelLabelFINE.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelFINE.SetFont( "Arial", 9, true, false );

        panelLabelEmpty3 = new VoltageLabel( "panelLabelEmpty3", "Panel label: •", this, "•" );
        AddComponent( panelLabelEmpty3 );
        panelLabelEmpty3.SetWantsMouseNotifications( false );
        panelLabelEmpty3.SetPosition( 196, 150 );
        panelLabelEmpty3.SetSize( 16, 8 );
        panelLabelEmpty3.SetEditable( false, false );
        panelLabelEmpty3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelEmpty3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelEmpty3.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelEmpty3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelEmpty3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelEmpty3.SetBorderSize( 1 );
        panelLabelEmpty3.SetMultiLineEdit( false );
        panelLabelEmpty3.SetIsNumberEditor( false );
        panelLabelEmpty3.SetNumberEditorRange( 0, 100 );
        panelLabelEmpty3.SetNumberEditorInterval( 1 );
        panelLabelEmpty3.SetNumberEditorUsesMouseWheel( false );
        panelLabelEmpty3.SetHasCustomTextHoverColor( false );
        panelLabelEmpty3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelEmpty3.SetFont( "Arial", 6, true, false );

        panelLabelEmpty4 = new VoltageLabel( "panelLabelEmpty4", "Panel label: •", this, "•" );
        AddComponent( panelLabelEmpty4 );
        panelLabelEmpty4.SetWantsMouseNotifications( false );
        panelLabelEmpty4.SetPosition( 233, 150 );
        panelLabelEmpty4.SetSize( 16, 8 );
        panelLabelEmpty4.SetEditable( false, false );
        panelLabelEmpty4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelEmpty4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelEmpty4.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelEmpty4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelEmpty4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelEmpty4.SetBorderSize( 1 );
        panelLabelEmpty4.SetMultiLineEdit( false );
        panelLabelEmpty4.SetIsNumberEditor( false );
        panelLabelEmpty4.SetNumberEditorRange( 0, 100 );
        panelLabelEmpty4.SetNumberEditorInterval( 1 );
        panelLabelEmpty4.SetNumberEditorUsesMouseWheel( false );
        panelLabelEmpty4.SetHasCustomTextHoverColor( false );
        panelLabelEmpty4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelEmpty4.SetFont( "Arial", 6, true, false );

        panelLabelTONE = new VoltageLabel( "panelLabelTONE", "Panel label: TONE", this, "TONE" );
        AddComponent( panelLabelTONE );
        panelLabelTONE.SetWantsMouseNotifications( false );
        panelLabelTONE.SetPosition( 213, 197 );
        panelLabelTONE.SetSize( 20, 20 );
        panelLabelTONE.SetEditable( false, false );
        panelLabelTONE.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelTONE.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelTONE.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelTONE.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelTONE.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelTONE.SetBorderSize( 1 );
        panelLabelTONE.SetMultiLineEdit( false );
        panelLabelTONE.SetIsNumberEditor( false );
        panelLabelTONE.SetNumberEditorRange( 0, 100 );
        panelLabelTONE.SetNumberEditorInterval( 1 );
        panelLabelTONE.SetNumberEditorUsesMouseWheel( false );
        panelLabelTONE.SetHasCustomTextHoverColor( false );
        panelLabelTONE.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelTONE.SetFont( "Arial", 9, true, false );

        panelLabelON = new VoltageLabel( "panelLabelON", "Panel label: ON", this, "ON" );
        AddComponent( panelLabelON );
        panelLabelON.SetWantsMouseNotifications( false );
        panelLabelON.SetPosition( 236, 191 );
        panelLabelON.SetSize( 20, 10 );
        panelLabelON.SetEditable( false, false );
        panelLabelON.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelON.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelON.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelON.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelON.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelON.SetBorderSize( 1 );
        panelLabelON.SetMultiLineEdit( false );
        panelLabelON.SetIsNumberEditor( false );
        panelLabelON.SetNumberEditorRange( 0, 100 );
        panelLabelON.SetNumberEditorInterval( 1 );
        panelLabelON.SetNumberEditorUsesMouseWheel( false );
        panelLabelON.SetHasCustomTextHoverColor( false );
        panelLabelON.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelON.SetFont( "Arial", 9, true, false );

        panelLabelOFF = new VoltageLabel( "panelLabelOFF", "Panel label: OFF", this, "OFF" );
        AddComponent( panelLabelOFF );
        panelLabelOFF.SetWantsMouseNotifications( false );
        panelLabelOFF.SetPosition( 187, 192 );
        panelLabelOFF.SetSize( 20, 10 );
        panelLabelOFF.SetEditable( false, false );
        panelLabelOFF.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelOFF.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelOFF.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelOFF.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelOFF.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelOFF.SetBorderSize( 1 );
        panelLabelOFF.SetMultiLineEdit( false );
        panelLabelOFF.SetIsNumberEditor( false );
        panelLabelOFF.SetNumberEditorRange( 0, 100 );
        panelLabelOFF.SetNumberEditorInterval( 1 );
        panelLabelOFF.SetNumberEditorUsesMouseWheel( false );
        panelLabelOFF.SetHasCustomTextHoverColor( false );
        panelLabelOFF.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelOFF.SetFont( "Arial", 9, true, false );

        panelLabelVOLTAGE = new VoltageLabel( "panelLabelVOLTAGE", "Panel label: VOLTAGE", this, "VOLTAGE" );
        AddComponent( panelLabelVOLTAGE );
        panelLabelVOLTAGE.SetWantsMouseNotifications( false );
        panelLabelVOLTAGE.SetPosition( 0, 24 );
        panelLabelVOLTAGE.SetSize( 172, 13 );
        panelLabelVOLTAGE.SetEditable( false, false );
        panelLabelVOLTAGE.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelVOLTAGE.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelVOLTAGE.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelVOLTAGE.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelVOLTAGE.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelVOLTAGE.SetBorderSize( 1 );
        panelLabelVOLTAGE.SetMultiLineEdit( false );
        panelLabelVOLTAGE.SetIsNumberEditor( false );
        panelLabelVOLTAGE.SetNumberEditorRange( 0, 100 );
        panelLabelVOLTAGE.SetNumberEditorInterval( 1 );
        panelLabelVOLTAGE.SetNumberEditorUsesMouseWheel( false );
        panelLabelVOLTAGE.SetHasCustomTextHoverColor( false );
        panelLabelVOLTAGE.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelVOLTAGE.SetFont( "Courier New", 9, true, false );

        panelLabelNOISE = new VoltageLabel( "panelLabelNOISE", "Panel label: NOISE", this, "NOISE" );
        AddComponent( panelLabelNOISE );
        panelLabelNOISE.SetWantsMouseNotifications( false );
        panelLabelNOISE.SetPosition( 365, 24 );
        panelLabelNOISE.SetSize( 95, 13 );
        panelLabelNOISE.SetEditable( false, false );
        panelLabelNOISE.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelNOISE.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelNOISE.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelNOISE.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelNOISE.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelNOISE.SetBorderSize( 1 );
        panelLabelNOISE.SetMultiLineEdit( false );
        panelLabelNOISE.SetIsNumberEditor( false );
        panelLabelNOISE.SetNumberEditorRange( 0, 100 );
        panelLabelNOISE.SetNumberEditorInterval( 1 );
        panelLabelNOISE.SetNumberEditorUsesMouseWheel( false );
        panelLabelNOISE.SetHasCustomTextHoverColor( false );
        panelLabelNOISE.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelNOISE.SetFont( "Courier New", 9, true, false );

        panelLabelTUNING = new VoltageLabel( "panelLabelTUNING", "Panel label: TUNING", this, "TUNING" );
        AddComponent( panelLabelTUNING );
        panelLabelTUNING.SetWantsMouseNotifications( false );
        panelLabelTUNING.SetPosition( 173, 24 );
        panelLabelTUNING.SetSize( 102, 13 );
        panelLabelTUNING.SetEditable( false, false );
        panelLabelTUNING.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelTUNING.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelTUNING.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelTUNING.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelTUNING.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelTUNING.SetBorderSize( 1 );
        panelLabelTUNING.SetMultiLineEdit( false );
        panelLabelTUNING.SetIsNumberEditor( false );
        panelLabelTUNING.SetNumberEditorRange( 0, 100 );
        panelLabelTUNING.SetNumberEditorInterval( 1 );
        panelLabelTUNING.SetNumberEditorUsesMouseWheel( false );
        panelLabelTUNING.SetHasCustomTextHoverColor( false );
        panelLabelTUNING.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelTUNING.SetFont( "Courier New", 9, true, false );

        panelLabelVOLTMETER = new VoltageLabel( "panelLabelVOLTMETER", "Panel label: VOLT METER", this, "VOLT METER" );
        AddComponent( panelLabelVOLTMETER );
        panelLabelVOLTMETER.SetWantsMouseNotifications( false );
        panelLabelVOLTMETER.SetPosition( 295, 179 );
        panelLabelVOLTMETER.SetSize( 50, 13 );
        panelLabelVOLTMETER.SetEditable( false, false );
        panelLabelVOLTMETER.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelVOLTMETER.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelVOLTMETER.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelVOLTMETER.SetBkColor( new Color( 35, 35, 35, 255 ) );
        panelLabelVOLTMETER.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelVOLTMETER.SetBorderSize( 1 );
        panelLabelVOLTMETER.SetMultiLineEdit( false );
        panelLabelVOLTMETER.SetIsNumberEditor( false );
        panelLabelVOLTMETER.SetNumberEditorRange( 0, 100 );
        panelLabelVOLTMETER.SetNumberEditorInterval( 1 );
        panelLabelVOLTMETER.SetNumberEditorUsesMouseWheel( false );
        panelLabelVOLTMETER.SetHasCustomTextHoverColor( false );
        panelLabelVOLTMETER.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelVOLTMETER.SetFont( "Courier New", 9, true, false );

        panelLabelREFERENCETONES = new VoltageLabel( "panelLabelREFERENCETONES", "Panel label: REFERENCE TONES", this, "REFERENCE TONES" );
        AddComponent( panelLabelREFERENCETONES );
        panelLabelREFERENCETONES.SetWantsMouseNotifications( false );
        panelLabelREFERENCETONES.SetPosition( 185, 317 );
        panelLabelREFERENCETONES.SetSize( 80, 20 );
        panelLabelREFERENCETONES.SetEditable( false, false );
        panelLabelREFERENCETONES.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelREFERENCETONES.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelREFERENCETONES.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelREFERENCETONES.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelREFERENCETONES.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelREFERENCETONES.SetBorderSize( 1 );
        panelLabelREFERENCETONES.SetMultiLineEdit( false );
        panelLabelREFERENCETONES.SetIsNumberEditor( false );
        panelLabelREFERENCETONES.SetNumberEditorRange( 0, 100 );
        panelLabelREFERENCETONES.SetNumberEditorInterval( 1 );
        panelLabelREFERENCETONES.SetNumberEditorUsesMouseWheel( false );
        panelLabelREFERENCETONES.SetHasCustomTextHoverColor( false );
        panelLabelREFERENCETONES.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelREFERENCETONES.SetFont( "Arial", 9, true, false );

        lowPassInput = new VoltageAudioJack( "lowPassInput", "Low Pass Input", this, JackType.JackType_AudioInput );
        AddComponent( lowPassInput );
        lowPassInput.SetWantsMouseNotifications( false );
        lowPassInput.SetPosition( 384, 285 );
        lowPassInput.SetSize( 25, 25 );
        lowPassInput.SetSkin( "Dark Jack Straight" );

        highPassInput = new VoltageAudioJack( "highPassInput", "High Pass Input", this, JackType.JackType_AudioInput );
        AddComponent( highPassInput );
        highPassInput.SetWantsMouseNotifications( false );
        highPassInput.SetPosition( 384, 224 );
        highPassInput.SetSize( 25, 25 );
        highPassInput.SetSkin( "Dark Jack Straight" );

        addAInput = new VoltageAudioJack( "addAInput", "Add / Convert A", this, JackType.JackType_AudioInput );
        AddComponent( addAInput );
        addAInput.SetWantsMouseNotifications( false );
        addAInput.SetPosition( 36, 198 );
        addAInput.SetSize( 25, 25 );
        addAInput.SetSkin( "Dark Jack Straight" );

        addBInput = new VoltageAudioJack( "addBInput", "Add / Convert B", this, JackType.JackType_AudioInput );
        AddComponent( addBInput );
        addBInput.SetWantsMouseNotifications( false );
        addBInput.SetPosition( 36, 235 );
        addBInput.SetSize( 25, 25 );
        addBInput.SetSkin( "Dark Jack Straight" );

        panelLabelADDCONVB = new VoltageLabel( "panelLabelADDCONVB", "Panel label: ADD/CONV. B", this, "ADD/CONV. B" );
        AddComponent( panelLabelADDCONVB );
        panelLabelADDCONVB.SetWantsMouseNotifications( false );
        panelLabelADDCONVB.SetPosition( 18, 255 );
        panelLabelADDCONVB.SetSize( 60, 20 );
        panelLabelADDCONVB.SetEditable( false, false );
        panelLabelADDCONVB.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelADDCONVB.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelADDCONVB.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelADDCONVB.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelADDCONVB.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelADDCONVB.SetBorderSize( 1 );
        panelLabelADDCONVB.SetMultiLineEdit( false );
        panelLabelADDCONVB.SetIsNumberEditor( false );
        panelLabelADDCONVB.SetNumberEditorRange( 0, 100 );
        panelLabelADDCONVB.SetNumberEditorInterval( 1 );
        panelLabelADDCONVB.SetNumberEditorUsesMouseWheel( false );
        panelLabelADDCONVB.SetHasCustomTextHoverColor( false );
        panelLabelADDCONVB.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelADDCONVB.SetFont( "Arial", 9, true, false );

        sweepTriggerButton = new VoltageButton( "sweepTriggerButton", "Sweep Trigger", this );
        AddComponent( sweepTriggerButton );
        sweepTriggerButton.SetWantsMouseNotifications( false );
        sweepTriggerButton.SetPosition( 195, 253 );
        sweepTriggerButton.SetSize( 20, 20 );
        sweepTriggerButton.SetSkin( "2500 Square Big" );
        sweepTriggerButton.ShowOverlay( false );
        sweepTriggerButton.SetOverlayText( "" );
        sweepTriggerButton.SetAutoRepeat( false );

        panelLabelTRIGGER = new VoltageLabel( "panelLabelTRIGGER", "Panel label: TRIGGER", this, "TRIGGER" );
        AddComponent( panelLabelTRIGGER );
        panelLabelTRIGGER.SetWantsMouseNotifications( false );
        panelLabelTRIGGER.SetPosition( 185, 269 );
        panelLabelTRIGGER.SetSize( 40, 20 );
        panelLabelTRIGGER.SetEditable( false, false );
        panelLabelTRIGGER.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelTRIGGER.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelTRIGGER.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelTRIGGER.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelTRIGGER.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelTRIGGER.SetBorderSize( 1 );
        panelLabelTRIGGER.SetMultiLineEdit( false );
        panelLabelTRIGGER.SetIsNumberEditor( false );
        panelLabelTRIGGER.SetNumberEditorRange( 0, 100 );
        panelLabelTRIGGER.SetNumberEditorInterval( 1 );
        panelLabelTRIGGER.SetNumberEditorUsesMouseWheel( false );
        panelLabelTRIGGER.SetHasCustomTextHoverColor( false );
        panelLabelTRIGGER.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelTRIGGER.SetFont( "Arial", 9, true, false );

        sweepCurveSwitch = new VoltageSwitch( "sweepCurveSwitch", "Sweep Curve", this, 1 );
        AddComponent( sweepCurveSwitch );
        sweepCurveSwitch.SetWantsMouseNotifications( false );
        sweepCurveSwitch.SetPosition( 180, 256 );
        sweepCurveSwitch.SetSize( 8, 14 );
        sweepCurveSwitch.SetSkin( "2-State Slide Black" );

        meterASwitch = new VoltageSwitch( "meterASwitch", "Meter A DC or Peak to Peak", this, 1 );
        AddComponent( meterASwitch );
        meterASwitch.SetWantsMouseNotifications( false );
        meterASwitch.SetPosition( 352, 208 );
        meterASwitch.SetSize( 8, 14 );
        meterASwitch.SetSkin( "2-State Slide Black" );

        meterBSwitch = new VoltageSwitch( "meterBSwitch", "Meter B DC or Peak to Peak", this, 0 );
        AddComponent( meterBSwitch );
        meterBSwitch.SetWantsMouseNotifications( false );
        meterBSwitch.SetPosition( 279, 253 );
        meterBSwitch.SetSize( 8, 14 );
        meterBSwitch.SetSkin( "2-State Slide Black" );

        meterModeSwitch = new VoltageSwitch( "meterModeSwitch", "Volts or Pitch", this, 1 );
        AddComponent( meterModeSwitch );
        meterModeSwitch.SetWantsMouseNotifications( false );
        meterModeSwitch.SetPosition( 316, 291 );
        meterModeSwitch.SetSize( 8, 14 );
        meterModeSwitch.SetSkin( "2-State Slide Black" );

        frequencyDisplay = new VoltageDigitalCounter( "frequencyDisplay", "Measured Frequency", this, 5 );
        AddComponent( frequencyDisplay );
        frequencyDisplay.SetWantsMouseNotifications( false );
        frequencyDisplay.SetPosition( 290, 51 );
        frequencyDisplay.SetSize( 60, 30 );
        frequencyDisplay.SetSkin( "Gray" );
        frequencyDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        meterADisplay = new VoltageDigitalCounter( "meterADisplay", "Meter A", this, 5 );
        AddComponent( meterADisplay );
        meterADisplay.SetWantsMouseNotifications( false );
        meterADisplay.SetPosition( 290, 200 );
        meterADisplay.SetSize( 60, 30 );
        meterADisplay.SetSkin( "Gray" );
        meterADisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        meterBDisplay = new VoltageDigitalCounter( "meterBDisplay", "Meter B", this, 5 );
        AddComponent( meterBDisplay );
        meterBDisplay.SetWantsMouseNotifications( false );
        meterBDisplay.SetPosition( 290, 245 );
        meterBDisplay.SetSize( 60, 30 );
        meterBDisplay.SetSkin( "Gray" );
        meterBDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        meterAInput = new VoltageAudioJack( "meterAInput", "Meter A Input", this, JackType.JackType_AudioInput );
        AddComponent( meterAInput );
        meterAInput.SetWantsMouseNotifications( false );
        meterAInput.SetPosition( 290, 285 );
        meterAInput.SetSize( 25, 25 );
        meterAInput.SetSkin( "Dark Jack Straight" );

        meterBInput = new VoltageAudioJack( "meterBInput", "Meter B Input", this, JackType.JackType_AudioInput );
        AddComponent( meterBInput );
        meterBInput.SetWantsMouseNotifications( false );
        meterBInput.SetPosition( 325, 285 );
        meterBInput.SetSize( 25, 25 );
        meterBInput.SetSkin( "Dark Jack Straight" );

        panelLabelA3 = new VoltageLabel( "panelLabelA3", "Panel label: A", this, "A" );
        AddComponent( panelLabelA3 );
        panelLabelA3.SetWantsMouseNotifications( false );
        panelLabelA3.SetPosition( 278, 209 );
        panelLabelA3.SetSize( 10, 10 );
        panelLabelA3.SetEditable( false, false );
        panelLabelA3.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelA3.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelA3.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelA3.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelA3.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelA3.SetBorderSize( 1 );
        panelLabelA3.SetMultiLineEdit( false );
        panelLabelA3.SetIsNumberEditor( false );
        panelLabelA3.SetNumberEditorRange( 0, 100 );
        panelLabelA3.SetNumberEditorInterval( 1 );
        panelLabelA3.SetNumberEditorUsesMouseWheel( false );
        panelLabelA3.SetHasCustomTextHoverColor( false );
        panelLabelA3.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelA3.SetFont( "Arial", 11, true, false );

        panelLabelB = new VoltageLabel( "panelLabelB", "Panel label: B", this, "B" );
        AddComponent( panelLabelB );
        panelLabelB.SetWantsMouseNotifications( false );
        panelLabelB.SetPosition( 352, 254 );
        panelLabelB.SetSize( 10, 10 );
        panelLabelB.SetEditable( false, false );
        panelLabelB.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelB.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelB.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelB.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelB.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelB.SetBorderSize( 1 );
        panelLabelB.SetMultiLineEdit( false );
        panelLabelB.SetIsNumberEditor( false );
        panelLabelB.SetNumberEditorRange( 0, 100 );
        panelLabelB.SetNumberEditorInterval( 1 );
        panelLabelB.SetNumberEditorUsesMouseWheel( false );
        panelLabelB.SetHasCustomTextHoverColor( false );
        panelLabelB.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelB.SetFont( "Arial", 11, true, false );

        panelLabelA4 = new VoltageLabel( "panelLabelA4", "Panel label: A", this, "A" );
        AddComponent( panelLabelA4 );
        panelLabelA4.SetWantsMouseNotifications( false );
        panelLabelA4.SetPosition( 297, 310 );
        panelLabelA4.SetSize( 10, 10 );
        panelLabelA4.SetEditable( false, false );
        panelLabelA4.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelA4.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelA4.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelA4.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelA4.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelA4.SetBorderSize( 1 );
        panelLabelA4.SetMultiLineEdit( false );
        panelLabelA4.SetIsNumberEditor( false );
        panelLabelA4.SetNumberEditorRange( 0, 100 );
        panelLabelA4.SetNumberEditorInterval( 1 );
        panelLabelA4.SetNumberEditorUsesMouseWheel( false );
        panelLabelA4.SetHasCustomTextHoverColor( false );
        panelLabelA4.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelA4.SetFont( "Arial", 11, true, false );

        panelLabelB2 = new VoltageLabel( "panelLabelB2", "Panel label: B", this, "B" );
        AddComponent( panelLabelB2 );
        panelLabelB2.SetWantsMouseNotifications( false );
        panelLabelB2.SetPosition( 333, 310 );
        panelLabelB2.SetSize( 10, 10 );
        panelLabelB2.SetEditable( false, false );
        panelLabelB2.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelB2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelB2.SetColor( new Color( 232, 232, 232, 255 ) );
        panelLabelB2.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelB2.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelB2.SetBorderSize( 1 );
        panelLabelB2.SetMultiLineEdit( false );
        panelLabelB2.SetIsNumberEditor( false );
        panelLabelB2.SetNumberEditorRange( 0, 100 );
        panelLabelB2.SetNumberEditorInterval( 1 );
        panelLabelB2.SetNumberEditorUsesMouseWheel( false );
        panelLabelB2.SetHasCustomTextHoverColor( false );
        panelLabelB2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelB2.SetFont( "Arial", 11, true, false );
}

void InitializeControls3()
{

        panelLabelFILTER = new VoltageLabel( "panelLabelFILTER", "Panel label: FILTER", this, "FILTER" );
        AddComponent( panelLabelFILTER );
        panelLabelFILTER.SetWantsMouseNotifications( false );
        panelLabelFILTER.SetPosition( 393, 179 );
        panelLabelFILTER.SetSize( 40, 13 );
        panelLabelFILTER.SetEditable( false, false );
        panelLabelFILTER.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelFILTER.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelFILTER.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelFILTER.SetBkColor( new Color( 35, 35, 35, 255 ) );
        panelLabelFILTER.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelFILTER.SetBorderSize( 1 );
        panelLabelFILTER.SetMultiLineEdit( false );
        panelLabelFILTER.SetIsNumberEditor( false );
        panelLabelFILTER.SetNumberEditorRange( 0, 100 );
        panelLabelFILTER.SetNumberEditorInterval( 1 );
        panelLabelFILTER.SetNumberEditorUsesMouseWheel( false );
        panelLabelFILTER.SetHasCustomTextHoverColor( false );
        panelLabelFILTER.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelFILTER.SetFont( "Courier New", 9, true, false );

        panelLabelMULTIMETER = new VoltageLabel( "panelLabelMULTIMETER", "Panel label: MULTIMETER", this, "MULTIMETER" );
        AddComponent( panelLabelMULTIMETER );
        panelLabelMULTIMETER.SetWantsMouseNotifications( false );
        panelLabelMULTIMETER.SetPosition( 273, 24 );
        panelLabelMULTIMETER.SetSize( 92, 13 );
        panelLabelMULTIMETER.SetEditable( false, false );
        panelLabelMULTIMETER.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelMULTIMETER.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelMULTIMETER.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelMULTIMETER.SetBkColor( new Color( 65, 65, 65, 0 ) );
        panelLabelMULTIMETER.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelMULTIMETER.SetBorderSize( 1 );
        panelLabelMULTIMETER.SetMultiLineEdit( false );
        panelLabelMULTIMETER.SetIsNumberEditor( false );
        panelLabelMULTIMETER.SetNumberEditorRange( 0, 100 );
        panelLabelMULTIMETER.SetNumberEditorInterval( 1 );
        panelLabelMULTIMETER.SetNumberEditorUsesMouseWheel( false );
        panelLabelMULTIMETER.SetHasCustomTextHoverColor( false );
        panelLabelMULTIMETER.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelMULTIMETER.SetFont( "Courier New", 9, true, false );

        panelLabelAMPLITUDE = new VoltageLabel( "panelLabelAMPLITUDE", "Panel label: AMPLITUDE", this, "AMPLITUDE" );
        AddComponent( panelLabelAMPLITUDE );
        panelLabelAMPLITUDE.SetWantsMouseNotifications( false );
        panelLabelAMPLITUDE.SetPosition( 297, 107 );
        panelLabelAMPLITUDE.SetSize( 45, 13 );
        panelLabelAMPLITUDE.SetEditable( false, false );
        panelLabelAMPLITUDE.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelAMPLITUDE.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelAMPLITUDE.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelAMPLITUDE.SetBkColor( new Color( 35, 35, 35, 255 ) );
        panelLabelAMPLITUDE.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelAMPLITUDE.SetBorderSize( 1 );
        panelLabelAMPLITUDE.SetMultiLineEdit( false );
        panelLabelAMPLITUDE.SetIsNumberEditor( false );
        panelLabelAMPLITUDE.SetNumberEditorRange( 0, 100 );
        panelLabelAMPLITUDE.SetNumberEditorInterval( 1 );
        panelLabelAMPLITUDE.SetNumberEditorUsesMouseWheel( false );
        panelLabelAMPLITUDE.SetHasCustomTextHoverColor( false );
        panelLabelAMPLITUDE.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelAMPLITUDE.SetFont( "Courier New", 9, true, false );

        frequencyInput = new VoltageAudioJack( "frequencyInput", "Frequency Counter Input", this, JackType.JackType_AudioInput );
        AddComponent( frequencyInput );
        frequencyInput.SetWantsMouseNotifications( false );
        frequencyInput.SetPosition( 308, 84 );
        frequencyInput.SetSize( 25, 25 );
        frequencyInput.SetSkin( "Dark Jack Straight" );

        panelLabelCOUNTER = new VoltageLabel( "panelLabelCOUNTER", "Panel label: COUNTER", this, "COUNTER" );
        AddComponent( panelLabelCOUNTER );
        panelLabelCOUNTER.SetWantsMouseNotifications( false );
        panelLabelCOUNTER.SetPosition( 297, 37 );
        panelLabelCOUNTER.SetSize( 45, 13 );
        panelLabelCOUNTER.SetEditable( false, false );
        panelLabelCOUNTER.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        panelLabelCOUNTER.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        panelLabelCOUNTER.SetColor( new Color( 147, 147, 147, 255 ) );
        panelLabelCOUNTER.SetBkColor( new Color( 35, 35, 35, 255 ) );
        panelLabelCOUNTER.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        panelLabelCOUNTER.SetBorderSize( 1 );
        panelLabelCOUNTER.SetMultiLineEdit( false );
        panelLabelCOUNTER.SetIsNumberEditor( false );
        panelLabelCOUNTER.SetNumberEditorRange( 0, 100 );
        panelLabelCOUNTER.SetNumberEditorInterval( 1 );
        panelLabelCOUNTER.SetNumberEditorUsesMouseWheel( false );
        panelLabelCOUNTER.SetHasCustomTextHoverColor( false );
        panelLabelCOUNTER.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        panelLabelCOUNTER.SetFont( "Courier New", 9, true, false );

        amplitudeDisplay = new VoltageDigitalCounter( "amplitudeDisplay", "RMS Amplitude", this, 5 );
        AddComponent( amplitudeDisplay );
        amplitudeDisplay.SetWantsMouseNotifications( false );
        amplitudeDisplay.SetPosition( 290, 121 );
        amplitudeDisplay.SetSize( 60, 30 );
        amplitudeDisplay.SetSkin( "Gray" );
        amplitudeDisplay.SetJustificationFlags( VoltageDigitalCounter.Justification.Centered );

        amplitudeInput = new VoltageAudioJack( "amplitudeInput", "RMS Amplitude Input", this, JackType.JackType_AudioInput );
        AddComponent( amplitudeInput );
        amplitudeInput.SetWantsMouseNotifications( false );
        amplitudeInput.SetPosition( 308, 154 );
        amplitudeInput.SetSize( 25, 25 );
        amplitudeInput.SetSkin( "Dark Jack Straight" );
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
        updateControls(true);
        controlsReady = true;
        StartGuiUpdateTimer(50);
        refreshDisplays();
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
                if (component == sweepTriggerButton) {
                    boolean down = doubleValue >= 0.5;
                    if (down && !triggerDown && !bypassed) sweepRequested = true;
                    triggerDown = down;
                }
                break;
            case GUI_Update_Timer:
                refreshDisplays();
                break;
            case Reset:
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                resetRequested = true;
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
        boolean resume = bypassed || !controlsReady;
        if (resetRequested) {
            resetRequested = false;
            sweepActive = false; sweepRequested = false;
            sweepHz = SWEEP_START_HZ; sweepSample = 0; sweepPhase = 0.0;
            resetMeasurements();
            resume = true;
        }
        if (resume) {
            resetMeasurements();
            highPass.state = readInput(highPassInput);
            lowPass.state = readInput(lowPassInput);
        }
        bypassed = false;
        updateControls(resume);
        controlsReady = true;
        int standard = (int) clamp(standardsSwitch.GetValue(), 0.0, 2.0);
        double manual = Math.round(clamp(wholeVoltsKnob.GetValue(), 0.0, 10.0)) + clamp(fractionVoltsKnob.GetValue(), 0.0, 1.0);
        if (polaritySwitch.GetValue() < 0.5) manual = -manual;
        double sum = manual + readInput(addAInput) + readInput(addBInput);
        double converted = convertPitch(sum, standard);
        positiveDcOutput.SetValue(converted);
        negativeDcOutput.SetValue(-converted);
        shownVoltage = converted;
        fixedMinus24Output.SetValue(-24.0); fixedPlus24Output.SetValue(24.0);
        fixedMinus15Output.SetValue(-15.0); fixedPlus15Output.SetValue(15.0);
        fixedMinus12Output.SetValue(-12.0); fixedPlus12Output.SetValue(12.0);
        fixedMinus10Output.SetValue(-10.0); fixedPlus10Output.SetValue(10.0);
        fixedMinus5Output.SetValue(-5.0); fixedPlus5Output.SetValue(5.0);
        fixedMinus1Output.SetValue(-1.0); fixedPlus1Output.SetValue(1.0);
        fixedZeroOutput.SetValue(0.0); fixed3v3Output.SetValue(3.3); fixed9Output.SetValue(9.0);

        tuningHz += CONTROL_SMOOTH * (targetTuning - tuningHz);
        double toneTarget = toneSwitch.GetValue() >= 0.5 ? 1.0 : 0.0;
        toneGain += CONTROL_SMOOTH * (toneTarget - toneGain);
        if (Math.abs(toneGain - toneTarget) < 1.0e-12) toneGain = toneTarget;
        aReferenceOutput.SetValue(5.0 * toneGain * Math.sin(TWO_PI * phaseA));
        cReferenceOutput.SetValue(5.0 * toneGain * Math.sin(TWO_PI * phaseC));
        fSharpReferenceOutput.SetValue(5.0 * toneGain * Math.sin(TWO_PI * phaseFSharp));
        oneHzOutput.SetValue(5.0 * Math.sin(TWO_PI * phaseOne));
        hundredHzOutput.SetValue(5.0 * Math.sin(TWO_PI * phaseHundred));
        thousandHzOutput.SetValue(5.0 * Math.sin(TWO_PI * phaseThousand));
        phaseA = nextPhase(phaseA, tuningHz);
        phaseC = nextPhase(phaseC, tuningHz * Math.pow(2.0, -0.75));
        phaseFSharp = nextPhase(phaseFSharp, tuningHz * F_SHARP_RATIO);
        phaseOne = nextPhase(phaseOne, 1.0);
        phaseHundred = nextPhase(phaseHundred, 100.0);
        phaseThousand = nextPhase(phaseThousand, 1000.0);
        shownTuning = targetTuning;
        if (sweepRequested) { sweepRequested = false; beginSweep(); }
        sweepOutput.SetValue(processSweep());
        noise.process();
        pinkOutput.SetValue(noise.pink); blueOutput.SetValue(noise.blue);
        fastPulseOutput.SetValue((impulseSample & 511) == 0 ? 5.0 : 0.0);
        slowPulseOutput.SetValue(impulseSample == 0 ? 5.0 : 0.0);
        impulseSample = (impulseSample + 1) & 4095;

        double highInput = readInput(highPassInput);
        highPassOutput.SetValue(highInput - highPass.low(highInput));
        lowPassOutput.SetValue(lowPass.low(readInput(lowPassInput)));

        boolean connectedA = meterAInput.IsConnected(), connectedB = meterBInput.IsConnected();
        double valueA = connectedA ? finite(meterAInput.GetValue()) : 0.0;
        double valueB = connectedB ? finite(meterBInput.GetValue()) : 0.0;
        meterA.process(valueA, connectedA); meterB.process(valueB, connectedB);
        boolean amplitudeConnected = amplitudeInput.IsConnected();
        rmsMeter.process(amplitudeConnected ? finite(amplitudeInput.GetValue()) : 0.0, amplitudeConnected);
        boolean frequencyConnected = frequencyInput.IsConnected();
        frequencyMeter.process(frequencyConnected ? finite(frequencyInput.GetValue()) : 0.0, frequencyConnected);
        if (++publishSample >= 480) {
            publishSample = 0;
            boolean pitch = meterModeSwitch.GetValue() < 0.5;
            shownA = pitch ? (connectedA ? pitchHz(valueA, standard) : 0.0) : (meterASwitch.GetValue() >= 0.5 ? meterA.dc : meterA.vpp);
            shownB = pitch ? (connectedB ? pitchHz(valueB, standard) : 0.0) : (meterBSwitch.GetValue() >= 0.5 ? meterB.dc : meterB.vpp);
            shownRms = rmsMeter.rms; shownFrequency = frequencyMeter.frequency;
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
        bypassed = true;
        sweepRequested = false;
        silenceSources();
        double sum = readInput(addAInput) + readInput(addBInput);
        positiveDcOutput.SetValue(sum); negativeDcOutput.SetValue(-sum);
        highPassOutput.SetValue(readInput(highPassInput));
        lowPassOutput.SetValue(readInput(lowPassInput));
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
        if (component == wholeVoltsKnob) return String.format(java.util.Locale.ROOT, "Whole: %.0f V", wholeVoltsKnob.GetValue());
        if (component == fractionVoltsKnob) return String.format(java.util.Locale.ROOT, "Fraction: %.4f V", fractionVoltsKnob.GetValue());
        if (component == fineTuneKnob) return String.format(java.util.Locale.ROOT, "Fine: %+.3f cents; center dead zone, full continuous coverage", fineCents(fineTuneKnob.GetValue()));
        if (component == tuningSelector) return String.format(java.util.Locale.ROOT, "A reference: %.1f Hz before fine adjustment", TUNINGS[(int) clamp(Math.round(tuningSelector.GetValue()) - 1, 0, 6)]);
        if (component == highPassKnob || component == lowPassKnob) return String.format(java.util.Locale.ROOT, "Cutoff: %.3f Hz; clean one-pole, no resonance", cutoffHz(component.GetValue()));
        if (component == voltageDisplay || component == positiveDcOutput) return String.format(java.util.Locale.ROOT, "Master output: %+.6f V (after conversion)", shownVoltage);
        if (component == negativeDcOutput) return String.format(java.util.Locale.ROOT, "Inverted master: %+.6f V", -shownVoltage);
        if (component == tuningDisplay) return String.format(java.util.Locale.ROOT, "A4: %.6f Hz; C4: %.6f Hz", shownTuning, shownTuning * Math.pow(2.0, -0.75));
        if (component == meterADisplay || component == meterBDisplay) {
            boolean a = component == meterADisplay;
            String unit = meterModeSwitch.GetValue() < 0.5 ? "Hz decoded from pitch CV" : ((a ? meterASwitch : meterBSwitch).GetValue() >= 0.5 ? "V signed DC mean" : "V peak to peak");
            return String.format(java.util.Locale.ROOT, "%s: %.6f %s", a ? "A" : "B", a ? shownA : shownB, unit);
        }
        if (component == amplitudeDisplay) return String.format(java.util.Locale.ROOT, "%.6f V RMS including DC; 1-second window", shownRms);
        if (component == frequencyDisplay) return String.format(java.util.Locale.ROOT, "%.6f Hz; waveform crossing counter", shownFrequency);
        if (component == addAInput) return "V/oct input A; added before selected output conversion";
        if (component == addBInput) return "V/oct input B; added before selected output conversion";
        if (component == polaritySwitch) return "Polarity of manual whole + fractional volts only; left negative, right positive";
        if (component == standardsSwitch) return "Left Hz/V (C1=1 V); center 1 V/oct (C0=0 V); right 0.5 V/oct";
        if (component == toneSwitch) return "A/C reference tones: left off, right on; Courtesy remains active";
        if (component == sweepTriggerButton) return "Start or restart one 42-second sine sweep; lit while running";
        if (component == fSharpReferenceOutput) return String.format(java.util.Locale.ROOT, "F#4 reference: %.4f Hz, +/-5 V; follows tuning, FINE and TONE", shownTuning * F_SHARP_RATIO);
        if (component == thousandHzOutput) return "1000 Hz Courtesy sine, +/-5 V; independent of TONE";
        if (component == sweepCurveSwitch) return "Up exponential / down linear; latched at the next trigger";
        if (component == sweepOutput) return "42-second sine sweep, 0.01 to 23760 Hz; +/-5 V with endpoint fades";
        if (component == meterModeSwitch) return "Up VOLTS: use DC/Vpp switches. Down PITCH: decode CV to Hz with STANDARDS";
        if (component == meterASwitch) return "Meter A: up signed DC mean, down Vpp; ignored in PITCH mode";
        if (component == meterBSwitch) return "Meter B: up signed DC mean, down Vpp; ignored in PITCH mode";
        if (component == meterAInput) return "Meter A input; voltage measurement or decoded pitch CV";
        if (component == meterBInput) return "Meter B input; voltage measurement or decoded pitch CV";
        if (component == frequencyInput) return "Audio/pulse frequency input; >=1 mV recurring crossings, not pitch CV";
        if (component == amplitudeInput) return "RMS input; includes DC, 1-second observation window";
        if (component == highPassInput) return "Independent HPF input; unpatched = silence";
        if (component == highPassOutput) return "Clean one-pole HPF output; bypass = direct input";
        if (component == lowPassInput) return "Independent LPF input; unpatched = silence";
        if (component == lowPassOutput) return "Clean one-pole LPF output; bypass = direct input";
        if (component == pinkOutput) return "Clean fixed-level pink noise approximation";
        if (component == blueOutput) return "Clean fixed-level blue-like noise, rising midband spectrum";
        if (component == fastPulseOutput) return "One +5 V sample every 512 samples (93.75 Hz at 48 kHz)";
        if (component == slowPulseOutput) return "One +5 V sample every 4096 samples (11.71875 Hz at 48 kHz)";
        if (component == aReferenceOutput) return "A4 sine, +/-5 V, selected tuning and fine adjustment";
        if (component == cReferenceOutput) return "C4 sine, +/-5 V, relative to selected A4 tuning";
        if (component == oneHzOutput) return "1 Hz Courtesy sine, +/-5 V, independent of TONE";
        if (component == hundredHzOutput) return "100 Hz Courtesy sine, +/-5 V, independent of TONE";
        if (component == fixedMinus15Output) return "Fixed -15 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedMinus12Output) return "Fixed -12 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedMinus10Output) return "Fixed -10 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedMinus5Output) return "Fixed -5 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedMinus1Output) return "Fixed -1 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedZeroOutput) return "Fixed +0 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedPlus1Output) return "Fixed +1 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedPlus5Output) return "Fixed +5 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedPlus10Output) return "Fixed +10 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedPlus12Output) return "Fixed +12 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedPlus15Output) return "Fixed +15 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixed3v3Output) return "Fixed +3.3 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixed9Output) return "Fixed +9 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedMinus24Output) return "Fixed -24 V; unaffected by STANDARDS; silent during bypass";
        if (component == fixedPlus24Output) return "Fixed +24 V; unaffected by STANDARDS; silent during bypass";
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
        if (!Double.isFinite(newValue)) return;
        if (component == fineTuneKnob) {
            double cents = clamp(newValue, -100.0, 100.0);
            double position = cents == 0.0 ? 0.0 : Math.copySign(0.025 + 0.975 * Math.abs(cents) / 100.0, cents);
            super.EditComponentValue(component, position, Double.toString(position));
            return;
        }
        if (component == highPassKnob || component == lowPassKnob) {
            double position = Math.log(clamp(newValue, 1.0, 20000.0)) / Math.log(20000.0);
            super.EditComponentValue(component, position, Double.toString(position));
            return;
        }
        if (component == tuningSelector) {
            int best = 0;
            for (int i = 1; i < TUNINGS.length; i++) if (Math.abs(TUNINGS[i] - newValue) < Math.abs(TUNINGS[best] - newValue)) best = i;
            super.EditComponentValue(component, best + 1.0, Integer.toString(best + 1));
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
    private VoltageAudioJack amplitudeInput;
    private VoltageDigitalCounter amplitudeDisplay;
    private VoltageLabel panelLabelCOUNTER;
    private VoltageAudioJack frequencyInput;
    private VoltageLabel panelLabelAMPLITUDE;
    private VoltageLabel panelLabelMULTIMETER;
    private VoltageLabel panelLabelFILTER;
    private VoltageLabel panelLabelB2;
    private VoltageLabel panelLabelA4;
    private VoltageLabel panelLabelB;
    private VoltageLabel panelLabelA3;
    private VoltageAudioJack meterBInput;
    private VoltageAudioJack meterAInput;
    private VoltageDigitalCounter meterBDisplay;
    private VoltageDigitalCounter meterADisplay;
    private VoltageDigitalCounter frequencyDisplay;
    private VoltageSwitch meterModeSwitch;
    private VoltageSwitch meterBSwitch;
    private VoltageSwitch meterASwitch;
    private VoltageSwitch sweepCurveSwitch;
    private VoltageLabel panelLabelTRIGGER;
    private VoltageButton sweepTriggerButton;
    private VoltageLabel panelLabelADDCONVB;
    private VoltageAudioJack addBInput;
    private VoltageAudioJack addAInput;
    private VoltageAudioJack highPassInput;
    private VoltageAudioJack lowPassInput;
    private VoltageLabel panelLabelREFERENCETONES;
    private VoltageLabel panelLabelVOLTMETER;
    private VoltageLabel panelLabelTUNING;
    private VoltageLabel panelLabelNOISE;
    private VoltageLabel panelLabelVOLTAGE;
    private VoltageLabel panelLabelOFF;
    private VoltageLabel panelLabelON;
    private VoltageLabel panelLabelTONE;
    private VoltageLabel panelLabelEmpty4;
    private VoltageLabel panelLabelEmpty3;
    private VoltageLabel panelLabelFINE;
    private VoltageLabel panelLabel03;
    private VoltageLabel panelLabel13;
    private VoltageLabel panelLabelMinus1;
    private VoltageLabel panelLabel442;
    private VoltageLabel panelLabel4225;
    private VoltageLabel panelLabelFSharp;
    private VoltageLabel panelLabelC;
    private VoltageLabel panelLabelA2;
    private VoltageLabel panelLabelA;
    private VoltageLabel panelLabel435;
    private VoltageLabel panelLabel432;
    private VoltageLabel panelLabel440;
    private VoltageLabel panelLabel444;
    private VoltageLabel panelLabelPITCH;
    private VoltageLabel panelLabelVpp2;
    private VoltageLabel panelLabelLIN;
    private VoltageLabel panelLabelVpp;
    private VoltageLabel panelLabelVOLTS;
    private VoltageLabel panelLabelDC2;
    private VoltageLabel panelLabelDC;
    private VoltageLabel panelLabelEXP;
    private VoltageLabel panelLabel392;
    private VoltageKnob lowPassKnob;
    private VoltageKnob highPassKnob;
    private VoltageLabel panelLabelHPF;
    private VoltageLabel panelLabelLPF;
    private VoltageLabel panelLabelO;
    private VoltageLabel panelLabelI;
    private VoltageAudioJack lowPassOutput;
    private VoltageDigitalCounter voltageDisplay;
    private VoltageDigitalCounter tuningDisplay;
    private VoltageLabel panelLabelMinusDC;
    private VoltageLabel panelLabelPlusDC;
    private VoltageAudioJack fSharpReferenceOutput;
    private VoltageAudioJack cReferenceOutput;
    private VoltageAudioJack sweepOutput;
    private VoltageAudioJack thousandHzOutput;
    private VoltageAudioJack aReferenceOutput;
    private VoltageAudioJack hundredHzOutput;
    private VoltageAudioJack oneHzOutput;
    private VoltageAudioJack positiveDcOutput;
    private VoltageAudioJack negativeDcOutput;
    private VoltageLabel panelLabelADDCONVA;
    private VoltageLabel panelLabelPlus;
    private VoltageLabel panelLabel05VO;
    private VoltageLabel panelLabelMinus;
    private VoltageLabel panelLabelHzV;
    private VoltageSwitch polaritySwitch;
    private VoltageSwitch standardsSwitch;
    private VoltageSwitch toneSwitch;
    private VoltageLabel panelLabelEmpty2;
    private VoltageLabel panelLabelEmpty;
    private VoltageLabel panelLabelCV;
    private VoltageLabel panelLabel05;
    private VoltageLabel panelLabel12;
    private VoltageLabel panelLabel02;
    private VoltageLabel panelLabel9;
    private VoltageKnob fractionVoltsKnob;
    private VoltageKnob fineTuneKnob;
    private VoltageLabel panelLabel1;
    private VoltageLabel panelLabelV;
    private VoltageLabel panelLabel6;
    private VoltageLabel panelLabel4;
    private VoltageLabel panelLabel8;
    private VoltageLabel panelLabel7;
    private VoltageLabel panelLabel2;
    private VoltageLabel panelLabel3;
    private VoltageLabel panelLabel5;
    private VoltageLabel panelLabel10;
    private VoltageLabel panelLabel0;
    private VoltageLabel panelLabelSLOWPULSES;
    private VoltageAudioJack slowPulseOutput;
    private VoltageLabel panelLabel24V;
    private VoltageAudioJack fixedPlus24Output;
    private VoltageKnob tuningSelector;
    private VoltageKnob wholeVoltsKnob;
    private VoltageLabel panelLabelFASTPULSES;
    private VoltageAudioJack fastPulseOutput;
    private VoltageLabel panelLabelInsectLaboratories;
    private VoltageLabel panelLabelBLUE;
    private VoltageAudioJack blueOutput;
    private VoltageLabel panelLabelPINK;
    private VoltageAudioJack pinkOutput;
    private VoltageLabel panelLabelMinus24V;
    private VoltageLabel panelLabel9V;
    private VoltageLabel panelLabel33V;
    private VoltageAudioJack fixedMinus24Output;
    private VoltageAudioJack fixed9Output;
    private VoltageLabel panelLabel0V;
    private VoltageLabel panelLabel15V;
    private VoltageLabel panelLabel12V;
    private VoltageLabel panelLabel10V;
    private VoltageLabel panelLabel5V;
    private VoltageLabel panelLabelO2;
    private VoltageLabel panelLabelSWEEP;
    private VoltageLabel panelLabel100Hz;
    private VoltageLabel panelLabel1Hz;
    private VoltageLabel panelLabel1KHz;
    private VoltageLabel panelLabel1V;
    private VoltageLabel panelLabelI2;
    private VoltageLabel panelLabelMinus1V;
    private VoltageLabel panelLabelMinus5V;
    private VoltageLabel panelLabelMinus10V;
    private VoltageLabel panelLabelMinus12V;
    private VoltageLabel panelLabelMinus15V;
    private VoltageLabel panelLabelSnMinus16U;
    private VoltageAudioJack fixed3v3Output;
    private VoltageAudioJack fixedPlus15Output;
    private VoltageAudioJack fixedPlus12Output;
    private VoltageAudioJack fixedPlus10Output;
    private VoltageAudioJack fixedPlus5Output;
    private VoltageAudioJack highPassOutput;
    private VoltageAudioJack fixedPlus1Output;
    private VoltageAudioJack fixedZeroOutput;
    private VoltageAudioJack fixedMinus1Output;
    private VoltageAudioJack fixedMinus5Output;
    private VoltageAudioJack fixedMinus10Output;
    private VoltageAudioJack fixedMinus12Output;
    private VoltageAudioJack fixedMinus15Output;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double TWO_PI = 2.0 * Math.PI;
    private static final double F_SHARP_RATIO = Math.pow(2.0, -0.25);
    private static final double C0_HZ = 16.351597831287414;
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE));
    private static final double[] TUNINGS = {392.0, 422.5, 432.0, 440.0, 435.0, 442.0, 444.0};
    private static final int SWEEP_SAMPLES = 42 * 48000;
    private static final double SWEEP_START_HZ = 0.01;
    private static final double SWEEP_END_HZ = SAMPLE_RATE * 0.495;
    private static final double SWEEP_RATIO = Math.pow(SWEEP_END_HZ / SWEEP_START_HZ, 1.0 / (SWEEP_SAMPLES - 1));
    private static final double SWEEP_STEP = (SWEEP_END_HZ - SWEEP_START_HZ) / (SWEEP_SAMPLES - 1);
    private final WindowMeter meterA = new WindowMeter();
    private final WindowMeter meterB = new WindowMeter();
    private final WindowMeter rmsMeter = new WindowMeter();
    private final FrequencyMeter frequencyMeter = new FrequencyMeter();
    private final OnePole highPass = new OnePole();
    private final OnePole lowPass = new OnePole();
    private final NoiseSource noise = new NoiseSource();
    private volatile boolean bypassed;
    private volatile boolean sweepRequested;
    private volatile boolean resetRequested;
    private volatile double shownVoltage, shownTuning = 440.0, shownA, shownB, shownRms, shownFrequency;
    private volatile boolean sweepActive;
    private boolean controlsReady, sweepExponential, triggerDown;
    private double cachedTuning = Double.NaN, cachedFine = Double.NaN;
    private double cachedHigh = Double.NaN, cachedLow = Double.NaN;
    private double targetTuning = 440.0, tuningHz = 440.0, toneGain;
    private double phaseA, phaseC, phaseFSharp, phaseOne, phaseHundred, phaseThousand, sweepPhase, sweepHz = SWEEP_START_HZ;
    private int impulseSample, sweepSample, publishSample;

    private static double finite(double value) {
        return Double.isFinite(value) ? value : 0.0;
    }

    private static double clamp(double value, double low, double high) {
        return Math.max(low, Math.min(high, finite(value)));
    }

    private static double readInput(VoltageAudioJack input) {
        return input.IsConnected() ? finite(input.GetValue()) : 0.0;
    }

    private static double nextPhase(double phase, double frequency) {
        phase += frequency / SAMPLE_RATE;
        return phase - Math.floor(phase);
    }

    private static double fineCents(double position) {
        double magnitude = Math.abs(clamp(position, -1.0, 1.0));
        return magnitude <= 0.025 ? 0.0 : Math.copySign(100.0 * (magnitude - 0.025) / 0.975, position);
    }

    private static double cutoffHz(double position) {
        return Math.pow(20000.0, clamp(position, 0.0, 1.0));
    }

    // VM pitch convention: C0 = 0 V. User's Hz/V convention: C1 = 1 V.
    private static double convertPitch(double voltage, int standard) {
        if (standard == 0) return Math.pow(2.0, clamp(voltage - 1.0, -80.0, 80.0));
        return standard == 2 ? voltage * 0.5 : voltage;
    }

    private static double pitchHz(double voltage, int standard) {
        if (standard == 0) return Math.max(0.0, voltage) * (2.0 * C0_HZ);
        return C0_HZ * Math.pow(2.0, clamp(voltage * (standard == 2 ? 2.0 : 1.0), -80.0, 80.0));
    }

    private void updateControls(boolean snap) {
        double tuning = clamp(tuningSelector.GetValue(), 1.0, 7.0);
        double fine = clamp(fineTuneKnob.GetValue(), -1.0, 1.0);
        if (tuning != cachedTuning || fine != cachedFine) {
            cachedTuning = tuning;
            cachedFine = fine;
            targetTuning = TUNINGS[(int) Math.round(tuning) - 1] * Math.pow(2.0, fineCents(fine) / 1200.0);
        }
        double high = clamp(highPassKnob.GetValue(), 0.0, 1.0);
        double low = clamp(lowPassKnob.GetValue(), 0.0, 1.0);
        if (high != cachedHigh) {
            cachedHigh = high;
            highPass.target = Math.tan(Math.PI * cutoffHz(high) / SAMPLE_RATE);
            highPass.target /= 1.0 + highPass.target;
        }
        if (low != cachedLow) {
            cachedLow = low;
            lowPass.target = Math.tan(Math.PI * cutoffHz(low) / SAMPLE_RATE);
            lowPass.target /= 1.0 + lowPass.target;
        }
        if (snap) {
            tuningHz = targetTuning;
            highPass.coefficient = highPass.target;
            lowPass.coefficient = lowPass.target;
            toneGain = toneSwitch.GetValue() >= 0.5 ? 1.0 : 0.0;
        }
    }

    private void beginSweep() {
        sweepSample = 0;
        sweepPhase = 0.0;
        sweepHz = SWEEP_START_HZ;
        sweepExponential = sweepCurveSwitch.GetValue() >= 0.5;
        sweepActive = true;
    }

    private double processSweep() {
        if (!sweepActive) return 0.0;
        // Endpoint fades do not alter the 42-second frequency trajectory.
        double envelope = Math.min(1.0, Math.min(sweepSample / 240.0, (SWEEP_SAMPLES - 1 - sweepSample) / 240.0));
        double output = 5.0 * envelope * Math.sin(TWO_PI * sweepPhase);
        sweepPhase = nextPhase(sweepPhase, sweepHz);
        sweepSample++;
        if (sweepSample >= SWEEP_SAMPLES) {
            sweepActive = false;
            sweepHz = SWEEP_START_HZ;
            sweepPhase = 0.0;
        } else {
            sweepHz = sweepExponential ? sweepHz * SWEEP_RATIO : SWEEP_START_HZ + SWEEP_STEP * sweepSample;
        }
        return output;
    }

    private void resetMeasurements() {
        meterA.reset();
        meterB.reset();
        rmsMeter.reset();
        frequencyMeter.reset();
        shownA = shownB = shownRms = shownFrequency = 0.0;
    }

    private void silenceSources() {
        fixedMinus24Output.SetValue(0.0); fixedPlus24Output.SetValue(0.0);
        fixedMinus15Output.SetValue(0.0); fixedPlus15Output.SetValue(0.0);
        fixedMinus12Output.SetValue(0.0); fixedPlus12Output.SetValue(0.0);
        fixedMinus10Output.SetValue(0.0); fixedPlus10Output.SetValue(0.0);
        fixedMinus5Output.SetValue(0.0); fixedPlus5Output.SetValue(0.0);
        fixedMinus1Output.SetValue(0.0); fixedPlus1Output.SetValue(0.0);
        fixedZeroOutput.SetValue(0.0); fixed3v3Output.SetValue(0.0); fixed9Output.SetValue(0.0);
        aReferenceOutput.SetValue(0.0); cReferenceOutput.SetValue(0.0); fSharpReferenceOutput.SetValue(0.0);
        oneHzOutput.SetValue(0.0); hundredHzOutput.SetValue(0.0); thousandHzOutput.SetValue(0.0); sweepOutput.SetValue(0.0);
        pinkOutput.SetValue(0.0); blueOutput.SetValue(0.0);
        fastPulseOutput.SetValue(0.0); slowPulseOutput.SetValue(0.0);
    }

    private void refreshDisplays() {
        // Change only the visual state; never feed sweep status back into the momentary trigger.
        sweepTriggerButton.UpdateGUIValue(!bypassed && sweepActive ? 1.0 : 0.0);
        // GUI work stays off the audio path. Native counters handle the decimal point.
        voltageDisplay.SetValue(bypassed ? 0.0 : shownVoltage);
        tuningDisplay.SetValue(shownTuning);
        frequencyDisplay.SetValue(bypassed ? 0.0 : shownFrequency);
        amplitudeDisplay.SetValue(bypassed ? 0.0 : shownRms);
        meterADisplay.SetValue(bypassed ? 0.0 : shownA);
        meterBDisplay.SetValue(bypassed ? 0.0 : shownB);
    }

    private static final class OnePole {
        double target, coefficient, state;
        double low(double input) {
            coefficient += CONTROL_SMOOTH * (target - coefficient);
            double delta = coefficient * (input - state);
            double output = state + delta;
            state = output + delta;
            return output;
        }
    }

    // Twenty 50 ms blocks: fixed memory and constant sample work, one-second observation window.
    private static final class WindowMeter {
        final double[] sums = new double[20], squares = new double[20], lows = new double[20], highs = new double[20];
        double sum, square, low = Double.POSITIVE_INFINITY, high = Double.NEGATIVE_INFINITY;
        double dc, rms, vpp;
        int sample, index, filled;
        boolean connected;
        void reset() {
            java.util.Arrays.fill(sums, 0.0); java.util.Arrays.fill(squares, 0.0);
            java.util.Arrays.fill(lows, 0.0); java.util.Arrays.fill(highs, 0.0);
            sum = square = dc = rms = vpp = 0.0;
            low = Double.POSITIVE_INFINITY; high = Double.NEGATIVE_INFINITY;
            sample = index = filled = 0;
        }
        void process(double input, boolean patched) {
            if (patched != connected) { reset(); connected = patched; }
            if (!patched) return;
            // Meter protection only; signal-through paths are not clipped.
            input = clamp(input, -1.0e9, 1.0e9);
            sum += input; square += input * input;
            low = Math.min(low, input); high = Math.max(high, input);
            if (++sample != 2400) return;
            sums[index] = sum; squares[index] = square; lows[index] = low; highs[index] = high;
            index = (index + 1) % 20; filled = Math.min(20, filled + 1);
            double total = 0.0, energy = 0.0, minimum = Double.POSITIVE_INFINITY, maximum = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < filled; i++) {
                total += sums[i]; energy += squares[i];
                minimum = Math.min(minimum, lows[i]); maximum = Math.max(maximum, highs[i]);
            }
            dc = total / (filled * 2400.0);
            rms = Math.sqrt(energy / (filled * 2400.0)); vpp = maximum - minimum;
            sum = square = 0.0; sample = 0;
            low = Double.POSITIVE_INFINITY; high = Double.NEGATIVE_INFINITY;
        }
    }

    private static final class FrequencyMeter {
        double previous, lastCross = Double.NaN, firstCross, frequency;
        long sample;
        int periods;
        boolean armed, connected;
        void reset() { previous = frequency = 0.0; lastCross = Double.NaN; sample = 0; periods = 0; armed = false; }
        void process(double value, boolean patched) {
            if (patched != connected) { reset(); connected = patched; }
            if (!patched) return;
            // Rising 1 mV crossing, rearmed below 0 V; works with bipolar waves and positive pulse trains.
            if (value <= 0.0) armed = true;
            if (armed && previous < 0.001 && value >= 0.001) {
                double crossing = sample - 1.0 + (0.001 - previous) / (value - previous);
                if (Double.isFinite(lastCross)) {
                    double period = crossing - lastCross;
                    if (period >= 2.0) {
                        if (periods == 0) firstCross = lastCross;
                        periods++;
                        if (crossing - firstCross >= 4800.0 || frequency == 0.0) {
                            frequency = SAMPLE_RATE * periods / (crossing - firstCross);
                            periods = 0;
                        }
                    }
                }
                lastCross = crossing; armed = false;
            }
            double timeout = frequency > 0.0 ? Math.max(96000.0, 2.5 * SAMPLE_RATE / frequency) : 120.0 * SAMPLE_RATE;
            if (Double.isFinite(lastCross) && sample - lastCross > timeout) {
                frequency = 0.0; lastCross = Double.NaN; periods = 0;
            }
            previous = value; sample++;
        }
    }

    private static final class NoiseSource {
        long seed = System.nanoTime() ^ 0x39c45b7a1dL;
        double b0, b1, b2, b3, b4, b5, b6, previousPink, pink, blue;
        double white() {
            seed ^= seed << 13; seed ^= seed >>> 7; seed ^= seed << 17;
            return (seed >> 11) * 0x1.0p-52;
        }
        void process() {
            double white = white();
            // Paul Kellett's refined pinking filter; see development README for attribution and limits.
            b0 = 0.99886 * b0 + white * 0.0555179;
            b1 = 0.99332 * b1 + white * 0.0750759;
            b2 = 0.96900 * b2 + white * 0.1538520;
            b3 = 0.86650 * b3 + white * 0.3104856;
            b4 = 0.55000 * b4 + white * 0.5329522;
            b5 = -0.7616 * b5 - white * 0.0168980;
            double raw = b0 + b1 + b2 + b3 + b4 + b5 + b6 + white * 0.5362;
            b6 = white * 0.115926;
            pink = raw * 0.5;
            blue = (raw - previousPink) * 1.1;
            previousPink = raw;
        }
    }
    //[/user-code-and-variables]
}
