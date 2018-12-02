package br.ucam_campos.enzo.volleyp2;

public interface VolleyResponseListener {
    void onFailure(String message);

    void onSuccess(String response);
}
