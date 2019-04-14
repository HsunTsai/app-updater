package com.hsun.appupdater;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

public class CustomInputStreamRequest extends Request<byte[]> {

    private final Response.Listener<byte[]> listener;
    private Map<String, String> params;
    //create a static map for directly accessing headers
    public Map<String, String> responseHeaders;

    public CustomInputStreamRequest(int method, String mUrl, Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params) {
        // TODO Auto-generated constructor stub
        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        this.listener = listener;
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        listener.onResponse(response);
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        try {
//            if (response != null) {
//                String content = request.responseHeaders.get("Content-Disposition")
//                        .toString();
//                StringTokenizer st = new StringTokenizer(content, "=");
//                String[] arrTag = st.toArray();
//
//                String filename = arrTag[1];
//                filename = filename.replace(":", ".");
//                Log.d("DEBUG::FILE NAME", filename);
//
//                try {
//                    long lenghtOfFile = response.length;
//
//                    InputStream input = new ByteArrayInputStream(response);
//
//                    File path = Environment.getExternalStorageDirectory();
//                    File file = new File(path, filename);
//                    map.put("resume_path", file.toString());
//                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
//                    byte data[] = new byte[1024];
//
//                    long total = 0;
//
//                    while ((count = input.read(data)) != -1) {
//                        total += count;
//                        output.write(data, 0, count);
//                    }
//
//                    output.flush();
//
//                    output.close();
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}