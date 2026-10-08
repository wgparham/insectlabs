package com.mycompany.newmodule;


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


public class MyModule extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public MyModule( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "My Module", ModuleType.ModuleType_Utility, 3.2 );

        InitializeControls();


        canBeBypassed = false;
        SetSkin( "b6f302c8540a44ddb860b1a6fe94f89d" );
    }

void InitializeControls()
{

        image2 = new VoltageImage( "image2", "image2", this, false );
        AddComponent( image2 );
        image2.SetWantsMouseNotifications( false );
        image2.SetPosition( 87, 213 );
        image2.SetSize( 93, 69 );
        image2.SetCurrentImage( "rLogo.png" );

        inputJack8 = new VoltageAudioJack( "inputJack8", "inputJack8", this, JackType.JackType_AudioOutput );
        AddComponent( inputJack8 );
        inputJack8.SetWantsMouseNotifications( false );
        inputJack8.SetPosition( 168, 220 );
        inputJack8.SetSize( 37, 37 );
        inputJack8.SetSkin( "Rotated Half" );

        inputJack4 = new VoltageAudioJack( "inputJack4", "inputJack4", this, JackType.JackType_AudioInput );
        AddComponent( inputJack4 );
        inputJack4.SetWantsMouseNotifications( false );
        inputJack4.SetPosition( 62, 272 );
        inputJack4.SetSize( 37, 37 );
        inputJack4.SetSkin( "Dark Jack Straight" );

        smallBlackInput = new VoltageAudioJack( "smallBlackInput", "Small Black Input", this, JackType.JackType_AudioInput );
        AddComponent( smallBlackInput );
        smallBlackInput.SetWantsMouseNotifications( false );
        smallBlackInput.SetPosition( 3, 252 );
        smallBlackInput.SetSize( 25, 25 );
        smallBlackInput.SetSkin( "Dark Jack Straight" );

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Label", this, "r." );
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

        descriptionLabel = new VoltageLabel( "descriptionLabel", "MODULE DESCRIPTION", this, "minimal mixer" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 0 );
        descriptionLabel.SetSize( 224, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        descriptionLabel.SetBkColor( new Color( 51, 51, 51, 255 ) );
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

        scale1A30DeepToneGenModLabel2 = new VoltageLabel( "scale1A30DeepToneGenModLabel2", "1A-30 deep tone gen/mod Label", this, "rm405" );
        AddComponent( scale1A30DeepToneGenModLabel2 );
        scale1A30DeepToneGenModLabel2.SetWantsMouseNotifications( false );
        scale1A30DeepToneGenModLabel2.SetPosition( 187, 335 );
        scale1A30DeepToneGenModLabel2.SetSize( 40, 13 );
        scale1A30DeepToneGenModLabel2.SetEditable( false, false );
        scale1A30DeepToneGenModLabel2.SetJustificationFlags( VoltageLabel.Justification.Right );
        scale1A30DeepToneGenModLabel2.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        scale1A30DeepToneGenModLabel2.SetColor( new Color( 232, 232, 232, 255 ) );
        scale1A30DeepToneGenModLabel2.SetBkColor( new Color( 85, 85, 85, 0 ) );
        scale1A30DeepToneGenModLabel2.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        scale1A30DeepToneGenModLabel2.SetBorderSize( 0 );
        scale1A30DeepToneGenModLabel2.SetMultiLineEdit( false );
        scale1A30DeepToneGenModLabel2.SetIsNumberEditor( false );
        scale1A30DeepToneGenModLabel2.SetNumberEditorRange( 0, 100 );
        scale1A30DeepToneGenModLabel2.SetNumberEditorInterval( 1 );
        scale1A30DeepToneGenModLabel2.SetNumberEditorUsesMouseWheel( false );
        scale1A30DeepToneGenModLabel2.SetHasCustomTextHoverColor( false );
        scale1A30DeepToneGenModLabel2.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        scale1A30DeepToneGenModLabel2.SetFont( "Courier New", 13, true, false );

        knob1 = new VoltageKnob( "knob1", "knob1", this, 0.0, 1.0, 0.0 );
        AddComponent( knob1 );
        knob1.SetWantsMouseNotifications( false );
        knob1.SetPosition( 22, 42 );
        knob1.SetSize( 35, 35 );
        knob1.SetSkin( "Cosmo Medium" );
        knob1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob1.SetKnobParams( 215, 145 );
        knob1.DisplayValueInPercent( true );
        knob1.SetKnobAdjustsRing( true );

        knob2 = new VoltageKnob( "knob2", "knob2", this, 0.0, 1.0, 0.0 );
        AddComponent( knob2 );
        knob2.SetWantsMouseNotifications( false );
        knob2.SetPosition( 22, 82 );
        knob2.SetSize( 35, 35 );
        knob2.SetSkin( "Cosmo Medium" );
        knob2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob2.SetKnobParams( 215, 145 );
        knob2.DisplayValueInPercent( true );
        knob2.SetKnobAdjustsRing( true );

        knob3 = new VoltageKnob( "knob3", "knob3", this, 0.0, 1.0, 0.0 );
        AddComponent( knob3 );
        knob3.SetWantsMouseNotifications( false );
        knob3.SetPosition( 22, 127 );
        knob3.SetSize( 35, 35 );
        knob3.SetSkin( "Cosmo Medium" );
        knob3.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob3.SetKnobParams( 215, 145 );
        knob3.DisplayValueInPercent( true );
        knob3.SetKnobAdjustsRing( true );

        knob4 = new VoltageKnob( "knob4", "knob4", this, 0.0, 1.0, 0.0 );
        AddComponent( knob4 );
        knob4.SetWantsMouseNotifications( false );
        knob4.SetPosition( 22, 167 );
        knob4.SetSize( 35, 35 );
        knob4.SetSkin( "Cosmo Medium" );
        knob4.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob4.SetKnobParams( 215, 145 );
        knob4.DisplayValueInPercent( true );
        knob4.SetKnobAdjustsRing( true );

        knob5 = new VoltageKnob( "knob5", "knob5", this, -1.0, 1.0, 0.0 );
        AddComponent( knob5 );
        knob5.SetWantsMouseNotifications( false );
        knob5.SetPosition( 162, 42 );
        knob5.SetSize( 35, 35 );
        knob5.SetSkin( "Cosmo Medium" );
        knob5.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knob5.SetKnobParams( 215, 145 );
        knob5.DisplayValueInPercent( false );
        knob5.SetKnobAdjustsRing( true );

        knob9 = new VoltageKnob( "knob9", "knob9", this, 0.0, 1.0, 0.0 );
        AddComponent( knob9 );
        knob9.SetWantsMouseNotifications( false );
        knob9.SetPosition( 61, 42 );
        knob9.SetSize( 35, 35 );
        knob9.SetSkin( "Cosmo Medium" );
        knob9.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob9.SetKnobParams( 215, 145 );
        knob9.DisplayValueInPercent( true );
        knob9.SetKnobAdjustsRing( true );

        knob6 = new VoltageKnob( "knob6", "knob6", this, -1.0, 1.0, 0.0 );
        AddComponent( knob6 );
        knob6.SetWantsMouseNotifications( false );
        knob6.SetPosition( 162, 82 );
        knob6.SetSize( 35, 35 );
        knob6.SetSkin( "Cosmo Medium" );
        knob6.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knob6.SetKnobParams( 215, 145 );
        knob6.DisplayValueInPercent( false );
        knob6.SetKnobAdjustsRing( true );

        knob10 = new VoltageKnob( "knob10", "knob10", this, 0.0, 1.0, 0.0 );
        AddComponent( knob10 );
        knob10.SetWantsMouseNotifications( false );
        knob10.SetPosition( 62, 82 );
        knob10.SetSize( 35, 35 );
        knob10.SetSkin( "Cosmo Medium" );
        knob10.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob10.SetKnobParams( 215, 145 );
        knob10.DisplayValueInPercent( true );
        knob10.SetKnobAdjustsRing( true );

        knob7 = new VoltageKnob( "knob7", "knob7", this, -1.0, 1.0, 0.0 );
        AddComponent( knob7 );
        knob7.SetWantsMouseNotifications( false );
        knob7.SetPosition( 162, 127 );
        knob7.SetSize( 35, 35 );
        knob7.SetSkin( "Cosmo Medium" );
        knob7.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knob7.SetKnobParams( 215, 145 );
        knob7.DisplayValueInPercent( false );
        knob7.SetKnobAdjustsRing( true );

        knob11 = new VoltageKnob( "knob11", "knob11", this, 0.0, 1.0, 0.0 );
        AddComponent( knob11 );
        knob11.SetWantsMouseNotifications( false );
        knob11.SetPosition( 62, 127 );
        knob11.SetSize( 35, 35 );
        knob11.SetSkin( "Cosmo Medium" );
        knob11.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob11.SetKnobParams( 215, 145 );
        knob11.DisplayValueInPercent( true );
        knob11.SetKnobAdjustsRing( true );

        knob8 = new VoltageKnob( "knob8", "eq", this, -1.0, 1.0, 0.0 );
        AddComponent( knob8 );
        knob8.SetWantsMouseNotifications( false );
        knob8.SetPosition( 162, 167 );
        knob8.SetSize( 35, 35 );
        knob8.SetSkin( "Cosmo Medium" );
        knob8.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knob8.SetKnobParams( 215, 145 );
        knob8.DisplayValueInPercent( false );
        knob8.SetKnobAdjustsRing( true );

        knob12 = new VoltageKnob( "knob12", "knob12", this, 0.0, 1.0, 0.0 );
        AddComponent( knob12 );
        knob12.SetWantsMouseNotifications( false );
        knob12.SetPosition( 62, 167 );
        knob12.SetSize( 35, 35 );
        knob12.SetSkin( "Cosmo Medium" );
        knob12.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob12.SetKnobParams( 215, 145 );
        knob12.DisplayValueInPercent( true );
        knob12.SetKnobAdjustsRing( true );

        inputJack1 = new VoltageAudioJack( "inputJack1", "inputJack1", this, JackType.JackType_AudioInput );
        AddComponent( inputJack1 );
        inputJack1.SetWantsMouseNotifications( false );
        inputJack1.SetPosition( 22, 222 );
        inputJack1.SetSize( 37, 37 );
        inputJack1.SetSkin( "Dark Jack Straight" );

        inputJack2 = new VoltageAudioJack( "inputJack2", "inputJack2", this, JackType.JackType_AudioInput );
        AddComponent( inputJack2 );
        inputJack2.SetWantsMouseNotifications( false );
        inputJack2.SetPosition( 22, 272 );
        inputJack2.SetSize( 37, 37 );
        inputJack2.SetSkin( "Dark Jack Straight" );

        inputJack3 = new VoltageAudioJack( "inputJack3", "inputJack3", this, JackType.JackType_AudioInput );
        AddComponent( inputJack3 );
        inputJack3.SetWantsMouseNotifications( false );
        inputJack3.SetPosition( 62, 222 );
        inputJack3.SetSize( 37, 37 );
        inputJack3.SetSkin( "Dark Jack Straight" );

        switch1 = new VoltageSwitch( "switch1", "switch1", this, 1 );
        AddComponent( switch1 );
        switch1.SetWantsMouseNotifications( false );
        switch1.SetPosition( 103, 53 );
        switch1.SetSize( 51, 15 );
        switch1.SetSkin( "4-State Slide Horiz" );

        textLabel6 = new VoltageLabel( "textLabel6", "textLabel6", this, "I" );
        AddComponent( textLabel6 );
        textLabel6.SetWantsMouseNotifications( false );
        textLabel6.SetPosition( 30, 255 );
        textLabel6.SetSize( 21, 20 );
        textLabel6.SetEditable( false, false );
        textLabel6.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel6.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel6.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel6.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel6.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel6.SetBorderSize( 1 );
        textLabel6.SetMultiLineEdit( false );
        textLabel6.SetIsNumberEditor( false );
        textLabel6.SetNumberEditorRange( 0, 100 );
        textLabel6.SetNumberEditorInterval( 1 );
        textLabel6.SetNumberEditorUsesMouseWheel( false );
        textLabel6.SetHasCustomTextHoverColor( false );
        textLabel6.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel6.SetFont( "Times New Roman", 13, true, false );

        textLabel7 = new VoltageLabel( "textLabel7", "textLabel7", this, "II" );
        AddComponent( textLabel7 );
        textLabel7.SetWantsMouseNotifications( false );
        textLabel7.SetPosition( 70, 255 );
        textLabel7.SetSize( 21, 20 );
        textLabel7.SetEditable( false, false );
        textLabel7.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel7.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel7.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel7.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel7.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel7.SetBorderSize( 1 );
        textLabel7.SetMultiLineEdit( false );
        textLabel7.SetIsNumberEditor( false );
        textLabel7.SetNumberEditorRange( 0, 100 );
        textLabel7.SetNumberEditorInterval( 1 );
        textLabel7.SetNumberEditorUsesMouseWheel( false );
        textLabel7.SetHasCustomTextHoverColor( false );
        textLabel7.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel7.SetFont( "Times New Roman", 13, true, false );

        textLabel8 = new VoltageLabel( "textLabel8", "textLabel8", this, "III" );
        AddComponent( textLabel8 );
        textLabel8.SetWantsMouseNotifications( false );
        textLabel8.SetPosition( 29, 305 );
        textLabel8.SetSize( 21, 20 );
        textLabel8.SetEditable( false, false );
        textLabel8.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel8.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel8.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel8.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel8.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel8.SetBorderSize( 1 );
        textLabel8.SetMultiLineEdit( false );
        textLabel8.SetIsNumberEditor( false );
        textLabel8.SetNumberEditorRange( 0, 100 );
        textLabel8.SetNumberEditorInterval( 1 );
        textLabel8.SetNumberEditorUsesMouseWheel( false );
        textLabel8.SetHasCustomTextHoverColor( false );
        textLabel8.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel8.SetFont( "Times New Roman", 13, true, false );

        textLabel9 = new VoltageLabel( "textLabel9", "textLabel9", this, "IIII" );
        AddComponent( textLabel9 );
        textLabel9.SetWantsMouseNotifications( false );
        textLabel9.SetPosition( 69, 305 );
        textLabel9.SetSize( 21, 20 );
        textLabel9.SetEditable( false, false );
        textLabel9.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel9.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel9.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel9.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel9.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel9.SetBorderSize( 1 );
        textLabel9.SetMultiLineEdit( false );
        textLabel9.SetIsNumberEditor( false );
        textLabel9.SetNumberEditorRange( 0, 100 );
        textLabel9.SetNumberEditorInterval( 1 );
        textLabel9.SetNumberEditorUsesMouseWheel( false );
        textLabel9.SetHasCustomTextHoverColor( false );
        textLabel9.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel9.SetFont( "Times New Roman", 13, true, false );

        textLabel10 = new VoltageLabel( "textLabel10", "textLabel10", this, "I" );
        AddComponent( textLabel10 );
        textLabel10.SetWantsMouseNotifications( false );
        textLabel10.SetPosition( 0, 50 );
        textLabel10.SetSize( 21, 20 );
        textLabel10.SetEditable( false, false );
        textLabel10.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel10.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel10.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel10.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel10.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel10.SetBorderSize( 1 );
        textLabel10.SetMultiLineEdit( false );
        textLabel10.SetIsNumberEditor( false );
        textLabel10.SetNumberEditorRange( 0, 100 );
        textLabel10.SetNumberEditorInterval( 1 );
        textLabel10.SetNumberEditorUsesMouseWheel( false );
        textLabel10.SetHasCustomTextHoverColor( false );
        textLabel10.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel10.SetFont( "Times New Roman", 13, true, false );

        textLabel11 = new VoltageLabel( "textLabel11", "textLabel11", this, "II" );
        AddComponent( textLabel11 );
        textLabel11.SetWantsMouseNotifications( false );
        textLabel11.SetPosition( 0, 90 );
        textLabel11.SetSize( 21, 20 );
        textLabel11.SetEditable( false, false );
        textLabel11.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel11.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel11.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel11.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel11.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel11.SetBorderSize( 1 );
        textLabel11.SetMultiLineEdit( false );
        textLabel11.SetIsNumberEditor( false );
        textLabel11.SetNumberEditorRange( 0, 100 );
        textLabel11.SetNumberEditorInterval( 1 );
        textLabel11.SetNumberEditorUsesMouseWheel( false );
        textLabel11.SetHasCustomTextHoverColor( false );
        textLabel11.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel11.SetFont( "Times New Roman", 13, true, false );

        textLabel12 = new VoltageLabel( "textLabel12", "textLabel12", this, "III" );
        AddComponent( textLabel12 );
        textLabel12.SetWantsMouseNotifications( false );
        textLabel12.SetPosition( 0, 135 );
        textLabel12.SetSize( 21, 20 );
        textLabel12.SetEditable( false, false );
        textLabel12.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel12.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel12.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel12.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel12.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel12.SetBorderSize( 1 );
        textLabel12.SetMultiLineEdit( false );
        textLabel12.SetIsNumberEditor( false );
        textLabel12.SetNumberEditorRange( 0, 100 );
        textLabel12.SetNumberEditorInterval( 1 );
        textLabel12.SetNumberEditorUsesMouseWheel( false );
        textLabel12.SetHasCustomTextHoverColor( false );
        textLabel12.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel12.SetFont( "Times New Roman", 13, true, false );

        textLabel13 = new VoltageLabel( "textLabel13", "textLabel13", this, "IIII" );
        AddComponent( textLabel13 );
        textLabel13.SetWantsMouseNotifications( false );
        textLabel13.SetPosition( 0, 175 );
        textLabel13.SetSize( 21, 20 );
        textLabel13.SetEditable( false, false );
        textLabel13.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel13.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel13.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel13.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel13.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel13.SetBorderSize( 1 );
        textLabel13.SetMultiLineEdit( false );
        textLabel13.SetIsNumberEditor( false );
        textLabel13.SetNumberEditorRange( 0, 100 );
        textLabel13.SetNumberEditorInterval( 1 );
        textLabel13.SetNumberEditorUsesMouseWheel( false );
        textLabel13.SetHasCustomTextHoverColor( false );
        textLabel13.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel13.SetFont( "Times New Roman", 13, true, false );

        textLabel14 = new VoltageLabel( "textLabel14", "textLabel14", this, "0 • L • M • H" );
        AddComponent( textLabel14 );
        textLabel14.SetWantsMouseNotifications( false );
        textLabel14.SetPosition( 102, 63 );
        textLabel14.SetSize( 53, 16 );
        textLabel14.SetEditable( false, false );
        textLabel14.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel14.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel14.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel14.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel14.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel14.SetBorderSize( 1 );
        textLabel14.SetMultiLineEdit( false );
        textLabel14.SetIsNumberEditor( false );
        textLabel14.SetNumberEditorRange( 0, 100 );
        textLabel14.SetNumberEditorInterval( 1 );
        textLabel14.SetNumberEditorUsesMouseWheel( false );
        textLabel14.SetHasCustomTextHoverColor( false );
        textLabel14.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel14.SetFont( "Courier New", 6, true, false );

        inputJack6 = new VoltageAudioJack( "inputJack6", "inputJack6", this, JackType.JackType_AudioInput );
        AddComponent( inputJack6 );
        inputJack6.SetWantsMouseNotifications( false );
        inputJack6.SetPosition( 131, 272 );
        inputJack6.SetSize( 37, 37 );
        inputJack6.SetSkin( "Jack Round" );

        bigMiniSilverJack = new VoltageAudioJack( "bigMiniSilverJack", "Big Mini Silver Jack", this, JackType.JackType_AudioOutput );
        AddComponent( bigMiniSilverJack );
        bigMiniSilverJack.SetWantsMouseNotifications( false );
        bigMiniSilverJack.SetPosition( 103, 277 );
        bigMiniSilverJack.SetSize( 25, 25 );
        bigMiniSilverJack.SetSkin( "Mini Jack 25px" );

        inputJack9 = new VoltageAudioJack( "inputJack9", "inputJack9", this, JackType.JackType_AudioOutput );
        AddComponent( inputJack9 );
        inputJack9.SetWantsMouseNotifications( false );
        inputJack9.SetPosition( 168, 272 );
        inputJack9.SetSize( 37, 37 );
        inputJack9.SetSkin( "Rotated Half" );

        switch2 = new VoltageSwitch( "switch2", "switch2", this, 1 );
        AddComponent( switch2 );
        switch2.SetWantsMouseNotifications( false );
        switch2.SetPosition( 103, 93 );
        switch2.SetSize( 51, 15 );
        switch2.SetSkin( "4-State Slide Horiz" );

        textLabel15 = new VoltageLabel( "textLabel15", "textLabel15", this, "0 • L • M • H" );
        AddComponent( textLabel15 );
        textLabel15.SetWantsMouseNotifications( false );
        textLabel15.SetPosition( 102, 103 );
        textLabel15.SetSize( 53, 16 );
        textLabel15.SetEditable( false, false );
        textLabel15.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel15.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel15.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel15.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel15.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel15.SetBorderSize( 1 );
        textLabel15.SetMultiLineEdit( false );
        textLabel15.SetIsNumberEditor( false );
        textLabel15.SetNumberEditorRange( 0, 100 );
        textLabel15.SetNumberEditorInterval( 1 );
        textLabel15.SetNumberEditorUsesMouseWheel( false );
        textLabel15.SetHasCustomTextHoverColor( false );
        textLabel15.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel15.SetFont( "Courier New", 6, true, false );

        switch3 = new VoltageSwitch( "switch3", "switch3", this, 1 );
        AddComponent( switch3 );
        switch3.SetWantsMouseNotifications( false );
        switch3.SetPosition( 103, 138 );
        switch3.SetSize( 51, 15 );
        switch3.SetSkin( "4-State Slide Horiz" );

        textLabel16 = new VoltageLabel( "textLabel16", "textLabel16", this, "0 • L • M • H" );
        AddComponent( textLabel16 );
        textLabel16.SetWantsMouseNotifications( false );
        textLabel16.SetPosition( 102, 148 );
        textLabel16.SetSize( 53, 16 );
        textLabel16.SetEditable( false, false );
        textLabel16.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel16.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel16.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel16.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel16.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel16.SetBorderSize( 1 );
        textLabel16.SetMultiLineEdit( false );
        textLabel16.SetIsNumberEditor( false );
        textLabel16.SetNumberEditorRange( 0, 100 );
        textLabel16.SetNumberEditorInterval( 1 );
        textLabel16.SetNumberEditorUsesMouseWheel( false );
        textLabel16.SetHasCustomTextHoverColor( false );
        textLabel16.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel16.SetFont( "Courier New", 6, true, false );

        switch4 = new VoltageSwitch( "switch4", "switch4", this, 1 );
        AddComponent( switch4 );
        switch4.SetWantsMouseNotifications( false );
        switch4.SetPosition( 103, 178 );
        switch4.SetSize( 51, 15 );
        switch4.SetSkin( "4-State Slide Horiz" );

        textLabel17 = new VoltageLabel( "textLabel17", "textLabel17", this, "0 • L • M • H" );
        AddComponent( textLabel17 );
        textLabel17.SetWantsMouseNotifications( false );
        textLabel17.SetPosition( 102, 188 );
        textLabel17.SetSize( 53, 16 );
        textLabel17.SetEditable( false, false );
        textLabel17.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel17.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel17.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel17.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel17.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel17.SetBorderSize( 1 );
        textLabel17.SetMultiLineEdit( false );
        textLabel17.SetIsNumberEditor( false );
        textLabel17.SetNumberEditorRange( 0, 100 );
        textLabel17.SetNumberEditorInterval( 1 );
        textLabel17.SetNumberEditorUsesMouseWheel( false );
        textLabel17.SetHasCustomTextHoverColor( false );
        textLabel17.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel17.SetFont( "Courier New", 6, true, false );

        knob13 = new VoltageKnob( "knob13", "knob13", this, 0.0, 1.0, 0.0 );
        AddComponent( knob13 );
        knob13.SetWantsMouseNotifications( false );
        knob13.SetPosition( 203, 50 );
        knob13.SetSize( 21, 21 );
        knob13.SetSkin( "JP-106 Gray" );
        knob13.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob13.SetKnobParams( 215, 145 );
        knob13.DisplayValueInPercent( true );
        knob13.SetKnobAdjustsRing( true );

        knob14 = new VoltageKnob( "knob14", "knob14", this, 0.0, 1.0, 0.0 );
        AddComponent( knob14 );
        knob14.SetWantsMouseNotifications( false );
        knob14.SetPosition( 203, 90 );
        knob14.SetSize( 21, 21 );
        knob14.SetSkin( "JP-106 Gray" );
        knob14.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob14.SetKnobParams( 215, 145 );
        knob14.DisplayValueInPercent( false );
        knob14.SetKnobAdjustsRing( true );

        knob15 = new VoltageKnob( "knob15", "knob15", this, 0.0, 1.0, 0.0 );
        AddComponent( knob15 );
        knob15.SetWantsMouseNotifications( false );
        knob15.SetPosition( 203, 135 );
        knob15.SetSize( 21, 21 );
        knob15.SetSkin( "JP-106 Gray" );
        knob15.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob15.SetKnobParams( 215, 145 );
        knob15.DisplayValueInPercent( false );
        knob15.SetKnobAdjustsRing( true );

        knob16 = new VoltageKnob( "knob16", "knob16", this, 0.0, 1.0, 0.0 );
        AddComponent( knob16 );
        knob16.SetWantsMouseNotifications( false );
        knob16.SetPosition( 203, 175 );
        knob16.SetSize( 21, 21 );
        knob16.SetSkin( "JP-106 Gray" );
        knob16.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knob16.SetKnobParams( 215, 145 );
        knob16.DisplayValueInPercent( false );
        knob16.SetKnobAdjustsRing( true );

        textLabel18 = new VoltageLabel( "textLabel18", "textLabel18", this, "3" );
        AddComponent( textLabel18 );
        textLabel18.SetWantsMouseNotifications( false );
        textLabel18.SetPosition( 177, 255 );
        textLabel18.SetSize( 21, 20 );
        textLabel18.SetEditable( false, false );
        textLabel18.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel18.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel18.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel18.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel18.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel18.SetBorderSize( 1 );
        textLabel18.SetMultiLineEdit( false );
        textLabel18.SetIsNumberEditor( false );
        textLabel18.SetNumberEditorRange( 0, 100 );
        textLabel18.SetNumberEditorInterval( 1 );
        textLabel18.SetNumberEditorUsesMouseWheel( false );
        textLabel18.SetHasCustomTextHoverColor( false );
        textLabel18.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel18.SetFont( "Times New Roman", 13, true, false );

        textLabel19 = new VoltageLabel( "textLabel19", "textLabel19", this, "7" );
        AddComponent( textLabel19 );
        textLabel19.SetWantsMouseNotifications( false );
        textLabel19.SetPosition( 180, 305 );
        textLabel19.SetSize( 21, 20 );
        textLabel19.SetEditable( false, false );
        textLabel19.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel19.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel19.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel19.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel19.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel19.SetBorderSize( 1 );
        textLabel19.SetMultiLineEdit( false );
        textLabel19.SetIsNumberEditor( false );
        textLabel19.SetNumberEditorRange( 0, 100 );
        textLabel19.SetNumberEditorInterval( 1 );
        textLabel19.SetNumberEditorUsesMouseWheel( false );
        textLabel19.SetHasCustomTextHoverColor( false );
        textLabel19.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel19.SetFont( "Times New Roman", 13, true, false );

        textLabel20 = new VoltageLabel( "textLabel20", "textLabel20", this, "S" );
        AddComponent( textLabel20 );
        textLabel20.SetWantsMouseNotifications( false );
        textLabel20.SetPosition( 95, 305 );
        textLabel20.SetSize( 41, 20 );
        textLabel20.SetEditable( false, false );
        textLabel20.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel20.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel20.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel20.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel20.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel20.SetBorderSize( 1 );
        textLabel20.SetMultiLineEdit( false );
        textLabel20.SetIsNumberEditor( false );
        textLabel20.SetNumberEditorRange( 0, 100 );
        textLabel20.SetNumberEditorInterval( 1 );
        textLabel20.SetNumberEditorUsesMouseWheel( false );
        textLabel20.SetHasCustomTextHoverColor( false );
        textLabel20.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel20.SetFont( "Times New Roman", 13, true, false );

        textLabel21 = new VoltageLabel( "textLabel21", "textLabel21", this, "R" );
        AddComponent( textLabel21 );
        textLabel21.SetWantsMouseNotifications( false );
        textLabel21.SetPosition( 130, 305 );
        textLabel21.SetSize( 41, 20 );
        textLabel21.SetEditable( false, false );
        textLabel21.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel21.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel21.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel21.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel21.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel21.SetBorderSize( 1 );
        textLabel21.SetMultiLineEdit( false );
        textLabel21.SetIsNumberEditor( false );
        textLabel21.SetNumberEditorRange( 0, 100 );
        textLabel21.SetNumberEditorInterval( 1 );
        textLabel21.SetNumberEditorUsesMouseWheel( false );
        textLabel21.SetHasCustomTextHoverColor( false );
        textLabel21.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel21.SetFont( "Times New Roman", 13, true, false );

        textLabel22 = new VoltageLabel( "textLabel22", "textLabel22", this, "LINK" );
        AddComponent( textLabel22 );
        textLabel22.SetWantsMouseNotifications( false );
        textLabel22.SetPosition( 5, 269 );
        textLabel22.SetSize( 21, 20 );
        textLabel22.SetEditable( false, false );
        textLabel22.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel22.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel22.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel22.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel22.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel22.SetBorderSize( 1 );
        textLabel22.SetMultiLineEdit( false );
        textLabel22.SetIsNumberEditor( false );
        textLabel22.SetNumberEditorRange( 0, 100 );
        textLabel22.SetNumberEditorInterval( 1 );
        textLabel22.SetNumberEditorUsesMouseWheel( false );
        textLabel22.SetHasCustomTextHoverColor( false );
        textLabel22.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel22.SetFont( "Times New Roman", 7, true, false );

        LED1 = new VoltageLED( "LED1", "LED1", this );
        AddComponent( LED1 );
        LED1.SetWantsMouseNotifications( false );
        LED1.SetPosition( 57, 40 );
        LED1.SetSize( 7, 7 );
        LED1.SetSkin( "2500 Lamp White" );

        LED2 = new VoltageLED( "LED2", "LED2", this );
        AddComponent( LED2 );
        LED2.SetWantsMouseNotifications( false );
        LED2.SetPosition( 57, 80 );
        LED2.SetSize( 7, 7 );
        LED2.SetSkin( "2500 Lamp White" );

        LED3 = new VoltageLED( "LED3", "LED3", this );
        AddComponent( LED3 );
        LED3.SetWantsMouseNotifications( false );
        LED3.SetPosition( 57, 125 );
        LED3.SetSize( 7, 7 );
        LED3.SetSkin( "2500 Lamp White" );

        LED4 = new VoltageLED( "LED4", "LED4", this );
        AddComponent( LED4 );
        LED4.SetWantsMouseNotifications( false );
        LED4.SetPosition( 57, 165 );
        LED4.SetSize( 7, 7 );
        LED4.SetSkin( "2500 Lamp White" );

        textLabel23 = new VoltageLabel( "textLabel23", "textLabel23", this, "GAIN" );
        AddComponent( textLabel23 );
        textLabel23.SetWantsMouseNotifications( false );
        textLabel23.SetPosition( 19, 23 );
        textLabel23.SetSize( 41, 20 );
        textLabel23.SetEditable( false, false );
        textLabel23.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel23.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel23.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel23.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel23.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel23.SetBorderSize( 1 );
        textLabel23.SetMultiLineEdit( false );
        textLabel23.SetIsNumberEditor( false );
        textLabel23.SetNumberEditorRange( 0, 100 );
        textLabel23.SetNumberEditorInterval( 1 );
        textLabel23.SetNumberEditorUsesMouseWheel( false );
        textLabel23.SetHasCustomTextHoverColor( false );
        textLabel23.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel23.SetFont( "Times New Roman", 9, true, false );

        textLabel24 = new VoltageLabel( "textLabel24", "textLabel24", this, "VOLUME" );
        AddComponent( textLabel24 );
        textLabel24.SetWantsMouseNotifications( false );
        textLabel24.SetPosition( 57, 23 );
        textLabel24.SetSize( 41, 20 );
        textLabel24.SetEditable( false, false );
        textLabel24.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel24.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel24.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel24.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel24.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel24.SetBorderSize( 1 );
        textLabel24.SetMultiLineEdit( false );
        textLabel24.SetIsNumberEditor( false );
        textLabel24.SetNumberEditorRange( 0, 100 );
        textLabel24.SetNumberEditorInterval( 1 );
        textLabel24.SetNumberEditorUsesMouseWheel( false );
        textLabel24.SetHasCustomTextHoverColor( false );
        textLabel24.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel24.SetFont( "Times New Roman", 9, true, false );

        textLabel25 = new VoltageLabel( "textLabel25", "textLabel25", this, "EQ" );
        AddComponent( textLabel25 );
        textLabel25.SetWantsMouseNotifications( false );
        textLabel25.SetPosition( 158, 23 );
        textLabel25.SetSize( 41, 20 );
        textLabel25.SetEditable( false, false );
        textLabel25.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel25.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel25.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel25.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel25.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel25.SetBorderSize( 1 );
        textLabel25.SetMultiLineEdit( false );
        textLabel25.SetIsNumberEditor( false );
        textLabel25.SetNumberEditorRange( 0, 100 );
        textLabel25.SetNumberEditorInterval( 1 );
        textLabel25.SetNumberEditorUsesMouseWheel( false );
        textLabel25.SetHasCustomTextHoverColor( false );
        textLabel25.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel25.SetFont( "Times New Roman", 9, true, false );

        textLabel26 = new VoltageLabel( "textLabel26", "textLabel26", this, "SEND" );
        AddComponent( textLabel26 );
        textLabel26.SetWantsMouseNotifications( false );
        textLabel26.SetPosition( 198, 23 );
        textLabel26.SetSize( 31, 20 );
        textLabel26.SetEditable( false, false );
        textLabel26.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel26.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel26.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel26.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel26.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel26.SetBorderSize( 1 );
        textLabel26.SetMultiLineEdit( false );
        textLabel26.SetIsNumberEditor( false );
        textLabel26.SetNumberEditorRange( 0, 100 );
        textLabel26.SetNumberEditorInterval( 1 );
        textLabel26.SetNumberEditorUsesMouseWheel( false );
        textLabel26.SetHasCustomTextHoverColor( false );
        textLabel26.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel26.SetFont( "Times New Roman", 9, true, false );

        inputJack10 = new VoltageAudioJack( "inputJack10", "inputJack10", this, JackType.JackType_AudioOutput );
        AddComponent( inputJack10 );
        inputJack10.SetWantsMouseNotifications( false );
        inputJack10.SetPosition( 192, 245 );
        inputJack10.SetSize( 37, 37 );
        inputJack10.SetSkin( "Rotated Half" );

        textLabel27 = new VoltageLabel( "textLabel27", "textLabel27", this, "10" );
        AddComponent( textLabel27 );
        textLabel27.SetWantsMouseNotifications( false );
        textLabel27.SetPosition( 205, 280 );
        textLabel27.SetSize( 21, 20 );
        textLabel27.SetEditable( false, false );
        textLabel27.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        textLabel27.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        textLabel27.SetColor( new Color( 232, 232, 232, 255 ) );
        textLabel27.SetBkColor( new Color( 65, 65, 65, 0 ) );
        textLabel27.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        textLabel27.SetBorderSize( 1 );
        textLabel27.SetMultiLineEdit( false );
        textLabel27.SetIsNumberEditor( false );
        textLabel27.SetNumberEditorRange( 0, 100 );
        textLabel27.SetNumberEditorInterval( 1 );
        textLabel27.SetNumberEditorUsesMouseWheel( false );
        textLabel27.SetHasCustomTextHoverColor( false );
        textLabel27.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        textLabel27.SetFont( "Times New Roman", 13, true, false );
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
            }
            break;
        
            case Variation_Loading_Start:    // sent when a variation is about to load
            {
            }
            break;
        
            case Variation_Loading_Finish:   // sent when a variation has just finished loading
            {
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



        //[/user-ProcessSample]
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
    private VoltageLabel textLabel27;
    private VoltageAudioJack inputJack10;
    private VoltageLabel textLabel26;
    private VoltageLabel textLabel25;
    private VoltageLabel textLabel24;
    private VoltageLabel textLabel23;
    private VoltageLED LED4;
    private VoltageLED LED3;
    private VoltageLED LED2;
    private VoltageLED LED1;
    private VoltageLabel textLabel22;
    private VoltageLabel textLabel21;
    private VoltageLabel textLabel20;
    private VoltageLabel textLabel19;
    private VoltageLabel textLabel18;
    private VoltageKnob knob16;
    private VoltageKnob knob15;
    private VoltageKnob knob14;
    private VoltageKnob knob13;
    private VoltageLabel textLabel17;
    private VoltageSwitch switch4;
    private VoltageLabel textLabel16;
    private VoltageSwitch switch3;
    private VoltageLabel textLabel15;
    private VoltageSwitch switch2;
    private VoltageAudioJack inputJack9;
    private VoltageAudioJack bigMiniSilverJack;
    private VoltageAudioJack inputJack6;
    private VoltageLabel textLabel14;
    private VoltageLabel textLabel13;
    private VoltageLabel textLabel12;
    private VoltageLabel textLabel11;
    private VoltageLabel textLabel10;
    private VoltageLabel textLabel9;
    private VoltageLabel textLabel8;
    private VoltageLabel textLabel7;
    private VoltageLabel textLabel6;
    private VoltageSwitch switch1;
    private VoltageAudioJack inputJack3;
    private VoltageAudioJack inputJack2;
    private VoltageAudioJack inputJack1;
    private VoltageKnob knob12;
    private VoltageKnob knob8;
    private VoltageKnob knob11;
    private VoltageKnob knob7;
    private VoltageKnob knob10;
    private VoltageKnob knob6;
    private VoltageKnob knob9;
    private VoltageKnob knob5;
    private VoltageKnob knob4;
    private VoltageKnob knob3;
    private VoltageKnob knob2;
    private VoltageKnob knob1;
    private VoltageLabel scale1A30DeepToneGenModLabel2;
    private VoltageLabel descriptionLabel;
    private VoltageLabel manufacturerLabel;
    private VoltageAudioJack smallBlackInput;
    private VoltageAudioJack inputJack4;
    private VoltageAudioJack inputJack8;
    private VoltageImage image2;


    //[user-code-and-variables]    Add your own variables and functions here
    //[/user-code-and-variables]





}

 