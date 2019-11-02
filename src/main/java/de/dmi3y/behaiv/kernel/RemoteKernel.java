package de.dmi3y.behaiv.kernel;

import de.dmi3y.behaiv.tools.Pair;
import io.swagger.client.ApiException;
import io.swagger.client.api.KernelApi;
import io.swagger.client.model.KernelData;

import java.util.List;

public class RemoteKernel extends Kernel {

    private final KernelApi kernelApi;

    public RemoteKernel(String id, KernelApi kernelApi) {
        super(id);
        this.kernelApi = kernelApi;

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

    }

    @Override
    public String predictOne(List<Double> features) {
        final KernelData data = new KernelData();

        return null;
    }

}
