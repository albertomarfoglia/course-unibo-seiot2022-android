package it.unibo.isi.seeiot.androidexample03.netutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpResponse {
    private int code;
    private InputStream response;

    public HttpResponse(final int code, final InputStream response){
        this.code = code;
        this.response = response;
    }

    public int code(){
        return code;
    }

    public InputStream content(){
        return response;
    }

    public String contentAsString() throws IOException {
        return readStream(response);
    }

    private String readStream(final InputStream in) throws IOException {
        final StringBuilder response = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }
}
