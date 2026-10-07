package com.insectlabs.n01;


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


public class n01 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public n01( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "n01", ModuleType.ModuleType_Source, 1.6 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "7c5dd340f06f460781ce40efc18a8a11" );
    }

void InitializeControls()
{

        randomLevelKnob = new VoltageKnob( "randomLevelKnob", "Random Level", this, 0.0, 1.0, 0.0 );
        AddComponent( randomLevelKnob );
        randomLevelKnob.SetWantsMouseNotifications( false );
        randomLevelKnob.SetPosition( 65, 154 );
        randomLevelKnob.SetSize( 35, 35 );
        randomLevelKnob.SetSkin( "Knurled Plastic Cream" );
        randomLevelKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        randomLevelKnob.SetKnobParams( 215, 145 );
        randomLevelKnob.DisplayValueInPercent( false );
        randomLevelKnob.SetKnobAdjustsRing( true );

        blueKnob = new VoltageKnob( "blueKnob", "Blue", this, 0.0, 1.0, 0.0 );
        AddComponent( blueKnob );
        blueKnob.SetWantsMouseNotifications( false );
        blueKnob.SetPosition( 15, 41 );
        blueKnob.SetSize( 35, 35 );
        blueKnob.SetSkin( "Knurled Plastic Sky" );
        blueKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        blueKnob.SetKnobParams( 215, 145 );
        blueKnob.DisplayValueInPercent( false );
        blueKnob.SetKnobAdjustsRing( true );

        redKnob = new VoltageKnob( "redKnob", "Red", this, 0.0, 1.0, 0.0 );
        AddComponent( redKnob );
        redKnob.SetWantsMouseNotifications( false );
        redKnob.SetPosition( 15, 83 );
        redKnob.SetSize( 35, 35 );
        redKnob.SetSkin( "Knurled Plastic Red" );
        redKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        redKnob.SetKnobParams( 215, 145 );
        redKnob.DisplayValueInPercent( false );
        redKnob.SetKnobAdjustsRing( true );

        descriptionLabel = new VoltageLabel( "descriptionLabel", "Module Description", this, "noise source" );
        AddComponent( descriptionLabel );
        descriptionLabel.SetWantsMouseNotifications( false );
        descriptionLabel.SetPosition( 3, 3 );
        descriptionLabel.SetSize( 109, 23 );
        descriptionLabel.SetEditable( false, false );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        descriptionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        descriptionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        descriptionLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
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

        numberLabel = new VoltageLabel( "numberLabel", "Model Number", this, "n01" );
        AddComponent( numberLabel );
        numberLabel.SetWantsMouseNotifications( false );
        numberLabel.SetPosition( 33, 335 );
        numberLabel.SetSize( 79, 13 );
        numberLabel.SetEditable( false, false );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.Right );
        numberLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        numberLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        numberLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        numberLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        numberLabel.SetBorderSize( 1 );
        numberLabel.SetMultiLineEdit( false );
        numberLabel.SetIsNumberEditor( false );
        numberLabel.SetNumberEditorRange( 0, 100 );
        numberLabel.SetNumberEditorInterval( 1 );
        numberLabel.SetNumberEditorUsesMouseWheel( false );
        numberLabel.SetHasCustomTextHoverColor( false );
        numberLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        numberLabel.SetFont( "Courier New", 13, true, false );

        slowRandomOutput = new VoltageAudioJack( "slowRandomOutput", "Slow Random Output", this, JackType.JackType_AudioOutput );
        AddComponent( slowRandomOutput );
        slowRandomOutput.SetWantsMouseNotifications( false );
        slowRandomOutput.SetPosition( 44, 202 );
        slowRandomOutput.SetSize( 25, 25 );
        slowRandomOutput.SetSkin( "Mini Jack 25px" );

        whiteOutput = new VoltageAudioJack( "whiteOutput", "White Output", this, JackType.JackType_AudioOutput );
        AddComponent( whiteOutput );
        whiteOutput.SetWantsMouseNotifications( false );
        whiteOutput.SetPosition( 70, 46 );
        whiteOutput.SetSize( 25, 25 );
        whiteOutput.SetSkin( "Mini Jack 25px" );

        spectraOutput = new VoltageAudioJack( "spectraOutput", "Spectra Output", this, JackType.JackType_AudioOutput );
        AddComponent( spectraOutput );
        spectraOutput.SetWantsMouseNotifications( false );
        spectraOutput.SetPosition( 70, 88 );
        spectraOutput.SetSize( 25, 25 );
        spectraOutput.SetSkin( "Mini Jack 25px" );

        randomRateKnob = new VoltageKnob( "randomRateKnob", "Random Rate", this, 0.0, 1.0, 0.0 );
        AddComponent( randomRateKnob );
        randomRateKnob.SetWantsMouseNotifications( false );
        randomRateKnob.SetPosition( 15, 153 );
        randomRateKnob.SetSize( 35, 35 );
        randomRateKnob.SetSkin( "Knurled Plastic Cream" );
        randomRateKnob.SetRange( 0.0, 1.0, 0.0, false, 0 );
        randomRateKnob.SetKnobParams( 215, 145 );
        randomRateKnob.DisplayValueInPercent( false );
        randomRateKnob.SetKnobAdjustsRing( true );

        manualTriggerButton = new VoltageButton( "manualTriggerButton", "Manual Trigger", this );
        AddComponent( manualTriggerButton );
        manualTriggerButton.SetWantsMouseNotifications( false );
        manualTriggerButton.SetPosition( 65, 296 );
        manualTriggerButton.SetSize( 35, 35 );
        manualTriggerButton.SetSkin( "2500 Square Big" );
        manualTriggerButton.ShowOverlay( false );
        manualTriggerButton.SetOverlayText( "" );
        manualTriggerButton.SetAutoRepeat( false );

        sampleSourceOutput = new VoltageAudioJack( "sampleSourceOutput", "Sample and Hold Source", this, JackType.JackType_AudioOutput );
        AddComponent( sampleSourceOutput );
        sampleSourceOutput.SetWantsMouseNotifications( false );
        sampleSourceOutput.SetPosition( 20, 256 );
        sampleSourceOutput.SetSize( 25, 25 );
        sampleSourceOutput.SetSkin( "Mini Jack 25px" );

        randomLabel = new VoltageLabel( "randomLabel", "Random Label", this, "SLOW RANDOM" );
        AddComponent( randomLabel );
        randomLabel.SetWantsMouseNotifications( false );
        randomLabel.SetPosition( 0, 136 );
        randomLabel.SetSize( 115, 13 );
        randomLabel.SetEditable( false, false );
        randomLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        randomLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        randomLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        randomLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        randomLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        randomLabel.SetBorderSize( 4 );
        randomLabel.SetMultiLineEdit( false );
        randomLabel.SetIsNumberEditor( false );
        randomLabel.SetNumberEditorRange( 0, 100 );
        randomLabel.SetNumberEditorInterval( 1 );
        randomLabel.SetNumberEditorUsesMouseWheel( false );
        randomLabel.SetHasCustomTextHoverColor( false );
        randomLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        randomLabel.SetFont( "Arial", 8, true, false );

        uncertaintyLabel = new VoltageLabel( "uncertaintyLabel", "Uncertainty Label", this, "UNCERTAINTY" );
        AddComponent( uncertaintyLabel );
        uncertaintyLabel.SetWantsMouseNotifications( false );
        uncertaintyLabel.SetPosition( 0, 241 );
        uncertaintyLabel.SetSize( 115, 13 );
        uncertaintyLabel.SetEditable( false, false );
        uncertaintyLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        uncertaintyLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        uncertaintyLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        uncertaintyLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        uncertaintyLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        uncertaintyLabel.SetBorderSize( 4 );
        uncertaintyLabel.SetMultiLineEdit( false );
        uncertaintyLabel.SetIsNumberEditor( false );
        uncertaintyLabel.SetNumberEditorRange( 0, 100 );
        uncertaintyLabel.SetNumberEditorInterval( 1 );
        uncertaintyLabel.SetNumberEditorUsesMouseWheel( false );
        uncertaintyLabel.SetHasCustomTextHoverColor( false );
        uncertaintyLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        uncertaintyLabel.SetFont( "Arial", 8, true, false );

        noiseLabel = new VoltageLabel( "noiseLabel", "Noise Label", this, "NOISE" );
        AddComponent( noiseLabel );
        noiseLabel.SetWantsMouseNotifications( false );
        noiseLabel.SetPosition( 0, 27 );
        noiseLabel.SetSize( 115, 13 );
        noiseLabel.SetEditable( false, false );
        noiseLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        noiseLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        noiseLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        noiseLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        noiseLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        noiseLabel.SetBorderSize( 4 );
        noiseLabel.SetMultiLineEdit( false );
        noiseLabel.SetIsNumberEditor( false );
        noiseLabel.SetNumberEditorRange( 0, 100 );
        noiseLabel.SetNumberEditorInterval( 1 );
        noiseLabel.SetNumberEditorUsesMouseWheel( false );
        noiseLabel.SetHasCustomTextHoverColor( false );
        noiseLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        noiseLabel.SetFont( "Arial", 8, true, false );

        triggerLabel = new VoltageLabel( "triggerLabel", "Trigger Label", this, "TRIGGER" );
        AddComponent( triggerLabel );
        triggerLabel.SetWantsMouseNotifications( false );
        triggerLabel.SetPosition( 12, 325 );
        triggerLabel.SetSize( 40, 10 );
        triggerLabel.SetEditable( false, false );
        triggerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        triggerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        triggerLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        triggerLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        triggerLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        triggerLabel.SetBorderSize( 4 );
        triggerLabel.SetMultiLineEdit( false );
        triggerLabel.SetIsNumberEditor( false );
        triggerLabel.SetNumberEditorRange( 0, 100 );
        triggerLabel.SetNumberEditorInterval( 1 );
        triggerLabel.SetNumberEditorUsesMouseWheel( false );
        triggerLabel.SetHasCustomTextHoverColor( false );
        triggerLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        triggerLabel.SetFont( "Arial", 6, true, false );

        sampleSourceLabel = new VoltageLabel( "sampleSourceLabel", "Sample Source Label", this, "S&H SOURCE" );
        AddComponent( sampleSourceLabel );
        sampleSourceLabel.SetWantsMouseNotifications( false );
        sampleSourceLabel.SetPosition( 7, 282 );
        sampleSourceLabel.SetSize( 50, 10 );
        sampleSourceLabel.SetEditable( false, false );
        sampleSourceLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        sampleSourceLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        sampleSourceLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        sampleSourceLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        sampleSourceLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        sampleSourceLabel.SetBorderSize( 4 );
        sampleSourceLabel.SetMultiLineEdit( false );
        sampleSourceLabel.SetIsNumberEditor( false );
        sampleSourceLabel.SetNumberEditorRange( 0, 100 );
        sampleSourceLabel.SetNumberEditorInterval( 1 );
        sampleSourceLabel.SetNumberEditorUsesMouseWheel( false );
        sampleSourceLabel.SetHasCustomTextHoverColor( false );
        sampleSourceLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        sampleSourceLabel.SetFont( "Arial", 6, true, false );

        rateLabel = new VoltageLabel( "rateLabel", "Rate Label", this, "RATE" );
        AddComponent( rateLabel );
        rateLabel.SetWantsMouseNotifications( false );
        rateLabel.SetPosition( 18, 189 );
        rateLabel.SetSize( 30, 10 );
        rateLabel.SetEditable( false, false );
        rateLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        rateLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        rateLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        rateLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        rateLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        rateLabel.SetBorderSize( 4 );
        rateLabel.SetMultiLineEdit( false );
        rateLabel.SetIsNumberEditor( false );
        rateLabel.SetNumberEditorRange( 0, 100 );
        rateLabel.SetNumberEditorInterval( 1 );
        rateLabel.SetNumberEditorUsesMouseWheel( false );
        rateLabel.SetHasCustomTextHoverColor( false );
        rateLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        rateLabel.SetFont( "Arial", 6, true, false );

        steppedLabel = new VoltageLabel( "steppedLabel", "Stepped Label", this, "STEPPED" );
        AddComponent( steppedLabel );
        steppedLabel.SetWantsMouseNotifications( false );
        steppedLabel.SetPosition( 58, 282 );
        steppedLabel.SetSize( 50, 10 );
        steppedLabel.SetEditable( false, false );
        steppedLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        steppedLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        steppedLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        steppedLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        steppedLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        steppedLabel.SetBorderSize( 4 );
        steppedLabel.SetMultiLineEdit( false );
        steppedLabel.SetIsNumberEditor( false );
        steppedLabel.SetNumberEditorRange( 0, 100 );
        steppedLabel.SetNumberEditorInterval( 1 );
        steppedLabel.SetNumberEditorUsesMouseWheel( false );
        steppedLabel.SetHasCustomTextHoverColor( false );
        steppedLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        steppedLabel.SetFont( "Arial", 6, true, false );

        polarityLabel = new VoltageLabel( "polarityLabel", "Polarity Label", this, "–   +" );
        AddComponent( polarityLabel );
        polarityLabel.SetWantsMouseNotifications( false );
        polarityLabel.SetPosition( 42, 191 );
        polarityLabel.SetSize( 30, 10 );
        polarityLabel.SetEditable( false, false );
        polarityLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        polarityLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        polarityLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        polarityLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        polarityLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        polarityLabel.SetBorderSize( 4 );
        polarityLabel.SetMultiLineEdit( false );
        polarityLabel.SetIsNumberEditor( false );
        polarityLabel.SetNumberEditorRange( 0, 100 );
        polarityLabel.SetNumberEditorInterval( 1 );
        polarityLabel.SetNumberEditorUsesMouseWheel( false );
        polarityLabel.SetHasCustomTextHoverColor( false );
        polarityLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        polarityLabel.SetFont( "Arial", 8, true, false );

        spectraLabel = new VoltageLabel( "spectraLabel", "Spectra Label", this, "SPECTRA" );
        AddComponent( spectraLabel );
        spectraLabel.SetWantsMouseNotifications( false );
        spectraLabel.SetPosition( 68, 116 );
        spectraLabel.SetSize( 30, 10 );
        spectraLabel.SetEditable( false, false );
        spectraLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        spectraLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        spectraLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        spectraLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        spectraLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        spectraLabel.SetBorderSize( 4 );
        spectraLabel.SetMultiLineEdit( false );
        spectraLabel.SetIsNumberEditor( false );
        spectraLabel.SetNumberEditorRange( 0, 100 );
        spectraLabel.SetNumberEditorInterval( 1 );
        spectraLabel.SetNumberEditorUsesMouseWheel( false );
        spectraLabel.SetHasCustomTextHoverColor( false );
        spectraLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        spectraLabel.SetFont( "Arial", 6, true, false );

        whiteLabel = new VoltageLabel( "whiteLabel", "White Label", this, "WHITE" );
        AddComponent( whiteLabel );
        whiteLabel.SetWantsMouseNotifications( false );
        whiteLabel.SetPosition( 68, 74 );
        whiteLabel.SetSize( 30, 10 );
        whiteLabel.SetEditable( false, false );
        whiteLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        whiteLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        whiteLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        whiteLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        whiteLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        whiteLabel.SetBorderSize( 4 );
        whiteLabel.SetMultiLineEdit( false );
        whiteLabel.SetIsNumberEditor( false );
        whiteLabel.SetNumberEditorRange( 0, 100 );
        whiteLabel.SetNumberEditorInterval( 1 );
        whiteLabel.SetNumberEditorUsesMouseWheel( false );
        whiteLabel.SetHasCustomTextHoverColor( false );
        whiteLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        whiteLabel.SetFont( "Arial", 6, true, false );

        levelLabel = new VoltageLabel( "levelLabel", "Level Label", this, "LEVEL" );
        AddComponent( levelLabel );
        levelLabel.SetWantsMouseNotifications( false );
        levelLabel.SetPosition( 68, 190 );
        levelLabel.SetSize( 30, 10 );
        levelLabel.SetEditable( false, false );
        levelLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        levelLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        levelLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        levelLabel.SetBkColor( new Color( 35, 35, 35, 0 ) );
        levelLabel.SetBorderColor( new Color( 35, 35, 35, 0 ) );
        levelLabel.SetBorderSize( 4 );
        levelLabel.SetMultiLineEdit( false );
        levelLabel.SetIsNumberEditor( false );
        levelLabel.SetNumberEditorRange( 0, 100 );
        levelLabel.SetNumberEditorInterval( 1 );
        levelLabel.SetNumberEditorUsesMouseWheel( false );
        levelLabel.SetHasCustomTextHoverColor( false );
        levelLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        levelLabel.SetFont( "Arial", 6, true, false );

        positiveLed = new VoltageLED( "positiveLed", "Positive Random Indicator", this );
        AddComponent( positiveLed );
        positiveLed.SetWantsMouseNotifications( false );
        positiveLed.SetPosition( 58, 185 );
        positiveLed.SetSize( 8, 8 );
        positiveLed.SetSkin( "2500 Lamp Green" );

        negativeLed = new VoltageLED( "negativeLed", "Negative Random Indicator", this );
        AddComponent( negativeLed );
        negativeLed.SetWantsMouseNotifications( false );
        negativeLed.SetPosition( 48, 185 );
        negativeLed.SetSize( 8, 8 );
        negativeLed.SetSkin( "2500 Lamp Red" );

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

        triggerInput = new VoltageAudioJack( "triggerInput", "Trigger Input", this, JackType.JackType_AudioInput );
        AddComponent( triggerInput );
        triggerInput.SetWantsMouseNotifications( false );
        triggerInput.SetPosition( 20, 300 );
        triggerInput.SetSize( 25, 25 );
        triggerInput.SetSkin( "Dark Jack Straight" );

        steppedOutput = new VoltageAudioJack( "steppedOutput", "Stepped Output", this, JackType.JackType_AudioOutput );
        AddComponent( steppedOutput );
        steppedOutput.SetWantsMouseNotifications( false );
        steppedOutput.SetPosition( 70, 256 );
        steppedOutput.SetSize( 25, 25 );
        steppedOutput.SetSkin( "Rotated Half" );
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
        resumePending = true;

