package com.github.shvul.wda.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public interface OutputImageType<T> {
    OutputImageType<String> BASE64 = new OutputImageType<String>() {
        public String convertFromBase64Png(String base64Png) {
            return base64Png;
        }

        public String toString() {
            return "OutputType.BASE64";
        }
    };
    OutputImageType<byte[]> BYTES = new OutputImageType<byte[]>() {
        public byte[] convertFromBase64Png(String base64Png) {
            return Base64.getMimeDecoder().decode(base64Png);
        }

        public String toString() {
            return "OutputType.BYTES";
        }
    };
    OutputImageType<File> FILE = new OutputImageType<File>() {
        public File convertFromBase64Png(String base64Png) {
            return this.save(BYTES.convertFromBase64Png(base64Png));
        }

        private File save(byte[] data) {
            try {
                File tmpFile = File.createTempFile("screenshot", ".png");
                tmpFile.deleteOnExit();
                try(FileOutputStream stream = new FileOutputStream(tmpFile)) {
                    stream.write(data);
                }
                return tmpFile;
            } catch (IOException e) {
                throw new WebDriverAgentException(e);
            }
        }

        public String toString() {
            return "OutputType.FILE";
        }
    };

    T convertFromBase64Png(String var1);
}
