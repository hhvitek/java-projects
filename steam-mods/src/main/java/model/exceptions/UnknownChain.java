package model.exceptions;

import model.modificationschains.ModificationsChain;

public class UnknownChain extends Exception {

    private String chainId;

    public UnknownChain(String chainId) {
        this.chainId = chainId;
    }

    @Override
    public String toString() {
        return "UnknownChain{" +
                "chainId='" + chainId + '\'' +
                '}';
    }
}