randomMeter = 0.0;
negativeLed.SetValue(0.0);
positiveLed.SetValue(0.0);

StartGuiUpdateTimer(25);
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
                if (component == manualTriggerButton) {
                    boolean down = doubleValue >= 0.5;
                    if (down && !manualDown && !bypassActive) {
                        manualRequests.incrementAndGet();
                    }
                    manualDown = down;
                }
                break;
            case GUI_Update_Timer:
                updateLamps();
                break;
            case Reset:
                requestedHold = 0.0;
                manualRequests.set(0);
                manualDown = false;
                resumePending = true;
                break;
            case Preset_Loading_Finish:
            case Variation_Loading_Finish:
                resumePending = true;
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
        boolean resumed = resumePending;
        bypassActive = false;
        double redTarget = clamp(redKnob.GetValue(), 0.0, 1.0);
        double blueTarget = clamp(blueKnob.GetValue(), 0.0, 1.0);
        double levelTarget = clamp(randomLevelKnob.GetValue(), 0.0, 1.0);
        double rate = clamp(randomRateKnob.GetValue(), 0.0, 1.0);
        if (rate != cachedRate) {
            cachedRate = rate;
            targetCoefficient = coefficient(rateHz(rate));
            // RMS compensation for two low-pass poles, independent of LEVEL.
            targetNormalization = 0.10 / Math.sqrt(targetCoefficient);
        }
        if (resumed) {
            resumePending = false;
            red = redTarget;
            blue = blueTarget;
            level = levelTarget;
            sourceRatePosition = rate;
            randomCoefficient = targetCoefficient;
            randomNormalization = targetNormalization;
        }
        red = smooth(red, redTarget);
        blue = smooth(blue, blueTarget);
        level = smooth(level, levelTarget);
        sourceRatePosition = smooth(sourceRatePosition, rate);
        randomCoefficient = smooth(randomCoefficient, targetCoefficient);
        randomNormalization = smooth(randomNormalization, targetNormalization);

        double noise = noiseSample();
        whiteLow += WHITE_COEFFICIENT * (noise - whiteLow);
        double white = 4.9 * (0.65 * noise + 0.35 * whiteLow);
        whiteOutput.SetValue(white);

        redLow += RED_COEFFICIENT * (noise - redLow);
        midLow += MID_COEFFICIENT * (noise - midLow);
        upperLow += UPPER_COEFFICIENT * (noise - upperLow);
        blueLow += BLUE_COEFFICIENT * (noise - blueLow);
        // Broad warm component plus independently adjustable brighter content.
        double warm = 9.0 * redLow + 4.5 * midLow + 2.0 * upperLow + blueLow;
        double bright = 1.35 * whiteLow - 0.35 * blueLow;
        double color = 3.2 * (red * warm + blue * bright);
        double spectra = warmLimit(color);
        spectraOutput.SetValue(spectra);

        // Continuous filtered noise: RATE is a bandwidth, never an S&H clock.
        driftFirst += randomCoefficient * (spectra - driftFirst);
        driftSecond += randomCoefficient * (driftFirst - driftSecond);
        double drift = driftSecond * randomNormalization;
        double random = 5.0 * level * drift / (1.0 + Math.abs(drift));
        slowRandomOutput.SetValue(random);
        randomMeter = random;

        // Knobs gently pull a continuous ramp; LEVEL changes timing variation only.
        double sourceMean = 60.0 + 24.0 * (sourceRatePosition - 0.5) + 8.0 * (blue - red);
        double sourceSpread = 0.30 + 0.20 * level;
        rampStep = clamp(sourceMean * (1.0 + sourceSpread * rampJitter), 20.0, 120.0) / SAMPLE_RATE;
        rampPhase += rampStep;
        if (rampPhase >= 1.0) {
            rampPhase -= 1.0;
            rampJitter = rampRandom();
        }
        double source = 10.0 * rampPhase - 5.0;
        sampleSourceOutput.SetValue(source);

        double restored = requestedHold;
        if (Double.isFinite(restored)) {
            heldVoltage = clamp(restored, -5.0, 5.0);
            requestedHold = Double.NaN;
        }
        double trigger = triggerInput.IsConnected() ? triggerInput.GetValue() : 0.0;
        if (!Double.isFinite(trigger)) {
            trigger = 0.0;
        }
        boolean fire = false;
        if (resumed && suppressTriggerOnResume) {
            triggerHigh = trigger >= 1.0;
            suppressTriggerOnResume = false;
            manualRequests.set(0);
        } else if (trigger >= 1.0 && !triggerHigh) {
            triggerHigh = true;
            fire = true;
        } else if (trigger <= 0.1) {
            triggerHigh = false;
        }
        if (manualRequests.getAndSet(0) > 0) {
            fire = true;
        }
        if (fire) {
            heldVoltage = source;
        }
        steppedOutput.SetValue(heldVoltage);
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
        bypassActive = true;
