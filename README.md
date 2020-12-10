# iexec-common

## Steps to genreate Java Wrappers for Smart Contacts

```
git clone https://github.com/iExecBlockchainComputing/PoCo-dev/
cd PoCo-dev
git checkout x.y.z
npm i
./node_modules/.bin/truffle compile
# Get web3j-cli (deprecated)
curl -L https://get.web3j.io | sh
# Import latest web3j in gradle: https://github.com/web3j/web3j/releases
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/App.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/Dataset.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/Ownable.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated

#comment tuple json parts
web3j truffle generate ~/iexecdev/PoCo-dev/build/contracts/IexecInterfaceTokenABILegacy.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated
# Rename IexecInterfaceTokenABILegacy.java to IexecHubContract.java

# Get epirus-cli (recommended)
# Clean IexecLibOrders_v5.OrderOperationEnum references from IexecLibOrders_v5.json, then
epirus generate solidity generate --abiFile=$HOME/iexecdev/PoCo-dev/build/abi/IexecLibOrders_v5.json -o ~/iexecdev/iexec-common/src/main/java/ -p com.iexec.common.contract.generated

```
