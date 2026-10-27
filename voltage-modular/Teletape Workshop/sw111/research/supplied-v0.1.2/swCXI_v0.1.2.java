package com.insectlabs.swcxi;


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


public class swcxi extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public swcxi( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "swCXI - Sequential Switch", ModuleType.ModuleType_Switches, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "c97c3f4f724246a0ba32407d3499dcc9" );
    }

void InitializeControls()
{

        input10 = new VoltageAudioJack( "input10", "Input 10", this, JackType.JackType_AudioInput );
        AddComponent( input10 );
        input10.SetWantsMouseNotifications( false );
        input10.SetPosition( 15, 285 );
        input10.SetSize( 25, 25 );
        input10.SetSkin( "Jack Round 25px" );

        labelFooter = new VoltageLabel( "labelFooter", "Footer", this, "sequential switch" );
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
        imageTTW.SetPosition( 71, 315 );
        imageTTW.SetSize( 41, 13 );
        imageTTW.SetCurrentImage( "image image(3).svg" );

        labelNumber = new VoltageLabel( "labelNumber", "Model Number Label", this, "swCXI" );
        AddComponent( labelNumber );
        labelNumber.SetWantsMouseNotifications( false );
        labelNumber.SetPosition( 3, 3 );
        labelNumber.SetSize( 46, 15 );
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

        input1 = new VoltageAudioJack( "input1", "Input 1", this, JackType.JackType_AudioInput );
        AddComponent( input1 );
        input1.SetWantsMouseNotifications( false );
        input1.SetPosition( 15, 60 );
        input1.SetSize( 25, 25 );
        input1.SetSkin( "Jack Round 25px" );

        input2 = new VoltageAudioJack( "input2", "Input 2", this, JackType.JackType_AudioInput );
        AddComponent( input2 );
        input2.SetWantsMouseNotifications( false );
        input2.SetPosition( 15, 85 );
        input2.SetSize( 25, 25 );
        input2.SetSkin( "Jack Round 25px" );

        input3 = new VoltageAudioJack( "input3", "Input 3", this, JackType.JackType_AudioInput );
        AddComponent( input3 );
        input3.SetWantsMouseNotifications( false );
        input3.SetPosition( 15, 110 );
        input3.SetSize( 25, 25 );
        input3.SetSkin( "Jack Round 25px" );

        input4 = new VoltageAudioJack( "input4", "Input 4", this, JackType.JackType_AudioInput );
        AddComponent( input4 );
        input4.SetWantsMouseNotifications( false );
        input4.SetPosition( 15, 135 );
        input4.SetSize( 25, 25 );
        input4.SetSkin( "Jack Round 25px" );

        input5 = new VoltageAudioJack( "input5", "Input 5", this, JackType.JackType_AudioInput );
        AddComponent( input5 );
        input5.SetWantsMouseNotifications( false );
        input5.SetPosition( 15, 160 );
        input5.SetSize( 25, 25 );
        input5.SetSkin( "Jack Round 25px" );

        input6 = new VoltageAudioJack( "input6", "Input 6", this, JackType.JackType_AudioInput );
        AddComponent( input6 );
        input6.SetWantsMouseNotifications( false );
        input6.SetPosition( 15, 185 );
        input6.SetSize( 25, 25 );
        input6.SetSkin( "Jack Round 25px" );

        input7 = new VoltageAudioJack( "input7", "Input 7", this, JackType.JackType_AudioInput );
        AddComponent( input7 );
        input7.SetWantsMouseNotifications( false );
        input7.SetPosition( 15, 210 );
        input7.SetSize( 25, 25 );
        input7.SetSkin( "Jack Round 25px" );

        input8 = new VoltageAudioJack( "input8", "Input 8", this, JackType.JackType_AudioInput );
        AddComponent( input8 );
        input8.SetWantsMouseNotifications( false );
        input8.SetPosition( 15, 235 );
        input8.SetSize( 25, 25 );
        input8.SetSkin( "Jack Round 25px" );

        input9 = new VoltageAudioJack( "input9", "Input 9", this, JackType.JackType_AudioInput );
        AddComponent( input9 );
        input9.SetWantsMouseNotifications( false );
        input9.SetPosition( 15, 260 );
        input9.SetSize( 25, 25 );
        input9.SetSkin( "Jack Round 25px" );

        output1 = new VoltageAudioJack( "output1", "Output 1", this, JackType.JackType_AudioOutput );
        AddComponent( output1 );
        output1.SetWantsMouseNotifications( false );
        output1.SetPosition( 75, 60 );
        output1.SetSize( 25, 25 );
        output1.SetSkin( "Mini Jack 25px" );

        output2 = new VoltageAudioJack( "output2", "Output 2", this, JackType.JackType_AudioOutput );
        AddComponent( output2 );
        output2.SetWantsMouseNotifications( false );
        output2.SetPosition( 75, 85 );
        output2.SetSize( 25, 25 );
        output2.SetSkin( "Mini Jack 25px" );

        output3 = new VoltageAudioJack( "output3", "Output 3", this, JackType.JackType_AudioOutput );
        AddComponent( output3 );
        output3.SetWantsMouseNotifications( false );
        output3.SetPosition( 75, 110 );
        output3.SetSize( 25, 25 );
        output3.SetSkin( "Mini Jack 25px" );

        output4 = new VoltageAudioJack( "output4", "Output 4", this, JackType.JackType_AudioOutput );
        AddComponent( output4 );
        output4.SetWantsMouseNotifications( false );
        output4.SetPosition( 75, 135 );
        output4.SetSize( 25, 25 );
        output4.SetSkin( "Mini Jack 25px" );

        output5 = new VoltageAudioJack( "output5", "Output 5", this, JackType.JackType_AudioOutput );
        AddComponent( output5 );
        output5.SetWantsMouseNotifications( false );
        output5.SetPosition( 75, 160 );
        output5.SetSize( 25, 25 );
        output5.SetSkin( "Mini Jack 25px" );

        output6 = new VoltageAudioJack( "output6", "Output 6", this, JackType.JackType_AudioOutput );
        AddComponent( output6 );
        output6.SetWantsMouseNotifications( false );
        output6.SetPosition( 75, 185 );
        output6.SetSize( 25, 25 );
        output6.SetSkin( "Mini Jack 25px" );

        output7 = new VoltageAudioJack( "output7", "Output 7", this, JackType.JackType_AudioOutput );
        AddComponent( output7 );
        output7.SetWantsMouseNotifications( false );
        output7.SetPosition( 75, 210 );
        output7.SetSize( 25, 25 );
        output7.SetSkin( "Mini Jack 25px" );

        output8 = new VoltageAudioJack( "output8", "Output 8", this, JackType.JackType_AudioOutput );
        AddComponent( output8 );
        output8.SetWantsMouseNotifications( false );
        output8.SetPosition( 75, 235 );
        output8.SetSize( 25, 25 );
        output8.SetSkin( "Mini Jack 25px" );

        output9 = new VoltageAudioJack( "output9", "Output 9", this, JackType.JackType_AudioOutput );
        AddComponent( output9 );
        output9.SetWantsMouseNotifications( false );
        output9.SetPosition( 75, 260 );
        output9.SetSize( 25, 25 );
        output9.SetSkin( "Mini Jack 25px" );

        output10 = new VoltageAudioJack( "output10", "Output 10", this, JackType.JackType_AudioOutput );
        AddComponent( output10 );
        output10.SetWantsMouseNotifications( false );
        output10.SetPosition( 75, 285 );
        output10.SetSize( 25, 25 );
        output10.SetSkin( "Mini Jack 25px" );

        outputCommon = new VoltageAudioJack( "outputCommon", "Common Output", this, JackType.JackType_AudioOutput );
        AddComponent( outputCommon );
        outputCommon.SetWantsMouseNotifications( false );
        outputCommon.SetPosition( 45, 310 );
        outputCommon.SetSize( 25, 25 );
        outputCommon.SetSkin( "Mini Jack 25px" );

        inputCommon = new VoltageAudioJack( "inputCommon", "Common Input", this, JackType.JackType_AudioInput );
        AddComponent( inputCommon );
        inputCommon.SetWantsMouseNotifications( false );
        inputCommon.SetPosition( 45, 35 );
        inputCommon.SetSize( 25, 25 );
        inputCommon.SetSkin( "Jack Round 25px" );

        lamp1 = new VoltageLED( "lamp1", "Lamp 1", this );
        AddComponent( lamp1 );
        lamp1.SetWantsMouseNotifications( false );
        lamp1.SetPosition( 50, 65 );
        lamp1.SetSize( 16, 16 );
        lamp1.SetSkin( "2500 Lamp Green" );

        lamp2 = new VoltageLED( "lamp2", "Lamp 2", this );
        AddComponent( lamp2 );
        lamp2.SetWantsMouseNotifications( false );
        lamp2.SetPosition( 50, 90 );
        lamp2.SetSize( 16, 16 );
        lamp2.SetSkin( "2500 Lamp Green" );

        lamp3 = new VoltageLED( "lamp3", "Lamp 3", this );
        AddComponent( lamp3 );
        lamp3.SetWantsMouseNotifications( false );
        lamp3.SetPosition( 50, 115 );
        lamp3.SetSize( 16, 16 );
        lamp3.SetSkin( "2500 Lamp Green" );

        lamp4 = new VoltageLED( "lamp4", "Lamp 4", this );
        AddComponent( lamp4 );
        lamp4.SetWantsMouseNotifications( false );
        lamp4.SetPosition( 50, 140 );
        lamp4.SetSize( 16, 16 );
        lamp4.SetSkin( "2500 Lamp Green" );

        lamp5 = new VoltageLED( "lamp5", "Lamp 5", this );
        AddComponent( lamp5 );
        lamp5.SetWantsMouseNotifications( false );
        lamp5.SetPosition( 50, 165 );
        lamp5.SetSize( 16, 16 );
        lamp5.SetSkin( "2500 Lamp Green" );

        lamp6 = new VoltageLED( "lamp6", "Lamp 6", this );
        AddComponent( lamp6 );
        lamp6.SetWantsMouseNotifications( false );
        lamp6.SetPosition( 50, 190 );
        lamp6.SetSize( 16, 16 );
        lamp6.SetSkin( "2500 Lamp Green" );

        lamp7 = new VoltageLED( "lamp7", "Lamp 7", this );
        AddComponent( lamp7 );
        lamp7.SetWantsMouseNotifications( false );
        lamp7.SetPosition( 50, 215 );
        lamp7.SetSize( 16, 16 );
        lamp7.SetSkin( "2500 Lamp Green" );

        lamp8 = new VoltageLED( "lamp8", "Lamp 8", this );
        AddComponent( lamp8 );
        lamp8.SetWantsMouseNotifications( false );
        lamp8.SetPosition( 50, 240 );
        lamp8.SetSize( 16, 16 );
        lamp8.SetSkin( "2500 Lamp Green" );

        lamp9 = new VoltageLED( "lamp9", "Lamp 9", this );
        AddComponent( lamp9 );
        lamp9.SetWantsMouseNotifications( false );
        lamp9.SetPosition( 50, 265 );
        lamp9.SetSize( 16, 16 );
        lamp9.SetSkin( "2500 Lamp Green" );

        lamp10 = new VoltageLED( "lamp10", "Lamp 10", this );
        AddComponent( lamp10 );
        lamp10.SetWantsMouseNotifications( false );
        lamp10.SetPosition( 50, 290 );
        lamp10.SetSize( 16, 16 );
        lamp10.SetSkin( "2500 Lamp Green" );

        knobSelect = new VoltageKnob( "knobSelect", "Select Knob", this, 1.00, 10.00, 10 );
        AddComponent( knobSelect );
        knobSelect.SetWantsMouseNotifications( false );
        knobSelect.SetPosition( 24, 19 );
        knobSelect.SetSize( 17, 17 );
        knobSelect.SetSkin( "ARP2500 Sm Red" );
        knobSelect.SetRange( 1.00, 10.00, 10, false, 10 );
        knobSelect.SetKnobParams( 215, 145 );
        knobSelect.DisplayValueInPercent( false );
        knobSelect.SetKnobAdjustsRing( true );

        inputSelect = new VoltageAudioJack( "inputSelect", "Select CV Input", this, JackType.JackType_AudioInput );
        AddComponent( inputSelect );
        inputSelect.SetWantsMouseNotifications( false );
        inputSelect.SetPosition( 4, 33 );
        inputSelect.SetSize( 25, 25 );
        inputSelect.SetSkin( "Jack Round 25px" );

        inputClock = new VoltageAudioJack( "inputClock", "Clock Input", this, JackType.JackType_AudioInput );
        AddComponent( inputClock );
        inputClock.SetWantsMouseNotifications( false );
        inputClock.SetPosition( 65, 4 );
        inputClock.SetSize( 25, 25 );
        inputClock.SetSkin( "Jack Round 25px" );

        inputReset = new VoltageAudioJack( "inputReset", "Reset Input", this, JackType.JackType_AudioInput );
        AddComponent( inputReset );
        inputReset.SetWantsMouseNotifications( false );
        inputReset.SetPosition( 86, 24 );
        inputReset.SetSize( 25, 25 );
        inputReset.SetSkin( "Jack Round 25px" );

        labelSelect = new VoltageLabel( "labelSelect", "Select Label", this, "SEL" );
        AddComponent( labelSelect );
        labelSelect.SetWantsMouseNotifications( false );
        labelSelect.SetPosition( 5, 18 );
        labelSelect.SetSize( 21, 20 );
        labelSelect.SetEditable( false, false );
        labelSelect.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelSelect.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelSelect.SetColor( new Color( 42, 42, 42, 255 ) );
        labelSelect.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelSelect.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelSelect.SetBorderSize( 1 );
        labelSelect.SetMultiLineEdit( false );
        labelSelect.SetIsNumberEditor( false );
        labelSelect.SetNumberEditorRange( 0, 100 );
        labelSelect.SetNumberEditorInterval( 1 );
        labelSelect.SetNumberEditorUsesMouseWheel( false );
        labelSelect.SetHasCustomTextHoverColor( false );
        labelSelect.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelSelect.SetFont( "Arial Black", 9, true, false );

        labelClock = new VoltageLabel( "labelClock", "Clock Label", this, "CLK" );
        AddComponent( labelClock );
        labelClock.SetWantsMouseNotifications( false );
        labelClock.SetPosition( 47, 7 );
        labelClock.SetSize( 21, 20 );
        labelClock.SetEditable( false, false );
        labelClock.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelClock.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelClock.SetColor( new Color( 42, 42, 42, 255 ) );
        labelClock.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelClock.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelClock.SetBorderSize( 1 );
        labelClock.SetMultiLineEdit( false );
        labelClock.SetIsNumberEditor( false );
        labelClock.SetNumberEditorRange( 0, 100 );
        labelClock.SetNumberEditorInterval( 1 );
        labelClock.SetNumberEditorUsesMouseWheel( false );
        labelClock.SetHasCustomTextHoverColor( false );
        labelClock.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelClock.SetFont( "Arial Black", 9, true, false );

        labelReset = new VoltageLabel( "labelReset", "Reset Label", this, "RST" );
        AddComponent( labelReset );
        labelReset.SetWantsMouseNotifications( false );
        labelReset.SetPosition( 88, 42 );
        labelReset.SetSize( 21, 20 );
        labelReset.SetEditable( false, false );
        labelReset.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        labelReset.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        labelReset.SetColor( new Color( 42, 42, 42, 255 ) );
        labelReset.SetBkColor( new Color( 65, 65, 65, 0 ) );
        labelReset.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        labelReset.SetBorderSize( 1 );
        labelReset.SetMultiLineEdit( false );
        labelReset.SetIsNumberEditor( false );
        labelReset.SetNumberEditorRange( 0, 100 );
        labelReset.SetNumberEditorInterval( 1 );
        labelReset.SetNumberEditorUsesMouseWheel( false );
        labelReset.SetHasCustomTextHoverColor( false );
        labelReset.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        labelReset.SetFont( "Arial Black", 9, true, false );
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
        // The core is already reset when it is constructed (SetStateInformation runs after Initialize, verified in Designer).
        stageIn = new VoltageAudioJack[] { input1, input2, input3, input4, input5, input6, input7, input8, input9, input10 };
        stageOut = new VoltageAudioJack[] { output1, output2, output3, output4, output5, output6, output7, output8, output9, output10 };
        setStageLamps( core.selectedStage );
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
                core.reset();
                setStageLamps( core.selectedStage );
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
        if (stageIn == null) {
            return;
        }
        core.update( knobSelect.GetValue(), readJack( inputSelect ), inputSelect.IsConnected(),
            readJack( inputClock ), inputClock.IsConnected(), readJack( inputReset ), inputReset.IsConnected() );
        readStageInputs();
        core.mix( readJack( inputCommon ), inputCommon.IsConnected(), outputCommon.IsConnected(), core.inputs );
        writeOutputs();
        setStageLamps( core.selectedStage );
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
        if (stageIn == null) {
            return;
        }
        core.bypassUpdate( readJack( inputClock ), inputClock.IsConnected(), readJack( inputReset ), inputReset.IsConnected() );
        readStageInputs();
        core.mix( readJack( inputCommon ), inputCommon.IsConnected(), outputCommon.IsConnected(), core.inputs );
        writeOutputs();
        setStageLamps( core.selectedStage );
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
        setStageLamps( core.selectedStage );
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
    private VoltageLabel labelReset;
    private VoltageLabel labelClock;
    private VoltageLabel labelSelect;
    private VoltageAudioJack inputReset;
    private VoltageAudioJack inputClock;
    private VoltageAudioJack inputSelect;
    private VoltageKnob knobSelect;
    private VoltageLED lamp10;
    private VoltageLED lamp9;
    private VoltageLED lamp8;
    private VoltageLED lamp7;
    private VoltageLED lamp6;
    private VoltageLED lamp5;
    private VoltageLED lamp4;
    private VoltageLED lamp3;
    private VoltageLED lamp2;
    private VoltageLED lamp1;
    private VoltageAudioJack inputCommon;
    private VoltageAudioJack outputCommon;
    private VoltageAudioJack output10;
    private VoltageAudioJack output9;
    private VoltageAudioJack output8;
    private VoltageAudioJack output7;
    private VoltageAudioJack output6;
    private VoltageAudioJack output5;
    private VoltageAudioJack output4;
    private VoltageAudioJack output3;
    private VoltageAudioJack output2;
    private VoltageAudioJack output1;
    private VoltageAudioJack input9;
    private VoltageAudioJack input8;
    private VoltageAudioJack input7;
    private VoltageAudioJack input6;
    private VoltageAudioJack input5;
    private VoltageAudioJack input4;
    private VoltageAudioJack input3;
    private VoltageAudioJack input2;
    private VoltageAudioJack input1;
    private VoltageLabel labelNumber;
    private VoltageImage imageTTW;
    private VoltageLabel labelFooter;
    private VoltageAudioJack input10;


    //[user-code-and-variables]    Add your own variables and functions here
    private static double readJack(VoltageAudioJack jack) {
        if (!jack.IsConnected()) return 0.0;
        double value = jack.GetValue();
        return Double.isFinite(value) ? value : 0.0;
    }

    private VoltageAudioJack[] stageIn;
    private VoltageAudioJack[] stageOut;
    /** Only the stage inputs that can reach an output this sample are read; the rest are 0. */
    private void readStageInputs() {
        double[] inputs = core.inputs;
        for (int i = 0; i < SwitchCore.STAGES; i++) {
            inputs[i] = core.needsInput( i ) ? readJack( stageIn[i] ) : 0.0;
        }
    }

    /** Every output is written every sample: a Voltage cable holds two alternating sample slots, so a value written once lands in only one of them. */
    private void writeOutputs() {
        for (int i = 0; i < SwitchCore.STAGES; i++) {
            stageOut[i].SetValue( core.outputs[i] );
        }
        outputCommon.SetValue( core.outCommon );
    }

    private int lastLampStage = -1;

    private void setStageLamps(int selectedStage) {
        if (selectedStage == lastLampStage) return;
        lamp1.SetValue(selectedStage == 0 ? 1.0 : 0.0);
        lamp2.SetValue(selectedStage == 1 ? 1.0 : 0.0);
        lamp3.SetValue(selectedStage == 2 ? 1.0 : 0.0);
        lamp4.SetValue(selectedStage == 3 ? 1.0 : 0.0);
        lamp5.SetValue(selectedStage == 4 ? 1.0 : 0.0);
        lamp6.SetValue(selectedStage == 5 ? 1.0 : 0.0);
        lamp7.SetValue(selectedStage == 6 ? 1.0 : 0.0);
        lamp8.SetValue(selectedStage == 7 ? 1.0 : 0.0);
        lamp9.SetValue(selectedStage == 8 ? 1.0 : 0.0);
        lamp10.SetValue(selectedStage == 9 ? 1.0 : 0.0);
        lastLampStage = selectedStage;
    }

    private static final class SwitchCore {
        static final int STAGES = 10;
        static final int FADE_SAMPLES = 48; // 1 ms at 48 kHz
        static final int EXCLUSIVE_FADE_SAMPLES = FADE_SAMPLES / 2;
        static final double CLOCK_HIGH_V = 3.0;
        static final double CLOCK_LOW_V = 1.8;
        static final double RESET_HIGH_V = 3.0;
        static final double RESET_LOW_V = 1.8;

        final double[] inputs = new double[STAGES];
        final double[] outputs = new double[STAGES];
        final double[] muxGains = new double[STAGES];
        final double[] muxStartGains = new double[STAGES];
        int selectedStage;
        double outCommon;
        private int muxFadePosition = FADE_SAMPLES;
        private int exclusiveStage;
        private int exclusiveTarget;
        private int exclusivePhase; // 0 steady, 1 fade old route down, 2 fade new route up
        private int exclusiveFadePosition;
        private double exclusiveStartGain = 1.0;
        private double exclusiveGain = 1.0;
        private boolean clockHigh;
        private boolean resetHigh;
        private boolean resetOverride;
        private int resetManualStage;
        private int resetCvStage;
        private boolean primed;

        SwitchCore() { reset(); }

        private static double finite(double value) { return Double.isFinite(value) ? value : 0.0; }
        private static int clamp(int value, int minimum, int maximum) {
            return value < minimum ? minimum : (value > maximum ? maximum : value);
        }
        private static int knobStage(double value) {
            return clamp((int) Math.round(finite(value)), 1, STAGES) - 1;
        }
        /** Wraps each 5 V cycle across the active stage count (knob); negative CV travels backward through the same bins. */
        static int stageFromCv(double volts, int stages) {
            stages = clamp(stages, 1, STAGES);
            double value = finite(volts);
            double wrapped = value - 5.0 * Math.floor(value / 5.0);
            int stage = (int) Math.floor((wrapped / 5.0) * stages);
            return clamp(stage, 0, stages - 1);
        }
        private static boolean schmitt(boolean wasHigh, double value, double high, double low) {
            value = finite(value);
            return wasHigh ? value >= low : value >= high;
        }
        private static double smoothstep(double position) {
            double t = position < 0.0 ? 0.0 : (position > 1.0 ? 1.0 : position);
            return t * t * (3.0 - 2.0 * t);
        }

        void reset() {
            selectedStage = 0;
            outCommon = 0.0;
            for (int i = 0; i < STAGES; i++) {
                inputs[i] = 0.0;
                outputs[i] = 0.0;
                muxGains[i] = i == 0 ? 1.0 : 0.0;
                muxStartGains[i] = muxGains[i];
            }
            muxFadePosition = FADE_SAMPLES;
            exclusiveStage = 0;
            exclusiveTarget = 0;
            exclusivePhase = 0;
            exclusiveFadePosition = 0;
            exclusiveStartGain = 1.0;
            exclusiveGain = 1.0;
            clockHigh = false;
            resetHigh = false;
            resetOverride = false;
            resetManualStage = 0;
            resetCvStage = 0;
            primed = false;
        }

        byte[] getState() {
            return new byte[] { (byte) selectedStage };
        }

        void setState(byte[] state) {
            if (state == null || state.length < 1) return;
            selectedStage = clamp(state[0] & 0xff, 0, STAGES - 1);
            for (int i = 0; i < STAGES; i++) {
                muxGains[i] = i == selectedStage ? 1.0 : 0.0;
                muxStartGains[i] = muxGains[i];
                outputs[i] = 0.0;
            }
            muxFadePosition = FADE_SAMPLES;
            exclusiveStage = selectedStage;
            exclusiveTarget = selectedStage;
            exclusivePhase = 0;
            exclusiveFadePosition = 0;
            exclusiveStartGain = 1.0;
            exclusiveGain = 1.0;
            clockHigh = false;
            resetHigh = false;
            resetOverride = false;
            resetManualStage = 0;
            resetCvStage = 0;
            primed = false;
        }

        private void requestStage(int stage) {
            stage = clamp(stage, 0, STAGES - 1);
            if (stage == selectedStage) return;
            selectedStage = stage;
            System.arraycopy(muxGains, 0, muxStartGains, 0, STAGES);
            muxFadePosition = 0;

            if (exclusivePhase == 0) {
                if (stage != exclusiveStage) {
                    exclusiveTarget = stage;
                    exclusivePhase = 1;
                    exclusiveFadePosition = 0;
                    exclusiveStartGain = exclusiveGain;
                }
            } else if (exclusivePhase == 1) {
                if (stage == exclusiveStage) {
                    exclusiveTarget = exclusiveStage;
                    exclusivePhase = 2;
                    exclusiveFadePosition = 0;
                    exclusiveStartGain = exclusiveGain;
                } else {
                    exclusiveTarget = stage;
                }
            } else if (stage != exclusiveStage) {
                exclusiveTarget = stage;
                exclusivePhase = 1;
                exclusiveFadePosition = 0;
                exclusiveStartGain = exclusiveGain;
            }
        }

        private void updateFades() {
            if (muxFadePosition < FADE_SAMPLES) {
                muxFadePosition++;
                double mix = smoothstep((double) muxFadePosition / FADE_SAMPLES);
                for (int i = 0; i < STAGES; i++) {
                    double target = i == selectedStage ? 1.0 : 0.0;
                    muxGains[i] = muxStartGains[i] * (1.0 - mix) + target * mix;
                }
            }
            if (exclusivePhase == 1) {
                exclusiveFadePosition++;
                double mix = smoothstep((double) exclusiveFadePosition / EXCLUSIVE_FADE_SAMPLES);
                exclusiveGain = exclusiveStartGain * (1.0 - mix);
                if (exclusiveFadePosition >= EXCLUSIVE_FADE_SAMPLES) {
                    exclusiveGain = 0.0;
                    exclusiveStage = exclusiveTarget;
                    exclusivePhase = 2;
                    exclusiveFadePosition = 0;
                    exclusiveStartGain = 0.0;
                }
            }
            if (exclusivePhase == 2) {          // also runs in the sample phase 1 ends, so the new route starts rising at once (no silent sample)
                exclusiveFadePosition++;
                double mix = smoothstep((double) exclusiveFadePosition / EXCLUSIVE_FADE_SAMPLES);
                exclusiveGain = exclusiveStartGain + (1.0 - exclusiveStartGain) * mix;
                if (exclusiveFadePosition >= EXCLUSIVE_FADE_SAMPLES) {
                    exclusiveGain = 1.0;
                    exclusivePhase = 0;
                    exclusiveFadePosition = 0;
                }
            }
        }

        /** Stage selection, reset, clock and fades. Call before mix(); needsInput() tells which stage inputs mix() will use. */
        void update(double knobValue, double selectCv, boolean selectConnected,
                    double clockCv, boolean clockConnected, double resetCv, boolean resetConnected) {
            int stages = clamp((int) Math.round(finite(knobValue)), 1, STAGES);
            boolean nextClockHigh = clockConnected && schmitt(clockHigh, clockCv, CLOCK_HIGH_V, CLOCK_LOW_V);
            boolean risingClock = primed && nextClockHigh && !clockHigh;
            clockHigh = nextClockHigh;
            boolean priorResetHigh = resetHigh;
            resetHigh = resetConnected && schmitt(resetHigh, resetCv, RESET_HIGH_V, RESET_LOW_V);

            if (!primed) {
                primed = true;
                if (resetHigh) {
                    resetOverride = true;
                    resetManualStage = knobStage(knobValue);
                    resetCvStage = stageFromCv(selectCv, stages);
                    requestStage(0);
                }
                else if (!clockConnected) requestStage(selectConnected ? stageFromCv(selectCv, stages) : knobStage(knobValue));
            } else if (resetHigh) {
                if (!priorResetHigh) {
                    resetOverride = true;
                    resetManualStage = knobStage(knobValue);
                    resetCvStage = stageFromCv(selectCv, stages);
                }
                requestStage(0);
            } else if (resetOverride) {
                if (clockConnected) {
                    if (risingClock) {
                        resetOverride = false;
                        requestStage(selectConnected ? stageFromCv(selectCv, stages) : (selectedStage + 1) % stages);
                    }
                } else if (selectConnected) {
                    int cvStage = stageFromCv(selectCv, stages);
                    if (cvStage != resetCvStage) {
                        resetOverride = false;
                        requestStage(cvStage);
                    }
                } else {
                    int manualStage = knobStage(knobValue);
                    if (manualStage != resetManualStage) {
                        resetOverride = false;
                        requestStage(manualStage);
                    }
                }
            } else if (!clockConnected) {
                requestStage(selectConnected ? stageFromCv(selectCv, stages) : knobStage(knobValue));
            } else if (selectConnected) {
                if (risingClock) requestStage(stageFromCv(selectCv, stages));
                else if (selectedStage >= stages) requestStage(stages - 1);
            } else {
                if (selectedStage >= stages) requestStage(stages - 1);
                else if (risingClock) requestStage((selectedStage + 1) % stages);
            }

            updateFades();
        }

        /** True when stage input i can reach an output this sample; the module only reads those jacks. */
        boolean needsInput(int stage) {
            return muxGains[stage] != 0.0 || stage == exclusiveStage;
        }

        /** Routes the stage inputs (entries that needsInput() rejected may be 0) to Common Out and the paired outputs. */
        void mix(double commonInput, boolean commonInputConnected, boolean commonOutputConnected, double[] stageInputs) {
            outCommon = 0.0;
            for (int i = 0; i < STAGES; i++) {
                double input = finite(stageInputs[i]);
                outCommon += input * muxGains[i];
                outputs[i] = commonInputConnected
                    ? (i == exclusiveStage ? finite(commonInput) * exclusiveGain : 0.0)
                    : (!commonOutputConnected && i == exclusiveStage ? input * exclusiveGain : 0.0);
            }
        }

        /** Whole sample in one call (headless tests); the module calls update(), reads the inputs it needs, then mix(). */
        void process(double knobValue, double selectCv, boolean selectConnected,
                     double clockCv, boolean clockConnected, double resetCv, boolean resetConnected,
                     double commonInput, boolean commonInputConnected, boolean commonOutputConnected,
                     double[] stageInputs) {
            update(knobValue, selectCv, selectConnected, clockCv, clockConnected, resetCv, resetConnected);
            mix(commonInput, commonInputConnected, commonOutputConnected, stageInputs);
        }

        /** Bypass freezes stage selection; the selected routes pass without fade. Follow with mix(). */
        void bypassUpdate(double clockCv, boolean clockConnected, double resetCv, boolean resetConnected) {
            clockHigh = clockConnected && schmitt(clockHigh, clockCv, CLOCK_HIGH_V, CLOCK_LOW_V);
            resetHigh = resetConnected && schmitt(resetHigh, resetCv, RESET_HIGH_V, RESET_LOW_V);
            primed = true;
            exclusiveStage = selectedStage;
            exclusiveTarget = selectedStage;
            exclusivePhase = 0;
            exclusiveGain = 1.0;
            exclusiveStartGain = 1.0;
            exclusiveFadePosition = 0;
            muxFadePosition = FADE_SAMPLES;
            for (int i = 0; i < STAGES; i++) {
                muxGains[i] = i == selectedStage ? 1.0 : 0.0;
                muxStartGains[i] = muxGains[i];
            }
        }

        /** Whole bypassed sample in one call (headless tests). */
        void bypass(double clockCv, boolean clockConnected, double resetCv, boolean resetConnected,
                    double commonInput, boolean commonInputConnected, boolean commonOutputConnected,
                    double[] stageInputs) {
            bypassUpdate(clockCv, clockConnected, resetCv, resetConnected);
            mix(commonInput, commonInputConnected, commonOutputConnected, stageInputs);
        }
    }

    private final SwitchCore core = new SwitchCore();
    //[/user-code-and-variables]
}

 