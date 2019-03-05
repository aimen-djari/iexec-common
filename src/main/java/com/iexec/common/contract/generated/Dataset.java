package com.iexec.common.contract.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 * Poco-dev: commit 0aa794bd1040a5308142c87ad78e1d3f9a17a9cb
 * <p>Generated with web3j version 4.1.1.
 */
public class Dataset extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506040516105de3803806105de8339810180604052610032919081019061018f565b60008054600160a060020a031916600160a060020a0386161790558251610060906001906020860190610081565b508151610074906002906020850190610081565b50600355506102b8915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100c257805160ff19168380011785556100ef565b828001600101855582156100ef579182015b828111156100ef5782518255916020019190600101906100d4565b506100fb9291506100ff565b5090565b61011991905b808211156100fb5760008155600101610105565b90565b60006101288251610277565b9392505050565b60006101288251610119565b6000601f8201831361014c57600080fd5b815161015f61015a82610244565b61021e565b9150808252602083016020830185838301111561017b57600080fd5b610186838284610288565b50505092915050565b600080600080608085870312156101a557600080fd5b60006101b1878761011c565b94505060208501516001604060020a038111156101cd57600080fd5b6101d98782880161013b565b93505060408501516001604060020a038111156101f557600080fd5b6102018782880161013b565b92505060606102128782880161012f565b91505092959194509250565b6040518181016001604060020a038111828210171561023c57600080fd5b604052919050565b60006001604060020a0382111561025a57600080fd5b506020601f91909101601f19160190565b600160a060020a031690565b60006102828261026b565b92915050565b60005b838110156102a357818101518382015260200161028b565b838111156102b2576000848401525b50505050565b610317806102c76000396000f3fe6080604052600436106100615763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630847c43181146100665780631ba99d7e14610091578063a61ca6c5146100b3578063deff41c1146100c8575b600080fd5b34801561007257600080fd5b5061007b6100ea565b6040516100889190610260565b60405180910390f35b34801561009d57600080fd5b506100a6610177565b6040516100889190610252565b3480156100bf57600080fd5b5061007b61017d565b3480156100d457600080fd5b506100dd6101d5565b604051610088919061023e565b60018054604080516020600284861615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561016f5780601f106101445761010080835404028352916020019161016f565b820191906000526020600020905b81548152906001019060200180831161015257829003601f168201915b505050505081565b60035481565b6002805460408051602060018416156101000260001901909316849004601f8101849004840282018401909252818152929183018282801561016f5780601f106101445761010080835404028352916020019161016f565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b6101fa8161027c565b82525050565b6101fa81610287565b600061021482610278565b8084526102288160208601602086016102a3565b610231816102d3565b9093016020019392505050565b6020810161024c82846101f1565b92915050565b6020810161024c8284610200565b602080825281016102718184610209565b9392505050565b5190565b600061024c8261028a565b90565b73ffffffffffffffffffffffffffffffffffffffff1690565b60005b838110156102be5781810151838201526020016102a6565b838111156102cd576000848401525b50505050565b601f01601f19169056fea265627a7a72305820ed71d2d1a1c327d80ee6153341856985bb2941bce10c783943aa8e591529983e6c6578706572696d656e74616cf50037";

    public static final String FUNC_M_DATASETNAME = "m_datasetName";

    public static final String FUNC_M_DATASETCHECKSUM = "m_datasetChecksum";

    public static final String FUNC_M_DATASETMULTIADDR = "m_datasetMultiaddr";

    public static final String FUNC_M_OWNER = "m_owner";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Dataset(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Dataset(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Dataset(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Dataset(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> m_datasetName() {
        final Function function = new Function(FUNC_M_DATASETNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> m_datasetChecksum() {
        final Function function = new Function(FUNC_M_DATASETCHECKSUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<byte[]> m_datasetMultiaddr() {
        final Function function = new Function(FUNC_M_DATASETMULTIADDR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> m_owner() {
        final Function function = new Function(FUNC_M_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static Dataset load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Dataset(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Dataset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Dataset(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Dataset load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Dataset(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Dataset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Dataset(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Dataset> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Dataset> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Dataset> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Dataset> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}