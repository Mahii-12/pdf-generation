package com.pdfgen.service.util;

public class Constants {
    public enum RESPONSE_STATUS {
        OK(200), ERROR(500), MOVED(301);
        private final int status;

        RESPONSE_STATUS(int i) {
            this.status = i;
        }

        public int getValue() {
            return this.status;
        }
    }

    public enum RESPONSE_MESSAGE {

        SUCCESS("success"),
        ERROR("error");

        private final String message;

        RESPONSE_MESSAGE(String msg) {
            this.message = msg;
        }

        public String getValue() {
            return this.message;
        }
    }
}