resumePending = true;
suppressTriggerOnResume = true;

manualRequests.set(0);
randomMeter = 0.0;

whiteOutput.SetValue(0.0);
spectraOutput.SetValue(0.0);
slowRandomOutput.SetValue(0.0);
sampleSourceOutput.SetValue(0.0);
steppedOutput.SetValue(0.0);
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
        if (component == redKnob || component == blueKnob) {
            return String.format(java.util.Locale.ROOT, "%s: %.1f%%; shapes Spectra/Slow Random; Red slows and Blue speeds S&H Source slightly", component == redKnob ? "Red" : "Blue", 100.0 * component.GetValue());
        }
        if (component == randomRateKnob) {
            return String.format(java.util.Locale.ROOT, "Random Rate: %.3f Hz bandwidth; clockwise faster; gently speeds S&H Source", rateHz(randomRateKnob.GetValue()));
        }
        if (component == randomLevelKnob) {
            return String.format(java.util.Locale.ROOT, "Random Level: %.1f%%; Slow Random amplitude; gently increases S&H Source timing variation", 100.0 * randomLevelKnob.GetValue());
        }
        if (component == whiteOutput) {
            return "White: fixed-level broadband noise with subtle top-end restraint; independent of all knobs";
        }
        if (component == spectraOutput) {
            return "Spectra: Red/Blue noise mixture through gentle warm compression";
        }
        if (component == slowRandomOutput) {
            return "Slow Random: continuously filtered Spectra; Rate controls motion, Level controls amplitude";
        }
        if (component == sampleSourceOutput) {
            return "S&H Source: continuous +/-5 V noisy ramp; internal 20-120 Hz cycle rate influenced gently by all knobs; no trigger needed";
        }
        if (component == steppedOutput) {
            return "Stepped: holds sampled S&H Source voltage until a new trigger or manual press";
        }
        if (component == triggerInput) {
            return "Trigger: sample on rising edge above +1 V; rearm below +0.1 V";
        }
        if (component == manualTriggerButton) {
            return "Manual Trigger: one new held voltage per press";
        }
        if (component == negativeLed || component == positiveLed) {
            return "Slow Random polarity and magnitude";
        }
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
        if (!Double.isFinite(newValue)) {
            return;
        }
        if (component == randomRateKnob) {
            super.EditComponentValue(component, Math.log(clamp(newValue, 0.05, 20.0) / 0.05) / Math.log(400.0), newText);
            return;
        }
        if (component == redKnob || component == blueKnob || component == randomLevelKnob) {
            super.EditComponentValue(component, clamp(newValue / 100.0, 0.0, 1.0), newText);
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
        return java.nio.ByteBuffer.allocate(9).put((byte) 1).putDouble(heldVoltage).array();
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
        if (stateInfo != null && stateInfo.length == 9 && stateInfo[0] == 1) {
            double value = java.nio.ByteBuffer.wrap(stateInfo, 1, 8).getDouble();
            if (Double.isFinite(value)) {
                requestedHold = clamp(value, -5.0, 5.0);
            }
        }
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
    private VoltageAudioJack steppedOutput;
    private VoltageAudioJack triggerInput;
    private VoltageLabel manufacturerLabel;
    private VoltageLED negativeLed;
    private VoltageLED positiveLed;
    private VoltageLabel levelLabel;
    private VoltageLabel whiteLabel;
    private VoltageLabel spectraLabel;
    private VoltageLabel polarityLabel;
    private VoltageLabel steppedLabel;
    private VoltageLabel rateLabel;
    private VoltageLabel sampleSourceLabel;
    private VoltageLabel triggerLabel;
    private VoltageLabel noiseLabel;
    private VoltageLabel uncertaintyLabel;
    private VoltageLabel randomLabel;
    private VoltageAudioJack sampleSourceOutput;
    private VoltageButton manualTriggerButton;
    private VoltageKnob randomRateKnob;
    private VoltageAudioJack spectraOutput;
    private VoltageAudioJack whiteOutput;
    private VoltageAudioJack slowRandomOutput;
    private VoltageLabel numberLabel;
    private VoltageLabel descriptionLabel;
    private VoltageKnob redKnob;
    private VoltageKnob blueKnob;
    private VoltageKnob randomLevelKnob;


    //[user-code-and-variables]    Add your own variables and functions here
    private static final double SAMPLE_RATE = 48000.0;
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE));
    private static final double WHITE_COEFFICIENT = coefficient(4200.0);
    private static final double RED_COEFFICIENT = coefficient(70.0);
    private static final double MID_COEFFICIENT = coefficient(250.0);
    private static final double UPPER_COEFFICIENT = coefficient(1000.0);
    private static final double BLUE_COEFFICIENT = coefficient(3200.0);
    private static final long SEED_STEP = 0x9e3779b97f4a7c15L;
    private static final java.util.concurrent.atomic.AtomicLong SEEDS =
            new java.util.concurrent.atomic.AtomicLong(System.nanoTime());
    private long noiseState = SEEDS.getAndAdd(SEED_STEP);
    private long rampState = SEEDS.getAndAdd(SEED_STEP);
    private final java.util.concurrent.atomic.AtomicInteger manualRequests =
            new java.util.concurrent.atomic.AtomicInteger();
    private boolean manualDown, triggerHigh, suppressTriggerOnResume;
    private volatile boolean bypassActive, resumePending = true;
    private volatile double heldVoltage, randomMeter;
    private volatile double requestedHold = Double.NaN;
    private double red, blue, level, randomCoefficient, randomNormalization;
    private double cachedRate = Double.NaN, targetCoefficient, targetNormalization;
    private double whiteLow, redLow, midLow, upperLow, blueLow;
    private double driftFirst, driftSecond, rampPhase, sourceRatePosition;
    private double rampJitter, rampStep = 60.0 / SAMPLE_RATE;

    private static double clamp(double x, double lo, double hi) {
    if (!Double.isFinite(x)) {
        return 0.0;
    }

    return Math.max(lo, Math.min(hi, x));
}

    private static double coefficient(double hz) {
        return 1.0 - Math.exp(-2.0 * Math.PI * hz / SAMPLE_RATE);
    }

    private static double rateHz(double position) {
        return 0.05 * Math.pow(400.0, position);
    }

    private static double smooth(double value, double target) {
        double next = value + CONTROL_SMOOTH * (target - value);
        return Math.abs(next - target) < 1.0e-12 ? target : next;
    }

    // SplitMix64 finalizer; two halves of each word form one triangular sample.
    // The two instances use separate states; no allocations or trigonometry here.
    private static long scramble(long value) {
        value = (value ^ (value >>> 30)) * 0xbf58476d1ce4e5b9L;
        value = (value ^ (value >>> 27)) * 0x94d049bb133111ebL;
        return value ^ (value >>> 31);
    }

    private double noiseSample() {
        long bits = scramble(noiseState += SEED_STEP);
        return ((int) bits / 2147483648.0 + (int) (bits >>> 32) / 2147483648.0) * 0.5;
    }

    private double rampRandom() {
        return (int) scramble(rampState += SEED_STEP) / 2147483648.0;
    }

    private static double warmLimit(double value) {
        // Gentle symmetric compression, zero preserving and strictly bounded.
        return value / (1.0 + 0.09 * Math.abs(value));
    }

    private void updateLamps() {
        double meter = bypassActive ? 0.0 : randomMeter;
        negativeLed.SetValue(clamp(-meter / 3.0, 0.0, 1.0));
        positiveLed.SetValue(clamp(meter / 3.0, 0.0, 1.0));
    }
    //[/user-code-and-variables]
}

 