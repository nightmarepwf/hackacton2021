 # Statically turn off all debugging facilities and assertions
 -assumenosideeffects class kotlinx.coroutines.DebugKt {
     boolean getASSERTIONS_ENABLED() return false;
     boolean getDEBUG() return false;
     boolean getRECOVER_STACK_TRACES() return false;
 }