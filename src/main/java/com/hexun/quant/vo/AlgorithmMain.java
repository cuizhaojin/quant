package com.hexun.quant.vo;

import java.io.Serializable;

/**
 * Created by hexun on 2018/2/8.
 */
public class AlgorithmMain implements Serializable {
    private static final long serialVersionUID = 6730294510769128486L;
    private String algorithm_id;
    private String algorithm_name;

    public String getAlgorithm_id() {
        return algorithm_id;
    }

    public void setAlgorithm_id(String algorithm_id) {
        this.algorithm_id = algorithm_id;
    }

    public String getAlgorithm_name() {
        return algorithm_name;
    }

    public void setAlgorithm_name(String algorithm_name) {
        this.algorithm_name = algorithm_name;
    }
}

