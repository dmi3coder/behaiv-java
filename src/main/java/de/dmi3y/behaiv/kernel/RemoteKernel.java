package de.dmi3y.behaiv.kernel;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import de.dmi3y.behaiv.storage.BehaivStorage;
import de.dmi3y.behaiv.tools.Pair;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.KernelApi;
import io.swagger.client.model.InlineResponse2001;
import io.swagger.client.model.KernelData;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RemoteKernel extends Kernel {

    private final KernelApi kernelApi;

    public RemoteKernel(String id, KernelApi kernelApi) {
        super(id);
        this.kernelApi = kernelApi;
        final ApiClient apiClient = kernelApi.getApiClient();
        final String uniqueId = UUID.randomUUID().toString();
        apiClient.setBasePath("http://localhost:5000")
                .getHttpClient().interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer " + uniqueId).build();
                return chain.proceed(newRequest);
            }
        });

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean readyToPredict() {
        try {
            return kernelApi.kernelPredictionReadyGet().isPredictionReady();
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void fit(List<Pair<List<Double>, String>> data) {
        for (int i = 0; i < data.size(); i++) {
            final Pair<List<Double>, String> pair = data.get(i);
            updateSingle(pair.getKey(), pair.getValue());
        }

    }

    @Override
    public String predictOne(List<Double> features) {
        final KernelData data = new KernelData();
        final HashMap<String, BigDecimal> featuresMap = new HashMap<>();
        for (int i = 0; i < features.size(); i++) {
            featuresMap.put("feature_" + i, new BigDecimal(features.get(i)));
        }
        data.setFeatures(featuresMap);
        try {
            final InlineResponse2001 predictionResult = kernelApi.kernelPredictPost(data);
            return predictionResult.getLabel();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateSingle(List<Double> features, String label) {
        final KernelData data = new KernelData();
        final HashMap<String, BigDecimal> featuresMap = new HashMap<>();
        for (int i = 0; i < features.size(); i++) {
            featuresMap.put("feature_" + i, new BigDecimal(features.get(i)));
        }
        data.setFeatures(featuresMap);
        data.setLabel(label);
        try {
            kernelApi.kernelFeedPost(data);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(BehaivStorage storage) throws IOException {
        // API is responsible for it
    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {
        // API is responsible for it
    }

}
