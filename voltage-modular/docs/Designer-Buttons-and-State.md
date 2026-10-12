# Designer buttons, mode state, and illumination

Reviewed 2026-10-11 against the installed `C:/ProgramData/Voltage/voltage.jar`, the current Model 62 2.0.0 source/project, its embedded button skin resources, and Cherry Audio's documentation.

## Findings from Model 62

1. `buttonPosition3` is a regular `VoltageButton`, not a `VoltageToggle`. The mode is a persistent setting, so the current control type is unsuitable for automatic latching, defaults, and host save/restore. Cherry Audio specifically identifies the Test Signals module's LOOP control as a toggle.
2. The current lighting calls use `SetValueNoNotification(value, false)`. Installed SDK bytecode shows that the second argument is the GUI-update flag. False changes the Java-side value while skipping `UpdateGUIValue`. NoNotification controls notification suppression independently of that flag.
3. The current `2500 Square Big On-On` skin embeds `2500-square-big-on-normal.svg`, `2500-square-big-on-hover.svg`, and `2500-square-big-on-pushed.svg`. All three are illuminated artwork. Changing a numeric button value cannot create an unlit background that is absent from the skin.
4. The previous headless substitute stored values regardless of the GUI flag and manually preloaded the mode's default to 1. It did not model native button release, native toggling, visual state, or host persistence. Its passing assertions did not establish that native lighting/default/recall worked.
5. The regular button constructor has no initially-toggled argument. Writing a generic `defaultValue` of 1 into a regular button's Designer metadata does not supply the native toggle initialization that `VoltageToggle(..., true, 0)` supplies. The current Initialize reads the regular button's value, which can overwrite the separately initialized High Fidelity boolean.
6. The current handler manually inverts its mode on a positive notification. That is an action-button pattern. A native toggle reports its resulting state on both 0 and 1, including recall and automation; its handler must assign that state rather than invert it.

The current project's saved Test Signals state includes `loopButton: true`. Its saved test-module controls omit `buttonPosition3`, corroborating the persistence distinction. This is evidence from saved data, not a native interaction test.

## Choose the control by its purpose

| Purpose | Control | State owner | Recall |
| --- | --- | --- | --- |
| Persistent mode, option, loop enable | Toggle Button / `VoltageToggle` | Native control; DSP mirrors its state | Host automatically saves/restores |
| Momentary start, stop, repair, trigger | Button / `VoltageButton` | The action updates internal state | Custom state needs explicit storage where appropriate |
| Running/armed/fault indicator | LED or explicit button artwork/overlay | DSP or transport state | Recomputed from actual system state |

A mode input and an operation indicator are different responsibilities. For Model 62, the 3rd mode belongs to the native toggle. PLAY/REC/SPLICE actions also have gates, power constraints, transport inertia, and faults, so their indicators should follow actual core state rather than a presumed button latch.

Sources: [Cherry Audio Buttons](https://docs.cherryaudio.com/voltage-module-designer/buttons), [Cherry Audio Toggle Buttons](https://docs.cherryaudio.com/voltage-module-designer/toggle-buttons).

## Native mode pattern

Designer: add or convert to **Toggle Button**, use a toggle-compatible skin with visibly different OFF and ON artwork, enable **Is Initially Toggled**, and set **Group ID = 0** for an independent mode. Preserve the control's identity when converting an established project. Match the Designer metadata and generated Java together.

The installed SDK constructor is:

```java
modeButton = new VoltageToggle("modeButton", "High Fidelity Mode", this, true, 0);
```

Initialize derives the DSP state from `GetValue()`. Handle both resulting states in Notify:

```java
case Button_Changed:
    if (component == modeButton) {
        highFidelityMode = doubleValue >= 0.5;
        wornTarget = !highFidelityMode;
        break;
    }
    // Other momentary action buttons use their own press-only handlers.
    break;
```

Do not invert the mode again: the native control already toggled it. Do not put the mode behind an action-button `doubleValue > .5` filter; that would ignore OFF. Do not reject a mode notification just because processing is bypassed: selected control state must remain consistent when processing resumes. During bulk preset/variation restoration, defer applying state as necessary and synchronize after the restore finishes. Do not have a GUI timer repeatedly write mode state back into an input control.

Toggle states are restored by the host, with `Button_Changed` notifications after Initialize. They also participate in the host's normal undo/redo support. [Cherry Audio state and module functions](https://docs.cherryaudio.com/voltage-module-designer/module-functions-and-variables).

## Programmatic visual updates

Installed SDK behavior:

```java
button.SetValueNoNotification(active ? 1.0 : 0.0, true);
```

This changes the value, requests the GUI update, and avoids calling the action handler. With the final flag false, the GUI update is suppressed. `SetValue(value, true)` requests the GUI update and also sends a `Button_Changed` notification; using it merely to light a button can trigger its action again.

The superclass updates the GUI only when its stored value changes. Mixing GUI-suppressed writes and GUI-enabled writes can leave a stale display if the later write repeats the already-stored value. Avoid the mixed pattern.

For ordinary action buttons, mouse release still returns the native pressed state to zero. A pressed-state image is therefore not inherently a persistent activity lamp. Reliable activity indications can use a separate LED or a deliberate skin/overlay update that does not alter the input value. Model 62's already available On-On and Off-Off skin families can support that pattern, provided both resource sets are embedded. Prefer updating those graphics when the actual activity state changes, with flashing handled by the GUI timer.

A toggle skin has its own ON/OFF art plus normal/hover/pushed variations. A generic button skin only has interaction states. Text-overlay color APIs change the caption; they do not turn background illumination on or off. The installed SDK additionally exposes state-specific and toggled-state-specific overlay APIs. [Cherry Audio Skin Editor](https://docs.cherryaudio.com/voltage-module-designer/the-skin-editor).

## Validation standard

- Assert that a persistent mode's Java class, Designer control type, initial toggle setting, skin type, and default agree.
- Test explicit ON and OFF notifications, repeated state assignment, recall after Initialize, reset, automation, and changes during bypass. An OFF recall must remain OFF rather than being inverted.
- Make any substitute control model the documented notification and GUI-update behavior. Verify visible-state requests independently of the stored Java value.
- Audit every artwork state, including hover and pressed. A lamp must have genuinely distinct OFF and ON images.
- Native Designer verification remains required for actual mouse press/release, sustained illumination, hover appearance, preset save/reopen, and undo/redo. Headless DSP tests cannot establish these.

## Required correction for the current candidate

Convert the 3rd mode to a genuine Toggle Button with the matching 2500-style toggle skin and ON default. Replace manual inversion with state assignment and remove the timer's mode-value writes. Repair programmatic transport indicator updates to request the GUI update or use explicit lit/unlit artwork driven by the core. Update the test substitute so it can detect the previous false-flag bug. These are control/UI corrections; they do not require a change to the selected sound design.
