#include "procenti.h"

JNIEXPORT jfloat JNICALL Java_gravedigger_example_ra127_12014_com_taskmanager_NativeClass_izracunajProcente
  (JNIEnv *env, jobject obj, jfloat zavrseni, jfloat svi){
	  return (jfloat)((zavrseni/svi) * 100);
  }