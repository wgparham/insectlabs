# SW1 v2.0.1 review

Lamp updates now use the Model 62 SetValueNoNotification(value, true) pattern. Explicit mouse down/up events preserve held GATE behavior and TOGGLE rising-edge detection independently of lamp values; native value notifications are ignored during a held mouse gesture. The 25 ms GUI timer maintains the selection lamp.

User confirmed working native switching and lamp control. Final change reverses only the lamp value mapping: upper/default uses 0 (bright), lower/alternate uses 1 (dim), as established by user testing of this ON/ON skin. The user verified the final illumination and canonized v2.0.1 on 2026-10-11. Audio routing and filtering are unchanged.

Final checks: exported and embedded Java match semantically, version notes are v2.0.1, and the integrity manifest is refreshed. Earlier canonical releases are preserved.
