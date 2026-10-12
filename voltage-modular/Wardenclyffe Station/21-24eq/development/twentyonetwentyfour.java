package com.insectlabs.twentyonetwentyfour;


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


public class twentyonetwentyfour extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public twentyonetwentyfour( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "21-24eq - frequency corrector", ModuleType.ModuleType_Filters, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "8732d9e53ff74d9289764e426afdf181" );
    }

void InitializeControls()
{

        jackOutput = new VoltageAudioJack( "jackOutput", "Output", this, JackType.JackType_AudioOutput );
        AddComponent( jackOutput );
        jackOutput.SetWantsMouseNotifications( false );
        jackOutput.SetPosition( 73, 215 );
        jackOutput.SetSize( 37, 37 );
        jackOutput.SetSkin( "Rotated Half" );

        jackInput = new VoltageAudioJack( "jackInput", "Input", this, JackType.JackType_AudioInput );
        AddComponent( jackInput );
        jackInput.SetWantsMouseNotifications( false );
        jackInput.SetPosition( 5, 215 );
        jackInput.SetSize( 37, 37 );
        jackInput.SetSkin( "Dark Jack Straight" );

        labelBass = new VoltageLabel( "labelBass", "Bass Label", this, "BASS" );
        AddComponent( labelBass );
        labelBass.SetWantsMouseNotifications( false );
        labelBass.SetPosition( 0, 205 );
        labelBass.SetSize( 115, 15 );
        labelBass.SetEditable( false, false );
        labelBass.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelBass.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelBass.SetColor( new Color( 147, 0, 0, 255 ) );
        labelBass.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelBass.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelBass.SetBorderSize( 1 );
        labelBass.SetMultiLineEdit( false );
        labelBass.SetIsNumberEditor( false );
        labelBass.SetNumberEditorRange( 0, 100 );
        labelBass.SetNumberEditorInterval( 1 );
        labelBass.SetNumberEditorUsesMouseWheel( false );
        labelBass.SetHasCustomTextHoverColor( false );
        labelBass.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelBass.SetFont( "Arial Black", 14, false, false );

        labelTreble = new VoltageLabel( "labelTreble", "Treble Label", this, "TREBLE" );
        AddComponent( labelTreble );
        labelTreble.SetWantsMouseNotifications( false );
        labelTreble.SetPosition( 0, 110 );
        labelTreble.SetSize( 115, 15 );
        labelTreble.SetEditable( false, false );
        labelTreble.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelTreble.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelTreble.SetColor( new Color( 147, 0, 0, 255 ) );
        labelTreble.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelTreble.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelTreble.SetBorderSize( 1 );
        labelTreble.SetMultiLineEdit( false );
        labelTreble.SetIsNumberEditor( false );
        labelTreble.SetNumberEditorRange( 0, 100 );
        labelTreble.SetNumberEditorInterval( 1 );
        labelTreble.SetNumberEditorUsesMouseWheel( false );
        labelTreble.SetHasCustomTextHoverColor( false );
        labelTreble.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelTreble.SetFont( "Arial Black", 14, false, false );

        image1 = new VoltageImage( "image1", "Panel Branding", this, false );
        AddComponent( image1 );
        image1.SetWantsMouseNotifications( false );
        image1.SetPosition( 21, 285 );
        image1.SetSize( 73, 54 );
        image1.SetCurrentImage( "image image.png" );

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Module Description", this, "frequency corrector" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 3 );
        descriptionLabel.SetSize( 109, 23 );
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

        label2124eq = new VoltageLabel( "label2124eq", "21-24eq Panel Title", this, "21-24eq" );
        AddComponent( label2124eq );
        label2124eq.SetWantsMouseNotifications( false );
        label2124eq.SetPosition( 33, 335 );
        label2124eq.SetSize( 79, 13 );
        label2124eq.SetEditable( false, false );
        label2124eq.SetJustificationFlags( VoltageLabel.Justification.Right );
        label2124eq.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        label2124eq.SetColor( new Color( 232, 232, 232, 255 ) );
        label2124eq.SetBkColor( new Color( 85, 85, 85, 0 ) );
        label2124eq.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        label2124eq.SetBorderSize( 0 );
        label2124eq.SetMultiLineEdit( false );
        label2124eq.SetIsNumberEditor( false );
        label2124eq.SetNumberEditorRange( 0, 100 );
        label2124eq.SetNumberEditorInterval( 1 );
        label2124eq.SetNumberEditorUsesMouseWheel( false );
        label2124eq.SetHasCustomTextHoverColor( false );
        label2124eq.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        label2124eq.SetFont( "Courier New", 13, true, false );

        labelFrequency = new VoltageLabel( "labelFrequency", "Frequency Label", this, "FREQUENCY SHIFT" );
        AddComponent( labelFrequency );
        labelFrequency.SetWantsMouseNotifications( false );
        labelFrequency.SetPosition( 0, 274 );
        labelFrequency.SetSize( 115, 13 );
        labelFrequency.SetEditable( false, false );
        labelFrequency.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelFrequency.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelFrequency.SetColor( new Color( 232, 232, 232, 255 ) );
        labelFrequency.SetBkColor( new Color( 85, 85, 85, 0 ) );
        labelFrequency.SetBorderColor( new Color( 85, 85, 85, 0 ) );
        labelFrequency.SetBorderSize( 0 );
        labelFrequency.SetMultiLineEdit( false );
        labelFrequency.SetIsNumberEditor( false );
        labelFrequency.SetNumberEditorRange( 0, 100 );
        labelFrequency.SetNumberEditorInterval( 1 );
        labelFrequency.SetNumberEditorUsesMouseWheel( false );
        labelFrequency.SetHasCustomTextHoverColor( false );
        labelFrequency.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelFrequency.SetFont( "Courier New", 9, true, false );

        knobTreble = new VoltageKnob( "knobTreble", "Treble", this, -1.0, 1.0, 0 );
        AddComponent( knobTreble );
        knobTreble.SetWantsMouseNotifications( false );
        knobTreble.SetPosition( 15, 30 );
        knobTreble.SetSize( 81, 81 );
        knobTreble.SetSkin( "Cosmo Large Black" );
        knobTreble.SetRange( -1.0, 1.0, 0, false, 0 );
        knobTreble.SetKnobParams( 215, 145 );
        knobTreble.DisplayValueInPercent( false );
        knobTreble.SetKnobAdjustsRing( true );

        knobBass = new VoltageKnob( "knobBass", "Bass", this, -1.0, 1.0, 0.0 );
        AddComponent( knobBass );
        knobBass.SetWantsMouseNotifications( false );
        knobBass.SetPosition( 15, 125 );
        knobBass.SetSize( 81, 81 );
        knobBass.SetSkin( "Cosmo Large Black" );
        knobBass.SetRange( -1.0, 1.0, 0.0, false, 0 );
        knobBass.SetKnobParams( 215, 145 );
        knobBass.DisplayValueInPercent( false );
        knobBass.SetKnobAdjustsRing( true );

        switchFrequency = new VoltageSwitch( "switchFrequency", "Frequency Profile", this, 0 );
        AddComponent( switchFrequency );
        switchFrequency.SetWantsMouseNotifications( false );
        switchFrequency.SetPosition( 38, 258 );
        switchFrequency.SetSize( 40, 15 );
        switchFrequency.SetSkin( "3-State Slide Horizontal" );
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
        resetRequested = true;

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
                resetRequested = true;
            }
            break;
        
            case Variation_Loading_Start:    // sent when a variation is about to load
            {
            }
            break;
        
            case Variation_Loading_Finish:   // sent when a variation has just finished loading
            {
                resetRequested = true;
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
                resetRequested = true;
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
        if( resetRequested )
        {
            toneCore.reset();
            resetRequested = false;
        }
        int frequencyProfile = (int)Math.round(clamp(switchFrequency.GetValue(), 0.0, 2.0));
        double output = toneCore.process(
                readInput(jackInput),
                knobBass.GetValue(),
                knobTreble.GetValue(),
                frequencyProfile);
        jackOutput.SetValue(output);
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
        jackOutput.SetValue(readInput(jackInput));
        resetRequested = true;
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
        if( component == knobBass ) return "Bass: counterclockwise cuts; clockwise boosts. The center retains the passive circuit's loss and contour.";
        if( component == knobTreble ) return "Treble: counterclockwise cuts; clockwise boosts. The center retains the passive circuit's loss and contour.";
        if( component == switchFrequency )
        {
            int profile = (int)Math.round(clamp(switchFrequency.GetValue(), 0.0, 2.0));
            String[] names = {"Reference", "Dark", "Extra Dark"};
            double[] bassCorners = {70.0, 35.0, 17.5};
            double[] trebleCorners = {7000.0, 3500.0, 1750.0};
            return String.format(java.util.Locale.ROOT,
                    "Impedance / frequency profile: %s; bass %.1f Hz, treble %.0f Hz",
                    names[profile], bassCorners[profile], trebleCorners[profile]);
        }
        if( component == jackInput ) return "Input: mono audio signal.";
        if( component == jackOutput ) return "Output: mono audio signal after tone shaping and passive insertion loss.";
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
    private VoltageSwitch switchFrequency;
    private VoltageKnob knobBass;
    private VoltageKnob knobTreble;
    private VoltageLabel labelFrequency;
    private VoltageLabel label2124eq;
    private VoltageLabel descriptionLabel;
    private VoltageImage image1;
    private VoltageLabel labelTreble;
    private VoltageLabel labelBass;
    private VoltageAudioJack jackInput;
    private VoltageAudioJack jackOutput;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double INPUT_ROLLOFF_HZ = 10.0;
    private static final double PASSIVE_INSERTION_DB = -20.0;
    private static final double LOW_BASE_SHELF_DB = 8.0;
    private static final double HIGH_BASE_SHELF_DB = 5.0;
    private static final double DRIVE_START_VOLTS = 6.0;
    private static final double DRIVE_SPAN_VOLTS = 12.0;
    private static final double CEILING_KNEE_VOLTS = 20.0;
    private static final double CEILING_LIMIT_VOLTS = 24.0;
    private static final double CONTROL_SMOOTH_SECONDS = 0.005;

    private volatile boolean resetRequested;
    private final PassiveToneCore toneCore = new PassiveToneCore();

    private static double readInput(VoltageAudioJack jack)
    {
        if( !jack.IsConnected() ) return 0.0;
        double value = jack.GetValue();
        if( !Double.isFinite(value) ) return 0.0;
        return Math.max(-1.0e6, Math.min(1.0e6, value));
    }

    private static double clamp(double value, double low, double high)
    {
        if( !Double.isFinite(value) ) return 0.0;
        return Math.max(low, Math.min(high, value));
    }

    private static double dbToGain(double db)
    {
        return Math.pow(10.0, db / 20.0);
    }

    private static double lowpassCoefficient(double frequencyHz)
    {
        return 1.0 - Math.exp(-2.0 * Math.PI * frequencyHz / SAMPLE_RATE);
    }

    private static double softDrive(double input)
    {
        double magnitude = Math.abs(input);
        if( magnitude <= DRIVE_START_VOLTS ) return input;
        double shaped = DRIVE_START_VOLTS + DRIVE_SPAN_VOLTS
                * Math.tanh((magnitude - DRIVE_START_VOLTS) / DRIVE_SPAN_VOLTS);
        return Math.copySign(shaped, input);
    }

    private static double safetyCeiling(double input)
    {
        double magnitude = Math.abs(input);
        if( magnitude <= CEILING_KNEE_VOLTS ) return input;
        double shaped = CEILING_KNEE_VOLTS + (CEILING_LIMIT_VOLTS - CEILING_KNEE_VOLTS)
                * Math.tanh((magnitude - CEILING_KNEE_VOLTS)
                / (CEILING_LIMIT_VOLTS - CEILING_KNEE_VOLTS));
        return Math.copySign(shaped, input);
    }

    /** First-pass passive-voiced DSP approximation; the circuit response is deliberately lossy. */
    private static final class PassiveToneCore
    {
        private final double inputHpCoefficient = Math.exp(
                -2.0 * Math.PI * INPUT_ROLLOFF_HZ / SAMPLE_RATE);
        private final double controlSmoothing = 1.0 - Math.exp(
                -1.0 / (CONTROL_SMOOTH_SECONDS * SAMPLE_RATE));

        private double previousInput;
        private double inputHpState;
        private double bassLowState;
        private double trebleLowState;
        private double bassControl;
        private double trebleControl;
        private double bassCornerHz;
        private double trebleCornerHz;
        private boolean initialized;

        double process(double input, double bassKnob, double trebleKnob, int frequencyProfile)
        {
            // The panel uses bipolar knobs: counterclockwise cuts, clockwise boosts.
            // The shelf math below uses 0=boost, 1=cut, so invert the panel values.
            double bassTarget = clamp((1.0 - bassKnob) * 0.5, 0.0, 1.0);
            double trebleTarget = clamp((1.0 - trebleKnob) * 0.5, 0.0, 1.0);
            int profile = (int)clamp(frequencyProfile, 0, 2);
            double bassCornerTarget = profile == 0 ? 70.0 : (profile == 1 ? 35.0 : 17.5);
            double trebleCornerTarget = profile == 0 ? 7000.0 : (profile == 1 ? 3500.0 : 1750.0);

            if( !initialized )
            {
                bassControl = bassTarget;
                trebleControl = trebleTarget;
                bassCornerHz = bassCornerTarget;
                trebleCornerHz = trebleCornerTarget;
                previousInput = input;
                initialized = true;
            }
            else
            {
                bassControl += controlSmoothing * (bassTarget - bassControl);
                trebleControl += controlSmoothing * (trebleTarget - trebleControl);
                bassCornerHz += controlSmoothing * (bassCornerTarget - bassCornerHz);
                trebleCornerHz += controlSmoothing * (trebleCornerTarget - trebleCornerHz);
            }

            inputHpState = inputHpCoefficient * (inputHpState + input - previousInput);
            previousInput = input;
            double driven = softDrive(inputHpState);

            double bassGainDb = bassControl < 0.5
                    ? (0.5 - bassControl) * 20.0
                    : (0.5 - bassControl) * 58.0;
            double trebleGainDb = trebleControl < 0.5
                    ? (0.5 - trebleControl) * 24.0
                    : (0.5 - trebleControl) * 48.0;

            double bassGain = dbToGain(LOW_BASE_SHELF_DB + bassGainDb);
            double bassAlpha = lowpassCoefficient(bassCornerHz);
            bassLowState += bassAlpha * (driven - bassLowState);
            double shaped = driven + (bassGain - 1.0) * bassLowState;

            double trebleGain = dbToGain(HIGH_BASE_SHELF_DB + trebleGainDb);
            double trebleAlpha = lowpassCoefficient(trebleCornerHz);
            trebleLowState += trebleAlpha * (shaped - trebleLowState);
            shaped += (trebleGain - 1.0) * (shaped - trebleLowState);

            double passiveOutput = shaped * dbToGain(PASSIVE_INSERTION_DB);
            return safetyCeiling(passiveOutput);
        }

        void reset()
        {
            previousInput = 0.0;
            inputHpState = 0.0;
            bassLowState = 0.0;
            trebleLowState = 0.0;
            initialized = false;
        }
    }
    //[/user-code-and-variables]
}

 
