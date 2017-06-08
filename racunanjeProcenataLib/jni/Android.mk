LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libRacunanjeProcenata
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := procenti.c

include $(BUILD_SHARED_LIBRARY)