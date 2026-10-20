package com.insectlabs.fpa;


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


public class fpa extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public fpa( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "fpa", ModuleType.ModuleType_Source, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "29a81754efed41b58a5adf6f5f9212d2" );
    }

void InitializeControls()
{

        imageTTW = new VoltageImage( "imageTTW", "TTW Imagemark", this, false );
        AddComponent( imageTTW );
        imageTTW.SetWantsMouseNotifications( false );
        imageTTW.SetPosition( 37, 344 );
        imageTTW.SetSize( 41, 13 );
        imageTTW.SetCurrentImage( "image image(4).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "fpa" );
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

        labelLocation = new VoltageLabel( "labelLocation", "Location Mark", this, "(r)aza (k)raft" );
        AddComponent( labelLocation );
        labelLocation.SetWantsMouseNotifications( false );
        labelLocation.SetPosition( 1, 58 );
        labelLocation.SetSize( 41, 14 );
        labelLocation.SetEditable( false, false );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelLocation.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelLocation.SetColor( new Color( 164, 34, 34, 255 ) );
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

        knobGain1 = new VoltageKnob( "knobGain1", "Gain 1", this, 0.0, 1.0, 0.5 );
        AddComponent( knobGain1 );
        knobGain1.SetWantsMouseNotifications( false );
        knobGain1.SetPosition( 34, 15 );
        knobGain1.SetSize( 48, 48 );
        knobGain1.SetSkin( "ARP2500 Large Black" );
        knobGain1.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobGain1.SetKnobParams( 215, 145 );
        knobGain1.DisplayValueInPercent( false );
        knobGain1.SetKnobAdjustsRing( true );

        knobGain2 = new VoltageKnob( "knobGain2", "Gain 2", this, 0.0, 1.0, 0.5 );
        AddComponent( knobGain2 );
        knobGain2.SetWantsMouseNotifications( false );
        knobGain2.SetPosition( 34, 70 );
        knobGain2.SetSize( 48, 48 );
        knobGain2.SetSkin( "ARP2500 Large Black" );
        knobGain2.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobGain2.SetKnobParams( 215, 145 );
        knobGain2.DisplayValueInPercent( false );
        knobGain2.SetKnobAdjustsRing( true );

        knobGain3 = new VoltageKnob( "knobGain3", "Gain 3", this, 0.0, 1.0, 0.5 );
        AddComponent( knobGain3 );
        knobGain3.SetWantsMouseNotifications( false );
        knobGain3.SetPosition( 34, 125 );
        knobGain3.SetSize( 48, 48 );
        knobGain3.SetSkin( "ARP2500 Large Black" );
        knobGain3.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobGain3.SetKnobParams( 215, 145 );
        knobGain3.DisplayValueInPercent( false );
        knobGain3.SetKnobAdjustsRing( true );

        knobGain4 = new VoltageKnob( "knobGain4", "Gain 4", this, 0.0, 1.0, 0.5 );
        AddComponent( knobGain4 );
        knobGain4.SetWantsMouseNotifications( false );
        knobGain4.SetPosition( 34, 180 );
        knobGain4.SetSize( 48, 48 );
        knobGain4.SetSkin( "ARP2500 Large Black" );
        knobGain4.SetRange( 0.0, 1.0, 0.5, false, 0 );
        knobGain4.SetKnobParams( 215, 145 );
        knobGain4.DisplayValueInPercent( false );
        knobGain4.SetKnobAdjustsRing( true );

        buttonPhase1 = new VoltageButton( "buttonPhase1", "Phase Button 1", this );
        AddComponent( buttonPhase1 );
        buttonPhase1.SetWantsMouseNotifications( false );
        buttonPhase1.SetPosition( 10, 30 );
        buttonPhase1.SetSize( 20, 20 );
        buttonPhase1.SetSkin( "2500 Square Big" );
        buttonPhase1.ShowOverlay( true );
        buttonPhase1.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase1.SetOverlayText( "" );
        buttonPhase1.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase1.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        buttonPhase1.SetAutoRepeat( false );

        buttonPhase2 = new VoltageButton( "buttonPhase2", "Phase Button 2", this );
        AddComponent( buttonPhase2 );
        buttonPhase2.SetWantsMouseNotifications( false );
        buttonPhase2.SetPosition( 10, 85 );
        buttonPhase2.SetSize( 20, 20 );
        buttonPhase2.SetSkin( "2500 Square Big" );
        buttonPhase2.ShowOverlay( true );
        buttonPhase2.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase2.SetOverlayText( "" );
        buttonPhase2.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase2.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        buttonPhase2.SetAutoRepeat( false );

        buttonPhase3 = new VoltageButton( "buttonPhase3", "Phase Button 3", this );
        AddComponent( buttonPhase3 );
        buttonPhase3.SetWantsMouseNotifications( false );
        buttonPhase3.SetPosition( 10, 140 );
        buttonPhase3.SetSize( 20, 20 );
        buttonPhase3.SetSkin( "2500 Square Big" );
        buttonPhase3.ShowOverlay( true );
        buttonPhase3.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase3.SetOverlayText( "" );
        buttonPhase3.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase3.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        buttonPhase3.SetAutoRepeat( false );

        buttonPhase4 = new VoltageButton( "buttonPhase4", "Phase Button 4", this );
        AddComponent( buttonPhase4 );
        buttonPhase4.SetWantsMouseNotifications( false );
        buttonPhase4.SetPosition( 10, 195 );
        buttonPhase4.SetSize( 20, 20 );
        buttonPhase4.SetSkin( "2500 Square Big" );
        buttonPhase4.ShowOverlay( true );
        buttonPhase4.SetImageOverlay( "button-label-phase-invert-icon.svg" );
        buttonPhase4.SetOverlayText( "" );
        buttonPhase4.SetOverlayArea( 0, 0, 0, 0 );
        buttonPhase4.SetOverlayTextJustification( VoltageButton.Justification.Centered );
        buttonPhase4.SetAutoRepeat( false );

        knobTrim1 = new VoltageKnob( "knobTrim1", "Trim 1", this, 0.0, 1.0, 0.0 );
        AddComponent( knobTrim1 );
        knobTrim1.SetWantsMouseNotifications( false );
        knobTrim1.SetPosition( 85, 30 );
        knobTrim1.SetSize( 20, 20 );
        knobTrim1.SetSkin( "TR Large" );
        knobTrim1.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobTrim1.SetKnobParams( 215, 145 );
        knobTrim1.DisplayValueInPercent( true );
        knobTrim1.SetKnobAdjustsRing( true );

        knobTrim2 = new VoltageKnob( "knobTrim2", "Trim 2", this, 0.0, 1.0, 0.0 );
        AddComponent( knobTrim2 );
        knobTrim2.SetWantsMouseNotifications( false );
        knobTrim2.SetPosition( 85, 85 );
        knobTrim2.SetSize( 20, 20 );
        knobTrim2.SetSkin( "TR Large" );
        knobTrim2.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobTrim2.SetKnobParams( 215, 145 );
        knobTrim2.DisplayValueInPercent( true );
        knobTrim2.SetKnobAdjustsRing( true );

        knobTrim3 = new VoltageKnob( "knobTrim3", "Trim 3", this, 0.0, 1.0, 0.0 );
        AddComponent( knobTrim3 );
        knobTrim3.SetWantsMouseNotifications( false );
        knobTrim3.SetPosition( 85, 195 );
        knobTrim3.SetSize( 20, 20 );
        knobTrim3.SetSkin( "TR Large" );
        knobTrim3.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobTrim3.SetKnobParams( 215, 145 );
        knobTrim3.DisplayValueInPercent( true );
        knobTrim3.SetKnobAdjustsRing( true );

        knobTrim4 = new VoltageKnob( "knobTrim4", "Trim 4", this, 0.0, 1.0, 0.0 );
        AddComponent( knobTrim4 );
        knobTrim4.SetWantsMouseNotifications( false );
        knobTrim4.SetPosition( 85, 140 );
        knobTrim4.SetSize( 20, 20 );
        knobTrim4.SetSkin( "TR Large" );
        knobTrim4.SetRange( 0.0, 1.0, 0.0, false, 0 );
        knobTrim4.SetKnobParams( 215, 145 );
        knobTrim4.DisplayValueInPercent( true );
        knobTrim4.SetKnobAdjustsRing( true );

        inputPre1 = new VoltageAudioJack( "inputPre1", "Input Pre-Amp 1", this, JackType.JackType_AudioInput );
        AddComponent( inputPre1 );
        inputPre1.SetWantsMouseNotifications( false );
        inputPre1.SetPosition( 3, 234 );
        inputPre1.SetSize( 25, 25 );
        inputPre1.SetSkin( "Jack Round 25px" );

        inputPre2 = new VoltageAudioJack( "inputPre2", "Input Pre-Amp 2", this, JackType.JackType_AudioInput );
        AddComponent( inputPre2 );
        inputPre2.SetWantsMouseNotifications( false );
        inputPre2.SetPosition( 31, 235 );
        inputPre2.SetSize( 25, 25 );
        inputPre2.SetSkin( "Jack Round 25px" );

        inputPre3 = new VoltageAudioJack( "inputPre3", "Input Pre-Amp 3", this, JackType.JackType_AudioInput );
        AddComponent( inputPre3 );
        inputPre3.SetWantsMouseNotifications( false );
        inputPre3.SetPosition( 59, 235 );
        inputPre3.SetSize( 25, 25 );
        inputPre3.SetSkin( "Jack Round 25px" );

        inputPre4 = new VoltageAudioJack( "inputPre4", "Input Pre-Amp 4", this, JackType.JackType_AudioInput );
        AddComponent( inputPre4 );
        inputPre4.SetWantsMouseNotifications( false );
        inputPre4.SetPosition( 87, 235 );
        inputPre4.SetSize( 25, 25 );
        inputPre4.SetSkin( "Jack Round 25px" );

        switch1 = new VoltageSwitch( "switch1", "Mode Selector Switch 1", this, 0 );
        AddComponent( switch1 );
        switch1.SetWantsMouseNotifications( false );
        switch1.SetPosition( 9, 265 );
        switch1.SetSize( 13, 34 );
        switch1.SetSkin( "4-State Slide Vertical" );

        switch2 = new VoltageSwitch( "switch2", "Mode Selector Switch 2", this, 0 );
        AddComponent( switch2 );
        switch2.SetWantsMouseNotifications( false );
        switch2.SetPosition( 37, 265 );
        switch2.SetSize( 13, 34 );
        switch2.SetSkin( "4-State Slide Vertical" );

        switch3 = new VoltageSwitch( "switch3", "Mode Selector Switch 3", this, 0 );
        AddComponent( switch3 );
        switch3.SetWantsMouseNotifications( false );
        switch3.SetPosition( 65, 265 );
        switch3.SetSize( 13, 34 );
        switch3.SetSkin( "4-State Slide Vertical" );

        switch4 = new VoltageSwitch( "switch4", "Mode Selector Switch 4", this, 0 );
        AddComponent( switch4 );
        switch4.SetWantsMouseNotifications( false );
        switch4.SetPosition( 93, 265 );
        switch4.SetSize( 13, 34 );
        switch4.SetSkin( "4-State Slide Vertical" );

        output1 = new VoltageAudioJack( "output1", "Output 1", this, JackType.JackType_AudioOutput );
        AddComponent( output1 );
        output1.SetWantsMouseNotifications( false );
        output1.SetPosition( 4, 305 );
        output1.SetSize( 25, 25 );
        output1.SetSkin( "Mini Jack 25px" );

        output2 = new VoltageAudioJack( "output2", "Output 2", this, JackType.JackType_AudioOutput );
        AddComponent( output2 );
        output2.SetWantsMouseNotifications( false );
        output2.SetPosition( 31, 305 );
        output2.SetSize( 25, 25 );
        output2.SetSkin( "Mini Jack 25px" );

        output3 = new VoltageAudioJack( "output3", "Output 3", this, JackType.JackType_AudioOutput );
        AddComponent( output3 );
        output3.SetWantsMouseNotifications( false );
        output3.SetPosition( 59, 305 );
        output3.SetSize( 25, 25 );
        output3.SetSkin( "Mini Jack 25px" );

        output4 = new VoltageAudioJack( "output4", "Output 4", this, JackType.JackType_AudioOutput );
        AddComponent( output4 );
        output4.SetWantsMouseNotifications( false );
        output4.SetPosition( 87, 305 );
        output4.SetSize( 25, 25 );
        output4.SetSkin( "Mini Jack 25px" );
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
    private VoltageAudioJack output4;
    private VoltageAudioJack output3;
    private VoltageAudioJack output2;
    private VoltageAudioJack output1;
    private VoltageSwitch switch4;
    private VoltageSwitch switch3;
    private VoltageSwitch switch2;
    private VoltageSwitch switch1;
    private VoltageAudioJack inputPre4;
    private VoltageAudioJack inputPre3;
    private VoltageAudioJack inputPre2;
    private VoltageAudioJack inputPre1;
    private VoltageKnob knobTrim4;
    private VoltageKnob knobTrim3;
    private VoltageKnob knobTrim2;
    private VoltageKnob knobTrim1;
    private VoltageButton buttonPhase4;
    private VoltageButton buttonPhase3;
    private VoltageButton buttonPhase2;
    private VoltageButton buttonPhase1;
    private VoltageKnob knobGain4;
    private VoltageKnob knobGain3;
    private VoltageKnob knobGain2;
    private VoltageKnob knobGain1;
    private VoltageLabel labelLocation;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;


    //[user-code-and-variables]    Add your own variables and functions here
    //[/user-code-and-variables]





}

 