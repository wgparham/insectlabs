package com.insectlabs.c234;


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


public class c234 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public c234( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "c2-34 - balanced modulator", ModuleType.ModuleType_Processor, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "45cf8f46ac21461f9e12504acd7f1553" );
    }

void InitializeControls()
{

        moduleDescriptionLabel = new VoltageLabel( "moduleDescriptionLabel", "Module Description", this, "balanced modulator" );
        AddComponent( moduleDescriptionLabel );
        moduleDescriptionLabel.SetWantsMouseNotifications( false );
        moduleDescriptionLabel.SetPosition( 3, 3 );
        moduleDescriptionLabel.SetSize( 109, 23 );
        moduleDescriptionLabel.SetEditable( false, false );
        moduleDescriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        moduleDescriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleDescriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        moduleDescriptionLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        moduleDescriptionLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        moduleDescriptionLabel.SetBorderSize( 4 );
        moduleDescriptionLabel.SetMultiLineEdit( false );
        moduleDescriptionLabel.SetIsNumberEditor( false );
        moduleDescriptionLabel.SetNumberEditorRange( 0, 100 );
        moduleDescriptionLabel.SetNumberEditorInterval( 1 );
        moduleDescriptionLabel.SetNumberEditorUsesMouseWheel( false );
        moduleDescriptionLabel.SetHasCustomTextHoverColor( false );
        moduleDescriptionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleDescriptionLabel.SetFont( "Courier New", 13, true, false );

        manufacturerLogoLabel = new VoltageLabel( "manufacturerLogoLabel", "Manufacturer Logo", this, "iL" );
        AddComponent( manufacturerLogoLabel );
        manufacturerLogoLabel.SetWantsMouseNotifications( false );
        manufacturerLogoLabel.SetPosition( 3, 337 );
        manufacturerLogoLabel.SetSize( 20, 20 );
        manufacturerLogoLabel.SetEditable( false, false );
        manufacturerLogoLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLogoLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLogoLabel.SetColor( new Color( 147, 0, 0, 255 ) );
        manufacturerLogoLabel.SetBkColor( new Color( 51, 51, 51, 255 ) );
        manufacturerLogoLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        manufacturerLogoLabel.SetBorderSize( 2 );
        manufacturerLogoLabel.SetMultiLineEdit( false );
        manufacturerLogoLabel.SetIsNumberEditor( false );
        manufacturerLogoLabel.SetNumberEditorRange( 0, 100 );
        manufacturerLogoLabel.SetNumberEditorInterval( 1 );
        manufacturerLogoLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerLogoLabel.SetHasCustomTextHoverColor( false );
        manufacturerLogoLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerLogoLabel.SetFont( "Courier New", 13, true, false );

        modelNumberLabel = new VoltageLabel( "modelNumberLabel", "Model Number", this, "c2-34" );
        AddComponent( modelNumberLabel );
        modelNumberLabel.SetWantsMouseNotifications( false );
        modelNumberLabel.SetPosition( 30, 335 );
        modelNumberLabel.SetSize( 81, 13 );
        modelNumberLabel.SetEditable( false, false );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        modelNumberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modelNumberLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        modelNumberLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        modelNumberLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        modelNumberLabel.SetBorderSize( 1 );
        modelNumberLabel.SetMultiLineEdit( false );
        modelNumberLabel.SetIsNumberEditor( false );
        modelNumberLabel.SetNumberEditorRange( 0, 100 );
        modelNumberLabel.SetNumberEditorInterval( 1 );
        modelNumberLabel.SetNumberEditorUsesMouseWheel( false );
        modelNumberLabel.SetHasCustomTextHoverColor( false );
        modelNumberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modelNumberLabel.SetFont( "Courier New", 13, true, false );

        signalInputJack = new VoltageAudioJack( "signalInputJack", "X Input", this, JackType.JackType_AudioInput );
        AddComponent( signalInputJack );
        signalInputJack.SetWantsMouseNotifications( false );
        signalInputJack.SetPosition( 10, 70 );
        signalInputJack.SetSize( 37, 37 );
        signalInputJack.SetSkin( "Dark Jack Straight" );

        modulatorInputJack = new VoltageAudioJack( "modulatorInputJack", "Y Input", this, JackType.JackType_AudioInput );
        AddComponent( modulatorInputJack );
        modulatorInputJack.SetWantsMouseNotifications( false );
        modulatorInputJack.SetPosition( 67, 70 );
        modulatorInputJack.SetSize( 37, 37 );
        modulatorInputJack.SetSkin( "Dark Jack Straight" );

        outputJack = new VoltageAudioJack( "outputJack", "Z Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputJack );
        outputJack.SetWantsMouseNotifications( false );
        outputJack.SetPosition( 67, 260 );
        outputJack.SetSize( 37, 37 );
        outputJack.SetSkin( "Rotated Half" );

        signalLabel = new VoltageLabel( "signalLabel", "X Label", this, "X" );
        AddComponent( signalLabel );
        signalLabel.SetWantsMouseNotifications( false );
        signalLabel.SetPosition( 18, 103 );
        signalLabel.SetSize( 20, 20 );
        signalLabel.SetEditable( false, false );
        signalLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        signalLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        signalLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        signalLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        signalLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        signalLabel.SetBorderSize( 0 );
        signalLabel.SetMultiLineEdit( false );
        signalLabel.SetIsNumberEditor( false );
        signalLabel.SetNumberEditorRange( 0, 100 );
        signalLabel.SetNumberEditorInterval( 1 );
        signalLabel.SetNumberEditorUsesMouseWheel( false );
        signalLabel.SetHasCustomTextHoverColor( false );
        signalLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        signalLabel.SetFont( "Arial", 13, true, false );

        modulatorLabel = new VoltageLabel( "modulatorLabel", "Y Label", this, "Y" );
        AddComponent( modulatorLabel );
        modulatorLabel.SetWantsMouseNotifications( false );
        modulatorLabel.SetPosition( 75, 103 );
        modulatorLabel.SetSize( 20, 20 );
        modulatorLabel.SetEditable( false, false );
        modulatorLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        modulatorLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        modulatorLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        modulatorLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        modulatorLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        modulatorLabel.SetBorderSize( 0 );
        modulatorLabel.SetMultiLineEdit( false );
        modulatorLabel.SetIsNumberEditor( false );
        modulatorLabel.SetNumberEditorRange( 0, 100 );
        modulatorLabel.SetNumberEditorInterval( 1 );
        modulatorLabel.SetNumberEditorUsesMouseWheel( false );
        modulatorLabel.SetHasCustomTextHoverColor( false );
        modulatorLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        modulatorLabel.SetFont( "Arial", 13, true, false );

        outputLabel = new VoltageLabel( "outputLabel", "Z Label", this, "Z" );
        AddComponent( outputLabel );
        outputLabel.SetWantsMouseNotifications( false );
        outputLabel.SetPosition( 75, 293 );
        outputLabel.SetSize( 20, 20 );
        outputLabel.SetEditable( false, false );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        outputLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        outputLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        outputLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        outputLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        outputLabel.SetBorderSize( 0 );
        outputLabel.SetMultiLineEdit( false );
        outputLabel.SetIsNumberEditor( false );
        outputLabel.SetNumberEditorRange( 0, 100 );
        outputLabel.SetNumberEditorInterval( 1 );
        outputLabel.SetNumberEditorUsesMouseWheel( false );
        outputLabel.SetHasCustomTextHoverColor( false );
        outputLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        outputLabel.SetFont( "Arial", 13, true, false );

        modeKnob = new VoltageKnob( "modeKnob", "BAL / UNBAL", this, 0.0, 1.0, 0.0 );
        AddComponent( modeKnob );
        modeKnob.SetWantsMouseNotifications( false );
        modeKnob.SetPosition( 32, 145 );
        modeKnob.SetSize( 50, 50 );
        modeKnob.SetSkin( "Pointer Knob Black" );
        modeKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        modeKnob.SetKnobParams( 1, 90 );
        modeKnob.DisplayValueInPercent( false );
        modeKnob.SetKnobAdjustsRing( true );

        unbalancedModeLabel = new VoltageLabel( "unbalancedModeLabel", "UNBAL Mode Label", this, "UNBAL." );
        AddComponent( unbalancedModeLabel );
        unbalancedModeLabel.SetWantsMouseNotifications( false );
        unbalancedModeLabel.SetPosition( 82, 162 );
        unbalancedModeLabel.SetSize( 30, 15 );
        unbalancedModeLabel.SetEditable( false, false );
        unbalancedModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        unbalancedModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        unbalancedModeLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        unbalancedModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        unbalancedModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        unbalancedModeLabel.SetBorderSize( 1 );
        unbalancedModeLabel.SetMultiLineEdit( false );
        unbalancedModeLabel.SetIsNumberEditor( false );
        unbalancedModeLabel.SetNumberEditorRange( 0, 100 );
        unbalancedModeLabel.SetNumberEditorInterval( 1 );
        unbalancedModeLabel.SetNumberEditorUsesMouseWheel( false );
        unbalancedModeLabel.SetHasCustomTextHoverColor( false );
        unbalancedModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        unbalancedModeLabel.SetFont( "<Sans-Serif>", 10, false, false );

        balancedModeLabel = new VoltageLabel( "balancedModeLabel", "BAL Mode Label", this, "BAL." );
        AddComponent( balancedModeLabel );
        balancedModeLabel.SetWantsMouseNotifications( false );
        balancedModeLabel.SetPosition( 43, 132 );
        balancedModeLabel.SetSize( 30, 15 );
        balancedModeLabel.SetEditable( false, false );
        balancedModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        balancedModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        balancedModeLabel.SetColor( new Color( 232, 232, 232, 147 ) );
        balancedModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        balancedModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        balancedModeLabel.SetBorderSize( 1 );
        balancedModeLabel.SetMultiLineEdit( false );
        balancedModeLabel.SetIsNumberEditor( false );
        balancedModeLabel.SetNumberEditorRange( 0, 100 );
        balancedModeLabel.SetNumberEditorInterval( 1 );
        balancedModeLabel.SetNumberEditorUsesMouseWheel( false );
        balancedModeLabel.SetHasCustomTextHoverColor( false );
        balancedModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        balancedModeLabel.SetFont( "<Sans-Serif>", 10, false, false );

        manufacturerNameLabel = new VoltageLabel( "manufacturerNameLabel", "Manufacturer Name", this, "insect" );
        AddComponent( manufacturerNameLabel );
        manufacturerNameLabel.SetWantsMouseNotifications( false );
        manufacturerNameLabel.SetPosition( 29, 212 );
        manufacturerNameLabel.SetSize( 56, 10 );
        manufacturerNameLabel.SetEditable( false, false );
        manufacturerNameLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerNameLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerNameLabel.SetColor( new Color( 19, 19, 19, 147 ) );
        manufacturerNameLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manufacturerNameLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerNameLabel.SetBorderSize( 1 );
        manufacturerNameLabel.SetMultiLineEdit( false );
        manufacturerNameLabel.SetIsNumberEditor( false );
        manufacturerNameLabel.SetNumberEditorRange( 0, 100 );
        manufacturerNameLabel.SetNumberEditorInterval( 1 );
        manufacturerNameLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerNameLabel.SetHasCustomTextHoverColor( false );
        manufacturerNameLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerNameLabel.SetFont( "Arial Black", 7, true, false );

        manufacturerLineLabel = new VoltageLabel( "manufacturerLineLabel", "Manufacturer Line", this, "laboratories" );
        AddComponent( manufacturerLineLabel );
        manufacturerLineLabel.SetWantsMouseNotifications( false );
        manufacturerLineLabel.SetPosition( 29, 217 );
        manufacturerLineLabel.SetSize( 56, 10 );
        manufacturerLineLabel.SetEditable( false, false );
        manufacturerLineLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLineLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLineLabel.SetColor( new Color( 19, 19, 19, 147 ) );
        manufacturerLineLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        manufacturerLineLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        manufacturerLineLabel.SetBorderSize( 1 );
        manufacturerLineLabel.SetMultiLineEdit( false );
        manufacturerLineLabel.SetIsNumberEditor( false );
        manufacturerLineLabel.SetNumberEditorRange( 0, 100 );
        manufacturerLineLabel.SetNumberEditorInterval( 1 );
        manufacturerLineLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerLineLabel.SetHasCustomTextHoverColor( false );
        manufacturerLineLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerLineLabel.SetFont( "Arial Black", 7, true, false );

        locationLabel = new VoltageLabel( "locationLabel", "Location", this, "allegheny city, pa" );
        AddComponent( locationLabel );
        locationLabel.SetWantsMouseNotifications( false );
        locationLabel.SetPosition( 29, 222 );
        locationLabel.SetSize( 56, 10 );
        locationLabel.SetEditable( false, false );
        locationLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        locationLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        locationLabel.SetColor( new Color( 19, 19, 19, 147 ) );
        locationLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        locationLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        locationLabel.SetBorderSize( 1 );
        locationLabel.SetMultiLineEdit( false );
        locationLabel.SetIsNumberEditor( false );
        locationLabel.SetNumberEditorRange( 0, 100 );
        locationLabel.SetNumberEditorInterval( 1 );
        locationLabel.SetNumberEditorUsesMouseWheel( false );
        locationLabel.SetHasCustomTextHoverColor( false );
        locationLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        locationLabel.SetFont( "Arial Black", 7, true, false );

        yearLabel = new VoltageLabel( "yearLabel", "Year", this, "— 1935 —" );
        AddComponent( yearLabel );
        yearLabel.SetWantsMouseNotifications( false );
        yearLabel.SetPosition( 29, 227 );
        yearLabel.SetSize( 56, 10 );
        yearLabel.SetEditable( false, false );
        yearLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        yearLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        yearLabel.SetColor( new Color( 19, 19, 19, 147 ) );
        yearLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        yearLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        yearLabel.SetBorderSize( 1 );
        yearLabel.SetMultiLineEdit( false );
        yearLabel.SetIsNumberEditor( false );
        yearLabel.SetNumberEditorRange( 0, 100 );
        yearLabel.SetNumberEditorInterval( 1 );
        yearLabel.SetNumberEditorUsesMouseWheel( false );
        yearLabel.SetHasCustomTextHoverColor( false );
        yearLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        yearLabel.SetFont( "Arial Black", 7, true, false );

        amplitudeKnob = new VoltageKnob( "amplitudeKnob", "Amplitude", this, 0.0, 1.0, 1.0 );
        AddComponent( amplitudeKnob );
        amplitudeKnob.SetWantsMouseNotifications( false );
        amplitudeKnob.SetPosition( 13, 262 );
        amplitudeKnob.SetSize( 35, 35 );
        amplitudeKnob.SetSkin( "Cosmo v2 Med" );
        amplitudeKnob.SetRange( 0.0, 1.0, 1.0, false, 0 );
        amplitudeKnob.SetKnobParams( 215, 145 );
        amplitudeKnob.DisplayValueInPercent( false );
        amplitudeKnob.SetKnobAdjustsRing( true );

        image1 = new VoltageImage( "image1", "image1", this, false );
        AddComponent( image1 );
        image1.SetWantsMouseNotifications( false );
        image1.SetPosition( 41, 236 );
        image1.SetSize( 32, 32 );
        image1.SetCurrentImage( "image image.svg" );
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
        balancedModulator = new BalancedModulatorDsp(modeKnob.GetValue(), amplitudeKnob.GetValue());
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
        balancedModulator.setControls(modeKnob.GetValue(), amplitudeKnob.GetValue());
        balancedModulator.process(readInput(signalInputJack), readInput(modulatorInputJack));
        outputJack.SetValue(balancedModulator.getOutput());
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
        outputJack.SetValue(readInput(signalInputJack));
        if (balancedModulator != null) {
            balancedModulator.onBypassed();
        }
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
        if (component == signalInputJack) {
            return "X Input: signal or carrier input; passes directly to Z in host bypass.";
        }
        if (component == modulatorInputJack) {
            return "Y Input: modulator/control input. BAL accepts bipolar voltage; UNBAL uses 0 to +5 V as its VCA range. DC is preserved.";
        }
        if (component == outputJack) {
            return "Z Output: processed X signal. Amplitude follows the modulation stage.";
        }
        if (component == modeKnob) {
    return "BAL / UNBAL ("
            + Math.round(modeKnob.GetValue() * 100.0)
            + "% BAL): 0% = UNBAL VCA; 100% = carrier-suppressed "
            + "four-quadrant BAL. Mid-travel adds up to 6% feedthrough.";
}
        if (component == amplitudeKnob) {
    return "Amplitude ("
            + Math.round(amplitudeKnob.GetValue() * 100.0)
            + "%): linear, smoothed output level after modulation; "
            + "enter 0-100.";
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
if (!Double.isFinite(newValue)) {
    return;
}

if (component == modeKnob ||
        component == amplitudeKnob) {

    super.EditComponentValue(
            component,
            clamp(newValue / 100.0, 0.0, 1.0),
            newText
    );

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
    private VoltageImage image1;
    private VoltageKnob amplitudeKnob;
    private VoltageLabel yearLabel;
    private VoltageLabel locationLabel;
    private VoltageLabel manufacturerLineLabel;
    private VoltageLabel manufacturerNameLabel;
    private VoltageLabel balancedModeLabel;
    private VoltageLabel unbalancedModeLabel;
    private VoltageKnob modeKnob;
    private VoltageLabel outputLabel;
    private VoltageLabel modulatorLabel;
    private VoltageLabel signalLabel;
    private VoltageAudioJack outputJack;
    private VoltageAudioJack modulatorInputJack;
    private VoltageAudioJack signalInputJack;
    private VoltageLabel modelNumberLabel;
    private VoltageLabel manufacturerLogoLabel;
    private VoltageLabel moduleDescriptionLabel;


    //[user-code-and-variables]    Add your own variables and functions here
    // v1.0.0 — approved BAL/UNBAL and dirty-zone DSP.
    // BAL is a carrier-suppressed four-quadrant product; UNBAL is a unipolar VCA.
    private static final double INPUT_REFERENCE_VOLTS = 5.0;
    private static final int MANUAL_RAMP_SAMPLES = 240; // 5 ms at 48 kHz
    private static final double MAX_CARRIER_LEAK = 0.06;
    private static final double MAX_MODULATOR_LEAK = 0.06;

    private BalancedModulatorDsp balancedModulator;

    private static double readInput(VoltageAudioJack jack) {
    if (!jack.IsConnected()) {
        return 0.0;
    }

    return clamp(
            finiteOrZero(jack.GetValue()),
            -1.0e6,
            1.0e6
    );
}

    private static double finiteOrZero(double value) {
        return Double.isFinite(value) ? value : 0.0;
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static double smoothstep(double value) {
        double t = clamp(value, 0.0, 1.0);
        return t * t * (3.0 - 2.0 * t);
    }

    private static final class BalancedModulatorDsp {
        private final ControlRamp mode;
        private final ControlRamp outputLevel;
        private boolean resumePending = true;
        private double output;

        private BalancedModulatorDsp(double initialMode, double initialOutputLevel) {
            mode = new ControlRamp(clamp(finiteOrZero(initialMode), 0.0, 1.0));
            outputLevel = new ControlRamp(clamp(finiteOrZero(initialOutputLevel), 0.0, 1.0));
        }

        private void setControls(double modeValue, double outputLevelValue) {
            mode.setTarget(clamp(finiteOrZero(modeValue), 0.0, 1.0));
            outputLevel.setTarget(clamp(finiteOrZero(outputLevelValue), 0.0, 1.0));
        }

        private void process(double x, double y) {
            if (resumePending) {
                mode.snap();
                outputLevel.snap();
                resumePending = false;
            }

            double dial = mode.next(); // 0 = UNBAL / VCA, 1 = BAL / suppressed carrier
            double level = outputLevel.next();

            double ringProduct = x * (y / INPUT_REFERENCE_VOLTS);
            double vcaGain = clamp(y / INPUT_REFERENCE_VOLTS, 0.0, 1.0);
            double vcaSignal = x * vcaGain;

            // Broad endpoint regions retain stable, forgiving BAL and UNBAL operation.
            double balancedWeight = smoothstep((dial - 0.22) / 0.56);
            double signal = vcaSignal + balancedWeight * (ringProduct - vcaSignal);

            // Intentional mid-travel feedthrough models imperfect balance/isolation.
            // DC in Y is preserved in the MODULATOR-leak zone.
            double carrierLeak = MAX_CARRIER_LEAK
                    * smoothstep((dial - 0.33) / 0.12)
                    * (1.0 - smoothstep((dial - 0.65) / 0.12));
            double modulatorLeak = MAX_MODULATOR_LEAK
                    * smoothstep((dial - 0.17) / 0.12)
                    * (1.0 - smoothstep((dial - 0.49) / 0.12));

            output = level * (signal + carrierLeak * x + modulatorLeak * y);
        }

        private double getOutput() {
            return output;
        }

        private void onBypassed() {
            resumePending = true;
        }
    }

    private static final class ControlRamp {
        private double current;
        private double target;
        private double step;
        private int remainingSamples;

        private ControlRamp(double initialValue) {
            current = target = initialValue;
        }

        private void setTarget(double newTarget) {
            if (newTarget == target) {
                return;
            }
            target = newTarget;
            step = (target - current) / MANUAL_RAMP_SAMPLES;
            remainingSamples = MANUAL_RAMP_SAMPLES;
        }

        private double next() {
            if (remainingSamples > 0) {
                current += step;
                if (--remainingSamples == 0) {
                    current = target;
                }
            }
            return current;
        }

        private void snap() {
            current = target;
            remainingSamples = 0;
        }
    }
    //[/user-code-and-variables]
}

 