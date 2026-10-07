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
        SetSkin( "35326b383a0f461caaa04135d320dabc" );
    }

void InitializeControls()
{

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "Manufacturer Label", this, "iL" );
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

        colophon = new VoltageLabel( "colophon", "colophon", this, "insect laboratories pittsburgh, PA        united states & beyond" );
        AddComponent( colophon );
        colophon.SetWantsMouseNotifications( false );
        colophon.SetPosition( 85, 335 );
        colophon.SetSize( 60, 20 );
        colophon.SetEditable( false, false );
        colophon.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        colophon.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        colophon.SetColor( new Color( 147, 147, 147, 147 ) );
        colophon.SetBkColor( new Color( 65, 65, 65, 0 ) );
        colophon.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        colophon.SetBorderSize( 1 );
        colophon.SetMultiLineEdit( true );
        colophon.SetIsNumberEditor( false );
        colophon.SetNumberEditorRange( 0, 100 );
        colophon.SetNumberEditorInterval( 1 );
        colophon.SetNumberEditorUsesMouseWheel( false );
        colophon.SetHasCustomTextHoverColor( false );
        colophon.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        colophon.SetFont( "Arial Black", 6, true, false );

        knob1 = new VoltageKnob( "knob1", "knob1", this, 0.0, 1.0, 0.5 );
        AddComponent( knob1 );
        knob1.SetWantsMouseNotifications( false );
        knob1.SetPosition( 22, 42 );
        knob1.SetSize( 35, 35 );
        knob1.SetSkin( "Cosmo Medium" );
        knob1.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob1.SetKnobParams( 215, 145 );
        knob1.DisplayValueInPercent( false );
        knob1.SetKnobAdjustsRing( true );

        knob2 = new VoltageKnob( "knob2", "knob2", this, 0.0, 1.0, 0.5 );
        AddComponent( knob2 );
        knob2.SetWantsMouseNotifications( false );
        knob2.SetPosition( 22, 82 );
        knob2.SetSize( 35, 35 );
        knob2.SetSkin( "Cosmo Medium" );
        knob2.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob2.SetKnobParams( 215, 145 );
        knob2.DisplayValueInPercent( false );
        knob2.SetKnobAdjustsRing( true );

        knob3 = new VoltageKnob( "knob3", "knob3", this, 0.0, 1.0, 0.5 );
        AddComponent( knob3 );
        knob3.SetWantsMouseNotifications( false );
        knob3.SetPosition( 22, 127 );
        knob3.SetSize( 35, 35 );
        knob3.SetSkin( "Cosmo Medium" );
        knob3.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob3.SetKnobParams( 215, 145 );
        knob3.DisplayValueInPercent( false );
        knob3.SetKnobAdjustsRing( true );

        knob4 = new VoltageKnob( "knob4", "knob4", this, 0.0, 1.0, 0.5 );
        AddComponent( knob4 );
        knob4.SetWantsMouseNotifications( false );
        knob4.SetPosition( 22, 167 );
        knob4.SetSize( 35, 35 );
        knob4.SetSkin( "Cosmo Medium" );
        knob4.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob4.SetKnobParams( 215, 145 );
        knob4.DisplayValueInPercent( false );
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

        knob9 = new VoltageKnob( "knob9", "knob9", this, 0.0, 1.0, 0.5 );
        AddComponent( knob9 );
        knob9.SetWantsMouseNotifications( false );
        knob9.SetPosition( 61, 42 );
        knob9.SetSize( 35, 35 );
        knob9.SetSkin( "Cosmo Medium" );
        knob9.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob9.SetKnobParams( 215, 145 );
        knob9.DisplayValueInPercent( false );
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

        knob10 = new VoltageKnob( "knob10", "knob10", this, 0.0, 1.0, 0.5 );
        AddComponent( knob10 );
        knob10.SetWantsMouseNotifications( false );
        knob10.SetPosition( 62, 82 );
        knob10.SetSize( 35, 35 );
        knob10.SetSkin( "Cosmo Medium" );
        knob10.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob10.SetKnobParams( 215, 145 );
        knob10.DisplayValueInPercent( false );
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

        knob11 = new VoltageKnob( "knob11", "knob11", this, 0.0, 1.0, 0.5 );
        AddComponent( knob11 );
        knob11.SetWantsMouseNotifications( false );
        knob11.SetPosition( 62, 127 );
        knob11.SetSize( 35, 35 );
        knob11.SetSkin( "Cosmo Medium" );
        knob11.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob11.SetKnobParams( 215, 145 );
        knob11.DisplayValueInPercent( false );
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

        knob12 = new VoltageKnob( "knob12", "knob12", this, 0.0, 1.0, 0.5 );
        AddComponent( knob12 );
        knob12.SetWantsMouseNotifications( false );
        knob12.SetPosition( 62, 167 );
        knob12.SetSize( 35, 35 );
        knob12.SetSkin( "Cosmo Medium" );
        knob12.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knob12.SetKnobParams( 215, 145 );
        knob12.DisplayValueInPercent( false );
        knob12.SetKnobAdjustsRing( true );

        inputJack7 = new VoltageAudioJack( "inputJack7", "inputJack7", this, JackType.JackType_AudioInput );
        AddComponent( inputJack7 );
        inputJack7.SetWantsMouseNotifications( false );
        inputJack7.SetPosition( 124, 302 );
        inputJack7.SetSize( 37, 37 );
        inputJack7.SetSkin( "Jack Round" );

        inputJack8 = new VoltageAudioJack( "inputJack8", "inputJack8", this, JackType.JackType_AudioInput );
        AddComponent( inputJack8 );
        inputJack8.SetWantsMouseNotifications( false );
        inputJack8.SetPosition( 115, 259 );
        inputJack8.SetSize( 37, 37 );
        inputJack8.SetSkin( "Rotated Half" );

        inputJack1 = new VoltageAudioJack( "inputJack1", "inputJack1", this, JackType.JackType_AudioInput );
        AddComponent( inputJack1 );
        inputJack1.SetWantsMouseNotifications( false );
        inputJack1.SetPosition( 22, 223 );
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

        inputJack4 = new VoltageAudioJack( "inputJack4", "inputJack4", this, JackType.JackType_AudioInput );
        AddComponent( inputJack4 );
        inputJack4.SetWantsMouseNotifications( false );
        inputJack4.SetPosition( 62, 272 );
        inputJack4.SetSize( 37, 37 );
        inputJack4.SetSkin( "Dark Jack Straight" );

        bigMiniSilverJack = new VoltageAudioJack( "bigMiniSilverJack", "Big Mini Silver Jack", this, JackType.JackType_AudioOutput );
        AddComponent( bigMiniSilverJack );
        bigMiniSilverJack.SetWantsMouseNotifications( false );
        bigMiniSilverJack.SetPosition( 182, 222 );
        bigMiniSilverJack.SetSize( 37, 37 );
        bigMiniSilverJack.SetSkin( "Mini Jack 25px" );

        switch1 = new VoltageSwitch( "switch1", "switch1", this, 1 );
        AddComponent( switch1 );
        switch1.SetWantsMouseNotifications( false );
        switch1.SetPosition( 103, 52 );
        switch1.SetSize( 51, 15 );
        switch1.SetSkin( "4-State Slide Horiz" );

        switch2 = new VoltageSwitch( "switch2", "switch2", this, 0 );
        AddComponent( switch2 );
        switch2.SetWantsMouseNotifications( false );
        switch2.SetPosition( 103, 92 );
        switch2.SetSize( 51, 15 );
        switch2.SetSkin( "4-State Slide Horiz" );

        switch3 = new VoltageSwitch( "switch3", "switch3", this, 0 );
        AddComponent( switch3 );
        switch3.SetWantsMouseNotifications( false );
        switch3.SetPosition( 103, 137 );
        switch3.SetSize( 51, 15 );
        switch3.SetSkin( "4-State Slide Horiz" );

        switch4 = new VoltageSwitch( "switch4", "switch4", this, 0 );
        AddComponent( switch4 );
        switch4.SetWantsMouseNotifications( false );
        switch4.SetPosition( 103, 177 );
        switch4.SetSize( 51, 15 );
        switch4.SetSkin( "4-State Slide Horiz" );

        textLabel6 = new VoltageLabel( "textLabel6", "textLabel6", this, "I" );
        AddComponent( textLabel6 );
        textLabel6.SetWantsMouseNotifications( false );
        textLabel6.SetPosition( 30, 254 );
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
        textLabel7.SetPosition( 70, 254 );
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
        textLabel8.SetPosition( 29, 304 );
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
        textLabel9.SetPosition( 69, 304 );
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
        textLabel14.SetPosition( 102, 62 );
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

        textLabel15 = new VoltageLabel( "textLabel15", "textLabel15", this, "0 • L • M • H" );
        AddComponent( textLabel15 );
        textLabel15.SetWantsMouseNotifications( false );
        textLabel15.SetPosition( 102, 102 );
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

        textLabel16 = new VoltageLabel( "textLabel16", "textLabel16", this, "0 • L • M • H" );
        AddComponent( textLabel16 );
        textLabel16.SetWantsMouseNotifications( false );
        textLabel16.SetPosition( 102, 147 );
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

        textLabel17 = new VoltageLabel( "textLabel17", "textLabel17", this, "0 • L • M • H" );
        AddComponent( textLabel17 );
        textLabel17.SetWantsMouseNotifications( false );
        textLabel17.SetPosition( 102, 187 );
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

        inputJack6 = new VoltageAudioJack( "inputJack6", "inputJack6", this, JackType.JackType_AudioInput );
        AddComponent( inputJack6 );
        inputJack6.SetWantsMouseNotifications( false );
        inputJack6.SetPosition( 182, 272 );
        inputJack6.SetSize( 37, 37 );
        inputJack6.SetSkin( "Jack Round" );
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
    private VoltageAudioJack inputJack6;
    private VoltageLabel textLabel17;
    private VoltageLabel textLabel16;
    private VoltageLabel textLabel15;
    private VoltageLabel textLabel14;
    private VoltageLabel textLabel13;
    private VoltageLabel textLabel12;
    private VoltageLabel textLabel11;
    private VoltageLabel textLabel10;
    private VoltageLabel textLabel9;
    private VoltageLabel textLabel8;
    private VoltageLabel textLabel7;
    private VoltageLabel textLabel6;
    private VoltageSwitch switch4;
    private VoltageSwitch switch3;
    private VoltageSwitch switch2;
    private VoltageSwitch switch1;
    private VoltageAudioJack bigMiniSilverJack;
    private VoltageAudioJack inputJack4;
    private VoltageAudioJack inputJack3;
    private VoltageAudioJack inputJack2;
    private VoltageAudioJack inputJack1;
    private VoltageAudioJack inputJack8;
    private VoltageAudioJack inputJack7;
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
    private VoltageLabel colophon;
    private VoltageLabel scale1A30DeepToneGenModLabel2;
    private VoltageLabel descriptionLabel;
    private VoltageLabel manufacturerLabel;


    //[user-code-and-variables]    Add your own variables and functions here
    //[/user-code-and-variables]





}

 