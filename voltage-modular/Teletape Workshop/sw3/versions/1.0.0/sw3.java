package com.insectlabs.sw3;


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


public class sw3 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public sw3( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "sw3 - Voltage Controlled Switch", ModuleType.ModuleType_Switches, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "eaf36c4bf8354d3f9ade1cfbe24f9aa1" );
    }

void InitializeControls()
{

        labelGateY = new VoltageLabel( "labelGateY", "Gate Label Y", this, "G" );
        AddComponent( labelGateY );
        labelGateY.SetWantsMouseNotifications( false );
        labelGateY.SetPosition( 0, 214 );
        labelGateY.SetSize( 30, 30 );
        labelGateY.SetEditable( false, false );
        labelGateY.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGateY.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGateY.SetColor( new Color( 42, 42, 42, 255 ) );
        labelGateY.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelGateY.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelGateY.SetBorderSize( 1 );
        labelGateY.SetMultiLineEdit( false );
        labelGateY.SetIsNumberEditor( false );
        labelGateY.SetNumberEditorRange( 0, 100 );
        labelGateY.SetNumberEditorInterval( 1 );
        labelGateY.SetNumberEditorUsesMouseWheel( false );
        labelGateY.SetHasCustomTextHoverColor( false );
        labelGateY.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelGateY.SetFont( "Arial Black", 33, true, false );

        labelGateX = new VoltageLabel( "labelGateX", "Gate Label X", this, "G" );
        AddComponent( labelGateX );
        labelGateX.SetWantsMouseNotifications( false );
        labelGateX.SetPosition( 85, 115 );
        labelGateX.SetSize( 30, 30 );
        labelGateX.SetEditable( false, false );
        labelGateX.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelGateX.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelGateX.SetColor( new Color( 42, 42, 42, 255 ) );
        labelGateX.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelGateX.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelGateX.SetBorderSize( 1 );
        labelGateX.SetMultiLineEdit( false );
        labelGateX.SetIsNumberEditor( false );
        labelGateX.SetNumberEditorRange( 0, 100 );
        labelGateX.SetNumberEditorInterval( 1 );
        labelGateX.SetNumberEditorUsesMouseWheel( false );
        labelGateX.SetHasCustomTextHoverColor( false );
        labelGateX.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelGateX.SetFont( "Arial Black", 33, true, false );

        labelLatchY = new VoltageLabel( "labelLatchY", "Y Latch Label", this, "LATCH" );
        AddComponent( labelLatchY );
        labelLatchY.SetWantsMouseNotifications( false );
        labelLatchY.SetPosition( 46, 301 );
        labelLatchY.SetSize( 60, 20 );
        labelLatchY.SetEditable( false, false );
        labelLatchY.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLatchY.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLatchY.SetColor( new Color( 166, 36, 36, 255 ) );
        labelLatchY.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLatchY.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLatchY.SetBorderSize( 1 );
        labelLatchY.SetMultiLineEdit( true );
        labelLatchY.SetIsNumberEditor( false );
        labelLatchY.SetNumberEditorRange( 0, 100 );
        labelLatchY.SetNumberEditorInterval( 1 );
        labelLatchY.SetNumberEditorUsesMouseWheel( false );
        labelLatchY.SetHasCustomTextHoverColor( false );
        labelLatchY.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLatchY.SetFont( "Arial", 14, true, false );

        labelFade = new VoltageLabel( "labelFade", "Fade Label", this, "fade" );
        AddComponent( labelFade );
        labelFade.SetWantsMouseNotifications( false );
        labelFade.SetPosition( 31, 316 );
        labelFade.SetSize( 30, 20 );
        labelFade.SetEditable( false, false );
        labelFade.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFade.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFade.SetColor( new Color( 42, 42, 42, 255 ) );
        labelFade.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelFade.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelFade.SetBorderSize( 1 );
        labelFade.SetMultiLineEdit( true );
        labelFade.SetIsNumberEditor( false );
        labelFade.SetNumberEditorRange( 0, 100 );
        labelFade.SetNumberEditorInterval( 1 );
        labelFade.SetNumberEditorUsesMouseWheel( false );
        labelFade.SetHasCustomTextHoverColor( false );
        labelFade.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFade.SetFont( "Arial", 9, true, false );

        labelLatchX = new VoltageLabel( "labelLatchX", "X Latch Label", this, "LATCH" );
        AddComponent( labelLatchX );
        labelLatchX.SetWantsMouseNotifications( false );
        labelLatchX.SetPosition( 10, 35 );
        labelLatchX.SetSize( 60, 20 );
        labelLatchX.SetEditable( false, false );
        labelLatchX.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLatchX.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLatchX.SetColor( new Color( 166, 36, 36, 255 ) );
        labelLatchX.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelLatchX.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelLatchX.SetBorderSize( 1 );
        labelLatchX.SetMultiLineEdit( true );
        labelLatchX.SetIsNumberEditor( false );
        labelLatchX.SetNumberEditorRange( 0, 100 );
        labelLatchX.SetNumberEditorInterval( 1 );
        labelLatchX.SetNumberEditorUsesMouseWheel( false );
        labelLatchX.SetHasCustomTextHoverColor( false );
        labelLatchX.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelLatchX.SetFont( "Arial", 14, true, false );

        labelFooter = new VoltageLabel( "labelFooter", "", this, "voltage controlled switch" );
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
        imageTTW.SetSize( 85, 27 );
        imageTTW.SetCurrentImage( "image image(3).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "sw3" );
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
        labelLocation.SetPosition( 9, 12 );
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

        switchX = new VoltageButton( "switchX", "Switch Button X", this );
        AddComponent( switchX );
        switchX.SetWantsMouseNotifications( false );
        switchX.SetPosition( 80, 40 );
        switchX.SetSize( 25, 25 );
        switchX.SetSkin( "2500 Square Big" );
        switchX.ShowOverlay( true );
        switchX.SetOverlayText( "gate" );
        switchX.SetOverlayTextFont( "Arial Black", 10, true, false );
        switchX.SetOverlayTextColor( new Color( 42, 42, 42 ) );
        switchX.SetOverlayArea( 0, 0, 0, 0 );
        switchX.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        switchX.SetAutoRepeat( false );

        inputCVX = new VoltageAudioJack( "inputCVX", "Switch CV X", this, JackType.JackType_AudioInput );
        AddComponent( inputCVX );
        inputCVX.SetWantsMouseNotifications( false );
        inputCVX.SetPosition( 80, 70 );
        inputCVX.SetSize( 25, 25 );
        inputCVX.SetSkin( "Jack Round 25px" );

        switchY = new VoltageButton( "switchY", "Switch Button Y", this );
        AddComponent( switchY );
        switchY.SetWantsMouseNotifications( false );
        switchY.SetPosition( 10, 290 );
        switchY.SetSize( 25, 25 );
        switchY.SetSkin( "2500 Square Big" );
        switchY.ShowOverlay( true );
        switchY.SetOverlayText( "gate" );
        switchY.SetOverlayTextFont( "Arial Black", 10, true, false );
        switchY.SetOverlayTextColor( new Color( 42, 42, 42 ) );
        switchY.SetOverlayArea( 0, 0, 0, 0 );
        switchY.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        switchY.SetAutoRepeat( false );

        inputCVY = new VoltageAudioJack( "inputCVY", "Switch CV Y", this, JackType.JackType_AudioInput );
        AddComponent( inputCVY );
        inputCVY.SetWantsMouseNotifications( false );
        inputCVY.SetPosition( 10, 260 );
        inputCVY.SetSize( 25, 25 );
        inputCVY.SetSkin( "Jack Round 25px" );

        inputX1 = new VoltageAudioJack( "inputX1", "X In 1", this, JackType.JackType_AudioInput );
        AddComponent( inputX1 );
        inputX1.SetWantsMouseNotifications( false );
        inputX1.SetPosition( 10, 85 );
        inputX1.SetSize( 25, 25 );
        inputX1.SetSkin( "Jack Round 25px" );

        inputX2 = new VoltageAudioJack( "inputX2", "X In 2", this, JackType.JackType_AudioInput );
        AddComponent( inputX2 );
        inputX2.SetWantsMouseNotifications( false );
        inputX2.SetPosition( 10, 145 );
        inputX2.SetSize( 25, 25 );
        inputX2.SetSkin( "Jack Round 25px" );

        outputX = new VoltageAudioJack( "outputX", "X Out", this, JackType.JackType_AudioOutput );
        AddComponent( outputX );
        outputX.SetWantsMouseNotifications( false );
        outputX.SetPosition( 60, 115 );
        outputX.SetSize( 25, 25 );
        outputX.SetSkin( "Mini Jack 25px" );

        outputY1 = new VoltageAudioJack( "outputY1", "Y Out 1", this, JackType.JackType_AudioOutput );
        AddComponent( outputY1 );
        outputY1.SetWantsMouseNotifications( false );
        outputY1.SetPosition( 80, 190 );
        outputY1.SetSize( 25, 25 );
        outputY1.SetSkin( "Mini Jack 25px" );

        outputY2 = new VoltageAudioJack( "outputY2", "Y Out 2", this, JackType.JackType_AudioOutput );
        AddComponent( outputY2 );
        outputY2.SetWantsMouseNotifications( false );
        outputY2.SetPosition( 80, 250 );
        outputY2.SetSize( 25, 25 );
        outputY2.SetSkin( "Mini Jack 25px" );

        inputY = new VoltageAudioJack( "inputY", "Y In", this, JackType.JackType_AudioInput );
        AddComponent( inputY );
        inputY.SetWantsMouseNotifications( false );
        inputY.SetPosition( 30, 220 );
        inputY.SetSize( 25, 25 );
        inputY.SetSkin( "Jack Round 25px" );

        knobTrimX = new VoltageKnob( "knobTrimX", "Reference Trimmer X", this, -10.0, 10.0, 0.0 );
        AddComponent( knobTrimX );
        knobTrimX.SetWantsMouseNotifications( false );
        knobTrimX.SetPosition( 3, 63 );
        knobTrimX.SetSize( 19, 19 );
        knobTrimX.SetSkin( "ARP2500 Sm Red" );
        knobTrimX.SetRange( -10.0, 10.0, 0.0, false, 0 );
        knobTrimX.SetKnobParams( 215, 145 );
        knobTrimX.SetUnits( "V" );
        knobTrimX.DisplayValueInPercent( false );
        knobTrimX.SetKnobAdjustsRing( true );

        knobTrimY = new VoltageKnob( "knobTrimY", "Reference Trimmer Y", this, -10.0, 10.0, 0.0 );
        AddComponent( knobTrimY );
        knobTrimY.SetWantsMouseNotifications( false );
        knobTrimY.SetPosition( 92, 278 );
        knobTrimY.SetSize( 19, 19 );
        knobTrimY.SetSkin( "ARP2500 Sm Red" );
        knobTrimY.SetRange( -10.0, 10.0, 0.0, false, 0 );
        knobTrimY.SetKnobParams( 215, 145 );
        knobTrimY.DisplayValueInPercent( false );
        knobTrimY.SetKnobAdjustsRing( true );

        labelX = new VoltageLabel( "labelX", "X Label", this, "X" );
        AddComponent( labelX );
        labelX.SetWantsMouseNotifications( false );
        labelX.SetPosition( 5, 112 );
        labelX.SetSize( 69, 30 );
        labelX.SetEditable( false, false );
        labelX.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelX.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelX.SetColor( new Color( 230, 225, 211, 255 ) );
        labelX.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelX.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelX.SetBorderSize( 1 );
        labelX.SetMultiLineEdit( false );
        labelX.SetIsNumberEditor( false );
        labelX.SetNumberEditorRange( 0, 100 );
        labelX.SetNumberEditorInterval( 1 );
        labelX.SetNumberEditorUsesMouseWheel( false );
        labelX.SetHasCustomTextHoverColor( false );
        labelX.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelX.SetFont( "Arial Black", 33, true, false );

        labelY = new VoltageLabel( "labelY", "Y Label", this, "Y" );
        AddComponent( labelY );
        labelY.SetWantsMouseNotifications( false );
        labelY.SetPosition( 42, 217 );
        labelY.SetSize( 69, 30 );
        labelY.SetEditable( false, false );
        labelY.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelY.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelY.SetColor( new Color( 230, 225, 211, 255 ) );
        labelY.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelY.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelY.SetBorderSize( 1 );
        labelY.SetMultiLineEdit( false );
        labelY.SetIsNumberEditor( false );
        labelY.SetNumberEditorRange( 0, 100 );
        labelY.SetNumberEditorInterval( 1 );
        labelY.SetNumberEditorUsesMouseWheel( false );
        labelY.SetHasCustomTextHoverColor( false );
        labelY.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelY.SetFont( "Arial Black", 33, true, false );

        outputGateX = new VoltageAudioJack( "outputGateX", "X Gate", this, JackType.JackType_AudioOutput );
        AddComponent( outputGateX );
        outputGateX.SetWantsMouseNotifications( false );
        outputGateX.SetPosition( 80, 135 );
        outputGateX.SetSize( 25, 25 );
        outputGateX.SetSkin( "Mini Jack 25px" );

        outputGateY = new VoltageAudioJack( "outputGateY", "Y Gate", this, JackType.JackType_AudioOutput );
        AddComponent( outputGateY );
        outputGateY.SetWantsMouseNotifications( false );
        outputGateY.SetPosition( 10, 200 );
        outputGateY.SetSize( 25, 25 );
        outputGateY.SetSkin( "Mini Jack 25px" );

        lampGateY = new VoltageLED( "lampGateY", "Gate Y Lamp", this );
        AddComponent( lampGateY );
        lampGateY.SetWantsMouseNotifications( false );
        lampGateY.SetPosition( 32, 198 );
        lampGateY.SetSize( 10, 10 );
        lampGateY.SetSkin( "2500 Lamp White" );

        lampGateX = new VoltageLED( "lampGateX", "Gate X Lamp", this );
        AddComponent( lampGateX );
        lampGateX.SetWantsMouseNotifications( false );
        lampGateX.SetPosition( 73, 152 );
        lampGateX.SetSize( 10, 10 );
        lampGateX.SetSkin( "2500 Lamp White" );

        switchLatchY = new VoltageSwitch( "switchLatchY", "Y Latch Switch", this, 0 );
        AddComponent( switchLatchY );
        switchLatchY.SetWantsMouseNotifications( false );
        switchLatchY.SetPosition( 40, 296 );
        switchLatchY.SetSize( 37, 15 );
        switchLatchY.SetSkin( "Rocker Switch Plastic Blue Hor" );

        switchLatchX = new VoltageSwitch( "switchLatchX", "X Latch Switch", this, 1 );
        AddComponent( switchLatchX );
        switchLatchX.SetWantsMouseNotifications( false );
        switchLatchX.SetPosition( 38, 46 );
        switchLatchX.SetSize( 37, 15 );
        switchLatchX.SetSkin( "Rocker Switch Plastic Blue Hor" );

        knobFade = new VoltageKnob( "knobFade", "Fade Time Knob", this, 0.0, 200.0, 0.0 );
        AddComponent( knobFade );
        knobFade.SetWantsMouseNotifications( false );
        knobFade.SetPosition( 21, 317 );
        knobFade.SetSize( 15, 15 );
        knobFade.SetSkin( "ARP2500 Med Black" );
        knobFade.SetRange( 0.0, 200.0, 0.0, false, 0 );
        knobFade.SetKnobParams( 215, 145 );
        knobFade.DisplayValueInPercent( false );
        knobFade.SetKnobAdjustsRing( true );
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
        // no reset here: a preset may already have restored the latched state
        ready = true;
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
        // nothing to release: no threads, no timers
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
        // controls are read every sample; nothing to handle here
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
        boolean latchX = ( switchLatchX.GetValue() < 0.5 ) == X_LATCH_IS_STATE_0;
        boolean latchY = ( switchLatchY.GetValue() < 0.5 ) == Y_LATCH_IS_STATE_0;
        core.process( readJack( inputCVX ), switchX.GetValue() > 0.5, latchX,
            readJack( inputX1 ), inputX1.IsConnected(), readJack( inputX2 ), inputX2.IsConnected(), knobTrimX.GetValue(),
            readJack( inputCVY ), switchY.GetValue() > 0.5, latchY,
            readJack( inputY ), inputY.IsConnected(), refVolts( knobTrimY ), Sw3Core.fadeMsFromPos( knobFraction( knobFade ) ) );
        outputX.SetValue( core.outX );
        outputY1.SetValue( core.outY1 );
        outputY2.SetValue( core.outY2 );
        outputGateX.SetValue( core.outGateX );
        outputGateY.SetValue( core.outGateY );
        setLamps( core.gx.lamp, core.gy.lamp );
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
        // Bypassed: no switching. IN X1 passes to OUT X, IN Y passes to OUT Y1, everything else is 0, lamps off.
        if (!ready) {
            return;
        }
        core.bypass( readJack( inputX1 ), inputX1.IsConnected(), readJack( inputY ), inputY.IsConnected() );
        outputX.SetValue( core.outX );
        outputY1.SetValue( core.outY1 );
        outputY2.SetValue( core.outY2 );
        outputGateX.SetValue( 0.0 );
        outputGateY.SetValue( 0.0 );
        setLamps( false, false );
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
        if (component == knobFade) {
            double ms = Sw3Core.fadeMsFromPos( knobFraction( knobFade ) );
            return ms <= 0.0 ? "Fade: off (hard switch)" : String.format( java.util.Locale.ROOT, ms < 10.0 ? "Fade: %.2f ms" : "Fade: %.1f ms", ms );
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
        // FADE: the tooltip shows milliseconds, so typed entry arrives in milliseconds and is converted back to the knob's own range.
        if (component == knobFade) {
            newValue = knobFade.GetMinValue() + Sw3Core.posFromFadeMs( newValue ) * ( knobFade.GetMaxValue() - knobFade.GetMinValue() );
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
        return core.getState();
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
        core.setState( stateInfo );
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
        SetStateInformation( stateInfo );
        //[/user-SetStateInformationForVariations]
    }


    // Auto-generated variables
    private VoltageKnob knobFade;
    private VoltageSwitch switchLatchX;
    private VoltageSwitch switchLatchY;
    private VoltageLED lampGateX;
    private VoltageLED lampGateY;
    private VoltageAudioJack outputGateY;
    private VoltageAudioJack outputGateX;
    private VoltageLabel labelY;
    private VoltageLabel labelX;
    private VoltageKnob knobTrimY;
    private VoltageKnob knobTrimX;
    private VoltageAudioJack inputY;
    private VoltageAudioJack outputY2;
    private VoltageAudioJack outputY1;
    private VoltageAudioJack outputX;
    private VoltageAudioJack inputX2;
    private VoltageAudioJack inputX1;
    private VoltageAudioJack inputCVY;
    private VoltageButton switchY;
    private VoltageAudioJack inputCVX;
    private VoltageButton switchX;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageLabel labelLatchX;
    private VoltageLabel labelFade;
    private VoltageLabel labelLatchY;
    private VoltageLabel labelGateX;
    private VoltageLabel labelGateY;


    //[user-code-and-variables]    Add your own variables and functions here
    // ---- sw3 module layer ----
    // Jack values are volts [MEASURED]. Gate jack: rises at 3 V, falls below 1.8 V. Button and gate jack are OR-ed.
    // The two latch switches are wired as the author's notes say (the Y half of the panel is rotated 180 degrees):
    //   X latch switch: state 0 (left) = LATCH, state 1 (right) = MOMENTARY.   Y latch switch: state 0 (left) = MOMENTARY, state 1 (right) = LATCH.
    private static final boolean X_LATCH_IS_STATE_0 = true;
    private static final boolean Y_LATCH_IS_STATE_0 = false;
    private final Sw3Core core = new Sw3Core();
    private boolean ready, lastLampX, lastLampY, lampsInit;

    // ---- Sw3Core (framework-free; same source as Sw3Core.java, tested headless) ----
    private static final class Sw3Core {
        public static final double FS = 48000.0;
        public static final double GATE_HIGH_V = 3.0, GATE_LOW_V = 1.8, GATE_OUT_V = 10.0, PULSE_S = 0.005, REF_MAX_V = 10.0, FADE_MAX_MS = 200.0, FADE_DEFAULT_MS = 0.25;
        public static final int PULSE_SAMPLES = (int) Math.round(PULSE_S * FS);
    
        /** FADE knob taper: exponential, 0 at the CCW end, 200 ms at CW. FADE_RATIO 1521 puts 5 ms at half travel (0.14 ms at 10 %, 0.7 ms at 25 %, 32 ms at 75 %). [GUESS] */
        public static final double FADE_RATIO = 1521.0;
        public static double fadeMsFromPos(double p) {
            p = clamp(fin(p), 0.0, 1.0);
            return FADE_MAX_MS * (Math.pow(FADE_RATIO, p) - 1.0) / (FADE_RATIO - 1.0);
        }
        public static double posFromFadeMs(double ms) {
            ms = clamp(fin(ms), 0.0, FADE_MAX_MS);
            return Math.log(1.0 + ms * (FADE_RATIO - 1.0) / FADE_MAX_MS) / Math.log(FADE_RATIO);
        }
    
        private static double fin(double v) { return (Double.isNaN(v) || Double.isInfinite(v)) ? 0.0 : v; }
        private static double clamp(double v, double lo, double hi) { return v < lo ? lo : (v > hi ? hi : v); }
    
        /** One gate / state machine. */
        public static final class Gate {
            public boolean secondary, gateOut, lamp;
            public double mix;                                   // 0 = PRIMARY route, 1 = SECONDARY route, ramps between
            private boolean cvHigh, cvPrev, btnPrev, primed;
            private int pulse;
    
            void unprime() { primed = false; pulse = 0; gateOut = false; lamp = false; }
    
            void step(double cv, boolean btn, boolean latch, double fadeStep) {
                cv = fin(cv);
                if (cvHigh) { if (cv < GATE_LOW_V) cvHigh = false; } else if (cv >= GATE_HIGH_V) cvHigh = true;
                boolean high = cvHigh || btn;                    // gate OUT / lamp follow this
                boolean sec = cvHigh ^ btn;                      // MOMENTARY state: XOR
                if (!primed) {                                   // first sample after reset or bypass: take the inputs as they are, no edge
                    cvPrev = cvHigh; btnPrev = btn; primed = true;
                    if (!latch) secondary = sec;
                    pulse = 0;
                    mix = secondary ? 1.0 : 0.0;
                } else {
                    boolean rise = (cvHigh && !cvPrev) || (btn && !btnPrev);
                    if (latch) {
                        if (rise) secondary = !secondary;
                    } else {
                        secondary = sec;
                    }
                    if (rise) pulse = PULSE_SAMPLES;                       // triggers on rising edges only, none when MOMENTARY falls back
                    cvPrev = cvHigh; btnPrev = btn;
                }
                if (primed) { double t = secondary ? 1.0 : 0.0; mix = mix < t ? Math.min(t, mix + fadeStep) : Math.max(t, mix - fadeStep); }
                gateOut = high || pulse > 0;
                if (pulse > 0) pulse--;
                lamp = secondary || gateOut;
            }
        }
    
        public final Gate gx = new Gate(), gy = new Gate();
        public double outX, outY1, outY2, outGateX, outGateY;
    
        /** Latched state for presets: 2 bytes, 1 = SECONDARY (X, then Y). */
        public byte[] getState() { return new byte[] { (byte) (gx.secondary ? 1 : 0), (byte) (gy.secondary ? 1 : 0) }; }
        public void setState(byte[] b) {
            if (b == null || b.length < 2) return;
            gx.secondary = b[0] != 0; gy.secondary = b[1] != 0;
            gx.unprime(); gy.unprime();                          // the next sample re-reads the gates without an edge; MOMENTARY follows the gate again
        }
    
        public void reset() { gx.unprime(); gy.unprime(); gx.secondary = false; gy.secondary = false; outX = outY1 = outY2 = outGateX = outGateY = 0.0; }
    
        /** X: gate jack, button, latch flag, IN X1 (connected?), IN X2 (connected?), reference volts. Y: gate jack, button, latch flag, IN Y (connected?), reference volts. */
        public void process(double cvX, boolean btnX, boolean latchX, double x1, boolean c1, double x2, boolean c2, double refX,
                            double cvY, boolean btnY, boolean latchY, double y, boolean cy, double refY) {
            process(cvX, btnX, latchX, x1, c1, x2, c2, refX, cvY, btnY, latchY, y, cy, refY, FADE_DEFAULT_MS);
        }
    
        /** fadeMs: crossfade time in ms from the FADE knob (0..200); 0 or less = hard switch. */
        public void process(double cvX, boolean btnX, boolean latchX, double x1, boolean c1, double x2, boolean c2, double refX,
                            double cvY, boolean btnY, boolean latchY, double y, boolean cy, double refY, double fadeMs) {
            fadeMs = clamp(fin(fadeMs), 0.0, FADE_MAX_MS);
            double fadeStep = fadeMs <= 0.0 ? 1.0 : Math.min(1.0, 1000.0 / (fadeMs * FS));
            gx.step(cvX, btnX, latchX, fadeStep);
            gy.step(cvY, btnY, latchY, fadeStep);
            double mx = gx.mix, my = gy.mix;
            double xp = c1 ? fin(x1) : clamp(fin(refX), -REF_MAX_V, REF_MAX_V), xs = c2 ? fin(x2) : 0.0;
            outX = (1.0 - mx) * xp + mx * xs;
            double yin = cy ? fin(y) : clamp(fin(refY), -REF_MAX_V, REF_MAX_V);       // IN Y unpatched: the Y reference is the source
            outY1 = (1.0 - my) * yin;
            outY2 = my * yin;
            outGateX = gx.gateOut ? GATE_OUT_V : 0.0;
            outGateY = gy.gateOut ? GATE_OUT_V : 0.0;
        }
    
        /** Bypass: no switching. The PRIMARY routes carry the inputs; gates are silent. The next processed sample re-reads gates without edges. */
        public void bypass(double x1, boolean c1, double y, boolean cy) {
            gx.unprime(); gy.unprime();
            outX = c1 ? fin(x1) : 0.0;
            outY1 = cy ? fin(y) : 0.0;
            outY2 = 0.0; outGateX = 0.0; outGateY = 0.0;
        }
    }

    private static double readJack(VoltageAudioJack jack) {
        if (!jack.IsConnected()) {
            return 0.0;
        }
        double v = jack.GetValue();
        return Double.isFinite(v) ? v : 0.0;
    }

    /** Reference trimmer to volts: spans -10..+10 V over the knob's own range, so the X knob (-10..10) reads in volts and a 0..1 knob centres at 0 V. */
    private static double refVolts(VoltageKnob k) {
        double lo = k.GetMinValue();
        double hi = k.GetMaxValue();
        if (!(hi > lo)) {
            return 0.0;
        }
        double f = ( k.GetValue() - lo ) / ( hi - lo );
        return -10.0 + 20.0 * ( f < 0.0 ? 0.0 : ( f > 1.0 ? 1.0 : f ) );
    }

    private static double knobFraction(VoltageKnob k) {
        double lo = k.GetMinValue();
        double hi = k.GetMaxValue();
        return hi > lo ? ( k.GetValue() - lo ) / ( hi - lo ) : 0.0;
    }

    private void setLamps(boolean x, boolean y) {
        if (!lampsInit || x != lastLampX) {
            lampGateX.SetValue( x ? 1.0 : 0.0 );
            lastLampX = x;
        }
        if (!lampsInit || y != lastLampY) {
            lampGateY.SetValue( y ? 1.0 : 0.0 );
            lastLampY = y;
        }
        lampsInit = true;
    }
    //[/user-code-and-variables]
}

 